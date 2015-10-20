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

            $scope.setFileEventListener = function (element) {
                $scope.uploadedFile = element.files[0];

                if ($scope.uploadedFile) {
                    $scope.$apply(function () {
                        $scope.upload_button_state = true;
                    });
                }
            };

            $scope.uploadFile = function () {
                uploadFile();
            };


            function uploadFile() {
                if (!$scope.uploadedFile) {
                    return;
                }

                var fd = new FormData();
                fd.append('file', $scope.uploadedFile);
                fd.append('przepisId', JSON.stringify(1));

                console.log('TEST');

                PrzepisImage.save(fd);
            }
        }]);
