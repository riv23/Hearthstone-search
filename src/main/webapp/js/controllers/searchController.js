app.controller('SearchCtrl', function ($scope, $http, $location, NamesSrvc, SearchSrvc) {

    var initLanguage = function () {
        if (_.isEmpty($location.search().lang)) {
            return navigator.language.split("-")[0];
        } else {
            return $location.search().lang.split("-")[0];
        }
    };

    $scope.query = $location.search().q;
    $scope.lang = initLanguage();

    $scope.tapedQuery = function (typed) {
        NamesSrvc.fetch(typed, $scope.lang)
            .success(function (data) {
                $scope.suggestions = _.map(data, _.iteratee('name'));
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    };

    $scope.filterMana= function(value) {

        SearchSrvc.fetch($scope.query, $scope.lang)
            .success(function (data) {
                $scope.cards = _.filter(data, function(card){
                    if(value != 10) {
                        return card.cost == value;
                    } else {
                        return card.cost >= 10;
                    }
                });
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
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

    $scope.$watch(function () { return $scope.query; }, function () {
        SearchSrvc.fetch($scope.query, $scope.lang)
            .success(function (data) {
                $scope.cards = data;
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    });

});