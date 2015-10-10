 'use strict';

angular.module('kucharzApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-kucharzApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-kucharzApp-params')});
                }
                return response;
            }
        };
    });
