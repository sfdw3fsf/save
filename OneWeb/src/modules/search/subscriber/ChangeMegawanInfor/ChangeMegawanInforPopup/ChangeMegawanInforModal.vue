<template>
    <b-modal
        ref="popupChangeMegawanInfor"
        size="xl"
        hide-footer
        body-class="p-0"
        no-close-on-backdrop
        title="THAY ĐỔI THÔNG SỐ KỸ THUẬT MEGAWAN/METRONET"
        @show="handleShowModal"
    >
    <ChangeMegawanInfor ref="changeMegawanInfor" :ma_tb="ma_tb" :row_id="row_id" :trangthai_dl="trangthai_dl" :propDichvuvt_id = "dichvuvt_id" />
    </b-modal>
</template>
<script>
import ChangeMegawanInfor from './ChangeMegawanInfor.vue'
export default {
    name:'ChangeMegawanInforModal',
    props:{
        ma_tb:{
            type:String,
            default:''
        },
        trangthai_dl:{
            type:Number,
            default:1
        },
        dichvuvt_id:{
            type:Number,
            default: 0
        },
        row_id:{
          type:String,
          default:''
        }
    },
    components:{
        ChangeMegawanInfor
    },
    data(){
        return {
            hienthi: ''
        }
    },
    methods:{
        handleShowModal(){
        },
        showModal() {
            setTimeout(()=>{
                this.$refs["popupChangeMegawanInfor"].show()
            }, 200)
        },
        hideModal() {
            this.$refs["popupChangeMegawanInfor"].hide()
        },
    }
}
</script>