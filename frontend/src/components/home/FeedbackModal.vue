<template>
    <VModal 
        ref="modal" 
        closeNative 
        :style="{'--bg': 'var(--c-lime-ghost)'}"
        @call="success = false"
    >
        <template #title>
            <h2>Оставить пожелание</h2>
        </template>
        
        <div class="form-wr">
            <div class="success" :show="success || null">
                <h3>Спасибо за ваше пожелание!</h3>
            </div>

            <div class="form pre-success" :show="!success || null">
                <div class="inp-wr">
                    <div class="title">Комментарий по&nbsp;проекту или&nbsp;лендингу</div>
                    <VTextarea/>
                </div>
                <div class="inp-wr">
                    <div class="title">Контакты для связи (необязательно)</div>
                    <VTextInput/>
                </div>
            </div>
            
            <VErr :err="err"/>
            <div class="btn-wr">
                <VButton class="success" @click="modal.close()" :show="success || null" med>Отлично!</VButton>
                <VButton class="pre-success" @click="confirm" :show="!success || null" :loading="loading || null" med>Отправить</VButton>
            </div>
        </div>
    </VModal>
</template>

<script setup>
    import { ref } from "vue";
    import { reviewAPI } from "@/script/api";

    const modal = ref();
    defineExpose({modal});

    const data = ref({
        comment: "",
        contact: ""
    })

    const reset = ()=>{
        data.value.comment = "";
        data.value.contact = "";
    }

    const err = ref();
    const loading = ref();
    const success = ref(false);

    const confirm = async ()=>{
        loading.value = true;
        err.value = '';

        await reviewAPI
        .add(
            data.comment,
            data.contact
        )
        .catch(error => {
            err = error.message || error; 
        });
        
        loading.value = false;

        if(!err.value){
            success.value = true;
            reset();
        }
    }
</script>

<style lang="scss" scoped>
    h2{
        font-size: 2.4rem;
        font-weight: 400;
        min-height: 1em;
    }

    .form-wr{
        position: relative;
    }

    .form{
        @include flex-col;
        gap: 1.6rem;
        margin: 3.2rem 0;

        .title{
            font-size: 1.4rem;
            color: var(--c-grey-dark);
            margin-bottom: .6rem;
            margin-right: 4.8rem;
        }
    }

    .btn-wr{
        position: relative;
        @include flex-c;
        width: 100%;

        @media (max-width: $mobile){
            .btn{
                font-size: 1.6rem;
            }
        }
    }

    .success, .btn.success{
        position: absolute;
        text-align: center;
        position: absolute;
        height: 100%;
        width: 100%;
        @include flex-c;
        gap: 1.6rem;
    }

    .btn.success{
        width: max-content;
    }

    .success,
    .pre-success{
        transition: .3s;
        &:not([show]){
            @include hidden(1rem);
        }
    }
</style>