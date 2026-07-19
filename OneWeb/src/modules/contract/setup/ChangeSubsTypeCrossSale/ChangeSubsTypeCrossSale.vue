<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="false"/>
        <div class="page-content cdlh-top">
            <div class="box-form padt8 padb8 fw6">
                <div class="row">
                    <div class="col-sm-2 col-12">
                        
                    </div>
                    <div class="col-sm-2 col-12">
                      
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">Kiểu PL</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_kieu_pl">
                                    <select2 :settings="{ dropdownParent: $refs['ds_kieu_pl'] }" ref="cboKieuPL"
                                        v-model="kieu_pl_selected" class="select2"
                                        :options="ds_kieu_pl.map(e=> ({id: e.kieuhd_id, text: e.kieu_hd}))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="checkCTV" @change="changeCheckCTV" title="Tích chọn để tìm kiếm cộng tác viên">
                                    <span class="name bold">Cộng tác viên</span>
                                </div>
                            </div>
                            <div class="value">
                                <input type="text" ref="inputCTV" class="form-control bold" @keypress="evt=>evt.preventDefault()" :value="ctv.ten_ctv" @change="e=>ctv.ten_ctv=e.target.value" :disabled="!checkCTV">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="checkNguoiGT" @change="checkCheckNguoiGT" title="Tích chọn để tìm kiếm người giới thiệu hợp đồng">
                                    <span class="name bold">Người giới thiệu</span>
                                </div>
                            </div>
                            <div class="value">
                                <input type="text" ref="inputNguoiGT" class="form-control bold" @keypress="evt=>evt.preventDefault()" :disabled="!checkNguoiGT" :value="nguoi_gt.ten_nguoi_gt" @change="e=>nguoi_gt.ten_nguoi_gt=e.target.value">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap bold">
                                Tổng tiền
                            </div>
                            <div class="value cdlh-disabled">
                                <vue-numeric class="form-control tright bold" separator=","  :value="input_tongtien" @change="e=>input_tongtien=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <ThongTinPhieuYeuCau ref="thongTinPhieuYeuCau"
                    @changeDichVu="changeDichVu"
                    @changeLoaiHinhTBCu="changeLoaiHinhTBCu"
                    @changeKieuYC="changeKieuYC"
                    @changeMucCuocTB="changeMucCuocTB"
                    @onEnterMaGD="onEnterMaGD"
                    @changeCheckKhuyenMai="changeCheckKhuyenMai"
                    @changeCheckTraTruoc="changeCheckTraTruoc"
                    :dsKM_HDTB="dsKM_HDTB"
                    :hdtb_id="hdtb_id"
                    :loaihinhtb_id="loaihinhtb_id"
                    @btnChiTietKMClick="btnChiTietKMClick"
                    @btnChiTietTCClick="btnChiTietTCClick"
                    @formCloseChiTietKM="formCloseChiTietKM"
                    @clickDiemDauTSL="clickDiemDauTSL"
                    @clickDiemCuoiTSL="clickDiemCuoiTSL"
                    @clickThongSoTC="clickThongSoTC"

                />
                <ThongTinKhachHang ref="thongTinKhachHang"
                    @onAcceptDiaChiLD="onAcceptDiaChiLD"
                    @onAcceptDiaChiTB="onAcceptDiaChiTB"
                />
            </div>
            
            <!-- Layout biding theo dịch vụ -->
            <CoDinh v-if="dichvu_vt_selected==1" ref="coDinh"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @onEnterMaTB="onEnterMaTB"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @acceptChonSoChinh="acceptChonSoChinh"
                @changeLoaiSoMoi="changeLoaiSoMoi"

            />
            <BangRongCoDinh v-else-if="dichvu_vt_selected==DichVuVienThong.ADSL||dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT" ref="bangRongCoDinh"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @onEnterMaTB="onEnterMaTB"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeGoiCuocAdsl="changeGoiCuocAdsl"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @changeMucCuoc="changeMucCuoc"
                @clickMaTN_ADSL="clickMaTN_ADSL"
                @clickTTTC="clickTTTC_ADSL"
            />
            <Megawan v-else-if="dichvu_vt_selected==DichVuVienThong.METRONET||dichvu_vt_selected==DichVuVienThong.MEGAWAN||dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM" ref="megawan"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @onEnterMaTB="onEnterMaTB"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @changeLoaiTBWanMoi="changeLoaiTBWanMoi"
                @changeTocDoCirWan="changeTocDoCirWan"
            />
            <KenhThueRieng v-else-if="dichvu_vt_selected==DichVuVienThong.TSL||dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM" ref="kenhThuRieng"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @onEnterMaTB="onEnterMaTB"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @changeTocDoKenhTSLMoi="changeTocDoKenhTSLMoi"
                @layLoaiKenh="layLoaiKenh"
            />
            <IMS v-else-if="dichvu_vt_selected==DichVuVienThong.IMS" ref="ims"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @onEnterMaTB="onEnterMaTB"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @clickMaTN_IMS="clickMaTN_IMS"
            />

            <div class="row">
                <MuaThietBi ref="muaThietBi"
                    :dsTenTB="dsTenTB"
                    :dsCTM_TBI="dsCTM_TBI"
                    :loaitb_id="loaihinhtbcu_id"
                    @acceptPopupCTMuaThietBi="acceptPopupCTMuaThietBi"
                />
                <ChiTietTienTheoKhoanMuc ref="chiTietTienTheoKhoanMuc"
                    :ds_tien_khoanmuc="ds_tien_khoanmuc"
                    @acceptTienKM="acceptTienKM"
                />
            </div>

            <div class="box-form">
                <div class="legend-title">Danh sách thuê bao</div>
                <KDataGrid
                    ref="gridDs"
                    :columns="columns"
                    :dataSource="dataSources"
                    :enable-paging-server="false"
                    :allowPaging="false"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    :enabledSelectFirstRow="true"
                    @onRowSelected="onSelectedRow"
                />
            </div>
        </div>
        <!-- Modal -->
        <ThongTinHenKhachHangModal ref="thongTinHenKhachHangModal" :hdkh_id="hdkh_id" :quyenduoc_hen="1"/>
        <ChonCongTacVienModal ref="chonCongTacVienModal" @accept="acceptCTV"/>
        <ChonNguoiGTModal ref="chonNguoiGTModal" @accept="acceptNguoiGT"/>
        <ThongTinLienHeModal ref="thongTinLienHeModal" :hdkh_id="hdkh_id" :khachhang_id="khachhang_id" :ten_kh="ten_kh" :diachi="diachi_kh"/>
        <EmailModal ref="emailModal" :hdkh_id="hdkh_id" :khachhang_id="khachhang_id"/>
        <InHoSo ref="popupInHoSo" :params="popupInHopDong_Param"/>
        <!-- <ThongSoTSLModal ref="thongSoTSL"/> -->
        <ChonTinhTSLModal ref="chonTinhTSL" @accept="acceptChonTinhTC_TSL"/>
        <ChonTinhModal ref="chonTinhModal" @accept="acceptChonTinhTC"/>
        <DanhSachThueBaoCungMaModal ref="danhSachThueBaoCungMaModal" @accept="acceptThuBaoCungMa" :data="dsTBCungMa"/>
        <ThongSoCoDinhModal ref="thongSoCoDinhModal"
            :khachhang_id="Number(khachhang_id)"
            :hdkh_id="Number(hdkh_id)"
            :thongso_ban="thongso_ban"
            :thongso_tc="thongso_tc"
            @accept="acceptThongSoCoDinh"
        />
        <!-- <ThongSoWanModal ref="thongSoWanModal"/> -->
        <TocDoMucCuocADSLModal ref="tocDoMucCuocADSLModal"
            :khachhang_id="Number(khachhang_id)"
            :hdkh_id="Number(hdkh_id)"
            :thongso_ban="thongso_ban"
            :thongso_tc="thongso_tc"
            @accept="acceptTocDoMucCuocADSL"
        />
        <frmThongSoWan ref="ref_frmThongSoWan" @closeThongSoWan="closeHienThiChonThongSo"/>
        <frmThongSoTSL ref="thongSoTSL" @chapnhan="closefrmThongSoTSL" />

    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/mirana/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ThongTinPhieuYeuCau from './ThongTinPhieuYeuCau.vue'
import ThongTinKhachHang from './ThongTinKhachHang.vue'
import MuaThietBi from './MuaThietBi.vue'
import ChiTietTienTheoKhoanMuc from './ChiTietTienTheoKhoanMuc.vue'
import CoDinh from './layouts/CoDinh.vue'
import BangRongCoDinh from './layouts/BangRongCoDinh.vue'
import Megawan from './layouts/Megawan.vue'
import KenhThueRieng from './layouts/KenhThueRieng.vue'
import IMS from './layouts/IMS.vue'
import ThongTinHenKhachHangModal from '../SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import ChonCongTacVienModal from '../SurveyRequest/popups/ChonCongTacVien/ChonCongTacVienModal.vue'
import ChonNguoiGTModal from '../SurveyRequest/popups/ChonNguoiGT/ChonNguoiGTModal.vue'
import moment from 'moment'
import ThongTinLienHeModal from '../ChangeSubsType/popups/ThongTinLienHeModal.vue'
import EmailModal from '../ChangeSubsType/popups/EmailModal.vue'
import {DichVuVienThong, LoaiHopDong, KHOANMUC_TT, TrangThaiHD, KIEU_HD,
        TRANGTHAI_DONGBO, LoaiHinhTB , TrangThaiTB, LOAISO_ISDN, KieuLapDat,
        ChiTietDV_IMS, DUNG_THU, LOAI_KV, LOAI_DV, LOAITBI_ADSL} from './ThamSo'
import ChangeSubsTypeCrossSaleAPI from './ChangeSubsTypeCrossSaleAPI'
import InHoSo from '../../../print/InHopDong/InHopDong.vue'
import ThongSoTSLModal from './popups/ThongSoTSLModal.vue'
import ChonTinhTSLModal from './popups/ChonTinhTSLModal.vue'
import ChonTinhModal from './popups/ChonTinhModal.vue'
import DanhSachThueBaoCungMaModal from '../ChangeSubsType/popups/DanhSachThueBaoCungMaModal.vue'
import {newThongSoThueBao, createObjectHDKH, createObjectHDTB, createObjectHDTB_CD, createObjectHDTB_IMS,
 createObjectHDTB_ADSL, createObjectHDTB_MGWAN, createObjectHDTB_TSL, createObjectHD_BANCHEO,
 createObjectHDTB_BANCHEO, createObjectHDTBBC_CD, createObjectHDTBBC_IMS, createObjectHDTBBC_ADSL, createObjectHDTBBC_MGWAN,
 createObjectHDTBBC_TSL} from './Utils'
import ThongSoCoDinhModal from './popups/ThongSoCoDinhModal.vue'
import ThongSoWanModal from './popups/ThongSoWanModal.vue'
import TocDoMucCuocADSLModal from './popups/TocDoMucCuocADSLModal.vue'

import frmThongSoWan from '@/modules/contract/setup/ChangeMegaWanConfigCrossSell/popups/frmThongSoWan.vue'
import frmThongSoTSL from "@/modules/contract/setup/SetupChangeTSLSpeedCrossSale/Popups/frmThongSoTSL/frmThongSoTSL.vue"

export default {
    name:'ChangeSubsTypeCrossSale',
    components:{
        breadcrumb,
        ActionTop,
        KDataGrid,
        ThongTinPhieuYeuCau,
        ThongTinKhachHang,
        MuaThietBi,
        ChiTietTienTheoKhoanMuc,
        CoDinh,
        BangRongCoDinh,
        Megawan,
        KenhThueRieng,
        IMS,
        ThongTinHenKhachHangModal,
        ChonCongTacVienModal,
        ChonNguoiGTModal,
        ThongTinLienHeModal,
        EmailModal,
        InHoSo,
        ThongSoTSLModal,
        ChonTinhTSLModal,
        ChonTinhModal,
        DanhSachThueBaoCungMaModal,
        ThongSoCoDinhModal,
        ThongSoWanModal,
        TocDoMucCuocADSLModal,
        frmThongSoWan,
        frmThongSoTSL
    },
    data(){
        return {
            header: {
                title: "Thay đổi loại hình thuê bao (bán chéo)",
                list: [
                    { 
                        name: "Bán chéo", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Thay đổi loại hình thuê bao",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus',
                    visible: true,
                    type:1
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save',
                    visible: true,
                    type:1
                },
                {
                    id:'huy',
                    name:'Hủy',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove',
                    visible: true,
                    type:1
                },
                {
                    id:'xoa_hd',
                    name:'Xóa HĐ',
                    active: true,
                    icon_class:'one-trash',
                    visible: true,
                    type:1
                },
                {
                    id:'thanhtoan',
                    name:'Thanh toán',
                    active: true,
                    icon_class:'nc-icon-glyph shopping_credit-card',
                    tooltip:'Thanh toán hợp đồng',
                    visible: true,
                    type:1
                },
                //Bổ sung droplist các control con
                {
                    id:'hoso',
                    name:'Hồ sơ',
                    active: true,
                    icon_class:'nc-icon-glyph files_single-content-02',
                    tooltip:'In hồ sơ',
                    visible: true,
                    type:1
                },
                {
                    id:'goidadv',
                    name:'Gói đa DV',
                    active: true,
                    icon_class:'one-file-edit',
                    tooltip:'Đăng ký gói đa dịch vụ',
                    visible: true,
                    type:1
                },
                //không dùng nữa
                {
                    id:'giaophieu',
                    name:'Giao phiếu',
                    active: true,
                    icon_class:'one-file-arrow-right1',
                    visible: true,
                    type:1
                },
                {
                    id:'themtb',
                    name:'Thêm TB',
                    active: true,
                    icon_class:'one-mobile-plus',
                    tooltip:'Thêm thuê bao',
                    visible: true,
                    type:1
                },
                {
                    id:'xoatb',
                    name:'Xóa TB',
                    active: true,
                    icon_class:'one-phone-remove',
                    tooltip:'Xóa thuê bao',
                    visible: true,
                    type:1
                },
                //Đóng không dùng
                // {
                //     id:'catmo',
                //     name:'Cắt mở',
                //     active: true,
                //     icon_class:'nc-icon-glyph arrows-1_simple-right',
                //     visible: false,
                //     type:1
                // },
                {
                    id:'capnhatdb',
                    name:'Cập nhật DB',
                    active: false,
                    icon_class:'one-reload1',
                    visible: true,
                    type:1
                },
                {
                    id:'lienhe',
                    name:'Liên hệ',
                    active: true,
                    icon_class:'nc-icon-glyph business_contacts',
                    tooltip:'Thông tin liên hệ',
                    visible: true,
                    type:1
                },
                {
                    id:'email',
                    name:'Email',
                    active: true,
                    icon_class:'one-email',
                    visible: true,
                    type:1
                },
                {
                    id:'henld',
                    name:'Hẹn LĐ',
                    active: true,
                    icon_class:'one-calendar-check',
                    tooltip:'Hẹn lắp đặt',
                    visible: true,
                    type:1
                },
                {
                    id:'sua_km',
                    name:'Sửa KM',
                    active: true,
                    icon_class:'',
                    tooltip:'Sửa khuyến mại, đặt cọc của hợp đồng',
                    visible: false,
                    type:1
                },
            ],
            columns:[
                {
                    fieldName: "MA_GD",
                    headerText: "Mã GD",
                    allowFiltering: true
                },
                {
                    fieldName: "MA_TB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true
                },
                {
                    fieldName: "TEN_TB",
                    headerText: "Tên thuê bao",
                    allowFiltering: true
                },
                {
                    fieldName: "DIACHI_LD",
                    headerText: "Địa chỉ LĐ",
                    allowFiltering: true
                },
                {
                    fieldName: "TRANGTHAI_VINA",
                    headerText: "Trạng thái đồng bộ",
                    allowFiltering: true
                }
            ],
            dataSources:[],
            dichvu_vt_selected:1,
            hdkh_id:0,
            checkCTV:false,
            checkNguoiGT:false,
            ctv:{
                ctv_id:null,
                ten_ctv:'',
                ma_ctv:'',
                loainv_id:-1,
                donvi_id:-1
            },
            nguoi_gt:{
                nguoi_gt_id:null,
                ten_nguoi_gt:'',
                ma_nguoi_gt:'',
                loainv_id:-1,
                donvi_id:-1
            },
            ds_kieu_pl:[],
            kieu_pl_selected:null,
            input_tongtien:'0',
            khachhang_id:0,
            iKenhTN: 1,//Keenh tieep nhan hop dog (trong bangr kenh_tn)
            iKieu: 0,//0: lap hop dong, 1: tra cuu, 2: sua km,
            ds_tien_khoanmuc:[],
            thamso:{
                //Default không bắt buộc nhập thông tin tính cước
                nhap_tttc:0,
                ////Default không nhập tốc độ thực adsl
                nhap_tocdo_thuc_adsl:0,

                khong_thaydoi_diachi_ld:0,
                // THam số ko bắt buộc phải nhập cộng tác viên
                khong_batbuoc_ctv:0,
                //Tham số bắt buộc nhập khu vực
                nhap_nvkd:0,
                // 
                batbuoc_gioithieu_loaihinh:0,

                //Thực hiện bắt buộc chọn đăng ký trả trươc - y/c mail HYN
                dk_tratruoc:0,
                nguoidung_boqua_tratruoc:0,

                //KNV:tham số tự động sinh số ảo
                TUDONG_SINH_SOAO:false,
                //Dùng cho Hni sinh ma GD theo don vi quan ly cua ng dung
                ts_sinhma_gd_theo_donvi:false,
                //
                batbuoc_chon_dacdiem_dcld:false

            },
            ds_loaihinh_tb:[],
            ds_goi_kt:[],
            dichvuvt_id:1,
            dsKM_HDTB:[],
            dtCTKM:[],
            dsDC_HDTB:[],
            loaihinhtb_id:0,
            hdtb_id:0,
            dsCTM_TBI:[],
            dsTenTB:[],
            loaihinhtbcu_id:0,
            dataDiaChiLD:{
                diachi_id:0,
                tinh_id:0,
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                sonha:'',
                //
                block:'',
                tang:'',
                sophong:'',
                motathem:'',
                diachi:'',

                kinhdo:0,
                vido:0
            },
            dataDiaChiTB:{
                diachi_id:0,
                tinh_id:0,
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                sonha:'',
                //
                block:'',
                tang:'',
                sophong:'',
                motathem:'',
                diachi:'',

                kinhdo:0,
                vido:0
            },
            loaitb_id:0,
            donviql_id:0,
            khuvuc_id:0,
            thuebao_id:0,
            checkQuyen:0,
            tt_nd:null,
            hdtb_id_cha:0,
            thuebao_id_cha:0,
            loaisochinh:0,
            hdkh_cha_id:0,

            //Hiếu bổ sung phần khai báo cho hàm laytien --17/09/2010
            tien_tbi: 0,
            vat_tbi: 0,
            tien_dv: 0,
            vat_dv: 0,
            tien_kmld: 0,
            vat_kmld: 0,
            tyle_kmld: 0,
            tien_kmtbi: 0,
            tong_kmld: 0,
            tien_datcoc: 0,
            vat_kmtbi: 0,

            tyle_kmld_tt: 0,
            tong_kmld_tt: 0,

            tien_tgtb_tratruoc: 0,
            vat_tgtb_tratruoc: 0,
            tien_tgtb_tragop: 0,
            vat_tgtb_tragop: 0,

            dtien_kmtb: 0,
            dvat_kmtb: 0,

            dtienkmdc: 0,
            dvatkmdc: 0,

            dtientbi: 0,
            dvattbi: 0,

            dtienkmld: 0,
            dvatkmld: 0,

            dtiendatcoc_tt: 0,
            dvatdatcoc_tt: 0,

            dtiendatcoc_km: 0,
            dvatdatcoc_km:0,

            dtienld: 0,
            dvatld: 0,

            cuoc_tc: 0,
            cuoc_tk: 0,

            KetCuoi_ID:-1,
            madoicap_cd:0,
            madoicap_mgw:0,
            ltb_adsl_id: 1,

            so_dt:'',
            khuvuc_id:0,
            kieuld_id:0,
            vhuong_kn:'',
            vdiachi_tb:'',
            doituong_id: 1, //Defaul: tư nhân
            trangbi_id: 3, //Default khách hàng tự trang bị
            kenh_trang: true, // hoangpkn : 18/05/2020
            chi_giao_net: true,
            sothang_sd:0,
            popupInHopDong_Param:{},
            tinh_thicong:0,
            thuebao_tc_id:0,
            tinh_dau:0,
            tinh_cuoi:0,
            thuebao_tc_id_tsl:0,
            vma_tb:'',
            dsTBCungMa:[],
            dsDBTB:[],
            dsDBTBBC:[],
            thongso_ban: newThongSoThueBao(),
            thongso_tc: newThongSoThueBao(),
            isCheckCotTinhBanTC:true,
            ds_tinh_tc:[],
            khachhang_tc_id:0,
            khachhang_id:0,

            isdn_sochinh:false,
            matb_isdn_chinh: "",
            ds_isdn:[],
            diachidau_id:0,
            diachicuoi_id:0,

            diachidau_id:0,
            quandau_id:0,
            phuongdau_id:0,
            phodau_id:0,
            ngodau_id:0,
            sonhadau:'',

            diachicuoi_id:0,
            quancuoi_id:0,
            phuongcuoi_id:0,
            phocuoi_id:0,
            ngocuoi_id:0,
            sonhacuoi:'',

            ds_loaidv_dau:[],
            ds_loaidv_cuoi:[],
            donvi_dau_id:0,
            donvi_cuoi_id:0,
            tram_dv_dau:0,
            tram_dv_cuoi:0,
            ma_tb_dau:'',
            ma_tb_cuoi:'',
            Lat_DD:0,
            Lng_DD:0,
            Lat_DC:0,
            Lng_DC:0,

            ma_gd_neo:'',
            rowSelected:null,
            thongso_tc_dau: newThongSoThueBao(), 
            thongso_tc_cuoi: newThongSoThueBao(),
            ds_tien_khoanmuc_bd:[],
            txtEmailAdsl:'',
            txtMatKhauADSL:'',

            quytrinh_id:0,
            luong_id:0,

            dsHDKH:[],
            dsHDTB:[],
            dsDiaChi:[],
            dsDiaChiHDTB:[],
            dsHDTB_CD: [],
            dsHDTB_DD: [],
            dsHDTB_ADSL: [],
            dsHDTB_GP: [],
            dsHDTB_TSL: [],
            dsHDTB_IMS:[],

            dsHDTB_MEWAN: [],
            dsHDTBDV: [],
            dsCTTHD:[],
            dsHDTB_KV:[],
            dsTIEN_HDTB_TC:[],

            //bán chéo
            dsHDBC:[],
            dsHDTBBC:[],

            kt_adsl:true,
            quytrinh_tc_tn:0,
            huonggiao_tc_tn:0,

            dsHDBCADSL:[],
            dsHDBCCD:[],
            dsHDBCMGWAN:[],

            dsHDBCTSL:[],
            dsHDBCIMS: [],
            dsDKDVGTBC: [],

            loai_diem_tsl:1, //1:đầu, 2 cuối
            Kieuld_tc_id_tsl: 0,
            Kieuld_tc_id: 0,
            quytrinh_tc_tn_dau: 0,
            huonggiao_tc_tn_dau: 0,

            quytrinh_tc_tn_cuoi: 0,
            huonggiao_tc_tn_cuoi: 0,

            loaikenh_selected: 0

        }
    },
    computed:{
        //Viết sau
        diachi_kh() {
            return ''
        },
        ten_kh() {
            return ''
        },
        DichVuVienThong() {
            return DichVuVienThong;
    }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.GhiLai()
            }else if(action.id=='huy'){
                this.setActiveActions(0)
            }else if(action.id=='xoa_hd'){
                this.$confirm('Bạn thật sự muốn xóa hợp đồng không ?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                }).then(() => {
                    this.XoaHDKH()
                })
            }else if(action.id=='thanhtoan'){
                this.thanhtoan()
            }else if(action.id=='hoso'){
                this.hoso()
            }else if(action.id=='giaophieu'){
                //nút trong code đã comment lại không có dùng, bỏ qua không xử lý
                

            }else if(action.id=='goidadv'){
                //frmDangKyGoiDaDVLM
                this.goidadv()
            }else if(action.id=='themtb'){
                this.ThemHDThueBao()
                
            }else if(action.id=='xoatb'){
                this.$confirm('Bạn thật sự muốn xóa thuê bao không ?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                }).then(() => {
                    this.XoaHDTB()
                }).catch(()=>{})
            }else if(action.id=='capnhatdb'){
                //nút trong code đã comment lại không có dùng, bỏ qua không xử lý
                this.CapNhatDB()
            }else if(action.id=='lienhe'){
                if(this.hdkh_id!=0){
                    this.$refs.thongTinLienHeModal.showModal()
                }
            }else if(action.id=='email'){
                if(this.hdkh_id!=0){
                    this.$refs.emailModal.showModal()
                }
            }else if(action.id=='henld'){
                if(this.hdkh_id!=0){
                    this.$refs.thongTinHenKhachHangModal.showModal()
                }
            }else if(action.id=='sua_km'){
                //Nút này không làm ở task này, tag==2 mới xử lý

            }else if(action.id=='catmo'){
                this.CatMo()
            }   
        },
        async setActiveActions(kieu){
            // inPhụLục.Enabled = false;
            this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='huy')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='xoa_hd')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='themtb')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='capnhatdb')].active=false

            if(kieu==-1){
                this.focusMaTB()
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true

                this.thongso_ban = newThongSoThueBao()
                this.thongso_tc = newThongSoThueBao()
            }else if(kieu==0){
                 //bắt đầu
                this.focusMaTB()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.thongso_ban = newThongSoThueBao()
                this.thongso_tc = newThongSoThueBao()
                await this.clear()
            }else if(kieu==1){
                 //Thêm mới
                this.focusMaTB()
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.checkCTV=false
                this.dataSources=[]
                this.thongso_ban = newThongSoThueBao()
                this.thongso_tc = newThongSoThueBao()
                await this.clear()
            }else if(kieu==2){
                 // Huỷ
                this.focusMaTB()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa_hd')].active=true
                await this.clear()
            }else if(kieu==3){
                this.focusMaTB()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa_hd')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true

                this.actions[this.actions.findIndex(x=>x.id=='themtb')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=true
            }
        },
        focusMaTB(){
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                this.$refs.coDinh.focusMaTB()
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                //băng rộng cố định
                //this.$refs.diDong.focusMaTB()
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                this.$refs.bangRongCoDinh.focusMaTB()
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                this.$refs.ims.focusMaTB()
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                this.$refs.kenhThuRieng.focusMaTB()
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                this.$refs.megawan.focusMaTB()
            }
        },
        async clear(){
            this.kenh_trang = true // hoangpkn : 18/05/2020
            this.chi_giao_net = true
            this.thongso_ban = newThongSoThueBao()
            this.thongso_tc = newThongSoThueBao()
            this.thongso_tc_dau=newThongSoThueBao()
            this.thongso_tc_cuoi=newThongSoThueBao()
            // Lat_TB = 0; Lng_TB = 0; Lat_LD = 0; Lng_LD = 0;
            // Lat_TT = 0; Lng_TT = 0; Lat_BC = 0; Lng_BC = 0;
            // Lat_KH = 0; Lng_KH = 0;
            // Lat_DD = 0; Lng_DD = 0; Lat_DC = 0; Lng_DC = 0;

            // clear
            this.kieu_pl_selected=null
            this.checkCTV=false
            this.checkNguoiGT=false
            this.input_tongtien='0'
            this.hdtb_id_cha = 0
            this.thuebao_id_cha = 0
            this.loaisochinh = 0
            this.hdkh_id = 0
            this.hdkh_cha_id = 0
            this.khachhang_id = 0
            this.hdtb_id = 0
            this.thuebao_id = 0
            this.doituong_id = 1 //Defaul: tư nhân
            this.trangbi_id=3 //Default khách hàng tự trang bị
            this.vdiachi_tb=''
            this.khuvuc_id=0
            this.so_dt=''
            this.vhuong_kn=''
            this.kieuld_id=0

            this.madoicap_mgw=0

            this.dtienld = 0
            this.dvatld = 0

            this.dtienkmld=0
            this.dvatkmld=0


            this.dtientbi=0
            this.dvattbi=0

            this.dtien_kmtb=0
            this.dvat_kmtb=0


            this.dtienkmdc=0
            this.dvatkmdc=0

            this.dtiendatcoc_tt=0
            this.dvatdatcoc_tt=0

            this.dtiendatcoc_km=0
            this.dvatdatcoc_km=0

            //
            this.tien_tbi = 0
            this.vat_tbi = 0
            this.tien_dv = 0
            this.vat_dv = 0
            this.tien_kmld = 0
            this.vat_kmld = 0

            this.madoicap_cd=0
            this.KetCuoi_ID=-1

            this.vma_tb=''
            this.txtEmailAdsl=''
            this.txtMatKhauADSL=''

            this.quytrinh_id=0
            this.luong_id=0
            this.quytrinh_tc_tn=0
            this.huonggiao_tc_tn=0

            this.ctv={
                ctv_id:null,
                ten_ctv:'',
                ma_ctv:'',
                loainv_id:-1,
                donvi_id:-1
            }
            this.nguoi_gt={
                nguoi_gt_id:null,
                ten_nguoi_gt:'',
                ma_nguoi_gt:'',
                loainv_id:-1,
                donvi_id:-1
            }
            this.dataDiaChiLD={
                diachi_id:0,
                tinh_id:0,
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                sonha:'',
                //
                block:'',
                tang:'',
                sophong:'',
                motathem:'',
                diachi:'',

                kinhdo:0,
                vido:0
            }
            this.dataDiaChiTB={
                diachi_id:0,
                tinh_id:0,
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                sonha:'',
                //
                block:'',
                tang:'',
                sophong:'',
                motathem:'',
                diachi:'',

                kinhdo:0,
                vido:0
            }
            this.dsKM_HDTB=[]
            this.dtCTKM=[]
            this.ds_tien_khoanmuc=await this.sp_lay_tienkhoanmuc_theo_loaihd(LoaiHopDong.CHUYENDOI_LH)
            //
            this.$refs.thongTinPhieuYeuCau.clearDulieu()
            this.$refs.thongTinKhachHang.clearDulieu()
            this.$refs.muaThietBi.clearDulieu()
            this.$refs.thongTinKhachHang.clearDulieu()

            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.clearDulieu()
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                //await this.$refs.diDong.clearDulieu()
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                await this.$refs.bangRongCoDinh.clearDulieu()
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.$refs.ims.clearDulieu()
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.$refs.kenhThuRieng.clearDulieu()
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.$refs.megawan.clearDulieu()
            }
            // //
            this.dsCTM_TBI=[]
            this.dsDC_HDTB=[]
            this.cuoc_tc=0
            this.cuoc_tk=0
            this.dsTenTB=[]


            await this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
            await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.dtiendatcoc_km, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)

            await this.HT_ThietBi()

            await this.LayTienTheoKhoanMuc()
        },
        //Hàm thừa
        async HienThiTienDichVu(tien_dv, vat_dv){
            // if (tien_dv.ToString() != "")
            //     txtTienDV.Text = tien_dv.ToString(Format.FM_TIEN_VND);
            // else
            //     txtTienDV.Text = "0";
            // if (vat_dv.ToString() != "")
            //     txtVatDV.Text = vat_dv.ToString(Format.FM_TIEN_VND);
            // else
            //     txtVatDV.Text = "0";
        },
        async HT_ThietBi(){

            var kt = true;//Kiểm tra xem biến 
            this.tien_tbi = 0
            this.vat_tbi = 0
            var soluong = 1
            this.tien_kmtbi = 0
            this.vat_kmtbi = 0
            this.tien_tgtb_tragop = 0
            this.vat_tgtb_tragop = 0
            this.tien_tgtb_tratruoc = 0
            this.vat_tgtb_tratruoc = 0
            //
            var dTyLe_KMTB = 0
            var dtien_kmtbi_tmp = 0
            var dvat_kmtbi_tmp = 0
            var dtien_tbi_tmp = 0
            var dvat_tbi_tmp = 0
            var dtien_tgtb_tratruoc_tmp = 0
            var dvat_tgtb_tratruoc_tmp = 0
            var dtien_tgtb_tragop_tmp = 0
            var dvat_tgtb_tragop_tmp = 0

            //Dư liệu ảo test

            this.dsTenTB=[]
            for(let i=0;i<this.dsCTM_TBI.length;i++){
                this.dsTenTB.push(this.dsCTM_TBI[i].LOAI_TBI)
                //
                kt = false
                soluong = this.dsCTM_TBI[i].SOLUONG
                if(this.dsCTM_TBI[i].TIEN!=null){
                    dtien_tbi_tmp=this.dsCTM_TBI[i].TIEN
                    this.tien_tbi=this.tien_tbi + dtien_tbi_tmp * soluong
                }
                if(this.dsCTM_TBI[i].VAT!=null){
                    dvat_tbi_tmp=this.dsCTM_TBI[i].VAT
                    this.vat_tbi=this.vat_tbi + dvat_tbi_tmp * soluong
                }
                //DS khuyến mãi
                let dtTMP_KMTBI=await this.sp_lay_ctkm_loai_tbi(this.ds_CTKM(), this.dsCTM_TBI[i].LOAITBI_ID)
                if(dtTMP_KMTBI.length>0){
                    dtien_kmtbi_tmp=dtTMP_KMTBI[0].tien_km!=null?dtTMP_KMTBI[0].tien_km:0
                    dvat_kmtbi_tmp=dtTMP_KMTBI[0].vat_km!=null?dtTMP_KMTBI[0].vat_km:0
                    dTyLe_KMTB=dtTMP_KMTBI[0].tyle_km!=null?dtTMP_KMTBI[0].tyle_km:0
                }
                console.log('dtTMP_KMTBI', dtTMP_KMTBI)
                if(dTyLe_KMTB>0){
                    dtien_kmtbi_tmp=Math.round(dtien_tbi_tmp * dTyLe_KMTB / 100)
                    dvat_kmtbi_tmp=Math.round(dvat_tbi_tmp * dTyLe_KMTB / 100)
                }
                if(dtTMP_KMTBI.length>0){
                    this.dsCTM_TBI[i].TIEN_KM=dtien_kmtbi_tmp
                    this.dsCTM_TBI[i].VAT_KM=dvat_kmtbi_tmp
                    this.tien_kmtbi+=dtien_kmtbi_tmp*soluong
                    this.vat_kmtbi+=dvat_kmtbi_tmp * soluong
                }
                //DS trả góp
                let dtTMP=await this.sp_lay_ds_tragop_thietbi(this.ds_CTKM(), this.dsCTM_TBI[i].LOAITBI_ID)
                if(dtTMP.length>0){
                    //có định nghĩa trả góp
                    if(this.dsCTM_TBI[i].TIEN_TRATRUOC!=null){
                        dtien_tgtb_tratruoc_tmp=this.dsCTM_TBI[i].TIEN_TRATRUOC
                    }
                    if(this.dsCTM_TBI[i].VAT_TRATRUOC!=null){
                        dvat_tgtb_tratruoc_tmp=this.dsCTM_TBI[i].VAT_TRATRUOC
                    }
                    console.log('dtien_tgtb_tratruoc_tmp trả góp', dtien_tgtb_tratruoc_tmp)
                    console.log('dvat_tgtb_tratruoc_tmp trả góp', dvat_tgtb_tratruoc_tmp)

                }else{
                    dtien_tgtb_tratruoc_tmp = dtien_tbi_tmp - dtien_kmtbi_tmp
                    dvat_tgtb_tratruoc_tmp = dvat_tbi_tmp - dvat_kmtbi_tmp
                    if(this.dsCTM_TBI[i].TIEN_TRATRUOC==''||this.dsCTM_TBI[i].TIEN_TRATRUOC==0){
                        this.dsCTM_TBI[i].TIEN_TRATRUOC=dtien_tgtb_tratruoc_tmp
                        this.dsCTM_TBI[i].TIEN_TRATRUOC=dvat_tgtb_tratruoc_tmp
                    }
                    console.log('dtien_tgtb_tratruoc_tmp  ktrả góp', dtien_tgtb_tratruoc_tmp)
                    console.log('dvat_tgtb_tratruoc_tmp ktrả góp', dvat_tgtb_tratruoc_tmp)
                }

                //Tổng tiền trả trước thiết bị
                this.tien_tgtb_tratruoc += dtien_tgtb_tratruoc_tmp * soluong
                //Tổng vat trả trước thiết bị
                this.vat_tgtb_tratruoc += dvat_tgtb_tratruoc_tmp * soluong
                //Tổng tiền trả góp thiết bị
                this.tien_tgtb_tragop += (dtien_tbi_tmp - Math.abs(dtien_kmtbi_tmp) - dtien_tgtb_tratruoc_tmp) * soluong
                //Tổng vat trả góp thiết bị
                this.vat_tgtb_tragop += (dvat_tbi_tmp - Math.abs(dvat_kmtbi_tmp) - dvat_tgtb_tratruoc_tmp) * soluong
                
                console.log('tien_tgtb_tratruoc', this.tien_tgtb_tratruoc)
                console.log('vat_tgtb_tratruoc', this.vat_tgtb_tratruoc)
                console.log('tien_tgtb_tragop', this.tien_tgtb_tragop)
                console.log('vat_tgtb_tragop', this.vat_tgtb_tragop)

            }
            if (kt == true)
            {
                this.tien_tbi = 0
                this.vat_tbi = 0
                this.tien_kmtbi = 0
                this.vat_kmtbi = 0
                this.tien_tgtb_tratruoc = 0
                this.vat_tgtb_tratruoc = 0
                this.tien_tgtb_tragop = 0
                this.vat_tgtb_tragop = 0
            }

            

            this.dtien_kmtb = this.tien_kmtbi
            this.dvat_kmtb = this.vat_kmtbi

            console.log('dtien_kmtb', this.dtien_kmtb)
            console.log('dvat_kmtb', this.vat_kmtbi)


            await this.HienThiTienMuaThietBi(this.tien_tbi, this.vat_tbi)
            await this.HienThiTienKhuyenMai_ThietBi(this.tien_kmtbi, this.vat_kmtbi)
            await this.HienThiTienTraTruocThietBi(this.tien_tgtb_tratruoc, this.vat_tgtb_tratruoc)
            await this.HienThiTienTraGopThietBi(this.tien_tgtb_tragop, this.vat_tgtb_tragop)
        },
        ds_CTKM(){
            var sDS = "-1"
            for(let i=0;i<this.dsKM_HDTB.length;i++){
                sDS+=',' + this.dsKM_HDTB[i].CHITIETKM_ID.toString()
            }
            for(let i=0;i<this.dsDC_HDTB.length;i++){
                sDS+=',' + this.dsDC_HDTB[i].CHITIETKM_ID.toString()
            }
            return sDS
        },
        async HienThiTienMuaThietBi(tien_tbi, vat_tbi){
            this.dtientbi=tien_tbi
            this.dvattbi=vat_tbi
            //
            const index=this.ds_tien_khoanmuc.findIndex(x=>x.khoanmuctt_id==KHOANMUC_TT.KMTT_MUATHIETBI.toString())
            if(index>-1){
                this.ds_tien_khoanmuc[index].tien=tien_tbi
                this.ds_tien_khoanmuc[index].vat=vat_tbi
            }
            
            await this.LayTongTien_HDTB()

        },
        async HienThiTienKhuyenMai_ThietBi(tien_kmtb, vat_kmtbi){
            this.dtien_kmtb=tien_kmtb
            this.dvat_kmtb=vat_kmtbi

            const index=this.ds_tien_khoanmuc.findIndex(x=>x.khoanmuctt_id==KHOANMUC_TT.KMTT_KM_THIETBI.toString())
            if(index>-1){
                if(tien_kmtb>0){
                    this.ds_tien_khoanmuc[index].tien=tien_kmtb*-1
                    this.ds_tien_khoanmuc[index].vat=vat_kmtbi*-1
                }else{
                    this.ds_tien_khoanmuc[index].tien=tien_kmtb
                    this.ds_tien_khoanmuc[index].vat=vat_kmtbi
                }
                
            }
            await this.LayTongTien_HDTB()
        },
        async HienThiTienTraTruocThietBi(tien_tratruoc_tbi, vat_tratruoc_tbi){

            const index=this.ds_tien_khoanmuc.findIndex(x=>x.khoanmuctt_id==KHOANMUC_TT.KMTT_TRATRUOC_THIETBI.toString())
            if(index>-1){
                this.ds_tien_khoanmuc[index].tien=tien_tratruoc_tbi
                this.ds_tien_khoanmuc[index].vat=vat_tratruoc_tbi
            }
            await this.LayTongTien_HDTB()
        },
        async HienThiTienTraGopThietBi(tien_tragop_tbi, vat_tragop_tbi){
            const index=this.ds_tien_khoanmuc.findIndex(x=>x.khoanmuctt_id==KHOANMUC_TT.KMTT_TRAGOP_THIETBI.toString())
            if(index>-1){
                this.ds_tien_khoanmuc[index].tien=tien_tragop_tbi
                this.ds_tien_khoanmuc[index].vat=vat_tragop_tbi
            }
            await this.LayTongTien_HDTB()
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

                if (dvvt_id == DichVuVienThong.MEGAWAN || dvvt_id == DichVuVienThong.METRONET || dvvt_id == DichVuVienThong.TSL||dvvt_id ==DichVuVienThong.NOIBO_2DIEM||dvvt_id ==DichVuVienThong.NOIBO_DADIEM)
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
        async Gan_thongso_bancheo_hdtb_tsl(hdtb_id){
            //
            let ds=await this.lay_thongso_thuebao_hdbc(hdtb_id)
            if(ds.length>0){
                for(let i=0;i<ds.length;i++){
                    this.thongso_ban.kieuld_id = ds[0].kieuld_ban?ds[0].kieuld_ban:0
                    this.thongso_ban.doituong_id = ds[0].doituong_ban?ds[0].doituong_ban:0
                    this.thongso_ban.chuquan_id = ds[0].chuquan_ban?ds[0].chuquan_ban:0
                    this.thongso_ban.chuquanld_id = ds[0].chuquanld_ban?ds[0].chuquanld_ban:0
                    this.thongso_ban.chuquanld_id_cuoi = ds[0].chuquanld_ban_cuoi?ds[0].chuquanld_ban_cuoi:0

                    this.thongso_ban.congnghe_id = ds[0].congnghe_ban?ds[0].congnghe_ban:0
                    this.thongso_ban.kieutra_id = ds[0].kieutra_ban?ds[0].kieutra_ban:0
                    this.thongso_ban.loaikenh_id = ds[0].loaikenh_ban?ds[0].loaikenh_ban:0
                    this.thongso_ban.loaimd_id = ds[0].loaimd_ban?ds[0].loaimd_ban:0
                    this.thongso_ban.ma_doicap = ds[0].madoicap_ban?ds[0].madoicap_ban:0

                    this.thongso_ban.ma_tn = ds[0].matn_ban?ds[0].matn_ban:0

                    this.thongso_ban.muccuoc_id = ds[0].muccuoc_ban?ds[0].muccuoc_ban:0
                    this.thongso_ban.muccuoctb_id = ds[0].muccuoctb_ban?ds[0].muccuoctb_ban:0
                    this.thongso_ban.thietbidc_id = ds[0].thietbidau_ban?ds[0].thietbidau_ban:0
                    this.thongso_ban.thietbidc_id_cuoi = ds[0].thietbicuoi_ban?ds[0].thietbicuoi_ban:0
                    this.thongso_ban.thoihan_id = ds[0].thoihan_ban?ds[0].thoihan_ban:0
                    this.thongso_ban.tocdo_id = ds[0].tocdo_ban?ds[0].tocdo_ban:0
                    this.thongso_ban.tocdo_isp = ds[0].tocdoisp_ban?ds[0].tocdoisp_ban:0
                    this.thongso_ban.tocdo_nix = ds[0].tocdonix_ban?ds[0].tocdonix_ban:0
                    this.thongso_ban.tocdo_pir_id = ds[0].tocdopir_ban?ds[0].tocdopir_ban:0
                    this.thongso_ban.tocdothuc_id = ds[0].tocdothuc_ban?ds[0].tocdothuc_ban:0
                    this.thongso_ban.trangbi_id =  ds[0].trangbi_ban?ds[0].trangbi_ban:0

                    // Thông số thi công
                    if (ds[0].tinh_dc_id==1) // Nếu row của tỉnh đầu
                    {
                        this.thongso_tc_dau.kieuld_id = ds[0].kieuld_tc?ds[0].kieuld_tc:0
                        this.thongso_tc_dau.doituong_id = ds[0].doituong_tc?ds[0].doituong_tc:0
                        this.thongso_tc_dau.chuquan_id = ds[0].chuquan_tc?ds[0].chuquan_tc:0
                        this.thongso_tc_dau.chuquanld_id = ds[0].chuquanld_tc?ds[0].chuquanld_tc:0
                        this.thongso_tc_dau.congnghe_id = ds[0].congnghe_tc?ds[0].congnghe_tc:0
                        this.thongso_tc_dau.kieutra_id = ds[0].kieutra_tc?ds[0].kieutra_tc:0
                        this.thongso_tc_dau.loaikenh_id = ds[0].loaikenh_tc?ds[0].loaikenh_tc:0
                        this.thongso_tc_dau.loaimd_id = ds[0].loaimd_tc?ds[0].loaimd_tc:0
                        this.thongso_tc_dau.ma_doicap = ds[0].madoicap_tc?ds[0].madoicap_tc:0
                        this.thongso_tc_dau.ma_tn = ds[0].matn_tc?ds[0].matn_tc:0
                        this.thongso_tc_dau.muccuoc_id = ds[0].muccuoc_tc?ds[0].muccuoc_tc:0
                        this.thongso_tc_dau.muccuoctb_id = ds[0].muccuoctb_tc?ds[0].muccuoctb_tc:0
                        this.thongso_tc_dau.thietbidc_id = ds[0].thietbidau_tc?ds[0].thietbidau_tc:0
                        this.thongso_tc_dau.thietbidc_id_cuoi = ds[0].thietbicuoi_tc?ds[0].thietbicuoi_tc:0
                        this.thongso_tc_dau.thoihan_id = ds[0].thoihan_tc?ds[0].thoihan_tc:0
                        this.thongso_tc_dau.tocdo_id = ds[0].tocdo_tc?ds[0].tocdo_tc:0
                        this.thongso_tc_dau.tocdo_isp = ds[0].tocdoisp_tc?ds[0].tocdoisp_tc:0
                        this.thongso_tc_dau.tocdo_nix = ds[0].tocdonix_tc?ds[0].tocdonix_tc:0
                        this.thongso_tc_dau.tocdo_pir_id = ds[0].tocdopir_tc?ds[0].tocdopir_tc:0
                        this.thongso_tc_dau.tocdothuc_id = ds[0].tocdothuc_tc?ds[0].tocdothuc_tc:0
                        this.thongso_tc_dau.trangbi_id = ds[0].trangbi_tc?ds[0].trangbi_tc:0
                    }
                    else
                    {
                        this.thongso_tc_cuoi.kieuld_id = ds[0].kieuld_tc?ds[0].kieuld_tc:0
                        this.thongso_tc_cuoi.doituong_id = ds[0].doituong_tc?ds[0].doituong_tc:0
                        this.thongso_tc_cuoi.chuquan_id = ds[0].chuquan_tc?ds[0].chuquan_tc:0
                        this.thongso_tc_cuoi.chuquanld_id = ds[0].chuquanld_tc?ds[0].chuquanld_tc:0
                        this.thongso_tc_cuoi.congnghe_id = ds[0].congnghe_tc?ds[0].congnghe_tc:0
                        this.thongso_tc_cuoi.kieutra_id = ds[0].kieutra_tc?ds[0].kieutra_tc:0
                        this.thongso_tc_cuoi.loaikenh_id = ds[0].loaikenh_tc?ds[0].loaikenh_tc:0
                        this.thongso_tc_cuoi.loaimd_id = ds[0].loaimd_tc?ds[0].loaimd_tc:0
                        this.thongso_tc_cuoi.ma_doicap = ds[0].madoicap_tc?ds[0].madoicap_tc:0
                        this.thongso_tc_cuoi.ma_tn = ds[0].matn_tc?ds[0].matn_tc:0
                        this.thongso_tc_cuoi.muccuoc_id = ds[0].muccuoc_tc?ds[0].muccuoc_tc:0
                        this.thongso_tc_cuoi.muccuoctb_id = ds[0].muccuoctb_tc?ds[0].muccuoctb_tc:0
                        this.thongso_tc_cuoi.thietbidc_id = ds[0].thietbidau_tc?ds[0].thietbidau_tc:0
                        this.thongso_tc_cuoi.thietbidc_id_cuoi = ds[0].thietbicuoi_tc?ds[0].thietbicuoi_tc:0
                        this.thongso_tc_cuoi.thoihan_id = ds[0].thoihan_tc?ds[0].thoihan_tc:0
                        this.thongso_tc_cuoi.tocdo_id = ds[0].tocdo_tc?ds[0].tocdo_tc:0
                        this.thongso_tc_cuoi.tocdo_isp = ds[0].tocdoisp_tc?ds[0].tocdoisp_tc:0
                        this.thongso_tc_cuoi.tocdo_nix = ds[0].tocdonix_tc?ds[0].tocdonix_tc:0
                        this.thongso_tc_cuoi.tocdo_pir_id = ds[0].tocdopir_tc?ds[0].tocdopir_tc:0
                        this.thongso_tc_cuoi.tocdothuc_id = ds[0].tocdothuc_tc?ds[0].tocdothuc_tc:0
                        this.thongso_tc_cuoi.trangbi_id = ds[0].trangbi_tc?ds[0].trangbi_tc:0
                    }


                }
            }
        },
        async onSelectedRow(item){
            //handle focus row
            this.rowSelected=item
            console.log('rowSelected', item)
            await this.HienThiTTHopDongTB(item)
        },
        async CapNhatDB(){
            
        },
        async CatMo(){
            this.loading(true)
            // let result=await this.lay_ds_chitiet_km_theo_hdtb(3214100)
            // console.log(result)
            await this.TaoDuLieu(true)
            await this.TaoDuLieuBC(true)
            this.loading(false)
        },
        getMaTB(){
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                return this.$refs.coDinh.input_matb.trim()
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                return this.$refs.bangRongCoDinh.input_matb.trim()
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                return this.$refs.megawan.input_matb.trim()
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                return this.$refs.kenhThuRieng.input_matb.trim()
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                return this.$refs.ims.input_matb.trim()
            }else{
                return ''
            }

        },
        async HienThiTTHopDongTB(item){
            console.log('HienThiTTHopDongTB', item)
            //uppercase
            // for (var key in item) {
            //     if (item.hasOwnProperty(key)) {
            //         item[key.toUpperCase()] = item[key];
            //         delete item[key];
            //     }
            // }
            // console.log('HienThiTTHopDongTB', item)


            //
            
            this.hdtb_id=item.HDTB_ID!=null?item.HDTB_ID:0
            this.thuebao_id=item.THUEBAO_ID!=null?item.THUEBAO_ID:0
            
            if(item.hasOwnProperty('THUEBAO_CHA_ID')&&item.THUEBAO_CHA_ID!=null){
                this.thuebao_id_cha=item.THUEBAO_CHA_ID
                this.loaisochinh=1
            }else{
                this.thuebao_id_cha=0
            }
            if(item.HDTB_CHA_ID!=null){
                this.hdtb_id_cha=item.HDTB_CHA_ID
                this.loaisochinh=0
            }else{
                this.hdtb_id_cha=0
            }
            this.vdiachi_tb=item.DIACHI_TB!=null?item.DIACHI_TB:''
            this.donviql_id = item.DONVI_ID!=null?item.DONVI_ID:0
            this.doituong_id = item.DOITUONG_ID!=null?item.DOITUONG_ID:0
            this.loaitb_id = item.LOAITB_ID!=null?item.LOAITB_ID:0
            await this.$refs.thongTinKhachHang.HienThiTTHopDongTB(item)
            //dichvuvt_id = Convert.ToInt32(checkdata.MAP_ID("dichvuvt_id", DatabaseConstants.DB2 + ".db_thuebao", "where thuebao_id = " + thuebao_id));
            this.dichvuvt_id=Number(await this.fn_map_db_thuebao(this.thuebao_id, 12, '-1'))
            if(this.dichvuvt_id==-1){
                this.$toast.error('Không có thông tin dịch vụ của thuê bao')
                return
            }
            if(this.dichvuvt_id!=DichVuVienThong.TSL&&this.dichvu_vt_selected!=DichVuVienThong.NOIBO_2DIEM){
                await this.Gan_thongso_bancheo_hdtb(this.hdtb_id)

                let tinh=await this.lay_tinh_theo_thuebaoid(this.thuebao_id)
                this.tinh_thicong=tinh.length>0?tinh[0].tinh_thicong:0
                if(this.tinh_thicong>0){
                    let resultTB=await this.lay_thuebaoid_thicong(this.thuebao_id, this.tinh_thicong)
                    this.thuebao_tc_id=resultTB.length>0?resultTB[0].thuebao_kn_id:0
                }else{
                    this.tinh_thicong=item.tinh_thicong?item.tinh_thicong:0
                    this.thuebao_tc_id = 0
                }

            }else{
                await this.Gan_thongso_bancheo_hdtb_tsl(this.hdtb_id)
                //
                let dt=await this.lay_tinhtc_id_tsl(this.dichvuvt_id, this.getMaTB(), this.$root.token.getPhanVungID())
                if(dt.length>0){
                    this.kenh_trang=false
                    this.tinh_dau=dt.find(x=>x.daucuoi_id==1).tinh_thicong
                    this.tinh_cuoi=dt.find(x=>x.daucuoi_id==2).tinh_thicong
                    this.thuebao_tc_id=dt.find(x=>x.daucuoi_id==1).thuebao_kn_id
                    this.thuebao_tc_id_tsl=dt.find(x=>x.daucuoi_id==2).thuebao_kn_id
                    if(this.tinh_thicong!=null){
                        this.tinh_thicong=0
                    }
                }else{
                    this.kenh_trang=true
                    let dt=await this.sp_lay_hdtb_tsl_theo_hdtb_id(this.hdtb_id)
                    if(dt.length>0){
                        this.tinh_dau=dt.find(x=>x.daucuoi_id==1).tinh_id
                        this.tinh_cuoi=dt.find(x=>x.daucuoi_id==2).tinh_id
                        this.thuebao_tc_id_tsl = 0
                        this.thuebao_tc_id = 0
                    }else{
                        this.$toast.error('Không lấy được tỉnh thi công TSL')
                    }
                    if(this.tinh_cuoi>0&&!this.kenh_trang){
                        this.dsDBTBBC=[]
                        let db_dau=await this.lay_danhba_theo_thuebaoid(this.tinh_dau, this.thuebao_tc_id, this.dichvu_vt_selected, 0,2)
                        let db_cuoi=await this.lay_danhba_theo_thuebaoid(this.tinh_cuoi, this.thuebao_tc_id_tsl, this.dichvu_vt_selected, 0,2)
                        if(db_dau.length>0){
                            this.dsDBTBBC.push(db_dau[0])
                        }
                        if(db_cuoi.length>0){
                            this.dsDBTBBC.push(db_cuoi[0])
                        }
                    }else{
                        this.dsDBTBBC=[]
                    }
                }
                //
            }
            //

            //hiển thị địa chỉ
            let dsDiaChi=await this.lay_ds_diachi_theo_hdtb_id(this.hdtb_id)
            //upper case key data source
            for(var i = 0; i < dsDiaChi.length; i++){ 
                for (var key in dsDiaChi[i]) {
                    if(key.toUpperCase() !== key){
                        dsDiaChi[i][key.toUpperCase()] = dsDiaChi[i][key];
                        delete dsDiaChi[i][key];
                    }
                }
            }
            console.log('dsDiaChi', dsDiaChi)
            await this.HienThiTTDiaChi(dsDiaChi)

            Object.assign(item,{dichvuvt_id_map:this.dichvuvt_id})
            await this.$refs.thongTinPhieuYeuCau.HienThiTTHopDongTB(item)
            if(this.dichvuvt_id!=this.dichvu_vt_selected){
                this.dichvu_vt_selected=this.dichvuvt_id
                //biding layout nếu khác dịch vụ
                setTimeout(async ()=>{
                    this.loading(true)
                    if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                        await this.$refs.coDinh.initDuLieu(this.ds_loaihinh_tb)
                        await this.$refs.coDinh.visibleToolStripMenuAndControl(this.iKieu==0)
                    }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){

                    }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                        //băng rộng cố định
                        await this.$refs.bangRongCoDinh.initDuLieu(this.ds_loaihinh_tb)
                        if(this.thamso.nhap_tocdo_thuc_adsl==0){
                            this.$refs.bangRongCoDinh.enabledCheckTocDoThuc=false
                        }else{
                            this.$refs.bangRongCoDinh.enabledCheckTocDoThuc=true
                        }
                        this.$refs.bangRongCoDinh.checkTocDoThuc=false
                        await this.$refs.bangRongCoDinh.visibleToolStripMenuAndControl(this.iKieu==0)
                    }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                        await this.$refs.ims.initDuLieu(this.ds_loaihinh_tb)
                        await this.$refs.ims.visibleToolStripMenuAndControl(this.iKieu==0)
                    }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                        await this.$refs.kenhThuRieng.initDuLieu(this.ds_loaihinh_tb)
                        await this.$refs.kenhThuRieng.visibleToolStripMenuAndControl(this.iKieu==0)
                    }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                        await this.$refs.megawan.initDuLieu(this.ds_loaihinh_tb, this.dichvu_vt_selected)
                        await this.$refs.megawan.visibleToolStripMenuAndControl(this.iKieu==0)
                    }
                    await this.HienThiTTHopDongTB_B2(item)
                    this.loading(false)
                }, 500)

            }else{
                //làm như thường
                this.loading(true)
                await this.HienThiTTHopDongTB_B2(item)
                this.loading(false)
            }


        },
        async HienThiTTHopDongTB_B2(item){
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                this.$refs.coDinh.input_matb=item.MA_TB!=null?item.MA_TB:''
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                this.$refs.diDong.input_matb=item.MA_TB!=null?item.MA_TB:''
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                this.$refs.bangRongCoDinh.input_matb=item.MA_TB!=null?item.MA_TB:''
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                this.$refs.ims.input_matb=item.MA_TB!=null?item.MA_TB:''
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                this.$refs.kenhThuRieng.input_matb=item.MA_TB!=null?item.MA_TB:''
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                this.$refs.megawan.input_matb=item.MA_TB!=null?item.MA_TB:''
                this.$refs.megawan.input_account_moi=item.MA_TB!=null?item.MA_TB:''
            }
            if(this.loaihinhtb_id==LoaiHinhTB.DIDONGTRATRUOC){
                this.loaihinhtbcu_id=LoaiHinhTB.DIDONGTRASAU
            }else if(this.loaitb_id!=LoaiHinhTB.FSECURE){
                this.loaihinhtbcu_id=(item.hasOwnProperty('LOAITB_ID_CU')&&item.LOAITB_ID_CU!=null)?item.LOAITB_ID_CU:0
            }else{
                this.loaihinhtbcu_id=this.loaitb_id
            }
            //
            console.log('HienThiTTHopDongTB_B2 loaihinhtb_id', this.loaihinhtb_id)
            await this.$refs.thongTinPhieuYeuCau.setLoaiHinhTbCu(this.loaihinhtbcu_id)
            await this.changeLoaiHinhTBCu(this.loaihinhtbcu_id)
            //trigger call
            await this.changeKieuYC(item.KIEULD_ID!=null?item.KIEULD_ID:0)
            await this.changeMucCuocTB(item.MUCUOCTB_ID!=null?item.MUCUOCTB_ID:-1)

            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.setLoaiHinhTbMoi(this.loaitb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
               
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                await this.$refs.bangRongCoDinh.setLoaiHinhTbMoi(this.loaitb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.$refs.ims.setLoaiHinhTbMoi(this.loaitb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.$refs.kenhThuRieng.setLoaiHinhTbMoi(this.loaitb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.$refs.megawan.setLoaiHinhTbMoi(this.loaitb_id)
            }

            await this.changeLoaiHinhTBMoi(this.loaitb_id)

            await this.$refs.thongTinPhieuYeuCau.HienThiTTHopDongTB_B2(item)
            //fn_map_bd_thuebao
            let dt_cu_id=Number(await this.fn_map_bd_thuebao(this.hdtb_id, 2, '-1'))
            Object.assign(item,{DOITUONG_ID_CU: dt_cu_id})
            let khuvuc=await this.sp_lay_ds_khuvuc_theo_hdtb_id_bancheo(this.hdtb_id)
            if(khuvuc.length>0){
                Object.assign(item,{TEN_KV: khuvuc[0].ten_kv})
                this.khuvuc_id=khuvuc[0].khuvuc_id?khuvuc[0].khuvuc_id:0
            }
            //handle các layout HienThiTTHopDongTB_B2
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.HienThiTTHopDongTB_B2(item)
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){

            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                await this.$refs.bangRongCoDinh.HienThiTTHopDongTB_B2(item)
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.$refs.ims.HienThiTTHopDongTB_B2(item)
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.$refs.kenhThuRieng.HienThiTTHopDongTB_B2(item)
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.$refs.megawan.HienThiTTHopDongTB_B2(item)
            }

            this.input_tongtien=await this.layTongTien()
            console.log('input_tongtien', this.input_tongtien)

            if(this.loaitb_id!=LoaiHinhTB.FSECURE){
                await this.HienThiTTMoRong_DB(this.thuebao_id, this.dichvuvt_id)
            }

            await this.HienThiTTMoRong_HD(this.hdtb_id, this.dichvuvt_id)
        
            //load mức cước tb
            var vtrangbi_id=0
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                vtrangbi_id=await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
            }
            var vloaitb_id_moi=0
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                vloaitb_id_moi=await this.$refs.coDinh.getLoaiHinhTB_ID_Moi()
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                //băng rộng cố định
                vloaitb_id_moi=await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                vloaitb_id_moi=await this.$refs.ims.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                vloaitb_id_moi=await this.$refs.kenhThuRieng.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                vloaitb_id_moi=await this.$refs.megawan.getLoaiHinhTB_ID_Moi()
            }

            let ngay_yc=await this.$refs.thongTinPhieuYeuCau.getNgayYC()
            await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)
            
            //Kết cuối
            let dtKC=await this.sp_lay_tt_ketcuoi_bancheo(this.hdtb_id)
            if(dtKC.length>0){
                this.KetCuoi_ID=dtKC[0].ketcuoi_id
            }

            this.dsKM_HDTB=[]
            this.dsKM_HDTB=await this.lay_ds_chitiet_km_theo_hdtb(this.hdtb_id)
            console.log('dsKM_HDTB', this.dsKM_HDTB)
            this.dtCTKM=await this.lay_tien_hd_km_theo_hdtb(this.hdtb_id)
            var tien_ld = 0
            var vat_ld = 0
            var tien_cd = 0
            var vat_cd = 0
            for(let i=0;i<this.dtCTKM.length;i++){
                if(this.dtCTKM[i].KHOANMUCTT_ID==KHOANMUC_TT.KMTT_TDLOAIHINHTB){
                    tien_ld=this.dtCTKM[i].TIEN
                    vat_ld=this.dtCTKM[i].VAT
                }
                if(this.dtCTKM[i].KHOANMUCTT_ID==KHOANMUC_TT.KMTT_KM_LAPDAT){
                    this.tien_kmld=this.dtCTKM[i].TIEN
                    this.vat_kmld=this.dtCTKM[i].VAT
                }
            }

            await this.HT_DS_KhuyenMai_Combobox()
            this.dsKM_HDTB=await this.lay_chitiet_km_theo_hdtb_id(this.hdtb_id, 0)
            if(this.dsKM_HDTB.length>0){
                this.$refs.thongTinPhieuYeuCau.khuyenmai.checkKhuyenMai=true
                this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected=this.dsKM_HDTB[0].KHUYENMAI_ID
            }
            this.dsDC_HDTB=await this.lay_chitiet_km_theo_hdtb_id(this.hdtb_id, 1)
            if(this.dsDC_HDTB.length>0){
                this.$refs.thongTinPhieuYeuCau.tratruoc.checkTraTruoc=true
                this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected=this.dsDC_HDTB[0].KHUYENMAI_ID
            }
            this.$refs.thongTinKhachHang.input_thang_ck=item.SOTHANG_CK!=null?item.SOTHANG_CK.toString():''

            //Thiết bị
            this.dsCTM_TBI=[]
            this.dsCTM_TBI=await this.lay_ds_mua_tbi_lhd(this.hdtb_id)

            //sp_lay_tienhd_km_theo_hdtb
            let dsTienKM=await this.sp_lay_tienhd_km_theo_hdtb(this.hdtb_id)
            this.ds_tien_khoanmuc =dsTienKM.map(x=>{
                return {
                    khoanmuctt_id: x.khoanmuctt_id.toString(),
                    tien: x.tien.toString(),
                    vat: x.vat.toString()
                }
            })
            this.ds_tien_khoanmuc_bd = [].concat(this.ds_tien_khoanmuc)
            for(let i=0;i<this.ds_tien_khoanmuc.length;i++){
                let item=this.ds_tien_khoanmuc[i]
                if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_TDLOAIHINHTB.toString()){
                    this.dtienld=Number(item.tien)
                    this.dvatld=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_KM_LAPDAT.toString()){
                    this.dtienkmld=Number(item.tien)
                    this.dvatkmld=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_KM_DATCOC.toString()){
                    this.dtienkmdc=Number(item.tien)
                    this.dvatkmdc=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_DATCOC.toString()){
                    this.dtiendatcoc_tt=Number(item.tien)
                    this.dvatdatcoc_tt=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_DATCOC_KM){
                    this.dtiendatcoc_km=Number(item.tien)
                    this.dvatdatcoc_km=Number(item.vat)
                }
            }
            await this.HT_ThietBi()
            await this.LayTongTien_HDTB()
            //
            if(this.dataSources.length<=1){
                this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=false
            }else{
                this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=true
            }
        },
        async HienThiTTMoRong_HD(hdtb_id, dichvuvt_id){
            this.$refs.thongTinKhachHang.checkSoCVEnabled=false
            this.$refs.thongTinKhachHang.checkSoCV=false
            this.$refs.thongTinKhachHang.input_socv=''
            this.$refs.thongTinKhachHang.ngay_cv=moment(new Date()).format('DD/MM/YYYY')
            if(dichvuvt_id==DichVuVienThong.CO_DINH){
                let ds=await this.lay_ds_hopdong_thuebao_codinh(hdtb_id)
                if(ds.length>0){
                    this.$refs.coDinh.trangbi_moi_selected=ds[0].trangbi_id?ds[0].trangbi_id:null
                    this.madoicap_cd=ds[0].madoicap?ds[0].madoicap:0
                }
            }else if(dichvuvt_id==DichVuVienThong.ADSL||dichvuvt_id==DichVuVienThong.ANTOAN_BAOMAT_TT){
                let ds=await this.lay_ds_hopdong_thuebao_adsl(hdtb_id)
                if(ds.length>0){
                    this.txtEmailAdsl = ds[0].email?ds[0].email.toString():''
                    this.txtMatKhauADSL=ds[0].password?ds[0].password.toString():''

                    await this.$refs.bangRongCoDinh.HienThiTTMoRong_HD(ds[0])
                    //
                    if(this.$refs.bangRongCoDinh.tocdo_moi_selected!=ds[0].tocdo_id){
                        this.$refs.bangRongCoDinh.tocdo_moi_selected=ds[0].tocdo_id
                        await this.changeGoiCuocAdsl(this.$refs.bangRongCoDinh.tocdo_moi_selected)
                    }else{
                        await this.changeGoiCuocAdsl(this.$refs.bangRongCoDinh.tocdo_moi_selected)
                    }
                    this.$refs.bangRongCoDinh.tocdo_thuc_selected=ds[0].tocdothuc_id?ds[0].tocdothuc_id:null
                    if(this.$refs.bangRongCoDinh.tocdo_thuc_selected&&this.$refs.bangRongCoDinh.tocdo_selected!=this.$refs.bangRongCoDinh.tocdo_moi_selected){
                        this.$refs.bangRongCoDinh.checkTocDoThuc=true
                    }else{
                        this.$refs.bangRongCoDinh.checkTocDoThuc=false
                    }
                }
            }else if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.METRONET||dichvuvt_id==DichVuVienThong.NOIBO_DADIEM){
                let ds=await this.lay_ds_hopdong_thuebao_mgwan(hdtb_id)
                if(ds.length>0){
                    let item=ds[0]
                    if(this.$refs.megawan.tocdo_cir_moi_selected!=item.tocdo_id){
                        this.$refs.megawan.tocdo_cir_moi_selected=item.tocdo_id
                        await this.changeTocDoCirWan(this.$refs.megawan.tocdo_cir_moi_selected)
                    }else{
                        await this.changeTocDoCirWan(this.$refs.megawan.tocdo_cir_moi_selected)
                    }
                    if(this.$refs.megawan.loaithietbi_moi_selected!=item.thietbidc_id){
                        this.$refs.megawan.loaithietbi_moi_selected=item.thietbidc_id
                        await this.changeLoaiTBWanMoi(this.$refs.megawan.loaithietbi_moi_selected)
                    }else{
                        await this.changeLoaiTBWanMoi(this.$refs.megawan.loaithietbi_moi_selected)
                    }
                    this.vhuong_kn=item.huong_kn!=null?item.huong_kn.toString():''
                    this.$refs.megawan.trangbi_moi_selected=item.trangbi_id!=null?item.trangbi_id:3
                    await this.changeTrangBiMoi(this.$refs.megawan.trangbi_moi_selected)
                    this.$refs.thongTinPhieuYeuCau.input_ma_cng=item.ma_pl!=null?item.ma_pl.toString():''
                    this.cuoc_tc=item.cuoc_tc!=null?item.cuoc_tc:0
                    this.cuoc_tk=item.cuoc_tk!=null?item.cuoc_tk:0
                    if(item.madoicap){
                        this.madoicap_mgw=Number(item.madoicap)
                    }else{
                        this.madoicap_mgw=0
                    }
                    await this.$refs.megawan.HienThiTTMoRong_HD(item)

                    this.$refs.thongTinKhachHang.checkSoCVEnabled=true
                    if(item.ma_cv!=null){
                        this.$refs.thongTinKhachHang.checkSoCV=true
                        this.$refs.thongTinKhachHang.changeCheckCV()
                        this.$refs.thongTinKhachHang.input_socv=item.ma_cv.toString()
                        //sau check lại chưa rõ format item.NGAY_CV
                        this.$refs.thongTinKhachHang.ngay_cv=item.ngay_cv?moment(item.ngay_cv).format('DD/MM/YYYY'):moment(new Date()).format('DD/MM/YYYY')
                    }
                    this.$refs.thongTinKhachHang.input_ma_bss=item.ma_bss?item.ma_bss.toString():''
                    this.$refs.thongTinKhachHang.input_ma_erp=item.id_erp?item.id_erp.toString():''

                }
            }else if(dichvuvt_id==DichVuVienThong.TSL||dichvuvt_id==DichVuVienThong.NOIBO_2DIEM){
                let ds=await this.lay_ds_hdtb_tsl_theo_hdtb_id(hdtb_id)
                if(ds.length>0){
                    let item=ds[0]

                    if(this.$refs.kenhThuRieng.tocdo_moi_selected!=item.tocdo_id){
                        this.$refs.kenhThuRieng.tocdo_moi_selected=item.tocdo_id
                        await this.changeTocDoKenhTSLMoi(this.$refs.kenhThuRieng.tocdo_moi_selected)
                    }else{
                        await this.changeTocDoKenhTSLMoi(this.$refs.kenhThuRieng.tocdo_moi_selected)
                    }
                    this.$refs.thongTinPhieuYeuCau.input_ma_cng=item.ma_pl!=null?item.ma_pl.toString():''
                    this.$refs.thongTinKhachHang.checkSoCVEnabled=true
                    if(item.ma_cv){
                        this.$refs.thongTinKhachHang.checkSoCV=true
                        this.$refs.thongTinKhachHang.changeCheckCV()
                        this.$refs.thongTinKhachHang.input_socv=item.ma_cv.toString()
                        //sau check lại chưa rõ format item.NGAY_CV
                        this.$refs.thongTinKhachHang.ngay_cv=item.ngay_cv!=null?moment(item.ngay_cv).format('DD/MM/YYYY'):moment(new Date()).format('DD/MM/YYYY')
                    }
                    this.$refs.kenhThuRieng.trangbi_moi_selected=item.trangbi_id!=null?item.trangbi_id:3
                    await this.changeTrangBiMoi(this.$refs.kenhThuRieng.trangbi_moi_selected)
                    await this.$refs.kenhThuRieng.HienThiTTMoRong_HD(item)

                    await this.HienThiTTDiaChi_TSL()

                    for(let i=0;i<ds.length;i++){
                        if(ds[i].daucuoi_id==1){
                            //điểm đầu
                            this.$refs.kenhThuRieng.lcapdau_moi_selected=ds[i].loaicap_id?ds[i].loaicap_id:null
                            this.diachidau_id=ds[i].diachi_id?ds[i].diachi_id:0
                            this.Lng_DD=ds[i].kinhdo_ld?ds[i].kinhdo_ld:0
                            this.Lat_DD=ds[i].vido_ld?ds[i].vido_ld:0
                            this.$refs.kenhThuRieng.input_diachi_dau_moi=ds[i].diachi?ds[i].diachi.toString():''
                            this.ma_tb_dau = ds[i].ma_tb_sub?ds[i].ma_tb_sub.toString():''
                            //sp_lay_ds_donvi_theo_loaidv_v5
                            this.donvi_dau_id=Number(await this.fn_map_hd_thuebao(hdtb_id, 10, '-1'))
                            this.ds_loaidv_dau=await this.sp_lay_ds_donvi_theo_loaidv_v5(this.donvi_dau_id, LOAI_DV.TRAM_VT)
                            
                            let tram_dau=await this.sp_lay_ds_hdtb_dv_kieudv(hdtb_id, LOAI_DV.TRAM_VT, 1)
                            if(tram_dau.length>0){
                                this.tram_dv_dau=tram_dau[0].donvi_id
                            }else{
                                this.tram_dv_dau=0
                            }

                        }else{
                            //2 điểm cuối
                            this.$refs.kenhThuRieng.lcapcuoi_moi_selected=ds[i].loaicap_id?ds[i].loaicap_id:null
                            this.diachicuoi_id=ds[i].diachi_id?ds[i].diachi_id:0
                            this.Lng_DC=ds[i].kinhdo_ld?ds[i].kinhdo_ld:0
                            this.Lat_DC=ds[i].vido_ld?ds[i].vido_ld:0
                            this.$refs.kenhThuRieng.input_diachi_cuoi_moi=ds[i].diachi?ds[i].diachi.toString():''
                            this.ma_tb_cuoi = ds[i].ma_tb_sub?ds[i].ma_tb_sub.toString():''
                            this.donvi_cuoi_id=Number(await this.fn_map_hd_thuebao(hdtb_id, 11, '-1'))
                            this.ds_loaidv_cuoi=await this.sp_lay_ds_donvi_theo_loaidv_v5(this.donvi_cuoi_id, LOAI_DV.TRAM_VT)

                            let tram_cuoi=await this.sp_lay_ds_hdtb_dv_kieudv(hdtb_id, LOAI_DV.TRAM_VT, 2)
                            if(tram_cuoi.length>0){
                                this.tram_dv_cuoi=tram_cuoi[0].donvi_id
                            }else{
                                this.tram_dv_cuoi=0
                            }

                        }
                    }





                    this.$refs.thongTinKhachHang.input_ma_bss=item.ma_bss?item.ma_bss.toString():''
                    this.$refs.thongTinKhachHang.input_ma_erp=item.id_erp?item.id_erp.toString():''
                }
            }else if(dichvuvt_id==DichVuVienThong.IMS){
                let ds=await this.lay_ds_hopdong_thuebao_ims(hdtb_id)
                if(ds.length>0){
                    await this.$refs.ims.HienThiTTMoRong_HD(ds[0]) 
                }
            }
        },
        async layTongTien(){
            if(this.dataSources.length==0){
                return '0'
            }
            var tong=0
            for (let i = 0; i < this.dataSources.length; i++) {
                if(this.dataSources[i].TONGTIEN!=null){
                    tong+=Number(this.dataSources[i].TONGTIEN)
                }
                
            }
            return tong.toString()
        },
        async XoaHDTB(){
            if(TRANGTHAI_DONGBO.OPEN_SYNC_VINA){
                //Không tìm thấy biến gán this.ma_gd_neo nên tạm thời bỏ qua khúc này
                if(this.ma_gd_neo!=null&&this.ma_gd_neo!=''&&this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                    for (let i = 0; i < 3; i++) {
                        
                        // string kq = Delete_HDThayDoiLHTB_VinaPhone(ma_gd_neo);
                        // if (kq == "3")
                        // {
                        //     break;
                        // }
                        // if (k == 2)
                        // {
                        //     Message_Box.ShowError("Không thể xóa được thuê bao thay đổi loại hình di động trả sau sang trả trước trên VinaPhone!");
                        //     return;
                        // }
                    }
                }
            }
            let result=await this.xoa_hopdong_thuebao(this.hdtb_id)
            if(result.code==1){
                this.$toast.success(result.message)
                this.loading(true)
                let ma_gd=await this.$refs.thongTinPhieuYeuCau.getMaGD()
                let ds = await this.lay_ds_hopdong_theo_ma_gd({
                    ma_gd: ma_gd,
                    loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                    donvi_id: this.$root.token.getDonViID(),
                    tthd_id: TrangThaiHD.MOI,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    donvi_dl_id: 0
                })
                await this.HienThiTTHopDongKH(ds)
                this.loading(false)
            }else{
                this.$toast.error(result.message)
            }

        },
        async XoaHDKH(){
            for(let i=0;i<this.dataSources.length;i++){
                if(TRANGTHAI_DONGBO.OPEN_SYNC_VINA){
                    //bỏ qua do ma_gd_neo luôn luôn null

                }else{
                    let resultXoaHDTB_BC=await this.xoa_hdbc_hdtb(this.dataSources[i].HDTB_ID, 0)
                    if(!resultXoaHDTB_BC){
                        return
                    }
                    let resultXoaHDTB=await this.xoa_hopdong_thuebao(this.dataSources[i].HDTB_ID)
                    if(resultXoaHDTB.code!=1){
                        this.$toast.error(resultXoaHDTB.message)
                        return
                    }
                }

            }
            let resultXoaHKH_BC=await this.xoa_hdbc(this.hdkh_id, 0)
            if(!resultXoaHKH_BC){
                return
            }
            let result=await this.xoa_hdkh_v2(this.hdkh_id, LoaiHopDong.CHUYENDOI_LH)
            if(result.code==0){
                this.$toast.error(result.message)
                return
            }
            this.$toast.success(result.message)
            this.dataSources=[]
            this.setActiveActions(0)
        },
        async ThemHDThueBao(){
            var async_status = false // Biến kiểm tra việc đồng bộ dữ liệu VinaPhone, MyTV...
            this.loading(true)
            if(!(await this.KiemTraDL_ThueBao())){
                this.loading(false)
                return
            }
            this.loading(false)
            let kthdtb=await this.kiemtra_hdtb_khi_themmoi({
                maTb:this.getMaTB(), 
                hdkhId:this.hdkh_id, 
                dichVuVtId:this.dichvu_vt_selected, 
                loaiTbId:0
            })
            if(kthdtb==1){
                this.$toast.error('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được')
                return
            }
            // if (!tracuuchung.KIEMTRA_DB_KHI_THEMMOI(txtMaTB.Text, khachhang_id, thuebao_id, dichvuvt_id))
            // {
            //     Message_Box.ShowTB("Thuê bao không tồn tại trong danh bạ, không thêm mới được ");
            //     return false;
            // }
            var tocdotn_id = -1
            var muccuoctn_id = -1
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){

            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                tocdotn_id=this.$refs.bangRongCoDinh.tocdo_moi_selected?this.$refs.bangRongCoDinh.tocdo_moi_selected:-1
                if(this.$refs.thongTinPhieuYeuCau.loaihinh_selected!=LoaiHinhTB.INTERNET_MYTV){
                    muccuoctn_id=this.$refs.bangRongCoDinh.muccuoc_moi_selected?this.$refs.bangRongCoDinh.muccuoc_moi_selected:-1
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET){
                tocdotn_id = this.$refs.megawan.tocdo_cir_moi_selected?this.$refs.megawan.tocdo_cir_moi_selected:-1
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                tocdotn_id=this.$refs.kenhThuRieng.tocdo_moi_selected?this.$refs.kenhThuRieng.tocdo_moi_selected:-1
            }
            //layhuonggiao_tiepnhan_v2
            var j_loaikenh_id=0
            if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                j_loaikenh_id=this.$refs.megawan.loaikenh_moi_selected
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL || this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                j_loaikenh_id=this.$refs.kenhThuRieng.loaikenh_moi_selected
            }else{ 
                j_loaikenh_id=0
            }
            this.loading(true)
            let ds=await this.layhuonggiao_tiepnhan_v2({
                dichvuvt_id: this.dichvu_vt_selected,
                kieuld_id: this.$refs.thongTinPhieuYeuCau.kieu_yc_selected?this.$refs.thongTinPhieuYeuCau.kieu_yc_selected:0,
                loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                loaikenh_id: j_loaikenh_id,
                loaitb_id: this.getLoaiHinhTB_ID_Moi(),
                muccuoc_id: muccuoctn_id,
                phanvung_id: this.$root.token.getPhanVungID(),
                tocdo_id: tocdotn_id
            })
            this.loading(false)
            var huonggiao_tn = 0
            this.luong_id=0
            if(ds.length<=0){
                this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
                return
            }
            huonggiao_tn=ds[0].huonggiao_id
            this.quytrinh_id=ds[0].quytrinh_id
            this.luong_id=ds[0].luong_id

            //Lấy hướng hợp đồng thuê bao thi công
            if(this.tinh_thicong>0){
                this.quytrinh_tc_tn = 0
                this.huonggiao_tc_tn = 0
                this.loading(true)
                let rs_qt=await this.layhuonggiao_tiepnhan({
                    kieuld_id: this.thongso_tc.kieuld_id?this.thongso_tc.kieuld_id:0,
                    loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                    loaitb_id: this.getLoaiHinhTB_ID_Moi(),
                    muccuoc_id: this.thongso_tc.muccuoc_id?this.thongso_tc.muccuoc_id:0,
                    tinhthicong_id: this.tinh_thicong,
                    tocdo_id: this.thongso_tc.tocdo_id?this.thongso_tc.tocdo_id:0
                })
                this.loading(false)
                if(rs_qt.length<=0){
                    this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
                    return
                }
                this.quytrinh_tc_tn=rs_qt[0].quytrinh_id
                this.huonggiao_tc_tn=rs_qt[0].huonggiao_id
            }
            //
            this.loading(true)
            let checkHDKhac = await this.kiemtra_thuebao_lap_hopdong_khac({
                maTb: this.getMaTB(),
                maGd: "0",
                dichVuVtId: this.dichvu_vt_selected,
                loaiHdId: LoaiHopDong.CHUYENDOI_LH
            })
            this.loading(false)
            if(checkHDKhac!=null){  
                let message='Số máy/Acc: '+item.ma_tb+' đang được lập bởi hợp đồng (phụ lục) '+checkHDKhac.ten_loaihd
                            +' có mã giao dịch '+checkHDKhac.ma_gd+' do User '+checkHDKhac.nguoi_cn+
                            ' thuộc đơn vị '+checkHDKhac.ten_dv+' thực hiện vào ngày '+checkHDKhac.ngay_yc
                this.$toast.error(message)
                return
            }
            //kiemtra_tb_cung_makh_tb_pl
            this.loading(true)
            let kt=await this.kiemtra_tb_cung_makh_tb_pl(this.$refs.thongTinPhieuYeuCau.input_magd, this.thuebao_id)
            this.loading(false)
            if(kt==0){
                this.$toast.error('Thuê bao không cùng mã khách hàng với thuê bao đã thay đổi loại hình !')
                return
            }
            this.loading(true)
            await this.TaoDuLieu_HDTB(true)
            await this.TaoDuLieu_HDTBDV()
            await this.TaoDuLieu_THONGTIN_TT(true)
            await this.TaoDuLieu_CT_TIENHD(true)
            await this.TaoDuLieu_DiaChi(true)

            this.dsHDTB_CD = []
            this.dsHDTB_ADSL = []
            this.dsHDTB_GP = []
            this.dsHDTB_MEWAN = []
            this.dsHDTB_TSL = []
            this.dsHDTB_IMS = []
            await this.CapNhat_CTKM(this.hdtb_id)

            for(let i=0;i<this.dsCTM_TBI.length;i++){
                this.dsCTM_TBI[i].HDTB_ID=this.hdtb_id
            }

            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.TaoDuLieu_HDTB_CD(true)
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){

            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                await this.TaoDuLieu_HDTB_ADSL(true)
            }else if(this.dichvu_vt_selected==DichVuVienThong.GPHONE){

            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.TaoDuLieu_HDTB_MGWAN(true)
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.TaoDuLieu_HDTB_TSL(true)
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.TaoDuLieu_HDTB_IMS(true)
            }

            await this.TaoDuLieuBC(true)

            this.loading(false)

            let dataHDTB={
                HUONGGIAO_TN:huonggiao_tn,
                HD_THUEBAO:this.dsHDTB,
                HDTB_DUNGTHU:[],
                HDTB_DV:this.dsHDTBDV,
                CT_TIENHD:this.dsCTTHD,
                DIACHI_HDTB:this.dsDiaChiHDTB,
                DIACHI:this.dsDiaChi,
                KHUYENMAI_HDTB:this.dsKM_HDTB,
                DATCOC_HDTB:this.dsDC_HDTB,
                HDTB_KV:this.dsHDTB_KV,
                CT_MUA_TBI:this.dsCTM_TBI,
                HDTB_CD:this.dsHDTB_CD,
                HDTB_DD:this.dsHDTB_DD,
                TIEN_HDTB_TC:this.dsTIEN_HDTB_TC,
                HDTB_IMS:this.dsHDTB_IMS,
                HDTB_ADSL:this.dsHDTB_ADSL,
                HDTB_GP:this.dsHDTB_GP,
                HDTB_MGWAN:this.dsHDTB_MEWAN,
                HDTB_TSL:this.dsHDTB_TSL,
                THONGTIN_TT:[],
                TIEN_HDTB:this.dsTIEN_HDTB_TC,
                HDTB_LOAIIP:[]
            }
            this.loading(true)
            let resultThemHDTB=await this.fn_themtb_cdlh_bancheo(JSON.stringify(dataHDTB))
            this.loading(false)
            if(resultThemHDTB!=1){
                this.$toast.error(resultThemHDTB)
                return
            }
            if(this.tinh_thicong>0){
                let dataBC={
                    HD_BANCHEO:this.dsHDBC,
                    HDTB_BANCHEO:this.dsHDTBBC,
                    HDBC_MGWAN:this.dsHDBCMGWAN,
                    HDBC_ADSL:this.dsHDBCADSL,
                    HDBC_CD:this.dsHDBCCD,
                    HDBC_TSL:this.dsHDBCTSL,
                    HDBC_IMS:this.dsHDBCIMS,
                    DANGKY_DVGT:this.dsDKDVGTBC
                }
                //fn_themmoi_hdbancheo_biendong
                this.loading(true)
                let resultThemHDBC=await this.fn_themmoi_hdbancheo_biendong(JSON.stringify(dataBC))
                this.loading(false)
                if(resultThemHDBC==1){
                    this.$toast.success('Thuê bao không cùng mã khách hàng với thuê bao đã thay đổi loại hình !')
                }else{
                    this.$toast.error(resultThemHDBC)
                    return
                }
            }
            await this.setActiveActions(3)
            //Thêm hàm hiển thị lại thông tin khách hàng sau khi Insert/Update hợp đồng
            this.loading(true)
            let ma_gd=await this.$refs.thongTinPhieuYeuCau.getMaGD()
            let dshd = await this.lay_ds_hopdong_theo_ma_gd({
                ma_gd: ma_gd,
                loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                donvi_id: this.$root.token.getDonViID(),
                tthd_id: TrangThaiHD.MOI,
                nhanvien_id: this.$root.token.getNhanVienID(),
                donvi_dl_id: 0
            })
            await this.HienThiTTHopDongKH(dshd)
            this.loading(false)

        },
        //Thêm mới/Cập nhật hợp đồng
        async GhiLai(){
            if(this.tinh_thicong>0&&(this.thongso_ban.muccuoctb_id==0||this.thongso_tc.muccuoctb_id==0)&&this.dichvu_vt_selected!=DichVuVienThong.TSL&&this.dichvu_vt_selected!=DichVuVienThong.NOIBO_2DIEM){
                this.$toast.error('Bạn chưa chọn mức cước cho thuê bao bán và thi công. Ân vào nút chọn để lấy thông tin')
                return
            }
            if(this.kenh_trang&&(this.thongso_ban.muccuoctb_id==0||this.thongso_tc.muccuoctb_id==0)&&this.dichvu_vt_selected!=DichVuVienThong.TSL&&this.dichvu_vt_selected!=DichVuVienThong.NOIBO_2DIEM){
                this.$toast.error('Bạn chưa chọn thông số cho kênh trắng. Ân vào nút chọn để lấy thông tin')
                return
            }
            if(this.kenh_trang&&(this.thongso_ban.muccuoctb_id == 0 || this.thongso_tc_dau.muccuoctb_id == 0 || this.thongso_tc_cuoi.muccuoctb_id == 0)&&(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM)){
                this.$toast.error('Bạn chưa chọn thông số cho kênh trắng. Ấn vào điểm đầu, điểm cuối để lấy thông tin')
                return
            }

            await this.CapNhat()

        },
        async CapNhat(){
            var matb_neo
            var async_status = false // Biến kiểm tra việc đồng bộ dữ liệu VinaPhone, MyTV...
            this.loading(true)
            if(!(await this.KiemTraDL_KhachHang())){
                this.loading(false)
                return
            }
            if(!(await this.KiemTraDL_ThueBao())){
                this.loading(false)
                return
            }
            if(!(await this.KiemTraDL_KhuyenMai())){
                this.loading(false)
                return
            }
            if (!this.kenh_trang){
                let result_1=await this.lay_danhba_theo_thuebaoid(this.tinh_thicong, this.thuebao_tc_id, this.dichvu_vt_selected, 0, 2)
                if (!result_1 || result_1.length === 0) {
                    this.$toast.error('Không lấy đc dữ liệu thuê bao ở tỉnh thi công')
                    this.loading(false)
                    return;
                }
            }
            this.loading(false)

            var batbuoc_sothang_ck = '0'
            let dsTS=await this.lay_ds_thamso_md_mats('BATBUOC_SOTHANG_CK')
            if(dsTS.length>0){
                batbuoc_sothang_ck=dsTS[0].ten_ts
            }
            if (batbuoc_sothang_ck == '1'){
                if(this.$refs.thongTinKhachHang.input_thang_ck.trim()==''||this.$refs.thongTinKhachHang.input_thang_ck.trim()=='0'){
                    let resultConfirm = await this.confirm('Hợp đồng chưa nhập số tháng cam kết. Bạn có muốn tiếp tục?','Thông báo')
                    if(resultConfirm==0){
                        return
                    }
                }
            }
            //
            var tocdotn_id = -1
            var muccuoctn_id = -1
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){

            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                tocdotn_id=this.$refs.bangRongCoDinh.tocdo_moi_selected?this.$refs.bangRongCoDinh.tocdo_moi_selected:-1
                if(this.loaitb_id==LoaiHinhTB.INTERNET_ADSL||this.loaitb_id == LoaiHinhTB.INTERNET_FTTH||this.loaitb_id == LoaiHinhTB.WIFI_FIBER){
                    muccuoctn_id=this.$refs.bangRongCoDinh.muccuoc_moi_selected?this.$refs.bangRongCoDinh.muccuoc_moi_selected:-1
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                tocdotn_id = this.$refs.megawan.tocdo_cir_moi_selected?this.$refs.megawan.tocdo_cir_moi_selected:-1
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                tocdotn_id=this.$refs.kenhThuRieng.tocdo_moi_selected?this.$refs.kenhThuRieng.tocdo_moi_selected:-1
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                
            }

            let nhapmoi=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?false:true
            if(nhapmoi){
                //kiemtra_danhba_khi_themmoi
                let ktdb_themmoi=await this.kiemtra_danhba_khi_themmoi(this.getMaTB(), this.khachhang_id, this.thuebao_id, this.dichvuvt_id)
                if(ktdb_themmoi==0){
                    this.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được')
                    return
                }

                //     if (!bangts.KIEMTRA_LHTB_KLD(Convert.ToInt32(cboKieuLD.SelectedValue), loaitb_id))
                //     {
                //         string sloaitb = checkdata.MAP_ID("loaihinh_tb", DatabaseConstants.DB2 + ".loaihinh_tb", "where loaitb_id = " + loaitb_id);
                //         Message_Box.ShowWarning("Bạn không được chuyển đổi cho loại hình thuê bao '" + sloaitb + "'");
                //         return;
                //     }
                if(this.$refs.thongTinPhieuYeuCau.loaihinh_selected!=LoaiHinhTB.INTERNET_MYTV){
                    if(this.$refs.thongTinPhieuYeuCau.loaihinh_selected==this.getLoaiHinhTB_ID_Moi()){
                        this.$toast.error('Loại hình thuê bao mới phải khác loại hình thuê bao cũ')
                        return
                    }
                }
                //     //Lam them ngay 04/10/2010: Kiem tra thue bao co dang lap hop dong khac khong
                //     if (WinUICommon.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(txtMaTB.Text.Trim(), "0", Convert.ToInt32(cboDichVuVT.SelectedValue), LoaiHopDong.CHUYENDOI_LH))
                //     {
                //         return;
                //     }

                //     if (loaihinhtbcu_id == LoaiHinhTB.ISDN2B_CD || loaihinhtbcu_id == LoaiHinhTB.ISDN2B_CQ || loaihinhtbcu_id == LoaiHinhTB.ISDN30B_CD || loaihinhtbcu_id == LoaiHinhTB.ISDN30B_CQ)
                //     {
                //         if (isdn_sochinh)
                //         {
                //             for (int i = 0; i < ds_isdn.Tables[0].Rows.Count; i++)
                //             {
                //                 DataRow dr = ds_isdn.Tables[0].Rows[i];
                //                 if (WinUICommon.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(dr["ma_tb"].ToString(), "0", Convert.ToInt32(cboDichVuVT.SelectedValue), LoaiHopDong.CHUYENDOI_LH))
                //                 {
                //                     return;
                //                 }
                //             }
                //         }
                //     }

                //layhuonggiao_tiepnhan_v2
                var j_loaikenh_id=0
                if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                    j_loaikenh_id=this.$refs.megawan.loaikenh_moi_selected
                }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                    j_loaikenh_id=this.$refs.kenhThuRieng.loaikenh_moi_selected
                }else{ 
                    j_loaikenh_id=0
                }
                let ds=await this.layhuonggiao_tiepnhan_v2({
                    dichvuvt_id: this.dichvu_vt_selected,
                    kieuld_id: this.$refs.thongTinPhieuYeuCau.kieu_yc_selected?this.$refs.thongTinPhieuYeuCau.kieu_yc_selected:0,
                    loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                    loaikenh_id: j_loaikenh_id,
                    loaitb_id: this.getLoaiHinhTB_ID_Moi(),
                    muccuoc_id: muccuoctn_id,
                    phanvung_id: this.$root.token.getPhanVungID(),
                    tocdo_id: tocdotn_id
                })
                var huonggiao_tn = 0
                this.luong_id=0
                if(ds.length<=0){
                    this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
                    return
                }
                huonggiao_tn=ds[0].huonggiao_id
                this.quytrinh_id=ds[0].quytrinh_id
                this.luong_id=ds[0].luong_id
                if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                    //TSL lay quy trinh huong giao
                    var tocdo_tc_dau = 0
                    var muccuoc_id_tc_dau = 0
                    var tocdo_tc_cuoi = 0
                    var muccuoc_id_tc_cuoi = 0
                    if(!this.kenh_trang){
                        tocdo_tc_dau=this.dsDBTBBC[0].tocdo_id
                        tocdo_tc_cuoi=this.dsDBTBBC[1].tocdo_id
                    }
                    //Lấy hướng giao, quy trình tại điểm đầu
                    this.quytrinh_tc_tn_dau = 0
                    this.huonggiao_tc_tn_dau = 0
                    let rs_qt_dau=await this.layhuonggiao_tiepnhan({
                        kieuld_id: this.thongso_tc_dau.kieuld_id?this.thongso_tc_dau.kieuld_id:0,
                        loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                        loaitb_id: this.$refs.thongTinPhieuYeuCau.loaihinh_selected,
                        muccuoc_id: this.thongso_tc_dau.muccuoc_id?this.thongso_tc_dau.muccuoc_id:0,
                        tinhthicong_id: this.tinh_dau,
                        tocdo_id: tocdo_tc_dau
                    })
                    if(rs_qt_dau.length<=0){
                        this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công điểm đầu. Liên hệ Admin để xử lý')
                        return
                    }
                    this.quytrinh_tc_tn_dau = rs_qt_dau[0].quytrinh_id
                    this.huonggiao_tc_tn_dau = rs_qt_dau[0].huonggiao_id

                    // Lấy hướng giao, quy trình tại điểm cuối
                    this.quytrinh_tc_tn_cuoi = 0
                    this.huonggiao_tc_tn_cuoi = 0
                    let rs_qt_cuoi=await this.layhuonggiao_tiepnhan({
                        kieuld_id: this.thongso_tc_cuoi.kieuld_id?this.thongso_tc_cuoi.kieuld_id:0,
                        loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                        loaitb_id: this.$refs.thongTinPhieuYeuCau.loaihinh_selected,
                        muccuoc_id: this.thongso_tc_cuoi.muccuoc_id?this.thongso_tc_cuoi.muccuoc_id:0,
                        tinhthicong_id: this.tinh_cuoi,
                        tocdo_id: tocdo_tc_cuoi
                    })
                    if(rs_qt_cuoi.length<=0){
                        this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công điểm cuối. Liên hệ Admin để xử lý')
                        return
                    }
                    this.quytrinh_tc_tn_cuoi = rs_qt_cuoi[0].quytrinh_id
                    this.huonggiao_tc_tn_cuoi = rs_qt_cuoi[0].huonggiao_id
                    // #endregion
                }else{
                    //Lấy hướng hợp đồng thuê bao thi công
                    if(this.tinh_thicong>0){
                        this.quytrinh_tc_tn = 0
                        this.huonggiao_tc_tn = 0
                        let rs_qt=await this.layhuonggiao_tiepnhan({
                            kieuld_id: this.thongso_tc.kieuld_id?this.thongso_tc.kieuld_id:0,
                            loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                            loaitb_id: this.getLoaiHinhTB_ID_Moi(),
                            muccuoc_id: this.thongso_tc.muccuoc_id?this.thongso_tc.muccuoc_id:0,
                            tinhthicong_id: this.tinh_thicong,
                            tocdo_id: this.thongso_tc.tocdo_id?this.thongso_tc.tocdo_id:0
                        })
                        if(rs_qt.length<=0){
                            this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
                            return
                        }
                        this.quytrinh_tc_tn=rs_qt[0].quytrinh_id
                        this.huonggiao_tc_tn=rs_qt[0].huonggiao_id

                    }
                }
                this.kt_adsl = true
                this.loading(true)
                await this.TaoDuLieu(true)
                await this.TaoDuLieuBC(true)
                this.loading(false)
                if (!this.kt_adsl){
                    return
                }
                //Thêm code đẩy lên VinaPhone
                // ....Code Di động tạm bỏ....//
                let data={
                    THEMMOI:1,
                    HUONGGIAO_ID: huonggiao_tn,
                    HD_KHACHHANG:this.dsHDKH,
                    HD_THUEBAO:this.dsHDTB,
                    HDTB_DUNGTHU:[],
                    HDTB_DV:this.dsHDTBDV,
                    CT_TIENHD:this.dsCTTHD,
                    DIACHI_HDTB:this.dsDiaChiHDTB,
                    DIACHI:this.dsDiaChi,
                    KHUYENMAI_HDTB:this.dsKM_HDTB,
                    DATCOC_HDTB:this.dsDC_HDTB,
                    HDTB_KV:this.dsHDTB_KV,
                    CT_MUA_TBI:this.dsCTM_TBI,
                    HDTB_CD:this.dsHDTB_CD,
                    HDTB_DD:this.dsHDTB_DD,
                    TIEN_HDTB_TC:this.dsTIEN_HDTB_TC,
                    HDTB_IMS:this.dsHDTB_IMS,
                    HDTB_ADSL:this.dsHDTB_ADSL,
                    HDTB_GP:this.dsHDTB_GP,
                    HDTB_MGWAN:this.dsHDTB_MEWAN,
                    HDTB_TSL:this.dsHDTB_TSL,
                    THONGTIN_TT:[],
                    TIEN_HDTB:this.dsTIEN_HDTB_TC,
                    HDTB_LOAIIP:[]
                }
                this.loading(true)
                let resultHD=await this.fn_ins_update_hd_cdlh_bancheo(JSON.stringify(data))
                this.loading(false)
                if(resultHD!=1){
                    this.$toast.error(resultHD)
                    return
                }
                if(this.tinh_thicong>0||this.kenh_trang){
                    let dataBC={
                        HD_BANCHEO:this.dsHDBC,
                        HDTB_BANCHEO:this.dsHDTBBC,
                        HDBC_MGWAN:this.dsHDBCMGWAN,
                        HDBC_ADSL:this.dsHDBCADSL,
                        HDBC_CD:this.dsHDBCCD,
                        HDBC_TSL:this.dsHDBCTSL,
                        HDBC_IMS:this.dsHDBCIMS,
                        DANGKY_DVGT:this.dsDKDVGTBC
                    }
                    //fn_themmoi_hdbancheo_biendong
                    this.loading(true)
                    let resultThemHDBC=await this.fn_themmoi_hdbancheo_biendong(JSON.stringify(dataBC))
                    this.loading(false)
                    if(resultThemHDBC==1){
                        this.$toast.success('Cập nhật hợp đồng thay đổi loại hình thuê bao thành công.')
                    }else{
                        this.$toast.error(resultThemHDBC)
                        return
                    }
                }

            }else{
                //cập nhật
                let ktdb_themmoi=await this.kiemtra_danhba_khi_themmoi(this.getMaTB(), this.khachhang_id, this.thuebao_id, this.dichvuvt_id)
                if(ktdb_themmoi==0){
                    this.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được')
                    return
                }
                //layhuonggiao_tiepnhan_v2
                var j_loaikenh_id=0
                if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                    j_loaikenh_id=this.$refs.megawan.loaikenh_moi_selected
                }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                    j_loaikenh_id=this.$refs.kenhThuRieng.loaikenh_moi_selected
                }else{ 
                    j_loaikenh_id=0
                }
                let ds=await this.layhuonggiao_tiepnhan_v2({
                    dichvuvt_id: this.dichvu_vt_selected,
                    kieuld_id: this.$refs.thongTinPhieuYeuCau.kieu_yc_selected?this.$refs.thongTinPhieuYeuCau.kieu_yc_selected:0,
                    loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                    loaikenh_id: j_loaikenh_id,
                    loaitb_id: this.getLoaiHinhTB_ID_Moi(),
                    muccuoc_id: muccuoctn_id,
                    phanvung_id: this.$root.token.getPhanVungID(),
                    tocdo_id: tocdotn_id
                })
                var huonggiao_tn = 0
                this.luong_id=0
                if(ds.length<=0){
                    this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
                    return
                }
                huonggiao_tn=ds[0].huonggiao_id
                this.quytrinh_id=ds[0].quytrinh_id
                this.luong_id=ds[0].luong_id
                this.kt_adsl = true
                this.loading(true)
                await this.TaoDuLieu(false)
                this.loading(false)
                if (!this.kt_adsl){
                    return
                }
                let data={
                    THEMMOI:0,
                    HUONGGIAO_ID: huonggiao_tn,
                    HD_KHACHHANG:this.dsHDKH,
                    HD_THUEBAO:this.dsHDTB,
                    HDTB_DUNGTHU:[],
                    HDTB_DV:this.dsHDTBDV,
                    CT_TIENHD:this.dsCTTHD,
                    DIACHI_HDTB:this.dsDiaChiHDTB,
                    DIACHI:this.dsDiaChi,
                    KHUYENMAI_HDTB:this.dsKM_HDTB,
                    DATCOC_HDTB:this.dsDC_HDTB,
                    HDTB_KV:this.dsHDTB_KV,
                    CT_MUA_TBI:this.dsCTM_TBI,
                    HDTB_CD:this.dsHDTB_CD,
                    HDTB_DD:this.dsHDTB_DD,
                    TIEN_HDTB_TC:this.dsTIEN_HDTB_TC,
                    HDTB_IMS:this.dsHDTB_IMS,
                    HDTB_ADSL:this.dsHDTB_ADSL,
                    HDTB_GP:this.dsHDTB_GP,
                    HDTB_MGWAN:this.dsHDTB_MEWAN,
                    HDTB_TSL:this.dsHDTB_TSL,
                    THONGTIN_TT:[],
                    TIEN_HDTB:this.dsTIEN_HDTB_TC,
                    HDTB_LOAIIP:[]
                }
                this.loading(true)
                let resultHD=await this.fn_ins_update_hd_cdlh_bancheo(JSON.stringify(data))
                this.loading(false)
                if(resultHD!=1){
                    this.$toast.error(resultHD)
                    return
                }
                if (this.tinh_thicong > 0){
                    this.loading(true)
                    await this.TaoDuLieuBC(false)
                    this.loading(false)
                    //fn_capnhat_hdbancheo_biendong
                    let dataBC={
                        HD_BANCHEO:this.dsHDBC,
                        HDTB_BANCHEO:this.dsHDTBBC,
                        HDBC_MGWAN:this.dsHDBCMGWAN,
                        HDBC_ADSL:this.dsHDBCADSL,
                        HDBC_CD:this.dsHDBCCD,
                        HDBC_TSL:this.dsHDBCTSL,
                        HDBC_IMS:this.dsHDBCIMS,
                        DANGKY_DVGT:this.dsDKDVGTBC,
                        DIACHI_BANCHEO:[]
                    }
                    this.loading(true)
                    let resultCapNhatBC=await this.fn_capnhat_hdbancheo_biendong(JSON.stringify(dataBC))
                    this.loading(false)
                    if(resultCapNhatBC==1){
                        this.$toast.success('Cập nhật hợp đồng thay đổi loại hình thuê bao thành công.')
                    }else{
                        this.$toast.error(resultCapNhatBC)
                        return
                    }
                }
                // Đối với loại hợp đồng có thay đổi tốc độ, mức cước
                // Phải lấy lại huonggiao_tn để update vào bảng giaophieu
                this.loading(true)
                await this.update_huonggiao_tiepnhan(this.hdtb_id, huonggiao_tn)
                this.loading(false)
                
            }
            await this.setActiveActions(3)
            //Thêm hàm hiển thị lại thông tin khách hàng sau khi Insert/Update hợp đồng
            this.loading(true)
            let ma_gd=await this.$refs.thongTinPhieuYeuCau.getMaGD()
            let ds = await this.lay_ds_hopdong_theo_ma_gd({
                ma_gd: ma_gd,
                loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                donvi_id: this.$root.token.getDonViID(),
                tthd_id: TrangThaiHD.MOI,
                nhanvien_id: this.$root.token.getNhanVienID(),
                donvi_dl_id: 0
            })
            await this.HienThiTTHopDongKH(ds)
            this.loading(false)
        },
        async KiemTraDL_KhachHang(){
            let ngay_cn=moment(new Date()).format('DD/MM/YYYY')
            let ngay_yc=this.$refs.thongTinPhieuYeuCau.ngay_yc!=null?this.$refs.thongTinPhieuYeuCau.ngay_yc:ngay_cn
            let ngay_lhd=this.$refs.thongTinKhachHang.ngay_lhd!=null?this.$refs.thongTinKhachHang.ngay_lhd:ngay_cn
            if(!this.KiemTraDK_LHD(ngay_yc, ngay_cn)){
                this.$toast.error('Ngày yêu cầu không được lớn hơn ngày hiện tại!')
                return false
            }
            if(!this.KiemTraDK_LHD(ngay_lhd, ngay_yc )){
                this.$toast.error('Bạn đang lập phụ lục cho khách hàng cũ. Ngày yêu cầu không được nhỏ hơn ngày lập hợp đồng!')
                return false
            }
            if(this.$refs.thongTinKhachHang.input_ten_kh.trim()==''){
                this.$toast.error('Hãy nhập tên khách hàng !')
                this.$refs.thongTinKhachHang.focusTenKH()
                return false
            }
            if(this.$refs.thongTinKhachHang.input_makh.trim()==''&&this.khachhang_id!=0){
                this.$toast.error('Kiểm tra lại mã khách hàng !')
                this.$refs.thongTinKhachHang.focusMaKH()
                return false
            }
            if(this.thamso.khong_batbuoc_ctv==0){
                if(!this.checkCTV||this.ctv.ten_ctv==''||this.ctv.ctv_id==null||this.ctv.ctv_id==0){
                    this.$toast.error('Bạn chưa chọn nhân viên phát triển!')
                    this.$refs.inputCTV.focus()
                    return false
                }
            }
            if(this.thamso.batbuoc_gioithieu_loaihinh==1){
                if(!this.checkNguoiGT||this.nguoi_gt.ten_nguoi_gt==''||this.nguoi_gt.nguoi_gt_id==null||this.nguoi_gt.nguoi_gt_id==0){
                    this.$toast.error('Bạn chưa chọn người giới thiệu hợp đồng/phụ lục')
                    this.$refs.inputNguoiGT.focus()
                    return false
                }
            }
            if(this.checkCTV&&this.ctv.ten_ctv==''){
                this.$toast.error('Bạn chưa chọn cộng tác viên !')
                this.$refs.inputCTV.focus()
                return false
            }
            if(this.checkNguoiGT&&this.nguoi_gt.ten_nguoi_gt==''){
                this.$toast.error('Bạn chưa chọn người giới thiệu hợp đồng !')
                this.$refs.inputNguoiGT.focus()
                return false
            }
            let kiemTraMaKh=await this.kiemtra_makh_matt_magd(this.$refs.thongTinKhachHang.input_makh.trim(), LoaiHopDong.CHUYENDOI_LH, 1)
            if(kiemTraMaKh==0){
                this.$toast.error('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
                this.$refs.thongTinKhachHang.focusMaKH()
                return false
            }
            if(this.$refs.thongTinPhieuYeuCau.input_magd.trim()!=''){
                let kiemTraMaGD=await this.kiemtra_makh_matt_magd(this.$refs.thongTinPhieuYeuCau.input_magd.trim(), LoaiHopDong.CHUYENDOI_LH, 3)
                if(kiemTraMaGD==0){
                    this.$toast.error('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
                    return false
                }

            }

            if(this.$refs.thongTinKhachHang.input_sdt.trim()==''){
                this.$toast.error('Hãy nhập số điện thoại liên hệ của khách hàng.')
                this.$refs.thongTinKhachHang.focusSoDT()
                return false
            }
            if(this.$refs.thongTinKhachHang.input_sdt.trim().length>10){
                this.$toast.error('Số điện thoại liên hệ của khách hàng không được quá 10 ký tự.\nBạn hãy bỏ số 0 ở đầu (nếu có)!')
                this.$refs.thongTinKhachHang.focusSoDT()
                return false
            }
            if(this.$refs.thongTinKhachHang.input_sdt.trim().length<7){
                this.$toast.error('Số điện thoại liên hệ của khách hàng không được ít hơn 7 ký tự.')
                this.$refs.thongTinKhachHang.focusSoDT()
                return false
            }

            return true

        },
        async KiemTraDL_ThueBao(){
            //Kiểm tra thông tin chung
            if(this.$refs.thongTinKhachHang.input_ma_hd.trim()==''&&this.khachhang_id!=0){
                this.$toast.error('Hãy nhập mã hợp đồng !')
                this.$refs.thongTinKhachHang.focusMaHD()
                return false
            }
            if(this.isdn_sochinh&&this.ds_isdn.length>1){
                this.$toast.error('Không chuyển đổi loại hình cho thuê bao ISDN số chính có số phụ đi kèm!')
                return false
            }
            if(this.kieuld_id==KieuLapDat.CHUYENGP_TRATRUOC_SANG_TRASAU){
                if(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected==null||this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected==0){
                    this.$toast.error('Bạn chưa chọn mức cước thuê bao cho kiểu lắp đặt này!')
                    return false
                }
            }
            if(this.$refs.thongTinKhachHang.input_ten_tb.trim()==''){
                this.$toast.error('Hãy nhập tên thuê bao !')
                this.$refs.thongTinKhachHang.focusTenTB()
                return false
            }
            const indexKieuLD=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.findIndex(x=>x.kieuld_id==this.$refs.thongTinPhieuYeuCau.kieu_yc_selected)
            if(indexKieuLD<0){
                this.$toast.error('Không có kiểu lắp đặt cho loại hình '+this.$refs.thongTinPhieuYeuCau.getLoaiHinhTB())
                return false
            }
            let loaitb_id=Number(await this.fn_map_db_thuebao(this.thuebao_id, 8, '-1'))
            if(loaitb_id!=this.$refs.thongTinPhieuYeuCau.loaihinh_selected){
                //getMaTB
                this.$toast.error('Loại hình '+this.$refs.thongTinPhieuYeuCau.getLoaiHinhTB()+' không phải là loại hình của thuê bao '+this.getMaTB()+' !')
                return false
            }
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                if(this.$refs.coDinh.getLoaiHinhTBMoi()==''){
                    this.$toast.error('Bạn chưa chọn loại hình mới !')
                    this.$refs.coDinh.focusLoaiHinhTBMoi()
                    return false
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                if(this.$refs.bangRongCoDinh.getLoaiHinhTBMoi()==''){
                    this.$toast.error('Bạn chưa chọn loại hình mới !')
                    this.$refs.bangRongCoDinh.focusLoaiHinhTBMoi()
                    return false
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                if(this.$refs.ims.getLoaiHinhTBMoi()==''){
                    this.$toast.error('Bạn chưa chọn loại hình mới !')
                    this.$refs.ims.focusLoaiHinhTBMoi()
                    return false
                }  
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                if(this.$refs.kenhThuRieng.getLoaiHinhTBMoi()==''){
                    this.$toast.error('Bạn chưa chọn loại hình mới !')
                    this.$refs.kenhThuRieng.focusLoaiHinhTBMoi()
                    return false
                } 
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                if(this.$refs.megawan.getLoaiHinhTBMoi()==''){
                    this.$toast.error('Bạn chưa chọn loại hình mới !')
                    this.$refs.megawan.focusLoaiHinhTBMoi()
                    return false
                }
            }
            //Kiểm tra dịch vụ cố định
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                if(this.kieuld_id==KieuLapDat.CHUYENTU_2B_SANG_30B){
                    if(this.loaihinhtb_id==LoaiHinhTB.ISDN2B_CD||this.loaihinhtb_id==LoaiHinhTB.ISDN2B_CQ
                        ||this.loaihinhtb_id==LoaiHinhTB.DIENTHOAI_CD){
                            this.$toast.error('Loại hình thuê bao mới phải là 30B+D !')
                            return false
                    }
                }
                if(this.kieuld_id==KieuLapDat.CHUYENTU_30B_SANG_2B){
                    if(this.loaihinhtb_id==LoaiHinhTB.ISDN30B_CD||this.loaihinhtb_id==LoaiHinhTB.ISDN30B_CQ||this.loaihinhtb_id==LoaiHinhTB.DIENTHOAI_CD){
                        this.$toast.error('Loại hình thuê bao mới phải là 2B+D !')
                        return false
                    }
                }
                if(this.kieuld_id==KieuLapDat.CHUYENTU_CODINH_SANG_ISDN){
                    if(this.loaihinhtb_id==LoaiHinhTB.DIENTHOAI_CD){
                        this.$toast.error('Loại hình thuê bao mới phải là ISDN !')
                        return false
                    }
                }
                if(this.kieuld_id==KieuLapDat.CHUYENTU_ISDN_SANG_CODINH){
                    if(this.loaihinhtb_id==LoaiHinhTB.ISDN30B_CD||this.loaihinhtb_id==LoaiHinhTB.ISDN30B_CQ
                    ||this.loaihinhtb_id==LoaiHinhTB.ISDN2B_CD||this.loaihinhtb_id==LoaiHinhTB.ISDN2B_CQ){
                        this.$toast.error('Loại hình thuê bao mới phải là Cố định !')
                        return false
                    }
                }

                if(this.$refs.thongTinPhieuYeuCau.disableMucCuocTB){
                    
                    if(this.$refs.coDinh.input_cuoctb_moi.trim()==''){
                        this.$toast.error('Chưa nhập mức cước thuê bao cho thuê bao!')
                        this.$refs.coDinh.focusCuocTBMoi()
                        return false
                    }
                    if(this.$refs.coDinh.loai_so_selected!=null&&this.$refs.coDinh.loai_so_selected==LOAISO_ISDN.SOPHU){
                        if(this.$refs.coDinh.input_sochinh_moi.trim()==''){
                            this.$toast.error('Chưa chọn số chính !')
                            //btnChonSoChinh.Focus();
                            return false
                        }
                        var id_sochinh=0
                        if(this.loaisochinh==1){
                            id_sochinh=this.thuebao_id_cha
                        }else{
                            id_sochinh=this.hdtb_id_cha
                        }
                        //
                        let kt=await this.kiemtra_tb_datmoi_isdn(id_sochinh, this.getLoaiHinhTB_ID_Moi(), this.loaisochinh)
                        if(kt==0){
                            this.$toast.error('Thuê bao chuyển đổi không cùng loại hình với số chính !')
                            return false
                        }

                    }

                }
            }
            //Kiểm tra khuyến mãi trả trước
            if(this.$refs.thongTinPhieuYeuCau.khuyenmai.checkKhuyenMai&&this.dsKM_HDTB.length==0){
                this.$toast.error('Bạn chưa chọn chi tiết khuyến mại!')
                return false
            }
            if(this.$refs.thongTinPhieuYeuCau.tratruoc.checkTraTruoc&&this.dsDC_HDTB.length==0){
                this.$toast.error('Bạn chưa chọn chi tiết trả trước!')
                return false
            }
            // thêm kiểm tra gói trước khi lập phụ lục thay đổi LHTB
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH||this.dichvu_vt_selected==DichVuVienThong.ADSL){
                if(this.$refs.thongTinPhieuYeuCau.ds_muccuoc_tb.length<=0){
                    this.$toast.error('Hãy nhập mức cước thuê bao!')
                    return false
                }
                var vtd_id=-1
                var vmc_id = 0
                if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                    vtd_id=this.$refs.bangRongCoDinh.tocdo_moi_selected
                    vmc_id=this.$refs.bangRongCoDinh.muccuoc_moi_selected
                }
                //kiemtra_thaydoi_goi_dadv_1
                let kq=await this.kiemtra_thaydoi_goi_dadv_1(this.thuebao_id, this.getLoaiHinhTB_ID_Moi(), vtd_id, vmc_id)
                if(kq!='1'){
                    this.$toast.error(kq)
                    return false
                }
            }
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL&&(this.loaihinhtb_id==LoaiHinhTB.INTERNET_ADSL||this.loaihinhtb_id==LoaiHinhTB.INTERNET_FTTH
            ||this.loaihinhtb_id==LoaiHinhTB.WIFI_FIBER)){
                if(this.thamso.nhap_tttc==1){
                    if(this.$refs.bangRongCoDinh.input_tttc.trim()==''){
                        this.$toast.error('Bạn phải nhập thông tin tính cước !')
                        this.$refs.bangRongCoDinh.focusInputTTTC()
                        return false
                    }
                }
            }
            var kieu=''
            let kieuLD=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x=>x.kieuld_id==this.$refs.thongTinPhieuYeuCau.kieu_yc_selected)
            kieu=kieuLD!=undefined?kieuLD.kieu:''

            if((kieu=='1'||kieu=='2'||kieu=='5')&&this.dichvu_vt_selected==DichVuVienThong.ADSL&&
                this.$refs.bangRongCoDinh.input_matn.trim()==''){
                this.$toast.error('Khi kiểu lắp đặt trên đường có sẵn(hoặc lắp kèm), mã truy nhập không được trống!')      
                this.$refs.bangRongCoDinh.focusInputMaTN()  
                return false

            }
            if((kieu=='1'||kieu=='2')&&this.dichvu_vt_selected==DichVuVienThong.IMS&&
                this.$refs.ims.input_matn.trim()==''){
                this.$toast.error('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập')
                this.$refs.ims.focusInputMaTN()
                return false

            }
            if((kieu=='1'||kieu=='2')&&this.dichvu_vt_selected==DichVuVienThong.IMS){
                if(!this.thongso_tc.ma_doicap||this.thongso_tc.ma_doicap.toString().trim()==''){
                    this.$toast.error('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập')
                }
                return false

            }
            //Kiểm tra thuê bao IMS
            if(this.dichvu_vt_selected==DichVuVienThong.IMS&&this.loaihinhtb_id==LoaiHinhTB.IMS_SIPTRUNKING){
                if(this.$refs.ims.input_sl_cuocgoi.trim()==''||this.$refs.ims.input_sl_cuocgoi.trim()=='0'){
                    this.$toast.error('Bạn chưa nhập số lượng cuộc gọi đồng thời!')
                    this.$refs.ims.focusSLCuocGoi()
                    return false
                }
            }
            return true
            
        },
        async KiemTraDL_KhuyenMai(){
            if(this.dsKM_HDTB.length==0&&this.$refs.thongTinPhieuYeuCau.khuyenmai.checkKhuyenMai){
                this.$toast.error('Hãy chọn chi tiết khuyến mại !')
                this.$refs.thongTinPhieuYeuCau.focusKhuyenMai()
                return false
            }
            return true
        },
        getLoaiHinhTB_ID_Moi(){
            var vloaitb_id_moi=0
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                vloaitb_id_moi=this.$refs.coDinh.loaihinh_tb_moi_selected?this.$refs.coDinh.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                vloaitb_id_moi=this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected?this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                vloaitb_id_moi=this.$refs.ims.loaihinh_tb_moi_selected?this.$refs.ims.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                vloaitb_id_moi=this.$refs.kenhThuRieng.loaihinh_tb_moi_selected?this.$refs.kenhThuRieng.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                vloaitb_id_moi=this.$refs.megawan.loaihinh_tb_moi_selected?this.$refs.megawan.loaihinh_tb_moi_selected:0
            }
            return vloaitb_id_moi
        },
        //dd/MM/yyyy
        KiemTraDK_LHD(thangnam_tt, thangnam_sys){
            console.log('thangnam_tt', thangnam_tt)
            console.log('thangnam_sys', thangnam_sys)
            let m_thangnam_tt=moment(thangnam_tt,'DD/MM/YYYY')
            let m_thangnam_sys=moment(thangnam_sys,'DD/MM/YYYY')
            if(Number(m_thangnam_tt.format('YYYY'))<Number(m_thangnam_sys.format('YYYY'))){
                return true
            }else if(Number(m_thangnam_tt.format('YYYY'))==Number(m_thangnam_sys.format('YYYY'))){
                if(Number(m_thangnam_tt.format('M'))<Number(m_thangnam_sys.format('M'))){
                    return true
                }else if(Number(m_thangnam_tt.format('M'))==Number(m_thangnam_sys.format('M'))){
                    if(Number(m_thangnam_tt.format('D'))<=Number(m_thangnam_sys.format('D'))){
                        return true
                    }else{
                        return false
                    }
                }else{
                    return false
                }

            }else {
                return false
            }
        },
        async TaoDuLieu(themmoi){
            await this.TaoDuLieu_HDKH(themmoi)
            await this.TaoDuLieu_HDTB(themmoi)
            await this.TaoDuLieu_DiaChi(themmoi)

            this.dsHDTB_CD = []
            this.dsHDTB_DD = []
            this.dsHDTB_ADSL = []
            this.dsHDTB_GP = []
            this.dsHDTB_TSL = []

            this.dsHDTB_MEWAN = []
            this.dsHDTBDV = []
            //hàm thừa
            await this.TaoDuLieu_THONGTIN_TT(themmoi)
            await this.TaoDuLieu_CT_TIENHD(true)
            await this.CapNhat_CTKM(this.hdtb_id)
            await this.TaoDuLieu_HDTBDV()
            await this.LoadDL_KV(this.khuvuc_id)

            for(let i=0;i<this.dsCTM_TBI.length;i++){
                this.dsCTM_TBI[i].HDTB_ID=this.hdtb_id
            }

            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.TaoDuLieu_HDTB_CD(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                // await this.TaoDuLieu_HDTB_DD(themmoi)
                // await this.TaoDuLieu_TIEN_HDTB_TC(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                await this.TaoDuLieu_HDTB_ADSL(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.GPHONE){

            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.TaoDuLieu_HDTB_MGWAN(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.TaoDuLieu_HDTB_TSL(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.TaoDuLieu_HDTB_IMS(themmoi)
            }


            //Bổ sung thong tin hdtb_id vao bang khuyenmai, dat coc
            for(let i=0;i<this.dsDC_HDTB.length;i++){
                this.dsDC_HDTB[i].HDTB_ID=this.hdtb_id
                
            }
            console.log('TaoDuLieu dsDC_HDTB', this.dsDC_HDTB)
            for(let i=0;i<this.dsKM_HDTB.length;i++){
                this.dsKM_HDTB[i].HDTB_ID=this.hdtb_id
            }
            console.log('TaoDuLieu dsKM_HDTB', this.dsKM_HDTB)



        },
        async TaoDuLieu_HDKH(themmoi){
            this.dsHDKH=[]
            
            let tratruoc=(this.$refs.thongTinPhieuYeuCau.tratruoc.checkTraTruoc&&this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected!=null)?this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected:0
            let result = await this.fn_tt_taodulieu_hdkh_cdlhtb(JSON.stringify({
                THUEBAO_ID:this.thuebao_id,
                NHAPMOI:themmoi?1:2,
                TRATRUOC:tratruoc,
                KHACHHANG_ID:this.khachhang_id,
                DONVI_ID:this.$root.token.getDonViID()
            }))
            if(result!=null&&result.ERROR_CODE==1){
                if(themmoi){
                    this.hdkh_id=result.RESULT.HDKH_ID
                }
                var object=createObjectHDKH(result.RESULT.LAY_DS_DBKH_THEO_KHID[0])
                object.HDKH_ID=this.hdkh_id
                if(object.PHANVUNG_ID==null){
                    object.PHANVUNG_ID=this.$root.token.getPhanVungID()
                }
                //Object.assign(object, result.RESULT.LAY_DS_DBKH_THEO_KHID[0])
                if(themmoi){
                    this.$refs.thongTinPhieuYeuCau.input_magd = result.RESULT.MA_GD
                    object.MA_GD = this.$refs.thongTinPhieuYeuCau.input_magd
                    object.MA_HD = this.$refs.thongTinKhachHang.input_ma_hd.trim()
                }else{
                    object.MA_GD = this.$refs.thongTinPhieuYeuCau.input_magd
                    object.MA_HD = this.$refs.thongTinKhachHang.input_ma_hd.trim()
                }
                object.MA_KH = this.$refs.thongTinKhachHang.input_makh.trim()
                if(this.khachhang_id>0){
                    object.KHACHHANG_ID=this.khachhang_id
                }else{
                    object.KHACHHANG_ID=0
                }
                if(this.hdkh_cha_id){
                    object.HDKH_CHA_ID=this.hdkh_cha_id
                }else{
                    object.HDKH_CHA_ID=0
                }
                object.TEN_KH=this.$refs.thongTinKhachHang.input_ten_kh.trim()
                object.DIACHI_KH=this.$refs.thongTinKhachHang.input_diachi_kh.trim()

                object.NGAYLAP_HD = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
                object.NGAY_YC = moment(this.$refs.thongTinPhieuYeuCau.ngay_yc, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
                object.DONVI_ID = this.$root.token.getDonViID()
                object.NHANVIEN_ID = this.$root.token.getNhanVienID()
                object.KHLON_ID=result.RESULT.KHLON_ID
                object.PHANLOAIKH_ID=result.RESULT.PHANLOAIKH_ID

                object.LOAIHD_ID = LoaiHopDong.CHUYENDOI_LH
                object.NGUOI_CN =this.$root.token.getUserName()
                object.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
                object.MAY_CN = 'localhost'
                object.IP_CN = '127.0.0.1'

                //parse date NGAY_SN/NGAYCAP
                object.NGAY_SN=object.NGAY_SN!=null?moment(object.NGAY_SN).format('DD/MM/YYYY HH:mm:ss'):null
                object.NGAYCAP=object.NGAYCAP!=null?moment(object.NGAYCAP).format('DD/MM/YYYY HH:mm:ss'):null
                object.NGAY_HK=object.NGAY_HK!=null?moment(object.NGAY_HK).format('DD/MM/YYYY HH:mm:ss'):null
                object.NGAYHETHAN_GT=object.NGAYHETHAN_GT!=null?moment(object.NGAYHETHAN_GT).format('DD/MM/YYYY HH:mm:ss'):null

                if(this.checkCTV&&this.ctv.ten_ctv!=null&&this.ctv.ten_ctv!=''){
                    object.CTV_ID = this.ctv.ctv_id
                }else{
                    object.CTV_ID = 0
                }
                if(this.checkNguoiGT&&this.nguoi_gt.ten_nguoi_gt!=null&&this.nguoi_gt.ten_nguoi_gt!=''){
                    object.NHANVIENGT_ID = this.nguoi_gt.nguoi_gt_id
                }else{
                    object.NHANVIENGT_ID = 0
                }

                object.SO_DT = this.$refs.thongTinKhachHang.input_sdt.trim()
                object.KIEUHD_ID=this.kieu_pl_selected!=null?this.kieu_pl_selected:0
                object.KENHTN_ID=this.iKenhTN
                //Bổ sung cho HCM theo y/c chị Tô Thảo - TP DHNV TTKD
                object.MA_DUAN = this.$refs.thongTinKhachHang.input_ma_duan.trim()

                this.dsHDKH.push(object)

                console.log('dsHDKH', this.dsHDKH)



            }else if(result!=null&&result.ERROR_CODE==0){
                this.$toast.error(result.MESSAGE)
            }
        },
        async TaoDuLieu_HDTB(themmoi){
            this.dsHDTB=[]
            var object=createObjectHDTB()
            var matb=''
            var loaihinhtbmoi_id=0
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                matb=this.$refs.coDinh.input_matb.trim()
                object.MA_TB=matb
                loaihinhtbmoi_id=this.$refs.coDinh.loaihinh_tb_moi_selected!=null?this.$refs.coDinh.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                matb=this.$refs.diDong.input_matb.trim()
                object.MA_TB=matb
                loaihinhtbmoi_id=this.$refs.diDong.loaihinh_tb_moi_selected!=null?this.$refs.diDong.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                matb=this.$refs.bangRongCoDinh.input_matb.trim()
                object.MA_TB=matb
                loaihinhtbmoi_id=this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected!=null?this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                matb=this.$refs.megawan.input_matb.trim()
                object.MA_TB=matb
                loaihinhtbmoi_id=this.$refs.megawan.loaihinh_tb_moi_selected!=null?this.$refs.megawan.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                matb=this.$refs.kenhThuRieng.input_matb.trim()
                object.MA_TB=matb
                loaihinhtbmoi_id=this.$refs.kenhThuRieng.loaihinh_tb_moi_selected!=null?this.$refs.kenhThuRieng.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                matb=this.$refs.ims.input_matb.trim()
                object.MA_TB=matb
                loaihinhtbmoi_id=this.$refs.ims.loaihinh_tb_moi_selected!=null?this.$refs.ims.loaihinh_tb_moi_selected:0
            }
            if(themmoi){
                this.hdtb_id=await this.get_keys('HD_THUEBAO')
            }
            object.HDTB_ID = this.hdtb_id
            object.HDKH_ID = this.hdkh_id
            if (this.thuebao_id > 0){
                object.THUEBAO_ID = this.thuebao_id
            }

            object.TEN_TB=this.$refs.thongTinKhachHang.input_ten_tb.trim()
            //Hiếu sửa địa chỉ tb thành dc LD ngày 06/09/2010
            object.DIACHI_LD=this.$refs.thongTinKhachHang.input_diachi_ld
            object.DIACHI_TB=this.$refs.thongTinKhachHang.input_diachi_tb

            object.KIEULD_ID=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected!=null?this.$refs.thongTinPhieuYeuCau.kieu_yc_selected:0
            //object.LOAITB_ID=Number(this.loaihinhtb_id)
            object.LOAITB_ID=loaihinhtbmoi_id

            object.DOITUONG_ID=this.doituong_id
            object.TTHD_ID=TrangThaiHD.MOI
            object.NGAY_HT=moment(this.$refs.thongTinPhieuYeuCau.ngay_yc, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
            object.DONVI_ID=this.donviql_id

            object.DICHVUVT_ID=Number(await this.fn_map_loaihinh_tb(loaihinhtbmoi_id, 2, '-1'))
            object.NGUOI_CN=this.$root.token.getUserName()
            object.MAY_CN='localhost'
            object.NGAY_CN=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            object.IP_CN='127.0.0.1'
            object.QUYTRINH_ID=this.quytrinh_id
            object.GHICHU=this.$refs.thongTinPhieuYeuCau.input_ghichu.trim()

            if(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected!=null){
                object.MUCUOCTB_ID=this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected
            }else{
                object.MUCUOCTB_ID=0
            }
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH&&(loaihinhtbmoi_id==LoaiHinhTB.ISDN2B_CD||loaihinhtbmoi_id==LoaiHinhTB.ISDN2B_CQ||
                loaihinhtbmoi_id==LoaiHinhTB.ISDN30B_CD||loaihinhtbmoi_id==LoaiHinhTB.ISDN30B_CQ||loaihinhtbmoi_id==LoaiHinhTB.TRUNGKE_2M||
                loaihinhtbmoi_id==LoaiHinhTB.TRUNGKE_THUONG||loaihinhtbmoi_id==LoaiHinhTB.TRUNGKE_TUONGTU)){
                    object.MUCUOC_TB=this.$refs.coDinh.input_cuoctb_moi.trim()!=''?Number(this.$refs.coDinh.input_cuoctb_moi.trim()):0
            }else{
                object.MUCUOC_TB=Number(await this.fn_map_muccuoc_tb(object.MUCUOCTB_ID, 1, '0'))
            }
            var status=''
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                status = "0"
            }
            let ktStatus=await this.fn_kt_thaotac(this.luong_id, 'STATUS_TONGDAI_CD')
            if(ktStatus==1){
                status = "0"
            }
            //Hiếu bổ sung thêm trường status: null: không pải đồng bộ, 0: chưa pải đồng bộ
            object.STATUS = status
            object.HDTB_CHA_ID=0
            object.THUEBAO_CHA_ID=0
            if(this.dichvu_vt_selected!=DichVuVienThong.MEGAWAN&&this.dichvu_vt_selected!=DichVuVienThong.METRONET&&this.dichvu_vt_selected!=26){
                if(this.$refs.thongTinPhieuYeuCau.disableMucCuocTB){
                    if(this.loaisochinh==1){
                        if(this.thuebao_id_cha>0){
                            object.THUEBAO_CHA_ID=this.thuebao_id_cha
                        }
                    }else{
                        if(this.hdtb_id_cha>0){
                            object.HDTB_CHA_ID=this.hdtb_id_cha
                        }
                    }
                }
            }else{
                if(this.thuebao_id>0){
                    let tb_cha_id=await this.fn_map_db_thuebao(this.thuebao_id, 5,'-1')
                    if(tb_cha_id!='-1'){
                        object.THUEBAO_CHA_ID=Number(tb_cha_id)
                    }
                }
            }

            if(this.$refs.thongTinPhieuYeuCau.goikt_selected!=null){
                object.GOIKT_ID=Number(this.$refs.thongTinPhieuYeuCau.goikt_selected)
            }else{
                object.GOIKT_ID=-1
            }

            object.SOTHANG_CK=this.$refs.thongTinKhachHang.input_thang_ck.trim()

            this.dsHDTB.push(object)

            console.log('dsHDTB', this.dsHDTB)

        },
        async TaoDuLieu_DiaChi(themmoi){
            //Gồm 2 item địa chỉ LD, TB
            this.dsDiaChi = []
            let objectDicChiLD={}
            let objectDicChiTB={}

            this.dsDiaChiHDTB = []
            let objectDiaChiHDTB={}

            if(themmoi){
                this.dataDiaChiLD.diachi_id=await this.get_keys('DIACHI')
                this.dataDiaChiTB.diachi_id=await this.get_keys('DIACHI')
            }

            //Địa chỉ ld
            objectDicChiLD.DIACHI_ID=this.dataDiaChiLD.diachi_id
            objectDicChiLD.TINH_ID=this.dataDiaChiLD.tinh_id
            objectDicChiLD.QUAN_ID=this.dataDiaChiLD.quan_id
            objectDicChiLD.PHUONG_ID=this.dataDiaChiLD.phuong_id
            objectDicChiLD.PHO_ID=this.dataDiaChiLD.pho_id
            objectDicChiLD.AP_ID=this.dataDiaChiLD.ap_id
            objectDicChiLD.KHU_ID=this.dataDiaChiLD.khu_id
            objectDicChiLD.DACDIEM_ID=this.dataDiaChiLD.dacdiem_id

            objectDicChiLD.SONHA=this.dataDiaChiLD.sonha
            objectDicChiLD.DIACHI=this.dataDiaChiLD.diachi
            objectDicChiLD.BLOCK=this.dataDiaChiLD.block
            objectDicChiLD.TANG=this.dataDiaChiLD.tang
            objectDicChiLD.PHONG=this.dataDiaChiLD.sophong
            objectDicChiLD.MOTA=this.dataDiaChiLD.motathem

            this.dsDiaChi.push(objectDicChiLD)

            //Địa chỉ tb
            objectDicChiTB.DIACHI_ID=this.dataDiaChiTB.diachi_id
            objectDicChiTB.TINH_ID=this.dataDiaChiTB.tinh_id
            objectDicChiTB.QUAN_ID=this.dataDiaChiTB.quan_id
            objectDicChiTB.PHUONG_ID=this.dataDiaChiTB.phuong_id
            objectDicChiTB.PHO_ID=this.dataDiaChiTB.pho_id
            objectDicChiTB.AP_ID=this.dataDiaChiTB.ap_id
            objectDicChiTB.KHU_ID=this.dataDiaChiTB.khu_id
            objectDicChiTB.DACDIEM_ID=this.dataDiaChiTB.dacdiem_id

            objectDicChiTB.SONHA=this.dataDiaChiTB.sonha
            objectDicChiTB.DIACHI=this.dataDiaChiTB.diachi
            objectDicChiTB.BLOCK=this.dataDiaChiTB.block
            objectDicChiTB.TANG=this.dataDiaChiTB.tang
            objectDicChiTB.PHONG=this.dataDiaChiTB.sophong
            objectDicChiTB.MOTA=this.dataDiaChiTB.motathem

            this.dsDiaChi.push(objectDicChiTB)

            console.log('TaoDuLieu_DiaChi dsDiaChi', this.dsDiaChi)

            objectDiaChiHDTB.DIACHI_ID=this.dataDiaChiTB.diachi_id
            objectDiaChiHDTB.HDTB_ID=this.hdtb_id
            objectDiaChiHDTB.DIACHILD_ID=this.dataDiaChiLD.diachi_id
            objectDiaChiHDTB.KINHDO=this.dataDiaChiTB.kinhdo
            objectDiaChiHDTB.VIDO=this.dataDiaChiTB.vido
            objectDiaChiHDTB.KINHDO_LD=this.dataDiaChiLD.kinhdo
            objectDiaChiHDTB.VIDO_LD=this.dataDiaChiLD.vido

            this.dsDiaChiHDTB.push(objectDiaChiHDTB)
            console.log('TaoDuLieu_DiaChi dsDiaChiHDTB', this.dsDiaChiHDTB)
        },
        async TaoDuLieu_THONGTIN_TT(themmoi){
            //không có code
        },
        async TaoDuLieu_CT_TIENHD(themmoi){
            this.dsCTTHD=[]
            for(let i=0; i<this.ds_tien_khoanmuc.length;i++){
                if(this.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_MUATHIETBI.toString()){
                    for(let j=0;j<this.dsCTM_TBI.length;j++){
                        let object={}
                        object.HDTB_ID=this.hdtb_id
                        if(themmoi){
                            object.CT_TIENHD_ID = await this.get_keys('CT_TIENHD')
                        }
                        object.KHOANMUCTT_ID=KHOANMUC_TT.KMTT_MUATHIETBI
                        object.TIEN=this.dsCTM_TBI[j].TIEN!=null?Number(this.dsCTM_TBI[j].TIEN):0
                        object.VAT=this.dsCTM_TBI[j].VAT!=null?Number(this.dsCTM_TBI[j].VAT):0
                        object.ID=this.dsCTM_TBI[j].LOAITBI_ID!=null?Number(this.dsCTM_TBI[j].LOAITBI_ID):0
                        object.LOAI_ID=3
                        this.dsCTTHD.push(object)
                    }
                }else if(this.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_KM_THIETBI.toString()){
                    for(let j=0;j<this.dsCTM_TBI.length;j++){
                        let object={}
                        object.HDTB_ID=this.hdtb_id
                        if(themmoi){
                            object.CT_TIENHD_ID=await this.get_keys('CT_TIENHD')
                        }
                        object.KHOANMUCTT_ID=KHOANMUC_TT.KMTT_KM_THIETBI
                    
                        var soluong = this.dsCTM_TBI[j].SOLUONG!=null?Number(this.dsCTM_TBI[j].SOLUONG):1
                        let tien=this.dsCTM_TBI[j].TIEN_KM!=null?Number(this.dsCTM_TBI[j].TIEN_KM)*soluong:0
                        var vat=this.dsCTM_TBI[j].VAT_KM!=null?Number(this.dsCTM_TBI[j].VAT_KM)*soluong:0
                        object.TIEN=Math.abs(tien)*-1
                        object.VAT=Math.abs(vat)*-1
                        object.ID=this.dsCTM_TBI[j].LOAITBI_ID!=null?Number(this.dsCTM_TBI[j].LOAITBI_ID):0
                        object.LOAI_ID=3
                        this.dsCTTHD.push(object)
                    }
                }else if(this.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_TRATRUOC_THIETBI.toString()){
                    for(let j=0;j<this.dsCTM_TBI.length;j++){
                        let object={}
                        object.HDTB_ID=this.hdtb_id
                        if(themmoi){
                            object.CT_TIENHD_ID=await this.get_keys('CT_TIENHD')
                        }
                        object.KHOANMUCTT_ID=KHOANMUC_TT.KMTT_TRATRUOC_THIETBI
                        var soluong = this.dsCTM_TBI[j].SOLUONG!=null?Number(this.dsCTM_TBI[j].SOLUONG):1
                        let tien=this.dsCTM_TBI[j].TIEN_TRATRUOC!=null?Number(this.dsCTM_TBI[j].TIEN_TRATRUOC)*soluong:0
                        var vat=this.dsCTM_TBI[j].VAT_TRATRUOC!=null?Number(this.dsCTM_TBI[j].VAT_TRATRUOC)*soluong:0
                        object.TIEN=tien
                        object.VAT=vat
                        object.ID=this.dsCTM_TBI[j].LOAITBI_ID!=null?Number(this.dsCTM_TBI[j].LOAITBI_ID):0
                        object.LOAI_ID=3
                        this.dsCTTHD.push(object)
                    }
                }else{
                    let object={}
                    object.HDTB_ID=this.hdtb_id
                    if(themmoi){
                        object.CT_TIENHD_ID=await this.get_keys('CT_TIENHD')
                    }
                    let khoanmuc_id=Number(this.ds_tien_khoanmuc[i].khoanmuctt_id)
                    object.KHOANMUCTT_ID=khoanmuc_id
                    if(khoanmuc_id==KHOANMUC_TT.KMTT_KM_LAPDAT||khoanmuc_id==KHOANMUC_TT.KMTT_KM_THIETBI
                        ||khoanmuc_id==KHOANMUC_TT.KMTT_KM_DATCOC||khoanmuc_id==KHOANMUC_TT.KMTT_TIENTHOAI_DC){
                        if(Number(this.ds_tien_khoanmuc[i].tien)>0){
                            object.TIEN=Number(this.ds_tien_khoanmuc[i].tien)*-1
                            object.VAT=Number(this.ds_tien_khoanmuc[i].vat)*-1
                        }else{
                            object.TIEN=Number(this.ds_tien_khoanmuc[i].tien)
                            object.VAT=Number(this.ds_tien_khoanmuc[i].vat)
                        }
                    }else{
                        object.TIEN=Number(this.ds_tien_khoanmuc[i].tien)
                        object.VAT=Number(this.ds_tien_khoanmuc[i].vat)
                    }
                    object.ID=khoanmuc_id
                    object.LOAI_ID=1
                    this.dsCTTHD.push(object)
                }
            }
            console.log('TaoDuLieu_CT_TIENHD', this.dsCTTHD)
        },
        async CapNhat_CTKM(hdtb_id){
            //dsKM_HDTB
            for(let i=0;i<this.dsKM_HDTB.length;i++){
                this.dsKM_HDTB[i].HDTB_ID=hdtb_id
            }
        },
        async TaoDuLieu_HDTBDV(){
            //dtControl sp_ht_danhmuc_dv_tdlhtb
            this.dsHDTBDV=[]
            let temp=await this.sp_ht_danhmuc_dv_tdlhtb(JSON.stringify({
                DICHVUVT_ID:this.dichvu_vt_selected,
                LOAIHD_ID:LoaiHopDong.CHUYENDOI_LH,
                KIEULD_ID:this.kieuld_id,
                LOAIKENH_ID:this.loaikenh_selected,
                LOAITB_ID_MOI:this.loaihinhtb_id!=null?Number(this.loaihinhtb_id):0
            }))
            // LOAIDV_DICH_ID: 4
            // TEN_LOAIDV: "Giao dịch khách hàng"
            // THUTU_ID: 1
            let dtControl=(temp!=null&&temp.ERROR_CODE==1)?temp.RESULT.DS_TT_LOAIDV:[]
            console.log('TaoDuLieu_HDTBDV dtControl', dtControl)
            let dtTemp=await this.ur2_1_035_038_temp(this.thuebao_id)
            console.log('TaoDuLieu_HDTBDV dtTemp', dtTemp)
            if(this.dichvu_vt_selected!=DichVuVienThong.TSL&&this.dichvu_vt_selected!=DichVuVienThong.NOIBO_2DIEM){
                //sp_lay_donvi_theo_loaidv_diachi_ld_v1
                //lay_ds_danhba_tsl
                //cbo[i].Tag = dtControl.Rows[i]["loaidv_dich_id"]
                for(let i=0;i<dtControl.length;i++){
                    var object={}
                    object.HDTB_ID = this.hdtb_id
                    let dr=dtTemp.filter(x=>x.loaidv_id==dtControl[i].LOAIDV_DICH_ID.toString())
                    let _donviID=await this.lay_dv_theo_loai_dv_diachi_ld(dtControl[i].LOAIDV_DICH_ID, this.dataDiaChiLD.phuong_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.khu_id)
                    object.LOAIDV_ID=dtControl[i].LOAIDV_DICH_ID
                    if(dr.length>0){
                        object.DONVI_ID=Number(dr[0].donvi_id)
                    }else if(dr.length==0&&_donviID!=-1&&_donviID>0){
                        object.DONVI_ID=_donviID
                    }else{
                        object.DONVI_ID=0
                    }
                    object.KIEUDV_ID=2
                    this.dsHDTBDV.push(object)
                }
            }else{
            //     let dt=await this.lay_ds_danhba_tsl(this.thuebao_id)
            //     for(let i=0;i<dt.length;i++){
            //         var object={}
            //         object.HDTB_ID = this.hdtb_id
            //         object.LOAIDV_ID = LOAI_DV.TRAM_VT

            //         if(dt[i].daucuoi_id!=null&&dt[i].daucuoi_id.toString()=='1'){
            //             //điểm đầu
            //             object.KIEUDV_ID = 1
            //             object.DONVI_ID = this.tram_dv_dau
            //         }else{
            //             object.KIEUDV_ID = 2
            //             object.DONVI_ID = this.tram_dv_cuoi
            //         }
            //         this.dsHDTBDV.push(object)
            //     }
            //     //Tạo cho bảng hdtb_dv
            //     //Với Truyền số liệu thì tự thêm các dòng # trạm viễn thông
            //     //DS_TT_LOAIDV:LOAIDV_DICH_ID(4,5,23)
            //     //DS_LOAIDV:(LOAIDV_4,LOAIDV_5,LOAIDV_23)
            //     //Trừ loại đơn vị trạm trạm viễn thông
            //     let dsLoaiDV=(temp!=null&&temp.ERROR_CODE==1)?temp.RESULT.DS_LOAIDV:null
            //     if(dsLoaiDV&&dsLoaiDV.LOAIDV_4){
            //         for(let i=0;i<dsLoaiDV.LOAIDV_4.length;i++){
            //             var object={}
            //             object.HDTB_ID = this.hdtb_id
            //             object.LOAIDV_ID=4
            //             object.DONVI_ID=dsLoaiDV.LOAIDV_4[i].DONVI_ID
            //             object.KIEUDV_ID = 2
            //             this.dsHDTBDV.push(object)
            //         }
            //     }
            //     if(dsLoaiDV&&dsLoaiDV.LOAIDV_23){
            //         for(let i=0;i<dsLoaiDV.LOAIDV_23.length;i++){
            //             var object={}
            //             object.HDTB_ID = this.hdtb_id
            //             object.LOAIDV_ID=23
            //             object.DONVI_ID=dsLoaiDV.LOAIDV_23[i].DONVI_ID
            //             object.KIEUDV_ID = 2
            //             this.dsHDTBDV.push(object)
            //         }
            //     } 
                let dtTemp1 = await this.lay_ds_donvi(this.thuebao_id)
                for(const item of dtTemp1){
                    let obj = {}
                    obj['KIEUDV_ID'] = item['kieudv_id']
                    obj['DONVI_ID'] = item['donvi_id']
                    obj['LOAIDV_ID'] = item['loaidv_id']
                    obj['HDTB_ID'] = this.hdtb_id
                    this.dsHDTBDV.push(obj)
                }
            }
            
            console.log('TaoDuLieu_HDTBDV', this.dsHDTBDV)

        },
        async LoadDL_KV(khuvuc_id){
            this.dsHDTB_KV=[]
            let object={}
            object.HDTB_ID=this.hdtb_id
            object.KHUVUC_ID=khuvuc_id
            object.LOAIKV_ID=LOAI_KV.KHUVUC_DIABAN
            object.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            object.NGUOI_CN = this.$root.token.getUserName()
            object.MAY_CN='localhost'
            object.IP_CN='127.0.0.1'
            object.KIEUKV_ID=2
            this.dsHDTB_KV.push(object)

            console.log('LoadDL_KV', this.dsHDTB_KV)
        },
        async TaoDuLieu_HDTB_CD(themmoi){
            this.dsHDTB_CD=[]
            
            //api lấy tạo object
            let result = await this.lay_ds_db_cd_theo_tbid_pro(this.thuebao_id)
            //upper case key data source
            for(var i = 0; i < result.length; i++){ 
                for (var key in result[i]) {
                    if(key.toUpperCase() !== key){
                        result[i][key.toUpperCase()] = result[i][key];
                        delete result[i][key];
                    }
                }
            }
            var object=createObjectHDTB_CD(result[0])

            //Object.assign(object, result[0])
            //
            if(object.PHANVUNG_ID==null){
                object.PHANVUNG_ID=this.$root.token.getPhanVungID()
            }
            object.HDTB_ID=this.hdtb_id
            //3 khách hàng tự trang bị
            object.TRANGBI_ID=this.$refs.coDinh.trangbi_moi_selected!=null?Number(this.$refs.coDinh.trangbi_moi_selected):3
            if(themmoi){
                object.MADOICAP = await this.get_keys('MADOICAP')
            }else{
                object.MADOICAP = this.madoicap_cd
            }
            object.KETCUOI_ID=this.KetCuoi_ID!=null?this.KetCuoi_ID:0

            if(this.loaihinhtb_id==LoaiHinhTB.PABX&&this.$refs.coDinh.loai_so_selected!=null
                &&this.$refs.coDinh.loai_so_selected==LOAISO_ISDN.SOPHU){
                object.SO_TAT=this.$refs.coDinh.input_sotat.trim()
            }else{
                object.SO_TAT=null
            }
            this.dsHDTB_CD.push(object)
            console.log('TaoDuLieu_HDTB_CD', this.dsHDTB_CD)

        },
        async TaoDuLieu_HDTB_IMS(themmoi){
            this.dsHDTB_IMS=[]
            let result = await this.lay_ds_db_ims_theo_tbid_pro(this.thuebao_id)
            //upper case key data source
            for(var i = 0; i < result.length; i++){ 
                for (var key in result[i]) {
                    if(key.toUpperCase() !== key){
                        result[i][key.toUpperCase()] = result[i][key];
                        delete result[i][key];
                    }
                }
            }
            var object=createObjectHDTB_IMS(result[0])
            if(object.PHANVUNG_ID==null){
                object.PHANVUNG_ID=this.$root.token.getPhanVungID()
            }
            object.HDTB_ID=this.hdtb_id
            object.THIETBI_ID=this.$refs.ims.thietbi_ims_moi_selected!=null?this.$refs.ims.thietbi_ims_moi_selected:0
            
            var kieu=''
            let kieuld_id=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
            let kieuld=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x=>x.kieuld_id==kieuld_id)
            if(kieuld!=undefined){
                kieu=kieuld.kieu.toString()
            }
            if(kieu=='1'||kieu=='2'){
                //Chuyển check hàm kiểm tra
                // if (txtMaDoiCap_ADSL.Text.Trim().Length == 0)
                // {
                //     Message_Box.ShowWarning("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập");
                //     return;
                // }
                object.MADOICAP=this.$refs.ims.input_madoicap
            }else{
                //TH còn lại sinh mã đôi cáp
                if(themmoi){
                    object.MADOICAP=await this.get_keys('MADOICAP')
                    this.$refs.ims.input_madoicap=object.MADOICAP.toString()
                }else{
                    object.MADOICAP=this.$refs.ims.input_madoicap
                }

            }
            object.MATB_TN = this.$refs.ims.input_matn!=null?this.$refs.ims.input_matn:''
            object.TRANGBI_ID = this.$refs.ims.trangbi_moi_selected!=null?this.$refs.ims.trangbi_moi_selected:3
            //sp_lay_dauso_ims_theo_tinh
            let tentat=this.$root.token.getMaTinh()
            let dt=await this.sp_lay_dauso_ims_theo_tinh(tentat)
            if(dt.length>0){
                if(dt[0].dauso_ims){
                    object.USERNAME='84'+dt[0].dauso_ims.toString()+this.$refs.ims.input_matb.trim()
                    object.PASSWORD='84'+dt[0].dauso_ims.toString()+this.$refs.ims.input_matb.trim()
                }else{
                    object.USERNAME='84'+this.$refs.ims.input_matb.trim()
                    object.PASSWORD='84'+this.$refs.ims.input_matb.trim()
                }
                
            }
            if(this.$refs.ims.loaihinh_tb_moi_selected==LoaiHinhTB.IMS_SIPTRUNKING){
                object.SL_CUOCGOI=(this.$refs.ims.input_sl_cuocgoi!=null&&this.$refs.ims.input_sl_cuocgoi!='')?Number(this.$refs.ims.input_sl_cuocgoi):null
                object.CTDV_ID = this.$refs.ims.chitiet_dv_selected
                if(this.$refs.ims.chitiet_dv_selected==ChiTietDV_IMS.internet){
                    object.IP=this.$refs.ims.input_ip
                }
            }else{
                object.SL_CUOCGOI=null
                object.CTDV_ID=null
                object.IP=null
            }

            this.dsHDTB_IMS.push(object)
        },
        async TaoDuLieu_HDTB_ADSL(themmoi){
            this.dsHDTB_ADSL=[]
            var object=createObjectHDTB_ADSL()
            if(object.PHANVUNG_ID==null){
                object.PHANVUNG_ID=this.$root.token.getPhanVungID()
            }
            object.HDTB_ID=this.hdtb_id
            object.PASSWORD=this.$refs.bangRongCoDinh.input_matkhau_adsl
            object.EMAIL=this.$refs.bangRongCoDinh.input_email_adsl
            object.TOCDO_ID=this.$refs.bangRongCoDinh.tocdo_moi_selected!=null?this.$refs.bangRongCoDinh.tocdo_moi_selected:0
            object.CHUQUAN_ID = this.thongso_ban.chuquan_id
            object.CHUQUANLD_ID = this.thongso_ban.chuquanld_id

            let profiledsl_id=await this.fn_map_tocdo_adsl(object.TOCDO_ID,3,'-1')

            if(profiledsl_id!='-1'){
                object.PROFILEDSL_ID=profiledsl_id
            }else{
                object.PROFILEDSL_ID=null
            }
            if(this.$refs.bangRongCoDinh.checkTocDoThuc){
                object.TOCDOTHUC_ID=this.$refs.bangRongCoDinh.tocdo_thuc_selected!=null?this.$refs.bangRongCoDinh.tocdo_thuc_selected:null
            }else{
                object.TOCDOTHUC_ID=object.TOCDO_ID
            }
            if(this.KetCuoi_ID>0){
                object.KETCUOI_ID=this.KetCuoi_ID
            }
            object.MUCCUOC_ID=this.$refs.bangRongCoDinh.muccuoc_moi_selected!=null?this.$refs.bangRongCoDinh.muccuoc_moi_selected:0
            object.THIETBIDC_ID=this.$refs.bangRongCoDinh.loaithietbi_moi_selected!=null?this.$refs.bangRongCoDinh.loaithietbi_moi_selected:0

            //fn_tt_taodulieu_hdtb_adsl_cdlhtb
            let result = await this.fn_tt_taodulieu_hdtb_adsl_cdlhtb(JSON.stringify({
                THUEBAO_ID:this.thuebao_id,
                TOCDO_ID:object.TOCDO_ID,
                NHAPMOI:themmoi?1:2,
                QUAN_ID:this.dataDiaChiLD.quan_id,
                MADOICAP:"",
                DICHVUVT_ID:this.dichvu_vt_selected,
                LOAITB_MOI_ID:this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected!=null?this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected:0,
                LOAITB_CU_ID:this.$refs.thongTinPhieuYeuCau.loaihinh_selected!=null?this.$refs.thongTinPhieuYeuCau.loaihinh_selected:0
            }))

            if(this.thamso.TUDONG_SINH_SOAO){
                if(this.$refs.thongTinPhieuYeuCau.loaihinh_selected!=LoaiHinhTB.INTERNET_MYTV){
                    if(this.$root.token.getMaTinh()=='HPG'){
                        //Code riêng HPG, nếu mã lt ko đúng chuẩn thì sinh mã mới
                        var isOld=false
                        var malt=''
                        malt=await this.fn_map_db_adsl(this.thuebao_id, 2,'-1')

                        if(malt!='-1'&&malt.length>3&&malt.substring(0, 3)!='131'){
                            isOld=true
                        }
                        if(isOld){
                            object.MA_LT=result.MA_LT
                        }else{
                            object.MA_LT=malt
                        }
                    }else{
                        if(result!=null){
                            object.MA_LT=result.MA_LT
                        }else{
                            object.MA_LT=null
                        }
                    }
                }else{
                    //Nếu là Mytv thì giữ nguyên số ảo, ko cần sinh lại nữa
                    let  malt=await this.fn_map_db_adsl(this.thuebao_id, 2,'-1')

                    if(malt!='-1'){
                        object.MA_LT=malt
                    }
                }
            }else{
                let malt=await this.fn_map_db_adsl(this.thuebao_id, 2,'-1')
                if(malt!='-1'){
                    object.MA_LT=malt
                }
            }
            //
            object.MATB_TN=this.$refs.bangRongCoDinh.input_matn.trim()
            //Gán giá trị cho column tức thì theo mức cước
            if(this.$refs.bangRongCoDinh.checkTucThi){
                if(this.$refs.bangRongCoDinh.muccuoc_selected!=this.$refs.bangRongCoDinh.muccuoc_moi_selected){
                    object.TUCTHI=3
                }else{
                    object.TUCTHI=2
                }
            }else{
                //Đối với chuyển đổi mega-FTTH chỉ có tháng 6 mới chuyển đổi (tucthi=0)
                object.TUCTHI=0
            }
            object.TRANGBI_ID=this.$refs.bangRongCoDinh.trangbi_moi_selected
            object.THONGTIN_TC=this.$refs.bangRongCoDinh.input_tttc!=null?this.$refs.bangRongCoDinh.input_tttc.trim():''


            let kieuld_id=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
            let kieu_ld=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x=>x.kieuld_id==kieuld_id)
            if(!kieu_ld){
                return
            }
            //Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
            if(kieu_ld.kieu=='1'||kieu_ld.kieu=='2'){
                if(this.$refs.bangRongCoDinh.input_madoicap_adsl.trim()==''){
                    this.kt_adsl=false
                    this.$toast.error('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập')
                    return
                }
                object.MADOICAP=this.$refs.bangRongCoDinh.input_madoicap_adsl.trim()
            }else{
                //TH còn lại sinh mã đôi cáp
                if(themmoi){
                    object.MADOICAP=result.MADOICAP
                }else{
                    object.MADOICAP=this.$refs.bangRongCoDinh.input_madoicap_adsl.trim()
                }
            }

            this.dsHDTB_ADSL.push(object)
        },
        async TaoDuLieu_HDTB_MGWAN(themmoi){
            this.dsHDTB_MEWAN=[]
            var object=createObjectHDTB_MGWAN()
            if(object.PHANVUNG_ID==null){
                object.PHANVUNG_ID=this.$root.token.getPhanVungID()
            }
            //
            let result = await this.fn_tt_taodulieu_hdtb_mgwan_cdlhtb(JSON.stringify({
                THUEBAO_ID:this.thuebao_id,
                TOCDO_ID:this.$refs.megawan.tocdo_cir_moi_selected!=null?this.$refs.megawan.tocdo_cir_moi_selected:0,
                NHAPMOI:themmoi?1:2,
                QUAN_ID:this.dataDiaChiLD.quan_id,
                MADOICAP:"",
                DICHVUVT_ID: this.dichvu_vt_selected,
                LOAITB_MOI_ID:this.$refs.megawan.loaihinh_tb_moi_selected!=null?this.$refs.megawan.loaihinh_tb_moi_selected:0,
                LOAITB_CU_ID:this.$refs.thongTinPhieuYeuCau.loaihinh_selected!=null?this.$refs.thongTinPhieuYeuCau.loaihinh_selected:0
            }))
            if(result!=null&&result.ERROR_CODE==1){
                let dbMegaWan=result.RESULT.LAY_DS_DB_MEGAWAN_THEO_TBID[0]
                console.log('dbMegaWan', dbMegaWan)
                object.HDTB_ID = this.hdtb_id
                object.TOCDO_ID = this.$refs.megawan.tocdo_cir_moi_selected!=null?this.$refs.megawan.tocdo_cir_moi_selected:0
                if(this.$refs.megawan.checkTocDoPIRMoi){
                    object.TOCDO_PIR_ID=this.$refs.megawan.tocdo_pir_moi_selected!=null?this.$refs.megawan.tocdo_pir_moi_selected:0
                }
                object.LOAIMD_ID = this.$refs.megawan.modem_moi_selected!=null?this.$refs.megawan.modem_moi_selected:0
                object.LOAIKENH_ID = this.$refs.megawan.loaikenh_moi_selected!=null?this.$refs.megawan.loaikenh_moi_selected:0
                object.THIETBIDC_ID = this.$refs.megawan.loaithietbi_moi_selected!=null?this.$refs.megawan.loaithietbi_moi_selected:0
                
                object.HUONG_KN = this.vhuong_kn
                object.TINH_KN_ID = dbMegaWan.TINH_KN_ID
                object.THOIHAN_ID = this.$refs.megawan.thoihan_moi_selected!=null?this.$refs.megawan.thoihan_moi_selected:0
                object.TRANGBI_ID = this.$refs.megawan.trangbi_moi_selected!=null?this.$refs.megawan.trangbi_moi_selected:0
                object.CHUQUAN_ID = this.$refs.megawan.chuquan_moi_selected!=null?this.$refs.megawan.chuquan_moi_selected:0
                object.LOAICAP_ID = this.$refs.megawan.loaicap_moi_selected!=null?this.$refs.megawan.loaicap_moi_selected:0

                if(this.KetCuoi_ID>0){
                    object.KETCUOI_ID=this.KetCuoi_ID
                }

                if(this.thamso.TUDONG_SINH_SOAO){
                    if(this.$root.token.getMaTinh()=='HPG'){
                        //Code riêng HPG, nếu mã lt ko đúng chuẩn thì sinh mã mới
                        var isOld = false
                        if(dbMegaWan.MA_LT!=null&&dbMegaWan.MA_LT.toString().length>3){
                            if(dbMegaWan.MA_LT.toString().substring(0, 3)!='131'){
                                isOld = true
                            }
                        }
                        if(isOld){
                            object.MA_LT=result.RESULT.MA_LT
                        }else{
                            object.MA_LT=dbMegaWan.MA_LT
                        }
                    }
                }else{
                    object.MA_LT=dbMegaWan.MA_LT
                }

                object.MA_PL = this.$refs.thongTinPhieuYeuCau.input_ma_cng.trim()
                object.SITE_ID = this.$refs.megawan.input_site_id_moi.trim()
                if(this.$refs.thongTinKhachHang.checkSoCV){
                    object.MA_CV = this.$refs.thongTinKhachHang.input_socv
                    object.NGAY_CV = this.$refs.thongTinKhachHang.ngay_cv
                }
                if(dbMegaWan.LOAINODE_ID!=null){
                    object.LOAINODE_ID=dbMegaWan.LOAINODE_ID
                }
                if(themmoi){
                    object.MADOICAP = result.RESULT.MADOICAP
                }else{
                    object.MADOICAP = this.madoicap_mgw
                }

                if(this.$refs.megawan.checkDoiTacMoi){
                    object.DOITAC_ID = this.$refs.megawan.doitac_moi_selcted!=null?this.$refs.megawan.doitac_moi_selcted:0
                    if(this.$refs.megawan.input_cuoc_tkdt_moi){
                        object.CUOC_TKDT=Number(this.$refs.megawan.input_cuoc_tkdt_moi.trim())
                    }else{
                        object.CUOC_TKDT=0
                    }
                    if(this.$refs.megawan.input_cuoc_tcdt_moi){
                        object.CUOC_TCDT=Number(this.$refs.megawan.input_cuoc_tcdt_moi.trim())
                    }else{
                        object.CUOC_TCDT=0
                    }
                    object.MAHD_DT=this.$refs.megawan.input_ma_hddt_moi!=null?this.$refs.megawan.input_ma_hddt_moi.trim():''
                }

                if(this.$refs.megawan.input_cuoc_ht_moi){
                    object.CUOC_HT = Number(this.$refs.megawan.input_cuoc_ht_moi.trim())
                }else{
                    object.CUOC_HT = 0
                }
                if(this.$refs.megawan.input_cuoc_tb_moi){
                    object.CUOC_TBI = Number(this.$refs.megawan.input_cuoc_tb_moi.trim())
                }else{
                    object.CUOC_TBI = 0
                }
                if(this.$refs.megawan.input_cuoc_tc_moi){
                    object.CUOC_TC = Number(this.$refs.megawan.input_cuoc_tc_moi.trim())
                }else{
                    object.CUOC_TC = 0
                }
                if(this.$refs.megawan.input_cuoc_tk_moi){
                    object.CUOC_TK = Number(this.$refs.megawan.input_cuoc_tk_moi.trim())
                }else{
                    object.CUOC_TK = 0
                }
                
                object.MA_BSS=this.$refs.thongTinKhachHang.input_ma_bss.trim()
                object.ID_ERP=this.$refs.thongTinKhachHang.input_ma_erp.trim()

                this.dsHDTB_MEWAN.push(object)

            }else if(result!=null&&result.ERROR_CODE==0){
                this.$toast.error(result.MESSAGE)
            }

        },
        async TaoDuLieu_HDTB_TSL(themmoi){
          //
            this.dsHDTB_TSL=[]
            let result=await this.lay_ds_danhba_tsl(this.thuebao_id)
            //upper case key data source
            for(var i = 0; i < result.length; i++){ 
                for (var key in result[i]) {
                    if(key.toUpperCase() !== key){
                        result[i][key.toUpperCase()] = result[i][key];
                        delete result[i][key];
                    }
                }
            }

            for (let i = 0; i < result.length; i++)
            {
                var object=createObjectHDTB_TSL(result[i])
                object.HDTB_ID = this.hdtb_id
                object.MA_PL = this.$refs.thongTinPhieuYeuCau.input_ma_cng.trim()
                object.MA_BSS=this.$refs.thongTinKhachHang.input_ma_bss.trim()
                object.ID_ERP=this.$refs.thongTinKhachHang.input_ma_erp.trim()
                this.dsHDTB_TSL.push(object)
            }
            console.log('TaoDuLieu_HDTB_TSL', this.dsHDTB_TSL)
        },
        async TaoDuLieuBC(themmoi){
            if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                this.dsHDBC=[]
                this.dsHDTBBC=[]
                await this.TaoDuLieu_HDBANCHEO_TSL(themmoi, 1)
                await this.TaoDuLieu_HDBANCHEO_TSL(themmoi, 2)
            }else{
                await this.TaoDuLieu_HDTBBC(themmoi)
            }

            this.dsHDBCADSL=[]
            this.dsHDBCCD=[]
            this.dsHDBCMGWAN=[]

            this.dsHDBCTSL = []
            this.dsHDBCIMS = []
            this.dsDKDVGTBC = []

            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.TaoDuLieu_HDTBBC_CD(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                await this.TaoDuLieu_HDTBBC_ADSL(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.TaoDuLieu_HDTBBC_MGWAN(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.TaoDuLieu_HDTBBC_TSL(themmoi)
                await this.TaoDuLieu_HDTBBC_TSL(themmoi)
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.TaoDuLieu_HDTBBC_IMS(themmoi)
            }   

        },
        async TaoDuLieu_HDTBBC(themmoi){
            this.dsHDBC=[]
            var objectHDBC=createObjectHD_BANCHEO()
            objectHDBC.HDTB_BAN = this.hdtb_id
            objectHDBC.TINH_THICONG = this.tinh_thicong
            objectHDBC.TINH_BAN = this.$root.token.getPhanVungID()
            objectHDBC.TRANG_THAI = 0
            objectHDBC.LOAIHD_BANCHEO = 1

            objectHDBC.THUEBAO_ID_BAN = this.thuebao_id
            objectHDBC.THUEBAO_ID_THICONG = this.thuebao_tc_id
            if(!this.kenh_trang&&!this.chi_giao_net){
                objectHDBC.THANHTOAN_ID_BAN = Number(await this.fn_map_db_thuebao(this.thuebao_id, 1, '-1'))
                objectHDBC.KHACHHANG_ID_BAN = this.khachhang_id
            }else{
                objectHDBC.THANHTOAN_ID_TC = 0;
                objectHDBC.KHACHHANG_ID_TC = 0;
            }
            this.dsHDBC.push(objectHDBC)
            console.log('TaoDuLieu_HDTBBC dsHDBC', this.dsHDBC)

            this.dsHDTBBC=[]
            var object={}
            if(!this.kenh_trang){
                let result=await this.lay_danhba_theo_thuebaoid(this.tinh_thicong, this.thuebao_tc_id, this.dichvu_vt_selected, 0, 2)
                //upper case key data source
                for(var i = 0; i < result.length; i++){ 
                    for (var key in result[i]) {
                        if(key.toUpperCase() !== key){
                            result[i][key.toUpperCase()] = result[i][key];
                            delete result[i][key];
                        }
                    }
                }
                if (!result || result.length === 0) {
                    this.$toast.error('không lấy đc dữ liệu thuê bao ở tỉnh thi công')
                    return;
                }
                console.log('createObjectHDTB_BANCHEO lay_danhba_theo_thuebaoid',result)
                
                object=result.length>0?createObjectHDTB_BANCHEO(result[0]):createObjectHDTB_BANCHEO({})
            }else{
                console.log('createObjectHDTB_BANCHEO(this.dsHDBC[0])', this.dsHDTB[0])
                object=createObjectHDTB_BANCHEO(this.dsHDTB[0])
                object.MUCUOCTB_ID = this.thongso_tc.muccuoctb_id
                let muccuoc_tb=await this.fn_map_bancheo_muccuoc_tb(this.thongso_tc.muccuoctb_id, 1, this.tinh_thicong,'0')
                if(muccuoc_tb!='-1'){
                    object.MUCUOC_TB=Number(muccuoc_tb)
                }
                object.DOITUONG_ID = this.thongso_tc.doituong_id
            }
            object.HDTB_ID = this.hdtb_id
            object.HDKH_ID = this.hdkh_id
            object.TTHD_ID = 1
            object.QUYTRINH_ID = this.quytrinh_tc_tn
            object.HUONGGIAO_TN_ID = this.huonggiao_tc_tn

            object.KIEULD_ID = this.thongso_tc.kieuld_id

            var loaihinhtbmoi_id=0
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                loaihinhtbmoi_id=this.$refs.coDinh.loaihinh_tb_moi_selected!=null?this.$refs.coDinh.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                loaihinhtbmoi_id=this.$refs.diDong.loaihinh_tb_moi_selected!=null?this.$refs.diDong.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                loaihinhtbmoi_id=this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected!=null?this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                loaihinhtbmoi_id=this.$refs.megawan.loaihinh_tb_moi_selected!=null?this.$refs.megawan.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                loaihinhtbmoi_id=this.$refs.kenhThuRieng.loaihinh_tb_moi_selected!=null?this.$refs.kenhThuRieng.loaihinh_tb_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                loaihinhtbmoi_id=this.$refs.ims.loaihinh_tb_moi_selected!=null?this.$refs.ims.loaihinh_tb_moi_selected:0
            }
            object.LOAITB_ID=loaihinhtbmoi_id

            let tinhtc=await this.sp_lay_tinh_tc_theo_tinh_id(this.tinh_thicong)
            object.DONVI_ID = tinhtc.length>0?tinhtc[0].donvi_id:0
            
            object.DICHVUVT_ID=Number(await this.fn_map_loaihinh_tb(loaihinhtbmoi_id, 2, '-1'))
            object.NGUOI_CN=this.$root.token.getUserName()
            object.MAY_CN='localhost'
            object.NGAY_CN=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            object.IP_CN='127.0.0.1'
            //Thiện thêm
            // console.log('Tên thuê bao', this.$refs.thongTinKhachHang.input_ten_tb.trim())
            // object.TEN_TB=this.$refs.thongTinKhachHang.input_ten_tb.trim()
            // object.DOITUONG_ID=this.doituong_id
            // console.log('DOITUONG_ID', this.doituong_id)
            // object.MA_TB=this.getMaTB()
            //

            object.GHICHU=this.$refs.thongTinPhieuYeuCau.input_ghichu.trim()
            
            object.MUCUOCTB_ID = this.thongso_tc.muccuoctb_id

            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH&&(loaihinhtbmoi_id==LoaiHinhTB.ISDN2B_CD||loaihinhtbmoi_id==LoaiHinhTB.ISDN2B_CQ||
                loaihinhtbmoi_id==LoaiHinhTB.ISDN30B_CD||loaihinhtbmoi_id==LoaiHinhTB.ISDN30B_CQ||loaihinhtbmoi_id==LoaiHinhTB.TRUNGKE_2M||
                loaihinhtbmoi_id==LoaiHinhTB.TRUNGKE_THUONG||loaihinhtbmoi_id==LoaiHinhTB.TRUNGKE_TUONGTU)){
                    object.MUCUOC_TB=this.$refs.coDinh.input_cuoctb_moi.trim()!=''?Number(this.$refs.coDinh.input_cuoctb_moi.trim()):0
            }else{
                let muccuoc_tb=await this.fn_map_bancheo_muccuoc_tb(this.thongso_tc.muccuoctb_id, 1, this.tinh_thicong, '0')
                if(muccuoc_tb!='-1'){
                    object.MUCUOC_TB=Number(muccuoc_tb)
                }
            }

            var status=''
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                status = "0"
            }
            let ktStatus=await this.fn_kt_thaotac(this.luong_id, 'STATUS_TONGDAI_CD')
            if(ktStatus==1){
                status = "0"
            }

            object.STATUS = status

            object.HDTB_CHA_ID=0
            object.THUEBAO_CHA_ID=0
            if(this.dichvu_vt_selected!=DichVuVienThong.MEGAWAN&&this.dichvu_vt_selected!=DichVuVienThong.METRONET&&this.dichvu_vt_selected!=26){
                if(this.$refs.thongTinPhieuYeuCau.disableMucCuocTB){
                    if(this.loaisochinh==1){
                        if(this.thuebao_id_cha>0){
                            object.THUEBAO_CHA_ID=this.thuebao_id_cha
                        }
                    }else{
                        if(this.hdtb_id_cha>0){
                            object.HDTB_CHA_ID=this.hdtb_id_cha
                        }
                    }
                }
            }else{
                if(this.thuebao_id>0){
                    let tb_cha_id=await this.fn_map_bancheo_db_thuebao(this.thuebao_tc_id, 5,'-1')
                    if(tb_cha_id!='-1'){
                        object.THUEBAO_CHA_ID=Number(tb_cha_id)
                    }
                }
            }

            if(this.$refs.thongTinPhieuYeuCau.goikt_selected!=null){
                object.GOIKT_ID=Number(this.$refs.thongTinPhieuYeuCau.goikt_selected)
            }else{
                object.GOIKT_ID=-1
            }

            object.SOTHANG_CK=this.$refs.thongTinKhachHang.input_thang_ck.trim()

            this.dsHDTBBC.push(object)
            console.log('TaoDuLieu_HDTBBC dsHDTBBC', this.dsHDTBBC)
        },
        async TaoDuLieu_HDBANCHEO_TSL(themmoi, daucuoi_id){
            //1:điểm đầu, 2 điểm cuối
            var rowHDBBC=createObjectHD_BANCHEO()
            rowHDBBC.HDTB_BAN = this.hdtb_id
            if (daucuoi_id == 1)
            {
                rowHDBBC.TINH_THICONG = this.tinh_dau
                rowHDBBC.THUEBAO_ID_THICONG = this.thuebao_tc_id
            }
            else
            {
                rowHDBBC.TINH_THICONG = this.tinh_cuoi
                rowHDBBC.THUEBAO_ID_THICONG = this.thuebao_tc_id_tsl
            }
            rowHDBBC.DAUCUOI_ID = daucuoi_id
            rowHDBBC.TINH_BAN = this.$root.token.getPhanVungID()
            rowHDBBC.THUEBAO_ID_BAN = this.thuebao_id
            rowHDBBC.TRANG_THAI = 0
            rowHDBBC.LOAIHD_BANCHEO = 1
            if(this.kenh_trang){
                if(this.khachhang_id!=0){
                    rowHDBBC.KHACHHANG_ID_BAN = this.khachhang_id
                }
            }else{
                if(this.khachhang_id!=0){
                    rowHDBBC.KHACHHANG_ID_BAN = this.khachhang_id
                    let khachhangtc_id = await this.fn_lay_khachhangid_thicong(this.khachhang_id, this.tinh_dau)
                    if(khachhangtc_id){
                        rowHDBBC.KHACHHANG_ID_TC = khachhangtc_id
                    }
                }
            }
            this.dsHDBC.push(rowHDBBC)

            // // hdtb_bancheo
            // //dsHDTBBC = new HDTB_BANCHEO_DATA();
            // HDTB_BANCHEO_DATA.HDTB_BANCHEORow rowHDTBBC = dsHDTBBC.HDTB_BANCHEO.NewHDTB_BANCHEORow();
            var rowHDTBBC={}
            if(daucuoi_id==1){
                if(!this.kenh_trang){
                    let result=await this.lay_danhba_theo_thuebaoid(this.tinh_dau, this.thuebao_tc_id, this.dichvu_vt_selected, 0, 2)
                    //upper case key data source
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    rowHDTBBC=createObjectHDTB_BANCHEO(result[0])
                }else{
                    rowHDTBBC=createObjectHDTB_BANCHEO(this.dsHDTB[0])
                    rowHDTBBC.THUEBAO_ID=null
                    rowHDTBBC.DONVI_ID=null
                    rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_dau.muccuoctb_id
                    let muccuoc_tb=await this.fn_map_bancheo_muccuoc_tb(this.thongso_tc_dau.muccuoctb_id, 1, this.tinh_dau,'0')
                    if(muccuoc_tb!='-1'){
                        rowHDTBBC.MUCUOC_TB=Number(muccuoc_tb)
                    }
                    rowHDTBBC.DOITUONG_ID = this.thongso_tc_dau.doituong_id
                }

                rowHDTBBC.KIEULD_ID = this.Kieuld_tc_id

                rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_dau
                rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_dau

                rowHDTBBC.TINH_ID = this.tinh_dau
                rowHDTBBC.TINH_DC_ID = 1
                rowHDTBBC.HDTB_ID = this.hdtb_id
                rowHDTBBC.HDKH_ID = this.hdkh_id
                rowHDTBBC.TTHD_ID = 1

                rowHDTBBC.NGUOI_CN = this.$root.token.getUserName()
                rowHDTBBC.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
                rowHDTBBC.MAY_CN = 'localhost'
                rowHDTBBC.IP_CN = '127.0.0.1'
                rowHDTBBC.GHICHU = this.$refs.thongTinPhieuYeuCau.input_ghichu.trim()
            }else{
                if(!this.kenh_trang){
                    let result=await this.lay_danhba_theo_thuebaoid(this.tinh_cuoi, this.thuebao_tc_id_tsl, this.dichvu_vt_selected, 0, 2)
                    // //upper case key data source
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    //let result={}
                    rowHDTBBC=createObjectHDTB_BANCHEO(result[0])
                }else{
                    rowHDTBBC=createObjectHDTB_BANCHEO(this.dsHDTB[0])
                    rowHDTBBC.THUEBAO_ID=null
                    rowHDTBBC.DONVI_ID=null
                    rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_cuoi.muccuoctb_id
                    let muccuoc_tb=await this.fn_map_bancheo_muccuoc_tb(this.thongso_tc_cuoi.muccuoctb_id, 1, this.tinh_cuoi,'0')
                    if(muccuoc_tb!='-1'){
                        rowHDTBBC.MUCUOC_TB=Number(muccuoc_tb)
                    }
                    rowHDTBBC.DOITUONG_ID = this.thongso_tc_cuoi.doituong_id
                }
                rowHDTBBC.KIEULD_ID = this.Kieuld_tc_id_tsl

                rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_cuoi
                rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_cuoi

                rowHDTBBC.TINH_ID = this.tinh_cuoi
                rowHDTBBC.TINH_DC_ID = 2
                rowHDTBBC.HDTB_ID = this.hdtb_id
                rowHDTBBC.HDKH_ID = this.hdkh_id
                rowHDTBBC.TTHD_ID = 1

                rowHDTBBC.NGUOI_CN = this.$root.token.getUserName()
                rowHDTBBC.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
                rowHDTBBC.MAY_CN = 'localhost'
                rowHDTBBC.IP_CN = '127.0.0.1'
                rowHDTBBC.GHICHU = this.$refs.thongTinPhieuYeuCau.input_ghichu.trim()
            }

            this.dsHDTBBC.push(rowHDTBBC)
        },
        async TaoDuLieu_HDTBBC_CD(themmoi){
            this.dsHDBCCD=[]
            let result=await this.lay_ds_db_cd_theo_tbid_bancheo(this.thuebao_tc_id, this.tinh_thicong)
            //upper case key data source
            for(var i = 0; i < result.length; i++){ 
                for (var key in result[i]) {
                    if(key.toUpperCase() !== key){
                        result[i][key.toUpperCase()] = result[i][key];
                        delete result[i][key];
                    }
                }
            }
            var object=createObjectHDTBBC_CD(result[0])
            object.HDTB_ID=this.hdtb_id
            object.TRANGBI_ID = this.thongso_tc.trangbi_id
            if(themmoi){
                object.MADOICAP=await this.get_key_bancheo('MADOICAP', this.tinh_thicong)
            }
            this.dsHDBCCD.push(object)

        },
        async TaoDuLieu_HDTBBC_IMS(themmoi){
            this.dsHDBCIMS=[]
            let result=await this.lay_ds_db_ims_theo_tbid_bancheo(this.thuebao_tc_id, this.tinh_thicong)
            //upper case key data source
            for(var i = 0; i < result.length; i++){ 
                for (var key in result[i]) {
                    if(key.toUpperCase() !== key){
                        result[i][key.toUpperCase()] = result[i][key];
                        delete result[i][key];
                    }
                }
            }
            var object=createObjectHDTBBC_IMS(result[0])
            object.HDTB_ID = this.hdtb_id
            object.THIETBI_ID = this.$refs.ims.thietbi_ims_moi_selected?this.$refs.ims.thietbi_ims_moi_selected:0

            var kieu=''
            let kieuLD=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x=>x.kieuld_id==this.$refs.thongTinPhieuYeuCau.kieu_yc_selected)
            kieu=kieuLD!=undefined?kieuLD.kieu:''
            if(kieu=='1'||kieu=='2'){
                object.MADOICAP = this.thongso_tc.ma_doicap
            }else{
                if(themmoi){
                    object.MADOICAP=await this.get_key_bancheo('MADOICAP', this.tinh_thicong)
                }else{
                    object.MADOICAP = this.thongso_tc.ma_doicap
                }
            }

            object.MATB_TN = this.thongso_tc.ma_tn
            object.TRANGBI_ID = this.thongso_tc.trangbi_id

            let tinh=await this.sp_lay_tinh_tc_theo_tinh_id(this.tinh_thicong)

            object.USERNAME = "84" + tinh.mavung + this.getMaTB()
            object.PASSWORD = "84" + tinh.mavung + this.getMaTB()

            if(this.$refs.ims.loaihinh_tb_moi_selected&&this.$refs.ims.loaihinh_tb_moi_selected==LoaiHinhTB.IMS_SIPTRUNKING){
                object.SL_CUOCGOI = Number(this.$refs.ims.input_sl_cuocgoi.trim()!=''?this.$refs.ims.input_sl_cuocgoi.trim():0)
            }

            this.dsHDBCIMS.push(object)
        },
        async TaoDuLieu_HDTBBC_ADSL(themmoi){
            this.dsHDBCADSL=[]
            //HDBC_ADSL_DATA.xsd
            var object=createObjectHDTBBC_ADSL({})
            object.HDTB_ID = this.hdtb_id
            object.PASSWORD = this.txtMatKhauADSL
            object.EMAIL = this.txtEmailAdsl
            object.TOCDO_ID = this.thongso_tc.tocdo_id
            object.CHUQUAN_ID = this.thongso_tc.chuquan_id
            object.CHUQUANLD_ID = this.thongso_tc.chuquanld_id

            let profiledsl_id=Number(await this.fn_map_bancheo_tocdo_adsl(this.thongso_tc.tocdo_id, 3, this.tinh_thicong, '-1'))
            //
            if(profiledsl_id!=-1){
                object.PROFILEDSL_ID = profiledsl_id
            }
            
            if (this.thongso_tc.tocdothuc_id > 0){
                object.TOCDOTHUC_ID = this.thongso_tc.tocdothuc_id
            }else{
                object.TOCDOTHUC_ID = object.TOCDO_ID
            }
               
            object.MUCCUOC_ID = this.thongso_tc.muccuoc_id
            object.THIETBIDC_ID = this.thongso_tc.thietbidc_id

            //fn_map_bancheo_db_adsl
            if(this.thamso.TUDONG_SINH_SOAO&&this.thuebao_tc_id<=0){
                if(this.$root.token.getMaTinh()=='HPG'){
                    //Code riêng HPG, nếu mã lt ko đúng chuẩn thì sinh mã mới
                    var isOld=false
                    let malt=await this.fn_map_bancheo_db_adsl(this.thuebao_tc_id, 3, this.tinh_thicong, '-1')
                    if(malt.toString()!='-1'&&malt.toString().length>3&&malt.toString().substring(0, 3)!='131'){
                        isOld=true
                    }
                    if(isOld){
                        object.MA_LT=await this.sinh_malt({
                            tinhthicong_id: this.tinh_thicong,
                            vquan_id: 0,
                            vdichvuvt_id: DichVuVienThong.ADSL,
                            vloaitb_id: this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected?this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected:0,
                            vtentat: ''
                        })
                    }else{
                        object.MA_LT = malt
                    }
                }else{
                    object.MA_LT=await this.sinh_malt({
                        tinhthicong_id: this.tinh_thicong,
                        vquan_id: this.dataDiaChiLD.quan_id,
                        vdichvuvt_id: DichVuVienThong.ADSL,
                        vloaitb_id: this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected?this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected:0,
                        vtentat: ''
                    })
                }
            }else if(this.thuebao_tc_id>0&&this.thamso.TUDONG_SINH_SOAO){
                let malt=await this.fn_map_bancheo_db_adsl(this.thuebao_tc_id, 3, this.tinh_thicong, '-1')
                if(malt.toString()!='-1'){
                    object.MA_LT = malt
                }
            }

            object.MATB_TN = this.thongso_tc.ma_tn
            //Gán giá trị cho column tức thì theo mức cước
            if(this.$refs.bangRongCoDinh.chkTucThi){
                if(this.$refs.bangRongCoDinh.muccuoc_selected!=this.$refs.bangRongCoDinh.muccuoc_moi_selected){
                    object.TUCTHI = 3
                }else{
                    object.TUCTHI = 2
                }
            }else{
                //Đối với chuyển đổi mega-FTTH chỉ có tháng 6 mới chuyển đổi (tucthi=0)
                object.TUCTHI = 0
            }

            object.TRANGBI_ID = this.thongso_tc.trangbi_id
            object.THONGTIN_TC = this.$refs.bangRongCoDinh.input_tttc

            var kieu=''
            let kieuLD=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x=>x.kieuld_id==this.$refs.thongTinPhieuYeuCau.kieu_yc_selected)
            kieu=kieuLD!=undefined?kieuLD.kieu:''
            if(kieu=='1'||kieu=='2'){
                if(!this.thongso_tc.ma_doicap||this.thongso_tc.ma_doicap.toString()==''){
                    this.kt_adsl = false
                    this.$toast.error('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập')
                    return
                }
                object.MADOICAP = this.thongso_tc.ma_doicap
            }else{
                if(themmoi){
                    object.MADOICAP=await this.get_key_bancheo('MADOICAP', this.tinh_thicong)
                }
            }

            this.dsHDBCADSL.push(object)
        },
        async TaoDuLieu_HDTBBC_MGWAN(themmoi){
            this.dsHDBCMGWAN=[]
            var object={}
            
            if (!this.kenh_trang && !this.chi_giao_net){
                //!= kenh trang
                let result=await this.lay_ds_db_megawan_theo_tbid_bancheo(this.thuebao_tc_id, this.tinh_thicong)
                //upper case key data source
                for(var i = 0; i < result.length; i++){ 
                    for (var key in result[i]) {
                        if(key.toUpperCase() !== key){
                            result[i][key.toUpperCase()] = result[i][key];
                            delete result[i][key];
                        }
                    }
                }
                object=createObjectHDTBBC_MGWAN(result.length>0?result[0]:{})

                object.HDTB_ID = this.hdtb_id
                object.TOCDO_ID = this.thongso_tc.tocdo_id

                if(this.$refs.megawan.checkTocDoPIRMoi){
                    object.TOCDO_PIR_ID = this.thongso_tc.tocdo_pir_id
                }
                    
                object.LOAIMD_ID = this.thongso_tc.loaimd_id
                object.LOAIKENH_ID = this.thongso_tc.loaikenh_id
                object.THIETBIDC_ID = this.thongso_tc.thietbidc_id
                object.HUONG_KN = this.vhuong_kn

                object.THOIHAN_ID = this.thongso_tc.thoihan_id
                object.TRANGBI_ID = this.thongso_tc.trangbi_id
                object.CHUQUAN_ID = this.thongso_tc.chuquan_id
                object.LOAICAP_ID = this.$refs.megawan.loaicap_moi_selected
                object.CHUQUANLD_ID = this.thongso_tc.chuquanld_id

                if(this.thamso.TUDONG_SINH_SOAO){
                    if(this.$root.token.getMaTinh()=='HPG'){
                        //Code riêng HPG, nếu mã lt ko đúng chuẩn thì sinh mã mới
                        var isOld=false
                        var malt=result[0].MA_LT?result[0].MA_LT.toString():''
                        if(malt.length>3&&malt.substring(0, 3)!='131'){
                            isOld=true
                        }
                        if(isOld){
                            object.MA_LT=await this.sinh_malt({
                                tinhthicong_id: this.tinh_thicong,
                                vquan_id: 0,
                                vdichvuvt_id: DichVuVienThong.ADSL,
                                vloaitb_id: this.$refs.megawan.loaihinh_tb_moi_selected?this.$refs.megawan.loaihinh_tb_moi_selected:0,
                                vtentat: ''
                            })
                        }        
                    }
                }
                object.MA_PL=this.$refs.thongTinPhieuYeuCau.input_ma_cng.trim()
                object.SITE_ID = this.$refs.megawan.input_site_id_moi.trim()
                object.MA_BSS = this.$refs.thongTinKhachHang.input_ma_bss.trim()
                object.ID_ERP =this.$refs.thongTinKhachHang.input_ma_erp.trim()
                if(this.$refs.thongTinKhachHang.checkSoCV){
                    object.MA_CV = this.$refs.thongTinKhachHang.input_socv
                    object.NGAY_CV = moment(this.$refs.thongTinKhachHang.ngay_cv, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
                }
                //Thay đổi loại hình tb pải sinh mã đôi cáp
                if(themmoi){
                    object.MADOICAP =await this.get_key_bancheo('MADOICAP', this.tinh_thicong)
                }

            }else{
                //kenh trang
                object=createObjectHDTBBC_MGWAN({})
                object.HDTB_ID = this.hdtb_id
                object.CHUQUANLD_ID = this.thongso_tc.chuquanld_id
                object.MA_PL=this.$refs.thongTinPhieuYeuCau.input_ma_cng.trim()
                object.MADOICAP = -1
                object.LOAIMD_ID = this.thongso_tc.loaimd_id
                object.CHUQUAN_ID = this.thongso_tc.chuquan_id?this.thongso_tc.chuquan_id:null
                object.LOAIKENH_ID = this.thongso_tc.loaikenh_id?this.thongso_tc.loaikenh_id:null
                object.THIETBIDC_ID = this.thongso_tc.thietbidc_id?this.thongso_tc.thietbidc_id:null
                object.THOIHAN_ID = this.thongso_tc.thoihan_id?this.thongso_tc.thoihan_id:null
                object.TOCDO_ID = this.thongso_tc.tocdo_id?this.thongso_tc.tocdo_id:null
                object.TOCDO_ISP = this.thongso_tc.tocdo_isp?this.thongso_tc.tocdo_isp:null
                object.TOCDO_NIX = this.thongso_tc.tocdo_nix?this.thongso_tc.tocdo_nix:null
                object.TOCDO_PIR_ID = this.thongso_tc.tocdo_pir_id?this.thongso_tc.tocdo_pir_id:null
                object.TRANGBI_ID = this.thongso_tc.trangbi_id?this.thongso_tc.trangbi_id:null
            }

            this.dsHDBCMGWAN.push(object)
        },
        async TaoDuLieu_HDTBBC_TSL(themmoi){
            console.log('TaoDuLieu_HDTBBC_TSL1')
            //this.dsHDBCTSL=[]
            //Tạo điểm đầu            
            var rowHDBCTSL=createObjectHDTBBC_TSL({TINH_ID:this.tinh_dau})
            rowHDBCTSL.HDTB_ID = this.hdtb_id;
            rowHDBCTSL.DAUCUOI_ID = 1
            rowHDBCTSL.TOCDO_ID = this.thongso_tc.tocdo_id
            rowHDBCTSL.LOAIKENH_ID = this.thongso_tc.loaikenh_id
            rowHDBCTSL.THOIHAN_ID = this.thongso_tc.thoihan_id
            rowHDBCTSL.TRANGBI_ID = this.thongso_tc.trangbi_id
            rowHDBCTSL.CHUQUAN_ID = this.thongso_tc.chuquan_id
            rowHDBCTSL.THIETBIDC_ID = this.thongso_tc.thietbidc_id
            rowHDBCTSL.MA_PL = this.$refs.thongTinPhieuYeuCau.input_ma_cng.trim()
            if(this.$refs.thongTinKhachHang.checkSoCV){
                rowHDBCTSL.MA_CV = this.$refs.thongTinKhachHang.input_socv
                rowHDBCTSL.NGAY_CV = this.$refs.thongTinKhachHang.ngay_cv
            }
            if(this.$refs.kenhThuRieng.checkDoiTacMoi){
                rowHDBCTSL.DOITAC_ID = this.$refs.kenhThuRieng.doitac_moi_selected
                rowHDBCTSL.CUOC_TKDT = this.$refs.kenhThuRieng.input_cuoc_tkdt_moi
                rowHDBCTSL.CUOC_TCDT = this.$refs.kenhThuRieng.input_cuoc_tcdt_moi
                rowHDBCTSL.MAHD_DT = this.$refs.kenhThuRieng.input_ma_hddt_moi
            }
            rowHDBCTSL.CUOC_HT = this.$refs.kenhThuRieng.input_cuoc_ht_moi
            rowHDBCTSL.CUOC_TBI = this.$refs.kenhThuRieng.input_cuoc_tb_moi
            rowHDBCTSL.CUOC_TC = this.$refs.kenhThuRieng.input_cuoc_tc_moi
            rowHDBCTSL.CUOC_TK = this.$refs.kenhThuRieng.input_cuoc_tk_moi

            //Điểm đầu
            rowHDBCTSL.DIACHI = this.$refs.kenhThuRieng.input_diachi_dau_moi.trim()
            rowHDBCTSL.DIACHI_ID = this.diachidau_id
            rowHDBCTSL.MA_TB_SUB = this.ma_tb_dau
            rowHDBCTSL.LOAICAP_ID = this.$refs.kenhThuRieng.lcapdau_moi_selected
            rowHDBCTSL.TINH_DC_ID = 1
            this.dsHDBCTSL.push(rowHDBCTSL)

            //Tạo điểm cuối
            rowHDBCTSL=createObjectHDTBBC_TSL({TINH_ID:this.tinh_cuoi})
            rowHDBCTSL.HDTB_ID = this.hdtb_id;
            rowHDBCTSL.DAUCUOI_ID = 2
            rowHDBCTSL.TOCDO_ID = this.thongso_tc.tocdo_id
            rowHDBCTSL.LOAIKENH_ID = this.thongso_tc.loaikenh_id
            rowHDBCTSL.THOIHAN_ID = this.thongso_tc.thoihan_id
            rowHDBCTSL.TRANGBI_ID = this.thongso_tc.trangbi_id
            rowHDBCTSL.CHUQUAN_ID = this.thongso_tc.chuquan_id
            rowHDBCTSL.THIETBIDC_ID = this.thongso_tc.thietbidc_id
            rowHDBCTSL.MA_PL = this.$refs.thongTinPhieuYeuCau.input_ma_cng.trim()
            if(this.$refs.thongTinKhachHang.checkSoCV){
                rowHDBCTSL.MA_CV = this.$refs.thongTinKhachHang.input_socv
                rowHDBCTSL.NGAY_CV = this.$refs.thongTinKhachHang.ngay_cv
            }
            if(this.$refs.kenhThuRieng.checkDoiTacMoi){
                rowHDBCTSL.DOITAC_ID = this.$refs.kenhThuRieng.doitac_moi_selected
                rowHDBCTSL.CUOC_TKDT = this.$refs.kenhThuRieng.input_cuoc_tkdt_moi
                rowHDBCTSL.CUOC_TCDT = this.$refs.kenhThuRieng.input_cuoc_tcdt_moi
                rowHDBCTSL.MAHD_DT = this.$refs.kenhThuRieng.input_ma_hddt_moi
            }
            rowHDBCTSL.CUOC_HT = this.$refs.kenhThuRieng.input_cuoc_ht_moi
            rowHDBCTSL.CUOC_TBI = this.$refs.kenhThuRieng.input_cuoc_tb_moi
            rowHDBCTSL.CUOC_TC = this.$refs.kenhThuRieng.input_cuoc_tc_moi
            rowHDBCTSL.CUOC_TK = this.$refs.kenhThuRieng.input_cuoc_tk_moi

            rowHDBCTSL.DIACHI = this.$refs.kenhThuRieng.input_diachi_cuoi_moi.trim()
            rowHDBCTSL.DIACHI_ID = this.diachicuoi_id
            rowHDBCTSL.MA_TB_SUB = this.ma_tb_cuoi
            rowHDBCTSL.LOAICAP_ID = this.$refs.kenhThuRieng.lcapcuoi_moi_selected
            rowHDBCTSL.TINH_DC_ID = 2

            this.dsHDBCTSL.push(rowHDBCTSL)

        },
        async HienThiControlFSecure(isFS){
            this.$refs.thongTinPhieuYeuCau.disableLoaiHinhTB=isFS
            this.$refs.thongTinPhieuYeuCau.disableKieuLD=isFS
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                this.$refs.coDinh.disableLoaiHinhTB=isFS
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                this.$refs.bangRongCoDinh.disableLoaiHinhTB=isFS
                this.$refs.bangRongCoDinh.disableGoiCuocADSLCu=isFS
                this.$refs.bangRongCoDinh.disableTocDoADSLCuThuc=isFS
                this.$refs.bangRongCoDinh.disableGoiCuocADSLMoi=isFS
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                this.$refs.ims.disableLoaiHinhTB=isFS

            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                this.$refs.kenhThuRieng.disableLoaiHinhTB=isFS
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                this.$refs.megawan.disableLoaiHinhTB=isFS
            }
        },
        async lay_dv_theo_loai_dv_diachi_ld(loaidv_dich_id, phuongld_id, phold_id, apld_id, khuld_id){
            //sp_lay_donvi_theo_loaidv_diachi_ld_v1
            if(loaidv_dich_id!=LOAI_DV.TRAM_VT&&loaidv_dich_id!=LOAI_DV.TTKD_NGHIEMTHU){
                let dsvt = await this.sp_lay_donvi_theo_loaidv_diachi_ld_v1({
                    p_loaidv_id: loaidv_dich_id,
                    p_phuong_id: phuongld_id,
                    p_pho_id: phold_id,
                    p_ap_id: apld_id,
                    p_khu_id: khuld_id
                })
                if(dsvt.length>0){
                    return Number(dsvt[0].donvi_id)
                }else{
                    return -1
                }
            }else if(loaidv_dich_id == LOAI_DV.TTKD_NGHIEMTHU){
                return this.$root.token.getDonViID()
            }else {
                return -1
            }
        },
        async HT_Tien_ConLai_DatCoc_Cu(thuebao_id, loaitb_id, kieuld_id){
            this.dtienthoai_dc=0
            if(loaitb_id==LoaiHinhTB.MEGA&&thuebao_id!=0&&kieuld_id==KieuLapDat.TDLH_MEGA_FTTH){
                let thang_thoai=Number(moment(new Date()).add(1, 'M').format('YYYYMM'))
                let result= await this.lay_ds_tien_dc_conlai(thuebao_id, loaitb_id, thang_thoai)
                if(result.length>0){
                    this.dtienthoai_dc = result[0].tien_conlai!=null?result[0].tien_conlai:0
                }
            }
            await this.HT_KM_Tien_Thoai_DC()
        },
        async HT_KM_Tien_Thoai_DC(){
            for(let i=0; i<this.ds_tien_khoanmuc.length; i++){
                if(this.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_TIENTHOAI_DC.toString()){
                    if((this.dtiendatcoc_tt*11/10)>this.dtienthoai_dc){
                        this.ds_tien_khoanmuc[i].tien=Math.abs(Math.round(this.dtienthoai_dc*10/11))
                        this.ds_tien_khoanmuc[i].vat=Math.abs(Math.round(this.dtienthoai_dc/11))
                    }else{
                        this.ds_tien_khoanmuc[i].tien=Math.abs(Math.round(this.dtiendatcoc_tt))
                        this.ds_tien_khoanmuc[i].vat=Math.abs(Math.round(this.dvatdatcoc_tt))
                    }
                }
            }
            await this.LayTongTien_HDTB()
        },
        async LayTongTien_HDTB(){
            console.log('LayTongTien_HDTB')
            var dtongtien = 0
            var dtongvat = 0
            for(let i=0;i<this.ds_tien_khoanmuc.length;i++){
                if(this.ds_tien_khoanmuc[i].khoanmuctt_id.toString()==KHOANMUC_TT.KMTT_KM_LAPDAT.toString()||
                    this.ds_tien_khoanmuc[i].khoanmuctt_id.toString()==KHOANMUC_TT.KMTT_TIENTHOAI_DC.toString()
                    ||this.ds_tien_khoanmuc[i].khoanmuctt_id.toString()==KHOANMUC_TT.KMTT_KM_DATCOC.toString()){
                    if(Number(this.ds_tien_khoanmuc[i].tien)<0){
                        dtongtien+=Number(this.ds_tien_khoanmuc[i].tien)
                        dtongvat+=Number(this.ds_tien_khoanmuc[i].vat)
                    }else{
                        dtongtien+=Number(this.ds_tien_khoanmuc[i].tien)*-1
                        dtongvat+=Number(this.ds_tien_khoanmuc[i].vat)*-1
                    }
                // }else{
                //     dtongtien+=Number(this.ds_tien_khoanmuc[i].tien)
                //     dtongvat+=Number(this.ds_tien_khoanmuc[i].vat)
                // }
                //comment
                }else if(this.ds_tien_khoanmuc[i].khoanmuctt_id.toString()!=KHOANMUC_TT.KMTT_TRAGOP_THIETBI.toString()&&
                    this.ds_tien_khoanmuc[i].khoanmuctt_id.toString()!=KHOANMUC_TT.KMTT_KM_THIETBI.toString()
                    &&this.ds_tien_khoanmuc[i].khoanmuctt_id.toString()!=KHOANMUC_TT.KMTT_MUATHIETBI.toString()){
                    dtongtien+=Number(this.ds_tien_khoanmuc[i].tien)
                    dtongvat+=Number(this.ds_tien_khoanmuc[i].vat)
                }
            }
            console.log('LayTongTien_HDTB dtongtien', dtongtien)
            console.log('LayTongTien_HDTB dtongvat', dtongvat)
            await this.$refs.chiTietTienTheoKhoanMuc.TongTienTB(dtongtien, dtongvat)
        },
        async LayTienTheoKhoanMuc(){
            console.log('LayTienTheoKhoanMuc')
            //Hàm lấy tiền lên ô text box ngày 14/09/2010
            if(this.dichvuvt_id!=DichVuVienThong.ADSL&&this.dichvuvt_id!=DichVuVienThong.MEGAWAN&&this.dichvuvt_id!=DichVuVienThong.METRONET&&this.dichvuvt_id!=26){
                this.kh_cd=0
                this.ltb_adsl_id=1
            }
            let kieuld_id=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected!=null?this.$refs.thongTinPhieuYeuCau.kieu_yc_selected:'0'

            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                if(kieuld_id==KieuLapDat.CHUYENDD_TRASAU_SANG_TRATRUOC.toString()){
                    return
                }
                if(this.loaihinhtb_id==LoaiHinhTB.DIDONGTRATRUOC||this.loaihinhtb_id==LoaiHinhTB.MEGAWAN_ADSL){
                    return
                }
                this.ltb_adsl_id=this.$refs.bangRongCoDinh.loaithietbi_moi_selected!=null?Number(this.$refs.bangRongCoDinh.loaithietbi_moi_selected):0
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                if(kieuld_id==KieuLapDat.TDLH_MEGA_FTTH.toString()||kieuld_id==KieuLapDat.CHUYENDD_TRASAU_SANG_TRATRUOC.toString()){
                    return
                }
                this.ltb_adsl_id=this.$refs.megawan.loaithietbi_moi_selected!=null?Number(this.$refs.megawan.loaithietbi_moi_selected):0
            }else if(this.dichvu_vt_selected==DichVuVienThong.GPHONE){
                if(kieuld_id==KieuLapDat.TDLH_MEGA_FTTH.toString()||kieuld_id==KieuLapDat.CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_CAPQUANG.toString()){
                    return
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                if(this.loaihinhtb_id==LoaiHinhTB.INTERNET_FTTH||this.loaihinhtb_id==LoaiHinhTB.WIFI_FIBER||this.loaihinhtb_id==LoaiHinhTB.MEGAWAN_ADSL){
                    return
                }
            }
            if(this.loaihinhtb_id==null||this.loaihinhtb_id==0){
                return
            }
            var muccuoc = 0
            var iTocdo_id = 0
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                muccuoc=this.$refs.bangRongCoDinh.muccuoc_moi_selected!=null?Number(this.$refs.bangRongCoDinh.muccuoc_moi_selected):0
                iTocdo_id=this.$refs.bangRongCoDinh.tocdo_moi_selected!=null?Number(this.$refs.bangRongCoDinh.tocdo_moi_selected):0
            }
            if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                iTocdo_id=this.$refs.megawan.tocdo_cir_moi_selected!=null?Number(this.$refs.megawan.tocdo_cir_moi_selected):0
            }
            let ngay_yc=await this.$refs.thongTinPhieuYeuCau.getNgayYC()
            let ds=await this.lay_tien_khoanmuc_ld({
                loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                ngay_yc:ngay_yc,
                dichvuvt_id:this.dichvu_vt_selected,
                loaitb_id:this.loaihinhtb_id,
                kieuld_id:kieuld_id,
                phuongld_id:this.dataDiaChiLD.phuong_id,
                tocdo_id:iTocdo_id,
                muccuoc_id:muccuoc,
                ltb_adsl_id:this.ltb_adsl_id,
                ltb_adsl_moi_id:0,
                ltb_adsl_cu_id:0,
                tocdo_moi_id:0,
                tocdo_cu_id:0,
                kh_cd:0,
                phold_id:this.dataDiaChiLD.pho_id,
                apld_id:this.dataDiaChiLD.ap_id,
                khuld_id:this.dataDiaChiLD.khu_id,
                doituong_id:this.doituong_id,
                trangbi_id:this.trangbi_id
            })
            //nếu dịch vụ là internet thì loại hình tb mới pải là ftth & kiểu ld = chuyển từ mega->ftth
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                if(this.loaihinhtb_id==LoaiHinhTB.INTERNET_FTTH||this.loaihinhtb_id==LoaiHinhTB.WIFI_FIBER){
                    if(kieuld_id!=KieuLapDat.TDLH_MEGA_FTTH){
                        return
                    }
                }
            }
            //Nếu loại hình mới = loại hình cũ -> return
            if(this.loaihinhtbcu_id==Number(this.loaihinhtb_id)&&Number(this.loaihinhtb_id)!=LoaiHinhTB.INTERNET_MYTV){
                this.dtienld = 0
                this.dvatld = 0
                return
            }

            if(ds.length>0){
                await this.HienThiTienLapDat(ds)
                await this.LayTongTien_HDTB()
            }else{
                this.dtienld = 0
                this.dvatld = 0
            }

        },
        async HienThiTienLapDat(ds){
            console.log('HienThiTienLapDat')
            var kt_lm=true
            if(ds[0].tien!='-1'){
                for(let i=0;i<ds.length;i++){
                    if(ds[i].khoanmuctt_id==KHOANMUC_TT.KMTT_TDLOAIHINHTB.toString()){
                        this.dtienld = Number(ds[i].tien)
                        this.dvatld = Number(ds[i].vat)
                        kt_lm = false
                    }
                }
                if(kt_lm){
                    this.dtienld = 0
                    this.dvatld = 0
                }

            }else{
                this.dtienld = 0
                this.dvatld = 0
            }
            for(let i=0;i<this.ds_tien_khoanmuc.length;i++){
                if(this.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_TDLOAIHINHTB.toString()){
                    this.ds_tien_khoanmuc[i].tien=this.dtienld
                    this.ds_tien_khoanmuc[i].vat=this.dvatld
                }
            }
            await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.dtiendatcoc_km, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)

        },
        async HienThiTienKhuyenMaiLapDat(tien_kmld, vat_kmld, tien_datcoc, dtienkmdc, dvatkmdc, dtiendatcoc_tt){
            console.log('HienThiTienKhuyenMaiLapDat')
            //
            var dtienkmld_tmp = 0
            var dvatkmld_tmp = 0
            var dtyle_kmld_tmp

            dtienkmld_tmp = this.dtienkmld
            dvatkmld_tmp = this.dvatkmld
            dtyle_kmld_tmp = this.tyle_kmld

            if(Math.abs(this.dtienkmld)<Math.abs(dtienkmdc)){
                dtienkmld_tmp=dtienkmdc
                dvatkmld_tmp=dvatkmdc
            }
            if(dtyle_kmld_tmp<this.tyle_kmld_tt){
                dtyle_kmld_tmp=this.tyle_kmld_tt
            }
            if(dtyle_kmld_tmp>0){
                dtienkmld_tmp=Math.round(this.dtienld*dtyle_kmld_tmp/100)
                dvatkmld_tmp=(this.dtienld + this.dvatld)*dtyle_kmld_tmp/100-dtienkmld_tmp
            }

            for(let i=0;i<this.ds_tien_khoanmuc.length;i++){
                if(this.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_KM_LAPDAT.toString()){
                    if(this.dtienkmld<0){
                        this.ds_tien_khoanmuc[i].tien=dtienkmld_tmp*(-1)
                        this.ds_tien_khoanmuc[i].vat=dvatkmld_tmp*(-1)
                    }else{
                        this.ds_tien_khoanmuc[i].tien=dtienkmld_tmp
                        this.ds_tien_khoanmuc[i].vat=dvatkmld_tmp
                    }
                }else if(this.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_DATCOC.toString()){
                    this.ds_tien_khoanmuc[i].tien=this.dtiendatcoc_tt
                    this.ds_tien_khoanmuc[i].vat=this.dvatdatcoc_tt
                }else if(this.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_DATCOC_KM.toString()){
                    this.ds_tien_khoanmuc[i].tien=Math.round(this.dtiendatcoc_km*10/11)
                    this.ds_tien_khoanmuc[i].vat=Math.round(this.dtiendatcoc_km/11)
                }
            }
            //
            await this.LayTongTien_HDTB()

        },
        async changeCheckKhuyenMai(data){
            this.dtienkmld=0
            this.dvatkmld=0
            if(data.isChecked){
                if(data.lenght_ds_khuyenmai>0){
                    if(data.khuyenmai_selected!=null&&data.khuyenmai_selected!=0){

                    }else{
                        this.dtienkmld = 0
                        this.dvatkmld = 0
                        this.tyle_kmld = 0
                    }
                }else{
                    this.dtienkmld = 0
                    this.dvatkmld = 0
                    this.tyle_kmld = 0
                }
            }else{
                this.dtienkmld = 0
                this.dvatkmld = 0
                this.tyle_kmld = 0
                this.dsKM_HDTB = []
            }
            await this.LaySothang_SD()

            await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt);
        },
        async changeCheckTraTruoc(data){
            if(data.isChecked){
                if(data.lenght_ds_tratruoc==0){
                    this.dtienkmdc = 0
                    this.dvatkmdc = 0
                    this.tyle_kmld_tt = 0
                    this.dtiendatcoc_tt = 0
                    this.dvatdatcoc_tt = 0
                }
            }else{
                this.dtienkmdc = 0
                this.dvatkmdc = 0
                this.tyle_kmld_tt = 0
                this.dtiendatcoc_tt = 0
                this.dvatdatcoc_tt = 0
                this.dsDC_HDTB = []
            }
            await this.LaySothang_SD()
            await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
            let kieuld_id=await this.$refs.thongTinPhieuYeuCau.getKieuID()
            await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
        },
        async LaySothang_SD(){
            console.log('LaySothang_SD')
            let thongTinPhieuYC=this.$refs.thongTinPhieuYeuCau
            if(!thongTinPhieuYC.khuyenmai.checkKhuyenMai&&!thongTinPhieuYC.tratruoc.checkTraTruoc){
                this.sothang_sd=0
            }else if(!thongTinPhieuYC.khuyenmai.checkKhuyenMai&&thongTinPhieuYC.tratruoc.checkTraTruoc){
                let khuyenmai_id_tt=thongTinPhieuYC.tratruoc.tratruoc_selected!=null?thongTinPhieuYC.tratruoc.tratruoc_selected:0
                let strStkm=await this.fn_map_khuyenmai(khuyenmai_id_tt, 2, '-1')
                this.sothang_sd = strStkm!='-1'?Number(strStkm):0
            }else if(thongTinPhieuYC.khuyenmai.checkKhuyenMai&&!thongTinPhieuYC.tratruoc.checkTraTruoc){
                let khuyenmai_id=thongTinPhieuYC.khuyenmai.khuyenmai_selected!=null?thongTinPhieuYC.khuyenmai.khuyenmai_selected:0
                let strStkm=await this.fn_map_khuyenmai(khuyenmai_id, 2, '-1')
                this.sothang_sd = strStkm!='-1'?Number(strStkm):0
            }else if(thongTinPhieuYC.khuyenmai.checkKhuyenMai&&thongTinPhieuYC.tratruoc.checkTraTruoc){
                let khuyenmai_id_tt=thongTinPhieuYC.tratruoc.tratruoc_selected!=null?thongTinPhieuYC.tratruoc.tratruoc_selected:0
                let strSttt=await this.fn_map_khuyenmai(khuyenmai_id_tt, 2, '-1')
                let st_tt=strSttt!='-1'?Number(strSttt):0

                let khuyenmai_id=thongTinPhieuYC.khuyenmai.khuyenmai_selected!=null?thongTinPhieuYC.khuyenmai.khuyenmai_selected:0
                let strStkm=await this.fn_map_khuyenmai(khuyenmai_id, 2, '-1')
                let st_km = strStkm != "-1" ? Number(strStkm) : 0
                this.sothang_sd=0
                if(st_tt>st_km){
                    this.sothang_sd=st_tt
                }else{
                    this.sothang_sd=st_km
                }
            }
            
            this.$refs.thongTinKhachHang.input_thang_ck=this.sothang_sd.toString()
        },
        async changeLoaiHinhTBMoi(loaihinhtb_id){
            this.loaihinhtb_id=loaihinhtb_id
            //Handle theo từng dịch vụ
            // if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
            //     if(loaihinhtb_id==LoaiHinhTB.DIDONGTRASAU){
            //         await this.$refs.diDong.setGoiCuocDDMoi(loaihinhtb_id)
            //     }else{
            //         //Gọi qua CCBS tạm pedding
            //        await this.HienThi_GoiCuoc_DDTraTruoc_CoTheChuyenSang(this.$refs.diDong.input_matb.trim())
            //     }
            // }
            //dịch vụ ADSL
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                this.loading(true)
                if(loaihinhtb_id==LoaiHinhTB.INTERNET_FTTH||loaihinhtb_id == LoaiHinhTB.WIFI_FIBER){
                    let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.INTERNET_FTTH)
                    let goicuocADSLCu=await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_ADSL)
                    await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
                    this.$refs.bangRongCoDinh.loaithietbi_moi_selected=LOAITBI_ADSL.CPE
                }else if(loaihinhtb_id == LoaiHinhTB.INTERNET_ADSL){
                    let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.INTERNET_ADSL)
                    let goicuocADSLCu=await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_FTTH)
                    await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
                    this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.ADSL
                }else if(loaihinhtb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC){
                    let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.INTERNET_ADSL_TINH_KHAC)
                    let goicuocADSLCu=await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
                    await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
                    this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.ADSL
                }else if(loaihinhtb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC){
                    let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
                    let goicuocADSLCu=await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_ADSL_TINH_KHAC)
                    await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
                    this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.ADSL
                }else if (loaihinhtb_id == LoaiHinhTB.FSECURE){
                    let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.FSECURE)
                    let goicuocADSLCu=await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.FSECURE)
                    await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
                    this.$refs.bangRongCoDinh.loaithietbi_moi_selected = 24
                }else if (loaihinhtb_id == LoaiHinhTB.INTERNET_MYTV){
                    let goicuocADSLCu=await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_MYTV)
                    await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLCu)
                    this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.SET_TOP_BOX
                }
                if(this.$refs.bangRongCoDinh.ds_tocdo_moi.length>0){
                    if(this.$refs.bangRongCoDinh.tocdo_moi_selected!=this.$refs.bangRongCoDinh.ds_tocdo_moi[0].tocdo_id){
                        this.$refs.bangRongCoDinh.tocdo_moi_selected=this.$refs.bangRongCoDinh.ds_tocdo_moi[0].tocdo_id
                        await this.changeGoiCuocAdsl(this.$refs.bangRongCoDinh.tocdo_moi_selected)
                    }else{
                        await this.changeGoiCuocAdsl(this.$refs.bangRongCoDinh.tocdo_moi_selected)
                    }
                }
                this.loading(false)
            }

            //Megawan
            if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET){
                if(loaihinhtb_id==LoaiHinhTB.MEGAWAN_QUANG_FE||loaihinhtb_id == LoaiHinhTB.METRONET_FE){
                    this.$refs.megawan.loaithietbi_moi_selected=LOAITBI_ADSL.CONG_FE
                }else if(loaihinhtb_id == LoaiHinhTB.MEGAWAN_QUANG_GE || loaihinhtb_id == LoaiHinhTB.METRONET_GE){
                    this.$refs.megawan.loaithietbi_moi_selected=LOAITBI_ADSL.CONG_GE
                }else if(loaihinhtb_id == LoaiHinhTB.MEGAWAN_ADSL){
                    this.$refs.megawan.loaithietbi_moi_selected=LOAITBI_ADSL.ADSL
                }else if(loaihinhtb_id == LoaiHinhTB.MEGAWAN_SHDSL){
                    this.$refs.megawan.loaithietbi_moi_selected=LOAITBI_ADSL.SHDL
                }
            }
            // if (dichvuvt_id == DichVuVienThong.GPHONE)
            // {
            //     cboTrangBiGPmoi.SelectedValue = TRANGBI.VIENTHONG_TRANGBI;
            //     trangbi_id = Convert.ToInt64(cboTrangBiGPmoi.SelectedValue);
            //     if (!kt_load)
            //         LayTienTheoKhoanMuc();
            // }
            // if (Convert.ToInt32(cboDichVuVT.SelectedValue) == DichVuVienThong.GPHONE)
            //     tocdo_id = 0;
            if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.HienThi_CTK_CTC()
            }
            
            //loại hình dịch vụ Cố định
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                if(loaihinhtb_id==LoaiHinhTB.ISDN2B_CD.toString()||
                    loaihinhtb_id==LoaiHinhTB.ISDN2B_CQ.toString()||
                    loaihinhtb_id==LoaiHinhTB.ISDN30B_CD.toString()||
                    loaihinhtb_id==LoaiHinhTB.ISDN30B_CQ.toString()||
                    loaihinhtb_id==LoaiHinhTB.PABX.toString()||
                    loaihinhtb_id==LoaiHinhTB.TRUNGKE_2M.toString()||
                    loaihinhtb_id==LoaiHinhTB.TRUNGKE_THUONG.toString()||
                    loaihinhtb_id==LoaiHinhTB.TRUNGKE_TUONGTU.toString()){
                        this.$refs.coDinh.inputLoaiSoMoiDisabled=false
                        this.$refs.coDinh.loai_so_moi_selected=LOAISO_ISDN.SOCHINH
                        this.$refs.coDinh.inputCuocTBMoiDisabled=false
                        this.$refs.thongTinPhieuYeuCau.disableMucCuocTB=true

                }else{
                    this.$refs.coDinh.inputLoaiSoMoiDisabled=true
                    this.$refs.coDinh.enableBtnChonSo=false
                    this.$refs.coDinh.input_cuoctb_moi=''
                    this.$refs.coDinh.inputCuocTBMoiDisabled=true;
                    this.$refs.thongTinPhieuYeuCau.disableMucCuocTB=false
                }
            }
            



            //Cước TB
            var vtrangbi_id=0
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                vtrangbi_id=await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
            }
            var vloaitb_id_moi=0
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                vloaitb_id_moi=await this.$refs.coDinh.getLoaiHinhTB_ID_Moi()
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                //băng rộng cố định
                vloaitb_id_moi=await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                vloaitb_id_moi=await this.$refs.ims.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                vloaitb_id_moi=await this.$refs.kenhThuRieng.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                vloaitb_id_moi=await this.$refs.megawan.getLoaiHinhTB_ID_Moi()
            }

            let ngay_yc=await this.$refs.thongTinPhieuYeuCau.getNgayYC()
            await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)

            await this.HT_DS_KhuyenMai_Combobox()

        },
        async changeDoiTuongMoi(doituong_id){
            this.doituong_id=Number(doituong_id)
            //
            await this.LayTienTheoKhoanMuc()
            //
            var vtrangbi_id=0
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                vtrangbi_id=await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
            }
            var vloaitb_id_moi=0
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                vloaitb_id_moi=await this.$refs.coDinh.getLoaiHinhTB_ID_Moi()
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                //băng rộng cố định
                vloaitb_id_moi=await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                vloaitb_id_moi=await this.$refs.ims.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                vloaitb_id_moi=await this.$refs.kenhThuRieng.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                vloaitb_id_moi=await this.$refs.megawan.getLoaiHinhTB_ID_Moi()
            }

            let ngay_yc=await this.$refs.thongTinPhieuYeuCau.getNgayYC()
            await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)
            
            await this.HT_DS_KhuyenMai_Combobox()
        },
        async changeTrangBiMoi(trangbi_id){
            this.trangbi_id=Number(trangbi_id)
            //handle theo từng dịch vụ tương ứng
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.HT_DS_KhuyenMai_Combobox()
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                await this.LayTienTheoKhoanMuc()
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                //băng rộng cố định
                await this.LayTienTheoKhoanMuc()

                var vtrangbi_id=0
                var vloaitb_id_moi=0
                if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                    vtrangbi_id=await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
                    vloaitb_id_moi=await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
                }
                let ngay_yc=await this.$refs.thongTinPhieuYeuCau.getNgayYC()
                await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)

            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.HT_DS_KhuyenMai_Combobox()
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
               await this.LayTienTheoKhoanMuc()
            }
        },
        changeLoaiSoMoi(){
            this.hdtb_id_cha = 0
            this.thuebao_id_cha = 0
        },
        onAcceptKhuVuc(khuvuc){
            this.khuvuc_id=khuvuc.khuvuc_id
        },
        async changeTocDoCirWan(tocdo_id){
            //
            await this.LayTienTheoKhoanMuc()
            await this.HT_DS_KhuyenMai_Combobox()
            if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.HienThi_CTK_CTC()
            }
        },
        async changeLoaiTBWanMoi(loaithietbi_id){
            //
            let kieuld_id=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected!=null?Number(this.$refs.thongTinPhieuYeuCau.kieu_yc_selected):0
            if((this.dichvu_vt_selected==DichVuVienThong.MEGAWAN&&kieuld_id==KieuLapDat.CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_CAPQUANG)||
                (this.dichvu_vt_selected==DichVuVienThong.MEGAWAN&&kieuld_id==KieuLapDat.CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_SHDSL)||
                (this.dichvu_vt_selected==DichVuVienThong.MEGAWAN&&kieuld_id==KieuLapDat.CHUYENTU_MEGAWAN_CAPQUANG_SANG_MEGAWAN_ADSL)||
                (this.dichvu_vt_selected==DichVuVienThong.MEGAWAN&&kieuld_id==KieuLapDat.CHUYENTU_MEGAWAN_CAPQUANG_SANG_MEGAWAN_SHDSL)||
                (this.dichvu_vt_selected==DichVuVienThong.MEGAWAN&&kieuld_id==KieuLapDat.CHUYENTU_MEGAWAN_SDHSL_SANG_MEGAWAN_ADSL)||
                (this.dichvu_vt_selected==DichVuVienThong.MEGAWAN&&kieuld_id==KieuLapDat.CHUYENTU_MEGAWAN_SDHSL_SANG_MEGAWAN_CAPQUANG)||
                (this.dichvu_vt_selected==DichVuVienThong.METRONET)){
                    this.ltb_adsl_id=this.$refs.megawan.loaithietbi_moi_selected!=null?this.$refs.megawan.loaithietbi_moi_selected:0
                    await this.LayTienTheoKhoanMuc()
            }
        },
        clickMaTN_ADSL(){
            let kieuld_id=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
            let kieu_ld=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x=>x.kieuld_id==kieuld_id)
            console.log('clickMaTN_IMS', kieu_ld)
            if(!kieu_ld){
                return
            }
            //
            if(kieu_ld.kieu=='1'||kieu_ld.kieu=='5'){
                if(this.hdkh_id>0){
                    var dulieu={}
                    dulieu.hdkh_id = this.hdkh_id
                    dulieu.vkhachhang_id = this.khachhang_id
                    dulieu.vkieuld_id = kieuld_id
                    
                    this.$refs.bangRongCoDinh.showDSTBLapKem(dulieu)
                }else{
                    if(kieu_ld.kieu=='1'){
                        this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !')
                        return
                    }
                    if(kieu_ld.kieu=='5'){
                        var dulieu={}
                        dulieu.hdkh_id = this.hdkh_id
                        dulieu.vkhachhang_id = this.khachhang_id
                        dulieu.vkieuld_id = kieuld_id
                        dulieu.kieu=5
                        this.$refs.bangRongCoDinh.showDSTBLapKem(dulieu)
                    }
                }
            }else if(kieu_ld.kieu=='2'){
                //Lắp mới trên đường có sẵn
                if(this.khachhang_id>0){
                    var dulieu={}
                    dulieu.khachhang_id = this.khachhang_id
                    dulieu.kieuld_id = kieuld_id
                    dulieu.loaitb_id = this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected!=null?this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected:0
                    dulieu.dichvuvt_id = this.dichvu_vt_selected
                    this.$refs.bangRongCoDinh.showDSTBCungDoiCap(dulieu)
                }else{
                    this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
                    return
                }
            }

        },
        async clickTTTC_ADSL(){
            if(this.$refs.thongTinPhieuYeuCau.ds_muccuoc_tb.length==0||this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected==null){
                this.$toast.error('Chưa có mức cước thuê bao. Bạn hãy kiểm tra lại!')
                return
            }
            //
            this.loading(true)
            var cuoc_tb=await this.fn_map_muccuoc_tb(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected, 1, '-1')
            var cuockhoan= await this.fn_map_muccuoc_tb(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected, 2, '-1')
            var cuoc_tg= await this.fn_map_muccuoc_tb(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected, 3, '-1')
            var cuoc_ll= await this.fn_map_muccuoc_tb(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected, 4, '-1')
            if(cuoc_tb=='-1'){
                cuoc_tb='0'
            }
            if(cuockhoan=='-1'){
                cuockhoan='0'
            }
            if(cuoc_tg=='-1'){
                cuoc_tg='0'
            }
            if(cuoc_ll=='-1'){
                cuoc_ll='0'
            }
            this.loading(false)
            //tạo dữ liệu
            let dulieu = {
                cuoc_tb: cuoc_tb,
                cuoc_tg: cuoc_tg,
                cuockhoan: cuockhoan,
                cuoc_ll: cuoc_ll,
                muccuoctb_id: this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected!=null?this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected:0,
                mienip: null,
                loaitb_id: this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected,
                thongtintc: this.$refs.bangRongCoDinh.input_tttc,
            }
            console.log('clickTTTC_ADSL', dulieu)
            this.$refs.bangRongCoDinh.showThongTinTC(dulieu)
        },
        clickMaTN_IMS(){
            let kieuld_id=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
            let kieu_ld=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x=>x.kieuld_id==kieuld_id)
            console.log('clickMaTN_IMS', kieu_ld)
            if(!kieu_ld){
                return
            }

            if(kieu_ld.kieu=='2'){
                //Lắp mới trên đường có sẵn
                if(this.khachhang_id>0){
                    var dulieu={}
                    dulieu.khachhang_id = this.khachhang_id
                    dulieu.kieuld_id = kieuld_id
                    dulieu.loaitb_id = this.$refs.ims.loaihinh_tb_moi_selected!=null?this.$refs.ims.loaihinh_tb_moi_selected:0
                    dulieu.dichvuvt_id = this.dichvu_vt_selected
                    this.$refs.ims.showDSTBCungDoiCap(dulieu)

                }else{
                    this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
                    return
                }
            }else if(kieu_ld.kieu=='1'){
                //Lắp kèm
                if(this.hdkh_id>0){
                    var dulieu={}
                    dulieu.hdkh_id = this.hdkh_id
                    dulieu.vkhachhang_id = this.khachhang_id
                    dulieu.vkieuld_id = kieuld_id
                    
                    this.$refs.ims.showDSTBLapKem(dulieu)
                }else{
                    //Kiểm tra xem có thuê bao mega đang thực hiện chuyển đổi không
                    this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !')
                    return
                }

            }
            
        },
        async changeTocDoKenhTSLMoi(tocdo_id){
            await this.HT_DS_KhuyenMai_Combobox()
            if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.HienThi_CTK_CTC()
            }
        },
        async changeGoiCuocAdsl(tocdo_id){
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                this.loading(true)
                //this.$refs.bangRongCoDinh.input_tttc=''
                //Hiển thị tốc độ thực adsl
                let ds_tocdo=await this.lay_tocdo_thuc_adsl_theo_tocdo_id(tocdo_id)
                this.$refs.bangRongCoDinh.ds_tocdo_thuc_moi=ds_tocdo
                this.$refs.bangRongCoDinh.tocdo_thuc_selected=tocdo_id

                var vtrangbi_id=0
                var vloaitb_id_moi=0
                if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                    vtrangbi_id=await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
                    vloaitb_id_moi=await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
                }
                let ngay_yc=await this.$refs.thongTinPhieuYeuCau.getNgayYC()
                await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)
            
                await this.HT_DS_KhuyenMai_Combobox()
                await this.LayTienTheoKhoanMuc()
                this.loading(false)

            }
        },
        async Lay_DS_MucCuoc_TB_V2(khuld_id, apld_id, phold_id, phuongld_id, loaitb_id, doituong_id, ngay, trangbi_id){
            var vtocdo_id=0
            var vmuccuoc_id=0 //2 Lưu lượng

            if(this.loaihinhtbcu_id==LoaiHinhTB.INTERNET_FTTH
                ||this.loaihinhtbcu_id==LoaiHinhTB.WIFI_FIBER
                ||this.loaihinhtbcu_id==LoaiHinhTB.INTERNET_ADSL){
                    //cboGoiCuocAdsl.EditValue: tốc độ mới
                    vtocdo_id=Number(await this.$refs.bangRongCoDinh.getGoiCuocADSL())
                    vmuccuoc_id=Number(await this.$refs.bangRongCoDinh.getMucCuocMoi())
            }
            if(this.loaihinhtbcu_id==LoaiHinhTB.INTERNET_MYTV){
                vtocdo_id=Number(await this.$refs.bangRongCoDinh.getGoiCuocADSL())
            }

            let ds=await this.lay_ds_mucuoc_tb_v2({
                khuld_id: khuld_id,
                apld_id: apld_id,
                phold_id: phold_id,
                phuongld_id: phuongld_id,
                loaitb_id: loaitb_id,
                doituong_id: doituong_id,
                tocdo_id: vtocdo_id,
                muccuoc_id: vmuccuoc_id,
                ngay:ngay,
                trangbi_id: trangbi_id
            })

            await this.$refs.thongTinPhieuYeuCau.DS_MucCuoc_TB_V2(ds)
        },
        async HienThi_CTK_CTC(){
            var int_loaitb_id = 0
            var int_tocdo_id = 0
            var int_doituong_id = 0
            var int_loaikenh_id = 0
            int_doituong_id = this.doituong_id
            if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                int_loaitb_id=this.$refs.kenhThuRieng.loaihinh_tb_moi_selected!=null?this.$refs.kenhThuRieng.loaihinh_tb_moi_selected:0
                int_tocdo_id=this.$refs.kenhThuRieng.tocdo_moi_selected!=null?this.$refs.kenhThuRieng.tocdo_moi_selected:0
                int_loaikenh_id=this.$refs.kenhThuRieng.loaikenh_moi_selected!=null?this.$refs.kenhThuRieng.loaikenh_moi_selected:0
            }
            if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                int_loaitb_id=this.$refs.megawan.loaihinh_tb_moi_selected!=null?this.$refs.megawan.loaihinh_tb_moi_selected:0
                int_tocdo_id=this.$refs.megawan.tocdo_cir_moi_selected!=null?this.$refs.megawan.tocdo_cir_moi_selected:0
                int_loaikenh_id=this.$refs.megawan.loaikenh_moi_selected!=null?this.$refs.megawan.loaikenh_moi_selected:0
            }
            let ds_ctk_ctc=await this.sp_lay_cuoctk_cuoctc({
                vloaitb_id: int_loaitb_id,
                vtocdo_id: int_tocdo_id,
                vdoituong_id: int_doituong_id,
                vloaikenh_id: int_loaikenh_id,
                vkieu: 1
            })

            if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                if(ds_ctk_ctc.length>0){
                    this.$refs.megawan.input_cuoc_tk_moi=ds_ctk_ctc[0].cuoc_tk!=null?ds_ctk_ctc[0].cuoc_tk.toString():'0'
                    this.$refs.megawan.input_cuoc_tc_moi=ds_ctk_ctc[0].cuoc_tc!=null?ds_ctk_ctc[0].cuoc_tc.toString():'0'
                }else{
                    this.$refs.megawan.input_cuoc_tk_moi='0'
                    this.$refs.megawan.input_cuoc_tc_moi='0'
                }
            }

            if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                if(ds_ctk_ctc.length>0){
                    this.$refs.kenhThuRieng.input_cuoc_tk_moi=ds_ctk_ctc[0].cuoc_tk!=null?ds_ctk_ctc[0].cuoc_tk.toString():'0'
                    this.$refs.kenhThuRieng.input_cuoc_tc_moi=ds_ctk_ctc[0].cuoc_tc!=null?ds_ctk_ctc[0].cuoc_tc.toString():'0'
                }else{
                    this.$refs.kenhThuRieng.input_cuoc_tk_moi='0'
                    this.$refs.kenhThuRieng.input_cuoc_tc_moi='0'
                }
            }

        },
        async changeMucCuocTB(mucuoctb_id){
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                let result = await this.fn_map_muccuoc_tb(mucuoctb_id, 1,'-1')
                if(result=='-1'){
                    this.$refs.coDinh.input_cuoctb_moi='0'
                }else{
                    this.$refs.coDinh.input_cuoctb_moi=result.toString()
                }
            }
        },
        async changeMucCuoc(muccuoc_id){
            await this.HT_DS_KhuyenMai_Combobox()
        },
        async changeKieuYC(kieuyc_id){
            console.log('changeKieuYC', kieuyc_id)
            this.kieuld_id=Number(kieuyc_id)
            //handle
            let kieu_ld=this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x=>x.kieuld_id==kieuyc_id)
            var kieu=''
            if(kieu_ld!=undefined){
                kieu=kieu_ld.kieu
            }
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                this.$refs.bangRongCoDinh.input_matn=''

                if(kieu=='1'||kieu=='2'||kieu=='5'){
                    this.$refs.bangRongCoDinh.enabledMaTN=true
                }else{
                    this.$refs.bangRongCoDinh.enabledMaTN=false
                }
            }
            
            await this.LayTienTheoKhoanMuc()
            //
            //sp_ht_lhtb_moi_combobox
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL&&this.loaihinhtbcu_id==LoaiHinhTB.INTERNET_MYTV){
                let dsLoaiHinhTBMoi=[
                    {
                        LOAITB_ID:LoaiHinhTB.INTERNET_MYTV,
                        LOAIHINH_TB:'MyTV'
                    }
                ]
                await this.$refs.bangRongCoDinh.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, LoaiHinhTB.INTERNET_MYTV)
            }else{
                let resultLoaiHinh=await this.sp_ht_lhtb_moi_combobox(kieuyc_id)
                let dsLoaiHinhTBMoi=resultLoaiHinh.map(x=>{
                    return {
                        LOAITB_ID:Number(x.loaitb_id),
                        LOAIHINH_TB:x.loaihinh_tb
                    }
                })
                if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                    await this.$refs.coDinh.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length>0?dsLoaiHinhTBMoi[0].LOAITB_ID:null)
                }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                     await this.$refs.diDong.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length>0?dsLoaiHinhTBMoi[0].LOAITB_ID:null)
                }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                    //băng rộng cố định
                    await this.$refs.bangRongCoDinh.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length>0?dsLoaiHinhTBMoi[0].LOAITB_ID:null)
                   
                }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                    await this.$refs.ims.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length>0?dsLoaiHinhTBMoi[0].LOAITB_ID:null)
                }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                    await this.$refs.kenhThuRieng.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length>0?dsLoaiHinhTBMoi[0].LOAITB_ID:null)
                }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                    await this.$refs.megawan.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length>0?dsLoaiHinhTBMoi[0].LOAITB_ID:null)
                }
            }
            //
            // Hàm HT_ComboBox_DV để làm gì chưa rõ sau này tính sau
            await this.HT_ComboBox_DV()

            let kieuld_id=await this.$refs.thongTinPhieuYeuCau.getKieuID()
            await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)

            
        },
        async HT_ComboBox_DV(){
            //Hiện tại chưa xử lý, chưa có tác dụng

        },
        async changeLoaiHinhTBCu(loaihinhtb_id){
            this.loaihinhtbcu_id=loaihinhtb_id
            //
            var ds_kieu_yc=[]
            if(loaihinhtb_id==LoaiHinhTB.FSECURE){
                ds_kieu_yc=await this.lay_ds_kieu_ld_lhtb(LoaiHopDong.DAT_MOI, loaihinhtb_id, 1)
            }else{
                ds_kieu_yc=await this.lay_ds_kieu_ld_lhtb(LoaiHopDong.CHUYENDOI_LH, loaihinhtb_id, 1)
            }

            await this.$refs.thongTinPhieuYeuCau.setKieuLD(ds_kieu_yc)
            if(ds_kieu_yc.length>0){
                await this.changeKieuYC(ds_kieu_yc[0].kieuld_id)
            }else{
                await this.changeKieuYC(0)
            }
            console.log('changeLoaiHinhTBCu', ds_kieu_yc)
            
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.changeLoaiHinhTBCu(loaihinhtb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                await this.$refs.diDong.changeLoaiHinhTBCu(loaihinhtb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                await this.$refs.bangRongCoDinh.changeLoaiHinhTBCu(loaihinhtb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.$refs.ims.changeLoaiHinhTBCu(loaihinhtb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.$refs.kenhThuRieng.changeLoaiHinhTBCu(loaihinhtb_id)
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.$refs.megawan.changeLoaiHinhTBCu(loaihinhtb_id)
            }

            // if(this.dichvu_vt_selected==DichVuVienThong.ADSL&&this.loaihinhtbcu_id==LoaiHinhTB.INTERNET_ADSL){
            //     this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected=LoaiHinhTB.INTERNET_FTTH
            // }
            // if(this.dichvu_vt_selected==DichVuVienThong.ADSL&&(this.loaihinhtbcu_id==LoaiHinhTB.INTERNET_FTTH||this.loaihinhtbcu_id == LoaiHinhTB.WIFI_FIBER)){
            //     this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected=LoaiHinhTB.INTERNET_ADSL
            // }
            //Dịch vụ: An toàn bảo mật thông tin chung layout ADSL
            if(this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT&&this.loaihinhtbcu_id==LoaiHinhTB.FSECURE){
                this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected=LoaiHinhTB.FSECURE
                await this.HienThiControlFSecure(true)
            }else{
                await this.HienThiControlFSecure(false)
            }

            let kieuld_id=await this.$refs.thongTinPhieuYeuCau.getKieuID()
            await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
        },
        async changeDichVu(dichvuvt_id){
            this.dichvu_vt_selected=dichvuvt_id
            this.dichvuvt_id=dichvuvt_id
            await this.$refs.thongTinKhachHang.changeDichVu(dichvuvt_id)
            //init layout tương ứng
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                setTimeout(async()=>{
                    this.loading(true)
                    await this.changeLoaiHinhTBCu(this.$refs.thongTinPhieuYeuCau.loaihinh_selected)
                    await this.$refs.coDinh.initDuLieu(this.ds_loaihinh_tb)
                    await this.$refs.coDinh.changeDichVu(dichvuvt_id)
                    await this.$refs.coDinh.visibleToolStripMenuAndControl(this.iKieu==0)
                    this.loading(false)
                },500)
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                    //init
                    // this.loading(true)
                    // await this.$refs.diDong.initDuLieu(this.ds_loaihinh_tb)
                    // await this.changeLoaiHinhTBCu(this.$refs.thongTinPhieuYeuCau.loaihinh_selected)
                    // await this.$refs.diDong.changeDichVu(dichvuvt_id)
                    // this.loading(false)
                    // cboLoaiHinhTBMoi.SelectedValue = LoaiHinhTB.DIDONGTRATRUOC;
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                setTimeout(async ()=>{
                    this.loading(true)
                    await this.changeLoaiHinhTBCu(this.$refs.thongTinPhieuYeuCau.loaihinh_selected)
                    await this.$refs.bangRongCoDinh.initDuLieu(this.ds_loaihinh_tb)
                    await this.$refs.bangRongCoDinh.changeDichVu(dichvuvt_id)
                    if(this.thamso.nhap_tocdo_thuc_adsl==0){
                        this.$refs.bangRongCoDinh.enabledCheckTocDoThuc=false
                    }else{
                        this.$refs.bangRongCoDinh.enabledCheckTocDoThuc=true
                    }
                    this.$refs.bangRongCoDinh.checkTocDoThuc=false
                    await this.$refs.bangRongCoDinh.visibleToolStripMenuAndControl(this.iKieu==0)
                    this.loading(false)
                },500)
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                setTimeout(async ()=>{
                    this.loading(true)
                    await this.changeLoaiHinhTBCu(this.$refs.thongTinPhieuYeuCau.loaihinh_selected)
                    await this.$refs.ims.initDuLieu(this.ds_loaihinh_tb)
                    await this.$refs.ims.changeDichVu(dichvuvt_id)
                    await this.$refs.ims.visibleToolStripMenuAndControl(this.iKieu==0)
                    this.loading(false)
                },500)
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                setTimeout(async ()=>{
                    this.loading(true)
                    await this.changeLoaiHinhTBCu(this.$refs.thongTinPhieuYeuCau.loaihinh_selected)
                    await this.$refs.kenhThuRieng.initDuLieu(this.ds_loaihinh_tb)
                    await this.$refs.kenhThuRieng.changeDichVu(dichvuvt_id)
                    await this.$refs.kenhThuRieng.visibleToolStripMenuAndControl(this.iKieu==0)
                    this.loading(false)
                },500)
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                setTimeout(async ()=>{
                    this.loading(true)
                    await this.changeLoaiHinhTBCu(this.$refs.thongTinPhieuYeuCau.loaihinh_selected)
                    await this.$refs.megawan.initDuLieu(this.ds_loaihinh_tb, this.dichvu_vt_selected)
                    await this.$refs.megawan.changeDichVu(dichvuvt_id)
                    await this.$refs.megawan.visibleToolStripMenuAndControl(this.iKieu==0)
                    this.loading(false)
                },500)
            }
        },
        async btnChiTietKMClick(){
            var vmuccuoctn_id=0
            var vtocdotn_id=0
            var dulieu={}
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                vtocdotn_id=this.$refs.bangRongCoDinh.tocdo_moi_selected!=null?this.$refs.bangRongCoDinh.tocdo_moi_selected:0
                if(this.loaihinhtb_id==LoaiHinhTB.INTERNET_ADSL||this.loaihinhtb_id==LoaiHinhTB.INTERNET_FTTH||this.loaihinhtb_id==LoaiHinhTB.WIFI_FIBER){
                    vmuccuoctn_id=this.$refs.bangRongCoDinh.muccuoc_moi_selected!=null?this.$refs.bangRongCoDinh.muccuoc_moi_selected:0
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                //tocdo_cir_moi_selected là cboTocDo
                vtocdotn_id=this.$refs.megawan.tocdo_cir_moi_selected!=null?this.$refs.megawan.tocdo_cir_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                vtocdotn_id=this.$refs.kenhThuRieng.tocdo_moi_selected!=null?this.$refs.kenhThuRieng.tocdo_moi_selected:0
            }

            dulieu.vmuccuoctn_id=vmuccuoctn_id
            dulieu.vtocdotn_id=vtocdotn_id

            if(this.$refs.thongTinPhieuYeuCau.khuyenmai.checkKhuyenMai){
                if(this.$refs.thongTinPhieuYeuCau.khuyenmai.ds_khuyenmai.length>0){
                    //kiemtra_thoihan_khuyenmai
                    let khuyenmai_id=this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected!=0?this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected:0
                    let result = await this.kiemtra_thoihan_khuyenmai(khuyenmai_id, moment(new Date()).format('DD/MM/YYYY'))
                    if(result!=null&&result.result==0){
                        let ngay_kt_km=await this.fn_map_khuyenmai(khuyenmai_id, 1, '')
                        let khuyenmai=this.$refs.thongTinPhieuYeuCau.khuyenmai.ds_khuyenmai.find(x=>x.khuyenmai_id==khuyenmai_id)
                        let tenKm=khuyenmai!=undefined?khuyenmai.ten_km:''
                        let message='Khuyến mại ' + tenKm + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?'
                        
                        let resultConfirm = await this.confirm(message,'Thông báo')
                        if(resultConfirm==0){
                            this.dtienkmld = 0
                            this.dvatkmld = 0
                            this.tyle_kmld = 0
                            this.tong_kmld = 0
                            this.tien_datcoc = 0
                            await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
                            return
                        }
                    }
                    //show dialog
                    await this.$refs.thongTinPhieuYeuCau.openDialogChiTietDKKhuyenMai(dulieu)

                }
            }else{
                dtienkmld = 0
                dvatkmld = 0
                tyle_kmld = 0
                tong_kmld = 0
                tien_datcoc = 0
            }
        },
        async btnChiTietTCClick(){
            var vmuccuoctn_id=0
            var vtocdotn_id=0
            var dulieu={}
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                vtocdotn_id=this.$refs.bangRongCoDinh.tocdo_moi_selected!=null?this.$refs.bangRongCoDinh.tocdo_moi_selected:0
                if(this.loaihinhtb_id==LoaiHinhTB.INTERNET_ADSL||this.loaihinhtb_id==LoaiHinhTB.INTERNET_FTTH||this.loaihinhtb_id==LoaiHinhTB.WIFI_FIBER){
                    vmuccuoctn_id=this.$refs.bangRongCoDinh.muccuoc_moi_selected!=null?this.$refs.bangRongCoDinh.muccuoc_moi_selected:0
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                //tocdo_cir_moi_selected là cboTocDo
                vtocdotn_id=this.$refs.megawan.tocdo_cir_moi_selected!=null?this.$refs.megawan.tocdo_cir_moi_selected:0
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                vtocdotn_id=this.$refs.kenhThuRieng.tocdo_moi_selected!=null?this.$refs.kenhThuRieng.tocdo_moi_selected:0
            }

            dulieu.vmuccuoctn_id=vmuccuoctn_id
            dulieu.vtocdotn_id=vtocdotn_id
            //
            if(this.$refs.thongTinPhieuYeuCau.tratruoc.checkTraTruoc){
                if(this.$refs.thongTinPhieuYeuCau.tratruoc.ds_tratruoc.length>0){
                    let khuyenmai_id_tt = this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected!=0?this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected:0
                    let result = await this.kiemtra_thoihan_khuyenmai(khuyenmai_id_tt, moment(new Date()).format('DD/MM/YYYY'))
                    if(result!=null&&result.result==0){
                        let ngay_kt_km=await this.fn_map_khuyenmai(khuyenmai_id_tt, 1, '')
                        let tratruoc=this.$refs.thongTinPhieuYeuCau.tratruoc.ds_tratruoc.find(x=>x.khuyenmai_id==khuyenmai_id_tt)
                        let tenKm=tratruoc!=undefined?tratruoc.ten_km:''
                        let message='Khuyến mại ' + tenKm + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?'
                        
                        let resultConfirm = await this.confirm(message,'Thông báo')
                        if(resultConfirm==0){
                            this.tyle_kmld_tt = 0
                            this.tong_kmld_tt = 0
                            this.dtiendatcoc_tt = 0
                            this.dvatdatcoc_tt = 0
                            this.dtienkmdc = 0
                            this.dvatkmdc = 0
                            await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dvatkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
                            return
                        }
                    }
                    //show dialog
                    await this.$refs.thongTinPhieuYeuCau.openDialogChiTietTraTruoc(dulieu)
                }else{
                    let kieuld_id=await this.$refs.thongTinPhieuYeuCau.getKieuID()
                    await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
                }
            }else{
                this.tyle_kmld_tt = 0
                this.tong_kmld_tt = 0
                this.dtiendatcoc_tt = 0
                this.dvatdatcoc_tt = 0
                this.dtienkmdc = 0
                this.dvatkmdc = 0

                let kieuld_id=await this.$refs.thongTinPhieuYeuCau.getKieuID()
                await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
            }
                

        },
        async formCloseChiTietKM(data){
            console.log('formCloseChiTietKM', data)
            if(data.loai==1){
                //khuyến mại
                await this.LaySothang_SD()
                this.dsKM_HDTB=data.ds.dsKM_HDTB
                this.dtienkmld=data.ds.tien_kmld
                this.dvatkmld=data.ds.vat_kmld
                this.tyle_kmld=data.ds.tyle_kmld
                this.tien_datcoc=data.ds.tien_datcoc

                //Kiểm tra xem nếu không có tiền khuyến mại thì ưu tiên khuyến mại theo tỷ lệ
                if(this.dtienkmld==0){
                    if(this.tyle_kmld>0){
                        this.dtienkmld = Math.round(this.dtienld*this.tyle_kmld / 100);
                        this.dvatkmld = (this.dtienld + this.dvatld)*this.tyle_kmld / 100 - this.dtienkmld;
                    }
                }
                this.tong_kmld=this.dtienkmld+this.dvatkmld
                //Hiếu bổ sung hàm kiểm tra xem user đã chọn chi tiết khuyến mại nào chưa - 23.09.2010
                let khuyenmai_id=this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected!=0?this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected:0
                if(this.dsKM_HDTB.length>0){
                    const index=this.dsKM_HDTB.findIndex(x=>x.KHUYENMAI_ID==khuyenmai_id)
                    if(index>-1){
                        //Lấy tiền khuyến mại theo khuyến mại id
                        await this.HT_ThietBi()
                    }
                }else{
                    this.dtienkmld = 0
                    this.dvatkmld = 0
                    this.tyle_kmld = 0
                    this.tong_kmld = 0
                    this.tien_datcoc = 0
                }
                await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)

            }else if(data.loai==2){
                //trả trước
                await this.LaySothang_SD()

                this.dsDC_HDTB=data.ds.dsDC_HDTB
                this.dtienkmdc=data.ds.tien_kmld_tt
                this.dvatkmdc=data.ds.vat_kmld_tt
                this.tyle_kmld_tt=data.ds.tyle_kmld_tt
                this.dtiendatcoc_tt=Math.round(data.ds.tien_datcoc_tt* 10/11)
                this.dvatdatcoc_tt=data.ds.tien_datcoc_tt-this.dtiendatcoc_tt
                //Kiểm tra xem nếu không có tiền khuyến mại thì ưu tiên khuyến mại theo tỷ lệ
                if(this.dtienkmdc==0){
                    if(this.tyle_kmld_tt>0){
                        this.dtienkmdc = Math.round(this.dtienld*this.tyle_kmld_tt/100);
                        this.dvatkmdc = (this.dtienld + this.dvatld)*this.tyle_kmld_tt/100 - this.dtienkmdc
                    }
                }
                this.tong_kmld_tt = this.dtienkmdc + this.dvatkmdc

                if(this.dsDC_HDTB.length==0){
                    this.tyle_kmld_tt = 0
                    this.tong_kmld_tt = 0
                    this.dtiendatcoc_tt = 0
                    this.dvatdatcoc_tt = 0
                    this.dtienkmdc = 0
                    this.dvatkmdc = 0
                }
                await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
                
                let kieuld_id=await this.$refs.thongTinPhieuYeuCau.getKieuID()
                await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)

            }
        },
        clickDiemDauTSL(){
            if(this.tinh_dau==0){
                this.$toast.error('Chưa có thuê bao')
                return
            }
            this.loai_diem_tsl=1
            let i_loaitb_id = this.$refs.thongTinPhieuYeuCau.loaihinh_selected
            let kieuld_id=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
            this.$refs.thongSoTSL.openDialog({tinhthicong_id: this.tinh_dau}, i_loaitb_id, i_loaitb_id, kieuld_id,
            this.thongso_ban, this.thongso_tc_dau, LoaiHopDong.CHUYENDOI_LH, this.kenh_trang)
            
        },
        clickDiemCuoiTSL(){
            if(this.tinh_cuoi==0){
                this.$toast.error('Chưa có thuê bao')
                return
            }
            this.loai_diem_tsl=2
            let i_loaitb_id = this.$refs.thongTinPhieuYeuCau.loaihinh_selected
            let kieuld_id=this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
            this.$refs.thongSoTSL.openDialog({tinhthicong_id: this.tinh_cuoi}, i_loaitb_id, i_loaitb_id, kieuld_id,
            this.thongso_ban, this.thongso_tc_cuoi, LoaiHopDong.CHUYENDOI_LH, this.kenh_trang)
            
        },
        closefrmThongSoTSL(data){
            console.log(data)
            if(this.loai_diem_tsl==1){
                Object.assign(this.thongso_ban, data.thongso_Ban)
                Object.assign(this.thongso_tc_dau, data.thongso_TC)
                this.Kieuld_tc_id = this.thongso_tc_dau.kieuld_id
                this.$refs.kenhThuRieng.tocdo_moi_selected=this.thongso_ban.tocdo_id
            }else{
                Object.assign(this.thongso_ban, data.thongso_Ban)
                Object.assign(this.thongso_tc_cuoi, data.thongso_TC)
                this.Kieuld_tc_id_tsl = this.thongso_tc_cuoi.kieuld_id
                this.$refs.kenhThuRieng.tocdo_moi_selected=this.thongso_ban.tocdo_id
            }
            
        },
        clickThongSoTC(){
            let loaitb_id=this.$refs.thongTinPhieuYeuCau.loaihinh_selected?this.$refs.thongTinPhieuYeuCau.loaihinh_selected:0
            let dichvu_vt=this.$refs.thongTinPhieuYeuCau.dichvu_selected?this.$refs.thongTinPhieuYeuCau.dichvu_selected:0
            if(dichvu_vt==DichVuVienThong.CO_DINH||dichvu_vt==DichVuVienThong.IMS){
                var loaitb_id_moi=0
                if(dichvu_vt==DichVuVienThong.CO_DINH){
                    loaitb_id_moi=this.$refs.coDinh.loaihinh_tb_moi_selected?this.$refs.coDinh.loaihinh_tb_moi_selected:0
                }else if(dichvu_vt==DichVuVienThong.IMS){
                    loaitb_id_moi=this.$refs.ims.loaihinh_tb_moi_selected?this.$refs.ims.loaihinh_tb_moi_selected:0
                }
                console.log('clickThongSoTC', this.tinh_thicong)
                //tinh_tc, ploaitb_ld, ploaitb, thuebao_id_ban, thuebao_id_thicong, loaihd_id
                this.$refs.thongSoCoDinhModal.showModal(this.tinh_thicong, loaitb_id, loaitb_id_moi, 0, 0, LoaiHopDong.CHUYENDOI_LH)
            }else if(dichvu_vt==DichVuVienThong.ADSL){
                this.$refs.tocDoMucCuocADSLModal.showModal(this.tinh_thicong, loaitb_id, this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected?this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected:0, 0, 0, LoaiHopDong.CHUYENDOI_LH)
            }else if(dichvu_vt==DichVuVienThong.MEGAWAN||dichvu_vt==DichVuVienThong.METRONET||dichvu_vt==DichVuVienThong.NOIBO_DADIEM){
                //chưa có khảo sát frmThongSoWan
                //this.$refs.thongSoWanModal.showModal()
                console.log('thongso_ban', this.thongso_ban)
                console.log('thongso_tc', this.thongso_tc)

                this.$refs.ref_frmThongSoWan.ThongsoBan = this.thongso_ban
                this.$refs.ref_frmThongSoWan.ThongsoTc = this.thongso_tc
                this.$refs.ref_frmThongSoWan.khachhang_id = this.khachhang_tc_id
                this.$refs.ref_frmThongSoWan.hdkh_id = this.hdkh_id
                this.$refs.ref_frmThongSoWan.openDialog1({tinhthicong_id: this.tinh_thicong}, this.$refs.megawan.loaihinh_tb_moi_selected, loaitb_id, LoaiHopDong.CHUYENDOI_LH, this.dichvu_vt_selected, this.thongso_ban, this.thongso_tc)


            }
        },
        acceptThongSoCoDinh(data){
            Object.assign(this.thongso_ban, data.thongso_ban)
            Object.assign(this.thongso_tc, data.thongso_tc)
            console.log('thongso_ban', this.thongso_ban)
            console.log('thongso_tc', this.thongso_tc)
            this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected=this.thongso_ban.muccuoctb_id
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                this.$refs.coDinh.trangbi_moi_selected=this.thongso_ban.trangbi_id
                this.$refs.coDinh.changeTrangBiMoi()
                this.$refs.coDinh.doituong_moi_selected=this.thongso_ban.doituong_id
                this.$refs.coDinh.changeDoiTuongMoi()
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                this.$refs.ims.trangbi_moi_selected=this.thongso_ban.trangbi_id
                this.$refs.ims.changeTrangBiMoi()
                this.$refs.ims.doituong_moi_selected=this.thongso_ban.doituong_id
                this.$refs.ims.changeDoiTuongMoi()
            }
        },
        async acceptTocDoMucCuocADSL(data){
            Object.assign(this.thongso_ban, data.thongso_ban)
            Object.assign(this.thongso_tc, data.thongso_tc)
            console.log('thongso_ban', this.thongso_ban)
            console.log('thongso_tc', this.thongso_tc)


            this.$refs.bangRongCoDinh.disableGoiCuocADSLMoi=true
            this.$refs.bangRongCoDinh.disabledTrangBi=true
            this.$refs.bangRongCoDinh.disabledMucCuoc=true
            this.$refs.bangRongCoDinh.disabledTocDoThucMoi=true
            this.$refs.bangRongCoDinh.enableDoiTuongMoi=false

            this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected=this.thongso_ban.muccuoctb_id

            this.$refs.bangRongCoDinh.tocdo_moi_selected=this.thongso_ban.tocdo_id
            await this.changeGoiCuocAdsl(this.thongso_ban.tocdo_id)
            this.$refs.bangRongCoDinh.tocdo_thuc_selected=this.thongso_ban.tocdothuc_id

            this.$refs.bangRongCoDinh.trangbi_moi_selected=this.thongso_ban.trangbi_id
            this.$refs.bangRongCoDinh.changeTrangBiMoi()
            this.$refs.bangRongCoDinh.doituong_moi_selected=this.thongso_ban.doituong_id
            this.$refs.bangRongCoDinh.changeDoiTuongMoi()
        },
        async closeHienThiChonThongSo(data){
            Object.assign(this.thongso_ban, data.ThongsoBan)
            Object.assign(this.thongso_tc, data.ThongsoTc)
            console.log('thongso_ban', this.thongso_ban)
            console.log('thongso_tc', this.thongso_tc)
            if(this.thongso_ban.tocdo_id>0){
                this.$refs.megawan.tocdo_cir_moi_selected=this.thongso_ban.tocdo_id
                await this.changeTocDoCirWan(this.thongso_ban.tocdo_id)
            }
            if(this.thongso_ban.tocdo_pir_id > 0){
                this.$refs.megawan.checkTocDoPIRMoi=true
                this.$refs.megawan.tocdo_cir_moi_selected=this.thongso_ban.tocdo_pir_id
            }else{
                this.$refs.megawan.checkTocDoPIRMoi=false
            }
            if(this.thongso_ban.chuquan_id>0){
                this.$refs.megawan.chuquan_moi_selected=this.thongso_ban.chuquan_id
            }
            if(this.thongso_ban.loaikenh_id > 0){
                this.$refs.megawan.loaikenh_moi_selected=this.thongso_ban.loaikenh_id
            }
            if(this.thongso_ban.loaimd_id > 0){
                this.$refs.megawan.modem_moi_selected=this.thongso_ban.loaimd_id
            }
            if(this.thongso_ban.thoihan_id > 0){
                this.$refs.megawan.thoihan_moi_selected=this.thongso_ban.thoihan_id
            }
            if(this.thongso_ban.thietbidc_id > 0){
                this.$refs.megawan.loaithietbi_moi_selected=this.thongso_ban.thietbidc_id
            }
            if(this.thongso_ban.trangbi_id > 0){
                this.$refs.megawan.trangbi_moi_selected=this.thongso_ban.trangbi_id
                this.$refs.megawan.changeTrangBiMoi()
            }
            if(this.thongso_ban.doituong_id > 0){
                this.$refs.megawan.doituong_moi_selected=this.thongso_ban.doituong_id
                this.$refs.megawan.changeDoiTuongMoi()
            }
        },
        acceptCTV(data){
            Object.assign(this.ctv, data)
        },
        acceptNguoiGT(data){
            Object.assign(this.nguoi_gt, data)
        },
        onAcceptDiaChiLD(data){
            Object.assign(this.dataDiaChiLD, data)
        },
        onAcceptDiaChiTB(data){
            Object.assign(this.dataDiaChiTB, data)
        },
        changeCheckCTV(){
            if(this.checkCTV){
                this.$refs.chonCongTacVienModal.showModal()
            }
        },
        checkCheckNguoiGT(){
            if(this.checkNguoiGT){
                this.$refs.chonNguoiGTModal.showModal()
            }
        },
        acceptChonSoChinh(data){
            this.loaisochinh=data.LOAI
            if(this.loaisochinh==1){
                this.thuebao_id_cha=data.ID_SOCHINH
            }else{
                this.hdtb_id_cha=data.ID_SOCHINH
            }
        },
        async onEnterMaGD(){
            this.loading(true)
            await this.hopdong_theo_ma_gd()
            this.loading(false)
        },
        async hopdong_theo_ma_gd(){
            let ma_gd=await this.$refs.thongTinPhieuYeuCau.getMaGD()
            if(ma_gd==''){
                return
            }
            if(this.iKieu==1){
                let ds = await this.lay_ds_hopdong_theo_ma_gd({
                    ma_gd: ma_gd,
                    loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                    donvi_id: 0,
                    tthd_id: 0,
                    nhanvien_id: 0,
                    donvi_dl_id: 0
                })
                await this.HienThiTTHopDongKH(ds)

            }else if(this.iKieu==2){
                var idonvi_id=0
                if(this.$root.token.getDonViDuLieuID()!=0){
                    idonvi_id=this.$root.token.getDonViID()
                }

                let ds = await this.lay_ds_hopdong_theo_ma_gd({
                    ma_gd: ma_gd,
                    loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                    donvi_id: idonvi_id,
                    tthd_id: 0,
                    nhanvien_id: 0,
                    donvi_dl_id: 0
                })
                await this.HienThiTTHopDongKH(ds)
            }else {
                let ds = await this.lay_ds_hopdong_theo_ma_gd({
                    ma_gd: ma_gd,
                    loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                    donvi_id: this.$root.token.getDonViID(),
                    tthd_id: TrangThaiHD.MOI,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    donvi_dl_id: 0
                })
                await this.HienThiTTHopDongKH(ds)
            }
            //
            await this.visibleToolStripMenuAndControl(this.iKieu == 0)
        },
        async onEnterMaTB(ma_tb){
            this.vma_tb=ma_tb
            if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                this.tinh_dau=0
                this.tinh_cuoi=0
                this.chi_giao_net=false
                let phanvung_id=this.$root.token.getPhanVungID()
                this.loading(true)
                let dt=await this.lay_tinhtc_id_tsl(this.dichvu_vt_selected, ma_tb, phanvung_id)
                
                if(dt.length>0){
                    this.kenh_trang = false
                    this.tinh_dau=dt.find(x=>x.daucuoi_id==1).tinh_thicong
                    this.tinh_cuoi=dt.find(x=>x.daucuoi_id==2).tinh_thicong
                    this.thuebao_tc_id=dt.find(x=>x.daucuoi_id==1).thuebao_kn_id
                    this.thuebao_tc_id_tsl=dt.find(x=>x.daucuoi_id==2).thuebao_kn_id
                    this.tinh_thicong=0

                }else{
                    this.kenh_trang = true
                    this.loading(false)
                    let rConfirm = await this.confirm('Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công !','Thông báo')
                    if(rConfirm==0){
                        await this.clear()
                        return
                    }
                    this.$refs.chonTinhTSL.showModal()
                    return
                } 
            }else{
                this.tinh_thicong=0
                this.loading(true)
                let result=await this.lay_tinh_theo_matb(ma_tb)
                if(result.length>0){
                    this.tinh_thicong=result[0].tinh_thicong
                }
                if(this.tinh_thicong==0){
                    this.chi_giao_net = false
                    this.kenh_trang = true
                    this.loading(false)
                    let rConfirm = await this.confirm('Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công !','Thông báo')
                    if(rConfirm==0){
                        await this.clear()
                        return
                    }
                    this.$refs.chonTinhModal.showModal()
                    return
                }else{
                    this.kenh_trang = false
                    let dtb=await this.sp_ds_db_thuebao_bancheo_theo_matb(ma_tb)
                    //this.loading(false)
                    if(dtb.length>0&&dtb[0].thuebao_kn_id==-1){
                        this.chi_giao_net = true
                    }else{
                        this.chi_giao_net = false
                    }
                    // if (!kenh_trang)
                    //             thuebao_tc_id = tcbancheo.LAY_THUEBAOID_THICONG_THEO_MATB(tinh, txtMaTB.Text);
                    //         else
                    //             thuebao_tc_id = 0;
                    let resultTB_TC=await this.sp_lay_thuebaoid_thicong_theo_matb({
                        vma_tb: ma_tb,
                        vtinhthicong: this.tinh_thicong
                    })
                    if(resultTB_TC.length>0){
                        this.thuebao_tc_id=resultTB_TC[0].thuebao_id_thicong
                    }else{
                        this.thuebao_tc_id=0
                    }
                }
              
            }
            //
            await this.onEnterMaTB_B2(ma_tb)

        },
        
        async acceptChonTinhTC(tinh_tc){
            this.tinh_thicong=tinh_tc
            await this.onEnterMaTB_B2(this.vma_tb)
         
        },
        async acceptChonTinhTC_TSL(value){
            this.tinh_dau=value.tinh_dau
            this.tinh_cuoi=value.tinh_cuoi
            this.thuebao_tc_id=0
            this.thuebao_tc_id_tsl=0
            this.tinh_thicong=0
            await this.onEnterMaTB_B2(this.vma_tb)
        },
        async onEnterMaTB_B2(ma_tb){
            this.loading(true)
            let ds = await this.lay_ds_hopdong_theo_ma_tb({
                ma_tb: ma_tb,
                in_loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                in_donvi_id: this.$root.token.getDonViID(),
                in_tthd_id: TrangThaiHD.MOI,
                in_nhanvien_id: this.$root.token.getNhanVienID(),
                in_donvi_dl_id: 0,
                in_dichvuvt_id: this.dichvuvt_id,
                in_loaitb_id: 0
            })
            if(ds.length>0){
                await this.HienThiTTHopDongKH(ds)
            }else{
                await this.LoadKM()
                await this.HienThiTT_DanhBa(ma_tb)
            }
            this.loading(false)
        },
        async HienThiTTHopDongKH(ds){
            if(ds.length>0){
                await this.setActiveActions(3)
                let item=ds[0]

                this.hdkh_id=item.hdkh_id
                this.hdkh_cha_id=(item.hdkh_cha_id!=null&&item.hdkh_cha_id!='')?Number(item.hdkh_cha_id):0
                if(item.khachhang_id){
                    this.khachhang_id=(item.khachhang_id!=null&&item.khachhang_id!='')?Number(item.khachhang_id):0
                    this.khachhang_tc_id = await this.fn_lay_khachhangid_thicong(this.khachhang_id, this.tinh_thicong)
                }
                
                await this.$refs.thongTinPhieuYeuCau.HienThiTTHopDongKH(item)
                await this.$refs.thongTinKhachHang.HienThiTTHopDongKH(item)

                if(item.kieuhd_id!=null&&item.kieuhd_id.toString()!=''){
                    this.kieu_pl_selected=item.kieuhd_id
                }else{
                    this.kieu_pl_selected=KIEU_HD.TAI_GD
                }
                console.log('kieu_pl_selected', this.kieu_pl_selected)

                this.dichvuvt_id=item.dichvuvt_id!=null?Number(item.dichvuvt_id):0
                if(this.dichvuvt_id==DichVuVienThong.DI_DONG){
                    this.actions[this.actions.findIndex(x=>x.id=='capnhatdb')].active=true
                }

                let result = await this.fn_lay_tt_ht_hdkh_cdlhtb_bancheo(JSON.stringify({
                    NGUOIGT_ID:item.nhanviengt_id!=null?item.nhanviengt_id:0,
                    DICHVUVT_ID:this.dichvuvt_id,
                    CTV_ID:item.ctv_id!=null?item.ctv_id:0,
                    HDKH_ID:item.hdkh_id!=null?item.hdkh_id:0,
                    HDTB_ID:item.hdtb_id!=null?item.hdtb_id:0
                }))
                if(result!=null&&result.ERROR_CODE==1){
                    let data=result.RESULT
                    //
                    //CTV
                    if(data.CTV!='-1'){
                        this.checkCTV=true
                        this.ctv.ctv_id=item.ctv_id
                        this.ctv.ten_ctv=data.CTV
                    }else{
                        this.checkCTV=false
                        this.ctv={
                            ctv_id:null,
                            ten_ctv:'',
                            ma_ctv:'',
                            loainv_id:-1,
                            donvi_id:-1
                        }
                    }
                    //Người GT
                    if(data.NGUOIGT!='-1'){
                        this.checkNguoiGT=true
                        this.nguoi_gt.nguoi_gt_id=item.nhanviengt_id
                        this.nguoi_gt.ten_nguoi_gt=data.NGUOIGT
                    }else{
                        this.checkNguoiGT=false
                        this.nguoi_gt={
                            nguoi_gt_id:null,
                            ten_nguoi_gt:'',
                            ma_nguoi_gt:'',
                            loainv_id:-1,
                            donvi_id:-1
                        }
                    }
                    //hiện thị ds hợp đồng thuê bao lên lưới
                    this.dsDBTB=[]
                    var dsDU=[].concat(data.TT_DSHDTB)
                    for(var i = 0; i < dsDU.length; i++){ 
                        for (var key in dsDU[i]) {
                            if(key.toLowerCase() !== key){
                                dsDU[i][key.toLowerCase()] = dsDU[i][key];
                                delete dsDU[i][key];
                            }
                        }
                    }
                    this.dsDBTB=dsDU



                    let dsHDTB=data.TT_DSHDTB

                    //Hiển thị địa chỉ
                    let dsDiaChi=data.TT_DIACHI
                    await this.HienThiTTDiaChi(dsDiaChi)


                    await this.HT_TT(dsHDTB)
                    



                }else if(result!=null&&result.ERROR_CODE==0){
                    this.$toast.error(result.MESSAGE)
                }

                console.log('result', result)
            }else{
                this.dataSources=[]
                await this.setActiveActions(0)
                this.$toast.error('Không có thông tin hợp đồng')
            }

        },
        async HT_TT(dsHDTB){
            console.log('HT_TT', dsHDTB)
            var result=dsHDTB
            for(var i = 0; i < result.length; i++){ 
                for (var key in result[i]) {
                    if(key.toUpperCase() !== key){
                        result[i][key.toUpperCase()] = result[i][key];
                        delete result[i][key];
                    }
                }
            }
            if(dsHDTB.length>0){
                if(dsHDTB.length>1){
                    this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=true
                }else{
                    this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=false
                }
                this.dataSources=[]
                setTimeout(async()=>{
                    this.dataSources=result
                    // this.rowSelectedId=dsHDTB[0].HDTB_ID
                    // await this.onSelectedRow(dsHDTB[0])
                }, 500)
            }else{
                this.dataSources=[]
            }
        },
        async HienThiTT_DanhBa(ma_tb){
            let ds = await this.sp_lay_danhba_theo_matb({
                dichvuvt_id: this.dichvuvt_id,
                ma_tb: ma_tb,
                donvi_dl_id: 0
            })
            this.dsDBTB = ds
            if(ds.length>0){
                if(ds.length>1){
                    this.dsTBCungMa=ds
                    this.$refs.danhSachThueBaoCungMaModal.showModal()
                    return
                }
                //
                await this.HienThiTT_DanhBaV2(ds[0])
            }else{
                let message='Không tìm thấy thông tin về thuê bao '+ma_tb+' đối với dịch vụ '+this.$refs.thongTinPhieuYeuCau.getDichVu()
                this.$toast.error(message)
            }
        },
        async acceptThuBaoCungMa(item){
            this.loading(true)
            await this.HienThiTT_DanhBaV2(item)
            this.loading(false)
        },
        async HienThiTT_DanhBaV2(item){
            console.log('HienThiTT_DanhBaV2', item)
            //if (WinUICommon.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb.Trim(), "0", Convert.ToInt32(cboDichVuVT.SelectedValue), LoaiHopDong.CHUYENDOI_LH)) return;
            let checkHDKhac = await this.kiemtra_thuebao_lap_hopdong_khac({
                maTb: item.ma_tb,
                maGd: "0",
                dichVuVtId: this.dichvuvt_id,
                loaiHdId: LoaiHopDong.CHUYENDOI_LH
            })
            if(checkHDKhac!=null){  
                let message='Số máy/Acc: '+item.ma_tb+' đang được lập bởi hợp đồng (phụ lục) '+checkHDKhac.ten_loaihd
                            +' có mã giao dịch '+checkHDKhac.ma_gd+' do User '+checkHDKhac.nguoi_cn+
                            ' thuộc đơn vị '+checkHDKhac.ten_dv+' thực hiện vào ngày '+checkHDKhac.ngay_yc
                this.$toast.error(message)
                return
            }
            ///---> Nếu trạng thái thuê bao # " Hoạt động bình thường" ---> không cho phép làm tiếp
            if(item.trangthaitb_id!=TrangThaiTB.THUONG){
                let message='Thuê bao có mã: '+item.ma_tb+' đang ở trạng thái: '+item.trangthai_tb+' Bạn không thể thực hiện đổi chuyển đổi loại hình thuê bao cho thuê bao này'
                this.$toast.error(message)
                return
            }
            //Kiểm tra khách hàng đã ký hợp đồng gốc hay chưa
            if(item.ky_hd==0){
                let message='Khách hàng có mã thuê bao : '+item.ma_tb+' chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc'
                this.$toast.error(message)
                return
            }
            //Kiểm tra nếu ko pải thuê bao ADSL thì return: hiếu tc ngày 11.10.2010
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL&&item.loaitb_id!=LoaiHinhTB.INTERNET_ADSL
                &&item.loaitb_id!=LoaiHinhTB.INTERNET_FTTH&&item.loaitb_id!=LoaiHinhTB.WIFI_FIBER
                &&item.loaitb_id!=LoaiHinhTB.INTERNET_ADSL_TINH_KHAC&&item.loaitb_id!=LoaiHinhTB.INTERNET_FIBER_TINH_KHAC
                &&item.loaitb_id!=LoaiHinhTB.INTERNET_MYTV){
                    let message='Thuê bao: '+item.ma_tb+' không phải là Internet. \n Bạn không thể thực hiện chuyển đổi loại hình đối với thuê bao này!'
                    this.$toast.error(message)
                    return
            }
            //
            //1551996
            let ds_km=await this.lay_ds_chitiet_km_theo_tbid(item.thuebao_id!=null?item.thuebao_id:0)
            if(ds_km.length>0){
                var message='Thuê bao '+item.ma_tb+' đang hưởng khuyến mại : \n '+ds_km[0].ten_km
                            +' \n    Có chi tiết khuyến mại : '
                for (let i =0; i<ds_km.length ;i++) {
                    message+='\n     - '+ds_km[i].ten_ctkm
                }
                message+='\nBạn có muốn tiếp tục thực hiện?'
                let result = await this.confirm(message,'Thông báo')
                if(result==0) return
            }
            //
            this.thuebao_id=item.thuebao_id?item.thuebao_id:0

            if(!this.kenh_trang){
                let tinh=await this.lay_tinh_theo_thuebaoid(this.thuebao_id)
                this.tinh_thicong=tinh.length>0?tinh[0].tinh_thicong:0
            }
            if(this.tinh_thicong>0&&!this.kenh_trang){
                //
                let resultTB=await this.lay_thuebaoid_thicong(this.thuebao_id, this.tinh_thicong)
                this.thuebao_tc_id=resultTB.length>0?resultTB[0].thuebao_kn_id:0
                this.dsDBTBBC=await this.lay_danhba_theo_thuebaoid(this.tinh_thicong, this.thuebao_tc_id, this.dichvu_vt_selected, 0,2)

            }else{
                this.thuebao_tc_id=0
                this.dsDBTBBC=[]
            }
            //
            if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                if(this.tinh_dau>0&&this.tinh_cuoi>0){
                    if(!this.kenh_trang){
                        let dau=await this.lay_thuebaoid_thicong(this.thuebao_id, this.tinh_dau)
                        this.thuebao_tc_id=dau.length>0?dau[0].thuebao_kn_id:0
                        let cuoi=await this.lay_thuebaoid_thicong(this.thuebao_id, this.tinh_cuoi)
                        this.thuebao_tc_id_tsl=cuoi.length>0?cuoi[0].thuebao_kn_id:0
                        this.dsDBTBBC=[]
                        let db_dau=await this.lay_danhba_theo_thuebaoid(this.tinh_dau, this.thuebao_tc_id, this.dichvu_vt_selected, 0,2)
                        let db_cuoi=await this.lay_danhba_theo_thuebaoid(this.tinh_cuoi, this.thuebao_tc_id_tsl, this.dichvu_vt_selected, 0,2)
                        if(db_dau.length>0){
                            this.dsDBTBBC.push(db_dau[0])
                        }
                        if(db_cuoi.length>0){
                            this.dsDBTBBC.push(db_cuoi[0])
                        }
                    
                    }else{
                        this.thuebao_tc_id = 0
                        this.thuebao_tc_id_tsl = 0
                        this.dsDBTBBC = []
                    }
                }
            }else{
                //var ds=[]
                if(this.tinh_thicong>0&&!this.kenh_trang){
                    //rà sau
                    let ds=await this.lay_danhba_theo_matb_bancheo({
                        in_dichvuvt_id: this.dichvuvt_id,
                        in_donvi_dl_id: 0,
                        in_loaihd_bancheo: 1,
                        in_ma_tb: item.ma_tb
                    })
                    this.dsDBTB=ds
                    item=ds[0]
                }
                //bỏ qua không cần thiết, default gán hàm trên
                // else{
                //     ds= await this.sp_lay_danhba_theo_matb({
                //         dichvuvt_id: this.dichvuvt_id,
                //         ma_tb: ma_tb,
                //         donvi_dl_id: 0
                //     })
                // }
                
            }
            console.log('HienThiTT_DanhBaV2 2', item)
            console.log('HienThiTT_DanhBaV2 dsDBTB', this.dsDBTB)
            //
            this.thongso_ban = newThongSoThueBao()
            this.thongso_tc = newThongSoThueBao()
            this.isCheckCotTinhBanTC=true
            await this.gan_thongso_theodanhba(1, this.dichvuvt_id, this.thuebao_id)
            if(!this.isCheckCotTinhBanTC){
                return
            }

            if(this.thuebao_tc_id!=0){
                await this.gan_thongso_theodanhba(2, this.dichvuvt_id, this.thuebao_tc_id)
                if(!this.isCheckCotTinhBanTC){
                    return
                }
            }

            //

            this.khachhang_id = item.khachhang_id?item.khachhang_id:0
            this.khachhang_tc_id = await this.fn_lay_khachhangid_thicong(this.khachhang_id, this.tinh_thicong)
            this.vdiachi_tb=item.diachi_tb?item.diachi_tb.toString():''
            this.loaitb_id=item.loaitb_id?item.loaitb_id:0
            this.donviql_id=item.donviql_id?item.donviql_id:0
            this.doituong_id=item.doituong_id?item.doituong_id:0
            this.so_dt=item.so_dt?item.so_dt.toString():''

            await this.$refs.thongTinKhachHang.HienThiTT_DanhBaV2(item)
            await this.$refs.thongTinPhieuYeuCau.HienThiTT_DanhBaV2(item)

            if(this.dichvuvt_id==DichVuVienThong.MEGAWAN||this.dichvuvt_id==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                this.isCheckCotTinhBanTC=true
                await this.gan_thongso_bancheo_thuebaoid(this.thuebao_id, this.thuebao_tc_id, this.tinh_thicong)
                if(!this.isCheckCotTinhBanTC){
                    return
                }
            }
            let ds_kv=await this.sp_lay_ds_khuvuc_theo_thuebaoid(this.thuebao_id)
            if(ds_kv.length>0){
                this.khuvuc_id=ds_kv[0].khuvuc_id
                Object.assign(item,{
                    ten_kv:ds_kv[0].ten_kv!=null?ds_kv[0].ten_kv:''
                })
            }else{
                this.khuvuc_id=0
            }
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.HienThiTT_DanhBaV2(item)

            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                //     cboDoiTuongCu.SelectedValue = Convert.ToInt32(ds.Tables[0].Rows[0]["doituong_id"]);
                //     cboDoiTuongMoi.SelectedValue = Convert.ToInt32(ds.Tables[0].Rows[0]["doituong_id"]);
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                await this.$refs.bangRongCoDinh.HienThiTT_DanhBaV2(item)
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.$refs.ims.HienThiTT_DanhBaV2(item)
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.$refs.kenhThuRieng.HienThiTT_DanhBaV2(item)
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.$refs.megawan.HienThiTT_DanhBaV2(item)
            }
            //hiển thị địa chỉ
            let dsDiaChi=await this.lay_ds_diachi_theo_dbtbid(this.thuebao_id)
            //upper case key data source
            for(var i = 0; i < dsDiaChi.length; i++){ 
                for (var key in dsDiaChi[i]) {
                    if(key.toUpperCase() !== key){
                        dsDiaChi[i][key.toUpperCase()] = dsDiaChi[i][key];
                        delete dsDiaChi[i][key];
                    }
                }
            }
            console.log('dsDiaChi', dsDiaChi)
            await this.HienThiTTDiaChi(dsDiaChi)

            await this.HienThiTTMoRong_DB(this.thuebao_id, this.dichvuvt_id)

            let kieuld_id=await this.$refs.thongTinPhieuYeuCau.getKieuID()
            await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaitb_id, kieuld_id)
            //Cước TB
            var vtrangbi_id=0
            if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                vtrangbi_id=await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
            }
            var vloaitb_id_moi=0
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                vloaitb_id_moi=await this.$refs.coDinh.getLoaiHinhTB_ID_Moi()
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL){
                //băng rộng cố định
                vloaitb_id_moi=await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                vloaitb_id_moi=await this.$refs.ims.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                vloaitb_id_moi=await this.$refs.kenhThuRieng.getLoaiHinhTB_ID_Moi()
                
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                vloaitb_id_moi=await this.$refs.megawan.getLoaiHinhTB_ID_Moi()
            }

            let ngay_yc=await this.$refs.thongTinPhieuYeuCau.getNgayYC()
            await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)

        },
        async gan_thongso_theodanhba(vkieu, vdichvu, vthuebaoid){
            if(vdichvu==DichVuVienThong.CO_DINH){
                if(vkieu==1){
                    let ds=await this.lay_ds_db_cd_theo_tbid(vthuebaoid)
                    if(ds.length>0){
                        this.thongso_ban.trangbi_id=ds[0].trangbi_id?ds[0].trangbi_id:0
                        this.thongso_ban.doituong_id=this.dsDBTB[0].doituong_id?this.dsDBTB[0].doituong_id:0
                        this.thongso_ban.muccuoctb_id=this.dsDBTB[0].muccuoctb_id?this.dsDBTB[0].muccuoctb_id:0
                    }
                }else{
                    let ds=await this.lay_ds_db_cd_theo_tbid_bancheo(vthuebaoid, this.tinh_thicong)
                    if(ds.length>0){
                        this.thongso_tc.trangbi_id=ds[0].trangbi_id?ds[0].trangbi_id:0
                        if(this.dsDBTBBC.length>0){
                            this.thongso_tc.doituong_id=this.dsDBTBBC[0].doituong_id?this.dsDBTBBC[0].doituong_id:0
                            this.thongso_tc.muccuoctb_id=this.dsDBTBBC[0].mucuoctb_id?this.dsDBTBBC[0].mucuoctb_id:0
                        }
                    }
                }
            }else if(vdichvu==DichVuVienThong.ADSL){
                if(vkieu==1){
                    let ds=await this.lay_ds_db_adsl_theo_tbid(vthuebaoid)
                    var exp_text=''
                    if(ds.length>0){
                        try{

                            exp_text = "trangbi_id"
                            this.thongso_ban.trangbi_id=ds[0].trangbi_id
                            exp_text = "tocdo_id"
                            this.thongso_ban.tocdo_id = ds[0].tocdo_id
                            exp_text = "tocdo_id"
                            this.thongso_ban.tocdothuc_id = ds[0].tocdothuc_id
                            exp_text = "muccuoc_id"
                            this.thongso_ban.muccuoc_id = ds[0].muccuoc_id
                            exp_text = "kieutra_id"
                            this.thongso_ban.kieutra_id = ds[0].kieutra_id
                            exp_text = "thietbidc_id"
                            this.thongso_ban.thietbidc_id = ds[0].thietbidc_id
                            exp_text = "doituong_id"
                            this.thongso_ban.doituong_id = this.dsDBTB[0].doituong_id?this.dsDBTB[0].doituong_id:0
                            exp_text = "mucuoctb_id"
                            this.thongso_ban.muccuoctb_id = this.dsDBTB[0].mucuoctb_id?this.dsDBTB[0].mucuoctb_id:0
                        }catch(e){
                            this.isCheckCotTinhBanTC=false
                            this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' tại tỉnh bán. Liên hệ admin để xử lý.')
                        }
                    }
                }else{
                    let ds=await this.lay_ds_db_adsl_theo_tbid_bancheo(vthuebaoid, this.tinh_thicong)
                    var exp_text=''
                    try{
                        exp_text = "trangbi_id"
                        this.thongso_tc.trangbi_id=ds[0].trangbi_id
                        exp_text = "tocdo_id"
                        this.thongso_tc.tocdo_id = ds[0].tocdo_id
                        exp_text = "tocdo_id"
                        this.thongso_tc.tocdothuc_id = ds[0].tocdothuc_id
                        exp_text = "muccuoc_id"
                        this.thongso_tc.muccuoc_id = ds[0].muccuoc_id
                        exp_text = "kieutra_id"
                        this.thongso_tc.kieutra_id = ds[0].kieutra_id
                        exp_text = "thietbidc_id"
                        this.thongso_tc.thietbidc_id = ds[0].thietbidc_id
                        if(this.dsDBTBBC.length>0){
                            exp_text = "doituong_id"
                            this.thongso_tc.doituong_id=this.dsDBTBBC[0].doituong_id?this.dsDBTB[0].doituong_id:0
                            exp_text = "mucuoctb_id"
                            this.thongso_tc.muccuoctb_id=this.dsDBTBBC[0].muccuoctb_id?this.dsDBTB[0].muccuoctb_id:0
                        }
                    }catch(e){
                        this.isCheckCotTinhBanTC=false
                        let tinhtc=this.ds_tinh_tc.find(x=>x.tinh_id==this.tinh_thicong)
                        if(!tinhtc){
                            this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' tại tỉnh thi công. Liên hệ admin để xử lý.')
                        }else{
                            this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' tại tỉnh thi công ('+tinhtc.tentinh.split('-')[1]+'). Liên hệ admin để xử lý.')
                        }
                    }
                }
            }else if(vdichvu==DichVuVienThong.MEGAWAN||vdichvu==DichVuVienThong.METRONET||vdichvu==DichVuVienThong.NOIBO_DADIEM){
                if(vkieu==1){
                    let ds=await this.lay_ds_danhba_megawan(vthuebaoid)
                    var exp_text=''
                    if(ds.length>0){
                        try{
                            exp_text = "trangbi_id"
                            this.thongso_ban.trangbi_id=ds[0].trangbi_id
                            exp_text = "tocdo_id"
                            this.thongso_ban.tocdo_id = ds[0].tocdo_id
                            this.thongso_ban.tocdo_pir_id=ds[0].tocdo_pir_id
                            this.thongso_ban.tocdo_isp=ds[0].tocdo_isp
                            this.thongso_ban.tocdo_nix = ds[0].tocdo_nix
                            exp_text = "loaimd_id"
                            this.thongso_ban.loaimd_id = ds[0].loaimd_id
                            exp_text = "loaikenh_id"
                            this.thongso_ban.loaikenh_id = ds[0].loaikenh_id
                            exp_text = "thoihan_id"
                            this.thongso_ban.thoihan_id = ds[0].thoihan_id
                            this.thongso_ban.muccuoc_id=ds[0].muccuoc_id?ds[0].muccuoc_id:0
                            this.thongso_ban.congnghe_id=ds[0].congnghe_id?ds[0].congnghe_id:0
                            exp_text = "thietbidc_id"
                            this.thongso_ban.thietbidc_id = ds[0].thietbidc_id?ds[0].thietbidc_id:0
                            exp_text = "doituong_id"
                            this.thongso_ban.doituong_id = this.dsDBTB[0].doituong_id?this.dsDBTB[0].doituong_id:0
                            exp_text = "mucuoctb_id"
                            this.thongso_ban.muccuoctb_id = this.dsDBTB[0].mucuoctb_id?this.dsDBTB[0].mucuoctb_id:0
                        }catch(e){
                            this.isCheckCotTinhBanTC=false
                            this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' tại tỉnh bán. Liên hệ admin để xử lý.')
                        }
                    }
                }else{
                    let ds=await this.lay_ds_db_megawan_theo_tbid_bancheo(vthuebaoid, this.tinh_thicong)
                    var exp_text=''
                    if(ds.length>0){
                        try{
                            exp_text = "trangbi_id"
                            this.thongso_tc.trangbi_id=ds[0].trangbi_id
                            exp_text = "tocdo_id"
                            this.thongso_tc.tocdo_id = ds[0].tocdo_id
                            this.thongso_tc.tocdo_pir_id=ds[0].tocdo_pir_id
                            this.thongso_tc.tocdo_isp=ds[0].tocdo_isp
                            this.thongso_tc.tocdo_nix = ds[0].tocdo_nix
                            exp_text = "loaimd_id"
                            this.thongso_tc.loaimd_id = ds[0].loaimd_id
                            exp_text = "loaikenh_id"
                            this.thongso_tc.loaikenh_id = ds[0].loaikenh_id
                            exp_text = "thoihan_id"
                            this.thongso_tc.thoihan_id = ds[0].thoihan_id
                            this.thongso_tc.muccuoc_id=ds[0].muccuoc_id?ds[0].muccuoc_id:0
                            this.thongso_tc.congnghe_id=ds[0].congnghe_id?ds[0].congnghe_id:0
                            exp_text = "thietbidc_id"
                            this.thongso_tc.thietbidc_id = ds[0].thietbidc_id?ds[0].thietbidc_id:0
                            if(this.dsDBTBBC.length>0){
                                exp_text = "doituong_id"
                                this.thongso_tc.doituong_id=this.dsDBTBBC[0].doituong_id?this.dsDBTB[0].doituong_id:0
                                exp_text = "mucuoctb_id"
                                this.thongso_tc.muccuoctb_id=this.dsDBTBBC[0].muccuoctb_id?this.dsDBTB[0].muccuoctb_id:0
                            }
                        }catch(e){
                            this.isCheckCotTinhBanTC=false
                            let tinhtc=this.ds_tinh_tc.find(x=>x.tinh_id==this.tinh_thicong)
                            if(!tinhtc){
                                this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' tại tỉnh thi công. Liên hệ admin để xử lý.')
                            }else{
                                this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' tại tỉnh thi công ('+tinhtc.tentinh.split('-')[1]+'). Liên hệ admin để xử lý.')
                            }
                        }
                    }
                }
            }else if(vdichvu==DichVuVienThong.TSL||vdichvu==DichVuVienThong.NOIBO_2DIEM){
                if(vkieu==1){
                    let ds=await this.lay_ds_danhba_tsl(vthuebaoid)
                    if(ds.length>0){
                        this.thongso_ban.trangbi_id = ds[0].trangbi_id?ds[0].trangbi_id:0
                        this.thongso_ban.tocdo_id = ds[0].tocdo_id?ds[0].tocdo_id:0
                        this.thongso_ban.loaikenh_id = ds[0].loaikenh_id?ds[0].loaikenh_id:0
                        this.thongso_ban.thietbidc_id = ds[0].thietbidc_id?ds[0].thietbidc_id:0
                        this.thongso_ban.thietbidc_id_cuoi = ds[0].thietbidc_id_cuoi?ds[0].thietbidc_id_cuoi:0

                        this.thongso_ban.doituong_id = this.dsDBTB[0].doituong_id?this.dsDBTB[0].doituong_id:0
                        this.thongso_ban.muccuoctb_id= this.dsDBTB[0].mucuoctb_id?this.dsDBTB[0].mucuoctb_id:0
                    }
                }else{
                    let ds=await this.lay_ds_db_tsl_theo_tbid_bancheo(vthuebaoid, this.tinh_thicong)
                    if(ds.length>0){
                        this.thongso_tc.trangbi_id = ds[0].trangbi_id?ds[0].trangbi_id:0
                        this.thongso_tc.tocdo_id = ds[0].tocdo_id?ds[0].tocdo_id:0
                        this.thongso_tc.loaikenh_id = ds[0].loaikenh_id?ds[0].loaikenh_id:0
                        this.thongso_tc.thietbidc_id = ds[0].thietbidc_id?ds[0].thietbidc_id:0
                        this.thongso_tc.thietbidc_id_cuoi = ds[0].thietbidc_id_cuoi?ds[0].thietbidc_id_cuoi:0
                        if(this.dsDBTBBC.length>0){
                            this.thongso_tc.doituong_id=this.dsDBTBBC[0].doituong_id?this.dsDBTBBC[0].doituong_id:0
                            this.thongso_tc.muccuoctb_id=this.dsDBTBBC[0].mucuoctb_id?this.dsDBTBBC[0].mucuoctb_id:0
                        }
                    }

                }
                
            }else if(vdichvu==DichVuVienThong.IMS){
                if(vkieu==1){
                    let ds=await this.lay_ds_danhba_ims(vthuebaoid)
                    if(ds.length>0){
                        this.thongso_ban.trangbi_id=ds[0].trangbi_id?ds[0].trangbi_id:0
                        this.thongso_ban.doituong_id=this.dsDBTB[0].doituong_id?this.dsDBTB[0].doituong_id:0
                        this.thongso_ban.muccuoctb_id=this.dsDBTB[0].muccuoctb_id?this.dsDBTB[0].muccuoctb_id:0
                    }
                }else{
                    let ds=await this.lay_ds_db_ims_theo_tbid_bancheo(vthuebaoid, this.tinh_thicong)
                    if(ds.length>0){
                        this.thongso_tc.trangbi_id=ds[0].trangbi_id?ds[0].trangbi_id:0
                        if(this.dsDBTBBC.length>0){
                            this.thongso_tc.doituong_id=this.dsDBTBBC[0].doituong_id?this.dsDBTBBC[0].doituong_id:0
                            this.thongso_tc.muccuoctb_id=this.dsDBTBBC[0].mucuoctb_id?this.dsDBTBBC[0].mucuoctb_id:0
                        }
                    }
                }
            }

            console.log('gan_thongso_theodanhba thongso_ban', this.thongso_ban)
            console.log('gan_thongso_theodanhba thongso_tc', this.thongso_tc)
        },
        async gan_thongso_bancheo_thuebaoid(thuebao_id, thuebao_tc_id, tinh_thicong){
            let ds=await this.lay_thongso_thuebao_thuebaoid(thuebao_id)
            var exp_text=""
            if(ds.length>0){
                try{
                    exp_text = "kieuld_id"
                    this.thongso_ban.kieuld_id = ds[0].kieuld_id
                    exp_text = "doituong_id"
                    this.thongso_ban.doituong_id = ds[0].doituong_id
                    exp_text = "chuquan_id"
                    this.thongso_ban.chuquan_id = ds[0].chuquan_id
                    exp_text = "chuquanld_id"
                    this.thongso_ban.chuquanld_id = ds[0].chuquanld_id
                    exp_text = "congnghe_id"
                    this.thongso_ban.congnghe_id = ds[0].congnghe_id
                    exp_text = "kieutra_id"
                    this.thongso_ban.kieutra_id = ds[0].kieutra_id
                    exp_text = "loaikenh_id"
                    this.thongso_ban.loaikenh_id = ds[0].loaikenh_id
                    exp_text = "loaimd_id"
                    this.thongso_ban.loaimd_id = ds[0].loaimd_id
                    exp_text = "ma_doicap"
                    this.thongso_ban.ma_doicap = ds[0].ma_doicap
                    exp_text = "ma_tn"
                    this.thongso_ban.ma_tn = ds[0].ma_tn
                    exp_text = "mucuoc_tb"
                    this.thongso_ban.muccuoc_id = ds[0].mucuoc_tb
                    exp_text = "mucuoctb_id"
                    this.thongso_ban.muccuoctb_id = ds[0].mucuoctb_id
                    exp_text = "thietbidc_id"
                    this.thongso_ban.thietbidc_id = ds[0].thietbidc_id
                    exp_text = "THIETBI_DICH" 
                    this.thongso_ban.thietbidc_id_cuoi = ds[0].thietbi_dich
                    exp_text = "thoihan_id"
                    this.thongso_ban.thoihan_id = ds[0].thoihan_id
                    exp_text = "tocdo_id"
                    this.thongso_ban.tocdo_id = ds[0].tocdo_id
                    exp_text = "tocdo_isp"
                    this.thongso_ban.tocdo_isp = ds[0].tocdo_isp
                    exp_text = "tocdo_nix"
                    this.thongso_ban.tocdo_nix = ds[0].tocdo_nix
                    this.thongso_ban.tocdo_pir_id=ds[0].tocdo_pir_id
                    exp_text = "tocdothuc_id"
                    this.thongso_ban.tocdothuc_id = ds[0].tocdothuc_id
                    exp_text = "trangbi_id"
                    this.thongso_ban.trangbi_id = ds[0].trangbi_id

                }catch(e){
                    this.isCheckCotTinhBanTC=false
                    this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' tại tỉnh bán. Liên hệ admin để xử lý.')
                }
            }
            if(thuebao_tc_id>0){
                let ds=await this.lay_thongso_thuebao_thuebaoid_bancheo(thuebao_tc_id, tinh_thicong)
                var exp_text=""
                if(ds.length>0){
                    try{
                        exp_text = "kieuld_id"
                        this.thongso_tc.kieuld_id = ds[0].kieuld_id
                        exp_text = "doituong_id"
                        this.thongso_tc.doituong_id = ds[0].doituong_id
                        exp_text = "chuquan_id"
                        this.thongso_tc.chuquan_id = ds[0].chuquan_id
                        exp_text = "chuquanld_id"
                        this.thongso_tc.chuquanld_id = ds[0].chuquanld_id
                        exp_text = "congnghe_id"
                        this.thongso_tc.congnghe_id = ds[0].congnghe_id
                        exp_text = "kieutra_id"
                        this.thongso_tc.kieutra_id = ds[0].kieutra_id
                        exp_text = "loaikenh_id"
                        this.thongso_tc.loaikenh_id = ds[0].loaikenh_id
                        exp_text = "loaimd_id"
                        this.thongso_tc.loaimd_id = ds[0].loaimd_id
                        exp_text = "ma_doicap"
                        this.thongso_tc.ma_doicap = ds[0].ma_doicap
                        exp_text = "ma_tn"
                        this.thongso_tc.ma_tn = ds[0].ma_tn
                        exp_text = "mucuoc_tb"
                        this.thongso_tc.muccuoc_id = ds[0].mucuoc_tb
                        exp_text = "mucuoctb_id"
                        this.thongso_tc.muccuoctb_id = ds[0].mucuoctb_id
                        exp_text = "thietbidc_id"
                        this.thongso_tc.thietbidc_id = ds[0].thietbidc_id
                        exp_text = "THIETBI_DICH" 
                        this.thongso_tc.thietbidc_id_cuoi = ds[0].thietbi_dich
                        exp_text = "thoihan_id"
                        this.thongso_tc.thoihan_id = ds[0].thoihan_id
                        exp_text = "tocdo_id"
                        this.thongso_tc.tocdo_id = ds[0].tocdo_id
                        exp_text = "tocdo_isp"
                        this.thongso_tc.tocdo_isp = ds[0].tocdo_isp
                        exp_text = "tocdo_nix"
                        this.thongso_tc.tocdo_nix = ds[0].tocdo_nix
                        this.thongso_tc.tocdo_pir_id=ds[0].tocdo_pir_id
                        exp_text = "tocdothuc_id"
                        this.thongso_tc.tocdothuc_id = ds[0].tocdothuc_id
                        exp_text = "trangbi_id"
                        this.thongso_tc.trangbi_id = ds[0].trangbi_id
                    }catch(e){
                        this.isCheckCotTinhBanTC=false
                        let tinhtc=this.ds_tinh_tc.find(x=>x.tinh_id==this.tinh_thicong)
                        if(!tinhtc){
                            this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' thuê bao '+ thuebao_tc_id+' tại tỉnh thi công. Liên hệ admin để xử lý.')
                        }else{
                            this.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' thuê bao '+ thuebao_tc_id+' tại tỉnh thi công ('+tinhtc.tentinh.split('-')[1]+'). Liên hệ admin để xử lý.')
                        }
                    }
                }
            }
            console.log('gan_thongso_bancheo_thuebaoid thongso_ban', this.thongso_ban)
            console.log('gan_thongso_bancheo_thuebaoid thongso_tc', this.thongso_tc)
        },
        async HienThiTTDiaChi(dsDiaChi){
            if(dsDiaChi.length>0){
                //Địa chỉ tb
                Object.assign(this.dataDiaChiTB, {
                    diachi_id:dsDiaChi[0].DIACHI_ID,
                    tinh_id:dsDiaChi[0].TINH_ID,
                    quan_id:dsDiaChi[0].QUAN_ID,
                    phuong_id:dsDiaChi[0].PHUONG_ID,
                    pho_id:dsDiaChi[0].PHO_ID!=null?dsDiaChi[0].PHO_ID:0,
                    ap_id:dsDiaChi[0].AP_ID!=null?dsDiaChi[0].AP_ID:0,
                    khu_id:dsDiaChi[0].KHU_ID!=null?dsDiaChi[0].KHU_ID:0,
                    dacdiem_id:dsDiaChi[0].DACDIEM_ID!=null?dsDiaChi[0].DACDIEM_ID:0,
                    sonha:dsDiaChi[0].SONHA!=null?dsDiaChi[0].SONHA:'',
                    //chưa rõ model
                    block:dsDiaChi[0].hasOwnProperty('BLOCK')?dsDiaChi[0].BLOCK:'',
                    tang:dsDiaChi[0].hasOwnProperty('TANG')?dsDiaChi[0].TANG:'',
                    sophong:dsDiaChi[0].hasOwnProperty('PHONG')?dsDiaChi[0].PHONG:'',
                    motathem:dsDiaChi[0].hasOwnProperty('MOTA')?dsDiaChi[0].MOTA:'',
                    diachi:'',
                    //
                    kinhdo:dsDiaChi[0].KINHDO,
                    vido:dsDiaChi[0].VIDO
                })
                //Địa chỉ LD
                Object.assign(this.dataDiaChiLD, {
                    diachi_id:dsDiaChi[0].DIACHILD_ID,
                    tinh_id:dsDiaChi[0].TINHLD_ID,
                    quan_id:dsDiaChi[0].QUANLD_ID,
                    phuong_id:dsDiaChi[0].PHUONGLD_ID,
                    pho_id:dsDiaChi[0].PHOLD_ID!=null?dsDiaChi[0].PHOLD_ID:0,
                    ap_id:dsDiaChi[0].APLD_ID!=null?dsDiaChi[0].APLD_ID:0,
                    khu_id:dsDiaChi[0].KHULD_ID!=null?dsDiaChi[0].KHULD_ID:0,
                    dacdiem_id:dsDiaChi[0].DACDIEMLD_ID!=null?dsDiaChi[0].DACDIEMLD_ID:0,
                    sonha:dsDiaChi[0].SONHA_LD!=null?dsDiaChi[0].SONHA_LD:'',
                    //chưa rõ model
                    block:dsDiaChi[0].hasOwnProperty('BLOCK_LD')?dsDiaChi[0].BLOCK_LD:'',
                    tang:dsDiaChi[0].hasOwnProperty('TANG_LD')?dsDiaChi[0].TANG_LD:'',
                    sophong:dsDiaChi[0].hasOwnProperty('PHONG_LD')?dsDiaChi[0].PHONG_LD:'',
                    motathem:dsDiaChi[0].hasOwnProperty('MOTA_LD')?dsDiaChi[0].MOTA_LD:'',
                    diachi:'',
                    //
                    kinhdo:dsDiaChi[0].KINHDO_LD,
                    vido:dsDiaChi[0].VIDO_LD
                })
            }
            await this.$refs.thongTinKhachHang.HienThiTTDiaChi(dsDiaChi)
        },
        async HienThiTTMoRong_DB(thuebao_id, dichvuvt_id){
            await this.$refs.thongTinKhachHang.HienThiTTMoRong_DB()
            if(dichvuvt_id==DichVuVienThong.CO_DINH){
                //lay_ds_db_cd_theo_tbid
                let ds=await this.lay_ds_db_cd_theo_tbid(thuebao_id)
                console.log('HienThiTTMoRong_DB DS_CODINH', ds)
                if(ds.length>0){
                    await this.$refs.coDinh.HienThiTTMoRong_DB(ds[0])
                }
                if(this.loaihinhtbcu_id==LoaiHinhTB.ISDN2B_CD||
                    this.loaihinhtbcu_id==LoaiHinhTB.ISDN2B_CQ||
                    this.loaihinhtbcu_id==LoaiHinhTB.ISDN30B_CD||
                    this.loaihinhtbcu_id==LoaiHinhTB.ISDN30B_CQ){
                    console.log('LAY_DS_ISDN_THEO_THUEBAO_ID')
                    await this.LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id)
                }

            }else if(dichvuvt_id==DichVuVienThong.ADSL){
                let ds=await this.lay_ds_db_adsl_theo_tbid(thuebao_id)
                console.log('HienThiTTMoRong_DB DS_ADSL', ds)
                if(ds.length>0){
                    await this.$refs.bangRongCoDinh.HienThiTTMoRong_DB(ds[0])
                    this.$refs.thongTinPhieuYeuCau.input_soao=ds[0].ma_lt
                }
            }else if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.METRONET||dichvuvt_id==DichVuVienThong.NOIBO_DADIEM){
                //lay_ds_danhba_megawan
                let ds=await this.lay_ds_danhba_megawan(thuebao_id)
                if(ds.length>0){
                    let item=ds[0]
                    if(this.$refs.megawan.tocdo_cir_moi_selected!=item.tocdo_id){
                        this.$refs.megawan.tocdo_cir_moi_selected=item.tocdo_id
                        await this.changeTocDoCirWan(this.$refs.megawan.tocdo_cir_moi_selected)
                    }else{
                        await this.changeTocDoCirWan(this.$refs.megawan.tocdo_cir_moi_selected)
                    }
                    if(this.$refs.megawan.loaithietbi_moi_selected!=item.thietbidc_id){
                        this.$refs.megawan.loaithietbi_moi_selected=item.thietbidc_id
                        await this.changeLoaiTBWanMoi(this.$refs.megawan.loaithietbi_moi_selected)
                    }else{
                        await this.changeLoaiTBWanMoi(this.$refs.megawan.loaithietbi_moi_selected)
                    }

                    this.vhuong_kn=item.huong_kn?item.huong_kn.toString():''

                    //fn_lay_text_huongketnoi_v2
                    if(this.vhuong_kn!=''){
                        let textHuongKN=await this.fn_lay_text_huongketnoi_v2(this.vhuong_kn)
                        this.$refs.megawan.input_huong_kn=textHuongKN
                        this.$refs.megawan.input_huong_kn_moi=textHuongKN
                    }else{
                        this.$refs.megawan.input_huong_kn=''
                        this.$refs.megawan.input_huong_kn_moi=''
                    }

                    this.cuoc_tc=item.cuoc_tc?item.cuoc_tc:0
                    this.cuoc_tk=item.cuoc_tk?item.cuoc_tk:0
                    this.$refs.megawan.trangbi_moi_selected=item.trangbi_id?item.trangbi_id:3
                    await this.changeTrangBiMoi(this.$refs.megawan.trangbi_moi_selected)

                    await this.$refs.megawan.HienThiTTMoRong_DB(item)

                    this.$refs.thongTinKhachHang.checkSoCVEnabled=true
                    this.$refs.thongTinKhachHang.ngay_cv=moment(new Date()).format('DD/MM/YYYY')
                    this.$refs.thongTinPhieuYeuCau.input_soao=item.ma_lt?item.ma_lt.toString():''
                    //txtMAHD_TTKD-->MaCNG
                    this.$refs.thongTinPhieuYeuCau.input_ma_cng=item.ma_pl?item.ma_pl.toString():''

                    this.$refs.thongTinKhachHang.input_ma_bss=item.ma_bss?item.ma_bss.toString():''
                    this.$refs.thongTinKhachHang.input_ma_erp=item.id_erp?item.id_erp.toString():''

                }

            }else if(dichvuvt_id==DichVuVienThong.TSL||dichvuvt_id==DichVuVienThong.NOIBO_2DIEM){
                //lay_ds_danhba_tsl
                let ds=await this.lay_ds_danhba_tsl(thuebao_id)
                if(ds.length>0){
                    let item=ds[0]
                    await this.$refs.kenhThuRieng.HienThiTTMoRong_DB(item)
                    this.$refs.thongTinPhieuYeuCau.input_ma_cng=item.ma_pl?item.ma_pl.toString():''
                    this.$refs.thongTinKhachHang.checkSoCVEnabled=true
                    this.$refs.thongTinKhachHang.ngay_cv=moment(new Date()).format('DD/MM/YYYY')
                    this.$refs.thongTinPhieuYeuCau.input_soao=''
                    this.$refs.thongTinKhachHang.input_ma_bss=item.ma_bss?item.ma_bss.toString():''
                    this.$refs.thongTinKhachHang.input_ma_erp=item.id_erp?item.id_erp.toString():''
                    var soao = ""

                    for(let i=0;i<ds.length;i++){
                        if(ds[i].daucuoi_id==1){
                            //Nếu là điểm đầu
                            this.diachidau_id=ds[i].diachi_id
                            soao+=ds[i].ma_lt?ds[i].ma_lt.toString():''+','
                        }else{
                            this.diachicuoi_id=ds[i].diachi_id
                            soao+=ds[i].ma_lt?ds[i].ma_lt.toString():''+','
                        }
                    }

                    this.$refs.thongTinPhieuYeuCau.input_soao=soao.substring(0, soao.length-1)
                    

                    await this.HienThiTTDiaChi_TSL()

                    for(let i=0;i<ds.length;i++){
                        if(ds[i].daucuoi_id==1){
                            this.$refs.kenhThuRieng.lcapdau_selected=ds[i].loaicap_id?ds[i].loaicap_id:null
                            this.$refs.kenhThuRieng.lcapdau_moi_selected=ds[i].loaicap_id?ds[i].loaicap_id:null
                            this.$refs.kenhThuRieng.input_diachi_dau=ds[i].diachi?ds[i].diachi.toString():''
                            this.$refs.kenhThuRieng.input_diachi_dau_moi=ds[i].diachi?ds[i].diachi.toString():''
                            this.ma_tb_dau=ds[i].ma_tb_sub?ds[i].ma_tb_sub.toString():''

                            this.Lng_DD=ds[i].kinhdo_ld?ds[i].kinhdo_ld:0
                            this.Lat_DD=ds[i].vido_ld?ds[i].vido_ld:0

                            this.donvi_dau_id=Number(await this.fn_map_db_thuebao(thuebao_id, 6, '-1'))
                            this.ds_loaidv_dau=await this.sp_lay_ds_donvi_theo_loaidv_v5(this.donvi_dau_id, LOAI_DV.TRAM_VT)
                            let tram_dau=await this.sp_lay_ds_tramtc_thuebao(thuebao_id, LOAI_DV.TRAM_VT, 1)
                            if(tram_dau.length>0){
                                this.tram_dv_dau=tram_dau[0].donvi_id
                            }else{
                                this.tram_dv_dau=0
                            }

                        }else{
                            this.$refs.kenhThuRieng.lcapcuoi_selected=ds[i].loaicap_id?ds[i].loaicap_id:null
                            this.$refs.kenhThuRieng.lcapcuoi_moi_selected=ds[i].loaicap_id?ds[i].loaicap_id:null
                            this.$refs.kenhThuRieng.input_diachi_cuoi=ds[i].diachi?ds[i].diachi.toString():''
                            this.$refs.kenhThuRieng.input_diachi_cuoi_moi=ds[i].diachi?ds[i].diachi.toString():''

                            this.ma_tb_cuoi=ds[i].ma_tb_sub?ds[i].ma_tb_sub.toString():''
                            this.Lng_DC=ds[i].kinhdo_ld?ds[i].kinhdo_ld:0
                            this.Lat_DC=ds[i].vido_ld?ds[i].vido_ld:0

                            this.donvi_cuoi_id=Number(await this.fn_map_db_thuebao(thuebao_id, 7, '-1'))
                            this.ds_loaidv_cuoi=await this.sp_lay_ds_donvi_theo_loaidv_v5(this.donvi_cuoi_id, LOAI_DV.TRAM_VT)
                            let tram_cuoi=await this.sp_lay_ds_tramtc_thuebao(thuebao_id, LOAI_DV.TRAM_VT, 2)
                            if(tram_cuoi.length>0){
                                this.tram_dv_cuoi=tram_cuoi[0].donvi_id
                            }else{
                                this.tram_dv_cuoi=0
                            }
                        }
                    }
                }

            }else if(dichvuvt_id==DichVuVienThong.IMS){
                //lay_ds_danhba_ims
                let ds=await this.lay_ds_danhba_ims(thuebao_id)
                console.log('HienThiTTMoRong_DB DS_IMS', ds)
                if(ds.length>0){
                    await this.$refs.ims.HienThiTTMoRong_DB(ds[0])
                }
            }
        },
        async LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id){
            this.isdn_sochinh = false
            this.matb_isdn_chinh = ""
            this.ds_isdn = await this.lay_ds_isdn_theo_thuebao_id(thuebao_id)

            for(let i=0; i<this.ds_isdn.length; i++){
                if(this.ds_isdn[i].thuebao_id!=null&&Number(this.ds_isdn[i].thuebao_id)==thuebao_id){
                    this.isdn_sochinh = true
                    await this.$refs.coDinh.ISDN_LOAISO('Số ISDN chính', LOAISO_ISDN.SOCHINH)
                }else{
                    await this.$refs.coDinh.ISDN_LOAISO('Số ISDN phụ', LOAISO_ISDN.SOPHU)
                }
                if(this.ds_isdn[i].thuebao_cha_id==null||this.ds_isdn[i].thuebao_cha_id==''){
                    this.matb_isdn_chinh=this.ds_isdn[i].ma_tb!=null?this.ds_isdn[i].ma_tb:''
                }
            }
        },
        async HienThiTTDiaChi_TSL(){
            //fn_lay_tt_hienthittdiachi_tsl_cdlhtb
            let result=await this.fn_lay_tt_hienthittdiachi_tsl_cdlhtb(JSON.stringify({
                DIACHIDAU_ID:this.diachidau_id,
                DIACHICUOI_ID:this.diachicuoi_id
            }))
            // 
            if(result!=null&&result.ERROR_CODE==1){
                let diachiDau=result.RESULT.DIACHIDAU
                if(diachiDau.length>0){
                    this.diachidau_id=diachiDau[0].DIACHI_ID?diachiDau[0].DIACHI_ID:0
                    this.quandau_id=diachiDau[0].QUAN_ID?diachiDau[0].QUAN_ID:0
                    this.phuongdau_id=diachiDau[0].PHUONG_ID?diachiDau[0].PHUONG_ID:0
                    this.phodau_id=diachiDau[0].PHO_ID?diachiDau[0].PHO_ID:0
                    this.ngodau_id=0
                    this.sonhadau=diachiDau[0].SONHA?diachiDau[0].SONHA.toString():0
                }
                let diachiCuoi=result.RESULT.DIACHICUOI
                if(diachiCuoi.length>0){
                    this.diachicuoi_id = diachiCuoi[0].DIACHI_ID?diachiCuoi[0].DIACHI_ID:0
                    this.quancuoi_id = diachiCuoi[0].QUAN_ID?diachiCuoi[0].QUAN_ID:0
                    this.phuongcuoi_id = diachiCuoi[0].PHUONG_ID?diachiCuoi[0].PHUONG_ID:0
                    this.phocuoi_id=diachiCuoi[0].PHO_ID?diachiCuoi[0].PHO_ID:0
                    this.ngocuoi_id=0
                    this.sonhacuoi=diachiCuoi[0].SONHA?diachiCuoi[0].SONHA.toString():0
                }
            }
        },
        async LoadKM(){
            this.dtienkmld=0
            this.dvatkmld=0

            this.dtientbi=0
            this.dvattbi=0

            this.dtien_kmtb=0
            this.dvat_kmtb=0

            this.dtienkmdc=0
            this.dvatkmdc=0

            this.dtiendatcoc_tt=0
            this.dvatdatcoc_tt=0

            this.dtiendatcoc_km=0
            this.dvatdatcoc_km=0

            this.ds_tien_khoanmuc=await this.sp_lay_tienkhoanmuc_theo_loaihd(LoaiHopDong.CHUYENDOI_LH)

        },
        async acceptPopupCTMuaThietBi(ds){
            this.dsCTM_TBI=[]
            if(ds.length<=0){
                return
            }
            for(let i=0; i<ds.length;i++){
                let item=ds[i]
                this.dsCTM_TBI.push({
                    LOAITBI_ID:item.loaitbi_id,
                    SOLUONG:item.soluong,
                    TIEN:item.tien,
                    VAT:item.vat,
                    TIEN_KM:item.tien_km,
                    VAT_KM:item.vat_km,
                    TIEN_TRATRUOC:item.tien_tratruoc,
                    VAT_TRATRUOC:item.vat_tratruoc,
                    TIEN_TRAGOP:item.tien_tragop,
                    VAT_TRAGOP:item.vat_tragop,
                    SERIAL:item.serial,
                    TYLE_VAT:item.tyle_vat,
                    TYLE_VAT_ID:item.tyle_vat_id,
                    SL_CHA:item.sl_cha,
                    BLOCK_TIEP:item.block_tiep,
                    GIA_BLOCK_TIEP:item.gia_block_tiep,
                    VAT_BLOCK_TIEP:item.vat_block_tiep,
                    BLOCK_DAU:item.block_dau,
                    TIEN_THUE:item.tien_thue,
                    TONG_TIEN:item.tong_tien,
                    TONG_THUE:item.tong_thue,
                    MA_TBI:item.ma_tbi,
                    LOAI_TBI:item.loai_tbi
                })
            }
            console.log('acceptPopupCTMuaThietBi', this.dsCTM_TBI)
            await this.HT_ThietBi()
        },
        async acceptTienKM(ds_tien_km){
            console.log('acceptTienKM', ds_tien_km)
            this.ds_tien_khoanmuc=[]
            for(let i=0;i<ds_tien_km.length;i++){
                this.ds_tien_khoanmuc.push({
                    khoanmuctt_id: ds_tien_km[i].khoanmuctt_id.toString(),
                    tien: ds_tien_km[i].tien.toString()!=''?ds_tien_km[i].tien.toString():'0',
                    vat: ds_tien_km[i].vat.toString()!=''?ds_tien_km[i].vat.toString():'0'
                })
            }
            console.log('ds_tien_khoanmuc', this.ds_tien_khoanmuc)
            //Handle
            for(let i=0; i<this.ds_tien_khoanmuc.length; i++){
                let item=this.ds_tien_khoanmuc[i]
                if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_TDLOAIHINHTB.toString()){
                    this.dtienld=Number(item.tien)
                    this.vat_ld=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_KM_LAPDAT.toString()){
                    this.dtienkmld=Number(item.tien)
                    this.dvatkmld=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_MUATHIETBI.toString()){
                    this.dtientbi=Number(item.tien)
                    this.dvattbi=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_KM_THIETBI.toString()){
                    this.dtien_kmtb=Number(item.tien)
                    this.dvat_kmtb=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_DATCOC.toString()){
                    this.dtiendatcoc_tt=Number(item.tien)
                    this.dvatdatcoc_tt=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_KM_DATCOC.toString()){
                    this.dtienkmdc=Number(item.tien)
                    this.dvatkmdc=Number(item.vat)
                }else if(item.khoanmuctt_id==KHOANMUC_TT.KMTT_DATCOC_KM.toString()){
                    this.dtiendatcoc_km=Number(item.tien)
                    this.dvatdatcoc_km=Number(item.vat)
                }
                //
                await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt);
                await this.LayTongTien_HDTB()
            }


        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        async thanhtoan(){
            if(this.hdkh_id==0){
                this.$toast.error('Chưa có thông tin hợp đồng!')
                return
            }
            //kiemtra_thanhtoan_datmoi_lh
            let result=await this.kiemtra_thanhtoan_datmoi_lh(this.hdkh_id)
            if(result=='0'){
                this.$toast.error('Hợp đồng chưa có thông tin liên hệ, bạn phải bổ sung thông tin liên hệ trước khi thanh toán !')
                return
            }
            //Payment
            let query={
                magd: this.$refs.thongTinPhieuYeuCau.input_magd.trim(),
            }
            let routeData = this.$router.resolve({name: 'Payment', query: query});
            window.open(routeData.href, '_blank')
            // f.HienThiTTHopDongKH();
        },
        async hoso(){
            if(this.hdkh_id!=0){
                this.popupInHopDong_Param.hdkh_id  = this.hdkh_id
                this.popupInHopDong_Param.ma_gd  = this.$refs.thongTinPhieuYeuCau.input_magd.trim()
                this.popupInHopDong_Param.ngay_yc  = this.$refs.thongTinPhieuYeuCau.ngay_yc
                this.popupInHopDong_Param.ngay_lhd  = this.$refs.thongTinKhachHang.ngay_lhd 
                setTimeout(()=>{
                    this.$refs.popupInHoSo.showModal()
                }, 500)
            }else{
                this.$toast.error('Chưa có thông tin hợp đồng!')
            }
        },
        async goidadv(){
            let query={
                ma_gd: this.$refs.thongTinPhieuYeuCau.input_magd.trim(),
            }
            let routeData = this.$router.resolve({name: 'DangKyGoiDaDichVu', query: query});
            window.open(routeData.href, '_blank')
        },
        async HT_DS_KhuyenMai_Combobox(){
            //
            var itocdo_id = 0
            var itrangbi_id = 0//Default: KH tự trang bị
            var imuccuoc_id = 0
            var iloaitb_id = 0
            var idoituong_id = 0

            let thongtinyeucau= await this.$refs.thongTinPhieuYeuCau.getThongTinPhieu()
            

            let mucuoc_tb_id=thongtinyeucau.muoccuoc_tb_selected
            let mucuoc_tb=thongtinyeucau.ds_muccuoc_tb.find(x=>x.mucuoctb_id==mucuoc_tb_id)

            if(thongtinyeucau.ds_kieu_yc.length==0){
                return
            }
            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                let thongtincodinh= await this.$refs.coDinh.getThongTin()
                if(thongtincodinh.ds_doituong.length==0){
                    return
                }
                idoituong_id=thongtincodinh.doituong_moi_selected!=null?thongtincodinh.doituong_moi_selected:0
                itocdo_id = 0;
                if(thongtincodinh.ds_trangbi.length==0){
                    return
                }
                itrangbi_id = thongtincodinh.trangbi_moi_selected!=null?Number(thongtincodinh.trangbi_moi_selected):0

                if(thongtincodinh.loaihinh_tb_moi_selected!=null&&(mucuoc_tb==undefined||!mucuoc_tb.tenmuccuoc.includes('.'))){
                    iloaitb_id=Number(thongtincodinh.loaihinh_tb_moi_selected)
                }

            }else if(this.dichvu_vt_selected==DichVuVienThong.GPHONE){
                // itocdo_id = 0;
                // if (cboTrangBiGPmoi.Items.Count <= 0) return;
                // itrangbi_id = Convert.ToInt32(cboTrangBiGPmoi.SelectedValue);
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                if(this.$refs.diDong.ds_goicuoc_dd_moi.length==0){
                    return
                }
                itocdo_id=this.$refs.diDong.goicuoc_dd_moi_selected!=null?Number(this.$refs.diDong.goicuoc_dd_moi_selected):0
                if(this.$refs.diDong.ds_trangbi.length==0){
                    return
                }
                itrangbi_id=this.$refs.diDong.trangbi_moi_selected!=null?Number(this.$refs.diDong.trangbi_moi_selected):0
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                if(this.$refs.bangRongCoDinh.ds_tocdo.length==0){
                    return
                }
                if(this.$refs.bangRongCoDinh.ds_trangbi.length==0){
                    return
                }
                if(this.$refs.bangRongCoDinh.ds_muccuoc.length==0){
                    return
                }
                itocdo_id = this.$refs.bangRongCoDinh.tocdo_moi_selected!=null?Number(this.$refs.bangRongCoDinh.tocdo_moi_selected):0
                itrangbi_id = this.$refs.bangRongCoDinh.trangbi_moi_selected!=null?Number(this.$refs.bangRongCoDinh.trangbi_moi_selected):0
                imuccuoc_id = this.$refs.bangRongCoDinh.muccuoc_moi_selected!=null?Number(this.$refs.bangRongCoDinh.muccuoc_moi_selected):0
                if(this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected!=null&&(mucuoc_tb==undefined||!mucuoc_tb.tenmuccuoc.includes('.'))){
                    iloaitb_id=Number(this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected)
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                if(this.$refs.megawan.ds_tocdo.length==0){
                    return
                }
                itocdo_id=this.$refs.megawan.tocdo_cir_moi_selected!=null?this.$refs.megawan.tocdo_cir_moi_selected:0
                if(this.$refs.megawan.ds_trangbi.length==0){
                    return
                }
                itrangbi_id=this.$refs.megawan.trangbi_moi_selected!=null?this.$refs.megawan.trangbi_moi_selected:0
                if(this.$refs.megawan.loaihinh_tb_moi_selected!=null&&(mucuoc_tb==undefined||!mucuoc_tb.tenmuccuoc.includes('.'))){
                    iloaitb_id=Number(this.$refs.megawan.loaihinh_tb_moi_selected)
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                if(this.$refs.kenhThuRieng.ds_tocdo.length==0){
                    return
                }
                itocdo_id = this.$refs.kenhThuRieng.tocdo_moi_selected!=null?Number(this.$refs.kenhThuRieng.tocdo_moi_selected):0
                if(this.$refs.kenhThuRieng.ds_trangbi.length==0){
                    return
                }
                itrangbi_id=this.$refs.kenhThuRieng.trangbi_moi_selected!=null?this.$refs.kenhThuRieng.trangbi_moi_selected:0
                if(this.$refs.kenhThuRieng.loaihinh_tb_moi_selected!=null&&(mucuoc_tb==undefined||!mucuoc_tb.tenmuccuoc.includes('.'))){
                    iloaitb_id=Number(this.$refs.kenhThuRieng.loaihinh_tb_moi_selected)
                }
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                if(this.$refs.ims.loaihinh_tb_moi_selected!=null&&(mucuoc_tb==undefined||!mucuoc_tb.tenmuccuoc.includes('.'))){
                    iloaitb_id=Number(this.$refs.ims.loaihinh_tb_moi_selected)
                }
            }

            //load ds khuyến mãi
            let dsKhuyenMai=await this.lay_khuyenmai_hdtb({
                vloaitb_id: iloaitb_id,
                vkieuld_id: thongtinyeucau.kieu_yc_selected!=null?Number(thongtinyeucau.kieu_yc_selected):0,
                vphuongld_id: this.dataDiaChiLD.phuong_id,
                vkhoanmuctt_id: KHOANMUC_TT.KMTT_DATMOI,
                vphold_id: this.dataDiaChiLD.pho_id,
                vapld_id: this.dataDiaChiLD.ap_id,
                vkhuld_id: this.dataDiaChiLD.khu_id,
                vtrangbi_id: itrangbi_id,
                vdoituong_id: idoituong_id,
                vtocdo_id: itocdo_id,
                vmuccuoc_id: imuccuoc_id,
                vloaikm: 0
            })

            await this.$refs.thongTinPhieuYeuCau.khoiTaoDsKhuyenMai(dsKhuyenMai)
            //load ds trả trước
            let dsTraTruoc=await this.lay_khuyenmai_hdtb({
                vloaitb_id: iloaitb_id,
                vkieuld_id: thongtinyeucau.kieu_yc_selected!=null?Number(thongtinyeucau.kieu_yc_selected):0,
                vphuongld_id: this.dataDiaChiLD.phuong_id,
                vkhoanmuctt_id: KHOANMUC_TT.KMTT_DATMOI,
                vphold_id: this.dataDiaChiLD.pho_id,
                vapld_id: this.dataDiaChiLD.ap_id,
                vkhuld_id: this.dataDiaChiLD.khu_id,
                vtrangbi_id: itrangbi_id,
                vdoituong_id: idoituong_id,
                vtocdo_id: itocdo_id,
                vmuccuoc_id: imuccuoc_id,
                vloaikm: 1
            })
            await this.$refs.thongTinPhieuYeuCau.khoiTaoDsTraTruoc(dsTraTruoc)
        },
        async sp_lay_tienkhoanmuc_theo_loaihd(loaihd_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_tienkhoanmuc_theo_loaihd(this.axios, loaihd_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async ds_thamso_md(kieu){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.ds_thamso_md(this.axios, kieu)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_danhmuc_cdlh_tt_chung(chuoi){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_danhmuc_cdlh_tt_chung(this.axios, chuoi)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async lay_ds_loaihinh_tb(){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_loaihinh_tb(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async kiemtra_quyen_nd(quyen_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_quyen_nd(this.axios, quyen_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async thongtin_nguoidung(){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.thongtin_nguoidung(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_khuyenmai_hdtb(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_khuyenmai_hdtb(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_kieu_ld_lhtb(loaihd_id, loaitb_id, tinhkhac){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_kieu_ld_lhtb(this.axios, loaihd_id, loaitb_id, tinhkhac)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_ht_lhtb_moi_combobox(kieuld_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_ht_lhtb_moi_combobox(this.axios, kieuld_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        // Begin map_id
        async fn_map_db_thuebao(thuebao_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_db_thuebao(this.axios, thuebao_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_bd_thuebao(param, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_bd_thuebao(this.axios, param, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_goicuoc_dd(goicuoc_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_goicuoc_dd(this.axios, goicuoc_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_tocdo_adsl(tocdo_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_tocdo_adsl(this.axios, tocdo_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_db_adsl(thuebao_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_db_adsl(this.axios, thuebao_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_hdtb_dd(hdtb_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_hdtb_dd(this.axios, hdtb_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_muccuoc_tb(muccuoctb_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_muccuoc_tb(this.axios, muccuoctb_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_khuyenmai(khuyenmai_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_khuyenmai(this.axios, khuyenmai_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data!='-1'){
                        return response.data.data 
                    }
                    return defaultValue
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_hd_thuebao(hdtb_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_hd_thuebao(this.axios, muccuoctb_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_loaihinh_tb(loaitb_id, type, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_loaihinh_tb(this.axios, loaitb_id, type)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_bancheo_muccuoc_tb(muccuoctb_id, type, tinh_tc, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_bancheo_muccuoc_tb(this.axios, muccuoctb_id, type, tinh_tc)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_bancheo_db_thuebao(thuebao_id, type, tinh_tc, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_bancheo_db_thuebao(this.axios, thuebao_id, type, tinh_tc)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_bancheo_tocdo_adsl(tocdo_id, type, tinh_tc, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_bancheo_tocdo_adsl(this.axios, tocdo_id, type, tinh_tc)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async fn_map_bancheo_db_adsl(thuebao_id, type, tinh_tc, defaultValue){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_map_bancheo_db_adsl(this.axios, thuebao_id, type, tinh_tc)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        // End replace map
        async sp_lay_ctkm_loai_tbi(ds_ctkm_id, loaitbi_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_ctkm_loai_tbi(this.axios, ds_ctkm_id, loaitbi_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_ds_tragop_thietbi(ds_ctkm_id, loaitbi_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_ds_tragop_thietbi(this.axios, ds_ctkm_id, loaitbi_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_tien_dc_conlai(thuebao_id, loaitb_id, thang_thoai){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_tien_dc_conlai(this.axios, thuebao_id, loaitb_id, thang_thoai)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async kiemtra_thoihan_khuyenmai(khuyenmai_id, ngay_yc){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_thoihan_khuyenmai(this.axios, khuyenmai_id, ngay_yc)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async sp_lay_tocdo_adsl(loaitb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_tocdo_adsl(this.axios, loaitb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_ht_tocdo_adsl_combobox_byloaitb(loaitb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_ht_tocdo_adsl_combobox_byloaitb(this.axios, loaitb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_mucuoc_tb_v2(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_mucuoc_tb_v2(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_tocdo_thuc_adsl_theo_tocdo_id(tocdo_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_tocdo_thuc_adsl_theo_tocdo_id(this.axios, tocdo_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_cuoctk_cuoctc(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_cuoctk_cuoctc(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_tien_khoanmuc_ld(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_tien_khoanmuc_ld(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async kiemtra_thanhtoan_datmoi_lh(hdkh_id){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_thanhtoan_datmoi_lh(this.axios, hdkh_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return '0'
                }
            }catch(e){
                this.loading(false)
                return '0'
            }
        },
        async lay_tinh_theo_matb(matb){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_tinh_theo_matb(this.axios, matb)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_ds_db_thuebao_bancheo_theo_matb(matb){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_ds_db_thuebao_bancheo_theo_matb(this.axios, matb)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_tinhtc_id_tsl(dichvuvt_id, ma_tb, tinhthicong_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_tinhtc_id_tsl(this.axios, dichvuvt_id, ma_tb)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_thuebaoid_thicong_theo_matb(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_thuebaoid_thicong_theo_matb(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_hopdong_theo_ma_tb(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_hopdong_theo_ma_tb(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_danhba_theo_matb(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_danhba_theo_matb(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async kiemtra_thuebao_lap_hopdong_khac(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_thuebao_lap_hopdong_khac(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async lay_ds_chitiet_km_theo_tbid(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_chitiet_km_theo_tbid(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_tinh_theo_thuebaoid(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_tinh_theo_thuebaoid(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
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
        async lay_danhba_theo_thuebaoid(tinhthicong_id, thuebao_id, dichvuvt_id, donvi_id, loaihdbc){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_danhba_theo_thuebaoid(this.axios, tinhthicong_id, thuebao_id, dichvuvt_id, donvi_id, loaihdbc)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_cd_theo_tbid(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_cd_theo_tbid(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_cd_theo_tbid_bancheo(thuebao_id, tinh_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_cd_theo_tbid_bancheo(this.axios, thuebao_id, tinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_danhba_ims(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_danhba_ims(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_ims_theo_tbid_bancheo(thuebao_id, tinh_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_ims_theo_tbid_bancheo(this.axios, thuebao_id, tinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_danhba_megawan(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_danhba_megawan(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_megawan_theo_tbid_bancheo(thuebao_id, tinh_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_megawan_theo_tbid_bancheo(this.axios, thuebao_id, tinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_tinh_thicong_combobox(){
            try{
                let response = await this.axios.post('/web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox',{})
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_danhba_tsl(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_danhba_tsl(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_donvi(thuebao_id){
            try{
                let input = {
                    p_thuebao_id: thuebao_id
                }
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_danhsach_donvi(this.axios, input)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_tsl_theo_tbid_bancheo(thuebao_id, tinh_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_tsl_theo_tbid_bancheo(this.axios, thuebao_id, tinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_adsl_theo_tbid(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_adsl_theo_tbid(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_adsl_theo_tbid_bancheo(thuebao_id, tinh_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_adsl_theo_tbid_bancheo(this.axios, thuebao_id, tinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async fn_lay_khachhangid_thicong(khachhang_id, tinh_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_lay_khachhangid_thicong(this.axios, khachhang_id, tinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async lay_thongso_thuebao_thuebaoid(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_thongso_thuebao_thuebaoid(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_thongso_thuebao_thuebaoid_bancheo(thuebao_id, tinh_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_thongso_thuebao_thuebaoid_bancheo(this.axios, thuebao_id, tinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_ds_khuvuc_theo_thuebaoid(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_ds_khuvuc_theo_thuebaoid(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_diachi_theo_dbtbid(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_diachi_theo_dbtbid(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_isdn_theo_thuebao_id(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_isdn_theo_thuebao_id(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async fn_lay_tt_hienthittdiachi_tsl_cdlhtb(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_lay_tt_hienthittdiachi_tsl_cdlhtb(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async fn_lay_text_huongketnoi_v2(huong_kn){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_lay_text_huongketnoi_v2(this.axios, huong_kn)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                return ''
            }
        },
        async sp_lay_ds_donvi_theo_loaidv_v5(donvi_id, loaidv_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_ds_donvi_theo_loaidv_v5(this.axios, donvi_id, loaidv_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_ds_tramtc_thuebao(thuebao_id, loaidv_id, kieudv_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_ds_tramtc_thuebao(this.axios, thuebao_id, loaidv_id, kieudv_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_danhba_theo_matb_bancheo(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_danhba_theo_matb_bancheo(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async xoa_hopdong_thuebao(hdtbId){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.xoa_hopdong_thuebao(this.axios, hdtbId)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return {
                        code:1,
                        message:'Xóa hợp đồng thuê bao thành công'
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        return {
                            code:0,
                            message:response.data.message
                        }
                    }else{
                        return {
                            code:0,
                            message:'Đã xảy ra lỗi, xóa hợp đồng thuê bao không thành công'
                        }
                    }
                    
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return {
                        code:0,
                        message:e.data.message
                    }
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return {
                        code:0,
                        message:e.response.data.message
                    }
                }else{
                    return {
                        code:0,
                        message:'Đã xảy ra lỗi, xoá dữ liệu không thành công'
                    }
                }
                
            }
        },
        async lay_ds_hopdong_theo_ma_gd(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_hopdong_theo_ma_gd(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async xoa_hdbc_hdtb(hdtbid, loaihd_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.xoa_hdbc_hdtb(this.axios, hdtbid, loaihd_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xóa hợp đồng thuê bao bán chéo không thành công')
                    }
                    return false
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xóa hợp đồng thuê bao bán chéo không thành công')
                }
                return false
            }
        },
        async xoa_hdbc(hdkh_id, loaihd_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.xoa_hdbc(this.axios, hdkh_id, loaihd_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xóa hợp đồng thuê bao bán chéo không thành công')
                    }
                    return false
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xóa hợp đồng thuê bao bán chéo không thành công')
                }
                return false
            }
        },
        async xoa_hdkh_v2(hdkh_id, loaihd_id){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.xoa_hdkh_v2(this.axios, hdkh_id, loaihd_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return {
                        code:1,
                        message:'Đã xóa hợp đồng chuyển đổi loại hình thành công trên hệ thống của viễn thông tỉnh'
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        return {
                            code:0,
                            message:response.data.message
                        }
                    }else{
                        return {
                            code:0,
                            message:'Đã xảy ra lỗi, xóa hợp đồng chuyển đổi loại hình không thành công'
                        }
                    }
                    
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return {
                        code:0,
                        message:e.data.message
                    }
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return {
                        code:0,
                        message:e.response.data.message
                    }
                }else{
                    return {
                        code:0,
                        message:'Đã xảy ra lỗi, xóa hợp đồng chuyển đổi loại hình không thành công'
                    }
                }
                
            }
        },
        async fn_lay_tt_ht_hdkh_cdlhtb_bancheo(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_lay_tt_ht_hdkh_cdlhtb_bancheo(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async lay_thongso_thuebao_hdbc(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_thongso_thuebao_hdbc(this.axios, hdtb_id)
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
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_loaiip_theo_hdtb_id(this.axios, hdtb_id)
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
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_kieunt_theo_hdtb_id(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_hdtb_tsl_theo_hdtb_id(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_hdtb_tsl_theo_hdtb_id(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_diachi_theo_hdtb_id(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_diachi_theo_hdtb_id(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_tt_ketcuoi_bancheo(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_tt_ketcuoi_bancheo(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_chitiet_km_theo_hdtb(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_chitiet_km_theo_hdtb(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result=response.data.data
                    //uppercase key json
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    return result
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_ds_khuvuc_theo_hdtb_id_bancheo(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_ds_khuvuc_theo_hdtb_id_bancheo(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_tien_hd_km_theo_hdtb(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_tien_hd_km_theo_hdtb(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result=response.data.data
                    //uppercase key json
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    return result
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_chitiet_km_theo_hdtb_id(hdtb_id, loai_km){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_chitiet_km_theo_hdtb_id(this.axios, hdtb_id, loai_km)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result=response.data.data
                    //uppercase key json
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    return result
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_mua_tbi_lhd(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_mua_tbi_lhd(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result=response.data.data
                    //uppercase key json
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    return result
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_tienhd_km_theo_hdtb(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_tienhd_km_theo_hdtb(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data&&response.data.data.rs1) {
                    return response.data.data.rs1
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_hopdong_thuebao_codinh(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_hopdong_thuebao_codinh(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_hopdong_thuebao_adsl(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_hopdong_thuebao_adsl(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_hopdong_thuebao_mgwan(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_hopdong_thuebao_mgwan(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_hdtb_tsl_theo_hdtb_id(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_hdtb_tsl_theo_hdtb_id(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_hopdong_thuebao_ims(hdtb_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_hopdong_thuebao_ims(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async get_keys(keyname){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async get_key_bancheo(keyname, tinh_tc){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.get_key_bancheo(this.axios, keyname, tinh_tc)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async sp_lay_ds_hdtb_dv_kieudv(hdtb_id, loaidv_id, kieudv_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_ds_hdtb_dv_kieudv(this.axios, hdtb_id, loaidv_id, kieudv_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async fn_tt_taodulieu_hdkh_cdlhtb(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_tt_taodulieu_hdkh_cdlhtb(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async fn_kt_thaotac(luong_id, code){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_kt_thaotac(this.axios, luong_id, code)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async sp_ht_danhmuc_dv_tdlhtb(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_ht_danhmuc_dv_tdlhtb(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.p_js_returnds) {
                    return JSON.parse(response.data.data.p_js_returnds)
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async ur2_1_035_038_temp(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.ur2_1_035_038_temp(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_donvi_theo_loaidv_diachi_ld_v1(data){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_donvi_theo_loaidv_diachi_ld_v1(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_cd_theo_tbid_pro(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_cd_theo_tbid_pro(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_db_ims_theo_tbid_pro(thuebao_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_db_ims_theo_tbid_pro(this.axios, thuebao_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_dauso_ims_theo_tinh(tentat){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_dauso_ims_theo_tinh(this.axios, tentat)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async fn_tt_taodulieu_hdtb_adsl_cdlhtb(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_tt_taodulieu_hdtb_adsl_cdlhtb(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result=JSON.parse(response.data.data)
                    if(result.ERROR_CODE==1){
                        return result.RESULT
                    }else{
                        return null
                    }
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async fn_tt_taodulieu_hdtb_mgwan_cdlhtb(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_tt_taodulieu_hdtb_mgwan_cdlhtb(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async sp_lay_tinh_tc_theo_tinh_id(tinh_tc){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.sp_lay_tinh_tc_theo_tinh_id(this.axios, tinh_tc)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async kiemtra_makh_matt_magd(ma_kh, loaihd_id, kieu){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_makh_matt_magd(this.axios, ma_kh, loaihd_id, kieu)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async kiemtra_tb_datmoi_isdn(id_sochinh, loaitb_id, loai){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_tb_datmoi_isdn(this.axios, id_sochinh, loaitb_id, loai)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async kiemtra_thaydoi_goi_dadv_1(thuebao_id, loaitb_id, tocdo_id, muccuoc_id){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_thaydoi_goi_dadv_1(this.axios, thuebao_id, loaitb_id, tocdo_id, muccuoc_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 'Lỗi kiểm tra đăng ký gói đa dịch vụ'
                }
            }catch(e){
                return 'Lỗi kiểm tra đăng ký gói đa dịch vụ'
            }
        },
        async lay_ds_thamso_md_mats(ma_ts){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.lay_ds_thamso_md_mats(this.axios, ma_ts)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async kiemtra_danhba_khi_themmoi(ma_tb, khachhang_id, thuebao_id, dichvuvt_id){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_danhba_khi_themmoi(this.axios, ma_tb, khachhang_id, thuebao_id, dichvuvt_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
            }
        },
        async layhuonggiao_tiepnhan_v2(data){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.layhuonggiao_tiepnhan_v2(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async layhuonggiao_tiepnhan(data){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.layhuonggiao_tiepnhan(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async fn_ins_update_hd_cdlh_bancheo(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_ins_update_hd_cdlh_bancheo(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result=JSON.parse(response.data.data)
                    if(result.ERROR_CODE==1){
                        return 1
                    }else{
                        return result.MESSAGE
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi cập nhật chuyển đổi loại hình không thành công!'
                    }
                }
            }catch(e){
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi cập nhật chuyển đổi loại hình không thành công!'
                }
            }
        },
        async fn_themmoi_hdbancheo_biendong(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_themmoi_hdbancheo_biendong(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                    // let result=JSON.parse(response.data.data)
                    // if(result.ERROR_CODE==1){
                    //     return 1
                    // }else{
                    //     return result.MESSAGE
                    // }
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi cập nhật chuyển đổi loại hình không thành công!'
                    }
                }
            }catch(e){
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi cập nhật chuyển đổi loại hình không thành công!'
                }
            }
        },
        async fn_capnhat_hdbancheo_biendong(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_capnhat_hdbancheo_biendong(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                    // let result=JSON.parse(response.data.data)
                    // if(result.ERROR_CODE==1){
                    //     return 1
                    // }else{
                    //     return result.MESSAGE
                    // }
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi cập nhật chuyển đổi loại hình không thành công!'
                    }
                }
            }catch(e){
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi cập nhật chuyển đổi loại hình không thành công!'
                }
            }
        },
        async update_huonggiao_tiepnhan(hdtb_id, huonggiao_id){
            try{
                this.loading(true)
                await ChangeSubsTypeCrossSaleAPI.update_huonggiao_tiepnhan(this.axios, hdtb_id, huonggiao_id)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async sinh_malt(data){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.sinh_malt(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async kiemtra_hdtb_khi_themmoi(data){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_hdtb_khi_themmoi(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
            }
        },
        async kiemtra_tb_cung_makh_tb_pl(ma_gd, thuebao_id){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeCrossSaleAPI.kiemtra_tb_cung_makh_tb_pl(this.axios, ma_gd, thuebao_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
            }
        },
        async fn_themtb_cdlh_bancheo(ds_para){
            try{
                let response = await ChangeSubsTypeCrossSaleAPI.fn_themtb_cdlh_bancheo(this.axios, ds_para)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    //return Number(response.data.data)
                    let result=JSON.parse(response.data.data)
                    if(result.ERROR_CODE==1){
                        return 1
                    }else{
                        return result.MESSAGE
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi cập nhật chuyển đổi loại hình không thành công!'
                    }
                }
            }catch(e){
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi cập nhật chuyển đổi loại hình không thành công!'
                }
            }
        },
        async khoiTaoThamSo(){
            let ds_thamso=await this.ds_thamso_md(0)
            if(ds_thamso.length>0){
                //NHAP_THONGTIN_TINHCUOC
                if(ds_thamso.findIndex(x=>x.ma_ts.toString()=='NHAP_THONGTIN_TINHCUOC'&&x.ten_ts.toString()=='1')>-1){
                    this.thamso.nhap_tttc=1
                }
                //NHAP_TOCDO_THUC_ADSL
                if(ds_thamso.findIndex(x=>x.ma_ts.toString()=='NHAP_TOCDO_THUC_ADSL'&&x.ten_ts.toString()=='1')>-1){
                    this.thamso.nhap_tocdo_thuc_adsl=1
                }

                //KHONG_THAYDOI_DIACHI_LD
                if(ds_thamso.findIndex(x=>x.ma_ts.toString()=='KHONG_THAYDOI_DIACHI_LD'&&x.ten_ts.toString()=='1')>-1){
                    this.thamso.khong_thaydoi_diachi_ld=1
                }
                //KHONGBATBUOC_CTV
                if(ds_thamso.findIndex(x=>x.ma_ts.toString()=='KHONGBATBUOC_CTV'&&x.ten_ts.toString()=='1')>-1){
                    this.thamso.khong_batbuoc_ctv=1
                }
                //NHAP_KHUVUC_LHTB
                if(ds_thamso.findIndex(x=>x.ma_ts.toString()=='NHAP_KHUVUC_LHTB'&&x.ten_ts.toString()=='1')>-1){
                    this.thamso.nhap_nvkd=1
                }
                //BATBUOC_GIOITHIEU_LOAIHINH
                if(ds_thamso.findIndex(x=>x.ma_ts.toString()=='BATBUOC_GIOITHIEU_LOAIHINH'&&x.ten_ts.toString()=='1')>-1){
                    this.thamso.batbuoc_gioithieu_loaihinh=1
                }

                //BATBUOC_DK_TRATRUOC_LHTB
                if(ds_thamso.findIndex(x=>x.ma_ts.toString()=='BATBUOC_DK_TRATRUOC_LHTB')>-1){
                    let ts=ds_thamso.find(x=>x.ma_ts.toString()=='BATBUOC_DK_TRATRUOC_LHTB')
                    this.thamso.dk_tratruoc=1
                    this.thamso.nguoidung_boqua_tratruoc=Number(ts.ten_ts.toString())
                }

                this.thamso.TUDONG_SINH_SOAO=ds_thamso.findIndex(x=>x.ma_ts.toString()=='TUDONG_SINH_SOAO')>-1
                this.thamso.ts_sinhma_gd_theo_donvi=ds_thamso.findIndex(x=>x.ma_ts.toString()=='SINH_MA_GD_THEO_DV')>-1

            }
            console.log('Tham số: ', this.thamso)
        },
        async visibleToolStripMenuAndControl(visible){
            for (let i = 0; i < this.actions.length; i++) {
                if(this.actions[i].id!='goidadv'){
                    this.actions[i].visible=visible
                }
            }
            this.actions[this.actions.findIndex(x=>x.id=='sua_km')].visible=this.iKieu==2
            //Handle cho iKieu=1(Tra cứu) hoặc 2(Sửa KM)
            // Kiểm tra nếu không có quyền thay đổi tốc độ khi sửa km thì không cho sửa
            this.checkQuyen=await this.kiemtra_quyen_nd(925)
            if((this.iKieu==1||this.iKieu==2)&&this.checkQuyen==0){
                //cboDichVuVT.Enabled = visible;
                this.$refs.thongTinPhieuYeuCau.enabledDichVuVT=visible
                this.$refs.muaThietBi.enabledMuaTbi=visible
                this.$refs.thongTinKhachHang.enabledDiaChiTB=visible
                this.$refs.thongTinKhachHang.enabledDiaChiLD=visible
                this.$refs.thongTinPhieuYeuCau.disableKieuLD=!visible
                if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                    this.$refs.coDinh.visibleToolStripMenuAndControl(visible)
                }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                    
                }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                    this.$refs.bangRongCoDinh.visibleToolStripMenuAndControl(visible)
                }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                    this.$refs.ims.visibleToolStripMenuAndControl(visible)
                }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                    this.$refs.kenhThuRieng.visibleToolStripMenuAndControl(visible)
                }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                    this.$refs.megawan.visibleToolStripMenuAndControl(visible)
                }
            }
        },
        async initDuLieu(){
            //Bỏ qua handled router, mặc định task chỉ có thay đổi loại hình bán chéo
            this.iKenhTN=1
            this.iKieu=0
            //
            this.loading(true)
            this.ds_tien_khoanmuc=await this.sp_lay_tienkhoanmuc_theo_loaihd(LoaiHopDong.CHUYENDOI_LH)
            this.ds_tinh_tc=await this.lay_ds_tinh_thicong_combobox()
            //khởi tạo tham số
            await this.khoiTaoThamSo()
            //Khởi tạo loại hình tb
            //lay_ds_loaihinh_tb
            this.ds_loaihinh_tb=await this.lay_ds_loaihinh_tb()

            //Hiển thị Kiểu PL (cboKieu_HD)
            let result = await this.lay_danhmuc_cdlh_tt_chung('KIEU_HD|GOI_KT')
            if(result!=null&&result.kieu_hd){
                this.ds_kieu_pl=result.kieu_hd.sort((a, b)=>a.kieuhd_id-b.kieuhd_id)
                if(this.ds_kieu_pl.length>0){
                    this.kieu_pl_selected=this.ds_kieu_pl[0].kieuhd_id
                }
                // tên gói = null, select2 không load vào nên map về ''
                // {
                //     "goikt_id": -1,
                //     "ten_goi": null
                // }
                this.ds_goi_kt=result.goi_kt.map(x=>Object.assign(x, {
                    ten_goi:x.ten_goi?x.ten_goi:''
                }))
            }
            this.dichvuvt_id=this.dichvu_vt_selected

            await this.$refs.thongTinPhieuYeuCau.initDuLieu(this.ds_loaihinh_tb, this.ds_goi_kt)
            await this.$refs.thongTinKhachHang.initDuLieu()

            if(this.dichvu_vt_selected==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.initDuLieu(this.ds_loaihinh_tb)
                await this.$refs.coDinh.changeDoiTuongMoi()
            }else if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                //await this.$refs.diDong.initDuLieu(this.ds_loaihinh_tb)
            }else if(this.dichvu_vt_selected==DichVuVienThong.ADSL||this.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT){
                //băng rộng cố định
                await this.$refs.bangRongCoDinh.initDuLieu(this.ds_loaihinh_tb)
                await this.$refs.bangRongCoDinh.changeDoiTuongMoi()
                if(this.thamso.nhap_tocdo_thuc_adsl==0){
                    this.$refs.bangRongCoDinh.enabledCheckTocDoThuc=false
                }else{
                    this.$refs.bangRongCoDinh.enabledCheckTocDoThuc=true
                }
                this.$refs.bangRongCoDinh.checkTocDoThuc=false
            }else if(this.dichvu_vt_selected==DichVuVienThong.IMS){
                await this.$refs.ims.initDuLieu(this.ds_loaihinh_tb)
                await this.$refs.ims.changeDoiTuongMoi()
            }else if(this.dichvu_vt_selected==DichVuVienThong.TSL||this.dichvu_vt_selected==DichVuVienThong.NOIBO_2DIEM){
                await this.$refs.kenhThuRieng.initDuLieu(this.ds_loaihinh_tb)
                await this.$refs.kenhThuRieng.changeDoiTuongMoi()
            }else if(this.dichvu_vt_selected==DichVuVienThong.METRONET||this.dichvu_vt_selected==DichVuVienThong.MEGAWAN||this.dichvu_vt_selected==DichVuVienThong.NOIBO_DADIEM){
                await this.$refs.megawan.initDuLieu(this.ds_loaihinh_tb, this.dichvu_vt_selected)
                await this.$refs.megawan.changeDoiTuongMoi()
            }

            await this.$refs.muaThietBi.initDuLieu()
            await this.$refs.chiTietTienTheoKhoanMuc.initDuLieu()

            //call trigger changeLoaiHinh tb cũ
            await this.$refs.thongTinPhieuYeuCau.changeLoaiHinhTBCu()

            await this.HT_DS_KhuyenMai_Combobox()

            await this.visibleToolStripMenuAndControl(this.iKieu == 0)

            if(this.thamso.khong_thaydoi_diachi_ld==1){
                this.$refs.thongTinKhachHang.enabledBtnDiaChiLD(false)
            }else{
                this.$refs.thongTinKhachHang.enabledBtnDiaChiLD(true)
            }
            //
            this.loading(false)
            //Lấy thông tin người dùng
            this.tt_nd=await this.thongtin_nguoidung()
            console.log('tt_nd', this.tt_nd)



            //....

            this.loading(false)

        },
        layLoaiKenh(value){
            this.loaikenh_selected = value
        }
    },
    mounted(){ 
        this.setActiveActions(-1)
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    },
    created(){
        this.$root.$on("bv::modal::shown", (bvEvent, modalId) => {
            document.getElementsByClassName("modal-content").forEach(function (v) {
                v.removeAttribute("tabindex");
            });
        });
    },
    watch:{
        dichvu_vt_selected(val){
            if(this.dichvu_vt_selected==DichVuVienThong.DI_DONG){
                this.actions[this.actions.findIndex(x=>x.id=='thanhtoan')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='giaophieu')].active=false
            }else{
                this.actions[this.actions.findIndex(x=>x.id=='thanhtoan')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='giaophieu')].active=true
            }
        }
    }
}
</script>
<style>
.cdlh-disabled { 
    pointer-events: none;
    color:lightgray;
    border-color:lightgray;
}
.cdlh-top{
    top: 100px !important;
}
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
    white-space: normal !important;
    word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
    width: 100% !important;
}

.select2-container--default .select2-selection--single {
    border-color: #E0E0E0;
    outline: none;
}

.select2-dropdown {
    border: 1px solid #E0E0E0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
    font-family: "FontAwesome";
    border: 0px;
    top: 0px;
    height: 1.428rem;
    bottom: 0px;
    line-height: 1.428rem;
    left: 5px;
    margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
    content: "";
}

.select2-container--default .select2-search--dropdown .select2-search__field {
    border: 0px;
    outline: none;
}
.select2-container--default.select2-container--disabled .select2-selection--single{
    background-color: #e9ecef!important
}
</style>
