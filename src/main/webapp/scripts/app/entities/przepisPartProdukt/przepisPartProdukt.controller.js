'use strict';

angular.module('kucharzApp')
    .controller('PrzepisPartProduktController', function ($scope, PrzepisPartProdukt) {
        $scope.przepisPartProdukts = [];
        $scope.loadAll = function () {
            PrzepisPartProdukt.query(function (result) {
                $scope.przepisPartProdukts = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PrzepisPartProdukt.get({id: id}, function (result) {
                $scope.przepisPartProdukt = result;
                $('#deletePrzepisPartProduktConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PrzepisPartProdukt.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePrzepisPartProduktConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.przepisPartProdukt = {
                quantity: null,
                id: null
            };
        };
    });
