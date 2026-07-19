<template src="./TransferRightsCrossSell.html"></template>
<style src="./TransferRightsCrossSell.scss"></style>
<script>
import api from "./TransferRightsCrossSell.js";
import api_cco from "../ChangeContractOwner/Api.js";
import api_ins from "../InstallNewSubs/InstallNewSubsAPI.js";
import breadcrumb from '@/components/breadcrumb'
import moment from "moment";
import { DichVuVienThong, LoaiHopDong, KHOANMUC_TT, LOAI_DV, LoaiHinhTB, HinhThucThanhToan, KieuLapDat, TrangThaiCaiDat, KIEU_KH, PHANLOAI_KH, Loai_NV, TRANGBI,
            TrangThaiTB, TrangThaiHD } from './Enums.js'
import PopupTimKiemNhanVienThuCuoc from '@/modules/contract/setup/InstallNewSubs/Popups/PopupTimKiemNhanVienThuCuoc'
import DSTBCungMa from '@/modules/contract/setup/ChangeMultiServicePackage/popups/DSTBCungMa/DSTBCungMa'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import ChonDonViModal from '@/modules/contract/setup/SurveyRequest/popups/ChonDonVi/ChonDonViModal.vue'
import InHopDong from '@/modules/print/InHopDong/InHopDong.vue'
import ThongTinEmail from "../ChangeContractOwner/popup/ThongTinEmail.vue"
import ThongTinLienHe from "../ChangeContractOwner/popup/ThongTinLienHe.vue"
// import CongTacVien from "../ChangeContractOwner/popup/CongTacVien.vue"
// import NguoiGioiThieu from "../ChangeContractOwner/popup/NguoiGioiThieu.vue" 
import frmDiaChiBC from "./popups/frmDiaChiBC/frmDiaChiBC.vue"
import ThongSoCoDinh from "./popups/ThongSoCoDinh/ThongSoCoDinh.vue"
import DangKyDVGTBC from "./popups/DangKyDVGTBC/DangKyDVGTBC.vue"
import frmTC_DBTB_KH from "./popups/frmTC_DBTB_KH/frmTC_DBTB_KH.vue"
// import ThanhToan from '@/modules/contract/setup/Payment/Payment.vue'
const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')
import ChonMaInGhep from './popups/popupChonMaInGhep.vue'

export default {
    name:'TransferRightsCrossSell',
    components:{
        breadcrumb,
        PopupTimKiemNhanVienThuCuoc,
        DSTBCungMa,
        ModalChonDiaChi,
        SearchContractModal,
        ChonDonViModal,
        InHopDong,
        ThongTinEmail,
        ThongTinLienHe,
        // CongTacVien,
        // NguoiGioiThieu,
        frmDiaChiBC,
        ThongSoCoDinh,
        DangKyDVGTBC,
        frmTC_DBTB_KH,
        // ThanhToan,
        ChonMaInGhep
    },
    data() {
        return {
            header: {
                title: 'CHUYỂN QUYỀN SỬ DỤNG',
                list: [
                    { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
                    { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
                ]
            },
            targetTop: '#app .main-wrapper',
            position: { X: 'center', Y: 'top' },
            Kieuld_click: false,
            khachhang_tc_id: null,
            kt_load: true,
            dichvuvt_id: 0,
            loaitb_id: null,
            httt_id: 0,
            hdkh_id: 0,
            hdkh_cha_id: 0,
            hdtt_id: 0,
            hdtb_id: 0,
            khachhang_id: 0,
            khachhang_id_nm: 0,
            khachhang_id_cq: 0,
            ma_gd: "",
            TBHD_PL: 1,
            ma_tt_cu: "",
            ten_tt_cu: "",
            diachi_tt_cu: "",
            ma_tb_cu: "",
            ten_tb_cu: "",
            check_tb_dd: false,
            donvi_ql: null,
            ma_kh_cu: "",
            strError_VinaPhone: "",
            clear_manvtc: "0",
            trangbi_id: 3,
            in_pl: 0,
            ds_isdn: [],
            matb_isdn_chinh: "",
            dtien_dv: 0,
            dvat_dv: 0,
            dtien_cq: 0,
            dvat_cq: 0,

            ds_tien_khoanmuc: [],
            thanhtoan_id: 0,
            thuebao_id: null,
            thuebao_cha_id: null,
            tuyenthu_id: 0,
            matb_dd: "",
            ma_tb: "",
            nhanvien_ql_am_id: 0,

            // -- dia chi lap dat
            diachild_id: 0,
            tinhld_id: 0,
            quanld_id: 0,
            phuongld_id: 0,
            phold_id: 0,
            apld_id: 0,
            khuld_id: 0,
            dacdiemld_id: 0,
            sonhald: "",
            blockld: "",
            tangld: "",
            sophongld: "",
            motadcld: "",

            // -- dia chi khach hang
            diachikh_id: 0,
            tinhkh_id: 0,
            quankh_id: 0,
            phuongkh_id: 0,
            phokh_id: 0,
            apkh_id: 0,
            khukh_id: 0,
            dacdiemkh_id: 0,
            sonhakh: "",

            // -- dia chi nguoi dai dien
            tinhndd_id: 0,
            quanndd_id: 0,
            phuongndd_id: 0,
            phondd_id: 0,
            apndd_id: 0,
            khundd_id: 0,
            dacdiemndd_id: 0,
            sonhandd: "",

            // -- dia chi chung tu
            tinhct_id: 0,
            quanct_id: 0,
            phuongct_id: 0,
            phoct_id: 0,
            apct_id: 0,
            khuct_id: 0,
            dacdiemct_id: 0,
            sonhact: "",

            // -- dia chi thanh toan
            diachitt_id: 0,
            tinhtt_id: 0,
            quantt_id: 0,
            phuongtt_id: 0,
            phott_id: 0,
            aptt_id: 0,
            khutt_id: 0,
            dacdiemtt_id: 0,
            sonhatt: "",

            // -- dia chi thue bao
            diachitb_id: 0,
            tinhtb_id: 0,
            quantb_id: 0,
            phuongtb_id: 0,
            photb_id: 0,
            aptb_id: 0,
            khutb_id: 0,
            dacdiemtb_id: 0,
            sonhatb: "",
            blocktb: "",
            tangtb: "",
            sophongtb: "",
            motadctb: "",

            // -- dia chi BC
            diachibc_id: 0,
            tinhbc_id: 0,
            quanbc_id: 0,
            phuongbc_id: 0,
            phobc_id: 0,
            apbc_id: 0,
            khubc_id: 0,
            dacdiembc_id: 0,
            sonhabc: "",
            //        

            tinhdau_id: null,
            diachidau_id: null,
            phodau_id: null,
            ngodau_id: null,
            phuongdau_id: null,
            quandau_id: null,
            sonhadau: null,
            donvi_dau_id: null,

            tinhcuoi_id: null,
            diachicuoi_id: null,
            phocuoi_id: null,
            ngocuoi_id: null,
            phuongcuoi_id: null,
            quancuoi_id: null,
            sonhacuoi: null,
            donvi_cuoi_id: null,
            status: "",
            linhvuc_id: 0,
            ma_tinh: null,

            //TuấnNA thêm khai báo cho nút ngân sách nhà nước --- Ngày 12/11/2010
            DV_QHNS: "",
            MACHUONG: "",
            MN_KT: "",
            MA_NDKT: "",
            MN_NSNN: "",
            TIEN_DM: null,

            //Hiếu bổ sung phần khai báo cho hàm laytien         
            loaihd_id: null,
            kieuld_id: null,
            doituong_id: null,
            ngaylhd: "",

            //TuấnNA thêm code khai báo các biến tiểu ngành
            TNC1_ID: 0,
            TNC2_ID: 0,
            TNC3_ID: 0,
            TNC4_ID: 0,
            nganhnghe_id: 0,
            doituong_id_bd: 0,
            mucuoctb_id: 0,
            tocdo_id: 0,
            ltb_adsl_id: 1, //Default
            kh_cd: 0,
            ltb_adsl_cu_id: 0,
            ltb_adsl_moi_id: 0,
            tocdo_moi_id: 0,
            tocdo_cu_id: 0,

            //Hiếu bổ sung phần khai báo cho hàm laytien --14/09/2010
            //TuấnNA bổ sung thêm biến sử dụng trong hàm lấy tiền ld ---Ngày 15/09/2010
            tien_tb: 0,
            vat_tb: 0,
            tien_dv: 0,
            vat_dv: 0,
            tien_kmld: 0,
            tien_kmtb: 0,
            DS_ID_DV: "",//Lưu danh sách ID dịch vụ gia tăng--Hiếu 16/09/2010
            DS_ID_TB: "",//Lưu danh sách ID mua thiết bị - Hiếu 16/09/2010

            nhanvientc_id: 0,
            quytrinh_id: 0,
            quytrinh_tc_tn: 0,
            huonggiao_tc_tn: 0,

            hdmig_id: 0,
            mig_id: 0,
            kyhd: "",

            tinh: {
                tinhthicong_id: 0
            },
            thuebao_tc_id: null,
            m_dsThueBao_DichVu: {
                dsdvgt_ban: [],
                dsdvgt_tc: []
            },

            // gia tri tren form
            ngay_sys: moment(new Date()).format("DD/MM/YYYY"),
            enable: {
                tsbtnNhapMoi: true,
                tsbtnGhiLai: true,
                tsbtnXoa: true,
                tsbtnHuyBo: true,
                tsbtnThemTB: true,
                tsddbIn: true,
                tsbtnThanhToan: true,
                tsbtnGiaoPhieu: true,
                tsbtnThemTB_DS: true,
                tsbtnXoaTB: true,
                tsbtnXoaTB_DS: true,
                tsbtnThemTT: false,
                tsbtnXoaTT: false,
                btnDiaChiTB: true,
                cboNganHangTT: false,
                btnNSNN: true,
                cboMucCuoc: false,
                cboGoiCuocAdsl: false,
                txtEmailAdsl: false,
                btnEditDVGT: true,
                rdoGTNam: true,
                rdoGTNone: true,
                rdoGTNu: true,
                cboNganHangKH: false,
            },
            visible: {
                pnlAdsl: false,       
                pnlGphone: false,
                pnlIMS: false,
                pnlTSL: false,          
                pnlWan: false,
                pnlDD: false,
                pnlInt: false,
                txtPhanLoaiKH: false,
                cboKhachHangLon: true,         
            },
            model: {
                // 1
                txtMaDuAn: "",
                txtTongTienHD: "0",
                
                // tab 1 - 1 - 1
                txtMaGD: "",       
                dtpNgayYC: "",
                txtMaHDCu: "",
                dtpNgayLapHDCQ: "",
                cboDichVuVT: {
                    value: null,
                    list: [],
                },
                cboLoaihinhTB: {
                    value: null,
                    list: [],
                },
                cboKieuLD: {
                    value: null,
                    list: [],
                },

                // tab 1 - 1 - 2
                txtMaKHCu: "",
                cboMuccuocTBCu: {
                    value: null,
                    list: [],
                },
                txtTenTBCu: "",
                txtTenKHCu: "",
                txtDiaChiKHCu: "",
                txtMaTBCu: "",     

                // tab 1 - 2 - 1
                txtMaTB: "",
                dtpNgaySD: "",
                txtTenTB: "",
                txtNgaySinhTB: "",
                txtDiaChiTB: "",
                txtDiachiLD: "",
                cboDaiVT: {
                    value: null,
                    list: [],
                },
                cboTT: {
                    value: null,
                    list: [],
                },               
                cboDoituongTB: {
                    value: null,
                    list: [],
                },
                cboMucCuocTB: {
                    value: null,
                    list: [],
                },            
                txtGhiChuTB: "",
                
                // tab 1 - 2 - 2
                pnlDD: {
                    cboTrangBiDD: {
                        value: null,
                        list: [],
                    },
                    txtSimCard: "",
                    cboDTVinaPhone: {
                        value: null,
                        list: [],
                    },
                    cboGoiCuocDD: {
                        value: null,
                        list: [],
                    },
                },
                pnlAdsl: {
                    cboGoiCuocAdsl: {
                        value: null,
                        list: [],
                    },
                    cboMucCuoc: {
                        value: null,
                        list: [],
                    },
                    txtMaTB_TN: "",
                    cboTrangBiADSL: {
                        value: null,
                        list: [],
                    },
                    txtEmailAdsl: "",
                },
                pnlGphone: {
                    txtSosimGP: "",
                    chkVSVXGP: false,
                    cboTrangBiGP: {
                        value: null,
                        list: [],
                    },
                },
                pnlIMS: {
                    cboTrangBiIMS: {
                        value: null,
                        list: [],
                    },
                    cboThietBi: {
                        value: null,
                        list: [],
                    },
                },
                pnlTSL: {
                    txtMaHD_TTKD_TSL: "",
                    cboTocDoKenhTSL: {
                        value: null,
                        list: [],
                    },
                    cboLoaiKenhTSL: {
                        value: null,
                        list: [],
                    },
                    cboThoiHan: {
                        value: null,
                        list: [],
                    },                
                    cboTrangBi: {
                        value: null,
                        list: [],
                    },
                    cboChuQuan: {
                        value: null,
                        list: [],
                    },
                    cboLoaiTBTSL: {
                        value: null,
                        list: [],
                    },
                    txtDiaChiDau: "",
                    txtDiaChiCuoi: "",
                },
                pnlWan: {
                    txtMaHD_TTKD_MGW: "",
                    txtSiteID: "",
                    cboThoihanWan: {
                        value: null,
                        list: [],
                    },
                    cboTrangBiWan: {
                        value: null,
                        list: [],
                    },
                    cboLoaiKenhWan: {
                        value: null,
                        list: [],
                    },
                    cboLoaiTBiWan: {
                        value: null,
                        list: [],
                    },
                    cboTocDo: {
                        value: null,
                        list: [],
                    },
                    cboTocDo_PIR: {
                        value: null,
                        list: [],
                    },
                    cboChuQuanWan: {
                        value: null,
                        list: [],
                    },
                    cboLoaiModem: {
                        value: null,
                        list: [],
                    },
                },
                
                // tab 1 - 3 - 1
                lvDvgt: "",

                // tab 1 - 3 - 2
                lvDvkhac: "",
                txtTongVat: "",
                txtTongTien: "",
                lblTongTien: "",

                // tab 2 - 1 - 1
                txtMaKH: "",
                txtMaHD: "",
                dtpNgayLapHDNQ: "",
                txtTenKH: "",
                txtDiaChiKH: "",
                cboGiayToKH: {
                    value: null,
                    list: [],
                },
                txtSoGT: "",
                txtNgayCap: "",
                txtNoiCap: "",
                txtNgayHetHan: "",
                lblNgaySN: "Ngày sinh (*)",
                txtNgaySN: "",            
                rdoGT: "rdoGTNam",
                cboQuocTich: {
                    value: null,
                    list: [],
                },
                cboNganhNgheLoc: {
                    value: null,
                    list: [],
                },          
                cboDanToc: {
                    value: null,
                    list: [],
                },
                            
                // tab 2 - 1 - 2
                cboLoaiKH: {
                    value: null,
                    list: [],
                },            
                txtPhanLoaiKH: "",
                cboKhachHangLon: {
                    value: null,
                    list: [],
                },
                txtNguoiDD: "",
                txtChucDanhDD: "",
                txtDiaChiNDD: "",
                txtTelKH: "",
                txtFaxKH: "",
                txtHoKhau: "",
                txtNoiCapHK: "",
                txtNgayCapHK: "",
                txtMaSTKH: "",
                txtSoTKKH: "",
                chkNganHangKH : false,
                cboNganHangKH: {
                    value: null,
                    list: [],
                },
                txtDacTrung: "",
               
                // tab 2 - 2 - 1
                txtMaTT: "",
                cboMaDD: {
                    value: null,
                    list: [],
                },
                txtTenTT: "",
                txtDiaChiTT: "",         
                txtDiaChiCT: "",
                txtDiaChiBC: "", 
                
                // tab 2 - 2 - 2
                txtMaIG: "",
                cboDonviQL: {
                    value: null,
                    list: [],
                },
                cboHTTT: {
                    value: null,
                    list: [],
                },
                txtMaNVTC: "",
                txtSTK_TT: "",
                txtChu_TK: "",
                cboNganHangTT: {
                    value: null,
                    list: [],
                },
                txtMST_TT: "",
                txtGhiChuTT: "",
                txtNhanVienQL: "",
                txtMaTuyen: "",
                txtMaTBDD: "",
                
                // tab 2 - 3 - 2            
                lvDvkhac_TT: "",
                txtGhiChuKH: "",
            },

            thongso_ban: {},
            thongso_tc: {},

            ts_kt_mst: 0,
            DONVIQL_HTTT: 0,
            TUYENTHU_HTTT: 0,
            ts_sinhma_gd_theo_donvi: false,

            DsHopDongTB: {
                headers: [
                    { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 50, textAlign: 'center' },
                    { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: false,width:100 },
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false,width:100 },
                    { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false,width:100 },
                    { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowSorting: false,width:200 },
                    { fieldName: 'tong_tien', headerText: 'Tiền', allowFiltering: true, allowSorting: false,width:100 },
                    { fieldName: 'ten_dvvt', headerText: 'Dịch vụ VT', allowFiltering: true, allowSorting: false,width:100 },
                    { fieldName: 'manv_tc', headerText: 'Mã NVTC', allowFiltering: true, allowSorting: false,width:100 },
                    { fieldName: 'status', headerText: 'Trạng thái đồng bộ', allowFiltering: true, allowSorting: false,width:100 }
                ],
                data: [],
                selected: {},
                checked: []
            },

            DsHopDongTT: {
                headers: [
                    { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 50, textAlign: 'center' },
                    { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: false,width:100 },
                    { fieldName: 'ten_tt', headerText: 'Tên thanh toán', allowFiltering: true, allowSorting: false,width:100 },         
                    { fieldName: 'diachi_tt', headerText: 'Địa chỉ thanh toán', allowFiltering: true, allowSorting: false,width:100 },
                    { fieldName: 'diachi_ct', headerText: 'Địa chỉ chứng từ', allowFiltering: true, allowSorting: false,width:100 }
                ],
                data: [],
                selected: {},
                checked: []
            },

            diachiLD: {
                QUAN_ID: 0,
                PHUONG_ID: 0,
                PHO_ID: 0,
                AP_ID: 0,
                KHU_ID: 0,
                DACDIEM_ID: 0,
                NHANVIENTC_ID: 0,
                TUYENTHU_ID: 0,
                HTTT_ID: 0,
            },

            dsDK_DVGT: [],
            dsDK_DVK: [],

            popupComponent: null,
            popupComponentData: null,
            popupComponentAttr: {},
            popupComponentEvts: {
                'form-close':this.popupClosed,
                'acceptChangeCTV':this.popupClosed,
                'acceptChangeNGT':this.popupClosed,
                'PopUpCongTacVien_Thoat':this.popupClosed
            },
            popupComponentEvents: {
                'form-close':this.popupClosed,
                paymentcreated: this.paymentcreated
            },
            popupComponentName: '',
            txtMaGDTT: "",
            paymentloaded:false,
            userComponentModule: null,
            tieunganh: {
                dsTNC1: [],
                dsTNC2: [],
                dsTNC3: [],
                dsTNC4: [],
                TNC1_ID: 0,
                TNC2_ID: 0,
                TNC3_ID: 0,
                TNC4_ID: 0,
                TNCMOTA: '',
            },

            kieukh_canhan: false,
            PHANLOAIKH_ID: -1,
            eventNganhNghe: false,
            dsDBTBBC: [],

            diaChiKH: {    
                tinh_id: 0,
                quan_id: 0,
                phuong_id: 0,
                pho_id: 0,
                ap_id: 0,
                khu_id: 0,
                dacdiem_id: 0,
                sonha: null,
                diachi: null,
            },

            diaChiNDD: {    
                tinh_id: 0,
                quan_id: 0,
                phuong_id: 0,
                pho_id: 0,
                ap_id: 0,
                khu_id: 0,
                dacdiem_id: 0,
                sonha: null,
                diachi: null,
            },

            diaChiHK: {
                tinh_id: 0,
                quan_id: 0,
                phuong_id: 0,
                pho_id: 0,
                ap_id: 0,
                khu_id: 0,
                dacdiem_id: 0,
                sonha: null,
                diachi: null,
            },

            diaChiTT: {
                tinh_id: 0,
                quan_id: 0,
                phuong_id: 0,
                pho_id: 0,
                ap_id: 0,
                khu_id: 0,
                dacdiem_id: 0,
                sonha: null,
                diachi: null,
            },

            diaChiCT: {
                tinh_id: 0,
                quan_id: 0,
                phuong_id: 0,
                pho_id: 0,
                ap_id: 0,
                khu_id: 0,
                dacdiem_id: 0,
                sonha: null,
                diachi: null,
            },

            diaChiBC: {
                tinh_id: 0,
                quan_id: 0,
                phuong_id: 0,
                pho_id: 0,
                ap_id: 0,
                khu_id: 0,
                dacdiem_id: 0,
                sonha: null,
                diachi: null,
            },

            dsToThiCong:[],
            modelInHopDong: {},

            dsHDTT: [],
            dsDiaChi_BanCheo: [],
            dsDiaChi: [],
            dsDiaChiHDKH: [],
            dsDiaChiHDTT: [],
            dsDiaChiHDTB: [],
            dsHDTT_NV: [],

            dsHDTB: [],
            diaChiLD_BC: {},
            diaChiTB_BC: {},

            P_ACTION: 0,
            HDTB_DATA: [],
            HDKH_DATA: [],
            HDKHSUB_DATA: [],
            HDTT_DATA: [],
            DIACHI_HDKH_DATA: [],
            DIACHI_HDTT_DATA: [],
            DIACHI_HDTB_DATA: [],
            CHITIET_TIENHD_DATA: [],

            chkCTV: false,
            enableCTV: false,
            txtMaCTV: "",
            ctv_id: 0,
            chkNguoiGT: false,
            enableNGT: false,
            txtMaNGT: "",

            modalInGhep: {},
        }
    },
    mounted() {
        this.init()
    },
    computed: {
        listkieukh_tree:{
            get() {
                var listkieukh = [...new Set(this.model.cboLoaiKH.list.map(x=>x.kieu_kh))].map(x=>({ label:x, id:x })); 
                for (var kieukh of listkieukh) { 
                    kieukh.children = [...new Set(this.model.cboLoaiKH.list.filter(x=>x.kieu_kh==kieukh.label).map(x=>x.ten_nhom))].map(x=>({label:x,id:x})); 
                    for (var nhomkh of kieukh.children) { 
                        nhomkh.children = this.model.cboLoaiKH.list.filter(x=>x.kieu_kh==kieukh.label && x.ten_nhom==nhomkh.label).map(x=>({id:x.loaikh_id,label:x.ten_loaikh})); 
                    } 
                }  
                return listkieukh;
            }
        }
    },

    methods: {
        async init() {
            try {
                this.loading(true)
                this.eventNganhNghe = false
                await this.frmChuyenQuyenSDBC_Load()
                this.eventNganhNghe = true
            } catch (e) {
                console.log(e)
                this.$toast.error("Xảy ra lỗi: " + e)
            } finally {
                this.loading(false)
            }         
        },
        open_thanhToanPopUp() {
          this.loading(true)
        },
        async paymentcreated(val) {      
            this.paymentloaded = true
            this.$refs.thanhtoanDialog.show(true)
        },
        Reset_Thongso_Thuebao() {
            let ketqua = {
                trangbi_id: 0,
                muccuoc_id: 0,
                chuquan_id: 0,
                congnghe_id: 0,
                doituong_id: 0,
                kieuld_id: 0,
                kieutra_id: 0,
                loaikenh_id: 0,
                loaimd_id: 0,
                muccuoctb_id: 0,
                thietbidc_id: 0,
                thoihan_id: 0,
                tocdo_id: 0,
                tocdo_isp: 0,
                tocdo_pir_id: 0,
                tocdothuc_id: 0,
                trangbi_id: 0,
                ma_doicap: "",
                ma_tn: "",
            }
            return ketqua
        },
        async SetButton(kieu) {
            this.enable.tsbtnNhapMoi = false;
            this.enable.tsbtnGhiLai = false;
            this.enable.tsbtnXoa = false;
            this.enable.tsbtnHuyBo = false;
            this.enable.tsbtnThemTB = false;
            this.enable.tsddbIn = false;
            this.enable.tsbtnThanhToan = false;
            this.enable.tsbtnGiaoPhieu = false;
            this.enable.tsbtnThemTB_DS = false;

            if (kieu == -1) //Bat dau
            {
                $("#txtMaTB").focus()
                this.enable.tsbtnGhiLai = true;
                this.enable.tsbtnHuyBo = true;
                this.enable.tsbtnXoaTB = false;
                this.enable.tsbtnXoaTB_DS = false;
                this.model.cboMaDD.value = 0
                this.model.cboMaDD.list = []
                this.thongso_ban = this.Reset_Thongso_Thuebao();
                this.thongso_tc = this.Reset_Thongso_Thuebao();
            }

            if (kieu == 0) //Bat dau
            {
                $("#txtMaTB").focus()
                this.enable.tsbtnNhapMoi = true;
                this.enable.tsbtnThemTT = false;
                this.enable.tsbtnXoaTB = false;
                this.enable.tsbtnXoaTB_DS = false;
                await this.Clear();
            }

            if (kieu == 1)//Nhap moi
            {
                $("#txtMaTB").focus()
                this.enable.tsbtnGhiLai = true;
                this.enable.tsbtnHuyBo = true;
                this.enable.tsbtnThemTT = false;
                this.enable.tsbtnXoaTB = false;
                this.enable.tsbtnXoaTB_DS = false;
                await this.Clear();
            }

            if (kieu == 2)//Huy
            {
                $("#txtMaGD").focus()
                this.enable.tsbtnNhapMoi = true;
                this.enable.tsbtnXoa = true;
                await this.Clear();
            }

            if (kieu == 3)//Edit
            {
                $("#txtMaGD").focus()
                this.enable.tsbtnNhapMoi = true;
                this.enable.tsbtnXoa = true;
                this.enable.tsbtnGhiLai = true;
                this.enable.tsbtnHuyBo = true;

                if (this.DsHopDongTT.data.length > 1)
                    this.enable.tsbtnXoaTT = true;
                if (this.DsHopDongTB.data.length > 1) {
                    this.enable.tsbtnXoaTB = true;
                    this.enable.tsbtnXoaTB_DS = true;
                }
                this.enable.tsbtnThemTB = true;
                this.enable.tsbtnThemTB_DS = true;
                this.enable.tsddbIn = true;
                this.enable.tsbtnThanhToan = true;
                this.enable.tsbtnGiaoPhieu = true;
            }
        },
        async Clear() {
            try {
                this.model.txtNhanVienQL = "";
                this.nhanvien_ql_am_id = 0;

                this.model.pnlWan.txtMaHD_TTKD_MGW = "";
                this.model.pnlTSL.txtMaHD_TTKD_TSL = "";
                this.model.pnlWan.txtSiteID = "";
                this.model.cboMaDD.value = 0
                this.model.cboMaDD.list = []
                
                this.model.txtMaDuAn = "";
                this.model.dtpNgayYC = this.ngay_sys
                this.model.dtpNgayLapHDNQ = this.ngay_sys
                this.quytrinh_id = 0;
                this.trangbi_id = 3;
                this.model.txtTongTienHD = "";
                this.hdkh_id = 0;
                this.model.txtMaGD = "";
                this.model.txtMaHDCu = "";
                this.model.txtMaKH = "";
                this.khachhang_id = 0;
                this.model.txtTenKH = "";
                this.model.txtDiaChiKH = "";

                this.quankh_id = 0;
                this.phuongkh_id = 0;
                this.phokh_id = 0;
                this.apkh_id = 0;
                this.khukh_id = 0;
                this.dacdiemkh_id = 0;
                this.sonhakh = "";

                this.model.txtNguoiDD = "";
                this.model.txtChucDanhDD = "";
                this.model.txtTelKH = "";
                this.model.txtFaxKH = "";

                this.model.txtNgaySN = "";
                this.model.txtSoGT = "";
                this.model.txtNgayCap = "";
                this.model.txtNoiCap = "";
                this.model.cboGiayToKH.value = 0;
                this.model.txtDacTrung = "";
                this.model.cboTT.list = [];
                this.DsHopDongTB.data = []
                this.DsHopDongTB.selected = {}
                this.DsHopDongTT.data = []
                this.model.txtMaTuyen = "";

                this.model.rdoGT = "rdoGTNam";

                this.hdtt_id = 0;
                this.model.txtMaTT = "";
                this.model.txtTenTT = "";
                this.model.txtDiaChiTT = "";
                this.quantt_id = 0;
                this.phuongtt_id = 0;
                this.phott_id = 0;
                this.aptt_id = 0;
                this.khutt_id = 0;
                this.dacdiemtt_id = 0;
                this.sonhatt = "";

                this.model.txtMST_TT = "";
                this.model.txtSTK_TT = "";
                this.model.txtChu_TK = "";
                this.model.cboHTTT.value = 0;
                this.model.cboDonviQL.value = 0;
                this.model.cboNganHangTT.value = 0;

                this.model.txtDiaChiNDD = "";
                this.quanndd_id = 0;
                this.phuongndd_id = 0;
                this.phondd_id = 0;
                this.apndd_id = 0;
                this.khundd_id = 0;
                this.dacdiemndd_id = 0;
                this.sonhandd = "";

                this.model.txtDiaChiCT = "";

                //diachi bc
                this.model.txtDiaChiBC = "";
                this.tinhbc_id = 0;
                this.quanbc_id = 0;
                this.phuongbc_id = 0;
                this.phobc_id = 0;
                this.apbc_id = 0;
                this.khubc_id = 0;
                this.dacdiembc_id = 0;
                this.sonhabc = "";
                //

                this.hdtb_id = 0;
                this.thuebao_id = 0;
                this.thuebao_cha_id = 0;
                this.model.txtMaTB = "";
                this.model.txtTenTB = "";
                this.model.txtDiaChiTB = "";
                this.quantb_id = 0;
                this.phuongtb_id = 0;
                this.photb_id = 0;
                this.aptb_id = 0;
                this.khutb_id = 0;
                this.dacdiemtb_id = 0;
                this.sonhatb = "";

                this.dichvuvt_id = DichVuVienThong.CO_DINH;
                this.model.cboDichVuVT.value = this.dichvuvt_id;
                this.model.cboKieuLD.value = null;
                this.model.cboDoituongTB.value = this.model.cboDoituongTB.list.length > 0 ? this.model.cboDoituongTB.list[0].id : null
                this.tuyenthu_id = 0;
                this.nhanvientc_id = 0;

                this.model.txtTenKHCu = "";
                this.model.txtDiaChiKHCu = "";
                this.model.txtMaKHCu = "";
                this.model.txtTenTBCu = "";

                //Tuan them code trong ham Clear() -- Ngay 06/07/2010            
                this.model.cboLoaiKH.value = null;
                this.model.txtNoiCapHK = "";
                this.model.txtMaHD = "";
                this.model.lvDvkhac = ""
                this.model.lvDvgt = ""
                this.khachhang_id_cq = 0;
                this.khachhang_id_nm = 0;
                this.model.txtMaNVTC = "";
                this.model.txtDacTrung = "";
                this.model.txtChucDanhDD = "";
                //End tuan

                /// Tuấn thêm code clear các biến, khởi tạo lại giá trị ban đầu -- Ngày 29/07/2010
                this.quanld_id = 0;
                this.phuongld_id = 0;
                this.phold_id = 0;
                this.apld_id = 0;
                this.khuld_id = 0;
                this.dacdiemld_id = 0;
                this.sonhald = "";
                this.model.txtDiachiLD = "";
                this.model.txtNgaySinhTB = "";
                this.model.txtNgaySN = "";
                this.ma_kh_cu = "";
                this.ma_tb = "";
                this.ma_tt_cu = "";
                this.ma_tb_cu = "";
                this.ma_gd = "";
                this.model.cboDonviQL.value = 0;
                this.khachhang_id_nm = 0;
                this.khachhang_id_cq = 0;
                this.ma_gd = "";
                this.TBHD_PL = 1;
                this.ma_tt_cu = "";
                this.ten_tt_cu = "";
                this.diachi_tt_cu = "";
                this.ma_tb_cu = "";
                this.ten_tb_cu = "";
                this.check_tb_dd = false;
                this.donvi_ql = 0;
                this.ma_kh_cu = "";

                this.loaihd_id = 0;
                this.kieuld_id = 0;
                this.doituong_id = 0;
                this.ngaylhd = "";

                this.model.cboNganhNgheLoc.value = this.model.cboNganhNgheLoc.list.length > 0 ? this.model.cboNganhNgheLoc.list[0].id : null
                this.model.cboQuocTich.value = this.model.cboQuocTich.list.length > 0 ? this.model.cboQuocTich.list[0].id : null
                this.model.cboDanToc.value = this.model.cboDanToc.list.length > 0 ? this.model.cboDanToc.list[0].id : null

                /// Tuấn thêm code clear các biến, khởi tạo lại giá trị các biến thuê bao truyền số liệu -- Ngày 31/08/2010
                this.model.pnlTSL.cboTocDoKenhTSL.value = this.model.pnlTSL.cboTocDoKenhTSL.list.length > 0 ? this.model.pnlTSL.cboTocDoKenhTSL.list[0].id : null
                this.model.pnlTSL.cboLoaiKenhTSL.value = this.model.pnlTSL.cboLoaiKenhTSL.list.length > 0 ? this.model.pnlTSL.cboLoaiKenhTSL.list[0].id : null
                this.model.pnlTSL.cboLoaiTBTSL.value = this.model.pnlTSL.cboLoaiTBTSL.list.length > 0 ? this.model.pnlTSL.cboLoaiTBTSL.list[0].id : null
                this.model.pnlTSL.cboThoiHan.value = this.model.pnlTSL.cboThoiHan.list.length > 0 ? this.model.pnlTSL.cboThoiHan.list[0].id : null      
                this.model.pnlTSL.cboTrangBi.value = this.model.pnlTSL.cboTrangBi.list.length > 0 ? this.model.pnlTSL.cboTrangBi.list[0].id : null
                this.model.pnlTSL.cboChuQuan.value = this.model.pnlTSL.cboChuQuan.list.length > 0 ? this.model.pnlTSL.cboChuQuan.list[0].id : null

                this.model.pnlTSL.txtDiaChiDau = "";
                this.model.pnlTSL.txtDiaChiCuoi = "";

                this.donvi_dau_id = 0;
                this.diachidau_id = 0;
                this.phodau_id = 0;
                this.ngodau_id = 0;
                this.phuongdau_id = 0;
                this.quandau_id = 0;
                this.sonhadau = "";

                this.donvi_cuoi_id = 0;
                this.diachicuoi_id = 0;
                this.phocuoi_id = 0;
                this.ngocuoi_id = 0;
                this.phuongcuoi_id = 0;
                this.quancuoi_id = 0;
                this.sonhacuoi = "";
                this.model.txtGhiChuTT = "";

                this.model.txtGhiChuKH = "";
                this.model.txtGhiChuTB = "";
                this.model.txtSoTKKH = "";
                this.model.txtNgayCapHK = "";
                this.model.txtHoKhau = "";
                this.clear_manvtc = "0";
                this.model.cboKhachHangLon.value = this.model.pnlTSL.cboChuQuan.list.length > 0 ? this.model.pnlTSL.cboChuQuan.list[0].id : null


                ///Tuấn thêm code clear một số thông tin của MGWan
                ///--- Ngày 12/09/2010
                this.model.pnlWan.cboChuQuanWan.value = this.model.pnlWan.cboChuQuanWan.list.length > 0 ? this.model.pnlWan.cboChuQuanWan.list[0].id : null
                this.model.pnlWan.cboLoaiTBiWan.value = this.model.pnlWan.cboLoaiTBiWan.list.length > 0 ? this.model.pnlWan.cboLoaiTBiWan.list[0].id : null
                this.model.pnlWan.cboLoaiModem.value = this.model.pnlWan.cboLoaiModem.list.length > 0 ? this.model.pnlWan.cboLoaiModem.list[0].id : null
                this.model.pnlWan.cboChuQuanWan.value = this.model.pnlWan.cboChuQuanWan.list.length > 0 ? this.model.pnlWan.cboChuQuanWan.list[0].id : null
                this.model.pnlWan.cboThoihanWan.value = this.model.pnlWan.cboThoihanWan.list.length > 0 ? this.model.pnlWan.cboThoihanWan.list[0].id : null
                this.model.pnlWan.cboTocDo.value = this.model.pnlWan.cboTocDo.list.length > 0 ? this.model.pnlWan.cboTocDo.list[0].id : null
                this.model.pnlWan.cboTocDo_PIR.value = this.model.pnlWan.cboTocDo_PIR.list.length > 0 ? this.model.pnlWan.cboTocDo_PIR.list[0].id : null
                this.model.pnlWan.cboLoaiKenhWan.value = this.model.pnlWan.cboLoaiKenhWan.list.length > 0 ? this.model.pnlWan.cboLoaiKenhWan.list[0].id : null

                this.model.txtMaSTKH = "";
                this.model.txtNgayHetHan = "";
                this.model.chkNganHangKH = false;
                this.model.cboNganHangKH.value = this.model.cboNganHangKH.list.length > 0 ? this.model.cboNganHangKH.list[0].id : null

                this.doituong_id = 1;
                await this.LayTienTheoKhoanMuc();

                //TuấnNA thêm code clear ngân sách nhà nước --- Ngày 12/11/2010
                this.DV_QHNS = "";
                this.MACHUONG = "";
                this.MN_KT = "";
                this.MA_NDKT = "";
                this.MN_NSNN = "";
                this.TIEN_DM = 0;

                //TuấnNA thêm code clear biến -- Ngày 03/12/2010
                this.TNC1_ID = 0;
                this.TNC2_ID = 0;
                this.TNC3_ID = 0;
                this.TNC4_ID = 0;
                this.nganhnghe_id = 0;

                this.model.cboMuccuocTBCu.list = []
                this.model.cboMucCuocTB.list = []
                this.model.dtpNgaySD = this.ngay_sys
                this.model.lvDvkhac_TT = "";

                this.hdmig_id = 0;
                this.mig_id = 0;
                this.model.txtMaIG = "";
            } catch (e) {
                console.log(e)
            }
        },
        async Lay_tienkhoanmuc_loaihd(loaihd_id) {
            let result = [];
            try {
            const params = { loaihd_id: loaihd_id };
            let response = await api.lay_tienkhoanmuc_loaihd(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;
            }
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async HT_DonVi_loaidv_Combobox(p_loaidv_id) {
            let result = []
            try {
                let params = {
                    "p_loaidv_id": p_loaidv_id,
                    "p_donvi_id": 0
                }
                let response = await api.sp_lay_donvi_quanly_ldv(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data               
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === "200") {
                return response.data.data;        
            } else {
                console.log(response.data.error_code);
            }
            return [];
        },
        getDmLoaiHinh: async function(dichvu_id){
            var input = {
                "dichvuvt_id": dichvu_id
            }
            let data = this.GetData(await api_cco.getDmLoaiHinh(this.axios, input))
            this.model.cboLoaihinhTB.list = data.map((x)=>({id:x.loaitb_id, text:x.loaihinh_tb}))
        },
        async LAY_DS_THUEBAO_DICHVU(hdtbId, thueBaoId, loaiTbId, kieuId) {
            let result = []
            try {
                var params = { 
                    "hdtbId": hdtbId, 
                    "thueBaoId": thueBaoId, 
                    "loaiTbId": loaiTbId, 
                    "kieuId": kieuId
                }
                let response = await api.lay_ds_thuebao_dichvu(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
            return result   
        },
        async LAY_DS_THUEBAO_DICHVU_BC(hdtbId, thueBaoId, loaiTbId, kieuId) {
            let result = []
            try {
                var params = { 
                    "hdtbId": hdtbId, 
                    "thueBaoId": thueBaoId, 
                    "loaiTbId": loaiTbId, 
                    "kieuId": kieuId
                }
                let response = await api.lay_ds_thuebao_dichvu_bc(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
            return result   
        },
        async LAY_DS_SUDUNG_DVGT(thuebao_id) {
            this.m_dsThueBao_DichVu.dsdvgt_ban = await this.LAY_DS_THUEBAO_DICHVU(0, thuebao_id, this.loaitb_id, 1);
            if (this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0) {
                this.m_dsThueBao_DichVu.dsdvgt_tc = await this.LAY_DS_THUEBAO_DICHVU_BC(0, this.thuebao_tc_id, this.loaitb_id, 1)
            }
                
            this.HT_DichVuGT();
        },
        async HT_DichVuGT() {
            let kt = true;// Kiểm tra xem có đăng ký dịch vụ nào không, nếu không trả tiền dv, vatdv về 0 
            this.tien_dv = 0;
            this.vat_dv = 0;
            this.DS_ID_DV = "";
            let str;
            this.model.lvDvgt = ""
            for (let i = 0; i < this.m_dsThueBao_DichVu.dsdvgt_ban.length; i++) {
                if (String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].huy) == "1" || String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].dangky) == "1") {
                    str = String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].ten_dvgt)
                    if (String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].dangky) == "1" && String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].huy) == "0") {
                        kt = false;
                        //Hiếu bổ sung code tính tiền dịch vụ
                        if (String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].cuoc_ld) != "") {
                            this.tien_dv = this.tien_dv + Number(this.m_dsThueBao_DichVu.dsdvgt_ban[i].cuoc_ld);
                        }
                        if (String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].vat_ld) != "") {
                            this.vat_dv = this.vat_dv + Number(this.m_dsThueBao_DichVu.dsdvgt_ban[i].vat_ld);
                        }
                        //
                        //Lấy danh sách ID dịch vụ gia tăng 
                        if (this.DS_ID_DV == "")
                            this.DS_ID_DV = String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].dichvugt_id)
                        else
                            this.DS_ID_DV = this.DS_ID_DV + "," + String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].dichvugt_id)
                        //
                        str = str + " (ĐK)";
                    }

                    if (String(this.m_dsThueBao_DichVu.dsdvgt_ban[i].huy) == "1")
                        str = str + " (Hủy)";
                    this.model.lvDvgt += (str + "\n")
                }
            }

            if (kt == true) {
                this.DS_ID_DV = "";
                this.tien_dv = 0;
                this.vat_dv = 0;
            }

            this.HienThiTienDichVu(this.tien_dv, this.vat_dv);
        },
        HienThiTienDichVu(tien_dv, vat_dv) {
            this.dtien_dv = tien_dv;
            this.dvat_dv = vat_dv;
            for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
                if (String(this.ds_tien_khoanmuc[j].khoanmuctt_id) == String(KHOANMUC_TT.KMTT_DICHVU)) {
                    this.ds_tien_khoanmuc[j].tien = this.dtien_dv;
                    this.ds_tien_khoanmuc[j].vat = this.dvat_dv;
                }
            }
            this.LayTongTien_HDTB();
        },
        LayTongTien_HDTB() {
            try {
                let dtongtien = 0;
                let dtongvat = 0;
                for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
                    dtongtien += Number(this.ds_tien_khoanmuc[i].tien);
                    dtongvat += Number(this.ds_tien_khoanmuc[i].vat);
                }
                this.model.txtTongTien = this.formatNumber(dtongtien);
                this.model.txtTongVat = this.formatNumber(dtongvat);
                this.model.lblTongTien = this.formatNumber(dtongtien + dtongvat);
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        formatNumber(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        },
        Unformat_tien(s){
            s = s.toLowerCase();
            let ketqua = "";
            for (let i = 0; i < s.length; i++) {
                if (String(s[i]) != "," && String(s[i]) != "." && String(s[i]) != " ") {
                    ketqua = ketqua + s[i];
                }
            }
            return ketqua;
        },
        async frmChuyenQuyenSDBC_Load() {
            try {
                // TODO
                // CommonFunction cm = new CommonFunction();
                // if (cm.KiemTra_ThamSo_Registry("kyhoadon", "Kỳ hóa đơn"))
                //     kyhd = cm.RegRead("kyhoadon", "");
                // else {
                //     this.BeginInvoke(new MethodInvoker(this.Close));
                //     return;
                // }

                this.model.dtpNgayLapHDNQ = this.ngay_sys;
                this.model.dtpNgayYC = this.ngay_sys;
                await this.SetButton(-1);

                // Gán danh sách cho tổng tiền ban đầu -- nhapt 08/06/2015
                this.ds_tien_khoanmuc = await this.Lay_tienkhoanmuc_loaihd(LoaiHopDong.CHUYEN_QUYEN);

                var input = {}
                let data = this.GetData(await api_cco.getDmCBB(this.axios, input))
                console.log(data)

                // bangts.HT_DoiTuong_Combobox(cboDoituongTB);
                if (data.doituong && data.doituong.length > 0){
                    this.model.cboDoituongTB.list = data.doituong.map((x)=>({id:x.doituong_id, text:x.ten_dt}))
                    this.model.cboDoituongTB.value = data.doituong[0].doituong_id
                }
         
                // bangts.HT_DichVuVT_Combobox(cboDichVuVT);
                if (data.dichvu_vt && data.dichvu_vt.length > 0){
                    this.model.cboDichVuVT.list = data.dichvu_vt.map((x)=>({id:x.dichvuvt_id, text:x.ten_dvvt}))
                    this.model.cboDichVuVT.value = data.dichvu_vt[0].dichvuvt_id
                }

                // bangts.HT_LoaiGT_Combobox(cboGiayToKH);
                if (data.loai_gt && data.loai_gt.length > 0){
                    this.model.cboGiayToKH.list = data.loai_gt.map((x)=>({id:x.loaigt_id, text:x.loai_gt}))
                    this.model.cboGiayToKH.value = data.loai_gt[0].loaigt_id
                }

                // cboLoaiKH
                if (data.loai_kh && data.loai_kh.length > 0){
                    this.model.cboLoaiKH.list = data.loai_kh
                    // this.model.cboLoaiKH.value = data.loai_kh[0].loaikh_id
                }

                // bangts.HT_LOAI_KHLON_Combobox(cboKhachHangLon);
                if (data.loai_khl && data.loai_khl.length > 0){
                    this.model.cboKhachHangLon.list = data.loai_khl.map((x)=>({id:x.khlon_id, text:x.ten_lkhl}))
                    this.model.cboKhachHangLon.value = data.loai_khl[0].khlon_id
                }

                // bangts.HT_HinhThucTT_Combobox(cboHTTT);
                if (data.hinhthuc_tt && data.hinhthuc_tt.length > 0){
                    this.model.cboHTTT.list = data.hinhthuc_tt.map((x)=>({id:x.httt_id, text:x.hinhthuc_tt}))
                    this.model.cboHTTT.value = data.hinhthuc_tt[0].httt_id
                }

                let temps = await this.HT_DonVi_loaidv_Combobox(LOAI_DV.DONVIQL_TT);
                if (temps && temps.length > 0){
                    this.model.cboDonviQL.list = temps.map((x)=>({id:x.donvi_id, text:x.ten_dv}))
                    this.model.cboDonviQL.value = temps[0].donvi_id
                }

                temps = await this.HT_DonVi_loaidv_Combobox(LOAI_DV.DONVIQL_LD);
                if (temps && temps.length > 0){
                    this.model.cboDaiVT.list = temps.map((x)=>({id:x.donvi_id, text:x.ten_dv}))
                    this.model.cboDaiVT.value = temps[0].donvi_id
                }

                // bangts.HT_Tocdo_Adsl_Combobox(cboGoiCuocAdsl);
                temps = await this.lay_thongtin_ds_tocdo_adsl()
                if (temps && temps.length > 0){
                    this.model.pnlAdsl.cboGoiCuocAdsl.list = temps.map((x)=>({id:x.tocdo_id, text:x.tocdo}))
                    this.model.pnlAdsl.cboGoiCuocAdsl.value = temps[0].tocdo_id
                }

                // bangts.HT_MucCuoc_Adsl_Combobox(cboMucCuoc);
                temps = await this.lay_thongtin_ds_muccuoc()
                if (temps && temps.length > 0){
                    this.model.pnlAdsl.cboMucCuoc.list = temps.map((x)=>({id:x.muccuoc_id, text:x.tenmuc}))
                    this.model.pnlAdsl.cboMucCuoc.value = temps[0].muccuoc_id
                }

                //Tuan them code hien thi thông tin cac combobox len form -- Ngay 07/07/2010
                if (data.nganhang && data.nganhang.length > 0){
                    this.model.cboNganHangTT.list = data.nganhang.map((x)=>({id:x.nganhang_id, text:x.ten_nh}))
                    this.model.cboNganHangTT.value = data.nganhang[0].nganhang_id

                    this.model.cboNganHangKH.list = data.nganhang.map((x)=>({id:x.nganhang_id, text:x.ten_nh}))
                    this.model.cboNganHangKH.value = data.nganhang[0].nganhang_id
                }

                // bangts.HT_DanToc_Combobox(cboDanToc);
                if (data.dantoc && data.dantoc.length > 0){
                    this.model.cboDanToc.list = data.dantoc.map((x)=>({id:x.dantoc_id, text:x.tendantoc}))
                    this.model.cboDanToc.value = data.dantoc[0].dantoc_id
                }

                // bangts.HT_QuocTich_Combobox(cboQuocTich);
                if (data.quoctich && data.quoctich.length > 0){
                    this.model.cboQuocTich.list = data.quoctich.map((x)=>({id:x.quoctich_id, text:x.tenquoctich}))
                    this.model.cboQuocTich.value = data.quoctich[0].quoctich_id
                }

                if (data.nganhnghe && data.nganhnghe.length > 0){
                    this.model.cboNganhNgheLoc.list = data.nganhnghe.map((x)=>({id:x.nganhnghe_id, text:x.tennganhnghe}))
                    this.model.cboNganhNgheLoc.value = data.nganhnghe[0].nganhnghe_id
                }

                await this.getDmLoaiHinh(1);

                // bangts.HT_DT_VinaPhone_Combobox(cboDTVinaPhone, 0);
                if (data.doituong_neo && data.doituong_neo.length > 0){
                    this.model.pnlDD.cboDTVinaPhone.list = data.doituong_neo.map((x)=>({id:x.doituong_id_neo, text:x.ten_dt_neo}))
                    this.model.pnlDD.cboDTVinaPhone.value = data.doituong_neo[0].doituong_id_neo
                }

                ///TuấnNA thêm code hiển thị thông tin các combobox của thuê bao TLS --- Ngày 30/08/2010

                // bangts.HT_ThoiHan_TSL_Combobox(cboThoiHan);
                // bangts.HT_ThoiHan_TSL_Combobox(cboThoihanWan);
                if (data.thoihan && data.thoihan.length > 0){
                    this.model.pnlTSL.cboThoiHan.list = data.thoihan.map((x)=>({id:x.thoihan_id, text:x.tenthoihan}))
                    this.model.pnlTSL.cboThoiHan.value = data.thoihan[0].thoihan_id

                    this.model.pnlWan.cboThoihanWan.list = data.thoihan.map((x)=>({id:x.thoihan_id, text:x.tenthoihan}))
                    this.model.pnlWan.cboThoihanWan.value = data.thoihan[0].thoihan_id
                }
                // bangts.HT_TrangBi_TSL_Combobox(cboTrangBi);
                // bangts.HT_TrangBi_TSL_Combobox(cboTrangBiADSL);
                // bangts.HT_TrangBi_TSL_Combobox(cboTrangBiDD);
                // bangts.HT_TrangBi_TSL_Combobox(cboTrangBiGP);
                // bangts.HT_TrangBi_TSL_Combobox(cboTrangBiWan);
                if (data.trang_bi && data.trang_bi.length > 0){
                    this.model.pnlTSL.cboTrangBi.list = data.trang_bi.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    this.model.pnlTSL.cboTrangBi.value = data.trang_bi[0].trangbi_id

                    this.model.pnlAdsl.cboTrangBiADSL.list = data.trang_bi.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    this.model.pnlAdsl.cboTrangBiADSL.value = data.trang_bi[0].trangbi_id

                    this.model.pnlDD.cboTrangBiDD.list = data.trang_bi.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    this.model.pnlDD.cboTrangBiDD.value = data.trang_bi[0].trangbi_id

                    this.model.pnlGphone.cboTrangBiGP.list = data.trang_bi.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    this.model.pnlGphone.cboTrangBiGP.value = data.trang_bi[0].trangbi_id

                    this.model.pnlWan.cboTrangBiWan.list = data.trang_bi.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    this.model.pnlWan.cboTrangBiWan.value = data.trang_bi[0].trangbi_id
                }
                // bangts.HT_ChuQuan_TSL_Combobox(cboChuQuan);
                // bangts.HT_ChuQuan_TSL_Combobox(cboChuQuanWan);
                if (data.chuquan && data.chuquan.length > 0){
                    this.model.pnlTSL.cboChuQuan.list = data.chuquan.map((x)=>({id:x.chuquan_id, text:x.ten_tat}))
                    this.model.pnlTSL.cboChuQuan.value = data.chuquan[0].chuquan_id

                    this.model.pnlWan.cboChuQuanWan.list = data.chuquan.map((x)=>({id:x.chuquan_id, text:x.ten_tat}))
                    this.model.pnlWan.cboChuQuanWan.value = data.chuquan[0].chuquan_id
                }
                // bangts.HT_Loai_Kenh_Combobox(cboLoaiKenhTSL);
                // bangts.HT_Loai_Kenh_Combobox(cboLoaiKenhWan);
                if (data.loai_kenh && data.loai_kenh.length > 0){
                    this.model.pnlTSL.cboLoaiKenhTSL.list = data.loai_kenh.map((x)=>({id:x.loaikenh_id, text:x.loai_kenh}))
                    this.model.pnlTSL.cboLoaiKenhTSL.value = data.loai_kenh[0].loaikenh_id

                    this.model.pnlWan.cboLoaiKenhWan.list = data.loai_kenh.map((x)=>({id:x.loaikenh_id, text:x.loai_kenh}))
                    this.model.pnlWan.cboLoaiKenhWan.value = data.loai_kenh[0].loaikenh_id
                }
                // bangts.HT_Tocdo_Kenh_Combobox(cboTocDoKenhTSL);
                // bangts.HT_Tocdo_Kenh_Combobox(cboTocDo);
                // bangts.HT_Tocdo_Kenh_Combobox(cboTocDo_PIR);
                if (data.tocdo_kenh && data.tocdo_kenh.length > 0){
                    this.model.pnlTSL.cboTocDoKenhTSL.list = data.tocdo_kenh.map((x)=>({id:x.tocdo_id, text:x.toc_do}))
                    this.model.pnlTSL.cboTocDoKenhTSL.value = data.tocdo_kenh[0].tocdo_id

                    this.model.pnlWan.cboTocDo.list = data.tocdo_kenh.map((x)=>({id:x.tocdo_id, text:x.toc_do}))
                    this.model.pnlWan.cboTocDo.value = data.tocdo_kenh[0].tocdo_id

                    this.model.pnlWan.cboTocDo_PIR.list = data.tocdo_kenh.map((x)=>({id:x.tocdo_id, text:x.toc_do}))
                    this.model.pnlWan.cboTocDo_PIR.value = data.tocdo_kenh[0].tocdo_id
                }

                // bangts.HT_Loai_Modem_Combobox(cboLoaiModem);
                if (data.loai_modem && data.loai_modem.length > 0){
                    this.model.pnlWan.cboLoaiModem.list = data.loai_modem.map((x)=>({id:x.loaimd_id, text:x.loai_md}))
                    this.model.pnlWan.cboLoaiModem.value = data.loai_modem[0].loaimd_id
                }

                // bangts.HT_LOAI_TB_TSL_Combobox(cboLoaiTBTSL);
                // bangts.HT_LOAI_TB_MEGAWAN_Combobox(cboLoaiTBiWan);
                if (data.loai_tb && data.loai_tb.length > 0){
                    let temps1 = data.loai_tb.filter(x => (x.dichvuvt_id == DichVuVienThong.TSL || x.dichvuvt_id == DichVuVienThong.NOIBO_2DIEM));
                    this.model.pnlTSL.cboLoaiTBTSL.list = temps1.map((x)=>({id:x.thietbidc_id, text:x.ten_tbi}))
                    this.model.pnlTSL.cboLoaiTBTSL.value = temps1[0].thietbidc_id

                    temps1 = data.loai_tb.filter(x => x.dichvuvt_id == DichVuVienThong.MEGAWAN);
                    this.model.pnlWan.cboLoaiTBiWan.list = temps1.map((x)=>({id:x.thietbidc_id, text:x.ten_tbi}))
                    this.model.pnlWan.cboLoaiTBiWan.value = temps1[0].thietbidc_id
                }
                
                await this.LAY_DS_SUDUNG_DVGT(0);

                let dsTS = await this.LAY_DS_THAMSO_MD_MATS("HIENTHI_KHL_DATMOI");
                let ht_khl = -1;
                if (dsTS != null && dsTS.length > 0) {
                    ht_khl = Number(dsTS[0].ten_ts);
                }

                if (ht_khl == 1) {
                    this.visible.cboKhachHangLon = true;
                    this.visible.txtPhanLoaiKH = false;
                } else {
                    this.visible.cboKhachHangLon = false;
                    this.visible.txtPhanLoaiKH = true;
                }

                let ds_kt1 = await this.LAY_DS_THAMSO_MD();
                if (ds_kt1.length > 0) {
                    for (let i = 0; i < ds_kt1.length; i++) {
                        if (ds_kt1[i].ma_ts === 'KIEMTRA_MST' && ds_kt1[i].ten_ts === '1') {
                            this.ts_kt_mst = 1
                        }
                        if (ds_kt1[i].ma_ts === 'DONVIQL_HTTT' && ds_kt1[i].ten_ts === '1') {
                            this.DONVIQL_HTTT = 1
                        }
                        if (ds_kt1[i].ma_ts === 'TUYENTHU_HTTT' && ds_kt1[i].ten_ts === '1') {
                            this.TUYENTHU_HTTT = 1
                        }

                        if (ds_kt1[i].ma_ts === 'SINH_MA_GD_THEO_DV') {
                            this.ts_sinhma_gd_theo_donvi = true
                        }
                    }
                }

                this.kt_load = false;
            } catch (e) {
                console.log(e)
            }
        },
        async LAY_DS_THAMSO_MD () {
            let result = []
            try {
                let params = {
                    "p_param": "",
                    "p_type": 1
                }
                let response = await api.sp_tt_thamso_md(this.axios, params)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_THAMSO_MD_MATS(ma_ts) {
            let result = []
            try {
                let params = {
                    "ma_ts": ma_ts
                }
                let response = await api.lay_ds_thamso_md_mats(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data               
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async LAY_DS_HOPDONG_TB_THEO_HDTB_ID(hdtb_id) {
            let result = []
            try {
                let params = {
                    "hdtb_id": hdtb_id
                }
                let response = await api.lay_ds_hopdong_tb_theo_hdtb_id(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async lvwThueBao_SelectedIndexChanged(args) {
            if (args.data == undefined) return
            try {
                this.loading(true)
                this.DsHopDongTB.selected = args.data
                this.hdtb_id = Number(args.data.hdtb_id);
                await this.Gan_thongso_bancheo_hdtb(this.hdtb_id);
                await this.HienThiTTHopDongTB(await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.hdtb_id));
            } catch (ex) {
                console.log(ex)
            } finally {
                this.loading(false)
            }
        },
        async LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(hdtbId) {
            let result = []
            try {
                let params = {
                    "hdtbId": hdtbId
                }
                let response = await api.lay_thongso_thuebao_bancheo_hdtbid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Gan_thongso_bancheo_hdtb(vhdtb) {
            let ds = await this.LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(Number(vhdtb));     
            if (ds.length > 0) {
                let drthongso = Object.assign({}, ds[0])
                let dvvt_id = Number(drthongso.dichvuvt_id);
                if (dvvt_id != DichVuVienThong.HOINGHI_TRUYENHINH &&
                    dvvt_id != DichVuVienThong.ANTOAN_BAOMAT_TT &&
                    dvvt_id != DichVuVienThong.DICHVU_CNTT &&
                    dvvt_id != DichVuVienThong.TRUNGTAM_DULIEU)
                {
                    this.thongso_ban.chuquanld_id = drthongso.chuquanld_ban;
                    this.thongso_ban.congnghe_id = drthongso.congnghe_ban;
                    this.thongso_ban.loaikenh_id = drthongso.loaikenh_ban;
                    this.thongso_ban.loaimd_id = drthongso.loaimd_ban;
                    this.thongso_ban.ma_doicap = drthongso.madoicap_ban;
                    this.thongso_ban.chuquan_id = drthongso.chuquan_ban;
                    this.thongso_ban.ma_tn = drthongso.matn_ban;
                    this.thongso_ban.thietbidc_id = drthongso.thietbidau_ban;
                    this.thongso_ban.thietbidc_id_cuoi = drthongso.thietbicuoi_ban;
                    this.thongso_ban.thoihan_id = drthongso.thoihan_ban;
                    this.thongso_ban.tocdo_id = drthongso.tocdo_ban;
                    this.thongso_ban.tocdo_isp = drthongso.tocdoisp_ban;
                    this.thongso_ban.tocdo_nix = drthongso.tocdonix_ban;
                    try {
                        this.thongso_ban.tocdo_pir_id = drthongso.tocdopir_ban;
                        this.thongso_ban.phanloai_id = drthongso.phanloai_id;
                        this.thongso_ban.phantach_id = drthongso.phantach_td;
                    } catch (e) { }
                    this.thongso_ban.tocdothuc_id = drthongso.tocdothuc_ban;
                    this.thongso_ban.trangbi_id = drthongso.trangbi_ban;

                    this.thongso_tc.chuquan_id = drthongso.chuquan_tc;
                    this.thongso_tc.chuquanld_id = drthongso.chuquanld_tc;
                    this.thongso_tc.congnghe_id = drthongso.congnghe_tc;
                    this.thongso_tc.loaikenh_id = drthongso.loaikenh_tc;
                    this.thongso_tc.loaimd_id = drthongso.loaimd_tc;
                    this.thongso_tc.ma_doicap = drthongso.madoicap_tc;
                    this.thongso_tc.ma_tn = drthongso.matn_tc;

                    this.thongso_tc.thietbidc_id = drthongso.thietbidau_tc;
                    this.thongso_tc.thietbidc_id_cuoi = drthongso.thietbicuoi_tc;
                    try {
                        this.thongso_tc.thoihan_id = drthongso.thoihan_tc;
                        this.thongso_tc.tocdo_pir_id = drthongso.tocdopir_tc;
                    } catch (e) { }
                    this.thongso_tc.tocdo_id = drthongso.tocdo_tc;
                    this.thongso_tc.tocdo_isp = drthongso.tocdoisp_tc;
                    this.thongso_tc.tocdo_nix = drthongso.tocdonix_tc;
                    this.thongso_tc.tocdothuc_id = drthongso.tocdothuc_tc;
                    this.thongso_tc.trangbi_id = drthongso.trangbi_tc;
                }

                this.thongso_ban.muccuoc_id = drthongso.muccuoc_ban;
                this.thongso_ban.muccuoctb_id = drthongso.muccuoctb_ban;
                this.thongso_ban.kieuld_id = drthongso.kieuld_ban;
                this.thongso_ban.doituong_id = drthongso.doituong_ban;
                this.thongso_tc.kieuld_id = drthongso.kieuld_tc;
                this.thongso_tc.doituong_id = drthongso.doituong_tc;
                this.thongso_tc.muccuoc_id = drthongso.muccuoc_tc;
                this.thongso_tc.muccuoctb_id = drthongso.muccuoctb_tc;
                this.thongso_tc.kieutra_id = drthongso.kieutra_tc;
                this.thongso_ban.kieutra_id = drthongso.kieutra_ban;

                if (dvvt_id == DichVuVienThong.MEGAWAN || dvvt_id == DichVuVienThong.METRONET || dvvt_id == DichVuVienThong.TSL || dvvt_id == DichVuVienThong.NOIBO_DADIEM || dvvt_id == DichVuVienThong.NOIBO_2DIEM)
                {
                    //gan tskt rieng cho kenh qt
                    if (drthongso.mhkn_id != null)
                        this.thongso_ban.mhkn_id = drthongso.mhkn_id;
                    if (drthongso.gdkn_id != null)
                        this.thongso_ban.gdkn_id = drthongso.gdkn_id;
                    if (drthongso.yc_baove != null)
                        this.thongso_ban.yc_baove = drthongso.yc_baove;
                    if (drthongso.tocdo_pop != null)
                        this.thongso_ban.tocdo_pop = drthongso.tocdo_pop;
                    if (drthongso.gdkn_pop_id != null)
                        this.thongso_ban.gdkn_pop_id = drthongso.gdkn_pop_id;
                    if (drthongso.cauhinh_dt_id != null)
                        this.thongso_ban.cauhinh_dt_id = drthongso.cauhinh_dt_id;
                    if (drthongso.kieu_duplex_id != null)
                        this.thongso_ban.kieu_duplex_id = drthongso.kieu_duplex_id;
                    if (drthongso.kieukn_id != null)
                        this.thongso_ban.kieukn_id = drthongso.kieukn_id;

                    this.thongso_ban.mtu_size = drthongso.mtu_size;
                    this.thongso_ban.auto_nego = drthongso.auto_nego;
                    this.thongso_ban.sncp = drthongso.sncp;
                    this.thongso_ban.cap_chinh = drthongso.cap_chinh;
                    this.thongso_ban.cap_duphong = drthongso.cap_duphong;
                    this.thongso_ban.diem_pop = drthongso.diem_pop;
                    this.thongso_ban.wan_ip = drthongso.wan_ip;
                    this.thongso_ban.lan_ip = drthongso.lan_ip;
                    this.thongso_ban.ce_ip = drthongso.ce_ip;
                    this.thongso_ban.tt_ip = drthongso.tt_ip;
                    this.thongso_ban.sl_ip = drthongso.sl_ip;
                    this.thongso_ban.ten_trunk_nni = drthongso.ten_trunk_nni;
                    this.thongso_ban.so_as = drthongso.so_as;
                    this.thongso_ban.hqos = drthongso.hqos;
                    this.thongso_ban.cos = drthongso.cos;
                    this.thongso_ban.svlan = drthongso.svlan;
                    this.thongso_ban.cvlan = drthongso.cvlan;
                    this.thongso_ban.vlan_transparent = drthongso.vlan_transparent;
                    this.thongso_ban.tt_port = drthongso.tt_port;

                    if (drthongso.mhkn_id != null)
                        this.thongso_tc.mhkn_id = drthongso.mhkn_id;
                    if (drthongso.gdkn_id != null)
                        this.thongso_tc.gdkn_id = drthongso.gdkn_id;
                    if (drthongso.yc_baove != null)
                        this.thongso_tc.yc_baove = drthongso.yc_baove;
                    if (drthongso.tocdo_pop != null)
                        this.thongso_tc.tocdo_pop = drthongso.tocdo_pop;
                    if (drthongso.gdkn_pop_id != null)
                        this.thongso_tc.gdkn_pop_id = drthongso.gdkn_pop_id;
                    if (drthongso.cauhinh_dt_id != null)
                        this.thongso_tc.cauhinh_dt_id = drthongso.cauhinh_dt_id;
                    if (drthongso.kieu_duplex_id != null)
                        this.thongso_tc.kieu_duplex_id = drthongso.kieu_duplex_id;
                    if (drthongso.kieukn_id != null)
                        this.thongso_tc.kieukn_id = drthongso.kieukn_id;

                    this.thongso_tc.mtu_size = drthongso.mtu_size;
                    this.thongso_tc.auto_nego = drthongso.auto_nego;
                    this.thongso_tc.sncp = drthongso.sncp;
                    this.thongso_tc.cap_chinh = drthongso.cap_chinh;
                    this.thongso_tc.cap_duphong = drthongso.cap_duphong;
                    this.thongso_tc.diem_pop = drthongso.diem_pop;
                    this.thongso_tc.wan_ip = drthongso.wan_ip;
                    this.thongso_tc.lan_ip = drthongso.lan_ip;
                    this.thongso_tc.ce_ip = drthongso.ce_ip;
                    this.thongso_tc.tt_ip = drthongso.tt_ip;
                    this.thongso_tc.sl_ip = drthongso.sl_ip;
                    this.thongso_tc.ten_trunk_nni = drthongso.ten_trunk_nni;
                    this.thongso_tc.so_as = drthongso.so_as;
                    this.thongso_tc.hqos = drthongso.hqos;
                    this.thongso_tc.cos = drthongso.cos;
                    this.thongso_tc.svlan = drthongso.svlan;
                    this.thongso_tc.cvlan = drthongso.cvlan;
                    this.thongso_tc.vlan_transparent = drthongso.vlan_transparent;
                    this.thongso_tc.tt_port = drthongso.tt_port;

                    this.thongso_ban.loai_ip = await this.lay_thongtin_ds_hdtb_loaiip(vhdtb)
                    this.thongso_tc.loai_ip = this.thongso_ban.loai_ip;
                    this.thongso_ban.kieu_nghiemthu = await this.lay_thongtin_ds_hdtb_kieu_nt(vhdtb)
                    this.thongso_tc.kieu_nghiemthu = this.thongso_ban.kieu_nghiemthu;
                }
            }
        },
        async rowSelected_HDTT(args) {
            try {
                if (this.DsHopDongTT.data.length > 0) {
                    this.hdtt_id = Number(args.data.hdtt_id);
                    await this.HienThiTTHopDongTT(await this.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(this.hdtt_id));
                    this.model.cboTT.value = this.hdtt_id;
                }
            } catch (ex) {
                console.log(ex)
            }
        },
        async getDmTienKM_LD() {
            let result = []
            try {
                var params = {
                    "loaihd_id": LoaiHopDong.CHUYEN_QUYEN,
                    "ngay_yc": this.model.dtpNgayYC,
                    "dichvuvt_id": this.model.cboDichVuVT.value,
                    "loaitb_id": this.model.cboLoaihinhTB.value,
                    "kieuld_id": this.model.cboKieuLD.value,
                    "phuongld_id": this.phuongld_id,
                    "tocdo_id": this.tocdo_id,
                    "muccuoc_id": 0,
                    "ltb_adsl_id": this.ltb_adsl_id,
                    "ltb_adsl_moi_id": this.ltb_adsl_moi_id,
                    "ltb_adsl_cu_id": this.ltb_adsl_cu_id,
                    "tocdo_moi_id": this.tocdo_moi_id,
                    "tocdo_cu_id": this.tocdo_cu_id,
                    "kh_cd": this.kh_cd,
                    "phold_id": this.phold_id,
                    "apld_id": this.apld_id,
                    "khuld_id": this.khuld_id,
                    "doituong_id": this.model.cboDoituongTB.value,
                    "trangbi_id": this.trangbi_id
                }

                let response = await api_cco.getDmTienKM_LD(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LayTienTheoKhoanMuc() {
            try {
                if (this.dichvuvt_id != DichVuVienThong.ADSL) {
                    this.kh_cd = 0;
                    this.ltb_adsl_id = 1;
                }

                let ds = await this.getDmTienKM_LD()
                if (ds.length > 0) {
                    this.HienThiTienLapDat(ds);
                }
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        HienThiTienLapDat(ds) {
            let kt_cd = true;
            let kt_lm = true;
            if (String(ds[0].tien) != "-1") {
                for (let i = 0; i < ds.length; i++) {
                    if (String(ds[i].khoanmuctt_id) == String(KHOANMUC_TT.KMTT_CAIDAT)) {
                        this.dtien_dv = Number(ds[i].tien)
                        this.dvat_dv = Number(ds[i].vat)
                        kt_cd = false
                    }  else if (String(ds[i].khoanmuctt_id) == String(KHOANMUC_TT.KMTT_CHUYENQUYEN)) {
                        this.dtien_cq = Number(ds[0].tien)
                        this.dvat_cq = Number(ds[0].vat)
                        kt_lm = false;
                    }
                }

                if (kt_cd == true) {
                    this.dtien_dv = 0;
                    this.dvat_dv = 0;
                }

                if (kt_lm == true) {
                    this.dtien_cq = 0;
                    this.dvat_cq = 0;
                }
            } else {
                this.dtien_dv = 0;
                this.dvat_dv = 0;
                this.dtien_cq = 0;
                this.dvat_cq = 0;
            }

            for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
                if (String(this.ds_tien_khoanmuc[j].khoanmuctt_id) == String(KHOANMUC_TT.KMTT_CHUYENQUYEN)) {
                    this.ds_tien_khoanmuc[j].tien = this.dtien_cq;
                    this.ds_tien_khoanmuc[j].vat = this.dvat_cq;
                }
            }
        },
        async GiaoDien_DichVuVT(dichvuvt_id) {
            this.visible.pnlTSL = false;
            this.visible.pnlInt = false;
            this.visible.pnlDD = false;
            this.visible.pnlAdsl = false;
            this.visible.pnlWan = false;
            this.visible.pnlGphone = false;
            this.visible.pnlIMS = false;

            if (dichvuvt_id == DichVuVienThong.DI_DONG) {
                this.enable.btnDiaChiTB = true;
                this.visible.pnlDD = true;
            } else if (dichvuvt_id == DichVuVienThong.ADSL || dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH ||
                    dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || dichvuvt_id == DichVuVienThong.DICHVU_CNTT || dichvuvt_id == DichVuVienThong.MEGA_EYES) {
                this.visible.pnlAdsl = true;
            } else if (dichvuvt_id == DichVuVienThong.TSL || DichVuVienThong.NOIBO_2DIEM) {
                this.visible.pnlTSL = true;
            } else if (dichvuvt_id == DichVuVienThong.GPHONE) {
                this.visible.pnlGphone = true;
            } else if (dichvuvt_id == DichVuVienThong.MEGAWAN) {
                this.visible.pnlWan = true;
            } else if (dichvuvt_id == DichVuVienThong.IMS) {
                this.visible.pnlIMS = true;
                await this.HT_ThietBi_IMS_Combobox();
                await this.HT_TrangBi_TSL_Combobox();
            }
        },
        async HT_ThietBi_IMS_Combobox() {
            let temps = await this.lay_thongtin_ds_thietbi_ims()
            if (temps && temps.length > 0){
                this.model.pnlIMS.cboThietBi.list = temps.map((x)=>({id:x.thietbi_id, text:x.ten_tbi}))
                this.model.pnlIMS.cboThietBi.value = temps[0].thietbi_id
            }
        },
        async HT_TrangBi_TSL_Combobox() {
            let temps = await this.lay_thongtin_ds_trangbi()
            if (temps && temps.length > 0){
                this.model.pnlIMS.cboTrangBiIMS.list = temps.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                this.model.pnlIMS.cboTrangBiIMS.value = temps[0].trangbi_id
            }
        },
        async cboDichVuVT_SelectedValueChanged() {
            console.log("cboDichVuVT_SelectedValueChanged")
            this.dichvuvt_id = this.model.cboDichVuVT.value
            await this.getDmLoaiHinh(this.dichvuvt_id)

            if (this.model.cboLoaihinhTB.list.length > 0) {         
                if (this.dichvuvt_id == DichVuVienThong.ADSL) {
                    console.log("1: " + LoaiHinhTB.INTERNET_ADSL)
                    this.model.cboLoaihinhTB.value = LoaiHinhTB.INTERNET_ADSL
                } else {
                    console.log("2: " + this.model.cboLoaihinhTB.list[0].id)
                    this.model.cboLoaihinhTB.value = this.model.cboLoaihinhTB.list[0].id
                }
            }
            
            let temps = await this.lay_thongtin_ds_goicuoc_dd(this.model.cboLoaihinhTB.value)
            if (temps && temps.length > 0){
                this.model.pnlDD.cboGoiCuocDD.list = temps.map((x)=>({id:x.goicuoc_id, text:x.tengoicuoc}))
                this.model.pnlDD.cboGoiCuocDD.value = temps[0].goicuoc_id
            }

            await this.GiaoDien_DichVuVT(this.dichvuvt_id);
            this.Kieuld_click = false;
        },
        async cboHTTT_SelectedValueChanged() {
            this.httt_id = Number(this.model.cboHTTT.value);
            if (this.httt_id == HinhThucThanhToan.UYNHIEM_THU) {
                this.enable.cboNganHangTT = true;
                if (cboNganHangTT.Properties.DataSource != null) {
                    let loai_nh = String(await this.fn_tt_nganhang(1, this.model.cboNganHangTT.value));
                    if (loai_nh == "2") {
                        this.enable.btnNSNN = true;
                    } else
                        this.enable.btnNSNN = false;
                }
            } else {
                this.enable.cboNganHangTT = false;
                this.enable.btnNSNN = false;
            }

            if (this.TUYENTHU_HTTT == 1) {
                if (this.phuongtt_id != 0 && (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0)) {
                    await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, Number(this.model.cboDonviQL.value), this.dacdiemtt_id);
                }
            }
        },
        async HT_nvtc_theodc_v2(vphuong_id, vpho_id, vap_id, vkhu_id, vdonvi_ql_id, vdacdiem_id, vhttt_id) {
            let result = []
            try {
                let params = {
                    "vphanvung_id": this.$root.token.getPhanVungID(),
                    "vphuong_id": vphuong_id,
                    "vpho_id": vpho_id,
                    "vap_id": vap_id,
                    "vkhu_id": vkhu_id,
                    "vdonvi_ql_id": vdonvi_ql_id,
                    "vdacdiem_id": vdacdiem_id,
                    "vhttt_id": vhttt_id
                }
                let response = await api.HT_nvtc_theodc_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async SP_HT_NVTC_TheoDC(vphuong_id, vpho_id, vap_id, vkhu_id, vdonvi_ql_id, vdacdiem_id) {
            let result = []
            try {
                let params = {
                    "vphanvung_id": this.$root.token.getPhanVungID(),
                    "vphuong_id": vphuong_id,
                    "vpho_id": vpho_id,
                    "vap_id": vap_id,
                    "vkhu_id": vkhu_id,
                    "vdonvi_ql_id": vdonvi_ql_id,
                    "vdacdiem_id": vdacdiem_id
                }
                let response = await api.ht_nvtc_theodc(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_NVTC_TheoDC(quan_id, phuong_id, pho_id, ap_id, khu_id, vdonvi_ql_id, dacdiem_id) {
            console.log("HT_NVTC_TheoDC")
            try {
                let ds = []
                if (this.TUYENTHU_HTTT == 1 && this.model.cboHTTT.value != null && String(this.model.cboHTTT.value) != "") {
                    ds = await this.HT_nvtc_theodc_v2(phuong_id, pho_id, ap_id, khu_id, vdonvi_ql_id,
                        dacdiem_id, Number(this.model.cboHTTT.value));
                } else {
                    ds = await this.SP_HT_NVTC_TheoDC(phuong_id, pho_id, ap_id, khu_id, vdonvi_ql_id, dacdiem_id);
                }      

                console.log(ds)
                if (ds.length > 1) {
                    this.diachiLD.QUAN_ID = quan_id;
                    this.diachiLD.PHUONG_ID = phuong_id;
                    this.diachiLD.PHO_ID = pho_id;
                    this.diachiLD.AP_ID = ap_id;
                    this.diachiLD.KHU_ID = khu_id;
                    this.diachiLD.DACDIEM_ID = dacdiem_id;
                    this.diachiLD.NHANVIENTC_ID = this.nhanvientc_id;
                    this.diachiLD.TUYENTHU_ID = Number(ds[0].tuyenthu_id);
                    if (this.TUYENTHU_HTTT == 1 && this.model.cboHTTT.value != null && String(this.model.cboHTTT.value) != "") {
                        this.diachiLD.HTTT_ID = Number(this.model.cboHTTT.value)
                    }
                    this.$refs.dlgTimKiemNVTC.openDialog();
                } else if (ds.length > 0) {
                    this.nhanvientc_id = Number(ds[0].nhanvien_id);
                    this.model.txtMaNVTC = ds[0].manv_tc;
                    if (ds[0].tuyenthu_id != undefined && String(ds[0].tuyenthu_id) != "") {
                        this.tuyenthu_id = Number(ds[0].tuyenthu_id);
                    }            
                    this.model.txtMaTuyen = ds[0].ma_tuyen;
                } else {
                    this.model.txtMaNVTC = "";
                    this.model.txtMaTuyen = "";
                    $("#txtMaNVTC").focus();
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async getNVTCProcess(value) {
            if (this.diachiLD.QUAN_ID != 0 && this.diachiLD.PHUONG_ID != 0) {
                this.nhanvientc_id = value.rowData.NHANVIEN_ID;
                this.model.txtMaNVTC = value.maNVTC;        
                this.model.txtMaTuyen = value.rowData.MA_TUYENTHU;
                this.tuyenthu_id = value.rowData.TUYENTHU_ID;
                if (this.DONVIQL_HTTT == 1)
                    await this.HienThi_NVQL_AM_HTTT(Number(this.model.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, 
                        this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.model.cboDonviQL.value), Number(this.model.cboHTTT.value));
                else
                    await this.HienThi_NVQL_AM(Number(this.model.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, 
                        this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.model.cboDonviQL.value));
            } else {
                this.$toast.error("Chưa có thông tin về địa chỉ thanh toán\nBạn kiểm tra lại!");
                this.nhanvientc_id = 0;
                this.model.txtMaNVTC = "";
                this.model.txtMaTuyen = "";
                this.tuyenthu_id = 0;
                $("#txtMaNVTC").focus();
            }
        },
        cboGiayToKH_SelectedValueChanged() {

        },
        async cboLoaihinhTB_SelectedValueChanged() {
            console.log("cboLoaihinhTB_SelectedValueChanged: " + this.model.cboLoaihinhTB.value)
            this.Lay_DS_MucCuoc_TB(this.phold_id, this.phuongld_id, Number(this.model.cboLoaihinhTB.value), Number(this.model.cboDoituongTB.value), this.ngay_sys);
            await this.HT_Kieu_LD_Combobox(Number(this.model.cboLoaihinhTB.value), LoaiHopDong.CHUYEN_QUYEN);
        },
        Lay_DS_MucCuoc_TB(vphold_id, vphuongld_id, vloaitb_id, vdoituong_id, ngay) {

        },
        async LAY_DS_KIEU_LD(vloaihd_id, vloaitb_id) {
            let result = []
            try {
                let params = {
                    "vloaihd_id": vloaihd_id,
                    "vloaitb_id": vloaitb_id
                }
                let response = await api.lay_ds_kieu_ld(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_Kieu_LD_Combobox(loaitb_id, loaihd_id) {
            console.log("HT_Kieu_LD_Combobox")
            console.log(this.DsHopDongTB.selected)
            let temps = []
            if (loaitb_id == 0)
                temps = await this.lay_thongtin_kieuld(loaihd_id)
            else {
                temps = await this.LAY_DS_KIEU_LD(loaihd_id, loaitb_id)
            }
          
            if (temps && temps.length > 0) {
                this.model.cboKieuLD.list = temps.map((x)=>({id:x.kieuld_id, text:x.ten_kieuld}))
                if (this.DsHopDongTB.selected != undefined && this.DsHopDongTB.selected.kieuld_id != undefined &&
                    this.DsHopDongTB.selected.kieuld_id != null) {
                    this.model.cboKieuLD.value = this.DsHopDongTB.selected.kieuld_id
                } else {
                    this.model.cboKieuLD.value = null;
                }
            }
        },
        btnEditDVGT_Click() {           
            this.$refs.frmDangKyDVGTBC.openDialog()
        },
        chapnhan_DangKyDVGTBC(dsDK_DVGT) {
            this.m_dsThueBao_DichVu = dsDK_DVGT;
            this.HT_DichVuGT();
        },
        cboKieuLD_onChange() {
            console.log("cboKieuLD_onChange")
            if (this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0) {
                this.$refs.frmThongSoCoDinh.openDialog(this.thongso_ban, this.thongso_tc)
            }
            // if (!this.kt_load)
            //     this.Kieuld_click = true;
        },
        async cboKieuLD_SelectedValueChanged() {
            console.log("cboKieuLD_SelectedValueChanged")
            if (Number(this.model.cboKieuLD.value) == KieuLapDat.STADSL_DOI_TOCDO) {
                this.enable.cboMucCuoc = true;
                this.enable.cboGoiCuocAdsl = true;
                this.enable.txtEmailAdsl = true;
            } else {
                this.enable.cboMucCuoc = false;
                this.enable.cboGoiCuocAdsl = false;
                this.enable.txtEmailAdsl = false;
            }

            ///TuấnNA bổ sung hàm lấy tiền lên ô text box --- Ngày 15/09/2010
            await this.LayTienTheoKhoanMuc();
            if (Number(this.model.cboKieuLD.value) == KieuLapDat.STCD_DOI_DICHVU) {
                this.enable.btnEditDVGT = true;
            } else {
                ///TuấnNa sửa code lấy lại danh sách dịch vụ cộng thêm ban đầu của thuê bao
                /// Trong trường hợp user lập hợp đồng chọn yêu cầu CQ CĐịnh + đổi dịch vụ cộng thêm
                /// ---> Sửa thành CQ cố định
                /// --- Ngày 30/08/2010
                this.m_dsThueBao_DichVu.dsdvgt_ban = await this.LAY_DS_THUEBAO_DICHVU(0, this.thuebao_id, this.loaitb_id, 1);
                if (this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0) {
                    this.m_dsThueBao_DichVu.dsdvgt_tc = await this.LAY_DS_THUEBAO_DICHVU_BC(0, this.thuebao_tc_id, this.loaitb_id, 1)
                }

                this.enable.btnEditDVGT = false;
                this.model.lvDvgt = ""
            }

            if (this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0 && this.Kieuld_click) {
                this.$refs.frmThongSoCoDinh.openDialog(this.thongso_ban, this.thongso_tc)
            }
        },
        capnhat_TSCD(tscd) {
            this.thongso_ban = tscd.thongso_Ban;
            this.thongso_tc = tscd.thongso_TC;
        },
        async cboDoituongTB_SelectedValueChanged() {
            if (this.model.cboDoituongTB.list.length > 0 && this.model.cboDichVuVT.list.length > 0) {
                await this.LayTienTheoKhoanMuc(); // Lấy tiền theo đối tượng
            }        
        },
        async LAY_DS_HOPDONG_TT_THEO_HDTT_ID(hdtt_id) {
            let result = []
            try {
                let params = {
                    "hdtt_id": hdtt_id
                }
                let response = await api.lay_ds_hopdong_tt_theo_hdtt_id(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        ChuanHoaTen(ten) {
            if(!ten) return '';
            var ten_new = '';
            var str_con = ten;
            var str_xl = "";
            var k = 0;
            var found=true;
            if(str_con.length<1) return;
            str_con = str_con.replaceAll(/\s+/g,' ');
            try {
                while(found) {
                    k= str_con.indexOf(' ');
                    if(k>0) {
                        str_xl = str_con.substring(0,k);
                        str_con = str_con.substring(k+1);
                        str_xl = this.ChuyenChuHoa(str_xl);
                        if(ten_new != "")
                        ten_new += " "+str_xl;
                        else ten_new = str_xl;
                        found=true;
                    } else found = false;
                }
                if(str_con != "") {
                str_xl = str_con;
                str_xl = this.ChuyenChuHoa(str_xl);
                if (ten_new != "")
                    ten_new = ten_new + " " + str_xl;
                else
                    ten_new = str_xl;
                }
                return ten_new;
            } catch(e) { return ten; }
        },
        ChuyenChuHoa(str) {
            var kq = "";
            if(str.ismatch(/[A-Z][A-Z]/g)) kq=str.toUpperCase();
            else {
                if(str[0]=='(')
                    kq = str[0]+str[1].toUpperCase()+str.substring(2).toLowerCase();
                else
                    kq = str[0].toUpperCase()+str.substring(1).toLowerCase();
            }
            return kq;
        },
        async LAY_DS_DIACHI_THEO_HDTT_ID(hdttId) {
            let result = []
            try {
                let params = {
                    "hdttId": hdttId
                }
                let response = await api.lay_ds_diachi_theo_hdtt(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_COMBO_MA_DAIDIEN(thanhtoan_id) {
            let result = []
            try {
                let params = {
                    "thanhtoan_id": thanhtoan_id
                }
                let response = await api.ht_combo_ma_daidien(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiTTHopDongTT(ds) {
            if (ds.length > 0) {
                this.hdtt_id = Number(ds[0].hdtt_id);
                this.model.txtMaTT = ds[0].ma_tt;

                if (ds[0].thanhtoan_id != "")
                    this.thanhtoan_id = Number(ds[0].thanhtoan_id);
                else
                    this.thanhtoan_id = 0;

                if (ds[0].hdmig_id != "") {
                    this.hdmig_id = Number(ds[0].hdmig_id);
                    this.model.txtMaIG = await this.fn_tt_hd_mig(1, this.hdmig_id);
                } else {
                    this.hdmig_id = 0;
                    this.model.txtMaIG = "";
                }

                this.model.txtTenTT = this.ChuanHoaTen(ds[0].ten_tt);
                this.model.txtDiaChiTT = this.ChuanHoaTen(ds[0].diachi_tt);
                this.model.txtDiaChiCT = this.ChuanHoaTen(ds[0].diachi_ct);
                this.model.txtDiaChiBC = this.ChuanHoaTen(ds[0].diachi_bc);
                this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_HDTT_ID(this.hdtt_id), 2);

                //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
                this.DV_QHNS = ds[0].dv_qhns;
                this.MACHUONG = ds[0].machuong;
                this.MN_KT = ds[0].mn_kt;
                this.MA_NDKT = ds[0].ma_ndkt;
                this.MN_NSNN = ds[0].mn_nsnn;
                if (ds[0].tien_dm != "")
                    this.TIEN_DM = Number(ds[0].tien_dm);
                else
                    this.TIEN_DM = 0;

                this.model.txtMST_TT = ds[0].mst;
                this.model.txtSTK_TT = ds[0].stk;
                this.model.txtChu_TK = ds[0].chu_tk;
                this.model.txtGhiChuTT = ds[0].ghichu;
                if (ds[0].nganhang_id != "")
                    this.model.cboNganHangTT.value = Number(ds[0].nganhang_id);
                this.model.cboHTTT.value = Number(ds[0].httt_id);

                ///TuanNA viết code thay thế cboDonviQL -- Ngày 26/07/2010
                if (ds[0].donvi_id != undefined && String(ds[0].donvi_id) != "")
                    this.model.cboDonviQL.value = ds[0].donvi_id;

                if (ds[0].nhanvien_id != "") {
                    this.nhanvientc_id = Number(ds[0].nhanvien_id);
                    this.model.txtMaNVTC = await this.fn_tt_nhanvien(4, this.nhanvientc_id);
                } else {
                    this.nhanvientc_id = 0;
                    this.model.txtMaNVTC = "";
                }

                if (ds[0].tuyenthu_id != "") {
                    this.tuyenthu_id = Number(ds[0].tuyenthu_id);
                    this.model.txtMaTuyen = ds[0].ma_tuyen;
                } else {
                    this.tuyenthu_id = 0;
                    this.model.txtMaTuyen = "";
                }
                this.matb_dd = ds[0].matb_dd;

                ///TuanNA thêm code hiển thị matb_dd trong hợp đồng thanh toán --- Ngày 29/08/2010
                let dt_madd = await this.HT_COMBO_MA_DAIDIEN(this.thanhtoan_id);
                if (dt_madd && dt_madd.length > 0){
                    this.model.cboMaDD.list = dt_madd.map((x)=>({id:x.ma_tb, text:x.ten_ht, ten_tat:x.ten_tat}))
                    this.model.cboMaDD.value = ds[0].matb_dd;
                }           

                await this.LAY_DS_SUDUNG_DVK(this.thanhtoan_id, 2);
                await this.HienThi_NVQL_AM_DB_HD(2, this.hdtt_id);
            }
        },
        HienThiTTDiaChi(ds, kieu) {
            if (ds.length > 0) {
                if (kieu == 1) {
                    /// Tuan sửa lại code kiểm tra dữ liệu địa chỉ thanh toán -- Ngay 29/07/2010
                    if (String(ds[0].diachi_id) != "")
                        this.diachikh_id = Number(ds[0].diachi_id);
                    else
                        this.diachikh_id = 0;

                    if (String(ds[0].tinh_id) != "")
                        this.tinhkh_id = Number(ds[0].tinh_id);
                    else
                        this.tinhkh_id = 0;

                    if (String(ds[0].quan_id) != "")
                        this.quankh_id = Number(ds[0].quan_id);
                    else
                        this.quankh_id = 0;

                    if (String(ds[0].phuong_id) != "")
                        this.phuongkh_id = Number(ds[0].phuong_id);
                    else
                        this.phuongkh_id = 0;

                    if (String(ds[0].pho_id) != "")
                        this.phokh_id = Number(ds[0].pho_id);
                    else
                        this.phokh_id = 0;

                    if (String(ds[0].ap_id) != "")
                        this.apkh_id = Number(ds[0].ap_id);
                    else
                        this.apkh_id = 0;

                    if (String(ds[0].khu_id) != "")
                        this.khukh_id = Number(ds[0].khu_id);
                    else
                        this.khukh_id = 0;

                    if (String(ds[0].dacdiem_id) != "")
                        this.dacdiemkh_id = Number(ds[0].dacdiem_id);
                    else
                        this.dacdiemkh_id = 0;

                    this.sonhakh = this.ChuanHoaTen(String(ds[0].sonha));
                }

                if (kieu == 2) {
                    if (String(ds[0].diachi_id) != "")
                        this.diachitt_id = Number(ds[0].diachi_id);
                    else
                        this.diachitt_id = 0;

                    if (String(ds[0].tinh_id) != "")
                        this.tinhtt_id = Number(ds[0].tinh_id);
                    else
                        this.tinhtt_id = 0;

                    if (String(ds[0].quan_id) != "")
                        this.quantt_id = Number(ds[0].quan_id);
                    else
                        this.quantt_id = 0;

                    if (String(ds[0].phuong_id) != "")
                        this.phuongtt_id = Number(ds[0].phuong_id);
                    else
                        this.phuongtt_id = 0;

                    if (String(ds[0].pho_id) != "")
                        this.phott_id = Number(ds[0].pho_id);
                    else
                        this.phott_id = 0;

                    if (String(ds[0].ap_id) != "")
                        this.aptt_id = Number(ds[0].ap_id);
                    else
                        this.aptt_id = 0;

                    if (String(ds[0].khu_id) != "")
                        this.khutt_id = Number(ds[0].khu_id);
                    else
                        this.khutt_id = 0;

                    if (String(ds[0].dacdiem_id) != "")
                        this.dacdiemtt_id = Number(ds[0].dacdiem_id);
                    else
                        this.dacdiemtt_id = 0;

                    this.sonhatt = this.ChuanHoaTen(String(ds[0].sonha));

                    //Địa chỉ báo cước
                    if (String(ds[0].diachibc_id) != "")
                        this.diachibc_id = Number(ds[0].diachibc_id);
                    else
                        this.diachibc_id = 0;

                    if (String(ds[0].tinhbc_id) != "")
                        this.tinhbc_id = Number(ds[0].tinhbc_id);
                    else
                        this.tinhbc_id = 0;

                    if (String(ds[0].quanbc_id) != "")
                        this.quanbc_id = Number(ds[0].quanbc_id);
                    else
                        this.quanbc_id = 0;

                    if (String(ds[0].phuongbc_id) != "")
                        this.phuongbc_id = Number(ds[0].phuongbc_id);
                    else
                        this.phuongbc_id = 0;

                    if (String(ds[0].phobc_id) != "")
                        this.phobc_id = Number(ds[0].phobc_id);
                    else
                        this.phobc_id = 0;

                    if (String(ds[0].apbc_id) != "")
                        this.apbc_id = Number(ds[0].apbc_id);
                    else
                        this.apbc_id = 0;

                    if (String(ds[0].khubc_id) != "")
                        this.khubc_id = Number(ds[0].khubc_id);
                    else
                        this.khubc_id = 0;

                    if (String(ds[0].dacdiembc_id) != "")
                        this.dacdiembc_id = Number(ds[0].dacdiembc_id);
                    else
                        this.dacdiembc_id = 0;

                    this.sonhabc = this.ChuanHoaTen(String(ds[0].sonha_bc));
                }

                if (kieu == 3) {
                    //Hiếu- thêm phần địa chỉ thuê bao + địa chỉ lắp đặt 22/07/2010
                    this.diachitb_id = Number(ds[0].diachi_id);
                    //Địa chỉ thuê bao
                    if (String(ds[0].tinh_id) != "")
                        this.tinhtb_id = Number(ds[0].tinh_id);
                    else
                        this.tinhtb_id = 0;

                    if (String(ds[0].quan_id) != "")
                        this.quantb_id = Number(ds[0].quan_id);
                    else
                        this.quantb_id = 0;

                    if (String(ds[0].phuong_id) != "")
                        this.phuongtb_id = Number(ds[0].phuong_id);
                    else
                        this.phuongtb_id = 0;

                    if (String(ds[0].pho_id) != "")
                        this.photb_id = Number(ds[0].pho_id);
                    else
                        this.photb_id = 0;

                    if (String(ds[0].ap_id) != "")
                        this.aptb_id = Number(ds[0].ap_id);
                    else
                        this.aptb_id = 0;

                    if (String(ds[0].khu_id) != "")
                        this.khutb_id = Number(ds[0].khu_id);
                    else
                        this.khutb_id = 0;

                    if (String(ds[0].dacdiem_id) != "")
                        this.dacdiemtb_id = Number(ds[0].dacdiem_id);
                    else
                        this.dacdiemtb_id = 0;
                    ///
                    this.sonhatb = this.ChuanHoaTen(String(ds[0].sonha));
                    this.model.txtDiaChiTB = (ds[0].diachi != undefined && ds[0].diachi != "") ? this.ChuanHoaTen(String(ds[0].diachi)) : this.model.txtDiaChiTB;

                    /// Tuấn thêm code lấy địa chỉ lắp đặt cho thuê bao -- Ngày 29/07/2010
                    if (String(ds[0].diachild_id) != "")
                        this.diachild_id = Number(ds[0].diachild_id);
                    else
                        this.diachild_id = 0;

                    if (String(ds[0].tinhld_id) != "")
                        this.tinhld_id = Number(ds[0].tinhld_id);
                    else
                        this.tinhld_id = 0;

                    if (String(ds[0].quanld_id) != "")
                        this.quanld_id = Number(ds[0].quanld_id);
                    else
                        this.quanld_id = 0;

                    if (String(ds[0].phuongld_id) != "")
                        this.phuongld_id = Number(ds[0].phuongld_id);
                    else
                        this.phuongld_id = 0;

                    if (String(ds[0].phold_id) != "")
                        this.phold_id = Number(ds[0].phold_id);
                    else
                        this.phold_id = 0;

                    if (String(ds[0].apld_id) != "")
                        this.apld_id = Number(ds[0].apld_id);
                    else
                        this.apld_id = 0;

                    if (String(ds[0].khuld_id) != "")
                        this.khuld_id = Number(ds[0].khuld_id);
                    else
                        this.khuld_id = 0;

                    if (String(ds[0].dacdiemld_id) != "")
                        this.dacdiemld_id = Number(ds[0].dacdiemld_id);
                    else
                        this.dacdiemld_id = 0;

                    ///
                    this.sonhald = this.ChuanHoaTen(String(ds[0].sonha_ld));
                    this.model.txtDiachiLD = (ds[0].diachi_ld != undefined && ds[0].diachi_ld != "") ? this.ChuanHoaTen(String(ds[0].diachi_ld)) : this.model.txtDiachiLD
                }
            }
        },
        async Lay_NhanVien_QL(vkieu, vid) {
            let result = []
            try {
                let params = {
                    "vkieu": vkieu,
                    "vid": vid
                }
                let response = await api.lay_nhanvien_ql(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThi_NVQL_AM_DB_HD(i_kieu, id) { // Kieu = 1 : danh ba, kieu = 2 : hop dong
            try {
                let ds_tt = await this.Lay_NhanVien_QL(i_kieu, id);
                if (ds_tt.length > 0) {
                    this.model.txtNhanVienQL = ds_tt[0].ma_nv + " - " + ds_tt[0].ten_nv;
                    this.nhanvien_ql_am_id = Number(ds_tt[0].nhanvien_id);
                } else {
                    this.model.txtNhanVienQL = "";
                    this.nhanvien_ql_am_id = 0;
                }
            } catch (ex) {
                this.$toast.error("Có lỗi : " + ex);
            }
        },
        async SP_LAY_DS_SUDUNG_DVK(thuebaoId, loaiId) {
            let result = []
            try {
                let params = {
                    "thuebaoId": thuebaoId,
                    "loaiId": loaiId
                }
                let response = await api_cco.getDSDichVuKhac_CQ(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_SUDUNG_DVK(db_id, vloaiid) {
            let dset = await this.SP_LAY_DS_SUDUNG_DVK(db_id, vloaiid);
            this.dsDK_DVK = []
            for (let i = 0; i < dset.length; i++) {
                let row = {}
                row.dvkhac_id = Number(dset[i].dvKhacId);
                row.kieu_yc = 1;
                row.ttth = TrangThaiCaiDat.MOI;
                row.hd_id = this.hdtb_id;
                row.ngay_yc = this.model.dtpNgayYC;
                row.loaiid = vloaiid;
                this.dsDK_DVK.push(row);
            }

            let str = "";
            if (vloaiid == 1) {
                this.model.lvDvkhac = ""
                for (let i = 0; i < this.dsDK_DVK.length; i++) {
                    let ds = await this.lay_thongtin_ds_dichvu_khac(Number(this.dsDK_DVK[i].dvkhac_id))
                    str = (String(ds[0].ten_dvkhac) + " (Đang SD) \n")
                    this.model.lvDvkhac += str;
                }
            } else {
                this.lvDvkhac_TT = ""
                for (let i = 0; i < this.dsDK_DVK.length; i++) {
                    let ds = await this.lay_thongtin_ds_dichvu_khac(Number(this.dsDK_DVK[i].dvkhac_id))
                    str = (String(ds[0].ten_dvkhac) + " (Đang SD) \n")
                    this.model.lvDvkhac_TT += str;
                }
            }
        },
        async cboTT_SelectedValueChanged() {
            try {
                this.hdtt_id = Number(this.model.cboTT.value);
                await this.HienThiTTHopDongTT(await this.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(this.hdtt_id));
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async cboTrangBi_SelectedValueChanged() {
            if (Number(this.model.cboDichVuVT.value) == DichVuVienThong.TSL || Number(this.model.cboDichVuVT.value) == DichVuVienThong.NOIBO_2DIEM) {
                this.trangbi_id = Number(this.model.pnlTSL.cboTrangBi.value);
                await this.LayTienTheoKhoanMuc();
            }
        },
        async cboTrangBiWan_SelectedValueChanged() {
            if (Number(this.model.cboDichVuVT.value) == DichVuVienThong.MEGAWAN) {
                this.trangbi_id = Number(this.model.pnlWan.cboTrangBiWan.value);
                await this.LayTienTheoKhoanMuc();
            }
        },
        async cboTrangBiADSL_SelectedValueChanged() {
            if (Number(this.model.cboDichVuVT.value) == DichVuVienThong.ADSL) {
                this.trangbi_id = Number(this.model.pnlAdsl.cboTrangBiADSL.value);
                await this.LayTienTheoKhoanMuc();
            }
        },
        async cboTrangBiDD_SelectedValueChanged() {
            if (Number(this.model.cboDichVuVT.value) == DichVuVienThong.DI_DONG) {
                this.trangbi_id = Number(this.model.pnlDD.cboTrangBiDD.value);
                await this.LayTienTheoKhoanMuc();
            }
        },
        async cboTrangBiGP_SelectedValueChanged() {
            if (Number(this.model.cboDichVuVT.value) == DichVuVienThong.GPHONE) {
                this.trangbi_id = Number(this.model.pnlGphone.cboTrangBiGP.value);
                await this.LayTienTheoKhoanMuc();
            }
        },
        async cboNganHangTT_EditValueChanged() {
            try {
                let loai_nh = String(await this.fn_tt_nganhang(1, this.model.cboNganHangTT.value));
                this.enable.btnNSNN = (loai_nh == "2");
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        cboNganhNgheLoc_EditValueChanged() {
            if (!this.eventNganhNghe) return;
            if (this.nganhnghe_id != Number(this.model.cboNganhNgheLoc.value)) {
                this.TNC1_ID = 0;
                this.TNC2_ID = 0;
                this.TNC3_ID = 0;
                this.TNC4_ID = 0;
            }
            // this.btnNganhNghe_Click();
        },
        btnNganhNghe_Click() {
            try {
                this.tieunganh.TNC1_ID = this.TNC1_ID;
                this.tieunganh.TNC2_ID = this.TNC2_ID;
                this.tieunganh.TNC3_ID = this.TNC3_ID;
                this.tieunganh.TNC4_ID = this.TNC4_ID;

                this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupTieuNganh');
                this.popupComponentName = "popupTieuNganh";
                this.popupComponentData = {"vnganhnghe_id": this.model.cboNganhNgheLoc.value, "tieunganh": this.tieunganh}
                this.Popup('popupComponentsCQBC');
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        Popup(modalId) {
            this.$bvModal.show(modalId);
        },
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId);
            this.popupComponent = null;
        },
        async popupClosed(val) {
            if(!val) {
                this.ClosePopup('popupComponentsCQBC');
                return;
            }
            this.loading(true);
            try {
                switch(this.popupComponentName) {
                    case "SearchAccount":
                        this.model.txtMaKH = val.ma_kh;
                        $("#txtMaKH").focus();
                        break
                    case "popupTieuNganh":
                        this.TNC1_ID = val.TNC1_ID;
                        this.TNC2_ID = val.TNC2_ID;
                        this.TNC3_ID = val.TNC3_ID;
                        this.TNC4_ID = val.TNC4_ID;
                        break;
                    case "popupTienKM":
                        this.ds_tien_khoanmuc = val;
                        if (this.ds_tien_khoanmuc.length > 0) {
                            for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
                                if (String(this.ds_tien_khoanmuc[i].khoanmuctt_id) == String(KHOANMUC_TT.KMTT_DICHVU)) {
                                    this.dtien_dv = Number(this.ds_tien_khoanmuc[i].tien);
                                    this.dvat_dv = Number(this.ds_tien_khoanmuc[i].vat);
                                }

                                if (String(this.ds_tien_khoanmuc[i].khoanmuctt_id) == String(KHOANMUC_TT.KMTT_CHUYENQUYEN)) {
                                    this.dtien_cq = Number(this.ds_tien_khoanmuc[i].tien);
                                    this.dvat_cq = Number(this.ds_tien_khoanmuc[i].vat);
                                }
                                this.LayTongTien_HDTB();
                            }
                        }
                        break;
                    case "popupChonMaTT":
                        this.closePopupLayMaTT(val)
                        break;
                    case "popupChonMaInGhep":
                        this.closePopupChonMaInGhep(val)
                        break;
                    case "popupCTV":
                        if (val.nhanvien_id == undefined || val.nhanvien_id == "") {
                            this.txtMaCTV = ""
                            this.ctv_id = 0
                            this.chkCTV = false
                            this.enableCTV = false
                        } else {
                            this.txtMaCTV = val.ten_ht;
                            this.ctv_id = val.nhanvien_id;
                            this.enableCTV = true
                        }
                        break;
                    case "popupNGT":
                        if (val.nhanvien_id == undefined || val.nhanvien_id == "") {
                            this.txtMaNGT = ""
                            this.chkNguoiGT = false
                            this.enableNGT = false
                        } else {
                            this.txtMaNGT = val.ten_ht;
                            this.enableNGT = true
                        }
                        break;
                }
            } catch(ex) { 
                this.$toast.error(ex); 
            } finally {
                this.ClosePopup('popupComponentsCQBC');
                this.ClosePopup("popupComponentPayment")
                this.loading(false);
            }
        },
        async Lay_ds_donviql_v2(phuong_id, pho_id, ap_id, khu_id, kieu, loaikh_id, httt_id) {
            let result = []
            try {
                let params = {
                    "phuong_id": phuong_id,
                    "pho_id": pho_id,
                    "ap_id": ap_id,
                    "khu_id": khu_id,
                    "kieu": kieu,
                    "loaikh_id": loaikh_id,
                    "httt_id": httt_id,
                }
                let response = await api.lay_ds_donviql_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DONVIQL(phuong_id, pho_id, ap_id, khu_id, kieu, loaikh_id) {
            let result = []
            try {
                let params = {
                    "vphuong_id": phuong_id,
                    "vpho_id": pho_id,
                    "vap_id": ap_id,
                    "vkhu_id": khu_id,
                    "vkieu": kieu,
                    "vloaikh_id": loaikh_id,
                }
                let response = await api.lay_ds_donviql(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        acceptChonDonVi(data) {
            this.model.cboDonviQL.value = data.donviql_id
        },
        async cboLoaiKH2_EditValueChanged() {
            try {
                if (this.model.cboLoaiKH.value == null || String(this.model.cboLoaiKH.value) == "" || String(this.model.cboLoaiKH.value) == "-1")
                    return;

                let kieukh_id = 0;
                let kieukh = "";

                let dsLoai = await this.lay_thongtin_ds_loai_kh(this.model.cboLoaiKH.value)
                if (dsLoai != null && dsLoai.length > 0) {
                    kieukh = dsLoai[0].loai;
                }

                if (kieukh != "")
                    kieukh_id = Number(kieukh);

                this.model.txtPhanLoaiKH = "";
                if (kieukh_id == KIEU_KH.CA_NHAN) {
                    let dsPL = await this.lay_thongtin_ds_phanloai_kh(PHANLOAI_KH.CA_NHAN)
                    if (dsPL != null && dsPL.length > 0) {
                        this.PHANLOAIKH_ID = Number(dsPL[0].phanloaikh_id);
                    }

                    this.model.lblNgaySN = "Ngày sinh (*)";
                    this.enable.rdoGTNam = true;      
                    this.enable.rdoGTNu = true;
                    this.enable.rdoGTNone = false;
                    this.model.rdoGT = "rdoGTNam";
                    this.kieukh_canhan = true;

                    this.model.txtPhanLoaiKH = dsPL[0].ma_plkh;
                } else {
                    let dsPL = await this.lay_thongtin_ds_phanloai_kh(PHANLOAI_KH.DOANH_NGHIEP)
                    if (dsPL != null && dsPL.length > 0) {
                        this.PHANLOAIKH_ID = Number(dsPL[0].phanloaikh_id);
                    }

                    this.model.lblNgaySN = "Ngày TL (*)";
                    this.enable.rdoGTNam = false;
                    this.enable.rdoGTNu = false;
                    this.enable.rdoGTNone = true;   
                    this.model.rdoGT = "rdoGTNone";              
                    this.kieukh_canhan = false;

                    this.model.txtPhanLoaiKH = dsPL[0].ma_plkh;
                }

                // hoangpkn : 25/10/2017 Thêm nhân viên quản lý
                if (this.phuongtt_id != 0 && (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0)) {
                    let ds = []
                    if (this.DONVIQL_HTTT == 1)
                        ds = await this.Lay_ds_donviql_v2(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1,
                            Number(this.model.cboLoaiKH.value), Number(this.model.cboHTTT.value));
                    else
                        ds = await this.LAY_DS_DONVIQL(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1,
                            Number(this.model.cboLoaiKH.value));

                    if (ds.length <= 0) {
                        if (this.model.cboDonviQL.list.length != 1) {
                            this.$toast.error("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
                            this.model.cboDonviQL.value = null;
                            return;
                        }
                    } else if (ds.length == 1) {
                        this.model.cboDonviQL.value = Number(ds[0].donvi_id);
                    } else {
                        this.dsToThiCong = ds
                        this.$refs.chonDonViModal.showModal()
                    }
                }

                await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 
                    Number(this.model.cboDonviQL.value), this.dacdiemtt_id);

                if (this.DONVIQL_HTTT == 1)
                    await this.HienThi_NVQL_AM_HTTT(Number(this.model.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, 
                        this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.model.cboDonviQL.value), Number(this.model.cboHTTT.value));
                else
                    await this.HienThi_NVQL_AM(Number(this.model.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, 
                        this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.model.cboDonviQL.value));
                // end hoàng

            } catch (e) {
                
            }
        },
        async HienThi_NVQL_AM(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id) {
            console.log("HienThi_NVQL_AM")
            try {
                if (this.kt_load == false) {
                    let ds_nvql = await this.lay_ds_nhanvien_ql_am_1(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id);     
                    console.log(ds_nvql)
                    if (ds_nvql.length <= 0) {
                        this.model.txtNhanVienQL = "";
                        this.nhanvien_ql_am_id = 0;
                    } else {
                        this.model.txtNhanVienQL = ds_nvql[0].ma_nv + " - " + ds_nvql[0].ten_nv;
                        this.nhanvien_ql_am_id = Number(ds_nvql[0].nhanvien_id);
                    }
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async lay_ds_nhanvien_ql_am_1(loaikh_id, loainv_id, phuong_id, pho_id, ap_id, khu_id, nvtc_id, donviql_id) {
            let result = []
            try {
                let params = {
                    "loaikh_id": loaikh_id,
                    "loainv_id": loainv_id,
                    "phuong_id": phuong_id,
                    "pho_id": pho_id,
                    "ap_id": ap_id,
                    "khu_id": khu_id,
                    "nvtc_id": nvtc_id,
                    "donviql_id": donviql_id
                }
                let response = await api.lay_ds_nhanvien_ql_am_1(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThi_NVQL_AM_HTTT(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id, vhttt_id) {
            try {
                if (this.kt_load == false) {
                    let ds_nvql = await this.Lay_ds_nhanvien_ql_am_v2(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id, vhttt_id);

                    if (ds_nvql.length <= 0) {
                        this.model.txtNhanVienQL = "";
                        this.nhanvien_ql_am_id = 0;
                    } else {
                        this.model.txtNhanVienQL = ds_nvql[0].ma_nv + " - " + ds_nvql[0].ten_nv;
                        this.nhanvien_ql_am_id = Number(ds_nvql[0].nhanvien_id);
                    }
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async Lay_ds_nhanvien_ql_am_v2(vloaikh_id, vloainv_id, vphuong_id, vpho_id, vap_id, vkhu_id, vnvtc_id, vdonviql_id, vhttt_id) {
            let result = []
            try {
                let params = {
                    "vphanvung_id": this.$root.token.getPhanVungID(),
                    "vloaikh_id": vloaikh_id,
                    "vloainv_id": vloainv_id,
                    "vphuong_id": vphuong_id,
                    "vpho_id": vpho_id,
                    "vap_id": vap_id,
                    "vkhu_id": vkhu_id,
                    "vnvtc_id": vnvtc_id,
                    "vdonviql_id": vdonviql_id,
                    "vhttt_id": vhttt_id,
                }
                let response = await api.lay_ds_nhanvien_ql_am_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        chkNganHangKH_CheckedChanged() {
            this.enable.cboNganHangKH = this.model.chkNganHangKH
        },
        async LAY_DS_SOMAY_TN_THEO_MATB(maTb) {
            let result = []
            try {
                let params = {
                    "maTb": maTb,
                }
                let response = await api.lay_somay_truynhap_theo_matb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_HOPDONG_THEO_MA_TB(maTb, loaiHdId, donViDlId, ttHdId, dichVuVtId, loaiTbId) {
            let result = []
            try {
                let params = {
                    "maTb": maTb, 
                    "loaiHdId": loaiHdId,
                    "donViDlId": donViDlId,
                    "ttHdId": ttHdId,
                    "dichVuVtId": dichVuVtId, 
                    "loaiTbId": loaiTbId
                }
                let response = await api_cco.getDSHopDong_MaTB(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async txtMaTB_KeyPress(e) {
            try {
                this.loading(true)
                if (e.keyCode == 13) {
                    if (this.model.txtMaTB != "") {
                        this.Kieuld_click = false;
                        // let ds_somay = await this.LAY_DS_SOMAY_TN_THEO_MATB(this.model.txtMaTB);
                        // let str = "";
                        // if (ds_somay.length > 0) {
                        //     for (let i = 0; i < ds_somay.length; i++) {
                        //         str = str + ds_somay[i] + ", ";
                        //     }

                        //     if (str != ", ")
                        //         this.$toast.error("Số máy/Acc: " + this.model.txtMaTB + " đang được đi kèm với các mã thuê bao: " +
                        //                     str.substring(0, str.length - 2) + ".");
                        // }

                        let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB(this.model.txtMaTB, LoaiHopDong.CHUYEN_QUYEN, 0, 
                                    TrangThaiHD.MOI, this.dichvuvt_id, 0);
                        if (ds.length > 0) {
                            await this.HienThiTTHopDongKH(ds);
                        } else {
                            await this.HienThiTT_DanhBa(this.model.txtMaTB);
                        }
                    }
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            } finally {
                this.loading(false)
            }
        },
        async HienThiTTHopDongKH(ds) {
            try {
                if (ds.length > 0) {
                    this.Kieuld_click = false;
                    this.hdkh_id = Number(ds[0].hdkh_id);
                    this.ma_gd = ds[0].ma_gd;
                    this.model.txtMaGD = ds[0].ma_gd;
                    this.model.txtMaHD = ds[0].ma_hd;
                    this.model.txtMaKH = ds[0].ma_kh;
                    if (ds[0].khachhang_id != "")
                        this.khachhang_id = Number(ds[0].khachhang_id);
                    else
                        this.khachhang_id = 0;
                    this.model.txtTenKH = this.ChuanHoaTen(ds[0].ten_kh);
                    this.model.txtDiaChiKH = this.ChuanHoaTen(ds[0].diachi_kh);
                    this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_HDKH_ID(this.hdkh_id), 1);
                    if (ds[0].diachi_ndd != undefined && ds[0].diachi_ndd != "")
                        this.model.txtDiaChiNDD = this.ChuanHoaTen(ds[0].diachi_ndd);
                    else
                        this.model.txtDiaChiNDD = this.model.txtDiaChiKH;
                    this.model.txtNguoiDD = this.ChuanHoaTen(ds[0].nguoi_dd);
                    this.model.txtChucDanhDD = this.ChuanHoaTen(ds[0].chucdanh_dd);
                    this.model.txtTelKH = ds[0].so_dt;
                    this.model.txtFaxKH = ds[0].so_fax;

                    this.model.txtHoKhau = this.ChuanHoaTen(ds[0].hokhau);
                    this.model.txtNoiCapHK = this.ChuanHoaTen(ds[0].noicap_hk);
                    this.model.cboQuocTich.value = ds[0].quoctich_id;
                    this.model.cboDanToc.value = ds[0].dantoc_id;

                    this.eventNganhNghe = false
                    this.model.cboNganhNgheLoc.value = ds[0].nganhnghe_id;
                    this.nganhnghe_id = Number(ds[0].nganhnghe_id);
                    this.eventNganhNghe = true

                    //Tuan them cac truong du lieu hien thi tren form -- Ngay 05/07/2010 
                    this.model.txtMaSTKH = ds[0].mst;
                    if (ds[0].gioitinh == "1") {
                        this.model.rdoGT = "rdoGTNu";
                    } else if (ds[0].gioitinh == "0") {
                        this.model.rdoGT = "rdoGTNam";
                    }
                    if (ds[0].ngay_hk != null && ds[0].ngay_hk != "")
                        this.model.txtNgayCapHK = moment(ds[0].ngay_hk, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                    else
                        this.model.txtNgayCapHK = "";

                    if (ds[0].khlon_id != "")
                        this.model.cboKhachHangLon.value = Number(ds[0].khlon_id);

                    if (ds[0].loaikh_id != "")
                        this.model.cboLoaiKH.value = Number(ds[0].loaikh_id);

                    try {
                        let strPLKH = await this.fn_tt_hd_khachhang(4, this.hdkh_id);
                        if (strPLKH != "-1") {
                            this.PHANLOAIKH_ID = Number(strPLKH);
                        }
                    } catch (e) {
                    }

                    if (ds[0].ngay_sn != null && ds[0].ngay_sn != "")
                        this.model.txtNgaySN = moment(ds[0].ngay_sn, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                    else
                        this.model.txtNgaySN = "";

                    if (ds[0].ngay_yc != "")
                        this.model.dtpNgayYC = ds[0].ngay_yc;
                    if (ds[0].ngaylap_hd != "")
                        this.model.dtpNgayLapHDCQ = moment(ds[0].ngaylap_hd, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                    else
                        this.model.dtpNgayLapHDCQ = this.ngay_sys

                    this.model.txtGhiChuKH = this.ChuanHoaTen(ds[0].ghichu);
                    this.model.txtDacTrung = ds[0].dactrung;

                    //Hiếu bổ sung khlon_id,ngaylhd,loaihd_id
                    this.model.cboKhachHangLon.value = Number(ds[0].khlon_id);
                    this.ngaylhd = this.model.dtpNgayLapHDCQ;
                    this.loaihd_id = Number(ds[0].loaihd_id);
                    if (ds[0].ma_duan != undefined)
                        this.model.txtMaDuAn = ds[0].ma_duan;
						
                    this.model.txtSoGT = ds[0].so_gt;
                    if (ds[0].ngaycap != null && ds[0].ngaycap != "") {
                        this.model.txtNgayCap = moment(ds[0].ngaycap, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                    } else {
                        this.model.txtNgayCap = "";
                    }

                    if (ds[0].ngayhethan_gt != null && ds[0].ngayhethan_gt != "")
                        this.model.txtNgayHetHan = moment(ds[0].ngayhethan_gt, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                    else
                        this.model.txtNgayHetHan = "";

                    this.model.cboGiayToKH.value = ds[0].loaigt_id;
                    this.model.txtNoiCap = this.ChuanHoaTen(ds[0].noicap);
                    this.model.txtSoTKKH = ds[0].stk;

                    if (ds[0].nganhang_id != "") {
                        this.model.chkNganHangKH = true;
                        this.model.cboNganHangKH.value = ds[0].nganhang_id;
                    }

                    //TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
                    if (ds[0].tnc1_id != "") {
                        this.TNC1_ID = Number(ds[0].tnc1_id);
                    }
                    if (ds[0].tnc2_id != "") {
                        this.TNC2_ID = Number(ds[0].tnc2_id);
                    }
                    if (ds[0].tnc3_id != "") {
                        this.TNC3_ID = Number(ds[0].tnc3_id);
                    }
                    if (ds[0].tnc4_id != "") {
                        this.TNC4_ID = Number(ds[0].tnc4_id);
                    }
                    //End tuan

                    await this.HienThiDS_HDTT_CBO(this.hdkh_id);
                    let ds_hd_tt = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
                    let ds_hd_tb = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
                    await this.HienThiTTHopDongTT(ds_hd_tt);
                    await this.HienThiTTHopDongTB(ds_hd_tb);
                    this.HienThiDanhSacHDTT(ds_hd_tt);
                    await this.HienThiDanhSacHDTB(ds_hd_tb);

                    await this.SetButton(3);
                } else {
                    this.$toast.error("Không tìm thấy thông tin về hợp đồng có mã giao dịch: " + this.model.txtMaGD + "\n Bạn hãy kiểm tra lại");
                    await this.SetButton(-1); /// TuấnNA sửa code --- ngày 24/09/2010
                    await this.Clear();
                }
            } catch (exp) {
                console.log(exp)
            }
        },
        async LAY_DANHBA_THEO_MATB(maTb, dichVuVtId, donViDlId) {
            let result = []
            try {
                let params = {
                    "maTb": maTb,
                    "dichVuVtId": dichVuVtId,
                    "donViDlId": donViDlId
                }
                let response = await api.lay_danhba_theo_matb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result.push(response.data.data)
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LayDS_TB_Huong_KM(thueBaoId, kieu) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId,
                    "kieu": kieu
                }
                let response = await api.lay_ds_chitiet_khuyenmai(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async kiemtra_thuebao_lap_hopdong_khac(maTb, maGd, dichVuVtId, loaiHdId) {
            let result = {}
            try {
                let params = {
                    "maTb": maTb,
                    "maGd": maGd,
                    "dichVuVtId": dichVuVtId,
                    "loaiHdId": loaiHdId
                }
                let response = await api.kiemtra_thuebao_lap_hopdong_khac(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb, ma_gd, vdichvuvt_id, vloaihd_id) {
            let result = false;
            try {
                if (ma_tb != undefined && ma_tb != "") {
                    let row = await this.kiemtra_thuebao_lap_hopdong_khac(ma_tb, ma_gd, vdichvuvt_id, vloaihd_id);
                    if (row.ma_tb != undefined) {
                        result = true;
                        let message =
                                "Số máy/Acc: " + ma_tb + " đang được lập bởi hợp đồng (phụ lục) " + row.ten_loaihd + " có mã giao dịch " + row.ma_gd + " do User " 
                                    + row.nguoi_cn + " thuộc đơn vị " + row.ten_dv + " thực hiện vào ngày " + row.ngay_yc
                        this.$toast.error(message);
                    }
                }             
            } catch (ex) {
                this.$toast.error(ex);
            }
            return result;
        },
        async LAY_DS_DIACHI_THEO_DBKHID(khachHangId) {
            let result = []
            try {
                let params = {
                    "khachHangId": khachHangId
                }
                let response = await api.lay_ds_diachi_theo_khachhang(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async KIEMTRA_TBDD_CUNG_MATT_HDTB(thueBaoId, maGd) {
            let result = ""
            try {
                let params = {
                    "thueBaoId": thueBaoId,
                    "maGd": maGd
                }
                let response = await api.kiemtra_hopdong_thuebao(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DIACHI_THEO_DBTBID(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.bancheo_lay_ds_diachi_theo_dbtbid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },    
        async HienThiTT_DanhBa(ma_tb) {
            try
            {
                ///Tuấn NA sửa code cho phép user được phép lập hợp đồng cho thuê bao không thuộc đơn vị dữ liệu của user đó --- Ngày 19/10/2010
                ///tt_nd.donvi_dl_id --> 0
                let ds = await this.LAY_DANHBA_THEO_MATB(ma_tb, this.dichvuvt_id, 0);
                if (ds.length > 0) {
                    //Lamnt: Neu co nhieu hon 2 thue bao trung ma-> Hien thi form chon
                    if (ds.length > 1) {
                        this.$refs.popupDSTBCungMa.showModal(ds)
                        if (this.$refs.popupDSTBCungMa.getDialogResult()) {
                            ds = this.$refs.popupDSTBCungMa.getDanhSach()
                        }
                    }

                    //TuannA  - 10.07.2014: thêm code kiểm tra thuê bao có khuyến mại còn cam kết hoặc còn hiệu lực không -> ko cho làm
                    let ds_km = await this.LayDS_TB_Huong_KM(Number(ds[0].thuebao_id), 2);
                    if (ds_km.length > 0) {
                        let tb = "Thuê bao " + ds[0].ma_tb;
                        tb += " đang hưởng khuyến mại : \n " + ds_km[0].ten_km;
                        tb += " \n  Có chi tiết khuyến mại : ";
                        for (let i = 0; i < ds_km.length; i++) {
                            tb += "\n   - " + ds_km[i].ten_ctkm;
                        }
                        this.$toast.error(tb);
                    }
                    //End TuanNA

                    //TuấnNA thêm code thông báo thuê bao lập PL khác -- ngày 15/12/2010
                    //HungHM-09/09/2010 - Kiểm tra xem mã TB này có đang được lập hợp đồng hoặc phụ lục khác hay không?
                    if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb, "0", this.model.cboDichVuVT.value, LoaiHopDong.CHUYEN_QUYEN)) return;
                    //

                    ///Tuấn thêm code kiểm tra trạng thái thuê bao --- Ngày 07/09/2010
                    ///---> Nếu trạng thái thuê bao # " Hoạt động bình thường" ---> không cho phép làm tiếp
                    if (Number(ds[0].trangthaitb_id) != TrangThaiTB.THUONG
                        && Number(ds[0].trangthaitb_id) != TrangThaiTB.KHOA_MOTCHIEU_YEUCAU
                        && Number(ds[0].trangthaitb_id) != TrangThaiTB.KHOA_HAICHIEU_YEUCAU
                        && Number(ds[0].trangthaitb_id) != TrangThaiTB.TAMDUNG) {
                        this.$toast.error("Thuê bao có mã: " + ds[0].ma_tb
                                            + " đang ở trạng thái: " + ds[0].trangthai_tb
                                            + "\nBạn không thể thực hiện chuyển quyền cho thuê bao này");
                        return;
                    }
                    //Tuan bo sung code kiem tra khach hang da ky hop dong goc chua -- Ngay 07/07/2010
                    //region lấy thông tin trong danh bạ
                    if (Number(ds[0].ky_hd) == 0) {
                        this.$toast.error("Khách hàng có mã thuê bao: " + this.model.txtMaTB + " chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc");
                    } else {
                        //Tuan bo sung code kiem tra thue bao nhap moi co thuoc khach hang dang chuyen quyen hay khong--Ngay 05/07/2010
                        this.khachhang_id_nm = Number(ds[0].khachhang_id);
                        // dang co khach hang dang chuyen quyen
                        if (this.khachhang_id_cq != 0 && this.DsHopDongTB.data.length > 0)  {
                            if (this.khachhang_id_nm != this.khachhang_id_cq) {
                                this.$toast.error("Mã thuê bao vừa nhập không thuộc khách hàng đang chuyển quyền. Bạn hãy kiểm tra lại !");
                            } else {
                                //Thông tin khách hàng (cũ)
                                this.model.txtMaHDCu = ds[0].ma_hd;
                                this.model.txtMaKHCu = ds[0].ma_kh;
                                this.ma_kh_cu = ds[0].ma_kh;///Tuấn thêm code mã khách hàng cũ -- Ngày 06/08/2010
                                if (ds[0].ngaylap_hd != "")
                                    this.model.dtpNgayLapHDCQ = moment(ds[0].ngaylap_hd, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                                else
                                    this.model.dtpNgayLapHDCQ = this.ngay_sys
                                this.khachhang_id_cq = Number(ds[0].khachhang_id);
                                this.model.txtTenKHCu = ds[0].ten_kh;

                                // Tuan them code lay gia tri ten_tb_cu cho khach hang CQ -- ngay 22/07/2010
                                this.ten_tb_cu = ds[0].ten_tb;
                                this.model.txtDiaChiKHCu = ds[0].diachi_kh;
                                this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBKHID(this.khachhang_id), 1);

                                // Tuan them code lay gia tri ma_tt_cu cho khach hang CQ -- ngay 22/07/2010
                                this.ma_tt_cu = ds[0].ma_tt;
                                // Tuan them code lay gia tri ten_tt_cu cho khach hang CQ -- ngay 22/07/2010
                                this.ten_tt_cu = ds[0].ten_tt;
                                // Tuan them code lay gia tri diachi_tt_cu cho khach hang CQ -- ngay 22/07/2010
                                this.diachi_tt_cu = ds[0].diachi_tt;


                                //Thông tin thuê bao
                                //region Thông tin thuê bao
                                this.thuebao_id = Number(ds[0].thuebao_id);
                                this.tinh = await this.LAY_TINHTHICONG_THEO_THUEBAOID(this.thuebao_id);
                                if (this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0) {
                                    this.thuebao_tc_id = await this.LAY_THUEBAOID_THICONG(this.tinh.tinhthicong_id, this.thuebao_id);
                                }

                                if (ds[0].thuebao_cha_id != "")
                                    this.thuebao_cha_id = Number(ds[0].thuebao_cha_id);
                                else
                                    this.thuebao_cha_id = 0;

                                if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.model.txtMaTB, "0", this.model.cboDichVuVT.value, LoaiHopDong.CHUYEN_QUYEN)) return;

                                ///TuấnNA thêm code chặn trường hợp thuê bao di động nhập mới không cùng mã thanh toán với các thuê bao di động có trong hợp đồng
                                ///--- Ngày 15/07/2010
                                if (this.dichvuvt_id == DichVuVienThong.DI_DONG && this.model.cboLoaihinhTB.value == LoaiHinhTB.DIDONGTRASAU) {
                                    if (this.ma_gd != "") {
                                        if (String(await this.KIEMTRA_TBDD_CUNG_MATT_HDTB(this.thuebao_id, this.ma_gd)) != "1") {
                                            this.$toast.error("Số thuê bao di động: " +
                                                this.model.txtMaTB + " vừa nhập không cùng mã thanh toán: " +
                                                ds[0].ma_tt +
                                                " với thuê bao di động trong phụ lục\nBạn phải lập một phụ lục khác cho thuê bao này! ");
                                            return;
                                        }
                                    }
                                }

                                ///TuấnNA sửa code hiển thị combobox đài VT Full --- Ngày 19/10/2010
                                ///Trong trường hợp thuê bao không thuộc đơn vị dữ liệu của user, chương trình tự động chọn cbodaiVT --- > donvi_id(db_thuebao)
                                ///Giao dịch viên không được chọn.
                                //bangts.HT_DaiVT_Combobox(cboDaiVT, 0);
                                //Tuan them code hien thi loaitb_id -- Ngay 23/07/2010
                                console.log("4: " + Number(ds[0].loaitb_id))
                                this.model.cboLoaihinhTB.value = Number(ds[0].loaitb_id);

                                //Tuan them code hien thi diachi lapdat -- Ngay 29/07/2010
                                this.model.txtDiachiLD = ds[0].diachi_ld;

                                // Tuan them code lay gia tri ma_tb cho khach hang CQ -- ngay 22/07/2010
                                this.ma_tb_cu = ds[0].ma_tb;
                                this.model.txtMaTBCu = ds[0].ma_tb;
                                this.model.txtTenTBCu = ds[0].ten_tb;
                                this.model.txtDiaChiTB = ds[0].diachi_tb;
                                this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_tc_id), 3);
                                //TuấnNA thêm code hiển thị thông tin email thuê bao -- theo y/c của A Thái --- Ngày 16/11/2010

                                this.loaitb_id = Number(ds[0].loaitb_id);
                                this.model.cboDoituongTB.value = ds[0].doituong_id;
                                this.doituong_id = Number(ds[0].doituong_id);           
                                this.dichvuvt_id = Number(await this.fn_tt_loaihinh_tb(2, this.loaitb_id));
                                // this.model.cboDichVuVT.value = this.dichvuvt_id;
                                this.model.cboDaiVT.value = Number(ds[0].donviql_id);///Đơn vị trong bàng db_thuebao

                                //TuấnNA thêm code hiển thị ngày SD -- ngày 14/12/2010
                                if (ds[0].ngay_sd != "")
                                    this.model.dtpNgaySD = moment(ds[0].ngay_sd, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                                //endregion

                                if (ds[0].mucuoctb_id != undefined && ds[0].mucuoctb_id != "") {
                                    this.mucuoctb_id = Number(ds[0].mucuoctb_id);
                                    let temps = await this.HT_MucCuocTB_Combobox(Number(ds[0].mucuoctb_id))
                                    if (temps && temps.length > 0){
                                        this.model.cboMucCuocTB.list = temps.map((x)=>({id:x.mucuoctb_id, text:x.tenmuccuoc}))
                                        this.model.cboMucCuocTB.value = temps[0].mucuoctb_id

                                        this.model.cboMuccuocTBCu.list = temps.map((x)=>({id:x.mucuoctb_id, text:x.tenmuccuoc}))
                                        this.model.cboMuccuocTBCu.value = temps[0].mucuoctb_id
                                    }
                                }

                                await this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id);
                                await this.LAY_DS_SUDUNG_DVGT(this.thuebao_id);
                                await this.LAY_DS_SUDUNG_DVK(this.thuebao_id, 1);
                                $("#txtTenTB").focus();
                            }
                        }
                        // chưa có khách hàng đang chuyển quyền
                        else
                        {
                            //Thông tin khách hàng
                            this.model.txtMaHDCu = ds[0].ma_hd;
                            this.model.txtMaKHCu = ds[0].ma_kh;
                            this.ma_kh_cu = ds[0].ma_kh;///Tuấn thêm code mã khách hàng cũ -- Ngày 06/08/2010
                            if (ds[0].ngaylap_hd != "")
                                this.model.dtpNgayLapHDCQ = moment(ds[0].ngaylap_hd, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                            else
                                this.model.dtpNgayLapHDCQ = this.ngay_sys

                            this.khachhang_id_cq = Number(ds[0].khachhang_id);
                            this.model.txtTenKHCu = ds[0].ten_kh;

                            this.ten_tb_cu = ds[0].ten_tb;
                            this.model.txtDiaChiKHCu = ds[0].diachi_kh;
                            this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBKHID(this.khachhang_id), 1);
                            this.ma_tt_cu = ds[0].ma_tt;
                            this.ten_tt_cu = ds[0].ten_tt;
                            this.diachi_tt_cu = ds[0].diachi_tt;

                            //Thông tin thuê bao
                            this.thuebao_id = Number(ds[0].thuebao_id);
                            this.tinh = await this.LAY_TINHTHICONG_THEO_THUEBAOID(this.thuebao_id);
                            if (this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0) {
                                this.thuebao_tc_id = await this.LAY_THUEBAOID_THICONG(this.tinh.tinhthicong_id, this.thuebao_id);
                            }

                            this.loaitb_id = Number(ds[0].loaitb_id);
                            console.log("5: " + Number(ds[0].loaitb_id))
                            this.model.cboLoaihinhTB.value = Number(ds[0].loaitb_id);//Tuan them code hien thi loaitb_id -- Ngay 23/07/2010
                            //Tuan them code hien thi diachi lapdat -- Ngay 29/07/2010
                            this.model.txtDiachiLD = ds[0].diachi_ld;
                            //Tuan them code hien thi goi cuoc DD -- Ngay 23/07/2010
                            if (this.dichvuvt_id == DichVuVienThong.DI_DONG && this.model.cboLoaihinhTB.value == LoaiHinhTB.DIDONGTRASAU) {
                                if (ds[0].goicuoc_id != "") {
                                    this.model.pnlDD.cboGoiCuocDD.value = Number(ds[0].goicuoc_id);
                                }
                            }
                            ///TuấnNA sửa code hiển thị combobox đài VT Full --- Ngày 19/10/2010
                            ///Trong trường hợp thuê bao không thuộc đơn vị dữ liệu của user, chương trình tự động chọn cbodaiVT --- > donvi_id(db_thuebao)
                            ///Giao dịch viên không được chọn.

                            //bangts.HT_DaiVT_Combobox(cboDaiVT, 0);
                            //txtMaTB.Text = ds[0].ma_tb;
                            // Tuan them code lay gia tri ma_tb cho khach hang CQ -- ngay 22/07/2010
                            this.ma_tb_cu = ds[0].ma_tb;
                            this.model.txtMaTBCu = ds[0].ma_tb;
                            this.model.txtTenTBCu = ds[0].ten_tb;
                            this.model.txtDiaChiTB = ds[0].diachi_tb;
                            this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_tc_id), 3);
                            //TuấnNA thêm code hiển thị thông tin email thuê bao -- theo y/c của A Thái --- Ngày 16/11/2010

                            this.model.cboDoituongTB.value = ds[0].doituong_id;
                            this.doituong_id = Number(ds[0].doituong_id);
                            this.dichvuvt_id = Number(await this.fn_tt_loaihinh_tb(2, this.loaitb_id));
                            // this.model.cboDichVuVT.value = this.dichvuvt_id;
                            this.model.cboDaiVT.value = Number(ds[0].donviql_id);
                            this.model.txtMaTB = ds[0].ma_tb;
                            this.model.txtTenTB = ds[0].ten_tb;
                            if (ds[0].ngay_sn != null && ds[0].ngay_sn != "")
                                this.model.txtNgaySinhTB = moment(ds[0].ngay_sn,"DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                            //TuấnNA thêm code hiển thị ngày SD -- ngày 14/12/2010
                            if (ds[0].ngay_sd != "")
                                this.model.dtpNgaySD = moment(ds[0].ngay_sd, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");

                            if (ds[0].mucuoctb_id != undefined && ds[0].mucuoctb_id != "") {
                                this.mucuoctb_id = Number(ds[0].mucuoctb_id);
                                let temps = await this.HT_MucCuocTB_Combobox(Number(ds[0].mucuoctb_id))
                                if (temps && temps.length > 0){
                                    this.model.cboMucCuocTB.list = temps.map((x)=>({id:x.mucuoctb_id, text:x.tenmuccuoc}))
                                    this.model.cboMucCuocTB.value = temps[0].mucuoctb_id

                                    this.model.cboMuccuocTBCu.list = temps.map((x)=>({id:x.mucuoctb_id, text:x.tenmuccuoc}))
                                    this.model.cboMuccuocTBCu.value = temps[0].mucuoctb_id
                                }
                            }

                            await this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id);
                            await this.LAY_DS_SUDUNG_DVGT(this.thuebao_id);
                            await this.LAY_DS_SUDUNG_DVK(this.thuebao_id, 1);
                            $("#txtTenTB").focus();
                        }

                        this.dsDBTBBC = await this.LAY_DS_DB_THUEBAO_THEO_TBID(this.thuebao_tc_id);
                        this.gan_thongso_theodanhba(1, this.dichvuvt_id, this.thuebao_id);
                        this.gan_thongso_theodanhba(2, this.dichvuvt_id, this.thuebao_tc_id);
                    }
                    //endregion
                }
                else {
                    this.$toast.error("Không tìm thấy thông tin về thuê bao: " + this.model.txtMaTB +
                        "\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !");
                }
            } catch (exp) {
                console.log(exp)
            }
        },
        async LAY_DS_DB_THUEBAO_THEO_TBID(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.bancheo_lay_ds_db_thuebao_theo_tbid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_CD_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_codinh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_CD_THEO_TBID_BC(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.bancheo_lay_ds_danhba_codinh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_IMS_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_ims(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_IMS_THEO_TBID_BC(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.bancheo_lay_ds_danhba_ims(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_ADSL_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_adsl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_ADSL_THEO_TBID_BC(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.bancheo_lay_ds_danhba_adsl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_TSL_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_tsl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_TSL_THEO_TBID_BC(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.bancheo_lay_ds_danhba_tsl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async gan_thongso_theodanhba(vkieu, vdichvu, vthuebaoid) {
            console.log("gan_thongso_theodanhba: " + vkieu +"|"+ vdichvu +"|"+ vthuebaoid)
            let ds = []

            if (vkieu == 1)
                this.thongso_ban = this.Reset_Thongso_Thuebao();
            else
                this.thongso_tc = this.Reset_Thongso_Thuebao();

            if (vdichvu == 1) {
                if (vkieu == 1) {
                    ds = await this.LAY_DS_DB_CD_THEO_TBID(vthuebaoid);
                    if (ds.length > 0) {
                        this.thongso_ban.trangbi_id = Number(ds[0].trangbi_id);
                        this.thongso_ban.doituong_id = Number(this.model.cboDoituongTB.value);
                        this.thongso_ban.muccuoctb_id = Number(this.model.cboMucCuocTB.value);
                    }
                }
                else
                {
                    ds = await this.LAY_DS_DB_CD_THEO_TBID_BC(vthuebaoid);
                    if (ds.length > 0) {
                        this.thongso_tc.trangbi_id = Number(ds[0].trangbi_id);
                        if (this.dsDBTBBC.length > 0) {
                            this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0].doituong_id);
                            this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0].mucuoctb_id);
                        }
                    }
                }
            }

            //dichvu vu co dinh
            if (vdichvu == 11) {
                if (vkieu == 1) {
                    ds = await this.LAY_DS_DB_IMS_THEO_TBID(vthuebaoid);
                    if (ds.length > 0) {
                        this.thongso_ban.trangbi_id = Number(ds[0].trangbi_id);
                        this.thongso_ban.doituong_id = Number(this.model.cboDoituongTB.value);
                        this.thongso_ban.muccuoctb_id = Number(this.model.cboMucCuocTB.value);
                    }
                }
                else {
                    ds = await this.LAY_DS_DB_IMS_THEO_TBID_BC(vthuebaoid);
                    if (ds.length > 0) {
                        this.thongso_tc.trangbi_id = Number(ds[0].trangbi_id);
                        if (this.dsDBTBBC.length > 0) {
                            this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0].doituong_id);
                            this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0].mucuoctb_id);
                        }
                    }
                }
            }

            //dich vu megwan metronet
            if (vdichvu == 8) {
                await this.Gan_thongso_bancheo_thuebaoid(this.thuebao_id, this.thuebao_tc_id, this.tinh.tinhthicong_id);
            }          
                        
            //dich vu băng rộng
            if (vdichvu == 4) {
                if (vkieu == 1) {
                    ds = await this.LAY_DS_DB_ADSL_THEO_TBID(vthuebaoid);
                    if (ds.length > 0) {
                        this.thongso_ban.trangbi_id = Number(ds[0].trangbi_id);
                        this.thongso_ban.tocdo_id = Number(ds[0].tocdo_id);
                        this.thongso_ban.tocdothuc_id = Number(ds[0].tocdo_id);
                        this.thongso_ban.muccuoc_id = Number(ds[0].muccuoc_id);
                        this.thongso_ban.kieutra_id = Number(ds[0].kieutra_id);
                        this.thongso_ban.thietbidc_id = Number(ds[0].thietbidc_id);
                        this.thongso_ban.doituong_id = Number(this.model.cboDoituongTB.value);
                        this.thongso_ban.muccuoctb_id = Number(this.model.cboMucCuocTB.value);
                    }
                }
                else
                {
                    ds = await this.LAY_DS_DB_ADSL_THEO_TBID_BC(vthuebaoid);
                    if (ds.length > 0) {
                        this.thongso_tc.trangbi_id = Number(ds[0].trangbi_id);
                        this.thongso_tc.tocdo_id = Number(ds[0].tocdo_id);
                        this.thongso_tc.tocdothuc_id = Number(ds[0].tocdo_id);
                        this.thongso_tc.muccuoc_id = Number(ds[0].muccuoc_id);
                        this.thongso_tc.kieutra_id = Number(ds[0].kieutra_id);
                        this.thongso_tc.thietbidc_id = Number(ds[0].thietbidc_id);
                        if (this.dsDBTBBC.length > 0) {
                            this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0].doituong_id);
                            this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0].mucuoctb_id);
                        }
                    }
                }
            }

            //dich vu TSL
            if (vdichvu == 9) {
                if (vkieu == 1) {
                    ds = await this.LAY_DS_DB_TSL_THEO_TBID(vthuebaoid);
                    if (ds.length > 0) {
                        this.thongso_ban.trangbi_id = Number(ds[0].trangbi_id);
                        this.thongso_ban.tocdo_id = Number(ds[0].tocdo_id);
                        this.thongso_ban.loaikenh_id = Number(ds[0].loaikenh_id);
                        this.thongso_ban.thietbidc_id = Number(ds[0].thietbidc_id);
                        this.thongso_ban.thietbidc_id_cuoi = Number(ds[1].thietbidc_id);
                        this.thongso_ban.doituong_id = Number(this.model.cboDoituongTB.value);
                        this.thongso_ban.muccuoctb_id = Number(this.model.cboMucCuocTB.value);
                    }
                }
                else
                {
                    ds = await this.LAY_DS_DB_TSL_THEO_TBID_BC(vthuebaoid);
                    if (ds.length > 0) {
                        this.thongso_tc.trangbi_id = Number(ds[0].trangbi_id);
                        this.thongso_tc.tocdo_id = Number(ds[0].tocdo_id);
                        this.thongso_tc.loaikenh_id = Number(ds[0].loaikenh_id);
                        this.thongso_tc.thietbidc_id = Number(ds[0].thietbidc_id);
                        this.thongso_tc.thietbidc_id_cuoi = Number(ds[1].thietbidc_id);
                        if (this.dsDBTBBC.length > 0) {
                            this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0].doituong_id);
                            this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0].mucuoctb_id);
                        }
                    }
                }
            }
        },
        async LAY_THONGSO_THUEBAO_THUEBAOID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_thongso_thuebao_thuebaoid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_THONGSO_THUEBAO_THUEBAOID_BC(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.bancheo_lay_thongso_thuebao_thuebaoid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Gan_thongso_bancheo_thuebaoid(vthuebao_ban, vthuebao_tc, tinh_tc_id) {
            let exp_text = "";
            let ds = await this.LAY_THONGSO_THUEBAO_THUEBAOID(vthuebao_ban)
            if (ds.length > 0) {
                try {
                    exp_text = "KIEULD_ID";
                    this.thongso_ban.kieuld_id = Number(ds[0].kieuld_id);
                    exp_text = "doituong_id";
                    this.thongso_ban.doituong_id = Number(ds[0].doituong_id);
                    exp_text = "chuquan_id";
                    this.thongso_ban.chuquan_id = Number(ds[0].chuquan_id);
                    exp_text = "chuquanld_id";
                    this.thongso_ban.chuquanld_id = Number(ds[0].chuquanld_id);
                    exp_text = "congnghe_id";
                    this.thongso_ban.congnghe_id = Number(ds[0].congnghe_id);
                    exp_text = "kieutra_id";
                    this.thongso_ban.kieutra_id = Number(ds[0].kieutra_id);
                    exp_text = "loaikenh_id";
                    this.thongso_ban.loaikenh_id = Number(ds[0].loaikenh_id);
                    exp_text = "loaimd_id";
                    this.thongso_ban.loaimd_id = Number(ds[0].loaimd_id);
                    exp_text = "ma_doicap";
                    this.thongso_ban.ma_doicap = ds[0].ma_doicap;
                    exp_text = "ma_tn";
                    this.thongso_ban.ma_tn = ds[0].ma_tn;
                    exp_text = "mucuoc_tb";
                    this.thongso_ban.muccuoc_id = Number(ds[0].mucuoc_tb);
                    exp_text = "mucuoctb_id";
                    this.thongso_ban.muccuoctb_id = Number(ds[0].mucuoctb_id);
                    exp_text = "thietbidc_id";
                    this.thongso_ban.thietbidc_id = Number(ds[0].thietbidc_id);
                    exp_text = "THIETBI_DICH";
                    this.thongso_ban.thietbidc_id_cuoi = Number(ds[0].thietbi_dich);
                    exp_text = "thoihan_id";
                    this.thongso_ban.thoihan_id = Number(ds[0].thoihan_id);
                    exp_text = "tocdo_id";
                    this.thongso_ban.tocdo_id = Number(ds[0].tocdo_id);
                    exp_text = "tocdo_isp";
                    this.thongso_ban.tocdo_isp = Number(ds[0].tocdo_isp);
                    exp_text = "tocdo_nix";
                    this.thongso_ban.tocdo_nix = Number(ds[0].tocdo_nix);

                    try {
                        this.thongso_ban.tocdo_pir_id = Number(ds[0].tocdo_pir_id);
                    } catch (e) {
                    }

                    exp_text = "tocdothuc_id";
                    this.thongso_ban.tocdothuc_id = Number(ds[0].tocdothuc_id);
                    exp_text = "trangbi_id";
                    this.thongso_ban.trangbi_id = Number(ds[0].trangbi_id);
                } catch (e) {
                    this.$toast.error("Thiếu dữ liệu trường " + exp_text.toUpperCase() + " ở tỉnh bán. Liên hệ admin để xử lý");
                }
            }

            if (vthuebao_tc != 0 && vthuebao_tc != -1) {
                let tinh_tc = await this.lay_thongtin_tinh_tc(tinh_tc_id)
                let ds_tc = await this.LAY_THONGSO_THUEBAO_THUEBAOID_BC(vthuebao_tc)
                if (ds_tc.length > 0) {
                    try {
                        exp_text = "kieuld_id";
                        this.thongso_tc.kieuld_id = Number(ds_tc[0].kieuld_id);
                        exp_text = "doituong_id";
                        this.thongso_tc.doituong_id = Number(ds_tc[0].doituong_id);
                        exp_text = "chuquan_id";
                        this.thongso_tc.chuquan_id = Number(ds_tc[0].chuquan_id);
                        exp_text = "chuquanld_id";
                        this.thongso_tc.chuquanld_id = Number(ds_tc[0].chuquanld_id);
                        exp_text = "congnghe_id";
                        this.thongso_tc.congnghe_id = Number(ds_tc[0].congnghe_id);
                        exp_text = "kieutra_id";
                        this.thongso_tc.kieutra_id = Number(ds_tc[0].kieutra_id);
                        exp_text = "loaikenh_id";
                        this.thongso_tc.loaikenh_id = Number(ds_tc[0].loaikenh_id);
                        exp_text = "loaimd_id";
                        this.thongso_tc.loaimd_id = Number(ds_tc[0].loaimd_id);
                        exp_text = "ma_doicap";
                        this.thongso_tc.ma_doicap = ds_tc[0].ma_doicap;
                        exp_text = "ma_tn";
                        this.thongso_tc.ma_tn = ds_tc[0].ma_tn;
                        exp_text = "mucuoc_tb";
                        this.thongso_tc.muccuoc_id = Number(ds_tc[0].mucuoc_tb);
                        exp_text = "mucuoctb_id";
                        this.thongso_tc.muccuoctb_id = Number(ds_tc[0].mucuoctb_id);
                        exp_text = "thietbidc_id";
                        this.thongso_tc.thietbidc_id = Number(ds_tc[0].thietbidc_id);
                        exp_text = "THIETBI_DICH";
                        this.thongso_tc.thietbidc_id_cuoi = Number(ds_tc[0].THIETBI_DICH);
                        exp_text = "thoihan_id";
                        this.thongso_tc.thoihan_id = Number(ds_tc[0].thoihan_id);
                        exp_text = "tocdo_id";
                        this.thongso_tc.tocdo_id = Number(ds_tc[0].tocdo_id);
                        exp_text = "tocdo_isp";
                        this.thongso_tc.tocdo_isp = Number(ds_tc[0].tocdo_isp);
                        exp_text = "tocdo_nix";
                        this.thongso_tc.tocdo_nix = Number(ds_tc[0].tocdo_nix);

                        try {
                            this.thongso_tc.tocdo_pir_id = Number(ds_tc[0].tocdo_pir_id);
                        } catch (e) {
                        }

                        exp_text = "tocdothuc_id";
                        this.thongso_tc.tocdothuc_id = Number(ds_tc[0].tocdothuc_id);
                        exp_text = "trangbi_id";
                        this.thongso_tc.trangbi_id = Number(ds_tc[0].trangbi_id);
                    } catch (e) {
                        this.$toast.error("Thiếu dữ liệu trường " + exp_text.toUpperCase() + " thuê bao " + vthuebao_tc + " ở tỉnh thi công (" + tinh_tc.tentat_ccbs + "). Liên hệ admin để xử lý");
                    }
                }
            }
        },
        async LAY_DS_DB_DD_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_dd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_MEGAWAN_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_megawan(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_GP_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_gp(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_MEGAEYES_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_megaeys(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DB_CNTT_THEO_TBID(thueBaoId) {
            let result = []
            try {
                let params = {
                    "thueBaoId": thueBaoId
                }
                let response = await api.lay_ds_danhba_cntt(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiTTDBMoRong(thuebao_id, dichvuvt_id) {
            let ds = []
            switch (dichvuvt_id) {
                case DichVuVienThong.CO_DINH:
                    ds = await this.LAY_DS_DB_CD_THEO_TBID(thuebao_id);
                    break;
                case DichVuVienThong.DI_DONG:
                    ds = await this.LAY_DS_DB_DD_THEO_TBID(thuebao_id);
                    break;
                case DichVuVienThong.TSL:
                case DichVuVienThong.NOIBO_2DIEM:
                    ds = await this.LAY_DS_DB_TSL_THEO_TBID(thuebao_id);
                    break;
                case DichVuVienThong.ADSL:
                case DichVuVienThong.EMAIL:
                    ds = await this.LAY_DS_DB_ADSL_THEO_TBID(thuebao_id);
                    break;
                case DichVuVienThong.MEGAWAN:
                    ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID(thuebao_id);
                    break;
                case DichVuVienThong.GPHONE:
                    ds = await this.LAY_DS_DB_GP_THEO_TBID(thuebao_id);
                    break;
                case DichVuVienThong.IMS:
                    ds = await this.LAY_DS_DB_IMS_THEO_TBID(thuebao_id);
                    break;
                case DichVuVienThong.MEGA_EYES:
                    ds = await this.LAY_DS_DB_MEGAEYES_THEO_TBID(thuebao_id);
                    break;
                case DichVuVienThong.DICHVU_CNTT:
                case DichVuVienThong.ANTOAN_BAOMAT_TT:
                case DichVuVienThong.TRUNGTAM_DULIEU:
                case DichVuVienThong.HOINGHI_TRUYENHINH:
                    ds = await this.LAY_DS_DB_CNTT_THEO_TBID(thuebao_id);
                    break;
                default:
                    ds = await this.LAY_DS_DB_CD_THEO_TBID(thuebao_id);
                    break;
            }

            if (ds.length > 0) {
                switch (dichvuvt_id) {
                    case DichVuVienThong.CO_DINH:
                        if (ds[0].trangbi_id != undefined && ds[0].trangbi_id != "")
                            this.trangbi_id = Number(ds[0].trangbi_id);
                        else
                            this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;//khách hàng tự trang bị                            
                        break;

                    case DichVuVienThong.DI_DONG:
                        if (ds[0].goicuoc_id != "") {
                            this.model.pnlDD.cboGoiCuocDD.value = Number(ds[0].goicuoc_id);
                        }
                        this.model.pnlDD.txtSimCard = ds[0].simcard;
                        if (ds[0].trangbi_id != undefined && ds[0].trangbi_id != "")
                            this.model.pnlDD.cboTrangBiDD.value = ds[0].trangbi_id;
                        else
                            this.model.pnlDD.cboTrangBiDD.value = TRANGBI.KHACHHANG_TU_TRANGBI;//khách hàng tự trang bị                                                 
                        break;

                    case DichVuVienThong.ADSL:
                    case DichVuVienThong.EMAIL:
                        this.model.pnlAdsl.txtMaTB_TN = ds[0].matb_tn;
                        this.model.pnlAdsl.txtEmailAdsl = ds[0].email;
                        this.model.pnlAdsl.cboGoiCuocAdsl.value = Number(ds[0].tocdo_id);
                        this.model.pnlAdsl.cboMucCuoc.value = Number(ds[0].muccuoc_id);
                        if (ds[0].trangbi_id != undefined && ds[0].trangbi_id != "")
                            this.model.pnlAdsl.cboTrangBiADSL.value = ds[0].trangbi_id;
                        else
                            this.model.pnlAdsl.cboTrangBiADSL.value = TRANGBI.KHACHHANG_TU_TRANGBI;//khách hàng tự trang bị
                        break;

                    case DichVuVienThong.MEGA_EYES:
                        this.model.pnlAdsl.cboGoiCuocAdsl.value = Number(ds[0].tocdo_id);
                        if (ds[0].trangbi_id != undefined && ds[0].trangbi_id != "")
                            this.model.pnlAdsl.cboTrangBiADSL.value = ds[0].trangbi_id;
                        else
                            this.model.pnlAdsl.cboTrangBiADSL.value = TRANGBI.KHACHHANG_TU_TRANGBI;//khách hàng tự trang bị
                        break;

                    case DichVuVienThong.TSL:
                    case DichVuVienThong.NOIBO_2DIEM:
                        this.model.pnlTSL.cboTocDoKenhTSL.value = Number(ds[0].tocdo_id);
                        this.model.pnlTSL.cboLoaiKenhTSL.value = Number(ds[0].loaikenh_id);
                        this.model.pnlTSL.cboThoiHan.value = Number(ds[0].thoihan_id);
                        this.model.pnlTSL.cboTrangBi.value = Number(ds[0].trangbi_id);
                        this.model.pnlTSL.cboChuQuan.value = Number(ds[0].chuquan_id);
                        this.model.pnlTSL.cboLoaiTBTSL.value = Number(ds[0].thietbidc_id);
                        this.model.pnlTSL.txtMaHD_TTKD_TSL = ds[0].ma_pl;

                        for (let i = 0; i < ds.length; i++) {
                            if (Number(ds[i].daucuoi_id) == 1)//Nếu là điểm đầu
                                this.model.pnlTSL.txtDiaChiDau = this.ChuanHoaTen(ds[i].diachi);
                            else
                                this.model.pnlTSL.txtDiaChiCuoi = this.ChuanHoaTen(ds[i].diachi);
                        }
                        break;

                    case DichVuVienThong.MEGAWAN:
                        ///TuấnNA thêm code hiển thị các thông tin của thuê bao MGWan --- Ngày 12/09/2010                                                
                        if (ds[0].tocdo_id != "")
                            this.model.pnlWan.cboTocDo.value = Number(ds[0].tocdo_id);
                        if (ds[0].loaimd_id != "")
                            this.model.pnlWan.cboLoaiModem.value = Number(ds[0].loaimd_id);
                        if (ds[0].loaikenh_id != "")
                            this.model.pnlWan.cboLoaiKenhWan.value = Number(ds[0].loaikenh_id);
                        if (ds[0].thietbidc_id != "")
                            this.model.pnlWan.cboLoaiTBiWan.value = Number(ds[0].thietbidc_id);
                        if (ds[0].thoihan_id != "")
                            this.model.pnlWan.cboThoihanWan.value = Number(ds[0].thoihan_id);
                        if (ds[0].chuquan_id != "")
                            this.model.pnlWan.cboChuQuanWan.value = Number(ds[0].chuquan_id);

                        this.model.pnlWan.txtMaHD_TTKD_MGW = ds[0].ma_pl;
                        this.model.pnlWan.txtSiteID = ds[0].site_id;
                        if (ds[0].tocdo_pir_id != "")
                            this.model.pnlWan.cboTocDo_PIR.value = Number(ds[0].tocdo_pir_id);
                        break;

                    case DichVuVienThong.GPHONE:
                        this.model.pnlGphone.chkVSVXGP = (ds[0].mc_tbgp_id == "1");
                        this.model.pnlGphone.txtSosimGP = ds[0].simcard;
                        break;

                    case DichVuVienThong.IMS:
                        if (ds[0].thietbi_id != undefined && ds[0].thietbi_id != "")
                            this.model.pnlIMS.cboThietBi.value = Number(ds[0].thietbi_id);
                        if (ds[0].trangbi_id != undefined && ds[0].trangbi_id != "")
                            this.model.pnlIMS.cboTrangBiIMS.value = ds[0].trangbi_id;
                        break;

                    case DichVuVienThong.DICHVU_CNTT:
                    case DichVuVienThong.ANTOAN_BAOMAT_TT:
                    case DichVuVienThong.TRUNGTAM_DULIEU:
                    case DichVuVienThong.HOINGHI_TRUYENHINH:
                        if (ds.length > 0) {
                            if (ds[0].linhvuc_id != undefined && ds[0].linhvuc_id != "")
                                this.linhvuc_id = Number(ds[0].linhvuc_id);
                        }
                        this.tocdo_id = 0;
                        break;
                        
                    default:
                        break;
                }
            }
        },
        async LAY_DS_DIACHI_THEO_HDKH_ID(hdKhId) {
            let result = []
            try {
                let params = {
                    "hdKhId": hdKhId
                }
                let response = await api.lay_ds_diachi_theo_hdkh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiDS_HDTT_CBO(hdkh_id) {
            let ds = await this.LAY_DS_HDTT_THEO_HDKH_ID_1(hdkh_id)
            if (ds && ds.length > 0) {
                this.model.cboTT.list = ds.map((x)=>({id:x.hdtt_id, text:x.tentt}))
                this.model.cboTT.value = ds[0].hdtt_id
            } 
        },
        async LAY_DS_HDTT_THEO_HDKH_ID_1(in_hdkh_id) {
            let result = []
            try {
                let params = {
                    "in_hdkh_id": in_hdkh_id
                }
                let response = await api.lay_ds_hdtt_theo_hdkh_id_1(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdKhId) {
            let result = []
            try {
                let params = {
                    "hdKhId": hdKhId
                }
                let response = await api.lay_ds_hopdong_thuebao_theo_hdkh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_HOPDONG_TT_THEO_HDKH_ID(hdKhId) {
            let result = []
            try {
                let params = {
                    "hdKhId": hdKhId
                }
                let response = await api.lay_ds_hopdong_thanhtoan_theo_hdkh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_TINHTHICONG_THEO_THUEBAOID(thuebao_id) {
            let result = {}
            try {
                let params = {
                    "thuebao_id": thuebao_id
                }
                let response = await api.lay_tinh_theo_thuebaoid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
                    result = response.data.data[0]
                    result.tinhthicong_id = result.tinh_thicong
                } else {
                    this.$toast.error("Thuê bao kênh trắng nên không thể chuyển quyền bán chéo!")
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_THUEBAOID_THICONG(vtinh_id, vthuebao_id) {
            let result = -1
            try {
                let params = {
                    "vtinh_id": vtinh_id,
                    "vthuebao_id": vthuebao_id
                }
                let response = await api.lay_thuebaoid_thicong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
                    result = Number(response.data.data[0].thuebao_kn_id)
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DIACHI_THEO_HDTB_ID(hdtbId) {
            let result = []
            try {
                let params = {
                    "hdtbId": hdtbId
                }
                let response = await api.lay_ds_diachi_theo_hdtb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_MucCuocTB_Combobox(mucuoctb_id) {
            let dt = await this.lay_thongtin_ds_muccuoc_tb(mucuoctb_id)
            return dt
        },
        async LAY_DS_DBTB_THEO_TBID(in_thuebao_id) {
            let result = []
            try {
                let params = {
                    "in_thuebao_id": in_thuebao_id
                }
                let response = await api.lay_ds_dbtb_theo_tbid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_BIENDONG_THEO_HDTB_ID(hdtbId) {
            let result = []
            try {
                let params = {
                    "hdtbId": hdtbId
                }
                let response = await api.lay_biendong_thongtin_thuebao(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_TIENHD_KM_THEO_HDTB(hdtbId) {
            let result = []
            try {
                let params = {
                    "hdtbId": hdtbId
                }
                let response = await api.lay_tien_hopdong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiTTHopDongTB(ds) {
            if (ds.length > 0) {
                this.hdtb_id = Number(ds[0].hdtb_id);

                if (ds[0].thuebao_id != "")
                    this.thuebao_id = Number(ds[0].thuebao_id);
                else
                    this.thuebao_id = 0;

                if (ds[0].thuebao_cha_id != "")
                    this.thuebao_cha_id = Number(ds[0].thuebao_cha_id);
                else
                    this.thuebao_cha_id = 0;

                this.tinh = await this.LAY_TINHTHICONG_THEO_THUEBAOID(this.thuebao_id);
                if (this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0) {
                    this.thuebao_tc_id = await this.LAY_THUEBAOID_THICONG(this.tinh.tinhthicong_id, this.thuebao_id);
                }
                    
                this.model.txtMaTB = ds[0].ma_tb;
                this.ma_tb = this.model.txtMaTB;
                this.model.txtTenTB = this.ChuanHoaTen(ds[0].ten_tb);
                this.model.txtDiaChiTB = this.ChuanHoaTen(ds[0].diachi_tb);
                this.model.txtDiachiLD = this.ChuanHoaTen(ds[0].diachi_ld); //Tuan them code dia chi lap dat -- Ngay 29/07/2010
                this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_HDTB_ID(this.hdtb_id), 3);

                if (ds[0].quytrinh_id != undefined && ds[0].quytrinh_id != "")
                    this.quytrinh_id = Number(ds[0].quytrinh_id);
                else
                    this.quytrinh_id = 0;

                
                this.dichvuvt_id = Number(await this.fn_tt_loaihinh_tb(2, ds[0].loaitb_id));
                // this.model.cboDichVuVT.value = this.dichvuvt_id;

                //TuanNA kiểm tra xem hợp đồng này có thuê bao di động hay không -- Ngày 28/07/2010
                for (let i = 0; i < ds.length; i++) {
                    if (Number(ds[0].dichvuvt_id) == DichVuVienThong.DI_DONG && Number(ds[0].loaitb_id) == LoaiHinhTB.DIDONGTRASAU) {
                        if (Number(ds[0].status) == 1) {
                            this.check_tb_dd = true;
                            break;
                        }
                    }
                }

                ///Tuấn thêm code kiểm tra thuê bao thuộc hợp đồng hay phụ lục --- Ngày 19/08/2010
                this.TBHD_PL = Number(ds[0].tbhd_pl);
                this.loaitb_id = Number(ds[0].loaitb_id);
                console.log("7: " + Number(ds[0].loaitb_id))
                this.model.cboLoaihinhTB.value = this.loaitb_id;
                this.model.cboKieuLD.value = ds[0].kieuld_id;
                this.model.cboDoituongTB.value = ds[0].doituong_id;

                //Hiếu thêm kieuld_id,doituong_id
                this.kieuld_id = Number(ds[0].kieuld_id);
                this.doituong_id = Number(ds[0].doituong_id);
                this.model.cboTT.value = ds[0].hdtt_id;
                this.model.cboDaiVT.value = Number(ds[0].donvi_id);

                this.status = ds[0].status;
                this.model.txtGhiChuTB = ds[0].ghichu;

                this.m_dsThueBao_DichVu.dsdvgt_ban = await this.LAY_DS_THUEBAO_DICHVU(this.hdtb_id, this.thuebao_id, this.loaitb_id, 1);
                if (this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0) {
                    this.m_dsThueBao_DichVu.dsdvgt_tc = await this.LAY_DS_THUEBAO_DICHVU_BC(0, this.thuebao_tc_id, this.loaitb_id, 1)
                }

                this.HT_DichVuGT();

                if (ds[0].mucuoctb_id != undefined && ds[0].mucuoctb_id != "") {
                    let temps = await this.HT_MucCuocTB_Combobox(Number(ds[0].mucuoctb_id))
                    if (temps && temps.length > 0){
                        this.model.cboMucCuocTB.list = temps.map((x)=>({id:x.mucuoctb_id, text:x.tenmuccuoc}))
                        this.model.cboMucCuocTB.value = temps[0].mucuoctb_id

                        this.model.cboMuccuocTBCu.list = temps.map((x)=>({id:x.mucuoctb_id, text:x.tenmuccuoc}))
                        this.model.cboMuccuocTBCu.value = temps[0].mucuoctb_id
                    }
                }

                await this.LAY_DS_SUDUNG_DVK(this.thuebao_id, 1);
                //

                //Hiếu bổ sung lấy đơn vị đầu, đơn vị cuối ngày 06/09/2010
                if (ds[0].donvi_dau_id != "")
                    this.donvi_dau_id = Number(ds[0].donvi_dau_id);
                if (ds[0].donvi_cuoi_id != "")
                    this.donvi_cuoi_id = Number(ds[0].donvi_cuoi_id);
                //

                let ds_dbtb = await this.LAY_DS_DBTB_THEO_TBID(this.thuebao_id);
                if (ds_dbtb.length > 0 && ds_dbtb[0].ngay_sd != "")
                    this.model.dtpNgaySD = moment(ds_dbtb[0].ngay_sd, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                else
                    this.model.dtpNgaySD = this.ngay_sys;
                //
                if (ds[0].ngay_sn != null && ds[0].ngay_sn != "")
                    this.model.txtNgaySinhTB = moment(ds[0].ngay_sn,"DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                await this.HienThiTTMoRong(this.hdtb_id, this.dichvuvt_id);
                this.HienThiThongTinCu(await this.LAY_BIENDONG_THEO_HDTB_ID(this.hdtb_id));

                //Thực hiện lấy tiền --nhapt thêm
                this.ds_tien_khoanmuc = await this.LAY_TIENHD_KM_THEO_HDTB(this.hdtb_id);
                for (let i = 0; i < this.ds_tien_khoanmuc.length; i++)  {
                    if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU) {
                        this.dtien_dv = Number(this.ds_tien_khoanmuc[i].tien);
                        this.dvat_dv = Number(this.ds_tien_khoanmuc[i].vat);
                    }

                    if (this.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_CHUYENQUYEN) {
                        this.dtien_cq = Number(this.ds_tien_khoanmuc[i].tien);
                        this.dvat_cq = Number(this.ds_tien_khoanmuc[i].vat);
                    }
                }
                this.LayTongTien_HDTB();
            }
        },
        HienThiDanhSacHDTT(ds) {
            this.DsHopDongTT.data = []
            if (ds.length > 0) {
                for (let i = 0; i < ds.length; i++) {
                    let myRow = {}
                    myRow.stt = i+1
                    myRow.hdtt_id = ds[i].hdtt_id
                    myRow.ma_tt = ds[i].ma_tt
                    myRow.ten_tt = this.ChuanHoaTen(ds[i].ten_tt)
                    myRow.diachi_tt = this.ChuanHoaTen(ds[i].diachi_tt)
                    myRow.diachi_ct = this.ChuanHoaTen(ds[i].diachi_ct)
                    this.DsHopDongTT.data.push(myRow);
                }

                if (this.DsHopDongTT.data.length == 1)
                    this.enable.tsbtnXoaTT = false;
                else
                    this.enable.tsbtnXoaTT = true;
            }
        },
        async HienThiDanhSacHDTB(ds) {
            let tongtienhd = 0;
            let tongtientb = 0;
            this.DsHopDongTB.data = []
            this.DsHopDongTB.selected = {}
            if (ds.length > 0) {
                // Tuan lay du lieu cho bien TBHD_PL tu danh sach thue bao  -- Ngay 06/07/2010
                this.TBHD_PL = Number(ds[0].tbhd_pl);

                //Tuan them code lay khachhang_id cua khach hang dang chuyen quyen -- Ngay 06/07/2010
                let ds_khcu = await this.LAY_BIENDONG_THEO_HDTB_ID(Number(ds[0].hdtb_id));
                if (ds_khcu.length > 0) {
                    this.khachhang_id_cq = Number(ds_khcu[0].khachhang_id);
                }
                //End tuan

                for (let i = 0; i < ds.length; i++) {
                    let myRow = {}
                    tongtientb = Number(ds[i].tongtien);

                    myRow.stt = i+1
                    myRow.ma_tt = ds[i].ma_tt
                    myRow.hdtb_id = ds[i].hdtb_id
                    myRow.ma_tb = ds[i].ma_tb
                    myRow.ten_tb = this.ChuanHoaTen(ds[i].ten_tb)
                    myRow.diachi_ld = this.ChuanHoaTen(ds[i].diachi_ld)
                    myRow.tong_tien = this.formatNumber(tongtientb)
                    myRow.ten_dvvt = ds[i].ten_dvvt
                    myRow.dichvuvt_id = ds[i].dichvuvt_id
                    myRow.manv_tc = ds[i].manv_tc
                    myRow.status = ds[i].trangthai_dongbo
                    myRow.kieuld_id = ds[i].kieuld_id

                    tongtienhd = tongtienhd + tongtientb;
                    this.DsHopDongTB.data.push(myRow);
                }

                if (this.DsHopDongTB.data.length > 1)
                    this.enable.tsbtnXoaTB = true;
                else
                    this.enable.tsbtnXoaTB = false;

                this.enable.tsbtnThemTT = true;
            }

            this.model.txtTongTienHD = this.formatNumber(tongtienhd);
        },
        async LAY_DS_HDTB_XXX_THEO_HDTB_ID(hdtb_id, dichvuvt_id) {
            let result = []
            try {
                let response
                if (dichvuvt_id == DichVuVienThong.DI_DONG) {
                    let params = {
                        "ID": hdtb_id
                    }
                    response = await api.lay_ds_hdtb_dd_theo_hdtb_id(this.axios, params);
                } else {
                    let params = {
                        "hdtbId": hdtb_id
                    }
                    if (dichvuvt_id == DichVuVienThong.CO_DINH) {
                        response = await api.lay_ds_hopdong_thuebao_codinh(this.axios, params);
                    } else if (dichvuvt_id == DichVuVienThong.ADSL || dichvuvt_id == DichVuVienThong.EMAIL) {
                        response = await api.lay_ds_hopdong_thuebao_adsl(this.axios, params);
                    } else if (dichvuvt_id == DichVuVienThong.MEGAWAN) {
                        response = await api.lay_ds_hopdong_thuebao_mgwan(this.axios, params);
                    } else if (dichvuvt_id == DichVuVienThong.TSL || dichvuvt_id == DichVuVienThong.NOIBO_2DIEM) {
                        response = await api.lay_ds_hopdong_thuebao_tls(this.axios, params);
                    } else if (dichvuvt_id == DichVuVienThong.GPHONE) {
                        response = await api.lay_ds_hopdong_thuebao_gp(this.axios, params);
                    } else if (dichvuvt_id == DichVuVienThong.IMS) {
                        response = await api.lay_ds_hopdong_thuebao_ims(this.axios, params);
                    } else if (dichvuvt_id == DichVuVienThong.DICHVU_CNTT || dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
                        dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
                        response = await api.lay_ds_hopdong_thuebao_cntt(this.axios, params);
                    } else {
                        response = await api.lay_ds_hopdong_thuebao_codinh(this.axios, params);
                    }
                }
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiTTMoRong(hdtb_id, dichvuvt_id) {
            let ds = await this.LAY_DS_HDTB_XXX_THEO_HDTB_ID(hdtb_id, dichvuvt_id)
            if (ds.length > 0) {
                ///TuấnNA thêm code hiển thị thông tin các loại dịch vụ viễn thông : Di động,internet...
                ///--Ngày 09/08/2010
                switch (dichvuvt_id) {
                    case DichVuVienThong.CO_DINH:
                        if (ds[0].trangbi_id != "")
                            this.trangbi_id = Number(ds[0].trangbi_id);
                        else
                            this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI; // khách hàng tự trang bị                     
                        break;
                    case DichVuVienThong.DI_DONG:
                        ///TuấnNA thêm code hiển thị các thông tin về di động
                        ///--Ngày 09/08/2010                        
                        this.model.pnlDD.cboGoiCuocDD.value = Number(ds[0].goicuoc_id);
                        this.model.pnlDD.cboDTVinaPhone.value = Number(ds[0].doituong_id_neo);
                        this.model.pnlDD.txtSimCard = ds[0].simcard;
                        break;

                    ///TuấnNA thêm code hiển thị thông tin mở rộng MGWan
                    ///---Ngày 11/09/2010
                    case DichVuVienThong.MEGAWAN:
                        if (ds[0].thoihan_id != "")
                            this.model.pnlWan.cboThoihanWan.value = Number(ds[0].thoihan_id);
                        this.model.pnlWan.cboTocDo.value = Number(ds[0].tocdo_id);
                        this.model.pnlWan.cboLoaiModem.value = Number(ds[0].loaimd_id);
                        this.model.pnlWan.cboLoaiKenhWan.value = Number(ds[0].loaikenh_id);
                        this.model.pnlWan.cboLoaiTBiWan.value = Number(ds[0].thietbidc_id);

                        this.model.pnlWan.txtMaHD_TTKD_MGW = ds[0].ma_pl;
                        this.model.pnlWan.txtSiteID = ds[0].site_id;
                        if (ds[0].tocdo_pir_id != "")
                            this.model.pnlWan.cboTocDo_PIR.value = Number(ds[0].tocdo_pir_id);
                        break;
                    case DichVuVienThong.ADSL:
                    case DichVuVienThong.EMAIL:
                        this.model.pnlAdsl.txtEmailAdsl = ds[0].email;
                        this.model.pnlAdsl.cboGoiCuocAdsl.value = Number(ds[0].tocdo_id);
                        this.model.pnlAdsl.cboMucCuoc.value = Number(ds[0].muccuoc_id);
                        break;
                    case DichVuVienThong.TSL:
                    case DichVuVienThong.NOIBO_2DIEM:
                        ///TuấnNA thêm code hiển thị thông tin hdtb_TSL --- NGày 01/09/2010
                        this.model.pnlTSL.cboChuQuan.value = ds[0].chuquan_id;
                        this.model.pnlTSL.cboTocDoKenhTSL.value = ds[0].tocdo_id;
                        this.model.pnlTSL.cboLoaiKenhTSL.value = ds[0].loaikenh_id;
                        this.model.pnlTSL.cboTrangBi.value = ds[0].trangbi_id;
                        this.model.pnlTSL.cboThoiHan.value = ds[0].thoihan_id;
                        this.model.pnlTSL.cboLoaiTBTSL.value = ds[0].thietbidc_id;
                        this.model.pnlTSL.txtMaHD_TTKD_TSL = ds[0].ma_pl;

                        for (let i = 0; i < ds.Tables[0].Rows.Count; i++) {
                            if (ds[i].daucuoi_id == "1") //Nếu là điểm đầu
                                this.model.pnlTSL.txtDiaChiDau = this.ChuanHoaTen(ds[i].diachi);
                            else
                                this.model.pnlTSL.txtDiaChiCuoi = this.ChuanHoaTen(ds[i].diachi);
                        }
                        break;
                    case DichVuVienThong.GPHONE:
                        this.model.pnlGphone.chkVSVXGP = (ds[0].mc_tbgp_id == "1");
                        this.model.pnlGphone.txtSosimGP = ds[0].simcard;
                        break;
                    case DichVuVienThong.IMS:
                        if (ds[0].thietbi_id != undefined && ds[0].thietbi_id != "")
                            this.model.pnlIMS.cboThietBi.value = ds[0].thietbi_id;
                        if (ds[0].trangbi_id != undefined && ds[0].trangbi_id != "")
                            this.model.pnlIMS.cboTrangBiIMS.value = ds[0].trangbi_id;
                        break;
                    case DichVuVienThong.DICHVU_CNTT:
                    case DichVuVienThong.ANTOAN_BAOMAT_TT:
                    case DichVuVienThong.TRUNGTAM_DULIEU:
                    case DichVuVienThong.HOINGHI_TRUYENHINH:
                        if (ds.length > 0) {
                            if (ds[0].linhvuc_id != "")
                                this.linhvuc_id = Number(ds[0].linhvuc_id);
                        } else
                            this.linhvuc_id = 0;

                        this.tocdo_id = 0;
                        break;
                    default:
                        break;
                }
            }
        },
        HienThiThongTinCu(ds) {
            if (ds.length > 0) {
                this.model.txtTenKHCu = this.ChuanHoaTen(ds.ten_kh);
                this.model.txtDiaChiKHCu = this.ChuanHoaTen(ds.diachi_kh);
                this.model.txtMaHDCu = ds[0].ma_hd;
                
                this.ma_tt_cu = ds[0].ma_tt;// Tuan them code lay gia tri ma_tt_cu cua khach hang CQ -- Ngay 22/07/2010
                this.ten_tt_cu = this.ChuanHoaTen(ds[0].ten_tt);// Tuan them code lay gia tri ten_tt_cu cua khach hang CQ -- Ngay 22/07/2010
                this.diachi_tt_cu = this.ChuanHoaTen(ds[0].diachi_tt);// Tuan them code lay gia tri diachi_tt_cu cua khach hang CQ -- Ngay 22/07/2010

                // Tuan them code lay gia tri ten_tb_cu cho khach hang CQ -- ngay 22/07/2010
                this.ten_tb_cu = this.ChuanHoaTen(ds[0].ten_kh);
                this.model.txtMaKHCu = ds.ma_kh;

                ///TuấnNA thêm code lấy dữ liệu cho biến ma_kh_cu -- Ngày 06/08/2010
                this.ma_kh_cu = ds[0].ma_kh;
                this.model.txtMaTBCu = ds[0].ma_tb;
                this.model.txtTenTBCu = this.ChuanHoaTen(ds[0].ten_tb);
                if (ds[0].mucuoctb_id != undefined && ds[0].mucuoctb_id != "") {
                    this.model.cboMuccuocTBCu.value = Number(ds[0].mucuoctb_id);
                }

                this.doituong_id_bd = Number(ds[0].doituong_id);
            }
        },
        async LAY_DS_HOPDONG_THEO_MA_GD(in_ma_gd, in_loaihd_id, in_tthd_id, in_donvi_dl_id) {
            let result = []
            try {
                let params = {
                    "in_ma_gd": in_ma_gd,
                    "in_loaihd_id": in_loaihd_id,
                    "in_tthd_id": in_tthd_id,
                    "in_donvi_dl_id": in_donvi_dl_id
                }
                let response = await api.lay_ds_hopdong_theo_ma_gd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async txtMaGD_KeyPress(e) {
            if (e.keyCode == 13) {
                try {
                    this.loading(true)
                    if (this.model.txtMaGD != "") {
                        await this.HienThiTTHopDongKH(await this.LAY_DS_HOPDONG_THEO_MA_GD(this.model.txtMaGD, LoaiHopDong.CHUYEN_QUYEN, TrangThaiHD.MOI, 0));
                    }
                } catch (e) {
                    console.log(e)
                } finally {
                    this.loading(false)
                } 
            }
        },
        async txtMaKH_KeyPress(e) {
            if (e.keyCode == 13) {
                try {
                    this.loading(true)
                    if (this.model.txtMaKH != "") {
                        await this.HienThiTT_DanhBa_KH_NhanCQ(this.model.txtMaKH);
                        $("#txtMaHD").focus();
                    } else {
                        this.$toast.error("Bạn chưa nhập mã khách hàng !");         
                        this.model.txtMaKH = "";
                        $("#txtMaKH").focus();
                    }
                } catch (e) {
                    console.log(e)
                } finally {
                    this.loading(false)
                }           
            }
        },
        async LAY_DB_THEO_MATB(ma_tb, donvi_dl_id, dichvuvt_id) {
            let result = []
            try {
                let params = {
                    "ma_tb": ma_tb,
                    "donvi_dl_id": donvi_dl_id,
                    "dichvuvt_id": dichvuvt_id,
                }
                let response = await api.lay_db_theo_matb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_DIACHI_THEO_DBTTID(dbttid) {
            let result = []
            try {
                let params = {
                    "dbttid": dbttid
                }
                let response = await api.lay_ds_diachi_theo_dbttid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_KHACHHANGID_THICONG(khachhangbanid) {
            let result = 0
            try {
                let params = {
                    "khachhangbanid": khachhangbanid,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.lay_khachhangid_thicong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiTT_DanhBa_KH_NhanCQ(ma_tb) {
            try {
                this.clear_manvtc = "0";///Kiểm tra để xử lý clear mã nhân viên thu cước--- Ngày 04/10/2010
                let ds = await this.LAY_DB_THEO_MATB(ma_tb, 0, 0);
                if (ds.length > 0) {
                    //Tuan bo sung code kiem tra khach hang da ky hop dng goc chua -- Ngay 07/07/2010
                    if (Number(ds[0].ky_hd) == 0) {
                        this.$toast.error("Khách hàng có mã: " + this.model.txtMaKH + " chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc");
                    } else {
                        //Tuan them bien TBHD_PL = 0 --> khach hang nhan quyen la khach hang cu -- Ngay 07/07/2010
                        this.TBHD_PL = 0;//In Phu Luc
                        //Thông tin khách hàng
                        if (ds[0].ma_kh == this.ma_kh_cu) {
                            ///Tuấn Sửa + Comment đoạn code sử dụng chức năng chuyển quyền để tách nhập thuê bao. --- Ngày 19/10/2010
                            this.$toast.error("Khách hàng nhận quyền và khách hàng chuyển quyền phải khác nhau !");
                            this.model.txtMaKH = "";
                            return;
                        } else {
                            this.model.txtMaKH = ds[0].ma_kh;
                            this.model.txtMaHD = ds[0].ma_hd;
                            if (ds[0].ngaylap_hd != "")
                                this.model.dtpNgayLapHDNQ = moment(ds[0].ngaylap_hd, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                            else
                                this.model.dtpNgayLapHDNQ = this.ngay_sys

                            //tuan 02/07/2010 them du lieu hien thi
                            this.model.cboQuocTich.value = ds[0].quoctich_id;
                            this.model.cboDanToc.value = ds[0].dantoc_id;

                            this.eventNganhNghe = false
                            this.model.cboNganhNgheLoc.value = ds[0].nganhnghe_id;
                            this.nganhnghe_id = Number(ds[0].nganhnghe_id);
                            this.eventNganhNghe = true

                            this.model.cboGiayToKH.value = ds[0].loaigt_id;
                            // end tuan
                            this.khachhang_id = Number(ds[0].khachhang_id);
                            this.khachhang_tc_id = await this.LAY_KHACHHANGID_THICONG(this.khachhang_id);
                            this.model.txtTenKH = this.ChuanHoaTen(ds[0].ten_kh);
                            this.model.txtDiaChiKH = this.ChuanHoaTen(ds[0].diachi_kh);
                            if (ds[0].loaikh_id != "")
                                this.model.cboLoaiKH.value = Number(ds[0].loaikh_id);

                            try {
                                let strPLKH = await this.fn_tt_db_khachhang(2, this.khachhang_id);
                                if (strPLKH != "-1") {
                                    this.PHANLOAIKH_ID = Number(strPLKH);
                                }
                            } catch (e) {
                            }

                            if (ds[0].diachi_ndd != undefined && ds[0].diachi_ndd != "")
                                this.model.txtDiaChiNDD = this.ChuanHoaTen(ds[0].diachi_ndd);
                            else
                                this.model.txtDiaChiNDD = this.model.txtDiaChiKH;

                            if (this.kieukh_canhan && ds[0].nguoi_dd != undefined && ds[0].nguoi_dd == "")
                                this.model.txtNguoiDD = this.model.txtTenKH;
                            else
                                this.model.txtNguoiDD = this.ChuanHoaTen(ds[0].nguoi_dd);

                            this.model.txtChucDanhDD = ds[0].chucdanh;
                            if (ds[0].ngayhethan_gt != null && ds[0].ngayhethan_gt != "")
                                this.model.txtNgayHetHan = moment(ds[0].ngayhethan_gt, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                            else
                                this.model.txtNgayHetHan = "";
                            this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBKHID(this.khachhang_id), 1);
                            this.model.txtTelKH = ds[0].so_dt;
                            this.model.txtFaxKH = ds[0].so_fax;
                            this.model.txtHoKhau = this.ChuanHoaTen(ds[0].hokhau);
                            this.model.txtDacTrung = ds[0].dactrung;
                            this.model.txtSoTKKH = ds[0].stk;
                            this.model.txtMaSTKH = ds[0].mst;

                            if (ds[0].nganhang_id != "") {
                                this.model.chkNganHangKH = true;
                                this.model.cboNganHangKH.value = ds[0].nganhang_id;
                            } else
                                this.model.chkNganHangKH = false;

                            //tuan them thong tin hien thi khach hang nhan quyen --Ngay 05/07/2010
                            if (ds[0].gioitinh == "1") {
                                this.model.rdoGT = "rdoGTNu"
                            } else if (ds[0].gioitinh == "0")
                                this.model.rdoGT = "rdoGTNam"
                            else if (ds[0].gioitinh == null)
                                this.model.rdoGT = "rdoGTNone"

                            this.model.txtGhiChuKH = ds[0].ghichu;

                            //end tuan
                            if (ds[0].ngay_hk != null && ds[0].ngay_hk != "")
                                this.model.txtNgayCapHK = moment(ds[0].ngay_hk,"DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                            else
                                this.model.txtNgayCapHK = "";

                            this.model.txtNoiCapHK = this.ChuanHoaTen(ds[0].noicap_hk);
                            if (ds[0].khlon_id != "")
                                this.model.cboKhachHangLon.value = Number(ds[0].khlon_id);

                            if (ds[0].ngay_sn != null && ds[0].ngay_sn != "") {
                                this.model.txtNgaySN = moment(ds[0].ngay_sn,"DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                                $("#txtNgaySN").focus();
                            } else {
                                this.model.txtNgaySN = "";
                            }
                                
                            this.model.txtSoGT = ds[0].so_gt;
                            if (ds[0].ngaycap != null && ds[0].ngaycap != "")
                                this.model.txtNgayCap = moment(ds[0].ngaycap, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY");
                            else
                                this.model.txtNgayCap = "";

                            if (ds[0].loaigt_id != "")
                                this.model.cboGiayToKH.value = ds[0].loaigt_id;
                            this.model.txtNoiCap = this.ChuanHoaTen(ds[0].noicap);

                            if (ds[0].ma_duan != undefined)
                                this.model.txtMaDuAn = ds[0].ma_duan;
						
                            //TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
                            if (ds[0].tnc1_id != "") {
                                this.TNC1_ID = Number(ds[0].tnc1_id);
                            }
                            if (ds[0].tnc2_id != "") {
                                this.TNC2_ID = Number(ds[0].tnc2_id);
                            }
                            if (ds[0].tnc3_id != "") {
                                this.TNC3_ID = Number(ds[0].tnc3_id);
                            }
                            if (ds[0].tnc4_id != "") {
                                this.TNC4_ID = Number(ds[0].tnc4_id);
                            }
                            //
                            
                            //Thông tin thanh toán
                            this.model.txtMaTT = ds[0].ma_tt;
                            this.thanhtoan_id = Number(ds[0].thanhtoan_id);

                            let dt_madd = await this.HT_COMBO_MA_DAIDIEN(this.thanhtoan_id);
                            if (dt_madd && dt_madd.length > 0){
                                this.model.cboMaDD.list = dt_madd.map((x)=>({id:x.ma_tb, text:x.ten_ht}))
                                this.model.cboMaDD.value = ds[0].matb_dd;
                            }

                            this.model.txtTenTT = this.ChuanHoaTen(ds[0].ten_tt);

                            /// TuanNA thêm code hiển thị thông tin  -- Ngày 06/08/2010
                            this.model.txtMaTBDD = ds[0].matb_dd;
                            this.model.txtDiaChiTT = this.ChuanHoaTen(ds[0].diachi_tt);
                            this.model.txtDiaChiCT = this.ChuanHoaTen(ds[0].diachi_ct);
                            this.model.txtDiaChiBC = this.ChuanHoaTen(ds[0].diachi_bc);
                            this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTTID(this.thanhtoan_id), 2);

                            //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
                            this.DV_QHNS = ds[0].dv_qhns;
                            this.MACHUONG = ds[0].machuong;
                            this.MN_KT = ds[0].mn_kt;
                            this.MA_NDKT = ds[0].ma_ndkt;
                            this.MN_NSNN = ds[0].mn_nsnn;
                            if (ds[0].tien_dm != "")
                                this.TIEN_DM = Number(ds[0].tien_dm);
                            else
                                this.TIEN_DM = 0;
                            //

                            /////TuấnNA thêm code lấy đơn vị quản lý cũ ứng với mã thanh toán khách hàng đang sử dụng
                            /////--- Ngày 01/09/2010
                            this.model.txtMST_TT = ds[0].mst_tt;
                            this.model.txtSTK_TT = ds[0].stk_tt;
                            this.model.txtChu_TK = ds[0].chu_tk_tt;
                            this.model.txtGhiChuTT = this.ChuanHoaTen(ds[0].ghichu_tt);

                            this.model.txtMaIG = ds[0].main_ghep;
                            if (ds[0].mig_id != "") {
                                this.mig_id = Number(ds[0].mig_id);
                            }

                            if (ds[0].nganhang_id_tt != "")
                                this.model.cboNganHangTT.value = ds[0].nganhang_id_tt;

                            this.model.cboHTTT.value = ds[0].httt_id;

                            /// TuanNA thêm code thay thế donvi_ql -->cboDonviQL  -- Ngày 29/07/2010
                            if (ds[0].donvi_id != undefined && ds[0].donvi_id != "")
                                this.model.cboDonviQL.value = ds[0].donvi_id;

                            if (ds[0].nhanvien_id != "") {
                                this.nhanvientc_id = Number(ds[0].nhanvien_id);
                                this.model.txtMaNVTC = await this.fn_tt_nhanvien(4, this.nhanvientc_id);
                            } else {
                                this.nhanvientc_id = 0;
                                this.model.txtMaNVTC = "";
                            }

                            if (ds[0].tuyenthu_id != "") {
                                this.tuyenthu_id = Number(ds[0].tuyenthu_id);
                                this.model.txtMaTuyen = await this.fn_tt_tuyenthu(1, this.tuyenthu_id);
                            } else {
                                this.tuyenthu_id = 0;
                                this.model.txtMaTuyen = "";
                            }
                            this.matb_dd = ds[0].matb_dd;

                            this.model.txtTenTB = this.ChuanHoaTen(ds[0].ten_tb);
                            $("#txtMaKH").focus();

                            await this.LAY_DS_SUDUNG_DVK(this.thanhtoan_id, 2);
                            await this.HienThi_NVQL_AM_DB_HD(1, this.thanhtoan_id);
                        }
                    }
                } else {
                    //Tuan them bien TBHD_PL = 1 --> khach hang nhan quyen la khach hang Moi -- Ngay 07/07/2010
                    this.TBHD_PL = 1;// In HD
                    this.$toast.error("Không tìm thấy thông tin về khách hàng có mã: " + this.model.txtMaKH + "\nĐề nghị kiểm tra lại !");
                    await this.SetButton(0);
                }
            } catch (ex) {
                this.$toast.error("Có lỗi:" + ex);
            }
        },
        async txtTenKH_KeyPress(e) {
            if (e.keyCode == 13) {
                this.btnDiaChiKH_Click()
            }
        },
        btnDiaChiKH_Click() {
            this.diaChiKH.tinh_id = this.tinhkh_id;
            this.diaChiKH.quan_id = this.quankh_id;
            this.diaChiKH.phuong_id = this.phuongkh_id;
            this.diaChiKH.pho_id = this.phokh_id;
            this.diaChiKH.ap_id = this.apkh_id;
            this.diaChiKH.khu_id = this.khukh_id;
            this.diaChiKH.dacdiem_id = this.dacdiemkh_id;
            this.diaChiKH.sonha = this.sonhakh;
            this.diaChiKH.diachi = this.model.txtDiaChiKH;
            this.Popup('popupDiaChiKH');
        },
        async getDiaChiKH(obj) {
            console.log("getDiaChiKH")
            console.log(obj)
            try {
                this.tinhkh_id = obj.tinh_id != null ? obj.tinh_id : 0;
                this.quankh_id = obj.quan_id != null ? obj.quan_id : 0;
                this.phuongkh_id = obj.phuong_id != null ? obj.phuong_id : 0;
                this.phokh_id = obj.pho_id != null ? obj.pho_id : 0;
                this.apkh_id = obj.ap_id != null ? obj.ap_id : 0;
                this.khukh_id = obj.khu_id != null ? obj.khu_id : 0;
                this.dacdiemkh_id = obj.dacdiem_id != null ? obj.dacdiem_id : 0;
                this.sonhakh = obj.sonha != null ? obj.sonha : 0;
                this.model.txtDiaChiKH = obj.diachimoi != null ? obj.diachimoi : 0;

                ///TuấnNA comment code --> hiển thị thông tin địa chỉ thanh toán, chứng từ, người đại diện theo địa chỉ KH --- Ngày 17/08/2010                
                if (this.hdkh_id == 0) {
                    if (this.model.txtDiaChiTT == "") {
                        //Hiếu tc: 21.1.2014: sửa lại hàm lấy đơn vị quản lý.
                        let ds = []
                        if (this.DONVIQL_HTTT == 1) {
                            ds = await this.Lay_ds_donviql_v2(this.phuongkh_id, this.phokh_id, this.apkh_id, this.khukh_id, 1,
                                Number(this.model.cboLoaiKH.value), Number(this.model.cboHTTT.value));
                        } else {
                            ds = await this.LAY_DS_DONVIQL(this.phuongkh_id, this.phokh_id, this.apkh_id, this.khukh_id, 1,
                                Number(this.model.cboLoaiKH.value));
                        } 
                        
                        if (ds.length <= 0) {
                            if (this.model.cboDonviQL.list.length != 1) {
                                this.$toast.error("Địa danh này chưa được gán đơn vị quản lý. Hãy liên hệ với Admin để xử lý!");
                                this.model.cboDonviQL.value = "";
                                return;
                            }
                        } else if (ds.length == 1) {
                            this.model.cboDonviQL.value = Number(ds[0].donvi_id);
                        } else {
                            this.dsToThiCong = ds
                            this.$refs.chonDonViModal.showModal()
                        }

                        this.clear_manvtc = "1";
                        this.tinhtt_id = this.tinhkh_id;
                        this.quantt_id = this.quankh_id;
                        this.phuongtt_id = this.phuongkh_id;
                        this.phott_id = this.phokh_id;
                        this.aptt_id = this.apkh_id;
                        this.khutt_id = this.khukh_id;
                        this.dacdiemtt_id = this.dacdiemkh_id;

                        await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 
                                Number(this.model.cboDonviQL.value), this.dacdiemtt_id);

                        if (this.DONVIQL_HTTT == 1)
                            await this.HienThi_NVQL_AM_HTTT(Number(this.model.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, 
                                this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.model.cboDonviQL.value), Number(this.model.cboHTTT.value));
                        else
                            await this.HienThi_NVQL_AM(Number(this.model.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, 
                                this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.model.cboDonviQL.value));

                        this.sonhatt = this.sonhakh;
                        this.model.txtDiaChiTT = this.model.txtDiaChiKH;
                    }

                    if (this.model.txtDiaChiCT == "") {
                        this.tinhct_id = this.tinhkh_id;
                        this.quanct_id = this.quankh_id;
                        this.phuongct_id = this.phuongkh_id;
                        this.phoct_id = this.phokh_id;
                        this.apct_id = this.apkh_id;
                        this.khuct_id = this.khukh_id;
                        this.dacdiemct_id = this.dacdiemkh_id;
                        this.sonhact = this.sonhakh;
                        this.model.txtDiaChiCT = this.model.txtDiaChiKH;
                    }

                    if (this.model.txtDiaChiTB == "") {
                        this.tinhtb_id = this.tinhkh_id;
                        this.quantb_id = this.quankh_id;
                        this.phuongtb_id = this.phuongkh_id;
                        this.photb_id = this.phokh_id;
                        this.aptb_id = this.apkh_id;
                        this.khutb_id = this.khukh_id;
                        this.dacdiemtb_id = this.dacdiemkh_id;
                        this.sonhatb = this.sonhakh;
                        this.model.txtDiaChiTB = this.model.txtDiaChiKH;
                    }
                }

                this.quanndd_id = this.quankh_id;
                this.phuongndd_id = this.phuongkh_id;
                this.phondd_id = this.phokh_id;
                this.apndd_id = this.apkh_id;
                this.khundd_id = this.khukh_id;
                this.dacdiemndd_id = this.dacdiemkh_id;
                this.sonhandd = this.sonhakh;
                this.model.txtDiaChiNDD = this.model.txtDiaChiKH;
            } catch (e) {
                console.log(e)
            }
        },
        async LAY_DBTT_THEO_MATT_KHID(vma_tt, vkhachhang_id, vdonvi_dl_id) {
            let result = 0
            try {
                let params = {
                    "vma_tt": vma_tt,
                    "vkhachhang_id": vkhachhang_id,
                    "vdonvi_dl_id": vdonvi_dl_id
                }
                let response = await api.lay_dbtt_theo_matt_khid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async txtMaTT_KeyPress(e) {
            if (e.keyCode == 13) {
                if (this.model.txtMaTT != undefined && this.model.txtMaTT != "") {
                    if (this.hdkh_id == 0) // Nếu chưa lập hđ thì view cả thông tin khách hàng cũ ra
                    {
                        if (this.khachhang_id == 0) {
                            await this.HienThiTT_DanhBa_KH_NhanCQ(this.model.txtMaTT);
                            this.$toast.error("Chưa có thông tin khách hàng. Hãy kiểm tra lại!");
                            return;
                        } else {
                            await this.HienThiTT_DanhBa_TT(await this.LAY_DBTT_THEO_MATT_KHID(this.model.txtMaTT, this.khachhang_id, 0));
                        }
                    }
                    else
                    {
                        if (khachhang_id == 0)
                            this.$toast.error("Đây là khách hàng mới. Bạn không thể tìm kiếm thông tin thanh toán cũ!");
                        else
                            await this.HienThiTT_DanhBa_TT(await this.LAY_DBTT_THEO_MATT_KHID(this.model.txtMaTT, this.khachhang_id, 0));
                    }
                }
            }
        },
        async HienThiTT_DanhBa_TT(ds) {
            try {
                if (ds.length > 0) {
                    //Thông tin thanh toán
                    this.model.txtMaTT = ds[0].ma_tt;
                    this.thanhtoan_id = Number(ds[0].thanhtoan_id);

                    let dt_madd = await this.HT_COMBO_MA_DAIDIEN(this.thanhtoan_id);
                    if (dt_madd && dt_madd.length > 0){
                        this.model.cboMaDD.list = dt_madd.map((x)=>({id:x.ma_tb, text:x.ten_ht}))
                        this.model.cboMaDD.value = ds[0].matb_dd;
                    }

                    this.model.txtTenTT = this.ChuanHoaTen(ds[0].ten_tt);
                    /// TuanNA thêm code hiển thị thông tin  -- Ngày 06/08/2010

                    this.model.txtMaTBDD = ds[0].matb_dd;
                    this.model.txtDiaChiTT = this.ChuanHoaTen(ds[0].diachi_tt);
                    this.model.txtDiaChiCT = this.ChuanHoaTen(ds[0].diachi_ct);

                    this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTTID(this.thanhtoan_id), 2);
                    this.model.txtMaIG = ds[0].main_ghep;

                    if (ds[0].mig_id != "") {
                        this.mig_id = Number(ds[0].mig_id);
                    }

                    //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
                    this.DV_QHNS = ds[0].dv_qhns;
                    this.MACHUONG = ds[0].machuong;
                    this.MN_KT = ds[0].mn_kt;
                    this.MA_NDKT = ds[0].ma_ndkt;
                    this.MN_NSNN = ds[0].mn_nsnn;
                    if (ds[0].tien_dm != "")
                        this.TIEN_DM = Number(ds[0].tien_dm);
                    else
                        this.TIEN_DM = 0;
                    //

                    /////--- Ngày 01/09/2010
                    //donvi_qlcu = LayDonVi_QL(txtMaTT.Text.Trim(), phuongtt_id, phott_id);
                    //donvi_qlmoi = donvi_qlcu;
                    /////
                    this.model.txtMST_TT = ds[0].mst;
                    this.model.txtSTK_TT = ds[0].stk;
                    this.model.txtChu_TK = ds[0].chu_tk;
                    this.model.txtGhiChuTT = this.ChuanHoaTen(ds[0].ghichu);

                    if (ds[0].nganhang_id != "")
                        this.model.cboNganHangTT.value = ds[0].nganhang_id;

                    this.model.cboHTTT.value = ds[0].httt_id;

                    /// TuanNA thêm code thay thế donvi_ql -->cboDonviQL  -- Ngày 29/07/2010
                    if (ds[0].donvi_id != undefined && ds[0].donvi_id != "")
                        this.model.cboDonviQL.value = ds[0].donvi_id;

                    if (ds[0].nhanvien_id != "") {
                        this.nhanvientc_id = Number(ds[0].nhanvien_id);
                        this.model.txtMaNVTC = await this.fn_tt_nhanvien(4, this.nhanvientc_id);
                    } else {
                        this.nhanvientc_id = 0;
                        this.model.txtMaNVTC = "";
                    }

                    if (ds[0].tuyenthu_id != "") {
                        this.tuyenthu_id = Number(ds[0].tuyenthu_id);
                        this.model.txtMaTuyen = await this.fn_tt_tuyenthu(1, this.tuyenthu_id);
                    } else {
                        this.tuyenthu_id = 0;
                        this.model.txtMaTuyen = "";
                    }
                    this.matb_dd = ds[0].matb_dd;

                    await this.LAY_DS_SUDUNG_DVK(this.thanhtoan_id, 2);
                    await this.HienThi_NVQL_AM_DB_HD(1, this.thanhtoan_id);
                }
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        btnDiaChiTB_Click() {
            this.diaChiTB_BC.tinh_id = this.tinhtb_id;
            this.diaChiTB_BC.quan_id = this.quantb_id;
            this.diaChiTB_BC.phuong_id = this.phuongtb_id;
            this.diaChiTB_BC.pho_id = this.photb_id;
            this.diaChiTB_BC.ap_id = this.aptb_id;
            this.diaChiTB_BC.khu_id = this.khutb_id;
            this.diaChiTB_BC.dacdiem_id = this.dacdiemtb_id;
            this.diaChiTB_BC.sonha = this.sonhatb;
            this.diaChiTB_BC.block = this.blocktb;
            this.diaChiTB_BC.tang = this.tangtb;
            this.diaChiTB_BC.sophong = this.sophongtb;
            this.diaChiTB_BC.motathem = this.motadctb;
            this.diaChiTB_BC.diachi = this.model.txtDiaChiTB;

            console.log(this.diaChiTB_BC)
            this.$refs.diaChiTB_BC.show()
            this.$refs.popupDiaChiTB_BC.frmDiaChiBC_Load()
        },
        closePopupDiaChiTB_BC() {
            this.$refs.diaChiTB_BC.hide()
        },
        getDiaChi_TBBC(obj) {
            this.$refs.diaChiTB_BC.hide()
            this.tinhtb_id = obj.tinh_id;
            this.quantb_id = obj.quan_id;
            this.phuongtb_id = obj.phuong_id;
            this.photb_id = obj.pho_id;
            this.aptb_id = obj.ap_id;
            this.khutb_id = obj.khu_id;
            this.dacdiemtb_id = obj.dacdiem_id;
            this.sonhatb = obj.sonha;
            this.blocktb = obj.block;
            this.tangtb = obj.tang;
            this.sophongtb = obj.sophong;
            this.motadctb = obj.motathem;
            this.model.txtDiaChiTB = obj.diachi;
        },
        async btnDiachiLD_Click() {          
            this.DiachiLD_Click();
            await this.LayTienTheoKhoanMuc();     
        },
        DiachiLD_Click() {
            this.diaChiLD_BC.tinh_id = 0;
            this.diaChiLD_BC.quan_id = this.quanld_id;
            this.diaChiLD_BC.phuong_id = this.phuongld_id;
            this.diaChiLD_BC.pho_id = this.phold_id;
            this.diaChiLD_BC.ap_id = this.apld_id;
            this.diaChiLD_BC.khu_id = this.khuld_id;
            this.diaChiLD_BC.dacdiem_id = this.dacdiemld_id;
            this.diaChiLD_BC.sonha = this.sonhald;
            this.diaChiLD_BC.block = this.blockld;
            this.diaChiLD_BC.tang = this.tangld;
            this.diaChiLD_BC.sophong = this.sophongld;
            this.diaChiLD_BC.motathem = this.motadcld;
            this.diaChiLD_BC.diachi = this.model.txtDiachiLD;

            this.$refs.diaChiLD_BC.show()
            this.$refs.popupDiaChiLD_BC.frmDiaChiBC_Load()
        },
        closePopupDiaChiLD_BC() {
            this.$refs.diaChiLD_BC.hide()
        },
        getDiaChi_LDBC(obj) {
            this.$refs.diaChiLD_BC.hide()
            this.tinhld_id = obj.tinh_id;
            this.quanld_id = obj.quan_id;
            this.phuongld_id = obj.phuong_id;
            this.phold_id = obj.pho_id;
            this.apld_id = obj.ap_id;
            this.khuld_id = obj.khu_id;
            this.dacdiemld_id = obj.dacdiem_id;
            this.sonhald = obj.sonha;
            this.blockld = obj.block;
            this.tangld = obj.tang;
            this.sophongld = obj.sophong;
            this.motadcld = obj.motathem;
            this.model.txtDiachiLD = obj.diachi;

            if (this.quankh_id == 0) {
                this.quankh_id = this.quanld_id;
                this.phuongkh_id = this.phuongld_id;
                this.phokh_id = this.phold_id;
                this.apkh_id = this.apld_id;
                this.khukh_id = this.khuld_id;
                this.dacdiemkh_id = this.dacdiemld_id;
                this.sonhakh = this.sonhald;
                this.model.txtDiaChiKH = this.model.txtDiachiLD;
            }

            if (this.quantt_id == 0) {
                this.quantt_id = this.quanld_id;
                this.phuongtt_id = this.phuongld_id;
                this.phott_id = this.phold_id;
                this.aptt_id = this.apld_id;
                this.khutt_id = this.khuld_id;
                this.dacdiemtt_id = this.dacdiemld_id;
                this.sonhatt = this.sonhald;
                this.model.txtDiaChiTT = this.model.txtDiachiLD;
            }

            if (this.quantb_id == 0 || this.model.txtDiaChiTB == "") {
                this.quantb_id = this.quanld_id;
                this.phuongtb_id = this.phuongld_id;
                this.photb_id = this.phold_id;
                this.aptb_id = this.apld_id;
                this.khutb_id = this.khuld_id;
                this.dacdiemtb_id = this.dacdiemld_id;
                this.sonhatb = this.sonhald;
                this.blocktb = obj.block;
                this.tangtb = obj.tang;
                this.sophongtb = obj.sophong;
                this.motadctb = obj.motathem;
                this.model.txtDiaChiTB = this.model.txtDiachiLD;
            }
        },
        btnLayMaGD_Click() {
            this.$refs.popupSearchContract.showModal();
        },
        async acceptSearchContract(item) {
            try {
                this.loading(true)
                this.model.txtMaGD = item.ma_gd
                $("#txtMaGD").focus();
                if (this.model.txtMaGD != "") {
                    await this.HienThiTTHopDongKH(await this.LAY_DS_HOPDONG_THEO_MA_GD(this.model.txtMaGD, LoaiHopDong.CHUYEN_QUYEN, TrangThaiHD.MOI, 0));
                }  
            } finally {
                this.loading(false)
            } 
        },
        async btnTienCQ_Click() {
            var ds = [];
            await api_ins.post_lay_khoanmuc_tt_theo_loaihd(this.axios, { "vloaihd_id": LoaiHopDong.CHUYEN_QUYEN }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    ds = response.data.data;
                    if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
                        for (var item of ds) {
                            var items = this.ds_tien_khoanmuc.filter(x=>x.khoanmuctt_id==item.khoanmuctt_id);
                            if (items && items.length>0) {
                                item.tien = items[0].tien;
                                item.vat  = items[0].vat;
                                item.tongtien = item.tien + item.vat;
                            }
                        }
                    }
                }
            });

            this.popupComponentData = {"ds":ds,cols:[
                    {fieldName: 'tentat_kmtt', headerText: 'Khoản mục', allowFiltering: false, allowSorting: false, isPrimaryKey:true},
                    {fieldName: 'tongtien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'tongtien'},  headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit:"tongtienParam"},
                    {fieldName: 'tien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'cuoclapdat'},  headerText: 'Tiền', allowFiltering: false, allowSorting: false, edit:"tienParam"},
                    {fieldName: 'vat', textAlign:'right', format:'N', customAttributes:{class: 'vatlapdat'}, headerText: 'Vat', allowFiltering: false, allowSorting: false},
            ], keycol: "khoanmuctt_id", allowExit:false, tieude:"Chi tiết tiền khoản mục", tieudebang:"Chi tiết khoản mục thanh toán",}
            this.popupComponentName = "popupTienKM";
            this.popupComponent = ()=>import('../InstallNewSubs/Popups/popupTienKM');
            this.Popup('popupComponentsCQBC');
        },
        bntLayMaKh_Click_1() {
            this.popupComponentAttr = { vISKHDN : this.$root.token.getPhanVungID() == 97 }
            this.popupComponent = ()=>import('@/modules/search/subscriber/SearchAccount');
            this.popupComponentName = "SearchAccount";
            this.Popup('popupComponentsCQBC');
        },
        btnDiaChiNDD_Click() {
            if (this.tinhndd_id == 0)
                this.diaChiNDD.tinh_id = this.tinhkh_id;
            else
                this.diaChiNDD.tinh_id = this.tinhndd_id;

            if (this.quanndd_id == 0)
                this.diaChiNDD.quan_id = this.quankh_id;
            else
                this.diaChiNDD.quan_id = this.quanndd_id;

            if (this.phuongndd_id == 0)
                this.diaChiNDD.phuong_id = this.phuongkh_id;
            else
                this.diaChiNDD.phuong_id = this.phuongndd_id;

            if (this.phondd_id == 0)
                this.diaChiNDD.pho_id = this.phokh_id;
            else
                this.diaChiNDD.pho_id = this.phondd_id;

            if (this.apndd_id == 0)
                this.diaChiNDD.ap_id = this.apkh_id;
            else
                this.diaChiNDD.ap_id = this.apndd_id;

            if (this.khundd_id == 0)
                this.diaChiNDD.khu_id = this.khukh_id;
            else
                this.diaChiNDD.khu_id = this.khundd_id;

            if (this.dacdiemndd_id == 0)
                this.diaChiNDD.khu_id = this.dacdiemkh_id;
            else
                this.diaChiNDD.khu_id = this.dacdiemndd_id;

            if (this.sonhandd == "")
                this.diaChiNDD.sonha = this.sonhakh;
            else
                this.diaChiNDD.sonha = this.sonhandd;

            this.diaChiNDD.diachi = this.model.txtDiaChiNDD;
            this.Popup('popupDiaChiNDD');
        },
        getDiaChiNDD(obj) {
            this.quanndd_id = obj.quan_id;
            this.phuongndd_id = obj.phuong_id;
            this.phondd_id = obj.pho_id != null ? obj.pho_id : 0;
            this.apndd_id = obj.ap_id != null ? obj.ap_id : 0;
            this.khundd_id = obj.khu_id != null ? obj.khu_id : 0;
            this.dacdiemndd_id = obj.dacdiem_id != null ? obj.dacdiem_id : 0;
            this.sonhandd = obj.sonha;
            this.model.txtDiaChiNDD = obj.diachimoi;
        },
        btnDCHoKhau_Click() {
            if (this.model.txtHoKhau != "") {
                this.diaChiHK.diachi = this.model.txtHoKhau;
            }
            this.Popup('popupHoKhau_KHNQ');
        },
        getDiaChiHK(obj) {
            this.model.txtHoKhau = obj.diachimoi;
        },
        btnLayMaTT_Click() {
            console.log("btnLayMaTT_Click: " + this.khachhang_id)
            if (this.khachhang_id == 0) {
                this.$toast.error("Bạn không thể chọn mã thanh toán của khách hàng mới. Hãy kiểm tra lại!");
                return;
            }

            if (this.model.txtMaKH != "") {
                this.popupComponentData = this.khachhang_id;
                this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonMaTT.vue');
                this.popupComponentName = "popupChonMaTT";
                this.Popup('popupComponentsCQBC');  
            }
        },
        async closePopupLayMaTT(val) {
            this.model.txtMaTT = val.ma_tt;
            $("#txtMaTT").focus();

            if (this.hdkh_id == 0) // Nếu chưa lập hđ thì view cả thông tin khách hàng cũ ra
            {
                if (this.khachhang_id == 0) {
                    this.$toast.error("Chưa có thông tin khách hàng. Hãy kiểm tra lại!");
                } else {
                    await this.HienThiTT_DanhBa_TT(await this.LAY_DBTT_THEO_MATT_KHID(this.model.txtMaTT, this.khachhang_id, 0));
                }
            } else {
                if (this.khachhang_id == 0)
                    this.$toast.error("Đây là khách hàng mới. Bạn không thể tìm kiếm thông tin thanh toán cũ!");
                else
                    await this.HienThiTT_DanhBa_TT(await this.LAY_DBTT_THEO_MATT_KHID(this.model.txtMaTT, this.khachhang_id, 0));
            }
        },
        btnDiaChiTT_Click() {
            this.diaChiTT.tinh_id = this.tinhtt_id;
            this.diaChiTT.quan_id = this.quantt_id;
            this.diaChiTT.phuong_id = this.phuongtt_id;
            this.diaChiTT.pho_id = this.phott_id;
            this.diaChiTT.ap_id = this.aptt_id;
            this.diaChiTT.khu_id = this.khutt_id;
            this.diaChiTT.dacdiem_id = this.dacdiemtt_id;
            this.diaChiTT.sonha = this.sonhatt;
            this.diaChiTT.diachi = this.model.txtDiaChiTT;
            this.Popup('popupThanhToan_KHNQ');
        },
        async getDiaChiTT(obj) {
            this.tinhtt_id = obj.tinh_id;
            this.quantt_id = obj.quan_id;
            this.phuongtt_id = obj.phuong_id;
            this.phott_id = obj.pho_id != null ? obj.pho_id : 0;
            this.aptt_id = obj.ap_id != null ? obj.ap_id : 0;
            this.khutt_id = obj.khu_id != null ? obj.khu_id : 0;
            this.dacdiemtt_id = obj.dacdiem_id != null ? obj.dacdiem_id : 0;;
            this.sonhatt = obj.sonha;
            this.model.txtDiaChiTT = obj.diachimoi;

            //Hiếu tc: 21.1.2014: sửa lại hàm lấy đơn vị quản lý.
            let ds = []
            if (this.DONVIQL_HTTT == 1) {
                ds = await this.Lay_ds_donviql_v2(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1,
                    Number(this.model.cboLoaiKH.value), Number(this.model.cboHTTT.value));
            } else {
                ds = await this.LAY_DS_DONVIQL(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1,
                    Number(this.model.cboLoaiKH.value));
            } 
            
            if (ds.length <= 0) {
                if (this.model.cboDonviQL.list.length != 1) {
                    this.$toast.error("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
                    this.model.cboDonviQL.value = "";
                    return;
                }
            } else if (ds.length == 1) {
                this.model.cboDonviQL.value = Number(ds[0].donvi_id);
            } else {
                this.dsToThiCong = ds
                this.$refs.chonDonViModal.showModal()
            }

            if (this.model.txtDiaChiBC == "") {
                this.model.txtMaNVTC = ""; //Nếu chọn lại địa chỉ thanh toán thì clear mã nhân viên thu cước - Hiếu 29.09.2010
                this.model.txtMaTuyen = "";
                ///TuanNA: Lấy mã NVTC theo địa chỉ tt
                ///Ngày 07.12.2012                  
                await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 
                        Number(this.model.cboDonviQL.value), this.dacdiemtt_id);
                if (this.DONVIQL_HTTT == 1)
                    await this.HienThi_NVQL_AM_HTTT(Number(this.model.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, 
                        this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.model.cboDonviQL.value), Number(this.model.cboHTTT.value));
                else
                    await this.HienThi_NVQL_AM(Number(this.model.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, 
                        this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.model.cboDonviQL.value));
                ///
            }

            if (this.model.txtDiaChiCT == "") {
                this.tinhct_id = obj.tinh_id;
                this.quanct_id = this.quantt_id;
                this.phuongct_id = this.phuongtt_id;
                this.phoct_id = this.phott_id;
                this.apct_id = obj.ap_id != null ? obj.ap_id : 0;
                this.khuct_id = obj.khu_id != null ? obj.khu_id : 0;
                this.dacdiemct_id = obj.dacdiem_id != null ? obj.dacdiem_id : 0;
                this.sonhact = this.sonhatt;
                this.model.txtDiaChiCT = this.model.txtDiaChiTT;
            }

            if (this.model.txtDiaChiTB == "") {
                this.tinhtb_id = obj.tinh_id;
                this.quantb_id = this.quantt_id;
                this.phuongtb_id = this.phuongtt_id;
                this.photb_id = this.phott_id;
                this.aptb_id = obj.ap_id != null ? obj.ap_id : 0;
                this.khutb_id = obj.khu_id != null ? obj.khu_id : 0;
                this.dacdiemtb_id = obj.dacdiem_id != null ? obj.dacdiem_id : 0;
                this.sonhatb = this.sonhatt;
                this.model.txtDiaChiTB = this.model.txtDiaChiTT;
            }
        },
        btnDiaChiCT_Click() {
            this.diaChiCT.tinh_id = this.tinhct_id;
            this.diaChiCT.quan_id = this.quanct_id;
            this.diaChiCT.phuong_id = this.phuongct_id;
            this.diaChiCT.pho_id = this.phoct_id;
            this.diaChiCT.ap_id = this.apct_id;
            this.diaChiCT.khu_id = this.khuct_id;
            this.diaChiCT.dacdiem_id = this.dacdiemct_id;
            this.diaChiCT.sonha = this.sonhact;
            this.diaChiCT.diachi = this.model.txtDiaChiCT;
            this.Popup('popupChungTu_KHNQ');
        },
        getDiaChiCT(obj) {
            this.tinhct_id = obj.tinh_id;
            this.quanct_id = obj.quan_id;
            this.phuongct_id = obj.phuong_id;
            this.phoct_id = obj.pho_id != null ? obj.pho_id : 0;
            this.apct_id = obj.ap_id != null ? obj.ap_id : 0;
            this.khuct_id = obj.khu_id != null ? obj.khu_id : 0;
            this.dacdiemct_id = obj.dacdiem_id != null ? obj.dacdiem_id : 0;
            this.sonhact = obj.sonha;
            this.model.txtDiaChiCT = obj.diachimoi;
        },
        btnDiaChiBC_Click() {
            this.diaChiBC.tinh_id = this.tinhbc_id;
            this.diaChiBC.quan_id = this.quanbc_id;
            this.diaChiBC.phuong_id = this.phuongbc_id;
            this.diaChiBC.pho_id = this.phobc_id;
            this.diaChiBC.ap_id = this.apbc_id;
            this.diaChiBC.khu_id = this.khubc_id;
            this.diaChiBC.dacdiem_id = this.dacdiembc_id;
            this.diaChiBC.sonha = this.sonhabc;
            this.diaChiBC.diachi = this.model.txtDiaChiBC;
            this.Popup('popupBaoCuoc_KHNQ');
        },
        async getDiaChiBC(obj) {
            this.model.txtMaNVTC = "";
            this.model.txtMaTuyen = "";
            this.tinhbc_id = obj.tinh_id;
            this.quanbc_id = obj.quan_id;
            this.phuongbc_id = obj.phuong_id;
            this.phobc_id = obj.pho_id != null ? obj.pho_id : 0;
            this.apbc_id = obj.ap_id != null ? obj.ap_id : 0;
            this.khubc_id = obj.khu_id != null ? obj.khu_id : 0;
            this.dacdiembc_id = obj.dacdiem_id != null ? obj.dacdiem_id : 0;
            this.sonhabc = obj.sonha;
            this.model.txtDiaChiBC = obj.diachimoi;

            //Hiếu tc: 21.1.2014: sửa lại hàm lấy đơn vị quản lý.
            let ds = new []
            if (this.DONVIQL_HTTT == 1) {
                ds = await this.Lay_ds_donviql_v2(this.phuongbc_id, this.phobc_id, this.apbc_id, this.khubc_id, 1,
                    Number(this.model.cboLoaiKH.value), Number(this.model.cboHTTT.value));
            } else {
                ds = await this.LAY_DS_DONVIQL(this.phuongbc_id, this.phobc_id, this.apbc_id, this.khubc_id, 1,
                    Number(this.model.cboLoaiKH.value));
            } 
            
            if (ds.length <= 0) {
                if (this.model.cboDonviQL.list.length != 1) {
                    this.$toast.error("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
                    this.model.cboDonviQL.value = "";
                    return;
                }
            } else if (ds.length == 1) {
                this.model.cboDonviQL.value = Number(ds[0].donvi_id);
            } else {
                this.dsToThiCong = ds
                this.$refs.chonDonViModal.showModal()
            }

            await this.HT_NVTC_TheoDC(this.quanbc_id, this.phuongbc_id, this.phobc_id, this.apbc_id, this.khubc_id, 
                    Number(this.model.cboDonviQL.value), this.dacdiembc_id);
        },
        btnTaoMIG_Click() {
            console.log("btnTaoMIG_Click: " + this.hdkh_id)
            if (this.hdkh_id != 0) {
                this.modalInGhep = {};
                this.modalInGhep.hdkh_id = this.hdkh_id;
                this.modalInGhep.khachhang_id = this.khachhang_id;
                this.modalInGhep.hdtt_id = this.hdtt_id;
                this.modalInGhep.thanhtoan_id = this.thanhtoan_id;
                this.modalInGhep.fmig_id = this.mig_id;
                this.modalInGhep.fhdmig_id = this.hdmig_id;
                this.modalInGhep.ma_tt = this.model.txtMaTT
                this.modalInGhep.ten_tt = this.model.txtTenTT
                this.$refs.dlg_ChonMaInGhep.show()
                this.$refs.ppChonMaInGhep.openDialog()
            } else {
                this.$toast.error("Chưa có thông tin về hợp đồng khách hàng!");
            } 
        },
        async closePopupChonMaInGhep(val) {
            if (val && val.hdmig_id) {
                this.hdmig_id = val.hdmig_id;
                this.model.txtMaIG = val.main_ghep;
            } else {
                await this.HienThiTTHopDongTT(await this.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(this.hdtt_id));
            }
        },
        btnTraCuuNVTC_click() {
            this.diachiLD.donviql_id = Number(this.model.cboDonviQL.value);
            this.diachiLD.QUAN_ID = this.quantt_id;
            this.diachiLD.PHUONG_ID = this.phuongtt_id;
            this.diachiLD.PHO_ID = this.phott_id;
            this.diachiLD.AP_ID = this.aptt_id;
            this.diachiLD.KHU_ID = this.khutt_id;
            this.diachiLD.DACDIEM_ID = this.dacdiemtt_id;
            this.diachiLD.TUYENTHU_ID = this.tuyenthu_id;
            this.diachiLD.NHANVIENTC_ID = this.nhanvientc_id;
            this.$refs.dlgTimKiemNVTC.openDialog();
        },
        async btnNSNN_Click() {
            let loai_nh = await this.fn_tt_nganhang(1, this.model.cboNganHangTT.value);
            if (loai_nh == "2") {        
                this.$bvModal.show('modal-nganhangnn');
            }
        },
        async tsbtnNhapMoi_Click() {
            if (!this.enable.tsbtnNhapMoi) return
            jQuery('#tabs a')[0].click();
            await this.SetButton(1);
        },
        async tsbtnHuyBo_Click() {
            if (!this.enable.tsbtnHuyBo) return
            await this.SetButton(0);
        },
        async KIEMTRA_THANHTOAN_LH(hdkhId) {
            let result = ""
            try {
                let params = {
                    "hdkhId": hdkhId
                }
                let response = await api.kiemtra_thanhtoan_lh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbtnThanhToan_Click() {
            if (!this.enable.tsbtnThanhToan) return
            try {
                var code = await this.KIEMTRA_THANHTOAN_LH(this.hdkh_id)
                if (code != "") {
                    if (code == "0") {
                        this.$toast.error("Hợp đồng chưa có thông tin liên hệ, bạn phải bổ sung thông tin liên hệ trước khi thanh toán !");
                        return;
                    } else if (code == "2") {
                        this.$toast.error("Phải chọn mục đích sử dụng là Liên Hệ cho tất cả các thuê bao!");
                        return;
                    }

                    this.txtMaGDTT = this.model.txtMaGD
                    this.userComponentModule = thanhToan
                    if (this.paymentloaded) this.$refs.thanhtoanDialog.show(true)

                    // this.$refs.thanhtoanDialog.show()
                    // this.$refs.thanhToanPopUp.SetMaGd(this.model.txtMaGD)
                    // this.$router.push({ name: "Payment", params: {magd: this.model.txtMaGD} })
                }
            } catch (e) {
                this.$toast.error(e)
            }
        },
        tsddbIn_Click() {
            if (!this.enable.tsddbIn) return
            try {
                if (this.hdkh_id != 0) {
                    this.modelInHopDong.ma_gd = this.model.txtMaGD
                    this.modelInHopDong.ngaylap_hd = this.model.dtpNgayLapHDCQ
                    this.modelInHopDong.ngay_yc = this.model.dtpNgayYC
                    this.modelInHopDong.hdkh_id = this.hdkh_id
                    this.$refs.frmInHopDong.showModal()
                } else {
                    this.$toast.error("Chưa có thông tin hợp đồng!");
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async tsbtnThongTinLH_Click() {
            if (this.hdkh_id != 0) {         
                try {
                    this.$refs.thongtinlienhe.show()
                    this.loading(true)
                    await this.$refs.ppThongTinLienHe.getThongTinLienHe(),
                    await this.$refs.ppThongTinLienHe.getThongTinMD_CG(),
                    await this.$refs.ppThongTinLienHe.getThongTinMD_DG()
                    this.$refs.ppThongTinLienHe.SetButtom(0)       
                } catch (e) {
                    console.log(e)
                } finally {
                    this.loading(false)
                }           
            }
        },
        tsbtnEmail_Click() {
            if (this.hdkh_id != 0) {
                try {
                    this.$refs.thongtinemail.show()
                    this.loading(true)
                    this.$refs.frmThongTinEmail.frmThongTinEmail_Load()
                } catch (e) {
                    console.log(e)
                } finally {
                    this.loading(false)
                }
            }
        },
        async KIEMTRA_HDTB_THEO_HDTT(hdttId) {
            let result = ""
            try {
                let params = {
                    "hdttId": hdttId
                }
                let response = await api.kiemtra_hdtb_theo_hdtt(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async XOA_HDTT(hdttId) {
            let result = "Xảy ra lỗi"
            try {
                let params = {
                    "hdttId": hdttId
                }
                let response = await api.xoa_hopdong_thanhtoan(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    result = "OK"
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbtnXoaTT_Click() {
            if (!this.enable.tsbtnXoaTT) return
            if (Number(await this.KIEMTRA_HDTB_THEO_HDTT(this.hdtt_id)) > 0) {
                this.$toast.error("Thanh toán " + this.model.txtMaTT + " đã có thuê bao! Hãy xóa thuê bao trước.");
                return;
            }
            //
            this.$confirm('Bạn thật sự muốn xóa thanh toán không ?', {
                confirmButtonText: 'Có', cancelButtonText: 'Không',
            }).then(async v => {
                if (v) {
                    try {
                        this.loading(true)
                        let kqXoa = await this.XOA_HDTT(this.hdtt_id)
                        if (kqXoa != "OK") {
                            this.$toast.error(kqXoa)
                            return
                        }

                        this.$toast.success("Xóa TT thành công")
                        await this.HienThiDS_HDTT_CBO(this.hdkh_id);
                        this.HienThiDanhSacHDTT(await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id));
                    } catch (exp) {
                        console.log(exp)
                        this.$toast.error(exp)
                    } finally {
                        this.loading(false)
                    }
                }
            })    
        },
        async GetKey(keyname) {
            let result = ""
            try {
                let params = {
                    "keyname": keyname,
                    "numblocksize": 0,
                    "numretry": 0
                }
                let response = await api.get_keys(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async SINH_MATT(donvi_id, quan_id) {
            let result = ""
            try {
                let params = {
                    "donvi_id": donvi_id,
                    "quan_id": quan_id
                }
                let response = await api.sinh_matt(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        StringToString(xauthamso) {
            if (xauthamso == null) return "";
            let letter;
            let Text11;
            let Text22 = "";
            Text11 = xauthamso;

            for (let j = 0; j < Text11.length; j++) {
                letter = Text11.substring(j,j+1);
                if (letter == "\r")
                    Text22 = Text22 + "\r";
                else if (letter == "\n")
                    Text22 = Text22 + "\n";
                else
                {
                    Text22 = (Text22 + letter);
                }
            }
            return Text22;
        },
        async TaoDuLieu_HDTT(themmoi) {
            this.dsHDTT = []
            if (themmoi == true)
                this.hdtt_id = await this.GetKey("HD_THANHTOAN");

            let rowHDTT = {}
            rowHDTT.BANCHEO = 1
            rowHDTT.HDTT_ID = this.hdtt_id;
            rowHDTT.HDKH_ID = this.hdkh_id;
            if (this.model.txtMaTT == "") {
                this.model.txtMaTT = await this.SINH_MATT(Number(this.model.cboDonviQL.value), this.quanld_id);/// TuấnNA thay thế donvi_ql --> CboDonviQL -- Ngay 29/07/2010
                this.thanhtoan_id = 0;
            }

            rowHDTT.DONVI_ID = Number(this.model.cboDonviQL.value);/// TuấnNA thay thế donvi_ql --> CboDonviQL -- Ngay 29/07/2010
            rowHDTT.MA_TT = this.model.txtMaTT;
            if (this.thanhtoan_id > 0)
                rowHDTT.THANHTOAN_ID = this.thanhtoan_id;

            ///Tuấn NA thêm code chuẩn hóa lại xâu ký tự đặc biệt(Ủy, Ánh...) trước khi đưa vào CSDL ---Ngày 23/10/2010
            rowHDTT.TEN_TT = this.StringToString(this.model.txtTenTT);
            rowHDTT.DIACHI_TT = this.StringToString(this.model.txtDiaChiTT);
            rowHDTT.DIACHI_BC = this.StringToString(this.model.txtDiaChiBC);
            rowHDTT.MST = this.model.txtMST_TT;
            rowHDTT.HTTT_ID = Number(this.model.cboHTTT.value);
            rowHDTT.STK = this.model.txtSTK_TT;
            rowHDTT.CHU_TK = this.model.txtChu_TK;
            if (Number(this.model.cboHTTT.value) == HinhThucThanhToan.UYNHIEM_THU) {
                rowHDTT.NGANHANG_ID = Number(this.model.cboNganHangTT.value);
            }

            if (this.tuyenthu_id != 0)
                rowHDTT.TUYENTHU_ID = this.tuyenthu_id;

            rowHDTT.MATB_DD = this.model.cboMaDD.value;
            rowHDTT.NGUOI_CN = this.$root.token.getUserName()
            rowHDTT.NGAY_CN = this.ngay_sys
            rowHDTT.MAY_CN = this.$root.token.getMachine()

            //TuanNA Them code thêm dữ liệu vào Dataset -- Ngày 07/07/2010
            //rowHDTT.NHANVIEN_ID = nhanvientc_id;
            rowHDTT.DIACHI_CT = this.StringToString(this.model.txtDiaChiCT);

            //rowHDTT.MS_NSNN = "";//TuấnNA bỏ các thông tin MS_NSNN chưa sử dụng -- Ngày 29/07/2010
            //rowHDTT.ML_NSNN = "";//TuấnNA bỏ các thông tin ML_NSNN chưa sử dụng -- Ngày 29/07/2010
            rowHDTT.GHICHU = this.StringToString(this.model.txtGhiChuTT);
            //End tuan

            //TuấnNA thêm code tạo dữ liệu NSNN --- Ngày 12/11/2010
            let loai_nh = await this.fn_tt_nganhang(1, this.model.cboNganHangTT.value);
            if (loai_nh == "2") {
                rowHDTT.DV_QHNS = this.DV_QHNS;
                rowHDTT.MACHUONG = this.MACHUONG;
                rowHDTT.MN_KT = this.MN_KT;
                rowHDTT.MA_NDKT = this.MA_NDKT;
                rowHDTT.MN_NSNN = this.MN_NSNN;
                rowHDTT.TIEN_DM = this.TIEN_DM;
            } else {
                rowHDTT.DV_QHNS = "";
                rowHDTT.MACHUONG = "";
                rowHDTT.MN_KT = "";
                rowHDTT.MA_NDKT = "";
                rowHDTT.MN_NSNN = "";
                rowHDTT.TIEN_DM = 0;
            }
            //

            this.dsHDTT.push(rowHDTT);
        },
        TaoDuLieu_HDTT_NV(themmoi) {
            this.dsHDTT_NV = []
            if (this.nhanvien_ql_am_id != 0) {
                let rowhdtt_nv = {}
                rowhdtt_nv.HDTT_ID = this.hdtt_id;
                rowhdtt_nv.NHANVIEN_ID = this.nhanvien_ql_am_id;
                rowhdtt_nv.MAY_CN = this.$root.token.getMachine()
                rowhdtt_nv.NGAY_CN = this.ngay_sys
                rowhdtt_nv.NGUOI_CN = this.$root.token.getUserName()
                this.dsHDTT_NV.push(rowhdtt_nv);
            }
        },
        async TaoDuLieu_DiaChi(themmoi, kieu) {
            this.dsDiaChi_BanCheo = []
            this.dsDiaChi = []
            this.dsDiaChiHDKH = []
            this.dsDiaChiHDTT = []
            this.dsDiaChiHDTB = []

            if (kieu == 1 || kieu == 4) {
                let rowDiaChiKH = {}
                let rowDCBC = {}

                if (themmoi == true)
                    this.diachikh_id = await this.GetKey("DIACHI");

                rowDiaChiKH.DIACHI_ID = this.diachikh_id;
                if (this.tinhkh_id != 0)
                    rowDiaChiKH.TINH_ID = this.tinhkh_id;
                rowDiaChiKH.QUAN_ID = this.quankh_id;

                if (this.phuongkh_id != 0)
                    rowDiaChiKH.PHUONG_ID = this.phuongkh_id;
                if (this.phokh_id != 0)
                    rowDiaChiKH.PHO_ID = this.phokh_id;
                if (this.apkh_id != 0)
                    rowDiaChiKH.AP_ID = this.apkh_id;
                if (this.khukh_id != 0)
                    rowDiaChiKH.KHU_ID = this.khukh_id;
                if (this.dacdiemkh_id != 0)
                    rowDiaChiKH.DACDIEM_ID = this.dacdiemkh_id;

                rowDiaChiKH.SONHA = this.StringToString(this.sonhakh);
                rowDiaChiKH.DIACHI = this.StringToString(this.model.txtDiaChiKH);

                rowDCBC = Object.assign({}, rowDiaChiKH)
                rowDCBC.LOAI_DC = 1;
                this.dsDiaChi_BanCheo.push(rowDCBC);
                this.dsDiaChi.push(rowDiaChiKH);

                if (themmoi == true) {
                    let rowDiaChiHDKH = {}
                    rowDiaChiHDKH.DIACHI_ID = this.diachikh_id;
                    rowDiaChiHDKH.HDKH_ID = this.hdkh_id;
                    this.dsDiaChiHDKH.push(rowDiaChiHDKH);
                }
            }

            if (kieu == 2 || kieu == 4) {
                let rowDiaChiTT = {}
                let rowDCBC = {}

                if (themmoi == true)
                    this.diachitt_id = await this.GetKey("DIACHI");

                rowDiaChiTT.DIACHI_ID = this.diachitt_id;
                if (this.tinhtt_id != 0)
                    rowDiaChiTT.TINH_ID = this.tinhtt_id;
                rowDiaChiTT.QUAN_ID = this.quantt_id;
                if (this.phuongtt_id != 0)
                    rowDiaChiTT.PHUONG_ID = this.phuongtt_id;

                if (this.phott_id != 0)
                    rowDiaChiTT.PHO_ID = this.phott_id;
                if (this.aptt_id != 0)
                    rowDiaChiTT.AP_ID = this.aptt_id;
                if (this.khutt_id != 0)
                    rowDiaChiTT.KHU_ID = this.khutt_id;
                if (this.dacdiemtt_id != 0)
                    rowDiaChiTT.DACDIEM_ID = this.dacdiemtt_id;

                rowDiaChiTT.SONHA = this.StringToString(this.sonhatt);
                rowDiaChiTT.DIACHI = this.StringToString(this.model.txtDiaChiTT);

                rowDCBC = Object.assign({}, rowDiaChiTT)
                rowDCBC.LOAI_DC = 2;
                this.dsDiaChi_BanCheo.push(rowDCBC);
                this.dsDiaChi.push(rowDiaChiTT);

                if (themmoi == true) {
                    let rowDiaChiHDTT = {}
                    rowDiaChiHDTT.DIACHI_ID = this.diachitt_id;
                    rowDiaChiHDTT.HDTT_ID = this.hdtt_id;
                    this.dsDiaChiHDTT.push(rowDiaChiHDTT);
                }
            }

            if (kieu == 3 || kieu == 4) {
                let rowDCBC = {}
                let rowDiaChiTB = {}
                let rowDiachiLD = {}
                if (themmoi == true) {
                    this.diachitb_id = await this.GetKey("DIACHI");
                    this.diachild_id = await this.GetKey("DIACHI");
                }

                //Ðịa chỉ thuê bao
                rowDiaChiTB.DIACHI_ID = this.diachitb_id;
                if (this.tinhtb_id != 0)
                    rowDiaChiTB.TINH_ID = this.tinhtt_id;
                rowDiaChiTB.QUAN_ID = this.quantt_id;

                if (this.phuongtb_id != 0)
                    rowDiaChiTB.PHUONG_ID = this.phuongtt_id;
                if (this.photb_id != 0)
                    rowDiaChiTB.PHO_ID = this.phott_id;
                if (this.aptb_id != 0)
                    rowDiaChiTB.AP_ID = this.aptt_id;
                if (this.khutb_id != 0)
                    rowDiaChiTB.KHU_ID = this.khutt_id;
                if (this.dacdiemtb_id != 0)
                    rowDiaChiTB.DACDIEM_ID = this.dacdiemtt_id;
                ///

                rowDiaChiTB.SONHA = this.StringToString(this.sonhatb);
                rowDiaChiTB.DIACHI = this.StringToString(this.model.txtDiaChiTB);

                rowDCBC = Object.assign({}, rowDiaChiTB)
                rowDCBC.LOAI_DC = 3;
                this.dsDiaChi_BanCheo.push(rowDCBC);
                this.dsDiaChi.push(rowDiaChiTB);

                //Ðịa chỉ lắp đặt --> dataset dsdiachi
                let rowDCBCLD = {}
                rowDiachiLD.DIACHI_ID = this.diachild_id;
                rowDiachiLD.TINH_ID = this.tinhtt_id;
                rowDiachiLD.QUAN_ID = this.quantt_id;

                if (this.phuongld_id != 0)
                    rowDiachiLD.PHUONG_ID = this.phuongtt_id;
                if (this.phold_id != 0)
                    rowDiachiLD.PHO_ID = this.phott_id;
                if (this.apld_id != 0)
                    rowDiachiLD.AP_ID = this.aptt_id;
                if (this.khuld_id != 0)
                    rowDiachiLD.KHU_ID = this.khutt_id;
                if (this.dacdiemld_id != 0)
                    rowDiachiLD.DACDIEM_ID = this.dacdiemtt_id;

                rowDiachiLD.SONHA = this.StringToString(this.sonhald);
                rowDiachiLD.DIACHI = this.StringToString(this.model.txtDiachiLD);
                
                rowDCBCLD = Object.assign({}, rowDiachiLD)
                rowDCBCLD.LOAI_DC = 4;
                this.dsDiaChi_BanCheo.push(rowDCBCLD);
                this.dsDiaChi.push(rowDiachiLD);

                if (themmoi == true) {
                    let rowDiaChiHDTB = {}
                    rowDiaChiHDTB.DIACHI_ID = this.diachitb_id;
                    rowDiaChiHDTB.HDTB_ID = this.hdtb_id;
                    rowDiaChiHDTB.DIACHILD_ID = this.diachild_id;
                    this.dsDiaChiHDTB.push(rowDiaChiHDTB);
                }
            }
        },
        async sp_lap_hopdong_chuyenquyen(action, thuebao_cq_id, js_hdtb_data, js_hdkh_data, js_hdtt_data, js_hdkh_sub_data, js_diachi_hdkh_data,
                    js_diachi_hdtt_data, js_diachi_hdtb_data, js_ct_tienhd_data) {
            let result = "Xảy ra lỗi"
            try {
                let params = {
                    "action": action,
                    "thuebao_cq_id": thuebao_cq_id,
                    "js_hdtb_data": JSON.stringify(js_hdtb_data),
                    "js_hdkh_data": JSON.stringify(js_hdkh_data),
                    "js_hdtt_data": JSON.stringify(js_hdtt_data),
                    "js_hdkh_sub_data": JSON.stringify(js_hdkh_sub_data),
                    "js_diachi_hdkh_data": JSON.stringify(js_diachi_hdkh_data),
                    "js_diachi_hdtt_data": JSON.stringify(js_diachi_hdtt_data),
                    "js_diachi_hdtb_data": JSON.stringify(js_diachi_hdtb_data),
                    "js_ct_tienhd_data": JSON.stringify(js_ct_tienhd_data),
                }
                let response = await api.sp_lap_hopdong_chuyenquyen(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async ThemMoi_HopDongTT() {
            let rowHDKH = { HDKH_ID : this.hdkh_id }
            let result = await this.sp_lap_hopdong_chuyenquyen(2, 0, [], rowHDKH, this.dsHDTT[0], [], [], this.dsDiaChi[0], [], [])
            if (result == "OK" || result == "Thành công") {
                this.$toast.success("Thêm TT thành công")
                await this.HienThiDS_HDTT_CBO(this.hdkh_id);
                this.HienThiDanhSacHDTT(await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id));
            } else {
                this.$toast.error(result)
            }
        },
        async tsbtnThemTT_Click() {
            if (!this.enable.tsbtnThemTT) return
            try {
                this.loading(true)
                this.model.txtMaTT = "";
                if (!await this.KiemTraDL_ThanhToan()) return;

                await this.TaoDuLieu_HDTT(true);
                await this.TaoDuLieu_DiaChi(true, 2);
                await this.ThemMoi_HopDongTT();             
            } catch (exp) {
                console.log(exp)
                this.$toast.error(exp)
            } finally {
                this.loading(false)
            }
        },
        async Kiemtra_makh_mtt(maKh, kieu, loaiHdId) {
            let result = "-1"
            try {
                let params = {
                    "maKh": maKh,
                    "kieu": kieu,
                    "loaiHdId": loaiHdId
                }
                let response = await api.kiemtra_makh_matt_magd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_MAKH_THEO_MST(maSoThue, hdKhId, khachHangId) {
            let result = []
            try {
                let params = {
                    "maSoThue": maSoThue,
                    "hdKhId": hdKhId,          
                    "khachHangId": khachHangId
                }
                let response = await api.lay_ds_makh_theo_mst(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async KiemTraDL_ThanhToan() {
            //tuan code
            // kiem tra ma nhan vien thu cuoc
            if (this.model.txtMaNVTC == "") {
                this.$toast.error("Hãy nhập mã nhân viên thu cước !");
                jQuery('#tabs a')[1].click();
                return false;
            }

            if (this.khachhang_id != 0) {
                if (this.model.cboMaDD.value == null || this.model.cboMaDD.value == "") {
                    this.$toast.error("Hãy chọn mã dại diện !");
                    return false;
                }
            }

            if (this.model.txtTenTT == "") {
                this.$toast.error("Hãy nhập tên thanh toán !");
                jQuery('#tabs a')[1].click();
                $("#txtTenTT").focus();
                return false;
            }

            if (this.model.txtDiaChiTT == "" || this.quantt_id == 0 || this.phuongtt_id == 0){
                this.$toast.error("Hãy kiểm tra lại địa chỉ thanh toán !");
                jQuery('#tabs a')[1].click();
                return false;
            }

            if (this.model.txtMaTT != "") {
                let ds = await this.LAY_DB_THEO_MATB(this.model.txtMaTT, 0, 0);
                if (ds.length > 0) {
                    if (this.model.txtTenTT != ds[0].ten_tt && this.enable.tsbtnNhapMoi == false) {
                        this.$confirm('Bạn có muốn thay đổi tên thanh toán mới cho mã thanh toán cũ: ' + this.model.txtMaTT + ' không ?', {
                            confirmButtonText: 'Có', cancelButtonText: 'Không',
                        }).then(async v => {
                            if (v) {
                                return true;
                            } else {
                                await this.HienThiTT_DanhBa_KH_NhanCQ(this.model.txtMaTT);
                                return false;
                            }
                        })
                    }
                }
            }

            //Hieutc: 11.10.2011: Kiểm tra mã thanh toán đã theo đúng chuẩn chưa
            if (this.model.txtMaTT != "") {
                if (Number(await this.Kiemtra_makh_mtt(this.model.txtMaTT, 2, LoaiHopDong.CHUYEN_QUYEN)) == 0) {
                    this.$toast.error("Mã thanh toán không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                    $("#txtMaTT").focus();
                    return false;
                }
            }

            if (Number(this.model.cboHTTT.value) == HinhThucThanhToan.UYNHIEM_THU) {
                if (this.model.txtSTK_TT == "") {
                    this.$toast.error("Hãy nhập số tài khoản!");
                    $("#txtSTK_TT").focus();
                    return false;
                }
            }

            //KhaiVK: 12/06/2018: Kiểm tra mã số thuế theo chuẩn
            let maSoThue = this.model.txtMST_TT;
            if (maSoThue != "" && this.ts_kt_mst == 1) {
                let ketQua = this.KiemTraDinhDangMaSoThue(maSoThue);
                if (ketQua != "OK") {
                    this.$toast.error(ketQua);
                    $("#txtMST_TT").focus();
                    return false;
                } else {
                    let ds_maKH = await this.LAY_DS_MAKH_THEO_MST(maSoThue, this.hdkh_id, this.khachhang_id);
                    if (ds_maKH.length == 1) {
                        this.$toast.error("Mã số thuế " + maSoThue + " đang được sử dụng bởi mã khách hàng: " + ds_maKH[0].ma_kh + ".");
                        $("#txtMST_TT").focus();
                        return false;
                    }
                    else if (ds_maKH.length > 1)
                    {
                        let str = "";
                        for (let i = 0; i < ds_maKH.length; i++) {
                            str = str + ds_maKH[i].ma_kh + ",";
                        }
                        this.$toast.error("Mã số thuế " + maSoThue + " đang được sử dụng bởi các mã khách hàng: " + str.substring(0, str.length - 1) + ".");
                        $("#txtMST_TT").focus();
                        return false;
                    }
                }
            }

            return true;
        },
        KiemTraDinhDangMaSoThue(inMaSoThue){
            try {
                let ketqua = "OK";
                //Cấu trúc MST 10 số: N1N2N3N4N5N6N7N8N9N10
                //Cấu trúc MST 13 số: N1N2N3N4N5N6N7N8N9N10-N11N12N13
                if (inMaSoThue != "") {
                    if (inMaSoThue.length < 10) {
                        ketqua = "Mã số thuế tối thiểu 10 ký tự.\nBạn hãy kiểm tra lại!";
                        return ketqua;
                    }
                    if (inMaSoThue.includes(' ')) {
                        ketqua = "Mã số thuế không được có khoảng trống.\nBạn hãy kiểm tra lại!";
                        return ketqua;
                    }
                    let maSoThue = inMaSoThue.substr(0, 10);
                    if (!this.IsNumber(maSoThue)) {
                        ketqua = "Mã số thuế phải là dạng số.\nBạn hãy kiểm tra lại!";
                        return ketqua;
                    }
                    //N10 -  Chữ số kiểm tra
                    let ArrCheck = [ 31, 29, 23, 19, 17, 13, 7, 5, 3 ];
                    let checkNumber = 0;

                    for (let i = 0; i < maSoThue.length - 1; i++)
                        checkNumber += maSoThue.substr(i, 1) * ArrCheck[i];
                    if (maSoThue.substr(9, 1) != (10 - checkNumber % 11)) {
                        ketqua = "Mã số thuế không hợp lệ!.\nBạn hãy kiểm tra lại!";
                        return ketqua;
                    }
                    //Với MST 13 số
                    if (inMaSoThue.Length > 10) {
                        if (inMaSoThue.Length > 14) {
                            ketqua = "Mã số thuế không hợp lệ.\nBạn hãy kiểm tra lại!";
                            return ketqua;
                        }
                        if (inMaSoThue.substr(10, 1) != "-") {
                            //ketqua = "Mã số thuế 13 số chỉ bao gồm chữ số và ký tự -.\nBạn hãy kiểm tra lại!";
                            ketqua = "Mã số thuế 13 số không đúng định dạng theo quy định.\nCấu trúc MST 13 số: N1N2N3N4N5N6N7N8N9N10-N11N12N13 \nBạn hãy kiểm tra lại!";
                            return ketqua;
                        }
                        // Kiểm tra 3 ký
                        let maSoThue2 = inMaSoThue.substr(11, 3);
                        if (!this.IsNumber(maSoThue2)) {
                            ketqua = "3 ký tự cuối MST phải là dạng số.\nBạn hãy kiểm tra lại!";
                            return ketqua;
                        } else {
                            let n3 = parseInt(maSoThue2);
                            if (n3 < 1 && n3 > 999)
                            {
                                ketqua = "Theo quy định 3 ký tự cuối MST là các số thứ tự từ 001 đến 999.\nBạn hãy kiểm tra lại!";
                                return ketqua;
                            }
                        }
                    }
                }
                return ketqua;
            }
            catch (Exception)
            {
                return "Mã số thuế không hợp lệ!.\nBạn hãy kiểm tra lại!";
            }
        },
        async XOA_HDKH_V2(vhdkh_id, vloaihd_id) {
            let result = "Xảy ra lỗi"
            try {
                let params = {
                    "vhdkh_id": vhdkh_id,
                    "vloaihd_id": vloaihd_id
                }
                let response = await api.xoa_hdkh_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    result = "OK"
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbtnXoa_Click() {
            if (!this.enable.tsbtnXoa) return
            try {
                this.$confirm('Bạn thật sự muốn xóa hợp đồng không ?', {
                    confirmButtonText: 'Có', cancelButtonText: 'Không',
                }).then(async v => {
                    if (v) {
                        try {
                            this.loading(true)
                            let result = await this.XOA_HDKH_V2(this.hdkh_id, LoaiHopDong.CHUYEN_QUYEN);
                            if (result == "OK") {
                                this.$toast.success("Xoá hợp đồng trên Viễn thông tỉnh thành công !");
                                await this.SetButton(0);
                            } else {
                                this.$toast.error(result)
                            }          
                        } catch (e) {
                            this.$toast.error(e)
                        } finally {
                            this.loading(false)
                        }
                    }
                })
            } catch (exp) {
                this.$toast.error(exp)
            }
        },
        async TaoDuLieu_HDTB(themmoi) {
            this.dsHDTB = []

            if (themmoi == true)
                this.hdtb_id = await this.GetKey("HD_THUEBAO");
            let rowHDTB = {}

            rowHDTB.HDKH_ID = this.hdkh_id;
            rowHDTB.HDTB_ID = this.hdtb_id;
            if (themmoi == true)
                rowHDTB.HDTT_ID = this.hdtt_id;
            else
                rowHDTB.HDTT_ID = Number(this.model.cboTT.value);

            if (this.thuebao_id > 0)
                rowHDTB.THUEBAO_ID = this.thuebao_id;

            if (this.thuebao_cha_id > 0)
                rowHDTB.THUEBAO_CHA_ID = this.thuebao_cha_id;

            rowHDTB.MA_TB = this.model.txtMaTB;
            rowHDTB.TEN_TB = this.StringToString(this.model.txtTenTB);
            rowHDTB.DIACHI_TB = this.StringToString(this.model.txtDiaChiTB);
            rowHDTB.KIEULD_ID = Number(this.model.cboKieuLD.value);
            rowHDTB.DOITUONG_ID = Number(this.model.cboDoituongTB.value);
            rowHDTB.TTHD_ID = TrangThaiHD.MOI;
            rowHDTB.LOAITB_ID = this.loaitb_id;
            rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
            rowHDTB.DONVI_ID = Number(this.model.cboDaiVT.value);//Number(cboDonviQL.SelectedValue);/// TuấnNA thay thế donvi_ql -->CboDonviQL -- Ngay 29/07/2010
            rowHDTB.HDKH_ID = this.hdkh_id;
            rowHDTB.NGUOI_CN = this.$root.token.getUserName()
            rowHDTB.NGAY_CN = this.ngay_sys
            rowHDTB.MAY_CN = this.$root.token.getMachine()
            rowHDTB.IP_CN = this.$root.token.getIP()
            if (themmoi == true) {
                if (rowHDTB.DICHVUVT_ID == DichVuVienThong.DI_DONG)
                    this.status = "0";
                else if (rowHDTB.DICHVUVT_ID == DichVuVienThong.DI_DONG || rowHDTB.LOAITB_ID == LoaiHinhTB.INTERNET_MYTV)
                    this.status = "0";
                else
                    this.status = "";
                rowHDTB.STATUS = this.status;
            }
            else
                rowHDTB.STATUS = this.status;

            if (this.model.txtNgaySinhTB != "") {
                rowHDTB.NGAY_SN = this.model.txtNgaySinhTB; //tuan tao du lieu cho truong ngay sinh cua thue bao -- Ngay 06/07/2010            
            }
            rowHDTB.TBHD_PL = this.TBHD_PL; // Tuan them truong TBHD_PL cho thue bao --> 1 <-> In HD; 0 <-> In PL -- Ngay 07/07/2010
            rowHDTB.DIACHI_LD = this.StringToString(this.model.txtDiachiLD); //TuấnNA thêm dữ liệu địa chỉ lắp đặt cho dataset HDTB -- Ngày 29/07/2010
            rowHDTB.GHICHU = this.StringToString(this.model.txtGhiChuTB); //TuấnNA thêm dữ liệu ghi chú cho dataset HDTB -- Ngày 29/07/2010
            //hiếu bổ sung thêm trường đơnvidau, donvicuoi vao bảng hd_thuebao
            rowHDTB.DONVI_DAU_ID = this.donvi_dau_id;
            rowHDTB.DONVI_CUOI_ID = this.donvi_cuoi_id;
            rowHDTB.QUYTRINH_ID = this.quytrinh_id;
            //end
            ///Tuấn NA thêm code lấy giá trị datcoc_csd --- Ngày 20/10/2010
            //rowHDTB.DATCOC_CSD = 0;

            if (this.model.cboMucCuocTB.list.length > 0) {
                rowHDTB.MUCUOCTB_ID = Number(this.model.cboMucCuocTB.value);
                rowHDTB.MUCUOC_TB = Number(await this.fn_tt_muccuoc_tb(1, Number(this.model.cboMucCuocTB.value)));
            }

            this.dsHDTB.push(rowHDTB);
        },
        async XOA_HDTB(hdtbId) {
            let result = "Xảy ra lỗi"
            try {
                let params = {
                    "hdtbId": hdtbId
                }
                let response = await api.xoa_hopdong_thuebao(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    result = "OK"
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbtnXoaTB_Click() {
            if (!this.enable.tsbtnXoaTB) return
            try {
                this.$confirm('Bạn thật sự muốn xóa thuê bao không ?', {
                    confirmButtonText: 'Có', cancelButtonText: 'Không',
                }).then(async v => {
                    if (v) {
                        try {
                            this.loading(true)
                            await this.TaoDuLieu_HDTB(false);

                            let result = await this.XOA_HDTB(this.hdtb_id);
                            if (result == "OK") {
                                await this.HienThiDanhSacHDTB(await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id));
                                if (this.DsHopDongTB.data.length > 0) {
                                    this.hdtb_id = Number(this.DsHopDongTB.data[0].hdtb_id);
                                    await this.HienThiTTHopDongTB(await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.hdtb_id));
                                }
                                this.$toast.success("Xóa thuê bao thành công")
                            } else {
                                this.$toast.error(result)
                            }                  
                        } catch (e) {
                            this.$toast.error(e)
                        } finally {
                            this.loading(false)
                        }
                    }
                })
            } catch (exp) {
                this.$toast.error(exp)
            }
        },
        async tsbtnThemTB_Click() {
            if (!this.enable.tsbtnThemTB) return
            try {
                this.loading(true)
                var ds_para = {
                    THUEBAO_ID : this.thuebao_id,
                    MA_TB_CU : this.model.txtMaTB,
                    HDKH_ID : this.hdkh_id,
                    HDTT_ID : this.hdtt_id,
                    KIEULD_ID : this.model.cboKieuLD.value,
                    NGUOI_CN :  this.$root.token.getUserName(),
                    MAY_CN : this.$root.token.getMachine(),
                    IP_CN : this.$root.token.getIP(),
                }

                var row_hdtb = {
                    HDTB_ID : this.hdtb_id,
                    KIEULD_ID : this.model.cboKieuLD.value,
                    NGAY_SN : this.model.txtNgaySinhTB,
                    GHICHU : this.model.txtGhiChuTB,
                    BANCHEO : 1
                }

                // DIACHI_HDTB_DATA: []
                var row_diachitb = {
                    DIACHI_ID : null,
                    DIACHI : this.model.txtDiaChiTB,
                    TINH_ID : this.tinhtb_id,
                    QUAN_ID : this.quantb_id,
                    PHUONG_ID : this.phuongtb_id,
                    PHO_ID : this.photb_id,
                    AP_ID : this.aptb_id,
                    KHU_ID : this.khutb_id,
                    SONHA : this.sonhatb,
                    DACDIEM_ID : this.dacdiemtb_id,
                    KINHDO : "",
                    VIDO : ""
                }         
                // CHITIET_TIENHD_DATA[]
                var row_tienhd = {
                    TIEN :  this.model.txtTongTien,
                    VAT : this.model.txtTongVat,
                    KHOANMUCTT_ID : this.ds_tien_khoanmuc.length > 0 ? this.ds_tien_khoanmuc[0].khoanmuctt_id : null,
                    TYLE_VAT : null,
                    TYLE_VAT_ID : null
                }

                var pdata = {
                    "ds_para": JSON.stringify(ds_para),
                    "hd_thuebao_data":JSON.stringify(row_hdtb),
                    "ct_tienhd_data":JSON.stringify(row_tienhd),
                    "diachi_hdtb_data":JSON.stringify(row_diachitb)
                }

                api_cco.postThemTB(this.axios, JSON.stringify(pdata)).then(async(response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        var apidata = JSON.parse(response.data.data);
                        if (apidata["KETQUA"] == 1) {
                            this.$toast.success(apidata["THONGBAO"])   

                            await this.HienThiDanhSacHDTB(await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id));
                            this.ma_tb = this.model.txtMaTB;
                        } else {
                            this.$toast.error(apidata["THONGBAO"]) 
                        }             
                    } else {      
                        this.$toast.error(response.data.message) 
                    }        
                });
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async tsbtnGhiLai_Click() {
            if (!this.enable.tsbtnGhiLai) return
            try {
                this.loading(true)
                if (this.thongso_tc.kieuld_id == 0) {
                    this.$toast.error("Bạn chưa chọn kiểu lắp đặt thi công. Click kiểu lắp đặt để chọn");
                    return;
                }

                if (!await this.KiemTraDL_KhachHang()) return;
                if (!await this.KiemTraDL_ThanhToan()) return;
                if (!await this.KiemTraDL_ThueBao()) return;

                if(this.enable.tsbtnNhapMoi == false){
                    this.P_ACTION = 0  // Tạo mới
                } else {
                    this.P_ACTION = 1  // Cập nhật
                }

                // HDTB_DATA: [],
                var row_hdtb = {}
                if (this.P_ACTION == 0){
                    row_hdtb.HDTB_ID = null
                    row_hdtb.KIEULD_ID = this.model.cboKieuLD.value
                    row_hdtb.NGAY_SN = this.model.txtNgaySinhTB
                    row_hdtb.GHICHU = this.model.txtGhiChuTB
                    row_hdtb.BANCHEO = 1
                } else {
                    row_hdtb.HDTB_ID = this.hdtb_id
                    row_hdtb.KIEULD_ID = this.model.cboKieuLD.value
                    row_hdtb.NGAY_SN = this.model.txtNgaySinhTB
                    row_hdtb.GHICHU = this.model.txtGhiChuTB
                    row_hdtb.BANCHEO = 1
                }
                this.HDTB_DATA.push(row_hdtb)

                // HDKH_DATA: []
                var row_hdkh = {}
                if (this.P_ACTION == 0){
                    if (this.khachhang_id > 0){
                        row_hdkh.KHACHHANG_ID = this.khachhang_id
                        row_hdkh.BANCHEO = 1
                    } else {
                        row_hdkh.HDKH_ID = null
                        row_hdkh.TEN_KH = this.model.txtTenKH
                        row_hdkh.DIACHI_KH = this.model.txtDiaChiKH
                        row_hdkh.NGUOI_DD = this.model.txtNguoiDD
                        row_hdkh.CHUCDANH_DD = this.model.txtChucDanhDD        
                        row_hdkh.SO_DT = this.model.txtTelKH        
                        row_hdkh.SO_FAX = this.model.txtFaxKH
                        row_hdkh.WEBSITE = ""   
                        row_hdkh.NGAY_SN = this.model.txtNgaySN
                        row_hdkh.NGAYLAP_HD = this.model.dtpNgayLapHDNQ
                        row_hdkh.SO_GT = this.model.txtSoGT
                        row_hdkh.NGAYCAP = this.model.txtNgayCap
                        row_hdkh.NOICAP = this.model.txtNoiCap
                        row_hdkh.GHICHU = this.model.txtGhiChuTT
                        row_hdkh.DONVI_ID = this.$root.token.getDonViID()
                        row_hdkh.LOAIGT_ID = this.model.cboGiayToKH.value
                        row_hdkh.NHANVIEN_ID = this.$root.token.getNhanVienID()
                        row_hdkh.KHLON_ID = this.model.cboKhachHangLon.value
                        row_hdkh.LOAIHD_ID = null
                        row_hdkh.KH_KT3 = null
                        row_hdkh.MST_KH = this.model.txtMaSTKH
                        row_hdkh.HOKHAU = this.model.txtHoKhau
                        row_hdkh.LOAIKH_ID = this.model.cboLoaiKH.value
                        row_hdkh.NOICAP_HK = this.model.txtNoiCapHK
                        row_hdkh.NGAY_HK = this.model.txtNgayCapHK
                        row_hdkh.NGAY_YC = ""
                        row_hdkh.GIOITINH = this.model.rdoGT == "0" ? "Nam" : (this.model.rdoGT == "1" ? "Nữ" : "Không")
                        row_hdkh.DANTOC_ID = this.model.cboDanToc.value
                        row_hdkh.QUOCTICH_ID = this.model.cboQuocTich.value
                        row_hdkh.NGANHNGHE_ID = this.model.cboNganhNgheLoc.value
                        row_hdkh.DIACHI_NDD = this.model.txtDiaChiNDD  
                        row_hdkh.NGAYHETHAN_GT = this.model.txtNgayHetHan
                        row_hdkh.MST = this.model.txtMaSTKH
                        row_hdkh.STK = this.model.txtSoTKKH            
                        row_hdkh.NGANHANG_ID = this.model.cboNganHangKH.value    
                        row_hdkh.CTV_ID = this.chkCTV ? this.ctv_id  : null
                        row_hdkh.TNC1_ID = this.TNC1_ID
                        row_hdkh.TNC2_ID = this.TNC2_ID
                        row_hdkh.TNC3_ID = this.TNC3_ID
                        row_hdkh.TNC4_ID = this.TNC4_ID
                        row_hdkh.DACTRUNG = this.model.txtDacTrung  
                        row_hdkh.KIEUHD_ID = null
                        row_hdkh.HTTTHI_ID = null
                        row_hdkh.PHANLOAIKH_ID = this.PHANLOAIKH_ID
                        row_hdkh.EMAIL = null
                        row_hdkh.BANCHEO = 1
                    }
                } else {
                    if (this.khachhang_id > 0){
                        row_hdkh.KHACHHANG_ID = this.khachhang_id
                        row_hdkh.BANCHEO = 1
                    }         
                }
                this.HDKH_DATA.push(row_hdkh)

                // HDTT_DATA: []
                var row_hdtt = {}
                if (this.P_ACTION == 0) {
                    if (this.model.txtMaTT == "" || this.thanhtoan_id == 0){
                        row_hdtt.HDTT_ID = null
                        row_hdtt.DONVI_ID = this.model.cboDonviQL.value
                        row_hdtt.THANHTOAN_ID =  this.thanhtoan_id
                        row_hdtt.TEN_TT = this.model.txtTenTT
                        row_hdtt.SO_DT = null
                        row_hdtt.EMAIL = null
                        row_hdtt.DIACHI_TT =  this.model.txtDiaChiTT
                        row_hdtt.DIACHI_BC =  this.model.txtDiaChiBC
                        row_hdtt.DIACHI_CT = this.model.txtDiaChiCT
                        row_hdtt.MST = this.model.txtMST_TT
                        row_hdtt.HTTT_ID = this.model.cboHTTT.value
                        row_hdtt.STK = this.model.txtSTK_TT
                        row_hdtt.CHU_TK =  this.model.txtChu_TK
                        row_hdtt.NGANHANG_ID = this.model.cboNganHangTT.value
                        row_hdtt.TUYENTHU_ID = this.tuyenthu_id
                        let fDD = this.model.cboMaDD.list.find(item => item.id = this.model.cboMaDD.value) 
                        row_hdtt.MATB_DD = fDD != undefined ? fDD.ten_tat : null
                        row_hdtt.GHICHU = this.model.txtNhanVienQL
                        row_hdtt.DV_QHNS = this.DV_QHNS;
                        row_hdtt.MACHUONG = this.MACHUONG;
                        row_hdtt.MN_KT = this.MN_KT;
                        row_hdtt.MA_NDKT = this.MA_NDKT;
                        row_hdtt.MN_NSNN = this.MN_NSNN;
                        row_hdtt.TIEN_DM = this.TIEN_DM;
                        row_hdtt.BANCHEO = 1
                    } else {
                        row_hdtt.THANHTOAN_ID = this.thanhtoan_id
                        row_hdtt.BANCHEO = 1
                    }          
                } else {
                    row_hdtt.HDTT_ID = this.hdtt_id
                    row_hdtt.DONVI_ID = this.model.cboDonviQL.value
                    row_hdtt.THANHTOAN_ID =  this.thanhtoan_id
                    row_hdtt.TEN_TT = this.model.txtTenTT
                    row_hdtt.SO_DT = null
                    row_hdtt.EMAIL = null
                    row_hdtt.DIACHI_TT =  this.model.txtDiaChiTT
                    row_hdtt.DIACHI_BC =  this.model.txtDiaChiBC
                    row_hdtt.DIACHI_CT = this.model.txtDiaChiCT
                    row_hdtt.MST = this.model.txtMST_TT
                    row_hdtt.HTTT_ID = this.model.cboHTTT.value
                    row_hdtt.STK = this.model.txtSTK_TT
                    row_hdtt.CHU_TK =  this.model.txtChu_TK
                    row_hdtt.NGANHANG_ID = this.model.cboNganHangTT.value
                    row_hdtt.TUYENTHU_ID = this.tuyenthu_id
                    let fDD = this.model.cboMaDD.list.find(item => item.id = this.model.cboMaDD.value) 
                    row_hdtt.MATB_DD = fDD != undefined ? fDD.ten_tat : null
                    row_hdtt.GHICHU = this.model.txtNhanVienQL
                    row_hdtt.DV_QHNS = this.DV_QHNS;
                    row_hdtt.MACHUONG = this.MACHUONG;
                    row_hdtt.MN_KT = this.MN_KT;
                    row_hdtt.MA_NDKT = this.MA_NDKT;
                    row_hdtt.MN_NSNN = this.MN_NSNN;
                    row_hdtt.TIEN_DM = this.TIEN_DM;     
                } 
                this.HDTT_DATA.push(row_hdtt)

                // HDKHSUB_DATA: []
                var row_hdkh_sub = {}
                this.HDKHSUB_DATA = []

                // DIACHI_HDKH_DATA: []
                var row_diachikh = {}
                if (this.P_ACTION == 0){
                    row_diachikh.DIACHI_ID = null
                    row_diachikh.DIACHI = this.model.txtDiaChiKH;
                    row_diachikh.TINH_ID = this.tinhkh_id
                    row_diachikh.QUAN_ID = this.quankh_id
                    row_diachikh.PHUONG_ID = this.phuongkh_id
                    row_diachikh.PHO_ID = this.phokh_id
                    row_diachikh.AP_ID = this.apkh_id
                    row_diachikh.KHU_ID = this.khukh_id
                    row_diachikh.SONHA = this.sonhakh
                    row_diachikh.DACDIEM_ID = this.dacdiemkh_id
                    row_diachikh.KINHDO = ""
                    row_diachikh.VIDO = ""
                }
                this.DIACHI_HDKH_DATA.push(row_diachikh)

                // DIACHI_HDTT_DATA: []
                var row_diachitt = {}  
                if (this.P_ACTION == 0){
                    row_diachitt.DIACHI_ID = null
                    row_diachitt.DIACHI = this.model.txtDiaChiTT;
                    row_diachitt.TINH_ID = this.tinhtt_id
                    row_diachitt.QUAN_ID = this.quantt_id
                    row_diachitt.PHUONG_ID = this.phuongtt_id
                    row_diachitt.PHO_ID = this.phott_id
                    row_diachitt.AP_ID = this.aptt_id
                    row_diachitt.KHU_ID = this.khutt_id
                    row_diachitt.SONHA = this.sonhatt
                    row_diachitt.DACDIEM_ID = this.dacdiemtt_id
                    row_diachitt.KINHDO = ""
                    row_diachitt.VIDO = ""
                }
                this.DIACHI_HDTT_DATA.push(row_diachitt)

                // DIACHI_HDTB_DATA: []   
                var row_diachitb = {}  
                if (this.P_ACTION == 0){
                    row_diachitb.DIACHI_ID = null
                    row_diachitb.DIACHI = this.model.txtDiaChiTB
                    row_diachitb.TINH_ID = this.tinhtb_id
                    row_diachitb.QUAN_ID = this.quantb_id
                    row_diachitb.PHUONG_ID = this.phuongtb_id
                    row_diachitb.PHO_ID = this.photb_id
                    row_diachitb.AP_ID = this.aptb_id
                    row_diachitb.KHU_ID = this.khutb_id
                    row_diachitb.SONHA = this.sonhatb
                    row_diachitb.DACDIEM_ID = this.dacdiemtb_id
                    row_diachitb.KINHDO = ""
                    row_diachitb.VIDO = ""
                }
                this.DIACHI_HDTB_DATA.push(row_diachitb)

                // CHITIET_TIENHD_DATA[]
                var row_tienhd = {}
                if (this.P_ACTION == 0){
                    row_tienhd.TIEN =  this.model.txtTongTien
                    row_tienhd.VAT = this.model.txtTongVat
                    row_tienhd.KHOANMUCTT_ID = this.ds_tien_khoanmuc.length > 0 ? this.ds_tien_khoanmuc[0].khoanmuctt_id : null
                    row_tienhd.TYLE_VAT = null
                    row_tienhd.TYLE_VAT_ID = null
                }
                this.CHITIET_TIENHD_DATA.push(row_tienhd)

                var pdata = {
                    "action":this.P_ACTION,
                    "thuebao_cq_id": this.thuebao_id,
                    "js_hdtb_data":JSON.stringify(row_hdtb),
                    "js_hdkh_data":JSON.stringify(row_hdkh),
                    "js_hdtt_data":JSON.stringify(row_hdtt),
                    "js_hdkh_sub_data":JSON.stringify(row_hdkh_sub),
                    "js_diachi_hdkh_data":JSON.stringify(row_diachikh),
                    "js_diachi_hdtt_data":JSON.stringify(row_diachitt),
                    "js_diachi_hdtb_data":JSON.stringify(row_diachitb),
                    "js_ct_tienhd_data":JSON.stringify(row_tienhd),
                    "p_kieu_ld_id_tc": this.thongso_tc.kieuld_id,
                    "p_tocdo_id_tc": this.thongso_tc.tocdo_id
                }
                await api_cco.postChuyenQuyen(this.axios, JSON.stringify(pdata)).then( async (response) => {
                    if (response && response.data && response.data.message.split("_")[0].toUpperCase() == "OK") {
                        this.$toast.success("Cập nhật thông tin chuyển quyền thành công!")
                        this.model.txtMaGD = response.data.message.split("_")[1]
                        await this.txtMaGD_KeyPress({keyCode: 13})
                        await this.SetButton(3)
                    } else {      
                        this.$toast.error(response.data.message) 
                    }        
                });
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async KiemTraDL_KhachHang() {
            if (!this.model.txtNgaySN || this.model.txtNgaySN.Text == "") {
                this.$toast.error("Hãy nhập ngày sinh !");
                jQuery('#tabs a')[1].click();
                $("#txtNgaySN").focus();
                return false;
            }
            if (this.model.txtNguoiDD == "") {
                this.$toast.error("Hãy nhập tên người đại diện !");
                $("#txtNguoiDD").focus();
                return false;
            }
            if (this.model.txtNgayCap == "") {
                this.$toast.error("Hãy nhập ngày cấp giấy tờ !");
                jQuery('#tabs a')[1].click();
                $("#txtNgayCap").focus();
                return false;
            }
            if (this.model.txtSoGT == "") {
                this.$toast.error("Hãy nhập số giấy tờ !");
                jQuery('#tabs a')[1].click();
                $("#txtSoGT").focus();
                return false;
            }
            if (this.model.txtTelKH == "") {
                this.$toast.error("Hãy nhập số điện thoại khách hàng !");
                jQuery('#tabs a')[1].click();
                $("#txtTelKH").focus();
                return false;
            }
            if (this.model.txtTelKH.length > 10) {
                this.$toast.error("Số điện thoại liên hệ của khách hàng không được quá 10 ký tự.");
                jQuery('#tabs a')[1].click();
                $("#txtTelKH").focus();
                return false;
            }
            if (isNaN(this.model.txtTelKH)) {
                this.$toast.error("Số điện thoại của khách hàng phải là số!");
                jQuery('#tabs a')[1].click();
                $("#txtTelKH").focus();
                return false;
            }

            if (this.model.txtTenKH == "") {
                this.$toast.error("Hãy nhập tên khách hàng !");
                jQuery('#tabs a')[1].click();
                $("#txtTenKH").focus();
                return false;
            }
            if (this.model.txtDiaChiKH == "" || this.quankh_id == 0 || this.phuongkh_id == 0) {
                this.$toast.error("Hãy nhập địa chỉ khách hàng !");
                jQuery('#tabs a')[1].click();
                $("#txtDiaChiKH").focus();
                return false;
            }

            if (!this.model.txtMaHDCu || this.model.txtMaHDCu.Text == "") {
                this.$toast.error("Hãy nhập mã hợp đồng cũ !");
                jQuery('#tabs a')[0].click();
                $("#txtMaHDCu").focus();
                return false;
            }
            if (this.TNC1_ID == 0 || this.TNC2_ID == 0 || this.TNC3_ID == 0) {
                this.$toast.error("Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại !");
                jQuery('#tabs a')[1].click();
                return false;
            }

            //Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
            if (this.model.txtMaKH != "") {
                if (await this.Kiemtra_makh_mtt(this.model.txtMaKH, 1, LoaiHopDong.CHUYEN_QUYEN) == 0) {
                    this.$toast.error("Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                    $("#txtMaKH").focus();
                    return false;
                }
            }
            if (this.model.txtMaGD != "") {
                if (await this.Kiemtra_makh_mtt(this.model.txtMaGD, 3, LoaiHopDong.CHUYEN_QUYEN) == 0) {
                    this.$toast.error("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                    $("#txtMaGD").focus();
                    return false;
                }
            }

            return true;
        },
        async KiemTraDL_ThanhToan() {
            // kiem tra ma nhan vien thu cuoc
            if (this.model.txtMaNVTC == "") {
                this.$toast.error("Hãy nhập mã nhân viên thu cước !");
                jQuery('#tabs a')[1].click();
                $("#txtMaNVTC").focus();
                return false;
            }
            if (this.khachhang_id != 0) {
                if (this.model.cboMaDD.value == null) {
                    this.$toast.error("Hãy chọn mã dại diện !");
                    return false;
                }

                if (this.model.cboMaDD.list.length > 0) {
                    if (this.model.cboMaDD.value == null) {
                        this.$toast.error("Hãy chọn mã đại diện");
                        return false;
                    }
                }
            }

            if (this.model.txtTenTT == "") {
                this.$toast.error("Hãy nhập tên thanh toán !");
                jQuery('#tabs a')[1].click();
                $("#txtTenTT").focus();
                return false;
            }

            if (this.model.txtDiaChiTT == "" || this.quantt_id == 0 || this.phuongtt_id == 0) {
                this.$toast.error("Hãy kiểm tra lại địa chỉ thanh toán !");
                jQuery('#tabs a')[1].click();
                $("#txtDiaChiTT").focus();
                return false;
            }
            if (this.model.txtMaTT != "") {
                let ds = await this.LAY_DB_THEO_MATB(this.model.txtMaTT, 0, 0);
                if (ds.length > 0) {
                    if (this.model.txtTenTT != ds[0].ten_tt && this.enable.tsbtnNhapMoi == false) {
                        this.$confirm('Bạn có muốn thay đổi tên thanh toán mới cho mã thanh toán cũ: ' + this.model.txtMaTT + ' không ?', {
                            confirmButtonText: 'Có', cancelButtonText: 'Không',
                        }).then(async v => {
                            if (v) {
                                return true;
                            } else {
                                await this.HienThiTT_DanhBa_KH_NhanCQ(this.model.txtMaTT);
                                return false;
                            }
                        })
                    }
                }
            }

            //Hieutc: 11.10.2011: Kiểm tra mã thanh toán đã theo đúng chuẩn chưa
            if (this.model.txtMaTT != "") {
                if (Number(await this.Kiemtra_makh_mtt(this.model.txtMaTT, 2, LoaiHopDong.CHUYEN_QUYEN)) == 0) {
                    this.$toast.error("Mã thanh toán không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                    $("#txtMaTT").focus();
                    return false;
                }
            }
            if (Number(this.model.cboHTTT.value) == HinhThucThanhToan.UYNHIEM_THU) {
                if (this.model.txtSTK_TT == "") {
                    this.$toast.error("Hãy nhập số tài khoản!");
                    $("#txtSTK_TT").focus();
                    return false;
                }
            }

            //KhaiVK: 12/06/2018: Kiểm tra mã số thuế theo chuẩn
            let maSoThue = this.model.txtMST_TT;
            if (maSoThue != "" && this.ts_kt_mst == 1) {
                let ketQua = this.KiemTraDinhDangMaSoThue(maSoThue);
                if (ketQua != "OK") {
                    this.$toast.error(ketQua);
                    $("#txtMST_TT").focus();
                    return false;
                } else {
                    let ds_maKH = await this.LAY_DS_MAKH_THEO_MST(maSoThue, this.hdkh_id, this.khachhang_id);
                    if (ds_maKH.length == 1) {
                        this.$toast.error("Mã số thuế " + maSoThue + " đang được sử dụng bởi mã khách hàng: " + ds_maKH[0].ma_kh + ".");
                        $("#txtMST_TT").focus();
                        return false;
                    }
                    else if (ds_maKH.length > 1)
                    {
                        let str = "";
                        for (let i = 0; i < ds_maKH.length; i++) {
                            str = str + ds_maKH[i].ma_kh + ",";
                        }
                        this.$toast.error("Mã số thuế " + maSoThue + " đang được sử dụng bởi các mã khách hàng: " + str.substring(0, str.length - 1) + ".");
                        $("#txtMST_TT").focus();
                        return false;
                    }
                }
            }

            return true;
        },
        async KiemTraDL_ThueBao() {
            if (this.model.txtMaHDCu == "" && this.khachhang_id != 0) {
                this.$toast.error("Hãy nhập mã hợp đồng !");
                jQuery('#tabs a')[0].click();
                $("#txtMaHDCu").focus();
                return false;
            }
            if (this.model.cboKieuLD.value == null) {
                this.$toast.error("Hãy chọn kiểu yêu cầu cho thuê bao này !");
                return false;
            }
            if (this.model.txtTenTB == "") {
                this.$toast.error("Hãy nhập tên thuê bao !");
                jQuery('#tabs a')[0].click();
                $("#txtTenTB").focus();
                return false;
            }

            if (this.model.txtDiaChiTB == "" || this.quantb_id == 0 || this.phuongtb_id == 0) {
                ///TuannA: Zem code không sử dụng hàm này -- ngày 22.06.2012
                ///Theo nghiệp vụ cũ: dskh_thieudc là bảng lưu những địa chỉ chưa cập nhật 
                ///còn hiện tại tất cả các khách hàng phải ký lại nên không dùng tới hàm và bảng này.
                //laphd.CAPNHAT_KH_THIEUDC(thuebao_id, 3);
                this.$toast.error("Hãy nhập địa chỉ thuê bao !");
                $("#txtDiaChiTB").focus();
                return false;
            }

            if (this.model.txtNgaySinhTB == "") {
                this.$toast.error("Hãy nhập ngày sinh thuê bao !");
                jQuery('#tabs a')[0].click();
                $("#txtNgaySinhTB").focus();
                return false;
            }

            // hoangpkn : 31/10/2013. Thêm hàm kiểm tra gói theo yc a Thắng
            let kq = await this.KIEMTRA_THAYDOI_GOI_DADV_2(this.thuebao_id, LoaiHopDong.CHUYEN_QUYEN);
            if (kq != "1") {
                this.$toast.error(kq);
                return false;
            }
            // end hoàng thêm

            return true;
        },
        async KIEMTRA_THAYDOI_GOI_DADV_2(thueBaoId, loaiHdId) {
            let result = ""
            try {
                let params = {
                    "thueBaoId": thueBaoId,
                    "loaiHdId": loaiHdId
                }
                let response = await api.kiemtra_thaydoi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        acceptChangeCTV(value){
            if (value.nhanvien_id == undefined || value.nhanvien_id == "") {
                this.txtMaCTV = ""
                this.ctv_id = 0
                this.chkCTV = false
            } else {
                this.txtMaCTV = value.ten_ht
                this.ctv_id = value.nhanvien_id
            }   
            this.$refs.congtacvien.hide();
        },
        acceptChangeNGT(value) {
            if (value.ten_ht == undefined || value.ten_ht == "") {
                this.txtMaNGT = ""    
                this.chkNguoiGT = false
            } else {
                this.txtMaNGT = value.ten_ht
            }     
            this.$refs.nguoigioithieu.hide();
        },
        PopUpCongTacVien_Thoat() {  
            if (this.txtMaCTV == null || this.txtMaCTV == "") {
                this.ctv_id = 0
                this.chkCTV = false
            }
            this.$refs.congtacvien.hide();
        },
        PopUpNguoiGioiThieu_Thoat() {
            if (this.txtMaNGT == null || this.txtMaNGT == "") {
                this.chkNguoiGT = false
            }
            this.$refs.nguoigioithieu.hide();
        },
        tsbtnThemTB_DS_Click() {
            if (!this.enable.tsbtnThemTB_DS) return
            if (this.hdkh_id != 0) {
                this.$refs.frmTC_DBTB_KH.openDialog()
            }
        },
        async chapnhan_TC_DBTB_KH(ds_matb) {
            if (ds_matb != "") {
                try {
                    this.loading(true)
                    let dsma_tb = ds_matb.split(';');
                    if (dsma_tb.length > 0) {
                        for (let str_thuebao_id_ds of dsma_tb) {
                            if (str_thuebao_id_ds != "") {
                                await this.ThemHDThueBao_DS(str_thuebao_id_ds);
                            }
                        }

                        this.$toast.success("Thêm DS thành công") 
                        await this.HienThiDanhSacHDTB(await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id));                
                    }
                } catch (e) {
                    console.log(e)
                    this.$toast.error(e)
                } finally {
                    this.loading(false)
                }
            }
        },
        async ThemHDThueBao_DS(thuebao_id) {
            var ds_para = {
                THUEBAO_ID : thuebao_id,
                MA_TB_CU : "",
                HDKH_ID : this.hdkh_id,
                HDTT_ID : this.hdtt_id,
                KIEULD_ID : this.model.cboKieuLD.value,
                NGUOI_CN :  this.$root.token.getUserName(),
                MAY_CN : this.$root.token.getMachine(),
                IP_CN : this.$root.token.getIP(),
            }

            var pdata = {
                "ds_para": JSON.stringify(ds_para),
                "hd_thuebao_data": "",
                "ct_tienhd_data": "",
                "diachi_hdtb_data": ""
            }

            let response = await api_cco.postThemTB(this.axios, JSON.stringify(pdata));
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                let res = JSON.parse(response.data.data)
                if (res.KETQUA == 0) {
                    this.$toast.error(res.THONGBAO) 
                }
            } else {      
                this.$toast.error(response.data.message) 
            }  
        },
        async lay_thongtin_ds_tocdo_adsl() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_tocdo_adsl(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_muccuoc() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_muccuoc(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_hdtb_loaiip(hdtb_id) {
            let res = []
            try {
                let params = {
                    "hdtb_id": hdtb_id
                }
                let response = await api.lay_thongtin_ds_hdtb_loaiip(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_hdtb_kieu_nt(hdtb_id) {
            let res = []
            try {
                let params = {
                    "hdtb_id": hdtb_id
                }
                let response = await api.lay_thongtin_ds_hdtb_kieu_nt(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_thietbi_ims() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_hdtb_kieu_nt(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_trangbi() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_trangbi(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_goicuoc_dd(loaitb_id) {
            let res = []
            try {
                let params = {
                    "loaitb_id": loaitb_id
                }
                let response = await api.lay_thongtin_ds_goicuoc_dd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_kieuld(loaihd_id) {
            let res = []
            try {
                let params = {
                    "loaihd_id": loaihd_id
                }
                let response = await api.lay_thongtin_kieuld(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_dichvu_khac(dvkhac_id) {
            let res = []
            try {
                let params = {
                    "dvkhac_id": dvkhac_id
                }
                let response = await api.lay_thongtin_ds_dichvu_khac(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_loai_kh(loaikh_id) {
            let res = []
            try {
                let params = {
                    "loaikh_id": loaikh_id
                }
                let response = await api.lay_thongtin_ds_loai_kh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_phanloai_kh(hienthi_md) {
            let res = []
            try {
                let params = {
                    "hienthi_md": hienthi_md
                }
                let response = await api.lay_thongtin_ds_phanloai_kh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_tinh_tc(tinh_id) {
            let res = []
            try {
                let params = {
                    "tinh_id": tinh_id
                }
                let response = await api.lay_thongtin_tinh_tc(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_muccuoc_tb(mucuoctb_id) {
            let res = []
            try {
                let params = {
                    "mucuoctb_id": mucuoctb_id
                }
                let response = await api.lay_thongtin_ds_muccuoc_tb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async fn_tt_nganhang(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_nganhang(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_tt_hd_mig(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_hd_mig(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_tt_nhanvien(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_nhanvien(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_tt_hd_khachhang(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_hd_khachhang(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_tt_db_khachhang(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_db_khachhang(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_tt_loaihinh_tb(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_loaihinh_tb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_tt_tuyenthu(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_tuyenthu(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_tt_muccuoc_tb(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_muccuoc_tb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
    },
    watch: {      
        'model.cboLoaihinhTB.value': async function(val){
            this.cboLoaihinhTB_SelectedValueChanged()
        },
        'model.cboGiayToKH.value': async function(val){      
            this.cboGiayToKH_SelectedValueChanged()
        },
        'model.cboHTTT.value': async function(val){      
            this.cboHTTT_SelectedValueChanged()
        },
        'model.cboKieuLD.value': async function(val){      
            this.cboKieuLD_SelectedValueChanged()
        },
        'model.cboDichVuVT.value': async function(val){      
            this.cboDichVuVT_SelectedValueChanged()
        },
        'model.cboDoituongTB.value': async function(val){      
            this.cboDoituongTB_SelectedValueChanged()
        },
        'model.cboTT.value': async function(val){      
            this.cboTT_SelectedValueChanged()
        },
        'model.pnlTSL.cboTrangBi.value': async function(val){      
            this.cboTrangBi_SelectedValueChanged()
        },
        'model.pnlWan.cboTrangBiWan.value': async function(val){      
            this.cboTrangBiWan_SelectedValueChanged()
        },
        'model.pnlAdsl.cboTrangBiADSL.value': async function(val){      
            this.cboTrangBiADSL_SelectedValueChanged()
        },
        'model.pnlDD.cboTrangBiDD.value': async function(val){      
            this.cboTrangBiDD_SelectedValueChanged()
        },
        'model.pnlGphone.cboTrangBiGP.value': async function(val){      
            this.cboTrangBiGP_SelectedValueChanged()
        },
        'model.cboNganHangTT.value': async function(val){      
            this.cboNganHangTT_EditValueChanged()
        },
        'model.cboNganhNgheLoc.value': async function(val){      
            this.cboNganhNgheLoc_EditValueChanged()
        },
        'model.cboLoaiKH.value': async function(val){      
            this.cboLoaiKH2_EditValueChanged()
        },
        chkCTV:async function(val){
            if (val) {
                this.popupComponent = ()=>import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien');
                this.popupComponentName = "popupCTV";
                this.Popup('popupComponentsCQBC');
                this.enableCTV = true
            } else {
                this.enableCTV = false
            }
        },    
        chkNguoiGT: async function(val){
            if (val) {
                this.popupComponent = ()=>import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu');
                this.popupComponentName = "popupNGT";
                this.Popup('popupComponentsCQBC');
                this.enableNGT = true
            } else {
                this.enableNGT = false
            }
        },
    }
}
</script>
<style>
    .collapsed.fa-angle-up,
    .not-collapsed.fa-angle-down {
        display: none !important;
    }

    .non-ative a {
        color: #d3d3d3 !important;
    }
</style>