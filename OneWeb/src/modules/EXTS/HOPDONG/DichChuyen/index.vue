<template src="./index.html">
</template>

<script>
import Modal from './Modal/index.vue'
// import ChonHopDong from './ChonHopDong/index.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import DateTimeLib from "../../../../utils/DateTimeLib";
import { required } from 'vuelidate/lib/validators'
import Enum from "./Enum";
import { currency } from "@/filters/currency";
// import vdcService from "./service/vdcService";
// import vasc from "./service/vascService";
// import service from './service/vtnService';
// import ccbsService from './service/ccbsService';
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
const DatabaseConstants = { DB2: "CSS" };
export default {
    components: { BssErrorMarker, BssRequiredMarker, DateTimeLib, Modal, SearchContractModal },
    validations: {
    //   arrayCBO: {
    //     cboTrangThai: {
    //       required
    //     },
    //     cboKieuCC: {
    //       required
    //     },
    //     cboHoSo: {
    //         required
    //     },
    //   },
      modelInput: {
        txtMaGD: {
          required
        },
        
        // ghiChu: {
        //   maxLength: maxLength(4000)
        // }
      },
    //   arrayDate: {
    //     startDate: {
    //       required,
    //       maxValue(val, {endDate}){
    //         return new Date(endDate) >= new Date(val);
    //       }
    //     },
    //     endDate: {
    //       required,
    //     },
    //     fromDate: {
    //       required,
    //       maxValue(val, {toDate}){
    //         return new Date(toDate) >= new Date(val);
    //       }
    //     },
    //     toDate: {
    //       required,
    //     },
    //   },
        
    },
    data: function() {
        return {
            // dsThaoTac: [],
            // dsDBKH: [],
            lblTongTien: '',
            s_display_div: 1,
            activetab: 1,
            tsbtnNhapMoi: false,
            tsbtnGhiLai: false,
            tsbtnHuyBo: false,
            tsbtnXoa: false,
            tsbtnThanhToan: false,
            toolStripDropDownButton1: false,
            tsbtnThemTB: false,
            tsbtnXoaTB: false,
            tsbtnThemTB_DS: false,
            tsbtnThongTinLH: false,
            tsbtnEmail: false,
            tsbtnHen: false,
            tsbtnVatTuMoi: false,
            // kieugoi_id: 0,
            // labelFunctionTitle: '',
             modelDateTime: {
                dtpNgayLapHD: new Date(),
                dtpNgayCV: new Date(),
                dtpNgayYC: new Date(),
                dtpNgayTN: new Date(),
                dtpTuNgay : new Date(),
                dtpDenNgay: new Date(),
                dateFormat: 'dd/MM/yyyy',
                enableDate: false
             },
             modelCbo: {
                cboDonViQL: -1,
                vcboDonViQL: [],
                cboGiayToKH: -1,
                vcboGiayToKH: [],
                cboHTTT: -1,
                vcboHTTT: [],
                cboQuocTich: -1,
                vcboQuocTich: [],
                cboNganhNgheLoc: -1,
                vcboNganhNgheLoc: [],
                cboDanToc: -1,
                vcboDanToc: [],
                cboLoaiKH2: -1,
                vcboLoaiKH2: [],
                cboNganHangKH: -1,
                vcboNganHangKH: [],
                cboNganHangTT: -1,
                vcboNganHangTT: [],
                
                cboMaTbDD: -1,
                vcboMaTbDD: [],
                cboHTHSo: -1,
                vcboHTHSo: [],
                cboTrangBiCD: -1,
                vcboTrangBiCD: [],
                cboTrangBiGP: -1,
                vcboTrangBiGP: [],
                cboTrangBiIMS: -1,
                vcboTrangBiIMS: [],
                cboKhuyenMai: -1,
                vcboKhuyenMai: [],
                cboDaiVT: -1,
                vcboDaiVT: [],
                cboDoituongTB: -1,
                vcboDoituongTB: [],
                cboDichVuVT: -1,
                vcboDichVuVT: [],
                cboLoaihinhTB: -1,
                vcboLoaihinhTB: [],
                cboKieuLD: -1,
                vcboKieuLD: [],
                cboTT: -1,
                vcboTT: [],
                 cboGoiKT: -1,
                vcboGoiKT: [],
                cboMucCuocTB: -1,
                vcboMucCuocTB: [],
                cboTramCap: -1,
                vcboTramCap: [],
                cboOMC: -1,
                vcboOMC: [],
                cboTramTC: -1,
                vcboTramTC: [],
                cboKieu_HD: -1,
                vcboKieu_HD: [],
                cboLoaiTBTSL_Dau: -1,
                vcboLoaiTBTSL_Dau: [],
                cboLoaiTBTSL_Cuoi: -1,
                vcboLoaiTBTSL_Cuoi: [],
                cboDoiTac_TSL: -1,
                vcboDoiTac_TSL: [],
                cboTramVTDau: -1,
                vcboTramVTDau:[],
                cboTramVTCuoi: -1,
                vcboTramVTCuoi: [],
                cboLoaiCap_Dau: -1,
                vcboLoaiCap_Dau: [],
                cboLoaiCap_Cuoi: -1,
                vcboLoaiCap_Cuoi: [],
                cboTrangBiADSL: -1,
                vcboTrangBiADSL: [],
                cboMucCuoc: -1,
                vcboMucCuoc: [],
                cboLoaiTBAdsl: -1,
                vcboLoaiTBAdsl: [],
                cboGoiCuocAdsl: -1,
                vcboGoiCuocAdsl: []
             },
            dataSend: {},
            // pnlAdsl: false,
            // pnlDD: false,
            // dataHD: {},
            // dtThueBao: [],
            dtList: [],
            modelCkeckBox: {
                chkCongVan: false,
                chkDoiTac_TSL: false,
                chkNganHangKH: false,
                disalbeQT: false,
                disalbeDT: false,
                chkNguoiGT: false,
                chkNguoiGT: false,
                disalbeNN: false,
                chkKhuyenMai: false,
                chkdisableKhuyenMai: false,
                chkTocDo_PIR: false,
                chkDoiTacWan: false,
                chkDC_Dau: false,
                chkDC_Cuoi: false,
                chkDoiTac_TSL: false,
                ckTuCaiADSL: false,
                chkDoiTac_ADSL: false,
                vbtnThueSo: false,
                chkTocDo_PIR: false,
                chkDoiTacWan: false,
                checkVSVX: false,
                chkDC_Dau: false,
                btnChonDCDau: false,
                btnChonDCCuoi: false,
                btnMaTN_IMS: false,
                btnMaTN_ADSL: false,
                btnMaTN_Wan: false,
                btnMaTN_CD: false,

             },
             modelInput: {
                txtSoSimGphone: '',
                txtEmailAdsl: '',
                txtMATN_IMS: '',
                txtDiaChiDau: '',
                txtDiaChiCuoi: '',
                txtCuocTBI_TSL: '',
                txtCuocTCDT_TSL: '',
                txtCuocTK_DT: '',
                txtMa_HDDT_TSL: '',
                txtCuocHT_TSL: '',
                txtCuocTCDT_TSL: '',
                txtCuocTKDT_TSL: '',
                txtCuocTC_TSL: '',
                txtSoAoTSL_Cuoi: '',
                txtCuocTK_TSL: '',
                txtSoAoTSL_Dau: '',
                txtMaHD_TTKD_TSL: '',
                txtCuocTC_DT: '',
                txtMaHD_DT: '',
                txtCuocHTWan: '',
                txtSoAoWan: '',
                txtSite_ID: '',
                txtMaHD_TTKD_MGWAN: '',
                txtCuocTC_DT: '',
                txtCuocTC: '',
                txtHuongKNWan: '',
                txtCuocTK: '',
                txtCTV:'',
                txtNguoiGT: '',
                txtTongTienHD: '',
                txtMaGD: '',
                txtMaHDcu:'',
                txtMaTB: '',
                txtMaTBMoi: '',
                txtTenTB: '',
                txtDiaChiLD: '',
                txtNgaySinhTB: '',
                txtMaERP: '',
                txtDiaChiTB: '',
                txtMaBSS: '',
                txtTongTien: 0,
                txtTongVat: 0,
                txtTongTienSauThue: 0,
                txtMaKH: '',
                txtTenKH: '',
                txtNgaySN: '',
                txtNgayHetHan: '',
                txtGiayToKem: '',
                txtPhanLoaiKH: '',
                txtChucDanhDD: '',
                txtNguoiDD: '',
                txtTelKH: '',
                txtFaxKH: '',
                txtHoKhau: '',
                txtNoiCapHK: '',
                txtNgayCapHK: '',
                txtMaSTKH: '',
                txtSoTKKH: '',
                txtMaIG: '',
                txtGhiChuKH: '',
            //     txtMaTT: '',
                txtNhanVienQL: '',
                txtTenTT: '',
                txtMaNVTC: '',
                txtMaTuyen: '',
                txtDiaChiTT: '',
                txtChu_TK: '',
                txtSTKTT: '',

                txtMST: '',
                txtDiaChiCT: '',
                txtDiaChiBC: '',
                txtGhiChuTT: '',
            
                
            //     txtDiaChiTB: '',
            //     txtDiachiLD: '',

            //     txtGhiChuTB: '',
                lvDvgt:'',
                lvDvkhac:'',
                lvwThanhToan: null,
                lvDvkhac_TT: '',
                lvwThueBao: [],
            },
            checkrdo : 'rdoNo',
            buttonState: {
                tsbtnNhapMoi: false,
                tsbtnGhiLai: false,
                tsbtnHuyBo: false,
                tsbtnXoa: false,
                tsbtnThanhToan: false,
                toolStripDropDownButton1: false,
                tsbtnXoaTB: false,
                tsbtnThemTB_DS: false,
                tsbtnThongTinLH: false,
                tsbtnEmail: false,
                tsbtnHen: false,
                tsbtnVatTuMoi: false,
                btnChonSM: false
            },
            // tsbtnChuyenDB: false,
            // tsbtnHuyBo: false,
            inputValue: {
                ht_loc_cbo_tram: false,
                kt_kenhphu_ao: false,
                kt_load: true,
                nvkd_id: 0, //Nhân viên phụ trách kinh doanh
                khuvuc_id: 0,
                iKenhTN: 1,//Keenh tieep nhan hop dog (trong bangr kenh_tn)
                kieu_thueso: 0, // 0 <-> ko thuê số, 1 <-> số trượt, 2 <-> thông báo
                noidung_thueso: "",
                sothang_thueso: 0,
                kieuld_ds: 0,
                hdtb_id_huygoi_mytv: 0,
                huonggiaotn_id: 0,
                quytrinh_id_2: 0,
                dc_daucuoi: 0,
                dichvuvt_id: -1,
                httt_id: -1,
                hdkh_id: -1,
                hdtt_id: -1,
                hdtb_id: -1,
                hdkh_cha_id: 0 ,

                khachhang_id:-1,
                thanhtoan_id: -1,
                thuebao_id: -1,
                tuyenthu_id: 0,
                matb_dd: "",
                ma_tb: "",

                //Thangph: Them 07/6/2015
                ds_tien_khoanmuc: [],
                dtienld: 0,
                dvatld: 0,
                dtien_dv: 0,
                dvat_dv: 0,
                //---------------------------------

                diachikh_id: -1,
                dacdiemkh_id: -1,
                phokh_id: -1,
                khukh_id: -1,
                apkh_id: -1,
                phuongkh_id: -1,
                quankh_id: -1,
                tinhkh_id: -1,
                sonhakh: '',

                blockkh: "",
                tangkh: "",
                sophongkh: "",
                motadckh: "",

                khongcho_chon_khuvuc: false,
                batbuoc_sdt: false,  //AnhDt
                //Hieu: 2/7/2010
                kt_adsl: true,//Kiểm tra mã đôi cáp ADSL - Hiếu 22.09.2010
                khach_hang_id: 0,//biến sử dụng cho phần lắp kèm,lắp trên đường có sẵn 22.09.2010 - Hiếu
                
                TBHD_PL: 1, ///Hiếu them bien TBHD_PL  -- ngay 10/07/2010
                //private CheckDataFacade objCheckData: new CheckDataFacade(),
                trangbi_id: 3,//Defaul khách hàng tự trang bị
                kt_fiber_colapchung: false,

                khundd_id: -1,
                dacdiemndd_id: -1,
                apndd_id: -1,
                phondd_id: -1,
                phuongndd_id: -1,
                quanndd_id: -1,
                tinhndd_id: -1,
                sonhandd: '',

                khuct_id: -1,
                apct_id: -1,
                phoct_id: -1,
                dacdiemct_id: -1,
                phuongct_id: -1,
                quanct_id: -1,
                tinhct_id: -1,
                sonhact: '',

                diachitt_id: -1,
                khutt_id: -1,
                phott_id: -1,
                aptt_id: -1,
                phuongtt_id: -1,
                dacdiemtt_id: -1,
                quantt_id: -1,
                tinhtt_id: -1,
                sonhatt: '',

                blocktt: "",
                tangtt: "",
                sophongtt: "",
                motadctt: "",

                diachitb_id: -1,
                khutb_id: -1,
                photb_id: -1,
                dacdiemtb_id: -1,
                aptb_id: -1,
                phuongtb_id: -1,
                quantb_id: -1,
                tinhtb_id: -1,
                sonhatb: '',

                blocktb: "",
                tangtb: "",
                sophongtb: "",
                motadctb: "",
                // -- dia chi BC
                diachibc_id: -1,
                phobc_id: -1,
                dacdiembc_id: -1,
                apbc_id: -1,
                khubc_id: -1,
                phuongbc_id: -1,
                quanbc_id: -1,
                tinhbc_id: -1,
                sonhabc: '',
                //
                diachild_id: -1,
                phold_id: -1,
                dacdiemld_id: -1,
                khuld_id: -1,
                apld_id: -1,
                phuongld_id: 0,
                quanld_id: -1,
                tinhld_id: -1,
                sonhald: '',

                blockld: "",
                tangld: "",
                sophongld: "",
                motadcld: "",

                diachidau_id: -1,
                tinhdau_id: -1,
                phodau_id: -1,
                apdau_id: -1,
                khudau_id: -1,
                phuongdau_id: -1,
                quandau_id: -1,
                sonhadau: '',

                diachicuoi_id: -1,
                phocuoi_id: -1,
                khucuoi_id: -1,
                apcuoi_id: -1,
                phuongcuoi_id: -1,
                quancuoi_id: -1,
                tinhcuoi_id: -1,
                sonhacuoi: '',
                SoMayTN: '',

                checkdata: null,//Hiếu:10/08/2010
                // private BangThamSo bangts = new BangThamSo();
                // private DichChuyenFacade dichchuyen = new DichChuyenFacade();
                // private LapHopDongFacade laphd = new LapHopDongFacade();
                // private LapHopDongFacade2 laphd2 = new LapHopDongFacade2();
                // private TCHopDongFacade tchopdong = new TCHopDongFacade();
                // private HoanThienHoSoFacade hths = new HoanThienHoSoFacade();
                // private TraCuuDanhBaFacade tcdanhba = new TraCuuDanhBaFacade();
                // private TraCuuChungFacade tracuuchung = new TraCuuChungFacade();
                // private KhoSoFacade khoso = new KhoSoFacade();
                // private HoanCongFacade hoancong = new HoanCongFacade();

                // private ThongTinNguoiDung tt_nd = new ThongTinNguoiDung();
                // private CommonUtilities cmUlt = new CommonUtilities();
                // private ThamSo_MDFacade thamso_md = new ThamSo_MDFacade();

                
                
              
                // private HDTT_NV_DATA dsHDTT_NV = new HDTT_NV_DATA();
                m_dsThueBao_DichVu: [],
                ma_tbmoi: "",
                ne_id: 0,
                sone: "",
                //---------------------------------------------------------------------------------
                //Hiếu bổ sung thêm biến tocdo_id sử dụng trong hàm lấy tiền ld - 14/09/2010
                tocdo_id: 0,
                ltb_adsl_id: 1,//Default
                kh_cd: 0,
                ltb_adsl_cu_id: 0,
                ltb_adsl_moi_id: 0,
                tocdo_moi_id: 0,
                tocdo_cu_id: 0,
                //
                bDuLieuKS: false,
                iHdtbID: 0,

                khachhang_id_cu: 0,
                report: '',///TuấnNA khai báo biến xử lý báo cáo --- Ngày 23/08/2010

                //Hiếu bổ sung khai báo lấy đơn vị đầu, đơn vị cuối 06/09/2010
                donvi_dau_id: -1,
                donvi_cuoi_id: -1,
                ma_tb_dau: "",//Mã thuê bao đầu của thuê bao truyền số liệu
                ma_tb_cuoi: "",//mã thuê bao cuối của thuê bao truyền số liệu
                //Hiếu bổ sung phần khai báo cho hàm laytien 08/08/2010
                tien_cd: 0,
                
                CT_TIENHD_ID: -1,
                //Hiếu bổ sung phần khai báo cho hàm laytien --14/09/2010
                tien_tb: 0,
                vat_tb: 0,
                tien_dv: 0,
                vat_dv: 0,
                //private DataTable dtCTKM;//hiếu 16/09/2010: Lưu danh sách các khoản mục.
                //
                //TuấnNA thêm khai báo cho nút ngân sách nhà nước --- Ngày 12/11/2010
                // Bổ sung Tien_dm ngày 16.03.2011
                DV_QHNS: '',
                MACHUONG: '',
                MN_KT: '',
                MA_NDKT: '',
                MN_NSNN: '',
                TIEN_DM: '',
                //

                loaihd_id: -1,
                loaitb_id: -1,
                kieuld_id: -1,
                doituong_id: -1,
                ngaylhd: '',
                nhaphd_kt: '',
                

                ikieuld_id: 0,//Hiếu bs biến lưu kiểu lắp đặt đã chọn khi gõ từ ô text mã thuê bao - 23.09.2010

                //TuấnNA thêm code khai báo các biến tiểu ngành
                TNC1_ID: 0,
                TNC2_ID: 0,
                TNC3_ID: 0,
                TNC4_ID: 0,
                nganhnghe_id: 0,

                host_id: 0,
                host_cu_id: 0,

                sochinh: false,

                isdn_sochinh: false,
                matb_isdn_chinh: "",

                hdtb_id_cha: 0,
                thuebao_id_cha: 0,
                loaisochinh: 0,
                cuoc_tk: 0,
                cuoc_tc: 0,
                nhanvientc_id: 0,
                nhanvien_ql_am_id: 0,

                //private DataTable dtControl = new DataTable();
                //Label[] lbl = new Label[0];
                //ComboBox[] cbo = new ComboBox[0];

                tocdotn_id: 0, //--ttt
                muccuoctn_id: 0, //--ttt
                quytrinh_id: 0,
                luong_id: 0,

                hdmig_id: 0,
                mig_id: 0,

                _loaitb_id: -1,
                _tocdo_id: -1,

                malt_dau: "",
                malt_cuoi: "",
                //longdx: bo sung phan khuyen mai
                dtienkmld: 0,
                dvatkmld: 0,
                tong_kmld: 0,
                tyle_kmld: 0,
                vhuong_kn: "",
                vloaikenh_id: 0,
                nhap_nvkd: 0,
                check_chuahoa_cntt: false,

                KetCuoi_ID: -1,//KieuNV:Thêm chọn kết cuối

                Lat_TB: -1, Lng_TB: -1, Lat_LD: -1, Lng_LD: -1, Lat_TT: -1, Lng_TT: -1, Lat_BC: -1, Lng_BC: -1, Lat_KH: -1, Lng_KH: -1, Lat_DD: -1, Lng_DD: -1, Lat_DC: -1, Lng_DC: -1,
                ts_kt_mst: 0,

                ctv_id: -1, donvi_ctv_id: -1, loainv_ctv_id: -1,
                nguoigt_id: -1, donvi_nguoi_gt_id: -1, loainv_nguoi_gt_id: -1,

                khong_batbuoc_ctv: 0,
                batbuoc_gioithieu_dichchuyen: 0,
                tudong_ganthem_hdtb_dv: false,

                DONVIQL_HTTT: 0,
                TUYENTHU_HTTT: 0,

                ts_sinhma_gd_theo_donvi: false, //Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
                loi_sinh_magd: false,
                //#endregion

                
                khong_ht_tracuudb: 0,

                vkieukh_id_: 0, //biến toàn cục kiểm tra KH cá nhân hay doanh nghiệp        
                ts_kt_mstmns_kh: 0, //Kiểm tra MST MNS khách hàng DN 
                ts_kt_tthdkhdn: 0,//Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài

                phantap_kh: false,
                pt_doanhthu: 0,
                pt_diem_tn: 0,

                //#region HieuTc: Tối ưu code load fom
                kiemtra_dm_mgwan_tsl: false,//Danh dau de load cac thong tin danh muc Mgwan
                kiemtra_dm_codinh: false,//Danh dau de load cac thong tin danh muc Co dinh
                kiemtra_dm_IMS: false,//Danh dau de load cac thong tin danh muc IMS
                kiemtra_dm_adsl: false,//Danh dau de load cac thong tin danh muc ADSL
                kiemtra_dm_didong: false,//Danh dau de load cac thong tin danh muc ADSL
                kiemtra_dm_gphone: false,//Danh dau de load cac thong tin danh muc ADSL
                
                load_ds_nvtc: true,
                load_ds_donvi: true,
                load_ds_kmai: true,
                load_ds_tien: true,
                ktratt: 0,

                VPHIEUCSKH_ID: 0,
                VPHIEUCSKHDV_ID: 0,
                batbuoc_chon_dacdiem_dcld: false,
                //#endregion

                /// <summary>
                /// true: ca nhan; false: doanh nghiep
                /// </summary>
                kieukh_canhan: false,
                PHANLOAIKH_ID: -1,
                nhomplkh: 0,
             },
             DT_CONTROLS: {
                dichvu_vt: 0,
                list: [],
                data: [],
                value: [],
                tagvalue: [],
                key: 0,
            },
            dsDanhMuc: null,
            dsDanhMuc_mgw_tsl: null,
            dsDanhMuc_cd: null,
            dsDanhMuc_ims: null,
            dsDanhMuc_adsl: null,
            dsDanhMuc_didong: null,
            dsDanhMuc_gphone: null,
            dtPhanLoaiKH: null,
            dtLoaiKH: null,
            dsHDTB_KV: [],
            dsDK_DVGT: [],
            dsDK_DVK: [],
            dsCTM_TBI: [],
            ds_isdn: [],
            dsKhuyenMai: [],
            dsKM_HDTB: [],
            dsHDKH_Sub: [],
            dsHDTB_Sub:  [],
            dsHDKH:  [],
            dsHDTT:  [],
            dsHDTB:  [],
            dsHDTBDV:  [],
            dsHDTB_CD:  [],
            dsHDTB_GP:  [],
            dsHDTB_ADSL:  [],
            dsDiaChi:  [],
            dsHDTB_TSL:  [],
            dsHDTB_MEWAN:  [],
            dsHDTB_IMS:  [],
            dsDiaChiHDKH:  [],
            dsDiaChiHDTT:  [],
            dsDiaChiHDTB:  [],
            //Hiếu bổ sung thêm 2 dataset thông tin thanh toán & chi tiết tiền khoản mục 15/09/2010
            dsTTTT: [],
            dsCTTHD: []

        }
    },
    computed: {

    },
    methods: {
        showPopupSearchContract(){   
            this.$refs.popupSearchContract.showModal();
        },
        chkCTV_CheckedChanged: function (){
            this.$root.$toast.error('view popup “Chọn cộng tác viên” Tham chiếu UR2.1.004 task BSS-1027')
        },
        chkNguoiGT_CheckedChanged: function (){
            this.$root.$toast.error('view popup “Chọn người giới thiệu” Tham chiếu UR2.1.004 task BSS-1027')
        },
        tsbtnNhapMoi_Click: function (){
            this.NhapMoi()
        },
        tsbtnGhiLai_Click: async function (){
            await this.CapNhat();
        },
        tsbtnHuyBo_Click: function (){
            this.SetButton(0);
        },
        //#region 
        tsbtnXoa_Click: async function (){
            this.$bvModal.msgBoxConfirm(
                                    "Bạn thật sự muốn xóa hợp đồng không ?",
                                    {
                                        title: "Xác nhận hành động",
                                        centered: true,
                                        size: "sm",
                                        okTitle: 'Đồng ý',
                                        cancelTitle: 'Hủy',
                                    }
                                    )
                                    .then(async (value) => {
                                    if (value) {
                                        await this.XoaHDKH();
                                    } else {
                                        return;
                                    }
                                    });
            
        },
        XoaHDKH: async function(){
            try
            {
                await this.XOA_HDKH_V2(this.inputValue.hdkh_id, Enum.LoaiHopDong.DI_CHUYEN);
                if (this.modelInput.txtMaTBMoi.trim() != "")
                    await this.CAPNHAT_TRANGTHAISO(this.modelInput.txtMaTBMoi.trim(), 1, this.inputValue.dichvuvt_id);
                this.SetButton(0);
            }
            catch (ex)
            {
                this.$root.$toast.error("Có lỗi: " +ex.response.data.message_detail)
            }
        },
        XOA_HDKH_V2: async function(vhdkh_id, vloaihd_id){
            let data = {
                v_phanvung_id: this.$root.token.getPhanVungID(),
                vhdkh_id: vhdkh_id,
                vloaihd_id: vloaihd_id,
                PROCEDURE_NAME: 'CSS.LAP_HOP_DONG.xoa_hdkh_v2'
            }
            await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data) 
        },
        CAPNHAT_TRANGTHAISO: async function(vma_tb, vtrangthai_so, vdichvuvt_id){
            let data = {
                "ma_tb": vma_tb,
                "trangthai_so": vtrangthai_so,
                "dichvuvt_id": vdichvuvt_id
            }
            await this.$root.context.post('/web-hopdong/chuyendich/capnhat_trangthaiso', data)
        },
        //#endregion
        //#region thanh toan
        tsbtnThanhToan_Click: function (){
            // this.$root.$toast.error('Link sang màn hình “Thanh toán tiền hợp đồng” – Tham chiếu UR2.2.001 – task BSS-1115')
            let _dtDK_Goi = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_trangthaiso?hdkh_id=', this.inputValue.hdkh_id);
            if (_dtDK_Goi.data != "1")
            {
                this.$root.$toast.error(' '+_dtDK_Goi.data)
                return;
            }
            //call frmThanhToanHD
            // frmThanhToanHD f = new frmThanhToanHD(txtMaGD.Text);
            // f.MdiParent = this.MdiParent;
            // f.Show();
            // f.HienThiTTHopDongKH();
        },
        //#endregion
        toolStripDropDownButton1_Click: function (){
            this.$root.$toast.error('view popup “In hồ sơ” tham chiếu UR2.1.008 task BSS-1345')
        },
        //#region tsbtnThemTB_Click
        tsbtnThemTB_Click: function (){
            // this.$root.$toast.error('view popup “Danh sách thuê bao cùng khách hàng” tham chiếu đến task BSS-14912')
            await this.ThemHDThueBao();
        },
        ThemHDThueBao: async function(){
            try
            {
                this.inputValue.kt_adsl = true;
                if (!await this.KiemTraDL_ThueBao()) return;
                this.KiemTraDuLieuNhap();
                if (!await this.KIEMTRA_MATB_KHOSO(this.modelInput.txtMaTB, this.inputValue.dichvuvt_id))
                {
                   this.$root.$toast.error('Số máy chưa có trong kho số !');
                    //txtMaTB.Focus();
                    // this.$refs['ma_tb'].focus()
                    return;
                }

                
                if (await this.KIEMTRA_HDTB_KHI_THEMMOI(this.modelInput.txtMaTB, this.inputValue.hdkh_id, this.inputValue.dichvuvt_id, 0))
                {
                    this.$root.$toast.error("Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ");
                    return;
                }
                if (this.modelCbo.cboKieuLD.toString() == Enum.KieuLapDat.STADSL_DOI_TOCDO.toString())
                {
                    if (!await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.inputValue.thuebao_id, this.inputValue.dichvuvt_id))
                    {
                        this.$root.$toast.error("Thuê bao không tồn tại trong danh bạ, không cập nhật được ");
                        return;
                    }
                }
                else
                    if (!await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0,  this.inputValue.thuebao_id, this.inputValue.dichvuvt_id))
                    {
                        this.$root.$toast.error("Thuê bao không tồn tại trong danh bạ, không cập nhật được ");
                        return;
                    }
                if (!await this.KIEMTRA_TB_CUNG_MAKH_TB_PL(this.modelInput.txtMaGD, this.inputValue.thuebao_id))
                {
                    this.$root.$toast.error("Thuê bao không cùng mã khách hàng với thuê bao đang thực hiện chuyển dịch !");
                    return;
                }
                //Kiểm tra xem thuê bao có lập phụ lục khác không
                if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTB.trim(), "0", this.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN))
                    return;
                this.inputValue.tocdotn_id = 0;
                this.inputValue.muccuoctn_id = 0;
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.BRCD_DOITAC)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboGoiCuocAdsl;
                    if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL || this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH
                         || this.inputValue.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER)
                    {
                        this.inputValue.muccuoctn_id = this.modelCbo.cboMucCuoc;
                    }
                }
                else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGA_EYES)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboGoiCuocAdsl;
                }
                else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboTocDoKenhTSL;
                }
                else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.METRONET
                    || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MGW_DOITAC)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboTocDo;
                }
                let dset = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.DI_CHUYEN, this.modelCbo.cboLoaihinhTB, this.inputValue.tocdotn_id, this.inputValue.muccuoctn_id, this.modelCbo.cboKieuLD);

                let huonggiao_tn = 0;
                if (dset.length <= 0)
                {
                    this.$root.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                    return;
                }
                huonggiao_tn = dset[0]["HUONGGIAO_ID"].toString();
                this.inputValue.quytrinh_id = dset[0]["QUYTRINH_ID"].toString();
                //Hiếu - 22.09.2010. Kiểm tra xem tạo dữ liệu ADSL có lỗi ko.

                let kt_diachitt = 3;
                if (this.inputValue.hdkh_id != 0)
                {
                    let _ds = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id);
                    if (_ds.length > 0)
                    {
                        // IEnumerable<DataRow> _q = from DataRow r in _ds.Tables[0].Rows
                        //                           where Convert.ToInt64(r["THANHTOAN_ID"]) == this.inputValue.thanhtoan_id
                        //                           select r;
                        let _q = _ds.filter(x=>x.THANHTOAN_ID == this.inputValue.thanhtoan_id)
                        if (_q != null && _q.length > 0)
                            this.inputValue.hdtt_id = _q[0]["HDTT_ID"];
                        else
                        {
                            await this.TaoDuLieu_HDTT(true);
                            kt_diachitt = 5;
                        }
                    }
                    else
                    {
                        this.$root.$toast.error("Không tìm thấy thông tin hợp đồng khách hàng");
                        return;
                    }
                }
                else
                {
                    await this.TaoDuLieu_HDTT(true);//TuấnNA thêm code để cập nhật matb_dd trong nút thêm thuê bao -- ngày 18.03.2011
                    kt_diachitt = 5;
                }
                //Tạo dữ liệu cho thông tin thanh toán & cttienhd 17/09/2010
                await this.TaoDuLieu_HDTB(true);
                await this.TaoDuLieu_CT_TIENHD(true);
                //
                await this.TaoDuLieu_DiaChi(true, kt_diachitt);
                await this.TaoDuLieu_DKDV();
                await this.CapNhat_HDTB_ID_DangKyDV(hdtb_id);

                await this.CapNhat_HDTB_ID_HDTB_KV();

                // dsHDTB_CD = new HDTB_CD_DATA();
                // dsHDTB_ADSL = new HDTB_ADSL_DATA();
                // dsHDTB_TSL = new HDTB_TSL_DATA();
                // dsHDTB_MEWAN = new HDTB_MGWAN_DATA();
                // dsHDTB_GP = new HDTB_GP_DATA();
                // dsHDTB_IMS = new HDTB_IMS_DATA();
                // dsHDTBDV = new HDTB_DV_DATA();
                switch (this.inputValue.dichvuvt_id)
                {
                    case Enum.DichVuVienThong.CO_DINH:
                        await this.TaoDuLieu_HDTB_CD(true);
                        break;
                    case Enum.DichVuVienThong.IMS:
                    case Enum.DichVuVienThong.IMS_DOITAC:
                        await this.TaoDuLieu_HDTB_IMS(true);
                        break;
                    case Enum.DichVuVienThong.ADSL:
                    case Enum.DichVuVienThong.BRCD_DOITAC:
                        await this.TaoDuLieu_HDTB_ADSL(true);
                        break;
                    case Enum.DichVuVienThong.MEGA_EYES:
                        await this.TaoDuLieu_HDTB_ADSL(true);
                        break;
                    case Enum.DichVuVienThong.MEGAWAN:
                    case Enum.DichVuVienThong.METRONET:
                    case Enum.DichVuVienThong.MGW_DOITAC:
                        await this.TaoDuLieu_HDTB_MGWAN(true);
                        break;
                    case Enum.DichVuVienThong.TSL:
                    case Enum.DichVuVienThong.TSL_DOITAC:
                        await this.TaoDuLieu_HDTB_TSL(true);
                        break;
                    case Enum.DichVuVienThong.GPHONE:
                        await this.TaoDuLieu_HDTB_GP(true);
                        break;
                    default:
                        await this.TaoDuLieu_HDTB_CD(true);
                        break;
                }
                if (this.inputValue.kt_adsl == false)
                    return;
                if (kt_diachitt == 3)
                {
                    // Ham ThemMoi_HopDongTB 19 tham so
                    await this.ThemMoi_HopDongTB(this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, 
                                    this.dsDiaChi, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, 
                                    this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, null, this.dsHDTB_IMS, huonggiao_tn,
                                    this.sHDTB_KV, this.dsDiaChiHDTT, this.dsKM_HDTB);
                }
                else
                {
                    // Ham ThemMoi_HopDongTB 19 tham so
                    await this.ThemMoi_HopDongTB(this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, 
                                    this.sDiaChiHDTB, this.dsDK_DVGT,  this.dsDK_DVK, this.dsHDTB_MEWAN, 
                                    this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, this.dsHDTT, this.dsHDTB_IMS, 
                                    huonggiao_tn, this.dsHDTB_KV, this.dsDiaChiHDTT, this.dsKM_HDTB);
                }

                if (this.modelInput.txtMaTBMoi.trim() != "")
                    await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi.trim(), "", this.inputValue.dichvuvt_id);
                else
                    await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, "", this.inputValue.dichvuvt_id);

                this.inputValue.ma_tb = this.modelInput.txtMaTB
                this.$root.$toast.error("Thêm thuê bao thành công!");

                await this.Them_MyTV_vao_goi_giadinh();

                if (this.inputValue.tudong_ganthem_hdtb_dv)
                    await this.tudong_ganthem_hdtb_dv(this.inputValue.hdtb_id);

                await this.HienThiDS_HDTT_CBO(hdkh_id);
                let ds_hdtt_tmp = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id);
                await this.HienThiDanhSacHDTT(ds_hdtt_tmp);
                let _ds_ = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
                await this.HienThiDanhSacHDTB(_ds_);
                this.inputValue.ma_tb = this.modelInput.txtMaTB

                // IEnumerable<ListViewItem> _a = from ListViewItem i in lvwThueBao.Items
                //                                where i.SubItems[2].Text == hdtb_id.ToString()
                //                                select i;
                // if (_a.Count() > 0)
                //     _a.First().Selected = true;

                // string ma_tt = lvwThueBao.SelectedItems[0].SubItems[1].Text;

                // if (lvwThanhToan.Items.Count > 0)
                // {
                //     IEnumerable<ListViewItem> _k = from ListViewItem i in lvwThanhToan.Items
                //                                    where i.SubItems[2].Text == ma_tt
                //                                    select i;
                //     if (_k.Count() > 0)
                //         _k.First().Selected = true;
                // }
                this.inputValue.kieuld_ds = this.modelCbo.cboKieuLD;
            }
            catch (exp)
            {
                this.$root.$toast.error("Có lỗi: " +exp.response.data.message_detail)
            }
        },
        //#endregion
        //#region  tsbtnXoaTB_Click
        tsbtnXoaTB_Click: function (){
            if (this.inputValue.lvwThueBao.length <= 0)
            {
                this.$root.$toast.error('Không có dữ liệu để xóa!')
                return;
            }
            if (MessageBox.Show("Bạn thật sự muốn xóa thuê bao không ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return;
            this.$bvModal.msgBoxConfirm(
                "Bạn thật sự muốn xóa thuê bao không ?",
                {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }
                )
                .then(async (value) => {
                if (value) {
                    await this. XoaHDTB();
                } else {
                    return;
                }
                });
           
        },
        XoaHDTB: async function(){
            try
            {
                await this.XOA_HDTB(this.inputValue.hdtb_id);
                let _ds_ = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
                await this.HienThiDanhSacHDTB(_ds_);
                if (this.inputValue.lvwThueBao.length > 0)
                {
                    this.inputValue.hdtb_id = this.inputValue.lvwThueBao[0][2]//Convert.ToInt32(lvwThueBao.Items[0].SubItems[2].Text);
                    await this.HienThiTTHopDongTB();
                }
            }
            catch (exp)
            {
                this.$root.$toast.error("Có lỗi: " +exp.response.data.message_detail)
            }
        },
        XOA_HDTB: async function (hdtb_id){
            await this.$root.context.post('/web-hopdong/chuyendich/xoa_hdtb?hdtb_id=',hdtb_id)
        },
        //#endregion tsbtnXoaTB_Click
        //#region tsbtnThemTB_DS_Click 
        tsbtnThemTB_DS_Click: function (){
            //this.$root.$toast.error('(Tham chiếu đến mục 1.1.6 của UR2.1.008)')
            try
            {
                if (this.inputValue.hdkh_id != 0)
                {
                    // frmTC_DBTB_KH frm = new frmTC_DBTB_KH();
                    // frm.hdkh_id = hdkh_id;
                    // frm.khachhang_id = khachhang_id;
                    // frm.loaitb_id = loaitb_id;
                    // frm.ShowDialog();
                }
            }
            catch (exp)
            {
                this.$root.$toast.error("Có lỗi: " +exp.response.data.message_detail)
            }
        },
        tsbtnThemTB_DS_Chapnhan: async function(data){
            if (data != "")
            {
                let dsma_tb = data.split(';');
                if (dsma_tb.length > 0)
                {
                    foreach (str_thuebao_id_ds in dsma_tb)
                    {
                        if (str_thuebao_id_ds != "")
                        {
                            // dsHDTB = new HD_THUEBAO_DATA();
                            // dsHDTB_CD = new HDTB_CD_DATA();
                            // dsHDTB_ADSL = new HDTB_ADSL_DATA();
                            // dsHDTB_TSL = new HDTB_TSL_DATA();
                            // dsHDTB_MEWAN = new HDTB_MGWAN_DATA();
                            // dsHDTB_GP = new HDTB_GP_DATA();
                            // dsHDTB_IMS = new HDTB_IMS_DATA();
                            // dsHDTBDV = new HDTB_DV_DATA();

                            // dsDiaChi = new DIACHI_DATA();

                            // dsDiaChiHDTB = new DIACHI_HDTB_DATA();
                            let hdtb_id_ds = null
                            await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'HD_THUEBAO'})
                                    .then(res =>{
                                    if (res.error === "200" && res.data.length > 0)
                                        hdtb_id_ds = res.data[0].id
                                    }).catch(e =>{throw e})
                            let thuebao_id_ds = str_thuebao_id_ds;

                            let dsTT = await this.Lay_tt_chuyenquyen_theo_tbid(thuebao_id_ds);
                            if (dsTT != null && dsTT.length > 0)
                            {
                                let row = dsTT[0];
                                let dichvuvt_id_ds = row["dichvuvt_id"].toString();
                                let loaitb_id_ds = row["loaitb_id"].toString();
                                await this.ThemHDThueBao_DS(hdtb_id_ds, thuebao_id_ds, dichvuvt_id_ds, loaitb_id_ds);
                            }
                        }
                    }

                    this.$root.toastSuccess('Thêm thuê bao thành công!')
                    await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id);
                    let ds_hdtt_tmp = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id);
                    await this.HienThiDanhSacHDTT(ds_hdtt_tmp);
                    let _ds_ = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
                    await this.HienThiDanhSacHDTB(_ds_);
                }
            }
        },
        ThemHDThueBao_DS: async function(hdtb_id_ds, thuebao_id_ds, dichvuvt_id, loaitb_id){
            let huonggiaotn_id2 = 0;
            await this.HienThiTTMoRongTB(thuebao_id_ds, dichvuvt_id);

            //Tạo dữ liệu cho thông tin thanh toán & cttienhd 17/09/2010
            huonggiaotn_id2 = await this.TaoDuLieu_HDTB_DS(hdtb_id_ds, thuebao_id_ds); //done
            await this.TaoDuLieu_CT_TIENHD_DS(hdtb_id_ds, thuebao_id_ds);
            //
            await this.TaoDuLieu_DiaChi_DS(hdtb_id_ds, thuebao_id_ds);
            await this.CapNhat_HDTB_ID_DangKyDV(hdtb_id_ds); //done

            await this.CapNhat_HDTB_ID_HDTB_KV_DS(hdtb_id_ds); //done

            switch (dichvuvt_id)
            {
                case DichVuVienThong.CO_DINH:
                    TaoDuLieu_HDTB_CD_DS(hdtb_id_ds, thuebao_id_ds);
                    break;
                case DichVuVienThong.IMS:
                case DichVuVienThong.IMS_DOITAC:
                    TaoDuLieu_HDTB_IMS_DS(hdtb_id_ds, thuebao_id_ds);
                    break;
                case DichVuVienThong.ADSL:
                case DichVuVienThong.BRCD_DOITAC:
                    TaoDuLieu_HDTB_ADSL_DS(hdtb_id_ds, thuebao_id_ds);
                    break;
                case DichVuVienThong.MEGA_EYES:
                    TaoDuLieu_HDTB_ADSL_DS(hdtb_id_ds, thuebao_id_ds);
                    break;
                case DichVuVienThong.MEGAWAN:
                case DichVuVienThong.METRONET:
                case DichVuVienThong.MGW_DOITAC:
                    TaoDuLieu_HDTB_MGWAN_DS(hdtb_id_ds, thuebao_id_ds);
                    break;
                case DichVuVienThong.TSL:
                case DichVuVienThong.TSL_DOITAC:
                    TaoDuLieu_HDTB_TSL_DS(hdtb_id_ds, thuebao_id_ds);
                    break;
                case DichVuVienThong.GPHONE:
                    TaoDuLieu_HDTB_GP_DS(hdtb_id_ds, thuebao_id_ds);
                    break;
                default:
                    TaoDuLieu_HDTB_CD_DS(hdtb_id_ds, thuebao_id_ds);
                    break;
            }
            if (kt_adsl == false)
                return;
            // Ham ThemMoi_HopDongTB 18 tham so
            this.ThemMoi_HopDongTB(dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDTB, dsDK_DVGT,
                   dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, null, dsHDTB_IMS, huonggiaotn_id2,
                   dsHDTB_KV, dsDiaChiHDTT);
            Them_MyTV_vao_goi_giadinh_DS(hdtb_id_ds, thuebao_id_ds, loaitb_id);
        },
        Lay_tt_chuyenquyen_theo_tbid: async function(thuebao_id_ds){
            let res = await this.$root.context.post('/web-hopdong/chuyendich/lay_tt_chuyenquyen_theo_tbid?thuebao_id=',thuebao_id_ds)
            return res.data
        },
        TaoDuLieu_HDTB_DS: async function(hdtb_id, thuebao_id){
            let quytrinh_id_2 = 0, huonggiaotn_id = 0;
            
            let rowHDTB = {};
            rowHDTB = await this.LAY_DS_DBTB_THEO_TBID(thuebao_id);

            let dsTT = await this.Lay_tt_chuyenquyen_theo_tbid(thuebao_id);
            if (dsTT != null && dsTT.length > 0)
            {
                let row = dsTT[0];
                rowHDTB.HDTB_ID = hdtb_id;
                rowHDTB.HDTT_ID = this.inputValue.hdtt_id;

                rowHDTB.HDKH_ID = this.inputValue.hdkh_id;
                rowHDTB.THUEBAO_ID = thuebao_id;

                rowHDTB.TEN_TB = this.modelInput.txtTenTB //CommonFunction.StringToString(txtTenTB.Text);
                rowHDTB.DIACHI_TB = this.modelInput.txtDiaChiTB//CommonFunction.StringToString(txtDiaChiTB.Text);
                //Hieu 10/07/2010
                rowHDTB.DIACHI_LD = this.modelInput.txtDiaChiLD//CommonFunction.StringToString(txtDiaChiLD.Text.Trim());
                if (this.modelInput.txtNgaySinhTB != '')
                {
                    rowHDTB.NGAY_SN = DateTimeLib.toDateDisplay(this.modelInput.txtNgaySinhTB) //Convert.ToDateTime(txtNgaySinhTB.Text);
                }
                //
                if (this.inputValue.kieuld_ds != 0)
                    rowHDTB.KIEULD_ID = this.inputValue.kieuld_ds;// Convert.ToInt32(cboKieuLD.SelectedValue);
                else
                    rowHDTB.KIEULD_ID = this.modelCbo.cboKieuLD;
                rowHDTB.DOITUONG_ID = row["doituong_id"].toString();
                rowHDTB.TTHD_ID = Enum.TrangThaiHD.MOI;
               
                this.inputValue.tocdotn_id = 0;
                this.inputValue.muccuoctn_id = 0;
                if ( parseInt(this.inputValue.dichvuvt_id) == Enum.DichVuVienThong.ADSL ||  parseInt(this.inputValue.dichvuvt_id) == Enum.DichVuVienThong.BRCD_DOITAC)
                {
                    this.inputValue.tocdotn_id = parseInt(this.modelCbo.cboGoiCuocAdsl);
                    if ( parseInt(this.inputValue.loaitb_id) == Enum.LoaiHinhTB.INTERNET_ADSL ||  parseInt(this.inputValue.loaitb_id) == Enum.LoaiHinhTB.INTERNET_FTTH
                         || this.inputValue.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER)
                    {
                        this.inputValue.muccuoctn_id = this.modelCbo.cboMucCuoc;
                    }
                }
                else if ( parseInt(this.inputValue.dichvuvt_id) == Enum.DichVuVienThong.MEGA_EYES)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboGoiCuocAdsl;
                }
                else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboTocDoKenhTSL;
                }
                else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.METRONET
                    || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MGW_DOITAC)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboTocDo;
                }
                let dset = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.DI_CHUYEN, this.inputValue.loaitb_id, this.inputValue.tocdotn_id, this.inputValue.muccuoctn_id,
                    this.modelCbo.cboKieuLD);
                if (dset.length > 0)
                {
                    huonggiaotn_id = dset[0]["huonggiao_id"].toString();
                    quytrinh_id_2 = dset[0]["quytrinh_id"].toString();
                }

                rowHDTB.QUYTRINH_ID = quytrinh_id_2;
                rowHDTB.DONVI_ID = this.modelCbo.cboDaiVT;
                let ip = await this.getIp()
                rowHDTB.NGUOI_CN = this.$root.token.getUserName();
                rowHDTB.NGAY_CN = DateTimeLib.toDateDisplay(new Date());
                rowHDTB.MAY_CN = ip;
                rowHDTB.IP_CN = ip;
                rowHDTB.KIEU_TB = "0";
                //Hiếu bổ sung thêm trường status: null: không pải đồng bộ, 0: chưa pải đồng bộ
                let status = "";
                if ( parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.ADSL
                    ||  parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.IMS
                    ||  parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.BRCD_DOITAC
                    ||  parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.IMS_DOITAC)
                    status = "0";

                if (await this.KT_ThaoTac(this.inputValue.luong_id, "STATUS_TONGDAI_CD"))
                    status = "0";

                rowHDTB.STATUS = status;
                rowHDTB.GHICHU = this.modelInput.txtGhiChuTB//CommonFunction.StringToString(txtGhiChuTB.Text.Trim());
                //end
                //hiếu bổ sung thêm trường đơnvidau, donvicuoi vao bảng hd_thuebao
                rowHDTB.DONVI_DAU_ID = this.inputValue.donvi_dau_id;
                rowHDTB.DONVI_CUOI_ID = this.inputValue.donvi_cuoi_id;

                //end
                //Hiếu bổ sung trường tiền đặt cọc cước sử dụng = 0 - 20.10.2010
                
                // hoangpkn : 26/08/2013
                if (this.modelCbo.vcboMucCuocTB.length > 0)
                {
                    rowHDTB.MUCUOCTB_ID = this.modelCbo.cboMucCuocTB
                    
                    let temp = await this.MAP_MUCCUOC_TB_NEO(this.modelCbo.cboMucCuocTB)
                    rowHDTB.MUCUOC_TB = temp[0].CUOC_TB// Convert.ToDecimal(checkdata.MAP_ID("cuoc_tb", DatabaseConstants.DB2 + ".muccuoc_tb", "where mucuoctb_id = " + this.modelCbo.cboMucCuocTB)));
                }

                if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CQ ||
                    this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CQ ||
                    this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_2M || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                    this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_TUONGTU)
                {
                    if (this.inputValue.loaisochinh == 0)
                        if (this.inputValue.hdtb_id_cha > 0)
                            rowHDTB.HDTB_CHA_ID = this.inputValue.hdtb_id_cha;
                }

                if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MEGAWAN
                    || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.METRONET)
                {
                    if (thuebao_id > 0)
                    {
                        let tb_cha_id = await this.MAP_THUEBAO_CHA_ID_NEO(thuebao_id)
                        //let tb_cha_id = this.modelCbo.checkdata.MAP_ID("thuebao_cha_id", DatabaseConstants.DB2 + ".db_thuebao", "where thuebao_id = " + thuebao_id));
                        if (tb_cha_id[0].THUEBAO_CHA_ID != -1)
                            rowHDTB.THUEBAO_CHA_ID = tb_cha_id[0].THUEBAO_CHA_ID;
                    }
                }

                if (this.modelCbo.vcboGoiKT.length > 0)
                    rowHDTB.GOIKT_ID = this.modelCbo.cboGoiKT;

                this.dsHDTB.push(rowHDTB);

                foreach (row1 in this.dsKM_HDTB)
                {
                    row1.HDTB_ID = hdtb_id;
                }

            }
            return huonggiaotn_id;
        },
        TaoDuLieu_CT_TIENHD_DS: async function(hdtb_id, thuebao_id){
            let rowCTTHD = {};

            let loaitb_id = Convert.ToInt32(checkdata.MAP_ID("loaitb_id", DatabaseConstants.DB2 + ".db_thuebao",
                "where thuebao_id =" + thuebao_id));
            m_dsThueBao_DichVu = tchopdong.LAY_DS_THUEBAO_DICHVU(0, thuebao_id, loaitb_id, 1);

            for (let i = 0; i < ds_tien_khoanmuc.Tables[0].Rows.Count; i++)
            {
                if (ds_tien_khoanmuc.Tables[0].Rows[i]["khoanmuctt_id"].ToString() == KHOANMUC_TT.KMTT_DICHVU.ToString())
                {
                    for (let j = 0; j < m_dsThueBao_DichVu.Tables[0].Rows.Count; j++)
                    {
                        if (m_dsThueBao_DichVu.Tables[0].Rows[j]["dangky"].ToString() == "1"
                            && m_dsThueBao_DichVu.Tables[0].Rows[j]["huy"].ToString() == "0")
                        {
                            rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow();
                            rowCTTHD.HDTB_ID = hdtb_id;
                            CT_TIENHD_ID = new GetKeyFacade().GetKey("CT_TIENHD");
                            rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID;

                            rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DICHVU;
                            let tien = 0;//Tiền dịch vụ chưa khuyến mại
                            let vat = 0;//Vat dịch vụ chưa khuyến mại
                            tien = Convert.ToDecimal(CommonFunction.Unformat_tien(m_dsThueBao_DichVu.Tables[0].Rows[j]["cuoc_ld"].ToString()));
                            rowCTTHD.TIEN = tien;
                            vat = Convert.ToDecimal(CommonFunction.Unformat_tien(m_dsThueBao_DichVu.Tables[0].Rows[j]["vat_ld"].ToString()));
                            rowCTTHD.VAT = vat;
                            rowCTTHD.ID = Convert.ToInt64(m_dsThueBao_DichVu.Tables[0].Rows[j]["dichvugt_id"].ToString());
                            rowCTTHD.LOAI_ID = 2;
                            dsCTTHD.CT_TIENHD.AddCT_TIENHDRow(rowCTTHD);
                        }
                    }
                }
                else
                {
                    rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow();
                    rowCTTHD.HDTB_ID = hdtb_id;
                    CT_TIENHD_ID = new GetKeyFacade().GetKey("CT_TIENHD");
                    rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID;

                    rowCTTHD.KHOANMUCTT_ID = Convert.ToInt32(ds_tien_khoanmuc.Tables[0].Rows[i]["khoanmuctt_id"].ToString());
                    rowCTTHD.TIEN = Convert.ToDecimal(ds_tien_khoanmuc.Tables[0].Rows[i]["tien"].ToString());
                    rowCTTHD.VAT = Convert.ToDecimal(ds_tien_khoanmuc.Tables[0].Rows[i]["vat"].ToString());
                    if (Convert.ToInt32(ds_tien_khoanmuc.Tables[0].Rows[i]["khoanmuctt_id"].ToString()) == KHOANMUC_TT.KMTT_KM_LAPDAT
                       || Convert.ToInt32(ds_tien_khoanmuc.Tables[0].Rows[i]["khoanmuctt_id"].ToString()) == KHOANMUC_TT.KMTT_KM_THIETBI
                       || Convert.ToInt32(ds_tien_khoanmuc.Tables[0].Rows[i]["khoanmuctt_id"].ToString()) == KHOANMUC_TT.KMTT_KM_DATCOC)
                    {
                        rowCTTHD.TIEN = Math.Abs(rowCTTHD.TIEN) * -1;
                        rowCTTHD.VAT = Math.Abs(rowCTTHD.VAT) * -1;
                    }
                    rowCTTHD.ID = Convert.ToInt32(ds_tien_khoanmuc.Tables[0].Rows[i]["khoanmuctt_id"].ToString());
                    rowCTTHD.LOAI_ID = 1;
                    dsCTTHD.CT_TIENHD.AddCT_TIENHDRow(rowCTTHD);
                }
            }
        },
        TaoDuLieu_DiaChi_DS: async function(hdtb_id_ds, thuebao_id_ds){},
        CapNhat_HDTB_ID_HDTB_KV_DS: async function(hdtb_id_ds){
            for (let i = 0; i < this.dsHDTB_KV.length; i++)
            {
                this.dsHDTB_KV[i]["HDTB_ID"] = hdtb_id_ds;
            }
        },
        TaoDuLieu_HDTB_CD_DS: async function(hdtb_id_ds, thuebao_id_ds){},

        TaoDuLieu_HDTB_IMS_DS: async function(hdtb_id_ds, thuebao_id_ds){},
        TaoDuLieu_HDTB_ADSL_DS: async function(hdtb_id_ds, thuebao_id_ds){},
        TaoDuLieu_HDTB_MGWAN_DS: async function(hdtb_id_ds, thuebao_id_ds){},
        TaoDuLieu_HDTB_TSL_DS: async function(hdtb_id_ds, thuebao_id_ds){},
        TaoDuLieu_HDTB_GP_DS: async function(hdtb_id_ds, thuebao_id_ds){},
        TaoDuLieu_HDTB_GOI_DADV_DS: async function(goi_id, nhomtb_id, hdtb_id){},
        Them_MyTV_vao_goi_giadinh_DS(hdtb_id, thuebao_id, loaitb_id){
            try
            {
                if ((loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH || loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV)
                        && this.modelInput.txtMaTN_ADSL != '')
                {
                    //themMyTVvaogoi = false;
                    let ds = await this.Kiemtra_dky_family_dichuyen(this.inputValue.hdtb_id, this.modelInput.txtMaTN_ADSL.trim());
                    if (ds != null && ds.length > 0)
                    {
                        if (ds[0][0].toString() == "1")
                        {
                            let goi_id = ds[0][1].toString();
                            let nhomtb_id = ds[0][2].toString();
                            let magoi_vasc = await this.Lay_goivasc_goi_dadv(goi_id
                                                , this.modelCbo.cboGoiCuocAdsl
                                                ,this.modelCbo.cboMucCuoc);

                            if ((magoi_vasc != -1 && loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV) || loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH)
                            {
                                if (this.inputValue.themMyTVvaogoi)
                                {
                                    if (loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV)
                                    {
                                        await this.TaoDuLieu_HDTB_GOI_DADV_DS(goi_id, nhomtb_id, hdtb_id);
                                        // Ham ThemMoi_HopDongTB 2 tham so
                                        await this.ThemMoi_HopDongTB(this.dsHDTBGOI, 0);
                                    }
                                    if (loaitb_id == LoaiHinhTB.INTERNET_FTTH)
                                    {
                                        await this.TaoDuLieu_HDTB_dkygoi_mytv(ds);
                                        // Ham ThemMoi_HopDongTB 3 tham so
                                        await this.ThemMoi_HopDongTB(this.dsHDTB, this.dsHDTBGOI, this.inputValue.huonggiaotn_id);

                                    }
                                }
                            }
                        }
                    }
                }
            }
             catch (exp)
            {
                this.$root.$toast.error("Có lỗi: " +exp.response.data.message_detail)
            }
        },
        //#endregion tsbtnThemTB_DS_Click
        tsbtnThongTinLH_Click: function (){
            this.$root.$toast.error('view popup “Thông tin liên hệ” tham chiếu UR2.1.008 - task BSS-1345')
        },
        tsbtnEmail_Click: function (){
            this.$root.$toast.error('view popup “Thông tin Email” tham chiếu UR2.1.008 - task BSS-1345')
        },
        tsbtnHen_Click: function (){
            this.$root.$toast.error('view popup “Thông tin hẹn khách hàng” tham chiếu UR2.1.001 – task BSS-1024')
        },
        tsbtnVatTuMoi_Click: function (){
            this.$root.$toast.error('view popup “Cấp/Thu hồi vật tư” tham chiếu UR2.4.007 task BSS-1446')
        },
        btnLayMaGD: async function(){
            //this.$root.$toast.error('view popup “Tra cứu hợp đồng phụ lục” – Tham chiếu UR2.8.001 – task BSS-1229')
            this.$refs.popupSearchContract.showModal();
        },
        btnThueSo: async function(){},
        
        btnMaTN_ADSL: async function(){},
        btnMaTN_IMS: async function(){},
        
        btnMaTN_Wan: async function(){},
        btnTKMaTB: async function(){
             this.$root.$toast.error('view popup “Danh sách thuê bao của khách hàng chuyển dịch” tham chiếu đến task BSS-14912')
        },
        btnChonSM: async function(){
            this.$root.$toast.error('view popup “Chọn số máy” Tham chiếu UR2.1.004 task BSS-1027')
        },
        btnDiachiLD: async function(){
            this.$root.$toast.error('(Tham chiếu UR2.1.001 mục 1.1.5)')
        },
        btnDiaChiTB: async function(){
            this.$root.$toast.error('(Tham chiếu UR2.1.001 mục 1.1.5)')
        },
        btnChon_KhuyenMai: async function(){
            this.$root.$toast.error('view popup “Chọn khuyến mại” Tham chiếu UR2.1.004 task BSS-1027')
        },
        btnChiTietKM: async function(){
            this.$root.$toast.error('view popup “Đăng ký chi tiết khuyến mại” Tham chiếu UR2.1.004 task BSS-1027')
        },
        btnMaNVKD: async function(){},
        btnMaTN_CD: async function(){
            this.$root.$toast.error('view popup “chọn mã truy nhập” them chiếu đến task BSS-14912')
        },
        btnEditDVGT: async function(){
            this.$root.$toast.error('view popup “Đăng ký dịch vụ gia tăng” Tham chiếu UR2.1.004 task BSS-1027')
        },
        btnTien: async function(){
             this.$root.$toast.error('view popup “Chi tiết tiền theo khoản mục” Tham chiếu UR2.1.004 task BSS-1027')
        },
        bntLayMaKh: async function(){},
        btnTTKhac: async function(){
             this.$root.$toast.error('Tham chiếu UR2.1.004 Mục 1.4.2 - BSS-6623')
        },
        btnDiaChiKH: async function(){
            this.$root.$toast.error('Tham chiếu UR2.1.001 mục 1.1.4 - BSS-6619')
        },
        btnNganhNghe: async function(){
            this.$root.$toast.error('Tham chiếu UR2.1.001 mục 1.1.6 – BSS-6619')
        },
        btnDiaChiNDD: async function(){
            this.$root.$toast.error('Tham chiếu UR2.1.001 mục 1.1.4 - BSS-6619')
        },
        btnDiaChiTT: async function(){
            this.$root.$toast.error('Tham chiếu UR2.1.001 mục 1.1.4 - BSS-6619')    
        },
        btnDiaChiCT: async function(){
            this.$root.$toast.error('Tham chiếu UR2.1.001 mục 1.1.4 - BSS-6619')    
        },

        btnDiaChiBC: async function(){
            this.$root.$toast.error('Tham chiếu UR2.1.001 mục 1.1.4 - BSS-6619')    
        },
        btnTaoMIG: async function(){},
        btnTimMaNVTC: async function(){
            this.$root.$toast.error('(Tham chiếu UR2.1.004 mục 1.4.1 - BSS-1027)')
        },
        // btnDiaChiCT: async function(){},
        // btnDiaChiCT: async function(){},
        // btnDiaChiCT: async function(){},
        // btnDiaChiCT: async function(){},
        // btnDiaChiCT: async function(){},
        // btnDiaChiCT: async function(){},
        NhapMoi: function() {
            this.activetab = 1;
            this.SetButton(1);
        },
        cbb_selectedChanged: async function(){},
        chkKhuyenMai_CheckedChanged: async function(){},
        chkCongVan_CheckedChanged: async function(){},
        chkDC_NoiBo: async function(){},
        acceptSearchContract: async function(item) {
            this.modelInput.txtMaGD = item.ma_gd;
            if(this.modelInput.txtMaGD && this.modelInput.txtMaGD.trim()!="")
                this.onEnterMaGD();
        },
        lay_ds_thuebao_co_cquyen: async function(){
            let res = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_thuebao_co_cquyen')
            return res.data
        },
        KiemTraDuLieuNhap: function(){
            return true;
        },
        KIEMTRA_MATB_KHOSO: async function(ma_tb, dichvuvt_id){
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/kiemtra_matb_khoso?ma_tb=${ma_tb}&dichvuvt_id=${dichvuvt_id}`)
            return res.data
        },
        KIEMTRA_DB_KHI_THEMMOI: async function(ma_tb, khachhang_id, thuebao_id, dichvuvt_id){
            let data = {
                ma_tb: ma_tb,
                khachhang_id: khachhang_id,
                thuebao_id: thuebao_id,
                dichvuvt_id: dichvuvt_id
            }
            let res = await this.$root.context.get('/web-hopdong/chuyendich/kiemtra_db_khi_themmoi', data)
            return res.data
        },
        IsNullOrEmpty: function (value) {
            return (
                value == undefined || value == null || value.toString().trim() == ""
            );
        },
        KIEMTRA_MA_TB_LAP_HOPDONG_KHAC: async function(ma_tb, ma_gd, vdichvuvt_id, vloaihd_id)
        {
            let result = false;
            let data = {
                ma_tb: ma_tb,
                ma_gd: ma_gd,
                dichvuvt_id: vdichvuvt_id,
                loaihd_id: vloaihd_id
            }
            try
            {
                if (ma_tb != '')
                {
                    let ds = []
                    ds = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac', data)
                    if (ds.data.length > 0)
                    {
                        result = true;
                        let message =
                                "Số máy/Acc: "+ma_tb+ " đang được lập bởi hợp đồng (phụ lục) "
                                +ds[0]["TEN_LOAIHD"]+" có mã giao dịch "
                                +ds[0]["MA_GD"]+" do User "
                                +ds[0]["NGUOI_CN"]+" thuộc đơn vị "
                                +ds[0]["TEN_DV"]+" thực hiện vào ngày "
                                +ds[0]["NGAY_YC"]+"";

                       this.$root.$toast.error(message);
                    }
                }

                return result;
            }
            catch (ex)
            {
                if (ex.toString().includes("discarded"))
                {
                    return await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac', data)
                }

                return result;
            }
        },
        LayHuongGiao_TiepNhan: async function(loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id){
            let data = {
                loaihd_id: loaihd_id, 
                loaitb_id: loaitb_id, 
                tocdo_id: tocdo_id, 
                muccuoc_id: muccuoc_id, 
                kieuld_id:kieuld_id
            }
            let res = await this.$root.context.post('/web-hopdong/chuyendich/layhuonggiao_tiepnhan', data)
            return res.data
        },
        LayHuongGiao_TiepNhan_2: async function(loaihd_id, kieuld_id){
            let data = {
                loaihd_id: loaihd_id, 
                loaitb_id: loaitb_id, 
                tocdo_id: tocdo_id, 
                muccuoc_id: muccuoc_id, 
                kieuld_id:kieuld_id
            }
            let res = await this.$root.context.post('/web-hopdong/chuyendich/layhuonggiao_tiepnhan_2', data)
            return res.data
        },
        kt_fiber_colapchung: async function(kieu, thuebao_id, hdtb_id, hdkh_id){
            let data = {
                kieu: kieu,
                thuebao_id: thuebao_id,
                hdtb_id: hdtb_id,
                hdkh_id: hdkh_id
            }
            let res = await this.$root.context.post('/web-hopdong/chuyendich/kt_fiber_colapchung', data)
            return res.data
        },
        KT_ThaoTac: async function(luong_id, code){
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/kt_thaotac?luong_id=${luong_id}&code=${code}`)
            if( res.data.length > 0)
                return true;
            else 
                return false;
        },
        Lay_Ma_GD_V2: async function(donvi_id, loaihd_id){
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_magd_v2?donvi_id=${donvi_id}&loaihd_id=${loaihd_id}`)
            return res.data
        },
        Lay_Ma_GD: async function(loaihd_id){
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_magd?loaihd_id=${loaihd_id}`)
            return res.data
        },
        //#region  Tao du lieu cho bang HD_KHACHHANG
        TaoDuLieu_HDKH: async function(themmoi)
        {
            let vmagd_temp = "";
            this.dsHDKH = [];
            if (themmoi == true)
                await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'HD_KHACHHANG'})
                                    .then(res =>{
                                    if (res.error === "200" && res.data.length > 0)
                                        this.inputValue.hdkh_id = res.data[0].id
                                    }).catch(e =>{throw e})
            let rowHDKH = {}
            rowHDKH.HDKH_ID = this.inputValue.hdkh_id;
            if (themmoi == true)
            {
                if (this.inputValue.ts_sinhma_gd_theo_donvi)
                    vmagd_temp = await this.Lay_Ma_GD_V2(this.$root.token.getDonViID(), Enum.LoaiHopDong.DI_CHUYEN); //s.PadLeft(1000, ' ');
                else
                    vmagd_temp = await this.Lay_Ma_GD(Enum.LoaiHopDong.DI_CHUYEN); //s.PadLeft(1000, ' ');
                if (vmagd_temp.includes("ERROR:"))
                {
                    this.$root.$toast.error(vmagd_temp.split(':')[1]);
                    this.inputValue.loi_sinh_magd = true;
                }
                this.modelInput.txtMaGD = vmagd_temp;

                rowHDKH.MA_GD = this.modelInput.txtMaGD;
                
            }
            else
            {
                rowHDKH.MA_GD = this.modelInput.txtMaGD;
            }
            rowHDKH.MA_HD = this.modelInput.txtMaHDcu;
            
            rowHDKH.NGAY_YC = this.modelDateTime.dtpNgayYC;
            rowHDKH.MA_KH = this.modelInput.txtMaKH;
            if (this.inputValue.khachhang_id > 0)
                rowHDKH.KHACHHANG_ID = this.inputValue.khachhang_id;
            else
                rowHDKH.KHACHHANG_ID = 0;
            rowHDKH.TEN_KH = this.StringToString(this.modelInput.txtTenKH);
            rowHDKH.DIACHI_KH = this.StringToString(this.modelInput.txtDiaChiKH);
            rowHDKH.NGUOI_DD = this.StringToString(this.modelInput.txtNguoiDD);
            rowHDKH.SO_DT = this.modelInput.txtTelKH;
            rowHDKH.CHUCDANH_DD = this.StringToString(this.modelInput.txtChucDanhDD);
            rowHDKH.SO_FAX = this.modelInput.txtFaxKH;

            if (this.modelInput.txtNgaySN != '')
            {
                rowHDKH.NGAY_SN = DateTimeLib.toDateDisplay(this.modelInput.txtNgaySN);
            }
            rowHDKH.NGAYLAP_HD = this.modelDateTime.dtpNgayLapHD;
            rowHDKH.LOAIGT_ID = this.modelCbo.cboGiayToKH;
            rowHDKH.SO_GT = this.modelInput.txtSoGT;


            if (this.modelInput.txtNgayCap != '')
            {
                rowHDKH.NGAYCAP = DateTimeLib.toDateDisplay(this.modelInput.txtNgayCap);
            }
            rowHDKH.NOICAP = this.StringToString(this.modelInput.txtNoiCap);
            rowHDKH.SO_FAX = this.modelInput.txtFaxKH;
            rowHDKH.DONVI_ID = this.$root.token.getDonViID();
            rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID()
            rowHDKH.HOKHAU = this.StringToString(this.modelInput.txtHoKhau);

            if (this.modelInput.txtNgayCapHK != '')
            {
                rowHDKH.NGAY_HK = DateTimeLib.toDateDisplay(this.modelInput.txtNgayCapHK);
            }
            rowHDKH.NOICAP_HK = this.StringToString(this.modelInput.txtNoiCapHK);
      

            rowHDKH.KHLON_ID = 1;
            rowHDKH.LOAIKH_ID = this.modelCbo.cboLoaiKH;

            if (PHANLOAIKH_ID != -1 && PHANLOAIKH_ID != 0)
                rowHDKH.PHANLOAIKH_ID = this.inputValue.PHANLOAIKH_ID;
            let ip = await this.getIp()
            rowHDKH.LOAIHD_ID = Enum.LoaiHopDong.DI_CHUYEN;
            rowHDKH.NGUOI_CN = this.$root.token.getUserName();
            rowHDKH.NGAY_CN = DateTimeLib.toDateDisplay(new Date());
            rowHDKH.MAY_CN = ip;
            rowHDKH.IP_CN = ip;
            //Hiếu thêm : 10/08/2010
            rowHDKH.MST = this.modelInput.txtMaSTKH;
            rowHDKH.STK = this.modelInput.txtSoTKKH;
            if (this.modelInput.txtNgayHetHan != '')
            {
                rowHDKH.NGAYHETHAN_GT = DateTimeLib.toDateDisplay(this.modelInput.txtNgayHetHan);
            }
            
            if (this.checkrdo === 'rdoF') {
                rowHDKH.GIOITINH = 0;
            }
            else if (this.checkrdo === 'rdoM'){
                rowHDKH.GIOITINH = 1;
            }
            rowHDKH.NGANHNGHE_ID = this.modelCbo.cboNganhNgheLoc;

            //TuấnNA thêm tạo dữ liệu tiểu ngành cấp 1,2,3 --- Ngày 03/12/2010           
            rowHDKH.TNC1_ID = this.inputValue.TNC1_ID;
            rowHDKH.TNC2_ID = this.inputValue.TNC2_ID;
            rowHDKH.TNC3_ID = this.inputValue.TNC3_ID;
            rowHDKH.TNC4_ID = this.inputValue.TNC4_ID;

            rowHDKH.QUOCTICH_ID = this.modelCbo.cboQuocTich;
   
            rowHDKH.DANTOC_ID = this.modelCbo.cboDanToc;

            rowHDKH.DIACHI_NDD = this.StringToString(this.modelInput.txtDiaChiNDD);
            if (this.modelCkeckBox.chkNganHangKH)
            {
                rowHDKH.NGANHANG_ID = this.modelCbo.cboNganHangKH;
            }
            rowHDKH.GHICHU = this.StringToString(this.modelInput.txtGhiChuKH);
            rowHDKH.KIEUHD_ID = this.modelCbo.cboKieu_HD;
            if (this.inputValue.hdkh_cha_id > 0)
            {
                rowHDKH.HDKH_CHA_ID = this.inputValue.hdkh_cha_id;
            }

            if (this.modelCkeckBox.chkCTV && this.modelInput.txtCTV != '')
            {
                rowHDKH.CTV_ID = this.inputValue.ctv_id;
            }
            if (this.modelCkeckBox.chkNguoiGT && this.modelInput.txtNguoiGT != '')
            {
                rowHDKH.NHANVIENGT_ID = this.inputValue.nguoigt_id;
            }
            rowHDKH.KENHTN_ID = this.inputValue.iKenhTN;
            //PNLINH B2A 02/05/2020
            if (this.inputValue.VPHIEUCSKH_ID != 0)
            {
                rowHDKH.PHIEUCSKH_ID = this.inputValue.VPHIEUCSKH_ID;
            }

            this.dsHDKH.push(rowHDKH);
        },
        TAO_DULIEU_HDKH_SUB: function(ds_hdkh_sub, v_hdkh_id)
        {
            let row = {};
            row.CHUCVU_GT = ds_hdkh_sub[0]["ChucVu_GT"].toString();
            row.HDKH_ID = v_hdkh_id;
            if (ds_hdkh_sub[0]["NgayCap_GT"]  != null)
                row.NGAYCAP_GT = DateTimeLib.toDateDisplay(ds_hdkh_sub[0]["NgayCap_GT"]);
            row.NGUOI_GT = ds_hdkh_sub[0]["Nguoi_GT"].toString();
            if (ds_hdkh_sub[0]["So_Phong"] != null && ds_hdkh_sub[0]["So_Phong"].toString() != "")
                row.SO_PHONG = ds_hdkh_sub[0]["So_Phong"].toString();

            row.SOGIAY_GT = ds_hdkh_sub[0]["SoGiay_GT"].toString();
            row.MA_NS = ds_hdkh_sub[0]["MA_NS"].toString();
            if (this.inputValue.ts_kt_tthdkhdn == 1)
            {
                row.SO_HD_KHDN = ds_hdkh_sub[0]["SO_HD_KHDN"].toString();
                row.MA_KHDN = ds_hdkh_sub[0]["MA_KHDN"].toString();
                if (ds_hdkh_sub[0]["GIATRI_HD"] != null)
                {
                    row.GIATRI_HD = ds_hdkh_sub[0]["GIATRI_HD"].toString();
                }
                if (ds_hdkh_sub[0]["NGAY_KY_HD"] != null)
                {
                    row.NGAY_KY_HD =  DateTimeLib.toDateDisplay(ds_hdkh_sub[0]["NGAY_KY_HD"]);
                }
                row.GIAY_UQ = ds_hdkh_sub[0]["GIAY_UQ"].toString();
                if (ds_hdkh_sub[0]["LOAIHDKH_ID"] != null && ds_hdkh_sub[0]["LOAIHDKH_ID"].toString() != "-1")
                {
                    row.LOAIHDKH_ID = ds_hdkh_sub[0]["LOAIHDKH_ID"];
                }
                if (ds_hdkh_sub[0]["TINHTRANGHD_ID"] != null && ds_hdkh_sub[0]["TINHTRANGHD_ID"].toString() != "-1")
                {
                    row.TINHTRANGHD_ID = ds_hdkh_sub[0]["TINHTRANGHD_ID"];
                }
            }
            this.dsHDKH_Sub.push(row)
        },
        TaoDuLieu_HDTT: async function(themmoi){
            this.dsHDTT = [];

            if (themmoi == true)
                await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'HD_THANHTOAN'})
                                    .then(res =>{
                                    if (res.error === "200" && res.data.length > 0)
                                        this.inputValue.hdtt_id = res.data[0].id
                                    }).catch(e =>{throw e})
            let rowHDTT = {}
            rowHDTT.HDTT_ID = this.inputValue.hdtt_id;
            rowHDTT.HDKH_ID = this.inputValue.hdkh_id;
            if (this.modelInput.txtMaTT == "")
            {
                let ma_tt = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_matt?donvi_id=${this.modelCbo.cboDonViQL}&quan_id=${this.inputValue.quanld_id}`)
                this.modelInput.txtMaTT = ma_tt.data
                this.inputValue.thanhtoan_id = 0;
            }
            if (this.inputValue.thanhtoan_id > 0)
                rowHDTT.THANHTOAN_ID = this.inputValue.thanhtoan_id;
            rowHDTT.MA_TT = this.modelInput.txtMaTT;
            rowHDTT.TEN_TT = this.StringToString(this.modelInput.txtTenTT);
            rowHDTT.DIACHI_TT = this.StringToString(this.modelInput.txtDiaChiTT);
            rowHDTT.DIACHI_BC = this.StringToString(this.modelInput.txtDiaChiBC);
            rowHDTT.MST = this.modelInput.txtMST;
            rowHDTT.HTTT_ID = this.modelCbo.cboHTTT;
            if (this.modelCbo.cboHTTT == Enum.HinhThucThanhToan.UYNHIEM_THU)
            {
                rowHDTT.NGANHANG_ID = this.modelCbo.cboNganHangTT;
            }
            rowHDTT.STK = this.modelInput.txtSTKTT;
            rowHDTT.CHU_TK = this.modelInput.txtChu_TK;
            rowHDTT.DONVI_ID = this.modelCbo.cboDonViQL;
            if (tuyenthu_id != 0)
                rowHDTT.TUYENTHU_ID = tuyenthu_id;
            if (this.modelCbo.cboMaDD != -1)
                rowHDTT.MATB_DD = this.modelCbo.cboMaDD
            else
                rowHDTT.MATB_DD = "";
            let ip = await this.getIp()
            rowHDTT.NGUOI_CN = this.$root.token.getUserName();
            rowHDTT.NGAY_CN = DateTimeLib.toDateDisplay(new Date());
            rowHDTT.MAY_CN = ip;
          

            //Hiếu bổ sung thêm column HDTT
            rowHDTT.DIACHI_CT = this.StringToString(this.modelInput.txtDiaChiCT);

            rowHDTT.GHICHU = this.StringToString(this.modelInput.txtGhiChuTT.trim());
            ///TuấnNA thêm code lấy tuyenthu_id --- Ngày 20/09/2010
            rowHDTT.TUYENTHU_ID = this.inputValue.tuyenthu_id;

            //TuấnNA thêm code tạo dữ liệu NSNN --- Ngày 12/11/2010
            let loai_nh = "";
            if (this.modelCbo.cboNganHangTT != -1)
            {
                let temp = await this.MAP_LOAI_NH_ID_NEO(this.modelCbo.cboNganHangTT)// obj.MAP_ID("loai_nh", DatabaseConstants.DB2 + ".nganhang", "where nganhang_id =" + cboNganHangTT.EditValue);
                loai_nh = temp[0].LOAI_NH
            }
            if (loai_nh == "2")
            {
                rowHDTT.DV_QHNS = this.inputValue.DV_QHNS;
                rowHDTT.MACHUONG = this.inputValue.MACHUONG;
                rowHDTT.MN_KT = this.inputValue.MN_KT;
                rowHDTT.MA_NDKT = this.inputValue.MA_NDKT;
                rowHDTT.MN_NSNN = this.inputValue.MN_NSNN;
                rowHDTT.TIEN_DM = this.inputValue.TIEN_DM;
            }
            else
            {
                rowHDTT.DV_QHNS = "";
                rowHDTT.MACHUONG = "";
                rowHDTT.MN_KT = "";
                rowHDTT.MA_NDKT = "";
                rowHDTT.MN_NSNN = "";
                rowHDTT.TIEN_DM = 0;
            }
            //

            dsHDTT.push(rowHDTT);

            
        },
        TaoDuLieu_HDTB: async function(themmoi){
            //#region HD_THUEBAO_DATA
            this.dsHDTB = [];

            if (themmoi == true)
                await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'HD_THUEBAO'})
                                    .then(res =>{
                                    if (res.error === "200" && res.data.length > 0)
                                        this.inputValue.hdtb_id = res.data[0].id
                                    }).catch(e =>{throw e})
            let rowHDTB = {};

            rowHDTB.HDTB_ID = this.inputValue.hdtb_id;
            if (themmoi == true)
                rowHDTB.HDTT_ID = this.inputValue.hdtt_id;
            else
                rowHDTB.HDTT_ID = this.modelCbo.cboTT;
            rowHDTB.HDKH_ID = this.inputValue.hdkh_id;
            if (this.inputValue.thuebao_id > 0)
                rowHDTB.THUEBAO_ID = this.inputValue.thuebao_id;
            //Trường hợp không chọn số máy mới
            if (this.modelInput.txtMaTBMoi.trim() == "")
                rowHDTB.MA_TB = this.modelInput.txtMaTB;
            else
                rowHDTB.MA_TB = this.modelInput.txtMaTBMoi;

            rowHDTB.TEN_TB = this.StringToString(this.modelInput.txtTenTB);
            rowHDTB.DIACHI_TB = this.StringToString(this.modelInput.txtDiaChiTB);
            //Hieu 10/07/2010
            rowHDTB.DIACHI_LD = this.StringToString(this.modelInput.txtDiaChiLD.trim());
            if (this.modelInput.txtNgaySinhTB != '')
            {
                rowHDTB.NGAY_SN = DateTimeLib.toDateDisplay(this.modelInput.txtNgaySinhTB);
            }

            rowHDTB.KIEULD_ID = this.modelCbo.cboKieuLD;
            rowHDTB.DOITUONG_ID = this.modelCbo.cboDoituongTB;
            rowHDTB.TTHD_ID = Enum.TrangThaiHD.MOI;
            rowHDTB.LOAITB_ID = this.inputValue._loaitb_id;// Convert.ToInt32(cboLoaihinhTB.SelectedValue);
            rowHDTB.DICHVUVT_ID = this.inputValue.dichvuvt_id;
            rowHDTB.QUYTRINH_ID = this.inputValue.quytrinh_id;
            
            rowHDTB.DONVI_ID = this.modelCbo.cboDaiVT;


            rowHDKH.LOAIHD_ID = Enum.LoaiHopDong.DI_CHUYEN;
            let ip = await this.getIp()
            rowHDKH.NGUOI_CN = this.$root.token.getUserName();
            rowHDKH.NGAY_CN = DateTimeLib.toDateDisplay(new Date());
            rowHDKH.MAY_CN = ip;
            rowHDKH.IP_CN = ip;
            
            //Hiếu bổ sung thêm trường status: null: không pải đồng bộ, 0: chưa pải đồng bộ
            let status = "";
            if ( parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.ADSL
                ||  parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.IMS
                ||  parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.IMS_DOITAC
                ||  parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.BRCD_DOITAC)
                status = "0";

            if (await this.KT_ThaoTac(this.inputValue.luong_id, "STATUS_TONGDAI_CD"))
                status = "0";

            rowHDTB.STATUS = status;
            rowHDTB.GHICHU = this.StringToString(this.modelInput.txtGhiChuTB.trim());
            //end
            //hiếu bổ sung thêm trường đơnvidau, donvicuoi vao bảng hd_thuebao
            rowHDTB.DONVI_DAU_ID = this.inputValue.donvi_dau_id;
            rowHDTB.DONVI_CUOI_ID = this.inputValue.donvi_cuoi_id;

            //end
            //Hiếu bổ sung trường tiền đặt cọc cước sử dụng = 0 - 20.10.2010
            
            // hoangpkn : 26/08/2013
            if (this.modelCbo.vcboMucCuocTB.length > 0)
            {
                rowHDTB.MUCUOCTB_ID = this.modelCbo.cboMucCuocTB;
                let temp = await this.MAP_MUCCUOC_TB_NEO(this.modelCbo.cboMucCuocTB)
                rowHDTB.MUCUOC_TB = temp[0].CUOC_TB
            }

            if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CQ ||
                this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CQ ||
                this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_2M || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_TUONGTU)
            {
                if (this.inputValue.loaisochinh == 0)
                    if (this.inputValue.hdtb_id_cha > 0 && this.inputValue.hdtb_id_cha != this.inputValue.hdtb_id)
                        rowHDTB.HDTB_CHA_ID = this.inputValue.hdtb_id_cha;
            }

            if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MEGAWAN
                || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.METRONET)
            {
                

                if (thuebao_id > 0)
                {
                    let temp = MAP_THUEBAO_CHA_ID_NEO(this.inputValue.thuebao_id)
                    let tb_cha_id = temp[0].THUEBAO_CHA_ID
                    if (tb_cha_id != -1)
                        rowHDTB.THUEBAO_CHA_ID = tb_cha_id;
                }
            }

            if (this.modelCbo.vcboGoiKT.length > 0)
                rowHDTB.GOIKT_ID = this.modelCbo.cboGoiKT;

            dsHDTB.push(rowHDTB);
            
            //#endregion

            // foreach (KHUYENMAI_HDTB_DATA.KHUYENMAI_HDTBRow row in dsKM_HDTB.KHUYENMAI_HDTB)
            // {
            //     row.HDTB_ID = hdtb_id;

            // }

        },
        TaoDuLieu_THONGTIN_TT: async function(themmoi){
            //Khong can do code cu rem
        },
        TaoDuLieu_CT_TIENHD: async function(themmoi){
            this.dsCTTHD = []
            let rowCTTHD = {};

            for (let i = 0; i < this.inputValue.ds_tien_khoanmuc.length; i++)
            {
                if (this.inputValue.ds_tien_khoanmuc[i]["KHOANMUCTT_ID"].toString() == Enum.KHOANMUC_TT.KMTT_DICHVU.toString())
                {
                    for (let j = 0; j < this.inputValue.m_dsThueBao_DichVu.length; j++)
                    {
                        if (this.inputValue.m_dsThueBao_DichVu[j]["DANGKY"].toString() == "1"
                            && this.inputValue.m_dsThueBao_DichVu[j]["HUY"].toString() == "0")
                        {
                            rowCTTHD.HDTB_ID = this.inputValue.hdtb_id;
                            if (themmoi == true)
                            {
                                await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'CT_TIENHD'})
                                    .then(res =>{
                                    if (res.error === "200" && res.data.length > 0)
                                        this.inputValue.CT_TIENHD_ID = res.data[0].id
                                        rowCTTHD.CT_TIENHD_ID = this.inputValue.CT_TIENHD_ID;
                                    }).catch(e =>{throw e})
                            }
                            rowCTTHD.KHOANMUCTT_ID = Enum.KHOANMUC_TT.KMTT_DICHVU;
                            let tien = 0;//Tiền dịch vụ chưa khuyến mại
                            let vat = 0;//Vat dịch vụ chưa khuyến mại
                            tien = this.inputValue.m_dsThueBao_DichVu[j]["CUOC_LD"].toString()//CommonFunction.Unformat_tien(m_dsThueBao_DichVu.Tables[0].Rows[j]["cuoc_ld"].ToString()));
                            rowCTTHD.TIEN = currency(tien);
                            vat = this.inputValue.m_dsThueBao_DichVu[j]["VAT_LD"].toString()//CommonFunction.Unformat_tien(m_dsThueBao_DichVu.Tables[0].Rows[j]["vat_ld"].ToString()));
                            rowCTTHD.VAT = vat;
                            rowCTTHD.ID = this.inputValue.m_dsThueBao_DichVu[j]["DICHVUGT_ID"].toString();
                            rowCTTHD.LOAI_ID = 2;
                            dsCTTHD.push(rowCTTHD);
                        }
                    }
                }
                else
                {
                    rowCTTHD.HDTB_ID = this.inputValue.hdtb_id;
                    if (themmoi == true)
                    {
                        await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'CT_TIENHD'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            this.inputValue.CT_TIENHD_ID = res.data[0].id
                            rowCTTHD.CT_TIENHD_ID = this.inputValue.CT_TIENHD_ID;
                        }).catch(e =>{throw e})
                    }
                    rowCTTHD.KHOANMUCTT_ID = this.inputValue.ds_tien_khoanmuc[i]["KHOANMUCTT_ID"].toString();
                    rowCTTHD.TIEN = this.inputValue.ds_tien_khoanmuc[i]["TIEN"].toString();
                    rowCTTHD.VAT = this.inputValue.ds_tien_khoanmuc[i]["VAT"].toString();
                    if (this.inputValue.ds_tien_khoanmuc[i]["KHOANMUCTT_ID"].toString() == Enum.KHOANMUC_TT.KMTT_KM_LAPDAT
                       || this.inputValue.ds_tien_khoanmuc[i]["KHOANMUCTT_ID"].toString() == Enum.KHOANMUC_TT.KMTT_KM_THIETBI
                       || this.inputValue.ds_tien_khoanmuc[i]["KHOANMUCTT_ID"].toString() == Enum.KHOANMUC_TT.KMTT_KM_DATCOC)
                    {
                        rowCTTHD.TIEN = Math.Abs(rowCTTHD.TIEN) * -1;
                        rowCTTHD.VAT = Math.Abs(rowCTTHD.VAT) * -1;
                    }
                    rowCTTHD.ID = this.inputValue.ds_tien_khoanmuc[i]["KHOANMUCTT_ID"].toString();
                    rowCTTHD.LOAI_ID = 1;
                    dsCTTHD.push(rowCTTHD);
                }
            }

        },
        TaoDuLieu_HDTT_NV: async function(themmoi){
            this.dsHDTT_NV = []
            if (this.inputValue.nhanvien_ql_am_id != 0)
            {
                let ip = await this.getIp()
                let rowhdtt_nv = {};
                rowhdtt_nv.HDTT_ID = this.inputValue.hdtt_id;
                rowhdtt_nv.NHANVIEN_ID = this.inputValue.nhanvien_ql_am_id;
                rowhdtt_nv.MAY_CN = ip//tt_nd.may_cn;
                rowhdtt_nv.NGAY_CN = DateTimeLib.toDateDisplay(new Date())//tt_nd.ngay_cn;
                rowhdtt_nv.NGUOI_CN = this.$root.token.getUserName()//tt_nd.ma_nd;
                this.dsHDTT_NV.push(rowhdtt_nv);
            }
        },
        TaoDuLieu_DiaChi: async function(themmoi, kieu){
            this.dsDiaChi = [];
            this.dsDiaChiHDKH = [];
            this.dsDiaChiHDTT = [];
            this.dsDiaChiHDTB = [];

            if (kieu == 1 || kieu == 4)
            {
                let rowDiaChiKH = {};
                if (themmoi == true)
                    await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'DIACHI'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            this.inputValue.diachikh_id = res.data[0].id
                        }).catch(e =>{throw e})
                rowDiaChiKH.DIACHI_ID = this.inputValue.diachikh_id;
                rowDiaChiKH.TINH_ID = this.inputValue.tinhkh_id;
                rowDiaChiKH.QUAN_ID = this.inputValue.quankh_id;
                rowDiaChiKH.PHUONG_ID = this.inputValue.phuongkh_id;
                if (this.inputValue.phokh_id != 0)
                    rowDiaChiKH.PHO_ID = this.inputValue.phokh_id;
                if (this.inputValue.apkh_id != 0)
                    rowDiaChiKH.AP_ID = this.inputValue.apkh_id;
                if (this.inputValue.khukh_id != 0)
                    rowDiaChiKH.KHU_ID = this.inputValue.khukh_id;
                if (this.inputValue.dacdiemkh_id != 0)
                    rowDiaChiKH.DACDIEM_ID = this.inputValue.dacdiemkh_id;
                rowDiaChiKH.BLOCK = this.StringToString(this.inputValue.blockkh);
                rowDiaChiKH.TANG = this.StringToString(this.inputValue.tangkh);
                rowDiaChiKH.PHONG = this.StringToString(this.inputValue.sophongkh);
                rowDiaChiKH.MOTA = this.StringToString(this.inputValue.motadckh);

                rowDiaChiKH.SONHA = this.StringToString(this.inputValue.sonhakh);
                rowDiaChiKH.DIACHI = this.StringToString(this.modelInput.txtDiaChiKH);
                this.dsDiaChi.push(rowDiaChiKH);


                let rowDiaChiHDKH = {}
                rowDiaChiHDKH.DIACHI_ID = this.inputValue.diachikh_id;
                rowDiaChiHDKH.HDKH_ID = this.inputValue.hdkh_id;
                rowDiaChiHDKH.KINHDO = this.inputValue.Lng_KH;
                rowDiaChiHDKH.VIDO = this.inputValue.Lat_KH;
                this.dsDiaChiHDKH.push(rowDiaChiHDKH);

            }

            if (kieu == 2 || kieu == 4 || kieu == 5)
            {
                let rowDiaChiTT = {}
                if (themmoi == true)
                    await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'DIACHI'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            this.inputValue.diachitt_id = res.data[0].id
                        }).catch(e =>{throw e})
                rowDiaChiTT.DIACHI_ID = this.inputValue.diachitt_id;
                rowDiaChiTT.TINH_ID = this.inputValue.tinhtt_id;
                rowDiaChiTT.QUAN_ID = this.inputValue.quantt_id;
                rowDiaChiTT.PHUONG_ID = this.inputValue.phuongtt_id;

                if (this.inputValue.phott_id != 0)
                    rowDiaChiTT.PHO_ID = this.inputValue.phott_id;
                if (this.inputValue.aptt_id != 0)
                    rowDiaChiTT.AP_ID = this.inputValue.aptt_id;
                if (this.inputValue.khutt_id != 0)
                    rowDiaChiTT.KHU_ID = this.inputValue.khutt_id;
                if (this.inputValue.dacdiemtt_id != 0)
                    rowDiaChiTT.DACDIEM_ID = this.inputValue.dacdiemtt_id;
                rowDiaChiTT.BLOCK = this.StringToString(this.inputValue.blocktt);
                rowDiaChiTT.TANG = this.StringToString(this.inputValue.tangtt);
                rowDiaChiTT.PHONG = this.StringToString(this.inputValue.sophongtt);
                rowDiaChiTT.MOTA = this.StringToString(this.inputValue.motadctt);

                rowDiaChiTT.SONHA = this.StringToString(this.inputValue.sonhatt);
                rowDiaChiTT.DIACHI = this.StringToString(this.modelInput.txtDiaChiTT);
                this.dsDiaChi.push(rowDiaChiTT);

                let rowDiaChiHDTT = {};
                rowDiaChiHDTT.DIACHI_ID = this.inputValue.diachitt_id;
                rowDiaChiHDTT.HDTT_ID = this.inputValue.hdtt_id;
                rowDiaChiHDTT.KINHDO = this.inputValue.Lng_TT;
                rowDiaChiHDTT.VIDO = this.inputValue.Lat_TT;
                rowDiaChiHDTT.KINHDO_BC = this.inputValue.Lng_BC;
                rowDiaChiHDTT.VIDO_BC = this.inputValue.Lat_BC;
                this.dsDiaChiHDTT.push(rowDiaChiHDTT);

            }

            if (kieu == 3 || kieu == 4 || kieu == 5)
            {
                //Hiếu Create phần địa chỉ lắp đặt & địa chỉ Tb 22/07/2010
                let rowDiaChiTB = {};
                let rowDiachiLD = {};
                if (themmoi == true)
                {
                    await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'DIACHI'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            this.inputValue.diachitb_id = res.data[0].id
                        }).catch(e =>{throw e})
                    await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'DIACHI'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            this.inputValue.diachild_id = res.data[0].id
                        }).catch(e =>{throw e})
                }

                //Địa chỉ ld
                rowDiachiLD.DIACHI_ID = this.inputValue.diachild_id;
                rowDiachiLD.TINH_ID = this.inputValue.tinhld_id;
                rowDiachiLD.QUAN_ID = this.inputValue.quanld_id;
                rowDiachiLD.PHUONG_ID = this.inputValue.phuongld_id;

                if (this.inputValue.phold_id != 0)
                    rowDiachiLD.PHO_ID = this.inputValue.phold_id;
                if (this.inputValue.apld_id != 0)
                    rowDiachiLD.AP_ID = this.inputValue.apld_id;
                if (this.inputValue.khuld_id != 0)
                    rowDiachiLD.KHU_ID = this.inputValue.khuld_id;
                if (this.inputValue.dacdiemld_id != 0)
                    rowDiachiLD.DACDIEM_ID = this.inputValue.dacdiemld_id;

                rowDiachiLD.BLOCK = this.StringToString(this.inputValue.blockld);
                rowDiachiLD.TANG = this.StringToString(this.inputValue.tangld);
                rowDiachiLD.PHONG = this.StringToString(this.inputValue.sophongld);
                rowDiachiLD.MOTA = this.StringToString(this.inputValue.motadcld);

                rowDiachiLD.SONHA = this.StringToString(this.inputValue.sonhald);
                rowDiachiLD.DIACHI = this.StringToString(this.modelInput.txtDiaChiLD);
                this.dsDiaChi.push(rowDiachiLD);

                //Địa chỉ thuê bao
                rowDiaChiTB.DIACHI_ID = this.inputValue.diachitb_id;
                rowDiaChiTB.TINH_ID = this.inputValue.tinhtb_id;
                rowDiaChiTB.QUAN_ID = this.inputValue.quantb_id;
                rowDiaChiTB.PHUONG_ID = this.inputValue.phuongtb_id;
                if (this.inputValue.photb_id != 0)
                    rowDiaChiTB.PHO_ID = this.inputValue.photb_id;
                if (this.inputValue.aptb_id != 0)
                    rowDiaChiTB.AP_ID = this.inputValue.aptb_id;
                if (this.inputValue.khutb_id != 0)
                    rowDiaChiTB.KHU_ID = this.inputValue.khutb_id;
                if (this.inputValue.dacdiemtb_id != 0)
                    rowDiaChiTB.DACDIEM_ID = this.inputValue.dacdiemtb_id;

                rowDiaChiTB.BLOCK = this.StringToString(this.inputValue.blocktb);
                rowDiaChiTB.TANG = this.StringToString(this.inputValue.tangtb);
                rowDiaChiTB.PHONG = this.StringToString(this.inputValue.sophongtb);
                rowDiaChiTB.MOTA = this.StringToString(this.inputValue.motadctb);

                rowDiaChiTB.SONHA = this.StringToString(this.inputValue.sonhatb);
                rowDiaChiTB.DIACHI = this.StringToString(this.modelInput.txtDiaChiTB);
                this.dsDiaChi.push(rowDiaChiTB);

                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
                {
                    let rowDiaChiDau = {};
                    if (themmoi == true)
                        await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'DIACHI'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            this.inputValue.diachidau_id = res.data[0].id
                        }).catch(e =>{throw e})

                    rowDiaChiDau.DIACHI_ID = this.inputValue.diachidau_id;
                    rowDiaChiDau.TINH_ID = this.inputValue.tinhdau_id;
                    rowDiaChiDau.QUAN_ID = this.inputValue.quandau_id;
                    rowDiaChiDau.PHUONG_ID = this.inputValue.phuongdau_id;
                    if (this.inputValue.phodau_id != 0)
                        rowDiaChiDau.PHO_ID = this.inputValue.phodau_id;
                    if (this.inputValue.apdau_id != 0)
                        rowDiaChiDau.AP_ID = this.inputValue.apdau_id;
                    if (this.inputValue.khudau_id != 0)
                        rowDiaChiDau.KHU_ID = this.inputValue.khudau_id;

                    rowDiaChiDau.SONHA = this.StringToString(this.inputValue.sonhadau);
                    rowDiaChiDau.DIACHI = this.StringToString(this.modelInput.txtDiaChiDau);
                    
                    this.dsDiaChi.push(rowDiaChiDau);

                    let rowDiaChiCuoi = {};
                    if (themmoi == true)
                        await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'DIACHI'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            this.inputValue.diachicuoi_id = res.data[0].id
                        }).catch(e =>{throw e})
                    rowDiaChiCuoi.DIACHI_ID = this.inputValue.diachicuoi_id;
                    rowDiaChiCuoi.TINH_ID = this.inputValue.tinhcuoi_id;
                    rowDiaChiCuoi.QUAN_ID = this.inputValue.quancuoi_id;
                    rowDiaChiCuoi.PHUONG_ID = this.inputValue.phuongcuoi_id;
                    if (this.inputValue.phocuoi_id != 0)
                        rowDiaChiCuoi.PHO_ID = this.inputValue.phocuoi_id;
                    if (this.inputValue.apcuoi_id != 0)
                        rowDiaChiCuoi.AP_ID = this.inputValue.apcuoi_id;
                    if (this.inputValue.khucuoi_id != 0)
                        rowDiaChiCuoi.KHU_ID = this.inputValue.khucuoi_id;

                    rowDiaChiCuoi.SONHA = this.StringToString(this.inputValue.sonhacuoi);
                    rowDiaChiCuoi.DIACHI = this.StringToString(this.modelInput.txtDiaChiCuoi);
                    
                    this.dsDiaChi.push(rowDiaChiCuoi);
                }

                
                let rowDiaChiHDTB = {};
                rowDiaChiHDTB.DIACHI_ID = this.inputValue.diachitb_id;
                rowDiaChiHDTB.HDTB_ID = this.inputValue.hdtb_id;
                rowDiaChiHDTB.DIACHILD_ID = this.inputValue.diachild_id;
                rowDiaChiHDTB.KINHDO = this.inputValue.Lng_TB;
                rowDiaChiHDTB.VIDO = this.inputValue.Lat_TB;
                rowDiaChiHDTB.KINHDO_LD = this.inputValue.Lng_LD;
                rowDiaChiHDTB.VIDO_LD = this.inputValue.Lat_LD;
                
                this.dsDiaChiHDTB.push(rowDiaChiHDTB);

            }

            // if (themmoi == false)
            // {
            //     //dsDiaChi.AcceptChanges();
            //     if (kieu == 1 || kieu == 2 || kieu == 3)
            //     {
            //         //dsDiaChi.DIACHI.Rows[0].SetModified();

            //         if (kieu == 3)
            //         {
            //             //dsDiaChi.DIACHI.Rows[1].SetModified();
            //             if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
            //             {
            //                 //dsDiaChi.DIACHI.Rows[2].SetModified();
            //             }
            //         }
            //     }
            //     else
            //     {
            //         // dsDiaChi.DIACHI.Rows[0].SetModified();
            //         // dsDiaChi.DIACHI.Rows[1].SetModified();
            //         // dsDiaChi.DIACHI.Rows[2].SetModified();
            //         // dsDiaChi.DIACHI.Rows[3].SetModified();
            //         if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
            //         {
            //             // dsDiaChi.DIACHI.Rows[4].SetModified();
            //             // dsDiaChi.DIACHI.Rows[5].SetModified();
            //         }
            //     }
            // }
        },
        TaoDuLieu_DKDV: async function(){
            this.dsDK_DVGT = []

            let kieu_dk_dvgt;
            foreach (dr in this.inputValue.m_dsThueBao_DichVu)
            {
                if (dr["DANGKY"].toString() == "1")
                    kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT;
                else if (dr["HUY"].toString() == "1")
                    kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_HUY_DVGT;
                else if (dr["SUA"].toString() == "1")
                    kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_SUA_DVGT;
                // else
                //     continue;

                let rowHDTDDV = {}
                rowHDTDDV.HDTB_ID = this.inputValue.hdtb_id;
                rowHDTDDV.NGAY_YC = DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC);
                rowHDTDDV.DICHVUGT_ID = dr["DICHVUGT_ID"];
                rowHDTDDV.NOIDUNG = dr["NOIDUNG"].toString();
                rowHDTDDV.KIEU_YC = kieu_dk_dvgt;
                rowHDTDDV.TTTH = 1;

                this.dsDK_DVGT.push(rowHDTDDV);
            }
        },
        CapNhat_HDTB_ID_DangKyDV: async function(hdtb_id){
            //DANGKY_DVK_DATA ds2 = dsDK_DVK;
            this.dsDK_DVK = []
            // dsDK_DVK = new DANGKY_DVK_DATA();
        },
        CapNhat_HDTB_ID_HDTB_KV: async function(){
            for (let i = 0; i < this.dsHDTB_KV.length; i++)
            {
                this.dsHDTB_KV[i]["HDTB_ID"] = this.inputValue.hdtb_id;
            }
        },
        TaoDuLieu_HDTB_CD: async function(themmoi){
              this.dsHDTB_CD = [];
            let rowHDTB_CD = {};

            rowHDTB_CD.HDTB_ID = this.inputValue.hdtb_id;

            //Hiếu TC - Bổ xung thêm mã truy nhập + mã đôi cáp
            rowHDTB_CD.MATB_TN = this.modelInput.txtMaTN_CD;
            let temp = await this.MAP_KIEU_LD_NEO(this.modelCbo.cboKieuLD)
            let kieu = temp[0].KIEU
            //Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
            //Hiện tại đối với dịch chuyển cố định kiểu =0, bổ sung kieu=1,2 sau
            if (kieu == 1 || kieu == 2 || kieu == 4)
            {
                if (this.modelInput.txtMaDoiCap_CD.trim().length == 0)
                {
                    this.$root.$toast.error("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
                    return;
                }
                rowHDTB_CD.MADOICAP = this.modelInput.txtMaDoiCap_CD.trim();
            }
            else //TH còn lại sinh mã đôi cáp
            {
                if (themmoi == true)
                {
                    await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'MADOICAP'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            rowHDTB_CD.MADOICAP = res.data[0].id
                            this.modelInput.txtMaDoiCap_CD = rowHDTB_CD.MADOICAP.toString();
                        }).catch(e =>{throw e})
                }
                else
                    rowHDTB_CD.MADOICAP = this.modelInput.txtMaDoiCap_CD.Text.trim();
            }
            if (kieu_thueso == Enum.THUESO.TRUOTSO)
            {
                rowHDTB_CD.TG_THUESO = this.modelInput.sothang_thueso;
                rowHDTB_CD.SOTRUOT = this.modelInput.noidung_thueso;
                rowHDTB_CD.NOIDUNG_TB = "";
            }
            if (kieu_thueso == Enum.THUESO.THONGBAO)
            {
                rowHDTB_CD.TG_THUESO = this.modelInput.sothang_thueso;
                rowHDTB_CD.SOTRUOT = "";
                rowHDTB_CD.NOIDUNG_TB = this.modelInput.noidung_thueso;
            }
            rowHDTB_CD.TRANGBI_ID = this.modelCbo.cboTrangBiCD;

            if (this.modelInput.KetCuoi_ID > 0)
                rowHDTB_CD.KETCUOI_ID = this.modelInput.KetCuoi_ID;

            //Thêm thông tin Ne cũ: Lamnt - 01/08/2019
            rowHDTB_CD.NE_ID = this.modelInput.ne_id;
            rowHDTB_CD.NE = this.modelInput.sone;

            this.dsHDTB_CD.push(rowHDTB_CD);
            
            //Tạo cho bảng hdtb_dv
            // for (int i = 0; i < dtControl.Rows.Count; i++)
            // {
            //     HDTB_DV_DATA.HDTB_DVRow row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow();
            //     row.HDTB_ID = hdtb_id;
            //     row.LOAIDV_ID = Convert.ToInt32(cbo[i].Tag);
            //     row.DONVI_ID = Convert.ToInt32(cbo[i].SelectedValue);
            //     row.KIEUDV_ID = 2;
            //     dsHDTBDV.HDTB_DV.AddHDTB_DVRow(row);
            // }
            //End
        },
        TaoDuLieu_HDTB_IMS: async function(themmoi){
            
            this.dsHDTB_IMS = [];
            let rowHDTB_IMS = await this.LAY_DS_DB_IMS_THEO_TBID(thuebao_id).data[0]//dsHDTB_IMS.HDTB_IMS.NewHDTB_IMSRow();

            rowHDTB_IMS.HDTB_ID = this.inputValue.hdtb_id;
            rowHDTB_IMS.MATB_TN = this.modelInput.txtMATN_IMS;
            let temp = await this.MAP_KIEU_LD_NEO(this.modelCbo.cboKieuLD)
            let kieu = temp[0].KIEU
            if (kieu == 1 || kieu == 2 || kieu == 4)
            {
                if (this.modelInput.txtMaDoiCap_IMS.trim().length == 0)
                {
                    this.$root.$toast.error("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
                    return;
                }
                rowHDTB_IMS.MADOICAP = this.modelInput.txtMaDoiCap_IMS.trim();
            }
            else //TH còn lại sinh mã đôi cáp
            {
                if (themmoi == true)
                {
                    await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'MADOICAP'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            rowHDTB_IMS.MADOICAP = res.data[0].id
                            this.modelInput.txtMaDoiCap_IMS = rowHDTB_IMS.MADOICAP.toString();
                        }).catch(e =>{throw e})
                }
                else
                    rowHDTB_IMS.MADOICAP = this.modelInput.txtMaDoiCap_IMS.trim();
            }
            rowHDTB_IMS.TRANGBI_ID = this.modelCbo.cboTrangBiIMS;
            rowHDTB_IMS.TUYENCAP = "";
            rowHDTB_IMS.LIENTU = "";
            rowHDTB_IMS.DOICAP_P = 0;
            rowHDTB_IMS.HOPCAP_P = "";
            rowHDTB_IMS.TUCAP_P = "";
            rowHDTB_IMS.CAP_GOC = "";
            rowHDTB_IMS.CAP_P = "";
            rowHDTB_IMS.DOICAP_GOC = 0;
            rowHDTB_IMS.TUCAP_GOC = "";
            rowHDTB_IMS.CULY = "0";
            if (this.modelInput.KetCuoi_ID > 0)
                rowHDTB_IMS.KETCUOI_ID = this.modelInput.KetCuoi_ID;
            
            dsHDTB_IMS.push(rowHDTB_IMS);

            

            //Tạo cho bảng hdtb_dv
            // for (int i = 0; i < dtControl.Rows.Count; i++)
            // {
            //     HDTB_DV_DATA.HDTB_DVRow row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow();
            //     row.HDTB_ID = hdtb_id;
            //     row.LOAIDV_ID = Convert.ToInt32(cbo[i].Tag);
            //     row.DONVI_ID = Convert.ToInt32(cbo[i].SelectedValue);
            //     row.KIEUDV_ID = 2;
            //     dsHDTBDV.HDTB_DV.AddHDTB_DVRow(row);
            // }
            //End
        },
        TaoDuLieu_HDTB_ADSL: async function(themmoi){
            this.dsHDTB_ADSL = [];
            let rowHDTB_ADSL = await this.LAY_DS_DB_ADSL_THEO_TBID(this.inputValue.thuebao_id).data[0]

            rowHDTB_ADSL.HDTB_ID = this.inputValue.hdtb_id;
            rowHDTB_ADSL.EMAIL = this.modelInput.txtEmailAdsl;
            rowHDTB_ADSL.SO_DT =  this.modelInput.txtMaTN_ADSL;
            if (this.modelCkeckBox.ckTuCaiADSL)
                rowHDTB_ADSL.KH_CD = 1;
            else
                rowHDTB_ADSL.KH_CD = 0;
            rowHDTB_ADSL.TOCDO_ID = this.inputValue._tocdo_id;
            rowHDTB_ADSL.MUCCUOC_ID = this.modelCbo.cboMucCuoc;
            rowHDTB_ADSL.THIETBIDC_ID = this.modelCbo.cboLoaiTBAdsl;

            if (this.inputValue.KetCuoi_ID > 0)
                rowHDTB_ADSL.KETCUOI_ID = this.inputValue.KetCuoi_ID;
            let temp = await this.MAP_PROFILEDSL_ID_NEO(this.modelCbo.cboGoiCuocAdsl)
            let profiledsl_id = temp[0].PROFILEDSL_ID
            if (profiledsl_id != -1)
            {
                rowHDTB_ADSL.PROFILEDSL_ID = profiledsl_id;
            }
            else
                rowHDTB_ADSL.PROFILEDSL_ID = null;

            //HiEUTC - Bổ sung thêm mã truy nhập + mã đôi cáp: 22.09.2010
            rowHDTB_ADSL.MATB_TN = this.modelInput.txtMaTN_ADSL;
            if (this.inputValue.thuebao_id > 0)
            {
                temp = await this.MAP_DB_ADSL_NEO(this.inputValue.thuebao_id)
                let malt = "";
                malt = temp[0].MA_LT 
                if (malt != "-1")
                    rowHDTB_ADSL.MA_LT = malt;
                let password = "";
                password = temp[0].PASSWORD
                if (password != "-1")
                    rowHDTB_ADSL.PASSWORD = password;
            }
            temp = await this.MAP_KIEU_LD_NEO(this.modelCbo.cboKieuLD)
            let kieu = temp[0].KIEU 
            //Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
            if (kieu == 1 || kieu == 2 || kieu == 4)
            {
                if (this.modelInput.txtMaDoiCap_ADSL.trim().length == 0)
                {
                    this.$root.$toast.error("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập")
                    this.inputValue.kt_adsl = false;
                    return;
                }
                rowHDTB_ADSL.MADOICAP = this.modelInput.txtMaDoiCap_ADSL.trim();
            }
            else //TH còn lại sinh mã đôi cáp
            {
                if (themmoi == true)
                {
                    await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'MADOICAP'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            rowHDTB_ADSL.MADOICAP = res.data[0].id
                            this.modelInput.txtMaDoiCap_ADSL = rowHDTB_ADSL.MADOICAP.toString();
                        }).catch(e =>{throw e})
                }
                else
                    rowHDTB_ADSL.MADOICAP = this.modelInput.txtMaDoiCap_ADSL;
            }
            rowHDTB_ADSL.TRANGBI_ID = this.modelCbo.cboTrangBiADSL;

            if (this.modelCkeckBox.chkDoiTac_ADSL)
            {
                rowHDTB_ADSL.USERNAME = this.inputValue.ma_tb;
                if (this.modelCbo.cboDoiTac_ADSL != null)
                {
                    rowHDTB_ADSL.USERNAME = this.inputValue.ma_tb.trim() + "" +this.dsDanhMuc.doitac.filter(x=>x.doitac_id == this.modelCbo.cboDoiTac_ADSL)[0].username_sub.trim()
                }
                rowHDTB_ADSL.DOITAC_ID = this.modelCbo.cboDoiTac_ADSL;
                if (this.modelInput.txtCuocDT_Adsl != '')
                    rowHDTB_ADSL.CUOC_DOITAC = this.modelInput.txtCuocDT_Adsl
                else
                    rowHDTB_ADSL.CUOC_DOITAC = 0;
            }
            else
            {
                rowHDTB_ADSL.USERNAME = this.inputValue.ma_tb.trim()
                rowHDTB_ADSL.DOITAC_ID = null
                rowHDTB_ADSL.CUOC_DOITAC = 0;
            }

            this.dsHDTB_ADSL.push(rowHDTB_ADSL);
            

            //Tạo cho bảng hdtb_dv
            // for (int i = 0; i < dtControl.Rows.Count; i++)
            // {
            //     HDTB_DV_DATA.HDTB_DVRow row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow();
            //     row.HDTB_ID = hdtb_id;
            //     row.LOAIDV_ID = Convert.ToInt32(cbo[i].Tag);
            //     row.DONVI_ID = Convert.ToInt32(cbo[i].SelectedValue);
            //     row.KIEUDV_ID = 2;
            //     dsHDTBDV.HDTB_DV.AddHDTB_DVRow(row);
            // }
            //End
        },
        TaoDuLieu_HDTB_MGWAN: async function(themmoi){
            this.dsHDTB_MEWAN = [];
            let ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID(this.inputValue.thuebao_id);
            let rowHDTB_MGWAN = ds.data[0];
            rowHDTB_MGWAN.HDTB_ID = this.inputValue.hdtb_id;
            rowHDTB_MGWAN.TOCDO_ID = this.modelCbo.cboTocDo;
            rowHDTB_MGWAN.LOAIMD_ID = this.modelCbo.cboLoaiModem;
            rowHDTB_MGWAN.LOAIKENH_ID = ds[0]["LOAIKENH_ID"].toString();
            rowHDTB_MGWAN.LOAICAP_ID = this.modelCbo.cboLoaiCap;
            if (this.modelCkeckBox.chkTocDo_PIR)
                rowHDTB_MGWAN.TOCDO_PIR_ID = this.modelCbo.cboTocDo_PIR;

            if (this.modelCkeckBox.chkCongVan)
            {
                rowHDTB_MGWAN.MA_CV = this.modelInput.txtCongVan.trim();
                rowHDTB_MGWAN.NGAY_CV = DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayCV);
            }

            if (this.inputValue.kt_kenhphu_ao == true)
                rowHDTB_MGWAN.KP_AO = 1;

            if (this.inputValue.KetCuoi_ID > 0)
                rowHDTB_MGWAN.KETCUOI_ID = this.inputValue.KetCuoi_ID;

            rowHDTB_MGWAN.THIETBIDC_ID = this.modelCbo.cboLoaiTBiWan;
            // hoangpkn : dịch chuyển megawan ko thay đổi hướng kn : 18/03/2016
            rowHDTB_MGWAN.HUONG_KN = ds[0]["HUONG_KN"].toString();
            if (ds[0]["TINH_KN_ID"].toString() != null)
                rowHDTB_MGWAN.TINH_KN_ID = ds[0]["TINH_KN_ID"].toString();

            // end hoàng
            rowHDTB_MGWAN.THOIHAN_ID = ds[0]["THOIHAN_ID"].toString();
            rowHDTB_MGWAN.TRANGBI_ID = this.modelCbo.cboTrangBiWan;
            rowHDTB_MGWAN.CHUQUAN_ID = this.modelCbo.cboChuQuanWan;
            rowHDTB_MGWAN.MA_PL = this.modelInput.txtMaHD_TTKD_MGWAN.trim();
            rowHDTB_MGWAN.SITE_ID = this.modelInput.txtSite_ID.trim();
            
            // hoangpkn : 04/06/2019 them theo yc HNI
            rowHDTB_MGWAN.VRF = ds[0]["VRF"].toString();
            rowHDTB_MGWAN.LAN_IP = ds[0]["LAN_IP"].toString();
            rowHDTB_MGWAN.WAN_IP = ds[0]["WAN_IP"].toString();
            // end hoang

            if (ds[0]["LOAINODE_ID"].toString() != null)
                rowHDTB_MGWAN.LOAINODE_ID = ds[0]["LOAINODE_ID"].toString();

            if (this.modelInput.txtCuocTC.trim() == "")
                rowHDTB_MGWAN.CUOC_TC = 0;
            else
                rowHDTB_MGWAN.CUOC_TC = this.modelInput.txtCuocTC.trim();

            if (this.modelInput.txtCuocTK.trim() == "")
                rowHDTB_MGWAN.CUOC_TK = 0;
            else
                rowHDTB_MGWAN.CUOC_TK = this.modelInput.txtCuocTK.trim();

            if (this.modelInput.txtCuocHTWan.trim() == "")
                rowHDTB_MGWAN.CUOC_HT = 0;
            else
                rowHDTB_MGWAN.CUOC_HT = this.modelInput.txtCuocHTWan.trim();

            if (this.modelInput.txtCuocTBiWan.trim() == "")
                rowHDTB_MGWAN.CUOC_TBI = 0;
            else
                rowHDTB_MGWAN.CUOC_TBI = this.modelInput.txtCuocTBiWan.trim();

            if (this.modelCkeckBox.chkDoiTacWan)
            {
                rowHDTB_MGWAN.DOITAC_ID = this.modelCbo.cboDoiTacWan;
                if (this.modelInput.txtCuocTK_DT != '')
                    rowHDTB_MGWAN.CUOC_TKDT = currency(this.modelInput.txtCuocTK_DT)//Common.Utilities.CommonFunction.Unformat_tien(this.modelInput.txtCuocTK_DT);
                else
                    rowHDTB_MGWAN.CUOC_TKDT = 0;

                if (this.modelInput.txtCuocTC_DT != '')
                    rowHDTB_MGWAN.CUOC_TCDT = currency(this.modelInput.txtCuocTC_DT)//Common.Utilities.CommonFunction.Unformat_tien(this.modelInput.txtCuocTC_DT);
                else
                    rowHDTB_MGWAN.CUOC_TCDT = 0;
                rowHDTB_MGWAN.MAHD_DT = this.modelInput.txtMaHD_DT.trim();
            }
            rowHDTB_MGWAN.MA_BSS = this.modelInput.txtMaBSS;
            rowHDTB_MGWAN.ID_ERP = this.modelInput.txtMaERP;
            rowHDTB_MGWAN.MA_LT = ds[0]["MA_LT"].toString();
            let temp = await this.MAP_KIEU_LD_NEO(this.modelCbo.cboKieuLD)
            let kieu = temp[0].KIEU 
            //Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
            if (kieu == Enum.KIEULAP.LAP_KEM || kieu == Enum.KIEULAP.LAP_LINE_SAN)
            {
                if (this.modelInput.txtMaDoiCap_Wan.trim().length == 0)
                {
                    this.$root.$toast.error("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
                    return;
                }
                rowHDTB_MGWAN.MADOICAP = this.modelInput.txtMaDoiCap_Wan.trim();
            }
            else //TH còn lại sinh mã đôi cáp
            {
                //Hiếu bổ sung chỉ sinh mã đôi cáp đối với TH thêm mới hợp đồng, phụ lục
                if (themmoi == true)
                {
                    await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'MADOICAP'})
                        .then(res =>{
                        if (res.error === "200" && res.data.length > 0)
                            rowHDTB_MGWAN.MADOICAP = res.data[0].id
                            this.modelInput.txtMaDoiCap_Wan = rowHDTB_MGWAN.MADOICAP.toString();
                        }).catch(e =>{throw e})
                }
                else
                    rowHDTB_MGWAN.MADOICAP = this.modelInput.txtMaDoiCap_Wan;
            }
            rowHDTB_MGWAN.MATB_TN = this.modelInput.txtMaTN_Wan;
            this.dsHDTB_MEWAN.push(rowHDTB_MGWAN);

            //Tạo cho bảng hdtb_dv
            // for (int i = 0; i < dtControl.Rows.Count; i++)
            // {
            //     HDTB_DV_DATA.HDTB_DVRow row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow();
            //     row.HDTB_ID = hdtb_id;
            //     row.LOAIDV_ID = Convert.ToInt32(cbo[i].Tag);
            //     row.DONVI_ID = Convert.ToInt32(cbo[i].SelectedValue);
            //     row.KIEUDV_ID = 2;
            //     dsHDTBDV.HDTB_DV.AddHDTB_DVRow(row);
            // }
            //End
        },
        TaoDuLieu_HDTB_TSL: async function(themmoi){
            this.dsHDTB_TSL = [];
            let rowHDTB_TSL = {};
            let row = {};

            this.inputValue.dc_daucuoi = 0;
            if (this.modelCkeckBox.chkDC_Dau && this.modelCkeckBox.chkDC_Cuoi)
                this.inputValue.dc_daucuoi = 3;
            else
            {
                if (this.modelCkeckBox.chkDC_Dau)
                    this.inputValue.dc_daucuoi = 1;
                if (this.modelCkeckBox.chkDC_Cuoi)
                    this.inputValue.dc_daucuoi = 2;
            }

            let ds = await this.LAY_DS_DB_TSL_THEO_TBID(this.inputValue.thuebao_id);
            for (let i = 0; i < 2; i++)
            {
                //rowHDTB_TSL = dsHDTB_TSL.HDTB_TSL.NewHDTB_TSLRow();
                rowHDTB_TSL.HDTB_ID = this.inputValue.hdtb_id;
                rowHDTB_TSL.DAUCUOI_ID = i + 1;
                rowHDTB_TSL.TOCDO_ID = this.inputValue._tocdo_id; 
                rowHDTB_TSL.TRANGBI_ID = this.modelCbo.cboTrangBi;
                rowHDTB_TSL.CHUQUAN_ID = this.modelCbo.cboChuQuan;
                rowHDTB_TSL.MA_PL = this.modelInput.txtMaHD_TTKD_TSL.trim();            
                rowHDTB_TSL.DC_DAUCUOI = this.inputValue.dc_daucuoi;

                if (this.inputValue.KetCuoi_ID > 0)
                    rowHDTB_TSL.KETCUOI_ID = this.inputValue.KetCuoi_ID;

                if (this.modelCkeckBox.chkCongVan)
                {
                    rowHDTB_TSL.MA_CV = this.modelInput.txtCongVan.trim();
                    rowHDTB_TSL.NGAY_CV = DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayCV);
                }

                if (this.modelInput.txtCuocTC_TSL.trim() == "")
                    rowHDTB_TSL.CUOC_TC = 0;
                else
                    rowHDTB_TSL.CUOC_TC = this.modelInput.txtCuocTC_TSL.trim();

                if (this.modelInput.txtCuocTK_TSL.trim() == "")
                    rowHDTB_TSL.CUOC_TK = 0;
                else
                    rowHDTB_TSL.CUOC_TK = this.modelInput.txtCuocTK_TSL.trim();

                if (this.modelInput.txtCuocHT_TSL.trim() == "")
                    rowHDTB_TSL.CUOC_HT = 0;
                else
                    rowHDTB_TSL.CUOC_HT = this.modelInput.txtCuocHT_TSL.trim();

                if (this.modelInput.txtCuocTBI_TSL.trim() == "")
                    rowHDTB_TSL.CUOC_TBI = 0;
                else
                    rowHDTB_TSL.CUOC_TBI = this.modelInput.txtCuocTBI_TSL.trim();

                if (this.modelCkeckBox.chkDoiTac_TSL)
                {
                    rowHDTB_TSL.DOITAC_ID = this.modelCbo.cboDoiTac_TSL;
                    if (this.modelInput.txtCuocTKDT_TSL != '')
                        rowHDTB_TSL.CUOC_TKDT = currency(this.modelInput.txtCuocTKDT_TSL)//Common.Utilities.CommonFunction.Unformat_tien(this.modelInput.txtCuocTKDT_TSL));
                    else
                        rowHDTB_TSL.CUOC_TKDT = 0;

                    if (this.modelInput.txtCuocTCDT_TSL != '')
                        rowHDTB_TSL.CUOC_TCDT = this.currency(modelInput.txtCuocTCDT_TSL)//Common.Utilities.CommonFunction.Unformat_tien(this.modelInput.txtCuocTCDT_TSL));
                    else
                        rowHDTB_TSL.CUOC_TCDT = 0;

                    rowHDTB_TSL.MAHD_DT = this.modelInput.txtMa_HDDT_TSL.trim();
                }
                rowHDTB_TSL.MA_BSS = this.modelInput.txtMaBSS;
                rowHDTB_TSL.ID_ERP = this.modelInput.txtMaERP;

                //row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow();
                row.HDTB_ID = this.inputValue.hdtb_id;
                row.LOAIDV_ID = Enum.LOAI_DV.TRAM_VT;
                row.KIEUDV_ID = i + 1;

                if (i == 0)//Nếu là điểm đầu
                {
                    rowHDTB_TSL.DIACHI = this.StringToString(this.modelInput.txtDiaChiDau);
                    rowHDTB_TSL.DIACHI_ID = this.inputValue.diachidau_id;
                    rowHDTB_TSL.KINHDO_LD = this.inputValue.Lng_DD;
                    rowHDTB_TSL.VIDO_LD = this.inputValue.Lat_DD;
                    rowHDTB_TSL.MA_TB_SUB = this.inputValue.ma_tb_dau;
                    rowHDTB_TSL.MA_LT = this.inputValue.malt_dau;
                    rowHDTB_TSL.THIETBIDC_ID = this.modelCbo.cboLoaiTBTSL_Dau;
                    row.DONVI_ID = this.modelCbo.cboTramVTDau;

                    if (ds.length > 0)
                    {
                        if (ds[0]["DAUCUOI_ID"].toString() == "1") // điểm đầu
                        {
                            rowHDTB_TSL.LOAIKENH_ID = ds[0]["LOAIKENH_ID"].toString();
                            if (this.inputValue.dc_daucuoi == 2)
                            {
                                rowHDTB_TSL.LOAICAP_ID = ds[0]["LOAICAP_ID"].toString();
                                rowHDTB_TSL.TEN_TBI = ds[0]["TEN_TBI"].toString();
                                rowHDTB_TSL.SERIAL = ds[0]["SERIAL"].toString();
                                rowHDTB_TSL.IP_WAN = ds[0]["IP_WAN"].toString();
                                rowHDTB_TSL.LINK = ds[0]["LINK"].toString();
                                rowHDTB_TSL.TIMESLOT = ds[0]["TIMESLOT"].toString();

                                let l_doicap_ngon = -1;
                                let l_doicap_goc = -1;
                                let l_loaicap_id = 0;
                                let l_culy = 0;
                                if (ds[0]["DOICAP_P"].toString() != "")
                                    l_doicap_ngon = ds[0]["DOICAP_P"].toString();
                                if (ds[0]["DOICAP_GOC"].toString() != "")
                                    l_doicap_goc = ds[0]["DOICAP_GOC"].toString();
                                if (ds[0]["LOAICAP_ID"].toString() != "")
                                    l_loaicap_id = ds[0]["LOAICAP_ID"].toString();
                                if (ds[0]["CULY"].toString() != "")
                                    l_culy = ds[0]["CULY"].toString();

                                rowHDTB_TSL.TUYENCAP = ds[0]["TUYENCAP"].toString();
                                rowHDTB_TSL.LIENTU = ds[0]["LIENTU"].toString();
                                rowHDTB_TSL.HOPCAP_P = ds[0]["HOPCAP_P"].toString();
                                rowHDTB_TSL.TUCAP_P = ds[0]["TUCAP_P"].toString();
                                rowHDTB_TSL.CAP_GOC = ds[0]["CAP_GOC"].toString();
                                rowHDTB_TSL.CAP_P = ds[0]["CAP_P"].toString();
                                rowHDTB_TSL.TUCAP_GOC = ds[0]["TUCAP_GOC"].toString();
                                rowHDTB_TSL.DOICAP_P = l_doicap_ngon;
                                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id;
                                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc;
                                rowHDTB_TSL.CULY = l_culy;

                                if (ds[0]["TRAMTB_ID"].toString() != null)
                                    rowHDTB_TSL.TRAMTB_ID = ds[0]["TRAMTB_ID"].toString();

                                if (ds[0]["BRAS_ID"].toString() != null)
                                    rowHDTB_TSL.BRAS_ID = ds[0]["BRAS_ID"].toString();

                                if (ds[0]["DSLAM_ID"].toString() != null)
                                    rowHDTB_TSL.DSLAM_ID = ds[0]["DSLAM_ID"].toString();

                                if (ds[0]["PORT_ID"].toString() != null)
                                    rowHDTB_TSL.PORT_ID = ds[0]["PORT_ID"].toString();

                                if (ds[0]["VCI_VPI_ID"].toString() != null)
                                    rowHDTB_TSL.VCI_VPI_ID = ds[0]["VCI_VPI_ID"].toString();
                            }
                            else
                                rowHDTB_TSL.LOAICAP_ID = this.modelCbo.cboLoaiCap_Dau;
                        }
                        else
                        {
                            rowHDTB_TSL.LOAIKENH_ID = ds[1]["LOAIKENH_ID"].toString();
                            if (this.inputValue.dc_daucuoi == 2)
                            {
                                rowHDTB_TSL.LOAICAP_ID = ds[1]["LOAICAP_ID"].toString();
                                rowHDTB_TSL.TEN_TBI = ds[1]["TEN_TBI"].toString();
                                rowHDTB_TSL.SERIAL = ds[1]["SERIAL"].toString();
                                rowHDTB_TSL.IP_WAN = ds[1]["IP_WAN"].toString();
                                rowHDTB_TSL.LINK = ds[1]["LINK"].toString();
                                rowHDTB_TSL.TIMESLOT = ds[1]["TIMESLOT"].toString();

                                let l_doicap_ngon = -1;
                                let l_doicap_goc = -1;
                                let l_loaicap_id = 0;
                                let l_culy = 0;
                                if (ds[1]["DOICAP_P"].toString() != null)
                                    l_doicap_ngon = ds[1]["DOICAP_P"].toString();
                                if (ds[1]["DOICAP_GOC"].toString() != null)
                                    l_doicap_goc = ds[1]["DOICAP_GOC"].toString();
                                if (ds[1]["LOAICAP_ID"].toString() != null)
                                    l_loaicap_id = ds[1]["LOAICAP_ID"].toString();
                                if (ds[1]["CULY"].toString() != null)
                                    l_culy = ds[1]["CULY"].toString();

                                rowHDTB_TSL.TUYENCAP = ds[1]["TUYENCAP"].toString();
                                rowHDTB_TSL.LIENTU = ds[1]["LIENTU"].toString();
                                rowHDTB_TSL.HOPCAP_P = ds[1]["HOPCAP_P"].toString();
                                rowHDTB_TSL.TUCAP_P = ds[1]["TUCAP_P"].toString();
                                rowHDTB_TSL.CAP_GOC = ds[1]["CAP_GOC"].toString();
                                rowHDTB_TSL.CAP_P = ds[1]["CAP_P"].toString();
                                rowHDTB_TSL.TUCAP_GOC = ds[1]["TUCAP_GOC"].toString();
                                rowHDTB_TSL.DOICAP_P = l_doicap_ngon;
                                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id;
                                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc;
                                rowHDTB_TSL.CULY = l_culy;

                                if (ds[1]["TRAMTB_ID"].toString() != null)
                                    rowHDTB_TSL.TRAMTB_ID = ds[1]["TRAMTB_ID"].toString();

                                if (ds[1]["BRAS_ID"].toString() != null)
                                    rowHDTB_TSL.BRAS_ID = ds[1]["BRAS_ID"].toString();

                                if (ds[1]["DSLAM_ID"].toString() != null)
                                    rowHDTB_TSL.DSLAM_ID = ds[1]["DSLAM_ID"].toString();

                                if (ds[1]["PORT_ID"].toString() != null)
                                    rowHDTB_TSL.PORT_ID = ds[1]["PORT_ID"].toString();

                                if (ds[1]["VCI_VPI_ID"].toString() != null)
                                    rowHDTB_TSL.VCI_VPI_ID = ds[1]["VCI_VPI_ID"].toString();
                            }
                            else
                                rowHDTB_TSL.LOAICAP_ID = cboLoaiCap_Dau;
                        }
                    }

                }
                else//Điểm cuối
                {
                    rowHDTB_TSL.DIACHI = this.StringToString(this.modelInput.txtDiaChiCuoi);
                    rowHDTB_TSL.DIACHI_ID = diachicuoi_id;
                    rowHDTB_TSL.KINHDO_LD = Lng_DC;
                    rowHDTB_TSL.VIDO_LD = Lat_DC;
                    rowHDTB_TSL.MA_TB_SUB = ma_tb_cuoi;
                    rowHDTB_TSL.MA_LT = malt_cuoi;
                    rowHDTB_TSL.THIETBIDC_ID = this.modelCbo.cboLoaiTBTSL_Cuoi;
                    row.DONVI_ID = this.modelCbo.cboTramVTCuoi;

                    if (ds.Count > 0)
                    {
                        if (ds[0]["DAUCUOI_ID"].toString() == "2") // điểm cuối
                        {
                            rowHDTB_TSL.LOAIKENH_ID = ds[0]["LOAIKENH_ID"].toString();
                            if (this.inputValue.dc_daucuoi == 1)
                            {
                                rowHDTB_TSL.LOAICAP_ID = ds[0]["LOAICAP_ID"].toString();
                                rowHDTB_TSL.TEN_TBI = ds[0]["TEN_TBI"].toString();
                                rowHDTB_TSL.SERIAL = ds[0]["SERIAL"].toString();
                                rowHDTB_TSL.IP_WAN = ds[0]["IP_WAN"].toString();
                                rowHDTB_TSL.LINK = ds[0]["LINK"].toString();
                                rowHDTB_TSL.TIMESLOT = ds[0]["TIMESLOT"].toString();

                                let l_doicap_ngon = -1;
                                let l_doicap_goc = -1;
                                let l_loaicap_id = 0;
                                let l_culy = 0;
                                if (ds[0]["DOICAP_P"].toString() != null)
                                    l_doicap_ngon = ds[0]["DOICAP_P"].toString();
                                if (ds[0]["DOICAP_GOC"].toString() != null)
                                    l_doicap_goc = ds[0]["DOICAP_GOC"].toString();
                                if (ds[0]["loaicap_id"].toString() != null)
                                    l_loaicap_id = ds[0]["LOAICAP_ID"].toString();
                                if (ds[0]["CULY"].toString() != null)
                                    l_culy = ds[0]["CULY"].toString();

                                rowHDTB_TSL.TUYENCAP = ds[0]["TUYENCAP"].toString();
                                rowHDTB_TSL.LIENTU = ds[0]["LIENTU"].toString();
                                rowHDTB_TSL.HOPCAP_P = ds[0]["HOPCAP_P"].toString();
                                rowHDTB_TSL.TUCAP_P = ds[0]["TUCAP_P"].toString();
                                rowHDTB_TSL.CAP_GOC = ds[0]["CAP_GOC"].toString();
                                rowHDTB_TSL.CAP_P = ds[0]["CAP_P"].toString();
                                rowHDTB_TSL.TUCAP_GOC = ds[0]["TUCAP_GOC"].toString();
                                rowHDTB_TSL.DOICAP_P = l_doicap_ngon;
                                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id;
                                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc;
                                rowHDTB_TSL.CULY = l_culy;

                                if (ds[0]["TRAMTB_ID"].toString() != null)
                                    rowHDTB_TSL.TRAMTB_ID = ds[0]["TRAMTB_ID"].toString();

                                if (ds[0]["BRAS_ID"].toString() != null)
                                    rowHDTB_TSL.BRAS_ID = ds[0]["BRAS_ID"].toString();

                                if (ds[0]["DSLAM_ID"].toString() != null)
                                    rowHDTB_TSL.DSLAM_ID = ds[0]["DSLAM_ID"].toString();

                                if (ds[0]["PORT_ID"].toString() != null)
                                    rowHDTB_TSL.PORT_ID = ds[0]["PORT_ID"].toString();

                                if (ds[0]["VCI_VPI_ID"].toString() != null)
                                    rowHDTB_TSL.VCI_VPI_ID = ds[0]["VCI_VPI_ID"].toString();
                            }
                            else
                                rowHDTB_TSL.LOAICAP_ID = cboLoaiCap_Cuoi;
                        }
                        else
                        {
                            rowHDTB_TSL.LOAIKENH_ID = ds[1]["LOAIKENH_ID"].toString();
                            if (this.inputValue.dc_daucuoi == 1)
                            {
                                rowHDTB_TSL.LOAICAP_ID = ds[1]["LOAICAP_ID"].toString();
                                rowHDTB_TSL.TEN_TBI = ds[1]["TEN_TBI"].toString();
                                rowHDTB_TSL.SERIAL = ds[1]["SERIAL"].toString();
                                rowHDTB_TSL.IP_WAN = ds[1]["IP_WAN"].toString();
                                rowHDTB_TSL.LINK = ds[1]["LINK"].toString();
                                rowHDTB_TSL.TIMESLOT = ds[1]["TIMESLOT"].toString();

                                let l_doicap_ngon = -1;
                                let l_doicap_goc = -1;
                                let l_loaicap_id = 0;
                                let l_culy = 0;
                                if (ds[1]["DOICAP_P"].toString() != null)
                                    l_doicap_ngon = ds[1]["DOICAP_P"].toString();
                                if (ds[1]["DOICAP_GOC"].toString() != null)
                                    l_doicap_goc = ds[1]["DOICAP_GOC"].toString();
                                if (ds[1]["LOAICAP_ID"].toString() != null)
                                    l_loaicap_id = ds[1]["LOAICAP_ID"].toString();
                                if (ds[1]["CULY"].toString() != null)
                                    l_culy = ds[1]["CULY"].toString()

                                rowHDTB_TSL.TUYENCAP = ds[1]["TUYENCAP"].toString();
                                rowHDTB_TSL.LIENTU = ds[1]["LIENTU"].toString();
                                rowHDTB_TSL.HOPCAP_P = ds[1]["HOPCAP_P"].toString();
                                rowHDTB_TSL.TUCAP_P = ds[1]["TUCAP_P"].toString();
                                rowHDTB_TSL.CAP_GOC = ds[1]["CAP_GOC"].toString();
                                rowHDTB_TSL.CAP_P = ds[1]["CAP_P"].toString();
                                rowHDTB_TSL.TUCAP_GOC = ds[1]["TUCAP_GOC"].toString();
                                rowHDTB_TSL.DOICAP_P = l_doicap_ngon;
                                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id;
                                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc;
                                rowHDTB_TSL.CULY = l_culy;

                                if (ds[1]["TRAMTB_ID"].toString() != null)
                                    rowHDTB_TSL.TRAMTB_ID = ds[1]["TRAMTB_ID"].toString();

                                if (ds[1]["BRAS_ID"].toString() != null)
                                    rowHDTB_TSL.BRAS_ID = ds[1]["BRAS_ID"].toString();

                                if (ds[1]["DSLAM_ID"].toString() != null)
                                    rowHDTB_TSL.DSLAM_ID = ds[1]["DSLAM_ID"].toString();

                                if (ds[1]["PORT_ID"].toString() != null)
                                    rowHDTB_TSL.PORT_ID = ds[1]["PORT_ID"].toString();

                                if (ds[1]["VCI_VPI_ID"].toString() != null)
                                    rowHDTB_TSL.VCI_VPI_ID = ds[1]["VCI_VPI_ID"].toString();
                            }
                            else
                                rowHDTB_TSL.LOAICAP_ID = this.modelCbo.cboLoaiCap_Cuoi;
                        }
                    }
                }
                rowHDTB_TSL.DAUCUOI_ID = i + 1;
                this.dsHDTB_TSL.push(rowHDTB_TSL);

                this.dsHDTBDV.push(row);
            }

            //Tạo cho bảng hdtb_dv
            //Với Truyền số liệu thì tự thêm các dòng # trạm viễn thông
            // for (int i = 0; i < dtControl.Rows.Count; i++)
            // {
            //     if (Convert.ToInt32(cbo[i].Tag) != LOAI_DV.TRAM_VT)
            //     {
            //         row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow();
            //         row.HDTB_ID = hdtb_id;
            //         row.LOAIDV_ID = Convert.ToInt32(cbo[i].Tag);
            //         row.DONVI_ID = Convert.ToInt32(cbo[i].SelectedValue);
            //         row.KIEUDV_ID = 2;
            //         dsHDTBDV.HDTB_DV.AddHDTB_DVRow(row);
            //     }
            // }
        },
        TaoDuLieu_HDTB_GP: async function(themmoi){
            this.dsHDTB_GP = []
            let rowHDTB_GP = {};

            rowHDTB_GP.HDTB_ID = this.inputValue.hdtb_id;
            rowHDTB_GP.SIMCARD = this.modelInput.txtSoSimGphone;
            rowHDTB_GP.TRANGBI_ID = this.modelCbo.cboTrangBiGP;
            this.dsHDTB_GP.push(rowHDTB_GP);

            //Tạo cho bảng hdtb_dv
            // for (int i = 0; i < dtControl.Rows.Count; i++)
            // {
            //     HDTB_DV_DATA.HDTB_DVRow row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow();
            //     row.HDTB_ID = hdtb_id;
            //     row.LOAIDV_ID = Convert.ToInt32(cbo[i].Tag);
            //     row.DONVI_ID = Convert.ToInt32(cbo[i].SelectedValue);
            //     row.KIEUDV_ID = 2;
            //     dsHDTBDV.HDTB_DV.AddHDTB_DVRow(row);
            // }
            //End
        },
        CAPNHAT_DIACHI_BC_HDTT: async function(vdiachibc_id, vhdtt_id, 
                                    vpho_id, vphuong_id, vquan_id, vsonha, vdiachi,
                                    vtinh_id, vap_id, vkhu_id, vdacdiem_id){
            let data = {
                diachibc_id: vdiachibc_id,
                hdtt_id: vhdtt_id,
                pho_id: vpho_id,
                phuong_id: vphuong_id,
                quan_id: vquan_id,
                sonha: vsonha,
                diachi: vdiachi,
                tinh_id: vtinh_id,
                ap_id: vap_id,
                khu_id: vkhu_id,
                dacdiem_id: vdacdiem_id
            }
            let res = await this.$root.context.post("/web-hopdong/chuyendich/capnhat_diachi_bc",data)
            return res.data
        },
        CapNhat_DiaChiBC: async function(){
            try
            {
                if (this.modelInput.txtDiaChiBC != "")
                {
                    await this.CAPNHAT_DIACHI_BC_HDTT(this.inputValue.diachibc_id, this.inputValue.hdtt_id, this.inputValue.phobc_id, 
                                                        this.inputValue.phuongbc_id, this.inputValue.quanbc_id, this.inputValue.sonhabc, 
                                                        this.modelInput.txtDiaChiBC.trim(), this.inputValue.tinhbc_id, this.inputValue.apbc_id, this.inputValue.khubc_id, this.inputValue.dacdiembc_id);
                }
                else
                {
                    await this.CAPNHAT_DIACHI_BC_HDTT(this.inputValue.diachibc_id, this.inputValue.hdtt_id, this.inputValue.phobc_id, 
                                                        this.inputValue.phuongbc_id, this.inputValue.quanbc_id, this.inputValue.sonhabc, 
                                                        "0", this.inputValue.tinhbc_id, this.inputValue.apbc_id, this.inputValue.khubc_id, this.inputValue.dacdiembc_id);
                }
            }
            catch (ex)
            {
                this.$root.$toast.error("Có lỗi: " +ex.response.data.message_detail)
            }
        },
        CAPNHAT_KHOSO: async function(ma_tb_moi, ma_tb_cu, dichvuvt_id){
            let data = {
                ma_tb_moi: ma_tb_moi,
                ma_tb_cu: ma_tb_cu,
                dichvuvt_id: dichvuvt_id  
            }
            let res = await this.$root.context.post("/web-hopdong/chuyendich/capnhat_khoso",data)
            return res.data
        },
        tudong_ganthem_hdtb_dv: async function(hdtb_id){
             let rs = await this.$root.context.get("/web-hopdong/chuyendich/tudong_ganthem_hdtb_dv?hdtb_id=",hdtb_id)
             return rs.data
        },
        KIEMTRA_HDTB_KHI_THEMMOI: async function(ma_tb, hdkh_id, dichvuvt_id, loaitb_id){
            let data = {
                maTb: ma_tb, hdkhId: hdkh_id, dichVuVtId: dichvuvt_id, loaiTbId: loaitb_id
            }
            await this.$root.context.post("/web-hopdong/chuyendich/kiemtra_hdtb_khi_themmoi",data)
        },
        KIEMTRA_TB_CUNG_MAKH_TB_PL: async function(ma_gd, thuebao_id){
            let data = {
                maTb: ma_gd, thuebaoId: thuebao_id
            }
            await this.$root.context.post("/web-hopdong/chuyendich/kiemtra_tb_cung_makh_tb_pl",data)
        },
        //Ham 24 tham so
        ThemMoi_HopDong_Sub: async function(dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDKH,
                        dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTB_IMS, dsKM_HDTB,
                        huonggiao_tn, dsHDTB_KV, dsHDTT_NV, dsHDKH_Sub, dsHDTB_Sub){},
        //ham 17 tham so
        ThemMoi_HopDongTB: async function(dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDTB, 
            dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTT, dsHDTB_IMS, huonggiao_id, dsHDTB_KV ){},
        //Ham 18 tham so
        ThemMoi_HopDongTB(dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDTB, dsDK_DVGT,
                   dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTT, dsHDTB_IMS, huonggiaotn_id2,
                   dsHDTB_KV, dsDiaChiHDTT){},
        //Ham 19 tham so
        ThemMoi_HopDongTB(dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDTB, dsDK_DVGT,
                       dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTT, dsHDTB_IMS, huonggiao_tn,
                       dsHDTB_KV, dsDiaChiHDTT, dsKM_HDTB){},
        //Ham 3 tham so
        ThemMoi_HopDongTB: async function(dsHDTB, dsGOI, huonggiao_id){
        },
        //Ham 2 tham so
        ThemMoi_HopDongTB: async function(dsHDTB, huonggiao_id){
        },
        //Ham 21 tham so
        CapNhat_HopDong: async function(dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi,
                        dsDiaChiHDKH, dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP,
                        dsTTTT, dsCTTHD, dsHDTB_IMS, dsKM_HDTB, dsHDTB_KV, dsHDTT_NV){},
        TUDONG_GAN_CAP_THUEBAO: async function(hdtb_id){
            let ip = await this.getIp()
            let data = {
                hdtb_id: hdtb_id, 
                nguoi_cn: this.$root.token.getUserName(), 
                may_cn: ip, 
                ip_cn: ip
            }
            let res = await this.$root.context.post("/web-hopdong/chuyendich/tudong_gan_cap_thuebao",data)
            return res.data
        },
        TUDONG_GAN_HODAN_THUEBAO:  async function(hdtb_id){
            
            let ip = await this.getIp()
            let data = {
                hdtb_id: hdtb_id, 
                nguoi_cn: this.$root.token.getUserName(), 
                may_cn: ip, 
                ip_cn: ip
            }
            let res = await this.$root.context.post("/web-hopdong/chuyendich/tudong_gan_hodan_thuebao",data)
            return res.data
        },
        CAPNHAT_TRANGTHAI_HD:  async function(hdtb_id, tthd_id){
            let data = {
                vhdtb_id: hdtb_id, 
                vtthd_id: tthd_id
            }
            await this.$root.context.post("/web-hopdong/chuyendich/capnhat_trangthai_hd",data)
        },
        Kiemtra_dky_family_dichuyen: async function(vhdtb_id, vmatb_tn){
            let data = {
                vhdtb_id: vhdtb_id, 
                vmatb_tn: vmatb_tn
            }
            let res = await this.$root.context.post("/web-hopdong/chuyendich/kiemtra_dky_family_dichuyen",data)
            return res.data
        },
        confirmChuyenDich: async function(goi_id, nhomtb_id){
            if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV)
            {
                await this.TaoDuLieu_HDTB_GOI_DADV(goi_id, nhomtb_id);
                // Ham ThemMoi_HopDongTB 2 tham so
                await this.ThemMoi_HopDongTB(this.dsHDTBGOI, 0);
                this.inputValue.themMyTVvaogoi = true;
            }
            if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH)
            {
                await this.TaoDuLieu_HDTB_dkygoi_mytv(ds);

                // Ham ThemMoi_HopDongTB 3 tham so
                await this.ThemMoi_HopDongTB(this.dsHDTB, this.dsHDTBGOI, this.inputValue.huonggiaotn_id);
                this.inputValue.themMyTVvaogoi = true;
            }
        },
        Them_MyTV_vao_goi_giadinh:  async function(){
            try
            {
                if ((this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH || this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV) && this.modelInput.txtMaTN_ADSL != "")
                {
                    let ds = await this.Kiemtra_dky_family_dichuyen(this.inputValue.hdtb_id, this.modelInput.txtMaTN_ADSL.trim());
                    if (ds != null && ds.data.length > 0)
                    {
                        if (ds.data[0][0].toString() == "1")
                        {
                            let goi_id = ds.data[0][1].toString();
                            let nhomtb_id =  ds.data[0][2].toString();
                            let magoi_vasc = await this.Lay_goivasc_goi_dadv(goi_id, this.modelCbo.cboGoiCuocAdsl, this.modelCbo.cboMucCuoc);

                            if ((magoi_vasc != -1 && this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV) || this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH)
                            {
                                this.$bvModal.msgBoxConfirm(
                                    "Thuê bao đang tham gia gói Gia đình (hoặc được dịch chuyển tới cùng đường với thuê bao đang tham gia gói Gia đình). Bạn có đồng ý thêm thuê bao này vào cùng gói không?",
                                    {
                                        title: "Xác nhận hành động",
                                        centered: true,
                                        size: "sm",
                                        okTitle: 'Đồng ý',
                                        cancelTitle: 'Hủy',
                                    }
                                    )
                                    .then(async (value) => {
                                    if (value) {
                                        await this.confirmChuyenDich(goi_id, nhomtb_id)
                                    } else {
                                        return;
                                    }
                                    });
                            }
                               
                        }
                    }
                }
            }
            catch (error)
            {
                this.$root.$toast.error("" +error.response.data.message_detail)
            }
        },
        Lay_goivasc_goi_dadv: async function(goi_id, tocdo_id, muccuoc_id){
             let data = {
                goi_id:goi_id,
                tocdo_id: tocdo_id,
                muccuoc_id: muccuoc_id
            }
            let rs = await this.$root.context.post("/web-hopdong/chuyendich/lay_goivasc_goi_dadv",data)
            return rs.data
        },
        Update_huonggiao_tiepnhan: async function(hdtb_id, huonggiao_tn){
            let data = {
                hdtb_id: hdtb_id, 
                huonggiao_tn: huonggiao_tn
            }
            await this.$root.context.post("/web-hopdong/chuyendich/update_huonggiao_tiepnhan",data)
        },
        /// <summary>
        /// hiếu TC thêm hàm trong trường hơp đã có HĐ rồi --- Ngày 27/09/2010
        /// kiểm tra thuê bao vừa gõ mã có thuộc HĐ/PL không
        /// ---> nếu thuộc : cho phép nhấn nút Ghi lại
        /// ---> Ngược lại : phải thêm thuê bao
        /// </summary>
        /// <param name="ma_tb"></param>
        /// <returns>true: cho phép cập nhật, false: ko cho phép cập nhật -> thêm tb</returns>
        KiemTraTBTrongHD: function(ma_tb, lvwThueBao, tt)
        {
            let result = false;
            // for (let i = 0; i < lvwThueBao.length; i++)
            // {
            //     if (string.IsNullOrEmpty(lvwThueBao.Items[i].SubItems[tt].Text.ToString().Trim()) || lvwThueBao.Items[i].SubItems[tt].Text.ToString().ToLower().Trim() == ma_tb.ToLower().Trim())
            //     {
            //         result = true;
            //         break;
            //     }
            //     else
            //         result = false;
            // }
            return result;
        },
        TaoDuLieu_HDTB_GOI_DADV: async function(goi_id, nhomtb_id)
        {
            this.dsHDTBGOI = []
            let rowGOI = {}
            rowGOI.GOI_ID = goi_id;
            rowGOI.NHOMTB_ID = nhomtb_id;
            rowGOI.CHUNHOM = 0;
            rowGOI.HDTB_ID = this.inputValue.hdtb_id;
            rowGOI.THANG_HUONG = 0;
            this.dsHDTBGOI.push(rowGOI);
        },
        LAY_DS_DBTB_THEO_TBID: async function(thuebao_id){
            let rs = await this.$root.context.get("/web-hopdong/chuyendich/lay_ds_dbtb_theo_tbid?thuebao_id=",thuebao_id)
            return rs.data
        },
        TaoDuLieu_HDTB_dkygoi_mytv: async function(dsMyTV){
            this.dsHDTB = [];
            this.dsHDTBGOI = [];

            for (let i = 0; i < dsMyTV.data.length; i++)
            {
                let row = dsMyTV.data[i];
                await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/getkey",{keyname:'HD_THUEBAO'})
                                    .then(res =>{
                                    if (res.error === "200" && res.data.length > 0)
                                        this.inputValue.hdtb_id_huygoi_mytv = res.data[0].id
                                    }).catch(e =>{throw e})
                let rowHDTB = await this.LAY_DS_DBTB_THEO_TBID(row["THUEBAO_ID"])[0];

                rowHDTB.HDTB_ID = this.inputValue.hdtb_id_huygoi_mytv;
                rowHDTB.HDKH_ID = this.inputValue.hdkh_id;
                rowHDTB.THUEBAO_ID = row["THUEBAO_ID"].toString();
                rowHDTB.MA_TB = row["MA_TB"].toString();//txtMaTB.Text;
                rowHDTB.TEN_TB = row["TEN_TB"].toString();//txtTenTB.Text;
                rowHDTB.DIACHI_TB = row["DIACHI_TB"].toString();//txtDiaChiLD.Text;

                rowHDTB.KIEULD_ID = Enum.KieuLapDat.THEM_TB_GOI_DADV;

                rowHDTB.DOITUONG_ID = row["DOITUONG_ID"].toString() ;
                rowHDTB.TTHD_ID = Enum.TrangThaiHD.MOI;
                rowHDTB.LOAITB_ID = Enum.LoaiHinhTB.INTERNET_MYTV;
                rowHDTB.DICHVUVT_ID = Enum.DichVuVienThong.ADSL;
                rowHDTB.DONVI_ID = this.$root.token.getDonViID();
                rowHDTB.NGUOI_CN = this.$root.token.getUserName();
                rowHDTB.NGAY_CN = DateTimeLib.toDateDisplay(new Date());
                rowHDTB.MAY_CN = this.$root.token.getUserName();

                

                // vinhpv lay huonggiao_id
                let dset = await this.LayHuongGiao_TiepNhan_2(Enum.LoaiHopDong.THAYDOI_GOI_DADV, Enum.KieuLapDat.XOA_TB_GOI_DADV);

                if (dset.length > 0)
                {
                    this.inputValue.huonggiaotn_id = dset[0]["HUONGGIAO_ID"];
                    this.inputValue.quytrinh_id_2 = dset[0]["QUYTRINH_ID"];
                }
                rowHDTB.QUYTRINH_ID = this.inputValue.quytrinh_id_2;

                let mucuoctb_id = await this.LayMucCuocTB(row["THUEBAO_ID"], 1);
                let mucuoctb = await this.LayMucCuocTB(row["THUEBAO_ID"], 2);
                if (mucuoctb_id != "-1")
                    rowHDTB.MUCUOCTB_ID = mucuoctb_id;
                if (mucuoctb != "-1")
                    rowHDTB.MUCUOC_TB = mucuoctb;


                this.dsHDTB.push(rowHDTB);

                let rowGOI = {}
                rowGOI.GOI_ID = row["GOI_ID"];
                rowGOI.NHOMTB_ID = row["NHOMTB_ID"];
                rowGOI.CHUNHOM = 0;
                rowGOI.HDTB_ID = this.inputValue.hdtb_id_huygoi_mytv;
                rowGOI.THANG_HUONG = 0;
                this.dsHDTBGOI.push(rowGOI);
            }
        },
        MAP_DB_THUEBAO_MUCCUOC_NEO: async function(thuebao_id){
             let rs = await this.$root.context.get("/web-hopdong/chuyendich/map_muccuoc_neo?thuebao_id=",thuebao_id)
             return rs.data;
        },
        SP_map_loaitb_id_db_thuebao_neo: async function(thuebao_id){
             let rs = await this.$root.context.get("/web-hopdong/chuyendich/map_muccuoc_neo?thuebao_id=",thuebao_id)
             return rs.data;
        },
        LayMucCuocTB: async function(thuebao_id, kieu){
            let res = await this.MAP_DB_THUEBAO_MUCCUOC_NEO(thuebao_id)
            if (kieu == 1)
            {
                return res[0].mucuoctb_id;
            }
            else
            {
                return res[0].mucuoc_tb;
            }
        },
        //#endregion
        TaoDuLieu: async function(themmoi)
        {
            await this.TaoDuLieu_HDKH(themmoi);
            await this.TaoDuLieu_HDTT(themmoi);
            await this.TaoDuLieu_HDTB(themmoi);
            //Hiếu bổ sung hàm tạo dữ liệu thông tin thanh toán 17/09/2010
            await this.TaoDuLieu_THONGTIN_TT(themmoi);
            await this.TaoDuLieu_CT_TIENHD(true);
            await this.TaoDuLieu_HDTT_NV(true);
            //
            await this.TaoDuLieu_DiaChi(themmoi, 4);
            await this.TaoDuLieu_DKDV();
            await this.CapNhat_HDTB_ID_DangKyDV(hdtb_id);

            await this.CapNhat_HDTB_ID_HDTB_KV();

            
            switch (this.inputValue.dichvuvt_id)
            {
                case Enum.DichVuVienThong.CO_DINH:
                    await this.TaoDuLieu_HDTB_CD(themmoi);
                    break;
                case Enum.DichVuVienThong.IMS:
                case Enum.DichVuVienThong.IMS_DOITAC:
                    await this.TaoDuLieu_HDTB_IMS(themmoi);
                    break;
                case Enum.DichVuVienThong.ADSL:
                case Enum.DichVuVienThong.BRCD_DOITAC:
                    await this.TaoDuLieu_HDTB_ADSL(themmoi);
                    break;
                case Enum.DichVuVienThong.MEGA_EYES:
                    await this.TaoDuLieu_HDTB_ADSL(themmoi);
                    break;
                case Enum.DichVuVienThong.MEGAWAN:
                case Enum.DichVuVienThong.METRONET:
                case Enum.DichVuVienThong.MGW_DOITAC:
                    await this.TaoDuLieu_HDTB_MGWAN(themmoi);
                    break;
                case Enum.DichVuVienThong.TSL:
                case Enum.DichVuVienThong.TSL_DOITAC:
                    await this.TaoDuLieu_HDTB_TSL(themmoi);
                    break;
                case Enum.DichVuVienThong.GPHONE:
                    await this.TaoDuLieu_HDTB_GP(themmoi);
                    break;
                default:
                    await this.TaoDuLieu_HDTB_CD(themmoi);
                    break;
            }
        },
        CapNhat: async function(){
            try
            {
                // labelFunctionTitle.Focus();
                if (!this.KiemTraDL_KhachHang()) return;
                if (!this.KiemTraDL_ThanhToan()) return;
                if (!await this.KiemTraDL_ThueBao()) return;
                //20/03/2020: Doantv kiểm tra thuê bao có hợp đồng chuyển quyền
                var d = this.lay_ds_thuebao_co_cquyen()
                if (d.length > 0)
                {
                    if (!await this.KemTraDL_HD_CQ(this.inputValue.thuebao_id)) return;
                }
                //Hiếu 17/09/2010: kiểm tra dữ liệu nhập vào từ các ô tiền
                this.KiemTraDuLieuNhap(); //return true
                if (this.modelCbo.cboKieuLD != Enum.KieuLapDat.DC_TINH_KHAC)
                {
                    if (await this.KIEMTRA_MATB_KHOSO(this.modelInput.txtMaTB, this.inputValue.dichvuvt_id) == 0)
                    {
                         this.$root.$toast.error("Số máy chưa có trong kho số !")
                        //txtMaTB.Focus();
                        return;
                    }
                    if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.IMS || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.GPHONE)
                    {
                        if (this.modelInput.txtMaTBMoi.trim() != "")
                        {
                            if (await this.KIEMTRA_MATB_KHOSO(this.modelInput.txtMaTBMoi, this.inputValue.dichvuvt_id) == 0)
                            {
                                this.$root.$toast.error("Số máy chưa có trong kho số !")
                                // txtMaTB.Focus();
                                return;
                            }
                        }
                    }
                }

                //04-02-2020 AnhDt: Kiểm tra bắt buộc nhập Sđt
                if (this.inputValue.batbuoc_sdt)
                {
                    if (this.modelInput.txtTelKH == "")
                    {
                        this.$root.$toast.error("Số điện thoại khách hàng không được để trống !")
                        return;
                    }
                }
                //End AnhDt

                //nhapt 09/03/2017
                //Thực hiện kiểm tra khu vực đã được gán hay chưa 
                //nhiều trường hợp gán thiếu
                if (this.inputValue.nhap_nvkd == 1)
                {
                    if (this.inputValue.khuvuc_id == 0 || this.modelInput.txtKhuVuc == "")
                    {
                        this.$root.$toast.error("Bạn chưa chọn khu vực quản lý địa bàn cho thuê bao!")
                        return;
                    }
                }
                this.inputValue.tocdotn_id = 0;
                this.inputValue.muccuoctn_id = 0;
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.BRCD_DOITAC)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboGoiCuocAdsl
                    if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL || this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH
                         || this.inputValue.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER)
                        this.inputValue.muccuoctn_id = this.modelCbo.cboMucCuoc;
                }
                else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGA_EYES)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboGoiCuocAdsl;
                }
                else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboTocDoKenhTSL;
                }
                else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.METRONET
                    || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MGW_DOITAC)
                {
                    this.inputValue.tocdotn_id = this.modelCbo.cboTocDo;
                }

                if (this.buttonState.tsbtnNhapMoi == false)
                {
                    if (!await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.inputValue.thuebao_id, this.inputValue.dichvuvt_id) == 0)
                    {
                        this.$root.$toast.error("Thuê bao không tồn tại trong danh bạ, không cập nhật được ");
                        return;
                    }

                    if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CQ ||
                        this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CQ ||
                        this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_2M || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                        this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_TUONGTU)
                    {
                        if (this.inputValue.isdn_sochinh)
                            for (let i = 0; i < ds_isdn.length; i++)
                            {
                                let dr = ds_isdn[0][i];
                                if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(dr["MA_TB"].toString(), "0", this.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN))
                                {
                                    return;
                                }
                            }
                    }

                    //Kiểm tra xem thuê bao có thuộc hợp đồng nào khác không
                    if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTB.trim(), "0", this.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN))
                        return;
                    //Hieutc: 19.11.2012: Kiểm tra xem thuê bao mới có lập phụ lục khác ko?
                    if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH 
                        || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.IMS 
                        || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.GPHONE)
                    {
                        if (this.modelInput.txtMaTB.trim() != this.modelInput.txtMaTBMoi.trim())
                        {
                            if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTBMoi.trim(), "0", this.inputValue.dichvuvt_id, Enum.LoaiHopDong.DI_CHUYEN)) return;
                        }
                    }
                    //
                    let dset = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.DI_CHUYEN, this.modelCbo.cboLoaihinhTB, this.inputValue.tocdotn_id,
                        this.inputValue.muccuoctn_id, this.modelCbo.cboKieuLD);

                    let huonggiao_tn = 0;
                    this.inputValue.luong_id = 0;
                    if (dset.length <= 0)
                    {
                        this.$root.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                        return;
                    }

                    //#region Nếu có đường IMS, MyTV lắp trên Fiber thì chặn
                    if (this.inputValue.kt_fiber_colapchung)
                    {
                        var dt_kt = await this.kt_fiber_colapchung(1, this.inputValue.thuebao_id, this.inputValue.hdtb_id, this.inputValue.hdkh_id);
                        if (dt_kt.length > 0)
                        {
                            if (dt_kt[0]["MA_TB"].toString() != null)
                            {
                                this.$root.$toast.error("Thuê bao " + this.modelInput.txtMaTB.trim() +
                                    " đang có đường IMS/MyTV lắp cùng. Bạn phải làm HĐ dịch chuyển IMS/MyTV của mã thuê bao "
                                    + dt_kt[0]["MA_TB"].toString() + " trước!");
                                return;
                            }
                        }
                        //}
                    }
                    //#endregion

                    huonggiao_tn = dset[0]["HUONGGIAO_ID"].toString()
                    this.inputValue.quytrinh_id = dset[0]["QUYTRINH_ID"].ToString();
                    this.inputValue.luong_id = dset[0]["LUONG_ID"].ToString();

                    await this.TaoDuLieu(true);
                    if (this.inputValue.loi_sinh_magd)
                    {
                        this.inputValue.loi_sinh_magd = false;
                        return;
                    }
                    if (this.inputValue.kt_adsl == false)
                        return;

                    //KhaiVK: 24/09/2020 - Bổ sung thông tin mã ngân sách vào bảng hdkh_sub
                    // dichchuyen.ThemMoi_HopDong_Sub
                    await this.ThemMoi_HopDong_Sub(this.dsHDKH, this.dsHDTT, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDKH,
                        this.dsDiaChiHDTT, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, this.dsHDTB_IMS, this.dsKM_HDTB,
                        huonggiao_tn, this.dsHDTB_KV, this.dsHDTT_NV, this.dsHDKH_Sub, this.dsHDTB_Sub);

                    await this.CapNhat_DiaChiBC();
                    //Tự động tạo 1 hợp đồng thanh lý khi thực hiện dịch chuyển + đổi số
                    //§ang xu ly
                    if (this.modelInput.txtMaTBMoi.trim() != "")
                        await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi.trim(), "", this.inputValue.dichvuvt_id);
                    else
                        await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, "", this.inputValue.dichvuvt_id);
                    ma_tb = this.modelInput.txtMaTB;

                    if (this.inputValue.tudong_ganthem_hdtb_dv)
                        await this.tudong_ganthem_hdtb_dv(this.inputValue.hdtb_id);

                    if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CQ ||
                        this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CQ ||
                        this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_2M || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                        this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_TUONGTU)
                    {
                        if (this.inputValue.isdn_sochinh)
                        {
                            if (this.inputValue.host_cu_id == this.inputValue.host_id)
                            {
                                this.inputValue.hdtb_id_cha = this.inputValue.hdtb_id;
                                for (let i = 0; i < ds_isdn.Tables[0].Rows.Count; i++)
                                {
                                    let dr = ds_isdn.Tables[0].Rows[i];
                                    if (dr["THUEBAO_CHA_ID"] != null)
                                    {
                                        this.inputValue.thuebao_id = dr["THUEBAO_ID"];
                                        this.modelInput.txtMaTB = dr["MA_TB"];
                                        this.inputValue.doituong_id = dr["DOITUONG_ID"];

                                        if (!await this.KiemTraDL_ThueBao()) return;
                                        this.KiemTraDuLieuNhap();
                                        if (!await this.KIEMTRA_MATB_KHOSO(this.modelInput.txtMaTB, this.inputValue.dichvuvt_id))
                                        {
                                            this.$root.$toast.error("Số máy chưa có trong kho số !");
                                            txtMaTB.Focus();
                                            return;
                                        }

                                        if (await this.KIEMTRA_HDTB_KHI_THEMMOI(this.modelInput.txtMaTB, this.inputValue.hdkh_id, this.inputValue.dichvuvt_id, 0))
                                        {
                                            this.$root.$toast.error("Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ");
                                            return;
                                        }

                                        if (!await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.inputValue.thuebao_id, this.inputValue.dichvuvt_id))
                                        {
                                            this.$root.$toast.error("Thuê bao không tồn tại trong danh bạ, không cập nhật được ");
                                            return;
                                        }

                                        if (!await this.KIEMTRA_TB_CUNG_MAKH_TB_PL(this.modelInput.txtMaGD, this.inputValue.thuebao_id))
                                        {
                                            Message_Box.ShowWarning("Thuê bao không cùng mã khách hàng với thuê bao đang thực hiện chuyển dịch !");
                                            return;
                                        }

                                        //Kiểm tra xem thuê bao có lập phụ lục khác không
                                        if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTB.trim(), "0", this.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN)) return;
                                        this.inputValue.tocdotn_id = 0;
                                        this.inputValue.muccuoctn_id = 0;
                                        if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.BRCD_DOITAC)
                                        {
                                            this.inputValue.tocdotn_id = this.modelCbo.cboGoiCuocAdsl;
                                            if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL || this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH
                                                 || this.inputValue.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER)
                                            {
                                                this.inputValue.muccuoctn_id = this.modelCbo.cboMucCuoc;
                                            }
                                        }
                                        else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGA_EYES)
                                        {
                                            this.inputValue.tocdotn_id = this.modelCbo.cboGoiCuocAdsl;
                                        }
                                        else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
                                        {
                                            this.inputValue.tocdotn_id = this.modelCbo.cboTocDoKenhTSL;
                                        }
                                        else if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN
                                            || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.METRONET
                                            || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MGW_DOITAC)
                                        {
                                            this.inputValue.tocdotn_id = this.modelCbo.cboTocDo;
                                        }
                                        dset = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.DI_CHUYEN, this.modelCbo.cboLoaihinhTB, this.inputValue.tocdotn_id, this.inputValue.muccuoctn_id, this.modelCbo.cboKieuLD);
                                        huonggiao_tn = 0;
                                        if (dset.length <= 0)
                                        {
                                            this.$root.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                                            return;
                                        }
                                        huonggiao_tn = dset[0]["HUONGGIAO_ID"].toString();
                                        this.inputValue.quytrinh_id = dset[0]["QUYTRINH_ID"].toString();
                                        //Hiếu - 22.09.2010. Kiểm tra xem tạo dữ liệu ADSL có lỗi ko.
                                        await this.TaoDuLieu_HDTB(true);
                                        await this.TaoDuLieu_HDTT(false);//TuấnNA thêm code để cập nhật matb_dd trong nút thêm thuê bao -- ngày 18.03.2011
                                        //Tạo dữ liệu cho thông tin thanh toán & cttienhd 17/09/2010
                                        await this.TaoDuLieu_THONGTIN_TT(true);
                                        await this.TaoDuLieu_CT_TIENHD(true);
                                        //explorer.exe
                                        await this.TaoDuLieu_DiaChi(true, 3);
                                        await this.TaoDuLieu_DKDV();
                                        await this.CapNhat_HDTB_ID_DangKyDV(hdtb_id);

                                        await this.CapNhat_HDTB_ID_HDTB_KV();

                                        // dsHDTB_CD = new HDTB_CD_DATA();
                                        // dsHDTB_ADSL = new HDTB_ADSL_DATA();
                                        // dsHDTB_TSL = new HDTB_TSL_DATA();
                                        // dsHDTB_MEWAN = new HDTB_MGWAN_DATA();
                                        // dsHDTB_GP = new HDTB_GP_DATA();
                                        // dsHDTB_IMS = new HDTB_IMS_DATA();
                                        // dsHDTBDV = new HDTB_DV_DATA();
                                        switch (dichvuvt_id)
                                        {
                                            case Enum.DichVuVienThong.CO_DINH:
                                                await this.TaoDuLieu_HDTB_CD(true);
                                                break;
                                            case Enum.DichVuVienThong.IMS:
                                                await this.TaoDuLieu_HDTB_IMS(true);
                                                break;
                                            case Enum.DichVuVienThong.ADSL:
                                                await this.TaoDuLieu_HDTB_ADSL(true);
                                                break;
                                            case Enum.DichVuVienThong.MEGA_EYES:
                                                await this.TaoDuLieu_HDTB_ADSL(true);
                                                break;
                                            case Enum.DichVuVienThong.MEGAWAN:
                                            case Enum.DichVuVienThong.METRONET:
                                                await this.TaoDuLieu_HDTB_MGWAN(true);
                                                break;
                                            case Enum.DichVuVienThong.TSL:
                                                await this.TaoDuLieu_HDTB_TSL(true);
                                                break;
                                            case Enum.DichVuVienThong.GPHONE:
                                                await this.TaoDuLieu_HDTB_GP(true);
                                                break;
                                            default:
                                                await this.TaoDuLieu_HDTB_CD(true);
                                                break;
                                        }
                                        // Ham ThemMoi_HopDongTB 17 tham so
                                        await this.ThemMoi_HopDongTB(this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, 
                                                    this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, 
                                                    this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, this.dsHDTT, 
                                                    this.dsHDTB_IMS, huonggiao_tn, this.dsHDTB_KV);

                                        if (this.modelInput.txtMaTBMoi.trim() != "")
                                            await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi.trim(), "", this.inputValue.hdtb_id);
                                        else
                                            await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, "", this.inputValue.hdtb_id);

                                        ma_tb = this.modelInput.txtMaTB;
                                        if (this.inputValue.tudong_ganthem_hdtb_dv)
                                            await this.tudong_ganthem_hdtb_dv(this.inputValue.hdtb_id);
                                    }
                                }

                                try
                                {
                                    //KNV|01/11/2018: Kiểm tra với tb dịch chuyển tới đường có sẵn->chuyển cáp từ thuê bao có sẵn sang
                                    await this.TUDONG_GAN_CAP_THUEBAO(this.inputValue.hdtb_id);
                                }
                                catch(ex) { }

                                //KNV|08/06/2017: Kiểm tra với tb lắp kèm hoặc trên đường có sẵn->gán tb vào hộ dân của thuê bao kèm.
                                await this.TUDONG_GAN_HODAN_THUEBAO(this.inputValue.hdtb_id);
                                let _ds_ = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
                                await this.HienThiDanhSacHDTB(_ds_);
                                this.inputValue.ma_tb = this.modelInput.txtMaTB;
                            }
                            else
                                this.$root.$toast.error("Thuê bao ISDN dịch chuyển khác host phải thực hiện đổi số lần lượt cho tất cả số phụ !");
                        }
                    }
                    //Nếu như lấy dữ liệu từ phiếu khảo sát
                    if (this.inputValue.bDuLieuKS)
                    {
                        await this.CAPNHAT_TRANGTHAI_HD(iHdtbID, Enum.TrangThaiHD.DA_LAY_DU_LIEU);
                        //cboDichVuVT.Enabled = true;
                        this.inputValue.bDuLieuKS = false;
                    }

                    await this.Them_MyTV_vao_goi_giadinh();

                    this.SetButton(3);
                    //lay du lieu tu ham nay, can viet lai chut HienThiTTHopDongTB
                    let ds_hdtb_tmp = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id);

                    await this.HienThiDanhSacHDTB(ds_hdtb_tmp);
                    await this.HienThiTTHopDongTB();

                    //lay du lieu cua doan nay HienThiTTHopDongTT
                    let ds_hdtt_tmp = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id);
                    await this.HienThiDanhSacHDTT(ds_hdtt_tmp);
                    await this.HienThiTTHopDongTT(this.inputValue.hdkh_id);
                    await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id);
                }
                else
                {
                    
                    //Hiếu thêm kiểm tra mã thuê bao trước khi update hd/pl 27.09.2010
                    if (this.modelInput.txtMaTBMoi.trim() == "")
                    {
                        if (!this.KiemTraTBTrongHD(this.modelInput.txtMaTB, this.inputValue.lvwThueBao, 3))
                        {
                            this.$root.$toast.error("Không có mã thuê bao " + this.modelInput.txtMaTB + " trong HĐ/PL. Hãy nhập lại mã thuê bao");
                            return;
                        }
                    }
                    let ds = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.DI_CHUYEN, this.modelCbo.cboLoaihinhTB, this.inputValue.tocdotn_id, this.inputValue.muccuoctn_id, this.modelCbo.cboKieuLD);

                    let huonggiao_tn = 0;
                    this.inputValue.luong_id = 0;
                    if (ds.length <= 0)
                    {
                        this.$root.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                        return;
                    }

                    if (!await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, thuebao_id, dichvuvt_id))
                    {
                        this.$root.$toast.error("Thuê bao không tồn tại trong danh bạ, không cập nhật được ");
                        return;
                    }

                    huonggiao_tn = ds[0]["HUONGGIAO_ID"];
                    this.inputValue.quytrinh_id = ds[0]["QUYTRINH_ID"];
                    this.inputValue.luong_id = ds[0]["LUONG_ID"];
                    await this.TaoDuLieu(false);
                    if (this.inputValue.kt_adsl == false)
                        return;

                    await this.CapNhat_HopDong(this.dsHDKH, this.dsHDTT, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi,
                        this.dsDiaChiHDKH, this.dsDiaChiHDTT, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP,
                        this.dsTTTT, this.dsCTTHD, this.dsHDTB_IMS, this.dsKM_HDTB, this.dsHDTB_KV, this.dsHDTT_NV);

                    await this.CapNhat_DiaChiBC();
                    //hoangpkn : 14/06/2014
                    // Đối với loại hợp đồng có thay đổi tốc độ, mức cước
                    // Phải lấy lại huonggiao_tn để update vào bảng giaophieu
                    await this.Update_huonggiao_tiepnhan(this.inputValue.hdtb_id, huonggiao_tn);
                    // end hoàng thêm
                    if (this.modelInput.txtMaTBMoi.trim() != "")
                        await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi.trim(), "", this.inputValue.dichvuvt_id);
                    else
                        await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, "", this.inputValue.dichvuvt_id);

                    ma_tb = this.modelInput.txtMaTB;

                    if (this.inputValue.tudong_ganthem_hdtb_dv)
                        await this.tudong_ganthem_hdtb_dv(this.inputValue.hdtb_id);

                    this.SetButton(3);
                    let ds1 = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id);
                    let ds2 = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id);
                    // await this.HienThiDanhSacHDTB(tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkh_id));
                    // await this.HienThiDanhSacHDTT(tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(hdkh_id));
                    await this.HienThiDanhSacHDTB(ds1);
                    await this.HienThiDanhSacHDTT(ds2);
                    await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id);
                    if (this.buttonState.tsbtnNhapMoi == false) //temp rem
                    {
                        //lvwThueBao.Items[0].Selected = true;
                    }
                    else
                    {
                        // IEnumerable<ListViewItem> _q = from ListViewItem i in lvwThueBao.Items
                        //                                where i.SubItems[2].Text == hdtb_id.ToString()
                        //                                select i;
                        // if (_q.Count() > 0)
                        //     _q.First().Selected = true;
                    }
                }
                ma_tb = this.modelInput.txtMaTB;
                this.inputValue.kieuld_ds = this.modelCbo.cboKieuLD;
                this.$root.$toast.error("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!");
                if (this.inputValue.VPHIEUCSKHDV_ID > 0) //PNLINH B2A: Cap nhat trang thai ttphdv_id sau khi lien ket hd
                {
                    let ip = await this.getIp();
                     await this.b2a_ht_phieucskhdv(this.inputValue.VPHIEUCSKHDV_ID, this.$root.token.getNhanVienID(), 
                        this.$root.token.getDonViID(), this.$root.token.getUserName(),ip, ip, 1 )
                }
                
            }
            catch (exp)
            {
                this.$root.$toast.error("" +exp.response.data.message_detail)
            }
        },
        b2a_ht_phieucskhdv: async function(phieucskhdv_id, nhanvien_id, donvi_id, nguoi_cn, ip_cn, may_cn, kieu){
            let data = {
                phieucskhdv_id: phieucskhdv_id,
                nhanvien_id: nhanvien_id,
                donvi_id: donvi_id,
                nguoi_cn: nguoi_cn,
                ip_cn: ip_cn,
                may_cn: may_cn,
                kieu: kieu
            }
            await this.$root.context.post('/web-hopdong/chuyendich/b2a_ht_phieucskhdv', data)
        },
        onEnterMaGD: async function(){
            try {
                this.loading(true);
                //if (this.modelInput.txtMaGD != '')
                {
                    let input = 'VTDC-02/0554133'//this.modelInput.txtMaGD.trim()
                    let ds = null
                    ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(input, Enum.LoaiHopDong.DI_CHUYEN, this.$root.token.getDonViID(), Enum.TrangThaiHD.MOI, this.$root.token.getNhanVienID(), 0)
                    console.log("🚀 ~ file: index.vue ~ line 3168 ~ onEnterMaGD:function ~ ds", ds)
                    await this.HienThiTTHopDongKH(ds);//
                    
                }
            }
            catch (error) {
                this.$root.$toast.error("" +error.response.data.message_detail)
            }
            finally {
                this.loading(false);
            }
            
        },
        CheckIsNumber: function (str) {
            try {
                let num = parseInt(str);
                return true;
            } catch (e) {
                return false;
            }
        },
        //#region Kiểm tra dữ liệu
        kiemtra_hd_bancheo_tc: async function(thuebao_id, loaihop_id){
            let data = {
                vthuebao_id: thuebao_id,
                vloaihd_id: loaihop_id
            }
            let res = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_hd_bancheo_tc', data)
            return res.data
        },
        kiemtra_ngaysinh: async function(ngaysinh){
            let res = await this.$root.context.get('/web-hopdong/chuyendich/kiemtra_ngaysinh?vngaysinh=', ngaysinh)
            return res.data
        },
        KiemTraDL_KhachHang: async function()
        {
            let kq_bc = await this.kiemtra_hd_bancheo_tc(thuebao_id, Enum.LoaiHopDong.DI_CHUYEN);
            if (kq_bc.data != "OK")
            {
                this.$root.$toast.error(' '+kq_bc.data)
                return false;
            }

            if (this.modelInput.txtTenKH == "")
            {
                this.$root.$toast.error("Bạn chưa nhập tên khách hàng !")
                this.activetab = 2
                //txtTenKH.Focus();
                return false;
            }

            if (this.modelInput.txtDiaChiKH == "" || this.modelInput.quankh_id == 0 || this.modelInput.phuongkh_id == 0)
            {
                this.$root.$toast.error("Bạn chưa nhập địa chỉ khách hàng !")
                this.activetab = 2
                // txtDiaChiKH.Focus();
                return false;
            }
            if (this.modelInput.txtMaHDcu == "")
            {
                this.$root.$toast.error("Bạn chưa nhập mã hợp đồng cho thuê bao dịch chuyển!")
                this.activetab = 1
                //txtMaHDcu.Focus();
                return false;
            }
            if (this.modelInput.txtSoGT == "")
            {
                this.$root.$toast.error("Hãy nhập số giấy tờ !")
                this.activetab = 2
                //txtSoGT.Focus();
                return false;
            }

            if (this.modelInput.txtNgayCap == "")
            {
                this.$root.$toast.error("Bạn chưa nhập ngày cấp giấy tờ !")
                this.activetab = 2
                //txtNgayCap.Focus();
                return false;
            }
            if (this.modelInput.txtHoKhau == "" && this.modelInput.txtSoGT == "")
            {
                this.$root.$toast.error("Bạn chưa nhập hộ khẩu hoặc loại giấy tờ khác !")
                //txtHoKhau.Focus();
                return false;
            }
            if (this.modelInput.txtNguoiDD.trim() == "")
            {
                this.$root.$toast.error("Hãy nhập tên người đại diện !")
                this.activetab = 2
                // txtNguoiDD.Focus();
                return false;
            }
            if (this.modelInput.txtNgayCap == "")
            {
                if (this.activetab == 2) this.activetab = 1;
                this.$root.$toast.error("Bạn chưa nhập ngày cấp giấy tờ !")
                // txtNgayCap.Focus();
                return false;
            }
            if (this.modelInput.txtNgaySN == "")
            {
                this.$root.$toast.error("Bạn chưa nhập ngày sinh khách hàng !")
                this.activetab = 2;
                //txtNgaySN.Focus();
                return false;
            }

            if (this.modelInput.txtNgaySN != "")
            {
                if (this.inputValue.kieukh_canhan || this.modelCbo.cboGiayToKH == Enum.LoaiGiayTo.CHUNG_MINH_THU)//Nếu là khách hàng cá nhân thì kiểm tra đã đủ 18 tuổi chưa
                {
                    if (await this.kiemtra_ngaysinh(this.modelInput.txtNgaySN) == 0)
                    {
                        if (this.activetab == 1) this.activetab = 2;
                        this.$root.$toast.error("Khách hàng chưa đủ 18 tuổi để làm hợp đồng!")
                        //txtNgaySN.Focus();
                        return false;
                    }
                }
            }

            if (this.inputValue.TNC1_ID == 0 || this.inputValue.TNC2_ID == 0 || this.inputValue.TNC3_ID == 0)
            {
                this.$root.$toast.error("Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại !")
                if (this.activetab == 1) this.activetab = 2;
               // cboNganhNgheLoc.Focus();
                return false;
            }
            //Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
            if (this.modelInput.txtMaKH.trim() != "")
            {
                if (laphd.Kiemtra_makh_mtt(this.modelInput.txtMaKH.trim(), 1, LoaiHopDong.DI_CHUYEN) == 0)
                {
                    this.$root.$toast.error("Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!")
                    //txtMaKH.Focus();
                    return false;
                }
            }
            if (this.modelInput.txtMaGD.trim() != "")
            {
                if (laphd.Kiemtra_makh_mtt(this.modelInput.txtMaGD.trim(), 3, LoaiHopDong.DI_CHUYEN) == 0)
                {
                    this.$root.$toast.error("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!")
                    // txtMaGD.Focus();
                    return false;
                }
            }
            if (this.modelInput.txtTelKH.trim() == "")
            {
                this.$root.$toast.error("Hãy nhập số điện thoại của khách hàng!")
                this.activetab = 2;
               // txtTelKH.Focus();
                return false;
            }
            //CuongNM - Kiểm tra nhập số điện thoại theo đúng chuẩn - VB 3309/VNPT VNP-KTNV
            if (this.modelInput.txtTelKH.trim().lenh < 10)
            {
                this.$root.$toast.error("Số điện thoại của khách hàng không đúng định dạng! Độ dài tối thiểu 10 chữ số.")
                this.activetab = 2;
                // txtTelKH.Focus();
                return false;
            }
            if (this.modelInput.txtTelKH.trim().length > 11)
            {
                this.$root.$toast.error("Số điện thoại liên hệ của khách hàng không được quá 11 ký tự.")
                this.activetab = 2;
                // txtTelKH.Focus();
                return false;
            }
            if (!this.IsNumber(this.modelInput.txtTelKH.trim()))
            {
                this.$root.$toast.error("Số điện thoại liên hệ của khách hàng phải là số!")
                 this.activetab = 2;
                //txtTelKH.Focus();
                return false;
            }
            if (!this.modelInput.txtTelKH.trim().startsWith("0"))
            {
                 this.$root.$toast.error("Dữ liệu điện thoại liên hệ phải bắt đầu là số 0")
                 this.activetab = 2;
                //txtTelKH.Focus();
                return false;
            }

            if (this.inputValue.khong_batbuoc_ctv == 0)
            {
                if (!this.modelCkeckBox.chkCTV || this.modelInput.txtCTV == '' || this.inputValue.ctv_id == 0)
                {
                    this.$root.$toast.error("Bạn chưa chọn nhân viên phát triển!")
                    //txtCTV.Focus();
                    return false;
                }

            }
            if (this.inputValue.batbuoc_gioithieu_dichchuyen == 1)
            {
                if (!this.modelCkeckBox.chkNguoiGT || this.modelInput.txtNguoiGT == '' || this.inputValue.nguoigt_id == 0)
                {
                    this.$root.$toast.error("Bạn chưa chọn người giới thiệu hợp đồng/phụ lục")
                    //txtNguoiGT.Focus();
                    return false;
                }
            }

            if (this.modelCkeckBox.chkCTV && this.modelInput.txtCTV == '' && this.inputValue.ctv_id == 0)
            {
                this.$root.$toast.error("Bạn chưa chọn nhân viên phát triển!")
                // txtCTV.Focus();
                return false;
            }
            if (this.modelCkeckBox.chkNguoiGT && this.modelInput.txtNguoiGT == '' && this.inputValue.nguoigt_id == 0)
            {
                this.$root.$toast.error("Bạn chưa chọn nhân viên giới thiệu hợp đồng!")
                // txtNguoiGT.Focus();
                return false;
            }
            //region Kiểm tra mã ngân sách
            let kieukh = dtLoaiKH.filter(x=>x.LOAIKH_ID == this.modelCbo.cboLoaiKH)[0]["LOAI"]//dtLoaiKH.Select("LOAIKH_ID=" + cboLoaiKH.EditValue)[0]["LOAI"].ToString();
            if (kieukh != "")
                this.inputValue.vkieukh_id_ = kieukh;

            if (this.inputValue.vkieukh_id_ == Enum.KIEU_KH.DOANH_NGHIEP) //Khách hàng doanh nghiệp
            {
                if (this.modelCbo.cboGiayToKH == Enum.LoaiGiayTo.MA_NGANSACH)
                {
                    let mans = "";
                    mans = this.modelInput.txtSoGT.trim();
                    if (!this.CheckIsNumber(mans))
                    {
                        this.$root.$toast.error("Mã ngân sách phải là dạng số.\nBạn hãy kiểm tra lại!")
                        return false;
                    }

                    if (mans.length != 7)
                    {
                         this.$root.$toast.error("Mã ngân sách phải đủ 7 ký tự số. \nBạn hãy kiểm tra lại!")
                        return false;
                    }
                    if (mans.substring(0, 1) != "1" && mans.substring(0, 1) != "2" && mans.substring(0, 1) != "3" && mans.substring(0, 1) != "7" && mans.substring(0, 1) != "8" && mans.substring(0, 1) != "9")
                    {
                        this.$root.$toast.error("Mã ngân sách phải bắt đầu là 1/2/3/7/8/9. \nBạn hãy kiểm tra lại!")
                        return false;
                    }
                }
                let mst_ = this.modelInput.txtMaSTKH.trim();
                if (this.modelInput.ts_kt_mstmns_kh == 1)
                {
                    if (mst_ == '' && (this.dsHDKH_Sub.length == 0 ||
                        (this.dsHDKH_Sub.length > 0 && this.dsHDKH_Sub[0]["MA_NS"] == null)))
                    {
                        this.$root.$toast.error("Bạn phải nhập mã số thuế hoặc mã ngân sách")
                        return false;
                    }
                    if (mst_ != '')
                    {
                        var kq_kt = await this.KiemTra_MST(mst_)
                        if (kq_kt.data != "OK")
                        {
                            this.$root.$toast.error(' '+kq_kt)
                            return false;
                        }
                    }

                    if (this.dsHDKH_Sub.length > 0 && this.dsHDKH_Sub[0]["MA_NS"] != null)
                    {
                        let mans = "";
                        mans = this.dsHDKH_Sub[0]["MA_NS"].toString();
                        if (!this.CheckIsNumber(mans))
                        {
                            this.$root.$toast.error("Mã ngân sách phải là dạng số.\nBạn hãy kiểm tra lại!")
                            return false;
                        }

                        if (mans.length != 7)
                        {
                            this.$root.$toast.error("Mã ngân sách phải đủ 7 ký tự số. \nBạn hãy kiểm tra lại!")
                            return false;
                        }
                        // int[] ArrCheck = { 1, 2, 3, 7, 8, 9 };
                        if (mans.substring(0, 1) != "1" && mans.substring(0, 1) != "2" && mans.substring(0, 1) != "3" && mans.substring(0, 1) != "7" && mans.substring(0, 1) != "8" && mans.substring(0, 1) != "9")
                        {
                            this.$root.$toast.error("Mã ngân sách phải bắt đầu là 1/2/3/7/8/9. \nBạn hãy kiểm tra lại!")
                            return false;
                        }
                    }

                }
            }
            else
                if (this.modelInput.txtMaSTKH.trim() != '')
                {
                    var kq_kt = await this.KiemTra_MST(this.modelInput.txtMaSTKH.trim())
                    if (kq_kt.toString() != "OK")
                    {
                        this.$root.$toast.error(' '+kq_kt.toString())
                        return false;
                    }
                }

            return true;
        },
        KiemTra_MST: async function(mst){
            let res = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_mst?vmst=', mst)
            return res.data
        },
        // IsNumber: function(pValue)
        // {
        //     for (const c of pValue)
        //     {
        //         if (!Char.IsDigit(c))
        //             return false;
        //     }
        //     return true;
        // },
        IsNumber: function(s) {
            return !isNaN(s - parseFloat(s));
        },
        KiemTraDL_ThanhToan: async function()
        {
            if (this.modelInput.txtTenTT == "")
            {
                this.$root.$toast.error("Hãy nhập tên thanh toán !")
                this.activetab = 2;
                //txtTenTT.Focus();
                return false;
            }

            if ((this.modelCbo.cboMaDD == -1 ) && this.modelInput.txtMaTT != "")
            {
                this.$root.$toast.error("Hãy chọn mã dại diện !")
                //cboMaDD.Focus();
                return false;
            }

            if (this.modelInput.txtDiaChiTT == "" || this.inputValue.quantt_id == 0 || this.inputValue.phuongtt_id == 0)
            {
                this.$root.$toast.error("Hãy nhập địa chỉ thanh toán !")
                this.activetab = 2;
                // txtDiaChiTT.Focus();
                return false;
            }
            if (this.modelInput.txtDiaChiCT == "")
            {
                this.$root.$toast.error("Hãy nhập địa chỉ chứng từ !")
                this.activetab = 2;
                // txtDiaChiCT.Focus();
                return false;
            }
            //Kiểm tra matt:Hiếu: 10/08/2010
            if (await this.KIEMTRA_MATT(this.modelInput.txtMaTT, this.inputValue.thanhtoan_id)[0].rs != 0)
            {
                if (this.activetab == 2) this.activetab = 1
                this.$root.$toast.error("Kiểm tra lại mã thanh toán !")
                //txtMaTT.Focus();
                return false;
            }
            if (this.modelInput.txtMaNVTC == "")
            {
                if (this.activetab == 2) this.activetab = 1
                this.$root.$toast.error("Hãy nhập mã nhân viên thu cước !")
                //txtMaNVTC.Focus();
                return false;
            }

            //Hieutc: 11.10.2011: Kiểm tra mã thanh toán đã theo đúng chuẩn chưa
            if (this.modelInput.txtMaTT.trim() != "")
            {
                if (await this.Kiemtra_makh_mtt(this.modelInput.txtMaTT.trim(), 2, Enum.LoaiHopDong.DI_CHUYEN)[0].rs == 0)
                {
                    this.$root.$toast.error("Mã thanh toán không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!")
                    //txtMaTT.Focus();
                    return false;
                }
            }

            if (this.modelCbo.vcboDonViQL.length <= 0)
            {
                if (this.activetab == 2) this.activetab = 1
                this.$root.$toast.error("Địa chỉ thanh toán này chưa được thiết lập đơn vị quản lý !")
                //btnDiaChiTT.Focus();
                return false;
            }
            else
            {
                if (this.modelCbo.cboDonViQL == 0 || this.modelCbo.cboDonViQL == -1)
                {
                    if (this.activetab == 2) this.activetab = 1
                    // btnDiaChiTT.Focus();
                    this.$root.$toast.error("Địa chỉ thanh toán này chưa được thiết lập đơn vị quản lý !")
                    return false;
                }
            }

            //KhaiVK: 12/06/2018: Kiểm tra mã số thuế theo chuẩn
            let maSoThue = this.modelInput.txtMST.trim();
            if (maSoThue != '' && this.modelInput.ts_kt_mst == 1)
            {
                let ketQua = this.KiemTraDinhDangMaSoThue(maSoThue);
                if (ketQua != "OK")
                {
                    this.$root.$toast.error(' '+ketQua)
                    // txtMST.Focus();
                    return false;
                }
                else
                {
                    let ds_maKH = []
                    ds_maKH = await this.LAY_DS_MAKH_THEO_MST(maSoThue, this.inputValue.hdkh_id, this.inputValue.khachhang_id);
                    if (ds_maKH.data.length == 1)
                    {
                        this.$root.$toast.error("Mã số thuế " + maSoThue + " đang được sử dụng bởi mã khách hàng: " +
                                        ds_maKH[0]["MA_KH"].toString() + ".")
                        //txtMST.Focus();
                        return false;
                    }
                    else if (ds_maKH.data.length > 1)
                    {
                        let str = "";
                        for (let i = 0; i < ds_maKH.data.length; i++)
                        {
                            str = str + ds_maKH[i]["MA_KH"].toString() + ",";
                        }
 
                        this.$root.$toast.error("Mã số thuế " + maSoThue + " đang được sử dụng bởi mã khách hàng: " +
                                        str.substring(0, str.Length - 1) + ".")
                       // txtMST.Focus();
                        return false;
                    }
                }
            }

            return true;
        },
        KIEMTRA_MATT: async function(vma_tt, vthanhtoan_id){
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/kiemtra_matt?vma_tt=${vma_tt}&vthanhtoan_id=${vthanhtoan_id}`)
            return res.data
        },
        Kiemtra_makh_mtt: async function(ma_kh, kieu, vloaihd_id){
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/kiemtra_makh_mtt?ma_kh=${ma_kh}&kieu=${kieu}&vloaihd_id=${vloaihd_id}`)
            return res.data
        },
        KiemTraDinhDangMaSoThue: async function(inMaSoThue){

            try
            {
                let ketqua = "OK";
                //Cấu trúc MST 10 số: N1N2N3N4N5N6N7N8N9N10
                //Cấu trúc MST 13 số: N1N2N3N4N5N6N7N8N9N10-N11N12N13
                if (inMaSoThue.trim() != "")
                {
                    if (inMaSoThue.length < 10)
                    {
                        ketqua = "Mã số thuế tối thiểu 10 ký tự.\nBạn hãy kiểm tra lại!";
                        return ketqua;
                    }
                    if (inMaSoThue.includes(' '))
                    {
                        ketqua = "Mã số thuế không được có khoảng trống.\nBạn hãy kiểm tra lại!";
                        return ketqua;
                    }
                    let maSoThue = inMaSoThue.substring(0, 10);
                    if (!this.CheckIsNumber(maSoThue))
                    {
                        ketqua = "Mã số thuế phải là dạng số.\nBạn hãy kiểm tra lại!";
                        return ketqua;
                    }
                    //N10 -  Chữ số kiểm tra
                    let ArrCheck = [ 31, 29, 23, 19, 17, 13, 7, 5, 3 ];
                    let checkNumber = 0;

                    for (let i = 0; i < maSoThue.length - 1; i++)
                        checkNumber += maSoThue.substring(i, 1) * ArrCheck[i];
                    if (maSoThue.substring(9, 1) !== (10 - checkNumber % 11))
                    {
                        ketqua = "Mã số thuế không hợp lệ!.\nBạn hãy kiểm tra lại!";
                        return ketqua;
                    }
                    //Với MST 13 số
                    if (inMaSoThue.Length > 10)
                    {
                        if (inMaSoThue.Length > 14)
                        {
                            ketqua = "Mã số thuế không hợp lệ.\nBạn hãy kiểm tra lại!";
                            return ketqua;
                        }
                        if (inMaSoThue.substring(10, 1) != "-")
                        {
                            //ketqua = "Mã số thuế 13 số chỉ bao gồm chữ số và ký tự -.\nBạn hãy kiểm tra lại!";
                            ketqua = "Mã số thuế 13 số không đúng định dạng theo quy định.\nCấu trúc MST 13 số: N1N2N3N4N5N6N7N8N9N10-N11N12N13 \nBạn hãy kiểm tra lại!";
                            return ketqua;
                        }
                        // Kiểm tra 3 ký 
                        let maSoThue2 = inMaSoThue.substring(11, 3);
                        if (!this.CheckIsNumber(maSoThue2))
                        {
                            ketqua = "3 ký tự cuối MST phải là dạng số.\nBạn hãy kiểm tra lại!";
                            return ketqua;
                        }
                        else
                        {
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
        LAY_DS_MAKH_THEO_MST: async function(vmasothue, vhdkh_id, vkhachhang_id){
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/lay_ds_makh_theo_mst?vmasothue=${vmasothue}&vhdkh_id=${vhdkh_id}&vkhachhang_id=${vkhachhang_id}`)
            return res.data
        },
        KIEMTRA_DOISO: async function(ma_tb, dichvuvt_id, vquan_id, vphuong_id){
            let data = {
                ma_tb: ma_tb, dichvuvt_id: dichvuvt_id, quan_id: vquan_id, phuong_id: vphuong_id    
            }
            let res = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_doiso',data)
            return res.data
        },
        KiemTraDL_ThueBao: async function()
        {
            //#region Kiểm tra các điều kiện trên giao diện
            if (this.modelInput.txtMaHDcu == "" && this.inputValue.khachhang_id != 0)
            {
                this.$root.$toast.error("Bạn chưa nhập mã hợp đồng !")
                this.activetab = 1
                //txtMaHDcu.Focus();
                return false;
            }

            if (this.modelInput.txtTenTB == "")
            {
                this.$root.$toast.error("Bạn chưa nhập tên thuê bao !")
                this.activetab = 1
                // txtTenTB.Focus();
                return false;
            }

            if (this.modelInput.txtDiaChiTB == "" || this.inputValue.quantb_id == 0 || this.inputValue.phuongtb_id == 0)
            {
                this.$root.$toast.error("Bạn chưa nhập địa chỉ thuê bao !")
                this.activetab = 1
                // txtDiaChiTB.Focus();
                return false;
            }
            if (this.modelInput.txtDiaChiLD == "" || this.inputValue.quanld_id == 0 || this.inputValue.phuongld_id == 0)
            {
                this.$root.$toast.error("Bạn chưa nhập địa chỉ lắp đặt !!")
                this.activetab = 1
                // txtDiaChiLD.Focus();
                return false;
            }
            if (this.modelInput.txtNgaySinhTB == "")
            {
                if (this.activetab == 2) this.activetab = 1
                 this.$root.$toast.error("Hãy nhập ngày sinh thuê bao !")
                // txtNgaySinhTB.Focus();
                return false;
            }
            if (this.modelCbo.cboKieuLD == 0 || this.modelCbo.cboKieuLD == -1)
            {
                this.$root.$toast.error("Hãy chọn kiểu yêu cầu cho thuê bao này !")
                return false;
            }
            if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH)
            {
                
            }

            if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.GPHONE)
            {
                if (this.modelCbo.cboMucCuocTB == 0 || this.modelCbo.cboMucCuocTB == -1)
                {
                    this.$root.$toast.error("Hãy nhập mức cước thuê bao!")
                    return false;
                }
            }
            //longdx:km không có chi tiết không cho cập nhật
            if (this.modelCkeckBox.chkKhuyenMai && this.dsKM_HDTB.length == 0)
            {
                this.$root.$toast.error("Bạn chưa chọn chi tiết khuyến mại!")
                return false;
            }

            //KhaiVK: Địa chỉ lắp đặt có đặc điểm thì bắt buộc GDV phải chọn để chuẩn hóa dữ liệu giao phiếu tự động
            if (batbuoc_chon_dacdiem_dcld)
            {
                if (this.modelCbo.cboDichVuVT != Enum.DichVuVienThong.DI_DONG && this.modelCbo.cboLoaihinhTB != Enum.LoaiHinhTB.DIDONGTRATRUOC)
                {
                    if ((this.inputValue.quanld_id != 0 || this.inputValue.phuongld_id != 0) && (this.inputValue.phold_id != 0 || this.inputValue.apld_id != 0 || this.inputValue.khuld_id != 0))
                    {
                        let data = {
                            VPHANVUNG_ID: this.$root.token.getPhanVungID(),
                            VPHUONGLD_ID: this.inputValue.phuongld_id,
                            VPHOLD_ID: this.inputValue.phold_id,
                            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LAY_DS_DACDIEM'
                        }
                        let dsDD = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
                        if (dsDD != null && dsDD.data.length > 0)
                        {
                            if (this.inputValue.dacdiemld_id == 0)
                            {
                                this.$root.$toast.error("Địa chỉ lắp đặt có đặc điểm. Yêu cầu chọn đặc điểm!")
                                // txtDiaChiLD.Focus();
                                return false;
                            }
                        }
                    }
                }
            }//End KhaiVK

            //#endregion

            //#region Kiểm tra truyền số liệu
            if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
            {
                //Hiếu bổ sung bắt lỗi pải nhập vào địa chỉ đầu, địa chỉ cuối ngày 06/09/2010
                if (this.modelCbo.cboTramVTDau == 0 || this.modelCbo.cboTramVTDau == -1)
                {
                    this.$root.$toast.error("Hãy chọn đơn vị đầu !")
                     this.activetab = 1
                    // cboTramVTDau.Focus();
                    return false;
                }
                if (this.modelCbo.cboTramVTCuoi == 0 || this.modelCbo.cboTramVTCuoi == -1)
                {
                    this.$root.$toast.error("Hãy chọn đơn vị cuối !")
                     this.activetab = 1
                    // cboTramVTCuoi.Focus();
                    return false;
                }
                if (this.modelInput.txtDiaChiDau == "" || this.inputValue.quandau_id == 0 || this.inputValue.phuongdau_id == 0)
                {
                    this.$root.$toast.error("Hãy nhập địa chỉ đầu !")
                     this.activetab = 1
                    return false;
                }
                if (this.modelInput.txtDiaChiCuoi == "" || this.inputValue.quancuoi_id == 0 || this.inputValue.phuongcuoi_id == 0)
                {
                      this.$root.$toast.error("Hãy nhập địa chỉ cuối !")
                     this.activetab = 1
                    return false;
                }
                if (this.modelCkeckBox.chkDC_Dau == false && this.modelCkeckBox.chkDC_Cuoi == false)
                {
                    this.$root.$toast.error("Bạn chưa chọn thay đổi địa chỉ đầu hoặc cuối !")
                    this.activetab = 1
                    return false;
                }
            }
            //#endregion

            //#region Kiểm tra mã truy nhập
            //HieuTC - 23/09/2010 Kiểm tra khi kiểu lắp đặt trên đường có sẵn => mã truy nhập không đc trống.
            //CheckDataFacade obj = new CheckDataFacade();
            let kieu = await this.MAP_KIEU_LD_NEO(this.modelCbo.cboKieuLD)[0].KIEU
            if ((kieu == 1 || kieu == 2 || kieu == 4)
                && this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.CO_DINH
                && this.modelInput.txtMaTN_CD.trim().length == 0)
            {
                this.$root.$toast.error("Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!")
                if (this.activetab == 2) this.activetab = 1
                // txtMaTN_CD.Focus();
                return false;
            }
            if ((kieu == 1 || kieu == 2 || kieu == 4)
                && (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.IMS
                    || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.IMS_DOITAC)
                && this.modelInput.txtMATN_IMS.trim().length == 0)
            {
                this.$root.$toast.error("Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!")
                if (this.activetab == 2) this.activetab = 1
                // txtMATN_IMS.Focus();
                return false;
            }
            if ((kieu == 1 || kieu == 2 || kieu == 4)
                && (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL
                    || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.BRCD_DOITAC)
                && this.modelInput.txtMaTN_ADSL.trim().length == 0)
            {
                 this.$root.$toast.error("Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!")
                if (this.activetab == 2) this.activetab = 1
                // txtMaTN_ADSL.Focus();
                return false;
            }
            if ((kieu == 1 || kieu == 2 || kieu == 4)
                && (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MEGAWAN
                    || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.METRONET
                    || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MGW_DOITAC)
                && this.modelInput.txtMaTN_Wan.trim().length == 0)
            {
                 this.$root.$toast.error("Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!")
                if (this.activetab == 2) this.activetab = 1
                // txtMaTN_Wan.Focus();
                return false;
            }
            //#endregion

            //#region Kiểm tra nếu dịch chuyển khác trạm thì phải đổi số
            if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH)
            {
                let tg_thueso = await this.MAP_DB_CD_NEO(this.modelInput.thuebao_id)[0].TG_THUESO
                if (tg_thueso != "-1")
                {
                    this.$root.$toast.error("Bạn không được lập phụ lục dịch chuyển cho thuê bao thuê số !")
                    return false;
                }

                let nhomkieu_ld = await this.MAP_KIEU_LD_NEO(this.modelCbo.cboKieuLD)[0].NHOMKIEU_LD
                if (nhomkieu_ld == "1")
                {
                    if (this.inputValue.kieu_thueso == 0)
                    {
                        this.$root.$toast.error("Bạn chưa nhập thông tin thuê số !")
                        // btnThueSo.Focus();
                        return false;
                    }
                    if (this.inputValue.sothang_thueso == 0)
                    {
                        this.$root.$toast.error("Bạn chưa nhập thời gian thuê số ")
                        // btnThueSo.Focus();
                        return false;
                    }
                    if (this.inputValue.kieu_thueso == 1 && this.inputValue.noidung_thueso == "")
                    {
                        this.$root.$toast.error("Bạn chưa nhập số trượt !")
                        // btnThueSo.Focus();
                        return false;
                    }
                    if (this.inputValue.kieu_thueso == 2 && this.inputValue.noidung_thueso == "")
                    {
                        this.$root.$toast.error("Bạn chưa nhập nội dung thông báo !")
                        // btnThueSo.Focus();
                        return false;
                    }
                }
                // Kiểm tra di chuyển đổi số cố định
                if (this.modelInput.txtMaTBMoi.trim() == "")
                {
                    if (await this.KIEMTRA_DOISO(this.modelInput.txtMaTB.trim(), this.modelCbo.cboDichVuVT, this.inputValue.quanld_id, this.inputValue.phuongld_id) <= 0)
                    {
                        this.$root.$toast.error("Thuê bao " + this.modelInput.txtMaTB + " di chuyển sang khu vực mới. Bạn hãy chọn số mới")
                        // btnChonSM.Focus();
                        return false;
                    }
                }
            }
            //#endregion

            if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.METRONET)
            {
                if (this.inputValue.kt_kenhphu_ao == true)
                {
                    if (this.modelCbo.cboKieuLD != Enum.KieuLapDat.DICHCHUYEN_KP_AO)
                    {
                        this.$root.$toast.error("Thuê bao " + this.modelInput.txtMaTB + " là kênh phụ ảo. Bạn phải chọn kiểu lắp đặt là 'Dịch chuyển kênh phụ ảo' !")
                        return false;
                    }
                }
                else
                {
                    if (this.modelCbo.cboKieuLD == Enum.KieuLapDat.DICHCHUYEN_KP_AO)
                    {
                        this.$root.$toast.error("Bạn không được chọn kiểu lắp đặt 'Dịch chuyển kênh phụ ảo' cho thuê bao '" + this.modelInput.txtMaTB + "' !")
                        return false;
                    }
                }
            }

            if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.DICHVU_CNTT || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.ANTOAN_BAOMAT_TT
                || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TRUNGTAM_DULIEU || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.HOINGHI_TRUYENHINH)
            {
                // Nếu là thuê bao cần chuẩn hóa --> ko cho thực hiện hợp đồng. cần phải Đặt mới (chon kieu ld = chuan hoa) hoặc làm hợp đồng Tạm dừng/Thanh lý
                if (this.inputValue.check_chuahoa_cntt)
                {
                    if (await this.check_chuahoa_cntt(this.inputValue.thuebao_id, Enum.LoaiHopDong.DI_CHUYEN))
                    {
                        // if (MessageBox.Show("Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.\n Bạn muốn chuẩn hóa thông tin ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return false;
                        // {
                        //     var frm = new frmDatMoi();
                        //     frm.MdiParent = this.MdiParent;
                        //     frm.Show();
                        //     frm.Load_hopdong_chuanhoa_cntt(txtMaKH.Text.Trim(), dichvuvt_id, loaitb_id, thuebao_id);
                        // }
                        this.$bvModal.msgBoxConfirm(
                            "Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.\n Bạn muốn chuẩn hóa thông tin ?",
                            {
                                title: "Xác nhận hành động",
                                centered: true,
                                size: "sm",
                                okTitle: 'Đồng ý',
                                cancelTitle: 'Hủy',
                            }
                            )
                            .then((value) => {
                            if (value) {
                                let data = {_ma_kh: this.modelInput.txtMaKH, _dvvt_id: this.inputValue.dichvuvt_id, _loaitb_id: this.inputValue.loaitb_id, _thuebao_id: this.inputValue.thuebao_id}
                                // let data = {txtMaKH.Text.Trim(), dichvuvt_id, loaitb_id, thuebao_id}
                                this.$bvModal.show('popupThueBao11111')
                            } else {
                                return;
                            }
                            });

                        return false;
                    }
                }
            }

            // for (let i = 0; i < dtControl.Rows.Count; i++)
            // {
            //     if (cbo[i].Text == "")
            //     {
            //         this.$root.$toast.error("Chưa có dữ liệu về đơn vị thi công. Bạn hãy kiểm tra lại !")
            //         return false;
            //     }
            //     if (Convert.ToInt32(cbo[i].SelectedValue) == 0)
            //     {
            //         this.$root.$toast.error("Chưa có dữ liệu về đơn vị thi công. Bạn hãy kiểm tra lại !")
            //         return false;
            //     }
            // }

            return true;
        },
        check_chuahoa_cntt: async function(thuebao_id, hopdong_id){
            let data = {
                thuebao_id: thuebao_id,
                loaihd_id: hopdong_id
            }
            let res = await this.$root.context.post('/web-hopdong/chuyendich/check_chuahoa_cntt', data)
            if (res.data > 0)
                return true;
            else 
                return false;
        },
        KemTraDL_HD_CQ: async function(thuebao_id)
        {
            //let kiemtra = laphd.KiemTra_HD_CQ(thuebao_id, 2);
            let data = {
                vthuebao_id: thuebao_id,
                vloaihd_id: 2
            }
            let res = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_chuyenquyen_tb', data)
            let kiemtra = res.data[0][0]
            if (kiemtra > 0)
            {
                this.$root.$toast.error("Thuê bao đã thực hiện chuyển quyền, bạn không thể thực hiện dịch chuyển!")
                return false;
            }
            return true;
        },
        //#endregion
        LAY_DS_HOPDONG_THEO_MA_GD: async function(ma_gd, loaihd_id, donvi_id, tthd_id, nhanvien_id, donvi_dl_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                IN_MA_GD: ma_gd,
                IN_LOAIHD_ID: loaihd_id,
                IN_DONVI_ID: donvi_id,
                IN_TTHD_ID: tthd_id,
                IN_NHANVIEN_ID: nhanvien_id,
                IN_DONVI_DL_ID: donvi_dl_id,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LAY_DS_HOPDONG_THEO_MA_GD'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
             //await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-hopdong-theomagd', data)
            if (rs.data.length > 0) //mo rem 2 dong nay va dong tren sau
                return rs.data;
            else 
                return null
        },
        selectedRowChanged1: async function(data){},
        // selectedRowChanged2: async function(data){
        //     if (!!data){
        //         this.selectedThueBao = data;
        //     }
        // },
        StringToString: function(xauthamso)
        {
            if (xauthamso == null || xauthamso == '') return "";
            let letter;
            let Text11;
            let Text22 = "";

            Text11 = xauthamso;

            // for (let j = 0; j < Text11.length; j++)
            // {
            //     letter = Text11.substring(j, 1);
            //     if (letter == "\r")
            //         Text22 = Text22 + "\r";
            //     else if (letter == "\n")
            //         Text22 = Text22 + "\n";
            //     else
            //     {
            //         Text22 = (Text22 + letter);
            //     }
            // }
            return xauthamso;
        },
        ChuanHoaTen: function(ten)
        {
            if(ten!=null){
            let convertToArray = ten.toLowerCase().split(' ');
            let result = convertToArray.map(function(val) {
            return val.replace(val.charAt(0), val.charAt(0).toUpperCase());
            });
            return result.join(' ');
            }else
            return ''
        },
        phantap_laydoanhthu_dtn: async function(khachhang_id){
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/phantap_laydoanhthu_dtn?khachhang_id=${khachhang_id}`)
            console.log("🚀 ~ file: index.vue ~ line 798 ~ phantap_laydoanhthu_dtn:function ~ res", res)
            return res.data
        },
        // //#region HienThiTTHopDongKH
        HienThiTTHopDongKH: async function(ds)
        {
            try
            {
               
                console.log("🚀 ~ file: index.vue ~ line 791 ~ ds", ds)
                if (ds != null && ds.length >0)
                {
                    this.inputValue.ikieuld_id = 0;
                    this.inputValue.hdkh_id = ds[0].HDKH_ID;
                    this.modelInput.txtMaGDxt = ds[0].MA_GD;
                    this.modelInput.txtMaKH = ds[0].MA_KH;
                    this.modelDateTime.dtpNgayYC = ds[0].NGAY_YC;
                    if (ds[0].KHACHHANG_ID != null)
                        this.inputValue.khachhang_id = ds[0].KHACHHANG_ID;
                        
                    else
                        this.inputValue.khachhang_id = 0;
                    if (this.inputValue.phantap_kh)
                    {
                        let ds_dt_dtn = await this.phantap_laydoanhthu_dtn(this.inputValue.khachhang_id);
                        if (ds_dt_dtn.length > 0)
                        {
                            
                            if (ds_dt_dtn.data[0].doanhthu != null)
                                this.inputValue.pt_doanhthu = ds_dt_dtn.data[0].doanhthu
                            else
                                this.inputValue.pt_doanhthu = 0;

                            if (ds_dt_dtn.data[0].dtn != null)
                                this.inputValue.pt_diem_tn = ds_dt_dtn.data[0].dtn
                            else
                                this.inputValue.pt_diem_tn = 0;
                        }
                        else
                        {
                            this.inputValue.pt_doanhthu = 0;
                            this.inputValue.pt_diem_tn = 0;
                        }
                    }

                    if (ds[0].KIEUHD_ID != null)
                        this.modelCbo.cboKieu_HD = ds[0].KIEUHD_ID;
                    else
                        this.modelCbo.cboKieu_HD = Enum.KIEU_HD.TAI_GD;

                    this.modelInput.txtTenKH = this.ChuanHoaTen(ds[0].TEN_KH);
                    this.modelInput.txtDiaChiKH = this.ChuanHoaTen(ds[0].DIACHI_KH);
                    await this.HienThiTTDiaChi(1);
                    this.modelInput.txtNguoiDD = this.ChuanHoaTen(ds[0].NGUOI_DD);
                    this.modelInput.txtChucDanhDD = this.ChuanHoaTen(ds[0].CHUCDANH_DD);
                    this.modelInput.txtTelKH = ds[0].SO_DT;
                    this.modelInput.txtFaxKH = ds[0].SO_FAX;
                    this.modelInput.txtHoKhau = this.ChuanHoaTen(ds[0].HOKHAU);
                    this.modelInput.txtNoiCapHK = this.ChuanHoaTen(ds[0].NOICAP_HK);
                    //Hiếu bổ sung thông tin mã hợp đồng, ngày lập hợp đồng của khách hàng mới 25/08/2010
                    if (ds[0].NGAY_HK != null)
                        this.modelInput.txtNgayCapHK = DateTimeLib.toDateDisplay(ds[0].NGAY_HK)//Convert.ToDateTime(ds.Tables[0].Rows[0]["ngay_hk"].ToString()).ToString("dd/MM/yyyy");
                    else
                        this.modelInput.txtNgayCapHK = "";

                    if (ds[0].NGAY_SN != null)
                    {
                        this.modelInput.txtNgaySN = DateTimeLib.toDateDisplay(ds[0].NGAY_SN)//Convert.ToDateTime(ds.Tables[0].Rows[0]["ngay_sn"].ToString()).ToString("dd/MM/yyyy");
                    }
                    else
                    {
                        this.modelInput.txtNgaySN = "";
                    }
                    this.modelInput.txtSoGT = ds[0].SO_GT;
                    if (ds[0].NGAYCAP != null)
                    {
                        this.modelInput.txtNgayCap = DateTimeLib.toDateDisplay(ds[0].NGAYCAP)//Convert.ToDateTime(ds.Tables[0].Rows[0]["ngaycap"].ToString()).ToString("dd/MM/yyyy");
                    }
                    else
                    {
                        this.modelInput.txtNgayCap = "";
                    }
                    this.modelCbo.cboGiayToKH = ds[0].LOAIGT_ID;
                    this.modelInput.txtNoiCap = this.ChuanHoaTen(ds[0].NOICAP);
                    
                    //Hiếu bổ sung một số thông tin:
                    this.modelInput.txtMaSTKH = ds[0].MST;
                    this.modelInput.txtSoTKKH = ds[0].STK;
                    this.modelInput.txtNgayHetHan = ds[0].NGAYHETHAN_GT
                    await this.updateOption(ds[0].GIOITINH)
                    if (ds[0].QUOCTICH_ID != null)
                    {
                        this.modelCkeckBox.disalbeQT = true;
                        this.modelCbo.cboQuocTich = ds[0].QUOCTICH_ID;
                    }
                    else
                    {
                        this.modelCkeckBox.disalbeQT = false;
                        this.modelCbo.cboQuocTich = -1;
                    }
                    if (ds[0].DANTOC_ID != null)
                    {
                        this.modelCkeckBox.disalbeDT = true;
                        this.modelCbo.cboDanToc = ds[0].DANTOC_ID
                    }
                    else
                    {
                        this.modelCkeckBox.disalbeDT = false;
                        this.modelCbo.cboDanToc = "";
                    }

                    if (ds[0].NGANHNGHE_ID != null)
                    {
                        this.modelCkeckBox.disalbeNN = true;
                         this.modelCbo.cboNganhNgheLoc = ds[0].NGANHNGHE_ID
                        this.inputValue.nganhnghe_id = ds[0].NGANHNGHE_ID
                    }
                    else
                    {

                        this.modelCkeckBox.disalbeNN = false;
                        this.modelCbo.cboNganhNgheLoc = -1
                    }
                    this.modelInput.txtDiaChiNDD = this.ChuanHoaTen(ds[0].DIACHI_NDD);
                    if (ds[0].NGANHANG_ID != null)
                    {
                        this.modelCbo.cboNganHangKH = ds[0].NGANHANG_ID
                        this.modelCkeckBox.chkNganHangKH = true;
                    }
                    this.modelInput.txtGhiChuKH = ds[0].GHICHU;
                    //TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
                    
                    if (ds[0].TNC1_ID != null)
                    {
                        this.inputValue.TNC1_ID = ds[0].TNC1_ID;
                    }
                    if (ds[0].TNC2_ID != null)
                    {
                        this.inputValue.TNC2_ID = ds[0].TNC2_ID
                    }
                    if (ds[0].TNC3_ID != null)
                    {
                        this.inputValue.TNC3_ID = ds[0].TNC3_ID
                    }
                    if (ds[0].TNC4_ID != null)
                    {
                        this.inputValue.TNC4_ID = ds[0].TNC4_ID
                        
                    }
                    //
                    if (ds[0].LOAIKH_ID != null)
                        this.modelCbo.cboLoaiKH = ds[0].LOAIKH_ID;

                    if (ds[0].CTV_ID != null)
                    {
                        // this.chkCTV.CheckedChanged -= new System.EventHandler(this.chkCTV_CheckedChanged);
                        this.modelCkeckBox.chkCTV = true;
                        this.inputValue.ctv_id =ds[0].CTV_ID
                        let temp = await this.MAP_TEN_NHANVIEN_ID_NEO(this.inputValue.ctv_id)
                        this.modelInput.txtCTV = temp[0].TEN_NV
                        // this.chkCTV.CheckedChanged += new System.EventHandler(this.chkCTV_CheckedChanged);
                        
                    }
                    else
                    {
                        this.modelInput.txtCTV = "";
                        this.modelCkeckBox.chkCTV = false;
                    }
                    if (ds[0].NHANVIENGT_ID != null)
                    {
                        // this.chkNguoiGT.CheckedChanged -= new System.EventHandler(this.chkNguoiGT_CheckedChanged);
                        this.modelCkeckBox.chkNguoiGT = true;
                        this.inputValue.nguoigt_id = ds[0].NHANVIENGT_ID;
                        
                        let temp = await this.MAP_TEN_NHANVIEN_ID_NEO(this.inputValue.nguoigt_id)
                        this.modelInput.txtNguoiGT =  temp[0].TEN_NV
                        // this.chkNguoiGT.CheckedChanged += new System.EventHandler(this.chkNguoiGT_CheckedChanged);
                        
                    }
                    else
                    {
                        this.modelInput.txtNguoiGT = "";
                        this.modelCkeckBox.chkNguoiGT = false;
                    }


                    
                    if (ds[0].PHANLOAIKH_ID != null)
                    {
                        this.inputValue.PHANLOAIKH_ID =ds[0].PHANLOAIKH_ID
                        let temp = await this.MAP_PHANLOAIKH_ID_NEO(ds[0].PHANLOAIKH_ID)
                        if (temp[0].MA_PLKH != "-1")
                            this.modelInput.txtPhanLoaiKH = temp[0].MA_PLKH;

                        if (temp[0].NHOM_PL != "-1")
                            this.inputValue.nhomplkh = temp[0].NHOM_PL;
                    }
                    

                    //PNLINH: B2A 06/05/2020
                    if (ds[0].PHIEUCSKH_ID != null)
                    {
                        this.VPHIEUCSKH_ID = ds[0].PHIEUCSKH_ID

                    }
                    //

                    await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id);
                    await this.HienThiTTHopDongTT(this.inputValue.hdkh_id);
                    await this.HienThiTTHopDongTB();
                    let _data = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id)
                    await this.HienThiDanhSacHDTT(_data);
                    let ds_ = await this.F_LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id);
                    await this.HienThiDanhSacHDTB(ds_);
                    this.inputValue.kieuld_ds = this.modelCbo.cboKieuLD

                    this.SetButton(3);
                }
                else
                {
                    this.SetButton(0);
                }

                
            }
            catch (exp)
            {
                this.$root.$toast.error(' '+exp.response.data.message_detail)
            }
        },
        LAY_DS_HOPDONG_TT_THEO_HDKH_ID: async function(input){
             let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_HDKH_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LAY_DS_HOPDONG_TT_THEO_HDKH_ID'
            }
            let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return ds.data
        },
        HienThiTTHopDongTT: async function(hdkh_id){
            
            let data = {
            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            P_HDKH_ID: hdkh_id,
            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LAY_DS_HOPDONG_TT_THEO_HDKH_ID' 
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            try
            {
                if (res.data.length > 0)
                {
                    let ds = res.data
                    this.inputValue.hdtt_id = ds[0].HDTT_ID;
                    this.modelInput.txtMaTT = ds[0].MA_TT;
                    if (ds[0].THANHTOAN_ID != null || ds[0].THANHTOAN_ID != 0)
                        this.inputValue.thanhtoan_id = ds[0].THANHTOAN_ID;
                    else
                        this.inputValue.thanhtoan_id = 0;
                    this.modelInput.txtTenTT = this.ChuanHoaTen(ds[0].TEN_TT);
                    this.modelInput.txtDiaChiTT = this.ChuanHoaTen(ds[0].DIACHI_TT);
                    this.modelInput.txtDiaChiBC = this.ChuanHoaTen(ds[0].DIACHI_BC);
                    await this.HienThiTTDiaChi(2);
                    this.modelInput.txtMST = ds[0].MST;
                    this.modelInput.txtSTK = ds[0].STK;
                    if (ds[0].NGANHANG_ID != null)
                        this.modelCbo.cboNganHang = ds[0].NGANHANG_ID
                    this.modelCbo.cboHTTT = ds[0].HTTT_ID;
                    this.modelCbo.cboDonViQL = ds[0].DONVI_ID;
                    this.modelInput.txtMaTBDD = ds[0].MATB_DD
                    
                    ///TuấnNA thêm code hiển thị một số thông tin thanh toán -- Ngày 08/08/2010 
                    this.modelInput.txtGhiChuTT = ds[0].GHICHU;
                    this.modelInput.txtDiaChiCT = this.ChuanHoaTen(ds[0].DIACHI_CT);


                    //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
                    this.inputValue.DV_QHNS = ds[0].DV_QHNS;
                    this.inputValue.MACHUONG = ds[0].MACHUONG;
                    this.inputValue.MN_KT = ds[0].MN_KT;
                    this.inputValue.MA_NDKT = ds[0].MA_NDKT;
                    this.inputValue.MN_NSNN = ds[0].MN_NSNN;
                    if (ds[0].TIEN_DM != null)
                        this.inputValue.TIEN_DM =ds[0].TIEN_DM;
                    else
                        this.inputValue.TIEN_DM = 0;
                    //

                    ///tuấnNA thêm code lấy thông tin tuyến thu trong bảng HDTT --- Ngày 31/08/2010
                    if (ds[0].NHANVIEN_ID != null)
                    {
                        this.inputValue.nhanvientc_id = ds[0].NHANVIEN_ID;
                        let rs1 = await this.MAP_ID_NHANVIEN_ID_NEO(ds[0].NHANVIEN_ID)
                        this.modelInput.txtMaNVTC =  rs1[0].MA_NV;
                    }
                    else
                    {
                        this.inputValue.nhanvientc_id = 0;
                        this.modelInput.txtMaNVTC = "";
                    }
                    if (ds[0].TUYENTHU_ID != null)
                    {
                        this.inputValue.tuyenthu_id = ds[0].TUYENTHU_ID;
                        let rs2 = await this.MAP_ID_TUYENTHU_ID_NEO(ds[0].TUYENTHU_ID)
                        this.modelInput.txtMaTuyen = rs2[0].MA_TUYEN;
                    }
                    else
                    {
                        this.inputValue.tuyenthu_id = 0;
                        this.modelInput.txtMaTuyen = "";
                    }
                }
            }
            catch (exp)
            {
                this.$root.$toast.error(' '+exp.response.data.message_detail)                
            }
        },
        LAY_DS_KHUVUC_THEO_HD_DB: async function(vhdtb_id, vinhd_db)
        {   
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                vhdtb_id: vhdtb_id,//vhdtb_id,
                vinhd_db: vinhd_db,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_KHUVUC_THEO_HD_DB'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data
        },
        F_LAY_DS_HOPDONG_TB_THEO_HDKH_ID: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdkh_id: input,
                PROCEDURE_NAME: 'CSS.TRACUU_COMMON.LAY_DS_HOPDONG_TB_THEO_HDKH_ID'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data
        },
        HienThiTTHopDongTB: async function(){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdkh_id: this.inputValue.hdkh_id,
                PROCEDURE_NAME: 'CSS.TRACUU_COMMON.LAY_DS_HOPDONG_TB_THEO_HDKH_ID'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            
            try
            { 
                if (res.data.length > 0)
                {
                    let ds = res.data
                    this.inputValue.ikieuld_id = 0;
                    this.inputValue.hdtb_id = ds[0].HDTB_ID
                    
                    if (ds[0].THUEBAO_ID != null)
                        this.inputValue.thuebao_id = ds[0].THUEBAO_ID
                    else
                        this.inputValue.thuebao_id = 0;

                    let dsNV = null
                    
                    //duy sửa, cho hiển thị theo hdtb trước, sau đó mới hiển thị theo db_thuebao
                    try
                    {
                        dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.inputValue.hdtb_id, 1);
                        let ktra = dsNV.length;
                        if (ktra < 1)
                        {
                            dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.inputValue.thuebao_id, 2);
                        }
                    }
                    catch (ex)
                    {
                        dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.inputValue.thuebao_id, 2);
                    }

                    this.modelInput.txtKhuVuc = "";
                    if (dsNV != null && dsNV.length > 0)
                    {
                        this.modelInput.txtKhuVuc = dsNV[0].TEN_KV;
                        this.inputValue.khuvuc_id = dsNV[0].KHUVUC_ID

                        // hoangpkn : 17/11/2020. lấy dsHDTB_KV ra
                        let ip = await this.getIp()
                        let row = {}
                        row.HDTB_ID = this.inputValue.hdtb_id;
                        row.KHUVUC_ID = this.inputValue.khuvuc_id;
                        row.LOAIKV_ID = Enum.LOAI_KV.KHUVUC_DIABAN;
                        row.IP_CN = ip;
                        row.NGAY_CN = DateTimeLib.toDateDisplay(new Date());
                        row.NGUOI_CN = this.$root.token.getUserName();
                        row.MAY_CN = ip;
                        this.dsHDTB_KV.push(row)
                        // end hoàng
                    }

                    this.modelInput.txtMaTB = ds[0].MA_TB;
                    this.inputValue.ma_tb = this.modelInput.txtMaTB;
                    this.modelInput.txtTenTB = this.ChuanHoaTen(ds[0].TEN_TB);
                    this.inputValue.ma_tbmoi = ds[0].MA_TB;
                    this.modelInput.txtMaTBMoi = ds[0].MA_TB;
                    this.modelInput.txtDiaChiTB = this.ChuanHoaTen(ds[0].DIACHI_TB);
                    // //hieu
                    this.modelInput.txtDiaChiLD = this.ChuanHoaTen(ds[0].DIACHI_LD);
                     this.modelInput.txtNgaySinhTB = DateTimeLib.toDateDisplay(ds[0].NGAY_SN)// Convert.ToDateTime(ds.Tables[0].Rows[0]["ngay_sn"].ToString()).ToString("dd/MM/yyy");

                    if (ds[0].HDTB_CHA_ID != null)
                    {
                        this.inputValue.hdtb_id_cha = ds[0].HDTB_CHA_ID;
                        this.inputValue.loaisochinh = 0;
                    }
                    await this.HienThiTTDiaChi(3);

                    let temp = await this.TTCT_LoaiHinh_TB(ds[0].LOAITB_ID)
                    this.inputValue.dichvuvt_id = temp[0].DICHVUVT_ID;
                    this.modelCbo.cboDichVuVT =  temp[0].DICHVUVT_ID;

                    if (ds[0].GOIKT_ID != null)
                        this.modelCbo.cboGoiKT = ds[0].GOIKT_ID;
                    else
                        this.modelCbo.cboGoiKT = 0;

                    this.modelCbo.cboLoaihinhTB = ds[0].LOAITB_ID;
                    this.inputValue._loaitb_id =  ds[0].LOAITB_ID;
                    this.modelCbo.cboKieuLD = ds[0].KIEULD_ID
                    this.modelCbo.cboDoituongTB = ds[0].DOITUONG_ID
                    this.modelCbo.cboTT = ds[0].HDTT_ID
                    this.modelCbo.cboDaiVT = ds[0].donvi_id
                    // //Hiếu bổ sung lấy đơn vị đầu, đơn vị cuối ngày 06/09/2010
                    if (ds[0].DONVI_DAU_ID != null)
                        this.inputValue.donvi_dau_id = ds[0].DONVI_DAU_ID;
                    if (ds[0].DONVI_CUOI_ID = null)
                        this.inputValue.donvi_cuoi_id = ds[0].DONVI_CUOI_ID;
                    //
                    if (ds[0].MUCUOCTB_ID = null)
                    {
                        await this.HT_MucCuocTB_Combobox(ds[0].MUCUOCTB_ID);
                    }
                    else
                        this.modelCbo.vcboMucCuocTB = null;

                    await this.LAY_DS_THUEBAO_DICHVU(this.inputValue.hdtb_id, this.inputValue.thuebao_id, this.inputValue.loaitb_id, 1)
                    await this.HT_DichVuGT();

                    // //Lấy thông tin dịch vụ khác trong danh bạ để view ra

                    await this.LAY_DS_SUDUNG_DVK(this.inputValue.thuebao_id, 1);

                    // //
                    await this.HienThiThongTinCu();
                    await this.HienThiTTMoRongHD(this.inputValue.hdtb_id, this.inputValue.dichvuvt_id);

                    try//KieuNV:20/01/2018:Lấy thông tin kết cuối
                    {
                        let data = {
                            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                            P_HDTB_ID: this.inputValue.hdtb_id,
                            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LAY_DANHSACH_KC'
                        }
                        let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
                        this.inputValue.KetCuoi_ID = -1;
                        if (res != null && res.data.length > 0)
                        {
                            this.inputValue.KetCuoi_ID = res.data[0]["KETCUOI_ID"];
                        }
                    }
                    catch(ex) {  this.$root.$toast.error(' '+ex.response.data.message_detail)}

                    
                    await this.HT_DS_KhuyenMai_Combobox();
                    //Longdx: bổ sung lấy chi tiết KM
                    let dsTemp = await this.LAY_CHITIET_KM_THEO_HDTB_ID(this.inputValue.hdtb_id, 0);
                    if (dsTemp.length > 0)
                    {
                        if (dsTemp[0]["KHUYENMAI_ID"] != null)
                        {
                            this.modelCbo.cboKhuyenMai = dsTemp[0]["KHUYENMAI_ID"];
                            this.modelCkeckBox.chkKhuyenMai = true
                        }
                        else
                        {
                             this.modelCkeckBox.chkKhuyenMai = false
                        }

                        let ithang_huongdc = 0, ithang_huongkm = 0, ithang_tg = 0;
                        let dtiendatcoc_csd = 0, dtien_td = 0;
                        for (let i = 0; i < dsTemp.length; i++)
                        {
                            ithang_huongdc = 0; ithang_huongkm = 0; ithang_tg = 0;
                            dtiendatcoc_csd = 0; dtien_td = 0;
                            let dr = {}
                            
                            dr.THANG_HUONGDC = ithang_huongdc = dsTemp[i]["THANG_HUONGDC"]//ithang_huongdc;
                            dr.THANG_HUONGKM = ithang_huongkm = dsTemp[i]["THANG_HUONGKM"]//ithang_huongkm;
                            dr.SOTHANG_TG = ithang_tg = dsTemp[i]["SOTHANG_TG"]//ithang_tg;
                            dr.DATCOC_CSD = dtiendatcoc_csd = dsTemp[i]["DATCOC_CSD"]//dtiendatcoc_csd;
                            dr.TIEN_TD = dtien_td = dsTemp[i]["TIEN_TD"]//dtien_td;

                            if (dsTemp[i]["KIEU_BDKM"] == "3")
                            {
                                // if (DateTime.TryParseExact(dsTemp.Tables[0].Rows[i]["thang_bdkm"].ToString(), "yyyyMM", 
                                //culture, System.Globalization.DateTimeStyles.None, out tmp))
                                // {
                                //     dr.THANG_BD = int.Parse(dsTemp.Tables[0].Rows[i]["thang_bdkm"].ToString());
                                //     dr.THANG_KT = int.Parse(tmp.AddMonths(ithang_huongkm).ToString("yyyyMM"));
                                // }
                            }
                            if (dsTemp[i]["KIEU_BDTG"] == "3")
                            {
                                // if (DateTime.TryParseExact(dsTemp.Tables[0].Rows[i]["thang_bdtg"].ToString(), "yyyyMM", culture, System.Globalization.DateTimeStyles.None, out tmp))
                                // {
                                //     dr.THANG_BDTG = int.Parse(dsTemp.Tables[0].Rows[i]["thang_bdtg"].ToString());
                                //     dr.THANG_KTTG = int.Parse(tmp.AddMonths(ithang_tg).ToString("yyyyMM"));
                                // }
                            }
                            if (dsTemp[i]["KIEU_BDDC"] == "3")
                            {
                                // if (DateTime.TryParseExact(dsTemp.Tables[0].Rows[i]["thang_bddc"].ToString(), "yyyyMM", culture, System.Globalization.DateTimeStyles.None, out tmp))
                                // {
                                //     dr.THANG_BDDC = int.Parse(dsTemp.Tables[0].Rows[i]["thang_bddc"].ToString());
                                //     dr.THANG_KTDC = int.Parse(tmp.AddMonths(ithang_huongkm).ToString("yyyyMM"));
                                // }
                            }
                            //longdx: bổ sung thông tin tỷ lệ km
                            dr.TYLE_KMLD = dsTemp[i]["TYLE_KMLD"];
                            dr.TYLE_SD = dsTemp[i]["TYLE_SD"];
                            dr.TYLE_TB = dsTemp[i]["TYLE_TB"];
                            dr.HDTB_ID = this.inputValue.hdtb_id;
                            dr.CHITIETKM_ID = dsTemp[i]["CHITIETKM_ID"];
                            dr.KHUYENMAI_ID = dsTemp[i]["KHUYENMAI_ID"];
                            dr.KIEU_YC = dsTemp[i]["KIEU_YC"];
                            dsKM_HDTB.push(dr);


                        }
                    }
                    else
                    {
                        this.modelCkeckBox.chkKhuyenMai = false;
                        dsKM_HDTB = []
                    }

                    this.inputValue.ds_tien_khoanmuc = await this.LAY_TIENHD_KM_THEO_HDTB(this.inputValue.hdtb_id);
                    for (let i = 0; i < ds_tien_khoanmuc.length; i++)
                    {
                        if (ds_tien_khoanmuc[i]["KHOANMUCTT_ID"] == Enum.KHOANMUC_TT.KMTT_CHUYENDICH)
                        {
                            this.inputValue.dtienld = ds_tien_khoanmuc[i]["TIEN"];
                            this.inputValue.dvatld = ds_tien_khoanmuc[i]["VAT"];
                        }
                        if (ds_tien_khoanmuc[i]["KHOANMUCTT_ID"] == Enum.KHOANMUC_TT.KMTT_KM_LAPDAT)
                        {
                            this.inputValue.dtienkmld = ds_tien_khoanmuc[i]["TIEN"];
                            this.inputValue.dvatkmld = ds_tien_khoanmuc[i]["VAT"];
                        }
                        if (ds_tien_khoanmuc[i]["KHOANMUCTT_ID"] == KHOANMUC_TT.KMTT_DICHVU)
                        {
                            this.inputValue.dtien_dv = ds_tien_khoanmuc[i]["TIEN"];
                            this.inputValue.dvat_dv = ds_tien_khoanmuc[i]["VAT"];
                        }

                    }
                    await this.LayTongTien_HDTB();

                    //

                    //Lấy ra các đơn vị được chọn
                    {
                        let data = {
                            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                            P_HDTB_ID: this.inputValue.hdtb_id,
                            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LAY_DANHSACH_DONVI_CHON'
                        }
                        let dtTemp = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
                        if (dtTemp.data.length > 0)
                        {
                            // temp rem
                            // for (let i = 0; i < dtControl.Rows.Count; i++)
                            // {
                            //     DataRow[] dr = dtTemp.Select("loaidv_id = " + Convert.ToInt32(cbo[i].Tag));
                            //     if (dr.Length > 0)
                            //     {
                            //         cbo[i].SelectedValue = dr[0]["donvi_id"];
                            //     }
                            // }
                        }
                    }
                }
            }
            catch (exp)
            {
                this.$root.$toast.error(' '+exp.response.data.message_detail)
            }
        },
        //Thangph: Copy ham tu Form Lap dat moi -Ngay 08/06/2015
        LayTongTien_HDTB: async function()
        {
            try
            {
                let dtongtien = 0;
                let dtongvat = 0;
                for (let i = 0; i < this.inputValue.ds_tien_khoanmuc.length; i++)
                {
                    if (this.inputValue.ds_tien_khoanmuc[i]["KHOANMUCTT_ID"] == Enum.KHOANMUC_TT.KMTT_KM_LAPDAT)
                    {
                        dtongtien += Math.Abs(this.inputValue.ds_tien_khoanmuc[i]["TIEN"]) * -1;
                        dtongvat += Math.Abs(this.inputValue.ds_tien_khoanmuc[i]["VAT"]) * -1;
                    }
                    else
                    {
                        dtongtien += this.inputValue.ds_tien_khoanmuc[i]["TIEN"];
                        dtongvat += this.inputValue.ds_tien_khoanmuc[i]["VAT"];
                    }

                }
                this.modelInput.txtTongTien = currency(dtongtien)//.ToString(Format.FM_TIEN_VND);
                this.modelInput.txtTongVat = currency(dtongvat)//.ToString(Format.FM_TIEN_VND);
                this.lblTongTien = currency(dtongtien + dtongvat)//.ToString(Format.FM_TIEN_VND);
            }
            catch (ex)
            {
               this.$root.$toast.error(' '+ex.response.data.message_detail)
            }

        },
        //#region LAY_TIENHD_KM_THEO_HDTB
        LAY_TIENHD_KM_THEO_HDTB: async function(vhdtb_id)
        {
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                vhdtb_id: vhdtb_id,
                PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_TIENHD_KM_THEO_HDTB'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data
        },
        //#endregion
        //#region LAY_CHITIET_KM_THEO_HDTB_ID
        LAY_CHITIET_KM_THEO_HDTB_ID: async function(vhdtb_id, vloai_km)
        {
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                vhdtb_id: vhdtb_id,
                vloai_km: vloai_km,
                PROCEDURE_NAME: 'CSS.LAP_HOP_DONG.LAY_CHITIET_KM_THEO_HDTB_ID'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data
        },
        //#endregion
        //#region HT_DS_KhuyenMai_Combobox
        LAY_KHUYENMAI_HDTB: async function(loaitb_id, kieuld_id, phuongld_id, khoanmuctt_id, phold_id, apld_id, khuld_id,
            trangbi_id, doituong_id, tocdo_id, muccuoc_id, vloai_km){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                vloaitb_id: loaitb_id,
                vkieuld_id: kieuld_id,
                vphuongld_id: phuongld_id,
                vkhoanmuctt_id: khoanmuctt_id,
                vphold_id: phold_id,
                vapld_id: apld_id,
                vkhuld_id: khuld_id,
                vtrangbi_id: trangbi_id,
                vdoituong_id: doituong_id,
                vtocdo_id: tocdo_id,
                vmuccuoc_id: muccuoc_id,
                vloai_km: vloai_km,
                PROCEDURE_NAME: 'CSS.LAP_HOP_DONG.LAY_KHUYENMAI_HDTB'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data
        },
        
        HT_DS_KhuyenMai_Combobox: async function()
        {
            console.log("🚀 ~ file: index.vue ~ line 4744 ~ this.modelCbo.cboDichVuVT", this.modelCbo.cboDichVuVT)
            let itocdo_id = 0;
            let itrangbi_id = 0;//Default: KH tự trang bị
            let imuccuoc_id = 0;
            let iloaitb_id = 0;
            if (this.modelCbo.vcboKieuLD.length <= 0)
                return;
            if (this.modelCbo.cboDoituongTB == null || this.modelCbo.cboDoituongTB == -1)
                return;
            if (this.modelCbo.cboDichVuVT != null && !this.modelCbo.cboDichVuVT.includes("."))
            
            {
                this.modelInput.dichvuvt_id = this.modelCbo.cboDichVuVT
            }
            switch (this.modelInput.dichvuvt_id)
            {
                case Enum.DichVuVienThong.CO_DINH:
                    itocdo_id = 0;
                    break;
                case Enum.DichVuVienThong.IMS:
                case Enum.DichVuVienThong.IMS_DOITAC:
                    itocdo_id = 0;
                    break;
                case Enum.DichVuVienThong.ADSL:
                case Enum.DichVuVienThong.BRCD_DOITAC:
                    if (this.modelCbo.cboGoiCuocAdsl == null || this.modelCbo.cboGoiCuocAdsl.includes("."))
                    {
                        return;
                    }
                    itocdo_id = this.modelCbo.cboGoiCuocAdsl
                    break;
                case Enum.DichVuVienThong.MEGA_EYES:
                    if (this.modelCbo.cboGoiCuocAdsl == null || this.modelCbo.cboGoiCuocAdsl.includes("."))
                    {
                        return;
                    }
                    itocdo_id = this.modelCbo.cboGoiCuocAdsl;
                    break;
                case Enum.DichVuVienThong.MEGAWAN:
                case Enum.DichVuVienThong.METRONET:
                case Enum.DichVuVienThong.MGW_DOITAC:
                    if (this.modelCbo.cboTocDo == null || this.modelCbo.cboTocDo.includes("."))
                    {
                        return;
                    }
                    itocdo_id = this.modelCbo.cboTocDo;
                    break;
                case Enum.DichVuVienThong.TSL:
                case Enum.DichVuVienThong.TSL_DOITAC:
                    if (this.modelCbo.cboTocDoKenhTSL == null || this.modelCbo.cboTocDoKenhTSL.includes("."))
                    {
                        return;
                    }
                    itocdo_id = this.modelCbo.cboTocDoKenhTSL;
                    break;

                case Enum.DichVuVienThong.GPHONE:
                    itocdo_id = 0;
                    break;
                default:
                    itocdo_id = 0;
                    break;
            }

            if (this.modelCbo.cboMucCuoc != null && !this.modelCbo.cboMucCuoc.includes("."))
                imuccuoc_id = this.modelCbo.cboMucCuoc
            if (this.modelCbo.cboLoaihinhTB != null && !this.modelCbo.cboLoaihinhTB.includes("."))
                iloaitb_id = this.modelCbo.cboLoaihinhTB;
            this.dsKhuyenMai = await this.LAY_KHUYENMAI_HDTB(iloaitb_id, this.modelCbo.cboKieuLD, this.modelInput.phuongld_id, Enum.KHOANMUC_TT.KMTT_DATMOI, this.modelInput.phold_id, this.modelInput.apld_id, this.modelInput.khuld_id, itrangbi_id, this.modelCbo.cboDoituongTB, itocdo_id, imuccuoc_id, 0);
           this.modelCbo.vcboKhuyenMai = this.dsKhuyenMai;

            if ( this.modelCbo.vcboKhuyenMai.length == 0)
                this.modelCkeckBox.chkdisableKhuyenMai = false;
            else
                this.modelCkeckBox.chkdisableKhuyenMai = true;

            this.modelCkeckBox.chkKhuyenMai = false;

        },
        //#endregion
        lay_dvid_tramvt: async function(loaidv_id, kieudv_id){
            let data = {
                hdtb_id:this.inputValue.hdtb_id,
                loaidv_id:loaidv_id,
                kieudv_id:kieudv_id
            }
            await this.$root.context.post('/web-hopdong/chuyendich/lay_dvid_tramvt',data).then(
                res=> {
                    return res.data
                }
            ).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        lay_dvid_tramvt_danhba: async function(loaidv_id, kieudv_id){
            let data = {
                hdtb_id:this.inputValue.thuebao_id,
                loaidv_id:loaidv_id,
                kieudv_id:kieudv_id
            }
            await this.$root.context.post('/web-hopdong/chuyendich/lay_dvid_tramvt_danhba',data).then(
                res=> {
                    return res.data
                }
            ).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        //#region HienThiTTMoRong
        HienThiTTMoRongHD: async function(hdtb_id, dichvuvt_id)
        {
            this.modelCkeckBox.chkCongVan = false;
            this.modelInput.txtCongVan = "";
            this.modelDateTime.dtpNgayCV = DateTimeLib.toDateDisplay(new Date())
            this.inputValue.kt_kenhphu_ao = false;
            let ds = []
            switch (dichvuvt_id)
            {
                case Enum.DichVuVienThong.CO_DINH:
                    ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.IMS:
                case Enum.DichVuVienThong.IMS_DOITAC:
                    ds = await this.LAY_DS_HDTB_IMS_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.ADSL:
                case Enum.DichVuVienThong.BRCD_DOITAC:
                    ds = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.MEGA_EYES:
                    ds = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.MEGAWAN:
                case Enum.DichVuVienThong.METRONET:
                case Enum.DichVuVienThong.MGW_DOITAC:
                    ds = await this.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.TSL:
                case Enum.DichVuVienThong.TSL_DOITAC:
                    ds = await this.LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.GPHONE:
                    ds = await this.lay_ds_hdtb_GP_theo_hdtb_id(hdtb_id);
                    break;
                default:
                    ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
                    break;
            }
console.log("🚀 ~ file: index.vue ~ line 1873 ~ HienThiTTMoRongTB", ds)
            if (ds.data.length > 0)
            {
                switch (dichvuvt_id)
                {
                    case Enum.DichVuVienThong.CO_DINH:
                        this.modelInput.txtSoMetDay = ds.data[0]["CULY"];
                        this.modelInput.txtMaDoiCap_CD = ds.data[0]["MADOICAP"];
                        this.modelInput.txtMaTN_CD = ds.data[0]["MATB_TN"];
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiCD = ds.data[0]["TRANGBI_ID"];

                        if (ds.data[0]["SOTRUOT"] != null)
                        {
                            this.inputValue.kieu_thueso = Enum.THUESO.TRUOTSO;
                            this.inputValue.sothang_thueso = (ds.data[0]["TG_THUESO"]);
                            this.inputValue.noidung_thueso = ds.data[0]["SOTRUOT"];
                        }
                        if (ds.data[0]["NOIDUNG_TB"] != null)
                        {
                            this.inputValue.kieu_thueso = Enum.THUESO.THONGBAO;
                            this.inputValue.sothang_thueso = (ds.data[0]["TG_THUESO"]);
                            this.inputValue.noidung_thueso = ds.data[0]["NOIDUNG_TB"];
                        }
                        if (ds.data[0]["SOTRUOT"] == null &&
                            ds.data[0]["NOIDUNG_TB"] == null)
                        {
                            this.inputValue.kieu_thueso = 0;
                            this.inputValue.sothang_thueso = 0;
                            this.inputValue.noidung_thueso = "";
                        }
                        if (ds.data[0]["NE_ID"] != null)
                        {
                            this.inputValue.ne_id = (ds.data[0]["NE_ID"]);
                        }
                        this.inputValue.sone = ds.data[0]["NE"];

                        break;
                    case Enum.DichVuVienThong.IMS:
                    case Enum.DichVuVienThong.IMS_DOITAC:
                        this.modelInput.txtMATN_IMS = ds.data[0]["MATB_TN"];
                        this.modelInput.txtMaDoiCap_IMS = ds.data[0]["MADOICAP"];
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiIMS = ds.data[0]["TRANGBI_ID"];
                        break;
                    case Enum.DichVuVienThong.ADSL:
                    case Enum.DichVuVienThong.BRCD_DOITAC:
                        this.modelInput.txtMaTN_ADSL = ds.data[0]["MATB_TN"];
                        this.modelInput.txtSomayTN = ds.data[0]["SO_DT"];
                        this.inputValue.SoMayTN = txtMaTN_ADSL;
                        this.modelInput.txtEmailAdsl = ds.data[0]["EMAIL"];
                        if (ds.data[0]["KH_CD"] == "1")
                            this.modelCkeckBox.ckTuCaiADSL = true;
                        else
                            this.modelCkeckBox.ckTuCaiADSL = false;
                        this.inputValue.cboGoiCuocAdsl = ds.data[0]["TOCDO_ID"];
                        this.inputValue._tocdo_id = ds.data[0]["TOCDO_ID"];
                        this.inputValue.cboMucCuoc = ds.data[0]["MUCCUOC_ID"];
                        this.inputValue.cboLoaiTBAdsl = ds.data[0]["THIETBIDC_ID"];
                        this.modelInput.txtMaDoiCap_ADSL = ds.data[0]["MADOICAP"];
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiADSL = ds.data[0]["TRANGBI_ID"];
                        if (ds.data[0]["DOITAC_ID"] != null)
                        {
                            this.modelCkeckBox.chkDoiTac_ADSL = true;
                            this.inputValue.cboDoiTac_ADSL = ds.data[0]["DOITAC_ID"];
                            this.modelInput.txtCuocDT_Adsl = ds.data[0]["CUOC_DOITAC"];
                        }
                        else
                        {
                            this.modelCkeckBox.chkDoiTac_ADSL = false;
                            this.modelCbo.cboDoiTac_ADSL = null;
                            this.modelInput.txtCuocDT_Adsl = "0";
                        }
                        break;
                    case Enum.DichVuVienThong.MEGA_EYES:
                        this.modelInput.txtMaTN_ADSL = ds.data[0]["MATB_TN"];
                        this.modelInput.txtSomayTN = ds.data[0]["SO_DT"];
                        this.inputValue.SoMayTN = txtMaTN_ADSL;
                        this.modelInput.txtEmailAdsl = ds.data[0]["EMAIL"];
                        if (ds.data[0]["KH_CD"] == "1")
                            this.modelCkeckBox.ckTuCaiADSL = true;
                        else
                            this.modelCkeckBox.ckTuCaiADSL = false;
                        this.inputValue.cboGoiCuocAdsl = ds.data[0]["TOCDO_ID"];
                        this.inputValue._tocdo_id = ds.data[0]["TOCDO_ID"];
                        this.inputValue.cboMucCuoc = ds.data[0]["MUCCUOC_ID"];
                        this.inputValue.cboLoaiTBAdsl = ds.data[0]["THIETBIDC_ID"];
                        this.modelInput.txtMaDoiCap_ADSL = ds.data[0]["MADOICAP"];
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiADSL = ds.data[0]["TRANGBI_ID"];

                        break;
                    case Enum.DichVuVienThong.MEGAWAN:
                    case Enum.DichVuVienThong.METRONET:
                    case Enum.DichVuVienThong.MGW_DOITAC:
                        this.inputValue.cboTocDo = ds.data[0]["TOCDO_ID"];
                        this.inputValue._tocdo_id = ds.data[0]["TOCDO_ID"];
                        this.inputValue.cboLoaiModem = ds.data[0]["LOAIMD_ID"];
                        this.inputValue.cboLoaiCap = ds.data[0]["LOAICAP_ID"];
                        this.inputValue.vloaikenh_id = ds.data[0]["LOAIKENH_ID"];

                        if (ds.data[0]["KP_AO"].trim() == "1")
                            this.inputValue.kt_kenhphu_ao = true;
                        else
                            this.inputValue.kt_kenhphu_ao = false;

                        if (ds.data[0]["TOCDO_PIR_ID"] != "")
                        {
                            this.modelCkeckBox.chkTocDo_PIR = true;
                            this.inputValue.cboTocDo_PIR = ds.data[0]["TOCDO_PIR_ID"];
                        }
                        else
                        {
                            this.modelCkeckBox.chkTocDo_PIR = false;
                            this.inputValue.cboTocDo_PIR = ds.data[0]["TOCDO_ID"];
                        }

                        this.modelCkeckBox.chkCongVan = true;
                        if (ds.data[0]["MA_CV"] != "")
                        {
                            this.modelCkeckBox.chkCongVan = true;
                            this.modelInput.txtCongVan = ds.data[0]["MA_CV"];
                            this.modelDateTime.dtpNgayCV = DateTimeLib.toDateDisplay(ds.data[0]["NGAY_CV"])
                        }

                        this.inputValue.cboLoaiTBiWan = ds.data[0]["THIETBIDC_ID"];
                        this.modelInput.txtMaTN_Wan = ds.data[0]["MATB_TN"];
                        this.modelInput.txtMaDoiCap_Wan = ds.data[0]["MADOICAP"];

                        this.modelInput.txtSite_ID = ds.data[0]["SITE_ID"];
                        this.modelInput.txtMaHD_TTKD_MGWAN = ds.data[0]["MA_PL"];

                        //Hiếu bổ sung thêm một số thông tin megawan ngày 11/09/2010
                        this.modelInput.txtSoAoWan = ds.data[0]["MA_LT"];
                        this.inputValue.vhuong_kn = ds.data[0]["HUONG_KN"];
                        if (this.inputValue.vhuong_kn != null)
                            this.modelInput.txtHuongKNWan = await this.Lay_text_huongketnoi(this.inputValue.vhuong_kn);
                            
                        else
                            this.modelInput.txtHuongKNWan = "";
                        console.log("🚀 ~ file: index.vue ~ line 1632 ~ this.modelInput.txtHuongKNWan", this.modelInput.txtHuongKNWan)
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiWan = ds.data[0]["TRANGBI_ID"];
                        else
                            this.inputValue.cboTrangBiWan = "";
                        if (ds.data[0]["CHUQUAN_ID"] != null)
                            this.inputValue.cboChuQuanWan = ds.data[0]["CHUQUAN_ID"];
                        else
                            this.inputValue.cboChuQuanWan = "";

                        this.inputValue.cuoc_tc = ds.data[0]["CUOC_TC"];
                        this.inputValue.cuoc_tk = ds.data[0]["CUOC_TK"];

                        this.modelInput.txtCuocTK = ds.data[0]["CUOC_TK"];
                        this.modelInput.txtCuocTC = ds.data[0]["CUOC_TC"];
                        this.modelInput.txtCuocHTWan = ds.data[0]["CUOC_HT"];
                        this.modelInput.txtCuocTBiWan = ds.data[0]["CUOC_TBI"];
                        this.modelInput.txtMaBSS = ds.data[0]["MA_BSS"];
                        this.modelInput.txtMaERP = ds.data[0]["ID_ERP"];
                        if (ds.data[0]["DOITAC_ID"] != null)
                        {
                            this.modelCkeckBox.chkDoiTacWan = true;
                            this.inputValue.cboDoiTacWan = (ds.data[0]["DOITAC_ID"]);
                            if (ds.data[0]["CUOC_TCDT"] != null)
                                this.modelInput.txtCuocTC_DT =  currency(ds.data[0]["CUOC_TCDT"])//.ToString(Format.FM_TIEN_VND);
                            else
                                this.modelInput.txtCuocTC_DT = "0";
                            if (ds.data[0]["CUOC_TKDT"] != null)
                                this.modelInput.txtCuocTK_DT =  currency(ds.data[0]["CUOC_TKDT"])//.ToString(Format.FM_TIEN_VND);
                            else
                                this.modelInput.txtCuocTK_DT = "0";

                            this.modelInput.txtMaHD_DT = ds.data[0]["MAHD_DT"];
                        }
                        else
                        {
                            this.modelCkeckBox.chkDoiTacWan = false;
                            this.modelInput.txtCuocTC_DT = "0";
                            this.modelInput.txtCuocTK_DT = "0";
                            this.modelInput.txtMaHD_DT = "";
                        }
                        //                        
                        break;
                    case Enum.DichVuVienThong.TSL:
                    case Enum.DichVuVienThong.TSL_DOITAC:
                        this.inputValue.cboTocDoKenhTSL = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue._tocdo_id = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue.vloaikenh_id = (ds.data[0]["LOAIKENH_ID"]);
                        this.inputValue.cboTrangBi = (ds.data[0]["TRANGBI_ID"]);
                        this.inputValue.cboChuQuan = (ds.data[0]["CHUQUAN_ID"]);

                        this.modelInput.txtMaHD_TTKD_TSL = ds.data[0]["MA_PL"];

                        this.modelCkeckBox.chkCongVan = true;
                        if (ds.data[0]["MA_CV"] != null)
                        {
                            this.modelCkeckBox.chkCongVan = true;
                            this.modelInput.txtCongVan = ds.data[0]["MA_CV"];
                            this.modelDateTime.dtpNgayCV = DateTimeLib.toDateDisplay(ds.data[0]["NGAY_CV"])
                        }

                        this.modelInput.txtCuocTK_TSL = ds.data[0]["CUOC_TK"];
                        this.modelInput.txtCuocTC_TSL = ds.data[0]["CUOC_TC"];
                        this.modelInput.txtCuocHT_TSL = ds.data[0]["CUOC_HT"];
                        this.modelInput.txtCuocTBI_TSL = ds.data[0]["CUOC_TBI"];

                        this.modelInput.dc_daucuoi = ds.data[0]["DC_DAUCUOI"];

                        if (this.modelInput.dc_daucuoi == 1)
                        {
                            this.modelCkeckBox.chkDC_Dau = true;
                            // btnChonDCDau.Enabled = true;
                            // txtDiaChiDau.Enabled = true;
                            // cboTramVTDau.Enabled = true;
                        }
                        if (this.modelInput.dc_daucuoi == 2)
                        {
                            this.modelCkeckBox.chkDC_Cuoi = true;
                            // btnChonDCCuoi.Enabled = true;
                            // txtDiaChiCuoi.Enabled = true;
                            // cboTramVTCuoi.Enabled = true;
                        }
                        if (this.modelInput.dc_daucuoi == 3)
                        {
                            this.modelCkeckBox.chkDC_Dau = true;
                            // btnChonDCDau.Enabled = true;
                            // txtDiaChiDau.Enabled = true;
                            // cboTramVTDau.Enabled = true;

                            this.modelCkeckBox.chkDC_Cuoi = true;
                            // btnChonDCCuoi.Enabled = true;
                            // txtDiaChiCuoi.Enabled = true;
                            // cboTramVTCuoi.Enabled = true;
                        }

                        if (ds.data[0]["DOITAC_ID"] != null)
                        {
                            this.modelCkeckBox.chkDoiTac_TSL = true;
                            this.inputValue.cboDoiTac_TSL = (ds.data[0]["DOITAC_ID"]);
                            if (ds.data[0]["CUOC_TCDT"] != null)
                                this.modelInput.txtCuocTCDT_TSL =  currency(ds.data[0]["CUOC_TCDT"])//.ToString(Format.FM_TIEN_VND);
                            else
                                this.modelInput.txtCuocTCDT_TSL = "0";

                            if (ds.data[0]["CUOC_TKDT"] != null)
                                this.modelInput.txtCuocTKDT_TSL =  currency(ds.data[0]["CUOC_TKDT"])//.ToString(Format.FM_TIEN_VND);
                            else
                                this.modelInput.txtCuocTKDT_TSL = "0";

                            this.modelInput.txtMa_HDDT_TSL = ds.data[0]["MAHD_DT"];
                        }
                        else
                        {
                            this.modelCkeckBox.chkDoiTac_TSL = false;
                            this.modelInput.txtCuocTCDT_TSL = "0";
                            this.modelInput.txtCuocTKDT_TSL = "0";
                            this.modelInput.txtMa_HDDT_TSL = "";
                        }

                        this.modelInput.txtMaBSS = ds.data[0]["MA_BSS"];
                        this.modelInput.txtMaERP = ds.data[0]["ID_ERP"];
                        for (let i = 0; i < ds.length; i++)
                        {
                            if (ds.Tables[0].Rows[i]["DAUCUOI_ID"] == 1)//Nếu là điểm đầu
                            {
                                this.inputValue.diachidau_id = ds.Tables[0].Rows[i]["DIACHI_ID"];

                                if (ds.Tables[0].Rows[i]["KINHDO_LD"] != "")
                                    this.inputValue.Lng_DD = (ds.Tables[0].Rows[i]["KINHDO_LD"]);
                                else
                                    this.inputValue.Lng_DD = 0;

                                if (ds.Tables[0].Rows[i]["VIDO_LD"] != "")
                                    this.inputValue.Lat_DD = (ds.Tables[0].Rows[i]["VIDO_LD"]);
                                else
                                    this.inputValue.Lat_DD = 0;
                                let temp = await this.MAP_DONVI_DAU_ID_NEO(hdtb_id)
                                this.inputValue.donvi_dau_id = temp.data[0].DONVI_DAU_ID
                                await this.HT_DonVi_loaidv_Combobox(this.inputValue.donvi_dau_id, Enum.LOAI_DV.TRAM_VT);
                                this.modelInput.txtDiaChiDau = this.ChuanHoaTen(ds.Tables[0].Rows[i]["DIACHI"]);
                                this.inputValue.ma_tb_dau = ds.Tables[0].Rows[i]["MA_TB_SUB"];
                                this.inputValue.cboLoaiTBTSL_Dau = ds.Tables[0].Rows[i]["THIETBIDC_ID"];
                                this.modelInput.txtSoAoTSL_Dau = malt_dau = ds.Tables[0].Rows[i]["MA_LT"];
                                //let str = "select * from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + hdtb_id + " and loaidv_id = " + Enum.LOAI_DV.TRAM_VT + " and kieudv_id = 1";
                                let dtTemp = await this.lay_dvid_tramvt(Enum.LOAI_DV.TRAM_VT, 1)
                                if (dtTemp.length > 0)
                                    this.inputValue.cboTramVTDau = dtTemp[0]["DONVI_ID"];
                                this.inputValue.cboLoaiCap_Dau = ds.Tables[0].Rows[i]["LOAICAP_ID"];
                            }
                            else
                            {
                                this.inputValue.diachicuoi_id = (ds.Tables[0].Rows[i]["DIACHI_ID"]);

                                if (ds.Tables[0].Rows[i]["KINHDO_LD"] != null)
                                    this.inputValue.Lng_DC = ds.Tables[0].Rows[i]["KINHDO_LD"];
                                else
                                    this.inputValue.Lng_DC = 0;

                                if (ds.Tables[0].Rows[i]["VIDO_LD"] != null)
                                    this.inputValue.Lat_DC = ds.Tables[0].Rows[i]["VIDO_LD"];
                                else
                                    this.inputValue.Lat_DC = 0;

                                let temp = await this.MAP_DONVI_DAU_ID_NEO(hdtb_id)
                                this.inputValue.donvi_cuoi_id = temp.data[0].DONVI_CUOI_ID
                                await this.HT_DonVi_loaidv_Combobox(this.inputValue.donvi_cuoi_id, Enum.LOAI_DV.TRAM_VT);
                                this.modelInput.txtDiaChiCuoi = this.ChuanHoaTen(ds.Tables[0].Rows[i]["DIACHI"]);
                                this.inputValue.ma_tb_cuoi = ds.Tables[0].Rows[i]["MA_TB_SUB"];
                                this.inputValue.cboLoaiTBTSL_Cuoi = (ds.Tables[0].Rows[i]["THIETBIDC_ID"]);
                                this.modelInput.txtSoAoTSL_Cuoi = malt_cuoi = ds.Tables[0].Rows[i]["MA_LT"];
                                //let str = "select * from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + hdtb_id + " and loaidv_id = " + Enum.LOAI_DV.TRAM_VT + " and kieudv_id = 2";
                                let dtTemp = await this.lay_dvid_tramvt(Enum.LOAI_DV.TRAM_VT, 2)
                                if (dtTemp.length > 0)
                                    this.inputValue.cboTramVTCuoi = dtTemp[0]["DONVI_ID"];
                                this.inputValue.cboLoaiCap_Cuoi = ds.Tables[0].Rows[i]["LOAICAP_ID"];
                            }
                        }
                        await this.HienThiTTDiaChi_TSL();
                        break;
                    case Enum.DichVuVienThong.GPHONE:
                        this.modelInput.txtSoSimGphone = ds.data[0]["SIMCARD"];
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiGP = ds.data[0]["TRANGBI_ID"];
                        break;
                    default:
                        break;
                }
            }
        },
        LAY_THONGTIN_DIACHI: async function(diachidau_id){
             let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_DIACHI_ID: diachidau_id,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LAY_THONGTIN_DIACHI'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data
        },
        //#region HienThiTTDiaChi_TSL
        HienThiTTDiaChi_TSL: async function()
        {
            let ds = []
            ds = await this.LAY_THONGTIN_DIACHI(this.inputValue.diachidau_id);
            if (ds.length > 0)
            {
                this.inputValue.diachidau_id = ds[0]["DIACHI_ID"];
                this.inputValue.tinhdau_id = ds[0]["TINH_ID"];
                this.inputValue.quandau_id = ds[0]["QUAN_ID"];
                this.inputValue.phuongdau_id = ds[0]["PHUONG_ID"];
                if (ds[0]["PHO_ID"] != null)
                    this.inputValue.phodau_id = ds[0]["PHO_ID"];
                else
                    this.inputValue.phodau_id = 0;
                if (ds[0]["AP_ID"] != null)
                    this.inputValue.apdau_id = (ds[0]["AP_ID"]);
                else
                    this.inputValue.apdau_id = 0;

                if (ds[0]["KHU_ID"] != null)
                    this.inputValue.khudau_id = (ds[0]["KHU_ID"]);
                else
                    this.inputValue.khudau_id = 0;
                this.inputValue.sonhadau = CommonFunction.ChuanHoaTen(ds[0]["SONHA"]);
            }

            ds = await this.LAY_THONGTIN_DIACHI(this.inputValue.diachicuoi_id);
            if (ds.length > 0)
            {
                this.inputValue.diachicuoi_id = ds[0]["DIACHI_ID"];
                this.inputValue.tinhcuoi_id = ds[0]["TINH_ID"];
                this.inputValue.quancuoi_id = ds[0]["QUAN_ID"];
                // int.TryParse(ds[0]["quan_id"], out quancuoi_id);
                this.inputValue.phuongcuoi_id = ds[0]["PHUONG_ID"];
                if (ds[0]["PHO_ID"] != null)
                    this.inputValue.phocuoi_id = ds[0]["PHO_ID"];
                else
                    this.inputValue.phocuoi_id = 0;
                if (ds[0]["AP_ID"] != null)
                    this.inputValue.apcuoi_id = ds[0]["AP_ID"];
                else
                    this.inputValue.apcuoi_id = 0;

                if (ds[0]["KHU_ID"] != null)
                    this.inputValue.khucuoi_id = ds[0]["KHU_ID"];
                else
                    this.inputValue.khucuoi_id = 0;
                this.inputValue.sonhacuoi = this.ChuanHoaTen(ds[0]["SONHA"]);
            }
        },
        //#endregion
        Lay_text_huongketnoi: async function(vhuong_kn) {
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_HUONG_KN: vhuong_kn,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LAY_TEXT_HUONGKETNOI'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            if (res.data.length < 0)
                return "";
            else
                return res.data[0]["HUONG_KN"];
        },
        FN_LAY_DS_ISDN_THEO_THUEBAO_ID: async function(thuebao_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                vthuebao_id: thuebao_id,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_ISDN_THEO_THUEBAO_ID'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data
        },
        LAY_DS_ISDN_THEO_THUEBAO_ID: async function(thuebao_id){

            try
            {
                this.inputValue.isdn_sochinh = false;
                this.inputValue.matb_isdn_chinh = "";
                this.ds_isdn = await this.FN_LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id);
                for (let i = 0; i < ds_isdn.length; i++)
                {
                    if (Convert.ToInt64(ds_isdn[i]["THUEBAO_ID"]) == thuebao_id)
                    {
                        if (ds_isdn[i]["THUEBAO_CHA_ID"] == null)
                            this.inputValue.isdn_sochinh = true;
                    }
                    if (ds_isdn[i]["THUEBAO_CHA_ID"] == null)
                    {
                        matb_isdn_chinh = ds_isdn[i]["MA_TB"];
                    }

                }

            }
            catch (ex)
            {
                this.$root.$toast.error("Có lỗi:  " +ex.response.data.message_detail)
            }
        },
        HienThiTTMoRongTB: async function(thuebao_id, dichvuvt_id)
        {
           

            this.modelCkeckBox.chkCongVan = false;
            this.modelInput.txtCongVan = "";
            this.modelDateTime.dtpNgayCV = DateTimeLib.toDateDisplay(new Date())
            this.inputValue.kt_kenhphu_ao = false;
            let ds = []
            switch (dichvuvt_id)
            {
                case Enum.DichVuVienThong.CO_DINH:
                    ds = await this.LAY_DS_DB_CD_THEO_TBID(thuebao_id);
                    break;
                case Enum.DichVuVienThong.IMS:
                case Enum.DichVuVienThong.IMS_DOITAC:
                    ds = await this.LAY_DS_DB_IMS_THEO_TBID(thuebao_id);
                    break;
                case Enum.DichVuVienThong.ADSL:
                case Enum.DichVuVienThong.BRCD_DOITAC:
                    ds = await this.LAY_DS_DB_ADSL_THEO_TBID(thuebao_id);
                    break;
                case Enum.DichVuVienThong.MEGA_EYES:
                    ds = await this.LAY_DS_DB_ADSL_THEO_TBID(thuebao_id);
                    break;
                case Enum.DichVuVienThong.MEGAWAN:
                case Enum.DichVuVienThong.METRONET:
                case Enum.DichVuVienThong.MGW_DOITAC:
                    ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID(thuebao_id);
                    break;
                case Enum.DichVuVienThong.TSL:
                case Enum.DichVuVienThong.TSL_DOITAC:
                    ds = await this.LAY_DS_DB_TSL_THEO_TBID(thuebao_id);
                    break;
                case Enum.DichVuVienThong.GPHONE:
                    ds = await this.LAY_DS_DB_GP_THEO_TBID(thuebao_id);
                    break;
                default:
                    ds = await this.LAY_DS_DB_CD_THEO_TBID(thuebao_id);
                    
                    break;
            }
            console.log("🚀 ~ file: index.vue ~ line 1873 ~ HienThiTTMoRongTB", ds)
            if (ds.data.length > 0)
            {
                switch (dichvuvt_id)
                {
                    case Enum.DichVuVienThong.CO_DINH:
                        this.modelInput.txtMaTN_CD = '';//Reset matb_tn

                        this.modelInput.txtSoMetDay = ds.data[0]["CULY"];
                        this.modelInput.txtMaDoiCap_CD = ds.data[0]["MADOICAP"];
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiCD = ds.data[0]["TRANGBI_ID"];
                        if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CQ
                            || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CQ
                            || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_2M || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_THUONG
                            || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_TUONGTU)
                        {
                            await this.LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id);
                        }
                        if ((ds.data[0]["NE_ID"]) != null)
                            this.inputValue.ne_id = (ds.data[0]["NE_ID"]);
                        this.inputValue.sone = ds.data[0]["NE"];
                        break;
                    case Enum.DichVuVienThong.IMS:
                    case Enum.DichVuVienThong.IMS_DOITAC:
                        this.modelInput.txtMATN_IMS = string.Empty;//Reset matb_tn

                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiIMS = ds.data[0]["TRANGBI_ID"];
                        this.modelInput.txtMaDoiCap_IMS = ds.data[0]["MADOICAP"];
                        break;
                    case Enum.DichVuVienThong.ADSL:
                    case Enum.DichVuVienThong.BRCD_DOITAC:
                        this.modelInput.txtMaTN_ADSL = string.Empty;//Reset matb_tn

                        this.modelInput.txtEmailAdsl = ds.data[0]["EMAIL"];
                        this.inputValue.cboGoiCuocAdsl = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue._tocdo_id = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue.cboMucCuoc = (ds.data[0]["MUCCUOC_ID"]);
                        this.modelInput.txtMaDoiCap_ADSL = ds.data[0]["MADOICAP"];
                        if (ds.data[0]["THIETBIDC_ID"] != null)
                            this.inputValue.cboLoaiTBAdsl = (ds.data[0]["THIETBIDC_ID"]);
                        else
                            this.inputValue.cboLoaiTBAdsl = "";
                        if ((ds.data[0]["TRANGBI_ID"]) != null)
                            this.inputValue.cboTrangBiADSL = ds.data[0]["TRANGBI_ID"];
                        if ((ds.data[0]["DOITAC_ID"]) != null)
                        {
                            this.modelCkeckBox.chkDoiTac_ADSL = true;
                            this.modelCbo.cboDoiTac_ADSL = ds.data[0]["DOITAC_ID"];
                        }
                        else
                        {
                            this.modelCkeckBox.chkDoiTac_ADSL = false;
                            this.modelCbo.cboDoiTac_ADSL = null;
                            this.modelInput.txtCuocDT_Adsl = "0";
                        }

                        break;
                    case Enum.DichVuVienThong.MEGA_EYES:
                        this.modelInput.txtEmailAdsl = ds.data[0]["EMAIL"];
                        this.inputValue.cboGoiCuocAdsl = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue._tocdo_id = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue.cboMucCuoc = (ds.data[0]["MUCCUOC_ID"]);
                        this.modelInput.txtMaDoiCap_ADSL = ds.data[0]["MADOICAP"];
                        if (ds.data[0]["THIETBIDC_ID"] != null)
                            this.inputValue.cboLoaiTBAdsl = (ds.data[0]["THIETBIDC_ID"]);
                        else
                            this.inputValue.cboLoaiTBAdsl = "";
                        if ((ds.data[0]["TRANGBI_ID"]) != null)
                            this.inputValue.cboTrangBiADSL = ds.data[0]["TRANGBI_ID"];
                        break;
                    case Enum.DichVuVienThong.MEGAWAN:
                    case Enum.DichVuVienThong.METRONET:
                    case Enum.DichVuVienThong.MGW_DOITAC:
                        this.modelInput.txtMaTN_Wan = string.Empty;//Reset matb_tn

                        this.inputValue.cboTocDo = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue._tocdo_id = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue.cboLoaiModem = (ds.data[0]["LOAIMD_ID"]);
                        this.inputValue.cboLoaiCap = (ds.data[0]["LOAICAP_ID"]);
                        this.inputValue.vloaikenh_id = (ds.data[0]["LOAIKENH_ID"]);

                        if (ds.data[0]["KP_AO"].trim() == "1")
                            this.inputValue.kt_kenhphu_ao = true;
                        else
                            this.inputValue.kt_kenhphu_ao = false;

                        if (ds.data[0]["TOCDO_PIR_ID"] != "")
                        {
                            this.modelCkeckBox.chkTocDo_PIR = true;
                            this.inputValue.cboTocDo_PIR = ds.data[0]["TOCDO_PIR_ID"];
                        }
                        else
                        {
                            this.modelCkeckBox.chkTocDo_PIR = false;
                            this.inputValue.cboTocDo_PIR = (ds.data[0]["TOCDO_ID"]);
                        }

                        this.modelInput.txtSite_ID = ds.data[0]["SITE_ID"];

                        this.modelCkeckBox.chkCongVan = true;
                        this.modelDateTime.dtpNgayCV = DateTimeLib.toDateDisplay(new Date())

                        //Hiếu bổ sung thêm một số thông tin megawan ngày 11/09/2010
                        this.modelInput.txtSoAoWan = ds.data[0]["MA_LT"];
                        this.inputValue.vhuong_kn = ds.data[0]["HUONG_KN"];
                        if (this.inputValue.vhuong_kn != null)
                            this.modelInput.txtHuongKNWan = await this.Lay_text_huongketnoi(this.inputValue.vhuong_kn);
                            
                        else
                            this.modelInput.txtHuongKNWan = "";
                        console.log("🚀 ~ file: index.vue ~ line 1984 ~ this.modelInput.txtHuongKNWan", this.modelInput.txtHuongKNWan)
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiWan = ds.data[0]["TRANGBI_ID"];
                        else
                            this.inputValue.cboTrangBiWan = "";
                        if (ds.data[0]["CHUQUAN_ID"] != null)
                            this.inputValue.cboChuQuanWan = ds.data[0]["CHUQUAN_ID"];
                        else
                            this.inputValue.cboChuQuanWan = "";

                        this.modelInput.txtMaDoiCap_Wan = ds.data[0]["MADOICAP"];
                        if (ds.data[0]["THIETBIDC_ID"] != null)
                            this.inputValue.cboLoaiTBiWan = (ds.data[0]["THIETBIDC_ID"]);
                        if ((ds.data[0]["MUCUOCTB_ID"]) != null)
                            this.inputValue.cboMucCuocTB = ds.data[0]["MUCUOCTB_ID"];
                        this.inputValue.cuoc_tc = (ds.data[0]["CUOC_TC"]);
                        this.inputValue.cuoc_tk = (ds.data[0]["CUOC_TK"]);

                        this.modelInput.txtCuocTK = ds.data[0]["CUOC_TK"];
                        this.modelInput.txtCuocTC = ds.data[0]["CUOC_TC"];
                        this.modelInput.txtCuocHTWan = ds.data[0]["CUOC_HT"];
                        this.modelInput.txtCuocTBiWan = ds.data[0]["CUOC_TBI"];

                        this.modelInput.txtMaBSS = ds.data[0]["MA_BSS"];
                        this.modelInput.txtMaERP = ds.data[0]["ID_ERP"];

                        if (ds.data[0]["DOITAC_ID"] != null)
                        {
                            this.modelCkeckBox.chkDoiTacWan = true;
                            this.inputValue.cboDoiTacWan = (ds.data[0]["DOITAC_ID"]);
                            if (ds.data[0]["CUOC_TCDT"] != null)
                                this.modelInput.txtCuocTC_DT = (ds.data[0]["CUOC_TCDT"])//.ToString(Format.FM_TIEN_VND);
                            else
                                this.modelInput.txtCuocTC_DT = "0";
                            if (ds.data[0]["CUOC_TKDT"] != null)
                                this.modelInput.txtCuocTK_DT = (ds.data[0]["CUOC_TKDT"])//.ToString(Format.FM_TIEN_VND);
                            else
                                this.modelInput.txtCuocTK_DT = "0";

                            this.modelInput.txtMaHD_DT = ds.data[0]["MAHD_DT"];
                        }
                        else
                        {
                            this.modelCkeckBox.chkDoiTacWan = false;
                            this.modelInput.txtCuocTC_DT = "0";
                            this.modelInput.txtCuocTK_DT = "0";
                            this.modelInput.txtMaHD_DT = "";
                        }
                        //
                        break;
                    case Enum.DichVuVienThong.TSL:
                    case Enum.DichVuVienThong.TSL_DOITAC:
                        this.inputValue.cboTocDoKenhTSL = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue._tocdo_id = (ds.data[0]["TOCDO_ID"]);
                        this.inputValue.vloaikenh_id = (ds.data[0]["LOAIKENH_ID"]);
                        this.inputValue.cboTrangBi = (ds.data[0]["TRANGBI_ID"]);
                        this.inputValue.cboChuQuan = (ds.data[0]["CHUQUAN_ID"]);

                        this.modelInput.txtMaHD_TTKD_TSL = ds.data[0]["MA_PL"];
                        this.modelCkeckBox.chkCongVan
                        this.modelDateTime.dtpNgayCV = DateTimeLib.toDateDisplay(new Date())

                        this.modelInput.txtCuocTK_TSL = ds.data[0]["CUOC_TK"];
                        this.modelInput.txtCuocTC_TSL = ds.data[0]["CUOC_TC"];
                        this.modelInput.txtCuocHT_TSL = ds.data[0]["CUOC_HT"];
                        this.modelInput.txtCuocTBI_TSL = ds.data[0]["CUOC_TBI"];

                        if (ds.data[0]["DOITAC_ID"] != null)
                        {
                            this.modelCkeckBox.chkDoiTac_TSL = true;
                            this.inputValue.cboDoiTac_TSL = (ds.data[0]["DOITAC_ID"]);
                            if (ds.data[0]["CUOC_TCDT"] != null)
                                this.modelInput.txtCuocTCDT_TSL = (ds.data[0]["CUOC_TCDT"])//.ToString(Format.FM_TIEN_VND);
                            else
                                this.modelInput.txtCuocTCDT_TSL = "0";

                            if (ds.data[0]["CUOC_TKDT"] != null)
                                this.modelInput.txtCuocTKDT_TSL = (ds.data[0]["CUOC_TKDT"])//.ToString(Format.FM_TIEN_VND);
                            else
                                this.modelInput.txtCuocTKDT_TSL = "0";

                            this.modelInput.txtMa_HDDT_TSL = ds.data[0]["MAHD_DT"];
                        }
                        else
                        {
                            this.modelCkeckBox.chkDoiTac_TSL = false;
                            this.modelInput.txtCuocTCDT_TSL = "0";
                            this.modelInput.txtCuocTKDT_TSL = "0";
                            this.modelInput.txtMa_HDDT_TSL = "";
                        }
                        this.modelInput.txtMaBSS = ds.data[0]["MA_BSS"];
                        this.modelInput.txtMaERP = ds.data[0]["ID_ERP"];
                        for (let i = 0; i < ds.Tables[0].Rows.Count; i++)
                        {
                            if ((ds.Tables[0].Rows[i]["DAUCUOI_ID"]) == 1)//Nếu là điểm đầu
                            {
                                this.inputValue.diachidau_id = (ds.Tables[0].Rows[i]["DIACHI_ID"]);

                                if (ds.Tables[0].Rows[i]["KINHDO_LD"] != null)
                                    this.inputValue.Lng_DD = (ds.Tables[0].Rows[i]["KINHDO_LD"]);
                                else
                                    this.inputValue.Lng_DD = 0;

                                if (ds.Tables[0].Rows[i]["VIDO_LD"] != null)
                                    this.inputValue.Lat_DD = (ds.Tables[0].Rows[i]["VIDO_LD"]);
                                else
                                    this.inputValue.Lat_DD = 0;

                                this.inputValue.cboLoaiTBTSL_Dau = (ds.Tables[0].Rows[i]["THIETBIDC_ID"]);
                                this.modelInput.txtSoAoTSL_Dau = malt_dau = ds.Tables[0].Rows[i]["MA_LT"];
                                this.inputValue.cboLoaiCap_Dau = (ds.Tables[0].Rows[i]["LOAICAP_ID"]);
                            }
                            else
                            {
                                this.inputValue.diachicuoi_id =  (ds.Tables[0].Rows[i]["DIACHI_ID"]);

                                if (ds.Tables[0].Rows[i]["KINHDO_LD"] != null)
                                    this.inputValue.Lng_DC = (ds.Tables[0].Rows[i]["KINHDO_LD"]);
                                else
                                    this.inputValue.Lng_DC = 0;

                                if (ds.Tables[0].Rows[i]["VIDO_LD"] != null)
                                    this.inputValue.Lat_DC = (ds.Tables[0].Rows[i]["VIDO_LD"]);
                                else
                                    this.inputValue.Lat_DC = 0;

                                this.inputValue.cboLoaiTBTSL_Cuoi = ds.Tables[0].Rows[i]["THIETBIDC_ID"];
                                this.modelInput.txtSoAoTSL_Cuoi = malt_cuoi = ds.Tables[0].Rows[i]["MA_LT"];
                                this.inputValue.cboLoaiCap_Cuoi = ds.Tables[0].Rows[i]["LOAICAP_ID"];
                            }
                        }
                        await this.HienThiTTDiaChi_TSL();
                        for (let i = 0; i < ds.Tables[0].Rows.Count; i++)
                        {
                            if (ds.Tables[0].Rows[i]["DAUCUOI_ID"] == 1)//Nếu là điểm đầu
                            {
                                let temp = await this.MAP_DONVI_ID_DBTBAO_NEO(thuebao_id)
                                this.inputValue.donvi_dau_id = temp.data[0].DONVI_DAU_ID
                                await this.HT_DonVi_loaidv_Combobox( this.inputValue.donvi_dau_id, Enum.LOAI_DV.TRAM_VT);
                                this.modelInput.txtDiaChiDau = CommonFunction.ChuanHoaTen(ds.Tables[0].Rows[i]["DIACHI"]);
                                this.inputValue.ma_tb_dau = ds.Tables[0].Rows[i]["MA_TB_SUB"];

                                let dtTemp = await this.lay_dvid_tramvt_danhba(Enum.LOAI_DV.TRAM_VT, 1)
                                if (dtTemp.length > 0)
                                    this.inputValue.cboTramVTDau = dtTemp[0]["DONVI_ID"];
                            }
                            else
                            {
                                let temp = await this.MAP_DONVI_ID_DBTBAO_NEO(thuebao_id)
                                this.inputValue.donvi_cuoi_id = temp.data[0].DONVI_CUOI_ID
                                await this.HT_DonVi_loaidv_Combobox(this.inputValue.donvi_cuoi_id, Enum.LOAI_DV.TRAM_VT);
                                this.modelInput.txtDiaChiCuoi = this.ChuanHoaTen(ds.Tables[0].Rows[i]["DIACHI"]);
                                this.inputValue.ma_tb_cuoi = ds.Tables[0].Rows[i]["MA_TB_SUB"];

                                let dtTemp = await this.lay_dvid_tramvt_danhba(Enum.LOAI_DV.TRAM_VT, 2)
                                if (dtTemp.length > 0)
                                    this.inputValue.cboTramVTCuoi = dtTemp[0]["DONVI_ID"];
                            }
                        }
                        break;
                    case Enum.DichVuVienThong.GPHONE:
                        this.modelInput.txtSoSimGphone = ds.data[0]["SIMCARD"];
                        if (ds.data[0]["TRANGBI_ID"] != null)
                            this.inputValue.cboTrangBiGP = ds.data[0]["TRANGBI_ID"];
                        break;
                    default:
                        break;
                }
            }
        },
        LAY_BIENDONG_THEO_HDTB_ID: async function(hdtb_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdtb_id: hdtb_id,
                PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_BIENDONG_THEO_HDTB_ID'
            }
            let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return ds;
        },
        //#endregion
        //#region HienThiThongTinCu
        HienThiThongTinCu: async function()
        {
            let ds = await this.LAY_BIENDONG_THEO_HDTB_ID(this.inputValue.hdtb_id)

            if (ds.data.length > 0)
            {
                //Hiếu bổ sung thông tin mã HĐ,ngaylapHD cũ 25/08/2010
                this.modelInput.txtMaHDcu = ds.data[0]["MA_HD"];
                this.modelDateTime.dtpNgayLapHD = DateTimeLib.toDateDisplay(ds.data[0]["NGAYLAP_HD"])
                this.modelInput.txtMaTB = ds.data[0]["MA_TB"];
                if (this.modelInput.txtMaTB == this.modelInput.txtMaTBMoi)
                    this.modelInput.txtMaTBMoi = "";
            }
        },
        //#endregion
        //#region LAY_DS_SUDUNG_DVK
        FN_LAY_DS_SUDUNG_DVK: async function(db_id, loaiid)
        {
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                vdb_id: db_id,//db_id,
                vloaiid: loaiid,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_SUDUNG_DVK'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data
        },
        //#endregion
        //#region LAY_DS_SUDUNG_DVK
        LAY_DS_SUDUNG_DVK: async function(db_id, vloaiid)
        {
            let dset = null
            dset = await this.FN_LAY_DS_SUDUNG_DVK(db_id, vloaiid);
            console.log("🚀 ~ file: index.vue ~ line 1469 ~ dset", dset)

            //this.dsDK_DVK = new DANGKY_DVK_DATA();

            for (let i = 0; i < dset.length; i++)
            {
                let row = {}
                row.DVKHAC_ID = dset[i]["DVKHAC_ID"]
                row.KIEU_YC = 1;
                row.TTTH = Enum.TrangThaiCaiDat.MOI;
                row.HD_ID = this.inputValue.hdtb_id;
                row.NGAY_YC = this.modelDateTime.dtpNgayYC;
                row.LOAIID = vloaiid;
                this.dsDK_DVK.push(row);
                console.log("🚀 ~ file: index.vue ~ line 1482 ~ this.dsDK_DVK", this.dsDK_DVK)
            }

            let str;
            if (vloaiid == 1)
            {
                this.modelInput.lvDvkhac = ''

                for (let i = 0; i < this.dsDK_DVK.length; i++)
                {
                    let ds = await this.TTCT_DichVuKhac(this.dsDK_DVK[i]["DVKHAC_ID"]);
                    console.log("🚀 ~ file: index.vue ~ line 1494 ~ ds1", ds)
                    str = ds[0].TEN_DVKHAC + " (Đang SD)" + "\n";
                    //lvDvkhac.Items.Add(str);
                }
                this.modelInput.lvDvkhac = str
                console.log("🚀 ~ file: index.vue ~ line 1498 ~ this.modelInput.lvDvkhac", this.modelInput.lvDvkhac)
            }
            else
            {
                this.modelInput.lvDvkhac_TT = ''

                for (let i = 0; i < this.dsDK_DVK.length; i++)
                {
                    let ds = await this.TTCT_DichVuKhac(this.dsDK_DVK[i].DVKHAC_ID);
                    console.log("🚀 ~ file: index.vue ~ line 1508 ~ ds2", ds)
                    str = ds[0].TEN_DVKHAC + " (Đang SD)"  + "\n";
                }
                this.modelInput.lvDvkhac_TT = str
                console.log("🚀 ~ file: index.vue ~ line 1509 ~ this.modelInput.lvDvkhac_TT", this.modelInput.lvDvkhac_TT)
            }
        },
        TTCT_DichVuKhac: async function(dvkhac_id) {
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                DICHVUGT_ID: dvkhac_id,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.TTCT_DICHVUKHAC'
            }
            let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return ds.data
        },
        
        //#endregion
        TTCT_LoaiHinh_TB: async function(loaitb_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                LOAITB_ID: loaitb_id,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LOAIHINH_TB'
            }
            let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return ds.data
        },
        // //#region Hiển thị dịch vụ GT, khác
        // HT_DichVuGT: async function()
        // {
        //     let str = ''
        //     this.modelInput.lvDvgt = ''
        //     for (let i = 0; i < this.dsDK_DVGT.length; i++)
        //     {
        //         let data = {
        //             P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //             DICHVUGT_ID: this.dsDK_DVGT[i].DICHVUGT_ID,
        //             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.TTCT_DICHVUGT'
        //         }
        //         let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //         str = ds.data[0].TEN_DVGT
        //         if (this.dsDK_DVGT[i].TTTH == "1")
        //             str = str + " (Đã CĐ)" +"\n";
        //         else
        //             str = str + " (Chưa CĐ)"+"\n";
        //     }
           
        //     this.modelInput.lvDvgt = str
        // },
        // HT_DichVuKhac: async function()
        // {
        //     let str  = ''
        //     this.modelInput.lvDvkhac = ''
        //     for (let i = 0; i < this.dsDK_DVK.length; i++)
        //     {
        //         let data = {
        //             P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //             DICHVUGT_ID: this.dsDK_DVK[i].DVKHAC_ID,
        //             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.TTCT_DICHVUKHAC'
        //         }
        //         let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //          str = ds.data[0].TEN_DVKHAC + '\n';
        //     }
        //     this.modelInput.lvDvkhac = str
            
        // },
        // //#endregion
        // LAY_DS_DANGKY_DVGT: async function(hdtb_id){
        //     let data = {
        //         vphanvung_id: this.$root.token.getPhanVungID(),
        //         vhdtb_id: hdtb_id,//3431000,//hdtb_id,
        //         PROCEDURE_NAME: 'CSS.LAP_HOP_DONG.LAY_DS_DANGKY_DVGT'
        //     }
        //     let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return ds.data
        // },
        // LAY_DS_DANGKY_DVK: async function(hdtb_id, type){
        //     let data = {
        //         vphanvung_id: this.$root.token.getPhanVungID(),
        //         vhd_id: hdtb_id,//3650260,//hdtb_id,
        //         vloaiid: type,
        //         PROCEDURE_NAME: 'CSS.LAP_HOP_DONG.LAY_DS_DANGKY_DVK'
        //     }
        //     let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return ds.data
        // },
        // LAY_DS_MUA_TBI: async function(hdtb_id){
        //     let data = {
        //         vphanvung_id: this.$root.token.getPhanVungID(),
        //         in_hdtb: hdtb_id,
        //         PROCEDURE_NAME: 'CSS.LAP_HOP_DONG.LAY_DS_MUA_TBI'
        //     }
        //     let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return ds.data
        // },
        HienThiDanhSacHDTT: async function(ds){
            this.inputValue.lvwThanhToan = [];
            if (ds.length > 0)
            {
                for (let i = 0; i < ds.length; i++)
                {
                    let myRow = ds[i];
                    let lvItem = {};

                    lvItem.hdtt_id = myRow["HDTT_ID"].toString();
                    lvItem.ma_tt = myRow["MA_TT"].toString();
                    lvItem.ten_tt = this.ChuanHoaTen(myRow["TEN_TT"].toString());
                    lvItem.diachi_tt = this.ChuanHoaTen(myRow["DIACHI_TT"].toString());
                    lvItem.diachi_ct = this.ChuanHoaTen(myRow["DIACHI_CT"].toString());
                    this.inputValue.lvwThanhToan.push(lvItem);

                }

                // if (myDataTables.Rows.Count <= 1)
                //     tsbtnXoaTT.Enabled = false;
                // else
                //     tsbtnXoaTT.Enabled = true;
            }
        },
        // // //Khong dung toi ham nay
        HienThiDanhSacHDTB: async function(ds){
            let tongtienhd = 0;
            let tongtientb = 0;
            this.inputValue.lvwThueBao = [];
            let myDataTables = ds;
            console.log("🚀 ~ file: index.vue ~ line 5202 ~ HienThiDanhSacHDTB:function ~ myDataTables", myDataTables)
            
            if (ds.length > 0)
            {
                //Hiếu them code lay khachhang_id cua khach hang dang chuyen dịch-- Ngay 10/07/2010
                let ds_khcu = await this.LAY_BIENDONG_THEO_HDTB_ID(ds[0].HDTB_ID)
                if (ds_khcu.data.length > 0)
                {
                    this.inputValue.khach_hang_id = ds_khcu.data[0]["KHACHHANG_ID"].toString();
                }

                for (let i = 0; i < ds.length; i++)
                {
                    let myRow = ds[i];
                    let lvItem = {}
                    tongtientb = myRow["TONGTIEN"];
                    lvItem.ma_tt = myRow["MA_TT"].toString();
                    lvItem.hdtb_id =myRow["HDTB_ID"].toString();
                    lvItem.ma_tb = myRow["MA_TB"].toString();
                    lvItem.ten_tb = this.ChuanHoaTen(myRow["TEN_TB"].toString());
                    lvItem.diachi_ld = this.ChuanHoaTen(myRow["DIACHI_LD"].toString());
                    lvItem.tongtientb = myRow["TONGTIEN"];
                    lvItem.ten_dvvt = myRow["TEN_DVVT"].toString();
                    lvItem.manv_tc = myRow["MANV_TC"].toString()
                    tongtienhd = tongtienhd + tongtientb;
                    this.inputValue.lvwThueBao.push(lvItem);
                                        
                }
                // if (myDataTables.Rows.Count <= 1)
                // {
                //     tsbtnXoaTB.Enabled = false;
                // }
                // else
                //     tsbtnXoaTB.Enabled = true;
            }

            this.modelInput.txtTongTienHD = currency(tongtienhd)//.ToString(Format.FM_TIEN_VND);
        },
        HienThiTTDiaChi: async function(kieu){
            let data = {
            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            P_HDKH_ID: this.inputValue.hdkh_id,
            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LAY_DS_DIACHI_THEO_HDKH_ID'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            if (res.data.length > 0)
            {
                let ds = res.data
                if (kieu == 1)
                {
                   
                    this.inputValue.diachikh_id = ds[0].DIACHI_ID;
                    this.inputValue.tinhkh_id = ds[0].TINH_ID
                    this.inputValue.quankh_id = ds[0].QUAN_ID
                    this.inputValue.phuongkh_id = ds[0].PHUONG_ID
                    if (ds[0].PHO_ID != null)
                        this.inputValue.phokh_id = ds[0].PHO_ID
                    else
                        this.inputValue.phokh_id = 0;
                    ///TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
                    ///// HoangPKN : lấy thông tin ngõ : 16/05/2012
                    if (ds[0].AP_ID != null)
                        this.inputValue.apkh_id = ds[0].AP_ID
                    else
                        this.inputValue.apkh_id = 0;
                    this.inputValue.sonhakh = this.ChuanHoaTen(ds[0].SONHA);
                }

                if (kieu == 2)
                {
                    this.inputValue.diachitt_id = ds[0].DIACHI_ID
                    this.inputValue.tinhtt_id = ds[0].TINH_ID
                    this.inputValue.quantt_id = ds[0].QUAN_ID
                    this.inputValue.phuongtt_id = ds[0].PHUONG_ID
                    if (ds[0].PHO_ID != null)
                        this.inputValue.phott_id = ds[0].PHO_ID
                    else
                       this.inputValue.phott_id = 0;
                    ///TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
                    /// Hoang pkn : lấy thêm ngõ : 16/05/2012
                    if (ds[0].ap_id != null)
                        this.inputValue.aptt_id = ds[0].AP_ID
                    else
                        this.inputValue.aptt_id = 0;
                    this.inputValue.sonhatt = this.ChuanHoaTen(ds[0].SONHA)
                }

                if (kieu == 3)
                {
                    this.inputValue.diachitb_id = ds[0].DIACHI_ID
                    this.inputValue.tinhtb_id = ds[0].TINH_ID
                    this.inputValue.quantb_id = ds[0].QUAN_ID
                    this.inputValue.phuongtb_id = ds[0].PHUONG_ID
                    if (ds[0].PHO_ID != null)
                        this.inputValue.photb_id = ds[0].PHO_ID
                    else
                        this.inputValue.photb_id = 0;
                    ///TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
                    /// hoangpkn : 16/05/2012 : lấy thêm ngõ
                    if (ds[0].AP_ID != null)
                        this.inputValue.aptb_id = ds[0].AP_ID
                    else
                        this.inputValue.aptb_id = 0;
                    this.inputValue.sonhatb = this.ChuanHoaTen(ds[0].SONHA);
                    if (ds[0].includes("block") || ds[0].includes("BLOCK"))
                        this.inputValue.blocktb = ds[0]["BLOCK"].toString();
                    if (ds[0].includes("tang") || ds[0].includes("TANG"))
                        this.inputValue.tangtb = ds[0]["TANG"].toString();
                    if (ds[0].includes("phong") || ds[0].includes("PHONG"))
                        this.inputValue.sophongtb = ds[0]["PHONG"].toString();
                    if (ds[0].includes("mota") || ds[0].includes("MOTA"))
                        this.inputValue.motadctb = ds[0]["MOTA"].toString();

                    /// Tuấn thêm code lấy địa chỉ lắp đặt cho thuê bao -- Ngày 29/07/2010
                    if (ds[0].DIACHILD_ID != null)
                        this.inputValue.diachild_id = ds[0].DIACHILD_ID
                    else
                        this.inputValue.diachild_id = 0;

                    if (ds[0].TINHLD_ID != null)
                        this.inputValue.tinhld_id = ds[0].TINHLD_ID
                    else
                        this.inputValue.tinhld_id = 0;

                    if (ds[0].QUANLD_ID != null)
                        this.inputValue.quanld_id = ds[0].QUANLD_ID
                    else
                        this.inputValue.quanld_id = 0;

                    if (ds[0].PHUONGLD_ID != null)
                        this.inputValue.phuongld_id = ds[0].PHUONGLD_ID
                    else
                        this.inputValue.phuongld_id = 0;

                    if (ds[0].PHOLD_ID != null)
                        this.inputValue.phold_id = ds[0].PHOLD_ID
                    else
                        this.inputValue.phold_id = 0;

                    if (ds[0].APLD_ID != null )
                        this.inputValue.apld_id = ds[0].APLD_ID
                    else
                        this.inputValue.apld_id = 0;
                    this.inputValue.sonhald = this.ChuanHoaTen(ds[0].SONHA_LD);

                    if (ds[0].includes("block_ld") || ds[0].includes("BLOCK_LD"))
                        this.inputValue.block_ld = ds[0]["BLOCK_LD"].toString();
                    if (ds[0].includes("tang_ld") || ds[0].includes("TANG_LD"))
                        this.inputValue.tang_ld = ds[0]["TANG_LD"].toString();
                    if (ds[0].includes("phong_ld") || ds[0].includes("PHONG_LD"))
                        this.inputValue.phong_ld = ds[0]["PHONG_LD"].toString();
                    if (ds[0].includes("mota_ld") || ds[0].includes("MOTA_LD"))
                        this.inputValue.mota_ld = ds[0]["MOTA_LD"].toString();

                }
            }
        },
        LAY_DS_DB_GP_THEO_TBID: async function(thuebao_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_thuebao_id: thuebao_id,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_DB_GP_THEO_TBID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_DB_TSL_THEO_TBID: async function(thuebao_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_thuebao_id: thuebao_id,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_DB_TSL_THEO_TBID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_DB_MEGAWAN_THEO_TBID: async function(thuebao_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_thuebao_id: thuebao_id,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_DB_MEGAWAN_THEO_TBID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_DB_ADSL_THEO_TBID: async function(thuebao_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_thuebao_id: thuebao_id,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_DB_ADSL_THEO_TBID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_DB_IMS_THEO_TBID: async function(thuebao_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_thuebao_id: thuebao_id,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_DB_IMS_THEO_TBID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_DB_CD_THEO_TBID: async function(thuebao_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_thuebao_id: thuebao_id,
                PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_DS_DB_CD_THEO_TBID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_HDTB_CD_THEO_HDTB_ID: async function (hdtb_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdtb_id: hdtb_id,
                PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_CD_THEO_HDTB_ID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_HDTB_IMS_THEO_HDTB_ID: async function(hdtb_id)
        {
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdtb_id: hdtb_id,
                PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.Lay_ds_hdtb_ims_theo_hdtb_id'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        // LAY_DS_HDTB_DD_THEO_HDTB_ID: async function (hdtb_id){
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         in_hdtb_id: hdtb_id,
        //         PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_DD_THEO_HDTB_ID'
        //     }
        //     return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            
        // },
        LAY_DS_HDTB_TSL_THEO_HDTB_ID: async function(hdtb_id)
        {
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdtb_id: hdtb_id,
                PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_TSL_THEO_HDTB_ID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_HDTB_MGWAN_THEO_HDTB_ID: async function(hdtb_id)
        {
             let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdtb_id: hdtb_id,
                PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        LAY_DS_HDTB_ADSL_THEO_HDTB_ID: async function (hdtb_id){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdtb_id: hdtb_id,
                PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_ADSL_THEO_HDTB_ID'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        lay_ds_hdtb_GP_theo_hdtb_id: async function (hdtb_id){
            
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                in_hdtb_id: hdtb_id,
                in_tthd_id: this.inputValue.tthd_id,
                PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_THEO_HDKH_TTHD'
            }
            return await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        },
        // HienThiTTMoRong: async function(ma_tb, hdtb_id, dichvuvt_id)
        // {
        //     let ds = []
        //     switch (dichvuvt_id)
        //     {
        //         case Enum.DichVuVienThong.CO_DINH:
        //             ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
        //             break;
        //         case Enum.DichVuVienThong.DI_DONG:
        //             ds = await this.LAY_DS_HDTB_DD_THEO_HDTB_ID(hdtb_id);
        //             break;
        //         case Enum.DichVuVienThong.ADSL:
        //         case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
        //         case Enum.DichVuVienThong.DICHVU_CNTT:
        //         case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
        //         case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
        //             ds = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
        //             break;
        //         case Enum.DichVuVienThong.GPHONE:
        //             ds = await this.lay_ds_hdtb_GP_theo_hdtb_id(hdtb_id);
        //             break;
        //         default:
        //             ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
        //             break;
        //     }

        //     if (ds.data.length > 0)
        //     {
        //         switch (dichvuvt_id)
        //         {
        //             case Enum.DichVuVienThong.CO_DINH:
        //                 this.modelInput.txtMaTB = ma_tb;
        //                 break;
        //             case Enum.DichVuVienThong.DI_DONG:
        //                 this.modelInput.txtMaTB = ma_tb;
        //                 ///TuấnNA thêm code hiển thị các thông tin về di động
        //                 ///--Ngày 09/08/2010                        
        //                 this.modelCbo.cboGoiCuocDD = ds.data[0].goicuoc_id;
        //                 this.modelCbo.cboDTVinaPhone =  ds.data[0].doituong_id_neo;
        //                 break;
        //             case Enum.DichVuVienThong.ADSL:
        //             case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
        //             case Enum.DichVuVienThong.DICHVU_CNTT:
        //             case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
        //             case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
        //                 this.modelInput.txtMaTB = ma_tb;
        //                 this.modelInput.txtEmailAdsl = ds.data[0].EMAIL;
        //                 this.modelCbo.cboGoiCuocAdsl = ds.data[0].TOCDO_ID;
        //                 this.modelCbo.cboMucCuoc = ds.data[0].MUCCUOC_ID
        //                 break;
        //             case Enum.DichVuVienThong.GPHONE:
        //                 break;
        //             default:
        //                 this.modelInput.txtMaTB = ma_tb;
        //                 break;
        //         }
        //     }
        // },
        // //#endregion
        // //#region
        // KiemTraDL_ChuyenVaoDB: function()
        // {
        //     if (this.modelInput.txtMaKH == "")
        //     {
        //         this.$root.$toast.error('Hãy sinh mã khách hàng !')
        //         //txtMaKH.Focus();
        //          this.$refs['ma-kh'].focus()
        //         return false;
        //     }

        //     if (this.modelInput.txtMaTT == "")
        //     {
        //         this.$root.$toast.error('Hãy sinh mã thanh toán !')
        //         //txtMaTT.Focus();
        //         this.$refs['ma-tt'].focus()
        //         return false;
        //     }
        //     return true;
        // },
        // CAPNHAT_STATUS_HDTB: async function(hdtb_id, status){
        //     let data = {
        //             P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //             P_HDTB_ID: hdtb_id,
        //             P_HDTB_ID: status,
        //             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.CAPNHAT_STATUS_HDTB'
        //         }
        //         let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
                
        //         return rs.data[0].KETQUA
        // },
        // HoanThienMyTV: async function(){
        //     // CheckDataFacade checkdata = new CheckDataFacade();
        //     // VascService = new VASCService(tt_nd.user_vasc, tt_nd.pass_vasc, tt_nd.diemgiaodich_vasc);
        //     let Result = 0;
        //     let Name = this.ChuanHoa_XML(this.inputValue.ten_kh);//tên KH           
        //     let Birthday = this.inputValue.ngay_sn; // ngày sinh KH dt.Rows[k]["ngay_sn"]).ToString("dd/MM/yyyy")
        //     let Sex = "";//Giới tính : Nam --> 0, Nữ --> 1; không giới tính --> 0
        //     if (this.inputValue.gioitinh != "1")
        //     {
        //         Sex = "0";
        //     }
        //     else
        //     {
        //         Sex = "1";
        //     }
        //     let FirstAdd = this.ChuanHoa_XML(this.inputValue.diachi_kh); //địa chỉ KH
        //     let SecondAdd = this.ChuanHoa_XML(this.inputValue.diachi_ld);//Dịa chỉ Lđặt
        //     let IDNo = this.ChuanHoa_XML(this.inputValue.so_gt); //Số GT khách hàng
        //     // nếu điện thoại lhệ> 8 -->tellno = "" & cellno = điện thoại liên hệ  và ngược lại
        //     let TelNo = "";
        //     let CellNo = "";
        //     if (this.modelInput.txtTelKH.length > 8)///Di dong
        //     {
        //         TelNo = "";
        //         CellNo = this.modelInput.txtTelKH;
        //     }
        //     else
        //     {
        //         TelNo = this.modelInput.txtTelKH;
        //         CellNo = "";
        //     }

        //     let Email = this.ChuanHoa_XML(this.inputValue.email);//Email KH
        //     let YM = "";// trống
        //     let Website = "";//trống
        //     let FaxNo = this.inputValue.sofax;//số Fax KH                    
        //     let IPTVAccount = this.ChuanHoa_XML(this.inputValue.matb_Mytv);//Mã TB dsHDTB
        //     //string IPTVPW = "";// default lsn123 public                    
        //     Result = vasc.ChangeSubscriber(Name, Birthday, Sex, FirstAdd, SecondAdd, IDNo, TelNo,
        //                             CellNo, Email, YM, Website, FaxNo, IPTVAccount);
        //     if (Result == 0)
        //     {
        //         this.$root.toastSuccess('Thực hiện hoàn thiện thuê bao MyTV trên VASC thành công!')
        //         //Cập nhật Status ---> 5
        //         await this.CAPNHAT_STATUS_HDTB(this.inputValue.hdtb_id, Enum.TRANGTHAI_DONGBO.DONGBO_CM);
        //     }
        //     else
        //     {
        //         return;
        //     }
        // },
        // CO_THUEBAO_DIDONG: async function(thanhtoan_id){
        //     let kq = false;
        //     try {
        //         let data = {
        //             P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //             P_THANHTOAN_ID: thanhtoan_id,
        //             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LAY_THONGTIN_TBAO'
        //         }
        //         let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
                
        //         if (rs.data.length > 0)
        //             kq = true;
        //         else
        //             kq = false;
        //     } catch (error) {
        //         kq = false;
        //     }
        //     return kq;
        // },
        // LAY_DS_DBKH_THEO_KHID: async function(khachhang_id){
        //     let rs = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_dbkh_theo_khid?khachhang_id=', khachhang_id)
        //     return rs.data
        // },
        MAP_DB_CD_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                vthuebao_id: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_DB_CD_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_DONVI_DAU_ID_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_HDTB_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_DONVI_ID_HOPDONG_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_DONVI_ID_DBTBAO_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_HDTB_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_DONVI_ID_DBTBAO_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_ID_NHANVIEN_ID_NEO: async function(nhanvienid){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                NHANVIEN_ID: nhanvienid,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_NHANVIEN_ID_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_ID_TUYENTHU_ID_NEO: async function(tuyenthuid){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_TUYENTHU_ID: tuyenthuid,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_TUYENTHU_ID_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_KIEU_LD_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_KIEULD_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_KIEU_LD_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        // MAP_ID_PHO_ID_NEO: async function(){
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         phuongkh_id: this.inputValue.phuongkh_id,
        //         phokh_id: this.inputValue.phokh_id,
        //         PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_ID_PHO_ID_NEO'
        //     }
        //     let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return rs.data
        // },
        // MAP_ID_USER_NEO: async function(){
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         nguoidung_id: this.$root.token.getNguoiDungID(),
        //         PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_ID_USER_NEO'
        //     }
        //     let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return rs.data
        // },
        // MAP_MA_TT_NEO: async function(ma_tt){
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         ma_tt: ma_tt,
        //         PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_MA_TT_NEO'
        //     }
        //     let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return rs.data
        // },
        // MAP_MANV_VNP: async function(ma_tt){
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         ma_tt: ma_tt,
        //         PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_MANV_VNP'
        //     }
        //     let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return rs.data
        // },
        MAP_DB_ADSL_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_THUEBAO_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_DB_ADSL_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_PROFILEDSL_ID_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_TOCDO_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_PROFILEDSL_ID_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_THUEBAO_CHA_ID_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_THUEBAO_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_THUEBAO_CHA_ID_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_MUCCUOC_TB_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_MUCUOCTB_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_MUCCUOC_TB_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_LOAI_NH_ID_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_NGANHANG_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_LOAI_NH_ID_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        
        MAP_PHANLOAIKH_ID_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_PHANLOAIKH_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_PHANLOAIKH_ID_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        MAP_TEN_NHANVIEN_ID_NEO: async function(input){
            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                P_NHANVIEN_ID: input,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_TEN_NHANVIEN_ID_NEO'
            }
            let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return rs.data
        },
        // ts_hoanthien_hd_cn_kh: async function(psma_tuyen, psdangkytv, pshoct_id, psuserid, pstaikhoan, 
        //                                         pskh_lon, psghichu, pshuongtt_id1, psma_qhns, psnoicapgt1, 
        //                                         pstennv, psnganhang_id, pskyten, psuantt_id, psdangkydb, 
        //                                         psloaikh, psdonviql0,psonhatt_id, psnganhnghe, pssogt1, 
        //                                         pshott_id, psuutien, pslydo_anh, psngaycapgt, psdiachitt, 
        //                                         psuanct_id, tentat_ccbs, psgioitinh, psma_kh0, psmsthue, 
        //                                         pssogiayto, psngaycapgt1, psdiachict, pssodaidien, psdienthoailh,
        //                                         psmacq, pstentt, psdonviql, psnguoidaidien, pshuongtt_id, psonhact_id, 
        //                                         psemail, psmabc, psnoicapgt, psdiadiemtt, psten_tuyen, psma_kh, 
        //                                         psloaigtid, psnguoi_gt, psloaigtid1, psngaysinh, pskhrr, 
        //                                         pscoquan,pschuyenkhoan, psmanv) 
        // {
        //     let result = ccbsService.ts_hoanthien_hd_cn_kh(psma_tuyen, psdangkytv, pshoct_id, psuserid, pstaikhoan, 
        //                                         pskh_lon, psghichu, pshuongtt_id1, psma_qhns, psnoicapgt1, 
        //                                         pstennv, psnganhang_id, pskyten, psuantt_id, psdangkydb, 
        //                                         psloaikh, psdonviql0,psonhatt_id, psnganhnghe, pssogt1, 
        //                                         pshott_id, psuutien, pslydo_anh, psngaycapgt, psdiachitt, 
        //                                         psuanct_id, tentat_ccbs, psgioitinh, psma_kh0, psmsthue, 
        //                                         pssogiayto, psngaycapgt1, psdiachict, pssodaidien, psdienthoailh,
        //                                         psmacq, pstentt, psdonviql, psnguoidaidien, pshuongtt_id, psonhact_id, 
        //                                         psemail, psmabc, psnoicapgt, psdiadiemtt, psten_tuyen, psma_kh, 
        //                                         psloaigtid, psnguoi_gt, psloaigtid1, psngaysinh, pskhrr, 
        //                                         pscoquan,pschuyenkhoan, psmanv);
        //     return result.data;
        // },
        // LAY_TT_NHANVIEN_TC: async function(input){
        //     let data = {
        //         vphanvung_id: this.$root.token.getPhanVungID(),
        //         vmanv_tc: input,
        //         PROCEDURE_NAME: 'CSS.TRACUU_DANHBA.LAY_TT_NHANVIEN_TC'
        //     }
        //     let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return rs.data
        // },
        // UPDATE_HDKH_VINAPHONE: async function(){
        //     let status = false;
        //     try
        //     {
        //         this.inputValue.strerrorVinaphone = "";
        //         //XmlNode result;
                
        //         let rs1 = await this.MAP_ID_USER_NEO()                        
        //         let PSUSERID = this.ChuanHoa_XML(rs1[0].USER_NEO);
        //         if (PSUSERID == "-1")
        //         {
        //             this.$root.$toast.error('Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }

        //         let PSMAKHCU = this.modelInput.txtMaTT
        //         let rs = await this.MAP_MA_TT_NEO()
        //         let PSMA_KH = rs[0].MA_TT_NEO
        //         if (PSMA_KH == "-1")
        //         {
        //             PSMA_KH = this.modelInput.txtMaTT 
        //         }
        //         //Lấy PSMACQ
        //         let PSMACQ = "";
                
        //         //TuanNA: zem code cũ -- ngày 02.12.2011
        //         let PSCOQUAN = this.ChuanHoa_XML(this.modelInput.txtTenKH);
        //         //Tên thanh toán 
        //         let PSTENTT = this.ChuanHoa_XML(this.modelInput.txtTenTT);//(idsHDTT.Tables[0].Rows[i]["ten_tt"].ToString());
        //         let PSSODAIDIEN = this.modelInput.txtMaTBDD.Text;// idsHDTT.Tables[0].Rows[i]["matb_dd"].ToString();
        //         let PSNGUOIDAIDIEN = this.ChuanHoa_XML(this.dsDBKH.data[0].NGUOI_DD);//danh bạ
        //         let PSDIENTHOAILH = this.ChuanHoa_XML(this.dsDBKH.data[0].SO_DT);//danh bạ
        //         let PSPHAI = this.dsDBKH.data[0].GIOITINH;//Lấy dữ liệu từ danh bạ 
        //         let PSEMAIL = this.ChuanHoa_XML(this.dsDBKH.data[0].EMAIL);//danh bạ

        //         let PSNGAYSINH = "";
        //         // if (this.modelInput.txtNgaySN != "  /  /")
        //         if (this.modelInput.txtNgaySN != null)
        //         {
        //             //TuanNA: zem code cũ -- ngày 02.12.2011
        //             PSNGAYSINH = DateTimeLib.toDateDisplay(this.modelInput.txtNgaySN);//Convert.ToDateTime(txtNgaySN.Text).ToString("dd/MM/yyyy");//danh bạ
        //         }
        //         //TuanNA: zem code cũ -- ngày 02.12.2011
        //         let PSSOGIAYTO = this.ChuanHoa_XML(this.modelInput.txtSoGT);//danh bạ
        //         let PSNOICAPGT = this.ChuanHoa_XML(this.modelInput.txtNoiCap);//danh bạ
        //         let PSNGAYCAPGT = "";
        //         if (this.modelInput.txtNgayCap != null)
        //         {
        //             PSNGAYCAPGT = DateTimeLib.toDateDisplay(this.modelInput.txtNgayCap);//Convert.ToDateTime(txtNgayCap.Text).ToString("dd/MM/yyyy");//danh bạ
        //         }
        //         let PSSOGT1 = "";
        //         let PSNOICAPGT1 = "";
        //         let PSNGAYCAPGT1 = "";

        //         rs = await this.MAP_ID_QUAN_ID_NEO()
        //         let PSUANCT_ID = rs[0].QUAN_ID_NEO 
        //         if (PSUANCT_ID == "-1")
        //         {
        //             this.$root.$toast.error("Thông tin về Quận/Huyện  của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }

        //         rs = await this.MAP_ID_PHUONG_ID_NEO()
        //         let PSHUONGCT_ID = rs[0].PHUONG_ID_NEO
        //         if (PSHUONGCT_ID == "-1")
        //         {
        //             this.$root.$toast.error("Thông tin về Phường/Xã của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }
        //         rs = await this.MAP_ID_PHO_ID_NEO()
                
        //         let PSHOCT_ID = rs[0].PHO_ID_NEO
        //         if (PSHOCT_ID == "-1")
        //         {
        //             this.$root.$toast.error("Thông tin về Phố/Thôn/Xóm của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }
        //         let PSONHACT_ID = this.ChuanHoa_XML(this.inputValue.sonhakh);
        //         let PSDIACHICT = this.ChuanHoa_XML(this.modelInput.txtDiaChiKH);
        //         let PSMSTHUE = this.ChuanHoa_XML(this.modelInput.txtMST);
        //         let PSTAIKHOAN = this.ChuanHoa_XML(this.modelInput.txtSTK);
               
        //         rs = await this.MAP_ID_QUAN_ID_NEO()
        //         let PSUANTT_ID = rs[0].QUAN_ID_NEO 
        //         if (PSUANTT_ID == "-1")
        //         {
        //             this.$root.$toast.error("Thông tin về Quận/Huyện của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }
        //         rs = await this.MAP_ID_PHUONG_ID_NEO()
        //         let PSHUONGTT_ID = rs[0].PHUONG_ID_NEO
        //         if (PSHUONGTT_ID == "-1")
        //         {
        //             this.$root.$toast.error("Thông tin về Phường/Xã của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }

        //         rs = await this.MAP_ID_PHO_ID_NEO()
        //         let PSHOTT_ID = rs[0].PHO_ID_NEO
        //         if (PSHOTT_ID == "-1")
        //         {
        //             this.$root.$toast.error("Thông tin Phố/Thôn/Xóm của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }

        //         let PSONHATT_ID = this.ChuanHoa_XML(this.inputValue.sonhatt);
        //         let PSDIACHITT = this.ChuanHoa_XML(this.modelInput.txtDiaChiTT);

        //         let PSDANGKYTV = "0";
        //         let PSDANGKYDB = "1";//Bổ sung sau
        //         let PSKHRR = "0";
        //         let rs2 = await this.MAP_ID_USER_NEO()                        
        //         // chuaw thay dung let PSNGUOICN = this.ChuanHoa_XML(rs2[0].USER_NEO);
        //         //Chuaw thay dung let PSMAYCN = this.GetIPAndComputerName();
        //         rs2 = await this.MAP_MANV_VNP(this.modelInput.txtMaNVTC) 
        //         let PSMANV = rs2[0].MANV_VNP
        //         if (PSMANV == "-1")
        //             PSMANV = "";
        //         //Lấy tên nhân viên thu cước
        //         let ten_nv = "";
        //         if (PSMANV != null){
        //             let tentat = await this.LAY_TT_NHANVIEN_TC(PSMANV)
        //             ten_nv = tentat[0].TEN_NV;
        //         }
                    
        //         let PSTENNV = ten_nv;
        //         let PSGHICHU = this.ChuanHoa_XML(this.modelInput.txtGhiChuTT);
                
        //         rs2 = await this.MAP_LOAIGT_ID_NEO(this.modelCbo.cboGiayToKH) 
        //         let PSLOAIGTID = rs2[0].LOAIGT_ID_NEO
        //         let PSLOAIGTID1 = "1";
                
        //         rs2 = await this.MAP_LOAIKH_ID_NEO(this.modelCbo.cboLoaiKH2) 
        //         let PSLOAIKH = rs2[0].LOAIKH_ID_NEO //danh bạ

        //         rs2 = await this.MAP_HTTT_ID_NEO(this.inputValue.httt_id) 
        //         let PSDIADIEMTT = rs2[0].HTTT_ID_NEO
        //         let PSNGANHANG = "";
        //         let PSCHUYENKHOAN = "0";

        //         //Đơn vị quản lý id --> map : donvi_id_neo
        //         rs2 = await this.MAP_DONVI_ID_NEO(this.modelCbo.cboDonViQL) 
        //         let PSDONVIQL = rs2[0].DONVI_ID_NEO
        //         if (PSDONVIQL == "-1")
        //         {
        //             this.$root.$toast.error("Thông tin về đơn vị quản lý chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }
        //         rs2 = await this.MAP_MABC_ID_NEO(this.modelCbo.cboDonViQL) 
        //         let PSMABC = rs2[0].MABC_ID_NEO 
        //         if (PSMABC == "-1")
        //         {
        //             this.$root.$toast.error("Thông tin về mã bưu cục chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
        //             this.inputValue.strerrorVinaphone = "error";
        //             return false;
        //         }

        //         let PSKHLON = "1";
        //         let PSUUTIEN = "0";
        //         //TuanNA: Zem code cũ -- ngày 02/12/2011
        //         rs2 = await this.MAP_TNC2_ID_NEO(this.inputValue.TNC2_ID) 
        //         let PSNGANHNGHE = TNC2_ID[0].TNC2_ID_NEO 
        //         let PSDOIDVQLTB = "0";
        //         let PSMAT = this.ChuanHoa_XML(this.modelInput.txtMaTuyen);
        //         let PSTENT = PSMANV;
        //         let PSKYTEN = "";
        //         let PSNGUOI_GT = "";
                




        //         let PSMA_TUYEN = "";
        //         let PSTEN_TUYEN = "";
        //         let PSKH_LON = "1";
        //         let PSMA_QHNS = "";
        //         let PSNGANHANG_ID = "0";
        //         let PSLYDO_ANH = "1"; // khong can upload.
        //         let PSGIOITINH = "";

        //         PSGIOITINH = PSPHAI;
        //         let tentat_ccbs = ''
        //         let kq = await this.ts_hoanthien_hd_cn_kh(PSMA_TUYEN, PSDANGKYTV, PSHOCT_ID, PSUSERID, PSTAIKHOAN, PSKH_LON, PSGHICHU, PSHUONGTT_ID,PSMA_QHNS, PSNOICAPGT1, PSTENNV, PSNGANHANG_ID, PSKYTEN, PSUANTT_ID, PSDANGKYDB, PSLOAIKH, PSDONVIQL,PSONHATT_ID, PSNGANHNGHE, PSSOGT1, PSHOTT_ID, PSUUTIEN, PSLYDO_ANH, PSNGAYCAPGT, PSDIACHITT, PSUANCT_ID,tentat_ccbs, PSGIOITINH, PSMA_KH, PSMSTHUE, PSSOGIAYTO, PSNGAYCAPGT1, PSDIACHICT, PSSODAIDIEN, PSDIENTHOAILH,PSMACQ, PSTENTT, PSDONVIQL, PSNGUOIDAIDIEN, PSHUONGTT_ID, PSONHACT_ID, PSEMAIL, PSMABC, PSNOICAPGT,PSDIADIEMTT, PSTEN_TUYEN, PSMA_KH, PSLOAIGTID, PSNGUOI_GT, PSLOAIGTID1, PSNGAYSINH, PSKHRR, PSCOQUAN,PSCHUYENKHOAN, PSMANV);
        //         //int n;

        //         switch (kq)
        //         {
        //             case null:
        //                 this.$root.$toast.error("Không lấy được dữ liệu trả về từ VNP")
        //                 this.inputValue.strerrorVinaphone = "error";
        //                 return false;
        //             default:
        //                 if (kq != 'OK')
        //                 {
        //                     this.$root.$toast.error("Có lỗi khi thực hiện " +kq )
        //                     this.inputValue.strerrorVinaphone = "error";
        //                     return false;
        //                 }
        //                 status = true;
        //                 return true;
        //         }
        //     }
        //     catch (ex)
        //     {
        //         status = false;
        //         this.inputValue.strerrorVinaphone = ex;
        //         this.$root.$toast.error("Loi xay ra trong qua trinh cap nhat danh ba di dong tren Vinaphone. Ma khach hang " + this.modelInput.txtMaKH + " ,ma thue bao " + this.modelInput.txtMaTB + " " +ex)
        //     }
        //     return status;
        // },
        // Kiemtra_doituong_vdc: async function(hdtb_id, loaihinhtb_id)
        // {
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         P_HDTB_ID: hdtb_id,
        //         PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LAY_SOLUONG_HDTB'
        //     }
        //     let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     let sl = rs.data[0].SL
        //     if (sl > 0)
        //         if (loaihinhtb_id == Enum.LoaiHinhTB.INTERNET_MYTV)
        //         {
        //             let data2 = {
        //                 P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //                 P_HDTB_ID: hdtb_id,
        //                 PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LAY_DOITUONG_HDTB'
        //             }
        //             let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data2)
        //             if (rs.data.length > 0)
        //                 return true;
        //             else
        //                 return false;
        //         }
        //         else
        //         {
        //             return true;
        //         }
        //     else
        //         return false;

        // },
        // ChuanHoa_XML: function (str) {
        //     let result = "";
        //     result = str.replace("&", "&amp");
        //     result = result.replace("<", "&lt");
        //     result = result.replace(">", "&gt");
        //     result = result.replace("'", "&apos");
        //     result = result.replace(String.fromCharCode(34), "&quot");
        //     return result;
        // },
        // ThayDoi_DoiTuong_Visa: async function(doituong_id)
        // {
        //     try
        //     {
        //         let account = this.ChuanHoa_XML(this.modelInput.txtMaTB);
        //         let rs = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
        //                     id_neo: 'doituong_id_vdc',
        //                     table: 'CSS.doituong',
        //                     dk: 'where doituong_id = ' + doituong_id
        //                 })
        //         return await vdcService.ChangeCustomerType(account, rs.data);
        //     }
        //     catch (ex)
        //     {
        //         return false;
        //     }
        // },
        // ThayDoi_NhomKhachHang_UuTien_VASC: async function (vma_tb, vdoituong_id) {
        //     try {
        //         let IPTVAccount = vma_tb;
        //         let sPromo_id = "0";
        //         if (vdoituong_id == Enum.DoiTuong.Y_TE) sPromo_id = "DT02";

        //         let kq =await vasc.ChangePromotions(IPTVAccount, sPromo_id);
        //         if (kq.result == 0) return true;
        //         else return false;
        //     } catch (ex) {
        //         //this.$root.toastError("" + ex);
        //         return false;
        //     }
        // },
        // CapNhat_NVTH_ID: async function (hdtb_id){
        //     try {
        //         let rs = await this.$root.context.get(
        //         "/web-ccdv/hoanthien-hs-tachnhap-tbao/capnhat_nvth/"+hdtb_id
        //         );
        //         return true;
        //     } catch (error) {
        //         return false;
        //     } finally {
        //     }
        // },
        // CapNhat_NVTH_ID_OLD: async function(hdtb_id){
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         HDTB_ID: hdtb_id,
        //         NHANVIENTH_ID: this.$root.token.getNhanVienID(),
        //         PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.CAPNHAT_NVTH_ID'
        //     }
        //     await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        // },
        // CapNhat_TT_Phieu_HTHS: async function (hdtb_id){
        //     try {
        //         let rs = await this.$root.context.get(
        //         "/web-ccdv/hoanthien-hs-tachnhap-tbao/capnhat_tt_phieu_hths/"+hdtb_id
        //         );
        //         return true;
        //     } catch (error) {
        //         return false;
        //     } finally {
        //     }
        // },
        // CapNhat_TT_Phieu_HTHS_OLD: async function(hdtb_id){
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         HDTB_ID: hdtb_id,
        //         PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.GET_HDTB_ID'
        //     }
        //     let _hdtb_id = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     let isHD_CaiDatTBI = false;
        //     let type_id = -1;
        //     if (hdtb_id.data.length > 0)
        //         isHD_CaiDatTBI = _hdtb_id.data[0].HDTB_ID == Enum.LoaiHopDong.VATTU_THUEBAO
        //     if (isHD_CaiDatTBI){//HĐ 13
        //         type_id = 1
        //     }
        //     else {
        //         type_id = 2
        //     }
        //     data = {
        //             P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //             HDTB_ID: hdtb_id,
        //             TYPE_ID: type_id,
        //             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.CAPNHAT_TT_PHIEU_HTHS'
        //         }
        //     await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)

        // },
        // HoanThienHoSo: async function(hdtb_id, may_cn, ngay_cn, ma_nd){
            
        //     let data = {
        //             vphanvung_id: this.$root.token.getPhanVungID(),
        //             vhdtb_id: hdtb_id,
        //             vmay_cn: may_cn,
        //             // vngay_cn: ngay_cn,
        //             vnguoi_cn: ma_nd,
        //             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.HOANTHIEN_HS_CHUYENQUYEN'
        //         }
        //     await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        // },
        // tinh_thicong: async function(tinh_id)
        // {
        //     let tinh = {}
        //     //let rs = objDB.Tinh_ThiCong(tinh_id).Tables[0];
        //     let data = {
        //             P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //             TINHTC: tinh_id,
        //             PROCEDURE_NAME: 'CSS.HOANTHIEN_HOSO.TINH_THICONG'
        //         }
        //     let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     if (rs.data.length > 0)
        //     {
        //         let dr = rs.data[0];
        //         tinh.user = dr.TEN_TRUYCAP;
        //         tinh.pass = dr.MAT_KHAU;
        //         tinh.nhanvien_id = dr.NHANVIEN_ID;
        //         tinh.donvi_id = dr.DONVI_ID;
        //         tinh.matinh = dr.MATINH;
        //         tinh.TNS = dr.TNS_STRING;
        //         tinh.tinhthicong_id = dr.TINH_ID;
        //         tinh.mavung = dr.MA_VUNG;
        //         // if (AppDomain.CurrentDomain.FriendlyName == "Shard.exe")
        //         //     tinh.ip_dll = string.Format("tcp://localhost:{0}/", 7900 + (tinh_id > 70 ? tinh_id - 30 : tinh_id));
        //         // else
        //             tinh.ip_dll = dr.IP_DLL;
        //     }
        //     else
        //     {
        //         tinh.user = "";
        //         tinh.pass = "";
        //         tinh.nhanvien_id = 0;
        //         tinh.donvi_id = 0;
        //         tinh.matinh = "";
        //         tinh.TNS = "";
        //         tinh.tinhthicong_id = 0;
        //         tinh.ip_dll = "";

        //     }
        //     return tinh;
        // },
        // hoangcong_hopdong_bancheo: async function(hdtb_id) {
        //     let data = {
        //             p_phanvung_id: this.$root.token.getPhanVungID(),
        //             PHDTB_ID: hdtb_id,
        //             PROCEDURE_NAME: 'CSS.BANCHEO.HOANCONG_THICONG_2'
        //         }
        //     let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     let tinhtc_id = 0;
        //     let ds = rs.data
        //     if (ds[0].HDTB_BAN > 0)
        //     { // bên thi công hoàn công update dữ liệu bên bán
        //         let tinh = await this.tinh_thicong(ds[0].tinhban);
        //         tinhtc_id = ds[0].tinhtc;

        //         // HopDong bcbase = new HopDong();
        //         // bcbase = ActivateType<HopDong>(tinh.ip_dll + bcfacade.HopDong);

        //         let data = {
        //             p_phanvung_id: this.$root.token.getPhanVungID(),
        //             VHDTB_BAN: ds[0].hdtb_ban,
        //             VKHID_TC: ds[0].khachhang_tc_id,
        //             VTTID_TC: ds[0].thanhtoan_tc_id,
        //             VTBID_TC: ds[0].thuebao_tc_id,
        //             VTINH_TC: tinhtc_id,
        //             PROCEDURE_NAME: 'CSS.BANCHEO.HOANCONG_THICONG_V3'
        //         }
        //         await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)

        //     }
        //     else
        //     {//bên bán hoàn thiện
                
        //         let data = {
        //             p_phanvung_id: this.$root.token.getPhanVungID(),
        //             VHDTBID: hdtb_id,
        //             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LOAIHD_ID_SEL'
        //         }
        //         let ds2 = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //         if (ds2.data.length != 0)
        //         {//anhnt thêm điều kiện thuebao_id_thicong != -1 cho chỉ giao net
        //             if (ds2.data[0].loaihd_id == Enum.LoaiHopDong.DAT_MOI && ds2.data[0].thuebao_id_thicong != "-1")
        //             {
        //                 if (ds2.data[0].dichvuvt_id  != Enum.DichVuVienThong.TSL)
        //                 {
        //                     let tinh = await this.tinh_thicong(ds2.data[0].tinh_thicong);
        //                     let data = {
        //                         p_phanvung_id: this.$root.token.getPhanVungID(),
        //                         thuebao_id: ds2.data[0].thuebao_id_thicong,
        //                         thuebao_kn_id: ds2.data[0].thuebao_id_ban,
        //                         tinh_kn_id: ds2.data[0].tinh_ban,
        //                         PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_BANCHEO_INS'
        //                     }
        //                     await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //                 }
        //                 else
        //                 {
        //                     for (let k = 0; k < ds2.data.length; k++)
        //                     {
        //                         let data = {
        //                             p_phanvung_id: this.$root.token.getPhanVungID(),
        //                             thuebao_id: ds2.data[k].thuebao_id_thicong,
        //                             thuebao_kn_id: ds2.data[k].thuebao_id_ban,
        //                             tinh_kn_id: ds2.data[k].tinh_ban,
        //                             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_BANCHEO_INS'
        //                         }
        //                         await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //                     }
        //                 }
        //             }
        //         }
        //     }
        // },
        // KT_TaoDB_Thang: async function(){
        //     let ngay_cn = DateTimeLib.toDateDisplayWithFormat(new Date(),'yyyyMM')
        //     let vkyhoadon = "" //yyyyMMck
        //     let thang = parseInt(ngay_cn.substring(4, 6))
        //     let nam =   parseInt(ngay_cn.substring(0, 4))
        //     if (thang == 1)
        //     {
        //         thang = 12
        //         nam = nam - 1
        //     }
        //     else
        //     {
        //         thang = thang - 1
        //     }
        //     if (thang.toString().length > 1)
        //     {
        //         vkyhoadon = nam.toString() + thang.toString() + "01"
        //     }
        //     else
        //     {
        //         vkyhoadon = nam.toString() + "0" + thang.toString() + "01"
                
        //     }
        //     let rs =  await this.demKyHoaDon(vkyhoadon)
            
        //     return rs;
        // },
        // demKyHoaDon: async function(vkyhoadon){
        //     try {
        //         let rs = await this.$root.context.get(
        //         "/web-ccdv/hoanthien-hs-tachnhap-tbao/ktra_taodb_thang",
        //         {kyhoadon:vkyhoadon}
        //         );
        //         if (rs.data != null) {
        //         return rs.data[0].kq
        //         }
        //     } catch (error) {
        //         return 0
        //     } finally {
        //     }
        // },
        // HoanThienHS: async function()
        // {
        //     try
        //     {
        //         if (this.dtThueBao.length > 0)
        //         {
        //             // if (tabThongTin.SelectedIndex == 0)
        //             //     tabThongTin.SelectedIndex = 1;

        //             if (this.selectedThueBao.length <= 0)
        //             {
        //                 this.$root.$toast.error(' Hãy chọn thuê bao hoàn thiện')
        //                 return;
        //             }
        //             let dsSelectedTB = this.dtThueBao.filter(i=> this.selectedThueBao.includes(i.HDTB_ID))
        //             for (let t = 0; t < this.selectedThueBao.length; t++)
                    
        //             {
        //                 //if (lvwThueBao.Items[t].Checked)
        //                 // {
        //                     this.inputValue.hdtb_id = dsSelectedTB[t].HDTB_ID//Convert.ToInt32(lvwThueBao.Items[t].SubItems[1].Text);
        //                     this.modelInput.txtMaTB = dsSelectedTB[t].MA_TB//lvwThueBao.Items[t].SubItems[2].Text;
        //                     await this.HienThiTTHopDongTB_V2();
        //                     if (dsSelectedTB[t].NGAY_TT == null)
                            
        //                     {
        //                         this.$root.$toast.error('Chưa có ngày thanh toán. Hãy kiểm tra lại!')
        //                         return;
        //                     }
        //                     let kt_taobang = await this.KT_TaoDB_Thang();
        //                     // if (kt_taobang == 0)
        //                     // {
        //                     //     this.$root.$toast.error("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !")
        //                     //     return;
        //                     // }
        //                     let check = this.KiemTraDL_ChuyenVaoDB()
        //                     if (!check) return;
        //                     if (Enum.DONGBO_TEST.TEST == false)
        //                     {
        //                         //#region Hoàn thiện vinaphone
        //                         ///TuấnNA thêm code đồng bộ hoàn thiện vinaphone -- Ngày 12/08/2010     
        //                         if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.DI_DONG && this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.DIDONGTRASAU)
        //                         {
        //                             if (Enum.TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU)
        //                             {
        //                                 this.$root.$toast.error("Bạn phải hoàn thiện ở menu: Lập hợp đồng-> đồng bộ dữ liệu-> đồng bộ dữ liệu di động lên vinaphone")
        //                                 return;
        //                                 //#region CUONG REM CODE
        //                                 // DataSet ds = new DataSet();
        //                                 // ds = tchopdong.LAY_DS_HDTB_THEO_HDKH_TTHD(hdkh_id, tthd_id);
        //                                 // string ma_gd_neo = ma_gd;// dsHDKH.data[0]["ma_gd"].ToString();
        //                                 // string matb = "";
        //                                 // ///TuấnNA sửa code lấy row[index] đã chọn trên lưới --- > hoàn thiện
        //                                 // int k = 0; ///biến đánh dấu
        //                                 // for (int i = 0; i < ds.Tables.Count; i++)
        //                                 // {
        //                                 //     if (Convert.ToInt64(ds.Tables[0].Rows[i]["hdtb_id"].ToString()) == hdtb_id)
        //                                 //     {
        //                                 //         k = i;
        //                                 //     }
        //                                 // }
        //                                 // matb = "84" + ds.Tables[0].Rows[k]["ma_tb"].ToString();
        //                                 // if (matb.Length > 0)
        //                                 // {
        //                                 //     for (int j = 0; j < 3; j++)
        //                                 //     {
                                                

        //                                 //         //#region Hàm mới
        //                                 //         string kq = Ts_laphd_chuyenquyen_sudung_hoanthienhd(ma_gd_neo, matb);
        //                                 //         if (kq == ma_gd_neo)
        //                                 //         {
        //                                 //             ///TuấnNA sửa code cập nhật Status cho từng thuê bao trong hợp đồng
        //                                 //             ///--- Ngày 17/08/2010                                
        //                                 //             obj.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_HT);
        //                                 //             this.$root.$toast.error("Đã cập nhật danh bạ VinaPhone thành công!");
        //                                 //             break;
        //                                 //         }
        //                                 //         if (j == 2)
        //                                 //         {
        //                                 //             Message_Box.ShowError("Có lỗi xảy ra trong quá trình cập nhật danh bạ lên VinaPhone.\n" + kq);
        //                                 //             return;
        //                                 //         }
        //                                 //         //#endregion
        //                                 //     }
        //                                 // }
        //                                 //#endregion END  CUONG REM CODE
        //                             }
        //                             else
        //                             {
        //                                 await this.CAPNHAT_STATUS_HDTB(this.inputValue.hdtb_id, Enum.TRANGTHAI_DONGBO.DONGBO_HT);
        //                             }
        //                         }

        //                         //#endregion

        //                         //#region Hoàn thiện MyTV
        //                         else if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL && this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.INTERNET_MYTV)
        //                         {
        //                             await this.HoanThienMyTV();
        //                         }
        //                         //#endregion

        //                         //#region Đồng bộ di động
        //                         //  Lamnt: Với trường hợp chuyển quyền một thuê bao <> di động cho 1 khách hàng cũ, và ghép chung hóa đơn
        //                         //với thuê bao di động của khách hàng này. nếu đồng thời thay đổi thông tin thanh toán của khách hàng
        //                         //thì cũng phải đồng bộ thông tin này lên VinaPhone
        //                         if (this.modelCbo.cboDichVuVT != Enum.DichVuVienThong.DI_DONG)
        //                         {
        //                             if (Enum.TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU)
                                    
        //                             {
        //                                 if (this.inputValue.khachhang_id != 0) //Khách hàng nhận quyền phải là khách hàng cũ
                                        
        //                                 {
        //                                     if (await this.CO_THUEBAO_DIDONG(this.inputValue.thanhtoan_id)) //Mã thanh toán có thuê bao di động
        //                                     {
        //                                         //Đồng bộ dữ liệu thông tin thanh toán lên Vinaphone
        //                                         this.dsDBKH = await this.LAY_DS_DBKH_THEO_KHID(this.inputValue.khachhang_id);

        //                                         for (let i = 0; i < 4; i++)
        //                                         {
        //                                             if (await this.UPDATE_HDKH_VINAPHONE())
        //                                             {
        //                                                 break;
        //                                             }
        //                                         }
        //                                         if (this.inputValue.strerrorVinaphone == "")
        //                                         {
        //                                             this.$root.toastSuccess("Cập nhật dữ liệu trên Vinaphone thành công");
        //                                         }
        //                                         else
        //                                         {
        //                                             this.$root.$toast.error("Có lỗi xảy ra trong quá trình cập nhật dữ liệu lên VinaPhone!" + this.inputValue.strerrorVinaphone)
        //                                             return;
        //                                         }
        //                                     }
        //                                 }
        //                             }
        //                         }

        //                         //#endregion

        //                         //#region Thay đổi đối tượng trên Visa
        //                         if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL && this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.INTERNET_ADSL || this.modelCbo.cboLoaihinhTB.SelectedValue == Enum.LoaiHinhTB.INTERNET_FTTH
        //                              || this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.WIFI_FIBER)
        //                         {
        //                             //Hieutc: 06.04.2012: Kiểm tra nếu có sự thay đổi về đối tượng vdc thì mới 
        //                             if (await this.Kiemtra_doituong_vdc(this.inputValue.hdtb_id, Enum.LoaiHinhTB.INTERNET_ADSL))
        //                             {
        //                                 if (!await this.ThayDoi_DoiTuong_Visa(this.modelCbo.cboDoituongTB))
        //                                 {
        //                                     this.$root.$toast.error("Có lỗi trong quá trình thay đổi đối tượng khách hàng trên visa !")
        //                                     return;
        //                                 }
        //                                 else
        //                                 {
        //                                     this.$root.toastSuccess("Thay đổi đối tượng khách hàng trên visa thành công !");
        //                                 }
        //                             }
        //                         }
        //                         //#endregion

        //                         //#region Thay doi nhom khach hang uu tien tren VASC
        //                         if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL && this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.INTERNET_MYTV)
        //                         {
        //                             if (await this.Kiemtra_doituong_vdc(this.inputValue.hdtb_id, Enum.LoaiHinhTB.INTERNET_MYTV))
        //                             {
        //                                 let promo_id = "0";
        //                                 if (this.modelCbo.cboDoituongTB == Enum.DoiTuong.Y_TE)
        //                                     promo_id = "DT02";
        //                                 if (!await this.ThayDoi_NhomKhachHang_UuTien_VASC(this.modelInput.txtMaTB, promo_id))
        //                                 {
        //                                     this.$root.$toast.error("Có lỗi trong quá trình thay đổi nhóm khách hàng ưu tiên trên VASC !")
        //                                     return;
        //                                 }
        //                                 else
        //                                 {
        //                                     this.$root.toastSuccess("Thay đổi nhóm khách hàng ưu tiên trên VASC thành công !");
        //                                 }
        //                             }
        //                         }
        //                         //#endregion

        //                         //#region Hoàn thiện IMS

        //                         //let service = {}//new WinUI.VNPTService.VTNService(tt_nd.user_vtn, tt_nd.pass_vtn);
                                

        //                         if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.IMS)
        //                         {
        //                             if (this.inputValue.loaitb_id != Enum.LoaiHinhTB.IMS_POTS && this.inputValue.loaitb_id != Enum.LoaiHinhTB.IMS_SIP)
        //                             {
        //                                 let result = service.ChuyenQuyen(this.inputValue.ma_tb, this.inputValue.hdtt_id, this.inputValue.hdtb_id);
        //                                 if (result == "ok")
        //                                 {
        //                                     this.$root.toastSuccess("Đã cập nhật danh bạ VTN thành công!");
        //                                  }
        //                                 else
        //                                 {
        //                                     this.$root.toastError("Có lỗi xảy ra trong quá trình cập nhật danh bạ lên VTN.\nLỗi: " + result);
        //                                     return;
        //                                 }
        //                             }
        //                         }
        //                         //# endregion
        //                     }
        //                     await this.CapNhat_NVTH_ID(this.inputValue.hdtb_id);
        //                     await this.CapNhat_TT_Phieu_HTHS(this.inputValue.hdtb_id);
        //                     let ip = await this.getIp()
        //                     await this.HoanThienHoSo(this.inputValue.hdtb_id,ip, DateTimeLib.toDateDisplay(new Date()), this.$root.token.getUserName());

        //                     if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO"))
        //                     {
        //                         try
        //                         {
        //                             await this.hoangcong_hopdong_bancheo(this.inputValue.hdtb_id);
        //                         }
        //                         catch (ex)
        //                         {
        //                             // tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action",
        //                             //     "vuser_name", tt_nd.ma_nd,
        //                             //     "vcomputer_name", tt_nd.may_cn,
        //                             //     "vaction_name", "frmHTHSThayDoiTocDo",
        //                             //     "vkey_parameter", "hdtb_id=" + hdtb_id.ToString() + ",ngay_cn=" + tt_nd.ngay_cn + ", Err : " + ex.ToString(),
        //                             //     "vserver_name", tt_nd.ip,
        //                             //     "vnote", " Hoàn công cố định");
        //                             this.$root.$toast.error(ex.response.data.message_detail )
        //                         }
        //                     }

        //                     if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CQ
        //                          || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CQ
        //                          || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_2M
        //                          || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_THUONG
        //                          || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_TUONGTU)
        //                     {
        //                         let vhdtb_id = 0;
        //                         for (let i = 0; i < this.dtThueBao.length; i++)
        //                         {
        //                             if (this.dtThueBao[i].thuebao_cha_id == this.inputValue.thuebao_id)
        //                             {
        //                                 vhdtb_id = this.dtThueBao[i].hdtb_id
        //                                 await this.CapNhat_NVTH_ID(vhdtb_id);
        //                                 await this.CapNhat_TT_Phieu_HTHS(vhdtb_id);
        //                                 let ip = await this.getIp()
        //                                 await this.HoanThienHoSo(vhdtb_id, ip, DateTimeLib.toDateDisplay(new Date()), this.$root.token.getUserName());
        //                                 if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO"))
        //                                 {
        //                                     try
        //                                     {
        //                                         await this.hoangcong_hopdong_bancheo(vhdtb_id);
        //                                     }
        //                                     catch (ex)
        //                                     {
        //                                         // tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action",
        //                                         //     "vuser_name", tt_nd.ma_nd,
        //                                         //     "vcomputer_name", tt_nd.may_cn,
        //                                         //     "vaction_name", "frmHTHSChuyenQuyen",
        //                                         //     "vkey_parameter", "hdtb_id=" + vhdtb_id.ToString() + ",ngay_cn=" + tt_nd.ngay_cn + ", Err : " + ex.ToString(),
        //                                         //     "vserver_name", tt_nd.ip,
        //                                         //     "vnote", " Hoàn thiện hồ sơ chuyển quyền");
        //                                         this.$root.$toast.error(ex.response.data.message_detail )
        //                                     }
        //                                 }
        //                             }
        //                         }
        //                     }

        //                 // }
        //             }
        //             this.$root.toastSuccess("Cập nhật vào danh bạ thành công !");
        //             let ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.modelInput.txtMaGD, Enum.LoaiHopDong.CHUYEN_QUYEN, 0, this.inputValue.tthd_id, 0, 0)
        //             await this.HienThiTTHopDongKH(ds);//
        //         }
        //     }
        //     catch (exp)
        //     {
        //         this.$root.$toast.error(' '+exp.response.data.message_detail)
        //     }
        // },
        getIp:async function(){
            try{
                const response = await fetch("https://jsonip.com/")
                const data = await response.json();
                return data.ip;
            }catch(e) {
                return null
            }
        },
        // HienThiTTHopDongTB_V2: async function()
        // {
        //     // let ds = tchopdong.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(hdtb_id)
        //     let data = {
        //             vphanvung_id: this.$root.token.getPhanVungID(),
        //             in_hdtb_id: this.inputValue.hdtb_id,
        //             PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG_COMMON.LAY_DS_HOPDONG_TB_THEO_HDTB_ID'
        //         }
        //         let ds = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
                
        //     try
        //     {
        //         if (ds.data.length > 0)
        //         {
        //             this.inputValue.hdtb_id = ds.data[0].HDTB_ID;
        //             if (ds.data[0].thuebao_id != null)
        //                 this.inputValue.thuebao_id = ds.data[0].thuebao_id
        //             else
        //                 this.inputValue.thuebao_id = 0;
        //             this.modelInput.txtMaTB = ds[0].ma_tb
        //             this.inputValue.ma_tb = ds[0].ma_tb;
        //             txtTenTB = this.ChuanHoaTen(ds[0].ten_tb);
        //             txtDiaChiTB = this.ChuanHoaTen(ds[0].diachi_tb);
        //             let temp = await this.TTCT_LoaiHinh_TB(ds[0].LOAITB_ID)
        //             this.inputValue.dichvuvt_id = temp[0].DICHVUVT_ID;
        //             this.modelCbo.cboDichVuVT = temp[0].DICHVUVT_ID;
        //             this.inputValue.loaitb_id = ds[0].loaitb_id
        //             this.modelCbo.cboLoaihinhTB = ds[0].loaitb_id;
        //             this.modelCbo.cboKieuLD = ds[0].kieuld_id
        //             this.modelCbo.cboDoituongTB = ds[0].doituong_id
        //             this.modelCbo.cboTT = ds[0].hdtt_id
        //             this.modelCbo.cboDaiVT = ds[0].donvi_id

        //             await this.HienThiTTMoRong(ds[0].ma_tb, this.inputValue.hdtb_id, temp[0].DICHVUVT_ID);
        //             if (ds[0].tthd_id == Enum.TrangThaiHD.DA_THI_CONG_XONG)
        //                 this.tsbtnChuyenDB = true;
        //             else
        //                  this.tsbtnChuyenDB = false;
        //             ///TuấnNA thêm code lấy thông tin thuê bao -- Ngày 08/08/2010
        //             this.modelInput.txtGhiChuTB = ds[0].ghichu;
        //             this.modelInput.txtDiachiLD = this.ChuanHoaTen(ds[0].diachi_ld);
        //             ///TuấnNA thêm code lấy thông tin thuê bao để hoàn thiện lên MyTV --- Ngày 29/08/2010                    
        //             this.inputValue.matb_Mytv = ds[0].Rma_tb;
        //             this.inputValue.diachi_ld = ds[0].diachi_ld;

                    
        //             dsThaoTac = bangts.GetDataSql(str);
        //             let data = {
        //                 P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //                 P_HDTB_ID: this.inputValue.hdtb_id,
        //                 PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_LAY_DS_THAOTAC'
        //             }
        //             this.dsThaoTac = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
                
        //         }
        //     }
        //     catch (exp)
        //     {
        //         this.$root.$toast.error(' '+exp.response.data.message_detail)
        //     }
        // },
        // PHAILAM: function (CODE) {
        //     let dr = this.dsThaoTac.filter((x) => x.ENABLE == 1 && x.CODE == CODE);
        //     if (dr.Length > 0) return true;
        //     return false;
        // },
        // //#endregion
        // lay_dk_bang: async function (tenBang) {
        //     try {
        //         let rs = await this.$root.context.get(
        //         "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_dk_bang",
        //         { tenBang: tenBang }
        //         );
        //         return rs.data;
        //     } catch (error) {
        //         return "";
        //     } finally {
        //     }
        // },
        // checkMapId: async function (id_neo, table, filter) {
        // try {
        //     if (!filter.includes("where")) filter = " where " + filter;
        //     let rs = await this.$root.context.post(
        //     "/web-hopdong/goi-da-dichvu/fn_map_id",
        //     {
        //         id_neo: id_neo,
        //         table: table,
        //         dk: filter,
        //     }
        //     );
        //     if (rs.data != null) return rs.data;
        //     else return "";
        // } catch (error) {
        //     return "";
        // } finally {
        // }
        // },
        // tsbtnChuyenDB_Click: async function (){
        //     await this.HoanThienHS();
        // },
        HT_DichVuVT_Combobox: async function (){
            //let tag = await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-dangky-cho-bang', {tenbang: 'DICHVU_VT' })
            let tag = await this.lay_dk_bang("DICHVU_VT");
            let input = tag != null ? tag : '';
            await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-dvvt', {thamso: input }).then(
                async (res)=> {
                    
                    this.modelCbo.vcboDichVuVT = res.data;
                    // this.modelCbo.cboDichVuVT = res.data[0].dichvuvt_id
                    if (this.modelCbo.vcboDichVuVT.length > 0) {
                        this.modelCbo.cboDichVuVT = res.data[0].dichvuvt_id
                        await this.getLoaiHinhThueBao()
                        //await this.onchangeDVVT()
                    }
                }
            )
        },
        HT_MucCuocTB_Combobox: async function (mucuoctb_id){

            let data = {
                P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                MUCUOCTB_ID: mucuoctb_id,
                PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.MUCCUOCTB'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            this.modelCbo.vcboMucCuocTB = res.data
            if (res.data.length > 0)
                this.modelCbo.cboMucCuocTB = res.data[0].MUCUOCTB_ID
        },      
        HT_DonVi_loaidv_Combobox: async function(donvi_id, loaidv_id){
            let data = {
                donvi_id: donvi_id,
                loaidv_id: loaidv_id
            }
            await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaidv',data).then(
                res=> {
                    this.modelCbo.vcboDaiVT = res.data
                    this.modelCbo.cboDaiVT = res.data[0].donvi_id
                }
            ).catch(error => {
                this.$root.$toast.error(' '+error)
            });
           
        },
        // HT_LoaiGT_Combobox: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaigt').then(res=> {
        //         this.modelCbo.vcboGiayToKH = res.data
        //         this.modelCbo.cboGiayToKH = res.data[0].loaigt_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_DoiTuong_Combobox: async function(){
        //     //let tag = await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-dangky-cho-bang', {tenbang: 'DOITUONG' })
        //     let tag = await this.lay_dk_bang("DOITUONG");
        //     let input = tag != null ? tag : '';
        //     await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-doituong', {thamso: input}).then(res=> {
        //         this.modelCbo.vcboDoituongTB = res.data
        //         this.modelCbo.cboDoituongTB = res.data[0].doituong_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_NganHang_Combobox_LD: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-nganhang').then(res=> {
        //         this.modelCbo.vcboNganHangKH = res.data
        //         this.modelCbo.cboNganHangKH = res.data[0].nganhang_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_HinhThucTT_Combobox: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-httt').then(res=> {
        //         this.modelCbo.vcboHTTT = res.data
        //         this.modelCbo.cboHTTT = res.data[0].httt_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // }, 
        // cboHTTT_SelectedValueChanged: async function(){
        //     if (this.modelCbo.vcboHTTT.length > 0)
        //     {
        //         //if (cboHTTT.SelectedValue.ToString() == "System.Data.DataRowView") return;
        //         this.inputValue.httt_id = this.modelCbo.cboHTTT

        //         if (parseInt(httt_id) == Enum.HinhThucThanhToan.UYNHIEM_THU)
        //         {
        //             // cboNganHang.Enabled = true;
        //             this.modelCkeckBox.chkNganHangKH = true
        //         }
        //         else
        //         {
        //             // cboNganHang.Enabled = false;
        //             this.modelCkeckBox.chkNganHangKH = false
        //         }
        //     }
        // },
        // Lay_ds_loaikh: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaikh', {nhomloaikh_id: 0}).then(res=> {
        //         this.modelCbo.vcboLoaiKH2 = res.data
        //         if (res.data.length > 0)
        //             this.modelCbo.cboLoaiKH2 = res.data[0].LOAIKH_ID
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_Tocdo_Adsl_Combobox: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-tocdocadsl').then(res=> {
        //         this.modelCbo.vcboGoiCuocAdsl = res.data
        //         this.modelCbo.cboGoiCuocAdsl = res.data[0].tocdo_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_MucCuoc_Adsl_Combobox: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-muccuoc-adsl').then(res=> {
        //         this.modelCbo.vcboMucCuocTB = res.data
        //         this.modelCbo.cboMucCuocTB = res.data[0].muccuoc_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_DonVi_loaidv_Combobox_TT: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaidv', {loaidv_id: 2}).then(res=> {
        //         this.modelCbo.vcboDonViQL = res.data
        //         this.modelCbo.cboDonViQL = res.data[0].donvi_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_DanToc_Combobox: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-dantoc').then(res=> {
        //         this.modelCbo.vcboDanToc = res.data
        //         this.modelCbo.cboDanToc = res.data[0].dantoc_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_QuocTich_Combobox: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-quoctich').then(res=> {
        //         this.modelCbo.vcboQuocTich = res.data
        //         this.modelCbo.cboQuocTich = res.data[0].quoctich_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        HT_NganhNgheLoc_Combobox: async function(){
            let rs = await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-nganhnghe',{ma: 'NGANHNGHE'})
            if (rs.data.length > 0){
                this.modelCbo.vcboNganhNgheLoc = rs.data
                this.modelCbo.cboNganhNgheLoc = rs.data[0].nganhnghe_id
            }
        },
        // HT_NganHang_Combobox_KH: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-nganhang').then(res=> {
        //         this.modelCbo.vcboNganHang = res.data
        //         this.modelCbo.cboNganHang = res.data[0].nganhang_id
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_DT_VinaPhone_Combobox: async function(){
        //     await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-vinaphone', {loaikh_id: 0}).then(res=> {
        //         this.modelCbo.vcboDTVinaPhone = res.data
        //         this.modelCbo.cboDTVinaPhone = res.data[0].doituong_id_neo
        //     }).catch(error => {
        //         this.$root.$toast.error(' '+error)
        //     });
        // },
        // HT_GoiCuoc_DD_Combobox: async function(loaitb_id){
        //     let rs = await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-goicuoc', {loaitb_id: loaitb_id});
        //     this.modelCbo.vcboGoiCuocDD = rs.data
        //         if (rs.data.length > 0)
        //             this.modelCbo.cboGoiCuocDD = rs.data[0].GOICUOC_ID
        // },
        HienThiDS_HDTT_CBO: async function(hdkh_id){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-hdtt-theohdid1', {hdkh_id: hdkh_id}).then(res=> {
                this.modelCbo.vcboTT = res.data
                this.modelCbo.cboTT = res.data[0].hdtt_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        SetButton: function(kieu){
            this.$v.$touch()
            this.buttonState.tsbtnNhapMoi = false;
            this.buttonState.tsbtnGhiLai = false;
            this.buttonState.tsbtnXoa = false;
            this.buttonState.tsbtnHuyBo = false;

            this.buttonState.tsbtnThemTB = false;
            this.buttonState.tsbtnThemTB_DS = false;
            this.buttonState.tsbtnThemTT = false;

            this.buttonState.tsbtnInhopdong = false;
            this.buttonState.tsbtnThanhToan = false;
            this.buttonState.tsbtnXoaTB = false;
            if (kieu == -1)//Bat dau
            {
                //txtMaTB.Focus();
                //this.$refs['ma_tb'].focus()
                this.buttonState.tsbtnGhiLai = true;
                this.buttonState.tsbtnHuyBo = true;
            }

            if (kieu == 0)//Bat dau
            {
                //txtMaTB.Focus();
                //this.$refs['ma_tb'].focus()
                this.buttonState.tsbtnNhapMoi = true;
                this.Clear();
            }

            if (kieu == 1)//Them moi
            {
                //txtMaTB.Focus();
                //this.$refs['ma_tb'].focus()
                this.buttonState.tsbtnGhiLai = true;
                this.buttonState.tsbtnHuyBo = true;
                this.Clear();
            }

            if (kieu == 2)//Huy
            {
                //txtMaGD.Focus();
                //this.$refs['ma_gd'].focus()
                this.buttonState.tsbtnNhapMoi = true;
                this.buttonState.tsbtnXoa = true;
                this.Clear();
            }

            if (kieu == 3)//Edit
            {
                //txtMaGD.Focus();
                //this.$refs['ma_gd'].focus()

                this.buttonState.tsbtnNhapMoi = true;
                this.buttonState.tsbtnXoa = true;
                this.buttonState.tsbtnGhiLai = true;
                this.buttonState.tsbtnHuyBo = true;

                //tsbtnXoaTB.Enabled = true;
                //tsbtnXoaTT.Enabled = true;
                this.buttonState.tsbtnThemTB = true;
                this.buttonState.tsbtnThemTB_DS = true;
                this.buttonState.tsbtnThemTT = true;

                this.buttonState.tsbtnInhopdong = true;
                this.buttonState.tsbtnThanhToan = true;

                if (this.modelInput.lvwThanhToan.length > 1)
                    this.buttonState.tsbtnXoaTT = true;
                if (this.modelInput.lvwThueBao.length > 1)
                    this.buttonState.tsbtnXoaTB = true;
            }
        },
        Clear: async function()
        {
            this.inputValue.Lat_TB = 0; this.inputValue.Lng_TB = 0; this.inputValue.Lat_LD = 0; this.inputValue.Lng_LD = 0;
            this.inputValue.Lat_TT = 0; this.inputValue.Lng_TT = 0; this.inputValue.Lat_BC = 0; this.inputValue.Lng_BC = 0;
            this.inputValue.Lat_KH = 0; this.inputValue.Lng_KH = 0;
            this.inputValue.Lat_DD = 0; this.inputValue.Lng_DD = 0; this.inputValue.Lat_DC = 0; this.inputValue.Lng_DC = 0;
            this.modelCbo.chkDoiTac_ADSL = false;
            this.modelCbo.cboDoiTac_ADSL = -1;

            this.inputValue.pt_doanhthu = 0;
            this.inputValue.pt_diem_tn = 0;

            this.inputValue.kt_kenhphu_ao = false;
            this.modelInput.txtCongVan = "";
            this.modelCkeckBox.chkCongVan = false;
            
            this.modelCkeckBox.chkDC_Dau = false;
            this.modelCkeckBox.chkDC_Cuoi = false;
            // txtDiaChiDau.Enabled = false;
            // txtDiaChiCuoi.Enabled = false;
            // cboTramVTDau.Enabled = false;
            // cboTramVTCuoi.Enabled = false;

            this.modelInput.txtSite_ID = "";
            this.modelInput.txtMaHD_TTKD_MGWAN = "";
            this.modelInput.txtMaHD_TTKD_TSL = "";

            this.modelCkeckBox.chkDoiTacWan = false;
            this.modelInput.txtCuocTK = "0";
            this.modelInput.txtCuocTC = "0";
            this.modelInput.txtCuocHTWan = "0";
            this.modelInput.txtCuocTBiWan = "0";
            this.modelInput.txtCuocTK_DT = "0";
            this.modelInput.txtCuocTC_DT = "0";
            this.modelInput.txtMaHD_DT = "";

            this.modelCkeckBox.chkDoiTac_TSL = false;
            this.modelInput.txtCuocTK_TSL = "0";
            this.modelInput.txtCuocTC_TSL = "0";
            this.modelInput.txtCuocHT_TSL = "0";
            this.modelInput.txtCuocTBI_TSL = "0";
            this.modelInput.txtCuocTKDT_TSL = "0";
            this.modelInput.txtCuocTCDT_TSL = "0";
            this.modelInput.txtMa_HDDT_TSL = "";

            this.inputValue.vhuong_kn = "";
            this.inputValue.kieu_thueso = 0; ;
            this.inputValue.noidung_thueso = "";
            this.inputValue.sothang_thueso = 0;

            this.inputValue.trangbi_id = 3;//Default khách hàng tự trang bị
            this.modelInput.txtTongTienHD = "0";
            this.inputValue.hdkh_id = 0;
            this.modelInput.txtMaGD = "";
            this.modelInput.txtMaHDcu = "";
            this.modelInput.txtMaKH = "";
            this.inputValue.khachhang_id = 0;
            this.modelInput.txtTenKH = "";
            this.modelInput.txtDiaChiKH = "";
            this.inputValue.tinhkh_id = 0;
            this.inputValue.quankh_id = 0;
            this.inputValue.phuongkh_id = 0;
            this.inputValue.phokh_id = 0;
            this.inputValue.apkh_id = 0;
            this.inputValue.khukh_id = 0;
            this.inputValue.dacdiemkh_id = 0;
            this.inputValue.sonhakh = "";
            this.modelInput.txtNguoiDD = "";
            this.modelInput.txtChucDanhDD = "";
            this.modelInput.txtTelKH = "";
            this.modelInput.txtFaxKH = "";

            this.modelInput.txtNgaySN = "";
            this.modelDateTime.dtpNgayLapHD = DateTimeLib.toDateDisplay(new Date())
            this.modelDateTime.dtpNgayYC = DateTimeLib.toDateDisplay(new Date())
            this.modelInput.txtSoGT = "";
            this.modelInput.txtNgayCap = "";
            this.modelInput.txtNoiCap = "";
            this.modelCbo.cboGiayToKH = 0;
            this.modelCbo.vcboTT = null;
            //lvwThanhToan.Items.Clear();
            //lvwThueBao.Items.Clear();
            this.modelInput.txtMaTuyen = "";
            this.inputValue.hdtt_id = 0;
            this.modelInput.txtMaTT = "";
            this.inputValue.thanhtoan_id = 0;
            this.modelInput.txtTenTT = "";
            this.modelInput.txtDiaChiTT = "";
            //diachi bc
            this.modelInput.txtDiaChiBC = "";
            this.inputValue.tinhbc_id = 0;
            this.inputValue.quanbc_id = 0;
            this.inputValue.phuongbc_id = 0;
            this.inputValue.phobc_id = 0;
            this.inputValue.apbc_id = 0;
            this.inputValue.khubc_id = 0;
            this.inputValue.dacdiembc_id = 0;
            this.inputValue.sonhabc = "";
            //
            this.inputValue.tinhtt_id = 0;
            this.inputValue.quantt_id = 0;
            this.inputValue.phuongtt_id = 0;
            this.inputValue.phott_id = 0;
            this.inputValue.aptt_id = 0;
            this.inputValue.khutt_id = 0;
            this.inputValue.dacdiemtt_id = 0;
            this.inputValue.sonhatt = "";
            this.modelInput.txtMST = "";
            this.modelInput.txtSoTKKH = "";
            this.modelCbo.cboHTTT = 0;
            this.modelCbo.cboDVQL = 0;
        
            //cboNganHangTT.Properties.View.FocusedRowHandle = 0;
            //cboMaDD.Properties.DataSource = null;
            //cboMaDD = "";
            this.inputValue.hdtb_id = 0;
            this.inputValue.thuebao_id = 0;
            this.modelInput.txtMaTB = "";
            this.modelInput.txtTenTB = "";
            this.modelInput.txtDiaChiTB = "";
            this.inputValue.tinhtb_id = 0;
            this.inputValue.quantb_id = 0;
            this.inputValue.phuongtb_id = 0;
            this.inputValue.photb_id = 0;
            this.inputValue.aptb_id = 0;
            this.inputValue.khutb_id = 0;
            this.inputValue.dacdiemtb_id = 0;
            this.inputValue.sonhatb = "";
            // Hiếu thêm thông tin địa chỉ lắp đặt 10/07/2010
            this.modelInput.txtDiaChiLD = "";
            this.inputValue.tinhld_id = 0;
            this.inputValue.quanld_id = 0;
            this.inputValue.phuongld_id = 0;
            this.inputValue.phold_id = 0;
            this.inputValue.apld_id = 0;
            this.inputValue.khuld_id = 0;
            this.inputValue.dacdiemld_id = 0;
            this.inputValue.sonhald = "";
            this.modelInput.txtMaTN_Wan = "";
            this.modelInput.txtSoAoWan = "";
            // hoangpkn : 05/09/2013
            // thêm thông tin về TSL
            this.inputValue.tinhdau_id = 0;
            this.inputValue.quandau_id = 0;
            this.inputValue.phuongdau_id = 0;
            this.inputValue.phodau_id = 0;
            this.inputValue.apdau_id = 0;
            this.inputValue.sonhadau = "";
            this.modelInput.txtDiaChiDau = "";
            this.modelInput.txtSoAoTSL_Dau = "";

            this.inputValue.tinhcuoi_id = 0;
            this.inputValue.quancuoi_id = 0;
            this.inputValue.phuongcuoi_id = 0;
            this.inputValue.phocuoi_id = 0;
            this.inputValue.apcuoi_id = 0;
            this.inputValue.sonhacuoi = "";
            this.modelInput.txtDiaChiCuoi = "";
            this.modelInput.txtSoAoTSL_Cuoi = "";
            //cboDoituongTB.EditValue = cboDoituongTB.Properties.GetKeyValue(0);
            this.modelInput.txtMaTN_CD = "";
            this.modelInput.txtMaDoiCap_CD = "";
            this.modelInput.txtMaDoiCap_ADSL = "";
            this.modelInput.txtMaTN_ADSL = "";
            this.modelInput.txtSomayTN = "";
            this.inputValue.SoMayTN = "";
            this.modelInput.txtSoMetDay = "0";
            this.modelInput.txtHuongKNWan = "";

            this.modelCkeckBox.cboDichVuVT = true;
            //Hiếu bổ sung thêm thông tin: 10/08/2010
            this.modelInput.txtDiaChiCT = "";
            this.modelInput.txtChucDanhDD = "";
            this.modelInput.txtNgayHetHan = "";
            this.modelInput.txtMaSTKH = "";
            this.modelInput.txtSoTKKH = "";
            this.modelInput.txtGhiChuKH = "";
            
            this.modelInput.txtGiayToKem = "";
            this.modelCkeckBox.chkNganHangKH = false;
            this.modelCkeckBox.cboNganHangKH = false;
            this.modelInput.txtMaTBDD = "";
            this.modelInput.txtMaERP = "";
            this.modelInput.txtMaBSS = "";

            this.inputValue.blockkh = "";
            this.inputValue.tangkh = "";
            this.inputValue.sophongkh = "";
            this.inputValue.motadckh = "";

            this.inputValue.blockld = "";
            this.inputValue.tangld = "";
            this.inputValue.sophongld = "";
            this.inputValue.motadcld = "";

            this.inputValue.blocktb = "";
            this.inputValue.tangtb = "";
            this.inputValue.sophongtb = "";
            this.inputValue.motadctb = "";

            this.inputValue.blocktt = "";
            this.inputValue.tangtt = "";
            this.inputValue.sophongtt = "";
            this.inputValue.motadctt = "";
            try
            {
                this.modelInput.txtNguoiGT = "";
                this.modelInput.txtCTV = "";
                this.inputValue.nguoigt_id = 0;
                this.inputValue.ctv_id = 0;
            }
            catch (Exception)
            {
            }

            this.modelInput.txtMaNVTC = "";
            this.modelInput.txtNhanVienQL = "";
            this.inputValue.nhanvien_ql_am_id = 0;

            this.modelInput.txtGhiChuTT = "";
            this.modelInput.txtNgaySinhTB = "";
            //Tsl 18/08/2010
            // if (cboTrangBi.DataSource != null)
            //     cboTrangBi.SelectedIndex = 0;
            // cboChuQuan.EditValue = 0;
            // if (cboLoaiTBTSL_Dau.DataSource != null)
            //     cboLoaiTBTSL_Dau.SelectedIndex = 0;
            // if (cboLoaiTBTSL_Cuoi.DataSource != null)
            //     cboLoaiTBTSL_Cuoi.SelectedIndex = 0;
            this.modelInput.txtNgayCapHK = "";
            this.modelInput.txtNoiCapHK = "";
            this.inputValue.donvi_dau_id = 0;
            this.inputValue.donvi_cuoi_id = 0;
            if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL
                || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.BRCD_DOITAC)
            {
                this.inputValue.ltb_adsl_id = 1//this.modelCbo.cboLoaiTBAdsl
            }
            else
            {
                if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MEGAWAN
                    || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.METRONET
                    || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MGW_DOITAC)
                    this.inputValue.ltb_adsl_id = 0//this.modelCbo.cboLoaiTBiWan
                else
                    this.inputValue.ltb_adsl_id = 1;
            }

            this.inputValue.doituong_id = 1;
            this.inputValue.khach_hang_id = 0;
            this.inputValue.kt_adsl = true;
            this.inputValue.ikieuld_id = 0;
            this.modelInput.txtDiaChiNDD = "";
            this.modelInput.txtEmailAdsl = "";
            this.inputValue.kt_adsl = true;
            this.modelInput.txtMaTBMoi = "";
            this.inputValue.ma_tbmoi = "";
            if (this.inputValue.kieukh_canhan)
                //rdoGTNam.Checked = true;
            //TuấnNA thêm code clear ngân sách nhà nước --- Ngày 12/11/2010
            this.inputValue.DV_QHNS = "";
            this.inputValue.MACHUONG = "";
            this.inputValue.MN_KT = "";
            this.inputValue.MA_NDKT = "";
            this.inputValue.MN_NSNN = "";
            this.inputValue.TIEN_DM = 0;
            await this.LAY_DS_SUDUNG_DVGT(0);

            //TuấnNA thêm code clear biến -- Ngày 03/12/2010
            this.inputValue.TNC1_ID = 0;
            this.inputValue.TNC2_ID = 0;
            this.inputValue.TNC3_ID = 0;
            this.inputValue.TNC4_ID = 0;
            this.inputValue.nganhnghe_id = 0;

            this.inputValue.cuoc_tc = 0;
            this.inputValue.cuoc_tk = 0;

            this.inputValue.hdmig_id = 0;
            this.inputValue.mig_id = 0;
            this.modelInput.txtMaIG = "";

            this.modelInput.txtHoKhau = "";
            this.modelInput.txtSTKTT = "";
            this.modelInput.txtChu_TK = "";
            this.inputValue.hdtb_id_cha = 0;
            this.modelInput.txtKhuVuc = "";

            this.dsHDKH_Sub = null;
            this.dsHDTB_Sub = null;
 
        },
        LAY_DS_SUDUNG_DVGT: async function(thuebao_id){
            await this.LAY_DS_THUEBAO_DICHVU(0, thuebao_id, this.inputValue.loaitb_id, 1)
            await this.HT_DichVuGT();
        },
        LAY_DS_THUEBAO_DICHVU: async function(hdtbId, thueBaoId, loaiTbId, kieuId){
            let data = {
                hdtbId: hdtbId,
                thueBaoId: thueBaoId,
                loaiTbId: loaiTbId,
                kieuId: kieuId
            }
            let res = await this.$root.context.post('web-hopdong/hopdong/lay_ds_thuebao_dichvu',  data);
            return res.data
        },
        HT_DichVuGT: async function() {},
        Lay_tienkhoanmuc_loaihd: async function(input){
            let rs = await this.$root.context.get(
                "/web-hopdong/chuyendich/lay_tienkhoanmuc_loaihd",{vloaihd_id:input}
                );
            console.log("🚀 ~ file: index.vue ~ line 3072 ~ Lay_tienkhoanmuc_loaihd:function ~ rs", rs)
                return rs.data;
        },
        Lay_dulieu_combobox: async function(){
            try {
                let args = "DICHVU_VT|LOAIHINH_TB|DANTOC|QUOCTICH|NGANHNGHE|TIEUNGANH_C1|TIEUNGANH_C2|TIEUNGANH_C3|TRANG_BI|THOIHAN|CHUQUAN|LOAIKENH|DOITUONG|HINHTHUC_TT|HINHTHUC_TC|KIEU_HD"
                let rs = await this.$root.context.post("/web-hopdong/chuyendich/lay_danhmuc_chung", {vchuoi: args});
                console.log("🚀 ~ file: index.vue ~ line 3142 ~ Lay_dulieu_combobox:function ~ rs", rs)
                if (rs.data != null) {
                    this.glbDanhMuc_Chung = rs.data;
                    this.modelCbo.vcboHTTT = rs.data.hinhthuc_tt
                    this.modelCbo.vcboDichVuVT = rs.data.dichvu_vt
                    this.modelCbo.vcboDoituongTB = rs.data.DOITUONG
                    this.modelCbo.vcboDanToc = rs.data.dantoc
                    this.modelCbo.vcboQuocTich = rs.data.quoctich
                    this.modelCbo.vcboKieu_HD = rs.data.kieu_hd
                                       
                }
                if (rs.data.hinhthuc_tt.length > 0){
                    this.modelCbo.cboHTTT = this.modelCbo.vcboHTTT[0].httt_id
                }
                 if (rs.data.DOITUONG.length > 0){
                    this.modelCbo.cboDoituongTB = this.modelCbo.vcboDoituongTB[0].doituong_id
                }
                if (rs.data.dichvu_vt.length > 0){
                    this.modelCbo.cboDichVuVT = this.modelCbo.vcboDichVuVT[0].dichvuvt_id
                    await this.onchangeDVVT()
                }
                if (rs.data.quoctich.length > 0){
                    this.modelCbo.cboQuocTich = this.modelCbo.vcboQuocTich[0].quoctich_id
                }
                if (rs.data.dantoc.length > 0){
                    this.modelCbo.cboDanToc = this.modelCbo.vcboDanToc[0].dantoc_id
                }
                if (rs.data.kieu_hd.length > 0){
                    this.modelCbo.cboKieu_HD = this.modelCbo.vcboKieu_HD[0].kieuhd_id
                }
                
            } 
            catch (error) {
                return null;
            } 
            finally {
            }
        },
        lay_danhmuc_chuyendich_tt_chung_v2: async function(){
            try {
                let args = "TRANG_BI|LOAI_TB|CHU_QUAN|TIEN_KM|LOAI_GT|PHANLOAI_KH|LOAI_KH|NGANHANG|GOI_KT|DONVI_QL|KIEU_LD|THOIHAN|DOITAC"
                let rs = await this.$root.context.post('web-hopdong/chuyendich/lay_danhmuc_chuyendich_tt_chung_v2', {vchuoi: args});
                console.log("🚀 ~ file: index.vue ~ line 3171 ~ lay_danhmuc_chuyendich_tt_chung_v2:function ~ rs", rs)
                if (rs.data != null) {
                    this.dsDanhMuc = rs.data;
                    this.modelCbo.vcboGiayToKH = rs.data.loai_gt
                    this.modelCbo.vcboNganHangTT = rs.data.nganhang
                    this.dtPhanLoaiKH = rs.data.phanloai_kh.filter(x => x.NHOM_PL === 1)
                    this.dtLoaiKH = rs.data.loai_kh
                    this.modelCbo.vcboDonViQL = rs.data.donvi_ql.filter(x => x.loaidv_id === Enum.LOAI_DV.DONVIQL_TT)
                    this.modelCbo.vcboDaiVT = rs.data.donvi_ql.filter(x => x.loaidv_id === Enum.LOAI_DV.DONVIQL_LD)
                    this.modelCbo.vcboNganHangKH = rs.data.nganhang 
                    this.modelCbo.vcboGoiKT = rs.data.goi_kt
                    this.modelCbo.vcboLoaiKH2 = rs.data.loai_kh
                }

                if(rs.data.loai_gt.length > 0)
                    this.modelCbo.cboGiayToKH = this.modelCbo.vcboGiayToKH[0].loaigt_id
                if(rs.data.nganhang.length > 0){
                    this.modelCbo.cboNganHangTT = this.modelCbo.vcboNganHangTT[0].nganhang_id
                    this.modelCbo.cboNganHangKH = this.modelCbo.vcboNganHangKH[0].nganhang_id
                }
                if( rs.data.goi_kt.length > 0)
                    this.modelCbo.cboGoiKT = this.modelCbo.vcboGoiKT[0].goikt_id
                if(this.modelCbo.vcboDonViQL.length > 0)
                    this.modelCbo.cboDonViQL = this.modelCbo.vcboDonViQL[0].donvi_id
                if(this.modelCbo.vcboDaiVT.length > 0)
                    this.modelCbo.cboDaiVT = this.modelCbo.vcboDaiVT[0].donvi_id

                // if(this.modelCbo.vcboGiayToKH.length > 0)
                //     this.modelCbo.cboGiayToKH = this.modelCbo.vcboGiayToKH[0].loaigt_id
                // if(this.modelCbo.vcboGiayToKH.length > 0)
                //     this.modelCbo.cboGiayToKH = this.modelCbo.vcboGiayToKH[0].loaigt_id

            } 
            catch (error) {
                return null;
            } 
            finally {
            }
        },
        lay_thamso_macdinh:async function(ma_ts){
            try {
                let rs = await this.$root.context.get(
                "/web-hopdong/chuyendich/lay_danhsach_thamso",
                {
                    kieu: ma_ts,
                }
                );
                return rs.data
            } catch (error) {
                return null
            } finally {
            }
        },
        frmChuyenDich_Activated: async function(){
            // if (this.modelInput.txtMaGD != "")
            // {
            //     HienThiTTHopDongKH(tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0));
            // }
            // txtMaTB.Focus();
        },

        frmChuyenDich_Load: async function(){
            try {
                //cboMaDD.Enabled = true;
                //tabThongTin.SelectedIndex = 1;
                //tabThongTin.SelectedIndex = 0;
                let str = "";
                let tag = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/getTagValue', {id: 1224 })
                if (tag != null && tag != "")
                {
                    let words = tag.data.TAGVALUE.split('+');
                    if (words.length >= 1)
                        this.inputValue.iKenhTN = words[0];
                    else
                        this.inputValue.iKenhTN = 1;
                }
                else
                {
                    this.inputValue.iKenhTN = 1;
                }
                this.modelDateTime.dtpNgayLapHD = DateTimeLib.toDateDisplay(new Date());
                this.modelDateTime.dtpNgayYC = DateTimeLib.toDateDisplay(new Date());
                this.SetButton(-1);
                this.inputValue.ds_tien_khoanmuc = await this.Lay_tienkhoanmuc_loaihd(Enum.LoaiHopDong.DI_CHUYEN);

                var check_kh = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_trangthaisd')
                if ('TRANGTHAISD_ID'.includes(check_kh.data[0]))
                    this.buttonState.tsbtnVatTuMoi = true;

                //#region Lay thong tin danh muc
                await this.lay_danhmuc_chuyendich_tt_chung_v2()
                await this.Lay_dulieu_combobox()
                
                await this.HT_NganhNgheLoc_Combobox()

                // btnHuongKNWan.Visible = false;
                this.inputValue.kt_load = false;
                // pnlTSL.AutoScroll = true;
                // pnlWan.AutoScroll = true;
                // //#endregion

                // //#region Lấy các tham số mặc đinh
                let ds = await this.lay_thamso_macdinh(0)
                
                let det = [];
                let str_vtemp = null
                let row = null
                if (ds.length > 0)
                {
                    //Tham số bắt buộc nhập nhân viên địa bàn
                    str_vtemp = "NHANVIEN_DIABAN_DC";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.nhap_nvkd = 1;
                    }

                    str_vtemp = "CHECK_CHUANHOA_CNTT";  // hoangpkn : 18/03/2020. Check thue bao CNTT can chuan hoa
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.check_chuahoa_cntt = true;
                    }

                    str_vtemp = "PHANTAP_KH";  // hoangpkn : 23/10/2020. Tham số phân tập khách hàng
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts === "1"){
                            this.inputValue.phantap_kh = true;
                        }
                    }

                    str_vtemp = "KIEMTRA_MST";  // hoangpkn : 21/07/2018. Tham soos kieemr tra max soos thuee
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.ts_kt_mst = 1;
                    }

                    str_vtemp = "HT_LOC_CBO_TRAM";  // hoangpkn : 11/03/2019. Lọc bớt dữ liệu combo Trạm trên dynamic
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.ht_loc_cbo_tram = true;
                    }

                    // THam số ko bắt buộc phải nhập cộng tác viên
                    str_vtemp = "KHONGBATBUOC_CTV";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.khong_batbuoc_ctv = 1;
                    }
                    // THam số ko bắt buộc phải nhập cộng tác viên
                    str_vtemp = "BATBUOC_GIOITHIEU_DICHCHUYEN";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.batbuoc_gioithieu_dichchuyen = 1;
                    }

                    str_vtemp = "TUDONG_GANTHEM_HDTB_DV";  // hoangpkn : 21/01/2019. Tự động insert thêm vào hdtb_dv ( phục vụ HNI )
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row["ten_ts"] == "1")
                            this.inputValue.tudong_ganthem_hdtb_dv = true;
                    }

                    //Tham số lấy đơn vị quản lý theo điều kiện hình thức thanh toán
                    str_vtemp = "DONVIQL_HTTT";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row =  det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.DONVIQL_HTTT = 1;
                    }

                    //Tham số lấy tuyến thu theo hình thức thanh toán -- Ha loi
                    str_vtemp = "TUYENTHU_HTTT";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.TUYENTHU_HTTT = 1;
                    }

                    str_vtemp = "KHONG_HIENTHI_TRACUU_DB_DICHCHUYEN";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.khong_ht_tracuudb = 1;
                    }

                    str_vtemp = "KHONGCHO_CHON_KHUVUC";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                        {
                            this.inputValue.khongcho_chon_khuvuc = true;
                            //btnMaNVKD.Enabled = false;
                        }
                    }

                    this.inputValue.ts_sinhma_gd_theo_donvi = ds.filter(x => x.ma_ts == "SINH_MA_GD_THEO_DV").length > 0;

                    //04-02-2020 AnhDt: Bắt buộc nhập sđt
                    str_vtemp = "KIEMTRA_SDT";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                        {
                            //lblRequiredTel.Visible = true;
                            this.inputValue.batbuoc_sdt = true;
                        }
                        else
                        {
                            //lblRequiredTel.Visible = false;
                            this.inputValue.batbuoc_sdt = false;
                        }
                    }
                    //End AnhDt

                    str_vtemp = "KIEMTRA_MSTMNS_KHDN";  // hoangpkn : 21/07/2018. Tham soos kieemr tra max soos thuee
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.ts_kt_mstmns_kh = 1;
                    }

                    str_vtemp = "THEM_TT_HDKHDN";  // hoangpkn : 21/07/2018. Tham soos kieemr tra max soos thuee
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.ts_kt_tthdkhdn = 1;
                    }

                    str_vtemp = "KTRA_TRANGTHAI_DICHCHUYEN";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.ktratt = 1;
                    }

                    //KhaiVK: bắt buộc chọn đặc điểm địa chỉ lắp đặt y/c HPG
                    str_vtemp = "BATBUOC_CHON_DACDIEM_DCLD";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        let row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.batbuoc_chon_dacdiem_dcld = true;
                        else
                            this.inputValue.batbuoc_chon_dacdiem_dcld = false;
                    }

                    str_vtemp = "KT_DICHCHUYEN_FIBER_COLAPCHUNG";
                    det = ds.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        let row = det[0];
                        if (row.ten_ts == "1")
                            this.inputValue.kt_fiber_colapchung = true;
                    }
                }
                //#endregion
            } catch (error) {
               this.$root.toastError(' '+error); 
            }
        },
        // cbb_selectedChanged: async function(data1){
        //     if (!!data1){
        //         this.modelCbo.cboLoaiKH2 = data1.loaikh_id
        //         try
        //         {
        //             if (data1 == null)
        //                 return;

        //             let kieukh_id = 0;
        //             let kieukh = "";
        //             let data = {
        //                 P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //                 p_loaikh_id: data.loaikh_id,
        //                 PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LOAI_KHACHHANG'
        //             }
        //             let dsLoai = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //             if (dsLoai != null && dsLoai.data.length > 0)
        //             {
        //                 kieukh = dsLoai[0].loai
        //             }

        //             if (kieukh != "")
        //                 kieukh_id = kieukh
        //             this.modelInput.txtPhanLoaiKH = "";

        //             if (kieukh_id == Enum.KIEU_KH.CA_NHAN)
        //             {
                    
        //                 let data = {
        //                 P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //                 p_loaikh_id: Enum.PHANLOAI_KH.CA_NHAN,
        //                 PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LOAI_KHACHHANG'
        //                 }
        //                 let dsPL = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //                 if (dsPL != null && dsPL.data.length > 0)
        //                 {
        //                     this.inputValue.PHANLOAIKH_ID = dsPL[0].PHANLOAIKH_ID
        //                 }

        //                 this.lblNgaySN = "Ngày sinh (*)";
        //                 this.modelCkeckBox.disalbeGT = false
        //                 this.modelCkeckBox.disalbeDN = true
        //                 this.checkrdo = 'rdoF';
        //                 this.modelInput.txtPhanLoaiKH = dsPL[0].MA_PLKH
        //             }
        //             else
        //             {
        //                 let data = {
        //                 P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //                 p_loaikh_id: Enum.PHANLOAI_KH.DOANH_NGHIEP,
        //                 PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LOAI_KHACHHANG'
        //                 }
        //                 let dsPL = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //                 if (dsPL != null && dsPL.data.length > 0)
        //                 {
        //                     this.inputValue.PHANLOAIKH_ID = dsPL[0].PHANLOAIKH_ID
        //                 }

        //                 this.lblNgaySN = "Ngày TL (*)";
        //                 this.modelCkeckBox.disalbeGT = true
        //                 this.modelCkeckBox.disalbeDN = false
        //                 this.checkrdo = 'rdoN';
        //                 this.modelInput.txtPhanLoaiKH = dsPL[0].MA_PLKH

        //             }
        //         }
        //         catch (exx)
        //         {
        //         }
        //     }
        // },
        // HienThiGiaoDien: async function(kieugoi_id){
        //     this.inputValue.tthd_id = kieugoi_id
        //     this.tsbtnChuyenDB = false;
        //     this.tsbtnHuyBo = false;
        //     // tss0.Visible = false;
        //     // tss2.Visible = false;
        //     let Text = "TRA CỨU THÔNG TIN HỢP ĐỒNG CHUYỂN QUYỀN";
        //     if (kieugoi_id == Enum.KIEUGOI_CN_HOPDONG.DIEUHANH_THI_CONG)
        //     {
        //         this.tsbtnGhiLai = true;
        //         this.tsbtnHuyBo = true;
        //         //this.tsbtnNhapMoi.Visible = true;
        //         //tss0.Visible = true;
        //         Text = "CẬP NHẬT HỢP ĐỒNG ĐIỀU HÀNH THI CÔNG";
        //     }

        //     if (kieugoi_id == Enum.KIEUGOI_CN_HOPDONG.DANG_THI_CONG)
        //     {
        //         //tsbtnGhiLai.Visible = true;
        //         this.tsbtnHuyBo = true;
        //         //tsbtnNhapMoi.Visible = true;
        //         Text = "CẬP NHẬT HỢP ĐỒNG ĐANG THI CÔNG";
        //     }

        //     if (kieugoi_id == Enum.KIEUGOI_CN_HOPDONG.DA_THI_CONG_XONG)
        //     {
        //         this.tsbtnHuyBo = true;
        //         this.tsbtnChuyenDB = true;
        //         //tss2.Visible = true;
        //         Text = "HOÀN THIỆN HỒ SƠ CHUYỂN QUYỀN";
        //     }
        //     this.labelFunctionTitle = Text;
        // },
        onclickTab: function (currentActivetab) {
            try {
                this.loading(true)
                this.activetab = currentActivetab;
            } catch (error) {
                this.$root.toastError(' '+error);
            }
            finally {
                this.loading(false)
            }
        },
        openPopup: function (){
            if (this.modelCbo.vcboLoaiKH2.length > 0){
                let selectedKH = this.modelCbo.vcboLoaiKH2.filter(obj=>obj.loaikh_id == this.modelCbo.cboLoaiKH2);
                 let selectedDslam = [...selectedKH][0]
                 let kieukh_id = -1
                 let nhomlkh_id = -1
                if (selectedDslam != null){
                    kieukh_id = selectedDslam.loai
                    nhomlkh_id = selectedDslam.nhomlkh_id
                }
                this.dataSend = {
                    loaikh_id: this.modelCbo.cboLoaiKH2,
                    kieukh_id: kieukh_id,
                    nhomlkh_id: nhomlkh_id 
                }
            }
            this.$bvModal.show('popupThueBao')    
        },
        chonKH: async function(data){
            this.modelCbo.cboLoaiKH2 = data
        },
        // openPopupHD: function (){
        //     this.dataHD = {
        //         loaihd_id: Enum.LoaiHopDong.CHUYEN_QUYEN,
        //         dichvuvt_id: 0,
        //         tthd_id: this.inputValue.tthd_id
        //     }
        //     this.$bvModal.show('popupHD')    
        // },
        // chonHD: async function(data){
        //     this.modelInput.txtMaGD = data
        //     await this.onEnterMaGD();
        // },
        
        changeOption: async function() {
        
            if (this.checkrdo === 'rdoF') {
                this.inputValue.gioitinh = 0
            }
            else if (this.checkrdo === 'rdoM'){
                this.inputValue.gioitinh = 1
            }
            else if (this.checkrdo == 'rdoNo')
                this.inputValue.gioitinh = 2
        },
        updateOption: async function(gioitinh) {
            if (gioitinh === 0)
            {
                this.checkrdo = 'rdoF';
            }
            else if (gioitinh === 1)
                this.checkrdo = 'rdoM';
            else 
                this.checkrdo = 'rdoNo';
        },
        LAY_LOAIHINH_TB: async function(dichvuvt_id)
        {
            if (dichvuvt_id != 0 && this.glbDanhMuc_Chung != null)
            {
                console.log("🚀 ~ file: index.vue ~ line 8461 ~ this.glbDanhMuc_Chung", this.glbDanhMuc_Chung)
                var dt = this.glbDanhMuc_Chung.loaihinh_tb.filter(x => x.DICHVUVT_ID == dichvuvt_id);
                console.log("🚀 ~ file: index.vue ~ line 8462 ~ dt", dt)
                
                if (dt.length > 0)
                {
                    return dt.data;
                }
                else
                {
                    return this.glbDanhMuc_Chung.loaihinh_tb;
                }
            }
            else
                return this.glbDanhMuc_Chung.loaihinh_tb;
        },
        GiaoDien_DichVuVT: async function(dichvuvt_id){
            //pnlCD.Visible = false;
            // pnlAdsl.Visible = false;
            // pnlWan.Visible = false;
            // pnlTSL.Visible = false;
            // pnlGP.Visible = false;
            // pnlIMS.Visible = false;
            this.s_display_div = -1
            this.modelCbo.vcboLoaihinhTB = []
            let loaihinh_tb = await this.LAY_LOAIHINH_TB(dichvuvt_id);
            console.log("🚀 ~ file: index.vue ~ line 8481 ~ GiaoDien_DichVuVT:function ~ loaihinh_tb", loaihinh_tb)
            
            this.modelCbo.vcboLoaihinhTB = loaihinh_tb 
              if (loaihinh_tb.length > 0)
                this.modelCbo.cboLoaihinhTB = loaihinh_tb[0].loaitb_id
            if (!this.inputValue.kt_load)
            {
                await this.LAY_DS_SUDUNG_DVGT(0);
            }
            ///TuấnNA comment code --- Ngày 13/09/2010
            if (dichvuvt_id == Enum.DichVuVienThong.CO_DINH)
            {
                // pnlCD.Visible = true;
                // pnlCD.Dock = DockStyle.Fill;
                this.s_display_div = 1
                this.modelCbo.cboLoaihinhTB = Enum.LoaiHinhTB.DIENTHOAI_CD;
                this.modelCbo.cboKieuLD = Enum.KieuLapDat.DC_DIENTHOAI;//Hieu-4.10.2010: Defaul dịch chuyển cố định
                this.inputValue.ltb_adsl_id = 1;
            }

            if (dichvuvt_id == Enum.DichVuVienThong.ADSL || dichvuvt_id == Enum.DichVuVienThong.BRCD_DOITAC)
            {
                // pnlAdsl.Visible = true;
                // pnlAdsl.Dock = DockStyle.Fill;
                this.s_display_div = 2
                this.modelCbo.cboLoaihinhTB = Enum.LoaiHinhTB.MEGA;
                this.inputValue.ltb_adsl_id = this.modelCbo.cboLoaiTBAdsl;
                this.modelCbo.cboKieuLD = Enum.KieuLapDat.DC_ADSL_KHONG_SO;//Hieu-4.10.2010: Defaul dịch chuyển internet không số
            }

            if (dichvuvt_id == Enum.DichVuVienThong.MEGA_EYES)
            {
                // pnlAdsl.Visible = true;
                // pnlAdsl.Dock = DockStyle.Fill;
                this.s_display_div = 2
                this.inputValue.ltb_adsl_id = this.modelCbo.cboLoaiTBAdsl;
            }

            if (dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || dichvuvt_id == Enum.DichVuVienThong.METRONET
                || dichvuvt_id == Enum.DichVuVienThong.MGW_DOITAC)
            {
                // pnlWan.Visible = true;
                // pnlWan.Dock = DockStyle.Fill;
                this.s_display_div = 3
                this.inputValue.ltb_adsl_id = this.modelCbo.cboLoaiTBiWan;
            }

            if (dichvuvt_id == Enum.DichVuVienThong.TSL || dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
            {
                // pnlTSL.Visible = true;
                // pnlTSL.Dock = DockStyle.Fill;
                this.s_display_div = 5
                this.inputValue.ltb_adsl_id = 1;
            }

            if (dichvuvt_id == Enum.DichVuVienThong.GPHONE)
            {
                // pnlGP.Visible = true;
                // pnlGP.Dock = DockStyle.Fill;
                this.s_display_div = 4
                this.inputValue.ltb_adsl_id = 1;
            }
            if (dichvuvt_id == Enum.DichVuVienThong.IMS || dichvuvt_id == Enum.DichVuVienThong.IMS_DOITAC)
            {
                // pnlIMS.Visible = true;
                // pnlIMS.Dock = DockStyle.Fill;
                this.s_display_div = 6
                this.inputValue.ltb_adsl_id = 1;
            }
            
        },
        onchangeDVVT: async function () {
            if (this.modelCbo.vcboDichVuVT.length > 0)
            {
                //if (cboDichVuVT.SelectedValue.ToString() == "System.Data.DataRowView") return;
                if (this.modelCbo.cboDichVuVT == null) return;
                this.inputValue.dichvuvt_id = parseInt(this.modelCbo.cboDichVuVT);
                console.log("🚀 ~ file: index.vue ~ line 3748 ~ this.inputValue.dichvuvt_id", this.inputValue.dichvuvt_id)

                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.METRONET)
                {
                    this.modelCkeckBox.chkCongVan = true;
                    
                }
                else
                {
                    this.modelCkeckBox.chkCongVan = false;
                    this.modelInput.txtCongVan = "";
                }
                console.log("🚀 ~ file: index.vue ~ line 8684 ~ this.modelCkeckBox.chkCongVan", this.modelCkeckBox.chkCongVan)
                // hoangpkn : ko cho dịch chuyển - đổi số IMS
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH)
                    this.buttonState.btnChonSM = true;
                else
                    this.buttonState.btnChonSM = false;

                await this.GiaoDien_DichVuVT(this.inputValue.dichvuvt_id);

                //#region Lay thong tin danh muc
                //#region Megawan, TSL, Metronet
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.METRONET || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL
                     || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MGW_DOITAC || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
                {
                    if (!this.inputValue.kiemtra_dm_mgwan_tsl)
                    {
                        let daivt_id = this.modelCbo.cboDaiVT;
                        let args = "LOAI_KENH|TOCDO_KENH|LOAI_CAP|THOIHAN|DOITAC|TINH_KN|LOAI_MD|TRAM_VT_DAU-donvi_id=" + daivt_id + "|TRAM_VT_CUOI-donvi_id=" + daivt_id + ""
                        // let args = "LOAI_KENH|TOCDO_KENH|LOAI_CAP|THOIHAN|DOITAC|TINH_KN|LOAI_MD|TRAM_VT_DAU|TRAM_VT_CUOI"
                        let rs = await this.$root.context.post("/web-hopdong/chuyendich/lay_danhmuc_chuyendich_mgw_tsl", {vchuoi: args});
                        console.log("🚀 ~ file: index.vue ~ line 3778 ~ rs", rs)
                        if (rs.data != null) {
                            this.dsDanhMuc_mgw_tsl = rs.data
                            this.modelCbo.vcboTocDo = rs.data.tocdo_kenh
                            this.modelCbo.vcboLoaiModem = rs.data.loai_md
                            this.modelCbo.vcboTocDoKenhTSL = rs.data.tocdo_kenh
                            this.modelCbo.vcboTocDo_PIR = rs.data.tocdo_kenh
                            this.modelCbo.vcboDoiTacWan = rs.data.doitac
                            this.modelCbo.vcboDoiTac_TSL = rs.data.doitac
                            this.modelCbo.vcboLoaiCap_Dau = rs.data.loai_cap
                            this.modelCbo.vcboLoaiCap_Cuoi = rs.data.loai_cap
                            this.modelCbo.vcboLoaiCap = rs.data.loai_cap
                        }
                        if (rs.data.tocdo_kenh.length > 0)
                        {
                            this.modelCbo.cboTocDo = this.modelCbo.vcboTocDo[0].TOCDO_ID
                        }
                        if (rs.data.loai_md.length > 0)
                        {
                            this.modelCbo.cboLoaiModem = this.modelCbo.vcboLoaiModem[0].loaimd_id
                        }
                        if (rs.data.tocdo_kenh.length > 0)
                        {
                            this.modelCbo.cboTocDoKenhTSL = this.modelCbo.vcboTocDoKenhTSL[0].TOCDO_ID
                        }
                        if (rs.data.tocdo_kenh.length > 0)
                        {
                            this.modelCbo.cboTocDo_PIR = this.modelCbo.vcboTocDo_PIR[0].TOCDO_ID
                        }

                        if (rs.data.doitac.length > 0)
                        {
                            this.modelCbo.cboDoiTac_TSL = this.modelCbo.vcboDoiTac_TSL[0].DOITAC_ID
                        }
                        if (rs.data.doitac.length > 0)
                        {
                            this.modelCbo.cboDoiTacWan = this.modelCbo.vcboDoiTacWan[0].DOITAC_ID
                        }
                        if (rs.data.loai_cap.length > 0)
                        {
                            this.modelCbo.cboLoaiCap_Cuoi = this.modelCbo.vcboLoaiCap_Cuoi[0].loaicap_id
                        }
                        if (rs.data.loai_cap.length > 0)
                        {
                            this.modelCbo.cboLoaiCap_Dau = this.modelCbo.vcboLoaiCap_Dau[0].loaicap_id
                        }
                        if (rs.data.loai_cap.length > 0)
                        {
                            this.modelCbo.cboLoaiCap = this.modelCbo.vcboLoaiCap[0].loaicap_id
                        }

                         if (this.dsDanhMuc != null){
                            this.modelCbo.vcboLoaiTBiWan = this.dsDanhMuc.loai_tb.filter(x=>x.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN)
                            if (this.modelCbo.vcboLoaiTBiWan.length > 0){
                             this.modelCbo.cboLoaiTBiWan =  this.modelCbo.vcboLoaiTBiWan[0].thietbidc_id
                            }
                            this.modelCbo.vcboTrangBi = this.dsDanhMuc.trangbi
                            if (this.modelCbo.vcboTrangBi.length > 0){
                             this.modelCbo.cboTrangBi =  this.modelCbo.vcboTrangBi[0].trangbi_id
                            }
                            this.modelCbo.vcboChuQuan = this.dsDanhMuc.chu_quan
                            if (this.modelCbo.vcboChuQuan.length > 0){
                             this.modelCbo.cboChuQuan =  this.modelCbo.vcboChuQuan[0].CHUQUAN_ID
                            }
                            this.modelCbo.vcboLoaiTBTSL_Dau = this.dsDanhMuc.loai_tb.filter(x=>x.dichvuvt_id == Enum.DichVuVienThong.TSL)
                            if (this.modelCbo.vcboLoaiTBTSL_Dau.length > 0){
                             this.modelCbo.cboLoaiTBTSL_Dau =  this.modelCbo.vcboLoaiTBTSL_Dau[0].thietbidc_id
                            }
                            this.modelCbo.vcboLoaiTBTSL_Cuoi = this.dsDanhMuc.loai_tb.filter(x=>x.dichvuvt_id == Enum.DichVuVienThong.TSL)
                            if (this.modelCbo.vcboLoaiTBTSL_Cuoi.length > 0){
                             this.modelCbo.cboLoaiTBTSL_Cuoi =  this.modelCbo.vcboLoaiTBTSL_Cuoi[0].thietbidc_id
                            }
                            this.modelCbo.vcboTrangBiWan = this.dsDanhMuc.trangbi
                            if (this.modelCbo.vcboTrangBiWan.length > 0){
                             this.modelCbo.cboTrangBiWan =  Enum.TRANGBI.VIENTHONG_TRANGBI
                            }
                            this.modelCbo.vcboChuQuanWan = this.dsDanhMuc.chu_quan
                            if (this.modelCbo.vcboChuQuanWan.length > 0){
                             this.modelCbo.cboChuQuanWan =  this.modelCbo.vcboChuQuanWan[0].CHUQUAN_ID
                            }
                        }

                    }
                }
                //#endregion

                //#region Internet
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGA_EYES
                    || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.HATANG_VIENTHONG || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.BRCD_DOITAC)
                {
                    if (!this.inputValue.kiemtra_dm_adsl)
                    {
                        this.inputValue.kiemtra_dm_adsl = true;
                        
                        let args = "KIEUTRA|THANGTRA|MUCCUOC"
                        let rs = await this.$root.context.post("/web-hopdong/chuyendich/lay_danhmuc_chuyendich_internet", {vchuoi: args});
                        console.log("🚀 ~ file: index.vue ~ line 8805 ~ rs", rs)
                        if (rs.data != null) {
                            this.dsDanhMuc_adsl = rs.data
                            this.modelCbo.vcboMucCuoc = rs.data.muccuoc
                        }
                        if (this.dsDanhMuc != null){
                            console.log("🚀 ~ file: index.vue ~ line 8811 ~ this.dsDanhMuc", this.dsDanhMuc)
                            this.modelCbo.vcboLoaiTBAdsl = this.dsDanhMuc.loai_tb.filter(x=>x.dichvuvt_id == Enum.DichVuVienThong.ADSL)
                            console.log("🚀 ~ file: index.vue ~ line 8812 ~ this.modelCbo.vcboLoaiTBAdsl", this.modelCbo.vcboLoaiTBAdsl)
                            this.modelCbo.vcboTrangBiADSL = this.dsDanhMuc.trangbi
                            console.log("🚀 ~ file: index.vue ~ line 8814 ~ this.modelCbo.vcboTrangBiADSL", this.modelCbo.vcboTrangBiADSL)
                            this.modelCbo.vcboDoiTac_ADSL = this.dsDanhMuc.doitac
                            console.log("🚀 ~ file: index.vue ~ line 8816 ~ this.modelCbo.vcboDoiTac_ADSL", this.modelCbo.vcboDoiTac_ADSL)
                        }
                        console.log("🚀 ~ file: index.vue ~ line 8816 ~ this.modelCbo.vcboMucCuoc.length", this.modelCbo.vcboMucCuoc.length)
                        if (this.modelCbo.vcboMucCuoc.length > 0){
                            
                            this.modelCbo.cboMucCuoc = this.modelCbo.vcboHTTT[0].muccuoc_id
                        }

                        if (this.modelCbo.vcboLoaiTBAdsl.length > 0){
                             this.modelCbo.cboLoaiTBAdsl =  this.modelCbo.vcboLoaiTBAdsl[0].thietbidc_id
                        }

                        if (this.modelCbo.vcboTrangBiADSL.length > 0){
                             this.modelCbo.cboTrangBiADSL =  this.modelCbo.vcboTrangBiADSL[0].trangbi_id
                        }

                        if (this.modelCbo.vcboDoiTac_ADSL.length > 0){
                             this.modelCbo.cboDoiTac_ADSL =  this.modelCbo.vcboDoiTac_ADSL[0].DOITAC_ID
                        }
                        
                    }
                }
                //#endregion

                //#region Co dinh
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH)
                {
                    if (!this.inputValue.kiemtra_dm_codinh)
                    {
                        this.inputValue.kiemtra_dm_codinh = true;
                        if (this.dsDanhMuc != null){
                            this.modelCbo.vcboTrangBiCD = this.dsDanhMuc.trangbi
                            if (this.modelCbo.vcboTrangBiCD.length > 0){
                             this.modelCbo.cboTrangBiCD =  this.modelCbo.vcboTrangBiCD[0].trangbi_id
                            }
                        }
                    }
                }
                //#endregion

                //#region Gphone
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.GPHONE)
                {
                    if (!this.inputValue.kiemtra_dm_gphone)
                    {
                        this.inputValue.kiemtra_dm_gphone = true;
                        if (this.dsDanhMuc != null){
                            this.modelCbo.vcboTrangBiGP = this.dsDanhMuc.trangbi
                            if (this.modelCbo.vcboTrangBiGP.length > 0){
                             this.modelCbo.cboTrangBiGP =  this.modelCbo.vcboTrangBiGP[0].trangbi_id
                            }
                        }
                    }
                }
                //#endregion

                //#region IMS
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.IMS || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.IMS_DOITAC)
                
                {
                    if (!this.inputValue.kiemtra_dm_IMS)
                    {
                        this.inputValue.kiemtra_dm_IMS = true;
                        if (this.dsDanhMuc != null){
                            this.modelCbo.vcboTrangBiIMS = this.dsDanhMuc.trangbi
                            if (this.modelCbo.vcboTrangBiIMS.length > 0){
                             this.modelCbo.cboTrangBiIMS =  this.modelCbo.vcboTrangBiIMS[0].trangbi_id
                            }
                        }
                    }
                }
                //#endregion

                //#endregion
            }
            console.log("🚀 ~ file: index.vue ~ line 3938 ~ this.inputValue.dichvuvt_id", this.inputValue.dichvuvt_id)
        },
        // HT_Kieu_LD: async function(loaitb_id, loaihd_id){
        //     let data = {
        //             P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //             P_LOAITB_ID: loaitb_id,
        //             P_LOAIHD_ID: loaihd_id,
        //             PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LAY_DS_KIEU_LD'
        //         }
        //         this.modelCbo.vcboKieuLD = []
        //         let dsLoai = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //         this.modelCbo.vcboKieuLD = dsLoai.data
        //         if (dsLoai.data.length > 0){
        //             this.modelCbo.cboKieuLD = dsLoai.data[0].KIEULD_ID
        //         }

        // },
        cboLoaihinhTB_SelectedValueChanged: async function(){

            console.log("🚀 ~ file: index.vue ~ line 8803 ~ cboLoaihinhTB_SelectedValueChanged:function ~ this.dsDanhMuc.kieu_ld", this.dsDanhMuc.kieu_ld)
            if (this.modelCbo.vcboLoaihinhTB.length > 0)
            
            {
                
                if (this.modelCbo.cboLoaihinhTB == null) return;
                this.inputValue.loaitb_id = this.modelCbo.cboLoaihinhTB;

                let dichvu_vt = this.modelCbo.cboDichVuVT;
                //Hiếu bổ sung  lấy kiểu lắp đặt ngày 15.09.2010
                let vdc_noibo = 0;
                if (this.modelCkeckBox.chkDC_NoiBo)
                    vdc_noibo = 1;
                let dtKieuLD = this.dsDanhMuc.kieu_ld.filter(x=>x.loaitb_id == this.inputValue.loaitb_id && x.tinhkhac != 2 && x.dc_noibo == vdc_noibo)
                console.log("🚀 ~ file: index.vue ~ line 8803 ~ cboLoaihinhTB_SelectedValueChanged:function ~ dtKieuLD", dtKieuLD)
                
                
                //var dtKieuLD = null
                // (from DataRow DSTONG
                //                       in dsDanhMuc.Tables["KIEU_LD"].Rows
                //                 where DSTONG["loaitb_id"].ToString() == loaitb_id.ToString() && DSTONG["tinhkhac"].ToString() != "2" && DSTONG["dc_noibo"].ToString() == vdc_noibo.ToString()
                //                 group DSTONG by new { KIEULD_ID = DSTONG["kieuld_id"].ToString(), TEN_KIEULD = DSTONG["TEN_KIEULD"].ToString(), THUTU = DSTONG["THUTU"].ToString() } into g
                //                 orderby Convert.ToInt32(g.Key.THUTU)
                //                 select new { KIEULD_ID = g.Key.KIEULD_ID, TEN_KIEULD = g.Key.TEN_KIEULD }).ToList();
                if (dtKieuLD.length > 0)
                {
                    this.modelCbo.vcboKieuLD = dtKieuLD;
                    if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.ADSL && this.inputValue.ikieuld_id != 0)
                        this.modelCbo.cboKieuLD = this.inputValue.ikieuld_id;
                    await this.cboKieuLD_SelectedValueChanged();
                }
                else
                    this.modelCbo.vcboKieuLD = null;

                if (this.inputValue.kt_load == false)
                {
                    if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.METRONET)
                        await this.HienThi_CTK_CTC();
                    if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.BRCD_DOITAC || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.MEGA_EYES)
                        await this.HT_Tocdo_Adsl_Combobox_ByLoaiTB(this.modelCbo.cboGoiCuocAdsl, this.inputValue.loaitb_id);
                }
            }
        },
        cboKieuLD_SelectedValueChanged: async function() {
            if (this.modelCbo.vcboKieuLD.length > 0)
            {
                if (this.modelCbo.cboKieuLD == null) return;
                //Hiếu TC bổ sung hàm lấy tiền lên ô text box ngày 14/09/2010
                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC)
                {
                    this.inputValue.tocdo_id =  this.modelCbo.cboTocDoKenhTSL;
                }

                let temp_ = await this.MAP_KIEU_LD_NEO(this.modelCbo.cboKieuLD)//Convert.ToInt32(objCheckData.MAP_ID("kieu", DatabaseConstants.DB2 + ".kieu_ld", "where kieuld_id =" + cboKieuLD.SelectedValue));
                let kieu = temp_[0].KIEU
                if (kieu == 1 || kieu == 2 || kieu == 4)
                {
                    // btnMaTN_Wan.Enabled = true;
                    // txtMaTN_Wan.Enabled = false;
                    this.modelInput.txtMaTN_Wan = "";

                    // btnMaTN_Ims.Enabled = true;
                    // txtMATN_IMS.Enabled = false;
                    this.modelInput.txtMATN_IMS = "";

                    // btnMaTN_ADSL.Enabled = true;
                    // txtMaTN_ADSL.Enabled = false;
                    this.modelInput.txtMaTN_ADSL = "";
                }
                else //Neu ko so thi clear o text ma truy nhap
                {
                    // btnMaTN_Wan.Enabled = false;
                    // txtMaTN_Wan.Enabled = false;
                    // btnMaTN_Ims.Enabled = false;
                    // txtMATN_IMS.Enabled = false;
                    // btnMaTN_ADSL.Enabled = false;
                    // txtMaTN_ADSL.Enabled = false;
                    if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MEGAWAN || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.METRONET
                        || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MGW_DOITAC)
                        this.modelInput.txtMaTN_Wan = "";
                    if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.IMS
                        || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.IMS_DOITAC)
                        this.modelInput.txtMATN_IMS = "";
                    if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL
                        || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.BRCD_DOITAC)
                        this.modelInput.txtMaTN_ADSL = "";
                }

                if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH)
                {
                    let temp_ = await this.MAP_KIEU_LD_NEO(this.modelCbo.cboKieuLD)
                    let nhomkld  = temp_[0].NHOMKIEU_LD
                    if (nhomkld == "1") // kiểu lắp đặt có thuê số
                        this.modelCkeckBox.vbtnThueSo = true;
                    else
                        this.modelCkeckBox.vbtnThueSo = false;
                }

                this.inputValue.kieu_thueso = 0; ;
                this.inputValue.noidung_thueso = "";
                this.inputValue.sothang_thueso = 0;
                if (!this.inputValue.kt_load)
                {
                    // if (this.inputValue.load_ds_donvi)
                    //     HT_COMBO_DONVI();
                    await this.LayTienTheoKhoanMuc();
                    await this.HT_DS_KhuyenMai_Combobox();
                }
            }
        },
        LayTienTheoKhoanMuc: async function(){
            try
            {
                //Hiếu TC bổ sung hàm lấy tiền lên ô text box ngày 14/09/2010
                if (this.inputValue.dichvuvt_id != Enum.DichVuVienThong.ADSL && this.inputValue.dichvuvt_id != Enum.DichVuVienThong.BRCD_DOITAC
                    && this.inputValue.dichvuvt_id != Enum.DichVuVienThong.MEGAWAN && this.inputValue.dichvuvt_id != Enum.DichVuVienThong.METRONET 
                    && this.inputValue.dichvuvt_id != Enum.DichVuVienThong.MGW_DOITAC)
                {
                    this.inputValue.kh_cd = 0;
                    this.inputValue.ltb_adsl_id = 1;
                }
                let itocdo_id = 0, imuccuoc_id = 0;
                if (this.inputValue.dichvuvt_id == DichVuVienThong.TSL || this.inputValue.dichvuvt_id == DichVuVienThong.TSL_DOITAC)
                {
                    itocdo_id = this.modelCbo.cboTocDoKenhTSL;
                    this.inputValue.trangbi_id = this.modelCbo.cboTrangBi;
                }
                if (this.inputValue.dichvuvt_id == DichVuVienThong.MEGAWAN || this.inputValue.dichvuvt_id == DichVuVienThong.METRONET || this.inputValue.dichvuvt_id == DichVuVienThong.MGW_DOITAC)
                {
                    this.inputValue.itocdo_id = this.modelCbo.cboTocDo;
                    this.inputValue.trangbi_id = this.modelCbo.cboTrangBiWan;
                }
                if (this.inputValue.dichvuvt_id == DichVuVienThong.ADSL || this.inputValue.dichvuvt_id == DichVuVienThong.BRCD_DOITAC)
                {
                    this.inputValue.itocdo_id = this.modelCbo.cboGoiCuocAdsl;
                    imuccuoc_id = this.modelCbo.cboMucCuoc;
                    this.inputValue.trangbi_id =  this.modelCbo.cboTrangBiADSL;
                }

                //DataSet ds = new DataSet();
                let ds = await this.LAY_TIEN_KHOANMUC_LD(Enum.LoaiHopDong.DI_CHUYEN, DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC), this.modelCbo.cboDichVuVT,
                                            this.modelCbo.cboLoaihinhTB, this.modelCbo.cboKieuLD,
                                            this.inputValue.phuongld_id, itocdo_id, imuccuoc_id, this.inputValue.ltb_adsl_id, this.inputValue.ltb_adsl_moi_id, 
                                            this.inputValue.ltb_adsl_cu_id, this.inputValue.tocdo_moi_id, this.inputValue.tocdo_cu_id, this.inputValue.kh_cd, this.inputValue.phold_id, this.inputValue.apld_id, this.inputValue.khuld_id, this.modelCbo.cboDoituongTB, this.inputValue.trangbi_id);
                if (ds.length > 0)
                {
                    await this.HienThiTienLapDat(ds);
                    await this.LayTongTien_HDTB();
                }
            }
            catch (ex)
            {
                //Message_Box.ShowWarning(ex.ToString());
                this.$root.$toast.error(' '+ex.response.data.message_detail)
            }
        },
        LAY_TIEN_KHOANMUC_LD:async function(loaihd_id, ngay_yc, dichvuvt_id, loaitb_id,
                          kieuld_id, phuongld_id, tocdo_id, muccuoc_id, ltb_adsl_id, ltb_adsl_moi_id,
                              ltb_adsl_cu_id, tocdo_moi_id, tocdo_cu_id, kh_cd, phold_id, apld_id, khuld_id,
                        doituong_id, trangbi_id){
            let data = {
                loaihd_id: loaihd_id, ngay_yc: ngay_yc, dichvuvt_id: dichvuvt_id, loaitb_id: loaitb_id,
                kieuld_id: kieuld_id, phuongld_id: phuongld_id, tocdo_id: tocdo_id, muccuoc_id: muccuoc_id, 
                ltb_adsl_id, ltb_adsl_moi_id, ltb_adsl_cu_id, 
                tocdo_moi_id, tocdo_cu_id, kh_cd, phold_id, 
                apld_id, khuld_id, doituong_id, trangbi_id
            }
            let res = await this.$root.context.post("/web-hopdong/chuyendich/lay_tien_khoanmuc_ld", data);
            return res.data;
        },
        HienThiTienLapDat:async function(ds){
            let kt_lm = true;
            let kt_km = true;
            if (ds[0][0].toString() != "-1")
            {
                for (let i = 0; i < ds.length; i++)
                {

                    if (ds[i]["KHOANMUCTT_ID"].toString().trim() == Enum.KHOANMUC_TT.KMTT_CHUYENDICH.toString())
                    {
                        this.inputValue.dtienld = Convert.ToDecimal(ds.Tables[0].Rows[0]["tien"].ToString());
                        this.inputValue.dvatld = Convert.ToDecimal(ds.Tables[0].Rows[0]["vat"].ToString());
                        kt_lm = false;
                    }

                }
                if (kt_lm == true)
                {

                    this.inputValue.dtienld = 0;
                    this.inputValue.dvatld = 0;

                }

            }
            else
            {

                this.inputValue.dtienld = 0;
                this.inputValue.dvatld = 0;
            }

            for (let j = 0; j < this.inputValue.ds_tien_khoanmuc.length; j++)
            {
                if (this.inputValue.ds_tien_khoanmuc[j]["KHOANMUCTT_ID"].toString() == Enum.KHOANMUC_TT.KMTT_CHUYENDICH.toString())
                {
                    this.inputValue.ds_tien_khoanmuc[j]["TIEN"] = this.inputValue.dtienld;
                    this.inputValue.ds_tien_khoanmuc[j]["VAT"] = this.inputValue.dvatld;
                }
            }
        },
        Lay_CUOCTK_CUOCTC: async function(int_loaitb_id, int_tocdo_id, int_doituong_id, vloaikenh_id, vkieu){
            let data = {
                loaitb_id: int_loaitb_id, tocdo_id: int_tocdo_id, doituong_id: int_doituong_id, loaikenh_id: vloaikenh_id, kieu: vkieu
            }
            let res = await this.$root.context.post("/web-hopdong/chuyendich/lay_cuoctk_cuoctc", data);
            return res.data;
        },
        HienThi_CTK_CTC: async function() {
            let int_loaitb_id = 0;
            let int_tocdo_id = 0;
            let int_doituong_id = 0;
            int_loaitb_id = this.modelCbo.cboLoaihinhTB;
            int_doituong_id = this.modelCbo.cboDoituongTB;
            if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.TSL)
                int_tocdo_id = this.modelCbo.cboTocDoKenhTSL;
            if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MEGAWAN
                || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.METRONET)
                int_tocdo_id = this.modelCbo.cboTocDo;

            let ds_ctk_ctc = await this.Lay_CUOCTK_CUOCTC(int_loaitb_id, int_tocdo_id, int_doituong_id, vloaikenh_id, 1);
            if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MEGAWAN
                || this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.METRONET)
            {
                if (ds_ctk_ctc.length > 0 && ds_ctk_ctc[0]["CUOC_TK"].toString() != null)
                {
                    this.modelInput.txtCuocTK = ds_ctk_ctc[0]["CUOC_TK"].toString()//Convert.ToDecimal(ds_ctk_ctc.Tables[0].Rows[0]["cuoc_tk"].ToString()).ToString(Format.FM_TIEN_VND);
                    this.modelInput.txtCuocTC = ds_ctk_ctc[0]["CUOC_TC"].toString()//Convert.ToDecimal(ds_ctk_ctc.Tables[0].Rows[0]["cuoc_tc"].ToString()).ToString(Format.FM_TIEN_VND);
                }
                else
                {
                    this.modelInput.txtCuocTK = "0";
                    this.modelInput.txtCuocTC = "0";
                }
            }
            if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.TSL)
            {
                if (ds_ctk_ctc.length > 0 && ds_ctk_ctc[0]["CUOC_TK"].oString() != null)
                {
                    this.modelInput.txtCuocTK = ds_ctk_ctc[0]["CUOC_TK"].toString()//Convert.ToDecimal(ds_ctk_ctc.Tables[0].Rows[0]["cuoc_tk"].ToString()).ToString(Format.FM_TIEN_VND);
                    this.modelInput.txtCuocTC = ds_ctk_ctc[0]["CUOC_TC"].toString()//Convert.ToDecimal(ds_ctk_ctc.Tables[0].Rows[0]["cuoc_tc"].ToString()).ToString(Format.FM_TIEN_VND);
                }
                else
                {
                    this.modelInput.txtCuocTK = "0";
                    this.modelInput.txtCuocTC = "0";
                }
            }
        },
        //TuấnNA thêm hàm hiển thị tốc độ ADSL theo danh sách loaitb_id --- Ngày 12/11/2010
        HT_Tocdo_Adsl_Combobox_DsLoaiTB: async function(ds_loaitb)
        {
            let res = await this.$root.context.get("/web-hopdong/chuyendich/lay_tocdo_adsl_loaitb?loaitb_id=", loaitb_id);
            return res.data;
        },
        //Added by HungHM 06/07/2010: Lấy danh sách tốc độ theo loại thuê bao.
        HT_Tocdo_Adsl_Combobox_ByLoaiTB: async function(loaitb_id)
        {
            let res = await this.$root.context.get("/web-hopdong/chuyendich/lay_tocdo_adsl_dsloaitb?loaitb_id=", loaitb_id);
            return res.data;
        }
    },
    mounted: async function() {
        try {
            this.loading(true);
            await this.frmChuyenDich_Load()
        } catch (error) {
            
        } finally {
            this.loading(false);
        }
    },
    watch: {
        // 'modelCbo.cboDichVuVT': function (){}
    }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
