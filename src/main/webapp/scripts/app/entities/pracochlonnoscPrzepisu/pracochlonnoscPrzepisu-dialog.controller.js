'use strict';

angular.module('kucharzApp').controller('PracochlonnoscPrzepisuDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PracochlonnoscPrzepisu', 'Przepis',
        function($scope, $stateParams, $modalInstance, entity, PracochlonnoscPrzepisu, Przepis) {

        $scope.pracochlonnoscPrzepisu = entity;
        $scope.przepiss = Przepis.query();
        $scope.load = function(id) {
            PracochlonnoscPrzepisu.get({id : id}, function(result) {
                $scope.pracochlonnoscPrzepisu = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('kucharzApp:pracochlonnoscPrzepisuUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.pracochlonnoscPrzepisu.id != null) {
                PracochlonnoscPrzepisu.update($scope.pracochlonnoscPrzepisu, onSaveFinished);
            } else {
                PracochlonnoscPrzepisu.save($scope.pracochlonnoscPrzepisu, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
