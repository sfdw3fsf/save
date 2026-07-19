<template>
    <div>
        <vue-bread-crumb :header="header" />
        <vue-nav>
            <ul class="list">
                <li>
                    <a @click="showPopup()">
                        <span class="icon one-save"></span> Show popup
                    </a>
                </li>                
            </ul>
        </vue-nav>
        <Thaydoimk ref=Thaydoimk />
    </div>
</template>
  
<script>
import api from './api/index.js'

import Thaydoimk from './popup/thaydoimk.vue';
export default {
    components: {
        Thaydoimk,
    } ,
    
    data() {
        return {
          header: {
                title: "Đổi mật khẩu ADSL, FTTH, MyTV",
                list: [
                { name: "Danh mục", link: { name: "Ui.cards" } },
                {
                    name: "Đổi mật khẩu ADSL, FTTH, MyTV",
                    link: { name: "Ui.buttons" },
                },
                ],
            },
                      
        }
    },
    mounted() {

    },
    computed: {

    },
    watch: {

    },
    methods: {
        showPopup(){
            this.$refs.Thaydoimk.show()
        },

        alertMessage(message) {
            this.$bvToast.toast(message, {
                title: 'Thông báo',
                toaster: 'b-toaster-bottom-right',
                autoHideDelay: 5000,
                appendToast: false
            })
        },

        showModal() {
            this.$refs['ref-modal'].show()
        },

        hideModal() {
            this.$refs['ref-modal'].hide()
        },
    }
}
</script>