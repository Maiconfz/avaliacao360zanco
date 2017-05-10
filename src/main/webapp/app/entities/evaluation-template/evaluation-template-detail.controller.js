(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('EvaluationTemplateDetailController', EvaluationTemplateDetailController);

    EvaluationTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EvaluationTemplate', 'QuestionTemplate', 'Team'];

    function EvaluationTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, EvaluationTemplate, QuestionTemplate, Team) {
        var vm = this;

        vm.evaluationTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:evaluationTemplateUpdate', function(event, result) {
            vm.evaluationTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
