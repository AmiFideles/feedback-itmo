<template>
    <div class="letters-wr">
        <div class="cas-wr cas-wr-primary">
            <div class="tabs">
                <div class="refresh-wr" :spin="spin || null" @click="refresh">
                    <div class="sh-link refresh">
                        <IReload/>
                    </div>
                </div>
            </div>
            <FeedbackPag admin ref="pagRef"/>
        </div>
    </div>
</template>

<script setup>
    import FeedbackPag from "@/components/feedback/FeedbackPag.vue";

    import IReload from "@/components/icons/IReload.vue";

    import R from "@/stores/Router.js"
    
    import { ref } from "vue";

    const pagRef = ref();

    const spin = ref()

    const refresh = ()=>{
        spin.value = true;
        setTimeout(()=>spin.value = false, 1000);

        pagRef.value.reset();
    };
</script>

<style lang="scss" scoped>
    .letters-wr{
        padding-top: 12.8rem;
    }

    .letters{
        padding-top: var(--generic-padding);
    }

    .tabs{
        font-size: 2.4rem;
        display: flex;
        gap: 2rem;

        .sh-link{
            --sh-color: var(--c-orange);
        }
    }

    .refresh{
        height: 2.8rem;
        width: 2.8rem;

        &-wr[spin]{
            transition: 1s;
            transform: rotate(-3turn);
        }

        svg{
            height: 100%;
            width: 100%;
            display: block;
        }
    }
</style>