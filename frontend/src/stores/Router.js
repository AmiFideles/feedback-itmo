import { defineStore } from 'pinia';
import { ref, computed, reactive, onMounted, watch } from "vue";
import { useRouter, useRoute } from "vue-router";


export default defineStore("R", ()=>{

    const route = useRoute();
    const router = useRouter();

    const push = (r)=>router.push(r);
    const back = (r)=>router.back(r);
    const resolve = (r)=>router.resolve(r);

    const meta = computed(()=>route.meta);
    const query = computed(()=>route.query);
    const params = computed(()=>route.params);

    const history = computed(()=>router.options.history.state);

    const pushQuery = (q)=>{
        // console.log(q);
        // console.trace();

        let res = Object.assign(
            {},
            query.value,
            q
        )

        res = Object.fromEntries(Object.entries(res).filter(([_, v]) => v != null));

        push({query: res});
    }

    const parseQuery = (key)=>{
        if(!query.value[key])return null;
        return Object.fromEntries(query.value[key].split(';').map(e => e.split('=')))
    }

//----------------------------------------------------------------
    return {
        route,
        router,

        push,
        back,
        resolve,

        pushQuery,
        parseQuery,

        meta,
        query,
        params,

        history,
    }
})