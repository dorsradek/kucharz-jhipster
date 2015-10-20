'use strict';

angular.module('kucharzApp')
    .factory('Przepis', function ($resource, DateUtils) {
        return $resource('api/przepiss/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    data.modificationDate = DateUtils.convertDateTimeFromServer(data.modificationDate);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    });


/*

 It's a matter of preference.

 But nothing prevents you from consolidating all your resources inside one factory as in:

 services.factory('Api', ['$resource',
 function($resource) {
 return {
 Recipe: $resource('/recipes/:id', {id: '@id'}),
 Users:  $resource('/users/:id', {id: '@id'}),
 Group:  $resource('/groups/:id', {id: '@id'})
 };
 }]);

 function myCtrl($scope, Api){
 $scope.recipe = Api.Recipe.get({id: 1});
 $scope.users = Api.Users.query();
 ...
 }
 */
