app.controller('SearchCtrl', function ($scope, $http, $location, $translate, NamesSrvc, SearchSrvc, FlagSrvc, ngProgress) {

    var initFlag = function (language) {
        if(language == "fr") {
            $scope.frenchClass = "usedLanguage";
            $scope.englishClass = "unusedLanguage";
        }

        if (language == "en") {
            $scope.frenchClass = "unusedLanguage";
            $scope.englishClass = "usedLanguage";
        }

        $scope.lang = language;
        $translate.use(language);
    };

    var search = function() {
        SearchSrvc.fetch($scope.query, $scope.lang)
            .success(function (data) {
                $scope.cards = data;
                ngProgress.complete();
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    };

    ngProgress.color('#ffd100');
    ngProgress.height('5px');
    ngProgress.start();

    $scope.query = $location.search().q;
    $scope.lang = FlagSrvc.initLanguage();
    $scope.costFilter = "";
    initFlag($scope.lang);

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
        $scope.costFilter = value;
    };

    $scope.submit = function () {
        search();
    };

    $scope.$watch(function () { return $scope.query; }, function () {
        search();
    });

    $scope.changeLanguage = function(language) {
        initFlag(language);
    };

});