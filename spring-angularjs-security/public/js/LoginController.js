app.controller('loginController', function($rootScope, $scope, $http, $location, authenticationService) {	
	$scope.credentials = {};
	$scope.login = function() {
		authenticationService.login($scope.credentials).then(function(data) {			
			if (data) {
				$location.path("/");
				$scope.error = false;
			} else {
				$scope.error = true;
			}
		}, function() {
			$scope.error = true;
		});
	};  
})