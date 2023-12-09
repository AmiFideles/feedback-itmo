<template>
    <div>
        <div class="cas-wr filters-wr">
            <div class="filters-wr-wr">
                <p>Сортировка</p>
                <div class="filters">
                    <VTextInput 
                        med 
                        v-model="faculty"
                        keyName="name"
                        placeholder="По подразделению"
                        class="faculty-select"
                        :style="{
                            '--bg': 'var(--c-lime-light)'
                        }"
                        :delay="300"
                        clearable
                    />
                    <VSelect
                        med 
                        v-model="year"
                        :list="years"
                        keyName="name"
                        placeholder="По году выпуска"
                        class="year-select"
                        :style="{
                            '--bg': 'var(--c-lime-light)'
                        }"
                    />
                </div>
            </div>
        </div>

        <div class="cas-wr letters-wr">
            <LettersPag/>
        </div>

        <VScrollTop/>
    </div>
</template>

<script setup>
    import { ref, computed, watch, onMounted } from 'vue';

    import LettersPag from "@/components/letter/LettersPag.vue";

    import R from "@/stores/Router.js"

    import Moment from 'moment';
    import { extendMoment } from 'moment-range';
    const moment = extendMoment(Moment);

    const faculty = ref(null);

    const years = computed(()=>[
        {name: 'Все годы', value: null},
        ...Array.from(moment.range('1900', new Date()).by('year')).map(m => {return {name: m.format('YYYY'), value: m.format('YYYY')}}).reverse()
    ]);
    const year = ref(null);

    watch(
        ()=>[faculty.value, year.value?.value], 
        (n)=>{
            if(!n.filter(e => e).length){
                R().pushQuery({filters: null})
                return;
            }

            R().pushQuery({
                filters: 
                Object.entries(Object.assign(
                    {},
                    faculty.value && {faculty: faculty.value},
                    year.value?.value && {graduationYear: year.value?.value},
                ))
                .map(([key, val])=>`${key}=${val}`)
                .join(';')
            })
        }
    )

    onMounted(()=>{
        R().pushQuery({filters: null})
    });
</script>

<style lang="scss" scoped>
    .filters-wr{
        .filters-wr-wr{
            padding-top: 12.8rem;
            @include flex-jtf;
            align-items: baseline;

            p{
                font-size: 2.4rem;
            }
        }
    }

    .filters{
        display: flex;
        gap: 2.8rem;

        .faculty-select{
            width: 34rem;
        }
        .year-select{
            width: 32.5rem;
        }
    }

    .letters-wr{
        padding-top: 8.4rem;
    }

    :deep(.filters .input-wr){
        font-size: 2.4rem;

        input{
            padding: 0.5em 1.25em;

            &::placeholder{
                color: var(--c-black)!important;
            }
        }
    }

    @media (max-width: $pad){
        .filters-wr{
            .filters-wr-wr{
                padding-top: var(--generic-padding);
            }
            
            .filters-wr-wr, .filters{
                flex-direction: column;
                gap: 1.8rem;
            }

            :deep(.filters :is(.input-wr, .select)), p{
                text-align: start;
            }

            .filters{
                gap: .8rem;
                width: 100%;

                .faculty-select{
                    width: 100%;
                }
                .year-select{
                    width: 100%;
                }
            }
        }

        .letters-wr{
            padding-top: var(--generic-padding);
        }
    }

    @media (max-width: $mobile-big){
        :deep(.filters :is(.input-wr, .select)), p{
            font-size: 2rem!important;
        }
    }
</style>