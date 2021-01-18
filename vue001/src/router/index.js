import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/module/login'
import main from '@/components/module/main'
import roomquery from '@/components/module/roomquery'
import checkin from '@/components/module/checkin'
import checkout from '@/components/module/checkout'
import roomop from '@/components/module/roomop'
import hotelreportop from '@/components/module/report1'
import addhotel from '@/components/module/addhotel'
import addstaff from '@/components/module/addstaff'
import staffhotel from '@/components/module/staffhotel'
import areareport from '@/components/module/report2'
Vue.use(Router)

const router = new Router({
    routes: [{
            path: '/',
            redirect: '/login'
        },
        {
            path: '/login',
            name: 'Login',
            component: login
        },
        {
            path: '/Main',
            name: 'Main',
            component: main,
            children: [{ path: '/roomquery', component: roomquery }, { path: '/checkin', component: checkin },
                { path: '/checkout', component: checkout }, { path: '/checkout', component: checkout },
                { path: '/roomop', component: roomop }, { path: '/hotelreportop', component: hotelreportop },
                { path: '/addhotel', component: addhotel }, { path: '/addstaff', component: addstaff }, { path: '/staffhotel', component: staffhotel },
                { path: '/printreportop', component: areareport }
            ]
        }
    ]
})

router.beforeEach((to, from, next) => {
    if (to.path === '/login') return next()
    const tokenstr = window.sessionStorage.getItem('token')
    if (!tokenstr) return next('/login')
    next()
})
export default router