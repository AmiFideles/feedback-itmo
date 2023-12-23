<template>
    <div>
        <div class="cas-wr cas-wr-primary filters-wr">
            <div class="filters-wr-wr">
                <p>Сортировка</p>
                <div class="filters">
                    <VTextInput 
                        med 
                        v-model="faculty"
                        keyName="name"
                        placeholder="По подразделению"
                        class="faculty-select select"
                        :style="{
                            '--bg': 'var(--c-lime-light)'
                        }"
                        :delay="300"
                        clearable
                    />
                    <VSelect
                        med 
                        v-model="sort"
                        :list="sorts"
                        keyName="name"
                        placeholder="Сортировка"
                        class="sort-select select"
                        :style="{
                            '--bg': 'var(--c-lime-light)'
                        }"
                    />
                    <VSelect
                        med 
                        v-model="year"
                        :list="years"
                        keyName="name"
                        placeholder="По году выпуска"
                        class="year-select select"
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

//faculty
    const faculty = ref(null);

//years
    const years = computed(()=>[
        {name: 'Все годы', value: null},
        ...Array.from(moment.range('1900', new Date()).by('year')).map(m => {return {name: m.format('YYYY'), value: m.format('YYYY')}}).reverse()
    ]);
    const year = ref(null);

//sort
    const sorts = computed(()=>[
        {name: 'Случайно', value: null},
        {name: 'По дате', value: 'dateTime'},
        {name: 'По ментору', value: 'mentorName'}
    ]);
    const sort = ref(sorts.value[0]);

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
    
    watch(
        sort, 
        (n)=>{
            R().pushQuery({sort: n?.value})
        }
    )

    onMounted(()=>{
        R().pushQuery({filters: null, sort: null})
    });
</script>

<style lang="scss" scoped>
    .filters-wr{
        .filters-wr-wr{
            padding-top: 12.8rem;
            @include flex-jtf;
            align-items: baseline;
            gap: 2.8rem;

            p{
                font-size: 2.4rem;
                flex-shrink: 0;
            }
        }
    }

    .filters{
        display: flex;
        flex-wrap: wrap;
        justify-content: end;
        gap: 1.6rem 2.8rem;

        .faculty-select{
            width: 34rem;
        }
        .year-select{
            width: 32.5rem;
        }
        .sort-select{
            width: 25.4rem;
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

                .select{
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