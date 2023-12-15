<template>
    <SideModal class="instruction-modal" ref="modal" @close="close">
        <h2>
            Если вы&nbsp;не&nbsp;знаете, 
            с&nbsp;чего начать, предлагаем 
            подумать над этими вопросами:
        </h2>
        <ul>
            <li>Из&nbsp;всех руководителей, преподавателей, менторов, есть&nbsp;ли для вас кто-то один особенный?</li>
            <li>Есть&nbsp;ли какие-то слова, которые вы&nbsp;вспоминаете сквозь года? Чьи они?</li>
            <li>Кто помог вам однажды принять судьбоносное решение?</li>
            <li>Кто ваш наставник? Какой он? Похож&nbsp;ли он&nbsp;на&nbsp;кого-нибудь из&nbsp;персонажей книг или фильмов?</li>
            <li>Какие интересные ситуации с&nbsp;ним связаны?</li>
            <li>За&nbsp;что вы&nbsp;благодарны своему наставнику?</li>
        </ul>
        <p class="quote">
            Мы&nbsp;очень далеки от&nbsp;представления, что наставник&nbsp;&mdash; это седой старец, изрекающий мудрости. Может быть, ваш ментор&nbsp;&mdash; лучший друг, студент или коллега. Удивите нас! :)
        </p> 
        <div class="btns">
            <RouterLink :to="Object.assign({name: 'List'}, R().query.filter && {query: {filter: R().query.filter}})"><VButton >Смотреть все послания</VButton></RouterLink>
            <VButton orange @click="R().pushQuery({lsend: 'true', infoModal: null})">Оставить послание</VButton>
        </div>
    </SideModal>
</template>

<script setup>
    import SideModal from "@/components/ui/modal/SideModal.vue";

    import { onMounted, ref, watch } from 'vue';
    
    import R from "@/stores/Router.js"

    const modal = ref();

    watch(
        ()=>R().query.infoModal,
        (n)=>{
            n?modal.value.call():modal.value.close()
        }
    )

    const close = ()=>{
        R().pushQuery({infoModal: null})
    }
</script>

<style lang="scss" scoped>
    .instruction-modal :deep(.container){
        @include flex-col;
        // justify-content: center;
        gap: 6.4rem;
    }

    h2{
        font-weight: 400;
    }

    .quote{
        text-align: end;
        font-size: 1.2rem;
        color: #263F5C;
        padding-left: 20%;
    }

    .btns{
        @include flex-jtf;
        flex-wrap: wrap;
        gap: 1.2rem;
    }

    @media (max-width: $mobile-med){
        .btns{
            .btn, a{
                width: 100%;
            }
        }
    }

</style>