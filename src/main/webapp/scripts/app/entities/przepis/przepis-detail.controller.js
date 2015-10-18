'use strict';

angular.module('kucharzApp')
    .controller('PrzepisDetailController', function ($scope, $rootScope, $stateParams, entity, Przepis, KategoriaPrzepisu, PracochlonnoscPrzepisu, PrzepisProdukt) {
        $scope.przepis = entity;
        $scope.load = function (id) {
            Przepis.get({id: id}, function (result) {
                $scope.przepis = result;
            });
        };
        $rootScope.$on('kucharzApp:przepisUpdate', function (event, result) {
            $scope.przepis = result;
        });
    });
