<template>
    <p v-if="err">{{err}}</p>
</template>

<script setup>
    const props = defineProps({
        err: [String, Object]
    })
</script>

<style lang="scss" scoped>
    p{
        color: var(--c-err);
    }
</style>