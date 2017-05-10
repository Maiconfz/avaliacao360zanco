'use strict';

describe('Controller Tests', function() {

    describe('Team Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTeam, MockEvaluationTemplate, MockEvaluation, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTeam = jasmine.createSpy('MockTeam');
            MockEvaluationTemplate = jasmine.createSpy('MockEvaluationTemplate');
            MockEvaluation = jasmine.createSpy('MockEvaluation');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Team': MockTeam,
                'EvaluationTemplate': MockEvaluationTemplate,
                'Evaluation': MockEvaluation,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("TeamDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ZancoApp:teamUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
