(function() {
  'use strict';
  angular.module('avaliacao360ZancoApp')
      .factory('EvaluationTemplate', EvaluationTemplate);

  EvaluationTemplate.$inject = ['$resource'];

  function EvaluationTemplate($resource) {
    var resourceUrl = 'api/evaluation-templates/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true},
      'queryByTeam':
          {url: (resourceUrl + '/team/:teamId'), method: 'GET', isArray: true},
      'get': {
        method: 'GET',
        transformResponse: function(data) {
          if (data) {
            data = angular.fromJson(data);
          }
          return data;
        }
      },
      'update': {method: 'PUT'}
    });
  }
})();
