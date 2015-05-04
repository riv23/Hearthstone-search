app.service("SearchSrvc", function ($http, $location, AccentSrvc) {
    var API_URI = 'http://Hearthstone-search-cards.appspot.com/api/search';

    return {
        fetch: function (query, lang, cost) {

            if (!_.isEmpty(query)) {
                $location.url("?q=" + query + "&lang=" + lang);
            }
            return $http.get(API_URI + "?q=" + AccentSrvc.withoutAccents(query) + "&lang=" + lang + "&cost=" + cost);
        }
    };

});
