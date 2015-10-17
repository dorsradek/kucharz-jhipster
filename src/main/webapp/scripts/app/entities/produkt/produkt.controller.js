'use strict';

angular.module('kucharzApp')
    .controller('ProduktController', function ($scope, Produkt) {
        $scope.produkts = [];
        $scope.loadAll = function () {
            Produkt.query(function (result) {
                $scope.produkts = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Produkt.get({id: id}, function (result) {
                $scope.produkt = result;
                $('#deleteProduktConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Produkt.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProduktConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.produkt = {
                name: null,
                id: null
            };
        };
    });
