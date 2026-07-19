<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li @click="tsbtnHoanThanh_Click">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xác nhận </a>
        </li>
        <li @click="tsbtnDocFile_Click">
          <a> <span class="one-xlsx-import f20 normal inline vcenter lh14 marr5"></span> Đọc file excel </a>
          <b-form-file id="fileUploadDSVT" v-model="arrayImportFile.filexcel" ref="fileUploadDSVT" @change="this.onChangeDSVT" accept=".xls, .xlsx" class="-ap icon-more_horiz hidden" plain></b-form-file>
        </li>
        <li @click="tsbtnFileMau_Click">
          <a> <span class="icon one-xlsx1 f20 inline vcenter"></span> File Excel Mẫu </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="legend-title"><span class="icon fa fa-angle-up"></span>Danh sách vật tư</div>
          <div class="col-12" :class="layoutControlItem3.visible ? 'col-sm-6' : 'col-sm-12'">
            <div class="table-content" style="overflow-x:hidden;">
              <DataGrid
                :columns="gridControl1.header"
                :dataSource="gridControl1.list"
                ref="TableGridControl1"
                :showColumnCheckbox="false"
                :defaultColumnCheckboxChecked="false"
                :enabledSelectFirstRow="false"
                :showFilter="true"
                :allowPaging="true"
                :enablePagingServer="false"
                @rowSelected="gridView1_MouseDown"
                :selectionSettings="{ checkboxOnly: true }"
              />
            </div>
          </div>
          <div v-if="layoutControlItem3.visible" class="col-sm-6 col-12">
            <div class="table-content" style="overflow-x:hidden;">
              <!-- <DataGrid
                ref="TableGridDS"
                :columns="gridDS.list"
                :dataSource="gridControl1.list"
                :showColumnCheckbox="false"
                :defaultColumnCheckboxChecked="false"
                :enabledSelectFirstRow="false"
                :showFilter="true"
                :allowPaging="true"
                :enablePagingServer="false"
                @rowSelected="gridView1_MouseDown"
                :selectionSettings="{ checkboxOnly: true }"
              /> -->
              <!-- <ejs-grid
                ref="TableGridDS"
                class="table-result table-gachle"
                locale="vi"
                :dataSource="gridDS.list"
                :allowPaging="true"
                :allowSorting="true"
                :allowFiltering="true"
                :filterSettings="filterOptions"
                :allowResizing="true"
                :allowSelection="true"
                :gridLines="'Both'"
                rowHeight="32"
                :enableHover="true"
                :pageSettings="pageSettings"
              >
                <e-columns>
                  <e-column field="STT" headerText="" width="20" textAlign="Center" :template="cRowSelected" :filterBarTemplate="templateOptions"></e-column>
                  <e-column type="checkbox" :allowFiltering="false" textAlign="Center" :allowSorting="false" width="60"></e-column>
                  <e-column field="MA_VT" headerTextAlign="Center" headerText="Mã vật tư" textAlign="Left" :filterBarTemplate="templateOptions"></e-column>
                  <e-column field="TEN_VT" headerTextAlign="Center" headerText="Tên vật tư" textAlign="Left" :filterBarTemplate="templateOptions"></e-column>
                  <e-column field="DVI_TINH" headerTextAlign="Center" headerText="ĐVT" textAlign="Left" :filterBarTemplate="templateOptions"></e-column>
                </e-columns>
              </ejs-grid> -->
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title padb5">
          <div class="pull-left"><span class="icon fa fa-angle-up"></span>Danh sách vật tư đã chọn</div>
          <div class="clearfix"></div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="table-content">
              <DataGrid
                ref="gridVatTu"
                :columns="gridVatTuHeader"
                :dataSource="gridVatTu.list"
                dataKeyField="ID_GEN"
                :showColumnCheckbox="false"
                :defaultColumnCheckboxChecked="false"
                :enabledSelectFirstRow="false"
                :showFilter="true"
                :allowPaging="true"
                :enablePagingServer="false"
                :editSettings="{ allowEditing: true, mode: 'Batch' }"
                @cellSaved="gridViewVatTu_ValidateRow"
              />
              <!-- @selectedRowChanged="gridVatTuSelectedRowChanged" -->
            </div>
          </div>
        </div>
      </div>
    </div>
    <ejs-dialog :isModal="true" :enableResize="true" :resizeHandles="['All']" :visible="false" ref="frmChonLoHang" header="Lô hàng" showCloseIcon="true" target=".main-wrapper" width="98%" allowDragging="true" @close="frmChonLoHang.visible = false">
      <div>
        <frmChonLoHang v-if="frmChonLoHang.visible" v-bind="frmChonLoHang.input" @onBtnXacNhanClicked="frmChonLoHang_onBtnXacNhanClicked" />
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from 'vuex'
import moment from 'moment'
import Vue from 'vue'
import breadcrumb from '@/components/breadcrumb'
import api from './api'
// import { getterName, actionName, statePropertyName, mutationName } from '../store/dsvattu'
import select2 from '@/components/Select2.vue'
import { Freeze, Edit, Group, Page, Filter, Sort, Resize } from '@syncfusion/ej2-vue-grids'
import { Query, Predicate } from '@syncfusion/ej2-data'
import EventBus from '@/utils/eventBus'
import XLSX from 'xlsx'
import DateTimeHelper from '../../helpers/dateHelper'
import { convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault } from '../../helpers'
import { FORM_CONFIG } from '../../const'
import { frmChonLoHang, btnThemVTTemplate, btnXoaVTTemplate, LoHangTemplate, MA_VT_Header, NgayBHTemplate } from './components'

export default {
  components: { breadcrumb, appSelect2: select2, XLSX, frmChonLoHang },
  provide: {
    grid: [Freeze, Edit, Group, Page, Filter, Sort, Resize]
  },
  emits: ['onBtnHoanThanhClicked'],
  props: {
    thamso_sinhlohang: {
      type: [Boolean],
      default: false,
      required: false
    },
    quyen: {
      type: [Boolean],
      default: false,
      required: false
    },
    vchungtu_id: {
      type: [String, Number],
      default: -1,
      required: false
    },
    ma_md: {
      type: [String, Number],
      default: -1,
      required: false
    },
    vkho_id: {
      type: [String, Number],
      default: -1,
      required: false
    },
    vkho_den_id: {
      type: [String, Number],
      default: -1,
      required: false
    },
    tt: {
      type: [String, Number],
      default: -1,
      required: false
    },
    kieu: {
      type: [String, Number],
      default: 0,
      required: true
    },
    dtLuuVT: {
      type: [Array],
      default: () => {
        return []
      },
      required: true
    },
    vngay_ct: {
      type: [String],
      default: '',
      required: false
    }
  },
  data() {
    return {
      localLoading: 0,
      header: {
        title: 'Danh sách vật tư',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      LOHANG: { visible: true },
      tsbtnDocFile: { visible: true },
      tsbtnFileMau: { visible: true },
      colMaTheTS: { visible: true },
      colGhiChu: { visible: true },
      colNguon_goc: { visible: true },
      colDongTbi: { visible: true },
      colHeThong: { visible: true },
      colChatLuong_ID: { visible: true },
      colVTCha: { visible: true },
      colLoaiTBI: { visible: true },
      colDongia: { visible: true },
      colCL: { visible: true },
      colLoHang: { visible: true },
      layoutControlItem3: { visible: false },
      arrayImportFile: {
        filexcel: null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: []
      },
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true },
      format: { format: 'N3', useGrouping: true },
      templateOptions: {
        create: function(args) {
          if (['STT', 'btnXoaVT', 'btnEditTbi'].includes(args.column.field)) {
            return '<span class="fa input-icon-right"></span>'
          }

          return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>'
        },
        write: function(args) {}
      },
      cRowSelected: function() {
        return {
          template: Vue.component('columntemplate', {
            template: '<span class="fa"></span>'
          }) // end of template
        }
      },
      pageSettings: { pageSizes: ['10', '20', '50', '100', 'All'], pageSize: 10 },
      dtVatTu: [], //gridDS.list
      dtThietBi: [],
      tagForm: '',
      XacNhan: false,
      dt: [],
      checkAll: false,
      _kt: 0,
      kieu_loc: 0,
      vma_md: '',

      mavt: '',
      tenvt: '',
      loaitbi: '',
      loaitbi_id: '',
      dt_file: [],

      ds_lohang: [], // cboLoHang
      cboLoHang: [],
      Tag: '',
      cboChatLuong_ID: [],
      MUCDICH_CCDC: {
        NHAP: 'CCDC.N',
        CHUYEN: 'CCDC.C',
        SUACHUA: 'CCDC.S',
        THUHOI: 'CCDC.T',
        THANHLY: 'CCDC.TL',
        DAUKY: 'CCDC.DK'
      },
      gridControl1: {
        header: [
          { fieldName: 'ISCHECK', headerText: '', type: 'checkbox', textAlign: 'Left', allowFiltering: true, width: '60', freeze: 'Left' },
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true },
          { fieldName: 'PART_NUMBER', headerText: 'Part_number', textAlign: 'Left', allowFiltering: true },
          { fieldName: 'LOAI_TBI', headerText: 'Loại TB', textAlign: 'Left', allowFiltering: true }
        ],
        list: [],
        value: null,
        selected: []
      },
      gridDS: {
        header: [],
        list: [],
        value: null,
        selected: []
      },
      gridVatTu: {
        header: [],
        list: [],
        value: null,
        selected: []
      },
      indexVatTu: -1,
      frmChonLoHang: {
        visible: false,
        input: {
          sophieu: 0,
          vchungtu_id: 0,
          vma_md: 0,
          dt_vt: null
        }
      }
    }
  },
  computed: {
    gridVatTuHeader() {
      let header = [
        { allowEditing: false, fieldName: 'MA_VT', headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true, headerTemplate: MA_VT_Header(this) },
        { allowEditing: false, headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true, template: LoHangTemplate(this), visible: this.LOHANG.visible },
        { allowEditing: false, fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true },
        { allowEditing: false, fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true },
        { allowEditing: true, fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', allowFiltering: true, type: 'number', editType: 'numericedit', format: 'n3' },
        { allowEditing: true, fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', allowFiltering: true, type: 'number', editType: 'numericedit', format: 'n3' },
        { allowEditing: false, fieldName: 'TIEN', headerText: 'Thành tiền', textAlign: 'Right', allowFiltering: true, type: 'number', editType: 'numericedit', format: 'n3' },
        // { allowEditing: true, fieldName: 'HAN_BH', headerText: 'Hạn BH', textAlign: 'Right', allowFiltering: true,type: 'date',  format: {type:'date', format:'dd/MM/yyyy'},allowEditing: true, editType: 'DatepickerEdit', },
        { allowEditing: false, fieldName: 'HAN_BH', headerText: 'Hạn BH', textAlign: 'Left', allowFiltering: true, template: NgayBHTemplate(this) },
        { allowEditing: false, fieldName: 'btnThemVT', headerText: 'Thêm VT', textAlign: 'Left', allowFiltering: true, template: btnThemVTTemplate(this), width: 70 },
        { fieldName: 'ID_GEN', isPrimaryKey: true, visible: false }
      ]
      return header
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    'gridVatTu.list'(val) {
      if (val.filter((e) => e.ID_GEN == null || e.ID_GEN == '').length) {
        this.gridVatTu.list = this.gridVatTu.list.map((e) => {
          e.ID_GEN = this.uuidv4()
          return e
        })
      }
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },

    resetParams() {
      this.dtVatTu = []
      this.dtLuuVT = []
      this.gridControl1.list = []
      this.gridVatTu.list = []
      this.gridDS.list = []
    },
    async Open() {
      this.resetParams()
      this.$refs.popup_frmDSVatTu.show()
    },
    // async Close() {
    //   await this.$parent.XacNhan_frmDSVatTu()
    //   this.$refs.popup_frmDSVatTu.hide()
    // },
    async frmDSVatTu_Load() {
      console.log('frmDSVatTu_Load')
      console.log(this.dtLuuVT)
      this.loading(true)
      this.Tag ? (this.tagForm = '') : (this.tagForm = this.Tag)
      if (this.tagForm == '10') {
        this.dtVatTu = await this.callApiWrapper(api.vat_tu_theo_tham_so, { thamSo: 1 })
      } else {
        // this.ds_lohang = (await api.lo_hang(this.axios, {})).data.data
        // this.ds_lohang.shift()
        // this.ds_lohang.unshift({ loHang: '', prefix: '1', donGia: '0' })
        // this.cboLoHang = this.ds_lohang
        await this.load_grid()
        if (this.ma_md == '1.N' || this.ma_md == 'N.VT') {
          this.LOHANG.visible = true
        } else {
          this.LOHANG.visible = false
        }
        this.layoutControlItem3.visible = false
        if (this.ma_md == '1.N' || this.ma_md == '1.NL' || this.ma_md == 'N.KDV' || this.ma_md == 'N.VT' || this.ma_md == '1.3' || this.ma_md == '1.1.5' || this.ma_md == '1.4' || this.ma_md == '1.5' || this.ma_md == '1.NBH') {
          this.tsbtnDocFile.visible = true
          this.tsbtnFileMau.visible = true
        }
        if (this.quyen) {
          this.colMaTheTS.visible = true
          this.colGhiChu.visible = true
          this.colNguon_goc.visible = true
          this.colDongTbi.visible = true
          this.colHeThong.visible = true
          this.colChatLuong_ID.visible = true
        }

        this.cboChatLuong_ID = await this.callApiWrapper(api.chat_luong, {})
      }
      this.loading(false)
    },
    /**
     * @see: private void load_grid()
     */
    async load_grid() {
      if (this.dtLuuVT.length) {
        if (this.kieu_loc == 1) {
          this.dtVatTu = await this.callApiWrapper(api.vat_tu_theo_tham_so, { thamSo: 2 })
          // this.gridDS.list = this.dtVatTu
        } else {
          if (this.dtVatTu.length == 0) {
            this.dtVatTu = await this.callApiWrapper(api.vat_tu_theo_tham_so, { thamSo: 1 })
          }
        }

        if (this.kieu_loc == 1 && this.ma_md != this.MUCDICH_CCDC.DAUKY) {
          var q = this.dtVatTu.filter((a) => parseFloat(a.SL) > 0)
          if (q.length) {
            this.dtVatTu = q
          } else {
            this.dtVatTu = []
          }
        } else if (this.kieu_loc == 1 && this.ma_md == this.MUCDICH_CCDC.DAUKY) {
          var q = this.dtVatTu.filter((a) => parseFloat(a.SL) > 0 || a.HIENTHI == '0')
          if (q.length) {
            this.dtVatTu = q
          } else {
            this.dtVatTu = []
          }
        }

        for (let i in this.dtVatTu) {
          var r = this.dtVatTu[i]
          for (let j in this.dtLuuVT) {
            var rt = this.dtLuuVT[j]
            if (rt.VATTU_ID == r.VATTU_ID) {
              this.dtVatTu.splice(i, 1)
              break
            }
          }
        }
        console.log('461 - chay den day')
        this.gridControl1.list = this.dtVatTu
        this.gridVatTu.list = this.dtLuuVT
        console.log('466 - this.gridVatTu.list')
        console.log(this.gridVatTu.list)

        console.log('showData_gridVatTu')
        console.log(this.gridVatTu.list)
        this.showData_gridVatTu()
        //#region
      } else {
        console.log('truong hop nay')
        if (this.kieu_loc == 1) this.dtVatTu = await this.callApiWrapper(api.vat_tu_theo_tham_so, { thamSo: 2 })
        else this.dtVatTu = (await api.vat_tu_theo_tham_so(this.axios, { thamSo: 1 })).data.data
        if (this.kieu_loc == 1 && this.ma_md != this.MUCDICH_CCDC.DAUKY) {
          let q = this.dtVatTu.filter((a) => parseFloat(a.SL) > 0)
          if (q.length) {
            this.dtVatTu = q.map((e) => e)
          } else {
            this.dtVatTu = []
          }
        } else if (this.kieu_loc == 1 && this.ma_md == this.MUCDICH_CCDC.DAUKY) {
          let q = this.dtVatTu.filter((a) => parseFloat(a.SL) > 0 || a.HIENTHI == '0')
          if (q.length) {
            this.dtVatTu = q.map((e) => e)
          } else {
            this.dtVatTu = []
          }
        }
        this.gridControl1.list = this.dtVatTu
        this.dtLuuVT = []
        // this.dtLuuVT = JSON.parse(JSON.stringify(this.dtVatTu))
      }

      if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('LOAI_TBI')) {
        this.colLoaiTBI.visible = true
      }
      if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('TEN_VT_CHA')) {
        this.colVTCha.visible = true
      }
      if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('GHICHU')) {
        this.colGhiChu.visible_VT = true
      }
      if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('DONGIA_VT')) {
        this.colDongia.visible = true
      }
      if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('CHATLUONG')) {
        this.colCL.visible = true
      }
      console.log('out - this.dtLuuVT')
      console.log(this.dtLuuVT)
    },
    /**
     * @see: string KiemTra_VT(DataTable dt)
     */
    async KiemTra_VT(dt) {
      if (this.kieu_loc != 1) {
        for (let dr of dt) {
          let d = await this.callApiWrapper(api.prefix_theo_ma_lo_hang, { loHang: dr.LOHANG })

          if (d.length) {
            if (d[0] != '1') {
              let q = dt.filter((a) => a.VATTU_ID == dr.VATTU_ID && a.LOHANG == dr.LOHANG)
              if (q.length > 1) {
                return 'Với lô hàng ' + dr.LOHANG + ' không tự sinh ko thể chọn nhiều vật tư trong 1 chứng từ'
              }
            }
          } else {
            console.log('FILE NHAP TEST')
            console.log(this.thamso_sinhlohang)
            console.log(this.ma_md)
            console.log(this.quyen)
            if (['1.N', '1.NL', 'N.KDV', 'N.VT', 'N.DK'].includes(this.ma_md) || (['1.3', 'N-X.DCMSL', '1.4'].includes(this.ma_md) && this.quyen)) {
              if (!this.thamso_sinhlohang) {
                let q = dt.filter((a) => a.VATTU_ID == dr.VATTU_ID && a.DONGIA == dr.DONGIA)
                if (q.length > 1) {
                  return 'Đang có ' + q.length + ' mã vật tư  giống nhau cùng đơn giá trong chứng từ'
                }
              }
            } else {
              let q = dt.filter((a) => a.VATTU_ID == dr.VATTU_ID)
              if (q.length > 1) {
                return 'Số lượng vật tư trong chứng từ chỉ được phép có 1 mã vật tư'
              }
            }
          }
        }
      }

      if (this.colLoHang.visible) {
        let lengt = this.dt.filter((a) => a.CHON == 's1')
        if (lengt.length) {
          for (let dr in lengt) {
            if (!dt.LOHANG) {
              let check = this.ds_lohang.filter((a) => a.LOHANG == dr.LOHANG && a.PREFIX == '0')
              if (check.length) {
                if (check[0].DONGIA != dr.DONGIA) {
                  return 'Lô hàng ' + dr.LOHANG + ' phải có đơn giá là ' + check[0].DONGIA
                }
              }
            }
          }
        }
      }
      return '1'
    },
    /**
     * @see: private void tsbtnHoanThanh_Click(object sender, EventArgs e)
     */
    async tsbtnHoanThanh_Click() {
      if (this.tagForm == '10') {
        let s = this.gridDS.list
        if (s == null || s.length <= 0) {
          this.$toast.error('Danh sách vật tư rỗng !')
          return
        }

        // let a = this.$refs.TableGridDS.getSelectedRecords()
        let a = this.gridVatTu.list
        if (a.length == 0) {
          this.$toast.error('Chưa chọn vật tư nào!')
          return
        }
        this.mavt = a[0].MA_VT
        this.tenvt = a[0].TEN_VT
        this.loaitbi = a[0].LOAI_TBI
        this.loaitbi_id = a[0].LOAITBI_ID
      } else {
        // tlstpTop.Focus()
        // let a = this.$refs.gridVatTu.grid.getSelectedRecords()
        let a = this.gridVatTu.list
        if (a.length == 0) {
          this.$toast.error('Chưa chọn vật tư nào!')
          this.XacNhan = false
          return
        }
        this.dt = a.map((e) => e)

        // Kiểm tra
        let kq = await this.KiemTra_VT(this.dt)
        if (kq != '1') {
          this.$toast.warning(kq)
          this.XacNhan = false
          return
        }

        let kt = this.dt.filter((p) => parseFloat(p.SOLUONG) > parseFloat(p.SOLUONG)) // không hiểu, nhưng server đang là như vậy
        let tongtien = this.dt.reduce((sum, b) => sum + parseFloat(b.TIEN), 0)
        if (kt.length) {
          this.$toast.error('Không được xuất quá lượng tồn của vật tư !')
          this.XacNhan = false
          return
        }
        if (!['1.N', '1.NBH', 'N.DK', 'N.DK', '1.NL', '1.3', 'N.KDV', 'N.VT', 'N_MS', 'N.KDV_DG'].includes(this.ma_md) && this.kieu_loc != 1) {
          /* tăng từ 50 => 100m --- 132 / QĐ-VNPT-KHĐT
           * lấy file văn bản ở Jira https://cntt.vnpt.vn/browse/IT360-274840
           */
          if (tongtien > 1000000 * 100) {
            if ((await this.$root.token.getMaTinh()) != 'HCM') {
              this.$toast.warning('Đơn hàng lẻ không được nhập quá 100,000,000')
              this.XacNhan = false
              return
            }
          }
        }
        if (this.kieu_loc == 1) {
          if (this.ma_md != this.MUCDICH_CCDC.DAUKY) {
            let k = this.dt.filter((z) => z.DONGIA > 30000000 && z.SOLUONG > 1)
            if (k.length) {
              this.$toast.warning('Những công cụ có đơn giá >= 30,000,000 phải có số lượng =1')
              this.XacNhan = false
              return
            }
          }
        }

        let s = this.dt.filter((z) => parseFloat(z.SOLUONG) <= 0)
        if (s.length) {
          this.$toast.error('Số lượng không thể <= 0. Bạn hãy kiểm tra lại!')
          this.XacNhan = false
          return
        }
      }
      this.XacNhan = true
      console.log('onBtnHoanThanhClicked ---> FROM')
      console.log(this.dt)
      this.dt = this.dt.map(e => e)
      this.$emit('onBtnHoanThanhClicked', { XacNhan: this.XacNhan, dt: this.dt })
      // this.Close()
    },
    showData_gridVatTu() {
      this.gridVatTu.list.forEach((a) => {
        a.DONGIA = a.DONGIA !== undefined && a.DONGIA !== null ? parseFloat(a.DONGIA) : 0
        a.SOLUONG = a.SOLUONG !== undefined && a.SOLUONG !== null ? parseFloat(a.SOLUONG) : 0
        a.TIEN = a.TIEN !== undefined && a.TIEN !== null ? parseFloat(a.TIEN) : 0

        if (a.hasOwnProperty('SL')) {
          a.SL = a.SL !== undefined && a.SL !== null ? parseFloat(a.SL) : 0
        } else {
          a.SL = 0
        }
        a.SOTHANG_PB = a.SOTHANG_PB !== undefined && a.SOTHANG_PB !== null ? parseInt(a.SOTHANG_PB) : 0
      })
    },

    /**
     * @see: private void gridViewVatTu_ValidateRow(object sender, DevExpress.XtraGrid.Views.Base.ValidateRowEventArgs e)
     */
    async gridViewVatTu_ValidateRow(arg) {
      //modify cho phù hợp vs web
      console.log('gridViewVatTu_ValidateRow')


      let changes = this.$refs.gridVatTu.getBatchChanges()

      console.log(this.gridVatTu.list)

      let row = this.gridVatTu.list.find((e) => e.ID_GEN == arg.rowData.ID_GEN)
      // changes.changedRecords.find((e) => e.ID_GEN == this.gridVatTu.value.ID_GEN)
      console.log(arg)
      console.log(changes)
      console.log(row)
      if (arg.columnName == 'SOLUONG') {
        if (arg.value < 0) {
          this.$toast.info('Số lượng cấp phải lớn hơn 0')
          this.$refs.gridVatTu.grid.refreshColumns()
          return
        } else if (arg.value == 0) {
          row.TIEN = 0
          row.DONGIA = 0
          row.SOLUONG = 0 // bổ sung thêm, cellSave ko save tới datasource
          row.CHON = 's0'
          this.dtVatTu.push(row)
          this.gridControl1.list.push(row)
          this.dtLuuVT_temp = this.dtLuuVT.filter(e => e.ID_GEN !=row.ID_GEN)
          this.dtLuuVT = this.dtLuuVT_temp
          this.gridVatTu.list = this.dtLuuVT_temp.map(e => e)

          await this.load_grid()
          this.$refs.gridVatTu.grid.refreshColumns()
        }
      }
      let soluong = parseFloat(row.SOLUONG)
      let dongia = parseFloat(row.DONGIA)
      let tien = parseFloat(row.TIEN)
      let vattu_id = row.VATTU_ID
      if (arg.columnName == 'SOLUONG') {
        tien = arg.value * dongia // arg.value new edited value
        row.TIEN = tien
        row.SOLUONG = arg.value //
        row.CHON = 's1'
      }
      if (arg.columnName == 'DONGIA') {
        if(arg.value<0) {
          this.$toast.info('Đơn giá ko thể âm')
          this.$refs.gridVatTu.grid.refreshColumns()
          return
        }
        tien = arg.value * soluong // arg.value new edited value
        row.TIEN = tien
        row.DONGIA = arg.value //
        row.CHON = 's1'
      }
      if (arg.columnName == 'HAN_BH') {
        row.HAN_BH = arg.value ? arg.value : ''
      }
      let primaryKeys = this.$refs.gridVatTu.grid.getPrimaryKeyFieldNames()
      // this.$refs.gridVatTu.grid.setRowData(primaryKeys[0], row)

      // this.$refs.gridVatTu.batchUpdate(changes)

      // setTimeout(() => {
      //   this.$refs.gridVatTu.grid.refreshColumns()
      // }, 300)
      this.$refs.gridVatTu.grid.refreshColumns()
    },
    gridView1_CellValueChanged() {},
    gridView1_ValidateRow() {},
    async gridView1_MouseDown(row) {
      row.data.CHON = 's1'
      this.dtLuuVT.push(row.data)
      this.gridVatTu.list = this.dtLuuVT.map(e => e)
      this.dtVatTu.splice(row.rowIndex, 1)
      this.gridControl1.list = this.dtVatTu
      await this.load_grid()
      this.$refs.gridVatTu.grid.refreshColumns()
      // this.$refs.TableGridControl1.refreshColumns()
    },
    async btnThemVT_ButtonClick(row) {
      // try {
      //wtf
      console.log('this.ma_md')
      console.log(this.ma_md)
      console.log('this.quyen')
      console.log(this.quyen)
      if (this.ma_md == '1.N' || this.ma_md == '1.NL' || this.ma_md == 'N.KDV' || this.ma_md == 'N.VT' || this.ma_md == 'N.DK' || ((this.ma_md == '1.3' || this.ma_md == 'N-X.DCMSL' || this.ma_md == '1.4') && this.quyen)) {
        row.ID_GEN = this.uuidv4()
        this.dtLuuVT.push(row)
        await this.load_grid()
      } else {
        this.$toast.error('Mã mục đích bạn chọn không có quyền thêm vật tư')
      }
      // } catch (error) {
      //     this.$toast.error("Mã mục đích bạn chọn không có quyền thêm vật tư")
      // }
    },

    async ShowDialog() {
      this.$refs.popup_frmDSVatTu.show()
    },
    async btnXoaVT_ButtonClick(row) {
      this.gridVatTu.list = this.dtLuuVT.map(e => e)

      this.dtVatTu.push(row)
      this.gridControl1.list = this.dtVatTu

      this.$refs.gridVatTu.grid.refreshColumns()
      this.$refs.TableGridControl1.grid.refreshColumns()
    },
    // tsbtnFileMau_Click(){
    //   const downloadLink = document.createElement("a");
    //   downloadLink.href =  this.$auth.getMaTinh() == "HCM" ? "http://10.70.52.164:8081/FILES/NET/VATTU/Mau_nhap_le_vattu.xls" : "http://10.70.52.164:8081/FILES/HCM/VATTU/Mau_NhapLeExcel_637189102398256929.xls"
    //   downloadLink.download = 'Mau_nhap_le_vattu.xls';
    //   downloadLink.click();
    // },
    // Hard code từ link file mẫu do Chrome chặn download file
    async tsbtnFileMau_Click() {
      let dt = [
        {
          MA_VT: '11.01.01.11.00.30',
          SOLUONG: 5,
          DONGIA: 11111
        },
        {
          MA_VT: 'BTS.HCM154.81.2011.2',
          SOLUONG: 20,
          DONGIA: 23456
        }
      ]

      let dt_hcm = [
        {
          MA_VT: "'01514063001314.001",
          SOLUONG: 2,
          DONGIA: 0,
          MATHETS: "'1514063001314",
          GHICHU: '2; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',
          CHATLUONG: 'TH_vụn'
        },
        {
          MA_VT: "'01514063001314.002",
          SOLUONG: 26,
          DONGIA: 0,
          MATHETS: "'1514063001314",
          GHICHU: '26; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',
          CHATLUONG: 'TH_vụn'
        },
        {
          MA_VT: "'01514063001314.003",
          SOLUONG: 22,
          DONGIA: 0,
          MATHETS: "'1514063001314",
          GHICHU: '23; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',
          CHATLUONG: 'TH_vụn'
        },
        {
          MA_VT: "'01514063001314.004",
          SOLUONG: 2,
          DONGIA: 0,
          MATHETS: "'1514063001314",
          GHICHU: '2; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',
          CHATLUONG: 'TH_vụn'
        },
        {
          MA_VT: "'01514063001314.005",
          SOLUONG: 1,
          DONGIA: 0,
          MATHETS: "'1514063001314",
          GHICHU: '1; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',
          CHATLUONG: 'TH_vụn'
        }
      ]
      let dt_excel = this.$auth.getMaTinh() == 'HCM' ? dt_hcm : dt
      let data1 = XLSX.utils.json_to_sheet(dt_excel)
      let wb = XLSX.utils.book_new() // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, 'Sheet1') // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, 'Mau_nhap_le_vattu.xls')
    },
    async tsbtnDocFile_Click() {
      document.getElementById('fileUploadDSVT').click()
    },
    checkfile: function() {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error('Chưa chọn file nào!')
        return false
      }

      if (this.arrayImportFile.filexcel.type != 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' && this.arrayImportFile.filexcel.type != 'application/vnd.ms-excel') {
        this.$toast.error('File không phải định dạng xlsx hoặc xls!')
        return false
      }
      return true
    },
    async onChangeDSVT() {
      this.arrayImportFile.filexcel = this.$refs.fileUploadDSVT.$refs.input.files[0]

      try {
        this.loading(true)
        if (this.checkfile()) {
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers = data.shift() // bỏ dòng header đầu tiên trong data

            // then build the json for each row
            var result = data.map(function(a) {
              var jsonRow = {}
              a.forEach(function(cellValue, cellIndex) {
                jsonRow[headers[cellIndex]] = cellValue
              })
              headers.forEach((b) => {
                if (!Object.keys(jsonRow).includes(b)) {
                  jsonRow[b] = ''
                }
              })
              return jsonRow
            })

            console.log(result, ' = result')
            if (result.length == 0) {
              this.$toast.error('File không có dữ liệu')
              return
            } else {
              if (this.$auth.getMaTinh() == 'HCM') {
                if (!('MA_VT' in result[0] && 'SOLUONG' in result[0] && 'DONGIA' in result[0] && 'MATHETS' in result[0] && 'GHICHU' in result[0] && 'CHATLUONG' in result[0])) {
                  this.$toast.error('Cấu trúc file phải có 6 cột MA_VT, SOLUONG, DONGIA, MATHETS (Có thể để trống dữ liệu), GHICHU, CHATLUONG')
                  return
                }
              } else {
                if (!('MA_VT' in result[0] && 'SOLUONG' in result[0] && 'DONGIA' in result[0])) {
                  this.$toast.error('Cấu trúc file phải có 3 cột MA_VT, SOLUONG, DONGIA!')
                  return
                }
              }
            }
            this.KiemTraDuLieu(result)
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
        }
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi upload file!')
      } finally {
        this.$refs.fileUploadDSVT.reset()
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    KiemTraDuLieu(dt_file) {
      console.log('KiemTraDuLieu')
      console.log(this.dtVatTu)
      let vat_file = dt_file.filter((e) => e.MA_VT != '')
      console.log(dt_file)
      for (let e of vat_file) {
        let vattu = this.dtVatTu.filter((a) => a.MA_VT == e.MA_VT)
        console.log(vattu)
        if (vattu.length == 0) {
          this.$toast.error('Có vật tư trong file không có trong danh sách vật tư !')
          return
        }
        let tien = parseInt(e.SOLUONG) * parseInt(e.DONGIA)
        if (this.$auth.getMaTinh() == 'HCM') {
          this.dtLuuVT.push({
            CHON: 's1',
            CTCT_ID: vattu[0].CTCT_ID,
            VATTU_ID: vattu[0].VATTU_ID,
            LOHANG: vattu[0].LOHANG,
            MA_VT: e.MA_VT,
            TEN_VT: vattu[0].TEN_VT,
            DVI_TINH: vattu[0].DVI_TINH,
            SOLUONG: parseFloat(e.SOLUONG),
            DONGIA: parseFloat(e.DONGIA),
            TIEN: parseFloat(tien),
            LOAI_TBI: vattu[0].LOAI_TBI,
            HAN_BH: vattu[0].HAN_BH,
            LOAITBI_ID: vattu[0].LOAITBI_ID,
            SOTHANG_PB: vattu[0].SOTHANG_PB,
            LOAIVT_ID: vattu[0].LOAIVT_ID,
            SL: vattu[0].SL,
            HIENTHI: vattu[0].HIENTHI,
            MATHETS: e.MATHETS,
            GHICHU: e.GHICHU,
            CHATLUONG: e.CHATLUONG,
            CHATLUONG_ID: e.CHATLUONG_ID,
          })
        } else {
          this.dtLuuVT.push({
            CHON: 's1',
            CTCT_ID: vattu[0].CTCT_ID,
            VATTU_ID: vattu[0].VATTU_ID,
            LOHANG: vattu[0].LOHANG,
            MA_VT: e.MA_VT,
            TEN_VT: vattu[0].TEN_VT,
            DVI_TINH: vattu[0].DVI_TINH,
            SOLUONG: parseFloat(e.SOLUONG),
            DONGIA: parseFloat(e.DONGIA),
            TIEN: parseFloat(tien),
            LOAI_TBI: vattu[0].LOAI_TBI,
            HAN_BH: vattu[0].HAN_BH,
            LOAITBI_ID: vattu[0].LOAITBI_ID,
            SOTHANG_PB: vattu[0].SOTHANG_PB,
            LOAIVT_ID: vattu[0].LOAIVT_ID,
            SL: vattu[0].SL,
            HIENTHI: vattu[0].HIENTHI,
            CHATLUONG: e.CHATLUONG,
            CHATLUONG_ID: e.CHATLUONG_ID,
          })
        }
      }
      let listVattuExcel = []
      let newDSVatTu = []
      for (let e of this.dtLuuVT) {
        listVattuExcel.push(e.MA_VT)
      }
      for (let e of this.dtVatTu) {
        if (!listVattuExcel.includes(e.MA_VT)) {
          newDSVatTu.push(e)
        }
      }
      this.dtVatTu = newDSVatTu
      this.gridControl1.list = this.dtVatTu
      this.gridVatTu.list = this.dtLuuVT.map(e => e)
    },
    gridVatTuSelectedRowChanged(e) {
      console.log('gridVatTuSelectedRowChanged')
      console.log(e)
      this.gridVatTu.value = e
    },
    frmChonLoHang_onBtnXacNhanClicked(data) {
      console.log('frmChonLoHang_onBtnXacNhanClicked')
      console.log(data)
      this.$refs.frmChonLoHang.hide()
      this.gridVatTu.list[this.gridVatTu.list.findIndex((e) => e.VATTU_ID == this.gridVatTu.value.VATTU_ID)].LOHANG = data.loHang
      this.$refs.gridVatTu.grid.refreshColumns()
    },
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    }
  },
  async mounted() {
    console.log('frmDSVatTu')
    //xu ly du lieu
    await this.frmDSVatTu_Load()
  }
  // created: async function() {
  //   // this.clearData()
  //   EventBus.$on('onChangeLoHang', this.onChangeLoHangHandler)
  //   // this.resetParams()
  // },
  // destroyed() {
  //   EventBus.$off('onChangeLoHang', this.onChangeLoHangHandler)
  // }
}
</script>
<style scoped></style>
