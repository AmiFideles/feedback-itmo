<template>
    <div class="card" v-if="info">
        <div class="title">
            <p>{{info.contact}}</p>
        </div>
        <div class="body">
            <p>{{info.comment}}</p>
        </div>
        <div class="link">
            <div class="spacer"></div>
            <a class="sh-link" @click="go">Читать полностью</a>
        </div>
    </div>
</template>

<script setup>
    import R from "@/stores/Router.js";
    import Static from "@/stores/Static.js";
    
    import { computed, ref } from "vue";

    const props = defineProps({
        info: Object,
    })

    const emit = defineEmits(['call']);

    const go = ()=>{
        emit('call')
    }
</script>

<style lang="scss" scoped>
    .card{
        --sh: #DAF667;

        background: var(--c-white);
        padding: 3.5rem;
        border-radius: 2.5rem;

        width: 42.8rem;
        min-width: 0;

        filter: drop-shadow(1rem 1rem 0 var(--sh));

        text-align: start;

        .title{
            border-bottom: .1rem solid var(--c-black);
            width: max-content;
            min-width: 0;
            max-width: 100%;
            margin-bottom: .8rem;
            min-height: 2em;

            p{
                width: 100%;
                font-size: 2.4rem;
                @include text-overflow;
            }
        }

        .name{
            margin-bottom: 1.6rem;

            p{
                width: 100%;
                font-size: 1.6rem;
                @include text-overflow;
            }
        }

        .body{
            height: 14.6rem;
            overflow: hidden;
            margin-bottom: 1.6rem;
            position: relative;

            &::after{
                @include pseudo-absolute;
                bottom: 0;
                width: 100%;
                height: 4rem;
                background: var(--gr-fade);
            }

            p{
                white-space: pre-wrap;
            }
        }

        .link{
            display: flex;
            justify-content: space-between;
            align-items: center;

            gap: .8rem;

            a{
                color: var(--c-grey);
                --sh-color: var(--sh)
            }

            .btns{
                display: flex;
                gap: .8rem;

                .btn{
                    padding: 0;
                    width: 2.2rem;
                    height: 2.2rem;
                    border-radius: 50%;

                    font-size: .8rem;
                    
                    --color: transparent;

                    border: .1rem solid var(--color);

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
                        font-size: 1.4rem;
                    }
                }
            }
        }
    }
</style>