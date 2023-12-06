<template>
    <div class="carousel-wr">
        <Carousel 
            class="carousel"

            :items-to-show="1"
            :wrap-around="true"
            v-model="slideId"
            ref="carRef"
        >
           <Slide class="slide" v-for="(i,k) in 3" :key="k">
                <div class="vid-wr">
                    <iframe src="https://www.youtube.com/embed/dQw4w9WgXcQ" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
                </div>
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

    import SliderArrows from "@/components/ui/SliderArrows.vue";
    
    import { ref } from 'vue';

    const carRef = ref();

    const slideId = ref(0);
</script>

<style lang="scss" scoped>
    .slide{
        padding: 0 1.6rem 1.6rem;
    }

    .carousel-wr{
        position: relative;
        width: calc(60rem + 3.2rem);
    }

    .vid-wr{
        width: 60rem;
        height: 32rem;
        background: var(--c-white);
        border-radius: 2.5rem;
        overflow: hidden;

        iframe{
            height: 100%;
            width: 100%;
        }
    }
</style>