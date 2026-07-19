export default {
    header: {
        title: "ĐỌC FILE NHẮN TIN THÔNG BÁO CƯỚC",
        list: [
            {
                name: "Đọc file nhắn tin thông báo cước",
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
    cboLoaiYC: {value: 0, Items: []},
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
        laytt: true,
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
                headerText: 'MA_TT',
                allowSorting: true,
                fieldName: 'MA_TT'
                , autoFit: "true"
            },
            {
                headerText: 'SO_DT',
                allowSorting: true,
                fieldName: 'SO_DT'
                , autoFit: "true"
            },
        ],
        LOI: [
            {
                headerText: 'Lý do lỗi',
                allowSorting: true,
                fieldName: 'TRANGTHAI'
                , autoFit: "true"
            },
            {
                headerText: 'MA_TT',
                allowSorting: true,
                fieldName: 'MA_TT'
                , autoFit: "true"
            },
            {
                headerText: 'SO_DT',
                allowSorting: true,
                fieldName: 'SO_DT'
                , autoFit: "true"
            },
        ]
    },
    
}
