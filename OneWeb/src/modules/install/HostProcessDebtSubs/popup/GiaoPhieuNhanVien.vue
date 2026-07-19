<template>
<ejs-dialog  ref="popup" :visible="false" :header='"Giao việc"' showCloseIcon=true width='80%' target='#app .main-wrapper' @close="onClose">
    <!-- <b-modal
      size="xl"
      dialog-class="dialog-content"
      body-class="body-content"
      content-class="popup-box"
      v-model="visible"
      hide-footer
      hide-header
    > -->
    <GiaoPhieuNhanVien
          ref="formGiaoPhieu"
          :phieu_id="phieu_id"
          :nguoigv_id="nguoigv_id"
          :ngaygiao="ngaygiao"
          :ma_tb="ma_tb"
          :hdtb_id="hdtb_id"
          :huonggiao_id="huonggiao_id"/>
    <!-- <div style="margin: -1rem" v-if="visible">
      <div class="popup-header">
          <div class="title">Giao việc</div>
          <div class="close -ap icon-close" data-dismiss="modal" @click="onClose">
          </div>
        </div>
      </div>
      <div class="modal-content popup-box">

      </div> -->
  <!-- </b-modal> -->
  </ejs-dialog>
</template>
<script>
import GiaoPhieuNhanVien from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue'
export default {
  components: {
    GiaoPhieuNhanVien
  },
  data: () => ({
    visible: false,
    phieu_id: null,
    nguoigv_id: null,
    ngaygiao: null,
    ma_tb: null,
    hdtb_id: null,
    huonggiao_id: null
  }),
  computed: {
  },
  methods: {
    show (visible, data) {
      if (data) {
        this.phieu_id = data.phieu_id
        this.nguoigv_id = data.nguoigv_id
        this.ngaygiao = data.ngaygiao
        this.ma_tb = data.ma_tb
        this.hdtb_id = data.hdtb_id
        this.huonggiao_id = data.huonggiao_id
      }

      if (visible) {
        this.$refs.popup.show()
      } else {
        this.$refs.popup.hide()
      }

      setTimeout(() => {
        this.$refs.formGiaoPhieu.init()
      })
    },
    onClose () {
      this.$refs.popup.hide()
      this.$emit('close')
    }
  },
  watch: {
  }
}
</script>
