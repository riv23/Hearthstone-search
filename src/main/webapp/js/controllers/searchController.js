app.controller('SearchCtrl', function ($scope, $http, $location, $translate, NamesSrvc, SearchSrvc, ngProgress) {
    ngProgress.color('#ffd100');
    ngProgress.height('5px');
    ngProgress.start();

    var initLanguage = function () {
        if (_.isEmpty($location.search().lang)) {
            return navigator.language.split("-")[0];
        } else {
            return $location.search().lang.split("-")[0];
        }
    };

    var initFlag = function(language) {
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

    $scope.query = $location.search().q;
    $scope.lang = initLanguage();
    $scope.cost = "";
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
        $scope.cost = value;
        SearchSrvc.fetch($scope.query, $scope.lang, $scope.cost)
            .success(function (data) {
                $scope.cards = data;
                ngProgress.complete();
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });

    };

    $scope.submit = function () {
        SearchSrvc.fetch($scope.query, $scope.lang, $scope.cost)
            .success(function (data) {
                $scope.cards = data;
                ngProgress.complete();
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    };

    $scope.$watch(function () { return $scope.query; }, function () {
        SearchSrvc.fetch($scope.query, $scope.lang, $scope.cost)
            .success(function (data) {
                $scope.cards = data;
                ngProgress.complete();
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    });

    $scope.changeLanguage = function(language) {

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

});