(function () {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('EvaluationTemplateController', EvaluationTemplateController);

    EvaluationTemplateController.$inject = ['$log', 'EvaluationTemplate', 'ParseLinks', 'AlertService', 'paginationConstants', 'filterTeam'];

    function EvaluationTemplateController($log, EvaluationTemplate, ParseLinks, AlertService, paginationConstants, filterTeam) {
        $log.debug(filterTeam);
        var vm = this;
        
        vm.evaluationTemplates = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        vm.filterTeam = filterTeam;

        loadAll();

        function loadAll() {
            EvaluationTemplate.queryByTeam({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort(),
                teamId: filterTeam.id
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.evaluationTemplates.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset() {
            vm.page = 0;
            vm.evaluationTemplates = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
