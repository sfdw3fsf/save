export default {
    lay_danhsach_dichvu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    lay_danhsach_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
    lay_danhsach_loai_cuoc: async (axios) => axios.get('/web-quantri/bgthue_kenh_cong/loai_cuoc'),

    lay_danhsach_banggia_cuoc: async (axios, loaihinh_id) => axios.get('/web-quantri/bgthue_kenh_cong/sp_banggia_lay_ds_banggia_cuoc_tk?loaitb_id='+loaihinh_id),
    
    lay_danhsach_loaihinh_tb_tocdo_cuoctk_tc: async (axios, banggia_id, loaitb_id, kieu) => axios.get('/web-quantri/bgthue_kenh_cong/sp_banggia_lay_ds_loaihinh_tb_tocdo_cuoctk_tc?banggia_id='+banggia_id+'&kieu='+kieu+'&loaitb_id='+loaitb_id),
    lay_danhsach_loaikenh_cuoctk_tc: async (axios, banggia_id, kieu) => axios.get('/web-quantri/bgthue_kenh_cong/sp_banggia_layds_loaikenh_cuoctk_tc?banggia_id='+banggia_id+'&kieu='+kieu),
    lay_danhsach_doituongkh_cuoctk_tc: async (axios, banggia_id, kieu) => axios.get('/web-quantri/bgthue_kenh_cong/sp_banggia_laydsdoituongkh_cuoctk_tc?banggia_id='+banggia_id+'&kieu='+kieu),

    sp_banggia_delete_kc: async (axios, banggia_id) => axios.get('/web-quantri/bgthue_kenh_cong/sp_banggia_delete_kc?banggia_id='+banggia_id),
    
    sp_banggia_insert_or_update: async (axios, data) => axios.post('/web-quantri/bgthue_kenh_cong/sp_banggia_insert_or_update', data),
    
}