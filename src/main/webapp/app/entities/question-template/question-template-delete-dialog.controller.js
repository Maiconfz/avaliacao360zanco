(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('QuestionTemplateDeleteController',QuestionTemplateDeleteController);

    QuestionTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'QuestionTemplate'];

    function QuestionTemplateDeleteController($uibModalInstance, entity, QuestionTemplate) {
        var vm = this;

        vm.questionTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QuestionTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
