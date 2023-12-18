<template>
    <div class="login">
        <div class="form cas-wr cas-wr-primary">
            <h2>Вход в систему</h2>
            <div class="title">Логин</div>
            <VTextInput v-model="data.username" @keydown.enter="passwInp.focus()"/>
            <div class="title">Пароль</div>
            <VTextInput type="password" v-model="data.password" ref="passwInp" @keydown.enter="login"/>
            <VErr :err="err"/>
            <VButton @click="login" :loading="loading || null">Войти</VButton>
        </div>
    </div>
</template>

<script setup>
    import { ref } from "vue";
    import A from "@/stores/Admin.js";

    const data = ref({
        username: "",
        password: ""
    })
    const passwInp = ref();

    const loading = ref(false);
    const err = ref();

    const login = async ()=>{
        loading.value = true;
        err.value = false;

        err.value = await A().login(
                        data.value.username,
                        data.value.password
                    );
        err.value = err.value?.message;

        loading.value = false;
    }

</script>

<style lang="scss" scoped>
    .login{
        @include flex-col;
        padding: 10rem 0;

        .form{
            width: 100%;
            max-width: 50rem;

            h2{
                text-align: center;
                margin-bottom: 3rem;
            }

            .title{
                margin-top: 2rem;
                margin-bottom: .8rem;
            }

            .btn{
                margin: 0 auto;
                margin-top: 2rem;
            }

            p[err]{
                margin-top: 2rem;
            }
        }
    }
</style>