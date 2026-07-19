<template src="./AcceptanceIncedentReportsOMCNet119.html" />
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import breadcrumb from "@/components/breadcrumb";
import api from "./AcceptanceIncedentReportsOMCNet119.js";
import moment from "moment";
import columns from "./columns.js";
import {
    Query
} from "@syncfusion/ej2-data";
import Vue from "vue";
import {
    DropDownListPlugin
} from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
import enums from "./enums";
import {
  KENHTN_BAOHONG
} from "../ProcessAcceptanceIncident/enum/KENHTN_BAOHONG";
import ModalChonToaDo from "@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo";
// import DeliveryIntermediate from '@/modules/install/DeliveryIntermediate/DeliveryIntermediate.vue'
import TraPhieuBaoHong from "../HandleAcceptanceIncidentReports/Popups/TraPhieuBaoHong/TraPhieuBaoHong.vue";
import GiaoPhieuNhanVien from "../HandleAcceptanceIncidentReports/Popups/GiaoPhieuNhanVien/GiaoPhieuNhanVien.vue";
import GiaoPhieuNhanVien_v2 from "../HandleAcceptanceIncidentReports/Popups/GiaoPhieuNhanVien_v2/GiaoPhieuNhanVien.vue";
import DoiTac from "../HandleAcceptanceIncidentReports/Popups/DoiTac.vue";
import XLSX from "xlsx";
import ChonSoNgay from "../HandleAcceptanceIncidentReports/Popups/ChonSoNgay.vue";
import {
    MultiSelectPlugin,
    CheckBoxSelection,
} from "@syncfusion/ej2-vue-dropdowns";
import ChuyenTo from "../HandleAcceptanceIncidentReports/Popups/ChuyenTo.vue";
import KetQuaDoKiem from "../HandleAcceptanceIncidentReports/Popups/DoKiem/DoKiemModal.vue";
import daunoithuebao from "../../../CABMAN/tracuu-thuebao-theo-cap/tracuu-dandocap-theothuebao"
import NoiDungGiao from "../HandleAcceptanceIncidentReports/Popups/NoiDungGiao.vue";
import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue";
import VatTuBaoHong from "../HandleAcceptanceIncidentReports/Popups/VatTuBaoHong/VatTuBaoHong.vue";
import TraCuuMDF from "../HandleAcceptanceIncidentReports/Popups/TraCuuMDF/TraCuuMDF.vue";
import ThayDoiTTBH from "../HandleAcceptanceIncidentReports/Popups/ThayDoiTTBH/ThayDoiTTBH.vue";
import LichSu_HenBH from "../HandleAcceptanceIncidentReports/Popups/LichSu_HenBH/LichSu_HenBH.vue";
import KhaoSatKH from "../ProcessAcceptanceIncident/components/PopUps/KhaoSatKH/KhaoSatKH.vue";
import InBienBan from "../../../print/InBienBan/InBienBan.vue";
import NhacViecBH from '../ProcessAcceptanceIncident/components/PopUps/NhacViecBH';
import CheckPort from "../../../search/subscriber/CheckPort";
export default {
    provide: {
        multiselect: [CheckBoxSelection],
    },
    name: "HandleAcceptanceIncidentReports",
    components: {
        DatePicker,
        breadcrumb,
        ModalChonToaDo,
        TraPhieuBaoHong,
        GiaoPhieuNhanVien,
        GiaoPhieuNhanVien_v2,
        DoiTac,
        ChonSoNgay,
        ChuyenTo,
        KetQuaDoKiem,
        daunoithuebao,
        NoiDungGiao,
        CapVatTuTB,
        VatTuBaoHong,
        TraCuuMDF,
        ThayDoiTTBH,
        LichSu_HenBH,
        KhaoSatKH,
        InBienBan,
        NhacViecBH,
        CheckPort
    },
    props: {
        tag_modal: {
            type: String,
            default: 5,
        },
        kieu_Load: {
            type: Number,
            default: -1,
        },
    },
    data() {
        return {
            header: {
                title: "[OMC/Đối tác -> Điểm chia] - Xử lý/nghiệm thu báo hỏng",
                list: [{
                        name: "Báo hỏng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "[OMC/Đối tác -> Điểm chia] - Xử lý/nghiệm thu báo hỏng",
                        link: {
                            name: "Ui.cards"
                        },
                    },
                ],
            },
            ...columns,
            model: {},
            ngay_ht: moment(new Date()).format("DD/MM/yyyy HH:mm"),
            Tag: "",
            visible: {
                lblSuCoTTVT: false,
                tsbtnInPhieuTC: false,
                tsbtnTCTienTrinhBH: false,
                tsbtnHenKH: false,
                tsbtnShowBras: false,
                tsmGiuPhieu: false,
                tsmHuyGiuPHieu: false,
                tsbtnThayDoiTT_BH: false,
                tsbtnVatTuMoi: false,
                tsbtnVatTu: false,
                tsbtnNghiemThu: true,
                tsbtnHoanCong: false,
                tsbtnChuyenDoiTac: false,
                btnGiaoPhieuTG: false,
                tsbtnTraPhieu: false,
                tsbtnChuyenTTDH: false,
                tsbtnChuyenTTVT: false,
                tsbtnChuyenTo: false,
                tsbtnChuyenTheoDoi: false,
                tsbtnNghiemThuNhieuPhieu: false,
                tsbtnBaoTon_BH: false,
                tsbtnCapNhatNhieuPhieu: false,
                tsbtnCapNhat: true,
                tsbtnGiaoViecNhieu: true,
                tsbtnGiaoViec: false,
                chkTuNgay: false,
                dtpTuNgay: false,
                lblDenNgay: false,
                dtpDenNgay: false,
                tsbtnKQ_DoKiem: false,
                tungaydenngay: false,
                cboTramVT: true,
                cboDaGiaoViec: true,
                chkChoXL: true,
                tsbtnHoanCongNET: false,
                tsbtnHoanCongOMC: false,
                tsbtnKhaoSat: false,
                pnlNguyenNhan1: true,
                pnlNguyenNhan2: true,
                pnlTon1: false,
                pnlTon2: false,
                txtHangSx: false,
                txtSoNgay: false,
                dtpNgayXN: false,
            },
            enable: {
                tsbtnThayDoiTT_BH: false,
                tsbtnVatTuMoi: false,
                tsbtnVatTu: false,
                tsbtnNghiemThu: false,
                tsbtnHoanCong: false,
                tsbtnChuyenDoiTac: false,
                btnGiaoPhieuTG: false,
                tsbtnTraPhieu: false,
                tsbtnChuyenTTDH: false,
                tsbtnChuyenTTVT: false,
                tsbtnChuyenTo: false,
                ctmGiuPhieu: false,
                cboKQXL: false,
                timer1: false,
                tsbtnHoanCongNET: false,
                tsbtnHoanCongOMC: false,
                dtpNgayTH: false,
                dtpNgayXN: false,
            },
            lblSuCoTTVT: "",
            ds_nvxl: [],
            ds_phieubaohong: [],
            ds_phieubaohong_real: [],
            ds_phieubaohong_selected: [],
            kieugoi_id: 0,
            huonggiao_id: null,
            tag_TuDong_LayTT: 0,
            BAOHONG_MANG_LOPTREN_NET: -1,
            CAPNHAT_XLSC_NHIEUPHIEU: -1,
            KIEMTRA_DOKIEM_NGUYENNHAN: -1,
            ts_capnhat_ont: 0,
            NGHIEMTHU_BH_1_NGUYENNHAN: -1,
            GIAOPHIEU_BAOHONG_CATCHUYEN: -1,
            GIOIHAN_NGUYENNHAN_BAOHONG: -1,
            iServer_DK: 1,
            vkyhoadon: "",
            vma_tb: "",
            vuser_alt: null,
            vpass_alt: null,
            vuser_xtest: null,
            vpass_xtest: null,
            ds_nhomton: [],
            ds_dichvuvt: [],
            ds_nguoigv: [],
            ds_tramvt: [],
            tramvt_id: null,
            ds_cboDagiaoviec: [],
            cboDagiaoviec_id: null,
            dtpNgayBH: null,
            dtpNgayTH: null,
            dtpTuNgay: null,
            dtpDenNgay: null,
            kt_load: true,
            ds_kqxl: [{
                    ID: "0",
                    NAME: "Đã xử lý xong"
                },
                {
                    ID: "1",
                    NAME: "Không xử lý được"
                },
                {
                    ID: "2",
                    NAME: "Đang chờ xử lý"
                },
            ],
            kqxl_id: "0",
            ttbh_id: null,
            ds_TBSC: [],
            dtList: [],
            LOAITB_ID: null,
            txtMaTB: "",
            txtTenTB: "",
            txtDiaChiLD: "",
            txtTrangThaiTB: "",
            txtGhiChuHong: "",
            txtDactrung: "",
            ds_tinhtrang: [],
            tinhtrang_id: null,
            ds_nguyennhan: [],
            ds_nguyennhan_id: [],
            txtGhiChuTH: "",
            txtNguoiBH: "",
            txtDienThoaiLH: "",
            txtLoaiTB: "",
            ds_quytrinh: [],
            quytrinh_id: "",
            dtThaoTac: [],
            ds_nhomNN: [],
            nhomnn_id: null,
            labelFunctionTitle: "",
            lblTramVT: "",
            luong_id: null,
            PHIEU_ID: null,
            trangthaibh_id: -1,
            baohong_id: null,
            thuebao_id: null,
            vma_tb: "",
            nhomtt_id: -1,
            nv_giao_id: 0,
            txtHangSx: "",
            ds_lydoton: [],
            ds_lydoton_id: [],
            dtpNgayGV: moment(new Date()).format("DD/MM/yyyy HH:mm"),
            congviec_th: "",
            nhanvien_th_id: 0,
            nv_giao_id: 0,
            r_ghichu: "",
            _nhomnn_id: 0,
            diachidau: null,
            diachidau_id: 0,
            phodau_id: null,
            phuongdau_id: null,
            quandau_id: null,
            sonhadau: null,
            diachicuoi: null,
            diachicuoi_id: 0,
            phocuoi_id: null,
            phuongcuoi_id: null,
            quancuoi_id: null,
            sonhacuoi: null,
            map: {
                lat: null,
                lng: null,
            },
            map_info: {
                dia_chi: "",
                title: "",
            },
            gpnv_vphieu: null,
            gpnv_vdonvi: null,
            gpnv_vnhanvien_giao_id: null,
            gpnv_vngaygiao: "",
            gpnv_vthuebao_id: 0,
            gpnv_vma_tb: "",
            txtSoNgay: "",
            ds_tt: [],
            ds_nn: [],
            ds_ton: [],
            ttnd_att_adsl: 0,
            ttnd_att_gpon: 0,
            ttnd_snr_adsl: 0,
            chuyento_tthdId: -1,
            chuyento_ttbhId: -1,
            chuyento_bhId: -1,
            dokiem_hdtb_id: 0,
            dokiem_baohong_id: 0,
            gpnv_v2_vdonvi: null,
            gpnv_v2_vnhanvien_giao_id: null,
            gpnv_v2_vngaygiao: "",
            gpnv_v2_lstChon: [],
            IS_USING_CABMAN_V2: false,
            modelCapVatTu: {},
            dtpNgayXN: null,
            tooltipContent: "",
            inBB: {
                baohong: true,
                n_baohong_id: 0,
                n_phieu_id: 0,
                huonggiao_id: 0,
                nvth_id: 0,
            },
            checkPortDialog: {
                isVisiable: false,
            },
            animationSettings: { effect: 'None' },
        };
    },
    computed: {
        nhomton_id: {
            get() {
                return Number(this.model.nhomton_id);
            },
            set(val) {
                if (val) this.$set(this.model, "nhomton_id", val);
            },
        },
        dichvuvt_id: {
            get() {
                return Number(this.model.dichvuvt_id);
            },
            set(val) {
                if (val) this.$set(this.model, "dichvuvt_id", val);
            },
        },
        nguoigv_id: {
            get() {
                return Number(this.model.nguoigv_id);
            },
            set(val) {
                if (val) this.$set(this.model, "nguoigv_id", val);
            },
        },
    },
    mounted() {
        this.init();
    },
    methods: {
        async init() {
            try {
                this.loading(true);
                await this.getTTND();
                await this.frmNghiemThuBH_Load();
                await this.frmNghiemThuBH_FormLoad();
            } catch (e) {
                console.log(e);
                this.$toast.error("Xảy ra lỗi: " + e);
            } finally {
                this.loading(false);
            }
        },
        setTooltipContent(event) {
        // Lấy dữ liệu để đặt nội dung cho tooltip, ví dụ:
        const selectedValue = this.ds_nguyennhan_id;
        const selectedItem = this.ds_nguyennhan.find(item => item.cthong_id === selectedValue);
        
        // Đặt nội dung cho tooltip
        event.currentTarget.title = selectedItem ? selectedItem.ct_hong : '';
        },
        async getTTND() {
            try {
                let response = await api.ur_41003_000_CSS_TINH(this.axios);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    let result = response.data.data;
                    for (let i = 0; i < result.length; i++) {
                        if (result[i].TINH_ID == this.$root.token.getPhanVungID()) {
                            this.ttnd_att_adsl = result[i].ATT_ADSL;
                            this.ttnd_att_gpon = result[i].ATT_GPON;
                            this.ttnd_snr_adsl = result[i].SNR_ADSL;
                            break;
                        }
                    }
                }
            } catch (e) {
                console.log(e);
            }
        },
        frmNghiemThuBH_Load() {
            try {
                if (this.kieugoi_id == 0)
                    this.Tag = this.$route.query.tag || this.tag_modal || 5;
                
                if (this.Tag != "") {
                    if (this.Tag.toString().includes("+")) {
                        let words = this.Tag.split("+");
                        if (words.length >= 1) this.kieugoi_id = Number(words[0]);
                        if (words.length >= 2) this.huonggiao_id = Number(words[1]);
                        if (words.length >= 3) this.tag_TuDong_LayTT = Number(words[2]);
                    } else this.kieugoi_id = Number(this.Tag);
                } else this.kieugoi_id = 0;
                
                if (this.$root.token.getMaTinh() == "HNI") {
                    let objIndex = this.columnsPBH.findIndex(
                        (obj) => obj.fieldName == "diachi_ld"
                    );
                    this.columnsPBH[objIndex].visible = false;
                } else {
                    let objIndex = this.columnsPBH.findIndex(
                        (obj) => obj.fieldName == "kenh_tn"
                    );
                    this.columnsPBH[objIndex].visible = false;
                    objIndex = this.columnsPBH.findIndex(
                        (obj) => obj.fieldName == "diachi_dau"
                    );
                    this.columnsPBH[objIndex].visible = false;
                    objIndex = this.columnsPBH.findIndex(
                        (obj) => obj.fieldName == "diachi_cuoi"
                    );
                    this.columnsPBH[objIndex].visible = false;
                }
            } catch (e) {
                this.kieugoi_id = 0;
            }
        },
        async LAY_DS_THAMSO_MD() {
            let result = [];
            try {
                let params = {
                    ma_ts: "",
                };
                let response = await api.ur_41003_001_lay_ds_thamso_md_mats(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        checkTSMD(ds_kt1, str_vtemp, macdinh) {
            let result = macdinh;
            for (let i = 0; i < ds_kt1.length; i++) {
                if (ds_kt1[i].ma_ts == str_vtemp) {
                    result = Number(ds_kt1[i].ten_ts);
                    break;
                }
            }
            return result;
        },
        async LAY_THAMSO_MACDINH(vma_ts) {
            let result;
            try {
                let params = {
                    vma_ts: vma_ts,
                };
                let response = await api.ur_41003_002_lay_thamso_macdinh(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async MAP_ID(id_neo, in_table, in_dk) {
            let result;
            try {
                let params = {
                    id_neo: id_neo,
                    in_table: in_table,
                    in_dk: in_dk,
                };
                let response = await api.ur_41003_003_thanh_ly_map_id(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Ht_NhomTon_Combobox() {
            this.ds_nhomton = [];
            try {
                let response = await api.ur_41003_004_BAOHONG_NHOMTON(this.axios);
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    this.ds_nhomton = response.data.data;
                    if (this.ds_nhomton.length > 0) {
                        this.nhomton_id = this.ds_nhomton[0].NHOMTON_ID;
                    }
                }
            } catch (e) {
                console.log(e);
            }
        },
        async HT_DichVuVT_Combobox_BH() {
            this.ds_dichvuvt = [];
            try {
                let response = await api.ur_41003_005_CSS_DICHVU_VT(this.axios);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    this.ds_dichvuvt = response.data.data;
                    if (this.ds_dichvuvt.length > 0) {
                        this.dichvuvt_id = this.ds_dichvuvt[0].DICHVUVT_ID;
                    }
                }
            } catch (e) {
                console.log(e);
            }
        },
        async Lay_ds_nhanvien_thuchien_theo_dv() {
            this.ds_nguoigv = [];
            try {
                let response = await api.ur_41003_006_sp_lay_ds_nhanvien_theo_donvi(
                    this.axios
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    this.ds_nguoigv = response.data.data;
                    if (this.ds_nguoigv.length > 0) {
                        this.nguoigv_id = this.ds_nguoigv[0].nhanvien_id;
                    }
                }
            } catch (e) {
                console.log(e);
            }
        },
        async sp_lay_ds_tramthicong(loaidv_id) {
            this.ds_tramvt = [];
            try {
                let params = {
                    loaidv_id: loaidv_id,
                };
                let response = await api.ur_41003_007_sp_lay_ds_tramthicong_v2(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    this.ds_tramvt = response.data.data;
                    if (this.ds_tramvt.length > 0) {
                        this.tramvt_id = "0";
                    }
                }
            } catch (e) {
                console.log(e);
            }
        },
        async frmNghiemThuBH_FormLoad() {
            try {
                let ds_kt1 = await this.LAY_DS_THAMSO_MD();
                if (ds_kt1.length > 0) {
                    this.BAOHONG_MANG_LOPTREN_NET = this.checkTSMD(
                        ds_kt1,
                        "BAOHONG_MANG_LOPTREN_NET",
                        -1
                    );
                    this.CAPNHAT_XLSC_NHIEUPHIEU = this.checkTSMD(
                        ds_kt1,
                        "CAPNHAT_XLSC_NHIEUPHIEU",
                        -1
                    );
                    this.KIEMTRA_DOKIEM_NGUYENNHAN = this.checkTSMD(
                        ds_kt1,
                        "KIEMTRA_DOKIEM_NGUYENNHAN",
                        -1
                    );
                    this.ts_capnhat_ont = this.checkTSMD(
                        ds_kt1,
                        "CAPNHAT_THONGTIN_ONT",
                        0
                    );
                    this.NGHIEMTHU_BH_1_NGUYENNHAN = this.checkTSMD(
                        ds_kt1,
                        "NGHIEMTHU_BH_1_NGUYENNHAN",
                        -1
                    );
                    this.GIAOPHIEU_BAOHONG_CATCHUYEN = this.checkTSMD(
                        ds_kt1,
                        "GIAOPHIEU_BAOHONG_CATCHUYEN",
                        -1
                    );
                    this.GIOIHAN_NGUYENNHAN_BAOHONG = this.checkTSMD(
                        ds_kt1,
                        "GIOIHAN_NGUYENNHAN_BAOHONG",
                        -1
                    );
                    ds_kt1.forEach((item) => {
                        if (item.ma_ts == "USING_CABMAN_2.0") {
                            this.IS_USING_CABMAN_V2 = true;
                        }
                    });
                }
                try {
                    this.iServer_DK = await this.LAY_THAMSO_MACDINH("SERVER_DK");
                } catch (e) {
                    this.iServer_DK = 1;
                }
                //Hiện nút tra cứu thông số Bras, dành riêng cho theo yêu cầu của LDG
                this.visible.tsbtnShowBras = this.$root.token.getMaTinh() == "LDG";
                // TODO Registry
                // string s
                // if (cm.KiemTra_ThamSo_Registry("kyhoadon", "Kỳ hóa đơn"))
                //     s = cm.RegRead("kyhoadon", "");
                // else
                // {
                //     this.BeginInvoke(new MethodInvoker(this.Close));
                //     return;
                // }
                var dkyhoadon = new Date();
                this.vkyhoadon =
                    dkyhoadon.getFullYear() + "" + (dkyhoadon.getMonth() + 1) + "01";
                this.vuser_alt = await this.MAP_ID(
                    "user_alt",
                    "CSS.tinh",
                    "where tinh_id = " + this.$root.token.getPhanVungID()
                );
                if (this.vuser_alt == "" || this.vuser_alt == "-1") {
                    this.vuser_alt = "";
                }
                this.vpass_alt = await this.MAP_ID(
                    "pass_alt",
                    "CSS.tinh",
                    "where tinh_id = " + this.$root.token.getPhanVungID()
                );
                if (this.vpass_alt == "" || this.vpass_alt == "-1") {
                    this.vpass_alt = "";
                }
                this.vuser_xtest = await this.MAP_ID(
                    "user_xtest",
                    "CSS.tinh",
                    "where tinh_id = " + this.$root.token.getPhanVungID()
                );
                if (this.vuser_xtest == "" || this.vuser_xtest == "-1") {
                    this.vuser_xtest = "";
                }
                this.vpass_xtest = await this.MAP_ID(
                    "pass_xtest",
                    "CSS.tinh",
                    "where tinh_id = " + this.$root.token.getPhanVungID()
                );
                if (this.vpass_xtest == "" || this.vpass_xtest == "-1") {
                    this.vpass_xtest = "";
                }
                this.kqxl_id = "0";
                await this.Ht_NhomTon_Combobox();
                await this.HT_DichVuVT_Combobox_BH();
                await this.Lay_ds_nhanvien_thuchien_theo_dv();
                this.nguoigv_id = this.$root.token.getNhanVienID();
                const TRAM_VT = 5;
                await this.sp_lay_ds_tramthicong(TRAM_VT);
                this.ds_cboDagiaoviec = [];
                this.ds_cboDagiaoviec.push({
                    ID: "-1",
                    NAME: "--Tất cả--"
                });
                this.ds_cboDagiaoviec.push({
                    ID: "0",
                    NAME: "Chưa giao việc"
                });
                this.ds_cboDagiaoviec.push({
                    ID: "1",
                    NAME: "Đã giao việc"
                });
                this.cboDagiaoviec_id = "-1";
                this.dtpNgayBH = this.ngay_ht;
                this.dtpNgayTH = this.ngay_ht;
                this.dtpNgayXN = this.ngay_ht;
                var today = moment();
                this.dtpTuNgay = moment(today).add(-7, "days").format("DD/MM/yyyy");
                $("#chkTuNgay").prop("checked", false);
                this.dtpDenNgay = this.ngay_ht;
                this.kt_load = false;
                if (this.kieugoi_id == enums.NET_BAOHONG.TTBH_XL_XONG) {
                    this.kqxl_id = "0";
                    this.enable.cboKQXL = false;
                }
                if (this.tag_TuDong_LayTT != 0) {
                    this.enable.timer1 = true;
                }
                await this.HienThiDanhSach();
            } catch (exp) {
                console.log(exp);
            }
        },
        async Lay_ds_phieu_hoancong_bh_v4(
            dichvuvt_id,
            ttbh_id,
            nhanvien_id,
            ma_tb,
            huonggiao_id,
            gv,
            vtungay,
            vdenngay
        ) {
            let result = [];
            try {
                let params = {
                    loaidvvt_id: dichvuvt_id,
                    ttbh_id: ttbh_id,
                    nhanvien_id: nhanvien_id,
                    ma_tb: ma_tb,
                    huonggiao_id: huonggiao_id,
                    giaoviec: gv,
                    tungay: vtungay,
                    denngay: vdenngay,
                };
                let response = await api.ur_41003_008_lay_ds_phieu_hoancong_bh_v4(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_ds_phieu_hoancong_bh_v5(
            dichvuvt_id,
            ttbh_id,
            nhanvien_id,
            ma_tb,
            huonggiao_id,
            gv,
            vtungay,
            vdenngay
        ) {
            let result = [];
            try {
                let params = {
                    loaidvvt_id: dichvuvt_id,
                    ttbh_id: ttbh_id,
                    nhanvien_id: nhanvien_id,
                    ma_tb: ma_tb,
                    huonggiao_id: huonggiao_id,
                    giaoviec: gv,
                    tungay: vtungay,
                    denngay: vdenngay,
                };
                let response = await api.ur_41003_009_lay_ds_phieu_hoancong_bh_v5(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_ds_phieu_baoduong_ttdh(
            dichvuvt_id,
            ttbh_id,
            nhanvien_id,
            ma_tb,
            huonggiao_id,
            gv,
            vtungay,
            vdenngay
        ) {
            let result = [];
            try {
                let params = {
                    loaidvvt_id: dichvuvt_id,
                    ttbh_id: ttbh_id,
                    nhanvien_id: nhanvien_id,
                    ma_tb: ma_tb,
                    huonggiao_id: huonggiao_id,
                    giaoviec: gv,
                    tungay: vtungay,
                    denngay: vdenngay,
                };
                let response = await api.ur_41003_010_lay_ds_phieu_baoduong_ttdh(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_ds_phieu_theodoi_cho_ht(
            dichvuvt_id,
            ttbh_id,
            nhanvien_id,
            ma_tb,
            huonggiao_id,
            donvi_giao_id,
            vtungay,
            vdenngay
        ) {
            let result = [];
            try {
                let params = {
                    loaidvvt_id: dichvuvt_id,
                    ttbh_id: ttbh_id,
                    nhanvien_id: nhanvien_id,
                    ma_tb: ma_tb,
                    huonggiao_id: huonggiao_id,
                    donvi_giao_id: donvi_giao_id,
                    tungay: vtungay,
                    denngay: vdenngay,
                };
                let response = await api.ur_41003_011_lay_ds_phieu_theodoi_cho_ht(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_ds_phieu_hoancong_bh_v6(
            dichvuvt_id,
            ttbh_id,
            nhanvien_id,
            ma_tb,
            huonggiao_id,
            donvi_giao_id,
            vtungay,
            vdenngay
        ) {
            if (huonggiao_id == null || ttbh_id == null) return [];
            let result = [];
            try {
                let params = {
                    loaidvvt_id: dichvuvt_id,
                    ttbh_id: ttbh_id,
                    nhanvien_id: nhanvien_id,
                    ma_tb: ma_tb,
                    huonggiao_id: huonggiao_id,
                    donvi_giao_id: donvi_giao_id,
                    tungay: vtungay,
                    denngay: vdenngay,
                };
                let response = await api.ur_41003_012_lay_ds_phieu_hoancong_bh_v6(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Laythongtin_table(table, result, where, groupby) {
            let res = [];
            try {
                let params = {
                    table: table,
                    result: result,
                    where: where,
                    groupby: groupby,
                };
                let response = await api.ur_41003_032_lay_thongtin_table(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    res = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return res;
        },
        async sp_lay_ds_thaotac_theo_luong_id(luong_id) {
            let result = [];
            try {
                let params = {
                    luong_id: luong_id,
                };
                let response = await api.ur_41003_033_sp_lay_ds_thaotac_theo_luong_id(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_thongtin_nghiemthu_bh(kieugoi_id, quytrinh_id, ttbh_id) {
            let res = [];
            try {
                let params = {
                    kieugoi_id: kieugoi_id,
                    quytrinh_id: quytrinh_id,
                    ttbh_id: ttbh_id
                };
                let response = await api.lay_thongtin_nghiemthu_bh(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    res = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return res;
        },
        async AnHienControls() {
            try {
                let quytrinh_id = this.quytrinh_id;
                let vttbh = this.kieugoi_id == 3 ? 4 : 3;
                if (this.kieugoi_id == 9) vttbh = 9;
                if (
                    this.kieugoi_id == enums.NET_BAOHONG.TTBH_DANG_XL ||
                    this.kieugoi_id == enums.NET_BAOHONG.TTBH_XL_XONG ||
                    this.kieugoi_id == 8 ||
                    this.kieugoi_id == 5 ||
                    this.kieugoi_id == enums.TRANGTHAI_BH.THEODOI_CHO_HT
                )
                    vttbh = this.kieugoi_id;
                // Thay đổi API
                // let table, result, where, groupby;
                // table = " CSS.huonggiao a, CSS.huonggiao_ldv b";
                // if (this.kieugoi_id != enums.TRANGTHAI_BH.THEODOI_CHO_HT) {
                //     result = " b.luong_id, a.huonggiao_id, a.huonggiao ";
                //     where =
                //         "  a.quytrinh_id = b.quytrinh_id and a.huonggiao_id = b.huonggiao_id ";
                // } else {
                //     result = " -1 luong_id, a.huonggiao_id, a.huonggiao ";
                //     where =
                //         "  a.quytrinh_id = b.quytrinh_id(+) and a.huonggiao_id = b.huonggiao_id(+) ";
                // }
                // const pv_id = this.$auth.getPhanVungID();
                // where +=
                //     "  and a.quytrinh_id = " +
                //     quytrinh_id +
                //     " and  a.ttbh_id = " +
                //     vttbh +
                //     `and a.phanvung_id = ${pv_id} and b.phanvung_id = ${pv_id}`;
                // groupby = "";
                //let ds = await this.Laythongtin_table(table, result, where, groupby);

                let ds = await this.Lay_thongtin_nghiemthu_bh(this.kieugoi_id, quytrinh_id, vttbh);
                if (ds.length > 0) {
                    this.luong_id = Number(ds[0].luong_id);
                    this.huonggiao_id = Number(ds[0].huonggiao_id);
                    this.dtThaoTac = await this.sp_lay_ds_thaotac_theo_luong_id(
                        this.luong_id
                    );
                    this.visible.tsbtnThayDoiTT_BH = false;
                    this.visible.tsbtnVatTuMoi = false;
                    this.visible.tsbtnVatTu = false;
                    this.visible.tsbtnNghiemThu = false;
                    this.visible.btnGiaoPhieuTG = false;
                    this.enable.btnGiaoPhieuTG = false;
                    this.visible.tsbtnTraPhieu = true;
                    this.enable.tsbtnTraPhieu = true;
                    this.enable.tsbtnChuyenTo = true;
                    this.visible.tsbtnChuyenTTDH = true;
                    this.enable.tsbtnChuyenTTDH = true;
                    this.visible.tsbtnChuyenTTVT = true;
                    this.enable.tsbtnChuyenTTVT = true;
                    this.visible.tsbtnChuyenTheoDoi = false;
                    this.enable.ctmGiuPhieu = true;
                    for (let i = 0; i < this.dtThaoTac.length; i++) {
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnThayDoiTT_BH" &&
                            this.dtThaoTac[i].enable == "1"
                        ) {
                            this.visible.tsbtnThayDoiTT_BH = true;
                            this.enable.tsbtnThayDoiTT_BH = true;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnVatTuMoi" &&
                            this.dtThaoTac[i].enable == "1"
                        ) {
                            this.visible.tsbtnVatTuMoi = true;
                            this.enable.tsbtnVatTuMoi = true;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnNghiemThu" &&
                            this.dtThaoTac[i].enable == "1"
                        ) {
                            this.visible.tsbtnNghiemThu = true;
                            this.enable.tsbtnNghiemThu = true;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnHoanCong" &&
                            this.dtThaoTac[i].enable == "1"
                        ) {
                            this.visible.tsbtnHoanCong = true;
                            this.enable.tsbtnHoanCong = true;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnVatTu" &&
                            this.dtThaoTac[i].enable == "1"
                        ) {
                            this.visible.tsbtnVatTu = true;
                            this.enable.tsbtnVatTu = true;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnChuyenDoiTac" &&
                            this.dtThaoTac[i].enable == "1"
                        ) {
                            this.visible.tsbtnChuyenDoiTac = true;
                            this.enable.tsbtnChuyenDoiTac = true;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "btnGiaoPhieuTG" &&
                            this.dtThaoTac[i].enable == "1"
                        ) {
                            this.visible.btnGiaoPhieuTG = true;
                            this.enable.btnGiaoPhieuTG = true;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnTraPhieu" &&
                            this.dtThaoTac[i].enable == "0"
                        ) {
                            this.enable.tsbtnTraPhieu = false;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnChuyenTo" &&
                            this.dtThaoTac[i].enable == "0"
                        ) {
                            this.enable.tsbtnChuyenTo = false;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnChuyenTTDH" &&
                            this.dtThaoTac[i].enable == "0"
                        ) {
                            this.enable.tsbtnChuyenTTDH = false;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnChuyenTTVT" &&
                            this.dtThaoTac[i].enable == "0"
                        ) {
                            this.enable.tsbtnChuyenTTVT = false;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "tsbtnChuyenTheoDoi" &&
                            this.dtThaoTac[i].enable == "1"
                        ) {
                            this.visible.tsbtnChuyenTheoDoi = true;
                        }
                        if (
                            this.dtThaoTac[i].control_name == "ctmGiuPhieu" &&
                            this.dtThaoTac[i].enable == "0"
                        ) {
                            this.enable.ctmGiuPhieu = false;
                        }
                    }
                    if (vttbh == enums.NET_BAOHONG.TTBH_XL_XONG || vttbh == 9) {
                        for (let i = 0; i < this.dtThaoTac.length; i++) {
                            if (this.dtThaoTac[i].control_name == "tsbtnNghiemThu") {
                                if (this.dtThaoTac[i].enable == "1") {
                                    this.visible.tsbtnNghiemThu = true;
                                    this.enable.tsbtnNghiemThu = true;
                                } else {
                                    this.visible.tsbtnNghiemThu = false;
                                    this.enable.tsbtnNghiemThu = false;
                                }
                            }
                        }
                    }
                    if (this.kieugoi_id == 19) {
                        this.enable.tsbtnTraPhieu = false;
                    }
                    if (this.kieugoi_id == enums.TRANGTHAI_BH.THEODOI_CHO_HT) {
                        this.visible.tsbtnTraPhieu = false;
                        this.visible.tsbtnChuyenTo = false;
                        this.visible.tsbtnChuyenTTDH = false;
                        this.visible.tsbtnChuyenTTVT = false;
                        this.visible.tsbtnChuyenDoiTac = false;
                        this.visible.tsbtnChuyenTheoDoi = false;
                        this.visible.tsbtnHoanCong = false;
                        this.visible.tsbtnNghiemThu = true;
                        this.enable.tsbtnNghiemThu = true;
                    }
                    if (this.CAPNHAT_XLSC_NHIEUPHIEU == 1) {
                        if (this.visible.tsbtnNghiemThu) {
                            this.visible.tsbtnNghiemThuNhieuPhieu = true;
                            this.visible.tsbtnNghiemThu = false;
                        } else {
                            this.visible.tsbtnNghiemThuNhieuPhieu = false;
                            this.visible.tsbtnNghiemThu = false;
                        }
                    }
                } else {
                    return;
                }
            } catch (e) {
                this.enable.tsbtnTraPhieu = false;
            }
        },
        async HienThiDanhSach() {
            try {
                this.loading(true);
                let dvg = 0,
                    gv = 0;
                if (this.tramvt_id != null) {
                    dvg = Number(this.tramvt_id);
                }
                gv = Number(this.cboDagiaoviec_id);
                await this.AnHienControls();
                let vtungay = "0",
                    vdenngay = "0";
                if (
                    this.kieugoi_id == enums.TRANGTHAI_BH.DANG_GIAO_XULY ||
                    this.kieugoi_id == 9
                ) {
                    if (this.BAOHONG_MANG_LOPTREN_NET == 1)
                        this.dtList = await this.Lay_ds_phieu_hoancong_bh_v4(
                            this.dichvuvt_id,
                            document.getElementById("chkChoXL").checked ? 7 : this.ttbh_id,
                            this.$root.token.getNhanVienID(),
                            "0",
                            this.huonggiao_id,
                            gv,
                            vtungay,
                            vdenngay
                        );
                    else
                        this.dtList = await this.Lay_ds_phieu_hoancong_bh_v5(
                            this.dichvuvt_id,
                            document.getElementById("chkChoXL").checked ? 7 : this.ttbh_id,
                            this.$root.token.getNhanVienID(),
                            "0",
                            this.huonggiao_id,
                            gv,
                            vtungay,
                            vdenngay
                        );
                } else if (this.kieugoi_id == 19) {
                    this.dtList = await this.Lay_ds_phieu_baoduong_ttdh(
                        this.dichvuvt_id,
                        9,
                        this.$root.token.getNhanVienID(),
                        "0",
                        this.huonggiao_id,
                        1399,
                        vtungay,
                        vdenngay
                    );
                } else if (this.kieugoi_id == enums.TRANGTHAI_BH.THEODOI_CHO_HT) {
                    this.dtList = await this.Lay_ds_phieu_theodoi_cho_ht(
                        this.dichvuvt_id,
                        document.getElementById("chkChoXL").checked ?
                        7 :
                        enums.TRANGTHAI_BH.THEODOI_CHO_HT,
                        this.$root.token.getNhanVienID(),
                        "0",
                        this.huonggiao_id,
                        dvg,
                        vtungay,
                        vdenngay
                    );
                } else
                    this.dtList = await this.Lay_ds_phieu_hoancong_bh_v6(
                        this.dichvuvt_id,
                        document.getElementById("chkChoXL").checked ? 7 : this.ttbh_id,
                        this.$root.token.getNhanVienID(),
                        "0",
                        this.huonggiao_id,
                        dvg,
                        vtungay,
                        vdenngay
                    );
                this.ds_phieubaohong_real = this.dtList;
                this.ds_phieubaohong = this.ds_phieubaohong_real.slice();
                if (this.dtList.length > 0) {
                    this.visible.tsbtnInPhieuTC = true;
                    this.dtpNgayTH = this.ngay_ht;
                } else {
                    this.Clear();
                    this.visible.tsbtnInPhieuTC = false;
                }
            } catch (ex) {
                console.log(ex);
                this.$toast.error(ex);
            } finally {
                this.loading(false);
            }
        },
        async Lay_ds_TinhTrang(baohong_id, dichvuvt_id, loaitb_id, kieu) {
            let result = [];
            try {
                let params = {
                    baohong_id: baohong_id,
                    dichvuvt_id: dichvuvt_id,
                    loaitb_id: loaitb_id,
                    kieu: kieu,
                };
                let response = await api.ur_41003_028_sp_lay_ds_tinhtrang(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_ds_nguyennhan(phieu_id, nhomnn_id, loaitb_id) {
            let result = [];
            try {
                let params = {
                    phieu_id: phieu_id,
                    nhomnn_id: nhomnn_id,
                    loaitb_id: loaitb_id,
                };
                let response = await api.ur_41003_019_sp_lay_ds_nguyennhan_hong(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_ds_nguyennhan_baohong_v2(phieu_id, nhomnn_id, ttbh_id) {
            let result = [];
            try {
                let params = {
                    phieu_id: phieu_id,
                    nhomnn_id: nhomnn_id,
                    ttbh_id: ttbh_id,
                };
                let response = await api.ur_41003_020_lay_ds_nguyennhan_baohong_v2(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_ds_lydoton_theo_lhtb(baohong_id, nhomton_id, ttxl_id, loaitb_id) {
            let result = [];
            try {
                let params = {
                    baohong_id: baohong_id,
                    nhomton_id: nhomton_id,
                    ttxl_id: ttxl_id,
                    loaitb_id: loaitb_id,
                };
                let response = await api.ur_41003_022_sp_lay_ds_baohong_bc_thicong(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async gridviewDanhSach_FocusedRowChanged(row) {
            if (row.data == undefined) return;
            try {
                this.loading(true);
                this.PHIEU_ID = Number(row.data.phieu_id);
                this.trangthaibh_id = Number(row.data.ttbh_id);
                this.LOAITB_ID = Number(row.data.loaitb_id);
                this.txtLoaiTB = row.data.loaihinh_tb;
                this.baohong_id = Number(row.data.baohong_id);
                this.txtMaTB = row.data.ma_tb;
                this.vma_tb = this.txtMaTB;
                this.dtpNgayBH = row.data.ngay_bh;
                this.dichvuvt_id = Number(row.data.dichvuvt_id);
                if (this.$root.token.getMaTinh() == "HNI") {
                    var r_ghichu = row.data.ghichu;
                    if (r_ghichu.includes("800126->VNPT-NET")) {
                        this.visible.tsbtnHoanCong = false;
                        this.visible.tsbtnHoanCongNET = true;
                        this.enable.tsbtnHoanCongNET = this.enable.tsbtnHoanCong;
                        this.visible.tsbtnHoanCongOMC = false;
                    } else if (r_ghichu.includes("800126->OMC Data")) {
                        this.visible.tsbtnHoanCong = false;
                        this.visible.tsbtnHoanCongOMC = true;
                        this.enable.tsbtnHoanCongOMC = this.enable.tsbtnHoanCong;
                        this.visible.tsbtnHoanCongNET = false;
                    } else {
                        this.visible.tsbtnHoanCong = true;
                        this.visible.tsbtnHoanCongNET = false;
                        this.visible.tsbtnHoanCongOMC = false;
                    }
                }
                let vloaitb_id = 0;
                if (this.dichvuvt_id == enums.DichVuVienThong.CO_DINH)
                    vloaitb_id = enums.LoaiHinhTB.DIENTHOAI_CD;
                else vloaitb_id = Number(row.data.loaitb_id);
                this.ds_tinhtrang = await this.Lay_ds_TinhTrang(
                    this.baohong_id,
                    this.dichvuvt_id,
                    vloaitb_id,
                    1
                );
                this.tinhtrang_id =
                    this.ds_tinhtrang.length > 0 ?
                    this.ds_tinhtrang[0].tinhtrang_id :
                    null;
                this.txtTenTB = row.data.ten_tb;
                this.txtDiaChiLD = row.data.diachi_ld;
                this.txtTrangThaiTB = row.data.trangthai_tb;
                this.txtGhiChuHong = row.data.ghichu_hong;
                this.txtNguoiBH = row.data.nguoi_bh;
                this.txtDienThoaiLH = row.data.dienthoai_lh;
                this.txtDactrung = row.data.dactrung;
                this.txtGhiChuTH = row.data.ghichu_th;
                if (
                    row.data.nhanvien_th_id != undefined &&
                    row.data.nhanvien_th_id.trim() != ""
                )
                    this.nv_giao_id = Number(row.data.nhanvien_th_id.trim());
                else this.nv_giao_id = this.$root.token.getNhanVienID();
                if (
                    this.kieugoi_id != enums.NET_BAOHONG.TTBH_DANG_XL &&
                    this.kieugoi_id != enums.NET_BAOHONG.TTBH_XL_XONG
                )
                    this.ds_nguyennhan = await this.Lay_ds_nguyennhan(
                        this.PHIEU_ID,
                        0,
                        vloaitb_id
                    );
                else
                    this.ds_nguyennhan = await this.Lay_ds_nguyennhan_baohong_v2(
                        this.PHIEU_ID,
                        0,
                        this.kieugoi_id
                    );
                if (this.ds_nguyennhan.length > 0) {
                    let dr = null;
                    if (this.ds_nguyennhan[0].hang_sx != undefined) {
                        for (let i = 0; i < this.ds_nguyennhan; i++) {
                            if (
                                this.ds_nguyennhan[i].hang_sx != null &&
                                this.ds_nguyennhan[i].hang_sx != ""
                            ) {
                                dr = this.ds_nguyennhan[i];
                                break;
                            }
                        }
                        if (dr != null) {
                            this.txtHangSx = dr.hang_sx;
                        }
                    }
                    let kt = true;
                    let nhomnn_id = 0;
                    for (let i = 0; i < this.ds_nguyennhan; i++) {
                        if (this.ds_nguyennhan[i].chon == "1") {
                            dr = this.ds_nguyennhan[i];
                            break;
                        }
                    }
                    if (dr != null) {
                        this.nhomnn_id = dr.loaihong_id;
                        nhomnn_id = Number(dr.loaihong_id);
                        if (
                            this.kieugoi_id != enums.NET_BAOHONG.TTBH_DANG_XL &&
                            this.kieugoi_id != enums.NET_BAOHONG.TTBH_XL_XONG
                        )
                            this.ds_nguyennhan = await this.Lay_ds_nguyennhan(
                                this.PHIEU_ID,
                                0,
                                vloaitb_id
                            );
                        else
                            this.ds_nguyennhan = await this.Lay_ds_nguyennhan_baohong_v2(
                                this.PHIEU_ID,
                                0,
                                this.kieugoi_id
                            );
                        kt = false;
                    }
                    if (kt) {
                        if (this.ds_nhomNN.length > 0) {
                            this.nhomnn_id = this.ds_nhomNN[0].LOAIHONG_ID;
                            nhomnn_id = Number(this.nhomnn_id);
                        }
                        if (
                            this.kieugoi_id != enums.NET_BAOHONG.TTBH_DANG_XL &&
                            this.kieugoi_id != enums.NET_BAOHONG.TTBH_XL_XONG
                        )
                            this.ds_nguyennhan = await this.Lay_ds_nguyennhan(
                                this.PHIEU_ID,
                                0,
                                vloaitb_id
                            );
                        else
                            this.ds_nguyennhan = await this.Lay_ds_nguyennhan_baohong_v2(
                                this.PHIEU_ID,
                                0,
                                this.kieugoi_id
                            );
                        this.visible.pnlNguyenNhan1 = true;
                        this.visible.pnlNguyenNhan2 = true;
                        this.visible.pnlTon1 = false;
                        this.visible.pnlTon2 = false;
                    }
                }
                for (let i = 0; i < this.ds_nguyennhan.length; i++) {
                    if (this.ds_nguyennhan[i].chon == "1") {
                        this.ds_nguyennhan_id.push(this.ds_nguyennhan[i].cthong_id);
                    }
                }
                this.kqxl_id = null;
                let ttbh_gp_id =
                    row.data.ttbh_gp_id != undefined ?
                    Number(row.data.ttbh_gp_id.trim()) :
                    null;
                if (ttbh_gp_id == enums.TRANGTHAI_BH.BAOTON) {
                    this.kqxl_id = "1";
                    let kiemtra = false;
                    this.ds_lydoton = await this.Lay_ds_lydoton_theo_lhtb(
                        this.baohong_id,
                        0,
                        2,
                        this.LOAITB_ID
                    );
                    if (this.ds_lydoton.length > 0) {
                        for (let i = 0; i < this.ds_lydoton.length; i++) {
                            if (Number(this.ds_lydoton[i].chon) == 1) {
                                kiemtra = true;
                                this.visible.pnlNguyenNhan1 = false;
                                this.visible.pnlNguyenNhan2 = false;
                                this.visible.pnlTon1 = true;
                                this.visible.pnlTon2 = true;
                                if (this.ds_lydoton[i].loai == "2") {
                                    this.ds_lydoton = await this.Lay_ds_lydoton_theo_lhtb(
                                        this.baohong_id,
                                        enums.NHOMTON.KHAC,
                                        1,
                                        this.LOAITB_ID
                                    );
                                } else {
                                    let nhomtonbh_id = 0;
                                    nhomtonbh_id = Number(this.ds_lydoton[0].nhomton_id);
                                    this.nhomton_id = this.ds_lydoton[0].nhomton_id;
                                    this.ds_lydoton = await this.Lay_ds_lydoton_theo_lhtb(
                                        this.baohong_id,
                                        nhomtonbh_id,
                                        2,
                                        this.LOAITB_ID
                                    );
                                }
                                break;
                            }
                        }
                    }
                    for (let i = 0; i < this.ds_lydoton.length; i++) {
                        if (this.ds_lydoton[i].chon == "1") {
                            this.ds_lydoton_id.push(this.ds_lydoton[i].lydotonbh_id);
                        }
                    }
                } else if (ttbh_gp_id == enums.TRANGTHAI_BH.CHO_XULY) {
                    this.kqxl_id = "2";
                    let kiemtra = false;
                    this.ds_lydoton = await this.Lay_ds_lydoton_theo_lhtb(
                        this.baohong_id,
                        0,
                        2,
                        this.LOAITB_ID
                    );
                    if (this.ds_lydoton.length > 0) {
                        for (let i = 0; i < this.ds_lydoton.length; i++) {
                            if (Number(this.ds_lydoton[i].chon) == 1) {
                                kiemtra = true;
                                this.visible.pnlNguyenNhan1 = false;
                                this.visible.pnlNguyenNhan2 = false;
                                this.visible.pnlTon1 = true;
                                this.visible.pnlTon2 = true;
                                let nhomtonbh_id = 0;
                                nhomtonbh_id = Number(this.ds_lydoton[i].nhomton_id);
                                if (this.ds_lydoton[i].loai == "2")
                                    this.ds_lydoton = await this.Lay_ds_lydoton_theo_lhtb(
                                        this.baohong_id,
                                        nhomtonbh_id,
                                        1,
                                        this.LOAITB_ID
                                    );
                                else
                                    this.ds_lydoton = await this.Lay_ds_lydoton_theo_lhtb(
                                        this.baohong_id,
                                        nhomtonbh_id,
                                        2,
                                        this.LOAITB_ID
                                    );
                                this.nhomton_id = nhomtonbh_id;
                                break;
                            }
                        }
                    }
                    for (let i = 0; i < this.ds_lydoton.length; i++) {
                        if (this.ds_lydoton[i].chon == "1") {
                            this.ds_lydoton_id.push(this.ds_lydoton[i].lydotonbh_id);
                        }
                    }
                } else this.kqxl_id = "0";
                if (this.trangthaibh_id == enums.TRANGTHAI_BH.CHO_XULY) {
                    this.kqxl_id = "0";
                    this.enable.cboKQXL = false;
                } else {
                    this.enable.cboKQXL = true;
                }
                if (row.data.ngay_th != undefined && row.data.ngay_th.trim() != "") {
                    $("#chkNgayBG").prop("checked", true);
                    this.enable.dtpNgayTH = true;
                    this.dtpNgayTH = row.data.ngay_th.trim();
                } else {
                    this.dtpNgayTH = this.ngay_ht;
                    this.enable.tsbtnHoanCong = false;
                }
                this.thuebao_id =
                    row.data.thuebao_id != undefined ?
                    Number(row.data.thuebao_id.trim()) :
                    null;
                await this.HT_THONGSO_KYTHUAT(this.thuebao_id);
                let dsNV = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.PHIEU_ID, 2);
                this.HienThiDSNV(dsNV);
            } catch (ex) {
                console.log(ex);
                this.$toast.error(ex);
            } finally {
                this.loading(false);
            }
        },
        HienThiDSNV(ds) {
            if (ds.length > 0) {
                $("#chkNgayGV").prop("checked", true);
                this.ds_nvxl = ds;
                this.dtpNgayGV = ds[0].ngaygiao;
                this.nguoigv_id =
                    ds[0].nhanvien_giao_id != undefined &&
                    ds[0].nhanvien_giao_id != null &&
                    ds[0].nhanvien_giao_id != "" ?
                    ds[0].nhanvien_giao_id :
                    this.$root.token.getNhanVienID();
                this.nhanvien_th_id = Number(ds[0].nhanvien_th_id);
                this.congviec_th = ds[0].nhiemvu;
            } else {
                this.ds_nvxl = [];
                this.dtpNgayGV = this.ngay_ht;
                this.nguoigv_id = this.$root.token.getNhanVienID();
                this.nhanvien_th_id = -1;
                this.congviec_th = "";
            }
        },
        async LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieu_id, kieu_id) {
            let result = [];
            try {
                let params = {
                    phieu_id: phieu_id,
                    kieu_id: kieu_id,
                };
                let response = await api.ur_41003_024_lay_ds_nhanvien_theo_phieu_id(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async Lay_ds_thongtin_kythuat(in_thuebao_id) {
            let result = [];
            try {
                let params = {
                    vthuebao_id: in_thuebao_id,
                };
                let response = await api.ur_41003_014_lay_ds_thongtin_kythuat(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async HT_THONGSO_KYTHUAT(in_thuebao_id) {
            try {
                let ds = await this.Lay_ds_thongtin_kythuat(in_thuebao_id);
                // Xóa hiển thị cũ
                $("#wbrTSKT").html("");
                // Hiển thị dữ liệu mới
                let wbrTSKT = "";
                for (let i = 0; i < ds.length; i++) {
                    wbrTSKT +=
                        "<b>" +
                        ds[i].ten_truong +
                        "</b>: " +
                        (ds[i].giatri != null ? ds[i].giatri : "") +
                        "<br/>";
                }
                $("#wbrTSKT").html(wbrTSKT);
            } catch (ex) {
                console.log(ex);
                $("#wbrTSKT").html("Chưa có dữ liệu");
            }
        },
        LocPhieuTheoNgay() {
            if (!document.getElementById("chkTuNgay").checked) {
                return this.dtList.filter(
                    (x) =>
                    x.ghichu &&
                    (x.ghichu.includes("VNPT-NET->800126") ||
                        x.ghichu.includes("OMC Data->800126") ||
                        x.ghichu.includes("IT360->800126"))
                );
            }
            try {
                return this.dtList.filter(
                    (x) =>
                    x.ngay_bh &&
                    this.strDDMMYYYYHHMM_toDate(x.ngay_bh) >=
                    this.strDDMMYYYYHHMM_toDate(this.dtpTuNgay) &&
                    this.strDDMMYYYYHHMM_toDate(x.ngay_bh) <=
                    this.strDDMMYYYYHHMM_toDate(this.dtpDenNgay) &&
                    (x["ghichu"] == "800126->VNPT-NET" ||
                        x["ghichu"] == "800126->OMC Data" ||
                        x["ghichu"] == "800126->IT360")
                );
            } catch (ex) {
                return this.dtList;
            }
        },
        strDDMMYYYY_toDate(dtStr) {
            let parts_of_date = dtStr.split("/");
            return new Date(
                +parts_of_date[2],
                parts_of_date[1] - 1,
                +parts_of_date[0]
            );
        },
        strDDMMYYYYHHMM_toDate(dtStr) {
            if (!dtStr) return null;
            let dateParts = dtStr.split("/");
            let timeParts = dateParts[2].split(" ")[1].split(":");
            dateParts[2] = dateParts[2].split(" ")[0];
            return new Date(
                +dateParts[2],
                dateParts[1] - 1,
                +dateParts[0],
                timeParts[0],
                timeParts[1],
                0
            );
        },
        Clear() {
            this.LOAITB_ID = 0;
            this.ds_nvxl = [];
            this.txtMaTB = "";
            this.dtpNgayBH = this.ngay_ht;
            this.dtpNgayTH = this.ngay_ht;
            this.txtTenTB = "";
            this.txtDiaChiLD = "";
            this.txtTrangThaiTB = "";
            this.txtGhiChuHong = "";
            this.ds_tinhtrang = [];
            this.tinhtrang_id = null;
            this.nguoigv_id = this.$root.token.getNhanVienID();
            $("#chkNgayBG").prop("checked", true);
            this.enable.dtpNgayTH = true;
            this.txtGhiChuTH = "";
            this.enable.btnHoanCong = false;
            this.ds_nguyennhan = [];
            this.ds_nguyennhan_id = [];
            this.txtNguoiBH = "";
            this.txtDienThoaiLH = "";
            this.txtDactrung = "";
            this.txtLoaiTB = "";
        },
        async sp_lay_ds_quytrinh_bh_theo_dichvuvt_id(dichvuvt_id) {
            let result = [];
            try {
                let params = {
                    dichvuvt_id: dichvuvt_id,
                };
                let response =
                    await api.ur_41003_017_sp_lay_ds_quytrinh_bh_theo_dichvuvt_id(
                        this.axios,
                        params
                    );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async sp_lay_ds_nhom_nn(kieugoi_id, loaihong_id, dichvuvt_id) {
            let result = [];
            try {
                let params = {
                    kieugoi_id: kieugoi_id,
                    loaihong_id: loaihong_id,
                    dichvuvt_id: dichvuvt_id,
                };
                let response = await api.ur_41003_015_sp_lay_ds_nhom_nn(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async sp_lay_ds_nhom_nn_v2(dichvuvt_id) {
            let result = [];
            try {
                let params = {
                    dichvuvt_id: dichvuvt_id,
                };
                let response = await api.ur_41003_016_sp_lay_ds_nhom_nn_v2(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async cboDichVuVT_Changed() {
            try {
                this.loading(true);
                this.ds_quytrinh = [];
                this.quytrinh_id = null;
                if (this.ds_dichvuvt.length <= 0) return;
                if (this.$root.token.getMaTinh() == "HNI") {
                    if (this.dichvuvt_id == 9) {
                        let objIndex = this.columnsPBH.findIndex(
                            (obj) => obj.fieldName == "kenh_tn"
                        );
                        this.columnsPBH[objIndex].visible = false;
                        objIndex = this.columnsPBH.findIndex(
                            (obj) => obj.fieldName == "diachi_dau"
                        );
                        this.columnsPBH[objIndex].visible = true;
                        objIndex = this.columnsPBH.findIndex(
                            (obj) => obj.fieldName == "diachi_cuoi"
                        );
                        this.columnsPBH[objIndex].visible = true;
                    } else {
                        let objIndex = this.columnsPBH.findIndex(
                            (obj) => obj.fieldName == "kenh_tn"
                        );
                        this.columnsPBH[objIndex].visible = true;
                        objIndex = this.columnsPBH.findIndex(
                            (obj) => obj.fieldName == "diachi_dau"
                        );
                        this.columnsPBH[objIndex].visible = false;
                        objIndex = this.columnsPBH.findIndex(
                            (obj) => obj.fieldName == "diachi_cuoi"
                        );
                        this.columnsPBH[objIndex].visible = false;
                    }
                }
                await this.HT_Nhom_NN(this.dichvuvt_id);
                this.HienThiGiaoDien(this.kieugoi_id);
                let ds = await this.sp_lay_ds_quytrinh_bh_theo_dichvuvt_id(
                    String(this.dichvuvt_id)
                );
                if (ds.length > 0) {
                    this.ds_quytrinh = ds.slice();
                await this.$nextTick();
                    if (this.ds_quytrinh.length > 0) {
                        this.quytrinh_id = this.ds_quytrinh[0].quytrinh_id;
                    }
                    this.Clear();
                    this.visible.tsbtnInPhieuTC = false;
                    this.ds_phieubaohong_real = [];
                    this.ds_phieubaohong = [];
                    this.ds_phieubaohong_selected = [];
                } else {
                    this.$toast.error("Không có quy trình");
                    this.Clear();
                    this.ds_phieubaohong_real = [];
                    this.ds_phieubaohong = [];
                    this.ds_phieubaohong_selected = [];
                    this.ds_nvxl = [];
                    $("#wbrTSKT").html("Chưa có dữ liệu");
                }
                this.setsize();
                if (this.dtThaoTac.length > 0 && this.PHAILAM("AN_CHUYEN_TTDH")) {
                    this.visible.tsbtnChuyenTTDH = false;
                }
            } catch (exp) {
                console.log(exp);
            } finally {
                this.loading(false);
            }
        },
        HienThiGiaoDien(kieugoi_id) {
            this.ttbh_id = kieugoi_id == 3 ? 4 : 3;
            if (kieugoi_id == 9) this.ttbh_id = 9;
            this.labelFunctionTitle = "ĐIỀU HÀNH XỬ LÝ SỰ CỐ";
            this.visible.tsbtnTraPhieu = false;
            let objIndex = this.columnsPBH.findIndex(
                (obj) => obj.fieldName == "nguyennhan"
            );
            this.columnsPBH[objIndex].visible = false;
            this.visible.tsbtnBaoTon_BH = false;
            this.visible.tungaydenngay = false;
            this.visible.tsbtnChuyenTo = true;
            this.visible.tsbtnKQ_DoKiem = true;
            this.visible.tsbtnVatTuMoi = false;
            this.visible.tsbtnChuyenTTDH = false;
            this.enable.dtpNgayXN = false;
            if (kieugoi_id == enums.TRANGTHAI_BH.DANG_GIAO_XULY) {
                // Trạm viễn thông
                this.visible.cboTramVT = false;
                this.visible.cboDaGiaoViec = !this.visible.cboTramVT;
                this.visible.tsbtnTraPhieu = this.$root.token.getMaTinh() != "HPG";
                this.lblTramVT = "Đã giao việc";
                this.visible.tsbtnChuyenTo = true;
                this.visible.tungaydenngay = true;
                this.visible.tsbtnTraPhieu = true;
                if (this.$root.token.getMaTinh() != "HPG")
                    this.visible.tsbtnBaoTon_BH = true;
                this.visible.tsbtnNghiemThu = true;
                this.visible.tsbtnCapNhat = true;
                this.visible.tsbtnHoanCong = true;
                this.visible.tsbtnInPhieuTC = false;
                this.visible.tsbtnVatTuMoi = true;
                this.labelFunctionTitle = "NGHIỆM THU BÁO HỎNG";
                this.visible.tsbtnChuyenTTDH = true;
                $("#atsbtnChuyenTTDH").html(
                    '<span class="dropdown-icon nc-icon-glyph arrows-1_simple-right" title="Chuyển phiếu xử lý mạng lớp trên"></span> Chuyển TTĐH'
                );
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "nguyennhan"
                );
                this.columnsPBH[objIndex].visible = true;
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "ten_dv"
                );
                this.columnsPBH[objIndex].visible = false;
                this.visible.chkChoXL = true;
                this.visible.tsbtnKhaoSat = false;
            }
            if (kieugoi_id == 9) {
                // Tổ khai thác
                this.visible.cboTramVT = false;
                this.visible.cboDaGiaoViec = !this.visible.cboTramVT;
                this.visible.tsbtnGiaoViecNhieu = false;
                this.visible.tsbtnGiaoViec = false;
                this.lblTramVT = "Đã giao việc";
                this.visible.tungaydenngay = true;
                this.visible.tsbtnTraPhieu = true;
                if (this.$root.token.getMaTinh() != "HPG")
                    this.visible.tsbtnBaoTon_BH = true;
                this.visible.tsbtnNghiemThu = false;
                this.visible.tsbtnCapNhat = true;
                this.visible.tsbtnHoanCong = true;
                this.visible.tsbtnInPhieuTC = false;
                this.visible.tsbtnChuyenTTDH = true;
                $("#atsbtnChuyenTTDH").html(
                    '<span class="dropdown-icon nc-icon-glyph arrows-1_simple-right" title="Chuyển phiếu xử lý mạng lớp trên"></span> Chuyển NET / Media'
                );
                this.labelFunctionTitle = "CẬP NHẬT KẾT QUẢ MẠNG VT LỚP TRÊN";
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "nguyennhan"
                );
                this.columnsPBH[objIndex].visible = true;
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "ten_dv"
                );
                this.columnsPBH[objIndex].visible = false;
                this.visible.chkChoXL = true;
                this.visible.tsbtnKhaoSat = false;
            }
            if (kieugoi_id == 3) {
                // Nghiệm thu 119
                this.visible.cboTramVT = true;
                this.visible.cboDaGiaoViec = !this.visible.cboTramVT;
                this.lblTramVT = "Trạm VT";
                this.visible.tungaydenngay = true;
                this.visible.tsbtnTraPhieu = true;
                this.visible.tsbtnBaoTon_BH = true;
                this.visible.tsbtnNghiemThu = true;
                this.visible.tsbtnCapNhat = true;
                this.visible.tsbtnHoanCong = false;
                this.visible.tsbtnInPhieuTC = false;
                this.visible.tsbtnBaoTon_BH = true;
                this.labelFunctionTitle = "NGHIỆM THU BÁO HỎNG";
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "nguyennhan"
                );
                this.columnsPBH[objIndex].visible = true;
                this.visible.tsbtnKhaoSat = true;
                this.visible.chkChoXL = true;
            }
            if (
                kieugoi_id == enums.TRANGTHAI_BH.DATHICONG ||
                kieugoi_id == enums.TRANGTHAI_BH.CHO_XULY
            ) {
                this.visible.tungaydenngay = true;
                this.visible.tsbtnBaoTon_BH = true;
                this.visible.tsbtnNghiemThu = true;
                this.visible.tsbtnCapNhat = true;
                this.visible.tsbtnHoanCong = false;
                this.visible.tsbtnInPhieuTC = false;
                this.visible.tsbtnBaoTon_BH = true;
                this.labelFunctionTitle = "NGHIỆM THU BÁO HỎNG";
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "nguyennhan"
                );
                this.columnsPBH[objIndex].visible = true;
                this.visible.chkChoXL = true;
                this.enable.dtpNgayXN = true;
            }
            if (kieugoi_id == enums.NET_BAOHONG.TTBH_DANG_XL) {
                // Net xlsc
                this.visible.cboTramVT = false;
                this.visible.cboDaGiaoViec = !this.visible.cboTramVT;
                this.lblTramVT = "Đã giao việc";
                this.visible.tungaydenngay = true;
                this.visible.tsbtnTraPhieu = true;
                this.visible.tsbtnBaoTon_BH = false;
                this.visible.tsbtnNghiemThu = false;
                this.visible.tsbtnCapNhat = true;
                this.visible.tsbtnHoanCong = true;
                this.visible.tsbtnInPhieuTC = false;
                this.labelFunctionTitle = "CẬP NHẬT KẾT QUẢ XỬ LÝ MẠNG VT LỚP TRÊN";
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "nguyennhan"
                );
                this.columnsPBH[objIndex].visible = true;
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "ten_dv"
                );
                this.columnsPBH[objIndex].visible = false;
                this.visible.chkChoXL = true;
                this.visible.tsbtnKhaoSat = false;
            }
            if (kieugoi_id == enums.NET_BAOHONG.TTBH_XL_XONG) {
                // TTĐH xlsc
                this.visible.cboTramVT = false;
                this.visible.cboDaGiaoViec = !this.visible.cboTramVT;
                this.visible.tsbtnGiaoViecNhieu = false;
                this.visible.tsbtnGiaoViec = false;
                this.lblTramVT = "Đã giao việc";
                this.visible.tungaydenngay = true;
                this.visible.tsbtnTraPhieu = true;
                this.visible.tsbtnBaoTon_BH = false;
                this.visible.tsbtnNghiemThu = false;
                this.visible.tsbtnCapNhat = true;
                this.visible.tsbtnHoanCong = true;
                this.visible.tsbtnInPhieuTC = false;
                this.labelFunctionTitle =
                    "TTĐH CẬP NHẬT KẾT QUẢ XỬ LÝ MẠNG VT LỚP TRÊN";
                $("#atsbtnChuyenTTDH").html(
                    '<span class="dropdown-icon nc-icon-glyph arrows-1_simple-right" title="Chuyển phiếu xử lý mạng lớp trên"></span> Chuyển NET / Media'
                );
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "nguyennhan"
                );
                this.columnsPBH[objIndex].visible = true;
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "ten_dv"
                );
                this.columnsPBH[objIndex].visible = false;
                this.visible.chkChoXL = true;
                this.visible.tsbtnKhaoSat = false;
            }
            if (kieugoi_id == 19) {
                // TTĐH xử lý bảo dưỡng
                this.visible.cboTramVT = false;
                this.visible.cboDaGiaoViec = !this.visible.cboTramVT;
                this.lblTramVT = "Đã giao việc";
                this.visible.tungaydenngay = true;
                this.visible.tsbtnTraPhieu = true;
                this.visible.tsbtnHoanThanh = true;
                if (this.$root.token.getMaTinh() != "HPG")
                    this.visible.tsbtnBaoTon_BH = true;
                this.visible.tsbtnNghiemThu = false;
                this.visible.tsbtnCapNhat = true;
                this.visible.tsbtnHoanCong = true;
                this.visible.tsbtnInPhieuTC = false;
                this.visible.tsbtnChuyenTTDH = false;
                $("#atsbtnChuyenTTDH").html(
                    '<span class="dropdown-icon nc-icon-glyph arrows-1_simple-right" title="Chuyển phiếu xử lý mạng lớp trên"></span> Chuyển VNPT - NET'
                );
                this.labelFunctionTitle = "TTĐH XỬ LÝ PHIẾU BẢO DƯỠNG";
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "nguyennhan"
                );
                this.columnsPBH[objIndex].visible = true;
                objIndex = this.columnsPBH.findIndex(
                    (obj) => obj.fieldName == "ten_dv"
                );
                this.columnsPBH[objIndex].visible = false;
                this.visible.chkChoXL = true;
                $("#chkChoXL").prop("checked", true);
                this.visible.tsbtnKhaoSat = false;
            }
            if (this.CAPNHAT_XLSC_NHIEUPHIEU == 1) {
                this.visible.tsbtnCapNhatNhieuPhieu = true;
                this.visible.tsbtnCapNhat = false;
                this.visible.tsbtnGiaoViecNhieu = true;
                this.visible.tsbtnGiaoViec = false;
                if (this.visible.tsbtnNghiemThu) {
                    this.visible.tsbtnNghiemThuNhieuPhieu = true;
                    this.visible.tsbtnNghiemThu = false;
                } else {
                    this.visible.tsbtnNghiemThuNhieuPhieu = false;
                    this.visible.tsbtnNghiemThu = false;
                }
            }
        },
        async HT_Nhom_NN(vdichvuvt_id) {
            let ds = [];
            if (this.kieugoi_id == enums.NET_BAOHONG.TTBH_DANG_XL) {
                ds = await this.sp_lay_ds_nhom_nn(
                    enums.NET_BAOHONG.TTBH_DANG_XL,
                    enums.NET_BAOHONG.LOAI_HONG,
                    0
                );
            } else if (this.kieugoi_id == enums.NET_BAOHONG.TTBH_XL_XONG) {
                ds = await this.sp_lay_ds_nhom_nn(
                    enums.NET_BAOHONG.TTBH_XL_XONG,
                    enums.NET_BAOHONG.LOAI_HONG,
                    vdichvuvt_id
                );
            } else {
                ds = await this.sp_lay_ds_nhom_nn_v2(vdichvuvt_id);
            }
            let dsTemp = [];
            if (
                this.kieugoi_id != enums.NET_BAOHONG.TTBH_DANG_XL &&
                this.kieugoi_id != enums.NET_BAOHONG.TTBH_XL_XONG
            ) {
                for (let i = 0; i < ds.length; i++) {
                    if (ds[i].dichvuvt_id == vdichvuvt_id) {
                        dsTemp.push(ds[i]);
                    }
                }
            } else {
                dsTemp = ds.slice();
            }
            this.ds_nhomNN = dsTemp.slice();
            if (this.ds_nhomNN.length > 0) {
                this.nhomnn_id = this.ds_nhomNN[0].LOAIHONG_ID;
            }
        },
        PHAILAM(CODE) {
            return this.dtThaoTac.some(
                    item => item.enable == 1 && item.code == CODE
                );
        },
        async lay_ds_nhomton_theoloai(vloai) {
            let result = [];
            try {
                let params = {
                    vloai: vloai,
                };
                let response = await api.ur_41003_018_lay_ds_nhomton_theoloai(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    let ds = response.data.data;
                    ds.forEach((item) => {
                        let temp = {};
                        temp.NHOMTON_ID = String(item.nhomton_id);
                        temp.NHOMTON = item.nhomton;
                        result.push(temp);
                    });
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async cboKQXL_Changed() {
            this.enable.tsbtnHoanCong = true;
            if (this.kqxl_id == "0") {
                this.visible.pnlNguyenNhan1 = true;
                this.visible.pnlNguyenNhan2 = true;
                this.visible.pnlTon1 = false;
                this.visible.pnlTon2 = false;
                for (let i = 0; i < this.dtThaoTac.length; i++) {
                    if (
                        this.dtThaoTac[i].control_name == "tsbtnNghiemThu" &&
                        this.dtThaoTac[i].enable == "1"
                    ) {
                        this.enable.tsbtnNghiemThu = true;
                    }
                    if (
                        this.dtThaoTac[i].control_name == "tsbtnHoanCong" &&
                        this.dtThaoTac[i].enable == "0"
                    ) {
                        this.enable.tsbtnHoanCong = false;
                    }
                }
                this.enable.tsbtnBaoTon_BH = false;
                if (this.kieugoi_id == enums.TRANGTHAI_BH.THEODOI_CHO_HT)
                    this.enable.tsbtnNghiemThu = true;
            } else if (this.kqxl_id == "1") {
                this.enable.tsbtnBaoTon_BH = false;
                for (let i = 0; i < this.dtThaoTac.length; i++) {
                    if (this.dtThaoTac[i].control_name == "tsbtnNghiemThu") {
                        this.enable.tsbtnNghiemThu = false;
                    }
                    if (
                        this.dtThaoTac[i].control_name == "tsbtnBaoTon_BH" &&
                        this.dtThaoTac[i].enable == "1"
                    ) {
                        this.enable.tsbtnBaoTon_BH = true;
                    }
                }
                this.enable.tsbtnHoanCong = false;
                this.enable.tsbtnNghiemThu = false;
                this.visible.pnlNguyenNhan1 = false;
                this.visible.pnlNguyenNhan2 = false;
                this.visible.pnlTon1 = true;
                this.visible.pnlTon2 = true;
                this.ds_nhomton = await this.lay_ds_nhomton_theoloai(1);
                if (this.ds_nhomton.length > 0) {
                    this.nhomton_id = this.ds_nhomton[0].NHOMTON_ID;
                }
            } else {
                for (let i = 0; i < this.dtThaoTac.length; i++) {
                    if (this.dtThaoTac[i].control_name == "tsbtnNghiemThu") {
                        this.enable.tsbtnNghiemThu = false;
                    }
                }
                this.visible.pnlNguyenNhan1 = false;
                this.visible.pnlNguyenNhan2 = false;
                this.visible.pnlTon1 = true;
                this.visible.pnlTon2 = true;
                this.enable.tsbtnBaoTon_BH = false;
                this.enable.tsbtnNghiemThu = false;
                this.enable.tsbtnHoanCong = false;
                this.ds_nhomton = await this.lay_ds_nhomton_theoloai(2);
                if (this.ds_nhomton.length > 0) {
                    this.nhomton_id = this.ds_nhomton[0].NHOMTON_ID;
                }
            }
        },
        cboNguoiGV_Changed() {
            if (this.nguoigv_id == null) return;
            if (this.nguoigv_id != "") this.nv_giao_id = Number(this.nguoigv_id);
            else this.nv_giao_id = this.$root.token.getNhanVienID();
        },
        cboTramVT_Changed() {
            if (this.kieugoi_id == enums.TRANGTHAI_BH.DANG_GIAO_XULY) {
                this.ds_phieubaohong = this.ds_phieubaohong_real.slice();
                return;
            }
            if (this.tramvt_id == null) {
                this.ds_phieubaohong = this.ds_phieubaohong_real.slice();
                return;
            }
            if (!this.kt_load) {
                if (String(this.tramvt_id) != "0") {
                    let cbo_tendv = "";
                    for (let i = 0; i < this.ds_tramvt.length; i++) {
                        if (this.tramvt_id == this.ds_tramvt[i].donvi_id) {
                            cbo_tendv = this.ds_tramvt[i].ten_dv;
                            break;
                        }
                    }
                    this.ds_phieubaohong = [];
                    this.ds_phieubaohong_selected = [];
                    for (let i = 0; i < this.ds_phieubaohong_real.length; i++) {
                        if (this.ds_phieubaohong_real[i].ten_dv == cbo_tendv) {
                            this.ds_phieubaohong.push(this.ds_phieubaohong_real[i]);
                        }
                    }
                } else {
                    this.ds_phieubaohong = this.ds_phieubaohong_real.slice();
                }
            }
        },
        async cboDaGiaoViec_Changed() {
            if (this.kieugoi_id == enums.TRANGTHAI_BH.DANG_GIAO_XULY)
                if (!this.kt_load) await this.HienThiDanhSach();
        },
        async cboNhomNN_Changed() {
            if (this.nhomnn_id == null || this.thuebao_id == 0) return;
            this._nhomnn_id = Number(this.nhomnn_id);
            if (
                this.kieugoi_id != enums.NET_BAOHONG.TTBH_DANG_XL &&
                this.kieugoi_id != enums.NET_BAOHONG.TTBH_XL_XONG
            )
                this.ds_nguyennhan = await this.Lay_ds_nguyennhan(
                    0,
                    this._nhomnn_id,
                    this.LOAITB_ID
                );
            else
                this.ds_nguyennhan = await this.Lay_ds_nguyennhan_baohong_v2(
                    0,
                    this._nhomnn_id,
                    this.kieugoi_id
                );
            for (let i = 0; i < this.ds_nguyennhan.length; i++) {
                if (this.ds_nguyennhan[i].chon == "1") {
                    this.ds_nguyennhan_id.push(this.ds_nguyennhan[i].cthong_id);
                }
            }
            this.setsize();
            this.txtHangSx = "";
            if (
                this.dichvuvt_id == enums.DichVuVienThong.ADSL &&
                this._nhomnn_id == enums.LOAIHONG.HONG_THIETBI_DAUCUOI &&
                this.LOAITB_ID == enums.LoaiHinhTB.INTERNET_FTTH
            ) {
                var dtAdsl = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id);
                this.txtHangSx =
                    dtAdsl.length > 0 &&
                    dtAdsl[0].hang_sx != undefined &&
                    dtAdsl[0].hang_sx != null &&
                    dtAdsl[0].hang_sx != "" ?
                    dtAdsl[0].hang_sx :
                    "Không xác định";
            }
        },
        async LAY_DS_DB_ADSL_THEO_TBID(thuebao_id) {
            let result = [];
            try {
                let params = {
                    thueBaoId: thuebao_id,
                };
                let response = await api.ur_41003_021_lay_ds_danhba_adsl(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async cboQuyTrinh_Changed() {
            try {
                this.loading(true);
                if (!this.kt_load) {
                    await this.HienThiDanhSach();
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },
        cboTinhTrang_Changed() {},
        cboNguyenNhan_Changed() {},
        async cboNhomTon_Changed() {
            this.ds_lydoton = [];
            if (!this.kqxl_id) return;
            if (this.kqxl_id == "2") {
                this.ds_lydoton = await this.Lay_ds_lydoton_theo_lhtb(
                    0,
                    Number(this.nhomton_id),
                    1,
                    this.LOAITB_ID
                );
            } else {
                this.ds_lydoton = await this.Lay_ds_lydoton_theo_lhtb(
                    0,
                    Number(this.nhomton_id),
                    0,
                    this.LOAITB_ID
                );
            }
            for (let i = 0; i < this.ds_lydoton.length; i++) {
                if (this.ds_lydoton[i].chon == "1") {
                    this.ds_lydoton_id.push(this.ds_lydoton[i].lydotonbh_id);
                }
            }
        },
        async cboLyDoTon_Changed() {},
        onFilterQuyTrinh(e) {
            var query = new Query();
            query =
                e.text !== "" ?
                query.where("quytrinh", "contains", e.text, true) :
                query;
            e.updateData(this.ds_quytrinh, query);
        },
        onFilterDichVuVT(e) {
            var query = new Query();
            query =
                e.text !== "" ?
                query.where("TEN_DVVT", "contains", e.text, true) :
                query;
            e.updateData(this.ds_dichvuvt, query);
        },
        onFilterKQXL(e) {
            var query = new Query();
            query =
                e.text !== "" ? query.where("NAME", "contains", e.text, true) : query;
            e.updateData(this.ds_kqxl, query);
        },
        onFilterNguoiGV(e) {
            var query = new Query();
            query =
                e.text !== "" ? query.where("ten_nv", "contains", e.text, true) : query;
            e.updateData(this.ds_nguoigv, query);
        },
        onFilterTramVT(e) {
            var query = new Query();
            query =
                e.text !== "" ? query.where("ten_dv", "contains", e.text, true) : query;
            e.updateData(this.ds_tramvt, query);
        },
        onFilterCboDaGiaoViec(e) {
            var query = new Query();
            query =
                e.text !== "" ? query.where("NAME", "contains", e.text, true) : query;
            e.updateData(this.ds_cboDagiaoviec, query);
        },
        onFilterNhomNN(e) {
            var query = new Query();
            query =
                e.text !== "" ?
                query.where("LOAIHONG", "contains", e.text, true) :
                query;
            e.updateData(this.ds_nhomNN, query);
        },
        onFilterNhomTon(e) {
            var query = new Query();
            query =
                e.text !== "" ?
                query.where("NHOMTON", "contains", e.text, true) :
                query;
            e.updateData(this.ds_nhomton, query);
        },
        onFilterLyDoTon(e) {
            var query = new Query();
            query =
                e.text !== "" ?
                query.where("baohong_ton", "contains", e.text, true) :
                query;
            e.updateData(this.ds_lydoton, query);
        },
        onFilterTinhTrang(e) {
            var query = new Query();
            query =
                e.text !== "" ?
                query.where("tinhtrang", "contains", e.text, true) :
                query;
            e.updateData(this.ds_tinhtrang, query);
        },
        onFilterNguyenNhan(e) {
            var query = new Query();
            query =
                e.text !== "" ?
                query.where("ct_hong", "contains", e.text, true) :
                query;
            e.updateData(this.ds_nguyennhan, query);
        },
        async HienThiTTDiaChi_TSL() {
            let ds = [];
            ds = await this.LAY_THONGTIN_DIACHI(this.diachidau_id);
            if (ds.length > 0) {
                this.diachidau_id = Number(ds[0].diachi_id);
                this.quandau_id = Number(ds[0].quan_id);
                this.phuongdau_id = Number(ds[0].phuong_id);
                if (String(ds[0].pho_id) != "") this.phodau_id = Number(ds[0].pho_id);
                else this.phodau_id = 0;
                this.sonhadau = this.ChuanHoaTen(ds[0].sonha);
            }
            ds = await this.LAY_THONGTIN_DIACHI(this.diachicuoi_id);
            if (ds.length > 0) {
                this.diachicuoi_id = Number(ds[0].diachi_id);
                this.quancuoi_id = Number(ds[0].quan_id);
                this.phuongcuoi_id = Number(ds[0].phuong_id);
                if (String(ds[0].pho_id) != "") this.phocuoi_id = Number(ds[0].pho_id);
                else this.phocuoi_id = 0;
                this.sonhacuoi = this.ChuanHoaTen(ds[0].sonha);
            }
        },
        async LAY_THONGTIN_DIACHI(diachi_id) {
            let result = [];
            try {
                let params = {
                    diachi_id: diachi_id,
                };
                let response = await api.ur_41003_013_lay_thongtin_diachi(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        ChuanHoaTen(ten) {
            if (!ten) return "";
            var ten_new = "";
            var str_con = ten.trim();
            var str_xl = "";
            var k = 0;
            var found = true;
            if (str_con.length < 1) return;
            str_con = str_con.replaceAll(/\s+/g, " ");
            try {
                while (found) {
                    k = str_con.indexOf(" ");
                    if (k > 0) {
                        str_xl = str_con.substring(0, k);
                        str_con = str_con.substring(k + 1);
                        str_xl = this.ChuyenChuHoa(str_xl);
                        if (ten_new != "") ten_new += " " + str_xl;
                        else ten_new = str_xl;
                        found = true;
                    } else found = false;
                }
                if (str_con != "") {
                    str_xl = str_con;
                    str_xl = this.ChuyenChuHoa(str_xl);
                    if (ten_new != "") ten_new = ten_new + " " + str_xl;
                    else ten_new = str_xl;
                }
                return ten_new;
            } catch (e) {
                return ten;
            }
        },
        ChuyenChuHoa(str) {
            var kq = "";
            if (str.ismatch(/[A-Z][A-Z]/g)) kq = str.toUpperCase();
            else {
                if (str[0] == "(")
                    kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase();
                else kq = str[0].toUpperCase() + str.substring(1).toLowerCase();
            }
            return kq;
        },
        async txtMaTB_KeyPress(e) {
            try {
                this.loading(true);
                if (this.txtMaTB != "") {
                    if (e.keyCode == 13) {
                        if (this.ttbh_id != enums.TRANGTHAI_BH.DATHICONG) {
                            let dvg = 0,
                                gv = 0;
                            if (this.tramvt_id != null) dvg = Number(this.tramvt_id);
                            gv = Number(this.cboDagiaoviec_id) + 1;
                            let vtungay = "0",
                                vdenngay = "0";
                            this.dtList = await this.Lay_ds_phieu_hoancong_bh_v5(
                                this.dichvuvt_id,
                                document.getElementById("chkChoXL").checked ? 7 : this.ttbh_id,
                                this.$root.token.getNhanVienID(),
                                this.txtMaTB.trim(),
                                this.huonggiao_id,
                                gv,
                                vtungay,
                                vdenngay
                            );
                        } else {
                            let vtungay = "",
                                vdenngay = "";
                            this.dtList = await this.LAY_DS_PHIEU_HOANCONG_BH_119(
                                this.dichvuvt_id,
                                this.ttbh_id,
                                this.$root.token.getNhanVienID(),
                                this.txtMaTB.trim(),
                                vtungay,
                                vdenngay
                            );
                        }
                        if (this.dtList.length > 0) {
                            this.ds_phieubaohong_real = this.LocPhieuTheoNgay();
                            this.ds_phieubaohong = this.ds_phieubaohong_real.slice();
                        } else {
                            let vtmp = this.txtMaTB.trim();
                            this.Clear();
                            this.ds_phieubaohong_real = this.LocPhieuTheoNgay();
                            this.ds_phieubaohong = this.ds_phieubaohong_real.slice();
                            this.txtMaTB = vtmp;
                        }
                    }
                }
            } catch (ex) {
                console.log(ex);
                this.$toast.error(ex);
            } finally {
                this.loading(false);
            }
        },
        async LAY_DS_PHIEU_HOANCONG_BH_119(
            dichvuvt_id,
            ttbh_id,
            nhanvien_id,
            ma_tb,
            vtungay,
            vdenngay
        ) {
            let result = [];
            try {
                let params = {
                    loaidv_id: dichvuvt_id,
                    ttbh_id: ttbh_id,
                    nhanvien_id: nhanvien_id,
                    ma_tb: ma_tb,
                    tungay: vtungay,
                    denngay: vdenngay,
                };
                let response = await api.ur_41003_027_lay_ds_phieu_hoancong_bh_119(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async LAY_TOADO_LAPDAT(hdtb_id, thuebao_id) {
            let result = "";
            try {
                let params = {
                    hdtb_id: hdtb_id,
                    thuebao_id: thuebao_id,
                    daucuoi_id: 2,
                };
                let response = await api.ur_41003_035_lay_toado_lapdat(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async btnChonToaDo_Click() {
            let data = {
                lat: null,
                lng: null,
            };
            (this.map_info.dia_chi = this.txtDiaChiLD),
            (this.map_info.title = this.txtDiaChiLD);
            try {
                this.loading(true);
                var ToadoLD = await this.LAY_TOADO_LAPDAT(-1, this.thuebao_id);
                var pair = ToadoLD.split(";");
                data.lat = Number(pair[0]);
                data.lng = Number(pair[1]);
                this.map = data;
                this.$bvModal.show("ModalChonToaDo");
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },
        async XacNhanToaDo(value) {
            if (this.map.lat == value.lat && this.map.lng == value.lng) return;
            this.$bvModal
                .msgBoxConfirm(
                    "Bạn có chắc muốn cập nhật toạ độ mới cho địa chỉ lắp đặt của thuê bao?", {
                        title: "",
                        size: "sm",
                        okTitle: "Đồng ý",
                        cancelTitle: "Hủy",
                    }
                )
                .then(async (v) => {
                    if (v) {
                        try {
                            this.loading(true);
                            await this.capnhat_toado_thuebao_v2(
                                this.thuebao_id,
                                value.lat,
                                value.lng
                            );
                        } catch (error) {
                            this.$toast.error(
                                error.message ? error.message : "Đã có lỗi xảy ra"
                            );
                        } finally {
                            this.loading(false);
                        }
                    }
                });
        },
        async capnhat_toado_thuebao_v2(thuebao_id, lat, lng) {
            try {
                let params = {
                    kieu: 0,
                    db_id: thuebao_id,
                    lat: lat,
                    lng: lng,
                    update_tb_cung_cap: 1,
                    force_update: 0,
                    daucuoi_id: 0,
                };
                let response = await api.ur_41003_036_capnhat_toado_thuebao_v2(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000"
                ) {
                    this.$toast.success("Cập nhật tọa độ thành công!");
                } else {
                    this.$toast.error(response.data.message);
                }
            } catch (e) {
                console.log(e);
                this.$toast.error(e);
            }
        },
        gridviewDanhSach_SelectRow(val) {
            this.ds_phieubaohong_selected = [];
            for (let index in val) {
                for (let ix in this.ds_phieubaohong) {
                    if (val[index].baohong_id == this.ds_phieubaohong[ix].baohong_id) {
                        this.ds_phieubaohong_selected.push(val[index]);
                    }
                }
            }
        },
        async btnGiaoPhieuTG_Click() {
            if (!this.visible.btnGiaoPhieuTG || !this.enable.btnGiaoPhieuTG) return;
            // TODO chua co de refer
            // this.$refs.dlg_giaophieutramtg.show()
        },
        LocPhieuTheoLoaiPhieu() {
            if (document.getElementById("chkPhieuTra").checked)
                this.dtList = this.dtList.filter((x) => x.nd_tra_con);
        },
        async fn_lay_ttbh_id_theo_phieu_id(phieu_id) {
            let result = [];
            try {
                let params = {
                    phieu_id: phieu_id,
                };
                let response = await api.ur_41003_038_fn_lay_ttbh_id_theo_phieu_id(
                    this.axios,
                    params
                );
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async btnTraPhieu_Click() {
            if (!this.visible.tsbtnTraPhieu || !this.enable.tsbtnTraPhieu) return;
            try {
                this.loading(true);
                if (this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có thuê bao báo hỏng!");
                    return;
                }
                if (this.kieugoi_id == 19) {
                    this.$refs.ppTraPhieuBaoHong.initPopup(
                        this.txtMaTB.trim(),
                        "",
                        this.kieugoi_id
                    );
                    this.$refs.dlg_traphieubaohong.show();
                } else {
                    this.$refs.ppTraPhieuBaoHong.initPopup(this.txtMaTB.trim(), "");
                    this.$refs.dlg_traphieubaohong.show();
                }
                await this.HienThiDanhSach();
            } catch (ex) {
                console.log(ex);
                this.$toast.error(ex);
            } finally {
                this.loading(false);
            }
        },
        async tsbtnBaoTon_Click() {
            if (!this.visible.tsbtnBaoTon_BH || !this.enable.tsbtnBaoTon_BH) return;
            try {
                if (this.baohong_id == 0 || this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có dữ liệu để thực hiện báo tồn!");
                    return;
                }
                if (this.kqxl_id == null) {
                    this.$toast.error("Hãy chọn kết quả xử lý");
                    return;
                }
                if (this.ds_lydoton_id.length == 0) {
                    this.$toast.error("Hãy chọn lý do tồn");
                    return;
                }
                this.$bvModal
                    .msgBoxConfirm("Bạn chắc chắn muốn báo tồn?", {
                        title: "",
                        size: "sm",
                        okTitle: "Đồng ý",
                        cancelTitle: "Hủy",
                    })
                    .then(async (v) => {
                        if (v) {
                            try {
                                this.loading(true);
                                this.ds_tt = [];
                                await this.TaoDuLieu_nguyennhan(true);
                                this.TaoDuLieu_baoton(true);
                                let ds_param = {
                                    kieugoi_id: this.kieugoi_id,
                                    thuebao_id: this.thuebao_id,
                                    kqxl_id: this.kqxl_id,
                                    ma_tb: this.vma_tb,
                                    ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                                    baohong_id: this.baohong_id,
                                    phieu_id: this.PHIEU_ID,
                                    ghichu_th: this.txtGhiChuTH,
                                    nhanvien_id: this.$root.token.getNhanVienID(),
                                    donvi_id: this.$root.token.getDonViID(),
                                    ma_nd: this.$root.token.getUserName(),
                                    may_cn: this.$root.token.getNguoiDungID(),
                                    ip: "",
                                    ngay_cn: new Date()
                                        .toISOString()
                                        .slice(0, 10)
                                        .replace(/-/g, ""),
                                    nhomton_id: this.nhomton_id,
                                    sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                                    ds_trangthai_bh: JSON.stringify(this.ds_tt),
                                    ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                                    ds_ton_bh: JSON.stringify(this.ds_ton),
                                    ds_para_ktr_phieu: JSON.stringify(
                                        this.ds_phieubaohong_selected
                                    ),
                                };
                                let kqnt = await this.fn_ntbh_baoton(ds_param);
                                try {
                                    let res = JSON.parse(kqnt.js);
                                    if (kqnt.result == 0) {
                                        if (res.ERROR_CODE == 0) {
                                            this.$toast.error(res.MESSAGE);
                                        }
                                        return;
                                    }
                                    if (res.ERROR_CODE == 1) {
                                        this.$toast.success(res.MESSAGE);
                                        await this.HienThiDanhSach();
                                    } else {
                                        this.$toast.error(res.MESSAGE);
                                    }
                                } catch (e) {
                                    this.$toast.error(kqnt);
                                }
                            } catch (error) {
                                this.$toast.error(
                                    error.message ? error.message : "Đã có lỗi xảy ra"
                                );
                            } finally {
                                this.loading(false);
                            }
                        }
                    });
            } catch (ex) {
                console.log(ex);
                this.$toast.error(ex);
            }
        },
        async fn_ntbh_baoton(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_115_fn_ntbh_baoton(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async cmtGiaoViecNhieu_Click() {
            if (this.ds_phieubaohong_selected.length <= 0) {
                await this.tsbtnGiaoViec_Click();
            } else {
                await this.GiaoViecNhieuPhieu();
            }
        },
        async tsbtnGiaoViec_Click() {
            try {
                if (this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
                    return;
                }
                if (this.nguoigv_id == "") {
                    this.$toast.error("Hãy nhập nhân viên giao việc!");
                    return;
                }
                if (!document.getElementById("chkNgayGV").checked) {
                    this.$toast.error("Hãy nhập ngày giao việc!");
                    return;
                }
                let vnhanvien_giao_id = 0;
                let vngaygiao;
                vnhanvien_giao_id = Number(this.nguoigv_id);
                vngaygiao = this.dtpNgayGV;
                this.gpnv_vphieu = this.PHIEU_ID;
                this.gpnv_vdonvi = Number(this.$root.token.getDonViID());
                this.gpnv_vnhanvien_giao_id = vnhanvien_giao_id;
                this.gpnv_vngaygiao = vngaygiao;
                this.gpnv_vthuebao_id = this.thuebao_id;
                this.gpnv_vma_tb = this.txtMaTB.trim();
                this.$refs.dlg_giaophieunhanvien.show();
                this.$refs.ppGiaoPhieuNhanVien.frmGiaoPhieuBHNV_Load();
                let ds = [];
                ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.PHIEU_ID, 2);
                this.HienThiDSNV(ds);
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async GiaoViecNhieuPhieu() {
            try {
                if (this.nv_giao_id == 0) {
                    this.$toast.error("Hãy nhập nhân viên giao việc!");
                    return;
                }
                if (!document.getElementById("chkNgayGV").checked) {
                    this.$toast.error("Hãy nhập ngày giao việc!");
                    return;
                }
                let vngaygiao;
                vngaygiao = this.dtpNgayGV;
                this.gpnv_v2_vdonvi = Number(this.$root.token.getDonViID());
                this.gpnv_v2_vnhanvien_giao_id = this.nv_giao_id;
                this.gpnv_v2_vngaygiao = vngaygiao;
                this.$refs.dlg_giaophieunhanvien_v2.show();
                this.$refs.ppGiaoPhieuNhanVien_v2.frmGiaoPhieuBHNV_v2_Load(
                    this.ds_phieubaohong_selected
                );
                let ds = [];
                ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.PHIEU_ID, 2);
                this.HienThiDSNV(ds);
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async tsbtnChuyenDoiTac_Click() {
            // Giao phiếu xử lý hỏng tới đối tác xã hội hóa, đặc thù riêng của HNI
            try {
                if (this.baohong_id == 0 || this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có dữ liệu để thực hiện báo tồn!");
                    return;
                }
                if (this.kqxl_id == null) {
                    this.$toast.error("Hãy chọn kết quả xử lý");
                    return;
                }
                if (this.ds_lydoton_id.length == 0) {
                    this.$toast.error("Hãy chọn lý do tồn");
                    return;
                }
                this.$refs.ppDoiTac.init();
                this.$refs.dlg_doitac.show();
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async dlg_doitac_xacnhan(vdoitac_id) {
            await this.Giaophieu_DoiTac(this.baohong_id, this.PHIEU_ID, vdoitac_id);
            await this.HienThiDanhSach();
            this.$refs.dlg_doitac.hide();
        },
        async Giaophieu_DoiTac(baohong_id, phieu_id, doitac_id) {
            try {
                let params = {
                    baohong_id: baohong_id,
                    phieu_id: phieu_id,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    doitac_id: doitac_id,
                    nguoi_cn: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                };
                let response = await api.ur_41003_040_giaophieu_doitac(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000"
                ) {} else {
                    this.$toast.error(response.data.message);
                }
            } catch (e) {
                console.log(e);
                this.$toast.error(e);
            }
        },
        async tsbtnLayTTMoi_Click() {
            try {
                this.loading(true);
                await this.HienThiDanhSach();
            } catch (e) {
                console.log(e);
                this.$toast.error("Xảy ra lỗi: " + e);
            } finally {
                this.loading(false);
            }
        },
        tsbtnExcel_Click() {
            if (this.ds_phieubaohong.length == 0) {
                this.$toast.error("Không có dữ liệu để xuất Excel !");
                return;
            }
            let worksheet = XLSX.utils.json_to_sheet(this.ds_phieubaohong);
            let workbook = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");
            XLSX.writeFile(workbook, "DS_phieubaohong.xlsx");
        },
        async tsbtnNghiemThu_Click() {
            if (!this.visible.tsbtnNghiemThu || !this.enable.tsbtnNghiemThu) return;
            try {
                this.loading(true);
                await this.HoanThien();
            } catch (e) {
                console.log(e);
                this.$toast.error("Xảy ra lỗi: " + e);
            } finally {
                this.loading(false);
            }
        },
        async tsbtnNghiemThuNhieuPhieu_Click() {
            if (!this.visible.tsbtnNghiemThuNhieuPhieu) return;
            if (this.ds_phieubaohong_selected.length <= 1) {
                await this.tsbtnNghiemThu_Click();
            } else {
                await this.HoanThienNhieuPhieu();
            }
        },
        async fn_ntbh_nghiemthu(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_077_fn_ntbh_nghiemthu(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async TaoDuLieu_nguyennhan(themmoi) {
            this.ds_nn = [];
            if (this.kqxl_id == "0") {
                let row = {};
                for (let i = 0; i < this.ds_nguyennhan_id.length; i++) {
                    for (let j = 0; j < this.ds_nguyennhan.length; j++) {
                        if (
                            this.ds_nguyennhan[j].cthong_id ==
                            this.ds_nguyennhan_id[i].cthong_id
                        ) {
                            row = {};
                            row.phieu_id = this.PHIEU_ID;
                            row.cthong_id = Number(this.ds_nguyennhan_id[i].cthong_id);
                            if (
                                this._nhomnn_id == enums.LOAIHONG.HONG_THIETBI_DAUCUOI &&
                                this.dichvuvt_id == enums.DichVuVienThong.ADSL
                            ) {
                                let dtAdsl = await this.LAY_DS_DB_ADSL_THEO_TBID(
                                    this.thuebao_id
                                );
                                row.hang_sx = this.txtHangSx;
                                row.hardware_version =
                                    dtAdsl.length > 0 ? dtAdsl[0].hardware_version : "";
                                row.firmware_version =
                                    dtAdsl.length > 0 ? dtAdsl[0].firmware_version : "";
                            }
                            this.ds_nn.push(row);
                            break;
                        }
                    }
                }
            }
        },
        TaoDuLieu_baoton(themmoi) {
            this.ds_ton = [];
            if (this.kqxl_id == "1" || this.kqxl_id == "2") {
                // Nếu xử lý được thì phải chọn nguyên nhân
                let row = {};
                for (let i = 0; i < this.ds_lydoton_id.length; i++) {
                    for (let j = 0; j < this.ds_lydoton.length; j++) {
                        if (
                            this.ds_lydoton[j].lydotonbh_id ==
                            this.ds_lydoton_id[i].lydotonbh_id
                        ) {
                            row = {};
                            row.baohong_id = this.baohong_id;
                            row.ctton_id = Number(this.ds_lydoton_id[i].lydotonbh_id);
                            this.ds_ton.push(row);
                            break;
                        }
                    }
                }
            }
        },
        init_ds_cbonguyennhan() {
            console.log("init_ds_cbonguyennhan");
            console.log(this.ds_nguyennhan_id);
            let ds_cbonguyennhan = [];
            let ds_nguyennhan_init = [];
            for (let i = 0; i < this.ds_nguyennhan.length; i++) {
                for (let j = 0; j < this.ds_nguyennhan_id.length; j++) {
                    if (this.ds_nguyennhan[i].cthong_id == this.ds_nguyennhan_id[j]) {
                        let temp = Object.assign({}, this.ds_nguyennhan[i]);
                        temp.checkstate = 1;
                        ds_cbonguyennhan.push(temp);
                        ds_nguyennhan_init.push(this.ds_nguyennhan[i]);
                        break;
                    }
                }
            }
            let dokiem_para = {
                cbo_nguyennhan: JSON.stringify(ds_cbonguyennhan),
                ds_nguyennhan: JSON.stringify(ds_nguyennhan_init),
            };
            console.log(dokiem_para);
            return dokiem_para;
        },
        init_ds_lydoton() {
            let ds_cboLydoton = [];
            for (let i = 0; i < this.ds_lydoton.length; i++) {
                let temp = Object.assign({}, this.ds_lydoton[i]);
                let checkstate = 0;
                for (let j = 0; j < this.ds_lydoton_id.length; j++) {
                    if (
                        this.ds_lydoton[i].lydotonbh_id ==
                        this.ds_lydoton_id[j].lydotonbh_id
                    ) {
                        checkstate = 1;
                        break;
                    }
                }
                temp.checkstate = checkstate;
                ds_cboLydoton.push(temp);
            }
            return ds_cboLydoton;
        },
        async HoanThien() {
            try {
                if (this.baohong_id == 0 || this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có dữ liệu nghiệm thu!");
                    return;
                }
                let ds_hoancong_para = [{
                    baohong_id: this.baohong_id,
                    phieu_id: this.PHIEU_ID,
                    nghiemthu: 0,
                    kt_tsl_tram_tg: this.PHAILAM("KT_TSL_TRAM_TG") ? "1" : "0",
                    dichvuvt_id: this.dichvuvt_id,
                    hdtb_id: -1,
                    thuebao_id: this.thuebao_id,
                    daucuoi_id: 0,
                    bo_qua_dokiem: this.PHAILAM("BO_QUA_DOKIEM") ? "1" : "0",
                    nguoi_cn: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ip_cn: "",
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    att_adsl: this.ttnd_att_adsl,
                    att_gpon: this.ttnd_att_gpon,
                    snr_adsl: this.ttnd_snr_adsl,
                    kqxl_id: this.kqxl_id,
                    ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
                    ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                    kieugoi_id: this.kieugoi_id,
                    huonggiao_id: this.huonggiao_id,
                    ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                }, ];
                this.ds_tt = [];
                await this.TaoDuLieu_nguyennhan(true);
                this.TaoDuLieu_baoton(true);
                let ds_param = {
                    phieu_id: this.PHIEU_ID,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    thuebao_id: this.thuebao_id,
                    kqxl_id: this.kqxl_id,
                    ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                    ma_tb: this.vma_tb,
                    baohong_id: this.baohong_id,
                    check_giaophieu_199_nghiemthu: this.PHAILAM(
                            "CHECK_GIAOPHIEU_199_NGHIEMTHU"
                        ) ?
                        "1" : "0",
                    lam_hoac_boqua: -1,
                    ds_hoancong_para: JSON.stringify(ds_hoancong_para),
                    kt_tsl_nt: this.PHAILAM("KT_TSL_NT") ? "1" : "0",
                    bo_qua_dokiem: this.PHAILAM("BO_QUA_DOKIEM") ? "1" : "0",
                    dichvuvt_id: this.dichvuvt_id,
                    nguoi_cn: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ip_cn: "",
                    ngay_cn: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                    att_adsl: this.ttnd_att_adsl,
                    att_gpon: this.ttnd_att_gpon,
                    snr_adsl: this.ttnd_snr_adsl,
                    kt_tsl_tram_tg: this.PHAILAM("KT_TSL_TRAM_TG") ? "1" : "0",
                    ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
                    kiemtra_srgp: this.PHAILAM("KIEMTRA_SRGP") ? "1" : "0",
                    dokiem_para: JSON.stringify(this.init_ds_cbonguyennhan()),
                    hdtb_id: -1,
                    kiemtra_sr_gpon: 0, // trong DB query lai
                    bo_qua_capnhat_tb: this.PHAILAM("BO_QUA_CAPNHAT_TB") ? "1" : "0",
                    kiemtra_lienhe_nghiemthu_bh: 0,
                    kieugoi_id: this.kieugoi_id,
                    ghichu_th: this.txtGhiChuTH,
                    donvi_id: this.$root.token.getDonViID(),
                    ma_nd: this.$root.token.getUserName(),
                    nhomton_id: this.nhomton_id,
                    sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                    ds_trangthai_bh: JSON.stringify(this.ds_tt),
                    ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                    ds_ton_bh: JSON.stringify(this.ds_ton),
                    ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                    nghiemthu: 0,
                    ngay_ht: this.dtpNgayTH,
                    ngay_xn: this.dtpNgayXN, // TODO Khong tim thay o dau
                    tudong_giaophieu_duyet_capvt: this.PHAILAM(
                            "TUDONG_GIAOPHIEU_DUYET_CAPVT"
                        ) ?
                        "1" : "0",
                    tudong_giaophieu_duyet_thuhoivt: this.PHAILAM(
                            "TUDONG_GIAOPHIEU_DUYET_THUHOIVT"
                        ) ?
                        "1" : "0",
                    tudong_giaophieu_quyettoan_vt: this.PHAILAM(
                            "TUDONG_GIAOPHIEU_QUYETTOAN_VT"
                        ) ?
                        "1" : "0",
                    tudong_giaophieu_yc_nhapcap: this.PHAILAM(
                            "TUDONG_GIAOPHIEU_YC_NHAPCAP"
                        ) ?
                        "1" : "0",
                    gui_sms_khi_hoancong: this.PHAILAM("GUI_SMS_KHI_HOANCONG") ?
                        "1" : "0",
                    dienthoailh: this.txtDienThoaiLH,
                };
                // P1: lam_hoac_boqua = -1, kiemtra_lienhe_nghiemthu_bh = 0, nghiemthu = 0
                let kqnt = await this.fn_ntbh_nghiemthu(ds_param);
                let result = "";
                try {
                    result = JSON.parse(kqnt.js);
                } catch (e) {
                    this.$toast.error(kqnt);
                    return;
                }
                if (result.ERROR_CODE == 1) {
                    // loi khi valid KIEMTRA_PHIEU
                    this.$toast.success(result.MESSAGE);
                    return;
                }
                if (result.MESSAGE.startsWith("LAM_HOAC_BOQUA|")) {
                    this.$bvModal
                        .msgBoxConfirm(result.MESSAGE.replace("LAM_HOAC_BOQUA|", ""), {
                            title: "",
                            size: "sm",
                            okTitle: "Đồng ý",
                            cancelTitle: "Hủy",
                        })
                        .then(async (v) => {
                            if (v) {
                                // P2: lam_hoac_boqua = 1, kiemtra_lienhe_nghiemthu_bh = 0, nghiemthu = 0
                                ds_param.lam_hoac_boqua = 1;
                                ds_param.kiemtra_lienhe_nghiemthu_bh = 0;
                                ds_param.nghiemthu = 0;
                                kqnt = await this.fn_ntbh_nghiemthu(ds_param);
                                try {
                                    result = JSON.parse(kqnt.js);
                                } catch (e) {
                                    this.$toast.error(kqnt);
                                    return;
                                }
                                if (result.MESSAGE.includes("Bạn có muốn tiếp tục không")) {
                                    this.$bvModal
                                        .msgBoxConfirm(result.MESSAGE, {
                                            title: "",
                                            size: "sm",
                                            okTitle: "Đồng ý",
                                            cancelTitle: "Hủy",
                                        })
                                        .then(async (v) => {
                                            if (v) {
                                                // P4: lam_hoac_boqua = 1, kiemtra_lienhe_nghiemthu_bh = 1, nghiemthu = 0
                                                ds_param.lam_hoac_boqua = 1;
                                                ds_param.kiemtra_lienhe_nghiemthu_bh = 1;
                                                ds_param.nghiemthu = 0;
                                                kqnt = await this.fn_ntbh_nghiemthu(ds_param);
                                                try {
                                                    result = JSON.parse(kqnt.js);
                                                } catch (e) {
                                                    this.$toast.error(kqnt);
                                                    return;
                                                }
                                                if (
                                                    result.MESSAGE ==
                                                    "Bạn chắc chắn muốn nghiệm thu kết quả báo hỏng?"
                                                ) {
                                                    this.$bvModal
                                                        .msgBoxConfirm(
                                                            "Bạn chắc chắn muốn nghiệm thu kết quả báo hỏng ?", {
                                                                title: "",
                                                                size: "sm",
                                                                okTitle: "Đồng ý",
                                                                cancelTitle: "Hủy",
                                                            }
                                                        )
                                                        .then(async (v) => {
                                                            if (v) {
                                                                // P5: lam_hoac_boqua = 1, kiemtra_lienhe_nghiemthu_bh = 1, nghiemthu = 1
                                                                ds_param.lam_hoac_boqua = 1;
                                                                ds_param.kiemtra_lienhe_nghiemthu_bh = 1;
                                                                ds_param.nghiemthu = 1;
                                                                kqnt = await this.fn_ntbh_nghiemthu(ds_param);
                                                                try {
                                                                    result = JSON.parse(kqnt.js);
                                                                } catch (e) {
                                                                    this.$toast.error(kqnt);
                                                                    return;
                                                                }
                                                                if (result.ERROR_CODE == 1) {
                                                                    this.$toast.success(result.MESSAGE);
                                                                    await this.HienThiDanhSach();
                                                                } else {
                                                                    this.$toast.error(result.MESSAGE);
                                                                }
                                                            }
                                                        });
                                                }
                                            }
                                        });
                                }
                            } else {
                                // P3: lam_hoac_boqua = 0, kiemtra_lienhe_nghiemthu_bh = 0, nghiemthu = 0
                                ds_param.lam_hoac_boqua = 0;
                                ds_param.kiemtra_lienhe_nghiemthu_bh = 0;
                                ds_param.nghiemthu = 0;
                                kqnt = await this.fn_ntbh_nghiemthu(ds_param);
                                try {
                                    result = JSON.parse(kqnt.js);
                                    if (result.ERROR_CODE == 1) {
                                        this.$toast.success(result.MESSAGE);
                                        await this.HienThiDanhSach();
                                    } else {
                                        this.$toast.error(result.MESSAGE);
                                    }
                                } catch (e) {
                                    this.$toast.error(kqnt);
                                }
                            }
                        });
                } else {
                    this.$toast.error(result.MESSAGE); // loi khi valid GIAOPHIEU_BAOHONG_CATCHUYEN
                    return;
                }
            } catch (ex) {
                this.$toast.error("" + ex.ToString());
            }
        },
        async fn_ntbh_hoanthanh_nhieuphieu(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_078_fn_ntbh_hoanthanh_nhieuphieu(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async HoanThienNhieuPhieu() {
            try {
                if (this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có dữ liệu nghiệm thu!");
                    return;
                }
                this.loading(true);
                let ds_hoancong_para = [{
                    baohong_id: this.baohong_id,
                    phieu_id: this.PHIEU_ID,
                    nghiemthu: 0,
                    kt_tsl_tram_tg: this.PHAILAM("KT_TSL_TRAM_TG") ? "1" : "0",
                    dichvuvt_id: this.dichvuvt_id,
                    hdtb_id: -1,
                    thuebao_id: this.thuebao_id,
                    daucuoi_id: 0,
                    bo_qua_dokiem: this.PHAILAM("BO_QUA_DOKIEM") ? "1" : "0",
                    nguoi_cn: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ip_cn: "",
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    att_adsl: this.ttnd_att_adsl,
                    att_gpon: this.ttnd_att_gpon,
                    snr_adsl: this.ttnd_snr_adsl,
                    kqxl_id: this.kqxl_id,
                    ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
                    ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                    kieugoi_id: this.kieugoi_id,
                    huonggiao_id: this.huonggiao_id,
                    ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                }, ];
                this.ds_tt = [];
                await this.TaoDuLieu_nguyennhan(true);
                this.TaoDuLieu_baoton(true);
                let ds_param = {
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    nghiemthu: 0,
                    js_dgvdanhsach: JSON.stringify(this.ds_phieubaohong),
                    kieugoi_id: this.kieugoi_id,
                    kqxl_id: this.kqxl_id,
                    ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                    check_giaophieu_199_nghiemthu: this.PHAILAM(
                            "CHECK_GIAOPHIEU_199_NGHIEMTHU"
                        ) ?
                        "1" : "0",
                    lam_hoac_boqua: -1,
                    kt_tsl_nt: this.PHAILAM("KT_TSL_NT") ? "1" : "0",
                    bo_qua_dokiem: this.PHAILAM("BO_QUA_DOKIEM") ? "1" : "0",
                    dichvuvt_id: this.dichvuvt_id,
                    ds_hoancong_para: JSON.stringify(ds_hoancong_para),
                    kiemtra_srgp: this.PHAILAM("KIEMTRA_SRGP") ? "1" : "0",
                    tudong_giaophieu_duyet_capvt: this.PHAILAM(
                            "TUDONG_GIAOPHIEU_DUYET_CAPVT"
                        ) ?
                        "1" : "0",
                    tudong_giaophieu_duyet_thuhoivt: this.PHAILAM(
                            "TUDONG_GIAOPHIEU_DUYET_THUHOIVT"
                        ) ?
                        "1" : "0",
                    tudong_giaophieu_quyettoan_vt: this.PHAILAM(
                            "TUDONG_GIAOPHIEU_QUYETTOAN_VT"
                        ) ?
                        "1" : "0",
                    tudong_giaophieu_yc_nhapcap: this.PHAILAM(
                            "TUDONG_GIAOPHIEU_YC_NHAPCAP"
                        ) ?
                        "1" : "0",
                    gui_sms_khi_hoancong: this.PHAILAM("GUI_SMS_KHI_HOANCONG") ?
                        "1" : "0",
                    bo_qua_capnhat_tb: this.PHAILAM("BO_QUA_CAPNHAT_TB") ? "1" : "0",
                    nguoi_cn: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ip_cn: "",
                    ghichu_th: this.txtGhiChuTH,
                    ngay_ht: this.dtpNgayTH,
                    ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                    att_adsl: this.ttnd_att_adsl,
                    att_gpon: this.ttnd_att_gpon,
                    snr_adsl: this.ttnd_snr_adsl,
                    kt_tsl_tram_tg: this.PHAILAM("KT_TSL_TRAM_TG") ? "1" : "0", //
                    dokiem_para: JSON.stringify(this.init_ds_cbonguyennhan()),
                    ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
                    kiemtra_sr_gpon: "-1", // trong DB query lai, khong can truyen
                    ma_nd: this.$root.token.getUserName(),
                    donvi_id: this.$root.token.getDonViID(),
                    dienthoailh: this.txtDienThoaiLH,
                    nhomton_id: this.nhomton_id,
                    ngay_xn: this.dtpNgayXN, // TODO Khong tim thay o dau
                    sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                    ds_trangthai_bh: JSON.stringify(this.ds_tt),
                    ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                    ds_ton_bh: JSON.stringify(this.ds_ton),
                    ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                };
                // P1: nghiemthu = 0
                let kqnt = await this.fn_ntbh_hoanthanh_nhieuphieu(ds_param);
                let result = "";
                try {
                    result = JSON.parse(kqnt.js);
                } catch (e) {
                    this.$toast.error(kqnt);
                    return;
                }
                if (
                    result.MESSAGE == "Bạn chắc chắn muốn nghiệm thu kết quả báo hỏng?"
                ) {
                    this.$bvModal
                        .msgBoxConfirm("Bạn chắc chắn muốn nghiệm thu kết quả báo hỏng?", {
                            title: "",
                            size: "sm",
                            okTitle: "Đồng ý",
                            cancelTitle: "Hủy",
                        })
                        .then(async (v) => {
                            if (v) {
                                // P2: nghiemthu = 1, lam_hoac_boqua = -1
                                ds_param.nghiemthu = 1;
                                ds_param.lam_hoac_boqua = -1;
                                kqnt = await this.fn_ntbh_hoanthanh_nhieuphieu(ds_param);
                                try {
                                    result = JSON.parse(kqnt.js);
                                } catch (e) {
                                    this.$toast.error(kqnt);
                                    return;
                                }
                                if (result.MESSAGE.startsWith("LAM_HOAC_BOQUA|")) {
                                    this.$bvModal
                                        .msgBoxConfirm(
                                            result.MESSAGE.replace("LAM_HOAC_BOQUA|", ""), {
                                                title: "",
                                                size: "sm",
                                                okTitle: "Đồng ý",
                                                cancelTitle: "Hủy",
                                            }
                                        )
                                        .then(async (v) => {
                                            if (v) {
                                                // P4: nghiemthu = 1, lam_hoac_boqua = 1
                                                ds_param.nghiemthu = 1;
                                                ds_param.lam_hoac_boqua = 1;
                                                kqnt = await this.fn_ntbh_hoanthanh_nhieuphieu(
                                                    ds_param
                                                );
                                            } else {
                                                // P3: nghiemthu = 1, lam_hoac_boqua = 0
                                                ds_param.nghiemthu = 1;
                                                ds_param.lam_hoac_boqua = 0;
                                                kqnt = await this.fn_ntbh_hoanthanh_nhieuphieu(
                                                    ds_param
                                                );
                                            }
                                            try {
                                                result = JSON.parse(kqnt.js);
                                                if (result.ERROR_CODE == 1) {
                                                    this.$toast.success(result.MESSAGE);
                                                    await this.HienThiDanhSach();
                                                } else {
                                                    this.$toast.error(result.MESSAGE);
                                                }
                                            } catch (e) {
                                                this.$toast.error(kqnt);
                                                return;
                                            }
                                        });
                                }
                            }
                        });
                }
            } catch (ex) {
                this.$toast.error("" + ex.ToString());
            } finally {
                this.loading(false);
            }
        },
        async fn_ntbh_chuyenttvt(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_112_fn_ntbh_chuyenttvt(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async tsbtnChuyenTTVT_Click() {
            if (!this.visible.tsbtnChuyenTTVT || !this.enable.tsbtnChuyenTTVT) return;
            try {
                if (this.baohong_id == 0 || this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có dữ liệu để thực hiện!");
                    return;
                }
                this.loading(true);
                this.ds_tt = [];
                await this.TaoDuLieu_nguyennhan(true);
                this.TaoDuLieu_baoton(true);
                let ds_param = {
                    baohong_id: this.baohong_id,
                    phieu_id: this.PHIEU_ID,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    capnhat: 0,
                    ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                    kieugoi_id: this.kieugoi_id,
                    thuebao_id: this.thuebao_id,
                    kqxl_id: this.kqxl_id,
                    ma_tb: this.vma_tb,
                    ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                    ghichu_th: this.txtGhiChuTH,
                    donvi_id: this.$root.token.getDonViID(),
                    ma_nd: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ip: "",
                    ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                    nhomton_id: this.nhomton_id,
                    sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                    ds_trangthai_bh: JSON.stringify(this.ds_tt),
                    ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                    ds_ton_bh: JSON.stringify(this.ds_ton),
                    huonggiao_id: this.huonggiao_id,
                };
                let kqnt = await this.fn_ntbh_chuyenttvt(ds_param);
                let result = "";
                try {
                    result = JSON.parse(kqnt.js);
                } catch (e) {
                    this.$toast.error(kqnt);
                    return;
                }
                if (result.ERROR_CODE == 0) return; //  valid kiem tra phieu false
                this.$bvModal
                    .msgBoxConfirm("Bạn chắc chắn muốn chuyển phiếu?", {
                        title: "",
                        size: "sm",
                        okTitle: "Đồng ý",
                        cancelTitle: "Hủy",
                    })
                    .then(async (v) => {
                        if (v) {
                            ds_param.capnhat = 1;
                            kqnt = await this.fn_ntbh_chuyenttvt(ds_param);
                            try {
                                result = JSON.parse(kqnt.js);
                            } catch (e) {
                                this.$toast.error(kqnt);
                                return;
                            }
                            if (result.ERROR_CODE == 1) {
                                this.$toast.success("Cập nhật dữ liệu thành công!");
                                await this.HienThiDanhSach();
                            } else {
                                this.$toast.error(result.MESSAGE);
                            }
                        }
                    });
            } catch (ex) {
                this.$toast.error(ex);
                console.log(e);
            } finally {
                this.loading(false);
            }
        },
        tsbtnDoKiem_Click() {
            this.showPort(0);
        },
        showPort(vkieu) {
            try {
                // if (this.ds_phieubaohong.length <= 0) {
                //     this.$toast.error("Bạn hãy nhập mã thuê bao !");
                //     $("#txtMaTB").focus();
                //     return;
                // }
                this.checkPortOnClick()
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async fn_ntbh_chuyentheodoi(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_114_fn_ntbh_chuyentheodoi(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async dlg_chonsongay_xacnhan(songay) {
            if (this.txtGhiChuTH.trim()) {
                this.$toast.error("Hãy nhập ghi chú!");
                $("#txtGhiChuTH").focus();
                return;
            }
            this.$bvModal
                .msgBoxConfirm("Bạn chắc chắn muốn chuyển theo dõi?", {
                    title: "",
                    size: "sm",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                })
                .then(async (v) => {
                    if (v) {
                        try {
                            this.loading(true);
                            this.ds_tt = [];
                            await this.TaoDuLieu_nguyennhan(true);
                            this.TaoDuLieu_baoton(true);
                            let ds_param = {
                                kieugoi_id: this.kieugoi_id,
                                thuebao_id: this.thuebao_id,
                                kqxl_id: this.kqxl_id,
                                ma_tb: this.vma_tb,
                                ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                                baohong_id: this.baohong_id,
                                phieu_id: this.PHIEU_ID,
                                ghichu_th: this.txtGhiChuTH,
                                nhanvien_id: this.$root.token.getNhanVienID(),
                                donvi_id: this.$root.token.getDonViID(),
                                ma_nd: this.$root.token.getUserName(),
                                may_cn: this.$root.token.getNguoiDungID(),
                                ip: "",
                                ngay_cn: new Date()
                                    .toISOString()
                                    .slice(0, 10)
                                    .replace(/-/g, ""),
                                nhomton_id: this.nhomton_id,
                                sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                                ds_trangthai_bh: JSON.stringify(this.ds_tt),
                                ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                                ds_ton_bh: JSON.stringify(this.ds_ton),
                                ds_para_ktr_phieu: JSON.stringify(
                                    this.ds_phieubaohong_selected
                                ),
                                huonggiao_id: this.huonggiao_id,
                            };
                            let kqnt = await this.fn_ntbh_chuyentheodoi(ds_param);
                            if (kqnt.result == 0) return;
                            this.$toast.success("Cập nhật dữ liệu thành công!");
                            await this.HienThiDanhSach();
                            this.$refs.dlg_chonsongay.hide();
                        } catch (error) {
                            this.$toast.error(
                                error.message ? error.message : "Đã có lỗi xảy ra"
                            );
                            console.log(e);
                        } finally {
                            this.loading(false);
                        }
                    }
                });
        },
        tsbtnChuyenTheoDoi_Click() {
            if (!this.visible.tsbtnChuyenTheoDoi) return;
            try {
                if (this.baohong_id == 0 || this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có dữ liệu để thực hiện!");
                    return;
                }
                this.$refs.ppChonSoNgay.init();
                this.$refs.dlg_chonsongay.show();
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async fn_ntbh_capnhatkq(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_113_fn_ntbh_capnhatkq(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async tsbtnCapNhatKQ_Click() {
            try {
                if (!this.ds_phieubaohong.length) {
                    this.$toast.warning("Không có thuê bao báo hỏng");
                    return;
                }
                this.loading(true);
                this.ds_tt = [];
                await this.TaoDuLieu_nguyennhan(true);
                this.TaoDuLieu_baoton(true);
                let ds_param = {
                    kieugoi_id: this.kieugoi_id,
                    thuebao_id: this.thuebao_id,
                    kqxl_id: this.kqxl_id,
                    ma_tb: this.vma_tb,
                    ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                    baohong_id: this.baohong_id,
                    phieu_id: this.PHIEU_ID,
                    ghichu_th: this.txtGhiChuTH,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    donvi_id: this.$root.token.getDonViID(),
                    ma_nd: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ip: "",
                    ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                    nhomton_id: this.nhomton_id,
                    sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                    ds_trangthai_bh: JSON.stringify(this.ds_tt),
                    ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                    ds_ton_bh: JSON.stringify(this.ds_ton),
                    ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                };
                let kqnt = await this.fn_ntbh_capnhatkq(ds_param);
                if (kqnt.result == 0) return;
                // let vma_tb = this.txtMaTB.trim();
                await this.HienThiDanhSach();
                this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
            } catch (ex) {
                this.$toast.error(ex);
            } finally {
                this.loading(false);
            }
        },
        async tsbtnCapNhatNhieuPhieu_Click() {
            if (!this.visible.tsbtnCapNhatNhieuPhieu) return;
            try {
                if (this.ds_phieubaohong_selected.length <= 1) {
                    tsbtnCapNhatKQ_Click();
                } else {
                    if (await this.CapNhatNhieuPhieu()) {
                        await this.HienThiDanhSach();
                        this.$toast.success("Cập nhật dữ liệu thành công!");
                    }
                }
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async fn_ntbh_capnhatkq_nhieuphieu(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_116_fn_ntbh_capnhatkq_nhieuphieu(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        KiemTra_CapNhatKQ(nghiemthu) {
            if (this.ds_phieubaohong.length <= 0) {
                this.$toast.error("Không có thuê bao báo hỏng");
                return false;
            }
            if (
                !this.PHAILAM("BO_QUA_GIAOVIEC") &&
                this.kieugoi_id != enums.TRANGTHAI_BH.THEODOI_CHO_HT
            ) {
                if (
                    this.ds_nvxl.length <= 0 &&
                    this.kieugoi_id != 3 &&
                    this.kieugoi_id != enums.NET_BAOHONG.TTBH_XL_XONG &&
                    this.kieugoi_id != 9
                ) {
                    this.$toast.error("Chưa giao việc cho nhân viên");
                    return false;
                }
            }
            // y/c cj thủy // Nếu nghiệm thu phải nhập nguyên nhân hỏng hoặc báo tồn.
            // 119 không cần nhập nguyên nhân hỏng
            if (this.kieugoi_id != 3) {
                if (this.kqxl == "0") {
                    // Kiểm tra xem đã chọn tình trạng chưa
                    if (this.ds_nguyennhan_id.length == 0) {
                        this.$toast.error("Hãy nhập nguyên nhân hỏng!");
                        return false;
                    }
                } else {
                    if (this.ds_lydoton_id.length == 0) {
                        this.$toast.error("Hãy nhập lý do tồn!");
                        return false;
                    }
                    if (
                        this.ttbh_id == enums.TRANGTHAI_BH.CHO_XULY &&
                        this.kqxl_id == "2"
                    ) {
                        this.$toast.error(
                            "Không được chọn Kết quả xử lý là 'Đang chờ xử lý' đối với báo hỏng Chờ xử lý !"
                        );
                        return false;
                    }
                }
            }
            return true;
        },
        async CapNhatNhieuPhieu() {
            try {
                if (!this.ds_phieubaohong.length) {
                    this.$toast.warning("Không có thuê bao báo hỏng");
                    return;
                }
                this.loading(true);
                if (this.KiemTra_CapNhatKQ(false)) {
                    // ds_nn co vphieu_id, ds_ton co vbaohong_id rieng cho tung ban ghi trong ds chon
                    // DB confirm khong can => truyen chung cho ca mang ds chon
                    this.ds_tt = [];
                    await this.TaoDuLieu_nguyennhan(true);
                    this.TaoDuLieu_baoton(true);
                    let ds_param = {
                        js_dschon: JSON.stringify(this.ds_phieubaohong_selected),
                        kieugoi_id: this.kieugoi_id,
                        thuebao_id: this.thuebao_id,
                        kqxl_id: this.kqxl_id,
                        ma_tb: this.vma_tb,
                        ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                        baohong_id: this.baohong_id,
                        phieu_id: this.PHIEU_ID,
                        ghichu_th: this.txtGhiChuTH,
                        nhanvien_id: this.$root.token.getNhanVienID(),
                        donvi_id: this.$root.token.getDonViID(),
                        ma_nd: this.$root.token.getUserName(),
                        may_cn: this.$root.token.getNguoiDungID(),
                        ip: "",
                        ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                        nhomton_id: this.nhomton_id,
                        sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                        ds_trangthai_bh: JSON.stringify(this.ds_tt),
                        ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                        ds_ton_bh: JSON.stringify(this.ds_ton),
                        ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                    };
                    let kqnt = await this.fn_ntbh_capnhatkq_nhieuphieu(ds_param);
                    try {
                        let result = JSON.parse(kqnt.js);
                        if (kqnt.result == 0) {
                            if (result.ERROR_CODE == 0) {
                                this.$toast.error(result.MESSAGE);
                            }
                            return false;
                        }
                        return true;
                    } catch (e) {
                        this.$toast.error(kqnt);
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (e) {
                console.log(e);
                return false;
            } finally {
                this.loading(false);
            }
        },
        dtpTuNgay_ValueChanged() {
            if (document.getElementById("chkTuNgay").checked)
                this.ds_phieubaohong = this.LocPhieuTheoNgay();
        },
        dtpDenNgay_ValueChanged() {
            if (document.getElementById("chkTuNgay").checked)
                this.ds_phieubaohong = this.LocPhieuTheoNgay();
        },
        chkTuNgay_CheckedChanged() {
            this.ds_phieubaohong = this.LocPhieuTheoNgay();
        },
        chkNgayBG_CheckedChanged() {
            this.enable.dtpNgayTH = document.getElementById("chkNgayBG").checked;
        },
        async LAY_DS_PHIEUVT_HDBH(phieu_id, baohong_id, hdtb_id, kieutbi, status) {
            let kqnt = null;
            try {
                let params = {
                    phieu_id: phieu_id,
                    baohong_id: baohong_id,
                    hdtb_id: hdtb_id,
                    kieutbi: kieutbi,
                    status: status,
                };
                let response = await api.ur_41003_043_sp_lay_ds_phieuvt_hdbh(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                }
            } catch (e) {
                console.log(e);
                this.$toast.error(e);
            }
            return kqnt;
        },
        async tsbtnChuyenTo_Click() {
            if (!this.enable.tsbtnChuyenTo || !this.visible.tsbtnChuyenTo) return;
            try {
                if (this.PHAILAM("KIEMTRA_VT_CHUYENTO")) {
                    let ds_phieuvt = await this.LAY_DS_PHIEUVT_HDBH(
                        this.PHIEU_ID,
                        this.baohong_id,
                        0,
                        0,
                        0
                    );
                    if (ds_phieuvt.length > 0) {
                        this.$toast.error(
                            "Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ"
                        );
                        return;
                    }
                }
                this.chuyento_bhId = this.baohong_id;
                this.chuyento_ttbhId = this.ttbh_id;
                this.$refs.popupChuyenTo.openDialog(this.PHIEU_ID, 0);
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async closeChuyenTo() {
            await this.HienThiDanhSach();
        },
        async tsbtnKQ_DoKiem_Click() {
            if (!this.visible.tsbtnKQ_DoKiem) return;
            if (this.baohong_id != null && this.baohong_id != 0) {
                this.dokiem_baohong_id = this.baohong_id;
                this.$refs.doKiem.showModal();
            } else {
                this.$toast.error("Bạn chưa chọn phiếu!");
            }
        },
        tsbtnGianDo_Click() {
            if (this.IS_USING_CABMAN_V2) {
                // let thongTinDauNoi = {
                //     thueBaoId: this.thuebao_id,
                //     kieu: 0,
                // };
                // this.$refs.daunoithuebao.setData(thongTinDauNoi);
                // this.$refs.daunoithuebao.show();
                this.$bvModal.show('dandocap');
            } else {
                this.$refs.dlg_tracuumdf.show();
                this.$refs.ppTraCuuMDF.frmTraCuuMDF_Load(this.txtMaTB.trim());
            }
        },
        async tsbtnChuyenTTDH_Click() {
            if (!this.visible.tsbtnChuyenTTDH || !this.enable.tsbtnChuyenTTDH) return;
            try {
                if (this.baohong_id == 0 || this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có dữ liệu để thực hiện!");
                    return;
                }
                if (this.kqxl_id == null) {
                    this.$toast.error("Hãy chọn kết quả xử lý");
                    return;
                }
                if (this.ds_lydoton_id.length == 0) {
                    this.$toast.error("Hãy chọn lý do tồn");
                    return;
                }
                this.loading(true);
                this.ds_tt = [];
                await this.TaoDuLieu_nguyennhan(true);
                this.TaoDuLieu_baoton(true);
                let ds_param = {
                    baohong_id: this.baohong_id,
                    phieu_id: this.PHIEU_ID,
                    nhanvien_id: Number(this.$root.token.getNhanVienID()),
                    ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                    da_nhap_nd: 0,
                    kieugoi_id: this.kieugoi_id,
                    thuebao_id: this.thuebao_id,
                    kqxl_id: Number(this.kqxl_id),
                    ma_tb: this.vma_tb,
                    ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                    ghichu_th: this.txtGhiChuTH,
                    donvi_id: Number(this.$root.token.getDonViID()),
                    ma_nd: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ip: "",
                    ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                    nhomton_id: this.nhomton_id,
                    sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                    ds_trangthai_bh: JSON.stringify(this.ds_tt),
                    ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                    ds_ton_bh: JSON.stringify(this.ds_ton),
                    huonggiao_id: this.huonggiao_id,
                    noidung_giao: "",
                };
                let kqnt = await this.sp_ntbh_chuyenttdh(ds_param);
                try {
                    let result = JSON.parse(kqnt.js);
                    if (result.ERROR_CODE == 0) {
                        if (result.MESSAGE != undefined && result.MESSAGE != "") {
                            this.$toast.error(result.MESSAGE);
                        }
                        return;
                    }
                    this.$refs.ppNoiDungGiao.init();
                    this.$refs.dlg_ndgiao.show();
                } catch (e) {
                    this.$toast.error(kqnt);
                }
            } catch (ex) {
                this.$toast.error(ex.message);
                console.log(ex);
            } finally {
                this.loading(false);
            }
        },
        async dlg_ndgiao_giao_phieu(nd_giao) {
            try {
                this.loading(true);
                this.ds_tt = [];
                await this.TaoDuLieu_nguyennhan(true);
                this.TaoDuLieu_baoton(true);
                let ds_param = {
                    baohong_id: this.baohong_id,
                    phieu_id: this.PHIEU_ID,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                    da_nhap_nd: 1,
                    kieugoi_id: this.kieugoi_id,
                    thuebao_id: this.thuebao_id,
                    kqxl_id: this.kqxl_id,
                    ma_tb: this.vma_tb,
                    ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                    ghichu_th: this.txtGhiChuTH,
                    donvi_id: this.$root.token.getDonViID(),
                    ma_nd: this.$root.token.getUserName(),
                    may_cn: this.$root.token.getNguoiDungID(),
                    ip: "",
                    ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                    nhomton_id: this.nhomton_id,
                    sonngay: this.visible.txtSoNgay ? Number(this.txtSoNgay) : 0,
                    ds_trangthai_bh: JSON.stringify(this.ds_tt),
                    ds_nguyennhan_bh: JSON.stringify(this.ds_nn),
                    ds_ton_bh: JSON.stringify(this.ds_ton),
                    huonggiao_id: this.huonggiao_id,
                    noidung_giao: nd_giao,
                };
                let kqnt = await this.sp_ntbh_chuyenttdh(ds_param);
                try {
                    let result = JSON.parse(kqnt.js);
                    if (result.ERROR_CODE != 1) {
                        if (result.MESSAGE != undefined && result.MESSAGE != "") {
                            this.$toast.error(result.MESSAGE);
                        }
                    } else {
                        this.$toast.success("Cập nhật dữ liệu thành công!");
                    }
                } catch (e) {
                    this.$toast.error(kqnt);
                }
            } catch (e) {
                console.log(e);
                this.$toast.error(e);
            } finally {
                this.$refs.dlg_ndgiao.hide();
                this.loading(false);
            }
        },
        async sp_ntbh_chuyenttdh(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_para: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_044_fn_ntbh_chuyenttdh(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        setsize() {
            if (Number(this.nhomnn_id) == 21 && Number(this.dichvuvt_id) == 4) {
                this.visible.txtHangSx = true;
            } else {
                this.visible.txtHangSx = false;
            }
        },
        async tsbtnVatTuMoi_Click() {
            if (this.PHIEU_ID != 0) {
                this.modelCapVatTu.phieu_id = this.PHIEU_ID;
                this.modelCapVatTu.loaihd_id = 0;
                this.modelCapVatTu.thuebao_id = this.thuebao_id;
                this.modelCapVatTu.loaitb_id = this.LOAITB_ID;
                this.modelCapVatTu.baohong_id = this.baohong_id;
                this.$refs.popupVatTuTB.openDialog();
            } else {
                this.$toast.error("Bạn chưa chọn phiếu!");
            }
        },
        tsbtnVatTu_Click() {
            if (this.PHIEU_ID != 0) {
                this.$refs.dlg_vattubaohong.show();
                this.$refs.ppVatTuBaoHong.frmVatTuThueBao_Load();
            } else {
                this.$toast.error("Bạn chưa chọn phiếu!");
            }
        },
        async tsbtnThayDoiTT_Click() {
            try {
                if (this.baohong_id == 0 || this.ds_phieubaohong.length <= 0) {
                    this.$toast.error("Không có dữ liệu để thực hiện !");
                    return;
                }
                this.$refs.popupThayDoiTTBH.openDialog();
            } catch (ex) {
                this.$toast.error("Lỗi: " + ex);
            }
        },
        async tsbtnLichSuHen_Click() {
            try {
                if (this.baohong_id == -1 || this.baohong_id == 0) return;
                this.$refs.popupLichSu_HenBH.openDialog();
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async KIEMTRA_PHIEU(ds_para, phieu_id, kieu) {
            let kqnt = null;
            try {
                let params = {
                    kieu: kieu,
                    phieu_id: phieu_id,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                    ds_para: JSON.stringify(ds_para),
                };
                let response = await api.ur_41003_fn_ntbh_kiemtra_phieu(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        tsbtnKhaoSat_Click() {
            if (this.baohong_id > 0) {
                this.$refs.dlg_khaosatkh.show();
                this.$refs.ppKhaoSatKH.init(this.baohong_id, null, null, null);
            }
        },
        async tsbtnHoanCong_Click() {
            let ds_param = {
                baohong_id: this.baohong_id,
                phieu_id: this.PHIEU_ID,
                nghiemthu: 0,
                kt_tsl_tram_tg: this.PHAILAM("KT_TSL_TRAM_TG") ? "1" : "0",
                dichvuvt_id: this.dichvuvt_id,
                hdtb_id: 0,
                thuebao_id: this.thuebao_id,
                daucuoi_id: 0,
                bo_qua_dokiem: this.PHAILAM("BO_QUA_DOKIEM") ? "1" : "0",
                nguoi_cn: this.$root.token.getUserName(),
                may_cn: this.$root.token.getNguoiDungID(),
                ip_cn: "",
                nhanvien_id: this.$root.token.getNhanVienID(),
                att_adsl: this.ttnd_att_adsl,
                att_gpon: this.ttnd_att_gpon,
                snr_adsl: this.ttnd_snr_adsl,
                kqxl_id: this.kqxl_id,
                ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
                ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                kieugoi_id: this.kieugoi_id,
                huonggiao_id: this.huonggiao_id,
                ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                ma_nd: this.$root.token.getUserName(),
            };
            try {
                this.loading(true)
                let kqnt = await this.fn_ntbh_hoancong(ds_param)
                try {
                    let kt = JSON.parse(kqnt.js)
                    if (kt.ERROR_CODE != 1) {
                        this.$toast.error(kt.MESSAGE)
                        return
                    }
                } catch (e) {
                    this.$toast.error(kqnt)
                    return
                }
            } finally {
                this.loading(false)
            }
                  
            this.$bvModal.msgBoxConfirm('Bạn thật sự muốn cập nhật kết quả xử lý ?', {
                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
            }).then(async v => {
                if (v) {
                    try {
                        this.loading(true)
                        ds_param.nghiemthu = 1
                        let kqnt = await this.fn_ntbh_hoancong(ds_param)
                        if (kqnt.result != 1) {
                            try {
                                let kt = JSON.parse(kqnt.js)
                                if (kt.ERROR_CODE == 0) {
                                    this.$toast.error(kt.MESSAGE)
                                    return
                                }
                            } catch (e) {
                                this.$toast.error(kqnt)
                                return
                            }
                        }
                        
                        this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
                        await this.HienThiDanhSach()
                    } catch(error) {
                        this.$toast.error(error.message ? error.message : 'Đã có lỗi xảy ra')
                    } finally {
                        this.loading(false)
                    }
                }
            }); 
        },
        async tsbtnHoanCongOMC_Click() {
            let ds_param = {
                baohong_id: this.baohong_id,
                phieu_id: this.PHIEU_ID,
                nghiemthu: 0,
                kt_tsl_tram_tg: this.PHAILAM("KT_TSL_TRAM_TG") ? "1" : "0",
                dichvuvt_id: this.dichvuvt_id,
                hdtb_id: 0,
                thuebao_id: this.thuebao_id,
                daucuoi_id: 0,
                bo_qua_dokiem: this.PHAILAM("BO_QUA_DOKIEM") ? "1" : "0",
                nguoi_cn: this.$root.token.getUserName(),
                may_cn: this.$root.token.getNguoiDungID(),
                ip_cn: "",
                nhanvien_id: this.$root.token.getNhanVienID(),
                att_adsl: this.ttnd_att_adsl,
                att_gpon: this.ttnd_att_gpon,
                snr_adsl: this.ttnd_snr_adsl,
                kqxl_id: this.kqxl_id,
                ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
                ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                kieugoi_id: this.kieugoi_id,
                huonggiao_id: this.huonggiao_id,
                ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                ma_nd: this.$root.token.getUserName(),
            };
            console.log(ds_param);
            // P1: nghiemthu = 0
            try {
                this.loading(true);
                let kqnt = await this.fn_ntbh_hoancong_OMC(ds_param);
                try {
                    let kt = JSON.parse(kqnt.js);
                    if (kt.ERROR_CODE != 1) {
                        this.$toast.error(kt.MESSAGE);
                        return;
                    }
                } catch (e) {
                    this.$toast.error(kqnt);
                    return;
                }
            } finally {
                this.loading(false);
            }
            this.$bvModal
                .msgBoxConfirm("Bạn thật sự muốn cập nhật kết quả xử lý ?", {
                    title: "",
                    size: "sm",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                })
                .then(async (v) => {
                    if (v) {
                        try {
                            this.loading(true);
                            ds_param.nghiemthu = 1;
                            let kqnt = await this.fn_ntbh_hoancong_OMC(ds_param);
                            if (kqnt.result != 1) {
                                try {
                                    let kt = JSON.parse(kqnt.js);
                                    if (kt.ERROR_CODE == 0) {
                                        this.$toast.error(kt.MESSAGE);
                                        return;
                                    }
                                } catch (e) {
                                    this.$toast.error(kqnt);
                                    return;
                                }
                            }
                            this.$toast.success(
                                "Cập nhật dữ liệu lên viễn thông tỉnh thành công!"
                            );
                            await this.HienThiDanhSach();
                        } catch (error) {
                            this.$toast.error(
                                error.message ? error.message : "Đã có lỗi xảy ra"
                            );
                        } finally {
                            this.loading(false);
                        }
                    }
                });
        },
        async tsbtnHoanCongNET_Click() {
            let ds_param = {
                baohong_id: this.baohong_id,
                phieu_id: this.PHIEU_ID,
                nghiemthu: 0,
                kt_tsl_tram_tg: this.PHAILAM("KT_TSL_TRAM_TG") ? "1" : "0",
                dichvuvt_id: this.dichvuvt_id,
                hdtb_id: 0,
                thuebao_id: this.thuebao_id,
                daucuoi_id: 0,
                bo_qua_dokiem: this.PHAILAM("BO_QUA_DOKIEM") ? "1" : "0",
                nguoi_cn: this.$root.token.getUserName(),
                may_cn: this.$root.token.getNguoiDungID(),
                ip_cn: "",
                nhanvien_id: this.$root.token.getNhanVienID(),
                att_adsl: this.ttnd_att_adsl,
                att_gpon: this.ttnd_att_gpon,
                snr_adsl: this.ttnd_snr_adsl,
                kqxl_id: this.kqxl_id,
                ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
                ds_para_ktr_phieu: JSON.stringify(this.ds_phieubaohong_selected),
                kieugoi_id: this.kieugoi_id,
                huonggiao_id: this.huonggiao_id,
                ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                ma_nd: this.$root.token.getUserName(),
            };
            console.log(ds_param);
            // P1: nghiemthu = 0
            try {
                this.loading(true);
                let kqnt = await this.fn_ntbh_hoancong_NET(ds_param);
                try {
                    let kt = JSON.parse(kqnt.js);
                    if (kt.ERROR_CODE != 1) {
                        this.$toast.error(kt.MESSAGE);
                        return;
                    }
                } catch (e) {
                    this.$toast.error(kqnt);
                    return;
                }
            } finally {
                this.loading(false);
            }
            this.$bvModal
                .msgBoxConfirm("Bạn thật sự muốn cập nhật kết quả xử lý ?", {
                    title: "",
                    size: "sm",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                })
                .then(async (v) => {
                    if (v) {
                        try {
                            this.loading(true);
                            ds_param.nghiemthu = 1;
                            let kqnt = await this.fn_ntbh_hoancong_NET(ds_param);
                            if (kqnt.result != 1) {
                                try {
                                    let kt = JSON.parse(kqnt.js);
                                    if (kt.ERROR_CODE == 0) {
                                        this.$toast.error(kt.MESSAGE);
                                        return;
                                    }
                                } catch (e) {
                                    this.$toast.error(kqnt);
                                    return;
                                }
                            }
                            this.$toast.success(
                                "Cập nhật dữ liệu lên viễn thông tỉnh thành công!"
                            );
                            await this.HienThiDanhSach();
                        } catch (error) {
                            this.$toast.error(
                                error.message ? error.message : "Đã có lỗi xảy ra"
                            );
                        } finally {
                            this.loading(false);
                        }
                    }
                });
        },
        async fn_ntbh_hoancong(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_057_fn_ntbh_hoancong(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async fn_ntbh_hoancong_OMC(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.fn_ntbh_hoancong_omc(this.axios, params);
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async fn_ntbh_hoancong_NET(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.fn_ntbh_hoancong_net(this.axios, params);
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async fn_ntbh_hoanthanh(ds_param) {
            let kqnt = null;
            try {
                let params = {
                    ds_param: JSON.stringify(ds_param),
                };
                let response = await api.ur_41003_070_fn_ntbh_hoanthanh(
                    this.axios,
                    params
                );
                console.log(response);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data
                ) {
                    kqnt = response.data.data;
                } else {
                    kqnt = response.data.message;
                }
            } catch (e) {
                console.log(e);
                kqnt = e.data && e.data.message ? e.data.message : e.message;
            }
            return kqnt;
        },
        async tsbtnHoanThanh_Click() {
            let ds_param = {
                kieugoi_id: this.kieugoi_id,
                phieu_id: this.PHIEU_ID,
                baohong_id: this.baohong_id,
                dichvuvt_id: this.dichvuvt_id,
                thuebao_id: this.thuebao_id,
                nguoi_cn: this.$root.token.getUserName(),
                may_cn: this.$root.token.getNguoiDungID(),
                ip_cn: "",
                nhanvien_id: this.$root.token.getNhanVienID(),
                ngay_ht: this.dtpNgayTH,
                ngay_cn: new Date().toISOString().slice(0, 10).replace(/-/g, ""),
                ma_nd: this.$root.token.getUserName(),
                ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
                bo_qua_dokiem: this.PHAILAM("BO_QUA_DOKIEM") ? "1" : "0",
                att_adsl: this.ttnd_att_adsl,
                att_gpon: this.ttnd_att_gpon,
                snr_adsl: this.ttnd_snr_adsl,
                kqxl_id: this.kqxl_id,
                kt_tsl_tram_tg: this.PHAILAM("KT_TSL_TRAM_TG") ? "1" : "0",
                capnhat: 0,
            };
            // P1: nghiemthu = 0
            let kqnt = await this.fn_ntbh_hoanthanh(ds_param);
            if (kqnt.result == 1) {
                this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
                return;
            }
            try {
                let kt = JSON.parse(kqnt.js);
                if (
                    kt.MESSAGE != undefined &&
                    kt.MESSAGE == "Bạn thật sự muốn cập nhật kết quả xử lý ?"
                ) {
                    this.$bvModal
                        .msgBoxConfirm("Bạn thật sự muốn cập nhật kết quả xử lý ?", {
                            title: "",
                            size: "sm",
                            okTitle: "Đồng ý",
                            cancelTitle: "Hủy",
                        })
                        .then(async (v) => {
                            if (v) {
                                try {
                                    this.loading(true);
                                    ds_param.capnhat = 1;
                                    let kqnt = await this.fn_ntbh_hoanthanh(ds_param);
                                    if (kqnt.result == 1) {
                                        this.$toast.success(
                                            "Cập nhật dữ liệu lên viễn thông tỉnh thành công!"
                                        );
                                    } else {
                                        try {
                                            let kt = JSON.parse(kqnt.js);
                                            this.$toast.error(kt.MESSAGE);
                                        } catch (e) {
                                            this.$toast.error(kqnt);
                                            return;
                                        }
                                    }
                                } catch (error) {
                                    this.$toast.error(
                                        error.message ? error.message : "Đã có lỗi xảy ra"
                                    );
                                } finally {
                                    this.loading(false);
                                }
                            }
                        });
                }
            } catch (e) {
                this.$toast.error(kqnt);
                return;
            }
        },
        async tsddbIn_Click() {
            try {
                if (!this.ds_phieubaohong.length) {
                    this.$toast.warning(
                        "Chưa có danh sach thuê bao. Bạn hãy kiểm tra lại! "
                    );
                    return;
                }
                this.inBB.n_phieu_id = "";
                this.inBB.n_baohong_id = "";
                let isConfirm = await this.$bvModal.msgBoxConfirm(
                    `Bạn có muốn in các phiếu thi công đã chọn không ?`, {
                        title: "Thông báo",
                        size: "sm",
                        okTitle: "Đồng ý",
                        cancelTitle: "Hủy",
                    }
                );
                if (isConfirm) {
                    for (const item of this.ds_phieubaohong_selected) {
                        this.inBB.n_phieu_id += "," + item.phieu_id;
                        this.inBB.n_baohong_id += "," + item.baohong_id;
                    }
                    if (this.inBB.n_phieu_id == "") {
                        this.$toast.warning("Chưa có phiếu nào được chọn");
                        return;
                    } else {
                        this.inBB.n_phieu_id = this.inBB.n_phieu_id.substring(1);
                        this.inBB.n_baohong_id = this.inBB.n_baohong_id.substring(1);
                    }
                } else {
                    this.inBB.n_phieu_id = this.PHIEU_ID.toString();
                    this.inBB.n_baohong_id = this.baohong_id.toString();
                }
                if (this.inBB.n_phieu_id && this.inBB.n_baohong_id) {
                    this.inBB.baohong = true;
                    this.inBB.huonggiao_id = this.huonggiao_id;
                    this.inBB.nvth_id = this.nhanvien_th_id;
                    this.$refs.popupInBB.showModal();
                }
            } catch (error) {
                this.$toast.error("Có lỗi khi in phiếu: " + error.message);
            }
        },
        tsbtnNhacViec_Click() {
            this.$refs.nhacViecBH.showDialog();
        },
        gridviewDanhSach_RowCellStyle(arg) {
            const { data, column } = arg;
            const rgb = (color) => {
                return `rgb(${color})`;
            };
            // From file design
            if (column.field == "gio_conlai") {
                if (data.gio_conlai <= 0.5) {
                    arg.cell.style.backgroundColor = "Red";
                }
            }
            if (column.field == "goi_kt") {
                if (data.mau_ht) {
                    let mau_ht = data.mau_ht.split(",") || [];
                    if (mau_ht.length == 3) {
                        arg.cell.style.backgroundColor = rgb(
                            mau_ht[0],
                            mau_ht[1],
                            mau_ht[2]
                        );
                    } else {
                        arg.cell.style.backgroundColor = rgb(255, 255, 255);
                    }
                }
            }
             if (column.field == "mucdo") {
                if (data.mau_ht_mucdo) {
                    let mau_ht = data.mau_ht_mucdo.split(",") || [];
                    if (mau_ht.length == 3) {
                        arg.cell.style.backgroundColor = rgb(
                            mau_ht[0],
                            mau_ht[1],
                            mau_ht[2]
                        );
                    } else {
                        arg.cell.style.backgroundColor = rgb(255, 255, 255);
                    }
                }
            }
            //nhapt Thêm màu cho phân loại khách hàng TEN_PLKH : 09/10/2017 (Update_date: Jun 17th 2022)
            if (column.field == "ten_plkh") {
                if (data.mau_htpl) {
                    let mau_ht_pl = data.mau_htpl.split(",") || [];
                    if (mau_ht_pl.length == 3) {
                        arg.cell.style.backgroundColor = rgb(
                            mau_ht_pl[0],
                            mau_ht_pl[1],
                            mau_ht_pl[2]
                        );
                    } else {
                        arg.cell.style.backgroundColor = rgb(255, 255, 255);
                    }
                }
            }
            if (column.field == "ma_tb") {
                if (data.hasOwnProperty("kenhtn_id")) {
                    const kenhtn = data.kenhtn_id;
                    if (kenhtn) {
                        if (
                            kenhtn == KENHTN_BAOHONG.BH_GPON_SUYHAO_CAO ||
                            kenhtn == KENHTN_BAOHONG.BH_LOICAP_QUANG
                        ) {
                            arg.cell.style.backgroundColor = "RoyalBlue";
                        } else {
                            arg.cell.style.backgroundColor = rgb(255, 255, 255);
                            arg.cell.style.color = "Black";
                        }
                        const GIO_CONLAI = data.gio_conlai;
                        if (
                            kenhtn != KENHTN_BAOHONG.BH_GPON_SUYHAO_CAO &&
                            kenhtn != KENHTN_BAOHONG.BH_XUAT_THEO_FILE
                        ) {
                            if (GIO_CONLAI < 0) {
                                arg.cell.style.backgroundColor = "Red";
                            } else if (GIO_CONLAI < 10) {
                                arg.cell.style.backgroundColor = "Aqua";
                            }
                        }
                    }
                }
            }
            if (column.field == "ten_tb") {
                if (data.hasOwnProperty("bh_lai")) {
                    const bh_lai = data.bh_lai;
                    if (bh_lai == 1) arg.cell.style.backgroundColor = rgb(0, 191, 255);
                    else {
                        arg.cell.style.backgroundColor = rgb(255, 255, 255);
                        arg.cell.style.color = "Black";
                    }
                }
            }
        },
        async checkPortOnClick() {
            this.checkPortDialog.isVisiable = true
            this.$refs.checkPort.show()
        },
        async checkPortOnClose() {
            this.checkPortDialog.isVisiable = false
        },
    },
};
</script>
<style>
.non-ative a {
    color: #d3d3d3 !important;
}
.non-ativea {
    color: #d3d3d3 !important;
}
.hoso-dauchuyen-daunoi-thuebao .page-content {
    overflow: visible !important;
    height: fit-content;
    background-color: white;
}
#boxLeft, #boxRight {
    height: 96%
}
</style>