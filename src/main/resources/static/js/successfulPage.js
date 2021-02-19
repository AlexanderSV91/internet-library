var app = angular.module("successfulPageApp", []);
app.controller("successfulPageCtrl", function ($scope, $http, $location) {

    $scope.imageUrl = "";

    $scope.activateUser = function () {
        let url = $location.absUrl();
        let token = url.replace('confirm', 'api-public/confirm');
        $http({
            url: token,
            method: 'GET'
        }).then(function successCallback(response) {
            let status = response.data;
            if (status === 'VERIFIED') {
                $scope.imageUrl = "https://img.pngio.com/green-registration-successful-logo-material-picture-logo-clipart-success-png-images-free-download-500_500.png";
            } else if (status === 'EXPIRED') {
                $scope.imageUrl = "https://image.shutterstock.com/image-vector/error-message-red-vector-icon-260nw-1184478349.jpg";
            }
        }, function errorCallback(response) {
        });
    }
    $scope.activateUser();
});
