'use strict';

angular.module('kucharzApp')
    .factory('PrzepisImage', function ($resource) {
        return $resource('api/przepisimage/:id', null, {
            'save': {
                method: 'POST',
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            },
            'get': {
                method: 'GET',
                headers: {'Content-Type': 'image/png'}
            }
        })
    });
