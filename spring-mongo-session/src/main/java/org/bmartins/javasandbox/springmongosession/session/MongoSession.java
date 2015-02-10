package org.bmartins.javasandbox.springmongosession.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.session.ExpiringSession;

public class MongoSession implements ExpiringSession {
	
    /**
     * Default {@link #setMaxInactiveIntervalInSeconds(int)} (30 minutes)
     */
    public static final int DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS = 1800;

    @Id
	private String id = UUID.randomUUID().toString();
    
    private Map<String, Object> sessionAttrs = new HashMap<String, Object>();
    private long creationTime = System.currentTimeMillis();
    private long lastAccessedTime = creationTime;

    /**
     * Defaults to 30 minutes
     */
    private int maxInactiveInterval = DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public <T> T getAttribute(String attributeName) {		
		return (T)sessionAttrs.get(attributeName);
	}

	@Override
	public Set<String> getAttributeNames() {
		return sessionAttrs.keySet();
	}

	@Override
	public void setAttribute(String attributeName, Object attributeValue) {		
		sessionAttrs.put(attributeName, attributeValue);
	}

	@Override
	public void removeAttribute(String attributeName) {
		sessionAttrs.remove(attributeName);		
	}

	@Override
	public long getCreationTime() {
		return this.creationTime;
	}

	@Override
	public long getLastAccessedTime() {
		return creationTime;
	}

	@Override
	public void setMaxInactiveIntervalInSeconds(int interval) {
		this.maxInactiveInterval = interval;
	}

	@Override
	public int getMaxInactiveIntervalInSeconds() {
		return maxInactiveInterval;
	}

	@Override
	public boolean isExpired() {
		long now = System.currentTimeMillis();
		if(maxInactiveInterval < 0) {
			return false;
		}
		return now - TimeUnit.SECONDS.toMillis(maxInactiveInterval) >= lastAccessedTime;	
	}

	void setLastAccessedTime(long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}
}
