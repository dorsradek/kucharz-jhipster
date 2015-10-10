'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rodzajProduktu', {
                parent: 'entity',
                url: '/rodzajProduktus',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.rodzajProduktu.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rodzajProduktu/rodzajProduktus.html',
                        controller: 'RodzajProduktuController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rodzajProduktu');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rodzajProduktu.detail', {
                parent: 'entity',
                url: '/rodzajProduktu/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.rodzajProduktu.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rodzajProduktu/rodzajProduktu-detail.html',
                        controller: 'RodzajProduktuDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rodzajProduktu');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RodzajProduktu', function($stateParams, RodzajProduktu) {
                        return RodzajProduktu.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rodzajProduktu.new', {
                parent: 'rodzajProduktu',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/rodzajProduktu/rodzajProduktu-dialog.html',
                        controller: 'RodzajProduktuDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rodzajProduktu', null, { reload: true });
                    }, function() {
                        $state.go('rodzajProduktu');
                    })
                }]
            })
            .state('rodzajProduktu.edit', {
                parent: 'rodzajProduktu',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/rodzajProduktu/rodzajProduktu-dialog.html',
                        controller: 'RodzajProduktuDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RodzajProduktu', function(RodzajProduktu) {
                                return RodzajProduktu.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rodzajProduktu', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
