'use strict';

angular.module('kucharzApp').controller('PrzepisDescriptionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PrzepisDescription', 'Przepis',
        function ($scope, $stateParams, $modalInstance, entity, PrzepisDescription, Przepis) {

            $scope.przepisDescription = entity;
            $scope.przepiss = Przepis.query();
            $scope.load = function (id) {
                PrzepisDescription.get({id: id}, function (result) {
                    $scope.przepisDescription = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('kucharzApp:przepisDescriptionUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.przepisDescription.id != null) {
                    PrzepisDescription.update($scope.przepisDescription, onSaveFinished);
                } else {
                    PrzepisDescription.save($scope.przepisDescription, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
