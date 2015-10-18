'use strict';

angular.module('kucharzApp').controller('PrzepisDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Przepis', 'KategoriaPrzepisu', 'PracochlonnoscPrzepisu', 'PrzepisProdukt',
        function ($scope, $stateParams, $modalInstance, entity, Przepis, KategoriaPrzepisu, PracochlonnoscPrzepisu, PrzepisProdukt) {

            $scope.przepis = entity;
            $scope.kategoriaprzepisus = KategoriaPrzepisu.query();
            $scope.pracochlonnoscprzepisus = PracochlonnoscPrzepisu.query();
            $scope.przepisprodukts = PrzepisProdukt.query();
            $scope.load = function (id) {
                Przepis.get({id: id}, function (result) {
                    $scope.przepis = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('kucharzApp:przepisUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.przepis.id != null) {
                    Przepis.update($scope.przepis, onSaveFinished);
                } else {
                    Przepis.save($scope.przepis, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
