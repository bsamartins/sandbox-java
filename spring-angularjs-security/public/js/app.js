// $routeProvider, 
angular.module('app', ['ngRoute'])
	.config(function($routeProvider, $httpProvider) {
		$routeProvider
			.when('/', {
				templateUrl : 'home.html',
				controller : 'homeController'
			})
			.when('/login', {
				templateUrl : 'login.html',
				controller : 'navigationController'
			})
			.otherwise('/');
				
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		
		var interceptor = ['$rootScope', '$q', "Base64", function (scope, $q, Base64) {
	        function success(response) {
	            return response;
	        }
	        function error(response) {
	            var status = response.status;
	            if (status == 401) {
	                //AuthFactory.clearUser();
	                window.location = "/account/login?redirectUrl=" + Base64.encode(document.URL);
	                return;
	            }
	            // otherwise
	            return $q.reject(response);
	        }
	        return function (promise) {
	            return promise.then(success, error);
	        }
	    }];
		
		//$httpProvider.responseInterceptors.push(interceptor);
	})  
	.controller('navigationController', function($rootScope, $scope, $http, $location) {
		function authenticate(credentials, callback) {
			var headers = credentials ? {
				authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
			} : {};
		  
			$http.get('api/user', {headers : headers}).success(function(data) {
				if (data.name) {
					$rootScope.authenticated = true;
				} else {
					$rootScope.authenticated = false;
				}
				callback && callback();
			}).error(function() {
				$rootScope.authenticated = false;
				callback && callback();
			});
		}		
		
		authenticate();		
		
		$scope.credentials = {};
		$scope.login = function() {
			authenticate($scope.credentials, function() {
				if ($rootScope.authenticated) {
					$location.path("#/");
					$scope.error = false;
				} else {
					$location.path("#/login");
					$scope.error = true;
				}
			});
		};
	  
		$scope.logout = function() {
			$http.post('/api/logout', {}).success(function() {
				$rootScope.authenticated = false;
				$location.path("#/");
			}).error(function(data) {
				$rootScope.authenticated = false;
			});
		}
	})
	.controller('homeController', function($scope, $http) {
		$scope.greeting = 'World'
	})
;  