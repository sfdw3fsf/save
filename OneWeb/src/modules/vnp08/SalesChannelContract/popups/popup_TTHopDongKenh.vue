<template>
<div>
  <breadcrumb :header="header" v-if="!dulieu || dulieu.isPopup == false" />
  <vue-nav>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript: void(0)" title="Hiển thị danh sách" @click="btnLayTT_click">
            <span class="icon one-file-attach"></span>Lấy TT
          </a>
        </li>
        <li>
          <a href="javascript: void(0)" title="Nhập mới" :class="{'disabled': !btn_Enabled.nhapMoi}"
            @click="btnNhapMoi_click">
            <span class="icon one-file-plus"></span>Nhập mới
          </a>
        </li>
        <li>
          <a href="javascript: void(0)" title="Ghi lại" :class="{'disabled': !btn_Enabled.ghiLai}"
            @click="btnCapNhat_click">
            <span class="icon one-save"></span>Ghi lại
          </a>
        </li>

        <li>
          <a href="javascript: void(0)" title="Hủy" :class="{'disabled': !btn_Enabled.huy}" @click="btnHuy_click">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>        
      </ul>
    </div>
  </vue-nav>
  <div class="page-content">
    <div class="row">      
      <div class="box-form">
        <div class="legend-title">Thông tin hợp đồng ký với kênh bán</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Số HĐ/PL <span class="red">(*)</span></div>
              <div class="value">
                <input type="text" class="form-control text" ref="txt_SoHDPL" :title="txt_SoHDPL.value"
                  :value="txt_SoHDPL.value" @change="e => txt_SoHDPL.value = e.target.value">
              </div>
            </div>
          </div>   
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Ngày ký <span class="red">(*)</span></div>
              <div class="value">
                <DatePicker :format="dateFormat" :value-type="'format'" v-model="dtp_NgayKy.value"
                  :input-attr="{required: 'true', title: 'Chưa có ngày ký hợp đồng!'}" @change="onChangeNgayKy">
                </DatePicker>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Người ký <span class="red">(*)</span></div>
              <div class="value">
                <input type="text" class="form-control text" ref="txt_NguoiKy" :title="txt_NguoiKy.value"
                  :value="txt_NguoiKy.value" @change="e => txt_NguoiKy.value = e.target.value">
              </div>
            </div>
          </div>                
        </div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">Loại hợp đồng</div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .5rem !important;" v-model="cbb_LoaiHD.value"
                :options="cbb_LoaiHD.Items.length > 0 ? cbb_LoaiHD.Items.map(e => ({ id: e.loaihdkenh_id, text: e.loaihd_kenh })) : []"
                :disabled="!cbb_LoaiHD.Enabled">
              </select2>
            </div>
          </div>   
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Loại thù lao <span class="red">(*)</span></div>
              <div class="value">
                <SelectExt id="cbb_LoaiThuLao" v-model.trim="cbb_LoaiThuLao.value"
                  :settings="{dropdownParent: $refs['cbb_LoaiThuLao']}" :dataSource="cbb_LoaiThuLao.Items"
                  dataTextField="loaithulao" dataValueField="loaithulao_id" @change="onChangeLoaiThuLao"/>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Tổng % thù lao</div>
              <div class="value">
                <input type="text" class="form-control text" ref="txt_TongTyLeThuLao" :title="txt_TongTyLeThuLao.value"
                  :value="txt_TongTyLeThuLao.value" @change="e => txt_TongTyLeThuLao.value = e.target.value" :disabled="!txt_TongTyLeThuLao.Enabled">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">Tần suất <span class="red">(*)</span></div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .5rem !important;" v-model="cbb_TanSuatHuong.value"
                :options="cbb_TanSuatHuong.Items.length > 0 ? cbb_TanSuatHuong.Items.map(e => ({ id: e.httt_kenh_id, text: e.httt_kenh })) : []"
                :disabled="!cbb_TanSuatHuong.Enabled">
              </select2>
            </div>
          </div>
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w110">Ghi chú</div>
              <div class="value">
                <input type="text" class="form-control text" ref="txt_GhiChu" :title="txt_GhiChu.value"
                  :value="txt_GhiChu.value" @change="e => txt_GhiChu.value = e.target.value">
              </div>
            </div>
          </div>
        </div>    
      </div>          
    </div>
    <div class="row">      
      <div class="box-form">
        <div class="legend-title">Danh sách cơ chế</div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <button class="btn btn-sm btn-primary" @click="btnReload_Click" title="Tải lại lưới">
              <span class="-ap one-refresh"></span>
            </button>
            <button class="btn btn-sm btn-primary" @click="btnThemCoChe_Click" title="Thêm mới cơ chế">
              <span class="-ap icon-plus"></span>
            </button>
          </div>
        </div>
        <DataGrid v-bind:columns="grid_CoChe.header" :commands="grid_CoChe.commands"
          @commandClicked="gridCommand_CoChe"
          :commandColumn="{headerText: 'Thao tác', width: 90, cellCssClass: 'center', firstColumn: true, show: true}"
          v-bind:dataSource="grid_CoChe.list" :showFilter="true" :allowPaging="true" ref="grid_CoChe"
          :enablePagingServer="false" :allowExcelExport="true" :enabledSelectFirstRow="false" panelDataHeight="300px" >
        </DataGrid>
      </div>
    </div>
    <div class="row">
      <div class="box-form">
        <div class="legend-title">Thông tin thanh toán</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Số tài khoản</div>
              <div class="value">
                <input type="text" class="form-control" ref="txt_SoTK" :value="txt_SoTK.value" @change="e => txt_SoTK.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Chủ tài khoản</div>
              <div class="value">
                <input type="text" class="form-control" ref="txt_ChuTK" :value="txt_ChuTK.value" @change="e => txt_ChuTK.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">Ngân hàng</div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .5rem !important;" v-model="cbb_NganHang.value"
                :options="cbb_NganHang.Items.length > 0 ? cbb_NganHang.Items.map(e => ({ id: e.nganhang_id, text: e.ten_nh })) : []"
                :disabled="!cbb_NganHang.Enabled">
              </select2>
            </div>
          </div>
        </div>
        <div class="row">          
          <div class="col-sm-4 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">HT thanh toán</div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .5rem !important;" v-model="cbb_HTTT.value"
                :options="cbb_HTTT.Items.length > 0 ? cbb_HTTT.Items.map(e => ({ id: e.httt_id, text: e.hinhthuc })) : []"
                :disabled="!cbb_HTTT.Enabled">
              </select2>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">ĐV quản lý</div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .5rem !important;" v-model="cbb_DVQL.value"
                :options="cbb_DVQL.Items.length > 0 ? cbb_DVQL.Items.map(e => ({ id: e.donvi_id, text: e.ten_dv })) : []"
                :disabled="!cbb_DVQL.Enabled" @change="onChangeDVQLTT($event)">
              </select2>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">NV chi trả</div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .5rem !important;" v-model="cbb_NVQL.value"
                :options="cbb_NVQL.Items.length > 0 ? cbb_NVQL.Items.map(e => ({ id: e.nhanvien_id, text: e.ten_nv })) : []" @change="onChange_NhanVienQL"
                :disabled="!cbb_NVQL.Enabled">
              </select2>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Mã số thuế</div>
              <div class="value">
                <input type="text" :value="txt_MST.value" @change="v => txt_MST.value = v.target.value"
                  class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Ví VNPT Pay</div>
              <div class="value">
                <input type="text" class="form-control" :value="txt_Vi_VNPT.value" @change="v => txt_Vi_VNPT.value = v.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Ghi chú</div>
              <div class="value">
                <input type="text" class="form-control" :value="txt_GhiChu_TT.value" @change="v => txt_GhiChu_TT.value = v.target.value">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="box-form">
        <div class="legend-title">Danh sách hợp đồng</div>
        <DataGrid v-bind:columns="grid_HopDongKenh.header" v-bind:dataSource="grid_HopDongKenh.list" :showFilter="true"
          :allowPaging="true" ref="grid_HopDongKenh" :enablePagingServer="false" :showColumnCheckbox="false"
          :allowExcelExport="true" @rowSelected="onHopDongSelected" panelDataHeight="300px" >
        </DataGrid>
      </div>
    </div>
  </div>

  <b-modal id="popupComponents" size="huge" :title="popupComponentHeader" :hide-header="popupComponentHeader?false:true"
    hide-footer body-class="modal-body p-0">
    <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
      isPopup="true" v-on="popupComponentEvts"></component>
  </b-modal>
</div>
</template>

<script>
import {
  required,
  maxLength,
  minLength,
  numeric
} from "vuelidate/lib/validators"
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import breadcrumb from '@/components/breadcrumb'
import api from "../API.js";

export default {
  name: 'popup_TTHopDongKenh',
  components: {
    breadcrumb,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
  },
  props: ['dulieu'],
  data() {
    return {
      dateFormat: 'DD/MM/YYYY',
      header: {
        title: "Thông tin Hợp đồng kênh bán",
        list: [
          {
            name: "Thông tin Hợp đồng kênh bán",
            link: {
              name: "Ui.buttons"
            },
          },
        ],
      },
      btn_Enabled: {
        nhapMoi: false,
        ghiLai: false,
        xoa: false,
        huy: false,
      },
      // popup
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
      },
      popupComponentName: '',
      popupComponentHeader: '',
      // TT phieu
      tt_nd: {},
      kenh_id: 0,
      hdkenh_id: 0,
      tvkenh_id: 0,
      dt_ban_id: 0,
      nvql_id: 0,
      // TT hop dong
      txt_SoHDPL: { value: '', Enabled: true },
      dtp_NgayKy: { value: '', Enabled: true },
      txt_NguoiKy: { value: '', Items: [], Enabled: true },
      cbb_LoaiHD: { value: 0, Items: [], Enabled: true },
      cbb_LoaiThuLao: { value: 0, Items: [], Enabled: true },
      txt_TongTyLeThuLao: { value: 0, Enabled: true },
      cbb_TanSuatHuong: { value: 0, Items: [], Enabled: true },
      txt_GhiChu: { value: '', Enabled: true },
      // TT thanh toan
      txt_SoTK: { value: '', Enabled: true },
      txt_ChuTK: { value: '', Enabled: true },
      cbb_NganHang: { value: 0, Items: [], Enabled: true },
      cbb_HTTT: { value: 0, Items: [], Enabled: true },
      cbb_DVQL: { value: 0, Items: [], Enabled: true },
      cbb_NVQL: { value: 0, Items: [], Enabled: true },
      txt_MST: { value: '', Enabled: true },
      txt_Vi_VNPT: { value: '', Enabled: true },
      txt_GhiChu_TT: { value: '', Enabled: true },
      // Table TT hop dong
      cbb_DichVuVT: { Items: [] },
      cbb_LoaiHinhTB: { Items: [] },
      cbb_CongDoan: { Items: [] },
      
      // table     
      grid_CoChe: {
        header: [
          { fieldName: 'hdkcc_id', headerText: 'ID', allowFiltering: true, allowSorting: false, width: 90 },
          {
            fieldName: 'dichvu_id', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <select2 :z-index="2000" class="select2" :ref="'cboDV_' + data.key" :id="'cboDV_' + data.key" @change="onChangeDichVu" v-model="data.dichvu_id"
												:options="cp_dichvu.Items.map(e=>({id:e.dichvuvt_id,text:e.ten_dvvt}))">
											</select2>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_dichvu() {
                      var popupContent = document.getElementById('PopUp___BV_modal_content_');
                      if (popupContent) {
                          popupContent.removeAttribute('tabindex');
                      }
                      return this.parent.cbb_DichVuVT
                    }
                  },
                  methods: {
                    onChangeDichVu(value) {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) {
                        this.parent.grid_CoChe.list[index]['dichvu_id'] = Number(value)
                        var ds_loaihinh = this.parent.cbb_LoaiHinhTB.Items
                        this.parent.grid_CoChe.list[index]['ds_loaihinh'] = ds_loaihinh.filter(e => e.dichvuvt_id == Number(value))
                      }
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'loaihinh_id', headerText: 'Loại hình', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div :ref="'div_cboLH_' + data.key" :id="'div_cboLH_' + data.key">
                      <select2 :z-index="2000" class="select2" :ref="'cboLH_' + data.key" :id="'cboLH_' + data.key" @change="onChangeLoaiTB" v-model="data.loaihinh_id"
												:options="cp_loaihinh && cp_loaihinh.length > 0 ? cp_loaihinh.map(e=>({id:e.loaitb_id,text:e.loaihinh_tb})) : []">
											</select2>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_loaihinh() {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) {
                        return this.$parent.$parent.$parent.grid_CoChe.list[index]['ds_loaihinh']
                      }
                    }
                  },
                  mounted() {
                  },
                  methods: {
                    onChangeLoaiTB(value) {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) {
                        var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                        if (index > -1) {
                          this.$parent.$parent.$parent.grid_CoChe.list[index]['loaihinh_id'] = Number(value)
                          var ds_congdoan = this.$parent.$parent.$parent.cbb_CongDoan.Items
                          this.$parent.$parent.$parent.grid_CoChe.list[index]['ds_congdoan'] = ds_congdoan.filter(e => e.loaitb_id == Number(value))
                        }
                      }
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'congdoan_id', headerText: 'Công đoạn', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <select2 :z-index="2000" class="select2" @change="onChangeCongDoan" v-model="data.congdoan_id"
												:options="cp_congdoan && cp_congdoan.length > 0 ? cp_congdoan.map(e=>({id:e.congdoan_id,text:e.ten_congdoan})) : []">
											</select2>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_congdoan() {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) {
                        return this.$parent.$parent.$parent.grid_CoChe.list[index]['ds_congdoan']
                      }
                    }
                  },
                  methods: {
                    onChangeCongDoan(value) {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.hdkcc_id == this.data.hdkcc_id)
                      if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['congdoan_id'] = Number(value)
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="text" class="form-control text" :value="data.noidung" @change="onChangeNoiDung">
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    onChangeNoiDung(e) {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.hdkcc_id == this.data.hdkcc_id)
                      if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['noidung'] = e.target.value
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'tyle', headerText: 'Tỷ lệ % thù lao', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="text" class="form-control tright" :value="data.tyle" @keypress="isNumber($event)" @change="onChangeTyle">
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    onChangeTyle(e) {
                      if (e.target.value && Number(e.target.value) > 100) {
                        this.$toast.error('Tỷ lệ không được > 100')
                      }
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.hdkcc_id == this.data.hdkcc_id)
                      if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['tyle'] = Number(e.target.value)
                    },
                    isNumber(evt) {
                      evt = evt ? evt : window.event;
                      var charCode = evt.which ? evt.which : evt.keyCode;
                      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                        evt.preventDefault();
                      } else {
                        return true;
                      }
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'sotien', headerText: 'Số tiền được hưởng', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input
                        type="text"
                        v-model="cp_tien"
                        @keypress="isNumber($event)"
                        class="form-control tright"
                      />
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_tien: {
                      get() {
                        return this.data.sotien ? this.numberWithCommas(this.data.sotien) : 0;
                      },
                      set(value) {
                        var replaced = value.replaceAll(",", "");
                        this.data.sotien = Number(replaced);
                        var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.hdkcc_id == this.data.hdkcc_id)
                        if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['sotien'] = Number(replaced)
                      },
                    },
                  },
                  methods: {
                    isNumber(evt) {
                      evt = evt ? evt : window.event;
                      var charCode = evt.which ? evt.which : evt.keyCode;
                      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                        evt.preventDefault();
                      } else {
                        return true;
                      }
                    },
                    numberWithCommas(n) {
                      var parts = n.toString().split(".");
                      return (
                        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
                        (parts[1] ? "." + parts[1] : "")
                      );
                    },
                  },
                },
              };
            },
          },
        ],
        commands: [
          { name: 'save', cssClass: 'pad3 mar5 lh14 icon -ap one-save btn-primary btn-search', iconCss: 'fa fa-times', width: 100 },
          { name: 'delete', cssClass: 'pad3 mar5 lh14 icon -ap icon-delete btn-danger btn-plus', iconCss: 'fa fa-times', width: 100 },
        ],
        list: [],
        selected: {},
        selectedItems: [],
      },
      grid_HopDongKenh: {
        selectionSettings: { type: 'Single' },
        header: [
          { fieldName: 'ma_hd', headerText: 'Số hợp đồng/PL', allowFiltering: true, allowSorting: false, width: 150 },
          { fieldName: 'ngay_ky', headerText: 'Ngày ký', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'nguoiky', headerText: 'Người ký', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'loaithulao', headerText: 'Loại thù lao', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'httt_kenh', headerText: 'Tần suất được hưởng', allowFiltering: true, allowSorting: false, width: 90 },
        ],
        list: [],
        selected: {},
        selectedItems: [],
      }
    }
  },
  async created() {
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
    this.setButton(-1);
  },
  mounted: async function () {
    await this.Load_Form()
  },
  watch: {
    'cbb_LoaiThuLao.value'(val) {
      if (val == 1 || val == '1') {
        // Thù lao tổng
        this.txt_TongTyLeThuLao.Enabled = true
      } else if (val == 2 || val == '2') {
        // Thù lao theo công đoạn
        this.txt_TongTyLeThuLao.Enabled = false
      }
    },
    'grid_CoChe.list'(val) {
      if (this.cbb_LoaiThuLao.value == 2 || this.cbb_LoaiThuLao.value == '2') {
        if (val && val.length > 0) {
          this.txt_TongTyLeThuLao.value = 0
          for (let item of val) {
            this.txt_TongTyLeThuLao.value += Number(item.tyle)
          }
        } else {
          this.txt_TongTyLeThuLao.value = 0
        }     
      }
    }
  },
  methods: {
    Load_Form: async function () {
      try {
        this.loading(true)
        this.tvkenh_id = (this.dulieu && this.dulieu.tvkenh_id) ? this.dulieu.tvkenh_id : (this.$route.query.tvkenh_id ? this.$route.query.tvkenh_id : 0)
        this.kenh_id = (this.dulieu && this.dulieu.kenh_id) ? this.dulieu.kenh_id : (this.$route.query.kenh_id ? this.$route.query.kenh_id : 0)
        this.dt_ban_id  = (this.dulieu && this.dulieu.dt_ban_id) ? this.dulieu.dt_ban_id : (this.$route.query.dt_ban_id ? this.$route.query.dt_ban_id : 0)
        this.nvql_id  = (this.dulieu && this.dulieu.nvql_id) ? this.dulieu.nvql_id : (this.$route.query.nvql_id ? this.$route.query.nvql_id : 0)

        await Promise.all([
          // this.getDanhsach_NguoiKy(),
          this.getDanhsach_LoaiHD(),
          this.getDanhsach_TanXuatHuong(),
          this.getDanhsach_LoaiThuLao(),

          this.getDanhsach_NganHang(),
          this.getDanhsach_HTTT(),
          this.getDanhsach_DonViQL()          
        ])
        await this.getDanhsach_NhanVienChiTra()

        await this.getDanhsach_HopDongKenhBan()
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },    
    //Lấy danh sách người ký
    getDanhsach_NguoiKy: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsNguoiKy(this.axios, input));
      this.cbb_NguoiKy.Items = data
    },
    //Lấy danh sách loại hợp đồng
    getDanhsach_LoaiHD: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsLoaiHopDong(this.axios, input));
      this.cbb_LoaiHD.Items = data
    },
    //Lấy danh sách tần suất được hưởng
    getDanhsach_TanXuatHuong: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsTanXuatHuong(this.axios, input));
      this.cbb_TanSuatHuong.Items = data;
    },  
    //Lấy danh sách loại thù lao
    getDanhsach_LoaiThuLao: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsLoaiThuLao(this.axios, input));
      this.cbb_LoaiThuLao.Items = data;
    },  
    onChangeLoaiThuLao: async function () {
      try {
        this.loading(true)
        this.txt_TongTyLeThuLao.value = 0
        await Promise.all([
          this.getDanhsach_DichVuVT(),
          this.getDanhsach_LoaiHinhTB(),
          this.getDanhsach_CongDoan(),    
        ])
        await this.getDanhsach_CoChe()   
      } catch (e) {
        
      } finally {
        this.loading(false)
      }      
    },
    // Lấy danh sách ngân hàng
    getDanhsach_NganHang: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsNganHang(this.axios, input));
      this.cbb_NganHang.Items = data;
    },
    // Lấy danh sách hình thức TT
    getDanhsach_HTTT: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsHinhThucThanhToan(this.axios, input));
      this.cbb_HTTT.Items = data;
    },
    // Lấy danh sách DVQL
    getDanhsach_DonViQL: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsDonViQL(this.axios, input));
      this.cbb_DVQL.Items = data;
    },
    onChangeDVQLTT: function () {
      this.getDanhsach_NhanVienChiTra()
    },
    // Lấy danh sách nhân viên chi trả
    getDanhsach_NhanVienChiTra: async function () {
      let input = { donvi_id: this.cbb_DVQL.value }
      let data = this.GetDataList(await api.dsNhanVienChiTra(this.axios, input));
      this.cbb_NVQL.Items = data;
    },
    onChange_NhanVienQL: async function () {
      try {
        this.loading(true)
        await Promise.all([
          this.getDanhsach_DichVuVT(),
          this.getDanhsach_LoaiHinhTB(),
          this.getDanhsach_CongDoan(),
        ])
        await this.getDanhsach_CoChe()   
      } catch (e) {
        
      } finally {
        this.loading(false)
      }      
    },
    // Lấy danh sách dịch vụ
    getDanhsach_DichVuVT: async function () {
      let input = {
        "hdkenh_id": this.hdkenh_id,
        "dt_ban_id": this.dt_ban_id,
        "loaithulao_id": this.cbb_LoaiThuLao.value,
        "nvql_id": this.nvql_id
      }
      let data = this.GetDataList(await api.dsDichVuVT(this.axios, input));
      this.cbb_DichVuVT.Items = data;
    },
    // Lấy danh sách loại hình tb
    getDanhsach_LoaiHinhTB: async function () {
      let input = {
        "hdkenh_id": this.hdkenh_id,
        "dt_ban_id": this.dt_ban_id,
        "loaithulao_id": this.cbb_LoaiThuLao.value,
        "nvql_id": this.nvql_id,
      }
      let data = this.GetDataList(await api.dsLoaiHinhTB(this.axios, input));
      this.cbb_LoaiHinhTB.Items = data;
    },
    // Lấy danh sách công đoạn
    getDanhsach_CongDoan: async function () {
      let input = {
        "hdkenh_id": this.hdkenh_id,
        "dt_ban_id": this.dt_ban_id,
        "loaithulao_id": this.cbb_LoaiThuLao.value,
        "nvql_id": this.nvql_id,
      }
      let data = this.GetDataList(await api.dsCongDoan(this.axios, input));
      this.cbb_CongDoan.Items = data;
    },
    // Lấy danh sách cơ chế của hợp đồng
    getDanhsach_CoChe: async function () {
      this.grid_CoChe.list = []
      let input = {
        hdkenh_id: this.hdkenh_id
      }
      let data = this.GetDataList(await api.dsCoChe(this.axios, input));
      if (data && data.length > 0) {
        for (let item of data) {
          item.key = Math.random().toString(36).slice(2, 7)
          item.ds_loaihinh = this.cbb_LoaiHinhTB.Items.filter(e => e.dichvuvt_id == item.dichvu_id)
          item.ds_congdoan = this.cbb_CongDoan.Items.filter(e => e.loaitb_id == item.loaihinh_id)
        }
      }
      this.grid_CoChe.list = data 
    },
    btnReload_Click: function () {
      this.getDanhsach_CoChe()
    },
    btnThemCoChe_Click: function () {
      var row = {
        tvkenh_id: this.tvkenh_id,
        hdkenh_id: this.hdkenh_id,
        dichvu_id: 0,
        loaihinh_id: 0,
        congdoan_id: 0,
        noidung: '',
        sotien: 0,
        tyle: 0,
        tong_thulao: this.txt_TongTyLeThuLao.value,
        dt_ban_id: this.dt_ban_id,
        loaithulao_id: this.cbb_LoaiThuLao.value,
        nvql_id: this.nvql_id,
        hdkcc_id: 0,
        key: Math.random().toString(36).slice(2, 7),
        ds_loaihinh: [],
        ds_congdoan: [],
      }
      var ds = [row]
      ds = ds.concat(this.grid_CoChe.list)
      this.grid_CoChe.list = ds
      console.log('btnThemCoChe_Click', ds)
    },
    // Button tren grid co che hop dong
    gridCommand_CoChe: async function (type, dataItem) {
      try {
        this.loading(true)
        if (type == 'save') {
          let input = {
            "tvkenh_id": this.tvkenh_id,
            "hdkenh_id": this.hdkenh_id,
            "noidung": dataItem.noidung,
            "congdoan_id": dataItem.congdoan_id,
            "dichvu_id": dataItem.dichvu_id,
            "loaihinh_id": dataItem.loaihinh_id,
            "tyle": dataItem.tyle,
            "sotien": dataItem.sotien,
            "tong_thulao": this.txt_TongTyLeThuLao.value,
            "dt_ban_id": this.dt_ban_id,
            "loaithulao_id": this.cbb_LoaiThuLao.value,
            "nvql_id": this.nvql_id,
            "hdkcc_id": dataItem.hdkcc_id,
          }
          var check_coche = this.GetData(await api.kiemTraDauVaoCoChe(this.axios, input));
          if (!check_coche) {
            this.$toast.error('Có lỗi kiểm tra cơ chế!')
            return
          }
          if (check_coche.codeError == 200 || check_coche.codeError == '200') {
            input.hdk_id_update = check_coche.hdk_id_update
            let data = ''
            if (!dataItem.hdkcc_id) {
              data = this.GetData(await api.themMoiCoChe(this.axios, input));
            } else {
              data = this.GetData(await api.capNhatCoChe(this.axios, input));
            }

            if (data == "OK" || data == "ok") {
              this.$toast.success('Ghi lại cơ chế thành công!');
              this.getDanhsach_CoChe()
            }
          } else if (check_coche.codeError == 300 || check_coche.codeError == '300') {
            this.$bvModal.msgBoxConfirm(check_coche.message, {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý',
            }).then(async v => {
              if (v) {       
                input.hdk_id_update = check_coche.hdk_id_update     
                let data = ''
                if (!dataItem.hdkcc_id) {
                  data = this.GetData(await api.themMoiCoChe(this.axios, input));
                } else {
                  data = this.GetData(await api.capNhatCoChe(this.axios, input));
                }

                if (data == "OK" || data == "ok") {
                  this.$toast.success('Ghi lại cơ chế thành công!');
                  this.getDanhsach_CoChe()
                }
              }
            });
          } else {
            this.$toast.error(check_coche.message)
          }
        } else if (type == 'delete') {
          this.$bvModal.msgBoxConfirm("Bạn có muốn xóa cơ chế đang chọn không?", {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Không đồng ý',
          }).then(async v => {
            if (v) {
              let input = {
                hdkcc_id: dataItem.hdkcc_id
              }
              let data = this.GetData(await api.xoaCoChe(this.axios, input));
              if (data == "OK" || data == "ok") {
                this.$toast.success('Xóa cơ chế thành công!');
                this.getDanhsach_CoChe()
              }
            }
          });
        }
      } catch (e) {} finally {
        this.loading(false)
      }
    },
    //Lấy danh sách hợp đồng đã ký
    getDanhsach_HopDongKenhBan: async function () {
      // this.kenh_id = 430920
      let data = this.GetDataList(await api.dsHopDongKenhBan(this.axios, this.kenh_id));
      this.grid_HopDongKenh.list = data
      if (this.grid_HopDongKenh.list.length == 0) {
        this.setButton(1)
      }
    },
    // click row table
    //Click chọn hopdong
    onHopDongSelected: async function (args) {
      console.log('onHopDongSelected', args)
      if (args && args.data) {
        this.grid_HopDongKenh.selected = args.data;
        this.setButton(2);

        await this.HienThi_ThongTin_HopDong(args.data)
      }
    },
    HienThi_ThongTin_HopDong: async function (data) {
      try {        
        this.loading(true)
        this.kenh_id = data['kenh_id']
        this.hdkenh_id = data['hdkenh_id']
        
        // Thông tin hợp đồng ký với kênh bán
        this.txt_SoHDPL.value = data['ma_hd'] ? data['ma_hd'] : ''
        this.dtp_NgayKy.value = data['ngay_ky'] ? data['ngay_ky'] : ''
        this.txt_NguoiKy.value = data['nguoiky'] ? data['nguoiky'] : ``
        this.cbb_LoaiHD.value = data['loaihd_id'] ? data['loaihd_id'] : 0
        this.cbb_LoaiThuLao.value = data['loaithulao_id'] ? data['loaithulao_id'] : 0
        this.txt_TongTyLeThuLao.value = data['tong_thulao'] ? data['tong_thulao'] : 0
        this.cbb_TanSuatHuong.value = data['htttk_id'] ? data['htttk_id'] : 0
        this.txt_GhiChu.value = data['ghichu'] ? data['ghichu'] : ''

        // TT thanh toan
        this.txt_SoTK.value = data['stk'] ? data['stk'] : ''
        this.txt_ChuTK.value = data['chutaikhoan'] ? data['chutaikhoan'] : ''
        this.cbb_NganHang.value = data['nganhang_id'] ? data['nganhang_id'] : 0
        this.cbb_HTTT.value = data['httt_id'] ? data['httt_id'] : 0
        this.cbb_DVQL.value = data['donvi_qltt'] ? data['donvi_qltt'] : 0
        this.cbb_NVQL.value = data['nv_chitra_id'] ? data['nv_chitra_id'] : 0
        this.txt_MST.value = data['mst_tt'] ? data['mst_tt'] : ''
        this.txt_Vi_VNPT.value = data['vi_vnpay'] ? data['vi_vnpay'] : ''
        this.txt_GhiChu_TT.value = data['ghichu_tt'] ? data['ghichu_tt'] : ''

        await Promise.all([
          this.getDanhsach_NhanVienChiTra(),

          this.getDanhsach_DichVuVT(),
          this.getDanhsach_LoaiHinhTB(),
          this.getDanhsach_CongDoan(),
        ])
        await this.getDanhsach_CoChe()   
      } catch (e) {
        this.$toast.error('Lỗi hiển thị thông tin hợp đồng: ' + e)
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    // Change value
    onChangeNgayKy: function (value_) {
      if (!moment(value_, 'DD/MM/YYYY').isValid()) {
        this.dtp_NgayKy.value = '01/01/2000'
      } else {
        this.dtp_NgayKy.value = value_
      }
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    clear: function () {
      this.hdkenh_id = 0
      
      // TT hop dong
      this.txt_SoHDPL.value = ''
      this.dtp_NgayKy.value = ''
      this.txt_NguoiKy.value = ``
      this.cbb_LoaiHD.value = ''
      this.cbb_LoaiThuLao.value = 0
      this.txt_TongTyLeThuLao.value = 0
      this.cbb_TanSuatHuong.value = ''
      this.txt_GhiChu.value = ''

      // TT thanh toan
      this.txt_SoTK.value = ''
      this.txt_ChuTK.value = ''
      this.cbb_NganHang.value = 0
      this.cbb_HTTT.value = 0
      this.cbb_DVQL.value = 0
      this.cbb_NVQL.value = 0
      this.txt_MST.value = ''
      this.txt_Vi_VNPT.value = ''
      this.txt_GhiChu_TT.value = ''
      
      // Table TT hop dong
      this.cbb_DichVuVT.value = ''
      this.cbb_LoaiHinhTB.value = ''
      this.cbb_CongDoan.value = ''
     
      this.grid_CoChe.list = []
      this.grid_HopDongKenh.list = []
    },
    khoamo_form: function (loai) {
      // Thông tin đề xuất ký hợp đồng     
      
    },
    setButton: function (kieu) {
      this.btn_Enabled = {
        nhapMoi: false,
        ghiLai: false,
        xoa: false,
        huy: false,
      };
      if (kieu === -1) { //Bat dau  
        this.btn_Enabled.nhapMoi = true;
        this.btn_Enabled.ghiLai = false;
        this.btn_Enabled.huy = false;
      } else if (kieu === 1) { //Them moi
        this.clear();
        this.btn_Enabled.nhapMoi = false;
        this.btn_Enabled.ghiLai = true;
        this.btn_Enabled.huy = true;
      } else if (kieu === 2) { //Cap nhat
        this.btn_Enabled.nhapMoi = true;
        this.btn_Enabled.ghiLai = true;
        this.btn_Enabled.huy = true;
        this.btn_Enabled.xoa = true;
      }
    },
    // Button click
    btnLayTT_click: function () {
      this.Load_Form()
    },
    btnNhapMoi_click: function () {
      //Nhấn nhập mới
      this.setButton(1);
    },
    btnCapNhat_click: async function () {
      try {
        // Nhấn ghi lại
        this.loading(true)
        if (!this.validate()) return
        await this.CapNhat()
      } catch (e) {
        this.$toast.error('Lỗi cập nhật: ' + e)
      } finally {
        this.loading(false)
      }
    },
    CapNhat: async function () {
      try {
        var input = {}
        var response = {}
        var ds_coche = this.grid_CoChe.list && this.grid_CoChe.list.length > 0 ? this.grid_CoChe.list.map(e => ({
          "noidung": e.noidung,
          "congdoan_id": e.congdoan_id,
          "dichvu_id": e.dichvu_id,
          "loaihinh_id": e.loaihinh_id,
          "tyle": e.tyle,
          "sotien": e.sotien,
        })) : []
        if (!this.btn_Enabled.nhapMoi) {
          input = {
            kenh_id: this.kenh_id,
            ma_hd: this.txt_SoHDPL.value,
            ngay_ky: this.dtp_NgayKy.value,
            nguoiky: this.txt_NguoiKy.value,
            loaihd_id: this.cbb_LoaiHD.value,
            loaithulao_id: this.cbb_LoaiThuLao.value,
            tong_thulao: Number(this.txt_TongTyLeThuLao.value),
            htttk_id: this.cbb_TanSuatHuong.value,
            ghichu: this.txt_GhiChu.value,
            stk: this.txt_SoTK.value,
            chutaikhoan: this.txt_ChuTK.value,    
            nganhang_id: this.cbb_NganHang.value,
            httt_id: this.cbb_HTTT.value,   
            donvi_qltt: this.cbb_DVQL.value,
            nv_chitra_id: this.cbb_NVQL.value,
            vi_vnpay: this.txt_Vi_VNPT.value,
            mst_tt: this.txt_MST.value,
            ghichu_tt: this.txt_GhiChu_TT.value,
            coche_data: ds_coche
          }
          response = await api.themMoiHopDongKenh(this.axios, input)
        } else {
          input = {
            hdkenh_id: this.hdkenh_id,
            kenh_id: this.kenh_id,
            ma_hd: this.txt_SoHDPL.value,
            ngay_ky: this.dtp_NgayKy.value,
            nguoiky: this.txt_NguoiKy.value,
            loaihd_id: this.cbb_LoaiHD.value,
            loaithulao_id: this.cbb_LoaiThuLao.value,
            tong_thulao: Number(this.txt_TongTyLeThuLao.value),
            htttk_id: this.cbb_TanSuatHuong.value,
            ghichu: this.txt_GhiChu.value,
            stk: this.txt_SoTK.value,
            chutaikhoan: this.txt_ChuTK.value,    
            nganhang_id: this.cbb_NganHang.value,
            httt_id: this.cbb_HTTT.value,   
            donvi_qltt: this.cbb_DVQL.value,
            nv_chitra_id: this.cbb_NVQL.value,
            vi_vnpay: this.txt_Vi_VNPT.value,
            mst_tt: this.txt_MST.value,
            ghichu_tt: this.txt_GhiChu_TT.value,
            coche_data: ds_coche
          }
          response = await api.capNhatHopDongKenh(this.axios, input)
        }        
        console.log('CapNhat',response)
        if (response && response.data.error_code == 'BSS-00000000') {
          if (response.data.data == 'ok' || response.data.data == 'OK') {
            var message = !this.btn_Enabled.nhapMoi ? 'Thêm mới' : 'Ghi lại'
            this.$toast.success(message + ' hợp đồng kênh thành công!')
            this.setButton(2)
            this.getDanhsach_HopDongKenhBan()
          } else if (response.data.data) {
            this.$toast.error(response.data.data)
          }
        } else {
          this.$toast.error('Lỗi ghi lại: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        }
      } catch (e) {
        this.$toast.error('Lỗi ghi lại: ' + e)
      }
    },
    validate: function () {     
      if (!this.txt_SoHDPL.value) {
        this.$toast.error('Số HĐ/PL không được để trống!')
        return false
      }

      if (!this.dtp_NgayKy.value) {
        this.$toast.error('Ngày ký không được để trống!')
        return false
      }

      if (!this.txt_NguoiKy.value || this.txt_NguoiKy.value.toString().trim() == '') {
        this.$toast.error('Người ký không được để trống!')
        return false
      }

      if (!this.nvql_id) {
        this.$toast.error('Không có thông tin nhân viên quản lý, hãy liên hệ admin!')
        return false
      }

      if (!this.cbb_LoaiThuLao.value || this.cbb_LoaiThuLao.value.toString().trim() == '') {
        this.$toast.error('Bạn chưa chọn loại thù lao!')
        return false
      }

      if (!this.cbb_TanSuatHuong.value || this.cbb_TanSuatHuong.value.toString().trim() == '') {
        this.$toast.error('Bạn chưa chọn tần suất hưởng!')
        return false
      }
      
      return true
    },
    btnHuy_click: async function () {
      try {
        // Nhấn Hủy
        this.loading(true)
        this.setButton(-1);
      } catch (e) {
        this.$toast.error('Lỗi hủy: ' + e)
      } finally {
        this.loading(false)
      }
    },
    btnXoa_click: async function () {
      try {
        // Nhấn Xóa
        this.loading(true)
        this.$bvModal.msgBoxConfirm("Bạn có muốn xóa hợp đồng này không?", {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Không đồng ý',
        }).then(async v => {
          if (v) {
            let input = {
              hdkenh_id: this.hdkenh_id
            }
            let data = this.GetData(await api.xoaHopDong(this.axios, input));
            if (data == 'ok' || data == 'OK') {
              this.$toast.success('Xóa hợp đồng thành công!');
              this.getDanhsach_HopDongKenhBan()
            }
          }
        });
      } catch (e) {
        this.$toast.error('Lỗi xóa dữ liệu: ' + e)
      } finally {
        this.loading(false)
      }
    },
    
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      try {
        if (!val) {
          this.ClosePopup('popupComponents')
          return
        }
        this.loading(true)

        console.log('popupClosed', this.popupComponentName, val)
        switch (this.popupComponentName) {
          case 'popup_TTThanhToan':
            break
        }
      } catch (e) {
        console.log('popupClosed', e)
      } finally {
        this.loading(false)
      }
    }
  },
}
</script>

<style scoped src="../SalesChannelContract.scss"></style>
<style>
.w110 {
  width: 110px !important;
}
.select2 {
  width: 100% !important;
}

.custom-row {
  display: flex !important;
  flex-direction: row !important;
}

.pad2 {
  padding-left: .5rem !important;
  padding-right: .5rem !important;
}

@media (min-width: 1000px) {
  .custom-form-50 {
    width: calc(50%) !important;
  }
}

@media (max-width: 999px) {
  .custom-form-50 {
    width: calc(100%) !important;
  }
}
</style>