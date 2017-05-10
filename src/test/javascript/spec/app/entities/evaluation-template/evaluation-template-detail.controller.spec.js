'use strict';

describe('Controller Tests', function() {

    describe('EvaluationTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvaluationTemplate, MockQuestionTemplate, MockTeam;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvaluationTemplate = jasmine.createSpy('MockEvaluationTemplate');
            MockQuestionTemplate = jasmine.createSpy('MockQuestionTemplate');
            MockTeam = jasmine.createSpy('MockTeam');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EvaluationTemplate': MockEvaluationTemplate,
                'QuestionTemplate': MockQuestionTemplate,
                'Team': MockTeam
            };
            createController = function() {
                $injector.get('$controller')("EvaluationTemplateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ZancoApp:evaluationTemplateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
