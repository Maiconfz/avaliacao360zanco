(function() {
    'use strict';

    angular.module('avaliacao360ZancoApp').controller('QuestionTemplateController', QuestionTemplateController);

    QuestionTemplateController.$inject = [ '$log', 'QuestionTemplate', 'ParseLinks', 'AlertService', 'paginationConstants', 'teamFilter', 'evaluationTemplateFilter' ];

    function QuestionTemplateController($log, QuestionTemplate, ParseLinks, AlertService, paginationConstants, teamFilter, evaluationTemplateFilter) {
        var vm = this;

        vm.questionTemplates = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last : 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.teamFilter = teamFilter;
        vm.evaluationTemplateFilter = evaluationTemplateFilter;

        loadAll();

        function loadAll() {
            QuestionTemplate.queryByEvaluationTemplate({
                page : vm.page,
                size : vm.itemsPerPage,
                sort : sort(),
                evaluationTemplateId : vm.evaluationTemplateFilter.id
            }, onSuccess, onError);

            function sort() {
                var result = [ vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc') ];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.questionTemplates.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset() {
            vm.page = 0;
            vm.questionTemplates = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
