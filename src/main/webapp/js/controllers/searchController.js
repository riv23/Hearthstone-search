app.controller('SearchCtrl', function ($scope, $http) {

    $scope.submit = function () {
        $http.get("http://hearthstone-search-cards.appspot.com/api/search?q=" + $scope.query)
            .success(function (data) {
                $scope.cards = data;
            });
    }
});