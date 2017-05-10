(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('QuestionTemplateDialogController', QuestionTemplateDialogController);

    QuestionTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuestionTemplate', 'EvaluationTemplate'];

    function QuestionTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QuestionTemplate, EvaluationTemplate) {
        var vm = this;

        vm.questionTemplate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluationtemplates = EvaluationTemplate.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.questionTemplate.id !== null) {
                QuestionTemplate.update(vm.questionTemplate, onSaveSuccess, onSaveError);
            } else {
                QuestionTemplate.save(vm.questionTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:questionTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
