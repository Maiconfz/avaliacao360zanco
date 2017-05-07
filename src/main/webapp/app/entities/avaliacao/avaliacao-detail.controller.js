(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('AvaliacaoDetailController', AvaliacaoDetailController);

    AvaliacaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Avaliacao', 'User'];

    function AvaliacaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Avaliacao, User) {
        var vm = this;

        vm.avaliacao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:avaliacaoUpdate', function(event, result) {
            vm.avaliacao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
