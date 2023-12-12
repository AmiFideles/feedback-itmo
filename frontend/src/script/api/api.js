import axios from 'axios';
import { getCookie } from '@/script/cookie.js';

import A from "@/stores/Admin.js";

const api = import.meta.env.VITE_API;

export const sendAPI = (method, url, data, options)=>{
    if(data && data.token != null)data.token = getCookie('token');

    let body = {
        method,
        url: api + url,
        headers: {
            'authorization': getCookie('accessToken') && 'Bearer ' + getCookie('accessToken'),
        }
    }

    if(method.toLowerCase() == 'get'){
        body.params = data;
    }else{
        body.data = data;
    }

    return new Promise(async (resolve, reject)=>{
        await axios(body).then(
            (res)=>resolve(res.data)
        ).catch(
            async (err)=>{
                if(err?.response?.status == 401){
                    if(err?.response?.data?.message === "Invalid refresh token"){
                        A().exit();
                        reject(err.response.data || err);
                        return;
                    }
                    await A().refresh();
                    return sendAPI(method, url, data, options);
                }
                reject(err.response.data || err);
            }
        );
    })
}