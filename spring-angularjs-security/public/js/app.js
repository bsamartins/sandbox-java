// $routeProvider, 
var app = angular.module('app', ['ngRoute'])
	.config(function($routeProvider, $httpProvider) {
		$routeProvider
			.when('/', {
				templateUrl : 'home.html',
				controller : 'homeController'
			})
			.when('/login', {
				templateUrl : 'login.html',
				controller : 'loginController'
			})
			.otherwise('/');
				
		$httpProvider.useLegacyPromiseExtensions = false;
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';		
	})  	
	.controller('homeController', function($scope, $http) {
		$scope.greeting = 'World'
	})
;  