const collumns = {
    collumnsNV: [
        {
            fieldName: "ten_nv",
            headerText: "Tên nhân viên",
            allowSorting: false,
            width: "100",
            textAlign: 'center',
            autoFit: "true",
        },
        {
            fieldName: "so_dt",
            headerText: "Số điện thoại",
            allowSorting: false,
            width: "100",
            textAlign: 'center',
            autoFit: "true",
        },
        {
            fieldName: "nhiemvu",
            headerText: "Nhiệm vụ",
            allowSorting: false,
            textAlign: 'center',
            width: "100",
            autoFit: "true",
        },
        {
            fieldName: "ghichu",
            headerText: "Ghi chú",
            allowSorting: false,
            textAlign: 'center',
            autoFit: "true",
        },
    ],
    collumnsDVGT: [
        {
            fieldName: "ma_dvgt",
            headerText: "Mã DVGT",
            width: "100",
            autoFit: "true",
            textAlign: 'center',
        },
        {
            fieldName: "ten_dvgt",
            headerText: "Tên dịch vụ",
            width: "100",
            autoFit: "true",
            textAlign: 'center',
        },
        {
            fieldName: "kieu",
            headerText: "Kiểu yêu cầu",
            width: "100",
            autoFit: "true",
            textAlign: 'center',
        },
        {
            fieldName: "noidung",
            headerText: "Nội dung",
            allowSorting: false,
            autoFit: "true",
            textAlign: 'center',
        },
    ],
    collumns: [
        {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            allowSorting: false,
            width: 140
        },
        {
            fieldName: "ma_tb_cha",
            headerText: "Số chính",
            allowFiltering: true,
            allowSorting: false,
            width: 140
        },
        {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "diachi_ld",
            headerText: "Địa chỉ lắp đặt",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "ma_kv",
            headerText: "Mã khu vực",
            allowFiltering: true,
            allowSorting: false,
            width: 170
        },
        {
            fieldName: "ten_kv",
            headerText: "Tên khu vực",
            allowFiltering: true,
            allowSorting: false,
            width: 140
        },
        {
            fieldName: "ngay_yc",
            headerText: "Ngày YC",
            allowFiltering: true,
            allowSorting: false,
            width: 140
        },
        {
            fieldName: "ngaygiao",
            headerText: "Ngày giao",
            customAttributes: {class: 'ngaygiao'},
            allowFiltering: true,
            allowSorting: false,
            width: 140
        },
        {
            fieldName: "dv_hen",
            headerText: "Đơn vị hẹn",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 140
        },
        {
            fieldName: "goi_kt",
            headerText: "Gói KT",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 140
        },
        {
            fieldName: "gio_conlai",
            headerText: "Giờ còn lại",
            allowFiltering: true,
            allowSorting: false,
            width: 140
        },
        {
            fieldName: "chitieu_tg",
            headerText: "Chỉ tiêu TG",
            allowFiltering: true,
            allowSorting: false,
            width: 140
        },
        {
            fieldName: "giohen_tu",
            headerText: "Hẹn từ",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 140
        },
        {
            fieldName: "giohen_den",
            headerText: "Hẹn đến",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 140
        },
        {
            fieldName: "loaihinh_tb",
            headerText: "Loại hình",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 140
        },
        {
            fieldName: "ma_gd",
            headerText: "Mã GD",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 140
        },
        {
            fieldName: "thoigian_ck",
            headerText: "Thời gian cam kết",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 200
        },
        {
            fieldName: "ngay_ht",
            headerText: "Ngày bàn giao",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 170
        },
        {
            fieldName: "dv_giao",
            headerText: "Đơn vị giao",
            customAttributes: {class: 'donvigiao'},
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 170
        },
        {
            fieldName: "trangthai_in",
            headerText: "Trạng thái in",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 170
        },
        {
            fieldName: "ghichu",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: false,
            autoFit: "true",
            width: 140
        }
    ]
}

export default collumns
