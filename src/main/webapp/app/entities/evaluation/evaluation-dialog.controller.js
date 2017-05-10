(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('EvaluationDialogController', EvaluationDialogController);

    EvaluationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Evaluation', 'Question', 'User', 'Team'];

    function EvaluationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Evaluation, Question, User, Team) {
        var vm = this;

        vm.evaluation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.questions = Question.query();
        vm.users = User.query();
        vm.teams = Team.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evaluation.id !== null) {
                Evaluation.update(vm.evaluation, onSaveSuccess, onSaveError);
            } else {
                Evaluation.save(vm.evaluation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:evaluationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
