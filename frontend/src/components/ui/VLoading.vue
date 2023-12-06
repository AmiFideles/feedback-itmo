<template>
    <div class="loader">
        <ILoader class="ico"/>
    </div>
</template>

<script setup>
    import ILoader from "@/components/icons/ILoader.vue"
</script>

<style lang="scss" scoped>
    .loader{
        @include flex-c;
        color: var(--c-black);
        width: 6.5rem;
        overflow: hidden;
    }
</style>