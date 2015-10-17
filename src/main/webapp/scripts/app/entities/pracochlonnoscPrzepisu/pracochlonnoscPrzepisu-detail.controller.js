'use strict';

angular.module('kucharzApp')
    .controller('PracochlonnoscPrzepisuDetailController', function ($scope, $rootScope, $stateParams, entity, PracochlonnoscPrzepisu, Przepis) {
        $scope.pracochlonnoscPrzepisu = entity;
        $scope.load = function (id) {
            PracochlonnoscPrzepisu.get({id: id}, function (result) {
                $scope.pracochlonnoscPrzepisu = result;
            });
        };
        $rootScope.$on('kucharzApp:pracochlonnoscPrzepisuUpdate', function (event, result) {
            $scope.pracochlonnoscPrzepisu = result;
        });
    });
