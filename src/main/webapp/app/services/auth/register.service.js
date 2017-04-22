(function () {
    'use strict';

    angular
        .module('avaliacao360ZancoApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
