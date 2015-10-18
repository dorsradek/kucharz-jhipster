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
                description: null,
                id: null
            };
        };
    });
