<template src="./frmKhaoSatKH_BaoDuong.html">

</template>
<style scope>
.e-grid .e-gridheader
{
    position: -webkit-sticky;
    position: sticky;
    top: 0px;
    z-index: 1;
}
</style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import API from "../api/TraCuuThueBaoKhaoSatApi";
import XacNhanNVKTAPI from "../api/XacNhanPhanHoiNVKTApi";
import frmLichSuBaoHong from "./frmLichSuBaoHong.vue"
import frmLichSuHenBaoHong from "./frmLichSuHenBaoHong.vue"
import moment from "moment";
import cauHoiKS from "./frmCauHoiKS.vue";
import YesNoModal from "../utils/YesNoModal.vue";
import Vue from 'vue'
import {isNullOrEmpty, kiemTraCauHoiKHL} from "../utils/functions/validators.js"
import {CSKH_CONST, FIMS_LYDO_KTL, LOAI_NV} from "../utils/functions/constants.js"
// import TraCuuTBKSModal from "../DanhSachPhieuKhaoSatOutbound/popup/TraCuuTBKSModal.vue"
const LOAI_LAN_GOI = {
    HOP_DONG: 1,
    BAO_HONG: 2
};
export default {
    name: "frmKhaoSatKH_BaoDuong",
    components: {
        frmLichSuBaoHong,
        frmLichSuHenBaoHong,
        cauHoiKS,
        confirmPopup: YesNoModal,
        // appTraCuuTBKSModal: TraCuuTBKSModal
    },
    props: {
        // baoHongId: {
        //     type: Number,
        //     default: 0
        // },
        // tbtlId: {
        //     type: Number,
        //     default: 0
        // },
        // thueBaoId: {
        //     type: Number,
        //     default: 0
        // },
        // hdkhId: {
        //     type: Number,
        //     default: 0
        // },
        // giaoPhieuKsId: {
        //     type: Number,
        //     default: 0
        // }
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },

            //
            baoHongId: 0,
            tbtlId: 0,
            thueBaoId: 0,
            hdkhId: 0,
            giaoPhieuKsId: 0,

            vDaCapNhap: false,
            loaiNvKs: LOAI_NV.NHANVIEN_TTVT_KHAOSAT_KH,
            hinhThucId: 16,

            //
            pCboLyDoKTL: "",
            pThamSoMacDinh: {
                BATBUOC_TRALOI_ALL_CAUHOI: false,
                KIEMTRA_CAUHOI_KHL: false,
                FIMS_DIEU_XN_DONVI: false,
                OUTBOUND_KHONG_DIEULAI: false,
                OB_KHONGTL_KTC1: false,
                KHONGTL_CHUYEN_OB_TUDONG: false,
                OB_CHON_NHIEU_LYDO_KTL: false,
                CHOPHEP_SUA_OB_KHONG_TL: false,
                LAN_KS_TOIDA: false,
                KT_DAMBAO_CKCL_KH: false
            },
            pTxtMaTB: "",
            pTxtLoaiHinh: "",
            pTxtTBCungCap: "",
            pTxtTenTB: "",
            pTxtDiaChiLD: "",
            pTxtDienThoaiLH: "",
            pTxtTenNguoiTL: "",
            pTxtSoLH: "",
            pTxtGhiChu: "",

            pTxtVPXL: "",
            pTxtNgayBH: "",
            pTxtNgayNT: "",
            pTxtNguoiBH: "",
            pTxtSoDTBH: "",
            pTxtTinhTrang: "",
            pTxtNguyenNhan: "",
            pTxtGhiChuNT: "",

            pChkKTL: false,
            pchkHenKS_Tu: false,
            pchkHenKS_Den: false,
            today: new Date(),
            pDtpHenKS_Tu: new Date(),
            pDtpHenKS_Den: new Date(),

            pDsThongTinBaoHong: [],
            pIsSuaPhieu: false,
            pIsShowThongBaoCoHen: true,

            pNgayKS: new Date(),
            pIsNew: false,
            pIsShowCKCL: false,
            pbaohong_ckcl: false,
            pSoLanHongTrong30Ngay: 0,

            pDsLanGoiChuaTraLoi: [],
            pDsCauHoi: [],

            pBtnDieuLaiEnabled: false,
            pKhaoSat: {
                dsCauHoiChinh: [],
                dsCauHoiPhu: [],
                dsCauTraLoi: []
            },
            pChiTietTraLoi: [],
            pDsLyDoKTL: [],
            fakeData: {
                cauHoi: [],
                traLoi: []
            },


            pMoPhieu: 0,
            pKieuKs: 0,
            styles: {
                tbh: {},
                dslh: {}
            },

            pchkHenKS_Tu_Disabled: false,
            pchkHenKS_Den_Disabled: false,
        };
    },
    computed: {
        ketQuaKS() {
            return this.$refs["cauHoiKS"].ketQuaKS
        },
        cauHoiKSPhu() {
            return this.$refs["cauHoiKS"].dsCauHoiPhu
        },
        cauHoiKSChinh() {
            return this.$refs["cauHoiKS"].dsCauHoiChinh
        },
        cauHoiKSChau() {
            return this.$refs["cauHoiKS"].dsCauHoiChau
        },
        xacNhanDieuLai() {
          return "Bạn có chắc chắn muốn điều hỏng thuê bao " + this.pTxtMaTB + " ?"
        },
        lichSuKSUrl() {
            return `/#/cskh/TraCuuThueBaoKhaoSat?maTB=${this.pTxtMaTB}`
        }
    },
    methods: {
        ...mapActions("cskhCommon", [
            "getThamSoMacDinh",
            "getListThamSoMacDinh"
        ]),
        async dialogOpen() {
            await this.onShow();
        },
        openDialog() {
            this.$refs.frmKhaoSatKH_BaoDuong.show();
        },
        closeDialog() {
            this.$emit("onHideKhaoSat");
            this.$refs.frmKhaoSatKH_BaoDuong.hide();
        },
        async onShow() {
            try {
                this.$root.showLoading(true);
                this.clearData();

                this.$nextTick(() => this.$refs.txtTenNguoiTL.select());
                await Promise.all([
                    this.getThamSoMacDinh(),
                    this.getDsLyDoKTL(),
                ])
                await this.hienThiThongTinTB()
                await this.$refs.cauHoiKS.loadData()

                this.pBtnDieuLaiEnabled = this.pIsNew;

                this.matchHeight()
            } catch (e) {
                console.log(e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        async getThamSoMacDinh() {
            try {
                let dsThamSoMacDinh = await this.getListThamSoMacDinh({
                    dsMaTs: Object.getOwnPropertyNames(
                        this.pThamSoMacDinh
                    ).join()
                });
                if (dsThamSoMacDinh && dsThamSoMacDinh.length > 0) {
                    let BATBUOC_TRALOI_ALL_CAUHOI = dsThamSoMacDinh.find(
                        x => x.MA_TS === "BATBUOC_TRALOI_ALL_CAUHOI"
                    );
                    this.pThamSoMacDinh.BATBUOC_TRALOI_ALL_CAUHOI =
                        (BATBUOC_TRALOI_ALL_CAUHOI ? true : false) &&
                        BATBUOC_TRALOI_ALL_CAUHOI.TEN_TS == "1";

                    let KIEMTRA_CAUHOI_KHL = dsThamSoMacDinh.find(
                        x => x.MA_TS === "KIEMTRA_CAUHOI_KHL"
                    );
                    this.pThamSoMacDinh.KIEMTRA_CAUHOI_KHL =
                        (KIEMTRA_CAUHOI_KHL ? true : false) &&
                        KIEMTRA_CAUHOI_KHL.TEN_TS == "1";

                    let FIMS_DIEU_XN_DONVI = dsThamSoMacDinh.find(
                        x => x.MA_TS === "FIMS_DIEU_XN_DONVI"
                    );
                    this.pThamSoMacDinh.FIMS_DIEU_XN_DONVI =
                        (FIMS_DIEU_XN_DONVI ? true : false) &&
                        FIMS_DIEU_XN_DONVI.TEN_TS == "1";

                    let OUTBOUND_KHONG_DIEULAI = dsThamSoMacDinh.find(
                        x => x.MA_TS === "OUTBOUND_KHONG_DIEULAI"
                    );
                    this.pThamSoMacDinh.OUTBOUND_KHONG_DIEULAI =
                        (OUTBOUND_KHONG_DIEULAI ? true : false) &&
                        OUTBOUND_KHONG_DIEULAI.TEN_TS == "1";

                    let OB_KHONGTL_KTC1 = dsThamSoMacDinh.find(
                        x => x.MA_TS === "OB_KHONGTL_KTC1"
                    );
                    this.pThamSoMacDinh.OB_KHONGTL_KTC1 =
                        (OB_KHONGTL_KTC1 ? true : false) &&
                        OB_KHONGTL_KTC1.TEN_TS == "1";

                    let KHONGTL_CHUYEN_OB_TUDONG = dsThamSoMacDinh.find(
                        x => x.MA_TS === "KHONGTL_CHUYEN_OB_TUDONG"
                    );
                    this.pThamSoMacDinh.KHONGTL_CHUYEN_OB_TUDONG =
                        (KHONGTL_CHUYEN_OB_TUDONG ? true : false) &&
                        KHONGTL_CHUYEN_OB_TUDONG.TEN_TS == "1";

                    let OB_CHON_NHIEU_LYDO_KTL = dsThamSoMacDinh.find(
                        x => x.MA_TS === "OB_CHON_NHIEU_LYDO_KTL"
                    );
                    this.pThamSoMacDinh.OB_CHON_NHIEU_LYDO_KTL =
                        (OB_CHON_NHIEU_LYDO_KTL ? true : false) &&
                        OB_CHON_NHIEU_LYDO_KTL.TEN_TS == "1";

                    let CHOPHEP_SUA_OB_KHONG_TL = dsThamSoMacDinh.find(
                        x => x.MA_TS === "CHOPHEP_SUA_OB_KHONG_TL"
                    );
                    this.pThamSoMacDinh.CHOPHEP_SUA_OB_KHONG_TL =
                        (CHOPHEP_SUA_OB_KHONG_TL ? true : false) &&
                        CHOPHEP_SUA_OB_KHONG_TL.TEN_TS == "1";

                    let LAN_KS_TOIDA = dsThamSoMacDinh.find(
                        x => x.MA_TS === "LAN_KS_TOIDA"
                    );
                    this.pThamSoMacDinh.LAN_KS_TOIDA =
                        (LAN_KS_TOIDA ? true : false) &&
                        LAN_KS_TOIDA.TEN_TS == "1";

                    let KT_DAMBAO_CKCL_KH = dsThamSoMacDinh.find(
                        x => x.MA_TS === "KT_DAMBAO_CKCL_KH"
                    );
                    this.pThamSoMacDinh.KT_DAMBAO_CKCL_KH =
                        (KT_DAMBAO_CKCL_KH ? true : false) &&
                        KT_DAMBAO_CKCL_KH.TEN_TS == "1";
                }
            } catch (e) {
            } finally {
            }
        },
        async hienThiThongTinTB() {
            let data = {
                baoHongId: this.baoHongId
            };
            this.pDsThongTinBaoHong = [];
            try {
                let response = await API.getThongTinBaoHong(this.axios, data);
                if (response.data.error_code === "BSS-00000000") {
                    this.pDsThongTinBaoHong = response.data.data.data || [];
                    if ( this.pDsThongTinBaoHong && this.pDsThongTinBaoHong.length > 0 ) {
                        let record = this.pDsThongTinBaoHong[0];
                        this.baoHongId = record.BAOHONG_ID;
                        this.thueBaoId = record.THUEBAO_ID;

                        this.pTxtMaTB = record.MA_TB;
                        this.pTxtTenTB = record.TEN_TB;
                        this.pTxtDiaChiLD = record.DIACHI_LD;
                        this.pTxtDienThoaiLH = record.SO_DT_KH;
                        if (this.$root.token.getPhanVungID() != 26) {
                            this.pTxtSoLH = record.DIENTHOAI_LH;
                            this.pTxtTenNguoiTL = record.TEN_TB;
                        }
                        // Lấy tốc độ loại hình
                        this.pTxtLoaiHinh = record.DICHVUVT;
                        // End thông tin thuê bao
                        // Thông tin báo hỏng
                        this.pTxtNgayBH = record.NGAY_BH
                        this.pTxtNguoiBH = record.NGUOI_BH;
                        this.pTxtSoDTBH = record.DIENTHOAI_LH;

                        this.pTxtNgayNT = record.NGAY_HT
                        this.pTxtTinhTrang = record.TINHTRANG;
                        this.pTxtNguyenNhan = record.NGUYENNHAN;

                        this.pTxtGhiChuNT = record.GHICHU_XL;
                        this.pTxtVPXL = record.VPXL;

                        this.pSoLanHongTrong30Ngay = await this.getSoLanHongTrong30Ngay()
                        // let lsBaoHong = await this.getLichSuBaoHong();

                        // this.pIsShowThongBaoCoHen = lsBaoHong && lsBaoHong.length > 0;

                        //danh sách lần hỏi
                        this.pDsLanGoiChuaTraLoi = await this.getLanGoiChuaTraLoi( LOAI_LAN_GOI.BAO_HONG );
                        let dtTBTL = await this.getThongTinTBTraLoi( LOAI_LAN_GOI.BAO_HONG );

                        if ( dtTBTL && dtTBTL.length > 0 ) {
                            this.pIsNew = false
                            if (this.tbtlId == 0)
                                this.tbtlId = dtTBTL[0].TBTL_ID;

                            this.pTxtTenNguoiTL = dtTBTL[0].NGUOI_TL;
                            this.pTxtSoLH = dtTBTL[0].DIENTHOAI_LH;
                            this.pTxtGhiChu = dtTBTL[0].GHICHU;
                            this.pChkKTL = dtTBTL[0].TRANGTHAI == 0;
                            this.pNgayKS = dtTBTL[0].NGAY_HOI;

                            if (this.pChkKTL) {
                                this.pIsNew = true;
                                this.tbtlId = 0;

                                if (dtTBTL[0].LYDOKTL_ID) {
                                    this.pCboLyDoKTL = dtTBTL[0].LYDOKTL_ID;
                                }
                            }

                            // if (dtTBTL[0].)
                        } else {
                            this.pIsNew = true;
                            this.tbtlId = 0;
                        }
                    }
                }
            } catch (e) {
                console.log(e);
            }
        },
        async getSoLanHongTrong30Ngay() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-bao-hong/so-lan-bao-hong-30-ngay",
                    {
                        thueBaoId: this.thueBaoId,
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || 0;
                }
                return 0;
            } catch (e) {
                return 0;
            }
        },
        async getLichSuBaoHong() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/lich-su-hen-bao-hong",
                    {
                        baoHongId: this.baoHongId,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getDsLyDoKTL() {
            try {
                this.pDsLyDoKTL = []

                let response = await this.$root.context.get(
                    "/web-cskh/api/danh-muc/ly-do-ktl",
                    {}
                );
                if (response.error_code === "BSS-00000000") {
                    this.pDsLyDoKTL = response.data || [];
                    if (this.pDsLyDoKTL.length > 0) {
                        this.pCboLyDoKTL = this.pDsLyDoKTL[0].LYDOKTL_ID;
                    }
                }
            } catch (e) {
            }
        },
        async getLanGoiChuaTraLoi(loai) {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi",
                    {
                        id: this.baoHongId,
                        loai: loai,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getThongTinTBTraLoi(loai) {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-thue-bao-tra-loi",
                    {
                        id: this.baoHongId,
                        loai: loai,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        gridLanGoiChuaTraLoi_selectedRowChanged(dataItem) {

        },
        clearData() {
            this.pTxtMaTB = "";
            this.pTxtLoaiHinh = "";
            this.pTxtTBCungCap = "";
            this.pTxtTenTB = "";
            this.pTxtDiaChiLD = "";
            this.pTxtDienThoaiLH = "";
            this.pTxtTenNguoiTL = "";
            this.pTxtSoLH = "";
            this.pTxtGhiChu = "";
            this.pTxtVPXL = "";
            this.pTxtNgayBH = "";
            this.pTxtNgayNT = "";
            this.pTxtNguoiBH = "";
            this.pTxtSoDTBH = "";
            this.pTxtTinhTrang = "";
            this.pTxtNguyenNhan = "";
            this.pTxtGhiChuNT = "";

            this.pIsSuaPhieu = false;
            this.pDsThongTinBaoHong = [];

            this.pKhaoSat.dsCauHoiChinh = []
            this.fakeData.cauHoi = []
            this.fakeData.traLoi = []

            this.pChkKTL = false
            this.pCboLyDoKTL = ""

            this.pIsNew = false
            this.pIsShowCKCL = false
            this.pbaohong_ckcl = false
            this.pSoLanHongTrong30Ngay = 0
            this.pChkKTL = false
            this.pchkHenKS_Tu = false
            this.pchkHenKS_Den = false
            this.pDtpHenKS_Tu = new Date()
            this.pDtpHenKS_Den = new Date()
            this.pBtnDieuLaiEnabled = false
            this.vDaCapNhap = false
        },

        async onGhiLaiClicked() {
            try {
                if (this.pIsNew && !this.vDaCapNhap) {
                    this.vDaCapNhap = false;
                    if (await this.kiemTraDuLieu(true)) {
                        this.loading(true)
                        let data = this.taoDuLieu(true, false);
                        let rs = await this.$root.context.post( "/web-cskh/api/khao-sat-kh-bao-hong/ghi-lai", data );
                        if (rs.error_code === "BSS-00000000") {
                            if (rs.data.ketQua == 1) {
                                this.$root.toastSuccess( "Cập nhật thành công!" );
                                if (rs.data.msgError)
                                {
                                    let listMessages = rs.data.msgError.split("#")
                                    listMessages.forEach(message => {
                                        let pattern = /.*\d/g;
                                        let result = message.match(pattern)
                                        if (result && result.length > 0 && result[0].includes('1'))
                                        {
                                            this.$root.toastSuccess(message.replace(result[0], ""))
                                        }
                                        else
                                        {
                                            this.$root.toastError(message.replace(result[0], ""))
                                        }
                                    });
                                }
                                this.closeDialog();
                            } else {
                                this.$root.toastError(rs.data.msgError);
                            }
                        } else {
                            this.$root.toastError("Cập nhật thất bại");
                        }
                    }
                }
            }
            catch (e) {
                console.log(e)
                this.$root.toastError('Có lỗi ' + e)
            } finally {
                this.loading(false)
            }
        },
        focusInput(name) {
            this.$nextTick(() => this.$refs[name].select())
        },
        taoDuLieu(isThemMoi, isXuatLai) {
            let kq = this.ketQuaKS;
            let ttksId = 1
            if (this.pChkKTL)
            {
                ttksId = 1
                let soLanKTL = pDsLanGoiChuaTraLoi.filter(x => x.LYDO_KTL != '').length
                if (soLanKTL + 1 > this.LAN_KS_TOIDA)
                {
                    ttksId = 7
                }
                this.vDaCapNhap = true
            }
            else
            {
                ttksId = 6
                this.vDaCapNhap = true;
            }


            let data = {
                tbtlId: isThemMoi ? 0 :this.tbtlId,
                baoHongId: this.baoHongId,
                hdkhId: this.hdkhId,
                hinhThuc: 16,
                nguoiTl: this.pTxtTenNguoiTL,
                dienThoaiLh: this.pTxtSoLH,
                ghiChu: this.pTxtGhiChu,
                trangThai: isXuatLai ? 2 : (this.pChkKTL ? 0 : 1),
                lyDoKtlId: this.pChkKTL ? this.pCboLyDoKTL : 0,
                ttksId: ttksId,

                hdtbId: this.hdtbId,
                giuPhieu: 0,


                thueBaoId: this.thueBaoId,
                ngayBdKs: moment(new Date()).format("DD/MM/YYYY"),

                // gioHenTu: this.pchkHenKS_Tu
                //     ? moment(this.pDtpHenKS_Tu).format("DD/MM/YYYY")
                //     : "",
                // gioHenDen: this.pchkHenKS_Den
                //     ? moment(this.pDtpHenKS_Den).format("DD/MM/YYYY")
                //     : "",
                dsCauHoiId: kq.map(x => x.CAUHOI_ID).join("#"),
                dsTraLoiId: kq.map(x => x.TRALOI_ID).join("#"),
                dsNoiDung: JSON.stringify(kq.map(x => `${x.NOIDUNG}`)),
                isCnSoLienHe: 1,
                nguoiDungId: this.$root.token.getNguoiDungID()
            };
            return data;
        },
        async kiemTraDuLieu(isThemMoi) {
            if (isNullOrEmpty(this.pTxtTenNguoiTL) && this.pChkKTL == true) {
                this.$root.toastError('Chưa nhập tên người trả lời !')
                this.focusInput('txtTenNguoiTL')
                return false
            }
            if (isNullOrEmpty(this.pTxtSoLH)) {
                this.$root.toastError('Chưa nhập điện thoại liên hệ !')
                this.focusInput('txtSoLH')
                return false
            }
            if (isNaN(this.pTxtSoLH)) {
                this.$root.toastError('Số điện thoại liên hệ của khách hàng phải là kiểu số!')
                this.focusInput('txtSoLH')
                return false
            }

            if (this.ketQuaKS.length > 0 && this.pChkKTL) {
                this.$root.toastError('Bạn đang chọn \"Không trả lời\" và đồng thời tích chọn trả lời trong Danh sách câu hỏi!')
                return
            }

            if (!this.pChkKTL) {
                if (this.ketQuaKS.length == 0) {
                    this.$root.toastError("Bạn phải chọn câu trả lời!")
                    return false
                }
            }

            for (let chIndex = 0; chIndex < this.ketQuaKS.length; chIndex++)
            {
                let traLoiId = this.ketQuaKS[chIndex].RCH_ID;
                let cauHoiPhu = this.cauHoiKSChau.filter( x => x.RCH_ID == traLoiId );
                if (cauHoiPhu.length > 0) {
                    let chpDcTL = false;
                    for ( let chpIndex = 0; chpIndex < cauHoiPhu.length; chpIndex++ )
                    {
                        if (cauHoiPhu[chpIndex].Checked)
                        {
                            chpDcTL = true;
                            break;
                        }
                    }
                    if (!chpDcTL)
                    {
                        this.$root.toastError( "Bạn chọn câu trả lời '" + this.ketQuaKS[chIndex].TRALOI + "', mà chưa chọn lý do ! " );
                        return false;
                    }
                }

                //kiểm tra các câu trả lời ý kiến khác mà không nhập text.
                let traLoi = this.cauHoiKSChau.filter( x => x.TRALOI_ID == traLoiId );
                if (traLoi.length > 0) {
                    if ( (traLoi[0].LOAI == 1 || traLoi[0].LOAI == 2) && this.ketQuaKS[chIndex].NOIDUNG )
                    {
                        this.$root.toastError( "Bạn chưa nhập nội dung cho câu trả lời '" + this.ketQuaKS[chIndex].TRALOI + " ! " );
                        return false;
                    }
                }
            }
            return true
        },
        async onDieuLaiClicked() {
            try {
                if (this.$root.token.getPhanVungID() == 28)
                {
                    this.$root.toastError("Hiện tại nghiệp vụ điều lại chưa được hỗ trợ với HCM");
                    return;
                }
                // if (!bangts.KiemTraDieuHongLai(baohong_id, 0))
                // {
                //     Message_Box.ShowTB("Thuê bao đang có phiếu điều hỏng chưa xử lý xong! Bạn không thể điều lại!");
                //     return;
                // }
                if (this.pIsNew) {
                    this.$bvModal.show("popup-confirmDieuLai");
                }
                else {
                    this.$root.toastError("Không thực hiện điều lại phiếu khảo sát này được!")
                }
            }
            catch (e) {
                console.log(e)
            }
            finally {

            }
        },
        async onDieuLaiConfirmed() {
            try {
                this.$root.showLoading(true);
                if (await this.kiemTraDuLieu(true)) {
                    let data = this.taoDuLieu(true, true);
                    let rs = await this.$root.context.post(
                        "/web-cskh/api/khao-sat-kh-bao-hong/dieu-xac-nhan-lai",
                        data
                    );
                    if (rs.error_code === "BSS-00000000") {
                        if (rs.data.ketQua == 1) {
                            // this.$root.toastSuccess("Điều lại thành công!");
                            if (rs.data.msgError)
                            {
                                let listMessages = rs.data.msgError.split("#")
                                listMessages.forEach(message => {
                                    let pattern = /.*\d/g;
                                    let result = message.match(pattern)
                                    if (result && result.length > 0 && result[0].includes('1'))
                                    {
                                        this.$root.toastSuccess(message.replace(result[0], ""))
                                    }
                                    else
                                    {
                                        this.$root.toastError(message.replace(result[0], ""))
                                    }
                                });
                            }
                            this.closeDialog();
                        } else {
                            this.$root.toastError(rs.data.msgError);
                        }
                    } else {
                        this.$root.toastError("Điều lại thất bại!");
                    }
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        matchHeight() {
            var heightTTKH = this.$refs.boxTTKH.clientHeight + 2 + 'px'
            var heightTTCN = this.$refs.boxTTCN.clientHeight + 2 + 'px'
            Vue.set(this.styles.tbh, 'height', heightTTKH)
            Vue.set(this.styles.dslh, 'height', heightTTCN)
        },
        // onTraCuuTBKSClicked() {
        //     this.$refs.appTraCuuTBKSModal.maTB = this.pTxtMaTB
        //     this.$refs.appTraCuuTBKSModal.openDialog()
        // }
        onLichSuBHClicked() {
            this.$refs.popupLichSuBH.thueBaoId = this.thueBaoId
            this.$refs.popupLichSuBH.id = 'frmKhaoSatKH_frmLichSuBaoHong'
            this.$refs.popupLichSuBH.openDialog()
        },
        onLichSuHenBHClicked() {
            this.$refs.popupLichSuHenBH.baoHongId = this.baoHongId
            this.$refs.popupLichSuHenBH.kieu = 2
            this.$refs.popupLichSuHenBH.openDialog()
        },
        onLichSuHongClicked() {
            this.$root.toastError("frmTraCuuPhieuBH")
        },
    },
    watch: {
        pCboLyDoKTL: {
            handler: async function(newValue) {
                if (!newValue) return
                if (newValue == 20) {
                    this.pchkHenKS_Tu_Disabled = false
                    this.pchkHenKS_Den_Disabled = false
                }
                else {
                    this.pchkHenKS_Tu_Disabled = true
                    this.pchkHenKS_Den_Disabled = true
                    this.pchkHenKS_Tu = false
                    this.pchkHenKS_Den = false
                }
            }
        }
    }
};
</script>
