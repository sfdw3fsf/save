<template>
  <div>
    <div :class="{ 'modal-content popup-box': type == 'modal' }">
      <breadcrumb v-if="type == 'view'" :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="(item, index) in menus" :class="{ disable: !item.enabled, 'btn-action': item.enabled }">
            <a @click.prevent="onMenuClicked(item)"><span :class="`${item.icon} icon`"></span>{{ item.name }}</a>
          </li>
        </ul>
      </div>
      <div class="page-content">
        <div class="row">
          <div class="col-sm-2 col-12">
            <div class="box-form" style="max-height: 1400px; overflow: scroll;">
              <div class="legend-title">Danh sách đơn vị</div>
              <div class="tree-plus">
                <span class="search-input e-input-group e-control-wrapper">
                  <input :disabled="!txtTreeDonViSearch.enabled" class="e-filtertext e-input highlight" placeholder="Nhập tên đơn vị cần tìm kiếm" v-model="txtTreeDonViSearch.value" @keypress.enter="onTxtTreeDonViSearchEntered" />
                  <span class="e-clear-icon e-clear-icon-hide" aria-label="close" role="button"></span>
                </span>
                <ejs-treeview ref="treeDonVi" @nodeSelected="onTreeDonviNodeSelected" :selectedNodes="treeDonVi.selected" :fields="{ dataSource: treeDonVi.list, id: 'donvi_id', text: 'ten_dv', child: 'children' }"></ejs-treeview>
              </div>
            </div>
          </div>
          <div class="col-sm-10">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="box-form">
                  <div class="legend-title">Thông tin nhân viên</div>
                  <div class="row">
                    <div class="col-sm-9 col-12">
                      <div class="row">
                        <div class="col-sm-6 col-12">
                          <div class="info-row">
                            <div class="key w80">
                              Mã NV
                              <span class="required">*</span>
                            </div>
                            <div class="value">
                              <input :disabled="!txtMaNV.enabled" class="e-input highlight" type="text" v-model="txtMaNV.value" @keypress.enter="timKiemNhanVienBy('ma_nv')" />
                            </div>
                          </div>
                        </div>
                        <div class="col-sm-6 col-12">
                          <div class="info-row">
                            <div class="key w80">Vị trí</div>
                            <div class="value">
                              <!-- <input type="text" class="form-control italic red readonly" readonly v-model="tiepNhanInfo.trangthai_tb"> -->
                              <!-- <SelectExt :disabled="!cboViTri.enabled" v-model="cboViTri.value" :dataSource="cboViTri.list" dataTextField="vitri" dataValueField="vitri_id" /> -->
                              <input :disabled="!txtViTri.enabled" class="e-input" type="text" v-model="txtViTri.value" />
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="info-row">
                        <div class="key w80">
                          Tên NV
                          <span class="required">*</span>
                        </div>
                        <div class="value">
                          <!-- <ejs-autocomplete ref="p_acc_somay" :dataSource="acTxtTenNV.list" :fields="{ value: 'ten_nv' }" v-model="acTxtTenNV.value" :filtering="acTxtTenNVChange" autofill="true" filterType="Contains"></ejs-autocomplete> -->
                          <input :disabled="!txtTenNV.enabled" class="e-input highlight" type="text" v-model="txtTenNV.value" @keypress.enter="timKiemNhanVienBy('ten_nv')" />
                        </div>
                      </div>
                      <div class="info-row">
                        <div class="key w80">
                          Tên TN
                          <span class="required">*</span>
                        </div>
                        <div class="value">
                          <input :disabled="!txtTenTN.enabled" class="e-input" type="text" v-model="txtTenTN.value" />
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-sm-6 col-12">
                          <div class="info-row">
                            <div class="key w80">
                              Ngày sinh
                              <span class="required">*</span>
                            </div>
                            <div class="value">
                              <vue-date :disabled="!dpNgaySinh.enabled" v-model="dpNgaySinh.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                              <!-- <ejs-datepicker v-model="ngaySinh"></ejs-datepicker> -->
                            </div>
                          </div>
                        </div>
                        <div class="col-sm-6 col-12">
                          <div class="info-row">
                            <div class="key w80">
                              Điện thoại
                              <span class="required">*</span>
                            </div>
                            <div class="value">
                              <input :disabled="!txtSdt.enabled" class="e-input" type="number" v-model="txtSdt.value" />
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="info-row">
                        <div class="key w80">
                          Địa chỉ
                          <span class="required">*</span>
                        </div>
                        <div class="value">
                          <div class="input-more-button">
                            <button class="btn" v-b-modal.popupDiaChiNhanVien>
                              <span class="-ap icon-more_horiz"></span>
                            </button>
                            <input :disabled="!txtDiaChi.enabled" class="e-input" type="text" v-model="txtDiaChi.value" />
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-sm-6 col-12">
                          <div class="info-row">
                            <div class="key w80">Tiền ký quỹ</div>
                            <div class="value">
                              <ejs-numerictextbox locale="vi-VN" format="n" :showSpinButton="false" v-model="txtTienKyQuy.value" />
                            </div>
                          </div>
                        </div>
                        <div class="col-sm-6 col-12">
                          <div class="info-row">
                            <div class="key w80">Mail VNPT</div>
                            <div class="value">
                              <input :disabled="!txtMailVNPT.enabled" type="text" class="form-control" v-model="txtMailVNPT.value" />
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="info-row">
                        <div class="key w80">
                          Đơn vị
                          <span class="required">*</span>
                        </div>
                        <div class="value">
                          <SelectExt :disabled="!cboDonVi.enabled" v-model="cboDonVi.value" :dataSource="cboDonVi.list" dataTextField="ten_ht" dataValueField="donvi_id" />
                        </div>
                      </div>
                      <div class="info-row">
                        <div class="key w80">Đơn vị DL</div>
                        <div class="value">
                          <SelectExt :disabled="!cboDonViDL.enabled" v-model="cboDonViDL.value" :dataSource="cboDonViDL.list" dataTextField="ten_dv" dataValueField="donvi_id" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3 col-12">
                      <div class="check-action mart7 marb10">
                        <input type="checkbox" class="check" v-model="cboGioiTinh.value" />
                        <span class="name">Nam</span>
                      </div>
                      <div class="info-row">
                        <div class="key">Chữ ký</div>
                        <div class="value w30">
                          <button class="btn btn-second" @click="btnUploadChuKyFileClicked">
                            <span class="-ap icon-more_horiz"></span>
                          </button>
                        </div>
                      </div>
                      <div
                        class="form-control nocorner"
                        style="
            height: 200px;
            display: flex;
            align-items: center;
            justify-content: center;
          "
                      >
                        <img :src="fpChuKy.value" alt="Chữ ký" />
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-3 col-12">
                      <div class="info-row">
                        <div class="key w80">Số thẻ</div>
                        <div class="value">
                          <input :disabled="!txtSoThe.enabled" type="text" class="form-control" v-model="txtSoThe.value" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3 col-12">
                      <div class="info-row">
                        <div class="key w80">Mã kênh</div>
                        <div class="value">
                          <input :disabled="!txtMaKenh.enabled" type="text" class="form-control" v-model="txtMaKenh.value" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Hợp đồng</div>
                        <div class="value">
                          <SelectExt :disabled="!cboHopDong.enabled" v-model="cboHopDong.value" :dataSource="cboHopDong.list" dataTextField="hinhthuc_hd" dataValueField="hthd_id" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-4 col-12">
                      <div class="info-row">
                        <div class="key w80">User ví</div>
                        <div class="value">
                          <input :disabled="!txtUserVi.enabled" type="text" class="form-control" v-model="txtUserVi.value" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-8 col-12">
                      <div class="info-row">
                        <div class="key w80">Ghi chú</div>
                        <div class="value">
                          <input :disabled="!txtGhiChu.enabled" type="text" class="form-control" v-model="txtGhiChu.value" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="legend-title mart10">Đơn vị đã thực hiện</div>
                  <div class="info-row">
                    <div class="key w80">Loại đơn vị</div>
                    <div class="value">
                      <SelectExt :disabled="!cboLoaiDV.enabled" v-model="cboLoaiDV.value" :dataSource="cboLoaiDV.list" dataTextField="ten_loaidv" dataValueField="loaidv_id" />
                    </div>
                  </div>
                </div>
                <div class="box-move-select-table">
                  <div class="box-col box-form">
                    <div class="legend-title">Đơn vị đã gán</div>
                    <DataGrid panelDataHeight="232" v-bind:columns="gridDonViDaGan.header" v-bind:dataSource="gridDonViDaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="gridDonViDaGan" />
                  </div>
                  <div class="actions">
                    <button class="btn" @click="donViMTL">
                      <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn" @click="donViMTR">
                      <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                  </div>
                  <div class="box-col box-form">
                    <div class="legend-title">Đơn vị chưa gán</div>
                    <DataGrid panelDataHeight="232" v-bind:columns="gridDonViChuaGan.header" v-bind:dataSource="gridDonViChuaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="gridDonViChuaGan" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="box-move-select-table">
                  <div class="box-col box-form">
                    <div class="legend-title">Loại nhân viên đã gán</div>
                    <DataGrid panelDataHeight="190" v-bind:columns="gridLoaiNhanVienDaGan.header" v-bind:dataSource="gridLoaiNhanVienDaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="gridLoaiNhanVienDaGan" />
                  </div>
                  <div class="actions">
                    <button class="btn" @click="loaiNhanVienMTL">
                      <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn" @click="loaiNhanVienMTR">
                      <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                  </div>
                  <div class="box-col box-form">
                    <div class="legend-title">Loại nhân viên chưa gán</div>
                    <DataGrid panelDataHeight="190" v-bind:columns="gridLoaiNhanVienChuaGan.header" v-bind:dataSource="gridLoaiNhanVienChuaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="gridLoaiNhanVienChuaGan" />
                  </div>
                </div>
                <div class="box-move-select-table">
                  <div class="box-col box-form">
                    <div class="legend-title">Nhân viên quản lý đã gán</div>
                    <DataGrid panelDataHeight="232" v-bind:columns="gridNhanVienQLDaGan.header" v-bind:dataSource="gridNhanVienQLDaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="gridNhanVienQLDaGan" />
                  </div>
                  <div class="actions">
                    <button class="btn" @click="nhanVienQLMTL">
                      <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn" @click="nhanVienQLMTR">
                      <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                  </div>
                  <div class="box-col box-form">
                    <div class="legend-title">Nhân viên quản lý chưa gán</div>
                    <DataGrid panelDataHeight="232" v-bind:columns="gridNhanVienQLChuaGan.header" v-bind:dataSource="gridNhanVienQLChuaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="gridNhanVienQLChuaGan" />
                  </div>
                </div>
              </div>
              <div class="box-form">
                <DataGrid v-bind:columns="gridNhanVien.header" v-bind:dataSource="gridNhanVien.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="gridNhanVien" @selectedRowChanged="gridNhanVienOnSelectedRowChanged" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ejs-dialog :isModal="true" :enableResize="true" :resizeHandles="['All']" :visible="false" ref="nhanVienViTriDialog" :header="'Gán nhân viên vị trí'" showCloseIcon="true" target=".main-wrapper" width="60%" allowDragging="true" @close="nhanVienViTriDialogOnClose">
      <div>
        <ModalNhanVienViTri v-if="nhanVienViTriDialog.isVisible && gridNhanVien.value && gridNhanVien.value.nhanvien_id != -1" :nhanVienId="gridNhanVien.value.nhanvien_id" :tenNhanVien="gridNhanVien.value.ten_nv" @save-success="onModalNhanVienViTriSaveSuccess" />
      </div>
    </ejs-dialog>

    <ejs-dialog :isModal="true" :enableResize="true" :resizeHandles="['All']" :visible="false" ref="khuVucDialog" :header="'Danh sách các khu vực của nhân viên đang phụ trách quản lý'" showCloseIcon="true" target=".main-wrapper" width="60%" allowDragging="true" @close="khuVucDialogOnClose">
      <div>
        <modal-khu-vuc v-if="khuVucDialog.isVisible && gridNhanVien.value && gridNhanVien.value.nhanvien_id != -1" v-bind="{ vma_nv: gridNhanVien.value.ma_nv }" />
        <!-- <modal-nhan-vien-vi-tri v-if="khuVucDialog.isVisible && gridNhanVien.value && gridNhanVien.value.nhanvien_id != 0" :nhanVienId="gridNhanVien.value.nhanvien_id" :tenNhanVien="gridNhanVien.value.ten_nv" @save-success="onModalNhanVienViTriSaveSuccess" /> -->
      </div>
    </ejs-dialog>
    <modal-chon-dia-chi :isStrictMode="false" :data="{}" modalId="popupDiaChiNhanVien" @xacnhan="onModalChonDiaChiSuccess" />
  </div>
</template>

<script>
import Vue from 'vue'
import lodash from 'lodash'
import { DashboardLayoutPlugin } from '@syncfusion/ej2-vue-layouts'
import * as moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import ModalNhanVienViTri from './modals/ModalNhanVienViTri'
import ModalKhuVuc from './modals/ModalKhuVuc'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import api from './api/new-api'
import ccbsApi from './api/ccbs-api'

Vue.use(DashboardLayoutPlugin)

export default {
  components: { breadcrumb, ModalNhanVienViTri, ModalKhuVuc, ModalChonDiaChi },
  props: {
    type: {
      type: String,
      default: 'view'
    },
    vhuonggiao_id: {
      type: [String, Number],
      required: false,
      default: null
    }
  },
  async mounted() {
    await this.init()
  },
  data() {
    return {
      count: 10,
      spacing: [10, 10],
      resizableHandles: ['e-south-east', 'e-east', 'e-west', 'e-north', 'e-south'],
      header: {
        title: 'Nhân viên',
        list: [{ name: '<<<', link: { name: 'Ui.cards' } }]
      },
      menus: [
        { id: 'nhap_moi', name: 'Nhập mới', icon: 'con one-file-plus', enabled: true },
        { id: 'ghi_lai', name: 'Ghi lại', icon: 'icon one-save', enabled: true },
        { id: 'huy', name: 'Hủy', icon: 'icon nc-icon-glyph ui-1_circle-remove', enabled: true },
        { id: 'xoa', name: 'Xóa', icon: 'icon one-trash', enabled: true },
        { id: 'tim_kiem', name: 'Tìm kiếm', icon: 'icon one-search', enabled: true },
        { id: 'gan_vi_tri', name: 'Gán vị trí', icon: 'icon nc-icon-glyph location_crosshair', enabled: true },
        { id: 'khu_vuc', name: 'Khu vực', icon: 'icon nc-icon-glyph location_position-pin', enabled: true }
      ],
      txtTreeDonViSearch: { enabled: true, value: null },
      treeDonVi: { flatList: [], list: [], selected: [] },
      txtMaNV: { enabled: true, value: '' },
      txtViTri: { enabled: true, value: null },
      cboGioiTinh: { enabled: true, list: [], value: null },
      txtTenNV: { enabled: true, value: '' },
      acTxtTenNV: { enabled: true, value: '' },
      txtTenTN: { enabled: true, list: [], value: '' },
      dpNgaySinh: { enabled: true, value: null },
      txtSdt: { enabled: true, value: '' },
      txtDiaChi: { enabled: true, value: '' },
      txtTienKyQuy: { enabled: true, value: 0 },
      txtMailVNPT: { enabled: true, value: null },
      cboDonVi: { enabled: true, list: [], value: null },
      cboDonViDL: { enabled: true, list: [], value: null },
      txtSoThe: { enabled: true, value: null },
      txtMaKenh: { enabled: true, value: null },
      cboHopDong: { enabled: true, list: [], value: null },
      txtUserVi: { enabled: true, value: null },
      txtGhiChu: { enabled: true, value: null },
      cboLoaiDV: { enabled: true, list: [], value: null },
      fpChuKy: { enabled: true, value: null },
      gridNhanVien: {
        header: [
          { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, width: 200 },
          { fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'ten_dv', headerText: 'Tên đơn vị', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'ten', headerText: 'Loại nhân viên', allowFiltering: true, allowHtml: true, width: 200 }
        ],
        list: [],
        value: null
      },
      gridDonViDaGan: {
        header: [
          { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, width: 150 },
          { fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowFiltering: true, width: 200 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridDonViChuaGan: {
        header: [
          { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, width: 150 },
          { fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowFiltering: true, width: 200 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridLoaiNhanVienDaGan: {
        header: [{ fieldName: 'ten', headerText: 'Loại nhân viên', allowFiltering: true, width: 150 }],
        list: [],
        selected: [],
        value: null
      },
      gridLoaiNhanVienChuaGan: {
        header: [{ fieldName: 'ten', headerText: 'Loại nhân viên', allowFiltering: true, width: 150 }],
        list: [],
        selected: [],
        value: null
      },
      gridNhanVienQLDaGan: {
        header: [
          { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'donvi', headerText: 'Đơn vị', allowFiltering: true, width: 150 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridNhanVienQLChuaGan: {
        header: [
          { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'donvi', headerText: 'Đơn vị', allowFiltering: true, width: 150 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridNhanVien: {
        header: [
          { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'ma_the', headerText: 'Mã thẻ', allowFiltering: true, width: 150 },
          { fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowFiltering: true, width: 150 },
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'chucdanh', headerText: 'Chức danh', allowFiltering: true, width: 150 },
          { fieldName: 'vitri', headerText: 'Vị trí', allowFiltering: true, width: 150 },
          { fieldName: 'dien_thoai', headerText: 'Điện thoại', allowFiltering: true, width: 150 },
          { fieldName: 'diachi_nv', headerText: 'Địa chỉ', allowFiltering: true, width: 150 },
          { fieldName: 'hinhthuc_hd', headerText: 'Hình thức HĐ', allowFiltering: true, width: 150 },
          { fieldName: 'vitri_id', headerText: 'Mã vị trí', allowFiltering: true, width: 150 }
        ],
        list: [],
        selected: [],
        value: null
      },
      searchResult: {
        nhanvien_id: null
      },
      nhanVienViTriDialog: {
        isVisible: false
      },
      khuVucDialog: {
        isVisible: false
      }
    }
  },
  watch: {
    async 'gridNhanVien.value'(val) {
      if (val) {
        console.log(this.gridNhanVien.value)
        this.txtMaNV.value = this.gridNhanVien.value.ma_nv
        this.txtViTri.value = this.gridNhanVien.value.vitri
        this.cboGioiTinh.value = this.gridNhanVien.value.gioitinh
        this.txtTenNV.value = this.gridNhanVien.value.ten_nv
        this.txtTenTN.value = this.gridNhanVien.value.ten_tn
        this.dpNgaySinh.value = moment(this.gridNhanVien.value.ngay_sn).format('DD/MM/YYYY')
        this.txtSdt.value = this.gridNhanVien.value.so_dt
        this.txtDiaChi.value = this.gridNhanVien.value.diachi_nv
        this.txtTienKyQuy.value = this.gridNhanVien.value.tien_dc
        this.txtMailVNPT.value = this.gridNhanVien.value.email
        this.cboDonVi.value = this.gridNhanVien.value.donvi_id
        this.cboDonViDL.value = this.gridNhanVien.value.donvi_dl_id
        this.txtSoThe.value = this.gridNhanVien.value.ma_the
        this.txtMaKenh.value = this.gridNhanVien.value.makenh
        this.cboHopDong.value = this.gridNhanVien.value.hthd_id
        this.txtUserVi.value = this.gridNhanVien.value.user_vi
        this.txtGhiChu.value = this.gridNhanVien.value.ghichu

        if (this.gridNhanVien.value.nhanvien_id != -1) {
          this.loading(true)
          let dataLoaiNhanVienDaGan = await api.sp_ds_loainhanvien(this.axios, {
            p_kieu: 1,
            p_nhanvien_id: val.nhanvien_id
          })
          this.gridLoaiNhanVienDaGan.list = dataLoaiNhanVienDaGan.data.data
          let dataLoaiNhanVienChuaGan = await api.sp_ds_loainhanvien(this.axios, {
            p_kieu: 0,
            p_nhanvien_id: val.nhanvien_id
          })
          this.gridLoaiNhanVienChuaGan.list = dataLoaiNhanVienChuaGan.data.data
          this.loadDonVi(val, 32)
          // let dataDonViDaGan = await api.sp_ds_donvi_theo_donvidl_loaidv(this.axios, {
          //   p_donvidl_id: 0,
          //   p_loaidv_id: 32,
          //   p_nhanvien_id: val.nhanvien_id || 0,
          //   p_kieu: 1
          // })
          // this.gridDonViDaGan.list = dataDonViDaGan.data.data
          // let dataDonViChuaGan = await api.sp_ds_donvi_theo_donvidl_loaidv(this.axios, {
          //   p_donvidl_id: 0,
          //   p_loaidv_id: 32,
          //   p_nhanvien_id: val.nhanvien_id || 0,
          //   p_kieu: 0
          // })
          // this.gridDonViChuaGan.list = dataDonViChuaGan.data.data
          let dataNhanVienQuanLyDaGan = await api.sp_ds_nhanvien_quanly(this.axios, {
            p_nhanvien_id: val.nhanvien_id,
            p_donvi_id: val.donvi_id,
            p_kieu: 1
          })
          this.gridNhanVienQLDaGan.list = dataNhanVienQuanLyDaGan.data.data
          let dataNhanVienQuanLyChuaGan = await api.sp_ds_nhanvien_quanly(this.axios, {
            p_nhanvien_id: val.nhanvien_id,
            p_donvi_id: val.donvi_id,
            p_kieu: 0
          })
          this.gridNhanVienQLChuaGan.list = dataNhanVienQuanLyChuaGan.data.data
          this.loading(false)
        } else {
          this.gridLoaiNhanVienDaGan.list = []
          this.gridLoaiNhanVienChuaGan.list = []
          this.gridNhanVienQLChuaGan.list = []
          this.gridNhanVienQLChuaGan.list = []
          this.gridDonViChuaGan.list = []
          this.gridDonViDaGan.list = []
          this.gridNhanVien.list = []
        }
      }
    },
    async 'cboLoaiDV.value'(val) {
      console.log('cboLoaiDV.value')
      console.log(val)
      if (this.gridNhanVien.value) {
        this.loadDonVi(this.gridNhanVien.value, this.cboLoaiDV.value)
      }
    }
  },
  methods: {
    async init() {
      await this.loadData()
      if (this.type == 'view') {
        this.initView()
      }
      if (this.type == 'modal') {
        this.initModal()
      }
    },
    async initView() {},
    async initModal() {},
    async loadData() {
      this.loading(true)
      let dataCboDonVi = await api.sp_cbo_donvi_dichvu_cha(this.axios, {})
      this.cboDonVi.list = dataCboDonVi.data.data
      let dataCboHopDong = await api.sp_cbo_ds_hinhthuc_hopdong(this.axios, {})
      this.cboHopDong.list = dataCboHopDong.data.data
      let dataCboLoaiDV = await api.sp_lay_dulieu_theo_dieukien(this.axios, {
        p_schema: 'admin',
        p_table_name: 'loai_dvi',
        p_value: 'loaidv_id',
        p_text: 'ten_loaidv',
        p_where: '',
        p_order: 'loaidv_id'
      })
      this.cboLoaiDV.list = dataCboLoaiDV.data.data
      let dataCboDonViDL = await api.sp_nv_donvi_dl(this.axios, {})
      this.cboDonViDL.list = dataCboDonViDL.data.data
      let dataTreeDonVi = await api.sp_load_treeview(this.axios, {})
      await this.compileDanhSachDonVi(dataTreeDonVi)
      this.loading(false)
      // this.$nextTick(() => {
      //   this.selectedDonViArray = [this.danhSachDonViTreeView[0].donvi_id]
      //   this.$refs.treeViewDonVi.expandAll([this.danhSachDonViTreeView[0].donvi_id])
      // })
    },
    async compileDanhSachDonVi(data) {
      let duLieu = data.data.data
      let listAllDonVi = duLieu.ls
      let donViCap1 = {
        ten_dv: duLieu.tendv_cha,
        unitlevel: 0,
        donvi_id: duLieu.donvi_id_cha,
        donvi_cha_id: duLieu.donvi_id_cha,
        children: []
      }
      let donViCap2 = duLieu.ls.filter((e) => e.donvi_cha_id == duLieu.donvi_id_cha)
      listAllDonVi = listAllDonVi.map((e) => {
        e.donvi_id = `${e.donvi_id}`
        return e
      })
      donViCap1.children = this.filterDonViCon(listAllDonVi, donViCap2)
      this.treeDonVi.flatList = duLieu.ls
      this.treeDonVi.list = [donViCap1]
    },
    filterDonViCon(listAllDonVi, donViCap2) {
      donViCap2 = donViCap2.map((dv) => {
        if (dv.unitlevel < 10) {
          let donViCon = this.filterDonViCon(
            listAllDonVi,
            listAllDonVi.filter((e) => e.donvi_cha_id == dv.donvi_id)
          )
          dv.children = donViCon
        }

        return dv
      })
      return donViCap2
    },
    async loadDonVi(nhanVienData, loaidv_id) {
      this.loading(true)
      let dataDonViDaGan = await api.sp_ds_donvi_theo_donvidl_loaidv(this.axios, {
        p_donvidl_id: 0,
        p_loaidv_id: loaidv_id,
        p_nhanvien_id: nhanVienData.nhanvien_id || 0,
        p_kieu: 1
      })
      this.gridDonViDaGan.list = dataDonViDaGan.data.data
      let dataDonViChuaGan = await api.sp_ds_donvi_theo_donvidl_loaidv(this.axios, {
        p_donvidl_id: 0,
        p_loaidv_id: loaidv_id,
        p_nhanvien_id: nhanVienData.nhanvien_id || 0,
        p_kieu: 0
      })
      this.gridDonViChuaGan.list = dataDonViChuaGan.data.data
      this.loading(false)
    },
    async get_app_config(key) {
      this.loading(true)
      let data = await api.get_app_config(this.axios, { keyname: key })
      this.loading(false)
      if (data.data) {
        if (data.data.keyname == key && data.data.dongbo == 1 && data.data.keyvalue == 'TRUE') {
          return true
        }
      }
      return false
    },
    onMenuClicked(action) {
      switch (action.id) {
        case 'nhap_moi':
          this.onBtnNhapMoiClicked()
          break
        case 'ghi_lai':
          this.onBtnGhiLaiOnClicked()
          break
        case 'huy':
          this.onBtnHuyClicked()
          break
        case 'xoa':
          this.onBtnXoaClicked()
          break
        case 'tim_kiem':
          this.timKiemNhanVienBy('ma_nv')
          break
        case 'gan_vi_tri':
          this.onBtnGanViTriClicked()
          break
        case 'khu_vuc':
          this.onBtnKhuVucOnClicked()
          break
      }
    },
    onBtnNhapMoiClicked() {
      this.resetForm()
    },
    resetForm() {
      this.gridNhanVien.value = {
        nhanvien_id: -1
        // txtMaNV: {value: ''},
        // txtTenNV: {value: ''},
        // txtTenTN: {value: ''},
        // dpNgaySinh: {value: null},
        // txtSdt: {value: ''},
        // txtDiaChi: {value: ''},
      }
    },
    async onBtnGhiLaiOnClicked() {
      if (this.gridNhanVien.value.nhanvien_id == -1) {
        this.actionNhanVien('them_moi')
      } else {
        this.actionNhanVien('cap_nhat')
      }
    },
    onBtnGhiLaiClicked() {},
    onBtnGanViTriClicked() {
      if (this.gridNhanVien.value && this.gridNhanVien.value.nhanvien_id != -1) {
        this.nhanVienViTriDialogOnClick()
      }
      // this.modalNhanVienVitriData = {
      //   nhanVienId: this.gridNhanVien.value.nhanvien_id,
      //   tenNhanVien: this.gridNhanVien.value.ten_nv
      // }
    },
    onBtnHuyClicked() {
      if (this.gridNhanVien.value == null || this.gridNhanVien.value.nhanvien_id == -1) {
        this.resetForm()
        return
      }
      this.timKiemNhanVienBy('nhanvien_id')
    },
    async onBtnXoaClicked() {
      if (!confirm(`Xác nhận xoá nhân viên: ${this.gridNhanVien.value.ten_nv}`)) return

      let deletedLoaiNhanVienDaGan = this.gridLoaiNhanVienDaGan.list
      this.loading(true)
      let deleteResult = await api.sp_delete_nhanvien(this.axios, {
        p_flag: 0,
        p_kyhoadon: moment(new Date()).format('YYYYMM01'),
        p_nhanvien_id: this.gridNhanVien.value.nhanvien_id,
        donvi_dl_id: this.gridNhanVien.value.donvi_dl_id ? this.gridNhanVien.value.donvi_dl_id : 0
      })
      this.loading(false)
      if (deleteResult.data.data == '1') {
        let OPEN_SYNC_VINA_TRASAU = await this.get_app_config('OPEN_SYNC_VINA_TRASAU')
        OPEN_SYNC_VINA_TRASAU = true
        if (OPEN_SYNC_VINA_TRASAU) {
          for (let i = 0; i < deletedLoaiNhanVienDaGan.length; i++) {
            if (deletedLoaiNhanVienDaGan[i].loainv_id == 25) {
              this.loading(true)
              let kq = await ccbsApi.ts_danhmuc_delete_nhanvien_tcs(this.axios, { manv: `${this.gridNhanVien.value.ma_nv}` })
              this.loading(false)
              if (kq.data.data.result == 'Xoa du lieu thanh cong') {
                this.$toast.success('Hủy loại nhân viên thu cước với nhân viên này trên vinaphone thành công!')
              } else {
                this.$toast.error(`Lỗi Web Service: \n${kq.data.data.result}`)
              }
            }
          }
        }
        this.$toast.success('Xoá nhân viên thành công')
        this.resetForm()
      } else {
        this.$toast.error(deleteResult.data.data)
      }
    },
    async onBtnKhuVucOnClicked() {
      this.khuVucDialogOnClick()
    },
    async onTreeDonviNodeSelected(e) {
      this.loading(true)
      let data = await api.sp_ds_nhanvien_donvi(this.axios, {
        p_donvi_id: e.nodeData.id,
        p_kieu: 1
      })
      this.loading(false)
      this.gridNhanVien.list = data.data.data

      ///
      if (this.searchResult.nhanvien_id) {
        try {
          const index = this.gridNhanVien.list.findIndex((item) => item.nhanvien_id == this.searchResult.nhanvien_id)
          if (index === -1) {
            this.$toast.warning('Không tìm thấy nhân viên')
          } else {
            const toPage = parseInt(index / this.$refs.gridNhanVien.pageSize)
            const toRow = index % this.$refs.gridNhanVien.pageSize
            setTimeout(() => {
              this.$refs.gridNhanVien.$refs.pagination.gotoPage(toPage)
              setTimeout(() => {
                this.$refs.gridNhanVien.selectRow(toRow, false)
              }, 200)
            }, 100)
            this.searchResult.nhanvien_id = null
          }
        } catch (e) {
          console.log(e)
        }
      }
    },
    onTxtTreeDonViSearchEntered(e) {},
    btnUploadChuKyFileClicked() {},
    loaiNhanVienMTL() {
      const data = this.$refs.gridLoaiNhanVienDaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridLoaiNhanVienDaGan.list = this.gridLoaiNhanVienDaGan.list.filter((e) => data.find((x) => x.loainv_id == e.loainv_id) == null)
        this.gridLoaiNhanVienChuaGan.list.unshift(...data)
      }
    },
    loaiNhanVienMTR() {
      const data = this.$refs.gridLoaiNhanVienChuaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridLoaiNhanVienChuaGan.list = this.gridLoaiNhanVienChuaGan.list.filter((e) => data.find((x) => x.loainv_id == e.loainv_id) == null)
        this.gridLoaiNhanVienDaGan.list.unshift(...data)
      }
    },
    donViMTL() {
      const data = this.$refs.gridDonViDaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridDonViDaGan.list = this.gridDonViDaGan.list.filter((e) => data.find((x) => x.donvi_id == e.donvi_id) == null)
        this.gridDonViChuaGan.list.unshift(...data)
      }
    },
    donViMTR() {
      const data = this.$refs.gridDonViChuaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridDonViChuaGan.list = this.gridDonViChuaGan.list.filter((e) => data.find((x) => x.donvi_id == e.donvi_id) == null)
        this.gridDonViDaGan.list.unshift(...data)
      }
    },
    nhanVienQLMTL() {
      const data = this.$refs.gridNhanVienQLDaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridNhanVienQLDaGan.list = this.gridNhanVienQLDaGan.list.filter((e) => data.find((x) => x.nhanvien_id == e.nhanvien_id) == null)
        this.gridNhanVienQLChuaGan.list.unshift(...data)
      }
    },
    nhanVienQLMTR() {
      const data = this.$refs.gridNhanVienQLChuaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridNhanVienQLChuaGan.list = this.gridNhanVienQLChuaGan.list.filter((e) => data.find((x) => x.nhanvien_id == e.nhanvien_id) == null)
        this.gridNhanVienQLDaGan.list.unshift(...data)
      }
    },
    gridNhanVienOnSelectedRowChanged(e) {
      this.gridNhanVien.value = e
    },
    async timKiemNhanVienBy(method) {
      let nhanvien_id = '-1'
      switch (method) {
        case 'ma_nv':
          if (this.txtMaNV.value.length == 0) {
            this.$toast.warning('Vui lòng nhập mã nhân viên cần tìm!')
            return
          }
          this.loading(true)
          nhanvien_id = (
            await api.fn_tt_nhanvien(this.axios, {
              param: this.txtMaNV.value,
              type: 1
            })
          ).data.data
          this.loading(false)
          if (nhanvien_id == '-1') {
            this.loading(true)
            nhanvien_id = (
              await api.fn_tt_nhanvien(this.axios, {
                param: this.txtMaNV.value,
                type: 7
              })
            ).data.data
            this.loading(false)
          }
          break
        case 'ten_nv':
          if (this.txtTenNV.value.length == 0) {
            this.$toast.warning('Vui lòng nhập tên nhân viên cần tìm!')
            return
          }
          this.loading(true)
          let data = await api.sp_get_nhanvien_manv_tennv(this.axios, {
            dk_tim: this.txtTenNV.value,
            kieu: 2
          })
          this.loading(false)
          if (data.data.data && data.data.data.length > 0) {
            nhanvien_id = data.data.data[0].nhanvien_id
          }
          break
        case 'nhanvien_id':
          nhanvien_id = this.gridNhanVien.value.nhanvien_id
          break
      }
      if (nhanvien_id == '-1') {
        this.$toast.warning('Không tìm thấy nhân viên bằng mã nhân viên hoặc user đăng nhập!')
        this.loading(false)
        return
      }
      this.searchResult.nhanvien_id = nhanvien_id
      this.loading(true)
      let donvi_id = (
        await api.thanh_ly_map_id(this.axios, {
          id_neo: 'donvi_id',
          in_table: 'admin.nhanvien',
          in_dk: `where nhanvien_id = ${nhanvien_id}`
        })
      ).data.data
      this.loading(false)
      if (donvi_id == '-1') {
      } else {
        let donViNode = this.treeDonViFindNode(this.treeDonVi.list, donvi_id)
        this.$refs.treeDonVi.expandAll(donViNode)
        this.treeDonVi.selected = [donvi_id]
      }
    },
    async timKiemNhanVienByTenNV(input) {
      this.loading(true)
      let data = await api.sp_get_nhanvien_manv_tennv(this.axios, {
        // dk_tim: this.txtTenNV.value,
        dk_tim: input,
        kieu: 2
      })
      this.loading(false)
      if (data.data.data && data.data.data.length > 0) {
        this.acTxtTenNV.list = data.data.data
      }
    },
    treeDonViFindNode(lstDonVi, id) {
      const a = lstDonVi.find((dv) => dv.donvi_id == id)
      if (a) {
        return [a.donvi_id.toString()]
      }

      for (let ii = 0; ii < lstDonVi.length; ii++) {
        const b = this.treeDonViFindNode(lstDonVi[ii].children, id)
        if (b) {
          b.unshift(lstDonVi[ii].donvi_id.toString())
          return b
        }
      }
    },
    async actionNhanVien(action) {
      let messages = []
      if (this.txtMaNV == null || this.txtMaNV.value == null || this.txtMaNV.value.length == 0) {
        messages.push('=> Mã nhân viên')
      }
      if (this.txtTenNV == null || this.txtTenNV.value == null || this.txtTenNV.value.length == 0) {
        messages.push('=> Tên nhân viên')
      }
      if (this.txtTenTN == null || this.txtTenTN.value == null || this.txtTenTN.value.length == 0) {
        messages.push('=> Tên TN')
      }
      if (this.dpNgaySinh == null || this.dpNgaySinh.value == null) {
        messages.push('=> Ngày sinh')
      }
      if (this.txtSdt == null || this.txtSdt.value == null || this.txtSdt.value.length == 0) {
        messages.push('=> Số điện thoại')
      }
      if (this.txtDiaChi == null || this.txtDiaChi.value == null || this.txtDiaChi.value.length == 0) {
        messages.push('=> Địa chỉ')
      }
      if (this.cboDonVi == null || this.cboDonVi.value == null) {
        messages.push('=> Đơn vị')
      }
      console.log('vuiha')
      console.log(messages)
      if (messages.length > 0) {
        this.$toast.warning(`Bạn phải nhập đủ các trường\n${messages.join('\n')}`)
        return
      }
      console.log('nhanvien')
      console.log(this.gridNhanVien.value)
      // const ngaySinhStr = moment(this.formThongTinNhanVienData.ngaySinh).format('DD/MM/YYYY')
      const param = {
        p_data: [
          {
            DIACHI_NV: this.txtDiaChi.value,
            DONVI_DL_ID: this.cboDonViDL.value,
            DONVI_ID: this.cboDonVi.value,
            EMAIL: this.txtMailVNPT.value,
            GHICHU: this.txtGhiChu.value,
            GIOITINH: this.cboGioiTinh.value ? 1 : 0,
            HTHD_ID: this.cboHopDong.value,
            MAKENH: this.txtMaKenh.value,
            MA_NV: this.txtMaNV.value,
            MA_THE: this.txtSoThe.value,
            NGAY_SN: this.dpNgaySinh.value,
            NHANVIEN_ID: action == 'them_moi' ? null : this.gridNhanVien.value.nhanvien_id,
            SO_DT: this.txtSdt.value,
            TEN_NV: this.txtTenNV.value,
            TEN_TN: this.txtTenTN.value,
            TIEN_DC: this.txtTienKyQuy.value,
            USER_VI: this.txtUserVi.value,
            CHUKY: ''
          }
        ],
        p_flag: action == 'them_moi' ? 1 : 2,
        p_is_insert: action == 'them_moi' ? 1 : 0,
        p_kyhoadon: moment(new Date()).format('YYYYMM01'),
        p_nhanvien_ql_data:
          this.gridNhanVienQLDaGan.list.map((item) => ({
            NHANVIEN_ID: item.nhanvien_id,
            NHANVIEN_QL_ID: action == 'them_moi' ? null : this.gridNhanVien.value.nhanvien_id
          })) || [],
        p_loainv_data:
          this.gridLoaiNhanVienDaGan.list.map((item) => ({
            NHANVIEN_ID: action == 'them_moi' ? null : this.gridNhanVien.value.nhanvien_id,
            LOAINV_ID: item.loainv_id
          })) || [],
        p_donvi_data:
          this.gridDonViDaGan.list.map((item) => ({
            NHANVIEN_ID: action == 'them_moi' ? null : this.gridNhanVien.value.nhanvien_id,
            DONVI_ID: item.donvi_id
          })) || []
      }

      let nhanVien = this.gridLoaiNhanVienDaGan.value
      let loaiNhanVienDaGan = this.gridLoaiNhanVienDaGan.list
      let updatedResult = null
      try {
        this.loading(true)
        updatedResult = await api.sp_insert_update_nhanvien(this.axios, param)
        this.loading(false)
      } catch (e) {
        this.loading(false)
        this.$toast.error(`${e.data.message}`)
        return
      }
      if (updatedResult.data.data && updatedResult.data.data == 'OK') {
        if (action == 'cap_nhat') {
          this.$toast.success('Cập nhật dữ liệu thành công!')
          let OPEN_SYNC_VINA = await this.get_app_config('OPEN_SYNC_VINA')
          OPEN_SYNC_VINA = true
          if (OPEN_SYNC_VINA) {
            for (let i = 0; i < loaiNhanVienDaGan.length; i++) {
              if (loaiNhanVienDaGan[i].loainv_id == 25) {
                this.loading(true)
                let donvi_id_neo = await api.map_donvi_id_neo(this.axios, {
                  donvi_id: this.gridNhanVien.value.donvi_id
                })
                this.loading(false)
                this.loading(true)
                let mabc_id_neo = await api.map_mabc_id_neo(this.axios, {
                  donvi_id: this.gridNhanVien.value.donvi_id
                })
                this.loading(false)

                this.loading(true)
                let kq = await ccbsApi.ts_dm_nhanvientcs_capnhat(this.axios, {
                  ma_bc: mabc_id_neo,
                  dienthoai_lh: this.txtSdt.value,
                  sms: '0',
                  donviql_id: donvi_id_neo,
                  ma_nv: this.txtMaNV.value,
                  ten_nv: this.txtTenNV.value,
                  dia_chi: this.txtDiaChi.value
                })
                this.loading(false)
                if (kq.data.data.result == 'Xoa du lieu thanh cong') {
                  this.$toast.success('Hủy loại nhân viên thu cước với nhân viên này trên vinaphone thành công!')
                } else {
                  this.$toast.error(`Lỗi Web Service: \n${kq.data.data.result}`)
                }
              }
            }
          }
        } else {
          for (let i = 0; i < loaiNhanVienDaGan.length; i++) {
            if (loaiNhanVienDaGan[i].loainv_id == 25) {
              let OPEN_SYNC_VINA = await this.get_app_config('OPEN_SYNC_VINA')
              if (OPEN_SYNC_VINA) {
                await this.CapNhatNV_VNP()
              }
            }
          }
          this.$toast.success('Thêm mới nhân viên vào dữ liệu viễn thông tỉnh thành công !')
        }
      } else {
        this.$toast.success(`${updatedResult.data.data}`)
      }
      // } catch (e) {
      // this.loading(false)
      // this.$toast.error(`Có lỗi xảy ra - FE-linecode-748: ${e.message}`)
      // }
    },
    acTxtTenNVChange(e) {
      if (this.timeout) clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        this.timKiemNhanVienByTenNV(e.text)
      }, 1000)
    },
    onModalNhanVienViTriSaveSuccess(e) {
      console.log('onModalNhanVienViTriSaveSuccess')
      console.log(e)
      this.timKiemNhanVienBy('nhanvien_id')
    },
    onModalChonDiaChiSuccess(e) {
      this.txtDiaChi.value = e.diachimoi
    },
    async nhanVienViTriDialogOnClick() {
      this.nhanVienViTriDialog.isVisible = true
      console.log('dieu kien nay')
      console.log(this.nhanVienViTriDialog.isVisible && this.gridNhanVien.value && this.gridNhanVien.value.nhanvien_id != -1)
      this.$refs.nhanVienViTriDialog.show()
    },
    async nhanVienViTriDialogOnClose() {
      this.nhanVienViTriDialog.isVisible = false
    },
    async khuVucDialogOnClick() {
      this.khuVucDialog.isVisible = true
      this.$refs.khuVucDialog.show()
    },
    async khuVucDialogOnClose() {
      this.khuVucDialog.isVisible = false
    }
  }
}
</script>
<style scoped></style>
