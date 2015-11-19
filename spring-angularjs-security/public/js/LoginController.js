app.controller('loginController', function($rootScope, $scope, $http, $location, $q, authenticationService) {	
	
	var DENIED_MESSAGE = "Login was cancelled or not enough permissions were granted.";
	var POPUP_ERROR_MESSAGE = "Unable to open popup. Please check your browser settings.";
	var GENERIC_ERROR_MESSAGE = "There was a problem logging in. Please try again.";
		
	function clearMessages() {
		$scope.errorMessage = undefined;
	}
	
	$scope.credentials = {};
	$scope.login = function() {
		authenticationService
		.login($scope.credentials)
		.then(function(data) {			
			if (data) {
				$location.path("/");
				clearMessages();
			} else {
				$scope.errorMessage = GENERIC_ERROR_MESSAGE;
			}
		}, function() {
			$scope.errorMessage = GENERIC_ERROR_MESSAGE;
		});
	};  
	
	function handleLoginSuccess(d) {
		console.log('Success', d)
		$location.path("/");
		clearMessages();					
	}
	
	function handleLoginError(err) {
		if(err.status == 'DENIED') {
			$scope.errorMessage = DENIED_MESSAGE;
		} else if(err.status == 'POPUP_NOT_OPEN') {
			$scope.errorMessage = POPUP_ERROR_MESSAGE;
		} else {
			$scope.errorMessage = GENERIC_ERROR_MESSAGE;
			console.error("Error logging in:", err);
		}
	}
	
	$scope.loginWithTwitter = function() {
		authenticationService
		.loginWithTwitter()
		.then(handleLoginSuccess, handleLoginError);
	}
	
	$scope.loginWithGoogle = function() {
		authenticationService
		.loginWithGoogle()
		.then(handleLoginSuccess, handleLoginError);
	}
	
	$scope.loginWithGithub = function() {
		authenticationService
		.loginWithGithub()
		.then(handleLoginSuccess, handleLoginError);
	}
	
	$scope.loginWithLinkedin = function() {
		authenticationService
		.loginWithLinkedin()
		.then(handleLoginSuccess, handleLoginError);
	}
})