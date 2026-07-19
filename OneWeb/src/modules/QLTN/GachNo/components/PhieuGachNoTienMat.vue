<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgPhieuTTGachTienMat"
    :header='$props.header'
    showCloseIcon=true
    width='95%'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    :beforeClose="onBeforeClose"
    :beforeOpen="onBeforeOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
            <li>
              <a href="#" v-on:click.prevent="doSearch()"><span class="icon one-search"></span> Tìm kiếm</a>
            </li>
            <li>
              <a href="#" v-on:click.prevent="doUpdate()"><span class="icon  one-save"></span> Cập nhật</a>
            </li>
            <li>
              <a :class="{ disabled: !f_enabled }" href="#" v-on:click.prevent="doDelete()"><span class="icon one-trash"></span> Xóa phiếu</a>
              <PopupXacNhanXoaPhieu ref="refXoaPhieuTT" :kiemtra_matkhau="this.kiemtra_matkhau" :kycuoc="this.params.p_kycuoc" :dsPhieu="dsPhieuCanXoa" @acceptChange="deletePhieuThuHandler"></PopupXacNhanXoaPhieu>
            </li>
            <li>
              <a :class="{ disabled: !f_enabled }" href="#" v-on:click.prevent="doUpdateDate()" title="Sửa lại ngày thanh toán"><span class="icon one-change-date"></span> Đổi ngày</a>
              <PopupThayDoiNgayThanhToan ref="dlgThayDoiNgayTT" :kycuoc="f_kycuoc" :phieu="selectedRow" @acceptChange="updateDatePhieuThuHandler"></PopupThayDoiNgayThanhToan>
            </li>
            <li>
              <a  href="#" v-on:click.prevent="doCancel()" title="Hủy thao tác"><span class="icon ui-1_circle-remove nc-icon-glyph"></span> Hủy</a>
            </li>
            <li>
              <a  href="#" v-on:click.prevent="doPrint()" title="In phiếu thu"><span class="icon one-print"></span>In phiếu</a>
            </li>
            <li>
              <a href="#" v-on:click.prevent="doPrintInvoice()" title="In hóa đơn"><span class="icon one-print"></span>In hóa đơn</a>
            </li>
            <li>
              <a href="#" v-on:click.prevent="doExportExcel()"><span class="icon one-excel"></span> Xuất Excel</a>
            </li>
            <li>
              <a href="#" v-on:click.prevent="doSyncVNP()" title="Đồng bộ VNP"><span class="icon one-folder-sync"></span> Đồng bộ VNP</a>
            </li>

        </ul>
        <div class="more">
            <a href="#">
                <span class="icon -ap  icon-dots-three-vertical"></span>
            </a>
        </div>
      </div>
      <div class="popup-body">
        <div class="box-form">
            <div class="legend-title mart0">
                <div class="pull-left mart10">
                    Thông tin tìm kiếm
                    <div class="list-checks normal">
                        <div class="item">
                            <div class="check-action">
                                <input type="radio" checked v-model="inLien" value=1 class="check">
                                <span class="name text-danger">In 1 liên</span>
                            </div>
                        </div>
                        <div class="item">
                            <div class="check-action">
                                <input type="radio" v-model="inLien" value=2 class="check">
                                <span class="name text-danger">In 2 liên</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pull-right w120">
                    <app-ky-cuoc :value="p_default_kycuoc" @chukycuocChange="chukycuocChangeHandler"></app-ky-cuoc>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="row">
                <div class="col-md-9 col-12">
                    <div class="row">
                        <div class="col-md-4 col-xs-12">
                            <div class="info-row">
                                <div class="key w100">Mã TT</div>
                                <div class="value">
                                  <input v-model="params.p_ma_tt" type="text" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-xs-12">
                            <div class="info-row">
                                <div class="key w100">User gạch nợ</div>
                                <div class="value">
                                  <input v-model="params.p_user_gachno" type="text" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-xs-12">
                            <div class="info-row">
                                <div class="key w80">
                                    <div class="">
                                      <b-form-checkbox
                                        v-model = "params.p_chk_ngay_ttoan"
                                        class = "check"
                                        value = '1'
                                        unchecked-value = '0'
                                      >
                                        <span class="name">Ngày TT</span>
                                      </b-form-checkbox>
                                    </div>
                                </div>
                                <div class="value">
                                  <ejs-datepicker
                                    v-model="p_ngay_ttoan"
                                    :value="configs.dateConfig.value"
                                    :format="configs.dateConfig.format"
                                    :allowEdit=false
                                    :showClearButton=false
                                    :enabled="chk_ngay_ttoan"
                                    >
                                  </ejs-datepicker>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">

                        <div class="col-md-4 col-xs-12">
                            <div class="info-row">
                                <div class="key w100">
                                    <div class="">
                                      <b-form-checkbox
                                        v-model = "params.p_chk_nhom_httt"
                                        class = "check"
                                        value = '1'
                                        unchecked-value = '0'
                                      >
                                        <span class="name">Nhóm HT</span>
                                      </b-form-checkbox>
                                    </div>
                                </div>
                                <div class="value">
                                    <div class="select-custom">
                                      <ejs-dropdownlist :change="onChangeNhomHTTT" :enabled="chk_nhom_httt" :dataSource='params.p_nhom_httt_options' :fields="configs.fieldsNhomHTTT" v-model = "params.p_nhom_httt"></ejs-dropdownlist>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-xs-12">
                            <div class="info-row">
                                <div class="key w100">
                                    <div class="">
                                      <b-form-checkbox
                                        v-model = "params.p_chk_httt"
                                        class = "check"
                                        value = '1'
                                        unchecked-value = '0'
                                        title="Hình thức thanh toán"
                                      >
                                        <span class="name">Hình thức</span>
                                      </b-form-checkbox>
                                    </div>
                                </div>
                                <div class="value">
                                    <div class="select-custom">
                                      <ejs-dropdownlist :enabled="chk_httt" :dataSource='params.p_httt_options' :fields="configs.fieldsHTTT" v-model = "params.p_httt"></ejs-dropdownlist>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-xs-12">
                            <div class="info-row">
                                <div class="key w80 "  >
                                    Tổng trả
                                </div>
                                <div class="value">
                                  <div class="input-icon-right calculator-box">
                                  <vue-numeric class="text-chartE form-control tright" currency="" separator="," v-model="$data.params.p_tongtra"></vue-numeric>
                                  <span class="icon one-calculator" @click="configs.isTongTienCalShow = !configs.isTongTienCalShow"></span>
                                  <app-calculator @display="$data.params.p_tongtra = $event" :value="$data.params.p_tongtra" :is-show="configs.isTongTienCalShow" @close="configs.isTongTienCalShow = !configs.isTongTienCalShow"></app-calculator>
                                </div>
                              </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="col-md-3 col-12">
                    <div class="info-row">
                        <div class="key w80 vat padt10">Ghi chú<span class="required">*</span></div>
                        <div class="value">
                          <textarea v-model="params.p_ghichu" ref="ref_ghichu" cols="30" rows="10" class="form-control" :class="{ 'is-invalid': $v.params.p_ghichu.$error }" style="height: 70px;resize: vertical;" ></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="table-content">
              <DataGrid
                v-bind:columns="[
                  {fieldName: 'PHIEU_ID', isPrimaryKey: true, headerText: 'Phiếu ID', allowFiltering: true, width: 90},
                  {fieldName: 'NGAY_TT', headerText: 'Ngày trả', textAlign: 'Right', width: 120, allowFiltering: true},
                  {fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Left', allowFiltering: true, width: 130},
                  {fieldName: 'TEN_TT', headerText: 'Tên TT', textAlign: 'Left', allowFiltering: true, width: 200},
                  {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ TT', textAlign: 'Left', allowFiltering: true, width: 200},
                  {fieldName: 'SOTIENTRA', headerText: 'Tiền trả', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'SO_HD', headerText: 'Số HĐ', textAlign: 'Left', allowFiltering: true, width: 80},
                  {fieldName: 'CHUNGTU', headerText: 'Chứng từ', textAlign: 'Left', allowFiltering: true, width: 100},
                  {fieldName: 'HINHTHUC_TT', headerText: 'Hình thức trả', textAlign: 'Left', allowFiltering: true, width: 150},
                  {fieldName: 'USER_GACH', headerText: 'User gạch', textAlign: 'Right', allowFiltering: true, width: 150},
                  {fieldName: 'NGAY_CN', headerText: 'Ngày gạch', textAlign: 'Left', allowFiltering: true, width: 150},
                  {fieldName: 'NGAY_NH', headerText: 'Ngày NH', textAlign: 'Right', allowFiltering: true, width: 130},
                  {fieldName: 'NGAN_HANG', headerText: 'Ngân hàng', textAlign: 'Left', allowFiltering: true, width: 150},
                  {fieldName: 'TEN_NV', headerText: 'Thu ngân', textAlign: 'Left', allowFiltering: true, width: 150},
                  {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true, width: 130},
                  {fieldName: 'MANV_TC', headerText: 'Đại lý', textAlign: 'Left', allowFiltering: true, width: 150},
                  {fieldName: 'STATUS', headerText: 'Status', textAlign: 'Left', allowFiltering: true, width: 100},
                  {fieldName: 'KIEUGACH', headerText: 'Kiểu gạch', textAlign: 'Left', allowFiltering: true, width: 130},
                  {fieldName: 'PHIEU_ID_NEO', headerText: 'Phiếu NEO', textAlign: 'Left', allowFiltering: true, width: 100},
                  {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', textAlign: 'Center', allowFiltering: true, width: 100, template: this.statusTemplate, freeze: 'Right'}
                ]"
                :showColumnCheckbox="true"
                ref="dsPhieuTToan"
                v-bind:dataSource="dsPhieuTToan"
                v-bind:aggregates="sumColumnsPhieuTT"
                :selectionSettings="selectionSettings"
                :allowExcelExport="true"
                :showFilter="true"
                :allowPaging="true"
                :enabledSelectFirstRow="false"
                :totalRecords="pageinfoDSPhieuTToan.totalElement"
                :pageSize="pageinfoDSPhieuTToan.maxSize"
                :pageIndex="pageinfoDSPhieuTToan.page"
                :key="keyDSPhieuTToan"
                @selectedRowChanged="phieuTTRowChanged"
                @recordClick="phieuTTRecordClick"
                @pageChanged="phieuTTpageChangeHandler"

                >
              </DataGrid>
            </div>
        </div>
        <div class="box-form box-tab-horizontal">
          <div class="nav tabs tab-left">
              <a href="#tabPTThangNo" :class="{active: params.p_kieutra === 0}" @click="tabGachNoClick(0)" data-toggle="tab">Tháng nợ</a>
              <a href="#tabPTKhoanMuc" :class="{active: params.p_kieutra === 1}" @click="tabGachNoClick(1)" data-toggle="tab">Khoản mục</a>
              <a href="#tabPTDichVu" :class="{active: params.p_kieutra === 2}" @click="tabGachNoClick(2)" data-toggle="tab">Dịch vụ</a>
          </div>
          <div class="tab-content" style="height: 500px;">
            <div id="tabPTThangNo" class="tab-pane" :class="{active: params.p_kieutra === 0}">
              <DataGrid
                v-bind:columns="[
                  {fieldName: 'DIENGIAI', headerText: 'Diễn giải', textAlign: 'Left'},
                  {fieldName: 'TIENTRA', headerText: 'Tiền trả', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'TRAGOC', headerText: 'Trả gốc', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'TRATHUE', headerText: 'Trả thuế', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'CHIHOAHONG', headerText: 'Chi hoa hồng', textAlign: 'Right', format: 'N0', type: 'number'},
                  ]"
                v-bind:dataSource="dsThangNo"
                v-bind:aggregates="sumColumnsDS"
                :showFilter="true"
                :allowPaging="false"
                :enablePagingServer="false"
                :totalRecords="pageinfoDSThangNo.totalElement"
                :pageSize="pageinfoDSThangNo.maxSize"
                :pageIndex="pageinfoDSThangNo.page"
                @headerCellInfo="thangnoHeaderCellInfo"
                :key="keyDSThangNo"
                >
              </DataGrid>
            </div>
            <div id="tabPTKhoanMuc" class="tab-pane" :class="{active: params.p_kieutra === 1}">
              <DataGrid
                v-bind:columns="[
                  {fieldName: 'DIENGIAI', headerText: 'Diễn giải', textAlign: 'Left'},
                  {fieldName: 'TIENTRA', headerText: 'Tiền trả', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'TRAGOC', headerText: 'Trả gốc', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'TRATHUE', headerText: 'Trả thuế', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'CHIHOAHONG', headerText: 'Chi hoa hồng', textAlign: 'Right', format: 'N0', type: 'number'},
                  ]"
                v-bind:dataSource="dsKhoanMuc"
                v-bind:aggregates="sumColumnsDS"
                :showFilter="true"
                :allowPaging="false"
                :enablePagingServer="false"
                :totalRecords="pageinfoDSKhoanMuc.totalElement"
                :pageSize="pageinfoDSKhoanMuc.maxSize"
                :pageIndex="pageinfoDSKhoanMuc.page"
                @headerCellInfo="khoannoHeaderCellInfo"
                :key="keyDSKhoanMuc"
                >
              </DataGrid>
            </div>
            <div id="tabPTDichVu" class="tab-pane" :class="{active: params.p_kieutra === 2}">
              <DataGrid
                v-bind:columns="[
                  {fieldName: 'DIENGIAI', headerText: 'Diễn giải', textAlign: 'Left'},
                  {fieldName: 'TIENTRA', headerText: 'Tiền trả', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'TRAGOC', headerText: 'Trả gốc', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'TRATHUE', headerText: 'Trả thuế', textAlign: 'Right', format: 'N0', type: 'number'},
                  {fieldName: 'CHIHOAHONG', headerText: 'Chi hoa hồng', textAlign: 'Right', format: 'N0', type: 'number'},
                  ]"
                v-bind:dataSource="dsDichVu"
                v-bind:aggregates="sumColumnsDS"
                :showFilter="true"
                :allowPaging="false"
                :enablePagingServer="false"
                :totalRecords="pageinfoDSDichVu.totalElement"
                :pageSize="pageinfoDSDichVu.maxSize"
                :pageIndex="pageinfoDSDichVu.page"
                @headerCellInfo="dichvuHeaderCellInfo"
                :key="keyDSDichVu"
                >
              </DataGrid>
            </div>
          </div>
        </div>
    </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import { Tooltip, DialogUtility } from '@syncfusion/ej2-popups'
import CommonsAPI from '../../api/Commons'
import GachNoAPI from '../../api/GachNoAPI'
import GachNoMaVachAPI from '../../api/GachNoMaVachAPI'
import XLAPI from '../../api/XuLyPhieuThanhToanAPI.js'
import KyCuoc from '@/components/KyCuoc2'
import moment from 'moment'
// import PopupXacNhanXoaPhieu from '../../components/PopupInXoaSuaPhieuThanhToan/PopupXacNhanXoaPhieu'
// import PopupThayDoiNgayThanhToan from '../../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiNgayThanhToan'
import { currency } from '@/filters/currency'
import Calculator from '@/components/Calculator'

import { required } from 'vuelidate/lib/validators'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { NHOM_HTTT,
  HTTT_NO,
  THUNO_KEY
} from '@/const/enums'

import { getDataFromStorage, setDataToStorage, previewPrint } from '@/utils/util'

Vue.use(DialogUtility)
let DialogObj

Vue.use(DialogPlugin)

export default Vue.extend({
  components: {
    appCalculator: Calculator,
    appKyCuoc: KyCuoc,
    BssRequiredMarker,
    // appDoiNgayThanhToan: DoiNgayThanhToan,
    PopupXacNhanXoaPhieu: () => import('../../components/PopupInXoaSuaPhieuThanhToan/PopupXacNhanXoaPhieu'),
    PopupThayDoiNgayThanhToan: () => import('../../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiNgayThanhToan')
    // appPrintPreview: PrintPreview
  },
  props: {
    header: {
      type: String,
      required: false
    },
    ma_tt: {
      type: String,
      required: false
    },
    kycuoc: {
      type: String,
      required: false
    },
    ma_tb: {
      type: String,
      required: false
    },
    nhom_httt: {
      type: Number,
      required: false,
      default: 0
    },
    thungan: {
      type: Object,
      required: false
    },
    kieu_ttoan: {
      type: Number,
      required: false
    },
    ngay_xl: {
      type: Number,
      default: -1
    },
    ngay_xl_tt: {
      type: Number,
      default: -1
    },
    in_biennhan_tt: {
      type: Boolean,
      default: false
    },
    kiemtra_matkhau: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    f_phieu_id () {
      return this.selectedRow !== null ? this.selectedRow.PHIEU_ID : null
    },
    f_ngay_tt () {
      return this.selectedRow !== null ? this.selectedRow.NGAY_TT : null
    },
    f_kycuoc () {
      return this.kycuoc
    },
    f_enabled () {
      return this.dsPhieuTToan.length > 0
    },
    chk_ngay_ttoan () {
      return this.params.p_chk_ngay_ttoan === '1'
    },
    p_ngay_ttoan: {
      get () {
        return this.params.p_ngay_ttoan
      },
      set (value) {
        this.params.p_ngay_ttoan = value
      }
    },
    chk_nhom_httt () {
      return this.params.p_chk_nhom_httt === '1'
    },
    chk_httt () {
      return this.params.p_chk_httt === '1'
    }
  },
  data () {
    return {
      inLien: '2',
      selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      groupOptions: {
        columns: ['CHUKYNO']
      },
      statusTemplate: function () {
        return {
          template: Vue.component('statusTemplate', {
            template: `<span :class="{'e-icons': true, '': data.TRANGTHAI == -1, 'grid-status-0': data.TRANGTHAI == 0, 'grid-status-1': data.TRANGTHAI == 1}"></span>`,
            data: function () { return { data: {} } }
          })
        }
      },
      sumTemplate: function () {
        return {
          template: Vue.component('sumTemplate', {
            template: `<span>Tổng: {{data.Sum}}</span>`,
            data: function () { return {data: {data: {}}} }
          })
        }
      },
      countTemplate: function () {
        return {
          template: Vue.component('countTemplate', {
            template: `<span>Tổng: {{data.Count}}</span>`,
            data: function () { return {data: {data: {}}} }
          })
        }
      },
      configs: {
        isTongTienCalShow: false,
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsNhomHTTT: { text: 'NHOM_HTTT', value: 'NHOM_HTTT_ID' },
        fieldsHTTT: { text: 'HTTT', value: 'HTTT_ID' }
      },
      p_default_kycuoc: null,
      dsPhieuTTCols: [],
      params: {
        p_kycuoc: moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM') + '01',
        p_khd_hientai: null,
        p_ma_tt: this.ma_tt,
        p_ma_tb: null,
        p_user_gachno: this.$auth.getUserName(),
        p_thungan: null,
        p_chk_ngay_ttoan: '0',
        p_ngay_ttoan: new Date(),
        p_chk_nhom_httt: '1',
        p_nhom_httt_options: [],
        p_nhom_httt: null,
        p_chk_httt: '0',
        p_httt_options: [],
        p_httt: null,
        p_tongtra: 0,
        p_ghichu: null,
        p_kieutra: 0, // 0; tháng, 1: khoản mục, 2: dịch vụ
        p_kieu_tk: 0, // 1 : theo mã TB, 0: không lấy
        p_quyen_dd: false, // quyền gạch nợ di động
        f_changed: false // flag data change
      },
      // dành cho form tìm kiếm
      searchData: {
        pThuNganId: null,
        pLoaiTien: 1,
        pNgayTT: null,
        pKyCuoc: null,
        pMaTT: null,
        pMaTB: null,
        pKieu: 1,
        pNhomHTTTId: null,
        pHTTTId: null,
        pTinhThuId: null, // this.$auth.getPhanVungID(),
        pNguoiCNPhieuThu: null,
        pageNum: 0,
        pageSize: 0,
        totalRow: 1
      },
      selectedRow: null,
      dsPhieuTToan: [],
      sumColumnsPhieuTT: [
        {field: 'MA_TT', type: 'Count', format: 'N0', footerTemplate: this.footerCount},
        {field: 'SOTIENTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      dsThangNo: [],
      sumColumnsDS: [
        {field: 'TIENTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TRAGOC', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TRATHUE', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'CHIHOAHONG', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      pageinfoDSThangNo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      dsKhoanMuc: [],
      pageinfoDSKhoanMuc: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      dsDichVu: [],
      pageinfoDSDichVu: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      pageinfoDSPhieuTToan: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      phieuSelected: [],
      XPParams: {
        tongTien: 0,
        tongPhieu: 0
      },
      dsPhieuCanXoa: [],
      dsPhieuCanIn: [],
      TDNTTParams: {
        ngayTT: '',
        kyCuoc: '',
        chuKy: ''
      },
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      keyDSPhieuTToan: 0,
      keyDSThangNo: 0,
      keyDSKhoanMuc: 0,
      keyDSDichVu: 0
    }
  },
  methods: {
    async getNhomHTTT () {
      try {
        const results = await CommonsAPI.getNhomHinhThucThanhToan(this.axios)
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        if (err.data !== undefined) { this.$toast.error(err.data.message_detail) } else { this.$toast.error(err) }
        return []
      }
    },
    async getTongNoTheoKyHoaDon () {
      try {
        let postData = {
          pKyCuoc: this.params.p_kycuoc,
          pMaTT: this.params.p_ma_tt
        }
        const results = await GachNoAPI.getTongNoTheoKyHoaDon(this.axios, postData)
        return results
      } catch (err) {
        console.log(err)
      }
    },
    async getDSPhieuDaGach (postData) {
      try {
        const results = await GachNoAPI.getDSPhieuDaGach(this.axios, postData)
        return results
      } catch (err) {
        console.log(err)
      }
    },
    onChangeNhomHTTT: function (args) {
      try {
        if (this.nhom_httt !== NHOM_HTTT.GACHNO_MAVACH) {
          CommonsAPI.getDanhSachHinhThucThanhToan(this.axios, {kieu: 0, nhomHTTT: args.value}).then(
            (response) => {
              if (response.data.error_code === 'BSS-00000000' &&
                  response.data.data !== undefined) {
                this.params.p_httt_options = response.data.data
                if (response.data.data.length > 0) { this.params.p_httt = response.data.data[0].HTTT_ID } else { this.params.p_httt = null }
              }
            }
          )
        } else {
          GachNoMaVachAPI.getHTTTMaVach(this.axios).then(
            (res) => {
              if (res.data.error_code === 'BSS-00000000' &&
                  res.data.data !== undefined &&
                  res.data.data.length > 0) {
                // this.params.p_httt_options = res.data.data
                res.data.data.forEach((item) => {
                  this.params.p_httt_options.push({HTTT_ID: item.HTTT_ID, HTTT: item.HINHTHUC})
                })
                this.params.p_httt = HTTT_NO.DAILY_BC_TAINHA
              } else {
                this.params.p_httt_options = []
                this.params.p_httt = null
              }
            }
          )
        }
      } catch (error) {
        this.$toast.error(error)
      }
    },
    openDialog () {
      // this.clearResult()

      this.$refs.dlgPhieuTTGachTienMat.show()
    },

    closeDialog () {
      this.$refs.dlgPhieuTTGachTienMat.hide()
    },
    onDialogClose () {
      // this.clearForm()
      // this.clearResult()
      this.$emit('onDialogClose', this.params.f_changed)
    },
    async onDialogOpen () {
      await this.initForm()
      await this.initData()
    },
    clearForm () {
      this.params.p_ghichu = ''
      this.params.p_ma_tt = ''
      // this.params.p_user_gachno = ''
      this.params.p_ngay_ttoan = new Date()
    },
    clearResult () {
      this.selectedRow = null

      this.dsPhieuTToan = []
      this.pageinfoDSPhieuTToan.page = 0
      this.pageinfoDSPhieuTToan.totalElement = 0

      this.dsThangNo = []
      this.pageinfoDSThangNo.page = 0
      this.pageinfoDSThangNo.totalElement = 0

      this.dsKhoanMuc = []
      this.pageinfoDSKhoanMuc.page = 0
      this.pageinfoDSKhoanMuc.totalElement = 0

      this.dsDichVu = []
      this.pageinfoDSDichVu.page = 0
      this.pageinfoDSDichVu.totalElement = 0
    },
    initForm () {
      this.params.p_user_gachno = this.$auth.getUserName()
      if (this.kycuoc !== null) {
        this.p_default_kycuoc = this.kycuoc
        this.params.p_kycuoc = this.kycuoc
      }
      this.params.p_ma_tt = this.ma_tt
      // this.params.p_user_gachno = this.thungan.MA_NV !== undefined ? this.thungan.MA_NV : null
      // this.params.p_thungan = this.thungan.NHANVIEN_ID
      if (this.ma_tb === null || this.ma_tb === undefined) { this.params.p_ma_tb = '0' } else { this.params.p_ma_tb = this.ma_tb }
      /*
        if(this.nhom_httt !== null)
          this.params.p_nhom_httt = this.nhom_httt
        */
      if (this.kieu_ttoan !== null) {
        if (this.kieu_ttoan === 2) { this.params.p_kieutra = 0 } else if (this.kieu_ttoan === 1) { this.params.p_kieutra = 1 } else if (this.kieu_ttoan === 3) { this.params.p_kieutra = 2 }
      }

      this.params.p_chk_ngay_ttoan = '0'
      this.params.p_ngay_ttoan = new Date()
      if (this.nhom_httt === 0 || this.nhom_httt === null) { this.params.p_chk_nhom_httt = '0' } else { this.params.p_chk_nhom_httt = '1' }
      this.params.p_chk_httt = '0'
      this.params.p_tongtra = 0
      this.params.p_ghichu = null
      this.params.p_kieutra = 0 // 0; tháng, 1: khoản mục, 2: dịch vụ
      this.params.p_kieu_tk = 0 // 1 : theo mã TB, 0: không lấy
      this.clearResult()
    },
    async initData () {
      if (this.params.p_nhom_httt_options === null || this.params.p_nhom_httt_options.length === 0) {
        const res = await this.getNhomHTTT()
        this.params.p_nhom_httt_options = res
        // this.params.p_nhom_httt = this.params.p_nhom_httt_options[0].NHOM_HTTT_ID
        if (this.nhom_httt !== null) { this.params.p_nhom_httt = this.nhom_httt } else {
          if (this.params.p_nhom_httt_options.length > 0) { this.params.p_nhom_httt = this.params.p_nhom_httt_options[0].NHOM_HTTT_ID }
        }
      }

      // this.params.p_user_gachno = this.$auth.getMaNhanVien()
      if (this.params.p_khd_hientai === null) { this.params.p_khd_hientai = this.getKyCuocHienTai() + '01' }
      /*
          this.getKyHoaDonHienTai().then(
            (res) => {
              if(res.data.error_code === 'BSS-00000000'
                && res.data.data !== undefined
                && res.data.data.length > 0)
                {
                  this.params.p_khd_hientai = res.data.data[0].KYCUOC
                }
            }
          )
          */
      if (this.params.p_ma_tt !== undefined && this.params.p_ma_tt !== null && this.params.p_ma_tt !== '') { this.doSearch() }
    },
    validateData: function () {
      let errors = []

      if (this.params.p_ma_tt !== null) { this.params.p_ma_tt = this.params.p_ma_tt.trim() }

      if (this.params.p_chk_ngay_ttoan === '1' &&
          this.params.p_ngay_ttoan === null) {
        errors.push('Ngày thanh toán bắt buộc nhập')
      }

      if (this.params.p_chk_nhom_httt === '1' &&
          this.params.p_nhom_httt === null) {
        errors.push('Nhóm HTTT bắt buộc nhập')
      }
      if (this.params.p_chk_httt === '1' &&
          this.params.p_httt === null) {
        errors.push('HTTT bắt buộc nhập')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    doSearch: function () {
      if (!this.validateData()) { return false }
      // let thungan = this.get_thungan()

      this.dsPhieuTToan = []
      this.pageinfoDSPhieuTToan.page = 0
      this.pageinfoDSPhieuTToan.totalElement = 0

      this.searchData = {
        // pThuNganId: this.thungan.NHANVIEN_ID,
        pThuNganId: null,
        pLoaiTien: 1,
        pNgayTT: this.params.p_chk_ngay_ttoan === '1' ? moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY') : null,
        pKyCuoc: this.params.p_kycuoc,
        pMaTT: this.params.p_ma_tt,
        pMaTB: null,
        pKieu: 1,
        pNhomHTTTId: this.params.p_chk_nhom_httt === '1' ? this.params.p_nhom_httt : null,
        pHTTTId: this.params.p_chk_httt === '1' ? this.params.p_httt : null,
        pTinhThuId: null, // this.$auth.getPhanVungID(),
        pNguoiCNPhieuThu: this.params.p_user_gachno,
        pageNum: this.pageinfoDSPhieuTToan.page + 1,
        pageSize: this.pageinfoDSPhieuTToan.maxSize,
        totalRow: 1
      }

      this.fetchDataSearch()
    },
    fetchDataSearch: function () {
      this.loading(true)
      // this.clearResult()
      GachNoAPI.getDSPhieuDaGach(this.axios, this.searchData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.data !== undefined &&
              res.data.data.data.length > 0) {
              this.keyDSPhieuTToan++
              this.dsPhieuTToan = res.data.data.data
              this.pageinfoDSPhieuTToan.totalElement = res.data.data.totalElement
              this.pageinfoDSPhieuTToan.page = res.data.data.page - 1
              this.params.p_tongtra = res.data.data.data[0].TONGTIENTHU_USER
            } else {
              this.dsPhieuTToan = []
              this.pageinfoDSPhieuTToan.page = 0
              this.pageinfoDSPhieuTToan.totalElement = 0
              this.params.p_tongtra = 0
              this.phieuTTRowChanged(null)
              // this.$toast.error('Không tìm thấy dữ liệu')
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },
    doUpdate: function () {
      this.phieuSelected = this.$refs.dsPhieuTToan.getSelectedRecords()

      if (this.phieuSelected.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu để cập nhật.')
        return false
      }

      this.$v.$touch()
      if (this.params.p_ghichu === null || this.params.p_ghichu.trim() === '') {
        this.$toast.error('Hãy nhập thông tin ghi chú!')
        this.$refs.ref_ghichu.focus()
        return false
      }

      this.updateProcess()

      /*
        selectedrows.forEach( item => {
          let postData = {
            "pKyCuoc": this.params.p_kycuoc,
            "pNgayTT": moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
            "pPhieuId": item.PHIEU_ID
          }

          GachNoAPI.updateNgayTT(this.axios, postData)
          .then(
            (res) => {
              if(res.data.error_code === 'BSS-00000000')
                this.$toast.success('Cập nhật phiếu ' + item.PHIEU_ID + ' thành công')
            }
          )
        })
        */
    },

    doDelete () {
      let tongTien = 0
      let tongPhieu = 0
      this.phieuSelected = this.$refs.dsPhieuTToan.getSelectedRecords()

      if (this.phieuSelected.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu để xóa.')
        return false
      }

      let errors = []
      let user = this.$auth.getUserName()

      this.phieuSelected.forEach(element => {
        if (element.USER_GACH !== user) { errors.push('Bạn không thể xóa phiếu thu ID' + element.PHIEU_ID + ' của user ' + element.USER_GACH) }
        tongTien += element.SOTIENTRA
      })

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      tongPhieu = this.phieuSelected.length
      this.XPParams = {
        tongTien: currency(tongTien),
        tongPhieu: currency(tongPhieu)
      }

      this.dsPhieuCanXoa = this.$refs.dsPhieuTToan.getSelectedRecords()

      this.$refs.refXoaPhieuTT.openDialog()
    },
    /*
      doDelete_old: function(e){
        if(this.selectedRow === null)
        {
          this.$toast.error('Chọn phiếu thanh toán cần xóa')
          return false
        }

        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có thật sự muốn xóa phiếu " + this.selectedRow.PHIEU_ID + " đã chọn không ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmDelete},
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        });
      },
      confirmDelete: function(){
        if(this.selectedRow === null)
          return
        this.loading(true)
        /*
        await Promise.all(items.map(async (item) => {
          const contents = await this.deletePhieuThu(item.PHIEU_THU)
          console.log(contents)
        }));

        this.deletePhieuThu(this.selectedRow.PHIEU_ID)
        .then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000')
            {
              DialogObj.hide()
              this.$toast.success('Xóa phiếu thành công')
              this.clearForm()
              this.clearResult()
              this.doSearch()
            }
          }
        )
        //alert('a')
      },
*/
    /*
      async deletePhieuThu(id){
        try{
          let postData = {
            "pKyCuoc": this.params.p_kycuoc,
            "pMayHuy": "-",
            "pPhieuId": id
          }
          const res = await GachNoAPI.deletePhieuThuMK(this.axios, postData)
          return res
        } catch(err) {
          this.$toast.error(err.data.message_detail)
        }

      },
*/
    doUpdateDate: function () {
      // this.$refs.dlgDoiNgayThanhToan.openDialog()
      this.phieuSelected = this.$refs.dsPhieuTToan.getSelectedRecords()

      if (this.phieuSelected.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu để đổi ngày.')
        return false
      }

      let errors = []
      let user = this.$auth.getUserName()

      this.phieuSelected.forEach(element => {
        if (element.USER_GACH !== user) { errors.push('Bạn không thể thay đổi phiếu thu ID' + element.PHIEU_ID + ' của user ' + element.USER_GACH) }
      })

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      this.TDNTTParams = {
        kyCuoc: moment(this.params.p_kycuoc, 'YYYYMMDD').format('MMYYYY'),
        chuKy: moment(this.params.p_kycuoc, 'YYYYMMDD').format('DD'),
        ngayTT: this.phieuSelected[0].NGAY_TT
      }

      this.$refs.dlgThayDoiNgayTT.openDialog()
    },
    doCancel: function () {
      this.clearForm()
    },
    doPrint () {
      let selected = this.$refs.dsPhieuTToan.getSelectedRecords()

      if (selected.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu để in.')
        return false
      }

      let ptt = 0

      let postData = {
        kyCuoc: this.params.p_kycuoc,
        dsPhieuId: new Array(1).fill(selected[0].PHIEU_ID),
        kieu: 1,
        lienIn: parseInt(this.inLien)
      }

      if (selected[0].SOTIENTRA !== null && selected[0].SOTIENTRA < 0) { ptt = 1 }// Phiếu thoái trả
      if (this.in_biennhan_tt) {
        postData.kieu = ptt === 0 ? 1 : 2

        this.InPhieuChi(postData)
      } else {
        if (ptt === 1) { // phiếu chi
          // postData.kieu = 2

          // this.InPhieuChi(postData)
          let printData = {
            thuNganId: this.thungan.NHANVIEN_ID,
            loaiTien: 1,
            ngayTT: selected[0].NGAY_TT,
            kyCuoc: selected[0].CHUKYNO1,
            maTT: selected[0].MA_TT,
            maTB: null,
            kieu: 2,
            nhomHTTTId: this.params.p_nhom_httt,
            htttId: selected[0].HTTT_ID
          }
          this.InPhieuThoaiTra(printData)
        } else {
          let data = {
            kyCuoc: this.params.p_kycuoc,
            dsPhieu: new Array(1).fill(selected[0].PHIEU_ID)
          }
          this.InPhieuThu(data)
        }
      }
    },
    doPrint_old: function () {
      // this.$toast.error('Chức năng in đang được phát triển')
      let selected = this.$refs.dsPhieuTToan.getSelectedRecords()

      if (selected.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu để in.')
        return false
      }

      let dsPhieuThu = []
      let dsPhieuChi = []

      // this.dsPhieuCanIn = []
      // this.dsPhieuCanIn.push(this.phieuSelected[0].PHIEU_ID)
      selected.forEach((item) => {
        if (item.SOTIENTRA > 0) { dsPhieuThu.push(item.PHIEU_ID) } else { dsPhieuChi.push(item.PHIEU_ID) }
        // this.dsPhieuCanIn.push(item.PHIEU_ID)
      })

      // this.$refs.refPrintPreview.openDialog();

      const baseUrl = process.env.API

      if (dsPhieuThu.length > 0) {
        const postData = {
          kyCuoc: this.params.p_kycuoc,
          dsPhieu: dsPhieuThu
        }
        this.loading(true)
        this.axios.post(baseUrl + '/web-thuno/api/thu-no/gach-no/in-phieu-thu', postData, {responseType: 'blob'})
          .then((response) => {
            previewPrint(response.data)
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      }
      if (dsPhieuChi.length > 0) {
        const postData = {
          kyCuoc: this.params.p_kycuoc,
          dsPhieuId: dsPhieuChi,
          kieu: 2
        }

        this.loading(true)
        this.axios.post(baseUrl + '/web-thuno/api/thu-no/in-hoa-don/in-bien-lai-thanh-toan-moi', postData, {responseType: 'blob'})
          .then((response) => {
            previewPrint(response.data)
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      }
    },
    InPhieuThu (postData) {
      this.loading(true)
      try {
        const baseUrl = process.env.API
        this.axios.post(baseUrl + '/web-thuno/api/thu-no/gach-no/bien-nhan', postData, {responseType: 'blob'})
          .then((response) => {
            previewPrint(response.data)
          })
          .finally(() => {
            this.loading(false)
          })
      } catch (error) {
        if (error.data !== undefined) { this.$toast.error(error.data.message_detail) } else { this.$toast.error(error) }
      }
    },
    InPhieuChi (postData) {
      this.loading(true)
      try {
        const baseUrl = process.env.API
        this.axios.post(baseUrl + '/web-thuno/api/thu-no/in-hoa-don/in-bien-lai-thanh-toan-moi', postData, {responseType: 'blob'})
          .then((response) => {
            previewPrint(response.data)
          })
          .finally(() => {
            this.loading(false)
          })
      } catch (error) {
        if (error.data !== undefined) { this.$toast.error(error.data.message_detail) } else { this.$toast.error(error) }
      }
    },
    InPhieuThoaiTra (postData) {
      this.loading(true)
      try {
        const baseUrl = process.env.API
        this.axios.post(baseUrl + '/web-thuno/api/thu-no/bao-cao/in-phieu-thoai-tra', postData, {responseType: 'blob'})
          .then((response) => {
            previewPrint(response.data)
          })
          .catch(() => {
            // console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      } catch (error) {
        // console.log(error)
        if (error.data !== undefined) { this.$toast.error(error.data.message_detail) } else { this.$toast.error(error) }
      }
    },
    doPrintInvoice: function () {

    },

    async getDataToExport (postData, index) {
      try {
        const res = await GachNoAPI.getDSPhieuDaGach(this.axios, postData)
        const data = await res.data
        if (data.error_code === 'BSS-00000000' && data.data !== undefined) {
          let excelExportProperties = {
            dataSource: data.data.data,
            fileName: 'dsPhieuThanhToan' + index + '.xlsx'
          }
          this.$refs.dsPhieuTToan.excelExport(excelExportProperties)
          // return data.data
          return data.data
        } else {
          return null
        }
      } catch (error) {
        return null
      }
    },

    async doExportExcel () {
      if (this.dsPhieuTToan.length <= 0) {
        this.$toast.error('Không có phiếu thanh toán để xuất excel.')
        return
      }

      let postData = {...this.searchData}

      // postData.totalRow = 0

      postData.pageSize = 10000
      postData.pageNum = 1
      this.loading(true)
      let res = await this.getDataToExport(postData, 1)
      let promises = []
      for (let i = 1; i < res.totalPages; i++) {
        postData.pageNum = i + 1
        promises.push(this.getDataToExport(postData, i + 1))
      }

      await Promise.all(promises)
      this.loading(false)
    },
    async doSyncVNP () {
      // this.$toast.error('Chức năng đồng bộ VNP đang được phát triển')
      // API syncCCBS

      if (this.dsPhieuTToan.length <= 0) {
        this.$toast.error('Không có dữ liệu phiếu trả đồng bộ')
        return
      }

      let selectedRows = this.$refs.dsPhieuTToan.getSelectedRecords()

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa check chọn phiếu trả để đồng bộ VNP')
        return false
      }

      let r = selectedRows.filter(x => (x.PHIEU_ID_NEO === null && x.TRANGTHAI === 0))
      if (r.length === 0) {
        this.$toast.error('Bạn chưa check chọn phiếu trả để đồng bộ VNP')
        return
      }

      // let var t = ds.Select("CHON='True' AND PHIEU_ID_NEO is null and (TRANGTHAI='-1' or TRANGTHAI='1')");
      let t = selectedRows.filter(x => (x.PHIEU_ID_NEO === null) && (x.TRANGTHAI === -1 || x.TRANGTHAI === 1))

      if (t.length > 0) {
        this.$toast.error('Bạn không được chọn phiếu đã đồng bộ hoặc phiếu không phải dịch vụ di động')
        return
      }

      let user = this.$auth.getUserName()

      // var t_user = ds.Select("CHON='True' AND PHIEU_ID_NEO is not null  and TRANGTHAI<>'-1' and TRANGTHAI<>'1' and USER_GACH<>'" + tt_nd.ma_nd + "'");
      // let tUser = selectedRows.filter(x => (x.PHIEU_ID_NEO === null && x.TRANGTHAI !== -1 && x.TRANGTHAI !== 1 && x.USER_GACH !== user))
      // if (tUser.length > 0) {
      //   this.$toast.error('Bạn không được đồng bộ phiếu của user vnp khác')
      //   return
      // }
      /*

        let postData = {
          "nhomHTTT": null,
          "data": []
        }

        selectedRows.forEach((item) => {
          postData.data.push(
            {
              "phieuIdNeo": item.PHIEU_ID_NEO,
              "trangThai": item.TRANGTHAI,
              "phieuId": item.PHIEU_ID,
              "nguoiCN": user,
              "httt": item.HTTT_ID,
              "kyCuoc": this.params.p_kycuoc,
              "maTT": item.MA_TT
            }
          )
        })

        this.loading(true)

        GachNoAPI.syncCCBS(this.axios, postData)
          .then((response) => {
            this.$toast.success('Đồng bộ VNP thành công.')
          })
          .catch((error) => {
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => {this.loading(false)})

        */

      let promises = []
      selectedRows.forEach((item) => {
        let postData = {
          'kieu': 1,
          'kyCuoc': this.params.p_kycuoc,
          'phieuId': item.PHIEU_ID
        }
        promises.push(this.doSyncCCBS(postData))
      })

      this.loading(true)

      const res = await Promise.all(promises)

      this.loading(false)

      let ok = res.filter(x => x === true)
      let failed = res.filter(x => x === false)

      if (ok.length === promises.length) {
        this.$toast.success('Đã đồng bộ thành công ' + ok.length + ' phiếu.')
        this.doSearch()
      } else { this.$toast.error('Đã phát sinh lỗi khi đồng bộ. Tổng số phiếu lỗi: ' + failed.length) }
    },
    async doSyncCCBS (postData) {
      try {
        const res = await GachNoAPI.syncCCBS(this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000') {
          if (data.data !== undefined && data.data[0].phieuIdNeo !== null) { return true }
        }
        return false
      } catch (error) {
        return false
      } finally {}
    },
    doSync: function () {
      // this.$toast.error('Chức năng đồng bộ đang được phát triển')
      let selectedRows = this.$refs.dsPhieuTToan.getSelectedRecords()

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return false
      }

      let user = this.$auth.getUserName()
      let count = selectedRows.filter(x => x.USER_GACH !== user)
      if (count.length > 0) {
        this.$toast.error('Bạn không được đồng bộ phiếu thanh toán của user ' + count[0].USER_GACH)
        return false
      }

      count = 0
      count = selectedRows.filter(x => x.STATUS === 'ok:')
      if (count.length > 0) {
        let ret = count.map(x => x.MA_TT)
        this.$toast.error('Phiếu thanh toán ' + ret.join(','))
        return false
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn thật sự muốn đồng bộ hóa đơn điện tử ?</div>",
        okButton: { text: 'Đồng ý', click: this.confirmSyncHDDT },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },

    confirmSyncHDDT () {
      DialogObj.hide()

      let selectedRows = this.$refs.dsPhieuTToan.getSelectedRecords()
      let selectedPhieuIDs = selectedRows.map(x => x.PHIEU_ID)

      // call API đồng bộ
      let postData = {
        'kyCuoc': this.params.p_kycuoc,
        'dsPhieu': selectedPhieuIDs
      }

      this.loading(true)

      GachNoAPI.syncHDDT(this.axios, postData)
        .then((res) => {
          this.$toast.success('Đã đồng bộ thành công ' + selectedPhieuIDs.length + ' phiếu.')
        })
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no !== null) { this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no } else { this.params.p_kycuoc = null }
    },
    phieuTTRowChanged (e) {
      // console.log(e)
      if (e === null) {
        if (this.params.p_kieutra === 0) {
          this.dsThangNo = []
          this.pageinfoDSThangNo.page = 0
          this.pageinfoDSThangNo.totalElement = 0
        } else if (this.params.p_kieutra === 1) {
          this.dsKhoanMuc = []
          this.pageinfoDSKhoanMuc.page = 0
          this.pageinfoDSKhoanMuc.totalElement = 0
        } else if (this.params.p_kieutra === 2) {
          this.dsDichVu = []
          this.pageinfoDSDichVu.page = 0
          this.pageinfoDSDichVu.totalElement = 0
        }
        return
      }
      this.selectedRow = e

      let postData = {
        pKyCuoc: this.params.p_kycuoc,
        pPhieuId: e.PHIEU_ID,
        pKieuTra: this.params.p_kieutra,
        pKieuTK: this.params.p_kieu_tk
      }
      this.loadChiTietPhieuTra(postData)
    },

    phieuTTRecordClick (e) {
      if (e === null) {
        if (this.params.p_kieutra === 0) {
          this.dsThangNo = []
          this.pageinfoDSThangNo.page = 0
          this.pageinfoDSThangNo.totalElement = 0
        } else if (this.params.p_kieutra === 1) {
          this.dsKhoanMuc = []
          this.pageinfoDSKhoanMuc.page = 0
          this.pageinfoDSKhoanMuc.totalElement = 0
        } else if (this.params.p_kieutra === 2) {
          this.dsDichVu = []
          this.pageinfoDSDichVu.page = 0
          this.pageinfoDSDichVu.totalElement = 0
        }
        return
      }

      // console.log(e)

      this.selectedRow = e.rowData

      let postData = {
        pKyCuoc: this.params.p_kycuoc,
        pPhieuId: e.rowData.PHIEU_ID,
        pKieuTra: this.params.p_kieutra,
        pKieuTK: this.params.p_kieu_tk
      }
      this.loadChiTietPhieuTra(postData)
    },
    loadChiTietPhieuTra (postData) {
      if (this.params.p_kieutra === 0) {
        this.dsThangNo = []
        this.pageinfoDSThangNo.page = 0
        this.pageinfoDSThangNo.totalElement = 0
      } else if (this.params.p_kieutra === 1) {
        this.dsKhoanMuc = []
        this.pageinfoDSKhoanMuc.page = 0
        this.pageinfoDSKhoanMuc.totalElement = 0
      } else if (this.params.p_kieutra === 2) {
        this.dsDichVu = []
        this.pageinfoDSDichVu.page = 0
        this.pageinfoDSDichVu.totalElement = 0
      }
      // this.loading(true)
      GachNoAPI.getChiTietPhieuDaGach(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              if (this.params.p_kieutra === 0) {
                this.keyDSThangNo++
                this.dsThangNo = res.data.data
                this.pageinfoDSThangNo.totalElement = res.data.data.length
              } else if (this.params.p_kieutra === 1) {
                this.keyDSKhoanMuc++
                this.dsKhoanMuc = res.data.data
                this.pageinfoDSKhoanMuc.totalElement = res.data.data.length
              } else if (this.params.p_kieutra === 2) {
                this.keyDSDichVu++
                this.dsDichVu = res.data.data
                this.pageinfoDSDichVu.totalElement = res.data.data.length
              }
            }
          }
        )
        .finally(() => { this.loading(false) })
    },
    tabGachNoClick (e) {
      this.params.p_kieutra = e
      if (this.selectedRow === null) {
        this.$toast.error('Vui lòng chọn thông tin thanh toán.')
        return false
      }
      let postData = {
        pKyCuoc: this.params.p_kycuoc,
        pPhieuId: this.selectedRow.PHIEU_ID,
        pKieuTra: this.params.p_kieutra,
        pKieuTK: this.params.p_kieu_tk
      }
      this.loadChiTietPhieuTra(postData)
    },
    updateNgayTToanHandler (e) {
      // console.log(e)
      if (e !== undefined && e !== 0) {
        this.doSearch()
      } else {
        this.$toast.error('Đã phát sinh lỗi khi cập nhật ngày thanh toán. Vui lòng thử lại.')
      }
    },
    phieuTTpageChangeHandler (e) {
      this.pageinfoDSPhieuTToan.page = e.pageIndex
      this.pageinfoDSPhieuTToan.maxSize = e.pageSize

      this.searchData.pageNum = this.pageinfoDSPhieuTToan.page + 1
      this.searchData.pageSize = this.pageinfoDSPhieuTToan.maxSize

      // this.doSearch()
      this.fetchDataSearch()
    },
    async deleteProcess (args) {
      let phieuCount = 0
      this.loading(true)
      try {
        let apiBody = {
          pKyCuoc: this.kycuoc,
          pMayHuy: '-',
          pNguoiHuy: this.$auth.getUserName(),
          pPhieuId: 0,
          pPassword: args.password
        }
        for (let item of this.phieuSelected) {
          if (this.kiemTraNgayGachPhieu(0, item)) {
            apiBody.pPhieuId = item.PHIEU_ID
            let result = await this.deletePhieuThu(apiBody)
            if (result) {
              phieuCount++
            } else {
              this.$toast.error('Không thể xóa phiếu thanh toán có mã TT:' + item.MA_TT)
            }
          }
        };

        if (phieuCount === this.phieuSelected.length) {
          this.$toast.success('Xóa phiếu thanh toán thành công!')
        };
        await this.doSearch()
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    kiemTraNgayGachPhieu (actionType, item, value = null) {
      return true
    },
    async deletePhieuThu (apiBody) {
      try {
        let response = await XLAPI.deletePhieuThanhToanMK(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000'
      } catch (error) {
        // console.log(error);
        this.$toast.error(error.data.message_detail)
        return false
      } finally {}
    },

    async changeDateProcess (args) {
      try {
        if (args.ngay_ttoan_moi !== null && args.ngay_ttoan_moi !== undefined && args.ngay_ttoan_moi !== '') {
          let phieuCount = 0
          this.loading(true)
          let apiBody = {
            pKyCuoc: this.kycuoc,
            pPhieuId: 0,
            pNgayThanhToan: args.ngay_ttoan_moi,
            pPassword: args.password
          }
          for (let item of this.phieuSelected) {
            if (!this.kiemTraNgayGachPhieu(1, item, args.ngay_ttoan_moi)) {
              return
            }
            if (item.PHIEU_ID_NEO !== '' && item.PHIEU_ID_NEO > 0) {
              this.$toast.error('Bạn không được sửa ngày thanh toán của mã ' + item.MA_TT + ' do đã gạch trên VNP!')
              return
            }
            apiBody.pPhieuId = item.PHIEU_ID
            let result = await this.updateNgayThanhToan(apiBody)
            if (result) {
              phieuCount++
            }
          }

          if (phieuCount === this.phieuSelected.length) {
            this.$toast.success('Sửa ngày thanh toán thành công!')
          };
          await this.doSearch()
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    async updateNgayThanhToan (apiBody) {
      try {
        let response = await XLAPI.updateNgayThanhToan(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000'
      } catch (error) {
        this.$toast.error('Không thể xóa phiếu ' + apiBody.pPhieuId + ' : ' + error.data.message_detail)
        return false
      } finally {}
    },
    async updateProcess () {
      let phieuCount = 0
      this.loading(true)
      try {
        let apiBody = {
          kyCuoc: this.kycuoc,
          phieuId: 0,
          ghiChu: ''
        }
        for (let item of this.phieuSelected) {
          if (this.kiemTraNgayGachPhieu(0, item)) {
            apiBody.phieuId = item.PHIEU_ID
            apiBody.ghiChu = this.params.p_ghichu
            let result = await this.updateGhiChu(apiBody)
            if (result) {
              phieuCount++
            } else {
              this.$toast.error('Không thể cập nhật phiếu thanh toán có mã TT:' + item.MA_TT)
            }
          }
        };

        if (phieuCount === this.phieuSelected.length) {
          this.$toast.success('Cập nhật phiếu thanh toán thành công!')
          this.$v.$reset()
        };
        await this.doSearch()
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    async updateGhiChu (apiBody) {
      try {
        let response = await XLAPI.updateGhiChuPhieuThanhToan(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000'
      } catch (error) {
        console.log(error)
        return false
      } finally {}
    },

    async deletePhieuThuHandler (flag) {
      if (flag) // xóa thành công
      {
        this.params.f_changed = true
        await this.doSearch()
      }
    },
    async updateDatePhieuThuHandler (flag) {
      if (flag) // xóa thành công
      {
        this.params.f_changed = true
        await this.doSearch()
      }
    },
    thangnoHeaderCellInfo (args) {
      if (args.cell.column.field === 'TRAGOC') {
        new Tooltip(
          {
            content: 'Số tiền khách hàng sẽ trả'
          },
          args.node
        )
      }
    },
    khoannoHeaderCellInfo (args) {
      if (args.cell.column.field === 'TRAGOC') {
        new Tooltip(
          {
            content: 'Số tiền khách hàng sẽ trả'
          },
          args.node
        )
      }
    },
    dichvuHeaderCellInfo (args) {
      if (args.cell.column.field === 'TRAGOC') {
        new Tooltip(
          {
            content: 'Số tiền khách hàng sẽ trả'
          },
          args.node
        )
      }
    },
    getKyCuocHienTai () {
      let ky_ht = getDataFromStorage(THUNO_KEY.KEY_KYCUOC_HT)
      if (ky_ht === null) {
        ky_ht = moment(new Date()).subtract(1, 'months').startOf('month').format('YYYYMM')
        this.p_kyhoadon = moment(new Date()).subtract(1, 'months').startOf('month').toDate()
        setDataToStorage(THUNO_KEY.KEY_KYCUOC_HT, ky_ht)
      }
      return ky_ht
    },
    onBeforeOpen (args) {
      this.$emit('beforeOpen', args)
    },
    onBeforeClose (args) {
      this.$emit('beforeClose', args)
    }
  },
  watch: {
    kycuoc: {
      handler: function (new_value, old_value) {
        this.p_default_kycuoc = new_value
      }
    }
  },
  validations: {
    params: {
      p_ghichu: { required }
    }
  }
})
</script>
<style>
.grid-status--1:before{
  content:'\e718';
}
.grid-status-0:before{
  content:'\e80b';
}
.grid-status-1:before{
  content:'\e718';
}
.grid-status--1{
  color: rgb(202, 133, 4)!important;
  font-weight: bold!important;
}
.grid-status-0{
  color: rgb(202, 133, 4)!important;
  font-weight: bold!important;
}
.grid-status-1{
  color: green!important;
  font-weight: bold!important;
}
</style>
