<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnHoanThanh.visible" :class="{ disabledInput: !tsbtnHoanThanh.enabled }" @click="tsbtnHoanThanh_Click">
          <a> <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Xác nhận </a>
        </li>
        <li v-if="btnXuatExel.visible" :class="{ disabledInput: !btnXuatExel.enabled }" @click="btnXuatExel_Click">
          <a> <span class="icon one-excel"></span> Excel VT </a>
        </li>
        <li v-if="tsbtnXuatExcelTbi.visible" :class="{ disabledInput: !tsbtnXuatExcelTbi.enabled }" @click="tsbtnXuatExcelTbi_Click">
          <a> <span class="icon one-excel"></span> Excel Tbi </a>
        </li>
        <li v-if="tsbtnDangKy.visible" :class="{ disabledInput: !tsbtnDangKy.enabled }" @click="tsbtnDangKy_Click">
          <a> <span class="icon one-clipart-1"></span> DS Đăng ký </a>
        </li>
        <li v-if="tsbtnPhieuTT.visible" :class="{ disabledInput: !tsbtnPhieuTT.enabled }" @click="tsbtnPhieuTT_Click">
          <a> <span class="icon one-clipart-1"></span> DS đăng ký TT </a>
        </li>
        <li>
          <a> <span class="icon one-question"></span>Trợ giúp </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">
                Từ kho
              </div>
              <div class="value" style="display: flex">
                <SelectExt style="flex-grow: 1" :disabled="!cboTuKho.enabled" ref="cboTuKho" v-model="cboTuKho.value" :dataSource="cboTuKho.list" dataTextField="tenKho" dataValueField="khoID" @select="cboTuKho_EditValueChanged" />
                <button class="btn" @click="cboTuKho_ButtonClick">
                  <span class="one-back1"></span>
                </button>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12" v-if="Visible.dtpNgayDK">
            <div class="info-row">
              <div class="key w90">Đến ngày <span class="red">*</span></div>
              <div class="value">
                <div class="input-icon-right">
                  <ejs-datetimepicker locale="vi" v-model="dtpNgayDK.value" :format="'dd/MM/yyyy HH:mm:ss'" :showClearButton="false" :readonly="ReadOnly.dtpNgayDK" :allowEdit="false"> </ejs-datetimepicker>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12 d-flex d-flex justify-content-start align-items-center" v-if="ckbAllTbi.visible">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="ckbAllTbi.value" />
              <span class="name">Lấy tất cả thiết bị</span>
            </div>
            <!-- <div class="input-icon-right f20">
              <input type="checkbox" class="check" v-model="ckbAllTbi.value">
            </div>
            <div class="mr-3">
              <span class="name">Lấy tất cả thiết bị</span>
              Lấy tất cả thiết bị
            </div> -->
          </div>

          <div class="col-sm-3 col-12" v-if="Visible.popupFilter">
            <div class="info-row">
              <div class="key w30"></div>
              <div class="value">
                <div class="vattu_box" id="drd">
                  <div class="input-group">
                    <div class="li input-text">
                      <div class="select-custom">
                        <input value="Tiêu chí tìm kiếm bổ sung" class="form-control" @click="isShow = true" />
                      </div>
                    </div>
                  </div>
                  <!-- <div class="select-custom" data-toggle="dropdown" >
                    <div class="form-control btn padt5 padb5 tleft" @click="isShow = true"></div>>Tiêu chí tìm kiếm bổ sung</div>
                  </div> -->
                  <div class="vattu_table" style="width: 600px;" v-if="isShow">
                    <div class="popup-header">
                      <div class="title">&nbsp;</div>
                      <div class="close -ap icon-close" @click="isShow = !isShow"></div>
                    </div>
                    <div class="popup-body">
                      <div class="box-form">
                        <div class="legend-title">
                          <div class="pull-left">Điều kiện lọc</div>
                          <div class="pull-right">
                            <button class="btn btn-second" @click="btnFilter_Click"><span class="one-search"></span> Tìm kiếm</button>
                          </div>
                          <div class="clearfix"></div>
                        </div>
                        <div class="row">
                          <div class="col-sm-6 col-12">
                            <div class="info-row">
                              <div class="key w110">
                                <div class="check-action">
                                  <input type="checkbox" class="check" v-model="ckbLinhVuc_TK.value" @change="ckbLinhVuc_TK_CheckedChanged" />
                                  <span class="name">Lĩnh vực</span>
                                </div>
                              </div>
                              <div class="value">
                                <div class="select-custom">
                                  <ejs-dropdownlist class="form-control" locale="vi" v-model="params.p_LinhVuc_TK" :dataSource="cboLinhVuc_TK" :fields="{ value: 'linhVucId', text: 'linhVuc' }" :allowFiltering="true" :readonly="!enabled.cboLinhVuc_TK" @change="cboLinhVuc_TK_EditValueChanged">
                                  </ejs-dropdownlist>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="col-sm-6 col-12">
                            <div class="info-row">
                              <div class="key w110">
                                <div class="pl-3">
                                  <span class="name">Hệ thống</span>
                                </div>
                              </div>
                              <div class="value">
                                <div class="select-custom">
                                  <ejs-dropdownlist class="form-control" locale="vi" v-model="params.p_HeThong_TK" :dataSource="cboHeThong_TK" :fields="{ value: 'heThongId', text: 'tenHeThong' }" :allowFiltering="true" :readonly="!enabled.cboHeThong_TK" @change="cboHeThong_TK_EditValueChanged">
                                  </ejs-dropdownlist>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-6 col-12">
                            <div class="info-row">
                              <div class="key w110">
                                <div class="check-action">
                                  <input type="checkbox" class="check" v-model="params.p_ckbHangSX_TK" @change="ckbHangSX_TK_CheckedChanged" />
                                  <span class="name">Hãng SX</span>
                                </div>
                              </div>
                              <div class="value">
                                <SelectExt ref="cboHangSX_TK" v-model="cboHangSX_TK.value" :dataSource="cboHangSX_TK.list" dataTextField="tenHangSx" dataValueField="hangSxId" @select="cboHangSX_TK_EditValueChanged" />
                              </div>
                            </div>
                          </div>
                          <div class="col-sm-6 col-12">
                            <div class="info-row">
                              <div class="key w110">
                                <div class="pl-3">
                                  <span class="name">Dòng thiết bị</span>
                                  u
                                </div>
                                <div class="value">
                                  <div class="select-custom">
                                    <ejs-dropdownlist class="form-control" locale="vi" v-model="params.p_cboDongTbi_TK" :dataSource="cboDongTbi_TK" :fields="{ value: 'dongTbiId', text: 'tenDongTbi' }" :allowFiltering="true" :readonly="!enabled.cboDongTbi_TK"> </ejs-dropdownlist>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row col-md-12 p-0 m-0">
          <div :class="Visible.splitContainer2.Panel2Collapsed ? 'col-md-12' : 'col-md-6'">
            <div class="nav tabs tab-over mart10">
              <a href="#tabA" class="active" data-toggle="tab">Danh sách vật tư</a>
              <a href="#tabB" data-toggle="tab" v-if="xtraTabPage2.visible">Danh sách thiết bị</a>
            </div>
            <div class="tab-content">
              <div class="tab-pane active" id="tabA">
                <div class="table-content">
                  <DataGrid
                    panelDataHeight="400px"
                    ref="gridVatTu"
                    :columns="gridVatTuHeader"
                    :dataSource="gridVatTu.list"
                    :showColumnCheckbox="false"
                    :showFilter="true"
                    :allowEditing="true"
                    :allowPaging="true"
                    :enablePagingServer="false"
                    :editSettings="{ allowEditing: true, mode: 'Batch' }"
                    @keyPressed="grid_keyPressed"
                    @cellSaved="gridViewVatTu_CellValueChanged"
                    dataKeyField="ID_GEN"
                    :enabledSelectFirstRow="false"
                    :selectionSettings="{ enableToggle: false, checkboxOnly: true, persistSelection: true }"
                    :aggregates="aggregates_gridVatTu"
                  />
                  <!-- @rowSelected="gridVatTuSelectedItemsChanged" -->
                  <!-- Đức đổi qua selectedItemsChanged để tránh lỗi các trang sau -->
                  <!-- @rowDeselected='gridVatTuSelectedItemsChanged' -->
                </div>

                <div class="legend-title mart10">
                  <div class="pull-left">
                    Danh sách vật tư xuất kho
                  </div>
                  <div class="pull-right">
                    <button class="btn btn-second pad4 lh16">
                      <span class="-ap icon-close"></span>
                    </button>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <div class="table-content">
                  <DataGrid panelDataHeight="400px" ref="gridXuat" :columns="gridXuat.header" :dataSource="gridXuat.list" :showColumnCheckbox="false" :showFilter="true" :allowPaging="false" :enablePagingServer="false" :editSettings="{ allowEditing: true, mode: 'Batch' }" :aggregates="aggregates_gridXuat" />
                </div>
              </div>
              <div class="tab-pane" id="tabB" v-if="xtraTabPage2.visible">
                <DataGrid :columns="gridThietBi_Columns" :dataSource="gridThietBi" ref="gridViewThietBi" :showColumnCheckbox="false" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :editSettings="{ allowEditing: true, mode: 'Batch' }" :enabledSelectFirstRow="false" />
                <div class="mart15 box-form" v-show="!Visible.ShowDKTT">
                  <div class="legend-title">
                    <div class="pull-left">
                      Danh sách nhu cầu đăng ký
                    </div>
                    <div class="pull-right">
                      <button class="btn btn-second pad4 lh16" @click="Visible.splitContainer2.Panel2Collapsed = !Visible.splitContainer2.Panel2Collapsed">
                        <span class="nc-icon-outline arrows-1_tail-right"></span>
                      </button>
                    </div>
                    <div class="clearfix"></div>
                  </div>

                  <div>
                    <DataGrid
                      :columns="grcDSDangKy_Columns"
                      :dataSource="grcDSDangKy"
                      ref="grvDSDangKy"
                      :showColumnCheckbox="false"
                      :showFilter="true"
                      :editSettings="{ allowEditing: true, mode: 'Batch' }"
                      :enabledSelectFirstRow="false"
                      :allowPaging="true"
                      :enablePagingServer="false"
                      @selectedRowChanged="grvDSDangKy_FocusedRowChanged"
                    />
                  </div>
                  <div class="mart15 legend-title">
                    Danh sách vật tư đăng ký
                  </div>

                  <DataGrid :columns="grcDSVatTu_DaDK_Columns" :dataSource="grcDSVatTu_DaDK" ref="grvDSVatTu_DaDK" :showColumnCheckbox="false" :showFilter="true" :editSettings="{ allowEditing: true, mode: 'Batch' }" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" />
                </div>

                <div class="mart15 box-form" v-show="Visible.ShowDKTT">
                  <div class="legend-title">
                    <div class="pull-left">
                      Danh sách nhu cầu đăng ký tổ
                    </div>
                    <div class="pull-right">
                      <button class="btn btn-second pad4 lh16" @click="Visible.splitContainer2.Panel2Collapsed = !Visible.splitContainer2.Panel2Collapsed">
                        <span class="nc-icon-outline arrows-1_tail-right"></span>
                      </button>
                    </div>
                    <div class="clearfix"></div>
                  </div>

                  <div>
                    <DataGrid
                      :columns="grcDK_TT_Columns"
                      :dataSource="grcDK_TT"
                      ref="grvDK_TT"
                      :showColumnCheckbox="false"
                      :showFilter="true"
                      :editSettings="{ allowEditing: true, mode: 'Batch' }"
                      :enabledSelectFirstRow="false"
                      :allowPaging="true"
                      :enablePagingServer="false"
                      @selectedRowChanged="grvDSDangKy_FocusedRowChanged"
                    />
                  </div>
                  <div class="mart15 legend-title">
                    Danh sách chi tiết vật tư đăng ký
                  </div>

                  <DataGrid :columns="grcCT_DK_Columns" :dataSource="grcCT_DK" ref="grvCT_DK" :showColumnCheckbox="false" :showFilter="true" :editSettings="{ allowEditing: true, mode: 'Batch' }" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue'
import _ from 'lodash'
import moment from 'moment'
import { GridVatTuCheckboxTemplate, GridXuatBtnXoaTemplate, GridXuatCountTemplate, GridXuatSumTemplate } from './components'
import api from './api'
import XLSX from 'xlsx'
import DateTimeHelper from '../../helpers/dateHelper'
import { convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault } from '../../helpers'
import { FORM_CONFIG } from '../../const'

export default {
  components: {},
  emits: ['onBtnXacNhanClicked'],
  props: {
    vkho_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    tt: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    kieu: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    dtLuuVT: {
      type: [Array],
      defaul: () => {
        return []
      },
      require: false
    },
    vngay_ct: {
      type: [String],
      defaul: null,
      require: false
    },
    vchungtu_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vkho_den_id: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },

  provide: {},
  computed: {
    gridVatTuHeader() {
      return this.gridVatTu.header.filter((e) => {
        if (e.fieldName == 'SO_HD') return ['HNI', 'HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'GHICHU') return ['HNI', 'HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'DIENGIAI') return ['HNI'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'NGUOI_QL') return ['HNI'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'CHATLUONG') return ['HNI'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'ID_NHAPGOC') return ['HNI'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'NOIDUNG') return ['HNI'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'DS_CTR') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'IDG') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'SL_C_DD') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'SL_TONTHUC') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'TRANGTHAI') return ['HNI'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'MAKHOTD_TEXT') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'LOAIKHO_TEXT') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'DONGIA') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh()) || this.IS_VIEWING_COL_DONGIA
        if (e.fieldName == 'NGAYNHAP') return ['HCM', 'NET', 'LAN'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'CHATLUONG') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'HANGSX_ID') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'DONGTBI_ID') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'HETHONG_ID') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'LINHVUC_ID') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'PART_NUMBER') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        if (e.fieldName == 'MOTA_CT') return ['HCM', 'NET'].includes(this.$root.token.getMaTinh())
        return true
      })
    },
    gridVatTuConLai() {
      return this.gridVatTu.list
    },
    aggregates_gridXuat() {
      return [
        { field: 'SOLUONG', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'LOHANG', type: 'Count', format: 'N0', footerTemplate: this.countTemplate }
      ]
    },
    aggregates_gridVatTu() {
      return [{ field: 'LOHANG', type: 'Count', format: 'N0', footerTemplate: this.countTemplate }]
    }
  },
  data() {
    return {
      localLoading: 0,
      header: {
        title: 'TRA CỨU THIẾT BỊ TỒN KHO',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      xtraTabPage2: { visible: true },
      tsbtnHoanThanh: { visible: true, enabled: true },
      btnXuatExel: { visible: true, enabled: true },
      tsbtnXuatExcelTbi: { visible: true, enabled: true },
      tsbDanhSach: { visible: true, enabled: true },
      tsbtnDangKy: { visible: true, enabled: true },
      tsbtnPhieuTT: { visible: true, enabled: true },
      cboTuKho: { visible: true, enabled: true, list: [], value: null },
      dtpNgayDK: { visible: true, enabled: true, list: [], value: null },
      ckbAllTbi: { visible: true, enabled: true, list: [], value: null },
      popupFilter: { visible: true, enabled: true, list: [], value: null },
      label1: { visible: true, enabled: true, value: null },

      ckbLinhVuc_TK: { visible: true, enabled: true, value: null },
      cboLinhVuc_TK: { visible: true, enabled: true, list: [], value: null },
      ckbHangSX_TK: { visible: true, enabled: true, value: null },
      cboHangSX_TK: { visible: true, enabled: true, list: [], value: null },
      cboHeThong_TK: { visible: true, enabled: true, list: [], value: null },
      cboHeThong_TK: { visible: true, enabled: true, list: [], value: null },
      btnFilter: { visible: true, enabled: true },

      colSHD: { visible: true, editable: true },
      colGC: { visible: true, editable: true },
      colDienGiai: { visible: true, editable: true },
      //colMaDA.visible = false;
      colNguoiQL: { visible: true, editable: true },
      colCL: { visible: true, editable: true },
      colIDNG: { visible: true, editable: true },
      colNoiDUng: { visible: true, editable: true },
      colCongTrinh: { visible: true, editable: true },
      colIDG: { visible: true, editable: true },
      colSLDD: { visible: true, editable: true },
      colMaKhoTD: { visible: true, editable: true },
      colLoaiKho: { visible: true, editable: true },
      colDonGia: { visible: true, editable: true },
      dtpNgayDK: { visible: true, enabled: true, value: null },
      gridVatTu: {
        visible: true,
        enabled: true,
        header: [
          // headerTemplate: GridVatTuCheckboxTemplate(this, 'header'),
          { fieldName: 'ID_GEN', headerText: '', visible: false, isPrimaryKey: true },
          { fieldName: 'CHECKBOX', headerText: '', headerTemplate: GridVatTuCheckboxTemplate(this, 'header'), width: '60px', template: GridVatTuCheckboxTemplate(this, 'row'), freeze: 'Left', allowFiltering: false, allowSorting: false, textAlign: 'Center' },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
          { fieldName: 'SL_TON', headerText: 'SL tồn', format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'SL_TONTHUC', headerText: 'SL tồn thực', format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'SL_C_DD', headerText: 'SL_DD', format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'SL_XUAT', headerText: 'SL xuất', editType: 'numericedit', allowEditing: true, format: 'N3', type: 'number', allowEditing: true, editType: 'numericedit', allowFiltering: true },
          { fieldName: 'MA_DUAN', headerText: 'Dự án', allowFiltering: true },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
          { fieldName: 'SL_TREO', headerText: 'SL treo', format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'SOLUONG', headerText: 'Số lượng', format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'LOAIKHO_TEXT', headerText: 'Loại kho', allowFiltering: true, visible: true },
          { fieldName: 'MAKHOTD_TEXT', headerText: 'Mã Kho TD', allowFiltering: true, visible: true },
          { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị', allowFiltering: true },
          { fieldName: 'SOTHANG_PB', headerText: 'Số tháng phân bổ', allowFiltering: true },
          { fieldName: 'THANHTIEN', headerText: 'Thành tiền', format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'VAT', headerText: 'VAT', format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'NGAYNHAP', headerText: 'Ngày nhập', allowFiltering: true },
          
          { fieldName: 'MA_HD_PSIS', headerText: 'Mã HD PSIS', allowFiltering: true },
          { fieldName: 'MA_DH_PSIS', headerText: 'Mã DH PSIS', allowFiltering: true },
          { fieldName: 'MA_GH', headerText: 'Mã GH', allowFiltering: true },

          // list cột chỉ hiển thị cho HCM,HNI,NET

          { fieldName: 'SL_TAMUNG', headerText: 'SL tạm ứng', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          { fieldName: 'CHATLUONG', headerText: 'Chất lượng', allowFiltering: true, visible: true },
          { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, visible: true },
          { fieldName: 'SO_HD', headerText: 'Số HĐ', allowFiltering: true, visible: true },
          { fieldName: 'NOIDUNG', headerText: 'Nội dung', allowFiltering: true, visible: true },
          { fieldName: 'DIENGIAI', headerText: 'Diễn giải', allowFiltering: true, visible: true },
          { fieldName: 'ID_NHAPGOC', headerText: 'Id nhập gốc', allowFiltering: true, visible: true },
          { fieldName: 'NGUOI_QL', headerText: 'Người QL', allowFiltering: true, visible: true },
          { fieldName: 'TY_LE', headerText: 'Tỷ lệ', allowFiltering: true, visible: true },
          { fieldName: 'IDG', headerText: 'IDG', allowFiltering: true, visible: true },
          // { fieldName: 'LOAIKHO', headerText: 'Loại kho',  allowFiltering: true ,visible:false},
          // { fieldName: 'MAKHOTD', headerText: 'Mã Kho TD',  allowFiltering: true ,visible:false},

          { fieldName: 'NGUON_GOC', headerText: 'Nguồn gốc', allowFiltering: true, visible: true },
          { fieldName: 'SL_TONTHUC', headerText: 'SL tồn thực', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          { fieldName: 'MATHETS', headerText: 'MATHETS', allowFiltering: true, visible: true },
          { fieldName: 'MATHUNG', headerText: 'Mã thùng', allowFiltering: true, visible: true },
          { fieldName: 'DS_CTR', headerText: 'Ds công trình', allowFiltering: true, visible: true },
          { fieldName: 'SL_C_DD', headerText: 'SL DD', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          { fieldName: 'DS_CTR', headerText: 'Ds công trình', allowFiltering: true, visible: true },
          { fieldName: 'TRANGTHAI', headerText: 'Trạng thái VT', allowFiltering: true, visible: true },
          { fieldName: 'HANGSX_ID', headerText: 'Hãng SX', allowFiltering: true, visible: true },
          { fieldName: 'DONGTBI_ID', headerText: 'Dòng TB', allowFiltering: true, visible: true },
          { fieldName: 'HETHONG_ID', headerText: 'Hệ thống', allowFiltering: true, visible: true },
          { fieldName: 'LINHVUC_ID', headerText: 'Lĩnh vực', allowFiltering: true, visible: true },
          { fieldName: 'PART_NUMBER', headerText: 'Part Number', allowFiltering: true, visible: true },
          { fieldName: 'MOTA_CT', headerText: 'Mô tả ct', allowFiltering: true, visible: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridXuat: {
        visible: true,
        enabled: true,
        header: [
          { width: 80, fieldName: 'BTN_XOA', headerText: 'Xóa', textAlign: 'Left', allowFiltering: true, freeze: 'Left', template: GridXuatBtnXoaTemplate(this) },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true, freeze: 'Left', visible: true },
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true, visible: true, freeze: 'Left' },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true, visible: true },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true, visible: true },
          { fieldName: 'SOLUONG', headerText: 'SL tồn', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          { fieldName: 'SL_XUAT', headerText: 'SL xuất', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          { fieldName: 'THANHTIEN', headerText: 'Thành tiền', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      grcDSDangKy: {
        visible: true,
        enabled: true,
        header: [],
        list: [],
        selected: [],
        value: null
      },
      grcDSVatTu_DaDK: {
        visible: true,
        enabled: true,
        header: [],
        list: [],
        selected: [],
        value: null
      },
      grcDK_TT: {
        visible: true,
        enabled: true,
        header: [],
        list: [],
        selected: [],
        value: null
      },
      grcCT_DK: {
        visible: true,
        enabled: true,
        header: [],
        list: [],
        selected: [],
        value: null
      },
      gridThietBi: {
        visible: true,
        enabled: true,
        header: [],
        list: [],
        selected: [],
        value: null
      },
      grcTT: {
        visible: true,
        enabled: true,
        header: [],
        list: [],
        selected: [],
        value: null
      },
      ///
      chungtu_id: 0,
      ctct_id: 0,

      dtVT: [],
      dtVT_Xuat: [],
      dtVatTu: [],
      dtThietBi: [],

      DSVatTuChecked: [],
      XacNhan: false,
      dt: [],
      checkAll: false,
      DS_Xuat: [],
      dsQuyen: [],
      ds_dangky: 0,
      coDS: false,

      IS_VIEWING_COL_DONGIA: false,
      TAMUNG: { visible: false },

      Visible: {
        tsbtnXuatExcelTbi: true,
        popupFilter: false,
        xtraTabPage2: true,
        label1: false,
        dtpNgayDK: false,
        tsbDanhSach: false,
        ckbAllTbi: true,
        splitContainer2: {
          Panel2Collapsed: true,
          ShowDKTT: false
        },
        colSlTamUng: false
      },
      ReadOnly: {
        dtpNgayDK: false
      },
      enabled: {
        cboTuKho: true,
        tsbDanhSach: true,
        grvDSVatTu_DaDK: {
          SL_XUAT: true,
          BODUYET: true
        },
        cboLinhVuc_TK: false,
        cboHeThong_TK: false,
        ckbHangSX_TK: false,
        cboDongTbi_TK: false
      },
      params: {
        p_TuKho: '',
        p_dtpNgayDK: new Date(),
        p_ckbAllTbi: false,

        p_ckbLinhVuc_TK: false,
        p_ckbHangSX_TK: false,
        p_LinhVuc_TK: '',
        p_HeThong_TK: '',
        p_HangSX_TK: '',
        p_DongTbi_TK: ''
      },
      gridThietBi: [],
      grcDSDangKy: [],
      grcDSVatTu_DaDK: [],

      grcDK_TT: [],
      grcCT_DK: [],

      repoLoaiKho: [],

      isShow: false,

      mapping_gridVatTuCol: {
        colSLDD: 'SL_C_DD',
        colChatluong_id: 'CHATLUONG_ID',
        colCL: 'CHATLUONG',
        colSHD: 'SO_HD',
        colMaDA: 'MA_DUAN',
        colIDNG: 'ID_NHAPGOC',
        colNguoiQL: 'NGUOI_QL',
        colDienGiai: 'DIENGIAI',
        colNoiDUng: 'NOIDUNG',
        colGC: 'GHICHU',
        colCongTrinh: 'DS_CTR',
        colIDG: 'IDG',
        colTrangThaiVT: 'TRANGTHAI',
        colTamUng: 'SL_TAMUNG'
      },
      gridThietBi_Columns: [
        { fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'MA_VT', headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'MA_TBI', headerText: 'Mã thiết bị', textAlign: 'Left', allowFiltering: true },

        { fieldName: 'SERIAL', headerText: 'Serial', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'SL_DD', headerText: 'Số lượng DD', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: false },

        { fieldName: 'SL_KD', headerText: 'SL Khả dụng', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: false },
        { fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'THANHTIEN', headerText: 'Thành tiền', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'SERIAL_GP', headerText: 'Serial Gp', textAlign: 'Left', allowFiltering: true, freeze: 'Right' }
      ],

      grcDSDangKy_Columns: [
        { fieldName: 'CHON', headerText: '', type: 'checkbox', textAlign: 'Center', allowFiltering: true, width: '60', freeze: 'Left' },
        { fieldName: 'SO_CV', headerText: 'Số công văn', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'TEN_TG', headerText: 'Thời gian ĐK', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'MA_CT', headerText: 'Dự án', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'NGAY_DK', headerText: 'Ngày yêu cầu', textAlign: 'Right', allowFiltering: true },

        { fieldName: 'DOT', headerText: 'Đợt', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'CONLAI', headerText: 'Số tháng ĐK', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'TEN_DV', headerText: 'Đơn vị ĐK', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'SODANGKY', headerText: 'SL Đăng ký', textAlign: 'Left', allowFiltering: true },

        { fieldName: 'NHOM', headerText: 'Nhóm', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'NGAYDUYET', headerText: 'Ngày duyệt', textAlign: 'Right', allowFiltering: true },
        { fieldName: 'NGUOI_CN', headerText: 'Người duyệt', textAlign: 'Left', allowFiltering: true }
      ],

      grcDSVatTu_DaDK_Columns: [
        { fieldName: 'MA_VT', headerText: 'Mã VT', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'MUCDICH', headerText: 'Mục đích', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true },

        { fieldName: 'SL_TON_DK', headerText: 'SL Tồn ĐK', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'SL_XUAT', headerText: 'SL Xuất', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'SL_DUYET', headerText: 'SL Duyệt', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'BODUYET', headerText: 'Bỏ duyệt', textAlign: 'Left', allowFiltering: true }
      ],

      grcDK_TT_Columns: [
        { fieldName: 'CHON', headerText: '', type: 'checkbox', textAlign: 'Left', allowFiltering: true, width: '60' },
        { fieldName: 'SO_CV', headerText: 'Số công văn', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'TEN_TG', headerText: 'Thời gian ĐK', textAlign: 'Left', allowFiltering: true },
        // {fieldName: 'MA_CT', headerText: 'Dự án', textAlign: 'Left', allowFiltering: true, },
        { fieldName: 'NGAY_DK', headerText: 'Ngày yêu cầu', textAlign: 'Right', allowFiltering: true },

        { fieldName: 'DOT', headerText: 'Đợt', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'CONLAI', headerText: 'Số tháng ĐK', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'TEN_DV', headerText: 'Đơn vị ĐK', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'SODANGKY', headerText: 'SL Đăng ký', textAlign: 'Left', allowFiltering: true },

        { fieldName: 'NHOM', headerText: 'Nhóm', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'NGAYDUYET', headerText: 'Ngày duyệt', textAlign: 'Right', allowFiltering: true },
        { fieldName: 'NGUOI_CN', headerText: 'Người duyệt', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'btnTraPhieu', headerText: 'Trả phiếu', textAlign: 'Left', allowFiltering: true, freeze: 'Right' }
      ],

      grcCT_DK_Columns: [
        { fieldName: 'MA_VT', headerText: 'Mã VT', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'MUCDICH', headerText: 'Mục đích', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true },

        { fieldName: 'SL_TON_DK', headerText: 'SL Tồn ĐK', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'SL_XUAT', headerText: 'SL Xuất', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        { fieldName: 'SL_DUYET', headerText: 'SL Duyệt', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true }
      ],

      gridVatTuPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      indexArray: [],
      functionName: '', // quý ,bổ sung khi 1 component có nhiều func gọi

      // Tiền Giang, Thái Nguyên
      tinh: ['TGG', 'TNN'],
      DS_QUYEN_VT: {
        HOANTHIEN_CHUNGTU: 1794
      },

      sumTemplate: function() {
        return {
          template: Vue.component('sumTemplate', {
            template: `
                <div class="input-group-prepend w-100">
                  <div class="input-group-text  w-100 d-flex justify-content-between">
                    <div>Tổng:</div>
                    <div class="tright pr-2">{{data.Sum}}</div>
                  </div>
                </div>
              `,
            // <input type="text" class="form-control right" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;"/>
            data: function() {
              return { data: { data: {} } }
            }
          })
        }
      },
      countTemplate: function() {
        return {
          template: Vue.component('countTemplate', {
            template: `
            <div class="input-group-prepend w-100">
              <div class="input-group-text  w-100 d-flex justify-content-between">
                <div>Đếm:</div>
                <div class="tright pr-2">{{data.Count}}</div>
              </div>
            </div>`,
            // <input type="text" class="form-control right" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />
            data: function() {
              return { data: { data: {} } }
            }
          })
        }
      }
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data != null ? result.data.data : null
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
    async Load_quyen() {
      this.dsQuyen = await this.callApiWrapper(api.ds_quyen_nd, { nguoidung_id: await this.$root.token.getNguoiDungID() })
    },
    async ChinhFont() {
      //lam sau
    },
    async frmVatTuTrongKho_Load() {
      this.colSHD.visible = false
      this.colGC.visible = false
      this.colDienGiai.visible = false
      //colMaDA.visible = false;
      this.colNguoiQL.visible = false
      this.colCL.visible = false
      this.colIDNG.visible = false
      this.colNoiDUng.visible = false
      this.colCongTrinh.visible = false
      this.colIDG.visible = false
      this.colSLDD.visible = false
      if (this.tinh_hientai == 'HNI') {
        // gridViewVatTu.OptionsView.ColumnAutoWidth = false
        this.colSHD.visible = true
        this.colGC.visible = true
        this.colDienGiai.visible = true
        //colMaDA.visible = true;
        this.colNguoiQL.visible = true
        this.colCL.visible = true
        this.colIDNG.visible = true
        this.colNoiDUng.visible = true
        this.colTrangThaiVT.visible = true
        this.colTrangThaiVTBottom.visible = true
        this.colDSTU.visible = true
        // this.colDSTU.VisibleIndex = 20
      } else if (this.tinh_hientai == 'HCM' || this.tinh_hientai == 'NET') {
        // gridViewVatTu.OptionsView.ColumnAutoWidth = false
        this.colMaKhoTD.visible = true
        this.colLoaiKho.visible = true
        this.colCongTrinh.visible = true
        this.colGC.visible = true
        this.colSHD.visible = true
        //colMaDA.visible = true;
        this.colIDG.visible = true
        this.colSLTonThuc.visible = true
        this.colSLTonThuc.VisibleIndex = 6
        this.colSLDD.visible = true
        this.colSLDD.VisibleIndex = 7
        // this.colIDG.Fixed = DevExpress.XtraGrid[0].FixedStyle.Right
        // this.colIDG.VisibleIndex = 0
        this.colDonGia.visible = true
        // this.colDonGia.VisibleIndex = 8
        this.colNguonGoc.visible = true
        this.colNgayNhap.visible = true

        this.colChatluong_id.visible = true
        this.colHangSX.visible = true
        this.colDongtbi_id.visible = true
        this.colHethong_id.visible = true
        this.colLinhVuc.visible = true
        this.colPartN.visible = true
        this.colMotaCT.visible = true

        this.popupFilter.visible = kieu == 0
      } else if (this.tinh_hientai == 'LAN') {
        this.colNgayNhap.visible = true
      } else {
        this.colMaKhoTD.visible = false
        this.colLoaiKho.visible = false
      }

      if (this.IS_VIEWING_COL_DONGIA) {
        this.colDonGia.visible = true
        // this.colDonGia.VisibleIndex = 8
      }

      await this.Load_quyen()
      if (this.kieu == 1) {
        // this.cboTuKho.Properties.LockEvents();
      }
      if (this.kieu == 0) this.dtpNgayDK.value = DateTimeHelper.NgayCN()
      this.cboTuKho.list = await this.callApiWrapper(api.ds_kho_nhan_vien, { nhanvien_id: await this.$root.token.getNhanVienID() })
      this.cboTuKho.value = this.cboTuKho.list.length ? this.cboTuKho.list[0].khoID : ''

      if (this.kieu == 1) {
        this.cboTuKho.value = null
        // cboTuKho.Properties.UnLockEvents();
        this.cboTuKho.value = this.vkho_id
        this.cboTuKho.enabled = false
        this.xtraTabPage2.visible = false
        this.label1.visible = false
        this.dtpNgayDK.visible = false
        this.tsbDanhSach.visible = true
      } else if (kieu == 2) {
        this.cboTuKho.list = await this.callApiWrapper(api.ds_kho_nha_cung_cap, { nhanvien_id: await this.$root.token.getNhanVienID() })
        this.cboTuKho.value = this.vkho_id
        this.cboTuKho.enabled = false
        this.ckbAllTbi.visible = false
        this.xtraTabPage2.visible = false
        this.label1.visible = false
        this.dtpNgayDK.visible = false
        this.tsbDanhSach.visible = true
      } else {
        this.dtpNgayDK.value = DateTimeHelper.NgayCN()
        if (this.cboTuKho.list == null) this.dtThietBi = await this.callApiWrapper(api.ds_thietbi_tonkho, { kho_id: this.vkho_id })

        this.gridThietBi.list = this.dtThietBi
        this.ckbAllTbi.visible = false
        this.label1.visible = true
        this.dtpNgayDK.visible = true
        this.dtpNgayDK.enabled = false
        this.tsbDanhSach.visible = false
      }
      await this.cboTuKho_EditValueChanged()
      await this.ds_vt_xuat()

      this.TAMUNG.visible = this.tt == 1 ? true : false

      //dtVatTu = ChungTu.LAY_DS_VATTU_TONKHO(vkho_id).Tables[0];
      //gridVatTu.DataSource = dtVatTu;

      // #region HANG_SX - LV - DONGTBI - he thong
      // string sStr_hsx;
      // sStr_hsx = @"   select -1 hangsx_id ,'' ten_hangsx from dual union all
      //                     select hangsx_id,ma_hangsx || ' - '|| ten_hangsx TEN_HANGSX from {?DB12}.hang_sx ";

      // let dt_hsx = tdan.GET_DATA_SQL(sStr_hsx);
      // bangts.HT_DS_GRIDLOOKUP(ckbHangSX_TK.list, dt_hsx.Select("HANGSX_ID<>-1").Length > 0 ? dt_hsx.Select("HANGSX_ID<>-1").CopyToDataTable() : dt_hsx, "TEN_HANGSX", "HANGSX_ID", true, 2, 400);

      // sStr_hsx = @" select linhvuc_id , ma_lv ||' - '||linhvuc linhvuc  from {?DB12}.linhvuc ";
      // let dt_lv = tdan.GET_DATA_SQL(sStr_hsx);
      // bangts.HT_DS_GRIDLOOKUP(cboLinhVuc_TK, dt_lv, "LINHVUC", "LINHVUC_ID", true, 2, 400);

      this.ckbHangSX_TK.list = await this.callApiWrapper(api.ds_hangsx, {})
      this.ckbHangSX_TK.value = this.ckbHangSX_TK.list.length ? this.ckbHangSX_TK.list[0].hangSxId : ''
      this.cboLinhVuc_TK = await this.callApiWrapper(api.ds_linhvuc, {})
      this.params.p_LinhVuc_TK = this.cboLinhVuc_TK.length ? this.cboLinhVuc_TK[0].heThongId : ''
    },

    async func_IS_VIEWING_COL_DONGIA() {
      let s = await this.callApiWrapper(api.ds_ten_tham_so, { maThamSo: 'QLVT_VIEWTON_COLDG' })
      if (s == '1') {
        this.IS_VIEWING_COL_DONGIA = true
      } else {
        this.IS_VIEWING_COL_DONGIA = false
      }
    },
    async ds_vt_xuat() {
      console.log('ds_vt_xuat')
      console.log(this.dtLuuVT)
      console.log(this.dtVatTu)
      let _dt = []
      let _dt_2 = []
      _dt = _.clone(this.DS_Xuat)
      _dt_2 = _.clone(this.DS_Xuat)
      console.log(_dt)
      console.log(_dt_2)
      for (let i = 0; i < this.dtLuuVT.length; i++) {
        let _k = 0
        let _i = this.dtLuuVT[i]
        for (let k = 0; k < this.dtVatTu.length; k++) {
          let _j = this.dtVatTu[k]
          if (_i.VATTU_ID == _j.VATTU_ID && _i.LOHANG == _j.LOHANG) {
            let tien = _i.SOLUONG * _j.DONGIA
            let vat = tien / 10
            if (this.dtVatTu[0].TRANGTHAI != null) {
              _j.TRANGTHAI = _i.TRANGTHAI
            } else {
              // _i.CHON, _j.LOHANG, _j.KHO_ID, _j.VATTU_ID, _j.DONGIA, _j.DVITINH_ID, _j.MA_VT, _j.TEN_VT, _j.DVI_TINH, _j.SL_TON, _j.LOAITBI_ID, _j.SL_TREO, _j.LOAI_TBI, _j.SOTHANG_PB, _j.SL_TON, _i.SOLUONG, tien, vat
              _dt.push(_j)
            }
            //new change
            if (this.dtLuuVT[0].TY_LE != null && this.dtLuuVT[0].MAKHOTD != null && this.dtLuuVT[0].LOAIKHO != null && this.dtLuuVT[0].MATHETS != null && this.dtLuuVT[0].MATHUNG != null) {
              _dt[i].TY_LE = _i.TY_LE
              _dt[i].MAKHOTD = _i.MAKHOTD == null || _i.MAKHOTD == '' ? 0 : _i.MAKHOTD
              _dt[i].LOAIKHO = _i.LOAIKHO == null || _i.LOAIKHO == '' ? 0 : _i.LOAIKHO
              _dt[i].MATHETS = _i.MATHETS
              _dt[i].MATHUNG = _i.MATHUNG
            }
            //
            console.log('_j.SL_XUAT')
            console.log(_j.SL_XUAT)
            console.log('_i.SOLUONG')
            console.log(_i.SOLUONG)

            // _j.SL_XUAT = _i.SOLUONG
            _j.CHON = 's1'
            //new change
            if (this.dtLuuVT[0].TY_LE != null && this.dtLuuVT[0].MAKHOTD != null && this.dtLuuVT[0].LOAIKHO != null && this.dtLuuVT[0].MATHETS != null && this.dtLuuVT[0].MATHUNG != null) {
              if (_j.CHON == 's1') {
                _j.TY_LE = _i.TY_LE
                _j.MAKHOTD = _i.MAKHOTD == '' || _i.MAKHOTD == null ? 0 : _i.MAKHOTD
                _j.LOAIKHO = _i.LOAIKHO == '' || _i.LOAIKHO == null ? 0 : _i.LOAIKHO
                _j.MATHETS = _i.MATHETS
                _j.MATHUNG = _i.MATHUNG
              }
            }
            //
            _k++
          }
        }
        if (_k == 0) {
          let tien = _i.SOLUONG * _i.DONGIA
          let vat = tien / 10
          let loaikho = _i.LOAIKHO == '' || _i.LOAIKHO == null ? 0 : _i.LOAIKHO
          let makho = _i.MAKHOTD == '' || _i.MAKHOTD == null ? 0 : _i.MAKHOTD
          console.log(`this.dtVatTu[]`)
          console.log(this.dtVatTu[0].TRANGTHAI != null)
          if (this.dtVatTu[0].TRANGTHAI != null) {
            console.log('push positive')
            console.log(_i)
            _dt_2,
              push({
                ID_GEN: _i.ID_GEN,
                CHON: _i.CHON,
                LOHANG: _i.LOHANG,
                KHO_ID: this.vkho_id,
                VATTU_ID: _i.VATTU_ID,
                DONGIA: _i.DONGIA,
                //  null,
                MA_VT: _i.MA_VT,
                TEN_VT: _i.TEN_VT,
                DVITINH_ID: _i.DVI_TINH,
                SOLUONG: _i.SOLUONG,
                LOAITBI_ID: _i.LOAITBI_ID,
                // 0,
                LOAI_TBI: _i.LOAI_TBI,
                SOTHANG_PB: _i.SOTHANG_PB,
                SOLUONG: _i.SOLUONG,
                SOLUONG: _i.SOLUONG,
                THANHTIEN: tien,
                VAT: vat,
                TRANGTHAI: _i.TRANGTHAI,
                //  null,
                //   null,
                LOAIKHO: loaikho,
                MAKHOTD: makho
              })
            // _dt_2.Rows.Add(_i.CHON, _i.LOHANG, vkho_id, _i.VATTU_ID, _i.DONGIA, null, _i.MA_VT, _i.TEN_VT, _i.DVI_TINH, _i.SOLUONG, _i.LOAITBI_ID, 0, _i.LOAI_TBI, _i.SOTHANG_PB, _i.SOLUONG, _i.SOLUONG, tien, vat, _i.TRANGTHAI, null, null, loaikho, makho)
          } else {
            console.log('push negative')
            console.log(_i)
            // _dt_2.Rows.Add(_i.CHON, _i.LOHANG, vkho_id, _i.VATTU_ID, _i.DONGIA, null, _i.MA_VT, _i.TEN_VT, _i.DVI_TINH, _i.SOLUONG, _i.LOAITBI_ID, 0, _i.LOAI_TBI, _i.SOTHANG_PB, _i.SOLUONG, _i.SOLUONG, tien, vat, null, null, loaikho, makho)
            _dt_2.push({
              ID_GEN: _i.ID_GEN,
              CHON: 's1',
              LOHANG: _i.LOHANG,
              KHO_ID: this.vkho_id,
              VATTU_ID: _i.VATTU_ID,
              DONGIA: _i.DONGIA,
              //  null,
              MA_VT: _i.MA_VT,
              TEN_VT: _i.TEN_VT,
              DVI_TINH: _i.DVI_TINH,
              SOLUONG: _i.SOLUONG,
              LOAITBI_ID: _i.LOAITBI_ID,
              // 0,
              LOAI_TBI: _i.LOAI_TBI,
              SOTHANG_PB: _i.SOTHANG_PB,
              SOLUONG: _i.SOLUONG,
              SOLUONG: _i.SOLUONG,
              THANHTIEN: tien,
              VAT: vat,
              // null,
              //  null,
              LOAIKHO: loaikho,
              MAKHOTD: makho
            })
          }
        }
      }
      console.log('----1----')
      // this.dtVatTu.concat(_dt_2)
      this.dtVatTu.push.apply(this.dtVatTu, _dt_2);
      
      this.dtVT_Xuat.push.apply(this.dtVT_Xuat, _dt);
      this.gridVatTu.list = this.dtVatTu
      console.log(this.dtVatTu)
      let a = this.dtVatTu.filter((p) => p.CHON == 's1')
      console.log('aaa')
      console.log(a)
      let dtt = []
      if (a.length > 0) {
        dtt = _.clone(a)
        this.gridXuat.list = dtt
        for (let e of this.gridXuat.list) {
          this.gridVatTu.list[this.gridVatTu.list.findIndex((x) => x.ID_GEN == e.ID_GEN)].SELECTED = true
        }
      }
    },
    // async ds_vt_xuat() {
    //   let dt = []
    //   let dt_2 = []
    //   console.log('482 - ds_vt_xuat')
    //   console.log(this.dtLuuVT)
    //   for (let item of this.dtLuuVT) {
    //     let _k = 0
    //     for (let vt of this.dtVatTu) {
    //       if (item['VATTU_ID'] == vt['VATTU_ID'] && item['LOHANG'] == vt['LOHANG']) {
    //         let tien = Math.round(parseFloat(item['SOLUONG']) * parseFloat(vt['DONGIA']))
    //         let vat = tien / 10
    //         if (this.dtVatTu[0].hasOwnProperty('TRANGTHAI')) {
    //           vt['TRANGTHAI'] = item['TRANGTHAI']
    //         } else {
    //           dt.push({
    //             CHON: item['CHON'],
    //             LOHANG: vt['LOHANG'],
    //             KHO_ID: vt['KHO_ID'],
    //             VATTU_ID: vt['VATTU_ID'],
    //             DONGIA: vt['DONGIA'],
    //             DVITINH_ID: vt['DVITINH_ID'],
    //             MA_VT: vt['MA_VT'],
    //             TEN_VT: vt['TEN_VT'],
    //             DVI_TINH: vt['DVI_TINH'],
    //             SL_TON: vt['SL_TON'],
    //             LOAITBI_ID: vt['LOAITBI_ID'],
    //             SL_TREO: vt['SL_TREO'],
    //             LOAI_TBI: vt['LOAI_TBI'],
    //             SOTHANG_PB: vt['SOTHANG_PB'],
    //             SL_XUAT: vt['SL_TON'],
    //             SOLUONG: item['SOLUONG'],
    //             THANHTIEN: tien,
    //             VAT: vat
    //           })
    //         }
    //         // //new change
    //         // if (this.dtLuuVT[0].hasOwnProperty('TY_LE') && this.dtLuuVT[0].hasOwnProperty('MAKHOTD') && this.dtLuuVT[0].hasOwnProperty('LOAIKHO') && this.dtLuuVT[0].hasOwnProperty('MATHETS') && this.dtLuuVT[0].hasOwnProperty('MATHUNG')) {
    //         //   dt[i]['TY_LE'] = item['TY_LE']
    //         //   dt[i]['MAKHOTD'] = !item['MAKHOTD'] || item['MAKHOTD'] == '' ? 0 : parseInt(item['MAKHOTD'])
    //         //   dt[i]['LOAIKHO'] = !item['LOAIKHO'] || item['LOAIKHO'] == '' ? 0 : parseInt(item['LOAIKHO'])
    //         //   dt[i]['MATHETS'] = item['MATHETS']
    //         //   dt[i]['MATHUNG'] = item['MATHUNG']
    //         // }
    //         // //

    //         vt['SL_XUAT'] = item['SOLUONG']
    //         vt['CHON'] = 's1'
    //         //new change
    //         if (this.dtLuuVT[0].hasOwnProperty('TY_LE') && this.dtLuuVT[0].hasOwnProperty('MAKHOTD') && this.dtLuuVT[0].hasOwnProperty('LOAIKHO') && this.dtLuuVT[0].hasOwnProperty('MATHETS') && this.dtLuuVT[0].hasOwnProperty('MATHUNG')) {
    //           if (vt['CHON'] == 's1') {
    //             vt['TY_LE'] = item['TY_LE']
    //             vt['MAKHOTD'] = !item['MAKHOTD'] || item['MAKHOTD'] == '' ? 0 : parseInt(item['MAKHOTD'])
    //             vt['LOAIKHO'] = !item['LOAIKHO'] || item['LOAIKHO'] == '' ? 0 : parseInt(item['LOAIKHO'])
    //             vt['MATHETS'] = item['MATHETS']
    //             vt['MATHUNG'] = item['MATHUNG']
    //           }
    //         }
    //         //
    //         _k++
    //       }
    //     }
    //     if (_k == 0) {
    //       let tien = Math.round(parseFloat(item['SOLUONG']) * parseFloat(item['DONGIA']))
    //       let vat = tien / 10
    //       let loaikho = !item['LOAIKHO'] || item['LOAIKHO'] == '' ? 0 : parseInt(item['LOAIKHO'])
    //       let makho = !item['MAKHOTD'] || item['MAKHOTD'] == '' ? 0 : parseInt(item['MAKHOTD'])
    //       if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('TRANGTHAI')) {
    //         dt_2.push({
    //           CHON: item['CHON'],
    //           LOHANG: item['LOHANG'],
    //           KHO_ID: this.vkho_id,
    //           VATTU_ID: item['VATTU_ID'],
    //           DONGIA: item['DONGIA'],
    //           DVITINH_ID: '',
    //           MA_VT: item['MA_VT'],
    //           TEN_VT: item['TEN_VT'],
    //           DVI_TINH: item['DVI_TINH'],
    //           SOLUONG: item['SOLUONG'],
    //           LOAITBI_ID: item['LOAITBI_ID'],
    //           SL_TREO: 0,
    //           LOAI_TBI: item['LOAI_TBI'],
    //           SOTHANG_PB: item['SOTHANG_PB'],
    //           SL_XUAT: item['SOLUONG'],
    //           SOLUONG: item['SOLUONG'],
    //           THANHTIEN: tien,
    //           VAT: vat,
    //           TRANGTHAI: item['TRANGTHAI'],
    //           // : '',
    //           // '',
    //           LOAIKHO: loaikho,
    //           MAKHO: makho
    //         })
    //       } else {
    //         dt_2.push({
    //           CHON: item['CHON'],
    //           LOHANG: item['LOHANG'],
    //           KHO_ID: this.vkho_id,
    //           VATTU_ID: item['VATTU_ID'],
    //           DONGIA: item['DONGIA'],
    //           DVITINH_ID: '',
    //           MA_VT: item['MA_VT'],
    //           TEN_VT: item['TEN_VT'],
    //           DVI_TINH: item['DVI_TINH'],
    //           SOLUONG: item['SOLUONG'],
    //           LOAITBI_ID: item['LOAITBI_ID'],
    //           SL_TREO: 0,
    //           LOAI_TBI: item['LOAI_TBI'],
    //           SOTHANG_PB: item['SOTHANG_PB'],
    //           SL_XUAT: item['SOLUONG'],
    //           SOLUONG: item['SOLUONG'],
    //           THANHTIEN: tien,
    //           VAT: vat,
    //           TRANGTHAI: '',
    //           // : '',
    //           // '',
    //           LOAIKHO: loaikho,
    //           MAKHO: makho
    //         })
    //       }
    //     }
    //   }
    //   dt_2.length ? this.dtVatTu.push(...dt_2) : ''
    //   dt.length ? this.dtVT_Xuat.push(...dt) : ''
    //   console.log('1097 - this.dtVatTu')
    //   console.log(this.dtLuuVT)

    //   this.gridVatTu.list = this.dtVatTu
    //   this.convertData_gridVatTu()

    //   let p = this.dtVatTu.map((e) => e)
    //   let vatTuIndexs = []
    //   console.log('1107 - this.dtVatTu')
    //   console.log(this.dtVatTu)
    //   this.dtVatTu.filter((b, index) => {
    //     if (b.CHON == 's1') {
    //       vatTuIndexs.push(index)
    //     }
    //   })

    //   if (p.length >= 0) {
    //     this.gridXuat.list = this.gridVatTu.list.filter((e) => this.dtLuuVT.filter((x) => x.LOHANG == e.LOHANG && x.VATTU_ID == e.VATTU_ID).length > 0)
    //     this.convertData_gridXuat()
    //     console.log('1116 - this.gridVatTu.list')
    //     console.log(this.gridXuat.list)
    //     console.log(this.gridVatTu.list)
    //     for (let e of this.gridXuat.list) {
    //       console.log(this.gridVatTu.list.find((x) => x.ID_GEN == e.ID_GEN))
    //       this.gridVatTu.list[this.gridVatTu.list.findIndex((x) => x.ID_GEN == e.ID_GEN)].SELECTED = true
    //     }
    //     this.$refs.gridVatTu.grid.refreshColumns()
    //     console.log('end')
    //     console.log(this.gridVatTu.list)
    //   }
    // },
    async tsbtnHoanThanh_Click() {
      let a = this.gridVatTu.list.filter((e) => e.SELECTED == true)
      if (!a.length) {
        this.$toast.error('Chưa chọn vật tư nào!')
        this.XacNhan = false
        return
      }
      // Kiểm tra
      let check = this.gridXuat.list
      console.log('662 - this.gridXuat.list ')
      console.log(this.gridXuat.list)
      if (check.length && check[0].hasOwnProperty('TRANGTHAI')) {
        let kq = await this.callApiWrapper(api.kiem_tra_trang_thai_vt, {
          dsVatTu: check
        })
        if (kq == '0') {
          this.$toast.error("Vật tư có trạng thái 'Chưa có đầu vào' không được chọn chung với các vật tư khác !")
          return
        }
      }

      // this.dt = a
      this.dt = this.gridXuat.list
      for (let item of this.dt) {
        if (parseFloat(item['SL_XUAT'] || 0) <= 0) {
          this.$toast.error('Số lượng xuất phải lớn hơn 0!')
          return
        }
      }

      let kt = this.dt.filter((p) => parseFloat(p['SL_XUAT']) > parseFloat(p['SOLUONG']))
      if (kt.length) {
        let message = ''
        for (let e of kt) {
          message = message + ' (' + e.LOHANG + ' - ' + e.MA_VT + ')'
        }
        //wtf
        this.$toast.error('Không được xuất quá lượng tồn của vật tư !' + message)
        this.XacNhan = false
        return
      }
      this.XacNhan = true
      this.checkAll = this.ckbAllTbi.value
      if (this.dt.length) {
        for (let item of this.dt) {
          item['SOLUONG'] = item['SL_XUAT']
        }
      }
      if (this.coDS) {
        this.ds_dangky = this.grcCT_DK
      }
      console.log('tsbtnHoanThanh_Click')
      console.log(this.dt)
      this.$emit('onBtnXacNhanClicked', {
        XacNhan: this.XacNhan,
        dt: this.dt,
        checkAll: this.checkAll,
        vkho_id: this.vkho_id,
        coDS: this.coDS,
        ds_dangky: this.ds_dangky
      })
    },

    async tsbtnHuyBo_Click() {
      this.XacNhan = false
    },

    async btnXuatExel_Click() {
      let dt = this.$refs.gridVatTu.getSelectedRecords()
      if (dt.length) {
        await this.SaveXlsAndOpenFile('VatTuKho-' + this.vkho_id + '.xlsx', 'sheet1', dt)
      } else {
        this.$toast.error('Không có vật tư nào được chọn để xuất excel!')
      }
    },
    async SaveXlsAndOpenFile(fileName, sheetName, exportData) {
      if (!exportData.length) {
        this.$toast.error('Không có bản ghi nào để xuất!')
        return
      }
      let data1 = XLSX.utils.json_to_sheet(exportData)
      let wb = XLSX.utils.book_new() // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, sheetName) // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, fileName)
    },
    async tsbtnXuatExcelTbi_Click() {
      let ds = this.$refs.gridVatTu.getSelectedRecords()

      let dt = []
      for (let rtbi of this.dtThietBi) {
        for (let item of ds) {
          if (rtbi['LOHANG'] == item.LOHANG && rtbi['VATTU_ID'] == item.VATTU_ID) {
            let dr = {}
            dr['LOHANG'] = item.LOHANG
            dr['MA_VT'] = rtbi['MA_VT']
            dr['TEN_VT'] = rtbi['TEN_VT']
            dr['DVI_TINH'] = rtbi['DVI_TINH']
            dr['MA_TBI'] = rtbi['MA_TBI']
            dr['SERIAL'] = rtbi['SERIAL']
            dr['SOLUONG'] = parseInt(rtbi['SOLUONG'])
            dr['DONGIA'] = parseInt(rtbi['DONGIA'])
            dr['SERIAL_GP'] = rtbi['SERIAL_GP']

            dt.push(dr)
          }
        }
      }
      if (dt.length == 0) {
        dt.push({
          LOHANG: '',
          MA_VT: '',
          TEN_VT: '',
          DVI_TINH: '',
          MA_TBI: '',
          SERIAL: '',
          SOLUONG: 0,
          DONGIA: 0,
          SERIAL_GP: ''
        })
      }
      await this.SaveXlsAndOpenFile('ThietBiKho-' + this.vkho_id + '.xlsx', 'sheet1', dt)
    },
    async cboTuKho_EditValueChanged(e) {
      console.log('cboTuKho_EditValueChanged')
      console.log(e)
      // try {
      await this.LOAD_DS_TK()
      // } catch (e) {
      //   this.$toast.error(e.data.message)
      // } finally {
      // }
    },
    async load_cboRePo() {},
    async LOAD_DS_TK() {
      // try {
      if (this.kieu == 0) {
        if (this.$auth.getMaTinh() == 'NET') {
          let table = [
            {
              IS_LINHVUC: this.ckbLinhVuc_TK.value ? 1 : 0,
              IS_HANGSX: this.params.p_ckbHangSX_TK ? 1 : 0,
              LINHVUC_ID: this.params.p_LinhVuc_TK,
              HETHONG_ID: this.params.p_HeThong_TK,
              HANGSX_ID: this.ckbHangSX_TK.value,
              DONGTBI_ID: this.params.p_DongTbi_TK
            }
          ]
          this.dtVatTu = await this.callApiWrapper(api.ds_vattu_ton_kho_v3, {
            // "phanVungId": 26,
            khoId: this.cboTuKho.value,
            ngayCT: getDateStringOrDefault(this.dtpNgayDK.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
            chungTuId: 0,
            kieu: 0,
            tinhHienTai: this.$auth.getMaTinh(),
            dataTonKho: JSON.stringify(table)
          })
        } else {
          this.dtVatTu = await this.callApiWrapper(api.ds_vattu_ton_kho, {
            // "phanVungId": 26,
            khoId: this.cboTuKho.value,
            ngayCT: getDateStringOrDefault(this.dtpNgayDK.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
            chungTuId: 0,
            kieu: this.kieu,
            tinhHienTai: this.$auth.getMaTinh()
          })
        }
      } else if (this.kieu == 1) {
        this.dtVatTu = await this.callApiWrapper(api.ds_vattu_ton_kho, {
          // "phanVungId": 26,
          khoId: this.cboTuKho.value,
          ngayCT: getDateStringOrDefault(this.dtpNgayDK.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
          chungTuId: 0,
          kieu: this.kieu,
          tinhHienTai: this.$auth.getMaTinh()
        })
      } else if (this.kieu == 2) {
        this.dtVatTu = await this.callApiWrapper(api.ds_vattu_ton_kho, {
          //  "phanVungId": 26,
          khoId: this.cboTuKho.value,
          ngayCT: getDateStringOrDefault(this.dtpNgayDK.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
          chungTuId: 0,
          kieu: this.kieu,
          tinhHienTai: this.$auth.getMaTinh()
        })
      }

      this.DS_Xuat = []
      if (this.dtVatTu.length) {
        this.DS_Xuat = [] //dtVatTu.Clone() = []
        this.dtVT_Xuat = []
      }
      this.gridVatTu.list = this.dtVatTu
      this.convertData_gridVatTu()
      if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('MAKHOTD') && this.dtVatTu[0].hasOwnProperty('LOAIKHO')) {
        this.repoLoaiKho = await this.callApiWrapper(api.ds_loai_khotd, {})
      }

      // this.setGridVatTuColumnsVisible(['colSL_DD','colSL_KD'],false)
      // colSL_DD -> SL_DD
      // colSL_KD -> SL_KD
      this.gridThietBi_Columns.forEach((a) => {
        if (['SL_DD', 'SL_KD'].includes(a.fieldName)) {
          a.visible = false
        }
      })
      if (this.kieu != 1 && this.kieu != 2) {
        this.dtThietBi = await this.callApiWrapper(api.ds_thietbi_tonkho, { kho_id: this.cboTuKho.value })
        if (this.dtThietBi.length) {
          if (this.dtThietBi[0].hasOwnProperty('SL_DD')) {
            // this.setGridVatTuColumnsVisible(['colSL_DD','colSL_KD'],true)
            this.gridThietBi_Columns.forEach((a) => {
              if (['SL_DD', 'SL_KD'].includes(a.fieldName)) {
                a.visible = true
              }
            })
          }
        }
        this.gridThietBi = this.dtThietBi
        this.convertData_gridThietBi()
      }
      // } catch (error) {
      //   this.$toast.error(error)
      // }
    },
    async chkChonVT_CheckedChanged() {
      // label3.Focus();
    },
    tsbDanhSach_Click() {
      this.enabled.tsbDanhSach = false
    },
    async gridViewVatTu_CellValueChanged(args) {
      let gridViewVatTu = this.$refs.gridVatTu
      let changes = gridViewVatTu.getBatchChanges()
      gridViewVatTu.batchUpdate(changes)
      gridViewVatTu.grid.refreshColumns()
      console.log('gridViewVatTu_CellValueChanged')
      console.log(args)
      // if (args.columnName == 'SL_XUAT'){
      // if (parseInt(args.value) > 0){
      //   let index = this.gridVatTu.list .findIndex(a=>a.VATTU_ID == args.rowData.VATTU_ID)
      //   this.indexArray.push(index)
      //   this.$refs.gridVatTu.grid.selectRows(indexArray,true)
      // }
      // else{
      //   let index = this.gridVatTu.list .findIndex(a=>a.VATTU_ID == args.rowData.VATTU_ID)
      //   this.indexArray.push(index)
      //   this.$refs.gridVatTu.grid.selectRows(indexArray,true)
      // }
      // }
      await this.LAY_DS_VTXUAT()
    },
    LAY_DS_VTXUAT() {
      console.log('LAY_DS_VTXUAT')
      console.log(this.gridVatTu.list)
      this.DS_Xuat = []
      let DS = []
      let dem = 0
      let ds_check = this.gridVatTu.list
      this.gridXuat.list = []
      console.log('this.DSVatTuChecked')
      console.log(this.DSVatTuChecked)
      // let RowVatTuChecked = this.$refs.gridVatTu.grid.getSelectedRecords()
      // getSelectedRecords chưa update giá trị, nhưng datasource đã update
      for (let dr of this.DSVatTuChecked) {
        // let dr = this.gridVatTu.list [i]
        let tien = parseFloat(dr['SL_XUAT']) * parseFloat(dr['DONGIA'])
        let vat = tien / 10
        let vatTu = {}

        // if (dr.hasOwnProperty('TRANGTHAI')) {
        //   vatTu = {
        //     ID_GEN: dr.ID_GEN,
        //     CHON: dr['CHON'],
        //     LOHANG: dr['LOHANG'],
        //     KHO_ID: dr['KHO_ID'],
        //     VATTU_ID: dr['VATTU_ID'],
        //     DONGIA: dr['DONGIA'],
        //     DVITINH_ID: dr['DVITINH_ID'],
        //     MA_VT: dr['MA_VT'],
        //     TEN_VT: dr['TEN_VT'],
        //     DVI_TINH: dr['DVI_TINH'],
        //     SL_TON: dr['SL_TON'],
        //     LOAITBI_ID: dr['LOAITBI_ID'],
        //     SL_TREO: dr['SL_TREO'],
        //     LOAI_TBI: dr['LOAI_TBI'],
        //     SOTHANG_PB: dr['SOTHANG_PB'],
        //     SOLUONG: dr['SOLUONG'],
        //     SL_XUAT: dr['SL_XUAT'],
        //     THANHTIEN: tien,
        //     VAT: vat,
        //     TRANGTHAI: dr['TRANGTHAI'],
        //     LOAIKHO: dr['LOAIKHO'],
        //     MAKHOTD: dr['MAKHOTD'],
        //     CHATLUONG: dr['CHATLUONG'],
        //     CHATLUONG_ID: dr['CHATLUONG_ID']
        //   }
        // } else {
        //   vatTu = {
        //     ID_GEN: dr.ID_GEN,
        //     CHON: dr['CHON'],
        //     LOHANG: dr['LOHANG'],
        //     KHO_ID: dr['KHO_ID'],
        //     VATTU_ID: dr['VATTU_ID'],
        //     DONGIA: dr['DONGIA'],
        //     DVITINH_ID: dr['DVITINH_ID'],
        //     MA_VT: dr['MA_VT'],
        //     TEN_VT: dr['TEN_VT'],
        //     DVI_TINH: dr['DVI_TINH'],
        //     SL_TON: dr['SL_TON'],
        //     LOAITBI_ID: dr['LOAITBI_ID'],
        //     SL_TREO: dr['SL_TREO'],
        //     LOAI_TBI: dr['LOAI_TBI'],
        //     SOTHANG_PB: dr['SOTHANG_PB'],
        //     SOLUONG: dr['SOLUONG'],
        //     SL_XUAT: dr['SL_XUAT'],
        //     THANHTIEN: tien,
        //     VAT: vat,
        //     LOAIKHO: dr['LOAIKHO'],
        //     MAKHOTD: dr['MAKHOTD'],
        //     CHATLUONG: dr['CHATLUONG'],
        //     CHATLUONG_ID: dr['CHATLUONG_ID']
        //   }
        // }
        dr.THANHTIEN = tien
        dr.VAT = vat
        let check = this.gridXuat.list.filter((a) => a.ID_GEN == dr.ID_GEN)
        console.log('check')
        console.log(check)

        if (check.length == 0) {
          this.gridXuat.list.push(dr)
          this.DS_Xuat.push(dr)
        } else {
          for (let e of this.gridXuat.list) {
            if (e.ID_GEN == dr.ID_GEN) {
              e.SL_XUAT = dr.SL_XUAT
            }
          }
        }
        // if (vatTu.SL_XUAT == 0) {
        //   // = 0 thì xóa
        //   console.log('SL_XUAT == 0')
        //   console.log()
        //   let gridXuat_temp = []
        //   for (let e of this.gridXuat.list) {
        //     if (e.ID_GEN != vatTu.ID_GEN) {
        //       gridXuat_temp.push(e)
        //     }
        //   }
        //   this.gridXuat.list = gridXuat_temp
        // }
      }
      // dem += 1
      // this.$refs.gridXuat.grid.refreshColumns()
      // this.$refs.gridXuat.grid.refreshColumns()
      this.convertData_gridXuat()
    },
    async tsbtnDangKy_Click() {
      // tableLayoutPanel1
      // try {
      if (this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU) && (this.dsQuyen.includes(916) || this.dsQuyen.includes(917))) {
        this.visible.splitContainer2.Panel2Collapsed = false
        // quy them
        this.visible.ShowDKTT = false
        let bien = 100
        if (this.tinh.includes(this.$auth.getMaTinh())) {
          bien = 101
        }
        let ds_dk = await this.callApiWrapper(api.ds_dangky_theo_dk, {
          tuThangDk: '0',
          denThangDk: '0',
          ttdkId: bien,
          donViId: -1,
          tuNgay: getDateStringOrDefault(this.vngay_ct, 'DD/MM/YYYY'),
          denNgay: getDateStringOrDefault(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()), 'DD/MM/YYYY')
        })

        if (ds_dk.length) {
          let s = 'Đăng ký đã tổng hợp'
          if (this.tinh.includes(this.$auth.getMaTinh())) {
            s = 'Đăng ký chưa tổng hợp'
          }
          let q = ds_dk.filter((l) => l['NHOM'].includes(s))
          this.grcDSDangKy = []
          this.grcDSVatTu_DaDK = []
          if (q.length) {
            this.grcDSDangKy = q
            this.convertData_grcDSDangKy()

            if (this.tinh.includes(this.$auth.getMaTinh())) {
              this.visible.btnXoa = true
              this.enabled.grvDSVatTu_DaDK.SL_XUAT = true
              this.enabled.grvDSVatTu_DaDK.BODUYET = true
            } else {
              this.visible.btnXoa = false
              this.enabled.grvDSVatTu_DaDK.SL_XUAT = false
              this.enabled.grvDSVatTu_DaDK.BODUYET = false
            }
          } else {
            this.grcDSVatTu_DaDK = []
            this.grcDSDangKy = []
          }
        }
      } else {
        this.$toast.error('Bạn không có quyền thuộc VT để dùng chức năng này')
      }
      // } catch (error) {
      //   "Có lỗi lấy danh sách đăng ký"
      // }
    },
    async picClose_Click() {
      this.visible.splitContainer2.Panel2Collapsed = true
    },
    async HienThiDS_VTDK() {
      let ds_th = await this.callApiWrapper(api.ds_da_dangky, { dangky_id: this.grvDSDangKy_FocusedRow.DANGKY_ID })
      if (ds_th.length) {
        this.grcDSVatTu_DaDK = ds_th
        this.convertData_grcDSVatTu_DaDK()
      } else {
        this.grcDSVatTu_DaDK = []
      }
    },
    async HienThiDS_VTTT() {
      let ds_th = await this.callApiWrapper(api.ds_da_dangky, { dangky_id: this.grvDK_TT_FocusedRow.DANGKY_ID })
      if (ds_th.length) {
        this.grcCT_DK = ds_th
        this.convertData_grcCT_DK()
      } else {
        this.grcCT_DK = []
      }
    },
    grvDSVatTu_DaDK_FocusedRowChanged() {},
    async btnXoa_Click() {
      if (this.tinh.includes(this.$auth.getMaTinh())) {
        await await this.callApiWrapper(api.xoa_soluong_duyet_vattu, { dangky_id: this.grvDSDangKy_FocusedRow.DANGKY_ID })
      }
      await this.HienThiDS_VTDK()
      return
    },
    async grvDSVatTu_DaDK_CellValueChanged() {
      // lam sau
    },
    async grvDSDangKy_FocusedRowChanged(row) {
      if (row) {
        this.grvDSDangKy_FocusedRow = row
      }
      this.grcDSVatTu_DaDK = []

      await this.HienThiDS_VTDK()
    },
    async btnDel_ButtonClick() {
      // lam sau
    },
    grcDSVatTu_DaDK_KeyDown() {},
    async tsbtnPhieuTT_Click() {
      if (this.vkho_den_id != 0) {
        if (this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU) && (this.dsQuyen.includes(916) || this.dsQuyen.includes(917) || this.dsQuyen.includes(914))) {
          this.visible.splitContainer2.Panel2Collapsed = false
          // quy them
          this.visible.ShowDKTT = true
        }
        let bien = 100
        if (this.tinh.includes(this.$auth.getMaTinh())) {
          bien = 101
        }
        let ds_dk = []
        if (!this.vchungtu_id) {
          ds_dk = await this.callApiWrapper(api.ds_dangky_theo_dktt, {
            tuThangDk: '0',
            denThangDk: '0',
            ttdkId: this.vkho_den_id,
            donViId: -1,
            tuNgay: getDateStringOrDefault(this.vngay_ct, 'DD/MM/YYYY'),
            denNgay: getDateStringOrDefault(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()), 'DD/MM/YYYY')
          })
        } else {
          ds_dk = await this.callApiWrapper(api.ds_dangky_theo_dktt, {
            tuThangDk: '1',
            denThangDk: '0',
            ttdkId: this.vkho_den_id,
            donViId: -1,
            tuNgay: getDateStringOrDefault(this.vchungtu_id, 'DD/MM/YYYY'),
            denNgay: getDateStringOrDefault(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()), 'DD/MM/YYYY')
          })
        }

        if (ds_dk.length) {
          this.grcDK_TT = ds_dk
          this.convertData_grcDK_TT()
          this.visible.btnXoa = false
        } else {
          this.grcDK_TT = []
          this.grcCT_DK = []
        }
      }
    },
    async repositoryItemCheckEdit4_CheckedChanged() {
      await this.HienThiDS_VTTT()
    },
    async pictureEdit1_Click() {
      this.visible.splitContainer2.Panel2Collapsed = true
      this.coDS = false
    },
    async repositoryItemButtonEdit1_ButtonClick() {
      // try {
      await this.$bvModal
        .msgBoxConfirm('Bạn có chắc chắn muốn trả phiếu không?', {
          okVariant: 'success',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        .then(async (value) => {
          if (value) {
            let q = this.grvDK_TT_FocusedRow.DANGKY_ID
            await this.callApiWrapper(api.vattu_dang_ky_tra_phieu, { dangKyId: q })

            this.$toast.success('Trả phiếu thành công')
            let ds_dk = []
            if (this.vchungtu_id == 0) {
              await this.callApiWrapper(api.ds_dangky_theo_dktt, {
                tuThangDk: '0',
                denThangDk: '0',
                ttdkId: this.vkho_den_id,
                donViId: -1,
                tuNgay: getDateStringOrDefault(this.vngay_ct, 'DD/MM/YYYY'),
                denNgay: getDateStringOrDefault(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()), 'DD/MM/YYYY')
              })
            } else {
              await this.callApiWrapper(api.ds_dangky_theo_dktt, {
                tuThangDk: '1',
                denThangDk: '0',
                ttdkId: this.vchungtu_id,
                donViId: -1,
                tuNgay: getDateStringOrDefault(this.vngay_ct, 'DD/MM/YYYY'),
                denNgay: getDateStringOrDefault(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()), 'DD/MM/YYYY')
              })
            }

            if (this.ds_dk.length) {
              this.grcDK_TT = ds_dk
              this.convertData_grcDK_TT()
              this.visible.btnXoa = false
            } else {
              this.grcDK_TT = []
              this.grcCT_DK = []
            }
          }
        })
        .catch((err) => {
          // An error occurred
        })

      // } catch (error) {

      // }
    },
    async cboTuKho_ButtonClick() {
      await this.LOAD_DS_TK()
    },
    gridViewVatTu_CustomSummaryCalculate() {},
    gridViewVatTu_MouseUp() {},
    gridviewXuat_CustomSummaryCalculate() {},
    gridviewXuat_MouseUp() {},
    gridViewThietBi_CustomRowCellEdit() {},
    async repositoryItemPopupContainerEdit1_Click() {
      // try {
      this.grcTT = await this.callApiWrapper(api.ds_vttb_chua_hoan_tamung, { khoId: this.cboTuKho.value, thietBiId: this.gridViewThietBi_FocusedRow.THIETBI_ID })
      // } catch (error) {
      //   this.$toast.error()
      // }
    },
    async ckbLinhVuc_TK_CheckedChanged() {
      this.enabled.cboLinhVuc_TK = this.ckbLinhVuc_TK.value
      this.enabled.cboHeThong_TK = this.ckbLinhVuc_TK.value
      if (this.ckbLinhVuc_TK.value) {
        await this.NAP_DS_DONG_TBI()
      }
    },
    async ckbHangSX_TK_CheckedChanged() {
      this.enabled.ckbHangSX_TK.list = this.params.p_ckbHangSX_TK
      this.enabled.cboDongTbi_TK = this.params.p_ckbHangSX_TK
      if (this.params.p_ckbHangSX_TK) {
        await this.NAP_DS_DONG_TBI()
      }
    },
    async cboLinhVuc_TK_EditValueChanged() {
      // try {
      this.cboHeThong_TK = []
      if (this.params.p_LinhVuc_TK) {
        this.cboHeThong_TK = await this.callApiWrapper(api.ds_he_thong, { linhVucId: this.params.p_LinhVuc_TK })
        this.cboHeThong_TK.length ? (this.params.p_HeThong_TK = this.cboHeThong_TK[0].heThongId) : ''
      }
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi nạp danh sách hệ thống")
      // }
    },
    async cboHangSX_TK_EditValueChanged() {
      await this.NAP_DS_DONG_TBI()
    },
    async NAP_DS_DONG_TBI() {
      // try {
      this.cboDongTbi_TK = []
      if (this.ckbHangSX_TK.value) {
        this.cboDongTbi_TK = await this.callApiWrapper(api.ds_dong_tbi, {
          hangSxId: this.ckbHangSX_TK.value,
          heThongId: this.params.p_HeThong_TK,
          checkLinhVuc: this.ckbLinhVuc_TK.value ? 1 : 0
        })
        this.cboDongTbi_TK.length ? (this.params.p_cboDongTbi_TK = this.cboDongTbi_TK[0].dongTbiId) : ''
      }
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi nạp danh sách hệ thống")
      // }
    },
    async cboHeThong_TK_EditValueChanged() {
      await this.NAP_DS_DONG_TBI()
    },
    async btnFilter_Click() {
      await this.LOAD_DS_TK()
    },
    // async ShowDialog() {
    //   this.$refs.gridVatTu.grid.clearSelection() // clear lưới vật tư đã chọn
    //   this.$refs.popup_frmVatTuTrongKho.show()
    // },

    async Clear() {
      this.gridVatTu.list = []
      this.gridXuat.list = []
    },
    openDialog() {
      this.$refs.popup_frmVatTuTrongKho.show()
    },
    convertData_gridVatTu() {
      console.log('convertData_gridVatTu')
      console.log(this.gridVatTu.list)
      this.gridVatTu.list.forEach((item) => {
        item.SL_TREO != null && item.SL_TREO ? (item.SL_TREO = parseInt(item.SL_TREO)) : 0
        item.SL_TON != null && item.SL_TON ? (item.SL_TON = parseFloat(item.SL_TON)) : 0
        item.DONGIA != null && item.DONGIA ? (item.DONGIA = parseFloat(item.DONGIA)) : 0
        item.SL_TAMUNG != null && item.SL_TAMUNG ? (item.SL_TAMUNG = parseInt(item.SL_TAMUNG)) : 0
        item.SOLUONG != null && item.SOLUONG ? (item.SOLUONG = parseFloat(item.SOLUONG)) : 0
        item.SL_C_DD != null && item.SL_C_DD ? (item.SL_C_DD = parseFloat(item.SL_C_DD)) : 0
        item.SL_XUAT != null && item.SL_XUAT ? (item.SL_XUAT = parseFloat(item.SL_XUAT)) : 0
        item.SL_TONTHUC != null && item.SL_TONTHUC ? (item.SL_TONTHUC = parseFloat(item.SL_TONTHUC)) : 0
        item.VAT != null && item.VAT ? (item.VAT = parseFloat(item.VAT)) : 0
        item.THANHTIEN != null && item.THANHTIEN ? (item.THANHTIEN = parseFloat(item.THANHTIEN)) : 0
      })
    },
    convertData_gridThietBi() {
      this.gridThietBi.forEach((item) => {
        item.SOLUONG != null && item.SOLUONG ? (item.SOLUONG = parseFloat(item.SOLUONG)) : ''
        item.SL_DD != null && item.SL_DD ? (item.SL_DD = parseFloat(item.SL_DD)) : ''
        item.SL_KD != null && item.SL_KD ? (item.SL_KD = parseFloat(item.SL_KD)) : ''
        item.DONGIA != null && item.DONGIA ? (item.DONGIA = parseFloat(item.DONGIA)) : ''
        item.THANHTIEN != null && item.THANHTIEN ? (item.THANHTIEN = parseFloat(item.THANHTIEN)) : ''
      })
    },

    convertData_gridXuat() {
      this.gridXuat.list.forEach((item) => {
        item.SOLUONG != null && item.SOLUONG ? (item.SOLUONG = parseFloat(item.SOLUONG)) : ''
        item.SL_XUAT != null && item.SL_XUAT ? (item.SL_XUAT = parseFloat(item.SL_XUAT)) : ''
        item.DONGIA != null && item.DONGIA ? (item.DONGIA = parseFloat(item.DONGIA)) : ''
        item.THANHTIEN != null && item.THANHTIEN ? (item.THANHTIEN = parseFloat(item.THANHTIEN)) : ''
      })
    },
    convertData_grcDSDangKy() {
      this.grcDSDangKy.forEach((item) => {
        item.CONLAI != null && item.CONLAI ? (item.CONLAI = parseFloat(item.CONLAI)) : ''
      })
    },
    convertData_grcDSVatTu_DaDK() {
      this.grcDSVatTu_DaDK.forEach((item) => {
        item.SL_TON_DK != null && item.SL_TON_DK ? (item.SL_TON_DK = parseFloat(item.SL_TON_DK)) : ''
        item.SOLUONG != null && item.SOLUONG ? (item.SOLUONG = parseFloat(item.SOLUONG)) : ''
        item.SL_XUAT != null && item.SL_XUAT ? (item.SL_XUAT = parseFloat(item.SL_XUAT)) : ''
        item.SL_DUYET != null && item.SL_DUYET ? (item.SL_DUYET = parseFloat(item.SL_DUYET)) : ''
      })
    },

    convertData_grcDK_TT() {
      this.grcDK_TT.forEach((item) => {
        item.CONLAI != null && item.CONLAI ? (item.CONLAI = parseFloat(item.CONLAI)) : ''
      })
    },
    convertData_grcCT_DK() {
      this.grcCT_DK.forEach((item) => {
        item.SL_TON_DK != null && item.SL_TON_DK ? (item.SL_TON_DK = parseFloat(item.SL_TON_DK)) : ''
        item.SOLUONG != null && item.SOLUONG ? (item.SOLUONG = parseFloat(item.SOLUONG)) : ''
        item.SL_XUAT != null && item.SL_XUAT ? (item.SL_XUAT = parseFloat(item.SL_XUAT)) : ''
        item.SL_DUYET != null && item.SL_DUYET ? (item.SL_DUYET = parseFloat(item.SL_DUYET)) : ''
      })
    },
    async gridVatTuCheckboxSelected(row, value) {
      console.log('gridVatTuCheckboxSelected')
      console.log(row)
      console.log(value)
      this.gridVatTu.list.find((e) => e.ID_GEN == row.ID_GEN).SELECTED = value
      this.DSVatTuChecked = this.gridVatTu.list.filter((e) => e.SELECTED == true)
      await this.LAY_DS_VTXUAT()
      console.log(this.DSVatTuChecked)
    },
    async gridVatTuCheckboxTitleSelected(value) {
      let visibleRecords = (await this.$refs.gridVatTu.grid.getCurrentViewRecords()).map(e => e.ID_GEN)
      this.gridVatTu.list = this.gridVatTu.list.map(e => {
        if(visibleRecords.includes(e.ID_GEN))  e.SELECTED = value
        return e
      })
      this.DSVatTuChecked = this.gridVatTu.list.filter((e) => e.SELECTED == true)
      await this.LAY_DS_VTXUAT()
    },
    async gridXuatBtnXoaClicked(row) {
      this.gridXuat.list = this.gridXuat.list.filter((e) => e.ID_GEN != row.ID_GEN)
      this.gridVatTu.list.find((e) => e.ID_GEN == row.ID_GEN).SELECTED = false
      this.$refs.gridVatTu.grid.refreshColumns()
    },
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    grid_keyPressed(arg) {
      console.log('grid_keyPressed')
      console.log(art)
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    'gridVatTu.list'(val) {
      console.log('watcher this.gridVatTu.list .list')
      if (val.filter((e) => e.ID_GEN == null || e.ID_GEN == '').length) {
        this.gridVatTu.list = this.gridVatTu.list.map((e) => {
          if (e.ID_GEN == null) e.ID_GEN = this.uuidv4()
          if (e.SELECTED == null) e.SELECTED = false
          return e
        })
      }
    }
    // async 'cboTuKho.value'(val) {
    //   await this.cboTuKho_EditValueChanged()
    // }
  },
  async mounted() {
    console.log('frmVatTuTrongKho_Load')
    console.log('this.dtLuuVT')
    console.log(this.dtLuuVT)
    await this.frmVatTuTrongKho_Load()
  }
}
</script>
<style scoped>
.xuly-thietbibaohanh .e-grid tr.e-emptyrow td {
  text-align: center !important;
}
.vattu_box {
  position: relative;
}

.vattu_table {
  position: absolute;
  z-index: 2;
  max-height: 300px;
  background: aliceblue;
  overflow-y: scroll;
  right: 0;
}
</style>
