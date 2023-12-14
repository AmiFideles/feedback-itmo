<template>
    <textarea 
        :placeholder="placeholder" 
        v-model="text"  
        :rows="rows"
        oninput="this.style.height = ''; this.style.height = (this.scrollHeight) +'px'"
    />
</template>

<script setup>
    import { ref, watch } from "vue";

    const props = defineProps({
        modelValue: [String, Number],
        type:{
            type: String,
            default: 'text'
        },
        placeholder: [String, Number],
        rows: {
            type: [String, Number],
            default: 3
        },
        delay: {
            type: Number,
            default: 0
        },
        clearable: Boolean,
    });

    const emit = defineEmits(['update:modelValue']);

    const inp = ref();

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
    textarea{
        background: var(--c-white);
        border-radius: 1.643em;
        padding: 1.072em 1.714em;
        width: 100%;
        width: 100%;
        border: none;
        resize: none;
        display: block;
        // max-height: 12em;

        &::-webkit-scrollbar{
            display: none;
        }

        &::placeholder{
            color: var(--c-grey-dark);
        }
    }
</style>