'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('przepis', {
                parent: 'entity',
                url: '/przepiss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.przepis.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/przepis/przepiss.html',
                        controller: 'PrzepisController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('przepis');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('przepis.detail', {
                parent: 'entity',
                url: '/przepis/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.przepis.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/przepis/przepis-detail.html',
                        controller: 'PrzepisDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('przepis');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Przepis', function ($stateParams, Przepis) {
                        return Przepis.get({id: $stateParams.id});
                    }]
                }
            })
            .state('przepis.new', {
                parent: 'przepis',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/przepis/przepis-dialog.html',
                        controller: 'PrzepisDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    duration: null,
                                    creationDate: null,
                                    modificationDate: null,
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('przepis', null, {reload: true});
                        }, function () {
                            $state.go('przepis');
                        })
                }]
            })
            .state('przepis.edit', {
                parent: 'przepis',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/przepis/przepis-dialog.html',
                        controller: 'PrzepisDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Przepis', function (Przepis) {
                                return Przepis.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('przepis', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
