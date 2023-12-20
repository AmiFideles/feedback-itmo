<template>
    <div class="card" v-if="info" :style="{'--sh': Static().getColor(info.color || 1)?.color}">
        <div class="title">
            <p>{{info.mentorName}}</p>
        </div>
        <div class="name" v-if="info.firstName">
            <p class="letter-text">от: {{info.firstName}} {{info.lastName}}</p>
        </div>
        <div class="body">
            <p class="letter-text">{{info.messageText}}</p>
        </div>
        <div class="link">
            <div class="btns" v-if="admin">
                <VButton class="REJECT" @click="setStatus('REJECTED')" :active="info?.status == 'REJECTED' || null" :loading="loading || null">
                    ╳
                </VButton>
                <VButton class="APPROVE" @click="setStatus('APPROVED')" :active="info?.status == 'APPROVED' || null" :loading="loading || null">
                    ✓
                </VButton>
            </div>
            <div class="spacer" v-else></div>
            <a class="sh-link" @click="go">Читать полностью</a>
        </div>
    </div>
</template>

<script setup>
    import R from "@/stores/Router.js";
    import Static from "@/stores/Static.js";
    
    import { computed, ref } from "vue";
    import { feedbackAPI } from "@/script/api/";

    const props = defineProps({
        info: Object,
        list: Array,
        offset: Number,
        admin: Boolean
    })

//admin
    const loading = ref();
    const controlsErr = ref();

    const setStatus = async (status)=>{
        loading.value = true;
        controlsErr.value = '';

        let toSend = Object.assign(
            {}, 
            props.info, 
            {status},
            {
                color: null,
                dateTime: null,
                graduationYear: null,
                photoURL: null,
            }
        )

        await feedbackAPI
            .change(toSend)
            .catch(error => controlsErr.value = error.message || error);
        
        if(!controlsErr.value){
            props.info.status = status;
        }

        loading.value = false;
    }

    const go = ()=>{
        R().pushQuery(
            Object.assign(
                {[props.admin?'lAdmin':'l']: props.info.id},
                {larr: (props.list || []).map(e => e.id).join(';')},
                {offset: props.offset}
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
            margin-bottom: .8rem;

            p{
                width: 100%;
                font-size: 2.4rem;
                @include text-overflow;
            }
        }

        .name{
            margin-bottom: 1.6rem;

            p{
                width: 100%;
                font-size: 1.6rem;
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
            justify-content: space-between;
            align-items: center;

            gap: .8rem;

            a{
                color: var(--c-grey);
                --sh-color: var(--sh)
            }

            .btns{
                display: flex;
                gap: .8rem;

                .btn{
                    padding: 0;
                    width: 2.2rem;
                    height: 2.2rem;
                    border-radius: 50%;

                    font-size: .8rem;
                    
                    --color: transparent;

                    border: .1rem solid var(--color);

                    background: transparent;

                    &[active]{
                        background: var(--color);
                    }

                    &.REJECT{
                        --color: #ff4642;
                        font-weight: 600;
                    }
                    &.APPROVE{
                        --color: #adff59;
                        font-size: 1.4rem;
                    }
                }
            }
        }
    }
</style>