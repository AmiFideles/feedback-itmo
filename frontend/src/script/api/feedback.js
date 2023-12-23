import { sendAPI } from ".";

export default {
    get(id){
        return sendAPI(
            'get',
            `/feedback/${id}`
        )
    },
    getList(
        type /*approved, notModerated, all*/, 
        page, 
        size, 
        filters={}, /* graduationYear, faculty */
        sort,
    ){
        return sendAPI(
            'get',
            `/${type == 'approved'?'feedback':'moderator'}/${type}`,
            Object.assign(
                {
                    page,
                    size,
                    sort
                },
                filters
            )
        )
    },
    getCount(){
        return sendAPI(
            'get',
            `/feedback/count/`
        )
    },
    add(data){
        return sendAPI(
            'post',
            `/feedback/add`,
            data
        )
    },
    change(data){
        return sendAPI(
            'put',
            `/moderator/feedback/`,
            data,
            //{query: true}
        )
    },
}