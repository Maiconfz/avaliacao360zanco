(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('AvaliacaoDialogController', AvaliacaoDialogController);

    AvaliacaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Avaliacao', 'Pergunta', 'User', 'Equipe'];

    function AvaliacaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Avaliacao, Pergunta, User, Equipe) {
        var vm = this;

        vm.avaliacao = entity;
        vm.clear = clear;
        vm.save = save;
        vm.perguntas = Pergunta.query();
        vm.users = User.query();
        vm.equipes = Equipe.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.avaliacao.id !== null) {
                Avaliacao.update(vm.avaliacao, onSaveSuccess, onSaveError);
            } else {
                Avaliacao.save(vm.avaliacao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:avaliacaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
