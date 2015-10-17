'use strict';

angular.module('kucharzApp').controller('PrzepisProduktDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PrzepisProdukt', 'Produkt', 'Przepis',
        function ($scope, $stateParams, $modalInstance, entity, PrzepisProdukt, Produkt, Przepis) {

            $scope.przepisProdukt = entity;
            $scope.produkts = Produkt.query();
            $scope.przepiss = Przepis.query();
            $scope.load = function (id) {
                PrzepisProdukt.get({id: id}, function (result) {
                    $scope.przepisProdukt = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('kucharzApp:przepisProduktUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.przepisProdukt.id != null) {
                    PrzepisProdukt.update($scope.przepisProdukt, onSaveFinished);
                } else {
                    PrzepisProdukt.save($scope.przepisProdukt, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
