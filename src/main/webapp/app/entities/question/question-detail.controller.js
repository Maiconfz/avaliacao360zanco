(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .controller('QuestionDetailController', QuestionDetailController);

    QuestionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Question', 'Answer', 'Evaluation'];

    function QuestionDetailController($scope, $rootScope, $stateParams, previousState, entity, Question, Answer, Evaluation) {
        var vm = this;

        vm.question = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ZancoApp:questionUpdate', function(event, result) {
            vm.question = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
