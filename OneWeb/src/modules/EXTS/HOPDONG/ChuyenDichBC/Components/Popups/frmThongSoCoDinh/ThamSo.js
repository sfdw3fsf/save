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
  TSL_DOITAC: 24
};
const LoaiHopDong = {
  CHUYENDOI_LH: 6,
  DAT_MOI: 1,
  DANGKY_GOI_DADV: 27,
  CHUYEN_QUYEN: 2,
  DI_CHUYEN: 3,
  KHOIPHUC_SD: 30
};
const LoaiHinhTB = {
  DIENTHOAI_CD: 1,
  TRUNGKE_THUONG: 3,
  TRUNGKE_TUONGTU: 4,
  TRUNGKE_2M: 5, // Có số chính, số phụ
  PABX: 8,
  MAIL_SMD: 9,
  INTERNET: 10,
  INTERNET_1260: 10,
  FSECURE: 24,
  INTERNET_FTTH: 58,
  WIFI_FIBER: 79,
  INTERNET_ADSL: 11,
  INTERNET_MYTV: 61,
  DIDONGTRASAU: 20,
  DIDONGTRATRUOC: 21,
  INTERNET_ADSL_TINH_KHAC: 57,
  INTERNET_FIBER_TINH_KHAC: 59,
  ISDN2B_CD: 14,
  ISDN30B_CD: 15,
  ISDN2B_CQ: 16,
  ISDN30B_CQ: 17,
  MEGA: 11,
  MEGAWAN_ADSL: 23,
  IMS_SIPTRUNKING: 77,

  MEGAWAN_SHDSL: 25,
  MEGAWAN_QUANG_FE: 26,
  METRONET_FE: 27,
  METRONET_GE: 28,
  MEGAWAN_QUANG_GE: 30
};
const KHOANMUC_TT = {
  KMTT_DATMOI: 1,
  KMTT_TIENTHOAI_DC: 26, // Tiền thoái trả đặt cọc cũ của thuê bao chuyển đổi loại hình,
  KMTT_KM_LAPDAT: 19, // Khuyến mại lắp đặt
  KMTT_KM_DATCOC: 21, // Khuyến mại đặt cọc : Chọn đặt cọc lấy ra khuyến mại.
  KMTT_TRAGOP_THIETBI: 24, // Tiền trả góp mà khách hàng cần phải trả thêm trong các tháng tiếp theo
  KMTT_KM_THIETBI: 20, // Khuyến mại thiết bị
  KMTT_MUATHIETBI: 5, // Tiền mua thiết bị
  KMTT_TDLOAIHINHTB: 17,
  KMTT_DATCOC: 11, // Tiền đặt cọc
  KMTT_DATCOC_KM: 22, // Đặt cọc của khuyến mại : Chọn khuyến mại lấy ra đặt cọc ==> = 0
  KMTT_TRATRUOC_THIETBI: 25 // Tiền trả trước thiết bị trong trường hợp khách hàng trả góp
};
const TrangThaiHD = {
  MOI: 1
};
const KIEU_HD = {
  TAI_GD: 1,
  TAI_NHA: 2
};
const TRANGTHAI_DONGBO = {
  DONGBO_LHD: 1,
  OPEN_SYNC_VINA: false
};
const TrangThaiTB = {
  THUONG: 1,
  KHOA_MOTCHIEU_YEUCAU: 2,
  KHOA_MOTCHIEU_CUONGBUC: 3,
  KHOA_HAICHIEU_YEUCAU: 4,
  KHOA_HAICHIEU_CUONGBUC: 5,
  TAMDUNG: 6,
  THANHLY: 7,
  DOISO: 8,
  THANHLY_NO: 9,
  MO: 10
};
const LOAISO_ISDN = {
  SOCHINH: 1,
  SOPHU: 2
};
const KieuLapDat = {
  TDLH_MEGA_FTTH: 182,
  CHUYENDD_TRASAU_SANG_TRATRUOC: 68,
  CHUYENGP_TRATRUOC_SANG_TRASAU: 183,
  CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_CAPQUANG: 190, // CHUYỂN TỪ MEGAWAN ADSL SANG MEGAWAN CÁP QUANG
  CHUYENTU_CODINH_SANG_ISDN: 271,
  CHUYENTU_ISDN_SANG_CODINH: 272,
  CHUYENTU_30B_SANG_2B: 273,
  CHUYENTU_2B_SANG_30B: 274,
  THEM_TB_GOI_DADV: 283,
  XOA_TB_GOI_DADV: 282,
  CHUYENTU_MEGAWAN_CAPQUANG_SANG_MEGAWAN_ADSL: 188, // CHUYỂN TỪ MEGAWAN CÁP QUANG SANG MEGAWAN ADSL
  CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_SHDSL: 186, // CHUYỂN TỪ MEGAWAN ADSL SANG MEGAWAN SDHSL
  CHUYENTU_MEGAWAN_SDHSL_SANG_MEGAWAN_ADSL: 187, // CHUYỂN TỪ MEGAWAN SDHSL SANG MEGAWAN ADSL
  CHUYENTU_MEGAWAN_CAPQUANG_SANG_MEGAWAN_SHDSL: 189, // CHUYỂN TỪ MEGAWAN CÁP QUANG SANG MEGAWAN SDHSL
  CHUYENTU_MEGAWAN_SDHSL_SANG_MEGAWAN_CAPQUANG: 191, // CHUYỂN TỪ MEGAWAN SDHSL SANG MEGAWAN CÁP QUANG
  CHUYENTU_METRONET_FE_SANG_METRONET_GE: 610, // CHU
  THAYDOI_CONGNGHE_TOCDO_AON_GPON: 885,
  THAYDOI_CONGNGHE_TOCDO_GPON_AON: 886,
  THAYDOI_MUCCUOC_ADSL: 76,
  THAYDOI_KIEUTRA_MYTV: 270, // Thay đổi kiểu tra MyTV
  THAYDOI_TOCDO_ADSL: 24,
  THAYDOI_TOCDO_MUCCUOC_ADSL: 77,
  THAYDOI_MUCCUOC_ADSL: 76,
  THAYDOI_TOCDOTHUC_ADSL: 655,
  DOIGOI_FSECURE: 668
};
const TRANGBI = {
  MUA_THIETBI: 1,
  THUE_THIETBI: 2,
  KHACHHANG_TU_TRANGBI: 3,
  VIENTHONG_TRANGBI: 4,
  TTKD_TRANGBI: 5,
  KHONG_SUDUNG_STB: 7,
  VIENTHONG_TRANGBI_TSD_VT: 9
};
const ChiTietDV_IMS = {
  // Dùng cho Siptrunking
  didong: 1, // Triển khai trên nền di động
  internet: 2, // Triển khai trên nền internet
  duongrieng: 3 // Sử dụng đường riêng
};
const DUNG_THU = {
  /// Đối tượng thuê bao dùng thử
  DOITUONG_TB: 190,
  /// thiet bi dung thu
  THIETBI_DC_ID: 145,
  /// Khuyến mại ID dùng thử
  KHUYENMAI_ID: 2999,
  /// Chi tiết khuyến mại ID dùng thử
  CHITIETKM_ID: 12999,
  /// Loại thiết bị mytv dùng thử
  LOAI_TBI_MYTV: 5800,
  /// Khuyến mại ID dùng thử KHDN
  KHUYENMAI_DN_ID: 2998,
  /// Chi tiết khuyến mại ID dùng thử KHDN
  CHITIETKM_DN_ID: 12998
};
const LOAI_KV = {
  // Khu vực khuyến mại
  KHUVUC_CHINHSACH: 1, // Kmai, muc cuoc, bang gia,....
  // Khu vực khác
  KHUVUC_KHAC: 2,
  // Khu vực áp dụng cho thù lao đại lý
  KHUVUC_THULAO_DAILY: 3,
  // Khu vực khoán địa bàn kinh doanh, kỹ thuật
  KHUVUC_DIABAN: 4,
  // Khu vực địa bàn cấp viễn thông
  KHUVUC_DIABAN_VT: 5,
  // Khu vực địa bàn cấp trung tâm
  KHUVUC_DIABAN_TT: 6,
  // Khu vực địa bàn cấ tổ
  KHUVUC_DIABAN_TC: 7
};

const LOAI_DV = {
  TRAM_VT: 5,
  TTKD_NGHIEMTHU: 65
};

const LOAITBI_ADSL = {
  ADSL: 1, // Modem ADSL
  SHDL: 2, // Modem SHDL
  CPE: 3, // CPE
  SET_TOP_BOX: 4, // SET-TOP-BOX
  CONG_FE: 5, // MODEM CỔNG FE
  CONG_GE: 6 // MODEM CỔNG FE
};

const KieuTra = {
  TRATRUOC: 1,
  TRASAU: 2
};
const MUCCUOC = {
  TRON_GOI: 1,
  LUU_LUONG: 2
};
export {
  DichVuVienThong,
  LoaiHopDong,
  LoaiHinhTB,
  KHOANMUC_TT,
  TrangThaiHD,
  KIEU_HD,
  TRANGTHAI_DONGBO,
  TrangThaiTB,
  LOAISO_ISDN,
  KieuLapDat,
  TRANGBI,
  ChiTietDV_IMS,
  DUNG_THU,
  LOAI_KV,
  LOAI_DV,
  LOAITBI_ADSL,
  KieuTra,
  MUCCUOC
};
