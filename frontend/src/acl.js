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
        isAdmin: new AclRule('ROLE_Administrator').generate(),
        isManager: new AclRule('ROLE_Manager').or('ROLE_Administrator').or('ROLE_Director').generate(),
        isLoggedUser: new AclRule('ROLE_Employee').or('ROLE_Manager').or('ROLE_Administrator').or('ROLE_Director').generate(),
        isPublic: new AclRule('public').generate()
    }
});