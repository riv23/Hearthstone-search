app.filter('filteredCost', function() {
    return function(input, value) {
        if(value === "") {
            return input;
        }

        return _.filter(input, function(card){
            var costValue = parseInt(card.cost, 10);
            if(costValue < 7) {
                return costValue === value;
            } else if (costValue >= 7) {
                return true;
            } else {
                return false;
            }
        });

    }
});
