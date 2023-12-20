<template>
    <VModal 
        ref="modal" 
        class="l-modal" 
        @close="close"
        :style="{
            '--sh': currentColor?.color
        }"
    >
        <template #title>
            <div class="name-wr" :show="!fade || null">
                <h2>{{info?.mentorName}}</h2>
            </div>
        </template>

        
        <div class="sender" :show="!fade || null">
            <p class="letter-text" v-if="info?.firstName">от: {{info.firstName}} {{info.lastName}}</p>
        </div>

        <div class="content">
            <div class="loader-wr" :show="fade || null">
                <VLoading/>
            </div>
            <div class="content-container" :show="!fade || null">
                <p class="letter-text">{{err || info?.messageText}}</p>
            </div>
        </div>
        <div class="controls-wr">
            <div class="controls">
                <VButton 
                    @click="R().pushQuery({lsend: 'true'})"
                    :style="{
                        '--bg': currentColor?.color,
                        '--hov': currentColor?.light
                    }"
                >Оставить послание</VButton>
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

    import R from "@/stores/Router.js";
    import Static from "@/stores/Static.js";

    import { feedbackAPI } from "@/script/api/"

    const modal = ref();

    const idList = ref([]);

    const id = computed(()=>R().query?.l);

// list
    const loop = ref(false);
    const offset = ref(0);

    const updateList = async (v=1)=>{
        let res = await feedbackAPI.getList(
            'approved',
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
                    info.value = null;
                    res(true);
                }, 
                111
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

        currentColor.value = Static().getColor(info.value?.color || 1)

        setTimeout(()=>fade.value = false);
    }

    const init = (n,o)=>{
        // console.log(n,o);
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
                R().pushQuery({l: n, larr: null, offset: null});
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
                R().pushQuery({l: idList.value[v == 1?0:idList.value.length -1]});
                if(idList.value.length == 1)updateInfo();
                return;
            }

            if(!await updateList(v)){
                R().pushQuery({l: idList.value[v == 1?0:idList.value.length -1]});
                updateInfo();
                return;
            }
        }

        localId = idList.value.indexOf(id.value);
        
        R().pushQuery({l: idList.value[localId == null?0:localId + v]});
    }

//color
    const currentColor = ref();

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
        max-height: 70rem;
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

                    background: transparent;

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