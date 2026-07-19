const schemaVPN = [
    {
        name: "Thông số cấu hình",
        tabs: [
            {
                name: "",
                id: '0',
                elements: [
                    {
                        label: 'Thứ tự PĐ (*)',
                        required: true,
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'THUTU_PD'
                    },
                    {
                        label: 'Thiết bị',
                        type: 'select',
                        name: 'THIETBI_ID_A',
                        options: "TBI_DAUCUOI",
                        md: '6',
                        labelWidth:'100',
                        textField: "ten_tbi",
                        valueField: "thietbidc_id" 
                    },
                    {
                        label: 'Tỉnh',
                        type: 'select',
                        name: 'TINH_A_ID',
                        disable: true,
                        options: "TINH",
                        md: '3',
                        labelWidth:'100',
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Site',
                        disable: true,
                        type: 'select',
                        name: 'SITE_A',
                        options: "SITE",
                        md: '3',
                        labelWidth:'100',
                        textField: "site",
                        valueField: "site_id"
                    },
                    {
                        label: 'Phòng đặt TB',
                        type: 'select',
                        disable: true,
                        name: 'PHONG_TBI_A',
                        md: '3',
                        labelWidth:'100',
                        options: "PHONGMAY",
                        textField: "phongmay",
                        valueField: "phongmay_id"
                    },
                    {
                        label: 'Interface',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'INTERFACE_NAME'
                    },
                    {
                        label: 'Unit',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'UNIT'
                    },
                    {
                        label: 'S-VLAN',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'S_VLAN'
                    },
                    {
                        label: 'C-VLAN',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'C_VLAN'
                    },
                    {
                        label: 'Service name',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'SERVICE_ID'
                    },
                    {
                        label: 'VC-ID',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'VC_ID'
                    },
                    {
                        label: 'RT import',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'RT_IMPORT'
                    },
                    {
                        label: 'RT Export',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'RT_EXPORT'
                    },
                    {
                        label: 'IP Gateway_IPv4',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'IP_GATEWAY'
                    },
                    {
                        label: 'IP WAN_IPv4',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'IP_WAN'
                    },
                    {
                        label: 'IP LAN_IPv4',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'IP_LAN'
                    },
                    {
                        label: 'IP Gateway_IPv6',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'IP_GATEWAY_V6'
                    },
                    {
                        label: 'IP WAN_IPv6',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'IP_WAN_V6'
                    },
                    {
                        label: 'IP LAN_IPv6',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'IP_LAN_V6'
                    },
                    {
                        label: 'GT định tuyến',
                        type: 'select',
                        md: '3',
                        labelWidth:'100',
                        name: 'GTDT_ID',
                        options: "CAUHINH_DT",
                        textField: "cauhinh_dt",
                        valueField: "cauhinh_dt_id"
                    },
                    {
                        label: 'Thông số ASN',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'SO_AS'
                    },
                    {
                        label: 'Số lượng MAC',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'SOLUONG_MAC'

                    },
                    {
                        label: 'MTU',
                        type: 'input',
                        md: '3',
                        labelWidth:'100',
                        name: 'MTU'
                    },
                    
                    {
                        label: 'Ghi chú',
                        type: 'textarea',
                        md: '12',
                        labelWidth:'100',
                        name: 'GHICHU'
                    },
                ]
            },
        ]
    },
]

export default schemaVPN;