<template>
    <div class="select" :drop="drop || null" v-click-outside="()=>drop = false">
        <div class="container" @click="drop = !drop">
            <p>
                {{(keyName?modelValue?.[keyName]:modelValue) || placeholder}}
            </p>
            <IDrop/>
        </div>

        <div class="list-wr">
            <div class="list">
                <div 
                    class="list-item"

                    v-for="(i,k) in list"
                    :key="k"

                    @click="
                        emit('update:modelValue', i);
                        drop = false;
                    "
                >
                    {{keyName?i[keyName]:i}}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref } from 'vue';

    import IDrop from '@/components/icons/IDrop.vue';

    import vClickOutside from 'click-outside-vue3/src/v-click-outside';

    const props = defineProps({
        modelValue: Object,
        list: Array,
        keyName: String,
        placeholder: String,
    })

    const emit = defineEmits(['update:modelValue']);

    const drop = ref(false);


</script>

<style lang="scss" scoped>
    .select{
        --bg: var(--c-white);
        --txt: var(--c-black);
        --hov: var(--c-lime-hov);
        position: relative;
        text-align: center;
        
        font-size: 1.6rem;

        &[med]{
            font-size: 2.4rem;
        }
        
        &[big]{
            font-size: 3.2rem;

            .container, .list-item{
                padding: 0.38em 1.25em;
            }
        }

        .container, .list-item{
            cursor: pointer;
            width: max-content;
            background: var(--bg);
            color: var(--txt);
            border-radius: 1.375em;
            transition: .3s;
            position: relative;
            padding: 0.5em 1.25em;
            width: 100%;

            display: flex;
            align-items: center;
            
            gap: .5em;

            p{
                font-size: inherit;
                @include text-overflow;
                width: 100%;
            }

            .loading{
                position: absolute;
                @include all-directions(0);
                margin: auto;
                max-width: 100%;
                color: var(--txt)!important;
            }

            @media (hover:hover){
                &:hover{
                    background: var(--hov);
                }
            }

            &:active{
                transition: 0.01s;
                box-shadow: .2rem .2rem .1rem 0 rgba(0, 0, 0, 0.25) inset;
                
                .content{
                    transition: 0.01s;
                    transform: translate(.2rem, .2rem);
                }
            }

            &[loading]{
                pointer-events: none;
                animation: pulse .6s linear alternate infinite;
            }

            &[disabled]{
                opacity: .7;
                pointer-events: none;
            } 

            svg{
                height: .5em;
                transition: .3s;
            }
        }

        .list-wr{
            border-radius: 1.375em;
            overflow: hidden;
            position: absolute;
            top: calc(.8rem + 100%);

            transition: .3s;
            width: 100%;

            z-index: 10;

            filter: drop-shadow(.1rem .1rem .3rem #00000011);

            .list{
                width: 100%;
                background: var(--bg);
                
                overflow: auto;
                max-height: 30vh;
                padding: .5rem 0;

                overscroll-behavior: contain;

                &::-webkit-scrollbar{
                    &-track{
                        background: var(--bg);
                    }

                    &-thumb{
                        border-color: var(--bg);
                    }
                }

                .list-item{
                    border-radius: 0;

                    &:not(:last-child){
                        border-bottom: 1px solid #8B8B8B;
                    }
                }
            }
        }
        
        &:not([drop]){
            .list-wr{
                @include hidden(-1rem);
            }
        }

        &[drop]{
            svg{
                transform: rotate(.5turn);
            }
        }

        &[rev]{
            .list-wr{
                top: unset;
                bottom: calc(.8rem + 100%);
            }

            &:not([drop]){

                .list-wr{
                    @include hidden(1rem);
                }
            }
        }
    }
</style>