app.service("SearchSrvc", function ($http, AccentSrvc) {
    var API_URI = '/api/search';

    return {

        fetch: function (query) {
            return $http.get(API_URI + "?q=" + AccentSrvc.withoutAccents(query) + "&lang=" + navigator.language);
        }

    };

});
