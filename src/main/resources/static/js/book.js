var app = angular.module("bookApp", []);
app.controller("bookCtrl", function ($scope, $http) {

    $scope.titleModal = "";
    $scope.books = [];
    $scope.pageable = {
        pageNumber: 0,
        pageSize: 0,
        totalPages: 0,
        totalElements: 0,
        last: false,
        first: true,
    };
    $scope.editFields = {
        id: 0,
        name: "",
        bookCondition: "",
        description: ""
    };

    $scope.changeTitleModal = function (titleName) {
        $scope.titleModal = titleName;
        if (titleName === "Add Book") {
            $scope.editFields.id = 0;
            $scope.editFields.name = "";
            $scope.editFields.bookCondition = "";
            $scope.editFields.description = "";
        }
    }

    $scope.fillFieldsModal = function (book) {
        $scope.editFields.id = book.id;
        $scope.editFields.name = book.name;
        $scope.editFields.bookCondition = book.bookCondition;
        $scope.editFields.description = book.description;
    }

    $scope.previousPage = function () {
        if ($scope.pageable.pageNumber > 0) {
            $scope.pageable.pageNumber--;
            $scope.getAllBook();
        }
    }

    $scope.nextPage = function () {
        if ($scope.pageable.pageNumber < $scope.pageable.totalPages - 1) {
            $scope.pageable.pageNumber++;
            $scope.getAllBook();
        }
    }

    $scope.getAllBook = function () {
        $http({
            url: 'http://localhost:8081/api/v1/book?page=' + $scope.pageable.pageNumber + '&size=5',
            method: 'GET'
        }).then(function (response) {
            $scope.books = response.data.content;
            $scope.pageable.pageNumber = response.data.pageable.pageNumber;
            $scope.pageable.pageSize = response.data.pageable.pageSize;
            $scope.pageable.totalPages = response.data.totalPages;
            $scope.pageable.totalElements = response.data.totalElements;
            $scope.pageable.last = response.data.last;
            $scope.pageable.first = response.data.first;
        })
    }
    $scope.getAllBook();

    $scope.deleteBookByIdRequest = function (bookId) {
        $http({
            url: 'http://localhost:8081/api/v1/book' + bookId,
            method: 'DELETE'
        }).then(function () {
            $scope.getAllBook();
        })
    }

    $scope.saveBook = function () {
        let data = '';
        let request = '';
        let method = '';
        if ($scope.editFields.id === 0) {
            request = 'http://localhost:8081/api/v1/book';
            method = 'POST';
            data = {
                name: $scope.editFields.name,
                bookCondition: $scope.editFields.bookCondition,
                description: $scope.editFields.description
            };
        } else {
            request = 'http://localhost:8081/api/v1/book' + $scope.editFields.id;
            method = 'PUT';
            data = {
                id: $scope.editFields.id,
                name: $scope.editFields.name,
                bookCondition: $scope.editFields.bookCondition,
                description: $scope.editFields.description
            };
        }
        $http({
            method: method,
            url: request,
            data: data
        }).then(function successCallback(response) {
            $scope.getAllBook();
        }, function errorCallback(response) {
            console.log('post', response.data);
            alert("Error!");
        });
    }
});