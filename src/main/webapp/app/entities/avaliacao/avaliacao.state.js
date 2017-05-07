(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('avaliacao', {
            parent: 'entity',
            url: '/avaliacao',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.avaliacao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avaliacao/avaliacaos.html',
                    controller: 'AvaliacaoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avaliacao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('avaliacao-detail', {
            parent: 'entity',
            url: '/avaliacao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.avaliacao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avaliacao/avaliacao-detail.html',
                    controller: 'AvaliacaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avaliacao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Avaliacao', function($stateParams, Avaliacao) {
                    return Avaliacao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'avaliacao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('avaliacao-detail.edit', {
            parent: 'avaliacao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao/avaliacao-dialog.html',
                    controller: 'AvaliacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Avaliacao', function(Avaliacao) {
                            return Avaliacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avaliacao.new', {
            parent: 'avaliacao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao/avaliacao-dialog.html',
                    controller: 'AvaliacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('avaliacao', null, { reload: 'avaliacao' });
                }, function() {
                    $state.go('avaliacao');
                });
            }]
        })
        .state('avaliacao.edit', {
            parent: 'avaliacao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao/avaliacao-dialog.html',
                    controller: 'AvaliacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Avaliacao', function(Avaliacao) {
                            return Avaliacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avaliacao', null, { reload: 'avaliacao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avaliacao.delete', {
            parent: 'avaliacao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao/avaliacao-delete-dialog.html',
                    controller: 'AvaliacaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Avaliacao', function(Avaliacao) {
                            return Avaliacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avaliacao', null, { reload: 'avaliacao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
