app.factory('authenticationService', function($http, $rootScope, $q) {
	
	function authenticate(credentials) {
		var headers = credentials ? {
			authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
		} : {};
	  
		return $http.get('api/user', {headers : headers})
			.then(function(result) {
				return result.data
			}, function(e) {
				return $q.reject(e)
			});
	}			
	
	function getUser() {
		return authenticate()
	}
	
	function init() {
		getUser().then(function(data) {					
			if(data) {
				$rootScope.authenticated = true
				$rootScope.user = data
			} else {
				$rootScope.authenticated = false
				$rootScope.user = undefined
			}
		}, function(e) {
			$rootScope.authenticated = false
			$rootScope.user = undefined
		})
	}
	
	init()
	
	return {
		getUser: function() {
			return getUser()
		},
		login: function(credentials) {
			return authenticate(credentials)
				.then(function(data) {
					console.log(data)
					if(data) {
						$rootScope.authenticated = true
						$rootScope.user = data
					} else {
						$rootScope.authenticated = false
						$rootScope.user = undefined
					}
					return data;
				}, function(e) {					
					$rootScope.authenticated = false
					$rootScope.user = undefined
					return $q.reject(e);
				})
		},
		logout: function() {
			return $http.get('/api/user/logout')
			.then(function() {
				$rootScope.authenticated = false
				$rootScope.user = undefined
			});
		}
	}	
})