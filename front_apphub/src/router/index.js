import Vue from 'vue'
import VueRouter from 'vue-router'
// Enter Login component
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import Index from '../components/Index.vue'
import Signup from '../components/Signup.vue'
import FoundPassword from '../components/FoundPassword.vue'
import ResetPassword from '../components/ResetPassword.vue'
import Welcome from '../components/Welcome.vue'
import ImageList from '../components/ImageList.vue'
import Detailed from '../components/Detailed.vue'
import Recipedetail from '../components/Recipedetail.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/signup',
        component: Signup
    },
    {
        path: '/',
        //component: Index,
        // redirect: '/login'
        redirect: '/home'
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/home',
        component: Home,
        redirect: '/image/public',
        children: [
            {path: '/image/public', component: ImageList},
            {path: '/image/detailed', component: Detailed},
            {path: '/image/Welcome', component: Welcome},
            {path: '/image/Recipedetail', component: Recipedetail},
        ]
    },

    {
        path: '/foundPassword',
        component: FoundPassword
    },
    {
        path: '/resetPassword',
        component: ResetPassword
    },
]

const router = new VueRouter({
    mode: 'history',
    routes
})


router.beforeEach((to, from, next) => {
 
    if(to.path=='/login') return next();
    if(to.path=='/') return next();
    if(to.path=='/signup') return next();
    if(to.path=='/foundPassword') return next();
    if(to.path=='/resetPassword') return next();
    const token = window.sessionStorage.getItem('token');
    if(!token) return next("/login"); 
    next(); 
    
})

export default router
