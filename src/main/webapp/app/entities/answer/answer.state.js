(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('answer', {
            parent: 'entity',
            url: '/answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.answer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/answer/answers.html',
                    controller: 'AnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('answer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('answer-detail', {
            parent: 'answer',
            url: '/answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.answer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/answer/answer-detail.html',
                    controller: 'AnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('answer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Answer', function($stateParams, Answer) {
                    return Answer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('answer-detail.edit', {
            parent: 'answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/answer/answer-dialog.html',
                    controller: 'AnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Answer', function(Answer) {
                            return Answer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('answer.new', {
            parent: 'answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/answer/answer-dialog.html',
                    controller: 'AnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nota: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('answer', null, { reload: 'answer' });
                }, function() {
                    $state.go('answer');
                });
            }]
        })
        .state('answer.edit', {
            parent: 'answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/answer/answer-dialog.html',
                    controller: 'AnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Answer', function(Answer) {
                            return Answer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('answer', null, { reload: 'answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('answer.delete', {
            parent: 'answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/answer/answer-delete-dialog.html',
                    controller: 'AnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Answer', function(Answer) {
                            return Answer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('answer', null, { reload: 'answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
