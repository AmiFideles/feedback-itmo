<template>
    <VModal 
        ref="modal" 
        class="l-modal" 
        @close="close"
        :style="{
            '--sh': currentColor?.color,
            '--bg': editMode?currentColor?.light:'var(--c-white)'
        }"
    >   
        <template #title>
            <div class="name-wr" :show="!fade || null">
                <h2>{{info?.mentorName}}</h2>
            </div>
        </template>

        <template v-if="!editMode">
            <div class="sender" :show="!fade || null">
                <p v-if="info?.firstName">от: {{info.firstName}} {{info.lastName}}</p>
            </div>

            <div class="content">
                <div class="loader-wr" :show="fade || null">
                    <VLoading/>
                </div>
                <div class="content-container" :show="!fade || null">
                    <p>{{err || info?.messageText}}</p>
                </div>
            </div>
        </template>

        <template v-else>
            <div class="content form" v-if="info">
                <div class="title">Ваши фамилия и имя</div>
                <div class="flex-inp-wr">
                    <VTextInput v-model="info.lastName"/>
                    <VTextInput v-model="info.firstName"/>
                </div>
                <div class="title">Ваше подразделение в ИТМО</div>
                <VTextInput v-model="info.faculty"/>
                <div class="title">Кому адресовано послание?</div>
                <VTextInput v-model="info.mentorName"/>
                <div class="title">Email адресата</div>
                <VTextInput v-model="info.mentorEmail"/>
                <div class="title">Текст послания</div>
                <VTextarea v-model="info.messageText"/>
            </div>
        </template>


        
        <VErr :err="controlsErr"/>
        <div class="controls-wr">
            <div class="controls">
                <div class="btns">
                    <VButton class="edit" @click="edit" :active="editMode || null" :loading="loading || null">
                        <IPencil v-if="!editMode"/>
                        <span v-else>✓</span>
                    </VButton>
                    <VButton class="REJECT" @click="setStatus('REJECTED')" :active="info?.status == 'REJECTED' || null" :loading="loading || null">
                        ╳
                    </VButton>
                    <VButton class="APPROVE" @click="setStatus('APPROVED')" :active="info?.status == 'APPROVED' || null" :loading="loading || null">
                        ✓
                    </VButton>
                </div>
                <SliderArrows
                    no-shrink
                    @next="move(1)"
                    @prev="move(-1)"
                />
            </div>
        </div>
    </VModal>
</template>

<script setup>
    import { computed, onMounted, ref, watch } from 'vue';
    import SliderArrows from "@/components/ui/SliderArrows.vue";

    import IPencil from "@/components/icons/IPencil.vue";

    import R from "@/stores/Router.js";
    import Static from "@/stores/Static.js";

    import { feedbackAPI } from "@/script/api/"

    const modal = ref();

    const idList = ref([]);

    const id = computed(()=>R().query?.lAdmin);

// list
    const loop = ref(false);
    const offset = ref(0);

    const updateList = async (v=1)=>{
        let res = await feedbackAPI.getList(
            R().route.name == 'AAllList'?
                'all':
                'notModerated',
            offset.value, 
            15, 
            R().parseQuery('filters')
        );

        let list = res.content;

        offset.value++;

        if(
            res.totalPages < offset.value || 
            !list || 
            !list.length ||
            (offset == 1 && idList.length == 1 && list.length == 1)
        ){
            console.log('loop');
            loop.value = true;
            return false;
        }

        idList.value[v == 1?'push':'unshift'](...res.content.map(e => e.id+""));
        idList.value = [...new Set(idList.value)]
        return true;
    }

// info
    const info = ref();
    const fade = ref();
    const err = ref('');

    const clearInfo = ()=>{
        fade.value = true;
        return new Promise(
            (res)=>setTimeout(
                ()=>{
                    loading.value = false;
                    controlsErr.value = '';
                    info.value = null;
                    res(true);
                }, 
                101
            ));
    } 

    const updateInfo = async ()=>{
        await clearInfo();
        err.value = '';

        info.value = await feedbackAPI.get(id.value).catch(
            (error) => {
                console.log('!',error)
                err.value = error.message || error
            }
        );

        console.log(info.value)

        currentColor.value = Static().getColor(info.value?.color || 1)

        setTimeout(()=>fade.value = false);
    }

    const init = (n,o)=>{
        // console.log(n,o);
        editMode.value = false;

        if(o && n){
            updateInfo();
            return;
        };

        if(n){
            modal.value.call();
            updateInfo();

            loop.value = false;
            offset.value = 0;

            if(R().query?.larr){
                idList.value = R().query.larr.split(';');
                offset.value = parseInt(R().query.offset) || 0;
                console.log(
                    idList.value,
                    offset.value
                )
                R().pushQuery({lAdmin: n, larr: null, offset: null});
            }else{
                idList.value = [n];
            }
        }else{
            modal.value.close()
        }
    }

    onMounted(()=>{
        init(id.value);
    })

    watch(id, init);

// move 
    const move = async (v=1)=>{
        if(fade.value)return;

        await clearInfo();

        let localId = idList.value.indexOf(id.value);

        if(localId+v == -1 || localId+v == idList.value.length){
            if(loop.value){
                R().pushQuery({lAdmin: idList.value[v == 1?0:idList.value.length -1]});
                if(idList.value.length == 1)updateInfo();
                return;
            }

            if(!await updateList(v)){
                R().pushQuery({lAdmin: idList.value[v == 1?0:idList.value.length -1]});
                updateInfo();
                return;
            }
        }

        localId = idList.value.indexOf(id.value);
        
        R().pushQuery({lAdmin: idList.value[localId == null?0:localId + v]});
    }

//color
    const currentColor = ref();

//admin
    const loading = ref();
    const controlsErr = ref();

    const setStatus = async (status)=>{
        loading.value = true;
        controlsErr.value = '';

        let toSend = Object.assign(
            {}, 
            info.value, 
            {status},
            {
                color: null,
                dateTime: null,
                graduationYear: null,
                photoURL: null,
            }
        )

        await feedbackAPI
            .change(toSend)
            .catch(error => controlsErr.value = error.message || error);
        
        if(!controlsErr.value){
            info.value.status = status;
        }

        loading.value = false;
    }

//edit
    const editMode = ref(false);

    // const editBcp = ref();

    // watch(editMode, (n)=>{
    //     if(n)editBcp.value = JSON.parse(JSON.stringify(info.value));
    // });

    const edit = async ()=>{
        editMode.value = !editMode.value;

        if(editMode.value)return;

        loading.value = true;
        controlsErr.value = '';

        let toSend = Object.assign(
            {}, 
            info.value,
            {
                color: null,
                dateTime: null,
                graduationYear: null,
                photoURL: null,
            }
        )

        await feedbackAPI
            .change(toSend)
            .catch(error => controlsErr.value = error.message || error);
        
        if(!controlsErr.value){
            editMode.value = false
        }

        loading.value = false;
    }

//close
    const close = ()=>{
        R().pushQuery({l: null, lAdmin: null});
        
        fade.value = true;
        setTimeout(()=>{
            fade.value = false;
            idList.value = []; 
        }, 301)
    }
</script>

<style lang="scss" scoped>
    .name-wr{
        position: relative;
        min-width: 0;
        padding-bottom: .8rem;
        min-height: 3.4rem;

        &::after{
            @include pseudo-absolute;
            left: 0;
            bottom: 0;
            width: calc(100% + 1.6rem);
            height: .1rem;
            background: var(--c-black);
        }

        h2{
            font-size: 2.4rem;
            font-weight: 400;
            @include text-overflow;
            min-height: 1em;
        }
    }
    
    .sender{
        min-width: 0;
        padding-top: .8rem;
        min-height: 1.75em;

        p{
            @include text-overflow;
        }
    }

    .modal.l-modal :deep(.container){
        height: 100vh;
        max-height: 90vh;
    }

    .content{
        height: 100%;
        overflow-y: auto;
        overflow-x: hidden;
        margin: 2.4rem 0;
        position: relative;

        &::-webkit-scrollbar{
            &-thumb{
                border-color: var(--c-white);
            }

            &-track{
                background: var(--c-white);
            }
        }

        .loader-wr{
            position: absolute;
            pointer-events: none;
            @include all-directions(0);
            margin: auto;
            width: 6.4rem;
            height: 6.4rem;
        }

        p{
            white-space: pre-wrap;
        }
    }

    .form{
        @include flex-col;
        gap: 1.6rem;

        padding-right: .8rem;

        .input-wr, textarea{
            flex-shrink: 0;
            width: 100%;
        }

        .title{
            color: var(--c-grey-dark);
            font-size: 1.4rem;
            margin-bottom: -.8rem;
        }

        .flex-inp-wr{
            display: flex;
            gap: 1.6rem;

            .input-wr{
                flex-shrink: 1;
                width: 100%;
            }
        }  
    }

    .controls-wr{
        @include  flex-c;

        .controls{
            position: relative;
            padding: 0 2rem;
            width: max-content;

            .btns{
                display: flex;
                gap: 2rem;

                .btn{
                    padding: 0;
                    width: 4.8rem;
                    height: 4.8rem;
                    border-radius: 50%;
                    
                    --color: transparent;

                    border: .2rem solid var(--color);

                    background: var(--c-white);

                    &[active]{
                        background: var(--color);
                    }

                    &.REJECT{
                        --color: #ff4642;
                        font-weight: 600;
                    }
                    &.APPROVE{
                        --color: #adff59;
                        font-size: 2.8rem;
                    }
                    &.edit{
                        --color: #437aff;
                        color: var(--color);
                        font-size: 2.8rem;

                        svg{
                            height: 75%;
                        }

                        &[active]{
                            color: var(--c-white);
                        }
                    }
                }
            }

            
        }
    }

    .name-wr,
    .loader-wr,
    .content-container,
    .sender{
        transition: .1s;

        &:not([show]){
            opacity: 0;
        }
    }

    @media (max-width: $mobile-big){
        .slider-arrows{
            :deep(.arrow){
                &.prev{
                    right: calc(100% - 1rem);
                }

                &.next{
                    left: calc(100% - 1rem);
                }
            }
        }
    }

</style>