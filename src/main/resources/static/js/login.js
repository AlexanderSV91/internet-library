var app = angular.module("loginApp", []);
app.controller("loginCtrl", function ($scope, $http) {
    $scope.username = ""
    $scope.password = ""

    $scope.login = function () {
        let request = 'http://localhost:8080/api-public/v1/auth/signin';
        let method = 'POST';
        let data = {
            username: $scope.username,
            password: $scope.password,
        };

        $http({
            method: method,
            url: request,
            data: data
        }).then(function successCallback(response) {
            document.location.href = '/';
        }, function errorCallback(response) {
            console.log('post', response.data);
        });
    }
});
