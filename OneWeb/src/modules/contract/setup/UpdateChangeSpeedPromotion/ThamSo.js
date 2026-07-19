const DichVuVienThong={
    TATCA:-1,
    CO_DINH:1,
    DI_DONG:2,
    ADSL:4,
    DD_TRATRUOC:5,
    EMAIL:6,
    METRONET:7,
    MEGAWAN:8,
    TSL:9,
    GPHONE:10,
    IMS:11,
    MEGA_EYES:12,
    HOINGHI_TRUYENHINH:13,
    ANTOAN_BAOMAT_TT:14,
    DICHVU_CNTT:15,
    TRUNGTAM_DULIEU:16,
    HATANG_VIENTHONG:19,
    IMS_DOITAC:21,
    BRCD_DOITAC:22,
    MGW_DOITAC:23,
    TSL_DOITAC:24
}
const LoaiHopDong={
    KHAOSATDATMOI: 33,
    KHAOSATCDLH: 34,
    KHAOSATDC: 35,
    TIEPNHANHD: 36,
    TUVAN_DV: 38,
    THAY_DOI_TOCDO_ADSL: 8,

    TIEPNHAN_DATMOI:26
}

const LoaiNV={
    NHANVIEN_KYTHUAT:51,
    NHANVIEN_KINHDOANH:52,
    NHANVIEN_AM:104
}

const KieuLapDat={
    KHAOSATCDLH: 618,
    THAYDOI_TOCDO_ADSL : 24,
    THAYDOI_MUCCUOC_ADSL : 76,
    THAYDOI_TOCDO_MUCCUOC_ADSL : 77,

    THAYDOI_TOCDO_ADSL_TK : 13080,
    THAYDOI_MUCCUOC_ADSL_TK : 13081,
    THAYDOI_TOCDO_MUCCUOC_ADSL_TK : 13082,

    THAYDOI_TOCDOTHUC_ADSL: 655,
    THAYDOI_CONGNGHE_TOCDO_AON_GPON: 885,
    THAYDOI_CONGNGHE_TOCDO_GPON_AON: 886,

    THAYDOI_CONGNGHE_AON_GPON: 934,
    THAYDOI_CONGNGHE_GPON_AON :935,
}
const TrangThaiHD={
    MOI : 1,
    THANH_TOAN : 2,
    DIEUHANH_THI_CONG : 3,
    DANG_THI_CONG : 4,
    DA_THI_CONG_XONG : 5,
    HOAN_THANH : 6,
    THOAITRA : 7,
    DA_LAY_DU_LIEU : 8,
    KHAIBAO_TONGDAI : 9,
    DANGCHO_HOANCONG : 10,
    DOISOAT_HOSO : 11,
    TTVT_XACMINH_NO : 14,
    THOAITRA_TMP : 17,
    TTVT_HOANCONG_DICHVU : 23,
    HTHS_BANCHEO : 26,
    NVKD_XACMINH : 27,
}

const KHOANMUC_TT  = {
    KMTT_DATMOI: 1,//Tiền lắp đặt cho HĐ lắp mới
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
    KMTT_DATCOC_KM: 22,//Đặt cọc của khuyến mại : Chọn khuyến mại lấy ra đặt cọc:>: 0
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
    KMTT_UUDAI_LANTOA: 8800
}

const LoaiHinhTB = {
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
}
export {
    DichVuVienThong,
    LoaiHopDong,
    LoaiNV,
    KieuLapDat,
    TrangThaiHD,
    KHOANMUC_TT,
    LoaiHinhTB
}