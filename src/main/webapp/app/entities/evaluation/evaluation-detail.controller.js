(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('EvaluationDetailController', EvaluationDetailController);

    EvaluationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Evaluation', 'Question', 'User', 'Team'];

    function EvaluationDetailController($scope, $rootScope, $stateParams, previousState, entity, Evaluation, Question, User, Team) {
        var vm = this;

        vm.evaluation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:evaluationUpdate', function(event, result) {
            vm.evaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
