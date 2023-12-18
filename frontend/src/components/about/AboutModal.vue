<template>
    <SideModal class="instruction-modal" ref="modal" @close="close">
        <h2>О проекте</h2>
        <p>
            ИТМО Спасибо&nbsp;&mdash; наша признательность за&nbsp;труд всем
            преподавателям ИТМО. 
            Идея проекта предложена в&nbsp;рамках онлайн-интенсива 
            &laquo;<a href="https://www.expert.itmo.ru/online_intensive2023" target="_blank">Наставничество как вид лидерства</a>&raquo; 
            (2023) в&nbsp;честь Года педагога и&nbsp;наставника в&nbsp;России.
        </p>
        <p>С&nbsp;любовью и&nbsp;благодарностью,<br>ДОД</p>
        <div class="btns">
            <RouterLink
                :to="
                    Object.assign(
                        { name: 'List' },
                        R().query.filter && {
                            query: { filter: R().query.filter },
                        }
                    )
                "
                ><VButton>Смотреть все послания</VButton></RouterLink
            >
            <VButton
                orange
                @click="R().pushQuery({ lsend: 'true', aboutModal: null })"
                >Оставить послание</VButton
            >
        </div>
    </SideModal>
</template>

<script setup>
import SideModal from "@/components/ui/modal/SideModal.vue";

import { onMounted, ref, watch } from "vue";

import R from "@/stores/Router.js";

const modal = ref();

watch(
    () => R().query.aboutModal,
    (n) => {
        n ? modal.value.call() : modal.value.close();
    }
);

const close = () => {
    R().pushQuery({ aboutModal: null });
};
</script>

<style lang="scss" scoped>
.instruction-modal :deep(.container) {
    @include flex-col;
    // justify-content: center;
    gap: 6.4rem;
}

h2 {
    font-weight: 400;
}

.quote {
    text-align: end;
    font-size: 1.2rem;
    color: #263f5c;
    padding-left: 20%;
}

.btns {
    @include flex-jtf;
    flex-wrap: wrap;
    gap: 1.2rem;
}

@media (max-width: $mobile-med) {
    .btns {
        .btn,
        a {
            width: 100%;
        }
    }
}
</style>