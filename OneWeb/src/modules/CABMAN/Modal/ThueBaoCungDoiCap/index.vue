<template src="./index.template.html"></template>
<script>
export default {
  components: { },
  name: 'ModalThueBaoCungDoiCap',
  props: {
    idModal: null
  },
  computed: {

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
      thuebao: { IS_THUEBAO: null, THUEBAO_ID: null, DAUCUOI_ID: null },
      danhsach: [],
      SelectedThueBao: null
    }
  },
  methods: {
    grid_selectedRowChanged (row) {
      this.SelectedThueBao = row
    },
    rowDoubleClicked (index, data) {
      console.log('rowDoubleClicked', data)
      this.resolvePromise({ok: true, data: data})
      this.$bvModal.hide(this.idModal)
    },
    async shownModal () {
      this.danhsach = await this.LAY_DS_TB_CUNG_DOICAP()
      console.log('🚀 ~ file: index.vue ~ line 36 ~ shownModal ~ this.danhsach', this.danhsach)
      if (this.danhsach.length > 0) {

      } else {
        this.$root.$toast.warning('Thuê bao lắp đường riêng hoặc chưa có thuê bao lắp kèm\r\n[Click để đóng]')
      }
    },
    btnChapNhan_ItemClick () {
      this.resolvePromise({ok: true, data: this.SelectedThueBao})
      this.$bvModal.hide(this.idModal)
    },
    hiddenModal () {
      this.thuebao = { IS_THUEBAO: null, DAUCUOI_ID: null, THUEBAO_ID: null }
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
    LAY_DS_TB_CUNG_DOICAP: async function () {
      try {
        this.$root.showLoading(true)
        var vis_thuebao = this.thuebao.IS_THUEBAO === 1
        var vdb_id = vis_thuebao ? this.thuebao.THUEBAO_ID : this.thuebao.HDTB_ID
        var vdaucuoi_id = this.thuebao.DAUCUOI_ID
        var rs = await this.$root.context.post('/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/lay_ds_thuebao_cung_doicap?db_id=' + vdb_id + '&kieu=' + (vis_thuebao ? 0 : 1) + '&daucuoi_id=' + vdaucuoi_id)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 67 ~ error', error)
        this.$root.showLoading(false)
        return []
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
.disabled-btn {
    pointer-events:none;
    opacity:0.5;
    background: #ccc;
}

.disable-btn:hover {
    background-color: white !important;
}

.disable-btn a {
    color: #6c757d !important;
}

.disable-btn a:hover {
    cursor: default;
    color: #6c757d;
}

a.disable-btn {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
}
</style>
