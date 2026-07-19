export const BreadcrumbTop = {
    title: "Giao phiếu xử lý nợ",
    list: [
        {
            name: "Quản lý thu nợ",
            link: {
                name: "Ui.buttons"
            }
        },
        {
            name: "Xử lý nợ",
            link: {
                name: "Ui.buttons"
            }
        },
        {
            name: "Giao phiếu xử lý nợ",
            link: {
                name: "Ui.buttons"
            }
        }
    ]
};
export const CollumnsPhieuChuaGiao = [
    {
        fieldName: "MANV_TC",
        headerText: "Nhân viên",
        allowFiltering: true,
        isGroupedColumn: true
    },
    {
        fieldName: "MA_XLN",
        headerText: "Mã XLN",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "MA_KH",
        headerText: "Mã KH",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "MATB_DD",
        headerText: "Mã đại diện",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TEN_TT",
        headerText: "Tên khách hàng",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "DIACHI_TT",
        headerText: "Địa chỉ khách hàng",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NGUOITAO",
        headerText: "Người tạo",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NGAYCHUYEN",
        headerText: "Ngày chuyển",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "BUOCCHUYEN",
        headerText: "Bước chuyển",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NGUOICHUYEN",
        headerText: "Người chuyển",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "SO_CV",
        headerText: "Số công văn",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TONGNO",
        headerText: "Nợ hiện tại",
        allowFiltering: true,
        format: "N0",
        type: "number",
        width: 130
    },
    {
        fieldName: "NO_GIAO",
        headerText: "Nợ giao",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        columnType: "numeric",
        editType: "numericedit",
        width: 130
    },
    {
        fieldName: "NO_LECH",
        headerText: "Nợ lệch",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 130
    },
    {
        fieldName: "THANGNO",
        headerText: "Tháng nợ",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TT_HS",
        headerText: "Tình trạng HS",
        allowFiltering: true,
        allowEditing: true,
        width: 130
    }
];
export const CollumnsDotLatSoLieu = [
    {
        fieldName: "STT",
        headerText: "STT",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 70
    },
    {
        fieldName: "TEN_NV",
        headerText: "Nhân viên",
        allowFiltering: true,
        isSum: true,
        sumTotal: true,
        width: 100
    },
    {
        fieldName: "SOLUONG",
        headerText: "Số lượng",
        allowFiltering: true,
        isSum: true,
        width: 100
    },
    {
        fieldName: "SL_CHUAGIAO",
        headerText: "Chưa giao",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        columnType: "numeric",
        editType: "numericedit",
        width: 100
    },
    {
        fieldName: "SL_CHUAXL",
        headerText: "Chưa XL",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 100
    },
    {
        fieldName: "SL_DAXL",
        headerText: "Đã XL",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 100
    },
    {
        fieldName: "SL_KETTHUC",
        headerText: "Kết thúc",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 100
    }
];
export const CollumnsPhieuHenTra = [
    {
        fieldName: "MA_XLN",
        headerText: "Mã XLN",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "MATB_DD",
        headerText: "Mã đại diện",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "MA_KH",
        headerText: "Mã kH",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TEN_KH",
        headerText: "Tên khách hàng",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "DIACHI_KH",
        headerText: "Địa chỉ khách hàng",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "SO_CV",
        headerText: "Số công văn",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TONG_NO",
        headerText: "Nợ hiện tại",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 130
    },
    {
        fieldName: "TONG_GIAO",
        headerText: "Nợ giao",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NO_LECH",
        headerText: "Nợ lệch",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 130
    },
    {
        fieldName: "NO_HENTRA",
        headerText: "Nợ hẹn trả",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 130
    },
    {
        fieldName: "NGAYHEN",
        headerText: "Ngày hẹn trả",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TT_HS",
        headerText: "Tình trạng HS",
        allowFiltering: true,
        allowEditing: true,
        width: 130
    }
];
export const CollumnsPhieuDaTra = [
    {
        fieldName: "NHANVIEN",
        headerText: "Nhân viên",
        allowFiltering: true,
        isGroupedColumn: true
    },
    {
        fieldName: "MA_XLN",
        headerText: "Mã XLN",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "MA_KH",
        headerText: "Mã KH",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "MATB_DD",
        headerText: "Mã đại diện",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TEN_KH",
        headerText: "Tên khách hàng",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "DIACHI_KH",
        headerText: "Địa chỉ khách hàng",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "SO_CV",
        headerText: "Số công văn",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "LAN_XLN",
        headerText: "Lần XLN",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TONGNO",
        headerText: "Nợ hiện tại",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TONG_GIAO",
        headerText: "Nợ giao",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NO_LECH",
        headerText: "Nợ lệch",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 130
    },
    {
        fieldName: "NO_HENTRA",
        headerText: "Tiền trả",
        allowFiltering: true,
        allowEditing: true,
        format: "N0",
        type: "number",
        editType: "numericedit",
        width: 130
    },
    {
        fieldName: "TRANGTHAI_XLN",
        headerText: "Trạng thái XLN",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NGAYHEN",
        headerText: "Ngày hẹn",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NGAYHAN",
        headerText: "Hạn trả",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "LYDO",
        headerText: "Lý do",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "GHICHU",
        headerText: "Ghi chú",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "THANGNO",
        headerText: "Tháng nợ",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "BUOC_XLN_NEXT",
        headerText: "Bước next",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NGAYCHUYEN",
        headerText: "Ngày chuyển",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NGAY_CN",
        headerText: "Ngày CN",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "NGUOI_CN",
        headerText: "Người CN",
        allowFiltering: true,
        width: 180
    },
    {
        fieldName: "MAY_CN",
        headerText: "Máy CN",
        allowFiltering: true,
        width: 130
    },
    {
        fieldName: "TT_HS",
        headerText: "Tình trạng HS",
        allowFiltering: true,
        allowEditing: true,
        width: 130
    },
    {
        fieldName: "THAOTAC",
        headerText: "Vật tư",
        allowFiltering: false,
        width: 100,
        cellCssClass: "css1",
        textAlign: "center",
        commands: [
            {
                type: "EDIT",
                buttonOption: {
                    // cssClass: "text-align: center !important",
                    iconCss: "yellow icon  nc-icon-glyph ui-1_edit-74"
                }
            }
        ]
    }
];
//Combo Loại nhân viên xử lý
export const LoaiNhanVienXuLy = [
    {
        LOAINV_ID: 39,
        LOAI_NV: "Nhân viên thu nợ"
    },
    {
        LOAINV_ID: 34,
        LOAI_NV: "Nhân viên pháp lý"
    }
];
export const KieuNo = [
    {
        KIEU_ID: 0,
        TENKIEU: "Nợ đầu kỳ"
    },
    {
        KIEU_ID: 5,
        TENKIEU: "Nợ phát sinh"
    },
    {
        KIEU_ID: 1,
        TENKIEU: "Tổng nợ"
    }
];
export const TrangThai = [
    {
        TRANGTHAI_ID: 0,
        TRANGTHAI: "Chưa giao"
    },
    {
        TRANGTHAI_ID: 1,
        TRANGTHAI: "Đã xử lý"
    }
];
export const CharacterLogic = [">", "=", ">="];
export const CharacterLogic2 = ["<", "<="];
export const TrangThaiLyDoTra = [
    {
        TRANGTHAI: 0,
        TRANG_THAI: "Đang xử lý"
    },
    {
        TRANGTHAI: 1,
        TRANG_THAI: "Đã xử lý"
    }
];
export const TrangThaiHienTaiDaGiao = [
    {
        TRANGTHAI_ID: 0,
        TRANGTHAI: "Chưa xử lý"
    },
    {
        TRANGTHAI_ID: 1,
        TRANGTHAI: "Đã xử lý"
    },
    {
        TRANGTHAI_ID: 3,
        TRANGTHAI: "Tất cả"
    }
];
export const PhieuHenDaGan = [
    {
        PHIEUHEN_ID: 1,
        PHIEUHEN: "Chỉ lấy phiếu hẹn trả"
    },
    {
        PHIEUHEN_ID: 2,
        PHIEUHEN: "Không lấy phiếu hẹn"
    }
];
export const ChieuKhoa = [
    {
        CHIEUKHOA_ID: 1,
        CHIEUKHOA: "Khóa 1 chiều gọi đi"
    },
    {
        CHIEUKHOA_ID: 2,
        CHIEUKHOA: "Khóa 2 chiều"
    },
    {
        CHIEUKHOA_ID: 0,
        CHIEUKHOA: "Khóa 1 chiều + khóa 2 chiều"
    }
];
export const CBONhanVienXLN = [
    {
        fieldName: "MA_NV",
        headerText: "Mã nhân viên"
    },
    {
        fieldName: "TEN_NV",
        headerText: "Tên nhân viên"
    }
];
export const CBOLanTaoDL = [
    {
        fieldName: "LANTAO_ID",
        headerText: "Lần tạo ID"
    },
    {
        fieldName: "NGAY_TAO",
        headerText: "Ngày tạo"
    },
    {
        fieldName: "NGUOI_CN",
        headerText: "Người tạo"
    },
    {
        fieldName: "SOLUONG",
        headerText: "Số lượng"
    },
    {
        fieldName: "TONGTIEN",
        headerText: "Tổng tiền"
    }
];
export const CollumnsHoSoXuLyGoc = [
    // {
    //     fieldName: "CHON",
    //     headerText: "Chọn",
    //     allowFiltering: false
    // },
    {
        fieldName: "MA_TB",
        headerText: "Số máy/Acc",
        allowFiltering: true
    },
    {
        fieldName: "TEN_TB",
        headerText: "Tên thuê bao",
        allowFiltering: true
    },
    {
        fieldName: "TEN_DVVT",
        headerText: "Dịch vụ",
        allowFiltering: true
    },
    {
        fieldName: "DOITUONG",
        headerText: "Đối tượng",
        allowFiltering: true
    },
    {
        fieldName: "TRANGTHAI_TB",
        headerText: "Trạng thái TB",
        allowFiltering: true
    },
    {
        fieldName: "TRANGTHAI",
        headerText: "Trạng thái",
        allowFiltering: true
    }
];
export const ExcelImport = [
    {
        fieldName: "MA_XLN"
    },
    {
        fieldName: "LYDO_ID"
    },
    {
        fieldName: "GHICHU"
    }
];
