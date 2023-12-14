import { sendAPI } from ".";

export default {
    add(comment, contact){
        return sendAPI(
            'post',
            `/site-review/`,
            {comment, contact}
        )
    },
    getList(){
        return sendAPI(
            'get',
            `/site-review/`,
        )
    },
}