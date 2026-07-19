<template>
    <b-modal
        ref="popupTraPhieu"
        size="xl"
        title="Trả phiếu"
        modal-class="traphieu"
        header-class="popup-header"
        content-class="traphieu-content"
        body-class="none-padding traphieu-body"
        no-close-on-backdrop
        hide-footer
    >
        <!-- <traphieu :ngaytra="modelTraPhieu.ngaytra" :ngaygiao="modelTraPhieu.ngaygiao"
            :ma_gd="modelTraPhieu.maGd" :nhanvien_id="modelTraPhieu.nhanvienId"
            :ma_tb="modelTraPhieu.ma_tb" :kieu="modelTraPhieu.kieugoiId"
            :loaihd_id="modelTraPhieu.loaihdId" :dichvuvt_id="modelTraPhieu.dichvuvtId"/> -->
        <frmTraPhieuTC ref="frmTraPhieuTC" @traphieu_success="traphieu_success"/>
    </b-modal>
</template>
<script>
//import traphieu from '../../ReturnInstallTicket/components/traphieu.vue'
import frmTraPhieuTC from '../../ReturnInstallTicket/frmTraPhieuTC.vue'
export default {
    name:'TraPhieuModal',
    props:{
        modelTraPhieu:{
            type:Object,
            default:()=>{
                return {
                    maGd: '',
                    loaihdId: 0,
                    dichvuvtId: 0,
                    donviId: 0,
                    matb: '',
                    nhanvienId: 0,
                    ngaygiao: '',
                    ngaytra: '',
                    kieugoiId: 0
                }
            }
        }
    },
    components:{
        //traphieu,
        frmTraPhieuTC
    },
    data(){
        return {

        }
    },
    methods:{
        showModal() {
            this.$refs['popupTraPhieu'].show()
            setTimeout(()=>{
                this.$refs.frmTraPhieuTC.frmTraPhieuTC(
                    this.modelTraPhieu.maGd,
                    this.modelTraPhieu.loaihdId,
                    this.modelTraPhieu.dichvuvtId,
                    this.modelTraPhieu.donviId,
                    this.modelTraPhieu.matb,
                    this.modelTraPhieu.ngaygiao,
                    this.modelTraPhieu.ngaytra,
                    this.modelTraPhieu.kieugoiId
                )
            }, 300)
        },
        hideModal() {
            this.$refs['popupTraPhieu'].hide()
        },
        traphieu_success(value){
            this.$emit('traphieu_success', value)
            this.hideModal()
        }
    },
    
}
</script>
<style>
.traphieu-body .page-content {
  position: unset !important;
}
</style>