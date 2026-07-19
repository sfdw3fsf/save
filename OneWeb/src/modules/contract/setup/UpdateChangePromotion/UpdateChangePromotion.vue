<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="false"/>
        <div class="page-content">
            <div class="box-form padt8 padb8 fw6">
                <div class="row">
                    <div class="col-sm-2 col-12">

                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">Mã dự án</div>
                            <div class="value">
                                <input type="text" class="form-control" :value="input_ma_duan" @change="e=>input_ma_duan=e.target.value">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">Kiểu PL</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="kieu_pl_selected" class="form-control">
                                        <option v-for="item in ds_kieu_pl" :key="item.kieuhd_id" :value="item.kieuhd_id">
                                            {{item.kieu_hd}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="checkCTV" @change="changeCheckCTV" title="Tích chọn để tìm kiếm cộng tác viên">
                                    <span class="name">Cộng tác viên</span>
                                </div>
                            </div>
                            <div class="value">
                                <input type="text" ref="inputCTV" class="form-control" @keypress="evt=>evt.preventDefault()" :value="ctv.ten_ctv" @change="e=>ctv.ten_ctv=e.target.value" :disabled="!checkCTV">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="checkNguoiGT" @change="checkCheckNguoiGT" title="Tích chọn để tìm kiếm người giới thiệu hợp đồng">
                                    <span class="name">Người giới thiệu</span>
                                </div>
                            </div>
                            <div class="value">
                                <input type="text" ref="inputNguoiGT" class="form-control" @keypress="evt=>evt.preventDefault()" :disabled="!checkNguoiGT" :value="nguoi_gt.ten_nguoi_gt" @change="e=>nguoi_gt.ten_nguoi_gt=e.target.value">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">
                                Tổng tiền
                            </div>
                            <div class="value disabled">
                                <vue-numeric class="form-control tright" separator=","  :value="input_tongtien" @change="e=>input_tongtien=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" class="form-control tright" :value="input_tongtien" @change="e=>input_tongtien=e.target.value"> -->
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
                    @onEnterMaGD="onEnterMaGD"
                    @changeMucCuocTB="changeMucCuocTB"
                    @changeCheckKhuyenMai="changeCheckKhuyenMai"
                    @changeCheckTraTruoc="changeCheckTraTruoc"
                    :dsKM_HDTB="dsKM_HDTB"
                    :hdtb_id="hdtb_id"
                    :loaihinhtb_id="loaihinhtb_id"
                    @btnChiTietKMClick="btnChiTietKMClick"
                    @btnChiTietTCClick="btnChiTietTCClick"
                    @formCloseChiTietKM="formCloseChiTietKM"

                />
                <ThongTinKhachHang ref="thongTinKhachHang"
                    @onAcceptDiaChiLD="onAcceptDiaChiLD"
                    @onAcceptDiaChiTB="onAcceptDiaChiTB"
                />
            </div>
            <!-- Layout thay đổi theo dịch vụ được chọn -->
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
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @onEnterMaTB="onEnterMaTB"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @acceptChonSoChinh="acceptChonSoChinh"
                @changeLoaiSoMoi="changeLoaiSoMoi"

            />
            <BangRongCoDinh v-else-if="dichvu_vt_selected==4||dichvu_vt_selected==14" ref="bangRongCoDinh"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onEnterMaTB="onEnterMaTB"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @changeGoiCuocAdsl="changeGoiCuocAdsl"
                @changeMucCuoc="changeMucCuoc"
                @clickMaTN_ADSL="clickMaTN_ADSL"
                @clickTTTC="clickTTTC_ADSL"
            />
            <IMS v-else-if="dichvu_vt_selected==11" ref="ims"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onEnterMaTB="onEnterMaTB"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @clickMaTN_IMS="clickMaTN_IMS"
            />
            <KenhThuRieng v-else-if="dichvu_vt_selected==9" ref="kenhThuRieng"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onEnterMaTB="onEnterMaTB"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @changeTocDoKenhTSLMoi="changeTocDoKenhTSLMoi"
            />
            <Megawan v-else-if="dichvu_vt_selected==7||dichvu_vt_selected==8" ref="megawan"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @changeDoiTuongMoi="changeDoiTuongMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onAcceptKhuVuc="onAcceptKhuVuc"
                @onEnterMaTB="onEnterMaTB"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeLoaiTBWanMoi="changeLoaiTBWanMoi"
                @changeTocDoCirWan="changeTocDoCirWan"
            />
            <DiDong v-else-if="dichvu_vt_selected==2" ref="diDong"
                :hdkh_id="hdkh_id"
                :khachhang_id="khachhang_id"
                :dataDiaChiLD="dataDiaChiLD"
                :khuvuc_id="khuvuc_id"
                :donviql_id="donviql_id"
                :hdtb_id="hdtb_id"
                :thuebao_id="thuebao_id"
                :checkQuyen="checkQuyen"
                :iKieu="iKieu"
                @changeLoaiHinhTBMoi="changeLoaiHinhTBMoi"
                @changeTrangBiMoi="changeTrangBiMoi"
                @onEnterMaTB="onEnterMaTB"
                @onAcceptKhuVuc="onAcceptKhuVuc"
            />
            <!--  -->
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
                <KTableV3 :columns="columns"
                    :dataSources="dataSources"
                    @onSelectedRow="onSelectedRow"
                    :rowSelectedId="rowSelectedId"
                    id="HDTB_ID"
                    :allowFilter="true"
                    :allowCheckBox="true"/>
            </div>
        </div>
        <Footer/>
        <!-- Modal -->
        <ChonCongTacVienModal ref="chonCongTacVienModal" @accept="acceptCTV"/>
        <ChonNguoiGTModal ref="chonNguoiGTModal" @accept="acceptNguoiGT"/>
        <ThongTinHenKhachHangModal ref="thongTinHenKhachHangModal" :hdkh_id="hdkh_id" :quyenduoc_hen="1"/>
        <DanhSachThueBaoCungMaModal ref="danhSachThueBaoCungMaModal" @accept="acceptThuBaoCungMa" :data="dsTBCungMa"/>
        <ThongTinLienHeModal ref="thongTinLienHeModal" :hdkh_id="hdkh_id" :khachhang_id="khachhang_id"/>
        <EmailModal ref="emailModal" :hdkh_id="hdkh_id" :khachhang_id="khachhang_id"/>
        <TTFsecureKemFtthModal ref="TTFsecureKemFtthModal" :dulieu="dulieuTTFsecureKemFtth" @form-close="formCloseTTFsecureKemFtth"/>
        <InHoSo ref="popupInHoSo" :params="popupInHopDong_Param"
        />
                 <popupDieuChinh ref="popupDieuChinh" v-on:confirmPopup="getPickedpopupDieuChinh" />

                 <frmDangKyGoiDaDVLM ref="frmDangKyGoiDaDVLM" />

    </div>
</template>
<script>
import Footer from "@/components/FooterStyle.vue"
import breadcrumb from "@/components/kylq_components/breadcrumb.vue"
import ActionTop from "@/components/kylq_components/mirana/ActionTop.vue"
import KTableV3 from "@/components/kylq_components/mirana/KTableV3.vue"
import ThongTinPhieuYeuCau from "./ThongTinPhieuYeuCau.vue"
import ThongTinKhachHang from "./ThongTinKhachHang.vue"
import CoDinh from "./layouts/CoDinh.vue"
import MuaThietBi from "./MuaThietBi.vue"
import ChiTietTienTheoKhoanMuc from "./ChiTietTienTheoKhoanMuc.vue"
import ChonCongTacVienModal from "../SurveyRequest/popups/ChonCongTacVien/ChonCongTacVienModal.vue"
import ChonNguoiGTModal from "../SurveyRequest/popups/ChonNguoiGT/ChonNguoiGTModal.vue"
import {DichVuVienThong, LoaiHopDong, KHOANMUC_TT, TrangThaiHD, KIEU_HD,
  TRANGTHAI_DONGBO, LoaiHinhTB, TrangThaiTB, LOAISO_ISDN, KieuLapDat,
  ChiTietDV_IMS, DUNG_THU, LOAI_KV, LOAI_DV, LOAITBI_ADSL} from "./ThamSo"
import BangRongCoDinh from "./layouts/BangRongCoDinh.vue"
import IMS from "./layouts/IMS.vue"
import KenhThuRieng from "./layouts/KenhThuRieng.vue"
import Megawan from "./layouts/Megawan.vue"
import ChangeSubsTypeAPI from "./ChangeSubsTypeAPI"
import ThongTinHenKhachHangModal from "../SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue"
import DanhSachThueBaoCungMaModal from "./popups/DanhSachThueBaoCungMaModal.vue"
import moment from "moment"
import ThongTinLienHeModal from "./popups/ThongTinLienHeModal.vue"
import EmailModal from "./popups/EmailModal.vue"
import {createObjectHDTB_CD, createObjectHDKH, createObjectHDTB_IMS, createObjectHDTB_ADSL, createObjectHDTB_MGWAN, createObjectHDTB_TSL, createObjectHDTB_DD} from "./Utils"
import TTFsecureKemFtthModal from "./popups/TTFsecureKemFtthModal.vue"
import DiDong from "./layouts/DiDong.vue"
import InHoSo from "../../../print/InHopDong/InHopDong.vue"
import popupDieuChinh from "./popups/popupDieuChinh.vue"
import serviceTest from "./test_service"
import service from "./service"
import frmDangKyGoiDaDVLM from '@/modules/admin/category/NiceNumberStyle/popups/frmDangKyGoiDaDVLM.vue';
// MaTB CD test: 3246604, 3589114
// MaTB IMS test: 3669761
// MaTB ADSL test: phuongvienadsl_hp . Tốc độ có mức cước: F1 - FTTH

export default {
  name: "ChangeSubsType",
  components: {
    Footer,
    breadcrumb,
    ActionTop,
    KTableV3,
    ThongTinPhieuYeuCau,
    ThongTinKhachHang,
    CoDinh,
    MuaThietBi,
    ChiTietTienTheoKhoanMuc,
    ChonCongTacVienModal,
    ChonNguoiGTModal,
    BangRongCoDinh,
    IMS,
    KenhThuRieng,
    Megawan,
    ThongTinHenKhachHangModal,
    DanhSachThueBaoCungMaModal,
    ThongTinLienHeModal,
    EmailModal,
    TTFsecureKemFtthModal,
    DiDong,
    InHoSo,
    frmDangKyGoiDaDVLM
  },
  data () {
    return {
      header: {
        title: "Sửa khuyến mại thay đổi loại hình thuê bao",
        list: [
        { name: "Cập nhật KQTC",
            link: { name: "Ui.cards" },
            active: false
          },
          {
            name: "Thay đổi, khoá mở, thanh lý",
            link: { name: "Ui.cards" },
            active: false
          },
          {
            name: "Sửa khuyến mại thay đổi loại hình thuê bao",
            link: { name: "Ui.buttons" },
            active: true
          }
        
        ]
      },
      actions: [
        {
          id: "nhapmoi",
          name: "Nhập mới",
          active: true,
          icon_class: "one-file-plus",
          visible: true,
          type: 1
        },
        {
          id: "ghilai",
          name: "Ghi lại",
          active: true,
          icon_class: "one-save",
          visible: true,
          type: 1
        },
        {
          id: "huy",
          name: "Hủy",
          active: true,
          icon_class: "nc-icon-glyph ui-1_circle-remove",
          visible: true,
          type: 1
        },
        {
          id: "xoa_hd",
          name: "Xóa HĐ",
          active: true,
          icon_class: "one-trash",
          visible: true,
          type: 1
        },
        {
          id: "thanhtoan",
          name: "Thanh toán",
          active: true,
          icon_class: "nc-icon-glyph shopping_credit-card",
          tooltip: "Thanh toán hợp đồng",
          visible: true,
          type: 1
        },
        // Bổ sung droplist các control con
        {
          id: "hoso",
          name: "Hồ sơ",
          active: true,
          icon_class: "nc-icon-glyph files_single-content-02",
          tooltip: "In hồ sơ",
          visible: true,
          type: 1
        },
        {
          id: "goidadv",
          name: "Gói đa DV",
          active: true,
          icon_class: "one-file-edit",
          tooltip: "Đăng ký gói đa dịch vụ",
          visible: true,
          type: 1
        },
        // không dùng nữa
        {
          id: "giaophieu",
          name: "Giao phiếu",
          active: true,
          icon_class: "one-file-arrow-right1",
          visible: true,
          type: 1
        },
        {
          id: "themtb",
          name: "Thêm TB",
          active: true,
          icon_class: "one-mobile-plus",
          tooltip: "Thêm thuê bao",
          visible: true,
          type: 1
        },
        {
          id: "xoatb",
          name: "Xóa TB",
          active: true,
          icon_class: "one-phone-remove",
          tooltip: "Xóa thuê bao",
          visible: true,
          type: 1
        },
        //
        {
          id: "capnhatdb",
          name: "Cập nhật DB",
          active: true,
          icon_class: "one-reload1",
          visible: true,
          type: 1
        },
        {
          id: "lienhe",
          name: "Liên hệ",
          active: true,
          icon_class: "nc-icon-glyph business_contacts",
          tooltip: "Thông tin liên hệ",
          visible: true,
          type: 1
        },
        {
          id: "email",
          name: "Email",
          active: true,
          icon_class: "one-email",
          visible: true,
          type: 1
        },
        {
          id: "henld",
          name: "Hẹn LĐ",
          active: true,
          icon_class: "one-calendar-check",
          tooltip: "Hẹn lắp đặt",
          visible: true,
          type: 1
        },
        {
          id: "sua_km",
          name: "Sửa KM",
          active: true,
          icon_class: "",
          tooltip: "Sửa khuyến mại, đặt cọc của hợp đồng",
          visible: false,
          type: 1
        }
      ],
      columns: [
        {
          field: "MA_GD",
          label: "Mã GD",
          allowFilter: true
        },
        {
          field: "MA_TB",
          label: "Số máy/Acc",
          allowFilter: true
        },
        {
          field: "TEN_TB",
          label: "Tên thuê bao",
          allowFilter: true
        },
        {
          field: "DIACHI_LD",
          label: "Địa chỉ LĐ",
          allowFilter: true
        },
        {
          field: "TRANGTHAI_VINA",
          label: "Trạng thái đồng bộ",
          allowFilter: true
        }
      ],
      // param tag
      iKieu: 0, // 0: lap hop dong, 1: tra cuu, 2: sua km
      iKenhTN: 1, // Keenh tieep nhan hop dog (trong bangr kenh_tn)
      //
      dataSources: [],
      rowSelectedId: -1,
      input_ma_duan: "",
      ds_kieu_pl: [],
      kieu_pl_selected: null,
      checkCTV: false,
      checkNguoiGT: false,
      input_tongtien: "0",
      ctv: {
        ctv_id: null,
        ten_ctv: "",
        ma_ctv: "",
        loainv_id: -1,
        donvi_id: -1
      },
      nguoi_gt: {
        nguoi_gt_id: null,
        ten_nguoi_gt: "",
        ma_nguoi_gt: "",
        loainv_id: -1,
        donvi_id: -1
      },
      // mặc định cố định
      dichvu_vt_selected: 1,

      thamso: {
        // Default không bắt buộc nhập thông tin tính cước
        nhap_tttc: 0,
        /// /Default không nhập tốc độ thực adsl
        nhap_tocdo_thuc_adsl: 0,

        kiemtra_khop_tien_datcoc: false,
        ts_sinh_matb_mgw_mte: false,
        khong_thaydoi_diachi_ld: 0,
        // THam số ko bắt buộc phải nhập cộng tác viên
        khong_batbuoc_ctv: 0,
        // Tham số bắt buộc nhập khu vực
        nhap_nvkd: 0,
        //
        batbuoc_gioithieu_loaihinh: 0,
        // Cho phép chọn đối tượng dùng thử khi chuyển đổi loại hình; =1: Cho phép
        CHUYENDOI_LH_DUNGTHU: -1,
        // Thực hiện bắt buộc chọn đăng ký trả trươc - y/c mail HYN
        dk_tratruoc: 0,
        nguoidung_boqua_tratruoc: 0,

        phainhap_ip_siptrunking: false,

        // KNV:tham số tự động sinh số ảo
        TUDONG_SINH_SOAO: false,
        // Dùng cho Hni sinh ma GD theo don vi quan ly cua ng dung
        ts_sinhma_gd_theo_donvi: false,
        //
        batbuoc_chon_dacdiem_dcld: false

      },
      ds_tien_khoanmuc: [],
      dataDiaChiLD: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
        //
        block: "",
        tang: "",
        sophong: "",
        motathem: "",
        diachi: "",

        kinhdo: 0,
        vido: 0
      },
      dataDiaChiTB: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
        //
        block: "",
        tang: "",
        sophong: "",
        motathem: "",
        diachi: "",

        kinhdo: 0,
        vido: 0
      },
      hdkh_id: 0,
      hdkh_cha_id: 0,
      hdtb_id: 0,
      hdtb_id_cha: 0,
      khachhang_id: 0,
      thuebao_id: 0,
      thuebao_id_cha: 0,
      loaisochinh: 0,
      loaitb_id: 0,
      donviql_id: 0,
      donvi_id: 0,
      dichvuvt_id: 0,
      doituong_id: 1,
      trangbi_id: 3, // Default khách hàng tự trang bị
      rowSelected: null,
      ma_gd_neo: "",
      vdiachi_tb: "",
      loaihinhtb_id: 0,
      loaihinhtbcu_id: 0,
      khuvuc_id: 0,
      so_dt: "",
      //
      dtienld: 0,
      dvatld: 0,

      dtienkmld: 0,
      dvatkmld: 0,

      dtientbi: 0,
      dvattbi: 0,

      dtien_kmtb: 0,
      dvat_kmtb: 0,

      dtienkmdc: 0,
      dvatkmdc: 0,

      dtiendatcoc_tt: 0,
      dvatdatcoc_tt: 0,

      dtiendatcoc_km: 0,
      dvatdatcoc_km: 0,

      // Hiếu bổ sung phần khai báo cho hàm laytien --17/09/2010
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

      dsTBCungMa: [],
      isdn_sochinh: false,
      matb_isdn_chinh: "",
      ds_isdn: [],
      dtienthoai_dc: 0,
      kieuld_id: 0,

      kh_cd: 0,
      ltb_adsl_id: 1,
      madoicap_cd: 0,
      dsCTM_TBI: [],
      cuoc_tc: 0,
      cuoc_tk: 0,
      KetCuoi_ID: -1,
      dsKM_HDTB: [],
      dtCTKM: [],
      dsDC_HDTB: [],
      ds_tien_khoanmuc_bd: [],
      // array string tên thiết bị hiển thị
      dsTenTB: [],
      sothang_sd: 0,
      quytrinh_id: 0,
      luong_id: 0,
      // tạo dữ liệu
      dsHDKH: [],
      dsHDTB: [],
      dsDiaChi: [],
      dsDiaChiHDTB: [],
      dsHDTB_DUNGTHU: [],

      dsHDTB_CD: [],
      dsHDTB_DD: [],
      dsHDTB_ADSL: [],
      dsHDTB_GP: [],
      dsHDTB_TSL: [],
      dsHDTB_IMS: [],

      dsHDTB_MEWAN: [],
      dsHDTBDV: [],
      dsCTTHD: [],
      dsHDTB_KV: [],

      dsTIEN_HDTB_TC: [],

      dsHDTBGOI: [],

      kieuld_fsecure_kem_fiber: 0,

      ds_loaihinh_tb: [],
      ds_goi_kt: [],
      kt_adsl: true,
      hdtb_id_huygoi_mytv: 0,
      huonggiaotn_id: 0,
      quytrinh_id_2: 0,
      dulieuTTFsecureKemFtth: {},
      sodt_fsecure_kem: "",
      kh_cd_fsecure: false,
      vhuong_kn: "",
      madoicap_mgw: 0,

      diachidau_id: 0,
      quandau_id: 0,
      phuongdau_id: 0,
      phodau_id: 0,
      ngodau_id: 0,
      sonhadau: "",

      diachicuoi_id: 0,
      quancuoi_id: 0,
      phuongcuoi_id: 0,
      phocuoi_id: 0,
      ngocuoi_id: 0,
      sonhacuoi: "",

      ds_loaidv_dau: [],
      ds_loaidv_cuoi: [],
      donvi_dau_id: 0,
      donvi_cuoi_id: 0,
      tram_dv_dau: 0,
      tram_dv_cuoi: 0,
      ma_tb_dau: "",
      ma_tb_cuoi: "",
      Lat_DD: 0,
      Lng_DD: 0,
      Lat_DC: 0,
      Lng_DC: 0,
      checkQuyen: 0,
      tt_nd: null,
      popupInHopDong_Param: {},
      tt_hddt: {
        tong_hddt: 0,
        tongvat_hddt: 0,
        tongtien_hddt: 0,
        tbc: "Không đồng",
        dsBDHDDT: [],
        Fkey: ""
      }
    }
  },
  methods: {
    getPickedpopupDieuChinh (e) {
      return e
    },
    onActionClick (action) {
      if (action.id == "nhapmoi") {
        this.setActiveActions(1)
      } else if (action.id == "ghilai") {
        this.capnhat()
      } else if (action.id == "huy") {
        this.setActiveActions(0)
      } else if (action.id == "xoa_hd") {
        this.$confirm("Bạn thật sự muốn xóa hợp đồng không ?", "Thông báo", {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Hủy"
        }).then(() => {
          this.xoaHDKH()
        })
      } else if (action.id == "thanhtoan") {
        this.thanhtoan()
      } else if (action.id == "hoso") {
        this.hoso()
      } else if (action.id == "giaophieu") {
        // nút trong code đã comment lại không có dùng, bỏ qua không xử lý
      } else if (action.id == "goidadv") {
        this.goidadv()
      } else if (action.id == "themtb") {
        this.themTB()
      } else if (action.id == "xoatb") {
        this.$confirm("Bạn thật sự muốn xóa thuê bao không ?", "Thông báo", {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Hủy"
        }).then(() => {
          this.xoaHDTB()
        }).catch(() => {})
      } else if (action.id == "capnhatdb") {
        // https://cntt.vnpt.vn/browse/BSS-1054
        this.$toast.error("Tính năng đang hoàn thiện và phát triển (UR2.1.028)")
      } else if (action.id == "lienhe") {
        if (this.hdkh_id != 0) {
          this.$refs.thongTinLienHeModal.showModal()
        }
      } else if (action.id == "email") {
        if (this.hdkh_id != 0) {
          this.$refs.emailModal.showModal()
        }
      } else if (action.id == "henld") {
        if (this.hdkh_id != 0) {
          this.$refs.thongTinHenKhachHangModal.showModal()
        }
      } else if (action.id == "sua_km") {
        this.sua_km()
      }
    },
    async setActiveActions (kieu) {
      this.actions[this.actions.findIndex(x => x.id == "nhapmoi")].active = false
      this.actions[this.actions.findIndex(x => x.id == "ghilai")].active = false
      this.actions[this.actions.findIndex(x => x.id == "huy")].active = false
      this.actions[this.actions.findIndex(x => x.id == "xoa_hd")].active = false
      this.actions[this.actions.findIndex(x => x.id == "themtb")].active = false
      this.actions[this.actions.findIndex(x => x.id == "xoatb")].active = false
      this.actions[this.actions.findIndex(x => x.id == "giaophieu")].active = false
      // this.actions[this.actions.findIndex(x=>x.id=='giaophieu')].visible=false
      this.actions[this.actions.findIndex(x => x.id == "capnhatdb")].active = false
      if (kieu == -1) {
        this.focusMaTB()
        this.actions[this.actions.findIndex(x => x.id == "ghilai")].active = true
        this.actions[this.actions.findIndex(x => x.id == "huy")].active = true
      } else if (kieu == 0) {
        // bắt đầu
        this.focusMaTB()
        this.actions[this.actions.findIndex(x => x.id == "nhapmoi")].active = true
        await this.clear()
      } else if (kieu == 1) {
        // Thêm mới
        this.focusMaTB()
        this.actions[this.actions.findIndex(x => x.id == "ghilai")].active = true
        this.actions[this.actions.findIndex(x => x.id == "huy")].active = true
        this.checkCTV = false
        this.dataSources = []
        await this.clear()
      } else if (kieu == 2) {
        // Huỷ
        this.focusMaTB()
        this.actions[this.actions.findIndex(x => x.id == "nhapmoi")].active = true
        this.actions[this.actions.findIndex(x => x.id == "xoa_hd")].active = true
        await this.clear()
      } else if (kieu == 3) {
        this.focusMaTB()
        this.actions[this.actions.findIndex(x => x.id == "nhapmoi")].active = true
        this.actions[this.actions.findIndex(x => x.id == "xoa_hd")].active = true
        this.actions[this.actions.findIndex(x => x.id == "ghilai")].active = true
        this.actions[this.actions.findIndex(x => x.id == "huy")].active = true

        this.actions[this.actions.findIndex(x => x.id == "themtb")].active = true
        this.actions[this.actions.findIndex(x => x.id == "xoatb")].active = true

        this.actions[this.actions.findIndex(x => x.id == "giaophieu")].active = true
        if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
          this.actions[this.actions.findIndex(x => x.id == "thanhtoan")].active = false
          this.actions[this.actions.findIndex(x => x.id == "giaophieu")].active = false
        }
      }
    },
    focusMaTB () {
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        this.$refs.coDinh.focusMaTB()
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        // băng rộng cố định
        this.$refs.diDong.focusMaTB()
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        this.$refs.bangRongCoDinh.focusMaTB()
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        this.$refs.ims.focusMaTB()
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        this.$refs.kenhThuRieng.focusMaTB()
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        this.$refs.megawan.focusMaTB()
      }
    },
    async clear () {
      // clear
      this.input_ma_duan = ""
      this.kieu_pl_selected = null
      this.checkCTV = false
      this.checkNguoiGT = false
      this.input_tongtien = "0"
      this.hdtb_id_cha = 0
      this.thuebao_id_cha = 0
      this.loaisochinh = 0
      this.hdkh_id = 0
      this.hdkh_cha_id = 0
      this.khachhang_id = 0
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.doituong_id = 1 // Defaul: tư nhân
      this.trangbi_id = 3 // Default khách hàng tự trang bị
      this.vdiachi_tb = ""
      this.khuvuc_id = 0
      this.so_dt = ""
      this.vhuong_kn = ""
      this.kieuld_id = 0

      this.madoicap_mgw = 0

      this.dtienld = 0
      this.dvatld = 0

      this.dtienkmld = 0
      this.dvatkmld = 0

      this.dtientbi = 0
      this.dvattbi = 0

      this.dtien_kmtb = 0
      this.dvat_kmtb = 0

      this.dtienkmdc = 0
      this.dvatkmdc = 0

      this.dtiendatcoc_tt = 0
      this.dvatdatcoc_tt = 0

      this.dtiendatcoc_km = 0
      this.dvatdatcoc_km = 0

      //
      this.tien_tbi = 0
      this.vat_tbi = 0
      this.tien_dv = 0
      this.vat_dv = 0
      this.tien_kmld = 0
      this.vat_kmld = 0

      this.madoicap_cd = 0
      this.KetCuoi_ID = -1

      this.ctv = {
        ctv_id: null,
        ten_ctv: "",
        ma_ctv: "",
        loainv_id: -1,
        donvi_id: -1
      }
      this.nguoi_gt = {
        nguoi_gt_id: null,
        ten_nguoi_gt: "",
        ma_nguoi_gt: "",
        loainv_id: -1,
        donvi_id: -1
      }
      this.dataDiaChiLD = {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
        //
        block: "",
        tang: "",
        sophong: "",
        motathem: "",
        diachi: "",

        kinhdo: 0,
        vido: 0
      }
      this.dataDiaChiTB = {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
        //
        block: "",
        tang: "",
        sophong: "",
        motathem: "",
        diachi: "",

        kinhdo: 0,
        vido: 0
      }
      this.dsKM_HDTB = []
      this.dtCTKM = []
      this.ds_tien_khoanmuc = await this.sp_lay_tienkhoanmuc_theo_loaihd(LoaiHopDong.CHUYENDOI_LH)
      //
      this.$refs.thongTinPhieuYeuCau.clearDulieu()
      this.$refs.thongTinKhachHang.clearDulieu()
      this.$refs.muaThietBi.clearDulieu()
      this.$refs.thongTinKhachHang.clearDulieu()

      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        await this.$refs.coDinh.clearDulieu()
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        await this.$refs.diDong.clearDulieu()
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        await this.$refs.bangRongCoDinh.clearDulieu()
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        await this.$refs.ims.clearDulieu()
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.$refs.kenhThuRieng.clearDulieu()
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        await this.$refs.megawan.clearDulieu()
      }
      //
      this.dsCTM_TBI = []
      this.dsDC_HDTB = []
      this.cuoc_tc = 0
      this.cuoc_tk = 0
      this.dsTenTB = []

      await this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
      await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.dtiendatcoc_km, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)

      await this.HT_ThietBi()

      await this.LayTienTheoKhoanMuc()
    },
    // Hàm thừa
    async HienThiTienDichVu (tien_dv, vat_dv) {
      // if (tien_dv.ToString() != "")
      //     txtTienDV.Text = tien_dv.ToString(Format.FM_TIEN_VND);
      // else
      //     txtTienDV.Text = "0";
      // if (vat_dv.ToString() != "")
      //     txtVatDV.Text = vat_dv.ToString(Format.FM_TIEN_VND);
      // else
      //     txtVatDV.Text = "0";
    },
    // mai làm
    async HT_ThietBi () {
      var kt = true// Kiểm tra xem biến
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

      // Dư liệu ảo test
      // this.dsCTM_TBI=[
      //     {
      //         PHANVUNG_ID:26,
      //         LOAITBI_ID:266,
      //         HDTB_ID:4248273,
      //         SOLUONG:1,
      //         TIEN:830000,
      //         VAT:83000,
      //         TIEN_KM:830000,
      //         VAT_KM:83000,
      //         TIEN_TRATRUOC:0,
      //         VAT_TRATRUOC:0,
      //         TIEN_TRAGOP:null,
      //         VAT_TRAGOP:null,
      //         SERIAL:null,
      //         TYLE_VAT:10,
      //         TYLE_VAT_ID:1,
      //         SL_CHA:1,
      //         BLOCK_TIEP:0,
      //         GIA_BLOCK_TIEP:0,
      //         VAT_BLOCK_TIEP:0,
      //         BLOCK_DAU:0,
      //         TIEN_THUE:null,
      //         TONG_TIEN:null,
      //         TONG_THUE:null,
      //         MA_TBI:"STBANDROI",
      //         LOAI_TBI:"Set Top Box Androi (Smart box)"
      //     }
      // ]
      // this.dsKM_HDTB=[
      //     {
      //         PHANVUNG_ID:26,
      //         HDTB_ID:4248273,
      //         KHUYENMAI_ID:11108,
      //         CHITIETKM_ID:13563,
      //         KHUYENMAI_ID_NEO:null,
      //         KIEU_YC:1,
      //         THANG_BD:201912,
      //         THANG_KT:202006,
      //         THANG_HUONGKM:7,
      //         THANG_HUONGDC:7,
      //         THANG_BDDC:201912,
      //         THANG_KTDC:202006,
      //         THANG_BDTG:null,
      //         THANG_KTTG:null,
      //         TIEN_TG:0,
      //         SOTHANG_TG:0,
      //         TYLE_KMLD:0,
      //         TYLE_TB:0,
      //         TYLE_SD:0,
      //         DATCOC_CSD:501600,
      //         TIEN_TD:71657,
      //         RKM_ID:null,
      //         FKEY:null,
      //         TIEN_SD:0,
      //         CUOC_SD:0,
      //         TIEN_TB:18857,
      //         CUOC_TB:0,
      //         NGAY_HUY:null,
      //         NGAY_BD:null,
      //         NGAY_KT:null,
      //         NGAY_THOAI:null
      //     }
      // ]

      this.dsTenTB = []
      for (let i = 0; i < this.dsCTM_TBI.length; i++) {
        this.dsTenTB.push(this.dsCTM_TBI[i].LOAI_TBI)
        //
        kt = false
        soluong = this.dsCTM_TBI[i].SOLUONG
        if (this.dsCTM_TBI[i].TIEN != null) {
          dtien_tbi_tmp = this.dsCTM_TBI[i].TIEN
          this.tien_tbi = this.tien_tbi + dtien_tbi_tmp * soluong
        }
        if (this.dsCTM_TBI[i].VAT != null) {
          dvat_tbi_tmp = this.dsCTM_TBI[i].VAT
          this.vat_tbi = this.vat_tbi + dvat_tbi_tmp * soluong
        }
        // DS khuyến mãi
        let dtTMP_KMTBI = await this.sp_lay_ctkm_loai_tbi(this.ds_CTKM(), this.dsCTM_TBI[i].LOAITBI_ID)
        if (dtTMP_KMTBI.length > 0) {
          dtien_kmtbi_tmp = dtTMP_KMTBI[0].tien_km != null ? dtTMP_KMTBI[0].tien_km : 0
          dvat_kmtbi_tmp = dtTMP_KMTBI[0].vat_km != null ? dtTMP_KMTBI[0].vat_km : 0
          dTyLe_KMTB = dtTMP_KMTBI[0].tyle_km != null ? dtTMP_KMTBI[0].tyle_km : 0
        }
        console.log("dtTMP_KMTBI", dtTMP_KMTBI)
        if (dTyLe_KMTB > 0) {
          dtien_kmtbi_tmp = Math.round(dtien_tbi_tmp * dTyLe_KMTB / 100)
          dvat_kmtbi_tmp = Math.round(dvat_tbi_tmp * dTyLe_KMTB / 100)
        }
        if (dtTMP_KMTBI.length > 0) {
          this.dsCTM_TBI[i].TIEN_KM = dtien_kmtbi_tmp
          this.dsCTM_TBI[i].VAT_KM = dvat_kmtbi_tmp
          this.tien_kmtbi += dtien_kmtbi_tmp * soluong
          this.vat_kmtbi += dvat_kmtbi_tmp * soluong
        }
        // DS trả góp
        let dtTMP = await this.sp_lay_ds_tragop_thietbi(this.ds_CTKM(), this.dsCTM_TBI[i].LOAITBI_ID)
        if (dtTMP.length > 0) {
          // có định nghĩa trả góp
          if (this.dsCTM_TBI[i].TIEN_TRATRUOC != null) {
            dtien_tgtb_tratruoc_tmp = this.dsCTM_TBI[i].TIEN_TRATRUOC
          }
          if (this.dsCTM_TBI[i].VAT_TRATRUOC != null) {
            dvat_tgtb_tratruoc_tmp = this.dsCTM_TBI[i].VAT_TRATRUOC
          }
          console.log("dtien_tgtb_tratruoc_tmp trả góp", dtien_tgtb_tratruoc_tmp)
          console.log("dvat_tgtb_tratruoc_tmp trả góp", dvat_tgtb_tratruoc_tmp)
        } else {
          dtien_tgtb_tratruoc_tmp = dtien_tbi_tmp - dtien_kmtbi_tmp
          dvat_tgtb_tratruoc_tmp = dvat_tbi_tmp - dvat_kmtbi_tmp
          if (this.dsCTM_TBI[i].TIEN_TRATRUOC == "" || this.dsCTM_TBI[i].TIEN_TRATRUOC == 0) {
            this.dsCTM_TBI[i].TIEN_TRATRUOC = dtien_tgtb_tratruoc_tmp
            this.dsCTM_TBI[i].TIEN_TRATRUOC = dvat_tgtb_tratruoc_tmp
          }
          console.log("dtien_tgtb_tratruoc_tmp  ktrả góp", dtien_tgtb_tratruoc_tmp)
          console.log("dvat_tgtb_tratruoc_tmp ktrả góp", dvat_tgtb_tratruoc_tmp)
        }

        // Tổng tiền trả trước thiết bị
        this.tien_tgtb_tratruoc += dtien_tgtb_tratruoc_tmp * soluong
        // Tổng vat trả trước thiết bị
        this.vat_tgtb_tratruoc += dvat_tgtb_tratruoc_tmp * soluong
        // Tổng tiền trả góp thiết bị
        this.tien_tgtb_tragop += (dtien_tbi_tmp - Math.abs(dtien_kmtbi_tmp) - dtien_tgtb_tratruoc_tmp) * soluong
        // Tổng vat trả góp thiết bị
        this.vat_tgtb_tragop += (dvat_tbi_tmp - Math.abs(dvat_kmtbi_tmp) - dvat_tgtb_tratruoc_tmp) * soluong

        console.log("tien_tgtb_tratruoc", this.tien_tgtb_tratruoc)
        console.log("vat_tgtb_tratruoc", this.vat_tgtb_tratruoc)
        console.log("tien_tgtb_tragop", this.tien_tgtb_tragop)
        console.log("vat_tgtb_tragop", this.vat_tgtb_tragop)
      }
      if (kt == true) {
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

      console.log("dtien_kmtb", this.dtien_kmtb)
      console.log("dvat_kmtb", this.vat_kmtbi)

      await this.HienThiTienMuaThietBi(this.tien_tbi, this.vat_tbi)
      await this.HienThiTienKhuyenMai_ThietBi(this.tien_kmtbi, this.vat_kmtbi)
      await this.HienThiTienTraTruocThietBi(this.tien_tgtb_tratruoc, this.vat_tgtb_tratruoc)
      await this.HienThiTienTraGopThietBi(this.tien_tgtb_tragop, this.vat_tgtb_tragop)
    },
    ds_CTKM () {
      var sDS = "-1"
      for (let i = 0; i < this.dsKM_HDTB.length; i++) {
        sDS += "," + this.dsKM_HDTB[i].CHITIETKM_ID.toString()
      }
      for (let i = 0; i < this.dsDC_HDTB.length; i++) {
        sDS += "," + this.dsDC_HDTB[i].CHITIETKM_ID.toString()
      }
      return sDS
    },
    async HienThiTienMuaThietBi (tien_tbi, vat_tbi) {
      this.dtientbi = tien_tbi
      this.dvattbi = vat_tbi
      //
      const index = this.ds_tien_khoanmuc.findIndex(x => x.khoanmuctt_id == KHOANMUC_TT.KMTT_MUATHIETBI.toString())
      if (index > -1) {
        this.ds_tien_khoanmuc[index].tien = tien_tbi
        this.ds_tien_khoanmuc[index].vat = vat_tbi
      }

      await this.LayTongTien_HDTB()
    },
    async HienThiTienKhuyenMai_ThietBi (tien_kmtb, vat_kmtbi) {
      this.dtien_kmtb = tien_kmtb
      this.dvat_kmtb = vat_kmtbi

      const index = this.ds_tien_khoanmuc.findIndex(x => x.khoanmuctt_id == KHOANMUC_TT.KMTT_KM_THIETBI.toString())
      if (index > -1) {
        if (tien_kmtb > 0) {
          this.ds_tien_khoanmuc[index].tien = tien_kmtb * -1
          this.ds_tien_khoanmuc[index].vat = vat_kmtbi * -1
        } else {
          this.ds_tien_khoanmuc[index].tien = tien_kmtb
          this.ds_tien_khoanmuc[index].vat = vat_kmtbi
        }
      }
      await this.LayTongTien_HDTB()
    },
    async HienThiTienTraTruocThietBi (tien_tratruoc_tbi, vat_tratruoc_tbi) {
      const index = this.ds_tien_khoanmuc.findIndex(x => x.khoanmuctt_id == KHOANMUC_TT.KMTT_TRATRUOC_THIETBI.toString())
      if (index > -1) {
        this.ds_tien_khoanmuc[index].tien = tien_tratruoc_tbi
        this.ds_tien_khoanmuc[index].vat = vat_tratruoc_tbi
      }
      await this.LayTongTien_HDTB()
    },
    async HienThiTienTraGopThietBi (tien_tragop_tbi, vat_tragop_tbi) {
      const index = this.ds_tien_khoanmuc.findIndex(x => x.khoanmuctt_id == KHOANMUC_TT.KMTT_TRAGOP_THIETBI.toString())
      if (index > -1) {
        this.ds_tien_khoanmuc[index].tien = tien_tragop_tbi
        this.ds_tien_khoanmuc[index].vat = vat_tragop_tbi
      }
      await this.LayTongTien_HDTB()
    },
    async thanhtoan () {
      // kiemtra_thanhtoan_datmoi_lh
      let result = await this.kiemtra_thanhtoan_datmoi_lh(this.hdkh_id)
      if (result == "0") {
        this.$toast.error("Hợp đồng chưa có thông tin liên hệ, bạn phải bổ sung thông tin liên hệ trước khi thanh toán !")
        return
      }
      // Payment
      let query = {
        magd: this.$refs.thongTinPhieuYeuCau.input_magd.trim()
      }
      let routeData = this.$router.resolve({name: "Payment", query: query})
      window.open(routeData.href, "_blank")
      // f.HienThiTTHopDongKH();
    },
    async hoso () {
      if (this.hdkh_id != 0) {
        this.popupInHopDong_Param.hdkh_id = this.hdkh_id
        this.popupInHopDong_Param.ma_gd = this.$refs.thongTinPhieuYeuCau.input_magd.trim()
        this.popupInHopDong_Param.ngay_yc = this.$refs.thongTinPhieuYeuCau.ngay_yc
        this.popupInHopDong_Param.ngay_lhd = this.$refs.thongTinKhachHang.ngay_lhd
        setTimeout(() => {
          this.$refs.popupInHoSo.showModal()
        }, 500)
      } else {
        this.$toast.error("Chưa có thông tin hợp đồng!")
      }
    },
    async goidadv () {
      var ma_gd = this.$refs.thongTinPhieuYeuCau.input_magd.trim()
      this.$refs.frmDangKyGoiDaDVLM.showDialog(ma_gd)
      // test
      // this.loading(true)
      // await this.TaoDuLieu_HDTB_MGWAN(true)
      // console.log('dsHDTB_MEWAN', this.dsHDTB_MEWAN)
      // this.loading(false)
      // this.TaoDuLieu_HDTB_TSL(true)
      // console.log('TaoDuLieu_HDTB_TSL', this.dsHDTB_TSL)
    },
    kiemtra_khoanmuc_tien () {
      console.log("ds_tien_khoanmuc_bd =", this.ds_tien_khoanmuc_bd)
      console.log("ds_tien_khoanmuc =", this.ds_tien_khoanmuc)
      var tmp_ds_tien_khoanmuc_cu = this.ds_tien_khoanmuc_bd.filter(item => parseFloat(item.vat) > 0 && parseFloat(item.tien) > 0)
      var tmp_ds_tien_khoanmuc_moi = this.ds_tien_khoanmuc.filter(item => parseFloat(item.vat) > 0 && parseFloat(item.tien) > 0)
      if (tmp_ds_tien_khoanmuc_cu.length != tmp_ds_tien_khoanmuc_moi.length) return false

      var vtien_bd, vvat_bd, vtien_moi, vvat_moi
      // Kiểm tra các khoản mục của 2 bên có khớp nhau không
      var kt = false
      // Duyệt toàn bộ bảng mới
      // Kiểm tra các khoản mục của bảng mới có tồn tại bên bảng cũ không?
      for (let itemMoi of tmp_ds_tien_khoanmuc_moi) {
        kt = false
        for (let itemCu of tmp_ds_tien_khoanmuc_cu) {
          vtien_bd = 0
          vvat_bd = 0
          vtien_moi = 0
          vvat_moi = 0
          if (itemMoi["khoanmuctt_id"] == itemCu["khoanmuctt_id"]) {
            vtien_moi = parseFloat(itemMoi.tien)
            vvat_moi = parseFloat(itemMoi.vat)
            vtien_bd = parseFloat(itemCu.tien)
            vvat_bd = parseFloat(itemCu.vat)

            if (vtien_bd == vtien_moi && vvat_bd == vvat_moi) {
              kt = true
              break
            }
          }
        }

        // không tìm thấy khoản mục tương ứng trong bảng cũ trùng với khoản mục đang xét trong bảng mới
        if (kt == false) return false
      }

      for (let itemCu of tmp_ds_tien_khoanmuc_cu) {
        kt = false
        for (let itemMoi of tmp_ds_tien_khoanmuc_moi) {
          vtien_bd = 0
          vvat_bd = 0
          vtien_moi = 0
          vvat_moi = 0
          if (itemMoi["khoanmuctt_id"] == itemCu["khoanmuctt_id"]) {
            vtien_moi = parseFloat(itemMoi.tien)
            vvat_moi = parseFloat(itemMoi.vat)
            vtien_bd = parseFloat(itemCu.tien)
            vvat_bd = parseFloat(itemCu.vat)

            if (vtien_bd == vtien_moi && vvat_bd == vvat_moi) {
              kt = true
              break
            }
          }
        }

        // không tìm thấy khoản mục tương ứng trong bảng cũ trùng với khoản mục đang xét trong bảng mới
        if (kt == false) return false
      }

      return kt
    },
    async phathanh_hddt_dieuchinh_td (dtKhoanmucCu, dtKhoanmucMoi) {
      if (!dtKhoanmucCu && !dtKhoanmucMoi) {
        return "OK"
      }
      var sKq = ""
      var dtProduct = structuredClone(dtKhoanmucCu)
      // so sánh khoản mục cũ và mới
      // PRODNAME, TONGTIEN,PRODPRICE,TIEN,VAT,AMOUNT
      var i1, i2 // row bảng cũ, bảng mới trùng nhau
      var bl1 = false // kiểm tra có row trùng nhau không
      var tongtien_cu = 0
      var tongtien_moi = 0
      var dtCu = structuredClone(dtKhoanmucCu)
      var dtMoi = structuredClone(dtKhoanmucMoi)
      for (var i = 0; i < dtKhoanmucCu.length; i++) {
        bl1 = false
        var tongtien, prodprice, tien, vat, amount
        tongtien_cu += parseFloat(dtKhoanmucCu[i]["tongtien"])
        for (var j = 0; j < dtKhoanmucMoi.length; j++) {
          if (dtKhoanmucCu[i]["PRODNAME"] === dtKhoanmucMoi[j]["PRODNAME"]) {
            // Cũ mới đều có, so sánh tiền
            tongtien = parseFloat(dtKhoanmucMoi[j]["tongtien"]) - parseFloat(dtKhoanmucCu[i]["tongtien"])
            prodprice = parseFloat(dtKhoanmucMoi[j]["prodprice"]) - parseFloat(dtKhoanmucCu[i]["prodprice"])
            tien = parseFloat(dtKhoanmucMoi[j]["tien"]) - parseFloat(dtKhoanmucCu[i]["tien"])
            vat = parseFloat(dtKhoanmucMoi[j]["vat"]) - parseFloat(dtKhoanmucCu[i]["vat"])
            amount = parseFloat(dtKhoanmucMoi[j]["amount"]) - parseFloat(dtKhoanmucCu[i]["amount"])
            if (tongtien != 0) {
              var dr = dtCu[i]
              if (tongtien < 0) {
                dr["PRODNAME"] = "Giảm doanh thu " + dr["PRODNAME"]
              } else {
                dr["PRODNAME"] = "Tăng doanh thu " + dr["PRODNAME"]
              }
              // sửa lại tiền
              dr["tongtien"] = tongtien
              dr["prodprice"] = prodprice
              dr["tien"] = tien
              dr["vat"] = vat
              dr["amount"] = amount
              dtProduct.push(dr)
            }
            bl1 = true
            break
          }
        }

        if (!bl1)// Cũ có mới không có, điều chỉnh tăng hoặc giảm tùy theo tiền âm hay dương
        {
          var dr = dtCu[i]

          tongtien = parseFloat(dtCu[i]["tongtien"])
          prodprice = parseFloat(dtCu[i]["prodprice"])
          tien = parseFloat(dtCu[i]["tien"])
          vat = parseFloat(dtCu[i]["vat"])
          amount = parseFloat(dtCu[i]["amount"])
          if (tongtien < 0)// Nếu cũ âm, mới ko có, ĐC tăng
          {
            dr["PRODNAME"] = "Tăng doanh thu " + dr["PRODNAME"]
          } else {
            dr["PRODNAME"] = "Giảm doanh thu " + dr["PRODNAME"]
          }
          dr["tongtien"] = -tongtien
          dr["prodprice"] = -prodprice
          dr["tien"] = -tien
          dr["vat"] = -vat
          dr["amount"] = -amount
          dtProduct.push(dr)
        }
      }
      for (var i = 0; i < dtKhoanmucMoi.length; i++) {
        bl1 = false
        var tongtien, prodprice, tien, vat, amount
        tongtien_moi += parseFloat(dtKhoanmucMoi[i]["tongtien"])
        for (var j = 0; j < dtKhoanmucCu.length; j++) {
          if (dtKhoanmucCu[j]["PRODNAME"] == dtKhoanmucMoi[i]["PRODNAME"]) {
            bl1 = true
            break
          }
        }
        if (!bl1)// Mới có cũ không có, điều chỉnh tăng giảm tùy thuộc dấu của khoản mục
        {
          var dr = dtMoi[i]
          tongtien = parseFloat(dtMoi[i]["tongtien"])
          if (tongtien < 0)// Cũ ko có, mới âm -> DC giảm
          { dr["PRODNAME"] = "Giảm doanh thu " + dr["PRODNAME"] }
          dtProduct.push(dr)
        }
      }

      if (dtProduct.length > 0) {
        var ds = await service.lay_tt_hddt_dc({"vhdkh_id": this.hdkh_id})
        if (!ds.error_msg && ds.data && ds.data.length > 0) {
          var vthaotac = 2, vkieu = 2
          if (tongtien_cu == tongtien_moi) {
            await service.sp_capnhat_phieutt_hd_hdkh_id({"p_hdkh_id": this.hdkh_id})
          } else if (tongtien_cu < tongtien_moi) {
            vkieu = 1
          }
          var vifkey = ds.data[0]["hddt_fkey"]
          if (!vifkey) {
            sKq = "OK"
            return sKq
          }
          var vdc_tt = 1// 1: Điều chỉnh, 2 thay thế
          // Kiểm tra hóa đơn trước đó đã được điều chỉnh hay chưa
          // Nếu điều chỉnh rồi thì không cho thay thế nữa
          var sp_kt_dieuchinh_hddtResult = service.sp_kt_dieuchinh_hddt({"p_hdkh_id": this.hdkh_id})
          if (sp_kt_dieuchinh_hddtResult.data && sp_kt_dieuchinh_hddtResult.data.length == 0) {
            // view form chon dieu chinh or thay the
            this.$refs.popupDieuChinh.showModal()
            vdc_tt = this.getPicked()
          }
          var dtThongTin = ds.data
          var vphieutt_id = parseInt(dtThongTin[0]["phieutt_id"])
          var vhdkh_id = parseInt(dtThongTin[0]["hdkh_id"])
          var vma_gd = dtThongTin[0]["ma_gd"]
          var vten_kh = dtThongTin[0]["ten_kh"]
          var vdiachi_kh = dtThongTin[0]["diachi_kh"]
          var vmst = dtThongTin[0]["mst"]
          var vten_nh = dtThongTin[0]["ten_nh"]
          var vstk = dtThongTin[0]["stk"]
          var vemail = dtThongTin[0]["email"]
          var vfax = dtThongTin[0]["so_fax"]
          var vsodt = dtThongTin[0]["so_dt"]
          var vndd = dtThongTin[0]["nguoi_dd"]
          var vloai_kh = dtThongTin[0]["loai_kh"]
          var vhttt = dtThongTin[0]["httt"]
          // Xuất hóa đơn điều chỉnh giảm hoặc thay thế
          if (vdc_tt == 1)// Điều chỉnh
          {
            vthaotac = 2
          } else// thay thế
          {
            vthaotac = 3
            dtProduct = dtKhoanmucMoi
          }
          sKq = this.dongbo_hddt_tudong(vthaotac, vkieu, vifkey, vphieutt_id, vhdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk, vemail, vfax, vsodt, vndd, vloai_kh, vhttt, dtProduct)
          if (sKq != "OK") {
            if (vdc_tt == 1) { sKq = "Phát hành hóa đơn điều chỉnh thất bại!" + sKq } else { sKq = "Phát hành hóa đơn thay thế thất bại!" + sKq }
          }
        }
      } else {
        sKq = "OK"
      }
      return sKq
    },
    async confirm (msg) {
      var isConfirm = await this.$bvModal.msgBoxConfirm(msg,
        {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy"
        })
      return isConfirm
    },
    TaoDuLieuBDCT (tb_hddt) {
      try {
        let dsBDHDDTCT = []
        for (let i = 0; i < tb_hddt.length; i++) {
          if (tb_hddt[i]["PRODNAME"].toString() != "") {
            let row = {}
            row.BDHDDT_ID = 0
            row.DONGIA = Math.abs(Number(tb_hddt[i]["PRODPRICE"].toString()))
            row.DONVITINH = tb_hddt[i]["PRODUNIT"].toString().trim()
            row.NOIDUNG = tb_hddt[i]["PRODNAME"].toString().trim()
            row.SOLUONG = Number(tb_hddt[i]["PRODQUANTITY"].toString())
            row.STT = i + 1
            row.THANHTIEN = Math.abs(Number(tb_hddt[i]["AMOUNT"].toString()))
            dsBDHDDTCT.push(row)
          }
        }
        return dsBDHDDTCT
      } catch (error) {
        this.$toast.error(error)
      }
    },
    async GetInvoiceXml_DieuChinh (i_kieu_dc, i_ten_kh, i_ma_gd, i_mst, i_diachi_ct, i_stk, i_so_dt, i_httt, i_dt, obj) {
      try {
        // let tongtien_hddt = 0, tongvat_hddt = 0, tong_hddt = 0;
        let str = ""
        let sinh_fkey = await this.axios.get("/web-ccdv/thanhtoan/sinh_fkey?kieu=" + i_kieu_dc)
        let fkey_new = sinh_fkey.data.data
        // new LapHopDongFacade2().SINH_FKEY(1);
        str += "<AdjustInv>"
        str += "<key>" + fkey_new + "</key>"
        str += "<CusCode>" + i_ma_gd + "</CusCode>"
        str += "<CusName><![CDATA[" + obj.CusName + "]]></CusName>"
        str += "<CusAddress><![CDATA[" + obj.CusAddress + "]]></CusAddress>"
        str += "<CusPhone>" + obj.CusPhone + "</CusPhone>"
        str += "<CusTaxCode><![CDATA[" + obj.CusTaxCode + "]]></CusTaxCode>"
        str += "<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>"
        str += "<KindOfService></KindOfService>"
        let type = i_kieu_dc == 1 ? "2" : i_kieu_dc == 2 ? "3" : "4"
        str += "<Type>" + type + "</Type>"
        str += "<Products>"
        for (let i = 0; i < i_dt; i++) {
          if (i_dt[i]["prodname"] != "") {
            str += "<Product>"
            str += "<ProdName>" + i_dt[i]["prodname"] + "</ProdName>"
            str += "<ProdUnit>" + i_dt[i]["produnit"] + "</ProdUnit>"
            str += "<ProdQuantity>" + i_dt[i]["prodquantity"] + "</ProdQuantity>"
            str += "<ProdPrice>" + Math.abs(Number(i_dt[i]["prodprice"])) + "</ProdPrice>"
            str += "<Amount>" + Math.abs(Number(i_dt[i]["amount"])) + "</Amount>"
            str += "</Product>"

            this.tt_hddt.tongtien_hddt += Number(i_dt[i]["tien"])
            this.tt_hddt.tongvat_hddt += Number(i_dt[i]["vat"])
            this.tt_hddt.tong_hddt += Number(i_dt[i]["tongtien"])
          }
        }
        this.tt_hddt.tong_hddt = Math.abs(this.tt_hddt.tong_hddt)
        this.tt_hddt.tongvat_hddt = Math.abs(this.tt_hddt.tongvat_hddt)
        this.tt_hddt.tongtien_hddt = Math.abs(this.tt_hddt.tongtien_hddt)
        str += "</Products>"
        str += "<Total>" + this.tt_hddt.tongtien_hddt.toString() + "</Total>"
        str += "<VATRate>10</VATRate>"
        str += "<VATAmount>" + this.tt_hddt.tongvat_hddt.toString() + "</VATAmount>"
        str += "<Amount>" + this.tt_hddt.tong_hddt.toString() + "</Amount>"

        let DocTien = ""
        let tienkt = ""
        tienkt = this.tt_hddt.tong_hddt.toString()
        if (this.tt_hddt.tong_hddt.toString().includes("-")) { tienkt = tienkt.replace("-", "") }
        // DocTien = CommonFunction.DocSo(tienkt, ',', "phẩy") + " đồng";
        let getMoneyTotext = await this.axios.post("/web-tracuu/tracuu_th/fn_doisosangchu", {
          money: Number(tienkt)
        })
        if (getMoneyTotext) {
          DocTien = getMoneyTotext.data.data
        }
        let TienBangChu = ""
        TienBangChu += DocTien.toString().toUpperCase()
        for (let i = 1; i < DocTien.Length; i++) {
          TienBangChu += DocTien[i]
        }
        if (this.tt_hddt.tong_hddt.toString().includes("-")) { this.tt_hddt.tbc = "Âm " + TienBangChu } else { this.tt_hddt.tbc = TienBangChu }

        str += "<AmountInWords>" + this.tt_hddt.tbc + "</AmountInWords>"
        str += "<Teller>" + this.tt_nd.ma_nv + "</Teller>"
        str += "</AdjustInv>"
        return str
      } catch (error) {
        this.$toast.error(error)
      }
    },
    async GetInvoiceXml_Cus (i_ten_kh, i_ma_gd, i_mst, i_diachi_kh, i_ten_nh,
      i_stk, i_email, i_sdt, i_loaikh, i_so_fax) {
      try {
        let str = ""
        str += "<Customers>"
        str += "<Customer>"
        str += "<Name><![CDATA[" + i_ten_kh + "]]></Name>"
        str += "<Code>" + i_ma_gd + "</Code>"
        str += "<TaxCode><![CDATA[" + i_mst + "]]></TaxCode>"
        str += "<Address><![CDATA[" + i_diachi_kh + "]]></Address>"
        str += "<BankAccountName></BankAccountName>"
        if (i_ten_nh != "") { str += "<BankName>" + i_ten_nh + "</BankName>" } else { str += "<BankName></BankName>" }
        str += "<BankNumber>" + i_stk + "</BankNumber>"
        str += "<Email>" + i_email + "</Email>"
        str += "<Fax>" + i_so_fax + "</Fax>"
        str += "<Phone>" + i_sdt + "</Phone>"
        str += "<ContactPerson>" + i_ten_kh + "</ContactPerson>"
        str += "<RepresentPerson>" + i_ten_kh + "</RepresentPerson>"
        if (i_loaikh != "1") { str += "<CusType>1</CusType>" } else { str += "<CusType>0</CusType>" }
        str += "</Customer>"
        str += "</Customers>"
        return str
      } catch (error) {
        console.error(error.msg)
      }
    },
    async GetInvoiceXml (i_ten_kh, i_ma_gd, i_mst, i_diachi_ct, i_stk, i_so_dt, i_httt, i_dt) {
      let str = ""
      let sinh_fkey = await this.axios.get("/web-ccdv/thanhtoan/sinh_fkey?kieu=" + i_kieu_dc)
      this.tt_hddt.Fkey = sinh_fkey.data.data
      str += "<Invoices>"
      str += "<Inv>"
      str += "<key>" + this.tt_hddt.Fkey + "</key>"
      str += "<Invoice>"
      str += "<CusCode>" + i_ma_gd + "</CusCode>"
      str += "<CusName><![CDATA[" + i_ten_kh + "]]></CusName>"
      str += "<CusAddress><![CDATA[" + i_diachi_ct + "]]></CusAddress>"
      str += "<CusPhone>" + i_so_dt + "</CusPhone>"
      str += "<CusTaxCode><![CDATA[" + i_mst + "]]></CusTaxCode>"
      str += "<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>"
      str += "<KindOfService></KindOfService>"
      str += "<Products>"
      for (let i = 0; i < i_dt.length; i++) {
        if (i_dt.Rows[i]["prodname"].toString() != "") {
          str += "<Product>"
          str += "<ProdName>" + i_dt.Rows[i]["prodname"].toString() + "</ProdName>"
          str += "<ProdUnit>" + i_dt.Rows[i]["produnit"].toString() + "</ProdUnit>"
          str += "<ProdQuantity>" + i_dt.Rows[i]["prodquantity"].toString() + "</ProdQuantity>"
          str += "<ProdPrice>" + Math.abs(Number(i_dt.Rows[i]["prodprice"].toString())) + "</ProdPrice>"
          str += "<Amount>" + Math.abs(Number(i_dt.Rows[i]["amount"].toString())) + "</Amount>"
          str += "</Product>"

          this.tt_hddt.tongtien_hddt += Number(i_dt.Rows[i]["tien"].toString())
          this.tt_hddt.tongvat_hddt += Number(i_dt.Rows[i]["vat"].toString())
          this.tt_hddt.tong_hddt += Number(i_dt.Rows[i]["tongtien"].toString())
        }
      }
      this.tt_hddt.tong_hddt = Math.Abs(tong_hddt)
      this.tt_hddt.tongvat_hddt = Math.Abs(tongvat_hddt)
      this.tt_hddt.tongtien_hddt = Math.Abs(tongtien_hddt)
      str.Append("</Products>")
      str.Append("<Total>" + this.tt_hddt.tongtien_hddt.toString() + "</Total>")
      str.Append("<DiscountAmount></DiscountAmount>")
      str.Append("<VATRate>10</VATRate>")
      str.Append("<VATAmount>" + this.tt_hddt.tongvat_hddt.toString() + "</VATAmount>")
      str.Append("<Amount>" + this.tt_hddt.tong_hddt.toString() + "</Amount>")

      let DocTien = ""
      let tienkt = ""
      tienkt = this.tt_hddt.tong_hddt.toString()
      if (this.tt_hddt.tong_hddt.toString().includes("-")) { tienkt = tienkt.replace("-", "") }
      // DocTien = CommonFunction.DocSo(tienkt, ',', "phẩy") + " đồng";
      let getMoneyTotext = await this.axios.post("/web-tracuu/tracuu_th/fn_doisosangchu", {
        money: Number(tienkt)
      })
      if (getMoneyTotext) {
        DocTien = getMoneyTotext.data.data
      }
      let TienBangChu = ""
      TienBangChu += DocTien.toString().toUpperCase()
      // for (let i = 1; i < DocTien.Length; i++)
      // {
      //     TienBangChu += DocTien[i];
      // }
      if (this.tt_hddt.tong_hddt.toString().includes("-")) { this.tt_hddt.tbc = "Âm " + TienBangChu } else { this.tt_hddt.tbc = TienBangChu }

      str += "<AmountInWords>" + tbc + "</AmountInWords>"
      str += "<Teller>" + tt_nd.ma_nv + "</Teller>"
      str += "</Invoice>"
      str += "</Inv>"
      str += "</Invoices>"
      return str
    },
    async dongbo_hddt_tudong (vthaotac, vkieu, vifkey, vphieutt_id, vhdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk,
      vemail, vfax, vsodt, vndd, vloai_kh, vhttt, product) {
      var invToken = ""
      try {
        if (vthaotac == 2) // Điều chỉnh hóa đơn
        {
          if (vkieu == 1 || vkieu == 2 || vkieu == 3) {
          // Điều chỉnh hóa đơn
            if (vifkey == "") {
              return "ERR: Không có thông tin Fkey cần điều chỉnh"
            }

            var ds_token = await service.sp_lay_ds_phieutt_hd_loaihoadon({"p_phieutt_id": vphieutt_id})
            if (ds_token.data && ds_token.data.length > 0) {
              invToken = ds_token.data[0]["ten_loaihd"] + ";" + ds_token.data[0]["seri"] + ";" + ds_token.data[0]["soseri"]
            } else {
              return "ERR: Không tìm thấy hóa đơn cần điều chỉnh !"
            }

            var bdhddt_cha_id = 0
            var sp_lay_ds_bd_hddt_maxResult = await service.sp_lay_ds_bd_hddt_max({"p_hdkh_id": this.hdkh_id}
            )
            if (sp_lay_ds_bd_hddt_maxResult.data && sp_lay_ds_bd_hddt_maxResult.data.length > 0) {
              bdhddt_cha_id = parseInt(sp_lay_ds_bd_hddt_maxResult.data[0]["max(nvl(bdhddt_id,0))"])
            }

            let downloadInvNoPay = await this.axios.post("/tichhop/vnptinvoice/downloadInvNoPay", {
              invToken: invToken
            })
            let xml = downloadInvNoPay.data.data
            let objHoaDonDienTu
            // invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
            if (!xml) {
              return "ERR: downloadInvNoPay"
            } else {
              // XmlDocument doc = new XmlDocument();
              // doc.LoadXml(xml);
              // XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
              // XmlDocument doc1 = new XmlDocument();
              // doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
              // objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
              xml = "<Content>" + xml + "</Content>"
              objHoaDonDienTu = JSON.parse(convert.xml2json(xml, {compact: true}))
              // console.log('asdads ', JSON.parse(objHoaDonDienTu));
            }
            this.tt_hddt.tongtien_hddt = 0
            this.tt_hddt.tongvat_hddt = 0
            this.tt_hddt.tong_hddt = 0

            let sohoadon = "", pattern = "", seri = "", loaihoadon_id = 0, seri_id = 0
            let sp_tt_loaihoadon_seri = await this.axios.get("/web-hopdong/thaydoiloaihinhtb/sp_tt_loaihoadon_seri")

            let ds_hd_seri = []
            ds_hd_seri = sp_tt_loaihoadon_seri.data.data
            if (ds_hd_seri.length > 0) {
              loaihoadon_id = Number(ds_hd_seri[0]["loaihoadon_id"])
              pattern = ds_hd_seri[0]["ten_loaihd"]
              seri_id = Number(ds_hd_seri[0]["seri_id"])
              seri = ds_hd_seri[0]["seri"]
            } else {
              return "ERR: Không tìm thấy loại hóa đơn, seri !"
            }

            // Kiểm tra hóa đơn hiện tại có đang ở trạng thái Đang sử dụng không ?
            // let kq_kt = invoice.listInvByCus_V2(vma_gd, "", "", tt_nd.user_hddt, tt_nd.pass_hddt, pattern);
            let listInvByCus_V2 = await this.axios.post("/tichhop/vnptinvoice/listInvByCus", {
              cusCode: vma_gd,
              fromDate: "",
              toDate: ""
            })
            let kq_kt = listInvByCus_V2.data.data
            if (!kq_kt) {
              return "ERR: listInvByCus"
            } else {
              // const parser = new DOMParser();
              // const doc = parser.parseFromString(kq_kt, "application/xml");

              // Data objKetQua_kt = (Data)ObjectToXML(kq_kt, typeof(Data));
              // EInvoice.Item item_kt = objKetQua_kt.Items.Where(x => x.invToken == invToken).Single();
              // let item_kt = parser.parseFromString(doc, "application/xml").getElementsByTagName('invToken')[0].childNodes[0].nodeValue;
              let item_kt_ls = JSON.parse(convert.xml2json(kq_kt))
              if (item_kt_ls && item_kt_ls.length > 0) {
                let item_kt = item_kt_ls.find(e => e["invToken"] == invToken)
                if (item_kt.status != 1) {
                  return "Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể điều chỉnh !"
                }
              }
            }

            // kichhoat:

            // let kq = invoice.adjustInv_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, GetInvoiceXml_DieuChinh(vkieu, vten_kh, vma_gd, vmst, vdiachi_kh,
            //     vstk, vsodt, vhttt, product, objHoaDonDienTu), tt_nd.user_hddt, tt_nd.pass_hddt, pattern, seri, vifkey, 0);
            // string[] result = kq.Split(':');
            let xmlInvData = this.GetInvoiceXml_DieuChinh(vkieu, vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product, objHoaDonDienTu)
            let adjustInv = await this.axios.post("/tichhop/vnptinvoice/adjustInv", {
              acPass: this.tt_nd.pass_hddt,
              account: this.tt_nd.user_hddt,
              convert: 0,
              fkey: vifkey,
              xmlInvData: xmlInvData
            })
            let kq = adjustInv.data.data
            let result = kq.split(":")
            if (result[0] == "OK") {
              try {
                let result1 = result[1].toString().split(";")
                pattern = result1[0].toString()
                seri = result1[1].toString()
                sohoadon = result1[2].toString().split("_")[1].toString()
                var fkey_new = result1[2].toString().split("_")[0].toString()
                invToken = pattern + ";" + seri + ";" + sohoadon

                this.tt_hddt.dsBDHDDT = []
                let row = {}
                // let BDHDDT_ID = 0;
                // BDHDDT_ID = getkey.GetKey("BDHDDT_ID");
                // row.BDHDDT_ID = BDHDDT_ID;
                row.MA_TT = vma_gd

                if (vkieu == 1) // Điều chỉnh tăng
                { row.KIEU_BD = 8 } else if (vkieu == 2) // Điều chỉnh giảm
                { row.KIEU_BD = 9 } else if (vkieu == 3) // Điều chỉnh thông tin
                { row.KIEU_BD = 10 } else { row.KIEU_BD = 1 }

                row.TONGTIEN = this.tt_hddt.tong_hddt
                row.NGUOI_CN = this.tt_nd.ma_nd
                row.MAY_CN = this.tt_nd.may_cn
                row.NGAY_CN = this.tt_nd.ngay_cn
                row.NHANVIEN_ID = this.tt_nd.nhanvien_id
                row.DONVI_ID = this.tt_nd.donvi_id
                row.GHICHU = "Tạo hóa đơn điện tử tự động PTTB"
                row.HDKH_ID = vhdkh_id
                row.PHIEUTT_ID = vphieutt_id
                row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern
                row.LOAIHOADON_MOI = pattern
                row.SERI_CU = objHoaDonDienTu.SerialNo
                row.SERI_MOI = seri
                row.SO_HD_CU = objHoaDonDienTu.InvoiceNo
                row.SO_HD_MOI = sohoadon
                row.SERI_MOI_ID = seri_id
                row.TRANGTHAI = 1
                row.BDHDDT_CHA_ID = bdhddt_cha_id
                row.TEN_TT = objHoaDonDienTu.CusName
                row.DIACHI_TT = objHoaDonDienTu.CusAddress
                row.SDT = objHoaDonDienTu.CusPhone
                row.MST = objHoaDonDienTu.CusTaxCode

                row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản"
                row.TIEN = this.tt_hddt.tongtien_hddt
                row.TIENBANGCHU = this.tt_hddt.tbc
                row.TYLE_VAT = 10
                row.VAT = this.tt_hddt.tongvat_hddt

                // 4.7 ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
                this.tt_hddt.dsBDHDDT.push(row)
                await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt", {
                  p_js_bd_hddt: JSON.stringify(this.tt_hddt.dsBDHDDT)
                })
                // 4.8
                // TaoDuLieuBDCT(product);
                // ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);
                let dsBDHDDTCT = [...this.TaoDuLieuBDCT(product)]
                await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt_ct", {
                  p_js_bd_hddt: JSON.stringify(dsBDHDDTCT)
                })
                // 4.9
                // let str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
                // str_update += " set hddt_fkey = '" + fkey_new + "', ";
                // str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
                // str_update += " seri_id = '" + seri_id + "',";
                // str_update += " seri = '" + seri + "',";
                // str_update += " soseri = '" + sohoadon + "'";
                // str_update += " Where phieutt_id  = " + vphieutt_id;
                // new BangThamSo().ExeSQL(str_update);
                await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_capnhat_phieutt_hd_v2", {
                  p_hddt_fkey: fkey_new,
                  p_loaihoadon_id: loaihoadon_id,
                  p_seri_id: seri_id,
                  p_seri: seri,
                  p_soseri: sohoadon,
                  p_phieutt_id: vphieutt_id
                })

                // 4.10
                // str_update = "";
                // str_update += " Update " + DatabaseConstants.DB6 + ".bd_hddt ";
                // str_update += " Set trangthai = 3, ";
                // str_update += "     ngay_td = sysdate, ";
                // str_update += "     nguoi_td = '" + tt_nd.ma_nd + "'";
                // str_update += " Where bdhddt_id = " + bdhddt_cha_id;
                // bangts.ExeSQL(str_update);
                await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_capnhat_bd_hddt_theo_bdhddt_cha_id", {
                  p_bdhddt_cha_id: bdhddt_cha_id
                })

                this.tt_hddt.Fkey = ""
                fkey_new = ""
                return "OK"
              } catch (error) {
                return "OK"
              }
            } else {
              return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq
            }
          } else {
            return "ERR: Kiểm tra lại thông tin kiểu điều chỉnh !"
          }
        } else if (vthaotac == 3) // Thay thế hóa đơn
        {
          // #region Thay thế hóa đơn
          if (vifkey == "") {
            return "ERR: Không có thông tin Fkey cần thay thế"
          }
          try {
            let ds_token = []
            let sp_lay_ds_phieutt_hd_loaihoadon = await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_phieutt_hd_loaihoadon", {
              p_phieutt_id: vphieutt_id
            })
            ds_token = [...sp_lay_ds_phieutt_hd_loaihoadon.data.data]
            if (ds_token.length > 0) {
              invToken = ds_token[0]["ten_loaihd"].ToString() + ";" + ds_token[0]["seri"].ToString() + ";" + ds_token[0]["soseri"].ToString()
            } else {
              return "ERR: Không tìm thấy hóa đơn cần điều chỉnh !"
            }

            let sohoadon = ""
            let pattern = ""
            let seri = ""
            let loaihoadon_id = 0
            let seri_id = 0

            let sp_tt_loaihoadon_seri = await this.axios.get("/web-hopdong/thaydoiloaihinhtb/sp_tt_loaihoadon_seri")
            let ds_hd_seri = []
            ds_hd_seri = sp_tt_loaihoadon_seri.data.data
            if (ds_hd_seri.length > 0) {
              loaihoadon_id = Number(ds_hd_seri[0]["loaihoadon_id"].toString())
              pattern = ds_hd_seri[0]["ten_loaihd"].toString()
              seri_id = Number(ds_hd_seri[0]["seri_id"].toString())
              seri = ds_hd_seri[0]["seri"].toString()
            } else {
              return "ERR: Không tìm thấy loại hóa đơn, seri !"
            }

            let listInvByCus_V2 = await this.axios.post("/tichhop/vnptinvoice/listInvByCus", {
              cusCode: vma_gd,
              fromDate: "",
              toDate: ""
            })
            let kq_kt = listInvByCus_V2.data.data
            if (!kq_kt) {
              return "ERR: listInvByCus"
            } else {
              let item_kt_ls = JSON.parse(convert.xml2json(kq_kt))
              if (item_kta_ls && item_kt_ls.length > 0) {
                let item_kt = item_kt_ls.find(e => e["invToken"] == invToken)
                if (item_kt.status != 1) {
                  return "Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể điều chỉnh !"
                }
              }
            }
            // CÓ THỂ KHAO SÁT THIẾU
            // Kiểm tra xem có tồn tại hóa đơn đã điều chỉnh không ?
            // string bd_tt = "";
            // bd_tt += " Select count(*) ";
            // bd_tt += " From " + DatabaseConstants.DB6 + ".bd_hddt ";
            // bd_tt += " Where hdkh_id = " + vhdkh_id;
            // bd_tt += "      and trangthai = 3 ";
            // DataSet ds_tt = bangts.getDataFromSQL(bd_tt, "bdtt");
            // if (ds_tt.Tables[0].Rows.Count > 0)
            // {
            //     if (Convert.ToInt32(ds_tt.Tables[0].Rows[0][0].ToString()) > 0)
            //         return "ERR: Tồn tại hóa đơn đã bị điều chỉnh. Bạn không thể thay thé !";
            // }
            // end kiểm tra

            let bdhddt_cha_id = 0
            let sp_lay_ds_bd_hddt_max = await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_bd_hddt_max", {
              p_hdkh_id: vhdkh_id
            })
            ds_bd_cha = [...sp_lay_ds_bd_hddt_max.data.data]
            if (ds_bd_cha.length > 0) { bdhddt_cha_id = (ds_bd_cha[0]["max(nvl(bdhddt_id,0))"]) }

            let downloadInvNoPay = await this.axios.post("/tichhop/vnptinvoice/downloadInvNoPay", {
              invToken: invToken
            })
            let xml = downloadInvNoPay.data.data
            let objHoaDonDienTu
            if (!xml) {
              return "ERR: Không có thông tin hóa đơn cần thay thế !"
            } else {
              objHoaDonDienTu = convert.xml2json(xml, {compact: true})
            }

            this.tt_hddt.tongtien_hddt = 0
            this.tt_hddt.tongvat_hddt = 0
            this.tt_hddt.tong_hddt = 0

            // kichhoat:

            let xmlInvData = this.GetInvoiceXml_ThayThe(vma_gd, vhttt, product, JSON.parse(objHoaDonDienTu))
            let replaceInv = await this.axios.post("/tichhop/vnptinvoice/replaceInv", {
              acPass: this.tt_nd.pass_hddt,
              account: this.tt_nd.user_hddt,
              convert: 0,
              fkey: vifkey,
              xmlInvData: xmlInvData
            })
            let kq = replaceInv.data.data ? replaceInv.data.data : []
            let result = kq.split(":")
            if (result[0] == "OK") {
              let objHoaDonDienTuEdit
              let result1 = result[1].toString().split(";")
              pattern = result1[0].toString()
              seri = result1[1].toString()
              sohoadon = result1[2].toString().split("_")[1].toString()
              fkey_new = result1[2].toString().split("_")[0].toString()
              invToken = pattern + ";" + seri + ";" + sohoadon
              let resXML1 = await this.axios.post("/tichhop/vnptinvoice/downloadInvNoPay", {
                invToken: invToken
              })
              let xml1 = resXML1.data.data ? resXML1.data.data : ""
              // invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
              if (xml1.startsWith("ERR:") || !xml1) {
                return "ERR: Không có thông tin hóa đơn đã thay thế !"
              } else {
                // XmlDocument doc2 = new XmlDocument();
                // doc2.LoadXml(xml1);
                // XmlNode xnList = doc2.SelectNodes("/Invoice/Content").Item(0);
                // XmlDocument doc3 = new XmlDocument();
                // doc3.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                // objHoaDonDienTuEdit = (Content)ObjectToXML(doc3.InnerXml, typeof(Content));
                xml1 = "<Content>" + xml1 + "</Content>"
                objHoaDonDienTuEdit = convert.xml2json(xml, {compact: true})
              }

              let dsBDHDDT = []
              let row = {}

              // BDHDDT_ID = getkey.GetKey("BDHDDT_ID");
              // row.BDHDDT_ID = BDHDDT_ID;
              row.MA_TT = vma_gd
              row.THONGTINHD_CU = objHoaDonDienTu
              row.THONGTINHD_MOI = objHoaDonDienTuEdit
              row.KIEU_BD = 2

              row.TONGTIEN = this.tt_hddt.tong_hddt
              row.NGUOI_CN = this.tt_nd.ma_nd
              row.MAY_CN = this.tt_nd.may_cn
              row.NHANVIEN_ID = this.tt_nd.nhanvien_id
              row.DONVI_ID = this.tt_nd.donvi_id
              row.NGAY_CN = this.tt_nd.ngay_cn
              row.GHICHU = "Thay thế hóa đơn điện tử tự động PTTB"
              row.HDKH_ID = vhdkh_id
              row.PHIEUTT_ID = vphieutt_id
              row.LOAIHOADON_CU = JSON.parse(objHoaDonDienTu).InvoicePattern
              row.LOAIHOADON_MOI = JSON.parse(objHoaDonDienTuEdit).InvoicePattern
              row.SERI_CU = JSON.parse(objHoaDonDienTu).SerialNo
              row.SERI_MOI = JSON.parse(objHoaDonDienTuEdit).SerialNo
              row.SO_HD_CU = JSON.parse(objHoaDonDienTu).InvoiceNo
              row.SO_HD_MOI = JSON.parse(objHoaDonDienTuEdit).InvoiceNo
              row.SERI_MOI_ID = seri_id
              row.TRANGTHAI = 1
              row.BDHDDT_CHA_ID = bdhddt_cha_id

              row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản"
              row.TIEN = this.tt_hddt.tongtien_hddt
              row.TIENBANGCHU = this.tt_hddt.tbc
              row.TYLE_VAT = 10
              row.VAT = this.tt_hddt.tongvat_hddt

              // 4.7 ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
              dsBDHDDT.push(row)
              await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt", {
                p_js_bd_hddt: JSON.stringify(dsBDHDDT)
              })

              // 4.8
              // TaoDuLieuBDCT(product);
              // ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);
              let dsBDHDDTCT = [...this.TaoDuLieuBDCT(product)]
              await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt_ct", {
                p_js_bd_hddt: JSON.stringify(dsBDHDDTCT)
              })

              // string str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
              // str_update += " set hddt_fkey = '" + fkey_new + "', ";
              // str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
              // str_update += " seri_id = '" + seri_id + "',";
              // str_update += " seri = '" + seri + "',";
              // str_update += " soseri = '" + sohoadon + "'";
              // str_update += " Where phieutt_id  = " + vphieutt_id;
              // new BangThamSo().ExeSQL(str_update);
              await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_capnhat_phieutt_hd_v2", {
                p_hddt_fkey: fkey_new,
                p_loaihoadon_id: loaihoadon_id,
                p_seri_id: seri_id,
                p_seri: seri,
                p_soseri: sohoadon,
                p_phieutt_id: vphieutt_id
              })

              // str_update = "";
              // str_update += " Update " + DatabaseConstants.DB6 + ".bd_hddt ";
              // str_update += " Set trangthai = 2, ";
              // str_update += "     ngay_td = sysdate, ";
              // str_update += "     nguoi_td = '" + tt_nd.ma_nd + "'";
              // str_update += " Where bdhddt_id = " + bdhddt_cha_id;
              // bangts.ExeSQL(str_update);
              await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_capnhat_bd_hddt_theo_bdhddt_cha_id", {
                p_bdhddt_cha_id: bdhddt_cha_id
              })
              this.tt_hddt.Fkey = ""
              fkey_new = ""
              return "OK"
            } else {
              // if (kq.StartsWith("ERR:20"))
              // {
              //    string lay_seri = invoice.getSerialByPattern_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, pattern);
              //    if (lay_seri.StartsWith("ERR"))
              //    {
              //        return "ERR: Lỗi lấy seri điện tử ";
              //    }
              //    else
              //    {
              //        string[] result_s = lay_seri.Split(';');
              //        seri = result_s[0].ToString();
              //        loi_het_seri = true;
              //        goto kichhoat;
              //    }
              // }
              // else
              return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq
            }
          } catch (error) {
            return "ERR: " + error
          }
        } else if (vthaotac == 5) // Hủy hóa đơn
        {
          return "ERR"
        } else if (vthaotac == 6) // Xuất hóa đơn
        {
          // #region Xuất hóa đơn
          try {
            this.tt_hddt.tongtien_hddt = 0
            this.tt_hddt.tongvat_hddt = 0
            this.tt_hddt.tong_hddt = 0

            let sohoadon = ""
            let pattern = ""
            let seri = ""
            let loaihoadon_id = 0
            let seri_id = 0

            let sp_tt_loaihoadon_seri = await this.axios.get("/web-hopdong/thaydoiloaihinhtb/sp_tt_loaihoadon_seri")

            let ds_hd_seri = []
            ds_hd_seri = sp_tt_loaihoadon_seri.data.data
            if (ds_hd_seri.length > 0) {
              loaihoadon_id = Number(ds_hd_seri[0]["loaihoadon_id"])
              pattern = ds_hd_seri[0]["ten_loaihd"]
              seri_id = Number(ds_hd_seri[0]["seri_id"])
              seri = ds_hd_seri[0]["seri"]
            } else {
              return "ERR: Không tìm thấy loại hóa đơn, seri !"
            }

            // let kq1 = invoice.UpdateCus_V2(
            //   GetInvoiceXml_Cus(vten_kh, vma_gd, vmst, vdiachi_kh, vten_nh, vstk, vemail, vsodt, vloai_kh, vfax),
            //   tt_nd.user_hddt, tt_nd.pass_hddt, 0);
            let xmlCusData = this.GetInvoiceXml_Cus(vten_kh, vma_gd, vmst, vdiachi_kh, vten_nh, vstk, vemail, vsodt, vloai_kh, vfax)
            let UpdateCus_V2 = await this.axios.post("/tichhop/vnptinvoice/updateCus", {
              convert: 0,
              xmlCusData: xmlCusData
            })
            let kq1 = UpdateCus_V2.data.data
            if (kq1 != "ok") {
              return "ERR: Có lỗi trong quá trình cập nhật thông tin khách hàng : " + kq1
            }

            // let kq = invoice.ImportAndPublishInv_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td,
            //     GetInvoiceXml(vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product),
            //     tt_nd.user_hddt, tt_nd.pass_hddt, pattern, seri, 0);
            let xmlInvData = this.GetInvoiceXml(vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product)
            let importAndPublishInv = await this.axios.post("/tichhop/vnptinvoice/importAndPublishInv", {
              acPass: this.tt_nd.pass_hddt_td,
              account: this.tt_nd.user_hddt,
              convert: 0,
              pattern: pattern,
              serial: seri,
              xmlInvData: xmlInvData
            })
            let kq = importAndPublishInv.data.data ? importAndPublishInv.data.data : []
            let result = kq.split(":")
            if (result[0] == "OK") {
              let result1 = result[1].toString().split(";")
              let InvoicePattern1 = result1[0].toString()
              let result2 = result1[1].toString().split("-")
              let SerialNo1 = result2[0].toString()
              let result3 = result2[1].toString().split("_")
              let InvoiceNo1 = result3[1].toString()

              let kq2 = ""
              // kq2 = invoice.confirmPaymentFkey_V2(Fkey, tt_nd.user_hddt, tt_nd.pass_hddt, InvoicePattern1, SerialNo1);
              let confirmPaymentFkey = await this.axios.post("/tichhop/vnptinvoice/confirmPaymentFkey", {
                fKey: this.tt_hddt.Fkey,
                convert: 0,
                pattern: pattern,
                serial: seri
              })
              kq2 = confirmPaymentFkey.data.data
              if (kq2 != "OK:") {
                return "ERR: Lỗi thanh toán HĐĐT : " + kq2
              }

              // if (loi_het_seri) {
              //   // code insert vào 2 bảng seri_hd và loaihoadon_seri
              //   // update thứ tự, trạng thái vào bảng seri_hd
              // }

              // let str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
              // str_update += " set hddt_fkey = '" + Fkey + "', ";
              // str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
              // str_update += " seri_id = '" + seri_id + "',";
              // str_update += " seri = '" + SerialNo1 + "',";
              // str_update += " soseri = '" + InvoiceNo1 + "',";
              // str_update += " hddt_status = '" + kq2 + "'";
              // str_update += " Where phieutt_id  = " + vphieutt_id;
              // new BangThamSo().ExeSQL(str_update);
              await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_capnhat_phieutt_hd_v2", {
                p_hddt_fkey: fkey_new,
                p_loaihoadon_id: loaihoadon_id,
                p_seri_id: seri_id,
                p_seri: seri,
                p_soseri: sohoadon,
                p_phieutt_id: vphieutt_id
              })

              let invToken = InvoicePattern1 + ";" + SerialNo1 + ";" + InvoiceNo1
              // let xml = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
              let downloadInvNoPay = await this.axios.post("/tichhop/vnptinvoice/downloadInvNoPay",
                {
                  invToken: invToken
                })
              let xml = downloadInvNoPay.data.data
              if (xml.startsWith("ERR:") || !xml) { return "ERR: Có lỗi cập nhật biến động hóa đơn điện tử: " + xml } else {
                // XmlDocument doc = new XmlDocument();
                // doc.LoadXml(xml);
                // XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
                // XmlDocument doc1 = new XmlDocument();
                // doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                // objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                // objHoaDonDienTuEdit = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                xml1 = "<Content>" + xml1 + "</Content>"
                objHoaDonDienTu = convert.xml2json(xml, {compact: true})
                objHoaDonDienTuEdit = convert.xml2json(xml, {compact: true})
              }

              this.tt_hddt.dsBDHDDT = []
              let row = {}
              // BDHDDT_ID = new GetKeyFacade().GetKey("BDHDDT_ID");
              // row.BDHDDT_ID = BDHDDT_ID;
              row.MA_TT = objHoaDonDienTuEdit.CusCode
              row.KIEU_BD = 5
              row.THONGTINHD_CU = convert.json2xml(objHoaDonDienTu)
              row.THONGTINHD_MOI = convert.json2xml(objHoaDonDienTuEdit)
              row.TONGTIEN = this.tt_hddt.tong_hddt
              row.NGUOI_CN = this.tt_nd.ma_nd
              row.MAY_CN = this.tt_nd.may_cn
              row.NGAY_CN = this.tt_nd.ngay_cn
              row.NHANVIEN_ID = this.tt_nd.nhanvien_id
              row.DONVI_ID = this.tt_nd.donvi_id
              row.GHICHU = "Tạo hóa đơn điện tử tự động PTTB"
              row.HDKH_ID = vhdkh_id
              row.PHIEUTT_ID = vphieutt_id
              row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern
              row.LOAIHOADON_MOI = objHoaDonDienTuEdit.InvoicePattern
              row.SERI_CU = objHoaDonDienTu.SerialNo
              row.SERI_MOI = objHoaDonDienTuEdit.SerialNo
              row.SO_HD_CU = objHoaDonDienTu.InvoiceNo
              row.SO_HD_MOI = objHoaDonDienTuEdit.InvoiceNo
              row.SERI_MOI_ID = seri_id
              row.TRANGTHAI = 1

              row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản"
              row.TIEN = this.tt_hddt.tongtien_hddt
              row.TIENBANGCHU = this.tt_hddt.tbc
              row.TYLE_VAT = 10
              row.VAT = this.tt_hddt.tongvat_hddt

              this.tt_hddt.dsBDHDDT.push(row)
              // ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
              // 4.7 ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
              // dsBDHDDT.push(row)
              await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt", {
                p_js_bd_hddt: JSON.stringify(this.tt_hddt.dsBDHDDT)
              })

              // TaoDuLieuBDCT(product);
              // ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);
              // 4.8
              // TaoDuLieuBDCT(product);
              // ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);
              let dsBDHDDTCT = [...this.TaoDuLieuBDCT(product)]
              await this.axios.post("/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt_ct", {
                p_js_bd_hddt: JSON.stringify(dsBDHDDTCT)
              })

              this.tt_hddt.Fkey = ""
              return "OK"
            } else {
              return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq
            }
          } catch (ex) {
            console.error(ex)
          }
        } else {
          return "ERR: Kiểm tra lại thông tin kiểu điều chỉnh !"
        }
      } catch (error) {
        return "ERR: " + error
      }
    },

    async sua_km () {
      console.log("dsKM_HDTB, dsDC_HDTB, dsCTTHD", this.dsKM_HDTB, this.dsDC_HDTB, this.dsCTTHD)
      try {
        this.loading(true)
        if (this.hdtb_id == 0) {
          this.$toast.error("Bạn phải lấy thông tin hợp đồng trước khi sửa khuyến mại!")
          return
        }

        // Bổ sung thong tin hdtb_id vao bang khuyenmai, dat coc
        for (let i = 0; i < this.dsDC_HDTB.length; i++) {
          this.dsDC_HDTB[i].HDTB_ID = this.hdtb_id
        }

        for (let i = 0; i < this.dsKM_HDTB.length; i++) {
          this.dsKM_HDTB[i].HDTB_ID = this.hdtb_id
        }

        var update_tien = false
        var vphieutt_id = -1
        if (!this.kiemtra_khoanmuc_tien()) {
          var isConfirm = await this.confirm("Tiền hợp đồng đã thay đổi, bạn có muốn tiếp tục cập nhật?")
          if (!isConfirm) return
          var dsPhieuChuaTT = await service.lay_ds_phieu_chuatt(this.hdtb_id)
          if (!dsPhieuChuaTT.error_msg && dsPhieuChuaTT.data.length > 0) {
            if (dsPhieuChuaTT.data[0].trangthai == 1) {
              isConfirm = await this.confirm("Phiếu thanh toán đã thu tiền sẽ chuyển thành trạng thái chưa thu tiền và hủy hóa đơn cũ, bạn có muốn sửa?")
              if (!isConfirm) return
            }
            vphieutt_id = dsPhieuChuaTT.data[0].phieutt_id
          } else {
            this.ThongBao("Không thể lấy thông tin phiếu thanh toán, xin thử lại sau!")
            return
          }
          update_tien = true
          await this.TaoDuLieu_CT_TIENHD(true)
        }

        if (this.thamso.kiemtra_khop_tien_datcoc) {
          var tien_tratruoc = 0
          var tien_datcoc = 0
          for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DATCOC) {
              tien_tratruoc = parseFloat(this.ds_tien_khoanmuc[i]["tien"]) + parseFloat(this.ds_tien_khoanmuc[i]["vat"])
            }
          }

          for (var dc1 = 0; dc1 < this.dsKM_HDTB.length; dc1++) {
            tien_datcoc += parseFloat(this.dsKM_HDTB[dc1]["DATCOC_CSD"])
          }

          for (var dc2 = 0; dc2 < this.dsDC_HDTB.length; dc2++) {
            tien_datcoc += parseFloat(this.dsDC_HDTB[dc2]["DATCOC_CSD"])
          }

          if (tien_tratruoc != tien_datcoc) {
            this.ThongBao("Tiền đặt cọc và tiền khoản mục không khớp. Liên hệ admin để kiểm tra lại! ")
            return
          }
        }
        if (this.dsCTTHD.length == 0 && this.dsKM_HDTB.length == 0 && this.dsDC_HDTB.length == 0) {
          console.log("null data")
          this.$toast.success("Không có sự thay đổi dữ liệu khuyến mại")
          return
        }
        var vdsStr = {
          "HDTB_ID": this.hdtb_id,
          "HC_HDTB_ID": this.HC_HDTB_ID,
          "HC_TDTT_HDTB_ID": this.HC_TDTT_HDTB_ID,
          "NGUOIDUNG_ID": this.$root.token.getNguoiDungID(),
          "MAY_CN": this.$root.token.getUserName(),
          "MA_ND": this.$root.token.getUserName(),
          "PHIEUTT_ID": vphieutt_id,
          "UPDATE_TIEN": update_tien
        }
        var vtaodulieuData = {
          CT_TIENHD: this.dsCTTHD,
          KHUYENMAI_HDTB: this.dsKM_HDTB,
          DATCOC_HDTB: this.dsDC_HDTB
        }
        var FN_SUAKM_CDLH_INPUT = {
          vds: JSON.stringify(vdsStr),
          vtaodulieu: JSON.stringify(vtaodulieuData)
        }
        var suaKMResult = await service.fn_suakm_cdlh(FN_SUAKM_CDLH_INPUT)

        var dsTbHddtResult = await service.lay_hopdong_tb_hddt({"vhdkh_id": this.hdkh_id, "vkieu": "1"})
        var shddt = await this.phathanh_hddt_dieuchinh_td(dsTbHddtResult.data, dsTbHddtResult.data)
        if (shddt != "OK") {
          this.$toast.warning(shddt)
        } else if (!suaKMResult.error_msg) {
          this.$toast.success("Cập nhật thành công")
          await this.onEnterMaGD()
        } else {
          this.$toast.warning(suaKMResult.error_msg)
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi xảy ra " + ex.message)
      } finally {
        this.loading(false)
      }
    },
    async ThongBao (msg, title) {
      if (!title) title = "Thông Báo"
      await this.$bvModal.msgBoxOk(msg, {
        title: title,
        size: "sm",
        okTitle: "Đồng ý"
      })
    },
    // Sinh thêm fsecure kèm fiber nếu lắp mới từ 01/7/2017 đến hết 31/12/2017
    async DatMoi_Fsecure_kem_fiber () {
      if (this.dichvu_vt_selected != DichVuVienThong.ADSL) {
        return
      }
      this.kieuld_fsecure_kem_fiber = 0
      // lay_tt_fs_kem_ftth_v2
      let dsFS = await this.lay_tt_fs_kem_ftth_v2(LoaiHopDong.CHUYENDOI_LH)
      if (dsFS.length > 0) {
        this.lay_tt_fs_kem_ftth_v2 = (dsFS[0].ten_ts != undefined && dsFS[0].ten_ts != null) ? Number(dsFS[0].ten_ts) : 0
      }

      // 182:MEGA_FTTH:Chuyển từ Mega sang FTTH
      if (this.kieuld_id == KieuLapDat.TDLH_MEGA_FTTH && this.lay_tt_fs_kem_ftth_v2 != 0) {
        // Sau này làm chưa cần thiết
        let result = await this.confirm("Đang trong thời gian khuyến mại: Chuyển đổi Mega - FTTH sẽ được tặng 01 gói Fsecure miễn phí. Bạn có đồng ý nhận khuyến mại?", "Thông báo")
        if (result == 0) return

        this.dulieuTTFsecureKemFtth = {}
        this.dulieuTTFsecureKemFtth.so_dt = this.$refs.thongTinKhachHang.input_sdt.trim()
        this.$refs.TTFsecureKemFtthModal.showModal()
      }
    },
    async formCloseTTFsecureKemFtth (value) {
      if (value == null) {
        return
      }
      console.log("formCloseTTFsecureKemFtth", value)
      this.sodt_fsecure_kem = value.so_dt
      this.kh_cd_fsecure = value.kh_cd_fsecure

      let _matb_tn = this.$refs.bangRongCoDinh.input_matb.trim()
      let _madoicap = this.$refs.bangRongCoDinh.input_madoicap_adsl.trim()

      //             cboDichVuVT.SelectedValue = DichVuVienThong.ANTOAN_BAOMAT_TT;
      //             cboLoaihinhTB.SelectedValue = LoaiHinhTB.FSECURE;
      //             cboKieuLD.SelectedValue = kieuld_fsecure_kem_fiber;
      //             tocdotn_id = FSECURE_KM_12_MONTHS.FreeFSD12M_2018;// TOCDO_ID_CFreeFSD12M;// TOCDO_ID_V2;
      //             muccuoctn_id = 0;
      //             cboGoiCuocAdsl.EditValue = tocdotn_id;

      //             //txtDienThoai_Fsecure.Text = sodt_fsecure_kem;
      //             int huonggiao_tn = 0;
      //             DataSet ds = bangts.LayHuongGiao_TiepNhan(LoaiHopDong.DAT_MOI, LoaiHinhTB.FSECURE,
      //                                         tocdotn_id, muccuoctn_id, kieuld_fsecure_kem_fiber);
    },
    async Them_MyTV_vao_goi_giadinh () {
      // Loại hình của dịch vụ băng rộng cố định
      if (this.dichvu_vt_selected != DichVuVienThong.ADSL) {
        return
      }
      //
      if ((this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.INTERNET_MYTV) && this.$refs.bangRongCoDinh.input_matn != "") {
        let ds = await this.kiemtra_dky_family_dichuyen(this.hdtb_id, this.$refs.bangRongCoDinh.input_matn)
        if (ds.length > 0) {
          if (Object.values(ds[0])[0] == 1) {
            let goi_id = Object.values(ds[0])[1].toString()
            let nhomtb_id = Object.values(ds[0])[2].toString()
            let magoi_vasc = await this.lay_goivasc_goi_dadv(goi_id, this.$refs.bangRongCoDinh.tocdo_moi_selected, this.$refs.bangRongCoDinh.muccuoc_moi_selected)
            if ((magoi_vasc != -1 && this.loaitb_id == LoaiHinhTB.INTERNET_MYTV) || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH) {
              let result = await this.confirm("Thuê bao đang tham gia gói Gia đình (hoặc CĐLH tới cùng đường với thuê bao đang tham gia gói Gia đình). Bạn có đồng ý thêm thuê bao này vào cùng gói không?", "Thông báo")
              if (result == 0) return
              if (this.loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
                await this.TaoDuLieu_HDTB_GOI_DADV(goi_id, nhomtb_id)
                let result = await this.sp_themmoi_hopdongtb_goida_dv_mytv(0, JSON.stringify(this.dsHDTBGOI))
                if (result.code == 1) {
                  console.log("Thêm mới hợp đồng thuê bao gói đa dịch vụ mytv thành công")
                } else {
                  console.log(result.message)
                }
              }
              if (this.loaitb_id == LoaiHinhTB.INTERNET_FTTH) {
                await this.TaoDuLieu_HDTB_dkygoi_mytv(ds)
                // sp_themmoi_hopdongtb_goida_dv_ftth js_hdtb, js_hdtbgoi
                let result = await this.sp_themmoi_hopdongtb_goida_dv_ftth(this.huonggiaotn_id, JSON.stringify(this.dsHDTB), JSON.stringify(this.dsHDTBGOI))
                if (result.code == 1) {
                  console.log("Thêm mới hợp đồng thuê bao gói đa dịch vụ mytv ftth thành công")
                } else {
                  console.log(result.message)
                }
              }
            }
          }
        }
      }
    },
    async TaoDuLieu_HDTB_GOI_DADV (goi_id, nhomtb_id) {
      this.dsHDTBGOI = []
      var object = {
        PHANVUNG_ID: this.$root.token.getPhanVungID(),
        HDTB_ID: this.hdtb_id,
        GOI_ID: goi_id,
        NHOMTB_ID: nhomtb_id,
        CHUNHOM: 0,
        THANG_HUONG: 0,
        CTV_ID: null,
        NGAY_KH_CCBS: null,
        CTV_VNP_ID: null,
        KIEU_YC: null
      }
      this.dsHDTBGOI.push(object)
    },
    // Chưa hoàn tất đang thiêu api lấy hướng giao, quy trình
    async TaoDuLieu_HDTB_dkygoi_mytv (dsMyTV) {
      this.dsHDTB = []
      this.dsHDTBGOI = []
      // lay_ds_dbtb_theo_tbid
      for (let i = 0; i < dsMyTV.length; i++) {
        let row = dsMyTV[i]
        // fn_lay_tt_taodl_hdtb_dkygoi_mytv
        let result = await this.fn_lay_tt_taodl_hdtb_dkygoi_mytv(row["thuebao_id"] != undefined ? row["thuebao_id"] : 0)
        if (result != null && result.ERROR_CODE == 1) {
          let dsDBTB = result.RESULT.TT_DB
          this.hdtb_id_huygoi_mytv = result.RESULT.HDTB_ID

          var objectHDTB = dsDBTB.length > 0 ? dsDBTB[0] : {}
          objectHDTB.HDTB_ID = this.hdtb_id_huygoi_mytv
          objectHDTB.HDKH_ID = this.hdkh_id
          objectHDTB.THUEBAO_ID = row["thuebao_id"] != undefined ? row["thuebao_id"] : 0
          objectHDTB.MA_TB = row["ma_tb"] != undefined ? row["ma_tb"] : ""
          objectHDTB.TEN_TB = row["ten_tb"] != undefined ? row["ten_tb"] : ""
          objectHDTB.DIACHI_TB = row["diachi_tb"] != undefined ? row["diachi_tb"] : 0
          objectHDTB.KIEULD_ID = KieuLapDat.THEM_TB_GOI_DADV
          objectHDTB.DOITUONG_ID = row["doituong_id"] != undefined ? row["doituong_id"] : 0
          objectHDTB.TTHD_ID = TrangThaiHD.MOI
          objectHDTB.LOAITB_ID = LoaiHinhTB.INTERNET_MYTV
          objectHDTB.DICHVUVT_ID = DichVuVienThong.ADSL
          objectHDTB.DONVI_ID = this.$root.token.getDonViID()
          objectHDTB.NGUOI_CN = this.$root.token.getUserName()
          objectHDTB.NGAY_CN = moment(new Date()).format("DD/MM/YYYY")
          objectHDTB.MAY_CN = "localhost"

          this.huonggiaotn_id = result.RESULT.TT_HUONGGIAO.HUONGGIAO_ID
          this.quytrinh_id_2 = result.RESULT.TT_HUONGGIAO.QUYTRINH_ID

          objectHDTB.QUYTRINH_ID = this.quytrinh_id_2
          objectHDTB.MUCUOCTB_ID = result.RESULT.TT_HUONGGIAO.MUCUOCTB_ID
          objectHDTB.MUCUOC_TB = result.RESULT.TT_HUONGGIAO.MUCUOCTB

          this.dsHDTB.push(objectHDTB)

          var objectHDTBGOI = {}
          objectHDTBGOI.GOI_ID = row["goi_id"] != undefined ? row["goi_id"] : 0
          objectHDTBGOI.NHOMTB_ID = row["nhomtb_id"] != undefined ? row["nhomtb_id"] : 0
          objectHDTBGOI.CHUNHOM = 0
          objectHDTBGOI.HDTB_ID = this.hdtb_id_huygoi_mytv
          objectHDTBGOI.THANG_HUONG = 0

          this.dsHDTBGOI.push(objectHDTBGOI)
        }
      }
    },
    async LayMucCuocTB (tmp_thuebao_id, kieu) {
      try {
        if (kieu == 1) {
          let mucuoctb_id = await service.fn_tt_db_thuebao(tmp_thuebao_id, 3)
          return mucuoctb_id
        } else {
          let mucuoc_tb = await service.fn_tt_muccuoc_tb(tmp_thuebao_id, 4)
          return mucuoc_tb
        }
      } catch (ex) {
        console.log(ex)
        return -1
      }
    },
    async capnhat () {
      var matb_neo
      var async_status = false // Biến kiểm tra việc đồng bộ dữ liệu VinaPhone, MyTV...
      if (!this.KiemTraDL_KhachHang()) {
        return
      }
      if (!this.KiemTraDL_ThueBao()) {
        return
      }
      if (!this.KiemTraDL_KhuyenMai()) {
        return
      }
      var tocdotn_id = -1
      var muccuoctn_id = -1
      var matb = ""
      var vtd_id = -1
      var vmc_id = 0
      var loaiso = 0

      var loaihinhtb_id = 0

      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        matb = this.$refs.coDinh.input_matb.trim()
        loaiso = this.$refs.coDinh.loai_so_moi_selected != null ? this.$refs.coDinh.loai_so_moi_selected : 0
        loaihinhtb_id = this.$refs.coDinh.loaihinh_tb_moi_selected != null ? this.$refs.coDinh.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        matb = this.$refs.diDong.input_matb.trim()
        loaihinhtb_id = this.$refs.diDong.loaihinh_tb_moi_selected != null ? this.$refs.diDong.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        matb = this.$refs.bangRongCoDinh.input_matb.trim()
        tocdotn_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? this.$refs.bangRongCoDinh.tocdo_moi_selected : 0
        if (this.loaihinhtb_id == LoaiHinhTB.INTERNET_ADSL || this.loaihinhtb_id == LoaiHinhTB.INTERNET_FTTH || this.loaihinhtb_id == LoaiHinhTB.WIFI_FIBER) {
          muccuoctn_id = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? this.$refs.bangRongCoDinh.muccuoc_moi_selected : 0
        }
        vtd_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? this.$refs.bangRongCoDinh.tocdo_moi_selected : 0
        vmc_id = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? this.$refs.bangRongCoDinh.muccuoc_moi_selected : 0
        loaihinhtb_id = this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected != null ? this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        matb = this.$refs.ims.input_matb.trim()
        loaihinhtb_id = this.$refs.ims.loaihinh_tb_moi_selected != null ? this.$refs.ims.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        matb = this.$refs.kenhThuRieng.input_matb.trim()
        tocdotn_id = this.$refs.kenhThuRieng.tocdo_moi_selected != null ? this.$refs.kenhThuRieng.tocdo_moi_selected : 0
        loaihinhtb_id = this.$refs.kenhThuRieng.loaihinh_tb_moi_selected != null ? this.$refs.kenhThuRieng.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        matb = this.$refs.megawan.input_matb.trim()
        tocdotn_id = this.$refs.megawan.tocdo_cir_moi_selected != null ? this.$refs.megawan.tocdo_cir_moi_selected : 0
        loaihinhtb_id = this.$refs.megawan.loaihinh_tb_moi_selected != null ? this.$refs.megawan.loaihinh_tb_moi_selected : 0
      }

      var id_sochinh = 0
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH && this.$refs.thongTinPhieuYeuCau.disableMucCuocTB) {
        if (this.$refs.coDinh.loai_so_moi_selected != null && this.$refs.coDinh.loai_so_moi_selected == LOAISO_ISDN.SOPHU) {
          if (this.loaisochinh == 1) {
            id_sochinh = this.thuebao_id_cha
          } else {
            id_sochinh = this.hdtb_id_cha
          }
        }
      }

      let insert = this.actions[this.actions.findIndex(x => x.id == "nhapmoi")].active ? 0 : 1

      // fn_kt_va_lay_tt_capnhat_cdlhtb
      let ds_para = {
        THUEBAO_ID: this.thuebao_id,
        SOTHANGCK: this.$refs.thongTinKhachHang.input_thang_ck.trim(),
        KHUYENMAI: this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected != null ? this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected : 0,
        TRATRUOC: this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected != null ? this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected : 0,
        NHAPMOI: insert,
        KHACHHANG_ID: this.khachhang_id,
        MA_TB: matb,
        DICHVUVT_ID: this.dichvu_vt_selected,
        LOAITB_MOI_ID: loaihinhtb_id,
        LOAITB_CU_ID: this.$refs.thongTinPhieuYeuCau.loaihinh_selected != null ? this.$refs.thongTinPhieuYeuCau.loaihinh_selected : 0,
        KIEULD_ID: this.$refs.thongTinPhieuYeuCau.kieu_yc_selected != null ? Number(this.$refs.thongTinPhieuYeuCau.kieu_yc_selected) : 0,
        ISDN_SOCHINH: this.isdn_sochinh ? 1 : 0,
        TOCDO_ID: tocdotn_id,
        MUCCUOC_ID: muccuoctn_id,
        MUCCUOCTB_ENABLED: this.$refs.thongTinPhieuYeuCau.disableMucCuocTB ? 0 : 1,
        ID_SOCHINH: id_sochinh,
        LOAISOCHINH: this.loaisochinh,
        VTD_ID: vtd_id,
        VMC_ID: vmc_id,
        LOAISO: loaiso,
        QUANLD_ID: this.dataDiaChiLD.quan_id,
        PHUONGLD_ID: this.dataDiaChiLD.phuong_id,
        PHOLD_ID: this.dataDiaChiLD.pho_id,
        APLD_ID: this.dataDiaChiLD.ap_id,
        KHULD_ID: this.dataDiaChiLD.khu_id,
        DACDIEMLD_ID: this.dataDiaChiLD.dacdiem_id
      }
      //
      console.log("ds_para", ds_para)
      this.loading(true)
      let resultCheck = await this.fn_kt_va_lay_tt_capnhat_cdlhtb(JSON.stringify(ds_para))
      this.loading(false)
      if (resultCheck != null && resultCheck.ERROR_CODE == 1) {
        // hanlde
        let huonggiao_tn = resultCheck.RESULT.HUONGGIAO_ID
        this.luong_id = resultCheck.RESULT.LUONG_ID
        this.quytrinh_id = resultCheck.RESULT.QUYTRINH_ID
        console.log("huonggiao_tn", huonggiao_tn)
        console.log("luong_id", this.luong_id)
        console.log("quytrinh_id", this.quytrinh_id)
        let themmoi = !this.actions[this.actions.findIndex(x => x.id == "nhapmoi")].active
        if (themmoi) {
          this.kt_adsl = true
          this.loading(true)
          await this.TaoDuLieu(true)
          this.loading(false)
          if (!this.kt_adsl) {
            return
          }
          // thực thi thêm mới
          let data = {
            THEMMOI: 1,
            HUONGGIAO_ID: huonggiao_tn,
            MA_GD_NEO: this.ma_gd_neo,
            OPEN_SYNC_VINA: TRANGTHAI_DONGBO.OPEN_SYNC_VINA,
            ASYNC_STATUS: async_status,
            CHUYENDOI_LH_DUNGTHU: this.thamso.CHUYENDOI_LH_DUNGTHU,
            HD_KHACHHANG: this.dsHDKH,
            HD_THUEBAO: this.dsHDTB,
            HDTB_DUNGTHU: this.dsHDTB_DUNGTHU,
            HDTB_DV: this.dsHDTBDV,
            CT_TIENHD: this.dsCTTHD,
            DIACHI_HDTB: this.dsDiaChiHDTB,
            DIACHI: this.dsDiaChi,
            KHUYENMAI_HDTB: this.dsKM_HDTB,
            DATCOC_HDTB: this.dsDC_HDTB,
            HDTB_KV: this.dsHDTB_KV,
            CT_MUA_TBI: this.dsCTM_TBI,
            HDTB_CD: this.dsHDTB_CD,
            HDTB_DD: this.dsHDTB_DD,
            TIEN_HDTB_TC: this.dsTIEN_HDTB_TC,
            HDTB_IMS: this.dsHDTB_IMS,
            HDTB_ADSL: this.dsHDTB_ADSL,
            HDTB_GP: this.dsHDTB_GP,
            HDTB_MGWAN: this.dsHDTB_MEWAN,
            HDTB_TSL: this.dsHDTB_TSL,
            THONGTIN_TT: [],
            TIEN_HDTB: this.dsTIEN_HDTB_TC,
            HDTB_LOAIIP: []
          }
          // "THONGTIN_TT":[], "TIEN_HDTB":[], "HDTB_LOAIIP":[]
          // fn_ins_update_hd_cdlh
          console.log("TaoDuLieu", data)
          this.loading(true)
          let result = await this.fn_ins_update_hd_cdlh(JSON.stringify(data))
          this.loading(false)
          if (result.code == 0) {
            this.$toast.error(result.message)
            return
          }
          this.$toast.success(result.message)
          // Hanlder nếu là dịch vụ di động
          if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG && this.$refs.thongTinPhieuYeuCau.kieu_yc_selected != null &&
                        Number(this.$refs.thongTinPhieuYeuCau.kieu_yc_selected) == KieuLapDat.CHUYENDD_TRASAU_SANG_TRATRUOC) {
            if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
              this.loading(true)
              let _q = this.dsHDTB.filter(x => x.DICHVUVT_ID == DichVuVienThong.DI_DONG).map(x => {
                return x.THUEBAO_ID
              })
              if (_q.length > 0) {
                let ds_thuebao = _q.join()
                let _ds = await this.lay_tt_tb_dd(ds_thuebao)
                // if (_ds != null && _ds.Tables.Count > 0 && _ds.Tables[0].Rows.Count > 0)
                if (_ds != null && _ds.length > 0) {
                  for (let i = 0; i < _ds.length; i++) {
                    var loaitb_id_tmp = "20"
                    var ma_kh = _ds[i].ma_tt_neo ? _ds[i].ma_tt_neo.toString() : _ds[i].ma_tt ? _ds[i].ma_tt.toString() : ""
                    let goicuoc_dd_moi_id = this.$refs.diDong.goicuoc_dd_moi_selected ? Number(this.$refs.diDong.goicuoc_dd_moi_selected) : 0
                    let loaitb_tratruoc = await service.fn_tt_goicuoc_dd(goicuoc_dd_moi_id)
                    //
                    let result = await this.ts_laphd_thanhly({
                      dia_chi_tt: _ds[i].diachi_tt ? _ds[i].diachi_tt : "",
                      ma_tinh: this.tt_nd ? this.tt_nd.tentat_ccbs : "",
                      ten_tb: _ds[i].ten_tb ? _ds[i].ten_tb : "",
                      ma_kh: ma_kh,
                      co_quan: "",
                      ma_hd: "",
                      userid: this.tt_nd ? this.tt_nd.user_neo : "",
                      loai_hinh_tra_truoc: loaitb_tratruoc,
                      dia_chi_ct: _ds[i].diachi_ct ? _ds[i].diachi_ct : "",
                      kieu_thanh_ly_1_2_3: "2",
                      ten_tt: _ds[i].ten_tt ? _ds[i].ten_tt : "",
                      ghi_chu: _ds[i].lydohuy ? _ds[i].lydohuy : "",
                      hinhthuc_thanh_ly_0_1: "1",
                      so_tb: "84" + _ds[i].ma_tb,
                      ngay_lap_hd: this.$refs.thongTinKhachHang.ngay_lhd,
                      loaitb_id: loaitb_id_tmp,
                      dia_chi: _ds[i].diachi_ld ? _ds[i].diachi_ld : ""
                    })
                    console.log("ts_laphd_thanhly", result)
                    if (result.code == 0) {
                      // thất bại
                      this.$toast.error(result.data.toString())
                      await this.xoa_hopdong_thuebao(this.hdtb_id)
                      this.loading(false)
                      return
                    } else {
                      let ma_hd_neo = result.data.length > 0 ? result.data[0].toString() : ""
                      await this.update_hdtb_dd(_ds[i].hdtb_id, ma_hd_neo, "ma_gd_neo tuong ung voi ma_hd thanhly tren neo")
                      await this.sp_capnhat_status_hdtb(_ds[i].hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
                    }
                  }
                  this.$toast.success("Cập nhật thông tin ccbs thành công")
                }
              }
              this.loading(false)
            }
          }
          //
          this.loading(true)
          // Kiểm tra với tb lắp kèm hoặc trên đường có sẵn->gán tb vào hộ dân của thuê bao kèm.
          await this.tudong_gan_hodan_thuebao({
            vhdtb_id: this.hdtb_id,
            vnguoi_cn: this.$root.token.getUserName(),
            vmay_cn: "localhost",
            vip_cn: "127.0.0.1"
          })
          // Kiểm tra với tb dịch chuyển tới đường có sẵn->chuyển cáp từ thuê bao có sẵn sang
          await this.tudong_gan_cap_thuebao({
            vhdtb_id: this.hdtb_id,
            vnguoi_cn: this.$root.token.getUserName(),
            vmay_cn: "localhost",
            vip_cn: "127.0.0.1"
          })
          this.loading(false)

          //
          this.loading(true)
          await this.Them_MyTV_vao_goi_giadinh()
          // chuyển DatMoi_Fsecure_kem_fiber xuống dưới
          // await this.DatMoi_Fsecure_kem_fiber()
          this.loading(false)
        } else {
          this.kt_adsl = true
          this.loading(true)
          await this.TaoDuLieu(false)
          this.loading(false)
          if (!this.kt_adsl) {
            return
          }
          // thực thi update

          // Hanlde nếu là dịch vụ di động
          if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG && this.$refs.thongTinPhieuYeuCau.kieu_yc_selected != null &&
                        Number(this.$refs.thongTinPhieuYeuCau.kieu_yc_selected) == KieuLapDat.CHUYENDD_TRASAU_SANG_TRATRUOC) {
            if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
              // Handle
              this.loading(true)
              let _q = this.dsHDTB.filter(x => x.DICHVUVT_ID == DichVuVienThong.DI_DONG).map(x => {
                return x.THUEBAO_ID
              })
              if (_q.length > 0) {
                let ds_thuebao = _q.join()
                let _ds = await this.lay_tt_tb_dd(ds_thuebao)
                // if (_ds != null && _ds.Tables.Count > 0 && _ds.Tables[0].Rows.Count > 0)
                if (_ds != null && _ds.length > 0) {
                  for (let i = 0; i < _ds.length; i++) {
                    var loaitb_id_tmp = "20"
                    var ma_kh = _ds[i].ma_tt_neo ? _ds[i].ma_tt_neo.toString() : _ds[i].ma_tt ? _ds[i].ma_tt.toString() : ""
                    // let loaitb_tratruoc = await this.fn_map_id({
                    //   id_neo: "ma_loai",
                    //   table: "css.goicuoc_dd",
                    //   dk: "where goicuoc_id = " + this.$refs.diDong.goicuoc_dd_moi_selected ? Number(this.$refs.diDong.goicuoc_dd_moi_selected) : 0
                    // }, "")
                    //
                    let goicuoc_dd_moi_id = this.$refs.diDong.goicuoc_dd_moi_selected ? Number(this.$refs.diDong.goicuoc_dd_moi_selected) : 0
                    let loaitb_tratruoc = await service.fn_tt_goicuoc_dd(goicuoc_dd_moi_id)
                    let result = await this.ts_laphd_thanhly({
                      dia_chi_tt: _ds[i].diachi_tt ? _ds[i].diachi_tt : "",
                      ma_tinh: this.tt_nd ? this.tt_nd.tentat_ccbs : "",
                      ten_tb: _ds[i].ten_tb ? _ds[i].ten_tb : "",
                      ma_kh: ma_kh,
                      co_quan: "",
                      ma_hd: "",
                      userid: this.tt_nd ? this.tt_nd.user_neo : "",
                      loai_hinh_tra_truoc: loaitb_tratruoc,
                      dia_chi_ct: _ds[i].diachi_ct ? _ds[i].diachi_ct : "",
                      kieu_thanh_ly_1_2_3: "2",
                      ten_tt: _ds[i].ten_tt ? _ds[i].ten_tt : "",
                      ghi_chu: _ds[i].lydohuy ? _ds[i].lydohuy : "",
                      hinhthuc_thanh_ly_0_1: "1",
                      so_tb: "84" + _ds[i].ma_tb,
                      ngay_lap_hd: this.$refs.thongTinKhachHang.ngay_lhd,
                      loaitb_id: loaitb_id_tmp,
                      dia_chi: _ds[i].diachi_ld ? _ds[i].diachi_ld : ""
                    })
                    console.log("ts_laphd_thanhly", result)
                    if (result.code == 0) {
                      // thất bại
                      this.$toast.error(result.data.toString())
                      this.loading(false)
                      return
                    } else {
                      await this.sp_capnhat_status_hdtb(_ds[i].hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
                    }
                  }
                  this.$toast.success("Cập nhật thông tin ccbs thành công")
                }
              }
              this.loading(false)
            }
          }

          // Thực thi cập nhật phía sau đã gom hàm
          let data = {
            THEMMOI: 2,
            HUONGGIAO_ID: huonggiao_tn,
            MA_GD_NEO: this.ma_gd_neo,
            OPEN_SYNC_VINA: TRANGTHAI_DONGBO.OPEN_SYNC_VINA,
            ASYNC_STATUS: async_status,
            CHUYENDOI_LH_DUNGTHU: this.thamso.CHUYENDOI_LH_DUNGTHU,
            HD_KHACHHANG: this.dsHDKH,
            HD_THUEBAO: this.dsHDTB,
            HDTB_DUNGTHU: this.dsHDTB_DUNGTHU,
            HDTB_DV: this.dsHDTBDV,
            CT_TIENHD: this.dsCTTHD,
            DIACHI_HDTB: this.dsDiaChiHDTB,
            DIACHI: this.dsDiaChi,
            KHUYENMAI_HDTB: this.dsKM_HDTB,
            DATCOC_HDTB: this.dsDC_HDTB,
            HDTB_KV: this.dsHDTB_KV,
            CT_MUA_TBI: this.dsCTM_TBI,
            HDTB_CD: this.dsHDTB_CD,
            HDTB_DD: this.dsHDTB_DD,
            TIEN_HDTB_TC: this.dsTIEN_HDTB_TC,
            HDTB_IMS: this.dsHDTB_IMS,
            HDTB_ADSL: this.dsHDTB_ADSL,
            HDTB_GP: this.dsHDTB_GP,
            HDTB_MGWAN: this.dsHDTB_MEWAN,
            HDTB_TSL: this.dsHDTB_TSL,
            THONGTIN_TT: [],
            TIEN_HDTB: this.dsTIEN_HDTB_TC,
            HDTB_LOAIIP: []
          }
          console.log("TaoDuLieu", data)
          this.loading(true)
          let result = await this.fn_ins_update_hd_cdlh(JSON.stringify(data))
          this.loading(false)
          if (result.code == 0) {
            this.$toast.error(result.message)
            return
          }
          this.$toast.success(result.message)
        }
        await this.setActiveActions(3)
        // Thêm hàm hiển thị lại thông tin khách hàng sau khi Insert/Update hợp đồng
        this.loading(true)
        let ma_gd = await this.$refs.thongTinPhieuYeuCau.getMaGD()
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

        // Tặng Fsecure khi lắp fiber
        if (themmoi) {
          // Tạm thời không dùng bỏ qua xử lý sau này quay lại
          // await this.DatMoi_Fsecure_kem_fiber()
        }
        // Sự kiện khi đặt mới thành công-->Callback không có tác dụng với form này(bỏ qua)
        // if (OnThayDoiLHTB != null)
        // {
        //     try
        //     {
        //         OnThayDoiLHTB.Invoke(this, hdkh_id, hdtb_id, PhieuHs_ID, txtTenKH.Text);
        //     }
        //     catch { }
        // }
        // PhieuHs_ID = 0;
      } else if (resultCheck != null && resultCheck.ERROR_CODE == 0) {
        this.$toast.error(resultCheck.MESSAGE)
      }
    },
    async TaoDuLieu (themmoi) {
      await this.TaoDuLieu_HDKH(themmoi)
      await this.TaoDuLieu_HDTB(themmoi)

      await this.TaoDuLieu_DiaChi(themmoi)

      if (this.doituong_id == DUNG_THU.DOITUONG_TB && this.thamso.CHUYENDOI_LH_DUNGTHU == 1) {
        await this.TaoDuLieu_HDTB_DUNGTHU(themmoi)
      }

      // Bổ xung thêm các bảng nghiệp vụ con
      this.dsHDTB_CD = []
      this.dsHDTB_DD = []
      this.dsHDTB_ADSL = []
      this.dsHDTB_GP = []
      this.dsHDTB_TSL = []

      // bổ sung thêm tạo dữ liệu megawan
      this.dsHDTB_MEWAN = []
      this.dsHDTBDV = []
      // Bổ sung hàm tạo dữ liệu thông tin thanh toán
      await this.TaoDuLieu_THONGTIN_TT(themmoi)
      await this.TaoDuLieu_CT_TIENHD(true)
      // //TuấnNA thêm code tạo dữ liệu cho chi tiết khuyến mại.
      await this.CapNhat_CTKM(this.hdtb_id)
      await this.TaoDuLieu_HDTBDV()
      await this.LoadDL_KV(this.khuvuc_id)

      for (let i = 0; i < this.dsCTM_TBI.length; i++) {
        this.dsCTM_TBI[i].HDTB_ID = this.hdtb_id
      }

      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        await this.TaoDuLieu_HDTB_CD(themmoi)
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        await this.TaoDuLieu_HDTB_DD(themmoi)
        await this.TaoDuLieu_TIEN_HDTB_TC(themmoi)
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        await this.TaoDuLieu_HDTB_ADSL(themmoi)
      } else if (this.dichvu_vt_selected == DichVuVienThong.GPHONE) {

      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        await this.TaoDuLieu_HDTB_MGWAN(themmoi)
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.TaoDuLieu_HDTB_TSL(themmoi)
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        await this.TaoDuLieu_HDTB_IMS(themmoi)
      }
      // Bổ sung thong tin hdtb_id vao bang khuyenmai, dat coc
      for (let i = 0; i < this.dsDC_HDTB.length; i++) {
        this.dsDC_HDTB[i].HDTB_ID = this.hdtb_id
      }
      console.log("TaoDuLieu dsDC_HDTB", this.dsDC_HDTB)
      for (let i = 0; i < this.dsKM_HDTB.length; i++) {
        this.dsKM_HDTB[i].HDTB_ID = this.hdtb_id
      }
      console.log("TaoDuLieu dsKM_HDTB", this.dsKM_HDTB)
    },
    async TaoDuLieu_HDKH (themmoi) {
      this.dsHDKH = []

      let tratruoc = (this.$refs.thongTinPhieuYeuCau.tratruoc.checkTraTruoc && this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected != null) ? this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected : 0
      let result = await this.fn_tt_taodulieu_hdkh_cdlhtb(JSON.stringify({
        THUEBAO_ID: this.thuebao_id,
        NHAPMOI: themmoi ? 1 : 2,
        TRATRUOC: tratruoc,
        KHACHHANG_ID: this.khachhang_id,
        DONVI_ID: this.$root.token.getDonViID()
      }))
      if (result != null && result.ERROR_CODE == 1) {
        if (themmoi) {
          this.hdkh_id = result.RESULT.HDKH_ID
        }
        var object = createObjectHDKH(result.RESULT.LAY_DS_DBKH_THEO_KHID[0])
        object.HDKH_ID = this.hdkh_id
        if (object.PHANVUNG_ID == null) {
          object.PHANVUNG_ID = this.$root.token.getPhanVungID()
        }
        // Object.assign(object, result.RESULT.LAY_DS_DBKH_THEO_KHID[0])
        if (themmoi) {
          this.$refs.thongTinPhieuYeuCau.input_magd = result.RESULT.MA_GD
          object.MA_GD = this.$refs.thongTinPhieuYeuCau.input_magd
          object.MA_HD = this.$refs.thongTinKhachHang.input_ma_hd.trim()
        } else {
          object.MA_GD = this.$refs.thongTinPhieuYeuCau.input_magd
          object.MA_HD = this.$refs.thongTinKhachHang.input_ma_hd.trim()
        }
        object.MA_KH = this.$refs.thongTinKhachHang.input_makh.trim()
        if (this.khachhang_id > 0) {
          object.KHACHHANG_ID = this.khachhang_id
        } else {
          object.KHACHHANG_ID = 0
        }
        if (this.hdkh_cha_id) {
          object.HDKH_CHA_ID = this.hdkh_cha_id
        } else {
          object.HDKH_CHA_ID = 0
        }
        object.TEN_KH = this.$refs.thongTinKhachHang.input_ten_kh.trim()
        object.DIACHI_KH = this.$refs.thongTinKhachHang.input_diachi_kh.trim()

        object.NGAYLAP_HD = moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
        object.NGAY_YC = moment(this.$refs.thongTinPhieuYeuCau.ngay_yc, "DD/MM/YYYY").format("DD/MM/YYYY HH:mm:ss")
        object.DONVI_ID = this.$root.token.getDonViID()
        object.NHANVIEN_ID = this.$root.token.getNhanVienID()
        object.KHLON_ID = result.RESULT.KHLON_ID
        object.PHANLOAIKH_ID = result.RESULT.PHANLOAIKH_ID

        object.LOAIHD_ID = LoaiHopDong.CHUYENDOI_LH
        object.NGUOI_CN = this.$root.token.getUserName()
        object.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
        object.MAY_CN = "localhost"
        object.IP_CN = "127.0.0.1"

        // parse date NGAY_SN/NGAYCAP
        object.NGAY_SN = object.NGAY_SN != null ? moment(object.NGAY_SN).format("DD/MM/YYYY HH:mm:ss") : null
        object.NGAYCAP = object.NGAYCAP != null ? moment(object.NGAYCAP).format("DD/MM/YYYY HH:mm:ss") : null
        object.NGAY_HK = object.NGAY_HK != null ? moment(object.NGAY_HK).format("DD/MM/YYYY HH:mm:ss") : null
        object.NGAYHETHAN_GT = object.NGAYHETHAN_GT != null ? moment(object.NGAYHETHAN_GT).format("DD/MM/YYYY HH:mm:ss") : null

        if (this.checkCTV && this.ctv.ten_ctv != null && this.ctv.ten_ctv != "") {
          object.CTV_ID = this.ctv.ctv_id
        } else {
          object.CTV_ID = 0
        }
        if (this.checkNguoiGT && this.nguoi_gt.ten_nguoi_gt != null && this.nguoi_gt.ten_nguoi_gt != "") {
          object.NHANVIENGT_ID = this.nguoi_gt.nguoi_gt_id
        } else {
          object.NHANVIENGT_ID = 0
        }

        object.SO_DT = this.$refs.thongTinKhachHang.input_sdt.trim()
        object.KIEUHD_ID = this.kieu_pl_selected != null ? this.kieu_pl_selected : 0
        object.KENHTN_ID = this.iKenhTN
        // Bổ sung cho HCM theo y/c chị Tô Thảo - TP DHNV TTKD
        object.MA_DUAN = this.input_ma_duan.trim()

        this.dsHDKH.push(object)

        console.log("dsHDKH", this.dsHDKH)
      } else if (result != null && result.ERROR_CODE == 0) {
        this.$toast.error(result.MESSAGE)
      }
    },
    async TaoDuLieu_HDTB (themmoi) {
      // fn_tt_taodulieu_hdtb_cdlhtb
      this.dsHDTB = []
      let object = {}
      var matb = ""
      var matb_moi = ""
      var loaihinhtbmoi_id = 0
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        matb = this.$refs.coDinh.input_matb.trim()
        object.MA_TB = matb
        loaihinhtbmoi_id = this.$refs.coDinh.loaihinh_tb_moi_selected != null ? this.$refs.coDinh.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        matb = this.$refs.diDong.input_matb.trim()
        object.MA_TB = matb
        loaihinhtbmoi_id = this.$refs.diDong.loaihinh_tb_moi_selected != null ? this.$refs.diDong.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        matb = this.$refs.bangRongCoDinh.input_matb.trim()
        object.MA_TB = matb
        loaihinhtbmoi_id = this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected != null ? this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        matb = this.$refs.megawan.input_matb.trim()
        matb_moi = this.$refs.megawan.input_account_moi.trim()
        // làm sau cho megawan/metronet
        if (this.thamso.ts_sinh_matb_mgw_mte) {
          if (themmoi) {
            // string strMatb = new LapHopDongFacade2().SINH_MATB_CDLH(Convert.ToInt32(cboLoaihinhTB.SelectedValue), Convert.ToInt32(cboLoaiHinhTBMoi.SelectedValue));
            // if (strMatb != "-1")
            //     rowHDTB.MA_TB = strMatb;
            // else
            //     rowHDTB.MA_TB = txtMaTB.Text;
            object.MA_TB = matb
          } else {
            object.MA_TB = matb_moi
          }
        } else {
          object.MA_TB = matb
        }
        loaihinhtbmoi_id = this.$refs.megawan.loaihinh_tb_moi_selected != null ? this.$refs.megawan.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        matb = this.$refs.kenhThuRieng.input_matb.trim()
        object.MA_TB = matb
        loaihinhtbmoi_id = this.$refs.kenhThuRieng.loaihinh_tb_moi_selected != null ? this.$refs.kenhThuRieng.loaihinh_tb_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        matb = this.$refs.ims.input_matb.trim()
        object.MA_TB = matb
        loaihinhtbmoi_id = this.$refs.ims.loaihinh_tb_moi_selected != null ? this.$refs.ims.loaihinh_tb_moi_selected : 0
      }
      let result = await this.fn_tt_taodulieu_hdtb_cdlhtb(JSON.stringify({
        THUEBAO_ID: this.thuebao_id,
        DONVI_ID: this.$root.token.getDonViID(),
        NHAPMOI: themmoi ? 1 : 2,
        KHACHHANG_ID: this.khachhang_id,
        MA_TB: matb,
        MA_TB_MOI: matb_moi,
        DICHVUVT_ID: this.dichvu_vt_selected,
        LOAITB_MOI_ID: this.loaihinhtb_id != null ? Number(this.loaihinhtb_id) : 0,
        LOAITB_CU_ID: this.loaihinhtbcu_id,
        MUCUOCTB_ID: this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected != null ? this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected : 0,
        LUONG_ID: this.luong_id
      }))

      if (result != null && result.ERROR_CODE == 1) {
        if (themmoi) {
          this.hdtb_id = result.RESULT.HDTB_ID
        }
        object.HDTB_ID = this.hdtb_id
        object.HDKH_ID = this.hdkh_id
        if (this.thuebao_id > 0) {
          object.THUEBAO_ID = this.thuebao_id
        } else {
          object.THUEBAO_ID = 0
        }
        object.TEN_TB = this.$refs.thongTinKhachHang.input_ten_tb.trim()
        // Hiếu sửa địa chỉ tb thành dc LD ngày 06/09/2010
        object.DIACHI_LD = this.$refs.thongTinKhachHang.input_diachi_ld
        object.DIACHI_TB = this.$refs.thongTinKhachHang.input_diachi_tb

        object.KIEULD_ID = this.$refs.thongTinPhieuYeuCau.kieu_yc_selected != null ? this.$refs.thongTinPhieuYeuCau.kieu_yc_selected : 0
        // object.LOAITB_ID=Number(this.loaihinhtb_id)
        object.LOAITB_ID = loaihinhtbmoi_id

        object.DOITUONG_ID = this.doituong_id
        object.TTHD_ID = TrangThaiHD.MOI
        object.NGAY_HT = moment(this.$refs.thongTinPhieuYeuCau.ngay_yc, "DD/MM/YYYY").format("DD/MM/YYYY HH:mm:ss")
        object.DONVI_ID = this.donviql_id

        object.DICHVUVT_ID = result.RESULT.DICHVUVT_ID
        object.NGUOI_CN = this.$root.token.getUserName()
        object.MAY_CN = "localhost"
        object.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
        object.IP_CN = "127.0.0.1"
        object.QUYTRINH_ID = this.quytrinh_id
        object.GHICHU = this.$refs.thongTinPhieuYeuCau.input_ghichu.trim()

        if (this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected != null) {
          object.MUCUOCTB_ID = this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected
        } else {
          object.MUCUOCTB_ID = 0
        }

        if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH && (loaihinhtbmoi_id == LoaiHinhTB.ISDN2B_CD || loaihinhtbmoi_id == LoaiHinhTB.ISDN2B_CQ ||
                    loaihinhtbmoi_id == LoaiHinhTB.ISDN30B_CD || loaihinhtbmoi_id == LoaiHinhTB.ISDN30B_CQ || loaihinhtbmoi_id == LoaiHinhTB.TRUNGKE_2M ||
                    loaihinhtbmoi_id == LoaiHinhTB.TRUNGKE_THUONG || loaihinhtbmoi_id == LoaiHinhTB.TRUNGKE_TUONGTU)) {
          object.MUCUOC_TB = this.$refs.coDinh.input_cuoctb_moi.trim() != "" ? Number(this.$refs.coDinh.input_cuoctb_moi.trim()) : 0
        } else {
          object.MUCUOC_TB = result.RESULT.MUCUOC_TB
        }

        // Hiếu bổ sung thêm trường status: null: không pải đồng bộ, 0: chưa pải đồng bộ
        object.STATUS = result.RESULT.STATUS

        object.HDTB_CHA_ID = 0
        object.THUEBAO_CHA_ID = 0
        if (this.dichvu_vt_selected != DichVuVienThong.MEGAWAN && this.dichvu_vt_selected != DichVuVienThong.METRONET) {
          if (this.$refs.thongTinPhieuYeuCau.disableMucCuocTB) {
            if (this.loaisochinh == 1) {
              if (this.thuebao_id_cha > 0) {
                object.THUEBAO_CHA_ID = this.thuebao_id_cha
              }
            } else {
              if (this.hdtb_id_cha > 0) {
                object.HDTB_CHA_ID = this.hdtb_id_cha
              }
            }
          }
        } else {
          if (this.thuebao_id > 0) {
            // let tb_cha_id = await this.fn_map_id({
            //   id_neo: "thuebao_cha_id",
            //   table: "css.db_thuebao",
            //   dk: "where thuebao_id = " + this.thuebao_id
            // }, "-1")
            // if (tb_cha_id != "-1") {
            //   object.THUEBAO_CHA_ID = Number(tb_cha_id)
            // }
            let tb_cha_id = await service.fn_tt_db_thuebao(this.thuebao_id, 5)
            if (tb_cha_id != -1) {
              object.THUEBAO_CHA_ID = Number(tb_cha_id)
            }
          }
        }

        if (this.$refs.thongTinPhieuYeuCau.goikt_selected != null) {
          object.GOIKT_ID = Number(this.$refs.thongTinPhieuYeuCau.goikt_selected)
        } else {
          object.GOIKT_ID = -1
        }

        object.SOTHANG_CK = this.$refs.thongTinKhachHang.input_thang_ck.trim()

        this.dsHDTB.push(object)

        console.log("TaoDuLieu_HDTB", this.dsHDTB)
      } else if (result != null && result.ERROR_CODE == 0) {
        this.$toast.error(result.MESSAGE)
      }
    },
    async TaoDuLieu_DiaChi (themmoi) {
      // Gồm 2 item địa chỉ LD, TB
      this.dsDiaChi = []
      let objectDicChiLD = {}
      let objectDicChiTB = {}

      this.dsDiaChiHDTB = []
      let objectDiaChiHDTB = {}

      if (themmoi) {
        this.dataDiaChiLD.diachi_id = await this.get_keys("DIACHI")
        this.dataDiaChiTB.diachi_id = await this.get_keys("DIACHI")
      }

      // Địa chỉ ld
      objectDicChiLD.DIACHI_ID = this.dataDiaChiLD.diachi_id
      objectDicChiLD.TINH_ID = this.dataDiaChiLD.tinh_id
      objectDicChiLD.QUAN_ID = this.dataDiaChiLD.quan_id
      objectDicChiLD.PHUONG_ID = this.dataDiaChiLD.phuong_id
      objectDicChiLD.PHO_ID = this.dataDiaChiLD.pho_id
      objectDicChiLD.AP_ID = this.dataDiaChiLD.ap_id
      objectDicChiLD.KHU_ID = this.dataDiaChiLD.khu_id
      objectDicChiLD.DACDIEM_ID = this.dataDiaChiLD.dacdiem_id

      objectDicChiLD.SONHA = this.dataDiaChiLD.sonha
      objectDicChiLD.DIACHI = this.dataDiaChiLD.diachi
      objectDicChiLD.BLOCK = this.dataDiaChiLD.block
      objectDicChiLD.TANG = this.dataDiaChiLD.tang
      objectDicChiLD.PHONG = this.dataDiaChiLD.sophong
      objectDicChiLD.MOTA = this.dataDiaChiLD.motathem

      this.dsDiaChi.push(objectDicChiLD)

      // Địa chỉ tb
      objectDicChiTB.DIACHI_ID = this.dataDiaChiTB.diachi_id
      objectDicChiTB.TINH_ID = this.dataDiaChiTB.tinh_id
      objectDicChiTB.QUAN_ID = this.dataDiaChiTB.quan_id
      objectDicChiTB.PHUONG_ID = this.dataDiaChiTB.phuong_id
      objectDicChiTB.PHO_ID = this.dataDiaChiTB.pho_id
      objectDicChiTB.AP_ID = this.dataDiaChiTB.ap_id
      objectDicChiTB.KHU_ID = this.dataDiaChiTB.khu_id
      objectDicChiTB.DACDIEM_ID = this.dataDiaChiTB.dacdiem_id

      objectDicChiTB.SONHA = this.dataDiaChiTB.sonha
      objectDicChiTB.DIACHI = this.dataDiaChiTB.diachi
      objectDicChiTB.BLOCK = this.dataDiaChiTB.block
      objectDicChiTB.TANG = this.dataDiaChiTB.tang
      objectDicChiTB.PHONG = this.dataDiaChiTB.sophong
      objectDicChiTB.MOTA = this.dataDiaChiTB.motathem

      this.dsDiaChi.push(objectDicChiTB)

      console.log("TaoDuLieu_DiaChi dsDiaChi", this.dsDiaChi)

      objectDiaChiHDTB.DIACHI_ID = this.dataDiaChiTB.diachi_id
      objectDiaChiHDTB.HDTB_ID = this.hdtb_id
      objectDiaChiHDTB.DIACHILD_ID = this.dataDiaChiLD.diachi_id
      objectDiaChiHDTB.KINHDO = this.dataDiaChiTB.kinhdo
      objectDiaChiHDTB.VIDO = this.dataDiaChiTB.vido
      objectDiaChiHDTB.KINHDO_LD = this.dataDiaChiLD.kinhdo
      objectDiaChiHDTB.VIDO_LD = this.dataDiaChiLD.vido

      this.dsDiaChiHDTB.push(objectDiaChiHDTB)
      console.log("TaoDuLieu_DiaChi dsDiaChiHDTB", this.dsDiaChiHDTB)
    },
    async TaoDuLieu_HDTB_DUNGTHU (themmoi) {
      this.dsHDTB_DUNGTHU = []
      let object = {}
      object.HDTB_ID = this.hdtb_id
      object.SONGAY = 30
      object.MAY_CN = "localhost"
      object.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
      object.NGUOI_CN = this.$root.token.getUserName()
      object.IP_CN = "127.0.0.1"
      this.dsHDTB_DUNGTHU.push(object)
      console.log("TaoDuLieu_HDTB_DUNGTHU", this.dsHDTB_DUNGTHU)
    },
    // Hàm thừa code cũ không có
    async TaoDuLieu_THONGTIN_TT (themmoi) {

    },
    // Format tiền việt chưa làm, để sau làm
    async TaoDuLieu_CT_TIENHD (themmoi) {
      this.dsCTTHD = []
      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_MUATHIETBI.toString()) {
          for (let j = 0; j < this.dsCTM_TBI.length; j++) {
            let object = {}
            object.HDTB_ID = this.hdtb_id
            if (themmoi) {
              object.CT_TIENHD_ID = await this.get_keys("CT_TIENHD")
            }
            object.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_MUATHIETBI
            object.TIEN = this.dsCTM_TBI[j].TIEN != null ? Number(this.dsCTM_TBI[j].TIEN) : 0
            object.VAT = this.dsCTM_TBI[j].VAT != null ? Number(this.dsCTM_TBI[j].VAT) : 0
            object.ID = this.dsCTM_TBI[j].LOAITBI_ID != null ? Number(this.dsCTM_TBI[j].LOAITBI_ID) : 0
            object.LOAI_ID = 3
            this.dsCTTHD.push(object)
          }
        } else if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_KM_THIETBI.toString()) {
          for (let j = 0; j < this.dsCTM_TBI.length; j++) {
            let object = {}
            object.HDTB_ID = this.hdtb_id
            if (themmoi) {
              object.CT_TIENHD_ID = await this.get_keys("CT_TIENHD")
            }
            object.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_KM_THIETBI

            var soluong = this.dsCTM_TBI[j].SOLUONG != null ? Number(this.dsCTM_TBI[j].SOLUONG) : 1
            let tien = this.dsCTM_TBI[j].TIEN_KM != null ? Number(this.dsCTM_TBI[j].TIEN_KM) * soluong : 0
            var vat = this.dsCTM_TBI[j].VAT_KM != null ? Number(this.dsCTM_TBI[j].VAT_KM) * soluong : 0
            object.TIEN = Math.abs(tien) * -1
            object.VAT = Math.abs(vat) * -1
            object.ID = this.dsCTM_TBI[j].LOAITBI_ID != null ? Number(this.dsCTM_TBI[j].LOAITBI_ID) : 0
            object.LOAI_ID = 3
            this.dsCTTHD.push(object)
          }
        } else if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_TRATRUOC_THIETBI.toString()) {
          for (let j = 0; j < this.dsCTM_TBI.length; j++) {
            let object = {}
            object.HDTB_ID = this.hdtb_id
            if (themmoi) {
              object.CT_TIENHD_ID = await this.get_keys("CT_TIENHD")
            }
            object.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_TRATRUOC_THIETBI
            var soluong = this.dsCTM_TBI[j].SOLUONG != null ? Number(this.dsCTM_TBI[j].SOLUONG) : 1
            let tien = this.dsCTM_TBI[j].TIEN_TRATRUOC != null ? Number(this.dsCTM_TBI[j].TIEN_TRATRUOC) * soluong : 0
            var vat = this.dsCTM_TBI[j].VAT_TRATRUOC != null ? Number(this.dsCTM_TBI[j].VAT_TRATRUOC) * soluong : 0
            object.TIEN = tien
            object.VAT = vat
            object.ID = this.dsCTM_TBI[j].LOAITBI_ID != null ? Number(this.dsCTM_TBI[j].LOAITBI_ID) : 0
            object.LOAI_ID = 3
            this.dsCTTHD.push(object)
          }
        } else {
          let object = {}
          object.HDTB_ID = this.hdtb_id
          if (themmoi) {
            object.CT_TIENHD_ID = await this.get_keys("CT_TIENHD")
          }
          let khoanmuc_id = Number(this.ds_tien_khoanmuc[i].khoanmuctt_id)
          object.KHOANMUCTT_ID = khoanmuc_id
          if (khoanmuc_id == KHOANMUC_TT.KMTT_KM_LAPDAT || khoanmuc_id == KHOANMUC_TT.KMTT_KM_THIETBI ||
                        khoanmuc_id == KHOANMUC_TT.KMTT_KM_DATCOC || khoanmuc_id == KHOANMUC_TT.KMTT_TIENTHOAI_DC) {
            if (Number(this.ds_tien_khoanmuc[i].tien) > 0) {
              object.TIEN = Number(this.ds_tien_khoanmuc[i].tien) * -1
              object.VAT = Number(this.ds_tien_khoanmuc[i].vat) * -1
            } else {
              object.TIEN = Number(this.ds_tien_khoanmuc[i].tien)
              object.VAT = Number(this.ds_tien_khoanmuc[i].vat)
            }
          } else {
            object.TIEN = Number(this.ds_tien_khoanmuc[i].tien)
            object.VAT = Number(this.ds_tien_khoanmuc[i].vat)
          }
          object.ID = khoanmuc_id
          object.LOAI_ID = 1
          this.dsCTTHD.push(object)
        }
      }

      console.log("TaoDuLieu_CT_TIENHD", this.dsCTTHD)
    },
    async CapNhat_CTKM (hdtb_id) {
      // dsKM_HDTB
      for (let i = 0; i < this.dsKM_HDTB.length; i++) {
        this.dsKM_HDTB[i].HDTB_ID = hdtb_id
      }
    },
    // TLS làm sau
    async TaoDuLieu_HDTBDV () {
      // dtControl sp_ht_danhmuc_dv_tdlhtb
      this.dsHDTBDV = []
      let temp = await this.sp_ht_danhmuc_dv_tdlhtb(JSON.stringify({
        DICHVUVT_ID: this.dichvu_vt_selected,
        LOAIHD_ID: LoaiHopDong.CHUYENDOI_LH,
        KIEULD_ID: this.kieuld_id,
        LOAITB_ID_MOI: this.loaihinhtb_id != null ? Number(this.loaihinhtb_id) : 0
      }))
      // LOAIDV_DICH_ID: 4
      // TEN_LOAIDV: "Giao dịch khách hàng"
      // THUTU_ID: 1
      let dtControl = (temp != null && temp.ERROR_CODE == 1) ? temp.RESULT.DS_TT_LOAIDV : []
      console.log("TaoDuLieu_HDTBDV dtControl", dtControl)
      let dtTemp = await this.ur2_1_035_038_temp(this.thuebao_id)
      console.log("TaoDuLieu_HDTBDV dtTemp", dtTemp)
      if (this.dichvu_vt_selected != DichVuVienThong.TSL) {
        // sp_lay_donvi_theo_loaidv_diachi_ld_v1
        // lay_ds_danhba_tsl
        // cbo[i].Tag = dtControl.Rows[i]["loaidv_dich_id"]
        for (let i = 0; i < dtControl.length; i++) {
          var object = {}
          object.HDTB_ID = this.hdtb_id
          let dr = dtTemp.filter(x => x.loaidv_id == dtControl[i].LOAIDV_DICH_ID.toString())
          let _donviID = await this.lay_dv_theo_loai_dv_diachi_ld(dtControl[i].LOAIDV_DICH_ID, this.dataDiaChiLD.phuong_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.khu_id)
          object.LOAIDV_ID = dtControl[i].LOAIDV_DICH_ID
          if (dr.length > 0) {
            object.DONVI_ID = Number(dr[0].donvi_id)
          } else if (dr.length == 0 && _donviID != -1 && _donviID > 0) {
            object.DONVI_ID = _donviID
          } else {
            object.DONVI_ID = 0
          }
          object.KIEUDV_ID = 2
          this.dsHDTBDV.push(object)
        }
      } else {
        let dt = await this.lay_ds_danhba_tsl(this.thuebao_id)
        for (let i = 0; i < dt.length; i++) {
          var object = {}
          object.HDTB_ID = this.hdtb_id
          object.LOAIDV_ID = LOAI_DV.TRAM_VT

          if (dt[i].daucuoi_id != null && dt[i].daucuoi_id.toString() == "1") {
            // điểm đầu
            object.KIEUDV_ID = 1
            object.DONVI_ID = this.tram_dv_dau
          } else {
            object.KIEUDV_ID = 2
            object.DONVI_ID = this.tram_dv_cuoi
          }
          this.dsHDTBDV.push(object)
        }
        // Tạo cho bảng hdtb_dv
        // Với Truyền số liệu thì tự thêm các dòng # trạm viễn thông
        // DS_TT_LOAIDV:LOAIDV_DICH_ID(4,5,23)
        // DS_LOAIDV:(LOAIDV_4,LOAIDV_5,LOAIDV_23)
        // Trừ loại đơn vị trạm trạm viễn thông
        let dsLoaiDV = (temp != null && temp.ERROR_CODE == 1) ? temp.RESULT.DS_LOAIDV : null
        if (dsLoaiDV && dsLoaiDV.LOAIDV_4) {
          for (let i = 0; i < dsLoaiDV.LOAIDV_4.length; i++) {
            var object = {}
            object.HDTB_ID = this.hdtb_id
            object.LOAIDV_ID = 4
            object.DONVI_ID = dsLoaiDV.LOAIDV_4[i].DONVI_ID
            object.KIEUDV_ID = 2
            this.dsHDTBDV.push(object)
          }
        }
        if (dsLoaiDV && dsLoaiDV.LOAIDV_23) {
          for (let i = 0; i < dsLoaiDV.LOAIDV_23.length; i++) {
            var object = {}
            object.HDTB_ID = this.hdtb_id
            object.LOAIDV_ID = 23
            object.DONVI_ID = dsLoaiDV.LOAIDV_23[i].DONVI_ID
            object.KIEUDV_ID = 2
            this.dsHDTBDV.push(object)
          }
        }
      }
      console.log("TaoDuLieu_HDTBDV", this.dsHDTBDV)
    },
    async LoadDL_KV (khuvuc_id) {
      this.dsHDTB_KV = []
      let object = {}
      object.HDTB_ID = this.hdtb_id
      object.KHUVUC_ID = khuvuc_id
      object.LOAIKV_ID = LOAI_KV.KHUVUC_DIABAN
      object.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
      object.NGUOI_CN = this.$root.token.getUserName()
      object.MAY_CN = "localhost"
      object.IP_CN = "127.0.0.1"
      this.dsHDTB_KV.push(object)

      console.log("LoadDL_KV", this.dsHDTB_KV)
    },
    async TaoDuLieu_HDTB_CD (themmoi) {
      this.dsHDTB_CD = []

      // api lấy tạo object
      let result = await this.lay_ds_db_cd_theo_tbid(this.thuebao_id)
      // upper case key data source
      for (var i = 0; i < result.length; i++) {
        for (var key in result[i]) {
          if (key.toUpperCase() !== key) {
            result[i][key.toUpperCase()] = result[i][key]
            delete result[i][key]
          }
        }
      }
      var object = createObjectHDTB_CD(result[0])

      // Object.assign(object, result[0])
      //
      if (object.PHANVUNG_ID == null) {
        object.PHANVUNG_ID = this.$root.token.getPhanVungID()
      }
      object.HDTB_ID = this.hdtb_id
      // 3 khách hàng tự trang bị
      object.TRANGBI_ID = this.$refs.coDinh.trangbi_moi_selected != null ? Number(this.$refs.coDinh.trangbi_moi_selected) : 3
      if (themmoi) {
        object.MADOICAP = await this.get_keys("MADOICAP")
      } else {
        object.MADOICAP = this.madoicap_cd
      }
      object.KETCUOI_ID = this.KetCuoi_ID != null ? this.KetCuoi_ID : 0

      if (this.loaihinhtb_id == LoaiHinhTB.PABX && this.$refs.coDinh.loai_so_selected != null &&
                this.$refs.coDinh.loai_so_selected == LOAISO_ISDN.SOPHU) {
        object.SO_TAT = this.$refs.coDinh.input_sotat.trim()
      } else {
        object.SO_TAT = null
      }
      this.dsHDTB_CD.push(object)
      console.log("TaoDuLieu_HDTB_CD", this.dsHDTB_CD)
    },
    async TaoDuLieu_HDTB_IMS (themmoi) {
      //
      this.dsHDTB_IMS = []
      // lay_ds_db_ims_theo_tbid
      let result = await this.lay_ds_db_ims_theo_tbid(this.thuebao_id)
      // upper case key data source
      for (var i = 0; i < result.length; i++) {
        for (var key in result[i]) {
          if (key.toUpperCase() !== key) {
            result[i][key.toUpperCase()] = result[i][key]
            delete result[i][key]
          }
        }
      }
      var object = createObjectHDTB_IMS(result[0])
      if (object.PHANVUNG_ID == null) {
        object.PHANVUNG_ID = this.$root.token.getPhanVungID()
      }
      object.HDTB_ID = this.hdtb_id
      object.THIETBI_ID = this.$refs.ims.thietbi_ims_moi_selected != null ? this.$refs.ims.thietbi_ims_moi_selected : 0

      var kieu = ""
      let kieuld_id = this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
      let kieuld = this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x => x.kieuld_id == kieuld_id)
      if (kieuld != undefined) {
        kieu = kieuld.kieu.toString()
      }
      if (kieu == "1" || kieu == "2" || kieu == "4") {
        object.MADOICAP = this.$refs.ims.input_madoicap
      } else {
        // TH còn lại sinh mã đôi cáp
        if (themmoi) {
          object.MADOICAP = await this.get_keys("MADOICAP")
          this.$refs.ims.input_madoicap = object.MADOICAP.toString()
        } else {
          object.MADOICAP = this.$refs.ims.input_madoicap
        }
      }
      object.MATB_TN = this.$refs.ims.input_matn != null ? this.$refs.ims.input_matn : ""
      object.TRANGBI_ID = this.$refs.ims.trangbi_moi_selected != null ? this.$refs.ims.trangbi_moi_selected : 3

      if (this.$refs.ims.loaihinh_tb_moi_selected == LoaiHinhTB.IMS_SIPTRUNKING) {
        object.SL_CUOCGOI = (this.$refs.ims.input_sl_cuocgoi != null && this.$refs.ims.input_sl_cuocgoi != "") ? Number(this.$refs.ims.input_sl_cuocgoi) : null
        object.CTDV_ID = this.$refs.ims.chitiet_dv_selected
        if (this.$refs.ims.chitiet_dv_selected == ChiTietDV_IMS.internet) {
          object.IP = this.$refs.ims.input_ip
        }
      } else {
        object.SL_CUOCGOI = null
        object.CTDV_ID = null
        object.IP = null
      }

      this.dsHDTB_IMS.push(object)
    },
    async TaoDuLieu_HDTB_ADSL (themmoi) {
      this.dsHDTB_ADSL = []
      var object = createObjectHDTB_ADSL()
      if (object.PHANVUNG_ID == null) {
        object.PHANVUNG_ID = this.$root.token.getPhanVungID()
      }
      object.HDTB_ID = this.hdtb_id
      object.PASSWORD = this.$refs.bangRongCoDinh.input_matkhau_adsl
      object.EMAIL = this.$refs.bangRongCoDinh.input_email_adsl
      object.TOCDO_ID = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? this.$refs.bangRongCoDinh.tocdo_moi_selected : 0

      // let profiledsl_id = await this.fn_map_id({
      //   id_neo: "profiledsl_id",
      //   table: "css.tocdo_adsl",
      //   dk: "where tocdo_id = " + object.TOCDO_ID
      // }, "-1")
      let profiledsl_id = await service.fn_tt_tocdo_adsl(object.TOCDO_ID, 5)
      if (profiledsl_id != "-1") {
        object.PROFILEDSL_ID = profiledsl_id
      } else {
        object.PROFILEDSL_ID = null
      }
      if (this.$refs.bangRongCoDinh.checkTocDoThuc) {
        object.TOCDOTHUC_ID = this.$refs.bangRongCoDinh.tocdo_thuc_selected != null ? this.$refs.bangRongCoDinh.tocdo_thuc_selected : 0
      } else {
        object.TOCDOTHUC_ID = object.TOCDO_ID
      }
      if (this.KetCuoi_ID > 0) {
        object.KETCUOI_ID = this.KetCuoi_ID
      }
      object.MUCCUOC_ID = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? this.$refs.bangRongCoDinh.muccuoc_moi_selected : 0
      object.THIETBIDC_ID = this.$refs.bangRongCoDinh.loaithietbi_moi_selected != null ? this.$refs.bangRongCoDinh.loaithietbi_moi_selected : 0

      // fn_tt_taodulieu_hdtb_adsl_cdlhtb
      let result = await this.fn_tt_taodulieu_hdtb_adsl_cdlhtb(JSON.stringify({
        THUEBAO_ID: this.thuebao_id,
        TOCDO_ID: object.TOCDO_ID,
        NHAPMOI: themmoi ? 1 : 2,
        QUAN_ID: this.dataDiaChiLD.quan_id,
        MADOICAP: "",
        DICHVUVT_ID: this.dichvu_vt_selected,
        LOAITB_MOI_ID: this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected != null ? this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected : 0,
        LOAITB_CU_ID: this.$refs.thongTinPhieuYeuCau.loaihinh_selected != null ? this.$refs.thongTinPhieuYeuCau.loaihinh_selected : 0
      }))

      if (this.thamso.TUDONG_SINH_SOAO) {
        if (this.$refs.thongTinPhieuYeuCau.loaihinh_selected != LoaiHinhTB.INTERNET_MYTV) {
          if (this.$root.token.getMaTinh() == "HPG") {
            // Code riêng HPG, nếu mã lt ko đúng chuẩn thì sinh mã mới
            var isOld = false
            var malt = ""
            // malt = await this.fn_map_id({
            //   id_neo: "ma_lt",
            //   table: "css.db_adsl",
            //   dk: "where thuebao_id = " + this.thuebao_id
            // }, "-1")
            malt = await service.fn_tt_tocdo_adsl(this.thuebao_id, 2)
            if (malt != "-1" && malt.length > 3 && malt.substring(0, 3) != "131") {
              isOld = true
            }
            if (isOld) {
              object.MA_LT = result.MA_LT
            } else {
              object.MA_LT = malt
            }
          } else {
            if (result != null) {
              object.MA_LT = result.MA_LT
            } else {
              object.MA_LT = null
            }
          }
        } else {
          // Nếu là Mytv thì giữ nguyên số ảo, ko cần sinh lại nữa
          // let malt = await this.fn_map_id({
          //   id_neo: "ma_lt",
          //   table: "css.db_adsl",
          //   dk: "where thuebao_id = " + this.thuebao_id
          // }, "-1")
          let malt = await service.fn_tt_tocdo_adsl(this.thuebao_id, 2)
          if (malt != "-1") {
            object.MA_LT = malt
          }
        }
      } else {
        // let malt = await this.fn_map_id({
        //   id_neo: "ma_lt",
        //   table: "css.db_adsl",
        //   dk: "where thuebao_id = " + this.thuebao_id
        // }, "-1")
        let malt = await service.fn_tt_tocdo_adsl(this.thuebao_id, 2)
        if (malt != "-1") {
          object.MA_LT = malt
        }
      }
      //
      object.MATB_TN = this.$refs.bangRongCoDinh.input_matn.trim()
      // Gán giá trị cho column tức thì theo mức cước
      if (this.$refs.bangRongCoDinh.checkTucThi) {
        if (this.$refs.bangRongCoDinh.muccuoc_selected != this.$refs.bangRongCoDinh.muccuoc_moi_selected) {
          object.TUCTHI = 3
        } else {
          object.TUCTHI = 2
        }
      } else {
        // Đối với chuyển đổi mega-FTTH chỉ có tháng 6 mới chuyển đổi (tucthi=0)
        object.TUCTHI = 0
      }
      object.TRANGBI_ID = this.$refs.bangRongCoDinh.trangbi_moi_selected
      object.THONGTIN_TC = this.$refs.bangRongCoDinh.input_tttc != null ? this.$refs.bangRongCoDinh.input_tttc.trim() : ""

      let kieuld_id = this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
      let kieu_ld = this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x => x.kieuld_id == kieuld_id)
      if (!kieu_ld) {
        return
      }
      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      if (kieu_ld.kieu == "1" || kieu_ld.kieu == "2") {
        if (this.$refs.bangRongCoDinh.input_madoicap_adsl.trim() == "") {
          this.kt_adsl = false
          this.$toast.error("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập")
          return
        }
        object.MADOICAP = this.$refs.bangRongCoDinh.input_madoicap_adsl.trim()
      } else {
        // TH còn lại sinh mã đôi cáp
        if (themmoi) {
          object.MADOICAP = result.MADOICAP
        } else {
          object.MADOICAP = this.$refs.bangRongCoDinh.input_madoicap_adsl.trim()
        }
      }

      this.dsHDTB_ADSL.push(object)
    },
    async TaoDuLieu_HDTB_MGWAN (themmoi) {
      this.dsHDTB_MEWAN = []
      var object = createObjectHDTB_MGWAN()
      if (object.PHANVUNG_ID == null) {
        object.PHANVUNG_ID = this.$root.token.getPhanVungID()
      }
      //
      let result = await this.fn_tt_taodulieu_hdtb_mgwan_cdlhtb(JSON.stringify({
        THUEBAO_ID: this.thuebao_id,
        TOCDO_ID: this.$refs.megawan.tocdo_cir_moi_selected != null ? this.$refs.megawan.tocdo_cir_moi_selected : 0,
        NHAPMOI: themmoi ? 1 : 2,
        QUAN_ID: this.dataDiaChiLD.quan_id,
        MADOICAP: "",
        DICHVUVT_ID: this.dichvu_vt_selected,
        LOAITB_MOI_ID: this.$refs.megawan.loaihinh_tb_moi_selected != null ? this.$refs.megawan.loaihinh_tb_moi_selected : 0,
        LOAITB_CU_ID: this.$refs.thongTinPhieuYeuCau.loaihinh_selected != null ? this.$refs.thongTinPhieuYeuCau.loaihinh_selected : 0
      }))
      if (result != null && result.ERROR_CODE == 1) {
        let dbMegaWan = result.RESULT.LAY_DS_DB_MEGAWAN_THEO_TBID[0]
        console.log("dbMegaWan", dbMegaWan)
        object.HDTB_ID = this.hdtb_id
        object.TOCDO_ID = this.$refs.megawan.tocdo_cir_moi_selected != null ? this.$refs.megawan.tocdo_cir_moi_selected : 0
        if (this.$refs.megawan.checkTocDoPIRMoi) {
          object.TOCDO_PIR_ID = this.$refs.megawan.tocdo_pir_moi_selected != null ? this.$refs.megawan.tocdo_pir_moi_selected : 0
        }
        object.LOAIMD_ID = this.$refs.megawan.modem_moi_selected != null ? this.$refs.megawan.modem_moi_selected : 0
        object.LOAIKENH_ID = this.$refs.megawan.loaikenh_moi_selected != null ? this.$refs.megawan.loaikenh_moi_selected : 0
        object.THIETBIDC_ID = this.$refs.megawan.loaithietbi_moi_selected != null ? this.$refs.megawan.loaithietbi_moi_selected : 0

        object.HUONG_KN = this.vhuong_kn
        object.TINH_KN_ID = dbMegaWan.TINH_KN_ID
        object.THOIHAN_ID = this.$refs.megawan.thoihan_moi_selected != null ? this.$refs.megawan.thoihan_moi_selected : 0
        object.TRANGBI_ID = this.$refs.megawan.trangbi_moi_selected != null ? this.$refs.megawan.trangbi_moi_selected : 0
        object.CHUQUAN_ID = this.$refs.megawan.chuquan_moi_selected != null ? this.$refs.megawan.chuquan_moi_selected : 0
        object.LOAICAP_ID = this.$refs.megawan.loaicap_moi_selected != null ? this.$refs.megawan.loaicap_moi_selected : 0

        if (this.KetCuoi_ID > 0) {
          object.KETCUOI_ID = this.KetCuoi_ID
        }

        if (this.thamso.TUDONG_SINH_SOAO) {
          if (this.$root.token.getMaTinh() == "HPG") {
            // Code riêng HPG, nếu mã lt ko đúng chuẩn thì sinh mã mới
            var isOld = false
            if (dbMegaWan.MA_LT != null && dbMegaWan.MA_LT.toString().length > 3) {
              if (dbMegaWan.MA_LT.toString().substring(0, 3) != "131") {
                isOld = true
              }
            }
            if (isOld) {
              object.MA_LT = result.RESULT.MA_LT
            } else {
              object.MA_LT = dbMegaWan.MA_LT
            }
          }
        } else {
          object.MA_LT = dbMegaWan.MA_LT
        }

        object.MA_PL = this.$refs.thongTinPhieuYeuCau.input_ma_cng.trim()
        object.SITE_ID = this.$refs.megawan.input_site_id_moi.trim()
        if (this.$refs.thongTinKhachHang.checkSoCV) {
          object.MA_CV = this.$refs.thongTinKhachHang.input_socv
          object.NGAY_CV = this.$refs.thongTinKhachHang.ngay_cv
        }
        if (dbMegaWan.LOAINODE_ID != null) {
          object.LOAINODE_ID = dbMegaWan.LOAINODE_ID
        }
        if (themmoi) {
          object.MADOICAP = result.RESULT.MADOICAP
        } else {
          object.MADOICAP = this.madoicap_mgw
        }

        if (this.$refs.megawan.checkDoiTacMoi) {
          object.DOITAC_ID = this.$refs.megawan.doitac_moi_selcted != null ? this.$refs.megawan.doitac_moi_selcted : 0
          if (this.$refs.megawan.input_cuoc_tkdt_moi) {
            object.CUOC_TKDT = Number(this.$refs.megawan.input_cuoc_tkdt_moi.trim())
          } else {
            object.CUOC_TKDT = 0
          }
          if (this.$refs.megawan.input_cuoc_tcdt_moi) {
            object.CUOC_TCDT = Number(this.$refs.megawan.input_cuoc_tcdt_moi.trim())
          } else {
            object.CUOC_TCDT = 0
          }
          object.MAHD_DT = this.$refs.megawan.input_ma_hddt_moi != null ? this.$refs.megawan.input_ma_hddt_moi.trim() : ""
        }

        if (this.$refs.megawan.input_cuoc_ht_moi) {
          object.CUOC_HT = Number(this.$refs.megawan.input_cuoc_ht_moi.trim())
        } else {
          object.CUOC_HT = 0
        }
        if (this.$refs.megawan.input_cuoc_tb_moi) {
          object.CUOC_TBI = Number(this.$refs.megawan.input_cuoc_tb_moi.trim())
        } else {
          object.CUOC_TBI = 0
        }
        if (this.$refs.megawan.input_cuoc_tc_moi) {
          object.CUOC_TC = Number(this.$refs.megawan.input_cuoc_tc_moi.trim())
        } else {
          object.CUOC_TC = 0
        }
        if (this.$refs.megawan.input_cuoc_tk_moi) {
          object.CUOC_TK = Number(this.$refs.megawan.input_cuoc_tk_moi.trim())
        } else {
          object.CUOC_TK = 0
        }
        object.LAN_IP = dbMegaWan.LAN_IP
        object.WAN_IP = dbMegaWan.WAN_IP

        // Bổ sung theo https://cntt.vnpt.vn/browse/IT360-54327
        if (this.$refs.thongTinPhieuYeuCau.kieu_yc_selected &&
                    Number(this.$refs.thongTinPhieuYeuCau.kieu_yc_selected) == KieuLapDat.CHUYENTU_METRONET_FE_SANG_METRONET_GE) {
          object.PORT_ID = dbMegaWan.PORT_ID
          object.VCI_VPI_ID = dbMegaWan.VCI_VPI_ID
          object.SVLAN = dbMegaWan.SVLAN
          object.CVLAN = dbMegaWan.CVLAN
        }

        this.dsHDTB_MEWAN.push(object)
      } else if (result != null && result.ERROR_CODE == 0) {
        this.$toast.error(result.MESSAGE)
      }
    },
    async TaoDuLieu_HDTB_TSL (themmoi) {
      //
      this.dsHDTB_TSL = []
      // Tạo điểm đầu i=0, điểm cuối = 1
      for (let i = 0; i < 2; i++) {
        var object = createObjectHDTB_TSL()
        if (object.PHANVUNG_ID == null) {
          object.PHANVUNG_ID = this.$root.token.getPhanVungID()
        }
        object.HDTB_ID = this.hdtb_id
        object.DAUCUOI_ID = i + 1
        object.TOCDO_ID = this.$refs.kenhThuRieng.tocdo_moi_selected ? this.$refs.kenhThuRieng.tocdo_moi_selected : 0
        object.LOAIKENH_ID = this.$refs.kenhThuRieng.loaikenh_moi_selected ? this.$refs.kenhThuRieng.loaikenh_moi_selected : 0
        object.THOIHAN_ID = this.$refs.kenhThuRieng.thoihan_moi_selected ? this.$refs.kenhThuRieng.thoihan_moi_selected : 0
        object.TRANGBI_ID = this.$refs.kenhThuRieng.trangbi_moi_selected ? this.$refs.kenhThuRieng.trangbi_moi_selected : 0
        object.CHUQUAN_ID = this.$refs.kenhThuRieng.chuquan_moi_selected ? this.$refs.kenhThuRieng.chuquan_moi_selected : 0
        object.THIETBIDC_ID = this.$refs.kenhThuRieng.loaithietbi_moi_selected ? this.$refs.kenhThuRieng.loaithietbi_moi_selected : 0
        object.MA_PL = this.$refs.thongTinPhieuYeuCau.input_ma_cng.trim()

        if (this.$refs.thongTinKhachHang.checkSoCV) {
          object.MA_CV = this.$refs.thongTinKhachHang.input_socv.trim()
          object.NGAY_CV = this.$refs.thongTinKhachHang.ngay_cv
        }
        if (this.$refs.kenhThuRieng.checkDoiTacMoi) {
          object.DOITAC_ID = this.$refs.kenhThuRieng.doitac_moi_selected ? this.$refs.kenhThuRieng.doitac_moi_selected : 0
          object.CUOC_TKDT = this.$refs.kenhThuRieng.input_cuoc_tkdt_moi ? Number(this.$refs.kenhThuRieng.input_cuoc_tkdt_moi) : 0
          object.CUOC_TCDT = this.$refs.kenhThuRieng.input_cuoc_tcdt_moi ? Number(this.$refs.kenhThuRieng.input_cuoc_tcdt_moi) : 0
          object.MAHD_DT = this.$refs.kenhThuRieng.input_ma_hddt_moi ? this.$refs.kenhThuRieng.input_ma_hddt_moi.trim() : ""
        }

        object.CUOC_HT = this.$refs.kenhThuRieng.input_cuoc_ht_moi ? Number(this.$refs.kenhThuRieng.input_cuoc_ht_moi) : 0
        object.CUOC_TBI = this.$refs.kenhThuRieng.input_cuoc_tb_moi ? Number(this.$refs.kenhThuRieng.input_cuoc_tb_moi) : 0
        object.CUOC_TC = this.$refs.kenhThuRieng.input_cuoc_tc_moi ? Number(this.$refs.kenhThuRieng.input_cuoc_tc_moi) : 0
        object.CUOC_TK = this.$refs.kenhThuRieng.input_cuoc_tk_moi ? Number(this.$refs.kenhThuRieng.input_cuoc_tk_moi) : 0

        if (i == 0) {
          // Điểm đầu
          object.DIACHI = this.$refs.kenhThuRieng.input_diachi_dau_moi ? this.$refs.kenhThuRieng.input_diachi_dau_moi.trim() : ""
          object.DIACHI_ID = this.diachidau_id
          object.MA_TB_SUB = this.ma_tb_dau
          object.LOAICAP_ID = this.$refs.kenhThuRieng.lcapdau_moi_selected ? this.$refs.kenhThuRieng.lcapdau_moi_selected : 0
        } else {
          // Điểm cuối
          object.DIACHI = this.$refs.kenhThuRieng.input_diachi_cuoi_moi ? this.$refs.kenhThuRieng.input_diachi_cuoi_moi.trim() : ""
          object.DIACHI_ID = this.diachicuoi_id
          object.MA_TB_SUB = this.ma_tb_cuoi
          object.LOAICAP_ID = this.$refs.kenhThuRieng.lcapcuoi_moi_selected ? this.$refs.kenhThuRieng.lcapcuoi_moi_selected : 0
        }

        this.dsHDTB_TSL.push(object)
      }
    },
    async TaoDuLieu_HDTB_DD (themmoi) {
      //
      this.dsHDTB_DD = []
      var object = createObjectHDTB_DD()
      if (object.PHANVUNG_ID == null) {
        object.PHANVUNG_ID = this.$root.token.getPhanVungID()
      }
      //
      object.HDTB_ID = this.hdtb_id
      object.SIMCARD = this.textBox7
      object.GOICUOC_ID = this.$refs.diDong.goicuoc_dd_moi_selected != null ? Number(this.$refs.diDong.goicuoc_dd_moi_selected) : 0
      // 1 <->Hủy thương, 2<--> Chuyển sang trả trước, 3<--> Chuyển vùng + mã tỉnh
      object.KIEU_TL = "2"
      object.TRANGBI_ID = this.$refs.diDong.trangbi_moi_selected != null ? Number(this.$refs.diDong.trangbi_moi_selected) : 0
      // object.MA_GD_NEO = await this.fn_map_id({
      //   id_neo: "ma_gd_neo",
      //   table: "css.hdtb_dd",
      //   dk: "where hdtb_id = " + this.hdtb_id
      // }, "-1")
      object.MA_GD_NEO = await service.fn_tt_hdtb_dd(this.hdtb_id, 1)
      this.dsHDTB_DD.push(object)
    },
    async TaoDuLieu_TIEN_HDTB_TC (themmoi) {
      //
      this.dsTIEN_HDTB_TC = []
      var object = {}
      object.HDTB_ID = this.hdtb_id
      object.THUEBAO_ID = this.thuebao_id
      object.LOAITIEN_ID = 2
      object.GIATRI = Number(this.$refs.diDong.txtCuocTamThu)

      this.dsTIEN_HDTB_TC.push(object)
    },
    async lay_dv_theo_loai_dv_diachi_ld (loaidv_dich_id, phuongld_id, phold_id, apld_id, khuld_id) {
      // sp_lay_donvi_theo_loaidv_diachi_ld_v1
      if (loaidv_dich_id != LOAI_DV.TRAM_VT && loaidv_dich_id != LOAI_DV.TTKD_NGHIEMTHU) {
        let dsvt = await this.sp_lay_donvi_theo_loaidv_diachi_ld_v1({
          p_loaidv_id: loaidv_dich_id,
          p_phuong_id: phuongld_id,
          p_pho_id: phold_id,
          p_ap_id: apld_id,
          p_khu_id: khuld_id
        })
        if (dsvt.length > 0) {
          return Number(dsvt[0].donvi_id)
        } else {
          return -1
        }
      } else if (loaidv_dich_id == LOAI_DV.TTKD_NGHIEMTHU) {
        return this.$root.token.getDonViID()
      } else {
        return -1
      }
    },
    KiemTraDL_KhachHang () {
      let ngay_cn = moment(new Date()).format("DD/MM/YYYY")
      let ngay_yc = this.$refs.thongTinPhieuYeuCau.ngay_yc != null ? this.$refs.thongTinPhieuYeuCau.ngay_yc : ngay_cn
      let ngay_lhd = this.$refs.thongTinKhachHang.ngay_lhd != null ? this.$refs.thongTinKhachHang.ngay_lhd : ngay_cn
      if (!this.KiemTraDK_LHD(ngay_yc, ngay_cn)) {
        this.$toast.error("Ngày yêu cầu không được lớn hơn ngày hiện tại!")
        return false
      }
      if (!this.KiemTraDK_LHD(ngay_lhd, ngay_yc)) {
        this.$toast.error("Bạn đang lập phụ lục cho khách hàng cũ. Ngày yêu cầu không được nhỏ hơn ngày lập hợp đồng!")
        return false
      }

      if (this.$refs.thongTinKhachHang.input_ten_kh.trim() == "") {
        this.$toast.error("Hãy nhập tên khách hàng !")
        this.$refs.thongTinKhachHang.focusTenKH()
        return false
      }
      if (this.$refs.thongTinKhachHang.input_makh.trim() == "" && this.khachhang_id == 0) {
        this.$toast.error("Kiểm tra lại mã khách hàng !")
        this.$refs.thongTinKhachHang.focusMaKH()
        return false
      }

      if (this.thamso.khong_batbuoc_ctv == 0) {
        if (!this.checkCTV || this.ctv.ten_ctv == "" || this.ctv.ctv_id == null || this.ctv.ctv_id == 0) {
          this.$toast.error("Bạn chưa chọn nhân viên phát triển!")
          this.$refs.inputCTV.focus()
          return false
        }
      }

      if (this.thamso.batbuoc_gioithieu_loaihinh == 1) {
        if (!this.checkNguoiGT || this.nguoi_gt.ten_nguoi_gt == "" || this.nguoi_gt.nguoi_gt_id == null || this.nguoi_gt.nguoi_gt_id == 0) {
          this.$toast.error("Bạn chưa chọn người giới thiệu hợp đồng/phụ lục")
          this.$refs.inputNguoiGT.focus()
          return false
        }
      }

      if (this.checkCTV && this.ctv.ten_ctv == "") {
        this.$toast.error("Bạn chưa chọn cộng tác viên !")
        this.$refs.inputCTV.focus()
        return false
      }

      if (this.checkNguoiGT && this.nguoi_gt.ten_nguoi_gt == "") {
        this.$toast.error("Bạn chưa chọn người giới thiệu hợp đồng !")
        this.$refs.inputNguoiGT.focus()
        return false
      }

      if (this.$refs.thongTinKhachHang.input_sdt.trim() == "") {
        this.$toast.error("Hãy nhập số điện thoại liên hệ của khách hàng.")
        this.$refs.thongTinKhachHang.focusSoDT()
        return false
      }

      if (this.$refs.thongTinKhachHang.input_sdt.trim().length > 10) {
        this.$toast.error("Số điện thoại liên hệ của khách hàng không được quá 10 ký tự.\nBạn hãy bỏ số 0 ở đầu (nếu có)!")
        this.$refs.thongTinKhachHang.focusSoDT()
        return false
      }
      if (this.$refs.thongTinKhachHang.input_sdt.trim().length < 7) {
        this.$toast.error("Số điện thoại liên hệ của khách hàng không được ít hơn 7 ký tự.")
        this.$refs.thongTinKhachHang.focusSoDT()
        return false
      }
      return true
    },
    KiemTraDL_ThueBao () {
      if (this.$refs.thongTinKhachHang.input_ma_hd.trim() == "" && this.khachhang_id != 0) {
        this.$toast.error("Hãy nhập mã hợp đồng !")
        this.$refs.thongTinKhachHang.focusMaHD()
        return false
      }
      if (this.isdn_sochinh && this.ds_isdn.length > 1) {
        this.$toast.error("Không chuyển đổi loại hình cho thuê bao ISDN số chính có số phụ đi kèm!")
        return false
      }
      if (this.kieuld_id == KieuLapDat.CHUYENGP_TRATRUOC_SANG_TRASAU) {
        if (this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected == null || this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected == 0) {
          this.$toast.error("Bạn chưa chọn mức cước thuê bao cho kiểu lắp đặt này!")
          return false
        }
      }
      if (this.$refs.thongTinKhachHang.input_ten_tb.trim() == "") {
        this.$toast.error("Hãy nhập tên thuê bao !")
        this.$refs.thongTinKhachHang.focusTenTB()
        return false
      }

      const indexKieuLD = this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.findIndex(x => x.kieuld_id == this.$refs.thongTinPhieuYeuCau.kieu_yc_selected)
      if (indexKieuLD < 0) {
        this.$toast.error("Không có kiểu lắp đặt cho loại hình " + this.$refs.thongTinPhieuYeuCau.getLoaiHinhTB())
        return false
      }

      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        if (this.$refs.coDinh.getLoaiHinhTBMoi() == "") {
          this.$toast.error("Bạn chưa chọn loại hình mới !")
          this.$refs.coDinh.focusLoaiHinhTBMoi()
          return false
        }
        if (this.thamso.nhap_nvkd == 1) {
          if (this.khuvuc_id == 0 || this.$refs.coDinh.input_khuvuc == "") {
            this.$toast.error("Bạn chưa chọn khu vực quản lý địa bàn cho thuê bao!")
            return false
          }
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {

      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        if (this.$refs.bangRongCoDinh.getLoaiHinhTBMoi() == "") {
          this.$toast.error("Bạn chưa chọn loại hình mới !")
          this.$refs.bangRongCoDinh.focusLoaiHinhTBMoi()
          return false
        }
        if (this.thamso.nhap_nvkd == 1) {
          if (this.khuvuc_id == 0 || this.$refs.bangRongCoDinh.input_khuvuc == "") {
            this.$toast.error("Bạn chưa chọn khu vực quản lý địa bàn cho thuê bao!")
            return false
          }
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        if (this.$refs.ims.getLoaiHinhTBMoi() == "") {
          this.$toast.error("Bạn chưa chọn loại hình mới !")
          this.$refs.ims.focusLoaiHinhTBMoi()
          return false
        }
        if (this.thamso.nhap_nvkd == 1) {
          if (this.khuvuc_id == 0 || this.$refs.ims.input_khuvuc == "") {
            this.$toast.error("Bạn chưa chọn khu vực quản lý địa bàn cho thuê bao!")
            return false
          }
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        if (this.$refs.kenhThuRieng.getLoaiHinhTBMoi() == "") {
          this.$toast.error("Bạn chưa chọn loại hình mới !")
          this.$refs.kenhThuRieng.focusLoaiHinhTBMoi()
          return false
        }
        if (this.thamso.nhap_nvkd == 1) {
          if (this.khuvuc_id == 0 || this.$refs.kenhThuRieng.input_khuvuc == "") {
            this.$toast.error("Bạn chưa chọn khu vực quản lý địa bàn cho thuê bao!")
            return false
          }
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        if (this.$refs.megawan.getLoaiHinhTBMoi() == "") {
          this.$toast.error("Bạn chưa chọn loại hình mới !")
          this.$refs.megawan.focusLoaiHinhTBMoi()
          return false
        }
        if (this.thamso.nhap_nvkd == 1) {
          if (this.khuvuc_id == 0 || this.$refs.megawan.input_khuvuc == "") {
            this.$toast.error("Bạn chưa chọn khu vực quản lý địa bàn cho thuê bao!")
            return false
          }
        }
      }
      // Kiểm tra Cố định
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        if (this.kieuld_id == KieuLapDat.CHUYENTU_2B_SANG_30B) {
          if (this.loaihinhtb_id == LoaiHinhTB.ISDN2B_CD || this.loaihinhtb_id == LoaiHinhTB.ISDN2B_CQ ||
                        this.loaihinhtb_id == LoaiHinhTB.DIENTHOAI_CD) {
            this.$toast.error("Loại hình thuê bao mới phải là 30B+D !")
            return false
          }
        }
        if (this.kieuld_id == KieuLapDat.CHUYENTU_30B_SANG_2B) {
          if (this.loaihinhtb_id == LoaiHinhTB.ISDN30B_CD || this.loaihinhtb_id == LoaiHinhTB.ISDN30B_CQ || this.loaihinhtb_id == LoaiHinhTB.DIENTHOAI_CD) {
            this.$toast.error("Loại hình thuê bao mới phải là 2B+D !")
            return false
          }
        }
        if (this.kieuld_id == KieuLapDat.CHUYENTU_CODINH_SANG_ISDN) {
          if (this.loaihinhtb_id == LoaiHinhTB.DIENTHOAI_CD) {
            this.$toast.error("Loại hình thuê bao mới phải là ISDN !")
            return false
          }
        }
        if (this.kieuld_id == KieuLapDat.CHUYENTU_ISDN_SANG_CODINH) {
          if (this.loaihinhtb_id == LoaiHinhTB.ISDN30B_CD || this.loaihinhtb_id == LoaiHinhTB.ISDN30B_CQ ||
                    this.loaihinhtb_id == LoaiHinhTB.ISDN2B_CD || this.loaihinhtb_id == LoaiHinhTB.ISDN2B_CQ) {
            this.$toast.error("Loại hình thuê bao mới phải là Cố định !")
            return false
          }
        }

        if (this.$refs.thongTinPhieuYeuCau.disableMucCuocTB) {
          if (this.$refs.coDinh.input_cuoctb_moi.trim() == "") {
            this.$toast.error("Chưa nhập mức cước thuê bao cho thuê bao!")
            this.$refs.coDinh.focusCuocTBMoi()
            return false
          }
          if (this.$refs.coDinh.loai_so_selected != null && this.$refs.coDinh.loai_so_selected == LOAISO_ISDN.SOPHU) {
            if (this.$refs.coDinh.input_sochinh_moi.trim() == "") {
              this.$toast.error("Chưa chọn số chính !")
              // btnChonSoChinh.Focus();
              return false
            }
            // khúc dưới DB xử lý trong hàm check gom
          }
        }
        // khúc dưới DB xử lý trong hàm check gom
      }
      // Kiểm tra khuyến mãi trả trước
      if (this.$refs.thongTinPhieuYeuCau.khuyenmai.checkKhuyenMai && this.dsKM_HDTB.length == 0) {
        this.$toast.error("Bạn chưa chọn chi tiết khuyến mại!")
        return false
      }
      if (this.$refs.thongTinPhieuYeuCau.tratruoc.checkTraTruoc && this.dsDC_HDTB.length == 0) {
        this.$toast.error("Bạn chưa chọn chi tiết trả trước!")
        return false
      }
      // Thêm kiểm tra gói trước khi lập phụ lục thay đổi LHTB
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH || this.dichvu_vt_selected == DichVuVienThong.ADSL ||
                this.dichvu_vt_selected == DichVuVienThong.IMS) {
        if (this.$refs.thongTinPhieuYeuCau.ds_muccuoc_tb.length <= 0) {
          this.$toast.error("Hãy nhập mức cước thuê bao!")
          return false
        }
        // khúc dưới DB xử lý trong hàm check gom
      }
      //
      var kieu = ""
      let kieuLD = this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x => x.kieuld_id == this.$refs.thongTinPhieuYeuCau.kieu_yc_selected)
      kieu = kieuLD != undefined ? kieuLD.kieu : ""
      // Kiểm tra thuê bao IMS
      if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        if (this.loaihinhtb_id == LoaiHinhTB.IMS_SIPTRUNKING) {
          if (this.$refs.ims.input_sl_cuocgoi.trim() == "" || this.$refs.ims.input_sl_cuocgoi.trim() == "0") {
            this.$toast.error("Bạn chưa nhập số lượng cuộc gọi cho thuê bao Sip trunking!")
            this.$refs.ims.focusSLCuocGoi()
            return false
          }
          if (this.$refs.ims.chitiet_dv_selected == 0) {
            this.$toast.error("Hãy chọn chi tiết DV")
            return false
          }
          // Nếu là lắp trên đường riêng thì chi tiết dịch vụ chỉ là nền internet thôi
          if (kieu == "0") {
            if (this.$refs.ims.chitiet_dv_selected != ChiTietDV_IMS.internet) {
              this.$toast.error("Với loại hình Siptrunking, lắp trên đường riêng bắt buộc phải chọn chi tiết dịch vụ là trên nền Internet")
              return false
            }
          }
          if (this.$refs.ims.chitiet_dv_selected == ChiTietDV_IMS.internet) {
            if (this.thamso.phainhap_ip_siptrunking && this.$refs.ims.input_ip.trim() == "") {
              this.$toast.error("Hãy nhập địa chỉ IP !")
              this.$refs.ims.focusInputIP()
              return false
            }
          }
        }
      }
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL && (this.loaihinhtb_id == LoaiHinhTB.INTERNET_ADSL || this.loaihinhtb_id == LoaiHinhTB.INTERNET_FTTH ||
            this.loaihinhtb_id == LoaiHinhTB.WIFI_FIBER)) {
        if (this.thamso.nhap_tttc == 1) {
          if (this.$refs.bangRongCoDinh.input_tttc.trim() == "") {
            this.$toast.error("Bạn phải nhập thông tin tính cước !")
            this.$refs.bangRongCoDinh.focusInputTTTC()
            return false
          }
        }
      }
      if ((kieu == "1" || kieu == "2" || kieu == "4" || kieu == "5") && this.dichvu_vt_selected == DichVuVienThong.ADSL &&
                this.$refs.bangRongCoDinh.input_matn.trim() == "") {
        this.$toast.error("Khi kiểu lắp đặt trên đường có sẵn(hoặc lắp kèm), mã truy nhập không được trống!")
        this.$refs.bangRongCoDinh.focusInputMaTN()
        return false
      }
      if ((kieu == "1" || kieu == "2" || kieu == "4" || kieu == "5") && this.dichvu_vt_selected == DichVuVienThong.IMS &&
                this.$refs.ims.input_matn.trim() == "") {
        this.$toast.error("Khi kiểu lắp đặt trên đường có sẵn(hoặc lắp kèm), mã truy nhập không được trống!")
        this.$refs.ims.focusInputMaTN()
        return false
      }
      if (this.$refs.thongTinKhachHang.input_thang_ck.trim() != "" && this.$refs.thongTinKhachHang.input_thang_ck.trim() != "0") {
        if (Number(this.$refs.thongTinKhachHang.input_thang_ck.trim()) < 0 || Number(this.$refs.thongTinKhachHang.input_thang_ck.trim()) > 36) {
          this.$toast.error("Số tháng cam kết phải >=0 và <=36!")
          this.$refs.thongTinKhachHang.focusInputSoThangCK()
          return false
        }
      }

      if (this.dichvu_vt_selected == DichVuVienThong.ADSL && (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
                this.loaitb_id == LoaiHinhTB.INTERNET_MYTV)) {
        if (this.thamso.CHUYENDOI_LH_DUNGTHU == -1) {
          if ((this.$refs.bangRongCoDinh.doituong_selected != null && this.$refs.bangRongCoDinh.doituong_selected == DUNG_THU.DOITUONG_TB) ||
                        (this.$refs.bangRongCoDinh.doituong_moi_selected != null && this.$refs.bangRongCoDinh.doituong_moi_selected == DUNG_THU.DOITUONG_TB)) {
            this.$toast.error("Không thể lập hợp đồng với thuê bao thuộc đối tượng \"Thuê bao dùng thử\"!")
            return false
          }
        } else {
          if (this.$refs.bangRongCoDinh.doituong_moi_selected != null && this.$refs.bangRongCoDinh.doituong_moi_selected == DUNG_THU.DOITUONG_TB) {
            // Chỉ chuyển Mega -> Fiber được phép chọn dùng thử
            if (this.kieuld_id != KieuLapDat.TDLH_MEGA_FTTH) {
              this.$toast.error("Chỉ được chọn đối tượng \"Thuê bao dùng thử\" khi lập hợp đồng chuyển đổi Mega - Fiber!")
              return false
            }
          }
        }
      }
      // bắt buộc trọn trả trước khi thực hiện lắp mới hyn - mail
      if (this.thamso.dk_tratruoc == 1 && this.$root.token.getNguoiDungID() != this.thamso.nguoidung_boqua_tratruoc) {
        if (this.dichvu_vt_selected == DichVuVienThong.ADSL && this.loaihinhtbcu_id != LoaiHinhTB.INTERNET_MYTV) {
          this.$toast.error("Bạn phải thực hiện đăng ký trả trước cho thuê bao!")
          // chkTraTruoc.Focus();
          return false
        }
      }

      if (this.dichvu_vt_selected == DichVuVienThong.ADSL && this.loaihinhtb_id == LoaiHinhTB.INTERNET_MYTV) {
        if (this.$refs.bangRongCoDinh.tocdo_selected != null && this.$refs.bangRongCoDinh.tocdo_moi_selected != null &&
                this.$refs.bangRongCoDinh.tocdo_selected != this.$refs.bangRongCoDinh.tocdo_moi_selected) {
          this.$toast.error("Chuyển đổi loại hình MyTV đồng quang không được đổi tốc độ thuê bao.")
          return false
        }
      }

      return true
    },
    KiemTraDL_KhuyenMai () {
      if (this.dsKM_HDTB.length == 0 && this.$refs.thongTinPhieuYeuCau.khuyenmai.checkKhuyenMai) {
        this.$toast.error("Hãy chọn chi tiết khuyến mại !")
        this.$refs.thongTinPhieuYeuCau.focusKhuyenMai()
        return false
      }
      return true
    },
    // dd/MM/yyyy
    KiemTraDK_LHD (thangnam_tt, thangnam_sys) {
      console.log("thangnam_tt", thangnam_tt)
      console.log("thangnam_sys", thangnam_sys)
      let m_thangnam_tt = moment(thangnam_tt, "DD/MM/YYYY")
      let m_thangnam_sys = moment(thangnam_sys, "DD/MM/YYYY")
      if (Number(m_thangnam_tt.format("YYYY")) < Number(m_thangnam_sys.format("YYYY"))) {
        return true
      } else if (Number(m_thangnam_tt.format("YYYY")) == Number(m_thangnam_sys.format("YYYY"))) {
        if (Number(m_thangnam_tt.format("M")) < Number(m_thangnam_sys.format("M"))) {
          return true
        } else if (Number(m_thangnam_tt.format("M")) == Number(m_thangnam_sys.format("M"))) {
          if (Number(m_thangnam_tt.format("D")) <= Number(m_thangnam_sys.format("D"))) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      } else {
        return false
      }
    },
    async themTB () {
      if (this.dataSources.length > 0) {
        for (let i = 0; i < this.dataSources.length; i++) {
          if (this.dataSources[i].DICHVUVT_ID == DichVuVienThong.DI_DONG) {
            if (this.dichvu_vt_selected != DichVuVienThong.DI_DONG) {
              this.$toast.error("Hợp đồng chuyển đổi loại hình chỉ được phép bao gồm các thuê bao di động !")
              return
            }
          } else {
            if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
              this.$toast.error("Bạn không được phép chuyển đổi loại hình thuê bao di động trên cùng hợp đồng với thuê bao khác !")
              return
            }
          }
        }
      }
      //
      await this.ThemHDThueBao()
      // nạp lại ds
    },
    async ThemHDThueBao () {
      var async_status = false
      if (!this.KiemTraDL_ThueBao()) {
        return
      }
      var matb = ""
      var tocdo_id = 0
      var tocdotn_id = -1
      var muccuoctn_id = 0
      var vtd_id = -1
      var vmc_id = 0
      var loaiso = 0
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        matb = this.$refs.coDinh.input_matb.trim()
        loaiso = this.$refs.coDinh.loai_so_moi_selected != null ? this.$refs.coDinh.loai_so_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        matb = this.$refs.bangRongCoDinh.input_matb.trim()
        tocdo_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? this.$refs.bangRongCoDinh.tocdo_moi_selected : 0
        tocdotn_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? this.$refs.bangRongCoDinh.tocdo_moi_selected : 0
        if (this.loaihinhtb_id != LoaiHinhTB.INTERNET_MYTV) {
          muccuoctn_id = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? this.$refs.bangRongCoDinh.muccuoc_moi_selected : 0
        }
        vtd_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? this.$refs.bangRongCoDinh.tocdo_moi_selected : 0
        vmc_id = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? this.$refs.bangRongCoDinh.muccuoc_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        matb = this.$refs.ims.input_matb.trim()
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        matb = this.$refs.kenhThuRieng.input_matb.trim()
        tocdotn_id = this.$refs.kenhThuRieng.tocdo_moi_selected != null ? this.$refs.kenhThuRieng.tocdo_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        matb = this.$refs.megawan.input_matb.trim()
        tocdotn_id = this.$refs.megawan.tocdo_cir_moi_selected != null ? this.$refs.megawan.tocdo_cir_moi_selected : 0
      }
      var id_sochinh = 0
      if (this.loaisochinh == 1) {
        id_sochinh = this.thuebao_id_cha
      } else {
        id_sochinh = this.hdtb_id_cha
      }

      let ds_para = {
        THUEBAO_ID: this.thuebao_id,
        MA_TB: matb,
        MA_GD: this.$refs.thongTinPhieuYeuCau.input_magd.trim(),
        KHACHHANG_ID: this.khachhang_id,
        HDKH_ID: this.hdkh_id,
        DICHVUVT_ID: this.dichvuvt_id,
        LOAITB_MOI_ID: this.loaihinhtb_id != null ? Number(this.loaihinhtb_id) : 0,
        LOAITB_CU_ID: this.loaihinhtbcu_id,
        KIEULD_ID: this.kieuld_id,
        TOCDO_ID: tocdo_id,
        TOCDOTN_ID: tocdotn_id,
        MUCCUOC_ID: muccuoctn_id,
        QUANLD_ID: this.dataDiaChiLD.quan_id,
        PHUONGLD_ID: this.dataDiaChiLD.phuong_id,
        PHOLD_ID: this.dataDiaChiLD.pho_id,
        APLD_ID: this.dataDiaChiLD.ap_id,
        KHULD_ID: this.dataDiaChiLD.khu_id,
        DACDIEMLD_ID: this.dataDiaChiLD.dacdiem_id,
        VTD_ID: vtd_id,
        VMC_ID: vmc_id,
        MUCCUOCTB_ENABLED: this.$refs.thongTinPhieuYeuCau.disableMucCuocTB ? 0 : 1,
        LOAISO: loaiso,
        ID_SOCHINH: id_sochinh,
        LOAISOCHINH: this.loaisochinh
      }
      console.log("ThemHDThueBao ds_para", ds_para)
      this.loading(true)
      let checkResult = await this.fn_ktdl_themtb_cdlh(JSON.stringify(ds_para))
      this.loading(false)

      if (checkResult != null && checkResult.ERROR_CODE == 1) {
        // thành công
        let huonggiao_tn = checkResult.RESULT.HUONGGIAO_ID
        this.luong_id = checkResult.RESULT.LUONG_ID
        this.quytrinh_id = checkResult.RESULT.QUYTRINH_ID

        this.loading(true)
        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_HDTBDV()
        // Hàm thừa
        await this.TaoDuLieu_THONGTIN_TT(true)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_DiaChi(true)

        this.dsHDTB_CD = []
        this.dsHDTB_ADSL = []
        this.dsHDTB_GP = []
        this.dsHDTB_MEWAN = []
        this.dsHDTB_TSL = []
        this.dsHDTB_IMS = []

        // Tạo dữ liệu cho chi tiết khuyến mại.
        await this.CapNhat_CTKM(this.hdtb_id)

        for (let i = 0; i < this.dsCTM_TBI.length; i++) {
          this.dsCTM_TBI[i].HDTB_ID = this.hdtb_id
        }

        var ma_tb = ""

        if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
          await this.TaoDuLieu_HDTB_CD(true)
          ma_tb = this.$refs.coDinh.input_matb.trim()
        } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
          // Không có cho di động, bỏ qua

        } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
          await this.TaoDuLieu_HDTB_ADSL(true)
          ma_tb = this.$refs.bangRongCoDinh.input_matb.trim()
        } else if (this.dichvu_vt_selected == DichVuVienThong.GPHONE) {

        } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
          await this.TaoDuLieu_HDTB_MGWAN(true)
          ma_tb = this.$refs.megawan.input_matb.trim()
        } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
          await this.TaoDuLieu_HDTB_TSL(true)
          ma_tb = this.$refs.kenhThuRieng.input_matb.trim()
        } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
          await this.TaoDuLieu_HDTB_IMS(true)
          ma_tb = this.$refs.ims.input_matb.trim()
        }
        this.loading(false)
        // Tạo dữ liệu
        let vds = {
          THUEBAO_ID: this.thuebao_id,
          MA_TB: ma_tb,
          MA_GD: this.$refs.thongTinPhieuYeuCau.input_magd.trim(),
          DICHVUVT_ID: this.dichvu_vt_selected,
          HUONGGIAO_TN: huonggiao_tn
        }

        let vtaodulieu = {
          HD_THUEBAO: this.dsHDTB,
          HDTB_DV: this.dsHDTBDV,
          CT_TIENHD: this.dsCTTHD,
          DIACHI_HDTB: this.dsDiaChiHDTB,
          DIACHI: this.dsDiaChi,
          KHUYENMAI_HDTB: this.dsKM_HDTB,
          DATCOC_HDTB: this.dsDC_HDTB,
          CT_MUA_TBI: this.dsCTM_TBI,
          TIEN_HDTB_TC: this.dsTIEN_HDTB_TC,
          TIEN_HDTB: this.dsTIEN_HDTB_TC,
          HDTB_CD: this.dsHDTB_CD,
          HDTB_IMS: this.dsHDTB_IMS,
          HDTB_ADSL: this.dsHDTB_ADSL,
          HDTB_GP: this.dsHDTB_GP,
          HDTB_MGWAN: this.dsHDTB_MEWAN,
          HDTB_TSL: this.dsHDTB_TSL,
          THONGTIN_TT: [],
          HDTB_LOAIIP: []
        }

        let data = {
          vds: JSON.stringify(vds),
          vtaodulieu: JSON.stringify(vtaodulieu)
        }
        console.log("TaoDuLieu", data)
        // thêm thuê bao cdlh
        // fn_themtb_cdlh
        this.loading(true)
        let result = await this.fn_themtb_cdlh(data)
        this.loading(false)
        if (result.code == 0) {
          this.$toast.error(result.message)
          return
        }
        this.$toast.success(result.message)
        this.loading(true)
        await this.Them_MyTV_vao_goi_giadinh()
        this.loading(false)
        // Hiển thị lại thông tin sau khi tạo thuê bao
        this.loading(true)
        let ma_gd = await this.$refs.thongTinPhieuYeuCau.getMaGD()
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
      } else if (checkResult != null && checkResult.ERROR_CODE == 0) {
        // show message
        this.$toast.error(checkResult.MESSAGE)
      }
      console.log("ThemHDThueBao result", checkResult)
    },
    async xoaHDTB () {
      if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
        // Không tìm thấy biến gán this.ma_gd_neo nên tạm thời bỏ qua khúc này
        if (this.ma_gd_neo != null && this.ma_gd_neo != "" && this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
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
      let result = await this.xoa_hopdong_thuebao(this.hdtb_id)
      if (result.code == 1) {
        this.$toast.success(result.message)
        this.loading(true)
        let ma_gd = await this.$refs.thongTinPhieuYeuCau.getMaGD()
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
      } else {
        this.$toast.error(result.message)
      }
    },
    async xoaHDKH () {
      //
      for (let i = 0; i < this.dataSources.length; i++) {
        let item = this.dataSources[i]
        if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
          if (item.DICHVUVT_ID == DichVuVienThong.DI_DONG) {
            // Sau này xử lý với dịch vụ di dộng

          }
        } else {
          await this.xoa_hopdong_thuebao(this.hdtb_id)
        }
      }
      // Xoá hợp đồng
      let result = await this.xoa_hdkh_v2(this.hdkh_id, LoaiHopDong.CHUYENDOI_LH)
      if (result.code == 0) {
        this.$toast.error(result.message)
        return
      }
      this.$toast.success(result.message)
      this.dataSources = []
      this.setActiveActions(0)
    },
    async onSelectedRow (item) {
      this.rowSelected = item
      console.log("rowSelected", item)
      await this.HienThiTTHopDongTB(item)
    },
    changeCheckCTV () {
      if (this.checkCTV) {
        this.$refs.chonCongTacVienModal.showModal()
      }
    },
    checkCheckNguoiGT () {
      if (this.checkNguoiGT) {
        this.$refs.chonNguoiGTModal.showModal()
      }
    },
    onAcceptKhuVuc (khuvuc) {
      this.khuvuc_id = khuvuc.khuvuc_id
    },
    acceptChonSoChinh (data) {
      this.loaisochinh = data.LOAI
      if (this.loaisochinh == 1) {
        this.thuebao_id_cha = data.ID_SOCHINH
      } else {
        this.hdtb_id_cha = data.ID_SOCHINH
      }
    },
    clickMaTN_IMS () {
      let kieuld_id = this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
      let kieu_ld = this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x => x.kieuld_id == kieuld_id)
      console.log("clickMaTN_IMS", kieu_ld)
      if (!kieu_ld) {
        return
      }

      if (kieu_ld.kieu == "2") {
        // Lắp mới trên đường có sẵn
        if (this.khachhang_id > 0) {
          var dulieu = {}
          dulieu.khachhang_id = this.khachhang_id
          dulieu.kieuld_id = kieuld_id
          dulieu.loaitb_id = this.$refs.ims.loaihinh_tb_moi_selected != null ? this.$refs.ims.loaihinh_tb_moi_selected : 0
          dulieu.dichvuvt_id = this.dichvu_vt_selected
          this.$refs.ims.showDSTBCungDoiCap(dulieu)
        } else {
          this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !")
        }
      } else if (kieu_ld.kieu == "1") {
        // Lắp kèm
        if (this.hdkh_id > 0) {
          var dulieu = {}
          dulieu.hdkh_id = this.hdkh_id
          dulieu.vkhachhang_id = this.khachhang_id
          dulieu.vkieuld_id = kieuld_id

          this.$refs.ims.showDSTBLapKem(dulieu)
        } else {
          // Kiểm tra xem có thuê bao mega đang thực hiện chuyển đổi không
          this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !")
        }
      }
    },
    async clickTTTC_ADSL () {
      if (this.$refs.thongTinPhieuYeuCau.ds_muccuoc_tb.length == 0 || this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected == null) {
        this.$toast.error("Chưa có mức cước thuê bao. Bạn hãy kiểm tra lại!")
        return
      }
      //
      this.loading(true)
      var cuoc_tb = await service.fn_tt_muccuoc_tb(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected, 1)
      var cuockhoan = await service.fn_tt_muccuoc_tb(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected, 2)
      var cuoc_tg = await service.fn_tt_muccuoc_tb(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected, 3)
      var cuoc_ll = await service.fn_tt_muccuoc_tb(this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected, 4)
      if (cuoc_tb == "-1") {
        cuoc_tb = "0"
      }
      if (cuockhoan == "-1") {
        cuockhoan = "0"
      }
      if (cuoc_tg == "-1") {
        cuoc_tg = "0"
      }
      if (cuoc_ll == "-1") {
        cuoc_ll = "0"
      }
      this.loading(false)
      // tạo dữ liệu
      let dulieu = {
        cuoc_tb: cuoc_tb,
        cuoc_tg: cuoc_tg,
        cuockhoan: cuockhoan,
        cuoc_ll: cuoc_ll,
        muccuoctb_id: this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected != null ? this.$refs.thongTinPhieuYeuCau.muoccuoc_tb_selected : 0,
        mienip: null,
        loaitb_id: this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected,
        thongtintc: this.$refs.bangRongCoDinh.input_tttc
      }
      console.log("clickTTTC_ADSL", dulieu)
      this.$refs.bangRongCoDinh.showThongTinTC(dulieu)
    },
    clickMaTN_ADSL () {
      let kieuld_id = this.$refs.thongTinPhieuYeuCau.kieu_yc_selected
      let kieu_ld = this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x => x.kieuld_id == kieuld_id)
      console.log("clickMaTN_IMS", kieu_ld)
      if (!kieu_ld) {
        return
      }
      //
      if (kieu_ld.kieu == "1" || kieu_ld.kieu == "5") {
        if (this.hdkh_id > 0) {
          var dulieu = {}
          dulieu.hdkh_id = this.hdkh_id
          dulieu.vkhachhang_id = this.khachhang_id
          dulieu.vkieuld_id = kieuld_id

          this.$refs.bangRongCoDinh.showDSTBLapKem(dulieu)
        } else {
          if (kieu_ld.kieu == "1") {
            this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !")
            return
          }
          if (kieu_ld.kieu == "5") {
            var dulieu = {}
            dulieu.hdkh_id = this.hdkh_id
            dulieu.vkhachhang_id = this.khachhang_id
            dulieu.vkieuld_id = kieuld_id
            dulieu.kieu = 5
            this.$refs.bangRongCoDinh.showDSTBLapKem(dulieu)
          }
        }
      } else if (kieu_ld.kieu == "2") {
        // Lắp mới trên đường có sẵn
        if (this.khachhang_id > 0) {
          var dulieu = {}
          dulieu.khachhang_id = this.khachhang_id
          dulieu.kieuld_id = kieuld_id
          dulieu.loaitb_id = this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected != null ? this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected : 0
          dulieu.dichvuvt_id = this.dichvu_vt_selected
          this.$refs.bangRongCoDinh.showDSTBCungDoiCap(dulieu)
        } else {
          this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !")
        }
      }
    },
    async changeCheckKhuyenMai (data) {
      this.dtienkmld = 0
      this.dvatkmld = 0
      if (data.isChecked) {
        if (data.lenght_ds_khuyenmai > 0) {
          if (data.khuyenmai_selected != null && data.khuyenmai_selected != 0) {

          } else {
            this.dtienkmld = 0
            this.dvatkmld = 0
            this.tyle_kmld = 0
          }
        } else {
          this.dtienkmld = 0
          this.dvatkmld = 0
          this.tyle_kmld = 0
        }
      } else {
        this.dtienkmld = 0
        this.dvatkmld = 0
        this.tyle_kmld = 0
        this.dsKM_HDTB = []
      }
      await this.LaySothang_SD()

      await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
    },
    async changeCheckTraTruoc (data) {
      if (data.isChecked) {
        if (data.lenght_ds_tratruoc == 0) {
          this.dtienkmdc = 0
          this.dvatkmdc = 0
          this.tyle_kmld_tt = 0
          this.dtiendatcoc_tt = 0
          this.dvatdatcoc_tt = 0
        }
      } else {
        this.dtienkmdc = 0
        this.dvatkmdc = 0
        this.tyle_kmld_tt = 0
        this.dtiendatcoc_tt = 0
        this.dvatdatcoc_tt = 0
        this.dsDC_HDTB = []
      }
      await this.LaySothang_SD()
      await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
      let kieuld_id = await this.$refs.thongTinPhieuYeuCau.getKieuID()
      await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
    },
    async acceptPopupCTMuaThietBi (ds) {
      this.dsCTM_TBI = []
      if (ds.length <= 0) {
        return
      }
      for (let i = 0; i < ds.length; i++) {
        let item = ds[i]
        this.dsCTM_TBI.push({
          LOAITBI_ID: item.loaitbi_id,
          SOLUONG: item.soluong,
          TIEN: item.tien,
          VAT: item.vat,
          TIEN_KM: item.tien_km,
          VAT_KM: item.vat_km,
          TIEN_TRATRUOC: item.tien_tratruoc,
          VAT_TRATRUOC: item.vat_tratruoc,
          TIEN_TRAGOP: item.tien_tragop,
          VAT_TRAGOP: item.vat_tragop,
          SERIAL: item.serial,
          TYLE_VAT: item.tyle_vat,
          TYLE_VAT_ID: item.tyle_vat_id,
          SL_CHA: item.sl_cha,
          BLOCK_TIEP: item.block_tiep,
          GIA_BLOCK_TIEP: item.gia_block_tiep,
          VAT_BLOCK_TIEP: item.vat_block_tiep,
          BLOCK_DAU: item.block_dau,
          TIEN_THUE: item.tien_thue,
          TONG_TIEN: item.tong_tien,
          TONG_THUE: item.tong_thue,
          MA_TBI: item.ma_tbi,
          LOAI_TBI: item.loai_tbi
        })
      }
      console.log("acceptPopupCTMuaThietBi", this.dsCTM_TBI)
      await this.HT_ThietBi()
    },
    async btnChiTietKMClick () {
      var vmuccuoctn_id = 0
      var vtocdotn_id = 0
      var dulieu = {}
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        vtocdotn_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? this.$refs.bangRongCoDinh.tocdo_moi_selected : 0
        if (this.loaihinhtb_id == LoaiHinhTB.INTERNET_ADSL || this.loaihinhtb_id == LoaiHinhTB.INTERNET_FTTH || this.loaihinhtb_id == LoaiHinhTB.WIFI_FIBER) {
          vmuccuoctn_id = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? this.$refs.bangRongCoDinh.muccuoc_moi_selected : 0
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        // tocdo_cir_moi_selected là cboTocDo
        vtocdotn_id = this.$refs.megawan.tocdo_cir_moi_selected != null ? this.$refs.megawan.tocdo_cir_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        vtocdotn_id = this.kenhThuRieng.tocdo_moi_selected != null ? this.kenhThuRieng.tocdo_moi_selected : 0
      }

      dulieu.vmuccuoctn_id = vmuccuoctn_id
      dulieu.vtocdotn_id = vtocdotn_id

      if (this.$refs.thongTinPhieuYeuCau.khuyenmai.checkKhuyenMai) {
        if (this.$refs.thongTinPhieuYeuCau.khuyenmai.ds_khuyenmai.length > 0) {
          // kiemtra_thoihan_khuyenmai
          let khuyenmai_id = this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected != 0 ? this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected : 0
          let result = await this.kiemtra_thoihan_khuyenmai(khuyenmai_id, moment(new Date()).format("DD/MM/YYYY"))
          if (result != null && result.result == 0) {
            let ngay_kt_km = await service.fn_tt_khuyenmai(khuyenmai_id, 1)

            let khuyenmai = this.$refs.thongTinPhieuYeuCau.khuyenmai.ds_khuyenmai.find(x => x.khuyenmai_id == khuyenmai_id)
            let tenKm = khuyenmai != undefined ? khuyenmai.ten_km : ""
            let message = "Khuyến mại " + tenKm + " đã hết thời hạn từ ngày " + ngay_kt_km + ".\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?"

            let resultConfirm = await this.confirm(message, "Thông báo")
            if (resultConfirm == 0) {
              this.dtienkmld = 0
              this.dvatkmld = 0
              this.tyle_kmld = 0
              this.tong_kmld = 0
              this.tien_datcoc = 0
              await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
              return
            }
          }
          // show dialog
          await this.$refs.thongTinPhieuYeuCau.openDialogChiTietDKKhuyenMai(dulieu)
        }
      } else {
        dtienkmld = 0
        dvatkmld = 0
        tyle_kmld = 0
        tong_kmld = 0
        tien_datcoc = 0
      }
    },
    async btnChiTietTCClick () {
      var vmuccuoctn_id = 0
      var vtocdotn_id = 0
      var dulieu = {}
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        vtocdotn_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? this.$refs.bangRongCoDinh.tocdo_moi_selected : 0
        if (this.loaihinhtb_id == LoaiHinhTB.INTERNET_ADSL || this.loaihinhtb_id == LoaiHinhTB.INTERNET_FTTH || this.loaihinhtb_id == LoaiHinhTB.WIFI_FIBER) {
          vmuccuoctn_id = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? this.$refs.bangRongCoDinh.muccuoc_moi_selected : 0
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        // tocdo_cir_moi_selected là cboTocDo
        vtocdotn_id = this.$refs.megawan.tocdo_cir_moi_selected != null ? this.$refs.megawan.tocdo_cir_moi_selected : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        vtocdotn_id = this.kenhThuRieng.tocdo_moi_selected != null ? this.kenhThuRieng.tocdo_moi_selected : 0
      }

      dulieu.vmuccuoctn_id = vmuccuoctn_id
      dulieu.vtocdotn_id = vtocdotn_id
      //
      if (this.$refs.thongTinPhieuYeuCau.tratruoc.checkTraTruoc) {
        if (this.$refs.thongTinPhieuYeuCau.tratruoc.ds_tratruoc.length > 0) {
          let khuyenmai_id_tt = this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected != 0 ? this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected : 0
          let result = await this.kiemtra_thoihan_khuyenmai(khuyenmai_id_tt, moment(new Date()).format("DD/MM/YYYY"))
          if (result != null && result.result == 0) {
            let ngay_kt_km = await service.fn_tt_khuyenmai(khuyenmai_id_tt, 1)
            let tratruoc = this.$refs.thongTinPhieuYeuCau.tratruoc.ds_tratruoc.find(x => x.khuyenmai_id == khuyenmai_id_tt)
            let tenKm = tratruoc != undefined ? tratruoc.ten_km : ""
            let message = "Khuyến mại " + tenKm + " đã hết thời hạn từ ngày " + ngay_kt_km + ".\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?"

            let resultConfirm = await this.confirm(message, "Thông báo")
            if (resultConfirm == 0) {
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
          // show dialog
          await this.$refs.thongTinPhieuYeuCau.openDialogChiTietTraTruoc(dulieu)
        } else {
          let kieuld_id = await this.$refs.thongTinPhieuYeuCau.getKieuID()
          await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
        }
      } else {
        this.tyle_kmld_tt = 0
        this.tong_kmld_tt = 0
        this.dtiendatcoc_tt = 0
        this.dvatdatcoc_tt = 0
        this.dtienkmdc = 0
        this.dvatkmdc = 0

        let kieuld_id = await this.$refs.thongTinPhieuYeuCau.getKieuID()
        await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
      }
    },
    async formCloseChiTietKM (data) {
      console.log("formCloseChiTietKM", data)
      if (data.loai == 1) {
        // khuyến mại
        await this.LaySothang_SD()
        this.dsKM_HDTB = data.ds.dsKM_HDTB
        this.dtienkmld = data.ds.tien_kmld
        this.dvatkmld = data.ds.vat_kmld
        this.tyle_kmld = data.ds.tyle_kmld
        this.tien_datcoc = data.ds.tien_datcoc

        // Kiểm tra xem nếu không có tiền khuyến mại thì ưu tiên khuyến mại theo tỷ lệ
        if (this.dtienkmld == 0) {
          if (this.tyle_kmld > 0) {
            this.dtienkmld = Math.round(this.dtienld * this.tyle_kmld / 100)
            this.dvatkmld = (this.dtienld + this.dvatld) * this.tyle_kmld / 100 - this.dtienkmld
          }
        }
        this.tong_kmld = this.dtienkmld + this.dvatkmld
        // Hiếu bổ sung hàm kiểm tra xem user đã chọn chi tiết khuyến mại nào chưa - 23.09.2010
        let khuyenmai_id = this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected != 0 ? this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected : 0
        if (this.dsKM_HDTB.length > 0) {
          const index = this.dsKM_HDTB.findIndex(x => x.KHUYENMAI_ID == khuyenmai_id)
          if (index > -1) {
            // Lấy tiền khuyến mại theo khuyến mại id
            await this.HT_ThietBi()
          }
        } else {
          this.dtienkmld = 0
          this.dvatkmld = 0
          this.tyle_kmld = 0
          this.tong_kmld = 0
          this.tien_datcoc = 0
        }
        await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
      } else if (data.loai == 2) {
        // trả trước
        await this.LaySothang_SD()

        this.dsDC_HDTB = data.ds.dsDC_HDTB
        this.dtienkmdc = data.ds.tien_kmld_tt
        this.dvatkmdc = data.ds.vat_kmld_tt
        this.tyle_kmld_tt = data.ds.tyle_kmld_tt
        this.dtiendatcoc_tt = Math.round(data.ds.tien_datcoc_tt * 10 / 11)
        this.dvatdatcoc_tt = data.ds.tien_datcoc_tt - this.dtiendatcoc_tt
        // Kiểm tra xem nếu không có tiền khuyến mại thì ưu tiên khuyến mại theo tỷ lệ
        if (this.dtienkmdc == 0) {
          if (this.tyle_kmld_tt > 0) {
            this.dtienkmdc = Math.round(this.dtienld * this.tyle_kmld_tt / 100)
            this.dvatkmdc = (this.dtienld + this.dvatld) * this.tyle_kmld_tt / 100 - this.dtienkmdc
          }
        }
        this.tong_kmld_tt = this.dtienkmdc + this.dvatkmdc

        if (this.dsDC_HDTB.length == 0) {
          this.tyle_kmld_tt = 0
          this.tong_kmld_tt = 0
          this.dtiendatcoc_tt = 0
          this.dvatdatcoc_tt = 0
          this.dtienkmdc = 0
          this.dvatkmdc = 0
        }
        await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)

        let kieuld_id = await this.$refs.thongTinPhieuYeuCau.getKieuID()
        await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
      }
    },
    changeLoaiSoMoi () {
      this.hdtb_id_cha = 0
      this.thuebao_id_cha = 0
    },
    async changeGoiCuocAdsl (tocdo_id) {
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        this.loading(true)
        // this.$refs.bangRongCoDinh.input_tttc=''
        // Hiển thị tốc độ thực adsl
        let ds_tocdo = await this.lay_tocdo_thuc_adsl_theo_tocdo_id(tocdo_id)
        this.$refs.bangRongCoDinh.ds_tocdo_thuc_moi = ds_tocdo
        this.$refs.bangRongCoDinh.tocdo_thuc_selected = tocdo_id

        var vtrangbi_id = 0
        var vloaitb_id_moi = 0
        if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
          vtrangbi_id = await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
          vloaitb_id_moi = await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
        }
        let ngay_yc = await this.$refs.thongTinPhieuYeuCau.getNgayYC()
        await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)

        await this.HT_DS_KhuyenMai_Combobox()
        await this.LayTienTheoKhoanMuc()
        this.loading(false)
      }
    },
    async changeMucCuoc (muccuoc_id) {
      await this.HT_DS_KhuyenMai_Combobox()
    },
    async changeLoaiTBWanMoi (loaithietbi_id) {
      //
      let kieuld_id = this.$refs.thongTinPhieuYeuCau.kieu_yc_selected != null ? Number(this.$refs.thongTinPhieuYeuCau.kieu_yc_selected) : 0
      if ((this.dichvu_vt_selected == DichVuVienThong.MEGAWAN && kieuld_id == KieuLapDat.CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_CAPQUANG) ||
                (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN && kieuld_id == KieuLapDat.CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_SHDSL) ||
                (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN && kieuld_id == KieuLapDat.CHUYENTU_MEGAWAN_CAPQUANG_SANG_MEGAWAN_ADSL) ||
                (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN && kieuld_id == KieuLapDat.CHUYENTU_MEGAWAN_CAPQUANG_SANG_MEGAWAN_SHDSL) ||
                (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN && kieuld_id == KieuLapDat.CHUYENTU_MEGAWAN_SDHSL_SANG_MEGAWAN_ADSL) ||
                (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN && kieuld_id == KieuLapDat.CHUYENTU_MEGAWAN_SDHSL_SANG_MEGAWAN_CAPQUANG) ||
                (this.dichvu_vt_selected == DichVuVienThong.METRONET)) {
        this.ltb_adsl_id = this.$refs.megawan.loaithietbi_moi_selected != null ? this.$refs.megawan.loaithietbi_moi_selected : 0
        await this.LayTienTheoKhoanMuc()
      }
    },
    async changeTocDoCirWan (tocdo_id) {
      //
      await this.LayTienTheoKhoanMuc()
      await this.HT_DS_KhuyenMai_Combobox()
      if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.TSL || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        await this.HienThi_CTK_CTC()
      }
    },
    async changeTocDoKenhTSLMoi (tocdo_id) {
      await this.HT_DS_KhuyenMai_Combobox()
      if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.HienThi_CTK_CTC()
      }
    },
    async changeTrangBiMoi (trangbi_id) {
      this.trangbi_id = Number(trangbi_id)
      // handle theo từng dịch vụ tương ứng
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        await this.HT_DS_KhuyenMai_Combobox()
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        await this.LayTienTheoKhoanMuc()
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        // băng rộng cố định
        await this.LayTienTheoKhoanMuc()

        var vtrangbi_id = 0
        var vloaitb_id_moi = 0
        if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
          vtrangbi_id = await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
          vloaitb_id_moi = await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
        }
        let ngay_yc = await this.$refs.thongTinPhieuYeuCau.getNgayYC()
        await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {

      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.HT_DS_KhuyenMai_Combobox()
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        await this.LayTienTheoKhoanMuc()
      }
    },

    async changeDoiTuongMoi (doituong_id) {
      this.doituong_id = Number(doituong_id)
      //
      await this.LayTienTheoKhoanMuc()
      //
      var vtrangbi_id = 0
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        vtrangbi_id = await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
      }
      var vloaitb_id_moi = 0
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        vloaitb_id_moi = await this.$refs.coDinh.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {

      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        // băng rộng cố định
        vloaitb_id_moi = await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        vloaitb_id_moi = await this.$refs.ims.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        vloaitb_id_moi = await this.$refs.kenhThuRieng.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        vloaitb_id_moi = await this.$refs.megawan.getLoaiHinhTB_ID_Moi()
      }

      let ngay_yc = await this.$refs.thongTinPhieuYeuCau.getNgayYC()
      await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)

      await this.HT_DS_KhuyenMai_Combobox()
    },
    async LaySothang_SD () {
      console.log("LaySothang_SD")
      let thongTinPhieuYC = this.$refs.thongTinPhieuYeuCau
      if (!thongTinPhieuYC.khuyenmai.checkKhuyenMai && !thongTinPhieuYC.tratruoc.checkTraTruoc) {
        this.sothang_sd = 0
      } else if (!thongTinPhieuYC.khuyenmai.checkKhuyenMai && thongTinPhieuYC.tratruoc.checkTraTruoc) {
        let khuyenmai_id_tt = thongTinPhieuYC.tratruoc.tratruoc_selected != null ? thongTinPhieuYC.tratruoc.tratruoc_selected : 0
        let strStkm = await service.fn_tt_khuyenmai(khuyenmai_id_tt, 2)
        this.sothang_sd = strStkm != "-1" ? Number(strStkm) : 0
      } else if (thongTinPhieuYC.khuyenmai.checkKhuyenMai && !thongTinPhieuYC.tratruoc.checkTraTruoc) {
        let khuyenmai_id = thongTinPhieuYC.khuyenmai.khuyenmai_selected != null ? thongTinPhieuYC.khuyenmai.khuyenmai_selected : 0
        let strStkm = await service.fn_tt_khuyenmai(khuyenmai_id, 2)
        this.sothang_sd = strStkm != "-1" ? Number(strStkm) : 0
      } else if (thongTinPhieuYC.khuyenmai.checkKhuyenMai && thongTinPhieuYC.tratruoc.checkTraTruoc) {
        let khuyenmai_id_tt = thongTinPhieuYC.tratruoc.tratruoc_selected != null ? thongTinPhieuYC.tratruoc.tratruoc_selected : 0
        let strSttt = await service.fn_tt_khuyenmai(khuyenmai_id_tt, 2)
        let st_tt = strSttt != "-1" ? Number(strSttt) : 0

        let khuyenmai_id = thongTinPhieuYC.khuyenmai.khuyenmai_selected != null ? thongTinPhieuYC.khuyenmai.khuyenmai_selected : 0
        let strStkm = await service.fn_tt_khuyenmai(khuyenmai_id, 2)
        let st_km = strStkm != "-1" ? Number(strStkm) : 0
        this.sothang_sd = 0
        if (st_tt > st_km) {
          this.sothang_sd = st_tt
        } else {
          this.sothang_sd = st_km
        }
      }

      this.$refs.thongTinKhachHang.input_thang_ck = this.sothang_sd.toString()
    },
    acceptCTV (data) {
      Object.assign(this.ctv, data)
    },
    acceptNguoiGT (data) {
      Object.assign(this.nguoi_gt, data)
    },
    onAcceptDiaChiLD (data) {
      Object.assign(this.dataDiaChiLD, data)
    },
    onAcceptDiaChiTB (data) {
      Object.assign(this.dataDiaChiTB, data)
    },
    async changeDichVu (dichvuvt_id) {
      this.dichvu_vt_selected = dichvuvt_id
      this.dichvuvt_id = dichvuvt_id
      // handle
      // if (dichvuvt_id == DichVuVienThong.GPHONE)
      //             cboLoaiHinhTBMoi.SelectedValue = LoaiHinhTB.GPHONE_TRATRUOC;
      await this.$refs.thongTinKhachHang.changeDichVu(dichvuvt_id)

      // init layout tương ứng
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        setTimeout(async () => {
          this.loading(true)
          await this.$refs.coDinh.initDuLieu(this.ds_loaihinh_tb)
          await this.$refs.coDinh.changeDichVu(dichvuvt_id)
          await this.$refs.coDinh.visibleToolStripMenuAndControl(this.iKieu == 0)
          this.loading(false)
        }, 500)
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        // init
        this.loading(true)
        await this.$refs.diDong.initDuLieu(this.ds_loaihinh_tb)
        await this.$refs.diDong.changeDichVu(dichvuvt_id)
        this.loading(false)
        // cboLoaiHinhTBMoi.SelectedValue = LoaiHinhTB.DIDONGTRATRUOC;
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        setTimeout(async () => {
          this.loading(true)
          await this.$refs.bangRongCoDinh.initDuLieu(this.ds_loaihinh_tb)
          await this.$refs.bangRongCoDinh.changeDichVu(dichvuvt_id)
          if (this.thamso.nhap_tocdo_thuc_adsl == 0) {
            this.$refs.bangRongCoDinh.enabledCheckTocDoThuc = false
          } else {
            this.$refs.bangRongCoDinh.enabledCheckTocDoThuc = true
          }
          this.$refs.bangRongCoDinh.checkTocDoThuc = false
          await this.$refs.bangRongCoDinh.visibleToolStripMenuAndControl(this.iKieu == 0)
          this.loading(false)
        }, 500)
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        setTimeout(async () => {
          this.loading(true)
          await this.$refs.ims.initDuLieu(this.ds_loaihinh_tb)
          await this.$refs.ims.changeDichVu(dichvuvt_id)
          await this.$refs.ims.visibleToolStripMenuAndControl(this.iKieu == 0)
          this.loading(false)
        }, 500)
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        setTimeout(async () => {
          this.loading(true)
          await this.$refs.kenhThuRieng.initDuLieu(this.ds_loaihinh_tb)
          await this.$refs.kenhThuRieng.changeDichVu(dichvuvt_id)
          await this.$refs.kenhThuRieng.visibleToolStripMenuAndControl(this.iKieu == 0)
          this.loading(false)
        }, 500)
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        setTimeout(async () => {
          this.loading(true)
          await this.$refs.megawan.initDuLieu(this.ds_loaihinh_tb, this.dichvu_vt_selected)
          await this.$refs.megawan.changeDichVu(dichvuvt_id)
          await this.$refs.megawan.visibleToolStripMenuAndControl(this.iKieu == 0)
          this.loading(false)
        }, 500)
      }
    },
    // cboKieuLD_SelectedValueChanged
    async changeKieuYC (kieuyc_id) {
      console.log("changeKieuYC", kieuyc_id)
      this.kieuld_id = Number(kieuyc_id)
      // handle
      let kieu_ld = this.$refs.thongTinPhieuYeuCau.ds_kieu_yc.find(x => x.kieuld_id == kieuyc_id)
      var kieu = ""
      if (kieu_ld != undefined) {
        kieu = kieu_ld.kieu
      }
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        this.$refs.bangRongCoDinh.input_matn = ""

        if (kieu == "1" || kieu == "2" || kieu == "5") {
          this.$refs.bangRongCoDinh.enabledMaTN = true
        } else {
          this.$refs.bangRongCoDinh.enabledMaTN = false
        }
      }

      await this.LayTienTheoKhoanMuc()
      //
      // sp_ht_lhtb_moi_combobox
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL && this.loaihinhtbcu_id == LoaiHinhTB.INTERNET_MYTV) {
        let dsLoaiHinhTBMoi = [
          {
            LOAITB_ID: LoaiHinhTB.INTERNET_MYTV,
            LOAIHINH_TB: "MyTV"
          }
        ]
        await this.$refs.bangRongCoDinh.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, LoaiHinhTB.INTERNET_MYTV)
      } else {
        let resultLoaiHinh = await this.sp_ht_lhtb_moi_combobox(kieuyc_id)
        let dsLoaiHinhTBMoi = resultLoaiHinh.map(x => {
          return {
            LOAITB_ID: Number(x.loaitb_id),
            LOAIHINH_TB: x.loaihinh_tb
          }
        })
        if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
          await this.$refs.coDinh.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length > 0 ? dsLoaiHinhTBMoi[0].LOAITB_ID : null)
        } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
          await this.$refs.diDong.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length > 0 ? dsLoaiHinhTBMoi[0].LOAITB_ID : null)
        } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
          // băng rộng cố định
          await this.$refs.bangRongCoDinh.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length > 0 ? dsLoaiHinhTBMoi[0].LOAITB_ID : null)
        } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
          await this.$refs.ims.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length > 0 ? dsLoaiHinhTBMoi[0].LOAITB_ID : null)
        } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
          await this.$refs.kenhThuRieng.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length > 0 ? dsLoaiHinhTBMoi[0].LOAITB_ID : null)
        } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
          await this.$refs.megawan.setDsLoaiHinhTbMoi(dsLoaiHinhTBMoi, dsLoaiHinhTBMoi.length > 0 ? dsLoaiHinhTBMoi[0].LOAITB_ID : null)
        }
      }
      //
      // Hàm HT_ComboBox_DV để làm gì chưa rõ sau này tính sau
      await this.HT_ComboBox_DV()

      let kieuld_id = await this.$refs.thongTinPhieuYeuCau.getKieuID()
      await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
    },
    async HT_ComboBox_DV () {
      // Hiện tại chưa xử lý, chưa có tác dụng

    },
    async changeMucCuocTB (mucuoctb_id) {
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        let result = await service.fn_tt_muccuoc_tb(mucuoctb_id, 1)
        if (result == "-1") {
          this.$refs.coDinh.input_cuoctb_moi = "0"
        } else {
          this.$refs.coDinh.input_cuoctb_moi = result.toString()
        }
      }
    },
    async changeLoaiHinhTBCu (loaihinhtb_id) {
      this.loaihinhtbcu_id = loaihinhtb_id

      //

      //
      var ds_kieu_yc = []
      if (loaihinhtb_id == LoaiHinhTB.FSECURE) {
        ds_kieu_yc = await this.lay_ds_kieu_ld_lhtb(LoaiHopDong.DAT_MOI, loaihinhtb_id, 0)
      } else {
        ds_kieu_yc = await this.lay_ds_kieu_ld_lhtb(LoaiHopDong.CHUYENDOI_LH, loaihinhtb_id, 0)
      }

      await this.$refs.thongTinPhieuYeuCau.setKieuLD(ds_kieu_yc)
      if (ds_kieu_yc.length > 0) {
        await this.changeKieuYC(ds_kieu_yc[0].kieuld_id)
      } else {
        await this.changeKieuYC(0)
      }
      console.log("changeLoaiHinhTBCu", ds_kieu_yc)

      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        await this.$refs.coDinh.changeLoaiHinhTBCu(loaihinhtb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        await this.$refs.diDong.changeLoaiHinhTBCu(loaihinhtb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        await this.$refs.bangRongCoDinh.changeLoaiHinhTBCu(loaihinhtb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        await this.$refs.ims.changeLoaiHinhTBCu(loaihinhtb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.$refs.kenhThuRieng.changeLoaiHinhTBCu(loaihinhtb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        await this.$refs.megawan.changeLoaiHinhTBCu(loaihinhtb_id)
      }

      if (this.dichvu_vt_selected == DichVuVienThong.ADSL && this.loaihinhtbcu_id == LoaiHinhTB.INTERNET_ADSL) {
        this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected = LoaiHinhTB.INTERNET_FTTH
      }
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL && (this.loaihinhtbcu_id == LoaiHinhTB.INTERNET_FTTH || this.loaihinhtbcu_id == LoaiHinhTB.WIFI_FIBER)) {
        this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected = LoaiHinhTB.INTERNET_ADSL
      }
      // Dịch vụ: An toàn bảo mật thông tin chung layout ADSL
      if (this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT && this.loaihinhtbcu_id == LoaiHinhTB.FSECURE) {
        this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected = LoaiHinhTB.FSECURE
        await this.HienThiControlFSecure(true)
      } else {
        await this.HienThiControlFSecure(false)
      }

      let kieuld_id = await this.$refs.thongTinPhieuYeuCau.getKieuID()
      await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaihinhtbcu_id, kieuld_id)
    },
    // Lam them lay thong tin tien
    async LayTienTheoKhoanMuc () {
      console.log("LayTienTheoKhoanMuc")
      // Hàm lấy tiền lên ô text box ngày 14/09/2010
      if (this.dichvuvt_id != DichVuVienThong.ADSL && this.dichvuvt_id != DichVuVienThong.MEGAWAN && this.dichvuvt_id != DichVuVienThong.METRONET) {
        this.kh_cd = 0
        this.ltb_adsl_id = 1
      }
      let kieuld_id = this.$refs.thongTinPhieuYeuCau.kieu_yc_selected != null ? this.$refs.thongTinPhieuYeuCau.kieu_yc_selected : "0"

      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        if (kieuld_id == KieuLapDat.CHUYENDD_TRASAU_SANG_TRATRUOC.toString()) {
          return
        }
        if (this.loaihinhtb_id == LoaiHinhTB.DIDONGTRATRUOC || this.loaihinhtb_id == LoaiHinhTB.MEGAWAN_ADSL) {
          return
        }
        this.ltb_adsl_id = this.$refs.bangRongCoDinh.loaithietbi_moi_selected != null ? Number(this.$refs.bangRongCoDinh.loaithietbi_moi_selected) : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        if (kieuld_id == KieuLapDat.TDLH_MEGA_FTTH.toString() || kieuld_id == KieuLapDat.CHUYENDD_TRASAU_SANG_TRATRUOC.toString()) {
          return
        }
        this.ltb_adsl_id = this.$refs.megawan.loaithietbi_moi_selected != null ? Number(this.$refs.megawan.loaithietbi_moi_selected) : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.GPHONE) {
        if (kieuld_id == KieuLapDat.TDLH_MEGA_FTTH.toString() || kieuld_id == KieuLapDat.CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_CAPQUANG.toString()) {
          return
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        if (this.loaihinhtb_id == LoaiHinhTB.INTERNET_FTTH || this.loaihinhtb_id == LoaiHinhTB.WIFI_FIBER || this.loaihinhtb_id == LoaiHinhTB.MEGAWAN_ADSL) {
          return
        }
      }
      if (this.loaihinhtb_id == null || this.loaihinhtb_id == 0) {
        return
      }
      var muccuoc = 0
      var iTocdo_id = 0
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        muccuoc = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? Number(this.$refs.bangRongCoDinh.muccuoc_moi_selected) : 0
        iTocdo_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? Number(this.$refs.bangRongCoDinh.tocdo_moi_selected) : 0
      }
      if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        iTocdo_id = this.$refs.megawan.tocdo_cir_moi_selected != null ? Number(this.$refs.megawan.tocdo_cir_moi_selected) : 0
      }
      let ngay_yc = await this.$refs.thongTinPhieuYeuCau.getNgayYC()
      let ds = await this.lay_tien_khoanmuc_ld({
        loaihd_id: LoaiHopDong.CHUYENDOI_LH,
        ngay_yc: ngay_yc,
        dichvuvt_id: this.dichvu_vt_selected,
        loaitb_id: this.loaihinhtb_id,
        kieuld_id: kieuld_id,
        phuongld_id: this.dataDiaChiLD.phuong_id,
        tocdo_id: iTocdo_id,
        muccuoc_id: muccuoc,
        ltb_adsl_id: this.ltb_adsl_id,
        ltb_adsl_moi_id: 0,
        ltb_adsl_cu_id: 0,
        tocdo_moi_id: 0,
        tocdo_cu_id: 0,
        kh_cd: 0,
        phold_id: this.dataDiaChiLD.pho_id,
        apld_id: this.dataDiaChiLD.ap_id,
        khuld_id: this.dataDiaChiLD.khu_id,
        doituong_id: this.doituong_id,
        trangbi_id: this.trangbi_id
      })
      // nếu dịch vụ là internet thì loại hình tb mới pải là ftth & kiểu ld = chuyển từ mega->ftth
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        if (this.loaihinhtb_id == LoaiHinhTB.INTERNET_FTTH || this.loaihinhtb_id == LoaiHinhTB.WIFI_FIBER) {
          if (kieuld_id != KieuLapDat.TDLH_MEGA_FTTH) {
            return
          }
        }
      }
      // Nếu loại hình mới = loại hình cũ -> return
      if (this.loaihinhtbcu_id == Number(this.loaihinhtb_id) && Number(this.loaihinhtb_id) != LoaiHinhTB.INTERNET_MYTV) {
        this.dtienld = 0
        this.dvatld = 0
        return
      }

      if (ds.length > 0) {
        await this.HienThiTienLapDat(ds)
        await this.LayTongTien_HDTB()
      } else {
        this.dtienld = 0
        this.dvatld = 0
      }
    },
    async HienThiTienLapDat (ds) {
      console.log("HienThiTienLapDat")
      var kt_lm = true
      if (ds[0].tien != "-1") {
        for (let i = 0; i < ds.length; i++) {
          if (ds[i].khoanmuctt_id == KHOANMUC_TT.KMTT_TDLOAIHINHTB.toString()) {
            this.dtienld = Number(ds[i].tien)
            this.dvatld = Number(ds[i].vat)
            kt_lm = false
          }
        }
        if (kt_lm) {
          this.dtienld = 0
          this.dvatld = 0
        }
      } else {
        this.dtienld = 0
        this.dvatld = 0
      }
      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_TDLOAIHINHTB.toString()) {
          this.ds_tien_khoanmuc[i].tien = this.dtienld
          this.ds_tien_khoanmuc[i].vat = this.dvatld
        }
      }
      await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.dtiendatcoc_km, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
    },
    async HienThiTienKhuyenMaiLapDat (tien_kmld, vat_kmld, tien_datcoc, dtienkmdc, dvatkmdc, dtiendatcoc_tt) {
      console.log("HienThiTienKhuyenMaiLapDat")
      //
      var dtienkmld_tmp = 0
      var dvatkmld_tmp = 0
      var dtyle_kmld_tmp

      dtienkmld_tmp = this.dtienkmld
      dvatkmld_tmp = this.dvatkmld
      dtyle_kmld_tmp = this.tyle_kmld

      if (Math.abs(this.dtienkmld) < Math.abs(dtienkmdc)) {
        dtienkmld_tmp = dtienkmdc
        dvatkmld_tmp = dvatkmdc
      }
      if (dtyle_kmld_tmp < this.tyle_kmld_tt) {
        dtyle_kmld_tmp = this.tyle_kmld_tt
      }
      if (dtyle_kmld_tmp > 0) {
        dtienkmld_tmp = Math.round(this.dtienld * dtyle_kmld_tmp / 100)
        dvatkmld_tmp = (this.dtienld + this.dvatld) * dtyle_kmld_tmp / 100 - dtienkmld_tmp
      }

      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
          if (this.dtienkmld < 0) {
            this.ds_tien_khoanmuc[i].tien = dtienkmld_tmp * (-1)
            this.ds_tien_khoanmuc[i].vat = dvatkmld_tmp * (-1)
          } else {
            this.ds_tien_khoanmuc[i].tien = dtienkmld_tmp
            this.ds_tien_khoanmuc[i].vat = dvatkmld_tmp
          }
        } else if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_DATCOC.toString()) {
          this.ds_tien_khoanmuc[i].tien = this.dtiendatcoc_tt
          this.ds_tien_khoanmuc[i].vat = this.dvatdatcoc_tt
        } else if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_DATCOC_KM.toString()) {
          this.ds_tien_khoanmuc[i].tien = Math.round(this.dtiendatcoc_km * 10 / 11)
          this.ds_tien_khoanmuc[i].vat = Math.round(this.dtiendatcoc_km / 11)
        }
      }
      //
      await this.LayTongTien_HDTB()
    },
    async HienThiControlFSecure (isFS) {
      this.$refs.thongTinPhieuYeuCau.disableLoaiHinhTB = isFS
      this.$refs.thongTinPhieuYeuCau.disableKieuLD = isFS
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        this.$refs.coDinh.disableLoaiHinhTB = isFS
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {

      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        this.$refs.bangRongCoDinh.disableLoaiHinhTB = isFS
        this.$refs.bangRongCoDinh.disableGoiCuocADSLCu = isFS
        this.$refs.bangRongCoDinh.disableTocDoADSLCuThuc = isFS
        this.$refs.bangRongCoDinh.disableGoiCuocADSLMoi = isFS
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        this.$refs.ims.disableLoaiHinhTB = isFS
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        this.$refs.kenhThuRieng.disableLoaiHinhTB = isFS
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        this.$refs.megawan.disableLoaiHinhTB = isFS
      }
    },
    // cboLoaiHinhTBMoi_SelectedValueChanged
    async changeLoaiHinhTBMoi (loaihinhtb_id) {
      this.loaihinhtb_id = loaihinhtb_id
      // Handle theo từng dịch vụ
      if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        if (loaihinhtb_id == LoaiHinhTB.DIDONGTRASAU) {
          await this.$refs.diDong.setGoiCuocDDMoi(loaihinhtb_id)
        } else {
          // Gọi qua CCBS tạm pedding
          await this.HienThi_GoiCuoc_DDTraTruoc_CoTheChuyenSang(this.$refs.diDong.input_matb.trim())
        }
      }
      // dịch vụ ADSL
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        this.loading(true)
        if (loaihinhtb_id == LoaiHinhTB.INTERNET_FTTH || loaihinhtb_id == LoaiHinhTB.WIFI_FIBER) {
          let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.INTERNET_FTTH)
          let goicuocADSLCu = await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_ADSL)
          await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
          this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.CPE
        } else if (loaihinhtb_id == LoaiHinhTB.INTERNET_ADSL) {
          let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.INTERNET_ADSL)
          let goicuocADSLCu = await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_FTTH)
          await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
          this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.ADSL
        } else if (loaihinhtb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC) {
          let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.INTERNET_ADSL_TINH_KHAC)
          let goicuocADSLCu = await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
          await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
          this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.ADSL
        } else if (loaihinhtb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
          let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
          let goicuocADSLCu = await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.INTERNET_ADSL_TINH_KHAC)
          await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
          this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.ADSL
        } else if (loaihinhtb_id == LoaiHinhTB.FSECURE) {
          let goicuocADSLMoi = await this.sp_lay_tocdo_adsl(LoaiHinhTB.FSECURE)
          let goicuocADSLCu = await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.FSECURE)
          await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi)
          this.$refs.bangRongCoDinh.loaithietbi_moi_selected = 24
        } else if (loaihinhtb_id == LoaiHinhTB.INTERNET_MYTV) {
          let goicuocADSLCu = await this.sp_ht_tocdo_adsl_combobox_byloaitb(LoaiHinhTB.FSECURE)
          await this.$refs.bangRongCoDinh.setGoiCuocADSL(goicuocADSLCu, goicuocADSLCu)
          this.$refs.bangRongCoDinh.loaithietbi_moi_selected = LOAITBI_ADSL.SET_TOP_BOX
        }
        if (this.$refs.bangRongCoDinh.ds_tocdo_moi.length > 0) {
          if (this.$refs.bangRongCoDinh.tocdo_moi_selected != this.$refs.bangRongCoDinh.ds_tocdo_moi[0].tocdo_id) {
            this.$refs.bangRongCoDinh.isChangeGoiCuocADSL = false
            this.$refs.bangRongCoDinh.tocdo_moi_selected = this.$refs.bangRongCoDinh.ds_tocdo_moi[0].tocdo_id
            await this.changeGoiCuocAdsl(this.$refs.bangRongCoDinh.tocdo_moi_selected)
          } else {
            await this.changeGoiCuocAdsl(this.$refs.bangRongCoDinh.tocdo_moi_selected)
          }
        }
        this.loading(false)
      }

      // Megawan
      if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        if (loaihinhtb_id == LoaiHinhTB.MEGAWAN_QUANG_FE || loaihinhtb_id == LoaiHinhTB.METRONET_FE) {
          this.$refs.megawan.loaithietbi_moi_selected = LOAITBI_ADSL.CONG_FE
        } else if (loaihinhtb_id == LoaiHinhTB.MEGAWAN_QUANG_GE || loaihinhtb_id == LoaiHinhTB.METRONET_GE) {
          this.$refs.megawan.loaithietbi_moi_selected = LOAITBI_ADSL.CONG_GE
        } else if (loaihinhtb_id == LoaiHinhTB.MEGAWAN_ADSL) {
          this.$refs.megawan.loaithietbi_moi_selected = LOAITBI_ADSL.ADSL
        } else if (loaihinhtb_id == LoaiHinhTB.MEGAWAN_SHDSL) {
          this.$refs.megawan.loaithietbi_moi_selected = LOAITBI_ADSL.SHDL
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
      if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.TSL || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        await this.HienThi_CTK_CTC()
      }

      // loại hình dịch vụ Cố định
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        if (loaihinhtb_id == LoaiHinhTB.ISDN2B_CD.toString() ||
                    loaihinhtb_id == LoaiHinhTB.ISDN2B_CQ.toString() ||
                    loaihinhtb_id == LoaiHinhTB.ISDN30B_CD.toString() ||
                    loaihinhtb_id == LoaiHinhTB.ISDN30B_CQ.toString() ||
                    loaihinhtb_id == LoaiHinhTB.PABX.toString() ||
                    loaihinhtb_id == LoaiHinhTB.TRUNGKE_2M.toString() ||
                    loaihinhtb_id == LoaiHinhTB.TRUNGKE_THUONG.toString() ||
                    loaihinhtb_id == LoaiHinhTB.TRUNGKE_TUONGTU.toString()) {
          this.$refs.coDinh.inputLoaiSoMoiDisabled = false
          this.$refs.coDinh.loai_so_moi_selected = LOAISO_ISDN.SOCHINH
          this.$refs.coDinh.inputCuocTBMoiDisabled = false
          this.$refs.thongTinPhieuYeuCau.disableMucCuocTB = true
        } else {
          this.$refs.coDinh.inputLoaiSoMoiDisabled = true
          this.$refs.coDinh.enableBtnChonSo = false
          this.$refs.coDinh.input_cuoctb_moi = ""
          this.$refs.coDinh.inputCuocTBMoiDisabled = true
          this.$refs.thongTinPhieuYeuCau.disableMucCuocTB = false
        }
      }

      // Cước TB
      var vtrangbi_id = 0
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        vtrangbi_id = await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
      }
      var vloaitb_id_moi = 0
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        vloaitb_id_moi = await this.$refs.coDinh.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {

      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        // băng rộng cố định
        vloaitb_id_moi = await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        vloaitb_id_moi = await this.$refs.ims.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        vloaitb_id_moi = await this.$refs.kenhThuRieng.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        vloaitb_id_moi = await this.$refs.megawan.getLoaiHinhTB_ID_Moi()
      }

      let ngay_yc = await this.$refs.thongTinPhieuYeuCau.getNgayYC()
      await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)

      await this.HT_DS_KhuyenMai_Combobox()
    },
    async HienThi_GoiCuoc_DDTraTruoc_CoTheChuyenSang (ma_tb) {
      await this.$refs.diDong.HienThi_GoiCuoc_DDTraTruoc_CoTheChuyenSang(ma_tb)
    },
    async HienThi_CTK_CTC () {
      var int_loaitb_id = 0
      var int_tocdo_id = 0
      var int_doituong_id = 0
      var int_loaikenh_id = 0
      int_doituong_id = this.doituong_id
      if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        int_loaitb_id = this.$refs.kenhThuRieng.loaihinh_tb_moi_selected != null ? this.$refs.kenhThuRieng.loaihinh_tb_moi_selected : 0
        int_tocdo_id = this.$refs.kenhThuRieng.tocdo_moi_selected != null ? this.$refs.kenhThuRieng.tocdo_moi_selected : 0
        int_loaikenh_id = this.$refs.kenhThuRieng.loaikenh_moi_selected != null ? this.$refs.kenhThuRieng.loaikenh_moi_selected : 0
      }
      if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        int_loaitb_id = this.$refs.megawan.loaihinh_tb_moi_selected != null ? this.$refs.megawan.loaihinh_tb_moi_selected : 0
        int_tocdo_id = this.$refs.megawan.tocdo_cir_moi_selected != null ? this.$refs.megawan.tocdo_cir_moi_selected : 0
        int_loaikenh_id = this.$refs.megawan.loaikenh_moi_selected != null ? this.$refs.megawan.loaikenh_moi_selected : 0
      }
      let ds_ctk_ctc = await this.sp_lay_cuoctk_cuoctc({
        vloaitb_id: int_loaitb_id,
        vtocdo_id: int_tocdo_id,
        vdoituong_id: int_doituong_id,
        vloaikenh_id: int_loaikenh_id,
        vkieu: 1
      })

      if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        if (ds_ctk_ctc.length > 0) {
          this.$refs.megawan.input_cuoc_tk_moi = ds_ctk_ctc[0].cuoc_tk != null ? ds_ctk_ctc[0].cuoc_tk.toString() : 0
          this.$refs.megawan.input_cuoc_tc_moi = ds_ctk_ctc[0].cuoc_tc != null ? ds_ctk_ctc[0].cuoc_tc.toString() : 0
        } else {
          this.$refs.megawan.input_cuoc_tk_moi = "0"
          this.$refs.megawan.input_cuoc_tc_moi = "0"
        }
      }

      if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        if (ds_ctk_ctc.length > 0) {
          this.$refs.kenhThuRieng.input_cuoc_tk_moi = ds_ctk_ctc[0].cuoc_tk != null ? ds_ctk_ctc[0].cuoc_tk.toString() : 0
          this.$refs.kenhThuRieng.input_cuoc_tc_moi = ds_ctk_ctc[0].cuoc_tc != null ? ds_ctk_ctc[0].cuoc_tc.toString() : 0
        } else {
          this.$refs.kenhThuRieng.input_cuoc_tk_moi = "0"
          this.$refs.kenhThuRieng.input_cuoc_tc_moi = "0"
        }
      }
    },
    async acceptThuBaoCungMa (item) {
      this.loading(true)
      await this.HienThiTT_DanhBaV2(item)
      this.loading(false)
    },
    async acceptTienKM (ds_tien_km) {
      console.log("acceptTienKM", ds_tien_km)
      this.ds_tien_khoanmuc = []
      for (let i = 0; i < ds_tien_km.length; i++) {
        this.ds_tien_khoanmuc.push({
          khoanmuctt_id: ds_tien_km[i].khoanmuctt_id.toString(),
          tien: ds_tien_km[i].tien.toString() != "" ? ds_tien_km[i].tien.toString() : "0",
          vat: ds_tien_km[i].vat.toString() != "" ? ds_tien_km[i].vat.toString() : "0"
        })
      }
      console.log("ds_tien_khoanmuc", this.ds_tien_khoanmuc)
      // Handle
      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        let item = this.ds_tien_khoanmuc[i]
        if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_TDLOAIHINHTB.toString()) {
          this.dtienld = Number(item.tien)
          this.vat_ld = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
          this.dtienkmld = Number(item.tien)
          this.dvatkmld = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_MUATHIETBI.toString()) {
          this.dtientbi = Number(item.tien)
          this.dvattbi = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_KM_THIETBI.toString()) {
          this.dtien_kmtb = Number(item.tien)
          this.dvat_kmtb = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DATCOC.toString()) {
          this.dtiendatcoc_tt = Number(item.tien)
          this.dvatdatcoc_tt = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_KM_DATCOC.toString()) {
          this.dtienkmdc = Number(item.tien)
          this.dvatkmdc = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DATCOC_KM.toString()) {
          this.dtiendatcoc_km = Number(item.tien)
          this.dvatdatcoc_km = Number(item.vat)
        }
        //
        await this.HienThiTienKhuyenMaiLapDat(this.dtienkmld, this.dvatkmld, this.tien_datcoc, this.dtienkmdc, this.dvatkmdc, this.dtiendatcoc_tt)
        await this.LayTongTien_HDTB()
      }
    },
    async onEnterMaTB (ma_tb) {
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
      if (ds.length > 0) {
        await this.HienThiTTHopDongKH(ds)
      } else {
        await this.LoadKM()
        await this.HienThiTT_DanhBa(ma_tb)
      }
      this.loading(false)
    },
    async HienThiTT_DanhBa (ma_tb) {
      let ds = await this.sp_lay_danhba_theo_matb({
        dichvuvt_id: this.dichvuvt_id,
        ma_tb: ma_tb,
        donvi_dl_id: 0
      })
      if (ds.length > 0) {
        if (ds.length > 1) {
          this.dsTBCungMa = ds
          this.$refs.danhSachThueBaoCungMaModal.showModal()
          return
        }
        //
        await this.HienThiTT_DanhBaV2(ds[0])
      } else {
        let message = "Không tìm thấy thông tin về thuê bao " + ma_tb + " đối với dịch vụ " + this.$refs.thongTinPhieuYeuCau.getDichVu()
        this.$toast.error(message)
      }
    },
    async HienThiTT_DanhBaV2 (item) {
      console.log("HienThiTT_DanhBaV2", item)

      // if (WinUICommon.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb.Trim(), "0", Convert.ToInt32(cboDichVuVT.SelectedValue), LoaiHopDong.CHUYENDOI_LH)) return;
      let checkHDKhac = await this.kiemtra_thuebao_lap_hopdong_khac({
        maTb: item.ma_tb,
        maGd: "0",
        dichVuVtId: this.dichvuvt_id,
        loaiHdId: LoaiHopDong.CHUYENDOI_LH
      })
      if (checkHDKhac != null) {
        let message = "Số máy/Acc: " + item.ma_tb + " đang được lập bởi hợp đồng (phụ lục) " + checkHDKhac.ten_loaihd +
                            " có mã giao dịch " + checkHDKhac.ma_gd + " do User " + checkHDKhac.nguoi_cn +
                            " thuộc đơn vị " + checkHDKhac.ten_dv + " thực hiện vào ngày " + checkHDKhac.ngay_yc
        this.$toast.error(message)
        return
      }
      /// ---> Nếu trạng thái thuê bao # " Hoạt động bình thường" ---> không cho phép làm tiếp
      if (item.trangthaitb_id != TrangThaiTB.THUONG) {
        let message = "Thuê bao có mã: " + item.ma_tb + " đang ở trạng thái: " + item.trangthai_tb + " Bạn không thể thực hiện đổi chuyển đổi loại hình thuê bao cho thuê bao này"
        this.$toast.error(message)
        return
      }
      // Kiểm tra khách hàng đã ký hợp đồng gốc hay chưa
      if (item.ky_hd == 0) {
        let message = "Khách hàng có mã thuê bao : " + item.ma_tb + " chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc"
        this.$toast.error(message)
        return
      }
      // Kiểm tra nếu ko pải thuê bao ADSL thì return: hiếu tc ngày 11.10.2010
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL && item.loaitb_id != LoaiHinhTB.INTERNET_ADSL &&
                item.loaitb_id != LoaiHinhTB.INTERNET_FTTH && item.loaitb_id != LoaiHinhTB.WIFI_FIBER &&
                item.loaitb_id != LoaiHinhTB.INTERNET_ADSL_TINH_KHAC && item.loaitb_id != LoaiHinhTB.INTERNET_FIBER_TINH_KHAC &&
                item.loaitb_id != LoaiHinhTB.INTERNET_MYTV) {
        let message = "Thuê bao: " + item.ma_tb + " không phải là Internet. \n Bạn không thể thực hiện chuyển đổi loại hình đối với thuê bao này!"
        this.$toast.error(message)
        return
      }
      //
      // 1551996
      let ds_km = await this.lay_ds_chitiet_km_theo_tbid(item.thuebao_id != null ? item.thuebao_id : 0)
      if (ds_km.length > 0) {
        var message = "Thuê bao " + item.ma_tb + " đang hưởng khuyến mại : \n " + ds_km[0].ten_km +
                            " \n    Có chi tiết khuyến mại : "
        for (let i = 0; i < ds_km.length; i++) {
          message += "\n     - " + ds_km[i].ten_ctkm
        }
        message += "\nBạn có muốn tiếp tục thực hiện?"
        let result = await this.confirm(message, "Thông báo")
        if (result == 0) return
      }

      this.thuebao_id = item.thuebao_id != null ? item.thuebao_id : 0
      this.so_dt = item.so_dt != null ? item.so_dt : ""
      this.vdiachi_tb = item.diachi_tb != null ? item.diachi_tb : ""
      this.loaitb_id = item.loaitb_id != null ? item.loaitb_id : 0
      this.loaihinhtbcu_id = item.loaitb_id != null ? item.loaitb_id : 0
      this.doituong_id = item.doituong_id != null ? item.doituong_id : 0
      this.donviql_id = item.donviql_id != null ? item.donviql_id : 0
      this.khachhang_id = item.khachhang_id != null ? item.khachhang_id : 0
      console.log("khachhang_id", this.khachhang_id)

      await this.$refs.thongTinKhachHang.HienThiTT_DanhBaV2(item)
      await this.$refs.thongTinPhieuYeuCau.HienThiTT_DanhBaV2(item)

      let ds_kv = await this.sp_lay_ds_khuvuc_theo_thuebaoid(this.thuebao_id)
      if (ds_kv.length > 0) {
        this.khuvuc_id = ds_kv[0].khuvuc_id
        Object.assign(item, {
          ten_kv: ds_kv[0].ten_kv != null ? ds_kv[0].ten_kv : ""
        })
      } else {
        this.khuvuc_id = 0
      }

      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        await this.$refs.coDinh.HienThiTT_DanhBaV2(item)
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        //     cboDoiTuongCu.SelectedValue = Convert.ToInt32(ds.Tables[0].Rows[0]["doituong_id"]);
        //     cboDoiTuongMoi.SelectedValue = Convert.ToInt32(ds.Tables[0].Rows[0]["doituong_id"]);

      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        await this.$refs.bangRongCoDinh.HienThiTT_DanhBaV2(item)
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        await this.$refs.ims.HienThiTT_DanhBaV2(item)
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.$refs.kenhThuRieng.HienThiTT_DanhBaV2(item)
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        await this.$refs.megawan.HienThiTT_DanhBaV2(item)
      }

      let dsDiaChi = await this.lay_ds_diachi_theo_dbtbid(this.thuebao_id)
      // upper case key data source
      for (var i = 0; i < dsDiaChi.length; i++) {
        for (var key in dsDiaChi[i]) {
          if (key.toUpperCase() !== key) {
            dsDiaChi[i][key.toUpperCase()] = dsDiaChi[i][key]
            delete dsDiaChi[i][key]
          }
        }
      }
      console.log("dsDiaChi", dsDiaChi)
      await this.HienThiTTDiaChi(dsDiaChi)

      await this.HienThiTTMoRong_DB(this.thuebao_id, this.dichvuvt_id)

      // Hiển thị tiền đặt cọc cũ còn thừa của mega nếu chuyển đổi loại hình mega -> fiber
      let kieuld_id = await this.$refs.thongTinPhieuYeuCau.getKieuID()
      await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaitb_id, kieuld_id)

      // Cước TB
      var vtrangbi_id = 0
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        vtrangbi_id = await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
      }
      var vloaitb_id_moi = 0
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        vloaitb_id_moi = await this.$refs.coDinh.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {

      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        // băng rộng cố định
        vloaitb_id_moi = await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        vloaitb_id_moi = await this.$refs.ims.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        vloaitb_id_moi = await this.$refs.kenhThuRieng.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        vloaitb_id_moi = await this.$refs.megawan.getLoaiHinhTB_ID_Moi()
      }

      let ngay_yc = await this.$refs.thongTinPhieuYeuCau.getNgayYC()
      await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)
    },
    async Lay_DS_MucCuoc_TB_V2 (khuld_id, apld_id, phold_id, phuongld_id, loaitb_id, doituong_id, ngay, trangbi_id) {
      var vtocdo_id = 0
      var vmuccuoc_id = 0 // 2 Lưu lượng

      if (this.loaihinhtbcu_id == LoaiHinhTB.INTERNET_FTTH ||
                this.loaihinhtbcu_id == LoaiHinhTB.WIFI_FIBER ||
                this.loaihinhtbcu_id == LoaiHinhTB.INTERNET_ADSL) {
        // cboGoiCuocAdsl.EditValue: tốc độ mới
        vtocdo_id = Number(await this.$refs.bangRongCoDinh.getGoiCuocADSL())
        vmuccuoc_id = Number(await this.$refs.bangRongCoDinh.getMucCuocMoi())
      }
      if (this.loaihinhtbcu_id == LoaiHinhTB.INTERNET_MYTV) {
        vtocdo_id = Number(await this.$refs.bangRongCoDinh.getGoiCuocADSL())
      }

      let ds = await this.lay_ds_mucuoc_tb_v2({
        khuld_id: khuld_id,
        apld_id: apld_id,
        phold_id: phold_id,
        phuongld_id: phuongld_id,
        loaitb_id: loaitb_id,
        doituong_id: doituong_id,
        tocdo_id: vtocdo_id,
        muccuoc_id: vmuccuoc_id,
        ngay: ngay,
        trangbi_id: trangbi_id
      })

      await this.$refs.thongTinPhieuYeuCau.DS_MucCuoc_TB_V2(ds)
    },
    async HT_Tien_ConLai_DatCoc_Cu (thuebao_id, loaitb_id, kieuld_id) {
      this.dtienthoai_dc = 0
      if (loaitb_id == LoaiHinhTB.MEGA && thuebao_id != 0 && kieuld_id == KieuLapDat.TDLH_MEGA_FTTH) {
        let thang_thoai = Number(moment(new Date()).add(1, "M").format("YYYYMM"))
        let result = await this.lay_ds_tien_dc_conlai(thuebao_id, loaitb_id, thang_thoai)
        if (result.length > 0) {
          this.dtienthoai_dc = result[0].tien_conlai != null ? result[0].tien_conlai : 0
        }
      }
      await this.HT_KM_Tien_Thoai_DC()
    },
    async HT_KM_Tien_Thoai_DC () {
      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_TIENTHOAI_DC.toString()) {
          if ((this.dtiendatcoc_tt * 11 / 10) > this.dtienthoai_dc) {
            this.ds_tien_khoanmuc[i].tien = Math.abs(Math.round(this.dtienthoai_dc * 10 / 11))
            this.ds_tien_khoanmuc[i].vat = Math.abs(Math.round(this.dtienthoai_dc / 11))
          } else {
            this.ds_tien_khoanmuc[i].tien = Math.abs(Math.round(this.dtiendatcoc_tt))
            this.ds_tien_khoanmuc[i].vat = Math.abs(Math.round(this.dvatdatcoc_tt))
          }
        }
      }
      await this.LayTongTien_HDTB()
    },
    async LayTongTien_HDTB () {
      console.log("LayTongTien_HDTB")
      var dtongtien = 0
      var dtongvat = 0
      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i].khoanmuctt_id.toString() == KHOANMUC_TT.KMTT_KM_LAPDAT.toString() ||
                    this.ds_tien_khoanmuc[i].khoanmuctt_id.toString() == KHOANMUC_TT.KMTT_TIENTHOAI_DC.toString() ||
                    this.ds_tien_khoanmuc[i].khoanmuctt_id.toString() == KHOANMUC_TT.KMTT_KM_DATCOC.toString()) {
          if (Number(this.ds_tien_khoanmuc[i].tien) < 0) {
            dtongtien += Number(this.ds_tien_khoanmuc[i].tien)
            dtongvat += Number(this.ds_tien_khoanmuc[i].vat)
          } else {
            dtongtien += Number(this.ds_tien_khoanmuc[i].tien) * -1
            dtongvat += Number(this.ds_tien_khoanmuc[i].vat) * -1
          }
          // }else{
          //     dtongtien+=Number(this.ds_tien_khoanmuc[i].tien)
          //     dtongvat+=Number(this.ds_tien_khoanmuc[i].vat)
          // }
          // comment
        } else if (this.ds_tien_khoanmuc[i].khoanmuctt_id.toString() != KHOANMUC_TT.KMTT_TRAGOP_THIETBI.toString() &&
                    this.ds_tien_khoanmuc[i].khoanmuctt_id.toString() != KHOANMUC_TT.KMTT_KM_THIETBI.toString() &&
                    this.ds_tien_khoanmuc[i].khoanmuctt_id.toString() != KHOANMUC_TT.KMTT_MUATHIETBI.toString()) {
          dtongtien += Number(this.ds_tien_khoanmuc[i].tien)
          dtongvat += Number(this.ds_tien_khoanmuc[i].vat)
        }
      }
      console.log("LayTongTien_HDTB dtongtien", dtongtien)
      console.log("LayTongTien_HDTB dtongvat", dtongvat)
      await this.$refs.chiTietTienTheoKhoanMuc.TongTienTB(dtongtien, dtongvat)
    },
    async LoadKM () {
      this.dtienkmld = 0
      this.dvatkmld = 0

      this.dtientbi = 0
      this.dvattbi = 0

      this.dtien_kmtb = 0
      this.dvat_kmtb = 0

      this.dtienkmdc = 0
      this.dvatkmdc = 0

      this.dtiendatcoc_tt = 0
      this.dvatdatcoc_tt = 0

      this.dtiendatcoc_km = 0
      this.dvatdatcoc_km = 0

      this.ds_tien_khoanmuc = await this.sp_lay_tienkhoanmuc_theo_loaihd(LoaiHopDong.CHUYENDOI_LH)
    },
    async confirm (message, title) {
      try {
        let result = await this.$confirm(message, title, {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Hủy"
        })
        return 1
      } catch (e) {
        return 0
      }
    },
    async ds_thamso_md (kieu) {
      try {
        let response = await ChangeSubsTypeAPI.ds_thamso_md(this.axios, kieu)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_danhmuc_cdlh_tt_chung (chuoi) {
      try {
        let response = await ChangeSubsTypeAPI.lay_danhmuc_cdlh_tt_chung(this.axios, chuoi)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async lay_ds_loaihinh_tb () {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_loaihinh_tb(this.axios)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },

    async sp_lay_tienkhoanmuc_theo_loaihd (loaihd_id) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_tienkhoanmuc_theo_loaihd(this.axios, loaihd_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_khuyenmai_hdtb (data) {
      try {
        let response = await ChangeSubsTypeAPI.lay_khuyenmai_hdtb(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_ds_hopdong_theo_ma_gd (data) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_hopdong_theo_ma_gd(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_ds_hopdong_theo_ma_tb (data) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_hopdong_theo_ma_tb(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async sp_lay_danhba_theo_matb (data) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_danhba_theo_matb(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async kiemtra_thuebao_lap_hopdong_khac (data) {
      try {
        let response = await ChangeSubsTypeAPI.kiemtra_thuebao_lap_hopdong_khac(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async lay_ds_chitiet_km_theo_tbid (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_chitiet_km_theo_tbid(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async sp_lay_ds_khuvuc_theo_thuebaoid (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_ds_khuvuc_theo_thuebaoid(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_ds_diachi_theo_dbtbid (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_diachi_theo_dbtbid(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_ds_isdn_theo_thuebao_id (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_isdn_theo_thuebao_id(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_ds_tien_dc_conlai (thuebao_id, loaitb_id, thang_thoai) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_tien_dc_conlai(this.axios, thuebao_id, loaitb_id, thang_thoai)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    // async fn_map_id (data, defaultValue) {
    //   try {
    //     let response = await ChangeSubsTypeAPI.fn_map_id(this.axios, data)
    //     if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
    //       return response.data.data
    //     } else {
    //       return defaultValue
    //     }
    //   } catch (e) {
    //     return defaultValue
    //   }
    // },
    async fn_lay_tt_ht_hdkh_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_lay_tt_ht_hdkh_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async fn_lay_tt_ht_hdtb_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_lay_tt_ht_hdtb_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async fn_lay_tt_hienthittmorong_db_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_lay_tt_hienthittmorong_db_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async lay_ds_mucuoc_tb_v2 (data) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_mucuoc_tb_v2(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_tien_khoanmuc_ld (data) {
      try {
        let response = await ChangeSubsTypeAPI.lay_tien_khoanmuc_ld(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async fn_lay_tt_hienthittmorong_hd_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_lay_tt_hienthittmorong_hd_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async lay_ds_kieu_ld_lhtb (loaihd_id, loaitb_id, tinhkhac) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_kieu_ld_lhtb(this.axios, loaihd_id, loaitb_id, tinhkhac)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async sp_ht_lhtb_moi_combobox (kieuld_id) {
      try {
        let response = await ChangeSubsTypeAPI.sp_ht_lhtb_moi_combobox(this.axios, kieuld_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async sp_lay_ctkm_loai_tbi (ds_ctkm_id, loaitbi_id) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_ctkm_loai_tbi(this.axios, ds_ctkm_id, loaitbi_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async sp_lay_ds_tragop_thietbi (ds_ctkm_id, loaitbi_id) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_ds_tragop_thietbi(this.axios, ds_ctkm_id, loaitbi_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async kiemtra_thoihan_khuyenmai (khuyenmai_id, ngay_yc) {
      try {
        let response = await ChangeSubsTypeAPI.kiemtra_thoihan_khuyenmai(this.axios, khuyenmai_id, ngay_yc)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async fn_ktdl_themtb_cdlh (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_ktdl_themtb_cdlh(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async fn_kt_va_lay_tt_capnhat_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_kt_va_lay_tt_capnhat_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async fn_tt_taodulieu_hdkh_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_tt_taodulieu_hdkh_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async fn_tt_taodulieu_hdtb_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_tt_taodulieu_hdtb_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async get_keys (keyname) {
      try {
        let response = await ChangeSubsTypeAPI.get_keys(this.axios, keyname)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return Number(response.data.data)
        } else {
          return 0
        }
      } catch (e) {
        return 0
      }
    },
    async sp_ht_danhmuc_dv_tdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.sp_ht_danhmuc_dv_tdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data && response.data.data.p_js_returnds) {
          return JSON.parse(response.data.data.p_js_returnds)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async lay_ds_db_cd_theo_tbid (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_db_cd_theo_tbid(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async ur2_1_035_038_temp (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.ur2_1_035_038_temp(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async sp_lay_donvi_theo_loaidv_diachi_ld_v1 (data) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_donvi_theo_loaidv_diachi_ld_v1(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_ds_danhba_tsl (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_danhba_tsl(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_tt_fs_kem_ftth_v2 (loaihd_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_tt_fs_kem_ftth_v2(this.axios, loaihd_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_ds_db_ims_theo_tbid (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_db_ims_theo_tbid(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async fn_tt_taodulieu_hdtb_adsl_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_tt_taodulieu_hdtb_adsl_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          let result = JSON.parse(response.data.data)
          if (result.ERROR_CODE == 1) {
            return result.RESULT
          } else {
            return null
          }
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async sp_lay_tocdo_adsl (loaitb_id) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_tocdo_adsl(this.axios, loaitb_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async sp_ht_tocdo_adsl_combobox_byloaitb (loaitb_id) {
      try {
        let response = await ChangeSubsTypeAPI.sp_ht_tocdo_adsl_combobox_byloaitb(this.axios, loaitb_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_tocdo_thuc_adsl_theo_tocdo_id (tocdo_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_tocdo_thuc_adsl_theo_tocdo_id(this.axios, tocdo_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async kiemtra_dky_family_dichuyen (hdtb_id, matb_tn) {
      try {
        let response = await ChangeSubsTypeAPI.kiemtra_dky_family_dichuyen(this.axios, hdtb_id, matb_tn)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_goivasc_goi_dadv (goi_id, tocdo_id, muccuoc_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_goivasc_goi_dadv(this.axios, goi_id, tocdo_id, muccuoc_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return -1
        }
      } catch (e) {
        return -1
      }
    },
    async lay_ds_dbtb_theo_tbid (thuebao_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_ds_dbtb_theo_tbid(this.axios, thuebao_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async fn_lay_tt_taodl_hdtb_dkygoi_mytv (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_lay_tt_taodl_hdtb_dkygoi_mytv(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data && response.data.data.p_js_returnds) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async fn_ins_update_hd_cdlh (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_ins_update_hd_cdlh(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          let result = JSON.parse(response.data.data)
          // {\"ERROR_CODE\":1,\"MESSAGE\":\"Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!\",\"RESULT\":{}}
          if (result.ERROR_CODE == 1) {
            return {
              code: 1,
              message: result.MESSAGE
            }
          } else {
            return {
              code: 0,
              message: result.MESSAGE
            }
          }
        } else {
          if (response && response.data && response.data.message) {
            return {
              code: 0,
              message: response.data.message
            }
          } else {
            return {
              code: 0,
              message: "Đã xảy ra lỗi, cập nhật chuyển đổi loại hình không thành công !"
            }
          }
        }
      } catch (e) {
        if (e.data && e.data.message) {
          return {
            code: 0,
            message: e.data.message
          }
        } else if (e.response && e.response.data && e.response.data.message) {
          return {
            code: 0,
            message: e.response.data.message
          }
        } else {
          return {
            code: 0,
            message: "Đã xảy ra lỗi, cập nhật chuyển đổi loại hình không thành công !"
          }
        }
      }
    },
    async fn_themtb_cdlh (data) {
      try {
        let response = await ChangeSubsTypeAPI.fn_themtb_cdlh(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          let result = JSON.parse(response.data.data)
          // {\"ERROR_CODE\":1,\"MESSAGE\":\"Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!\",\"RESULT\":{}}
          if (result.ERROR_CODE == 1) {
            return {
              code: 1,
              message: result.MESSAGE
            }
          } else {
            return {
              code: 0,
              message: result.MESSAGE
            }
          }
        } else {
          if (response && response.data && response.data.message) {
            return {
              code: 0,
              message: response.data.message
            }
          } else {
            return {
              code: 0,
              message: "Đã xảy ra lỗi, thêm thuê bao chuyển đổi loại hình không thành công!"
            }
          }
        }
      } catch (e) {
        if (e.data && e.data.message) {
          return {
            code: 0,
            message: e.data.message
          }
        } else if (e.response && e.response.data && e.response.data.message) {
          return {
            code: 0,
            message: e.response.data.message
          }
        } else {
          return {
            code: 0,
            message: "Đã xảy ra lỗi, thêm thuê bao chuyển đổi loại hình không thành công!"
          }
        }
      }
    },
    async kiemtra_thanhtoan_datmoi_lh (hdkh_id) {
      try {
        this.loading(true)
        let response = await ChangeSubsTypeAPI.kiemtra_thanhtoan_datmoi_lh(this.axios, hdkh_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return "0"
        }
      } catch (e) {
        this.loading(false)
        return "0"
      }
    },
    async sp_themmoi_hopdongtb_goida_dv_mytv (huonggiao_id, js_hdtbgoi) {
      try {
        let response = await ChangeSubsTypeAPI.sp_themmoi_hopdongtb_goida_dv_mytv(this.axios, huonggiao_id, js_hdtbgoi)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          let result = JSON.parse(response.data.data)
          // {\"ERROR_CODE\":1,\"MESSAGE\":\"Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!\",\"RESULT\":{}}
          if (result.ERROR_CODE == 1) {
            return {
              code: 1,
              message: result.MESSAGE
            }
          } else {
            return {
              code: 0,
              message: result.MESSAGE
            }
          }
        } else {
          if (response && response.data && response.data.message) {
            return {
              code: 0,
              message: response.data.message
            }
          } else {
            return {
              code: 0,
              message: "Đã xảy ra lỗi"
            }
          }
        }
      } catch (e) {
        if (e.data && e.data.message) {
          return {
            code: 0,
            message: e.data.message
          }
        } else if (e.response && e.response.data && e.response.data.message) {
          return {
            code: 0,
            message: e.response.data.message
          }
        } else {
          return {
            code: 0,
            message: "Đã xảy ra lỗi"
          }
        }
      }
    },
    async sp_themmoi_hopdongtb_goida_dv_ftth (huonggiao_id, js_hdtb, js_hdtbgoi) {
      try {
        let response = await ChangeSubsTypeAPI.sp_themmoi_hopdongtb_goida_dv_ftth(this.axios, huonggiao_id, js_hdtb, js_hdtbgoi)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          let result = JSON.parse(response.data.data)
          // {\"ERROR_CODE\":1,\"MESSAGE\":\"Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!\",\"RESULT\":{}}
          if (result.ERROR_CODE == 1) {
            return {
              code: 1,
              message: result.MESSAGE
            }
          } else {
            return {
              code: 0,
              message: result.MESSAGE
            }
          }
        } else {
          if (response && response.data && response.data.message) {
            return {
              code: 0,
              message: response.data.message
            }
          } else {
            return {
              code: 0,
              message: "Đã xảy ra lỗi"
            }
          }
        }
      } catch (e) {
        if (e.data && e.data.message) {
          return {
            code: 0,
            message: e.data.message
          }
        } else if (e.response && e.response.data && e.response.data.message) {
          return {
            code: 0,
            message: e.response.data.message
          }
        } else {
          return {
            code: 0,
            message: "Đã xảy ra lỗi"
          }
        }
      }
    },
    async sp_lay_cuoctk_cuoctc (data) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_cuoctk_cuoctc(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async lay_tt_tb_dd (vds_tb_id) {
      try {
        let response = await ChangeSubsTypeAPI.lay_tt_tb_dd(this.axios, vds_tb_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async thongtin_nguoidung () {
      try {
        let response = await ChangeSubsTypeAPI.thongtin_nguoidung(this.axios)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        return []
      }
    },
    async ts_laphd_thanhly () {
      try {
        let response = await ChangeSubsTypeAPI.ts_laphd_thanhly(this.axios)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return {
            code: 1,
            data: response.data.data
          }
        } else {
          return {
            code: 0,
            data: response.data.data
          }
        }
      } catch (e) {
        if (e.data && e.data.message) {
          return {
            code: 0,
            message: [e.data.message]
          }
        } else if (e.response && e.response.data && e.response.data.message) {
          return {
            code: 0,
            message: [e.response.data.message]
          }
        } else {
          return {
            code: 0,
            message: ["Đã xảy ra lỗi"]
          }
        }
      }
    },
    async fn_lay_text_huongketnoi_v2 (huong_kn) {
      try {
        let response = await ChangeSubsTypeAPI.fn_lay_text_huongketnoi_v2(this.axios, huong_kn)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return ""
        }
      } catch (e) {
        return ""
      }
    },

    async fn_tt_taodulieu_hdtb_mgwan_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_tt_taodulieu_hdtb_mgwan_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async fn_lay_tt_hienthittdiachi_tsl_cdlhtb (ds_para) {
      try {
        let response = await ChangeSubsTypeAPI.fn_lay_tt_hienthittdiachi_tsl_cdlhtb(this.axios, ds_para)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async kiemtra_quyen_nd (quyen_id) {
      try {
        let response = await ChangeSubsTypeAPI.kiemtra_quyen_nd(this.axios, quyen_id)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return Number(response.data.data)
        } else {
          return 0
        }
      } catch (e) {
        return 0
      }
    },
    async tudong_gan_hodan_thuebao (data) {
      try {
        await ChangeSubsTypeAPI.tudong_gan_hodan_thuebao(this.axios, data)
      } catch (e) {

      }
    },
    async tudong_gan_cap_thuebao (data) {
      try {
        await ChangeSubsTypeAPI.tudong_gan_cap_thuebao(this.axios, data)
      } catch (e) {

      }
    },
    async sp_capnhat_status_hdtb (hdtb_id, status) {
      try {
        await ChangeSubsTypeAPI.sp_capnhat_status_hdtb(this.axios, hdtb_id, status)
      } catch (e) {

      }
    },
    async update_hdtb_dd (hdtb_id, ma_hd_neo, ghichu) {
      try {
        await ChangeSubsTypeAPI.update_hdtb_dd(this.axios, hdtb_id, ma_hd_neo, ghichu)
      } catch (e) {

      }
    },
    async sp_lay_thongtin_hdkh_tuvan (hdkh_cha_id, kieu) {
      try {
        let response = await ChangeSubsTypeAPI.sp_lay_thongtin_hdkh_tuvan(this.axios, hdkh_cha_id, kieu)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async khoiTaoThamSo () {
      let ds_thamso = await this.ds_thamso_md(0)
      if (ds_thamso.length > 0) {
        // NHAP_THONGTIN_TINHCUOC
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "NHAP_THONGTIN_TINHCUOC" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.nhap_tttc = 1
        }
        // NHAP_TOCDO_THUC_ADSL
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "NHAP_TOCDO_THUC_ADSL" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.nhap_tocdo_thuc_adsl = 1
        }
        // KIEMTRA_KHOP_TIEN_DATCOC
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "KIEMTRA_KHOP_TIEN_DATCOC" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.kiemtra_khop_tien_datcoc = true
        }
        // SINHMA_TB_MGW_MTR
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "SINHMA_TB_MGW_MTR" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.ts_sinh_matb_mgw_mte = true
        }
        // KHONG_THAYDOI_DIACHI_LD
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "KHONG_THAYDOI_DIACHI_LD" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.khong_thaydoi_diachi_ld = 1
        }
        // KHONGBATBUOC_CTV
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "KHONGBATBUOC_CTV" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.khong_batbuoc_ctv = 1
        }
        // NHAP_KHUVUC_LHTB
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "NHAP_KHUVUC_LHTB" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.nhap_nvkd = 1
        }
        // BATBUOC_GIOITHIEU_LOAIHINH
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "BATBUOC_GIOITHIEU_LOAIHINH" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.batbuoc_gioithieu_loaihinh = 1
        }
        // CHUYENDOI_LH_DUNGTHU
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "CHUYENDOI_LH_DUNGTHU" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.CHUYENDOI_LH_DUNGTHU = 1
        }
        // BATBUOC_DK_TRATRUOC_LHTB
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "BATBUOC_DK_TRATRUOC_LHTB") > -1) {
          let ts = ds_thamso.find(x => x.ma_ts.toString() == "BATBUOC_DK_TRATRUOC_LHTB")
          this.thamso.dk_tratruoc = 1
          this.thamso.nguoidung_boqua_tratruoc = Number(ts.ten_ts.toString())
        }
        // IP_SIPTRUNKING
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "IP_SIPTRUNKING" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.phainhap_ip_siptrunking = true
        }

        this.thamso.TUDONG_SINH_SOAO = ds_thamso.findIndex(x => x.ma_ts.toString() == "TUDONG_SINH_SOAO") > -1
        this.thamso.ts_sinhma_gd_theo_donvi = ds_thamso.findIndex(x => x.ma_ts.toString() == "SINH_MA_GD_THEO_DV") > -1
        // BATBUOC_CHON_DACDIEM_DCLD
        if (ds_thamso.findIndex(x => x.ma_ts.toString() == "BATBUOC_CHON_DACDIEM_DCLD" && x.ten_ts.toString() == "1") > -1) {
          this.thamso.batbuoc_chon_dacdiem_dcld = 1
        }
      }
      console.log("Tham số: ", this.thamso)
    },
    async hopdong_theo_ma_gd () {
      let ma_gd = await this.$refs.thongTinPhieuYeuCau.getMaGD()
      if (ma_gd == "") {
        return
      }
      if (this.iKieu == 1) {
        let ds = await this.lay_ds_hopdong_theo_ma_gd({
          ma_gd: ma_gd,
          loaihd_id: LoaiHopDong.CHUYENDOI_LH,
          donvi_id: 0,
          tthd_id: 0,
          nhanvien_id: 0,
          donvi_dl_id: 0
        })
        await this.HienThiTTHopDongKH(ds)
      } else if (this.iKieu == 2) {
        var idonvi_id = 0
        if (this.$root.token.getProperty("donvi_dl_id") != 0) {
          idonvi_id = this.$root.token.getDonViID()
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
      } else {
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
    async xoa_hopdong_thuebao (hdtbId) {
      try {
        this.loading(true)
        let response = await ChangeSubsTypeAPI.xoa_hopdong_thuebao(this.axios, hdtbId)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000") {
          return {
            code: 1,
            message: "Xóa hợp đồng thuê bao thành công"
          }
        } else {
          if (response && response.data && response.data.message) {
            return {
              code: 0,
              message: response.data.message
            }
          } else {
            return {
              code: 0,
              message: "Đã xảy ra lỗi, xóa hợp đồng thuê bao không thành công"
            }
          }
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          return {
            code: 0,
            message: e.data.message
          }
        } else if (e.response && e.response.data && e.response.data.message) {
          return {
            code: 0,
            message: e.response.data.message
          }
        } else {
          return {
            code: 0,
            message: "Đã xảy ra lỗi, xoá dữ liệu không thành công"
          }
        }
      }
    },
    async xoa_hdkh_v2 (hdkh_id, loaihd_id) {
      try {
        this.loading(true)
        let response = await ChangeSubsTypeAPI.xoa_hdkh_v2(this.axios, hdkh_id, loaihd_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000") {
          return {
            code: 1,
            message: "Đã xóa hợp đồng chuyển đổi loại hình thành công trên hệ thống của viễn thông tỉnh"
          }
        } else {
          if (response && response.data && response.data.message) {
            return {
              code: 0,
              message: response.data.message
            }
          } else {
            return {
              code: 0,
              message: "Đã xảy ra lỗi, xóa hợp đồng chuyển đổi loại hình không thành công"
            }
          }
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          return {
            code: 0,
            message: e.data.message
          }
        } else if (e.response && e.response.data && e.response.data.message) {
          return {
            code: 0,
            message: e.response.data.message
          }
        } else {
          return {
            code: 0,
            message: "Đã xảy ra lỗi, xóa hợp đồng chuyển đổi loại hình không thành công"
          }
        }
      }
    },
    async HienThiTTHopDongKH (ds) {
      // handle
      if (ds.length > 0) {
        await this.setActiveActions(3)
        let item = ds[0]

        this.hdkh_id = item.hdkh_id
        this.hdkh_cha_id = (item.hdkh_cha_id != null && item.hdkh_cha_id != "") ? Number(item.hdkh_cha_id) : 0
        this.khachhang_id = (item.khachhang_id != null && item.khachhang_id != "") ? Number(item.khachhang_id) : 0

        await this.$refs.thongTinPhieuYeuCau.HienThiTTHopDongKH(item)
        await this.$refs.thongTinKhachHang.HienThiTTHopDongKH(item)

        if (item.kieuhd_id != null && item.kieuhd_id.toString() != "") {
          this.kieu_pl_selected = item.kieuhd_id
        } else {
          this.kieu_pl_selected = KIEU_HD.TAI_GD
        }
        console.log("kieu_pl_selected", this.kieu_pl_selected)

        this.input_ma_duan = item.ma_duan != null ? item.ma_duan : ""
        this.dichvuvt_id = item.dichvuvt_id != null ? Number(item.dichvuvt_id) : 0

        //
        if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
          this.actions[this.actions.findIndex(x => x.id == "capnhatdb")].active = true
        }

        // item.nhanviengt_id=452
        // item.ctv_id=13431
        // UR2.1.035_015 API gom
        let result = await this.fn_lay_tt_ht_hdkh_cdlhtb(JSON.stringify({
          NGUOIGT_ID: item.nhanviengt_id != null ? item.nhanviengt_id : 0,
          DICHVUVT_ID: this.dichvuvt_id,
          CTV_ID: item.ctv_id != null ? item.ctv_id : 0,
          HDKH_ID: item.hdkh_id != null ? item.hdkh_id : 0,
          HDTB_ID: item.hdtb_id != null ? item.hdtb_id : 0
        }))

        if (result != null && result.ERROR_CODE == 1) {
          let data = result.RESULT
          // CTV
          if (data.CTV != "-1") {
            this.checkCTV = true
            this.ctv.ctv_id = item.ctv_id
            this.ctv.ten_ctv = data.CTV
          } else {
            this.checkCTV = false
            this.ctv = {
              ctv_id: null,
              ten_ctv: "",
              ma_ctv: "",
              loainv_id: -1,
              donvi_id: -1
            }
          }
          // Người GT
          if (data.NGUOIGT != "-1") {
            this.checkNguoiGT = true
            this.nguoi_gt.nguoi_gt_id = item.nhanviengt_id
            this.nguoi_gt.ten_nguoi_gt = data.NGUOIGT
          } else {
            this.checkNguoiGT = false
            this.nguoi_gt = {
              nguoi_gt_id: null,
              ten_nguoi_gt: "",
              ma_nguoi_gt: "",
              loainv_id: -1,
              donvi_id: -1
            }
          }
          // hiện thị ds hợp đồng thuê bao lên lưới
          let dsHDTB = data.TT_DSHDTB
          await this.HT_TT(dsHDTB)
          // Hiển thị địa chỉ
          let dsDiaChi = data.TT_DIACHI
          await this.HienThiTTDiaChi(dsDiaChi)
          //
        } else if (result != null && result.ERROR_CODE == 0) {
          this.$toast.error(result.MESSAGE)
        }

        console.log("result", result)
      } else {
        this.dataSources = []
        await this.setActiveActions(0)
        this.$toast.error("Không có thông tin hợp đồng")
      }
    },
    async HT_TT (dsHDTB) {
      if (dsHDTB.length > 0) {
        if (dsHDTB.length > 1) {
          this.actions[this.actions.findIndex(x => x.id == "xoatb")].active = true
        } else {
          this.actions[this.actions.findIndex(x => x.id == "xoatb")].active = false
        }
        this.dataSources = []
        setTimeout(async () => {
          this.dataSources = dsHDTB
          this.rowSelectedId = dsHDTB[0].HDTB_ID
          await this.onSelectedRow(dsHDTB[0])
        }, 500)
      } else {
        this.dataSources = []
      }
    },
    async HienThiTTDiaChi (dsDiaChi) {
      if (dsDiaChi.length > 0) {
        // Địa chỉ tb
        Object.assign(this.dataDiaChiTB, {
          diachi_id: dsDiaChi[0].DIACHI_ID,
          tinh_id: dsDiaChi[0].TINH_ID,
          quan_id: dsDiaChi[0].QUAN_ID,
          phuong_id: dsDiaChi[0].PHUONG_ID,
          pho_id: dsDiaChi[0].PHO_ID != null ? dsDiaChi[0].PHO_ID : 0,
          ap_id: dsDiaChi[0].AP_ID != null ? dsDiaChi[0].AP_ID : 0,
          khu_id: dsDiaChi[0].KHU_ID != null ? dsDiaChi[0].KHU_ID : 0,
          dacdiem_id: dsDiaChi[0].DACDIEM_ID != null ? dsDiaChi[0].DACDIEM_ID : 0,
          sonha: dsDiaChi[0].SONHA != null ? dsDiaChi[0].SONHA : "",
          // chưa rõ model
          block: dsDiaChi[0].hasOwnProperty("BLOCK") ? dsDiaChi[0].BLOCK : "",
          tang: dsDiaChi[0].hasOwnProperty("TANG") ? dsDiaChi[0].TANG : "",
          sophong: dsDiaChi[0].hasOwnProperty("PHONG") ? dsDiaChi[0].PHONG : "",
          motathem: dsDiaChi[0].hasOwnProperty("MOTA") ? dsDiaChi[0].MOTA : "",
          diachi: "",
          //
          kinhdo: dsDiaChi[0].KINHDO,
          vido: dsDiaChi[0].VIDO
        })
        // Địa chỉ LD
        Object.assign(this.dataDiaChiLD, {
          diachi_id: dsDiaChi[0].DIACHILD_ID,
          tinh_id: dsDiaChi[0].TINHLD_ID,
          quan_id: dsDiaChi[0].QUANLD_ID,
          phuong_id: dsDiaChi[0].PHUONGLD_ID,
          pho_id: dsDiaChi[0].PHOLD_ID != null ? dsDiaChi[0].PHOLD_ID : 0,
          ap_id: dsDiaChi[0].APLD_ID != null ? dsDiaChi[0].APLD_ID : 0,
          khu_id: dsDiaChi[0].KHULD_ID != null ? dsDiaChi[0].KHULD_ID : 0,
          dacdiem_id: dsDiaChi[0].DACDIEMLD_ID != null ? dsDiaChi[0].DACDIEMLD_ID : 0,
          sonha: dsDiaChi[0].SONHA_LD != null ? dsDiaChi[0].SONHA_LD : "",
          // chưa rõ model
          block: dsDiaChi[0].hasOwnProperty("BLOCK_LD") ? dsDiaChi[0].BLOCK_LD : "",
          tang: dsDiaChi[0].hasOwnProperty("TANG_LD") ? dsDiaChi[0].TANG_LD : "",
          sophong: dsDiaChi[0].hasOwnProperty("PHONG_LD") ? dsDiaChi[0].PHONG_LD : "",
          motathem: dsDiaChi[0].hasOwnProperty("MOTA_LD") ? dsDiaChi[0].MOTA_LD : "",
          diachi: "",
          //
          kinhdo: dsDiaChi[0].KINHDO_LD,
          vido: dsDiaChi[0].VIDO_LD
        })
      }
      await this.$refs.thongTinKhachHang.HienThiTTDiaChi(dsDiaChi)
    },
    // Bổ sung lại api gom
    async HienThiTTHopDongTB (item) {
      console.log("HienThiTTHopDongTB", item)
      this.hdtb_id = item.HDTB_ID != null ? item.HDTB_ID : 0
      this.thuebao_id = item.THUEBAO_ID != null ? item.THUEBAO_ID : 0

      if (item.hasOwnProperty("THUEBAO_CHA_ID") && item.THUEBAO_CHA_ID != null) {
        this.thuebao_id_cha = item.THUEBAO_CHA_ID
        this.loaisochinh = 1
      } else {
        this.thuebao_id_cha = 0
      }

      if (item.HDTB_CHA_ID != null) {
        this.hdtb_id_cha = item.HDTB_CHA_ID
        this.loaisochinh = 0
      } else {
        this.hdtb_id_cha = 0
      }
      this.vdiachi_tb = item.DIACHI_TB != null ? item.DIACHI_TB : ""
      this.donviql_id = item.DONVI_ID != null ? item.DONVI_ID : 0
      this.doituong_id = item.DOITUONG_ID != null ? item.DOITUONG_ID : 0

      //
      await this.$refs.thongTinKhachHang.HienThiTTHopDongTB(item)

      this.loaitb_id = item.LOAITB_ID != null ? item.LOAITB_ID : 0

      // sử dụng api gom cho hàm này
      this.loading(true)
      let result = await this.fn_lay_tt_ht_hdtb_cdlhtb(JSON.stringify({
        THUEBAO_ID: this.thuebao_id,
        HDTB_ID: this.hdtb_id
      }))
      this.loading(false)
      console.log("HienThiTTHopDongTB", result)
      if (result && result.ERROR_CODE == 1) {
        this.dichvuvt_id = Number(result.RESULT.DICHVUVT_ID)
        // this.dichvuvt_id=item.DICHVUVT_ID!=null?item.DICHVUVT_ID:0
        Object.assign(item, {dichvuvt_id_map: this.dichvuvt_id})
        await this.$refs.thongTinPhieuYeuCau.HienThiTTHopDongTB(item)
        if (this.dichvuvt_id != this.dichvu_vt_selected) {
          this.dichvu_vt_selected = this.dichvuvt_id
          // biding layout nếu khác dịch vụ
          setTimeout(async () => {
            this.loading(true)
            if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
              await this.$refs.coDinh.initDuLieu(this.ds_loaihinh_tb)
              await this.$refs.coDinh.visibleToolStripMenuAndControl(this.iKieu == 0)
            } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
              await this.$refs.diDong.initDuLieu(this.ds_loaihinh_tb)
            } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
              // băng rộng cố định
              await this.$refs.bangRongCoDinh.initDuLieu(this.ds_loaihinh_tb)
              if (this.thamso.nhap_tocdo_thuc_adsl == 0) {
                this.$refs.bangRongCoDinh.enabledCheckTocDoThuc = false
              } else {
                this.$refs.bangRongCoDinh.enabledCheckTocDoThuc = true
              }
              this.$refs.bangRongCoDinh.checkTocDoThuc = false
              await this.$refs.bangRongCoDinh.visibleToolStripMenuAndControl(this.iKieu == 0)
            } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
              await this.$refs.ims.initDuLieu(this.ds_loaihinh_tb)
              await this.$refs.ims.visibleToolStripMenuAndControl(this.iKieu == 0)
            } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
              await this.$refs.kenhThuRieng.initDuLieu(this.ds_loaihinh_tb)
              await this.$refs.kenhThuRieng.visibleToolStripMenuAndControl(this.iKieu == 0)
            } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
              await this.$refs.megawan.initDuLieu(this.ds_loaihinh_tb, this.dichvu_vt_selected)
              await this.$refs.megawan.visibleToolStripMenuAndControl(this.iKieu == 0)
            }
            await this.HienThiTTHopDongTB_B2(item, result.RESULT)
            this.loading(false)
          }, 500)
        } else {
          // làm như thường
          this.loading(true)
          await this.HienThiTTHopDongTB_B2(item, result.RESULT)
          this.loading(false)
        }
      } else if (result && result.ERROR_CODE == 0) {
        this.$toast.error(result.MESSAGE)
      }
    },
    async HienThiTTHopDongTB_B2 (item, result) {
      // Làm sau với MEGAWAN, METRONET
      if (this.thamso.ts_sinh_matb_mgw_mte && this.dichvu_vt_selected == DichVuVienThong.MEGAWAN ||
                this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        this.$refs.megawan.input_matb = item.MA_TB_CU != null ? item.MA_TB_CU : ""
        this.$refs.megawan.input_account_moi = item.MA_TB != null ? item.MA_TB : ""
      } else {
        if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
          this.$refs.coDinh.input_matb = item.MA_TB != null ? item.MA_TB : ""
        } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
          this.$refs.diDong.input_matb = item.MA_TB != null ? item.MA_TB : ""
        } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
          // băng rộng cố định
          this.$refs.bangRongCoDinh.input_matb = item.MA_TB != null ? item.MA_TB : ""
        } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
          this.$refs.ims.input_matb = item.MA_TB != null ? item.MA_TB : ""
        } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
          this.$refs.kenhThuRieng.input_matb = item.MA_TB != null ? item.MA_TB : ""
        } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
          this.$refs.megawan.input_matb = item.MA_TB != null ? item.MA_TB : ""
          this.$refs.megawan.input_account_moi = item.MA_TB != null ? item.MA_TB : ""
        }
      }

      // handle
      await this.HienThiTTDiaChi(result.TT_DIACHI_HDTB)
      // bỏ tạm
      if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        this.loaihinhtb_id = this.loaitb_id
      }

      if (this.loaihinhtb_id == LoaiHinhTB.DIDONGTRATRUOC) {
        this.loaihinhtbcu_id = LoaiHinhTB.DIDONGTRASAU
      } else if (this.loaitb_id != LoaiHinhTB.FSECURE) {
        this.loaihinhtbcu_id = (item.hasOwnProperty("LOAITB_ID_CU") && item.LOAITB_ID_CU != null) ? item.LOAITB_ID_CU : 0
      } else {
        this.loaihinhtbcu_id = this.loaitb_id
      }
      console.log("HienThiTTHopDongTB_B2 loaihinhtb_id", this.loaihinhtb_id)
      await this.$refs.thongTinPhieuYeuCau.setLoaiHinhTbCu(this.loaihinhtbcu_id)
      await this.changeLoaiHinhTBCu(this.loaihinhtbcu_id)
      // trigger call
      await this.changeKieuYC(item.KIEULD_ID != null ? item.KIEULD_ID : 0)
      await this.changeMucCuocTB(item.MUCUOCTB_ID != null ? item.MUCUOCTB_ID : -1)

      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        await this.$refs.coDinh.setLoaiHinhTbMoi(this.loaitb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        await this.$refs.diDong.setLoaiHinhTbMoi(this.loaitb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        await this.$refs.bangRongCoDinh.setLoaiHinhTbMoi(this.loaitb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        await this.$refs.ims.setLoaiHinhTbMoi(this.loaitb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.$refs.kenhThuRieng.setLoaiHinhTbMoi(this.loaitb_id)
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        await this.$refs.megawan.setLoaiHinhTbMoi(this.loaitb_id)
      }

      await this.changeLoaiHinhTBMoi(this.loaitb_id)

      await this.$refs.thongTinPhieuYeuCau.HienThiTTHopDongTB_B2(item, result)

      // handle các layout HienThiTTHopDongTB_B2
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        await this.$refs.coDinh.HienThiTTHopDongTB_B2(item, result)
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        await this.$refs.diDong.HienThiTTHopDongTB_B2(item, result)
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        await this.$refs.bangRongCoDinh.HienThiTTHopDongTB_B2(item, result)
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        await this.$refs.ims.HienThiTTHopDongTB_B2(item, result)
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.$refs.kenhThuRieng.HienThiTTHopDongTB_B2(item, result)
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        await this.$refs.megawan.HienThiTTHopDongTB_B2(item, result)
      }

      // txtCuocTamThu.Text = Convert.ToDecimal(dst.Rows[rowIndex]["cuoctamthu"].ToString()).ToString(Format.FM_TIEN_VND);
      this.input_tongtien = await this.layTongTien()
      console.log("input_tongtien", this.input_tongtien)

      this.khuvuc_id = result.KHUVUC_ID != "" ? Number(result.KHUVUC_ID) : 0

      if (this.loaitb_id != LoaiHinhTB.FSECURE) {
        await this.HienThiTTMoRong_DB(this.thuebao_id, this.dichvuvt_id)
      }

      await this.HienThiTTMoRong_HD(this.hdtb_id, this.dichvuvt_id)

      // load mức cước tb
      var vtrangbi_id = 0
      if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        vtrangbi_id = await this.$refs.bangRongCoDinh.getTrangBiAdslIDMoi()
      }
      var vloaitb_id_moi = 0
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        vloaitb_id_moi = await this.$refs.coDinh.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {

      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL) {
        // băng rộng cố định
        vloaitb_id_moi = await this.$refs.bangRongCoDinh.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        vloaitb_id_moi = await this.$refs.ims.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        vloaitb_id_moi = await this.$refs.kenhThuRieng.getLoaiHinhTB_ID_Moi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        vloaitb_id_moi = await this.$refs.megawan.getLoaiHinhTB_ID_Moi()
      }

      let ngay_yc = await this.$refs.thongTinPhieuYeuCau.getNgayYC()
      await this.Lay_DS_MucCuoc_TB_V2(this.dataDiaChiLD.khu_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.phuong_id, vloaitb_id_moi, this.doituong_id, ngay_yc, vtrangbi_id)
      //

      this.KetCuoi_ID = result.KETCUOI_ID != null ? result.KETCUOI_ID : 0

      this.dsKM_HDTB = []
      this.dsKM_HDTB = result.TT_DS_CTKM_HDTB
      // Hien thi tien hop dong
      this.dtCTKM = result.TT_TIEN_HD_HDTB
      var tien_ld = 0
      var vat_ld = 0
      var tien_cd = 0
      var vat_cd = 0
      for (let i = 0; i < this.dtCTKM.length; i++) {
        if (this.dtCTKM[i].KHOANMUCTT_ID == KHOANMUC_TT.KMTT_TDLOAIHINHTB) {
          tien_ld = this.dtCTKM[i].TIEN
          vat_ld = this.dtCTKM[i].VAT
        }
        if (this.dtCTKM[i].KHOANMUCTT_ID == KHOANMUC_TT.KMTT_KM_LAPDAT) {
          this.tien_kmld = this.dtCTKM[i].TIEN
          this.vat_kmld = this.dtCTKM[i].VAT
        }
      }
      await this.HT_DS_KhuyenMai_Combobox()

      // DataSet dsTemp = new TCHopDongFacade().LAY_CHITIET_KM_THEO_HDTB_ID(hdtb_id, 0);
      //     dsKM_HDTB = new KHUYENMAI_HDTB_DATA();
      this.dsKM_HDTB = []
      this.dsKM_HDTB = result.TT_CTKM_HDTB_KM
      if (this.dsKM_HDTB.length > 0) {
        this.$refs.thongTinPhieuYeuCau.khuyenmai.checkKhuyenMai = true
        this.$refs.thongTinPhieuYeuCau.khuyenmai.khuyenmai_selected = this.dsKM_HDTB[0].KHUYENMAI_ID
      }

      this.dsDC_HDTB = result.TT_CTKM_HDTB_DATCOC
      if (this.dsDC_HDTB.length > 0) {
        this.$refs.thongTinPhieuYeuCau.tratruoc.checkTraTruoc = true
        this.$refs.thongTinPhieuYeuCau.tratruoc.tratruoc_selected = this.dsDC_HDTB[0].KHUYENMAI_ID
      }

      this.$refs.thongTinKhachHang.input_thang_ck = item.SOTHANG_CK != null ? item.SOTHANG_CK.toString() : ""

      // Thiết bị
      this.dsCTM_TBI = []
      this.dsCTM_TBI = result.TT_DS_MUA_TBI

      // Hiển thị tiền khoản mục
      this.ds_tien_khoanmuc = result.TT_TIENHD_HDTB.map(x => {
        return {
          khoanmuctt_id: x.KHOANMUCTT_ID.toString(),
          tien: x.TIEN.toString(),
          vat: x.VAT.toString()
        }
      })
      this.ds_tien_khoanmuc_bd = this.ds_tien_khoanmuc.slice()
      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        let item = this.ds_tien_khoanmuc[i]
        if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_TDLOAIHINHTB.toString()) {
          this.dtienld = Number(item.tien)
          this.dvatld = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
          this.dtienkmld = Number(item.tien)
          this.dvatkmld = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_KM_DATCOC.toString()) {
          this.dtienkmdc = Number(item.tien)
          this.dvatkmdc = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DATCOC.toString()) {
          this.dtiendatcoc_tt = Number(item.tien)
          this.dvatdatcoc_tt = Number(item.vat)
        } else if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DATCOC_KM) {
          this.dtiendatcoc_km = Number(item.tien)
          this.dvatdatcoc_km = Number(item.vat)
        }
      }

      await this.HT_ThietBi()
      await this.LayTongTien_HDTB()

      if (this.dataSources.length <= 1) {
        this.actions[this.actions.findIndex(x => x.id == "xoatb")].active = false
      } else {
        this.actions[this.actions.findIndex(x => x.id == "xoatb")].active = true
      }
    },
    async HienThiTTMoRong_DB (thuebao_id, dichvuvt_id) {
      let result = await this.fn_lay_tt_hienthittmorong_db_cdlhtb(JSON.stringify({
        THUEBAO_ID: thuebao_id,
        DICHVUVT_ID: dichvuvt_id
      }))
      if (result != null && result.ERROR_CODE == 1) {
        await this.$refs.thongTinKhachHang.HienThiTTMoRong_DB(result.RESULT.TT_DBTB)
        if (result.RESULT.TT_DBTB.length > 0) {
          if (this.dichvuvt_id == DichVuVienThong.CO_DINH) {
            console.log("HienThiTTMoRong_DB_CoDinh", result.RESULT.TT_DBTB)
            await this.$refs.coDinh.HienThiTTMoRong_DB(result.RESULT.TT_DBTB[0])
            if (this.loaihinhtbcu_id == LoaiHinhTB.ISDN2B_CD ||
                            this.loaihinhtbcu_id == LoaiHinhTB.ISDN2B_CQ ||
                            this.loaihinhtbcu_id == LoaiHinhTB.ISDN30B_CD ||
                            this.loaihinhtbcu_id == LoaiHinhTB.ISDN30B_CQ) {
              console.log("LAY_DS_ISDN_THEO_THUEBAO_ID")

              await this.LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id)
            }
          } else if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
            await this.$refs.diDong.HienThiTTMoRong_DB(result.RESULT.TT_DBTB[0])
          } else if (this.dichvuvt_id == DichVuVienThong.ADSL) {
            await this.$refs.bangRongCoDinh.HienThiTTMoRong_DB(result.RESULT.TT_DBTB[0])
            this.$refs.thongTinPhieuYeuCau.input_soao = result.RESULT.TT_DBTB[0].MA_LT != null ? result.RESULT.TT_DBTB[0].MA_LT.toString() : ""
          } else if (this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.METRONET) {
            let item = result.RESULT.TT_DBTB[0]
            if (this.$refs.megawan.tocdo_cir_moi_selected != item.TOCDO_ID) {
              this.$refs.megawan.isChangeTocDo = false
              this.$refs.megawan.tocdo_cir_moi_selected = item.TOCDO_ID
              await this.changeTocDoCirWan(this.$refs.megawan.tocdo_cir_moi_selected)
            } else {
              await this.changeTocDoCirWan(this.$refs.megawan.tocdo_cir_moi_selected)
            }
            if (this.$refs.megawan.loaithietbi_moi_selected != item.THIETBIDC_ID) {
              this.$refs.megawan.loaithietbi_moi_selected = item.THIETBIDC_ID
              await this.changeLoaiTBWanMoi(this.$refs.megawan.loaithietbi_moi_selected)
            } else {
              await this.changeLoaiTBWanMoi(this.$refs.megawan.loaithietbi_moi_selected)
            }

            this.vhuong_kn = item.HUONG_KN != null ? item.HUONG_KN.toString() : ""

            // fn_lay_text_huongketnoi_v2
            if (this.vhuong_kn != "") {
              let textHuongKN = await this.fn_lay_text_huongketnoi_v2(this.vhuong_kn)
              this.$refs.megawan.input_huong_kn = textHuongKN
              this.$refs.megawan.input_huong_kn_moi = textHuongKN
            } else {
              this.$refs.megawan.input_huong_kn = ""
              this.$refs.megawan.input_huong_kn_moi = ""
            }

            this.cuoc_tc = item.CUOC_TC != null ? item.CUOC_TC : 0
            this.cuoc_tk = item.CUOC_TK != null ? item.CUOC_TK : 0
            this.$refs.megawan.trangbi_moi_selected = item.TRANGBI_ID != null ? item.TRANGBI_ID : 3
            await this.changeTrangBiMoi(this.$refs.megawan.trangbi_moi_selected)

            await this.$refs.megawan.HienThiTTMoRong_DB(result.RESULT.TT_DBTB[0])
            this.$refs.thongTinKhachHang.checkSoCVEnabled = true
            this.$refs.thongTinKhachHang.ngay_cv = moment(new Date()).format("DD/MM/YYYY")
            this.$refs.thongTinPhieuYeuCau.input_soao = result.RESULT.TT_DBTB[0].MA_LT != null ? result.RESULT.TT_DBTB[0].MA_LT.toString() : ""
            // txtMAHD_TTKD-->MaCNG
            this.$refs.thongTinPhieuYeuCau.input_ma_cng = item.MA_PL != null ? item.MA_PL.toString() : ""
          } else if (this.dichvuvt_id == DichVuVienThong.TSL) {
            let item = result.RESULT.TT_DBTB[0]
            await this.$refs.kenhThuRieng.HienThiTTMoRong_DB(result.RESULT.TT_DBTB[0])
            this.$refs.thongTinPhieuYeuCau.input_ma_cng = item.MA_PL != null ? item.MA_PL.toString() : ""
            this.$refs.thongTinKhachHang.checkSoCVEnabled = true
            this.$refs.thongTinKhachHang.ngay_cv = moment(new Date()).format("DD/MM/YYYY")
            this.$refs.thongTinPhieuYeuCau.input_soao = ""
            var soao = ""
            for (let i = 0; i < result.RESULT.TT_DBTB.length; i++) {
              if (result.RESULT.TT_DBTB[i].DAUCUOI_ID == 1) {
                // Nếu là điểm đầu
                this.diachidau_id = result.RESULT.TT_DBTB[i].DIACHI_ID
                soao += result.RESULT.TT_DBTB[i].MA_LT ? result.RESULT.TT_DBTB[i].MA_LT.toString() : "" + ","
              } else {
                this.diachicuoi_id = result.RESULT.TT_DBTB[i].DIACHI_ID
                soao += result.RESULT.TT_DBTB[i].MA_LT ? result.RESULT.TT_DBTB[i].MA_LT.toString() : "" + ","
              }
            }
            this.$refs.thongTinPhieuYeuCau.input_soao = soao.substring(0, soao.length - 1)
            await this.HienThiTTDiaChi_TSL()
            // "DONVI_LOAIDV_DAU":[
            //     {
            //         "DONVI_ID":872,
            //         "TEN_DV":"Tổ tổng hợp"
            //     },
            this.ds_loaidv_dau = result.RESULT.DONVI_LOAIDV_DAU
            this.donvi_dau_id = result.RESULT.DONVI_DAU_ID ? Number(result.RESULT.DONVI_DAU_ID) : 0
            this.ds_loaidv_cuoi = result.RESULT.DONVI_LOAIDV_CUOI
            this.donvi_cuoi_id = result.RESULT.DONVI_CUOI_ID ? Number(result.RESULT.DONVI_CUOI_ID) : 0
            this.tram_dv_dau = (result.RESULT.TRAMVTDAU && result.RESULT.TRAMVTDAU != "") ? Number(result.RESULT.TRAMVTDAU) : 0
            this.tram_dv_cuoi = (result.RESULT.TRAMVTCUOI && result.RESULT.TRAMVTCUOI != "") ? Number(result.RESULT.TRAMVTCUOI) : 0
            for (let i = 0; i < result.RESULT.TT_DBTB.length; i++) {
              if (result.RESULT.TT_DBTB[i].DAUCUOI_ID == 1) {
                this.$refs.kenhThuRieng.lcapdau_selected = result.RESULT.TT_DBTB[i].LOAICAP_ID ? result.RESULT.TT_DBTB[i].LOAICAP_ID : null
                this.$refs.kenhThuRieng.lcapdau_moi_selected = result.RESULT.TT_DBTB[i].LOAICAP_ID ? result.RESULT.TT_DBTB[i].LOAICAP_ID : null
                this.$refs.kenhThuRieng.input_diachi_dau = result.RESULT.TT_DBTB[i].DIACHI ? result.RESULT.TT_DBTB[i].DIACHI.toString() : ""
                this.$refs.kenhThuRieng.input_diachi_dau_moi = result.RESULT.TT_DBTB[i].DIACHI ? result.RESULT.TT_DBTB[i].DIACHI.toString() : ""
                this.ma_tb_dau = result.RESULT.TT_DBTB[i].MA_TB_SUB ? result.RESULT.TT_DBTB[i].MA_TB_SUB.toString() : ""

                this.Lng_DD = result.RESULT.TT_DBTB[i].KINHDO_LD ? result.RESULT.TT_DBTB[i].KINHDO_LD : 0
                this.Lat_DD = result.RESULT.TT_DBTB[i].VIDO_LD ? result.RESULT.TT_DBTB[i].VIDO_LD : 0
              } else {
                this.$refs.kenhThuRieng.lcapcuoi_selected = result.RESULT.TT_DBTB[i].LOAICAP_ID ? result.RESULT.TT_DBTB[i].LOAICAP_ID : null
                this.$refs.kenhThuRieng.lcapcuoi_moi_selected = result.RESULT.TT_DBTB[i].LOAICAP_ID ? result.RESULT.TT_DBTB[i].LOAICAP_ID : null
                this.$refs.kenhThuRieng.input_diachi_cuoi = result.RESULT.TT_DBTB[i].DIACHI ? result.RESULT.TT_DBTB[i].DIACHI.toString() : ""
                this.$refs.kenhThuRieng.input_diachi_cuoi_moi = result.RESULT.TT_DBTB[i].DIACHI ? result.RESULT.TT_DBTB[i].DIACHI.toString() : ""

                this.ma_tb_cuoi = result.RESULT.TT_DBTB[i].MA_TB_SUB ? result.RESULT.TT_DBTB[i].MA_TB_SUB.toString() : ""
                this.Lng_DC = result.RESULT.TT_DBTB[i].KINHDO_LD ? result.RESULT.TT_DBTB[i].KINHDO_LD : 0
                this.Lat_DC = result.RESULT.TT_DBTB[i].VIDO_LD ? result.RESULT.TT_DBTB[i].VIDO_LD : 0
              }
            }
          } else if (this.dichvuvt_id == DichVuVienThong.IMS) {
            await this.$refs.ims.HienThiTTMoRong_DB(result.RESULT.TT_DBTB[0])
          } else if (this.dichvuvt_id == DichVuVienThong.GPHONE) {

          }
        }
      } else if (result != null && result.ERROR_CODE == 0) {
        this.$toast.error(result.MESSAGE)
      }
    },
    async HienThiTTDiaChi_TSL () {
      // fn_lay_tt_hienthittdiachi_tsl_cdlhtb
      let result = await this.fn_lay_tt_hienthittdiachi_tsl_cdlhtb(JSON.stringify({
        DIACHIDAU_ID: this.diachidau_id,
        DIACHICUOI_ID: this.diachicuoi_id
      }))
      //
      if (result != null && result.ERROR_CODE == 1) {
        let diachiDau = result.RESULT.DIACHIDAU
        if (diachiDau.length > 0) {
          this.diachidau_id = diachiDau[0].DIACHI_ID ? diachiDau[0].DIACHI_ID : 0
          this.quandau_id = diachiDau[0].QUAN_ID ? diachiDau[0].QUAN_ID : 0
          this.phuongdau_id = diachiDau[0].PHUONG_ID ? diachiDau[0].PHUONG_ID : 0
          this.phodau_id = diachiDau[0].PHO_ID ? diachiDau[0].PHO_ID : 0
          this.ngodau_id = 0
          this.sonhadau = diachiDau[0].SONHA ? diachiDau[0].SONHA.toString() : 0
        }
        let diachiCuoi = result.RESULT.DIACHICUOI
        if (diachiCuoi.length > 0) {
          this.diachicuoi_id = diachiCuoi[0].DIACHI_ID ? diachiCuoi[0].DIACHI_ID : 0
          this.quancuoi_id = diachiCuoi[0].QUAN_ID ? diachiCuoi[0].QUAN_ID : 0
          this.phuongcuoi_id = diachiCuoi[0].PHUONG_ID ? diachiCuoi[0].PHUONG_ID : 0
          this.phocuoi_id = diachiCuoi[0].PHO_ID ? diachiCuoi[0].PHO_ID : 0
          this.ngocuoi_id = 0
          this.sonhacuoi = diachiCuoi[0].SONHA ? diachiCuoi[0].SONHA.toString() : 0
        }
      }
    },
    async HienThiTTMoRong_HD (hdtb_id, dichvuvt_id) {
      // fn_lay_tt_hienthittmorong_hd_cdlhtb
      let result = await this.fn_lay_tt_hienthittmorong_hd_cdlhtb(JSON.stringify({
        HDTB_ID: hdtb_id,
        THUEBAO_ID: this.thuebao_id,
        DICHVUVT_ID: dichvuvt_id
      }))
      console.log("HienThiTTMoRong_HD", result)
      this.$refs.thongTinKhachHang.checkSoCVEnabled = false
      this.$refs.thongTinKhachHang.checkSoCV = false
      this.$refs.thongTinKhachHang.input_socv = ""
      this.$refs.thongTinKhachHang.ngay_cv = moment(new Date()).format("DD/MM/YYYY")
      //
      if (result != null && result.ERROR_CODE == 1) {
        if (result.RESULT.TT_HDTB.length > 0) {
          let item = result.RESULT.TT_HDTB[0]
          if (dichvuvt_id == DichVuVienThong.CO_DINH) {
            if (item.TRANGBI_ID != null) {
              this.$refs.coDinh.trangbi_moi_selected = item.TRANGBI_ID
            } else {
              this.$refs.coDinh.trangbi_moi_selected = null
            }
            if (item.MADOICAP != null) {
              this.madoicap_cd = item.MADOICAP
            } else {
              this.madoicap_cd = 0
            }
            this.$refs.coDinh.input_sotat = item.SO_TAT != null ? item.SO_TAT : ""
          } else if (dichvuvt_id == DichVuVienThong.DI_DONG) {
            await this.$refs.diDong.HienThiTTMoRong_HD(item)
            this.$refs.diDong.trangbi_moi_selected = item.TRANGBI_ID
            await this.changeTrangBiMoi(this.$refs.diDong.trangbi_moi_selected)
          } else if (dichvuvt_id == DichVuVienThong.ADSL || dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT) {
            await this.$refs.bangRongCoDinh.HienThiTTMoRong_HD(item)
            //
            if (this.$refs.bangRongCoDinh.tocdo_moi_selected != item.TOCDO_ID) {
              this.$refs.bangRongCoDinh.isChangeGoiCuocADSL = false
              this.$refs.bangRongCoDinh.tocdo_moi_selected = item.TOCDO_ID
              await this.changeGoiCuocAdsl(this.$refs.bangRongCoDinh.tocdo_moi_selected)
            } else {
              await this.changeGoiCuocAdsl(this.$refs.bangRongCoDinh.tocdo_moi_selected)
            }
            this.$refs.bangRongCoDinh.tocdo_thuc_selected = item.TOCDOTHUC_ID != null ? item.TOCDOTHUC_ID : -1
            if (this.$refs.bangRongCoDinh.tocdo_thuc_selected != null && this.$refs.bangRongCoDinh.tocdo_selected != this.$refs.bangRongCoDinh.tocdo_moi_selected) {
              this.$refs.bangRongCoDinh.checkTocDoThuc = true
            }
          } else if (dichvuvt_id == DichVuVienThong.MEGAWAN || dichvuvt_id == DichVuVienThong.METRONET) {
            if (this.$refs.megawan.tocdo_cir_moi_selected != item.TOCDO_ID) {
              this.$refs.megawan.isChangeTocDo = false
              this.$refs.megawan.tocdo_cir_moi_selected = item.TOCDO_ID
              await this.changeTocDoCirWan(this.$refs.megawan.tocdo_cir_moi_selected)
            } else {
              await this.changeTocDoCirWan(this.$refs.megawan.tocdo_cir_moi_selected)
            }
            if (this.$refs.megawan.loaithietbi_moi_selected != item.THIETBIDC_ID) {
              this.$refs.megawan.loaithietbi_moi_selected = item.THIETBIDC_ID
              await this.changeLoaiTBWanMoi(this.$refs.megawan.loaithietbi_moi_selected)
            } else {
              await this.changeLoaiTBWanMoi(this.$refs.megawan.loaithietbi_moi_selected)
            }
            this.vhuong_kn = item.HUONG_KN != null ? item.HUONG_KN.toString() : ""
            this.$refs.megawan.trangbi_moi_selected = item.TRANGBI_ID != null ? item.TRANGBI_ID : 3
            await this.changeTrangBiMoi(this.$refs.megawan.trangbi_moi_selected)
            this.$refs.thongTinPhieuYeuCau.input_ma_cng = item.MA_PL != null ? item.MA_PL.toString() : ""
            this.cuoc_tc = item.CUOC_TC != null ? item.CUOC_TC : 0
            this.cuoc_tk = item.CUOC_TK != null ? item.CUOC_TK : 0

            if (item.MADOICAP != null) {
              this.madoicap_mgw = Number(item.MADOICAP)
            } else {
              this.madoicap_mgw = 0
            }
            await this.$refs.megawan.HienThiTTMoRong_HD(item)

            this.$refs.thongTinKhachHang.checkSoCVEnabled = true
            if (item.MA_CV != null) {
              this.$refs.thongTinKhachHang.checkSoCV = true
              this.$refs.thongTinKhachHang.changeCheckCV()
              this.$refs.thongTinKhachHang.input_socv = item.MA_CV.toString()
              // sau check lại chưa rõ format item.NGAY_CV
              this.$refs.thongTinKhachHang.ngay_cv = item.NGAY_CV != null ? moment(item.NGAY_CV).format("DD/MM/YYYY") : moment(new Date()).format("DD/MM/YYYY")
            }
          } else if (dichvuvt_id == DichVuVienThong.TSL) {
            if (this.$refs.kenhThuRieng.tocdo_moi_selected != item.TOCDO_ID) {
              this.$refs.kenhThuRieng.isChangeTocDo = false
              this.$refs.kenhThuRieng.tocdo_moi_selected = item.TOCDO_ID
              await this.changeTocDoKenhTSLMoi(this.$refs.kenhThuRieng.tocdo_moi_selected)
            } else {
              await this.changeTocDoKenhTSLMoi(this.$refs.kenhThuRieng.tocdo_moi_selected)
            }
            this.$refs.thongTinPhieuYeuCau.input_ma_cng = item.MA_PL != null ? item.MA_PL.toString() : ""
            this.$refs.thongTinKhachHang.checkSoCVEnabled = true
            if (item.MA_CV) {
              this.$refs.thongTinKhachHang.checkSoCV = true
              this.$refs.thongTinKhachHang.changeCheckCV()
              this.$refs.thongTinKhachHang.input_socv = item.MA_CV.toString()
              // sau check lại chưa rõ format item.NGAY_CV
              this.$refs.thongTinKhachHang.ngay_cv = item.NGAY_CV != null ? moment(item.NGAY_CV).format("DD/MM/YYYY") : moment(new Date()).format("DD/MM/YYYY")
            }
            this.$refs.kenhThuRieng.trangbi_moi_selected = item.TRANGBI_ID != null ? item.TRANGBI_ID : 3
            await this.changeTrangBiMoi(this.$refs.kenhThuRieng.trangbi_moi_selected)
            await this.$refs.kenhThuRieng.HienThiTTMoRong_HD(item)

            await this.HienThiTTDiaChi_TSL()
            // "DONVI_LOAIDV_DAU":[
            //     {
            //         "DONVI_ID":872,
            //         "TEN_DV":"Tổ tổng hợp"
            //     },
            this.ds_loaidv_dau = result.RESULT.DONVI_LOAIDV_DAU ? result.RESULT.DONVI_LOAIDV_DAU : []
            this.donvi_dau_id = result.RESULT.DONVI_DAU_ID ? Number(result.RESULT.DONVI_DAU_ID) : 0
            this.ds_loaidv_cuoi = result.RESULT.DONVI_LOAIDV_CUOI ? result.RESULT.DONVI_LOAIDV_CUOI : []
            this.donvi_cuoi_id = result.RESULT.DONVI_CUOI_ID ? Number(result.RESULT.DONVI_CUOI_ID) : 0
            this.tram_dv_dau = (result.RESULT.TRAMVTDAU && result.RESULT.TRAMVTDAU != "") ? Number(result.RESULT.TRAMVTDAU) : 0
            this.tram_dv_cuoi = (result.RESULT.TRAMVTCUOI && result.RESULT.TRAMVTCUOI != "") ? Number(result.RESULT.TRAMVTCUOI) : 0

            for (let i = 0; i < result.RESULT.TT_HDTB.length; i++) {
              if (result.RESULT.TT_HDTB[i].DAUCUOI_ID == 1) {
                // Nếu là điểm đầu
                this.$refs.kenhThuRieng.lcapdau_moi_selected = result.RESULT.TT_HDTB[i].LOAICAP_ID ? result.RESULT.TT_HDTB[i].LOAICAP_ID : null
                this.diachidau_id = result.RESULT.TT_HDTB[i].DIACHI_ID ? result.RESULT.TT_HDTB[i].DIACHI_ID : 0

                this.Lng_DD = result.RESULT.TT_HDTB[i].KINHDO_LD ? result.RESULT.TT_HDTB[i].KINHDO_LD : 0
                this.Lat_DD = result.RESULT.TT_HDTB[i].VIDO_LD ? result.RESULT.TT_HDTB[i].VIDO_LD : 0

                this.$refs.kenhThuRieng.input_diachi_dau_moi = result.RESULT.TT_HDTB[i].DIACHI ? result.RESULT.TT_HDTB[i].DIACHI.toString() : ""
                this.ma_tb_dau = result.RESULT.TT_HDTB[i].MA_TB_SUB ? result.RESULT.TT_HDTB[i].MA_TB_SUB.toString() : ""
              } else {
                this.$refs.kenhThuRieng.lcapcuoi_moi_selected = result.RESULT.TT_HDTB[i].LOAICAP_ID ? result.RESULT.TT_HDTB[i].LOAICAP_ID : null
                this.diachicuoi_id = result.RESULT.TT_HDTB[i].DIACHI_ID ? result.RESULT.TT_HDTB[i].DIACHI_ID : 0

                this.Lng_DC = result.RESULT.TT_HDTB[i].KINHDO_LD ? result.RESULT.TT_HDTB[i].KINHDO_LD : 0
                this.Lat_DC = result.RESULT.TT_HDTB[i].VIDO_LD ? result.RESULT.TT_HDTB[i].VIDO_LD : 0

                this.$refs.kenhThuRieng.input_diachi_cuoi_moi = result.RESULT.TT_HDTB[i].DIACHI ? result.RESULT.TT_HDTB[i].DIACHI.toString() : ""
                this.ma_tb_cuoi = result.RESULT.TT_HDTB[i].MA_TB_SUB ? result.RESULT.TT_HDTB[i].MA_TB_SUB.toString() : ""
              }
            }
          } else if (dichvuvt_id == DichVuVienThong.GPHONE) {

          } else if (dichvuvt_id == DichVuVienThong.IMS) {
            await this.$refs.ims.HienThiTTMoRong_HD(item)
          }
        }
      } else if (result != null && result.ERROR_CODE == 0) {
        this.$toast.error(result.MESSAGE)
      }
    },
    async LAY_DS_ISDN_THEO_THUEBAO_ID (thuebao_id) {
      this.isdn_sochinh = false
      this.matb_isdn_chinh = ""
      this.ds_isdn = await this.lay_ds_isdn_theo_thuebao_id(thuebao_id)

      for (let i = 0; i < this.ds_isdn.length; i++) {
        if (this.ds_isdn[i].thuebao_id != null && Number(this.ds_isdn[i].thuebao_id) == thuebao_id) {
          this.isdn_sochinh = true
          await this.$refs.coDinh.ISDN_LOAISO("Số ISDN chính", LOAISO_ISDN.SOCHINH)
        } else {
          await this.$refs.coDinh.ISDN_LOAISO("Số ISDN phụ", LOAISO_ISDN.SOPHU)
        }
        if (this.ds_isdn[i].thuebao_cha_id == null || this.ds_isdn[i].thuebao_cha_id == "") {
          this.matb_isdn_chinh = this.ds_isdn[i].ma_tb != null ? this.ds_isdn[i].ma_tb : ""
        }
      }
    },
    async layTongTien () {
      if (this.dataSources.length == 0) {
        return "0"
      }
      var tong = 0
      for (let i = 0; i < this.dataSources.length; i++) {
        if (this.dataSources[i].TONGTIEN != null) {
          tong += Number(this.dataSources[i].TONGTIEN)
        }
      }
      return tong.toString()
    },
    async onEnterMaGD () {
      try {
        this.loading(true)
        await this.hopdong_theo_ma_gd()
      } catch (ex) {
        this.$toast.error("Có lỗi lấy hợp đồng từ api" + ex.message)
        console.log(ex)
      } finally {
        this.loading(false)
      }
    },
    async HT_DS_KhuyenMai_Combobox () {
      //
      var itocdo_id = 0
      var itrangbi_id = 0// Default: KH tự trang bị
      var imuccuoc_id = 0
      var iloaitb_id = 0
      var idoituong_id = 0

      let thongtinyeucau = await this.$refs.thongTinPhieuYeuCau.getThongTinPhieu()

      let mucuoc_tb_id = thongtinyeucau.muoccuoc_tb_selected
      let mucuoc_tb = thongtinyeucau.ds_muccuoc_tb.find(x => x.mucuoctb_id == mucuoc_tb_id)

      if (thongtinyeucau.ds_kieu_yc.length == 0) {
        return
      }
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        let thongtincodinh = await this.$refs.coDinh.getThongTin()
        if (thongtincodinh.ds_doituong.length == 0) {
          return
        }
        idoituong_id = thongtincodinh.doituong_moi_selected != null ? thongtincodinh.doituong_moi_selected : 0
        itocdo_id = 0
        if (thongtincodinh.ds_trangbi.length == 0) {
          return
        }
        itrangbi_id = thongtincodinh.trangbi_moi_selected != null ? Number(thongtincodinh.trangbi_moi_selected) : 0

        if (thongtincodinh.loaihinh_tb_moi_selected != null && (mucuoc_tb == undefined || !mucuoc_tb.tenmuccuoc.includes("."))) {
          iloaitb_id = Number(thongtincodinh.loaihinh_tb_moi_selected)
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.GPHONE) {
        // itocdo_id = 0;
        // if (cboTrangBiGPmoi.Items.Count <= 0) return;
        // itrangbi_id = Convert.ToInt32(cboTrangBiGPmoi.SelectedValue);
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        if (this.$refs.diDong.ds_goicuoc_dd_moi.length == 0) {
          return
        }
        itocdo_id = this.$refs.diDong.goicuoc_dd_moi_selected != null ? Number(this.$refs.diDong.goicuoc_dd_moi_selected) : 0
        if (this.$refs.diDong.ds_trangbi.length == 0) {
          return
        }
        itrangbi_id = this.$refs.diDong.trangbi_moi_selected != null ? Number(this.$refs.diDong.trangbi_moi_selected) : 0
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        if (this.$refs.bangRongCoDinh.ds_tocdo.length == 0) {
          return
        }
        if (this.$refs.bangRongCoDinh.ds_trangbi.length == 0) {
          return
        }
        if (this.$refs.bangRongCoDinh.ds_muccuoc.length == 0) {
          return
        }
        itocdo_id = this.$refs.bangRongCoDinh.tocdo_moi_selected != null ? Number(this.$refs.bangRongCoDinh.tocdo_moi_selected) : 0
        itrangbi_id = this.$refs.bangRongCoDinh.trangbi_moi_selected != null ? Number(this.$refs.bangRongCoDinh.trangbi_moi_selected) : 0
        imuccuoc_id = this.$refs.bangRongCoDinh.muccuoc_moi_selected != null ? Number(this.$refs.bangRongCoDinh.muccuoc_moi_selected) : 0
        if (this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected != null && (mucuoc_tb == undefined || !mucuoc_tb.tenmuccuoc.includes("."))) {
          iloaitb_id = Number(this.$refs.bangRongCoDinh.loaihinh_tb_moi_selected)
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.MEGAWAN || this.dichvu_vt_selected == DichVuVienThong.METRONET) {
        if (this.$refs.megawan.ds_tocdo.length == 0) {
          return
        }
        itocdo_id = this.$refs.megawan.tocdo_cir_moi_selected != null ? this.$refs.megawan.tocdo_cir_moi_selected : 0
        if (this.$refs.megawan.ds_trangbi.length == 0) {
          return
        }
        itrangbi_id = this.$refs.megawan.trangbi_moi_selected != null ? this.$refs.megawan.trangbi_moi_selected : 0
        if (this.$refs.megawan.loaihinh_tb_moi_selected != null && (mucuoc_tb == undefined || !mucuoc_tb.tenmuccuoc.includes("."))) {
          iloaitb_id = Number(this.$refs.megawan.loaihinh_tb_moi_selected)
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        if (this.$refs.kenhThuRieng.ds_tocdo.length == 0) {
          return
        }
        itocdo_id = this.$refs.kenhThuRieng.tocdo_moi_selected != null ? Number(this.$refs.kenhThuRieng.tocdo_moi_selected) : 0
        if (this.$refs.kenhThuRieng.ds_trangbi.length == 0) {
          return
        }
        itrangbi_id = this.$refs.kenhThuRieng.trangbi_moi_selected != null ? this.$refs.kenhThuRieng.trangbi_moi_selected : 0
        if (this.$refs.kenhThuRieng.loaihinh_tb_moi_selected != null && (mucuoc_tb == undefined || !mucuoc_tb.tenmuccuoc.includes("."))) {
          iloaitb_id = Number(this.$refs.kenhThuRieng.loaihinh_tb_moi_selected)
        }
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        if (this.$refs.ims.loaihinh_tb_moi_selected != null && (mucuoc_tb == undefined || !mucuoc_tb.tenmuccuoc.includes("."))) {
          iloaitb_id = Number(this.$refs.ims.loaihinh_tb_moi_selected)
        }
      }

      // load ds khuyến mãi
      let dsKhuyenMai = await this.lay_khuyenmai_hdtb({
        vloaitb_id: iloaitb_id,
        vkieuld_id: thongtinyeucau.kieu_yc_selected != null ? Number(thongtinyeucau.kieu_yc_selected) : 0,
        vphuongld_id: this.dataDiaChiLD.phuong_id,
        vkhoanmuctt_id: KHOANMUC_TT.KMTT_DATMOI,
        vphold_id: this.dataDiaChiLD.pho_id,
        vapld_id: this.dataDiaChiLD.ap_id,
        vkhuld_id: this.dataDiaChiLD.khu_id,
        vtrangbi_id: itrangbi_id,
        vdoituong_id: this.doituong_id,
        vtocdo_id: itocdo_id,
        vmuccuoc_id: imuccuoc_id,
        vloaikm: 0
      })

      // test
      // let dsKhuyenMai=await this.lay_khuyenmai_hdtb({
      //     vloaitb_id: 58,
      //     vkieuld_id: 550,
      //     vphuongld_id: 182,
      //     vkhoanmuctt_id: 0,
      //     vphold_id: 3330,
      //     vapld_id: 0,
      //     vkhuld_id: 0,
      //     vtrangbi_id: 4,
      //     vdoituong_id: 1,
      //     vtocdo_id: 532,
      //     vmuccuoc_id: 1,
      //     vloaikm: 1
      // })

      // CHITIETKM_ID: 15010
      // CUOC_SD: 0
      // CUOC_TB: 0
      // DATCOC_CSD: 0
      // KHUYENMAI_ID: 11099
      // KIEU_YC: 1
      // SOTHANG_TG: 0
      // THANG_HUONGDC: undefined
      // THANG_HUONGKM: undefined
      // TIEN_SD: 180000
      // TIEN_TB: 0
      // TIEN_TD: 0
      // TYLE_KMLD: 0
      // TYLE_SD: 0
      // TYLE_TB: 0
      // this.dsKM_HDTB=[
      //     {
      //         PHANVUNG_ID:26,
      //         HDTB_ID:4248273,
      //         KHUYENMAI_ID:11108,
      //         CHITIETKM_ID:13563,
      //         KHUYENMAI_ID_NEO:null,
      //         KIEU_YC:1,
      //         THANG_BD:201912,
      //         THANG_KT:202006,
      //         THANG_HUONGKM:7,
      //         THANG_HUONGDC:7,
      //         THANG_BDDC:201912,
      //         THANG_KTDC:202006,
      //         THANG_BDTG:null,
      //         THANG_KTTG:null,
      //         TIEN_TG:0,
      //         SOTHANG_TG:0,
      //         TYLE_KMLD:0,
      //         TYLE_TB:0,
      //         TYLE_SD:0,
      //         DATCOC_CSD:501600,
      //         TIEN_TD:71657,
      //         RKM_ID:null,
      //         FKEY:null,
      //         TIEN_SD:0,
      //         CUOC_SD:0,
      //         TIEN_TB:18857,
      //         CUOC_TB:0,
      //         NGAY_HUY:null,
      //         NGAY_BD:null,
      //         NGAY_KT:null,
      //         NGAY_THOAI:null
      //     }
      // ]

      await this.$refs.thongTinPhieuYeuCau.khoiTaoDsKhuyenMai(dsKhuyenMai)
      // load ds trả trước
      let dsTraTruoc = await this.lay_khuyenmai_hdtb({
        vloaitb_id: iloaitb_id,
        vkieuld_id: thongtinyeucau.kieu_yc_selected != null ? Number(thongtinyeucau.kieu_yc_selected) : 0,
        vphuongld_id: this.dataDiaChiLD.phuong_id,
        vkhoanmuctt_id: KHOANMUC_TT.KMTT_DATMOI,
        vphold_id: this.dataDiaChiLD.pho_id,
        vapld_id: this.dataDiaChiLD.ap_id,
        vkhuld_id: this.dataDiaChiLD.khu_id,
        vtrangbi_id: itrangbi_id,
        vdoituong_id: this.doituong_id,
        vtocdo_id: itocdo_id,
        vmuccuoc_id: imuccuoc_id,
        vloaikm: 1
      })
      // let dsTraTruoc=await this.lay_khuyenmai_hdtb({
      //     vloaitb_id: 58,
      //     vkieuld_id: 550,
      //     vphuongld_id: 182,
      //     vkhoanmuctt_id: 0,
      //     vphold_id: 3330,
      //     vapld_id: 0,
      //     vkhuld_id: 0,
      //     vtrangbi_id: 4,
      //     vdoituong_id: 1,
      //     vtocdo_id: 532,
      //     vmuccuoc_id: 1,
      //     vloaikm: 1
      // })
      await this.$refs.thongTinPhieuYeuCau.khoiTaoDsTraTruoc(dsTraTruoc)
    },
    async initDuLieu () {
    //   let endPath = this.$router.currentRoute.path.split("/").pop()
    //   if (endPath == "DetailChangeType") {
    //     // Tra cứu biến động chuyển đổi
    //     // [UR2.8.011] - Chi tiết chuyển đổi loại hình
    //     this.iKieu = 1
    //   } else if (endPath == "UpdateChangePromotion") {
    //     // Sau này bổ sung path
    //     // [UR2.7.003] - Sửa khuyến mại thay đổi loại hình thuê bao
    //     this.iKieu = 2
    //   } else {
    //     // default: ChangeSubsType
    //     // [UR2.1.035] - Thay đổi loại hình thuê bao
    //     this.iKieu = 0
    //   }
      // let iKieu = this.$route.query.iKieu
      // this.iKieu=iKieu!=undefined?Number(iKieu):0
      this.iKieu = 2
      let iKenhTN = this.$route.query.iKenhTN
      this.iKenhTN = iKenhTN != undefined ? Number(iKenhTN) : 1
      console.log("iKieu: " + this.iKieu)
      console.log("iKenhTN: " + this.iKenhTN)
      //
      this.loading(true)
      //
      this.ds_tien_khoanmuc = await this.sp_lay_tienkhoanmuc_theo_loaihd(LoaiHopDong.CHUYENDOI_LH)
      // khởi tạo tham số
      await this.khoiTaoThamSo()
      // Khởi tạo loại hình tb
      // lay_ds_loaihinh_tb
      this.ds_loaihinh_tb = await this.lay_ds_loaihinh_tb()

      // Hiển thị Kiểu PL (cboKieu_HD)
      let result = await this.lay_danhmuc_cdlh_tt_chung("KIEU_HD|GOI_KT")
      if (result != null && result.kieu_hd) {
        this.ds_kieu_pl = result.kieu_hd
        if (this.ds_kieu_pl.length > 0) {
          this.kieu_pl_selected = this.ds_kieu_pl[0].kieuhd_id
        }
        this.ds_goi_kt = result.goi_kt
      }
      this.dichvuvt_id = this.dichvu_vt_selected
      //
      await this.$refs.thongTinPhieuYeuCau.initDuLieu(this.ds_loaihinh_tb, this.ds_goi_kt)
      await this.$refs.thongTinKhachHang.initDuLieu()
      //
      if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
        await this.$refs.coDinh.initDuLieu(this.ds_loaihinh_tb)
        await this.$refs.coDinh.changeDoiTuongMoi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {
        await this.$refs.diDong.initDuLieu(this.ds_loaihinh_tb)
      } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
        // băng rộng cố định
        await this.$refs.bangRongCoDinh.initDuLieu(this.ds_loaihinh_tb)
        await this.$refs.bangRongCoDinh.changeDoiTuongMoi()
        if (this.thamso.nhap_tocdo_thuc_adsl == 0) {
          this.$refs.bangRongCoDinh.enabledCheckTocDoThuc = false
        } else {
          this.$refs.bangRongCoDinh.enabledCheckTocDoThuc = true
        }
        this.$refs.bangRongCoDinh.checkTocDoThuc = false
      } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
        await this.$refs.ims.initDuLieu(this.ds_loaihinh_tb)
        await this.$refs.ims.changeDoiTuongMoi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
        await this.$refs.kenhThuRieng.initDuLieu(this.ds_loaihinh_tb)
        await this.$refs.kenhThuRieng.changeDoiTuongMoi()
      } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
        await this.$refs.megawan.initDuLieu(this.ds_loaihinh_tb, this.dichvu_vt_selected)
        await this.$refs.megawan.changeDoiTuongMoi()
      }
      //
      await this.$refs.muaThietBi.initDuLieu()
      await this.$refs.chiTietTienTheoKhoanMuc.initDuLieu()

      // call trigger changeLoaiHinh tb cũ
      await this.$refs.thongTinPhieuYeuCau.changeLoaiHinhTBCu()

      await this.HT_DS_KhuyenMai_Combobox()

      if (this.iKieu == 1) {
        this.header.title = "TRA CỨU THAY ĐỔI LOẠI HÌNH THUÊ BAO"
      } else if (this.iKieu == 2) {
        this.header.title = "SỬA KM THAY ĐỔI LOẠI HÌNH THUÊ BAO"
      } else {
        this.header.title = "THAY ĐỔI LOẠI HÌNH THUÊ BAO"
      }

      await this.visibleToolStripMenuAndControl(this.iKieu == 0)

      if (this.thamso.khong_thaydoi_diachi_ld == 1) {
        this.$refs.thongTinKhachHang.enabledBtnDiaChiLD(false)
      } else {
        this.$refs.thongTinKhachHang.enabledBtnDiaChiLD(true)
      }
      //
      this.loading(false)
      // Lấy thông tin người dùng
      this.tt_nd = await this.thongtin_nguoidung()
      console.log("tt_nd", this.tt_nd)

      // gọi từ form ngoài
      await this.LoadTuHopDongTuVan()
    },
    async visibleToolStripMenuAndControl (visible) {
      for (let i = 0; i < this.actions.length; i++) {
        if (this.actions[i].id != "goidadv") {
          this.actions[i].visible = visible
        }
      }
      this.actions[this.actions.findIndex(x => x.id == "sua_km")].visible = this.iKieu == 2
      this.actions[this.actions.findIndex(x => x.id == "goidadv")].visible = this.iKieu == 2
      // Handle cho iKieu=1(Tra cứu) hoặc 2(Sửa KM)
      // Kiểm tra nếu không có quyền thay đổi tốc độ khi sửa km thì không cho sửa
      this.checkQuyen = await this.kiemtra_quyen_nd(925)
      if ((this.iKieu == 1 || this.iKieu == 2) && this.checkQuyen == 0) {
        // cboDichVuVT.Enabled = visible;
        this.$refs.muaThietBi.enabledMuaTbi = visible
        this.$refs.thongTinKhachHang.enabledDiaChiTB = visible
        this.$refs.thongTinKhachHang.enabledDiaChiLD = visible
        this.$refs.thongTinPhieuYeuCau.disableKieuLD = !visible
        if (this.dichvu_vt_selected == DichVuVienThong.CO_DINH) {
          this.$refs.coDinh.visibleToolStripMenuAndControl(visible)
        } else if (this.dichvu_vt_selected == DichVuVienThong.DI_DONG) {

        } else if (this.dichvu_vt_selected == DichVuVienThong.ADSL || this.dichvu_vt_selected == DichVuVienThong.ANTOAN_BAOMAT_TT) {
          this.$refs.bangRongCoDinh.visibleToolStripMenuAndControl(visible)
        } else if (this.dichvu_vt_selected == DichVuVienThong.IMS) {
          this.$refs.ims.visibleToolStripMenuAndControl(visible)
        } else if (this.dichvu_vt_selected == DichVuVienThong.TSL) {
          this.$refs.kenhThuRieng.visibleToolStripMenuAndControl(visible)
        } else if (this.dichvu_vt_selected == DichVuVienThong.METRONET || this.dichvu_vt_selected == DichVuVienThong.MEGAWAN) {
          this.$refs.megawan.visibleToolStripMenuAndControl(visible)
        }
      }
    },
    // Làm bổ sung cho bên ngoài task khác gọi, chưa rõ chức năng làm gì
    async LoadTuHopDongTuVan () {
      // sp_lay_thongtin_hdkh_tuvan
      let phdkh_cha_id = this.$route.query.hdkh_cha_id
      if (phdkh_cha_id) {
        this.setActiveActions(1)
        this.hdkh_cha_id = phdkh_cha_id
        let result = await this.sp_lay_thongtin_hdkh_tuvan(this.hdkh_cha_id, 0)
        if (result && result.ERROR_CODE == 1) {
          let item = result.RESULT
          this.$refs.thongTinKhachHang.input_ten_kh = item.TEN_KH ? item.TEN_KH.toString() : ""
          this.$refs.thongTinKhachHang.input_diachi_kh = item.DIACHI_KH ? item.DIACHI_KH.toString() : ""
          this.$refs.thongTinKhachHang.input_sdt = item.SO_DT ? item.SO_DT.toString() : ""

          this.dataDiaChiLD.tinh_id = item.TINH_ID ? item.TINH_ID : 0
          this.dataDiaChiLD.quan_id = item.QUAN_ID ? item.QUAN_ID : 0
          this.dataDiaChiLD.phuong_id = item.PHUONG_ID ? item.PHUONG_ID : 0
          this.dataDiaChiLD.pho_id = item.PHO_ID ? item.PHO_ID : 0
          this.dataDiaChiLD.khu_id = item.KHU_ID ? item.KHU_ID : 0
          this.dataDiaChiLD.ap_id = item.AP_ID ? item.AP_ID : 0
          this.dataDiaChiLD.sonha = item.SONHA ? item.SONHA.toString() : ""

          this.dataDiaChiLD.kinhdo = item.KINHDO ? item.KINHDO : 0
          this.dataDiaChiLD.vido = item.VIDO ? item.VIDO : 0

          this.khachhang_id = item.KHACHHANG_ID ? item.KHACHHANG_ID : 0
        }
      }
    }
  },

  mounted () {
    this.setActiveActions(-1)
    setTimeout(() => {
      this.initDuLieu()
    }, 500)
  }
}
</script>
<style scoped>
.disabled {
    pointer-events: none;
    color:lightgray;
    border-color:lightgray;
}
</style>
