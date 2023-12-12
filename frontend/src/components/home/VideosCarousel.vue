<template>
    <div class="carousel-wr">
        <Carousel 
            class="carousel"

            :items-to-show="1"
            :wrap-around="false"
            v-model="slideId"
            ref="carRef"
        >
           <Slide class="slide" v-for="(i,k) in links" :key="k">
                <div class="vid-wr">
                    <VLoading class="loader"/>
                    <YoutubeVue3 v-if="slideId == k" :ref="e => i.ref = e" :videoid="i.id"/>
                </div>
           </Slide>
        </Carousel>

        <SliderArrows
            @prev="carRef.prev()" 
            @next="carRef.next()"
        />

        <!-- <div class="vid-wr">
            <YoutubeVue3 :ref="e => ytRefs[0] = e" :videoid="links[0].id"/>
        </div>
        <VButton @click="ytRefs[0]?.player?.stopVideo()">SUS</VButton> -->
    </div>
</template>

<script setup>
    import 'vue3-carousel/dist/carousel.css'
    import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel'

    import { YoutubeVue3 } from 'youtube-vue3'

    import SliderArrows from "@/components/ui/SliderArrows.vue";
    
    import { onMounted, ref, watch } from 'vue';

    const carRef = ref();

    const slideId = ref(0);

    const links = ref([
        {id: 'GPoZUPrzm0k'},
        {id: '5gd13Bh2oM4'},
        {id: 'AVQTYN4gFzw'},
        {id: 'SUinKbTo6UI'},
        {id: '8hTIX4Srgp8'},
        {id: 'ziPX84XTsn0'},
        {id: 'PPiYcaDAN7Y'},
        {id: 'JOtGnkPmuaM'},
        {id: '8VHXc6IZGLc'},
    ]
        .map(value => ({ value, sort: Math.random() }))
        .sort((a, b) => a.sort - b.sort)
        .map(({ value }) => value)
    )

    const stopAll = ()=>{
        links.value.forEach(e => {
            e.ref?.player?.stopVideo()
        });
    }

    watch(slideId, stopAll);

    onMounted(()=>{
        setTimeout(stopAll);
    })
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
            position: relative;
            width: 60rem;
            height: calc(var(--w) * 0.53333);
            background: var(--c-white);
            border-radius: 2.5rem;
            overflow: hidden;

            :deep(iframe){
                position: relative;
                height: 100%;
                width: 100%;
                z-index: 1;
            }
        }
    }

    .loader{
        height: 100%;
        position: absolute;
        left: 0;
        right: 0;
        margin: auto;
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