const schemaVPN = [
    {
        name: "Phân đoạn truyền tải",
        tabs: [
            {
                name: "Trong nước",
                id: '0',
                elements: [
                    {
                        label: 'Thứ tự PĐ (*) ',
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
                        label: 'PT bảo vệ ',
                        type: 'select',
                        labelWidth:'150',
                        name: 'PTBV_ID',
                        options: "PHUONGTHUC_BV",
                        textField: "phuongthuc_bv",
                        
                        md: '12',
                        labelWidth:'150',
                        valueField: "phuongthuc_bv_id"
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
                        labelWidth:'150',
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
                        options: "TINH",
                        labelWidth:'150',
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Site A',
                        type: 'select',
                        labelWidth:'150',
                        name: 'SITE_A',
                        disable: true,
                        options: "SITE",
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Site Z',
                        type: 'select',
                        name: 'SITE_Z',
                        disable: true,
                        labelWidth:'150',
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
                        options: "PHONGMAY",
                        disable: true,
                        labelWidth:'150',
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Tọa độ trên TB A',
                        type: 'input',
                        name: 'TOADO_TBI_A',
                        labelWidth:'150',
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
                    // {
                    //     label: 'Thứ tự PĐ (*) ',
                    //     
                    //     type: 'input',
                    //     name: 'THUTU_PD'
                    // },
                    // {
                    //     label: 'PT bảo vệ ',
                    //     type: 'select',
                    //     name: 'TINH_A_ID',
                    //     options: "PHUONGTHUC_BV",
                    //     textField: "phuongthuc_bv",
                    //     
                    //     valueField: "phuongthuc_bv_id"
                    // },
                    // {
                    //     label: 'Mã trung kế MT',
                    //     type: 'input',
                    //     name: 'MA_TK_MT'
                    // },
                    // {
                    //     label: 'Tỉnh A',
                    //     type: 'select',
                    //     name: 'TINH_A_ID',
                    //     options: "TINH",
                    //     textField: "tentinh",
                    //     valueField: "tinh_id"
                    // },
                    // {
                    //     label: 'Site A ',
                    //     
                    //     type: 'select',
                    //     name: 'SITE_A',
                    //     options: "SITE",
                    //     textField: "site",
                    //     valueField: "site_id"
                    // },
                    // {
                    //     label: 'Phòng đặt TB A',
                    //     type: 'select',
                    //     name: 'PHONG_TBI_A',
                    //     options: "PHONGMAY",
                    //     textField: "phongmay",
                    //     valueField: "phongmay_id"
                    // },
                    // {
                    //     label: 'Hệ thống A ',
                    //     
                    //     type: 'select',
                    //     name: 'HETHONG_A',
                    //     options: "HE_THONG",
                    //     textField: "hethong",
                    //     valueField: "hethong_id"
                    // },
                    // {
                    //     label: 'Thiết bị A ',
                    //     
                    //     type: 'select',
                    //     name: 'THIETBI_A',
                    //     options: "TBI_DAUCUOI",
                    //     textField: "ten_tbi",
                    //     valueField: "thietbidc_id"
                    // },
                    // {
                    //     label: 'Tọa độ trên TB A',
                    //     type: 'select',
                    //     name: 'TOADO_TBI_A',
                    //     options: "RX_POL_A",
                    //     textField: "ma_tsvt",
                    //     valueField: "giatri"
                    // },
                    // {
                    //     label: 'Tọa độ dấu nháy A',
                    //     type: 'select',
                    //     name: 'TOADO_DAUNHAY_A',
                    //     options: "TX_POL_A",
                    //     textField: "ma_tsvt",
                    //     valueField: "giatri"
                    // },
                    // {
                    //     label: 'Tỉnh Z',
                    //     type: 'select',
                    //     name: 'TINH_Z_ID',
                    //     options: "TINH",
                    //     textField: "tentinh",
                    //     valueField: "tinh_id"
                    // },
                    // {
                    //     label: 'Site Z',
                    //     type: 'select',
                    //     name: 'SITE_Z',
                    //     options: "SITE",
                    //     textField: "site",
                    //     valueField: "site_id"
                    // },
                    // {
                    //     label: 'Phòng đặt TB Z ',
                    //     
                    //     type: 'select',
                    //     name: 'PHONG_TB_Z',
                    //     options: "PHONGMAY",
                    //     textField: "phongmay",
                    //     valueField: "phongmay_id"
                    // },
                    // {
                    //     label: 'Hệ thống Z',
                    //     type: 'select',
                    //     name: 'HETHONG_Z',
                    //     options: "HE_THONG",
                    //     textField: "hethong",
                    //     valueField: "hethong_id"
                    // },
                    // {
                    //     label: 'Thiết bị Z',
                    //     type: 'select',
                    //     name: 'THIETBI_Z',
                    //     options: "TBI_DAUCUOI",
                    //     textField: "ten_tbi",
                    //     valueField: "thietbidc_id"
                    // },
                    // {
                    //     label: 'Tọa độ trên TB Z',
                    //     type: 'select',
                    //     name: 'TOADO_TBI_Z',
                    //     options: "RX_POL_Z",
                    //     textField: "ma_tsvt",
                    //     valueField: "giatri"
                    // },
                    // {
                    //     label: 'Tọa độ dấu nháy Z',
                    //     type: 'select',
                    //     name: 'TOADO_DAUNHAY_Z',
                    //     options: "TX_POL_Z",
                    //     textField: "ma_tsvt",
                    //     valueField: "giatri"
                    // },
                    // {
                    //     label: 'Ghi chú',
                    //     type: 'input',
                    //     name: 'GHICHU'
                    // },
                ]
            }
        ]

    }
]

export default schemaVPN;