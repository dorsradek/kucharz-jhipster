'use strict';

angular.module('kucharzApp')
    .factory('PrzepisProdukt', function ($resource, DateUtils) {
        return $resource('api/przepisProdukts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
