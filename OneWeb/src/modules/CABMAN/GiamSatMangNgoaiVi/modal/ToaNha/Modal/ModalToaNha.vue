<template src="./ModalToaNha.html"></template>
<script>
export default {
  name: 'ModalToaNha',
  props: ['modalId', 'toanha_id'],
  created: async function () {},
  components: {
  },
  computed: {},
  methods: {
    focusMyElement: async function () {
      try {
        console.log('🚀 ~ file: ModalToaNha.vue:14 ~ this.toanha_id:', this.toanha_id)
        this.$root.showLoading(true)
      } catch (er) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    hiddenModal: function () {
      this.dsTOANHA = []
      this.id_toanha = null
      this.ten_toanha = null
    },
    getIMS: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TOANHA', option: ''}
        )
        if (rs.data != null && rs.data.length > 0) {
          this.dsTOANHA = rs.data
          if (this.toanha_id !== null && this.toanha_id !== '') {

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
        this.id_toanha = data.TOANHA_ID
        this.ten_toanha = data.TEN_TOANHA
      }
    },
    chonMa: function () {
      this.$emit('xacnhan', { toanha_id: this.id_toanha, ten_toanha: this.ten_toanha })
      this.$bvModal.hide(this.modalId)
    }
  },
  data: function () {
    return {
      dsTOANHA: [],
      id_toanha: '',
      ten_toanha: ''
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
