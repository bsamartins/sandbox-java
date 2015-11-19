app.factory('socialAuthenticationService', function($http, $rootScope, $q, $http, $window, $document) {
	
	function login(providerId) {
		return $http.post('/api/connect/' + providerId);				
	}
	
	function connect(providerId) {
		return login(providerId)
		.then(function(d) {
			return d;
		}, function(e) {
			if(e.status == 303) {				
				return openAuthProviderPopup(e.data);
			} else {
				return $q.reject(e);
			}
		});
	}
	
	function openAuthProviderPopup(data) {
		var windowOptions = "toolbar=0,scrollbars=1,status=1,resizable=1,location=1,menuBar=0"
		var popup = $window.open(data.social_provider_url, "Autorization", windowOptions);
		
		var deferred = $q.defer();
		
		if(popup) {
			$window.onAuth = function(data) { 
				if(data.status == 'SUCCESS') {
					popup.close();
					deferred.resolve(data);
				} else {
					popup.close();
					deferred.reject(data);
				}														 
			};
			popup.focus();
		} else {
			var err = { status: 'POPUP_NOT_OPEN' };
			return deferred.reject(err);
		}				
		
		return deferred.promise;
	}
	
	return {
		authenticate: connect,
		authenticateWithTwitter: function() {
			return connect('twitter');
		},
		authenticateWithGithub: function() {
			return connect('github');
		},
		authenticateWithLinkedin: function() {
			return connect('linkedin');
		},				
		authenticateWithGoogle: function() {
			return connect('google');
		}
	}	
})