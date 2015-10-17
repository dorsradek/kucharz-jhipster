'use strict';

angular.module('kucharzApp')
    .controller('PrzepisDescriptionController', function ($scope, PrzepisDescription) {
        $scope.przepisDescriptions = [];
        $scope.loadAll = function () {
            PrzepisDescription.query(function (result) {
                $scope.przepisDescriptions = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PrzepisDescription.get({id: id}, function (result) {
                $scope.przepisDescription = result;
                $('#deletePrzepisDescriptionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PrzepisDescription.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePrzepisDescriptionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.przepisDescription = {
                title: null,
                text: null,
                id: null
            };
        };
    });
