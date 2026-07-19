<template>
    <div>
    
  <b-modal
    id="ChuyenHopDong"
    ref="popup_ChuyenHopDong"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> Chuyển hợp đồng
      </div>
      <div
        @click="close"
        class="close -ap icon-close"
        data-dismiss="modal"
      ></div>
    </template>

    <div class="list-actions-top">
    </div>

    <div class="popup-body">
      <CapNhatThueBaoDatCoc ref="popupCapNhatThueBaoDatCoc"/>
    </div>
            


  </b-modal>


    </div>
</template>
<script>


import CapNhatThueBaoDatCoc from '@/modules/EXTS/HOPDONG/CapNhatThueBaoDatCoc/index.vue'
import breadcrumb from '@/components/breadcrumb'


export default {
name:'popup_ChuyenHopDong',
  data() {
    return {

    };
  },
  components: {breadcrumb,CapNhatThueBaoDatCoc },  
  created() {
    this.$refs.popupCapNhatThueBaoDatCoc.HienThiTT_DanhBa()
  },


  watch: {

  },

  methods: {


        showModal() {
            this.$bvModal.show("ChuyenHopDong")
        },
        hideModal() {
            this.$bvModal.hide("ChuyenHopDong")
        },




  },
};
</script>
<style>


</style>