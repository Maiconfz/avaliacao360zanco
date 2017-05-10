'use strict';

describe('Controller Tests', function() {

    describe('Evaluation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvaluation, MockQuestion, MockUser, MockTeam;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvaluation = jasmine.createSpy('MockEvaluation');
            MockQuestion = jasmine.createSpy('MockQuestion');
            MockUser = jasmine.createSpy('MockUser');
            MockTeam = jasmine.createSpy('MockTeam');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Evaluation': MockEvaluation,
                'Question': MockQuestion,
                'User': MockUser,
                'Team': MockTeam
            };
            createController = function() {
                $injector.get('$controller')("EvaluationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ZancoApp:evaluationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
