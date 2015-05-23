app.filter('filteredCost', function() {
    return function(input, value) {
        if(value === "") {
            return input;
        }

        return _.filter(input, function(card){
            var costValue = parseInt(card.cost, 10);
            if(value < 7) {
                return costValue === value;
            } else if (value >= 7) {
                return costValue >= 7;
            } else {
                return false;
            }
        });

    }
});
