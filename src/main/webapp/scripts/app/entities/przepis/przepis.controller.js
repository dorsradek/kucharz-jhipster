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

        $scope.divide = function (duration) {
            var hours = Math.floor(duration / 60);
            var minutes = duration - (hours * 60);
            var result = hours + ':';
            if (minutes == 0) {
                result += '00';
            } else if (minutes < 10) {
                result += '0' + minutes;
            } else {
                result += minutes;
            }
            return result;
        };
    });
