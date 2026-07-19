<template>
    <div>
        <div class="main-wrapper">
            <div class="breadcrumb-top">
                <div class="main-title">TRA CỨU VÀ THOÁI TRẢ HỢP ĐỒNG THI CÔNG</div>
                <ul class="breadcrumb">
                    <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                    <li class="breadcrumb-item"><a href="#">Bán chéo</a></li>
                    <li class="breadcrumb-item active">Tra cứu và thoái trả hợp đồng thi công</li>
                </ul>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a style="cursor: pointer;" @click.prevent="hienThiDanhSachHDKH">
                            <span class="icon one-search"></span> Tìm kiếm
                        </a>
                    </li>
                    <li v-show="hienthi_nut.tsbtnCapNhan.Visible">
                        <a @click.prevent="chapNhan" style="cursor: pointer;">
                            <span class="icon one-folder-back"></span> Thoái trả bán
                        </a>
                    </li>
                    <!-- <li v-show="hienthi_nut.hoso">
                        <a href="#">
                            <span class="icon one-print"></span> Hồ sơ
                        </a>
                    </li> -->
                    <li>
                        <a style="cursor: pointer;" @click.prevent="openFormThongTinBC">
                            <span class="icon -ap icon-info"></span> Thông tin BC
                        </a>
                    </li>
                    <li v-show="hienthi_nut.nhanhd">
                        <a style="cursor: pointer;" @click.prevent="btnNhanHD_Click">
                            <span class="icon one-prin"></span> Nhận HĐ
                        </a>
                    </li>
                    
                    <li v-show="hienthi_nut.chuyengiaohd.Visible" :class="{disabled: !hienthi_nut.chuyengiaohd.Enabled}">
                        <a @click.prevent="openFormChuyenHD" style="cursor: pointer;">
                            <span class="icon one-user-contact"></span> Chuyển giao HĐ
                        </a>
                    </li>
                    <li v-show="hienthi_nut.tsbtnTTNV.Visible" :class="{disabled: !hienthi_nut.tsbtnTTNV.Enabled}">
                        <a style="cursor: pointer;" @click.prevent="openFormThongTinNV">
                            <span class="icon nc-icon-glyph business_contacts"></span> Liên hệ nhân viên tỉnh bán
                        </a>
                    </li>
                    
                </ul>
            </div>
            <div class="page-content">
                <div class="box-form">
                    <div class="legend-title active">
                        <span class="icon fa fa-angle-up hand_click"></span> Thông tin tìm kiếm
                    </div>
                    <div class="grid-stack-box">
                        <div class="box-col col-sm-6 col-6" id="boxLeft">
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" v-model="thongtintimkiem.chk_loaihd" v-on:change="thayDoiCheck(1)">
                                                <span class="name">Loại HĐ/PL</span>
                                            </div>
                                        </div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <SelectExt v-model="thongtintimkiem.loaihd_id" :allowFiltering="true" dataTextField="TEN_LOAIHD"
                                                    dataValueField="LOAIHD_ID" :dataSource="thongtintimkiem.ds_loaihd" :class="{'disabled': thongtintimkiem.dis_loaihd}">
                                                </SelectExt>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w70">
                                            Kiểu HĐ
                                        </div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <SelectExt v-model="thongtintimkiem.kieu_hd" :allowFiltering="true" dataTextField="KIEU_HD"
                                                    dataValueField="ID" :dataSource="thongtintimkiem.ds_kieuhd" @change="cboKieuHD_SelectedIndexChanged">
                                                </SelectExt>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" v-model="thongtintimkiem.chk_dvvt" v-on:change="thayDoiCheck(4)">
                                                <span class="name">Dịch vụ</span>
                                            </div>
                                        </div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <SelectExt v-model="thongtintimkiem.dichvuvt_id" :allowFiltering="true" dataTextField="TEN_DVVT"
                                                    dataValueField="DICHVUVT_ID" :dataSource="thongtintimkiem.ds_dichvu" :class="{'disabled': thongtintimkiem.dis_dvvt}">
                                                </SelectExt>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w70">
                                            Mã GD
                                        </div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtintimkiem.ma_gd">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" v-model="thongtintimkiem.chk_ngayyc" v-on:change="thayDoiCheck(3)">
                                                <span class="name">Ngày YC</span>
                                            </div>
                                        </div>
                                        <div class="value">
                                            <vue-date style="margin-left:-5px" format="DD/MM/YYYY" type="date" v-model="thongtintimkiem.ngay_yc" :class="{'disabled': thongtintimkiem.dis_ngayyc}"></vue-date>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <vue-date format="DD/MM/YYYY" type="date" labelWidth="70" v-model="thongtintimkiem.denngay" label="Đến ngày" :class="{'disabled': thongtintimkiem.dis_ngayyc}"></vue-date>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="box-col col-sm-6 col-6" id="boxRight">
                            <div class="info-row">
                                <div class="key w140">
                                    Tra cứu theo
                                </div>
                                <div class="value">
                                    <div class="select-custom">
                                        <SelectExt v-model="thongtintimkiem.tracuu_theo" :allowFiltering="true" dataTextField="kieu_tk"
                                            dataValueField="dieukien" :dataSource="thongtintimkiem.ds_tracuutheo">
                                        </SelectExt>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w140">
                                            Thông tin tra cứu
                                        </div>
                                        <div class="value">
                                            <input @keyup.enter="hienThiDanhSachHDKH" type="text" class="form-control" v-model="thongtintimkiem.thongtin_tracuu">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w90">
                                            Mã HĐ-TTKD
                                        </div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtintimkiem.ma_hd_ttkd">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w140">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" v-model="thongtintimkiem.chk_trangthai" v-on:change="thayDoiCheck(2)">
                                        <span class="name">Trạng thái HĐ/PL</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <div class="select-custom">
                                        <SelectExt v-model="thongtintimkiem.tthd_id" :allowFiltering="true" dataTextField="TRANGTHAI_HD"
                                            dataValueField="TTHD_ID" :dataSource="thongtintimkiem.ds_trangthaihd" :class="{'disabled': thongtintimkiem.dis_trangthai}">
                                        </SelectExt>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="grid-stack-ver" style="height: 900px;">
                    <div class="box-form">
                        <div class="legend-title active">
                            <span class="icon fa fa-angle-up hand_click"></span> Danh sách hợp đồng/phụ lục khách hàng
                        </div>
                        <DataGrid v-bind:columns="ds_kh.headers" v-bind:dataSource="ds_kh.data"
                            :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true"
                            dataKeyField="hdkh_id" :showFilter="true"
                            @rowSelected="rowSelected_HDKH" >
                        </DataGrid>
                    </div>
                    <div class="box-form">
                        <div class="legend-title active">
                            <span class="icon fa fa-angle-up hand_click"></span> Danh sách thanh toán
                        </div>
                        <vue-table :loading="loading_ds_tt" :options="ds_tt" :config="config_ds_tt" />
                    </div>
                    <div class="box-form">
                        <div class="legend-title active">
                            <span class="icon fa fa-angle-up hand_click"></span> Danh sách thuê bao
                        </div>
                        <DataGrid v-bind:columns="ds_tb.headers" v-bind:dataSource="ds_tb.data"
                            :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true"
                            dataKeyField="hdtb_id" :showFilter="true"
                            @rowSelected="rowSelected_HDTB" >
                        </DataGrid>
                    </div>
                </div>
            </div>
        </div>

        <b-modal id="thoai-tra-bc" size="xl" body-class="p-0" hide-header hide-header-close hide-footer>
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon nc-icon-outline ui-1_settings"></span> Cập nhật thoái trả hợp đồng bán chéo</div>
                    <div class="close -ap icon-close" @click="hideModal('thoai-tra-bc')"></div>
                </div>
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a @click.prevent="capNhatThoaiTraBan" class="upload-button" style="cursor: pointer;">
                                <span class="icon one-save"></span> Cập nhật
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-12">
                                <div class="title mart10 text left">Nội dung</div>
                                <textarea class="form-control" v-model="$v.thoaitrabc.noidung.$model" v-bind:class="{'is-invalid': $v.thoaitrabc.noidung.$error}"></textarea>
                            </div>
                            <div class="error" v-if="!$v.thoaitrabc.noidung.maxLength">Vượt quá tối đa {{ $v.thoaitrabc.noidung.$params.maxLength.max }} ký tự</div>
                        </div>
                    </div>
                </div>
            </div>
        </b-modal>

        <b-modal id="thongtin-bc" size="xl" body-class="custom-modal-body" hide-header hide-header-close hide-footer>
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon one-notepad"></span> Thông tin chuyển thi công hợp đồng</div>
                    <div class="close -ap icon-close" @click="hideModal('thongtin-bc')" ></div>
                </div>
                <div class="popup-body">
                    <div class="row">
                        <ThongTinVanban ref="thongtinvanban" :parent="this" :propFromParent="p_hdkh_id" />
                        <ThongTinTrienkhai ref="thongtintrienkhai" :parent="this" :propFromParent="p_hdkh_id"/>
                    </div>
                    <ThongTinPhanChiaDoanhThu ref="thongtinphanchiadoanhthu" :parent="this" :propFromParent="p_hdkh_id"/>
                    <div class="box-form">
                        <!-- <div class="info-row">
                            <div class="key w80">Văn bản</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="thongtinbc.vanban">
                            </div>
                        </div> -->
                        <!-- <div class="info-row">
                            <div class="key w80">File văn bản</div>
                            <div class="value padt7"> -->
                                <!-- <a class="link" :href="thongtinbc.url" style="cursor: pointer;" @click.prevent="downloadFile(thongtinbc)">{{ thongtinbc.taifile }}</a> -->
                                <!-- <a class="link" :href="thongtinbc.url" style="cursor: pointer;" target="_blank">{{ thongtinbc.taifile }}</a>
                            </div>
                        </div> -->
                        <div class="info-row">
                            <vue-date format="DD/MM/YYYY" type="date" labelWidth="80" v-model="thongtinbc.ngayyc" label="Ngày YC"></vue-date>
                            <div class="value padt7 w30 nowrap">
                                (Ngày yêu cầu hoàn thành cho NET/VIP)
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Nội dung chuyển thi công</div>
                        <textarea v-model="thongtinbc.noidung" class="form-control" style="height: 140px;resize: none;"></textarea>
                    </div>
                </div>
            </div>
        </b-modal>

        <b-modal id="chuyen-hd-nv" size="xl" body-class="p-0" hide-header hide-header-close hide-footer>
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon one-user-focus"></span> Tra cứu thông tin nhân viên</div>
                    <div class="close -ap icon-close" @click="hideModal('chuyen-hd-nv')"></div>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="info-row">
                            <div class="key" style="width: 200px; font-weight: bold;">Tìm kiếm theo mã nhân viên:</div>
                            <div class="value w20">
                                <button class="btn btn-second" @click="searchNV">
                                    <span class="-ap one-search"></span>
                                </button>
                            </div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <input type="text" class="form-control" v-model="chuyenhdnv.timkiem" @keyup.enter="searchNV">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách nhân viên</div>
                        <DataGrid v-bind:columns="chuyenhdnv.dsnv.headers" v-bind:dataSource="chuyenhdnv.dsnv.data"
                            :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true"
                            dataKeyField="nhanvien_id" :showFilter="true"
                            @rowSelected="rowSelected_NV" >
                        </DataGrid>
                    </div>
                    <div class="group-buttons -bottom right">
                        <button class="btn btn-second nocorner" style="width: 120px;" @click.prevent="chonNhanVien">
                            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chọn
                        </button>
                        <button class="btn btn-second nocorner" style="width: 120px;" @click="hideModal('chuyen-hd-nv')">
                            <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Hủy bỏ
                        </button>
                    </div>
                </div>
            </div>
        </b-modal>

        <b-modal id="thongtin-nv-tinhban" size="xl" body-class="p-0" hide-header hide-header-close hide-footer>
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon one-notepad"></span>Thông tin nhân viên lập hợp đồng tỉnh bán</div>
                    <div class="close -ap icon-close" @click="hideModal('thongtin-nv-tinhban')"></div>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Thông tin nhân viên</div>
                        <div class="info-row">
                            <div class="key w90">Tên nhân viên</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="thongtinnv.ten_nv">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Số điện thoại</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="thongtinnv.so_dt">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Đơn vị</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="thongtinnv.ten_dv">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </b-modal>
    </div>
</template>
<style>
.hand_click { cursor: pointer; }
.modal.show .modal-dialog {
    top: calc(50vh - 130px) !important;    
}
</style>
<script>
import api from './api';
import { required, maxLength } from 'vuelidate/lib/validators'
import moment from 'moment'
//import '../../../../assets/vendor/jquery/split.js'
import ThongTinVanban from './components/ThongTinVanBan.vue'
import ThongTinTrienkhai from './components/ThongTinTrienKhai.vue'
import ThongTinPhanChiaDoanhThu from './components/ThongTinPhanChiaDoanhThu.vue'

export default {
    components: {
        ThongTinVanban,
        ThongTinTrienkhai,
        ThongTinPhanChiaDoanhThu,
    },

    data() {
        return {
            p_hdkh_id :0,
            hienthi_nut: {
                tsbtnCapNhan: { Visible: false, Enabled: false },
                nhanhd: false,
                //hoso: false,
                //inhd: false,
                //inpl: false,
                //inbb: false,
                //khuyenmai: false,
                //inphieutt: false,
                chuyengiaohd: { Visible: false, Enabled: false }, //tsbtnChuyenNV
                tsbtnTTNV: { Visible: false, Enabled: false }
            },
            thamso_md: '',
            donvi_id: 0,
            nhanvien_id: 0,
            kieu: 1, 
            kt_inhd: 0,
            donvi_dl_id: 0,
            phanvung_id: Number(this.$root.token.getPhanVungID()),
            kqSoAo: false,
            ma_tb: '',
            hdkh_id: 0,
            tinhban: 0,
            khachhang_id: 0,
            loaihd_id: 0,
            dichvuvt_id: 0,
            ngay_yc: '',
            denngay: '',
            tthd_id: 0,
            thongtintimkiem: {
                chk_loaihd: false,
                chk_dvvt: false,
                chk_trangthai: false,
                chk_ngayyc: false,
                dis_loaihd: true,
                dis_dvvt: true,
                dis_trangthai: true,
                dis_ngayyc: true,
                loaihd_id: 0,
                ds_loaihd: [],
                dichvuvt_id: 0,
                ds_dichvu: [],
                ngay_yc: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear(),
                kieu_hd: 0,
                ds_kieuhd: [{ID: 0, KIEU_HD: 'Yêu cầu bên bán'}, {ID: 1, KIEU_HD: 'Đã thoái trả'}, {ID: 2, KIEU_HD: 'Đã thi công'}],
                ma_gd: '',
                denngay: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear(),
                tracuu_theo: "tb.ma_tb = ':values'",
                ds_tracuutheo: [],
                thongtin_tracuu: '',
                ma_hd_ttkd: '',
                tthd_id: 0,
                ds_trangthaihd: []
            },
            ds_kh: {
                headers: [
                    { fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: false },
                    { fieldName: 'tentinh', headerText: 'Tỉnh', allowFiltering: true, allowSorting: false },
                    { fieldName: 'mst', headerText: 'Mã số thuế', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false },
                    { fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true, allowSorting: false },
                    { fieldName: 'so_dt', headerText: 'Số liên hệ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_loaihd', headerText: 'Loại HĐ/PL', allowFiltering: true, allowSorting: false },
                    { fieldName: 'nguoi_cn', headerText: 'Người cập nhật', allowFiltering: true, allowSorting: false }
                ],
                data: []
            },
            ds_tt: [],
            loading_ds_tt: false,
            config_ds_tt: [
                {
                    label: 'Mã thanh toán',
                    filterAble: true,
                    key: 'ma_tt'
                },
                {
                    label: 'Mã đại diện',
                    filterAble: true,
                    key: 'matb_dd'
                },
                {
                    label: 'Tên thanh toán',
                    filterAble: true,
                    key: 'ten_tt'
                },
                {
                    label: 'Địa chỉ thanh toán',
                    filterAble: true,
                    key: 'diachi_tt'
                }
            ],
            ds_tb: {
                headers: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false },
                    { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowSorting: false },
                    { fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: false },
                    { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false }
                ],
                data: []
            },
            hdtb_id: 0,
            sloaihd_id: 0,
            // khai báo biến trong form Thoái trả bán
            thoaitrabc: { 
                noidung: ''            },
            // Khai báo biến trong form Chuyển hợp đồng
            chuyenhd: {
                nhanvien_id: 0,
                donvi_id: 0,
            },
            // Khai báo biến trong form Thông tin bán chéo
            thongtinbc: {
                vanban: '',
                url: '',
                ngayyc: '',
                noidung: '',
                taifile: 'Tải File (Chưa có File nội dung)'
            },
            // Khai báo biến trong form Chuyển hợp đồng nhân vien
            chuyenhdnv: {
                dsnv: {
                    headers: [
                        { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true, allowSorting: false },
                        { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, allowSorting: false },
                        { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, allowSorting: false },
                        { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true, allowSorting: false }
                    ],
                    data: []
                },
                nhanvien_id: 0,
                donvi_id: 0,
                timkiem: ''
            },
            // Khai báo biến trong form Thông tin nhân viên tỉnh bán
            thongtinnv: {
                ten_nv: '',
                ten_dv: '',
                so_dt: '',
                hdtb_ban: 0
            },
            ma_gd_selected: ""
        }
    },
    mounted() {
        $('.legend-title').click(function(){
            if ($(this).hasClass('active')){
                $(this).next().slideUp()
                if ($(this).children().hasClass('fa-angle-up')) {
                    $(this).children().removeClass('fa-angle-up').addClass('fa-angle-down')
                }
                $(this).removeClass('active')
            }
            else {
                $(this).addClass('active') 
                if ($(this).children().hasClass('fa-angle-down')) {
                    $(this).children().removeClass('fa-angle-down').addClass('fa-angle-up')
                }       
                $(this).next().slideDown()
            }
        });
    },
    created() {
        this.khoiTao();
    },
    validations: 
    {
        thoaitrabc: {
            noidung: { required, maxLength: maxLength(1000) }
        }
    },
    watch: {
        'ds_tb.data'(val) {
            if (val && val.length > 0) {
                this.hienthi_nut.tsbtnTTNV.Visible = this.hienthi_nut.tsbtnTTNV.Enabled = this.hienthi_nut.chuyengiaohd.Enabled = true;
            } else {
                this.hienthi_nut.tsbtnTTNV.Visible = this.hienthi_nut.tsbtnTTNV.Enabled = this.hienthi_nut.chuyengiaohd.Enabled = false;
            }
        }
    },
    methods: {
        khoiTao() {
            this.layDs();
            if (this.kieu == 1) this.hienThiDanhSachHDKH();
            api.mapId_v2(this.axios, {
                "p_mats": "CHO_DOI_NHANVIEN_BANCHEO",
                "p_tents" : ""
                // "in_id_neo": "ten_ts",
                // "in_table": "admin.THAMSO_MD",
                // "in_dk": "where ma_ts ='CHO_DOI_NHANVIEN_BANCHEO'"
            }).then(res => {
                this.thamso_md = res.data.data ? res.data.data : '';
                if (this.thamso_md == "1" && this.thongtintimkiem.kieu_hd == 0) {
                    this.hienthi_nut.chuyengiaohd.Visible = true;
                }
            });
            this.cboKieuHD_SelectedIndexChanged()
        },
        layDs() {
            api.getDsTraCuuTheo(this.axios, {
                "p_nhom_id": 1
            }).then(res => {
                this.thongtintimkiem.ds_tracuutheo = res.data.data ? res.data.data : [];
                this.thongtintimkiem.tracuu_theo = "tb.ma_tb = ':values'";
                this.thongtintimkiem.ds_kieuhd = [{ID: 0, KIEU_HD: 'Yêu cầu bên bán'}, {ID: 1, KIEU_HD: 'Đã thoái trả'}, {ID: 2, KIEU_HD: 'Đã thi công'}],
                this.thongtintimkiem.kieu_hd = 0;
            });

            api.getDsTTHD(this.axios).then(res => {
                this.thongtintimkiem.ds_trangthaihd = res.data.data ? res.data.data : [];
                this.thongtintimkiem.tthd_id = 1;
            });

            api.getDsLoaiHD(this.axios).then(res => {
                this.thongtintimkiem.ds_loaihd = res.data.data ? res.data.data : [];
                this.thongtintimkiem.loaihd_id = 1;
            });

            api.getDsDichVu(this.axios).then(res => {
                this.thongtintimkiem.ds_dichvu = res.data.data ? res.data.data : [];
                this.thongtintimkiem.dichvuvt_id = 1;
            });
        },
        thayDoiCheck(num) {
            if (num == 1) this.thongtintimkiem.dis_loaihd = !this.thongtintimkiem.chk_loaihd;
            else if (num == 2) this.thongtintimkiem.dis_trangthai = !this.thongtintimkiem.chk_trangthai;
            else if (num == 3) this.thongtintimkiem.dis_ngayyc = !this.thongtintimkiem.chk_ngayyc;
            else if (num == 4) this.thongtintimkiem.dis_dvvt = !this.thongtintimkiem.chk_dvvt;
        },
        cboKieuHD_SelectedIndexChanged()
        {
            if (this.thongtintimkiem.kieu_hd == 0 || !this.thongtintimkiem.kieu_hd)
            {
                this.hienthi_nut.tsbtnCapNhan.Visible = true;
                if (this.thamso_md == "1")
                    this.hienthi_nut.chuyengiaohd.Visible = true;

            }
            else
            {
                this.hienthi_nut.chuyengiaohd.Visible = false;
                if (this.thongtintimkiem.kieu_hd == 1)
                {
                    this.hienthi_nut.tsbtnCapNhan.Visible = false;

                }
                else
                    if (this.thongtintimkiem.kieu_hd == 4)
                    {
                        this.hienthi_nut.tsbtnCapNhan.Visible = false;

                    }
                    else
                    {
                        this.hienthi_nut.tsbtnCapNhan.Visible = false;


                    }
            }

            // HienThiDanhSacHDKH();
            this.hienThiDanhSachHDKH()
        },
        hienThiDanhSachHDKH: async function() {
            // Bắt đầu xử lý khi check/uncheck checkbox trong thông tin tìm kiếm
            if (this.thongtintimkiem.chk_loaihd) this.loaihd_id = this.thongtintimkiem.loaihd_id;
            else this.loaihd_id = 0;
            if (this.thongtintimkiem.chk_dvvt) this.dichvuvt_id = this.thongtintimkiem.dichvuvt_id;
            else this.dichvuvt_id = 0;
            if (this.thongtintimkiem.chk_ngayyc) {
                this.ngay_yc = this.thongtintimkiem.ngay_yc;
                this.denngay = this.thongtintimkiem.denngay;
            } else {
                this.ngay_yc = '';
                this.denngay = '';
            }
            if (this.thongtintimkiem.chk_trangthai) this.tthd_id = this.thongtintimkiem.tthd_id;
            else this.tthd_id = 0;
            // Kết thúc xử lý khi check/uncheck checkbox trong thông tin tìm kiếm

            let dieukien = "";
            if (this.thongtintimkiem.thongtin_tracuu == "") {
                dieukien = "0";
            } else {
                dieukien = this.thongtintimkiem.tracuu_theo.replace(":values", this.thongtintimkiem.thongtin_tracuu);
            }
            if (this.thongtintimkiem.loaihd_id == 26 || this.kt_inhd == 1) {
                this.donvi_dl_id = this.$root.token.getDonViDuLieuID();
            }
            if (this.thongtintimkiem.ds_tracuutheo.find(x => x.dieukien === this.thongtintimkiem.tracuu_theo) && this.thongtintimkiem.ds_tracuutheo.find(x => x.dieukien === this.thongtintimkiem.tracuu_theo).kieu_tk == "Số ảo") {
                await api.searchTheoDKBanCheo(this.axios, {
                    "vphanvung_id": this.phanvung_id,
                    "vkieu": 0,
                    "vtextboxtc": this.thongtintimkiem.thongtin_tracuu,
                    "vsite_id": 0
                }).then(res => {
                    if (Number(res.data.error) == 200 && res.data.data) {
                        this.ma_tb = res.data.data[0].ma_tb;
                        this.kqSoAo = true;
                    } else {
                        this.kqSoAo = false;
                    }
                });

                if (!this.kqSoAo) {
                    await api.searchTheoDKBanCheo(this.axios, {
                        "vphanvung_id": this.phanvung_id,
                        "vkieu": 1,
                        "vtextboxtc": this.thongtintimkiem.thongtin_tracuu,
                        "vsite_id": 0
                    }).then(res => {
                        if (Number(res.data.error) == 200 && res.data.data) {
                            this.ma_tb = res.data.data[0].ma_tb;
                        }
                    });
                }
                dieukien = " tb.ma_tb = '" + this.ma_tb + "'";
                await api.searchHopDongTC(this.axios, {
                    "vphanvung_id": this.phanvung_id,
                    "vdieukien": dieukien,
                    "vloaihd_id": this.loaihd_id,
                    "vtthd_id": this.tthd_id,
                    "vdonvi_id": this.$root.token.getDonViID(),
                    "vnhanvien_id": this.$root.token.getNhanVienID(),
                    "vdonvi_dl_id": this.donvi_dl_id,
                    "vdichvuvt_id": this.dichvuvt_id,
                    "vtungay_yc": this.ngay_yc,
                    "vdenngay_yc": this.denngay,
                    "vin_hd": this.kt_inhd,
                    "vkieuhd": this.thongtintimkiem.kieu_hd
                }).then(res => {
                    this.ds_kh.data = res.data.data ? res.data.data : [];
                    this.dsKHFixNgayYC();
                });
            } else if (this.thongtintimkiem.ds_tracuutheo.find(x => x.dieukien === this.thongtintimkiem.tracuu_theo) && this.thongtintimkiem.ds_tracuutheo.find(x => x.dieukien === this.thongtintimkiem.tracuu_theo).kieu_tk == "Site ID") {
                if (this.thongtintimkiem.thongtin_tracuu == '') {
                    this.$toast.error("Bạn chưa nhập thông tin Site ID để tra cứu !");
                    return;
                }
                await api.searchTheoDKBanCheo(this.axios, {
                    "vphanvung_id": this.phanvung_id,
                    "vkieu": 2,
                    "vtextboxtc": this.thongtintimkiem.thongtin_tracuu,
                    "vsite_id": 1
                }).then(res => {
                    this.ds_kh.data = res.data.data ? res.data.data : [];
                    this.dsKHFixNgayYC();
                });
            } else if (this.thongtintimkiem.ds_tracuutheo.find(x => x.dieukien === this.thongtintimkiem.tracuu_theo) && this.thongtintimkiem.ds_tracuutheo.find(x => x.dieukien === this.thongtintimkiem.tracuu_theo).kieu_tk == "Mã HĐ TTKD") {
                if (this.thongtintimkiem.thongtin_tracuu == '') {
                    this.$toast.error("Bạn chưa nhập thông tin Mã hợp đồng TTKD để tra cứu !");
                    return;
                }
                await api.searchTheoDKBanCheo(this.axios, {
                    "vphanvung_id": this.phanvung_id,
                    "vkieu": 3,
                    "vtextboxtc": this.thongtintimkiem.thongtin_tracuu,
                    "vsite_id": 0
                }).then(res => {
                    this.ds_kh.data = res.data.data ? res.data.data : [];
                    this.dsKHFixNgayYC();
                });
            } else {
                await api.searchHopDongTC(this.axios, {
                    "vphanvung_id": this.phanvung_id,
                    "vdieukien": dieukien,
                    "vloaihd_id": this.loaihd_id,
                    "vtthd_id": this.tthd_id,
                    "vdonvi_id": this.$root.token.getDonViID(),
                    "vnhanvien_id": this.$root.token.getNhanVienID(),
                    "vdonvi_dl_id": this.donvi_dl_id,
                    "vdichvuvt_id": this.dichvuvt_id,
                    "vtungay_yc": this.ngay_yc,
                    "vdenngay_yc": this.denngay,
                    "vin_hd": this.kt_inhd,
                    "vkieuhd": this.thongtintimkiem.kieu_hd
                }).then(res => {
                    this.ds_kh.data = res.data.data ? res.data.data : [];
                    this.dsKHFixNgayYC();
                });
            }
            if (this.ds_kh.data && this.ds_kh.data.length > 0) {
                this.hdkh_id = this.ds_kh.data[0].hdkh_id;
                this.tinhban = this.ds_kh.data[0].tinh_ban;
                this.sloaihd_id = this.ds_kh.data[0].loaihd_id;
                // if (this.sloaihd_id == 2 || this.sloaihd_id == 1 || this.sloaihd_id == 24) {
                //     this.hienthi_nut.inhd = true;
                // } else {
                //     this.hienthi_nut.inhd = false;
                // }
                // if (this.sloaihd_id == 2 || this.sloaihd_id == 4 || this.sloaihd_id == 7 || this.sloaihd_id == 8 || this.sloaihd_id == 3 || this.sloaihd_id == 14) {
                //     this.hienthi_nut.inpl = true;
                // } else {
                //     this.hienthi_nut.inpl = false;
                // }
                // this.hienthi_nut.inbb = false;
                this.thongtintimkiem.ma_gd = this.ds_kh.data[0].ma_gd;

                this.loading_ds_tt = true;
                await this.hienThiDanhSacHDTT(this.hdkh_id);
                if (this.thongtintimkiem.kieu_hd == 1) {
                    await this.hienThiDanhSacHDTB(1, this.hdkh_id);
                } else {
                    await this.hienThiDanhSacHDTB(2, this.hdkh_id);
                }
            } else {
                this.ds_tt = [];
                this.ds_tb.data = [];
            }
        },
        rowSelected_HDKH: async function(args){
            console.log("rowSelected_HDKH=" + JSON.stringify(args));
            this.hdkh_id = args.data.hdkh_id;
            this.p_hdkh_id = args.data.hdkh_id;
            this.thongtintimkiem.ma_gd = args.data.ma_gd;
            this.khachhang_id = args.data.khachhang_id;
            this.tinhban = args.data.tinh_ban;
            await this.hienThiDanhSacHDTT(this.hdkh_id);
            if (this.thongtintimkiem.kieu_hd == 1) {
                await this.hienThiDanhSacHDTB(1, this.hdkh_id);
            } else {
                await this.hienThiDanhSacHDTB(2, this.hdkh_id);
            }
        },
        hienThiDanhSacHDTT: async function(hdkh_id) {
            await api.getDsHDTTTheoHDKHId(this.axios, hdkh_id).then(res => {
                this.ds_tt = res.data.data ? res.data.data : [];
                this.loading_ds_tt = false;
            });
        },
        hienThiDanhSacHDTB: async function(num, hdkh_id) {
            if (num == 1) {
                await api.getDsHDTBTraBanTheoHDKHId(this.axios, {
                    "vhdkh_id": hdkh_id
                }).then(res => {
                    this.ds_tb.data = res.data.data ? res.data.data : [];
                    if (res.data.data) {
                        this.hdtb_id = res.data.data[0].hdtb_id;
                    } else {
                        this.hdtb_id = 0;
                    }
                });
            } else if (num == 2) {
                await api.getDsHDTBTheoHDKHId(this.axios, {
                    "in_hdkh_id": hdkh_id
                }).then(res => {
                    this.ds_tb.data = res.data.data ? res.data.data : [];
                    if (res.data.data) {
                        this.hdtb_id = res.data.data[0].hdtb_id;
                    } else {
                        this.hdtb_id = 0;
                    }
                });
            }
        },
        rowSelected_HDTB: async function(args) {
            console.log("rowSelected_HDTB=" + JSON.stringify(args));
            this.hdtb_id = args.data.hdtb_id;
            let vdichvuvt_id = args.data.dichvuvt_id;
            if (vdichvuvt_id == 8 || vdichvuvt_id == 9 || vdichvuvt_id == 7) {
                let text = "";
                if (vdichvuvt_id == 8 || vdichvuvt_id == 7) {
                    let in_dk = "where hdtb_id = " + this.hdtb_id + " and phanvung_id=" + this.phanvung_id;
                    await api.mapId(this.axios, {
                        "in_id_neo": "ma_pl",
                        "in_table": "css.hdtb_mgwan",
                        "in_dk": in_dk
                    }).then(res => {
                        text = res.data.data ? res.data.data : '';
                    });
                } else {
                    let in_dk = "where hdtb_id = " + this.hdtb_id + " and daucuoi_id = 1 and phanvung_id=" + this.phanvung_id;
                    await api.mapId(this.axios, {
                        "in_id_neo": "ma_pl",
                        "in_table": "css.hdtb_tsl",
                        "in_dk": in_dk
                    }).then(res => {
                        text = res.data.data ? res.data.data : '';
                    });
                }
                if (text != "-1" && text != "")
                {
                    this.thongtintimkiem.ma_hd_ttkd = text;
                }
                else
                {
                    this.thongtintimkiem.ma_hd_ttkd = "";
                }
            } else {
                this.thongtintimkiem.ma_hd_ttkd = "";
            }
        },
        chapNhan() {
            if (this.thongtintimkiem.kieu_hd == 1) {
                this.$toast.error("Bạn chưa nhập thông tin Mã hợp đồng TTKD để tra cứu !");
            } else {
                this.$bvModal.msgBoxConfirm("Bạn muốn trả hợp đồng '" + this.thongtintimkiem.ma_gd + "' sang tỉnh bán", {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(!v) {
                        return;
                    }
                    let self = this;
                    //let in_dk1 = "where hdtb_id = " + this.hdtb_id + " and phanvung_id=" + this.phanvung_id;
                    let in_dk2 = "where hdkh_id = " + this.hdkh_id + " and phanvung_id=" + this.phanvung_id;
                    let /*num = 0,*/ a = 0;
                    // await api.mapId(this.axios, {
                    //     "in_id_neo": "tthd_id",
                    //     "in_table": "css.hd_thuebao",
                    //     "in_dk": in_dk1
                    // }).then(res => {
                    //     num = res.data.data ? Number(res.data.data) : 0;
                    // });
                    await api.mapId(this.axios, {
                        "in_id_neo": "nhanvien_id",
                        "in_table": "css.hd_khachhang",
                        "in_dk": in_dk2
                    }).then(res => {
                        a = res.data.data ? Number(res.data.data) : 0;
                    });
                    if (a != Number(this.$root.token.getNhanVienID())) {
                        this.$toast.error("Chỉ nhân viên tạo hợp đồng có thể thoái.");
                    } else {
                        await api.fnThoaiTraBan(this.axios, {
                            "sloaihd_id": this.sloaihd_id,
                            "vhdkh_id": this.hdkh_id,
                            "vhdtb_id": this.hdtb_id,
                            "vnhanvien_id": a,
                            "vphanvung_id": this.phanvung_id
                        }).then(async res => {
                            if (res.data.error_code === 'BSS-00000000' && Number(res.data.data) == 1) {
                                await this.openFormThoaiTraBan();
                                await this.hienThiDanhSachHDKH();
                            } else {
                                this.$toast.error(res.data.message);
                            }
                        }).catch(err => {
                            let str = err.data.message_detail ? err.data.message_detail : err.data.message;
                            self.$toast.error(str);
                        });
                    }
                });
            }
        },
        openFormThoaiTraBan: async function() {
            let in_dk = "where tinh_thicong = " + this.phanvung_id + " and hdtb_thicong=" + this.hdtb_id + " and phanvung_id=" + this.phanvung_id;
            await api.mapId(this.axios, {
                "in_id_neo": "nd_thoaitra",
                "in_table": "css.hd_bancheo",
                "in_dk": in_dk
            }).then(res => {
                this.thoaitrabc.noidung = res.data.data ? res.data.data : "";
                if (this.thoaitrabc.noidung.trim() == "-1") {
                    this.thoaitrabc.noidung = "";
                }
            }).catch(() => {
                this.thoaitrabc.noidung = "";
            });
            this.$bvModal.show("thoai-tra-bc");
        },
        capNhatThoaiTraBan: async function() {
            this.$v.$touch();
            if (this.$v.$invalid) {
                // this.$toast.error("Bạn hãy nhập nội dung thoái trả hợp đông, Tên, SĐT để tỉnh bán liên hệ.");
                return;
            } else {
                if (this.thoaitrabc.noidung == "-1") {
                    this.$toast.error("Bạn hãy nhập nội dung thoái trả hợp đông, Tên, SĐT để tỉnh bán liên hệ.");
                    return;
                } else {
                    // Cật nhật thoái trả hợp đồng
                    await api.capNhatThoaiTraHD(this.axios, {
                        "vhdtb_id": this.hdtb_id,
                        "vnoidung": this.thoaitrabc.noidung,
                        "vtinh_id": this.tinhban
                    }).then().catch();

                    // Thoái trả hợp đồng
                    await api.thoaiTraHD(this.axios, {
                        "vhdtbid": this.hdtb_id,
                        "vnoidung": this.thoaitrabc.noidung
                    }).then().catch();

                    let codulieu = false;
                    // Lấy ds hợp đồng bán chéo theo hợp đồng thi công
                    await api.getHDBCTheoHDTC(this.axios, {
                        "vhdtb_id": this.hdtb_id,
                        "vtinhtc": this.phanvung_id
                    }).then(res => {
                        if (res.data.error_code === 'BSS-00000000' && res.data.data && res.data.data.length > 0) {
                            codulieu = true;
                        } else {
                            codulieu = false;
                        }
                    }).catch(() => {
                        codulieu = false;
                    });

                    // Nếu có dữ liệu ds hợp đồng bán chéo theo hợp đồng thi công thì 
                    // 1) Lấy danh sách phiếu bán chéo VIP, NET, MEDIA
                    // 2) Cập nhật bán chéo từng phiếu trong danh sách phiếu bán chéo VIP, NET, MEDIA
                    if (codulieu) {
                        let ds_phieuvip = [];
                        await api.getPhieuVIP(this.axios, this.hdtb_id).then(res => {
                            ds_phieuvip = res.data.data ? res.data.data : [];
                        }).catch(() => {
                            ds_phieuvip = [];
                        });

                        if (ds_phieuvip && ds_phieuvip.length > 0) {
                            for (let phieu in ds_phieuvip) {
                                await api.bcThoaiUpdateVip(this.axios, {
                                    "vphieu_tinh_id": phieu.phieu_id,
                                    "vtinh_id": this.tinhban
                                }).then().catch();
                            }
                        }
                    }
                    this.hideModal('thoai-tra-bc');
                    this.$toast.success("Thoái trả hợp đồng thành công !");
                }
            }
        },
        openFormThongTinBC: async function() {
            let self = this;
            await api.getDsHDBCTheoLoai(this.axios, {
                "p_hdtb_id": this.hdtb_id,
                "p_loaihd_bancheo": 2
            }).then(res => {
                let dsttnv = res.data.data ? res.data.data : [];
                console.log('có data',dsttnv.length)
                if (dsttnv.length > 0) {
                    this.thongtinbc.noidung = dsttnv[0].nd_chuyenthicong;
                    this.thongtinbc.vanban = dsttnv[0].vanban;
                    //this.thongtinbc.url = "http://www.africau.edu/images/default/sample.pdf"; // Test
                    this.thongtinbc.url = dsttnv[0].url;
                    if (this.thongtinbc.url) {
                        this.thongtinbc.taifile = "Tải File"
                    } else {
                        this.thongtinbc.taifile = "Tải File (Chưa có File nội dung)"
                    }
                    this.$bvModal.show("thongtin-bc");
                    
                    // Tự thêm điều kiện này vì cảm thấy không có thông tin thì hiển thị popup làm gì cho phí thời gian
                    // if (this.thongtinbc.noidung || this.thongtinbc.vanban || this.thongtinbc.url) { 
                    //     this.$bvModal.show("thongtin-bc");
                    // } else {
                    //     this.$toast.error("Không có thông tin chuyển thi công hợp đồng"); // Tự thêm thông báo nếu không có thông tin
                    // }
                } else {
                    this.$toast.error("Không có thông tin chuyển thi công hợp đồng"); // Tự thêm thông báo nếu không có thông tin
                }
            }).catch(err => {
                self.$toast.error("Lỗi khi lấy thông tin chuyển thi công hợp đồng: " + err.data.message); // Tự thêm thông báo nếu lỗi khi lấy dữ liệu
            });
        },
        // Rất tiếc là hàm download file này bị lỗi CORS nên bỏ, gán url trực tiếp vào href thẻ a để tải văn bản về
        downloadFile (thongtinbc) { 
            let self = this;
            this.axios.get(thongtinbc.url, { responseType: 'blob' }).then(response => {
                const blob = new Blob([response.data], { type: 'application/pdf' });
                const link = document.createElement('a');
                link.href = URL.createObjectURL(blob);
                link.download = thongtinbc.vanban;
                link.click();
                URL.revokeObjectURL(link.href);
            }).catch(err => {
                self.$toast.error("Có lỗi khi tải file: " + err.data.message)
            });
        },
        openFormChuyenHD: async function() {
            let self = this;
            let kiemtra = false;
            await api.getChuyenNvBC(this.axios, {
                "ma_gd": this.thongtintimkiem.ma_gd,
                "phanvung_id": this.phanvung_id
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                    let temp = Number(res.data.data[0].count);
                    if (temp == 0) {
                        this.$toast.error("Chỉ có thể thực hiện khi trạng thái hợp đồng là mới tiếp nhận.");
                        kiemtra = false;
                    } else {
                        kiemtra = true;
                    }
                } else {
                    this.$toast.error("Có lỗi khi chuyển giao hợp đồng: " + res.data.message);
                    kiemtra = false;
                }
            }).catch((err) => {
                self.$toast.error("Có lỗi khi chuyển giao hợp đồng: " + err.data.message);
                kiemtra = false;
            });
            if (kiemtra) {
                this.$bvModal.show("chuyen-hd-nv");
            }
        },
        rowSelected_NV: async function(args) {
            //console.log("rowSelected_NV=" + JSON.stringify(args));
            this.chuyenhdnv.nhanvien_id = args.data.nhanvien_id;
            this.chuyenhdnv.donvi_id = args.data.donvi_id;
        },
        searchNV:async function(event) {
            if (event) {
                let self = this;
                await api.getLayThongTinNV(this.axios, this.chuyenhdnv.timkiem).then(async (res) => {
                    this.chuyenhdnv.dsnv.data = res.data.data ? res.data.data : [];
                }).catch(() => {
                    self.chuyenhdnv.dsnv.data = [];
                });
            }
        },
        chonNhanVien() {
            if (this.chuyenhdnv.nhanvien_id && this.chuyenhdnv.nhanvien_id > 0 && this.chuyenhdnv.donvi_id && this.chuyenhdnv.donvi_id > 0) {
                let self = this;
                api.bcChuyenHDNV(this.axios, {
                    "vdonvi_id": this.chuyenhdnv.donvi_id,
                    "vma_gd": this.thongtintimkiem.ma_gd,
                    "vnhanvien_id": this.chuyenhdnv.nhanvien_id,
                    "vphanvung_id": this.phanvung_id
                }).then(res => {
                    if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                        this.hienThiDanhSachHDKH();
                    } else {
                        this.$toast.error("Có lỗi khi chuyển giao hợp đồng cho nhân viên tỉnh bán: " + res.data.message);
                    }
                }).catch(err => {
                    self.$toast.error("Có lỗi khi chuyển giao hợp đồng cho nhân viên tỉnh bán: " + err.data.message);
                });
            } else {
                this.$toast.error("Bạn chưa chọn nhân viên cụ thể trong danh sách nhân viên!");
            }
        },
        openFormThongTinNV: async function() {
            let dsttnv = [];
            this.thongtinnv = {
                ten_nv: '',
                ten_dv: '',
                so_dt: '',
                hdtb_ban: 0
            }
            await api.getDsHDBCTheoLoai(this.axios, {
                "p_hdtb_id": this.hdtb_id,
                "p_loaihd_bancheo": 2
            }).then(res => {
                dsttnv = res.data.data ? res.data.data : [];
            }).catch(() => {
                dsttnv = [];
            });
            if (dsttnv.length > 0) {
                this.thongtinnv.hdtb_ban = dsttnv[0].hdtb_ban;
                this.tinhban = dsttnv[0].tinh_ban;
                if (this.phanvung_id > 0 && this.thongtinnv.hdtb_ban > 0)
                {
                    await api.getBCLayTTNVTinhBan(this.axios, {
                        "vbientam": "",
                        "vhdtb_id": this.thongtinnv.hdtb_ban,
                        "vtinhban_id": this.tinhban,
                        "vkieu": 1
                    }).then(res => {
                        if (res.data.error_code === 'BSS-00000000') {
                            let data = res.data.data ? res.data.data : [];
                            if (data.length > 0) {
                                this.thongtinnv.ten_nv = data[0].ten_nv;
                                this.thongtinnv.ten_dv = data[0].ten_dv;
                                this.thongtinnv.so_dt = data[0].so_dt;
                            }
                        }
                    }).catch();

                }
                this.$bvModal.show("thongtin-nv-tinhban");
            }
        },
        hideModal(str) {
            this.$bvModal.hide(str);

        },
        dsKHFixNgayYC() {
            if (this.ds_kh.data && this.ds_kh.data.length > 0) {
                for (let i=0;i<this.ds_kh.data.length;i++) {
                    this.ds_kh.data[i].ngay_yc = moment(this.ds_kh.data[i].ngay_yc).format('DD/MM/YYYY HH:mm:ss');
                }
                //console.log("ds_kh.data=" + JSON.stringify(this.ds_kh.data));
            }
        },

        btnNhanHD_Click(){
            if (this.ds_kh.data && this.ds_kh.data.length > 0) {
                this.loading_ds_tt = true;
                api.nhanHopDong(this.axios, 
                    {vnhanvien_id: this.$root.token.getNhanVienID(), 
                    vdonvi_id: this.$root.token.getDonViID(), 
                    vma_gd: this.thongtintimkiem.ma_gd }
                ).then(response =>{
                    const { error_code, data } = response.data;
                   
                    if (error_code === "BSS-00000000") {
                        this.hienThiDanhSachHDKH();
                        this.$toast.success('Nhận hợp đồng thành công!')
                    }
                    else{
                        this.$toast.error(`Có lỗi xaỷ ra: ${response.data.message}`, 'title')
                    }
                }).catch(err=>{
                    console.log(err)
                    this.$toast.error(`Có lỗi xaỷ ra: ${err}`, 'title')
                })
            }else{
                return 
            }
            //doing
           
        }
    }
}
</script>
<style>
/* CSS cho modal */
.custom-modal-body .modal-content {
    width: 90vw; /* Chiều rộng của modal */
    max-width: 90vw; /* Chiều rộng tối đa của modal */
    height: 90vh; /* Chiều cao của modal */
    max-height: 70vh; /* Chiều cao tối đa của modal */
    position: absolute; /* Vị trí tuyệt đối */
    top: 50%; 
    left: 50%; /* Đẩy modal sang bên phải 50% */
    transform: translate(-50%, -50%); 
    overflow: auto;
}
</style>
