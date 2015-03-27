app.service("NamesSrvc", function ($http, $location,  AccentSrvc) {
    var API_URI = '/api/names';

    return {

        fetch: function (query) {
            var lang = $location.search("lang");
            if(_.isEmpty(lang)){
                lang = navigator.language;
            }
            return $http.get(API_URI + "?q=" + AccentSrvc.withoutAccents(query) + "&lang=" + lang);
        }

    };

});