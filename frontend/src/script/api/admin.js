import { sendAPI } from ".";

export default {
    refresh(
        refreshToken
    ){
        return sendAPI(
            'POST',
            '/auth/access-token',
            {refreshToken}
        )  
    },
    login(
        username,
        password
    ){
        return sendAPI(
            'POST',
            '/auth/login',
            {
                username,
                password
            }
        )  
    },
    exit(){
        // return sendAPI(
        //     'POST',
        //     '/auth/access-token',
        //     {refreshToken}
        // )  
    },
}