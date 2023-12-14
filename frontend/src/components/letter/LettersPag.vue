<template>
    <div class="letters">
        <div class="letters-container">
            <LCard v-for="(i,k) in list" :key="k" :info="i" :list="list" :offset="offset" :admin="admin"/>
        </div>

        <VErr :err="err"/>
        <div class="loader" v-if="!stop"><VLoading/></div>
        <h3 v-else-if="!err && !list.length">Список пуст</h3>
    </div>
</template>

<script setup>
    import LCard from "@/components/letter/LCard.vue";

    import R from "@/stores/Router.js";

    import { feedbackAPI } from "@/script/api";
    
    import { onMounted, onUnmounted, ref, watch } from "vue";

    const props = defineProps({
        admin: Boolean
    });

    const list = ref([]);

    const offset = ref(0);
    const pause = ref(false);
    const stop = ref(false);

    const err = ref('');

    const reset = ()=>{
        offset.value = 0;
        pause.value = false;
        stop.value = false;
        list.value = []; 
        checkPag();
    }

    onMounted(()=>{
        reset();
    });
    watch(()=>R().query.filters, reset);
    watch(()=>R().route.name, reset);

    onMounted(()=>{(document.body).addEventListener('scroll', checkPag);});
    onUnmounted(()=>(document.body).removeEventListener('scroll', checkPag));


    const paginate = async ()=>{
        console.log(offset.value);

        err.value = '';

        let res = await feedbackAPI.getList(
            !props.admin?
                'approved':
            R().route.name == 'AAllList'?
                'all':
                'notModerated',
            offset.value, 
            15, 
            R().parseQuery('filters')
        ).catch(error => {
            err.value = error;
        });

        if(err.value){
            stop.value = true;
            return;
        }

        offset.value++;
        list.value.push(...res.content);

        if(res.totalPages <= offset.value){
            stop.value = true;
            return;
        }

        console.log(offset.value);
        
        setTimeout(()=>{
            pause.value = false;
            checkPag();
        });
    }

    const checkPag = ()=>{
        console.log(pause.value, stop.value)
        if(
            !pause.value && 
            !stop.value && 
            (document.body.scrollTop + document.body.clientHeight > document.body.scrollHeight - 600)
        ){
            pause.value = true;
            paginate();
        }
    }

    defineExpose({reset})


</script>

<style lang="scss" scoped>
    .letters{
        margin-bottom: 4.8rem;
    }

    .letters-container{
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 4.8rem;
        padding-bottom: 4.8rem;

        .card{
            width: 100%;
        }

        @media screen and (max-width: $pad) {
            grid-template-columns: repeat(2, 1fr);

            gap: var(--generic-padding);
        }

        @media screen and (max-width: $mobile-med) {
            grid-template-columns: repeat(1, 1fr);
        }
    }

    .loader{
        width: 100%;
        @include flex-c;

        :deep(svg){
            height: 1.8rem;
        }
    }

    h3{
        width: 100%;
        text-align: center;
    }

    p[err]{
        padding: 0 4.8rem;
    }



</style>