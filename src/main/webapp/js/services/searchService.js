app.service("SearchSrvc", function ($http) {
    var API_URI = '/api/search';

    return {

        fetch: function (query) {
            return $http.get(API_URI + "?q=" + query + "&lang=" + navigator.language);
        }

    };

});
