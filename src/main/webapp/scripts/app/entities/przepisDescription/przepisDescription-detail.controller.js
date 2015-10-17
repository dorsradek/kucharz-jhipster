'use strict';

angular.module('kucharzApp')
    .controller('PrzepisDescriptionDetailController', function ($scope, $rootScope, $stateParams, entity, PrzepisDescription, Przepis) {
        $scope.przepisDescription = entity;
        $scope.load = function (id) {
            PrzepisDescription.get({id: id}, function (result) {
                $scope.przepisDescription = result;
            });
        };
        $rootScope.$on('kucharzApp:przepisDescriptionUpdate', function (event, result) {
            $scope.przepisDescription = result;
        });
    });
