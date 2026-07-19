export default {
    header: {
        title: "BÁN THIẾT BỊ - TTVT THỰC HIỆN",
        list: [{
            name: "Lập hợp đồng",
            link: {
                name: "Ui.cards"
            }
        },
        {
            name: "Bán thiết bị  - TTVT trang bị",
            link: {
                name: "Ui.buttons"
            },
        },
        ],
    },
    map: {
        lat: '',
        long: ''
    },
    DichVuVienThong:
    {
        TATCA: -1,
        CO_DINH: 1,
        DI_DONG: 2,
        ADSL: 4,
        DD_TRATRUOC: 5,
        EMAIL: 6,
        METRONET: 7,
        MEGAWAN: 8,
        TSL: 9,
        GPHONE: 10,
        IMS: 11,
        MEGA_EYES: 12,
        HOINGHI_TRUYENHINH: 13,
        ANTOAN_BAOMAT_TT: 14,
        DICHVU_CNTT: 15,
        TRUNGTAM_DULIEU: 16,
        HATANG_VIENTHONG: 19,

        IMS_DOITAC: 21,
        BRCD_DOITAC: 22,
        MGW_DOITAC: 23,
        TSL_DOITAC: 24,
    },
    dataTienKM: {
        ds: [],
        cols: [
            // {
            //     headerText: 'Khoản mục',
            //     allowSorting: true,
            //     fieldName: 'khoanmuctt_id'
            //     , autoFit: "true"
            // },
            {
                headerText: 'Khoản mục',
                allowSorting: true,
                fieldName: 'khoanmuctt'
                , autoFit: "true"
            },
            {
                headerText: 'Tổng tiền',
                allowSorting: true,
                fieldName: 'tongtien'
                , autoFit: "true"
            },
            {
                headerText: 'Tiền',
                allowSorting: true,
                fieldName: 'tien'
                , autoFit: "true"
            },
            {
                headerText: 'Vat',
                allowSorting: true,
                fieldName: 'vat'
                , autoFit: "true"
            },
            {
                headerText: 'Tỷ lệ Vat (%)',
                allowSorting: true,
                fieldName: 'tyle_vat'
                , autoFit: "true"
            }
        ],
        keycol: '',
        tieude: 'Chi tiết tiền khoản mục',
        tieudebang: 'Chi tiết khoản mục thanh toán'
    },
    dataDinhKem:{
        ds: [],
        cols: [
            {
                headerText: 'Mã TT',
                allowSorting: true,
                fieldName: 'ma_tt'
                , autoFit: "true"
            },
            {
                headerText: 'Số máy/Acc',
                allowSorting: true,
                fieldName: 'ma_tb'
                , autoFit: "true"
            },
            {
                headerText: 'Địa chỉ thuê bao',
                allowSorting: true,
                fieldName: 'diachi_tb'
                , autoFit: "true"
            },
            {
                headerText: 'Địa chỉ lắp đặt',
                allowSorting: true,
                fieldName: 'diachi_ld'
                , autoFit: "true"
            },
            {
                headerText: 'Loại hình',
                allowSorting: true,
                fieldName: 'loai_hinh'
                , autoFit: "true"
            },
            {
                headerText: 'Ngày SD',
                allowSorting: true,
                fieldName: 'ngay_sd'
                , autoFit: "true"
            },
            {
                headerText: 'Trạng thái',
                allowSorting: true,
                fieldName: 'trang_thai'
                , autoFit: "true"
            },
            {
                headerText: 'Doanh thu',
                allowSorting: true,
                fieldName: 'doanh_thu'
                , autoFit: "true"
            },
            {
                headerText: 'Gói nâng cấp',
                allowSorting: true,
                fieldName: 'goi_nc'
                , autoFit: "true"
            }
        ],
        keycol: '',
        tieude: 'Danh sách thuê bao đính kèm',
        tieudebang: 'Danh sách thuê bao',
        allowExit: true,
        allowAccept: true,
        
    },
    modeMap: 0,
    loai_hd_id: 15,
    trangthai_hd: 1,
    ngay_yc: '',
    DS_VatTu:[],
    ip: '1.1.1.1',
    tt_nd: null,
    dataDevice: {},
    dataChooseDevice: {},
    DS_ThueBao: [],
    dsCTM_TBI: [],
    hdtb_id: 0,
    hdkh_id: 0,
    ds_tien_khoanmuc: [],
    tocdo_id: 0,
    muccuoc_id: 0,
    diachiLD: [],
    doituong_id: 0,
    thuebao_id: 0,
    khachhang_id: 0,
    ds_dbkh: [],
    loading: false,
    nhanvien_id: null,
    donvi_id: null,
    Tag: null,
    ThHdId: null,
    initLoaiHdArr: null,
    initDVVTArr: null,
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
        // ngày lập HD
        ngay_yc: '',
        tong_tien: 0,
    },
     // THONG TIN KHÁCH HÀNG
    TTTT: {
        ma_gd:null,
        ngay_yc: '',
        ma_hd: null,
        ma_tb: null,
        dia_chi: null,
        dich_vu: null,
        ghi_chu: null,
        gia_goi: null,
        
        ma_kh: null,
        ten_kh: null,
        ten_tb: null,
        diachi_ld: null,
        loai_hinh: null,
        trang_thaitb: null,
        kieu_yc: null,
    },

    // THONG TIN THIET BI
    TBI: {
        txtareaTBI: [],
        tong_tien: null,
        vat: null,
        txtareaVatTu: null
    },

    // THONG TIN ĐÍNH KÈM
    TTDK: {
        txtareaTB: [],
        tong_doanh_thu: null,
    },
    // THONG TIN DON VI
    TTDV:{
        tram_thicong: null
    },
    button: {
        nhapmoi: false,
        ghilai: true,
        huy: true,
        xoaHD: true,
        thanhtoan: true
    },
    selectionOptions: { type: 'Multiple' },
    model: {
        hopdong: {
        },
        
    },
    loadingtable: {
        hopdong: false,
    },
    isLoad: true,
    options: {
        hopdong: [
        ]
    },
    config: {
        hopdong: [
            {
                headerText: 'Mã Thuê bao',
                allowSorting: true,
                fieldName: 'ma_tb'
            , autoFit: "true" },
            {
                headerText: 'Tên thuê bao',
                allowSorting: true,
                fieldName: 'ten_tb'
            , autoFit: "true" },
            {
                headerText: 'Loại hình',
                allowSorting: true,
                fieldName: 'loaihinh_tb'
            , autoFit: "true" }
        ],
    },
    
}
