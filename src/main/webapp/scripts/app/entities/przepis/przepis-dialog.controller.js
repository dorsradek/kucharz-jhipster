'use strict';

angular.module('kucharzApp').controller('PrzepisDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Przepis', 'KategoriaPrzepisu', 'PracochlonnoscPrzepisu', 'PrzepisPartProdukt',
        function ($scope, $stateParams, $modalInstance, entity, Przepis, KategoriaPrzepisu, PracochlonnoscPrzepisu, PrzepisPartProdukt) {

            $scope.przepis = entity;
            $scope.kategoriaprzepisus = KategoriaPrzepisu.query();
            $scope.pracochlonnoscprzepisus = PracochlonnoscPrzepisu.query();
            $scope.przepisprodukts = PrzepisPartProdukt.query();
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
                var file = document.getElementById('file').files[0];
                var fileReader = new FileReader();
                fileReader.onload = function (fileLoadedEvent) {
                    var base64String = fileLoadedEvent.target.result;
                    $scope.przepis.image = base64String;
                    if ($scope.przepis.id != null) {
                        Przepis.update($scope.przepis, onSaveFinished);
                    } else {
                        Przepis.save($scope.przepis, onSaveFinished);
                    }
                };
                fileReader.readAsDataURL(file);
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };

        }]);
