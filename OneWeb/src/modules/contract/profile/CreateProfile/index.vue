<template>
  <div>
    <div class="breadcrumb-top">
      <div class="main-title">Quản lý bộ hồ sơ</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="javascript:;"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="javascript:;">Quản lý hồ sơ</a></li>
        <li class="breadcrumb-item active">Tạo bộ hồ sơ</li>
      </ul>
    </div>
    <vue-nav>
      <ul class="list">
        <li @click="clickButton('laytt')" :style="this.button.laytt ? '' : 'pointer-events: none'" :class="this.button.laytt ? 'active' : 'non-active'">
          <a href="javascript:;"> <span class="icon one-file-attach1"></span>Lấy TT </a>
        </li>
        <li @click="clickButton('nhapmoi')" :style="this.button.nhapmoi ? '' : 'pointer-events: none'" :class="this.button.nhapmoi ? 'active' : 'non-active'">
          <a href="javascript:;"> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>
        <li @click="clickButton('capnhat')" :style="this.button.capnhat ? '' : 'pointer-events: none'" :class="this.button.capnhat ? 'active' : 'non-active'" v-if="Visible_capnhat">
          <a href="javascript:;"> <span class="icon one-reload1"></span>Cập nhật</a>
        </li>

        <li :style="this.button.huy ? '' : 'pointer-events: none'" @click="clickButton('huy')" v-if="Visible_huy" :class="this.button.huy ? 'active' : 'non-active'">
          <a href="javascript:;"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy bỏ</a>
        </li>

        <li :style="this.button.xoaphieu ? '' : 'pointer-events: none'" @click="clickButton('xoaphieu')" v-if="Visible_xoaphieu" :class="this.button.xoaphieu ? 'active' : 'non-active'">
          <a href="javascript:;"> <span class="icon one-file-trash"></span>Xóa phiếu</a>
        </li>

        <li :style="this.button.themfile ? '' : 'pointer-events: none'" @click="clickButton('themfile')" v-if="Visible_themfile" :class="this.button.themfile ? 'active' : 'non-active'">
          <a href="javascript:;"> <span class="icon nc-icon-glyph ui-1_circle-add"></span>Thêm file</a>
        </li>

        <li :style="this.button.xoafile ? '' : 'pointer-events: none'" @click="clickButton('xoafile')" v-if="Visible_xoafile" :class="this.button.xoafile ? 'active' : 'non-active'">
          <a href="javascript:;"> <span class="icon one-file-trash"></span>Xóa file</a>
        </li>

        <li :style="this.button.hdan ? '' : 'pointer-events: none'" @click="clickButton('hdan')" :class="this.button.hdan ? 'active' : 'non-active'">
          <a href="javascript:;"> <span class="icon one-book1"></span>Hướng dẫn</a>
        </li>
      </ul>
    </vue-nav>

    <div class="page-content">
      <div style="height: 850px">
        <div class="wrapper" @mouseup="endDragging()">
          <!-- <div :class="this.x" id="boxLeft0" :style="{width: `${this.dividerPosition}%`}"> -->
          <div :class="this.x" id="boxLeft0">
            <div class="grid-stack-box box-col" id="boxTop">
              <div class="box-col box-form ovauto col-4" id="boxLeft">
                <div class="legend-title">Thông tin bộ hồ sơ</div>
                <div class="row">
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w70">Mã GD</div>
                      <div class="value dropdown">
                        <div class="input-icon-right" data-toggle="dropdown">
                          <input type="text" class="form-control highlight" v-model="txtMaGD" ref="magd" v-on:keyup="keymonitor" />
                          <span class="icon one-search text-main"></span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w70">Ngày tạo</div>
                      <div class="value">
                        <div class="input-icon-right">
                          <flat-pickr class="form-control" :config="dateconfig" v-model="dtpNgayTao" :disabled="dtpNgayTao_disabled" />
                          <span class="icon one-calendar"></span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w70">Mã bộ HS</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtMaBHS" :disabled="txtMaBHS_disabled" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w70">Mã KH</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtMaKH" :disabled="txtMaKH_disabled" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w70">Tên KH</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtTenKH" :disabled="txtTenKH_disabled" />
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w70">Ngày HT</div>
                      <div class="value">
                        <div class="input-icon-right">
                          <flat-pickr class="form-control" :config="dateconfig" v-model="dtpNgayHT" :disabled="dtpNgayHT_disabled" />
                          <span class="icon one-calendar"></span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w70">Trạng thái</div>
                      <div class="value">
                        <div class="select-custom">
                          <SelectExt v-model="cboTrangThai" :options="this.options.trangthai" :disabled="cboTrangThai_disabled">
                            <option disabled value="0">Select one</option>
                          </SelectExt>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w70">Kho hồ sơ</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt v-model="cboKhoHS" :options="this.options.khohs" :disabled="cboKhoHS_disabled">
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w70">Ghi chú</div>
                  <div class="value">
                    <textarea class="form-control" v-model="txtGhiChu" :disabled="txtGhiChu_disabled" style="height: 74px; resize: none" id="" cols="30" rows="10"></textarea>
                  </div>
                </div>
              </div>

              <div class="box-col box-form col-4" id="boxMid">
                <div class="legend-title">
                  <div class="check-action">
                    <input type="checkbox" class="check" />
                    <span class="name">Chi tiết bộ hồ sơ</span>
                  </div>
                </div>
                <div class="table-content" style="height: calc(100% - 80px)">
                  <DataGrid
                    ref="grid_chitiet"
                    v-bind:columns="this.options.field_chitiet"
                    v-bind:dataSource="this.grid_chitiet"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    :editSettings="editSettings"
                    :showColumnPointer="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showColumnCheckbox="true"
                    @rowSelected="rowSelected_chitiet"
                  >
                  </DataGrid>
                </div>
              </div>

              <div class="box-col box-form col-4" id="boxRight">
                <div class="legend-title">Đơn vị nhận hồ sơ</div>
                <div class="table-content" style="height: calc(100% - 80px)">
                  <DataGrid
                    ref="grid_donvinhanhoso"
                    v-bind:columns="this.options.field_donvinhanhoso"
                    v-bind:dataSource="this.grid_donvinhanhoso"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"
                    :editSettings="editSettings"
                    :showColumnPointer="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                  >
                  </DataGrid>
                </div>
              </div>
            </div>

            <div class="box-col box-form col-12" id="boxBottom">
              <div class="legend-title">
                <div class="pull-left">
                  <div class="inline vcenter">Danh sách bộ hồ sơ</div>
                  <div class="inline vcenter marl20">
                    <div class="info-row marb0">
                      <div class="key normal w20">từ</div>
                      <div class="value">
                        <div class="input-icon-right">
                          <flat-pickr class="form-control" :config="dateconfig" v-model="dtpTuNgay" :disabled="dtpTuNgay_disabled" />
                          <span class="icon one-calendar"></span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="inline vcenter">
                    <div class="info-row marb0">
                      <div class="key normal w20">đến</div>
                      <div class="value">
                        <div class="input-icon-right">
                          <flat-pickr class="form-control" :config="dateconfig" v-model="dtpDenNgay" :disabled="dtpDenNgay_disabled" />
                          <span class="icon one-calendar"></span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="pull-right">
                  <div class="list-checks normal mart7">
                    <div class="item">
                      <div class="check-action">
                        <input type="checkbox" class="check" v-model="ckbAll" />
                        <span class="name">Lấy toàn bộ</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input type="checkbox" class="check" v-model="ckbAutoCheck" />
                        <span class="name">HS cùng Hợp đồng</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="clearfix"></div>
              </div>
              <div class="grid-stack-box" style="height: calc(100% - 50px)">
                <div class="box-col box-form col-8" id="boxLeft1">
                  <div class="table-content" style="height: calc(100% - 40px)">
                    <DataGrid
                      ref="grid_danhsachbohoso"
                      v-bind:columns="this.options.field_danhsachbohoso"
                      v-bind:dataSource="this.grid_danhsachbohoso"
                      :showFilter="true"
                      :enabledSelectFirstRow="false"
                      :editSettings="editSettings"
                      :showColumnPointer="false"
                      :enable-paging-server="false"
                      :allowPaging="true"
                      :showColumnCheckbox="true"
                      @rowSelected="rowSelected_danhsachbohoso"
                      @rowDeselected="rowDeselected_danhsachbohoso"
                      :selectionSettings="selectionOptions"
                      @recordClick="recordClick_danhsachbohoso"
                    >
                    </DataGrid>
                  </div>
                </div>
                <div class="box-col box-form col-4" id="boxRight1">
                  <div class="info-row" style="border: solid 1px #b5b4b2; padding: 9px; border-radius: 5">
                    <div class="dropdown">
                      <div class="select-custom" data-toggle="dropdown">
                        <button ref="showInputButton" class="form-control" @click="showInput = !showInput">Tiêu chí tìm kiếm</button>
                      </div>

                      <div ref="dropdown" v-if="showInput" class="popover-custom">
                        <div class="legend-title" style="margin-top: 5px">
                          <div class="pull-left" style="font-weight: 600 !important">Điều kiện lọc</div>
                          <div class="pull-right">
                            <button class="btn btn-main pad2" @click="showInput = !showInput">
                              <span class="-ap icon-close lh16"></span>
                            </button>
                          </div>
                          <div class="clearfix"></div>
                        </div>

                        <div class="info-row">
                          <div class="key w110">
                            <div class="check-action">
                              <input type="checkbox" class="check" v-model="chkTimKiem" />
                              <span class="name">Ngày HC từ</span>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-sm-6 col-2">
                              <div class="input-icon-right">
                                <flat-pickr class="form-control" :config="dateconfig" v-model="dtpTuNgayTK" :disabled="!chkTimKiem" />
                                <span class="icon one-calendar"></span>
                              </div>
                            </div>

                            <div class="col-sm-6 col-3">
                              <div class="input-icon-right">
                                <flat-pickr class="form-control" :config="dateconfig" v-model="dtpDenNgayTK" :disabled="!chkTimKiem" />
                                <span class="icon one-calendar"></span>
                              </div>
                            </div>
                          </div>
                        </div>

                        <!-- <div class="group-buttons -bottom tright mart10" :disabled="!showInput">
                            <button class="btn btn-search btn-main" @click="TimKiemHS">
                                <span class="one-search normal f20 inline vcenter"></span> Tìm kiếm
                            </button>
                        </div> -->
                      </div>
                    </div>
                  </div>
                  <div class="table-content">
                    <DataGrid
                      ref="grid_danhsachtimkiem"
                      v-bind:columns="this.options.field_danhsachtimkiem"
                      v-bind:dataSource="this.grid_danhsachtimkiem"
                      :showFilter="true"
                      :enabledSelectFirstRow="false"
                      :editSettings="editSettings"
                      :showColumnPointer="false"
                      :enable-paging-server="false"
                      :allowPaging="true"
                      :showColumnCheckbox="true"
                    >
                    </DataGrid>
                  </div>

                  <div class="group-buttons mart10">
                    <div class="pull-left">
                      <div class="check-action">
                        <input type="checkbox" class="check" v-model="ckbLayAll_DS" />
                        <span class="name">Lấy toàn bộ</span>
                      </div>
                    </div>
                    <div class="pull-right">
                      <button class="btn btn-main" @click="TimKiemHS"><span class="one-search f20 inline vcenter lh20"></span> Tìm kiếm</button>
                      <button class="btn btn-main" @click="this.btnTaoDS_HS_Click"><span class="nc-icon-glyph ui-1_circle-add f20 inline vcenter lh20"></span> Tạo hồ sơ</button>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- <div class="divider" :style="{left: `${this.dividerPosition}%`}" @mousedown="startDragging()"></div> -->
          <!-- <div :class="this.y" id="boxRight0"  :style=" {display:this.dpnone,width: `${100 - this.dividerPosition}%`}" > -->
          <div :class="this.y" id="boxRight0" :style="{ display: this.dpnone }" v-if="!this.button.themfile">
            <div class="legend-title">
              <div class="pull-left">
                <span class="title inline vcenter">Thông tin file</span>
                <button class="btn btn-second" @click="this.btnClear_Click"><span class="one-reload"></span> Làm mới</button>
                <button class="btn btn-second" @click="this.btnLuuFile"><span class="one-file-plus"></span> Lưu file</button>
              </div>
              <div class="pull-right">
                <a href="javascript:;" class="red btn f20 lh20" @click="this.HIDE">
                  <span class="-ap icon-arrow-right3"></span>
                </a>
              </div>
              <div class="clearfix"></div>
            </div>

            <div class="nav tabs tab-over mart10">
              <a href="#tab1" data-toggle="tab" @click="changetab_ttupload" class="active">Thông tin upload</a>
              <a href="#tab2" data-toggle="tab" @click="changetab_dsupload">Danh sách đã upload</a>
            </div>

            <div class="tab-content marb0">
              <div id="tab1" class="tab-pane active">
                <div class="info-row">
                  <div class="key w80">Nhóm file</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt v-model="cboNhomFile" :options="this.options.nhomfile">
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                    </div>
                  </div>
                </div>

                <div class="info-row">
                  <div class="key w80">Loại File</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt v-model="CboLoaiFile" :options="this.options.loaifile">
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                    </div>
                  </div>
                </div>

                <div class="info-row">
                  <div class="key w80">Đường dẫn</div>
                  <div class="value" :key="file_refresh">
                    <div class="input-more-button -right">
                      <label class="btn" for="upload-file">
                        <span class="-ap icon-more_horiz"></span>
                        <input type="file" ref="files" class="hidden" id="upload-file" @change="importFile" />
                      </label>
                      <input type="text" class="form-control highlight" v-model="txtDuongDan" />
                    </div>
                  </div>
                </div>
                <div class="info-row" v-if="txtDuongDan">
                  <div style="color: blue">Import thành công: {{ pathFileUpload }}</div>
                </div>
                <div class="info-row">
                  <div class="key w80">Ghi chú</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtGhiChuFile" />
                  </div>
                </div>
                <div class="title-bg-main">
                  <div class="pull-left">
                    <span class="title mart5">Danh sách thuê bao</span>
                    <a href="javascript:;" class="btn btn-second pad2 lh20 button-not-bg marl10" @click="NAP_DS_HOPDONGTB(vhdkh_id)">
                      <span class="nc-icon-outline arrows-1_refresh-69 f20"></span>
                    </a>
                  </div>
                  <div class="pull-right">
                    <a href="javascript:;" class="btn btn-second pad2 lh20 button-not-bg marl10">
                      <span class="nc-icon-outline ui-2_filter f20"></span>
                    </a>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <div class="table-content" style="max-height: 800px">
                  <DataGrid
                    ref="grid_danhsachthuebao"
                    v-bind:columns="this.options.field_danhsachthuebao"
                    v-bind:dataSource="this.grid_danhsachthuebao"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    :editSettings="editSettings"
                    :showColumnPointer="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    @rowSelected="rowSelected_danhsachthuebao"
                  >
                  </DataGrid>
                </div>
              </div>
              <div id="tab2" class="tab-pane">
                <div class="table-content" style="max-height: 800px">
                  <DataGrid
                    ref="grid_grcListUpGDV"
                    v-bind:columns="this.options.field_grcListUpGDV"
                    v-bind:dataSource="this.grcListUpGDV"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"
                    :editSettings="editSettings"
                    :showColumnPointer="false"
                    :showColumnCheckbox="true"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    @rowSelected="rowSelected_grcListUpGDV"
                    @rowDeselected="rowDeselected_grcListUpGDV"
                  >
                  </DataGrid>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <TraCuu ref="popup_tracuu" :grcTT="this.grcTT" @chapnhan="acceptTraCuu" />
  </div>
</template>
<script>
import api from './api'
import define from './define'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
// import DataGrid from "@/components/Controls/DataGrid";
import DataGridEdit from './components/DataGrid'
import { Aggregate, CommandColumn, Edit, Filter, Freeze, Group, Page, Resize, Sort, Toolbar } from '@syncfusion/ej2-vue-grids'
import VueFlatPickr from 'vue-flatpickr-component'

// import SaveFileModal from "@/modules/contract/profile/ExportData/SaveFileModal.vue";
import TraCuu from './Popup_TraCuu.vue'
import Vue from 'vue'
import XLSX from 'xlsx'
import Popper from 'popper.js'
Popper.Defaults.modifiers.computeStyle.gpuAcceleration = false

export default {
  data() {
    return {
      dividerPosition: 100,
      dpnone: 'none',
      ...define,
      editSettings: {
        allowEditing: false,
        allowAdding: false,
        allowDeleting: false,
        mode: 'Normal'
      },
      selectionOptions: {
        checkboxOnly: true
      },
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true
      },

      button: {
        laytt: true,
        nhapmoi: true,
        capnhat: true,
        huy: true,
        xoaphieu: true,
        themfile: true,
        xoafile: true,
        hdan: true
      },

      params: {
        isPopupShow: false
      },
      tab_selected: 1,
      x: 'box-col box-form col-12',
      y: 'box-col box-form col-2',
      txtMaGD: '',
      dtpNgayTao: new Date(),
      dtpNgayTao_disabled: false,
      txtMaBHS: '',
      txtMaBHS_disabled: false,
      txtMaKH: '',
      txtMaKH_disabled: false,
      txtTenKH: '',
      txtTenKH_disabled: false,
      dtpNgayHT: new Date(),
      dtpNgayHT_disabled: false,
      cboTrangThai: '',
      cboTrangThai_disabled: false,
      cboKhoHS: '',
      cboKhoHS_disabled: false,
      txtGhiChu: '',
      txtGhiChu_disabled: false,
      dtpTuNgay: new Date(),
      dtpDenNgay: new Date(),

      dtpTuNgayTK: '',
      dtpDenNgayTK: '',

      dtpTuNgay_disabled: false,
      dtpDenNgay_disabled: false,

      Visible_themfile: false,
      Visible_xoafile: false,
      Visible_capnhat: false,
      Visible_huy: false,
      Visible_xoaphieu: false,

      chkTimKiem: false,
      ckbAll: true,
      ckbAutoCheck: true,
      popupFilter: '',
      ckbLayAll_DS: true,
      cboNhomFile: '',
      CboLoaiFile: -1,
      txtDuongDan: '',
      txtGhiChuFile: '',

      danhsach: false,
      dtHSThueBao: [],
      fileName: '',

      showInput: false,

      grid_chitiet: [],
      grid_donvinhanhoso: [],
      grid_danhsachbohoso: [],
      grid_danhsachtimkiem: [],
      grid_danhsachthuebao: [],
      grcListUpGDV: [],
      Selected_danhsachbohoso: [],
      options: {
        trangthai: [],
        khohs: [],
        popupFilter: [],
        nhomfile: [],
        loaifile: [],

        field_chitiet: [
          {
            textAlign: 'left',
            fieldName: 'nhom_file',
            headerText: 'Nhóm file',
            allowFiltering: true,
            allowSorting: false,
            isGroupedColumn: true
          },
          {
            textAlign: 'left',
            fieldName: 'ma_tb',
            headerText: 'Số máy/ Acc',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'loai_file',
            headerText: 'Loại file',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ten_file',
            headerText: 'Tên file',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'loai_hd',
            headerText: 'Loại HĐ/PL',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_yc',
            headerText: 'Ngày YC',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'nguoi_cn',
            headerText: 'Người Upload',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_cn',
            headerText: 'Ngày Upload',
            allowSorting: 'true',
            allowFiltering: 'true'
          },

          {
            fieldName: '',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Center',
            width: 180,
            template: function() {
              return {
                template: Vue.component('columnTemplate', {
                  template: `<row><a class="btn btn-huylydo lh14" @click="onDownLoad"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                    <span class="one-search lh14"></span></a>
                                <a class="btn btn-huylydo lh14" @click="onDownLoad"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                    <span class="fa fa-angle-down lh14"></span></a></row>             
                    `,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    async onDownLoad() {
                      if (this.data) {
                        //this.downloadFile(this.data.url, this.data.ten_file)
                        this.downloadFile_old(this.data.url)
                      }
                    },
                    downloadFile(url, filename) {
                      var anchor = document.createElement('a')
                      anchor.setAttribute('download', filename)
                      anchor.setAttribute('href', url)
                      anchor.style.display = 'none'
                      document.body.appendChild(anchor)
                      anchor.click()
                      document.body.removeChild(anchor)
                    },

                    downloadFile_old(url) {
                      var downloadLink = document.createElement('a')
                      downloadLink.href = url
                      downloadLink.target = '_blank'
                      downloadLink.click()
                    }
                  }
                })
              }
            }
          }
        ],

        field_grcListUpGDV: [
          {
            textAlign: 'left',
            fieldName: 'nhom_file',
            headerText: 'Nhóm file',
            allowFiltering: true,
            allowSorting: false,
            isGroupedColumn: true
          },
          {
            textAlign: 'left',
            fieldName: 'ma_tb',
            headerText: 'Số máy/ Acc',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'loai_file',
            headerText: 'Loại file',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ten_file',
            headerText: 'Tên file',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'loai_hd',
            headerText: 'Loại HĐ/PL',
            allowSorting: 'true',
            allowFiltering: 'true'
          },

          {
            textAlign: 'left',
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowSorting: 'true',
            allowFiltering: 'true'
          },

          {
            textAlign: 'left',
            fieldName: 'ma_kh',
            headerText: 'Mã KH',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_yc',
            headerText: 'Ngày YC',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'url',
            headerText: 'Đường dẫn',
            allowSorting: 'true',
            allowFiltering: 'true'
          },

          {
            fieldName: '',
            headerText: 'Xem/Tải về',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Center',
            width: 180,
            template: function() {
              return {
                template: Vue.component('columnTemplate', {
                  template: `<row>
                                <a class="btn btn-huylydo lh14" @click="onDownLoad"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                    <span class="fa fa-angle-down lh14"></span></a></row>             
                    `,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    async onDownLoad() {
                      if (this.data) {
                        //this.downloadFile(this.data.url, this.data.ten_file)
                        this.downloadFile_old(this.data.url)
                      }
                    },
                    downloadFile(url, filename) {
                      var anchor = document.createElement('a')
                      anchor.setAttribute('download', filename)
                      anchor.setAttribute('href', url)
                      anchor.style.display = 'none'
                      document.body.appendChild(anchor)
                      anchor.click()
                      document.body.removeChild(anchor)
                    },

                    downloadFile_old(url) {
                      var downloadLink = document.createElement('a')
                      downloadLink.href = url
                      downloadLink.target = '_blank'
                      downloadLink.click()
                    }
                  }
                })
              }
            }
          },

          {
            textAlign: 'left',
            fieldName: 'nguoi_cn',
            headerText: 'Người Upload',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_cn',
            headerText: 'Ngày Upload',
            allowSorting: 'true',
            allowFiltering: 'true'
          }
        ],

        field_donvinhanhoso: [
          {
            textAlign: 'left',
            fieldName: 'huonggiao',
            headerText: 'Hướng giao',
            allowFiltering: true,
            allowSorting: false,
            isGroupedColumn: true
          },
          {
            textAlign: 'left',
            fieldName: 'ten_dv',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'ten_loaidv',
            headerText: 'Loại đơn vị',
            allowSorting: 'true',
            allowFiltering: 'true'
          }
        ],

        field_danhsachbohoso: [
          {
            textAlign: 'left',
            fieldName: 'ma_bhs',
            headerText: 'Mã bộ HS',
            allowFiltering: true,
            allowSorting: false
          },
          {
            textAlign: 'left',
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'ma_kh',
            headerText: 'Mã KH',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ma_hd',
            headerText: 'Mã HĐ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            textAlign: 'left',
            fieldName: 'ngaytao',
            headerText: 'Ngày tạo',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'ten_kh',
            headerText: 'Tên KH',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'diachi_kh',
            headerText: 'Địa chỉ KH',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ten_dv',
            headerText: 'Đơn vị tạo',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ten_nv',
            headerText: 'Nhân viên tạo',
            allowSorting: 'true',
            allowFiltering: 'true'
          },

          {
            textAlign: 'left',
            fieldName: 'ngay_ht',
            headerText: 'Ngày lưu kho',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'trangthai',
            headerText: 'Trạng thái',
            allowSorting: 'true',
            allowFiltering: 'true'
          }
        ],

        field_danhsachtimkiem: [
          {
            textAlign: 'left',
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowFiltering: true,
            allowSorting: false
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_yc',
            headerText: 'Ngày YC',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'ngay_ht',
            headerText: 'Ngày HT',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ma_kh',
            headerText: 'Mã KH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            textAlign: 'left',
            fieldName: 'ten_kh',
            headerText: 'Tên khách hàng',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'ten_loaihd',
            headerText: 'Loại HĐ',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ten_dv',
            headerText: 'Đơn vị lập HĐ/PL',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ten_nv',
            headerText: 'Người lập',
            allowSorting: 'true',
            allowFiltering: 'true'
          }
        ],

        field_danhsachthuebao: [
          {
            textAlign: 'left',
            fieldName: 'ma_tb',
            headerText: 'Số máy/ Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            textAlign: 'left',
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'trangthai_tb',
            headerText: 'Trạng thái',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'loai_hd',
            headerText: 'Nhóm :',
            allowFiltering: true,
            allowSorting: false,
            isGroupedColumn: true
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_bh',
            headerText: 'Ngày báo hỏng',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'nguoi_bh',
            headerText: 'Người báo hỏng',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_ht',
            headerText: 'Ngày nghiệm thu',
            allowSorting: 'true',
            allowFiltering: 'true'
          }
        ]
      },

      //bieens

      Tag: 1,
      dsQuyen: [],
      tths_id: 1,
      quytrinh_id: 10837,
      load_first: true,
      vhdkh_id: 0,
      vdl_file_id: 0,
      vbohs_id: 0,
      ngay_bd: '',
      ngay_kt: '',
      kq_timkiem: [],
      kieu_timkiem: 0,
      grcTT: [],
      loi: [],

      vnguoi_cn_luoi: '',
      vtenkh_luoi: '',
      vma_bhs_luoi: '',
      vhdkh_id_luoi: '',
      vma_kh_luoi: '',
      vma_gd_luoi: '',
      ngaytao_luoi: '',
      ngay_ht_luoi: '',
      vtrangthai_id: '',
      vnhomfile_id: -1,
      vhoso_id: 0,
      vname: 'TEMP',
      vurl_luoi: '',
      vurl: '',
      batbuoc: -1,
      vbohs_luoi_id: -1,
      vghichu_luoi: '',

      fullFile: [],
      pathFileUpload: '',
      file_refresh: 0,
      loaifile_new: [],

      list_file_id: '',
      array_file_id: []
    }
  },
  components: {
    breadcrumb,
    // DataGrid,
    DataGridEdit,
    VueFlatPickr,
    TraCuu
  },
  provide: {
    grid: [Sort, Filter, Resize, Group, Freeze, CommandColumn, Edit, Aggregate, Toolbar, Page, CommandColumn]
  },

  created() {
    this.Load_quyen()
    this.dtpTuNgay = moment(this.dtpDenNgay)
      .add(-14, 'd')
      .format('DD/MM/YYYY')
    this.LOAD_COMBO()
    this.NAP_DS_HDKH_CHUACOHS()
    if (this.tths_id != 6) {
      this.TIMKIEM('')
    } else {
      //nạp treeview
    }
  },

  async mounted() {
    await this.$nextTick(function() {
      var popper = new Popper(this.$refs['showInputButton'], this.$refs['dropdown'], {
        placement: 'bottom-start'
      })
    })
    var tags = this.$route.query.tag.split('-')
    if (tags.length > 1) {
      switch (tags[0]) {
        case '1':
          this.quytrinh_id = tags[1]
          break
        case '2':
          break
      }
    }
  },

  watch: {
    cboNhomFile(newval) {
      this.vnhomfile_id = newval
      this.NAP_DS_HOPDONGTB(this.vhdkh_id)
      this.cboNhomFile = newval
      this.get_loai_file(newval)
      this.CboLoaiFile = -1
      this.grid_danhsachthuebao = []
    },
    CboLoaiFile(newval) {
      for (var i = 0; i < this.loaifile_new.length; i++) {
        if (this.loaifile_new[i].loaifile_id == this.CboLoaiFile) {
          this.batbuoc = this.loaifile_new[i].kieu
        }
      }
      this.CboLoaiFile = newval
      this.Load_dstb(this.vhdkh_id)
    }
  },

  methods: {
    handleDragging(e) {
      var percentage = (e.pageX / window.innerWidth) * 100
      if (percentage >= 10 && percentage <= 90) {
        this.dividerPosition = percentage.toFixed(2)
      }
    },
    startDragging() {
      document.addEventListener('mousemove', this.handleDragging)
    },
    endDragging() {
      document.removeEventListener('mousemove', this.handleDragging)
    },

    async Load_dstb(bien_hdkh_id) {
      // var res =  await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, {
      //             "vhdkh_id": bien_hdkh_id
      //     })

      //hot fix
      var res = await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, {
        vhdkh_id: bien_hdkh_id
      })

      this.grid_danhsachthuebao = res.data.data ? res.data.data : []
      this.LOC_LUOI_TB()
    },

    async Load_quyen() {
      var res = await api.ds_quyen_nguoidung(this.axios, {})
      this.dsQuyen = res.data.data ? res.data.data : []
    },

    async LOAD_COMBO() {
      this.loading(true)
      var res = await api.trangthai_bo_hoso(this.axios, {})
      var kq = res.data.data ? res.data.data : []
      var items = []
      kq.forEach(function(item) {
        if (item.NAME != null) {
          items.push({ id: item.ID, text: item.NAME })
        }
      })
      this.options.trangthai = items
      this.cboTrangThai = items[0].id

      res = await api.kho_hoso(this.axios, {})
      kq = res.data.data ? res.data.data : []
      items = []
      kq.forEach(function(item) {
        if (item.NAME != null) {
          items.push({ id: item.ID, text: item.NAME })
        }
      })
      this.options.khohs = items
      this.cboKhoHS = items[0].id

      res = await api.nhom_file(this.axios, {})

      //doing
      // res =  await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, {
      //   vhdkh_id:  this.vhdkh_id
      // })

      kq = res.data.data ? res.data.data : []
      items = []
      kq.forEach(function(item) {
        if (item.NAME != null) {
          items.push({ id: item.ID, text: item.NAME })
        }
      })
      this.options.nhomfile = items
      this.cboNhomFile = items[0].id

      res = await api.loai_file(this.axios, {})

      kq = res.data.data ? res.data.data : []
      items = []
      kq.forEach(function(item) {
        if (item.loai_file != null) {
          items.push({ id: item.loaifile_id, text: item.loai_file })
        }
      })
      this.options.loaifile = items
      this.CboLoaiFile = items[0].id
      this.loading(false)
    },

    async NAP_DS_HDKH_CHUACOHS() {
      this.loading(true)
      var loai_id = 0
      if (this.ckbLayAll_DS == true) {
        loai_id = this.$root.token.getNguoiDungID()
      }

      var denngay = new Date()
      var tungay = moment(denngay)
        .add(-1, 'M')
        .format('DD/MM/YYYY')
      denngay = moment(denngay).format('DD/MM/YYYY')

      var res = await api.layds_hopdong_chuaco_hoso(this.axios, {
        loai_id: loai_id,
        tungay: tungay,
        denngay: denngay
      })
      this.grid_danhsachtimkiem = res.data.data ? res.data.data : []

      this.loading(false)
    },

    KHOAMO_FORM(mo) {
      this.dtpNgayTao_disabled = !mo
      this.dtpNgayHT_disabled = !mo
      this.txtMaBHS_disabled = !mo
      this.txtMaKH_disabled = !mo
      this.txtGhiChu_disabled = !mo
      this.cboKhoHS_disabled = !mo
      this.cboTrangThai_disabled = !mo
      this.txtTenKH_disabled = !mo
    },

    XOA_FORM() {
      this.vhdkh_id = 0
      this.cboKhoHS = ''
      this.dtpNgayTao = ''
      this.dtpNgayHT = ''
      this.txtMaGD = ''
      this.txtMaBHS = ''
      this.txtMaKH = ''
      this.txtTenKH = ''
      this.txtGhiChu = ''
      this.cboTrangThai = 1
      this.grid_chitiet = []
      this.grid_donvinhanhoso = []
      this.button.themfile = true
      this.button.xoafile = true
      this.Visible_themfile = false
      this.Visible_xoafile = false
      this.KHOAMO_FORM(false)
      this.Visible_capnhat = false
      this.Visible_huy = false
      this.button.xoaphieu = false
      this.btnClear_Click()
    },

    THAOTAC_NUT(kieu) {
      if (kieu == 1) {
        this.vbohs_id = 0
        this.XOA_FORM()
        this.KHOAMO_FORM(true)
        // Cập nhật nút
        this.Visible_huy = true
        this.Visible_capnhat = true
        this.button.nhapmoi = true
        this.Visible_themfile = false
        this.Visible_xoafile = false
        this.button.nhapmoi = false
        this.$refs.magd.focus()
      } else if (kieu == 3) {
        this.XOA_FORM()
        this.Visible_capnhat = false
        this.Visible_huy = false
        this.button.nhapmoi = true
        this.Visible_themfile = false
        this.Visible_xoafile = false
      }
    },

    async READ_DATA(dr) {
      if (dr.bohs == 0) {
        //  Nhập mới
        this.THAOTAC_NUT(1)
        this.txtMaBHS = ''
        this.vhdkh_id = dr.hdkh_id
        this.txtMaKH = dr.ma_kh
        this.txtMaGD = dr.ma_gd
        this.txtTenKH = dr.ten_kh
        this.cboTrangThai = 1
        this.dtpNgayTao = new Date()
        this.dtpNgayHT = ''
        this.txtGhiChu = ''
      } else {
        this.TIMKIEM(dr.ma_gd)
      }
    },

    async TIMKIEM_SQL(ma_gd) {
      var res = await api.layds_theo_matb(this.axios, {
        ma_thuebao: ma_gd
      })
      var ds_hdkh = res.data.data ? res.data.data : []
      var arr = ds_hdkh

      var arrUniq = [...new Map(arr.map((v) => [JSON.stringify([v.ma_gd, v.ma_kh, v.ngay_yc, v.hdkh_id, v.donvi_id, v.ten_dv, v.loaihd_id, v.ten_loaihd, v.bohs, v.ten_kh]), v])).values()]
      ds_hdkh = arrUniq

      if (ds_hdkh.length == 0) {
        this.$toast.error('Không tìm thấy thông tin với mã giao dịch ' + ma_gd)
        this.kq_timkiem = []
        this.kieu_timkiem = 0
        return
      }

      if (ds_hdkh.length == 1) {
        if (ds_hdkh[0].bohs == 0) {
          //nếu =0 thì ko có dữ liệu
          this.kq_timkiem = ds_hdkh
          this.kieu_timkiem = 1 // Nhập mới
          return
        } else {
          var vtimall = 0
          if (this.ckbAll == true) {
            vtimall = 1
          }
          res = await api.lay_ds_bo_hs(this.axios, {
            vdenngay: '',
            vhdkh_id: ds_hdkh[0].hdkh_id,
            vtimall: vtimall,
            vtths_id: this.tths_id,
            vtungay: ''
          })

          this.kq_timkiem = res.data.data ? res.data.data : []
          this.kieu_timkiem = 2
          console.log('kq_timkiem', this.kq_timkiem, this.kieu_timkiem)
          return
        }
      } else {
        this.kq_timkiem = ds_hdkh
        this.kieu_timkiem = 3
        return
      }
    },

    async TIMKIEM_ALL() {
      this.loading(true)
      var vdl_tungay = ''
      if (this.dtpTuNgay != null) {
        vdl_tungay = moment(this.dtpTuNgay, 'DD/MM/YYYY').format('DD/MM/YYYY')
      }
      var vdl_denngay = ''
      if (this.dtpDenNgay != null) {
        vdl_denngay = moment(this.dtpDenNgay, 'DD/MM/YYYY').format('DD/MM/YYYY')
      }
      console.log('vdl_denngay', moment(this.dtpDenNgay, 'DD/MM/YYYY').format('DD/MM/YYYY'), this.dtpDenNgay)

      var vtimall = 0
      if (this.ckbAll == true) {
        vtimall = 1
      }
      var res = await api.lay_ds_bo_hs(this.axios, {
        vdenngay: vdl_denngay,
        vhdkh_id: 0,
        vtimall: vtimall,
        vtths_id: this.tths_id,
        vtungay: vdl_tungay
      })
      this.kq_timkiem = res.data.data ? res.data.data : []
      this.loading(false)
    },

    async TIMKIEM(ma_gd) {
      this.vbohs_id = 0
      this.grcTT = []
      this.grid_danhsachbohoso = []
      this.THAOTAC_NUT(1)
      if (ma_gd != '') {
        //Tìm kiếm theo mã gd
        this.loading(true)
        this.txtMaGD = ma_gd
        await this.TIMKIEM_SQL(ma_gd)
        if (this.kieu_timkiem == 1) {
          // Nhập mới
          if (this.kq_timkiem.length > 0) {
            this.READ_DATA(this.kq_timkiem[0])
          }
        } else if (this.kieu_timkiem == 2) {
          // Có dữ liệu sẽ load lưới
          if (this.kq_timkiem.length > 0) {
            this.grid_danhsachbohoso = this.kq_timkiem
          } else {
            this.$toast.error(ma_gd + ' mã này được tạo bộ hồ sơ. Bạn hãy tra cứu để biết thêm chi tiết')
          }
        } else if (this.kieu_timkiem == 3) {
          // Send ra danh sách để chọn
          this.grcTT = this.kq_timkiem
          this.$refs.magd.focus()
          //show popup
          this.$refs.popup_tracuu.showModal()
        }
        this.loading(false)
      } else {
        //Tìm kiếm tất cả
        await this.TIMKIEM_ALL()
        if (this.kq_timkiem.length == 0) {
          if ((this.load_first = true)) {
            this.load_first == false
          } else {
            this.$toast.error('Không tìm thấy dữ liệu')
          }
        } else {
          this.grid_danhsachbohoso = this.kq_timkiem
        }
      }
    },

    async KIEMTRA_DULIEU() {
      if (this.vhdkh_id == 0) {
        this.$toast.error('Bạn chưa chọn mã giao dịch hợp đồng của khách hàng để lập hồ sơ')
        return false
      }
      if (this.vnguoi_cn_luoi != (await this.$root.token.getUserName()) && this.button.nhapmoi == true) {
        this.$toast.error('Bạn không được quyền cập nhật bộ hồ sơ của người khác')
        return false
      }
      if (this.tths_id == 6 && this.button.nhapmoi == false) {
        this.$toast.error('Không được quyền thêm mới hồ sơ ở chức năng này')
        return false
      }
      return true
    },

    async CAPNHAT() {
      if (!(await this.KIEMTRA_DULIEU())) {
        return
      }
      this.ngay_bd = moment(this.dtpTuNgay, 'DD/MM/YYYY').format('DD/MM/YYYY')
      this.ngay_kt = moment(this.dtpDenNgay, 'DD/MM/YYYY').format('DD/MM/YYYY')
      var kieu = 0
      if (this.button.nhapmoi == false) {
        //Nhập mới
        kieu = 1
      }

      console.log('bohs_id', this.vbohs_id, 'ghichu', this.txtGhiChu, 'hdkh_id', this.vhdkh_id, 'hoso_id', 0, 'kieu', kieu, 'quytrinh_id', this.quytrinh_id, 'tths_id', this.tths_id)

      var res = await api.capnhat_bohoso(this.axios, [
        {
          bohs_id: this.vbohs_id,
          ghichu: this.txtGhiChu,
          hdkh_id: this.vhdkh_id,
          hoso_id: 0,
          kieu: kieu,
          quytrinh_id: this.quytrinh_id,
          tths_id: this.tths_id
        }
      ])
      var kq = res.data.data[0] ? res.data.data[0] : ''

      if (kq.substring(0, 2) == 'OK') {
        if (this.button.nhapmoi == false) {
          //Nhập mới
          this.$toast.success('Tạo bộ hồ sơ thành công')
        } else {
          this.$toast.success('Cập nhật thông tin hồ sơ thành công')
        }
        this.dtpTuNgay = moment(this.ngay_bd, 'DD/MM/YYYY').format('DD/MM/YYYY')
        if (this.button.nhapmoi == false) {
          //Nhập mới
          this.dtpDenNgay = moment(new Date()).format('DD/MM/YYYY')
        } else {
          this.dtpDenNgay = moment(this.ngay_kt, 'DD/MM/YYYY').format('DD/MM/YYYY')
        }
        this.TIMKIEM('')
      } else {
        this.$toast.error(kq)
      }
    },

    async get_loai_file(cboNhomFile_new) {
      var res = await api.loai_file(this.axios, {})
      this.loaifile_new = res.data.data ? res.data.data : []
      this.loaifile_new = this.loaifile_new.filter(function(ds) {
        return ds.nhomfile_id == -1 || ds.nhomfile_id == cboNhomFile_new
      })

      var items = []
      this.loaifile_new.forEach(function(item) {
        if (item.loai_file != null) {
          items.push({ id: item.loaifile_id, text: item.loai_file })
        }
      })
      this.options.loaifile = items
      //update:gan file dau tien
      this.CboLoaiFile = items.length > 0 ? items[0].loaifile_id : ''
    },

    async NAP_DS_HOPDONGTB(bien_hdkh_id) {
      this.CboLoaiFile = -1
      this.cboNhomFile = -1
      this.txtDuongDan = ''
      this.txtGhiChuFile = ''
      this.grid_danhsachthuebao = []

      //hot fix
      // var res =  await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, {
      //         "vhdkh_id": bien_hdkh_id
      // })
      var res = await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, { vhdkh_id: bien_hdkh_id })
      if (res && (res.data.error_code == 'BSS-00000000' || res.data.error_code == 'BSS-00000204')) {
        this.grid_danhsachthuebao = res.data.data ? res.data.data : []
      } else {
        this.$toast.error(res.data.message)
        this.grid_danhsachthuebao = []
      }

      ///duy26/04

      this.grcListUpGDV = []
      var res1 = await api.lay_ds_filehs_theo_kh(this.axios, {}, bien_hdkh_id)
      if (res1 && (res1.data.error_code == 'BSS-00000000' || res1.data.error_code == 'BSS-00000204')) {
        this.grcListUpGDV = res1.data.data ? res1.data.data : []
      } else {
        this.$toast.error(res1.data.message)
        this.grcListUpGDV = []
      }

      console.log('this.grcListUpGDV', this.grcListUpGDV)
      ///end duy 26/04

      this.LOC_LUOI_TB()
    },

    async LOC_LUOI_TB() {
      // this.grid_danhsachthuebao = this.grid_danhsachthuebao.filter(function (ds) {
      //       return ds.chk == 0
      // });

      if (this.CboLoaiFile == -1) {
        this.batbuoc = -1
      }

      if (this.button.themfile == false) {
        if (this.batbuoc == 1) {
          console.log('this.vnhomfile_id', this.vnhomfile_id)
          //Nếu ko phải bắt buộc
          if ((this.vnhomfile_id == 1 || this.vnhomfile_id <= 0) && this.vnhomfile_id != -1) {
            this.grid_danhsachthuebao = this.grid_danhsachthuebao.filter(function(ds) {
              return ds.loaihd_id != 0 && ds.ma_kh == 0
            })
          } else if (this.vnhomfile_id == -1) {
          } else {
            this.grid_danhsachthuebao = this.grid_danhsachthuebao.filter(function(ds) {
              return ds.loaihd_id == 0 && ds.ma_kh == 0
            })
          }
        }

        if (this.batbuoc == 2) {
          console.log('this.vnhomfile_id_2', this.vnhomfile_id)
          var ind = []
          for (var i = 0; i < this.grid_danhsachthuebao.length; i++) {
            if (this.grid_danhsachthuebao[i].hdkh_id != null && this.grid_danhsachthuebao[i].hdkh_id != 0) {
              ind.push(i)
            }
          }
          this.$refs.grid_danhsachthuebao.flagSelectedRowIndexes = ind
          //Nếu ko phải bắt buộc
          if ((this.vnhomfile_id == 1 || this.vnhomfile_id <= 0) && this.vnhomfile_id != -1) {
            this.grid_danhsachthuebao = this.grid_danhsachthuebao.filter(function(ds) {
              return ds.loaihd_id != 0 && ds.ma_kh != 0
            })
          } else if (this.vnhomfile_id == -1) {
          } else {
            this.grid_danhsachthuebao = this.grid_danhsachthuebao.filter(function(ds) {
              return ds.loaihd_id == 0 && ds.ma_kh != 0
            })
          }
        }
      }
    },

    async NAP_DS_CHITIET_TT() {
      this.vbohs_id = this.vbohs_luoi_id
      this.vhdkh_id = this.vhdkh_id_luoi
      this.txtMaKH = this.vma_kh_luoi
      this.txtMaGD = this.vma_gd_luoi
      this.txtMaBHS = this.vma_bhs_luoi
      this.txtGhiChu = this.vghichu_luoi
      this.txtTenKH = this.vtenkh_luoi
      this.dtpNgayTao = moment(this.ngaytao_luoi).format('DD/MM/YYYY')
      if (this.ngay_ht_luoi != null) {
        this.dtpNgayHT = moment(this.ngay_ht_luoi).format('DD/MM/YYYY')
      }
      if (this.vhoso_id != 0) {
        this.cboKhoHS = this.vhoso_id
      }
      this.cboTrangThai = this.vtrangthai_id
      if (this.dsQuyen.includes(2075) == false && this.tths_id == 6) {
        this.KHOAMO_FORM(true)
      } else {
        this.Visible_capnhat = true
        this.Visible_themfile = true
        this.Visible_xoafile = true
        this.Visible_huy = true
        this.Visible_xoaphieu = true
        this.button.xoaphieu = true
        this.button.nhapmoi = true
      }
      this.grcTT = []
      this.LOADFILE_HS()
      this.NAP_DS_HOPDONGTB(this.vhdkh_id_luoi)
    },

    async LOADFILE_HS() {
      this.grid_chitiet = []
      this.grid_donvinhanhoso = []
      if (this.vbohs_id == 0) {
        this.XOA_FORM()
        return
      }
      var res = await api.layds_file_trong_hoso(this.axios, { hoso_id: this.vbohs_id })
      if (res && (res.data.error_code == 'BSS-00000000' || res.data.error_code == 'BSS-00000204')) {
        this.grid_chitiet = res.data.data ? res.data.data : []
      } else {
        this.$toast.error(res.data.message)
        this.grid_chitiet = []
      }

      var res1 = await api.layds_donvi_nhan_hoso(this.axios, { hoso_id: this.vbohs_id })
      if (res1 && (res1.data.error_code == 'BSS-00000000' || res1.data.error_code == 'BSS-00000204')) {
        this.grid_donvinhanhoso = res1.data.data ? res1.data.data : []
      } else {
        this.$toast.error(res1.data.message)
        this.grid_donvinhanhoso = []
      }
    },

    async UploadFileHoSo() {
      try {
        if (this.txtDuongDan && this.fullFile && this.fullFile.length > 0) {
          var formData = new FormData()
          for (var file of this.fullFile) {
            formData.append('files', file)
          }
          var response = await api.upload_file_hoso(this.axios, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          var kq = response.data.data ? response.data.data : ''
          this.pathFileUpload = kq.join('|')
          this.capnhat_file_hoso()
        }
      } catch (e) {
        this.$toast.error(e)
      }
    },

    async capnhat_file_hoso() {
      try {
        if (!this.button.themfile && !this.danhsach) {
          //Up lẻ
          //hot fix
          var input = {
            vkieu: 1,
            vhoso_id: 0,
            vloaifile_id: this.CboLoaiFile,
            vfile_cn: 0, //aka file_cn
            vbohs_id: this.vbohs_id,
            vurl: this.pathFileUpload,
            vghichu: this.txtGhiChu,
            vfile_clob: JSON.stringify(
              this.UpperCasePropertyList([
                {
                  file_id: 0,
                  hdtb_id: 0,
                  thuebao_id: 0,
                  trangthai: 0
                }
              ])
            ),
            vds_hs_thuebao: JSON.stringify(this.UpperCasePropertyList(this.dtHSThueBao))
          }
          var res = await api.fn_capnhat_file_hstb(this.axios, input)

          var kq = res.data ? res.data : ''
          if (!kq || kq.error_code != 'BSS-00000000' || kq.data.includes('ERROR') == true) {
            this.DeleteFileOnServer(this.pathFileUpload)
            var error = kq.data ? kq.data : kq.message_detail ? kq.message_detail : 'Lỗi ghi lại file'
            this.$toast.error(error)
            return
          }

          this.$toast.success('Thêm mới file hồ sơ thành công!')
          var ma_gd = this.txtMaGD
          this.btnClear_Click()
          this.LOADFILE_HS()
          await this.FocusGridViewRow('grid_danhsachbohoso', 'ma_gd', ma_gd)
        } else if (!this.button.themfile && this.danhsach) {
          var ar = []
          var dt_kt = this.$refs.grid_danhsachbohoso.getSelectedRecords()

          for (var i = 0; i < dt_kt.length; i++) {
            ar.push({
              BOHS_ID: dt_kt[i].bohs_id,
              HDKH_ID: dt_kt[i].hdkh_id,
              MA_KH: dt_kt[i].ma_kh,
              MA_HD: dt_kt[i].ma_hd
            })
          }

          var res = await api.capnhat_filehs_ds(this.axios, {
            vbohs_id: ar,
            vfile_clob: '',
            vghichu: this.txtGhiChu,
            vkieu: 1,
            vloaifile_id: this.CboLoaiFile,
            vma_kh: ar[0].ma_hd,
            vurl: this.pathFileUpload
          })
          //hot fix
          var kq = res.data ? res.data : ''
          if (!kq || kq.error_code != 'BSS-00000000' || kq.data.includes('ERROR') == true) {
            this.DeleteFileOnServer(this.pathFileUpload)
            var error = kq.data ? kq.data : kq.message_detail ? kq.message_detail : 'Lỗi ghi lại file'
            this.$toast.error(error)
            return
          }

          this.$toast.success('Thêm mới file hồ sơ thành công!')
          var ma_gd = this.txtMaGD
          this.btnClear_Click()
          this.LOADFILE_HS()
          await this.FocusGridViewRow('grid_danhsachbohoso', 'ma_gd', ma_gd)
        }
      } catch (ex) {
        this.DeleteFileOnServer(this.pathFileUpload)
        this.$toast.error('Lưu file thất bại: ' + kq)
        return
      }
    },

    async XOA_BOHOSO() {
      this.$confirm(`Bạn thật sự muốn xoá bộ hồ sơ với mã giao dịch ` + this.vma_gd_luoi + ` không?`, {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      }).then(async () => {
        if (this.vnguoi_cn_luoi != (await this.$root.token.getUserName())) {
          this.$toast.error('Bạn không được phép xóa bộ hồ sơ của người khác')
          return
        }
        var res = await api.capnhat_bohoso(this.axios, [
          {
            bohs_id: this.vbohs_id,
            ghichu: this.txtGhiChu,
            hdkh_id: this.vhdkh_id,
            hoso_id: 0,
            kieu: 3,
            quytrinh_id: this.quytrinh_id,
            tths_id: this.tths_id
          }
        ])
        var kq = res.data.data ? res.data.data : ''
        if (kq == null) {
          this.$toast.error('Không nhận được thông tin phản hồi khi xóa phiếu')
          return
        }
        if (kq == 1) {
          // Xóa file sever
          if (this.grid_chitiet.length > 0) {
            for (var i = 0; i < this.grid_chitiet.length; i++) {
              if (this.grid_chitiet[i].url == null || (this.grid_chitiet[i].machung != null && this.grid_chitiet[i].nhom_id != null)) {
                if (this.grid_chitiet[i].machung != null && this.grid_chitiet[i].nhom_id != null) {
                  res = await api.sp_lay_file_hs_nhom_id(this.axios, {
                    file_id: this.grid_chitiet[i].file_id,
                    nhom_id: this.grid_chitiet[i].nhom_id
                  })
                  var dt = res.data.data ? res.data.data : []
                  if (dt[0] == 0) {
                    if (this.grid_chitiet[i].url != null) {
                      this.DeleteFileOnServer(this.grid_chitiet[i].url)
                    }
                  }
                }
              } else {
                if (this.grid_chitiet[i].url != null) {
                  this.DeleteFileOnServer(this.grid_chitiet[i].url)
                }
              }
            }
          }

          this.$toast.success('Xóa bộ hồ sơ thành công')
          // this.XOA_FORM()
          // this.btnClear_Click()
          this.TIMKIEM('')
        }
      })
    },

    async DeleteFileOnServer(url) {
      var res = await api.xoa_file(this.axios, {
        list_file: url
      })
      var kq = res.data.error_code ? res.data.error_code : 0
      if (kq == 'BSS-00000000') {
        return true
      } else {
        return false
      }
    },

    async xoa_file_bsh_theo_fileid(file_id) {
      var res = await api.xoa_file_bsh_theo_fileid(this.axios, { ID: file_id })
    },

    async XOA_FILE() {
      try {
        if (this.grid_chitiet.length > 0) {
          var SelectedItems = this.$refs.grid_chitiet.getSelectedRecords()
          if (SelectedItems.length == 0) {
            this.$toast.error('Bạn phải chọn ít nhất một file để xóa')
          } else {
            var hsIsNull = SelectedItems.filter((e) => e.hs_id == null)

            var hsIsNotNull = SelectedItems.filter((e) => e.hs_id != null)

            // Check dk ho so duoc chenull
            if (hsIsNull.length > 0) {
              var nguoi_cn = await this.$root.token.getUserName()

              if (nguoi_cn != 'admin') {
                var dr = hsIsNull.filter(function(ds) {
                  return ds.nguoi_cn != nguoi_cn
                })
                if (dr.length > 0) {
                  this.$toast.error('Bạn không được quyền sửa xóa file hồ sờ của người dùng khác')
                  return
                }
              }
              this.$confirm(`Bạn có chắc chắn xóa nhiều file hồ sơ được chọn không ?`, {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
                type: 'warning'
              }).then(async () => {
                var file_clob = '<ds>'
                for (var i = 0; i < hsIsNull.length; i++) {
                  file_clob += '<vt fid="' + hsIsNull[i].file_id + '"/>'
                }

                file_clob += '</ds>'

                var input = {
                  bohoso_id: this.vbohs_id,
                  file_clob: file_clob,
                  file_cn: this.vdl_file_id,
                  ghichu: this.txtGhiChu,
                  hoso_id: 0,
                  kieu: 3,
                  loaifile_id: this.CboLoaiFile,
                  file_url: ''
                }
                //hot fix
                var res = await api.capnhat_file_hoso(this.axios, input)

                var kq = res.data ? res.data : 'ERROR'
                if (!kq || kq.error_code != 'BSS-00000000' || kq.data.includes('ERROR') == true) {
                  this.$toast.error(res.data.message_detail)
                  return
                }
                for (var i = 0; i < hsIsNull.length; i++) {
                  if (hsIsNull[i].url == null || (hsIsNull[i].machung != null && hsIsNull[i].nhom_id != null)) {
                    if (hsIsNull[i].machung != null && hsIsNull[i].nhom_id != null) {
                      res = await api.sp_lay_file_hs_nhom_id(this.axios, {
                        file_id: hsIsNull[i].file_id,
                        nhom_id: hsIsNull[i].nhom_id
                      })
                      var dt = res.data.data ? res.data.data : []
                      if (dt[0] == 0) {
                        if (hsIsNull[i].url != null) {
                          this.DeleteFileOnServer(hsIsNull[i].url)
                        }
                      }
                    }
                  } else {
                    if (hsIsNull[i].url != null) {
                      this.DeleteFileOnServer(hsIsNull[i].url)
                    }
                  }
                }
                //hot fix gan file

                // Check dk ho so duoc cho null
                if (hsIsNotNull.length > 0) {
                  var list_update_id = []
                  hsIsNotNull.forEach((item) => {
                    list_update_id.push({ FILE_ID: item.file_id })
                  })
                  var input = {
                    bohs_id: this.vbohs_id,
                    file_clob: JSON.stringify(list_update_id),
                    file_cn: this.vdl_file_id,
                    ghichu: this.txtGhiChu,
                    hoso_id: 0,
                    kieu: 8,
                    loaifile_id: this.CboLoaiFile,
                    url: ''
                  }
                  var res = await api.capnhat_file_hoso(this.axios, input)
                  var kq = res.data.data ? res.data.data : 'ERROR'
                  if (res.data.error_code != 'BSS-00000000') {
                    this.$toast.error(res.data.message)
                    return
                  }
                  if (kq.includes('ERROR') == true) {
                    this.$toast.error(res.data.message)
                    return
                  }
                }
                this.$toast.success('Xóa/Gỡ thành công file hồ sơ')
                this.btnClear_Click()
                this.LOADFILE_HS()
              })
            }
          }
        }
      } catch (e) {}
    },

    rowSelected_chitiet(subject) {
      this.vname = subject.data.name
      this.vurl_luoi = subject.data.url
    },

    rowSelected_danhsachthuebao(subject) {
      //subject.data.chk = 1
      // this.tab_selected = 1
    },

    rowSelected_grcListUpGDV(subject) {
      if (subject.data) {
        if (Array.isArray(subject.data)) {
          this.array_file_id = subject.data.reduce((result, current) => result.concat({ file_id: current.file_id }), [])
        } else {
          this.array_file_id.push({
            file_id: subject.data.file_id
          })
        }
      }
    },

    rowDeselected_grcListUpGDV(subject) {
      if (subject.data) {
        var tmp = this.array_file_id
        if (Array.isArray(subject.data)) {
          for (var i = 0; i < subject.data.length; i++) {
            tmp.splice(
              tmp.findIndex((a) => a.file_id == subject.data[i].file_id),
              1
            )
          }
        } else {
          tmp.splice(
            tmp.findIndex((a) => a.file_id == subject.data.file_id),
            1
          )
        }
        this.array_file_id = tmp
      }
    },

    rowSelected_danhsachbohoso(subject) {
      if (Array.isArray(subject.data)) {
        // this.vbohs_luoi_id = subject.data[0].bohs_id
        // this.vhs_luoi_id = subject.data[0].hoso_id
        // this.vghichu_luoi = subject.data[0].ghichu
        // this.vtenkh_luoi = subject.data[0].ten_kh
        // this.vnguoi_cn_luoi = subject.data[0].nguoi_cn
        // this.vma_bhs_luoi = subject.data[0].ma_bhs
        // this.vhdkh_id_luoi = subject.data[0].hdkh_id
        // this.vma_kh_luoi = subject.data[0].ma_kh
        // this.vma_gd_luoi = subject.data[0].ma_gd
        // this.ngaytao_luoi = subject.data[0].ngaytao
        // this.ngay_ht_luoi = subject.data[0].ngay_ht
        // this.vtrangthai_id = subject.data[0].trangthai_id
        this.Selected_danhsachbohoso = subject.data
        // this.NAP_DS_CHITIET_TT()
      } else {
        // this.vbohs_luoi_id = subject.data.bohs_id
        // this.vhs_luoi_id = subject.data.hoso_id
        // this.vghichu_luoi = subject.data.ghichu
        // this.vtenkh_luoi = subject.data.ten_kh
        // this.vnguoi_cn_luoi = subject.data.nguoi_cn
        // this.vma_bhs_luoi = subject.data.ma_bhs
        // this.vhdkh_id_luoi = subject.data.hdkh_id
        // this.vma_kh_luoi = subject.data.ma_kh
        // this.vma_gd_luoi = subject.data.ma_gd
        // this.ngaytao_luoi = subject.data.ngaytao
        // this.ngay_ht_luoi = subject.data.ngay_ht
        // this.vtrangthai_id = subject.data.trangthai_id
        this.Selected_danhsachbohoso = [subject.data]
        // this.NAP_DS_CHITIET_TT()
      }
    },
    rowDeselected_danhsachbohoso(subject) {
      if (subject.data) {
        var tmp = this.Selected_danhsachbohoso
        if (Array.isArray(subject.data)) {
          for (var i = 0; i < subject.data.length; i++) {
            tmp.splice(
              tmp.findIndex((a) => a.bohs_id == subject.data[i].bohs_id),
              1
            )
          }
        } else {
          tmp.splice(
            tmp.findIndex((a) => a.bohs_id == subject.data.bohs_id),
            1
          )
        }
        this.Selected_danhsachbohoso = tmp
      }
      if (tmp && tmp.length > 0) {
        this.vbohs_luoi_id = tmp[0].bohs_id
        this.vhs_luoi_id = tmp[0].hoso_id
        this.vghichu_luoi = tmp[0].ghichu
        this.vtenkh_luoi = tmp[0].ten_kh
        this.vnguoi_cn_luoi = tmp[0].nguoi_cn
        this.vma_bhs_luoi = tmp[0].ma_bhs
        this.vhdkh_id_luoi = tmp[0].hdkh_id
        this.vma_kh_luoi = tmp[0].ma_kh
        this.vma_gd_luoi = tmp[0].ma_gd
        this.ngaytao_luoi = tmp[0].ngaytao
        this.ngay_ht_luoi = tmp[0].ngay_ht
        this.vtrangthai_id = tmp[0].trangthai_id
      } else {
        this.grid_chitiet = []
        this.grid_donvinhanhoso = []
      }
    },
    recordClick_danhsachbohoso(subject) {
      this.vbohs_luoi_id = subject.rowData.bohs_id
      this.vbohs_id = subject.rowData.bohs_id
      this.vhs_luoi_id = subject.rowData.hoso_id
      this.vghichu_luoi = subject.rowData.ghichu
      this.vtenkh_luoi = subject.rowData.ten_kh
      this.vnguoi_cn_luoi = subject.rowData.nguoi_cn
      this.vma_bhs_luoi = subject.rowData.ma_bhs
      this.vhdkh_id_luoi = subject.rowData.hdkh_id
      this.vma_kh_luoi = subject.rowData.ma_kh
      this.vma_gd_luoi = subject.rowData.ma_gd
      this.ngaytao_luoi = subject.rowData.ngaytao
      this.ngay_ht_luoi = subject.rowData.ngay_ht
      this.vtrangthai_id = subject.rowData.trangthai_id
      this.NAP_DS_CHITIET_TT()
    },
    record_init(subject) {
      this.vbohs_luoi_id = subject.bohs_id
      this.vbohs_id = subject.bohs_id
      this.vhs_luoi_id = subject.hoso_id
      this.vghichu_luoi = subject.ghichu
      this.vtenkh_luoi = subject.ten_kh
      this.vnguoi_cn_luoi = subject.nguoi_cn
      this.vma_bhs_luoi = subject.ma_bhs
      this.vhdkh_id_luoi = subject.hdkh_id
      this.vma_kh_luoi = subject.ma_kh
      this.vma_gd_luoi = subject.ma_gd
      this.ngaytao_luoi = subject.ngaytao
      this.ngay_ht_luoi = subject.ngay_ht
      this.vtrangthai_id = subject.trangthai_id
      this.NAP_DS_CHITIET_TT()
    },

    async FocusGridViewRow(grid, fieldname, value) {
      let indexs = this[grid].findIndex((a) => a[fieldname] != null && a[fieldname].toString() == value.toString())
      if (indexs != -1) {
        if (!(indexs + 1 > this.$refs[grid].pageIndexData * this.$refs[grid].pageSizeData && indexs + 1 <= (this.$refs[grid].pageIndexData + 1) * this.$refs[grid].pageSizeData)) {
          this.$refs[grid].$refs.pagination.gotoPage(parseInt(indexs / this.$refs[grid].pageSizeData))
        }
        let self = this
        let ind = parseInt(indexs % this.$refs[grid].pageSizeData)
        setTimeout(() => {
          self.$refs[grid].grid.selectRow(ind, true)
          this.record_init(this[grid][indexs])
        }, 1000)
      }
    },

    async btnTaoDS_HS_Click() {
      if (this.grid_danhsachtimkiem.length == 0) {
        this.$toast.error('Không có dữ liệu để tạo hồ sơ ')
        return
      }
      var SelectedItems = this.$refs.grid_danhsachtimkiem.getSelectedRecords()
      console.log('SelectedItems', SelectedItems)
      if (SelectedItems.length == 0) {
        this.$toast.error('Bạn chưa chọn danh sách hợp đồng cần tạo hồ sơ ')
        return
      }

      this.$confirm(`Bạn có muốn tạo danh sách hồ sơ hợp đồng đã chọn không?`, {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      }).then(async () => {
        var dem = 0
        this.loi = []
        for (var i = 0; i < SelectedItems.length; i++) {
          console.log('hdkh_id', SelectedItems[i].hdkh_id, 'quytrinh_id', this.quytrinh_id, 'tths_id', this.tths_id)
          var res = await api.capnhat_bohoso(this.axios, [
            {
              bohs_id: 0,
              ghichu: 'Tạo hồ sơ danh sách',
              hdkh_id: SelectedItems[i].hdkh_id,
              hoso_id: 0,
              kieu: 1,
              quytrinh_id: this.quytrinh_id,
              tths_id: this.tths_id
            }
          ])
          var kq = res.data.data[0] ? res.data.data[0] : ''
          console.log('kq', kq, res.data)
          if (kq.substring(0, 2) == 'OK') {
            dem = dem + 1
          } else {
            this.loi.push({
              Ma_GD: SelectedItems[i].ma_gd,
              LYDO: kq
            })
          }
        }
        if (this.loi.length > 0) {
          this.$confirm(`Đã thực hiện ` + dem + `/ ` + SelectedItems.length + ` bộ hồ sơ thành công. Bạn có muốn xuất danh sách lỗi không?`, {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }).then(async () => {
            this.XuatExcel()
          })
        } else {
          this.$toast.success('Đã thực hiện tạo được ' + dem + '/' + SelectedItems.length + ' bộ hồ sơ')
        }

        this.NAP_DS_HDKH_CHUACOHS()
        this.TIMKIEM('')
      })
    },

    XuatExcel() {
      var data = XLSX.utils.json_to_sheet(this.loi)
      var wb = XLSX.utils.book_new() // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data, this.sheetAName) // sheetAName is name of Worksheet
      // export Excel file
      var fileFullName = 'DANHSACHLOI.xls'
      XLSX.writeFile(wb, fileFullName)
    },

    btnClear_Click() {
      this.CboLoaiFile = -1
      this.cboNhomFile = -1
      this.txtDuongDan = ''
      this.txtGhiChuFile = ''
      this.pathFileUpload = ''
      this.fullFile = []
      this.file_refresh += 1
    },

    CAPNHAT_FILE() {
      try {
        if (!this.grid_danhsachbohoso || this.grid_danhsachbohoso.length == 0) {
          this.$toast.error('Không tìm thấy bộ hồ sơ để thêm file.')
          return
        }
        if (this.CboLoaiFile == -1) {
          this.$toast.error('Bạn chưa chọn loại file.')
          return
        }
        if (!this.txtDuongDan || this.txtDuongDan.toString().trim() == '') {
          this.$toast.error('Bạn chưa nhập đường dẫn file')
          return
        }
        this.danhsach = false
        var t = this.$refs.grid_danhsachbohoso.getSelectedRecords()
        if (!t || t.length == 0) {
          //region Thực hiện upload theo 1 file
          if (!this.vbohs_id || this.vbohs_id == 0) {
            this.$toast.error('Không tìm thấy bộ hồ sơ để thêm file.')
            return
          }
          if (this.button.themfile == false) {
            this.GET_HS_THUEBAO()
            if (!this.dtHSThueBao || this.dtHSThueBao.length == 0) {
              this.$toast.error('Bạn bắt buộc phải gán thuê bao với loại file ' + this.CboLoaiFile)
              return
            }
            this.UploadFileHoSo()
          } else {
            this.$toast.error('this.button.themfile')
          }
        } else {
          //Thực hiện upload theo ds
          this.danhsach = true
          this.$confirm(`Bạn có muốn upload file này cho ` + t.length + ` hợp đồng không?`, {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }).then(async () => {
            if (this.button.themfile == false) {
              this.GET_HS_THUEBAO()
              if (!this.dtHSThueBao || this.dtHSThueBao.length == 0) {
                this.$toast.error('Bạn bắt buộc phải gán thuê bao với loại file ' + this.CboLoaiFile)
                return
              }
              this.UploadFileHoSo()
            } else {
              this.$toast.error('this.button.themfile')
            }
          })
        }
      } catch (e) {
        this.$toast.error(e)
        console.log(e)
      }
    },

    async GANFILEUPLOAD_BHS() {
      //duy123123123
      if (!this.grid_danhsachbohoso || this.grid_danhsachbohoso.length == 0) {
        this.$toast.error('Không tìm thấy bộ hồ sơ để thêm file.')
        return
      }
      if (!this.vbohs_id || this.vbohs_id == 0) {
        this.$toast.error('Không tìm thấy bộ hồ sơ để thêm file.')
        return
      }
      if (!this.grcListUpGDV || this.grcListUpGDV.length == 0) {
        this.$toast.error('Không tìm thấy thông tin danh sách đã upload')
        return
      }
      if (!this.array_file_id || this.array_file_id.length == 0) {
        this.$toast.error('Bạn chưa chọn file upload để gán vào bộ hồ sơ')
        return
      }

      this.list_file_id = ''
      for (var i = 0; i < this.array_file_id.length; i++) {
        this.list_file_id = this.list_file_id + this.array_file_id[i].file_id + ','
      }
      this.list_file_id = this.list_file_id.substring(0, this.list_file_id.length - 1)

      var res = await api.sp_capnhat_file_hs(this.axios, {
        bohs_id: this.vbohs_id,
        list_file_id: this.list_file_id
      })

      this.array_file_id = []
      this.NAP_DS_HOPDONGTB(this.vhdkh_id_luoi)
      this.LOADFILE_HS()
      this.$toast.success('Đã gán file thành công')
    },

    btnLuuFile() {
      try {
        this.loading(true)
        if (this.tab_selected == 1) {
          this.CAPNHAT_FILE()
        } else {
          this.GANFILEUPLOAD_BHS()
        }
      } catch (e) {
        this.loading(false)
      } finally {
        this.loading(false)
      }
    },

    GET_HS_THUEBAO() {
      if (this.grid_danhsachthuebao.length == 0) {
        return
      }
      var kt_baohong = false

      var ar = this.$refs.grid_danhsachthuebao.getSelectedRecords()
      if (ar.includes('baohong_id') == true) {
        kt_baohong = true
      }

      for (var i = 0; i < ar.length; i++) {
        this.dtHSThueBao.push({
          thuebao_id: !ar[i].thuebao_id ? null : ar[i].thuebao_id,
          thanhtoan_id: !ar[i].thanhtoan_id ? null : ar[i].thanhtoan_id,
          ma_tb: !ar[i].hdkh_id || ar[i].hdkh_id.toString() == '0' ? ar[i].ma_tb : '',
          hdtb_id: !ar[i].hdtb_id ? null : ar[i].hdtb_id,
          ma_kh: ar[i].ma_kh && ar[i].ma_kh.toString() != '0' ? ar[i].ma_kh : '',
          baohong_id: kt_baohong && ar[i].baohong_id ? ar[i].baohong_id : null,
          hdkh_id: !ar[i].hdkh_id ? null : ar[i].hdkh_id
        })
      }
    },

    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },

    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },

    keymonitor: function(event) {
      try {
        if (event.key == 'Enter') {
          if (this.txtMaGD == null) return
          if (this.tths_id != 6) {
            this.TIMKIEM(this.txtMaGD)
          }
        }
      } catch (e) {}
    },

    acceptTraCuu(item) {
      this.READ_DATA(item)
    },

    HIDE() {
      this.dpnone = 'none'
      this.x = 'box-col box-form col-12'
      this.y = 'box-col box-form'
      this.button.themfile = true
    },

    async TimKiemHS() {
      this.loading(true)
      var loai_id = 0
      if (this.ckbLayAll_DS == true) {
        loai_id = this.$root.token.getNguoiDungID()
      }

      var denngay = 0
      var tungay = 0
      if (this.dtpDenNgayTK != '') {
        denngay = moment(this.dtpDenNgayTK, 'DD/MM/YYYY').format('DD/MM/YYYY')
      }
      if (this.dtpTuNgayTK != '') {
        tungay = moment(this.dtpTuNgayTK, 'DD/MM/YYYY').format('DD/MM/YYYY')
      }

      await this.api_TKHS(loai_id, tungay, denngay)

      this.loading(false)
    },

    async api_TKHS(vloai_id, vtungay, vdenngay) {
      var res = await api.layds_hopdong_chuaco_hoso(this.axios, {
        loai_id: vloai_id,
        tungay: vtungay,
        denngay: vdenngay
      })
      if (res.data.error_code == 'BSS-00000000' || res.data.error_code == 'BSS-00000204') {
        this.grid_danhsachtimkiem = res.data.data ? res.data.data : []
      } else {
        this.$toast.error('Lỗi: ' + res.data.message)
      }
    },

    async importFile(e) {
      try {
        this.file_refresh += 1
        this.txtDuongDan = e.target.files[0].name
        this.fullFile = e.target.files
        //todo
        //up load file to minio
        // lay duong dan
        // if (this.txtDuongDan) {
        //   var formData = new FormData()
        //   for (var file of this.$refs.files.files) {
        //     formData.append('files', file)
        //   }
        //   var response = await api.upload_file_hoso(this.axios, formData, {
        //     headers: {
        //       'Content-Type': 'multipart/form-data'
        //     }
        //   })
        //   var kq = response.data.data ? response.data.data : ''

        //   this.pathFileUpload = kq.join('|')
        // }
      } catch (e) {
        this.$toast.error('Lỗi import file: ' + e)
      }
    },

    changetab_ttupload() {
      this.tab_selected = 1
    },
    changetab_dsupload() {
      this.tab_selected = 2
    },

    clickButton(key) {
      try {
        this.loading(true)
        if (key == 'laytt') {
          if (this.tths_id != 6) {
            var bd = moment(this.dtpTuNgay, 'DD/MM/YYYY').format('DD/MM/YYYY')
            this.TIMKIEM('')
            this.dtpTuNgay = bd
          }
        }

        if (key == 'capnhat') {
          this.CAPNHAT()
        }
        if (key == 'nhapmoi') {
          this.THAOTAC_NUT(1)
        }
        if (key == 'huy') {
          this.THAOTAC_NUT(3)
        }

        if (key == 'themfile') {
          // alert(2014)
          this.button.themfile = false
          this.dpnone = 'block'
          this.x = 'box-col box-form col-10'
          this.y = 'box-col box-form col-2'
          this.NAP_DS_HOPDONGTB(this.vhdkh_id)
        }
        if (key == 'xoafile') {
          this.XOA_FILE()
        }

        if (key == 'xoaphieu') {
          this.XOA_BOHOSO()
        }
      } catch (e) {
        this.loading(false)
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style>
li.non-active a {
  color: #d3d3d3 !important;
}

.traphieu .page-content {
  position: unset;
}

.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  min-width: 720px !important;
}

.modal {
  position: fixed;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
  width: 60% !important;
  height: 70% !important;
  margin-top: 0 !important;
}

.divider {
  height: 100vh;
  width: 6px;
  background: #f9f8f8;
  transform: translateX(-3px);
  position: absolute;
  top: 0;
  z-index: 1;
  cursor: ew-resize;
}

.wrapper {
  display: flex;
}

.e-grid .below-30 {
  background-color: orangered !important;
}
</style>
