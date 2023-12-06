<template>
    <div class="scroll-top" :showArr="showArr || null">
        <VButton @click="scrl">
            <ISСrollArr class="ico"/>
        </VButton>
    </div>
</template>

<script setup>
    import ISСrollArr from "@/components/icons/ISСrollArr.vue";

    import {onMounted, onUnmounted, ref} from "vue" 

    const scrl = ()=>{
        window.scrollTo(0,0);
    }

    onMounted(()=>{
        window.addEventListener('scroll', scrollHandler);
    })

    onUnmounted(() => {
        window.removeEventListener('scroll', scrollHandler);
    })

    let showArr = ref(false);

    const scrollHandler = ()=>{
        if(document.scrollingElement.scrollTop > 100){
            showArr.value = true;
            return;
        }
        showArr.value = false;
    }
</script>

<style lang="scss" scoped>
    .scroll-top{
        position: fixed;
        left: var(--generic-padding);
        bottom: var(--generic-padding);
        transition: .3s;

        :deep(.btn){
            width: 7.5rem;
            height: 7.5rem;
            border-radius: 50%;
        }

        :deep(.ico){
            width: 86%;
        }

        &:not([showArr]){
            @include hidden(10rem);
        }
    }
</style>