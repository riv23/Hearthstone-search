app.service("NamesSrvc", function ($http) {
    var API_URI = '/api/names';

    var withoutAccents = function (str) {

        if (_.isEmpty(str)) {
            return "";
        }

        var accent = [
            /[\300-\306]/g, /[\340-\346]/g, // A, a
            /[\310-\313]/g, /[\350-\353]/g, // E, e
            /[\314-\317]/g, /[\354-\357]/g, // I, i
            /[\322-\330]/g, /[\362-\370]/g, // O, o
            /[\331-\334]/g, /[\371-\374]/g, // U, u
            /[\321]/g, /[\361]/g, // N, n
            /[\307]/g, /[\347]/g // C, c
        ];
        var noaccent = ['A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'N', 'n', 'C', 'c'];

        for (var i = 0; i < accent.length; i++) {
            str = str.replace(accent[i], noaccent[i]);
        }

        var toLowerCase = str.toLowerCase();
        if (_.isEmpty(toLowerCase)) { //check undefined value
            return "";
        }
        return toLowerCase;
    };

    return {

        fetch: function (query) {
            return $http.get(API_URI + "?q=" + withoutAccents(query) + "&lang=" + navigator.language);
        }

    };

});