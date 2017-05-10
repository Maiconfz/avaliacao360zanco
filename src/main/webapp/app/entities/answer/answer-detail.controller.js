(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('AnswerDetailController', AnswerDetailController);

    AnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Answer', 'Question'];

    function AnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, Answer, Question) {
        var vm = this;

        vm.answer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:answerUpdate', function(event, result) {
            vm.answer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
