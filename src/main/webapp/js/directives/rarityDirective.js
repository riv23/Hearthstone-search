app.directive('rarity', function () {
    var LEGENDARY = "img/legendary.png";
    var EPIC = "img/epic.png";
    var RARE = "img/rare.png";
    var COMMON = "img/common.png";

    //TODO refacto this in order to include id for rarity instead of a string value
    return {
        restrict: 'E',
        transclude: 'true',
        link: function (scope, element, attrs) {
            var value = attrs.value;
            var color = "";
            if (value) {
                    if(_.isEqual(value, "Legendary") || _.isEqual(value, "Légendaire")) {
                        color = LEGENDARY;
                    } else if(_.isEqual(value, "Epic") || _.isEqual(value, "Épique")) {
                        color = EPIC;
                    } else if(_.isEqual(value, "Rare")) {
                        color = RARE;
                    } else {
                        color = COMMON;
                    }
            }
            element.replaceWith("<img class='img-responsive centerfy ' src='"+color+"' />");
        }
    };
});