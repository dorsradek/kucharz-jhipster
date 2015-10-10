'use strict';

angular.module('kucharzApp')
    .controller('PracochlonnoscPrzepisuController', function ($scope, PracochlonnoscPrzepisu) {
        $scope.pracochlonnoscPrzepisus = [];
        $scope.loadAll = function() {
            PracochlonnoscPrzepisu.query(function(result) {
               $scope.pracochlonnoscPrzepisus = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PracochlonnoscPrzepisu.get({id: id}, function(result) {
                $scope.pracochlonnoscPrzepisu = result;
                $('#deletePracochlonnoscPrzepisuConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PracochlonnoscPrzepisu.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePracochlonnoscPrzepisuConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.pracochlonnoscPrzepisu = {
                name: null,
                id: null
            };
        };
    });
