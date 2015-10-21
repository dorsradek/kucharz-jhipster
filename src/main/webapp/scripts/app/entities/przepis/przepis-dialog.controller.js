'use strict';

angular.module('kucharzApp').controller('PrzepisDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Przepis', 'PrzepisImage', 'KategoriaPrzepisu', 'PracochlonnoscPrzepisu', 'PrzepisProdukt',
        function ($scope, $stateParams, $modalInstance, entity, Przepis, PrzepisImage, KategoriaPrzepisu, PracochlonnoscPrzepisu, PrzepisProdukt) {

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
                $scope.uploadFile(result.id);
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

            $scope.uploadFile = function (przepisId) {
                var file = document.getElementById('file').files[0];
                var formData = new FormData();
                formData.append('file', file);
                formData.append('przepisId', JSON.stringify(przepisId));
                PrzepisImage.save(formData);
            };
        }]);
