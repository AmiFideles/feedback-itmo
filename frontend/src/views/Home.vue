<template>
    <div class="page-wr">
        <div class="lighthouse-wr">
            <Lighthouse class="cas-wr cas-wr-primary"/>
        </div>

        <!-- <div class="ribbons">
            <img src="@/components/icons/ribbons/IRib1.svg" alt="">
            <img src="@/components/icons/ribbons/IRib2.svg" alt="">
        </div> -->

        <div class="about cas-wr">
            <p>Мы&nbsp;точно знаем, что успех каждого человека&nbsp;&mdash; это его усилия, труд и&nbsp;вовремя полученный ценный совет. Вспомните того, кто однажды вдохновил, поддержал, поверил, оказался рядом&nbsp;&mdash; вашего преподавателя, наставника, коллегу. Быть признательным и&nbsp;не&nbsp;бояться рассказать об&nbsp;этом всему миру&nbsp;&mdash; это стиль жизни Университета ИТМО. Присоединяйтесь, <a href="https://be.itmo.ru/" target="_blank">be&nbsp;ITMO</a>!</p>
        </div>

        <div class="carousel cas-wr">
            <LettersCarousel/>
            <div class="btn-wr">
                <RouterLink :to="{name: 'List'}">
                    <VButton med>Смотреть все послания</VButton>
                </RouterLink>
                <div class="count">
                    <p>Счетчик посланий</p>
                    <p class="num">{{round(Static().lettersCount || 0, 0, {splitThree: true})}}</p>
                </div>
            </div>
        </div>
        
        <div class="about cas-wr">
            <p>Поделитесь с&nbsp;нами историями о&nbsp;ваших наставниках, напишите несколько слов благодарности, расскажите о&nbsp;том, кто стал для вас маяком, указавшим путь в&nbsp;большом плавании жизни</p>
            <VButton big @click="R().pushQuery({lsend: 'true'})">Оставить послание</VButton>
        </div>

        <div class="vids-wr thin-wr about cas-wr">
            <p>Видеоинтервью с&nbsp;преподавателями, менторами, адаптерами и&nbsp;эдвайзерами ИТМО</p>
            <VideosCarousel/>
        </div>

        <div class="team about cas-wr">
            <p>Команда проекта</p>
            <div class="team-wr">
                <a :href="i.href" class="sh-link" v-for="(i,k) in team" :key="k" target="_blank">
                    <img :src="i.img" alt="" :style="{height: i.h}">
                </a>
            </div>
        </div>

        <div class="about thin-wr feedback-wr cas-wr">
            <p>Обратная связь по&nbsp;проекту</p>
            <VButton big @click="$refs.feedbackModal.modal.call()">Есть предложения!</VButton>
        </div>

        <FeedbackModal ref="feedbackModal"/>
    </div>
</template>

<script setup>
    import Lighthouse from "@/components/home/Lighthouse.vue";
    import LettersCarousel from "@/components/letter/LettersCarousel.vue";
    import VideosCarousel from "@/components/home/VideosCarousel.vue";
    import FeedbackModal from "@/components/feedback/FeedbackModal.vue";

    import R from "@/stores/Router.js";
    import Static from "@/stores/Static.js";

    import { round } from "@/script/helpers/num.js"
    
    import { computed, ref } from "vue";

    const team = computed(() => [
        {href: "https://expert.itmo.ru/", img: "/img/team/3.png", h: "3.5rem"},
        {href: "https://be.itmo.ru/", img: "/img/team/0.svg", h: "8.7rem"},
        {href: "https://news.itmo.ru/ru/university_live/social_activity/news/13259/", img: "/img/team/1.png", h: "8.7rem"},
        {href: "https://alumni.itmo.ru/", img: "/img/team/2.png", h: "3.5rem"},
    ]);
    
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

        a{
            text-decoration: underline;
        }

        a:hover{
            text-decoration: none;
        }
    }

    .carousel{
        .btn-wr{
            @include flex-c;
            padding-top: 6.4rem;

            gap: 1.6rem 12.8rem;
            flex-wrap: wrap;

            .count{
                font-size: 2.4rem;
                display: flex;
                align-items: baseline;
                justify-content: center;
                flex-wrap: wrap;
                
                p{
                    padding: .5em 0;
                }

                .num{
                    margin-left: 1em;
                    background: var(--c-orange-field);
                    border-radius: 5rem;
                    padding-left: 2.08em;
                    padding-right: 2.08em;
                }
            }
        }
    }

    .vids-wr{
        flex-direction: row;
        align-items: center;

        P{
            text-align: left;
            padding-right: var(--generic-padding);
        }

        @media (max-width: 1485px){
            flex-direction: column;

            p{
                text-align: center;
            }
        }
    }

    .about.team{
        flex-direction: row;
        flex-wrap: wrap;
        gap: 1.6rem 6.4rem;
        justify-content: center;

        .team-wr{
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 1.6rem 6.6rem;
        }
    }

    .about.feedback-wr{
        flex-direction: row;
        justify-content: center;
        gap: 1.6rem 12.8rem;
        flex-wrap: wrap;
    }

    .thin-wr{
        padding-left: calc(var(--generic-padding) * 2);
        padding-right: calc(var(--generic-padding) * 2);
    }

    @media (max-width: $mobile-big){
        .page-wr, .about{
            gap: 4.8rem;
        }

        .about p{
            text-align: start;
            font-size: 1.6rem;
        }

        .carousel .btn-wr .count{
            font-size: 1.6rem;
        }

        .btn{
            font-size: 1.6rem!important;
        }

        .team{
            flex-direction: column;

            &-wr{
                justify-content: center;
            }
        }

        .thin-wr{
            padding-left: var(--generic-padding);
            padding-right: var(--generic-padding);
        }
    }
</style>