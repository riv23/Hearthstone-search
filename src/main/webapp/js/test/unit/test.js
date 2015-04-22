// test/unit/foo_controller.spec.js
describe('SearchCtrl', function() {
    var $scope, controller;

    beforeEach(module('hssc'));
    beforeEach(inject(function ($rootScope, $controller) {
        $scope = $rootScope.$new();
        controller = $controller('SearchCtrl', {
            $scope: $scope
        });
    }));

    it('should set save function', function() {
        expect($scope.lang).toBeDefined();
    });
});