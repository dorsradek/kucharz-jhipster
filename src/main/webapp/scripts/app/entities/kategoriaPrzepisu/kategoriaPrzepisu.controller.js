'use strict';

angular.module('kucharzApp')
    .controller('KategoriaPrzepisuController', function ($scope, KategoriaPrzepisu) {
        $scope.kategoriaPrzepisus = [];
        $scope.loadAll = function() {
            KategoriaPrzepisu.query(function(result) {
               $scope.kategoriaPrzepisus = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            KategoriaPrzepisu.get({id: id}, function(result) {
                $scope.kategoriaPrzepisu = result;
                $('#deleteKategoriaPrzepisuConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            KategoriaPrzepisu.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteKategoriaPrzepisuConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.kategoriaPrzepisu = {
                name: null,
                id: null
            };
        };
    });
