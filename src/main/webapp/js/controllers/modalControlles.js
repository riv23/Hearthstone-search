app.controller('ModalDemoCtrl', function ($scope, $modal, $log) {

    $scope.open = function () {

        var modalInstance = $modal.open({
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
app.controller('ModalInstanceCtrl', function ($scope, $modalInstance, $http, $log) {

    $scope.ok = function () {
        sendMessage();
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    var sendMessage = function () {
        $http.post('/api/message', {name:$scope.name, email:$scope.email, message:$scope.message}).
            success(function(data, status, headers, config) {
                $modalInstance.close();
            }).
            error(function(data, status, headers, config) {
                $log.info('Message dismissed at: ' + new Date());
            });
    }
});