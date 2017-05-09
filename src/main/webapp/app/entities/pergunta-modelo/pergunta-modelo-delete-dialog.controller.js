(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('PerguntaModeloDeleteController',PerguntaModeloDeleteController);

    PerguntaModeloDeleteController.$inject = ['$uibModalInstance', 'entity', 'PerguntaModelo'];

    function PerguntaModeloDeleteController($uibModalInstance, entity, PerguntaModelo) {
        var vm = this;

        vm.perguntaModelo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PerguntaModelo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
