export default {
    header: {
        title: "RESET CELL CỦA TỈNH",
        list: [
            {
                name: "Cập nhật KQTC",
                link: {
                    name: "Ui.cards"
                }
            },
            {
                name: "Thay đổi, khóa mở, thanh lý",
                link: {
                    name: "Ui.buttons"
                },
            },
            {
                name: "Reset Cell của tỉnh",
                link: {
                    name: "Ui.buttons"
                },
            },
        ],
    },
    isDisableTB: false,
    vma_tinh: null,
    userGP: null,
    donvi_id: null,
    Tag: null,
    ThHdId: null,
    initLoaiHdArr: null,
    initDVVTArr: null,
    position: { X: 'center', Y: 'top' },
    // cb 
    cbTinh: [],
    cbLoai_tb: [],

    // DONGBO_GPHONE
    DONGBO_GPHONE: {
        DONGBO: false,
    },

    form: {
        tinh: null,
        loai_tb: null,
        ghi_chu: null,
    },
    
    button: {
        thuc_hien: true,
        thoat: true
    },
    selectionOptions: { type: 'Multiple' },
    model: {
        hopdong: {
        },
        nhanvien:{

        }
    },
    loadingtable: {
        thuebao: false,
        hopdong: false,
    },
    isLoad: true,
    options: {
        hopdong: [
            
        ],
        nhanvien: [
            
        ]
    },
    config: {
        hopdong: [
            {
                headerText: 'Mã GD',
                allowSorting: true,
                fieldName: 'ma_gd'
            },
            {
                headerText: 'Số máy/Acc',
                allowSorting: true,
                fieldName: 'ma_tb'
            },
            {
                headerText: 'Tên thuê bao',
                allowSorting: true,
                fieldName: 'ten_tb'
            },
            {
                headerText: 'Địa chỉ LĐ',
                allowSorting: true,
                fieldName: 'dia_chi_ld'
            },
            {
                headerText: 'Ngày YC',
                allowSorting: true,
                fieldName: 'ngay_yc'
            },
            {
                headerText: 'Tiền',
                allowSorting: true,
                fieldName: 'tien'
            }
        ],
        nhanvien:[
            {
                headerText: 'Điện thoại LH',
                allowSorting: true,
                fieldName: 'ten_nv'
            },
            {
                headerText: 'Điện thoại',
                allowSorting: true,
                fieldName: 'dien_thoai'
            },
            {
                headerText: 'Nhiệm vụ',
                allowSorting: true,
                fieldName: 'nhiem_vu'
            },
            {
                headerText: 'Ghi chú',
                allowSorting: true,
                fieldName: 'ghi_chu'
            }
        ]
    },
    
}
