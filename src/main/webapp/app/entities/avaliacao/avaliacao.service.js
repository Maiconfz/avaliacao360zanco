(function() {
    'use strict';
    angular
        .module('avaliacao360ZancoApp')
        .factory('Avaliacao', Avaliacao);

    Avaliacao.$inject = ['$resource'];

    function Avaliacao ($resource) {
        var resourceUrl =  'api/avaliacaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
