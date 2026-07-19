export default {
    getDanhSachTyLeThuCuoc: (axios, data) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md', data),    
    getCommissionRateChuKy: (axios, data) => axios.post('/web-quantri/tyle_thulao_duochuong/tyle_tps_layds_theo_chukyno', data),
    getCommissionRateNV: (axios, data) => axios.post('/web-quantri/tyle_thulao_duochuong/tyle_tps_layds_theo_loainv', data),
    getCommissionRateKhuVuc: (axios, data) => axios.post('/web-quantri/tyle_thulao_duochuong/tyle_tps_layds_theo_khuvuc', data),
    postCommissionRate: (axios, data) => axios.post('/web-quantri/tyle_thulao_duochuong/tyle_tps_insert_or_update', data),
    deleteCommissionRate: (axios, data) => axios.post('/web-quantri/tyle_thulao_duochuong/tyle_tps_delete', data),

    sp_tt_tyle_tps: (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_tyle_tps', data),
    

}
