(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('question-template', {
            parent: 'entity',
            url: '/question-template',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.questionTemplate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-template/question-templates.html',
                    controller: 'QuestionTemplateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionTemplate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('question-template-detail', {
            parent: 'question-template',
            url: '/question-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.questionTemplate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-template/question-template-detail.html',
                    controller: 'QuestionTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionTemplate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'QuestionTemplate', function($stateParams, QuestionTemplate) {
                    return QuestionTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'question-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('question-template-detail.edit', {
            parent: 'question-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-template/question-template-dialog.html',
                    controller: 'QuestionTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionTemplate', function(QuestionTemplate) {
                            return QuestionTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-template.new', {
            parent: 'question-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-template/question-template-dialog.html',
                    controller: 'QuestionTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subject: null,
                                text: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('question-template', null, { reload: 'question-template' });
                }, function() {
                    $state.go('question-template');
                });
            }]
        })
        .state('question-template.edit', {
            parent: 'question-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-template/question-template-dialog.html',
                    controller: 'QuestionTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionTemplate', function(QuestionTemplate) {
                            return QuestionTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-template', null, { reload: 'question-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-template.delete', {
            parent: 'question-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-template/question-template-delete-dialog.html',
                    controller: 'QuestionTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QuestionTemplate', function(QuestionTemplate) {
                            return QuestionTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-template', null, { reload: 'question-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
