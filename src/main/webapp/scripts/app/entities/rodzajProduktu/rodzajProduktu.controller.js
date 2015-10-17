'use strict';

angular.module('kucharzApp')
    .controller('RodzajProduktuController', function ($scope, RodzajProduktu) {
        $scope.rodzajProduktus = [];
        $scope.loadAll = function () {
            RodzajProduktu.query(function (result) {
                $scope.rodzajProduktus = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            RodzajProduktu.get({id: id}, function (result) {
                $scope.rodzajProduktu = result;
                $('#deleteRodzajProduktuConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            RodzajProduktu.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRodzajProduktuConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.rodzajProduktu = {
                name: null,
                id: null
            };
        };
    });
