'use strict';

angular.module('kucharzApp')
    .controller('ProduktDetailController', function ($scope, $rootScope, $stateParams, entity, Produkt, MiaraProduktu, RodzajProduktu, PrzepisProdukt) {
        $scope.produkt = entity;
        $scope.load = function (id) {
            Produkt.get({id: id}, function(result) {
                $scope.produkt = result;
            });
        };
        $rootScope.$on('kucharzApp:produktUpdate', function(event, result) {
            $scope.produkt = result;
        });
    });
