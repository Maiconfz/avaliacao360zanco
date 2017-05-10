(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('QuestionDialogController', QuestionDialogController);

    QuestionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Question', 'Answer', 'Evaluation'];

    function QuestionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Question, Answer, Evaluation) {
        var vm = this;

        vm.question = entity;
        vm.clear = clear;
        vm.save = save;
        vm.answers = Answer.query();
        vm.evaluations = Evaluation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.question.id !== null) {
                Question.update(vm.question, onSaveSuccess, onSaveError);
            } else {
                Question.save(vm.question, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:questionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
