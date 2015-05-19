app.service("NamesSrvc", function ($http, $location,  AccentSrvc) {
    var API_URI = 'http://hearthstone-search-cards.appspot.com//api/names';

    return {
        fetch: function (query, lang) {
            $location.url("?q=" + query + "&lang=" + lang);
            return $http.get(API_URI + "?q=" + AccentSrvc.withoutAccents(query) + "&lang=" + lang);
        }
    };

});