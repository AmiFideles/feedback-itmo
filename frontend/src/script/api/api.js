import axios from 'axios';
import { getCookie } from '@/script/cookie.js';

import A from "@/stores/Admin.js";

const api = import.meta.env.VITE_API;

let prevStatus = null;

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
        let prevPrevStatus = prevStatus;
        await axios(body).then(
            (res)=>{
                prevStatus = res.status;
                resolve(res.data)
            }
        ).catch(
            async (err)=>{
                prevStatus = err?.response?.status;
                if(err?.response?.status == 401){
                    if(err?.response?.data?.error != "Unauthorized" || prevPrevStatus == 401){
                        A().exit();
                        reject(err.response.data || err);
                        
                        return;
                    }
                    
                    await A().refresh();
                    resolve(await sendAPI(method, url, data, options));
                    return;
                }

                reject(err.response?.data || err);
            }
        );
    })
}