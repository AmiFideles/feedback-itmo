<template>
    <nav class="header cas-wr">
        <RouterLink :to="{name: 'Home'}" class="logo">
            <img src="/logo.png" class="image"/>
        </RouterLink>
        <div class="links" v-if="!isAdmin" :drop="drop || null" @click="drop = false">
            <a class="sh-link" @click="R().pushQuery({infoModal: 'true'})">Инструкция</a>
            <a class="sh-link" @click="R().pushQuery({lsend: 'true'})">Оставить послание</a>
        </div>
        <div class="links" v-if="isAdmin && A().isLogged" :drop="drop || null" @click="drop = false">
            <RouterLink class="sh-link tab-link-wr" :active="R().meta.nest == 'AFeedback' || null" :to="{name: 'AFeedback'}"><span class="tab-link">Обратная связь</span></RouterLink>
            <RouterLink class="sh-link tab-link-wr" :active="R().meta.nest == 'Admin' || null" :to="{name: 'Admin'}"><span class="tab-link">Модерация</span></RouterLink>
            <a class="sh-link" @click="A().exit()">Выйти</a>
        </div>

        <div class="caller" mob :drop="drop || null" @click="drop = !drop">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
    </nav>
</template>

<script setup>
    import ILogo from "@/components/icons/ILogo.vue";

    import R from "@/stores/Router.js"
    import A from "@/stores/Admin.js";
    
    import { computed, ref } from "vue";

    const drop = ref(false);

    const isAdmin = computed(()=>R().meta?.admin);
</script>

<style lang="scss" scoped>
    .header{
        @include flex-jtf;
        align-items: center;
        padding: 0 var(--generic-padding);
        position: absolute;
        width: 100%;
        top: 12.8rem;
        z-index: 2;
        
        left: 0;
        right: 0;
        margin: 0 auto;

        .logo{
            height: 11.2rem;
            z-index: 2;

            .image{
                height: 100%;
                width: auto;
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

        .caller{
            z-index: 2;
            width: 3.6rem;
            height: 3.6rem;
            position: relative;
            cursor: pointer;
            border-radius: 100%;

            transition: .3s;
            will-change: transform;

            background-color: var(--c-lime-ghost);

            &:active{
                transition: .1s;
                transform: translateY(.2rem);
            }
            
            .bar{
                width: 50%;
                background: var(--c-black);
                height: .2rem;
                border-radius: 2rem;

                transition: .15s;
                transition-delay: .15s, 0s, 0s;
                transition-property: top, opacity, transform;

                position: absolute;
                @include all-directions(0);
                margin: auto;

                &:first-child{
                    top: 1rem;
                }
                
                &:last-child{
                    top: -1rem;
                }
            }

            &[drop]{
                background-color: transparent;

                .bar{
                    transform: rotate(45deg);
                    
                    transition-delay: 0s, .15s, .15s;
                    transition-property: top, opacity, transform;

                    &:first-child{
                        top: 0;
                        transform: rotate(45deg);
                    }

                    &:nth-child(2){
                        opacity: 0;
                    }
                    
                    &:last-child{
                        top: 0;
                        transform: rotate(-45deg);
                    }
                }
            }

        }

        @media (max-width: $laptop){
            .logo{
                height: 5rem;
            }
        }

        @media (max-width: $mobile-big) {
            top: var(--generic-padding);

            .logo{
                height: 4rem;
            }

            .links{
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                background: var(--c-white);
                padding: 5.3rem 8rem;
                border-radius: 0 0 4.5rem 4.5rem;
                filter: drop-shadow(.1rem .1rem .3rem #00000011);
                transition: .3s;
                flex-direction: column;
                gap: 0;
                font-size: 2rem;
                align-items: start;

                a.sh-link{
                    padding: .5rem;
                }

                &::before{
                    @include pseudo-absolute;
                    bottom: 100%;
                    left: 0;
                    width: 100%;
                    height: var(--generic-padding);
                    background: var(--c-white);
                }

                &:not([drop]){
                    @include hidden(-10rem);
                }
            }
        }
    }

    @media (min-width: calc($mobile-big + 1px)) {
        [mob]{
            display: none;
        }
    }
</style>