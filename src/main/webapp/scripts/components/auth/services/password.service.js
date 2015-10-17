'use strict';

angular.module('kucharzApp')
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {});
    });

angular.module('kucharzApp')
    .factory('PasswordResetInit', function ($resource) {
        return $resource('api/account/reset_password/init', {}, {})
    });

angular.module('kucharzApp')
    .factory('PasswordResetFinish', function ($resource) {
        return $resource('api/account/reset_password/finish', {}, {})
    });
