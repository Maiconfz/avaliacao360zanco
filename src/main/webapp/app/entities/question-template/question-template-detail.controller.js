(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('QuestionTemplateDetailController', QuestionTemplateDetailController);

    QuestionTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionTemplate', 'EvaluationTemplate'];

    function QuestionTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionTemplate, EvaluationTemplate) {
        var vm = this;

        vm.questionTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:questionTemplateUpdate', function(event, result) {
            vm.questionTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
