<template>
    <div>
        <div class="main-wrapper">
            <div class="breadcrumb-top">
                <div class="main-title">{{thongtinhopdong.loaihopdong}}</div>
                <ul class="breadcrumb">
                    <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ</a></li>
                    <li class="breadcrumb-item"><a href="#">Quản lý trả trước</a></li>
                    <li class="breadcrumb-item active">Cập nhật thông tin trả trước doanh nghiệp</li>
                </ul>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a @click="nhapMoi" v-bind:class="{'disabled': disabled_nut.nhapmoi}" class="hand_click">
                            <span class="icon one-file-plus"></span>Nhập mới
                        </a>
                    </li>
                    <li>
                        <a @click="ghiLai" v-bind:class="{'disabled': disabled_nut.ghilai}" class="hand_click">
                            <span class="icon one-save"></span>Ghi lại
                        </a>
                    </li>
                    <li>
                        <a @click="huy" v-bind:class="{'disabled': disabled_nut.huy}" class="hand_click">
                            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ
                        </a>
                    </li>
                    <li>
                        <a @click="layMaXacThuc" class="hand_click">
                            <span class="icon one-email"></span>Gửi mã XT
                        </a>
                    </li>
                    <li>
                        <a @click="hoanThien" class="hand_click">
                            <span class="icon one-file-check"></span>Hoàn thiện
                        </a>
                    </li>
                    <li>
                        <a @click="openUploadImageForm" class="hand_click">
                            <span class="icon nc-icon-glyph design_image"></span>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="page-content">
                <div class="box-form">
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Mã giao dịch</div>
                                        <div class="value">
                                            <div class="input-more-button">
                                                <a class="btn" @click="showPopupSearchContract">
                                                    <span class="-ap icon-more_horiz"></span>
                                                </a>
                                                <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract"/>
                                                <input type="text" ref="ma_gd" class="form-control highlight" v-model="thongtinhopdong.magiaodich">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <vue-date format="DD/MM/YYYY" type="date" labelWidth="90" v-model="thongtinhopdong.ngayyeucau" label="Ngày yêu cầu"></vue-date>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Mã hợp đồng</div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinhopdong.mahopdong">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <vue-date format="DD/MM/YYYY" type="date" labelWidth="90" v-model="thongtinhopdong.ngaylaphd" label="Ngày lập HĐ"></vue-date>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <vue-select v-model="thongtinhopdong.loaihopdong" :options="thongtinhopdong.dsloaihopdong" valueField="ten" labelField="ten" labelWidth="130" label="Loại hợp đồng"></vue-select>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="box-form">
                    <div class="legend-title">Thông tin khách hàng</div>

                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120">Số máy (TK)</div>
                                <div class="value w20">
                                    <a class="btn btn-second" @click.prevent="clickButtonMaTB">
                                        <span class="-ap icon-more_horiz"></span>
                                    </a>
                                </div>
                                <div class="value">
                                    <div class="input-more-button">
                                        <span class="btn highlight text-desc bold" style="border-color: #666;">
                                            <span class="">84</span>
                                        </span>
                                        <input @keyup.enter="checkMaTB" type="text" v-model.trim="$v.thongtinkhachhang.somay.$model" class="form-control highlight" :class="{'is-invalid': $v.thongtinkhachhang.somay.$error }">
                                    </div>
                                    <div class="error" v-if="!$v.thongtinkhachhang.somay.maxLength">Số máy (TK) không được vượt quá tối đa {{ $v.thongtinkhachhang.somay.$params.maxLength.max }} ký tự</div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w120">Ds số máy <span class="red">(*)</span></div>
                                
                                <div class="value">
                                    <textarea v-model.trim="$v.thongtinkhachhang.dssomay.$model" class="form-control" v-bind:class="{'disabled': disabled_control.dssomay, 'is-invalid': $v.thongtinkhachhang.dssomay.$error}" style="height: 66px;resize: none;"></textarea>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w120">Tên khách hàng <span class="red">(*)</span></div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model.trim="$v.thongtinkhachhang.tenkhachhang.$model" v-bind:class="{'disabled': disabled_control.tenkh, 'is-invalid': $v.thongtinkhachhang.tenkhachhang.$error}">
                                    <div class="error" v-if="!$v.thongtinkhachhang.tenkhachhang.maxLength">Tên khách hàng không được vượt quá tối đa {{ $v.thongtinkhachhang.tenkhachhang.$params.maxLength.max }} ký tự</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Ngày sinh <span class="red">(*)</span></div>
                                        <div class="value">
                                            <vue-date style="margin-left:-5px" format="DD/MM/YYYY" type="date" v-model="$v.thongtinkhachhang.ngaysinh.$model" v-bind:class="{'disabled': disabled_control.ngaysinh, 'is-invalid': $v.thongtinkhachhang.ngaysinh.$error}"></vue-date>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <div class="key w90">Giới tính <span class="red">(*)</span></div>
                                        <div class="value">
                                            <div class="list-checks mart7" v-bind:class="{'disabled': disabled_control.gioitinh}">
                                                <div class="item">
                                                    <div class="check-action">
                                                        <input type="radio" class="check" value="male" v-model="$v.thongtinkhachhang.gioitinh.$model" :class="{ 'is-invalid': $v.thongtinkhachhang.gioitinh.$error }">
                                                        <span class="name">Nam</span>
                                                    </div>
                                                </div>
                                                <div class="item">
                                                    <div class="check-action">
                                                        <input type="radio" class="check" value="female" v-model="$v.thongtinkhachhang.gioitinh.$model" :class="{ 'is-invalid': $v.thongtinkhachhang.gioitinh.$error }">
                                                        <span class="name">Nữ</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Loại giấy tờ <span class="red">(*)</span></div>
                                        <div class="value">
                                            <SelectExt v-model="$v.thongtinkhachhang.loaigiayto.$model" :allowFiltering="true" dataTextField="TENLOAI_GT"
                                                dataValueField="LOAIGT_ID_NEO" :dataSource="thongtinkhachhang.dsloaigiayto" v-bind:class="{'disabled': disabled_control.loaigt, 'is-invalid': $v.thongtinkhachhang.loaigiayto.$error}">
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <div class="key w90">Số GT <span class="red">(*)</span></div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="$v.thongtinkhachhang.sogt.$model" v-bind:class="{'disabled': disabled_control.sogt, 'is-invalid': $v.thongtinkhachhang.sogt.$error}">
                                            <div class="error" v-if="!$v.thongtinkhachhang.sogt.maxLength">Số GT không được vượt quá tối đa {{ $v.thongtinkhachhang.sogt.$params.maxLength.max }} ký tự</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Ngày cấp GT <span class="red">(*)</span></div>
                                        <div class="value">
                                            <vue-date style="margin-left:-5px" format="DD/MM/YYYY" type="date" v-model="$v.thongtinkhachhang.ngaycapgt.$model" v-bind:class="{'disabled': disabled_control.ngaycapgt, 'is-invalid': $v.thongtinkhachhang.ngaycapgt.$error}"></vue-date>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <div class="key w90">Nơi cấp GT <span class="red">(*)</span></div>
                                        <div class="value">
                                            <SelectExt v-model="$v.thongtinkhachhang.noicapgt.$model" :allowFiltering="true" dataTextField="TENTINH_NEO"
                                                dataValueField="TENTAT_NEO" :dataSource="thongtinkhachhang.dsnoicapgt" v-bind:class="{'disabled': disabled_control.noicapgt, 'is-invalid': $v.thongtinkhachhang.noicapgt.$error}">
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Nước <span class="red">(*)</span></div>
                                        <div class="value">
                                            <SelectExt v-model="$v.thongtinkhachhang.nuoc.$model" :allowFiltering="true" dataTextField="TENQUOCTICH"
                                                dataValueField="QUOCTICH_NEO_ID" :dataSource="thongtinkhachhang.dsnuoc" v-bind:class="{'disabled': disabled_control.nuoc, 'is-invalid': $v.thongtinkhachhang.nuoc.$error}">
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <div class="key w90">Nghề nghiệp</div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinkhachhang.nghenghiep" v-bind:class="{'disabled': disabled_control.nghenghiep}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w120">Địa chỉ <span class="red">(*)</span></div>
                                <div class="value">
                                    <div class="input-more-button">
                                        <button class="btn" @click="openChonDiaChiForm">
                                            <span class="-ap icon-more_horiz"></span>
                                        </button>
                                        <input type="text" class="form-control" v-model="$v.thongtinkhachhang.diachi.$model" v-bind:class="{'disabled': disabled_control.diachi, 'is-invalid': $v.thongtinkhachhang.diachi.$error}">
                                    </div>
                                    <div class="error" v-if="!$v.thongtinkhachhang.diachi.maxLength">Địa chỉ không được vượt quá tối đa {{ $v.thongtinkhachhang.diachi.$params.maxLength.max }} ký tự</div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w120">Ghi chú</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="thongtinkhachhang.ghichu" v-bind:class="{'disabled': disabled_control.ghichu}">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w130">Loại khách hàng <span class="red">(*)</span></div>
                                <div class="value">
                                    <SelectExt v-model="$v.thongtinkhachhang.loaikh.$model" :allowFiltering="true" dataTextField="TEN_LKH"
                                        dataValueField="LOAIKH_ID" :dataSource="thongtinkhachhang.dsloaikh" :class="{ 'is-invalid': $v.thongtinkhachhang.loaikh.$error }">
                                    </SelectExt>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w130">Công ty</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="thongtinkhachhang.congty">
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w130">Địa chỉ công ty</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="thongtinkhachhang.diachicongty">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Email</div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinkhachhang.email">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Số giấy phép KD</div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinkhachhang.sogiayphepkd">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <vue-date format="DD/MM/YYYY" type="date" labelWidth="130" v-model="thongtinkhachhang.ngaycapgiaykd" label="Ngày cấp giấy KD"></vue-date>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Đơn vị cấp giấy KD</div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinkhachhang.dvcapgiaykd">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Số giấy giới thiệu</div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinkhachhang.sogiaygioithieu">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <vue-date format="DD/MM/YYYY" type="date" labelWidth="120" v-model="thongtinkhachhang.ngaycapgiaygt" label="Ngày cấp giấy GT"></vue-date>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Chức vụ</div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinkhachhang.chucvu">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Người ký giấy GT</div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinkhachhang.nguoikygiaygt">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key w130">
                                            <a @click="layMaXacThuc" class="link underline hand_click">Mã xác thực</a>
                                        </div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="thongtinkhachhang.maxacthuc">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w130">Ảnh CMT/Hộ chiếu</div>
                                <div class="value tleft">
                                    <a @click="chonHinh(1)" class="link underline hand_click">{{ten_img1}}</a>
                                    <a @click="chonHinh(2)" class="link underline hand_click">{{ten_img2}}</a>
                                </div>
                                <div class="value tright">
                                    <button class="btn btn-main" @click="openUploadImageForm">Tải ảnh</button>
                                </div>                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <b-modal id="frm-upload" size="xl" body-class="p-0" hide-header hide-header-close hide-footer>
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title">
                        <span class="icon nc-icon-outline ui-1_settings"></span> Chọn ảnh
                    </div>
                    <div @click="hideModal" class="close -ap icon-close" ></div>
                </div>
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a class="upload-button hand_click">
                                <span class="icon one-file-check-1"></span> Chọn File
                                <input type="file" class="file" ref="file" v-on:change="previewImage" multiple>
                            </a>
                        </li>
                        <li>
                            <a @click="uploadFile" class="hand_click">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-sm-3 col-12">
                                <div class="image">
                                    <img src="../../../../assets/uploads/No_Image_Available.jpeg" alt="" class="fullw hand_click" id="img_cmnd_truoc" @click="clickImage(1)">
                                    <input type="file" ref="file1" v-on:change="showImage($event, 1)" style="display:none">
                                </div>
                                <div class="title mart10 text center">Ảnh CMND mặt trước <span class="red">(*)</span></div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="image">
                                    <img src="../../../../assets/uploads/No_Image_Available.jpeg" alt="" class="fullw hand_click" id="img_cmnd_sau" @click="clickImage(2)">
                                    <input type="file" ref="file2" v-on:change="showImage($event, 2)" style="display:none">
                                </div>
                                <div class="title mart10 text center">Ảnh CMND mặt sau <span class="red">(*)</span></div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="image">
                                    <img src="../../../../assets/uploads/No_Image_Available.jpeg" alt="" class="fullw hand_click" id="img_nguoi_dk" @click="clickImage(3)">
                                    <input type="file" ref="file3" v-on:change="showImage($event, 3)" style="display:none">
                                </div>
                                <div class="title mart10 text center">Ảnh người đăng ký</div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="image">
                                    <img src="../../../../assets/uploads/No_Image_Available.jpeg" alt="" class="fullw hand_click" id="img_phieu_dk" @click="clickImage(4)">
                                    <input type="file" ref="file4" v-on:change="showImage($event, 4)" style="display:none">
                                </div>
                                <div class="title mart10 text center">Ảnh phiếu đăng ký</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </b-modal>

        <ModalChonDiaChi :modalId="'formdiachi'" @xacnhan="updateDiaChi"/>
    </div>
</template>
<style scoped>
.hand_click { cursor: pointer; }
</style>
<script>
import api from './api'
import ModalChonDiaChi from '../InstallNewSubs/Popups/popupChonDiaChi/ModalChonDiaChi'
import SearchContractModal from '../HandoverTicket/components/SearchContractModal';
import { required, maxLength } from 'vuelidate/lib/validators'
export default {
    data() {
        return {
            disabled_nut: {
                nhapmoi: true,
                ghilai: true,
                huy: true
            },
            disabled_control: {
                dssomay: true,
                tenkh: true,
                ngaysinh: true,
                sogt: true,
                ngaycapgt: true,
                noicapgt: true,
                nuoc: true,
                nghenghiep: true,
                diachi: true,
                ghichu: true,
                gioitinh: true,
                loaigt: true
            },
            thongtinhopdong: {
                magiaodich: '',
                ngayyeucau: '',
                loaihopdong: 'Cập nhật thông tin thuê bao Doanh nghiệp',
                dsloaihopdong: [{ten:'Nhập mới thông tin thuê bao Doanh nghiệp'},{ten: 'Cập nhật thông tin thuê bao Doanh nghiệp'}],
                mahopdong: '',
                ngaylaphd: ''
            },
            thongtinkhachhang: {
                somay: '',
                dssomay: '',
                tenkhachhang: '',
                ngaysinh: '',
                gioitinh: 'male',
                loaigiayto: 0,
                dsloaigiayto: [],
                sogt: '',
                ngaycapgt: '',
                noicapgt: '',
                dsnoicapgt: [],
                nuoc: 0,
                dsnuoc: [],
                nghenghiep: '',
                diachi: '',
                loaikh: 0,
                dsloaikh: [],
                congty: '',
                diachicongty: '',
                email: '',
                sogiayphepkd: '',
                ngaycapgiaykd: '',
                dvcapgiaykd: '',
                sogiaygioithieu: '',
                ngaycapgiaygt: '',
                chucvu: '',
                nguoikygiaygt: '',
                ghichu: '',
                maxacthuc: ''
            },
            tt_nd: {
                may_cn: 'localhost',
                ip_cn: '127.0.0.1',
                nguoi_cn: this.$root.token.getUserName(),
                phanvung_id: Number(this.$root.token.getPhanVungID()),
                donvi_id: Number(this.$root.token.getDonViID()),
                nhanvien_id: Number(this.$root.token.getNhanVienID())
            },
            ktra_matb_lap_hd_khac: false,
            dshd_tontai: '',
            dsDBTB: [],
            thuebao_id: null,
            khachhang_id: null,
            so_dt: '',
            donvi_id: null,
            doituong_id: 145, // Gán đại là KHDN
            doituong_id_neo: null,
            simcard: '',
            tinhkhac: 0,
            loi_sinh_magd: false,
            hdtt_id: null,
            hdkh_id: null,
            hdtb_id: null,
            thanhtoan_id: null,
            dsHDKH: [],
            dsHDTB: [],
            dsHDTB_DD: [],
            dsHDTT: [],
            tuyenthu_id: 0,
            dsDiaChi: [],
            dsDiaChiHDKH: [],
            dsDiaChiHDTT: [],
            dsDiaChiHDTB: [],
            diachikh_id: null,
            tinhkh_id: null,
            quankh_id: null,
            phuongkh_id: null,
            phokh_id: null,
            apkh_id: null,
            khukh_id: null,
            dacdiemkh_id: null,
            sonhakh: '',
            diachitt_id: null,
            tinhtt_id: null,
            quantt_id: null,
            phuongtt_id: null,
            phott_id: null,
            aptt_id: null,
            khutt_id: null,
            dacdiemtt_id: null,
            sonhatt: '',
            diachibc_id: null,
            diachild_id: null,
            tinhld_id: null,
            quanld_id: null,
            phuongld_id: null,
            phold_id: null,
            apld_id: null,
            khuld_id: null,
            dacdiemld_id: null,
            sonhald: '',
            diachitb_id: null,
            tinhtb_id: null,
            quantb_id: null,
            phuongtb_id: null,
            photb_id: null,
            aptb_id: null,
            khutb_id: null,
            dacdiemtb_id: null,
            sonhatb: '',
            loaitb_id: 21,
            dichvuvt_id: 2,
            huonggiao_id: 5600,
            homnay: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear() + ' ' + ((new Date()).getHours()>9 ? '' : '0') + (new Date()).getHours() + ':' + ((new Date()).getMinutes()>9 ? '' : '0') + (new Date()).getMinutes() + ':' + ((new Date()).getSeconds()>9 ? '' : '0') + (new Date()).getSeconds(),
            arrCCBS: [],
            link_img1: '',
            link_img2: '',
            ten_img1: '',
            ten_img2: '',
            base64_img1: '',
            base64_img2: '',
            path_img1: '',
            path_img2: '',
            ma_khdn: '',
            ma_nguoidd: ''
        }
    },
    created() {
        this.khoiTao();
    },
    components: {
        ModalChonDiaChi,
        SearchContractModal
    },
    validations: 
    {
        // Cách khai báo cho đối tượng
        thongtinkhachhang: {
            somay: { required, maxLength: maxLength(30) },
            dssomay: { required },
            sogt: { required, maxLength: maxLength(100) },
            ngaysinh: { required },
            ngaycapgt: { required },
            tenkhachhang: { required, maxLength: maxLength(200) },
            loaigiayto: { required },
            loaikh: { required },
            gioitinh: { required },
            noicapgt: { required },
            nuoc: { required },
            diachi: { required, maxLength: maxLength(500) }
        }
    },
    methods: {
        chuanHoaTen (ten) {
            if (!ten) return '';
            let tenNew = '';
            let strCon = ten.trim();
            let strXL = '';
            let k = 0;
            let found = true;
            if (strCon.length < 1) return;
            strCon = strCon.replaceAll(/\s+/g, ' ');
            try {
                while (found) {
                    k = strCon.indexOf(' ');
                    if (k > 0) {
                        strXL = strCon.substring(0, k);
                        strCon = strCon.substring(k + 1);
                        strXL = this.ChuyenChuHoa(strXL);
                        if (tenNew !== '') {
                            tenNew += ' ' + strXL;
                        } else tenNew = strXL;
                        found = true;
                    } else found = false
                }
                if (strCon !== '') {
                    strXL = strCon;
                    strXL = this.ChuyenChuHoa(strXL);
                    if (tenNew !== '') {
                        tenNew = tenNew + ' ' + strXL;
                    } else {
                        tenNew = strXL;
                    }
                }
                return tenNew;
            } catch (e) {
                return ten;
            }
        },
        chuyenChuHoa (str) {
            let kq = '';
            let regex = /[A-Z][A-Z]/g;
            if (regex.test(str)) kq = str.toUpperCase();
            else {
                if (str[0] === '(') {
                    kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase();
                } else {
                    kq = str[0].toUpperCase() + str.substring(1).toLowerCase();
                }
            }
            return kq;
        },
        loadInterface(num) {
            this.disabled_nut.nhapmoi = true;
            this.disabled_nut.ghilai = false;
            this.disabled_nut.huy = false;
            if (num == -1) { // Nút Nhập mới disable => các control disable
                this.disabled_control.dssomay = this.disabled_nut.nhapmoi;
                this.disabled_control.tenkh = this.disabled_nut.nhapmoi;
                this.disabled_control.ngaysinh = this.disabled_nut.nhapmoi;
                this.disabled_control.sogt = this.disabled_nut.nhapmoi;
                this.disabled_control.ngaycapgt = this.disabled_nut.nhapmoi;
                this.disabled_control.noicapgt = this.disabled_nut.nhapmoi;
                this.disabled_control.nuoc = this.disabled_nut.nhapmoi;
                this.disabled_control.nghenghiep = this.disabled_nut.nhapmoi;
                this.disabled_control.diachi = this.disabled_nut.nhapmoi;
                this.disabled_control.ghichu = this.disabled_nut.nhapmoi;
                this.disabled_control.gioitinh = this.disabled_nut.nhapmoi;
                this.disabled_control.loaigt = this.disabled_nut.nhapmoi;
            }
            if (num == 0) { // Nút Nhập mới disable => các control enable
                this.disabled_control.dssomay = !this.disabled_nut.nhapmoi;
                this.disabled_control.tenkh = !this.disabled_nut.nhapmoi;
                this.disabled_control.ngaysinh = !this.disabled_nut.nhapmoi;
                this.disabled_control.sogt = !this.disabled_nut.nhapmoi;
                this.disabled_control.ngaycapgt = !this.disabled_nut.nhapmoi;
                this.disabled_control.noicapgt = !this.disabled_nut.nhapmoi;
                this.disabled_control.nuoc = !this.disabled_nut.nhapmoi;
                this.disabled_control.nghenghiep = !this.disabled_nut.nhapmoi;
                this.disabled_control.diachi = !this.disabled_nut.nhapmoi;
                this.disabled_control.ghichu = !this.disabled_nut.nhapmoi;
                this.disabled_control.gioitinh = !this.disabled_nut.nhapmoi;
                this.disabled_control.loaigt = !this.disabled_nut.nhapmoi;
            }
            if (num == 1) { // Nút Nhập mới enable => các control enable
                this.disabled_nut.nhapmoi = false;
                this.disabled_control.dssomay = this.disabled_nut.nhapmoi;
                this.disabled_control.tenkh = this.disabled_nut.nhapmoi;
                this.disabled_control.ngaysinh = this.disabled_nut.nhapmoi;
                this.disabled_control.sogt = this.disabled_nut.nhapmoi;
                this.disabled_control.ngaycapgt = this.disabled_nut.nhapmoi;
                this.disabled_control.noicapgt = this.disabled_nut.nhapmoi;
                this.disabled_control.nuoc = this.disabled_nut.nhapmoi;
                this.disabled_control.nghenghiep = this.disabled_nut.nhapmoi;
                this.disabled_control.diachi = this.disabled_nut.nhapmoi;
                this.disabled_control.ghichu = this.disabled_nut.nhapmoi;
                this.disabled_control.gioitinh = this.disabled_nut.nhapmoi;
                this.disabled_control.loaigt = this.disabled_nut.nhapmoi;
            }
        },
        clearData() {
            this.thongtinkhachhang.somay = "";
            this.thongtinkhachhang.dssomay = "";
            this.thongtinkhachhang.tenkhachhang = "";
			this.thongtinkhachhang.sogt = "";
			this.thongtinkhachhang.nghenghiep = "";
			this.thongtinkhachhang.diachi = "";
			this.thongtinkhachhang.ghichu = "";
			this.thongtinkhachhang.congty = "";
			this.thongtinkhachhang.diachicongty = "";
			this.thongtinkhachhang.email = "";
			this.thongtinkhachhang.sogiayphepkd = "";
			this.thongtinkhachhang.dvcapgiaykd = "";
			this.thongtinkhachhang.sogiaygioithieu = "";
			this.thongtinkhachhang.chucvu = "";
			this.thongtinkhachhang.nguoikygiaygt = "";
			this.thongtinkhachhang.maxacthuc = "";
			this.thongtinkhachhang.ngaycapgiaygt = "";
			this.thongtinkhachhang.ngaycapgiaykd = "";
			this.thongtinkhachhang.ngaycapgt = "";
			this.thongtinkhachhang.ngaysinh = "";
			this.thongtinkhachhang.loaigiayto = 1;
			this.thongtinkhachhang.noicapgt = this.thongtinkhachhang.dsnoicapgt.find(x => x.TINH_ID_NEO == this.tt_nd.phanvung_id) ? this.thongtinkhachhang.dsnoicapgt.find(x => x.TINH_ID_NEO == this.tt_nd.phanvung_id).TENTAT_NEO : "";
			this.thongtinkhachhang.nuoc = 232;
			this.thongtinkhachhang.dvcapgiaykd = "";
        },
        khoiTao() {
            let self = this;
            api.getDanhMucCNTraTruoc(this.axios, 'LOAI_KHDN_NEO').then(res => {
                var dstemp = [...new Map(res.data.data.map(item => [item['LOAIKH_ID'], item])).values()];
                this.thongtinkhachhang.dsloaikh = res.data.data ? dstemp : [];
                this.thongtinkhachhang.loaikh = this.thongtinkhachhang.dsloaikh.length>0 ? res.data.data[0].LOAIKH_ID : 0;
            }).catch(err => {
                self.thongtinkhachhang.dsloaikh = [];
                self.thongtinkhachhang.loaikh = 0;
                self.$toast.error('Không lấy được danh sách Loại khách hàng: ' + err.data.message);
            });

            api.getDanhMucCNTraTruoc(this.axios, 'LOAI_GT_NEO').then(res => {
                this.thongtinkhachhang.dsloaigiayto = res.data.data ? res.data.data : [];
                this.thongtinkhachhang.loaigiayto = this.thongtinkhachhang.dsloaigiayto.length>0 ? 1 : 0;
            }).catch(err => {
                self.thongtinkhachhang.dsloaigiayto = [];
                self.thongtinkhachhang.loaigiayto = 0;
                self.$toast.error('Không lấy được danh sách Loại giấy tờ: ' + err.data.message);
            });

            api.getDanhMucCNTraTruoc(this.axios, 'TINH_NEOS').then(res => {
                this.thongtinkhachhang.dsnoicapgt = res.data.data ? res.data.data : [];
                this.thongtinkhachhang.noicapgt = this.thongtinkhachhang.dsnoicapgt.find(x => x.TINH_ID_NEO == this.tt_nd.phanvung_id) ? this.thongtinkhachhang.dsnoicapgt.find(x => x.TINH_ID_NEO == this.tt_nd.phanvung_id).TENTAT_NEO : "";
            }).catch(err => {
                self.thongtinkhachhang.dsnoicapgt = [];
                self.thongtinkhachhang.noicapgt = '';
                self.$toast.error('Không lấy được danh sách Nơi cấp giấy tờ: ' + err.data.message);
            });

            api.getDanhMucCNTraTruoc(this.axios, 'QUOCTICH_NEO').then(res => {
                this.thongtinkhachhang.dsnuoc = res.data.data ? res.data.data : [];
                this.thongtinkhachhang.nuoc = this.thongtinkhachhang.dsnuoc.length>0 ? 232 : 0;
            }).catch(err => {
                self.thongtinkhachhang.dsnuoc = [];
                self.thongtinkhachhang.nuoc = 0;
                self.$toast.error('Không lấy được danh sách Nước: ' + err.data.message);
            });

            this.loadInterface(-1);
        },
        nhapMoi() {
            this.loadInterface(-1);
            this.clearData();
        },
        layThongTinTbKHDN: async function() {
            let co_tttb = false;
            let self = this;
            await api.getTT_TB_KHDN(this.axios, {
                "so_tb": "84" + this.thongtinkhachhang.somay
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                    this.arrCCBS = res.data.data;
                    let ccbs = res.data.data[0];
                    if (ccbs.fullname) {
                        co_tttb = true;
                        this.loadInterface(1);
                        this.thongtinkhachhang.dssomay = ccbs.dssomay;
                        this.thongtinkhachhang.tenkhachhang = ccbs.fullname;
                        this.thongtinkhachhang.ngaysinh = ccbs.birthday;
                        this.thongtinkhachhang.gioitinh = ccbs.gender;
                        this.thongtinkhachhang.loaigiayto = ccbs.idnumbertype;
                        this.thongtinkhachhang.sogt = ccbs.idnumber;
                        this.thongtinkhachhang.ngaycapgt = ccbs.placedate;
                        this.thongtinkhachhang.noicapgt = ccbs.placeofissue;
                        this.thongtinkhachhang.nuoc = ccbs.nationalityid;
                        this.thongtinkhachhang.nghenghiep = ccbs.job;
                        this.thongtinkhachhang.diachi = ccbs.address;
                        this.thongtinkhachhang.ghichu = ccbs.note;
                        this.thongtinkhachhang.loaikh = ccbs.customertype;
                        this.thongtinkhachhang.congty = ccbs.company;
                        this.thongtinkhachhang.diachicongty = ccbs.address_company;
                        this.thongtinkhachhang.sogiayphepkd = ccbs.so_giay_kd;
                        this.thongtinkhachhang.email = ccbs.email;
                        this.thongtinkhachhang.ngaycapgiaykd = ccbs.ngay_cap_kd;
                        this.thongtinkhachhang.chucvu = ccbs.chuc_vu;
                        this.thongtinkhachhang.nguoikygiaygt = ccbs.nguoi_ky;
                        this.thongtinkhachhang.sogiaygioithieu = ccbs.so_giay_gt;
                        this.thongtinkhachhang.dvcapgiaykd = ccbs.noi_cap_kd;
                        this.thongtinkhachhang.ngaycapgiaygt = ccbs.ngaycap_ggt;
                        this.thongtinkhachhang.maxacthuc = '';
                        this.ma_khdn = ccbs.ma_khdn ? ccbs.ma_khdn : '';
                        this.ma_nguoidd = ccbs.ma_nguoidd ? ccbs.ma_nguoidd : '';
                    } else {
                        co_tttb = false;
                        this.loadInterface(0);
				        this.clearData();
                        this.$toast.error('Thuê bao chưa có thông tin trả trước khách hàng doanh nghiệp.');
                    }
                } else {
                    co_tttb = false;
                    this.loadInterface(0);
                    this.clearData();
                    let str = res.data.message.replace('$newline$','\n');
                    this.$toast.error(str);
                }
            }).catch(err => {
                co_tttb = false;
                self.loadInterface(0);
                self.clearData();
                // let str = err.data.message.replace('$newline$','\n');
                // self.$toast.error(str);
                this.$toast.error('Thuê bao chưa có thông tin trả trước khách hàng doanh nghiệp.');
            });

            if (co_tttb) {
                api.getTTTB_IMG_KHDN(this.axios, {
                    "so_tb": this.thongtinkhachhang.dssomay
                }).then(res => {
                    this.ten_img1 = "";
                    this.base64_img1 = "";
                    this.ten_img2 = "";
                    this.base64_img2 = "";

                    if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                        let ds_img = res.data.data;
                        if (ds_img.find(x => x.type == 5)) {
                            this.base64_img1 = ds_img.find(x => x.type == 5).image_base;
                            this.ten_img1 = "Mặt trước CMT";
                        }
                        if (ds_img.find(x => x.type == 6)) {
                            this.base64_img2 = ds_img.find(x => x.type == 6).image_base;
                            this.ten_img2 = "Mặt sau CMT";
                        }
                    }
                });
            }
        },
        layThongTinTbKHDN_CapNhat: async function() {
            let co_tttb = false;
            await api.getTT_TB_KHDN(this.axios, {
                "so_tb": "84" + this.thongtinkhachhang.somay
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                    this.arrCCBS = res.data.data;
                    let ccbs = res.data.data[0];
                    if (ccbs.fullname) {
                        co_tttb = true;
                        this.ma_khdn = ccbs.ma_khdn ? ccbs.ma_khdn : '';
                        this.ma_nguoidd = ccbs.ma_nguoidd ? ccbs.ma_nguoidd : '';
                    } else {
                        co_tttb = false;
                    }
                } else {
                    co_tttb = false;
                }
            }).catch(() => {
                co_tttb = false;
            });

            if (co_tttb) {
                api.getTTTB_IMG_KHDN(this.axios, {
                    "so_tb": this.thongtinkhachhang.dssomay
                }).then(res => {
                    this.ten_img1 = "";
                    this.base64_img1 = "";
                    this.ten_img2 = "";
                    this.base64_img2 = "";

                    if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                        let ds_img = res.data.data;
                        if (ds_img.find(x => x.type == 5)) {
                            this.base64_img1 = ds_img.find(x => x.type == 5).image_base;
                            this.ten_img1 = "Mặt trước CMT";
                        }
                        if (ds_img.find(x => x.type == 6)) {
                            this.base64_img2 = ds_img.find(x => x.type == 6).image_base;
                            this.ten_img2 = "Mặt sau CMT";
                        }
                    }
                });
            }
        },
        layDanhBaTheoMaTB: async function() {
            let self = this;
            await api.getDanhBaTheoMaTB(this.axios, {
                "in_ma_tb": this.thongtinkhachhang.somay,
                "in_dichvuvt_id": 2,
                "in_donvi_dl_id": 0
            }).then(res => {
                this.dsDBTB = res.data.data ? res.data.data : [];
                if (this.dsDBTB.length > 0) {
                    if (Number(this.dsDBTB[0].trangthaitb_id) == 7 || Number(this.dsDBTB[0].trangthaitb_id) == 9 || Number(this.dsDBTB[0].trangthaitb_id) == 8) {
                        var str = "Thuê bao có mã: " + this.dsDBTB[0].ma_tb + " đang ở trạng thái: " + this.dsDBTB[0].trangthai_tb + "\nBạn không thể thực hiện cập nhật TT thuê bao này";
                        this.$toast.error(str);
                    } else {
                        this.thuebao_id = Number(this.dsDBTB[0].thuebao_id);
                        this.thongtinkhachhang.dssomay = "84" + this.thongtinkhachhang.somay;
                        
                        this.thongtinhopdong.mahopdong = this.dsDBTB[0].ma_hd;
                        
                        this.khachhang_id = Number(this.dsDBTB[0].khachhang_id);
                        this.thongtinkhachhang.tenkhachhang = this.chuanHoaTen(this.dsDBTB[0].ten_kh);
                        this.thongtinkhachhang.diachi = this.chuanHoaTen(this.dsDBTB[0].diachi_kh);
                        let date_ngaylap_hd = new Date(this.dsDBTB[0].ngaylap_hd);

                        this.thongtinhopdong.ngaylaphd = (date_ngaylap_hd.getDate() > 9 ? '' : '0') + date_ngaylap_hd.getDate() + '/' + ((date_ngaylap_hd.getMonth() + 1) > 9 ? '' : '0') + (date_ngaylap_hd.getMonth() + 1) + '/' + date_ngaylap_hd.getFullYear();
                        
                        this.so_dt = this.dsDBTB[0].so_dt;
                        this.thongtinkhachhang.sogt = this.dsDBTB[0].so_gt;
                        let date_ngaysinh = new Date(this.dsDBTB[0].ngay_sn);
                        this.thongtinkhachhang.ngaysinh = (date_ngaysinh.getDate() > 9 ? '' : '0') + date_ngaysinh.getDate() + '/' + ((date_ngaysinh.getMonth() + 1) > 9 ? '' : '0') + (date_ngaysinh.getMonth() + 1) + '/' + date_ngaysinh.getFullYear();
                        let date_ngaycap_gt = new Date(this.dsDBTB[0].ngaycap);
                        this.thongtinkhachhang.ngaycapgt = (date_ngaycap_gt.getDate() > 9 ? '' : '0') + date_ngaycap_gt.getDate() + '/' + ((date_ngaycap_gt.getMonth() + 1) > 9 ? '' : '0') + (date_ngaycap_gt.getMonth() + 1) + '/' + date_ngaycap_gt.getFullYear();
                        this.donvi_id = Number(this.dsDBTB[0].donviql_id);
                        this.doituong_id = Number(this.dsDBTB[0].doituong_id);
                        let value = this.dsDBTB[0].doituong_id_neo;
                        if (value == null) {
                            this.doituong_id_neo = 1;
                        } else {
                            this.doituong_id_neo = Number(this.dsDBTB[0].doituong_id_neo);
                        }
                        this.simcard = this.dsDBTB[0].simcard;
                        this.thongtinkhachhang.gioitinh = this.dsDBTB[0].gioitinh ? (Number(this.dsDBTB[0].gioitinh) == 1 ? 'male' : 'female') : '';
                    }
                } else {
                    var str = "Không tìm thấy thông tin về mã thuê bao " + this.thongtinkhachhang.somay;
                    this.$toast.error(str);
                }
            }).catch(() => {
                var str = "Không tìm thấy thông tin về mã thuê bao " + this.thongtinkhachhang.somay;
                self.$toast.error(str);
            });
        },
        kiemTraMaTBLapHDKhac: async function() {
            let result = false;
            let self = this;
            await api.checkMaTBLapHDKhac(this.axios, {
                "vma_tb": this.thongtinkhachhang.somay,
                "vma_gd": 0,
                "vdichvuvt_id": 2,
                "vloaihd_id": 100
            }).then(async (res) => {
                result = res.data.data.length > 0 ? true : false;
                let arr = [];
                this.dshd_tontai = '';
                if (res.data.data && res.data.data.length > 0) {
                    let ds = res.data.data;
                    for (let i=0; i<ds.length; i++) {
                        arr.push(ds[i].ma_gd);
                    }
                    this.dshd_tontai = arr.join(', ');
                }
            }).catch(err => {
                self.$toast.error(err.data.message);
            });
            return result;
        },
        checkMaTB: async function(event) {
            if (event) {
                if (this.thongtinhopdong.loaihopdong == "Nhập mới thông tin thuê bao Doanh nghiệp") {
                    await this.layThongTinTbKHDN();
                } else {
                    this.ktra_matb_lap_hd_khac = false;//await this.kiemTraMaTBLapHDKhac();
                    if (!this.ktra_matb_lap_hd_khac) {
                        await this.layDanhBaTheoMaTB();
                        await this.layThongTinTbKHDN_CapNhat(); // Hòa thêm chỗ này gán biến ma_khdn và ma_nguoidd để phục vụ việc dangKyCCBS
                        if (this.dsDBTB.length > 0) {
                            this.tinhkhac = 0;
                        } else {
                            this.tinhkhac = 1;
                        }
                        this.loadInterface(0);
                    } else {
                        this.$toast.error("Thuê bao " + this.thongtinkhachhang.somay + " đang tồn tại (các) hợp đồng khác có mã giao dịch: " + this.dshd_tontai + " chưa hoàn thành!");
                    }
                }
            }
        },
        clickButtonMaTB: async function() {
            if (this.thongtinhopdong.loaihopdong == 'Nhập mới thông tin thuê bao Doanh nghiệp') {
                await this.layThongTinTbKHDN();
            }
        },
        layMaGD: async function() {
            let self = this;
            await api.getMaGDv2(this.axios, {
                "vdonvi_id": Number(this.$root.token.getDonViDuLieuID()),
                "vloaihd_id": 100
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000') {
                    this.thongtinhopdong.magiaodich = res.data.data ? res.data.data : "";
                } else {
                    this.$toast.error(res.data.message);
                    this.loi_sinh_magd = true;
                    return;
                }
            }).catch(err => {
                self.$toast.error(err.data.message);
                self.loi_sinh_magd = true;
                return;
            });
        },
        layMaHD: async function() {
            let self = this;
            await api.getMaHD(this.axios, {
                "vdonvi_id": Number(this.$root.token.getDonViDuLieuID())
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000') {
                    this.thongtinhopdong.mahopdong = res.data.data ? res.data.data : "";
                } else {
                    this.$toast.error(res.data.message);
                    return;
                }
            }).catch(err => {
                self.$toast.error(err.data.message);
                return;
            });
        },
        chuyenStringToDate(str) {
            let strConver = str + " 00:00:00";
            return str == '' ? null : strConver;
        },
        taoDuLieuHDKH: async function() {
            this.dsHDKH = [];
            if (!this.hdkh_id || this.hdkh_id == 0) {
                await api.getKey(this.axios, {
                    "keyname": "HD_KHACHHANG"
                }).then(res => {
                    this.hdkh_id = res.data.data ? Number(res.data.data) : 0;
                });
            }
            let hd_KHACHHANGRow = {};
            if (this.khachhang_id > 0) {
                api.getDsDBKHTheoKHId(this.axios, {
                    "in_khachhang_id": this.khachhang_id
                }).then(res => {
                    if (res.data.data) {
                        hd_KHACHHANGRow.PHANVUNG_ID = res.data.data[0].phanvung_id;
                        hd_KHACHHANGRow.KHACHHANG_ID = res.data.data[0].khachhang_id;
                        hd_KHACHHANGRow.MA_KH = res.data.data[0].ma_kh;
                        hd_KHACHHANGRow.TEN_KH = res.data.data[0].ten_kh;
                        hd_KHACHHANGRow.DIACHI_KH = res.data.data[0].diachi_kh;
                        hd_KHACHHANGRow.SO_DT = res.data.data[0].so_dt;
                        hd_KHACHHANGRow.SO_FAX = res.data.data[0].so_fax;
                        hd_KHACHHANGRow.EMAIL = res.data.data[0].email;
                        hd_KHACHHANGRow.WEBSITE = res.data.data[0].website;
                        hd_KHACHHANGRow.NGAY_SN = res.data.data[0].ngay_sn ? this.chuyenStringToDate(res.data.data[0].ngay_sn) : null;
                        hd_KHACHHANGRow.SO_GT = res.data.data[0].so_gt;
                        hd_KHACHHANGRow.NGAYCAP = res.data.data[0].ngaycap ? this.chuyenStringToDate(res.data.data[0].ngaycap) : null;
                        hd_KHACHHANGRow.NOICAP = res.data.data[0].noicap;
                        hd_KHACHHANGRow.GHICHU = res.data.data[0].ghichu;
                        hd_KHACHHANGRow.KHLON_ID = res.data.data[0].khlon_id;
                        hd_KHACHHANGRow.KHACHHANG_CHA_ID = res.data.data[0].khachhang_cha_id;
                        hd_KHACHHANGRow.LOAIGT_ID = res.data.data[0].loaigt_id;
                        hd_KHACHHANGRow.MA_HD = res.data.data[0].ma_hd;
                        hd_KHACHHANGRow.MAKH_CU = res.data.data[0].makh_cu;
                        hd_KHACHHANGRow.MAY_CN = res.data.data[0].may_cn;
                        hd_KHACHHANGRow.NGAY_CN = res.data.data[0].ngay_cn ? this.chuyenStringToDate(res.data.data[0].ngay_cn) : null;
                        hd_KHACHHANGRow.NGUOI_CN = res.data.data[0].nguoi_cn;
                        hd_KHACHHANGRow.NGAYLAP_HD = res.data.data[0].ngaylap_hd ? this.chuyenStringToDate(res.data.data[0].ngaylap_hd) : null;
                        hd_KHACHHANGRow.KH_KT3 = res.data.data[0].kh_kt3;
                        hd_KHACHHANGRow.KM_HD = res.data.data[0].km_hd;
                        hd_KHACHHANGRow.HOKHAU = res.data.data[0].hokhau;
                        hd_KHACHHANGRow.LOAIKH_ID = res.data.data[0].loaikh_id;
                        hd_KHACHHANGRow.NOICAP_HK = res.data.data[0].noicap_hk;
                        hd_KHACHHANGRow.NGAY_HK = res.data.data[0].ngay_hk;
                        hd_KHACHHANGRow.NGUOI_DD = res.data.data[0].nguoi_dd;
                        hd_KHACHHANGRow.CHUCDANH = res.data.data[0].chucdanh;
                        hd_KHACHHANGRow.KY_HD = res.data.data[0].ky_hd;
                        hd_KHACHHANGRow.KIEUKH_ID = res.data.data[0].kieukh_id;
                        hd_KHACHHANGRow.KETQUA_CH = res.data.data[0].ketqua_ch;
                        hd_KHACHHANGRow.GIOITINH = res.data.data[0].gioitinh;
                        hd_KHACHHANGRow.DANTOC_ID = res.data.data[0].dantoc_id;
                        hd_KHACHHANGRow.QUOCTICH_ID = res.data.data[0].quoctich_id;
                        hd_KHACHHANGRow.NGANHNGHE_ID = res.data.data[0].nganhnghe_id;
                        hd_KHACHHANGRow.DIACHI_NDD = res.data.data[0].diachi_ndd;
                        hd_KHACHHANGRow.NGAYHETHAN_GT = res.data.data[0].ngayhethan_gt ? this.chuyenStringToDate(res.data.data[0].ngayhethan_gt) : null;
                        hd_KHACHHANGRow.MST = res.data.data[0].mst;
                        hd_KHACHHANGRow.STK = res.data.data[0].stk;
                        hd_KHACHHANGRow.NGANHANG_ID = res.data.data[0].nganhang_id;
                        hd_KHACHHANGRow.DACTRUNG = res.data.data[0].dactrung;
                        hd_KHACHHANGRow.TNC1_ID = res.data.data[0].tnc1_id;
                        hd_KHACHHANGRow.TNC2_ID = res.data.data[0].tnc2_id;
                        hd_KHACHHANGRow.TNC3_ID = res.data.data[0].tnc3_id;
                        hd_KHACHHANGRow.KHACHHANGCU_ID = res.data.data[0].khachhangcu_id;
                        hd_KHACHHANGRow.CTV_ID = res.data.data[0].ctv_id;
                        hd_KHACHHANGRow.MA_HD_CU = res.data.data[0].ma_hd_cu;
                        hd_KHACHHANGRow.PHANLOAIKH_ID = res.data.data[0].phanloaikh_id;
                        hd_KHACHHANGRow.MA_KH_WEB = res.data.data[0].ma_kh_web;
                        hd_KHACHHANGRow.MA_KH_CRM = res.data.data[0].ma_kh_crm;
                        hd_KHACHHANGRow.TNC4_ID = res.data.data[0].tnc4_id;
                        hd_KHACHHANGRow.HDKH_CHA_ID = res.data.data[0].hdkh_cha_id;
                        hd_KHACHHANGRow.MA_KH_VNP = res.data.data[0].ma_kh_vnp;
                        hd_KHACHHANGRow.TEN_KH_CU = res.data.data[0].ten_kh_cu;
                        hd_KHACHHANGRow.LOAIKH_ID_VTN = res.data.data[0].loaikh_id_vtn;
                        hd_KHACHHANGRow.LOAIKH_ID_HDDT = res.data.data[0].loaikh_id_hddt;
                    }
                });
            }
            hd_KHACHHANGRow.DIACHI_KH = this.thongtinkhachhang.diachi;
            hd_KHACHHANGRow.HDKH_ID = this.hdkh_id;
            let kiemtra_magd = (!this.thongtinhopdong.magiaodich || this.thongtinhopdong.magiaodich.trim() == "") ? false : true;
            let kiemtra_mahd = (!this.thongtinhopdong.mahopdong || this.thongtinhopdong.mahopdong.trim() == "") ? false : true;
            if (!kiemtra_magd || !kiemtra_mahd) {
                if (!kiemtra_magd) {
                    await this.layMaGD();
                } 
                if (!kiemtra_mahd) {
                    await this.layMaHD();
                }
                hd_KHACHHANGRow.MA_GD = this.thongtinhopdong.magiaodich;
                hd_KHACHHANGRow.MA_HD = this.thongtinhopdong.mahopdong;
                if (!this.khachhang_id || this.khachhang_id == 0) {
                    await api.getMaKH(this.axios, {
                        "vdonvi_id": Number(this.$root.token.getDonViDuLieuID())
                    }).then(res => {
                        hd_KHACHHANGRow.MA_KH = res.data.data ? res.data.data : "";
                    });
                }
            } else {
                hd_KHACHHANGRow.MA_GD = this.thongtinhopdong.magiaodich;
                hd_KHACHHANGRow.MA_HD = this.thongtinhopdong.mahopdong;
            }
            if (this.khachhang_id > 0) {
                hd_KHACHHANGRow.KHACHHANG_ID = this.khachhang_id;
                let in_dk = "where khachhang_id=" + this.khachhang_id + " and phanvung_id=" + this.tt_nd.phanvung_id;
                await api.mapId(this.axios, {
                    "in_id_neo": "khlon_id",
                    "in_table": "css.db_khachhang",
                    "in_dk": in_dk
                }).then(res => {
                    if (res.data.error_code === 'BSS-00000000') {
                        let text2 = res.data.data;
                        if (text2 != "" && Number(text2) != -1) {
                            hd_KHACHHANGRow.KHLON_ID = Number(text2);
                        } else {
                            hd_KHACHHANGRow.KHLON_ID = 1;
                        }
                    } else {
                        hd_KHACHHANGRow.KHLON_ID = 1;
                    }
                });
            } else {
                hd_KHACHHANGRow.KHLON_ID = 1;
            }
            hd_KHACHHANGRow.TEN_KH = this.thongtinkhachhang.tenkhachhang; // CommonFunction.StringToString()
            hd_KHACHHANGRow.DIACHI_KH = this.thongtinkhachhang.diachi; // CommonFunction.StringToString()
            hd_KHACHHANGRow.NGAY_SN = this.chuyenStringToDate(this.thongtinkhachhang.ngaysinh);
            hd_KHACHHANGRow.NGAYCAP = this.chuyenStringToDate(this.thongtinkhachhang.ngaycapgt);
            hd_KHACHHANGRow.SO_GT = this.thongtinkhachhang.sogt;
            hd_KHACHHANGRow.NOICAP = this.thongtinkhachhang.dsnoicapgt.find(x => x.TENTAT_NEO == this.thongtinkhachhang.noicapgt) ? this.thongtinkhachhang.dsnoicapgt.find(x => x.TENTAT_NEO == this.thongtinkhachhang.noicapgt).TINH_ID_NEO : this.thongtinkhachhang.dsnoicapgt.find(x => x.TINH_ID_NEO == this.tt_nd.phanvung_id).TINH_ID_NEO;
            hd_KHACHHANGRow.DONVI_ID = this.tt_nd.donvi_id;
            hd_KHACHHANGRow.NHANVIEN_ID = this.tt_nd.nhanvien_id;
            hd_KHACHHANGRow.LOAIHD_ID = 100;
            hd_KHACHHANGRow.NGUOI_CN = this.tt_nd.nguoi_cn;
            hd_KHACHHANGRow.NGAY_CN = this.homnay;
			hd_KHACHHANGRow.MAY_CN = this.tt_nd.may_cn; 
			hd_KHACHHANGRow.IP_CN = this.tt_nd.ip_cn; 
			hd_KHACHHANGRow.NGAY_YC = this.thongtinhopdong.ngayyeucau ? this.chuyenStringToDate(this.thongtinhopdong.ngayyeucau) : this.homnay;
			hd_KHACHHANGRow.NGAYLAP_HD = this.thongtinhopdong.ngaylaphd ? this.chuyenStringToDate(this.thongtinhopdong.ngaylaphd) : this.homnay;
            let loaikh_id_erp = 0;
            if (this.thongtinkhachhang.loai_kh == 1) loaikh_id_erp = 3;
            else if (this.thongtinkhachhang.loai_kh == 2) loaikh_id_erp = 2;
            else loaikh_id_erp = 4;
            hd_KHACHHANGRow.LOAIKH_ID = loaikh_id_erp; // Phải bổ sung thêm tham số này chứ không là bị lỗi
            if (this.thongtinkhachhang.gioitinh && this.thongtinkhachhang.gioitinh.length > 0) hd_KHACHHANGRow.GIOITINH = this.thongtinkhachhang.gioitinh == 'male' ? 1 : 0; // Tự thêm chỗ này vì không có dòng này thì không có lưu thông tin giới tính
            this.dsHDKH.push(hd_KHACHHANGRow);
        },
        taoDuLieuHDTT: async function() {
            this.dsHDTT = [];
            let hd_THANHTOANRow = {};
            if (!this.hdtt_id || this.hdtt_id == 0) {
                await api.getKey(this.axios, {
                    "keyname": "HD_THANHTOAN"
                }).then(res => {
                    this.hdtt_id = res.data.data ? Number(res.data.data) : 0;
                });
            }
			hd_THANHTOANRow.HDTT_ID = this.hdtt_id;
			hd_THANHTOANRow.HDKH_ID = this.hdkh_id;
			if (this.thanhtoan_id > 0)
			{
				hd_THANHTOANRow.THANHTOAN_ID = this.thanhtoan_id;
			}
			hd_THANHTOANRow.TEN_TT = this.thongtinkhachhang.tenkhachhang;
			hd_THANHTOANRow.DIACHI_TT = this.thongtinkhachhang.diachi; // CommonFunction.StringToString(this.txtDiaChi.Text);
			hd_THANHTOANRow.DIACHI_BC = this.thongtinkhachhang.diachi; // CommonFunction.StringToString(this.txtDiaChi.Text);
			hd_THANHTOANRow.HTTT_ID = 1;
			hd_THANHTOANRow.DONVI_ID = this.tt_nd.donvi_id;
			if (this.tuyenthu_id != 0) {
				hd_THANHTOANRow.TUYENTHU_ID = this.tuyenthu_id;
			}
			hd_THANHTOANRow.NGUOI_CN = this.tt_nd.nguoi_cn;
			hd_THANHTOANRow.NGAY_CN = this.homnay;
			hd_THANHTOANRow.MAY_CN = this.tt_nd.may_cn;
			hd_THANHTOANRow.IP_CN = this.tt_nd.ip_cn;
			hd_THANHTOANRow.DIACHI_CT = this.thongtinkhachhang.diachi;
			this.dsHDTT.push(hd_THANHTOANRow);
        },
		taoDuLieuHDTB: async function() {
            this.dsHDTB = [];
            let hd_THUEBAORow = {};
            if (!this.hdtb_id || this.hdtb_id == 0) {
                await api.getKey(this.axios, {
                    "keyname": "HD_THUEBAO"
                }).then(res => {
                    this.hdtb_id = res.data.data ? Number(res.data.data) : 0;
                });
                hd_THUEBAORow.HDTT_ID = this.hdtt_id;
            }
            hd_THUEBAORow.HDTB_ID = this.hdtb_id;
			hd_THUEBAORow.HDKH_ID = this.hdkh_id;
            if (this.tinhkhac == 0)
			{
				if (this.thuebao_id > 0)
				{
					hd_THUEBAORow.THUEBAO_ID = this.thuebao_id;
				}
			}
            hd_THUEBAORow.MA_TB = this.thongtinkhachhang.somay;
			hd_THUEBAORow.TEN_TB = this.thongtinkhachhang.tenkhachhang; // CommonFunction.StringToString(this.txtTenKH.Text)
			hd_THUEBAORow.DIACHI_LD = this.thongtinkhachhang.diachi; // CommonFunction.StringToString(this.txtTenKH.Text)
			hd_THUEBAORow.DIACHI_TB = this.thongtinkhachhang.diachi; // CommonFunction.StringToString(this.txtTenKH.Text)
			hd_THUEBAORow.GHICHU = this.thongtinkhachhang.ghichu;
			hd_THUEBAORow.KIEULD_ID = 75;
			hd_THUEBAORow.DOITUONG_ID = this.doituong_id;
			hd_THUEBAORow.TTHD_ID = 1;
			hd_THUEBAORow.LOAITB_ID = this.loaitb_id;
			hd_THUEBAORow.DICHVUVT_ID = this.dichvuvt_id;
			hd_THUEBAORow.DONVI_ID = this.tt_nd.donvi_id;
			hd_THUEBAORow.NGUOI_CN = this.tt_nd.nguoi_cn;
			hd_THUEBAORow.NGAY_CN = this.homnay;
			hd_THUEBAORow.MAY_CN = this.tt_nd.may_cn;
			hd_THUEBAORow.IP_CN = this.tt_nd.ip_cn;
			hd_THUEBAORow.STATUS = "0";
            this.dsHDTB.push(hd_THUEBAORow);
        },
		taoDuLieuHDTBDD: async function() {
            this.dsHDTB_DD = [];
            let hdtb_DDRow = {};
            hdtb_DDRow.HDTB_ID = this.hdtb_id;
			hdtb_DDRow.GOICUOC_ID = 21;
            if (this.thuebao_id > 0) {
                await api.getDsHDTBDdTheoTBId(this.axios, {
                    "in_thuebao_id": this.thuebao_id
                }).then(res => {
                    hdtb_DDRow.THUEBAO_D = res.data.data[0].thuebao_id;
                    hdtb_DDRow.SIMCARD = res.data.data[0].simcard;
                    hdtb_DDRow.GHICHU = res.data.data[0].ghichu;
                    hdtb_DDRow.SODU_TK = res.data.data[0].sodu_tk;
                    hdtb_DDRow.GOICUOC_ID = res.data.data[0].goicuoc_id;
                    hdtb_DDRow.GOICUOC_ID_NEO = res.data.data[0].goicuoc_id_neo;
                    hdtb_DDRow.DOITUONG_ID_NEO = res.data.data[0].doituong_id_neo;
                    hdtb_DDRow.TRANGBI_ID = res.data.data[0].trangbi_id;
                    hdtb_DDRow.TENGOICUOC = res.data.data[0].tengoicuoc;
                }).catch(err => {
                    //console.log("api.getDsHDTBDdTheoTBId with in_thuebao_id=" + this.thuebao_id + ":" + err.data.message);
                });
            } else {
                await api.getDsHDTBDdTheoHDTBId(this.axios, {
                    "in_hdtb_id": this.hdtb_id
                }).then(res => {
                    if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                        hdtb_DDRow.PHANVUNG_ID = res.data.data[0].phanvung_id;
                        hdtb_DDRow.HDTB_ID = res.data.data[0].hdtb_id;
                        hdtb_DDRow.SIMCARD = res.data.data[0].simcard;
                        hdtb_DDRow.GHICHU = res.data.data[0].ghichu;
                        hdtb_DDRow.TIEN_DTK = res.data.data[0].tien_dtk;
                        hdtb_DDRow.GOICUOC_ID = res.data.data[0].goicuoc_id;
                        hdtb_DDRow.DOITUONG_ID_NEO = res.data.data[0].doituong_id_neo;
                        hdtb_DDRow.SOMAY_NTK = res.data.data[0].somay_ntk;
                        hdtb_DDRow.MA_GD_NEO = res.data.data[0].ma_gd_neo;
                        hdtb_DDRow.KIEU_TL = res.data.data[0].kieu_tl;
                        hdtb_DDRow.TRANGBI_ID = res.data.data[0].trangbi_id;
                        hdtb_DDRow.DATCOC_CS = res.data.data[0].datcoc_cs;
                        hdtb_DDRow.CHUQUAN_ID = res.data.data[0].chuquan_id;
                        hdtb_DDRow.TINHKHAC = res.data.data[0].tinhkhac;
                        hdtb_DDRow.DOITUONGSD_ID_NEO = res.data.data[0].doituongsd_id_neo;
                        hdtb_DDRow.LOAISV_ID_NEO = res.data.data[0].loaisv_id_neo;
                        hdtb_DDRow.TRUONGHOC_ID_NEO = res.data.data[0].truonghoc_id_neo;
                        hdtb_DDRow.MA_VNPT = res.data.data[0].ma_vnpt;
                        hdtb_DDRow.MUCUOCTB_ID = res.data.data[0].mucuoctb_id;
                    }
                }).catch(err => {
                    //console.log("api.getDsHDTBDdTheoHDTBId with in_hdtb_id=" + this.hdtb_id + ":" + err.data.message);
                });
            }
            this.dsHDTB_DD.push(hdtb_DDRow);
        },
		taoDuLieuDiaChi: async function(kieu) {
            this.dsDiaChi = [];
            this.dsDiaChiHDKH = [];
            this.dsDiaChiHDTT = [];
            this.dsDiaChiHDTB = [];
            
            if (kieu == 1 || kieu == 4) {
                let diachirow = {};
                if (!this.diachikh_id || this.diachikh_id == 0) {
                    await api.getKey(this.axios, {
                        "keyname": "DIACHI"
                    }).then(res => {
                        this.diachikh_id = res.data.data ? Number(res.data.data) : 0;
                    });
                }
                diachirow.DIACHI_ID = this.diachikh_id;
                diachirow.TINH_ID = this.tinhkh_id;
                diachirow.QUAN_ID = this.quankh_id;
                diachirow.PHUONG_ID = this.phuongkh_id;
                if (this.phokh_id != 0) {
                    diachirow.PHO_ID = this.phokh_id;
                }
                if (this.apkh_id != 0) {
                    diachirow.AP_ID = this.apkh_id;
                }
                if (this.khukh_id != 0) {
                    diachirow.KHU_ID = this.khukh_id;
                }
                if (this.dacdiemkh_id != 0) {
                    diachirow.DACDIEM_ID = this.dacdiemkh_id;
                }
                diachirow.SONHA = this.sonhakh; // CommonFunction.StringToString(this.sonhakh);
                diachirow.DIACHI = this.thongtinkhachhang.diachi; // CommonFunction.StringToString(this.txtDiaChi.Text);
                this.dsDiaChi.push(diachirow);
                let diachi_HDKHRow = {};
                diachi_HDKHRow.DIACHI_ID = this.diachikh_id;
				diachi_HDKHRow.HDKH_ID = this.hdkh_id;
                this.dsDiaChiHDKH.push(diachi_HDKHRow);
            }
            if (kieu == 2 || kieu == 4) {
                let diachirow2 = {};
                if (!this.diachitt_id || this.diachitt_id == 0) {
                    await api.getKey(this.axios, {
                        "keyname": "DIACHI"
                    }).then(res => {
                        this.diachitt_id = res.data.data ? Number(res.data.data) : 0;
                    });
                }
                diachirow2.DIACHI_ID = this.diachitt_id;
				diachirow2.TINH_ID = this.tinhtt_id;
				diachirow2.QUAN_ID = this.quantt_id;
				diachirow2.PHUONG_ID = this.phuongtt_id;
				if (this.phott_id != 0)
				{
					diachirow2.PHO_ID = this.phott_id;
				}
				if (this.aptt_id != 0)
				{
					diachirow2.AP_ID = this.aptt_id;
				}
				if (this.khutt_id != 0)
				{
					diachirow2.KHU_ID = this.khutt_id;
				}
				if (this.dacdiemtt_id != 0)
				{
					diachirow2.DACDIEM_ID = this.dacdiemtt_id;
				}
				diachirow2.SONHA = this.sonhatt; // CommonFunction.StringToString(this.sonhatt);
				diachirow2.DIACHI = this.thongtinkhachhang.diachi; // CommonFunction.StringToString(this.txtDiaChi.Text);
				this.dsDiaChi.push(diachirow2);
				let diachi_HDTTRow = {};
				diachi_HDTTRow.DIACHI_ID = this.diachitt_id;
				diachi_HDTTRow.DIACHIBC_ID = this.diachibc_id;
				diachi_HDTTRow.HDTT_ID = this.hdtt_id;
				this.dsDiaChiHDTT.push(diachi_HDTTRow);
            }
            if (kieu == 3 || kieu == 4)
			{
				let diachirow4 = {};
				let diachirow5 = {};
				if (!this.diachitb_id || this.diachitb_id == 0)
				{
                    await api.getKey(this.axios, {
                        "keyname": "DIACHI"
                    }).then(res => {
                        this.diachitb_id = res.data.data ? Number(res.data.data) : 0;
                    });

                    await api.getKey(this.axios, {
                        "keyname": "DIACHI"
                    }).then(res => {
                        this.diachild_id = res.data.data ? Number(res.data.data) : 0;
                    });
				}
				diachirow5.DIACHI_ID = this.diachild_id;
				diachirow5.TINH_ID = this.tinhld_id;
				diachirow5.QUAN_ID = this.quanld_id;
				diachirow5.PHUONG_ID = this.phuongld_id;
				if (this.phold_id != 0)
				{
					diachirow5.PHO_ID = this.phold_id;
				}
				if (this.apld_id != 0)
				{
					diachirow5.AP_ID = this.apld_id;
				}
				if (this.khuld_id != 0)
				{
					diachirow5.KHU_ID = this.khuld_id;
				}
				if (this.dacdiemld_id != 0)
				{
					diachirow5.DACDIEM_ID = this.dacdiemld_id;
				}
				diachirow5.SONHA = this.sonhald; // CommonFunction.StringToString(this.sonhald);
				diachirow5.DIACHI = this.thongtinkhachhang.diachi; // CommonFunction.StringToString(this.txtDiaChi.Text);
				this.dsDiaChi.push(diachirow5);
				diachirow4.DIACHI_ID = this.diachitb_id;
				diachirow4.TINH_ID = this.tinhtb_id;
				diachirow4.QUAN_ID = this.quantb_id;
				diachirow4.PHUONG_ID = this.phuongtb_id;
				if (this.photb_id != 0)
				{
					diachirow4.PHO_ID = this.photb_id;
				}
				if (this.aptb_id != 0)
				{
					diachirow4.AP_ID = this.aptb_id;
				}
				if (this.khutb_id != 0)
				{
					diachirow4.KHU_ID = this.khutb_id;
				}
				if (this.dacdiemtb_id != 0)
				{
					diachirow4.DACDIEM_ID = this.dacdiemtb_id;
				}
				diachirow4.SONHA = this.sonhatb; // CommonFunction.StringToString(this.sonhatb);
				diachirow4.DIACHI = this.thongtinkhachhang.diachi; // CommonFunction.StringToString(this.txtDiaChi.Text);
				this.dsDiaChi.push(diachirow4);
				let diachi_HDTBRow = {};
				diachi_HDTBRow.DIACHI_ID = this.diachitb_id;
				diachi_HDTBRow.HDTB_ID = this.hdtb_id;
				diachi_HDTBRow.DIACHILD_ID = this.diachild_id;
				this.dsDiaChiHDTB.push(diachi_HDTBRow);
			}
        },
        themMoiHopDongDN: async function() {
            let self = this;
            await api.insertHopDongDoanhNghiep(this.axios, {
                "js_hd_khachhang": this.dsHDKH,
                "js_hd_thanhtoan": this.dsHDTT,
                "js_hd_thuebao": this.dsHDTB,
                "js_hdtb_dd": this.dsHDTB_DD,
                "js_diachi": this.dsDiaChi,
                "js_diachi_hdkh": this.dsDiaChiHDKH,
                "js_diachi_hdtb": this.dsDiaChiHDTB,
                "js_diachi_hdtt": this.dsDiaChiHDTT,
                "huonggiao_id": this.huonggiao_id,
                "tinhkhac": this.tinhkhac
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000') {
                    this.$toast.success('Ghi Lại Hợp Đồng Thành Công');
                    this.loadInterface(1);
                } else {
                    this.$toast.error('Có lỗi trong quá trình ghi lại: ' + res.data.message);
                    this.loadInterface(0);
                }
            }).catch(err => {
                self.$toast.error('Có lỗi trong quá trình ghi lại: ' + err.data.message_detail);
                self.loadInterface(0);
            });
        },
        capNhatHopDongDN: async function() {
            let self = this;
            await api.updateHopDongDoanhNghiep(this.axios, {
                "js_hd_khachhang": this.dsHDKH,
                "js_hd_thuebao": this.dsHDTB,
                "js_hdtb_dd": this.dsHDTB_DD,
                "js_diachi": this.dsDiaChi,
                "js_diachi_hdtb": this.dsDiaChiHDTB,
                "tinhkhac": this.tinhkhac
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000') {
                    this.$toast.success('Ghi Lại Hợp Đồng Thành Công');
                    this.loadInterface(1);
                } else {
                    this.$toast.error('Có lỗi trong quá trình ghi lại: ' + res.data.message);
                    this.loadInterface(0);
                }
            }).catch(err => {
                self.$toast.error('Có lỗi trong quá trình ghi lại: ' + err.data.message_detail);
                self.loadInterface(0);
            });
        },
        ghiLai: async function() {
            this.$v.$touch();
            if (this.$v.$invalid) {
                return;
            } else {
                // let isKHDN = false;
                // if (this.arrCCBS && this.arrCCBS.length > 0) isKHDN = true;
                // else isKHDN = false;

                // if (this.thongtinhopdong.loaihopdong == 'Nhập mới thông tin thuê bao Doanh nghiệp') {
                //     if (isKHDN) return;
                // }
                await this.taoDuLieuHDKH();
                if (this.loi_sinh_magd) {
                    this.$toast.error("Có lỗi khi sinh mã giao dịch");
				} else {
                    await this.taoDuLieuHDTT();
                    await this.taoDuLieuHDTB();
                    await this.taoDuLieuHDTBDD();
                    await this.taoDuLieuDiaChi(4);

                    if (this.hdtb_id && this.hdtb_id > 0) {
                        let kt_capnhat = false;
                        // Kiểm tra xem hợp đồng thuê bao này tạo chưa, 
                        // nếu tạo rồi thì cập nhật hợp đồng, ngược lại thì thêm mới hợp đồng
                        await api.getDsHDTBDdTheoHDTBId(this.axios, {
                            "in_hdtb_id": this.hdtb_id
                        }).then(res => {
                            if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                                kt_capnhat = true;
                            } else {
                                kt_capnhat = false;
                            }
                        }).catch(() => {
                            kt_capnhat = false;
                        });
                        if (!kt_capnhat) {
                            await this.themMoiHopDongDN();
                        } else {
                            await this.capNhatHopDongDN();
                        }
                    }
                }
            }
        },
        huy: async function() {
            if (!this.disabled_nut.nhapmoi) {
                await this.layThongTinTbKHDN();
            } else {
                this.clearData();
            }
        },
        openUploadImageForm(){
            this.$bvModal.show("frm-upload");
        },
        previewImage(event) {
            if (event.target.files[0]) {
                let output1 = document.getElementById('img_cmnd_truoc');
                output1.src = URL.createObjectURL(event.target.files[0]);
                output1.onload = function() {
                    URL.revokeObjectURL(output1.src) // free memory
                }   
            }
            
            if (event.target.files[1]) {
                let output2 = document.getElementById('img_cmnd_sau');
                output2.src = URL.createObjectURL(event.target.files[1]);
                output2.onload = function() {
                    URL.revokeObjectURL(output2.src) // free memory
                }
            }   

            if (event.target.files[2]) {
                let output3 = document.getElementById('img_nguoi_dk');
                output3.src = URL.createObjectURL(event.target.files[2]);
                output3.onload = function() {
                    URL.revokeObjectURL(output3.src) // free memory
                }
            }

            if (event.target.files[3]) {
                let output4 = document.getElementById('img_phieu_dk');
                output4.src = URL.createObjectURL(event.target.files[3]);
                output4.onload = function() {
                    URL.revokeObjectURL(output4.src) // free memory
                }
            }
        },
        uploadFile() {
            let formData = new FormData();
            let sohinh = 0;
            if (this.$refs.file.files.length > 0) {
                for(var file of this.$refs.file.files) {
                    formData.append('files', file);
                    sohinh++;
                }
            } else {
                if (this.$refs.file1.files.length > 0 && this.$refs.file2.files.length > 0) {
                    formData.append('files', this.$refs.file1.files[0]);
                    formData.append('files', this.$refs.file2.files[0]);
                    sohinh = 2;
                }
                else {
                    sohinh = 0;
                }
            }

            if (sohinh>=2) {
                let self = this;
                api.upload_file(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                    if(response.data.error_code === 'BSS-00000000') {
                        this.path_img1 = response.data.data[0] ? response.data.data[0] : "";
                        this.path_img2 = response.data.data[1] ? response.data.data[1] : "";
                        let ds_file = response.data.data.join(',');
                        api.getLinkFile(this.axios, {
                            "list_file": ds_file
                        }).then((res) => {
                            if (res.data.data && res.data.data.length >= 2) {
                                this.link_img1 = res.data.data[0].file_url;
                                this.link_img2 = res.data.data[1].file_url;
                                this.ten_img1 = "Mặt trước CMT";
                                this.ten_img2 = "Mặt sau CMT";
                                this.base64_img1 = "";
                                this.base64_img2 = "";
                            }
                            else {
                                this.link_img1 = '';
                                this.link_img2 = '';
                                this.ten_img1 = '';
                                this.ten_img2 = '';
                                this.base64_img1 = "";
                                this.base64_img2 = "";
                            }
                            this.$bvModal.hide('frm-upload');
                        });
                    }
                }).catch(function() {
                    self.$toast.error('File upload failed!'); // Dùng this thì bị lỗi nên phải khai báo biến self để gán this
                });
            }
            
        },
        chonHinh(num) {
            let url = "";
            if (num == 1) {
                if (this.base64_img1) { 
                    var w = window.open("");
                    w.document.write('<img src=' + "data:image/png;base64," + this.base64_img1 + '>');
                } else {
                    url = this.link_img1;
                    window.open(url);
                }
            } else if (num == 2) {
                if (this.base64_img2) {
                    var w = window.open("");
                    w.document.write('<img src=' + "data:image/png;base64," + this.base64_img2 + '>');
                } else {
                    url = this.link_img2;
                    window.open(url);
                }
            }
        },
        hideModal() {
            this.$bvModal.hide('frm-upload');
        },
        openChonDiaChiForm(){
            this.$bvModal.show("formdiachi");
        },
        capNhatTheoDiaChiKH()
		{
			this.tinhkh_id = this.tinhtb_id;
			this.quankh_id = this.quantb_id;
			this.phuongkh_id = this.phuongtb_id;
			this.phokh_id = this.photb_id;
			this.apkh_id = this.aptb_id;
			this.khukh_id = this.khutb_id;
			this.dacdiemkh_id = this.dacdiemtb_id;
			this.sonhakh = this.sonhatb;
			this.tinhtt_id = this.tinhkh_id;
			this.quantt_id = this.quankh_id;
			this.phuongtt_id = this.phuongkh_id;
			this.phott_id = this.phokh_id;
			this.aptt_id = this.apkh_id;
			this.khutt_id = this.khukh_id;
			this.dacdiemtt_id = this.dacdiemkh_id;
			this.sonhatt = this.sonhakh;
			this.tinhct_id = this.tinhkh_id;
			this.quanct_id = this.quankh_id;
			this.phuongct_id = this.phuongkh_id;
			this.phoct_id = this.phokh_id;
			this.khuct_id = this.khukh_id;
			this.dacdiemct_id = this.dacdiemkh_id;
			this.sonhact = this.sonhakh;
			this.tinhld_id = this.tinhkh_id;
			this.quanld_id = this.quankh_id;
			this.phuongld_id = this.phuongkh_id;
			this.phold_id = this.phokh_id;
			this.apld_id = this.apkh_id;
			this.khuld_id = this.khukh_id;
			this.dacdiemld_id = this.dacdiemkh_id;
			this.sonhald = this.sonhakh;
		},
        updateDiaChi(diachi_tmp) {
            this.tinhtb_id = Number(diachi_tmp.tinh_id);
            this.quantb_id = Number(diachi_tmp.quan_id);
            this.phuongtb_id = Number(diachi_tmp.phuong_id);
            this.photb_id = Number(diachi_tmp.pho_id);
            this.dacdiemtb_id = Number(diachi_tmp.dacdiem_id);
            this.aptb_id = Number(diachi_tmp.ap_id);
            this.khutb_id = Number(diachi_tmp.khu_id);
            this.sonhatb = diachi_tmp.sonha;
            this.thongtinkhachhang.diachi = diachi_tmp.diachimoi;

            this.tinhtt_id = Number(diachi_tmp.tinh_id);
            this.quantt_id = Number(diachi_tmp.quan_id);
            this.phuongtt_id = Number(diachi_tmp.phuong_id);
            this.phott_id = Number(diachi_tmp.pho_id);
            this.dacdiemtt_id = Number(diachi_tmp.dacdiem_id);
            this.aptt_id = Number(diachi_tmp.ap_id);
            this.khutt_id = Number(diachi_tmp.khu_id);
            this.sonhatt = diachi_tmp.sonha;

            this.tinhkh_id = Number(diachi_tmp.tinh_id);
            this.quankh_id = Number(diachi_tmp.quan_id);
            this.phuongkh_id = Number(diachi_tmp.phuong_id);
            this.phokh_id = Number(diachi_tmp.pho_id);
            this.dacdiemkh_id = Number(diachi_tmp.dacdiem_id);
            this.apkh_id = Number(diachi_tmp.ap_id);
            this.khukh_id = Number(diachi_tmp.khu_id);
            this.sonhakh = diachi_tmp.sonha;

            this.tinhld_id = Number(diachi_tmp.tinh_id);
            this.quanld_id = Number(diachi_tmp.quan_id);
            this.phuongld_id = Number(diachi_tmp.phuong_id);
            this.phold_id = Number(diachi_tmp.pho_id);
            this.dacdiemld_id = Number(diachi_tmp.dacdiem_id);
            this.apld_id = Number(diachi_tmp.ap_id);
            this.khuld_id = Number(diachi_tmp.khu_id);
            this.sonhald = diachi_tmp.sonha;
            //console.log("diachi_tmp=" + JSON.stringify(diachi_tmp));
        },
        layMaXacThuc() {
            let self = this;
            api.getMaXT(this.axios, {
                "so_tb": "84" + this.thongtinkhachhang.somay
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000' && res.data.data.toString().includes('Thanh cong')) {
                    this.$toast.success("Đã gửi mã xác nhận đến thuê bao: 84" + this.thongtinkhachhang.somay);
                } else {
                    let str = res.data.message.replace('$newline$','\n');
                    this.$toast.error(str);
                }
            }).catch(err => {
                let str = err.data.message.replace('$newline$','\n');
                self.$toast.error(str);
            });
        },
        capNhatHDTB: async function() {
            await api.updateHDTB(this.axios, {
                "p_hdtb_id": this.hdtb_id
            }).then(res => {
                //console.log("capNhatHDTB = " + JSON.stringify(res));
            });
        },
        hoanThienHDTB() {
            let self = this;
            api.completeHDTB(this.axios, {
                "vhdtb_id": this.hdtb_id
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000') {
                    this.$toast.success('Hoàn công nhập mới thông tin TB doanh nghiệp thành công');
                } else {
                    let str = res.data.message.replace('$newline$','\n');
                    this.$toast.error(str);
                }
            }).catch(err => {
                let str = err.data.message.replace('$newline$','\n');
                self.$toast.error(str);
            });
        },
        hoanThienHSSV() {
            let self = this;
            api.completeHSSV(this.axios, {
                "vhdtb_id": this.hdtb_id
            }).then(res => {
                if (res.data.error_code === 'BSS-00000000') {
                    this.$toast.success('Cập nhập thông tin TB doanh nghiệp thành công');
                } else {
                    let str = res.data.message.replace('$newline$','\n');
                    this.$toast.error(str);
                }
            }).catch(err => {
                let str = err.data.message.replace('$newline$','\n');
                self.$toast.error(str);
            });
        },
        dangKyCCBS: async function() {
            if (this.ma_nguoidd === '' && this.ma_khdn === '') {
                this.$toast.error("Thuê bao 84" + this.thongtinkhachhang.somay + " thiếu thông tin mã khdn và mã người đại diện nên không thể cập nhật thông tin thuê bao KHDN trên CCBS!");
            } else {
                // Cập nhật thông tin thuê bao khdn CCBS
                await api.updateTT_TB_KHDN(this.axios, {
                    "dia_chi": this.thongtinkhachhang.diachi,
                    "diachi_coquan": this.thongtinkhachhang.diachi,
                    "diachi_pgd": this.thongtinkhachhang.diachi,
                    "dienthoai_pgd": this.thongtinkhachhang.dssomay,
                    "doituongsd": "",
                    "email": this.thongtinkhachhang.email,
                    "ghi_chu": this.thongtinkhachhang.ghichu,
                    "ghichu": this.thongtinkhachhang.ghichu,
                    "gioi_tinh": this.thongtinkhachhang.gioitinh,
                    "ho_chieu": "",
                    "loai_gt": this.thongtinkhachhang.loaigiayto,
                    "loai_kh": this.thongtinkhachhang.loaikh,
                    "ma_khdn": this.ma_khdn,
                    "ma_nguoidd": this.ma_nguoidd,
                    "ma_nuoc": this.thongtinkhachhang.nuoc,
                    "ma_xac_thuc": this.thongtinkhachhang.maxacthuc,
                    "nganhghe": this.thongtinkhachhang.nghenghiep,
                    "ngay_cap_gt": this.thongtinkhachhang.ngaycapgt,
                    "ngay_sinh": this.thongtinkhachhang.ngaysinh,
                    "ngay_visa_hh": this.thongtinkhachhang.ngaycapgiaykd,
                    "noi_cap": this.thongtinkhachhang.noicapgt,
                    "so_cmt": this.thongtinkhachhang.sogt,
                    "so_tb": this.thongtinkhachhang.dssomay,
                    "so_visa": "",
                    "ten_coquan": this.thongtinkhachhang.congty,
                    "ten_kh": this.thongtinkhachhang.tenkhachhang
                }).then(res => {
                    if (res.data.error_code === 'BSS-00000000' && res.data.data) {
                        if (this.path_img1 && this.path_img2) {
                            // hàm thêm/cập nhật thông tin ảnh
                            api.uploadImgTTTBKHDN(this.axios, {
                                "gt_img_1": this.path_img1,
                                "gt_img_2": this.path_img2,
                                "img_type_1": "5",
                                "img_type_2": "6",
                                "so_tb": this.thongtinkhachhang.dssomay
                            }).then(res => {
                                //console.log("uploadImgTTKHDN=" + JSON.stringify(res));
                            });
                        }
                        this.$toast.success('Cập nhật thông tin thuê bao trả trước khách hàng doanh nghiệp thành công');
                    } else {
                        this.$toast.error(res.data.message);
                    }
                }).catch(err => {
                    self.$toast.error(err.data.message);
                });
            }
        },
        hoanThien: async function() {
            this.$v.$touch();
            if (this.$v.$invalid) {
                return;
            } else {
                await this.dangKyCCBS();
                await this.capNhatHDTB();
                if (this.thongtinhopdong.loaihopdong == 'Nhập mới thông tin thuê bao Doanh nghiệp') {
                    this.hoanThienHDTB();
                } else {
                    this.hoanThienHSSV();
                }
            }
        },
        clickImage(num) {
            if (num == 1) {
                this.$refs.file1.click();
            } else if (num == 2) {
                this.$refs.file2.click();
            } else if (num == 3) {
                this.$refs.file3.click();
            } else if (num == 4) {
                this.$refs.file4.click();
            }
        },
        showImage(event, num) {
            if (event.target.files[0]) {
                let str = '';
                if (num == 1) {
                    str = 'img_cmnd_truoc';
                } else if (num == 2) {
                    str = 'img_cmnd_sau';
                } else if (num == 3) {
                    str = 'img_nguoi_dk';
                } else if (num == 4) {
                    str = 'img_phieu_dk';
                }
                let output = document.getElementById(str);
                output.src = URL.createObjectURL(event.target.files[0]);
                output.onload = function() {
                    URL.revokeObjectURL(output.src) // free memory
                }   
            }
        },
        acceptSearchContract(item) {
            this.thongtinhopdong.magiaodich = item.ma_gd
            this.$refs.ma_gd.focus()
        },
        showPopupSearchContract () {
            this.$refs.popupSearchContract.showModal()
        }
    }
}
</script>