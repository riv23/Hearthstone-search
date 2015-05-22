app.service("FlagSrvc", function ($translate, $location) {
    return {
        initLanguage: function () {
            if (_.isEmpty($location.search().lang)) {
                var language = "en";
                if (window.navigator.userAgent.indexOf("MSIE") > 0) {
                    language = clientInformation.userLanguage.split("-")[0];
                }else {
                    language = navigator.language.split("-")[0];
                }
                return language;
            } else {
                return $location.search().lang.split("-")[0];
            }
        }
    };

});