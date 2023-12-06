import { defineStore } from 'pinia';
import { ref, computed, reactive, onMounted, watch } from "vue";

import { colorsAPI } from "@/script/api";

export default defineStore("Static", ()=>{

//colors
    const colors = ref([]);

    onMounted(()=>{
        (async ()=>colors.value = await colorsAPI.getList())()
    });

    const getColor=(id)=>colors.value.find(e => e.id == id);
    

// ----------------------------------------------------------------
return {
    colors,
    getColor
}
});
