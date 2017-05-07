(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('avaliacao-controle', {
            parent: 'entity',
            url: '/avaliacao-controle',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.avaliacaoControle.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avaliacao-controle/avaliacao-controles.html',
                    controller: 'AvaliacaoControleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avaliacaoControle');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('avaliacao-controle-detail', {
            parent: 'entity',
            url: '/avaliacao-controle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.avaliacaoControle.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avaliacao-controle/avaliacao-controle-detail.html',
                    controller: 'AvaliacaoControleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avaliacaoControle');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AvaliacaoControle', function($stateParams, AvaliacaoControle) {
                    return AvaliacaoControle.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'avaliacao-controle',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('avaliacao-controle-detail.edit', {
            parent: 'avaliacao-controle-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao-controle/avaliacao-controle-dialog.html',
                    controller: 'AvaliacaoControleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AvaliacaoControle', function(AvaliacaoControle) {
                            return AvaliacaoControle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avaliacao-controle.new', {
            parent: 'avaliacao-controle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao-controle/avaliacao-controle-dialog.html',
                    controller: 'AvaliacaoControleDialogController',
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
                    $state.go('avaliacao-controle', null, { reload: 'avaliacao-controle' });
                }, function() {
                    $state.go('avaliacao-controle');
                });
            }]
        })
        .state('avaliacao-controle.edit', {
            parent: 'avaliacao-controle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao-controle/avaliacao-controle-dialog.html',
                    controller: 'AvaliacaoControleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AvaliacaoControle', function(AvaliacaoControle) {
                            return AvaliacaoControle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avaliacao-controle', null, { reload: 'avaliacao-controle' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avaliacao-controle.delete', {
            parent: 'avaliacao-controle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao-controle/avaliacao-controle-delete-dialog.html',
                    controller: 'AvaliacaoControleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AvaliacaoControle', function(AvaliacaoControle) {
                            return AvaliacaoControle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avaliacao-controle', null, { reload: 'avaliacao-controle' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
