'use strict';

angular.module('kucharzApp')
    .controller('RodzajProduktuDetailController', function ($scope, $rootScope, $stateParams, entity, RodzajProduktu, Produkt) {
        $scope.rodzajProduktu = entity;
        $scope.load = function (id) {
            RodzajProduktu.get({id: id}, function(result) {
                $scope.rodzajProduktu = result;
            });
        };
        $rootScope.$on('kucharzApp:rodzajProduktuUpdate', function(event, result) {
            $scope.rodzajProduktu = result;
        });
    });
