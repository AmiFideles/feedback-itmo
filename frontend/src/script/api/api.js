import axios from 'axios';
import { getCookie } from '@/script/cookie.js';

const api = import.meta.env.VITE_API;

export const sendAPI = (method, url, data, options)=>{
    if(data && data.token != null)data.token = getCookie('token');

    let body = {
        method,
        url: api + url,
        // headers: {
        //     'authorization': getCookie('token') && 'Bearer ' + getCookie('token'),
        // }
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
            (err)=>{
                reject(err.response.data || err);
            }
        );
    })
}