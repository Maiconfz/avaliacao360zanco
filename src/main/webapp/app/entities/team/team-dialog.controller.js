(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('TeamDialogController', TeamDialogController);

    TeamDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Team', 'EvaluationTemplate', 'Evaluation', 'User'];

    function TeamDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Team, EvaluationTemplate, Evaluation, User) {
        var vm = this;

        vm.team = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluationtemplates = EvaluationTemplate.query();
        vm.evaluations = Evaluation.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.team.id !== null) {
                Team.update(vm.team, onSaveSuccess, onSaveError);
            } else {
                Team.save(vm.team, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:teamUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
