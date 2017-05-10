(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('EvaluationTemplateDialogController', EvaluationTemplateDialogController);

    EvaluationTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EvaluationTemplate', 'QuestionTemplate', 'Team'];

    function EvaluationTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EvaluationTemplate, QuestionTemplate, Team) {
        var vm = this;

        vm.evaluationTemplate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.questiontemplates = QuestionTemplate.query();
        vm.teams = Team.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evaluationTemplate.id !== null) {
                EvaluationTemplate.update(vm.evaluationTemplate, onSaveSuccess, onSaveError);
            } else {
                EvaluationTemplate.save(vm.evaluationTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:evaluationTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
