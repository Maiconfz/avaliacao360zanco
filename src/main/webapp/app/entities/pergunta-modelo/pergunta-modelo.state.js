(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pergunta-modelo', {
            parent: 'entity',
            url: '/pergunta-modelo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.perguntaModelo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pergunta-modelo/pergunta-modelos.html',
                    controller: 'PerguntaModeloController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('perguntaModelo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pergunta-modelo-detail', {
            parent: 'entity',
            url: '/pergunta-modelo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.perguntaModelo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pergunta-modelo/pergunta-modelo-detail.html',
                    controller: 'PerguntaModeloDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('perguntaModelo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PerguntaModelo', function($stateParams, PerguntaModelo) {
                    return PerguntaModelo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pergunta-modelo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pergunta-modelo-detail.edit', {
            parent: 'pergunta-modelo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pergunta-modelo/pergunta-modelo-dialog.html',
                    controller: 'PerguntaModeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PerguntaModelo', function(PerguntaModelo) {
                            return PerguntaModelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pergunta-modelo.new', {
            parent: 'pergunta-modelo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pergunta-modelo/pergunta-modelo-dialog.html',
                    controller: 'PerguntaModeloDialogController',
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
                    $state.go('pergunta-modelo', null, { reload: 'pergunta-modelo' });
                }, function() {
                    $state.go('pergunta-modelo');
                });
            }]
        })
        .state('pergunta-modelo.edit', {
            parent: 'pergunta-modelo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pergunta-modelo/pergunta-modelo-dialog.html',
                    controller: 'PerguntaModeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PerguntaModelo', function(PerguntaModelo) {
                            return PerguntaModelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pergunta-modelo', null, { reload: 'pergunta-modelo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pergunta-modelo.delete', {
            parent: 'pergunta-modelo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pergunta-modelo/pergunta-modelo-delete-dialog.html',
                    controller: 'PerguntaModeloDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PerguntaModelo', function(PerguntaModelo) {
                            return PerguntaModelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pergunta-modelo', null, { reload: 'pergunta-modelo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
