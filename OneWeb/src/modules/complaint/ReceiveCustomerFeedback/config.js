import APIHandle from "./APIHandle";
import moment from "moment";
import {TrangThaiHD as TRANGTHAI_PH} from "../../../utils/Enum";

export default {
  colsChiTietNo: [
    {fieldName: "tenkhoanmuc", headerText: "Tên khoản mục", allowFiltering: true, allowSorting: false, width: 150},
    {
      fieldName: "tongnothucte",
      headerText: "Tổng nợ",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
      format: 'N',
      type: 'number'
    },
    {
      fieldName: "tongsetradk",
      headerText: "Cộng nợ ĐK",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
      format: 'N',
      type: 'number'
    },
    {
      fieldName: "tongsetraps",
      headerText: "Cộng nợ PS",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
      format: 'N',
      type: 'number'
    },
    {
      fieldName: "tongsephaitra",
      headerText: "Còn nợ",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
      format: 'N',
      type: 'number'
    },
    {
      fieldName: "tongthuctra",
      headerText: "Tổng đã trả",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
      format: 'N',
      type: 'number'
    },
    {
      fieldName: "tongthucdc",
      headerText: "Đã điều chỉnh",
      allowFiltering: true,
      allowSorting: false,
      width: 150,
      format: 'N',
      type: 'number'
    },
  ],

  colsChuKyNo: [
    {fieldName: "sl", headerText: "Bộ phận xử lý", allowFiltering: true, allowSorting: false, visible: false},
    {fieldName: "tenchude", headerText: "Chủ đề khiếu nại", allowFiltering: true, allowSorting: false},
    {fieldName: "nd_khieunai", headerText: "Nội dung KN", allowFiltering: true, allowSorting: false},
    {fieldName: "nhanvien_tn", headerText: "Người tiếp nhận", allowFiltering: true, allowSorting: false},
    {
      fieldName: "ngay_tn",
      headerText: "Ngày tiếp nhận",
      allowFiltering: true,
      allowSorting: false,
      format: 'dd/MM/yyyy HH:mm:ss',
      type: 'date',
      textAlign: 'right'
    },
    {fieldName: "nhanvien_gq", headerText: "Người giải quyết", allowFiltering: true, allowSorting: false},
    {
      fieldName: "ngay_gq",
      headerText: "Ngày giải quyết",
      allowFiltering: true,
      allowSorting: false,
      format: 'dd/MM/yyyy HH:mm:ss',
      type: 'date',
      textAlign: 'right'
    },
    {fieldName: "khieunai_id", headerText: "Khiếu nại ID", allowFiltering: true, allowSorting: false, visible: false},
    {fieldName: "ma_kn", headerText: "Mã khiếu nại", allowFiltering: true, allowSorting: false},
    {fieldName: "trangthai_kn", headerText: "Trạng thái KN", allowFiltering: true, allowSorting: false},
  ],

  colsTienGC: [
    {fieldName: "ischeck", headerText: "ischeck", allowFiltering: true, allowSorting: false, visible: false},
    {fieldName: "khieunai_id", headerText: "khieunai_id", allowFiltering: true, allowSorting: false, visible: false},
    {fieldName: "thang_gc", headerText: "Tháng GC", allowFiltering: true, allowSorting: false, width: 120},
    {fieldName: "ky_gc", headerText: "Kỳ giảm", allowFiltering: true, allowSorting: false, width: 120},
    {fieldName: "tien", headerText: "Tiền GC", allowFiltering: true, allowSorting: false, width: 120},
    {fieldName: "tien_gc", headerText: "Tiền", allowFiltering: true, allowSorting: false, width: 120},
    {fieldName: "vat_gc", headerText: "Thuế", allowFiltering: true, allowSorting: false, width: 120},
    {fieldName: "delete_cmd", headerText: "Xóa", allowFiltering: true, allowSorting: false, width: 80},
  ],

  colsDsKhieuNai: [
    {fieldName: "ma_kn", headerText: "Mã phản hồi", allowFiltering: true, allowSorting: false},
    {fieldName: "ma_tb", headerText: "Số máy/Acc", allowFiltering: true, allowSorting: false},
    {fieldName: "nguoi_kn", headerText: "Người KN", allowFiltering: true, allowSorting: false},
    {fieldName: "tenchude", headerText: "Tên chủ đề", allowFiltering: true, allowSorting: false},
    {fieldName: "dienthoai_lh", headerText: "Điện thoại LH", allowFiltering: true, allowSorting: false},
    {fieldName: "ten_nv", headerText: "Người tiếp nhận", allowFiltering: true, allowSorting: false},
    {
      fieldName: "ngay_tn",
      headerText: "Ngày TN",
      allowFiltering: true,
      allowSorting: false,
      format: 'dd/MM/yyyy HH:mm:ss',
      type: 'date',
      textAlign: 'right'
    },
    {
      fieldName: "ngaytra",
      headerText: "Ngày trả",
      allowFiltering: true,
      allowSorting: false,
      format: 'dd/MM/yyyy HH:mm:ss',
      type: 'date',
      textAlign: 'right'
    },
    {fieldName: "nguoitra", headerText: "Người trả", allowFiltering: true, allowSorting: false},
    {fieldName: "nd_tra", headerText: "Nội dung trả", allowFiltering: true, allowSorting: false},
    {fieldName: "email", headerText: "Email", allowFiltering: true, allowSorting: false},
  ],

  KHIEUNAI_ROW: () => {
    return {
      KHIEUNAI_ID: null,
      THUEBAO_ID: null,
      DICHVUVT_ID: null,
      LOAITB_ID: null,
      NGAY_TN: null,
      NGUOI_KN: null,
      DIENTHOAI_LH: null,
      CHUDEKN_ID: null,
      ND_KHIEUNAI: null,
      TTKN_ID: null,
      DONVI_ID: null,
      NHANVIEN_ID: null,
      NGAY_GQ: null,
      NHANVIEN_GQ_ID: null,
      NOIDUNG_GQ: null,
      HENGQ_TU: null,
      HENGQ_DEN: null,
      ND_TON: null,
      GHICHU_KN: null,
      MAY_CN: null,
      NGAY_CN: null,
      NGUOI_CN: null,
      CONGVAN_XL: null,
      NGAY_BN: null,
      CONGVAN_BN: null,
      CMT: null,
      NGAYCAP: null,
      NOICAP: null,
      HTKN_ID: null,
      NGUYENNHAN_KN: null,
      DIACHI_LH: null,
      MA_TB: null,
      QUYTRINH_ID: null,
      KETQUAKN_ID: null,
      THANHTOAN_ID: null,
      MA_KN: null,
      KY_KN: null,
      MA_TT: null,
      MA_KH: null,
      EMAIL: null,
    }
  },

  KHIEUNAI_DV: () => {
    return {
      KHIEUNAI_ID: null,
      LOAIDV_ID: null,
      DONVI_ID: null,
    }
  },

  GIAOPHIEU_KN: () => {
    return {
      "PHIEUKN_ID": null,
      "KHIEUNAI_ID": null,
      "NGAYGIAO": null,
      "ND_GIAO": null,
      "DONVI_GIAO_ID": null,
      "NHANVIEN_GIAO_ID": null,
      "DONVI_NHAN_ID": null,
      "NHANVIEN_TH_ID": null,
      "PHIEU_CHA_ID": null,
      "HUONGGIAO_ID": null,
      "TTKN_ID": null,
      "GHICHU_TH": null,
      "TTPH_ID": null,
      "NHANVIEN_TRA_ID": null,
      "NGAYTRA": null,
      "NGAY_TH": null,
      "MAY_CN": null,
      "NGUOI_CN": null,
      "NGAY_CN": null,
      "ND_TRA": null,
      "LYDO_TON": null,
      "SOCONGVAN": '',
      "ND_XACMINH": null,
      "CHITIEU_TG": null,
      "THOIGIAN_CK": null,
      "MOTA": null,
      "SONGAY_TN": null,
      "THOIGIAN_TN": null,
      "LYDOTRA_CON_ID": null,
      "ND_TRA_CON": null,
      "NGAYTRA_CON": null,
      "DONVITRA_CON_ID": null,
      "NHANVIENTRA_CON_ID": null,
      "NHANVIEN_GQ_ID": null
    }
  }

}
