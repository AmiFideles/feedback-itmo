<template>
    <div class="global-wr">
        <VHeader/>

        <RouterView v-slot="{ Component }" >
            <Transition name="fade" mode="out-in">
                <Component :is="Component"/>
            </Transition>
        </RouterView>

        <InstructionModal/>
        <LModal/>
        <LSendModal/>
    </div>
</template>

<script setup>
    import VHeader from "@/components/header/VHeader.vue";
    import InstructionModal from "@/components/instruction/InstructionModal.vue";
    import LModal from "@/components/letter/LModal.vue";
    import LSendModal from "@/components/letter/LSendModal.vue";

    import RStore from "@/stores/Router.js";
    import StaticStore from "@/stores/Static.js";
    const R = RStore();
    const Static = StaticStore();
</script>

<style lang="scss" scoped>
    .global-wr{
        position: relative;
        min-height: 100vh;
    }

    .fade-enter-from,
    .fade-leave-to{
        opacity: 0;
    }
    
    .fade-enter-active,
    .fade-leave-active{
        transition: opacity .15s;
    }

    // .global-wr{
    //     margin: 0 auto;
    //     width: 100%;
    //     max-width: 190rem;
    //     padding: 12.8rem 7.5rem;

    //     height: 300vh;
    // }
</style>