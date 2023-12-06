<template>
    <div class="modal" :show="show || null">
        <div class="container-wr" @mousedown.self="emit('close')">
            <div class="container">
                <div class="title-wr">
                    <div class="title">
                        <slot name="title"/>
                    </div>
                    <div class="close sh-link" @click="emit('close')">
                        <IClose class="ico"/>
                    </div>
                </div>
                
                <slot/>
            </div>
        </div>
        
    </div>
</template>

<script setup>
    import { ref } from "vue";

    import IClose from "@/components/icons/IClose.vue";

    const emit = defineEmits(['close', 'call'])
    const show = ref(false)

    const call = ()=>{
        var body = document.body;
        var oldWidth = body.clientWidth+'px';
        body.style.overflow = "hidden";
        body.style.width = oldWidth;

        show.value = true;

        emit('call');
    }

    const close = ()=>{
        var body = document.body;
        body.style.overflow = null;
        body.style.width=null;

        show.value = false;
    }

    defineExpose({
        call,
        close
    });
</script>

<style lang="scss" scoped>
    .modal{
        --pad: 4.5rem;
        --sh: #DAF667;
        --bg: var(--c-white);

        opacity: 1;

        z-index: 1001;
        position: fixed;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        background: var(--c-black-ST);
        transition: .3s;
        padding: 5vh 0;
        overflow-y: auto;

        .container-wr{
            height: max-content;
            min-height: 100%;
            width: 100%;
            @include flex-c;
        }

        .container{
            padding: var(--pad);

            @include flex-col;

            width: 100%;
            max-width: 71.4rem;
            max-height: 90vh;
            background: var(--bg);
            height: max-content;

            border-radius: 2.5rem;
            
            filter: drop-shadow(1rem 1rem 0 var(--sh));
            
            transition: .3s;

            .title-wr{
                @include flex-jtf;
                gap: 2rem;

                .title{
                    padding-top: .2rem;
                    min-width: 0;
                }
            }

            .close{
                height: 2.4rem;
                width: 2.4rem;
                cursor: pointer;
                @include flex-c;
                flex-shrink: 0;

                .ico{
                    width: 67%;
                }
            }       
        }

        &:not([show]){
            pointer-events: none;
            opacity: 0;
            
            .container{
                @include hidden(10rem);
            }
        }
    }
</style>