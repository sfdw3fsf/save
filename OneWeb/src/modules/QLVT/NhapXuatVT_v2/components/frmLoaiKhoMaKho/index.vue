<template>
  <!-- Form -->
  <div class="">
    <div class="list-actions-top">
      <ul class="list">
        <li @click="tsbtnLayTT_Click">
          <a> <span class="icon one-file-attach"></span>Lấy TT </a>
        </li>
        <li @click="tsbtnNew_Click" v-if="Visible.tsbtnNew" :class="{ disabledInput: !Enabled.tsbtnNew }">
          <a> <span class="icon  one-file-plus"></span> Nhập mới </a>
        </li>
        <li @click="tsbtnSave_Click" v-if="Visible.tsbtnSave" :class="{ disabledInput: !Enabled.tsbtnSave }">
          <a> <span class="icon  one-save"></span> Cập nhật </a>
        </li>
        <li @click="tsbtnCancel_Click" v-if="Visible.tsbtnCancel" :class="{ disabledInput: !Enabled.tsbtnCancel }">
          <a> <span class="icon  nc-icon-glyph ui-1_circle-remove"></span> Hủy </a>
        </li>
        <li @click="tsbtnDel_Click" v-if="Visible.tsbtnDel" :class="{ disabledInput: !Enabled.tsbtnDel }">
          <a> <span class="icon one-trash"></span> Xóa </a>
        </li>
        <li @click="tsbtnXacNhan_Click" v-if="Visible.tsbtnXacNhan">
          <a> <span class="icon  nc-icon-glyph ui-1_check-circle-08"></span> Xác nhận </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="nav tabs tab-over">
        <a href="#loai-kho" v-if="Visible.tabLoaiKho" data-toggle="tab" :class="{ active: xtraTabControl1 == 0, disabledInput: !Enabled.tabLoaiKho }" @click="xtraTabControl1_SelectedPageChanged(0)">
          Loại kho
        </a>
        <a href="#ma-kho" v-if="Visible.tabMaKho" data-toggle="tab" :class="{ active: xtraTabControl1 == 1, disabledInput: !Enabled.tabMaKho }" @click="xtraTabControl1_SelectedPageChanged(1)">
          Mã kho
        </a>
        <a href="#tk-doi-ung" data-toggle="tab" v-if="Visible.tabTKDU" :class="{ active: xtraTabControl1 == 2 }" @click="xtraTabControl1_SelectedPageChanged(2)">
          TK Đối ứng
        </a>
        <a href="#chon-lk-mk" v-if="Visible.tabEditLKMK" data-toggle="tab" :class="{ active: xtraTabControl1 == 3 }" @click="xtraTabControl1_SelectedPageChanged(3)">
          Chọn loại kho - mã kho
        </a>
      </div>

      <div class="box-form tab-content">
        <!-- TAB Loại Kho -->
        <div class="tab-pane" :class="{ active: xtraTabControl1 == 0 }" id="loai-kho">
          <div class="grid-stack-box">
            <div class="box-col box-form col-sm-5 col-12">
              <div class="legend-title" style="height: 27px;"></div>

              <div class="row">
                <div class="info-row">
                  <div class="key w100">Mã loại kho <span class="required">*</span></div>
                  <div class="value">
                    <input type="text" class="form-control" ref="txtMaLK" v-model="tabLoaiKho.txtMaLK" />
                  </div>
                  <div class="value">
                    <div class="check-action mart7">
                      <input type="checkbox" class="check" :checked="tabLoaiKho.ckbTonKho" v-model="tabLoaiKho.ckbTonKho" />
                      <span class="name">Tồn kho</span>
                    </div>
                  </div>
                </div>

                <div class="info-row">
                  <div class="key w100">Tên loại kho <span class="required">*</span></div>
                  <div class="value">
                    <textarea class="form-control" style="height: 60px;resize: none;" v-model="tabLoaiKho.txtTenLK" ref="txtTenLK"></textarea>
                  </div>
                </div>

                <div class="info-row">
                  <div class="key w100">
                    Ghi chú
                  </div>
                  <div class="value">
                    <textarea class="form-control" style="height: 60px;resize: none;" v-model="tabLoaiKho.txtGhiChuLK"></textarea>
                  </div>
                </div>
              </div>
            </div>
            <div class="box-col box-form col-sm-7 col-12">
              <div class="legend-title">
                Loại kho
              </div>
              <div class="info-row">
                <div class="value">
                  <div class="check-action mart7">
                    <input type="checkbox" class="check" v-model="tabLoaiKho.ckbXemTonKho" />
                    <span class="name">Xem các kho tồn</span>
                  </div>
                </div>
              </div>

              <div class="table-content">
                <DataGrid3
                  :columns="[
                    { fieldName: 'MA_LK', headerText: 'Mã LK', allowFiltering: true },
                    { fieldName: 'TEN_LK', headerText: 'Tên LK', allowFiltering: true },
                    { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true },
                    { fieldName: 'NGAY_CN', headerText: 'Ngày CN', allowFiltering: true },
                    { fieldName: 'NGUOI_CN', headerText: 'Người CN', allowFiltering: true },
                    { fieldName: 'NXCC_ID', headerText: '', visible: false, isPrimaryKey: true }
                  ]"
                  ref="gridChungTu"
                  :showFilter="true"
                  :allowPaging="true"
                  dataKeyField="NXCC_ID"
                  v-bind:dataSource="tabLoaiKho.DSLoaiKho"
                  :enablePagingServer="false"
                  :enabledSelectFirstRow="true"
                  @selectedRowChanged="grvLoaiKho_FocusedRowChanged"
                />
              </div>
            </div>
          </div>

          <div class="box-move-select-table">
            <div class="box-col box-form">
              <div class="legend-title">
                Danh sách được gán
              </div>
              <div class="table-content">
                <DataGrid
                  :columns="[
                    { fieldName: 'MUCDICH', headerText: 'Kiểu phiếu', allowFiltering: true },
                    { fieldName: 'LOAI_CT', headerText: 'Loại', allowFiltering: true }
                  ]"
                  ref="gridLKMD"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  v-bind:dataSource="tabLoaiKho.DSLKMD"
                  :showColumnCheckbox="true"
                  :enabledSelectFirstRow="false"
                  @selectedItemsChanged="gridLKMD_Checked"
                >
                </DataGrid>
              </div>
            </div>
            <div class="actions">
              <button class="btn" @click="btnLeftLKMD_Click">
                <span class="-ap icon-chevron-thin-left"></span>
              </button>
              <button class="btn" @click="btnRightLDKMD_Click">
                <span class="-ap icon-chevron-thin-right"></span>
              </button>
            </div>
            <div class="box-col box-form">
              <div class="legend-title">
                Danh sách loại kho - mục đích chưa được gán
              </div>
              <div class="table-content">
                <DataGrid
                  :columns="[
                    { fieldName: 'TEN', headerText: 'Kiểu phiếu', allowFiltering: true },
                    { fieldName: 'LOAI_CT', headerText: 'Loại', allowFiltering: true },
                    { fieldName: 'NOIDUNG', headerText: 'Ghi chú', allowFiltering: true }
                  ]"
                  ref="gridMucDich"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  v-bind:dataSource="tabLoaiKho.DSMucDich"
                  :showColumnCheckbox="true"
                  :enabledSelectFirstRow="false"
                  @selectedItemsChanged="gridMucDich_Checked"
                >
                </DataGrid>
              </div>
            </div>
          </div>
        </div>

        <!-- TAB Mã Kho -->
        <div class="tab-pane" :class="{ active: xtraTabControl1 == 1 }" id="ma-kho">
          <div class="grid-stack-box">
            <div class="box-col box-form col-sm-5 col-12">
              <div class="legend-title" style="height: 27px;"></div>

              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Mã kho <span class="required">*</span></div>
                    <div class="value">
                      <input type="text" class="form-control" ref="txtMaKhoKT" v-model="tabMaKho.txtMaKhoKT" />
                    </div>
                  </div>
                </div>
                <div class=" col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Tài khoản
                    </div>
                    <div class="value">
                      <input type="number" class="form-control" ref="txtTaiKhoan" v-model="tabMaKho.txtTaiKhoan" />
                    </div>
                  </div>
                </div>
              </div>

              <div class="info-row">
                <div class="key w100">Tên kho <span class="required">*</span></div>
                <div class="value">
                  <textarea class="form-control" style="height: 60px;resize: none;" ref="txtTenKhoKT" v-model="tabMaKho.txtTenKhoKT"></textarea>
                </div>
              </div>

              <div class="info-row">
                <div class="key w100">
                  Ghi chú
                </div>
                <div class="value">
                  <textarea class="form-control" style="height: 60px;resize: none;" ref="txtGhiChuKT" v-model="tabMaKho.txtGhiChuKT"></textarea>
                </div>
              </div>
            </div>
            <div class="box-col box-form col-sm-7 col-12">
              <div class="legend-title">
                Mã kho
              </div>

              <div class="table-content">
                <DataGrid
                  :columns="[
                    { fieldName: 'MA_KHOTD', headerText: 'Mã kho', allowFiltering: true },
                    { fieldName: 'TEN_KHOTD', headerText: 'Tên kho', allowFiltering: true },
                    { fieldName: 'TAIKHOAN', headerText: 'Tài khoản', allowFiltering: true },
                    { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true },
                    { fieldName: 'NGAY_CN', headerText: 'Ngày CN', allowFiltering: true },
                    { fieldName: 'NGUOI_CN', headerText: 'Người CN', allowFiltering: true }
                  ]"
                  ref="gridKhoKT"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  v-bind:dataSource="tabMaKho.DSKhoKT"
                  :showColumnCheckbox="false"
                  @selectedRowChanged="grvKhoKT_FocusedRowChanged"
                  @selectedItemsChanged=""
                />
              </div>
            </div>
          </div>
          <div class="box-move-select-table">
            <div class="box-col box-form">
              <div class="legend-title">
                Danh sách đã gán
              </div>
              <div class="table-content">
                <DataGrid
                  :columns="[{ fieldName: 'TEN_LK', headerText: 'Tên LK', allowFiltering: true }]"
                  ref="gridKTLKLeft"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  v-bind:dataSource="tabMaKho.DSKTLKLeft"
                  :showColumnCheckbox="true"
                  :enabledSelectFirstRow="false"
                  @selectedItemsChanged="gridKTLKLeft_Checked"
                />
              </div>
            </div>
            <div class="actions">
              <button class="btn" @click="btnLeftKT_Click">
                <span class="-ap icon-chevron-thin-left"></span>
              </button>
              <button class="btn" @click="btnRightKT_Click">
                <span class="-ap icon-chevron-thin-right"></span>
              </button>
            </div>
            <div class="box-col box-form">
              <div class="legend-title">
                Danh sách mã kho - loại kho chưa gán
              </div>
              <div class="table-content">
                <DataGrid
                  :columns="[
                    { fieldName: 'MA_LK', headerText: 'Mã LK', allowFiltering: true },
                    { fieldName: 'TEN_LK', headerText: 'Tên LK', allowFiltering: true },
                    { fieldName: 'LOAIKHO_TD_ID', visible: false, isPrimaryKey: true }
                  ]"
                  ref="gridKTLK"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  dataKeyField="LOAIKHO_TD_ID"
                  v-bind:dataSource="tabMaKho.DSKTLK"
                  :showColumnCheckbox="true"
                  :enabledSelectFirstRow="false"
                  @selectedItemsChanged="gridKTLK_Checked"
                />
              </div>
            </div>
          </div>
        </div>

        <!-- TAB TK Đối ứng -->
        <!-- <div class="tab-pane" :class="{active:xtraTabControl1 == 2}" id="tk-doi-ung">
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="box-form">
                <div class="row">
                  <div class="col-sm-3 col-12">
                    <div class="info-row">
                      <div class="key w100">
                        Loại CT
                      </div>

                      <div class="select-custom">
                        <ejs-dropdownlist ref="cboLoaiChungTu" class="form-control" locale="vi" v-model="tabTKDU.cboLoaiCT" :dataSource="tabTKDU.DSLoaiCT" :fields="{value:'loaiCtId', text: 'loaiCt'}" @change="cboLoaiCT_EditValueChanged" :allowFiltering="true" :filtering="onFilterLoaiCT">
                        </ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                  <div class=" col-sm-3 col-12">
                    <div class="info-row">
                      <div class="key w100">
                        Kiểu phiếu
                      </div>
                      <div class="value">
                        <ejs-dropdownlist
                          ref="cboLoaiChungTu"
                          class="form-control"
                          locale="vi"
                          v-model="tabTKDU.cboKieuPhieu"
                          :dataSource="tabTKDU.DSKieuPhieu"
                          :fields="{value:'mucDichId', text: 'mucDich'}"
                          @change="cboKieuPhieu_EditValueChanged"
                          :allowFiltering="true"
                          :filtering="onFilterKieuPhieu"
                        >
                        </ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box-move-select-table">
            <div class="box-col box-form">
              <div class="legend-title">
                Danh sách đã gán
              </div>
              <div class="table-content">
                <DataGrid
                  :columns="[
                                    {fieldName: 'MA_KHOTD', headerText: 'Mã kho KT', allowFiltering: true},     
                                    {fieldName: 'TAIKHOAN', headerText: 'TK đối ứng', allowFiltering: true},                                
                                    ]"
                  ref="gridTKDU_DaGan"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  v-bind:dataSource="tabTKDU.DSTKDU_DaGan"
                  :showColumnCheckbox="true"
                  :enabledSelectFirstRow="false"
                  @selectedItemsChanged="gridTKDU_DaGan_Checked"
                />
              </div>
            </div>
            <div class="actions">
              <button class="btn" @click="btnLeftTKDU_Click">
                <span class="-ap icon-chevron-thin-left"></span>
              </button>
              <button class="btn" @click="btnRightTKDU_Click">
                <span class="-ap icon-chevron-thin-right"></span>
              </button>
            </div>
            <div class="box-col box-form">
              <div class="legend-title">
                Danh sách tài khoản đối ứng chưa gán
              </div>
              <div class="table-content">
                <DataGrid
                  :columns="[
                                    {fieldName: 'MA_KHOTD', headerText: 'Mã kho KT', allowFiltering: true},   
                                    {fieldName: 'TEN_KHOTD', headerText: 'Tên kho', allowFiltering: true},     
                                    {fieldName: 'TAIKHOAN', headerText: 'TK đối ứng', allowFiltering: true},                               
                                    ]"
                  ref="gridTKDU_ChuaGan"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  v-bind:dataSource="tabTKDU.DSTKDU_ChuaGan"
                  :showColumnCheckbox="true"
                  :enabledSelectFirstRow="false"
                  @selectedItemsChanged="gridTKDU_ChuaGan_Checked"
                />
              </div>
            </div>
          </div>
        </div> -->

        <!-- TAB Chọn loại kho - mã kho -->
        <div class="tab-pane" :class="{ active: xtraTabControl1 == 3 }" id="chon-lk-mk">
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="">
                <div class="row">
                  <div class="col-sm-5 col-12 info-row">
                    <div class="key w100">Kiểu phiếu</div>
                    <div class="value">
                      <SelectExt :disabled="!cboKieuPhieu.enabled" v-model="tabLKMK.cboKieuP" :dataSource="tabLKMK.DSKieuPhieu" dataTextField="mucDich" dataValueField="mucDichId" />
                    </div>
                  </div>
                  <div class="col-sm-3 col-12 info-row">
                    <div class="key w100">
                      Chất lượng
                    </div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="btnGanOnClicked('CHATLUONG_ID', tabLKMK.cboChatLuong)">
                          <span class="-ap icon-chevron-thin-down"></span>
                        </button>
                        <SelectExt ref="cboChatLuong" v-model="tabLKMK.cboChatLuong" :dataSource="tabLKMK.DSChatLuong" dataTextField="chatLuong" dataValueField="chatLuongId" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12 info-row">
                    <div class="key w100">
                      Dòng thiết bị
                    </div>
                    <div class="value">
                      <div class="input-more-button">
                        <button :disabled="!cboDongTB.enabled" class="btn" @click="btnGanOnClicked('DONGTBI_ID', tabLKMK.cboDongTB)">
                          <span class="-ap icon-chevron-thin-down"></span>
                        </button>
                        <SelectExt :disabled="!cboDongTB.enabled" ref="cboDongTB" v-model="tabLKMK.cboDongTB" :dataSource="tabLKMK.DSDongTB" dataTextField="tenDongTbi" dataValueField="dongTbiId" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-5 col-12 info-row">
                    <div class="key w100">
                      Loại kho
                    </div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="btnGanOnClicked('LOAIKHO', tabLKMK.cboLK)">
                          <span class="-ap icon-chevron-thin-down"></span>
                        </button>
                        <SelectExt ref="cboLoaiChungTu" v-model="tabLKMK.cboLK" :dataSource="tabLKMK.DSLK" dataTextField="tenLk" dataValueField="loaiKhoTdId" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-3 col-12 info-row">
                    <div class="key w100">
                      Mã kho TD
                    </div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="btnGanOnClicked('MAKHOTD', tabLKMK.cboMKTD)">
                          <span class="-ap icon-chevron-thin-down"></span>
                        </button>
                        <SelectExt ref="cboLoaiChungTu" v-model="tabLKMK.cboMKTD" :dataSource="tabLKMK.DSMKTD" dataTextField="maKhoTd" dataValueField="khoTDId" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12 info-row">
                    <div class="key w100">
                      Hệ thống
                    </div>
                    <div class="value">
                      <div class="input-more-button">
                        <button :disabled="!cboHeThong.enabled" class="btn" @click="btnGanOnClicked('HETHONG_ID', tabLKMK.cboHeThong)">
                          <span class="-ap icon-chevron-thin-down"></span>
                        </button>
                        <SelectExt :disabled="!cboHeThong.enabled" ref="cboHeThong" v-model="tabLKMK.cboHeThong" :dataSource="tabLKMK.DSHeThong" dataTextField="tenHeThong" dataValueField="heThongId" />
                      </div>
                    </div>
                  </div>
                </div>
                <!-- {fieldName: 'LOAIKHO', headerText: 'Loại kho', allowFiltering: true},   -->
                <div class="table-content">
                  <!-- dataKeyField="VATTU_ID" -->
                  <DataGrid
                    ref="gridVatTu"
                    :columns="gridVatTu.header"
                    :dataSource="tabLKMK.DSVatTu"
                    :showFilter="true"
                    :allowPaging="true"
                    :enablePagingServer="false"
                    :selectionSettings="{ enableToggle: false, checkboxOnly: true, persistSelection: false, checkboxMode: 'ResetOnRowClick' }"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    :columnCheckboxFreeze="true"
                    @selectedItemsChanged="gridVatTuTbiSelectedItemsChanged"
                  />
                  <!-- :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }" -->
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
import { mapActions, mapState, mapGetters, mapMutations } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import { getterName, actionName, statePropertyName, mutationName } from '@/modules/QLVT/store/loaikhomakho'
import Vue from 'vue'
import api from './api'
import { GridVatTuChatLuongTemplate, GridVatTuDongTbTemplate, GridVatTuHeThongTemplate, GridVatTuLoaiKhoTemplate, GridVatTuMaKhoTDTemplate } from '..'

export default {
  components: { breadcrumb },
  emits: ['onBtnXacNhanOnClicked'],
  props: {
    v_tag: {
      type: [String, Number],
      required: true
    },
    vkieuphieu_id: {
      type: [String, Number],
      default: '0'
    },
    vloai_ct: {
      type: [String, Number],
      default: '0'
    },
    vdt_dasua: {
      type: Array
    }
  },
  name: 'popupLoaiKhoMaKho',
  async mounted() {
    if (this.vkieuphieu_id) {
      this.tabLKMK.DSLK = await this.getDSLoaiKhoTheoMucDich(this.vkieuphieu_id)
      this.tabLKMK.cboKieuP = this.vkieuphieu_id
    }
    if (this.v_tag) {
      this.tagForm = this.v_tag
    }
    if (this.vloai_ct) {
      this.loaict = this.vloai_ct
    }
    if (this.vdt_dasua) {
      this.dt_dasua = this.vdt_dasua
    }

    this.OpenDialog()
  },
  data() {
    return {
      tagForm: '',
      Tag: '',
      loaict: 0,
      kieuphieu: 0,
      dt_dasua: [],
      loaikho: 0,
      khoid: 0,
      xtraTabControl1: 0,
      xacnhan: 0,
      cboDongTB: {
        value: null,
        enabled: false
      },
      cboKieuPhieu: {
        value: null,
        enabled: false
      },
      cboHeThong: {
        value: null,
        enabled: false
      },
      Enabled: {
        tsbtnNew: true,
        tsbtnSave: true,
        tsbtnDel: true,
        tsbtnCancel: true,
        tabLoaiKho: true,
        tabMaKho: true
      },

      Visible: {
        tsbtnXacNhan: false,
        tabEditLKMK: false,
        tabLoaiKho: true,
        tabMaKho: true,
        tabTKDU: true,

        tsbtnNew: true,
        tsbtnSave: true,
        tsbtnCancel: true,
        tsbtnDel: true
      },

      tabLoaiKho: {
        txtMaLK: '',
        txtTenLK: '',
        txtGhiChuLK: '',
        DSLoaiKho: [],
        DSLKMD: [],
        DSMucDich: [],
        ckbXemTonKho: true,
        ckbTonKho: false,
        DSMucDichChecked: [],
        DSLKMDChecked: [],
        LoaiKhoSelected: {}
      },

      tabMaKho: {
        txtMaKhoKT: '',
        txtTenKhoKT: '',
        txtGhiChuLK: '',
        txtTaiKhoan: '',
        DSKhoKT: [],
        DSKTLKLeft: [],
        DSKTLK: [],
        DSKTLKChecked: [],
        DSKTLKLeftChecked: [],
        KhoKTSelected: {}
      },

      tabTKDU: {
        DSLoaiCT: [],
        cboLoaiCT: 0,
        DSKieuPhieu: [],
        cboKieuPhieu: 0,
        DSTKDU_DaGan: [],
        DSTKDU_DaGan_Checked: [],

        DSTKDU_ChuaGan: [],
        DSTKDU_ChuaGan_Checked: []
      },

      tabLKMK: {
        DSKieuPhieu: [],
        DSChatLuong: [],
        cboKieuP: 0,
        cboChatLuong: 0,
        DSVatTu: [],
        cboChatluongRepo: [],
        DSLK: [],
        cboLK: 0,
        cboMKTD: 0,
        DSMKTD: [],
        DSVatTu_Checked: [],
        DSDongTB: [],
        cboDongTB: 0,
        cboHeThong: 0,
        DSHeThong: []
      },
      gridVatTu: {
        value: null,
        header: [
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
          { width: 120, fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
          { width: 300, fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { width: 70, fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
          { width: 60, fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true },
          { width: 120, fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true },
          { width: 350, headerText: 'Loại kho', template: this.gridVatTuCboLoaiKho(this), allowFiltering: true },
          { headerText: 'Mã kho TD', template: this.gridVatTuCboMaKhoTD(this), allowFiltering: true },
          { headerText: 'Chất lượng', template: this.gridVatTuCboChatLuong(this), allowFiltering: true },
          { headerText: 'Dòng tbi', template: this.gridVatTuCboDongTB(this), allowFiltering: true },
          { headerText: 'Hệ thống', template: this.gridVatTuCboHeThong(this), allowFiltering: true },
          { fieldName: 'VATTU_ID', visible: false, isPrimaryKey: true }
        ],
        selected: [],
        list: [],
        cboMaKhoTD: []
      },
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      listMaKhoTD: []
    }
  },
  watch: {
    async 'tabLKMK.cboLK'(val) {
      if (val) {
        this.tabLKMK.DSMKTD = await this.getDSKhoTD(val)
      }
    }
  },
  computed: {},
  methods: {
    gridVatTuCboLoaiKho(parent) {
      return function() {
        return {
          template: {
            template: `
                <div v-if="cboLoaiKho.optimize">{{ cboLoaiKho.value }}</div>
                <div v-else style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboLoaiKho.enabled" style="width: 100%;" v-model="cboLoaiKho.value" :dataSource="cboLoaiKho.list" dataTextField="tenLk" dataValueField="loaiKhoTdId" @select="cboOnChanged"/>
                </div>
                  `,
            data() {
              return {
                data: {},
                cboLoaiKho: {
                  optimize: true,
                  enabled: true,
                  value: null,
                  list: []
                }
              }
            },
            async mounted() {
              if (this.cboLoaiKho.optimize) {
                this.cboLoaiKho.value = parent.tabLKMK.DSLK.find((e) => e.loaiKhoTdId == this.data.LOAIKHO)?.tenLk
              } else {
                this.cboLoaiKho.value = this.data.LOAIKHO
                this.cboLoaiKho.list = parent.tabLKMK.DSLK
              }
            },
            methods: {
              cboOnChanged(kho) {
                let list = parent.tabLKMK.DSVatTu.map((e, idx) => {
                  if (this.data.index == idx) {
                    e.LOAIKHO = kho.id
                  }
                  return e
                })
                parent.setLaiGridVatTu(list)
              }
            }
          }
        }
      }
    },
    gridVatTuCboChatLuong(parent) {
      return function() {
        return {
          template: {
            template: `
                 <div v-if="cboChatLuong.optimize">{{ cboChatLuong.value }}</div>
                  <div v-else style="width: 100%; display: flex; justify-content: center; align-items: center;">
                    <SelectExt :disabled="!cboChatLuong.enabled" style="width: 100%;" v-model="cboChatLuong.value" :dataSource="cboChatLuong.list" dataTextField="chatLuong" dataValueField="chatLuongId" @select="cboOnChanged"/>
                  </div>
                  `,
            data() {
              return {
                data: {},
                cboChatLuong: {
                  optimize: true,
                  enabled: true,
                  value: null,
                  list: []
                }
              }
            },
            mounted() {
              if (this.cboChatLuong.optimize) {
                this.cboChatLuong.value = parent.tabLKMK.DSChatLuong.find((e) => e.chatLuongId == this.data.CHATLUONG_ID)?.chatLuong
              } else {
                this.cboChatLuong.list = parent.tabLKMK.DSChatLuong
                this.cboChatLuong.value = this.data.CHATLUONG_ID
              }
            },
            methods: {
              async cboOnChanged(event) {
                let index = parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)
                parent.tabLKMK.DSVatTu[index].CHATLUONG_ID = event.id
              }
            }
          }
        }
      }
    },
    gridVatTuCboMaKhoTD(parent) {
      return function() {
        return {
          template: {
            template: `
                <div v-if="cboMaKhoTD.optimize">{{ cboMaKhoTD.value }}</div>
                <div v-else style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboMaKhoTD.enabled" style="width: 100%;" v-model="cboMaKhoTD.value" :dataSource="cboMaKhoTD.list" dataTextField="MA_KHOTD" dataValueField="KHOTD_ID" @select="cboOnChanged"/>
                </div>
                  `,
            data() {
              return {
                data: {},
                cboMaKhoTD: {
                  optimize: true,
                  enabled: true,
                  value: null,
                  list: []
                }
              }
            },
            async mounted() {
              if (this.cboMaKhoTD.optimize) {
                this.cboMaKhoTD.value = parent.listMaKhoTD.find((e) => e.KHOTD_ID == this.data.MAKHOTD)?.MA_KHOTD
              } else {
                this.cboMaKhoTD.list = parent.listMaKhoTD
                this.cboMaKhoTD.value = this.data.MAKHOTD
              }
            },
            methods: {
              cboOnChanged(event) {
                parent.tabLKMK.DSVatTu[parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)].MAKHOTD = event.id
              }
            }
          }
        }
      }
    },
    gridVatTuCboDongTB(parent) {
      return function() {
        return {
          template: {
            template: `
              <div v-if="cboDongTB.optimize">{{ cboDongTB.value }}</div>
              <div v-else style="width: 100%; display: flex; justify-content: center; align-items: center;">
                <SelectExt :disabled="!cboDongTB.enabled" style="width: 100%;" v-model="cboDongTB.value" :dataSource="cboDongTB.list" dataTextField="tenDongTbi" dataValueField="dongTbiId" @select="cboOnChanged"/>
              </div>
                  `,
            data() {
              return {
                data: {},
                cboDongTB: {
                  optimize: true,
                  value: null,
                  list: [],
                  enabled: false
                }
              }
            },
            mounted() {
              if (this.cboDongTB.optimize) {
                this.cboDongTB.value = parent.tabLKMK.DSDongTB.find((e) => e.dongTbiId == this.data.DONGTBI_ID)?.tenDongTbi
              } else {
                this.cboDongTB.list = parent.tabLKMK.DSDongTB
                this.cboDongTB.value = this.data.DONGTBI_ID
                this.cboDongTB.enabled = parent.cboDongTB.enabled
              }
            },
            methods: {
              cboOnChanged(event) {
                parent.tabLKMK.DSVatTu[parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)].DONGTBI_ID = event.id
              }
            }
          }
        }
      }
    },
    gridVatTuCboHeThong(parent) {
      return function() {
        return {
          template: {
            template: `
            <div v-if="cboHeThong.optimize">{{ cboHeThong.value }}</div>
            <div v-else style="width: 100%; display: flex; justify-content: center; align-items: center;">
              <SelectExt :disabled="!cboHeThong.enabled" style="width: 100%;" v-model="cboHeThong.value" :dataSource="cboHeThong.list" dataTextField="tenHeThong" dataValueField="heThongId" @select="cboOnChanged"/>
            </div>
                  `,
            data() {
              return {
                data: {},
                cboHeThong: {
                  optimize: true,
                  value: null,
                  enabled: false,
                  list: []
                }
              }
            },
            mounted() {
              if (this.cboHeThong.optimize) {
                this.cboHeThong.value = parent.tabLKMK.DSHeThong.find((e) => e.heThongId == this.data.HETHONG_ID)?.tenHeThong
              } else {
                this.cboHeThong.list = parent.tabLKMK.DSHeThong
                this.cboHeThong.value = this.data.HETHONG_ID
                this.cboHeThong.enabled = parent.cboHeThong.enabled
              }
            },
            methods: {
              cboOnChanged(event) {
                parent.tabLKMK.DSVatTu[parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)].HETHONG_ID = event.id
              }
            }
          }
        }
      }
    },
    ...mapActions('loaikhomakho', actionName),
    ...mapMutations('loaikhomakho', mutationName),
    async OpenDialog() {
      try {
        this.loading(true)
        this.listMaKhoTD = (await api.sp_lay_ds_makhotd(this.axios, {})).data.data
        await this.frmLoaiKho_MaKho_Load()
        // this.$refs.popupLoaiKhoMaKho.show()
      } catch (error) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async frmLoaiKho_MaKho_Load() {
      // this.tagForm = this.Tag
      await this.setButton(0)
      if (this.tagForm == '2') {
        var Text = 'Chọn loại kho - mã kho'
        this.xtraTabControl1 = 3
        this.Visible.tabEditLKMK = true
        this.Visible.tabLoaiKho = false
        this.Visible.tabMaKho = false
        this.Visible.tabTKDU = false

        this.Visible.tsbtnXacNhan = true
        this.Visible.tsbtnSave = false
        this.Visible.tsbtnCancel = false
        this.Visible.tsbtnDel = false
        this.Visible.tsbtnNew = false

        var loai = this.loaict == 99 ? 1 : this.loaict
        this.tabLKMK.DSKieuPhieu = await this.getDSKieuPhieu(loai)
        var DSChatLuong = await this.getDSChatLuong()
        this.tabLKMK.DSChatLuong = DSChatLuong
        this.tabLKMK.cboChatluongRepo = DSChatLuong
        if (this.tabLKMK.DSChatLuong.length > 0) {
          this.tabLKMK.cboChatLuong = this.tabLKMK.DSChatLuong[0].chatLuongId
        }
        this.tabLKMK.DSVatTu = this.dt_dasua.map((e, idx) => {
          e.IDX = idx
          return e
        })
      }
    },
    clear() {
      if (this.xtraTabControl1 == 0) {
        this.tabLoaiKho.txtMaLK = ''
        ;(this.tabLoaiKho.txtTenLK = ''), (this.tabLoaiKho.txtGhiChuLK = '')
      } else {
        this.tabMaKho.txtMaKhoKT = ''
        ;(this.tabMaKho.txtTenKhoKT = ''), (this.tabMaKho.txtGhiChuKT = '')
        this.tabMaKho.txtTaiKhoan = ''
      }
    },
    async setButton(kieu) {
      if (kieu == 0) {
        //Khoi tao luc load form
        this.Enabled.tsbtnSave = false
        this.Enabled.tsbtnDel = false

        this.tabLoaiKho.DSLoaiKho = []
        this.tabLoaiKho.DSMucDich = []
        this.tabLoaiKho.DSLKMD = []

        this.tabMaKho.DSKhoKT = []
        this.tabMaKho.DSKTLKLeft = []
        this.tabMaKho.DSKTLK = []
      } else if (kieu == 1) {
        //Bam them moi

        this.Enabled.tsbtnNew = false
        this.Enabled.tsbtnDel = false
        this.Enabled.tsbtnSave = true
        this.Enabled.tsbtnCancel = true
        this.clear()
        this.Enabled.tabLoaiKho = this.xtraTabControl1 == 0
        this.Enabled.tabMaKho = this.xtraTabControl1 != 0
      } else if (kieu == 2) {
        //Bam Huy
        this.Enabled.tsbtnNew = true
        this.Enabled.tsbtnDel = true
        this.Enabled.tsbtnSave = false
        this.Enabled.tsbtnCancel = false
        this.Enabled.tabLoaiKho = true
        this.Enabled.tabMaKho = true
        this.clear()
      } else if (kieu == 3) {
        this.Enabled.tsbtnNew = true
        this.Enabled.tsbtnDel = true
        this.Enabled.tsbtnSave = true
        this.Enabled.tsbtnCancel = false
        this.Enabled.tabLoaiKho = true
        this.Enabled.tabMaKho = true
      }
    },
    xtraTabControl1_SelectedPageChanged(kieu) {
      this.xtraTabControl1 = kieu
      if (this.xtraTabControl1 == 2 || this.xtraTabControl1 == 3) {
        this.Visible.tsbtnNew = false
        this.Visible.tsbtnSave = false
        this.Visible.tsbtnCancel = false
        this.Visible.tsbtnDel = false
      } else {
        this.Visible.tsbtnNew = true
        this.Visible.tsbtnSave = true
        this.Visible.tsbtnCancel = true
        this.Visible.tsbtnDel = true
      }
    },
    async tsbtnLayTT_Click() {
      try {
        this.loading(true)
        await this.load_LoaiKho()
        await this.load_MaKhoKT()
        this.tabTKDU.DSLoaiCT = await this.getDSLoaiCT()
        if (this.tabTKDU.DSLoaiCT.length > 0) {
          this.tabTKDU.cboLoaiCT = this.tabTKDU.DSLoaiCT[0].loaiCtId
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async load_LoaiKho() {
      var DSLoaiKhoMaKho = await this.getDSLoaiKhoMaKho({
        kieu: 0,
        loaiKhoId: this.tabLoaiKho.ckbXemTonKho ? 1 : 0
      })
      this.tabMaKho.DSKTLK = DSLoaiKhoMaKho
      this.tabLoaiKho.DSLoaiKho = DSLoaiKhoMaKho
      if (this.tabLoaiKho.DSLoaiKho.length == 0) {
        await this.setButton(1)
        this.tabLoaiKho.DSMucDich = []
        this.tabLoaiKho.DSLKMD = []
        this.tabLoaiKho.LoaiKhoSelected = {}
      }
    },
    async load_MaKhoKT() {
      this.tabMaKho.DSKhoKT = await this.getDSLoaiKhoMaKho({
        kieu: 1,
        loaiKhoId: 0
      })
      if (this.tabMaKho.DSKhoKT.length == 0) {
        await this.setButton(1)
        this.tabMaKho.DSKTLKLeft = []
        this.tabMaKho.DSKTLK = []
        this.tabMaKho.KhoKTSelected = {}
      }
    },
    async grvLoaiKho_FocusedRowChanged(row) {
      if (row) {
        this.tabLoaiKho.LoaiKhoSelected = row
        await this.setButton(3)
        this.tabLoaiKho.txtMaLK = row.MA_LK
        this.tabLoaiKho.txtTenLK = row.TEN_LK
        this.tabLoaiKho.txtGhiChuLK = row.GHICHU
        this.tabLoaiKho.ckbTonKho = row.HIENTHI == 1
        this.loaikho = row.LOAIKHO_TD_ID == null || row.LOAIKHO_TD_ID == '' ? 0 : row.LOAIKHO_TD_ID
        await this.load_LoaiKho_MucDich(this.loaikho)
        await this.load_MucDich(this.loaikho)
      }
    },
    async load_LoaiKho_MucDich(loaikho) {
      this.tabLoaiKho.DSLKMD = await this.getDSLoaiKhoMaKho({
        kieu: 4,
        loaiKhoId: loaikho
      })
    },
    async load_MucDich(loaikho) {
      this.tabLoaiKho.DSMucDich = await this.getDSLoaiKhoMaKho({
        kieu: 2,
        loaiKhoId: loaikho
      })
    },
    async btnLeftLKMD_Click() {
      if (this.tabLoaiKho.DSLoaiKho.length == 0) {
        this.$toast.error('Không có dữ liệu loại kho!')
        return
      }
      if (this.tabLoaiKho.DSMucDich.length == 0) {
        this.$toast.error('Không có kiểu phiếu nào được hiển thị! ')
        return
      }
      if (this.tabLoaiKho.DSMucDichChecked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn kiểu phiếu nào!')
        return
      }
      try {
        this.loading(true)
        var data = []
        for (const e of this.tabLoaiKho.DSMucDichChecked) {
          data.push({
            maLk: this.loaikho,
            taiKhoan: e.MUCDICH_ID,
            lkId: 0,
            khoTdId: 0
          })
        }
        var ketQua = await this.capNhatMaKhoLoaiKho({
          kieu: 0,
          loai: 2,
          data: data,
          tenLk: '',
          ghiChu: ''
        })
        await this.load_MucDich(this.loaikho)
        await this.load_LoaiKho_MucDich(this.loaikho)
        this.tabLoaiKho.DSMucDichChecked = []
      } catch (e) {
        this.$toast.error('Đã xảy ra lỗi vui lòng thử lại!')
      } finally {
        this.loading(false)
      }
    },
    async btnRightLDKMD_Click() {
      if (this.tabLoaiKho.DSLoaiKho.length == 0) {
        this.$toast.error('Không có dữ liệu loại kho!')
        return
      }
      if (this.tabLoaiKho.DSLKMD.length == 0) {
        this.$toast.error('Không có kiểu phiếu đã gán nào được hiển thị! ')
        return
      }
      if (this.tabLoaiKho.DSLKMDChecked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn kiểu phiếu đã gán nào!')
        return
      }

      try {
        this.loading(true)
        var data = []
        for (const e of this.tabLoaiKho.DSLKMDChecked) {
          data.push({
            maLk: e.LOAIKHO_TD_ID,
            taiKhoan: 0,
            lkId: e.MUCDICH_ID,
            khoTdId: 0
          })
        }

        var ketQua = await this.capNhatMaKhoLoaiKho({
          kieu: 2,
          loai: 2,
          data: data,
          tenLk: '',
          ghiChu: ''
        })

        await this.load_LoaiKho_MucDich(this.loaikho)
        await this.load_MucDich(this.loaikho)
        this.tabLoaiKho.DSLKMDChecked = []
      } catch (e) {
        this.$toast.error('Đã xảy ra lỗi vui lòng thử lại!')
      } finally {
        this.loading(false)
      }
    },
    gridMucDich_Checked: function(dataItem) {
      this.tabLoaiKho.DSMucDichChecked = dataItem
    },
    gridLKMD_Checked: function(dataItem) {
      this.tabLoaiKho.DSLKMDChecked = dataItem
    },
    async grvKhoKT_FocusedRowChanged(row) {
      if (row) {
        this.tabMaKho.KhoKTSelected = row
        await this.setButton(3)
        this.tabMaKho.txtMaKhoKT = row.MA_KHOTD
        this.tabMaKho.txtTenKhoKT = row.TEN_KHOTD
        this.tabMaKho.txtGhiChuKT = row.GHICHU
        this.tabMaKho.txtTaiKhoan = row.TAIKHOAN
        this.khoid = row.KHOTD_ID == null || row.KHOTD_ID == '' ? 0 : row.KHOTD_ID
        this.load_LoaiKho_2(this.khoid)
        this.load_LoaiKho_MaKT(this.khoid)
      }
    },
    async load_LoaiKho_2(khoid) {
      this.tabMaKho.DSKTLK = await this.getDSLoaiKhoMaKho({
        kieu: 5,
        loaiKhoId: khoid
      })
    },
    async load_LoaiKho_MaKT(khoid) {
      this.tabMaKho.DSKTLKLeft = await this.getDSLoaiKhoMaKho({
        kieu: 3,
        loaiKhoId: khoid
      })
    },
    async btnLeftKT_Click() {
      if (this.tabMaKho.DSKhoKT.length == 0) {
        this.$toast.error('Không có dữ liệu mã kho!')
        return
      }
      if (this.tabMaKho.DSKTLK.length == 0) {
        this.$toast.error('Không có loại kho nào được hiển thị! ')
        return
      }

      if (this.tabMaKho.DSKTLKChecked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn kiểu phiếu đã gán nào!')
        return
      }

      this.loading(true)
      var data = []
      for (const LOAIKHO_TD_ID of this.tabMaKho.DSKTLKChecked) {
        data.push({
          maLk: LOAIKHO_TD_ID,
          taiKhoan: 0,
          lkId: parseInt(this.khoid),
          khoTdId: 0
        })
      }

      var ketQua = await this.capNhatMaKhoLoaiKho({
        kieu: 0,
        loai: 3,
        data: data,
        tenLk: '',
        ghiChu: ''
      })
      await this.load_LoaiKho_2(this.khoid)
      await this.load_LoaiKho_MaKT(this.khoid)
      this.tabMaKho.DSKTLKChecked = []
      this.loading(false)
    },

    async btnRightKT_Click() {
      if (this.tabMaKho.DSKhoKT.length == 0) {
        this.$toast.error('Không có dữ liệu mã kho!')
        return
      }
      if (this.tabMaKho.DSKTLKLeft.length == 0) {
        this.$toast.error('Không có loại kho nào được hiển thị! ')
        return
      }

      if (this.tabMaKho.DSKTLKLeftChecked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn kiểu phiếu đã gán nào!')
        return
      }
      this.loading(true)
      var data = []

      for (const e of this.tabMaKho.DSKTLKLeftChecked) {
        data.push({
          maLk: e.LOAIKHO_TD_ID,
          taiKhoan: 0,
          lkId: parseInt(e.KHOTD_ID),
          khoTdId: 0
        })
      }

      var ketQua = await this.capNhatMaKhoLoaiKho({
        kieu: 2,
        loai: 3,
        data: data,
        tenLk: '',
        ghiChu: ''
      })
      await this.load_LoaiKho_MaKT(this.khoid)
      await this.load_LoaiKho_2(this.khoid)
      this.tabMaKho.DSKTLKLeftChecked = []
      this.loading(false)
    },
    gridKTLK_Checked: function(dataItem) {
      this.tabMaKho.DSKTLKChecked = dataItem
    },
    gridKTLKLeft_Checked: function(dataItem) {
      this.tabMaKho.DSKTLKLeftChecked = dataItem
    },
    async cboLoaiCT_EditValueChanged() {
      this.tabTKDU.DSKieuPhieu = await this.getDSKieuPhieu(this.tabTKDU.cboLoaiCT)
      if (this.tabTKDU.DSKieuPhieu.length > 0) {
        this.tabTKDU.cboKieuPhieu = this.tabTKDU.DSKieuPhieu[0].mucDichId
      }
    },
    async cboKieuPhieu_EditValueChanged() {
      await this.load_TKDU()
      await this.load_TKDU_DaGan()
    },
    async load_TKDU() {
      this.tabTKDU.DSTKDU_ChuaGan = await this.getDSLoaiKhoMaKho({
        kieu: 7,
        loaiKhoId: this.tabTKDU.cboKieuPhieu
      })
    },
    async load_TKDU_DaGan() {
      this.tabTKDU.DSTKDU_DaGan = await this.getDSLoaiKhoMaKho({
        kieu: 8,
        loaiKhoId: this.tabTKDU.cboKieuPhieu
      })
    },
    async btnLeftTKDU_Click() {
      if (this.tabTKDU.DSTKDU_ChuaGan.length == 0) {
        this.$toast.error('Không có dữ liệu được hiển thị! ')
        return
      }

      if (this.tabTKDU.DSTKDU_ChuaGan_Checked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn tài khoản đối ứng nào!')
        return
      }

      this.loading(true)
      var data = []
      for (const e of this.tabTKDU.DSTKDU_ChuaGan_Checked) {
        data.push({
          maLk: '',
          taiKhoan: parseInt(e.TAIKHOAN),
          lkId: this.tabTKDU.cboKieuPhieu,
          khoTdId: parseInt(e.KHOTD_ID)
        })
      }

      var ketQua = await this.capNhatMaKhoLoaiKho({
        kieu: 0,
        loai: 4,
        data: data,
        tenLk: '',
        ghiChu: ''
      })
      await this.load_TKDU()
      await this.load_TKDU_DaGan()
      this.tabTKDU.DSTKDU_ChuaGan_Checked = []
      this.loading(false)
    },

    gridTKDU_ChuaGan_Checked: function(dataItem) {
      this.tabTKDU.DSTKDU_ChuaGan_Checked = dataItem
    },

    gridTKDU_DaGan_Checked: function(dataItem) {
      this.tabTKDU.DSTKDU_DaGan_Checked = dataItem
    },

    async btnRightTKDU_Click() {
      if (this.tabTKDU.DSTKDU_DaGan.length == 0) {
        this.$toast.error('Không có dữ liệu được hiển thị! ')
        return
      }

      if (this.tabTKDU.DSTKDU_DaGan_Checked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn tài khoản đối ứng nào!')
        return
      }

      this.loading(true)
      var data = []
      for (const e of this.tabTKDU.DSTKDU_DaGan_Checked) {
        data.push({
          maLk: '',
          taiKhoan: 0,
          lkId: this.tabTKDU.cboKieuPhieu,
          khoTdId: parseInt(e.KHOTD_ID)
        })
      }

      var ketQua = await this.capNhatMaKhoLoaiKho({
        kieu: 2,
        loai: 4,
        data: data,
        tenLk: '',
        ghiChu: ''
      })
      await this.load_TKDU_DaGan()
      await this.load_TKDU()
      this.tabTKDU.DSTKDU_DaGan_Checked = []
      this.loading(false)
    },

    tsbtnNew_Click() {
      this.setButton(1)
    },
    tsbtnCancel_Click() {
      this.setButton(2)
    },
    async tsbtnDel_Click() {
      try {
        if (this.xtraTabControl1 == 0) {
          if (this.tabLoaiKho.LoaiKhoSelected != {}) {
            var isBoxConfirm = false
            await this.$bvModal
              .msgBoxConfirm('Bạn có thực sự muốn xóa [' + this.tabLoaiKho.LoaiKhoSelected.MA_LK + '] ?', {
                title: 'Thông báo',
                centered: true,
                size: 'md',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy'
              })
              .then((value) => {
                if (value) {
                  isBoxConfirm = true
                } else {
                  return
                }
              })

            if (!isBoxConfirm) {
              return
            }

            var data = []
            var vid_lk = parseInt(this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == null || this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == '' ? 0 : this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID)
            data.push({
              maLk: '',
              taiKhoan: 0,
              lkId: vid_lk,
              khoTdId: 0
            })
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 2,
              loai: 0,
              data: data,
              tenLk: '',
              ghiChu: ''
            })
            if (ketQua == 1) {
              this.$toast.success('Xóa thành công!')
              await this.load_LoaiKho()
            } else {
              this.$toast.error(ketQua)
            }
          } else {
            this.$toast.error('Bạn chưa chọn dòng loại kho!')
          }
        } else {
          if (this.tabMaKho.KhoKTSelected != {}) {
            var isBoxConfirm = false
            await this.$bvModal
              .msgBoxConfirm('Bạn có thực sự muốn xóa [' + this.tabMaKho.KhoKTSelected.MA_KHOTD + '] ?', {
                title: 'Thông báo',
                centered: true,
                size: 'md',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy'
              })
              .then((value) => {
                if (value) {
                  isBoxConfirm = true
                } else {
                  return
                }
              })

            if (!isBoxConfirm) {
              return
            }

            var data = []
            var vid_makhotd = parseInt(this.tabMaKho.KhoKTSelected.KHOTD_ID == null || this.tabMaKho.KhoKTSelected.KHOTD_ID == '' ? 0 : this.tabMaKho.KhoKTSelected.KHOTD_ID)
            data.push({
              maLk: '',
              taiKhoan: 0,
              lkId: vid_makhotd,
              khoTdId: 0
            })
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 2,
              loai: 1,
              data: data,
              tenLk: '',
              ghiChu: ''
            })

            if (ketQua == 1) {
              this.$toast.success('Xóa thành công!')
              await this.load_MaKhoKT()
            } else {
              this.$toast.error(ketQua)
            }
          } else {
            this.$toast.error('Bạn chưa chọn dòng mã kho!')
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },

    async tsbtnSave_Click() {
      try {
        if (this.Enabled.tsbtnNew) {
          // cập nhật

          if (this.xtraTabControl1 == 0) {
            // Loại Kho

            if (this.tabLoaiKho.txtMaLK.trim() == '') {
              this.$toast.error('Mã loại kho không được để trống!')
              this.$refs.txtMaLK.focus()
              return
            }
            if (this.tabLoaiKho.txtTenLK.trim() == '') {
              this.$toast.error('Tên loại kho không được để trống!')
              this.$refs.txtTenLK.focus()
              return
            }

            var data = []
            var vid_lk = parseInt(this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == null || this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == '' ? 0 : this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID)
            data.push({
              maLk: this.tabLoaiKho.txtMaLK.trim(),
              taiKhoan: 0,
              lkId: vid_lk,
              khoTdId: this.tabLoaiKho.ckbTonKho ? 1 : 0
            })
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 1,
              loai: 0,
              data: data,
              tenLk: this.tabLoaiKho.txtTenLK.trim(),
              ghiChu: this.tabLoaiKho.txtGhiChuLK.trim()
            })

            if (ketQua == 1) {
              this.$toast.success('Cập nhật loại kho thành công !')
              await this.load_LoaiKho()
            } else {
              this.$toast.error(ketQua)
            }
          } else {
            // Mã kho
            var kiemTra = this.KIEMTRA_DL_MAKHO()
            if (kiemTra == false) {
              return
            }

            var data = []
            var vid_makhotd = parseInt(this.tabMaKho.KhoKTSelected.KHOTD_ID == null || this.tabMaKho.KhoKTSelected.KHOTD_ID == '' ? 0 : this.tabMaKho.KhoKTSelected.KHOTD_ID)
            data.push({
              maLk: this.tabMaKho.txtMaKhoKT.trim(),
              taiKhoan: this.tabMaKho.txtTaiKhoan.trim(),
              lkId: vid_makhotd,
              khoTdId: 0
            })
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 1,
              loai: 1,
              data: data,
              tenLk: this.tabMaKho.txtTenKhoKT.trim(),
              ghiChu: this.tabMaKho.txtGhiChuKT.trim()
            })

            if (ketQua == 1) {
              this.$toast.success('Cập nhật mã kho KT thành công !')
              await this.load_MaKhoKT()
            } else {
              this.$toast.error(ketQua)
            }
          }
        } else {
          // Thêm mới

          if (this.xtraTabControl1 == 0) {
            // Loại Kho

            if (this.tabLoaiKho.txtMaLK.trim() == '') {
              this.$toast.error('Mã LK không được để trống!')
              this.$refs.txtMaLK.focus()
              return
            }
            if (this.tabLoaiKho.txtTenLK.trim() == '') {
              this.$toast.error('Tên LK không được để trống!')
              this.$refs.txtTenLK.focus()
              return
            }

            var data = []
            var vid_lk = parseInt(this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == null || this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == '' ? 0 : this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID)
            data.push({
              maLk: this.tabLoaiKho.txtMaLK.trim(),
              taiKhoan: 0,
              lkId: vid_lk,
              khoTdId: this.tabLoaiKho.ckbTonKho ? 1 : 0
            })
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 0,
              loai: 0,
              data: data,
              tenLk: this.tabLoaiKho.txtTenLK.trim(),
              ghiChu: this.tabLoaiKho.txtGhiChuLK.trim()
            })
            if (ketQua == 1) {
              this.$toast.success('Thêm mới loại kho thành công !')
              await this.load_LoaiKho()
            } else {
              this.$toast.error(ketQua)
            }
          } else {
            // Mã kho
            var kiemTra = this.KIEMTRA_DL_MAKHO()
            if (kiemTra == false) {
              return
            }

            var data = []
            var vid_makhotd = parseInt(this.tabMaKho.KhoKTSelected.KHOTD_ID == null || this.tabMaKho.KhoKTSelected.KHOTD_ID == '' ? 0 : this.tabMaKho.KhoKTSelected.KHOTD_ID)
            data.push({
              maLk: this.tabMaKho.txtMaKhoKT.trim(),
              taiKhoan: parseInt(this.tabMaKho.txtTaiKhoan),
              lkId: vid_makhotd,
              khoTdId: 0
            })
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 0,
              loai: 1,
              data: data,
              tenLk: this.tabMaKho.txtTenKhoKT.trim(),
              ghiChu: this.tabMaKho.txtGhiChuKT.trim()
            })

            if (ketQua == 1) {
              this.$toast.success('Cập nhật mã kho KT thành công !')
              await this.load_MaKhoKT()
            } else {
              this.$toast.error(ketQua)
            }

            if (ketQua == 1) {
              this.$toast.success('Thêm mới mã kho KT thành công !')
              await this.load_MaKhoKT()
            } else {
              this.$toast.error(ketQua)
            }
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    KIEMTRA_DL_MAKHO() {
      if (this.tabMaKho.txtMaKhoKT.trim() == '') {
        this.$toast.error('Mã Kho KT không được để trống! ')
        this.$refs.txtMaKhoKT.focus()
        this.$refs.txtTenKhoKT.focus()
        return false
      }
      if (this.tabMaKho.txtTenKhoKT.trim() == '') {
        this.$toast.error('Tên Kho KT không được để trống! ')
        this.$refs.txtTenKhoKT.focus()
        return false
      }
      if (this.tabMaKho.txtTaiKhoan.trim() == '') {
        this.$toast.error('Tài khoản không được để trống! ')
        this.$refs.txtTaiKhoan.focus()
        return false
      }
      return true
    },
    async cboKieuP_EditValueChanged() {
      this.tabLKMK.DSLK = await this.getDSLoaiKhoTheoMucDich(this.tabLKMK.cboKieuP)
      this.tabLKMK.cboLK = this.tabLKMK.DSLK.length > 0 ? this.tabLKMK.DSLK[0].loaiKhoTdId : 0
    },
    async cboLK_EditValueChanged() {
      this.tabLKMK.DSMKTD = await this.getDSKhoTD(this.tabLKMK.cboLK)
      this.tabLKMK.cboMKTD = this.tabLKMK.DSMKTD.length > 0 ? this.tabLKMK.DSMKTD[0].khoTDId : 0
    },
    tsbtnXacNhan_Click() {
      this.xacnhan = true
      // this.$refs.popupLoaiKhoMaKho.hide()
      this.$emit('onBtnXacNhanOnClicked', this.tabLKMK.DSVatTu)
      // this.$parent.XacNhan_frmLoaiKho_MaKho()
    },

    gridVatTuTbiSelectedItemsChanged: async function(dataItem) {
      this.tabLKMK.DSVatTu_Checked = dataItem
      // if (dataItem.length > 0) {
      //   this.tabLKMK.DSDongTB = await this.getDSDongThietBi({
      //     dem: dataItem.length,
      //     vatTuId: dataItem.map(e => e.VATTU_ID)
      //   })
      //   this.tabLKMK.cboDongTB = this.tabLKMK.DSDongTB.length > 0 ? this.tabLKMK.DSDongTB[0].dongTbiId : 0
      // }
      this.gridVatTu.selected = dataItem
    },
    async cboDongTB_EditValueChanged() {
      this.tabLKMK.DSHeThong = await this.getDSKhoTD(this.tabLKMK.cboLK)
      this.tabLKMK.cboHeThong = this.tabLKMK.DSHeThong.length > 0 ? this.tabLKMK.DSHeThong[0].heThongId : 0
    },
    btnLKDown_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn vật tư nào !!!')
        return
      }
      if (this.tabLKMK.cboLK == 0 || this.tabLKMK.cboLK == null) {
        this.$toast.error('Bạn chưa chọn loại kho !!!')
        return
      }
      let target = this.tabLKMK.DSLK.find((e) => e.loaiKhoTdId == this.tabLKMK.cboLK)

      // for(const i in this.tabLKMK.DSVatTu){
      //   if(this.tabLKMK.DSVatTu_Checked.includes(this.tabLKMK.DSVatTu[i].VATTU_ID)){

      //     this.tabLKMK.DSVatTu[i].LOAIKHO = this.tabLKMK.cboLK
      //   }
      // }

      this.tabLKMK.DSVatTu = this.tabLKMK.DSVatTu.map((e) => {
        if (e.LOAIKHO == target.loaiKhoTdId) {
          // e = {...e, ...target}
          // e.LOAIKHO = target.loaiKhoTdI
          // e.tenLk =
        }
        return e
      })
      this.$toast.success('Cập nhật loại kho thành công!!!')
    },
    btnChatLuong_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn vật tư nào !!!')
        return
      }
      if (this.tabLKMK.cboChatLuong == 0 || this.tabLKMK.cboChatLuong == null) {
        this.$toast.error('Bạn chưa chọn chất lượng !!!')
        return
      }
      for (const i in this.tabLKMK.DSVatTu) {
        if (this.tabLKMK.DSVatTu_Checked.includes(this.tabLKMK.DSVatTu[i].VATTU_ID)) {
          this.tabLKMK.DSVatTu[i].CHATLUONG_ID = this.tabLKMK.cboChatLuong
        }
      }
      this.$toast.success('Cập nhật chất lượng thành công!!!')
    },
    btnMKTDDown_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn vật tư nào !!!')
        return
      }
      if (this.tabLKMK.cboLK == 0 || this.tabLKMK.cboLK == null) {
        this.$toast.error('Bạn chưa chọn loại kho !!!')
        return
      }
      if (this.tabLKMK.cboMKTD == 0 || this.tabLKMK.cboMKTD == null) {
        this.$toast.error('Bạn chưa chọn mã kho TD !!!')
        return
      }
      for (const i in this.tabLKMK.DSVatTu) {
        if (this.tabLKMK.DSVatTu_Checked.includes(this.tabLKMK.DSVatTu[i].VATTU_ID)) {
          this.tabLKMK.DSVatTu[i].MAKHOTD = this.tabLKMK.cboMKTD
        }
      }
      this.$toast.success('Cập nhật mã kho TĐ thành công!!!')
    },
    btnDongtbi_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn vật tư nào !!!')
        return
      }
      if (this.tabLKMK.cboDongTB == 0 || this.tabLKMK.cboDongTB == null) {
        this.$toast.error('Bạn chưa chọn dòng thiết bị !!!')
        return
      }

      for (const i in this.tabLKMK.DSVatTu) {
        if (this.tabLKMK.DSVatTu_Checked.includes(this.tabLKMK.DSVatTu[i].VATTU_ID)) {
          this.tabLKMK.DSVatTu[i].DONGTBI_ID = this.tabLKMK.cboDongTB
        }
      }
      this.$toast.success('Cập nhật dòng thiết bị thành công!!!')
    },
    btnHeThong_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error('Bạn chưa tích chọn vật tư nào !!!')
        return
      }
      if (this.tabLKMK.cboDongTB == 0 || this.tabLKMK.cboDongTB == null) {
        this.$toast.error('Bạn chưa chọn dòng thiết bị !!!')
        return
      }
      if (this.tabLKMK.cboHeThong == 0 || this.tabLKMK.cboHeThong == null) {
        this.$toast.error('Bạn chưa chọn hệ thống !!!')
        return
      }
      for (const i in this.tabLKMK.DSVatTu) {
        if (this.tabLKMK.DSVatTu_Checked.includes(this.tabLKMK.DSVatTu[i].VATTU_ID)) {
          this.tabLKMK.DSVatTu[i].HETHONG_ID = this.tabLKMK.cboHeThong
        }
      }
      this.$toast.success('Cập nhật hệ thống thành công!!!')
    },
    btnGanOnClicked(name, value) {
      this.tabLKMK.DSVatTu = this.tabLKMK.DSVatTu.map((e) => {
        let find = this.gridVatTu.selected.find((x) => e.IDX == x.IDX)
        if (find) {
          e[name] = value
        }
        return e
      })
      let backupSelected = this.gridVatTu.selected.map((e) => {
        return this.tabLKMK.DSVatTu.findIndex((x) => e.IDX == x.IDX)
      })
      setTimeout(() => {
        this.$refs.gridVatTu.selectRows(backupSelected)
      }, 200)
    },
    setLaiGridVatTu(val) {
      this.tabLKMK.DSVatTu = val
    }
  },
  created: async function() {}
}
</script>
<style scoped>
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: rgba(153, 153, 153, 0.6) !important;
}

.disabledInput > a {
  color: #666 !important;
}/*# sourceMappingURL=popupLoaiKhoMaKho.css.map */
</style>