(function() {
    'use strict';
    angular
        .module('avaliacao360ZancoApp')
        .factory('PerguntaModelo', PerguntaModelo);

    PerguntaModelo.$inject = ['$resource'];

    function PerguntaModelo ($resource) {
        var resourceUrl =  'api/pergunta-modelos/:id';

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
