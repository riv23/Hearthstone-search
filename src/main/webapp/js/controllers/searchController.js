app.controller('SearchCtrl', function ($scope, $http) {

    $scope.submit = function () {
        $http.get("/api/search?q=" + $scope.query)
            .success(function (data) {
                $scope.cards = data;
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    }
});