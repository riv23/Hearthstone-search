app.service("NamesSrvc", function ($http, AccentSrvc) {
    var API_URI = '/api/names';

    return {

        fetch: function (query) {
            return $http.get(API_URI + "?q=" + AccentSrvc.withoutAccents(query) + "&lang=" + navigator.language);
        }

    };

});