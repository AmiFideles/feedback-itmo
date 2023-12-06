<template>
    <div class="btn" :loading="loading || null">
        <VLoading 
            v-if="loading" 
            hollow
            :inversed="!hollow || null"
            class="loading"
        />
        <div class="content" :style="{opacity: loading?0:1}">
            <slot/>
        </div>
    </div>
</template>

<script setup>
    const props = defineProps({
        loading: Boolean,
    })
</script>

<style lang="scss" scoped>
    .btn{
        --bg: var(--c-lime-light);
        --txt: var(--c-black);
        --hov: var(--c-lime-hov);

        &[orange]{
            --bg: var(--c-orange-light);
            --hov: var(--c-orange-hov);
        }

        &[disabled]{
            pointer-events: none;
            --bg: var(--c-grey);
            --hov: var(--c-grey-dark);
        }

        cursor: pointer;
        @include flex-c;
        width: max-content;
        background: var(--bg);
        color: var(--txt);
        border-radius: 1.375em;
        font-size: 1.6rem;
        transition: .3s;
        position: relative;
        padding: 0.5em 1.25em;
        
        &[med]{
            font-size: 2.4rem;
        }
        
        &[big]{
            font-size: 3.2rem;
            padding: 0.38em 1.25em;
        }

        

        .content{
            @include flex-c;
            gap: 1rem;
            width: 100%;
            height: 100%;
            transition: .3s;
            will-change: transform;
            text-align: center;
        }

        .loading{
            position: absolute;
            @include all-directions(0);
            margin: auto;
            max-width: 100%;
            color: var(--txt)!important;
        }

        @media (hover:hover){
            &:hover{
                background: var(--hov);
            }
        }

        &:active{
            transition: 0.01s;
            box-shadow: .2rem .2rem .1rem 0 rgba(0, 0, 0, 0.25) inset;
            
            .content{
                transition: 0.01s;
                transform: translate(.2rem, .2rem);
            }
        }

        &[loading]{
            pointer-events: none;
            animation: pulse .6s linear alternate infinite;
        }

        &[disabled]{
            opacity: .7;
            pointer-events: none;
        }        
    }
</style>