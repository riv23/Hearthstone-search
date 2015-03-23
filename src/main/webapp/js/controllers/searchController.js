app.controller('SearchCtrl', function ($scope, $http) {

    $scope.submit = function () {
        doSearch($scope.query);
    };

    $scope.tapedQuery = function(typed){
        $http.get("/api/names?q=" + withoutAccents(typed) + "&lang=" + getLanguage())
            .success(function(data) {
                $scope.suggestions = _.map(data, _.iteratee('name'));
            });
    };
    $scope.$watch(function () { return $scope.query; }, function () {
       doSearch($scope.query);
    });

    var doSearch = function(query) {
        $http.get("/api/search?q=" + query + "&lang=" + getLanguage())
            .success(function (data) {
                $scope.cards = data;
            })
            .error(function (error) {
                console.log("Error while accessing to API " + error);
            });
    };

    var withoutAccents = function(str){

        if(_.isEmpty(str)) {
            return "";
        }

        var accent = [
            /[\300-\306]/g, /[\340-\346]/g, // A, a
            /[\310-\313]/g, /[\350-\353]/g, // E, e
            /[\314-\317]/g, /[\354-\357]/g, // I, i
            /[\322-\330]/g, /[\362-\370]/g, // O, o
            /[\331-\334]/g, /[\371-\374]/g, // U, u
            /[\321]/g, /[\361]/g, // N, n
            /[\307]/g, /[\347]/g, // C, c
        ];
        var noaccent = ['A','a','E','e','I','i','O','o','U','u','N','n','C','c'];

        for(var i = 0; i < accent.length; i++){
            str = str.replace(accent[i], noaccent[i]);
        }

        return str.toLowerCase();
    };

    var getLanguage = function() {
        var language = navigator.language;
        if(language !== "fr") {
            return "";
        }
        return language;
    }

});