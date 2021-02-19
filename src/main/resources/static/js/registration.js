var app = angular.module("registrationApp", []);
app.controller("registrationCtrl", function ($scope, $http) {
    $scope.errors = []
    $scope.editFields = {
        userName: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        email: "",
        age: ""
    };

    $scope.saveUser = function () {
        console.log($scope.editFields);
        if ($scope.editFields.userName !== 'username exists' &&
            $scope.editFields.email !== 'Please provide an email' &&
            $scope.editFields.lastName !== 'Please provide your last name' &&
            $scope.editFields.firstName !== 'Please provide your first name' &&
            $scope.editFields.password === $scope.editFields.confirmPassword) {

            let request = 'http://localhost:8080/api-public/registration';
            let method = 'POST';
            let data = {
                userName: $scope.editFields.userName,
                password: $scope.editFields.password,
                firstName: $scope.editFields.firstName,
                lastName: $scope.editFields.lastName,
                email: $scope.editFields.email,
                age: $scope.editFields.age
            };
            $http({
                method: method,
                url: request,
                data: data
            }).then(function successCallback(response) {
                document.location.href = '/';
            }, function errorCallback(response) {
                console.log('post', response.data);
                if (response.data.message.userName != null) {
                    console.log(response.data.message.userName)
                    $scope.editFields.userName = response.data.message.userName;
                }
                if (response.data.message.firstName != null) {
                    $scope.editFields.firstName = response.data.message.firstName;
                }
                if (response.data.message.lastName != null) {
                    $scope.editFields.lastName = response.data.message.lastName;
                }
                if (response.data.message.email != null) {
                    $scope.editFields.email = response.data.message.email;
                }
            });
        } else {
            alert("fields are incorrectly filled");
        }
    }
});
