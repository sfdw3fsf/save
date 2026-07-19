<template>
    <div>
        <div class="breadcrumb-top">
            <div class="main-title">{{title}}</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">{{title}}</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="btnLayTTMoi_Click">
                        <span class="icon nc-icon-glyph design_bullet-list-67"></span> Lấy DS
                    </a>
                </li>
                <li :class="{'-disabled' : !tsbtnNhapMoi.enabled}">
                    <a @click="tsbtnNhapMoi_Click">
                        <span class="icon  ui-1_circle-add nc-icon-glyph"></span> {{tsbtnNhapMoi.text}}
                    </a>
                </li>
                <li :class="{'-disabled' : !tsbtnGhiLai.enabled}">
                    <a @click="tsbtnGhiLai_Click">
                        <span class="icon one-save"></span> {{tsbtnGhiLai.text}}
                    </a>
                </li>
                <li :class="{'-disabled' : !tsbtnHuyBo.enabled}">
                    <a @click="tsbtnHuyBo_Click">
                        <span class="icon   nc-icon-glyph ui-1_circle-remove"></span> {{tsbtnHuyBo.text}}
                    </a>
                </li>
                <li :class="{'-disabled' : !tsbtnXoa.enabled}">
                    <a @click="tsbtnXoa_Click">
                        <span class="icon  one-trash"></span> {{tsbtnXoa.text}}
                    </a>
                </li>
                <!-- <li v-if="tsbtnNhapExcel.visible">
                    <a @click="readFile">
                        <span class="icon  one-clipart-1"></span> {{tsbtnNhapExcel.text}}
                    </a>
                </li> -->
                <li v-if="tsbtnDocFileXoaTBDB.visible">
                    <a>
                        <span class="icon  one-clipart-1"></span> {{tsbtnDocFileXoaTBDB.text}}
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="box-form" style="padding: 12px 10px 3px 0;">
                <div class="pull-right">
                    <div class="inline">
                        <div class="info-row marb0">
                            <vue-select
                                v-model="selectedTrangThai"
                                :labelWidth="'80'"
                                :options="dsTrangThai"
                                :valueField="'id'"
                                :labelField="'trangThai'"
                                label="Trạng thái"
                                :disable="false"
                                :checkbox="false"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="inline marl10">
                        <div class="info-row marb0">
                            <vue-select
                                v-model="selectedKyHoaDon"
                                :labelWidth="'80'"
                                :options="dsKyHoaDon"
                                :valueField="'key'"
                                :labelField="'kyHoaDon'"
                                label="Kỳ hóa đơn"
                                :disable="true"
                                :checkbox="false"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="inline marl10">
                        <div class="info-row marb0">
                            <vue-select
                                v-model="selectedChuKy"
                                :labelWidth="'50'"
                                :options="dsChuKy"
                                :valueField="'key'"
                                :labelField="'chuky'"
                                label="Chu kỳ"
                                :disable="true"
                                :checkbox="false"
                            ></vue-select>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="row">
                <div class="col-sm-2 col-12">
                    <!-- <div class="list-tp" style="height: 600px;">
                        <div v-for="tinh in dsTinh"
                            :key="tinh.ID"
                            :class="{ 'item' : !tinh.isSelected, 'item active' :  tinh.isSelected}"
                            @click="onSelectedTinh(tinh)">
                            <a href="#">{{tinh.NAME}}</a>
                        </div>
                    </div> -->
                       <div class="box-col">
                         <div class="table-content grid-ext grid-ext-freeze" style="height: 600px;">
                          <ejs-grid ref="gridDsTinh"
                          :dataSource="dsTinh"
                          :allowGrouping="true"
                          :groupSettings="dsTinhGroupOptions.columns"
                          :allowSorting='false'
                          :allowFiltering='true'
                          :allowPaging="false"
                          :pageSettings='pageSettings'
                          :selectionSettings='dsTinhSelectionOptions'
                          :rowSelected='rowSelectedDsTinh'
                          :rowDeselected='rowDeselectedDsTinh'>
                            <e-columns>
                                <e-column type='checkbox' width='50'></e-column>
                                <e-column field='NAME' isPrimaryKey='true' headerText='Tỉnh'></e-column>
                                <e-column field='GROUP' headerText='Vùng'></e-column>
                            </e-columns>
                          </ejs-grid>
                         </div>
                       </div>
                    <div class="title-bg-main text-main">Đang chọn: {{selectedDsTinh.length}}/{{dsTinh.length}} tỉnh </div>
                </div>
                <div class="col-sm-10 col-12">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="legend-title">Thông tin cập nhật</div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <vue-select
                                                v-model="selectedLoaiThongBaoSuCo"
                                                :labelWidth="'120'"
                                                :options="dsLoaiThongBaoSuCo"
                                                :valueField="'ID'"
                                                :labelField="'NAME'"
                                                label="Loại DS"
                                                :checkbox="false"
                                            ></vue-select>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <vue-select
                                                v-model="selectedMucdoXly"
                                                :labelWidth="'120'"
                                                :options="dsMucDoXly"
                                                :valueField="'mucdo_id'"
                                                :labelField="'mucdo_sc'"
                                                label="Mức độ"
                                                :checkbox="false"
                                            ></vue-select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <vue-select
                                                v-model="selectedDonViDuocThongBao"
                                                :labelWidth="'120'"
                                                :options="dsDonViDuocThongBao"
                                                :valueField="'ID'"
                                                :labelField="'NAME'"
                                                label="Đ.vị thông báo"
                                                :checkbox="false"
                                            ></vue-select>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">Đv thông báo</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                    <select name="" id="" class="form-control">
                                                        <option value="">VNPT-NET</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <vue-date
                                            ref="dtpNgayTH"
                                            label="Ngày TH"
                                            v-model="dtpNgayTH"
                                            :labelWidth="'120'"
                                            :required="false"
                                            :checkbox="true"
                                            @input="onDtpNgayTHChange"
                                            ></vue-date>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <vue-date
                                            ref="dtpDenNgayTH"
                                            label="Đến ngày"
                                            v-model="dtpDenNgayTH"
                                            format="DD/MM/YYYY HH:mm:ss"
                                            :labelWidth="'120'"
                                            :required="false"
                                            :checkbox="true"
                                            @input="onDtpDenNgayTHChange"
                                            ></vue-date>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                  <div class="col-sm-12 col-12">
                                      <div class="info-row">
                                          <div class="key w120">Lý do</div>
                                          <div class="value">
                                            <textarea class="form-control" v-model="txtLyDo" style="height: 120px;resize: none;"></textarea>
                                          </div>
                                      </div>
                                  </div>
                                </div>
                                <div class="row">
                                  <div class="col-sm-12 col-12">
                                      <div class="info-row">
                                          <div class="key w120">Nội dung</div>
                                          <div class="value">
                                              <textarea class="form-control" style="height: 120px;resize: none;"
                                                  v-model="txtGhiChu"></textarea>
                                          </div>
                                      </div>
                                  </div>
                                </div>
                                <div class="legend-title">Thông tin dùng cho hệ thống MyTV</div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <vue-select
                                                v-model="selectedLoaiKH"
                                                :labelWidth="'120'"
                                                :options="dsLoaiKH"
                                                :valueField="'ma_loai'"
                                                :labelField="'ten_loai'"
                                                label="Loại khách hàng"
                                                :checkbox="false"
                                            ></vue-select>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                      <div class="info-row" style="padding-top:3px">
                                          <div class="key">
                                              <div class="check-action">
                                                  <input type="checkbox" class="check"
                                                      v-model="isCheckedAnhHuongToiKH">
                                                  <span class="name">Ảnh hưởng tới khách hàng</span>
                                              </div>
                                          </div>
                                      </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <vue-select
                                                v-model="selectedHTDV"
                                                :labelWidth="'120'"
                                                :options="dsHTDV"
                                                :valueField="'loaitbi_id'"
                                                :labelField="'loai_tbi'"
                                                label="Hệ thống/Dịch vụ"
                                                :checkbox="false"
                                            ></vue-select>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <vue-select
                                                v-model="selectedNenTang"
                                                :labelWidth="'120'"
                                                :options="dsNenTang"
                                                :valueField="'ma_nen_tang'"
                                                :labelField="'nen_tang'"
                                                label="Nền tảng"
                                                :checkbox="false"
                                            ></vue-select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12 col-12">
                                        <div class="info-row">
                                            <vue-select
                                                v-model="selectedTbiDauCuoi"
                                                :labelWidth="'120'"
                                                :options="dsTbiDauCuoi"
                                                :valueField="'thietbidc_id'"
                                                :labelField="'ten_tbi'"
                                                label="Thiết bị đầu cuối"
                                                :checkbox="false"
                                            ></vue-select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                              <div class="legend-title">
                                  <div class="title ">Danh sách phần tử hệ thống</div>
                              </div>
                              <DataGrid
                                  :columns="dsPTHT.columns"
                                  :dataSource="dsPTHT.dataSource"
                                  :enable-paging-server="false"
                                  :allowPaging="true"
                                  :loading="dsPTHT.isLoading"
                                  :showFilter="true"
                                  @selectedRowChanged="onDsPTHTSelectedRowChanged"
                                  @pageChanged="onChangePage"
                              ></DataGrid>
                            </div>
                        </div>
                        <div class="legend-title">
                            <div class="title ">Thông tin tra cứu</div>
                        </div>
                        <DataGrid
                            :columns="columns"
                            :dataSource="dsThueBao"
                            :enable-paging-server="false"
                            :allowPaging="true"
                            :loading="isLoading"
                            :showFilter="true"
                            :showColumnCheckbox="true"
                            :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                            @selectedRowChanged="grid_selectedRowChanged"
                            @pageChanged="onChangePage"
                        ></DataGrid>
                    </div>
                </div>
            </div>
        </div>
      <DocFileThem ref="popupDocFileThem"
        :input="inputDocFileThem"
        :acceptChange="readFileAcceptChange"></DocFileThem>
    </div>
</template>

<script>
import api from './api/index'
import axios from 'axios'
import moment from 'moment'
import { GridComponent, ColumnsDirective, ColumnDirective, Group, Sort, Page, Filter } from '@syncfusion/ej2-vue-grids'

const DS_KHONG_XULY = {
  THUEBAO_KHONG_KHOA: 1,
  THUEBAO_KHONG_NHACNO: 2,
  THUEBAO_KHONG_HUY: 3,
  THUEBAO_KHONG_NHANHONG: 5,
  THUEBAO_KHONG_OB_LM: 6,
  THUEBAO_KHONG_OB_BH: 7,
  THONGBAO_SUCO_TTVT: 12
}

export default {
  components: {
    'ejs-grid': GridComponent,
    'e-columns': ColumnsDirective,
    'e-column': ColumnDirective,
    'DocFileThem': () => import('../../../QLTN/components/PopupDocFile/PopupDocFileDSKhoaMo.vue')
  },
  provide: {
    grid: [Group, Sort, Page, Filter]
  },
  data () {
    return {
      tag: '',
      title: '',
      selectedKyHoaDon: 0,
      dsKyHoaDon: [],
      selectedChuKy: 0,
      dsChuKy: [],
      selectedLoaiThongBaoSuCo: 0,
      dsLoaiThongBaoSuCo: [],
      selectedMucdoXly: 0,
      dsMucDoXly: [],
      dsloaiid: '',
      donvi_thongbao: 2,
      selectedDonViDuocThongBao: '',
      dsDonViDuocThongBao: [],
      selectedTienDoXuLy: 0,
      dsTienDoXuLy: [],
      selectedTinh: 0,
      selectedDsTinh: [],
      dsTinh: [
        {
          ID: "1",
          NAME: "An Giang",
          CODE: "AGG",
          CATEGORY_NAME: "10.70.52.198",
          GROUP: "Miền Nam"
        },
        {
          ID: "8",
          NAME: "Bình Dương",
          CODE: "BDG",
          CATEGORY_NAME: "10.70.52.145",
          GROUP: "Miền Nam"
        },
        {
          ID: "6",
          NAME: "Bình Định",
          CODE: "BDH",
          CATEGORY_NAME: "10.70.52.102",
          GROUP: "Miền Trung"
        },
        {
          ID: "3",
          NAME: "Bắc Giang",
          CODE: "BGG",
          CATEGORY_NAME: "10.70.52.103",
          GROUP: "Miền Bắc"
        },
        {
          ID: "4",
          NAME: "Bắc Cạn",
          CODE: "BKN",
          CATEGORY_NAME: "10.70.52.101",
          GROUP: "Miền Bắc"
        },
        {
          ID: "11",
          NAME: "Bạc Liêu",
          CODE: "BLU",
          CATEGORY_NAME: "10.70.52.147",
          GROUP: "Miền Nam"
        },
        {
          ID: "5",
          NAME: "Bắc Ninh",
          CODE: "BNH",
          CATEGORY_NAME: "10.70.52.104",
          GROUP: "Miền Bắc"
        },
        {
          ID: "9",
          NAME: "Bình Phước",
          CODE: "BPC",
          CATEGORY_NAME: "10.70.52.199",
          GROUP: "Miền Nam"
        },
        {
          ID: "7",
          NAME: "Bến Tre",
          CODE: "BTE",
          CATEGORY_NAME: "10.70.52.143",
          GROUP: "Miền Nam"
        },
        {
          ID: "10",
          NAME: "Bình Thuận",
          CODE: "BTN",
          CATEGORY_NAME: "10.70.52.105",
          GROUP: "Miền Trung"
        },
        {
          ID: "12",
          NAME: "Cao Bằng",
          CODE: "CBG",
          CATEGORY_NAME: "10.70.52.106",
          GROUP: "Miền Bắc"
        },
        {
          ID: "14",
          NAME: "Cà Mau",
          CODE: "CMU",
          CATEGORY_NAME: "10.70.52.146",
          GROUP: "Miền Nam"
        },
        {
          ID: "13",
          NAME: "Cần Thơ",
          CODE: "CTO",
          CATEGORY_NAME: "10.70.52.149",
          GROUP: "Miền Nam"
        },
        {
          ID: "22",
          NAME: "Điện Biên",
          CODE: "DBN",
          CATEGORY_NAME: "10.70.52.108",
          GROUP: "Miền Bắc"
        },
        {
          ID: "16",
          NAME: "Đắc Lắc",
          CODE: "DLC",
          CATEGORY_NAME: "10.70.52.109",
          GROUP: "Miền Trung"
        },
        {
          ID: "15",
          NAME: "TP. Đà Nẵng",
          CODE: "DNG",
          CATEGORY_NAME: "10.70.52.110",
          GROUP: "Miền Trung"
        },
        {
          ID: "17",
          NAME: "Đồng Nai",
          CODE: "DNI",
          CATEGORY_NAME: "10.70.52.144",
          GROUP: "Miền Nam"
        },
        {
          ID: "64",
          NAME: "Đắc Nông",
          CODE: "DNO",
          CATEGORY_NAME: "10.70.52.107",
          GROUP: "Miền Trung"
        },
        {
          ID: "18",
          NAME: "Đồng Tháp",
          CODE: "DTP",
          CATEGORY_NAME: "10.70.52.111",
          GROUP: "Miền Nam"
        },
        {
          ID: "19",
          NAME: "Gia Lai",
          CODE: "GLI",
          CATEGORY_NAME: "10.70.52.112",
          GROUP: "Miền Trung"
        },
        {
          ID: "65",
          NAME: "Hòa Bình",
          CODE: "HBH",
          CATEGORY_NAME: "10.70.52.113",
          GROUP: "Miền Bắc"
        },
        {
          ID: "28",
          NAME: "TP Hồ Chí Minh",
          CODE: "HCM",
          CATEGORY_NAME: "10.70.52.210",
          GROUP: "Miền Nam"
        },
        {
          ID: "27",
          NAME: "Hải Dương",
          CODE: "HDG",
          CATEGORY_NAME: "10.70.52.114",
          GROUP: "Miền Bắc"
        },
        {
          ID: "20",
          NAME: "Hà Giang",
          CODE: "HGG",
          CATEGORY_NAME: "10.70.52.162",
          GROUP: "Miền Bắc"
        },
        {
          ID: "66",
          NAME: "Hậu Giang",
          CODE: "HGI",
          CATEGORY_NAME: "10.70.52.155",
          GROUP: "Miền Nam"
        },
        {
          ID: "21",
          NAME: "Hà Nội",
          CODE: "HNI",
          CATEGORY_NAME: "10.70.52.115",
          GROUP: "Miền Bắc"
        },
        {
          ID: "25",
          NAME: "Hà Nam",
          CODE: "HNM",
          CATEGORY_NAME: "10.70.52.116",
          GROUP: "Miền Bắc"
        },
        {
          ID: "26",
          NAME: "Hải Phòng",
          CODE: "HPG",
          CATEGORY_NAME: "10.70.52.164",
          GROUP: "Miền Bắc"
        },
        {
          ID: "23",
          NAME: "Hà Tĩnh",
          CODE: "HTH",
          CATEGORY_NAME: "10.70.52.240",
          GROUP: "Miền Bắc"
        },
        {
          ID: "53",
          NAME: "Huế",
          CODE: "HUE",
          CATEGORY_NAME: "10.70.52.117",
          GROUP: "Miền Trung"
        },
        {
          ID: "24",
          NAME: "Hưng Yên",
          CODE: "HYN",
          CATEGORY_NAME: "10.70.52.163",
          GROUP: "Miền Bắc"
        },
        {
          ID: "30",
          NAME: "Kiên Giang",
          CODE: "KGG",
          CATEGORY_NAME: "10.70.52.169",
          GROUP: "Miền Nam"
        },
        {
          ID: "29",
          NAME: "Khánh Hoà",
          CODE: "KHA",
          CATEGORY_NAME: "10.70.52.118",
          GROUP: "Miền Nam"
        },
        {
          ID: "31",
          NAME: "Kon Tum",
          CODE: "KTM",
          CATEGORY_NAME: "10.70.52.119",
          GROUP: "Miền Trung"
        },
        {
          ID: "36",
          NAME: "Long An",
          CODE: "LAN",
          CATEGORY_NAME: "10.70.52.180",
          GROUP: "Miền Nam"
        },
        {
          ID: "34",
          NAME: "Lào Cai",
          CODE: "LCI",
          CATEGORY_NAME: "10.70.52.121",
          GROUP: "Miền Bắc"
        },
        {
          ID: "32",
          NAME: "Lai Châu",
          CODE: "LCU",
          CATEGORY_NAME: "10.70.52.122",
          GROUP: "Miền Bắc"
        },
        {
          ID: "35",
          NAME: "Lâm Đồng",
          CODE: "LDG",
          CATEGORY_NAME: "10.70.52.148",
          GROUP: "Miền Trung"
        },
        {
          ID: "33",
          NAME: "Lạng Sơn",
          CODE: "LSN",
          CATEGORY_NAME: "10.70.52.172",
          GROUP: "Miền Bắc"
        },
        {
          ID: "38",
          NAME: "Nghệ An",
          CODE: "NAN",
          CATEGORY_NAME: "10.70.52.123",
          GROUP: "Miền Bắc"
        },
        {
          ID: "39",
          NAME: "Ninh Bình",
          CODE: "NBH",
          CATEGORY_NAME: "10.70.52.239",
          GROUP: "Miền Bắc"
        },
        {
          ID: "37",
          NAME: "Nam Định",
          CODE: "NDH",
          CATEGORY_NAME: "10.70.52.241",
          GROUP: "Miền Bắc"
        },
        {
          ID: "40",
          NAME: "Ninh Thuận",
          CODE: "NTN",
          CATEGORY_NAME: "10.70.52.124",
          GROUP: "Miền Trung"
        },
        {
          ID: "59",
          NAME: "Phú Thọ",
          CODE: "PTO",
          CATEGORY_NAME: "10.70.52.125",
          GROUP: "Miền Bắc"
        },
        {
          ID: "41",
          NAME: "Phú Yên",
          CODE: "PYN",
          CATEGORY_NAME: "10.70.52.126",
          GROUP: "Miền Trung"
        },
        {
          ID: "42",
          NAME: "Quảng Bình",
          CODE: "QBH",
          CATEGORY_NAME: "10.70.52.186",
          GROUP: "Miền Trung"
        },
        {
          ID: "45",
          NAME: "Quảng Ninh",
          CODE: "QNH",
          CATEGORY_NAME: "10.70.52.187",
          GROUP: "Miền Bắc"
        },
        {
          ID: "44",
          NAME: "Quảng Ngãi",
          CODE: "QNI",
          CATEGORY_NAME: "10.70.52.188",
          GROUP: "Miền Trung"
        },
        {
          ID: "43",
          NAME: "Quảng Nam",
          CODE: "QNM",
          CATEGORY_NAME: "10.70.52.189",
          GROUP: "Miền Trung"
        },
        {
          ID: "46",
          NAME: "Quảng Trị",
          CODE: "QTI",
          CATEGORY_NAME: "10.70.52.190",
          GROUP: "Miền Trung"
        },
        {
          ID: "49",
          NAME: "Sơn La",
          CODE: "SLA",
          CATEGORY_NAME: "10.70.52.173",
          GROUP: "Miền Bắc"
        },
        {
          ID: "47",
          NAME: "Sóc Trăng",
          CODE: "STG",
          CATEGORY_NAME: "10.70.52.150",
          GROUP: "Miền Nam"
        },
        {
          ID: "51",
          NAME: "Thái Bình",
          CODE: "TBH",
          CATEGORY_NAME: "10.70.52.168",
          GROUP: "Miền Bắc"
        },
        {
          ID: "54",
          NAME: "Tiền Giang",
          CODE: "TGG",
          CATEGORY_NAME: "10.70.52.167",
          GROUP: "Miền Nam"
        },
        {
          ID: "52",
          NAME: "Thanh Hoá",
          CODE: "THA",
          CATEGORY_NAME: "10.70.52.191",
          GROUP: "Miền Bắc"
        },
        {
          ID: "50",
          NAME: "Tây Ninh",
          CODE: "TNH",
          CATEGORY_NAME: "10.70.52.170",
          GROUP: "Miền Nam"
        },
        {
          ID: "61",
          NAME: "Thái Nguyên",
          CODE: "TNN",
          CATEGORY_NAME: "10.70.52.192",
          GROUP: "Miền Trung"
        },
        {
          ID: "56",
          NAME: "Tuyên Quang",
          CODE: "TQG",
          CATEGORY_NAME: "10.70.52.193",
          GROUP: "Miền Bắc"
        },
        {
          ID: "55",
          NAME: "Trà Vinh",
          CODE: "TVH",
          CATEGORY_NAME: "10.70.52.165",
          GROUP: "Miền Nam"
        },
        {
          ID: "57",
          NAME: "Vĩnh Long",
          CODE: "VLG",
          CATEGORY_NAME: "10.70.52.237",
          GROUP: "Miền Nam"
        },
        {
          ID: "58",
          NAME: "Vĩnh Phúc",
          CODE: "VPC",
          CATEGORY_NAME: "10.70.52.194",
          GROUP: "Miền Bắc"
        },
        {
          ID: "2",
          NAME: "Bà Rịa Vũng Tàu",
          CODE: "VTU",
          CATEGORY_NAME: "10.70.52.197",
          GROUP: "Miền Nam"
        },
        {
          ID: "60",
          NAME: "Yên Bái",
          CODE: "YBI",
          CATEGORY_NAME: "10.159.21.200",
          GROUP: "Miền Bắc"
        }
      ],
      dsTinhGroupOptions: {
        columns: ['GROUP']
      },
      dsTinhSelectionOptions: { type: 'Multiple' },
      pageSettings: { pageSize: Number.MAX_VALUE },
      dsThueBao: [],
      columns: [{
        headerText: 'Lý do',
        fieldName: 'lydo'
      }, {
        headerText: 'Người CN',
        fieldName: 'nguoi_cn'
      }, {
        headerText: 'Ngày thực hiện',
        fieldName: 'ngay_th'
      }, {
        headerText: 'Đến ngày',
        fieldName: 'ngay_th_den'
      }, {
        headerText: 'Đơn vị thông báo',
        fieldName: 'dvi_thongbao'
      }, {
        headerText: 'Loại thông báo',
        fieldName: 'trangthai'
      }],
      isLoading: true,
      dtpNgayTH: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      dtpDenNgayTH: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      tsbtnNhapExcel: {
        visible: false,
        text: 'Đọc file thêm'
      },
      tsbtnDocFileXoaTBDB: {
        visible: false,
        text: 'Đọc file Xóa'
      },
      txtLyDo: '',
      txtGhiChu: '',
      tsbtnNhapMoi: {
        enabled: true,
        text: 'Nhập mới'
      },
      tsbtnGhiLai: {
        enabled: true,
        text: 'Ghi lại'
      },
      tsbtnXoa: {
        enabled: true,
        text: 'Xóa'
      },
      tsbtnHuyBo: {
        enabled: true,
        text: 'Hủy'
      },
      thao_tac: '',
      thuebao_id: 0,
      thanhtoan_id: 0,
      row_donvi_id: 0,
      nguoi_cn: '',
      inputDocFileThem: {},
      selectedTrangThai: 0,
      dsTrangThai: [{
        id: 0,
        trangThai: 'Chưa thực hiện'
      }, {
        id: 1,
        trangThai: 'Đã thực hiện'
      }],
      dsPTHT: {
        columns: [{
          headerText: 'Mã thiết bị',
          fieldName: 'ma_tbi'
        }, {
          headerText: 'Tên thiết bị',
          fieldName: 'ten_tbi'
        }, {
          headerText: 'Địa chỉ',
          fieldName: 'dia_chi'
        }, {
          headerText: 'Số serial',
          fieldName: 'serial'
        }, {
          headerText: 'Thông tin khác',
          fieldName: 'thongtin_khac'
        }],
        dataSource: [],
        isLoading: true
      },
      dsLoaiKH: [],
      selectedLoaiKH: '',
      dsHTDV: [],
      selectedHTDV: '',
      dsNenTang: [],
      selectedNenTang: '',
      dsTbiDauCuoi: [],
      selectedTbiDauCuoi: '',
      isCheckedAnhHuongToiKH: false,
      ma_TB:'',
    }
  },
  watch: {
    dsKyHoaDon: function (newValue, oldValue) {
      this.selectedKyHoaDon = newValue.length > 0 ? newValue[newValue.length - 1].key : 0
    },
    dsChuKy: function (newValue, oldValue) {
      this.selectedChuKy = newValue.length > 0 ? newValue[0].key : 0
    },
    dsLoaiThongBaoSuCo: function (newValue, oldValue) {
      this.selectedLoaiThongBaoSuCo = newValue.length > 0 ? newValue[0].ID : 0
    },
    dsMucDoXly: function (newValue, oldValue) {
      this.selectedMucdoXly = newValue.length > 0 ? newValue[0].mucdo_id : 0
    },
    dsDonViDuocThongBao: function (newValue, oldValue) {
      this.selectedDonViDuocThongBao = this.donvi_thongbao
    },
    dsTienDoXuLy: function (newValue, oldValue) {
      this.selectedTienDoXuLy = newValue.length > 0 ? parseInt(newValue[0].ID) : 0
    },
    selectedHTDV: async function (newValue, oldValue) {
      this.loading(true)
      try {
        const resDsPTHT = await api.getDsPTHT(axios, {
          loaitbi: newValue
        })
        if (resDsPTHT.data.message_detail) throw resDsPTHT.data.message_detail
        this.dsPTHT.dataSource = resDsPTHT.data.data
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    }
  },
  mounted: async function () {
    this.loading(true)
    try {
      // Xử lý tag
      if (this.$route.query.tag) {
        this.tag = this.$route.query.tag
      } else {
        switch (this.$route.name) {
          case 'NotifTeleCenterIncident':
            this.tag = '12'
            break
          case 'IncidentMessageTTVTNet':
            this.tag = '12;0'
            break
          case 'IncidentMessageTTVTMedia':
            this.tag = '12;1'
            break
          default:
            this.tag = ''
        }
      }

      switch (this.$route.name) {
        case 'NotifTeleCenterIncident':
          this.title = "THÔNG BÁO SỰ CỐ TRUNG TÂM VIỄN THÔNG"
          break
        case 'IncidentMessageTTVTNet':
          this.title = 'THÔNG BÁO SỰ CỐ TRUNG TÂM VIỄN THÔNG - VNPT NET'
          break
        case 'IncidentMessageTTVTMedia':
          this.title = "THÔNG BÁO SỰ CỐ TRUNG TÂM VIỄN THÔNG -VNPT MEDIA"
          break
      }

      if (this.tag.toString() !== "") {
        const arrTags = this.tag.toString().split(';')
        if (arrTags.length >= 1) { this.dsloaiid = arrTags[0].toString() }
        if (arrTags.length >= 2) {
          this.donvi_thongbao = parseInt(arrTags[1])
        }
      } else {
        this.dsloaiid = ''
      }

      // Kỳ hóa đơn
      const resDsKyHoaDon = await api.getDsKyHoaDon(axios)
      if (resDsKyHoaDon.data.message_detail) throw resDsKyHoaDon.data.message_detail
      this.dsKyHoaDon = []
      if (resDsKyHoaDon.data.data && resDsKyHoaDon.data.data.length > 0) {
        for (let i = 0; i < resDsKyHoaDon.data.data.length; i++) {
          this.dsKyHoaDon.push({
            key: i,
            kyHoaDon: moment(resDsKyHoaDon.data.data[i], 'YYYYMM').format('MM/YYYY'),
            originalKyHoaDon: resDsKyHoaDon.data.data[i]
          })
        }
      }

      // Combo chu kỳ nợ
      this.selectedKyHoaDon = this.dsKyHoaDon.length > 0 ? this.dsKyHoaDon[this.dsKyHoaDon.length - 1].key : 0
      const resChuky = await api.getDsChuKy(axios, {
        kyHoaDon: this.dsKyHoaDon.find(khd => khd.key === this.selectedKyHoaDon).originalKyHoaDon
      })
      if (resChuky.data.message_detail) throw resChuky.data.message_detail
      this.dsChuKy = []
      if (resChuky.data.data && resChuky.data.data.length > 0) {
        for (let i = 0; i < resChuky.data.data.length; i++) {
          this.dsChuKy.push({
            key: i,
            chuky: resChuky.data.data[i]
          })
        }
      } else {
        const errorText = 'Không có thông tin về chu kỳ!'
        throw errorText
      }

      // Hiển thị combo Loại DS
      const resDsLoaiThongBaoSuCo = await api.getDsLoaiThongBaoSuCo(axios)
      if (resDsLoaiThongBaoSuCo.data.message_detail) throw resDsLoaiThongBaoSuCo.data.message_detail
      this.dsLoaiThongBaoSuCo = []
      if (resDsLoaiThongBaoSuCo.data.data) {
        if (this.dsloaiid.toString() === DS_KHONG_XULY.THONGBAO_SUCO_TTVT.toString()) {
          this.dsLoaiThongBaoSuCo = resDsLoaiThongBaoSuCo.data.data.filter(i => i.ID.toString() === this.dsloaiid.toString())
          this.tsbtnNhapExcel.visible = true
          this.tsbtnDocFileXoaTBDB.visible = false
        }
      }

      // Danh sách đơn vị
      const resDsDonViDuocThongBao = await api.getDsDonViDuocThongBao(axios)
      if (resDsDonViDuocThongBao.data.message_detail) throw resDsDonViDuocThongBao.data.message_detail
      this.dsDonViDuocThongBao = []
      if (resDsDonViDuocThongBao.data.data) {
        this.dsDonViDuocThongBao = resDsDonViDuocThongBao.data.data
      }

      // Danh sách tỉnh
      // const resDsDVVTTinh = await api.getDsDVVTTinh(axios)
      // if (resDsDVVTTinh.data.message_detail) throw resDsDVVTTinh.data.message_detail
      // this.dsTinh = []
      // if (resDsDVVTTinh.data.data && resDsDVVTTinh.data.data.length > 0) {
      //   this.dsTinh = resDsDVVTTinh.data.data
      // }

      const resDsTienDoXuLy = await api.getDsTienDoXuLy(axios)
      if (resDsTienDoXuLy.data.message_detail) throw resDsTienDoXuLy.data.message_detail
      // this.dsTienDoXuLy = []
      if (resDsTienDoXuLy.data.data) {
        this.dsTienDoXuLy = Array.from(resDsTienDoXuLy.data.data)
      }

      // Thông tin dùng cho hệ thống MyTV
      const resDsPTHT = await api.getDsCboBox(axios)
      if (resDsPTHT.data.message_detail) throw resDsPTHT.data.message_detail
      this.dsLoaiKH = resDsPTHT.data.data.loai_kh
      this.dsHTDV = resDsPTHT.data.data.hethong
      this.dsNenTang = resDsPTHT.data.data.nentang
      this.dsTbiDauCuoi = resDsPTHT.data.data.tbi_daucuoi
      this.dsMucDoXly = resDsPTHT.data.data.mucdo_sc

      // Hiển thị danh sách thuê bao
      this.HT_DS_TB_Khoa_Mo()
    } catch (error) {
      console.log(error)
      this.$toast.error(error.toString())
    }
    this.loading(false)
  },
  methods: {
    HT_DS_TB_Khoa_Mo: async function () {
      let tuNgay = null
      let denNgay = null
      if (this.$refs.dtpNgayTH.checkbox_value && this.dtpNgayTH) {
        tuNgay = moment(this.dtpNgayTH, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
      }

      if (this.$refs.dtpDenNgayTH.checkbox_value && this.dtpDenNgayTH) {
        denNgay = moment(this.dtpDenNgayTH, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
      }

      const resDsThongBaoSuCoTTVT = await api.getDsThongBaoSuCoTTVT(axios, {
        tuNgay,
        denNgay
      })

      if (resDsThongBaoSuCoTTVT.data.message_detail) throw resDsThongBaoSuCoTTVT.data.message_detail

      this.dsThueBao = resDsThongBaoSuCoTTVT.data.data !== null ? resDsThongBaoSuCoTTVT.data.data.map(i => {
        i.ngay_th = moment(i.ngay_th, 'YYYY-MM-DD HH:mm:ss').add(7, 'hours').format('YYYY-MM-DD HH:mm:ss')
        i.ngay_th_den = moment(i.ngay_th_den, 'YYYY-MM-DD HH:mm:ss').add(7, 'hours').format('YYYY-MM-DD HH:mm:ss')
        return i
      }) : []

      this.dsThueBao.sort((b,a) => moment(a.ngay_cn, 'YYYY-MM-DD HH:mm:ss') - moment(b.ngay_cn, 'YYYY-MM-DD HH:mm:ss'))
      //this.dsThueBao.sort((b,a) => moment(a.ngay_cn, 'YYYY-MM-DD HH:mm:ss') - moment(b.ngay_cn, 'YYYY-MM-DD HH:mm:ss'))
      
      //đưa dòng xử lý lên đầu
      // if (this.saved_lydo && this.saved_lydo!=''){
      //   let temp = this.dsThueBao
      //   let index = temp.findIndex(
      //     (item) => item.lydo == this.saved_lydo && item.ghichu == this.saved_ghichu
      //   );
      //   if (index>-1) {
      //     let row = temp[index]
      //     temp.splice(index, 1)
      //     temp.unshift(row)
      //     this.dsThueBao = temp
      //     this.saved_lydo = ''
      //     this.saved_ghichu = ''
      //   }

      // }

    },
    onSelectedTinh: function (e) {
      this.selectedTinh = e
    },
    rowSelectedDsTinh: function (e) {
      this.selectedDsTinh = this.$refs.gridDsTinh.getSelectedRecords()
    },
    rowDeselectedDsTinh: function (e) {
      this.selectedDsTinh = this.$refs.gridDsTinh.getSelectedRecords()
    },
    grid_selectedRowChanged: async function (e) {
      if (e) {
        if (e.ngay_th) {
          this.dtpNgayTH = moment(e.ngay_th, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
          this.$refs.dtpNgayTH.checkbox_value = true
        } else {
          this.$refs.dtpNgayTH.checkbox_value = false
        }

        if (e.ngay_th_den) {
          this.dtpDenNgayTH = moment(e.ngay_th_den, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
          this.$refs.dtpDenNgayTH.checkbox_value = true
        } else {
          this.$refs.dtpDenNgayTH.checkbox_value = false
        }

        this.selectedMucdoXly = e.mucdo_sc_id !== null ? e.mucdo_sc_id : 0
        this.txtLyDo = e.lydo
        this.txtGhiChu = e.ghichu
        this.selectedDonViDuocThongBao = e.thanhtoan_id.toString()
        this.selectedTienDoXuLy = e.loaitb_id

        this.row_donvi_id = e.thanhtoan_id
        this.nguoi_cn = e.nguoi_cn

        this.selectedLoaiKH = e.loai_kh
        this.isCheckedAnhHuongToiKH = e.anhhuong_kh
        this.selectedHTDV = e.loaitbi_id
        this.selectedNenTang = e.nen_tang
        this.selectedTbiDauCuoi = e.thietbidc_id
        this.maTB = e.ma_tb
        this.setButton(3)
      }
    },
    onDsPTHTSelectedRowChanged: function (e) {

    },
    setButton: function (kieu) {
      this.tsbtnNhapMoi.enabled = false
      this.tsbtnGhiLai.enabled = false
      this.tsbtnXoa.enabled = false
      this.tsbtnHuyBo.enabled = false

      // Bat dau
      if (kieu === -1) {
        this.tsbtnGhiLai.enabled = true
        this.tsbtnHuyBo.enabled = true
      }

      // Bat dau
      if (kieu === 0) {
        this.tsbtnNhapMoi.enabled = true
      }

      // Them moi
      if (kieu === 1) {
        this.thao_tac = "I"
        this.tsbtnGhiLai.enabled = true
        this.tsbtnHuyBo.enabled = true
        this.selectedDonViDuocThongBao = this.donvi_thongbao.toString()
        this.clear()
      }

      // Huy
      if (kieu == 2) {
        this.tsbtnNhapMoi.enabled = true
        this.tsbtnXoa.enabled = true
      }

      // Edit
      if (kieu == 3) {
        this.thao_tac = "U"
        this.tsbtnNhapMoi.enabled = true
        this.tsbtnXoa.enabled = true
        this.tsbtnGhiLai.enabled = true
        this.tsbtnHuyBo.enabled = true
      }
    },
    clear: function () {
      this.txtGhiChu = ''
      this.txtLyDo = ''
      this.thuebao_id = 0
      this.thanhtoan_id = 0
      this.dtpNgayTH = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.dtpDenNgayTH = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
    },
    onChangePage: function (e) {},
    onDtpNgayTHChange: function (e) {},
    onDtpDenNgayTHChange: function (e) {},
    tsbtnNhapMoi_Click: function () {
      this.setButton(1)
    },
    btnLayTTMoi_Click: async function () {
      this.loading(true)
      try {
        await this.HT_DS_TB_Khoa_Mo()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    tsbtnHuyBo_Click: function () {
      this.clear()
    },
    tsbtnGhiLai_Click: async function () {
      await this.FN_CapNhatDuLieu()
    },
    FN_CapNhatDuLieu: async function () {
      try {
        let type = 0
        if (this.thao_tac === 'I') {
          const isConfirm = await this.$bvModal
            .msgBoxConfirm(`Bạn chắc chắn muốn thêm dữ liệu?`, {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })

          if (!isConfirm) return
          type = 1
          this.maTB = ''

          if (this.selectedDsTinh.length === 0) {
            this.$toast.error('Bạn chưa chọn tỉnh!')
            return
          }          
        } else if (this.thao_tac === 'U') {
          if (this.nguoi_cn !== this.$auth.getMaNhanVien()) {
            this.$toast.error('Người tạo thông báo mới được phép cập nhập, xóa thông báo')
            return
          }

          const isConfirm = await this.$bvModal
            .msgBoxConfirm(`Bạn chắc chắn muốn cập nhật dữ liệu?`, {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
          if (!isConfirm) return          
        } else if (this.thao_tac === 'D') {          
          if (this.nguoi_cn !== this.$auth.getMaNhanVien()) {
            this.$toast.error('Người tạo thông báo mới được phép cập nhập, xóa thông báo')
            return
          }

          const isConfirm = await this.$bvModal
            .msgBoxConfirm(`Bạn chắc chắn muốn xóa dữ liệu?`, {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
          if (!isConfirm) return
          type = -1
        } else {
          this.$toast.error('Chưa chọn dữ liệu/thao tác')
          return
        }

        this.loading(true)

        const resCapNhat = await api.themMoiThongTin(axios, {
          thanhToanId: this.row_donvi_id,
          loaiTBId: this.selectedTienDoXuLy,
          type: type,
          ngayTH: this.dtpNgayTH, // moment(this.dtpNgayTH, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'),
          ngayTHDen: this.dtpDenNgayTH, // moment(this.dtpDenNgayTH, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'),
          lyDo: this.txtLyDo,
          ghiChu: this.txtGhiChu,
          dsTinh: this.selectedDsTinh.map(i => i.CODE).join(','),
          mucDoId: this.selectedMucdoXly,
          maTb:this.maTB
        })

        if (resCapNhat.data.message_detail) throw resCapNhat.data.message_detail

        let _msg = 'Thêm mới thành công dữ liệu !'
        if (this.thao_tac === 'U') {
          _msg = 'Cập nhật thành công dữ liệu !'
        } else if (this.thao_tac === 'D') {
          _msg = 'Xóa thành công dữ liệu !'
        }

        this.$toast.success(_msg)

        this.dtpNgayTH = null
        this.dtpDenNgayTH = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')

        await this.HT_DS_TB_Khoa_Mo()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }

      this.loading(false)
    },
    tsbtnXoa_Click: async function () {
      this.thao_tac = 'D'
      await this.FN_CapNhatDuLieu()
    },
    readFile: function () {
      return
      this.inputDocFileThem.loaiDSId = this.selectedLoaiThongBaoSuCo
      this.inputDocFileThem.lyDo = this.txtLyDo
      this.inputDocFileThem.kyHoaDon = moment('01/' + this.dsKyHoaDon.find(i => i.key === this.selectedKyHoaDon).kyHoaDon, 'DD/MM/YYYY').format('YYYYMMDD')
      this.inputDocFileThem.tuNgay = this.dtpNgayTH
      this.inputDocFileThem.denNgay = this.dtpDenNgayTH
      this.inputDocFileThem.denNgay_Checked = this.$refs.dtpDenNgayTH.checkbox_value

      if (this.inputDocFileThem.loaiDSId === DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG) {
        if (!this.reason.trim()) {
          this.$toast.error('Hãy nhập lý do')
        }
      }

      if (!this.$refs.dtpDenNgayTH.checkbox_value) {
        this.$toast.error('Hãy chọn thông tin đến ngày!')
      }

      this.$refs.popupDocFileThem.openDialog()
    },
    readFileAcceptChange: function (e) {
      if (e) {

      }
    }
  }
}
</script>

<style scoped>
.-disabled{
    pointer-events: none;
    cursor: default;
}
.-disabled:hover{
    background: unset;
}
.-disabled > a {
    color: gray !important;
}
.-disabled > a > span {
    color: gray;
}

a:hover{
  cursor: pointer;
}
</style>
