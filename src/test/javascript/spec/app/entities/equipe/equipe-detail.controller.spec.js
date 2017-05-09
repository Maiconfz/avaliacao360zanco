'use strict';

describe('Controller Tests', function() {

    describe('Equipe Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEquipe, MockAvaliacaoModelo, MockAvaliacao, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEquipe = jasmine.createSpy('MockEquipe');
            MockAvaliacaoModelo = jasmine.createSpy('MockAvaliacaoModelo');
            MockAvaliacao = jasmine.createSpy('MockAvaliacao');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Equipe': MockEquipe,
                'AvaliacaoModelo': MockAvaliacaoModelo,
                'Avaliacao': MockAvaliacao,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("EquipeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ZancoApp:equipeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
