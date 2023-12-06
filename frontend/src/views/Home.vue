<template>
    <div class="page-wr">
        <div class="lighthouse-wr">
            <Lighthouse class="cas-wr"/>
        </div>

        <!-- <div class="ribbons">
            <img src="@/components/icons/ribbons/IRib1.svg" alt="">
            <img src="@/components/icons/ribbons/IRib2.svg" alt="">
        </div> -->

        <div class="about cas-wr">
            <p>
                В&nbsp;год педагога и&nbsp;наставника хочется сказать спасибо всем, кто однажды вдохновил, поддержал, поверил, направил, вовремя оказался рядом. Мы&nbsp;точно знаем, что успех каждого человека&nbsp;&mdash; это его усилия, труд и&nbsp;вовремя полученный ценный совет. 
            </p>
        </div>

        <div class="carousel cas-wr">
            <LettersCarousel/>
            <div class="btn-wr">
                <RouterLink :to="{name: 'List'}">
                    <VButton med>Смотреть все послания</VButton>
                </RouterLink>
            </div>
        </div>
        
        <div class="about cas-wr">
            <p>
                Поделитесь с&nbsp;нами невероятными историями о&nbsp;ваших наставниках, напишите несколько слов благодарности, расскажите о&nbsp;том, кто стал для вас маяком, указавшим путь в&nbsp;большом плавании жизни.
            </p>
            <VButton big @click="R().pushQuery({lsend: 'true'})">Оставить послание</VButton>
        </div>

        <div class="vids-wr about cas-wr">
            <p>
                Видееоинтервью с&nbsp;преподавателями, менторами, адаптерами и&nbsp;эдвайзерами ИТМО
            </p>
            <!-- <VideosCarousel/> -->
        </div>


    </div>
</template>

<script setup>
    import Lighthouse from "@/components/home/Lighthouse.vue";
    import LettersCarousel from "@/components/letter/LettersCarousel.vue";
    import VideosCarousel from "@/components/home/VideosCarousel.vue";

    import R from "@/stores/Router.js";
</script>

<style lang="scss" scoped>

    .page-wr{
        @include flex-col;
        gap: 12.8rem;
        padding-bottom: 12.8rem;
    }

    .lighthouse-wr{
        overflow: hidden;
        width: 100%;
    }

    .about{
        @include flex-col;
        align-items: center;
        gap: 6.4rem;

        p{
            font-size: 3.2rem;
            text-align: center;
        }
    }

    .carousel{
        .btn-wr{
            @include flex-c;
            padding-top: 6.4rem;
        }
    }

    .vids-wr{
        flex-direction: row;
        align-items: center;

        P{
            text-align: left;
        }
    }
</style>