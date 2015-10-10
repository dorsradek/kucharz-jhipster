'use strict';

angular.module('kucharzApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pracochlonnoscPrzepisu', {
                parent: 'entity',
                url: '/pracochlonnoscPrzepisus',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.pracochlonnoscPrzepisu.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pracochlonnoscPrzepisu/pracochlonnoscPrzepisus.html',
                        controller: 'PracochlonnoscPrzepisuController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pracochlonnoscPrzepisu');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pracochlonnoscPrzepisu.detail', {
                parent: 'entity',
                url: '/pracochlonnoscPrzepisu/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kucharzApp.pracochlonnoscPrzepisu.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pracochlonnoscPrzepisu/pracochlonnoscPrzepisu-detail.html',
                        controller: 'PracochlonnoscPrzepisuDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pracochlonnoscPrzepisu');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PracochlonnoscPrzepisu', function($stateParams, PracochlonnoscPrzepisu) {
                        return PracochlonnoscPrzepisu.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pracochlonnoscPrzepisu.new', {
                parent: 'pracochlonnoscPrzepisu',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pracochlonnoscPrzepisu/pracochlonnoscPrzepisu-dialog.html',
                        controller: 'PracochlonnoscPrzepisuDialogController',
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
                        $state.go('pracochlonnoscPrzepisu', null, { reload: true });
                    }, function() {
                        $state.go('pracochlonnoscPrzepisu');
                    })
                }]
            })
            .state('pracochlonnoscPrzepisu.edit', {
                parent: 'pracochlonnoscPrzepisu',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pracochlonnoscPrzepisu/pracochlonnoscPrzepisu-dialog.html',
                        controller: 'PracochlonnoscPrzepisuDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PracochlonnoscPrzepisu', function(PracochlonnoscPrzepisu) {
                                return PracochlonnoscPrzepisu.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pracochlonnoscPrzepisu', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
