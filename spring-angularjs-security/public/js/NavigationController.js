app.controller('navigationController', function($rootScope, $scope, $http, $location, authenticationService) {
	$scope.logout = function() {
		console.log($rootScope.user)
		authenticationService.logout().then(function() {
			$location.path("/");
		})
	}		
})