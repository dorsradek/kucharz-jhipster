'use strict';

angular.module('kucharzApp')
    .factory('RodzajProduktu', function ($resource, DateUtils) {
        return $resource('api/rodzajProduktus/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    });
