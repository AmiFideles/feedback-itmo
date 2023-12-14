<template>
    <div class="carousel-wr">
        <Carousel 
            class="carousel"

            :items-to-show="1"
            :breakpoints="breakpoints"
            v-model="slideId"
            ref="carRef"
        >
            <Slide class="slide" v-for="slide in list" :key="slide">
                <LCard :info="slide" :list="list"/>
            </Slide>

            <Slide class="slide" v-if="!stop">
                <VLoading/>
            </Slide>
        </Carousel>

        <SliderArrows
            @prev="carRef.prev()" 
            @next="carRef.next()"
        />
    </div>
</template>

<script setup>
    import 'vue3-carousel/dist/carousel.css'
    import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel'

    import { feedbackAPI } from "@/script/api";

    import LCard from "@/components/letter/LCard.vue";
    import SliderArrows from "@/components/ui/SliderArrows.vue";
    
    import { onMounted, ref, watch } from 'vue';

    const carRef = ref();

    const slideId = ref(0);
    const list = ref([]);

    const stop = ref();
    const offset = ref(0);

    const paginate = async ()=>{
        let res = await feedbackAPI.getList('approved', offset.value, 9);

        let l = res.content;

        offset.value++;

        list.value.push(...l);

        if(res.totalPages <= offset.value || !l || !l.length){
            stop.value = true;
            return;
        }
    }

    const breakpoints = {
        // 700px and up
        1060: {
            itemsToShow: 3,            
            snapAlign: 'center',
        },
        // 1024 and up
        800: {
            itemsToShow: 2,
            snapAlign: 'start',
        },
    }

    onMounted(paginate);
    watch(slideId, n =>{if(n >= list.value.length - 2)paginate();})
</script>

<style lang="scss" scoped>
    .slide{
        padding: 0 1.6rem 1.6rem;
    }

    :deep(.carousel){ //notdone pc only
        .carousel__track{
            transition: .3s!important;
        }
    }

    .carousel-wr{
        position: relative;
    }
</style>