(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('RespostaDetailController', RespostaDetailController);

    RespostaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Resposta', 'Pergunta'];

    function RespostaDetailController($scope, $rootScope, $stateParams, previousState, entity, Resposta, Pergunta) {
        var vm = this;

        vm.resposta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:respostaUpdate', function(event, result) {
            vm.resposta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
