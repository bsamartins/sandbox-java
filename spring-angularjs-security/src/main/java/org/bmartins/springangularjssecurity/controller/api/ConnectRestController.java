package org.bmartins.springangularjssecurity.controller.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.connect.web.DisconnectInterceptor;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api/connect")
public class ConnectRestController implements InitializingBean {
	
	public enum ConnectionStatus { CONNECTED, NOT_CONNECTED }
	
	private final static Logger logger = LoggerFactory.getLogger(ConnectController.class);
	
	private final ConnectionFactoryLocator connectionFactoryLocator;	
	private final ConnectionRepository connectionRepository;

	private final MultiValueMap<Class<?>, ConnectInterceptor<?>> connectInterceptors = new LinkedMultiValueMap<Class<?>, ConnectInterceptor<?>>();
	private final MultiValueMap<Class<?>, DisconnectInterceptor<?>> disconnectInterceptors = new LinkedMultiValueMap<Class<?>, DisconnectInterceptor<?>>();

	private ConnectSupport connectSupport;
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	@Autowired
	private ObjectMapper jacksonObjectMapper;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * Constructs a ConnectController.
	 * @param connectionFactoryLocator the locator for {@link ConnectionFactory} instances needed to establish connections
	 * @param connectionRepository the current user's {@link ConnectionRepository} needed to persist connections; must be a proxy to a request-scoped bean
	 */
	@Inject
	public ConnectRestController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.connectionRepository = connectionRepository;
	}

	/**
	 * Configure the list of connect interceptors that should receive callbacks during the connection process.
	 * Convenient when an instance of this class is configured using a tool that supports JavaBeans-based configuration.
	 * @param interceptors the connect interceptors to add
	 */
	public void setConnectInterceptors(List<ConnectInterceptor<?>> interceptors) {
		for (ConnectInterceptor<?> interceptor : interceptors) {
			addInterceptor(interceptor);
		}
	}

	/**
	 * Configure the list of discconnect interceptors that should receive callbacks when connections are removed.
	 * Convenient when an instance of this class is configured using a tool that supports JavaBeans-based configuration.
	 * @param interceptors the connect interceptors to add
	 */
	public void setDisconnectInterceptors(List<DisconnectInterceptor<?>> interceptors) {
		for (DisconnectInterceptor<?> interceptor : interceptors) {
			addDisconnectInterceptor(interceptor);
		}
	}

	/**
	 * Configures the base secure URL for the application this controller is being used in e.g. <code>https://myapp.com</code>. Defaults to null.
	 * If specified, will be used to generate OAuth callback URLs.
	 * If not specified, OAuth callback URLs are generated from web request info. 
	 * You may wish to set this property if requests into your application flow through a proxy to your application server.
	 * In this case, the request URI may contain a scheme, host, and/or port value that points to an internal server not appropriate for an external callback URL.
	 * If you have this problem, you can set this property to the base external URL for your application and it will be used to construct the callback URL instead.
	 * @param applicationUrl the application URL value
	 */
	public void setApplicationUrl(String applicationUrl) {
		connectSupport.setApplicationUrl(applicationUrl);
	}
	
	/**
	 * Sets a strategy to use when persisting information that is to survive past the boundaries of a request.
	 * The default strategy is to set the data as attributes in the HTTP Session.
	 * @param sessionStrategy the session strategy.
	 */
	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}
	
	/**
	 * Adds a ConnectInterceptor to receive callbacks during the connection process.
	 * Useful for programmatic configuration.
	 * @param interceptor the connect interceptor to add
	 */
	public void addInterceptor(ConnectInterceptor<?> interceptor) {
		Class<?> serviceApiType = GenericTypeResolver.resolveTypeArgument(interceptor.getClass(), ConnectInterceptor.class);
		connectInterceptors.add(serviceApiType, interceptor);
	}

	/**
	 * Adds a DisconnectInterceptor to receive callbacks during the disconnection process.
	 * Useful for programmatic configuration.
	 * @param interceptor the connect interceptor to add
	 */
	public void addDisconnectInterceptor(DisconnectInterceptor<?> interceptor) {
		Class<?> serviceApiType = GenericTypeResolver.resolveTypeArgument(interceptor.getClass(), DisconnectInterceptor.class);
		disconnectInterceptors.add(serviceApiType, interceptor);
	}

	/**
	 * Render the status of connections across all providers to the user as HTML in their web browser.
	 */
	@RequestMapping(method=RequestMethod.GET)	
	public Map<String, Object> connectionStatus() {		
		Map<String, List<Connection<?>>> connections = connectionRepository.findAllConnections();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("providerIds", connectionFactoryLocator.registeredProviderIds());		
		model.put("connectionMap", connections);
		
		return model;
	}
	
	/**
	 * Render the status of the connections to the service provider to the user as HTML in their web browser.
	 */
	@RequestMapping(value="/{providerId}", method=RequestMethod.GET)
	public Map<String, Object> connectionStatus(@PathVariable String providerId, NativeWebRequest request) {
		List<Connection<?>> connections = connectionRepository.findConnections(providerId);
		ConnectionStatus connectionStatus = connections.isEmpty() ? ConnectionStatus.NOT_CONNECTED : ConnectionStatus.CONNECTED;

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("connectionStatus", connectionStatus);
		
		return model;
	}

	/**
	 * Process a connect form submission by commencing the process of establishing a connection to the provider on behalf of the member.
	 * For OAuth1, fetches a new request token from the provider, temporarily stores it in the session, then redirects the member to the provider's site for authorization.
	 * For OAuth2, redirects the user to the provider's site for authorization.
	 */
	@RequestMapping(value="/{providerId}", method=RequestMethod.POST)
	public Map<String, Object> connect(@PathVariable String providerId, NativeWebRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>(); 
		preConnect(connectionFactory, parameters, request);
		try {			
			model.put(PROVIDER_URL_ATTRIBUTE, connectSupport.buildOAuthUrl(connectionFactory, request, parameters));
			response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		} catch (Exception e) {
			model.put(PROVIDER_ERROR_ATTRIBUTE, e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.warn("Exception while trying to connect to provider {}: {}", providerId, e);
		}
		
		return model;
	}

	/**
	 * Process the authorization callback from an OAuth 1 service provider.
	 * Called after the user authorizes the connection, generally done by having he or she click "Allow" in their web browser at the provider's site.
	 * On authorization verification, connects the user's local account to the account they hold at the service provider
	 * Removes the request token from the session since it is no longer valid after the connection is established.
	 */
	@RequestMapping(value="/{providerId}", method=RequestMethod.GET, params="oauth_token")
	public String oauth1Callback(@PathVariable String providerId, NativeWebRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			OAuth1ConnectionFactory<?> connectionFactory = (OAuth1ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
			Connection<?> connection = connectSupport.completeConnection(connectionFactory, request);
			authenticate(connection);
			addConnection(connection, connectionFactory, request);			
			model.put("status", "SUCCESS");
		} catch (Exception e) {			
			model.put(PROVIDER_ERROR_ATTRIBUTE, e.getMessage());
			logger.warn("Exception while handling OAuth1 callback: {}", e);
		}		
		
		return authCompleted(model);
	}

	/**
	 * Process the authorization callback from an OAuth 2 service provider.
	 * Called after the user authorizes the connection, generally done by having he or she click "Allow" in their web browser at the provider's site.
	 * On authorization verification, connects the user's local account to the account they hold at the service provider.
	 */
	@RequestMapping(value="/{providerId}", method=RequestMethod.GET, params="code")
	public String oauth2Callback(@PathVariable String providerId, NativeWebRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
			Connection<?> connection = connectSupport.completeConnection(connectionFactory, request);
			authenticate(connection);
			addConnection(connection, connectionFactory, request);
			model.put("status", "SUCCESS");
		} catch (Exception e) {
			model.put(PROVIDER_ERROR_ATTRIBUTE, e.getMessage());
			logger.warn("Exception while handling OAuth2 callback (" + e.getMessage() + "). Redirecting to " + providerId +" connection status page.");
		}
		
		return authCompleted(model);
	}
	
	/**
	 * Process the authorization callback from an OAuth 2 service provider.
	 * Called after the user authorizes the connection, generally done by having he or she click "Allow" in their web browser at the provider's site.
	 * On authorization verification, connects the user's local account to the account they hold at the service provider.
	 */
	@RequestMapping(value="/{providerId}", method=RequestMethod.GET, params="denied")
	public String oauth2DeniedCallback(@PathVariable String providerId, NativeWebRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();		
		model.put("status", "DENIED");
		return authCompleted(model);
	}
	
	/**
	 * Process an error callback from an OAuth 2 authorization as described at http://tools.ietf.org/html/rfc6749#section-4.1.2.1.
	 * Called after upon redirect from an OAuth 2 provider when there is some sort of error during authorization, typically because the user denied authorization.
	 */
	@RequestMapping(value="/{providerId}", method=RequestMethod.GET, params="error")
	public String oauth2ErrorCallback(@PathVariable String providerId, 
			@RequestParam("error") String error, 
			@RequestParam(value="error_description", required=false) String errorDescription,
			@RequestParam(value="error_uri", required=false) String errorUri,
			NativeWebRequest request) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("error", error);
		if (errorDescription != null) { 
			model.put("errorDescription", errorDescription); 
		}
		
		if (errorUri != null) { 
			model.put("errorUri", errorUri); 
		}
		return authCompleted(model);
	}

	/**
	 * Remove all provider connections for a user account.
	 * The user has decided they no longer wish to use the service provider from this application.
	 * Note: requires {@link HiddenHttpMethodFilter} to be registered with the '_method' request parameter set to 'DELETE' to convert web browser POSTs to DELETE requests.
	 */
	@RequestMapping(value="/{providerId}", method=RequestMethod.DELETE)
	public void removeConnections(@PathVariable String providerId, NativeWebRequest request) {
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
		preDisconnect(connectionFactory, request);
		connectionRepository.removeConnections(providerId);
		postDisconnect(connectionFactory, request);
	}

	/**
	 * Remove a single provider connection associated with a user account.
	 * The user has decided they no longer wish to use the service provider account from this application.
	 * Note: requires {@link HiddenHttpMethodFilter} to be registered with the '_method' request parameter set to 'DELETE' to convert web browser POSTs to DELETE requests.
	 */
	@RequestMapping(value="/{providerId}/{providerUserId}", method=RequestMethod.DELETE)
	public void removeConnection(@PathVariable String providerId, @PathVariable String providerUserId, NativeWebRequest request) {
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
		preDisconnect(connectionFactory, request);
		connectionRepository.removeConnection(new ConnectionKey(providerId, providerUserId));
		postDisconnect(connectionFactory, request);
	}

	// From InitializingBean
	public void afterPropertiesSet() throws Exception {
		this.connectSupport = new ConnectSupport(sessionStrategy);
	}

	private void addConnection(Connection<?> connection, ConnectionFactory<?> connectionFactory, WebRequest request) {
		try {
			connectionRepository.addConnection(connection);
			postConnect(connectionFactory, connection, request);
		} catch (DuplicateConnectionException e) {
			sessionStrategy.setAttribute(request, DUPLICATE_CONNECTION_ATTRIBUTE, e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void preConnect(ConnectionFactory<?> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
		for (ConnectInterceptor interceptor : interceptingConnectionsTo(connectionFactory)) {
			interceptor.preConnect(connectionFactory, parameters, request);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void postConnect(ConnectionFactory<?> connectionFactory, Connection<?> connection, WebRequest request) {
		for (ConnectInterceptor interceptor : interceptingConnectionsTo(connectionFactory)) {
			interceptor.postConnect(connection, request);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void preDisconnect(ConnectionFactory<?> connectionFactory, WebRequest request) {
		for (DisconnectInterceptor interceptor : interceptingDisconnectionsTo(connectionFactory)) {
			interceptor.preDisconnect(connectionFactory, request);
		}		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void postDisconnect(ConnectionFactory<?> connectionFactory, WebRequest request) {
		for (DisconnectInterceptor interceptor : interceptingDisconnectionsTo(connectionFactory)) {
			interceptor.postDisconnect(connectionFactory, request);
		}		
	}

	private List<ConnectInterceptor<?>> interceptingConnectionsTo(ConnectionFactory<?> connectionFactory) {
		Class<?> serviceType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
		List<ConnectInterceptor<?>> typedInterceptors = connectInterceptors.get(serviceType);
		if (typedInterceptors == null) {
			typedInterceptors = Collections.emptyList();
		}
		return typedInterceptors;
	}

	private List<DisconnectInterceptor<?>> interceptingDisconnectionsTo(ConnectionFactory<?> connectionFactory) {
		Class<?> serviceType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
		List<DisconnectInterceptor<?>> typedInterceptors = disconnectInterceptors.get(serviceType);
		if (typedInterceptors == null) {
			typedInterceptors = Collections.emptyList();
		}
		return typedInterceptors;
	}

	private static final String DUPLICATE_CONNECTION_ATTRIBUTE = "social_addConnection_duplicate";	
	private static final String PROVIDER_ERROR_ATTRIBUTE = "social_provider_error";
	private static final String PROVIDER_URL_ATTRIBUTE = "social_provider_url";
	//private static final String AUTHORIZATION_ERROR_ATTRIBUTE = "social_authorization_error";

	private String authCompleted(Map<String, Object> params) throws Exception {
		
		String dataResult = jacksonObjectMapper.writeValueAsString(params);
		
		return "<!DOCTYPE html>" 
		+ "<html>"
		+ "<title>Page Title</title>"
		+ "<script>"
		    + "function myFunction() {"
		       + "if(window.opener) window.opener.onAuth(" + dataResult + ");"
		    + "}"
		    + "window.onload=myFunction;"
		+ "</script>"
		+ "<body>"
		+ "</body>"
		+ "</html>";
	}
	
	private void authenticate(Connection<?> connection) throws Exception {
		SocialAuthenticationToken socialAuthenticationToken = new SocialAuthenticationToken(connection, new HashMap<>());
		
		SecurityContextHolder
			.getContext()
			.setAuthentication(authenticationManager.authenticate(socialAuthenticationToken));
	}
}
