<template src="./ModalCsht.html"></template>
<script>
import {
  GridPlugin,
  Page,
  Filter,
  CommandColumn,
  Resize
} from '@syncfusion/ej2-vue-grids'
// import ToaDo from "@/modules/CABMAN/BanDoMangCap/modal/ToaDo/Modal";
export default {
  name: 'ModalCsht',
  props: ['modalId', 'ma_ttvt'],
  provide: {
    grid: [Filter, Page, CommandColumn, Resize]
  },
  created: async function () {},
  components: {
    // ToaDo,
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
      this.txtQuery = ''
      this.dsCsht = []
      this.totalRow = 0
    },
    getCSHT: async function (pNo = 1, pSize = 10) {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/ds_csht',
          {
            ma_csht: this.txtQuery,
            page_index: pNo,
            page_size: pSize
          }
        )
        if (rs.data != null && rs.data.length > 0) {
          this.dsCsht = rs.data
          this.totalRow = this.dsCsht.length
        }
      } catch (err) {
        this.$root.toastError(err.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
    },
    async layThongTinNguoiDung () {
      try {
        let rs = await this.$root.context.get('/web-hopdong/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung')
        return !rs.data ? null : rs.data[0]
      } catch (error) {
        return null
      }
    },
    getCSHTNew: async function () {
      try {
        this.$root.showLoading(true)
        let matinh = this.$root.context.user.getProperty('ma_tinh')
        var rs = await this.$root.context.post(
          '/tichhop/qlts/asset_get_station_by_code',
          {
            ma_csht: (this.txtQuery == undefined || this.txtQuery == null || this.txtQuery == '') ? '' : "['" + this.txtQuery + "']",
            ma_tinh: "['" + matinh + "']",
            page_index: this.pNo,
            page_size: 1000000
            // page_index: this.pNo,
            // page_size: this.pSize
          }
        )
        if (rs.data.success) {
          let userObj = rs.data
          let dsTem = []
          let ds = []
          let phanvung_id = this.$auth.getPhanVungID()
          if (userObj.data != null && userObj.data.length > 0) {
            if (Number(phanvung_id) === 28) {
              dsTem = userObj.data[0].json_station.filter((w) => (this.ma_ttvt == null || (w.ma_don_vi == this.ma_ttvt && (w.muc_dich_su_dung.includes('Di động + Cố định + Băng rộng') || w.muc_dich_su_dung.includes('Di động + Cố định') || w.muc_dich_su_dung.includes('Di động + Băng rộng')))))
              this.totalRow = Number(dsTem.length)
            } else {
              dsTem = userObj.data[0].json_station
              this.totalRow = Number(userObj._web_page_size)
            }
            let _stt = 1
            dsTem.forEach(x => {
              let obj = new Object()
              obj.stt = _stt++
              obj.ma_csht = x.ma_csht
              obj.ma_don_vi = x.ma_don_vi
              obj.ten_csht = x.ten_csht
              obj.dia_chi = x.dia_chi
              obj.muc_dich_su_dung = x.muc_dich_su_dung
              obj.mo_ta = x.mo_ta
              obj.ngay_cap_nhat = x.ngay_cap_nhat
              obj.toado = x.vi_do + ';' + x.kinh_do
              ds.push(obj)
            })
            this.dsCsht = [...ds]
          } else { this.dsCsht = null }
        } else {
          this.dsCsht = []
          this.$root.toastError(rs.data.message)
        }
      } catch (err) {
        console.log(err)
        this.$root.toastError('Tra cứu thất bại!')
      } finally {
        this.$root.showLoading(false)
      }
    },
    grid_selectedRowChanged: function (data) {
      if (data != null) {
        this.ma_csht = data.ma_csht
        this.objToaDo.toado = data.toado
        this.objToaDo.diachi = data.dia_chi
      }
    },
    chonMa: function () {
      this.$emit('xacnhan', this.ma_csht)
      this.$bvModal.hide(this.modalId)
    },
    grid_dataGridCellCreated (e) {
      e.cellAllowHtml = true
      $(e.cellElement).append(
        '<span class="btn btn-main btn-open-map nc-icon-glyph location_map-pin"></span>'
      )
      $('.btn-open-map').on('click', (e) => {
        this.$bvModal.show('popupToaDo')
      })
    },
    csht_PageChanged: async function (args) {
      // console.log('🚀 ~ file: ModalCsht.vue:136 ~ args:', args)
      // this.$root.showLoading(true)
      // try {
      //   this.pSize = args.pageSize
      //   this.pNo = args.pageIndex + 1
      //   await this.getCSHTNew()
      // } finally {
      //   this.$root.showLoading(false)
      // }
    },
    checkpNo: function () {
      if (this.pNo > 1) { this.pNo = this.pNo - 1 } else { this.pNo = 1 }
    }
  },
  data: function () {
    return {
      dsCsht: [],
      txtQuery: '',
      ma_csht: '',
      pNo: 1,
      pSize: 10,
      totalRow: 0,
      objToaDo: {
        toado: '0,0',
        diachi: ''
      }
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
