import { defineStore } from 'pinia';
import { ref, computed, reactive, onMounted, watch } from "vue";

import { getCookie, setCookie, deleteCookie } from "@/script/cookie.js";

import R from "@/stores/Router.js";

import { adminAPI } from "@/script/api";
 
export default defineStore("Admin", ()=>{
    const isLogged = ref(null);

    const checkLogin = ()=>{
        if(!getCookie('refreshToken')){
            isLogged.value = false;
            return
        }

        refresh();
    }

    onMounted(()=>{
        setTimeout(()=>{
            if(R().meta.admin)checkLogin();
        });
    });
    watch(R().meta?.admin, ()=>{
        if(R().meta.admin)checkLogin();
    });

    const refresh = async ()=>{
        let err = false;

        console.log(adminAPI);

        let resp = await adminAPI.refresh(getCookie('refreshToken')).catch(
            e => err = true
        );
            
        if(!err){
            setCookie('accessToken', resp.accessToken, {'max-age': 2678400000});
            setCookie('refreshToken', resp.refreshToken, {'max-age': 2678400000});
            isLogged.value = true;
        }else{
            exit();
        }

        return !err;
    }

    const login = async (
        username,
        password
    )=>{
        let err = null;

        let resp = await adminAPI.login(
            username,
            password
        ).catch(
            error => err = error 
        );

        if(err)return err;

        setCookie('accessToken', resp.accessToken, {'max-age': 2678400000});
        setCookie('refreshToken', resp.refreshToken, {'max-age': 2678400000});
        isLogged = true;
    }

    const exit = ()=>{
        isLogged.value = false;

        adminAPI.exit();

        deleteCookie('accessToken');
        deleteCookie('refreshToken');
    }

// ----------------------------------------------------------------
return {
    isLogged,
    checkLogin,
    login,
    exit
}
});