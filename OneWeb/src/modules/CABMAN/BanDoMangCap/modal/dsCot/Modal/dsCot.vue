<template src="./dsCot.html"></template>
<script>
import {
  Page,
  Filter,
  CommandColumn,
  Resize
} from '@syncfusion/ej2-vue-grids'
export default {
  name: 'ModalCsht',
  props: ['modalId', 'ma_ttvt'],
  provide: {
    grid: [Filter, Page, CommandColumn, Resize]
  },
  created: async function () {},
  components: {
  },
  computed: {},
  methods: {
    focusMyElement: async function () {
      await this.getData()
    },
    hiddenModal: function () {
      this.dsCot = []
    },
    getData: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', { loai: 'DS_COL', option: '' })
        if (rs.data != null && rs.data.length > 0) {
          this.dsCot = rs.data
        }
      } catch (err) {
        this.dsCot = []
        this.$root.toastError(err.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
    },
    grid_selectedRowChanged: function (data) {
      if (data != null) {
        this.dataSelect = data
      }
    },
    chonMa: function () {
      this.$emit('xacnhan', this.dataSelect)
      this.$bvModal.hide(this.modalId)
    }
  },
  data: function () {
    return {
      dsCot: [],
      dataSelect: ''
    }
  }
}
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10002 !important;
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
