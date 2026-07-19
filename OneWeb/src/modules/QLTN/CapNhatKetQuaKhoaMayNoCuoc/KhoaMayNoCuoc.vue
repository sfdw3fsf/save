<template src="./KhoaMayNoCuoc.html"></template>
<style src="./KhoaMayNoCuoc.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import {
    LoaiHopDong,
    NHOM_KQXL,
    KETQUA_XL,
    TRANGTHAI_DONGBO,
    TrangThaiHD,
} from "@/utils/Enum";
import API from "../api/CapNhatKQKhoaMayNoCuoc";
import token from "@/plugins/token";
import ExportExcel from "./components/exportExcel";
import LyDoHuyModal from "./components/LyDoHuy";
// import GiaoViecModal from "./components/GiaoViec";
import PopupLichSuKS from "./components/frmLichSuKS.vue";
import GiaoViecModal from "../../../modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue";
import moment from "moment";
import {
    fieldsPhieuChoXacNhan,
    fieldsPhieuDaHuyYeuCau,
    headerCapNhatKetQuaKhoaMayNocuoc,
    headerTrungTamVienThongXacMinhNoCuoc,
    headerDuyetKetQuaXuLyNoCuoc,
    fieldsThongTinGiaoViec,
    fieldsLyDoHuy,
} from "./fields";
const enumTTHD = {
    DANG_THI_CONG: 4,
    NVKD_XACMINH: 27,
    TTVT_XACMINH_NO: 14,
};
import PopUpCapNhatGiaoPhieu from "./components/CapNhatGiaoPhieu";


export default {
    name: "CapNhatKetQuaKhoaMayNoCuoc",
    components: {
        breadcrumb,
        appExportExcel: ExportExcel,
        appLyDoHuy: LyDoHuyModal,
        appGiaoViecModal: GiaoViecModal,
        PopupLichSuKS,
        PopUpCapNhatGiaoPhieu
    },
    beforeMount() {
        if (this.$route.path.toString().includes("CapNhatKetQuaKhoaMayNoCuoc")) {
            this.tag = this.$route.query.tag;
            this.header = headerCapNhatKetQuaKhoaMayNocuoc;
            this.btnCapNhatNgay_visible = true;
            this.btnDuyet_visible = true;
            this.btnGiaoViec_visible = true;
        } else if (
            this.$route.path.toString().includes("TrungTamVienThongXacMinhNoCuoc")
        ) {

            this.tag = this.$route.query.tag;
            this.header = headerTrungTamVienThongXacMinhNoCuoc;
            this.btnCapNhatNgay_visible = true;
            this.btnDuyet_visible = true;
            this.btnGiaoViec_visible = true;
            this.tabPhieuDaHuy = false;
        } else if (this.$route.path.toString().includes("DuyetKetQuaXuLyNoCuoc")) {

            this.tag = this.$route.query.tag;
            this.header = headerDuyetKetQuaXuLyNoCuoc;
            this.btnCapNhatNgay_visible = false;
            this.btnGiaoViec_visible = false;
            this.btnDuyet_visible = true;
            this.btnDuyet_enable = true;
        }
    },
    data() {
        return {
            header: {},
            nhanvien_id: "",
            kyCuoc: "",
            dstthd_id: "",
            dshuonggiao_id: "",
            dsdichvuvt_id: "",
            kt_thamso_thuhoi: false,
            PHAINHAP_GHICHU_TTVT_XN_NO: -1,
            NGAY_THUHOI: -1,
            currentPage: 0,
            _XacMinh_TheoYeuCau: 0,
            ds_nhanvien_id: "",
            loaitb_id: "",
            TUDONG_HOANCONG_KHOAMAY: -1,
            PHAINHAP_LYDOHUY: 0,
            IS_NEW_FORM: 0,
            tag: "",
            btnLyDoHuyVisibled: false,
            cbbNhomKQ: [],
            valueNhomKQSelected: null,
            cbbDichVuVT: [],
            valueDichVuVTSelected: null,
            cbbQuyTrinh: [],
            valueQuyTrinhSelected: null,
            cbbKQ: [],
            valueKQSelected: null,
            dsLyDoTra: [],
            dsThamSoMD: [],
            chkDaThuDuoc_checked: false,
            chkDaThuDuoc_visible: true,
            chkDaThuDuoc_enable: true,
            txtTongThu: "",
            txtTongThu_visible: true,
            txtTongThu_enable: true,
            txtNDThucHien: "",
            txtNDThucHien_visible: true,
            txtNDThucHien_enable: true,
            txtGhiChu: "",
            txtGhiChu_visible: true,
            txtGhiChu_enable: true,
            txtTimKiem_visible: true,
            txtTimKiem_enable: true,
            rdoGiaoNgay_checkedText: "Giao ngay",
            rdoGiaoNgay_visible: true,
            rdoGiaoNgay_enable: true,
            rdoGiao_TheoQD_checkedText: "Giao theo QĐ",
            rdoGiao_TheoQD_visible: true,
            rdoGiao_TheoQD_enable: true,
            rdoChecked: "",
            btnCapNhatNgay_visible: true,
            btnCapNhatNgay_enable: true,
            btnDuyet_visible: true,
            btnDuyet_enable: true,
            btnGiaoViec_visible: true,
            btnGiaoViec_enable: true,
            btnGiaoPhieu_visible: true,
            btnGiaoPhieu_enable: true,

            btnPhieuThu_enable: true,
            btnPhieuThu_visible: true,
            btnKichHoat_enable: true,
            btnKichHoat_visible: false,
            btnHoanCong_enable: true,
            btnHoanCong_visible: false,
            tabPhieuDaHuy: true,
            dtThaoTac: [],
            luong_id: 0,
            huonggiao_id: 0,
            quytrinh_id: 0,
            phieu_id: null,
            hdtb_id: null,
            ma_tt: "",
            thuebao_id: -1,
            dtLuotGiao: [],
            dtGiaoViec: [],
            dtLyDoHuy: [],
            dtPhieuChuaThu: [],
            itemPhieuChuaThuFocus: {},
            itemSelectedPhieuChuaThu: [],
            dtTraHoaDon: [],
            dtTraTronBo: [],
            dtPhieuHen: [],
            dtTraGiayBao: [],
            dtPhieuThuDuoc: [],
            dtHoaDonLoai: [],
            dtPhieuHuy: [],
            itemSelectedPhieuHuy: [],
            labelFunctionTitle: "",
            fieldsChoXacNhan: fieldsPhieuChoXacNhan,
            fieldsPhieuHuy: fieldsPhieuDaHuyYeuCau,
            fieldsGiaoViec: fieldsThongTinGiaoViec,
            fieldsLyDoHuy: fieldsLyDoHuy,
            loaild_id: -1,
            kieu_lydohuy_lhtb: 2,
            FORM_TO_TRUONG: 0,
            thongbao: "Thông báo:",
            msgThongBao: {
                11: "Bạn có chắc chắn muốn kích hoạt các thuê bao IMS này ?",
                4: "Bạn có chắc chắn muốn kích hoạt các thuê bao đã chọn hay không?",
                6: "Bạn có chắc chắn muốn kích hoạt các thuê bao đã chọn hay không?",
                1: "Bạn có chắc chắn kích hoạt lên tổng đài hay không?",
                2: "Bạn có chắc chắn muốn kích hoạt các dịch vụ này không?",
                10: "Bạn có chắc chắn muốn kích hoạt các dịch vụ này không?",
            },
            msgKichHoat: "",
            dsKichHoat: [],
            sumTemplate: function () {
                return {
                    template: Vue.component("sumTemplate", {
                        template: `<span>Sum: {{data.Sum}}</span>`,
                        data: function () {
                            return { data: { data: {} } };
                        },
                    }),
                };
            },
            aggregatesColumns: [
                {
                    field: "TIEN_NO",
                    type: "Sum",
                    format: "N0",
                    footerTemplate: this.sumTemplate,
                },
            ],
            isActionCapNhat: true
        };
    },
    computed: {
        getShowComponent: function () {
            return true;
        },
        showBtnLyDo: function () {
            if (this.$route.path.toString().includes("DuyetKetQuaXuLyNoCuoc")) {
                return false;
            }
            return true;
        },
    },
    watch: {
        valueQuyTrinhSelected() {
            this.cbbQuyTrinhChanged();
        },
    },
    methods: {
        isNullOrEmpty(value) {
            return value === null || value === "";
        },
        dateToString(value, format = "DD/MM/YYYY") {
            return moment(value).format(format);
        },
        stringToDate(value, format = "DD/MM/YYYY") {
            return moment(value, format).toDate();
        },
        setFirstValue(cbo, key) {
            if (cbo.length) {
                return cbo[0][key];
            } else {
                return "";
            }
        },
        async cbbQuyTrinhChanged() {
            if (this.valueQuyTrinhSelected) {
                try {
                    this.loading(true);
                    let ds = [];
                    let data = {
                        pQuyTrinhId: this.valueQuyTrinhSelected,
                        pTTHDId: this.dstthd_id,
                    };
                    let response = await API.getHuongGiaoQuyTrinh(this.axios, data);
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        ds = response.data.data;
                        this.labelFunctionTitle = "";
                        if (ds.length) {
                            this.luong_id = ds[0].LUONG_ID;
                            this.huonggiao_id = ds[0].HUONGGIAO_ID;
                            this.labelFunctionTitle = ds[0].HUONGGIAO;

                            try {
                                document.getElementsByClassName("main-title")[0].textContent =
                                    this.labelFunctionTitle;
                            } catch (error) { }
                        } else {
                            this.$toast.error(
                                "Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại"
                            );
                            this.hienThiDanhSach(this.currentPage);
                            return;
                        }
                        this.hienThiGiaoDien(this.luong_id);
                        this.hienThiDanhSach(this.currentPage);
                        if (
                            this.phaiLam(this.luong_id, "TUDONG_THUHOI_KHI_HOANCONG") &&
                            this.kt_thamso_thuhoi == true
                        ) {
                            this.rdoChecked = this.rdoGiao_TheoQD_checkedText;
                            this.rdoGiao_TheoQD_enable = true;
                            this.rdoGiaoNgay_checked = false;
                            this.rdoGiaoNgay_enable = true;
                        } else {
                            this.rdoChecked = "";
                            this.rdoGiao_TheoQD_enable = false;
                            this.rdoGiaoNgay_enable = false;
                        }
                    }
                } catch (error) {
                    console.log(error);
                } finally {
                    this.loading(false);
                }
            }
        },
        async cbbDichVuVTChanged() {
            this.cbbQuyTrinh = await this.getQuyTrinh();
            if (this.cbbQuyTrinh.length) {
                this.valueQuyTrinhSelected = this.setFirstValue(
                    this.cbbQuyTrinh,
                    "QUYTRINH_ID"
                );
            } else {
                this.valueQuyTrinhSelected = null;
            }

            this.dtPhieuChuaThu = [];
            this.dtPhieuHuy = [];
            if (this.cbbQuyTrinh.length > 0) {
                if (this.valueNhomKQSelected == NHOM_KQXL.CHUA_THU_CUOC.toString()) {
                    this.btnPhieuThu_enable = true;
                } else if (
                    this.valueNhomKQSelected ==
                    NHOM_KQXL.DA_THU_KO_CO_NHUCAU_SUDUNG.toString() ||
                    this.valueNhomKQSelected ==
                    NHOM_KQXL.DA_THU_CO_NHUCAU_SUDUNG.toString()
                ) {
                    this.btnPhieuThu_enable = false;
                } else {
                    this.btnPhieuThu_enable = false;
                }
                if (this.phaiLam(this.luong_id, "KICHHOAT_TD")) {
                    if (
                        this.valueNhomKQSelected == NHOM_KQXL.CHUA_THU_CUOC.toString() ||
                        (this.valueNhomKQSelected == NHOM_KQXL.TTKD_DA_DON_DOC.toString() &&
                            this.valueKQSelected == KETQUA_XL.KH_DONG_Y_THANHTOAN)
                    ) {
                        this.btnKichHoat_visible = false;
                        this.btnHoanCong_visible = false;
                    } else {
                        this.btnKichHoat_visible = true;
                        this.btnHoanCong_visible = true;
                    }
                }
                this.hienThiDanhSach(this.currentPage);
            } else {
                this.cbbQuyTrinh = [];
            }
        },
        async cbbNhomKQChanged() {
            this.cbbKQ = await this.getNhomKQXLN();
            this.valueKQSelected = this.setFirstValue(this.cbbKQ, "KQXL_ID");
        },
        async cbbKQSelectChanged() {
            if (this.valueKQSelected) {
                if (this.valueNhomKQSelected == NHOM_KQXL.CHUA_THU_CUOC.toString()) {
                    this.btnPhieuThu = true;
                } else {
                    this.btnPhieuThu = false;
                }
                if (this.phaiLam(this.luong_id, "KICHHOAT_TD")) {
                    if (
                        this.valueNhomKQSelected == NHOM_KQXL.CHUA_THU_CUOC.toString() ||
                        (this.valueNhomKQSelected == NHOM_KQXL.TTKD_DA_DON_DOC.toString() &&
                            this.valueKQSelected == KETQUA_XL.KH_DONG_Y_THANHTOAN)
                    ) {
                        this.btnKichHoat_visible = false;
                        this.btnHoanCong_visible = false;
                    } else {
                        this.btnKichHoat_visible = true;
                        this.btnHoanCong_visible = true;
                    }
                }
            }
        },
        async fetchKyCuoc() {
            const response = await API.getKyCuocHienHanh(this.axios);
            if (response.data.error_code === "BSS-00000000") {
                this.kyCuoc = response.data.data[0].KYCUOC;
            }
        },
        async getNhomKQXLN() {
            try {
                this.loading(true);
                let res = [];
                let data = {
                    pNhomKQId: this.valueNhomKQSelected,
                };
                let response = await API.getKetQua(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data;
                }
                return res;
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        phaiLam(luongid, code) {
            try {
                if (this.dtThaoTac.length <= 0) return false;
                let dataSelect = this.dtThaoTac.filter((item) => {
                    if (item.ENABLE == 1 && item.CODE == code) return item;
                });
                if (dataSelect.length > 0) return true;
            } catch (error) {
                return false;
            }
        },
        async getQuyTrinh() {
            let res = [];
            if (this._XacMinh_TheoYeuCau == 1) {
                try {
                    this.loading(true);
                    let data = {
                        loaiHDId: LoaiHopDong.THAY_DOI_DV,
                        dichVuVTId: this.valueDichVuVTSelected,
                        TTHDId: this.dstthd_id,
                        kieuYC: 1,
                    };
                    let response = await API.getDSQuyTrinhL1(this.axios, data);
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        return response.data.data;
                    }
                } catch (error) {
                    console.log(error);
                } finally {
                    this.loading(false);
                }
            } else {
                try {
                    this.loading(true);
                    let res = [];
                    let data = {
                        pDSLoaiHDId: LoaiHopDong.THAY_DOI_DV,
                        pDichVuVTId: this.valueDichVuVTSelected,
                        pTTHDId: this.dstthd_id,
                        pKieu: 1,
                    };
                    let response = await API.getDSQuyTrinhL2(this.axios, data);
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        return response.data.data;
                    }
                } catch (error) {
                    console.log(error);
                } finally {
                    this.loading(false);
                }
            }
        },
        async hienThiDanhSach(page) {
            if (this._XacMinh_TheoYeuCau == 1) {
                if (page == 0) {
                    try {
                        this.loading(true);

                        let data = {
                            pXacMinhTheoYeuCau: 1,
                            pMaGD: null,
                            pDichVuVTId: this.valueDichVuVTSelected,
                            pLoaiTBId: null,
                            pTTHDId: this.dstthd_id,
                            pDSHuongGiaoId: this.dshuonggiao_id,
                            pKieuYC: 1,
                            pLoaiId: null,
                            pKyCuoc: this.kyCuoc,
                            pQuyTrinhId: this.valueQuyTrinhSelected,
                            pToTruong: this.FORM_TO_TRUONG
                        };
                        this.dtPhieuChuaThu = [];
                        let response = await API.getDSHDTBKhoaMoMayHC(
                            this.axios,
                            data
                        ).catch((err) => {
                            this.$toast.error(err.data.message_detail);
                        });
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            if (response.data.data.length > 0) {
                                this.dtPhieuChuaThu = response.data.data;
                                // response.data.data.forEach((item) => {
                                //     this.dtPhieuChuaThu.push(item);
                                // });
                                //setdiemtinnhiem
                                // try {
                                //     this.loading(true);
                                //     let dsKHId = this.dtPhieuChuaThu.map(
                                //         (item) => item.KHACHHANG_ID
                                //     );
                                //     let data = {
                                //         pDSKhachHangId: dsKHId.join(","),
                                //     };
                                //     let response = await API.getDSDiemTinNhiem(this.axios, data);
                                //     if (
                                //         response.data.error_code === "BSS-00000000" &&
                                //         response.data.data !== undefined
                                //     ) {
                                //         if (response.data.data.length > 0) {
                                //             for (
                                //                 let index = 0;
                                //                 index < this.dtPhieuChuaThu.length;
                                //                 index++
                                //             ) {
                                //                 for (
                                //                     let index1 = 0;
                                //                     index1 < response.data.data.length;
                                //                     index1++
                                //                 ) {
                                //                     if (
                                //                         this.dtPhieuChuaThu[index].KHACHHANG_ID ==
                                //                         response.data.data[index1].KHACHHANG_ID
                                //                     ) {
                                //                         this.dtPhieuChuaThu[index].DIEMTINNHIEM =
                                //                             response.data.data[index1].DIEMTINNHIEM;
                                //                     }
                                //                 }
                                //             }
                                //         }
                                //     }
                                // } catch (error) {
                                // } finally {
                                //     this.loading(false);
                                // }
                                //end setdiemtinnhiem
                            }
                            //console.log(this.dtPhieuChuaThu);
                        }
                    } catch (error) {
                        //  this.$toast.error("Dữ liệu đầu vào không hợp lệ");
                        //  this.$toast.error(error.response.data.message_detail);
                    } finally {
                        this.loading(false);
                    }
                }
                if (page == 1) {
                    try {
                        this.loading(true);
                        this.dtPhieuHuy = [];
                        let data = {
                            pXacMinhTheoYeuCau: 1,
                            pMaGD: null,
                            pDichVuVTId: this.valueDichVuVTSelected,
                            pLoaiTBId: null,
                            pTTHDId: 7,
                            pDSHuongGiaoId: parseInt(this.dshuonggiao_id),
                            pKieuYC: 1,
                            pLoaiId: null,
                            pKyCuoc: this.kyCuoc,
                            pQuyTrinhId: this.valueQuyTrinhSelected,
                            pToTruong: this.FORM_TO_TRUONG
                        };
                        this.dtPhieuHuy = []
                        let response = await API.getDSHDTBKhoaMoMayHC(
                            this.axios,
                            data
                        ).catch((err) => {
                            this.$toast.error(err.data.message_detail);
                        });
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            if (response.data.data.length > 0) {
                                response.data.data.forEach((item) => {
                                    this.dtPhieuHuy.push(item);
                                });
                            }
                            console.log(this.dtPhieuHuy);
                        }
                    } catch (error) {
                        //  this.$toast.error("Dữ liệu đầu vào không hợp lệ");
                        // this.$toast.error(error.response.data.message_detail);
                    } finally {
                        this.loading(false);
                    }
                }
            } else {
                if (page == 0) {
                    this.dtPhieuChuaThu = [];
                    try {
                        this.loading(true);
                        let data = {
                            pXacMinhTheoYeuCau: 0,
                            pMaGD: null,
                            pDichVuVTId: this.valueDichVuVTSelected,
                            pLoaiTBId: null,
                            pTTHDId: this.dstthd_id,
                            pDSHuongGiaoId: this.dshuonggiao_id,
                            pKieuYC: 1,
                            pLoaiId: null,
                            pKyCuoc: this.kyCuoc,
                            pQuyTrinhId: this.valueQuyTrinhSelected,
                            pToTruong: this.FORM_TO_TRUONG
                        };
                        let response = await API.getDSHDTBKhoaMoMayHC(
                            this.axios,
                            data
                        ).catch((err) => {
                            this.$toast.error(err.data.message_detail);
                        });
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {

                            if (response.data.data.length > 0) {
                                this.dtPhieuChuaThu = response.data.data;
                            }
                        }
                    } catch (error) {
                        // this.$toast.error("Dữ liệu đầu vào không hợp lệ");
                    } finally {
                        this.loading(false);
                    }
                }
                if (page == 1) {
                    try {
                        this.loading(true);
                        this.dtPhieuHuy = [];
                        let data = {
                            pXacMinhTheoYeuCau: 0,
                            pMaGD: null,
                            pDichVuVTId: this.valueDichVuVTSelected,
                            pLoaiTBId: null,
                            pTTHDId: 7,
                            pDSHuongGiaoId: parseInt(this.dshuonggiao_id),
                            pKieuYC: 1,
                            pLoaiId: null,
                            pKyCuoc: this.kyCuoc,
                            pQuyTrinhId: this.valueQuyTrinhSelected,
                            pToTruong: this.FORM_TO_TRUONG
                        };
                        this.dtPhieuHuy = [];
                        let response = await API.getDSHDTBKhoaMoMayHC(
                            this.axios,
                            data
                        ).catch((err) => {
                            this.$toast.error(err.data.message_detail);
                        });
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            if (response.data.data.length > 0) {
                                response.data.data.forEach((item) => {
                                    this.dtPhieuHuy.push(item);
                                });
                            }
                        }
                    } catch (error) {
                        //this.$toast.error("Dữ liệu đầu vào không hợp lệ");
                        // this.$toast.error(error.response.data.message_detail);
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        async hienThiGiaoDien(luong_id) {
            this.btnCapNhatNgay_visible = false;
            this.btnDuyet_visible = false;
            this.btnDuyet_enable = false;
            this.btnGiaoViec_visible = false;
            this.btnGiaoPhieu_visible = false;
            this.btnPhieuThu_visible = false;
            this.btnKichHoat_visible = false;
            this.btnKichHoat_enable = false;
            this.btnHoanCong_visible = false;
            //custom show action top
            if (this.$route.path.toString().includes("CapNhatKetQuaKhoaMayNocuoc")) {
                this.btnCapNhatNgay_visible = true;
            } else if (
                this.$route.path.toString().includes("TrungTamVienThongXacMinhNoCuoc")
            ) {
                this.btnCapNhatNgay_visible = true;
            } else if (
                this.$route.path.toString().includes("DuyetKetQuaXuLyNoCuoc")
            ) {
                this.btnCapNhatNgay_visible = false;
                this.btnDuyet_visible = true;
                this.btnDuyet_enable = true;
            }
            //
            try {
                let data = {
                    pLuongId: luong_id,
                };
                let response = await API.getDSControll(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data.length > 0) {
                        this.dtThaoTac = response.data.data
                        
                        // response.data.data.forEach((item) => {
                        //     this.dtThaoTac.push(item);
                        // });
                        // let ctlNames = this.dtThaoTac.map(x => x.CONTROL_NAME)
                        this.dtThaoTac.forEach((element) => {
                            if (element.CONTROL_NAME.includes("tsbtnDuyet")) {
                                if (element.ENABLE === 1) {
                                    this.btnDuyet_enable = true;
                                    this.btnDuyet_visible = true;
                                } else {
                                    this.btnDuyet_visible = false;
                                    this.btnDuyet_enable = false;
                                }
                            } else if (element.CONTROL_NAME.includes("tsbtnCapNhatNgay")) {
                                if (element.ENABLE === 1) {
                                    this.btnCapNhatNgay_enable = true;
                                    this.btnCapNhatNgay_visible = true;
                                } else {
                                    this.btnCapNhatNgay_enable = false;
                                    this.btnCapNhatNgay_visible = false;
                                }
                            } else if (element.CONTROL_NAME.includes("tsbtnGiaoViec")) {
                                if (element.ENABLE === 1) {
                                    this.btnGiaoViec_enable = true;
                                    this.btnGiaoViec_visible = true;
                                } else {
                                    this.btnGiaoViec_enable = false;
                                    this.btnGiaoViec_visible = false;
                                }
                            } else if (element.CONTROL_NAME.includes("tsbtnGiaoPhieu")) {
                                if (element.ENABLE === 1) {
                                    this.btnGiaoPhieu_enable = true;
                                    this.btnGiaoPhieu_visible = true;
                                } else {
                                    this.btnGiaoPhieu_enable = false;
                                    this.btnGiaoPhieu_visible = false;
                                }
                            } else if (element.CONTROL_NAME.includes("tsbtnPhieuThu")) {
                                if (element.ENABLE === 1) {
                                    this.btnPhieuThu_enable = true;
                                    this.btnPhieuThu_visible = true;
                                } else {
                                    this.btnPhieuThu_enable = false;
                                    this.btnPhieuThu_visible = false;
                                }
                            } else if (element.CONTROL_NAME.includes("tsbtnKichHoat")) {
                                if (element.ENABLE === 1) {
                                    this.btnKichHoat_enable = true;
                                    this.btnKichHoat_visible = true;
                                } else {
                                    this.btnKichHoat_enable = false;
                                    this.btnKichHoat_visible = false;
                                }
                            } else if (element.CONTROL_NAME.includes("tsbtnHoanCong")) {
                                if (element.ENABLE === 1) {
                                    this.btnHoanCong_enable = true;
                                    this.btnHoanCong_visible = true;
                                } else {
                                    this.btnHoanCong_enable = false;
                                    this.btnHoanCong_visible = false;
                                }
                            } else if (element.CONTROL_NAME.includes("txtTongThu")) {
                                if (element.ENABLE === 1) {
                                    this.txtTongThu_enable = true;
                                } else {
                                    this.txtTongThu_enable = false;
                                }
                            } else if (element.CONTROL_NAME.includes("chkDaThuDuoc")) {
                                if (element.ENABLE === 1) {
                                    this.chkDaThuDuoc_enable = true;
                                } else {
                                    this.chkDaThuDuoc_enable = false;
                                }
                            } else if (element.CONTROL_NAME.includes("txtNDThucHien")) {
                                if (element.ENABLE === 1) {
                                    this.txtNDThucHien_enable = true;
                                } else {
                                    this.txtNDThucHien_enable = false;
                                }
                            } else if (element.CONTROL_NAME.includes("txtGhiChu")) {
                                if (element.ENABLE === 1) {
                                    this.txtGhiChu_enable = true;
                                } else {
                                    this.txtGhiChu_enable = false;
                                }
                            }
                        });
                    }
                }
                if (this.FORM_TO_TRUONG == 1) {
                    this.btnGiaoViec_enable = true;
                    this.btnGiaoViec_visible = true;
                }
            } catch (error) {
                console.log(error);
            }
            if (this.btnDuyet_visible) {
                this.tabPhieuDaHuy = true;
            } else {
                this.tabPhieuDaHuy = false;
            }
        },
        async getThamSoMDMats(thamSoMD) {
            try {
                this.loading(true);
                let data = {
                    pMaThamSo: thamSoMD, //"LAY_QUYTRINH_PROC"
                };

                let response = await API.getDanhSachThamSoMDMATS(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data;
                }
                return [];
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        async getDanhSachNhomKetQuaXuLy(dstthd_id) {
            try {
                this.loading(true);
                let data = {
                    pDSTTHDId: dstthd_id, // this.dstthd_id
                };
                let response = await API.getDanhSachNhomKetQuaXuLy(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data;
                }
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        async getDanhSachDichVuKhoaMayXacMinh(dsdichvuvt_id) {
            try {
                this.loading(true);
                let data = {
                    pDSDichVuVT: dsdichvuvt_id, // this.dsdichvuvt_id
                };

                let response = await API.getDanhSachDichVuKhoaMayXacMinh(
                    this.axios,
                    data
                );
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data;
                }
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        async getDanhSachLyDoTra() {
            try {
                this.loading(true);
                let response = await API.getDanhSachLyDoTra(this.axios, null);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data;
                }
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        async getDSThamSoMD() {
            try {
                this.loading(true);
                let response = await API.getDSThamSoMD(this.axios, null);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data;
                }
                return res;
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        itemSelectedChangedPhieuChoXN(value) {

            this.itemSelectedPhieuChuaThu = value;
        },
        itemSelectedChangedPhieuHuyXN(value) {

            this.itemSelectedPhieuHuy = value;
        },
        async getDSNhanVienTheoPhieuId(phieuId, id, kieuId) {
            try {
                let data = {
                    pPhieuId: phieuId,
                    pId: id,
                    pKieuId: kieuId,
                };
                let res = [];
                let response = await API.getDSNhanVienTheoPhieuId(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data;
                }
                return res;
            } catch (error) {
                console.log(error);
            }
        },
        formatPrice(value) {
            return new Intl.NumberFormat("vi-VN", {
                style: "currency",
                currency: "VND",
            }).format(value);
        },
        async itemFocusClick(index, value) {
            this.dtLyDoHuy = [];
        },
        async itemFocus(value) {

            if (value) {

                this.itemPhieuChuaThuFocus = value;
                this.phieu_id = value.PHIEU_ID;
                this.hdtb_id = value.HDTB_ID;
                this.loaitb_id = value.LOAITB_ID;
                this.ma_tt = value.MA_TT;
                this.thuebao_id = value.THUEBAO_ID;
                this.dtGiaoViec = await this.getDSNhanVienTheoPhieuId(
                    this.phieu_id,
                    token.getNhanVienID(),
                    2
                ).catch((err) => {
                    this.$toast.error(err.data.message_detail);
                });
                let dsNhomKq_KqXl = await this.getDSNhomKQKQXuLyId(this.phieu_id);
                let kqxl_id = "-1";
                if (dsNhomKq_KqXl.length) {
                    let nhomkq_id = dsNhomKq_KqXl[0].NHOMKQ_ID;
                    kqxl_id = dsNhomKq_KqXl[0].KQXL_ID;
                    let itemselect = this.cbbNhomKQ.filter(
                        (item) => item.NHOMKQ_ID == nhomkq_id
                    );
                    this.valueNhomKQSelected = itemselect.NHOMKQ_ID;
                    itemselect = this.cbbKQ.filter((item) => item.KQXL_ID == kqxl_id);
                    this.valueKQSelected = itemselect.KQXL_ID;
                }
                this.txtTongThu = value.TIEN_HD;
                //Nếu phải kích hoạt tổng đài
                if (this.phaiLam(this.luong_id, "KICHHOAT_TD")) {
                    let status = 0;
                    if (value.STATUS != 0) status = parseInt(value.STATUS);
                    else status = -1;

                    if (
                        status == 7 ||
                        status == 5
                    ) {
                        this.btnKichHoat_enable = false;
                        this.btnCapNhatNgay_enable = false;
                        this.btnHoanCong_enable = true;
                    } else {
                        this.btnKichHoat_enable = true;
                        this.btnCapNhatNgay_enable = true;
                        this.btnHoanCong_enable = false;
                    }
                }

                await this.hienThiDS_LyDoHuy(this.hdtb_id);
            } else {
                this.dtLyDoHuy = [];
            }
        },
        async getDSNhomKQKQXuLyId(phieuId) {
            try {
                let data = {
                    pPhieuId: phieuId,
                };
                let res = [];
                let response = await API.getDSNhomKQKQXuLyId(this.axios, data).catch(
                    (err) => {
                        this.$toast.error(err.data.message_detail);
                    }
                );
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data;
                }
                return res;
            } catch (error) {
                console.log(error);
            }
        },
        async hienThiDS_LyDoHuy(vhdtb_id) {
            try {
                let data = {
                    pHDTBId: vhdtb_id,
                };
                let res = [];
                let response = await API.getDsLyDoHuy(this.axios, data).catch((err) => {
                    this.$toast.error(err.data.message_detail);
                });

                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.dtLyDoHuy = response.data.data;
                    return response.data.data;
                }


                return res;
            } catch (error) {
                console.log(error);
            }
        },
        async btnXem() {
            await this.hienThiDanhSach(this.currentPage);
        },
        async selectedTab(value) {
            console.log(value);
            await this.hienThiGiaoDien(this.luong_id);
            if (value == "tabPhieuChoXN") {
                if (this.btnDuyet_visible) {
                    this.btnDuyet_enable = true;
                }
                this.currentPage = 0;
            } else if (value == "tabPhieuHuyXN") {
                this.currentPage = 1;
                this.btnPhieuThu_enable = false;
                this.btnGiaoViec_enable = false;
                this.btnDuyet_enable = false;
                this.btnCapNhatNgay_enable = false;
            }
            await this.hienThiDanhSach(this.currentPage);
        },
        Clear() {
            this.dtPhieuChuaThu = [];
            this.dtGiaoViec = [];
        },
        closePopUpCapNhatGP(id){
            console.log(id)
            this.handlerCapNhat(id)
        },
        async btnCapNhat() {
            this.isActionCapNhat = true
            let dsChon = this.$refs.gridPhieuChuaThu.getSelectedRecords();
            if (dsChon.length <= 0) {
                this.$toast.error("Bạn chưa chọn dữ liệu trên lưới để cập nhật!");
                return;
            }
            if (this.valueKQSelected == null || this.valueKQSelected == "") {
                this.$toast.error("Hãy chọn kết quả xử lý!");
                return;
            }
            if (
                // this.PHAINHAP_GHICHU_TTVT_XN_NO == 1 &&
                !this.txtNDThucHien
            ) {
                this.$toast.error("Hãy nhập nội dung thực hiện!");
                this.$refs.refNDThucHien.focus();
                return;
            }
            if (this.phaiLam(this.luong_id, "CHON_NVKT")) { // PHAILAM
                // if (
                //     this.valueNhomKQSelected == NHOM_KQXL.CHUA_THU_CUOC.toString() || this.valueNhomKQSelected == NHOM_KQXL.DA_THU_CO_NHUCAU_SUDUNG.toString() ||
                //     (this.valueNhomKQSelected == NHOM_KQXL.TTKD_DA_DON_DOC.toString() &&
                //         this.valueKQSelected == KETQUA_XL.KH_DONG_Y_THANHTOAN)
                // ){
                //     // do
                // } else {
                const selectDichVu = this.cbbDichVuVT.find(item => item.DICHVUVT_ID == this.valueDichVuVTSelected)

                this.$refs.popupCapNhatGiaoPhieu.vhdtb_id = this.hdtb_id
                this.$refs.popupCapNhatGiaoPhieu.vhuonggiao_id = this.huonggiao_id
                this.$refs.popupCapNhatGiaoPhieu.vquytrinh_id = this.quytrinh_id
                this.$refs.popupCapNhatGiaoPhieu.dsSelected = dsChon
                this.$refs.popupCapNhatGiaoPhieu.txtDichVuVT = selectDichVu.TEN_DVVT
                this.$refs.popupCapNhatGiaoPhieu.dsLyDoHuy = this.dtLyDoHuy
                this.$refs.popupCapNhatGiaoPhieu.openDialog()
                this.isActionCapNhat = false
                // }
            }
            if(this.isActionCapNhat){
                this.handlerCapNhat(null)
            }
        },
        async handlerCapNhat(idNhanVien){
            try {
                if (this.currentPage == 0) {
                    let dsChon = this.$refs.gridPhieuChuaThu.getSelectedRecords();
                    let nhom_kq = this.valueNhomKQSelected;

                    if (this.chkDaThuDuoc_checked && dsChon.length > 1) {
                        let ktNhieuRow = false;
                        let thanhtoan_id = dsChon[0].THANHTOAN_ID;
                        let lst2 = dsChon.filter(
                            (item) => item.THANHTOAN_ID != thanhtoan_id
                        );
                        if (lst2.length > 0) {
                            //nếu có row khác thanhtoan_id
                            this.$toast.error(
                                "Bạn đang chọn 'Đã thu được tiền', không thể thực hiện trên các thuê bao khác thanh toán!"
                            );
                            return;
                        }
                    }

                    try {
                        let dsPhieuChon = [];
                        dsChon.forEach((element) => {
                            dsPhieuChon.push({
                                THANHTOAN_ID: element.THANHTOAN_ID,
                                PHIEU_ID: element.PHIEU_ID,
                                MA_TB: element.MA_TB,
                                HDTB_ID: element.HDTB_ID,
                                TTHD_ID: element.TTHD_ID,
                                HOADON_ID: element.HOADON_ID,
                                KIEUDV_ID: element.KIEUDV_ID,
                                DC_DAUCUOI: element.DC_DAUCUOI,
                                DONVINHAN_ID: element.DONVINHAN_ID,
                                DICHVUVT_ID: element.DICHVUVT_ID,
                            });
                        });
                        if (!this.valueQuyTrinhSelected) {
                            return this.$toast.error("Combobox quy trình không hợp lệ!");
                        }
                        let data = {
                            pTongThu: this.txtTongThu,
                            pNhomKQ: nhom_kq,
                            pKetQuaXL: this.valueKQSelected,
                            pLuongId: this.luong_id,
                            pXacMinhTheoYC: this._XacMinh_TheoYeuCau,
                            pNDThucHien: this.txtNDThucHien,
                            pGhiChu: this.txtGhiChu,
                            pKyCuoc: this.kyCuoc,
                            pHuongGiaoId: this.huonggiao_id,
                            pQuyTrinhId: this.valueQuyTrinhSelected,
                            pDSPhieu: dsPhieuChon,
                            pNhanVienID: idNhanVien
                        };
                        let response = await API.putCapNhatXacMinhNoCuoc(
                            this.axios,
                            data
                        ).catch((err) => {
                            this.$toast.error(err.data.message_detail);
                        });
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            if (response.data.data.toString() == true.toString()) {
                                this.$toast.success("Cập nhật phiếu thành công!");
                            }
                        }
                    } catch (error) {
                        console.log(error);
                        //this.$toast.error(error.response.data.message_detail);
                    }
                }
            } catch (error) {
                console.log(error);
                // this.$toast.error(error.data.message_detail);
            } finally {
                this.Clear();
                this.hienThiDanhSach(this.currentPage);
            }
        },
        async btnDuyet() {
            try {
                if (this.currentPage == 0) {
                    this.loading(true);
                    let dsChon = [];
                    this.itemSelectedPhieuChuaThu.forEach((element) => {
                        this.dtPhieuChuaThu.forEach((item) => {
                            if (item.HDTB_ID == element) {
                                dsChon.push(item);
                            }
                        });
                    });
                    if (dsChon.length <= 0) {
                        this.$toast.error("Bạn chưa chọn dữ liệu trên lưới để cập nhật!");
                        return;
                    }
                    if (this.valueKQSelected == "") {
                        this.$toast.error("Bạn chưa chọn kết quả xử lý để cập nhật!");
                        return;
                    }



                    let dsPhieuChon = [];
                    dsChon.forEach((element) => {
                        dsPhieuChon.push({
                            PHIEU_ID: element.PHIEU_ID,
                            HDTB_ID: element.HDTB_ID,
                            TTHD_ID: element.TTHD_ID,
                            HOADON_ID: element.HOADON_ID,
                            DICHVUVT_ID: element.DICHVUVT_ID,
                        });
                    });
                    let data = {
                        pKetQuaXL: this.valueKQSelected,
                        pNDThucHien: this.txtNDThucHien,
                        pGhiChu: this.txtGhiChu,
                        pKyCuoc: this.kyCuoc,
                        pHuongGiaoId: this.huonggiao_id,
                        pQuyTrinhId: this.valueQuyTrinhSelected,
                        pMayCN: null,
                        pIPCN: "WEB",
                        pDSPhieu: dsPhieuChon,
                    };
                    if (this.valueKQSelected == 29 && this.TUDONG_HOANCONG_KHOAMAY == 1) {

                        //await this.onClickKichHoat();
                        //this.hienThiDanhSach(this.currentPage);
                        this.dsKichHoat = this.dtPhieuChuaThu.filter((item) => this.itemSelectedPhieuChuaThu.includes(item.HDTB_ID));
                        this.loading(true);
                        this.dsKichHoat.forEach((element) => {
                            // console.log(element);
                            this.HTHSHoanCong(element);
                        });

                    }
                    else {
                        let response = await API.putCapNhatDuyetNoCuoc(
                            this.axios,
                            data
                        ).catch((err) => {
                            this.$toast.error(err.data.message_detail);
                        });
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            if (response.data.data == true) {
                                this.$toast.success("Duyệt phiếu thành công!");
                            }
                        }
                    }
                    this.Clear();
                    this.hienThiDanhSach();
                }
            } catch (error) {
                console.log(error);
                // this.$toast.error(error.response.data.message_detail);
            } finally {
                this.loading(false);
            }
        },

        async onClickHoanCong() {

            //if (await this.validateHoanCong()) {


            this.dsKichHoat = this.dtPhieuChuaThu.filter((item) => this.itemSelectedPhieuChuaThu.includes(item.HDTB_ID));

            if (this.dsKichHoat.length > 0) {
                this.loading(true);
                this.dsKichHoat.forEach((element) => {
                    // console.log(element);
                    if (element.STATUS != '' || element.STATUS != 0)
                        this.HTHSHoanCong(element);
                });
            }


            //}
        },
        async onClickKichHoat() {
            if (await this.validateKichHoat()) {

                this.msgKichHoat = this.msgThongBao[this.valueDichVuVTSelected];
                this.dsKichHoat = this.dtPhieuChuaThu.filter((item) => this.itemSelectedPhieuChuaThu.includes(item.HDTB_ID));
                this.dsKichHoat.length > 0 && this.$bvModal.show("notice-kichhoat");
            }
        },
        async closeKHModal(value) {
            this.$bvModal.hide("notice-kichhoat");
            if (value) {
                // const lstChosen = this.dtPhieuChuaThu.filter((item) => this.itemSelectedPhieuChuaThu.includes(item.HDTB_ID));

                let promises = []
                this.dsKichHoat.forEach((element) => {
                    promises.push(this.kichHoat(element))
                });

                const res = await Promise.all(promises)
                const ok = res.filter(x => x === true).length
                const failed = res.filter(x => x !== true)
                if (ok > 0) {
                    this.$toast.success(`Đã kích hoạt thành công ${ok} / ${res.length}`)
                    // this.getDsThueBao()
                    await this.hienThiDanhSach(this.currentPage)
                } else {
                    this.$toast.error(`Lỗi khi kích hoạt ${failed.length} / Tổng số ${res.length} mục`)
                    this.$toast.error(failed.join('\r\n'))
                }
            }
        },
        validateKichHoat: async function () {
            this.thongbao = "Thông báo:";
            const lstErr = this.dtPhieuChuaThu.filter((item) => this.itemSelectedPhieuChuaThu.includes(item.HDTB_ID) && item.NGAY_HT_GP == "");
            if (lstErr.length > 0) {
                this.$toast.error(`Thuê bao: ${lstErr[0].MA_TB} chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!`);
                return false;
            }

            if (this.dtGiaoViec.length == 0) {
                this.$toast.error(`Bạn chưa cập nhật thông tin giao việc cho nhân viên!`);
                return false;
            }
            const lstChosen = this.dtPhieuChuaThu.filter((item) => this.itemSelectedPhieuChuaThu.includes(item.HDTB_ID));

            if (lstChosen.length == 0) {
                this.$toast.error(`Bạn chưa chọn thuê bao trên lưới để kích hoạt !`);
                return false;
            }

            const statusErr = lstChosen.filter((item) => item.STATUS == 5 || item.STATUS == 7);
            if (statusErr.length > 0) {
                this.$toast.error(`Thuê bao: ${statusErr[0].MA_TB} đã được kích hoạt!`);
                return false;
            }
            const ttHdErr = lstChosen.filter((item) => item.TTHD_ID == 6 || item.TTHD_ID == 7);
            if (ttHdErr.length > 0) {
                this.$toast.error(`Hợp đồng đã hoàn thiện. Hãy lấy lại thông tin trước khi kích hoạt!`);
                return false;
            }

            let msg = "";
            let rst = true;
            if (this.btnKichHoat_visible) {
                for (let index = 0; index < lstChosen.length; index++) {
                    const element = lstChosen[index];
                    const tthdId = await this.getTTHDId(element.HDTB_ID);
                    if (tthdId == 6 || (tthdId == 7)) {
                        msg = `${element.MA_TB} đã Hoàn thiện hoặc Thoái trả. Hãy lấy lại thông tin trước khi kích hoạt!`;
                        rst = false;
                        break;
                    }

                }
                if (!rst) {
                    this.$toast.error(msg);
                    return false;
                }
            }

            return true;
        },
        getTTHDId: async function (hdtbId) {
            const body = {
                "tenId": "TTHD_ID",
                "ma": hdtbId
            };
            try {
                var rs = await this.$root.context.post("/web-thuno/api/thu-no/common/lay-id-theo-ma", body);
                if (rs.data && rs.data.length > 0) {
                    return rs.data[0].TTHD_ID;
                }
                return "";
            } catch (error) { }
        },
        kichHoat: async function (body) {
            try {
                const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/kich-hoat-dich-vu/hop-dong-thue-bao/${body.HDTB_ID}`);
                // console.log(data);
                if (data && data.error == "200") {
                    const res = await this.nangTocDo(body);
                    return res
                } else {
                    // this.$toast.error("Kích hoạt thất bại!");
                    let s = data !== undefined && data !== null && data.message_detail !== undefined ? data.message_detail : 'Kích hoạt thất bại!'
                    return `- ${body.MA_TB} : ${s}`
                }
            } catch (error) {
                // this.$toast.error(error.data.message_detail);
                // return false
                let s = error.data !== undefined && error.data !== null && error.data.message_detail !== undefined ? error.data.message_detail : 'Kích hoạt thất bại!'
                return `- ${body.MA_TB} : ${s}`
            }
        },
        nangTocDo: async function (body) {
            try {
                const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/nang-toc-do/${body.THUEBAO_ID}`);
                // console.log(data);
                if (data && data.error == "200") {
                    const res = await this.capNhatNgay(body)
                    return res
                    // this.$toast.success("Kích hoạt thành công!");
                    // this.getDsThueBao();
                } else {
                    // this.$toast.error("Kích hoạt thất bại!");
                    // return false
                    let s = data !== undefined && data !== null && data.message_detail !== undefined ? data.message_detail : 'Kích hoạt thất bại!'
                    return `- ${body.MA_TB} : ${s}`
                }
            } catch (error) {
                // this.$toast.error("Kích hoạt thất bại!");
                // return false
                let s = error.data !== undefined && error.data !== null && error.data.message_detail !== undefined ? error.data.message_detail : 'Kích hoạt thất bại!'
                return `- ${body.MA_TB} : ${s}`
            }
        },
        capNhatNgay: async function (body) {
            // console.log('abc', body);
            const bd = {
                "hdtbId": body.HDTB_ID,
                "ngayKH": moment(new Date()).format("DD/MM/YYYY"),
                "ngayHT": moment(new Date()).format("DD/MM/YYYY"),
                "status": body.DICHVUVT_ID == 1 ? 7 : 5
            }
            try {
                const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/cap-nhat-ngay-kh-ngay-ht-v2`, bd);
                // console.log(data);
                if (data && data.error == "200") {
                    // this.$toast.success("Kích hoạt thành công!");
                    // this.getDsThueBao();
                    return true
                } else {
                    // this.$toast.error("Kích hoạt thất bại!");
                    // return false
                    let s = data !== undefined && data !== null && data.message_detail !== undefined ? data.message_detail : 'Kích hoạt thất bại!'
                    return `- ${body.MA_TB} : ${s}`
                }
            } catch (error) {
                // this.$toast.error("Kích hoạt thất bại!");
                // return false
                let s = error.data !== undefined && error.data !== null && error.data.message_detail !== undefined ? error.data.message_detail : 'Kích hoạt thất bại!'
                return `- ${body.MA_TB} : ${s}`
            }
        },
        exportExcel() {
            if (this.dtPhieuChuaThu.length) {
                this.$bvModal.show("modal-export");
            } else {
                this.$toast.error("Không có dữ liệu để xuất Excel !");
            }
        },
        async removeLyDoHuy(arg) {
            // let element = this.dtLyDoHuy[value.index];
            let element = arg.rowData;
            if (element.KIEU == "1") {
                this.$toast.error(
                    "Bạn không được phép xóa lý do hủy của Giao dịch viên đã nhập."
                );
                return;
            }
            if (this.dstthd_id == TrangThaiHD.NVKD_XACMINH.toString()) {
                if (element.KIEU != 3) {
                    this.$toast.error("Bạn không được phép xóa lý do hủy này.");
                    return;
                }
            }
            if (this.dstthd_id == TrangThaiHD.TTVT_XACMINH_NO.toString()) {
                if (element.KIEU != 2) {
                    this.$toast.error("Bạn không được phép xóa lý do hủy này.");
                    return;
                }
            }

            await this.$bvModal
                .msgBoxConfirm("Bạn chắc chắn muốn xóa lý do hủy ?", {
                    title: "Thông báo",
                    size: "lg",
                    buttonSize: "lg",
                    okVariant: "primary",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy bỏ",
                    hideHeaderClose: false,
                    centered: true,
                    "modal-class": ["f18", "text-center"],
                })
                .then(async (value) => {
                    if (value) {
                        this.loading(true);
                        let data = {
                            pHDTBId: this.hdtb_id,
                            pLyDoHuyId: element.LYDOHUY_ID,
                        };
                        const response = await API.deleteLyDoHuy(this.axios, data).catch(
                            (err) => {
                                this.$toast.error(err.data.message_detail);
                            }
                        );
                        if (response.data.error_code === "BSS-00000000") {
                            this.loading(false);
                            this.$toast.success(`Xóa thành công!`);
                            this.hienThiDS_LyDoHuy(this.hdtb_id);
                        } else {
                            this.loading(false);
                            this.$toast.error(response.data.message_detail);
                        }
                    }
                })
                .catch((err) => {
                    this.loading(false);
                    // this.$toast.error(err.response.data.message_detail);
                });
        },
        async showLyDoHuy() {
            if (!this.itemPhieuChuaThuFocus || this.itemPhieuChuaThuFocus == {}) {
                return this.$toast.error(
                    "Bạn chưa chọn dữ liệu trên lưới phiếu chờ xác nhận!"
                );
            }
            let DONGBO_LYDOHUY_GDV_NVKT = 0;
            let resMDs = await this.getThamSoMDMats("DONGBO_LYDOHUY_GDV_NVKT");
            if (resMDs != null && resMDs.length > 0) {
                DONGBO_LYDOHUY_GDV_NVKT = resMDs[0].MACDINH;
            }
            if (this._XacMinh_TheoYeuCau == 1 && DONGBO_LYDOHUY_GDV_NVKT == 1) {
                this.loaild_id = 1;
            } else {
                this.loaild_id = 2;
            }
            if (this.dstthd_id == TrangThaiHD.NVKD_XACMINH) {
                this.kieu_lydohuy_lhtb = 3;
            }
            this.$bvModal.show("modal-lydohuy");
        },
        resetModal() {
            this.hienThiDS_LyDoHuy(this.hdtb_id);
        },
        dateToString(value, format = "DD/MM/YYYY") {
            return moment(value).format(format);
        },
        btnGiaoViec() {
            let phieuId = this.itemPhieuChuaThuFocus.PHIEU_ID;
            let hdtbId = this.itemPhieuChuaThuFocus.HDTB_ID;
            let maTB = this.itemPhieuChuaThuFocus.MA_TB;
            this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(
                phieuId,
                this.$auth.getDonViID(),
                this.$auth.getNhanVienID(),
                this.dateToString(new Date())
            );
            this.$refs.frnGiaoPhieuNV.vma_tb = maTB;
            this.$refs.frnGiaoPhieuNV.vhdtb_id = hdtbId;
            this.$nextTick(() => {
                this.$refs.frnGiaoPhieuNV.load();
                this.$refs.popupfrnGiaoPhieuNV.show();
            });
        },
        async giaoViecClosed() {
            try {
                this.loading(true);
                let response = await API.getDSNhanVienTheoPhieuId(this.axios, {
                    pPhieuId: this.phieu_id,
                    pId: this.$auth.getNhanVienID(),
                    pKieuId: 2,
                });
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.dtGiaoViec = response.data.data || [];
                }
            } catch (error) {
                this.$toast.error(error.data.error_code);
            } finally {
                this.loading(false);
            }
        },
        onClickKS() {
            this.$bvModal.show("modal-lichsu-ks");
        },
        selectedRowChanged(data) {
            if (data) this.thuebao_id = data.THUEBAO_ID;
        },

        hoanCong: async function (body) {
            try {
                const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/kich-hoat-hoan-cong`, body);
                if (data && data.error == "200") {
                    this.$toast.success("Tự động hoàn công thành công!");
                    this.loading(false);
                    this.hienThiDanhSach(this.currentPage);
                } else {
                    this.$toast.error("Tự động hoàn công thất bại!");
                    this.loading(false);
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
                this.loading(false);
            }
        },
        async HTHSHoanCong(item) {
            if (this.valueKQSelected == null || this.valueKQSelected == "") {
                this.$toast.error("Hãy chọn kết quả xử lý!");
                this.loading(false);
                return;
            }

            if (
                !this.txtNDThucHien
            ) {
                this.$toast.error("Hãy nhập nội dung thực hiện!");
                this.loading(false);
                this.$refs.refNDThucHien.focus();
                return;
            }
            const ds = [{
                chieuKhoa: item.CHIEUKHOA,
                dichVuVTId: item.DICHVUVT_ID,
                giaoOMC: 2,
                hdkhId: item.HDKH_ID,
                hdtbId: item.HDTB_ID,
                kieuQuyTrinhId: 0,
                loaiTBId: item.LOAITB_ID,
                maTB: item.MA_TB,
                phieuId: item.PHIEU_ID,
                status: item.DICHVUVT_ID == 4 ? 5 : 7,
                thueBaoId: item.THUEBAO_ID,
            }];
            const body = {
                boQuaLoiHoanCong: 0,
                donViId: this.$auth.getDonViID(),
                ds: ds,
                hdtbId: item.HDTB_ID,
                huongGiao: this.huonggiao_id,
                kieuYC: 1,
                luongId: this.luong_id,
                maNguoiDung: this.$auth.getMaNhanVien(),
                ngayGV: moment(new Date).format("DD/MM/YYYY HH:mm"),
                ngayHT: moment(new Date).format("DD/MM/YYYY HH:mm"),
                nhanVienId: this.$auth.getNhanVienID(),
                thuHoiThietBi: this.rdoChecked = this.rdoGiao_TheoQD_checkedText ? 1 : 0,
            };
            this.hoanCong(body);

        },

    },
    created() {
        this.fetchKyCuoc();
    },
    async mounted() {

        if (this.tag) {
            let dataSplit = this.tag.split("-");

            if (dataSplit.length >= 1) {
                this.dstthd_id = dataSplit[0];
                //this.dstthd_id = this.dstthd_id.replaceAll(";", ",");
            }
            if (dataSplit.length >= 2) {
                if (dataSplit[1] == "NEW") {
                    this.IS_NEW_FORM = 1;
                } else {

                    this.dshuonggiao_id = dataSplit[1];

                    //this.dshuonggiao_id = this.dshuonggiao_id.replaceAll(";", ",");
                }
            }
            if (dataSplit.length >= 3) {
                if (dataSplit[2] == "NEW") {
                    this.IS_NEW_FORM = 1;
                } else {
                    this.dsdichvuvt_id = dataSplit[2];
                    //this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(";", ",");
                }
            }

            if (dataSplit.length >= 4) {
                if (dataSplit[3] == "NEW") {
                    this.IS_NEW_FORM = 1;
                } else if (dataSplit[3] == "FTOTRUONG") {
                    this.FORM_TO_TRUONG = 1;
                }
            }
            if (dataSplit.length >= 5) {
                if (dataSplit[4] == "FTOTRUONG") {
                    this.FORM_TO_TRUONG = 1;
                }
            }
        } else {
            this.dstthd_id = 1;
            this.dshuonggiao_id = 1;
        }
        if (
            this.dstthd_id == enumTTHD.DANG_THI_CONG ||
            this.dstthd_id == enumTTHD.NVKD_XACMINH ||
            this.dstthd_id == enumTTHD.TTVT_XACMINH_NO
        ) {
            this.btnLyDoHuyVisibled = true;
        }
        //laythamsomd
        let resMD = await this.getThamSoMDMats("LAY_QUYTRINH_PROC");
        if (resMD.length && this.IS_NEW_FORM == 1) {
            this._XacMinh_TheoYeuCau = resMD[0].MACDINH;
        }
        //getDanhSachNhomKetQuaXuLy
        this.cbbNhomKQ = await this.getDanhSachNhomKetQuaXuLy(this.dstthd_id);
        this.valueNhomKQSelected = this.setFirstValue(this.cbbNhomKQ, "NHOMKQ_ID");
        await this.cbbNhomKQChanged();
        //getDanhSachDichVuKhoaMayXacMinh cbbDVVT
        this.cbbDichVuVT = await this.getDanhSachDichVuKhoaMayXacMinh(
            this.dsdichvuvt_id
        );
        this.valueDichVuVTSelected = this.setFirstValue(
            this.cbbDichVuVT,
            "DICHVUVT_ID"
        );
        //getDanhSachLyDoTra dsLyDoTra
        this.dsLyDoTra = await this.getDanhSachLyDoTra();
        //ds tham soMD
        this.dsThamSoMD = await this.getDSThamSoMD();

        let KHONG_HT_THUTIEN_KM = -1;
        if (this.dsThamSoMD.length > 0) {
            //KHONG_HT_THUTIEN_KM
            let str_vtemp = "KHONG_HT_THUTIEN_KM";
            let dataCP = this.dsThamSoMD.filter((item) => item.MA_TS == str_vtemp);
            if (dataCP.length > 0) {
                if (dataCP[0].TEN_TS.toString().trim() == "1") KHONG_HT_THUTIEN_KM = 1;
            }
            //PHAINHAP_GHICHU_TTVT_XN_NO
            str_vtemp = "PHAINHAP_GHICHU_TTVT_XN_NO";
            dataCP = this.dsThamSoMD.filter((item) => item.MA_TS == str_vtemp);
            if (dataCP.length > 0) {
                if (dataCP[0].TEN_TS.toString().trim() == "1")
                    this.PHAINHAP_GHICHU_TTVT_XN_NO = 1;
            }
            //NGAY_THUHOI
            str_vtemp = "NGAY_THUHOI";
            dataCP = this.dsThamSoMD.filter((item) => item.MA_TS == str_vtemp);
            if (dataCP.length > 0) {
                if (
                    dataCP[0].TEN_TS != null &&
                    dataCP[0].TEN_TS != "" &&
                    dataCP[0].TEN_TS.toString().trim() != "0"
                ) {
                    this.NGAY_THUHOI = parseInt(dataCP[0].TEN_TS.toString().trim());
                }
            }
            //PHAINHAP_GHICHU_TTVT_XN_NO
            str_vtemp = "TUDONG_HOANCONG_KHOAMAY";
            dataCP = this.dsThamSoMD.filter((item) => item.MA_TS == str_vtemp);
            if (dataCP.length > 0) {
                if (dataCP[0].TEN_TS.toString().trim() == "1")
                    this.TUDONG_HOANCONG_KHOAMAY = 1;
            }
            //PHAINHAP_LYDOHUY
            str_vtemp = "PHAINHAP_LYDOHUY";
            dataCP = this.dsThamSoMD.filter((item) => item.MA_TS == str_vtemp);
            if (dataCP.length > 0) {
                if (dataCP[0].TEN_TS.toString().trim() == "1")
                    this.PHAINHAP_LYDOHUY = 1;
            }
        }
        if (KHONG_HT_THUTIEN_KM == 1) {
            this.chkDaThuDuoc_visible = false;
            this.txtTongThu_visible = false;
        }
        if (this.NGAY_THUHOI != -1) {
            // rdoGiao_TheoQD.Text = "Giao theo QĐ (" + NGAY_THUHOI + " ngày)";
            this.kt_thamso_thuhoi = true;
        } else {
            this.rdoChecked = this.rdoGiaoNgay_checkedText;
            this.rdoGiaoNgay_enable = false;
            this.rdoGiao_TheoQD_enable = false;
        }
        this.cbbQuyTrinh = await this.getQuyTrinh();

        this.valueQuyTrinhSelected = this.setFirstValue(
            this.cbbQuyTrinh,
            "QUYTRINH_ID"
        );
    },
};
</script>
