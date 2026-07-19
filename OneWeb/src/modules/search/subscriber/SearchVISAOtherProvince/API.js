export default{
    lookup_account: async (axios, data, header) => axios.post('/tichhop/visa-app/lookupRASAccountOtherProvince', data,header),
    ds_thamsodv: async (axios, data,header) => axios.get('/web-quantri/thamso/ds_thamsodv', data,header),
    map_id: async (axios, data,header) => axios.post('/web-quantri/thamso/map_id', data,header),
    lay_ls_tac_dong: async (axios, data, header) => axios.post('/tichhop/visa-app/lookupRASAccountOtherProvince', data,header),
    fn_tt_dslam: async (axios, data, header) => axios.post('/web-bancheo/tracuu/fn_tt_dslam', data, header), 
    fn_tt_tinh: async (axios, data, header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh', data, header), 
}
    
