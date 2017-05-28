(function() {
    'use strict';

    var backButton = {
        template : '<button class="btn btn-primary btn-xs" ng-click="vm.goBack()">&lt;&lt; Back</button>',
        controller : backButtonController,
        controllerAs : 'vm'
    };

    angular.module('avaliacao360ZancoApp').component('backButton', backButton);

    backButtonController.$inject = [ '$log', '$scope', '$window', 'AlertService' ];

    function backButtonController($log, $scope, $window, AlertService) {
        var vm = this;
        vm.goBack = goBack;

        $log.debug('BackButton component loaded');

        function goBack() {
            $log.debug('BackButton pressed.');
            $window.history.back();
        }

    }
})();
