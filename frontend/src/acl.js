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
        isAdmin: new AclRule('Administrator').generate(),
        isManager: new AclRule('Manager').or('Administrator').or('Director').generate(),
        isLoggedUser: new AclRule('Employee').or('Manager').or('Administrator').or('Director').generate(),
        isPublic: new AclRule('public').generate()
    }
});
