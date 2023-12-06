import { sendAPI } from ".";

export default{
    getList(){
        return sendAPI(
            'get',
            '/color'
        )
    }
}

const func = ()=>{
    return new Promise((resolve)=>{
        setTimeout(()=>{
            resolve(123)
        }, 100);
    });
}


