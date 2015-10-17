'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('przepisProdukt', {
                parent: 'entity',
                url: '/przepisProdukts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.przepisProdukt.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/przepisProdukt/przepisProdukts.html',
                        controller: 'PrzepisProduktController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('przepisProdukt');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('przepisProdukt.detail', {
                parent: 'entity',
                url: '/przepisProdukt/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.przepisProdukt.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/przepisProdukt/przepisProdukt-detail.html',
                        controller: 'PrzepisProduktDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('przepisProdukt');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrzepisProdukt', function ($stateParams, PrzepisProdukt) {
                        return PrzepisProdukt.get({id: $stateParams.id});
                    }]
                }
            })
            .state('przepisProdukt.new', {
                parent: 'przepisProdukt',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/przepisProdukt/przepisProdukt-dialog.html',
                        controller: 'PrzepisProduktDialogController',
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
                            $state.go('przepisProdukt', null, {reload: true});
                        }, function () {
                            $state.go('przepisProdukt');
                        })
                }]
            })
            .state('przepisProdukt.edit', {
                parent: 'przepisProdukt',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/przepisProdukt/przepisProdukt-dialog.html',
                        controller: 'PrzepisProduktDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PrzepisProdukt', function (PrzepisProdukt) {
                                return PrzepisProdukt.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('przepisProdukt', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
