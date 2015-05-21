app.service("FlagSrvc", function ($translate, $location) {
    return {
        initLanguage: function () {
            if (_.isEmpty($location.search().lang)) {
                return navigator.language.split("-")[0];
            } else {
                return $location.search().lang.split("-")[0];
            }
        }
    };

});