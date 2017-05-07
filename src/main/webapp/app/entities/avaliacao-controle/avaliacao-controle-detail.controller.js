(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('AvaliacaoControleDetailController', AvaliacaoControleDetailController);

    AvaliacaoControleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AvaliacaoControle', 'AvaliacaoModelo', 'User'];

    function AvaliacaoControleDetailController($scope, $rootScope, $stateParams, previousState, entity, AvaliacaoControle, AvaliacaoModelo, User) {
        var vm = this;

        vm.avaliacaoControle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:avaliacaoControleUpdate', function(event, result) {
            vm.avaliacaoControle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
