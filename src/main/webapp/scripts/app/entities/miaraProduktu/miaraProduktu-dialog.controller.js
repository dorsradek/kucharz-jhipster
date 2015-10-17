'use strict';

angular.module('kucharzApp').controller('MiaraProduktuDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MiaraProduktu', 'Produkt',
        function ($scope, $stateParams, $modalInstance, entity, MiaraProduktu, Produkt) {

            $scope.miaraProduktu = entity;
            $scope.produkts = Produkt.query();
            $scope.load = function (id) {
                MiaraProduktu.get({id: id}, function (result) {
                    $scope.miaraProduktu = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('kucharzApp:miaraProduktuUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.miaraProduktu.id != null) {
                    MiaraProduktu.update($scope.miaraProduktu, onSaveFinished);
                } else {
                    MiaraProduktu.save($scope.miaraProduktu, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
