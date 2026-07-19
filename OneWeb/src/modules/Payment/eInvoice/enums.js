const DichVuVienThong = {
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
    TSL_DOITAC: 24,
};
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
const LoaiHopDong = {
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
    TUVAN_DV: 38, //, phối hợp tư vấn dịch vụ giữa NVKT và TTKD
    THAYDOI_TT_DD: 100, // vuth them - thay doi thoong tin di dong
  
    DD_SIPTRUNKING: 40, // Taita thêm đăng ký dịch vụ SIPTRUNKING
    DD_HUYSTKING: 42, // Hủy dịch vụ Siptrunking di động
    DD_DKVCC: 43, //  Đăng ký dịch vụ VCC di động
    DD_HUYVCC: 44, //  Hủy dịch vụ VCC di động
    DD_TAMDUNGVCC: 45, //  Tạm dừng dịch vụ VCC di động
    DD_KICHVCC: 46, //   Kích hoạt lại dịch vụ VCC di động
    DD_CAPNHATGOI: 47, // Cập nhật gói VCC
    DD_XOAGOI: 48, // Xóa gói VCC
    DD_CAPNHATDATA: 49, // Thêm mới dịch vụ data
    DD_XOADATA: 50, // Xóa data VCC
  
    DD_HSSV: 101, // vuth update before 1509
    DD_DOANVIEN: 102, // vuth update before 1509
    DK_MULTISIM: 39, // vuth them 27/08/2020
    GIAHAN: 41 // vuth them 27/08/2020
  }
  const TRANGTHAI_DONGBO = {
    KHONGPHAI_DB: -1,
    CHUADONGBO: 0,
    DONGBO_LHD: 1,
    DONGBO_TT: 2,
    KICH_HOAT_CA: 3,
    DONGBO_CM: 5,
    DONGBO_HT: 6,
    DONGBO_SERVICE: 7,
    OPEN_SYNC_VINA: true, //tuanlv: bật cờ để test chức năng đồng bộ thuê bao vinaphone
    OPEN_SYNC_VINA_TRASAU: true
  }

export  {
    DichVuVienThong,
    LoaiHinhTB,
    LoaiHopDong,
    TRANGTHAI_DONGBO
}