<template>
  <b-modal
    ref="popupDangKyChiTietKhuyenMai"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <popupDangkychitietkhuyenmai :dulieu="dulieu" @form-close="formClose"/>
  </b-modal>
</template>
<script>
//Clone branch develop-erp
import popupDangkychitietkhuyenmai from '../../InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai.vue'
export default {
    name:'DangKyChiTietKhuyenMaiModal',
    props:['dulieu'],
    components:{
        popupDangkychitietkhuyenmai
    },
    data(){
        return {

        }
    },
    methods:{
        showModal() {
            this.$refs["popupDangKyChiTietKhuyenMai"].show()
        },
        hideModal() {
            this.$refs["popupDangKyChiTietKhuyenMai"].hide()
        },
        formClose(ds){
            this.$emit('form-close', ds)
            this.hideModal()
        },
        handleShowModal(){
            
        },
    }
}
</script>