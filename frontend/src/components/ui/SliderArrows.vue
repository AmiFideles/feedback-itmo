<template>
    <div class="sliderArrows">
        <div class="arrow prev" @click="emit('prev')"><ISСrollArr/></div>
        <div class="arrow next" @click="emit('next')"><ISСrollArr/></div>
    </div>
</template>

<script setup>
    import ISСrollArr from "@/components/icons/ISСrollArr.vue";

    const emit = defineEmits(['prev', 'next'])
</script>

<style lang="scss" scoped>
    .sliderArrows{
        position: static;
    }

    .arrow{
        position: absolute;
        top: 0;
        bottom: 0;
        margin: auto;

        width: 5rem;
        height: 5rem;

        cursor: pointer;

        @include flex-c;

        &.prev{
            right: calc(100% + 1rem);
            transform: rotate(-.25turn);
        }

        &.next{
            left: calc(100% + 1rem);
            transform: rotate(.25turn);
        }

        svg{
            width: 45%;
            transition: .3s;
            will-change: transform;
        }

        &:active{
            svg{
                transition: .1s;
                transform: translateX(20%);
            }
        }

        &.next{
            &:active{
                svg{
                    transform: translateX(-20%);
                }
            }
        }

        @media (hover: hover){
            &:hover{
                svg{
                    transform: translateY(-30%);
                }
            }

            &:active{
                svg{
                    transform: translateY(-30%) translateX(-20%);
                }
            }

            &.next{
                &:active{
                    svg{
                        transform: translateY(-30%) translateX(20%);
                    }
                }
            }
        }
    }
</style>