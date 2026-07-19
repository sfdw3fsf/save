export const DanhSachVatTuChungTuCollumn = [
    {
        fieldName: "LOHANG",
        headerText: "Lô hàng",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "MA_VT",
        headerText: "Mã vật tư",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "TEN_VT",
        headerText: "Tên vật tư",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "DVI_TINH",
        headerText: "ĐVT",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "SOLUONG",
        headerText: "Số lượng",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130,
        format: "N0",
        type: "number"
    },
    {
        fieldName: "SL_CHON",
        headerText: "SL Đã chọn",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130,
        format: "N0",
        type: "number"
    },
    {
        fieldName: "DONGIA",
        headerText: "Đơn giá",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130,
        format: "N0",
        type: "number"
    },
    {
        fieldName: "TIEN",
        headerText: "Thành tiền",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130,
        format: "N0",
        type: "number"
    },
    {
        fieldName: "SOTHANG_PB",
        headerText: "Số tháng PB",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "LOAI_TBI",
        headerText: "Loại thiết bị",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "btnXoaVT",
        headerText: "Xóa",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "HAN_BH",
        headerText: "Hạn BH",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "MA_HD",
        headerText: "Mã HĐ",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "DUAN",
        headerText: "Dự án",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "MA_TH",
        headerText: "Đợt TH",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "GHICHU_HD",
        headerText: "Ghi chú HĐ",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "btnEdit",
        headerText: "Cuộn cáp",
        allowFiltering: true,
        isPrimaryKey: true,
        width: 130
    },
    {
        fieldName: "THAOTAC",
        headerText: "  ",
        allowFiltering: false,
        width: 40,
        commands: [
            {
                type: "EDIT",
                buttonOption: {
                    cssClass: "",
                    iconCss: "icon nc-icon-glyph ui-1_edit-74"
                }
            }
        ]
    }
];