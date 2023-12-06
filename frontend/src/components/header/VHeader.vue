<template>
    <nav class="header cas-wr">
        <RouterLink :to="{name: 'Home'}" class="logo">
            <ILogo/>
        </RouterLink>

        <div class="links">
            <a class="sh-link" @click="R().pushQuery({infoModal: 'true'})">Инструкция</a>
            <a class="sh-link" @click="R().pushQuery({lsend: 'true'})">Оставить послание</a>
        </div>
    </nav>
</template>

<script setup>
    import ILogo from "@/components/icons/ILogo.vue";

    import R from "@/stores/Router.js"
</script>

<style lang="scss" scoped>
    .header{
        @include flex-jtf;
        align-items: center;
        padding: 0 var(--generic-padding);
        position: absolute;
        width: 100%;
        top: 12.8rem;
        z-index: 1;
        
        left: 0;
        right: 0;
        margin: 0 auto;

        .logo{
            height: 5.2rem;

            svg{
                height: 100%;
            }
        }

        .links{
            display: flex;
            gap: calc(6.4rem - 2em);
            margin-right: -1em;

            .sh-link{
                --sh-color: var(--c-orange);
                @include flex-c;
                padding: 1em;
            }
        }
    }
</style>