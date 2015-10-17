'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('przepisDescription', {
                parent: 'entity',
                url: '/przepisDescriptions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.przepisDescription.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/przepisDescription/przepisDescriptions.html',
                        controller: 'PrzepisDescriptionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('przepisDescription');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('przepisDescription.detail', {
                parent: 'entity',
                url: '/przepisDescription/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.przepisDescription.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/przepisDescription/przepisDescription-detail.html',
                        controller: 'PrzepisDescriptionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('przepisDescription');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrzepisDescription', function ($stateParams, PrzepisDescription) {
                        return PrzepisDescription.get({id: $stateParams.id});
                    }]
                }
            })
            .state('przepisDescription.new', {
                parent: 'przepisDescription',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/przepisDescription/przepisDescription-dialog.html',
                        controller: 'PrzepisDescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    text: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('przepisDescription', null, {reload: true});
                        }, function () {
                            $state.go('przepisDescription');
                        })
                }]
            })
            .state('przepisDescription.edit', {
                parent: 'przepisDescription',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/przepisDescription/przepisDescription-dialog.html',
                        controller: 'PrzepisDescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PrzepisDescription', function (PrzepisDescription) {
                                return PrzepisDescription.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('przepisDescription', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
