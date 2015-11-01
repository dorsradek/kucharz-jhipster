'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('PrzepisPartProdukt', {
                parent: 'entity',
                url: '/PrzepisPartProdukts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.PrzepisPartProdukt.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/PrzepisPartProdukt/PrzepisPartProdukts.html',
                        controller: 'PrzepisPartProduktController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('PrzepisPartProdukt');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('PrzepisPartProdukt.detail', {
                parent: 'entity',
                url: '/PrzepisPartProdukt/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.PrzepisPartProdukt.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/PrzepisPartProdukt/PrzepisPartProdukt-detail.html',
                        controller: 'PrzepisPartProduktDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('PrzepisPartProdukt');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrzepisPartProdukt', function ($stateParams, PrzepisPartProdukt) {
                        return PrzepisPartProdukt.get({id: $stateParams.id});
                    }]
                }
            })
            .state('PrzepisPartProdukt.new', {
                parent: 'PrzepisPartProdukt',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/PrzepisPartProdukt/PrzepisPartProdukt-dialog.html',
                        controller: 'PrzepisPartProduktDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    quantity: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('PrzepisPartProdukt', null, {reload: true});
                        }, function () {
                            $state.go('PrzepisPartProdukt');
                        })
                }]
            })
            .state('PrzepisPartProdukt.edit', {
                parent: 'PrzepisPartProdukt',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/PrzepisPartProdukt/PrzepisPartProdukt-dialog.html',
                        controller: 'PrzepisPartProduktDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PrzepisPartProdukt', function (PrzepisPartProdukt) {
                                return PrzepisPartProdukt.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('PrzepisPartProdukt', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
