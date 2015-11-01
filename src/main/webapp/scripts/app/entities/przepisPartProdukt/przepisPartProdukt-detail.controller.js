'use strict';

angular.module('kucharzApp')
    .controller('PrzepisPartProduktDetailController', function ($scope, $rootScope, $stateParams, entity, PrzepisPartProdukt, Produkt, Przepis) {
        $scope.przepisPartProdukt = entity;
        $scope.load = function (id) {
            PrzepisPartProdukt.get({id: id}, function (result) {
                $scope.przepisPartProdukt = result;
            });
        };
        $rootScope.$on('kucharzApp:przepisPartProduktUpdate', function (event, result) {
            $scope.przepisPartProdukt = result;
        });
    });
