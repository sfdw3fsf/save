export default {
  cols: [
    {
      fieldName: "tinh",
      headerText: "Tỉnh",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "ma_tb",
      headerText: "Số máy/Acc",
      allowFiltering: true,
      allowSorting: false,

    },{
      fieldName: "ma_bh",
      headerText: "Mã báo hỏng",
      allowFiltering: true,
      allowSorting: false,
      visible: true
    },
    {
      fieldName: "ma_lt",
      headerText: "Số ảo",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "loaihinh_tb",
      headerText: "Loại hình",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "ht_port",
      headerText: "TT Port",
      allowFiltering: true,
      allowSorting: false,

    },

    {
      fieldName: "tinhtrang",
      headerText: "Tình trạng",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ten_tb",
      headerText: "Tên thuê bao",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "nguoi_bh",
      headerText: "Người BH",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "dienthoai_lh",
      headerText: "Liên hệ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ngay_bh",
      headerText: "Ngày BH",
      allowFiltering: true,
      allowSorting: false,
      textAlign: 'right'
    },
    {
      fieldName: "goi_kt",
      headerText: "Gói KT",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "chitieu_tg",
      headerText: "Chỉ tiêu TG",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "thoigian_ck",
      headerText: "Thời gian CK",
      allowFiltering: true,
      allowSorting: false,
      visible: false
    },
    {
      fieldName: "ten_ttvt",
      headerText: "Trung tâm VT",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "tram_vt",
      headerText: "Trạm VT",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "diachi_ld",
      headerText: "Địa chỉ LĐ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ngay_sd",
      headerText: "Ngày SD",
      allowFiltering: true,
      allowSorting: false,
      textAlign: 'right',
      format: {type: 'date', format: 'dd/MM/yyyy'},
      type: 'date',
    },
    {
      fieldName: "ghichu_hong",
      headerText: "Ghi chú hỏng",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "tt_ph",
      headerText: "Trạng thái phiếu",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ten_nv",
      headerText: "Người nhận",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "dv_nhan",
      headerText: "Đv nhận",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "nd_tra",
      headerText: "Nội dung trả",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "luot_bh",
      headerText: "Lượt BH",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "lan_pa",
      headerText: "KH gọi",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ten_loaikh",
      headerText: "Loại KH",
      allowFiltering: true,
      allowSorting: false,
      visible: false
    },
    {
      fieldName: "ten_plkh",
      headerText: "Phân loại KH",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "cuoc_ps",
      headerText: "Cước PS",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ngaytra",
      headerText: "Ngày trả",
      allowFiltering: true,
      allowSorting: false,
      format: {type: 'date', format: 'dd/MM/yyyy'},
      type: 'date',
    },
    {
      fieldName: "nv_giu",
      headerText: "Người giữ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "daucuoi",
      headerText: "ĐIỂM",
      allowFiltering: true,
      allowSorting: false,
    },
  ],

  contextMenuItems: [
    {text: 'Hiển thị TT kỹ thuật', id: 'dsbhHienThiTTKT', target: '.e-content', iconCss: 'icon one-td-settings text-primary',},
    {text: 'Lưu TT Port', id: 'dsbhLuuTTPort', target: '.e-content', iconCss: 'icon one-save text-primary',},
    {text: 'Khóa DS Phiếu', id: 'dsbhKhoaDSPhieu', target: '.e-content', iconCss: 'icon one-file-lock text-danger',},
    {text: 'Show port', id: 'dsbhShowPort', target: '.e-content', iconCss: 'icon one-clipart-arrow-right text-primary',},
    {text: 'Test port', id: 'dsbhTestPort', target: '.e-content', iconCss: 'icon nc-icon-glyph arrows-1_simple-up text-primary',},
    {text: 'Xóa DS Phiếu', id: 'dsbhXoaDSPhieu', target: '.e-content', iconCss: 'icon one-trash text-danger'},
    {text: 'CN Chờ xử lý', id: 'dsbhChoXuLy', target: '.e-content', iconCss: 'icon one-dots text-warning'},
    {text: 'Cập nhật TT Kênh trắng', id: 'dsbhTTKenhTrang', target: '.e-content', iconCss: 'icon one-save text-primary'},
    {text: 'Giữ phiếu', id: 'dsbhGiuPhieu', target: '.e-content', iconCss: 'icon one-key text-primary'},
    {text: 'Hủy giữ phiếu', id: 'dsbhHuyGiuPhieu', target: '.e-content', iconCss: 'icon one-cancel text-danger'},
  ]
}
