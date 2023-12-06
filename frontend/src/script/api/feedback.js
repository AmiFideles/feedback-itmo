import { sendAPI } from ".";

export default {
    get(id){
        return sendAPI(
            'get',
            `/feedback/${id}`
        )
    },
    getList(page, size, filters={} /* graduationYear, faculty */){
        return sendAPI(
            'get',
            '/feedback/approved',
            Object.assign(
                {
                    page,
                    size,
                },
                filters
            )
        )
    },
    add(data){
        return sendAPI(
            'post',
            `/feedback/add`,
            data
        )
    },
}