<template>
    <VModal 
        ref="modal" 
        class="l-modal" 
        closeNative
    >
        <template #title>
            <div class="name-wr" :show="!fade || null">
                <h2>{{info?.comment}}</h2>
            </div>
        </template>
        <div class="content">
            <div class="content-container" :show="!fade || null">
                <p>{{info?.contact}}</p>
            </div>
        </div>
    </VModal>
</template>

<script setup>
    import { computed, onMounted, ref, watch } from 'vue';
    import SliderArrows from "@/components/ui/SliderArrows.vue";

    import R from "@/stores/Router.js";
    import Static from "@/stores/Static.js";

    const modal = ref();

    const info = ref({});

    const call = (i)=>{
        info.value = i;
        modal.value.call();
    };

    defineExpose({call});
</script>

<style lang="scss" scoped>
    .name-wr{
        position: relative;
        min-width: 0;
        padding-bottom: .8rem;

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
    .content-container{
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