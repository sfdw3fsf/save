export default {
    header: {
        title: "DANH MỤC TỔ CHỨC QUẢN LÝ DỰ ÁN",
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
    button: {
        layTT: false,
        nhapmoi: false,
        ghilai: false,
        huy: false,
        xoa: false
    },
    cbophanloai: [],
    cbonganhang: [],
    txtmaTCQLDA: null,
    txtSDT: null,
    txtchucvu: null,
    txtsotaikhoan: null,
    txttenTCQLDA: null,
    txtnguoilienhe:null,
    txtemail: null,
    txtmasothue: null,
    txtdiachi: null,
    txtghichu: null,
    ip_cn: null,
    CHU_DAU_TU_ID: null,
    success_code: "BSS-00000000",

    current: {
        phanloaitbd: null,
        nganhang: null,
        tenchudautu: null,
        machudautu: null,
        sdt: null,
        chucvu: null,
        sotaikhoan: null,
        nguoilienhe: null,
        email: null,
        masothue: null,
        diachi: null,
        ghichu: null        
    },

    form: {
        phanloai: null,
        nganhang: null,
    },

    options: {
        griddanhsachTCQLDA: []
    },

    columns_danhsach: [
        {
            fieldName: "ma_chudtu",
            headerText: "Mã TCQLDA",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "ten_chudtu",
            headerText: "Tên TCQLDA",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "nguoi_lh",
            headerText: "Người liên hệ",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "so_dt",
            headerText: "Số điện thoại",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "taikhoan",
            headerText: "Số tài khoản",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "ten_nh",
            headerText: "Ngân hàng",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "diachi",
            headerText: "Địa chỉ",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "ghichu",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        }
        
    ]

    
}