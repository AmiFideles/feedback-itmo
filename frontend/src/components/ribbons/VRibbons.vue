<template>
    <div class="ribbons">
        <div class="rel-wr">
            <RibTop/>
            <RibBot/>
        </div>
    </div>
</template>

<script setup>
    import RibTop from './RibTop.vue';
    import RibBot from './RibBot.vue';
</script>

<style lang="scss" scoped>
    .ribbons{
        position: absolute;
        left: calc(-1 * var(--generic-padding) / 2);
        right: calc(-1 * var(--generic-padding) / 2);
        margin: auto;
        width: calc(100% + var(--generic-padding));
        max-width: calc(200rem + var(--generic-padding));
        pointer-events: none;
        z-index: 0;
        
        .rel-wr{
            position: relative;
        }

        --c1: #E9B300;
        --c2: #FEFE00;

        svg{
            position: relative;
            @include all-directions(0);
            width: 100%;
            height: auto;

            color: var(--c2);

            &:first-child{
                transform: translateY(-31%);
                color: var(--c1);
            }

            &:last-child{
                position: absolute;
            }
        }
    }
</style>