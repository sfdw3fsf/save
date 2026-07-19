const collumns = {
    collumnsLNV: [
        {
            fieldName: "loainv_id",
            visible: false
        },
        {
            fieldName: "ten",
            headerText: "Loại NV",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
    ],
    collumnsLKH: [
        {
            fieldName: "loaikh_id",
            visible: false
        },
        {
            fieldName: "ten_nhom",
            headerText: "Nhóm khách hàng",
            isGroupedColumn: true
        }, 
        {
            fieldName: "ma_loaikh",
            headerText: "Mã loại",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            width: "100px",
        },
        {
            fieldName: "ten_loaikh",
            headerText: "Loại KH",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
    ],
    collumnsNN: [
        {
            fieldName: "nganhnghe_id",
            visible: false
        },
        {
            fieldName: "manganh",
            headerText: "Mã ngành",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            width: "100px",
        },
        {
            fieldName: "tennganhnghe",
            headerText: "Tên ngành nghề",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
    ],
    collumnsDT: [
        {
            fieldName: "loainv_id",
            visible: false
        },
        {
            fieldName: "doanhthu_tu",
            headerText: "Doanh thu từ",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'right',
            autoFit: "true",
        },
        {
            fieldName: "doanhthu_den",
            headerText: "Doanh thu đến",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'right',
            autoFit: "true",
        },
    ],
    collumnsDTN: [
        {
            fieldName: "loainv_id",
            visible: false
        },
        {
            fieldName: "diemtn_tu",
            headerText: "Điểm tín nhiệm từ",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'right',
            autoFit: "true",
        },
        {
            fieldName: "diemtn_den",
            headerText: "Điểm tín nhiệm đến",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'right',
            autoFit: "true",
        },
    ],
    collumnsNV: [
        {
            fieldName: "nhanvien_id",
            visible: false
        },
        {
            fieldName: "STT",
            headerText: "STT",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'center',
            width: "100px",
        },
        {
            fieldName: "ma_nv",
            headerText: "Mã nhân viên",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "ma_the",
            headerText: "Mã thẻ",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "ma_dv",
            headerText: "Mã đơn vị",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "ten_dv",
            headerText: "Tên nhân viên",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "chucdanh",
            headerText: "Chức danh",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "vitri",
            headerText: "Vị trí",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "so_dt",
            headerText: "Điện thoại",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "diachi_nv",
            headerText: "Địa chỉ",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "vitri_id",
            headerText: "Mã vị trí",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
    ]
}

export default collumns
