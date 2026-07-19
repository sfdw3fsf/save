<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a class="hand_click" @click.prevent="nhapMoi" :class="{'disabled': disabled_control.btn_nhapmoi}">
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li>
                    <a class="hand_click" @click.prevent="ghiLai" :class="{'disabled': disabled_control.btn_ghilai}">
                        <span class="icon one-save"></span>Cập nhật
                    </a>
                </li>
                <li>
                    <a class="hand_click" @click.prevent="huyBo" :class="{'disabled': disabled_control.btn_huybo}">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy bỏ
                    </a>
                </li>
                <li>
                    <a class="hand_click" @click.prevent="xoa" :class="{'disabled': disabled_control.btn_xoa}">
                        <span class="icon one-trash"></span>Xóa HĐ
                    </a>
                </li>
                <li>
                    <a class="hand_click" @click.prevent="themTB" :class="{'disabled': disabled_control.btn_themtb}" v-if="visibled_control.btn_themtb">
                        <span class="icon one-mobile-plus"></span>Thêm TB
                    </a>
                </li>
                <li>
                    <a class="hand_click" @click.prevent="xoaTB" :class="{'disabled': disabled_control.btn_xoatb}">
                        <span class="icon one-phone-remove"></span>Xóa TB
                    </a>
                </li>
                <li>
                    <a class="hand_click" @click.prevent="themDSThueBao">
                        <span class="icon one-file-plus-1"></span>Thêm DS
                    </a>
                </li>
                <li>
                    <a class="hand_click" @click.prevent="thanhToan">
                        <span class="icon nc-icon-glyph shopping_credit-card"></span>Thanh toán
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="box-form padt8 padb8">
                <div class="row">
                    <div class="col-sm-10 col-12">
                        
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row">
                            <div class="key w30 nowrap">Tổng tiền</div>
                            <div class="value">
                                <input type="text" class="form-control fw6 tright" v-model="thongso_ims.tongtien">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Thông tin phiếu yêu cầu</div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">Mã giao dịch</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn" @click.prevent="openFrmTraCuuHopDong">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                            <input type="text" @keyup.enter="maGD_KeyPress" class="form-control highlight" ref="ma_gd" v-model="$v.thongso_ims.ma_gd.$model" :class="{'is-invalid': $v.thongso_ims.ma_gd.$error}">
                                        </div>
                                        <div class="error" v-if="!$v.thongso_ims.ma_gd.maxLength">Vượt quá tối đa {{ $v.thongso_ims.ma_gd.$params.maxLength.max }} ký tự</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key">Ngày yêu cầu</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <vue-date style="margin-left:-5px" format="DD/MM/YYYY" type="date" v-model="thongso_ims.ngay_yc"></vue-date>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">Mã hợp đồng</div>
                                    <div class="value">
                                        <input type="text" class="form-control" ref="ma_hd" v-model="$v.thongso_ims.ma_hd.$model" :class="{'is-invalid': $v.thongso_ims.ma_hd.$error}">
                                        <div class="error" v-if="!$v.thongso_ims.ma_hd.maxLength">Vượt quá tối đa {{ $v.thongso_ims.ma_hd.$params.maxLength.max }} ký tự</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key">Ngày lập HĐ</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <vue-date style="margin-left:-5px" format="DD/MM/YYYY" type="date" v-model="thongso_ims.ngaylap_hd"></vue-date>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Mã khách hàng</div>
                                    <div class="value">
                                        <input type="text" ref="ma_kh" class="form-control" v-model="$v.thongso_ims.ma_kh.$model" :class="{'is-invalid': $v.thongso_ims.ma_kh.$error}">
                                        <div class="error" v-if="!$v.thongso_ims.ma_kh.maxLength">Vượt quá tối đa {{ $v.thongso_ims.ma_kh.$params.maxLength.max }} ký tự</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tên KH</div>
                                    <div class="value">
                                        <input type="text" ref="ten_kh" class="form-control" v-model="$v.thongso_ims.ten_kh.$model" :class="{'is-invalid': $v.thongso_ims.ten_kh.$error}">
                                        <div class="error" v-if="!$v.thongso_ims.ten_kh.maxLength">Vượt quá tối đa {{ $v.thongso_ims.ten_kh.$params.maxLength.max }} ký tự</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Địa chỉ KH</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="$v.thongso_ims.diachi_kh.$model" :class="{'is-invalid': $v.thongso_ims.diachi_kh.$error}">
                                <div class="error" v-if="!$v.thongso_ims.diachi_kh.maxLength">Vượt quá tối đa {{ $v.thongso_ims.diachi_kh.$params.maxLength.max }} ký tự</div>
                            </div>
                        </div>
                
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Thông tin thuê bao</div>
                
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="key">Account</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click.prevent="openFrmTraCuuDanhBa">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" @keyup.enter="maTB_KeyPress" class="form-control highlight" ref="ma_tb" v-model="$v.thongso_ims.ma_tb.$model" :class="{'is-invalid': $v.thongso_ims.ma_tb.$error}">
                                </div>
                                <div class="error" v-if="!$v.thongso_ims.ma_tb.maxLength">Vượt quá tối đa {{ $v.thongso_ims.ma_tb.$params.maxLength.max }} ký tự</div>
                            </div>
                            <div class="value w20">
                                <button class="btn" @click.prevent="openFrmDSTB">
                                    <span class="icon ui-1_circle-add nc-icon-glyph"></span>
                                </button>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Tên thuê bao</div>
                            <div class="value">
                                <input type="text" ref="ten_tb" class="form-control" v-model="$v.thongso_ims.ten_tb.$model" :class="{'is-invalid': $v.thongso_ims.ten_tb.$error}">
                                <div class="error" v-if="!$v.thongso_ims.ten_tb.maxLength">Vượt quá tối đa {{ $v.thongso_ims.ten_tb.$params.maxLength.max }} ký tự</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">Chi tiết DV mới</div>
                                    <div class="value">
                                        <div class="select-custom">
                                                <SelectExt v-model="thongso_ims.ctdv_id_moi" :allowFiltering="true" dataTextField="chitiet_dv"
                                                dataValueField="ctdv_id" :dataSource="thongso_ims.ds_ctdv_moi" @change="thayDoiCTDVMoi">
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w120">Chi tiết DV cũ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                                <SelectExt v-model="thongso_ims.ctdv_id_cu" :allowFiltering="true" dataTextField="chitiet_dv"
                                                dataValueField="ctdv_id" :dataSource="thongso_ims.ds_ctdv_cu">
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">Thiết bị IMS mới</div>
                                    <div class="value">
                                        <!-- <div class="select-custom">
                                                <SelectExt v-model="thongso_ims.thietbi_id_moi" :allowFiltering="true" dataTextField="ten_tbi"
                                                dataValueField="thietbi_id" :dataSource="thongso_ims.ds_tbi_moi">
                                            </SelectExt>
                                        </div> -->
                                        <select2 v-model="thongso_ims.thietbi_id_moi" :options="thongso_ims.ds_tbi_moi"></select2>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w120">Thiết bị IMS cũ</div>
                                    <div class="value">
                                        <select2 v-model="thongso_ims.thietbi_id_cu" :options="thongso_ims.ds_tbi_cu"></select2>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">Cuộc gọi Đ.Thời mới</div>
                                    <div class="value">
                                        <input type="number" @keypress="validateNumber" ref="sl_cuocgoi_moi" class="form-control highlight" v-model="$v.thongso_ims.cuocgoi_dt_moi.$model" :class="{'disabled': disabled_control.txt_soluong_moi, 'is-invalid': $v.thongso_ims.cuocgoi_dt_moi.$error}">
                                        <div class="error" v-if="!$v.thongso_ims.cuocgoi_dt_moi.maxLength">Vượt quá tối đa {{ $v.thongso_ims.cuocgoi_dt_moi.$params.maxLength.max }} ký tự</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w120">Cuộc gọi Đ.Thời cũ</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongso_ims.cuocgoi_dt_cu" :class="{'disabled': disabled_control.txt_soluong_cu}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">IP mới</div>
                                    <div class="value">
                                        <input type="text" ref="ip_moi" class="form-control highlight" v-model="$v.thongso_ims.ip_moi.$model" :class="{'disabled': disabled_control.txt_ip_moi, 'is-invalid': $v.thongso_ims.ip_moi.$error}">
                                        <div class="error" v-if="!$v.thongso_ims.ip_moi.maxLength">Vượt quá tối đa {{ $v.thongso_ims.ip_moi.$params.maxLength.max }} ký tự</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w120">IP cũ</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongso_ims.ip_cu" :class="{'disabled': disabled_control.txt_ip_cu}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">Ngưỡng CSD mới</div>
                                    <div class="value">
                                        <input type="number" @keypress="validateNumber" ref="nguong_csd_moi" class="form-control highlight" v-model="$v.thongso_ims.nguong_csd_moi.$model" :class="{'disabled': disabled_control.txt_nguongcsd_moi, 'is-invalid': $v.thongso_ims.nguong_csd_moi.$error}">
                                        <div class="error" v-if="!$v.thongso_ims.nguong_csd_moi.maxLength">Vượt quá tối đa {{ $v.thongso_ims.nguong_csd_moi.$params.maxLength.max }} ký tự</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w120">Ngưỡng CSD cũ</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongso_ims.nguong_csd_cu" :class="{'disabled': disabled_control.txt_nguongcsd_cu}">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Dịch vụ VT</div>
                                    <div class="value">
                                        <div class="select-custom">
                                                <SelectExt v-model="thongso_ims.dichvuvt_id" :allowFiltering="true" dataTextField="ten_dvvt"
                                                dataValueField="dichvuvt_id" :dataSource="thongso_ims.ds_dichvuvt" @change="thayDoiDichVu">
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại TB</div>
                                    <div class="value">
                                        <div class="select-custom">
                                                <SelectExt v-model="thongso_ims.loaitb_id" :allowFiltering="true" dataTextField="LOAIHINH_TB"
                                                dataValueField="LOAITB_ID" :dataSource="thongso_ims.ds_loaitb" @change="thayDoiLoaiTB">
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-12">
                                <div class="info-row">
                                    <div class="key">Kiểu yêu cầu</div>
                                    <div class="value">
                                        <div class="select-custom">
                                                <SelectExt v-model="thongso_ims.kieuld_id" :allowFiltering="true" dataTextField="ten_kieuld" @change="thayDoiKieuLD"
                                                dataValueField="kieuld_id" :dataSource="thongso_ims.ds_kieuld">
                                            </SelectExt>
                                        </div>
                                    </div>
                                    <div class="value w50">
                                        <button class="btn" style="border-width: 1px" @click.prevent="openFrmThongSo">
                                            <span class="icon ui-1_circle-add nc-icon-glyph"></span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Địa chỉ LĐ</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="$v.thongso_ims.diachi_ld.$model" v-bind:class="{'is-invalid': $v.thongso_ims.diachi_ld.$error}">
                                <div class="error" v-if="!$v.thongso_ims.diachi_ld.maxLength">Vượt quá tối đa {{ $v.thongso_ims.diachi_ld.$params.maxLength.max }} ký tự</div>
                            </div>
                        </div>
                        <div class="legend-title mart20">Chi tiết tiền theo khoản mục</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Tiền</div>
                                    <div class="value">
                                        <input @keypress="validateNumber" type="number" class="form-control tright" v-model="thongso_ims.tien">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Vat</div>
                                    <div class="value">
                                        <input @keypress="validateNumber" type="number" class="form-control tright" v-model="thongso_ims.vat">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-12">
                                <div class="info-row">
                                    <div class="key">Ghi chú</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="$v.thongso_ims.ghichu.$model" v-bind:class="{'is-invalid': $v.thongso_ims.ghichu.$error}">
                                        <div class="error" v-if="!$v.thongso_ims.ghichu.maxLength">Vượt quá tối đa {{ $v.thongso_ims.ghichu.$params.maxLength.max }} ký tự</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách thuê bao</div>
                <DataGrid v-bind:columns="ds_thuebao.headers" v-bind:dataSource="ds_thuebao.data"
                    :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true"
                    dataKeyField="thuebao_id" :showFilter="true"
                    @rowSelected="rowSelected_TB" >
                </DataGrid>
            </div>
        </div>
        <SearchContractModal :loai_hd_id="frm_hopdong.loai_hd_id" :dichvu_id="frm_hopdong.dichvu_id" @accept="chonHopDong" ref="popupSearchContract"/>

        <SearchSubscriber :khachhang_id="khachhang_id" :dichvuvt_id="thongso_ims.dichvuvt_id" :loaihd_id="frm_hopdong.loai_hd_id" :ma_gd="thongso_ims.ma_gd" :ma_tb="thongso_ims.ma_tb" ref="popupSearchSubscriber" @accept="chonThueBao"/>

        <!-- <FrmThemDSThuebaoTDTSIMS :khachhang_id="khachhang_id" :dsdichvuvt_id="frm_themdstb_tdts_ims.dsdichvuvt_id" :dsloaihd_id="frm_themdstb_tdts_ims.dsloaihd_id" :hdkh_id="hdkh_id" ref="popupThemDSThueBao_TDTS_IMS"/> -->

        <b-modal id="popupComponentMGs" size="xl" hide-footer hide-header hide-header-close body-class="modal-body p-0">
            <component :is="popupComponent" ref="generalPopup" v-bind="popupComponentAttr" isPopup="true"
            v-on="popupComponentEvts"></component>
        </b-modal>

        <ejs-dialog :enableResize='false' :visible='false' ref='thanhtoanDialog' :position='position' :header='"Thanh toán"'
            showCloseIcon='true' :target='targetTop' width='100%'>
            <keep-alive>
                <div class="popupComponentPayment">
                    <component :is="userComponentModule"  ref='thanhToanPopUp' :ispopup="true" :magiaodich='txtMaGDTT' v-bind="popupComponentAttr" v-on="popupComponentEvts" />
                </div>
            </keep-alive>
        </ejs-dialog>

        <ThongSoCoDinhModal ref="thongSoCoDinhModal"
            :khachhang_id="Number(khachhang_tc_id)"
            :hdkh_id="Number(hdkh_id)"
            :thongso_ban="thongso_ban"
            :thongso_tc="thongso_tc"
            @accept="acceptThongSoCoDinh"
        />
    </div>
</template>
<style scoped  src='./ChangeIMSInfor.scss'></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from './api'
import ChangeSubsTypeCrossSaleAPI from '@/modules/contract/setup/ChangeSubsTypeCrossSale/ChangeSubsTypeCrossSaleAPI.js'
// import SearchContractModal from '../../../contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchSubscriber from './popup/SearchSubscriber'
//import FrmThemDSThuebaoTDTSIMS from './popup/FrmThemDSThuebaoTDTSIMS'
import moment from 'moment'
import { DichVuVienThong, KieuLapDat, LoaiHopDong } from '@/modules/contract/setup/InstallNewSubs/Enums.js'
import { maxLength } from 'vuelidate/lib/validators'
const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')
const ThongSoCoDinhModal= () => import('@/modules/contract/setup/ChangeSubsTypeCrossSale/popups/ThongSoCoDinhModal.vue')
export default {
    data() {
        return {            
            header: {
                title: "Thay đổi Thông số IMS",
                list: [
                    {
                        name: 'Thay đổi Thông số IMS',
                        link: { 
                            name: 'Ui.buttons',
                            active: true 
                        }
                    }
                ],
            },     
            // Bán chéo
            BANCHEO: false,
            tinh: {},
            thongso_ban: {},
            thongso_tc: {},
            thuebao_id_tc: 0,
            khachhang_tc_id: 0,   
            quytrinh_tc_tn: 0,    
            huonggiao_tc_tn: 0,     
            dsDBTBBC: [],
            dsHDBC: [],
            dsHDTBBC: [],
            dsHDBCMGWAN: [],
            dsHDBCADSL: [],
            dsHDBCCD: [],
            dsHDBCTSL: [],
            dsHDBCIMS: [],
            dsDK_DVGT_BC: [],
            // 
            thongso_ims: {
                tongtien: 0,
                ma_gd: '',
                ma_hd: '',
                ngay_yc: '',
                ngaylap_hd: '',
                ma_kh: '',
                ten_kh: '',
                diachi_kh: '',
                ma_tb: '',
                ten_tb: '',
                dichvuvt_id: 0,
                ds_dichvuvt: [],
                loaitb_id: 0,
                ds_loaitb: [],
                kieuld_id: 0,
                ds_kieuld: [],
                diachi_ld: '',
                ctdv_id_moi: 0,
                ds_ctdv_moi: [],
                ctdv_id_cu: 0,
                ds_ctdv_cu: [],
                thietbi_id_moi: 0,
                ds_tbi_moi: [],
                thietbi_id_cu: 0,
                ds_tbi_cu: [],
                cuocgoi_dt_moi: '',
                cuocgoi_dt_cu: '',
                ip_moi: '',
                ip_cu: '',
                nguong_csd_moi: '',
                nguong_csd_cu: '',
                tien: 0,
                vat: 0,
                ghichu: ''
            },
            ds_thuebao: {
                headers: [
                    { fieldName: 'ma_tb', headerText: 'Số máy', allowFiltering: true, allowSorting: false },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false },
                    { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_kieuld', headerText: 'Kiểu lắp đặt', allowFiltering: true, allowSorting: false },
                    { fieldName: 'sl_cuocgoi_cu', headerText: 'SL cuộc gọi cũ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'sl_cuocgoi_moi', headerText: 'SL cuộc gọi mới', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ip_cu', headerText: 'IP cũ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ip_moi', headerText: 'IP mới', allowFiltering: true, allowSorting: false },
                    { fieldName: 'nguong_csd_cu', headerText: 'Ngưỡng CSD cũ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'nguong_csd_moi', headerText: 'Ngưỡng CSD mới', allowFiltering: true, allowSorting: false }
                ],
                data: []
            },
            visibled_control: {
                btn_themtb: true,
            },
            disabled_control: {
                // Nút
                btn_nhapmoi: true,
                btn_ghilai: true,
                btn_xoa: true,
                btn_huybo: true,
                btn_themtb: true,
                btn_xoatb: true,
                // Textbox
                txt_ip_cu: true,
                txt_ip_moi: true,
                txt_soluong_cu: true,
                txt_soluong_moi: true,
                txt_nguongcsd_cu: false,
                txt_nguongcsd_moi: false,
            },
            position: { X: 'center', Y: 'top' },
            targetTop: '#app .main-wrapper',
            homnay: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear(),
            ts_sinhma_gd_theo_donvi: false,
            phuongld_id: 0,
            phold_id: 0,
            apld_id: 0,
            khuld_id: 0,
            hdkh_id: 0,
            khachhang_id: 0,
            hdtb_id: 0,
            thuebao_id: 0,
            diachi_tb: '',
            doituong_id: 1,
            trangbi_id: 3,
            ltb_adsl_id: 4,
            diachi_ld: '',
            huonggiaotn_id: 0,
            quytrinhtn_id: 0,
            donvi_id: 0,
            hdkh_cha_id: 0,
            userComponentModule: null,
            txtMaGDTT: '',
            popupComponent: null,
            popupComponentAttr: {},
            popupComponentEvts: {
                'form-close': this.popupClosed,
                paymentcreated: this.paymentcreated
            },
            paymentloaded: false,
            popupComponentName: '',
            // arr de insert/update
            dsHDTB_IMS: [],
            dsHDTBDV: [],
            dsHDTB: [],
            dsCTTHD: [],
            dsHDKH: [],
            ds_para: {},
            frm_hopdong: {
                loai_hd_id: 21,
                dichvu_id: 11
            },
            frm_danhba: {
                isPopup: true
            },
            frm_themdstb_tdts_ims: {
                dsdichvuvt_id: '(11)',
                dsloaihd_id: ''
            },
            tt_nd: {
                chitiet: this.$root.token.getThongTinNguoiDung(),
                nhanvien_id: Number(this.$root.token.getNhanVienID()),
                donvi_id: Number(this.$root.token.getDonViID()),
                donvi_dl_id: Number(this.$root.token.getDonViDuLieuID()),
                phanvung_id: Number(this.$root.token.getPhanVungID()),
                username: this.$root.token.getUserName(),
                may_cn: 'TEST',
                ip: '127.0.0.1'
            },
            kieuld: {
                thaydoi_tt_cuocgoi_ims: 134,
                thaydoi_tt_ip_ims: 133,
                thaydoi_tt_nguong_csd_ims: 135
            },
            khoanmuc_tt: {
                kmtt_doits: 8,
                kmtt_datmoi: 1
            }
        }
    },
    validations: 
    {
        thongso_ims: {
            ma_gd: { maxLength: maxLength(99) },
            ma_hd: { maxLength: maxLength(80) },
            ma_kh: { maxLength: maxLength(30) },
            ten_kh: { maxLength: maxLength(500) },
            diachi_kh: { maxLength: maxLength(1000) },
            ma_tb: { maxLength: maxLength(100) },
            ten_tb: { maxLength: maxLength(500) },
            diachi_ld: { maxLength: maxLength(1000) },
            cuocgoi_dt_moi: { maxLength: maxLength(5) },
            ip_moi: { maxLength: maxLength(50) },
            nguong_csd_moi: { maxLength: maxLength(10) },
            ghichu: { maxLength: maxLength(1500) }
        }
    },
    mounted() {
        this.setButton(-1);
        setTimeout(async () => {
            this.tt_nd.may_cn = await this.$root.token.getMachine();
            this.tt_nd.ip = await this.$root.token.getIP();
        }, 100)
        console.log('tt_nd', this.tt_nd)
    },
    created() {
        this.khoiTao();
    },
    components: {
        breadcrumb,
        SearchContractModal,
        SearchSubscriber,
        //FrmThemDSThuebaoTDTSIMS,
        ThongSoCoDinhModal,
    },
    methods: {
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
        isNumeric(str) {
            if (typeof str != "string") return false // we only process strings!  
            return !isNaN(str) && // use type coercion to parse the _entirety_ of the string (`parseFloat` alone does not do this)...
                !isNaN(parseFloat(str)) // ...and ensure strings of whitespace fail
        },
        khoiTao() {
            this.BANCHEO = false
            if (this.$route.params && this.$route.params.tag !== null && this.$route.params.tag !== undefined) {
                this.BANCHEO = true
            }
            if (this.$route.query && this.$route.query.tag !== null && this.$route.query.tag !== undefined) {
                this.BANCHEO = true
            }
            if (this.dulieu && this.dulieu.tag !== null && this.dulieu.tag !== undefined) {
                this.BANCHEO = true
            }
            if (this.BANCHEO) {
                this.header.title = 'Thay đổi Thông số IMS bán chéo'
                this.header.list[0].name = 'Thay đổi Thông số IMS bán chéo'
            }
            api.getDsThamSoMD(this.axios, {
                "p_kieu_id": 0
            }).then(res => {
                this.ts_sinhma_gd_theo_donvi = res.data.data && res.data.data.find(x => x.ma_ts == 'SINH_MA_GD_THEO_DV') ? true : false;
                //console.log("ts_sinhma_gd_theo_donvi=" + JSON.stringify(this.ts_sinhma_gd_theo_donvi));
            }).catch(() => {
                this.ts_sinhma_gd_theo_donvi = false;
            });
            this.thongso_ims.ngay_yc = this.homnay;
            api.getDsChiTietDvIMS(this.axios, {
                "kieu": 0
            }).then(res => {
                this.thongso_ims.ds_ctdv_moi = res.data.data ? res.data.data : [];
                this.thongso_ims.ctdv_id_moi = this.thongso_ims.ds_ctdv_moi.length > 0 ? this.thongso_ims.ds_ctdv_moi[0].ctdv_id : 0;
                this.thongso_ims.ds_ctdv_cu = res.data.data ? res.data.data : [];
                this.thongso_ims.ctdv_id_cu = this.thongso_ims.ds_ctdv_cu.length > 0 ? this.thongso_ims.ds_ctdv_cu[0].ctdv_id : 0;
            }).catch(() => {
                this.thongso_ims.ds_ctdv_moi = [];
                this.thongso_ims.ds_ctdv_cu = [];
                this.thongso_ims.ctdv_id_moi = 0;
                this.thongso_ims.ctdv_id_cu = 0;
            });
            api.getDsThietBiIMS(this.axios, {
                "p_schema": "css",
                "p_table_name": "thietbi_ims",
                "p_value": "thietbi_id",
                "p_text": "ten_tbi",
                "p_where": "",
                "p_order": "thietbi_id"
            }).then(res => {
                this.thongso_ims.ds_tbi_moi = [];
                this.thongso_ims.ds_tbi_cu = []

                if(res.data.data.length > 0){
                    this.thongso_ims.ds_tbi_moi = res.data.data.map((e) => ({
                        id: e.thietbi_id,
                        text: e.ten_tbi
                    }))
                    this.thongso_ims.ds_tbi_cu = this.thongso_ims.ds_tbi_moi
                    
                }
                this.thongso_ims.thietbi_id_moi = this.thongso_ims.ds_tbi_moi.length > 0 ? this.thongso_ims.ds_tbi_moi[0].id : 1;
                this.thongso_ims.thietbi_id_cu = this.thongso_ims.ds_tbi_cu.length > 0 ? this.thongso_ims.ds_tbi_cu[0].id : 1;
            }).catch(() => {
                this.thongso_ims.ds_tbi_moi = [];
                this.thongso_ims.ds_tbi_cu = [];
                this.thongso_ims.thietbi_id_moi = 0;
                this.thongso_ims.thietbi_id_cu = 0;
            });
            api.getDsDichVuVT(this.axios, {
                "CATEGORY_NAME": "",
                "CODE": "",
                "ID": this.frm_hopdong.dichvu_id,
                "NAME": ""
            }).then(res => {
                this.thongso_ims.ds_dichvuvt = res.data.data ? res.data.data : [];
                this.thongso_ims.dichvuvt_id = this.thongso_ims.ds_dichvuvt.length > 0 ? this.thongso_ims.ds_dichvuvt[0].dichvuvt_id : 0;
            }).catch(() => {
                this.thongso_ims.ds_dichvuvt = [];
                this.thongso_ims.dichvuvt_id = 0;
            });
            api.getDsLoaiTB(this.axios).then(res => {
                this.thongso_ims.ds_loaitb = res.data.data ? res.data.data.filter(x => x.DICHVUVT_ID == this.frm_hopdong.dichvu_id) : [];
                this.thongso_ims.loaitb_id = this.thongso_ims.ds_loaitb.length > 0 ? this.thongso_ims.ds_loaitb[0].LOAITB_ID : 0;
            }).catch(() => {
                this.thongso_ims.ds_loaitb = [];
                this.thongso_ims.loaitb_id = 0;
            });
            api.getDsKieuLD(this.axios, {
                "vloaihd_id": this.frm_hopdong.loai_hd_id,
                "vloaitb_id": null
            }).then(res => {
                this.thongso_ims.ds_kieuld = res.data.data ? res.data.data : [];
                this.thongso_ims.kieuld_id = this.thongso_ims.ds_kieuld.length > 0 ? this.thongso_ims.ds_kieuld[0].kieuld_id : 0;
            }).catch(() => {
                this.thongso_ims.ds_kieuld = [];
                this.thongso_ims.kieuld_id = 0;
            });
            this.visibled_control.btn_themtb = !this.BANCHEO;
        },
        setButton(kieu) {
            this.disabled_control.btn_nhapmoi = true;
            this.disabled_control.btn_ghilai = true;
            this.disabled_control.btn_xoa = true;
            this.disabled_control.btn_huybo = true;
            this.disabled_control.btn_themtb = true;
            this.disabled_control.btn_xoatb = true;

            if (kieu == -1) {//Bat dau
                this.$refs.ma_tb.focus(); // Ngộ nhỉ, cái này mà chạy trong hàm created thì lỗi không tồn tại $refs.ma_tb, chạy trong mounted thì ok
                this.disabled_control.btn_ghilai = false;
                this.disabled_control.btn_huybo = false;
            }

            if (kieu == 0) {//Bat dau
                this.$refs.ma_tb.focus();
                this.disabled_control.btn_nhapmoi = false;
                this.clear();
            }

            if (kieu == 1) {//Them moi
                this.$refs.ma_tb.focus();
                this.disabled_control.btn_ghilai = false;
                this.disabled_control.btn_huybo = false;
                this.clear();
                this.ds_thuebao.data = [];
            }

            if (kieu == 2)//Huy
            {
                this.$refs.ma_gd.focus(); 
                this.disabled_control.btn_nhapmoi = false;
                this.disabled_control.btn_xoa = false;
                this.clear();
            }

            if (kieu == 3)//Edit
            {
                this.$refs.ma_gd.focus(); 
                this.disabled_control.btn_nhapmoi = false;
                this.disabled_control.btn_xoa = false;
                this.disabled_control.btn_ghilai = false;
                this.disabled_control.btn_huybo = false;
                this.disabled_control.btn_themtb = false;
                this.disabled_control.btn_xoatb = false;
            }
        },
        clear() {
            this.thongso_ban = this.Reset_Thongso_Thuebao()
            this.thongso_tc = this.Reset_Thongso_Thuebao()
            this.thongso_ims.ip_moi = '';
            this.thongso_ims.ip_cu = '';
            this.thongso_ims.cuocgoi_dt_moi = '';
            this.thongso_ims.cuocgoi_dt_cu = '';
            this.thongso_ims.ctdv_id_moi = this.thongso_ims.ds_ctdv_moi.length > 0 ? this.thongso_ims.ds_ctdv_moi[0].ctdv_id : 0;
            this.thongso_ims.ctdv_id_cu = this.thongso_ims.ds_ctdv_cu.length > 0 ? this.thongso_ims.ds_ctdv_cu[0].ctdv_id : 0;
            this.phuongld_id = 0;
            this.phold_id = 0;
            this.apld_id = 0;
            this.khuld_id = 0;
            this.$refs.ma_tb.focus();
            this.thongso_ims.diachi_ld = '';
            this.hdkh_id = 0;
            this.thongso_ims.ma_gd = '';
            this.thongso_ims.ma_hd = '';
            this.thongso_ims.ma_kh = '';
            this.khachhang_id = 0;
            this.thongso_ims.ten_kh = '';
            this.thongso_ims.diachi_kh = '';
            this.thongso_ims.ngaylap_hd = this.homnay;
            this.hdtb_id = 0;
            this.thuebao_id = 0;
            this.thongso_ims.ten_tb = '';
            this.thongso_ims.ghichu = '';
            this.diachi_tb = "";
            this.thongso_ims.ngay_yc = this.homnay;
            this.thongso_ims.ma_tb = "";
            this.thongso_ims.kieuld_id = this.thongso_ims.ds_kieuld.length > 0 ? this.thongso_ims.ds_kieuld[0].kieuld_id : 0;
            this.doituong_id = 1;// Default tư nhân
            this.thongso_ims.tien = "";
            this.thongso_ims.vat = "";
            this.trangbi_id = 3;// default khách hàng tự trang bị
            this.ltb_adsl_id = 4;// Default Mytv
            this.layTienTheoKhoanMuc();
            this.thongso_ims.tongtien = 0;
            this.thongso_ims.nguong_csd_cu = "";
            this.thongso_ims.nguong_csd_moi = "";
            this.ds_thuebao.data = [];
        },
        openFrmTraCuuHopDong() {
            this.$refs.popupSearchContract.showModal();
        },
        chonHopDong: async function(itemSelected) { // tương đương với hàm C# btnLayMaGD_Click từ đoạn code if (f.chapnhan)
            this.thongso_ims.ma_gd = itemSelected.ma_gd;
            this.$refs.ma_gd.focus();
            if (this.thongso_ims.ma_gd && this.thongso_ims.ma_gd.trim().length > 0) {
                let dshd = [];
                await api.getDsHopDongTheoMaGD(this.axios, {
                    "in_ma_gd": this.thongso_ims.ma_gd,
                    "in_loaihd_id": this.frm_hopdong.loai_hd_id,
                    "in_donvi_id": this.tt_nd.donvi_id,
                    "in_tthd_id": 1,
                    "in_nhanvien_id": this.tt_nd.nhanvien_id,
                    "in_donvi_dl_id": 0
                }).then(res => {
                    dshd = res.data.data ? res.data.data : [];
                    if (dshd.length > 0) {
                        this.hdkh_id = dshd[0].hdkh_id;
                        this.thongso_ims.ma_gd = dshd[0].ma_gd;
                        this.thongso_ims.ma_hd = dshd[0].ma_hd;
                        this.thongso_ims.ma_kh = dshd[0].ma_kh;
                        if (dshd[0].khachhang_id) this.khachhang_id = dshd[0].khachhang_id;
                        this.thongso_ims.ten_kh = dshd[0].ten_kh;
                        this.thongso_ims.diachi_kh = dshd[0].diachi_kh;
                        this.thongso_ims.ngaylap_hd = moment(dshd[0].ngaylap_hd).format('DD/MM/YYYY');
                        this.thongso_ims.ngay_yc = moment(dshd[0].ngay_yc).format('DD/MM/YYYY');
                    }
                }).catch(err => {
                    dshd = [];
                    this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                });
                if (dshd.length > 0) {
                    let result = await this.hienThiDanhSachHDTB();
                    //this.setButton(result);
                    this.setButton(3);
                } else {
                    this.setButton(0);
                }
            }
        },
        openFrmTraCuuDanhBa() {
            this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
            this.popupComponentName = 'SearchAccount'
            try {
                this.popupComponent.data.isPopup = true
                this.popupComponent.data.vdichvuvt_id = 0
            } catch (ex) {
                if (this.debug) console.error(ex)
            }
            this.Popup('popupComponentMGs')
        },
        layBienDongTheoHDTBId: async function() {
            let ds = [];
            await api.getBienDongTheoHDTBId(this.axios, {
                "hdtb_id": this.hdtb_id
            }).then(res => {
                ds = res.data.data ? res.data.data : [];
            }).catch(err => {
                this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
            });
            return ds;
        },
        openFrmDSTB: async function() {
            if (this.thongso_ims.ma_tb.trim().length > 0) {
                if (this.khachhang_id == 0) {
                    let ds_khcu = await this.layBienDongTheoHDTBId();
                    //console.log("ds_khcu=" + JSON.stringify(this.ds_khcu));
                    if (ds_khcu.length > 0) {
                        this.khachhang_id = ds_khcu[0].khachhang_id;
                    }
                } 
                //console.log("khachhang_id=" + this.khachhang_id);
                this.$refs.popupSearchSubscriber.showModal();
            } else {
                this.$toast.error("Bạn chưa nhập mã thuê bao!!!");
            }
        },
        chonThueBao: async function(data) {
            //console.log("frmTraCuuThueBao=" + JSON.stringify(data));
            if (data.ma_tb) {
                this.thongso_ims.ma_tb = data.ma_tb;
            }
            if (data.dichvuvt_id) {
                this.thongso_ims.dichvuvt_id = data.dichvuvt_id;
            }
            this.$refs.ma_tb.focus();
            await this.enter_MaTB();
        },
        thayDoiDichVu: async function () {
            if (this.frm_hopdong.dichvu_id) {
                await api.getDsLoaiTB(this.axios).then(res => {
                    this.thongso_ims.ds_loaitb = res.data.data ? res.data.data.filter(x => x.DICHVUVT_ID == this.frm_hopdong.dichvu_id) : [];
                    this.thongso_ims.loaitb_id = this.thongso_ims.ds_loaitb.length > 0 ? this.thongso_ims.ds_loaitb[0].LOAITB_ID : 0;
                }).catch(() => {
                    this.thongso_ims.ds_loaitb = [];
                    this.thongso_ims.loaitb_id = 0;
                });
            }
        },
        thayDoiLoaiTB: async function () {
            if (this.thongso_ims.loaitb_id) {
                if (!this.BANCHEO) {
                    // bangts.HT_Kieu_LD_Combobox(cboKieuLD, Convert.ToInt32(cboLoaihinhTB.SelectedValue), LoaiHopDong.THAYDOI_THONGSO_IMS);
                    await api.getDsKieuLD(this.axios, {
                        "vloaihd_id": this.frm_hopdong.loai_hd_id,
                        "vloaitb_id": this.thongso_ims.loaitb_id
                    }).then(res => {
                        this.thongso_ims.ds_kieuld = res.data.data ? res.data.data : [];
                        this.thongso_ims.kieuld_id = this.thongso_ims.ds_kieuld.length > 0 ? this.thongso_ims.ds_kieuld[0].kieuld_id : 0;
                    }).catch(() => {
                        this.thongso_ims.ds_kieuld = [];
                        this.thongso_ims.kieuld_id = 0;
                    });
                }                    
                else {
                    // bangts.HT_Kieu_LD_BANCHEO_Combobox(cboKieuLD, loaitb_id, LoaiHopDong.THAYDOI_THONGSO_IMS, 2);
                    await api.lay_ds_kieu_ld_bc(this.axios, {
                        "vloaihd_id": this.frm_hopdong.loai_hd_id,
                        "vkieu": 2,
                        "vloaitb_id": this.thongso_ims.loaitb_id
                    }).then(res => {
                        this.thongso_ims.ds_kieuld = res.data.data ? res.data.data : [];
                        this.thongso_ims.kieuld_id = this.thongso_ims.ds_kieuld.length > 0 ? this.thongso_ims.ds_kieuld[0].kieuld_id : 0;
                    }).catch(() => {
                        this.thongso_ims.ds_kieuld = [];
                        this.thongso_ims.kieuld_id = 0;
                    });
                }                    
            }
        },
        thayDoiKieuLD() {
            if (this.thongso_ims.ds_kieuld.length > 0)
            {
                this.disabled_control.txt_ip_cu = true;
                this.disabled_control.txt_ip_moi = true;
                this.disabled_control.txt_soluong_cu = true;
                this.disabled_control.txt_soluong_moi = true;
                this.disabled_control.txt_nguongcsd_cu = true;
                this.disabled_control.txt_nguongcsd_moi = true;
                if (this.thongso_ims.kieuld_id == this.kieuld.thaydoi_tt_ip_ims)
                {
                    this.disabled_control.txt_ip_moi = false;
                }
                if (this.thongso_ims.kieuld_id == this.kieuld.thaydoi_tt_cuocgoi_ims)
                {
                    this.disabled_control.txt_soluong_moi = false;
                }
                if (this.thongso_ims.kieuld_id == this.kieuld.thaydoi_tt_nguong_csd_ims)
                {
                    this.disabled_control.txt_nguongcsd_moi = false;
                }
                this.layTienTheoKhoanMuc();

                // #region bancheo
                if (this.BANCHEO && this.thuebao_id != 0)
                {
                    // frmThongSoCoDinh frm = new frmThongSoCoDinh(tinh, loaitb_ld_id, loaitb_id, 0, 0, LoaiHopDong.THAYDOI_THONGSO_IMS);
                    this.$refs.thongSoCoDinhModal.showModal(this.tinh.tinh_thicong, this.thongso_ims.loaitb_id, this.thongso_ims.loaitb_id, 0, 0, this.frm_hopdong.loai_hd_id)
                    
                    // frm.thongso_Ban = thongso_ban;
                    // frm.thongso_TC = thongso_tc;
                    // frm.khachhang_id = khachhang_tc_id;
                    // frm.hdkh_id = hdkh_id;
                    // frm.ShowDialog();
                }
                // #endregion
            }
        },
        openFrmThongSo: function () {
            if (!this.thuebao_id) {
                this.$toast.error('Chưa có thông tin thuê bao ID, bạn hãy tìm thuê bao trước!')
                return
            }
            if (this.BANCHEO && this.thuebao_id != 0) {
                // frmThongSoCoDinh frm = new frmThongSoCoDinh(tinh, loaitb_ld_id, loaitb_id, 0, 0, LoaiHopDong.THAYDOI_THONGSO_IMS);
                this.$refs.thongSoCoDinhModal.showModal(this.tinh.tinh_thicong, this.thongso_ims.loaitb_id, this.thongso_ims.loaitb_id, 0, 0, this.frm_hopdong.loai_hd_id)
                
                // frm.thongso_Ban = thongso_ban;
                // frm.thongso_TC = thongso_tc;
                // frm.khachhang_id = khachhang_tc_id;
                // frm.hdkh_id = hdkh_id;
                // frm.ShowDialog();
            }
        },
        async acceptThongSoCoDinh(data) {
            Object.assign(this.thongso_ban, data.thongso_ban)
            Object.assign(this.thongso_tc, data.thongso_tc)
            let kieuld_id_tc = data.thongso_tc.kieuld_id;
            if (kieuld_id_tc == KieuLapDat.THAYDOI_TT_IP_IMS)
            {
                // txtIpMoi.Enabled = true;
                this.disabled_control.txt_ip_moi = false
            }
            if (kieuld_id_tc == KieuLapDat.THAYDOI_TT_CUOCGOI_IMS)
            {
                // txtSoLuongMoi.Enabled = true;
                this.disabled_control.txt_soluong_moi = false
            }
            if (kieuld_id_tc == KieuLapDat.THAYDOI_TT_NGUONG_CSD_IMS)
            {
                // txtNguongCSD_Moi.Enabled = true;
                this.disabled_control.txt_nguongcsd_moi = false
            }
        },
        layTienTheoKhoanMuc: async function() {
            let flag = true;
            await api.getTienKhoanMucLD(this.axios, {
                "apld_id": this.apld_id,
                "dichvuvt_id": this.thongso_ims.dichvuvt_id,
                "doituong_id": this.doituong_id,
                "kh_cd": 0,
                "khuld_id": this.khuld_id,
                "kieuld_id": this.thongso_ims.kieuld_id,
                "loaihd_id": this.frm_hopdong.loai_hd_id,
                "loaitb_id": this.thongso_ims.loaitb_id,
                "ltb_adsl_cu_id": 0,
                "ltb_adsl_id": this.ltb_adsl_id,
                "ltb_adsl_moi_id": 0,
                "muccuoc_id": 0,
                "ngay_yc": this.thongso_ims.ngay_yc,
                "phold_id": this.phold_id,
                "phuongld_id": this.phuongld_id,
                "tocdo_cu_id": 0,
                "tocdo_id": 0,
                "tocdo_moi_id": 0,
                "trangbi_id": this.trangbi_id
            }).then(res => {
                if (res.data.data && res.data.data.length > 0) {
                    let ds = res.data.data;
                    if (Number(ds[0].id) != -1) {
                        for (let i=0; i < ds.length; i++) {
                            if (Number(ds[i].khoanmuctt_id) == 8) {
                                this.thongso_ims.tien = Number(ds[i].tien);
                                this.thongso_ims.vat = Number(ds[i].vat);
                                flag = false;
                            }
                        }
                        if (flag) {
                            this.thongso_ims.tien = 0;
                            this.thongso_ims.vat = 0;
                        }
                    } else {
                        this.thongso_ims.tien = 0;
                        this.thongso_ims.vat = 0;
                    }
                }
            }).catch(err => {
                this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                this.thongso_ims.tien = 0;
                this.thongso_ims.vat = 0;
            })
        },
        ktraTBLapHDKhac: async function() {
            let ktra_matb_lap_hd_khac = false;
            await api.ktraTB_LapHDKhac(this.axios, {
                "dichVuVtId": this.thongso_ims.dichvuvt_id,
                "loaiHdId": this.frm_hopdong.loai_hd_id,
                "maGd": "0",
                "maTb": this.thongso_ims.ma_tb
            }).then(res => {
                if (res.data.data) {
                    let tt_tb = res.data.data
                    this.$toast.error(`Thuê bao ${tt_tb['ma_tb']} đang có hợp đồng ${tt_tb['ten_loaihd']} ${tt_tb['ma_gd']} ${tt_tb['trangthai_hd']} ngày ${tt_tb['ngay_yc']}`)
                    ktra_matb_lap_hd_khac = true;
                } else {
                    ktra_matb_lap_hd_khac = false;
                }
            }).catch(() => {
                ktra_matb_lap_hd_khac = false;
            });
            return ktra_matb_lap_hd_khac;
        },
        enter_MaTB: async function() {
            try {
                if (this.thongso_ims.ma_tb.trim().length > 0) {                    
                    let dshd = [];
                    this.loading(true);
                    if (this.BANCHEO) {
                        // tinh = new BanCheoBase().LAY_TINHTHICONG_THEO_MATB(txtMaTB.Text);
                        this.tinh = {}
                        await api.lay_tinhthicong_theo_matb(this.axios, this.thongso_ims.ma_tb)
                            .then(res => {
                                if (res.data.error_code == 'BSS-00000000' && res.data.data) {
                                    this.tinh = res.data.data[0];
                                } else {
                                    this.$toast.error('Lỗi lấy tỉnh thi công bán chéo ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                                }
                            }).catch((e) => {
                                this.$toast.error('Lỗi lấy tỉnh thi công bán chéo ' + e)
                            });

                        let check_tinh_tc = true
                        if (!this.tinh || !this.tinh.tinh_thicong)
                        {                        
                            this.$bvModal.msgBoxConfirm("Thuê bao này không thuộc hợp đồng bán chéo. Bạn có muốn tiếp tục không?", {
                                title: "Thông báo",
                                size: "sm",
                                okTitle: "Đồng ý",
                                cancelTitle: "Hủy",
                            }).then((v) => {
                                if (!v) {
                                    this.thongso_ims.ma_tb = "";
                                    check_tinh_tc = false
                                }
                            })
                        }
                        if (!check_tinh_tc) return
                        if (this.tinh && this.tinh.tinh_thicong > 0) {
                            // ds = new TraCuu().LAY_DS_HOPDONG_THEO_MA_TB(txtMaTB.Text, LoaiHopDong.THAYDOI_THONGSO_IMS, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0, dichvuvt_id, 0);
                            await api.lay_ds_hopdong_theo_ma_tb_TraCuu(this.axios, {
                                "tinhthicong_id": this.tt_nd.phanvung_id,
                                "in_dichvuvt_id": this.thongso_ims.dichvuvt_id,
                                "in_donvi_dl_id": 0,
                                "in_donvi_id": this.tt_nd.donvi_id,
                                "in_loaihd_id": this.frm_hopdong.loai_hd_id,
                                "in_loaitb_id": 0,
                                "in_ma_tb": this.thongso_ims.ma_tb,
                                "in_nhanvien_id": this.tt_nd.nhanvien_id,
                                "in_tthd_id": 1
                            }).then(res => {
                                dshd = res.data.data ? res.data.data : [];                            
                            }).catch(err => {
                                dshd = [];
                                this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                            });
                        } else {
                            // ds = new TCHopDongFacade().LAY_DS_HOPDONG_THEO_MA_TB(txtMaTB.Text, LoaiHopDong.THAYDOI_THONGSO_IMS, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0, dichvuvt_id, 0);
                            await api.getDsHopDongTheoMaTB(this.axios, {
                                "in_dichvuvt_id": this.thongso_ims.dichvuvt_id,
                                "in_donvi_dl_id": 0,
                                "in_donvi_id": this.tt_nd.donvi_id,
                                "in_loaihd_id": this.frm_hopdong.loai_hd_id,
                                "in_loaitb_id": 0,
                                "in_ma_tb": this.thongso_ims.ma_tb,
                                "in_nhanvien_id": this.tt_nd.nhanvien_id,
                                "in_tthd_id": 1
                            }).then(res => {
                                dshd = res.data.data ? res.data.data : [];
                            }).catch(err => {
                                dshd = [];
                                this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                            });
                        }                        
                    }
                    else {
                        // ds = tchopdong.LAY_DS_HOPDONG_THEO_MA_TB(txtMaTB.Text, LoaiHopDong.THAYDOI_THONGSO_IMS, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0, dichvuvt_id, 0);
                        await api.getDsHopDongTheoMaTB(this.axios, {
                            "in_dichvuvt_id": this.thongso_ims.dichvuvt_id,
                            "in_donvi_dl_id": 0,
                            "in_donvi_id": this.tt_nd.donvi_id,
                            "in_loaihd_id": this.frm_hopdong.loai_hd_id,
                            "in_loaitb_id": 0,
                            "in_ma_tb": this.thongso_ims.ma_tb,
                            "in_nhanvien_id": this.tt_nd.nhanvien_id,
                            "in_tthd_id": 1
                        }).then(res => {
                            dshd = res.data.data ? res.data.data : [];
                        }).catch(err => {
                            dshd = [];
                            this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                        });
                    }
                    if (dshd.length > 0) {
                        this.donvi_id = dshd[0].donvi_id; // Cái này tự chế vì code cũ không có chỗ gán nên nếu theo code cũ thì giá trị của nó = 0
                        this.thuebao_id = dshd[0].thuebao_id; // Cái này tự chế vì code cũ không có chỗ gán nên nếu theo code cũ thì giá trị của nó = 0
                        this.hdkh_id = dshd[0].hdkh_id;
                        this.thongso_ims.ma_gd = dshd[0].ma_gd;
                        this.thongso_ims.ma_hd = dshd[0].ma_hd;
                        this.thongso_ims.ma_kh = dshd[0].ma_kh;
                        if (dshd[0].khachhang_id) this.khachhang_id = dshd[0].khachhang_id;
                        this.thongso_ims.ten_kh = dshd[0].ten_kh;
                        this.thongso_ims.diachi_kh = dshd[0].diachi_kh;
                        this.thongso_ims.ngaylap_hd = moment(dshd[0].ngaylap_hd).format('DD/MM/YYYY');
                        this.thongso_ims.ngay_yc = moment(dshd[0].ngay_yc).format('DD/MM/YYYY');

                        let result = await this.hienThiDanhSachHDTB();
                        //this.setButton(result);
                        this.setButton(3);
                    } else {
                        let ktra_matb_lap_hd_khac = await this.ktraTBLapHDKhac();
                        let hienThiTTMoRong_DB = false;
                        let db = await api.getDanhBaTheoMaTB(this.axios, {
                            "in_dichvuvt_id": this.thongso_ims.dichvuvt_id,
                            "in_donvi_dl_id": 0,
                            "in_ma_tb": this.thongso_ims.ma_tb
                        }).then(res => {
                            if (res.data.data && res.data.data.length > 0) {
                                return res.data.data                                
                            } else {
                                this.$toast.error("Không tìm thấy thông tin về mã thuê bao " + this.thongso_ims.ma_tb);
                                return []
                            }
                        }).catch(err => {
                            this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                            return []
                        });
                        console.log('getDanhBaTheoMaTB > db', db)
                        if (ktra_matb_lap_hd_khac) {
                            return
                        } else {
                            if (db[0].trangthaitb_id != 1) {
                                let str = "Thuê bao có mã: " + db[0].ma_tb + " đang ở trạng thái: " + db[0].trangthai_tb + "\nBạn không thể thực hiện thay đổi thông số cho thuê bao này";
                                this.$toast.error(str);
                            } else if (db[0].dichvuvt_id != this.frm_hopdong.dichvu_id) {
                                this.$toast.error("Chức năng này chỉ thực hiện đối với thuê bao IMS!");
                            } else {
                                this.donvi_id = db[0].donvi_id; // Cái này tự chế vì code cũ không có chỗ gán nên nếu theo code cũ thì giá trị của nó = 0
                                this.thongso_ims.loaitb_id = db[0].loaitb_id;
                                this.thongso_ims.ma_kh = db[0].ma_kh;
                                this.thongso_ims.ma_hd = db[0].ma_hd;
                                this.khachhang_id = db[0].khachhang_id;
                                this.thongso_ims.ten_kh = db[0].ten_kh;
                                this.thongso_ims.diachi_kh = db[0].diachi_kh;
                                this.thongso_ims.ngaylap_hd = db[0].ngaylap_hd;
                                this.thuebao_id = db[0].thuebao_id;
                                this.thongso_ims.ten_tb = db[0].ten_tb;
                                this.thongso_ims.diachi_ld = db[0].diachi_ld;
                                this.diachi_tb = db[0].diachi_tb;
                                this.diachi_ld = db[0].diachi_ld;
                                if (this.BANCHEO) {
                                    if (this.tinh.tinh_thicong > 0) {
                                        // this.thuebao_id_tc = tcbancheo.LAY_THUEBAOID_THICONG(tinh, thuebao_id);
                                        let ds = await this.lay_thuebaoid_thicong(this.thuebao_id, this.tinh.tinh_thicong)
                                        if (ds.length > 0) {
                                            this.thuebao_id_tc = ds[0].thuebao_kn_id > 0 ? ds[0].thuebao_kn_id : 0
                                        }
                                    }
                                    if (this.thuebao_id_tc) {
                                        let input = { 
                                            tinhthicong_id: this.tinh.tinh_thicong, 
                                            thuebao_id: this.thuebao_id_tc, 
                                            dichvuvt_id: db[0].dichvuvt_id, 
                                            donvi_id: 0, 
                                            loaihdbc: 2,
                                        }
                                        let ds = await this.lay_danhba_theo_thuebaoid(input)
                                        this.dsDBTBBC = ds
                                    } else {
                                        this.$toast.error('Không lấy được thuê bao thi công ID')
                                    }                                    

                                    // dsDBTBBC = tcbancheo.LAY_DANHBA_THEO_THUEBAOID(tinh, thuebao_id_tc, this.frm_hopdong.dichvu_id, 0, 2);
                                    this.gan_thongso_theodanhba(1, this.frm_hopdong.dichvu_id, db[0].thuebao_id);
                                    this.gan_thongso_theodanhba(2, this.frm_hopdong.dichvu_id, this.thuebao_id_tc);
                                }
                                hienThiTTMoRong_DB = true;
                            }
                        }

                        if (hienThiTTMoRong_DB) {
                            await api.layDsDanhBaIMS(this.axios, { thuebao_id: this.thuebao_id }).then(res => {
                                let ds = res.data.data ? res.data.data : [];
                                if (ds.length > 0) {
                                    this.thongso_ims.ctdv_id_cu = ds[0].ctdv_id ? ds[0].ctdv_id : 0;
                                    this.thongso_ims.ctdv_id_moi = ds[0].ctdv_id ? ds[0].ctdv_id : 0;
                                    this.thongso_ims.cuocgoi_dt_cu = ds[0].sl_cuocgoi ? ds[0].sl_cuocgoi : '';
                                    this.thongso_ims.cuocgoi_dt_moi = ds[0].sl_cuocgoi ? ds[0].sl_cuocgoi : '';
                                    this.thongso_ims.ip_cu = ds[0].ip ? ds[0].ip : '';
                                    this.thongso_ims.ip_moi = ds[0].ip ? ds[0].ip : '';
                                    this.thongso_ims.nguong_csd_moi = ds[0].nguong_csd ? ds[0].nguong_csd : '';
                                    this.thongso_ims.nguong_csd_cu = ds[0].nguong_csd ? ds[0].nguong_csd : '';
                                }
                            }).catch(err => {
                                console.log("err layDsDanhBaIMS="+JSON.stringify(err))
                            });
                        }
                    }
                    this.thayDoiCTDVMoi();
                    this.loading(false);
                }
            } catch (e) {} finally { this.loading(false); }
        },
        gan_thongso_theodanhba(vkieu, vdichvu, vthuebaoid) {
            try {
                let ds = []
                //dichvu vu co dinh
                if (vkieu == 1) {
                    // this.thongso_ban = tcbancheo.Reset_Thongso_Thuebao();
                    this.thongso_ban = this.Reset_Thongso_Thuebao()
                } else {
                    // this.thongso_tc = tcbancheo.Reset_Thongso_Thuebao();
                    this.thongso_tc = this.Reset_Thongso_Thuebao()
                }                                  
            } catch (e) {}
        },
        Reset_Thongso_Thuebao() {
            let ketqua = {};
            ketqua.trangbi_id = 0;
            ketqua.muccuoc_id = 0;
            ketqua.chuquan_id = 0;
            ketqua.congnghe_id = 0;
            ketqua.doituong_id = 0;
            ketqua.kieuld_id = 0;
            ketqua.kieutra_id = 0;
            ketqua.loaikenh_id = 0;
            ketqua.loaimd_id = 0;
            ketqua.muccuoctb_id = 0;
            ketqua.thietbidc_id = 0;
            ketqua.thoihan_id = 0;
            ketqua.tocdo_id = 0;
            ketqua.tocdo_isp = 0;
            ketqua.tocdo_pir_id = 0;
            ketqua.tocdothuc_id = 0;
            ketqua.trangbi_id = 0;
            ketqua.ma_doicap = "";
            ketqua.ma_tn = "";
            return ketqua;
        },
        maTB_KeyPress: async function(event) {
            if (event) {
                await this.enter_MaTB();
                await this.thayDoiLoaiTB()
            }
        },
        maGD_KeyPress: async function(event) {
            if (event) {
                if (this.thongso_ims.ma_gd.trim().length > 0) {
                    let dshd = [];
                    await api.getHDKHTheoMaGD(this.axios, {
                        "p_ma_gd": this.thongso_ims.ma_gd,
                        "p_loaihd_id": this.frm_hopdong.loai_hd_id,
                        "p_tthd_id": 1,
                        "p_donvi_id": this.tt_nd.donvi_id,
                        "p_nhanvien_id": this.tt_nd.nhanvien_id
                    }).then(res => {
                        dshd = res.data.data ? res.data.data : [];
                    }).catch(err => {
                        dshd = [];
                        this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                    });
                    if (dshd.length > 0) {
                        this.hdkh_id = dshd[0].hdkh_id;
                        this.thongso_ims.ma_gd = dshd[0].ma_gd;
                        this.thongso_ims.ma_hd = dshd[0].ma_hd;
                        this.thongso_ims.ma_kh = dshd[0].ma_kh;
                        if (dshd[0].khachhang_id) this.khachhang_id = dshd[0].khachhang_id;
                        this.thongso_ims.ten_kh = dshd[0].ten_kh;
                        this.thongso_ims.diachi_kh = dshd[0].diachi_kh;
                        this.thongso_ims.ngaylap_hd = moment(dshd[0].ngaylap_hd).format('DD/MM/YYYY');
                        this.thongso_ims.ngay_yc = moment(dshd[0].ngay_yc).format('DD/MM/YYYY');
                        let result = await this.hienThiDanhSachHDTB();
                        //this.setButton(result);
                        this.setButton(3);
                    }else {
                        this.setButton(0);
                    }
                    this.thayDoiCTDVMoi();
                }
            }
        },
        nhapMoi() {
            this.setButton(1);
        },
        ktraMaKH_MaTT_MaGD: async function(kieu, ma) {
            let kt_ma = 0;
            await api.ktraMaKH_MaTT_MaGD(this.axios, {
                "kieu": kieu,
                "loaiHdId": this.frm_hopdong.loai_hd_id,
                "maKh": ma
            }).then(res => {
                if (res.data.error_code === "BSS-00000000" && res.data.data) {
                    kt_ma = Number(res.data.data);
                }
            }).catch(err => {
                this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
            });
            if (kt_ma == 0) {
                if (kieu == 1) {
                    this.$toast.error("Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                    this.$refs.ma_kh.focus();
                } else if (kieu == 3) {
                    this.$toast.error("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                    this.$refs.ma_gd.focus();
                }
                return false;
            }
        },
        kiemTraDL_KhachHang: async function() {
            let result = false;
            if (this.BANCHEO) {
                let kq_bc = ''                
                // string kq_bc = bc.kiemtra_hd_bancheo_tc(thuebao_id, LoaiHopDong.CHUYENDOI_LH);
                await api.kiemtra_hd_bancheo_tc(this.axios, {
                    "thuebao_id": this.thuebao_id,
                    "loaihd_id": this.frm_hopdong.loai_hd_id
                }).then(res => {
                    if (res.data.error_code === "BSS-00000000" && res.data.data) {
                        kq_bc = res.data.data;
                    }
                })
                if (kq_bc != "OK") {
                    this.$toast.error(kq_bc);
                    return false;
                }
            }

            if (!this.thongso_ims.ten_kh || this.thongso_ims.ten_kh.trim().length == 0) {
                this.$toast.error("Hãy nhập tên khách hàng !");
                this.$refs.ten_kh.focus();
                result = false;
            } else if ((!this.thongso_ims.ma_kh || this.thongso_ims.ma_kh.trim().length == 0) && this.khachhang_id != 0) {
                this.$toast.error("Kiểm tra lại mã khách hàng !");
                this.$refs.ma_kh.focus();
                result = false;
            } else {
                if (this.thongso_ims.ma_kh && this.thongso_ims.ma_kh.trim().length > 0) {
                    await this.ktraMaKH_MaTT_MaGD(1, this.thongso_ims.ma_kh);
                }

                if (this.thongso_ims.ma_gd && this.thongso_ims.ma_gd.trim().length > 0) {
                    await this.ktraMaKH_MaTT_MaGD(3, this.thongso_ims.ma_gd);
                }
                result = true;
            }
            return result;
        },
        kiemTraDL_ThueBao: async function() {
            let result = false;
            if ((!this.thongso_ims.ma_hd || this.thongso_ims.ma_hd.trim().length == 0) && this.khachhang_id != 0) {
                this.$toast.error("Hãy nhập mã hợp đồng !");
                this.$refs.ma_hd.focus();
                result = false;
            } else if (!this.thongso_ims.ten_tb || this.thongso_ims.ten_tb.trim().length == 0) {
                this.$toast.error("Hãy nhập tên thuê bao !");
                this.$refs.ten_tb.focus();
                result = false;
            } else {
                if (!this.disabled_control.btn_nhapmoi) {
                    if (this.hdtb_id == 0) {
                        this.$toast.error("Hãy chọn thuê bao để cập nhật thông tin");
                        return false;
                    }
                }
                let vkieuld_id = Number(this.thongso_ims.kieuld_id);
                if (this.BANCHEO) vkieuld_id = this.thongso_tc.kieuld_id;
                if (this.thongso_ims.loaitb_id == 77) { // IMS_SIPTRUNKING 
                    if (!this.thongso_ims.cuocgoi_dt_moi || this.thongso_ims.cuocgoi_dt_moi.length == 0 || this.thongso_ims.cuocgoi_dt_moi == '0') {
                        this.$toast.error("Bạn chưa nhập số lượng cuộc gọi mới cho thuê bao Sip trunking!");
                        this.$refs.sl_cuocgoi_moi.focus();
                        return false;
                    }
                    if (this.thongso_ims.cuocgoi_dt_moi && !this.isNumeric(this.thongso_ims.cuocgoi_dt_moi.toString().trim())) { 
                        this.$toast.error("Số lượng cuộc gọi mới phải là kiểu số!");
                        this.$refs.sl_cuocgoi_moi.focus();
                        return false;
                    }
                    if (this.thongso_ims.ctdv_id_moi == 0) {
                        this.$toast.error("Hãy chọn chi tiết DV mới");
                        return false;
                    } else if (this.thongso_ims.ctdv_id_moi == 2) { // internet
                        if (!this.thongso_ims.ip_moi || this.thongso_ims.ip_moi.length == 0) {
                            this.$toast.error("Hãy nhập địa chỉ IP !");
                            // this.txtIpMoi.BackColor = Color.Azure;
                            this.$refs.ip_moi.focus();
                            return false;
                        }
                    }
                    if (vkieuld_id == this.kieuld.thaydoi_tt_ip_ims) { 
                        if (this.thongso_ims.ip_cu == this.thongso_ims.ip_moi) {
                            this.$toast.error("Hãy nhập IP mới");
                            this.$refs.ip_moi.focus();
                            return false;
                        }
                    }
                    if (vkieuld_id == this.kieuld.thaydoi_tt_cuocgoi_ims) { 
                        if (this.thongso_ims.cuocgoi_dt_cu == this.thongso_ims.cuocgoi_dt_moi) {
                            this.$toast.error("Hãy nhập số lượng cuộc gọi đồng thời mới");
                            this.$refs.sl_cuocgoi_moi.focus();
                            return false;
                        }
                    }
                    if (vkieuld_id == this.kieuld.thaydoi_tt_nguong_csd_ims) { 
                        if (this.thongso_ims.nguong_csd_cu == this.thongso_ims.nguong_csd_moi) {
                            this.$toast.error("Hãy nhập hạn mức ngưỡng cước sử dụng mới");
                            this.$refs.nguong_csd_moi.focus();
                            return false;
                        }
                        if (this.thongso_ims.nguong_csd_moi && !this.isNumeric(this.thongso_ims.nguong_csd_moi.toString().trim())) {
                            this.$toast.error("Hạn mức ngưỡng cước sử dụng mới phải là kiểu số!");
                            this.$refs.nguong_csd_moi.focus();
                            return false;
                        }
                    }
                }
                result = true;
            }
            return result;
        },
        layKey: async function(keyname) {
            let result = 0;
            await api.getKey(this.axios, {
                "keyname": keyname
            }).then(res => {
                result = res.data.data ? Number(res.data.data) : 0;
            }).catch(err => {
                this.$toast.error(err.data.message_detail)
            });
            return result;
        },
        layMaGDv2: async function() {
            let result = '';
            await api.getMaGDv2(this.axios, {
                "donvi_id": this.tt_nd.donvi_id,
                "loaihd_id": this.frm_hopdong.loai_hd_id
            }).then(res => {
                result = res.data.data ? res.data.data : '';
            }).catch(err => {
                this.$toast.error(err.data.message_detail)
            });
            return result;
        },
        layMaGD: async function() {
            let result = '';
            await api.getMaGD(this.axios, {
                "loaihd_id": this.frm_hopdong.loai_hd_id
            }).then(res => {
                result = res.data.data ? res.data.data : '';
            }).catch(err => {
                this.$toast.error(err.data.message_detail)
            });
            return result;
        },
        taoDuLieu_HDKH: async function(themmoi) {
            try {
                this.dsHDKH = [];
                if (themmoi)
                {
                    this.hdkh_id = await this.layKey("HD_KHACHHANG");
                }
                let hd_KHACHHANGRow = {};
                hd_KHACHHANGRow.HDKH_ID = this.hdkh_id;
                if (themmoi)
                {
                    let text = "";
                    if (this.ts_sinhma_gd_theo_donvi)
                    {
                        text = await this.layMaGDv2();
                    }
                    else
                    {
                        text = await this.layMaGD();
                    }
                    this.thongso_ims.ma_gd = text;
                    hd_KHACHHANGRow.MA_GD = text;
                }
                else
                {
                    hd_KHACHHANGRow.MA_GD = this.thongso_ims.ma_gd;
                }
                hd_KHACHHANGRow.MA_HD = this.thongso_ims.ma_hd;
                hd_KHACHHANGRow.NGAY_YC = this.thongso_ims.ngay_yc;
                hd_KHACHHANGRow.MA_KH = this.thongso_ims.ma_kh;
                if (this.khachhang_id > 0)
                {
                    hd_KHACHHANGRow.KHACHHANG_ID = this.khachhang_id;
                }
                if (this.hdkh_cha_id > 0)
                {
                    hd_KHACHHANGRow.HDKH_CHA_ID = this.hdkh_cha_id;
                    this.hdkh_cha_id = 0;
                }
                hd_KHACHHANGRow.TEN_KH = this.thongso_ims.ten_kh;
                hd_KHACHHANGRow.DIACHI_KH = this.thongso_ims.diachi_kh;
                hd_KHACHHANGRow.NGAYLAP_HD = this.thongso_ims.ngaylap_hd;
                hd_KHACHHANGRow.DONVI_ID = this.tt_nd.donvi_id;
                hd_KHACHHANGRow.NHANVIEN_ID = this.tt_nd.nhanvien_id;
                hd_KHACHHANGRow.KHLON_ID = 1;
                hd_KHACHHANGRow.LOAIHD_ID = this.frm_hopdong.loai_hd_id;
                hd_KHACHHANGRow.NGUOI_CN = this.tt_nd.username;
                hd_KHACHHANGRow.NGAY_CN = this.homnay;
                hd_KHACHHANGRow.MAY_CN = this.tt_nd.may_cn;
                hd_KHACHHANGRow.IP_CN = this.tt_nd.ip;
                this.dsHDKH.push(hd_KHACHHANGRow);
                return true
            } catch (e) { return false }
        },
        taoDuLieu_HDTB: async function(themmoi) {
            try {
                this.dsHDTB = [];
                if (themmoi)
                {
                    this.hdtb_id = await this.layKey("HD_THUEBAO");
                }
                let hd_THUEBAORow = {};
                hd_THUEBAORow.HDTB_ID = this.hdtb_id;
                hd_THUEBAORow.HDKH_ID = this.hdkh_id;
                if (this.thuebao_id > 0)
                {
                    hd_THUEBAORow.THUEBAO_ID = this.thuebao_id;
                }
                hd_THUEBAORow.MA_TB = this.thongso_ims.ma_tb;
                hd_THUEBAORow.GHICHU = this.thongso_ims.ghichu;
                hd_THUEBAORow.TEN_TB = this.thongso_ims.ten_tb;
                hd_THUEBAORow.DIACHI_TB = this.diachi_tb;
                hd_THUEBAORow.DIACHI_LD = this.diachi_ld;
                hd_THUEBAORow.KIEULD_ID = this.thongso_ims.kieuld_id;
                hd_THUEBAORow.DOITUONG_ID = this.doituong_id;
                hd_THUEBAORow.TTHD_ID = 1;
                hd_THUEBAORow.LOAITB_ID = this.thongso_ims.loaitb_id; 
                hd_THUEBAORow.DICHVUVT_ID = this.thongso_ims.dichvuvt_id;
                hd_THUEBAORow.DONVI_ID = this.donvi_id; // 0 => rà soát lại
                hd_THUEBAORow.NGUOI_CN = this.tt_nd.username;
                hd_THUEBAORow.NGAY_CN = this.homnay;
                hd_THUEBAORow.MAY_CN = this.tt_nd.may_cn;
                hd_THUEBAORow.QUYTRINH_ID = this.quytrinhtn_id;
                hd_THUEBAORow.MUCUOCTB_ID = this.thongso_ban['muccuoctb_id'];
                hd_THUEBAORow.STATUS = "0";
                hd_THUEBAORow.THUEBAO_ID = this.thuebao_id; // Cái này tự chế vì DB bảo phải có trường này
                this.dsHDTB.push(hd_THUEBAORow);
                return true
            } catch (e) { return false }
        },
        taoDuLieu_HDTBDV: async function() {
            let result = true
            try {                
                this.dsHDTBDV = [];
                await api.getDsDBTB_DV(this.axios, {
                    "thuebao_id": this.thuebao_id
                }).then(res => {
                    if (res.data.data && res.data.data.length > 0) {
                        let ds = res.data.data;
                        for (let i=0; i < ds.length; i++) {
                            let hdtb_DVRow = {};
                            hdtb_DVRow.HDTB_ID = this.hdtb_id;
                            hdtb_DVRow.LOAIDV_ID = ds[i].loaidv_id;
                            hdtb_DVRow.DONVI_ID = ds[i].donvi_id;
                            hdtb_DVRow.KIEUDV_ID = 2;
                            this.dsHDTBDV.push(hdtb_DVRow);
                        }
                    } else {
                        this.$toast.error('Lỗi taoDuLieu_HDTBDV: ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                        result = false
                    }
                }).catch(err => {
                    this.$toast.error("Lỗi taoDuLieu_HDTBDV: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                    result = false
                });                
            } catch (e) { 
                console.log('taoDuLieu_HDTBDV', e)
                result = false 
            } finally {
                return result
            }
        },
        taoDuLieu_HDTB_IMS: async function() {
            let result = true
            try {
                this.dsHDTB_IMS = [];
                await api.layDsDanhBaIMS(this.axios, { thuebao_id: this.thuebao_id }).then(res => {
                    if (res.data.data && res.data.data.length > 0) {
                        let obj = res.data.data[0];
                        let hdtb_IMSRow = {};
                        hdtb_IMSRow.PHANVUNG_ID = obj.phanvung_id;// ? Number(obj.phanvung_id) : null;
                        hdtb_IMSRow.THUEBAO_ID = obj.thuebao_id;// ? Number(obj.thuebao_id) : null;
                        hdtb_IMSRow.GHICHU = obj.ghichu;// ? obj.ghichu : "";
                        hdtb_IMSRow.CULY = obj.culy;// ? obj.culy : "";
                        hdtb_IMSRow.BRAS_ID = obj.bras_id;// ? obj.bras_id : "";
                        hdtb_IMSRow.DSLAM_ID = obj.dslam_id;// ? obj.dslam_id : "";
                        hdtb_IMSRow.MADOICAP = obj.madoicap;// ? obj.madoicap : "";
                        hdtb_IMSRow.MA_LT = obj.ma_lt;// ? obj.ma_lt : "";
                        hdtb_IMSRow.TRANGBI_ID = obj.trangbi_id;// ? obj.trangbi_id : "";
                        hdtb_IMSRow.PORT_ID = obj.port_id;// ? obj.port_id : "";
                        hdtb_IMSRow.VCI_VPI_ID = obj.vci_vpi_id;// ? obj.vci_vpi_id : "";
                        hdtb_IMSRow.USERNAME = obj.username;// ? obj.username : "";
                        hdtb_IMSRow.PASSWORD = obj.password;// ? obj.password : "";
                        hdtb_IMSRow.THIETBI_ID = obj.thietbi_id;// ? obj.thietbi_id : "";
                        hdtb_IMSRow.TRAMTB_ID = obj.tramtb_id;// ? obj.tramtb_id : "";
                        hdtb_IMSRow.TUYENCAP = obj.tuyencap;// ? obj.tuyencap : "";
                        hdtb_IMSRow.LIENTU = obj.lientu;// ? obj.lientu : "";
                        hdtb_IMSRow.DOICAP_P = obj.doicap_p;// ? obj.doicap_p : "";
                        hdtb_IMSRow.HOPCAP_P = obj.hopcap_p;// ? obj.hopcap_p : "";
                        hdtb_IMSRow.TUCAP_P = obj.tucap_p;// ? obj.tucap_p : "";
                        hdtb_IMSRow.LOAICAP_ID = obj.loaicap_id;// ? obj.loaicap_id : "";
                        hdtb_IMSRow.CAP_GOC = obj.cap_goc;// ? obj.cap_goc : "";
                        hdtb_IMSRow.CAP_P = obj.cap_p;// ? obj.cap_p : "";
                        hdtb_IMSRow.DOICAP_GOC = obj.doicap_goc;// ? obj.doicap_goc : "";
                        hdtb_IMSRow.TUCAP_GOC = obj.tucap_goc;// ? obj.tucap_goc : "";
                        hdtb_IMSRow.MATB_TN = obj.matb_tn;// ? obj.matb_tn : "";
                        hdtb_IMSRow.CHUQUAN_ID = obj.chuquan_id;// ? obj.chuquan_id : "";
                        hdtb_IMSRow.SL_CUOCGOI = obj.sl_cuocgoi;// ? obj.sl_cuocgoi : "";
                        hdtb_IMSRow.CAP_ID = obj.cap_id;// ? obj.cap_id : "";
                        hdtb_IMSRow.DOICAP = obj.doicap;// ? obj.doicap : "";
                        hdtb_IMSRow.KETCUOI_ID = obj.ketcuoi_id;// ? obj.ketcuoi_id : "";
                        hdtb_IMSRow.VITRI = obj.vitri;// ? obj.vitri : "";
                        hdtb_IMSRow.DOICAP_2 = obj.doicap_2;// ? obj.doicap_2 : "";
                        hdtb_IMSRow.VITRI_2 = obj.vitri_2;// ? obj.vitri_2 : "";
                        hdtb_IMSRow.SVLAN = obj.svlan;// ? obj.svlan : "";
                        hdtb_IMSRow.CVLAN = obj.cvlan;// ? obj.cvlan : "";
                        hdtb_IMSRow.ONU_PASSWORD_CU = obj.onu_password_cu;// ? obj.onu_password_cu : "";
                        hdtb_IMSRow.MOTA_TEMBH = obj.mota_tembh;// ? obj.mota_tembh : "";
                        hdtb_IMSRow.CONGNGHE_ID = obj.congnghe_id;// ? obj.congnghe_id : "";
                        hdtb_IMSRow.THOIHAN_ID = obj.thoihan_id;// ? obj.thoihan_id : "";
                        hdtb_IMSRow.TOANHA_ID = obj.toanha_id;// ? obj.toanha_id : "";
                        hdtb_IMSRow.IP = obj.ip;// ? obj.ip : "";
                        hdtb_IMSRow.CTDV_ID = obj.ctdv_id;// ? obj.ctdv_id : "";
                        hdtb_IMSRow.CHUQUANLD_ID = obj.chuquanld_id;// ? obj.chuquanld_id : "";
                        hdtb_IMSRow.CUOC_DOITAC = obj.cuoc_doitac;// ? obj.cuoc_doitac : "";
                        hdtb_IMSRow.DOITAC_ID = obj.doitac_id;// ? obj.doitac_id : "";
                        hdtb_IMSRow.TINHKHAC = obj.tinhkhac;// ? obj.tinhkhac : "";
                        hdtb_IMSRow.MA_VNPT = obj.ma_vnpt;// ? obj.ma_vnpt : "";
                        hdtb_IMSRow.TONGDAI_ID = obj.tongdai_id;// ? obj.tongdai_id : "";
                        hdtb_IMSRow.TRUNKID = obj.trunkid;// ? obj.trunkid : "";
                        hdtb_IMSRow.HTTC_ID = obj.httc_id;// ? obj.httc_id : "";
                        hdtb_IMSRow.ENDPOINT_ID = obj.endpoint_id;// ? obj.endpoint_id : "";
                        hdtb_IMSRow.PROFILE = obj.profile;// ? obj.profile : "";
                        hdtb_IMSRow.PROFILE_INFO = obj.profile_info;// ? obj.profile_info : "";
                        hdtb_IMSRow.SERI_MD = obj.seri_md;// ? obj.seri_md : "";
                        hdtb_IMSRow.SLID = obj.slid;// ? obj.slid : "";
                        hdtb_IMSRow.DOICAP_X = obj.doicap_x;// ? obj.doicap_x : "";
                        hdtb_IMSRow.HOPCAP_X = obj.hopcap_x;// ? obj.hopcap_x : "";
                        hdtb_IMSRow.SLOT = obj.slot;// ? obj.slot : "";
                        hdtb_IMSRow.PORT = obj.port;// ? obj.port : "";
                        hdtb_IMSRow.SYSTEM = obj.system;// ? obj.system : "";
                        hdtb_IMSRow.RACK = obj.rack;// ? obj.rack : "";
                        hdtb_IMSRow.SHELF = obj.shelf;// ? obj.shelf : "";
                        hdtb_IMSRow.THAMSO = obj.thamso;// ? obj.thamso : "";
                        hdtb_IMSRow.TT_PORT_ID = obj.tt_port_id;// ? obj.tt_port_id : "";
                        hdtb_IMSRow.DSLAM_TINH_ID = obj.dslam_tinh_id;// ? obj.dslam_tinh_id : "";
                        hdtb_IMSRow.VCI_VPI_ID1 = obj.vci_vpi_id1;// ? obj.vci_vpi_id1 : "";
                        hdtb_IMSRow.VCI = obj.vci;// ? obj.vci : "";
                        hdtb_IMSRow.VPI = obj.vpi;// ? obj.vpi : "";
                        hdtb_IMSRow.MA_TBI = obj.ma_tbi;// ? obj.ma_tbi : "";
                        hdtb_IMSRow.PORT_MDF = obj.port_mdf;// ? obj.port_mdf : "";
                        hdtb_IMSRow.ADSL_PORT = obj.adsl_port;// ? obj.adsl_port : "";
                        hdtb_IMSRow.ONU_PASSWORD = obj.onu_password;// ? obj.onu_password : "";
                        hdtb_IMSRow.TENDSLAM = obj.tendslam;// ? obj.tendslam : "";
                        hdtb_IMSRow.SELFID01 = obj.selfid01;// ? obj.selfid01 : "";
                        hdtb_IMSRow.SELFID02 = obj.selfid02;// ? obj.selfid02 : "";
                        hdtb_IMSRow.SELFID03 = obj.selfid03;// ? obj.selfid03 : "";
                        hdtb_IMSRow.SELFID04 = obj.selfid04;// ? obj.selfid04 : "";
                        hdtb_IMSRow.HDTB_ID = this.hdtb_id;
                        if (this.thongso_ims.kieuld_id == this.kieuld.thaydoi_tt_cuocgoi_ims)
                        {
                            hdtb_IMSRow.SL_CUOCGOI = this.thongso_ims.cuocgoi_dt_moi;
                        }
                        if (this.thongso_ims.kieuld_id == this.kieuld.thaydoi_tt_ip_ims || this.thongso_ims.ctdv_id_moi == 2) // cái điều kiện this.thongso_ims.ctdv_id_moi == 2 là tự chế
                        {
                            hdtb_IMSRow.IP = this.thongso_ims.ip_moi;
                        }
                        if (this.thongso_ims.kieuld_id == this.kieuld.thaydoi_tt_nguong_csd_ims)
                        {
                            hdtb_IMSRow.NGUONG_CSD = this.thongso_ims.nguong_csd_moi;
                        }
                        this.dsHDTB_IMS.push(hdtb_IMSRow);                        
                    } else {
                        this.$toast.error("Lỗi taoDuLieu_HDTB_IMS: " + (res.data.message_detail ? res.data.message_detail : res.data.message));
                        result =  false
                    }
                }).catch(err => {
                    this.$toast.error("Lỗi taoDuLieu_HDTB_IMS: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                    result =  false
                });                
            } catch (e) { result =  false } finally { return result }
        },
        taoDuLieu_CT_TIENHD: async function(themmoi) {
            let result = true
            try {
                this.dsCTTHD = [];
                let ct_TIENHDRow = {};
                if (themmoi)
                {
                    ct_TIENHDRow.CT_TIENHD_ID = await this.layKey("CT_TIENHD");
                }
                ct_TIENHDRow.HDTB_ID = this.hdtb_id;
                ct_TIENHDRow.KHOANMUCTT_ID = this.khoanmuc_tt.kmtt_doits;
                ct_TIENHDRow.TIEN = this.thongso_ims.tien;
                ct_TIENHDRow.VAT = this.thongso_ims.vat;
                ct_TIENHDRow.LOAI_ID = 1;
                ct_TIENHDRow.ID = this.khoanmuc_tt.kmtt_datmoi;
                this.dsCTTHD.push(ct_TIENHDRow);
            } catch (e) { result = false } finally { return result }
        },
        TaoDuLieuBC: async function (themmoi) {
            this.dsHDBC = []
            this.dsHDTBBC = []
            this.dsHDBCIMS = []
            let result = true
            result = await this.TaoDuLieu_HDTBBC(themmoi);
            if (!result) return false
            result = await this.TaoDuLieu_HDBC_IMS(themmoi);
            if (!result) return false
            return result
        },
        TaoDuLieu_HDTBBC: async function (themmoi) {
            try {
                // ds = tcbancheo.LAY_DS_DB_THUEBAO_THEO_TBID(tinh, thuebao_id_tc);
                let ds = []
                await api.lay_ds_db_thuebao_theo_tbid(this.axios, {
                    "thuebao_id": this.thuebao_id_tc,
                    "tinhthicong_id": this.tinh.tinh_thicong
                }).then(res => {
                    if (res.data.error_code == 'BSS-00000000') {
                        ds = res.data.data ? res.data.data : [];
                    } else if (res.data.error_code == 'BSS-00000204') {

                    } else {
                        this.$toast.error('Lỗi lay_ds_db_thuebao_theo_tbid ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                        return false
                    }                    
                }).catch(err => {
                    this.$toast.error('Lỗi lay_ds_db_thuebao_theo_tbid ' + err.data.message_detail)
                    return false
                });
                let rowHDBC = {};

                rowHDBC.HDTB_BAN = this.hdtb_id;
                rowHDBC.TINH_THICONG = this.tinh.tinh_thicong;
                rowHDBC.TINH_BAN = this.tt_nd.phanvung_id;
                rowHDBC.TRANG_THAI = 0;
                rowHDBC.LOAIHD_BANCHEO = 1;

                rowHDBC.THUEBAO_ID_BAN = this.thuebao_id;
                rowHDBC.THUEBAO_ID_THICONG = this.thuebao_id_tc;

                rowHDBC.THANHTOAN_ID_BAN = 0
                await api.lay_thanhtoan_theo_thuebaoid(this.axios, {
                    "thuebao_id": this.thuebao_id,
                }).then(res => {
                    if (res.data.error_code == 'BSS-00000000') {
                        rowHDBC.THANHTOAN_ID_BAN = res.data.data ? res.data.data : 0;
                    } else {
                        this.$toast.error('Lỗi lay_thanhtoan_theo_thuebaoid ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                        return false
                    }                    
                }).catch(err => {
                    this.$toast.error(err.data.message_detail)
                    return false
                });
                // rowHDBC.THANHTOAN_ID_BAN = Convert.ToInt64(checkdata.MAP_ID("thanhtoan_id", DatabaseConstants.DB2 + ".db_thuebao", "where thuebao_id =" + thuebao_id));
                rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id;
                rowHDBC.THANHTOAN_ID_TC = Number(ds[0]?.thanhtoan_id);
                rowHDBC.KHACHHANG_ID_TC = Number(ds[0]?.khachhang_id);               
                this.dsHDBC.push(rowHDBC);

                let rowHDTBBC = {};
                // cmUlt.CopyRow(tcbancheo.LAY_DANHBA_THEO_THUEBAOID(tinh, Convert.ToInt32(thuebao_id_tc.ToString()), Convert.ToInt32(cboDichVuVT.SelectedValue.ToString()), 0, 2).Tables[0].Rows[0], rowHDTBBC);
                let input = { 
                    "tinhthicong_id": this.tinh.tinh_thicong, 
                    "thuebao_id": this.thuebao_id_tc, 
                    "dichvuvt_id": this.thongso_ims.dichvuvt_id, 
                    "donvi_id": 0, 
                    "loaihdbc": 2,  
                }
                let dsHDTBBC = await this.lay_danhba_theo_thuebaoid(input)
                if (dsHDTBBC.length > 0) dsHDTBBC = this.UpperCasePropertyList(dsHDTBBC)
                rowHDTBBC = dsHDTBBC[0]
                if(!dsHDTBBC[0]){
                    rowHDTBBC = {}
                }
                rowHDTBBC.HDTB_ID = this.hdtb_id;
                rowHDTBBC.HDKH_ID = this.hdkh_id;
                rowHDTBBC.TTHD_ID = 1;
                rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn;
                rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn;
                rowHDTBBC.KIEULD_ID = this.thongso_tc.kieuld_id;
                // Anh Tuấn Anh sửa lại thành lúc chuyển tỉnh (16/8/2024)
                // rowHDTBBC.DONVI_ID = this.tinh.donvi_id; 
                
                rowHDTBBC.NGUOI_CN = this.tt_nd.username;
                rowHDTBBC.NGAY_CN = this.homnay;
                rowHDTBBC.MAY_CN = this.tt_nd.may_cn;
                rowHDTBBC.IP_CN = this.tt_nd.ip;

                rowHDTBBC.GHICHU = this.thongso_ims.ghichu;

                rowHDTBBC.MUCUOCTB_ID = this.thongso_tc.muccuoctb_id;
                // string s = "";
                // if (string.IsNullOrEmpty(tinh.onebss_ip))
                //     s = new CheckData().MAP_ID(tinh, "cuoc_tb", "CSS_" + tinh.matinh + ".muccuoc_tb a", "where a.mucuoctb_id = " + thongso_tc.muccuoctb_id);
                // else
                //     s = new CheckData().MAP_ID(tinh, "cuoc_tb", "CSS.muccuoc_tb a", "where phanvung_id = " + tinh.tinh_thicong + " and a.mucuoctb_id = " + thongso_tc.muccuoctb_id);
                // if (s != "-1")
                rowHDTBBC.MUCUOC_TB = 0;
                await api.fn_tt_muccuoc_tb(this.axios, {
                    "tinhthicong_id": this.tinh.tinh_thicong,
                    "mucuoctb_id": this.thongso_tc.muccuoctb_id,
                }).then(res => {
                    if (res.data.error_code == 'BSS-00000000') {
                        rowHDTBBC.MUCUOC_TB = res.data.data ? res.data.data : 0;
                    } else {
                        this.$toast.error('Lỗi fn_tt_muccuoc_tb ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                        return false
                    }                    
                }).catch(err => {
                    this.$toast.error(err.data.message_detail)
                    return false
                });
                //Hiếu bổ sung thêm trường status: null: không pải đồng bộ, 0: chưa pải đồng bộ
                let status = "0";
                if (Number(this.thongso_ims.dichvuvt_id) == DichVuVienThong.ADSL || Number(this.thongso_ims.dichvuvt_id) == DichVuVienThong.DI_DONG) {
                    status = "0"
                }
                rowHDTBBC.STATUS = status;                
                this.dsHDTBBC.push(rowHDTBBC);
                return true
            } catch (e) { 
                console.log('TaoDuLieu_HDTBBC', e)
                return false 
            }
        },
        TaoDuLieu_HDBC_IMS: async function (themmoi) {
            try {
                // dsHDBCIMS = new HDBC_IMS_DATA();
                let rowHDBC_IMS = {};
                // new CommonUtilities().CopyRow(tcbancheo.LAY_DS_DB_IMS_THEO_TBID(tinh, thuebao_id_tc).Tables[0].Rows[0], rowHDBC_IMS);
                await api.lay_ds_db_ims_theo_tbid(this.axios, {
                    "thuebao_id": this.thuebao_id_tc,
                    "tinhthicong_id": this.tinh.tinh_thicong
                }).then(res => {
                    if (res.data.error_code == 'BSS-00000000') {
                        let ds = res.data.data ? this.UpperCasePropertyList(res.data.data) : [];
                        rowHDBC_IMS = ds[0]
                    } else {
                        this.$toast.error('Lỗi lay_ds_db_ims_theo_tbid ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
                        return false
                    }                    
                }).catch(err => {
                    this.$toast.error(err.data.message_detail)
                    return false
                });
                rowHDBC_IMS.HDTB_ID = this.hdtb_id;

                if (this.thongso_tc.kieuld_id == KieuLapDat.THAYDOI_TT_CUOCGOI_IMS) {
                    rowHDBC_IMS.SL_CUOCGOI = Number(this.thongso_ims.cuocgoi_dt_moi);
                }

                if (this.thongso_tc.kieuld_id == KieuLapDat.THAYDOI_TT_IP_IMS) {
                    rowHDBC_IMS.IP = this.thongso_ims.ip_moi;
                }

                if (this.thongso_tc.kieuld_id == KieuLapDat.THAYDOI_TT_NGUONG_CSD_IMS) {
                    rowHDBC_IMS.NGUONG_CSD = this.thongso_ims.nguong_csd_moi;
                }                                
                this.dsHDBCIMS.push(rowHDBC_IMS)
                return true
            } catch (e) { return false }
        },
        taoDuLieu: async function(themmoi) {
            let result = true
            result = await this.taoDuLieu_HDKH(themmoi);
            if (!result) return false
			result = await this.taoDuLieu_HDTB(themmoi);
            if (!result) return false
			result = await this.taoDuLieu_HDTBDV();
            if (!result) return false
			result = await this.taoDuLieu_HDTB_IMS();
            if (!result) return false
			result = await this.taoDuLieu_CT_TIENHD(true);
            if (!result) return false
            if (this.BANCHEO) {
                result = await this.TaoDuLieuBC(themmoi);
                if (!result) return false
            }
            return true
        },
        themSuaTDTS_IMS: async function(themmoi) {
            try {
                let result = await this.taoDuLieu(themmoi);
                if (!result) return false
                this.ds_para.HD_KHACHHANG = this.dsHDKH;
                this.ds_para.HD_THUEBAO = this.dsHDTB;
                this.ds_para.HDTB_DV = this.dsHDTBDV;
                this.ds_para.HDTB_IMS = this.dsHDTB_IMS;
                this.ds_para.CT_TIENHD = this.dsCTTHD;
                // BANCHEO
                this.ds_para.BANCHEO = this.BANCHEO ? 1 : 0;
                this.ds_para.HD_BANCHEO = this.dsHDBC;
                this.ds_para.HDTB_BANCHEO = this.dsHDTBBC;
                this.ds_para.HDBC_MGWAN = this.dsHDBCMGWAN;
                this.ds_para.HDBC_ADSL = this.dsHDBCADSL;
                this.ds_para.HDBC_CD = this.dsHDBCCD;
                this.ds_para.HDBC_TSL = this.dsHDBCTSL;
                this.ds_para.HDBC_IMS = this.dsHDBCIMS;
                this.ds_para.DANGKY_DVGT = this.dsDK_DVGT_BC;
                let p_tt_nd = {
                    "DONVI_ID": this.tt_nd.donvi_id,
                    "NHANVIEN_ID": this.tt_nd.nhanvien_id,
                    "NGUOI_CN": this.tt_nd.username,
                    "MAY_CN": this.tt_nd.may_cn,
                    "IP_CN": this.tt_nd.ip
                }
                console.log('fnInsUpdateTDTS_IMS > ds_para', this.ds_para)
                await api.fnInsUpdateTDTS_IMS(this.axios, {
                    "p_ds_para": JSON.stringify(this.ds_para),
                    "p_tt_nd": JSON.stringify(p_tt_nd)
                }).then(async res => {
                    if (res.data.data && res.data.error_code === "BSS-00000000") {
                        let result = JSON.parse(res.data.data);
                        if (result.ERROR_CODE == 1) {
                            await this.hienThiDanhSachHDTB();
                            this.setButton(3);
                            this.$toast.success(result.MESSAGE);
                        } else {
                            this.$toast.error(result.MESSAGE);
                        }
                    }
                }).catch(err => {
                    this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                });
            } catch (e) {
                console.log('themSuaTDTS_IMS', e)
            }
        },
        hienThiDanhSachHDTB: async function() {
            let result = 0;
            await api.getDsHDTB_TDTS_IMS(this.axios, this.hdkh_id).then(res => {
                this.ds_thuebao.data = res.data.data ? res.data.data : [];
                if (this.ds_thuebao.data.length > 0) {
                    let tongtien = 0
                    for(let i=0; i<this.ds_thuebao.data.length; i++) {
                        tongtien += this.ds_thuebao.data[i].tongtien;
                    }
                    this.thongso_ims.tongtien = tongtien;
                    result = 3;
                } 
                else {
                    result = 0;
                }
                return result;
            }).catch(err => {
                this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                return result;
            });
        },
        ghiLai: async function() {
            try {
                this.$v.$touch();
                if (this.$v.$invalid) {
                    return;
                }
                this.loading(true);
                let ktra_matb_lap_hd_khac = await this.ktraTBLapHDKhac();
                if (ktra_matb_lap_hd_khac) {
                    this.loading(false);
                    return;
                } 
                if (this.BANCHEO && this.tinh.tinh_thicong && (!this.thongso_ban.muccuoctb_id || !this.thongso_tc.muccuoctb_id)) {
                    this.$toast.error("Bạn chưa chọn mức cước cho thuê bao bán và thi công. Ấn vào kiểu lắp đặt để lấy thông tin");
                    return;
                }                
                let kiemtra_kh = await this.kiemTraDL_KhachHang();
                let kiemtra_tb = await this.kiemTraDL_ThueBao();
                let obj = null;
                this.ds_para = {};
                if (!kiemtra_kh) {
                    this.loading(false);
                    return;
                } 
                if (!kiemtra_tb) {
                    this.loading(false);
                    return;
                } 
                if (this.disabled_control.btn_nhapmoi) {
                    this.huonggiaotn_id = 0;
                    this.quytrinhtn_id = 0;
                    let check_huonggiao = true
                    await api.getHuongGiaoTiepNhan(this.axios, {
                        "vkieuld_id": this.thongso_ims.kieuld_id,
                        "vloaihd_id": this.frm_hopdong.loai_hd_id,
                        "vloaitb_id": this.thongso_ims.loaitb_id,
                        "vmuccuoc_id": 0,
                        "vphanvung_id": this.tt_nd.phanvung_id,
                        "vtocdo_id": 0
                    }).then(res => {
                        if (res.data.data && res.data.data.length > 0) {
                            obj = res.data.data[0];
                            this.huonggiaotn_id = obj.huonggiao_id;
                            this.quytrinhtn_id = obj.quytrinh_id;
                        } else {
                            check_huonggiao = false
                            this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                            this.loading(false);
                            return;
                        }
                    }).catch(err => {
                        check_huonggiao = false
                        this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                    });
                    if (!check_huonggiao) return
                    // #region bancheo
                    if (this.BANCHEO) {                    
                        // DataSet rs_qt = new DanhMuc().LayHuongGiao_TiepNhan(tinh, LoaiHopDong.THAYDOI_THONGSO_IMS, Convert.ToInt32(cboLoaihinhTB.SelectedValue),
                        //  Convert.ToInt32(thongso_tc.tocdo_id.ToString()), Convert.ToInt32(thongso_tc.muccuoc_id.ToString()), thongso_tc.kieuld_id);
                        this.quytrinh_tc_tn = 0;
                        this.huonggiao_tc_tn = 0;
                        let check_huonggiao_bc = true
                        await api.layhuonggiao_tiepnhan_bancheo(this.axios, {
                            "vloaihd_id": this.frm_hopdong.loai_hd_id,
                            "tinhthicong_id": this.tinh.tinh_thicong,
                            "vloaitb_id": this.thongso_ims.loaitb_id,
                            "vtocdo_id": this.thongso_tc.tocdo_id,
                            "vmuccuoc_id": this.thongso_tc.muccuoc_id,
                            "vkieuld_id": this.thongso_tc.kieuld_id,
                        }).then(res => {
                            if (res.data.data && res.data.data.length > 0) {
                                obj = res.data.data[0];
                                this.quytrinh_tc_tn = obj.quytrinh_id;
                                this.huonggiao_tc_tn = obj.huonggiao_id;
                            } else {
                                check_huonggiao_bc = false
                                this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công, bạn hãy liên hệ Admin để xử lý!");
                                this.loading(false);
                                return;
                            }
                        }).catch(err => {
                            check_huonggiao_bc = false
                            this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                            return;
                        });
                        if (!check_huonggiao_bc) return
                    }
                    // #endregion                    
                    if (obj) {
                        this.ds_para.HUONGGIAO_ID = this.huonggiaotn_id;
                        this.ds_para.THEMMOI = true;
                        await this.themSuaTDTS_IMS(true);
                    }
                            
                } else {
                    // #region bancheo
                    if (this.BANCHEO) {
                        // DataSet rs_qt = new DanhMuc().LayHuongGiao_TiepNhan(tinh, LoaiHopDong.THAYDOI_THONGSO_IMS, Convert.ToInt32(cboLoaihinhTB.SelectedValue),
                        //                                                     Convert.ToInt32(thongso_tc.tocdo_id.ToString()), Convert.ToInt32(thongso_tc.muccuoc_id.ToString()), thongso_tc.kieuld_id);
                        this.quytrinh_tc_tn = 0;
                        this.huonggiao_tc_tn = 0;
                        let check_huonggiao_bc = true
                        await api.layhuonggiao_tiepnhan_bancheo(this.axios, {
                            "vloaihd_id": this.frm_hopdong.loai_hd_id,
                            "tinhthicong_id": this.tinh.tinh_thicong,
                            "vloaitb_id": this.thongso_ims.loaitb_id,
                            "vtocdo_id": this.thongso_tc.tocdo_id,
                            "vmuccuoc_id": this.thongso_tc.muccuoc_id,
                            "vkieuld_id": this.thongso_tc.kieuld_id,
                        }).then(res => {
                            if (res.data.data && res.data.data.length > 0) {
                                obj = res.data.data[0];
                                this.quytrinh_tc_tn = obj.quytrinh_id;
                                this.huonggiao_tc_tn = obj.huonggiao_id;
                            } else {
                                check_huonggiao_bc = false
                                this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công, bạn hãy liên hệ Admin để xử lý!");
                                this.loading(false);
                                return;
                            }
                        }).catch(err => {
                            check_huonggiao_bc = false
                            this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                            return;
                        });
                        if (!check_huonggiao_bc) return
                    }
                    // #endregion
                    this.ds_para.HUONGGIAO_ID = this.huonggiaotn_id;
                    this.ds_para.THEMMOI = false;
                    await this.themSuaTDTS_IMS(false);
                }
                this.loading(false);
            } catch (e) {} finally { this.loading(false); }
        },
        huyBo() {
            this.setButton(0);
        },
        xoa: async function() { // Thực hiện khi click nút Xóa
            this.loading(true);
            await api.delHDKH(this.axios, {
                "hdkh_id": this.hdkh_id,
                "loaihd_id": this.frm_hopdong.loai_hd_id
            }).then(res => {
                if (res.data.error_code === "BSS-00000000") {
                    this.$toast.success("Xóa phụ lục thành công !");
                    this.setButton(0);
                    this.ds_thuebao.data = [];
                }
            }).catch(err => {
                this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
            }).finally(()=> this.loading(false));
        },
        xoaTB() { // Thực hiện khi click nút Xóa TB
            if (this.ds_thuebao.data.length == 1)
			{
                this.$toast.error("Chỉ còn 1 thuê bao trên lưới, bạn hãy nhấn nút 'Xóa HĐ'");
			}
			else
			{
                this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xóa thuê bao không ?", {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(!v) {
                        return;
                    }
                    this.loading(true);
                    await api.delHDTB(this.axios, {
                        "vhdtb_id": this.hdtb_id // Tự chế input name chứ nguyên bản là vhdkh_id
                    }).then(async res => {
                        if (res.data.error_code === "BSS-00000000") {
                            await this.hienThiDanhSachHDTB();
                        }
                    }).catch(err => {
                        this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                    }).finally(()=> this.loading(false));
                });
			}
        },
        themMoi_HopDongTB_IMS: async function() {
            await this.taoDuLieu_HDTB(true);
            await this.taoDuLieu_HDTB_IMS();
            await this.taoDuLieu_CT_TIENHD(true);
            await this.taoDuLieu_HDTBDV();
            this.ds_para.HD_THUEBAO = this.dsHDTB;
            this.ds_para.HDTB_DV = this.dsHDTBDV;
            this.ds_para.HDTB_IMS = this.dsHDTB_IMS;
            this.ds_para.CT_TIENHD = this.dsCTTHD;
            let p_tt_nd = {
                "DONVI_ID": this.tt_nd.donvi_id,
                "NHANVIEN_ID": this.tt_nd.nhanvien_id,
                "NGUOI_CN": this.tt_nd.username,
                "MAY_CN": this.tt_nd.may_cn,
                "IP_CN": this.tt_nd.ip
            }
            await api.fnInsUpdateTDTS_IMS_ThemTB(this.axios, {
                "p_ds_para": JSON.stringify(this.ds_para),
                "p_tt_nd": JSON.stringify(p_tt_nd)
            }).then(async res => {
                if (res.data.data && res.data.error_code === "BSS-00000000") {
                    this.$toast.success("Thêm thuê bao thành công!");
                    await this.hienThiDanhSachHDTB();
                }
            }).catch(err => {
                this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
            });
        },
        themTB: async function() {
            this.loading(true);
            let kiemtradl_thuebao = await this.kiemTraDL_ThueBao();
            if (kiemtradl_thuebao) {
                await api.getHuongGiaoTiepNhan(this.axios, {
                    "vkieuld_id": this.thongso_ims.kieuld_id,
                    "vloaihd_id": this.frm_hopdong.loai_hd_id.toString(),
                    "vloaitb_id": this.thongso_ims.loaitb_id,
                    "vmuccuoc_id": 0,
                    "vphanvung_id": this.tt_nd.phanvung_id,
                    "vtocdo_id": 0
                }).then(async res => {
                    if (res.data.data && res.data.data.length > 0) {
                        let obj = res.data.data[0];
                        this.huonggiaotn_id = obj.huonggiao_id;
						this.quytrinhtn_id = obj.quytrinh_id;
                        await this.themMoi_HopDongTB_IMS();
                    } else {
                        this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                        this.loading(false);
                        return;
                    }
                }).catch(err => {
                    this.$toast.error("Có lỗi: " + (err.data.message_detail ? err.data.message_detail : err.data.message));
                });
            }
            this.loading(false);
        },
        thanhToan() {
            try {
                this.userComponentModule = thanhToan
                this.txtMaGDTT = this.thongso_ims.ma_gd;
                // tao component neu chua tao, show popup khi da goi xong ham khoi tao
                if (this.paymentloaded) this.$refs.thanhtoanDialog.show(true)
            } catch (err) {
                console.log('tsbtnThanhToan_Click', err)
            } finally {
            }
        },
        themDSThueBao() {
            // Form này bỏ vì anh Phạm Hoàng bảo trong code cũ form này chưa hoàn chỉnh
            //this.$refs.popupThemDSThueBao_TDTS_IMS.showModal();
        },
        rowSelected_TB: async function(args){
            console.log("rowSelected_TB=" + JSON.stringify(args));
            if (!args) {
                this.hdtb_id = 0;
                this.hdkh_id = 0;
                this.thongso_ims.ip_cu = '';
                this.thongso_ims.ip_moi = '';
                this.thongso_ims.cuocgoi_dt_cu = '';
                this.thongso_ims.cuocgoi_dt_moi = '';
                this.thongso_ims.ten_tb = '';
                this.thongso_ims.ma_tb = '';
                this.thongso_ims.ctdv_id_cu = 0;
                this.thongso_ims.ctdv_id_moi = 0;
                this.thongso_ims.diachi_ld = '';
                this.thongso_ims.tien = 0;
                this.thongso_ims.vat = 0;
                this.thongso_ims.nguong_csd_cu = '';
                this.thongso_ims.nguong_csd_moi = '';
            } else {
                this.hdtb_id = args.data.hdtb_id;
                this.hdkh_id = args.data.hdkh_id;
                if (args.thuebao_id) 
                {
                    this.thuebao_id = args.data.thuebao_id;
                }
                this.thongso_ims.ma_tb = args.data.ma_tb;
                this.thongso_ims.ten_tb = args.data.ten_tb;
                this.thongso_ims.ghichu = args.data.ghichu;
                this.diachi_tb = args.data.diachi_tb;
                this.diachi_ld = args.data.diachi_ld;
                this.thongso_ims.diachi_ld = args.data.diachi_ld;
                this.thongso_ims.dichvuvt_id = args.data.dichvuvt_id;
                this.thongso_ims.loaitb_id = args.data.loaitb_id;
                this.thongso_ims.kieuld_id = args.data.kieuld_id;
                if (args.data.kieuld_id)
                {
                    this.doituong_id = args.data.doituong_id;
                }
                else
                {
                    this.doituong_id = 0;
                }
                this.donvi_id = args.data.donvi_id;
                this.thongso_ims.cuocgoi_dt_cu = args.data.sl_cuocgoi_cu;
                this.thongso_ims.cuocgoi_dt_moi = args.data.sl_cuocgoi_moi;
                this.thongso_ims.ip_cu = args.data.ip_cu;
                this.thongso_ims.ip_moi = args.data.ip_moi;
                if (args.data.ctdv_id_cu)
                {
                    this.thongso_ims.ctdv_id_cu = args.data.ctdv_id_cu;
                }
                else
                {
                    this.thongso_ims.ctdv_id_cu = 0;
                }
                if (args.data.ctdv_id_moi)
                {
                    this.thongso_ims.ctdv_id_moi = args.data.ctdv_id_moi;
                }
                else
                {
                    this.thongso_ims.ctdv_id_moi = 0;
                }
                this.thongso_ims.nguong_csd_cu = args.data.nguong_csd_cu;
                this.thongso_ims.nguong_csd_moi = args.data.nguong_csd_moi;

                // #region bancheo
                if (this.BANCHEO)
                {
                    // new DanhMuc().Gan_thongso_bancheo_hdtb(hdtb_id, ref thongso_ban, ref thongso_tc);
                    await this.Gan_thongso_bancheo_hdtb(this.hdtb_id)
                    this.tinh = await this.LAY_TINHTHICONG_THEO_THUEBAOID(this.thuebao_id);
                    
                    if (this.tinh && this.tinh.tinh_thicong > 0) {
                        // this.thuebao_id_tc = tcbancheo.LAY_THUEBAOID_THICONG(tinh, thuebao_id);
                        
                        let ds = await this.lay_thuebaoid_thicong(this.thuebao_id, this.tinh.tinh_thicong)
                        if (ds.length > 0) {
                            this.thuebao_id_tc = ds[0].thuebao_kn_id > 0 ? ds[0].thuebao_kn_id : 0
                        }
                    }  
                }
                // #endregion
            }
        },
        async lay_thuebaoid_thicong(thuebao_id, tinh_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_thuebaoid_thicong(this.axios, thuebao_id, tinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async Gan_thongso_bancheo_hdtb(hdtb_id){
            let ds=await this.lay_thongso_thuebao_hdbc(hdtb_id)
            if(ds.length>0){
                let dvvt_id = ds[0].dichvuvt_id
                if (dvvt_id != DichVuVienThong.HOINGHI_TRUYENHINH &&
                    dvvt_id != DichVuVienThong.ANTOAN_BAOMAT_TT &&
                    dvvt_id != DichVuVienThong.DICHVU_CNTT &&
                    dvvt_id != DichVuVienThong.TRUNGTAM_DULIEU){

                        this.thongso_ban.chuquanld_id = ds[0].chuquanld_ban?ds[0].chuquanld_ban:0
                        this.thongso_ban.congnghe_id = ds[0].congnghe_ban?ds[0].congnghe_ban:0 
                        this.thongso_ban.loaikenh_id = ds[0].loaikenh_ban?ds[0].loaikenh_ban:0
                        this.thongso_ban.loaimd_id = ds[0].loaimd_ban?ds[0].loaimd_ban:0 
                        this.thongso_ban.ma_doicap = ds[0].madoicap_ban?ds[0].madoicap_ban:0
                        this.thongso_ban.chuquan_id = ds[0].chuquan_ban?ds[0].chuquan_ban:0
                        this.thongso_ban.ma_tn = ds[0].matn_ban?ds[0].matn_ban:0 
                        this.thongso_ban.thietbidc_id = ds[0].thietbidau_ban?ds[0].thietbidau_ban:0 
                        this.thongso_ban.thietbidc_id_cuoi = ds[0].thietbicuoi_ban?ds[0].thietbicuoi_ban:0
                        this.thongso_ban.thoihan_id = ds[0].thoihan_ban?ds[0].thoihan_ban:0
                        this.thongso_ban.tocdo_id = ds[0].tocdo_ban?ds[0].tocdo_ban:0 
                        this.thongso_ban.tocdo_isp = ds[0].tocdoisp_ban?ds[0].tocdoisp_ban:0
                        this.thongso_ban.tocdo_nix = ds[0].tocdonix_ban?ds[0].tocdonix_ban:0

                        this.thongso_ban.tocdo_pir_id = ds[0].tocdopir_ban?ds[0].tocdopir_ban:0
                        this.thongso_ban.phanloai_id = ds[0].phanloai_id?ds[0].phanloai_id:0 
                        this.thongso_ban.phantach_id = ds[0].phantach_id?ds[0].phantach_id:0
                    
                        
                        this.thongso_ban.tocdothuc_id = ds[0].tocdothuc_ban?ds[0].tocdothuc_ban:0
                        this.thongso_ban.trangbi_id = ds[0].trangbi_ban?ds[0].trangbi_ban:0


                        this.thongso_tc.chuquan_id = ds[0].chuquan_tc?ds[0].chuquan_tc:0
                        this.thongso_tc.chuquanld_id = ds[0].chuquanld_tc?ds[0].chuquanld_tc:0
                        this.thongso_tc.congnghe_id = ds[0].congnghe_tc?ds[0].congnghe_tc:0
                        this.thongso_tc.loaikenh_id = ds[0].loaikenh_tc?ds[0].loaikenh_tc:0
                        this.thongso_tc.loaimd_id = ds[0].loaimd_tc?ds[0].loaimd_tc:0
                        this.thongso_tc.ma_doicap = ds[0].madoicap_tc?ds[0].madoicap_tc:0
                        this.thongso_tc.ma_tn = ds[0].matn_tc?ds[0].matn_tc:0

                        this.thongso_tc.thietbidc_id = ds[0].thietbidau_tc?ds[0].thietbidau_tc:0
                        this.thongso_tc.thietbidc_id_cuoi = ds[0].thietbicuoi_tc?ds[0].thietbicuoi_tc:0
                        this.thongso_tc.thoihan_id = ds[0].thoihan_tc?ds[0].thoihan_tc:0
                        this.thongso_tc.tocdo_id = ds[0].tocdo_tc?ds[0].tocdo_tc:0
                        this.thongso_tc.tocdo_isp = ds[0].tocdoisp_tc?ds[0].tocdoisp_tc:0
                        this.thongso_tc.tocdo_nix = ds[0].tocdonix_tc?ds[0].tocdonix_tc:0
                        this.thongso_tc.tocdo_pir_id = ds[0].tocdopir_tc?ds[0].tocdopir_tc:0

                        this.thongso_tc.tocdothuc_id = ds[0].tocdothuc_tc?ds[0].tocdothuc_tc:0
                        this.thongso_tc.trangbi_id = ds[0].trangbi_tc?ds[0].trangbi_tc:0
                }
                this.thongso_ban.muccuoc_id = ds[0].muccuoc_ban?ds[0].muccuoc_ban:0
                this.thongso_ban.muccuoctb_id = ds[0].muccuoctb_ban?ds[0].muccuoctb_ban:0
                this.thongso_ban.kieuld_id = ds[0].kieuld_ban?ds[0].kieuld_ban:0
                this.thongso_ban.doituong_id = ds[0].doituong_ban?ds[0].doituong_ban:0
                this.thongso_tc.kieuld_id = ds[0].kieuld_tc?ds[0].kieuld_tc:0
                this.thongso_tc.doituong_id = ds[0].doituong_tc?ds[0].doituong_tc:0
                this.thongso_tc.muccuoc_id = ds[0].muccuoc_tc?ds[0].muccuoc_tc:0
                this.thongso_tc.muccuoctb_id = ds[0].muccuoctb_tc?ds[0].muccuoctb_tc:0 

                this.thongso_tc.kieutra_id = ds[0].kiemtra_tc?ds[0].kiemtra_tc:0
                this.thongso_ban.kieutra_id = ds[0].kiemtra_ban?ds[0].kiemtra_ban:0

                if (dvvt_id == DichVuVienThong.MEGAWAN || dvvt_id == DichVuVienThong.METRONET || dvvt_id == DichVuVienThong.TSL)
                {
                    this.thongso_ban.mhkn_id = ds[0].mhkn_id?ds[0].mhkn_id:0 
                    this.thongso_ban.gdkn_id = ds[0].gdkn_id?ds[0].gdkn_id:0
                    this.thongso_ban.yc_baove = ds[0].yc_baove?ds[0].yc_baove:0
                    this.thongso_ban.tocdo_pop = ds[0].tocdo_pop?ds[0].tocdo_pop:0
                    this.thongso_ban.gdkn_pop_id = ds[0].gdkn_pop_id?ds[0].gdkn_pop_id:0
                    this.thongso_ban.cauhinh_dt_id = ds[0].cauhinh_dt_id?ds[0].cauhinh_dt_id:0
                    this.thongso_ban.kieu_duplex_id = ds[0].kieu_duplex_id?ds[0].kieu_duplex_id:0
                    this.thongso_ban.kieukn_id = ds[0].kieukn_id?ds[0].kieukn_id:0

                    this.thongso_ban.mtu_size = ds[0].mtu_size?ds[0].mtu_size:0
                    this.thongso_ban.auto_nego = ds[0].auto_nego?ds[0].auto_nego:0
                    this.thongso_ban.sncp = ds[0].sncp?ds[0].sncp:0
                    this.thongso_ban.cap_chinh = ds[0].cap_chinh?ds[0].cap_chinh:0
                    this.thongso_ban.cap_duphong = ds[0].cap_duphong?ds[0].cap_duphong:0
                    this.thongso_ban.diem_pop = ds[0].diem_pop?ds[0].diem_pop:0
                    this.thongso_ban.wan_ip = ds[0].wan_ip?ds[0].wan_ip:0
                    this.thongso_ban.lan_ip = ds[0].lan_ip?ds[0].lan_ip:0
                    this.thongso_ban.ce_ip = ds[0].ce_ip?ds[0].ce_ip:0
                    this.thongso_ban.tt_ip = ds[0].tt_ip?ds[0].tt_ip:0
                    this.thongso_ban.sl_ip = ds[0].sl_ip?ds[0].sl_ip:0
                    this.thongso_ban.ten_trunk_nni = ds[0].ten_trunk_nni?ds[0].ten_trunk_nni:0
                    this.thongso_ban.so_as = ds[0].so_as?ds[0].so_as:0
                    this.thongso_ban.hqos = ds[0].hqos?ds[0].hqos:0
                    this.thongso_ban.cos = ds[0].cos?ds[0].cos:0
                    this.thongso_ban.svlan = ds[0].svlan?ds[0].svlan:0
                    this.thongso_ban.cvlan = ds[0].cvlan?ds[0].cvlan:0
                    this.thongso_ban.vlan_transparent = ds[0].vlan_transparent?ds[0].vlan_transparent:0
                    this.thongso_ban.tt_port = ds[0].tt_port?ds[0].tt_port:0


                    this.thongso_tc.mhkn_id = ds[0].mhkn_id?ds[0].mhkn_id:0
                    this.thongso_tc.gdkn_id = ds[0].gdkn_id?ds[0].gdkn_id:0
                    this.thongso_tc.yc_baove = ds[0].yc_baove?ds[0].yc_baove:0
                    this.thongso_tc.tocdo_pop = ds[0].tocdo_pop?ds[0].tocdo_pop:0
                    this.thongso_tc.gdkn_pop_id = ds[0].gdkn_pop_id?ds[0].gdkn_pop_id:0
                    this.thongso_tc.cauhinh_dt_id = ds[0].cauhinh_dt_id?ds[0].cauhinh_dt_id:0
                    this.thongso_tc.kieu_duplex_id = ds[0].kieu_duplex_id?ds[0].kieu_duplex_id:0
                    this.thongso_tc.kieukn_id = ds[0].kieukn_id?ds[0].kieukn_id:0

                    this.thongso_tc.mtu_size = ds[0].mtu_size?ds[0].mtu_size:0
                    this.thongso_tc.auto_nego = ds[0].auto_nego?ds[0].auto_nego:0
                    this.thongso_tc.sncp = ds[0].sncp?ds[0].sncp:0
                    this.thongso_tc.cap_chinh = ds[0].cap_chinh?ds[0].cap_chinh:0
                    this.thongso_tc.cap_duphong = ds[0].cap_duphong?ds[0].cap_duphong:0
                    this.thongso_tc.diem_pop = ds[0].diem_pop?ds[0].diem_pop:0
                    this.thongso_tc.wan_ip = ds[0].wan_ip?ds[0].wan_ip:0
                    this.thongso_tc.lan_ip = ds[0].lan_ip?ds[0].lan_ip:0
                    this.thongso_tc.ce_ip = ds[0].ce_ip?ds[0].ce_ip:0
                    this.thongso_tc.tt_ip = ds[0].tt_ip?ds[0].tt_ip:0
                    this.thongso_tc.sl_ip = ds[0].sl_ip?ds[0].sl_ip:0
                    this.thongso_tc.ten_trunk_nni = ds[0].ten_trunk_nni?ds[0].ten_trunk_nni:0
                    this.thongso_tc.so_as = ds[0].so_as?ds[0].so_as:0
                    this.thongso_tc.hqos = ds[0].hqos?ds[0].hqos:0
                    this.thongso_tc.cos = ds[0].cos?ds[0].cos:0
                    this.thongso_tc.svlan = ds[0].svlan?ds[0].svlan:0
                    this.thongso_tc.cvlan = ds[0].cvlan?ds[0].cvlan:0
                    this.thongso_tc.vlan_transparent = ds[0].vlan_transparent?ds[0].vlan_transparent:0
                    this.thongso_tc.tt_port = ds[0].tt_port?ds[0].tt_port:0

                    this.thongso_ban.loai_ip = await this.lay_ds_loaiip_theo_hdtb_id(hdtb_id)
                    this.thongso_tc.loai_ip = this.thongso_ban.loai_ip;
                    this.thongso_ban.kieu_nghiemthu = await this.lay_ds_kieunt_theo_hdtb_id(hdtb_id)
                    this.thongso_tc.kieu_nghiemthu = this.thongso_ban.kieu_nghiemthu;
                }

            }
        },
        async lay_thongso_thuebao_hdbc(hdtb_id){
            try{
                let response = await api.lay_thongso_thuebao_hdbc(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_loaiip_theo_hdtb_id(hdtb_id){
            try{
                let response = await api.lay_ds_loaiip_theo_hdtb_id(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_kieunt_theo_hdtb_id(hdtb_id){
            try{
                let response = await api.lay_ds_kieunt_theo_hdtb_id(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async LAY_TINHTHICONG_THEO_THUEBAOID(thuebao_id){
            try{
                let response = await api.lay_tinh_theo_thuebaoid(this.axios, thuebao_id)
                
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data[0]
                }else{
                    return {}
                }
            }catch(e){
                return {}
            }
        },
        lay_danhba_theo_thuebaoid: async function (input){
            try{
                let response = await api.lay_danhba_theo_thuebaoid(this.axios, input)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    thiss.$toast.error('Không tìm thấy danh bạ thuê bao IMS bán chéo: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
                    return []
                }
            }catch(e){
                return []
            }
        },
        validateNumber(event) {
            var theEvent = event || window.event;
            // Handle paste
            if (theEvent.type === 'paste') {
                key = event.clipboardData.getData('text/plain');
            } else {
            // Handle key press
                var key = theEvent.keyCode || theEvent.which;
                key = String.fromCharCode(key);
            }
            var regex = /[0-9]|\./;
            if( !regex.test(key) ) {
                theEvent.returnValue = false;
                if(theEvent.preventDefault) theEvent.preventDefault();
            }
        },
        thayDoiCTDVMoi() {
            if (this.thongso_ims.loaitb_id == 77 && this.thongso_ims.ctdv_id_moi == 2) {
                this.disabled_control.txt_ip_moi = false;
            } else if (this.thongso_ims.loaitb_id == 77 && this.thongso_ims.ctdv_id_moi != 2 && this.thongso_ims.kieuld_id == this.kieuld.thaydoi_tt_ip_ims) {
                
            } else {
                this.disabled_control.txt_ip_moi = true;
            }
        },
        async paymentcreated(val) {
            this.paymentloaded = true
            this.$refs.thanhtoanDialog.show(true)
        },
        Popup(modalId) {
            this.$bvModal.show(modalId)
        },
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId)
            this.popupComponent = null
        },
        async popupClosed(val) {
            if (!val) {
                this.ClosePopup('popupComponentMGs')
                return
            }
            this.loading(true)
            try {
                switch (this.popupComponentName) {
                    case 'SearchAccount':
                        console.log('SearchAccount', val)
                        if (val.ma_tb) this.thongso_ims.ma_tb = val.ma_tb
                        if (this.thongso_ims.ma_tb && this.thongso_ims.ma_tb.trim() != '') await this.maTB_KeyPress()
                        //this.$refs.ma_tb.focus();
                        break
                }
            } catch (ex) {
            } finally {
                this.ClosePopup('popupComponentMGs')
                this.loading(false)
            }
        }
    }
}
</script>
