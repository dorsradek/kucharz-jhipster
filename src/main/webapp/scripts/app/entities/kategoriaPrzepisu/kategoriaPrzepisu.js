'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('kategoriaPrzepisu', {
                parent: 'entity',
                url: '/kategoriaPrzepisus',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.kategoriaPrzepisu.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/kategoriaPrzepisu/kategoriaPrzepisus.html',
                        controller: 'KategoriaPrzepisuController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('kategoriaPrzepisu');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('kategoriaPrzepisu.detail', {
                parent: 'entity',
                url: '/kategoriaPrzepisu/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.kategoriaPrzepisu.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/kategoriaPrzepisu/kategoriaPrzepisu-detail.html',
                        controller: 'KategoriaPrzepisuDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('kategoriaPrzepisu');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'KategoriaPrzepisu', function ($stateParams, KategoriaPrzepisu) {
                        return KategoriaPrzepisu.get({id: $stateParams.id});
                    }]
                }
            })
            .state('kategoriaPrzepisu.new', {
                parent: 'kategoriaPrzepisu',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/kategoriaPrzepisu/kategoriaPrzepisu-dialog.html',
                        controller: 'KategoriaPrzepisuDialogController',
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
                            $state.go('kategoriaPrzepisu', null, {reload: true});
                        }, function () {
                            $state.go('kategoriaPrzepisu');
                        })
                }]
            })
            .state('kategoriaPrzepisu.edit', {
                parent: 'kategoriaPrzepisu',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/kategoriaPrzepisu/kategoriaPrzepisu-dialog.html',
                        controller: 'KategoriaPrzepisuDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['KategoriaPrzepisu', function (KategoriaPrzepisu) {
                                return KategoriaPrzepisu.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('kategoriaPrzepisu', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
