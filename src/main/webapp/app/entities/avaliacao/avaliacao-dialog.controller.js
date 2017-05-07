(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('AvaliacaoDialogController', AvaliacaoDialogController);

    AvaliacaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Avaliacao', 'User'];

    function AvaliacaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Avaliacao, User) {
        var vm = this;

        vm.avaliacao = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

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
