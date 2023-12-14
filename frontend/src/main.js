import '@/style/reset.scss';
import '@/style/colors.scss';
import '@/style/style.scss';

import { createApp } from 'vue'

import App from './App.vue'

const app = createApp(App)

//libs
    import { createPinia } from 'pinia'
    import router from './router'
    import V3ScrollLock from 'v3-scroll-lock';

    app.use(createPinia())
    app.use(router)
    app.use(V3ScrollLock, { reserveScrollBarGap: true })

//components
    import VLoading from '@/components/ui/VLoading.vue';
    import VButton from '@/components/ui/VButton.vue';
    import VSelect from '@/components/ui/VSelect.vue';
    import VTextInput from '@/components/ui/VTextInput.vue';
    import VTextarea from '@/components/ui/VTextarea.vue';
    import VScrollTop from '@/components/ui/VScrollTop.vue';
    import VModal from '@/components/ui/modal/VModal.vue';
    import VErr from '@/components/ui/VErr.vue';

    app.component('VLoading', VLoading);
    app.component('VButton', VButton);
    app.component('VSelect', VSelect);
    app.component('VTextInput', VTextInput);
    app.component('VTextarea', VTextarea);
    app.component('VScrollTop', VScrollTop);
    app.component('VModal', VModal);
    app.component('VErr', VErr);

app.mount('#app')
