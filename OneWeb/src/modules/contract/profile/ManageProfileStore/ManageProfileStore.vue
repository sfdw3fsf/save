<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnLayTT.visible" @click="tsbtnLayTT_Clicked" :class="{ disabledInput: !tsbtnLayTT.enabled }">
          <a> <span :class="tsbtnLayTT.icon"></span>{{ tsbtnLayTT.text }}</a>
        </li>
        <li v-if="tsbtnNhapMoi.visible" @click="tsbtnNhapMoi_Clicked" :class="{ disabledInput: !tsbtnNhapMoi.enabled }">
          <a> <span :class="tsbtnNhapMoi.icon"></span>{{ tsbtnNhapMoi.text }}</a>
        </li>
        <li v-if="tsbtnCapNhat.visible" @click="tsbtnCapNhat_Clicked" :class="{ disabledInput: !tsbtnCapNhat.enabled }">
          <a> <span :class="tsbtnCapNhat.icon"></span>{{ tsbtnCapNhat.text }}</a>
        </li>
        <li v-if="tsbtnHuyBo.visible" @click="tsbtnHuyBo_Clicked" :class="{ disabledInput: !tsbtnHuyBo.enabled }">
          <a> <span :class="tsbtnHuyBo.icon"></span>{{ tsbtnHuyBo.text }}</a>
        </li>
        <li v-if="tsbtnXoaPhieu.visible" @click="tsbtnXoaPhieu_Clicked" :class="{ disabledInput: !tsbtnXoaPhieu.enabled }">
          <a> <span :class="tsbtnXoaPhieu.icon"></span>{{ tsbtnXoaPhieu.text }}</a>
        </li>
        <li v-if="tsbtnThemFile.visible" @click="tsbtnThemFile_Clicked" :class="{ disabledInput: !tsbtnThemFile.enabled }">
          <a> <span :class="tsbtnThemFile.icon"></span>{{ tsbtnThemFile.text }}</a>
        </li>
        <li v-if="tsbtnXoaFile.visible" @click="tsbtnXoaFile_Clicked" :class="{ disabledInput: !tsbtnXoaFile.enabled }">
          <a> <span :class="tsbtnXoaFile.icon"></span>{{ tsbtnXoaFile.text }}</a>
        </li>
        <li v-if="tsbtnChuyenHoSo.visible" @click="tsbtnChuyenHoSo_Clicked" :class="{ disabledInput: !tsbtnChuyenHoSo.enabled }">
          <a> <span :class="tsbtnChuyenHoSo.icon"></span>{{ tsbtnChuyenHoSo.text }}</a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="" style="display: flex">
        <div style="display: flex">
          <div>
            <div class="box-form">
              <div class="legend-title">
                Danh sách kho hồ sơ
                <a href="#" class="btn btn-second pad2 lh20 button-not-bg marl10" @click.prevent="refreshTree">
                  <span class="nc-icon-outline arrows-1_refresh-69 f20"></span>
                </a>
              </div>
              <div class="input-more-button -right">
                <button class="btn" @click.prevent="btnSort">
                  <span class="one-sort"></span>
                </button>
                <div class="input-icon-left">
                  <input type="text" class="form-control" v-model="txtDoiTuong" v-on:keyup.enter="onEnter" />
                  <span class="icon nc-icon-outline ui-2_filter"></span>
                </div>
              </div>

              <div class="tree-plus mart10">
                <ejs-treeview cssClass="mytree" id="treeview" :fields="fields" ref="treeview" :showCheckBox="false" :expandAll="false" :allowMultiSelection="true" @nodeSelected="treeClicked"></ejs-treeview>
              </div>
            </div>
          </div>
          <div>
            <div class="row box-form" style="">
              <div class="col-sm-4 col-12">
                <div class="legend-title">Thông tin bộ hồ sơ</div>

                <div class="row">
                  <div class="info-row">
                    <div class="key w80">Mã GD</div>

                    <div class="value dropdown">
                      <div class="input-icon-right" data-toggle="dropdown">
                        <input :disabled="!ttBoHS.enable" type="text" class="form-control highlight" v-model="ttBoHS.magd" ref="magd" v-on:keyup.enter="onMaGDEnter" />
                        <span class="icon one-search text-main"></span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="info-row">
                    <vue-date format="DD/MM/YYYY" v-model="ttBoHS.ngay_tao" :labelWidth="'80'" label="Ngày tạo" :disable="!ttBoHS.enable"></vue-date>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w80">Mã bộ HS</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input :disabled="!ttBoHS.enable" type="text" class="form-control highlight" v-model="ttBoHS.mabohs" v-on:keyup.enter="onMaBoHSEnter" />
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w80">Mã KH</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input :disabled="!ttBoHS.enable" type="text" class="form-control highlight" v-model="ttBoHS.makh" />
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w80">Tên KH</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input :disabled="!ttBoHS.enable" type="text" class="form-control highlight" v-model="ttBoHS.tenkh" />
                    </div>
                  </div>
                </div>

                <div class="info-row">
                  <vue-date format="DD/MM/YYYY" v-model="ttBoHS.ngay_ht" :labelWidth="'80'" label="Ngày HT" :disable="!ttBoHS.enable"></vue-date>
                </div>
                <div class="info-row">
                  <div class="key w80">Trạng thái</div>
                  <div class="value">
                    <div class="select-custom">
                      <select name="" id="" class="form-control" :disabled="!ttBoHS.enable" v-model="ttBoHS.trangthaihoso">
                        <option v-for="trangthai in listbox.trangthaihoso.list" v-bind:value="trangthai.ID">{{ trangthai.NAME }}</option>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w80">Kho hồ sơ</div>
                  <div class="value">
                    <div class="select-custom">
                      <select :disabled="cboKhoHSDisable" class="form-control" v-model="listbox.khohoso.value.id">
                        <option v-for="khohs in listbox.khohoso.list" v-bind:value="khohs.ID">{{ khohs.NAME }}</option>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w80">Ghi chú</div>
                  <div class="value">
                    <textarea :disabled="!ttBoHS.enable" class="form-control" style="height: 74px; resize: none" id="" cols="30" rows="10" v-model="ttBoHS.ghichu"></textarea>
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="legend-title">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="listbox.chitiet_hoso.isZoom" />
                    <span class="name">Chi tiết bộ hồ sơ</span>
                  </div>
                </div>
                <div class="table-content" style="max-height: 300px">
                  <table class="table-result table-gachle tree-grid-2">
                    <tr>
                      <td>
                        <div class="table-content">
                          <DataGrid ref="grid" v-bind:columns="listbox.chitiet_hoso.header" v-bind:dataSource="listbox.chitiet_hoso.list" :enablePagingServer="false" :allowSorting="true" :allowPaging="true" :allowResizing="true" :showFilter="true"> </DataGrid>
                        </div>
                      </td>
                    </tr>
                  </table>
                </div>
              </div>
              <div v-if="!listbox.chitiet_hoso.isZoom" class="col-sm-4 col-12">
                <div class="legend-title">Đơn vị nhận hồ sơ</div>
                <DataGrid v-bind:columns="listbox.donvinhan_hoso.header" v-bind:dataSource="listbox.donvinhan_hoso.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="false"> </DataGrid>
              </div>
              <!-- <div v-if="!listbox.chitiet_hoso.isZoom" class="col-sm-4 col-12">
              <div class="legend-title">Đơn vị nhận hồ sơ</div>
              <div class="table-content" style="max-height: 300px;">
                <table class="table-result table-gachle tree-grid-2">
                  <tr>
                    <td>
                      <div>
                        <DataGrid v-bind:columns="listbox.donvinhan_hoso.header" v-bind:dataSource="listbox.donvinhan_hoso.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="false"> </DataGrid>
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
            </div> -->
            </div>
            <div class="box-form">
              <div class="legend-title">
                <div class="pull-left">
                  <span class="inline">Danh sách bộ hồ sơ</span>
                  <div class="inline vcenter marl20">
                    <div class="info-row marb0">
                      <div class="key normal w20">Từ</div>
                      <div class="value">
                        <div class="input-icon-right">
                          <vue-date v-model="tu_ngay" format="DD/MM/YYYY" type="date" />
                          <span class="icon one-calendar"></span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="inline vcenter">
                    <div class="info-row marb0">
                      <div class="key normal w20">Đến</div>
                      <div class="value">
                        <div class="input-icon-right">
                          <vue-date v-model="den_ngay" format="DD/MM/YYYY" type="date" />
                          <span class="icon one-calendar"></span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="pull-right">
                  <div class="list-checks mart7 normal">
                    <div class="item">
                      <div class="check-action">
                        <input type="checkbox" class="check" v-model="laytoanbo" />
                        <span class="name">Lấy toàn bộ</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input type="checkbox" class="check" v-model="isHSCungHopDong" />
                        <span class="name">HS cùng Hợp đồng</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="clearfix"></div>
              </div>
              <div class="table-content">
                <table class="table-result table-gachle table-filter">
                  <tr>
                    <td>
                      <div>
                        <DataGrid
                          :enabledSelectFirstRow="false"
                          ref="grid_ds_bo_hs"
                          v-bind:columns="listbox.ds_bohoso.header"
                          v-bind:dataSource="listbox.ds_bohoso.list"
                          :enable-paging-server="false"
                          :allowPaging="true"
                          :showFilter="true"
                          :showColumnCheckbox="false"
                          @selectedRowChanged="gridHoSoChanged"
                          @selectedItemsChanged="gridDSBoHoSo_selectedItemsChanged"
                          @actionComplete="gridHoSoRowChon"
                        >
                          <!-- :pageIndex="listbox.ds_bohoso.pageIndex"
                        :pageSize="listbox.ds_bohoso.pageSize"
                        @pageChanged="gridHoSoPageChanged" -->
                        </DataGrid>
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
            </div>
          </div>
        </div>

        <!-- <div v-if="isThemFile" class="box-col box-form file-info-wrapper" id="boxRight" style="width: calc(23% - 8px);">
          <div class="legend-title file-info-toolbar-wrapper">
            <div class="pull-left">
              <span class="title inline vcenter btn-no-padding">Thông tin file</span>
              <button class="btn btn-second btn-no-padding" @click="resetClicked" :disabled="!isThemFile"><span class="one-reload"></span> Làm mới</button>
              <button class="btn btn-second btn-no-padding" @click="btlLuuFileClick" :disabled="!isThemFile"><span class="one-file-plus"></span> Lưu file</button>
            </div>
            <div class="pull-right">
              <a href="#" class="red btn f20 lh20" @click="isThemFile = false">
                <span class="-ap icon-arrow-right3"></span>
              </a>
            </div>
            <div class="clearfix"></div>
          </div>
          <ejs-tab ref="tabObj" id="tab_wizard" heightAdjustMode="None" height="auto" @selected="onThongTinFileSelected">
            <e-tabitems>
              <e-tabitem :header="thongTinUploadHeaderText" content="#thongTinUpload"></e-tabitem>
              <e-tabitem :header="danhSachDaUploadHeaderText" content="#danhSachDaUpload"></e-tabitem>
            </e-tabitems>
          </ejs-tab>
          <div id="thongTinUpload" class="file-info-tab" style="display: none;">
            <div class="info-row">
              <div class="key w80">Nhóm file</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control" v-model="listbox.nhomfile.value.id" :disabled="!isThemFile">
                    <option v-for="nhomfile in listbox.nhomfile.list" v-bind:value="nhomfile.ID">{{ nhomfile.NAME }}</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Loại File</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control" v-model="listbox.loaifile.value.id" :disabled="!isThemFile">
                    <option v-for="loaifile in listbox.loaifile.list" v-bind:value="loaifile.loaifile_id">{{ loaifile.loai_file }}</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Đường dẫn</div>
              <div class="value">
                <div class="input-more-button -right">
                  <label class="btn" for="upload-file" :disabled="!isThemFile">
                    <span class="-ap icon-more_horiz"></span>
                    <input type="file" ref="files" class="hidden" id="upload-file" @change="UploadFileHoSo" />
                  </label>
                  <input type="text" class="form-control highlight" v-model="listbox.ds_file_hs.value.url" :disabled="!isThemFile" value="http://10.70.521.164:8081/FILES/QBH/HOSO/proud.jpg" />
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Ghi chú</div>
              <div class="value">
                <input type="text" class="form-control" v-model="ghichufile" :disabled="!isThemFile" />
              </div>
            </div>
            <div class="title-bg-main">
              <div class="pull-left">
                <span class="title mart5">Danh sách thuê bao</span>
                <a href="#" @click="btnResetClick" class="btn btn-second pad2 lh20 button-not-bg marl10">
                  <span class="nc-icon-outline arrows-1_refresh-69 f20"></span>
                </a>
              </div>
              <div class="pull-right">
                <a href="#" @click="btnNhomClick" class="btn btn-second pad2 lh20 button-not-bg marl10">
                  <span class="nc-icon-outline ui-2_filter f20"></span>
                </a>
              </div>
              <div class="clearfix"></div>
            </div>

            <div class="table-content" style="max-height: 300px;">
              <table class="table-result table-gachle tree-grid-2">
                <tr>
                  <td>
                    <div>
                      <DataGrid :key="listbox.ds_thuebao.tableKey" v-bind:columns="listbox.ds_thuebao.header" v-bind:dataSource="listbox.ds_thuebao.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" @selectedItemsChanged="gridTBItemshanged">
                      </DataGrid>
                    </div>
                  </td>
                </tr>
              </table>
            </div>
          </div>
          <div id="danhSachDaUpload" class="file-info-tab" style="display: none;">
            <div class="table-content">
              <table class="table-result table-gachle tree-grid-2">
                <tr>
                  <td>
                    <div>
                      <DataGrid :key="listbox.ds_thuebao.tableKey" v-bind:columns="listbox.ds_daUpload.header" v-bind:dataSource="listbox.ds_daUpload.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" @selectedItemsChanged="gridTBItemshanged">
                      </DataGrid>
                    </div>
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div> -->
        <frmUploadFile v-if="isThemFile" style="width: 400px" v-bind="frmUploadFile.input" @tsbtnClosed_Clicked="isThemFile = !isThemFile" @onUploadedListener="onMaGDEnter" />
      </div>
    </div>
    <b-modal id="dsHopDong-modal" size="xl" hide-footer body-class="modal-body p-0">
      <DataGrid style="position: absolute; z-index: 9999" v-bind:columns="listbox.ds_hopdong.header" v-bind:dataSource="listbox.ds_hopdong.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="false"> </DataGrid>
    </b-modal>
  </div>
</template>
<script>
import Vue from 'vue'
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import api from './ManageProfileStoreAPI'
// import gridview from "@/components/Shared/gridview";
import treemenu from './treemenu'
import { Page, Filter, CommandColumn, Resize, Sort, Freeze } from '@syncfusion/ej2-vue-grids'

//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import moment from 'moment'
import { HOSO_LC } from '@/const/enums/index.js'
import { TabPlugin } from '@syncfusion/ej2-vue-navigations'
import frmUploadFile from './components/frmUploadFile'

Vue.use(TabPlugin)

export default {
  provide: {
    grid: [Filter, Page, CommandColumn, Resize, Sort, Freeze]
  },
  components: {
    breadcrumb,
    VueFlatPickr,
    treemenu,
    frmUploadFile
  },
  name: 'ManageProfileStore',
  async mounted() {
    this.tt_nd.ngay_cn = this.$auth.getNgayCapNhat()
    this.tt_nd.nguoidung_id = this.$root.token.getNguoiDungID()
    this.tt_nd.ma_nd = this.$root.token.getMaNhanVien()
    // Lay gia trị tag truyen tu url
    if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
      this.Tag = this.$route.query.tag
    }

    let kq = await api.ds_quyen_nguoidung(this.axios, {})
    if (kq.data.data) {
      this.dsQuyen = kq.data.data
    } else {
      this.dsQuyen = []
    }

    if (this.Tag) {
      this.THIETLAP_GIAODIEN()
    }
    this.refreshTree(false)

    api.trangthai_bo_hoso(this.axios, {}).then((response) => {
      if (response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.trangthaihoso.list = response.data.data
        if (this.listbox.trangthaihoso.list.length > 0) {
          this.listbox.trangthaihoso.value.id = this.listbox.trangthaihoso.list[0].ID
        }
      }
    })
    api.kho_hoso(this.axios, {}).then((response) => {
      if (response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.khohoso.list = response.data.data
        if (this.listbox.khohoso.list.length > 0) {
          this.listbox.khohoso.value.id = this.listbox.khohoso.list[1].ID
        }
      }
    })
    api.nhom_file(this.axios, {}).then((response) => {
      if (response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.nhomfile.list = response.data.data
        if (this.listbox.nhomfile.list.length > 0) {
          this.listbox.nhomfile.value.id = this.listbox.nhomfile.list[1].ID
        }
      }
    })
    api.loai_file(this.axios, {}).then((response) => {
      if (response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.loaifile.list = response.data.data
        if (this.listbox.loaifile.list.length > 0) {
          this.listbox.loaifile.value.id = this.listbox.loaifile.list[1].loaifile_id
        }
      }
    })

    this.listbox.ds_bohoso.list = []
    this.listbox.chitiet_hoso.list = []
    this.listbox.donvinhan_hoso.list = []
    this.listbox.ds_hopdong_chuacohoso.list = []
    this.listbox.ds_hopdong_chuacohoso.header = [
      { name: 'ma_gd', text: 'Mã GD' },
      { name: 'ngay_yc', text: 'Ngày YC' },
      { name: 'ngay_ht', text: 'Ngày HT' },
      { name: 'ma_kh', text: 'Mã KH' },
      { name: 'ten_kh', text: 'Tên KH' }
    ]

    this.listbox.ds_thuebao.list = []
  },
  data() {
    return {
      tsbtnLayTT: { enabled: true, visible: true, text: 'Lấy TT', icon: 'icon one-file-attach1' },
      tsbtnNhapMoi: { enabled: true, visible: true, text: 'Nhập mới', icon: 'icon one-file-plus' },
      tsbtnCapNhat: { enabled: true, visible: true, text: 'Cập nhật', icon: 'icon one-reload1' },
      tsbtnHuyBo: { enabled: true, visible: true, text: 'Huỷ bỏ', icon: 'icon nc-icon-glyph ui-1_circle-remove' },
      tsbtnXoaPhieu: { enabled: true, visible: true, text: 'Xoá phiếu', icon: 'icon one-file-trash' },
      tsbtnThemFile: { enabled: true, visible: true, text: 'Thêm file', icon: 'icon nc-icon-glyph ui-1_circle-add' },
      tsbtnXoaFile: { enabled: true, visible: true, text: 'Xoá file', icon: 'icon one-folder-trash' },
      tsbtnChuyenHoSo: { enabled: true, visible: true, text: 'Chuyển Hồ sơ', icon: 'icon one-file-back-1' },
      thongTinUploadHeaderText: { text: 'Thông tin upload' },
      danhSachDaUploadHeaderText: { text: 'Danh sách đã upload' },

      gridDSBoHoSo: {
        header: [],
        list: [],
        selected: [],
        value: null
      },
      thongTinUploadTemplate: function () {
        return {
          template: Vue.component('ThongTinUpload', ThongTinUpload)
        }
      },
      danhSachDaUploadTemplate: function () {
        return {
          template: Vue.component('DanhSachDaUpload', DanhSachDaUpload)
        }
      },
      fields: {
        dataSource: [],
        id: 'hoso_id',
        parentID: 'hoso_cha_id',
        text: 'ma_hs_doituong',
        hasChildren: 'has_child',
        child: 'child',
        imageUrl: 'icon'
      },
      Tag: '',
      quytrinh_id: '',
      tths_id: '',
      // dateconfig: {
      //   altFormat: 'd/m/Y',
      //   altInput: true,
      //   dateFormat: 'DD/MM/YYYY',
      //   allowInput: true
      // },
      txtMaGDSearch: '',
      ngay_ht: new Date(),
      header: {
        title: 'QUẢN LÝ BỘ HỒ SƠ',
        list: []
      },
      bsort: 1, //1: a->z, -1: z->a
      txtDoiTuong: '',
      isNhapMoi: false,
      isCapNhat: false,
      isThemFile: false,
      isXoaFile: false,
      isLayToanBo: false,
      isHSCungHopDong: false,
      listbox: {
        trangthaihoso: {
          list: [],
          value: { id: '', name: '' }
        },
        khohoso: {
          list: [],
          value: { id: '', name: '' }
        },
        nhomfile: {
          list: [],
          value: { id: '', name: '' }
        },
        loaifile: {
          list: [],
          value: { loaifile_id: '', loai_file: '' }
        },
        ds_doituonghs: {
          list: {},
          value: { hoso_id: '', ma_hs_doituong: '' },
          isEnabled: false
        },
        ds_thuebao: {
          list: [],
          checked: [],
          tableKey: 0,
          header: [
            {
              fieldName: 'MA_TB',
              headerText: 'Số máy/Acc',
              allowFiltering: true,
              width: 110
            },
            {
              fieldName: 'LOAIHINH_TB',
              headerText: 'Loại hình',
              allowFiltering: true,
              width: 120
            },
            {
              fieldName: 'TRANGTHAI_TB',
              headerText: 'Trạng thái',
              allowFiltering: true,
              width: 110
            },
            {
              fieldName: 'NGAY_BH',
              headerText: 'Ngày báo hỏng',
              allowFiltering: true,
              width: 120
            },
            {
              fieldName: 'NGUOI_BH',
              headerText: 'Người báo hỏng',
              allowFiltering: true,
              width: 120
            },
            {
              fieldName: 'NGAY_HT',
              headerText: 'Ngày nghiệm thu',
              allowFiltering: true,
              width: 120
            },
            {
              fieldName: 'LOAI_HD',
              headerText: 'Loại HĐ',
              isGroupedColumn: true,
              width: 120
            }
          ],
          value: {},
          isEnabled: true
        },
        ds_daUpload: {
          list: [],
          checked: [],
          tableKey: 0,
          header: [
            {
              fieldName: 'ma_tb',
              headerText: 'Số máy/Acc',
              allowFiltering: true
            },
            {
              fieldName: 'nhom_file',
              headerText: 'Nhóm file',
              allowFiltering: true,
              allowHtml: true,
              isGroupedColumn: true
            },
            {
              fieldName: 'url',
              headerText: 'Xem/Tải về',
              width: 110,
              template: () => {
                return {
                  template: Vue.component('action-button', {
                    template: `<div><a style="background-color: rgb(1, 118, 255);border: 0px;border-radius: 3px;padding: 0px 10px;color: #FFFF;" @click="onPreviewlick"><i class='fa fa-search'></i></a>
                    <a style="background-color: rgb(1, 118, 255);border: 0px;border-radius: 3px;padding: 0px 10px;color: #FFFF;" @click="onDownloadlick"><i class='fa fa-download'></i></a></div>`,
                    data: function () {
                      return {
                        data: {}
                      }
                    },
                    methods: {
                      onPreviewlick() {
                        this.$parent.$parent.$parent.downloadFile(1, this.$data.data.url, this.$data.data.ten_file)
                      },
                      onDownloadlick() {
                        this.$parent.$parent.$parent.downloadFile(0, this.$data.data.url, this.$data.data.ten_file)
                      }
                    }
                  })
                }
              }
            }
          ]
        },
        ds_bohoso: {
          pageSize: 10,
          pageIndex: 0,
          isCheckAll: false,
          checked: [],
          list: [],
          header: [
            {
              headerText: '',
              width: 50,
              allowFiltering: false,
              textAlign: 'Center',
              headerTemplate: () => {
                return {
                  template: Vue.component('action-check-all', {
                    template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.listbox.ds_bohoso.isCheckAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                    data: function () {
                      return { data: {} }
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent
                      }
                    }
                  })
                }
              },
              template: () => {
                return {
                  template: Vue.component('action-check', {
                    template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.listbox.ds_bohoso.checked"
                                    @change="onChangeProcessed(data)"
                                    :value="data.bohs_id"
                                    />
                                <span class="name"></span>
                            </div>
                        `,
                    data() {
                      return { data: {} }
                    },
                    methods: {
                      onChangeProcessed(value) {
                        var obj = this.parent.listbox.ds_bohoso.list.find((f) => f.bohs_id == value.bohs_id)
                        obj.checked = true
                      }
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent
                      }
                    }
                  })
                }
              }
            },
            {
              fieldName: 'ma_bhs',
              headerText: 'Mã bô HS',
              allowFiltering: true,
              width: 90
            },
            {
              fieldName: 'ma_gd',
              headerText: 'Mã GD',
              allowFiltering: true,
              width: 90
            },
            {
              fieldName: 'ma_kh',
              headerText: 'Mã KH',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ma_hd',
              headerText: 'Mã HĐ',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ngaytao',
              headerText: 'Ngày tạo',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ten_kh',
              headerText: 'Tên KH',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'diachi_kh',
              headerText: 'Địa chỉ KH',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ten_dv',
              headerText: 'Đơn vị tạo',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ten_nv',
              headerText: 'Nhân viên tạo',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ngay_ht',
              headerText: 'Ngày lưu kho',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'trangthai',
              headerText: 'Trạng thái',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            }
          ],
          value: {},
          isEnabled: true,
          checked: []
        },
        chitiet_hoso: {
          isZoom: false,
          isCheckAll: false,
          checked: [],
          list: [],
          header: [
            {
              headerText: '',
              width: 50,
              allowFiltering: false,
              textAlign: 'Center',
              headerTemplate: () => {
                return {
                  template: Vue.component('action-check-all', {
                    template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.listbox.chitiet_hoso.isCheckAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                    data: function () {
                      return { data: {} }
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent
                      }
                    }
                  })
                }
              },
              template: () => {
                return {
                  template: Vue.component('action-check', {
                    template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.listbox.chitiet_hoso.checked"
                                    @change="onChangeProcessed(data)"
                                    :value="data.file_id"
                                    />
                                <span class="name"></span>
                            </div>
                        `,
                    data() {
                      return { data: {} }
                    },
                    methods: {
                      onChangeProcessed(value) {
                        var obj = this.parent.listbox.chitiet_hoso.list.find((f) => f.file_id == value.file_id)
                        obj.checked = true
                      }
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent
                      }
                    }
                  })
                }
              }
            },
            {
              fieldName: 'ma_tb',
              headerText: 'Số máy/Acc',
              allowFiltering: true,
              width: 110
            },
            {
              fieldName: 'ma_gd',
              headerText: 'Mã GD',
              allowFiltering: true,
              width: 90
            },
            {
              fieldName: 'loai_file',
              headerText: 'Loại file',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ten_file',
              headerText: 'Tên file',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'loai_hd',
              headerText: 'Loại HĐ/PL',
              allowFiltering: true,
              allowHtml: true,
              width: 120
            },
            {
              fieldName: 'ngay_yc',
              headerText: 'Ngày YC',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'nguoi_cn',
              headerText: 'Người upload',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ngay_cn',
              headerText: 'Ngày upload',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'nhom_file',
              headerText: 'Nhóm file',
              allowFiltering: true,
              allowHtml: true,
              width: 90,
              isGroupedColumn: true
            },
            {
              fieldName: 'url',
              headerText: 'Xem/Tải về',
              width: 110,
              template: () => {
                return {
                  template: Vue.component('action-button', {
                    template: `<div><a style="background-color: rgb(1, 118, 255);border: 0px;border-radius: 3px;padding: 0px 10px;color: #FFFF;" @click="onPreviewlick"><i class='fa fa-search'></i></a>
                    <a style="background-color: rgb(1, 118, 255);border: 0px;border-radius: 3px;padding: 0px 10px;color: #FFFF;" @click="onDownloadlick"><i class='fa fa-download'></i></a></div>`,
                    data: function () {
                      return {
                        data: {}
                      }
                    },
                    methods: {
                      onPreviewlick() {
                        this.$parent.$parent.$parent.downloadFile(1, this.$data.data.url, this.$data.data.ten_file)
                      },
                      onDownloadlick() {
                        this.$parent.$parent.$parent.downloadFile(0, this.$data.data.url, this.$data.data.ten_file)
                      }
                    }
                  })
                }
              }
            }
          ],
          value: {},
          isEnabled: true,
          checked: []
        },
        donvinhan_hoso: {
          list: [],
          header: [
            {
              fieldName: 'ten_dv',
              headerText: 'Đơn vị nhận',
              allowFiltering: true,
              width: 150
            },
            {
              fieldName: 'ten_loaidv',
              headerText: 'Loại đơn vị',
              allowFiltering: true,
              width: 150
            }
          ],
          value: {},
          isEnabled: true
        },
        ds_hopdong_chuacohoso: {
          list: [],
          header: [],
          value: {},
          isEnabled: true
        },
        ds_hopdong: {
          list: [],
          header: [
            {
              fieldName: '',
              headerText: '',
              width: 30,
              allowFiltering: false,
              template: () => {
                return {
                  template: Vue.component('action-button', {
                    template: `<a style="background-color: rgb(1, 118, 255);border: 0px;border-radius: 3px;padding: 0px 10px;color: #FFFF;" @click="onChonGDClick"><i class='fa fa-plus'></i></a>`,
                    data: function () {
                      return {
                        data: {}
                      }
                    },
                    methods: {
                      onChonGDClick() {
                        console.log('onChonGDClick')
                        console.log(this.$data.data)
                        if (this.$data.data.bohs == 0) {
                          this.$data.data.trangthai_id = HOSO_LC.TRANGTHAI_MOI
                          this.$data.data.ngaytao = this.$parent.$parent.$parent.$parent.$parent.tt_nd.ngay_cn

                          this.$parent.$parent.$parent.$parent.$parent.SET_FORM_TT_BO_HS(this.$data.data)
                        } else {
                          this.$parent.$parent.$parent.$parent.$parent.loadBoHSTheoMaGD(this.$data.data)
                        }

                        this.$parent.$parent.$parent.$parent.hide()
                      }
                    }
                  })
                }
              }
            },
            {
              fieldName: 'ma_gd',
              headerText: 'Mã GD',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ngay_yc',
              headerText: 'Ngày yêu cầu',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ten_loaihd',
              headerText: 'Loại HĐ',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            },
            {
              fieldName: 'ten_dv',
              headerText: 'Đơn vị lập HĐ/PL',
              allowFiltering: true,
              allowHtml: true,
              width: 90
            }
          ],
          value: {},
          show: false
        },
        ds_file_hs: {
          list: [],
          header: [],
          value: {
            url: ''
          },
          isEnabled: false
        }
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      success_code: 'BSS-00000000',
      ngay_tao: new Date(),
      ngay_ht: new Date(),
      tu_ngay: moment().startOf('month').format('DD/MM/YYYY'),
      den_ngay: moment().endOf('month').format('DD/MM/YYYY'),
      thoihancon_lonhon: 0,
      thoihancon_nhohon: 0,
      tt_nd: {
        nhanvien_id: '',
        ngay_cn: '',
        donvi_dl_id: '',
        ma_nv: '',
        ma_nd: '',
        may_cn: '',
        phanvung_id: ''
      },
      txtMaGDSearch: '',
      btnNhapMoiVisible: false,
      btnThemFileVisible: false,
      btnXoaFileVisible: false,
      btnChuyenKhoVisible: true,
      cboKhoHSDisable: false,
      dsQuyen: [],
      ttBoHS: {
        enable: true,
        magd: '',
        ngay_tao: '',
        mabohs: '',
        makh: '',
        tenkh: '',
        ngay_ht: '',
        trangthaihoso: '',
        khohoso: '',
        ghichu: ''
      },
      ghichufile: '',
      frmUploadFile: {
        input: {}
      }
    }
  },
  watch: {
    'listbox.chitiet_hoso.isCheckAll': {
      handler: function (value) {
        if (value) {
          this.listbox.chitiet_hoso.list.forEach((f) => {
            f.checked = true
          })
          this.listbox.chitiet_hoso.checked = this.listbox.chitiet_hoso.list.map((f) => f.file_id)
          return
        } else {
          this.listbox.chitiet_hoso.list.forEach((f) => {
            f.checked = false
          })
          this.listbox.chitiet_hoso.checked = []
          return
        }
      },
      deep: true
    },
    'listbox.chitiet_hoso.checked': {
      // handler: function (value) {
      //   this.listbox.chitiet_hoso.isCheckAll =
      //     value.length > 0 &&
      //     value.length === this.listbox.chitiet_hoso.list.length;
      // },
      handler: function (value) {
        this.listbox.chitiet_hoso.isCheckAll = value.length > 0 && value.length === this.listbox.chitiet_hoso.list.length
      },
      deep: true
    },
    'listbox.ds_bohoso.isCheckAll': {
      handler: function (value) {
        if (value) {
          this.listbox.ds_bohoso.list.forEach((f) => {
            f.checked = true
          })
          this.listbox.ds_bohoso.checked = this.listbox.ds_bohoso.list.map((f) => f.bohs_id)
          return
        } else {
          this.listbox.ds_bohoso.list.forEach((f) => {
            f.checked = false
          })
          this.listbox.ds_bohoso.checked = []
          return
        }
      },
      deep: true
    },
    'listbox.ds_bohoso.checked': {
      handler: function (value) {
        this.listbox.ds_bohoso.isCheckAll = value.length > 0 && value.length === this.listbox.ds_bohoso.list.length
      },
      deep: true
    },
    'gridDSBoHoSo.value'(val) {
      console.log('gridDSBoHoSo.value watch')
      this.frmUploadFile.input.vdanhsach_bhs = [this.gridDSBoHoSo.value]
      this.isThemFile = false
    }
  },
  computed: {
    nhohon: {
      get() {
        return this.isNhoHon
      },
      set(value) {
        this.isNhoHon = value
        this.thoihancon_nhohon = '0'
      }
    },
    lonhon: {
      get() {
        return this.isLonHon
      },
      set(value1) {
        this.isLonHon = value1
        this.thoihancon_lonhon = '0'
      }
    },
    laytoanbo: {
      get() {
        return this.isLayToanBo
      },
      set(value) {
        this.isLayToanBo = value
      }
    },
    boxMidWidth() {
      //width: calc(62% - 16px);
      if (!this.isThemFile) {
        return 'width: calc(85% - 16px);'
      } else {
        return 'width: calc(65% - 16px);'
      }
    },
    chiTietHSWidth() {
      if (!this.listbox.chitiet_hoso.isZoom) {
        return 'width: calc(44.0583% - 16px);'
      } else {
        return 'width: calc(69.0189% - 16px);'
      }
    }
  },
  methods: {
    async gridDSBoHoSo_selectedItemsChanged(e) {
      console.log('gridDSBoHoSo_selectedItemsChanged')
      console.log(this.listbox.ds_bohoso.checked)
    },
    async tsbtnLayTT_Clicked() {
      if (this.tths_id != HOSO_LC.TRANGTHAI_HT) {
        this.TIMKIEM(null)
      }
    },
    async tsbtnNhapMoi_Clicked() {
      this.isNhapMoi = true
      this.isCapNhat = false
      this.XOA_FORM_TT_BO_HS()
      this.ttBoHS.enable = true
      this.listbox.trangthaihoso.value.id = 1
      this.$refs.magd.focus()
    },
    async tsbtnCapNhat_Clicked() {
      var kieu = 0
      var message = 'Cập nhật thông tin hồ sơ thành công'
      //Nhập mới
      if (this.isNhapMoi) {
        if (this.tths_id == HOSO_LC.TRANGTHAI_HT && !this.btnNhapMoiVisible) {
          this.ShowAlert('Không được quyền thêm mới hồ sơ ở chức năng này', 1)
          return
        }
        kieu = 1
        message = 'Tạo bộ hồ sơ thành công'
      } else {
        if (this.ttBoHS.hdkhid == 0) {
          this.ShowAlert('Bạn chưa chọn mã giao dịch hợp đồng của khách hàng để lập hồ sơ', 1)
          return
        }
        if (this.ttBoHS.nguoi_cn != this.tt_nd.ma_nd && this.btnNhapMoiVisible) {
          this.ShowAlert('Bạn không được quyền cập nhật bộ hồ sơ của người khác', 1)
          return
        }
      }
      //Tạo bộ hồ sơ
      var input = {
        bohs_id: this.ttBoHS.bohsid,
        ghichu: this.ttBoHS.ghichu,
        hdkh_id: this.ttBoHS.hdkhid,
        hoso_id: 0,
        kieu: kieu,
        quytrinh_id: this.quytrinh_id,
        tths_id: this.tths_id
      }
      this.loading(true)
      api
        .capnhat_bohoso(this.axios, [input])
        .then((response) => {
          if (response.data.data.length > 0) {
            if (response.data.data[0]) {
              if (response.data.data[0].search('OK') == -1) {
                this.ShowAlert(response.data.data[0], 1)
                return
              }
            } else {
              this.ShowAlert(message, 0)
            }
            //load danh sách bộ hồ sơ
            var pdata = {}
            pdata.hoso_id = this.ttBoHS.mabohs
            pdata.tungay = this.tu_ngay
            pdata.denngay = this.den_ngay
            if (this.isLayToanBo) pdata.tim_all = 1
            else pdata.tim_all = 0
            pdata.tths_id = this.tths_id
            api
              .layds_bohoso_trong_kho(this.axios, pdata)
              .then((response) => {
                if (response.data.error_code && response.data.error_code === this.success_code) {
                  this.listbox.ds_bohoso.list = response.data.data
                  // this.listbox.ds_bohoso.list.forEach((i) => {
                  //   i.ngay_cn = _this.formatDatTime(i.ngay_cn)
                  //   i.ngaytao = _this.formatDatTime(i.ngaytao)
                  //   i.ngay_ht = _this.formatDatTime(i.ngay_ht)
                  // })
                } else {
                  this.listbox.ds_bohoso.list = []
                  this.listbox.chitiet_hoso.list = []
                  this.listbox.donvinhan_hoso.list = []
                  this.XOA_FORM_TT_BO_HS()
                }
              })
              .finally(() => {
                this.loading(false)
              })
          } else {
            this.ShowAlert('Có lỗi trong khi thêm mới hồ sơ', 2)
          }
        })
        .catch(function () {
          this.ShowAlert('Tạo bô hồ sơ không thành công!', 2)
          return
        })
        .finally(() => {
          this.loading(false)
        })
      this.loading(false)
    },
    async tsbtnHuyBo_Clicked() {
      this.isNhapMoi = false
      this.isCapNhat = false
      this.XOA_FORM_TT_BO_HS()
    },
    async tsbtnXoaPhieu_Clicked() {
      if (this.ttBoHS.nguoi_cn != this.tt_nd.ma_nd) {
        this.ShowAlert('Bạn không được phép xóa bộ hồ sơ của người khác', 1)
        return
      }
      this.$bvModal
        .msgBoxConfirm('Bạn có thực sự muốn xóa hồ sơ không?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            if (this.listbox.ds_bohoso.checked.length > 0) {
              var pdata = []
              for (var hs of this.listbox.ds_bohoso.checked) {
                pdata.push({
                  bohs_id: this.ttBoHS.bohsid,
                  ghichu: this.ttBoHS.ghichu,
                  hdkh_id: this.ttBoHS.hdkhid,
                  hoso_id: 0,
                  kieu: 3,
                  quytrinh_id: this.quytrinh_id,
                  tths_id: this.tths_id
                })
              }
              this.loading(true)
              await api
                .capnhat_bohoso(this.axios, [pdata])
                .then((response) => {
                  if (response.data.data.length > 0) {
                    if (response.data.data[0].search('OK') == -1) {
                      this.ShowAlert(response.data.data[0], 1)
                      return
                    } else {
                      // Xóa file sever
                      this.listbox.chitiet_hoso.list.forEach((file) => {
                        if (file.url == null || !file.url || file.machung != null || file.machung || file.nhom_id != null || file.nhom_id) {
                          if (file.machung != null || file.machung || file.nhom_id != null || file.nhom_id) {
                            api
                              .sp_lay_file_hs_nhom_id(this.axios, {
                                file_id: file.file_id,
                                nhom_id: file.nhom_id
                              })
                              .then((response) => {
                                if (response.data.data) {
                                  api
                                    .xoa_file(this.axios, {
                                      list_file: file.url
                                    })
                                    .then((response) => {})
                                }
                              })
                          }
                        }
                      })
                      this.TIMKIEM(null)
                      this.ShowAlert(response.data.data[0], 1)
                      return
                    }
                  }
                })
                .catch(function () {
                  this.ShowAlert('Xóa bộ hồ sơ không thành công!', 2)
                  return
                })
                .finally(() => {
                  this.loading(false)
                })
              //this.btnUpdate();
            }
          }
        })
    },
    async tsbtnThemFile_Clicked() {
      console.log('tsbtnThemFile_Clicked')
      console.log(this.listbox.ds_bohoso.checked)
      this.frmUploadFile.input.vhdkh_id = this.ttBoHS.hdkhid
      this.frmUploadFile.input.vdanhsach_bhs = [this.gridDSBoHoSo.value]
      this.isThemFile = !this.isThemFile
      //Lấy danh sách thuê bao
      // this.loading(true)
      // api
      //   .lay_ds_hd_tb_theo_hdkh_id(this.axios, {
      //     vhdkh_id: this.ttBoHS.hdkhid
      //   })
      //   .then((response) => {
      //     if (response.data.error_code && response.data.error_code === this.success_code) {
      //       this.listbox.ds_thuebao.list = response.data.data
      //     } else {
      //       this.listbox.ds_thuebao.list = []
      //       this.listbox.ds_thuebao.value = {}
      //     }
      //   })
      //   .finally(() => {
      //     this.loading(false)
      //   })
    },
    async tsbtnXoaFile_Clicked() {
      if (this.listbox.chitiet_hoso.checked.length == 0) {
        this.ShowAlert('Bạn phải chọn ít nhất một file để xóa', 1)
        return
      }
      if (this.tt_nd.ma_nd != 'admin') {
        let check = false
        this.listbox.chitiet_hoso.checked.forEach((check_id) => {
          var f = this.listbox.chitiet_hoso.list.find((f) => f.file_id == check_id)
          if (f && f.nguoi_cn != this.$root.token.getUserName()) {
            check = true
          }
        })
        if (check) {
          this.ShowAlert('Bạn không được quyền sửa xóa file hồ sờ của người dùng khác', 1)
          return
        }
      }
      this.$bvModal
        .msgBoxConfirm('Bạn có thực sự muốn xóa file hồ sơ được chọn không?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            var pdata = {}
            pdata.bohoso_id = this.ttBoHS.bohsid
            pdata.file_url = this.listbox.ds_file_hs.value.url
            pdata.ghichu = this.ghichufile
            pdata.hoso_id = 0
            pdata.kieu = 3
            pdata.file_id = 0
            pdata.loaifile_id = this.listbox.loaifile.value.id
            pdata.files = []

            this.listbox.chitiet_hoso.checked.forEach((check_id) => {
              let file = this.listbox.chitiet_hoso.list.find((f) => f.file_id == check_id)
              if (file) {
                pdata.files.push({
                  file_id: file.file_id,
                  hdtb_id: file.hdtb_id,
                  thuebao_id: file.thuebao_id,
                  trangthai: file.trangthaihs_id
                })
              }
            })
            api
              .capnhat_file_hoso(this.axios, pdata)
              .then((response) => {
                if (response.data.error_code && response.data.error_code === this.success_code) {
                  if (response.data.data[0]) {
                    this.ShowAlert(response.data.data[0], 1)
                  }
                  // Xóa file sever
                  this.listbox.chitiet_hoso.checked.forEach((check_id) => {
                    let file = this.listbox.chitiet_hoso.list.find((f) => f.file_id == check_id)
                    var check = false
                    if (file.url == null || !file.url || file.machung != null || file.machung || file.nhom_id != null || file.nhom_id) {
                      if (file.machung != null || file.machung || file.nhom_id != null || file.nhom_id) {
                        api
                          .sp_lay_file_hs_nhom_id(this.axios, {
                            file_id: file.file_id,
                            nhom_id: file.nhom_id
                          })
                          .then((response) => {
                            if (response.data.data) {
                              api
                                .xoa_file(this.axios, {
                                  list_file: file.url
                                })
                                .then((response) => {})
                            }
                          })
                      }
                    }
                  })
                  // load lại dữ liệu file hồ sơ sau khi xóa
                  api
                    .layds_file_trong_hoso(this.axios, {
                      hoso_id: this.ttBoHS.bohsid
                    })
                    .then((response) => {
                      if (response.data.error_code && response.data.error_code === this.success_code) {
                        this.listbox.chitiet_hoso.list = response.data.data
                      } else {
                        this.listbox.chitiet_hoso.list = []
                      }
                    })
                    .finally(() => {
                      this.loading(false)
                    })
                  this.ShowAlert('Xóa file hồ sơ thành công!', 0)
                } else {
                  this.ShowAlert(response.data.message, 2)
                }
              })
              .catch(function () {
                this.ShowAlert('Xóa file hồ sơ không thành công!', 2)
              })
              .finally(() => {
                this.loading(false)
              })
          }
        })
    },
    async tsbtnChuyenHoSo_Clicked() {
      if (this.listbox.khohoso.value.id == '' || this.listbox.khohoso.value.id == null) {
        this.ShowAlert('Bạn chưa chọn kho hồ sơ để chuyển', 1)
        return
      }
      if (this.listbox.khohoso.value.id == this.hosoid) {
        this.ShowAlert('Bạn chưa chọn kho mới để chuyển bộ hồ sơ đến', 1)
        return
      }
      var kq = confirm('Bạn có thực sự muốn chuyển kho hồ sơ không?')
      if (kq == false) {
        return
      }
      //gọi api chuyển kho hồ sơ
      var pdata = {}
      pdata.bhs_id = this.ttBoHS.bohsid
      pdata.hoso_id_new = this.listbox.khohoso.value.id
      pdata.hoso_id_old = this.ttBoHS.khohoso
      this.loading(true)
      api
        .chuyen_hoso(this.axios, pdata)
        .then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert('Chuyển kho hồ sơ thành công!', 0)
          } else {
            this.ShowAlert(response.data.data, 2)
            return
          }
        })
        .finally(() => {
          this.loading(false)
        })

      //refresh lại tree
      this.refreshTree(false)
    },
    THIETLAP_GIAODIEN() {
      var tag = this.Tag.split('-')
      if (tag[0] == 1) {
        this.tths_id = HOSO_LC.TRANGTHAI_MOI
        this.quytrinh_id = tag[1]
      }
      if (tag[0] == 2) {
        this.tths_id = HOSO_LC.TRANGTHAI_HT
      }
      // Kiểm tra quyền
      console.log('THIETLAP_GIAODIEN')
      console.log(this.dsQuyen)
      console.log(HOSO_LC.QUYEN_CNHS)
      console.log(this.tths_id)
      console.log(HOSO_LC.TRANGTHAI_HT)

      if (!this.dsQuyen.includes(HOSO_LC.QUYEN_CNHS) && this.tths_id == HOSO_LC.TRANGTHAI_HT) {
        this.tsbtnNhapMoi.visible = false
        this.tsbtnThemFile.visible = false
        this.tsbtnXoaFile.visible = false
        this.tsbtnCapNhat.visible = false
        this.tsbtnXoaPhieu.visible = false
      }
      this.tsbtnChuyenHoSo.visible = false
      if (this.dsQuyen.includes(HOSO_LC.QUYEN_LCHS) && this.tths_id == HOSO_LC.TRANGTHAI_HT) {
        this.cboKhoHSDisable = false
        this.tsbtnChuyenHoSo.visible = false
      }
    },
    onThongTinFileSelected(e) {
      if (e.selectedIndex == 1) {
        this.loading(true)
        api
          .lay_ds_filehs_theo_kh(this.axios, { params: { vhdkh_id: this.ttBoHS.hdkhid } })
          .then((res) => {
            this.loading(false)
            console.log('lay_ds_filehs_theo_kh')
            console.log(res.data)
            this.listbox.ds_daUpload.list = res.data.data
          })
          .catch((err) => {
            this.loading(false)
          })
      }
    },
    loadDanhSachDaUpload() {
      console.log('loadDanhSachDaUpload')
    },
    ShowAlert(mesage, val) {
      if (val == 0) {
        this.$toast.success(mesage)
      } else if (val == 1) {
        this.$toast.warning(mesage)
      } else if (val == 2) {
        this.$toast.error(mesage)
      }
    },
    btnSort() {
      this.bsort = this.bsort * -1
      this.refreshTree(true)
    },
    refreshTree(isSort) {
      this.loading(true)
      if (!isSort) this.listbox.ds_doituonghs.list = []
      let postData = {
        tungay: this.tu_ngay.toLocaleString('en-GB').substring(0, 10), //.getDate(),
        denngay: this.den_ngay.toLocaleString('en-GB').substring(0, 10) //.getDate()
      }
      api.lay_ds_doituong_hs_v2(this.axios, postData).then((response) => {
        this.loading(false)
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_doituonghs.list = response.data.data
          this.listbox.ds_doituonghs.list.forEach((i) => {
            i.isSelected = false
            if (i.hoso_id == -1) {
              i.expanded = true
            }
          })
          this.createTree([...this.listbox.ds_doituonghs.list])
        }
      })
    },
    createTree(dsdoituong) {
      dsdoituong.forEach((i) => {
        i.has_child = i.has_child == 0 ? false : true
      })
      var tree = dsdoituong.filter((a) => a.hoso_cha_id == null)
      function addTree(item, sort, sfilter) {
        if (item.doituong == null) item.doituong = 'Root'
        var dsitems = dsdoituong.filter((a) => a.hoso_cha_id === item.hoso_id)
        if (sfilter) {
          dsitems = dsdoituong.filter((a) => a.hoso_cha_id === item.hoso_id && a.doituong.indexOf(sfilter) >= 0)
        }
        if (dsitems && dsitems.length > 0) {
          for (var i = 0; i < dsitems.length; i++) dsitems[i] = addTree(dsitems[i])
          item.child = dsitems
          item.child = dsitems.sort((a, b) => (a.doituong > b.doituong ? sort : b.doituong > a.doituong ? -sort : 0))
        }
        return item
      }
      for (var j = 0; j < tree.length; j++) tree[j] = addTree(tree[j], this.bsort, this.txtDoiTuong)
      this.fields = {
        dataSource: tree,
        id: 'hoso_id',
        parentID: 'hoso_cha_id',
        text: 'ma_hs_doituong',
        hasChildren: 'has_child',
        child: 'child',
        imageUrl: 'icon',
        selected: 'isSelected'
      }
      this.$forceUpdate()
    },
    sortTree(dsitems) {
      if (dsitems && dsitems.length > 0) {
        return dsitems.sort((a, b) => (a.doituong > b.doituong ? this.bsort : b.doituong > a.doituong ? -this.bsort : 0))
      }
    },
    onEnter() {
      this.refreshTree(false)
    },
    treeClicked(val) {
      if (val.nodeData.id == -1) {
        return
      }
      var pdata = {}
      pdata.hoso_id = val.nodeData.id
      pdata.tungay = this.tu_ngay
      pdata.denngay = this.den_ngay
      pdata.timail_id = this.isLayToanBo ? 1 : 0
      pdata.tths_id = this.tths_id
      this.loading(true)
      let _this = this
      api.lay_ds_bo_hs_trongkho(this.axios, pdata).then((response) => {
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_bohoso.list = ['', null].includes(this.ttBoHS.magd) ? response.data.data : response.data.data.filter((e) => e.ma_gd == this.ttBoHS.magd)

          console.log('listbox.ds_bohoso.list', this.listbox.ds_bohoso.list)
          // this.listbox.ds_bohoso.list.forEach((i) => {
          //   i.ngay_cn = _this.formatDatTime(i.ngay_cn)
          //   i.ngaytao = _this.formatDatTime(i.ngaytao)
          //   i.ngay_ht = _this.formatDatTime(i.ngay_ht)
          // })
          if (this.txtMaGDSearch) {
            let index = this.listbox.ds_bohoso.list.findIndex((item) => item.ma_gd == this.txtMaGDSearch)
            if (index >= 0) {
              if (index >= this.listbox.ds_bohoso.pageSize) {
                var page = Math.floor(index / this.listbox.ds_bohoso.pageSize)
                var odd = index % this.listbox.ds_bohoso.pageSize
                this.$refs.grid_ds_bo_hs.$refs.pagination.gotoPage(page)
                this.$refs.grid_ds_bo_hs.$refs.grid.selectRow(odd, true)
              } else {
                this.$refs.grid_ds_bo_hs.$refs.grid.selectRow(index, true)
              }
            }
          } else {
            this.$refs.grid_ds_bo_hs.$refs.grid.selectRow(0, true)
          }
        } else {
          this.listbox.ds_bohoso.list = []
          this.listbox.chitiet_hoso.list = []
          this.listbox.donvinhan_hoso.list = []
          this.XOA_FORM_TT_BO_HS()
        }
        this.loading(false)
      })
    },

    loadBoHSTheoMaGD(obj) {
      this.loading(true)
      api
        .lay_ds_bo_hs(this.axios, {
          vtungay: obj ? '' : this.tu_ngay.toLocaleString('en-GB').substring(0, 10),
          vdenngay: obj ? '' : this.den_ngay.toLocaleString('en-GB').substring(0, 10),
          vhdkh_id: obj ? obj.hdkh_id : 0,
          vtimall: this.isLayToanBo ? 1 : 0,
          vtths_id: this.tths_id
        })
        .then((res) => {
          this.listbox.ds_bohoso.list = []
          if (res.data.error_code && res.data.error_code === this.success_code) {
            this.listbox.ds_bohoso.list = res.data.data
            // this.listbox.ds_bohoso.list.forEach((i) => {
            //   i.ngay_cn = _this.formatDatTime(i.ngay_cn)
            //   i.ngaytao = _this.formatDatTime(i.ngaytao)
            //   i.ngay_ht = _this.formatDatTime(i.ngay_ht)
            // })
          } else {
            if (res.data.error_code == 'BSS-00000204') {
              this.ShowAlert('Không tìm thấy dữ liệu', 1)
            } else {
              this.ShowAlert(res.data.message, 1)
            }
          }
          this.loading(false)
        })
    },
    onMaGDEnter() {
      this.txtMaGDSearch = this.ttBoHS.magd
      if (!this.ttBoHS.magd) {
        return
      }
      if (this.tths_id != HOSO_LC.TRANGTHAI_HT) this.TIMKIEM(this.txtMaGDSearch)
      else this.TIMKIEM_KHO_HS(this.txtMaGDSearch)
    },
    onSelectGD(obj) {
      // Nhập mới
      if (this.ttBoHS.bohs == 0) {
        this.SET_FORM_TT_BO_HS(obj)
      }
      // load data theo ma GD
      else {
        this.TIMKIEM(obj.magd)
      }
    },
    //Xử lý khi nhập mã bộ hs enter
    onMaBoHSEnter() {
      var pdata = {}
      pdata.hoso_id = this.ttBoHS.mabohs
      pdata.tungay = this.ngay_tao.toLocaleString('en-GB').substring(0, 10)
      pdata.denngay = this.ngay_ht.toLocaleString('en-GB').substring(0, 10)
      pdata.tim_all = 0
      pdata.tths_id = this.listbox.trangthaihoso.value.id
      console.log(pdata)
      this.loading(true)
      api
        .layds_bohoso_trong_kho(this.axios, pdata)
        .then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.ds_bohoso.list = response.data.data
            // this.listbox.ds_bohoso.list.forEach((i) => {
            //   i.ngay_cn = _this.formatDatTime(i.ngay_cn)
            //   i.ngaytao = _this.formatDatTime(i.ngaytao)
            //   i.ngay_ht = _this.formatDatTime(i.ngay_ht)
            // })
            if (this.listbox.ds_bohoso.list.length > 0) {
              this.SET_FORM_TT_BO_HS(this.listbox.ds_bohoso.list[0])
              this.listbox.trangthaihoso.value.id = this.listbox.ds_bohoso.list[0].trangthai_id
            }
          } else {
            this.listbox.ds_bohoso.list = []
            this.listbox.chitiet_hoso.list = []
            this.XOA_FORM_TT_BO_HS()
          }
        })
        .finally(() => {
          this.loading(false)
        })
    },
    // Xử lý lâý toàn bộ hdkh chưa có hs
    onLayToanBoChecked() {
      this.loading(true)
      api
        .layds_hopdong_chuaco_hoso(this.axios, {
          tungay: this.tu_ngay.toLocaleString('en-GB').substring(0, 10),
          denngay: this.den_ngay.toLocaleString('en-GB').substring(0, 10),
          loai_id: this.tt_nd.nguoidung_id
        })
        .then((res) => {
          this.listbox.ds_bohoso.list = []
          this.XOA_FORM_TT_BO_HS()
          if (res.data.error_code && res.data.error_code === this.success_code) {
            this.listbox.ds_bohoso.list = res.data.data
            // this.listbox.ds_bohoso.list.forEach((i) => {
            //   i.ngay_cn = _this.formatDatTime(i.ngay_cn)
            //   i.ngaytao = _this.formatDatTime(i.ngaytao)
            //   i.ngay_ht = _this.formatDatTime(i.ngay_ht)
            // })
          } else {
            this.ShowAlert(res.data.message_detail, 1)
          }
          this.loading(false)
        })
    },

    gridtbaoChanged(obj) {
      if (!obj) {
        return
      }
      this.SET_FORM_TT_BO_HS(obj)
      //Lấy danh sách thuê bao
      var pdata = {}
      pdata.vhdkh_id = this.hdkhid
      this.loading(true)
      api
        .lay_ds_hd_tb_theo_hdkh_id(this.axios, pdata)
        .then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.ds_thuebao.list = response.data.data
          } else {
            this.listbox.ds_thuebao.list = []
          }
        })
        .finally(() => {
          this.loading(false)
        })
    },
    gridHoSoChanged(obj) {
      console.log('gridHoSoChanged')
      console.log(obj)

      if (!obj) {
        return
      }
      this.gridDSBoHoSo.value = obj
      // if (this.dsQuyen.includes(HOSO_LC.QUYEN_CNHS) == false && this.tths_id == HOSO_LC.TRANGTHAI_HT) {
      //   this.ttBoHS.enable = false
      // } else {
      //   this.ttBoHS.enable = true
      // }
      this.isCapNhat = true
      this.loading(true)
      this.SET_FORM_TT_BO_HS(obj)

      //load chi tiết hồ sơ
      api
        .layds_file_trong_hoso(this.axios, {
          hoso_id: this.ttBoHS.bohsid
        })
        .then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.chitiet_hoso.list = response.data.data
          } else {
            this.listbox.chitiet_hoso.list = []
          }
        })
        .finally(() => {
          this.loading(false)
        })

      //load đơn vị nhận hồ sơ
      api
        .layds_donvi_nhan_hoso(this.axios, {
          hoso_id: this.ttBoHS.bohsid
        })
        .then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.donvinhan_hoso.list = response.data.data
          } else {
            this.listbox.donvinhan_hoso.list = []
          }
        })
        .finally(() => {
          this.loading(false)
        })

      // Lấy danh sách thuê bao
      // if (this.isThemFile) {
      //   this.tsbtnThemFile_Clicked()
      // }

      // check hs cùng hợp đồng
      if (this.isHSCungHopDong) {
        this.listbox.ds_bohoso.checked = []
        this.listbox.ds_bohoso.list.forEach((hs) => {
          if (hs.ma_kh == obj.ma_kh) {
            hs.checked = true
            this.listbox.ds_bohoso.checked.push(hs.bohs_id)
          } else {
            hs.checked = false
          }
        })
      }
      api
        .lay_ds_filehs_theo_kh(this.axios, { params: { vhdkh_id: this.ttBoHS.hdkhid } })
        .then((res) => {
          this.loading(false)
          console.log('lay_ds_filehs_theo_kh')
          console.log(res.data)
          this.listbox.ds_daUpload.list = res.data.data
        })
        .catch((err) => {
          this.loading(false)
        })

      // load file da upload
      console.log('this.ttBoHS.hdkhid load lai')
      console.log(this.ttBoHS.hdkhid)
    },
    gridHoSoPageChanged(args) {
      this.listbox.ds_bohoso.pageSize = args.pageSize
      this.listbox.ds_bohoso.pageIndex = args.pageIndex
    },
    gridHoSoRowChon(agr) {
      if (!this.txtMaGDSearch) {
        this.$refs.grid_ds_bo_hs.$refs.grid.selectRow(0, true)
        return
      }
      if (this.txtMaGDSearch) {
        let index = this.listbox.ds_bohoso.list.findIndex((item) => item.ma_gd == this.txtMaGDSearch)
        if (index >= 0) {
          if (index >= this.listbox.ds_bohoso.pageSize) {
            var page = Math.floor(index / this.listbox.ds_bohoso.pageSize)
            var odd = index % this.listbox.ds_bohoso.pageSize
            this.$refs.grid_ds_bo_hs.$refs.pagination.gotoPage(page)
            this.$refs.grid_ds_bo_hs.$refs.grid.selectRow(odd, true)
          } else {
            this.$refs.grid_ds_bo_hs.$refs.grid.selectRow(index, true)
          }
        }
      } else {
        this.$refs.grid_ds_bo_hs.$refs.grid.selectRow(0, true)
      }
      this.txtMaGDSearch = ''
    },
    gridChiTietHoSoChanged: function (dataKeys) {},
    gridTBItemshanged(data) {
      this.listbox.ds_thuebao.checked = data
    },

    //xử lý sự kiện khi click vào nút Tìm kiếm
    findClicked() {
      var pdata = {}
      pdata.loai_id = 1
      pdata.tungay = this.tu_ngay.toLocaleString('en-GB').substring(0, 10)
      pdata.denngay = this.den_ngay.toLocaleString('en-GB').substring(0, 10)
      this.loading(true)
      api
        .layds_hopdong_chuaco_hoso(this.axios, pdata)
        .then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.ds_hopdong_chuacohoso.list = response.data.data
          } else {
            this.listbox.ds_hopdong_chuacohoso.list = []
          }
        })
        .finally(() => {
          this.loading(false)
        })
    },

    resetClicked() {
      this.listbox.nhomfile.value.id = ''
      this.listbox.loaifile.value.id = ''
      this.ghichufile = ''
      this.listbox.ds_file_hs.value.url = ''
    },
    //Xử lý sự kiện upload file
    UploadFileHoSo() {
      let formData = new FormData()
      for (var file of this.$refs.files.files) {
        formData.append('files', file)
      }
      this.loading(true)
      api
        .upload_file_hoso(this.axios, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.ds_file_hs.value.url = response.data.data.join('|')
            console.log(this.listbox.ds_file_hs.value.url)
          }
        })
        .catch(function () {
          console.log('Upload file hồ sơ không thành công!')
        })
        .finally(() => {
          this.loading(false)
        })
    },
    btlLuuFileClick() {
      //Trong phần khảo sát code không thấy có đoạn thêm file này (người khảo sát Nguyễn Văn Tiến)
      if (this.listbox.ds_bohoso.list.length == 0 || this.listbox.ds_bohoso.checked.length == 0) {
        this.ShowAlert('Không tìm thấy bộ hồ sơ để thêm file.', 1)
        return
      }
      if (this.listbox.loaifile.value.id == -1) {
        this.ShowAlert('Bạn chưa chọn loại file.', 1)
        return
      }
      if (this.listbox.ds_file_hs.value.url == '') {
        this.ShowAlert('Bạn chưa nhập đường dẫn file', 1)
        return
      }

      if (this.listbox.loaifile.value.id == 2 && this.listbox.ds_thuebao.checked.length == 0) {
        // biên bản nghiệm thu lắp đặt thì  phải tích chọn mã thuê bao để lưu file
        this.ShowAlert('Bạn bắt buộc phải gán thuê bao với loại file biên bản nghiệm thu lắp đặt', 1)
        return
      }
      // 1 hồ sơ
      if (this.listbox.ds_bohoso.checked.length == 1) {
        if (this.ttBoHS.khohoso == '') {
          this.ShowAlert('Không tìm thấy bộ hồ sơ để thêm file.', 1)
          return
        }
        var pdata = {}
        pdata.bohoso_id = this.ttBoHS.bohsid
        pdata.file_url = this.listbox.ds_file_hs.value.url
        pdata.ghichu = this.ghichufile
        pdata.hoso_id = this.ttBoHS.khohoso
        pdata.kieu = 1
        pdata.file_id = 0
        pdata.loaifile_id = this.listbox.loaifile.value.id
        pdata.files = []

        this.listbox.ds_thuebao.checked.forEach((tb) => {
          let file = {
            file_id: 0,
            hdtb_id: tb.HDTB_ID,
            thuebao_id: tb.THUEBAO_ID ? tb.THUEBAO_ID : '',
            trangthai: 0
          }
          pdata.files.push(file)
        })
        // pdata.files = JSON.stringify(pdata.files);
        api
          .capnhat_file_hoso(this.axios, pdata)
          .then((response) => {
            if (response.data.error_code && response.data.error_code === this.success_code) {
              this.ShowAlert('Lưu file hồ sơ thành công!', 0)
            } else {
              this.ShowAlert(response.data.message, 1)
            }
          })
          .catch(function () {
            this.ShowAlert('Lưu file hồ sơ không thành công!', 2)
          })
          .finally(() => {
            this.loading(false)
          })
      }

      // nhiều hồ sơ
      if (this.listbox.ds_bohoso.checked.length > 1) {
        var pdata = {}
        pdata.vbohs_id = []
        pdata.vfile_clob = ''
        pdata.vghichu = this.ghichufile
        pdata.vkieu = 1
        pdata.vloaifile_id = this.listbox.loaifile.value.id
        pdata.vma_kh = 0
        pdata.vurl = this.listbox.ds_file_hs.value.url

        this.listbox.ds_bohoso.checked.forEach((check_id) => {
          var hs = this.listbox.ds_bohoso.list.find((hs) => hs.bohs_id == check_id)
          if (hs) {
            pdata.vbohs_id.push({
              BOHS_ID: hs.bohs_id,
              HDKH_ID: hs.hdkh_id,
              MA_HD: hs.ma_hd,
              MA_KH: hs.ma_kh
            })
          }
        })
        api
          .capnhat_filehs_ds(this.axios, pdata)
          .then((response) => {
            if (response.data.data) {
              this.ShowAlert(response.data.data, 2)
              return
            }
            this.ShowAlert('Lưu file hồ sơ thành công!', 0)
          })
          .catch(function () {
            this.ShowAlert('Lưu file hồ sơ không thành công!', 2)
          })
          .finally(() => {
            this.loading(false)
          })
      }
    },
    btnResetClick() {
      this.tsbtnThemFile_Clicked()
    },
    btnNhomClick() {
      this.listbox.ds_thuebao.header.forEach((h) => {
        if (h.fieldName == 'LOAI_HD') {
          h.isGroupedColumn = !h.isGroupedColumn
        }
      })
      this.listbox.ds_thuebao.tableKey += 1
    },

    async downloadFile(isPreview, url, name) {
      if (isPreview) {
        window.open(url, '_blank')
      } else {
        var instance = this.axios.create()
        instance.baseURL = ''
        instance
          .get(url, { responseType: 'blob' })
          .then((response) => {
            // const download_url = window.URL.createObjectURL(new Blob([response.data]))
            // const link = document.createElement('a')
            // link.href = download_url
            // link.setAttribute('download', name)
            // document.body.appendChild(link)
            // link.click()
            const blob = new Blob([response.data], { type: 'application/pdf' })
            const objectUrl = window.URL.createObjectURL(blob)

            window.open(objectUrl)
            elt.impressionEnCours = false
          })
          .catch((err) => {
            this.ShowAlert('Lỗi tải file: ' + url, 2)
          })
      }
    },
    // format datetime
    formatDatTime(date) {
      return moment(date).format('DD/MM/YYYY HH:mm:ss')
    },

    // Tìm kiếm danh sách hợp đồng theo mã gd
    TIMKIEM(magd) {
      if (magd) {
        this.ttBoHS.magd = magd.trim()
      }
      let _this = this
      // Tìm kiếm theo mã giao dịch
      if (magd) {
        this.loading(true)
        api
          .layds_theo_matb(this.axios, { ma_thuebao: magd })
          .then((response) => {
            if (response.data.error_code && response.data.error_code === this.success_code) {
              if (response.data.data.length == 1) {
                //nếu = 0 thì ko có dữ liệu - Nhập mới
                if (response.data.data[0].bohs == 0) {
                  response.data.data[0].trangthai_id = HOSO_LC.TRANGTHAI_MOI
                  response.data.data[0].ngaytao = this.tt_nd.ngay_cn
                  this.SET_FORM_TT_BO_HS(response.data.data[0])
                }
                // Có dữ liệu
                else {
                  _this.loadBoHSTheoMaGD(response.data.data[0])
                }
              } else {
                // Nhiều record - show ds giao dịch để chọn
                this.listbox.ds_hopdong.list = response.data.data
                this.$bvModal.show('dsHopDong-modal')
              }
            } else {
              this.ShowAlert('Không tìm thấy thông tin với mã giao dịch ' + this.ttBoHS.magd, 1)
            }
          })
          .finally(() => {
            this.loading(false)
          })
      }
      // TIMKIEM_ALL
      else {
        this.loadBoHSTheoMaGD(null)
      }
    },

    async TIMKIEM_KHO_HS(magd) {
      console.log('TIMKIEM_KHO_HS')
      // try {
      // this.loading(true)
      let kq = await api.sp_ds_bo_hs_theo_ma_gd(this.axios, { ma_gd: magd })
      if (kq.data.data && kq.data.data.length) {
        var today = new Date()
        var lastDayOfMonth = new Date(today.getFullYear(), today.getMonth() + 1, 0)
        this.tu_ngay = moment(kq.data.data[0].ngaytao).format('DD/MM/YYYY')
        console.log(moment(kq.data.data[0].ngaytao).format('DD/MM/YYYY'))
        console.log(this.tu_ngay)
        this.den_ngay = moment(lastDayOfMonth).format('DD/MM/YYYY')
        console.log(this.den_ngay)
        this.FIND_NODE_FILE(kq.data.data[0])
        this.frmUploadFile.input.vhoso_id = kq.data.data[0].hoso_id
      } else {
        this.ShowAlert('Không tìm thấy thông tin với mã giao dịch ' + this.ttBoHS.magd, 1)
      }
      // } catch (e) {
      //   this.$toast.error(`${e}`)
      // } finally {
      //   this.loading(false)
      // }

      // api.sp_ds_bo_hs_theo_ma_gd(this.axios, { ma_gd: magd }).then((response) => {
      //   if (response.data.error_code && response.data.error_code === this.success_code) {
      //     if (response.data.data.length > 0) {
      //       this.FIND_NODE_FILE(response.data.data[0])
      //     } else {
      //       this.ShowAlert('Không tìm thấy thông tin với mã giao dịch ' + this.ttBoHS.magd, 1)
      //     }
      //   } else {
      //     this.ShowAlert(response.data.message, 1)
      //   }
      // })
    },
    FIND_NODE_FILE(item) {
      this.listbox.ds_file_hs.value.file_id = item.hoso_id
      this.$refs['treeview'].expandAll([item.hoso_id], 1, false)
      this.listbox.ds_doituonghs.list.forEach((i) => {
        if (i.hoso_id == item.hoso_id) {
          i.isSelected = true
        } else {
          i.isSelected = false
        }
      })
      this.createTree([...this.listbox.ds_doituonghs.list])
      var node = {
        nodeData: {
          id: item.hoso_id
        }
      }
      this.treeClicked(node)
    },
    XOA_FORM_TT_BO_HS() {
      this.ttBoHS.bohsid = 0
      this.ttBoHS.magd = ''
      this.ttBoHS.mabohs = ''
      this.ttBoHS.makh = ''
      this.ttBoHS.tenkh = ''
      this.ttBoHS.hdkhid = ''
      this.ttBoHS.ghichu = ''
      this.ttBoHS.ngay_tao = ''
      this.ttBoHS.ngay_ht = ''
      this.ttBoHS.trangthaihoso = ''
      this.ttBoHS.khohoso = ''
      this.isThemFile = false
      this.btnThemFileVisible = true
      this.btnXoaFileVisible = true
    },
    SET_FORM_TT_BO_HS(data) {
      this.ttBoHS.bohsid = data.bohs_id
      this.ttBoHS.magd = data.ma_gd
      this.ttBoHS.mabohs = data.ma_bhs
      this.ttBoHS.makh = data.ma_kh
      this.ttBoHS.tenkh = data.ten_kh
      this.ttBoHS.hdkhid = data.hdkh_id
      this.ttBoHS.ghichu = data.ghichu
      this.ttBoHS.ngay_tao = data.ngaytao
      this.ttBoHS.ngay_ht = data.ngay_ht ? data.ngay_ht : ''
      this.ttBoHS.trangthaihoso = data.trangthai_id
      this.ttBoHS.khohoso = data.hoso_id ? data.hoso_id : '0'
      this.listbox.khohoso.value.id = this.ttBoHS.khohoso
    }
  }
}
</script>
<style scoped>
a.disabled {
  color: lightgray !important;
}
a.disabled:hover {
  color: lightgray !important;
}
.disabled {
  pointer-events: none;
}
.btn-main {
  background: #0176ff;
  color: #fff;
}

.btn {
  padding: 5px 10px;
  border-radius: 4px;
}

.file-info-wrapper {
  padding: 3px 3px !important;
}

.btn-no-padding {
  padding: 0 0 !important;
  font-size: 14px;
}

.file-info-toolbar-wrapper {
  display: flex;
  align-items: center;
  padding: 0;
}

.upload-tab {
  padding: 0 0 !important;
  font-size: 12px;
}
.upload-tab a {
  padding: 10px 0 !important;
}

.file-info-tab {
  padding: 10px 0;
}

.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}

.disabledInput > a {
  color: #666 !important;
}
</style>
