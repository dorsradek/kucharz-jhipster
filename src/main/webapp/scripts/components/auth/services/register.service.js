'use strict';

angular.module('kucharzApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {});
    });


