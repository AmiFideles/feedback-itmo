<template>
    <footer>
        <div class="footer-wr">
            <div class="links">
                <div class="container">
                    <div class="logo"><ILogo /></div>
                    <div>Программа развития ИТМО 2023</div>
                    <div>Разработка &copy;&nbsp;2023</div>
                    <div>Информация &copy;&nbsp;Университет ИТМО </div>
                </div>
                <div class="container">
                    <div>г. Санкт-Петербург, Кронверкский пр., 49</div>
                    <div class="a-wr">
                        <a href="mailto:expert@itmo.ru">expert@itmo.ru</a>
                        <a href="mailto:be@itmo.ru">be@itmo.ru</a>
                    </div>
                    <a href="https://itmo.ru/images/pages/79/Pravila_ispolzovanija_informacii.pdf" target="_blank">Правила использования информации<br />в&nbsp;доменной зоне itmo.ru</a>
                    <a href="https://itmo.ru/file/pages/79/personal_data_policy.pdf" target="_blank">Политика по&nbsp;обработке Персональных данных</a>
                </div>
            </div>
        </div>
    </footer>
</template>

<script setup>
    import ILogo from "@/components/icons/ILogo.vue";
</script>

<style lang="scss" scoped>
    footer{
        background: #000;
        color: var(--c-grey-black);
        padding: 8.7rem;
        font-size: 1.5rem;
    }

    .footer-wr{
        max-width: 125.6rem;
        width: 100%;
        margin: 0 auto;
    }

    .logo{
        color: var(--c-white);
        height: 4.1rem;
        margin-bottom: 1.6rem;

        svg{
            height: 100%
        }
    }

    .links{
        @include flex-jtf;
        align-items: end;
        width: 100%;
        gap: 3rem;

        .container{
            @include flex-col;
            gap: .6rem;

            &:last-child{
                text-align: end;
            }
        }

        a{
            display: block;
            transition: .3s;

            &:hover{
                color: var(--c-white);
            }
        }

        .a-wr{
            display: flex;
            gap: .5em;
            justify-content: end;
        }
    }

    @media (max-width: $pad){
        footer{
            padding: 6.4rem 4.1rem;
        }

        .footer-wr{
            width: max-content;
        }

        .logo{
            @include flex-c;
        }

        .links{
            flex-direction: column;
            align-items: start;
            gap: .5rem;
            width: 33rem;

            .container{
                &:last-child{
                    text-align: start;
                }
            }

            .a-wr{
                justify-content: start;
            }
        }
    }
</style>