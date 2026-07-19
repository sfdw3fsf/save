<template>
<!-- <ejs-dialog :enableResize='true' :visible="false" ref="popupThongTinLienHe" :header='"Thông tin liên hệ"'
    showCloseIcon=true target=".main-wrapper"  width='80%' height="800" :position="{X:'center', Y:'top'}" :zIndex="1500">
    <ThongTinLienHe :hdkh_id="hdkh_id" :khachhang_id="khachhang_id"/>
</ejs-dialog> -->
    <b-modal
        ref="popupThongTinLienHe"
        size="xl"
        title="Thông tin liên hệ"
        no-close-on-backdrop
        hide-footer
        body-class="p-0"
    >
        <ThongTinLienHe :hdkh_id="hdkh_id" :khachhang_id="khachhang_id"/>
    </b-modal>
</template>
<script>
//clone: https://scm.devops.vnpt.vn/erp.ptpm1.onebss/OneWeb/-/tree/develop/src/modules/contract/setup/ChangeContractOwner
import ThongTinLienHe from '../../ChangeContractOwner/popup/ThongTinLienHe.vue'
export default {
    name:'ThongTinLienHeModal',
    props:{
      hdkh_id:{
          type:Number,
          default: 0
      },
      //Dịch vụ
      khachhang_id:{
          type:Number,
          default:0
      }       
    },
    components:{
        ThongTinLienHe
    },
    data(){
        return {

        }
    },
    methods:{
        showModal() {
            this.$refs["popupThongTinLienHe"].show()
        },
        hideModal() {
            this.$refs["popupThongTinLienHe"].hide()
        }
    }
}
</script>