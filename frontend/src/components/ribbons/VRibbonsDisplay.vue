<template>
    <div class="ribbons-display">
        <VRibbons :style="{top: r.top + 'px', '--c1': r.color}"/>
    </div>
</template>

<script setup>
    import VRibbons from "./VRibbons.vue";
    import { onMounted, onUnmounted, ref } from "vue";

    import { convertRemToPixels } from "@/script/helpers/num.js";

    onMounted(()=>{
        (document.body).addEventListener('scroll', update);
        window.addEventListener('resize', update);
        setTimeout(update, 1);
    });
    onUnmounted(()=>{
        (document.body).removeEventListener('scroll', update);
        window.removeEventListener('resize', update);
    });

    const ribRef = ref();

    const colors = [
        '#E9B300',
        '#FFDB55',
        '#DBCE02',
    ]

    const r = ref(
        {
            top: -1000000,
            color: '#E9B300'
        }
    )

    const update = () => {
        const windowHeight = window.innerHeight;

        const bump =  
            window.innerWidth > 800? 
                windowHeight * .74 : 
            window.innerWidth > 640?
                convertRemToPixels(31):
                convertRemToPixels(48.5);

        const screen = Math.ceil((document.body.scrollTop+1) / windowHeight) - 1;

        const dRatio = 1.78;
        const domain = Math.floor((screen+1)/dRatio);

        // console.log(screen , domain, bump);

        r.value.top = windowHeight * dRatio * domain + bump;

        if(r.value.top + document.querySelector('.ribbons-display .ribbons').offsetHeight > document.getElementById('app').offsetHeight)r.value.top = -1000000;
        
        // console.log(
        //     r.value.top + document.querySelector('.ribbons-display .ribbons').offsetHeight, 
        //     document.getElementById('app').offsetHeight
        // );

        //colors
            r.value.color = colors[domain%colors.length];
    }
</script>

<style lang="scss" scoped>
    .ribbons-display{
        position: static;
    }
</style>