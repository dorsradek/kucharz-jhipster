'use strict';

angular.module('kucharzApp')
    .controller('MiaraProduktuController', function ($scope, MiaraProduktu) {
        $scope.miaraProduktus = [];
        $scope.loadAll = function () {
            MiaraProduktu.query(function (result) {
                $scope.miaraProduktus = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MiaraProduktu.get({id: id}, function (result) {
                $scope.miaraProduktu = result;
                $('#deleteMiaraProduktuConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MiaraProduktu.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMiaraProduktuConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.miaraProduktu = {
                name: null,
                shortcut: null,
                id: null
            };
        };
    });
