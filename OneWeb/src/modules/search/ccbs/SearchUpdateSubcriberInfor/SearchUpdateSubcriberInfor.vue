
<template src='./SearchUpdateSubcriberInfor.html'></template>

<style scoped src='./SearchUpdateSubcriberInfor.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css'
import moment from 'moment'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    components: {
        VueElement,
        DataGridCustom,
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            ...collums,
            header: {
                title: "Tra cứu, Cập nhật thông tin thuê bao",
                list: [{
                        name: "Lập hợp đồng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Lắp đặt mới",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            Fn: {
                NhapMoi: false,
                GhiLai: false,
                HuyBo: false,
                Xoa: false,
            },
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            dataSelected: {
              
            },
            options: {
             
            },
        };
    },
    mounted() {
        this.init();
    },
    computed: {

    },
    validations: {

    },
    methods: {
        async init() {
            try {
              
            } catch (e) {
                console.log(e);
            }
        },
        
    },
    watch: {}
};
</script>
