app.controller('SearchCtrl', function ($scope, $http, $location, $translate, $filter, SearchSrvc, ngProgressFactory) {

    $scope.progressbar = ngProgressFactory.createInstance();

    $scope.progressbar.setColor('#ffd100');
    $scope.progressbar.setHeight('5px');
    $scope.images = [];
    $scope.filter = {};
    $scope.filteredCards = [];

    var search = function() {
        $scope.progressbar.start();
        SearchSrvc.fetch()
            .success(function (data) {
                _.each(data, function(item) {
                    item.image = "https://wow.zamimg.com/images/hearthstone/cards/frfr/original/"+item.id+".png";
                    if(!item.playerClass) {
                        item.playerClass = "ALL";
                    }
                });
                $scope.cards = _.sortBy(_.reject(data, function(item) {
                    return item.type === "HERO";
                }), 'cost');
                $scope.progressbar.complete();
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
                $scope.progressbar.complete();
            });
    };

    $scope.submit = function() {
        $scope.filteredCards = $filter('filteredCards')($scope.cards, $scope.filter)
    };

    $scope.filterMana = function(value) {
        $scope.filter.cost = value;
        $scope.filteredCards = $filter('filteredCards')($scope.cards, $scope.filter)
    };

    search();

});
