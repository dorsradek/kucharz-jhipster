'use strict';

angular.module('kucharzApp')
    .controller('PrzepisController', function ($scope, Przepis) {
        $scope.przepiss = [];
        $scope.loadAll = function () {
            Przepis.query(function (result) {
                $scope.przepiss = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Przepis.get({id: id}, function (result) {
                $scope.przepis = result;
                $('#deletePrzepisConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Przepis.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePrzepisConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.przepis = {
                duration: null,
                creationDate: null,
                modificationDate: null,
                name: null,
                id: null
            };
        };
    });
