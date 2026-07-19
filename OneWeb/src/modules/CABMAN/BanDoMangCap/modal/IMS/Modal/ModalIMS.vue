<template src="./ModalIMS.html"></template>
<script>
export default {
  name: 'ModalIMS',
  props: ['modalId'],
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
      this.dsIMS = []
    },
    getIMS: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/ds-tbi-chitiet'
        )
        console.log('getIMS', rs)
        if (rs.data != null && rs.data.length > 0) {
          this.dsIMS = rs.data
        }
      } catch (err) {
        this.$root.toastError(err.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
    },
    grid_selectedRowChanged: function (data) {
      if (data != null) {
        this.ma_ims = data.ID_CHITIET
      }
    },
    chonMa: function () {
      this.$emit('xacnhan', this.ma_ims)
      this.$bvModal.hide(this.modalId)
    }
    // grid_dataGridCellCreated (e) {
    //   e.cellAllowHtml = true
    //   $(e.cellElement).append(
    //     '<span class="btn btn-main btn-open-map nc-icon-glyph location_map-pin"></span>'
    //   )
    //   $('.btn-open-map').on('click', (e) => {
    //     this.$bvModal.show('popupToaDo')
    //   })
    // }
    // csht_PageChanged: async function (args) {
    //   this.$root.showLoading(true)
    //   try {
    //     await this.getCSHT(args.pageIndex + 1, args.pageSize)
    //   } finally {
    //     this.$root.showLoading(false)
    //   }
    // }
  },
  data: function () {
    return {
      dsIMS: [],
      ma_ims: ''
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
