const collumns = {
    col_tuyenthu: [
        {
            fieldName: "tuyenthu_id",
            headerText: "Tuyến ID",
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "ma_tuyen",
            headerText: "Mã tuyến",
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "tentuyen",
            headerText: "Tên tuyến",
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "ten_httt",
            headerText: "Hình thức TT",
            allowSorting: false,
            autoFit: "true",
        }
    ],
    col_dsnap: [
        {
            fieldName: "ten_quan",
            headerText: "Tên quận",
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "ten_phuong",
            headerText: "Tên phường",
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "ten_pho",
            headerText: "Tên phố",
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "ten_ap",
            headerText: "Tên ấp",
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "ten_khu",
            headerText: "Tên khu",
            allowSorting: false,
            autoFit: "true",
        },
        {
            fieldName: "dacdiem",
            headerText: "Đặc điểm",
            allowSorting: false,
            autoFit: "true",
        }
    ],
    col_pho: [
        {
            fieldName: "TEN_PHO",
            headerText: "Tên phố",
            allowSorting: false,
            width: '120'
        }
    ],
    col_pho_left: [
        {
            fieldName: "TEN_PHO",
            headerText: "Tên phố",
            allowSorting: false,
            width: '120'
        },
        {
            fieldName: "TEN_PHUONG",
            headerText: "Phường",
            allowSorting: false,
            width: '120'
        }
    ],
    col_ap: [
        {
            fieldName: "TEN_PHO",
            headerText: "Tên ấp",
            allowSorting: false,
            width: '120'
        }
    ],
    col_ap_left: [
        {
            fieldName: "TEN_PHO",
            headerText: "Tên ấp",
            allowSorting: false,
            width: '120'
        },
        {
            fieldName: "TEN_PHUONG",
            headerText: "Phường",
            allowSorting: false,
            width: '120'
        }
    ],
    col_khu: [
        {
            fieldName: "TEN_PHO",
            headerText: "Tên khu",
            allowSorting: false,
            width: '120'
        }
    ],
    col_khu_left: [
        {
            fieldName: "TEN_PHO",
            headerText: "Tên khu",
            allowSorting: false,
            width: '120'
        },
        {
            fieldName: "TEN_PHUONG",
            headerText: "Phường",
            allowSorting: false,
            width: '120'
        }
    ],
    col_dacdiem: [
        {
            fieldName: "DACDIEM",
            headerText: "Đặc điểm",
            allowSorting: false,
            width: '120'
        },
        {
            fieldName: "TEN_PHO",
            headerText: "Phố/Ấp/Khu",
            allowSorting: false,
            width: '120'
        }
    ]
}

export default collumns
