export default {
    header: {
        title: "ĐỌC FILE CHUYỂN HỢP ĐỒNG DÙNG THỰC",
        list: [{
            name: "Lập hợp đồng",
            link: {
                name: "Ui.cards"
            }
        },
        {
            name: "Lắp đặt mới",
            link: {
                name: "Ui.buttons"
            },
        },
        ],
    },
    tt_nd: null,
    toolbarOptions: ['ExcelExport'],
    phanvung_id: null,
    nguoidung_id: null,
    position: { X: 'center', Y: 'top' },
    // cb 
    cbDichVu: [],
    cbTrangThai: [],
    cbLoaiHinh: [],
    cbLoaiHinhCommon: [],
    cbTrangThaiTB: [],
    cbKieuYC: [],
    cbTram: [],

    form: {
        duongdan: null,

    },
    
    button: {
        capnhat: true,
        xuatexcel: true
    },
    selectionOptions: { type: 'Multiple' },
    model: {
        hopdong: {
        },
    },
    options: {
        hopdong: [
        ],
        loi: [
            // {
            //     ma_tb: 'aaa',
            //     doituong_id: 'aaa',
            //     ngay_nt: 'aa',
            //     lydo: 'aa',
            // } 
        ],
    },
    config: {
        hopdong: [
            {
                headerText: 'Mã TB',
                allowSorting: true,
                fieldName: 'ma_tb'
                , autoFit: "true"
            },
            {
                headerText: 'Đối tượng',
                allowSorting: true,
                fieldName: 'doituong_id'
                , autoFit: "true"
            },
            {
                headerText: 'Ngày NT',
                allowSorting: true,
                fieldName: 'ngay_nt'
                , autoFit: "true"
            },
            {
                headerText: 'Loại hình TB',
                allowSorting: true,
                fieldName: 'loaihinh_tb'
                , autoFit: "true"
            }
        ],
        LOI: [
            {
                headerText: 'Lý do lỗi',
                allowSorting: true,
                fieldName: 'trangthai'
                , autoFit: "true"
            },
            {
                headerText: 'Mã thuê bao',
                allowSorting: true,
                fieldName: 'ma_tb'
                , autoFit: "true"
            },
            {
                headerText: 'Đối tượng',
                allowSorting: true,
                fieldName: 'doituong_id'
                , autoFit: "true"
            },
            {
                headerText: 'Ngày NT',
                allowSorting: true,
                fieldName: 'ngay_nt'
                , autoFit: "true"
            }
        ]
    },
    
}
