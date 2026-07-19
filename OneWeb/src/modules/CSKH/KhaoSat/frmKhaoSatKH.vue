<template src="./frmKhaoSatKH.html">

</template>
<style scope>
.e-grid .e-gridheader
{
    position: -webkit-sticky;
    position: sticky;
    top: 0px;
    z-index: 1;
}

.select2-selection__rendered {
    font-weight: normal;
}
.legend-title{
    font-weight: 600 !important;
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
// import TraCuuTBKSModal from "../DanhSachPhieuKhaoSatOutbound/popup/TraCuuTBKSModal.vue"
const LOAI_LAN_GOI = {
    HOP_DONG: 1,
    BAO_HONG: 2
};
const FIMS_LYDO_KTL =
{
    KH_TU_CHOI_TIEP_CHUYEN: 1,
    KHACHHANG_HEN: 20,
    MAY_BAN: 21,
    KHONG_XACNHAN_DICHVU: 22,
}
const SO_LAN_KS_TOI_DA = 4
export default {
    name: "frmKhaoSatKH",
    components: {
        frmLichSuBaoHong,
        frmLichSuHenBaoHong,
        cauHoiKS,
        confirmPopup: YesNoModal,
        // appTraCuuTBKSModal: TraCuuTBKSModal
    },
    props: {
        loaiKhaoSat: {
            type: String,
            default: 'frmKhaoSatKH'
        },
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
            baoHongId: 0,
            tbtlId: 0,
            thueBaoId: 0,
            hdkhId: 0,
            giaoPhieuKsId: 0,
            vmophieu: 0,
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
            pBaoHongId: 0,
            pThueBaoId: 0,
            pIsDaCapNhap : false,

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

            vDaCapNhap: false,
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
        getKetQua() {
            let dtKQ = []
            this.pKhaoSat.dsCauHoiChinh.forEach((ch, indexChinh) => {
                ch.cauHoiPhu.forEach((chp, indexPhu) => {
                    if (chp.Checked) {
                        dtKQ.push({
                            CAUHOI_ID: chp.CAUHOI_ID,
                            TRALOI_ID: chp.TRALOI_ID,
                            NOIDUNG: chp.Text,
                            CAUHOI: ch.TENCAUHOI,
                            TRALOI: chp.NOIDUNG_TL,
                            DIEULAI: chp.DIEULAI,
                            RCH_ID: chp.RCH_ID1,
                            DIEULAI_XN: chp.DIEULAI_XN
                        })
                    }

                    if (chp.dsCauHoiChau && chp.dsCauHoiChau.length > 0) {
                        //có câu hỏi cháu
                        chp.dsCauHoiChau.forEach((chc, indexChau) => {
                            if (chc.Checked) {
                                dtKQ.push({
                                    CAUHOI_ID: chc.CAUHOI_ID,
                                    TRALOI_ID: chc.TRALOI_ID,
                                    NOIDUNG: chc.Text,
                                    CAUHOI: ch.TENCAUHOI,
                                    TRALOI: chc.NOIDUNG_TL,
                                    DIEULAI: chc.DIEULAI,
                                    RCH_ID: chc.RCH_ID,
                                    DIEULAI_XN: chc.DIEULAI_XN
                                })
                            }
                        })
                    }
                })
            })
            return dtKQ
        },
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
        },
        idConfirmDieuLaiPopup() {
            return 'popup-confirmDieuLai-' + this.loaiKhaoSat
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
            this.$refs.frmKhaoSatKH.show();
        },
        closeDialog() {
            this.$emit("onHideKhaoSat");
            this.$refs.frmKhaoSatKH.hide();
            this.tbtlId = 0;
        },
        async onShow() {
            try {
                this.$root.showLoading(true);
                this.clearData();
                this.pDsLyDoKTL = await this.getDsLyDoKTL();

                if (this.pDsLyDoKTL.length > 0) {
                    this.pCboLyDoKTL = this.pDsLyDoKTL[0].LYDOKTL_ID;
                }
                this.$nextTick(() => this.$refs.txtTenNguoiTL.select());

                await this.getThamSoMacDinh();
                await this.hienThiThongTinTB();
                await this.$refs.cauHoiKS.loadData()
                // await this.hienThiCH();
                // this.processFakeData();

                this.pBtnDieuLaiEnabled = this.pIsNew;
                if (this.pThamSoMacDinh.KT_DAMBAO_CKCL_KH) {
                    let ktCKCL = await this.getKiemTraPhieuBaoHongCKCL()
                    this.pbaohong_ckcl = ktCKCL == 1
                    this.pIsShowCKCL = ktCKCL == 1
                }
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
                        this.pBaoHongId = record.BAOHONG_ID;
                        this.pThueBaoId = record.THUEBAO_ID;

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
                        let lsBaoHong = await this.getLichSuBaoHong();

                        this.pIsShowThongBaoCoHen = lsBaoHong && lsBaoHong.length > 0;

                        this.pDsLanGoiChuaTraLoi = await this.getLanGoiChuaTraLoi( LOAI_LAN_GOI.BAO_HONG );
                        let dsThongTinTBChuaTraLoi = null;
                        if (this.pIsSuaPhieu)
                            dsThongTinTBChuaTraLoi = await this.getThongTinTBTraLoi( LOAI_LAN_GOI.HOP_DONG );
                        else
                            dsThongTinTBChuaTraLoi = await this.getThongTinTBTraLoi( LOAI_LAN_GOI.BAO_HONG );


                        if ( dsThongTinTBChuaTraLoi && dsThongTinTBChuaTraLoi.length > 0 ) {
                            this.pIsNew = false
                            if (this.tbtlId == 0)
                                this.tbtlId = dsThongTinTBChuaTraLoi[0].TBTL_ID;
                            this.pTxtTenNguoiTL =
                                dsThongTinTBChuaTraLoi[0].NGUOI_TL;
                            this.pTxtSoLH =
                                dsThongTinTBChuaTraLoi[0].DIENTHOAI_LH;
                            this.pTxtGhiChu = dsThongTinTBChuaTraLoi[0].GHICHU;
                            this.pChkKTL =
                                dsThongTinTBChuaTraLoi[0].TRANGTHAI == 0;
                            this.pNgayKS = dsThongTinTBChuaTraLoi[0].NGAY_HOI;

                            if (this.pChkKTL) {
                                this.pIsNew = true;
                                if (this.CHOPHEP_SUA_OB_KHONG_TL == -1)
                                    this.tbtlId = 0;

                                if (dsThongTinTBChuaTraLoi[0].LYDOKTL_ID) {
                                    this.pCboLyDoKTL =
                                        dsThongTinTBChuaTraLoi[0].LYDOKTL_ID;
                                }
                            }

                            // if (dsThongTinTBChuaTraLoi[0].)
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
        async hienThiCH() {
            // await this.getDsCauHoi();
            this.fakeData.cauHoi = await this.getCauHoiChinh();
            this.fakeData.traLoi = await this.getDsChiTietTraLoi();
            this.processFakeData()
        },
        async getDsCauHoi() {
            try {
                this.pChiTietTraLoi = await this.getDsChiTietTraLoi();
                this.pKhaoSat.dsCauHoiChinh = await this.getCauHoiChinh();
                this.pKhaoSat.dsCauHoiChinh.forEach(async (cauhoi, index) => {
                    try {
                        let cauhoi2 = cauhoi;
                        cauhoi2.cauHoiPhu = await this.getCauHoiPhu(
                            cauhoi2.CAUHOI_ID
                        );
                        cauhoi2.cauHoiPhu.forEach(element => {
                            let tl = this.pChiTietTraLoi.filter(
                                x =>
                                    x.CAUHOI_ID == cauhoi2.CAUHOI_ID &&
                                    x.TRALOI_ID == element.TRALOI_ID
                            );
                            if (tl && tl.length > 0) {
                                element.Checked = true;
                                element.TxtNoiDung = tl.NOIDUNG;
                            } else {
                                element.Checked = false;
                                element.TxtNoiDung = "";
                            }
                        });
                        this.$set(this.pKhaoSat.dsCauHoiChinh, index, cauhoi2);
                    } catch (e) {
                        console.log(e);
                    }
                });
            } catch (e) {
                console.log(e);
            }
        },
        async getKiemTraPhieuBaoHongCKCL() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-bao-hong/kiem-tra-phieu-bao-hong-ckcl",
                    {
                        baoHongId: this.baoHongId,
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
        async getSoLanHongTrong30Ngay() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-bao-hong/so-lan-bao-hong-30-ngay",
                    {
                        thueBaoId: this.pThueBaoId,
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
                let response = await this.$root.context.get(
                    "/web-cskh/api/danh-muc/ly-do-ktl",
                    {}
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || [];
                }
                return [];
            } catch (e) {
                return [];
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
        async getCauHoiChinh() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/cau-hoi",
                    {
                        nhanVienId: 6,
                        hinhThuc: 2
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getCauHoiPhu(cauHoiId) {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/tra-loi",
                    {
                        cauHoiId: cauHoiId
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getDsChiTietTraLoi() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/chi-tiet-tbtl",
                    {
                        tbtlId: this.tbtlId
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        processFakeData() {
            this.fakeData.cauHoi.forEach(x => {
                var i = this.pKhaoSat.dsCauHoiChinh.findIndex(
                    cauhoi => x.CAUHOI_ID == cauhoi.CAUHOI_ID
                );
                if (i <= -1 && x.KIEU != 2) {
                    let dsCauHoiPhu = this.fakeData.cauHoi.filter(
                        y => x.CAUHOI_ID == y.CAUHOI_ID && y.RCH_ID == 0
                    );

                    dsCauHoiPhu.forEach(chp => {
                        let dsCauHoiChau = this.fakeData.cauHoi.filter(
                            y => y.RCH_ID == chp.RCH_ID1
                        );
                        dsCauHoiChau.forEach(e => {
                            let dapan = this.fakeData.traLoi.find(tl => tl.CAUHOI_ID == e.CAUHOI_ID && tl.TRALOI_ID == e.TRALOI_ID)
                            if (dapan) {
                                e.Checked = true
                                e.Text = dapan.NOIDUNG
                            }
                            else {
                                e.Checked = false
                                e.Text = ''
                            }

                        })
                        chp.dsCauHoiChau = dsCauHoiChau;

                        let dapan = this.fakeData.traLoi.find(tl => tl.CAUHOI_ID == chp.CAUHOI_ID && tl.TRALOI_ID == chp.TRALOI_ID)
                        if (dapan) {
                            chp.Checked = true
                            chp.Text = dapan.NOIDUNG
                        }
                        else {
                            chp.Checked = false
                            chp.Text = ''
                        }
                    });

                    dsCauHoiPhu = dsCauHoiPhu.sort(
                        (a, b) => (a.THUTU_TL > b.THUTU_TL ? 1 : -1)
                    );

                    this.pKhaoSat.dsCauHoiChinh.push({
                        CAUHOI_ID: x.CAUHOI_ID,
                        TENCAUHOI: x.NOIDUNG,
                        KIEU: x.KIEU,
                        HINHTHUC: x.HINHTHUC,
                        LOAICH_ID: x.LOAI,
                        THUTU: x.THUTU,
                        cauHoiPhu: dsCauHoiPhu
                    });
                }
            });
            this.pKhaoSat.dsCauHoiChinh = this.pKhaoSat.dsCauHoiChinh.sort(
                (a, b) => (a.THUTU > b.THUTU ? 1 : -1)
            );
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
            this.pBaoHongId = 0;
            this.pThueBaoId = 0;

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
            this.pIsDaCapNhap = false
        },
        onTraLoiChecked(index, indexPhu, isRadio) {
            let ch = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu];
            if (isRadio) {
                for (let i = 0; i < this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu.length; i++) {
                    let tmp = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[i]
                    if (i != indexPhu) {
                        tmp.Checked = false
                        this.$set(this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, i, tmp);
                    }
                }
            }

            ch.Checked = !ch.Checked
            this.$set(this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, indexPhu, ch);
        },
        onTraLoiCauHoiPhuChecked(index, indexPhu, indexChau) {
            let chp = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu]
            for (let i = 0; i < chp.dsCauHoiChau.length; i++) {
                if (i != indexChau) {
                    chp.dsCauHoiChau[i].Checked = false
                }
                else {
                    chp.dsCauHoiChau[i].Checked = !chp.dsCauHoiChau[i].Checked
                }
            }
            this.$set(this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, indexPhu, chp);
        },

        async onGhiLaiClicked() {
            try {
                this.loading(true)
                if ((this.pIsNew && !this.pIsDaCapNhap) || this.vmophieu == 1) {
                    this.pIsDaCapNhap = false;
                    if (await this.kiemTraDuLieu(true)) {
                        let data = this.taoDuLieu(true, false);
                        let rs = await this.$root.context.post(
                            "/web-cskh/api/khao-sat-kh-bao-hong/ghi-lai",
                            data
                        );

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
                            // if (rs.data.msgError) {
                            //     this.$root.toastError(
                            //         "Có lỗi: " + rs.data.msgError
                            //     );
                            // } else {
                            //     this.$root.toastSuccess("Cập nhật thành công!");
                            //     this.closeDialog()
                            // }
                        } else {
                            this.$root.toastError("Cập nhật thất bại");
                        }
                    }

                }
                else {
                    if (await this.kiemTraDuLieu(false)) {
                        let data = this.taoDuLieu(false, false)
                        let rs = await this.$root.context.post(
                            "/web-cskh/api/khao-sat-kh-bao-hong/ghi-lai",
                            data
                        );

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
                            // if (rs.data.msgError) {
                            //     this.$root.toastError(
                            //         "Có lỗi: " + rs.data.msgError
                            //     );
                            // } else {
                            //     this.$root.toastSuccess("Cập nhật thành công!");
                            //     this.closeDialog()
                            // }
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
            let ttksId = 1;
            if (this.pChkKTL) {
                ttksId = 1
                let dsChuaTraLoi = this.pDsLanGoiChuaTraLoi.filter(x => x.LYDO_KTL)
                if (dsChuaTraLoi + 1 > SO_LAN_KS_TOI_DA) {
                    ttksId = 7
                }
                this.pIsDaCapNhap = true
            }
            else {
                ttksId = 6
                this.pIsDaCapNhap = true
            }

            if (this.pThamSoMacDinh.OB_KHONGTL_KTC1 && this.pChkKTL && this.pCboLyDoKTL === FIMS_LYDO_KTL.KH_TU_CHOI_TIEP_CHUYEN) {
                this.pIsDaCapNhap = true
                ttksId = 6
            }
            let data = {
                tbtlId: isThemMoi ? 0 : this.tbtlId,
                giuPhieu: 0,
                baoHongId: this.baoHongId,

                thueBaoId: this.pThueBaoId,
                nguoiTl: this.pTxtTenNguoiTL,
                dienThoaiLh: this.pTxtSoLH,
                ghiChu: this.pTxtGhiChu,
                ttksId: ttksId,
                giaoPhieuKsId: this.giaoPhieuKsId,
                ngayBdKs: moment(new Date()).format("DD/MM/YYYY"),
                lyDoKtlId: this.pChkKTL ? this.pCboLyDoKTL : 0,
                gioHenTu: this.pchkHenKS_Tu
                    ? moment(this.pDtpHenKS_Tu).format("DD/MM/YYYY")
                    : "",
                gioHenDen: this.pchkHenKS_Den
                    ? moment(this.pDtpHenKS_Den).format("DD/MM/YYYY")
                    : "",
                dsCauHoiId:   kq.map(x => x.CAUHOI_ID).join("#"),
                dsTraLoiId:   kq.map(x => x.TRALOI_ID).join("#"),
                dsNoiDung:  JSON.stringify(kq.map(x => `${x.NOIDUNG}`)),// kq.map(x => x.NOIDUNG).join("#"),
                isCnSoLienHe: 1,
                nguoiDungId: this.$root.token.getNguoiDungID()
            };
            return data;
        },
        async kiemTraDuLieu(isThemMoi) {
            if (!this.pTxtTenNguoiTL && !this.pChkKTL) {
                this.$root.toastError('Chưa nhập tên người trả lời !')
                this.focusInput('txtTenNguoiTL')
                return false
            }
            if (!this.pTxtSoLH) {
                this.$root.toastError('Chưa nhập điện thoại liên hệ !')
                this.focusInput('txtSoLH')
                return false
            }
            if (isNaN(this.pTxtSoLH)) {
                this.$root.toastError('Số điện thoại liên hệ của khách hàng phải là kiểu số!')
                this.focusInput('txtSoLH')
                return false
            }
            if (this.pChkKTL && this.pCboLyDoKTL == 20) //FIMS_LYDO_KTL.KHACHHANG_HEN
            {
                if (this.pchkHenKS_Tu && this.pchkHenKS_Den) {
                    this.$root.toastError('Hãy chọn Ngày hẹn')
                    return false
                }
                if (this.pchkHenKS_Tu && !this.pDtpHenKS_Tu) {
                    this.$root.toastError('Hãy chọn Ngày hẹn khảo sát từ')
                    return false
                }
                if (this.pchkHenKS_Tu && this.pDtpHenKS_Tu < this.today) {
                    this.$root.toastError('Ngày hẹn từ không thể nhỏ hơn ngày hiện tại')
                    return false
                }
                if (this.pchkHenKS_Den && !this.pDtpHenKS_Den) {
                    this.$root.toastError('Hãy chọn Ngày hẹn khảo sát đến')
                    return false
                }
                if (this.pchkHenKS_Den && this.pDtpHenKS_Den < this.today) {
                    this.$root.toastError('Ngày hẹn đến không thể nhỏ hơn ngày hiện tại')
                    return false
                }
                if (this.pchkHenKS_Tu && this.pDsThongTinBaoHong[0].NGAY_HT && moment(this.pDsThongTinBaoHong[0].NGAY_HT, "DD/MM/YYYY HH:mm:ss").add(3, 'days') < this.pDtpHenKS_Tu) {
                    this.$root.toastError('Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: ' + this.pDsThongTinBaoHong[0].NGAY_HT)
                    return false
                }
                if (this.pchkHenKS_Den && this.pDsThongTinBaoHong[0].NGAY_HT && moment(this.pDsThongTinBaoHong[0].NGAY_HT, "DD/MM/YYYY HH:mm:ss").add(3, 'days') < this.pDtpHenKS_Den) {
                    this.$root.toastError('Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: ' + this.pDsThongTinBaoHong[0].NGAY_HT)
                    return false
                }
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

                let cauHoiKS = this.$refs["cauHoiKS"];
                if (!this.pChkKTL) {
                    if (this.ketQuaKS.length <= 0) {
                        this.$root.toastError("Bạn phải chọn câu trả lời!");
                        return false;
                    }
                    if (this.pThamSoMacDinh.BATBUOC_TRALOI_ALL_CAUHOI) {
                    }
                }

                for (let chIndex = 0; chIndex < this.ketQuaKS.length; chIndex++) {
                    let traLoiId = this.ketQuaKS[chIndex].RCH_ID;
                    let cauHoiPhu = cauHoiKS.dsCauHoiChau.filter(
                        x => x.RCH_ID == traLoiId
                    );

                    let chpDcTL = false;
                    for (
                        let chpIndex = 0;
                        chpIndex < cauHoiPhu.length;
                        chpIndex++
                    ) {
                        if (cauHoiPhu[chpIndex].Checked) {
                            chpDcTL = true;
                            break;
                        }
                    }
                    if (cauHoiPhu.length> 0 && !chpDcTL) {
                        this.$root.toastError(
                            "Bạn chọn câu trả lời '" +
                                this.ketQuaKS[chIndex].TRALOI +
                                "', mà chưa chọn lý do ! "
                        );
                        return false;
                    }

                    //kiểm tra các câu trả lời ý kiến khác mà không nhập text.
                    let traLoi = cauHoiKS.dsCauHoiChau.filter(
                        x => x.TRALOI_ID == traLoiId
                    );

                    if (traLoi && traLoi.length > 0) {
                        if (
                            (traLoi[0].LOAI == 1 || traLoi[0].LOAI == 2) &&
                            this.ketQuaKS[chIndex].NOIDUNG
                        ) {
                            this.$root.toastError(
                                "Bạn chưa nhập nội dung cho câu trả lời '" +
                                    this.ketQuaKS[chIndex].TRALOI +
                                    " ! "
                            );
                            return false;
                        }
                    }

                    //Nếu câu trả lời lq đến NET thì fai tồn tại hướng giao về Net - VB 1348/VNPT-CLG-TL
                    // if (a.Rows[0]["MA_TL"].ToString() == "NET1" || a.Rows[0]["MA_TL"].ToString() == "NET2" || a.Rows[0]["MA_TL"].ToString() == "NET3")
                    // if (fims.kiemtra_giaophieu_net_v2(hdtb_id, 1) != "1")
                    // {
                    //     Message_Box.ShowTB("Bạn chỉ được chọn hạng mục 'NET1', 'NET2', 'NET3' sau khi kiểm tra có phiếu VNPT Net xử lý");
                    //     return false;
                    // }
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
                    this.$bvModal.show(this.idConfirmDieuLaiPopup);
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
                                    if (message.includes('1'))
                                    {
                                        this.$root.toastSuccess(message.replace(result, ""))
                                    }
                                    else
                                    {
                                        this.$root.toastError(message.replace(result, ""))
                                    }
                                });
                            }
                            this.closeDialog()
                        } else {
                            this.$root.toastError(rs.data.msgError);
                        }

                    } else {
                        this.$root.toastError("Điều lại thất bại!");
                    }
                    // if (rs.error_code === "BSS-00000000") {
                    //     if (rs.data.msgError) {
                    //         this.$root.toastSuccess(
                    //             "Có lỗi: " + rs.data.msgError
                    //         );
                    //     } else
                    //         this.$root.toastSuccess("Cập nhật thành công!");
                    // } else {
                    //     this.$root.toastError("Cập nhật thất bại");
                    // }
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
            this.$refs.popupLichSuBH.thueBaoId = this.pThueBaoId
            this.$refs.popupLichSuBH.id = 'frmKhaoSatKH_frmLichSuBaoHong'
            this.$refs.popupLichSuBH.openDialog()
        },
        onLichSuHenBHClicked() {
            this.$refs.popupLichSuHenBH.baoHongId = this.pBaoHongId
            this.$refs.popupLichSuHenBH.kieu = 2
            this.$refs.popupLichSuHenBH.openDialog()
        }
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
