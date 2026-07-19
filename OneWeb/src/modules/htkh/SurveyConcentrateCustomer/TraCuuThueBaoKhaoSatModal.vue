<template>
  <b-modal
    ref="popupTraCuuThueBaoKhaoSat"
    size="xl"
    title="Tra cứu thuê bao khảo sát"
    modal-class="tracuutb"
    header-class="popup-header"
    content-class="tracuutb-content"
    body-class="none-padding tracuutb-body"
    no-close-on-backdrop
    hide-footer
    @show="handleShowModal"
  >
    <TraCuuThueBaoKhaoSat ref="traCuuThueBaoKhaoSat"/>
  </b-modal>  
</template>
<script>
import TraCuuThueBaoKhaoSat from '../../CSKH/TraCuuThueBaoKhaoSat'
export default {
    name:'TraCuuThueBaoKhaoSatModal',
    props:['ma_tb'],
    components:{
        TraCuuThueBaoKhaoSat
    },
    data(){
        return{

        }
    },
    methods:{
        showModal() {
            this.$refs["popupTraCuuThueBaoKhaoSat"].show()
        },
        hideModal() {
            this.$refs["popupTraCuuThueBaoKhaoSat"].hide()
        },
        handleShowModal(){
            setTimeout(()=>{
                this.$refs.traCuuThueBaoKhaoSat.pTxtMaTB=this.ma_tb
                this.$refs.traCuuThueBaoKhaoSat.onTimKiemClicked(true)
            }, 1000)
        },
    }
}
</script>
<style>
.tracuutb-body .page-content {
  position: unset !important;
}
</style>