app.filter('filteredCards', function () {

    var accent = [
            /[\300-\306]/g, /[\340-\346]/g, // A, a
            /[\310-\313]/g, /[\350-\353]/g, // E, e
            /[\314-\317]/g, /[\354-\357]/g, // I, i
            /[\322-\330]/g, /[\362-\370]/g, // O, o
            /[\331-\334]/g, /[\371-\374]/g, // U, u
            /[\321]/g, /[\361]/g, // N, n
            /[\307]/g, /[\347]/g, // C, c
        ],
        noaccent = ['A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'N', 'n', 'C', 'c'];

    function removeAccent(string) {
        for (var i = 0; i < accent.length; i++) {
            if (accent[i] && noaccent[i] && (typeof string === "string")) {
                string = string.replace(accent[i], noaccent[i]);
            }
        }
        if (typeof string === "string") {
            return string.toLowerCase();
        } else {
            return string;
        }
    }

    return function (input, value) {
        if (_.isEmpty(value.query)) {
            return [];
        }

        try {
            var re = new RegExp(removeAccent(value.query), "i");
        } catch (err) {
            return input;
        }
        var test = function (item) {
            return re.exec(removeAccent(item));
        };

        input = _.filter(input, function (card) {
            var costValue = parseInt(card.cost, 10);
            if (value.cost < 7) {
                return costValue === value.cost;
            } else if (value.cost >= 7) {
                return costValue >= 7;
            } else {
                return true;
            }
        });

        input = input.filter(function (item) {
            for (var p in item) if (item.hasOwnProperty(p)) {
                if (typeof item[p] === "string") {
                    if (test(item[p])) return true;
                }
            }
            return false;
        });

        return input;

    }
});
