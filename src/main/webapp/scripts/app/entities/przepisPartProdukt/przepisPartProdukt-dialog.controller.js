'use strict';

angular.module('kucharzApp').controller('PrzepisPartProduktDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PrzepisPartProdukt', 'Produkt', 'Przepis',
        function ($scope, $stateParams, $modalInstance, entity, PrzepisPartProdukt, Produkt, Przepis) {

            $scope.przepisPartProdukt = entity;
            $scope.produkts = Produkt.query();
            $scope.przepiss = Przepis.query();
            $scope.load = function (id) {
                PrzepisPartProdukt.get({id: id}, function (result) {
                    $scope.przepisPartProdukt = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('kucharzApp:przepisPartProduktUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.przepisPartProdukt.id != null) {
                    PrzepisPartProdukt.update($scope.przepisPartProdukt, onSaveFinished);
                } else {
                    PrzepisPartProdukt.save($scope.przepisPartProdukt, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
