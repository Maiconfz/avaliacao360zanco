(function() {
    'use strict';
    angular.module('avaliacao360ZancoApp').factory('Team', Team);

    Team.$inject = [ '$resource' ];

    function Team($resource) {
        var resourceUrl = 'api/teams/:id';

        return $resource(resourceUrl, {}, {
            'query' : {
                method : 'GET',
                isArray : true
            },
            'queryByLeader' : {
                url : (resourceUrl + '/leader/:leaderId'),
                method : 'GET',
                isArray : true
            },
            'queryByMember' : {
                url : (resourceUrl + '/member/:memberId'),
                method : 'GET',
                isArray : true
            },
            'get' : {
                method : 'GET',
                transformResponse : function(data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update' : {
                method : 'PUT'
            }
        });
    }
})();
