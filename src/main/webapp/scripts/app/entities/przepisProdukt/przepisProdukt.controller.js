'use strict';

angular.module('kucharzApp')
    .controller('PrzepisProduktController', function ($scope, PrzepisProdukt) {
        $scope.przepisProdukts = [];
        $scope.loadAll = function() {
            PrzepisProdukt.query(function(result) {
               $scope.przepisProdukts = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PrzepisProdukt.get({id: id}, function(result) {
                $scope.przepisProdukt = result;
                $('#deletePrzepisProduktConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PrzepisProdukt.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePrzepisProduktConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.przepisProdukt = {
                quantity: null,
                id: null
            };
        };
    });
