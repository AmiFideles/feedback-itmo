import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/views/Home.vue';
import List from '@/views/List.vue';

import Admin from '@/views/Admin.vue';

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
        path: '/admin',
        name: 'Admin',
        component: Admin,
        meta: {admin: true}
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
