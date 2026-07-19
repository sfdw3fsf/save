<template>
  <div class="">
    <!-- Form -->
    <div class="list-actions-top">
      <ul class="list">
        <li @click="onClickXacNhan">
          <a> <span class="icon files_archive-3d-check nc-icon-glyph"></span> Xác nhận </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="" style="display: flex;">
          <div v-show="layoutCuonCap.visible" style="flex-grow: 1;">
            <div class="legend-title mart10">
              <div class="pull-left padt5">
                Danh sách vật tư của đơn hàng
              </div>
              <div class="clearfix"></div>
            </div>
            <DataGrid ref="gridVatTu" dataKeyField="ID_GEN" :columns="gridVatTu.header" :dataSource="gridVatTu.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :editSettings="editSettings" :selectionSettings="{ enableToggle: false }" @cellSaved="editGridVatTuDonHang" />
          </div>
          <div class="actions"></div>
          <div v-show="layoutVTDK.visible" style="flex-grow: 1;">
            <div class="legend-title mart10">
              <div class="pull-left padt5">
                Danh sách vật tư xuất đăng ký
              </div>
              <div class="clearfix"></div>
            </div>
            <DataGrid3
              ref="grcVattuDK"
              :columns="grcVattuDK.header"
              :dataSource="grcVattuDK.list"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              :editSettings="{ allowEditing: true, mode: 'Batch' }"
              :selectionSettings="{ enableToggle: false }"
              @selectedRowChanged="grcVattuDKSelectedRowChanged"
              @selectedItemsChanged="grcVattuDKSelectedItemsChanged"
              @cellSaved="editGridVatTuDangKy"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import Vue from 'vue'
import { GridPlugin, Group, Page, Filter, Sort, Resize } from '@syncfusion/ej2-vue-grids'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
Vue.use(DatePickerPlugin)
Vue.use(GridPlugin)
import api from './api'

export default {
  components: { breadcrumb },
  emits: ['onBtnXacNhanClicked'],
  props: {
    dt_vattu: {
      type: [Array],
      default() {
        return []
      },
      require: true
    },
    dt_cuoncap: {
      type: [Array],
      default() {
        return []
      },
      require: true
    }
  },
  data() {
    return {
      vkieu: 0,
      dt_vattugoc: [],
      // dt_vattu: [],
      dt_serial: [],
      // dt_cuoncap: [],
      chia: [],
      XacNhan: false,
      is_cc: false,
      xml_ds_vt_dk: '',
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      layoutCuonCap: { visible: true },
      layoutVTDK: { visible: true },
      gridVatTu: {
        header: [
          { fieldName: 'ID_GEN', headerText: '', visible: false, isPrimaryKey: true },
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
          { fieldName: 'TONG_XUAT', headerText: 'Tổng xuất', allowFiltering: true },
          { fieldName: 'SL_XUAT', headerText: 'Cuộn cáp', allowEditing: true, type: 'number', editType: 'numericedit' },
          { fieldName: 'MA_VT', headerText: '', visible: false }
        ],
        list: [],
        selected: [],
        value: null
      },
      grcVattuDK: {
        header: [
          { fieldName: 'ID_GEN', headerText: '', visible: false, isPrimaryKey: true },
          { fieldName: 'TEN_KHO', headerText: 'Kho', allowFiltering: true },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { fieldName: 'SOLUONG_GROUP', headerText: 'Số lượng', allowFiltering: true, allowEditing: true },
          { fieldName: 'SL_KD', headerText: 'Số lượng KD', allowFiltering: true },
          { fieldName: 'VTDK_ID', headerText: '', visible: false }
        ],
        list: [],
        selected: [],
        value: null
      },
      editSettings: { allowEditing: true, mode: 'Batch' }
    }
  },

  computed: {},
  async mounted() {
    this.loading(true)
    await this.frmChiaCuonCap_Load()
    this.loading(false)
  },
  methods: {
    async frmChiaCuonCap_Load() {
      console.log('frmChiaCuonCap_Load')
      console.log(this.dt_vattu)
      console.log(this.dt_cuoncap)
      this.gridVatTu.list = []
      this.grcVattuDK.list = []
      if (this.vkieu == 0) {
        this.layoutCuonCap.visible = true
        this.layoutVTDK.visible = false
        this.load_ds()
      } else if (this.vkieu == 1) {
        this.layoutCuonCap.visible = false
        this.layoutVTDK.visible = true
        if (this.xml_ds_vt_dk != '' && this.xml_ds_vt_dk != null) {
          this.grcVattuDK.list = (await api.thong_tin_don_hang(this.axios, { ds: this.xml_ds_vt_dk })).data.data
        }
      }
    },
    async load_ds() {
      console.log('load_ds')
      console.log(this.dt_vattu)
      let serial = this.dt_vattu.filter((p) => p.LOAITBI_ID == '2')
      if (serial.length) {
        this.dt_serial = serial.map((e) => e)
      }
      this.chia = []
      console.log('82 - this.dt_cuoncap')
      console.log(this.dt_cuoncap)
      console.log('84 - this.dt_serial')
      console.log(this.dt_serial)

      for (let i of this.dt_cuoncap) {
        let sl = i.CHIA_SL
        if (sl > 0) {
          for (let j = 0; j < sl; j++) {
            this.chia.push({
              CHON: 's0',
              PHANVUNG_ID: i.PHANVUNG_ID,
              CTDH_ID: i.CTDH_ID,
              DONHANG_ID: i.DONHANG_ID,
              VATTU_ID: i.VATTU_ID,
              SOLUONG: '',
              DONGIA: i.DONGIA,
              TIEN: '',
              SL_NT: '',
              SL_HONG: '',
              SL_CHUAN: '',
              VAT: '',
              HDVT_ID: i.HDVT_ID,
              HAN_BH: i.HAN_BH,
              MA_VT: i.MA_VT,
              TEN_VT: i.TEN_VT,
              DVITINH_ID: i.DVITINH_ID,
              DVI_TINH: i.DVI_TINH,
              SOTHANG_PB: i.SOTHANG_PB,
              LOAI_TBI: i.LOAI_TBI,
              LOAITBI_ID: i.LOAITBI_ID,
              SL_XUAT: 0,
              TONG_XUAT: i.SL_XUAT
            })
          }
        }
      }
      this.gridVatTu.list = this.chia
      for (let i = 0; i < this.gridVatTu.list.length; i++) {
        this.gridVatTu.list[i].ID_GEN = this.uuidv4()
      }
      console.log(this.gridVatTu.list)
    },

    async editGridVatTuDonHang(arg) {
      console.log('editGridVatTuDonHang')
      let changes = this.$refs.gridVatTu.getBatchChanges()
      // let row = changes.changedRecords.find((e) => e.ID_GEN == this.gridVatTu.value.ID_GEN)
      // console.log(changes)
      // console.log(row)
      // this.gridVatTu.list = this.gridVatTu.list.map((e) => {
      //   if (e.ID_GEN == row.ID_GEN) return row
      //   else return e
      // })

      // if (changes.changedRecords.length > 0) {
      //   if (arg.columnName == 'SL_XUAT') {
      //     if (changes.changedRecords[0].SL_XUAT < 0) {
      //       changes.changedRecords[0].SL_XUAT = 0
      //     }
      //   }
      // }
      // changes.changedRecords[0]
      this.$refs.gridVatTu.batchUpdate(changes)
      this.$refs.gridVatTu.grid.refreshColumns()
    },

    async editGridVatTuDangKy(arg) {
      let grcVattuDK = this.$refs.grcVattuDK
      let changes = grcVattuDK.getBatchChanges()
      if (changes.changedRecords.length > 0) {
        if (arg.columnName == 'SOLUONG_GROUP') {
          if (changes.changedRecords[0].SOLUONG_GROUP < 0) {
            changes.changedRecords[0].SOLUONG_GROUP = 0
          }
        }
      }
      // grcVattuDK.batchUpdate(changes)

      let primaryKeys = this.$refs.grcVattuDK.grid.getPrimaryKeyFieldNames()
      // console.log(changes)
      // this.$refs.gridVatTu.batchUpdate(changes)
      this.$refs.grcVattuDK.grid.setRowData(primaryKeys[0], changes.changedRecords[0])
      this.$refs.grcVattuDK.grid.refreshColumns()
    },

    async onClickXacNhan() {
      console.log('onClickXacNhan')
      console.log(this.vkieu)
      console.log(this.dt_serial)
      console.log(this.dt_cuoncap)
      console.log(this.chia)
      this.gridVatTu.selected = this.$refs.gridVatTu.grid.getSelectedRecords()
      console.log(this.gridVatTu.selected)
      this.chia = this.gridVatTu.list

      if (this.vkieu == 0) {
        if (this.dt_serial != null) {
          for (const i in this.dt_cuoncap) {
            let sum = 0
            for (const j in this.chia) {
              if (this.dt_cuoncap[i].MA_VT == this.chia[j].MA_VT) {
                sum += this.chia[j].SL_XUAT
              }
            }
            console.log('sum')
            console.log(sum)
            if (sum != this.dt_cuoncap[i].SL_XUAT) {
              this.$toast.error('Mã VT: ' + this.dt_cuoncap[i].MA_VT + ' tổng số cuộn cáp [' + sum + '] không bằng [' + this.dt_cuoncap[i].SL_XUAT + ']')
              return
            }
          }
          for (let i = 0; i < this.chia.length; i++) {
            this.chia[i] = { ...this.chia[i], ...this.dt_serial[i] }
          }
          // this.dt_serial.forEach((e) => {
          //   this.chia.push(e)
          // })
          console.log('191 - this.dt_serial')
          console.log(this.chia)
        }
        if (this.chia.length > 0) {
          this.XacNhan = true
          this.is_cc = true
        }
      } else if (this.vkieu == 1) {
        if (this.grcVattuDK.list.length > 0 && this.grcVattuDK.list != null) {
          let VTKiemTra = this.grcVattuDK.list.filter(function(el) {
            return el.SOLUONG_GROUP == 0
          })
          if (VTKiemTra.length > 0) {
            this.$toast.error('Bạn chưa chọn số lượng cho kho và vật tư không thể xác nhận')
            return
          }

          let ds_xacnhan = []
          this.grcVattuDK.list.forEach((e) => {
            ds_xacnhan.push({
              VTDK_ID: e.VTDK_ID,
              KHO_ID: e.KHO_ID,
              VATTU_ID: e.VATTU_ID,
              SOLUONG_GROUP: e.SOLUONG_GROUP,
              SL_KD: e.SL_KD
            })
          })

          let KTSoLuong = (
            await api.so_luong_lo_hang_dang_ky(this.axios, {
              ds: JSON.stringify(ds_xacnhan)
            })
          ).data.data
          console.log(KTSoLuong)
          if (KTSoLuong != '1') {
            this.$toast.error('Có lỗi khi kiểm tra vật tư :' + KTSoLuong)
            return
          }
          let xdoc_vtdk = []
          ds_xacnhan.forEach((e) => {
            xdoc_vtdk.push({
              vtid: e.VTDK_ID,
              soluong: 0,
              kho_id: e.KHO_ID,
              sl_duyet: e.SOLUONG_GROUP
            })
          })

          this.xml_ds_vt_dk = JSON.stringify(xdoc_vtdk)
          this.XacNhan = true
          console.log('chia xong roi')
          
        }
      }
      this.$emit('onBtnXacNhanClicked', {
            XacNhan: this.XacNhan,
            chia: this.chia,
            is_cc: this.is_cc
          })
    },
    gridVatTuSelectedRowChanged(e) {
      console.log('gridVatTuSelectedRowChanged')
      console.log(e)
      this.gridVatTu.value = e
    },
    gridVatTuSelectedItemsChanged(e) {
      console.log('gridVatTuSelectedItemsChanged')
      console.log(e)
      this.gridVatTu.selected = Array.isArray(e) ? e : [e]
    },
    grcVattuDKSelectedRowChanged(e) {
      console.log('grcVattuDKSelectedRowChanged')
      console.log(e)
      this.grcVattuDK.value = e
    },
    grcVattuDKSelectedItemsChanged(e) {
      console.log('grcVattuDKSelectedItemsChanged')
      console.log(e)
      this.grcVattuDK.selected = Array.isArray(e) ? e : [e]
    },
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    }
  },

  created: async function() {},
  provide: {
    grid: [Group, Page, Filter, Sort, Resize]
  }
}
</script>
<style scoped>
.disabledInput {
  pointer-events: none;
  opacity: 0.4;
}
</style>
