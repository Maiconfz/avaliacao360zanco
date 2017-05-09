(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('PerguntaDetailController', PerguntaDetailController);

    PerguntaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pergunta', 'Resposta', 'Avaliacao'];

    function PerguntaDetailController($scope, $rootScope, $stateParams, previousState, entity, Pergunta, Resposta, Avaliacao) {
        var vm = this;

        vm.pergunta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:perguntaUpdate', function(event, result) {
            vm.pergunta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
