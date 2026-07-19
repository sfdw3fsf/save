export default {
    header: {
        title: "QUẢN LÝ GIAO NHẬN HỒ SƠ: Hoàn thiện hồ sơ",
        list: [{
            name: "Hợp đồng",
            link: {
                name: "Ui.cards"
            }
        },
        {
            name: "Hồ sơ",
            link: {
                name: "Ui.buttons"
            },
        },
            {
                name: "QLK hoàn thiện",
                link: {
                    name: "Ui.buttons"
                },
            },
        ],
    },
    dsDVTem: [],
    nuthc: false,
    nutgp: false,
    nhanvien_id: null,
    thaotac: [],
    tths_id: 4,
    quytrinh_id: 550,
    huonggiao_id: 0,
    donViTree: [],
    tt_doituong_hs: {
        fieldsDonVi: ''
    },
    // combobox
    cbloHS: [],
    cbKieuGiao: [],
    cbdvGiao: [],
    cbTrangThai: [],
    cbNhomFile: [],
    cbLoaiFile: [],

    // BUTTON
    button: {
        themfile: false
    },

    // THONG TIN VE BO HO SO
    tt_bo_hs: {
        // thong tin tim kiem
        kieu_giao: '-1',
        so_lo_hs: null,
        ma_gd: null,
        dv_giao: null,
        trang_thai: '1',
        giao_tungay: '',
        ischkTungay: false,
        denngay: '',

        // thong tin bo ho so
        ischkTuDong: true,
        solohs: null,
        makh: null,
        trangthai: null,
        tenkh: null,
        dvgiao: null,
        magd: null,
        nd_tra: null,
    },

    // THONG TIN FILE
    tt_file: {
        nhom_file: null,
        loai_file: null,
        duong_dan: null,
        ghichu: null,
    },
    model: {
        thuebao: {

        },
        hoso: {

        },
        ct_hoso: {

        }
    },
    
    options: {
        thuebao: [
            // {
            //     ma_tb: 1,
            //     ma_gd: 1,

            // },
            // {
            //     ma_tb: 1,
            //     ma_gd: 1,


            // }
        ],
        hoso: [
            // {
            //     stt: 1,
            //     ma_gd: 1,
            //     ten_kh: "ấd",
                
            // },
            // {
            //     stt: 1,
            //     ma_gd: 1,
            //     ten_kh: "ấd",

            // }
        ],
        ct_hoso: [
            // {
            //     ma_tb: 1,
            //     ma_gd: 1,
                
            // },
            // {
            //     ma_tb: 1,
            //     ma_gd: 1,
                

            // }
        ]
    }
}
