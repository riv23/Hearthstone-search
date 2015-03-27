app.controller('SearchCtrl', function ($scope, $http, NamesSrvc, SearchSrvc) {

    $scope.tapedQuery = function (typed) {
        NamesSrvc.fetch(typed)
            .success(function (data) {
                $scope.suggestions = _.map(data, _.iteratee('name'));
            });
    };

    $scope.submit = function () {
        SearchSrvc.fetch($scope.query)
            .success(function (data) {
                $scope.cards = data;
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    };

    $scope.$watch(function () {return $scope.query;}, function () {
        SearchSrvc.fetch($scope.query)
            .success(function (data) {
                $scope.cards = data;
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    });

});