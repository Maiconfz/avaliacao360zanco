(function() {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('equipe', {
            parent: 'entity',
            url: '/equipe',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.equipe.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/equipe/equipes.html',
                    controller: 'EquipeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('equipe');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('equipe-detail', {
            parent: 'entity',
            url: '/equipe/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ZancoApp.equipe.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/equipe/equipe-detail.html',
                    controller: 'EquipeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('equipe');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Equipe', function($stateParams, Equipe) {
                    return Equipe.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'equipe',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('equipe-detail.edit', {
            parent: 'equipe-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equipe/equipe-dialog.html',
                    controller: 'EquipeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Equipe', function(Equipe) {
                            return Equipe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('equipe.new', {
            parent: 'equipe',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equipe/equipe-dialog.html',
                    controller: 'EquipeDialogController',
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
                    $state.go('equipe', null, { reload: 'equipe' });
                }, function() {
                    $state.go('equipe');
                });
            }]
        })
        .state('equipe.edit', {
            parent: 'equipe',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equipe/equipe-dialog.html',
                    controller: 'EquipeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Equipe', function(Equipe) {
                            return Equipe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('equipe', null, { reload: 'equipe' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('equipe.delete', {
            parent: 'equipe',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equipe/equipe-delete-dialog.html',
                    controller: 'EquipeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Equipe', function(Equipe) {
                            return Equipe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('equipe', null, { reload: 'equipe' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
