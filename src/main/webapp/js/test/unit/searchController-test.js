'use strict';

function nothing(args) {
}

describe('SearchCtrl', function () {
    beforeEach(module('hssc'));

    var scope;
    var controller;
    var searchCtrl;
    var location;
    var http;
    var translate;
    var filteredCardsFilter;
    var SearchSrvc;

    beforeEach(inject(function ($rootScope, $controller) {
        controller = $controller;
        scope = {};

        var ngProgressFactory = {
            createInstance: function () {
                return {
                    setColor: nothing,
                    setHeight: nothing,
                    start: nothing,
                    complete: nothing
                }
            }

        };


        searchCtrl = $controller('SearchCtrl', {
            $scope: scope,
            $http: http,
            $translate: translate,
            $location: location,
            filteredCardsFilter: filteredCardsFilter,
            SearchSrvc: SearchSrvc,
            ngProgressFactory: ngProgressFactory
        });
    }));

    it('should set save function', function () {
        console.log("it");
        expect(scope).toBeDefined();
    });
});