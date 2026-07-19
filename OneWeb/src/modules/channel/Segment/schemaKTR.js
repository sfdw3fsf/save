const schemaKTR = [
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
                        labelWidth:'110',
                        name: 'THUTU_PD'
                    },
                    {
                        label: 'Mã trung kế MT',
                        type: 'input',
                        labelWidth:'110',
                        name: 'MA_TK_TREN'
                    },
                    {
                        label: 'Thiết bị A',
                        
                        type: 'select',
                        name: 'THIETBI_ID_A',
                        labelWidth:'110',
                        options: "TBI_DAUCUOI",
                        textField: "ten_tbi",
                        valueField: "thietbidc_id"
                    },
                    {
                        label: 'Thiết bị Z',
                        type: 'select',
                        
                        labelWidth:'110',
                        name: 'THIETBI_ID_Z',
                        options: "TBI_DAUCUOI",
                        textField: "ten_tbi",
                        valueField: "thietbidc_id"
                    },
                    {
                        label: 'Tỉnh A',
                        type: 'select',
                        name: 'TINH_A_ID',
                        labelWidth:'110',
                        options: "TINH",
                        disable: true,
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Tỉnh Z',
                        type: 'select',
                        labelWidth:'110',
                        name: 'TINH_Z_ID',
                        disable: true,
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Site A',
                        
                        type: 'select',
                        name: 'SITE_A',
                        disable: true,
                        labelWidth:'110',
                        options: "SITE",
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Site Z', 
                        type: 'select',
                        name: 'SITE_Z',
                        disable: true,
                        labelWidth:'110',
                        options: "SITE",
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Hệ thống A',
                        
                        type: 'select',
                        disable: true,
                        name: 'HETHONG_A',
                        labelWidth:'110',
                        options: "HE_THONG",
                        textField: "hethong",
                        valueField: "hethong_id"
                    },
                    {
                        label: 'Hệ thống Z',
                        type: 'select',
                        name: 'HETHONG_Z',
                        disable: true,
                        labelWidth:'110',
                        options: "HE_THONG",
                        textField: "hethong",
                        valueField: "hethong_id"
                    },
                    
                    {
                        label: 'Phòng đặt TB A',
                        type: 'select',
                        labelWidth:'110',
                        disable: true,
                        name: 'PHONG_TBI_A',
                        options: "PHONGMAY",
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Phòng đặt TB Z',
                        type: 'select',
                        disable: true,
                        name: 'PHONG_TB_Z',
                        labelWidth:'110',
                        options: "PHONGMAY",
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Tọa độ trên TB A',
                        type: 'input',
                        name: 'TOADO_TBI_A',
                        labelWidth:'110'
                    },
                    {
                        label: 'Tọa độ trên TB Z',
                        type: 'input',
                        name: 'TOADO_TBI_Z',
                        labelWidth:'110'
                    },
                    {
                        label: 'Tọa độ dấu nháy A',
                        type: 'input',
                        name: 'TOADO_DAUNHAY_A',
                        labelWidth:'110'
                    },
                    {
                        label: 'Tọa độ dấu nháy Z',
                        type: 'input',
                        name: 'TOADO_DAUNHAY_Z',
                        labelWidth:'110'
                    },
                    {
                        label: 'Ghi chú',
                        type: 'textarea',
                        labelWidth:'110',
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
                        labelWidth:'110',
                        name: 'THUTU_PD'
                    },
                    {
                        label: 'Mã trung kế MT',
                        type: 'input',
                        labelWidth:'110',
                        name: 'MA_TK_MT'
                    },
                    {
                        label: 'Thiết bị A',
                        
                        type: 'select',
                        labelWidth:'110',
                        name: 'THIETBI_ID_A',
                        options: "TBI_DAUCUOI",
                        textField: "ten_tbi",
                        valueField: "thietbidc_id"
                    },
                    {
                        label: 'Thiết bị Z',
                        type: 'select',
                        
                        labelWidth:'110',
                        name: 'THIETBI_ID_Z',
                        options: "TBI_DAUCUOI",
                        textField: "ten_tbi",
                        valueField: "thietbidc_id"
                    },
                    {
                        label: 'Tỉnh A',
                        type: 'select',
                        name: 'TINH_A_ID',
                        disable: true,
                        labelWidth:'110',
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Tỉnh Z',
                        type: 'select',
                        name: 'TINH_Z_ID',
                        disable: true,
                        labelWidth:'110',
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Site A',
                        type: 'select',
                        labelWidth:'110',
                        disable: true,
                        name: 'SITE_A',
                        options: "SITE",
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Site Z',
                        type: 'select',
                        disable: true,
                        name: 'SITE_Z',
                        labelWidth:'110',
                        options: "SITE",
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Hệ thống A',
                        type: 'select',
                        labelWidth:'110',
                        disable: true,
                        name: 'HETHONG_A',
                        options: "HE_THONG",
                        textField: "hethong",
                        valueField: "hethong_id"
                    },
                    {
                        label: 'Hệ thống Z',
                        type: 'select',
                        name: 'HETHONG_Z',
                        options: "HE_THONG",
                        labelWidth:'110',
                        disable: true,
                        textField: "hethong",
                        valueField: "hethong_id"
                    },
                    
                    {
                        label: 'Phòng đặt TB A',
                        type: 'select',
                        name: 'PHONG_TBI_A',
                        labelWidth:'110',
                        disable: true,
                        options: "PHONGMAY",
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Phòng đặt TB Z',
                        type: 'select',
                        name: 'PHONG_TB_Z',
                        labelWidth:'110',
                        disable: true,
                        options: "PHONGMAY",
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Tọa độ trên TB A',
                        type: 'input',
                        name: 'TOADO_TBI_A',
                        labelWidth:'110'
                    },
                    {
                        label: 'Tọa độ trên TB Z',
                        type: 'input',
                        name: 'TOADO_TBI_Z',
                        labelWidth:'110'
                    },
                    {
                        label: 'Tọa độ dấu nháy A',
                        type: 'input',
                        name: 'TOADO_DAUNHAY_A',
                        labelWidth:'110'
                    },
                    {
                        label: 'Tọa độ dấu nháy Z',
                        type: 'input',
                        name: 'TOADO_DAUNHAY_Z',
                        labelWidth:'110'
                    },
                    {
                        label: 'Hệ thống cáp QT',
                        
                        type: 'select',
                        name: 'HT_CAPQT_ID',
                        labelWidth:'110',
                        options: "HT_CAPQT",
                        textField: "capqt",
                        valueField: "capqt_id"
                    },
                    {
                        label: 'ID cáp QT',
                        type: 'input',
                        labelWidth:'110',
                        name: 'ID_CAPQT'
                    },
                    {
                        label: 'Đối tác cáp QT',
                        type: 'input',
                        labelWidth:'110',
                        name: 'DT_CAPQT'
                    },
                    {
                        label: 'Địa chỉ IP',
                        type: 'input',
                        labelWidth:'110',
                        name: 'IP'
                    },
                    {
                        label: 'ID XConnect',
                        labelWidth:'110',
                        type: 'input',
                        name: 'ID_XCONNECT'
                    },
                    {
                        label: 'Đối tác XConnect',
                        type: 'select',
                        labelWidth:'110',
                        name: 'DT_XCONNECT',
                        options: "DOITAC",
                        textField: "ten_dt",
                        valueField: "doitac_id"
                    },
                    {
                        label: 'ID Backkhaul',
                        type: 'input',
                        labelWidth:'110',
                        name: 'ID_BACKHAUL',
                        
                    },
                    {
                        label: 'Đối tác Backkhaul',
                        type: 'select',
                        labelWidth:'110',
                        name: 'DT_BACKHAUL',
                        options: "DOITAC",
                        textField: "ten_dt",
                        valueField: "doitac_id"
                    },
                    {
                        label: 'Service ID POP',
                        type: 'input',
                        labelWidth:'110',
                        name: 'Service_ID_POP'
                    },
                    {
                        label: 'VC ID POP',
                        type: 'input',
                        labelWidth:'110',
                        name: 'VC_ID_POP'
                    },
                    {
                        label: 'S VLAN POP',
                        type: 'input',
                        labelWidth:'110',
                        name: 'S_VLAN_POP'
                    },
                    {
                        label: 'C LAN POP',
                        type: 'input',
                        labelWidth:'110',
                        name: 'C_VLAN_POP'
                    },
                    {
                        label: 'Ghi chú',
                        type: 'textarea',
                        labelWidth:'110',
                        md: '12',
                        name: 'GHICHU_QT'
                    },
                ]
            }
        ]

    }
]

export default schemaKTR;