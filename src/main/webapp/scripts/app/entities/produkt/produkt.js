'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('produkt', {
                parent: 'entity',
                url: '/produkts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.produkt.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/produkt/produkts.html',
                        controller: 'ProduktController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('produkt');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('produkt.detail', {
                parent: 'entity',
                url: '/produkt/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.produkt.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/produkt/produkt-detail.html',
                        controller: 'ProduktDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('produkt');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Produkt', function ($stateParams, Produkt) {
                        return Produkt.get({id: $stateParams.id});
                    }]
                }
            })
            .state('produkt.new', {
                parent: 'produkt',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/produkt/produkt-dialog.html',
                        controller: 'ProduktDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('produkt', null, {reload: true});
                        }, function () {
                            $state.go('produkt');
                        })
                }]
            })
            .state('produkt.edit', {
                parent: 'produkt',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/produkt/produkt-dialog.html',
                        controller: 'ProduktDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Produkt', function (Produkt) {
                                return Produkt.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('produkt', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
