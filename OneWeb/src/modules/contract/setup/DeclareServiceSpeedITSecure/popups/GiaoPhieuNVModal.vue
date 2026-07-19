<template>
    <!-- <b-modal
    ref="popupGiaoPhieuNV"
    size="xl"
    title="Giao phiếu nhân viên"
    header-class="popup-header"
    body-class="none-padding"
    no-close-on-backdrop
    hide-footer
    >
        <frnGiaoPhieuNV ref="frnGiaoPhieuNV" @giaoviec_success="giaoviec_success"/>
    </b-modal> -->
    <ejs-dialog :enableResize='true' :visible="false" ref="popupGiaoPhieuNV" :header='"Giao phiếu nhân viên"'
        showCloseIcon=true target=".main-wrapper" width='60%' height="800">
        <frnGiaoPhieuNV
            ref="frnGiaoPhieuNV" @giaoviec_success="giaoviec_success" />
    </ejs-dialog>
</template>
<script>
import frnGiaoPhieuNV from '../../DeclareLandline/popups/frnGiaoPhieuNV.vue'
//rnGiaoPhieuNV(_vdsphieu, _vdonvi, _nhanvien_giao_id, _ngaygiao)
export default {
    name:'GiaoPhieuNVModal',
    components:{
        frnGiaoPhieuNV
    },
    props: [
        "phieu_id",
        "nguoigv_id",
        "ngaygiao",
        "ma_tb",
        "hdtb_id",
        "huonggiao_id",
        "donvi_id",
        "dsphieuGV",
    ],
    methods:{
        showModal() {
            this.$refs['popupGiaoPhieuNV'].show()
            setTimeout(()=>{
                this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.dsphieuGV ? this.dsphieuGV : [this.phieu_id], this.donvi_id, this.nguoigv_id, this.ngaygiao);
                if(this.ma_tb){
                    this.$refs.frnGiaoPhieuNV.vma_tb = this.ma_tb;
                }
                if(this.hdtb_id){
                    this.$refs.frnGiaoPhieuNV.vhdtb_id = this.hdtb_id;
                }                   
                if(this.huonggiao_id){
                    this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiao_id;
                }
                this.$refs.frnGiaoPhieuNV.load()
            }, 300)
        },
        hideModal() {
            this.$refs['popupGiaoPhieuNV'].hide()
        },
        giaoviec_success(value){
            this.$emit('giaoviec_success', value)
        }
    },

}
</script>