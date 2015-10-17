'use strict';

angular.module('kucharzApp').controller('KategoriaPrzepisuDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'KategoriaPrzepisu', 'Przepis',
        function ($scope, $stateParams, $modalInstance, entity, KategoriaPrzepisu, Przepis) {

            $scope.kategoriaPrzepisu = entity;
            $scope.przepiss = Przepis.query();
            $scope.load = function (id) {
                KategoriaPrzepisu.get({id: id}, function (result) {
                    $scope.kategoriaPrzepisu = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('kucharzApp:kategoriaPrzepisuUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.kategoriaPrzepisu.id != null) {
                    KategoriaPrzepisu.update($scope.kategoriaPrzepisu, onSaveFinished);
                } else {
                    KategoriaPrzepisu.save($scope.kategoriaPrzepisu, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
