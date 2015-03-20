app.controller('SearchCtrl', function ($scope, $http) {

    $scope.submit = function () {
        doSearch($scope.query);
    };

    $scope.tapedQuery = function(typed){
        $http.get("/api/names?q=" + typed)
            .success(function(data) {
                $scope.suggestions = _.map(data, _.iteratee('name'));
            });
    };
    $scope.$watch(function () { return $scope.query; }, function () {
       doSearch($scope.query);
    });

    var doSearch = function(query) {
        $http.get("/api/search?q=" + query)
            .success(function (data) {
                $scope.cards = data;
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    }

});