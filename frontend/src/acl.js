import Vue from 'vue'
import {AclCreate, AclInstaller, AclRule} from 'vue-acl'
import router from './router'

Vue.use(AclInstaller);

export default new AclCreate({
    initial: 'public',
    // notfound: '/error',
    router,
    acceptLocalRules: true,
    globalRules: {
        isAdmin: new AclRule('ROLE_ADMIN').generate(),
        isManager: new AclRule('ROLE_MANAGER').or('ROLE_ADMIN').generate(),
        isLoggedUser: new AclRule('ROLE_USER').or('ROLE_MANAGER').or('ROLE_ADMIN').generate(),
        isPublic: new AclRule('public').generate()
    }
});