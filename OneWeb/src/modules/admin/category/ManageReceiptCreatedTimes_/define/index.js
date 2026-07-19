export const TRANGTHAI_DONGBO = {
  KHONGPHAI_DB: -1,
  CHUADONGBO: 0,
  DONGBO_LHD: 1,
  DONGBO_TT: 2,
  KICH_HOAT_CA: 3,
  DONGBO_CM: 5,
  DONGBO_HT: 6,
  DONGBO_SERVICE: 7,
  OPEN_SYNC_VINA: false,
  OPEN_SYNC_VINA_TRASAU: false
}

export const TrangThaiHD =
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
  NVKD_XACMINH: 27,
}

export const LoaiHopDong =
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
  GIAHAN: 41, // vuth them 27/08/2020
}

export const LOAI_DV =
{
    DONVI_QL : 1,
    DONVIQL_TT : 2,
    DONVIQL_LD : 3,
    GIAODICH : 4,
    TRAM_VT : 5,                  //Trạm thực hiện thi công
    VETINH : 5,
    HOST : 6,                     // hoangpkn : 27/04/2012
    DONVIQL_TT_BUUDIEN : 7,
    KHACHHANG : 8,
    TRAM_ATM : 9,
    TRAM_MINI : 10,
    DHTC_QLDL : 11,               //Hoàng : 05/02/2012 : Điều hành TC và QLDL
    DONVI_TSL : 12,
    HOST_VATLY : 16,              // Tổng đài xử lý
    QUAYTHU : 18,
    XULY_NO_KHODOI : 19,
    GIAIQUYET_KHIEUNAI : 20,      // hoangpkn : 20/08/2012
    TRAM_IP : 21,                 // Hoangpkn : 28/09/2012
    TO_QUANLY : 22,               //Chientq: 30/06/2011 tổ quản lý kết cuối bên cab
    HOANTHIEN_HOSO : 23,          // Hoangpkn : 26/03/2014
    TRAM_VETINH : 25,             //Vệ tinh cố định
    TRAM_IP_SWITCH : 26,          // Trạm Ip Switch : phát triển FiberVNN
    CONGTACVIEN : 30,             //Lamnt: Đơn vị quản lý cộng tác viên
    PHONGBAN_XM_KHIEUNAI : 31,    // Hoangpkn : 07/06/2013
    TO_THU_CUOC : 32,             //Tổ thu cước
    DAI_BR : 37,                  //Đài Băng Rộng
    TRAM_PORT : 39,               // Hoangpkn : 18/07/2014
    DAILY_XLKN : 45,              // Hoangpkn : 06/09/2014
    PHONG_BANHANG_KV : 46,        //Minhnt thêm loại đơn vị Phòng bán hàng khu vực
    XULY_YCKM : 49,               // TuanNA : 09/02/2015
    BANHANG_TRUCTIEP : 54,        // Hoangpkn : thêm cho HCM 03/10/2016
    GD_NHAN_BANGKE : 61,          // Hoangpkn : 13/07/2015
    KETOAN_NHAN_BANGKE : 62,      // Hoangpkn : 13/07/2015
    DIEUHANH_TRUNGTAM_VT : 63,    // vinhpv
    CUA_HANG : 63,                // hoangpkn : 14/09/2016
    TTDH_NGHIEMTHU : 64,          // Hoangpkn : 18/03/2016
    TTKD_NGHIEMTHU : 65,          // Hoangpkn : 18/03/2016
    DONVI_LAPHOPDONG : 72,        // Hoangpkn : 10/10/2016
    DONVI_LAPHOPDONG_CHA : 73,    // Hoangpkn : 17/10/2016
    KHO_NHAN_TBI_BAOHANH : 80,    // vinhpv : 08/06/2016
    TO_BHSC : 81,                 // vinhpv : 08/06/2016
    KHO_NHAN_TBI_BAOHANH_XONG : 82,          // vinhpv : 08/06/2016
    HOANTHIEN_BAOHANH : 83,       // vinhpv : 08/06/2016
    KENHBAN_THICONG_DV : 98,       // hoangpkn : 25/02/2021
    TONGDAI_CABMAN : 100,         // Lamnt: 27/10/2015
    TIEPNHAN_BAOHONG : 66,         // hoangpkn : 10/07/2017
    TOKD_XLKN : 427,               // hoangpkn : 02/11/2017
    TRAM_CAP_PORT : 504,            // hoangpkn : 22/11/2018
    CSHT_NHATRAM : 555,               // vinhpv : 20180821
    NHOM_XLSC : 620, //Nhóm xử lý sự cố --> minhnt -> dành riêng cho Hà Lội
    BH_QLDH : 635, //Nhóm xử lý sự cố --> minhnt -> dành riêng cho HCM
    BH_DVXLDH : 636, //Nhóm xử lý sự cố --> minhnt -> dành riêng cho HCM
    TT_BANHANG_MIEN : 890, //Trung tâm bán hàng miền (VIP)
    TTKD_BAOHANH_VT : 998, //TTKD - Bảo hành vật tư
    NET_SNOC1 : 432,
    PBH_KHAIBAO_DV : 598,
}
