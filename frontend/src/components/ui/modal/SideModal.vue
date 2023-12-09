<template>
    <div class="side-modal" :show="show || null" @click.self="emit('close')">
        <div class="container-wr">
            <div class="close-wr">
                <div class="close sh-link" @click="emit('close')">
                    <IClose class="ico"/>
                </div>
            </div>
            <div class="container">
                <slot/>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref } from "vue"

    import IClose from "@/components/icons/IClose.vue";

    const emit = defineEmits(['close'])
    const show = ref(false)

    const call = ()=>{
        var body = document.body;
        var oldWidth = body.clientWidth+'px';
        body.style.overflow = "hidden";
        body.style.width = oldWidth;

        show.value = true;
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
    .side-modal{
        --pad: 7.6rem;
        opacity: 1;

        z-index: 1000;
        position: fixed;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        background: var(--c-black-ST);
        transition: .3s;

        .container-wr{
            padding-top: var(--pad);

            @include flex-col;

            position: absolute;
            right: 0;
            width: 100%;
            max-width: 71.4rem;
            height: 100%;
            background: var(--c-white);

            border-radius: 2.5rem 0 0 2.5rem;
            
            transition: .3s;

            .close-wr{
                padding: 0 var(--pad);
                display: flex;
                justify-content: end;
                margin-bottom: calc(var(--pad) / 2);

                .close{
                    height: 2.4rem;
                    width: 2.4rem;
                    cursor: pointer;
                    @include flex-c;

                    .ico{
                        width: 67%;
                    }
                }
            }

            .container{
                padding: 0 var(--pad) var(--pad);
                overflow-y: scroll;
                overscroll-behavior: none;
                
                height: 100%;


                &::-webkit-scrollbar{
                    &-thumb{
                        border-color: var(--c-white);
                    }

                    &-track{
                        background: var(--c-white);
                    }
                }
            }

            
        }

        &:not([show]){
            @include hidden(0);
            
            .container-wr{
                @include hidden-hor(10rem)
            }
        }

        @media (max-width: $mobile-big){
            --pad: calc(var(--generic-padding) * 1.7);

            .container-wr{
                border-radius: 0 0 2.5rem 2.5rem;
            }

            &:not([show]){
                .container-wr{
                    @include hidden(-10rem)
                }
            }
        }
    }
</style>