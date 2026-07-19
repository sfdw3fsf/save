export default {
    header: {
        title: "HTHS - THU TIỀN PHÁT SINH",
        list: [{
            name: "Lập hợp đồng",
            link: {
                name: "Ui.cards"
            }
        },
        {
            name: "Lắp đặt mới",
            link: {
                name: "Ui.buttons"
            },
        },
        ],
    },
    tag: null,
    dsCTTHD: {
        CT_TIENHD: []
    },
    tt_nd: null,
    phanvung_id: 0,
    dtControl: [],
    isLoading: false,
    luong_id: 0,
    huonggiao_id: 0,
    nhanvien_tc_id: -1,
    congviec_th: '',
    dtiendatcoc_tt: 0,
    dvatdatcoc_tt: 0,
    dtienld: 0,
    dvatld: 0,
    i_muccuoc_id: 0,
    i_tocdo_id: -1,
    i_dichvuvt_id: 0,
    i_loaitb_id: 0,
    vdoituong_id: -1,
    dtThaoTac: [],
    vma_tb: "",
    hdtb_id: 0,
    hdkh_id: 0,
    phieu_id: 0,
    donvi_id: 0,
    tag: null,
    tthd_id: 0,
    dsloaihd_id: '1',
    dsdichvuvt_id: '',
    huonggiao_id: 0,
    ds_tien_khoanmuc: [],
    vnhanvien_id: null,
    position: { X: 'center', Y: 'top' },

    dsDC_HDTB: {
        KHUYENMAI_HDTB: []
    },
    // cb 
    cbDichVu: [],
    cbQuyTrinh: [],
    cbKQ_XL: [],
    cbDoiTocDo: [],
    cbKM: [],
    cbNguoiGv: [],
    cbNhanVien_yc: [],

    //
    TrangThaiHD:
    {
        MOI: 1,
        THANH_TOAN: 2,
        DIEUHANH_THI_CONG: 3,
        DANG_THI_CONG: 4,
        DA_THI_CONG_XONG: 5,
        HOAN_THANH: 6,
        THOAITRA: 7,
        DA_LAY_DU_LIEU: 8,
        KHAIBAO_TONGDAI: 9,
        DANGCHO_HOANCONG: 10,
        DOISOAT_HOSO: 11,
        TTVT_XACMINH_NO: 14,
        THOAITRA_TMP: 17,
        TTVT_HOANCONG_DICHVU: 23,
        HTHS_BANCHEO: 26,
        NVKD_XACMINH: 27
    },
    DichVuVienThong:{
        TATCA: -1,
        CO_DINH: 1,
        DI_DONG: 2,
        ADSL: 4,
        DD_TRATRUOC: 5,
        EMAIL: 6,
        METRONET: 7,
        MEGAWAN: 8,
        TSL: 9,
        GPHONE: 10,
        IMS: 11,
        MEGA_EYES: 12,
        HOINGHI_TRUYENHINH: 13,
        ANTOAN_BAOMAT_TT: 14,
        DICHVU_CNTT: 15,
        TRUNGTAM_DULIEU: 16,
        HATANG_VIENTHONG: 19,

        IMS_DOITAC: 21,
        BRCD_DOITAC: 22,
        MGW_DOITAC: 23,
        TSL_DOITAC: 24
    },
    LoaiHinhTB:
    {
        DIENTHOAI_CD: 1,
        TRUNGKE_THUONG: 3,
        TRUNGKE_TUONGTU: 4,
        TRUNGKE_2M: 5,// Có số chính, số phụ
        PABX: 8,
        MAIL_SMD: 9,
        INTERNET: 10,
        INTERNET_1260: 10,
        MEGA: 11,
        INTERNET_ADSL: 11,//Hiếu bổ sung ngày 25/08/2010
        Web_Hosting: 12,
        EMAIL: 13,
        ISDN2B_CD: 14,//megawan cổng FE
        ISDN30B_CD: 15,//megawan cổng GE
        ISDN2B_CQ: 16,
        ISDN30B_CQ: 17,
        HTVC_FIBERVNN_ANALOG: 18,
        HTVC_FIBERVNN_DIGITAL: 19,
        DIDONGTRASAU: 20,
        DIDONGTRATRUOC: 21,
        LEASEDLINE: 22,//Hiếu bổ sung ngày 29/08/2010
        MEGAWAN_ADSL: 23,
        FSECURE: 24,
        MEGAWAN_SHDSL: 25,
        MEGAWAN_QUANG_FE: 26,
        METRONET_FE: 27,
        METRONET_GE: 28,
        MEGAWAN_QUANG_GE: 30,
        MEGACAMERA: 31,
        GPHONE: 32,
        GPHONE_TRATRUOC: 34,
        IOFFICE: 35,
        TD_1800: 38,
        VNPT_COLOCATION: 39,
        INTERNET_ADSL_TINH_KHAC: 57,
        INTERNET_FTTH: 58,
        INTERNET_FIBER_TINH_KHAC: 59,
        INTERNET_MYTV: 61,//B2C
        INTERNET_MYTV_B2B: 171,
        IMS_POTS: 62,
        IMS_SIP: 63,
        IMS_RGW: 64,
        INTERNET_SGTV: 65,
        CAMERA_HOCDUONG: 70,
        MEGAWAN_ADSL_2M: 74,
        METRONET_2FE: 75,
        MEGA_CONFERENCE: 76,
        IMS_SIPTRUNKING: 77,
        SSL: 78,
        WIFI_FIBER: 79,//Hiếu bổ sung ngày 10/01/2017
        VNPT_MEETING: 89,
        HNTH_NGN: 90,
        KTR_Leasedline_GE: 96,
        KTR_Leasedline_E1: 97,
        KTR_Leasedline_STM1: 98,
        KTR_Leasedline_nx64k: 99,
        KTR_TRUCTHONG_DONG: 100,
        KTR_TRUCTHONG_QUANG: 101,
        KTR_DOICAP_QUANG: 102,
        KTR_DOICAP_DONG: 103,
        KTR_Leasedline_FE: 104,
        KTR_Nail_up: 105,
        CDN: 112,
        CA: 116,
        CA_IVAN: 117,
        VN_EDU: 118,
        VNPT_PORTAL: 120,
        TTDL_HDDT: 122,
        TTDL_MAIL_OFFICE: 124,
        TD_1900: 127,
        BRANDNAME: 131,
        IVAN: 132,

        DEDICATED_SERVER: 139,
        TDTH_THUYENHINH: 146,
        VCC: 149,
        MGW_INTERNET_TRUCTIEP: 134,
        COSO_HATANG: 158,
        CAPQUANG_TRANG_TSL: 123,
        MEGAEYES_CLOUD_CAMERA: 83,
        WIFI_MESH: 210,
        INDOOR_CAM: 222,
        OUTDOOR_CAM: 224,
        TIVI_SAMSUNG: 266,

        IMS_DT: 228,
        BRCD_FBDT: 229,
        BRCD_THDT: 230,
        MGW_DT: 231,
        TSL_DT: 232,


        // nhóm dịch vụ CNTT
        ECABINET: 200,
        ELEARNING: 208,
        ORM: 160,
        VXP: 235,
        VNPT_HIS: 136,
        VNPT_HMIS: 156,
        BIENLAIDT: 175,
        VNPT_MCAP: 267,
        VNPT_HCSN: 202,
        VNPT_SMART_TOURIST: 223,
        VNPT_SMCC: 268,
        VEDU_LIB: 205,
        VNPT_SLL: 215,
        VNPT_LIS: 261,

    },
    LoaiHopDong:
    {
        KHONG_DUNG: 0,
        DAT_MOI: 1,
        CHUYEN_QUYEN: 2,
        DI_CHUYEN: 3,
        CHAMDUT_SD: 4,
        THAYDOI_THONGSO_MEGAWAN: 5,
        CHUYENDOI_LH: 6,
        THAY_DOI_DV: 7,
        THAY_DOI_TOCDO_ADSL: 8,
        THAY_DOI_SOMAY_TN: 9,
        TACHNHAP_TB: 10,
        THAY_TT_KHAC: 11,
        THAY_DOI_SO_SIM: 12,
        THUEBAO_TRATRUOC: 13,
        THAY_DAY_MAY: 13,
        VATTU_THUEBAO: 13,
        DOISO_TB: 14,
        BAN_THIETBI: 15,
        THAY_DOI_TOCDO_TSL: 16,
        THU_KHAC: 17,
        KHAO_SAT: 18,
        TDTT_DV_CNTT: 19,
        GHEP_MA_KHACHHANG: 20,
        KY_LAI_HD_GOC: 24,
        TAMDUNG_SD: 25,
        THAYDOI_THONGSO_MYTV: 25,
        TIEPNHAN_DATMOI: 26,
        DOIGOICUOC_DD: 27,
        TIEPNHAN_CDLH: 28,
        TIEPNHAN_DC: 29,
        KHOIPHUC_SD: 30,

        DANGKY_GOI_DADV: 27,
        THAYDOI_GOI_DADV: 28,

        DATCOC_MOI: 31,
        THAYDOI_DATCOC: 32,

        KHAOSATDATMOI: 33,
        KHAOSATCDLH: 34,
        KHAOSATDC: 35,
        TIEPNHANHD: 36,
        THUCUOC_THUHOIVT: 37,
        TUVAN_DV: 38,//phối hợp tư vấn dịch vụ giữa NVKT và TTKD
        THAYDOI_TT_DD: 100, // vuth them - thay doi thoong tin di dong

        DD_SIPTRUNKING: 40, // Taita thêm đăng ký dịch vụ SIPTRUNKING
        DD_HUYSTKING: 42,   // Hủy dịch vụ Siptrunking di động
        DD_DKVCC: 43,      //  Đăng ký dịch vụ VCC di động
        DD_HUYVCC: 44,     //  Hủy dịch vụ VCC di động
        DD_TAMDUNGVCC: 45, //  Tạm dừng dịch vụ VCC di động
        DD_KICHVCC: 46,   //   Kích hoạt lại dịch vụ VCC di động
        DD_CAPNHATGOI: 47, // Cập nhật gói VCC
        DD_XOAGOI: 48,     // Xóa gói VCC
        DD_CAPNHATDATA: 49, // Thêm mới dịch vụ data
        DD_XOADATA: 50,     // Xóa data VCC  

        DD_HSSV: 101, // vuth update before 1509  
        DD_DOANVIEN: 102,// vuth update before 1509
        DK_MULTISIM: 39, // vuth them 27/08/2020
        GIAHAN: 41 // vuth them 27/08/2020
    },
    KHOANMUC_TT:
    {
        KKMTT_DATMOI: 1,//Tiền lắp đặt cho HĐ lắp mới
        KMTT_CHUYENDICH: 2,//Tiền lắp đặt cho HĐ dịch chuyển
        KMTT_DOITOCDO: 3,
        KMTT_DICHVU: 4,//Tiền dịch vụ gia tăng
        KMTT_MUATHIETBI: 5,//Tiền mua thiết bị
        KMTT_DOISIM: 6,
        KMTT_DOISO: 7,
        KMTT_DOITS: 8,
        KMTT_CAIDAT: 9,//Tiền cài đặt
        KMTT_DATCOC: 11,//Tiền đặt cọc
        KMTT_KHOIPHUCSD: 13,//Khôi phục sử dụng
        KMTT_CHUYENQUYEN: 14,
        KMTT_TNTHUEBAO: 15,//Tách nhập thuê bao
        KMTT_DOITD_MB: 16,//Thay đổi tốc độ mobibroadband
        KMTT_TDLOAIHINHTB: 17,
        KMTT_TDDICHVU: 18,//Thay đổi dịch vụ 
        KMTT_KM_LAPDAT: 19,//Khuyến mại lắp đặt
        KMTT_KM_THIETBI: 20,//Khuyến mại thiết bị 
        KMTT_KM_DATCOC: 21,//Khuyến mại đặt cọc : Chọn đặt cọc lấy ra khuyến mại.
        KMTT_DATCOC_KM: 22,//Đặt cọc của khuyến mại : Chọn khuyến mại lấy ra đặt cọc ::> : 0
        KMTT_KM_DVGT: 28,//Khuyến mại cho các khoản dịch vụ gia tăng
        KMTT_TRAGOP_THIETBI: 24,//Tiền trả góp mà khách hàng cần phải trả thêm trong các tháng tiếp theo
        KMTT_TRATRUOC_THIETBI: 25,//Tiền trả trước thiết bị trong trường hợp khách hàng trả góp
        KMTT_TIENTHOAI_DC: 26,//Tiền thoái trả đặt cọc cũ của thuê bao chuyển đổi loại hình
        KMTT_THAYTHE_THUHOI: 30,//Tiền thay thế thu hồi vật tư
        KMTT_CUOC_TAMUNG: 3130,
        KMTT_HOAMANG_DD_TRASAU: 33,//Hòa mạng di động trả sau
        KMTT_TIENTHOAI_DC_DOI_TD: 35,//Tiền thoái trả đặt cọc cũ của thuê bao thay đổi tốc độ
        KMTT_TIENTHOAI_TDDC: 36,//Tiền thoái trả đặt cọc cũ Thay đổi đặt cọc
        KMTT_TDTT_DV_CNTT: 46,//Tiền thoái trả đặt cọc cũ Thay đổi đặt cọc
        KMTT_DK_MULTISIM: 49, // vuth them 27/08/2020: Đăng ký dịch vụ MultiSIM - DDTS
        KMTT_HUY_MULTISIM: 50, // vuth them 27/08/2020: Hủy dịch vụ MultiSIM - DDTS
        KMTT_SODEP: 23, // vuth them de lay tien so dep dd tra sau tu ccbs

        /// <summary>
        /// Ưu đãi lan tỏa
        /// </summary>
        KMTT_UUDAI_LANTOA: 8800,
    },
    form: {
        dich_vu_id: null,
        quy_trinh_id: null,
        kq_xl: null,
        doi_toc_do: 0,
        km: null,
        chkKM: false,
        cuocDC: 0,
        tienTD: 0,
        so_thang: 0,
        tong_tien: 0,
        ma_gd: null,
        ma_tb: null,
        kieu_yc: null,
        ten_kh: null,
        dia_chi_kh: null,
        ten_thanh_toan: null,
        dia_chi_thanh_toan: null,
        isChecked_ngayGV: false,
        isChecked_ngayHT: false,
        isChecked_KQ_XL: false,
        isChecked_Doi_Toc_Do: false,
        ngay_gv: null, 
        ngay_ht: null,
        nguoi_gv: null,
        nv_yc: null,
        noi_dung_giao: null,
        noi_dung_th: null,
        txtGhiChu: null,
    },
    
    button: {
        layTT: true,
        capnhat: true,
        giaoviec: true,
        hoancong: false,
        xuatphieu: true
        
    },
    selectionOptions: { type: 'Multiple' },
    model: {
        hopdong: {
        },
        nhanvien:{

        }
    },
    loadingtable: {
        thuebao: false,
        hopdong: false,
    },
    isLoad: true,
    options: {
        hopdong: [
            // {
            //     hdkh_id: 5264388,
            //     diachi_ld: "Xóm Ông Hổ, Thôn Phi Liệt, Xã Lại Xuân, Huyện Thủy Nguyên, Hải Phòng",
            //     ngaygiao: "2021-03-15 14:11:00",
            //     nd_giao: "Thu thập thông tin liên hệ, số máy: 0855155988",
            //     ma_gd: "HPG-BD/00984467",
            //     phieu_id: 11639915,
            //     ten_kh: "Đặng Quang Phát",
            //     nhanvien_id: 7045,
            //     nv_lhd: "Phạm Thị Vân Anh",
            //     dichvuvt_id: 4,
            //     ma_tb: "hpgmytv654121",
            //     ten_tt: "Đặng Quang Phát",
            //     diachi_tt: "Xóm Ông Hổ, Thôn Phi Liệt, Xã Lại Xuân, Huyện Thủy Nguyên, Hải Phòng",
            //     so_dt: "0989032386",
            //     thangnam_sys: "07/05/2022",
            //     nd_thuchien: null,
            //     ten_ctv: "Đỗ Thị Na",
            //     ten_tb: "Đặng Quang Phát",
            //     ghichu: "ngô trọng sơn yc hometv1 tt15t cài app",
            //     so_dt_lh: "0989032386",
            //     kieuld_id: 586,
            //     ngay_yc: "2021-03-15 14:10:59",
            //     tien: 0,
            //     ngay_ht_gp: null,
            //     chon: 0,
            //     dv_giao: "Phòng Bán hàng Online",
            //     hdtb_id: 5876882,
            //     ten_kieuld: "Thay đổi thông tin liên hệ",
            //     stt: 1,
            //     thuebao_id: 2699750,
            //     loaihinh_tb: "MyTV",
            //     doituong_id: 1,
            //     diachi_kh: "Xóm Ông Hổ, Thôn Phi Liệt, Xã Lại Xuân, Huyện Thủy Nguyên, Hải Phòng",
            //     loaitb_id: 61,
            //     ctv_id: 2681
            // },
            // {
            //     hdkh_id: 5264388,
            //     diachi_ld: "Xóm Ông Hổ, Thôn Phi Liệt, Xã Lại Xuân, Huyện Thủy Nguyên, Hải Phòng",
            //     ngaygiao: "2021-03-15 14:11:00",
            //     nd_giao: "Thu thập thông tin liên hệ, số máy: 0855155988",
            //     ma_gd: "HPG-BD/00984467",
            //     phieu_id: 11639915,
            //     ten_kh: "Đặng Quang Phát",
            //     nhanvien_id: 7045,
            //     nv_lhd: "Phạm Thị Vân Anh",
            //     dichvuvt_id: 4,
            //     ma_tb: "hpgmytv654121",
            //     ten_tt: "Đặng Quang Phát",
            //     diachi_tt: "Xóm Ông Hổ, Thôn Phi Liệt, Xã Lại Xuân, Huyện Thủy Nguyên, Hải Phòng",
            //     so_dt: "0989032386",
            //     thangnam_sys: "07/05/2022",
            //     nd_thuchien: null,
            //     ten_ctv: "Đỗ Thị Na",
            //     ten_tb: "Đặng Quang Phát",
            //     ghichu: "ngô trọng sơn yc hometv1 tt15t cài app",
            //     so_dt_lh: "0989032386",
            //     kieuld_id: 586,
            //     ngay_yc: "2021-03-15 14:10:59",
            //     tien: 0,
            //     ngay_ht_gp: null,
            //     chon: 0,
            //     dv_giao: "Phòng Bán hàng Online",
            //     hdtb_id: 5876882,
            //     ten_kieuld: "Thay đổi thông tin liên hệ",
            //     stt: 1,
            //     thuebao_id: 2699750,
            //     loaihinh_tb: "MyTV",
            //     doituong_id: 1,
            //     diachi_kh: "Xóm Ông Hổ, Thôn Phi Liệt, Xã Lại Xuân, Huyện Thủy Nguyên, Hải Phòng",
            //     loaitb_id: 61,
            //     ctv_id: 2681
            // }
        ],
        nhanvien: [
            
        ]
    },
    config: {
        hopdong: [
            {
                headerText: 'Mã GD',
                allowSorting: true,
                fieldName: 'ma_gd'
            },
            {
                headerText: 'Số máy/Acc',
                allowSorting: true,
                fieldName: 'ma_tb'
            },
            {
                headerText: 'Tên thuê bao',
                allowSorting: true,
                fieldName: 'ten_tb'
            },
            {
                headerText: 'Địa chỉ LĐ',
                allowSorting: true,
                fieldName: 'diachi_ld'
            },
            {
                headerText: 'Ngày YC',
                allowSorting: true,
                fieldName: 'ngaygiao'
            },
            {
                headerText: 'Điện thoại LH',
                allowSorting: true,
                fieldName: 'so_dt_lh'
            },
            {
                headerText: 'Tiền',
                allowSorting: true,
                fieldName: 'tien'
            }
        ],
        nhanvien: [
            {
                headerText: 'Tên nhân viên',
                allowSorting: true,
                fieldName: 'ten_nv'
            },
            {
                headerText: 'Điện thoại',
                allowSorting: true,
                fieldName: 'so_dt'
            },
            {
                headerText: 'Nhiệm vụ',
                allowSorting: true,
                fieldName: 'nhiemvu'
            },
            {
                headerText: 'Ghi chú',
                allowSorting: true,
                fieldName: 'ghichu'
            }
        ]
    },
    
}
