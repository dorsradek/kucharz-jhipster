'use strict';

angular.module('kucharzApp')
    .controller('PrzepisDetailController', function ($scope, $state, $rootScope, $stateParams, entity, Przepis, KategoriaPrzepisu, PracochlonnoscPrzepisu, PrzepisPartProdukt) {
        $scope.przepis = entity;
        $scope.state = $state;

        $scope.load = function (id) {
            Przepis.get({id: id}, function (result) {
                $scope.przepis = result;
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

        $scope.divide = function (duration) {
            var hours = Math.floor(duration / 60);
            var minutes = duration - (hours * 60);
            var result = hours + ':';
            if (minutes == 0) {
                result += '00';
            } else if (minutes < 10) {
                result += '0' + minutes;
            } else {
                result += minutes;
            }
            return result;
        };

    });
