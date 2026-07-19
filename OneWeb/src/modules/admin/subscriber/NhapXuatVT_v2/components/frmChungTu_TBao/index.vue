<template>
  <div>
    <!-- <div class="popup-header d-flex ">
      <div class="flex-grow-1 ">
        Danh sách thuê bao
      </div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="Close()"></div>
    </div> -->
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="btnTimKiem_Click"> <span class="icon  one-file-attach"></span> Lấy TT [F5] </a>
        </li>
        <li>
          <a @click="tsbtnXuatExcel_Click"> <span class="one-xlsx-import f20 normal inline vcenter lh14 marr5"></span> Xuất Excel </a>
        </li>
        <li v-if="Visible.tsbtnBBBG" :class="{ disabled: !Enabled.tsbtnBBBG }">
          <a @click="tsbtnBBBG_Click"> <span class=" one-file-edit2 f20 normal inline vcenter lh14 marr5"></span> Xem BBBG </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="legend-title d-flex">
        <div class="pull-left flex-grow-1">
          Danh sách thuê bao của chứng từ
          <div class="pl-3 check-action normal">
            <input type="checkbox" class="check" v-model="params.p_ckbChungTu" @change="ckbChungTu_CheckedChanged" />
            <span class="name">Chỉ lấy Serial</span>
          </div>
          <div class="pull-right" @click="Visible.Panel2Collapsed = !Visible.Panel2Collapsed">
            <button class="btn btn-second pad4 lh16">
              <span class="nc-icon-outline arrows-1_tail-right" :style="{ transform: Visible.Panel2Collapsed ? `rotate(0deg)` : `rotate(180deg)` }"></span>
            </button>
          </div>
        </div>
      </div>
      <div class="row">
        <div :class="Visible.Panel2Collapsed ? 'col-md-8' : 'col-md-12'">
          <DataGrid3
            v-bind:columns="gridDanhSachColumns"
            ref="gridviewDanhSach"
            class="table-result table-gachle"
            v-bind:dataSource="gridDanhSach"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :totalRecords="gridDanhSachPage.totalElement"
            :pageSize="gridDanhSachPage.maxSize"
            :pageIndex="gridDanhSachPage.page"
            :height="300"
            @pageChanged="gridDanhSachPageHandler"
            @selectedRowChanged="gridviewDanhSach_FocusedRowChanged"
          >
          </DataGrid3>
        </div>
        <div class="col-md-4 pl-0" v-show="Visible.Panel2Collapsed">
          <DataGrid
            v-bind:columns="[
              { fieldName: 'ISCHECK', type: 'checkbox', headerText: '', textAlign: 'Center', allowFiltering: false, width: '70' },
              { fieldName: 'TEN_COT', headerText: 'TEN_COT', textAlign: 'Left', allowFiltering: true },
              { fieldName: 'TEN_FIELD', headerText: 'TEN_FIELD', textAlign: 'Left', allowFiltering: true, visible: false }
            ]"
            ref="grvCot"
            class="table-result table-gachle"
            v-bind:dataSource="grcCot"
            :showColumnCheckbox="false"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :totalRecords="grcCotPage.totalElement"
            :pageSize="grcCotPage.maxSize"
            :pageIndex="grcCotPage.page"
            :enabledSelectFirstRow="false"
            @pageChanged="grcCotPageHandler"
            @rowSelected="repositoryItemCheckEdit1_CheckedChanged"
            @rowDeselected="repositoryItemCheckEdit1_CheckedChanged"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import Vue from 'vue'
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import select2 from '@/components/Select2.vue'
import { Freeze, Group, Page, Filter, Sort, Resize, ForeignKey } from '@syncfusion/ej2-vue-grids'
import { L10n } from '@syncfusion/ej2-base'
import XLSX from 'xlsx'
import { previewPrint } from '@/utils/util'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})

export default {
  components: { XLSX, breadcrumb, appSelect2: select2 },
  name: 'frmChungTu_TBao',
  props: {
    kieu: {
      type: [String, Number],
      default: false,
      required: false
    },
    chungtu_id: {
      type: [String, Number],
      default: false,
      required: false
    }
  },
  provide: {
    grid: [Freeze, Group, Page, Filter, Sort, Resize, ForeignKey]
  },
  computed: {},
  data() {
    return {
      gridDanhSach: [],
      gridDanhSach_temp: [],
      grcCot: [],
      ds_cot: [],

      params: {
        p_ckbChungTu: false
      },

      Text: {
        grpThietBi: ''
      },
      gridDanhSachColumns: [
        { fieldName: 'STT', headerText: 'STT', textAlign: 'Left', visible: true, allowFiltering: true, width: '70', freeze: 'Left' },
        { fieldName: 'MA_TB', headerText: 'Số máy/Acc', textAlign: 'Left', visible: true, allowFiltering: true, freeze: 'Left' },
        { fieldName: 'MA_LT', headerText: 'Số ảo', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'LOAI_HD', headerText: 'Loại HĐ', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'TEN_KIEULD', headerText: 'Kiểu yêu cầu', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'TEN_VT', headerText: 'Tên VT', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'SERIAL', headerText: 'Serial', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', visible: true, allowFiltering: true },
        { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'NGAY_CAP', headerText: 'Ngày cấp', textAlign: 'Right', visible: true, allowFiltering: true },
        { fieldName: 'MUCDICH', headerText: 'Lý do cấp', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', visible: true, allowFiltering: true },
        { fieldName: 'NGAY_HT', headerText: 'Ngày HT', textAlign: 'Right', visible: true, allowFiltering: true },
        { fieldName: 'TEN_KHO', headerText: 'Kho cấp', textAlign: 'Left', visible: true, allowFiltering: true, freeze: 'Right' }
      ],

      gridDanhSachPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      grcCotPage: {
        page: 0,
        maxSize: 20,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },

      Visible: {
        Panel2Collapsed: true,
        tsbtnBBBG: true
      },
      Enabled: {
        tsbtnBBBG: false
      },
      hdkh_id: 0, // ưpdate code moi tu dhsx
      ts_xem_bienban_bangiao: false
    }
  },
  methods: {
    // chuyển Date thành type string
    f_DateToString: function(value, format = 'DD/MM/YYYY') {
      return moment(value).format(format)
    },
    f_StringToDate(value, format = 'DD/MM/YYYY') {
      return moment(value, format).toDate()
    },

    gridDanhSachPageHandler(e) {
      this.gridDanhSachPage.page = e.pageIndex
      this.gridDanhSachPage.maxSize = e.pageSize
    },
    grcCotPageHandler(e) {
      this.grcCotPage.page = e.pageIndex
      this.grcCotPage.maxSize = e.pageSize
    },
    async btnTimKiem_Click() {
      // try {
      await this.Lay_DanhSachTB()
      // } catch (error) {
      // this.$toast.error("Có lỗi hàm lấy danh sách thuê bao ")
      // }
    },
    async Lay_DanhSachTB() {
      if (this.kieu == 0) {
        this.gridDanhSach = []
        await api
          .getDSThueBaoChungTu(this.axios, { chungTuId: this.chungtu_id })
          .then((response) => {
            this.gridDanhSach = response.data.data || []
          })
          .catch(function() {})
          .finally(function() {})
      } else {
        let dem = 7
        let k = 0

        this.gridDanhSachColumns.push({ fieldName: 'TEN_DV', headerText: 'Tên đơn vị', textAlign: 'Left', allowFiltering: true })
        this.gridDanhSachColumns.push({ fieldName: 'SR_DOI', headerText: 'Serial đổi', textAlign: 'Left', allowFiltering: true })
        this.gridDanhSachColumns.push({ fieldName: 'SR_BH', headerText: 'Serial bảo hành', textAlign: 'Left', allowFiltering: true })
        this.gridDanhSachColumns.push({ fieldName: 'MA_VT', headerText: 'Mã VT', textAlign: 'Left', allowFiltering: true })
        this.gridDanhSachColumns.push({ fieldName: 'TEN_VT', headerText: 'Tên VT', textAlign: 'Left', allowFiltering: true })
        this.gridDanhSachColumns.push({ fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true })
        await api
          .getDSThueBaoChungTuV2(this.axios, { chungTuId: this.chungtu_id })
          .then((response) => {
            this.gridDanhSach = response.data.data || []
          })
          .catch(function() {})
          .finally(function() {})
      }
      this.ds_cot = []
      let indexArray = []
      if (this.gridDanhSach.length) {
        if (this.$auth.getMaTinh() == 'HPG') {
          let tinh = ['MA_TB', 'STT', 'TEN_TB', 'DIACHI_LD', 'TEN_KIEULD', 'TEN_VT', 'SOLUONG', 'TEN_KHO', 'NGAY_HT']
          this.gridDanhSachColumns.forEach((a, index) => {
            if (tinh.includes(a.fieldName) && !this.ds_cot.map((b) => b.TEN_FIELD).includes(a.fieldName)) {
              this.ds_cot.push({
                ISCHECK: true,
                TEN_COT: a.headerText,
                TEN_FIELD: a.fieldName
              })
              indexArray.push(index)
              a.visible = true
            } else if (!this.ds_cot.map((b) => b.TEN_FIELD).includes(a.fieldName)) {
              this.ds_cot.push({
                ISCHECK: false,
                TEN_COT: a.headerText,
                TEN_FIELD: a.fieldName
              })
              a.visible = false
            }
          })
        } else {
          this.gridDanhSachColumns.forEach((a) =>
            this.ds_cot.push({
              ISCHECK: false,
              TEN_COT: a.headerText,
              TEN_FIELD: a.fieldName
            })
          )
        }
      }
      this.grcCot = this.ds_cot
      this.gridDanhSach_temp = this.gridDanhSach
      indexArray.length ? this.$refs.grvCot.grid.selectRows(indexArray, true) : ''
    },
    async frmChungTu_TBao_Load() {
      // try {
      this.$root.showLoading(true)
      if (this.kieu == 1) {
        this.Text.grpThietBi = 'Danh sách thiết bị bảo hành'
      }
      var kq = ''
      await api
        .CHECK_IS_EXIST(this.axios, { maThamSo: 'TS_XEM_BIENBAN_BANGIAO' })
        .then((response) => {
          kq = response.data.data || []
        })
        .catch(function() {})
        .finally(function() {})
      if (kq == '1') {
        this.Visible.tsbtnBBBG = true
        this.ts_xem_bienban_bangiao = true
      } else {
        this.Visible.tsbtnBBBG = false
        this.ts_xem_bienban_bangiao = false
      }

      await this.Lay_DanhSachTB()
      this.$root.showLoading(false)
      // } catch (error) {
      //     this.$toast.error("Có lỗi hàm lấy danh sách thuê bao ")
      // }
    },
    async tsbtnXuatExcel_Click() {
      let exportData = []
      let exportCols = this.$refs.grvCot.getSelectedRecords()
      console.log('tsbtnXuatExcel_Click')
      console.log(this.gridDanhSach)
      this.gridDanhSach.forEach((a) => {
        let row = {}
        exportCols.forEach((c) => {
          row[c.TEN_COT] = a[c.TEN_FIELD]
        })
        !!row ? exportData.push(row) : ''
      })
      console.log(exportData)
      if (exportData.length == 0) {
        exportData.push(exportCols.map((b) => b.TEN_COT))
      }
      await this.HT_DLG_XUAT_EXCEL(exportData, 'DSTbi')
    },
    async ckbChungTu_CheckedChanged() {
      if (this.params.p_ckbChungTu) {
        this.gridDanhSach = this.gridDanhSach.filter((a) => a.SERIAL.toString() != '')
      } else {
        this.gridDanhSach = this.gridDanhSach_temp
      }
    },
    async IsValidFileName() {},
    async lnkXuatExcel_ProgressChanged() {},
    async HT_DLG_XUAT_EXCEL(dt, filename) {
      let data1 = XLSX.utils.json_to_sheet(dt)
      let wb = XLSX.utils.book_new() // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, filename) // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, filename + '.xlsx')
    },
    async repositoryItemCheckEdit1_CheckedChanged(args) {
      // debugger
      if (args) {
        var visibleFields = this.$refs.grvCot.getSelectedRecords().map((a) => a.TEN_FIELD)
        this.gridDanhSachColumns.forEach((b) => {
          if (visibleFields.includes(b.fieldName)) {
            b.visible = true
          } else {
            b.visible = false
          }
        })
      }
    },
    async Close() {
      this.$parent.hide()
    },
    async ShowDialog() {
      this.$parent.show()
    },
    // update code moi dhsx
    async tsbtnBBBG_Click() {
      this.$root.showLoading(true)
      let res = ''
      await api
        .xemBaoCao(this.axios, { hdKhId: this.hdkh_id })
        .then((response) => {
          res = response
        })
        .catch(function() {})
        .finally(function() {})

      this.$root.showLoading(false)
      res ? previewPrint(res.data) : ''
    },
    async gridviewDanhSach_FocusedRowChanged(row) {
      if (this.ts_xem_bienban_bangiao) {
        if (row) {
          this.hdkh_id = row.HDKH_ID || 0
          this.Enabled.tsbtnBBBG = this.hdkh_id.toString() != '0'
        } else {
          this.hdkh_id = 0
          this.Enabled.tsbtnBBBG = false
          return
        }
      }
    }
  },
  async mounted() {
    await this.frmChungTu_TBao_Load()
    console.log('frmChungTu_TBao_Load xong')
    console.log(this.grcCot)
    setTimeout(() => {
      this.$refs.grvCot.grid.selectRowsByRange(0,this.grcCot.length)
      // this.$refs.grvCot.grid.selectRows([1,2,3,4,5])
    }, 500)
  }
}
</script>
<style scoped>
.disabled a {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.disabled a:hover {
  cursor: default;
  color: #6c757d;
}
</style>
