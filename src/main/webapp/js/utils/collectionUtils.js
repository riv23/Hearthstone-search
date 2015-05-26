function Collections() {}

Collections.prototype.partition = function (items, size) {
    var result = _.groupBy(items, function(item, i) {
        return Math.floor(i/size);
    });
    return _.values(result);
};