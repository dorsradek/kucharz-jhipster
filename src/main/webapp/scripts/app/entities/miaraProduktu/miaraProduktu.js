'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('miaraProduktu', {
                parent: 'entity',
                url: '/miaraProduktus',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.miaraProduktu.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/miaraProduktu/miaraProduktus.html',
                        controller: 'MiaraProduktuController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('miaraProduktu');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('miaraProduktu.detail', {
                parent: 'entity',
                url: '/miaraProduktu/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.miaraProduktu.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/miaraProduktu/miaraProduktu-detail.html',
                        controller: 'MiaraProduktuDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('miaraProduktu');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MiaraProduktu', function($stateParams, MiaraProduktu) {
                        return MiaraProduktu.get({id : $stateParams.id});
                    }]
                }
            })
            .state('miaraProduktu.new', {
                parent: 'miaraProduktu',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/miaraProduktu/miaraProduktu-dialog.html',
                        controller: 'MiaraProduktuDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    shortcut: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('miaraProduktu', null, { reload: true });
                    }, function() {
                        $state.go('miaraProduktu');
                    })
                }]
            })
            .state('miaraProduktu.edit', {
                parent: 'miaraProduktu',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/miaraProduktu/miaraProduktu-dialog.html',
                        controller: 'MiaraProduktuDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MiaraProduktu', function(MiaraProduktu) {
                                return MiaraProduktu.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('miaraProduktu', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
