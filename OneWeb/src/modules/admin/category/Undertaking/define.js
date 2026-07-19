import moment from "moment";

export default {
    header: {
        title: "QUẢN LÝ CHỦ TRƯƠNG",
        list: [{
            name: "Dữ liệu",
            link: {
                name: "Ui.cards"
            }
        },
        
        {
            name: "Danh mục QLVT",
            link: {
                name: "Ui.cards"
            },
        },
        {
            name: "Chủ trương",
            link: {
                name: "Ui.buttons"
            },
        },
        ],
    },
    ngay_cn: null,
    may_cn: null,
    nguoi_cn: null,

    yearFormat: 'YYYY',
    typeFormatYear: 'year',

    dateFormat: 'DD/MM/YYYY HH:mm:ss',
    dateFormatMonth: 'MM/YYYY',
    typeFormat: 'datetime',
    typeFormatMonth: 'month',
    dateConfig: {
      dateFormat: 'd/m/Y',
      altFormat: 'd/m/Y'
    },
    showTimePanel: false,
    txtChutruong: null,
    txtSocongvan:null,
    txtKyhieu: null,
    txtGhichu: null,
    txtNgayQD: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
    CHU_TRUONG_ID: null,
    danhsachfile:[],
    txtNam: null,
    year: "",
    ip: "",
    isAddNew: false,
    success_code: "BSS-00000000",
    current: {
        chutruongtbd: null,
        socongvan: null,
        kyhieu: null,
        ghichu: null,
        ngayqd: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        nam: null
    },
    button: {
        nhapmoi: false,
        huybo: false,
        xoa: false,
        ghilai: false,
        trogiup: false
    },
    options: {
        danh_sach_chu_truong: []
    },
    columns_ds_chu_truong: [
        {
            fieldName: "chutruong",
            headerText: "Tên chủ trương",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "nam",
            headerText: "Năm",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "so_cv",
            headerText: "Số văn bản",
            allowFiltering: true,
            width: 'auto'
        },
        {
            fieldName: "kyhieu",
            headerText: "Ký hiệu",
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
        },
        {
            fieldName: "ngay_qd",
            headerText: "Ngày QĐ",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        }
    ],

}