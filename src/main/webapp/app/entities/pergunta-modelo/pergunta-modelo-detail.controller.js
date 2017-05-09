(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('PerguntaModeloDetailController', PerguntaModeloDetailController);

    PerguntaModeloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PerguntaModelo', 'AvaliacaoModelo'];

    function PerguntaModeloDetailController($scope, $rootScope, $stateParams, previousState, entity, PerguntaModelo, AvaliacaoModelo) {
        var vm = this;

        vm.perguntaModelo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:perguntaModeloUpdate', function(event, result) {
            vm.perguntaModelo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
