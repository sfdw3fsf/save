<template src="./index.template.html"></template>
<script>

export default {
  components: { },
  name: 'ModalLichSuCap',
  props: {
    idModal: null
  },
  computed: {
    message () {
      return 'Lịch sử thay đổi cáp của thuê bao: ' + this.thuebao.TEN_TB
    }
  },
  async mounted () {
    try {

    } catch (error) {
      console.log('error', error)
    }
  },
  data () {
    return {
      resolvePromise: null,
      rejectPromise: null,
      thuebao: {TEN_TB: null, THUEBAO_ID: null},
      lichsucap: []
    }
  },
  methods: {
    grid_selectedRowChanged () {},
    shownModal () {
      console.log('lichsucap thuebao_id', this.thuebao)
      this.getLichSuCap()
    },
    hiddenModal () {
      this.lichsucap = []
      this.thuebao = {TEN_TB: null, THUEBAO_ID: null}
    },
    show (thuebao) {
      return new Promise((resolve, reject) => {
        this.thuebao = thuebao
        this.$bvModal.show(this.idModal)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    closeModal: function () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.idModal)
    },
    getLichSuCap: async function () {
      try {
        this.$root.showLoading(true)
        // var rs = await this.$root.context.post(
        //   '/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/sp_lichsu_cap', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_THUEBAO_ID: this.thuebao.THUEBAO_ID, PROCEDURE_NAME: 'CABMAN.PKG_DAUNOI_TB.SP_LISHSU_CAP'}
        // )
        var rs = await this.$root.context.post(
          '/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/sp_lichsu_cap', {P_THUEBAO_ID: this.thuebao.THUEBAO_ID}
        )
        console.log('rs.data', rs.data)
        this.lichsucap = rs.data
      } catch (error) {
        console.log('getLichSuCap', error)
        this.$root.showLoading(false)
      } finally {
        this.$root.showLoading(false)
      }
    }
  },
  watch: {
    thuebao_id (val) {
    }
  },
  created () {
  }
}
</script>

<style>
.modal-fullscreen .modal-dialog {
    max-width: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100vh;
    display: flex;
    position: fixed;
    z-index: 100000;
}
</style>
