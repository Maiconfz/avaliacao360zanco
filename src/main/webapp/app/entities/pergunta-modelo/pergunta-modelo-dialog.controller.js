(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('PerguntaModeloDialogController', PerguntaModeloDialogController);

    PerguntaModeloDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PerguntaModelo', 'AvaliacaoModelo'];

    function PerguntaModeloDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PerguntaModelo, AvaliacaoModelo) {
        var vm = this;

        vm.perguntaModelo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.avaliacaomodelos = AvaliacaoModelo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.perguntaModelo.id !== null) {
                PerguntaModelo.update(vm.perguntaModelo, onSaveSuccess, onSaveError);
            } else {
                PerguntaModelo.save(vm.perguntaModelo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ZancoApp:perguntaModeloUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
