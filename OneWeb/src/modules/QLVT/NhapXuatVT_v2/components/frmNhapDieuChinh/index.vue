<template>
  <div class="">
    <div class="list-actions-top" style="position: unset;">
      <ul class="list" style="position: unset;">
        <!-- v-if="tsbtnUpPXK.visible" @click="tsbtnUpPXK_Click" :class="{'disabledInput': !tsbtnUpPXK.enabled}" -->
        <li v-if="btnTimKiem.visible" :class="{ disabledInput: !btnTimKiem.enabled }" @click="btnTimKiem_Click">
          <a> <span class="icon one-search"></span>Tìm kiếm </a>
        </li>
        <li v-if="btnLayTT.visible" :class="{ disabledInput: !btnLayTT.enabled }" @click="btnLayTT_Click">
          <a> <span class="icon one-reload1"></span>Làm mới </a>
        </li>

        <li v-if="tsbtnXacNhan.visible" :class="{ disabledInput: !tsbtnXacNhan.enabled }" @click="tsbtnXacNhan_Click">
          <a> <span class="icon one-save"></span>Xác nhận </a>
        </li>
        <li v-if="tsbtnXoa.visible" :class="{ disabledInput: !tsbtnXoa.enabled }" @click="tsbtnXoa_Click">
          <a> <span class="icon fa fa-close"></span>Gỡ chứng từ </a>
        </li>
        <li v-if="tsbtnNhapExcel.visible" :class="{ disabledInput: !tsbtnNhapExcel.enabled }" @click="tsbtnNhapExcel_Click">
          <a> <span class="icon one-xlsx-import"></span>Nhập file excel </a>
          <input ref="fileInput" type="file" id="upload" style="display: none" @change="onChangeFile" accept=".xls, .xlsx" @click="$refs.fileInput.value = null" />
        </li>
        <li v-if="tsbtnFileMau.visible" :class="{ disabledInput: !tsbtnFileMau.enabled }" @click="tsbtnFileMau_Click">
          <a> <span class="icon one-xlsx-import"></span>File Excel mẫu </a>
        </li>
      </ul>
    </div>

    <div class="box-form">
      <div class="legend-title">
        Tìm kiếm chứng từ
      </div>
      <div class="row">
        <div class="col-sm-3 col-12">
          <div class="info-row">
            <div class="key w100">Số chứng từ</div>
            <div class="value">
              <input :disabled="!txtSoChungTu.enabled" v-model="txtSoChungTu.value" type="text" class="form-control" @keyup.enter="txtSoChungTu_KeyUp" />
            </div>
          </div>
          <div class="info-row">
            <div class="key w100">Mã lô hàng</div>
            <div class="value">
              <input :disabled="!txtMaLoHang.enabled" v-model="txtMaLoHang.value" type="text" class="form-control" :readonly="true" @keyup.enter="txtMaLoHang_KeyUp" />
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w100">Nội dung CT</div>
            <div class="value">
              <input :disabled="!txtNoiDungChungTu.enabled" v-model="txtNoiDungChungTu.value" type="text" class="form-control" />
            </div>
          </div>
          <div class="info-row">
            <div class="key w100">Đến kho</div>
            <div class="value">
              <SelectExt v-model.trim="cboDenKho.value" :dataSource="cboDenKho.list" dataTextField="TEN_KHO" dataValueField="KHO_ID" placeholder="Chọn kho"></SelectExt>
            </div>
          </div>
        </div>

        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w90">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="ckbTuNgay.value" @click="onClickCBTuNgayNgay" />
                <span class="name">Từ ngày</span>
              </div>
            </div>
            <div class="value">
              <vue-date v-model="dtpTuNgay.value" format="DD/MM/YYYY" type="datetime" />
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="ckbDenNgay.value" @click="onClickCBDenNgay" />
                <span class="name">Đến ngày</span>
              </div>
            </div>
            <div class="value">
              <vue-date v-model="dtpDenNgay.value" format="DD/MM/YYYY" type="datetime" />
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="box-form">
      <div class="legend-title">Kết quả tìm kiếm<map name=""></map></div>
      <div class="table-content">
        <DataGrid :columns="grdChungTu.header" :showFilter="true" :showColumnCheckbox="false" dataKeyField="CHUNGTU_ID" :dataSource="grdChungTu.list" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" @rowSelected="grvChungTu_FocusedRowChanged" />
      </div>
    </div>

    <div class="tabs nav tab-over">
      <a v-show="xTabVt.visible" href="#tab-1" style="min-width: 195px;" :class="{ active: xTabMain.tab == 'xTabVt' }" @click="xTabMain.tab = 'xTabVt'" data-toggle="tab">Danh sách vật tư chứng từ đã chọn</a>
      <a v-show="xTabXacNhan.visible" href="#tab-2" style="min-width: 195px;" :class="{ active: xTabMain.tab == 'xTabXacNhan' }" @click="xTabMain.tab = 'xTabXacNhan'" data-toggle="tab">Danh sách vật tư thay đổi</a>
      <a v-show="xtabv2.visible" href="#tab-3" style="min-width: 195px;" :class="{ active: xTabMain.tab == 'xtabv2' }" @click="xTabMain.tab = 'xtabv2'" data-toggle="tab">Danh sách vật tư đã chọn</a>
    </div>
    <div class="tab-content">
      <div id="tab-1" class="tab-pane active" v-show="xTabMain.tab == 'xTabVt'">
        <div class="table-content">
          <DataGrid
            ref="grdChiTietVT"
            v-bind:columns="grdChiTietVTHeader"
            :showFilter="true"
            :showColumnCheckbox="false"
            @selectedRowChanged="grvChiTietVT_FocusedRowChanged"
            :dataSource="grdChiTietVT.list"
            :enabledSelectFirstRow="false"
            :allowPaging="true"
            :enablePagingServer="false"
            :editSettings="editSettings"
            dataKeyField="CTCT_ID"
            @cellSaved="grvChiTietVT_CellValueChanged"
          />
        </div>
      </div>

      <div id="tab-2" class="tab-pane active" v-show="xTabMain.tab == 'xTabXacNhan'">
        <div class="table-content">
          <DataGrid
            ref="grdChiTietDieuChinh"
            dataKeyField="ID_GEN"
            :columns="grdChiTietDieuChinhHeader"
            :showFilter="true"
            :showColumnCheckbox="true"
            :dataSource="grdChiTietDieuChinh.list"
            :enabledSelectFirstRow="false"
            :allowPaging="true"
            :enablePagingServer="false"
            :editSettings="editSettings"
            @cellSaved="editGridChiTietDieuChinh"
            @commandClick="onGridDeleteClicked"
            @selectedRowChanged="grdChiTietDieuChinhSelectedRowChanged"
          >
          </DataGrid>
        </div>
      </div>
      <!-- //TODO an tam -->
      <div id="tab-3" class="tab-pane active" v-show="xTabMain.tab == 'xtabv2'">
        <div class="table-content">
          <div class="row">
            <div class="col-sm-8 col-12 info-row">
              <div class="key w120">
                Chọn mã vật tư
              </div>
              <div class="value" style="display: flex">
                <ComboboxGrid style="flex-grow: 1" ref="cboCongTrinh" v-bind:columns="cboChonMaVT.header" v-bind:dataSource="cboChonMaVT.list" v-model.trim="cboChonMaVT.value" textField="ten_vt" valueField="vattu_id" :allowPaging="true" />
                <button v-if="cboChonMaVT.buttons[0].visible" :disabled="!cboChonMaVT.buttons[0].enabled" style="margin: 0 0 0 15px;" @click="cboChonMaVT_ButtonClick('BH')">
                  <span class="-ap icon-chevron-thin-down"></span>
                </button>
              </div>
            </div>
            <DataGrid
              :columns="grdChiTietVT_v2Header"
              ref="grdChiTietVT_v2"
              :showFilter="true"
              dataKeyField="VATTU_ID"
              :showColumnCheckbox="true"
              :dataSource="grdChiTietVT_v2.list"
              :enabledSelectFirstRow="false"
              :allowPaging="true"
              :enablePagingServer="false"
              :editSettings="editSettings"
              @cellSaved="grvChiTietVT_CellValueChanged_v2"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import api from './api'
import xlsx from 'xlsx'
import DateTimeHelper from '../../helpers/dateHelper'
// import DateTimeHelper from '../../helpers/'
import { convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault } from '../../helpers'
import { FORM_CONFIG } from '../../const'

export default {
  components: {},
  props: {
    tag: {
      type: [String, Number],
      default: 0,
      required: true
    },
    vma_md: {
      type: [String, Number],
      default: null
      // required: true
    },
    vchungtu_id: {
      type: [String, Number],
      default: null
      // required: true
    },
    vchungtu_id_v2: {
      type: [String, Number],
      default: null
      // required: true
    },
    vchungtu_luu_tam: {
      type: [String, Number],
      default: 0
      // required: true
    },
    vchungtu_gan: {
      type: [String, Number],
      default: null
      // required: true
    }
  },
  emits: ['onBtnXacNhanClicked'],
  data() {
    return {
      localLoading: 0,
      header: { text: 'NHẬP ĐIỀU CHỈNH' }, //labelControl1
      btnTimKiem: { enabled: true, visible: true },
      btnLayTT: { enabled: true, visible: true },
      tsbtnXacNhan: { enabled: true, visible: true },
      tsbtnXoa: { enabled: true, visible: true },
      tsbtnXoa: { enabled: true, visible: true },
      tsbtnNhapExcel: { enabled: true, visible: true },
      tsbtnFileMau: { enabled: true, visible: true },
      colTinhtrang: { visible: true },
      colDonG: { allowEdit: true },
      ckbTuNgay: { value: null },
      ckbDenNgay: { value: null },
      cboTinhTrangTbi: [],
      dt: [],
      grdChungTu: {
        header: [
          { fieldName: 'SOPHIEU', headerText: 'Số phiếu', allowFiltering: true },
          { fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true },
          { fieldName: 'LOAI_CT', headerText: 'Loại chứng từ', allowFiltering: true },
          { fieldName: 'NGAY_CT', headerText: 'Ngày yêu cầu', allowFiltering: true },
          { fieldName: 'TIEN', headerText: 'Tiền', type: 'number', format: 'n3', allowFiltering: true },
          { fieldName: 'KHO_GIAO', headerText: 'Từ kho', allowFiltering: true },
          { fieldName: 'KHO_NHAN', headerText: 'Đến kho', allowFiltering: true },
          { fieldName: 'ND_GIAO', headerText: 'Nội dung', allowFiltering: true }
        ],
        list: [],
        value: null
      },
      grdChiTietDieuChinh: {
        header: [],
        list: [],
        value: null
      },
      grdChiTietVT: {
        header: [],
        list: [],
        value: null
      },
      grdChiTietVT_v2: {
        header: [],
        list: [],
        value: null
      },
      cboDenKho: { enabled: true, list: [], value: null },
      vctct_id: 0,
      kho_nhan: 0,
      kho_giao: 0,
      xacnhan: false,
      sophieu: '',
      xacnhan: null,

      selectedChungTu: null,
      selectedChiTietVT: null,
      selectedChiTietDieuChinh: null,
      selectedChiTietVT_V2: null,

      //TAB
      xTabMain: { visible: true, tab: 'xTabVt' },
      xTabVt: { visible: true },
      xTabXacNhan: { visible: true },
      xtabv2: { visible: true },
      tsbtnXoa: { visible: true, enabled: true },
      txtNoiDungChungTu: { enabled: true, value: '' },
      txtSoChungTu: { enabled: true, value: '' },
      txtMaLoHang: { enabled: true, value: '' },
      dtpTuNgay: { enabled: true, value: '' },
      dtpDenNgay: { enabled: true, value: '' },
      cbxTuNgay: { enabled: true, value: '' },
      cbxDenNgay: { enabled: true, value: '' },
      cboChonMaVT: {
        enabled: true,
        buttons: [{ visible: true, enabled: true }],
        header: [
          { width: '500px', fieldName: 'ten_vt', headerText: 'Tên vật tư', allowFiltering: true },
          { width: '180px', fieldName: 'loai_tbi', headerText: 'Loại thiết bị', allowFiltering: true },
          { width: '120px', fieldName: 'dvi_tinh', headerText: 'Đơn vị tính', allowFiltering: true }
        ],
        value: ''
      },
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      editSettings: { allowEditing: true, mode: 'Batch', showConfirmDialog: false },
      //global variable
      chungtu_id: null,
      congtrinh_id: null
    }
  },
  validations: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  async mounted() {
    //set props to state
    console.log('frmNhapDieuChinh_v2')
    console.log(this.vchungtu_id)
    this.chungtu_id = this.vchungtu_id
    this.ckbTuNgay.value = true
    await this.frmNhapDieuChinh_Load()
    this.xTabMainShowDefaultView()
  },
  methods: {
    async callApiWrapper(action, body, options = { showError: true }) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        if (options.showError) {
          if (e.data != null) {
            this.$toast.error(`${e.data.message}`)
          } else {
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    xTabMainShowDefaultView() {
      if (this.xTabVt.visible) this.xTabMain.tab = 'xTabVt'
      else if (this.xTabXacNhan.visible) this.xTabMain.tab = 'xTabXacNhan'
      else if (this.xtabv2.visible) this.xTabMain.tab = 'xtabv2'
    },
    tsbtnFileMau_Click() {
      if (this.grdChiTietDieuChinh.list.length == 0) {
        this.$toast.error('Không có dữ liệu để xuất excel !')
        return
      }
      this.loading(true)

      let NameFile = 'fileDSNhapDieuChinh.xls'
      this.grdChiTietDieuChinh.list.forEach((e) => {
        ColExcel.push({
          CTCT_ID: e.CTCT_ID,
          LOHANG: e.LOHANG,
          LOHANG_CHA: e.LOHANG_CHA,
          MA_VT: e.MA_VT,
          TEN_VT: e.TEN_VT,
          SOLUONG: e.SOLUONG,
          DONGIA: e.DONGIA,
          TIEN: e.TIEN,
          LOAI_TBI: e.LOAI_TBI,
          DVI_TINH: e.DVI_TINH,
          LOAITBI_ID: e.LOAITBI_ID,
          TINHTRANG: e.TINHTRANG,
          VATTU_ID: e.VATTU_ID,
          MAKHOTD: e.MAKHOTD,
          LOAIKHO: e.LOAIKHO,
          CHATLUONG_ID: e.CHATLUONG_ID,
          DONGTBI_ID: e.DONGTBI_ID,
          HETHONG_ID: e.HETHONG_ID
        })
      })
      let dataExcel = xlsx.utils.json_to_sheet(ColExcel)
      let wb = xlsx.utils.book_new()
      xlsx.utils.book_append_sheet(wb, dataExcel, 'Sheet1')
      xlsx.writeFile(wb, NameFile)
      this.loading(false)
    },
    tsbtnNhapExcel_Click() {
      this.$refs.fileInput.click()
    },
    LAMMOI() {
      //verifed
      this.grdChungTu.list = []
      this.grdChiTietVT.list = []
      this.grdChiTietDieuChinh.list = []
      this.grdChiTietVT_v2.list = []
      this.txtSoChungTu.value = ''
      this.txtMaLoHang.value = ''
    },
    async Load_ds_chungtu() {
      //copy not verifed
      console.log('frmNhapDieuChinh Load_ds_chungtu')
      console.log(this.tag)
      console.log(this.vchungtu_id)
      console.log(this.chungtu_id)
      console.log(this.vchungtu_luu_tam)
      let tungay = null,
        denngay = null
      if (this.ckbTuNgay.value) {
        tungay = this.dtpTuNgay.value
      }
      if (this.ckbDenNgay.value) {
        denngay = this.dtpDenNgay.value
      }

      if (this.tag == '4' && this.vchungtu_luu_tam != 0) {
        console.log('Load_ds_chungtu - 1')
        await this.LAY_DS_VTCT_SERIAL()
      } else {
        console.log('Load_ds_chungtu - 2')
        let kieu = this.tag == '1' ? 'OK' : this.txtMaLoHang.value.trim()
        let ds = await this.callApiWrapper(api.sp_lay_ds_chungtu_frmnhapdieuchinh, {
          vsochungtu: this.txtSoChungTu.value,
          vmalohang: kieu,
          vtungay: tungay,
          vdenngay: denngay
        })
        console.log('ds_chung_tu_v3')
        console.log(ds)
        ds = ds.map((e) => {
          e.TIEN = parseInt(e.TIEN)
          return e
        })
        if (this.chungtu_id_v2 > 0) {
          this.grdChungTu.list = ds.filter((x) => x.CHUNGTU_ID == this.chungtu_id)
        } else {
          this.grdChungTu.list = ds
        }
        if (ds.length == 0) {
          this.$toast.info('Không tìm thấy chứng từ nào phù hợp')
        }
      }
    },

    async LAY_DS_VTCT_SERIAL() {
      //copy not verifed
      this.grdChiTietDieuChinh.list = []
      this.grdChiTietDieuChinh.list = []
      let res = await this.callApiWrapper(api.lay_ds_vattu_chungtu, { chungtu_id: this.chungtu_id })
      let ds = res
      console.log('lay_ds_vattu_chungtu')
      console.log(ds)

      if (ds.length > 0) {
        if (this.tag == 1) {
          this.grdChiTietVT_v2.list = ds
        } else if (this.tag == 2) {
          this.grdChiTietVT.list = ds
        } else if (this.tag == 3) {
          this.grdChiTietVT.list = ds
          this.grdChiTietVT.list.forEach((element) => {
            element.SPCHIAVT = 1
          })
          this.grdChiTietDieuChinh.list = this.grdChiTietVT.list.map((e) => {
            return e
          })
        } else if (this.tag == '4') {
          // if (!ds2.Columns.Contains("ISCHECK"))
          // {
          //     DataColumn ischeck = new DataColumn("ISCHECK", typeof(System.String));
          //     ischeck.DefaultValue = "0";
          //     ds2.Columns.Add(ischeck);
          // }
          this.grdChiTietVT_v2.list = ds
        }
      } else {
        this.grdChiTietVT.list = ds
      }
    },
    async LAY_DS_CHITIET_CT(index) {
      console.log('LAY_DS_CHITIET_CT')
      console.log('SPCHIAVT: ' + this.selectedChiTietVT.SPCHIAVT)
      console.log('this.grdChiTietDieuChinh.list')
      console.log(this.grdChiTietDieuChinh.list)
      let dr_v = {}
      let ds_dk = this.grdChiTietVT.list.map((e) => e)
      let bltrung = false
      let blcheck = false
      let ds = this.grdChiTietDieuChinh.list.map((e) => e)

      if (ds == null) {
        ds = ds_dk
      }
      let soluong = this.grdChiTietVT.list[index].SPCHIAVT
      let soluong_goc = this.grdChiTietVT.list[index].SOLUONG
      let chia_het = Math.floor(soluong_goc / soluong)
      let chia_du = soluong_goc % soluong
      console.log(soluong)
      console.log(soluong_goc)
      console.log(chia_het)
      console.log(chia_du)
      for (let i = 0; i < soluong; i++) {
        let ldr = {}
        dr_v = ds_dk[index]
        console.log('dr_v')
        console.log(dr_v)
        ldr.CTCT_ID = dr_v.CTCT_ID
        ldr.LOHANG_CHA = dr_v.LOHANG
        ldr.MA_VT = dr_v.MA_VT
        ldr.TEN_VT = dr_v.TEN_VT
        ldr.DVI_TINH = dr_v.DVI_TINH
        ldr.LOAI_TBI = dr_v.LOAI_TBI
        ldr.VATTU_ID = dr_v.VATTU_ID
        if (this.vma_md == 'N.DCDG' || this.vma_md == 'SC_BH_DONGIA') {
          ldr.SOLUONG = 0
          ldr.DONGIA = 0
          ldr.TIEN = 0
        } else if (this.vma_md == 'N.TTCL') {
          ldr.DONGIA = dr_v.DONGIA
          if (i == 0) ldr.SOLUONG = chia_het + chia_du
          else ldr.SOLUONG = chia_het
          ldr.TIEN = ldr.SOLUONG * dr_v.DONGIA
        } else if (this.vma_md == 'NV_DAUTU_BQLDA' || this.vma_md == 'NV_KHAC_BQLDA') {
          ldr.SOLUONG = soluong_goc
          ldr.DONGIA = dr_v.DONGIA
          ldr.TIEN = ldr.SOLUONG * ldr.DONGIA
        }
        ldr.CHUNGTU_ID = dr_v.CHUNGTU_ID
        ldr.LOAITBI_ID = dr_v.LOAITBI_ID
        ldr.MAKHOTD = dr_v.MAKHOTD
        ldr.LOAIKHO = dr_v.LOAIKHO
        ldr.CHATLUONG_ID = dr_v.CHATLUONG_ID == '' || dr_v.CHATLUONG_ID == null ? 0 : dr_v.CHATLUONG_ID
        ldr.HETHONG_ID = dr_v.HETHONG_ID == '' || dr_v.HETHONG_ID == null ? 0 : dr_v.HETHONG_ID
        ldr.DONGTBI_ID = dr_v.DONGTBI_ID == '' || dr_v.DONGTBI_ID == null ? 0 : dr_v.DONGTBI_ID
        ldr.TONGSL = dr_v.TONGSL
        console.log('loi o ds')
        console.log(ds)
        console.log(ldr)
        console.log(this.selectedChungTu)
        for (let dr of ds) {
          if (dr.CHUNGTU_ID != ldr.CHUNGTU_ID) {
            bltrung = true
            break
          }
        }
        if (bltrung) {
          this.$toast.error('Chỉ cho phép điều chỉnh 1 chứng từ xuất kho!')
          return
        }
        // code C# chuối vl, ds = this.grdChiTietDieuChinh.list
        ds.push(ldr)
        console.log('358 - LAY_DS_CHITIET_CT')
        console.log(ds)
        console.log(this.grdChiTietDieuChinh.list)
      }
      this.grdChiTietDieuChinh.list = ds.map((e, idx) => {
        console.log('mapper')
        console.log(e)
        console.log(idx)
        e.ID_GEN = this.uuidv4()
        return e
      })
    },
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    //private void btnLayTT_Click(object sender, EventArgs e)
    btnLayTT_Click() {
      this.LAMMOI()
    },
    //private void tsbtnXacNhan_Click(object sender, EventArgs e)
    async tsbtnXacNhan_Click() {
      //wtf
      console.log('tsbtnXacNhan_Click')

      console.log(this.selectedChungTu)
      console.log(this.tag)

      if (this.tag == 1) {
        try {
          let kq = await this.callApiWrapper(api.fn_xacnhan_frmnhapdieuchinh_tag, {
            vkieu: 0,
            vchungtu_id: this.selectedChungTu.CHUNGTU_ID,
            vchungtu_gan: this.vchungtu_gan,
            vngay_ct: getDateStringOrDefault(this.selectedChungTu.NGAY_CT, null, FORM_CONFIG.CAP_NHAT_CT_THEO_KIEU_DATE_FORMAT)
          })
          console.log(kq)
          if (kq == 1) {
            this.$toast.success('Gán thành công')
          } else {
            this.$toast.error(`${kq}`)
            return
          }
        } catch (e) {}
      } else if (this.tag == 2) {
        let gr_chitiet = this.grdChiTietVT.list.map((e) => e)
        let gr_chia = this.grdChiTietDieuChinh.list.map((e) => e)
        let sum = 0
        let sl = 0
        let ctct_id = 0
        let vt = ''
        for (let i = 0; i < gr_chitiet.length; i++) {
          if (gr_chitiet[i].SPCHIAVT > 0) {
            for (let j = 0; j < gr_chia.length; j++) {
              if (gr_chitiet[i].CTCT_ID == gr_chia[j].CTCT_ID) {
                if (gr_chia[j].SOLUONG < 1) {
                  this.$toast.info('Số lượng phải lớn hơn 0')
                  return
                }
                sum += gr_chia[j].SOLUONG
                ctct_id = gr_chia[j].CTCT_ID
              }
            }
            if (ctct_id == gr_chitiet[i].CTCT_ID) {
              if (sum != gr_chitiet[i].SOLUONG) {
                vt += gr_chitiet[i].TEN_VT
                sl += gr_chitiet[i].SOLUONG
                this.$toast.info('[' + vt + '] đã chia ra: [' + sum + '] phải bằng số lượng cũ: [' + sl + ']')
                sum = 0
                return
              }
              sum = 0
            }
          } else {
            this.$toast.info('Số lượng chia phải lớn hơn 0')
            // break
            return
          }
        }
        // gr_chitiet.forEach((i) => {
        //   if (i.SPCHIAVT > 0) {
        //     gr_chia.forEach((j) => {
        //       if (i.CTCT_ID == j.CTCT_ID) {
        //         if (j.SOLUONG < 1) {
        //           this.$toast.info('Số lượng phải lớn hơn 0')
        //           return
        //         }
        //         sum += j.SOLUONG
        //         ctct_id = j.CTCT_ID
        //       }
        //     })
        //     if (ctct_id == i.CTCT_ID) {
        //       if (sum != i.SOLUONG) {
        //         vt += i.TEN_VT
        //         sl += i.SOLUONG
        //         this.$toast.info('[' + vt + '] đã chia ra: [' + sum + '] phải bằng số lượng cũ: [' + sl + ']')
        //         sum = 0
        //         return
        //       }
        //       sum = 0
        //     }
        //   } else {
        //     this.$toast.info('Số lượng chia phải lớn hơn 0')
        //     return
        //   }
        // })
        console.log('451 - gr_chia')
        console.log(gr_chia)
        if (this.vma_md == 'N.TTCL') {
          let tt_check = gr_chia.filter((x) => x.TINHTRANG == null)
          if (tt_check.length > 0) {
            this.$toast.info('Bạn chưa chọn hết tình trạng cho vật tư !')
            return
          }
        }
        console.log(this.grdChiTietDieuChinh.list)
        this.dt = this.grdChiTietDieuChinh.list
      } else if (this.tag == 3) {
        // if(['N.DCDG', 'N.TTCL', 'SC_BH_DONGIA', 'DC_ĐT.XDCB', 'DC_SCL.DDTS', 'DC_CT', 'DC_CT_TTVT', 'DC_CTDP', 'DC_LDSC'].includes(this.vma_md)) {
        //   if(isNullOrEmpty(this.cboCongTrinh.value)) {
        //     this.$toast.warning('Bạn chưa chọn công trình!')
        //     return
        //   }
        //   // if(this.selectedChungTu) {

        //   // }
        //   if(this.cboCongTrinh.value == this.selectedChungTu.CONGTRINH_ID) {
        //     this.$toast.warning(`Công trình đang chọn phải khác công trình xuất: ${this.selectedChungTu.SOHIEU_CT}!`)
        //     return
        //   }
        // }
        this.congtrinh_id = this.selectedChungTu.CONGTRINH_ID
        this.dt = this.grdChiTietDieuChinh.list
        this.xacnhan = true //xemlai
      } else if (this.tag == 4) {
        if (this.cboDenKho.value == null || this.cboDenKho.value == 0) {
          this.$toast.warning('Chưa chọn kho đến !')
          return
        }
        if (isNullOrEmpty(this.txtNoiDungChungTu.value)) {
          this.$toast.warning('Chưa nhập nội dụng chứng từ !')
          return
        }
        ;``
        if (!confirm(`Bạn thực sự muốn nhập vật tư thay đổi về kho ${this.cboDenKho.list.find((e) => e.KHO_ID == this.cboDenKho.value)?.TEN_KHO} ?`)) return
        let dsTemp = []
        this.grdChiTietVT_v2.list.forEach((e) => {
          dsTemp.push({
            CTCT_ID: e.CTCT_ID,
            MA_VT: e.MA_VT
          })
        })

        let data = {
          ds: JSON.stringify(dsTemp),
          chungTuId: this.chungtu_id,
          chungTuDaXoa: this.vchungtu_luu_tam,
          khoNhanId: this.cboDenKho.value,
          noiDung: this.txtNoiDungChungTu.value,
        }
        let res = await this.callApiWrapper(api.tao_chung_tu_dieu_chinh_psis, data)
        if (res == 'error') {
          return
        } else {
          this.$toast.success('Cập nhật thành công!!')
          this.xacnhan = true
          this.sophieu = this.txtSoChungTu.value
        }
      }
      this.sophieu = this.txtSoChungTu.value //xemlai
      this.xacnhan = true //xemlai
      // this.$parent.XacNhan_frmNhapDieuChinh()
      // this.$refs.popupNhapDieuChinh.hide()
      let output = {
        xacnhan: true,
        tag: this.tag,
        kho_nhan: this.kho_nhan,
        kho_giao: this.kho_giao,
        dt: this.dt,
        vchungtu_id: this.chungtu_id,
        sophieu: this.sophieu,
        congtrinh_id: this.congtrinh_id
      }
      this.$emit('onBtnXacNhanClicked', output)
    },
    //private void grvChungTu_FocusedRowChanged(object sender, DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs e)
    grvChungTu_FocusedRowChanged(e) {
      console.log('grvChungTu_FocusedRowChanged')
      console.log(e)
      let row = e.data
      this.selectedChungTu = row
      if (row != null) {
        //private void grvChungTu_FocusedRowChanged(int index)
        this.chungtu_id = row.CHUNGTU_ID
        this.txtSoChungTu.value = row.SOPHIEU
        this.LAY_DS_VTCT_SERIAL()
        this.kho_giao = row.KHO_GIAO_ID
        this.kho_nhan = row.KHO_NHAN_ID
      } else {
        this.txtSoChungTu.value = ''
        this.grdChiTietVT.list = []
        this.grdChiTietDieuChinh.list = []
        this.grdChiTietVT_v2.list = []
        this.chungtu_id = 0
      }
    },
    //private void grvChiTietVT_FocusedRowChanged(object sender, DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs e)
    grvChiTietVT_FocusedRowChanged(data) {
      console.log('grvChiTietVT_FocusedRowChanged')
      console.log(data)
      this.selectedChiTietVT = data
      if (data != null) {
        //private void grvChiTietVT_FocusedRowChanged(int index)
        this.vctct_id = data.CTCT_ID
      }
    },

    async frmNhapDieuChinh_Load() {
      console.log(this.columnTab2Nomarl)
      // this.columnTab1 = this.columnTab1Normal
      // this.columnTab2 = this.columnTab2Normal

      this.dtpDenNgay.value = moment().format('DD/MM/YYYY')
      this.dtpTuNgay.value = moment()
        .add(-7, 'days')
        .format('DD/MM/YYYY')
      // new Date(Date.now() - 7 * 24 * 60 * 60 * 1000)
      console.log('frmNhapDieuChinh_Load')
      console.log('this.tag')
      console.log(this.tag)
      console.log('this.vma_md')
      console.log(this.vma_md)
      if (this.tag == 2) {
        this.xtabv2.visible = false
        if (this.vma_md == 'N.DCDG') {
          this.header = 'NHẬP ĐIỀU CHỈNH ĐƠN GIÁ'
          this.colTinhtrang.visible = false
        }
        if (this.vma_md == 'N.TTCL') {
          this.header = 'NHẬP ĐIỀU CHỈNH TÌNH TRẠNG, CHẤT LƯỢNG'
          this.colDonG.allowEdit = false
        }
        this.cboTinhTrangTbi = await this.callApiWrapper(api.tinh_trang_thiet_bi)
      } else if (this.tag == 1) {
        this.xtabv2.visible = false
        this.xTabVt.visible = false
        this.xTabXacNhan.visible = false
        this.tsbtnXoa.visible = true

        // this.visibleTab1 = false
        // this.visibleTab2 = false
        // this.visibleTab3 = false
        // this.visibleGoChungTu = true
      } else if (this.tag == 3) {
        this.xtabv2.visible = false
        // this.cboCongTrinh.list = await this.callApiWrapper(api.ds_cong_trinh, {
        //   kieuId: 1,
        //   nhanVienId: this.$root.token.getNhanVienID(),
        //   maNguoiDung: await this.$root.token.getUserName()
        // })
        // if (this.vma_md == 'NV_KHAC_BQLDA' || this.vma_md == 'NV_DAUTU_BQLDA') {
        //   this.columnTab1 = this.columnTab1Hide
        //   this.columnTab2 = this.columnTab2Hide
        //   this.visibleFileMau = false
        //   this.visibleNhapExcel = false
        //   this.txtMaLoHang.value = this.vma_md
        // }
        // xtabv2.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never
        if (['DC_SCL.DDTS', 'DC_ĐT.XDCB', 'DC_CT', 'DC_CT_TTVT', 'DC_CTDP', 'DC_LDSC'].includes(this.vma_md)) {
          this.header.text = 'NHẬP ĐIỀU CHỈNH TÀI KHOẢN ĐỐI ỨNG BQLDA'
          this.colTinhtrang.visible = false
          this.colDonG.allowEdit = false
          // gridColumn81.OptionsColumn.ReadOnly = true;
          // colSL.OptionsColumn.ReadOnly = true;
          // colDonG.OptionsColumn.ReadOnly = true;
          this.tsbtnFileMau.visible = false
          this.tsbtnNhapExcel.visible = false
          this.txtMaLoHang.enabled = false
          this.txtMaLoHang.value = this.vma_md
        }
        this.cboTinhTrangTbi =  await this.callApiWrapper(api.tinh_trang_thiet_bi)
      } else if (this.tag == 4) {
        this.xTabXacNhan.visible = false
        this.xTabVt.visible = false
        this.header.text = 'NHẬP ĐIỀU CHỈNH MÃ VẬT TƯ TỪ PSIS'
        //txtMaLoHang.Enabled = false;
        this.txtMaLoHang.Text = this.vma_md
        // txtNDCT.Properties.ReadOnly = false;
        // cboDenKho.list.Properties.ReadOnly = false;
        this.cboChonMaVT.enabled = true

        // bangts.HT_GLU_KHO(cboDenKho.list, ttnd.nhanvien_id, ttnd.donvi_id);
        this.txtMaLoHang.value = this.vma_md
        let res = await this.callApiWrapper(api.sp_kho_giao_psis, {})
        console.log('sp_kho_giao_psis')
        console.log(res)
        this.cboDenKho.list = res
        let kq = await this.callApiWrapper(api.sp_getdanhmucvt_dvt_all)
        console.log('sp_getdanhmucvt_dvt_all')
        console.log(kq)
        this.cboChonMaVT.list = kq.filter((item) => item.vattu_id != null && item.hienthi == '1')
        await this.btnTimKiem_Click()
      }
      if (this.dt.length > 0) {
        await this.Load_ds_chungtu()
        this.grdChiTietDieuChinh.list = this.dt
      }
    },
    //private void txtSoChungTu_KeyUp(object sender, KeyEventArgs e)
    txtSoChungTu_KeyUp() {
      this.Load_ds_chungtu()
    },
    //private void txtMaLoHang_KeyUp(object sender, KeyEventArgs e
    txtMaLoHang_KeyUp() {
      this.Load_ds_chungtu()
    },
    //private void btnTimKiem_Click(object sender, EventArgs e)
    btnTimKiem_Click() {
      this.Load_ds_chungtu()
    },
    //private void grvChiTietVT_CellValueChanged(object sender, DevExpress.XtraGrid.Views.Base.CellValueChangedEventArgs e)
    async grvChiTietVT_CellValueChanged(arg) {
      console.log(arg)
      let grdChiTietVT = this.$refs.grdChiTietVT
      let changes = grdChiTietVT.getBatchChanges()
      // let row = changes.changedRecords.find((e) => e.CTCT_ID == this.selectedChiTietVT.CTCT_ID)
      // console.log(this.selectedChiTietVT)
      console.log(changes)
      // console.log('TABLE - 1 CHANGED')
      // console.log('SPCHIAVT: ' + row.SPCHIAVT)
      let row = changes.changedRecords[0]
      let ctct_id_temp = this.vctct_id
      if (arg.columnName == 'SPCHIAVT') {
        if (row.SPCHIAVT >= 0 && row.SPCHIAVT <= row.SOLUONG) {
          console.log(this.grdChiTietDieuChinh.list)
          console.log(ctct_id_temp)
          let gr_chia = this.grdChiTietDieuChinh.list.filter(function(el) {
            return el.CTCT_ID != ctct_id_temp
          })
          if (gr_chia.length > 0) {
            this.grdChiTietDieuChinh.list = gr_chia
          } else {
            this.grdChiTietDieuChinh.list = []
          }
          console.log(changes)
          grdChiTietVT.batchUpdate(changes)
          this.LAY_DS_CHITIET_CT(this.grdChiTietVT.list.findIndex((e) => e.CTCT_ID == row.CTCT_ID))
        }
      }
      this.$refs.grdChiTietVT.grid.refreshColumns()
    },
    onClickCBDenNgay() {
      this.cbxDenNgay.value = !this.cbxDenNgay.value
    },
    onClickCBTuNgayNgay() {
      this.cbxTuNgay.value = !this.cbxTuNgay.value
    },

    async tsbtnXoa_Click() {
      if (!confirm(`Bạn có muốn gỡ chứng từ xuất ra khỏi chứng từ nhập bạn đang chọn không?`)) return
      try {
        let kq = await this.callApiWrapper(api.fn_xacnhan_frmnhapdieuchinh_tag, {
          vkieu: 1,
          vchungtu_id: -1,
          vchungtu_gan: this.vchungtu_gan,
          vngay_ct: ''
        })
        if (kq == 1) {
          this.$toast.success('Gỡ chứng từ thành công!')
          this.txtSoChungTu.value = ''
          await this.Load_ds_chungtu()
          this.xacnhan = true
          // this.$parent.XacNhan_frmNhapDieuChinh()
          // this.$refs.popupNhapDieuChinh.hide()
        } else {
          this.$toast.error(res.data.data)
        }
      } catch (e) {}
    },

    getCBOTinhTrangTbi(parent) {
      return function() {
        return {
          template: {
            components: {},
            template: `<SelectExt ref="cboTinhTrangTbi" :disabled="!cboTinhTrangTbi.enabled" v-model="cboTinhTrangTbi.value" :dataSource="cboTinhTrangTbi.list" dataTextField="tinhTrangTBi" dataValueField="tinhTrangTBiId" />`,
            data() {
              return {
                data: {},
                cboTinhTrangTbi: {
                  enabled: true,
                  list: [],
                  value: null
                }
              }
            },
            computed: {},
            mounted() {
              console.log('mounted - cboTinhTrang')
              console.log(this.data)
              this.cboTinhTrangTbi.list = parent.cboTinhTrangTbi
              this.cboTinhTrang.value = null
            },
            methods: {
              cboValueChanged(e) {}
            },
            watch: {
              'cboTinhTrangTbi.value'(value) {
                let obj = parent.grdChiTietDieuChinh.list.find((item) => item.ID_GEN == this.data.ID_GEN)
                console.log('watch - cboTinhTrangTbi.value')
                console.log(value)
                obj.TINHTRANG = value
                console.log(parent.grdChiTietDieuChinh.list)
              }
            }
          }
        }
      }
    },
    //private void grdChiTietDieuChinh_CellValueChanged(object sender, CellValueChangedEventArgs e)
    async grvChiTietVT_CellValueChanged_v2(arg) {
      console.log('grvChiTietVT_CellValueChanged_v2')
      console.log(arg)
      let grdChiTietVT_v2 = this.$refs.grdChiTietVT_v2
      let changes = grdChiTietVT_v2.getBatchChanges()

      if (changes.changedRecords.length > 0) {
        return
      }
      if (arg.columnName == 'SOLUONG') {
        if (changes.changedRecords[0].SOLUONG <= 0) {
          changes.changedRecords[0].SOLUONG = 0
          this.$toast.error('Số lượng phải lớn hơn 0 !')
          return
        } else {
          changes.changedRecords[0].TIEN = changes.changedRecords[0].SOLUONG * changes.changedRecords[0].DONGIA
        }
      } else if (arg.columnName == 'MA_VT') {
        if (changes.changedRecords[0].MA_VT == null || changes.changedRecords[0].MA_VT == '') {
          this.$toast.error('Mã vật tư không được để trống')
          return
        } else {
          let res = await this.callApiWrapper(api.ds_chung_tu_v3, changes.changedRecords[0].MA_VT)
          let ds = res
          if (ds.length == 0) {
            this.$toast.error('Mã vật tư không tồn tại !')
            return
          }
          changes.changedRecords[0].TEN_VT = ds[0].tenVatTu
        }
      }

      grdChiTietVT_v2.batchUpdate(changes)
      this.$refs.grdChiTietVT_v2.grid.refreshColumns()
    },
    //private void grvChiTietDieuChinh_CellValueChanged(object sender, CellValueChangedEventArgs e)
    async editGridChiTietDieuChinh(arg) {
      console.log('editGridChiTietDieuChinh')
      console.log(arg)
      let grdChiTietDieuChinh = this.$refs.grdChiTietDieuChinh
      let changes = grdChiTietDieuChinh.getBatchChanges()
      if (changes.changedRecords.length == 0) {
        return
      }

      let CTCT_ID = this.vctct_id
      // let row = changes.changedRecords.find((e) => e.ID_GEN == this.selectedChiTietDieuChinh.ID_GEN)

      let row = changes.changedRecords[0]
      console.log(changes)
      console.log(row)

      if (arg.columnName == 'SOLUONG') {
        if (row.SOLUONG < 0) {
          row.SOLUONG = 0
          row.TIEN = 0
          row.DONGIA = 0
        } else {
          let sum = 0
          this.grdChiTietDieuChinh.list.forEach((e) => {
            if (e.CTCT_ID == CTCT_ID) {
              sum += e.SOLUONG
            }
          })
          // row.TONGSL = row.SOLUONG - sum
          row.TIEN = row.SOLUONG * row.DONGIA
        }
      } else if (arg.columnName == 'DONGIA' || arg.columnName == 'SOLUONG') {
        let dongia = row.DONGIA
        let sl_dieuchinh = row.SOLUONG
        if (row.DONGIA < 0) {
          row.TIEN = 0
          row.DONGIA = 0
        } else {
          row.TIEN = row.SOLUONG * row.DONGIA
        }
      }

      // changes.changedRecords = changes.changedRecords.filter((e) => e.ID_GEN == this.selectedChiTietDieuChinh.ID_GEN)

      // let index = this.grdChiTietDieuChinh.list.findIndex((e) => e.ID_GEN == row.ID_GEN)
      // console.log('output-change')
      // console.log(JSON.stringify(changes))
      // console.log(JSON.stringify(row))
      // console.log(JSON.stringify(index))
      // this.grdChiTietDieuChinh.list[index] = row
      // this.grdChiTietDieuChinh.list = this.grdChiTietDieuChinh.list.map((e) => e)
      // grdChiTietDieuChinh.list.updateRow(index, row)
      // changes.changedRecords = this.grdChiTietDieuChinh.list.map(e => e)
      grdChiTietDieuChinh.batchUpdate(changes)
      this.$refs.grdChiTietDieuChinh.grid.refreshColumns()
      // grdChiTietDieuChinh.list.grid.refreshColumns()
    },
    async onChangeFile(e) {
      const files = e.target.files
      this.duongDan = e.target.files[0].name
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError('File excel không đúng định dạng!')
      }
      const fileReader = new FileReader()
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result
          const XLSX = xlsx
          const workbook = XLSX.read(data, {
            type: 'binary'
          })
          const wsname = workbook.SheetNames[0]
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
          console.log(ws)
          let res = await this.callApiWrapper(api.kiem_tra_file_dieu_chinh, {
            chungTuId: this.chungtu_id,
            lyDoTraId: ws
          })
          if (!res.data.data.includes('OK')) {
            this.$toast.error(res.data.data)
            return
          }

          this.grdChiTietDieuChinh.list = []
          this.ws.forEach((element) => {
            if (element.SOLUONG != null && element[DONGIA] != null) {
              element.TIEN = element.SOLUONG * element[DONGIA]
            }
          })
          this.grdChiTietDieuChinh.list = ws
          this.changeTab(1)
        } catch (error) {
          return this.$root.toastError('Kiểm tra dữ liệu file đầu vào!')
        }
      }
      fileReader.readAsBinaryString(files[0])
    },
    onGridDeleteClicked(arg) {
      this.grdChiTietDieuChinh.list = this.grdChiTietDieuChinh.list.filter((item) => item.CTCT_ID !== arg.rowData.CTCT_ID)
    },
    grdChiTietDieuChinhSelectedRowChanged(e) {
      console.log('grdChiTietDieuChinhSelectedRowChanged')
      console.log(e)
      this.selectedChiTietDieuChinh = e
    },
    async cboChonMaVT_ButtonClick(buttonTag) {
      console.log('cboChonMaVT_ButtonClick')
      // try {
      if (buttonTag == 'BH') {
        if (this.cboChonMaVT.value == null) {
          this.$toast.warning('Bạn chưa chọn vật tư nào !!!')
          return
        }

        console.log(this.grdChiTietVT_v2.list)
        if (this.grdChiTietVT_v2.list == null || this.grdChiTietVT_v2.list == 0) return
        let checkis = this.$refs.grdChiTietVT_v2.getSelectedRecords()
        console.log('checkis')
        console.log(checkis)
        if (checkis.length == 0) {
          this.$toast.warning('Chưa tích chọn dòng vật tư nào cần đổi !')
          return
        }
        let vattuchon = this.cboChonMaVT.list.find((e) => e.vattu_id == this.cboChonMaVT.value)
        console.log('vattuchon')
        console.log(vattuchon)
        for (let i of checkis) {
          let exam = await this.callApiWrapper(api.kt_dieuchinh_vt_psis, {
            vctct_id: i.CTCT_ID,
            vma_vt_moi: vattuchon.ma_vt
          })
          if (exam != '1') {
            this.$toast.error(exam)
            return
          }
          i.MA_VT = vattuchon.ma_vt
          i.TEN_VT = vattuchon.ten_vt
        }
        this.$refs.grdChiTietVT_v2.grid.refreshColumns()
      }
      // } catch (ex) {
      //   this.$toast.warning(`Lỗi hàm chọn từ vật tư ${ex}`)
      // }
    }
  },
  computed: {
    grdChiTietVTHeader() {
      return [
        { fieldName: 'LOHANG', headerText: 'Mã lô hàng', allowFiltering: true },
        { fieldName: 'TEN_VT', headerText: 'Tên VT', allowFiltering: true },
        { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
        { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
        { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true },
        // { fieldName: 'TONGSL', headerText: 'Tổng SL', allowFiltering: true, visible: false },
        { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, type: 'number', format: 'n3' },
        { fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true, type: 'number', format: 'n3' },
        { fieldName: 'VAT', headerText: 'Thuế', allowFiltering: true },
        { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị', allowFiltering: true },
        { fieldName: 'SPCHIAVT', headerText: 'Chia số lượng', allowFiltering: true, allowEditing: true, type: 'number', editType: 'numericedit' },
        { fieldName: 'CTCT_ID', headerText: '', visible: false, isPrimaryKey: true }
      ]
    },
    grdChiTietDieuChinhHeader() {
      let header = [
        { fieldName: 'ID_GEN', headerText: '', visible: false, isPrimaryKey: true },
        { fieldName: 'LOHANG', headerText: 'Mã lô hàng', allowFiltering: true },
        { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
        { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
        { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
        { fieldName: 'TONGSL', headerText: 'Tổng SL', allowFiltering: true },
        { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true, allowEditing: this.tag != 3, type: 'number', editType: 'numericedit' },
        { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, allowEditing: this.tag != 3, type: 'number', format: 'n3', editType: 'numericedit' },
        { width: 220, fieldName: 'TINHTRANG', headerText: 'Tình trạng', textAlign: 'Left', allowFiltering: false, template: this.getCBOTinhTrangTbi(this) },
        { fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true, type: 'number', format: 'n3' },
        { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị', allowFiltering: true },
        {
          visible: this.tag != 3,
          fieldName: '',
          headerText: 'Xóa',
          allowFiltering: false,
          width: 70,
          commands: [
            {
              type: 'Xóa',
              buttonOption: {
                cssClass: 'btn btn-second button-not-bg lh14 pad4',
                iconCss: '-ap icon-close'
              }
            }
          ],
          cssClass: 'text-center'
        },
        { fieldName: 'CTCT_ID', headerText: '', visible: false, isPrimaryKey: true }
      ]
      header = header.filter((e) => {
        // console.log('header')
        // console.log('e')
        // console.log(e)
        // console.log('this.colTinhtrang.visible')
        // console.log(this.colTinhtrang.visible)
        if (e.fieldName == 'TINHTRANG' && this.colTinhtrang.visible) {
          return true
        } else {
          if (e.fieldName != 'TINHTRANG') {
            return true
          }
        }
        return false
      })
      header = header.filter((e) => {
        if (e.fieldName == 'DONGIA' && this.colTinhtrang.visible) {
          e.allowEditing = false
        }
        return e
      })

      // console.log('output header')
      // console.log(header)
      return header
    },
    grdChiTietVT_v2Header() {
      return [
        { fieldName: 'LOHANG', headerText: 'Mã lô hàng', allowFiltering: true },
        { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
        { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
        { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
        { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true, type: 'number' },
        { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, type: 'number', format: 'n3' },
        { fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true, type: 'number', format: 'n3' },
        { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị', allowFiltering: true },
        { fieldName: 'VATTU_ID', headerText: '', visible: false, isPrimaryKey: true }
      ]
    }
  }
}
</script>
