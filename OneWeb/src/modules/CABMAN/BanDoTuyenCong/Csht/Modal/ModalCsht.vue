<template src="./ModalCsht.html"></template>
<script>
import {
  GridPlugin,
  Page,
  Filter,
  CommandColumn,
  Resize
} from '@syncfusion/ej2-vue-grids'
import ToaDo from '../../ToaDo/Modal'
export default {
  name: 'ModalCsht',
  props: ['modalId'],
  provide: {
    grid: [Filter, Page, CommandColumn, Resize]
  },
  created: async function () {},
  components: {
    ToaDo
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
    getCSHTNew: async function () {
      try {
        this.$root.showLoading(true)
        let matinh = this.$root.context.user.getProperty('ma_tinh')

        var rs = await this.$root.context.post(
          '/tichhop/qlts/asset_get_station_by_code',
          {
            ma_csht: (this.txtQuery==undefined || this.txtQuery==null || this.txtQuery=='')?"":"['"+this.txtQuery+"']",
            ma_tinh: "['" + matinh + "']",
            page_index: this.pNo,
            page_size: this.pSize
          }
        )
        if (rs.data.success) {
        // let userObj = JSON.parse(rs.data);
          let userObj = rs.data
          let dsTem = []
          let ds = []
          if (userObj.data != null && userObj.data.length > 0) {
            dsTem = userObj.data[0].json_station
            this.totalRow = this.pSize
            dsTem.forEach(x => {
              let obj = new Object()
              obj.stt = x.stt
              obj.ma_csht = x.ma_csht
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
    getCSHT: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/ds_csht',
          {
            ma_csht: this.txtQuery,
            page_index: this.pNo,
            page_size: this.pSize
          }
        )
        this.dsCsht = rs.data
        //  console.log(this.dsCsht);
      } catch (error) {
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
    setPageSize: function (size) {
      this.pSize = size
    },
    checkpNo: function () {
      if (this.pNo > 1) this.pNo = this.pNo - 1
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
    openMap: function (args) {
      console.log('openMap:' + args)
    }
  },
  data: function () {
    return {
      dsCsht: [],
      txtQuery: '',
      ma_csht: '',
      pNo: 1,
      pSize: 1000,
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
