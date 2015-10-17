'use strict';

angular.module('kucharzApp')
    .controller('PrzepisProduktDetailController', function ($scope, $rootScope, $stateParams, entity, PrzepisProdukt, Produkt, Przepis) {
        $scope.przepisProdukt = entity;
        $scope.load = function (id) {
            PrzepisProdukt.get({id: id}, function (result) {
                $scope.przepisProdukt = result;
            });
        };
        $rootScope.$on('kucharzApp:przepisProduktUpdate', function (event, result) {
            $scope.przepisProdukt = result;
        });
    });
