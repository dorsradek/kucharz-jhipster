'use strict';

angular.module('kucharzApp').controller('RodzajProduktuDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'RodzajProduktu', 'Produkt',
        function($scope, $stateParams, $modalInstance, entity, RodzajProduktu, Produkt) {

        $scope.rodzajProduktu = entity;
        $scope.produkts = Produkt.query();
        $scope.load = function(id) {
            RodzajProduktu.get({id : id}, function(result) {
                $scope.rodzajProduktu = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('kucharzApp:rodzajProduktuUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.rodzajProduktu.id != null) {
                RodzajProduktu.update($scope.rodzajProduktu, onSaveFinished);
            } else {
                RodzajProduktu.save($scope.rodzajProduktu, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
