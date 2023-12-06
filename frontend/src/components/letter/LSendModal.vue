<template>
    <VModal 
        ref="modal" 
        class="l-modal" 
        @close="close"
        :style="{
            '--sh': activeColor?.color,
            '--bg': activeColor?.light
        }"
    >
        <template #title>
            <h2>Оставить послание</h2>
        </template>

        <div class="rel-wr">
            <div class="success" :show="success || null">
                <h3>Ваше послание успешно отравлено!</h3>
            </div>
            <div class="form" :show="!success || null">
                <div class="inp-wr" v-for="(i,k) in data" :key="k">
                    <textarea 
                        v-if="k == 'messageText'" 
                        :placeholder="i.title" 
                        v-model="i.value"  
                        rows="3"
                        oninput="this.style.height = ''; this.style.height = (this.scrollHeight) +'px'"
                    />
                    <VSelect 
                        v-else-if="i.list" 
                        v-model="i.value" 
                        :list="i.list" 
                        keyName="name"
                        :style="{
                            '--bg': 'var(--c-white)',
                            '--hov': activeColor?.color,
                        }"

                        :placeholder="i.title"

                        :rev="k == 'prompt' || null"
                    />
                    <VTextInput v-else v-model="i.value" :placeholder="i.title"/>
                </div>
                <VErr :err="err"/>
            </div>
        </div>

        <div class="rel-wr"> 
            <div class="success-controls" :show="success || null">
                <VButton 
                    @click="success = false"
                    :style="{
                        '--bg': activeColor?.color,
                        '--hov': activeColor?.light,
                    }"
                >
                    Отправить еще одно послание
                </VButton>
                <VButton 
                    @click="close"
                    :style="{
                        '--bg': activeColor?.color,
                        '--hov': activeColor?.light,
                    }"
                >
                    Назад
                </VButton>
            </div>
            <div class="controls" :show="!success || null">
                <div class="colors">
                    <VButton
                        class="color" 
                        v-for="(i,k) in colors"
                        :key="k"
                        @click="activeColor = i" 
                        :style="{
                            '--bg': i.color,
                            '--hov': i.light,
                        }"
                    />
                </div>
                <VButton 
                    @click="confirm" 
                    :loading="loading || null"
                    :style="{
                        '--bg': activeColor?.color,
                        '--hov': activeColor?.light,
                    }"
                >
                    Отправить
                </VButton>
            </div>
        </div> 
    </VModal>
</template>

<script setup>
    import { computed, ref, watch } from 'vue';

    import R from "@/stores/Router.js";
    import Static from "@/stores/Static.js";
    
    import { feedbackAPI } from "@/script/api";
    import { validateEmail } from "@/script/helpers/validations.js";

    import Moment from 'moment';
    import { extendMoment } from 'moment-range';
    const moment = extendMoment(Moment);

    const modal = ref();

    const data = ref({});

    watch(()=>R().query?.lsend, (n)=>{
        if(n){
            success.value = false;
            data.value = {
                name: {title: "Ваши имя и фамилия", value: ""},
                graduationYear: {title: "Ваши имя и фамилия", value: years.value[0], list: years.value},
                faculty: {title: "Ваше подразделение в ИТМО", value: ""},
                mentorName: {title: "Имя и фамилия наставника", value: ""},
                mentorEmail: {title: "Email наставника", value: ""},
                messageText: {title: "Текст послания", value: ""},
                prompt: {title: "Выберите послание", value: null, list: prompts},
            }

            //tmp
            // data.value = {
            //     name: {title: "Ваши имя и фамилия", value: "Test Boy"},
            //     graduationYear: {title: "Ваши имя и фамилия", value: years.value[0], list: years.value},
            //     faculty: {title: "Ваше подразделение в ИТМО", value: "Test_Fac"},
            //     mentorName: {title: "Имя и фамилия наставника", value: "Testor"},
            //     mentorEmail: {title: "Email наставника", value: ""},
            //     messageText: {title: "Текст послания", value: "Test\n\nText\n\nBoy"},
            //     prompt: {title: "Выберите послание", value: prompts.value[1], list: prompts},
            // }

            modal.value.call();
        }else{
            modal.value.close()
        }
    })

    const years = computed(()=>[
        ...Array.from(moment.range('1900', new Date()).by('year')).map(m => {return {name: m.format('YYYY'), value: m.format('YYYY')}}).reverse()
    ]);

    const prompts = computed(()=>[
        {name: 'Без послания', value: null},
        {name: 'Спасибо за талант и веру в нас!', value: 'Спасибо за талант и веру в нас!'},
        {name: 'Спасибо за вдохновение и пример!', value: 'Спасибо за вдохновение и пример!'},
        {name: 'Спасибо за самый нужный совет!', value: 'Спасибо за самый нужный совет!'},
        {name: 'Спасибо за помощь и знания!', value: 'Спасибо за помощь и знания!'},
    ]);

//colors
    const colors = computed(()=>Static().colors);
    const activeColor = ref(colors.value[0]);

    watch(()=>colors.value.length, ()=>activeColor.value = colors.value[0])
    

//confirm
    const loading = ref();
    const err = ref('');

    const success = ref(false);

    const confirm = async ()=>{
        loading.value = true;
        err.value = '';

        const getValue = (key) =>data.value[key].value;

        let toSend = {
            "lastName": getValue('name').split(' ')[1],
            "firstName": getValue('name').split(' ')[0],
            "graduationYear": parseInt(getValue('graduationYear').value),
            "faculty": getValue('faculty'),
            "mentorName": getValue('mentorName'),
            "messageText": getValue('messageText') + (getValue('prompt')?.value?`\n\n${getValue('prompt').value}`:''),
            "mentorEmail": getValue('mentorEmail'),
            "color": activeColor.value.id
        }

        if(!toSend.firstName)err.value = 'Заполните имя';
        if(!toSend.mentorName)err.value = 'Заполните ФИО наставника';
        if(!toSend.messageText)err.value = 'Оставьте послание';
        if(toSend.mentorEmail && !validateEmail(toSend.mentorEmail))err.value = 'Неверный Email';

        if(err.value){
            loading.value = false;
            return;
        }

        let res = await feedbackAPI.add(toSend).catch(
            error => err.value = error.message || error
        )

        console.log(res);

        loading.value = false;

        if(!err.value){
            success.value = true;
        }
    }

//close
    const close = ()=>{
        if(loading.value)return;
        R().pushQuery({lsend: null})
    }
    
</script>

<style lang="scss" scoped>
    h2{
        font-size: 2.4rem;
        font-weight: 400;
        @include text-overflow;
        min-height: 1em;
    }

    .l-modal.modal :deep(.container){
        max-height: unset;
    }

    .form{
        @include flex-col;
        gap: 1.6rem;
        padding: 3.2rem 0;
        font-size: 1.4rem;

        :deep(.select){
            font-size: 1.4rem;
            text-align: left;

            .container, .list-item{
                padding: 1.072em 1.714em;
            }
        }

        
    }

    textarea{
        background: var(--c-white);
        border-radius: 1.643em;
        padding: 1.072em 1.714em;
        width: 100%;
        width: 100%;
        border: none;
        resize: none;
        display: block;
        // max-height: 12em;

        &::-webkit-scrollbar{
            display: none;
        }

        &::placeholder{
            color: var(--c-grey-dark);
        }
    }

    

    .controls{
        @include flex-jtf;
        gap: 1.6rem;

        .colors{
            display: flex;
            gap: 1.6rem;

            .color{
                width: 3.6rem;
                height: 3.6rem;
                padding: 0;
                border: 1px solid var(--c-grey-dark);
            }
        }
    }

//success
    .rel-wr{
        position: relative;
    }

    .success, 
    .success-controls{
        position: absolute;
        text-align: center;
        position: absolute;
        height: 100%;
        width: 100%;
        @include flex-c;
        gap: 1.6rem;
        transition: .3s;
    }

    .success-controls{
        justify-content: end;
    }

    .success, 
    .success-controls,
    .form,
    .controls{
        transition: .3s;

        &:not([show]){
            @include hidden(1rem);
        }
    }
</style>