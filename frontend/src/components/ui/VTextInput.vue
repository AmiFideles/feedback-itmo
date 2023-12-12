<template>
    <div class="input-wr">
        <input 
            :type="type" 
            v-model="text" 
            :placeholder="placeholder"
            ref="inp"
        >
        <div v-if="clearable" class="cross sh-link" :show="text || null" @click="text = ''">
            <IClose/>
        </div>
        <slot/>
    </div>
</template>

<script setup>
    import { ref, watch } from "vue";
    import IClose from "@/components/icons/IClose.vue";

    const props = defineProps({
        modelValue: [String, Number],
        type:{
            type: String,
            default: 'text'
        },
        placeholder: [String, Number],
        delay: {
            type: Number,
            default: 0
        },
        clearable: Boolean,
    });

    const emit = defineEmits(['update:modelValue']);

    let delayTimeout = null;
    const text = ref(props.modelValue != null ? props.modelValue : '');
    watch(text, (n)=>{
        if(delayTimeout)clearTimeout(delayTimeout);

        delayTimeout = setTimeout(
            ()=>emit('update:modelValue', n),
            props.delay
        )
    });
    watch(()=>props.modelValue, (n)=>text.value = n);
</script>

<style lang="scss" scoped>
    .input-wr{
        --bg: var(--c-white);
        background: var(--bg);
        border-radius: 1.643em;
        display: flex;
        align-items: center;
        position: relative;
        overflow: hidden;

        input{
            padding: 1.072em 1.714em;
            width: 100%;
            width: 100%;

            &::placeholder{
                color: var(--c-grey-dark);
                transition: .1s;
            }

            &:focus::placeholder{
                opacity: 0;
            }
        }

        .cross{
            height: 100%;
            width: 5.5rem;
            @include flex-c;
            flex-shrink: 0;
            cursor: pointer;
            
            &:not([show]){
                width: 0;
                @include hidden-hor(.1rem);
            }
        }
    }
</style>