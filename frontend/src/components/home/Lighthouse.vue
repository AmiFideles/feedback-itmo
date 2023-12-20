<template>
    <div class="lighthouse">
        
        <div class="intro">
            <h1>Нет на&nbsp;свете излишества прекраснее, чем&nbsp;излишек благодарности</h1>
        </div>

        <div class="sprite-wr">
            <div class="sprite-wr-wr">
                <img class="sprite" src="./lighthouse.svg" alt="">
                <div class="spark"></div>
                <div class="ray-wr">
                    <img class="ray" src="./ray.svg" alt="">
                    <img class="ray" src="./ray.svg" alt="">
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
    .lighthouse{
        height: calc(100vh);
        position: relative;

        .sprite-wr{
            position: absolute;
            right: calc(var(--generic-padding) * 2);
            bottom: calc(5%);
            height: calc(90% - 18rem);

            --period: 10s;

            .sprite-wr-wr{
                position: relative;
                height: 100%;
                width: 100%;

                perspective: 200vh;
                perspective-origin: 66% 11%;
                transform-style: preserve-3d;
            }

            .sprite{
                height: 100%;
            }

            .ray-wr{
                position: absolute;
                display: flex;
                z-index: -1;
                top: -34%;
                right: 34%;
                gap: 2.1%;
                width: 0;
                height: 100%;
                animation: rotate var(--period) linear infinite;
                pointer-events: none;

                .ray{
                    display: block;
                    height: 160%;

                    

                    &:first-child{
                        transform: translateX(-102%);
                    }

                    &:last-child{
                        transform: translateX(-98%) scaleX(-1);
                    }
                }
            }

            .spark{
                position: absolute;
                bottom: 73%;
                right: -6%;
                width: 80%;
                aspect-ratio: 1;
                background: radial-gradient( #FFF 10%, #FFFFFF00 70%);
                border-radius: 50%;
                
                animation: pulse calc(var(--period) * 0.25) linear infinite alternate;
            }

            @keyframes pulse {
                0% {opacity: 0;}
                100% {opacity: 1;}
            }

            @keyframes rotate {
                0%   {transform: rotateY(calc(1turn) * 0); opacity: 0;}

                20%  {transform: rotateY(calc(1turn) * .2); opacity: 1;}
                25%  {transform: rotateY(calc(1turn) * .25); opacity: 0;}
                30%  {transform: rotateY(calc(1turn) * .3); opacity: 1;}

                50%  {transform: rotateY(calc(1turn) * .5); opacity: 0;}

                70%  {transform: rotateY(calc(1turn) * .7); opacity: 1;}
                75%  {transform: rotateY(calc(1turn) * .75); opacity: 0;}
                80%  {transform: rotateY(calc(1turn) * .8); opacity: 1;}

                100% {transform: rotateY(calc(1turn) * 1); opacity: 0;}
            }
        }

        .intro{
            width: 100%;
            font-weight: 700;
            height: 100%;
            @include flex-c;
            padding-bottom: 5.2rem;
            position: relative;
            z-index: 1;
            padding-right: 40vh;
        }

        .ribbons{
            height: 6.2rem;
            position: absolute;
        }

        @media (max-width: $pad) {
            height: 100vh;

            h1{
                font-size: 4.4rem;
            }

            .intro{
                padding-right: 20vh;
            }
        }

        @media (max-width: $mobile-big) {
            height: auto;

            h1{
                font-size: 3.2rem;
            }

            .sprite-wr{
                height: calc(100% - var(--generic-padding) * 2 - 5.2rem);
            }

            .intro{
                padding-top: 7.7rem;
                padding-bottom: 7.7rem;
                padding-right: 30%;
            }
        }

        @media (max-width: $mobile-med) {
            h1{
                font-size: 2.4rem;
            }

            .sprite-wr{
                height: 20rem;
            }

            .intro{
                max-width: unset;
                padding: 7.7rem 0;
                padding-bottom: calc(7.7rem + 20rem);
            }
        }
    }
</style>