'use strict';

describe('Controller Tests', function() {

    describe('PerguntaModelo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPerguntaModelo, MockAvaliacaoModelo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPerguntaModelo = jasmine.createSpy('MockPerguntaModelo');
            MockAvaliacaoModelo = jasmine.createSpy('MockAvaliacaoModelo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PerguntaModelo': MockPerguntaModelo,
                'AvaliacaoModelo': MockAvaliacaoModelo
            };
            createController = function() {
                $injector.get('$controller')("PerguntaModeloDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ZancoApp:perguntaModeloUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
