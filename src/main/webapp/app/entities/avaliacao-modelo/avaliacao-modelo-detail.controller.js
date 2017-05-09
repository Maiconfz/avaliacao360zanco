(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('AvaliacaoModeloDetailController', AvaliacaoModeloDetailController);

    AvaliacaoModeloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AvaliacaoModelo', 'PerguntaModelo', 'Equipe'];

    function AvaliacaoModeloDetailController($scope, $rootScope, $stateParams, previousState, entity, AvaliacaoModelo, PerguntaModelo, Equipe) {
        var vm = this;

        vm.avaliacaoModelo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:avaliacaoModeloUpdate', function(event, result) {
            vm.avaliacaoModelo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
