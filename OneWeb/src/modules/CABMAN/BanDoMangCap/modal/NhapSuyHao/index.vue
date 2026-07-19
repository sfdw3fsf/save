<template src="./index.template.html"></template>
<script>
import RowSuyHaoTemplate from './RowSuyHaoTemplate/index.vue'
export default {
  name: 'ModalNhapSuyHao',
  props: {
    modalId: String,
    dataSuyHao: Object,
    loaiModal: Number
  },
  data: function () {
    return {
      dsSuyHao: [],
      loai: null,
      rowTemplateSuyHaoOption: function (e) {
        return {
          template: RowSuyHaoTemplate
        }
      }
    }
  },
  mounted: async function () {},
  methods: {
    focusMyElement: async function () {
      this.clear()
      this.loaiModal == 1 ? (this.loai = 'Kết cuối') : (this.loai = 'Cáp')
      this.getDsSuyHao()
    },
    saveData: async function () {
      this.$root.showLoading(true)
      try {
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/capnhat_danhsach_suyhao_ketcuoi_cap', {
        ketcuoiCapId: this.loaiModal == 1 ? this.dataSuyHao.ketcuoi_id : this.dataSuyHao.cap_id,
        dsVitri: this.dsSuyHao.filter((e) => e.suyhao != null).map((item) => item.vitri).join(';'),
        dsSuyHao: this.dsSuyHao.filter((e) => e.suyhao != null).map((item) => item.suyhao).join(';'),
        loai: this.loaiModal
        })
        if (rs.error == 200) {
          this.$root.$toast.success('Cập nhật thành công')
        } else {
          this.$root.$toast.error(rs.message)
        }
        this.$root.showLoading(false)
      } catch (e) {
        this.$root.$toast.error('Có lỗi xảy ra')
        this.$root.showLoading(false)
      }
     
    },
    clear() {
      this.loai = null
    },
    grid1_rowSelected() {
      //
    },
    onDoubleClick() {
      //
    },
    async getDsSuyHao() {
      this.dsSuyHao = []
      let dataDungLuong = this.loaiModal == 1 ? this.dataSuyHao.dungluong : this.dataSuyHao.sodoiday
      for (let i = 0; i < dataDungLuong; i++) {
        this.dsSuyHao.push({
          vitri: i + 1,
          suyhao: null
        })
      }
      let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/ds_suyhao_ketcuoi_cap', {
        ketcuoiCapId: this.loaiModal == 1 ? this.dataSuyHao.ketcuoi_id : this.dataSuyHao.cap_id,
        loai: this.loaiModal
      })
      rs.data.forEach((item) => {
        this.dsSuyHao.find((x) => x.vitri == item.VITRI).suyhao = item.SUYHAO
      })
      this.$refs.suyhao.refresh()
    },
    updateDataSource: function (dataItem) {
      console.log(dataItem)
      this.dsSuyHao[dataItem.index].suyhao = dataItem.suyhao
      console.log(this.dsSuyHao)
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
.bss1833-box-file-image .input-more-button.-right.input-title {
  position: inherit;
}
.box-file-image-upload .file-canvas .input-image {
  margin-bottom: 10px !important;
}
.input-more-button.-right > .btn {
  right: -6px !important;
}
.body-suyhao {
  max-height: 80vh;
  overflow-y: scroll;
}
</style>
