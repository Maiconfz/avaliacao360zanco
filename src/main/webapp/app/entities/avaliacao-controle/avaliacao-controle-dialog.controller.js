(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('AvaliacaoControleDialogController', AvaliacaoControleDialogController);

    AvaliacaoControleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AvaliacaoControle', 'AvaliacaoModelo', 'User'];

    function AvaliacaoControleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AvaliacaoControle, AvaliacaoModelo, User) {
        var vm = this;

        vm.avaliacaoControle = entity;
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
            if (vm.avaliacaoControle.id !== null) {
                AvaliacaoControle.update(vm.avaliacaoControle, onSaveSuccess, onSaveError);
            } else {
                AvaliacaoControle.save(vm.avaliacaoControle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:avaliacaoControleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
