var app = angular.module("orderBookApp", []);
app.controller("orderBookCtrl", function ($scope, $http) {

    $scope.titleModal = "";
    $scope.orderBooks = [];
    $scope.orderBookStatuses = [];
    $scope.users = [];
    $scope.userStr = "";
    $scope.books = [];
    $scope.bookStr = "";
    $scope.editFields = {
        id: 0,
        status: "",
        userId: "",
        userUsername: "",
        userFirstName: "",
        userLastName: "",
        userAge: "",
        bookId: "",
        bookName: "",
        bookCondition: "",
        bookDescription: "",
        note: "",
        startDate: "",
        endDate: ""
    };
    $scope.pageable = {
        pageNumber: 0,
        pageSize: 0,
        totalPages: 0,
        totalElements: 0,
        last: false,
        first: true,
    };

    $scope.changeTitleModal = function (titleName) {
        $scope.titleModal = titleName;
        if (titleName === "Add Order Book") {
            $scope.editFields.id = 0;
            $scope.editFields.status = "";
            $scope.editFields.userId = 0;
            $scope.editFields.userUsername = "";
            $scope.editFields.userFirstName = "";
            $scope.editFields.userLastName = "";
            $scope.editFields.userAge = 0;
            $scope.editFields.bookId = 0;
            $scope.editFields.bookName = "";
            $scope.editFields.bookCondition = "";
            $scope.editFields.bookDescription = "";
            $scope.editFields.note = "";
            $scope.editFields.startDate = "";
            $scope.editFields.endDate = "";
            $scope.bookStr = "";
            $scope.userStr = "";
        }
    }

    $scope.fillFieldsModalThanUser = function () {
        let string = $scope.userStr.split(' ');

        $scope.editFields.userId = Number.parseInt(string[0]);
        $scope.editFields.userUsername = string[1];
        $scope.editFields.userFirstName = string[2];
        $scope.editFields.userLastName = string[3];
        $scope.editFields.userAge = Number.parseInt(string[4]);
    }

    $scope.fillFieldsModalThanBook = function () {
        let string = $scope.bookStr.split(" ");
        for (let i = 0; i < $scope.books.length; i++) {
            if (string[0].toString() === $scope.books[i].id.toString()) {
                $scope.editFields.bookId = $scope.books[i].id;
                $scope.editFields.bookName = $scope.books[i].name;
                $scope.editFields.bookCondition = $scope.books[i].bookCondition;
                $scope.editFields.bookDescription = $scope.books[i].description;
                break;
            }
        }
    }

    $scope.fillFieldsModal = function (userBook) {
        $scope.editFields.id = userBook.id;
        $scope.editFields.status = userBook.status;
        $scope.editFields.userId = userBook.user.id;
        $scope.editFields.userUsername = userBook.user.username;
        $scope.editFields.userFirstName = userBook.user.firstName;
        $scope.editFields.userLastName = userBook.user.lastName;
        $scope.editFields.userAge = userBook.user.age;
        $scope.userStr = userBook.user.id + ' ' + userBook.user.username + ' ' + userBook.user.firstName + ' ' + userBook.user.lastName + ' ' + userBook.user.age;
        $scope.editFields.bookId = userBook.book.id;
        $scope.editFields.bookName = userBook.book.name;
        $scope.editFields.bookCondition = userBook.book.bookCondition;
        $scope.editFields.bookDescription = userBook.book.description;
        $scope.bookStr = userBook.book.id + ' ' + userBook.book.name + ' ' + userBook.book.bookCondition + ' ' + userBook.book.description;
        $scope.editFields.note = userBook.note;
        $scope.editFields.startDate = userBook.startDate;
        $scope.editFields.endDate = userBook.endDate;
    }

    $scope.previousPage = function () {
        if ($scope.pageable.pageNumber > 0) {
            $scope.pageable.pageNumber--;
            $scope.getAllOrderBook();
        }
    }

    $scope.nextPage = function () {
        if ($scope.pageable.pageNumber < $scope.pageable.totalPages - 1) {
            $scope.pageable.pageNumber++;
            $scope.getAllOrderBook();
        }
    }

    $scope.getAllOrderBook = function () {
        $http({
            url: 'http://localhost:8080/api/v1/orderbook?page=' + $scope.pageable.pageNumber + '&size=5',
            method: 'GET'
        }).then(function (response) {
            $scope.orderBooks = response.data.content;
            $scope.pageable.pageNumber = response.data.pageable.pageNumber;
            $scope.pageable.pageSize = response.data.pageable.pageSize;
            $scope.pageable.totalPages = response.data.totalPages;
            $scope.pageable.totalElements = response.data.totalElements;
            $scope.pageable.last = response.data.last;
            $scope.pageable.first = response.data.first;
        })
    }
    $scope.getAllOrderBook();

    $scope.getAllOrderBookStatuses = function () {
        $http({
            url: 'http://localhost:8080/api/v1/orderbook/status',
            method: 'GET'
        }).then(function (response) {
            $scope.orderBookStatuses = response.data;
            console.log($scope.orderBookStatuses)
        })
    }
    $scope.getAllOrderBookStatuses();


    $scope.getAllBook = function () {
        $http({
            url: 'http://localhost:8080/api/v1/book',
            method: 'GET'
        }).then(function (response) {
            $scope.books = response.data.content;
        })
    }
    $scope.getAllBook();

    $scope.getAllBookByUserId = function (userId) {
        $http({
            url: 'http://localhost:8080/api/v1/orderbook/user/' + userId,
            method: 'GET'
        }).then(function (response) {
            $scope.orderBooks = response.data;
        })
    }

    $scope.getAllUser = function () {
        $http({
            url: 'http://localhost:8080/api/v1/user',
            method: 'GET'
        }).then(function (response) {
            $scope.users = response.data.content;
            console.log($scope.users);
        })
    }
    $scope.getAllUser();

    $scope.deleteOrderBookByIdRequest = function (bookId) {
        $http({
            url: 'http://localhost:8080/api/v1/orderbook/' + bookId,
            method: 'DELETE'
        }).then(function () {
            $scope.getAllOrderBook();
        })
    }

    $scope.saveOrderBook = function () {
        let data = '';
        let request = '';
        let method = '';
        if ($scope.editFields.id === 0) {
            request = 'http://localhost:8080/api/v1/orderbook/';
            method = 'POST';
            data = {
                status: $scope.editFields.status,
                user: {
                    id: $scope.editFields.userId,
                },
                book: {
                    id: $scope.editFields.bookId,
                },
                note: $scope.editFields.note,
                startDate: $scope.editFields.startDate,
                endDate: $scope.editFields.endDate
            };
        } else {
            request = 'http://localhost:8080/api/v1/orderbook/' + $scope.editFields.id;
            method = 'PUT';
            data = {
                id: $scope.editFields.id,
                status: $scope.editFields.status,
                user: {
                    id: $scope.editFields.userId,
                },
                book: {
                    id: $scope.editFields.bookId,
                },
                note: $scope.editFields.note,
                startDate: $scope.editFields.startDate,
                endDate: $scope.editFields.endDate
            };
        }
        $http({
            method: method,
            url: request,
            data: data
        }).then(function successCallback(response) {
            $scope.getAllOrderBook();
        }, function errorCallback(response) {
            console.log('post', response.data);
            alert("Error!");
        });
    }
});