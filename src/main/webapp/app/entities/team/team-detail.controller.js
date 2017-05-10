(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('TeamDetailController', TeamDetailController);

    TeamDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Team', 'EvaluationTemplate', 'Evaluation', 'User'];

    function TeamDetailController($scope, $rootScope, $stateParams, previousState, entity, Team, EvaluationTemplate, Evaluation, User) {
        var vm = this;

        vm.team = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:teamUpdate', function(event, result) {
            vm.team = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
