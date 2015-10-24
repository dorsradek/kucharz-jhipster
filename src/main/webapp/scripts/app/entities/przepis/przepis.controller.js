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

        $scope.getClass = function (idx) {
            //if(idx < 2) {
            //    return "col-xs-12 col-sm-6 col-lg-6";
            //} else {
            return "col-xs-12 col-sm-6 col-lg-4";
            //}

        };
    });
