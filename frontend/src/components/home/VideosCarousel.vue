<template>
    <div class="carousel-wr">
        <Carousel 
            class="carousel"

            :items-to-show="1"
            :wrap-around="true"
            v-model="slideId"
            ref="carRef"
        >
           <Slide class="slide" v-for="(i,k) in links" :key="k">
                <div class="vid-wr">
                    <iframe :src="i" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
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

    const links = ref([
        'https://www.youtube.com/embed/GPoZUPrzm0k',
        'https://www.youtube.com/embed/5gd13Bh2oM4',
        'https://www.youtube.com/embed/AVQTYN4gFzw',
        'https://www.youtube.com/embed/SUinKbTo6UI',
        'https://www.youtube.com/embed/8hTIX4Srgp8',
        'https://www.youtube.com/embed/ziPX84XTsn0',
        'https://www.youtube.com/embed/PPiYcaDAN7Y',
        'https://www.youtube.com/embed/JOtGnkPmuaM',
    ])
</script>

<style lang="scss" scoped>
    .slide{
        padding: 0 1.6rem 1.6rem;
    }

    .carousel-wr{
        position: relative;
        width: calc(60rem + 3.2rem);

        --w: 60rem;

        .vid-wr{
            width: 60rem;
            height: calc(var(--w) * 0.53333);
            background: var(--c-white);
            border-radius: 2.5rem;
            overflow: hidden;

            iframe{
                height: 100%;
                width: 100%;
            }
        }
    }

    

    @media (max-width: $mobile-med){
        .carousel-wr{
            --w: calc(100vw - var(--generic-padding) * 2);
            width: var(--w);

            .vid-wr{
                width: 100%;
                height: calc(var(--w) * 0.53333);
            }
        }
    }
</style>