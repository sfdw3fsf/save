<template>
    <div class="grid-stack-box" id="QLGiaoNhan">
        <div class="box-col" id="boxLeft">
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="legend-title">Các thông tin về bộ hồ sơ</div>
                    <div class="title-bg-main">Thông tin tìm kiếm</div>
                    <div class="info-row">
                        <div class="key">
                            Kiểu giao
                        </div>
                        <div class="value">
                            <div class="select-custom">
                                <ejs-dropdownlist-tt id="kieugiao"
                                    locale="vi-VN" @change="cboKieuGiao_Changed"
                                    v-model="kieugiao"
                                    :dataSource="lstKieuGiao"
                                    :allowFiltering="true" :filtering="onFilterKieuGiao"
                                    :fields="{ value: 'ID', text: 'NAME' }"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-7 col-12">
                            <div class="info-row">
                                <div class="key">
                                    Số lô HS
                                </div>
                                <div class="value">
                                    <ejs-dropdownlist-tt id="solohs"
                                        locale="vi-VN" @change="cboSoLoHS_Changed"
                                        v-model="solohs"
                                        :dataSource="lstSoLoHS"
                                        :allowFiltering="true" :filtering="onFilterSoLoHS"
                                        :fields="{ value: 'LOHS_ID', text: 'MALO_HS' }"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5 col-12">
                            <div class="info-row">
                                <div class="key  w80">
                                    Mã KH/GD
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control highlight" v-model="ma_kh_gd" id="ma_kh_gd" v-on:keyup.enter="txtMaGD_KeyPress">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-7 col-12">
                            <div class="info-row">
                                <div class="key">
                                    Đơn vị giao
                                </div>
                                <div class="value">
                                    <div class="select-custom">
                                        <ejs-dropdownlist-tt id="donvigiao"
                                            locale="vi-VN" @change="cboDonViGiao_Changed"
                                            v-model="donvigiao"
                                            :dataSource="lstDonViGiao"
                                            :allowFiltering="true" :filtering="onFilterDonViGiao"
                                            :fields="{ value: 'DONVI_GIAO_ID', text: 'DV_GIAO' }"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5 col-12">
                            <div class="info-row">
                                <div class="key w80">
                                    Trạng thái
                                </div>
                                <div class="value">
                                    <div class="select-custom">
                                        <ejs-dropdownlist-tt id="trangthai"
                                            locale="vi-VN" @change="cboTrangThai_Changed"
                                            v-model="trangthai"
                                            :dataSource="lstTrangThai"
                                            :allowFiltering="true" :filtering="onFilterTrangThai"
                                            :fields="{ value: 'ID', text: 'NAME' }"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-7 col-12">
                            <div class="info-row">
                                <div class="key">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="chb_ngaygiaotu">
                                        <span class="name">Giao từ ngày</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <vue-date format="DD/MM/YYYY" type="datetime" v-model="ngaygiaotu" id="ngaygiaotu"></vue-date>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5 col-12">
                            <div class="info-row">
                                <div class="key  w80">
                                    Đến ngày
                                </div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <vue-date format="DD/MM/YYYY" type="datetime" v-model="ngaygiaoden" id="ngaygiaoden"></vue-date>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="title-bg-main">
                        <span class="title">Thông tin bộ hồ sơ</span>
                        <div class="check-action marl10">
                            <input type="checkbox" class="check" v-model="ckbAuto">
                            <span class="name">Tự động chọn cùng lô</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-7 col-12">
                            <div class="info-row">
                                <div class="key">
                                    Số lô HS
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtSoLHS" id="txtSoLHS">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5 col-12">
                            <div class="info-row">
                                <div class="key  w80">
                                    Mã KH
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtMaKH" id="txtMaKH">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-7 col-12">
                            <div class="info-row">
                                <div class="key">
                                    Trạng thái
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtTrangThai" id="txtTrangThai">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5 col-12">
                            <div class="info-row">
                                <div class="key  w80">
                                    Tên KH
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtTenKH" id="txtTenKH">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-7 col-12">
                            <div class="info-row">
                                <div class="key">
                                    Đơn vị giao
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtDonviGiao" id="txtDonviGiao">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5 col-12">
                            <div class="info-row">
                                <div class="key  w80">
                                    Mã GD
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtGD" id="txtGD">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key">
                            Nội dung trả
                        </div>
                        <div class="value">
                            <textarea name="" class="form-control" style="height:62px;resize:none" id="txtNDTRa" cols="30" rows="10" v-model="txtNDTRa"></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="legend-title">Chi tiết bộ hồ sơ</div>
                    <div class="table-content" style="max-height: 350px;">               
                        <DataGrid
                            ref="ds_chitiet_bohoso"
                            v-bind:columns="columnsCTHS"
                            v-bind:dataSource="ds_chitiet_bohoso"
                            :showColumnCheckbox="true"
                            :showFilter="true"
                            :enabledSelectFirstRow="true"
                            :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                            panelDataHeight="230"
                            :allowPaging="true" :enablePagingServer="false"
                            @selectedItemsChanged="gridCTHS_SelectRow"
                        ></DataGrid>   
                    </div>
                </div>
            </div>
            <img id="imgFile" width="20px" height="20px" src='@/assets/uploads/bss1534/file.png' style="display: none;" />
            <img id="imgSuccess" width="20px" height="20px" src='@/assets/uploads/bss793/success.png' style="display: none;" />
            <img id="imgWarning" width="20px" height="20px" src='@/assets/uploads/bss1534/warning.png' style="display: none;" />
            <div class="legend-title mart20">Danh sách bộ hồ sơ</div>
            <div class="row" :style="!tableLayoutPanel2 ? 'display: none' : ''">
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key w70">Tuyến thu</div>
                        <div class="value">
                            <ejs-dropdownlist-tt id="tuyenthu"
                                locale="vi-VN" @change="cboTuyenThu_Changed"
                                v-model="tuyenthu"
                                :dataSource="lstTuyenThu"
                                :allowFiltering="true" :filtering="onFilterTuyenThu"
                                :fields="{ value: 'tuyenthu_id', text: 'tuyenthu' }"
                            />
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key w70">Đơn vị</div>
                        <div class="value">
                            <ejs-dropdownlist-tt id="dvtt"
                                locale="vi-VN" @change="cboDVTT_Changed"
                                v-model="dvtt"
                                :dataSource="lstDVTT"
                                :allowFiltering="true" :filtering="onFilterDVTT"
                                :fields="{ value: 'donvi_id', text: 'ten_dv' }"
                            />
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key w70">Nhân viên</div>
                        <div class="value">
                            <ejs-dropdownlist-tt id="nhanviennhan"
                                locale="vi-VN" @change="cboNhanVienNhan_Changed"
                                v-model="nhanviennhan"
                                :dataSource="lstNhanVienNhan"
                                :allowFiltering="true" :filtering="onFilterNhanVienNhan"
                                :fields="{ value: 'nhanvien_id', text: 'ten_nv' }"
                            />
                        </div>
                    </div>
                </div>
            </div>
            <div class="table-content">
                <DataGrid
                    ref="ds_danhsach_bohoso"
                    v-bind:columns="columnsDSHS"
                    v-bind:dataSource="ds_danhsach_bohoso"
                    :showColumnCheckbox="false"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick', checkboxOnly: false }"
                    panelDataHeight="350"
                    :allowPaging="true" :enablePagingServer="false"
                    @rowSelected="gridviewDanhSach_FocusedRowChanged"
                    @filterChanged="gridviewDanhSach_filterChanged"
                ></DataGrid>
            </div>
        </div>
        <div class="box-col padt5" id="boxRight" :style="themfile ? 'display:none' : ''">
            <div class="legend-title padb2">
                <div class="pull-left">
                    <span class="title inline vcenter">Thông tin file</span>
                    <button class="btn btn-second" @click="btnClear_Click">
                        <span class="one-reload"></span> Làm mới
                    </button>
                    <button class="btn btn-second" @click="btnThemFile_Click">
                        <span class="nc-icon-outline tech-2_sim-card"></span> Lưu file
                    </button>
                </div>
                <div class="pull-right">
                    <a @click="closePanelThemfile" class="red btn f20 lh20">
                        <span class="-ap icon-arrow-right3"></span>
                    </a>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="info-row">
                <div class="key w80">Nhóm file</div>
                <div class="value">
                    <div class="select-custom">
                        <ejs-dropdownlist-tt id="nhomfile"
                            locale="vi-VN" @change="cboNhomFile_Changed"
                            v-model="nhomfile"
                            :dataSource="lstNhomFile"
                            :allowFiltering="true" :filtering="onFilterNhomFile"
                            :fields="{ value: 'ID', text: 'NAME' }"
                        />
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Loại File</div>
                <div class="value">
                    <div class="select-custom">
                        <ejs-dropdownlist-tt id="loaifile"
                            locale="vi-VN" @change="cboLoaiFile_Changed"
                            v-model="loaifile"
                            :dataSource="lstLoaiFile"
                            :allowFiltering="true" :filtering="onFilterLoaiFile"
                            :fields="{ value: 'loaifile_id', text: 'loai_file' }"
                        />
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Đường dẫn</div>
                <div class="value" :key="file_refresh">
                    <div class="input-more-button -right">
                        <label class="btn" for="upload-file">
                            <span class="-ap icon-more_horiz"></span>
                            <input type="file" ref="files" class="hidden" id="upload-file" @change="txtDuongDan_ButtonClick"
                                accept="image/png, image/jpeg, image/jpg, application/msword, application/vnd.ms-excel, application/pdf" >
                        </label>
                        <input type="text" class="form-control highlight" v-model="txtDuongDan" value="">
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Ghi chú</div>
                <div class="value">
                    <input type="text" class="form-control" v-model="txtGhiChuFile" id="txtGhiChuFile">
                </div>
            </div>
            <div class="title-bg-main">
                <div class="pull-left">
                    <span class="title mart5">Danh sách thuê bao</span>
                    <a href="#" class="btn btn-second pad2 lh20 button-not-bg marl10">
                        <span class="nc-icon-outline arrows-1_refresh-69 f20"></span>
                    </a>
                </div>
                <div class="pull-right">
                    <a href="#" class="btn btn-second pad2 lh20 button-not-bg marl10">
                        <span class="nc-icon-outline ui-2_filter f20"></span>
                    </a>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="table-content" style="max-height: 300px;">
                <DataGrid
                    ref="ds_danhsach_thuebao"
                    v-bind:columns="columnsDSTB"
                    v-bind:dataSource="ds_danhsach_thuebao"
                    :showColumnCheckbox="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    panelDataHeight="150"
                    :allowPaging="true" :enablePagingServer="false"
                    @selectedItemsChanged="gridviewDanhSachTB_SelectRow"
                ></DataGrid>   
            </div>
        </div>

        <ejs-dialog :enableResize='true' :visible="false" ref="lydotra"
                :header='"Nhập nội dung trả phiếu"' showCloseIcon=true target=".main-wrapper" width='40%' height="300">
            <LyDoTra @xac_nhan="popup_LyDoTra_XacNhan" ref="ppLyDoTra" />
        </ejs-dialog>
    </div>
</template>

<script>
// import DataGridCustom from "./components/DataGrid";
import DataGrid from "@/components/Controls/DataGrid";
import collums from "./define/collums.js";
import moment from "moment";
import { Query } from '@syncfusion/ej2-data'
import api from './api/index.js'
import LyDoTra from "./popup/popup-LyDoTra.vue";
import UploadEProfileAPI from '@/modules/contract/profile/UploadEProfile/UploadEProfileAPI'

import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);

export default {
    name:'QuanLyLoHoSo',
    components:{
        // DataGridCustom,
        DataGrid,
        LyDoTra
    },
    props:{
        tths_id:{
            type:Number,
            default: -1
        },
        quytrinh_id:{
            type:Number,
            default: -1
        },
        nuthc:{
            type:Boolean,
            default: false
        },
        themfile:{
            type:Boolean,
            default: false
        },
        tab1visible:{
            type:Boolean,
            default: false
        },
        tableLayoutPanel2: {
            type:Boolean,
            default: false
        }
    },
    data() {
        return {
            ...collums,
            columnsDSHS: [
                {
                    fieldName:'ischeck',
                    headerText:'',
                    allowFiltering:false,
                    width:120,
                    textAlign: "center",
                    freeze: 'left',
                    template: this.columnTemplateCheckBox(this),
                    headerTemplate:this.columnTemplateCheckBoxHeader(this)
                },
                { fieldName: "stt", headerText: "STT", allowFiltering: true, allowSorting: true, textAlign: 'center', width: "100px", freeze: 'left' },
                { fieldName: "ma_gd", headerText: "Mã GD", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true", customAttributes: {class: 'clblue'}, freeze: 'left' },
                { fieldName: "ma_kh", headerText: "Mã KH", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true" },
                { fieldName: "ten_kh", headerText: "Tên KH", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true" },
                { fieldName: "diachi_kh", headerText: "Địa chỉ KH", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true" },
                { fieldName: "ngaygiao", headerText: "Ngày giao", allowFiltering: true, allowSorting: true, textAlign: 'center', autofit: "true" },
                { fieldName: "malo_hs", headerText: "Số lô", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true", customAttributes: {class: 'clred'} },
                { fieldName: "dv_giao", headerText: "Đơn vị giao", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true" },
                { fieldName: "ma_bhs", headerText: "Mã bộ HS", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true", customAttributes: {class: 'clbrown'} },
                { fieldName: "tendv_next", headerText: "Đơn vị nhận", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true" },
                { fieldName: "nhanvien_phat", headerText: "Nhân viên phát", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true", visible: true },
                { fieldName: "tt_phat_hs", headerText: "Trạng thái HS", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true", visible: true },
                { fieldName: "nguoitao", headerText: "Người tạo", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true" },
                { fieldName: "tuyenthutt", headerText: "Tuyến thu", allowFiltering: true, allowSorting: true, textAlign: 'left', autofit: "true" },
                { fieldName: "trangthai_up_file_img", headerText: "UpFile", allowHtml: true, textAlign: 'center', autofit: "true" },
                { fieldName: "ckb_xacnhan_img", headerText: "Đã xác nhận", allowHtml: true, textAlign: 'center', autofit: "true" },
            ],
            visible: { luufile: false },
            ds_danhsach_bohoso: [],
            ds_danhsach_bohoso_load: [],
            ds_danhsach_bohoso_selected: [],
            ds_chitiet_bohoso: [],
            ds_chitiet_bohoso_selected: [],
            ds_danhsach_thuebao: [],
            ds_danhsach_thuebao_load: [],
            ds_danhsach_thuebao_selected: [],
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            ngaygiaotu: null,
            ngaygiaoden: null,
            solohs: null,
            lstSoLoHS: [],
            donvigiao: null,
            lstDonViGiao: [],
            kieugiao: null,
            lstKieuGiao: [],    
            trangthai: null,
            lstTrangThai: [],  
            nhomfile: null,
            lstNhomFile: [],    
            loaifile: null,
            lstLoaiFile: [],
            lstLoaiFile_load: [],
            ma_kh_gd: "",
            txtSoLHS: "",
            txtNDTRa: "",
            txtMaKH: "",
            txtTenKH: "",
            txtGD: "",
            txtTrangThai: "",
            txtDonviGiao: "",
            file_refresh: 0,
            fullFile: {},
            txtDuongDan: "",
            txtGhiChuFile: "",
            vhdkh_id_luoi: null,
            vphieu_id_luoi: null,
            vbohs_id: -1,
            batbuoc: -1,
            vdl_file_id: 0,
            vma_gd: "",
            dtHSThueBao: [],
            tuyenthu: null,
            lstTuyenThu: [],
            dvtt: null,
            lstDVTT: [],
            nhanviennhan: null,
            lstNhanVienNhan: [],
            tuyenthutt_id: null,
            ckbAuto: true,
            pageChange: false,
            selectIndex:0
        }
    },
    methods: {
        init() {
            var today = moment();
            this.ngaygiaotu = moment(today).add(-10, 'days').format("DD/MM/yyyy");
            this.ngaygiaoden = this.ngay_ht;
            var objIndex = this.columnsDSHS.findIndex((obj => obj.fieldName == "tt_phat_hs")); 
            this.columnsDSHS[objIndex].visible = false
            if (this.nuthc) {
                this.columnsDSHS[objIndex].visible = true
            }
        },
        async txtMaGD_KeyPress() {
            if (this.ma_kh_gd == undefined || this.ma_kh_gd == "") return
            if (this.tths_id != 6) {
                await this.TIMKIEM(this.ma_kh_gd, 1)
            }         
        },
        async NAP_COMB_GIAOPHIEU(bosung_filehs, giaophieu_qltt) {
            if (!giaophieu_qltt) {
                var objIndex = this.columnsDSHS.findIndex((obj => obj.fieldName == "nhanvien_phat")); this.columnsDSHS[objIndex].visible = false
            }

            this.lstNhomFile = [];
            this.lstLoaiFile = [];
            this.lstLoaiFile_load = [];
            var dt_kieugiao = await this.kieu_giao_hoso();
            if (dt_kieugiao != null && dt_kieugiao.length > 0) {
                this.lstKieuGiao = dt_kieugiao;
                this.kieugiao = dt_kieugiao[0].ID;
            }

            var dt_trangthai = await this.trangthai_ph();
            if (dt_trangthai != null && dt_trangthai.length > 0) {
                this.lstTrangThai = dt_trangthai;
                this.trangthai = dt_trangthai[0].ID;
            }

            if (bosung_filehs) {
                var dt_nhomfile = await this.nhom_file();
                if (dt_nhomfile != null && dt_nhomfile.length > 0) {
                    this.lstNhomFile = dt_nhomfile;
                    this.nhomfile = String(dt_nhomfile[0].ID);
                }

                var dt_loaifile = await this.loai_file();
                if (dt_loaifile != null && dt_loaifile.length > 0) {
                    for (var i=0; i<dt_loaifile.length; i++) {
                        dt_loaifile[i].loaifile_id = String(dt_loaifile[i].loaifile_id)
                    }
                    this.lstLoaiFile = dt_loaifile;
                    this.lstLoaiFile_load = dt_loaifile
                    this.loaifile = String(dt_loaifile[0].loaifile_id);
                }
            }
        },
        async lay_tt_huonggiao() {
            var dt_hg = []
            try {
                var params = {
                    "quytrinh_id": this.quytrinh_id,
                    "tths_id": this.tths_id,
                }
                var response = await api.ur_003_ds_hoso_cho_laphopdong(this.axios, params);                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    dt_hg = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return dt_hg
        },
        async kieu_giao_hoso() {
            var dt_kieugiao = []
            try {
                var response = await api.ur_29002_002_kieu_giao_hoso(this.axios);                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    dt_kieugiao = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return dt_kieugiao
        },
        async trangthai_ph() {
            var dt_trangthai = []
            try {
                var response = await api.ur_29002_005_trangthai_ph(this.axios);               
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    dt_trangthai = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return dt_trangthai
        },
        async nhom_file() {
            var dt_nhomfile = []
            try {
                var response = await api.ur_29002_010_nhom_file(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    dt_nhomfile = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return dt_nhomfile
        },
        async loai_file() {
            var dt_loaifile = []
            try {
                var response = await api.ur_29002_011_loai_file(this.axios);                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    dt_loaifile = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return dt_loaifile
        },
        onFilterKieuGiao: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.lstKieuGiao, query);
        },
        onFilterSoLoHS: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('MALO_HS', 'contains', e.text, true) : query;
            e.updateData(this.lstSoLoHS, query);
        },
        onFilterDonViGiao: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('DV_GIAO', 'contains', e.text, true) : query;
            e.updateData(this.lstDonViGiao, query);
        },
        onFilterTrangThai: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.lstTrangThai, query);
        },
        onFilterNhomFile: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.lstNhomFile, query);
        },
        onFilterLoaiFile: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('loai_file', 'contains', e.text, true) : query;
            e.updateData(this.lstLoaiFile, query);
        },
        onFilterTuyenThu: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('tuyenthu', 'contains', e.text, true) : query;
            e.updateData(this.lstTuyenThu, query);
        },
        onFilterDVTT: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_dv', 'contains', e.text, true) : query;
            e.updateData(this.lstDVTT, query);
        },
        onFilterNhanVienNhan: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_nv', 'contains', e.text, true) : query;
            e.updateData(this.lstNhanVienNhan, query);
        },
        async loadCboTuyenThu() {
            this.lstTuyenThu = []
            this.tuyenthu = null
            try {
                var params = {
                    "p_type": 1,
                    "p_param": ""
                }
                var response = await api.ur_29004_001_sp_tt_tuyenthu(this.axios, params);           
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.lstTuyenThu = response.data.data
                    if (this.lstTuyenThu.length > 0) {
                        this.tuyenthu = this.lstTuyenThu[0].tuyenthu_id
                    }     
                }
            } catch (e) {
                console.log(e)
            }
        },
        async cboTuyenThu_Changed() {
            this.lstDVTT = []
            this.dvtt = null
            try {
                var params = {
                    "tuyenthu_id": this.tuyenthu
                }
                var response = await api.ur_29004_002_sp_lay_ds_donvi_theo_tuyenthu(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.lstDVTT = response.data.data
                    if (this.lstDVTT.length > 0) {
                        this.dvtt = this.lstDVTT[0].donvi_id
                    }     
                }
            } catch (e) {
                console.log(e)
            }
        },
        async cboDVTT_Changed() {
            this.lstNhanVienNhan = []
            this.nhanviennhan = null
            if (this.dvtt != null) {    
                try {
                    var params = {
                        "dvtt_id": this.dvtt
                    }
                    var response = await api.ur_29004_003_ds_nhanvien_donvi(this.axios, params);
                    console.log(response)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                        this.lstNhanVienNhan = response.data.data
                        if (this.lstNhanVienNhan.length > 0) {
                            this.nhanviennhan = this.lstNhanVienNhan[0].nhanvien_id
                        }     
                    }
                } catch (e) {
                    console.log(e)
                }
            }       
        },
        cboNhanVienNhan_Changed() {
            
        },
        async cboKieuGiao_Changed() {
            await this.TIMKIEM("", 0);
        },
        cboSoLoHS_Changed() {
            this.LOC_LUOI_TB();
        },
        cboDonViGiao_Changed() {
            this.LOC_LUOI_TB();
        },
        async cboTrangThai_Changed() {
            if (String(this.trangthai) == "2") {
                this.$emit("cboTrangThai_Changed", false)
            }  else {
                this.$emit("cboTrangThai_Changed", true)
            }
            await this.TIMKIEM("", 0);
        },
        cboNhomFile_Changed() {
            try {
                var lstTemp = []
                for (var i=0; i<this.lstLoaiFile_load.length; i++) {
                    if (String(this.lstLoaiFile_load[i].nhomfile_id) == "-1" || String(this.lstLoaiFile_load[i].nhomfile_id) == String(this.nhomfile)) {
                        lstTemp.push(this.lstLoaiFile_load[i])
                    }
                }

                this.lstLoaiFile = lstTemp.slice();
                this.LOC_LUOI_TB_FILE();
            } catch (ex) {
                console.log(ex)
            }
        },
        cboLoaiFile_Changed() {
            try {
                this.batbuoc = -1;
                var lstTemp = []
                for (var i=0; i<this.lstLoaiFile.length; i++) {
                    if (String(this.lstLoaiFile[i].loaifile_id) == String(this.loaifile)) {
                        this.batbuoc = Number(this.lstLoaiFile[i].kieu);
                        console.log(this.lstLoaiFile[i])
                    }

                    if (String(this.lstLoaiFile[i].nhomfile_id) == "-1" || String(this.lstLoaiFile[i].nhomfile_id) == String(this.nhomfile)) {
                        lstTemp.push(this.lstLoaiFile[i])
                    }
                }

                this.lstLoaiFile = lstTemp.slice();
                this.LOC_LUOI_TB_FILE();
            } catch (e) {
                console.log(e)
            }
        },
        LOC_LUOI_TB() {
            this.ds_danhsach_bohoso = this.ds_danhsach_bohoso_load.slice()
            if (this.ds_danhsach_bohoso.length > 0) {
                var lst = [];
                if (!this.donvigiao || this.donvigiao == "-1") {
                    lst = this.ds_danhsach_bohoso.slice();
                } else {
                    lst = this.ds_danhsach_bohoso.filter(e => String(e.donvi_giao_id) == this.donvigiao)
                }

                if (!this.solohs || this.solohs == "0") {
                    this.ds_danhsach_bohoso = lst.slice()
                } else {
                    this.ds_danhsach_bohoso = lst.filter(e => String(e.lohs_id) == this.solohs)
                }
            }
        },
        CLEAR() {
            this.ds_chitiet_bohoso = []
            this.ds_chitiet_bohoso_selected = []
            this.txtSoLHS = ""
            this.txtTrangThai = ""
            this.txtDonviGiao = ""
            this.txtNDTRa = ""
            this.txtMaKH = ""
            this.txtTenKH = ""
            this.txtGD = ""
        },
        async TIMKIEM(vma_gd, thongbao) {
            try {
                this.loading(true)
                this.CLEAR();
                this.ds_danhsach_bohoso = []
                this.ds_danhsach_bohoso_load = []
                if (vma_gd == "") vma_gd = "0"
                var ds = await this.layds_bohoso_chuagiao_theo_makh_magd(vma_gd)
                if (!ds || ds.length == 0) {
                    if (thongbao == 1) {
                        this.$toast.error("Không tìm thấy thông tin phiếu giao đến");
                    }            
                    this.ds_danhsach_bohoso = []
                    this.ds_danhsach_bohoso_load = []
                } else {
                    this.ds_danhsach_bohoso = ds;
                    this.ds_danhsach_bohoso_load = ds;
                    this.ds_danhsach_bohoso.forEach(item => item.chon = "0")
                    this.NAP_COMBO_TIMKIEM();
                    if (this.nuthc) {
                        this.NAP_DS_DL_DOITUONG();
                    }            
                }
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }        
        },
        NAP_COMBO_TIMKIEM() {
            try {
                this.lstSoLoHS = []
                this.lstDonViGiao = []
                if (this.ds_danhsach_bohoso != null && this.ds_danhsach_bohoso.length != 0) {  
                    for (var i=0; i<this.ds_danhsach_bohoso.length; i++) {
                        if (this.ds_danhsach_bohoso[i].malo_hs != null && this.ds_danhsach_bohoso[i].malo_hs != "") {
                            var temp = {"LOHS_ID": String(this.ds_danhsach_bohoso[i].lohs_id), "MALO_HS": this.ds_danhsach_bohoso[i].malo_hs}
                            if (this.lstSoLoHS.filter(e => e.LOHS_ID == temp.LOHS_ID).length == 0) {
                                this.lstSoLoHS.push(temp)
                            }           
                        }

                        var temp = {"DONVI_GIAO_ID": String(this.ds_danhsach_bohoso[i].donvi_giao_id), "DV_GIAO": this.ds_danhsach_bohoso[i].dv_giao}
                        if (this.lstDonViGiao.filter(e => e.DONVI_GIAO_ID == temp.DONVI_GIAO_ID).length == 0) {
                            this.lstDonViGiao.push(temp)
                        } 
                    }

                    if (this.lstSoLoHS.length > 0) {
                        this.lstSoLoHS.push({"LOHS_ID": "0", "MALO_HS": "Tất cả"})
                        this.lstSoLoHS.sort((a, b) => (Number(a.LOHS_ID) > Number(b.LOHS_ID)) ? 1 : -1)                    
                        this.solohs = this.lstSoLoHS[0].LOHS_ID
                    }

                    this.lstDonViGiao.push({"DONVI_GIAO_ID": "-1", "DV_GIAO": "Tất cả"})
                    this.lstDonViGiao.sort((a, b) => (Number(a.DONVI_GIAO_ID) > Number(b.DONVI_GIAO_ID)) ? 1 : -1)
                    this.donvigiao = this.lstDonViGiao[0].DONVI_GIAO_ID
                }
            } catch (e) {
                console.log(e)      
            }
        },
        NAP_DS_DL_DOITUONG() {

        },
        async layds_bohoso_chuagiao_theo_makh_magd(vma_gd) {
            var ds = []
            var checkBox = document.getElementById("chb_ngaygiaotu");
            try {
                var params = {
                    "denngay": this.ngaygiaoden,
                    "huonggiao_id": 0,
                    "kieugiao": Number(this.kieugiao),
                    "ma_gd": vma_gd,
                    "phieu_id": 0,
                    "quytrinh_id": this.quytrinh_id,
                    "timngay": checkBox.checked ? 1 : 0,
                    "tths_id": this.tths_id,
                    "ttph_id": Number(this.trangthai),
                    "tungay": this.ngaygiaotu
                }
                var response = await api.ur_29002_007_layds_bohoso_chuagiao_theo_makh_magd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    ds = response.data.data;
                    for (var i=0; i<ds.length; i++) {
                        if (ds[i].trangthai_up_file == 1) {
                            ds[i].trangthai_up_file_img = "<div class='image'><img style='mix-blend-mode: multiply; width: 20px !important; height: 20px !important' src='" + document.getElementById("imgFile").src + "' /></div>";
                        } else {
                            ds[i].trangthai_up_file_img = "";
                        }

                        if (ds[i].ckb_xacnhan == 1) {
                            ds[i].ckb_xacnhan_img = "<div class='image'><img style='mix-blend-mode: multiply; width: 20px !important; height: 20px !important' src='" + document.getElementById("imgSuccess").src + "' /></div>";
                        } else {
                            ds[i].ckb_xacnhan_img = "<div class='image'><img style='mix-blend-mode: multiply; width: 20px !important; height: 20px !important' src='" + document.getElementById("imgWarning").src + "' /></div>";
                        }
                    }
                }
            } catch (e) {
                console.log(e)
            }
            return ds
        },
        gridviewDanhSach_DataBound() {
            if (this.pageChange) {
                this.pageChange = false
                this.gridviewDanhSach_actionComplete()
            }
        },
        gridviewDanhSach_pageChanged() {     
            this.pageChange = true     
        },
        gridviewDanhSach_rowDeselected(row) {
            if (row.data == undefined) return
            if (!row.isInteracted) return
            if (row.data.length == undefined) {
                for (var i=0; i<this.ds_danhsach_bohoso.length; i++) {
                    if (row.data.bohs_id == this.ds_danhsach_bohoso[i].bohs_id) {
                        this.ds_danhsach_bohoso[i].chon = "0"
                    } else {
                        if (this.ckbAuto && row.data.malo_hs == this.ds_danhsach_bohoso[i].malo_hs) {
                            this.ds_danhsach_bohoso[i].chon = "0"
                        }
                    }
                }
            } else {      
                for (var ix=0; ix<row.data.length; ix++) {
                    for (var i=0; i<this.ds_danhsach_bohoso.length; i++) {
                        if (row.data[ix].bohs_id == this.ds_danhsach_bohoso[i].bohs_id) {
                            this.ds_danhsach_bohoso[i].chon = "0"
                        } else {
                            if (this.ckbAuto && row.data[ix].malo_hs == this.ds_danhsach_bohoso[i].malo_hs) {
                                this.ds_danhsach_bohoso[i].chon = "0"
                            }
                        }
                    }
                }
            }
            this.gridviewDanhSach_actionComplete()
        },
        gridviewDanhSach_actionComplete() {
            var lstCheck = []
            var pIndex = this.$refs.ds_danhsach_bohoso.$refs.pagination.pageIndexData
            var pSize = this.$refs.ds_danhsach_bohoso.$refs.pagination.pageSizeData
            for (var i=0; i<this.ds_danhsach_bohoso.length; i++) {
                if (this.ds_danhsach_bohoso[i].chon == "1") {
                    if (i>=pIndex*pSize && i<(pIndex+1)*pSize) {
                        lstCheck.push(i - pIndex*pSize)             
                    }
                }
            }

            this.$refs.ds_danhsach_bohoso.grid.selectRows(lstCheck)
        },
        async gridviewDanhSach_FocusedRowChanged(row) {
            if (row.data == undefined) return
            this.selectIndex=row.rowIndex
            
            try {
                this.loading(true)
                if (row.data.length == undefined) {
                    this.CLEAR();
                    await this.NAP_DS_LUOI(row.data);

                    // for (var ix in this.ds_danhsach_bohoso) {
                    //     if (row.data.bohs_id == this.ds_danhsach_bohoso[ix].bohs_id) {
                    //         this.ds_danhsach_bohoso[ix].chon = "1"
                    //     } else {
                    //         if (this.ckbAuto && row.data.malo_hs == this.ds_danhsach_bohoso[ix].malo_hs) {
                    //             this.ds_danhsach_bohoso[ix].chon = "1"
                    //         }
                    //     }  
                    // }

                   // this.gridviewDanhSach_actionComplete()
                } else {            
                    // for (var i=0; i<row.data.length; i++) {
                    //     for (var ix in this.ds_danhsach_bohoso) {           
                    //         if (row.data[i].bohs_id == this.ds_danhsach_bohoso[ix].bohs_id) {
                    //             this.ds_danhsach_bohoso[ix].chon = "1"
                    //         } else {
                    //             if (this.ckbAuto && row.data[i].malo_hs == this.ds_danhsach_bohoso[ix].malo_hs) {
                    //                 this.ds_danhsach_bohoso[ix].chon = "1"
                    //             }
                    //         }  
                    //     }
                    // }
                }
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        gridviewDanhSach_filterChanged: function (data) {
            // console.log('dataItems', this.$refs.ds_danhsach_bohoso.dataItems)
            // console.log('filteredDataSource', this.$refs.ds_danhsach_bohoso.filteredDataSource)
            // this.ds_danhsach_bohoso = this.$refs.ds_danhsach_bohoso.filteredDataSource
        },
        async NAP_DS_LUOI(row) {
            this.txtGD = row.ma_gd
            this.txtTenKH = row.ten_kh
            this.txtMaKH = row.ma_kh
            this.txtSoLHS = row.malo_hs
            this.txtTrangThai = row.trangthai_hs
            this.txtDonviGiao = row.dv_giao
            this.txtNDTRa = row.nd_tra_con
            this.vhdkh_id_luoi = (row.hdkh_id == null ? -1 : Number(row.hdkh_id))
            this.vphieu_id_luoi = (row.phieu_id == null ? -1 : Number(row.phieu_id))
            this.vbohs_id = (row.bohs_id == null ? -1 : Number(row.bohs_id))
            this.vma_gd = row.ma_gd
            this.tuyenthutt_id = row.tuyenthutt_id

            await this.LOADFILE_HS();
            if (!this.themfile) {
                this.NAP_DS_HOPDONGTB(this.vhdkh_id_luoi);
            }           
        },
        async LOADFILE_HS() {
            try {
                var params = {
                    "hoso_id": this.vbohs_id
                }
                var response = await api.ur_29002_008_layds_file_trong_hoso(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_chitiet_bohoso = response.data.data;
                    for (var i=0; i<this.ds_chitiet_bohoso.length; i++) {
                        var html = "";
                        html += "<a href='" + this.ds_chitiet_bohoso[i].url + "' target='_blank' rel='noopener noreferrer' class='btn btn-main pad3'><span class='one-search lh14'></span></a>"
                        html += "<a href='" + this.ds_chitiet_bohoso[i].url + "' download='" + this.ds_chitiet_bohoso[i].url.split('/').pop() + "' class='btn btn-main pad3 w24'><span class='fa fa-angle-down lh14'></span></a>"
                        this.ds_chitiet_bohoso[i].xem_taive = html;                                                             
                    }            
                }
            } catch (e) {
                console.log(e)
                this.$toast.error("Có lỗi khi lấy danh sách file theo hồ sơ " + e)
            }
        },
        async lay_ds_hd_tb_theo_hdkh_id(vhdkh_id) {
            var ds = []
            try {
                var params = {
                    "vhdkh_id": vhdkh_id
                }
                var response = await api.ur_29002_012_lay_ds_hd_tb_theo_hdkh_id(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    ds = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return ds;
        },
        async NAP_DS_HOPDONGTB(hdkh_id) {
            try {
                this.loaifile = "-1";
                this.nhomfile = "-1";
                this.txtDuongDan = "";
                this.txtGhiChuFile = "";

                this.ds_danhsach_thuebao_load = await this.lay_ds_hd_tb_theo_hdkh_id(hdkh_id)
                this.ds_danhsach_thuebao = this.ds_danhsach_thuebao_load.slice()
                if (this.ds_danhsach_thuebao.length > 0) {
                    this.LOC_LUOI_TB_FILE();
                }
            } catch (ex) {
                this.$toast.error("Có lỗi khi lấy thông tin hợp đồng " + ex);
            }
        },
        LOC_LUOI_TB_FILE() {
            try {
                this.ds_danhsach_thuebao_selected = [];
                if (String(this.loaifile) == "-1") {
                    this.batbuoc = -1;
                }

                if (!this.themfile) {
                    if (this.batbuoc == 1) {
                        if ((Number(this.nhomfile) == 1 || Number(this.nhomfile) <= 0) && Number(this.nhomfile) != -1) {
                            this.ds_danhsach_thuebao = []
                            for (var i=0; i<this.ds_danhsach_thuebao_load.length; i++) {
                                if (Number(this.ds_danhsach_thuebao_load[i].LOAIHD_ID) != 0 && String(this.ds_danhsach_thuebao_load[i].MA_KH) == "0") {
                                    this.ds_danhsach_thuebao.push(this.ds_danhsach_thuebao_load[i])
                                }
                            }
                        } else if (Number(this.nhomfile) == -1) {

                        } else {
                            this.ds_danhsach_thuebao = []
                            for (var i=0; i<this.ds_danhsach_thuebao_load.length; i++) {
                                if (Number(this.ds_danhsach_thuebao_load[i].LOAIHD_ID) == 0 && String(this.ds_danhsach_thuebao_load[i].MA_KH) == "0") {
                                    this.ds_danhsach_thuebao.push(this.ds_danhsach_thuebao_load[i])
                                }
                            }
                        }
                    }

                    if (this.batbuoc == 2) {
                        if ((Number(this.nhomfile) == 1 || Number(this.nhomfile) <= 0) && Number(this.nhomfile) != -1) {
                            this.ds_danhsach_thuebao = []
                            for (var i=0; i<this.ds_danhsach_thuebao_load.length; i++) {
                                if (Number(this.ds_danhsach_thuebao_load[i].LOAIHD_ID) != 0 && String(this.ds_danhsach_thuebao_load[i].MA_KH) != "0") {
                                    this.ds_danhsach_thuebao.push(this.ds_danhsach_thuebao_load[i])
                                }
                            }
                        } else if (Number(this.nhomfile) == -1) {

                        } else {
                            this.ds_danhsach_thuebao = []
                            for (var i=0; i<this.ds_danhsach_thuebao_load.length; i++) {
                                if (Number(this.ds_danhsach_thuebao_load[i].LOAIHD_ID) == 0 && String(this.ds_danhsach_thuebao_load[i].MA_KH) != "0") {
                                    this.ds_danhsach_thuebao.push(this.ds_danhsach_thuebao_load[i])
                                }
                            }
                        }

                        if (this.ds_danhsach_thuebao != null && this.ds_danhsach_thuebao.length > 0) {
                            var lstSelect = []
                            for (var i=0; i<this.ds_danhsach_thuebao.length; i++) {
                                if (Number(this.ds_danhsach_thuebao[i].HDKH_ID) != 0) {
                                    this.ds_danhsach_thuebao_selected.push(this.ds_danhsach_thuebao[i])
                                    lstSelect.push(i)
                                }
                            }
                            this.$refs.ds_danhsach_thuebao.$refs.grid.selectRows(lstSelect)
                        }            
                    }
                }
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        btnClear_Click() {
            this.loaifile = "-1";
            this.nhomfile = "-1";
            this.txtDuongDan = "";
            this.txtGhiChuFile = "";
            $("#upload-file").val(null);
            this.file_refresh = 0;
            this.fullFile = []
        },
        txtDuongDan_ButtonClick(event) {
            try {
                this.file_refresh += 1
                this.txtDuongDan = event.target.files[0].name
                this.fullFile = event.target.files                
            } catch (e) {
                this.$toast.error('Lỗi import file: ' + e)
            }
        },
        btnThemFile_Click() {
            this.$emit("btnThemFile_Click")
        },
        async fn_lay_soluong_phieu_bohs(phieu_id) {
            var kq = "1"
            try {
                var params = {
                    "phieu_id": phieu_id
                }
                var response = await api.ur_29004_008_fn_lay_soluong_phieu_bohs(this.axios, params);      
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    kq = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return kq
        },
        async CAPNHAT_FILE(quyen) {
            try {
                if (this.vbohs_id == -1) {
                    this.$toast.error("Không tìm thấy bộ hồ sơ để thêm file.");
                    return;
                }

                if (Number(this.loaifile) == -1) {
                    this.$toast.error("Bạn chưa chọn loại file.");
                    return;
                }

                if (this.txtDuongDan == "" && !this.themfile) {
                    this.$toast.error("Bạn chưa nhập đường dẫn file");
                    return;
                }

                var kt = await this.fn_lay_soluong_phieu_bohs(this.vphieu_id_luoi)
                if (kt != "0" && !quyen) {
                    this.$toast.error("Phiếu " + this.vma_gd + " trạng thái bị thay đổi. Bạn hãy kiểm tra lại");
                    return;
                }

                this.$bvModal.msgBoxConfirm("Bạn có muốn thêm file cho mã GD: " + this.vma_gd, {
                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                    }).then(async v => {
                        if (v) {
                            if (!this.themfile) {
                                this.GET_HS_THUEBAO();
                                if (this.dtHSThueBao.length == 0) {
                                    this.$toast.error("Bạn bắt buộc phải gán thuê bao với loại file " + this.loaifile);
                                    return;
                                }

                                await this.fu_UploadCompleted();
                            }
                        }
                    });
            } catch (ex) {
                this.$toast.error("Có lỗi khi thêm file cho bộ hồ sơ " + ex);
            }
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
        async fu_UploadCompleted() {            
            try {
                this.loading(true)
                if (!this.themfile) {
                    if (this.txtDuongDan && this.fullFile && this.fullFile.length > 0) { 
                        var formData = new FormData()
                        for (var file of this.fullFile) {
                            formData.append('files', file)
                        }
                        await UploadEProfileAPI.upload_file_hoso(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                            if(response.data.error_code && response.data.error_code === "BSS-00000000") {
                                this.txtDuongDan = response.data.data.join('|');         
                            }
                        }).catch(function(){
                            console.log('File upload failed! ');
                        });
                    }
                    var vurl = this.txtDuongDan;
                    var file_id = 0;
                    var kq = await this.fn_capnhat_file_hs(6, 0, Number(this.loaifile), vurl, file_id, [], this.vbohs_id, this.txtGhiChuFile, this.dtHSThueBao)
                    if (String(kq).includes("ERROR")) {
                        this.DeleteFileOnServer(vurl);
                        this.$toast.error(String(kq));
                        return;
                    }

                    this.$toast.success("Thêm mới file hồ sơ thành công!");
                    await this.LOADFILE_HS();
                    this.btnClear_Click();

                    this.ds_chitiet_bohoso_selected = []
                    for (var i=0; i<this.ds_chitiet_bohoso.length; i++) {
                        if (this.ds_chitiet_bohoso[i].file_id == file_id) {
                            this.ds_chitiet_bohoso_selected.push(this.ds_chitiet_bohoso[i])
                            this.$refs.ds_chitiet_bohoso.$refs.grid.selectRow(i)
                            break;
                        }
                    }
                }
            } catch (ex) {
                this.$toast.error("Ghi thông tin thất bại: " + ex);
            } finally {
                this.loading(false)
            }
        },
        GET_HS_THUEBAO() {
            this.dtHSThueBao = []
            if (this.ds_danhsach_thuebao == null || this.ds_danhsach_thuebao.length == 0) return;

            var kt_baohong = false;
            for (var j = 0; j < this.ds_danhsach_thuebao.length; j++) {
                if (this.ds_danhsach_thuebao[j].BAOHONG_ID != undefined) {
                    kt_baohong = true;
                    break;
                }
            }

            for (var i = 0; i < this.ds_danhsach_thuebao_selected.length; i++) {
                var dr = {}
                dr.PHANVUNG_ID = String(this.$root.token.getPhanVungID())
                dr.THUEBAO_ID = String(this.ds_danhsach_thuebao_selected[i].THUEBAO_ID) == "0" ? "" : String(this.ds_danhsach_thuebao_selected[i].THUEBAO_ID);
                dr.THANHTOAN_ID = String(this.ds_danhsach_thuebao_selected[i].THANHTOAN_ID) == "0" ? "" : String(this.ds_danhsach_thuebao_selected[i].THANHTOAN_ID);
                dr.MA_TB = String(this.ds_danhsach_thuebao_selected[i].HDKH_ID) == "0" ? String(this.ds_danhsach_thuebao_selected[i].MA_TB) : "";
                dr.HDTB_ID = String(this.ds_danhsach_thuebao_selected[i].HDTB_ID) == "0" ? "" : String(this.ds_danhsach_thuebao_selected[i].HDTB_ID);
                dr.MA_KH = String(this.ds_danhsach_thuebao_selected[i].MA_KH) != "0" ? String(this.ds_danhsach_thuebao_selected[i].MA_KH) : "";
                if (kt_baohong)
                    dr.BAOHONG_ID = String(this.ds_danhsach_thuebao_selected[i].BAOHONG_ID) != "" ? String(this.ds_danhsach_thuebao_selected[i].BAOHONG_ID) : "";
                else
                    dr.BAOHONG_ID = "";
                dr.BAOHONG_ID = "";
                dr.HDKH_ID = String(this.ds_danhsach_thuebao_selected[i].HDKH_ID) != "0" ? String(this.ds_danhsach_thuebao_selected[i].HDKH_ID) : "";
                this.dtHSThueBao.push(dr);
            }
        },
        async giaophieu_donvi_tieptheo(danhsach, hoso_id, huonggiao_id, kieu) {
            var kq = ""
            try {
                var params = {
                    "danhsach": danhsach,
                    "hoso_id": hoso_id,
                    "huonggiao_id": huonggiao_id,
                    "kieu": kieu,
                    "quytrinh_id": this.quytrinh_id
                }
                var response = await api.ur_29002_014_giaophieu_donvi_tieptheo(this.axios, params);      
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    kq = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return kq
        },
        async GIAOPHIEU() {
            this.ds_danhsach_bohoso_selected = this.$refs.ds_danhsach_bohoso.filteredDataSource.filter(x=>x.ischeck=='1')
            // this.ds_danhsach_bohoso.forEach(item => {
            //     if (item.chon == "1") {
            //         this.ds_danhsach_bohoso_selected.push(item)
            //     }
            // })
            if (this.ds_danhsach_bohoso_selected.length == 0) {
                this.$toast.error("Bạn phải chọn phiếu để thực hiện")
                return
            }

            try {
                this.loading(true)
                if (!this.KIEMTRA_PHIEU(1)) return;

                var huonggiao_ht = 0;
                var danhsach = []
                for (var i = 0; i < this.ds_danhsach_bohoso_selected.length; i++) {
                    var temp = {}
                    temp.bhs_id = this.ds_danhsach_bohoso_selected[i].bohs_id
                    temp.phieu_id = this.ds_danhsach_bohoso_selected[i].phieu_id
                    temp.dvnext_id = this.ds_danhsach_bohoso_selected[i].dvnext_id
                    temp.dvgiao_id = this.ds_danhsach_bohoso_selected[i].donvi_nhan_id
                    temp.malhs = this.ds_danhsach_bohoso_selected[i].malo_hs
                    temp.lhs_id = this.ds_danhsach_bohoso_selected[i].lohs_id
                    temp.tthu_id = this.ds_danhsach_bohoso_selected[i].tuyenthutt_id
                    temp.dvtt_id = this.ds_danhsach_bohoso_selected[i].donvi_id_tt
                    temp.nv_nhan = this.ds_danhsach_bohoso_selected[i].nhanvien_nhan_id
                    danhsach.push(temp)

                    huonggiao_ht = Number(this.ds_danhsach_bohoso_selected[i].huonggiao_id);
                }

                var ketqua = await this.giaophieu_donvi_tieptheo(danhsach, 0, huonggiao_ht, 2)
                if (String(ketqua) == "1") {
                    this.$toast.success("Thực hiện giao phiếu thành công");
                    await this.TIMKIEM("", 0);
                } else {
                    this.$toast.error(ketqua);
                }
            } catch (e) {
                this.$toast.error(e);
            } finally {
                this.loading(false)
            }
        },
        KIEMTRA_PHIEU(kieu) {
            try {
                if (this.ds_danhsach_bohoso == null || this.ds_danhsach_bohoso.length == 0) {
                    this.$toast.error("Không có danh sách phiếu để thực hiện");
                    return false;
                }

                if (this.ds_danhsach_bohoso_selected.length == 0) {
                    this.$toast.error("Bạn chưa chọn phiếu để thực hiện");
                    return false;
                }
                
                if (kieu == 1) {
                    for (var i=0; i<this.ds_danhsach_bohoso_selected.length; i++) {
                        if (this.ds_danhsach_bohoso_selected[i].ckb_xacnhan == "0") {
                            this.$toast.error("Trong danh sách có phiếu chưa được xác nhận bạn không thể thực hiện");
                            return false;
                        }
                    }
                }

                if (kieu != 1) return true;

                var ds = []
                for (var i=0; i<this.ds_danhsach_bohoso_selected.length; i++) {
                    if (this.ds_danhsach_bohoso_selected[i].lohs_id != null && this.ds_danhsach_bohoso_selected[i].lohs_id != "") {
                        ds.push(this.ds_danhsach_bohoso_selected[i])
                    }
                }
                if (ds.length <= 0) return true;

                for (var i=0; i<ds.length; i++) {
                    var q = 0
                    for (var j=0; j<this.ds_danhsach_bohoso.length; j++) {
                        var isSelected = false;
                        for (var k=0; k<this.ds_danhsach_bohoso_selected.length; k++) {
                            if (this.ds_danhsach_bohoso[j].lohs_id == this.ds_danhsach_bohoso_selected[k].lohs_id) {
                                isSelected = true;
                                break;
                            }
                        }
                        if (!isSelected && this.ds_danhsach_bohoso[j].lohs_id == ds[i].lohs_id) {
                            q++
                        }
                    }
                    if (q > 0) {
                        this.$toast.error("Có phiếu thuộc lô " + ds[i].malo_hs + " chưa được chọn để giao");
                        return false;
                    }
                }

                return true;
            } catch (ex) {
                this.$toast.error("Có lỗi khi kiểm tra danh sách phiếu " + ex);
                return false;
            }
        },
        gridviewDanhSachTB_SelectRow(val) {
            this.ds_danhsach_thuebao_selected = [];
            for (var index in val) {
                for (var ix in this.ds_danhsach_thuebao) {
                    if (val[index].MA_TB == this.ds_danhsach_thuebao[ix].MA_TB) {
                        this.ds_danhsach_thuebao_selected.push(val[index]);
                    }
                }
            }
        },
        gridCTHS_SelectRow(val) {
            this.ds_chitiet_bohoso_selected = [];
            for (var index in val) {
                for (var ix in this.ds_chitiet_bohoso) {
                    if (val[index].file_id == this.ds_chitiet_bohoso[ix].file_id) {
                        this.ds_chitiet_bohoso_selected.push(val[index]);
                    }
                }
            }
        },
        async TRAPHIEU() {
            this.ds_danhsach_bohoso_selected = this.$refs.ds_danhsach_bohoso.filteredDataSource.filter(x=>x.ischeck=='1')            
            // this.ds_danhsach_bohoso.forEach(item => {
            //     if (item.chon == "1") {
            //         this.ds_danhsach_bohoso_selected.push(item)
            //     }
            // })
            if (this.ds_danhsach_bohoso_selected.length == 0) {
                this.$toast.error("Bạn phải chọn phiếu để thực hiện")
                return
            }

            try {
                if (!this.KIEMTRA_PHIEU(0)) return;
                this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn trả phiếu không ?", {
                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                    }).then(async v => {
                        if (v) {
                            this.$refs.ppLyDoTra.noiDungTra = "";
                            this.$refs.lydotra.show(); 
                        }
                    });
            } catch (e) {

            }
        },
        async popup_LyDoTra_XacNhan(ndTra) {
            this.$refs.lydotra.hide();
            await this.capnhat_traphieu(ndTra)
        },
        async traphieu_bohoso(danhsach, huonggiao_id, kieu, noidung, xoalohs) {
            var kq = ""
            try {
                var params = {
                    "danhsach": danhsach,
                    "huonggiao_id": huonggiao_id,
                    "kieu": kieu,
                    "noidung": noidung,
                    "xoalohs": xoalohs
                }
                var response = await api.ur_29002_015_traphieu_bohoso(this.axios, params);      
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    kq = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return kq
        },
        async capnhat_traphieu(ndTra) {
            try {
                this.loading(true)
                var huonggiao_ht = 0;
                var danhsach = []
                for (var i = 0; i < this.ds_danhsach_bohoso_selected.length; i++) {
                    var temp = {}
                    temp.bhs_id = this.ds_danhsach_bohoso_selected[i].bohs_id
                    temp.phieu_id = this.ds_danhsach_bohoso_selected[i].phieu_id
                    temp.phieu_id_cha = this.ds_danhsach_bohoso_selected[i].phieu_cha_id
                    temp.dvnext_id = this.ds_danhsach_bohoso_selected[i].dvnext_id
                    temp.dvgiao_id = this.ds_danhsach_bohoso_selected[i].donvi_nhan_id
                    temp.malhs = this.ds_danhsach_bohoso_selected[i].malo_hs
                    temp.lhs_id = this.ds_danhsach_bohoso_selected[i].lohs_id
                    temp.tthu_id = null
                    temp.dvtt_id = null
                    temp.nv_nhan = null
                    danhsach.push(temp)
                    huonggiao_ht = Number(this.ds_danhsach_bohoso_selected[i].huonggiao_id);
                }

                var ketqua = await this.traphieu_bohoso(danhsach, huonggiao_ht, 2, ndTra, this.tab1visible ? 1 : 0)
                if (String(ketqua) == "1") {
                    this.$toast.success("Thực hiện trả phiếu thành công");
                    await this.TIMKIEM("", 0);
                } else {
                    this.$toast.error(ketqua);
                }
            } catch (e) {
                this.$toast.error(e);
            } finally {
                this.loading(false)
            }
        },
        async xacnhan_bohoso(danhsach, huonggiao_id, kieu, noidung, xn_gv) {
            var kq = ""
            try {
                var params = {
                    "danhsach": danhsach,
                    "huonggiao_id": huonggiao_id,
                    "kieu": kieu,
                    "noidung": noidung,
                    "xn_gv": xn_gv
                }
                var response = await api.ur_29002_017_xacnhan_bohoso(this.axios, params);      
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    kq = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return kq
        },
        async XACNHAN_DS() {
            this.ds_danhsach_bohoso_selected = this.$refs.ds_danhsach_bohoso.filteredDataSource.filter(x=>x.ischeck=='1')
            // this.ds_danhsach_bohoso.forEach(item => {
            //     if (item.chon == "1") {
            //         this.ds_danhsach_bohoso_selected.push(item)
            //     }
            // })
            if (this.ds_danhsach_bohoso_selected.length == 0) {
                this.$toast.error("Bạn phải chọn phiếu để thực hiện")
                return
            }
            
            if (!this.KIEMTRA_PHIEU(0)) return;
            this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn xác nhận danh sách bộ hồ sơ không ?", {
                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
            }).then(async v => {
                if (v) {
                    try {
                        this.loading(true)
                        var huonggiao_ht = 0;
                        var danhsach = []
                        for (var i = 0; i < this.ds_danhsach_bohoso_selected.length; i++) {
                            var temp = {}
                            temp.bhs_id = this.ds_danhsach_bohoso_selected[i].bohs_id
                            temp.phieu_id = this.ds_danhsach_bohoso_selected[i].phieu_id
                            temp.phieu_id_cha = this.ds_danhsach_bohoso_selected[i].phieu_cha_id
                            temp.dvnext_id = this.ds_danhsach_bohoso_selected[i].dvnext_id
                            temp.dvgiao_id = this.ds_danhsach_bohoso_selected[i].donvi_nhan_id
                            temp.malhs = this.ds_danhsach_bohoso_selected[i].malo_hs
                            temp.lhs_id = this.ds_danhsach_bohoso_selected[i].lohs_id
                            temp.tthu_id = null
                            temp.dvtt_id = null
                            temp.nv_nhan = null
                            danhsach.push(temp)
                            huonggiao_ht = Number(this.ds_danhsach_bohoso_selected[i].huonggiao_id);
                        }

                        var ketqua = await this.xacnhan_bohoso(danhsach, huonggiao_ht, 2, "", 0)
                        if (String(ketqua) == "1") {
                            this.$toast.success("Thực hiện xác nhận thành công");
                            await this.TIMKIEM("", 0);
                        } else {
                            this.$toast.error(ketqua);
                        }
                    } catch (ex) {
                        this.$toast.error("Có lỗi khi xác nhận " + ex);
                    } finally {
                        this.loading(false)
                    }
                }
            });
        },
        async capnhat_file_hs(vkieu, vhoso_id, vloaifile_id, vurl, vfile_cn, vfile_clob, vbohs_id, vghichu) {
            var kq = "ERROR"
            try {
                var params = {
                    "kieu": vkieu,
                    "hoso_id": vhoso_id,
                    "loaifile_id": vloaifile_id,
                    "file_url": vurl,
                    "ghichu": vghichu,
                    "file_id": vfile_cn,
                    "files": vfile_clob,
                    "bohoso_id": vbohs_id
                }
                var response = await api.ur_29002_019_capnhat_file_hoso(this.axios, params);      
                
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    kq = "1";              
                }
            } catch (e) {
                console.log(e)
            }
            return kq
        },
        async fn_capnhat_file_hs(vkieu, vhoso_id, vloaifile_id, vurl, vfile_cn, vfile_clob, vbohs_id, vghichu, v_json_hoso_thuebao) {
            var kq = "ERROR"
            try {
                var params = {
                    "vkieu": vkieu,
                    "vhoso_id": vhoso_id,
                    "vloaifile_id": vloaifile_id,
                    "vfile_cn": vfile_cn,
                    "vbohs_id": vbohs_id,
                    "vurl": vurl,
                    "vghichu": vghichu,                    
                    "vfile_clob": JSON.stringify(vfile_clob),                    
                    "v_json_hoso_thuebao": JSON.stringify(v_json_hoso_thuebao)
                }
                var response = await api.fn_capnhat_file_hstb(this.axios, params);      
                
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    kq = "1";              
                } else {
                    kq = "ERROR: " + response.data.data
                }
            } catch (e) {
                console.log(e)
            }
            return kq
        },
        async sp_capnhat_file_hs_ghichu_tt(file_clob) {
            var kq = "ERROR"
            try {
                var params = {
                    "file_clob": file_clob
                }
                var response = await api.ur_29004_008_sp_capnhat_file_hs_ghichu_tt(this.axios, params);      
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    kq = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return kq
        },
        async XOA_FILE(quyen) {
            try {
                if (this.ds_chitiet_bohoso != null && this.ds_chitiet_bohoso.length > 0) {                  
                    if (this.ds_chitiet_bohoso_selected.length == 0) {
                        this.$toast.error("Bạn phải chọn ít nhất một file để xóa");
                    } else {
                        var ttph_dagiao = false;
                        var kt_tt = await this.fn_lay_soluong_phieu_bohs(this.vphieu_id_luoi)
                        if (kt_tt != "0" && !quyen) {
                            this.$toast.error("Phiếu " + this.vma_gd + " trạng thái bị thay đổi. Bạn hãy kiểm tra lại");
                            return;
                        }

                        if (kt_tt != "0") {
                            ttph_dagiao = true;
                        }

                        this.$bvModal.msgBoxConfirm("Bạn có chắc chắn xóa nhiều file hồ sơ được chọn không?", {
                                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                            }).then(async v => {
                                if (v) {
                                    try {
                                        this.loading(true)
                                        var vfile_clob = []
                                        var vfile_clob_ghichu = []
                                        for (var i = 0; i < this.ds_chitiet_bohoso_selected.length; i++) {
                                            var temp = {}
                                            temp.file_id = this.ds_chitiet_bohoso_selected[i].file_id
                                            temp.hdtb_id = this.ds_chitiet_bohoso_selected[i].hdtb_id
                                            temp.thuebao_id = this.ds_chitiet_bohoso_selected[i].thuebao_id
                                            temp.trangthai = this.ds_chitiet_bohoso_selected[i].trangthaihs_id
                                            vfile_clob.push(temp)

                                            var temp2 = {}
                                            temp2.FILE_ID = this.ds_chitiet_bohoso_selected[i].file_id
                                            vfile_clob_ghichu.push(temp2)
                                        }

                                        if (!ttph_dagiao) {
                                            var kq = await this.capnhat_file_hs(7, 0, Number(this.loaifile), "", this.vdl_file_id, vfile_clob, this.vbohs_id, this.txtGhiChuFile)
                                            if (String(kq).includes("ERROR")) {
                                                this.$toast.error(String(kq));
                                                return;
                                            }
                                            
                                            this.$toast.success("Xóa thành công file hồ sơ");
                                            await this.LOADFILE_HS();
                                        } else {
                                            if (!quyen) {
                                                this.$toast.error("Bạn không có quyền thêm /xoá file sau khi trạng thái đã bị thay đổi");
                                                return;
                                            }
                                                        
                                            var kq = await this.sp_capnhat_file_hs_ghichu_tt(vfile_clob_ghichu)                                       
                                            if (!isNaN(kq)) {
                                                this.$toast.success("Xóa thành công file hồ sơ");
                                                await this.LOADFILE_HS();
                                            } else {
                                                this.$toast.error("Xoá file thất bại " + kq);
                                            }
                                        }
                                    } catch (e) {
                                        this.$toast.error("Xoá file thất bại " + e);
                                    } finally {
                                        this.loading(false)
                                    }                             
                                }
                            });
                    }
                }
            } catch (ex) {
                this.$toast.error(ex.Message);
            }
        },
        closePanelThemfile() {
            this.btnClear_Click()
            this.$emit("closePanelThemfile")
        },
        async sp_capnhat_tuyenthu_bhs() {
            try {
                var params = {
                    "tuyenthu_id": this.tuyenthu,
                    "bohs_id": this.vbohs_id,
                    "phieu_id": this.vphieu_id_luoi
                }
                var response = await api.ur_29004_006_sp_capnhat_tuyenthu_bhs(this.axios, params);      
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    this.$toast.success("Cập nhật tuyến thu thành công");
                    await this.TIMKIEM("", 1);     
                } else {
                    this.$toast.error(response.data.message)
                }
            } catch (e) {
                console.log(e)
            }
        },
        async btnCapNhatTT_Click() {
            try {
                if (this.tuyenthu == null || String(this.tuyenthu) == "") {
                    this.$toast.error("Bạn chưa chọn tuyến thu");
                    return;
                }
                if (this.vbohs_id == -1) {
                    this.$toast.error("Bạn chưa chọn bộ hồ sơ để cập nhật tuyến thu");
                    return;
                }
                if (String(this.tuyenthutt_id) == "-1") {
                    this.$toast.error(this.vma_gd + " lập hợp đồng với kiểu hợp đồng khác kiểu hợp đồng tại nhà. Không thể cập nhật tuyến thu");
                    return;
                }
                
                this.$bvModal.msgBoxConfirm("Bạn có muốn cập nhật lại tuyến thu cho mã GD " + this.vma_gd, {
                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                    }).then(async v => {
                        if (v) {
                            try {
                                this.loading(true)
                                await this.sp_capnhat_tuyenthu_bhs()
                            } catch (e) {
                                console.log(e)
                                this.$toast.error("Cập nhật tuyến thu có lỗi " + e);
                            } finally {
                                this.loading(false)
                            }
                        }
                    });
            } catch (e) {
                console.log(e)
                this.$toast.error("Có lỗi khi cập nhật lại tuyến thu " + e);
            }
        },
        rowGridSelected(args){
            this.selectIndex=args.rowIndex
            console.log('selectIndex', this.selectIndex)
        },
        changeCheckDsHoSo(item, checked){
            if(this.ckbAuto){
                if(checked){
                    if (item.malo_hs != null && item.malo_hs != "") {
                        for(var i=0;i<this.ds_danhsach_bohoso.length;i++){
                            if(this.ds_danhsach_bohoso[i].malo_hs==item.malo_hs){
                                this.ds_danhsach_bohoso[i].ischeck='1'
                            }
                        }
                    } else {
                        const index=this.ds_danhsach_bohoso.findIndex(x=>x.bohs_id==item.bohs_id)
                        if (index>-1) {
                            this.ds_danhsach_bohoso[index].ischeck='1'
                        }
                    }
                }else{
                    if (item.malo_hs != null && item.malo_hs != "") {
                        for(var i=0;i<this.ds_danhsach_bohoso.length;i++){
                            if(this.ds_danhsach_bohoso[i].malo_hs==item.malo_hs){
                                this.ds_danhsach_bohoso[i].ischeck='0'
                            }
                        }
                    } else {
                        const index=this.ds_danhsach_bohoso.findIndex(x=>x.bohs_id==item.bohs_id)
                        if (index>-1) {
                            this.ds_danhsach_bohoso[index].ischeck='0'
                        }
                    }
                }
                this.$refs.ds_danhsach_bohoso.flagSelectedRowIndexes=[this.selectIndex]
                this.ds_danhsach_bohoso=this.ds_danhsach_bohoso.concat([])
            }else{
                const index=this.ds_danhsach_bohoso.findIndex(x=>x.bohs_id==item.bohs_id)
                if(index>-1){
                    if(checked){
                        this.ds_danhsach_bohoso[index].ischeck='1'
                    }else{
                        this.ds_danhsach_bohoso[index].ischeck='0'
                    }
                }
            }
            
        },
        columnTemplateCheckBox(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                          <div class="check-action">
                            <ejs-checkbox :value="isChecked" @change="changeCheck">
                            </ejs-checkbox>
                          </div>
                        `,
                        data() {
                            return {
                              data: {},
                              parent:parent
                            }
                        },
                        computed: {
                            isChecked(){
                              return this.data.ischeck=='1'
                            }
                        },
                        methods:{
                            changeCheck(value){
                                this.parent.changeCheckDsHoSo(this.data, value.checked)
                                
                            }
                        }
                    }
                }
            }
        },
        changeCheckHeader(checked){
            if(checked){
                for(var i=0;i<this.ds_danhsach_bohoso.length;i++){
                    this.ds_danhsach_bohoso[i].ischeck='1'
                }
            }else{
                for(var i=0;i<this.ds_danhsach_bohoso.length;i++){
                    this.ds_danhsach_bohoso[i].ischeck='0'
                }
            }
            this.$refs.ds_danhsach_bohoso.flagSelectedRowIndexes=[this.selectIndex]
            this.ds_danhsach_bohoso=this.ds_danhsach_bohoso.concat([])
        },
        columnTemplateCheckBoxHeader(parent){
            return function() {
                return { 
                    template: {
                        template: `
                          <div class="check-action">
                            <ejs-checkbox id="checkAllHeader"  @change="changeCheck">
                            </ejs-checkbox>
                          </div>
                        `,
                        data() {
                            return {
                              parent:parent,
                              data:{}
                            }
                        },
                        computed: {
                            
                        },
                        methods:{
                            changeCheck(value){                            
                                this.parent.changeCheckHeader(value.checked)
                            }
                        }
                    }
                }
            }
        }
    }
}
</script>