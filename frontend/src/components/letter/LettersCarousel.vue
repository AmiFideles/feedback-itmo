<template>
    <div class="carousel-wr">
        <Carousel 
            class="carousel"

            :items-to-show="3"
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
        let res = await feedbackAPI.getList(offset.value, 9);

        let l = res.content;

        offset.value++;

        if(res.totalPages <= offset.value || !l || !l.length){
            stop.value = true;
            return;
        }

        list.value.push(...l);
        console.log(list.value);
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