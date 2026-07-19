
<template src="./SearchSMSs.html"></template>
<style scoped src="./SearchSMSs.scss"></style>
<script>
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
import {
    required,
    maxLength,
    minLength,
    numeric
} from "vuelidate/lib/validators"
import SearchAccountModal from './popups/SearchAccountModal.vue'
import XLSX from "xlsx";
import moment from 'moment'
import api from "./api/index.js";
export default {
    components: {
        appCombobox: ComboboxGrid,
        SearchAccountModal
    },
    name: 'SearchSMSs', 
    async created() {
    },
    data() {
        return {
            dateFormat: 'dd/MM/yyyy',
            formData: {
                thuebao_id: 0,
                dichvuvt_id: 0,
                ma_tb: ''
            },
            isDisabled: true,
            items: {
                ma_tb:"",
                so_dt:"",
                ma_kh: ""

            },
            header: {
                title: "Tra cứu SMS",
                list: [{
                        name: "Nghiệm vụ quản lý",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Kênh bán",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            DsSMSs: []
        }
    },
    mounted() {
        
    },
    methods: {
        colLinkQR (parent) {
            console.log(parent)
              return function () {
                    return {
                    template: {
                        template: `
                                      <div class="row">
                                      <div class="col-md-8" style='text-align: left'>

                                        <a v-if="data.shortenLink" 
                                        :href="data.shortenLink" 
                                        target="_blank" 
                                        :title="data.shortenLink"
                                        style="color: blue; text-decoration: underline;">
                                        {{ data.shortenLink }}
                                        </a></div>
                                       <div class="col-md-4" style='text-align: right; color:blue'> <a type="button" @click="resendQrCode(data)" title="Tạo lại link QrCode"> <span class="icon one-reload"></span></a></div>
                                    </div>
                                    `,
                        data() {
                            return {
                                data: {},
                                isEditing: false,
                                thutu_new: "0"
                            }
                        },
                        mounted(){
                            console.log(2222222222222)
                            console.log(parent)
                            console.log(DsSMSs)
                            console.log(this.data)
                        },
                        computed: {
                            DsSMSs(){   
                                return this.$DsSMSs.$DsSMSs.$DsSMSs.$DsSMSs
                            },
                            parent() {
                            console.log(parent,"parent")
                                
                                return this.$parent.$parent.$parent.$parent
                            }
                        },
                        methods: {
                            onViewButtonClick() {
                                if (this.data.shortenLink) {
                                    window.open(this.data.shortenLink, '_blank');
                                } else {
                                    console.error("Link không tồn tại!");
                                }
                            },
                            resendQrCode(data){
                                //data.customerId
                                api.fn_kiemtra_qr_thanhtoan(this.parent.axios, {"p_ma_gd":data.customerId}).then(res => {
                                    if (res.data.error_code === 'BSS-00000000') {
                                        if (res.data.data  != "0") {
                                        this.$toast.success('QrCode đã tồn tại, không thể gửi lại mã QRCode');
                                        } 
                                        else{
                                        api.genQRByMaGD(this.parent.axios, {"ma_gd":data.customerId}).then(res => {
                                            if (res.data.error_code === 'BSS-00000000') {
                                                parent.getDsSMSs();
                                                this.$toast.success('Gửi lại mã QRCode thành công');
                                            } else {
                                                this.$toast.error('Gửi lại mã QRCode không thành công');
                                            }
                                        })
                                    };
                                    } else {
                                        this.$toast.error('Gửi lại mã QRCode không thành công');
                                    }
                                }).catch(err => {
                                    this.$toast.error('Gửi lại mã QRCode không thành công');
                                });
                            },
                        },
                    }
                }
            }
        },
        async acceptSearchAccount(item) {
            this.items.ma_tb = item.ma_tb;
        },
        showPopupSearchAccount() {
            this.$refs.searchAccountModal.showModal();
        },
        async getDsSMSs(){
            if(this.items.so_dt == ""){
                this.$toast.warning("Vui lòng nhập số điện thoại để tìm kiếm!");
                return;
            }
            let input = {
                "so_dt": this.items.so_dt,
                "ma_tb": this.items.ma_tb, 
                "ma_gd": this.items.ma_kh,
                "dichvu": "",
                "ma_tinh": ""//this.$auth.getPhanVungID()
            }
            let res = await api.tracuuSMS(this.axios,input);
            console.log(res.data.data)
            this.DsSMSs = res.data.data;
            console.log(11111111111)
            console.log(this.DsSMSs)
        },
        async txtMaKH_KeyPress(){
            this.getDsSMSs();
        },
        async btnTimkiemClick(){
            this.getDsSMSs();
        },
        GetDataList: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return []
        },
        GetData: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
                return null
            } else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return null
        }
    }
}
</script>
<style>
.info-row .value{
    padding-left: 0px !important;
}
.select2-container .select2-selection--single{
    border: 1px solid #ced4da !important
}
.pd-l0{
    padding-left: 0px !important;
}
.bd-cl0{
    border-color: gray !important
}
.bd-0{
    border-color: gray !important
}
</style>