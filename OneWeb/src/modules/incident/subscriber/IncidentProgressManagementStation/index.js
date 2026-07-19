import IncidentProgressManagementStation from "./IncidentProgressManagementStation.vue";
export default IncidentProgressManagementStation;

export const phieuYeuCauColumns = [
  { fieldName: "tinhThanh", headerText: "Tỉnh", allowFiltering: true },
  { fieldName: "ma_tb", headerText: "Số máy/Acc", allowFiltering: true },
  { fieldName: "ma_lt", headerText: "Số ảo", allowFiltering: true },
  {
    fieldName: "ten_dv",
    headerText: "Đơn vị tiếp nhận",
    allowFiltering: true
  },
  {
    fieldName: "trangThaiBH",
    headerText: "Trạng thái BH",
    allowFiltering: true
  },
  { fieldName: "ngay_bh", headerText: "Ngày BH", allowFiltering: true },
  {
    fieldName: "thoigian_ck",
    headerText: "Thời gian CK",
    allowFiltering: true
  },
  {
    fieldName: "chitieu_tg",
    headerText: "Chỉ tiêu TG",
    allowFiltering: true
  },
  {
    fieldName: "loaiHinh",
    headerText: "Loại hình",
    allowFiltering: true
  },
  {
    fieldName: "dienthoai_lh",
    headerText: "Điện thoại KH",
    allowFiltering: true
  },
  { fieldName: "luot_bh", headerText: "Lượt KH", allowFiltering: true },
  { fieldName: "lan_pa", headerText: "KH Gọi", allowFiltering: true },
  {
    fieldName: "ghichu_hong",
    headerText: "Ghi chú hỏng",
    allowFiltering: true
  },
  { fieldName: "nguoi_cn", headerText: "Người CN", allowFiltering: true },
  {
    fieldName: "phanloaikh_id",
    headerText: "Phân loại KH",
    allowFiltering: true
  }
];

export const donViVNP = [
  {
    name: "Vùng: Miền Bắc",
    id: 1,
    child: [
      {
        id: 3,
        name: "Bắc Giang",
        ma: "BGG",
        vung: 1,
        maDonVi: "204"
      },
      {
        id: 4,
        name: "Bắc Cạn",
        ma: "BKN",
        vung: 1,
        maDonVi: "209"
      },
      {
        id: 5,
        name: "Bắc Ninh",
        ma: "BNH",
        vung: 1,
        maDonVi: "222"
      },
      {
        id: 12,
        name: "Cao Bằng",
        ma: "CBG",
        vung: 1,
        maDonVi: "206"
      },
      {
        id: 20,
        name: "Hà Giang",
        ma: "HGG",
        vung: 1,
        maDonVi: "219"
      },
      {
        id: 21,
        name: "Hà Nội",
        ma: "HNI",
        vung: 1,
        maDonVi: "24"
      },
      {
        id: 22,
        name: "Điện Biên",
        ma: "DBN",
        vung: 1,
        maDonVi: "215"
      },
      {
        id: 23,
        name: "Hà Tĩnh",
        ma: "HTH",
        vung: 1,
        maDonVi: "239"
      },
      {
        id: 24,
        name: "Hưng Yên",
        ma: "HYN",
        vung: 1,
        maDonVi: "221"
      },
      {
        id: 25,
        name: "Hà Nam",
        ma: "HNM",
        vung: 1,
        maDonVi: "226"
      },
      {
        id: 26,
        name: "Hải Phòng",
        ma: "HPG",
        vung: 1,
        maDonVi: "225"
      },
      {
        id: 27,
        name: "Hải Dương",
        ma: "HDG",
        vung: 1,
        maDonVi: "220"
      },
      {
        id: 32,
        name: "Lai Châu",
        ma: "LCU",
        vung: 1,
        maDonVi: "213"
      },
      {
        id: 33,
        name: "Lạng Sơn",
        ma: "LSN",
        vung: 1,
        maDonVi: "205"
      },
      {
        id: 34,
        name: "Lào Cai",
        ma: "LCI",
        vung: 1,
        maDonVi: "214"
      },
      {
        id: 37,
        name: "Nam Định",
        ma: "NDH",
        vung: 1,
        maDonVi: "228"
      },
      {
        id: 38,
        name: "Nghệ An",
        ma: "NAN",
        vung: 1,
        maDonVi: "238"
      },
      {
        id: 39,
        name: "Ninh Bình",
        ma: "NBH",
        vung: 1,
        maDonVi: "229"
      },
      {
        id: 45,
        name: "Quảng Ninh",
        ma: "QNH",
        vung: 1,
        maDonVi: "203"
      },
      {
        id: 49,
        name: "Sơn La",
        ma: "SLA",
        vung: 1,
        maDonVi: "212"
      },
      {
        id: 51,
        name: "Thái Bình",
        ma: "TBH",
        vung: 1,
        maDonVi: "227"
      },
      {
        id: 52,
        name: "Thanh Hoá",
        ma: "THA",
        vung: 1,
        maDonVi: "237"
      },
      {
        id: 56,
        name: "Tuyên Quang",
        ma: "TQG",
        vung: 1,
        maDonVi: "207"
      },
      {
        id: 58,
        name: "Vĩnh Phúc",
        ma: "VPC",
        vung: 1,
        maDonVi: "211"
      },
      {
        id: 59,
        name: "Phú Thọ",
        ma: "PTO",
        vung: 1,
        maDonVi: "210"
      },
      {
        id: 60,
        name: "Yên Bái",
        ma: "YBI",
        vung: 1,
        maDonVi: "216"
      },
      {
        id: 61,
        name: "Thái Nguyên",
        ma: "TNN",
        vung: 1,
        maDonVi: "208"
      },
      {
        id: 65,
        name: "Hòa Bình",
        ma: "HBH",
        vung: 1,
        maDonVi: "218"
      }
    ]
  },
  {
    name: "Vùng: Miền Trung",
    id: 2,
    child: [
      {
        id: 6,
        name: "Bình Định",
        ma: "BDH",
        vung: 2,
        maDonVi: "256"
      },
      {
        id: 15,
        name: "Tp.Đà Nẵng",
        ma: "DNG",
        vung: 2,
        maDonVi: "236"
      },
      {
        id: 16,
        name: "Đắc Lắc",
        ma: "DLC",
        vung: 2,
        maDonVi: "262"
      },
      {
        id: 19,
        name: "Gia Lai",
        ma: "GLI",
        vung: 2,
        maDonVi: "269"
      },
      {
        id: 29,
        name: "Khánh Hoà",
        ma: "KHA",
        vung: 2,
        maDonVi: "258"
      },
      {
        id: 31,
        name: "Kon Tum",
        ma: "KTM",
        vung: 2,
        maDonVi: "260"
      },
      {
        id: 41,
        name: "Phú Yên",
        ma: "PYN",
        vung: 2,
        maDonVi: "257"
      },
      {
        id: 42,
        name: "Quảng Bình",
        ma: "QBH",
        vung: 2,
        maDonVi: "232"
      },
      {
        id: 43,
        name: "Quảng Nam",
        ma: "QNM",
        vung: 2,
        maDonVi: "235"
      },
      {
        id: 44,
        name: "Quảng Ngãi",
        ma: "QNI",
        vung: 2,
        maDonVi: "255"
      },
      {
        id: 46,
        name: "Quảng Trị",
        ma: "QTI",
        vung: 2,
        maDonVi: "233"
      },
      {
        id: 53,
        name: "Huế",
        ma: "HUE",
        vung: 2,
        maDonVi: "234"
      },
      {
        id: 64,
        name: "Đắc Nông",
        ma: "DNO",
        vung: 2,
        maDonVi: "261"
      }
    ]
  },
  {
    name: "Vùng: Miền Nam",
    id: 3,
    child: [
      {
        id: 1,
        name: "An Giang",
        ma: "AGG",
        vung: 3,
        maDonVi: "296"
      },
      {
        id: 2,
        name: "Bà Rịa Vũng Tàu",
        ma: "VTU",
        vung: 3,
        maDonVi: "254"
      },
      {
        id: 7,
        name: "Bến Tre",
        ma: "BTE",
        vung: 3,
        maDonVi: "275"
      },
      {
        id: 8,
        name: "Bình Dương",
        ma: "BDG",
        vung: 3,
        maDonVi: "274"
      },
      {
        id: 9,
        name: "Bình Phước",
        ma: "BPC",
        vung: 3,
        maDonVi: "271"
      },
      {
        id: 10,
        name: "Bình Thuận",
        ma: "BTN",
        vung: 3,
        maDonVi: "252"
      },
      {
        id: 11,
        name: "Bạc Liêu",
        ma: "BLU",
        vung: 3,
        maDonVi: "291"
      },
      {
        id: 13,
        name: "Cần Thơ",
        ma: "CTO",
        vung: 3,
        maDonVi: "292"
      },
      {
        id: 14,
        name: "Cà Mau",
        ma: "CMU",
        vung: 3,
        maDonVi: "290"
      },
      {
        id: 17,
        name: "Đồng Nai",
        ma: "DNI",
        vung: 3,
        maDonVi: "251"
      },
      {
        id: 18,
        name: "Đồng Tháp",
        ma: "DTP",
        vung: 3,
        maDonVi: "277"
      },
      {
        id: 28,
        name: "Tp Hồ Chí Minh",
        ma: "HCM",
        vung: 3,
        maDonVi: "28"
      },
      {
        id: 30,
        name: "Kiên Giang",
        ma: "KGG",
        vung: 3,
        maDonVi: "297"
      },
      {
        id: 35,
        name: "Lâm Đồng",
        ma: "LDG",
        vung: 3,
        maDonVi: "263"
      },
      {
        id: 36,
        name: "Long An",
        ma: "LAN",
        vung: 3,
        maDonVi: "272"
      },
      {
        id: 40,
        name: "Ninh Thuận",
        ma: "NTN",
        vung: 3,
        maDonVi: "259"
      },
      {
        id: 47,
        name: "Sóc Trăng",
        ma: "STG",
        vung: 3,
        maDonVi: "299"
      },
      {
        id: 50,
        name: "Tây Ninh",
        ma: "TNH",
        vung: 3,
        maDonVi: "276"
      },
      {
        id: 54,
        name: "Tiền Giang",
        ma: "TGG",
        vung: 3,
        maDonVi: "273"
      },
      {
        id: 55,
        name: "Trà Vinh",
        ma: "TVH",
        vung: 3,
        maDonVi: "294"
      },
      {
        id: 57,
        name: "Vĩnh Long",
        ma: "VLG",
        vung: 3,
        maDonVi: "270"
      },
      {
        id: 66,
        name: "Hậu Giang",
        ma: "HGI",
        vung: 3,
        maDonVi: "293"
      }
    ]
  }
];
