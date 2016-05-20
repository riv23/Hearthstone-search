app.service("SearchSrvc", function ($http) {

    var defaultLanguage = function () {
        var language = "en";
        if (window.navigator.userAgent.indexOf("MSIE") > 0) {
            language = clientInformation.userLanguage.split("-")[0];
        }else {
            language = navigator.language.split("-")[0];
        }
        switch (language) {
            case "fr" :
                return "fr";
            case "en" :
                return "en";
            default :
                return "en"
        }
    };

    var API_URI_ROOT = 'https://api.hearthstonejson.com/v1/latest/';
    var LANG= "enUS";

    if(defaultLanguage() == "fr") {
        LANG = "frFR";
    }

    var url = API_URI_ROOT + LANG + "/cards.collectible.json";
    return {
        fetch: function () {
            return $http.get(url);
        }
    };

});
