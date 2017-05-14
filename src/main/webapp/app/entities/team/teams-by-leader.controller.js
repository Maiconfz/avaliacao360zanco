(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('TeamsByLeaderController', TeamsByLeaderController);

    TeamsByLeaderController.$inject = ['$log', 'Principal', 'Team', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function TeamsByLeaderController($log, Principal, Team, ParseLinks, AlertService, paginationConstants) {
        $log.debug("Loading TeamsByleaderController");

        var vm = this;

        vm.teams = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        function loadAll () {
            var getUser = Principal.identity();

            getUser.then(function(user){
                Team.queryByLeader({
                    page: vm.page,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    leaderId: user.id
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
                        vm.teams.push(data[i]);
                    }
                }

                function onError(error) {
                    AlertService.error(error.data.message);
                }
            });
        }

        function reset () {
            vm.page = 0;
            vm.teams = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
