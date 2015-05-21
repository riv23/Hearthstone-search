app.filter('filteredCost', function() {
    return function(input, value) {

        if(value === "") {
            return input;
        }

        return _.filter(input, function(card){
            if(parseInt(card.cost, 10) < 7) {
                return parseInt(card.cost, 10) == value && card.collectible === "true";
            }
            return parseInt(card.cost, 10) >= 7 && card.collectible === "true";
        });

    }
});