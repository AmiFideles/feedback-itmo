import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/views/Home.vue';
import List from '@/views/List.vue';

import Admin from '@/views/admin/Admin.vue';
import AFeedback from '@/views/admin/AFeedback.vue';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/list',
        name: 'List',
        component: List
    },
    {
        path: '/:catchAll(.*)',
        redirect: to => {
            return {name: "Home"}
        },
    },

    

    //admin
    {
        path: '/admin',
        name: 'Admin',
        component: Admin,
        meta: {admin: true, nest: 'Admin'}
    },
    {
        path: '/admin/all',
        name: 'AAllList',
        component: Admin,
        meta: {admin: true, nest: 'Admin'}
    },
    {
        path: '/admin/feedback',
        name: 'AFeedback',
        component: AFeedback,
        meta: {admin: true, nest: 'AFeedback'}
    },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if(to.name != from.name)document.body.scrollTo({
        top: 0,
        left: 0,
        // behavior: 'instant',
    });
  },
})

export default router
