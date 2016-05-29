app.controller('ModalDemoCtrl', function ($scope, $uibModal, $log) {

    $scope.open = function () {

        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'templates/feedModal.html',
            controller: 'ModalInstanceCtrl'
        });

        //Passing param to instance
        modalInstance.result.then(function () {
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

});

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.
app.controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, $http, $log) {

    $scope.ok = function () {
        sendMessage();
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    var sendMessage = function () {
        $http.post('/api/message', {
            name: $scope.name,
            email: $scope.email,
            message: $scope.message
        }).success(function (data, status, headers, config) {
            $uibModalInstance.close();
        }).error(function (data, status, headers, config) {
            $log.info('Message dismissed at: ' + new Date());
        });
    }
});