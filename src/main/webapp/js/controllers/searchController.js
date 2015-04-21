app.controller('SearchCtrl', function ($scope, $http, $location, NamesSrvc, SearchSrvc) {

    var initLanguage = function () {
        if (_.isEmpty($location.search().lang)) {
            return navigator.language;
        } else {
            return $location.search().lang;
        }
    };

    $scope.query = $location.search().q;
    $scope.lang = initLanguage();



    $scope.tapedQuery = function (typed) {
        NamesSrvc.fetch(typed, $scope.lang)
            .success(function (data) {
                $scope.suggestions = _.map(data, _.iteratee('name'));
            });
    };

    $scope.submit = function () {
        SearchSrvc.fetch($scope.query, $scope.lang)
            .success(function (data) {
                $scope.cards = data;
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    };

    $scope.$watch(function () {
        return $scope.query;
    }, function () {
        if (_.isEmpty($scope.query)) {
            return;
        } else {
            $location.url("?q=" + $scope.query + "&lang=" + $scope.lang);
        }
        SearchSrvc.fetch($scope.query, $scope.lang)
            .success(function (data) {
                $scope.cards = data;
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    });
});