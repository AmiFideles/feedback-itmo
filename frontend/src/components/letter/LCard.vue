<template>
    <div class="card" v-if="info" :style="{'--sh': Static().getColor(info.colorId || 1)?.color}">
        <div class="title">
            <p>{{info.mentorName}}</p>
        </div>
        <div class="body">
            <p>{{info.messageText}}</p>
        </div>
        <div class="link">
            <a class="sh-link" @click="go">Читать полностью</a>
        </div>
    </div>
</template>

<script setup>
    import R from "@/stores/Router.js";
    import Static from "@/stores/Static.js";
    
    import { computed } from "vue";

    const props = defineProps({
        info: Object,
        list: Array
    })

    const go = ()=>{
        R().pushQuery(
            Object.assign(
                {l: props.info.id},
                {larr: (props.list || []).map(e => e.id).join(';')}
            )
        );
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
            margin-bottom: 1.6rem;

            p{
                width: 100%;
                font-size: 2.4rem;
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
            justify-content: end;

            a{
                color: var(--c-grey);
                --sh-color: var(--sh)
            }
        }
    }
</style>