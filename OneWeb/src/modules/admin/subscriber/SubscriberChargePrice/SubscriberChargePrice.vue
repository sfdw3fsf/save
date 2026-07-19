<template>
  <div>
    <breadcrumb :header="header" />

    <ActionTop :actions="actions" @onActionClick="onActionClick" />

    <div class="page-content">
      <div class="grid-stack-box">
        <div class="box-col" style="width: calc(50% - 8px);">
          <div class="box-form">
            <div class="legend-title">
              <span class="icon fa fa-angle-up"></span> Thông tin mức cước thuê
              bao
            </div>
            <div class="info-row">
              <div class="key w100">Dịch vụ VT</div>
              <div class="value">
                <div class="select-custom" ref="dsdichvuvt">
                  <select2
                    :z-index="2000"
                    :settings="{ dropdownParent: $refs['dsdichvuvt'] }"
                    v-model="dichvuvt_id"
                    :options="
                      dichvu.map((e) => ({
                        id: e.DICHVUVT_ID,
                        text: e.TEN_DVVT,
                      }))
                    "
                  ></select2>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Tên cước TB</div>
              <div class="value">
                <input
                  ref="txtMucCuoc"
                  type="text"
                  class="form-control"
                  v-bind:value="txtMucCuoc.value"
                  @change="(e) => (txtMucCuoc.value = e.target.value)"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Từ ngày</div>
                  <div class="value">
                    <DatePicker
                      :format="dateFormat"
                      :value-type="'format'"
                      v-model="dtpNgayBD.value"
                      ref="dtpNgayBD"
                      @change="onChangeNgayBD"
                    >
                    </DatePicker>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Đến ngày</div>
                  <div class="value">
                    <DatePicker
                      :format="dateFormat"
                      :value-type="'format'"
                      v-model="dtpNgayKT.value"
                      ref="dtpNgayKT"
                      @change="onChangeNgayKT"
                    >
                    </DatePicker>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Cước TB</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control tright"
                      v-bind:value="cp_txtCuoc"
                      @keypress="isNumber($event)"
                      @change="(e) => (cp_txtCuoc = e.target.value)"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Mức cước</div>
                  <div class="value">
                    <div class="select-custom" ref="dsmuccuoc">
                      <select2
                        :z-index="2000"
                        :settings="{ dropdownParent: $refs['dsmuccuoc'] }"
                        v-model="cmbMucCuoc.value"
                        :disabled="!cmbMucCuoc.Enabled"
                        :options="
                          cmbMucCuoc.DataSource.map((e) => ({
                            id: e.MUCCUOC_ID,
                            text: e.TENMUC,
                          }))
                        "
                      ></select2>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Tốc độ</div>
              <div class="value">
                <SelectExt
                  v-model="cboTocDo.value"
                  :dataSource="cboTocDo.DataSource"
                  dataTextField="THUONGHIEU"
                  dataValueField="TOCDO_ID"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w100">Cước khoán</div>
                  <div class="value">
                    <input
                      ref="txtCuocKhoan"
                      type="text"
                      class="form-control tright"
                      v-bind:value="cp_txtCuocKhoan"
                      :disabled="txtCuocKhoan.ReadOnly"
                      :readonly="txtCuocKhoan.ReadOnly"
                      @keypress="isNumber($event)"
                      @change="(e) => (cp_txtCuocKhoan = e.target.value)"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w100">Cước lưu lượng</div>
                  <div class="value">
                    <input
                      ref="txtCuocLuuLuong"
                      type="text"
                      class="form-control tright"
                      :disabled="txtCuocLuuLuong.ReadOnly"
                      :readonly="txtCuocLuuLuong.ReadOnly"
                      v-bind:value="cp_txtCuocLuuLuong"
                      @keypress="isNumber($event)"
                      @change="(e) => (cp_txtCuocLuuLuong = e.target.value)"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w100">Cước trọn gói</div>
                  <div class="value">
                    <input
                      ref="txtCuocTronGoi"
                      type="text"
                      class="form-control tright"
                      v-bind:value="cp_txtCuocTronGoi"
                      :disabled="txtCuocTronGoi.ReadOnly"
                      :readonly="txtCuocTronGoi.ReadOnly"
                      @keypress="isNumber($event)"
                      @change="(e) => (cp_txtCuocTronGoi = e.target.value)"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w100">Cước TB phụ</div>
                  <div class="value">
                    <input
                      ref="txtCuocTBPhu"
                      type="text"
                      class="form-control tright"
                      v-bind:value="cp_txtCuocTBPhu"
                      :disabled="txtCuocTBPhu.ReadOnly"
                      :readonly="txtCuocTBPhu.ReadOnly"
                      @keypress="isNumber($event)"
                      @change="(e) => (cp_txtCuocTBPhu = e.target.value)"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Ghi chú</div>
              <div class="value">
                <textarea
                  name=""
                  class="form-control"
                  style="height: 100px; resize: none"
                  v-bind:value="txtGhiChu.value"
                  @change="(e) => (txtGhiChu.value = e.target.value)"
                ></textarea>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">
              <span class="icon fa fa-angle-up"></span> Thông tin tìm kiếm
            </div>
            <div class="info-row">
              <div class="key w100">
                <div class="check-action">
                  <input
                    type="checkbox"
                    v-model="chkTKLoaiHinhTB.Checked"
                    :disabled="!chkTKLoaiHinhTB.Enabled"
                    class="check"
                  />
                  <span class="name">Loại hình</span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom" ref="dsTKLoaiHinhTB">
                  <select2
                    :z-index="2000"
                    :settings="{ dropdownParent: $refs['dsTKLoaiHinhTB'] }"
                    :disabled="!cboTKLoaiHinhTB.Enabled"
                    v-model="cboTKLoaiHinhTB.value"
                    :options="
                      cboTKLoaiHinhTB.DataSource.map((e) => ({
                        id: e.LOAITB_ID,
                        text: e.LOAIHINH_TB,
                      }))
                    "
                  ></select2>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">
                <div class="check-action">
                  <input
                    type="checkbox"
                    v-model="chkTKTocDo.Checked"
                    :disabled="!chkTKTocDo.Enabled"
                    class="check"
                  />
                  <span class="name">Tốc độ</span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom" ref="dsTKTocDo">
                  <select2
                    :z-index="2000"
                    :settings="{ dropdownParent: $refs['dsTKTocDo'] }"
                    :disabled="!cboTKTocDo.Enabled"
                    v-model="cboTKTocDo.value"
                    :options="
                      cboTKTocDo.DataSource.map((e) => ({
                        id: e.TOCDO_ID,
                        text: e.THUONGHIEU,
                      }))
                    "
                  ></select2>
                </div>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">
              <span class="icon fa fa-angle-up"></span> Danh sách mức cước thuê
              bao
            </div>
            <KDataGrid
              ref="gvBangGia"
              class="table-result table-gachle"
              panelDataHeight="250"
              :columns="gridBangGia.header"
              v-bind:dataSource="gridBangGia.list"
              :rowIndex="rowIndex"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :showColumnCheckbox="false"
              :enabledSelectFirstRow="true"
              @selectedItemsChanged="onSelectedRow"
              @rowSelected="rowSelected"
              :commandColumn="{headerText: 'Thao tác', width: 200, cellCssClass: 'center', firstColumn: false, show: true}"
              :commands="gridBangGia.commands"
              @commandClicked="gridCommand_BangGia"
            />
          </div>
        </div>
        <div class="gutter gutter-horizontal" style="width: 16px;"></div>
        <div class="box-col" style="width: calc(50% - 8px);">
          <div class="box-grid-stack" style="height: 886px">
            <div class="box-form marb0" id="box1">
              <div class="legend-title">
                <span class="icon fa fa-angle-up"></span>
                Loại hình thuê bao
              </div>
              <div class="box-move-select-table">
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridLoaiHinhGan.header"
                    v-bind:dataSource="gridLoaiHinhGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    :defaultColumnCheckboxChecked="true"
                    ref="gvLoaiTBGan"
                    @selectedItemsChanged="
                      dataCheckedsGan($event, TYPE.LOAI_HINH)
                    "
                  />
                </div>
                <div class="actions">
                  <button class="btn" @click="boGan(TYPE.LOAI_HINH)">
                    <span class="-ap icon-chevron-thin-right"></span>
                  </button>
                  <button class="btn" @click="gan(TYPE.LOAI_HINH)">
                    <span class="-ap icon-chevron-thin-left"></span>
                  </button>
                </div>
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridLoaiHinhChuaGan.header"
                    v-bind:dataSource="gridLoaiHinhChuaGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    ref="gvLoaiTBChuaGan"
                    @selectedItemsChanged="
                      dataCheckedsChuaGan($event, TYPE.LOAI_HINH)
                    "
                  />
                </div>
              </div>
            </div>
            <div class="gutter gutter-vertical" style="height: 10px"></div>
            <div class="box-form marb0" id="box2">
              <div class="legend-title">
                <span class="icon fa fa-angle-up"></span>
                Đối tượng
              </div>
              <div class="box-move-select-table">
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridDoiTuongGan.header"
                    :dataSource="gridDoiTuongGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    :defaultColumnCheckboxChecked="true"
                    ref="gvDoiTuongGan"
                    @selectedItemsChanged="
                      dataCheckedsGan($event, TYPE.DOI_TUONG)
                    "
                  />
                </div>
                <div class="actions">
                  <button class="btn" @click="boGan(TYPE.DOI_TUONG)">
                    <span class="-ap icon-chevron-thin-right"></span>
                  </button>
                  <button class="btn" @click="gan(TYPE.DOI_TUONG)">
                    <span class="-ap icon-chevron-thin-left"></span>
                  </button>
                </div>
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridDoiTuongChuaGan.header"
                    :dataSource="gridDoiTuongChuaGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    ref="gvDoiTuongChuaGan"
                    @selectedItemsChanged="
                      dataCheckedsChuaGan($event, TYPE.DOI_TUONG)
                    "
                  />
                </div>
              </div>
            </div>
            <div class="gutter gutter-vertical" style="height: 10px"></div>
            <div class="box-form marb0" id="box3">
              <div class="legend-title">
                <span class="icon fa fa-angle-up"></span>
                Khu vực
              </div>
              <div class="box-move-select-table">
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridKhuVucGan.header"
                    :dataSource="gridKhuVucGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    :defaultColumnCheckboxChecked="true"
                    ref="gvKhuVucGan"
                    @selectedItemsChanged="
                      dataCheckedsGan($event, TYPE.KHU_VUC)
                    "
                  />
                </div>
                <div class="actions">
                  <button class="btn" @click="boGan(TYPE.KHU_VUC)">
                    <span class="-ap icon-chevron-thin-right"></span>
                  </button>
                  <button class="btn" @click="gan(TYPE.KHU_VUC)">
                    <span class="-ap icon-chevron-thin-left"></span>
                  </button>
                </div>
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridKhuVucChuaGan.header"
                    :dataSource="gridKhuVucChuaGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    ref="gvKhuVucChuaGan"
                    @selectedItemsChanged="
                      dataCheckedsChuaGan($event, TYPE.KHU_VUC)
                    "
                  />
                </div>
              </div>
            </div>
            <div class="gutter gutter-vertical" style="height: 10px"></div>
            <div class="box-form marb0" id="box4">
              <div class="legend-title">
                <span class="icon fa fa-angle-up"></span>
                Trang bị
              </div>
              <div class="box-move-select-table">
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridTrangBiGan.header"
                    :dataSource="gridTrangBiGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    :defaultColumnCheckboxChecked="true"
                    ref="gvTrangBiGan"
                    @selectedItemsChanged="
                      dataCheckedsGan($event, TYPE.TRANG_BI)
                    "
                  />
                </div>
                <div class="actions">
                  <button class="btn" @click="boGan(TYPE.TRANG_BI)">
                    <span class="-ap icon-chevron-thin-right"></span>
                  </button>
                  <button class="btn" @click="gan(TYPE.TRANG_BI)">
                    <span class="-ap icon-chevron-thin-left"></span>
                  </button>
                </div>
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridTrangBiChuaGan.header"
                    :dataSource="gridTrangBiChuaGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    ref="gvTrangBiChuaGan"
                    @selectedItemsChanged="
                      dataCheckedsChuaGan($event, TYPE.TRANG_BI)
                    "
                  />
                </div>
              </div>
            </div>
            <div class="gutter gutter-vertical" style="height: 10px"></div>
            <div class="box-form marb0" id="box4">
              <div class="legend-title">
                <span class="icon fa fa-angle-up"></span>
                Tòa nhà
              </div>
              <div class="box-move-select-table">
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridToaNhaGan.header"
                    :dataSource="gridToaNhaGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    :defaultColumnCheckboxChecked="true"
                    ref="gvToaNhaGan"
                    @selectedItemsChanged="
                      dataCheckedsGan($event, TYPE.TOA_NHA)
                    "
                  />
                </div>
                <div class="actions">
                  <button class="btn" @click="boGan(TYPE.TOA_NHA)">
                    <span class="-ap icon-chevron-thin-right"></span>
                  </button>
                  <button class="btn" @click="gan(TYPE.TOA_NHA)">
                    <span class="-ap icon-chevron-thin-left"></span>
                  </button>
                </div>
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridToaNhaChuaGan.header"
                    :dataSource="gridToaNhaChuaGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    ref="gvToaNhaChuaGan"
                    @selectedItemsChanged="
                      dataCheckedsChuaGan($event, TYPE.TOA_NHA)
                    "
                  />
                </div>
              </div>
            </div>
            <div class="gutter gutter-vertical" style="height: 10px"></div>
            <div class="box-form marb0" id="box4">
              <div class="legend-title">
                <span class="icon fa fa-angle-up"></span>
                Loại nhà
              </div>
              <div class="box-move-select-table">
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridLoaiNhaGan.header"
                    :dataSource="gridLoaiNhaGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    :defaultColumnCheckboxChecked="true"
                    ref="gvLoaiNhaGan"
                    @selectedItemsChanged="
                      dataCheckedsGan($event, TYPE.LOAI_NHA)
                    "
                  />
                </div>
                <div class="actions">
                  <button class="btn" @click="boGan(TYPE.LOAI_NHA)">
                    <span class="-ap icon-chevron-thin-right"></span>
                  </button>
                  <button class="btn" @click="gan(TYPE.LOAI_NHA)">
                    <span class="-ap icon-chevron-thin-left"></span>
                  </button>
                </div>
                <div class="box-col">
                  <DataGrid
                    class="table-result table-gachle"
                    panelDataHeight="100"
                    :columns="gridLoaiNhaChuaGan.header"
                    :dataSource="gridLoaiNhaChuaGan.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    ref="gvLoaiNhaChuaGan"
                    @selectedItemsChanged="
                      dataCheckedsChuaGan($event, TYPE.LOAI_NHA)
                    "
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <NhapGiaCucSanModal ref="nhapGiaCucSanModal" :muccuoctb_id="muccuoctb_id" />
    <frmTTMucCuocKhoanMuc ref="frmTTMucCuocKhoanMuc" :muccuoctb_id="muccuoctb_id" />
  </div>
</template>
<script>
import moment from "moment";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import Footer from "@/components/FooterStyle.vue";
import breadcrumb from "@/components/breadcrumb";
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import KDataGrid from "@/components/kylq_components/KDataGrid";
import DataGrid from "@/components/Controls/DataGrid";
import SubscriberChargePriceAPI from "./SubscriberChargePriceAPI";
import NhapGiaCucSanModal from "./popups/NhapGiaCucSanModal.vue";
import frmTTMucCuocKhoanMuc from "./popups/frmTTMucCuocKhoanMuc.vue"
import {
  MUCCUOC,
  DichVuVienThong,
  LoaiHinhTB,
} from "@/modules/admin/category/MultiServicePackage/enum.js";

//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";

export default {
  name: "SubscriberChargePrice",
  components: {
    DatePickerPlugin,
    DatePicker,
    DataGrid,
    KDataGrid,
    DropDownListPlugin,
    Footer,
    breadcrumb,
    ActionTop,
    NhapGiaCucSanModal,
    frmTTMucCuocKhoanMuc,
  },
  props: ["itag"],
  data() {
    return {
      Tag: "",
      _dsDichvu: "",
      dateFormat: "DD/MM/YYYY HH:mm:ss",
      rowIndex: 0,
      header: {
        title: "Danh sách mức cước thuê bao",
        list: [
          {
            name: "Danh sách mức cước thuê bao",
            link: { name: "Ui.buttons" },
            active: true,
          },
        ],
      },
      actions: [
        {
          id: "nhapmoi",
          name: "Nhập mới",
          active: true,
          icon_class: "one-file-plus",
        },
        {
          id: "ghilai",
          name: "Ghi lại",
          active: true,
          icon_class: "one-save",
        },
        {
          id: "huy",
          name: "Huỷ",
          active: true,
          icon_class: "nc-icon-glyph ui-1_circle-remove",
        },
        {
          id: "xoa",
          name: "Xoá",
          active: true,
          icon_class: "one-trash",
        },
        {
          id: "cuoc_tu_den",
          name: "Cước Từ - Đến",
          active: true,
          icon_class: "one-money",
        },
        {
          id: "ctkm",
          name: "CT khoản mục",
          active: true,
          icon_class: "one-file-check",
        },
      ],
      gridBangGia: {
        list: [],
        commands: [
          { name: 'detail', title: 'CT Khoản mục', text: 'CT Khoản mục', cssClass: 'pad3 mar5 lh14 btn-primary', width: 100 },
        ],
        header: [
          {
            fieldName: "MUCUOCTB_ID",
            headerText: "ID",
            allowFiltering: true,
            textAlign: "right",
            type: "Number",
            width: 100,
          },
          {
            fieldName: "MUCCUOC",
            headerText: "Tên mức cước",
            allowFiltering: true,
          },
          {
            fieldName: "CUOC_TB",
            headerText: "Cước TB",
            allowFiltering: true,
            textAlign: "right",
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "CUOCKHOAN",
            headerText: "Cước khoán",
            allowFiltering: true,
            textAlign: "right",
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "CUOC_LL",
            headerText: "Cước lưu lượng",
            allowFiltering: true,
            textAlign: "right",
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "CUOC_TG",
            headerText: "Cước trọn gói",
            allowFiltering: true,
            textAlign: "right",
            type: "Number",
            format: "N0",
          },
        ],
        value: {},
        selected: {},
        isEnabled: true,
        checked: [],
      },
      gridLoaiHinhGan: {
        list: [],
        header: [
          {
            fieldName: "LOAIHINH_TB",
            headerText: "Loại hình TB đã gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridLoaiHinhChuaGan: {
        list: [],
        header: [
          {
            fieldName: "LOAIHINH_TB",
            headerText: "Loại hình TB chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridDoiTuongGan: {
        list: [],
        header: [
          {
            fieldName: "TEN_DT",
            headerText: "Đối tượng đã gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridDoiTuongChuaGan: {
        list: [],
        header: [
          {
            fieldName: "TEN_DT",
            headerText: "Đối tượng chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridKhuVucGan: {
        list: [],
        header: [
          {
            fieldName: "TEN_KV",
            headerText: "Khu vực đã gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridKhuVucChuaGan: {
        list: [],
        header: [
          {
            fieldName: "TEN_KV",
            headerText: "Khu vực chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridTrangBiGan: {
        list: [],
        header: [
          {
            fieldName: "TENTRANGBI",
            headerText: "Trang bị gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridTrangBiChuaGan: {
        list: [],
        header: [
          {
            fieldName: "TENTRANGBI",
            headerText: "Trang bị chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridToaNhaGan: {
        list: [],
        header: [
          {
            fieldName: "TEN_TOANHA",
            headerText: "Tòa nhà đã gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridToaNhaChuaGan: {
        list: [],
        header: [
          {
            fieldName: "TEN_TOANHA",
            headerText: "Tòa nhà chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridLoaiNhaGan: {
        list: [],
        header: [
          {
            fieldName: "TEN_LOAINHA",
            headerText: "Loại nhà đã gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridLoaiNhaChuaGan: {
        list: [],
        header: [
          {
            fieldName: "TEN_LOAINHA",
            headerText: "Loại nhà chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      TYPE: {
        LOAI_HINH: 1,
        DOI_TUONG: 2,
        KHU_VUC: 3,
        TRANG_BI: 4,
        TOA_NHA: 5,
        LOAI_NHA: 6,
      },
      dichvu: [],
      dichvuvt_id: null,
      cmbMucCuoc: {
        value: null,
        Enabled: true,
        DataSource: [],
      },
      cboTocDo: {
        value: null,
        Enabled: true,
        DataSource: [],
      },
      tt_nd: {
        phanvung_id: this.$root.token.getPhanVungID(),
        nguoidung_id: this.$root.token.getNguoiDungID(),
      },
      tkloaihinhtb_all: [],
      cboTKLoaiHinhTB: { value: null, Enabled: false, DataSource: [] },
      cboTKTocDo: { value: null, Enabled: false, DataSource: [] },
      chkTKLoaiHinhTB: { Checked: false, Enabled: true },
      chkTKTocDo: { Checked: false, Enabled: false },
      dataCheckedsLoaiHinhGan: [],
      dataCheckedsLoaiHinhChuaGan: [],
      dataCheckedsDoiTuongGan: [],
      dataCheckedsDoiTuongChuaGan: [],
      dataCheckedsKhuVucGan: [],
      dataCheckedsKhuVucChuaGan: [],
      dataCheckedsTrangBiGan: [],
      dataCheckedsTrangBiChuaGan: [],      
      dataCheckedsToaNhaGan: [],
      dataCheckedsToaNhaChuaGan: [],
      dataCheckedsLoaiNhaGan: [],
      dataCheckedsLoaiNhaChuaGan: [],
      // Left Form
      pKieu: 0,
      muccuoctb_id: 0,
      txtMucCuoc: { value: "" },
      txtCuoc: { value: 0 },
      txtGhiChu: { value: "" },
      txtCuocKhoan: { value: 0, ReadOnly: false },
      txtCuocLuuLuong: { value: 0, ReadOnly: false },
      txtCuocTronGoi: { value: 0, ReadOnly: false },
      txtCuocTBPhu: { value: 0, ReadOnly: false },
      dtpNgayBD: { value: moment(new Date()).format("DD/MM/YYYY HH:mm:ss") },
      dtpNgayKT: { value: moment(new Date()).format("DD/MM/YYYY HH:mm:ss") },
      // Capnhat
      js_muccuoc_tb: [],
      js_mctb_dt: [],
      js_mctb_lhtb: [],
      js_mctb_kv: [],
      js_mctb_tbi: [],
      js_mctb_toanha: [],
      js_mctb_loainha: [],
    };
  },
  methods: {
    tsbtnGhiLai_Click() {
      this.CapNhat();      
    },
    tsbtnXoa_Click() {
      if (
        !this.txtMucCuoc.value ||
        this.txtMucCuoc.value == "" ||
        !this.muccuoctb_id ||
        this.muccuoctb_id == 0
      ) {
        this.$toast.error("Bạn phải chọn mức cước để xóa!");
        return;
      }
      this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn xóa không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.fn_muccuoctb_xoa(this.muccuoctb_id);
            this.loading(false);
          }
        })
        .catch((e) => {
          if (e.data && e.data.message_detail)
            this.ShowError("Lỗi xóa dữ liệu: " + e.data.message_detail);
          else this.ShowError("Lỗi xóa dữ liệu: " + e);
          this.loading(false);
        });
    },
    tsbtnMucCuocUpDown_Click() {
      if (this.muccuoctb_id == 0) {
        this.$toast.error(
          "Chưa có mức cước thuê bao. Hãy nhập mức cước thuê bao trước khi nhập cước từ - đến"
        );
        return;
      }
      this.$refs.nhapGiaCucSanModal.showModal();
    },
    async onActionClick(action) {
      if (action.id == "nhapmoi") {
        this.loading(true);
        await Promise.all([
          this.setActiveActions(1),
          this.hienthi_loaihinhtb(Number(this.dichvuvt_id), this.muccuoctb_id),
          this.hienthi_doituong(this.muccuoctb_id),
          this.hienthi_khuvuc(this.muccuoctb_id),
          this.hienthi_trangbi(this.muccuoctb_id),
          this.hienthi_toanha(this.muccuoctb_id),
          this.hienthi_loainha(this.muccuoctb_id),
        ]);
        this.loading(false);
      } else if (action.id == "ghilai") {
        await this.tsbtnGhiLai_Click();
      } else if (action.id == "huy") {
        await this.setActiveActions(2);
      } else if (action.id == "xoa") {
        await this.tsbtnXoa_Click();
      } else if (action.id == "cuoc_tu_den") {
        await this.tsbtnMucCuocUpDown_Click();
      } else if (action.id == "ctkm") {
        if (!this.gridBangGia.list || this.gridBangGia.list.length == 0) {
          this.$toast.error('Hãy chọn mức cước thuê bao trước khi thêm chi tiết khoản mục!')
          return false
        }
        this.$refs.frmTTMucCuocKhoanMuc.initMucCuoc = this.gridBangGia.selected
        this.$refs.frmTTMucCuocKhoanMuc.showModal();
      }
    },
    setActiveActions(kieu) {
      this.actions.forEach((item) => {
        if (item.id != "cuoc_tu_den" && item.id != "ctkm") {
          item.active = false;
        }
      });
      if (kieu == -1) {
        //bắt đầu
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
        this.pKieu = 0;
        this.$refs.txtMucCuoc.focus();
      } else if (kieu == 0) {
        //bắt đầu
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.pKieu = 0;
        this.$refs.txtMucCuoc.focus();
        this.clear();
      } else if (kieu == 1) {
        //thêm mới
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
        this.pKieu = 0;
        this.$refs.txtMucCuoc.focus();
        this.clear();
      } else if (kieu == 2) {
        //Huỷ
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "xoa")
        ].active = true;
        this.pKieu = 1;
        this.$refs.txtMucCuoc.focus();
        this.clear();
      } else if (kieu == 3) {
        //edit
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "xoa")
        ].active = true;
        this.pKieu = 1;
        this.$refs.txtMucCuoc.focus();
      }
    },
    clear() {
      this.cmbMucCuoc.value = 0;
      this.muccuoctb_id = 0;
      this.txtCuoc.value = 0;
      this.txtGhiChu.value = "";
      this.txtMucCuoc.value = "";
      this.txtCuocTBPhu.value = "";
      this.$refs.txtMucCuoc.focus();
      this.dtpNgayBD.value = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.dtpNgayKT.value = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");

      if (Number(this.dichvuvt_id) == DichVuVienThong.ADSL) {
        if (Number(this.cmbMucCuoc.value) == MUCCUOC.LUU_LUONG) {
          this.txtCuocTronGoi.ReadOnly = true;
          this.txtCuocTronGoi.value = "";
          this.txtCuocKhoan.value = 0;
          this.txtCuocKhoan.ReadOnly = false;
          this.txtCuocLuuLuong.value = 0;
          this.txtCuocLuuLuong.ReadOnly = false;
        } else {
          this.txtCuocTronGoi.ReadOnly = false;
          this.txtCuocTronGoi.value = 0;
          this.txtCuocKhoan.value = "";
          this.txtCuocKhoan.ReadOnly = true;
          this.txtCuocLuuLuong.value = "";
          this.txtCuocLuuLuong.ReadOnly = true;
        }
      } else {
        this.txtCuocTronGoi.value = "";
        this.txtCuocLuuLuong.value = "";
        this.txtCuocKhoan.value = "";
      }
    },
    fn_muccuoctb_xoa: async function (muccuoctb_id) {
      try {
        var input = {
          muccuoctb_id: muccuoctb_id,
        };
        let response = await SubscriberChargePriceAPI.fn_muccuoctb_xoa(
          this.axios,
          input
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          var data = JSON.parse(response.data.data.p_js_returnds);
          if (data.ERROR_CODE == 1) {
            this.$toast.success("Xóa dữ liệu thành công!");
            this.setActiveActions(3);
            await this.HienThiDanhSach();
            this.rowIndex = 0;
          } else {
            this.$toast.error(data.MESSAGE);
          }
        } else {
          this.$toast.error("Lỗi xóa dữ liệu " + (response.data.message_detail ? response.data.message_detail : response.data.message));
        }
      } catch (e) {}
    },
    async CapNhat() {
      try {
        this.loading(true)
        //hieu - 12.11.2010: Kiểm tra dữ liệu trước khi insert
        if (!this.KT_DuLieuNhap()) return;
        var nhapmoi =
          !this.actions[this.actions.findIndex((x) => x.id == "nhapmoi")]
            .active;
        if (nhapmoi) {
          await this.TaoDuLieu(true);
          if (this.muccuoctb_id == 0) {
            this.$toast.error("muccuoctb_id không được bằng 0!");
            return;
          }
          var dt = {
            nhapmoi: nhapmoi ? 1 : 0,
            muccuoctb_id: this.muccuoctb_id,
            js_muccuoc_tb:
              this.js_muccuoc_tb.length > 0 ? this.js_muccuoc_tb : "",
            js_mctb_dt: this.js_mctb_dt.length > 0 ? this.js_mctb_dt : "",
            js_mctb_lhtb: this.js_mctb_lhtb.length > 0 ? this.js_mctb_lhtb : "",
            js_mctb_kv: this.js_mctb_kv.length > 0 ? this.js_mctb_kv : "",
            js_mctb_tbi: this.js_mctb_tbi.length > 0 ? this.js_mctb_tbi : "",
            js_mctb_toanha: this.js_mctb_toanha.length > 0 ? this.js_mctb_toanha : "",
            js_mctb_loainha: this.js_mctb_loainha.length > 0 ? this.js_mctb_loainha : "",
          };
          // console.log(dt);
          await this.fn_muccuoctb_capnhat(JSON.stringify(dt));
        } else {
          await this.TaoDuLieu(false);
          if (this.muccuoctb_id == 0) {
            this.$toast.error("muccuoctb_id không được bằng 0!");
            return;
          }
          var dt = {
            nhapmoi: nhapmoi ? 1 : 0,
            muccuoctb_id: this.muccuoctb_id,
            js_muccuoc_tb:
              this.js_muccuoc_tb.length > 0 ? this.js_muccuoc_tb : "",
            js_mctb_dt: this.js_mctb_dt.length > 0 ? this.js_mctb_dt : "",
            js_mctb_lhtb: this.js_mctb_lhtb.length > 0 ? this.js_mctb_lhtb : "",
            js_mctb_kv: this.js_mctb_kv.length > 0 ? this.js_mctb_kv : "",
            js_mctb_tbi: this.js_mctb_tbi.length > 0 ? this.js_mctb_tbi : "",
            js_mctb_toanha: this.js_mctb_toanha.length > 0 ? this.js_mctb_toanha : "",
            js_mctb_loainha: this.js_mctb_loainha.length > 0 ? this.js_mctb_loainha : "",
          };
          // console.log(dt);
          await this.fn_muccuoctb_capnhat(JSON.stringify(dt));
        }
        // int vmuccuoctb_id = muccuoctb_id;
        // SetButton(3);
        // HienThiDanhSach();
        //Choose updated row
        // grvDanhSach.ClearColumnsFilter();
        // grvDanhSach.FocusedRowHandle = GridControl.AutoFilterRowHandle;
        // ChucNang.FocusGridViewRow(grvDanhSach, "MUCUOCTB_ID", vmuccuoctb_id);
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Lỗi tạo dữ liệu cập nhật " + e);
        }
      } finally {
        this.loading(false)
      }
    },
    fn_muccuoctb_capnhat: async function (input) {
      try {
        let response = await SubscriberChargePriceAPI.fn_muccuoctb_capnhat(
          this.axios,
          input
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          var data = JSON.parse(response.data.data.p_js_returnds);
          if (data.ERROR_CODE == 1) {
            this.$toast.success("Cập nhật dữ liệu thành công!");
            this.setActiveActions(3);
            await this.HienThiDanhSach();

            var nhapmoi =
              !this.actions[this.actions.findIndex((x) => x.id == "nhapmoi")]
                .active;
            var totalRecords = this.gridBangGia.list.length;
            var page_sz = this.$refs.gvBangGia.$refs.pagination.pageSizeData;
            var page_num = (totalRecords - (totalRecords % page_sz)) / page_sz;
            if (nhapmoi) {
              this.$refs.gvBangGia.$refs.pagination.gotoPage(page_num);
              this.rowIndex =
                totalRecords % page_sz == 0
                  ? page_sz - 1
                  : (totalRecords % page_sz) - 1;
            }
          } else {
            this.$toast.error(`Lỗi cập nhật dữ liệu muccuoctb_id ${this.muccuoctb_id} :` + data.MESSAGE);
          }
        } else {
          this.$toast.error(
            `Lỗi cập nhật dữ liệu muccuoctb_id ${this.muccuoctb_id} :` + (response.data.message_detail ? response.data.message_detail : response.data.message)
          );
        }
      } catch (e) {}
    },
    async TaoDuLieu(themmoi) {
      try {
        this.js_muccuoc_tb = [];
        this.js_mctb_dt = [];
        this.js_mctb_lhtb = [];
        this.js_mctb_kv = [];
        this.js_mctb_tbi = [];
        this.js_mctb_toanha = [];
        this.js_mctb_loainha = [];
        var mct = [];
        var rowdata = {};
        if (themmoi) {
          this.muccuoctb_id = await this.GET_KEYS("MUCCUOC_TB");
        }
        rowdata.DICHVUVT_ID = Number(this.dichvuvt_id);
        rowdata.MUCUOCTB_ID = Number(this.muccuoctb_id);
        rowdata.MUCCUOC = this.txtMucCuoc.value;
        rowdata.CUOC_TB = Number(this.txtCuoc.value);
        rowdata.TUNGAY = this.dtpNgayBD.value;
        rowdata.DENNGAY = this.dtpNgayKT.value;
        rowdata.GHICHU = this.txtGhiChu.value;
        rowdata.CUOC_TB_PHU = this.txtCuocTBPhu.value ? Number(this.txtCuocTBPhu.value) : 0
        if (
          this.dichvuvt_id == DichVuVienThong.ADSL ||
          this.dichvuvt_id == DichVuVienThong.BRCD_DOITAC
        ) {
          rowdata.TOCDO_ID = Number(this.cboTocDo.value);
          //Lấy ra loại hình thuê bao
          // Nếu là mtv
          var input = {
            type: 2,
            param: this.cboTocDo.value.toString(),
          };
          var vloaitb_id = await this.FN_TT_TOCDO_ADSL(input);
          if (Number(vloaitb_id) != LoaiHinhTB.INTERNET_MYTV) {
            rowdata.MUCCUOC_ID = Number(this.cmbMucCuoc.value);
            if (Number(this.cmbMucCuoc.value) == MUCCUOC.LUU_LUONG) {
              rowdata.CUOCKHOAN = this.txtCuocKhoan.value
                ? Number(this.txtCuocKhoan.value.toString().trim())
                : 0;
              rowdata.CUOC_LL = this.txtCuocLuuLuong.value
                ? Number(this.txtCuocLuuLuong.value.toString().trim())
                : 0;
              rowdata.CUOC_TG = 0;
            } else {
              rowdata.CUOC_TG = this.txtCuocTronGoi.value
                ? Number(this.txtCuocTronGoi.value.toString().trim())
                : 0;
              rowdata.CUOCKHOAN = 0;
              rowdata.CUOC_LL = 0;
            }
          }
        }
        if (
          this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
          this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
          this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
          this.dichvuvt_id == DichVuVienThong.MEGA_EYES ||
          this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH ||
          this.dichvuvt_id == DichVuVienThong.HATANG_VIENTHONG
        ) {
          rowdata.TOCDO_ID = Number(this.cboTocDo.value);
          rowdata.MUCCUOC_ID = Number(this.cmbMucCuoc.value);
          if (Number(this.cmbMucCuoc.value) == MUCCUOC.LUU_LUONG) {
            rowdata.CUOCKHOAN = this.txtCuocKhoan.value
              ? Number(this.txtCuocKhoan.value.toString().trim())
              : 0;
            rowdata.CUOC_LL = this.txtCuocLuuLuong.value
              ? Number(this.txtCuocLuuLuong.value.toString().trim())
              : 0;
            rowdata.CUOC_TG = 0;
          } else {
            rowdata.CUOC_TG = this.txtCuocTronGoi.value
              ? Number(this.txtCuocTronGoi.value.toString().trim())
              : 0;
            rowdata.CUOCKHOAN = 0;
            rowdata.CUOC_LL = 0;
          }
        }
        mct.push(rowdata);

        var md = [];
        var ml = [];
        var kv = [];
        var tbi = [];
        var toanha = [];
        var loainha = [];
        if (this.gridDoiTuongGan.list.length > 0) {
          for (var i = 0; i < this.gridDoiTuongGan.list.length; i++) {
            var row = {};
            row.DOITUONG_ID = Number(
              this.gridDoiTuongGan.list[i]["DOITUONG_ID"]
            );
            row.MUCUOCTB_ID = this.muccuoctb_id;
            md.push(row);
          }
        }

        if (this.gridLoaiHinhGan.list.length > 0) {
          for (var i = 0; i < this.gridLoaiHinhGan.list.length; i++) {
            var row = {};
            row.LOAITB_ID = Number(this.gridLoaiHinhGan.list[i]["LOAITB_ID"]);
            row.MUCUOCTB_ID = this.muccuoctb_id;
            ml.push(row);
          }
        }

        if (this.gridKhuVucGan.list.length > 0) {
          for (var i = 0; i < this.gridKhuVucGan.list.length; i++) {
            var row = {};
            row.KHUVUC_ID = Number(this.gridKhuVucGan.list[i]["KHUVUC_ID"]);
            row.MUCUOCTB_ID = this.muccuoctb_id;
            kv.push(row);
          }
        }

        if (this.gridTrangBiGan.list.length > 0) {
          for (var i = 0; i < this.gridTrangBiGan.list.length; i++) {
            var row = {};
            row.TRANGBI_ID = Number(this.gridTrangBiGan.list[i]["TRANGBI_ID"]);
            row.MUCUOCTB_ID = this.muccuoctb_id;
            tbi.push(row);
          }
        }

        if (this.gridToaNhaGan.list.length > 0) {
          for (var i = 0; i < this.gridToaNhaGan.list.length; i++) {
            var row = {};
            row.TOANHA_ID = Number(this.gridToaNhaGan.list[i]["TOANHA_ID"]);
            row.MUCUOCTB_ID = this.muccuoctb_id;
            toanha.push(row);
          }
        }

        if (this.gridLoaiNhaGan.list.length > 0) {
          for (var i = 0; i < this.gridLoaiNhaGan.list.length; i++) {
            var row = {};
            row.LOAINHA_ID = Number(this.gridLoaiNhaGan.list[i]["LOAINHA_ID"]);
            row.MUCUOCTB_ID = this.muccuoctb_id;
            loainha.push(row);
          }
        }
        this.js_muccuoc_tb = mct;
        this.js_mctb_dt = md;
        this.js_mctb_lhtb = ml;
        this.js_mctb_kv = kv;
        this.js_mctb_tbi = tbi;
        this.js_mctb_toanha = toanha;
        this.js_mctb_loainha = loainha;
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Lỗi tạo dữ liệu cập nhật " + e);
        }
      }
    },
    KT_DuLieuNhap() {      
      if (new Date(moment(this.dtpNgayBD.value, "DD/MM/YYYY HH:mm:ss")) > new Date(moment(this.dtpNgayKT.value, "DD/MM/YYYY HH:mm:ss"))) {
        this.$toast.error("Từ ngày không được lớn hơn Đến ngày!");
        return false;
      }

      if (!this.txtMucCuoc.value) {
        this.$toast.error("Bạn phải nhập tên mức cước thuê bao!");
        this.$refs.txtMucCuoc.focus();
        return false;
      }

      //TuanNA: Zemcode theo yc của HiếuTV --> cho phép cập nhật mức cước không theo đối tượng -- ngày 09.08.2012
      if (this.gridLoaiHinhGan.list.length <= 0) {
        this.$toast.error("Bạn phải gán mức cước loại hình thuê bao!");
        return false;
      }

      if (this.dichvuvt_id.toString() == DichVuVienThong.ADSL.toString()) {
        if (Number(this.cmbMucCuoc.value) == MUCCUOC.LUU_LUONG) {
          if (this.txtCuocKhoan.value != 0 && (this.txtCuocKhoan.value == "" || this.txtCuocKhoan.value == null)) {
            this.$toast.error("Bạn chưa nhập cước khoán!");
            this.$refs.txtCuocKhoan.focus();
            return false;
          }
          if (this.txtCuocLuuLuong.value != 0 && (this.txtCuocLuuLuong.value == "" || this.txtCuocLuuLuong.value == null)) {
            this.$toast.error("Bạn chưa nhập cước lưu lượng!");
            this.$refs.txtCuocLuuLuong.focus();
            return false;
          }
        } else {
          if (this.txtCuocTronGoi.value != 0 && (this.txtCuocTronGoi.value == "" || this.txtCuocTronGoi.value == null)) {
            this.$toast.error("Bạn chưa nhập cước trọn gói!");
            this.$refs.txtCuocTronGoi.focus();
            return false;
          }
        }
      }
      return true;
    },
    GET_KEYS: async function (key) {
      var input = {
        keyname: key,
      };
      let response = await SubscriberChargePriceAPI.getKey(this.axios, input);
      if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        return Number(response.data.data);
      } else {
        this.$toast.error("Có lỗi trong quá trình tạo key mới");
        return;
      }
    },
    FN_TT_TOCDO_ADSL: async function (dt) {
      let response = await SubscriberChargePriceAPI.FN_TT_TOCDO_ADSL(
        this.axios,
        dt
      );
      if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        return Number(response.data.data);
      } else {
        this.$toast.error(response.data.message_detail ? response.data.message_detail : response.data.message);
        return;
      }
    },
    async onSelectedRow(item) {
      this.loading(true);
      try {
        if (this.gridBangGia.list.length <= 0) {
          this.setActiveActions(1);
          return;
        }
        if (item.length <= 0) {
          this.gridBangGia.selected = {}
          return;
        }
        this.gridBangGia.selected = item[0]
        this.dichvuvt_id = item[0]["DICHVUVT_ID"]
          ? Number(item[0]["DICHVUVT_ID"])
          : 0;

        this.txtMucCuoc.value = item[0]["MUCCUOC"] ? item[0]["MUCCUOC"] : "";

        this.txtCuoc.value = item[0]["CUOC_TB"] || item[0]["CUOC_TB"] == 0
          ? Number(item[0]["CUOC_TB"])
          : null;

        this.cmbMucCuoc.value = item[0]["MUCCUOC_ID"]
          ? Number(item[0]["MUCCUOC_ID"])
          : 0;

        this.muccuoctb_id = item[0]["MUCUOCTB_ID"]
          ? Number(item[0]["MUCUOCTB_ID"])
          : 0;

        this.cboTocDo.value = item[0]["TOCDO_ID"]
          ? Number(item[0]["TOCDO_ID"])
          : 0;

        this.txtCuocKhoan.value = item[0]["CUOCKHOAN"] || item[0]["CUOCKHOAN"] == 0
          ? Number(item[0]["CUOCKHOAN"])
          : null;

        this.txtCuocTronGoi.value = item[0]["CUOC_TG"] || item[0]["CUOC_TG"] == 0
          ? Number(item[0]["CUOC_TG"])
          : null;

        this.txtCuocLuuLuong.value = item[0]["CUOC_LL"] || item[0]["CUOC_LL"] == 0
          ? Number(item[0]["CUOC_LL"])
          : null;

        this.txtCuocTBPhu.value = item[0]["CUOC_TB_PHU"] || item[0]["CUOC_TB_PHU"] == 0
          ? Number(item[0]["CUOC_TB_PHU"])
          : null;
        
        this.txtGhiChu.value = item[0]["GHICHU"] ? item[0]["GHICHU"] : "";

        this.dtpNgayBD.value = item[0]["TUNGAY"]
          ? moment(item[0]["TUNGAY"], "YYYY-MM-DD HH:mm:ss").format(
              "DD/MM/YYYY HH:mm:ss"
            )
          : moment(new Date()).format("DD/MM/YYYY HH:mm:ss");

        this.dtpNgayKT.value = item[0]["DENNGAY"]
          ? moment(item[0]["DENNGAY"], "YYYY-MM-DD HH:mm:ss").format(
              "DD/MM/YYYY HH:mm:ss"
            )
          : moment(new Date()).format("DD/MM/YYYY HH:mm:ss");

        await Promise.all([
          this.hienthi_loaihinhtb(this.dichvuvt_id, this.muccuoctb_id),
          this.hienthi_doituong(this.muccuoctb_id),
          this.hienthi_khuvuc(this.muccuoctb_id),
          this.hienthi_trangbi(this.muccuoctb_id),
          this.hienthi_toanha(this.muccuoctb_id),
          this.hienthi_loainha(this.muccuoctb_id),
        ]);
        this.setActiveActions(3);
      } catch (ex) {
        this.$toast.error("Có lỗi khi chọn dòng DS bảng giá: " + ex);
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    rowSelected: function (value) {
      this.rowIndex = value.rowIndex;
    },
    gridCommand_BangGia: function (type, dataItem) {
      if (type == 'detail') {
        console.log('gridCommand_BangGia', dataItem)
        if (!dataItem.MUCUOCTB_ID) {
          this.$toast.error("Chưa có mức cước thuê bao!");
          return;
        }
        this.$refs.frmTTMucCuocKhoanMuc.initMucCuoc = dataItem
        this.$refs.frmTTMucCuocKhoanMuc.showModal();
      }
    },
    dataCheckedsGan(data, type) {
      if (type == this.TYPE.LOAI_HINH) {
        this.dataCheckedsLoaiHinhGan = data;
      } else if (type == this.TYPE.DOI_TUONG) {
        this.dataCheckedsDoiTuongGan = data;
      } else if (type == this.TYPE.KHU_VUC) {
        this.dataCheckedsKhuVucGan = data;
      } else if (type == this.TYPE.TRANG_BI) {
        this.dataCheckedsTrangBiGan = data;
      } else if (type == this.TYPE.TOA_NHA) {
        this.dataCheckedsToaNhaGan = data;
      } else if (type == this.TYPE.LOAI_NHA) {
        this.dataCheckedsLoaiNhaGan = data
      }
    },
    dataCheckedsChuaGan(data, type) {
      if (type == this.TYPE.LOAI_HINH) {
        this.dataCheckedsLoaiHinhChuaGan = data;
      } else if (type == this.TYPE.DOI_TUONG) {
        this.dataCheckedsDoiTuongChuaGan = data;
      } else if (type == this.TYPE.KHU_VUC) {
        this.dataCheckedsKhuVucChuaGan = data;
      } else if (type == this.TYPE.TRANG_BI) {
        this.dataCheckedsTrangBiChuaGan = data;
      } else if (type == this.TYPE.TOA_NHA) {
        this.dataCheckedsToaNhaChuaGan = data;
      } else if (type == this.TYPE.LOAI_NHA) {
        this.dataCheckedsLoaiNhaChuaGan = data
      }
    },
    gan(type) {
      this.loading(true);
      try {
        if (type == this.TYPE.LOAI_HINH) {
          var dagan = this.gridLoaiHinhGan.list;
          var chuagan = this.gridLoaiHinhChuaGan.list;
          var checkedChuaGan = this.dataCheckedsLoaiHinhChuaGan;
          if (checkedChuaGan.length == 0) {
            this.loading(false);
            return;
          }
          for (var i = 0; i < checkedChuaGan.length; i++) {
            dagan.push(checkedChuaGan[i]);
            const index = chuagan.findIndex((x) => x == checkedChuaGan[i]);
            if (index > -1) {
              chuagan.splice(index, 1);
            }
          }
          this.gridLoaiHinhGan.list = [];
          this.gridLoaiHinhChuaGan.list = [];
          this.HT_Tocdo_Adsl_Combobox_DsLoaiTB(dagan);
          setTimeout(() => {
            this.dataCheckedsLoaiHinhChuaGan = [];
            this.gridLoaiHinhGan.list = dagan;
            this.gridLoaiHinhChuaGan.list = chuagan;
            this.loading(false);
          }, 200);
        } else if (type == this.TYPE.DOI_TUONG) {
          var dagan = this.gridDoiTuongGan.list;
          var chuagan = this.gridDoiTuongChuaGan.list;
          var checkedChuaGan = this.dataCheckedsDoiTuongChuaGan;
          if (checkedChuaGan.length == 0) {
            this.loading(false);
            return;
          }
          for (var i = 0; i < checkedChuaGan.length; i++) {
            dagan.push(checkedChuaGan[i]);
            const index = chuagan.findIndex((x) => x == checkedChuaGan[i]);
            if (index > -1) {
              chuagan.splice(index, 1);
            }
          }
          this.gridDoiTuongGan.list = [];
          this.gridDoiTuongChuaGan.list = [];
          setTimeout(() => {
            this.dataCheckedsDoiTuongChuaGan = [];
            this.gridDoiTuongGan.list = dagan;
            this.gridDoiTuongChuaGan.list = chuagan;
            this.loading(false);
          }, 200);
        } else if (type == this.TYPE.KHU_VUC) {
          var dagan = this.gridKhuVucGan.list;
          var chuagan = this.gridKhuVucChuaGan.list;
          var checkedChuaGan = this.dataCheckedsKhuVucChuaGan;
          if (checkedChuaGan.length == 0) {
            this.loading(false);
            return;
          }
          for (var i = 0; i < checkedChuaGan.length; i++) {
            dagan.push(checkedChuaGan[i]);
            const index = chuagan.findIndex((x) => x == checkedChuaGan[i]);
            if (index > -1) {
              chuagan.splice(index, 1);
            }
          }
          this.gridKhuVucGan.list = [];
          this.gridKhuVucChuaGan.list = [];
          setTimeout(() => {
            this.dataCheckedsKhuVucChuaGan = [];
            this.gridKhuVucGan.list = dagan;
            this.gridKhuVucChuaGan.list = chuagan;
            this.loading(false);
          }, 200);
        } else if (type == this.TYPE.TRANG_BI) {
          var dagan = this.gridTrangBiGan.list;
          var chuagan = this.gridTrangBiChuaGan.list;
          var checkedChuaGan = this.dataCheckedsTrangBiChuaGan;
          if (checkedChuaGan.length == 0) {
            this.loading(false);
            return;
          }
          for (var i = 0; i < checkedChuaGan.length; i++) {
            dagan.push(checkedChuaGan[i]);
            const index = chuagan.findIndex((x) => x == checkedChuaGan[i]);
            if (index > -1) {
              chuagan.splice(index, 1);
            }
          }
          this.gridTrangBiGan.list = [];
          this.gridTrangBiChuaGan.list = [];
          setTimeout(() => {
            this.dataCheckedsTrangBiChuaGan = [];
            this.gridTrangBiGan.list = dagan;
            this.gridTrangBiChuaGan.list = chuagan;
            this.loading(false);
          }, 200);
        } else if (type == this.TYPE.TOA_NHA) {
          var dagan = this.gridToaNhaGan.list;
          var chuagan = this.gridToaNhaChuaGan.list;
          var checkedChuaGan = this.dataCheckedsToaNhaChuaGan;
          if (checkedChuaGan.length == 0) {
            this.loading(false);
            return;
          }
          for (var i = 0; i < checkedChuaGan.length; i++) {
            dagan.push(checkedChuaGan[i]);
            const index = chuagan.findIndex((x) => x == checkedChuaGan[i]);
            if (index > -1) {
              chuagan.splice(index, 1);
            }
          }
          this.gridToaNhaGan.list = [];
          this.gridToaNhaChuaGan.list = [];
          setTimeout(() => {
            this.dataCheckedsToaNhaChuaGan = [];
            this.gridToaNhaGan.list = dagan;
            this.gridToaNhaChuaGan.list = chuagan;
            this.loading(false);
          }, 200);
        } else if (type == this.TYPE.LOAI_NHA) {
          var dagan = this.gridLoaiNhaGan.list;
          var chuagan = this.gridLoaiNhaChuaGan.list;
          var checkedChuaGan = this.dataCheckedsLoaiNhaChuaGan;
          if (checkedChuaGan.length == 0) {
            this.loading(false);
            return;
          }
          for (var i = 0; i < checkedChuaGan.length; i++) {
            dagan.push(checkedChuaGan[i]);
            const index = chuagan.findIndex((x) => x == checkedChuaGan[i]);
            if (index > -1) {
              chuagan.splice(index, 1);
            }
          }
          this.gridLoaiNhaGan.list = [];
          this.gridLoaiNhaChuaGan.list = [];
          setTimeout(() => {
            this.dataCheckedsLoaiNhaChuaGan = [];
            this.gridLoaiNhaGan.list = dagan;
            this.gridLoaiNhaChuaGan.list = chuagan;
            this.loading(false);
          }, 200);
        }
      } catch (e) {
        // console.log(e);
        this.loading(false);
      }
    },
    boGan(type) {
      this.loading(true);
      if (type == this.TYPE.LOAI_HINH) {
        var dagan = this.gridLoaiHinhGan.list;
        var chuagan = this.gridLoaiHinhChuaGan.list;
        var checkedGan = this.dataCheckedsLoaiHinhGan;
        if (checkedGan.length == 0) {
          this.loading(false);
          return;
        }
        for (var i = 0; i < checkedGan.length; i++) {
          chuagan.push(checkedGan[i]);
          const index = dagan.findIndex((x) => x == checkedGan[i]);
          if (index > -1) {
            dagan.splice(index, 1);
          }
        }
        this.gridLoaiHinhGan.list = [];
        this.gridLoaiHinhChuaGan.list = [];
        this.HT_Tocdo_Adsl_Combobox_DsLoaiTB(dagan);
        setTimeout(async () => {
          this.dataCheckedsLoaiHinhGan = dagan;
          this.gridLoaiHinhGan.list = dagan;
          this.gridLoaiHinhChuaGan.list = chuagan;
          this.loading(false);
        }, 200);
      } else if (type == this.TYPE.DOI_TUONG) {
        var dagan = this.gridDoiTuongGan.list;
        var chuagan = this.gridDoiTuongChuaGan.list;
        var checkedGan = this.dataCheckedsDoiTuongGan;
        if (checkedGan.length == 0) {
          this.loading(false);
          return;
        }
        for (var i = 0; i < checkedGan.length; i++) {
          chuagan.push(checkedGan[i]);
          const index = dagan.findIndex((x) => x == checkedGan[i]);
          if (index > -1) {
            dagan.splice(index, 1);
          }
        }
        this.gridDoiTuongGan.list = [];
        this.gridDoiTuongChuaGan.list = [];
        setTimeout(() => {
          this.dataCheckedsDoiTuongGan = dagan;
          this.gridDoiTuongGan.list = dagan;
          this.gridDoiTuongChuaGan.list = chuagan;
          this.loading(false);
        }, 200);
      } else if (type == this.TYPE.KHU_VUC) {
        var dagan = this.gridKhuVucGan.list;
        var chuagan = this.gridKhuVucChuaGan.list;
        var checkedGan = this.dataCheckedsKhuVucGan;
        if (checkedGan.length == 0) {
          this.loading(false);
          return;
        }
        for (var i = 0; i < checkedGan.length; i++) {
          chuagan.push(checkedGan[i]);
          const index = dagan.findIndex((x) => x == checkedGan[i]);
          if (index > -1) {
            dagan.splice(index, 1);
          }
        }
        this.gridKhuVucGan.list = [];
        this.gridKhuVucChuaGan.list = [];
        setTimeout(() => {
          this.dataCheckedsKhuVucGan = dagan;
          this.gridKhuVucGan.list = dagan;
          this.gridKhuVucChuaGan.list = chuagan;
          this.loading(false);
        }, 200);
      } else if (type == this.TYPE.TRANG_BI) {
        var dagan = this.gridTrangBiGan.list;
        var chuagan = this.gridTrangBiChuaGan.list;
        var checkedGan = this.dataCheckedsTrangBiGan;
        if (checkedGan.length == 0) {
          this.loading(false);
          return;
        }
        for (var i = 0; i < checkedGan.length; i++) {
          chuagan.push(checkedGan[i]);
          const index = dagan.findIndex((x) => x == checkedGan[i]);
          if (index > -1) {
            dagan.splice(index, 1);
          }
        }
        this.gridTrangBiGan.list = [];
        this.gridTrangBiChuaGan.list = [];
        setTimeout(() => {
          this.dataCheckedsTrangBiGan = dagan;
          this.gridTrangBiGan.list = dagan;
          this.gridTrangBiChuaGan.list = chuagan;
          this.loading(false);
        }, 200);
      } else if (type == this.TYPE.TOA_NHA) {
        var dagan = this.gridToaNhaGan.list;
        var chuagan = this.gridToaNhaChuaGan.list;
        var checkedGan = this.dataCheckedsToaNhaGan;
        if (checkedGan.length == 0) {
          this.loading(false);
          return;
        }
        for (var i = 0; i < checkedGan.length; i++) {
          chuagan.push(checkedGan[i]);
          const index = dagan.findIndex((x) => x == checkedGan[i]);
          if (index > -1) {
            dagan.splice(index, 1);
          }
        }
        this.gridToaNhaGan.list = [];
        this.gridToaNhaChuaGan.list = [];
        setTimeout(() => {
          this.dataCheckedsToaNhaGan = dagan;
          this.gridToaNhaGan.list = dagan;
          this.gridToaNhaChuaGan.list = chuagan;
          this.loading(false);
        }, 200);
      } else if (type == this.TYPE.LOAI_NHA) {
        var dagan = this.gridLoaiNhaGan.list;
        var chuagan = this.gridLoaiNhaChuaGan.list;
        var checkedGan = this.dataCheckedsLoaiNhaGan;
        if (checkedGan.length == 0) {
          this.loading(false);
          return;
        }
        for (var i = 0; i < checkedGan.length; i++) {
          chuagan.push(checkedGan[i]);
          const index = dagan.findIndex((x) => x == checkedGan[i]);
          if (index > -1) {
            dagan.splice(index, 1);
          }
        }
        this.gridLoaiNhaGan.list = [];
        this.gridLoaiNhaChuaGan.list = [];
        setTimeout(() => {
          this.dataCheckedsLoaiNhaGan = dagan;
          this.gridLoaiNhaGan.list = dagan;
          this.gridLoaiNhaChuaGan.list = chuagan;
          this.loading(false);
        }, 200);
      }
    },
    async frmMucCuocTB_Load() {
      try {
        // Form load
        let responseForm = await SubscriberChargePriceAPI.fn_muccuoctb_load(
          this.axios,
          Number(this.tt_nd.phanvung_id),
          JSON.stringify({
            nguoidung_id: this.tt_nd.nguoidung_id,
          })
        );
        if (
          responseForm &&
          responseForm.data &&
          responseForm.data.error_code &&
          responseForm.data.error_code == "BSS-00000000"
        ) {
          var data = JSON.parse(responseForm.data.data.p_js_returnds);
          if (data.ERROR_CODE.toString() == "1") {
            this.dichvu = data.DS_DICHVUVT;
            this.cmbMucCuoc.DataSource = data.DS_MUCCUOC;            
            this.cboTocDo.DataSource = data.DS_TOCDO_ADSL;
          } else {
            this.$toast.error(data.MESSAGE);
            this.dichvu = [];
            this.cmbMucCuoc.DataSource = [];
            this.cboTocDo.DataSource = [];
          }
        } else {
          this.$toast.error(
            "Lỗi khởi tạo form " + (responseForm.data.message_detail ? responseForm.data.message_detail : responseForm.data.message)
          );
          this.dichvu = [];
          this.cmbMucCuoc.DataSource = [];
          this.cboTocDo.DataSource = [];
        }

        if (this.dichvu.length > 0) {
          this.dichvuvt_id = this.dichvu[0].DICHVUVT_ID;
        }
        if (this.cmbMucCuoc.DataSource.length > 0) {
          this.cmbMucCuoc.value = this.cmbMucCuoc.DataSource[0].MUCCUOC_ID;
        }
        if (this.cboTocDo.DataSource.length > 0) {
          this.cboTocDo.value = this.cboTocDo.DataSource[0].TOCDO_ID;
        }

        // //hard 10679
        await this.HienThiDanhSach();
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Đã xảy ra lỗi khi khởi tạo dữ liệu " + e);
          // console.log(e);
        }
      }
    },
    async HienThiDanhSach() {
      let dichvuvt_id = this.dichvuvt_id ? Number(this.dichvuvt_id) : 0;
      var vloaitb_id = 0;
      var vtocdo_id = -1;
      if (
        dichvuvt_id == DichVuVienThong.ADSL ||
        dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH ||
        dichvuvt_id == DichVuVienThong.MEGA_EYES ||
        dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
        dichvuvt_id == DichVuVienThong.DICHVU_CNTT
      ) {
        if (this.chkTKLoaiHinhTB.Checked)
          vloaitb_id = Number(this.cboTKLoaiHinhTB.value);
        if (this.chkTKTocDo.Checked) vtocdo_id = Number(this.cboTKTocDo.value);
      }

      await this.HienThiDSMucCuoc_TB(dichvuvt_id, vloaitb_id, vtocdo_id);
    },
    async HienThiDSMucCuoc_TB(dichvuvt_id, loaitb_id, tocdo_id) {
      let response = await SubscriberChargePriceAPI.sp_selectmuccuoc_tb(
        this.axios,
        dichvuvt_id,
        loaitb_id,
        tocdo_id
      );
      if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000000"
      ) {
        // console.log(response.data.data);
        if (response.data.data.length > 0) {
          this.gridBangGia.list = this.UpperCasePropertyList(
            response.data.data
          );
        } else {
          this.gridBangGia.list = [];
          this.setActiveActions(1);
          // Right Form
          await Promise.all([
            this.hienthi_loaihinhtb(dichvuvt_id, this.muccuoctb_id),
            this.hienthi_doituong(this.muccuoctb_id),
            this.hienthi_khuvuc(this.muccuoctb_id),
            this.hienthi_trangbi(this.muccuoctb_id),
            this.hienthi_toanha(this.muccuoctb_id),
            this.hienthi_loainha(this.muccuoctb_id),
          ]);
        }
      } else if (response.data.error_code == "BSS-00000204" || response.data.error == "204") {
        this.gridBangGia.list = [];
        this.setActiveActions(1);
        // Right Form
        await Promise.all([
          this.hienthi_loaihinhtb(dichvuvt_id, this.muccuoctb_id),
          this.hienthi_doituong(this.muccuoctb_id),
          this.hienthi_khuvuc(this.muccuoctb_id),
          this.hienthi_trangbi(this.muccuoctb_id),
          this.hienthi_toanha(this.muccuoctb_id),
          this.hienthi_loainha(this.muccuoctb_id),
        ]);
      } else {
        this.$toast.error(
          "Lỗi lấy danh sách mức cước " + (response.data.message_detail ? response.data.message_detail : response.data.message)
        );
        this.gridBangGia.list = [];
        this.setActiveActions(1);
      }
    },
    async hienthi_loaihinhtb(dichvuvt_id, muccuoc_id) {
      try {
        //loại hình gán
        var upper = [];
        this.gridLoaiHinhGan.list = [];
        let responseGan =
          await SubscriberChargePriceAPI.bangtudien_sp_lay_layds_lhtb(
            this.axios,
            muccuoc_id,
            dichvuvt_id,
            1
          );
        if (
          responseGan &&
          responseGan.data &&
          responseGan.data.error_code &&
          responseGan.data.error_code == "BSS-00000000"
        ) {
          if (responseGan.data.data && responseGan.data.data.length > 0) {
            upper = this.UpperCasePropertyList(responseGan.data.data);
            this.gridLoaiHinhGan.list = upper;
            await this.HT_Tocdo_Adsl_Combobox_DsLoaiTB(upper);
          } else this.gridLoaiHinhGan.list = [];
        } else {
          this.gridLoaiHinhGan.list = [];
        }
        // loại hình chưa gán
        this.gridLoaiHinhChuaGan.list = [];
        let responseChuaGan =
          await SubscriberChargePriceAPI.bangtudien_sp_lay_layds_lhtb(
            this.axios,
            muccuoc_id,
            dichvuvt_id,
            0
          );
        if (
          responseChuaGan &&
          responseChuaGan.data &&
          responseChuaGan.data.error_code &&
          responseChuaGan.data.error_code == "BSS-00000000"
        ) {
          this.gridLoaiHinhChuaGan.list = responseChuaGan.data.data
            ? this.UpperCasePropertyList(responseChuaGan.data.data)
            : [];
        } else {
          this.gridLoaiHinhChuaGan.list = [];
        }
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Đã xảy ra lỗi khi lấy danh sách loại hình");
        }
      }
    },
    async hienthi_doituong(muccuoc_id) {
      // đối tượng gán
      try {
        this.gridDoiTuongGan.list = [];
        this.gridDoiTuongChuaGan.list = [];
        let responseDoiTuong =
          await SubscriberChargePriceAPI.fn_hienthi_doituongtb(
            this.axios,
            JSON.stringify({
              muccuoc_id: muccuoc_id,
            })
          );
        if (
          responseDoiTuong &&
          responseDoiTuong.data &&
          responseDoiTuong.data.error_code &&
          responseDoiTuong.data.error_code == "BSS-00000000"
        ) {
          var data = JSON.parse(responseDoiTuong.data.data.p_js_returnds);
          if (data.ERROR_CODE.toString() == "1") {
            this.gridDoiTuongGan.list = data.DS_DOITUONG_DAGAN;
            this.gridDoiTuongChuaGan.list = data.DS_DOITUONG_CHUAGAN;
          } else {
            this.$toast.error(data.MESSAGE);
            this.gridDoiTuongGan.list = [];
            this.gridDoiTuongChuaGan.list = [];
          }
        } else {
          this.gridDoiTuongGan.list = [];
          this.gridDoiTuongChuaGan.list = [];
        }
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Đã xảy ra lỗi khi lấy danh sách đối tượng " + e);
        }
      }
    },
    async hienthi_khuvuc(muccuoc_id) {
      try {
        //khu vực gán
        this.gridKhuVucGan.list = [];
        let responseGan =
          await SubscriberChargePriceAPI.bangtudien_sp_layds_khuvuc(
            this.axios,
            muccuoc_id,
            1
          );
        if (
          responseGan &&
          responseGan.data &&
          responseGan.data.error_code &&
          responseGan.data.error_code == "BSS-00000000"
        ) {
          this.gridKhuVucGan.list =
            responseGan.data.data && responseGan.data.data.length > 0
              ? this.UpperCasePropertyList(responseGan.data.data)
              : [];
        } else if (responseGan.data.error_code == "BSS-00000204" || responseGan.data.error == "204") {
          this.gridKhuVucGan.list = [];
        } else {
          this.gridKhuVucGan.list = [];
          this.$toast.error(
            "Lỗi lấy danh sách khu vực đã gán " +
              (responseGan.data.message_detail ? responseGan.data.message_detail : responseGan.data.message)
          );
        }
        //khu vực chưa gán
        this.gridKhuVucChuaGan.list = [];
        let responseChuaGan =
          await SubscriberChargePriceAPI.bangtudien_sp_layds_khuvuc(
            this.axios,
            muccuoc_id,
            0
          );
        if (
          responseChuaGan &&
          responseChuaGan.data &&
          responseChuaGan.data.error_code &&
          responseChuaGan.data.error_code == "BSS-00000000"
        ) {
          this.gridKhuVucChuaGan.list =
            responseChuaGan.data.data && responseChuaGan.data.data.length > 0
              ? this.UpperCasePropertyList(responseChuaGan.data.data)
              : [];
        } else if (responseChuaGan.data.error_code == "BSS-00000204" || responseChuaGan.data.error == "204") {
          this.gridKhuVucChuaGan.list = [];
        } else {
          this.$toast.error(
            "Lỗi lấy danh sách khu vực chưa gán " +
              (responseChuaGan.data.message_detail ? responseChuaGan.data.message_detail : responseChuaGan.data.message)
          );
          this.gridKhuVucChuaGan.list = [];
        }
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Đã xảy ra lỗi khi lấy danh sách khu vực " + e);
        }
        // console.log(e);
      }
    },
    async hienthi_trangbi(muccuoc_id) {
      try {
        //trang bị gán
        this.gridTrangBiGan.list = [];
        let responseGan = await SubscriberChargePriceAPI.sp_layds_trangbi(
          this.axios,
          muccuoc_id,
          1
        );
        if (
          responseGan &&
          responseGan.data &&
          responseGan.data.error_code &&
          responseGan.data.error_code == "BSS-00000000" &&
          responseGan.data.data
        ) {
          this.gridTrangBiGan.list = this.UpperCasePropertyList(
            responseGan.data.data
          );
        } else {
          this.gridTrangBiGan.list = [];
        }
        //trang bị chưa gán
        this.gridTrangBiChuaGan.list = [];
        let responseChuaGan = await SubscriberChargePriceAPI.sp_layds_trangbi(
          this.axios,
          muccuoc_id,
          0
        );
        if (
          responseChuaGan &&
          responseChuaGan.data &&
          responseChuaGan.data.error_code &&
          responseChuaGan.data.error_code == "BSS-00000000" &&
          responseChuaGan.data.data
        ) {
          this.gridTrangBiChuaGan.list = this.UpperCasePropertyList(
            responseChuaGan.data.data
          );
        } else {
          this.gridTrangBiChuaGan.list = [];
        }
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Đã xảy ra lỗi khi lấy danh sách trang bị " + e);
        }
      }
    },
    async hienthi_toanha(muccuoc_id) {
      try {
        //tòa nhà gán
        this.gridToaNhaGan.list = [];
        let responseGan = await SubscriberChargePriceAPI.sp_lay_ds_toanha_theo_muccuoc(
          this.axios,
          muccuoc_id,
          1
        );
        if (
          responseGan &&
          responseGan.data &&
          responseGan.data.error_code &&
          responseGan.data.error_code == "BSS-00000000" &&
          responseGan.data.data
        ) {
          this.gridToaNhaGan.list = this.UpperCasePropertyList(
            responseGan.data.data
          );
        } else {
          this.gridToaNhaGan.list = [];
        }
        //tòa nhà chưa gán
        this.gridToaNhaChuaGan.list = [];
        let responseChuaGan = await SubscriberChargePriceAPI.sp_lay_ds_toanha_theo_muccuoc(
          this.axios,
          muccuoc_id,
          0
        );
        if (
          responseChuaGan &&
          responseChuaGan.data &&
          responseChuaGan.data.error_code &&
          responseChuaGan.data.error_code == "BSS-00000000" &&
          responseChuaGan.data.data
        ) {
          this.gridToaNhaChuaGan.list = this.UpperCasePropertyList(
            responseChuaGan.data.data
          );
        } else {
          this.gridToaNhaChuaGan.list = [];
        }
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Đã xảy ra lỗi khi lấy danh sách tòa nhà " + e);
        }
      }
    },
    async hienthi_loainha(muccuoc_id) {
      try {
        //Loại nhà đã gán
        this.gridLoaiNhaGan.list = [];
        let responseGan = await SubscriberChargePriceAPI.sp_lay_ds_loainha_muccuoc_tb(
          this.axios,
          this.tt_nd.phanvung_id,
          muccuoc_id,
          1
        );
        if (
          responseGan &&
          responseGan.data &&
          responseGan.data.error_code &&
          responseGan.data.error_code == "BSS-00000000" &&
          responseGan.data.data
        ) {
          this.gridLoaiNhaGan.list = this.UpperCasePropertyList(
            responseGan.data.data
          );
        } else {
          this.gridLoaiNhaGan.list = [];
        }
        //Loại nhà chưa gán
        this.gridLoaiNhaChuaGan.list = [];
        let responseChuaGan = await SubscriberChargePriceAPI.sp_lay_ds_loainha_muccuoc_tb(
          this.axios,
          this.tt_nd.phanvung_id,
          muccuoc_id,
          0
        );
        if (
          responseChuaGan &&
          responseChuaGan.data &&
          responseChuaGan.data.error_code &&
          responseChuaGan.data.error_code == "BSS-00000000" &&
          responseChuaGan.data.data
        ) {
          this.gridLoaiNhaChuaGan.list = this.UpperCasePropertyList(
            responseChuaGan.data.data
          );
        } else {
          this.gridLoaiNhaChuaGan.list = [];
        }
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message);
        } else {
          this.$toast.error("Đã xảy ra lỗi khi lấy danh sách loại nhà " + e);
        }
      }
    },
    async HT_LoaiHinh_TB_Combobox(dichvuvt_id) {
      try {
        //lấy danh sách loai hình tk
        let responseLoaiHinhTK =
          await SubscriberChargePriceAPI.lay_danhsach_loaihinhtb(this.axios);
        if (
          responseLoaiHinhTK &&
          responseLoaiHinhTK.data &&
          responseLoaiHinhTK.data.error_code &&
          responseLoaiHinhTK.data.error_code == "BSS-00000000"
        ) {
          this.tkloaihinhtb_all = responseLoaiHinhTK.data.data;
          this.cboTKLoaiHinhTB.DataSource = responseLoaiHinhTK.data.data;
          if (responseLoaiHinhTK.data.data.length > 0) {
            this.cboTKLoaiHinhTB.DataSource =
              responseLoaiHinhTK.data.data.filter(
                (e) => e.DICHVUVT_ID == dichvuvt_id
              );
            this.cboTKLoaiHinhTB.value =
              this.cboTKLoaiHinhTB.DataSource[0].LOAITB_ID;
          }
        } else if (responseLoaiHinhTK.data.error_code == "BSS-00000204" || responseLoaiHinhTK.data.error == "204") {
          this.cboTKLoaiHinhTB.DataSource = [];
        } else {
          this.$toast.error("Có lỗi combobox loại hình TB: " + (responseLoaiHinhTK.data.message_detail ? responseLoaiHinhTK.data.message_detail : responseLoaiHinhTK.data.message));
          this.cboTKLoaiHinhTB.DataSource = [];
        }
      } catch (e) {}
    },
    async HT_Tocdo_Adsl_Combobox_ByLoaiTB_moiV2() {
      try {
        //lấy danh toc do tk
        let responseTocDoTK =
          await SubscriberChargePriceAPI.sp_todotk_loaihinhtk(
            this.axios,
            this.cboTKLoaiHinhTB.value
          );
        if (
          responseTocDoTK &&
          responseTocDoTK.data &&
          responseTocDoTK.data.error_code &&
          responseTocDoTK.data.error_code == "BSS-00000000"
        ) {
          var data = responseTocDoTK.data.data;
          if (data.length > 0) {
            this.cboTKTocDo.DataSource = this.UpperCasePropertyList(data);
            this.cboTKTocDo.value = this.cboTKTocDo.DataSource[0].TOCDO_ID;
          }
        } else if (responseTocDoTK.data.error_code == "BSS-00000204" || responseTocDoTK.data.error == '204') {
          this.cboTKTocDo.DataSource = [];
        } else {
          this.$toast.error("Có lỗi: " + (responseTocDoTK.data.message_detail ? responseTocDoTK.data.message_detail : responseTocDoTK.data.message));
          this.cboTKTocDo.DataSource = [];
        }
      } catch (e) {
        console.log(e)
        this.cboTKTocDo.DataSource = [];
      }
    },
    async HT_Tocdo_Adsl_Combobox_DsLoaiTB(gridLoaiHinhGan) {
      this.loading(true);
      try {
        var ds_loaitb = "";
        if (gridLoaiHinhGan.length <= 0) {
          this.cboTocDo.DataSource = [];
          return;
        }
        for (var i = 0; i < gridLoaiHinhGan.length; i++) {
          ds_loaitb =
            ds_loaitb + gridLoaiHinhGan[i]["LOAITB_ID"].toString() + ",";
        }
        ds_loaitb = ds_loaitb.substring(0, ds_loaitb.length - 1);
        var input = {
          phanvung_id: this.tt_nd.phanvung_id,
          ds_loaitb: ds_loaitb,           
        }
        // DataTable _dt = bangts.HT_Tocdo_Adsl(ds_loaitb);
        // cboTocDo.Properties.DataSource = _dt;
        //lấy danh toc do tk
        let responseTocDo = await SubscriberChargePriceAPI.sp_ht_tocdo_adsl_v2(
          this.axios,
          input
        );
        if (
          responseTocDo &&
          responseTocDo.data &&
          responseTocDo.data.error_code &&
          responseTocDo.data.error_code == "BSS-00000000"
        ) {
          var data = responseTocDo.data.data;
          if (data.length > 0) {
            this.cboTocDo.DataSource = this.UpperCasePropertyList(data);
          } else {
            this.cboTocDo.DataSource = [];
          }
        } else if (responseTocDo.data.error_code == "BSS-00000204" || responseTocDo.data.error == '204') {
          this.cboTocDo.DataSource = [];
        } else {
          this.$toast.error("Có lỗi: " + (responseTocDo.data.message_detail ? responseTocDo.data.message_detail : responseTocDo.data.message));
          this.cboTocDo.DataSource = [];
        }
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    async onChange_DichVuVT(value) {
      this.loading(true);
      try {
        if (value) {
          await this.HienThiDanhSach();
        }
        //nhapt 15/11/2016
        var filter = this._dsDichvu
          .split(",")
          .filter((s) => s == this.dichvuvt_id.toString());
        if (!filter || filter.length <= 0) {
          this.cmbMucCuoc.Enabled = true;
          this.cboTocDo.Enabled = true;          
          this.chkTKLoaiHinhTB.Enabled = true;
          this.chkTKLoaiHinhTB.Checked = false;
          this.cboTKLoaiHinhTB.Enabled = false;
          this.chkTKTocDo.Enabled = true;
          this.chkTKTocDo.Checked = false;
          this.cboTKTocDo.Enabled = false;
          await this.HT_LoaiHinh_TB_Combobox(this.dichvuvt_id);
        } else {
          this.cmbMucCuoc.Enabled = false;
          this.cboTocDo.Enabled = false;
          this.txtCuocKhoan.ReadOnly = true;
          this.txtCuocLuuLuong.ReadOnly = true;          
          this.txtCuocTronGoi.ReadOnly = true;
          this.cmbMucCuoc.value = MUCCUOC.LUU_LUONG;
          // this.txtCuocKhoan.value = "";
          // this.txtCuocLuuLuong.value = "";
          // this.txtCuocTronGoi.value = "";

          this.chkTKLoaiHinhTB.Enabled = false;
          this.chkTKLoaiHinhTB.Checked = false;
          this.cboTKLoaiHinhTB.Enabled = false;

          this.chkTKTocDo.Enabled = false;
          this.chkTKTocDo.Checked = false;
          this.cboTKTocDo.Enabled = false;
        }
      } catch (e) {
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    onChange_MucCuoc: function (value) {
      if (Number(value) == MUCCUOC.LUU_LUONG) {
        this.txtCuocKhoan.ReadOnly = false;
        this.txtCuocLuuLuong.ReadOnly = false;
        // this.txtCuocTronGoi.value = "";
        this.txtCuocTronGoi.ReadOnly = true;
      } else {
        // this.txtCuocKhoan.value = "";
        this.txtCuocKhoan.ReadOnly = true;
        this.txtCuocLuuLuong.ReadOnly = true;
        // this.txtCuocLuuLuong.value = "";
        this.txtCuocTronGoi.ReadOnly = false;        
      }
    },
    onChange_TocDo(value) {
      if (value) this.cboTocDo.value = Number(value);
    },
    async chkTKLoaiHinhTB_CheckedChanged(value) {
      // this.loading(true);
      try {
        this.cboTKLoaiHinhTB.Enabled = value;
        this.chkTKTocDo.Enabled = value;
        this.chkTKTocDo.Checked = false;
        await this.HienThiDanhSach();
      } catch (e) {
        // this.loading(false);
      } finally {
        // this.loading(false);
      }
    },
    async cboTKLoaiHinhTB_EditValueChanged(value) {
      this.loading(true);
      try {
        if (!value) return;
        if (value) {
          //Chỉ tìm kiếm theo các tốc độ được đồng bộ
          await this.HT_Tocdo_Adsl_Combobox_ByLoaiTB_moiV2();
          if (this.chkTKLoaiHinhTB.Checked) await this.HienThiDanhSach();
        }
      } catch (e) {
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    async chkTKTocDo_CheckedChanged(value) {
      // this.loading(true);
      try {
        this.cboTKTocDo.Enabled = value;
        await this.HienThiDanhSach();
      } catch (e) {
        // this.loading(false);
      } finally {
        // this.loading(false);
      }
    },
    async cboTKTocDo_SelectedValueChanged(value) {
      this.loading(true);
      try {
        if (!this.cboTKTocDo.value || this.cboTKTocDo.value == null) return;
        if (value) {
          if (this.chkTKTocDo.Checked) await this.HienThiDanhSach();
        }
      } catch (e) {
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    onChangeNgayBD(value_) {
      if (!moment(value_, "DD/MM/YYYY HH:mm:ss").isValid()) {
        this.dtpNgayBD.value = "01/01/2000 00:00:00";
      } else {
        this.dtpNgayBD.value = value_;
      }
    },
    onChangeNgayKT(value_) {
      if (!moment(value_, "DD/MM/YYYY HH:mm:ss").isValid()) {
        this.dtpNgayKT.value = "01/01/2000 00:00:00";
      } else {
        this.dtpNgayKT.value = value_;
      }
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
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
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
  },
  async mounted() {
    this.loading(true);
    try {
      this.setActiveActions(-1);
      // Tag
      var Tag = "";
      if (this.$route.query.tag && this.$route.query.tag.trim() != "") Tag = this.$route.query.tag;
      if (this.itag && this.itag.trim() != "") Tag = this.itag;

      if (Tag && Tag.trim() != "") {
        var words = Tag.split("+");
        if (words && words.length >= 1) {
          var _dsDichvu = words[0];
          _dsDichvu = _dsDichvu.replace(";", ",");
          this._dsDichvu = _dsDichvu;
        }
      } else {
        this._dsDichvu = "";
      }

      await Promise.all([this.frmMucCuocTB_Load()]);
    } catch (e) {
      this.loading(false);
    } finally {
      this.loading(false);
    }
  },
  computed: {
    cp_txtCuoc: {
      get() {
        var txtCuoc = this.txtCuoc.value || this.txtCuoc.value == 0
          ? this.numberWithCommas(this.txtCuoc.value.toString())
          : "";
        return txtCuoc;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuoc.value = Number(replaced);
      },
    },
    cp_txtCuocKhoan: {
      get() {
        var txtCuocKhoan = this.txtCuocKhoan.value || this.txtCuocKhoan.value == 0
          ? this.numberWithCommas(this.txtCuocKhoan.value.toString())
          : "";
        return txtCuocKhoan;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocKhoan.value = Number(replaced);
      },
    },
    cp_txtCuocLuuLuong: {
      get() {
        var txtCuocLuuLuong = this.txtCuocLuuLuong.value || this.txtCuocLuuLuong.value == 0
          ? this.numberWithCommas(this.txtCuocLuuLuong.value.toString())
          : "";
        return txtCuocLuuLuong;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocLuuLuong.value = Number(replaced);
      },
    },
    cp_txtCuocTronGoi: {
      get() {
        var txtCuocTronGoi = this.txtCuocTronGoi.value || this.txtCuocTronGoi.value == 0
          ? this.numberWithCommas(this.txtCuocTronGoi.value.toString())
          : "";
        return txtCuocTronGoi;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocTronGoi.value = Number(replaced);
      },
    },
    cp_txtCuocTBPhu: {
      get() {
        var txtCuocTBPhu = this.txtCuocTBPhu.value || this.txtCuocTBPhu.value == 0
          ? this.numberWithCommas(this.txtCuocTBPhu.value.toString())
          : "";
        return txtCuocTBPhu;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocTBPhu.value = Number(replaced);
      },
    },
  },
  watch: {
    dichvuvt_id(val) {
      this.onChange_DichVuVT(val);
    },
    "cmbMucCuoc.value"(val) {
      this.onChange_MucCuoc(val);
    },
    "chkTKLoaiHinhTB.Checked"(val) {
      this.chkTKLoaiHinhTB_CheckedChanged(val);
    },
    "cboTKLoaiHinhTB.value"(val) {
      // if (this.chkTKLoaiHinhTB.Checked)
        this.cboTKLoaiHinhTB_EditValueChanged(val);
    },
    "chkTKTocDo.Checked"(val) {
      this.chkTKTocDo_CheckedChanged(val);
    },
    "cboTKTocDo.value"(val) {
      if (this.chkTKTocDo.Checked) this.cboTKTocDo_SelectedValueChanged(val);
    },
  },
};
</script>
<style scoped>
.gutter {
  width: 15px;
  padding: 0px 5px;
  display: block;
  cursor: ew-resize;
  z-index: 9;
  background: none !important;
  background-image: none !important;
  background-size: 8px auto;
  background-position: center;
  background-repeat: no-repeat;
}
.gutter-vertical {
  background: none !important;
  background-image: none !important;
  padding: 5px 0px;
  height: 8px;
  background-size: auto 8px;
  display: block;
  width: 100%;
}
.mx-datepicker {
  width: 100%;
}
</style>