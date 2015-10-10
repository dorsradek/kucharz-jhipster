'use strict';

angular.module('kucharzApp')
    .controller('KategoriaPrzepisuDetailController', function ($scope, $rootScope, $stateParams, entity, KategoriaPrzepisu, Przepis) {
        $scope.kategoriaPrzepisu = entity;
        $scope.load = function (id) {
            KategoriaPrzepisu.get({id: id}, function(result) {
                $scope.kategoriaPrzepisu = result;
            });
        };
        $rootScope.$on('kucharzApp:kategoriaPrzepisuUpdate', function(event, result) {
            $scope.kategoriaPrzepisu = result;
        });
    });
