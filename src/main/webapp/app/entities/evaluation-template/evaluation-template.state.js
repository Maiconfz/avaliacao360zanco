(function () {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('evaluation-template-team', {
                parent: 'entity',
                url: '/evaluation-template/team/{teamId}',
                data: {
                    authorities: ['ROLE_LEADER'],
                    pageTitle: 'avaliacao360ZancoApp.evaluationTemplate.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evaluation-template/evaluation-templates.html',
                        controller: 'EvaluationTemplateController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evaluationTemplate');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    filterTeam: ['$stateParams', 'Team', function ($stateParams, Team) {
                        return Team.get({ id: $stateParams.teamId }).$promise;
                    }],
                }
            })
            .state('evaluation-template-team-detail', {
                parent: 'evaluation-template-team',
                url: '/evaluation-template/{id}',
                data: {
                    authorities: ['ROLE_LEADER'],
                    pageTitle: 'avaliacao360ZancoApp.evaluationTemplate.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evaluation-template/evaluation-template-detail.html',
                        controller: 'EvaluationTemplateDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evaluationTemplate');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EvaluationTemplate', function ($stateParams, EvaluationTemplate) {
                        return EvaluationTemplate.get({ id: $stateParams.id }).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'evaluation-template',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('evaluation-template-team-detail.edit', {
                parent: 'evaluation-template-team-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_LEADER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evaluation-template/evaluation-template-dialog.html',
                        controller: 'EvaluationTemplateDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['EvaluationTemplate', function (EvaluationTemplate) {
                                return EvaluationTemplate.get({ id: $stateParams.id }).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, { reload: false });
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('evaluation-template-team.new', {
                parent: 'evaluation-template-team',
                url: '/new',
                data: {
                    authorities: ['ROLE_LEADER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evaluation-template/evaluation-template-dialog.html',
                        controller: 'EvaluationTemplateDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('evaluation-template-team', null, { reload: 'evaluation-template-team' });
                    }, function () {
                        $state.go('evaluation-template-team');
                    });
                }]
            })
            .state('evaluation-template-team.edit', {
                parent: 'evaluation-template-team',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_LEADER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evaluation-template/evaluation-template-dialog.html',
                        controller: 'EvaluationTemplateDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['EvaluationTemplate', function (EvaluationTemplate) {
                                return EvaluationTemplate.get({ id: $stateParams.id }).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('evaluation-template-team', null, { reload: 'evaluation-template-team' });
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('evaluation-template-team.delete', {
                parent: 'evaluation-template-team',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evaluation-template/evaluation-template-delete-dialog.html',
                        controller: 'EvaluationTemplateDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['EvaluationTemplate', function (EvaluationTemplate) {
                                return EvaluationTemplate.get({ id: $stateParams.id }).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('evaluation-template-team', null, { reload: 'evaluation-template-team' });
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
