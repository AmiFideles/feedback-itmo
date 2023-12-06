<template>
    <div class="sliderArrows">
        <div class="arrow prev" @click="emit('prev')"><ISCrollArr/></div>
        <div class="arrow next" @click="emit('next')"><ISCrollArr/></div>
    </div>
</template>

<script setup>
    import ISCrollArr from "@/components/icons/ISCrollArr.vue";

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