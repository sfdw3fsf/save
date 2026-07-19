export default {
    docFile: false,
    huonggiaotn_id: null,
    quytrinh_id: null,
    phanvung_id: null,
    ten_kh: null,
    diachi_kh: null,
    ngaylap_hd: null,
    nhanvien_id: null,
    nguoi_cn: null,
    may_cn: null,
    ngay_cn: null,
    ip_cn: null,
    tthd: 0,
    vbaocao_hd_id: 808,
    TrangThaiHD:
    {
        K: 0,
        MOI: 1,
        THANH_TOAN: 2,
        DIEUHANH_THI_CONG: 3,
        DANG_THI_CONG: 4,
        DA_THI_CONG_XONG: 5,
        HOAN_THANH: 6,
        THOAITRA: 7,
        DA_LAY_DU_LIEU: 8,
        KHAIBAO_TONGDAI: 9,
        DANGCHO_HOANCONG: 10,
        DOISOAT_HOSO: 11,
        TTVT_XACMINH_NO: 14,
        THOAITRA_TMP: 17,
        TTVT_HOANCONG_DICHVU: 23,
        HTHS_BANCHEO: 26,
        NVKD_XACMINH: 27,
    },
    LoaiHopDong: {
        DATCOC_MOI : 31,
    },
    KieuLapDat:{
        DATCOC_MOI: 550
    },
    KHOANMUC_TT: {
        KMTT_DATCOC: 11,
    },
    header: {
        title: "Hoàn thiện hồ sơ đặt cọc theo file",
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
    donvi_id: null,
    position: { X: 'center', Y: 'top' },
    // thông tin thuê bao
    isChecked: false,
    cbDonViQL: [
    ],
    selectDonViQL: null,
    cbNhom: [],
    selectNhom: null,
    NHOM_LNV:
    {
        GIAODICHVIEN: 2,
        CONGTACVIEN: 6,
        CHAM_SOC_KH: 9,
        GIOITHIEU_HD: 11,
    },
    dichvuvt_id: 0,
    loaitb_id: 0,
    hdtb_id: 0,
    loai_hd_id: 31,
    trangthai_hd: 1,
    tt_nd: null,
    cbNhanVien:[],
    selectNV: null,
    doituong_id: null,
    vhdkh_id: null,
    ma_kh: null,
    khachhang_id: null,
    khlon_id: null,
    SELECT_DichVu_ID: null,
    SELECT_LOAI_HINH_ID: null,
    SELECTED_NHOM_COC: null,
    muccuoc_id: 0,
    tocdo_id: 0,
    kieuld_id: 0,
    doituong_id: 0,
    thuebao_dc_id: 0,
    thuebao_id: 0,
    ct_tienhd_id: 0,
    THANGBDMOI: 0,
    THANGKTMOI: 0,
    form: {
        duong_dan: null,
        txtMaGD: null,
        ngay_yc: null,
        so_may_acc: null,
        selectDichVu: null,
        selectLoaiHinh: null,
        ma_tb: null,
        ten_tb: null,
        diachi_tb: null,

        nhom_dat_coc: null,
        tra_truoc: null,
        chi_tiet_km: null,

        thang_MG: null,
        so_thang_MG: null,
        ty_le_tb: 0,
        ty_le_sd: 0,
        tien_tb: 0,
        tien_sd: 0,
        ghi_chu: null,

        thang_BD: null,
        so_thang: null,
        cuoc_DC: null,
        tien_thang: null,

    },
    listbox: {
        thamso: {
            list: [],
            value: {},
        },
        loaihd: {
            list: [],
            value: {},
        },
        dichvu: {
            list: [],
            value: {},
        },
        quytrinh: {
            list: [],
            value: {},
        },
        trangthaiphieu: {
            list: [{ id: 1, text: "Chưa giao" }, { id: 2, text: "Đã giao" }],
            value: {},
        },
        donvinhan: {
            list: [],
            value: {},
        },
        huonggiao: {
            list: [],
            value: {},
        },
        ds_phieuyeucau: {
            list: [],
            header: [{ fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150 },
            { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 200 },
            { fieldName: 'diachi_tb', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowHtml: true, width: 300 },
            { fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true, allowHtml: true, width: 300 },
            { fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true, allowHtml: true },
            { fieldName: '', headerText: 'Nội dung trả', allowFiltering: true, allowHtml: true },],
            value: {},
            isEnabled: true,
            checked: []
        },
    },

    cbDichVu: [],
    cbLoaiHinh: [],
    cbNhomDatCoc: [],
    cbTraTruoc: [],
    cbChiTietKM: [],
    
    button: {
        nhapmoi: true,
        visibleNhapmoi: true,
        ghilai: true,
        visibleGhilai: true,
        xoa: true,
        visibleXoa: true,
        huybo: true,
        visibleHuybo: true,
        thanhtoan: true,
        visibleThanhtoan: true,
        hoanthien: true,
        visibleHoanthien: true,
        phieuthu: true,
        visiblePhieuthu: true,
        lienhe: true,
        visibleLienhe: true,
        email: true,
        visibleEmail: true,
        thoat: true,
        visibleThoat: true,
        thang_bd: true
    },
    selectionOptions: { type: 'Multiple' },

    model: {
        hopdong: {
        },
        thuebao:{

        }
    },
    loadingtable: {
        thuebao: false,
        hopdong: false,
    },
    isLoad: true,
    options: {
        hopdong: [
            // {
            //     hdkh_id: 5501941,
            //     ma_gd: "HPG-KL/00530427"
            // },
            // {
            //     hdkh_id: 5501942,
            //     ma_gd: "HPG-KL/00530427"
            // }
        ],
        thuebao: [
            
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
                headerText: 'Tên KH',
                allowSorting: true,
                fieldName: 'ten_kh'
            },
            {
                headerText: 'Ngày YC',
                allowSorting: true,
                fieldName: 'ngay_yc'
            }
        ],
        thuebao:[
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
                headerText: 'Loại hình',
                allowSorting: true,
                fieldName: 'loaihinh_tb'
            },
            {
                headerText: 'Tháng bắt đầu',
                allowSorting: true,
                fieldName: 'thang_bd'
            },
            {
                headerText: 'Tháng kết thúc',
                allowSorting: true,
                fieldName: 'thang_kt'
            },
            {
                headerText: 'Cước đặt cọc',
                allowSorting: true,
                fieldName: 'cuoc_dc'
            },
            {
                headerText: 'Tiền ĐC/tháng',
                allowSorting: true,
                fieldName: 'tien_td'
            },
            {
                headerText: 'Người CN',
                allowSorting: true,
                fieldName: 'nguoi_cn'
            }
        ]
    },
    
}
