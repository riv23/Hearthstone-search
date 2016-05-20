app.directive('rarity', function () {
    return {
        restrict: 'E',
        transclude: 'true',
        link: function (scope, element, attrs) {
            var value = attrs.value;
            var color = "img/" + value.toLowerCase() + ".png";
            element.replaceWith("<img class='img-responsive centerfy ' src='" + color + "' />");
        }
    };
});