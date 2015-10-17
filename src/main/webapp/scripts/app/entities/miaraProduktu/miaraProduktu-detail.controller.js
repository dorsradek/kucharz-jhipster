'use strict';

angular.module('kucharzApp')
    .controller('MiaraProduktuDetailController', function ($scope, $rootScope, $stateParams, entity, MiaraProduktu, Produkt) {
        $scope.miaraProduktu = entity;
        $scope.load = function (id) {
            MiaraProduktu.get({id: id}, function (result) {
                $scope.miaraProduktu = result;
            });
        };
        $rootScope.$on('kucharzApp:miaraProduktuUpdate', function (event, result) {
            $scope.miaraProduktu = result;
        });
    });
