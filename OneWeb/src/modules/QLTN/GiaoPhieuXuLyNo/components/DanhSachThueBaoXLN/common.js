export const GridKhachHangCollumns = [{
        fieldName: "MA_XLN",
        headerText: "Mã KH",
        allowFiltering: false,
        isSum: true,
    },
    {
        fieldName: "TEN_KH",
        headerText: "Tên khách hàng",
        allowFiltering: false
    }
];
export const GridLichSuTTCollumns = [{
        fieldName: "THANHTOAN",
        headerText: "Mã thanh toán",
        isGroupedColumn: true
    },
    {
        fieldName: "THANGNO",
        headerText: "Tháng nợ",
        allowFiltering: false,
        cssClass: 'text-right'
    },
    {
        fieldName: "TONGNO",
        headerText: "Tổng nợ",
        allowFiltering: false,
        isSum: true,
        type: 'number',
        format: 'N0'
    }
];
export const Grid_DsthuebaoCollumns = [{
        fieldName: "MA_KH",
        headerText: "Mã KH",
        isGroupedColumn: true
    },
    {
        fieldName: "MA_TT",
        headerText: "Mã TT",
        allowFiltering: false
    },
    {
        fieldName: "MA_TB",
        headerText: "Số máy/Acc",
        allowFiltering: false
    },
    {
        fieldName: "TONGNO",
        headerText: "Tổng nợ",
        allowFiltering: false,
        isSum: true,
        type: 'number',
        format: 'N0'
    },
    {
        fieldName: "TEN_DVVT",
        headerText: "Dịch vụ",
        allowFiltering: false
    },
    {
        fieldName: "TRANGTHAI_TB",
        headerText: "Trạng thái",
        allowFiltering: false
    }
];
export const GridLichSuTBCollumns = [{
        fieldName: "MA_TB",
        headerText: "Số máy/Acc",
        isGroupedColumn: true
    },
    {
        fieldName: "THANGNO",
        headerText: "Tháng nợ",
        allowFiltering: false,
        cssClass: 'text-right'
    },
    {
        fieldName: "TONGNO",
        headerText: "Còn nợ",
        allowFiltering: false,
        isSum: true,
        type: 'number',
        format: 'N0'
    }
];
