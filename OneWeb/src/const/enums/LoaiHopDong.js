const LoaiHopDong = Object.freeze(
  {
        KHONG_DUNG:0,
        DAT_MOI:1,
        CHUYEN_QUYEN:2,
        DI_CHUYEN:3,
        CHAMDUT_SD:4,
        THAYDOI_THONGSO_MEGAWAN:5,
        CHUYENDOI_LH:6,
        THAY_DOI_DV:7,
        THAY_DOI_TOCDO_ADSL:8,
        THAY_DOI_SOMAY_TN:9,
        TACHNHAP_TB:10,
        THAY_TT_KHAC:11,
        THAY_DOI_SO_SIM:12,
        THUEBAO_TRATRUOC:13,
        THAY_DAY_MAY:13,
        VATTU_THUEBAO:13,
        DOISO_TB:14,
        BAN_THIETBI:15,
        THAY_DOI_TOCDO_TSL:16,
        THU_KHAC:17,
        KHAO_SAT:18,
        TDTT_DV_CNTT:19,
        GHEP_MA_KHACHHANG:20,
        KY_LAI_HD_GOC:24,
        TAMDUNG_SD:25,
        THAYDOI_THONGSO_MYTV:25,
        TIEPNHAN_DATMOI:26,
        DOIGOICUOC_DD:27,
        TIEPNHAN_CDLH:28,
        TIEPNHAN_DC:29,
        KHOIPHUC_SD:30,

        DANGKY_GOI_DADV:27,
        THAYDOI_GOI_DADV:28,

        DATCOC_MOI:31,
        THAYDOI_DATCOC:32,

        KHAOSATDATMOI:33,
        KHAOSATCDLH:34,
        KHAOSATDC:35,
        TIEPNHANHD:36,
        THUCUOC_THUHOIVT:37,
        TUVAN_DV:38,//phối hợp tư vấn dịch vụ giữa NVKT và TTKD
        THAYDOI_TT_DD:100, // vuth them - thay doi thoong tin di dong

        DD_SIPTRUNKING:40, // Taita thêm đăng ký dịch vụ SIPTRUNKING
        DD_HUYSTKING:42,   // Hủy dịch vụ Siptrunking di động
        DD_DKVCC:43,      //  Đăng ký dịch vụ VCC di động
        DD_HUYVCC:44,     //  Hủy dịch vụ VCC di động
        DD_TAMDUNGVCC:45, //  Tạm dừng dịch vụ VCC di động
        DD_KICHVCC:46,   //   Kích hoạt lại dịch vụ VCC di động
        DD_CAPNHATGOI:47, // Cập nhật gói VCC
        DD_XOAGOI:48,     // Xóa gói VCC
        DD_CAPNHATDATA:49, // Thêm mới dịch vụ data
        DD_XOADATA:50,     // Xóa data VCC

        DD_HSSV:101, // vuth update before 1509
        DD_DOANVIEN:102,// vuth update before 1509
        DK_MULTISIM:39, // vuth them 27/08/2020
        GIAHAN:41, // vuth them 27/08/2020
  }
)
export default LoaiHopDong
