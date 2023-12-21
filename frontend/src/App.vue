<template>
    <div class="global-wr">
        <div class="backdrop"></div>

        <VRibbonsDisplay/>

        <VHeader/>

        <template v-if="isAdmin && Admin.isLogged == null">
            <VLoading/>
        </template>
        <template v-else-if="isAdmin && Admin.isLogged == false">
            <ALogin/>
        </template>
        <template v-else>
            <RouterView v-slot="{ Component }" >
                <Transition name="fade" mode="out-in">
                    <Component :is="Component" class="page"/>
                </Transition>
            </RouterView>

            <AboutModal/>
            <InstructionModal/>
            <LModal/>
            <LAdminModal/>
            <LSendModal/>
        </template>

        <VFooter/>
    </div>
</template>

<script setup>
    import VHeader from "@/components/layout/header/VHeader.vue";
    import VFooter from "@/components/layout/VFooter.vue";
    import AboutModal from "@/components/about/AboutModal.vue";
    import InstructionModal from "@/components/instruction/InstructionModal.vue";
    import LModal from "@/components/letter/LModal.vue";
    import LAdminModal from "@/components/letter/LAdminModal.vue";
    import LSendModal from "@/components/letter/LSendModal.vue";
    import VRibbonsDisplay from "@/components/ribbons/VRibbonsDisplay.vue";

    import ALogin from "@/components/admin/ALogin.vue";

    import RStore from "@/stores/Router.js";
    import StaticStore from "@/stores/Static.js";
    import AdminStore from "@/stores/Admin.js";

    import { computed, watch } from "vue";

    const R = RStore();
    const Static = StaticStore();
    const Admin = AdminStore(); 

//admin
    const isAdmin = computed(()=>R.meta?.admin);
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

    .page{
        min-height: 100vh;
    }

    .backdrop{
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        width: 100%;
        z-index: 0;
        overflow: hidden;

        &::before{
            @include pseudo;
            width: 100%;
            height: 200vh;
            background: var(--gr-background);
        }
    }
</style>