<template src="./ModalDuAn.html"></template>
<script>
export default {
  name: 'ModalDuAn',
  props: ['modalId', 'duan_id'],
  created: async function () {},
  components: {
  },
  computed: {},
  methods: {
    focusMyElement: async function () {
      try {
        this.$root.showLoading(true)
      } catch (er) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    hiddenModal: function () {
      this.dsDUAN = []
      this.id_duan = null
      this.ten_duan = null
      this.ma_duan = null
      this.duan_ims_id = null
    },
    getIMS: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DUAN', option: ''}
        )
        if (rs.data != null && rs.data.length > 0) {
          this.dsDUAN = rs.data
          if (this.duan_id !== null && this.duan_id !== '') {

          }
        }
      } catch (err) {
        this.$root.toastError(err.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
    },
    grid_selectedRowChanged: function (data) {
      if (data != null) {
        this.id_duan = data.DUAN_ID
        this.ten_duan = data.MA_CT
        this.ma_duan = data.SOHIEU_CT
        this.duan_ims_id = data.DUAN_IMS_ID
      }
    },
    chonMa: function () {
      this.$emit('xacnhan', { duan_id: this.id_duan, ten_duan: this.ten_duan, ma_duan: this.ma_duan, duan_ims_id: this.duan_ims_id })
      this.$bvModal.hide(this.modalId)
    }
  },
  data: function () {
    return {
      dsDUAN: [],
      id_duan: '',
      ten_duan: '',
      ma_duan: '',
      duan_ims_id: ''
    }
  }
}
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
}
</style>
