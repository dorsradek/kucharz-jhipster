'use strict';

angular.module('kucharzApp')
    .controller('PrzepisDetailController', function ($scope, $state, $rootScope, $stateParams, entity, entityImage, Przepis, KategoriaPrzepisu, PracochlonnoscPrzepisu, PrzepisProdukt) {
        $scope.przepis = entity;
        $scope.dupa = entityImage;
        $scope.state = $state;
        $scope.load = function (id) {
            Przepis.get({id: id}, function (result) {
                $scope.przepis = result;
            });
            PrzepisImage.get({id: id}, function (result) {
                $scope.dupa = result;
            });
        };
        $rootScope.$on('kucharzApp:przepisUpdate', function (event, result) {
            $scope.przepis = result;
        });

        $scope.delete = function (id) {
            Przepis.get({id: id}, function (result) {
                $scope.przepis = result;
                $('#deletePrzepisConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Przepis.delete({id: id},
                function () {
                    $('#deletePrzepisConfirmation').bind('hidden.bs.modal', function () {
                        $scope.state.go('przepis');
                    }).modal('hide');
                });
        };
        ó

    });
