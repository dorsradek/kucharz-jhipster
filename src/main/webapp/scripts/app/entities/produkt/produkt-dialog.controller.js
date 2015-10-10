'use strict';

angular.module('kucharzApp').controller('ProduktDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Produkt', 'MiaraProduktu', 'RodzajProduktu', 'PrzepisProdukt',
        function($scope, $stateParams, $modalInstance, entity, Produkt, MiaraProduktu, RodzajProduktu, PrzepisProdukt) {

        $scope.produkt = entity;
        $scope.miaraproduktus = MiaraProduktu.query();
        $scope.rodzajproduktus = RodzajProduktu.query();
        $scope.przepisprodukts = PrzepisProdukt.query();
        $scope.load = function(id) {
            Produkt.get({id : id}, function(result) {
                $scope.produkt = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('kucharzApp:produktUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.produkt.id != null) {
                Produkt.update($scope.produkt, onSaveFinished);
            } else {
                Produkt.save($scope.produkt, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
