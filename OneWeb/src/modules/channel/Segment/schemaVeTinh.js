const schemaVeTinh = [
    {
        name: "Phân đoạn truyền tải",
        tabs: [
            {
                name: "Trong nước",
                id: '0',
                elements: [
                    {
                        label: "Thứ tự PĐ (*)",
                        type: "input",
                        labelWidth:'100',
                        name: "thutu_pd",
                        md: '12',
                        required: true
                        
                    },
                    {
                        label: 'Tỉnh A',
                        type: 'select',
                        name: 'tinh_a_id',
                        labelWidth:'100',
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'Tỉnh Z',
                        type: 'select',
                        labelWidth:'100',
                        name: 'tinh_z_id',
                        options: "TINH",
                        textField: "tentinh",
                        valueField: "tinh_id"
                    },
                    {
                        label: 'A_Station ',
                        type: 'select',
                        name: 'a_station',
                        labelWidth:'100',
                        options: "A_STATION",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id",
                        
                    },
                    {
                        label: 'Z_Station ',
                        type: 'input',
                        labelWidth:'100',
                        name: 'z_station',
                        
                    },
                    {
                        label: 'Extend_Point_A ',
                        type: 'select',
                        labelWidth:'100',
                        name: 'extend_point_a',
                        options: "EXTEND_POINT_A",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id",
                        
                    },
                    {
                        label: 'Extend_Point_Z ',
                        type: 'select',
                        labelWidth:'100',
                        name: 'extend_point_z',
                        options: "EXTEND_POINT_A",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id",
                        
                    },
                    {
                        label: 'TX Rate_A ',
                        type: 'input',
                        name: 'tx_rate_a',
                        labelWidth:'100',
                        
                    },
                    {
                        label: 'TX Rate_Z',
                        type: 'input',
                        labelWidth:'100',
                        name: 'tx_rate_z',
                    },
                    {
                        label: 'RX Rate_A',
                        type: 'input',
                        labelWidth:'100',
                        name: 'rx_rate_a',
                    },
                    {
                        label: 'RX Rate_Z',
                        type: 'input',
                        labelWidth:'100',
                        name: 'rx_rate_z',
                    },
                    {
                        label: 'TX_Fre_A ',
                        type: 'input',
                        name: 'tx_fre_a',
                        labelWidth:'100',
                        
                    },
                    {
                        label: 'TX_Fre_Z',
                        type: 'input',
                        labelWidth:'100',
                        name: 'tx_fre_z',
                    },
                    {
                        label: 'RX_Fre_A ',
                        type: 'input',
                        labelWidth:'100',
                        name: 'rx_fre_a',
                        
                    },
                    {
                        label: 'RX_Fre_Z',
                        type: 'input',
                        labelWidth:'100',
                        name: 'rx_fre_z',
                    },
                    {
                        label: 'Tx_Pol_A',
                        type: 'select',
                        name: 'tx_pol_a',
                        labelWidth:'100',
                        options: "TX_POL_A",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'Tx_Pol_Z',
                        type: 'select',
                        name: 'tx_pol_z',
                        labelWidth:'100',
                        options: "TX_POL_Z",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'Rx_Pol_A',
                        type: 'select',
                        name: 'rx_pol_a',
                        labelWidth:'100',
                        options: "RX_POL_A",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    
                    {
                        label: 'Rx_Rate Item_A',
                        type: 'select',
                        name: 'rx_pol_z',
                        labelWidth:'100',
                        options: "RX_POL_Z",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'Tx_Rate Item_A',
                        type: 'select',
                        name: 'tx_rate_item_a',
                        labelWidth:'100',
                        options: "TX_RATE_ITEM_A",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    
                    {
                        label: 'Rx_Rate Item_A',
                        type: 'select',
                        name: 'rx_pol_z',
                        labelWidth:'100',
                        options: "RX_POL_Z",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'Tx_Rate Item_A',
                        type: 'select',
                        name: 'tx_rate_item_a',
                        labelWidth:'100',
                        options: "TX_RATE_ITEM_A",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    
                    {
                        label: 'Rx_Rate Item_A',
                        type: 'select',
                        labelWidth:'100',
                        name: 'rx_rate_item_a',
                        options: "RX_RATE_ITEM_A",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'Hub_Trans',
                        type: 'select',
                        name: 'hub_trans',
                        labelWidth:'100',
                        options: "HUB_TRANS",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'Vsat_trans',
                        type: 'select',
                        name: 'vsat_trans',
                        labelWidth:'100',
                        options: "VSAT_TRANS",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'Long_loc',
                        type: 'input',
                        labelWidth:'100',
                        name: 'long_loc',
                    },
                    {
                        label: 'Lat_loc',
                        type: 'input',
                        labelWidth:'100',
                        name: 'lat_loc',
                    },
                    {
                        label: 'Mode code',
                        type: 'select',
                        labelWidth:'100',
                        name: 'mode_code',
                        options: "MODE_CODE",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'Rolloff',
                        type: 'select',
                        labelWidth:'100',
                        name: 'rolloff',
                        options: "ROLLOFF",
                        textField: "giatri",
                        valueField: "thongso_vetinh_id"
                    },
                    {
                        label: 'VSAT_ID',
                        type: 'input',
                        labelWidth:'100',
                        name: 'vsat_id',
                    },
                    {
                        label: 'Sub_SE',
                        type: 'input',
                        name: 'sub_se',
                    },
                    {
                        label: 'Sub_Voice',
                        type: 'input',
                        labelWidth:'100',
                        name: 'sub_voice',
                    },
                    {
                        label: 'IP_Pub',
                        type: 'input',
                        labelWidth:'100',
                        name: 'ip_pub',
                    },
                    {
                        label: 'Voice_Num',
                        type: 'input',
                        labelWidth:'100',
                        name: 'voice_num',
                    }
                ]
            },
            {
                name: "Quốc tế",
                id: '1',
                elements: [
                    {
                        label: "Thứ tự PĐ (*)",
                        type: "input",
                        name: "thutu_pd",
                        labelWidth:'100',
                        required: true
                    },
                    {
                        label: 'Hệ thống cáp QT ',
                        type: 'select',
                        name: 'ht_capqt_id',
                        options: "HT_CAPQT",
                        textField: "capqt",
                        labelWidth:'100',
                        valueField: "capqt_id",
                        
                    },
                    {
                        label: "Ngày hết hạn ",
                        type: "date",
                        labelWidth:'100',
                        name: "ngayhh_capqt",
                        
                    },
                    {
                        label: "ID cáp QT ",
                        type: "input",
                        name: "id_capqt",
                        labelWidth:'100',
                        
                    },
                    {
                        label: 'ID XConnect ',
                        type: 'select',
                        name: 'tinh_a_id',
                        options: "DOITAC",
                        labelWidth:'100',
                        textField: "ten_dt",
                        valueField: "doitac_id",
                        
                    },
                    {
                        label: "Đối tác XConnect",
                        type: "input",
                        labelWidth:'100',
                        name: "thutu_pd",

                    },
                    {
                        label: "Ngày HH XConnect",
                        type: "input",
                        labelWidth:'100',
                        name: "thutu_pd",
                    },
                    {
                        label: "ID Backhaul",
                        type: "input",
                        labelWidth:'100',
                        name: "thutu_pd",
                    },
                    {
                        label: 'Đối tác Backhaul',
                        type: 'select',
                        name: 'tinh_a_id',
                        options: "DOITAC",
                        labelWidth:'100',
                        textField: "ten_dt",
                        valueField: "doitac_id"
                    },
                    {
                        label: "Ngày HH Backhaul",
                        type: "date",
                        labelWidth:'100',
                        name: "ngayhh_backhaul",

                    },
                    {
                        label: "Ghi chú",
                        type: "textarea",
                        labelWidth:'100',
                        name: "ghichu",
                        md: '12'
                    },
                ]
            }
        ]

    }
]

export default schemaVeTinh;