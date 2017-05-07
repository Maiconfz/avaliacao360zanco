(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('EquipeDialogController', EquipeDialogController);

    EquipeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Equipe', 'AvaliacaoModelo', 'User'];

    function EquipeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Equipe, AvaliacaoModelo, User) {
        var vm = this;

        vm.equipe = entity;
        vm.clear = clear;
        vm.save = save;
        vm.avaliacaomodelos = AvaliacaoModelo.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.equipe.id !== null) {
                Equipe.update(vm.equipe, onSaveSuccess, onSaveError);
            } else {
                Equipe.save(vm.equipe, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:equipeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
