const schemaVPN = [
    {
        name: "Phân đoạn truyền tải",
        tabs: [
            {
                name: "Trong nước",
                id: '0',
                elements: [
                    {
                        label: 'Thứ tự PĐ (*)',
                        required: true,
                        type: 'input',
                        labelWidth:'150',
                        name: 'THUTU_PD'
                    },
                    {
                        label: 'Mã trung kế MT',
                        type: 'input',
                        labelWidth:'150',
                        name: 'MA_TK_MT'
                    },
                    {
                        label: 'Thiết bị A ',
                        
                        type: 'select',
                        labelWidth:'150',
                        name: 'THIETBI_ID_A',
                        options: "TBI_DAUCUOI",
                        textField: "ten_tbi",
                        valueField: "thietbidc_id"
                    },
                    {
                        label: 'Thiết bị Z ',
                        
                        type: 'select',
                        name: 'THIETBI_ID_Z',
                        labelWidth:'150',
                        options: "TBI_DAUCUOI",
                        textField: "ten_tbi",
                        valueField: "thietbidc_id"
                    },
                    {
                        label: 'Tỉnh A',
                        type: 'select',
                        name: 'TINH_A_ID',
                        disable: true,
                        labelWidth:'150',
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Tỉnh Z',
                        type: 'select',
                        name: 'TINH_Z_ID',
                        disable: true,
                        labelWidth:'150',
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Site A',
                        type: 'select',
                        labelWidth:'150',
                        disable: true,
                        name: 'SITE_A',
                        options: "SITE",
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Site Z',
                        type: 'select',
                        name: 'SITE_Z',
                        disable: true,
                        options: "SITE",
                        labelWidth:'150',
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Hệ thống A',
                        type: 'select',
                        disable: true,
                        name: 'HETHONG_A',
                        labelWidth:'150',
                        options: "HE_THONG",
                        textField: "hethong",
                        valueField: "hethong_id"
                    },
                    {
                        label: 'Hệ thống Z',
                        type: 'select',
                        labelWidth:'150',
                        disable: true,
                        name: 'HETHONG_Z',
                        options: "HE_THONG",
                        textField: "hethong",
                        valueField: "hethong_id"
                    },
                    
                    {
                        label: 'Phòng đặt TB A',
                        type: 'select',
                        name: 'PHONG_TBI_A',
                        labelWidth:'150',
                        disable: true,
                        options: "PHONGMAY",
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Phòng đặt TB Z',
                        type: 'select',
                        name: 'PHONG_TB_Z',
                        disable: true,
                        options: "PHONGMAY",
                        labelWidth:'150',
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Tọa độ trên TB A',
                        type: 'input',
                        labelWidth:'150',
                        name: 'TOADO_TBI_A'
                    },
                    {
                        label: 'Tọa độ trên TB Z',
                        type: 'input',
                        name: 'TOADO_TBI_Z',
                        labelWidth:'150'
                    },
                    {
                        label: 'Tọa độ dấu nháy A',
                        type: 'input',
                        name: 'TOADO_DAUNHAY_A',
                        labelWidth:'150'
                    },
                    {
                        label: 'Tọa độ dấu nháy Z',
                        type: 'input',
                        name: 'TOADO_DAUNHAY_Z',
                        labelWidth:'150'
                    },
                    {
                        label: 'Ghi chú',
                        type: 'textarea',
                        labelWidth:'150',
                        name: 'GHICHU',
                        md: '12'
                    },
                ]
            },
            {
                name: "Quốc tế",
                id: '1',
                elements: [
                    {
                        label: 'Thứ tự PĐ (*)',
                        required: true,
                        type: 'input',
                        labelWidth:'150',
                        name: 'THUTU_PD'
                    },
                    {
                        label: 'Mã trung kế MT',
                        type: 'input',
                        labelWidth:'150',
                        name: 'MA_TK_MT'
                    },
                    {
                        label: 'Thiết bị A ',
                        
                        type: 'select',
                        name: 'THIETBI_ID_A',
                        labelWidth:'150',
                        options: "TBI_DAUCUOI",
                        textField: "ten_tbi",
                        valueField: "thietbidc_id"
                    },
                    {
                        label: 'Thiết bị Z ',
                        type: 'select',
                        
                        name: 'THIETBI_ID_Z',
                        labelWidth:'150',
                        options: "TBI_DAUCUOI",
                        textField: "ten_tbi",
                        valueField: "thietbidc_id"
                    },
                    {
                        label: 'Tỉnh A',
                        type: 'select',
                        disable: true,
                        name: 'TINH_A_ID',
                        labelWidth:'150',
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Tỉnh Z',
                        type: 'select',
                        disable: true,
                        labelWidth:'150',
                        name: 'TINH_Z_ID',
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Site A',
                        
                        type: 'select',
                        labelWidth:'150',
                        disable: true,
                        name: 'SITE_A',
                        options: "SITE",
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Site Z',
                        type: 'select',
                        labelWidth:'150',
                        name: 'SITE_Z',
                        disable: true,
                        options: "SITE",
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Hệ thống A',
                        type: 'select',
                        labelWidth:'150',
                        disable: true,
                        name: 'HETHONG_A',
                        options: "HE_THONG",
                        textField: "hethong",
                        valueField: "hethong_id"
                    },
                    {
                        label: 'Hệ thống Z',
                        type: 'select',
                        labelWidth:'150',
                        disable: true,
                        name: 'HETHONG_Z',
                        options: "HE_THONG",
                        textField: "hethong",
                        valueField: "hethong_id"
                    },
                    
                    {
                        label: 'Phòng đặt TB A',
                        type: 'select',
                        name: 'PHONG_TBI_A',
                        options: "PHONGMAY",
                        disable: true,
                        labelWidth:'150',
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Phòng đặt TB Z',
                        type: 'select',
                        name: 'PHONG_TB_Z',
                        disable: true,
                        options: "PHONGMAY",
                        labelWidth:'150',
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Tọa độ trên TB A',
                        type: 'input',
                        name: 'TOADO_TBI_A',
                        labelWidth:'150'
                    },
                    {
                        label: 'Tọa độ trên TB Z',
                        type: 'input',
                        name: 'TOADO_TBI_Z',
                        labelWidth:'150'
                    },
                    {
                        label: 'Tọa độ dấu nháy A',
                        type: 'input',
                        name: 'TOADO_DAUNHAY_A',
                        labelWidth:'150'
                    },
                    {
                        label: 'Tọa độ dấu nháy Z',
                        type: 'input',
                        name: 'TOADO_DAUNHAY_Z',
                        labelWidth:'150'
                    },
                    {
                        label: 'Hệ thống cáp QT ',
                        
                        type: 'select',
                        name: 'HT_CAPQT_ID',
                        options: "HT_CAPQT",
                        textField: "capqt",
                        labelWidth:'150',
                        valueField: "capqt_id"
                    },
                    {
                        label: 'ID cáp QT ',
                        type: 'input',
                        labelWidth:'150',
                        name: 'ID_CAPQT'
                    },
                    {
                        label: 'Đối tác cáp QT ',
                        type: 'input',
                        labelWidth:'150',
                        name: 'DT_CAPQT'
                    },
                    {
                        label: 'Địa chỉ IP',
                        type: 'input',
                        labelWidth:'150',
                        name: 'IP'
                    },
                    {
                        label: 'ID XConnect ',
                        
                        type: 'input',
                        labelWidth:'150',
                        name: 'ID_XCONNECT'
                    },
                    {
                        label: 'Đối tác XConnect',
                        type: 'select',
                        name: 'DT_XCONNECT',
                        options: "DOITAC",
                        labelWidth:'150',
                        textField: "ten_dt",
                        valueField: "doitac_id"
                    },
                    {
                        label: 'ID Backkhaul',
                        type: 'input',
                        labelWidth:'150',
                        name: 'ID_BACKHAUL',
                        
                    },
                    {
                        label: 'Đối tác Backkhaul',
                        type: 'select',
                        labelWidth:'150',
                        name: 'DT_BACKHAUL',
                        options: "DOITAC",
                        textField: "ten_dt",
                        valueField: "doitac_id"
                    },
                     {
                        label: 'Ngày hết hạn XConnect',
                        type: 'date',
                        labelWidth:'150',
                        name: 'NGAYHH_XCONNECT'
                    },
                    {
                        label: 'Ngày hết hạn Backhaul',
                        type: 'date',
                        labelWidth:'150',
                        name: 'NGAYHH_BACKHAUL'
                    },
                    {
                        label: 'Ghi chú',
                        type: 'textarea',
                        md: '12',
                        labelWidth:'150',
                        name: 'GHICHU_QT'
                    },
                    // {
                    //     label: 'Thứ tự PĐ (*)',
                    //     
                    //     type: 'input',
                    //     name: 'THUTU_PD'
                    // },
                    // {
                    //     label: 'Hệ thống cáp QT ',
                    //     
                    //     type: 'select',
                    //     name: 'HT_CAPQT_ID',
                    //     options: "HT_CAPQT",
                    //     textField: "capqt",
                    //     valueField: "capqt_id"
                    // },
                    // {
                    //     label: 'Ngày hết hạn ',
                    //     type: 'date',
                    //     
                    //     name: 'NGAYHH_CAPQT'
                    // },
                    // {
                    //     label: 'ID cáp QT ',
                    //     
                    //     type: 'input',
                    //     name: 'ID_CAPQT'
                    // },
                    // {
                    //     label: 'ID XConnect',
                    //     type: 'input',
                    //     name: 'ID_XCONNECT',
                        
                    // },
                    // {
                    //     label: 'Đối tác XConnect',
                    //     type: 'select',
                    //     name: 'DT_XCONNECT',
                    //     options: "DOITAC",
                    //     textField: "ten_dt",
                    //     valueField: "doitac_id"
                    // },
                    // {
                    //     label: 'Ngày hết hạn XConnect',
                    //     type: 'date',
                    //     name: 'NGAYHH_XCONNECT'
                    // },
                    // {
                    //     label: 'ID Backhaul',
                    //     type: 'input',
                    //     name: 'ID_BACKHAUL',
                    // },
                    // {
                    //     label: 'Đối tác Backhaul',
                    //     type: 'select',
                    //     name: 'DT_BACKHAUL',
                    //     options: "DOITAC",
                    //     textField: "ten_dt",
                    //     valueField: "doitac_id"
                    // },
                    // {
                    //     label: 'Ngày hết hạn Backhaul',
                    //     type: 'date',
                    //     name: 'NGAYHH_BACKHAUL'
                    // },
                    // {
                    //     label: 'Ghi chú',
                    //     type: 'input',
                    //     md: '12',
                    //     name: 'GHICHU_QT'
                    // },
                ]
            }
        ]

    }
]

export default schemaVPN;