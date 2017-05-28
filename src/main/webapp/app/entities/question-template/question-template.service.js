(function() {
    'use strict';
    angular.module('avaliacao360ZancoApp').factory('QuestionTemplate', QuestionTemplate);

    QuestionTemplate.$inject = [ '$resource' ];

    function QuestionTemplate($resource) {
        var commonResourceUrl = 'api/question-templates';
        var resourceUrl = 'api/question-templates/:id';
        return $resource(resourceUrl, {}, {
            'query' : {
                method : 'GET',
                isArray : true
            },
            'queryByEvaluationTemplate' : {
                url : (commonResourceUrl + '/evaluation-template/:evaluationTemplateId'),
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
