app.controller('SearchCtrl', function ($scope, $http, $location, $translate, SearchSrvc, ngProgress) {

    ngProgress.color('#ffd100');
    ngProgress.height('5px');
    $scope.filter = {};

    var search = function() {
        ngProgress.start();
        SearchSrvc.fetch()
            .success(function (data) {
                _.each(data, function(item) {
                    item.image = "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/"+item.id+".png";
                });
                $scope.cards = _.sortBy(data, 'cost');
                ngProgress.complete();
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
                ngProgress.complete();
            });
    };

    $scope.filterMana= function(value) {
        $scope.filter.cost = value;
    };

    search();

});
