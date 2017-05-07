(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pergunta', {
            parent: 'entity',
            url: '/pergunta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.pergunta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pergunta/perguntas.html',
                    controller: 'PerguntaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pergunta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pergunta-detail', {
            parent: 'entity',
            url: '/pergunta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.pergunta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pergunta/pergunta-detail.html',
                    controller: 'PerguntaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pergunta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pergunta', function($stateParams, Pergunta) {
                    return Pergunta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pergunta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pergunta-detail.edit', {
            parent: 'pergunta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pergunta/pergunta-dialog.html',
                    controller: 'PerguntaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pergunta', function(Pergunta) {
                            return Pergunta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pergunta.new', {
            parent: 'pergunta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pergunta/pergunta-dialog.html',
                    controller: 'PerguntaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                assunto: null,
                                texto: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pergunta', null, { reload: 'pergunta' });
                }, function() {
                    $state.go('pergunta');
                });
            }]
        })
        .state('pergunta.edit', {
            parent: 'pergunta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pergunta/pergunta-dialog.html',
                    controller: 'PerguntaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pergunta', function(Pergunta) {
                            return Pergunta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pergunta', null, { reload: 'pergunta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pergunta.delete', {
            parent: 'pergunta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pergunta/pergunta-delete-dialog.html',
                    controller: 'PerguntaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pergunta', function(Pergunta) {
                            return Pergunta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pergunta', null, { reload: 'pergunta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
