(function () {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('TeamDialogController', TeamDialogController);

    TeamDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Team', 'EvaluationTemplate', 'Evaluation', 'User', 'Principal'];

    function TeamDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Team, EvaluationTemplate, Evaluation, User, Principal) {
        var vm = this;

        vm.team = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluationtemplates = EvaluationTemplate.query();
        vm.evaluations = Evaluation.query();
        vm.users = User.query();
        vm.leaderAccount = null;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            console.log("Saving new team");
            vm.isSaving = true;

            var getUserPromise = Principal.identity(true);

            getUserPromise.then(function (user) {
                vm.team.leader = user;

                if (vm.team.id !== null) {
                    Team.update(vm.team, onSaveSuccess, onSaveError);
                } else {
                    Team.save(vm.team, onSaveSuccess, onSaveError);
                }
            });
        }

        function onSaveSuccess(result) {
            $scope.$emit('avaliacao360ZancoApp:teamUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }
    }
})();
