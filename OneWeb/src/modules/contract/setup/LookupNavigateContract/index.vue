<template>
    <div>
        <div class="breadcrumb-top" v-if="!dulieu || dulieu.isPopup == false">
            <div class="main-title">TRA CỨU VÀ ĐIỀU HƯỚNG HỢP ĐỒNG</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Bán chéo</a></li>
                <li class="breadcrumb-item active">Tra cứu và điều hướng hợp đồng</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a class="hand_click" @click.prevent="timKiem">
                        <span class="icon one-search"></span> Tìm kiếm
                    </a>
                </li>
                <li v-show="hienthi_nut.capnhan">
                    <a @click.prevent="openFrmCapNhan" class="hand_click">
                        <span class="icon fa fa-check"></span> Chuyển thi công
                    </a>
                </li>
                <li v-show="hienthi_nut.chuyennet">
                    <a class="hand_click" title="Chuyển HD cho NET với kênh liên tỉnh" @click.prevent="chuyenNet">
                        <span class="icon nc-icon-glyph arrows-1_simple-right"></span> Chuyển NET
                    </a>
                </li>
                <li v-show="hienthi_nut.chigiaonet">
                    <a class="hand_click" title="Chỉ giao phiếu cho NET và không giao cho tỉnh" @click.prevent="tsbtnChiGiaoNet_Click">
                        <span class="icon nc-icon-glyph arrows-1_simple-right"></span> Chỉ giao NET
                    </a>
                </li>
                <li v-show="hienthi_nut.chigiaonet_tinh">
                    <a class="hand_click" title="Tỉnh chỉ giao NET" @click.prevent="tsbtnChiGiaoNet_tinh_Click">
                        <span class="icon nc-icon-glyph arrows-1_simple-right"></span> Tỉnh chỉ giao NET
                    </a>
                </li>
                <li v-show="hienthi_nut.giao_vnpt_it">
                    <a class="hand_click" title="Giao phiếu VNPT IT" @click.prevent="tsbtnGiaoVNPTI_Click">
                        <span class="icon nc-icon-glyph arrows-1_simple-right"></span> Giao phiếu VNPT-I
                    </a>
                </li>
                <li v-show="hienthi_nut.xoatb">
                    <a class="hand_click" @click.prevent="xoaTB">
                        <span class="icon one-phone-remove"></span> Xóa TB thi công
                    </a>
                </li>
                <li v-show="hienthi_nut.xemhs">
                    <a @click.prevent="openFrmFileHS" class="hand_click">
                        <span class="icon one-file-search1"></span> Xem file hồ sơ ĐT
                    </a>
                </li>
            </ul>
        </div>
        <div :class="dulieu && dulieu.isPopup ? 'modal-content' : 'page-content'">
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
                                                dataValueField="LOAIHD_ID" :dataSource="thongtintimkiem.ds_loaihd" :class="{'disabled': thongtintimkiem.dis_loaihd}" :disabled="thongtintimkiem.dis_loaihd">
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
                                            <SelectExt v-model="thongtintimkiem.kieuhd_id" :allowFiltering="true" dataTextField="KIEU_HD"
                                                dataValueField="ID" :dataSource="thongtintimkiem.ds_kieuhd" @change="chonKieuHD">
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
                                                dataValueField="DICHVUVT_ID" :dataSource="thongtintimkiem.ds_dichvu" :class="{'disabled': thongtintimkiem.dis_dvvt}" :disabled="thongtintimkiem.dis_dvvt">
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
                                        <vue-date style="margin-left:-5px" format="DD/MM/YYYY" type="date" v-model="thongtintimkiem.ngay_yc" :class="{'disabled': thongtintimkiem.dis_ngayyc}" :disable="thongtintimkiem.dis_ngayyc"></vue-date>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <vue-date format="DD/MM/YYYY" type="date" labelWidth="70" v-model="thongtintimkiem.denngay" label="Đến ngày" :class="{'disabled': thongtintimkiem.dis_ngayyc}" :disable="thongtintimkiem.dis_ngayyc"></vue-date>
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
                                    <SelectExt v-model="thongtintimkiem.tracuu_theo" :allowFiltering="true" dataTextField="KIEU_TK"
                                        dataValueField="DIEUKIEN" :dataSource="thongtintimkiem.ds_tracuutheo" :disabled="thongtintimkiem.dis_tracuu_theo">
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
                                        <input @keyup.enter="hienThiDanhSachHDKH" type="text" class="form-control" v-model="thongtintimkiem.thongtin_tracuu" :disabled="thongtintimkiem.dis_thongtin_tracuu">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">
                                        Mã HĐ-TTKD
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongtintimkiem.ma_hd_ttkd" :disabled="thongtintimkiem.dis_ma_hd_ttkd">
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
                        <span class="icon fa fa-angle-up hand_click"></span>Danh sách hợp đồng/phụ lục khách hàng
                    </div>
                    <DataGrid v-bind:columns="ds_kh.headers" v-bind:dataSource="ds_kh.data"
                        :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true"
                        dataKeyField="hdkh_id" :showFilter="true"
                        @rowSelected="rowSelected_HDKH">
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
                        @rowSelected="rowSelected_HDTB" @rowDoubleClicked="rowDoubleClicked_HDTB">
                    </DataGrid>
                </div>
            </div>
        </div>
        <FrmCapNhat ref="frmCapNhat" :vhdtbid="hdtb_id" :vtinhid="tt_nd.phanvung_id" :vhdkh_id="hdkh_id" :visible_nut_capnhat="frm_capnhat.visible_nut_capnhat" :vngayycht_netvip="frm_capnhat.ngayycht_netvip" :_vanban="frm_capnhat.vanban" :vnoidung="frm_capnhat.noidung" :vtieude="frm_capnhat.tieude" @capNhat="capNhatNDChuyenBC"/>
        <FrmXemHS ref="frmXemHS" :vhdtb_ban="hdtb_id"/>
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
import FrmCapNhat from './popup/FrmCapNhat'
import FrmXemHS from './popup/FrmXemHS'
import moment from 'moment'
export default {
    emits: ['onChuyenThiCongSuccess'],
    props: ['dulieu'],
    data() {
        return {
            kt_inhd: 0,
            ts_kt_tthdkhdn: 0,
            ts_giao_vip_net: 0,
            ts_update_qt_tsl: 0,
            kieu: 1,
            is_nv_net: false,
            kieu_tracuu: 1,
            homnay: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear() + ' ' + ((new Date()).getHours()>9 ? '' : '0') + (new Date()).getHours() + ':' + ((new Date()).getMinutes()>9 ? '' : '0') + (new Date()).getMinutes() + ':' + ((new Date()).getSeconds()>9 ? '' : '0') + (new Date()).getSeconds(),
            donvi_id: 0,
            nhanvien_id: 0,
            donvi_dl_id: 0,
            hdkh_id: 0,
            tinhban: 0,
            khachhang_id: 0,
            loaihd_id: 0,
            dichvuvt_id: 0,
            ngay_yc: '',
            denngay: '',
            tthd_id: 0,
            tinhtc: 0,
            loaitb_id: 0,
            vdichvuvt_id: 0,
            vtthd_id: 0,
            loaikenh_id: 0,
            hdtb_thicong: 0,
            ma_kh: '',
            ten_kh: '',
            diachi_kh: '',
            hdtb_id: 0,
            sloaihd_id: 0,
            daucuoi_id: 0,
            hdkh_tc_id: 0,
            hdtt_tc_id: 0,
            thuebao_id_tc: 0,
            thanhtoan_id_tc: 0,
            khachhang_id_tc: 0,
            rowSelect: {},
            kieuld_hd: 0,
            error_capnhatchuyentinh: false,
            error_msg_capnhatchuyentinh: '',
            obj_hdkh: {},
            dsHDBANCHEO: [],
            dsHDTB: [],
            dsHDKH: [],
            dsHDTT: [],
            dsHDTBCD: [],
            dsHDTBADSL: [],
            dsHDTBMGWAN: [],
            dsHDTBTSL: [],
            dsHDTBIMS: [],
            dsDK_DVGT: [],
            dsHDTBLYDOHUY: [],
            dsDCHDTB: [],
            dsTien_HDTB: [],
            ds_hdttlh: [],
            dsHDTB_LoaiIp: [],
            dsDIACHI: [],
            dsDCHDKH: [],
            dsDCHDTT: [],
            ds_hdlh: [],
            ds_hdkh_md: [],
            ds_hdkh_sub: [],
            ds_hdtb_hen: [],
            map_lh_tc_ban: [],
            tinh: {
                tinh_id: 0,
                ma_vung: '',
                donvi_id: 0,
                nhanvien_id: 0
            },
            hienthi_nut: {
                inpl: false,
                inbb: false,
                capnhan: true,
                xoatb: false,
                xemhs: false,
                chuyennet: false,
                chigiaonet: false,
                chigiaonet_tinh: false,
                giao_vnpt_it: true
            },
            thongtintimkiem: {
                chk_loaihd: false,
                chk_dvvt: false,
                chk_trangthai: false,
                chk_ngayyc: false,
                dis_loaihd: true,
                dis_dvvt: true,
                dis_trangthai: true,
                dis_ngayyc: true,
                dis_tracuu_theo: false,
                dis_thongtin_tracuu: false,
                dis_ma_hd_ttkd: false,
                loaihd_id: 0,
                ds_loaihd: [],
                dichvuvt_id: 0,
                ds_dichvu: [],
                ngay_yc: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear(),
                kieuhd_id: 0,
                ds_kieuhd: [{ID: 0, KIEU_HD: 'Chưa chuyển'}, {ID: 1, KIEU_HD: 'Đã Chuyển'}, {ID: 2, KIEU_HD: 'Đã Thi công'}, {ID: 3, KIEU_HD: 'HĐ trả từ thi công'}, ],
                ma_gd: '',
                denngay: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear(),
                tracuu_theo: 0,
                ds_tracuutheo: [],
                thongtin_tracuu: '',
                ma_hd_ttkd: '',
                tthd_id: 0,
                ds_trangthaihd: []
            },
            loaihopdong: {
                chuyen_quyen: 2,
                dat_moi: 1,
                ky_lai_hd_goc: 24,
                chamdut_sd: 4,
                thay_doi_dv: 7,
                thay_doi_tocdo_adsl: 8,
                di_chuyen: 3,
                doiso_tb: 14,
                chuyendoi_lh: 6,
                tiepnhan_datmoi: 26
            },
            dichvuvienthong: {
                megawan: 8,
                tsl: 9,
                metronet: 7,
                co_dinh: 1,
                ims: 11,
                dichvu_cntt: 15,
                hoinghi_truyenhinh: 13,
                antoan_baomat_tt: 14,
                trungtam_dulieu: 16
            },
            loaikenh: {
                noihat: 8
            },
            loaihinhtb: {
                hnth_ngn: 90,
                tdth_truyenhinh: 146
            },
            ds_kh: {
                headers: [
                    { fieldName: 'MA_GD', headerText: 'Mã giao dịch', allowFiltering: true, allowSorting: false },
                    { fieldName: 'NGAY_YC', headerText: 'Ngày yêu cầu', allowFiltering: true, allowSorting: false },
                    { fieldName: 'MA_KH', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: false },
                    { fieldName: 'TEN_KH', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false },
                    { fieldName: 'DIACHI_KH', headerText: 'Địa chỉ khách hàng', allowFiltering: true, allowSorting: false },
                    { fieldName: 'SO_DT', headerText: 'Số liên hệ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'TEN_LOAIHD', headerText: 'Loại HĐ/PL', allowFiltering: true, allowSorting: false },
                    { fieldName: 'NGUOI_CN', headerText: 'Người cập nhật', allowFiltering: true, allowSorting: false }
                ],
                data: []
            },
            ds_tt: [],
            loading_ds_tt: false,
            config_ds_tt: [
                {
                    label: 'Mã thanh toán',
                    filterAble: true,
                    key: 'MA_TT'
                },
                {
                    label: 'Mã đại diện',
                    filterAble: true,
                    key: 'MATB_DD'
                },
                {
                    label: 'Tên thanh toán',
                    filterAble: true,
                    key: 'TEN_TT'
                },
                {
                    label: 'Địa chỉ thanh toán',
                    filterAble: true,
                    key: 'DIACHI_TT'
                }
            ],
            ds_tb: {
                headers: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false },
                    { fieldName: 'tentinh', headerText: 'Tỉnh', allowFiltering: true, allowSorting: false },
                    { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowSorting: false },
                    { fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ngay_hoancong', headerText: 'Ngày hoàn công', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: false },
                    { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false },
                    { fieldName: 'loaikenh', headerText: 'Loại kênh', allowFiltering: true, allowSorting: false },
                    { fieldName: 'tt_bc', headerText: 'Trạng thái BC', allowFiltering: true, allowSorting: false },
                    { fieldName: 'url', headerText: 'URL', allowFiltering: true, allowSorting: false },
                ],
                data: []
            },
            tt_nd: {
                phanvung_id: Number(this.$root.token.getPhanVungID()),
                donvi_id: Number(this.$root.token.getDonViID()),
                nhanvien_id: Number(this.$root.token.getNhanVienID()),
                donvi_dl_id: Number(this.$root.token.getDonViDuLieuID()),
                nguoi_cn: this.$root.token.getUserName(),
                may_cn: 'localhost',
                ip_cn: '127.0.0.1'
            },
            // Khai báo biến trong form Cập nhật nội dung chuyển thi công hợp đồng
            frm_capnhat: {
                visible_nut_capnhat: true,
                ngayycht_netvip: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear(),
                vanban: '',
                noidung: '',
                tieude: 'Cập nhật nội dung chuyển thi công hợp đồng'
            },
            checkCT: true
        }
    },
    components: {
        FrmCapNhat,
        FrmXemHS
    },
    async mounted() {
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
        console.log('LookupNavigateContract > dulieu', this.dulieu)
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip = await this.$root.token.getIP();
        this.is_nv_net = this.dulieu && this.dulieu.is_nv_net ? this.dulieu.is_nv_net : (this.$route.query.is_nv_net ? this.$route.query.is_nv_net : false)
        await this.khoiTao();
        this.hienthi_nut.giao_vnpt_it = Number(this.tt_nd.phanvung_id) == 98 ? true : false
    },
    created() {},
    methods: {
        // API UR2.1.003_003
        // Xử lý hàm trong openFrmCapNhan()
        kiemTraNutChuyenTinhKHDN: async function() {
            let result = '';
            await api.checkNutChuyenTinhKHDN(this.axios, {
                "vhdkh_id": this.hdkh_id
            }).then(res => {
                if (res.data.data && res.data.data.length > 0) {
                    result = res.data.data;
                }
            });
            return result;
        },
        // Khảo sát UR2.1.003_003
        // Xử lý sự kiện khi click nút Chuyển thi công
        openFrmCapNhan: async function () {
            if (!this.ds_tb.data || this.ds_tb.data.length == 0) {
                this.$toast.error("Không có thuê bao!");
                return
            }
            if (this.thongtintimkiem.kieuhd_id == 1) {
                this.$toast.error("Hợp đồng này đã chuyển thi công");
                return
            } else {
                if (this.sloaihd_id == this.loaihopdong.chamdut_sd) {
                    if (this.vtthd_id != 1 && this.vtthd_id != 25) {
                        this.$toast.error("Hợp đồng Thanh lý không ở trạng thái Mới. Bạn không thể chuyển Tỉnh thi công !");
                        return;
                    }
                } else {
                    if (this.vtthd_id == 1) {
                        this.$toast.error("Hợp đồng đang chờ thanh toán. Bạn không thể chuyển Tỉnh thi công");
                        return; 
                    }
                }
                    
                let nguoi_chuyen = await this.kiemTraNutChuyenTinhKHDN();
                console.log('openFrmCapNhan > kiemTraNutChuyenTinhKHDN ts_kt_tthdkhdn, nguoi_chuyen', this.ts_kt_tthdkhdn, nguoi_chuyen)
                if (this.ts_kt_tthdkhdn == 1 && nguoi_chuyen == "AM_KHDN_TEST" && this.sloaihd_id != this.loaihopdong.chuyen_quyen) {
                    let rows = this.ds_tb.data.filter(x => (x.loaikenh_id != this.loaikenh.noihat && (x.dichvuvt_id == 7 || x.dichvuvt_id == 8 || x.dichvuvt_id == 26 || x.dichvuvt_id == 9 || x.dichvuvt_id == 25)) || x.loaitb_id == 134 || x.loaitb_id == 385);
                    console.log('openFrmCapNhan > kiemTraNutChuyenTinhKHDN rows, ds_tb.data', rows, this.ds_tb.data)
                    if (rows.length > 0) {
                        this.$toast.error("Thuê bao khác nội tỉnh hoặc là thuê bao Internet trực tiếp hãy ấn nút chuyển NET");
                        return;
                    }
                }
                this.$bvModal.msgBoxConfirm("Bạn muốn chuyển hợp đồng '" + this.thongtintimkiem.ma_gd + "' sang tỉnh thi công", {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(v => {
                    if(!v) {
                        return;
                    }                        
                    this.frm_capnhat.tieude = "Cập nhật nội dung chuyển thi công hợp đồng";
                    this.frm_capnhat.visible_nut_capnhat = true;
                    this.frm_capnhat.ngayycht_netvip = this.homnay;
                    this.frm_capnhat.noidung = "";
                    this.frm_capnhat.vanban = "";
                    this.$refs.frmCapNhat.showModal();
                })      
            }
        },
        // UR2.1.003_003
        // Xử lý sự kiện sau khi nhấn nut Cập nhật ở FrmCapNhat
        capNhatNDChuyenBC: async function(dulieu) {
            try {
                this.loading(true);
                if (!this.ds_tb.data || this.ds_tb.data.length == 0) {
                    this.$toast.error('Không có thuê bao!')
                    return
                }
                this.frm_capnhat.ngayycht_netvip = dulieu.ngayycht_netvip;
                if (dulieu.capnhat) {
                    this.error_msg_capnhatchuyentinh = ''
                    // region: giao phieu cho net khi cấp cáp
                    if (this.ts_kt_tthdkhdn == 1) {                        
                        for (let i=0; i < this.ds_tb.data.length; i++) {
                            let dichvuvt_temp = this.ds_tb.data[i].dichvuvt_id;
                            if (dichvuvt_temp == 8 || dichvuvt_temp == 7 || dichvuvt_temp == 26 || dichvuvt_temp == 9 || dichvuvt_temp == 25) {
                                let capngoai_vnpt = this.ds_tb.data[i].capngoai_vnpt;
                                let tb_id_thicong = this.ds_tb.data[i].thuebao_id_thicong;
                                if (capngoai_vnpt == 1 || tb_id_thicong == -1) {
                                    let daucuoi_id = this.ds_tb.data[i].daucuoi_id ? this.ds_tb.data[i].daucuoi_id : 0;
                                    await this.banCheoChiNetThiCongV2(this.hdtb_id, daucuoi_id); // Hàm này bị lỗi nên cũng không biết đầu ra thế nào, lỗi này có đề cập trong mô tả DB và API của mã UR2.1.003_003
                                }
                            }
                        }
                    }
                    // endregion
                    // region: chuyển hợp đồng đi
                    if (this.obj_hdkh.LOAIHD_ID == this.loaihopdong.dat_moi) {
                        let dicHDKH = {}
                        for (let i=0; i < this.ds_tb.data.length; i++) {
                            // console.log('%cThis is a colored message!', 'color: red; font-weight: bold;', "ABC123");
                            this.tinh = await this.layTinhThiCong(this.ds_tb.data[i].tinh_thicong);

                            if (!dicHDKH.hasOwnProperty(this.tinh.tinh_id )){
                                this.hdkh_tc_id = await this.taoKey(this.tinh.tinh_id, "HD_KHACHHANG");
                                dicHDKH[this.tinh.tinh_id] = this.hdkh_tc_id;
                                var result = true
                                result = await this.tao_HDKH(this.hdkh_id); // UR2.1.003_004
                                if (!result) { 
                                    console.log('tao_HDKH', result)
                                    this.error_capnhatchuyentinh = true
                                    return false 
                                }
                                result = await this.tao_HDKH_SUB(this.hdkh_id, this.hdkh_tc_id); // UR2.1.003_006: Trong code C# mới nhất không có dòng này :D
                                if (!result) { 
                                    console.log('tao_HDKH_SUB', result)
                                    this.error_capnhatchuyentinh = true
                                    return false 
                                }
                                result = await this.tao_HDTT(); // UR2.1.003_007 
                                if (!result) { 
                                    console.log('tao_HDTT', result)
                                    this.error_capnhatchuyentinh = true
                                    return false 
                                }
                                result = await this.taoDuLieu_HD_LIENHE(this.hdkh_tc_id, this.tinh.tinh_id); // UR2.1.003_005
                                if (!result) { 
                                    console.log('taoDuLieu_HD_LIENHE', result)
                                    this.error_capnhatchuyentinh = true
                                    return false 
                                }
                                result = await this.tao_HDBancheo(this.tinh.tinh_id, this.hdkh_id); // UR2.1.003_008
                                if (!result) { 
                                    console.log('tao_HDBancheo', result)
                                    this.error_capnhatchuyentinh = true
                                    return false 
                                }
                                if (this.dsHDBANCHEO.length > 0) {
                                    result = await this.tao_DIACHIHD(this.dsHDBANCHEO[0].HDTB_BAN); // UR2.1.003_022
                                    if (!result) { 
                                        console.log('tao_DIACHIHD', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    result = await this.tao_HDTB_HEN(this.tinh.donvi_id, this.tinh.nhanvien_id); // UR2.1.003_023
                                    if (!result) { 
                                        console.log('tao_HDTB_HEN', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    if(this.checkCT == true){
                                        await this.capNhatTinh(); // UR2.1.003_027
                                    }
                                }
                            }
                        }
                    } else {
                        if (this.ds_tb.data && this.ds_tb.data.length > 0) {
                            for (let i=0; i<this.ds_tb.data.length; i++) {
                                //this.dsDIACHI = [];  Edit 02/08/2023
                                let tb_id_thicong = this.ds_tb.data[i].thuebao_id_thicong;
                                if (tb_id_thicong != -1) {
                                    this.tinh = await this.layTinhThiCong(this.ds_tb.data[i].tinh_thicong);
                                    this.hdkh_tc_id = await this.taoKey(this.ds_tb.data[i].tinh_thicong, "HD_KHACHHANG");
                                    var result = true
                                    result = await this.tao_HDKH_v2(i); // UR2.1.003_024
                                    if (!result) { 
                                        console.log('tao_HDKH_v2', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    result = await this.tao_HDKH_SUB(this.hdkh_id, this.hdkh_tc_id);
                                    if (!result) { 
                                        console.log('tao_HDKH_SUB', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    result = await this.tao_HDTT_v2(); // UR2.1.003_026
                                    if (!result) { 
                                        console.log('tao_HDTT_v2', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    result = await this.taoDuLieu_HD_LIENHE_v2();
                                    if (!result) { 
                                        console.log('taoDuLieu_HD_LIENHE_v2', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    let daucuoi_id = this.ds_tb.data[i].daucuoi_id;
                                    result = await this.tao_HDBancheo_v2(this.ds_tb.data[i].hdtb_id, daucuoi_id); // UR2.1.003_025
                                    if (!result) { 
                                        console.log('tao_HDBancheo_v2', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    result = await this.tao_DIACHIHD_v2(this.dsHDBANCHEO[0].HDTB_BAN);
                                    if (!result) { 
                                        console.log('tao_DIACHIHD_v2', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    result = await this.tao_HDTB_HEN_v2(this.tinh.donvi_id, this.tinh.nhanvien_id);
                                    if (!result) { 
                                        console.log('tao_HDTB_HEN_v2', result)
                                        this.error_capnhatchuyentinh = true
                                        return false 
                                    }
                                    if (i == 0 || ((this.ds_tb.data[i].dichvuvt_id == 9 || this.ds_tb.data[i].dichvuvt_id == 25) && this.ds_tb.data[0].tinh_thicong != this.ds_tb.data[1].tinh_thicong) || 
                                        (this.ds_tb.data[i].dichvuvt_id != 9 && this.ds_tb.data[i].dichvuvt_id != 25)) {
                                        await this.capNhatTinh(); // UR2.1.003_027
                                    } //else break;
                                }
                            }
                        }
                    }
                    //endregion
                }
            }
            catch(ex) {
                console.log('capNhatNDChuyenBC', ex)
                if (ex.data && ex.data.message_detail) {
                    this.$toast.error("Có lỗi xảy ra khi chuyển tỉnh: " + ex.data.message_detail)
                } else {
                    this.$toast.error("Có lỗi xảy ra khi chuyển tỉnh: " + JSON.stringify(ex))
                }                
                this.error_capnhatchuyentinh = true;
            }
            finally {
                if (!this.error_capnhatchuyentinh) {
                    //wtf
                    this.$emit('onChuyenThiCongSuccess')
                    this.$toast.success("Đã chuyển thành công hợp đồng");
                } else {
                    // console.log('capNhatNDChuyenBC', this.error_msg_capnhatchuyentinh);
                    // this.$toast.error("Có lỗi xảy ra khi chuyển tỉnh " + this.error_msg_capnhatchuyentinh)
                }
                await this.hienThiDanhSachHDKH(); // UR2.1.003_002
                this.loading(false);
            }
        },
        // API UR2.1.003_003
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu) => Hàm đang lỗi, DB+API bảo ko cần test hàm này
        banCheoChiNetThiCongV2: async function(hdtb_id, daucuoi_id) {
            let result = '';
            await api.bcChiNetThiCongV2(this.axios, {
                "vhdtb_id": hdtb_id,
                "vdaucuoi_id": Number(daucuoi_id)
            }).then(res => {
                if (res.data.data && res.data.data.length > 0) {
                    result = res.data.data;
                }
            });
            return result;
        },
        // API UR2.1.003_003 => Thực tế mô tả API ko có, phải nhờ Nhật Anh cung cấp ngoài
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        layTinhThiCong: async function(tinhtc) {
            let result = {};
            let self = this;
            await api.getTinhThiCong(this.axios, {
                 "tinhthicong_id": tinhtc
            }).then(res => {
                let ds = res.data.data ? res.data.data : [];
                if (ds.length > 0) {
                    result = ds[0];
                }
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        // API UR2.1.003_003
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        taoKey: async function(tinhthicong_id, keyname) {
            let result = '';
            let self = this;
            await api.getKey(this.axios, {
                "tinhthicong_id": tinhthicong_id,
                "keyname": keyname
            }).then(res => {
                result = res.data.data ? res.data.data : '';
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        // API 1. UR2.1.003_004
        // Xử lý hàm getHDKhachHang trong tao_HDKH(hdkh_id)
        getHDKhachHang: async function(hdkh_id) {
            let result = [];
            let self = this;
            await api.getHDKhachHang(this.axios, hdkh_id).then(res => {
                result = res.data.data ? res.data.data : [];
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        // API 2. UR2.1.003_004
        // Xử lý hàm trong tao_HDKH(hdkh_id)
        // MapID
        mapId: async function(id_neo, table, dk) {
            let result;
            let self = this;
            await api.mapId(this.axios, {
                "id_neo": id_neo,
                "table": table,
                "dk": dk
            }).then(res => {
                result = res.data.data ? res.data.data : null;
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        fn_tt_hd_bancheo: async function(type, param, param1) {
            let result;
            let self = this;
            var input = {
                type: type, 
                param: param, 
                param1: param1
            }
            await api.fn_tt_hd_bancheo(this.axios, input).then(res => {
                result = res.data.data ? res.data.data : null;
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        fn_tt_hdtb_mgwan: async function(type, param) {
            let result;
            let self = this;
            var input = {
                type: type, 
                param: param
            }
            await api.fn_tt_hdtb_mgwan(this.axios, input).then(res => {
                result = res.data.data ? res.data.data : null;
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        fn_tt_hdtb_tsl: async function(type, param) {
            let result;
            let self = this;
            var input = {
                type: type, 
                param: param
            }
            await api.fn_tt_hdtb_tsl(this.axios, input).then(res => {
                result = res.data.data ? res.data.data : null;
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        // API 4. UR2.1.003_004
        // Xử lý hàm trong tao_HDKH(hdkh_id)
        sinhMaGD: async function(tinhthicong_id, loaihd_id) {
            let result = '';
            let self = this;
            await api.sinhMaGD(this.axios, {
                "tinhthicong_id": tinhthicong_id,
                "vloaihd_id": loaihd_id
            }).then(res => {
                result = res.data.data ? res.data.data : '';
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        // API 5. UR2.1.003_004
        // Xử lý hàm trong tao_HDKH(hdkh_id)
        sinhMaHD: async function(tinhthicong_id, vdonvi_id) {
            let result = '';
            let self = this;
            await api.sinhMaHD(this.axios, {
                 "tinhthicong_id": tinhthicong_id,
                 "vdonvi_id": vdonvi_id
            }).then(res => {
                result = res.data.data ? res.data.data : '';
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        // API 6. UR2.1.003_004
        // Xử lý hàm trong tao_HDKH(hdkh_id)
        sinhMaKH: async function(tinhthicong_id, vdonvi_id) {
            let result = '';
            let self = this;
            await api.sinhMaKH(this.axios, {
                "tinhthicong_id": tinhthicong_id,
                "vdonvi_id": vdonvi_id
            }).then(res => {
                result = res.data.data ? res.data.data : '';
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        // API 8. UR2.1.003_004
        // Xử lý hàm trong tao_HDKH(hdkh_id)
        getKhachHangId: async function(khachhangbanid, tinhthicong_id) {
            let result = 0;
            let self = this;
            await api.lay_khachhangid_thicong(this.axios, {
                "khachhangbanid": khachhangbanid,
                "tinhthicong_id": tinhthicong_id
            }).then(res => {
                result = res.data.data && res.data.data.length > 0 ? Number(res.data.data[0].khachhang_id) : 0;
            }).catch(err => {
                self.$toast.error(err.data.message_detail);
            });
            return result;
        },
        // Xử lý hàm trong tao_HDKH(hdkh_id)
        copyRowHDKH(row) {
            let rowHDKH = {
                HDKH_ID: row.hdkh_id,
                NGAYLAP_HD:row.ngaylap_hd,
                NGAYHETHAN_GT: row.ngayhethan_gt ? moment(row.ngayhethan_gt).format('DD/MM/YYYY HH:mm:ss') : null,
                TNC1_ID: row.tnc1_id,
                KENHTN_ID: row.kenhtn_id,
                NGAY_CN: row.ngay_cn ? moment(row.ngay_cn).format('DD/MM/YYYY HH:mm:ss') : null,
                KENHTN_ID: row.noicap,
                HTTTHI_ID: row.httthi_id,
                MAY_CN: row.may_cn,
                NHANVIENGT_ID: row.nhanviengt_id,
                TEN_KH: row.ten_kh,
                NHANVIEN_ID: row.nhanvien_id,
                QUOCTICH_ID: row.quoctich_id,
                MST: row.mst,
                DONHANG_ID: row.donhang_id,
                NGAY_SN: row.ngay_sn ? moment(row.ngay_sn).format('DD/MM/YYYY HH:mm:ss') : null,
                HOKHAU: row.hokhau,
                PHANLOAIKH_ID: row.phanloaikh_id,
                MA_KH_WEB: row.ma_kh_web,
                MST_KH: row.mst_kh,
                PHANVUNG_ID: row.phanvung_id,
                GHICHU: row.ghichu,
                NGUOILAP_HD: row.nguoilap_hd,
                MA_HD: row.ma_hd,
                KIEUHD_ID: row.kieuhd_id,
                NOICAP_HK: row.noicap_hk,
                STK: row.stk,
                KENHTN_ID: row.hdkh_cha_id,
                TBTL_ID: row.tbtl_id,
                SO_PL: row.so_pl,
                SO_GT: row.so_gt,
                KIEUTN_ID: row.kieutn_id,
                TNC4_ID: row.tnc4_id,
                SO_FAX: row.so_fax,
                MA_DUAN: row.ma_duan,
                NGAYCAP: row.ngaycap ? moment(row.ngaycap).format('DD/MM/YYYY HH:mm:ss') : null,
                TNC3_ID: row.tnc3_id,
                NGUOI_CN: row.nguoi_cn,
                GIOITINH: row.gioitinh,
                MA_GD: row.ma_gd,
                MA_KH: row.ma_kh,
                PHIEUCSKH_ID: row.phieucskh_id,
                YEUCAU_ID: row.yeucau_id,
                MUCDO_ID: row.mucdo_id,
                LOAIKH_ID: row.loaikh_id,
                DACTRUNG: row.dactrung,
                SO_DT: row.so_dt,
                HDKH_ID_HNI: row.hdkh_id_hni,
                EMAIL: row.email, // Điều chỉnh lại theo yêu cầu của anh Tuấn Anh 3/7/2024
                DIACHI_NDD: row.diachi_ndd,
                LOAIHD_ID: row.loaihd_id,
                WEBSITE: row.website,
                TNC2_ID: row.tnc2_id,
                CHUCDANH_DD: row.chucdanh_dd,
                KHACHHANG_ID: row.khachhang_id,
                NGAY_YC: row.ngay_yc ? moment(row.ngay_yc).format('DD/MM/YYYY HH:mm:ss') : null,
                BOSUNGTB_ID: row.bosungtb_id,
                DONVI_ID: row.donvi_id,
                NGANHNGHE: row.nganhnghe_id,
                KHLON_ID: row.khlon_id,
                LOAIGT_ID: row.loaigt_id,
                NGAY_HK: row.ngay_hk ? moment(row.ngay_hk).format('DD/MM/YYYY HH:mm:ss') : null,
                NGANHANG_ID: row.nganhang_id,
                KH_KT3: row.kh_kt3,
                DANTOC_ID: row.dantoc_id,
                IP_CN: row.ip_cn,
                DIACHI_KH: row.diachi_kh,
                CTV_ID: row.ctv_id,
                LOAINC_ID: row.loainc_id,
                NGUOI_DD: row.nguoi_dd,
                KH_TIEMNANG: row.kh_tiemnang,
                LOAINC_IC: row.loainc_ic
            };
            return rowHDKH;
        },
        // API 9. UR2.1.003_004
        // Xử lý hàm trong tao_HDKH(hdkh_id)
        getDBTheoThueBaoId: async function(tinhthicong_id, thuebao_id, dichvuvt_id, donvi_id, loaihdbc) {
            let result = [];
            await api.getDBTheoThueBaoId(this.axios, {
                "tinhthicong_id": tinhthicong_id,
                "thuebao_id": thuebao_id,
                "dichvuvt_id": dichvuvt_id,
                "donvi_id": donvi_id,
                "loaihdbc": loaihdbc
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 10. UR2.1.003_004
        // Xử lý hàm trong tao_HDKH(hdkh_id)
        getAllDbKhachHang: async function(tinhthicong_id, khachhang_id_tc) {
            let result = [];
            await api.getAllDbKhachHang(this.axios, {
                "tinhthicong_id": tinhthicong_id,
                "khachhang_id_tc": khachhang_id_tc
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_004
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDKH: async function(hdkh_id) {
            try {
                this.dsHDKH = [];
                let dts = await this.getHDKhachHang(hdkh_id);
                let rowHDKH = {};
                // this.thuebao_id_tc = await this.mapId("thuebao_id_thicong", "css.hd_bancheo", "where hdtb_ban = " + this.hdtb_id + " and phanvung_id=" + this.tt_nd.phanvung_id);
                // this.thanhtoan_id_tc = await this.mapId("thanhtoan_id_tc", "css.hd_bancheo", "where hdtb_ban = " + this.hdtb_id+ " and phanvung_id=" + this.tt_nd.phanvung_id);
                this.thuebao_id_tc = await this.fn_tt_hd_bancheo(5, this.hdtb_id, this.tt_nd.phanvung_id)
                this.thanhtoan_id_tc = await this.fn_tt_hd_bancheo(4, this.hdtb_id, this.tt_nd.phanvung_id)
                if (dts.length > 0) {
                    if (!dts[0].khachhang_id || dts[0].khachhang_id == 0) {
                        rowHDKH = this.copyRowHDKH(dts[0]);
                        rowHDKH.LOAIHD_ID = dts[0].loaihd_id;
                        rowHDKH.MA_GD = await this.sinhMaGD(this.tinh.tinh_id, dts[0].loaihd_id);
                        rowHDKH.MA_HD = await this.sinhMaHD(this.tinh.tinh_id, this.tinh.donvi_id);
                        rowHDKH.MA_KH = await this.sinhMaKH(this.tinh.tinh_id, this.tinh.donvi_id);
                        rowHDKH.CTV_ID = null;
                        rowHDKH.NGANHANG_ID = null;
                    } else {
                        this.khachhang_id_tc = await this.getKhachHangId(dts[0].khachhang_id, this.tinh.tinh_id);
                        if (!this.khachhang_id_tc || this.khachhang_id_tc == 0) {
                            rowHDKH = this.copyRowHDKH(dts[0]);
                            rowHDKH.KHACHHANG_ID = null;
                            rowHDKH.MA_HD = await this.sinhMaHD(this.tinh.tinh_id, this.tinh.donvi_id);
                            rowHDKH.MA_KH = await this.sinhMaKH(this.tinh.tinh_id, this.tinh.donvi_id);
                            rowHDKH.NGANHANG_ID = null;
                        } else {
                            if (dts[0].loaihd_id != this.loaihopdong.dat_moi) {
                                let ds = await this.getDBTheoThueBaoId(this.tinh.tinh_id, this.thuebao_id_tc, this.vdichvuvt_id, 0, 2);
                                if (ds.length > 0) {
                                    rowHDKH = this.copyRowHDKH(ds[0]);
                                    rowHDKH.KHACHHANG_ID = null;
                                }
                            } else {
                                let dbkh_tc = await this.getAllDbKhachHang(this.tinh.tinh_id, this.khachhang_id_tc);
                                if (dbkh_tc.length > 0) {
                                    rowHDKH = this.copyRowHDKH(dbkh_tc[0]);
                                    rowHDKH.KHACHHANG_ID = this.khachhang_id_tc;
                                    rowHDKH.MA_HD = dbkh_tc[0].ma_hd;
                                    rowHDKH.MA_KH = dbkh_tc[0].ma_kh;
                                } else {
                                    rowHDKH = this.copyRowHDKH(dts);
                                    rowHDKH.KHACHHANG_ID = null;
                                    rowHDKH.MA_HD = await this.sinhMaHD(this.tinh.tinh_id, this.tinh.donvi_id);
                                    rowHDKH.MA_KH = await this.sinhMaKH(this.tinh.tinh_id, this.tinh.donvi_id);
                                }
                            }
                        }
                        rowHDKH.LOAIHD_ID = dts[0].loaihd_id;
                        rowHDKH.MA_GD = await this.sinhMaGD(this.tinh.tinh_id, dts[0].loaihd_id);
                    }
                    rowHDKH.NGAY_YC = this.homnay;
                    rowHDKH.HDKH_ID = this.hdkh_tc_id;
                    rowHDKH.NHANVIEN_ID = this.tinh.nhanvien_id;
                    rowHDKH.DONVI_ID = this.tinh.donvi_id;
                    rowHDKH.LOAIGT_ID = 9;
                    rowHDKH.HTTTHI_ID = 2;
                    rowHDKH.KHLON_ID = null;
                    rowHDKH.CTV_ID = null; // Thêm theo yêu cầu của Nguyễn Tuấn ANh lúc 4h30 19/04/2023
                    this.loaihd_id = rowHDKH.LOAIHD_ID;
                    this.dsHDKH.push(rowHDKH);
                }
                return true
            } catch (e) { return false }
        },
        // API UR2.1.003_006
        // Xử lý hàm trong tao_HDKH_SUB(hdkh_id, hdkh_tc_id)
        layHDKHSubData: async function(hdkh_id) {
            let result = [];
            await api.getHDKHSubData(this.axios, hdkh_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_006
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDKH_SUB: async function(hdkh_id, hdkh_tc_id) {
            try {
                this.ds_hdkh_sub = [];
                let dt = await this.layHDKHSubData(hdkh_id);
                if (dt.length > 0) {
                    let row = dt[0];
                    row.HDKH_ID = hdkh_tc_id;
                    this.ds_hdkh_sub.push(row);
                }
                return true
            } catch (e) { 
                console.log('tao_HDKH_SUB', e) 
                return false
            }
        },
        // API 1. UR2.1.003_007
        // Xử lý hàm trong tao_HDTT()
        layHDThanhToanData: async function(hdkh_id) {
            let result = [];
            await api.getHDThanhToanData(this.axios, hdkh_id).then(res => {
                if (res.data.error_code != "BSS-00000000" && res.data.error_code != "BSS-00000204") {
                    this.$toast.error('Lỗi layHDThanhToanData: ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                }                
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 2. UR2.1.003_007
        // Xử lý hàm trong tao_HDTT()
        sinhMaTT: async function() {
            try {
                let result = '';
                await api.sinhMaTT(this.axios, {
                    donvi_id: this.tinh.donvi_id,
                    tinhthicong_id: this.tinh.tinh_id,
                    quan_id: 0
                }).then(res => {
                    if (res.data.error_code != "BSS-00000000") {
                        this.$toast.error('Lỗi sinh_matt: ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                        return false
                    }
                    result = res.data.data ? res.data.data : '';
                });
                return result;
            } catch (e) {
                this.$toast.error('Lỗi sinh_matt: ' + e)
                return false
            }
        },
        // Khảo sát UR2.1.003_007
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDTT: async function() {
            try {
                this.dsHDTT = [];
                let dts = await this.layHDThanhToanData(this.hdkh_id);
                let rowHDTT = {};
                if (dts.length > 0) {
                    this.hdtt_tc_id = await this.taoKey(this.tinh.tinh_id, "HD_THANHTOAN");
                    rowHDTT = this.copyRowHDTT(dts[0]);
                    rowHDTT.PHANVUNG_ID = this.tt_nd.phanvung_id;
                    rowHDTT.HDKH_ID = this.hdkh_tc_id;
                    rowHDTT.HDTT_ID = this.hdtt_tc_id;
                    rowHDTT.NGANHANG_ID = null;
                    rowHDTT.TUYENTHU_ID = 99999;
                    rowHDTT.HTTT_ID = 1;
                    rowHDTT.THANHTOAN_ID = null;
                    let ma_tt = await this.sinhMaTT();
                    if (!ma_tt) return false
                    else rowHDTT.MA_TT = ma_tt
                    this.dsHDTT.push(rowHDTT);
                }
                return true
            } catch (e) { return false }
        },
        // API UR2.1.003_005
        // Xử lý hàm trong taoDuLieu_HD_LIENHE(hdkh_tc, tinhtc)
        layHdTTLH_HdLienHe_HdBanCheo: async function(vhdkh_id, tinh_thicong) {
            let result = [];
            await api.getHdTTLH_HdLienHe_HdBanCheo(this.axios, {
                "vhdkh_id": vhdkh_id,
                "tinh_thicong": tinh_thicong
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong taoDuLieu_HD_LIENHE(hdkh_tc, tinhtc)
        copyRowHD_LIENHE(row) {
            let result = {
                HDKH_ID: row.hdkh_id,
                DIACHI: row.diachi,
                DIENTHOAI: row.dienthoai,
                DONVI: row.donvi,
                PHANVUNG_ID: row.phanvung_id,
                KIEU_YC: row.kieu_yc,
                TTLH_ID: row.ttlh_id,
                TEN_LH: row.ten_lh,
                FAX: row.fax,
                HDLH_ID: row.hdlh_id,
                DOITUONGCS_ID: row.doituongcs_id,
                EMAIL: row.email
            }
            return result;
        },
        // Khảo sát UR2.1.003_005
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        taoDuLieu_HD_LIENHE: async function(hdkh_tc, tinhtc) {
            try {
                this.ds_hdlh = [];
                this.ds_hdkh_md = [];
                let ds = await this.layHdTTLH_HdLienHe_HdBanCheo(this.hdkh_id, tinhtc);
                let row1 = {};
                let rowHD_LIENHE = {};
                if (ds.length > 0) {
                    this.map_lh_tc_ban = [];
                    for (let r=0; r<ds.length; r++) {
                        let hdlh_id = await this.taoKey(tinhtc, "HDLH_ID");
                        rowHD_LIENHE = this.copyRowHD_LIENHE(ds[r]);
                        rowHD_LIENHE.HDLH_ID = hdlh_id;
                        rowHD_LIENHE.HDKH_ID = hdkh_tc;
                        rowHD_LIENHE.TTLH_ID = null;
                        this.ds_hdlh.push(rowHD_LIENHE);
                        this.map_lh_tc_ban.push({key:hdlh_id, value: ds[r].hdlh_id});
                        row1 = {
                            MUCDICH_ID: 1,
                            HDLH_ID: hdlh_id
                        };
                        this.ds_hdkh_md.push(row1);
                    }
                }
                return true
            } catch (e) { return false } 
        },
        // API UR2.1.003_008
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        layDataHDBanCheoTheoHDKH: async function(hdkh_id) {
            let result = [];
            await api.getDataHDBanCheoTheoHDKH(this.axios, {
                 "vhdkh_id": hdkh_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        copyRowHDBANCHEO(row) {
            let result = {
                AP_ID: row?.ap_id || null,
                LOAI_DC: row?.loai_dc || null,
                PHUONG_ID: row?.phuong_id || null,
                SONHA: row?.sonha || null,
                KHU_ID: row?.khu_id || null,
                PHANVUNG_ID: row?.phanvung_id || null,
                VIDO: row?.vido || null,
                PHONG: row?.phong || null,
                PHO_ID: row?.pho_id || null,
                TANG: row?.tang || null,
                DIACHI: row?.diachi || null,
                KINHDO: row?.kinhdo || null,
                TINH_ID: row?.tinh_id || null,
                DACDIEM_ID: row?.dacdiem_id || null,
                QUAN_ID: row?.quan_id || null,
                BLOCK: row?.block || null,
                MOTA: row?.mota || null,
                DIACHI_ID: row?.diachi_id || null,
                LOAIHD_BANCHEO: row?.loaihd_bancheo || null,
                NGUOI_CN: row?.nguoi_cn || null,
                KHACHHANG_ID_TC: row?.khachhang_id_tc || null,
                NGAY_CN: row.ngay_cn ? moment(row.ngay_cn).format('DD/MM/YYYY HH:mm:ss') : null,
                MAY_CN: row.may_cn,
                THANHTOAN_ID_TC: row?.thanhtoan_id_tc || null,
                HDTB_THICONG: row?.hdtb_thicong || null,
                DICHVUVT_ID: row?.dichvuvt_id || null,
                DAUCUOI_ID: row?.daucuoi_id || null,
                ND_THOAITRA: row?.nd_thoaitra || null,
                HDTB_BAN: row?.hdtb_ban || null,
                ND_CHUYENTHICONG: row?.nd_chuyenthicong || null,
                MANV_DIEUHUONG: row?.manv_dieuhuong || null,
                THUEBAO_ID_BAN: row?.thuebao_id_ban || null,
                VANBAN: row?.vanban || null,
                TRANG_THAI: row?.trang_thai || null,
                THANHTOAN_ID_BAN: row?.thanhtoan_id_ban || null,
                KHACHHANG_ID_BAN: row?.khachhang_id_ban || null,
                TINH_BAN: row?.tinh_ban || null,
                URL: row?.url || null,
                TINH_THICONG: row?.tinh_thicong || null,
                MA_TB_TINH: row?.ma_tb_tinh || null,
                UPLOAD_BBNT: row?.upload_bbnt || null,
                THUEBAO_ID_THICONG: row?.thuebao_id_thicong || null,
                KIEU_GIAO: row?.kieu_giao || null,
                NGAY_HOANCONG: row.ngay_hoancong ? moment(row.ngay_hoancong).format('DD/MM/YYYY HH:mm:ss') : null,
                NGAY_HOANTHIEN: row.ngay_hoanthien ? moment(row.ngay_hoanthien).format('DD/MM/YYYY HH:mm:ss') : null
            }
            return result;
        },
        // API UR2.1.003_009
        // Xử lý hàm trong tao_HDTB(hdtb_ban_id, hdtb_tc_id)
        layDiaChiThueBaoTheoHDTBId: async function(hdtb_ban_id) {
            let result = [];
            await api.getDiaChiThueBaoTheoHDTBId(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API UR2.1.003_009
        // Xử lý hàm trong tao_HDTB(hdtb_ban_id, hdtb_tc_id)
        layDonViTheoLoaiDvDiaChiLDv2: async function(phuong_id, pho_id, ap_id, khu_id, dacdiem_id) {
            let result = [];
            await api.getDonViTheoLoaiDvDiaChiLDv2(this.axios, {
                "p_loaidv_id": 5,
                "p_phuong_id": phuong_id,
                "p_pho_id": pho_id,
                "p_ap_id": ap_id,
                "p_khu_id": khu_id,
                "p_dacdiem_id": dacdiem_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API UR2.1.003_009 => Trong mô tả api không có hàm này, phải nhờ anh Nhật Anh cung cấp
        // Xử lý hàm trong tao_HDTB(hdtb_ban_id, hdtb_tc_id)
        layHDTBBanCheoData: async function(hdtb_id, tinh_id) {
            let result = [];
            await api.getHDTBBanCheoData(this.axios, {
                "hdtb_id": hdtb_id,
                "tinh_id": tinh_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        copyRowHDTB(row) {
            try {
                let result = {
                    HDKH_ID: row.hdkh_id, 
                    KHU_ID: row.khu_id,
                    HDTB_CHA_ID: row.hdtb_cha_id,
                    NGAY_CN: row.ngay_cn ? moment(row.ngay_cn).format('DD/MM/YYYY HH:mm:ss') : null,
                    TBHD_PL: row.tbhd_pl,
                    GOI_ID: row.goi_id,
                    MAY_CN: row.may_cn,
                    IP_KH: row.ip_kh,
                    TRANGTHAIYC_ID: row.trangthaiyc_id,
                    DICHVUVT_ID: row.dichvuvt_id,
                    KIEU_TB: row.kieu_tb,
                    TTHD_ID: row.tthd_id,
                    HUONGGIAO_TN_ID: row.huonggiao_tn_id,
                    NGAY_KH: row.ngay_kh ? moment(row.ngay_kh).format('DD/MM/YYYY HH:mm:ss') : null,
                    NGAY_SN: row.ngay_sn ? moment(row.ngay_sn).format('DD/MM/YYYY HH:mm:ss') : null,
                    DONVI_CUOI_ID: row.donvi_cuoi_id,
                    TG_THUE_DEN: row.tg_thue_den,
                    QUAN_ID: row.quan_id,
                    TG_THUE_TU: row.tg_thue_tu,
                    LAN_KS: row.lan_ks,
                    SOLUONG: row.soluong,
                    QUYTRINH_ID: row.quytrinh_id,
                    KETQUAKS: row.ketquaks,
                    BANGKE_ID: row.bangke_id,
                    AP_ID: row.ap_id,
                    HTCM_ID: row.htcm_id,
                    PHANVUNG_ID: row.phanvung_id,
                    GHICHU: row.ghichu,
                    THUEBAO_CHA_ID: row.thuebao_cha_id,
                    LOAI_CSHT_ID: row.loai_csht_id,
                    HDTB_ID: row.hdtb_id,
                    NHOMTB_ID: row.nhomtb_id,
                    SYNC_STB: row.sync_stb,
                    THUEBAO_ID: row.thuebao_id,
                    SO_PL: row.so_pl,
                    SYNC_STATUS: row.sync_status,
                    NHACC_CU_ID: row.nhacc_cu_id,
                    CHITIEU_TG: row.chitieu_tg,
                    STATUS: row.status,
                    NGAY_HH_TD: row.ngay_hh_td ? moment(row.ngay_hh_td).format('DD/MM/YYYY HH:mm:ss') : null,
                    DIACHI_LD: row.diachi_ld,
                    NGUOI_CN: row.nguoi_cn,
                    SOTHANG_CK: row.sothang_ck,
                    PHUONG_ID: row.phuong_id,
                    MA_DHVIP: row.ma_dhvip,
                    DIACHI_TB: row.diachi_tb,
                    NGUOI_KH: row.nguoi_kh,
                    DAUCUOI_ID: row.daucuoi_id,
                    MA_TB: row.ma_tb,
                    TINH_ID: row.tinh_id,
                    MUCUOC_TB: row.mucuoc_tb,
                    THOIGIAN_CK: row.thoigian_ck,
                    EMAIL: row.email,
                    NGAY_INS: row.ngay_ins ? moment(row.ngay_ins).format('DD/MM/YYYY HH:mm:ss') : null,
                    GOIKT_ID: row.goikt_id,
                    TEN_TB: row.ten_tb,
                    NGAY_HT: row.ngay_ht ? moment(row.ngay_ht).format('DD/MM/YYYY HH:mm:ss') : null,
                    DOITUONG_CT_ID: row.doituong_ct_id,
                    TINH_DC_ID: row.tinh_dc_id,
                    KIEULD_ID: row.kieuld_id,
                    DONVI_ID: row.donvi_id,
                    CHUNHOM: row.chunhom,
                    HDTB_ID_HNI: row.hdtb_id_hni,
                    DONVI_DAU_ID: row.donvi_dau_id,
                    TIENTRINHTC_ID: row.tientrinhtc_id,
                    PHO_ID: row.pho_id,
                    NGAY_TT: row.ngay_tt ? moment(row.ngay_tt).format('DD/MM/YYYY HH:mm:ss') : null,
                    CHUYENMANG: row.chuyenmang,
                    DACDIEM_ID: row.dacdiem_id,
                    DOITUONG_ID: row.doituong_id,
                    IP_CN: row.ip_cn,
                    LOAITB_ID: row.loaitb_id,
                    HDTT_ID: row.hdtt_id,
                    MUCUOCTB_ID: row.mucuoctb_id,
                    NGAY_KTCK: row.ngay_ktck ? moment(row.ngay_ktck).format('DD/MM/YYYY HH:mm:ss') : null
                };
                return result;
            } catch (e) { console.log('Lỗi copyRowHDTB ' + JSON.stringify(e)) }
        },
        // API UR2.1.003_009
        // Xử lý hàm trong tao_HDTB(hdtb_ban_id, hdtb_tc_id)
        banChaoLayTTNhanVienFunTinhBan: async function(hdtb_ban_id) {
            let result = '';
            await api.banChaoLayTTNhanVienFunTinhBan(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : '';
            });
            return result;
        },
        // Khảo sát UR2.1.003_009
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_HDTB: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                let vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0, vtid = 0;
                let ds_diachi_ld = await this.layDiaChiThueBaoTheoHDTBId(hdtb_ban_id);
                if (ds_diachi_ld.length > 0) {
                    if (ds_diachi_ld[0].phuong_id && ds_diachi_ld[0].phuong_id > 0) {
                        vphuong_id = ds_diachi_ld[0].phuong_id
                    }
                    if (ds_diachi_ld[0].ap_id && ds_diachi_ld[0].ap_id > 0) {
                        vap_id = ds_diachi_ld[0].ap_id
                    }
                    if (ds_diachi_ld[0].khu_id && ds_diachi_ld[0].khu_id > 0) {
                        vkhu_id = ds_diachi_ld[0].khu_id
                    }
                    if (ds_diachi_ld[0].pho_id && ds_diachi_ld[0].pho_id > 0) {
                        vpho_id = ds_diachi_ld[0].pho_id
                    }
                    if (ds_diachi_ld[0].dacdiem_id && ds_diachi_ld[0].dacdiem_id > 0) {
                        vdacdiem_id = ds_diachi_ld[0].dacdiem_id
                    }
                    let dsvt = await this.layDonViTheoLoaiDvDiaChiLDv2(vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
                    if (dsvt.length > 0) {
                        vtid = dsvt[0].donviql_id;
                    }
                }
                let dts = await this.layHDTBBanCheoData(hdtb_ban_id, this.tinh.tinh_id);
                if (!dts || dts.length == 0) {
                    this.$toast.error('Không tìm thấy HDTB bán chéo layHDTBBanCheoData!')
                    console.log('tao_HDTB > layHDTBBanCheoData', hdtb_ban_id, this.tinh.tinh_id)
                    return false
                }
                let rowHDTB = this.copyRowHDTB(dts[0]);
                let ghichu = rowHDTB.GHICHU;
                let str = await this.banChaoLayTTNhanVienFunTinhBan(hdtb_ban_id);
                ghichu += "--" + str;
                rowHDTB.GHICHU = ghichu; 
                rowHDTB.HDKH_ID = this.hdkh_tc_id;
                rowHDTB.HDTB_ID = hdtb_tc_id;
                if (vtid > 0)
                    rowHDTB.DONVI_ID = vtid;
                if ((rowHDTB.DICHVUVT_ID == this.dichvuvienthong.co_dinh || rowHDTB.DICHVUVT_ID == this.dichvuvienthong.ims)
                    && (this.loaihd_id == this.loaihopdong.dat_moi || this.loaihd_id == this.loaihopdong.thay_doi_dv))
                {
                    rowHDTB.MA_TB =  rowHDTB.MA_TB.substring(this.tinh.ma_vung.length -1);
                }
                if (this.hdtt_tc_id > 0)
                    rowHDTB.HDTT_ID = this.hdtt_tc_id;
                if (this.thuebao_id_tc != null && this.thuebao_id_tc != 0 && this.thuebao_id_tc != -1)
                    rowHDTB.THUEBAO_ID = this.thuebao_id_tc;
                else
                    rowHDTB.THUEBAO_ID = null;
                if (rowHDTB.DICHVUVT_ID == this.dichvuvienthong.megawan || rowHDTB.DICHVUVT_ID == this.dichvuvienthong.metronet || rowHDTB.DICHVUVT_ID == 26)
                {
                    rowHDTB.HDTB_CHA_ID = null;
                    rowHDTB.THUEBAO_CHA_ID = null;
                }
                this.loaitb_id = rowHDTB.LOAITB_ID;
                this.kieuld_hd = rowHDTB.KIEULD_ID;
                rowHDTB.TTHD_ID = 1;
                this.dsHDTB.push(rowHDTB);
                if (this.loaihd_id == this.loaihopdong.dat_moi) {
                    // UR2.1.003_009
                    await api.capNhatKhoSo(this.axios, {
                        "vma_tb_moi": rowHDTB.MA_TB,
                        "vma_tb_cu": "",
                        "vdichvuvt_id" : rowHDTB.DICHVUVT_ID,
                        "vdonvi_id": rowHDTB.DONVI_ID,
                        "tinhthicong_id": this.tinh.tinh_id,
                    }).then(res => {
                        console.log("api.capNhatKhoSo")
                    });
                }
                let row = {
                    HDTB_ID: hdtb_tc_id,
                    THUEBAO_ID: 0,
                    LOAITIEN_ID: 3,
                    GIATRI: 0
                }
                this.dsTien_HDTB.push(row);
                row = {
                    HDTB_ID: hdtb_tc_id,
                    THUEBAO_ID: 0,
                    LOAITIEN_ID: 1,
                    GIATRI: 0
                }
                this.dsTien_HDTB.push(row);
                return true
            } catch (e) { 
                console.log('Lỗi tao_HDTB ' + JSON.stringify(e)) 
                return false
            }
        },
        // API UR2.1.003_010
        // Xử lý hàm trong tao_HDTBCD(hdtb_ban_id, hdtb_tc_id)
        layHDBCCDData: async function(hdtb_ban_id) {
            let result = [];
            await api.getHDBCCDData(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_HDTBCD(hdtb_ban_id, hdtb_tc_id)
        copyRowHDTBCD(row) {
            try {
                let result = {
                    TG_THUESO: row.tg_thueso,
                    TOANHA_ID: row.toanha_id,
                    NGAYCD_DV: row.ngaycd_dv ? moment(row.ngaycd_dv).format('DD/MM/YYYY HH:mm:ss') : null,
                    NE_CU: row.ne_cu,
                    MATB_TN: row.matb_tn,
                    DOICAP_GOC: row.doicap_goc,
                    TUYENCAP: row.tuyencap,
                    CULY: row.culy,
                    NOIDUNG_TB: row.noidung_tb,
                    DOICAP_2: row.doicap_2,
                    TT_TC_DM: row.tt_tc_dm,
                    DOICAP_P: row.doicap_p,
                    CAP_ID: row.cap_id,
                    HOPCAP_P: row.hopcap_p,
                    MC_TBCD_ID: row.mc_tbcd_id,
                    MIENCUOC_TB: row.miencuoc_tb,
                    CONGNGHE_ID: row.congnghe_id,
                    NE_ID: row.ne_id,
                    NHANVIEN_DAIVT_ID: row.nhanvien_daivt_id,
                    NGAYCN_HC: row.ngaycn_hc ? moment(row.ngaycn_hc).format('DD/MM/YYYY HH:mm:ss') : null,
                    CAP_P: row.cap_p,
                    KETCUOI_ID: row.ketcuoi_id,
                    PHANVUNG_ID: row.phanvung_id,
                    GHICHU: row.ghichu,
                    LIENTU: row.lientu,
                    MADOICAP: row.madoicap,
                    DOICAP: row.doicap,
                    CAP_GOC: row.cap_goc,
                    VITRI: row.vitri,
                    NHANVIEN_HC_ID: row.nhanvien_hc_id,
                    HDTB_ID: row.hdtb_id,
                    CAIDAT_DV: row.caidat_dv,
                    TRANGBI_ID: row.trangbi_id,
                    SOTRUOT: row.sotruot,
                    VITRI_2: row.vitri_2,
                    LOAICAP_ID: row.loaicap_id,
                    THOIHAN_ID: row.thoihan_id,
                    NE: row.ne,
                    TRAMTB_ID: row.tramtb_id,
                    CHUQUAN_ID: row.chuquan_id,
                    TUCAP_P: row.tucap_p,
                    MA_VNPT: row.ma_vnpt,
                    TUCAP_GOC: row.tucap_goc,
                    CHUQUANLD_ID: row.chuquanld_id,
                    NAP_ID: row.nap_id,
                    MA_LT: row.ma_lt
                }
                return result;
            } catch (e) { this.$toast.error('Lỗi copyRowHDTBCD ' + JSON.stringify(e)) }
        },
        // Khảo sát UR2.1.003_010
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_HDTBCD: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                let dts = await this.layHDBCCDData(hdtb_ban_id);
                if (dts.length > 0) {
                    let rowHDTBCD = this.copyRowHDTBCD(dts[0]);
                    rowHDTBCD.HDTB_ID = hdtb_tc_id;
                    rowHDTBCD.TINHKHAC = null;
                    this.dsHDTBCD.push(rowHDTBCD);
                }
                return true
            } catch (e) {
                return false
            }
        },
        // API UR2.1.003_011
        // Xử lý hàm trong tao_HDTBIMS(hdtb_ban_id, hdtb_tc_id)
        layHDBCIMSData: async function(hdtb_ban_id) {
            let result = [];
            await api.getHDBCIMSData(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_HDTBIMS(hdtb_ban_id, hdtb_tc_id)
        copyRowHDTBIMS(row) {
            let result = {
                SLID: row.slid,
                DOICAP_GOC: row.doicap_goc,
                TUYENCAP: row.tuyencap,
                DOICAP_2: row.doicap_2,
                PASSWORD: row.password,
                CUOC_DOITAC: row.cuoc_doitac,
                HOPCAP_P: row.hopcap_p,
                TONGDAI_ID: row.tongdai_id,
                CTDV_ID: row.ctdv_id,
                PHANVUNG_ID: row.phanvung_id,
                GHICHU: row.ghichu,
                LIENTU: row.lientu,
                DOICAP: row.doicap,
                IP: row.ip,
                PROFILE: row.profile,
                VITRI: row.vitri,
                HC_DM: row.hc_dm,
                HDTB_ID: row.hdtb_id,
                NGAY_TCDM: row.ngay_tcdm ? moment(row.ngay_tcdm).format('DD/MM/YYYY HH:mm:ss') : null,
                TRANGBI_ID: row.trangbi_id,
                VITRI_2: row.vitri_2,
                LOAICAP_ID: row.loaicap_id,
                TRUNKID: row.trunkid,
                SERI_MD: row.seri_md,
                TRAMTB_ID: row.tramtb_id,
                PORT_ID: row.port_id,
                TINHKHAC: row.tinhkhac,
                TUCAP_GOC: row.tucap_goc,
                CHUQUANLD_ID: row.chuquanld_id,
                MA_LT: row.ma_lt,
                TOANHA_ID: row.toanha_id,
                DSLAM_ID: row.dslam_id,
                ENDPOINT_ID: row.endpoint_id,
                MATB_TN: row.matb_tn,
                BRAS_ID: row.bras_id,
                CULY: row.culy,
                TT_TC_DM: row.tt_tc_dm,
                DOICAP_P: row.doicap_p,
                CAP_ID: row.cap_id,
                HTTC_ID: row.httc_id,
                CONGNGHE_ID: row.congnghe_id,
                SVLAN: row.svlan,
                CULY_TN: row.culy_tn,
                NGAYCN_HC: row.ngaycn_hc ? moment(row.ngaycn_hc).format('DD/MM/YYYY HH:mm:ss') : null,
                DOITAC_ID: row.doitac_id,
                THIETBI_ID: row.thietbi_id,
                CAP_P: row.cap_p,
                KETCUOI_ID: row.ketcuoi_id,
                MADOICAP: row.madoicap,
                CAP_GOC: row.cap_goc,
                DSLAM_TINH_ID: row.dslam_tinh_id,
                PROFILE_INFO: row.profile_info,
                NHANVIEN_HC_ID: row.nhanvien_hc_id,
                CVLAN: row.cvlan,
                SL_CUOCGOI: row.sl_cuocgoi,
                NHANVIEN_CD_ID: row.nhanvien_cd_id,
                THOIHAN_ID: row.thoihan_id,
                CHUQUAN_ID: row.chuquan_id,
                PROFILEINFO: row.profileinfo,
                TUCAP_P: row.tucap_p,
                MA_VNPT: row.ma_vnpt,
                VCI_VPI_ID: row.vci_vpi_id,
                USERNAME: row.username
            }
            return result;
        },
        // Khảo sát UR2.1.003_011
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_HDTBIMS: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                let dts = await this.layHDBCIMSData(hdtb_ban_id);
                if (dts.length > 0) {
                    let rowHDTBIMS = this.copyRowHDTBIMS(dts[0]);
                    rowHDTBIMS.HDTB_ID = hdtb_tc_id;
                    rowHDTBIMS.TINHKHAC = null;
                    this.dsHDTBIMS.push(rowHDTBIMS);
                }
                return true
            } catch (e) {
                return false
            }
        },
        // API UR2.1.003_012
        // Xử lý hàm trong tao_HDTBADSL(hdtb_ban_id, hdtb_tc_id)
        layHDBCADSLData: async function(hdtb_ban_id) {
            let result = [];
            await api.getHDBCADSLData(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_HDTBADSL(hdtb_ban_id, hdtb_tc_id)
        copyRowHDTBADSL(row) {
            let result = {
                SLID: row.slid,
                TL_GIAMCUOC_QL: row.tl_giamcuoc_ql,
                SLTV_HTVC: row.sltv_htvc,
                DOICAP_GOC: row.doicap_goc,
                LOAI_EMAIL: row.loai_email,
                TUYENCAP: row.tuyencap,
                STATUS_IP: row.status_ip,
                KIEUONU_ID: row.kieuonu_id,
                DOICAP_2: row.doicap_2,
                PASSWORD: row.password,
                NGAYTRA: row.ngaytra ? moment(row.ngaytra).format('DD/MM/YYYY HH:mm:ss') : null,
                TYLE_GC: row.tyle_gc,
                NGAY_GC: row.ngay_gc ? moment(row.ngay_gc).format('DD/MM/YYYY HH:mm:ss') : null,
                MOTACS_WF1TR: row.motacs_wf1tr,
                CONGSUAT_WF1T: row.congsuat_wf1t,
                THIETBIDC_ID: row.thietbidc_id,
                HANG_SX: row.hang_sx,
                MATB_MGWAN: row.matb_mgwan,
                DOICAP: row.doicap,
                IP: row.ip,
                PROFILEDSL_ID: row.profiledsl_id,
                NGAY_TCDM: row.ngay_tcdm ? moment(row.ngay_tcdm).format('DD/MM/YYYY HH:mm:ss') : null,
                MA_XACTHUC: row.ma_xacthuc,
                VITRI_2: row.vitri_2,
                SYNC_STB: row.sync_stb,
                MOTACS_WF1T: row.motacs_wf1t,
                PORT_ID: row.port_id,
                THONGTIN_TC: row.thongtin_tc,
                TUCAP_GOC: row.tucap_goc,
                CONGSUAT_WF2T: row.congsuat_wf2t,
                MA_LT: row.ma_lt,
                THANG_ID: row.thang_id,
                IP_MP: row.ip_mp,
                TOANHA_ID: row.toanha_id,
                DSLAM_ID: row.dslam_id,
                FIRMWARE_VERSION: row.firmware_version,
                HARDWARE_VERSION: row.hardware_version,
                CULY: row.culy,
                DOICAP_P: row.doicap_p,
                PORT_MYTV: row.port_mytv,
                MOTACS_WF2TR: row.motacs_wf2tr,
                TG_GIAM: row.tg_giam,
                CONGNGHE_ID: row.congnghe_id,
                EMAIL: row.email,
                NGAYCN_HC: row.ngaycn_hc ? moment(row.ngaycn_hc).format('DD/MM/YYYY HH:mm:ss') : null,
                DOITAC_ID: row.doitac_id,
                KETCUOI_ID: row.ketcuoi_id,
                MADOICAP: row.madoicap,
                MATKHAU_WF: row.matkhau_wf,
                CAP_GOC: row.cap_goc,
                CUOC_TN: row.cuoc_tn,
                NHANVIEN_CD_ID: row.nhanvien_cd_id,
                MA_VNPT: row.ma_vnpt,
                KIEUTRA_ID: row.kieutra_id,
                VCI_VPI_ID: row.vci_vpi_id,
                MUCCUOC_ID: row.muccuoc_id,
                TOCDO_ID: row.tocdo_id,
                CUOC_SODEP: row.cuoc_sodep,
                CUOC_SD: row.cuoc_sd,
                TOCDOTHUC_ID: row.tocdothuc_id,
                CONGSUAT_WF2TR: row.congsuat_wf2tr,
                CUOC_TBI: row.cuoc_tbi,
                KH_CD: row.kh_cd,
                HOPCAP_P: row.hopcap_p,
                VMP: row.vmp,
                TUCTHI: row.tucthi,
                PHANVUNG_ID: row.phanvung_id,
                GHICHU: row.ghichu,
                LIENTU: row.lientu,
                VITRI: row.vitri,
                HC_DM: row.hc_dm,
                LOAI_CSHT_ID: row.loai_csht_id,
                HDTB_ID: row.hdtb_id,
                SYNC: row.sync,
                TRANGBI_ID: row.trangbi_id,
                LOAICAP_ID: row.loaicap_id,
                SERI_MD: row.seri_md,
                HANDATCHO: row.handatcho,
                TRAMTB_ID: row.tramtb_id,
                CHUQUANLD_ID: row.chuquanld_id,
                CONGSUAT_WF3M: row.congsuat_wf3m,
                MATB_TN: row.matb_tn,
                CUOC_QL: row.cuoc_ql,
                BRAS_ID: row.bras_id,
                TT_TC_DM: row.tt_tc_dm,
                CAP_ID: row.cap_id,
                CONGSUAT_WF1TR: row.congsuat_wf1tr,
                MOTACS_WF2T: row.motacs_wf2t,
                THONGTIN_WF: row.thongtin_wf,
                SO_DT: row.so_dt,
                CULY_TN: row.culy_tn,
                CAP_P: row.cap_p,
                NHANVIEN_HC_ID: row.nhanvien_hc_id,
                MOTACS_WF3M: row.motacs_wf3m,
                VITRI_WF: row.vitri_wf,
                THOIHAN_ID: row.thoihan_id,
                CHUQUAN_ID: row.chuquan_id,
                TUCAP_P: row.tucap_p,
                USERNAME: row.username
            }
            return result;
        },
        // Khảo sát UR2.1.003_012
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_HDTBADSL: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                let dts = await this.layHDBCADSLData(hdtb_ban_id);
                if (dts.length > 0) {
                    let rowHDTBADSL = this.copyRowHDTBADSL(dts[0]);
                    rowHDTBADSL.HDTB_ID = hdtb_tc_id;
                    rowHDTBADSL.TINHKHAC = null;
                    this.dsHDTBADSL.push(rowHDTBADSL);
                }
                return true
            } catch (e) {
                return false
            }
        },
        // API UR2.1.003_013
        // Xử lý hàm trong tao_HDTBMGWAN(hdtb_ban_id, hdtb_tc_id)
        layHDBCMGWANData: async function(hdtb_ban_id) {
            let result = [];
            await api.getHDBCMGWANData(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_HDTBMGWAN(hdtb_ban_id, hdtb_tc_id)
        copyRowHDTBMGWAN(row) {
            let result = {
                GDKN_POP_ID: row.gdkn_pop_id,
                DOICAP_GOC: row.doicap_goc,
                TUYENCAP: row.tuyencap,
                DOICAP_2: row.doicap_2,
                DIEM_POP: row.diem_pop,
                MAKENH_VTN: row.makenh_vtn,
                THIETBIDC_ID: row.thietbidc_id,
                DSLAM: row.dslam,
                KIEUKN_ID: row.kieukn_id,
                TINHCUOC: row.tinhcuoc,
                DOICAP: row.doicap,
                TOCDO_NIX: row.tocdo_nix,
                MHKN_ID: row.mhkn_id,
                VRF: row.vrf,
                VITRI_2: row.vitri_2,
                MA_CRM: row.ma_crm,
                ROUTE: row.route,
                LOAIKENH_ID: row.loaikenh_id,
                KIEU_DUPLEX_ID: row.kieu_duplex_id,
                SITE_ID: row.site_id,
                PORT_ID: row.port_id,
                NHAN_HD: row.nhan_hd,
                TUCAP_GOC: row.tucap_goc,
                MA_LT: row.ma_lt,
                TOANHA_ID: row.toanha_id,
                SL_IP: row.sl_ip,
                MA_VPN: row.ma_vpn,
                NGAY_YCHT: row.ngay_ycht ? moment(row.ngay_ycht).format('DD/MM/YYYY') : null,
                KP_AO: row.kp_ao,
                CULY: row.culy,
                DOICAP_P: row.doicap_p,
                LAN_IP: row.lan_ip,
                VLAN_TRANSPARENT: row.vlan_transparent,
                CONGNGHE_ID: row.congnghe_id,
                DOITAC_ID: row.doitac_id,
                KETCUOI_ID: row.ketcuoi_id,
                MADOICAP: row.madoicap,
                CAP_GOC: row.cap_goc,
                PHANTACH_TD: row.phantach_td,
                DSLAM_TINH_ID: row.dslam_tinh_id,
                CUOC_TK: row.cuoc_tk,
                CUOC_TC: row.cuoc_tc,
                CUOC_NIX: row.cuoc_nix,
                THIETBI_DICH: row.thietbi_dich,
                DIACHI_KN: row.diachi_kn,
                MA_VNPT: row.ma_vnpt,
                VCI_VPI_ID: row.vci_vpi_id,
                SO_AS: row.so_as,
                TEN_TBI: row.ten_tbi,
                TOCDO_POP: row.tocdo_pop,
                TOCDO_ISP: row.tocdo_isp,
                CUOC_CIR: row.cuoc_cir,
                CE_IP: row.ce_ip,
                COS: row.cos,
                TOCDO_ID: row.tocdo_id,
                LOAINODE_ID: row.loainode_id,
                CUOC_TCDT: row.cuoc_tcdt,
                PHANLOAI_ID: row.phanloai_id,
                MA_BSS: row.ma_bss,
                CUOC_TBI: row.cuoc_tbi,
                TOCDO_PIR_ID: row.tocdo_pir_id,
                GIUKENH: row.giukenh,
                HOPCAP_P: row.hopcap_p,
                HQOS: row.hqos,
                LOAIMD_ID: row.loaimd_id,
                PHANVUNG_ID: row.phanvung_id,
                GHICHU: row.ghichu,
                LIENTU: row.lientu,
                VITRI: row.vitri,
                MA_PL: row.ma_pl,
                HDTB_ID: row.hdtb_id,
                TRANGBI_ID: row.trangbi_id,
                LOAICAP_ID: row.loaicap_id,
                CUOC_TKDT: row.cuoc_tkdt,
                MTU_SIZE: row.mtu_size,
                VFI: row.vfi,
                TINHKHAC: row.tinhkhac,
                CUOC_IP: row.cuoc_ip,
                GDKN_KH_ID: row.gdkn_kh_id,
                HUONG_KN: row.huong_kn,
                CHUQUANLD_ID: row.chuquanld_id,
                IP_POP: row.ip_pop,
                TEN_TRUNK_NNI: row.ten_trunk_nni,
                LINK: row.link,
                MATB_TN: row.matb_tn,
                PORT_PE: row.port_pe,
                CAP_ID: row.cap_id,
                MA_CV: row.ma_cv,
                CUOC_ISP: row.cuoc_isp,
                SLOT_PE: row.slot_pe,
                SVLAN: row.svlan,
                CUOC_HT: row.cuoc_ht,
                SD_TBI_NET: row.sd_tbi_net,
                CAP_P: row.cap_p,
                ID_ERP: row.id_erp,
                MAHD_DT: row.mahd_dt,
                CVLAN: row.cvlan,
                TINH_KN_ID: row.tinh_kn_id,
                THOIHAN_ID: row.thoihan_id,
                SERIAL: row.serial,
                CHUQUAN_ID: row.chuquan_id,
                CUOC_PIR: row.cuoc_pir,
                TUCAP_P: row.tucap_p,
                CAUHINH_DT_ID: row.cauhinh_dt_id,
                WAN_IP: row.wan_ip,
                PE_ID: row.pe_id,
                PORT_POP: row.port_pop,
                NGAY_CV: row.ngay_cv ? moment(row.ngay_cv).format('DD/MM/YYYY') : null
            }
            return result;
        },
        // Khảo sát UR2.1.003_013
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_HDTBMGWAN: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                let dts = await this.layHDBCMGWANData(hdtb_ban_id);
                if (dts.length > 0) {
                    let rowHDTB_MGWAN = this.copyRowHDTBMGWAN(dts[0]);
                    rowHDTB_MGWAN.HDTB_ID = hdtb_tc_id;
                    rowHDTB_MGWAN.DOITAC_ID = null;
                    rowHDTB_MGWAN.TINHKHAC = null;
                    this.dsHDTBMGWAN.push(rowHDTB_MGWAN);
                }
                return true
            } catch (e) { return false }
        },
        // API 1. UR2.1.003_014
        // Xử lý hàm trong tao_HDTBTSL(hdtb_ban_id, hdtb_tc_id)
        layHDBanCheoHDBCTSL: async function(hdtb_id, tinhthicong_id) {
            let result = [];
            await api.getHDBanCheoHDBCTSL(this.axios, {
                "vhdtb_id": hdtb_id,
                "tinhthicong_id": tinhthicong_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 2. UR2.1.003_014
        // Xử lý hàm trong tao_HDTBTSL(hdtb_ban_id, hdtb_tc_id)
        layDiaChiBanCheoData: async function(diachi_id) {
            let result = [];
            await api.getDiaChiBanCheoData(this.axios, diachi_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_HDTBTSL(hdtb_ban_id, hdtb_tc_id)
        copyRowDIACHI(row) {
            let result = {
                AP_ID: row?.ap_id || null,
                LOAI_DC: row?.loai_dc || null,
                PHUONG_ID: row?.phuong_id || null,
                SONHA: row?.sonha || null,
                KHU_ID: row?.khu_id || null,
                PHANVUNG_ID: row?.phanvung_id || null,
                VIDO: row?.vido || null,
                PHONG: row?.phong || null,
                PHO_ID: row?.pho_id || null,
                TANG: row?.tang || null,
                DIACHI: row?.diachi || null,
                KINHDO: row?.kinhdo || null,
                TINH_ID: row?.tinh_id || null,
                DACDIEM_ID: row?.dacdiem_id || null,
                QUAN_ID: row?.quan_id || null,
                BLOCK: row?.block || null,
                MOTA: row?.mota || null,
                DIACHI_ID: row?.diachi_id || null
            }
            return result;
        },
        // Xử lý hàm trong tao_HDTBTSL(hdtb_ban_id, hdtb_tc_id)
        copyRowHDTBTSL(row) {
            let result = {
                KINHDO_LD: row.kinhdo_ld,
                TOCDO_ID: row.tocdo_id,
                LOAINODE_ID: row.loainode_id,
                CUOC_TCDT: row.cuoc_tcdt,
                DOICAP_GOC: row.doicap_goc,
                TUYENCAP: row.tuyencap,
                DOICAP_2: row.doicap_2,
                MA_TB_SUB: row.ma_tb_sub,
                MA_BSS: row.ma_bss,
                CUOC_TBI: row.cuoc_tbi,
                HOPCAP_P: row.hopcap_p,
                MAKENH_VTN: row.makenh_vtn,
                TIMESLOT_CN: row.timeslot_cn,
                THIETBIDC_ID: row.thietbidc_id,
                LOAIMD_ID: row.loaimd_id,
                CAP_DUPHONG: row.cap_duphong,
                PHANVUNG_ID: row.phanvung_id,
                GHICHU: row.ghichu,
                LIENTU: row.lientu,
                DOICAP: row.doicap,
                MAPPING_CN: row.mapping_cn,
                VITRI: row.vitri,
                MA_PL: row.ma_pl,
                MHKN_ID: row.mhkn_id,
                VRF: row.vrf,
                HDTB_ID: row.hdtb_id,
                TRANGBI_ID: row.trangbi_id,
                VITRI_2: row.vitri_2,
                LOAICAP_ID: row.loaicap_id,
                MA_CRM: row.ma_crm,
                DIACHI: row.diachi,
                TINH_THICONG: row.tinh_thicong,
                LOAIKENH_ID: row.loaikenh_id,
                CUOC_TKDT: row.cuoc_tkdt,
                PORT: row.port,
                TRAMTB_ID: row.tramtb_id,
                SITE_ID: row.site_id,
                MTU_SIZE: row.mtu_size,
                PORT_ID: row.port_id,
                NHAN_HD: row.nhan_hd,
                TUCAP_GOC: row.tucap_goc,
                CHUQUANLD_ID: row.chuquanld_id,
                MA_LT: row.ma_lt,
                DIACHI_ID: row.diachi_id,
                TOANHA_ID: row.toanha_id,
                SLOT_CN: row.slot_cn,
                DSLAM_ID: row.dslam_id,
                LINK: row.link,
                IP_WAN: row.ip_wan,
                PORT_CN: row.port_cn,
                SLOT: row.slot,
                NGAY_YCHT: row.ngay_ycht ? moment(row.ngay_ycht).format('DD/MM/YYYY HH:mm:ss') : null,
                BRAS_ID: row.bras_id,
                CULY: row.culy,
                DAUCUOI_ID: row.daucuoi_id,
                DOICAP_P: row.doicap_p,
                CAP_ID: row.cap_id,
                MA_CV: row.ma_cv,
                TINH_ID: row.tinh_id,
                VIDO_LD: row.vido_ld,
                SO_DT: row.so_dt,
                CONGNGHE_ID: row.congnghe_id,
                SL_SOIQUANG: row.sl_soiquang,
                SNBP: row.snbp,
                CUOC_HT: row.cuoc_ht,
                SD_TBI_NET: row.sd_tbi_net,
                DOITAC_ID: row.doitac_id,
                CAP_P: row.cap_p,
                KETCUOI_ID: row.ketcuoi_id,
                ID_ERP: row.id_erp,
                TINH_DC_ID: row.tinh_dc_id,
                MADOICAP: row.madoicap,
                GDKN_ID: row.gdkn_id,
                CAP_GOC: row.cap_goc,
                CUOC_TK: row.cuoc_tk,
                MAHD_DT: row.mahd_dt,
                YC_BAOVE: row.yc_baove,
                AUTO_NEGO: row.auto_nego,
                DC_DAUCUOI: row.dc_daucuoi,
                CUOC_TC: row.cuoc_tc,
                CAP_CHINH: row.cap_chinh,
                THOIHAN_ID: row.thoihan_id,
                SERIAL: row.serial,
                THIETBI_DICH: row.thietbi_dich,
                CHUQUAN_ID: row.chuquan_id,
                TIMESLOT: row.timeslot,
                TUCAP_P: row.tucap_p,
                MA_VNPT: row.ma_vnpt,
                VCI_VPI_ID: row.vci_vpi_id,
                TEN_TBI: row.ten_tbi,
                NGAY_CV: row.ngay_cv ? moment(row.ngay_cv).format('DD/MM/YYYY HH:mm:ss') : null
            }
            return result;
        },
        // API 4. UR2.1.003_014
        // Xử lý hàm trong tao_HDTBTSL(hdtb_ban_id, hdtb_tc_id)
        layDiaChiByDiaChiId: async function(tinhthicong_id, thuebao_id) {
            let result = [];
            await api.getDiaChiByDiaChiId(this.axios, {
                "tinhthicong_id": tinhthicong_id,
                "thuebao_id": thuebao_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_014
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_HDTBTSL: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                let rowHDTBTSL = {};
                let rowDIACHI = {};
                //this.dsDIACHI = []  Edit 02/08/2023
                let dt = await this.layHDBanCheoHDBCTSL(hdtb_ban_id, this.tinh.tinh_id);
                console.log('B0000000000000')
                console.log('tao_HDTBTSL > dt, dsDIACHI init', dt, this.dsDIACHI, this.dsDIACHI.length)
                if (dt.length > 0) {
                    console.log('B11111111')
                    if (this.loaihd_id == this.loaihopdong.dat_moi || this.loaihd_id == this.loaihopdong.di_chuyen) {
                        if (dt.length == 2) { // Tỉnh đầu khác tỉnh cuối
                            for (let i=0; i<dt.length; i++) {
                                let diachitsl = await this.layDiaChiBanCheoData(dt[i].diachi_id);
                                rowDIACHI = this.copyRowDIACHI(diachitsl[0]);
                                rowHDTBTSL = this.copyRowHDTBTSL(dt[i]);
                                rowHDTBTSL.HDTB_ID = hdtb_tc_id;
                                let diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                                while (diachi_tsl && this.dsDIACHI.find(e => Number(e['DIACHI_ID']) == Number(diachi_tsl))) {
                                    diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                                }
                                rowHDTBTSL.DIACHI_ID = diachi_tsl
                                rowDIACHI.DIACHI_ID = diachi_tsl
                                rowHDTBTSL.DOITAC_ID = null;
                                this.dsHDTBTSL.push(rowHDTBTSL);
                                if (this.dsHDTBTSL && this.dsHDTBTSL.length > 0) this.dsDIACHI.push(rowDIACHI);
                            }
                        
                        } else {
                            let row = dt[0];
                            let diachitsl = await this.layDiaChiBanCheoData(row.diachi_id);
                            rowDIACHI = this.copyRowDIACHI(diachitsl[0]);
                            rowHDTBTSL = this.copyRowHDTBTSL(row);
                            rowHDTBTSL.HDTB_ID = hdtb_tc_id;
                            let diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                            while (diachi_tsl && this.dsDIACHI.find(e => Number(e['DIACHI_ID']) == Number(diachi_tsl))) {
                                diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                            }
                            rowHDTBTSL.DIACHI_ID = diachi_tsl
                            rowDIACHI.DIACHI_ID = diachi_tsl
                            rowHDTBTSL.DOITAC_ID = null;
                            this.dsHDTBTSL.push(rowHDTBTSL);
                            if (this.dsHDTBTSL && this.dsHDTBTSL.length > 0) this.dsDIACHI.push(rowDIACHI);
                            // Tìm thằng thứ 2 hợp lý để nhét vào
                            for (let i = 1; i < dt.length; i++)
                            {
                                if (dt[i].daucuoi_id != dt[0].daucuoi_id && dt[i].tinh_dc_id != dt[0].tinh_dc_id)
                                {
                                    let row1 = dt[i];
                                    let diachitsl1 = await this.layDiaChiBanCheoData(row1.diachi_id);
                                    rowDIACHI = this.copyRowDIACHI(diachitsl1[0]);
                                    rowHDTBTSL = this.copyRowHDTBTSL(row1);
                                    rowHDTBTSL.HDTB_ID = hdtb_tc_id;
                                    diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                                    while (diachi_tsl && this.dsDIACHI.find(e => Number(e['DIACHI_ID']) == Number(diachi_tsl))) {
                                        diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                                    }
                                    rowHDTBTSL.DIACHI_ID = diachi_tsl
                                    rowDIACHI.DIACHI_ID = diachi_tsl
                                    rowHDTBTSL.DOITAC_ID = null;
                                    this.dsHDTBTSL.push(rowHDTBTSL);
                                    if (this.dsHDTBTSL && this.dsHDTBTSL.length > 0) this.dsDIACHI.push(rowDIACHI);
                                }
                            }
                        }
                    } else {
                        let kenh_trang = false;
                        let diachitsl = [];
                        if (dt.length == 2) { // Tỉnh đầu khác tỉnh cuối
                            for (let i=0; i<dt.length; i++) {
                                if (!this.dsHDTB[0].thuebao_id || this.dsHDTB[0].thuebao_id == 0) {
                                    kenh_trang = true;
                                    diachitsl = await this.layDiaChiByDiaChiId(this.tinh.tinh_id, 1);
                                } else {
                                    kenh_trang = false;
                                    diachitsl = await this.layDiaChiByDiaChiId(this.tinh.tinh_id, this.dsHDTB[0].thuebao_id);
                                }
                                rowDIACHI = this.copyRowDIACHI(diachitsl[0]);
                                rowHDTBTSL = this.copyRowHDTBTSL(dt[i]);
                                rowHDTBTSL.HDTB_ID = hdtb_tc_id;
                                let diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                                while (diachi_tsl && this.dsDIACHI.find(e => Number(e['DIACHI_ID']) == Number(diachi_tsl))) {
                                    diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                                }
                                rowHDTBTSL.DIACHI_ID = diachi_tsl
                                rowDIACHI.DIACHI_ID = diachi_tsl
                                if (kenh_trang == true)
                                    rowDIACHI.DIACHI = rowHDTBTSL.DIACHI;
                                rowHDTBTSL.DOITAC_ID = null;
                                this.dsHDTBTSL.push(rowHDTBTSL);
                                if (this.dsHDTBTSL && this.dsHDTBTSL.length > 0) this.dsDIACHI.push(rowDIACHI);
                            }
                        } else { // Tỉnh đầu trùng tỉnh cuối (4 row)
                            // Nhét thằng đầu tiên vào
                            let row = dt[0];
                            rowDIACHI = {};
                            rowHDTBTSL = {};
                            diachitsl = await this.layDiaChiBanCheoData(row.diachi_id);
                            if (!this.dsHDTB[0].thuebao_id || this.dsHDTB[0].thuebao_id == 0) {
                                kenh_trang = true;
                                diachitsl = await this.layDiaChiByDiaChiId(this.tinh.tinh_id, 1);
                            } else {
                                kenh_trang = false;
                                diachitsl = await this.layDiaChiByDiaChiId(this.tinh.tinh_id, this.dsHDTB[0].thuebao_id);
                            }
                            rowDIACHI = this.copyRowDIACHI(diachitsl[0]);
                            rowHDTBTSL = this.copyRowHDTBTSL(row);
                            rowHDTBTSL.HDTB_ID = hdtb_tc_id;
                            let diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                            while (diachi_tsl && this.dsDIACHI.find(e => Number(e['DIACHI_ID']) == Number(diachi_tsl))) {
                                diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                            }
                            rowHDTBTSL.DIACHI_ID = diachi_tsl
                            rowDIACHI.DIACHI_ID = diachi_tsl
                            if (kenh_trang == true)
                                rowDIACHI.DIACHI = rowHDTBTSL.DIACHI;
                            rowHDTBTSL.DOITAC_ID = null;
                            this.dsHDTBTSL.push(rowHDTBTSL);
                            if (this.dsHDTBTSL && this.dsHDTBTSL.length > 0) this.dsDIACHI.push(rowDIACHI);
                            // Tìm thằng thứ 2 hợp lý để nhét vào
                            for (let i = 1; i < dt.length; i++)
                            {
                                if (dt[i].daucuoi_id != dt[0].daucuoi_id && dt[i].tinh_dc_id != dt[0].tinh_dc_id)
                                {
                                    let row1 = dt[i];
                                    rowDIACHI = this.copyRowDIACHI(diachitsl[0]);
                                    rowHDTBTSL = this.copyRowHDTBTSL(row1);
                                    rowHDTBTSL.HDTB_ID = hdtb_tc_id;
                                    diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                                    while (diachi_tsl && this.dsDIACHI.find(e => Number(e['DIACHI_ID']) == Number(diachi_tsl))) {
                                        diachi_tsl = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                                    }
                                    rowHDTBTSL.DIACHI_ID = diachi_tsl
                                    rowDIACHI.DIACHI_ID = diachi_tsl
                                    if (kenh_trang == true)
                                        rowDIACHI.DIACHI = rowHDTBTSL.DIACHI;
                                    rowHDTBTSL.DOITAC_ID = null;
                                    this.dsHDTBTSL.push(rowHDTBTSL);
                                    if (this.dsHDTBTSL && this.dsHDTBTSL.length > 0) this.dsDIACHI.push(rowDIACHI);
                                }
                            }
                        }
                    }
                    if (this.dsHDTBTSL.length < 2) {
                        this.$toast.error('Lỗi api get_hd_bancheo_hdbc_tsl: dsHDTBTSL có daucuoi_id và tinh_dc_id trùng nhau')
                        return false
                    }
                }
                console.log('tao_HDTBTSL > dsHDTBTSL, dsDIACHI', this.dsHDTBTSL, this.dsDIACHI, this.dsDIACHI.length)
                return true
            } catch (e) { return false }
        },
        // API UR2.1.003_015
        // Xử lý hàm trong tao_DANGKY_DVGT(hdtb_ban_id)
        layDangKyDVGTBCData: async function(hdtb_ban_id) {
            let result = [];
            await api.getDangKyDVGTBCData(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_DANGKY_DVGT(hdtb_ban_id, hdtb_tc_id)
        copyRowDKDVGT(row) {
            let result = {
                TTTH: row.ttth,
                BLOCK_DAU: row.block_dau,
                GIA_BLOCK_TIEP: row.gia_block_tiep,
                VAT_BLOCK_TIEP: row.vat_block_tiep,
                PHANVUNG_ID: row.phanvung_id,
                NGAY_YC: row.ngay_yc ? moment(row.ngay_yc).format('DD/MM/YYYY HH:mm:ss') : null,
                DICHVUGT_ID: row.dichvugt_id,
                HDTB_ID: row.hdtb_id,
                SL_CHA: row.sl_cha,
                VAT_SD: row.vat_sd,
                CUOC_SD: row.cuoc_sd,
                KIEU_YC: row.kieu_yc,
                HE_SO: row.he_so,
                NOIDUNG: row.noidung,
                BLOCK_TIEP: row.block_tiep
            }
            return result;
        },
        // Khảo sát UR2.1.003_015
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_DANGKY_DVGT: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                let dts = await this.layDangKyDVGTBCData(hdtb_ban_id);
                for (let i=0; i<dts.length; i++) {
                    let rowDKDVGT = this.copyRowDKDVGT(dts[i]);
                    rowDKDVGT.HDTB_ID = hdtb_tc_id;
                    this.dsDK_DVGT.push(rowDKDVGT);
                }
                return true
            } catch (e) { return false }
        },
        // API UR2.1.003_016
        // Xử lý hàm trong tao_DANGKY_DVGT(hdtb_ban_id)
        layHDTBLoaiIPData: async function(hdtb_ban_id) {
            let result = [];
            await api.getHDTBLoaiIPData(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_DANGKY_DVGT(hdtb_ban_id, hdtb_tc_id)
        copyRowHDTB_LoaiIp(row) {
            let result = {
                PHANVUNG_ID: row.phanvung_id,
                HDTB_ID: row.hdtb_id,
                LOAIIP_ID: row.loaiip_id
            }
            return result;
        },
        // Khảo sát UR2.1.003_016
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        taoDuLieu_HDTB_LOAIIP: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                let ds = await this.layHDTBLoaiIPData(hdtb_ban_id);
                let rowHDTB_LoaiIp = {};
                if (ds.length > 0) {
                    for (let i=0; i<ds.length; i++) {
                        rowHDTB_LoaiIp = this.copyRowHDTB_LoaiIp(ds[i]);
                        rowHDTB_LoaiIp.HDTB_ID = hdtb_tc_id;
                        this.dsHDTB_LoaiIp.push(rowHDTB_LoaiIp);
                    }
                } else {
                    rowHDTB_LoaiIp = {
                        HDTB_ID: hdtb_tc_id,
                        LOAIIP_ID: 1
                    };
                    this.dsHDTB_LoaiIp.push(rowHDTB_LoaiIp);
                    rowHDTB_LoaiIp = {
                        HDTB_ID: hdtb_tc_id,
                        LOAIIP_ID: 2
                    };
                    this.dsHDTB_LoaiIp.push(rowHDTB_LoaiIp);
                    rowHDTB_LoaiIp = {
                        HDTB_ID: hdtb_tc_id,
                        LOAIIP_ID: 3
                    };
                    this.dsHDTB_LoaiIp.push(rowHDTB_LoaiIp);
                }
                return true
            } catch (e) { return false }
        },
        // API 1. UR2.1.003_017
        // Xử lý hàm trong tao_diachi_HDTB(hdtb_ban_id, hdtb_tc_id)
        layDiaChiHDTBDiaChiBanCheoData: async function(hdtb_ban_id) {
            let result = [];
            await api.getDiaChiHDTBDiaChiBanCheoData(this.axios, hdtb_ban_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 3. UR2.1.003_017
        // Xử lý hàm trong tao_diachi_HDTB(hdtb_ban_id, hdtb_tc_id)
        layHDBCTSLDiaChiBanCheoData: async function(hdtb_ban_id, tinh_id) {
            let result = [];
            await api.getHDBCTSLDiaChiBanCheoData(this.axios, {
                "hdtb_id": hdtb_ban_id,
                "tinh_id": tinh_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_017
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_diachi_HDTB: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                console.log('tao_diachi_HDTB')
                console.log('---'+hdtb_ban_id+'----'+hdtb_tc_id)                
                let rowDCHDTB = {};
                let rowDIACHI_TB = {};
                let rowDIACHI_LD = {};
                let diachitb_id = 0;
                let diachild_id = 0;
                let ds_dctb = await this.layDiaChiHDTBDiaChiBanCheoData(hdtb_ban_id);
            
                let ds_dctsl = [];
                if (this.dsHDTBTSL.length > 0) {
                    ds_dctsl = await this.layHDBCTSLDiaChiBanCheoData(hdtb_ban_id, this.tinh.tinh_id)
                }
            
                if (ds_dctb.length > 0) {
                    diachitb_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                    while (diachitb_id && this.dsDIACHI.find(e => e['DIACHI_ID'] == diachitb_id)) {
                        diachitb_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                    }
                    // diachi
                    rowDIACHI_TB = {};
                    if (this.dsHDTBTSL.length > 0) {
                        rowDIACHI_TB = this.copyRowDIACHI(ds_dctsl[0]);
                        rowDIACHI_TB.DIACHI = ds_dctb[0].diachi;
                    } else {
                        rowDIACHI_TB = this.copyRowDIACHI(ds_dctb[0]);
                    }
                    rowDIACHI_TB.DIACHI_ID = diachitb_id;
                    rowDCHDTB.DIACHI_ID = diachitb_id;
                }
                let ds_dcld = await this.layDiaChiHDTBDiaChiBanCheoData(hdtb_ban_id);
                if (ds_dcld.length > 0) {
                
                    diachild_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                    while (diachild_id && this.dsDIACHI.find(e => e['DIACHI_ID'] == diachild_id)) {
                        diachild_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                    }
                    // diachi
                    rowDIACHI_LD = {};
                    if (this.dsHDTBTSL.length > 0) {
                        rowDIACHI_LD = this.copyRowDIACHI(ds_dctsl[0]);
                        rowDIACHI_LD.DIACHI = ds_dcld[0].diachi;
                    } else {
                        rowDIACHI_LD = this.copyRowDIACHI(ds_dcld[0]);
                    }
                    rowDIACHI_LD.DIACHI_ID = diachild_id;
                    rowDCHDTB.DIACHILD_ID = diachild_id;
                }                
                if ((ds_dctb.length > 0) || (ds_dcld.length > 0)) {
                    rowDCHDTB.HDTB_ID = hdtb_tc_id;
                    this.dsDCHDTB.push(rowDCHDTB);
                    this.dsDIACHI.push(rowDIACHI_TB);
                    this.dsDIACHI.push(rowDIACHI_LD);
                }
                return true
            } catch (e) { return false }
        },
        // API UR2.1.003_018
        // Xử lý hàm trong taoDuLieu_HD_TTLH(hdtb_ban, hdtb_tc)
        layHDTTLHData: async function(hdtb_ban) {
            let result  = [];
            await api.getHDTTLHData(this.axios, hdtb_ban).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong taoDuLieu_HD_TTLH(hdtb_ban, hdtb_tc)
        copyRow_hdttlh(row) {
            let result = {
                DAUCUOI_ID: row.daucuoi_id,
                TTKH_ID: row.ttkh_id,
                PHANVUNG_ID: row.phanvung_id,
                HD_ID: row.hd_id,
                KIEU_YC: row.kieu_yc,
                HDLH_ID: row.hdlh_id
            }
            return result;
        },
        // Khảo sát UR2.1.003_018
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        taoDuLieu_HD_TTLH: async function(hdtb_ban, hdtb_tc) {
            try {
                // Thuê bao
                let ds = await this.layHDTTLHData(hdtb_ban);
                let row_hdttlh = {};
                if (ds.length > 0) {
                    for (let i=0; i<ds.length; i++) {
                        row_hdttlh = {};
                        row_hdttlh = this.copyRow_hdttlh(ds[i]);
                        for (let j=0; j<this.map_lh_tc_ban.length; j++) {
                            if (this.map_lh_tc_ban[j].value == row_hdttlh.HDLH_ID) {
                                row_hdttlh.HDLH_ID = this.map_lh_tc_ban[j].key;
                            }
                        }
                        row_hdttlh.HD_ID = hdtb_tc;
                        this.ds_hdttlh.push(row_hdttlh);
                    }
                }
                return true
            } catch (e) { return false }
        },
        // API UR2.1.003_019
        // Xử lý hàm trong tao_HDTBCNTT_SUB(hdtb_ban_id, hdtb_tc_id)
        layDsHDTBCNTTTheoHDTBIdBC: async function(hdtb_ban_id, kieu) {
            let result = [];
            await api.layDsHDTBCNTTTheoHDTBIdBC(this.axios, {
                "vhdtb_id": hdtb_ban_id,
                "vkieu": kieu
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 1. UR2.1.003_020
        // Xử lý hàm trong init_Properties(loaitb_id, table_name, append_name, opt_list, is_sub)
        layDsThuocTinhSub: async function(loaitb_id, table_name, append_name, opt_list) {
            let result = [];
            await api.layDsThuocTinhSub(this.axios, {
                "vappend_name": append_name,
                "vloaitb_id": loaitb_id,
                "vopt_list": opt_list,
                "vphanvung_id": this.tt_nd.phanvung_id,
                "vten_bang": table_name
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 2. UR2.1.003_020
        // Xử lý hàm trong init_Properties(loaitb_id, table_name, append_name, opt_list, is_sub)
        layDsThuocTinhV2: async function(loaitb_id, opt_list) {
            let result = [];
            await api.layDsThuocTinhV2(this.axios, {
                "loaitb_id": loaitb_id,
                "opt_list": opt_list
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_020
        // Xử lý hàm trong tao_HDTBCNTT_SUB(hdtb_ban_id, hdtb_tc_id)
        init_Properties: async function(loaitb_id, table_name, append_name, opt_list, is_sub) {
            let ds = [];
            let result = [];
            if (is_sub) {
                ds = await this.layDsThuocTinhSub(loaitb_id, table_name, append_name, opt_list);
            } else {
                ds = await this.layDsThuocTinhV2(loaitb_id, opt_list);
            }
            if (ds.length > 0) {
                for (let i=0; i<ds.length; i++) {
                    let row = ds[i];
                    let ItemList = {
                        sFieldName: row.field_name,
                        sCaption: row.ten_hienthi,
                        iAtt: row.att,
                        iDataType: row.data_type,
                        sMaxValue: row.max_value,
                        sMinvalue: row.min_value,
                        sValue: row.field_value,
                        nFieldLength: row.field_length ? row.field_length : 0,
                        bIsNotNull: row.field_nullable ? row.field_nullable : false,
                        bIsUnique: row.field_unique ? row.field_unique : false,
                        sLookUpTableName: row.parent_table,
                        sLookUpKeyField: row.parent_keyfield,
                        sLookUpValueField: row.parent_descfield,
                        strDepandField: row.depend_field,
                        StrSql: row.lookup_sql
                    }
                    result.push(ItemList);
                }
            }
            return result;
        },
        // Thiếu khảo sát hàm này
        taoCommand:async function(hdtb_tc_id, ItemList, is_sub) {
        },
        // Khảo sát UR2.1.003_019
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_HDTBCNTT_SUB: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                if (this.vdichvuvt_id != this.dichvuvienthong.dichvu_cntt && this.vdichvuvt_id != this.dichvuvienthong.hoinghi_truyenhinh && this.vdichvuvt_id != this.dichvuvienthong.antoan_baomat_tt && this.vdichvuvt_id != this.dichvuvienthong.trungtam_dulieu) {
                    return true;
                }
                let datas = await this.layDsHDTBCNTTTheoHDTBIdBC(hdtb_ban_id, 2);
                if (datas.length == 0) {
                    console.log('layDsHDTBCNTTTheoHDTBIdBC', datas)
                    return true;
                }
                let ItemList_CNTT_SUB = await this.init_Properties(this.loaitb_id, "HDTB_CNTT_SUB", "", "00000", true); // UR2.1.003_020
                for (let i=0; i<datas.length; i++) {
                    for (let j=0; j<ItemList_CNTT_SUB.length; ++j) {
                        if (ItemList_CNTT_SUB[j].sFieldName == "HDTB_ID") {
                            ItemList_CNTT_SUB[j].sValue = hdtb_tc_id;
                        } else if (ItemList_CNTT_SUB[j].sFieldName == "HDTB_ID") { // Không hiểu code C# lại đưa điều kiện này trong khi nó trùng điều kiện trên :)))
                            ItemList_CNTT_SUB[j].sValue = await this.taoKey(this.tinh.tinh_id, "HDTB_CNTT_SUB");
                        } else {
                            ItemList_CNTT_SUB[j].sValue = datas[i][ItemList_CNTT_SUB[j].sFieldName.toString().toLowerCase()];
                        }
                    }
                    await this.taoCommand(hdtb_tc_id, ItemList_CNTT_SUB, true); // Thiếu khảo sát hàm này
                }
                return true
            } catch (e) { return false }
        },
        // Khảo sát UR2.1.003_021
        // Xử lý hàm trong tao_HDBancheo(tinh_thicong, hdkh_id)
        tao_HDTBCNTT: async function(hdtb_ban_id, hdtb_tc_id) {
            try {
                if (this.vdichvuvt_id != this.dichvuvienthong.dichvu_cntt && this.vdichvuvt_id != this.dichvuvienthong.hoinghi_truyenhinh && this.vdichvuvt_id != this.dichvuvienthong.antoan_baomat_tt && this.vdichvuvt_id != this.dichvuvienthong.trungtam_dulieu) {
                    return true;
                }
                let datas = await this.layDsHDTBCNTTTheoHDTBIdBC(hdtb_ban_id, 1);
                let ItemList = await this.init_Properties(this.loaitb_id, "HDTB_CNTT", "", "00000", false);
                for (let i=0; i<ItemList.length; i++) {
                    if (ItemList[i].sFieldName == "HDTB_ID") {
                        ItemList[i].sValue = hdtb_tc_id;
                    } else {
                        ItemList[i].sValue = datas[0][ItemList[i].sFieldName.toString().toLowerCase()];
                    }
                }
                await this.taoCommand(hdtb_tc_id, ItemList, false); // Chưa có khảo sát hàm này
                return true
            } catch (e) { return false }
        },
        // Khảo sát UR2.1.003_008
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDBancheo: async function(tinh_thicong, hdkh_id) {
            try {
                this.dsHDBANCHEO = [];
                let dts = await this.layDataHDBanCheoTheoHDKH(hdkh_id);
                this.dsHDTB = [];
                this.dsHDTBCD = [];
                this.dsHDTBADSL = [];
                this.dsHDTBMGWAN = [];
                this.dsHDTBTSL = [];
                this.dsHDTBIMS = [];
                this.dsDK_DVGT = [];
                this.dsHDTBLYDOHUY = [];
                this.dsDCHDTB = [];
                this.dsTien_HDTB = [];
                this.ds_hdttlh = [];
                this.dsHDTB_LoaiIp = [];
                this.dsDIACHI = [];
                this.dsDCHDKH = [];
                this.dsDCHDTT = [];
                for (let i=0; i<dts.length; i++) {
                    console.log('tao_HDBancheo > layDataHDBanCheoTheoHDKH: dts[i]', dts[i])
                    if ((!dts[i].trang_thai || dts[i].trang_thai == 0 || dts[i].trang_thai == 5) && dts[i].tinh_thicong == tinh_thicong 
                        && (dts[i].hdtb_thicong == null || dts[i].hdtb_thicong == 0 || dts[i].hdtb_thicong == -9999)) {    

                        if ((dts[i].dichvuvt_id == this.dichvuvienthong.tsl || dts[i].dichvuvt_id == 25) && this.dsHDBANCHEO.length > 0) {
                            for (let j=0; j < this.dsHDBANCHEO.length; j++) {
                                if (dts[i].hdtb_ban == this.dsHDBANCHEO[j].HDTB_BAN && dts[i].tinh_thicong != this.dsHDBANCHEO[j].TINH_THICONG) {                                    
                                    let rowHDBANCHEO = this.copyRowHDBANCHEO(dts[i]);
                                    let hdtb_ban_id = rowHDBANCHEO.HDTB_BAN;
                                    let hdtb_tc_id = await this.taoKey(tinh_thicong, "HD_THUEBAO");
                                    rowHDBANCHEO.LOAIHD_BANCHEO = 2;
                                    if (rowHDBANCHEO.THUEBAO_ID_THICONG) {
                                        this.thuebao_id_tc = rowHDBANCHEO.THUEBAO_ID_THICONG;
                                    }
                                    if (rowHDBANCHEO.KHACHHANG_ID_TC) {
                                        this.khachhang_id_tc = rowHDBANCHEO.KHACHHANG_ID_TC;
                                    }
                                    rowHDBANCHEO.TRANG_THAI = 0;
                                    rowHDBANCHEO.HDTB_THICONG = hdtb_tc_id;
                                    rowHDBANCHEO.MANV_DIEUHUONG = this.tt_nd.nhanvien_id;
                                    rowHDBANCHEO.NGUOI_CN = this.tt_nd.nguoi_cn;
                                    rowHDBANCHEO.MAY_CN = this.tt_nd.may_cn;
                                    rowHDBANCHEO.NGAY_CN = this.homnay;
                                    this.dsHDBANCHEO.push(rowHDBANCHEO); 
                                    var result = true
                                    result = await this.tao_HDTB(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_009
                                    if (!result) { 
                                        console.log('tao_HDTB', result)
                                        return false 
                                    }
                                    result = await this.tao_HDTBCD(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_010
                                    if (!result) { 
                                        console.log('tao_HDTBCD', result)
                                        return false 
                                    }
                                    result = await this.tao_HDTBIMS(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_011
                                    if (!result) { 
                                        console.log('tao_HDTBIMS', result)
                                        return false 
                                    }
                                    result = await this.tao_HDTBADSL(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_012
                                    if (!result) { 
                                        console.log('tao_HDTBADSL', result)
                                        return false 
                                    }
                                    result = await this.tao_HDTBMGWAN(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_013
                                    if (!result) { 
                                        console.log('tao_HDTBMGWAN', result)
                                        return false 
                                    }
                                    result = await this.tao_HDTBTSL(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_014
                                    if (!result) { 
                                        console.log('tao_HDTBTSL', result)
                                        return false 
                                    }
                                    result = await this.tao_DANGKY_DVGT(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_015
                                    if (!result) { 
                                        console.log('tao_DANGKY_DVGT', result)
                                        return false 
                                    }
                                    result = await this.taoDuLieu_HDTB_LOAIIP(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_016
                                    if (!result) { 
                                        console.log('taoDuLieu_HDTB_LOAIIP', result)
                                        return false 
                                    }
                                    result = await this.tao_diachi_HDTB(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_017
                                    if (!result) { 
                                        console.log('tao_diachi_HDTB', result)
                                        return false 
                                    }
                                    result = await this.taoDuLieu_HD_TTLH(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_018
                                    if (!result) { 
                                        console.log('taoDuLieu_HD_TTLH', result)
                                        return false 
                                    }
                                }
                            }
                        } else {
                            let rowHDBANCHEO = this.copyRowHDBANCHEO(dts[i]);
                            let hdtb_ban_id = rowHDBANCHEO.HDTB_BAN;
                            let hdtb_tc_id = await this.taoKey(tinh_thicong, "HD_THUEBAO");
                            rowHDBANCHEO.LOAIHD_BANCHEO = 2;
                            if (rowHDBANCHEO.THUEBAO_ID_THICONG)
                            this.thuebao_id_tc = rowHDBANCHEO.THUEBAO_ID_THICONG;
                            if (rowHDBANCHEO.KHACHHANG_ID_TC)
                            this.khachhang_id_tc = rowHDBANCHEO.KHACHHANG_ID_TC;
                            rowHDBANCHEO.TRANG_THAI = 0;
                            rowHDBANCHEO.HDTB_THICONG = hdtb_tc_id;
                            rowHDBANCHEO.MANV_DIEUHUONG = this.tt_nd.nhanvien_id;
                            rowHDBANCHEO.NGUOI_CN = this.tt_nd.nguoi_cn;
                            rowHDBANCHEO.MAY_CN = this.tt_nd.may_cn;
                            rowHDBANCHEO.NGAY_CN = this.homnay;
                            this.dsHDBANCHEO.push(rowHDBANCHEO);
                            var result = true
                            result = await this.tao_HDTB(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_009
                            if (!result) { 
                                console.log('tao_HDTB', result)
                                return false 
                            }
                            result = await this.tao_HDTBCD(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_010
                            if (!result) { 
                                console.log('tao_HDTBCD', result)
                                return false 
                            }
                            result = await this.tao_HDTBIMS(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_011
                            if (!result) { 
                                console.log('tao_HDTBIMS', result)
                                return false 
                            }
                            result = await this.tao_HDTBADSL(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_012
                            if (!result) { 
                                console.log('tao_HDTBADSL', result)
                                return false 
                            }
                            result = await this.tao_HDTBMGWAN(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_013
                            if (!result) { 
                                console.log('tao_HDTBMGWAN', result)
                                return false 
                            }
                            result = await this.tao_HDTBTSL(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_014
                            if (!result) { 
                                console.log('tao_HDTBTSL', result)
                                return false 
                            }
                            result = await this.tao_DANGKY_DVGT(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_015
                            if (!result) { 
                                console.log('tao_DANGKY_DVGT', result)
                                return false 
                            }
                            result = await this.taoDuLieu_HDTB_LOAIIP(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_016
                            if (!result) { 
                                console.log('taoDuLieu_HDTB_LOAIIP', result)
                                return false 
                            }
                            result = await this.tao_diachi_HDTB(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_017
                            if (!result) { 
                                console.log('tao_diachi_HDTB', result)
                                return false 
                            }
                            result = await this.taoDuLieu_HD_TTLH(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_018
                            if (!result) { 
                                console.log('taoDuLieu_HD_TTLH', result)
                                return false 
                            }
                            result = await this.tao_HDTBCNTT_SUB(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_019
                            if (!result) { 
                                console.log('tao_HDTBCNTT_SUB', result)
                                return false 
                            }
                            result = await this.tao_HDTBCNTT(hdtb_ban_id, hdtb_tc_id); // Khảo sát UR2.1.003_021
                            if (!result) { 
                                console.log('tao_HDTBCNTT', result)
                                return false 
                            }
                        }
                    }
                }
                return true
            } catch (e) { return false }
        },
        // API 1. UR2.1.003_022
        // Xử lý hàm trong tao_DIACHIHD(hd_thuebao_ban)
        layHDThueBaoDiaChiHDTBBanCheo: async function(hdkh_id) {
            let result = [];
            await api.getHDThueBaoDiaChiHDTBBanCheo(this.axios, hdkh_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 3. UR2.1.003_022
        // Xử lý hàm trong tao_DIACHIHD(hd_thuebao_ban)
        layDiaChiHDTBBanCheoByLD: async function(hd_thuebao_ban) {
            let result = [];
            await api.getDiaChiHDTBBanCheoByLD(this.axios, hd_thuebao_ban).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 4. UR2.1.003_022
        // Xử lý hàm trong tao_DIACHIHD(hd_thuebao_ban)
        layDiaChiHDKHData: async function(hdkh_id) {
            let result = [];
            await api.getDiaChiHDKHData(this.axios, hdkh_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 6. UR2.1.003_022tao_DIACHIHDth
        // Xử lý hàm trong tao_DIACHIHD(hd_thuebao_ban)
        layDiaChiHDTTHDThueBao: async function(hdkh_id) {
            let result = [];
            await api.getDiaChiHDTTHDThueBao(this.axios, hdkh_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_022
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_DIACHIHD: async function(hd_thuebao_ban) {
            try {
                let rowDIACHI = {};
                let rowDCHDKH = {};
                let rowDCHDTT = {};
                let ds_diachi_ld = [];
                if (this.dsHDTBTSL.length == 0) {
                    ds_diachi_ld = await this.layDiaChiHDTBBanCheoByLD(hd_thuebao_ban);
                } else {
                    ds_diachi_ld = await this.layHDBCTSLDiaChiBanCheoData(hd_thuebao_ban, this.tinh.tinh_id);
                }
                let ds_diachi_tt = await this.layHDThueBaoDiaChiHDTBBanCheo(this.hdkh_id);
                if (ds_diachi_ld.length > 0) {
                    // Lấy địa chỉ lắp đặt bắn vào địa chỉ khách hàng, thanh toán ( tránh lỗi constrain quan_id, phuong_id, pho_id
                    // them diachi_id khachhang_id
                    let ds = await this.layDiaChiHDKHData(this.hdkh_id);
                    if (ds.length > 0) {
                        let diachikh_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                        while (diachikh_id && this.dsDIACHI.find(e => e['DIACHI_ID'] == diachikh_id)) {
                            diachikh_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                        }
                        // diachi
                        rowDIACHI = this.copyRowDIACHI(ds_diachi_ld[0]);
                        rowDIACHI.DIACHI_ID = diachikh_id;
                        rowDIACHI.DIACHI = ds_diachi_tt[0].diachi;
                        this.dsDIACHI.push(rowDIACHI);
                        // diachi_hdkh
                        rowDCHDKH.HDKH_ID = this.hdkh_tc_id;
                        rowDCHDKH.DIACHI_ID = diachikh_id;
                        this.dsDCHDKH.push(rowDCHDKH);
                    }
                    // them dia chi thanh toan
                    ds = await this.layDiaChiHDTTHDThueBao(this.hdkh_id);
                    if (ds.length > 0) {
                        let diachitt_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                        while (diachitt_id && this.dsDIACHI.find(e => e['DIACHI_ID'] == diachitt_id)) {
                            diachitt_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                        }
                        // diachi
                        rowDIACHI = this.copyRowDIACHI(ds_diachi_ld[0]);
                        rowDIACHI.DIACHI_ID = diachitt_id;
                        rowDIACHI.DIACHI = ds_diachi_tt[0].diachi;
                        this.dsDIACHI.push(rowDIACHI);
                        // địa chỉ hdtt
                        rowDCHDTT.DIACHI_ID = diachitt_id;
                        rowDCHDTT.HDTT_ID = this.hdtt_tc_id;
                        this.dsDCHDTT.push(rowDCHDTT);
                    }
                }
                console.log('tao_DIACHIHD > ds_diachi_ld, dsDIACHI', ds_diachi_ld, this.dsDIACHI, this.dsDIACHI.length)
                return true
            } catch (e) { return false }
        },
        // API UR2.1.003_023
        // Xử lý hàm trong tao_HDTB_HEN(donvi, nhanvien_id)
        getHDTBHen: async function(hdtb_ban) {
            let result = [];
            await api.getHDTBHen(this.axios, hdtb_ban).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý hàm trong tao_HDTB_HEN(donvi, nhanvien_id)
        copyRowHDTB_HEN(row) {
            let result = {
                NGUOI_CN: row.nguoi_cn,
                PHANVUNG_ID: row.phanvung_id,
                NGAY_CN: row.ngay_cn ? moment(row.ngay_cn).format('DD/MM/YYYY HH:mm:ss') : null,
                MAY_CN: row.may_cn,
                DONVI_ID: row.donvi_id,
                RID: row.rid,
                HDTB_ID: row.hdtb_id,
                GIOHEN_TU: row.giohen_tu ? moment(row.giohen_tu).format('DD/MM/YYYY HH:mm:ss') : null,
                GIOHEN_DEN: row.giohen_den ? moment(row.giohen_den).format('DD/MM/YYYY HH:mm:ss') : null,
                NHANVIEN_ID: row.nhanvien_id,
                NGUOI_HEN: row.nguoi_hen,
                SODT_HEN: row.sodt_hen,
                DAUCUOI_ID: row.daucuoi_id,
                NOIDUNG: row.noidung,
                KIEU: row.kieu
            }
            return result;
        },
        // Khảo sát UR2.1.003_023
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDTB_HEN: async function(donvi, nhanvien_id) {
            try {
                this.ds_hdtb_hen = [];
                for (let i=0; i<this.dsHDBANCHEO.length; i++) {
                    let hdtb_ban = this.dsHDBANCHEO[i].HDTB_BAN;
                    let hdtb_tc = this.dsHDBANCHEO[i].HDTB_THICONG;
                    let dts = await this.getHDTBHen(hdtb_ban);
                    for (let j=0; j<dts.length; j++) {
                        let row = this.copyRowHDTB_HEN(dts[j]);
                        row.HDTB_ID = hdtb_tc;
                        row.DONVI_ID = donvi;
                        row.NHANVIEN_ID = nhanvien_id;
                        row.RID = await this.taoKey(this.tinh.tinh_id, "HDTB_HEN");
                        this.ds_hdtb_hen.push(row);
                    }
                }
                return true
            } catch (e) { return false }
        },
        getHDTBIdHuongGiaoTNId: async function(hdkh_id, tinh_id) {
            let result = [];
            await api.getHDTBIdHuongGiaoTNId(this.axios, {
                "hdkh_id": hdkh_id,
                "p_tinh_id": tinh_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        banCheoLayTTMaTB: async function(tinhthicong_id, hdtb_id) {
            let result = '';
            await api.banCheoLayTTMaTB(this.axios, {
                "tinhthicong_id": tinhthicong_id,
                "vhdtb_id": hdtb_id
            }).then(res => {
                result = res.data.data ? res.data.data : '';
            });
            return result;
        },
        countTinhThiCong: async function(hdtbban) {
            let result = 0;
            await api.countTinhThiCong(this.axios, hdtbban).then(res => {
                result = res.data.data && res.data.data.length > 0 ? res.data.data[0].num : 0;
            });
            return result;
        },
        banCheoLayDuLieuHNTH: async function(hdtbban) {
            let result = [];
            await api.countTinhThiCong(this.axios, {
                "vhdtb_id": hdtbban
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        tinhGiaoPhieuVipNetBC: async function(hdtb_id) {
            let result = [];
            await api.gettinhGiaoPhieuVipNetBC(this.axios, { params: {
                "vhdtb_id": hdtb_id
            }}).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        copyGiaoVipNet(row) {
            let result = {
                TINH_ID: row.tinh_id,
                HDTB_TINH_ID: row.hdtb_tinh_id,
                HDKH_TINH_ID: row.hdkh_tinh_id,
                PHIEU_TINH_ID: row.phieu_tinh_id,
                HUONGGIAO_ID: row.huonggiao_id,
                LOAIHD_ID: row.loaihd_id,
                MA_TB: row.ma_tb,
                DICHVUVT_ID: row.dichvuvt_id,
                LOAITB_ID: row.loaitb_id,
                DICHVU: row.dichvu,
                LOAIHINH: row.loaihinh,
                DIACHI_TB: row.diachi_tb,
                DIACHI_LD: row.diachi_ld,
                DOITUONG_TB: row.doituong_tb,
                TINH_LD: row.tinh_ld,
                KIEU_YC: row.kieu_yc,
                NGAY_YC: row.ngay_yc ? moment(row.ngay_yc).format('DD/MM/YYYY HH:mm:ss') : null,
                MA_GD: row.ma_gd,
                TEN_KH: row.ten_kh,
                DIACHI_KH: row.diachi_kh,
                SO_DT_KH: row.so_dt_kh,
                LOAI_KH: row.loai_kh,
                NGUOIGIAO: row.nguoigiao,
                NGAYGIAO: row.ngaygiao ? moment(row.ngaygiao).format('DD/MM/YYYY HH:mm:ss') : null,
                DV_GIAO: row.dv_giao,
                SO_DT_GIAO: row.so_dt_giao,
                DONVI_NHAN_ID: row.donvi_nhan_id,
                TEN_LOAIHD: row.ten_loaihd,
                LOG_GIAO: row.log_giao,
                XULY: row.xuly,
                GIOHEN_TU: row.giohen_tu ? moment(row.giohen_tu).format('DD/MM/YYYY HH:mm:ss') : null,
                GIOHEN_DEN: row.giohen_den ? moment(row.giohen_den).format('DD/MM/YYYY HH:mm:ss') : null,
                NOIDUNG_HEN: row.noidung_hen,
                ID_ERP: row.id_erp,
                MA_BSS: row.ma_bss
            }
            return result;
        },
        copyRowDS_BC(row) {
            let result = {
                HUONGGIAO_TN_ID: row.huonggiao_tn_id,
                HDTB_ID: row.hdtb_id
            }
            return result;
        },
        // Khảo sát UR2.1.003_025
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        capNhatTinh: async function() {
            this.error_capnhatchuyentinh = false;
            this.error_msg_capnhatchuyentinh = '';
            let nguoi_chuyen = await this.kiemTraNutChuyenTinhKHDN();
            console.log('capNhatTinh > kiemTraNutChuyenTinhKHDN', nguoi_chuyen)
            if (nguoi_chuyen == "AM_KHDN" || this.ts_kt_tthdkhdn == 0 || nguoi_chuyen == "AM_KHDN_TEST") {
                // region AM CHUYỂN
                console.log('capNhatTinh > dsHDBANCHEO.length, dsHDTB.length, ts_kt_tthdkhdn', this.dsHDBANCHEO.length, this.dsHDTB.length, this.ts_kt_tthdkhdn)
                if (this.dsHDBANCHEO.length == 0 && this.dsHDTB.length == 0 && this.ts_kt_tthdkhdn == 0) return;
                console.log('capNhatTinh > tinh_id, phanvung_id', this.tinh.tinh_id,this.tt_nd.phanvung_id)
                if (this.tinh.tinh_id == this.tt_nd.phanvung_id) {
                    await api.bcGiaoPhieuTSL1Diem(this.axios, {
                        "in_hdtb_id": this.hdtb_id
                    }).then(res => {
                        console.log("api.bcGiaoPhieuTSL1Diem AM CHUYỂN")
                        if (res.data.error_code != 'BSS-00000000') {
                            this.error_capnhatchuyentinh = true;
                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api bcGiaoPhieuTSL1Diem: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                        }
                    }).catch(err => {                        
                        this.error_capnhatchuyentinh = true;
                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api bcGiaoPhieuTSL1Diem: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                    });
                    if (this.error_capnhatchuyentinh) return
                } else {
                    let ds_bc_inhoa = [];
                    let ds_bc = await this.getHDTBIdHuongGiaoTNId(this.hdkh_id, this.tinh.tinh_id);
                    for (let i=0; i<ds_bc.length; i++) {
                        let row = this.copyRowDS_BC(ds_bc[i]);
                        ds_bc_inhoa.push(row);
                    }
                    this.dsDIACHI = [...new Map(this.dsDIACHI.map(item => [item['DIACHI_ID'], item])).values()];
                    let dsDIACHI = this.dsDIACHI
                    for (let item of this.dsDIACHI) {
                        let count = 0;
                        let check = this.dsDCHDKH?.find(e => e['DIACHI_ID'] == item['DIACHI_ID']) || null
                        if (check) count ++
                        check = this.dsDCHDTT?.find(e => e['DIACHI_ID'] == item['DIACHI_ID']) || null
                        if (check) count ++
                        check = this.dsDCHDTB?.find(e => e['DIACHI_ID'] == item['DIACHI_ID'] || e['DIACHILD_ID'] == item['DIACHI_ID']) || null
                        if (check) count ++
                        check = this.dsHDTBTSL?.find(e => e['DIACHI_ID'] == item['DIACHI_ID']) || null
                        if (check) count ++
                        if (!count) dsDIACHI = dsDIACHI.filter(e => e['DIACHI_ID'] != item['DIACHI_ID'])
                    }
                    let p_ds_para = {
                        LOAIHD_ID: this.loaihd_id,
                        DS_BC: ds_bc_inhoa,
                        HD_THANHTOAN: this.dsHDTT,
                        HD_THUEBAO: this.dsHDTB,
                        HD_KHACHHANG:this.dsHDKH,
                        HDKH_SUB: this.ds_hdkh_sub,
                        HDTB_CD: this.dsHDTBCD,
                        HDTB_ADSL: this.dsHDTBADSL,
                        HDTB_MGWAN: this.dsHDTBMGWAN,
                        HDTB_TSL: this.dsHDTBTSL,
                        HDTB_GP: [],
                        HDTB_IMS: this.dsHDTBIMS,
                        DANGKY_DVGT: this.dsDK_DVGT,
                        TIEN_HDTB_TC: this.dsTien_HDTB,
                        DIACHI_HDKH: this.dsDCHDKH,
                        DIACHI_HDTT: this.dsDCHDTT,
                        DIACHI_HDTB: this.dsDCHDTB,
                        DIACHI: dsDIACHI,
                        HDTB_LOAIIP: this.dsHDTB_LoaiIp,
                        HD_BANCHEO: this.dsHDBANCHEO,
                        LYDOHUY_HDTB: [],
                        HDTB_HEN: this.ds_hdtb_hen,
                        HD_LIENHE: this.ds_hdlh,
                        HD_LIENHE_MD: this.ds_hdkh_md,
                        HD_TTLH: this.ds_hdttlh
                    };
                    let p_tt_nd = {
                        DONVI_ID: this.tt_nd.donvi_id,
                        NHANVIEN_ID: this.tt_nd.nhanvien_id,
                        NGUOI_CN: this.tt_nd.nguoi_cn,
                        MAY_CN: this.tt_nd.may_cn,
                        IP_CN: this.tt_nd.ip_cn
                    };
                    await api.fnChuyenTinhTCBanCheo(this.axios, {
                        "tinhthicong_id": this.tinh.tinh_id,
                        "p_ds_para": JSON.stringify(p_ds_para),
                        "p_tt_nd": JSON.stringify(p_tt_nd)
                    }).then(res => {
                        if (res.data.error_code != 'BSS-00000000') {
                            this.error_capnhatchuyentinh = true;
                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api fn_chuyentinh_tc_bancheo: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                        } else if (res.data.data != null) {
                            let obj = JSON.parse(res.data.data);
                            if (obj.ERROR_CODE == 1) {
                                this.checkCT = true
                            }
                            else {                                
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = (obj.MESSAGE_DETAIL ? obj.MESSAGE_DETAIL : obj.MESSAGE);
                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                                this.checkCT = false
                            }
                        }
                    }).catch(err => {
                        this.error_capnhatchuyentinh = true;
                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api fn_chuyentinh_tc_bancheo: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                    });
                    if (this.error_capnhatchuyentinh) return;
                    if (this.dsHDKH.length > 0) {
                        await api.banCheoChuyenTinhTC(this.axios, {
                            "tinhthicong_id": this.tinh.tinh_id,
                            "vhdkh_id": this.dsHDKH[0].HDKH_ID
                        }).then(res => {
                            console.log("api.banCheoChuyenTinhTC AM CHUYỂN")
                            if (res.data.error_code != 'BSS-00000000') {
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = 'Lỗi hàm api bancheo_chuyentinh_tc: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                            }
                        }).catch(err => {
                            this.error_capnhatchuyentinh = true;
                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api bancheo_chuyentinh_tc: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                        });
                        if (this.error_capnhatchuyentinh) return;
                    }
                    if (this.dsHDBANCHEO.length > 0) {
                        for (let i=0; i<this.dsHDBANCHEO.length; i++) {
                            let ma_tb_tinh = await this.banCheoLayTTMaTB(this.tinh.tinh_id, this.dsHDTB[i].HDTB_ID);
                            if (this.dsHDBANCHEO[i].DAUCUOI_ID == ""||this.dsHDBANCHEO[i].DAUCUOI_ID == null) {
                                await api.updateHDBanCheo(this.axios, {
                                    "hdtb_thicong": this.dsHDTB[i].HDTB_ID,
                                    "ma_tb_tinh": ma_tb_tinh,
                                    "hdtb_ban": this.dsHDBANCHEO[i].HDTB_BAN
                                }).then(res => {
                                    console.log("api.updateHDBanCheo AM CHUYỂN with this.dsHDBANCHEO[i].DAUCUOI_ID="+this.dsHDBANCHEO[i].DAUCUOI_ID)
                                    if (res.data.error_code != 'BSS-00000000') {
                                        this.error_capnhatchuyentinh = true;
                                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api update_hd_bancheo: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                                    }
                                }).catch(err => {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi hàm api update_hd_bancheo: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                    this.$toast.error(this.error_msg_capnhatchuyentinh);
                                });
                                if (this.error_capnhatchuyentinh) return
                            } else {
                                //let num = await this.countTinhThiCong(this.dsHDTB[i].HDTB_ID);
                                //if (num > 1) {
                                    await api.updateHDBanCheo(this.axios, {
                                        "hdtb_thicong": this.dsHDTB[i].HDTB_ID,
                                        "ma_tb_tinh": ma_tb_tinh,
                                        "hdtb_ban": this.dsHDBANCHEO[i].HDTB_BAN,
                                        "daucuoi_id": this.dsHDBANCHEO[i].DAUCUOI_ID
                                    }).then(res => {
                                        console.log("api.updateHDBanCheo AM CHUYỂN ")
                                        if (res.data.error_code != 'BSS-00000000') {
                                            this.error_capnhatchuyentinh = true;
                                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api update_hd_bancheo: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                                        }
                                    }).catch(err => {
                                        this.error_capnhatchuyentinh = true;
                                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api update_hd_bancheo: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                                    });
                                    if (this.error_capnhatchuyentinh) return
                                // } else {
                                //     await api.updateHDBanCheo(this.axios, {
                                //         "hdtb_thicong": this.dsHDTB[i].HDTB_ID,
                                //         "ma_tb_tinh": ma_tb_tinh,
                                //         "hdtb_ban": this.dsHDBANCHEO[i].HDTB_BAN
                                //     }).then(res => {
                                //         console.log("api.updateHDBanCheo AM CHUYỂN with num=" + num)
                                //     }).catch(err => {
                                //         this.$toast.error(err.data.message);
                                //         this.error_capnhatchuyentinh = true;
                                //         this.error_msg_capnhatchuyentinh = 'Lỗi hàm api update_hd_bancheo: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                //     });
                                // }
                            }
                            if (this.error_capnhatchuyentinh) return;
                            await api.giaoPhieuVip(this.axios, {
                                "vhdtb_id": this.dsHDBANCHEO[i].HDTB_BAN,
                                "vdonvi_giao_id": this.tt_nd.donvi_id,
                                "vnhanvien_giao_id": this.tt_nd.nhanvien_id,
                                "vtinh_id": this.tinh.tinh_id
                            }).then(res => {
                                console.log("api.giaoPhieuVip AM CHUYỂN")
                                if (res.data.error_code != 'BSS-00000000') {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi hàm api giaoPhieuVip: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                    this.$toast.error(this.error_msg_capnhatchuyentinh);
                                }
                            }).catch(err => {
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = 'Lỗi hàm api giaoPhieuVip: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                            });
                            if (this.error_capnhatchuyentinh) return
                            await api.banCheoCapNhatHDTBDV(this.axios, {
                                "tinhthicong_id": this.tinh.tinh_id,
                                "in_hdtb_id": this.dsHDTB[i].HDTB_ID
                            }).then(res => {
                                console.log("api.banCheoCapNhatHDTBDV AM CHUYỂN")
                                if (res.data.error_code != 'BSS-00000000') {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi hàm api banCheoCapNhatHDTBDV: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                    this.$toast.error(this.error_msg_capnhatchuyentinh);
                                }
                            }).catch(err => {
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = 'Lỗi hàm api banCheoCapNhatHDTBDV: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                            });
                            if (this.error_capnhatchuyentinh) return
                            await api.giaoPhieuVNPTi(this.axios, {
                                "tinhthicong_id": this.tinh.tinh_id,
                                "vhdtb_id": this.dsHDBANCHEO[i].HDTB_BAN,
                                "vdonvi_giao_id": this.tt_nd.donvi_id,
                                "vnhanvien_giao_id": this.tt_nd.nhanvien_id,
                                "vtinh_id" : this.tinh.tinh_id
                            }).then(res => {
                                console.log("api.giaoPhieuVNPTi AM CHUYỂN")
                                if (res.data.error_code != 'BSS-00000000') {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi hàm api giaoPhieuVNPTi: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                    this.$toast.error(this.error_msg_capnhatchuyentinh);
                                }
                            }).catch(err => {
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = 'Lỗi hàm api giaoPhieuVNPTi: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                            });
                            if (this.error_capnhatchuyentinh) return
                            if (this.vdichvuvt_id == this.dichvuvienthong.hoinghi_truyenhinh) {
                                let dt = await this.banCheoLayDuLieuHNTH(this.dsHDBANCHEO[i].HDTB_BAN);
                                if (dt.length > 0) {
                                    await api.capNhatHoiNghiTHBC(this.axios, {
                                        "tinhthicong_id": this.tinh.tinh_id,
                                        "vkieu": 1,
                                        "vhdtb_id": this.dsHDTB[i].HDTB_ID,
                                        "vhdtb_cha_id": dt[0].hdtb_cha_id,
                                        "vthuebao_cha_id": dt[0].thuebao_cha_id,
                                        "vmatb_tn": dt[0].matb_tn,
                                        "vmadoicap" : dt[0].madoicap,
                                        "vhdtb_con_id" : dt[0].hdtb_con_id,
                                        "vthuebao_con_id" : dt[0].hdtb_con_id,
                                        "vtinh_id" : dt[0].tinh_id
                                    }).then(res => {
                                        console.log("api.capNhatHoiNghiTHBC AM CHUYỂN")
                                        if (res.data.error_code != 'BSS-00000000') {
                                            this.error_capnhatchuyentinh = true;
                                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api capNhatHoiNghiTHBC: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                                        }
                                    }).catch(err => {
                                        this.error_capnhatchuyentinh = true;
                                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api capNhatHoiNghiTHBC: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                                    });
                                    if (this.error_capnhatchuyentinh) return
                                }
                            }
                        }
                        if (this.ts_kt_tthdkhdn == 1 && nguoi_chuyen == "AM_KHDN") {
                            // region Đoạn này insert vào net của KHDN
                            let list = [...new Map(this.dsHDBANCHEO.map(item => [item['HDTB_BAN'], item])).values()];
                            if (list.length > 0) {
                                for(let i=0; i<list.length; i++) {
                                    if (this.dsHDTB[i].DICHVUVT_ID == this.dichvuvienthong.megawan || this.dsHDTB[i].DICHVUVT_ID == this.dichvuvienthong.metronet || this.dsHDTB[i].DICHVUVT_ID == 26 || this.dsHDTB[i].DICHVUVT_ID == this.dichvuvienthong.tsl || this.dsHDTB[i].DICHVUVT_ID == 25 || this.dsHDTB[i].LOAITB_ID == this.loaihinhtb.hnth_ngn || this.dsHDTB[i].LOAITB_ID == this.loaihinhtb.tdth_truyenhinh) {
                                        await api.giaoPhieuNet(this.axios, {
                                            "vhdtb_id": list[i].HDTB_BAN,
                                            "vdonvi_giao_id": this.tt_nd.donvi_id,
                                            "vnhanvien_giao_id": this.tt_nd.nhanvien_id
                                        }).then(res => {
                                            console.log("api.giaoPhieuNet AM_KHDN")
                                            if (res.data.error_code != 'BSS-00000000') {
                                                this.error_capnhatchuyentinh = true;
                                                this.error_msg_capnhatchuyentinh = 'Lỗi hàm api giaoPhieuNet: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                                            }
                                        }).catch(err => {
                                            this.error_capnhatchuyentinh = true;
                                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api giaoPhieuNet: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                                        });
                                        if (this.error_capnhatchuyentinh) return
                                    }
                                }
                            }
                            // endregion
                        } else if (this.ts_kt_tthdkhdn == 0) {
                            if (this.ts_giao_vip_net == 1) {
                                // region đoạn này insert vào NET VIP của tỉnh
                                let str_hdtb = "";
                                let cop = [];
                                for (let i=0; i<this.dsHDBANCHEO.length; i++) {
                                    let c = {
                                        HDTB_BAN: this.dsHDBANCHEO[i].HDTB_BAN
                                    }
                                    cop.push(c);
                                    if (str_hdtb.length > 0) {
                                        str_hdtb += "," + this.dsHDBANCHEO[i].HDTB_BAN;
                                    } else {
                                        str_hdtb += this.dsHDBANCHEO[i].HDTB_BAN;
                                    }
                                }
                                // Giao Vip, Net tại tỉnh
                                await api.tinhGiaoPhieuVipNetBC(this.axios, {
                                    "vjson_hdtb_id": JSON.stringify(cop),
                                    "vdonvi_giao_id": this.tt_nd.donvi_id,
                                    "vnhanvien_giao_id": this.tt_nd.nhanvien_id,
                                    "vkieu": 3
                                }).then(res => {
                                    console.log("api.tinhGiaoPhieuVipNetBC")
                                    if (res.data.error_code != 'BSS-00000000') {
                                        this.error_capnhatchuyentinh = true;
                                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api tinhGiaoPhieuVipNetBC: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                                    }
                                }).catch(err => {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi hàm api tinhGiaoPhieuVipNetBC: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                    this.$toast.error(this.error_msg_capnhatchuyentinh);
                                });
                                if (this.error_capnhatchuyentinh) return
                                // Giao Vip, Net trên hệ thống KHDN
                                if (str_hdtb.length > 0) {
                                    let ds_khdn = await this.tinhGiaoPhieuVipNetBC(str_hdtb);
                                    if (ds_khdn.length > 0) {
                                        let js_ds_khdn = [];
                                        for (let i=0; i<ds_khdn.length; i++) {
                                            let row = this.copyGiaoVipNet(ds_khdn[i]);
                                            js_ds_khdn.push(row);
                                        }
                                        await api.capNhatHoiNghiTHBC2(this.axios, {
                                            "tinhthicong_id": 97,
                                            "vjson_khdn": JSON.stringify(js_ds_khdn)
                                        }).then(res => {
                                            console.log("api.capNhatHoiNghiTHBC2")
                                            if (res.data.error_code != 'BSS-00000000') {
                                                this.error_capnhatchuyentinh = true;
                                                this.error_msg_capnhatchuyentinh = 'Lỗi hàm api capNhatHoiNghiTHBC2: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                                            }
                                        }).catch(err => {
                                            this.error_capnhatchuyentinh = true;
                                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api capNhatHoiNghiTHBC2: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                                        });
                                        if (this.error_capnhatchuyentinh) return
                                    }
                                }
                                // endregion
                            }
                        }
                        // region Cập nhật ngày yc ht
                        let _list = [...new Map(this.dsHDBANCHEO.map(item => [item['HDTB_BAN'], item])).values()];
                        if (_list.length > 0) {
                            for(let i=0; i<_list.length; i++) {
                                if (this.dsHDTB[i].DICHVUVT_ID == this.dichvuvienthong.megawan || this.dsHDTB[i].DICHVUVT_ID == this.dichvuvienthong.metronet || this.dsHDTB[i].DICHVUVT_ID == 26 || this.dsHDTB[i].DICHVUVT_ID == this.dichvuvienthong.tsl || this.dsHDTB[i].DICHVUVT_ID == 25 || this.dsHDTB[i].LOAITB_ID == this.loaihinhtb.hnth_ngn || this.dsHDTB[i].LOAITB_ID == this.loaihinhtb.tdth_truyenhinh) {
                                    await api.capNgayYCHTVipNet(this.axios, {
                                        "vhdtb_id": _list[i].HDTB_BAN,
                                        "vngay_yc": this.frm_capnhat.ngayycht_netvip + " 00:00:00"
                                    }).then(res => {
                                        console.log("api.capNgayYCHTVipNet")
                                        if (res.data.error_code != 'BSS-00000000') {
                                            this.error_capnhatchuyentinh = true;
                                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api capNgayYCHTVipNet: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                                        }
                                    }).catch(err => {
                                        this.error_capnhatchuyentinh = true;
                                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api capNgayYCHTVipNet: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                                    });
                                    if (this.error_capnhatchuyentinh) return
                                }
                            }
                        }
                        // endregion
                    }
                }
                // endregion
            } else if (nguoi_chuyen == "NET") {
                // region NET
                console.log('capNhatTinh > NET > dsHDBANCHEO, dsHDTB', this.dsHDBANCHEO, this.dsHDTB)
                if (this.dsHDBANCHEO.length == 0 && this.dsHDTB.length == 0 && this.ts_kt_tthdkhdn == 0) return;
                if (this.tinh.tinh_id == this.tt_nd.phanvung_id && this.ts_update_qt_tsl == 1) {
                    await api.bcGiaoPhieuTSL1Diem(this.axios, {
                        "in_hdtb_id": hdtb_id
                    }).then(res => {
                        console.log("api.bcGiaoPhieuTSL1Diem NET")
                        if (res.data.error_code != 'BSS-00000000') {
                            this.error_capnhatchuyentinh = true;
                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api bcGiaoPhieuTSL1Diem: ' + (res.data.message_detail ? res.data.message_detail : res.data.message)
                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                        }
                    }).catch(err => {
                        this.error_capnhatchuyentinh = true;
                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api bcGiaoPhieuTSL1Diem: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                    });
                    if (this.error_capnhatchuyentinh) return
                } else {
                    let ds_bc_inhoa = [];
                    let ds_bc = await this.getHDTBIdHuongGiaoTNId(this.hdkh_id, this.tinh.tinh_id);
                    for (let i=0; i<ds_bc.length; i++) {
                        let row = this.copyRowDS_BC(ds_bc[i]);
                        ds_bc_inhoa.push(row);
                    }
                    this.dsDIACHI = [...new Map(this.dsDIACHI.map(item => [item['DIACHI_ID'], item])).values()];
                    let dsDIACHI = this.dsDIACHI
                    for (let item of this.dsDIACHI) {
                        let count = 0;
                        let check = this.dsDCHDKH?.find(e => e['DIACHI_ID'] == item['DIACHI_ID']) || null
                        if (check) count ++
                        check = this.dsDCHDTT?.find(e => e['DIACHI_ID'] == item['DIACHI_ID']) || null
                        if (check) count ++
                        check = this.dsDCHDTB?.find(e => e['DIACHI_ID'] == item['DIACHI_ID'] || e['DIACHILD_ID'] == item['DIACHI_ID']) || null
                        if (check) count ++
                        check = this.dsHDTBTSL?.find(e => e['DIACHI_ID'] == item['DIACHI_ID']) || null
                        if (check) count ++
                        if (!count) dsDIACHI = dsDIACHI.filter(e => e['DIACHI_ID'] != item['DIACHI_ID'])
                    }
                    let p_ds_para = {
                        LOAIHD_ID: this.loaihd_id,
                        DS_BC: ds_bc_inhoa,
                        HD_THANHTOAN: this.dsHDTT,
                        HD_THUEBAO: this.dsHDTB,
                        HD_KHACHHANG:this.dsHDKH,
                        HDKH_SUB: this.ds_hdkh_sub,
                        HDTB_CD: this.dsHDTBCD,
                        HDTB_ADSL: this.dsHDTBADSL,
                        HDTB_MGWAN: this.dsHDTBMGWAN,
                        HDTB_TSL: this.dsHDTBTSL,
                        HDTB_GP: [],
                        HDTB_IMS: this.dsHDTBIMS,
                        DANGKY_DVGT: this.dsDK_DVGT,
                        TIEN_HDTB_TC: this.dsTien_HDTB,
                        DIACHI_HDKH: this.dsDCHDKH,
                        DIACHI_HDTT: this.dsDCHDTT,
                        DIACHI_HDTB: this.dsDCHDTB,
                        DIACHI: dsDIACHI,
                        HDTB_LOAIIP: this.dsHDTB_LoaiIp,
                        HD_BANCHEO: this.dsHDBANCHEO,
                        LYDOHUY_HDTB: [],
                        HDTB_HEN: this.ds_hdtb_hen,
                        HD_LIENHE: this.ds_hdlh,
                        HD_LIENHE_MD: this.ds_hdkh_md,
                        HD_TTLH: this.ds_hdttlh
                    };
                    let p_tt_nd = {
                        DONVI_ID: this.tt_nd.donvi_id,
                        NHANVIEN_ID: this.tt_nd.nhanvien_id,
                        NGUOI_CN: this.tt_nd.nguoi_cn,
                        MAY_CN: this.tt_nd.may_cn,
                        IP_CN: this.tt_nd.ip_cn
                    };
                    await api.fnChuyenTinhTCBanCheo(this.axios, {
                        "tinhthicong_id": this.tinh.tinh_id,
                        "p_ds_para": JSON.stringify(p_ds_para),
                        "p_tt_nd": JSON.stringify(p_tt_nd)
                    }).then(res => {
                        if (res.data.error_code != 'BSS-00000000') {
                            this.error_capnhatchuyentinh = true;
                            this.error_msg_capnhatchuyentinh = 'Lỗi fn_chuyentinh_tc_bancheo: ' + (res.data.message_detail ? res.data.message_detail : res.data.message);
                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                        }
                        if (res.data.data != null) {
                            let obj = JSON.parse(res.data.data);
                            if (obj.ERROR_CODE == 1) {
                                this.checkCT = true
                            }
                            else {
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = (obj.MESSAGE_DETAIL ? obj.MESSAGE_DETAIL : obj.MESSAGE);
                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                                this.checkCT = false
                            }
                        }
                    }).catch(err => {
                        this.error_capnhatchuyentinh = true;
                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api fn_chuyentinh_tc_bancheo: ' + (err.data.message_detail ? err.data.message_detail : err.data.message);
                        this.$toast.error(this.error_msg_capnhatchuyentinh);
                    });
                    if (this.error_capnhatchuyentinh) {
                        return
                    }
                    if (this.dsHDKH && this.dsHDKH.length > 0) {
                        await api.banCheoChuyenTinhTC(this.axios, {
                            "tinhthicong_id": this.tinh.tinh_id,
                            "vhdkh_id": this.dsHDKH[0].HDKH_ID
                        }).then(res => {
                            console.log("api.banCheoChuyenTinhTC NET")
                            if (res.data.error_code != 'BSS-00000000') {
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = 'Lỗi api.bancheo_chuyentinh_tc NET: ' + (res.data.message_detail ? res.data.message_detail : res.data.message);
                                this.$toast.error(this.error_msg_capnhatchuyentinh);
                            }
                        }).catch(err => {
                            this.error_capnhatchuyentinh = true;
                            this.error_msg_capnhatchuyentinh = 'Lỗi hàm api bancheo_chuyentinh_tc: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                            this.$toast.error(this.error_msg_capnhatchuyentinh);
                        });
                        if (this.error_capnhatchuyentinh) return
                    }
                    if (this.error_capnhatchuyentinh) {
                        return
                    }
                    if (this.dsHDBANCHEO && this.dsHDBANCHEO.length > 0) {
                        for (let i=0; i<this.dsHDBANCHEO.length; i++) {
                            let ma_tb_tinh = await this.banCheoLayTTMaTB(this.tinh.tinh_id, this.dsHDTB[i].HDTB_ID);
                            if (this.dsHDBANCHEO[i].DAUCUOI_ID == null || this.dsHDBANCHEO[i].DAUCUOI_ID.toString() == "") {
                                await api.updateHDBanCheo(this.axios, {
                                    "hdtb_thicong": this.dsHDTB[i].HDTB_ID,
                                    "ma_tb_tinh": ma_tb_tinh,
                                    "hdtb_ban": this.dsHDBANCHEO[i].HDTB_BAN
                                }).then(res => {
                                    console.log("api.updateHDBanCheo NET with this.dsHDBANCHEO[i].DAUCUOI_ID="+this.dsHDBANCHEO[i].DAUCUOI_ID)
                                    if (res.data.error_code != 'BSS-00000000') {
                                        this.error_capnhatchuyentinh = true;
                                        this.error_msg_capnhatchuyentinh = 'Lỗi api.update_hd_bancheo NET: ' + (res.data.message_detail ? res.data.message_detail : res.data.message);
                                        this.$toast.error(this.error_msg_capnhatchuyentinh)
                                    }
                                }).catch(err => {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi hàm api update_hd_bancheo: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                    this.$toast.error(error_msg_capnhatchuyentinh);
                                });
                            } else {
                                //let num = await this.countTinhThiCong(this.dsHDTB[i].HDTB_ID);
                                //if (num > 1) {
                                await api.updateHDBanCheo(this.axios, {
                                    "hdtb_thicong": this.dsHDTB[i].HDTB_ID,
                                    "ma_tb_tinh": ma_tb_tinh,
                                    "hdtb_ban": this.dsHDBANCHEO[i].HDTB_BAN,
                                    "daucuoi_id": this.dsHDBANCHEO[i].DAUCUOI_ID
                                }).then(res => {
                                    console.log("api.updateHDBanCheo NET with this.dsHDBANCHEO[i].DAUCUOI_ID="+this.dsHDBANCHEO[i].DAUCUOI_ID)
                                    if (res.data.error_code != 'BSS-00000000') {
                                        this.error_capnhatchuyentinh = true;
                                        this.error_msg_capnhatchuyentinh = 'Lỗi api.update_hd_bancheo NET: ' + (res.data.message_detail ? res.data.message_detail : res.data.message);
                                        this.$toast.error(this.error_msg_capnhatchuyentinh)
                                    }
                                }).catch(err => {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi hàm api update_hd_bancheo: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                    this.$toast.error(error_msg_capnhatchuyentinh);
                                });
                                // } else {
                                //     await api.updateHDBanCheo(this.axios, {
                                //         "hdtb_thicong": this.dsHDTB[i].HDTB_ID,
                                //         "ma_tb_tinh": ma_tb_tinh,
                                //         "hdtb_ban": this.dsHDBANCHEO[i].HDTB_BAN
                                //     }).then(res => {
                                //         console.log("api.updateHDBanCheo NET with this.dsHDBANCHEO[i].DAUCUOI_ID="+this.dsHDBANCHEO[i].DAUCUOI_ID)
                                //     }).catch(err => {
                                //         this.$toast.error(err.data.message);
                                //         this.error_capnhatchuyentinh = true;
                                //         this.error_msg_capnhatchuyentinh = 'Lỗi hàm api update_hd_bancheo: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                //     });
                                // }
                            }
                            if (this.error_capnhatchuyentinh) {
                                return false
                            }
                            // Thiếu hàm giao phiếu cho net KHDN
                            await api.capNhatPhieuNetGiaoKHDN(this.axios, {
                                "tinhthicong_id": 97,
                                "vhdtb_id": this.dsHDBANCHEO[i].HDTB_BAN,
                                "vdonvi_giao_id": this.tt_nd.donvi_id,
                                "vnhanvien_giao_id": this.tt_nd.nhanvien_id,
                                "vtinh_id": this.tinh.tinh_id
                            }).then(res => {
                                console.log("api.capNhatPhieuNetGiaoKHDN NET")
                                if (res.data.error_code != 'BSS-00000000') {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi api.capnhat_phieu_net_giao_khdn NET: ' + (res.data.message_detail ? res.data.message_detail : res.data.message);
                                    this.$toast.error(error_msg_capnhatchuyentinh);
                                }
                            }).catch(err => {
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = 'Lỗi hàm api capnhat_phieu_net_giao_khdn NET: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                this.$toast.error(error_msg_capnhatchuyentinh);
                            });
                            if (this.error_capnhatchuyentinh) return false
                            await api.banCheoCapNhatHDTBDV(this.axios, {
                                "tinhthicong_id": this.tinh.tinh_id,
                                "in_hdtb_id": this.dsHDTB[i].HDTB_ID
                            }).then(res => {
                                console.log("api.banCheoCapNhatHDTBDV NET")
                                if (res.data.error_code != 'BSS-00000000') {
                                    this.error_capnhatchuyentinh = true;
                                    this.error_msg_capnhatchuyentinh = 'Lỗi api.bancheo_capnhat_hdtb_dv NET: ' + (res.data.message_detail ? res.data.message_detail : res.data.message);
                                    this.$toast.error(error_msg_capnhatchuyentinh);
                                }
                            }).catch(err => {
                                this.error_capnhatchuyentinh = true;
                                this.error_msg_capnhatchuyentinh = 'Lỗi hàm api bancheo_capnhat_hdtb_dv NET: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                this.$toast.error(error_msg_capnhatchuyentinh);
                            });
                            if (this.error_capnhatchuyentinh) return false
                            if (this.vdichvuvt_id == this.dichvuvienthong.hoinghi_truyenhinh) {
                                let dt = await this.banCheoLayDuLieuHNTH(this.dsHDBANCHEO[i].HDTB_BAN);
                                if (dt.length > 0) {
                                    await api.capNhatHoiNghiTHBC(this.axios, {
                                        "tinhthicong_id": this.tinh.tinh_id,
                                        "vkieu": 1,
                                        "vhdtb_id": this.dsHDTB[i].HDTB_ID,
                                        "vhdtb_cha_id": dt[0].hdtb_cha_id,
                                        "vthuebao_cha_id": dt[0].thuebao_cha_id,
                                        "vmatb_tn": dt[0].matb_tn,
                                        "vmadoicap" : dt[0].madoicap,
                                        "vhdtb_con_id" : dt[0].hdtb_con_id,
                                        "vthuebao_con_id" : dt[0].hdtb_con_id,
                                        "vtinh_id" : dt[0].tinh_id
                                    }).then(res => {
                                        console.log("api.capnhat_hoinghith_bc NET")
                                        if (res.data.error_code != 'BSS-00000000') {
                                            this.error_capnhatchuyentinh = true;
                                            this.error_msg_capnhatchuyentinh = 'Lỗi api.capnhat_hoinghith_bc NET: ' + (res.data.message_detail ? res.data.message_detail : res.data.message);
                                            this.$toast.error(error_msg_capnhatchuyentinh);
                                        }
                                    }).catch(err => {
                                        this.error_capnhatchuyentinh = true;
                                        this.error_msg_capnhatchuyentinh = 'Lỗi hàm api capnhat_hoinghith_bc NET: ' + (err.data.message_detail ? err.data.message_detail : err.data.message)
                                        this.$toast.error(error_msg_capnhatchuyentinh);
                                    });
                                }
                            }
                            if (this.error_capnhatchuyentinh) return false
                        }
                    }
                }
            }
        },
        // API 1. UR2.1.003_024
        // Xử lý hàm trong tao_HDKH_v2(row)
        getBanCheoHDKhachHangData: async function(hdkh_id) {
            let result = [];
            await api.getBanCheoHDKhachHangData(this.axios, hdkh_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 4. UR2.1.003_024
        // Xử lý hàm trong tao_HDKH_v2(row)
        getThueBaoIdThiCongFromHDBanCheo: async function(hdtb_id) {
            let result = [];
            await api.getThueBaoIdThiCongFromHDBanCheo(this.axios, hdtb_id).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 6.2 UR2.1.003_024
        // Xử lý hàm trong tao_HDKH_v2(row)
        getThueBaoKNIdByKhachHangId: async function(khachhang_id,tinh_tc) {
            let result = [];
            await api.getThueBaoKNIdByKhachHangId(this.axios, {
                "khachhangbanid": khachhang_id,
                "tinhthicong_id": tinh_tc
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // API 10. UR2.1.003_024
        // Xử lý hàm trong tao_HDKH_v2(row)
        getDBKhachHangData: async function(khachhang_id_tc) {
            let result = [];
            await api.getDBKhachHangData(this.axios, khachhang_id_tc).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_024
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDKH_v2: async function(row) {
            try {    
                this.dsHDKH = [];
                let dts = await this.getBanCheoHDKhachHangData(this.hdkh_id);
                let rowHDKH = {};
                // let hdtb_id = this.ds_tb.data[row].hdtb_id ? this.ds_tb.data[row].hdtb_id : 0;
                // let res = await this.getThueBaoIdThiCongFromHDBanCheo(hdtb_id);
                this.thuebao_id_tc = this.ds_tb.data[row]['thuebao_id_thicong']
                this.thanhtoan_id_tc = this.ds_tb.data[row]['thanhtoan_id_tc']
                if (!dts[0].khachhang_id || dts[0].khachhang_id.toString().length == 0) {
                    rowHDKH = this.copyRowHDKH(dts[0]);
                    rowHDKH.LOAIHD_ID = dts[0].loaihd_id;
                    rowHDKH.MA_GD = await this.sinhMaGD(this.tinh.tinh_id, dts[0].loaihd_id);
                    rowHDKH.MA_HD = await this.sinhMaHD(this.tinh.tinh_id, this.tinh.donvi_id);
                    rowHDKH.CTV_ID = null;
                    rowHDKH.MA_KH = await this.sinhMaKH(this.tinh.tinh_id, this.tinh.donvi_id);
                    rowHDKH.NGANHANG_ID = null;
                } else {
                    this.khachhang_id_tc = await this.getThueBaoKNIdByKhachHangId(dts[0].khachhang_id,this.tinh.tinh_id);
                    if (!this.khachhang_id_tc || this.khachhang_id_tc == 0 || this.thuebao_id_tc == 0 || this.thuebao_id_tc == -1) {
                        rowHDKH = this.copyRowHDKH(dts[0]);
                        rowHDKH.KHACHHANG_ID = null;
                        rowHDKH.MA_HD = await this.sinhMaHD(this.tinh.tinh_id, this.tinh.donvi_id);
                        rowHDKH.MA_KH = await this.sinhMaKH(this.tinh.tinh_id, this.tinh.donvi_id);
                        rowHDKH.NGANHANG_ID = null;
                    } else {
                        if (dts[0].loaihd_id != this.loaihopdong.dat_moi && dts[0].loaihd_id != this.loaihopdong.chuyendoi_lh) {
                            let _vdichvuvt_id = this.ds_tb.data[row].dichvuvt_id;
                            let row_data = await this.getDBTheoThueBaoId(this.tinh.tinh_id, this.thuebao_id_tc, _vdichvuvt_id, 0, 2);
                            if (row_data.length > 0) {
                                rowHDKH = this.copyRowHDKH(row_data[0]);
                            } else {
                                this.$toast.error("Lệch dịch vụ giữa tỉnh thi công và tỉnh bán");
                                return false;
                            }
                        } else {
                            let dbkh_tc = await this.getDBKhachHangData(this.khachhang_id_tc);
                            if (dbkh_tc.length > 0) {
                                rowHDKH = this.copyRowHDKH(dts[0]);
                                rowHDKH.KHACHHANG_ID = this.khachhang_id_tc;
                                rowHDKH.MA_HD = dbkh_tc[0].ma_hd;
                                rowHDKH.MA_KH = dbkh_tc[0].ma_kh;
                            } else {
                                rowHDKH = this.copyRowHDKH(dts[0]);
                                rowHDKH.KHACHHANG_ID = null;
                                rowHDKH.MA_HD = await this.sinhMaHD(this.tinh.tinh_id, this.tinh.donvi_id);
                                rowHDKH.MA_KH = await this.sinhMaKH(this.tinh.tinh_id, this.tinh.donvi_id);
                            }
                        }
                    }
                    rowHDKH.LOAIHD_ID = dts[0].loaihd_id;
                    rowHDKH.MA_GD = await this.sinhMaGD(this.tinh.tinh_id, rowHDKH.LOAIHD_ID);
                }
                rowHDKH.NGAY_YC = this.homnay;
                rowHDKH.HDKH_ID = this.hdkh_tc_id;
                rowHDKH.NHANVIEN_ID = this.tinh.nhanvien_id;
                rowHDKH.DONVI_ID = this.tinh.donvi_id;
                rowHDKH.LOAIGT_ID = 9;
                rowHDKH.CTV_ID = null; // Thêm theo yêu cầu của Nguyễn Tuấn ANh lúc 4h30 19/04/2023
                this.loaihd_id = rowHDKH.LOAIHD_ID;
                this.dsHDKH.push(rowHDKH);
                return true
            } catch (e) { 
                console.log("tao_HDKH_v2", e) 
                return false;
            }        
        },
        copyRowHDTT(row) {
            let result = {
                MATB_DD: row.matb_dd,
                HDKH_ID: row.hdkh_id,
                NGUOI_CN: row.nguoi_cn,
                DIACHI_CT: row.diachi_ct,
                NGAY_CN: row.ngay_cn ? moment(row.ngay_cn).format('DD/MM/YYYY HH:mm:ss') : null,
                TIEN_DM: row.tien_dm,
                MN_NSNN: row.mn_nsnn,
                MAY_CN: row.may_cn,
                HDDT_ID: row.hddt_id,
                HDTT_ID_HNI: row.hdtt_id_hni,
                MST: row.mst,
                MA_NDKT: row.ma_ndkt,
                DV_QHNS: row.dv_qhns,
                HTTC_ID: row.httc_id,
                DIACHI_BC: row.diachi_bc,
                TEN_TT: row.ten_tt,
                DIACHI_TT: row.diachi_tt,
                SO_DT: row.so_dt,
                EMAIL: row.email,
                PHANVUNG_ID: row.phanvung_id,
                GHICHU: row.ghichu,
                MA_TT: row.ma_tt,
                DONVI_ID: row.donvi_id,
                TUYENTHU_ID: row.tuyenthu_id,
                HDMIG_ID: row.hdmig_id,
                STK: row.stk,
                THANHTOAN_ID: row.thanhtoan_id,
                NGANHANG_ID: row.nganhang_id,
                MACHUONG: row.machuong,
                MN_KT: row.mn_kt,
                IP_CN: row.ip_cn,
                CHU_TK: row.chu_tk,
                HDTT_ID: row.hdtt_id,
                HTTT_ID: row.httt_id
            }
            return result;
        },
        // API 3. UR2.1.003_026
        // Xử lý hàm trong tao_HDTT_v2()
        getDBThanhToan: async function(tinhthicong_id, thanhtoan_id_tc) {
            let result = [];
            await api.getDBThanhToan(this.axios, {
                "tinhthicong_id": tinhthicong_id,
                "thanhtoan_id": thanhtoan_id_tc
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_026
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDTT_v2: async function() {
            try {
                this.dsHDTT = [];
                let dts = await this.layHDThanhToanData(this.hdkh_id);
                let rowHDTT = {};
                if (dts.length > 0) {
                    this.hdtt_tc_id = await this.taoKey(this.tinh.tinh_id, "HD_THANHTOAN");
                    rowHDTT = this.copyRowHDTT(dts[0]);
                    rowHDTT.HDKH_ID = this.hdkh_tc_id;
                    rowHDTT.HDTT_ID = this.hdtt_tc_id;
                    rowHDTT.NGANHANG_ID = null;
                    rowHDTT.HTTT_ID = 1;
                    rowHDTT.TUYENTHU_ID = 99999;
                    if (this.thanhtoan_id_tc > 0 && this.thanhtoan_id_tc != null) {
                        rowHDTT.THANHTOAN_ID = this.thanhtoan_id_tc;
                        //Sửa theo yêu cầu của AnhNT
                        let rs = await this.getDBThanhToan(this.tinh.tinh_id, this.thanhtoan_id_tc); // chưa có hàm api
                        if (rs.length > 0) {
                            rowHDTT.MA_TT = rs[0].ma_tt;
                            rowHDTT.DONVI_ID = rs[0].donvi_id ? rs[0].donvi_id : 0;
                            if (rowHDTT.DONVI_ID == 0) {
                                this.$toast.error("Thiếu dữ liệu DONVI_ID thanh toán tại tỉnh thi công");
                                return false
                            }
                            rowHDTT.TUYENTHU_ID = rs[0].tuyenthu_id ? rs[0].tuyenthu_id : 0;
                            if (rowHDTT.TUYENTHU_ID == 0) {
                                this.$toast.error("Thiếu dữ liệu TUYENTHU_ID tại tỉnh thi công");
                                return false
                            }
                        }
                    } else {
                        rowHDTT.THANHTOAN_ID = null;
                        let ma_tt = await this.sinhMaTT();
                        if (!ma_tt) return false
                        else rowHDTT.MA_TT = ma_tt
                    }
                    this.dsHDTT.push(rowHDTT);                    
                }
                return true
            } catch (e) { 
                console.log('tao_HDTT_v2', e) 
                return false
            }
        },
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        taoDuLieu_HD_LIENHE_v2: async function() {
            try {
                this.ds_hdlh = [];
                this.ds_hdkh_md = [];
                let ds = await this.layHdTTLH_HdLienHe_HdBanCheo(this.hdkh_id, this.tinh.tinh_id);
                let row1 = {};
                let rowHD_LIENHE = {};
                if (ds.length > 0) {
                    this.map_lh_tc_ban = [];
                    for (let r=0; r<ds.length; r++) {
                        rowHD_LIENHE = {};
                        let hdlh_id = await this.taoKey(this.tinh.tinh_id, "HDLH_ID");
                        rowHD_LIENHE = this.copyRowHD_LIENHE(ds[r]);
                        rowHD_LIENHE.HDLH_ID = hdlh_id;
                        rowHD_LIENHE.HDKH_ID = this.hdkh_tc_id;
                        rowHD_LIENHE.TTLH_ID = null;
                        this.ds_hdlh.push(rowHD_LIENHE);
                        this.map_lh_tc_ban.push({key:hdlh_id, value: ds[r].hdlh_id});
                        row1 = {
                            MUCDICH_ID: 1,
                            HDLH_ID: hdlh_id
                        };
                        this.ds_hdkh_md.push(row1);
                    }
                }
                return true
            } catch (e) { 
                console.log('taoDuLieu_HD_LIENHE_v2', e) 
                return false
            }
        },
        // API 1. UR2.1.003_025
        // Xử lý hàm trong tao_HDBancheo_v2(hdtb_temp, daucuoi_id)
        layDataHDBanCheoTheoHDTB: async function(hdtb_id, nhanvien_id, daucuoi_id) {
            let result = [];
            await api.layDataHDBanCheoTheoHDTB(this.axios, {
                "vhdtb_id": hdtb_id,
                "vnhanvien_id": nhanvien_id,
                "vdaucuoi_id": daucuoi_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Khảo sát UR2.1.003_025
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDBancheo_v2: async function(hdtb_temp, daucuoi_id) {
            try {
                this.dsHDBANCHEO = [];
                let dts = await this.layDataHDBanCheoTheoHDTB(hdtb_temp, this.tt_nd.nhanvien_id, daucuoi_id);
                let rowHDBANCHEO = {};
                this.dsHDTB = [];
                this.dsHDTBCD = [];
                this.dsHDTBADSL = [];
                this.dsHDTBMGWAN = [];
                this.dsHDTBTSL = [];
                this.dsHDTBIMS = [];
                this.dsDK_DVGT = [];
                this.dsHDTBLYDOHUY = [];
                this.dsDCHDTB = [];
                this.dsDCHDKH = [];
                this.dsDCHDTT = [];
                this.dsTien_HDTB = [];
                this.ds_hdttlh = [];
                this.dsHDTB_LoaiIp = [];
                let _trang_thai = dts[0].trang_thai;
                console.log('tao_HDBancheo_v2 > layDataHDBanCheoTheoHDTB: dts', dts)
                if (!_trang_thai || _trang_thai == 0 || _trang_thai == 5 && (!dts[0].hdtb_thicong || dts[0].hdtb_thicong == 0 || dts[0].hdtb_thicong == -9999)) {
                    rowHDBANCHEO = {};
                    let hdtb_ban_id = hdtb_temp;
                    let hdtb_tc_id = await this.taoKey(this.tinh.tinh_id, "HD_THUEBAO");
                    let det = dts.filter(x => x.hdtb_ban == hdtb_ban_id);
                    if (daucuoi_id != 0) {
                        det = det.filter(x => x.daucuoi_id == daucuoi_id);
                    }
                    rowHDBANCHEO = this.copyRowHDBANCHEO(det[0]);
                    rowHDBANCHEO.LOAIHD_BANCHEO = 2;
                    if (rowHDBANCHEO.THUEBAO_ID_THICONG) {
                        this.thuebao_id_tc = rowHDBANCHEO.THUEBAO_ID_THICONG;
                    }
                    if (rowHDBANCHEO.KHACHHANG_ID_TC) {
                        this.khachhang_id_tc = rowHDBANCHEO.KHACHHANG_ID_TC;
                    }
                    rowHDBANCHEO.TRANG_THAI = 0;
                    rowHDBANCHEO.HDTB_THICONG = hdtb_tc_id;
                    rowHDBANCHEO.MANV_DIEUHUONG = this.tt_nd.nhanvien_id;
                    rowHDBANCHEO.NGUOI_CN = this.tt_nd.username;
                    rowHDBANCHEO.MAY_CN = this.tt_nd.may_cn;
                    rowHDBANCHEO.NGAY_CN = this.homnay;
                    rowHDBANCHEO.HDTB_BAN = hdtb_ban_id;
                    this.dsHDBANCHEO.push(rowHDBANCHEO);
                    var result = true
                    result = await this.tao_HDTB(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('tao_HDTB', result)
                        return false 
                    }
                    result = await this.tao_HDTBCD(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('tao_HDTBCD', result)
                        return false 
                    }
                    result = await this.tao_HDTBIMS(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('tao_HDTBIMS', result)
                        return false 
                    }
                    result = await this.tao_HDTBADSL(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('tao_HDTBADSL', result)
                        return false 
                    }
                    result = await this.tao_HDTBMGWAN(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('tao_HDTBMGWAN', result)
                        return false 
                    }
                    result = await this.tao_HDTBTSL(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('tao_HDTBTSL', result)
                        return false 
                    }
                    result = await this.tao_DANGKY_DVGT(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('tao_DANGKY_DVGT', result)
                        return false 
                    }
                    result = await this.tao_diachi_HDTB(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('tao_diachi_HDTB', result)
                        return false 
                    }
                    result = await this.taoDuLieu_HD_TTLH(hdtb_ban_id, hdtb_tc_id);
                    if (!result) { 
                        console.log('taoDuLieu_HD_TTLH', result)
                        return false 
                    }
                }
                return true
            } catch (e) { 
                console.log('tao_HDBancheo_v2',e) 
                return false
            }
        },
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_DIACHIHD_v2: async function(hd_thuebao_ban) {
            try {
                let rowDIACHI = {};
                let rowDCHDKH = {};
                let rowDCHDTT = {};
                let ds_diachi_ld = await this.layDiaChiHDTBBanCheoByLD(hd_thuebao_ban);
                let ds_diachi_tt = await this.layHDThueBaoDiaChiHDTBBanCheo(this.hdkh_id);
                if (ds_diachi_ld.length > 0) {
                    // Lấy địa chỉ lắp đặt bắn vào địa chỉ khách hàng, thanh toán ( tránh lỗi constrain quan_id, phuong_id, pho_id
                    let ds = await this.layDiaChiHDKHData(this.hdkh_id);
                    if (ds.length > 0) {
                        let diachikh_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                        while (diachikh_id && this.dsDIACHI.find(e => Number(e['DIACHI_ID']) == Number(diachikh_id))) {
                            diachikh_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                        }
                        // diachi
                        rowDIACHI = {};
                        rowDIACHI = this.copyRowDIACHI(ds_diachi_ld[0]);
                        rowDIACHI.DIACHI_ID = diachikh_id;
                        rowDIACHI.DIACHI = ds_diachi_tt[0].diachi;
                        this.dsDIACHI.push(rowDIACHI);
                        // diachi_hdkh
                        rowDCHDKH.HDKH_ID = this.hdkh_tc_id;
                        rowDCHDKH.DIACHI_ID = diachikh_id;
                        this.dsDCHDKH.push(rowDCHDKH);
                    }
                    // them dia chi thanh toan
                    ds = await this.layDiaChiHDTTHDThueBao(this.hdkh_id);
                    if (ds.length > 0) {
                        let diachitt_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                        while (diachitt_id && this.dsDIACHI.find(e => Number(e['DIACHI_ID']) == Number(diachitt_id))) {
                            diachitt_id = await this.taoKey(this.tinh.tinh_id, "DIACHI");
                        }
                        // diachi
                        rowDIACHI = {}
                        // if(this.vdichvuvt_id = this.dichvuvienthong.tsl && this.loaihd_id == this.loaihopdong.di_chuyen) {
                        //     let drs = this.dsDIACHI.filter(x => x.TINH_ID == this.tinh.tinh_id);
                        //     if (drs.length > 0) {
                        //         rowDIACHI = drs[0];
                        //     } else {
                        //         rowDIACHI = this.copyRowDIACHI(ds_diachi_ld[0]);
                        //     }
                        // } else {
                            rowDIACHI = this.copyRowDIACHI(ds_diachi_ld[0]);
                        // }
                        rowDIACHI.DIACHI_ID = diachitt_id;
                        rowDIACHI.DIACHI = ds_diachi_tt[0].diachi;
                        this.dsDIACHI.push(rowDIACHI);
                        // địa chỉ hdtt
                        rowDCHDTT.DIACHI_ID = diachitt_id;
                        rowDCHDTT.HDTT_ID = this.hdtt_tc_id;
                        this.dsDCHDTT.push(rowDCHDTT);
                    }
                }
                console.log('tao_DIACHIHD_v2 > ds_diachi_ld, dsDIACHI', ds_diachi_ld, this.dsDIACHI, this.dsDIACHI.length)
                return true
            } catch (e) { 
                console.log('tao_DIACHIHD_v2',e) 
                return false
            }
        },
        // Xử lý hàm trong capNhatNDChuyenBC(dulieu)
        tao_HDTB_HEN_v2: async function(donvi, nhanvien_id) {
            try {
                this.ds_hdtb_hen = [];
                let dtrow = {};
                let hdtb_ban = this.dsHDBANCHEO[0].HDTB_BAN;
                let hdtb_tc = this.dsHDBANCHEO[0].HDTB_THICONG;
                let dts = await this.getHDTBHen(hdtb_ban);
                for (let j=0; j<dts.length; j++) {
                    dtrow = {};
                    dtrow = this.copyRowHDTB_HEN(dts[j]);
                    dtrow.HDTB_ID = hdtb_tc;
                    dtrow.DONVI_ID = donvi;
                    dtrow.NHANVIEN_ID = nhanvien_id;
                    dtrow.RID = await this.taoKey(this.tinh.tinh_id, "HDTB_HEN");
                    this.ds_hdtb_hen.push(dtrow);
                }
                return true
            } catch (e) { 
                console.log('tao_HDTB_HEN_v2',e) 
                return false
            }
        },        
        timKiem: async function() {
            // await this.hienThiDanhSachHDKH();
            await this.chonKieuHD()
        },
        openFrmFileHS() {
            this.$refs.frmXemHS.showModal();
        },
        khoiTao: async function() {
            // Bưng code của frmTraCuuHopDongBC(int _kieu_tracuu) bê vào đây
            if (this.$route.query.kieuhd_id) {
                this.thongtintimkiem.kieuhd_id = this.$route.query.kieuhd_id
            } else if (this.dulieu && this.dulieu.kieuhd_id) {
                this.thongtintimkiem.kieuhd_id = this.dulieu.kieuhd_id
            }
            if (this.$route.query.kieu_tracuu == 0 || (this.$route.query.kieu_tracuu && this.$route.query.kieu_tracuu.toString().length > 0)) {
                this.kieu_tracuu = Number(this.$route.query.kieu_tracuu.toString());
                this.hienthi_nut.capnhan = false;
                if(this.kieu_tracuu == 0) {
                    //cboKieuHD.Enabled = false;
                    this.hienthi_nut.capnhan = true;
                    // Bưng code dòng 219 trong hàm private void frmTraCuuHopDongBC_Load(object sender, EventArgs e) bê vào đây
                    this.thongtintimkiem.kieuhd_id = 2;
                } else {
                    // Bưng code dòng 222 trong hàm private void frmTraCuuHopDongBC_Load(object sender, EventArgs e) bê vào đây
                    this.thongtintimkiem.kieuhd_id = 0;
                }
            } else if (this.dulieu && (
                this.dulieu.kieu_tracuu == 0 || (this.dulieu.kieu_tracuu && this.dulieu.kieu_tracuu.toString().length > 0)
                )) {
                this.kieu_tracuu = Number(this.dulieu.kieu_tracuu.toString());
                this.hienthi_nut.capnhan = false;
                if(this.kieu_tracuu == 0) {
                    //cboKieuHD.Enabled = false;
                    this.hienthi_nut.capnhan = true;
                    // Bưng code dòng 219 trong hàm private void frmTraCuuHopDongBC_Load(object sender, EventArgs e) bê vào đây
                    this.thongtintimkiem.kieuhd_id = 2;
                } else {
                    // Bưng code dòng 222 trong hàm private void frmTraCuuHopDongBC_Load(object sender, EventArgs e) bê vào đây
                    this.thongtintimkiem.kieuhd_id = 0;
                }
            }

            // Bưng code của frmTraCuuHopDongBC(string ma_tb) bê vào đây
            if (this.$route.query.ma_tb && this.$route.query.ma_tb.toString().length > 0) {
                this.thongtintimkiem.thongtin_tracuu = this.$route.query.ma_tb;
            } else if (this.dulieu && this.dulieu.ma_tb && this.dulieu.ma_tb.toString().length > 0) {
                this.thongtintimkiem.thongtin_tracuu = this.dulieu.ma_tb;
            }

            // Bưng code của frmTraCuuHopDongBC(int _loaihd_id, int _tthd_id, int _donvi_id, int _nhanvien_id, string _ngay_yc, int _kieu, int _donvi_dl_id, int _dichvuvt_id) bê vào đây
            if (this.$route.query.loaihd_id && this.$route.query.tthd_id && this.$route.query.donvi_id && this.$route.query.nhanvien_id && this.$route.query.ngay_yc && this.$route.query.kieu && this.$route.query.donvi_dl_id && this.$route.query.dichvuvt_id) {
                this.loaihd_id = this.$route.query.loaihd_id;
                this.tthd_id = this.$route.query.tthd_id;
                this.donvi_id = this.$route.query.donvi_id;
                this.nhanvien_id = this.$route.query.nhanvien_id;
                this.ngay_yc = this.$route.query.ngay_yc;
                this.kieu = this.$route.query.kieu;
                this.donvi_dl_id = this.$route.query.donvi_dl_id;
                this.dichvuvt_id = this.$route.query.dichvuvt_id;
            } else if (this.dulieu) {
                if (this.dulieu['tthd_id']) this.loaihd_id = this.dulieu['loaihd_id'];
                if (this.dulieu['tthd_id']) this.tthd_id = this.dulieu['tthd_id'];
                if (this.dulieu['donvi_id']) this.donvi_id = this.dulieu['donvi_id'];
                if (this.dulieu['nhanvien_id']) this.nhanvien_id = this.dulieu['nhanvien_id'];
                if (this.dulieu['ngay_yc']) this.ngay_yc = this.dulieu['ngay_yc'];
                if ( this.dulieu['kieu']) this.kieu = this.dulieu['kieu'];
                if (this.dulieu['donvi_dl_id']) this.donvi_dl_id = this.dulieu['donvi_dl_id'];
                if (this.dulieu['dichvuvt_id']) this.dichvuvt_id = this.dulieu['dichvuvt_id'];
            }

            // Bưng code của frmTraCuuHopDongBC_Load(object sender, EventArgs e) bê vào đây
            if (this.$route.query.tag && this.$route.query.tag.toString().length > 0) {
                this.kt_inhd = Number(this.$route.query.tag.toString());
            } else if (this.dulieu && this.dulieu.tag && this.dulieu.tag.toString().length > 0) {
                this.kt_inhd = Number(this.dulieu.tag.toString());
            } else {
                this.kt_inhd = 0;
            }
            this.hienthi_nut.inpl = (this.kt_inhd == 1) ? true : false;
            this.hienthi_nut.inbb = (this.kt_inhd == 1) ? true : false;
            await this.layDs();
            if (this.kieu == 1) {
                await this.timKiem();
                return
            }
            if (this.thongtintimkiem.thongtin_tracuu && this.thongtintimkiem.thongtin_tracuu.length > 0) {
                await this.timKiem();
                // this.hienthi_nut.chuyennet = false;
                // this.hienthi_nut.chigiaonet = false;
                return
            }
        },
        layDs: async function() {
            await api.getDsThamSoMD(this.axios).then(res => {
                let dulieu = res.data.data ? JSON.parse(res.data.data) : {};
                if (dulieu && dulieu.ERROR_CODE == 1) {
                    let ds_tsmd = dulieu.RESULT.DS_TSMD;
                    this.thongtintimkiem.ds_trangthaihd = dulieu.RESULT.CBOTRANGTHAI;
                    if (ds_tsmd.find(x => x.MA_TS == "THEM_TT_HDKHDN")) this.ts_kt_tthdkhdn = Number(ds_tsmd.find(x => x.MA_TS == "THEM_TT_HDKHDN").TEN_TS);
                    if (ds_tsmd.find(x => x.MA_TS == "GIAO_VIP_NET")) this.ts_giao_vip_net = Number(ds_tsmd.find(x => x.MA_TS == "GIAO_VIP_NET").TEN_TS);
                    if (ds_tsmd.find(x => x.MA_TS == "UPDATE_QT_TSL_BC")) this.ts_update_qt_tsl = Number(ds_tsmd.find(x => x.MA_TS == "UPDATE_QT_TSL_BC").TEN_TS);
                    this.thongtintimkiem.tthd_id = 1;
                    this.thongtintimkiem.ds_loaihd = dulieu.RESULT.CBOLOAIHD;
                    this.thongtintimkiem.loaihd_id = 1;
                    this.thongtintimkiem.ds_dichvu = dulieu.RESULT.CBODICHVUVT;
                    this.thongtintimkiem.dichvuvt_id = 1;
                    this.thongtintimkiem.ds_tracuutheo = dulieu.RESULT.CBODKTRACUU;
                    this.thongtintimkiem.tracuu_theo = "tb.ma_tb = ':values'";
                    this.thongtintimkiem.kieuhd_id = 0;
                    this.thongtintimkiem.ds_kieuhd = [{ID: 0, KIEU_HD: 'Chưa chuyển'}, {ID: 1, KIEU_HD: 'Đã Chuyển'}, {ID: 2, KIEU_HD: 'Đã Thi công'}, {ID: 3, KIEU_HD: 'HĐ trả từ thi công'}];
                }
            });
        },
        thayDoiCheck(num) {
            if (num == 1) this.thongtintimkiem.dis_loaihd = !this.thongtintimkiem.chk_loaihd;
            else if (num == 2) this.thongtintimkiem.dis_trangthai = !this.thongtintimkiem.chk_trangthai;
            else if (num == 3) this.thongtintimkiem.dis_ngayyc = !this.thongtintimkiem.chk_ngayyc;
            else if (num == 4) this.thongtintimkiem.dis_dvvt = !this.thongtintimkiem.chk_dvvt;
        },
        chonKieuHD: async function() {
            // if (!this.is_nv_net && this.thongtintimkiem.kieuhd_id == 0) {
            //     this.hienthi_nut.capnhan = true;
            //     this.hienthi_nut.xoatb = false;
            //     this.hienthi_nut.xemhs = false;
            //     if (this.ts_kt_tthdkhdn == 1) this.hienthi_nut.chuyennet = true;
            // } else {
            //     this.hienthi_nut.chuyennet = false;
            //     this.hienthi_nut.xemhs = true;
            //     this.hienthi_nut.chigiaonet = false;
            //     this.hienthi_nut.chigiaonet_tinh = false;
            //     if (this.thongtintimkiem.kieuhd_id == 1) {
            //         this.hienthi_nut.capnhan = false;
            //         this.hienthi_nut.xoatb = true;
            //     } else {
            //         this.hienthi_nut.capnhan = false;
            //         this.hienthi_nut.xoatb = false;
            //     }
            // }
            if (this.thongtintimkiem.kieuhd_id == 0)
            {
                this.hienthi_nut.capnhan = true;
                this.hienthi_nut.xoatb = false;
                this.hienthi_nut.xemhs = false;
                if (!this.is_nv_net && this.ts_kt_tthdkhdn == 1)
                    this.hienthi_nut.chuyennet = true;
                else
                    this.hienthi_nut.chuyennet = false;
            }
            else
            {
                this.hienthi_nut.chuyennet = false;
                this.hienthi_nut.xemhs = true;
                this.hienthi_nut.chigiaonet = false;
                this.hienthi_nut.chigiaonet_tinh = false;
                if (this.thongtintimkiem.kieuhd_id == 1)
                {
                    this.hienthi_nut.capnhan = false;
                    this.hienthi_nut.xoatb = true;
                }
                else
                {
                    this.hienthi_nut.capnhan = false;
                    this.hienthi_nut.xoatb = false;
                }
            }
            console.log('hienthi_nut', this.hienthi_nut)
            await this.hienThiDanhSachHDKH();
        },
        timKiemTheoDieuKienBanCheo: async function(kieu, site_id) {
            let result = [];
            await api.searchTheoDKBanCheo(this.axios, {
                "vphanvung_id": this.tt_nd.phanvung_id,
                "vkieu": kieu,
                "vtextboxtc": this.thongtintimkiem.thongtin_tracuu,
                "vsite_id": site_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Hàm lấy danh sách khách hàng
        hienThiDanhSachHDKH: async function() {
            this.ds_kh.data = [];
            let dieukien = "";
            if (this.thongtintimkiem.thongtin_tracuu == "" || this.thongtintimkiem.thongtin_tracuu == "Nhập thông tin cần tìm kiếm") {
                dieukien = "0";
            } else {
                dieukien = this.thongtintimkiem.tracuu_theo.replace(":values", this.thongtintimkiem.thongtin_tracuu);
            }
            // Bắt đầu xử lý khi check/uncheck checkbox trong thông tin tìm kiếm
            if (this.thongtintimkiem.chk_loaihd) this.loaihd_id = this.thongtintimkiem.loaihd_id;
            else this.loaihd_id = 0;
            if (this.thongtintimkiem.chk_dvvt) this.dichvuvt_id = this.thongtintimkiem.dichvuvt_id;
            else this.dichvuvt_id = 0;
            if (this.thongtintimkiem.chk_trangthai) this.tthd_id = this.thongtintimkiem.tthd_id;
            else this.tthd_id = 0;
            if (this.thongtintimkiem.chk_ngayyc) {
                this.ngay_yc = this.thongtintimkiem.ngay_yc;
                this.denngay = this.thongtintimkiem.denngay;
            } else {
                this.ngay_yc = '0';
                this.denngay = '0';
            }
            // Kết thúc xử lý khi check/uncheck checkbox trong thông tin tìm kiếm
            if (this.thongtintimkiem.loaihd_id == this.loaihopdong.tiepnhan_datmoi || this.kt_inhd == 1) {
                this.donvi_dl_id = Number(this.$root.token.getDonViDuLieuID());
            }
            this.loading(true);
            let p_ds_para = {
                "DK_TRACUU": this.thongtintimkiem.ds_tracuutheo && this.thongtintimkiem.ds_tracuutheo.find(x => x.DIEUKIEN === this.thongtintimkiem.tracuu_theo) ? this.thongtintimkiem.ds_tracuutheo.find(x => x.DIEUKIEN === this.thongtintimkiem.tracuu_theo).KIEU_TK : "",
                "DIEUKIEN": dieukien,
                "TXTEXTBOXTC": this.thongtintimkiem.thongtin_tracuu,
                "LOAIHD_ID": this.loaihd_id,
                "TTHD_ID": this.tthd_id,
                "DONVI_ID": this.tt_nd.donvi_id,
                "NHANVIEN_ID": this.tt_nd.nhanvien_id,
                "DONVI_DL_ID": this.tt_nd.donvi_dl_id,
                "VTUNGAY_YC": this.ngay_yc,
                "VDENNGAY_YC": this.denngay,
                "KIEUHD_ID": this.thongtintimkiem.kieuhd_id
            };
            await api.getDsHDKH(this.axios, {
                "p_ds_para": JSON.stringify(p_ds_para)
            }).then(res => {
                if (res.data.error_code === "BSS-00000000" && res.data.data) {
                    let data = JSON.parse(res.data.data);
                    if (data.ERROR_CODE == 1 || data.ERROR_CODE == '1') {
                        if (data.RESULT.DS_KH && data.RESULT.DS_KH.length > 0) {
                            this.ds_kh.data = data.RESULT.DS_KH;
                            for (let i=0; i<this.ds_kh.data.length; i++) {
                                this.ds_kh.data[i].NGAY_YC = this.ds_kh.data[i].NGAY_YC ? moment(this.ds_kh.data[i].NGAY_YC).format('DD/MM/YYYY') : null;
                            }
                            this.ds_tt = data.RESULT.LAY_DS_HOPDONG_TT_THEO_HDKH_ID;
                            let tmp_dstb = data.RESULT.BANCHEO_LAY_DS_HDTB_THEO_HDKH_ID_V3;
                            this.ds_tb.data = [...new Map(tmp_dstb.map(item => [item['hdtb_id'], item])).values()];
                        }
                    } else {
                        this.$toast.error('Lỗi fn_tt_hienthidanhsachdkh_frmtracuuhopdongbc ' + data.MESSAGE)
                    }
                }
            });
            this.loading(false);
            if (this.ds_kh.data.length > 0) {
                this.hdkh_id = this.ds_kh.data[0].HDKH_ID;
                this.sloaihd_id = this.ds_kh.data[0].LOAIHD_ID;
                if (this.sloaihd_id == this.loaihopdong.chuyen_quyen || this.sloaihd_id == this.loaihopdong.dat_moi || this.sloaihd_id == this.loaihopdong.ky_lai_hd_goc) {
                    this.hienthi_nut.inhd = true;
                } else {
                    this.hienthi_nut.inhd = false;
                }
                if (this.sloaihd_id == this.loaihopdong.chuyen_quyen || this.sloaihd_id == this.loaihopdong.chamdut_sd || this.sloaihd_id == this.loaihopdong.thay_doi_dv || this.sloaihd_id == this.loaihopdong.thay_doi_tocdo_adsl || this.sloaihd_id == this.loaihopdong.di_chuyen || this.sloaihd_id == this.loaihopdong.doiso_tb) {
                    this.hienthi_nut.inpl = true;
                } else {
                    this.hienthi_nut.inpl = false;
                }
                if (this.sloaihd_id == this.loaihopdong.chuyen_quyen) {
                    this.hienthi_nut.chuyennet = false
                }
                this.hienthi_nut.inbb = false;
                this.thongtintimkiem.ma_gd = this.ds_kh.data[0].MA_GD;
            } else {
                this.ds_tt = [];
                this.ds_tb.data = [];
            }
        },
        bancheoLayDsHdtbTheoHdkhIdV3: async function(hdkh_id) {
            let result = [];
            await api.bancheoLayDsHdtbTheoHdkhIdV3(this.axios, {
                "in_hdkh_id": hdkh_id,
                "in_nhanvien_id": this.tt_nd.nhanvien_id,
                "in_tthd": this.thongtintimkiem.kieuhd_id//this.tthd_id
            }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        LAY_DS_HOPDONG_TT_THEO_HDKH_ID: async function(hdkh_id) {
            let result = [];
            await api.get_lay_ds_hopdong_thanhtoan_theo_hdkh(this.axios, { params: { hdKhId: hdkh_id } }).then(res => {
                result = res.data.data ? this.UpperCasePropertyList(res.data.data) : [];
            });
            return result;
        },
        // Xử lý sự kiện khi click row của this.ds_kh.data
        rowSelected_HDKH: async function(args){
            try {
                this.loading(true)
                if (args) {
                    //console.log("rowSelected_HDKH=" + JSON.stringify(args));
                    this.obj_hdkh = args.data;
                    this.hdkh_id = args.data.HDKH_ID;
                    if (args.data.KHACHHANG_ID && args.data.KHACHHANG_ID.toString().length > 0) {
                        this.khachhang_id = args.data.KHACHHANG_ID;
                    } else {
                        this.khachhang_id = 0;
                    }
                    this.ma_kh = args.data.MA_KH;
                    this.ten_kh = args.data.TEN_KH;
                    this.diachi_kh = args.data.DIACHI_KH;
                    this.thongtintimkiem.ma_gd = args.data.MA_GD;
                    this.sloaihd_id = args.data.LOAIHD_ID;
                    let tmp_dstt = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(args.data.HDKH_ID)
                    let tmp_dstb = await this.bancheoLayDsHdtbTheoHdkhIdV3(args.data.HDKH_ID)
                    this.ds_tt = tmp_dstt
                    this.ds_tb.data = tmp_dstb
                    console.log('rowSelected_HDKH tmp_dstt, tmp_dstb', tmp_dstt, tmp_dstb)
                    // if (!tmp_dstt || tmp_dstt.length == 0) {
                    //     this.$toast.error('Không có dữ liệu LAY_DS_HOPDONG_TT_THEO_HDKH_ID ' + args.data.HDKH_ID)
                    // }

                    if (!tmp_dstb || tmp_dstb.length == 0) {
                        this.$toast.error('Không có dữ liệu bancheoLayDsHdtbTheoHdkhIdV3 ' + args.data.HDKH_ID)
                    }
                }
            } catch (e) {} finally { this.loading(false) }
           
        },
        // Xử lý sự kiện khi click row của this.ds_tb.data
        rowSelected_HDTB: async function(args) {
            if (args) {
                this.rowSelect = args.data
                this.hdtb_id = args.data.hdtb_id;
                this.vdichvuvt_id = args.data.dichvuvt_id;
                this.vtthd_id = args.data.tthd_id;
                this.tinhtc = args.data.tinh_thicong;
                this.loaikenh_id = args.data.loaikenh_id;
                this.hdtb_thicong = args.data.hdtb_thicong;
                this.daucuoi_id = args.data.daucuoi_id;
                if (this.vdichvuvt_id == this.dichvuvienthong.megawan || this.vdichvuvt_id == this.dichvuvienthong.metronet || this.vdichvuvt_id == 26 || this.vdichvuvt_id == this.dichvuvienthong.tsl || this.vdichvuvt_id == 25) {
                    let mahd_ttkd = '';
                    if (this.vdichvuvt_id == this.dichvuvienthong.megawan || this.vdichvuvt_id == this.dichvuvienthong.metronet || this.vdichvuvt_id == 26) {
                        // mahd_ttkd = await this.mapId('ma_pl', 'css.hdtb_mgwan', 'where hdtb_id = ' + this.hdtb_id + ' and phanvung_id=' + this.tt_nd.phanvung_id);
                        mahd_ttkd = await this.fn_tt_hdtb_mgwan(1, this.hdtb_id)
                    } else {
                        // mahd_ttkd = await this.mapId('ma_pl', 'css.hdtb_tsl', 'where hdtb_id = ' + this.hdtb_id + ' and phanvung_id=' + this.tt_nd.phanvung_id);
                        mahd_ttkd = await this.fn_tt_hdtb_tsl(3, this.hdtb_id)
                    }
                    if (mahd_ttkd != '-1' || mahd_ttkd != '') {
                        this.thongtintimkiem.ma_hd_ttkd = mahd_ttkd;
                    } else {
                        this.thongtintimkiem.ma_hd_ttkd = '';
                    }
                } else {
                    this.thongtintimkiem.ma_hd_ttkd = '';
                }
                if ((this.vdichvuvt_id == this.dichvuvienthong.megawan || this.vdichvuvt_id == this.dichvuvienthong.metronet || this.vdichvuvt_id == 26) && this.ts_kt_tthdkhdn == 1 && this.thongtintimkiem.kieuhd_id == 0) {
                    if (this.sloaihd_id == this.loaihopdong.dat_moi || !args.data.thuebao_id_thicong || (args.data.thuebao_id_thicong == -1 || args.data.thuebao_id_thicong == '-1')) {
                        this.hienthi_nut.chigiaonet = true;
                    } else if (this.loaikenh_id == this.loaikenh.noihat && (args.data.thuebao_id_thicong == -1 || args.data.thuebao_id_thicong == '-1')) {
                        this.hienthi_nut.chigiaonet = true;
                    } else {
                        this.hienthi_nut.chigiaonet = false;
                    }
                } else if ((this.vdichvuvt_id == this.dichvuvienthong.tsl || this.vdichvuvt_id == 25) && this.ts_kt_tthdkhdn == 1) {
                    this.hienthi_nut.chigiaonet = true;
                } else {
                    this.hienthi_nut.chigiaonet = false;
                }
                if ((this.vdichvuvt_id == this.dichvuvienthong.megawan || this.vdichvuvt_id == this.dichvuvienthong.metronet || this.vdichvuvt_id == 26) && this.ts_kt_tthdkhdn == 0 && this.thongtintimkiem.kieuhd_id == 0) {
                    this.hienthi_nut.chigiaonet_tinh = true;
                } else if ((this.vdichvuvt_id == this.dichvuvienthong.tsl || this.vdichvuvt_id == 25) && this.ts_kt_tthdkhdn == 0 && this.thongtintimkiem.kieuhd_id == 0) {
                    this.hienthi_nut.chigiaonet_tinh = true;
                } else {
                    this.hienthi_nut.chigiaonet_tinh = false;
                }
            }
        },
        banCheoKiemTraXoaHDTinhBan: async function(hdtb_id, tinhthicong_id) {
            let result = "";
            await api.banCheoKiemTraXoaHDTinhBan(this.axios, {
                "vhdtb_id": hdtb_id,
                "tinhthicong_id": tinhthicong_id
            }).then(res => {
                result = res.data.data ? res.data.data : "";
            });
            return result;
        },
        banCheoLayPhieuVipNetMedia: async function(hdtb_id) {
            let result = [];
            await api.banCheoLayPhieuVipNetMedia(this.axios, { vhdtb_id: hdtb_id }).then(res => {
                result = res.data.data ? res.data.data : [];
            });
            return result;
        },
        // Xử lý sự kiện khi nhấn nút Xóa TB
        xoaTB() {
            if (this.hdtb_thicong == -1) {
                this.$toast.error("Thuê bao chỉ giao cho NET thi công, liên hệ NET để được trả phiếu.");
                return;
            }
            var result = ''
            this.$bvModal.msgBoxConfirm("Sẽ xóa 1 thuê bao trong HĐ này?", {
                title: 'Thông báo',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if(!v) {
                    return;
                }
                let kq = await this.banCheoKiemTraXoaHDTinhBan(this.hdtb_thicong, this.tinhtc);
                if (kq != "ok" && kq != "thoai") {
                    this.$toast.error(kq);
                    return;
                } else if (kq == "ok") {
                    await api.banCheoThoaiHDTinhBan(this.axios, {
                        "vhdtb_id": this.hdtb_id,
                        "vkieu": 1,
                        "vbientam": ""
                    }).then(res => {
                        console.log("bancheo_thoai_hd_tinhban", res)
                        if (res && res.data && res.data.error_code != "BSS-00000000") {
                            result = res.data.message_detail ? res.data.message_detail : res.data.message
                        }                        
                        if (result && result.length > 0) {
                            this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                            return
                        }
                    });
                    // await api.xoaHDTB(this.axios, { hdtbId: this.hdtb_thicong }).then(res => {
                    //     console.log("xoa_hopdong_thuebao")
                    // });
                    await api.xoaHDTB(this.axios, {
                        "vhdtb_id": this.hdtb_thicong,//this.hdtb_id,
                        "tinhthicong_id": this.tinhtc
                    }).then(res => {
                        console.log("xoa_hdtb", res)
                        if (res && res.data && res.data.error_code != "BSS-00000000") {
                            result = res.data.message_detail ? res.data.message_detail : res.data.message
                        }  
                        if (result && result.length > 0) {
                            this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                            return
                        }
                    });
                    await api.updateHdBanCheo1(this.axios, {
                        "hdtb_ban": this.hdtb_id
                    }).then(res => {
                        console.log("update_hd_bancheo", res)
                        if (res && res.data && res.data.error_code != "BSS-00000000") {
                            result = res.data.message_detail ? res.data.message_detail : res.data.message
                        }  
                        if (result && result.length > 0) {
                            this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                            return
                        }
                    });
                    await api.updateTrangThaiXoaHDBCDieuHuong(this.axios, {
                        "vhdtb_ban": this.hdtb_id,
                        "tinh_ban" : this.tt_nd.phanvung_id,
                        "tinhthicong_id": this.tinhtc
                    }).then(res => {
                        console.log("delete-hd-bancheo", res)
                        if (res && res.data && res.data.error_code != "BSS-00000000") {
                            result = res.data.message_detail ? res.data.message_detail : res.data.message
                        }  
                        if (result && result.length > 0) {
                            this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                            return
                        }
                    });
                } else if (kq == "thoai") {
                    await api.banCheoThoaiTraHD(this.axios, {
                        "vtinh_id": this.tinhtc,
                        "vhdtb_id" : this.hdtb_thicong,
                        "vnoidung": "Bên bán thoái"
                    }).then(res => {
                        console.log("bancheo_thoaitra_hd", res)
                        if (res && res.data && res.data.error_code != "BSS-00000000") {
                            result = res.data.message_detail ? res.data.message_detail : res.data.message
                        }  
                        if (result && result.length > 0) {
                            this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                            return
                        }
                    });
                    await api.thoaiTraHD(this.axios, {
                        "vhdtb_id": this.hdtb_thicong,
                        "tinhthicong_id" : this.tinhtc,
                        "vnoidung": "Bên bán thoái"
                    }).then(res => {
                        console.log("thoaitra-hd", res)
                        if (res && res.data && res.data.error_code != "BSS-00000000") {
                            result = res.data.message_detail ? res.data.message_detail : res.data.message
                        }  
                        if (result && result.length > 0) {
                            this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                            return
                        }
                    });
                }
                let dt = await this.banCheoLayPhieuVipNetMedia(this.hdtb_id);
                if (dt.length > 0) {
                    for (let i=0; i < dt.length; i++) {
                        await api.banCheoThoaiUpdateVipNetMedia(this.axios, {
                            "tinhthicong_id": 97,
                            "vphieu_tinh_id" : dt[i].phieu_id,
                            "vtinh_id": this.tt_nd.phanvung_id
                        }).then(res => {
                            console.log("bancheo_thoai_update_vip_net_media", res)
                            if (res && res.data && res.data.error_code != "BSS-00000000") {
                                result = res.data.message_detail ? res.data.message_detail : res.data.message
                            }  
                            if (result && result.length > 0) {
                            this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                            return
                        }
                        });
                    }
                }                
                this.$toast.success("Xóa hợp đồng thành công !");
                await this.hienThiDanhSachHDKH();
            });
        },
        async tsbtnChiGiaoNet_Click() {
            try {
                this.loading(true)
                let chi_giao_net = true
                let daucuoi_id = this.rowSelect.daucuoi_id ? this.rowSelect.daucuoi_id.toString() : '0';
                await api.bcChiNetThiCongV2(this.axios, {
                    vhdtb_id: this.hdtb_id,
                    vdaucuoi_id: Number(daucuoi_id) 
                }).then(async res => {
                    var result = ''
                    if (res && res.data && res.data.error_code != "BSS-00000000") {
                        result = res.data.message_detail ? res.data.message_detail : res.data.message
                        chi_giao_net = false
                    }  
                    if (result && result.length > 0) {
                        this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                        chi_giao_net= false
                    } else {
                        chi_giao_net = true                       
                    }
                }).catch((e) => {
                    this.$toast.error('Có lỗi bancheo_chi_net_thicong_v2: ' + JSON.stringify(e))
                    chi_giao_net = false
                })
                if (chi_giao_net) {
                    if (Number(this.tt_nd.phanvung_id) == 98) {
                        let input = {
                            vphanvung_id: Number(this.tt_nd.phanvung_id),
                            vhdtb_id: this.hdtb_id,
                            vdaucuoi_id: Number(daucuoi_id)
                        }
                        let ds_khdn = await this.lay_tt_giao_vip_net_khdn_bc_vnpti(input)     
                        if (ds_khdn && ds_khdn.length > 0) {
                            let input1 = {
                                tinhthicong_id: 97,
                                vjson_khdn: JSON.stringify(this.UpperCasePropertyList(ds_khdn))
                            }
                            let insert = await this.tinh_insert_vip_net(input1)
                            if (insert) {
                                this.$toast.success('Thực hiện Chỉ giao NET thành công!')
                                await this.hienThiDanhSachHDKH();
                            }
                        }                       
                    } else {
                        this.$toast.success('Thực hiện Chỉ giao NET thành công!')
                        await this.hienThiDanhSachHDKH();
                    }    
                }
            } catch (e) {
                this.$toast.error("Lỗi Chỉ giao NET: " + JSON.stringify(e));
            } finally {
                this.loading(false)
            }
        },
        async tsbtnChiGiaoNet_tinh_Click()
        {
            try
            {
                this.loading(true)
                //region đoạn này insert vào NET VIP của tỉnh và update hd_bancheo
                let str_hdtb = "";
                let cop = [];
                const cl_hdtb = "HDTB_BAN";
                cop.push({ HDTB_BAN: this.rowSelect.hdtb_ban.toString() });
                if (cop.length > 0)
                {
                    let json_hdtb_id = JSON.stringify(cop);
                    let daucuoi_id = this.rowSelect.daucuoi_id.toString();
                    for (let k = 0; k < cop.length; k++)
                    {
                        str_hdtb += cop[k]["HDTB_BAN"].toString() + ",";
                    }
                    await api.sp_chigiaonet_tinh(this.axios, {
                        "json_hdtb_id": json_hdtb_id,
                        "daucuoi_id" : Number(daucuoi_id),
                        "str_hdtb": str_hdtb
                    }).then(async res => {
                        var result = ''
                        if (res && res.data && res.data.error_code != "BSS-00000000") {
                            result = res.data.message_detail ? res.data.message_detail : res.data.message
                        }  
                        if (result && result.length > 0) {
                            this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                        } else {
                            this.$toast.success('Thực hiện tỉnh chỉ giao NET thành công!')
                            await this.hienThiDanhSachHDKH();
                            this.loading(false)
                        }
                    });
                }
            } catch (e) {
                this.$toast.error("Lỗi giao Net tỉnh: "+ JSON.stringify(e));
            } finally {
                this.loading(false)
            }
        },
        // Xử lý sự kiện khi nhấn nút Chuyển NET
        chuyenNet: async function() {
            // DB không khảo sát => ko có API => ko thể thực hiện chức năng này (https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=620043524&range=E8260)
            try {
                this.loading(true)
                let nguoi_chuyen = await this.kiemTraNutChuyenTinhKHDN();
                if (nguoi_chuyen == "AM_KHDN_TEST") {
                    // string daucuoi_id = gridviewThueBao.GetFocusedDataRow()["DAUCUOI_ID"].ToString();
                    // var rows = (gridThueBao.DataSource as DataTable).Select("(loaikenh_id = " + LOAIKENH.NOIHAT + " and dichvuvt_id in (7,8,9,25,26) and loaitb_id <> 134 and loaitb_id <> 385) or dichvuvt_id not in (7,8,9,25,26)");

                    let daucuoi_id = this.rowSelect.daucuoi_id ? this.rowSelect.daucuoi_id.toString() : '0';
                    let rows = this.ds_tb.data.filter(x => 
                        ((x.loaikenh_id == this.loaikenh.noihat && (x.dichvuvt_id == 7 || x.dichvuvt_id == 8 || x.dichvuvt_id == 26 || x.dichvuvt_id == 9 || x.dichvuvt_id == 25)) && x.loaitb_id != 134 && x.loaitb_id != 385) || 
                        (x.dichvuvt_id != 7 && x.dichvuvt_id != 8 && x.dichvuvt_id != 26 && x.dichvuvt_id != 9 && x.dichvuvt_id != 25)
                    );
                    console.log('chuyenNet', this.ds_tb.data, rows)
                    if (rows.length > 0) {
                        this.$toast.error("Thuê bao nội tỉnh không ấn nút chuyển NET");
                        return;
                    } else {
                        let bancheo_chuyen_net_lientinh = true
                        await api.sp_bancheo_chuyen_net_lientinh(this.axios, { 
                            vhdtb_id: this.hdtb_id,
                            vdaucuoi_id: Number(daucuoi_id) 
                        }).then(async res => {
                            var result = ''
                            if (res && res.data && res.data.error_code != "BSS-00000000") {
                                result = res.data.message_detail ? res.data.message_detail : res.data.message
                                bancheo_chuyen_net_lientinh = false
                            }  
                            if (result && result.length > 0) {
                                this.$toast.error('Có lỗi sp_bancheo_chuyen_net_lientinh: ' + JSON.stringify(result))
                                bancheo_chuyen_net_lientinh = false
                            } else {         
                                bancheo_chuyen_net_lientinh = true      
                            }
                        }).catch((e) => {
                            this.$toast.error('Có lỗi sp_bancheo_chuyen_net_lientinh: ' + JSON.stringify(e))
                            bancheo_chuyen_net_lientinh = false
                        })
                        if (bancheo_chuyen_net_lientinh) {
                            if (Number(this.tt_nd.phanvung_id) == 98) {
                                let input = {
                                    vphanvung_id: Number(this.tt_nd.phanvung_id),
                                    vhdtb_id: this.hdtb_id,
                                    vdaucuoi_id: Number(daucuoi_id)
                                }
                                let ds_khdn = await this.lay_tt_giao_vip_net_khdn_bc_vnpti(input)     
                                if (ds_khdn && ds_khdn.length > 0) {
                                    let input1 = {
                                        tinhthicong_id: 97,
                                        vjson_khdn: JSON.stringify(this.UpperCasePropertyList(ds_khdn))
                                    }
                                    let insert = await this.tinh_insert_vip_net(input1)
                                    if (insert) {
                                        this.$toast.success('Chuyển NET thành công!')
                                        await this.hienThiDanhSachHDKH();
                                    }
                                }                       
                            } else {
                                this.$toast.success('Chuyển NET thành công!')
                                await this.hienThiDanhSachHDKH();
                            }    
                        } 
                    }
                } else this.$toast.error("Hiện tại vẫn áp dụng theo quy trình cũ. Ấn nút chuyển tỉnh để chuyển hợp đồng.");
            } catch (e) {
                this.$toast.error('Lỗi Chuyển NET: ' + JSON.stringify(e))
            } finally {
                this.loading(false)
            }
        },
        lay_tt_giao_vip_net_khdn_bc_vnpti: async function (input) {
            try {
                let res = await api.lay_tt_giao_vip_net_khdn_bc_vnpti(this.axios, input)
                if (res && res.data && res.data.error_code == "BSS-00000000") {
                    return res.data.data
                } else {
                    this.$toast.error('Lỗi lay_tt_giao_vip_net_khdn_bc_vnpti: ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                    return []
                }
            } catch (e) { 
                this.$toast.error('Lỗi lay_tt_giao_vip_net_khdn_bc_vnpti: ' + JSON.stringify(e))
                return [] 
            }
        },
        tinh_insert_vip_net: async function (input) {
            try {
                let res = await api.tinh_insert_vip_net(this.axios, input)
                if (res && res.data && res.data.error_code == "BSS-00000000") {
                    return true
                } else {
                    this.$toast.error('Lỗi tinh_insert_vip_net: ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                    return false
                }
            } catch (e) { 
                this.$toast.error('Lỗi tinh_insert_vip_net: ' + JSON.stringify(e))
                return false 
            }
        },
        // Nút giao VNPT IT
        async tsbtnGiaoVNPTI_Click() {
            try {
                this.loading(true)
                if (!this.ds_tb.data || this.ds_tb.data.length == 0) {
                    this.$toast.error('Không có thuê bao!')
                    return
                }
                for (let item of this.ds_tb.data) {
                    if (item['loaitb_id'] == 320 || item['loaitb_id'] == 257 && item['loaitb_id'] == 258 ) {
                        let result = ''
                        await api.sp_lay_danhsach_nhomloaikenh(this.axios, { 
                        "p_hdtb_id": this.hdtb_id
                        }).then(async res => {
                            if(res && res.data && res.data.error_code != "BSS-00000000"){
                                result = res.data.data
                            }
                        })
                        if("2" == result){
                            this.$toast.error("Chỉ dùng cho thuê bao không qua hạ tầng VNPT");
                            return
                        }
                    }
                }
                await api.giaoPhieuVNPTi(this.axios, {
                    "tinhthicong_id": this.tinh.tinh_id,
                    "vhdtb_id": this.hdtb_id,
                    "vdonvi_giao_id": this.tt_nd.donvi_id,
                    "vnhanvien_giao_id": this.tt_nd.nhanvien_id,
                    "vtinh_id" : 98
                }).then(async res => {
                    var result = ''
                    if (res && res.data && res.data.error_code != "BSS-00000000") {
                        result = res.data.message_detail ? res.data.message_detail : res.data.message
                    }  
                    if (result && result.length > 0) {
                        this.$toast.error('Có lỗi: ' + JSON.stringify(result))
                    } else {
                        this.$toast.success('Chuyển NET thành công!')
                        await this.hienThiDanhSachHDKH();
                        this.loading(false)
                    }
                }).finally(e => {
                    this.loading(false)
                });
            } catch (e) {} finally { this.loading(false) }
        },
        rowDoubleClicked_HDTB:async function(rowIndex, dataItem) {
            if (this.thongtintimkiem.kieuhd_id == 1) {
                // this.frm_capnhat.vanban = await this.mapId("VANBAN", "css.hd_bancheo", " where tinh_ban=" + this.tt_nd.phanvung_id + " and hdtb_ban=" + this.hdtb_id);
                // this.frm_capnhat.noidung = await this.mapId("nd_chuyenthicong", "css.hd_bancheo", " where tinh_ban=" + this.tt_nd.phanvung_id + " and hdtb_ban=" + this.hdtb_id);
                this.frm_capnhat.vanban = await this.fn_tt_hd_bancheo(2, this.hdtb_id, this.tt_nd.phanvung_id);
                this.frm_capnhat.noidung = await this.fn_tt_hd_bancheo(3, this.hdtb_id, this.tt_nd.phanvung_id);
                this.frm_capnhat.tieude = "Xem nội dung chuyển hợp đồng thi công";
                this.frm_capnhat.visible_nut_capnhat = false;
                this.$refs.frmCapNhat.showModal();
            }
            if (this.thongtintimkiem.kieuhd_id == 3) {
                // this.frm_capnhat.vanban = await this.mapId("VANBAN", "css.hd_bancheo", " where tinh_ban=" + this.tt_nd.phanvung_id + " and hdtb_ban=" + this.hdtb_id);
                // this.frm_capnhat.noidung = await this.mapId("nd_thoaitra", "css.hd_bancheo", " where tinh_ban=" + this.tt_nd.phanvung_id + " and hdtb_ban=" + this.hdtb_id);
                this.frm_capnhat.vanban = await this.fn_tt_hd_bancheo(2, this.hdtb_id, this.tt_nd.phanvung_id);
                this.frm_capnhat.noidung = await this.fn_tt_hd_bancheo(1, this.hdtb_id, this.tt_nd.phanvung_id);
                this.frm_capnhat.tieude = "Xem nội dung Thoái trả hợp đồng";
                this.frm_capnhat.visible_nut_capnhat = false;
                this.$refs.frmCapNhat.showModal();
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
    }
}
</script>