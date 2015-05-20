app.filter('filteredCost', function() {
    return function(input, value) {

        if(value === "") {
            return input;
        }

        return _.filter(input, function(card){
            if(card != 7) {
                return card.cost == value && card.collectible === "true";
            }
            return card.cost >= 7 && card.collectible === "true";
        });

    }
});