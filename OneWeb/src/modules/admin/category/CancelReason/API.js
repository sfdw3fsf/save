export default {
    lay_ds_loai_lydo_huy: async (axios) => axios.get('/web-quantri/danhmuc/SP_LAY_DS_LOAI_LYDO_HUY'),
    lay_ds_dichvu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    lay_ds_lydohuy: async (axios) => axios.get('/web-quantri/danhmuc/SP_LAY_DS_LYDO_HUY'),

    lay_ds_lydohuy_lhtb_dagan: async (axios, lydohuy_id) => axios.post('/web-quantri/danhmuc/SP_LAY_DS_LYDOHUY_LHTB_DAGAN',{
        p_lydohuy_id:lydohuy_id
    }),

    lay_ds_lydohuy_lhtb_chuagan: async (axios, lydohuy_id, dichvuvt_id) => axios.post('/web-quantri/danhmuc/SP_LAY_DS_LYDOHUY_LHTB_CHUAGAN',{
        p_lydohuy_id: lydohuy_id,
        p_dichvuvt_id: dichvuvt_id
    }),

    xoa_lydohuy: async (axios, lydohuy_id) => axios.post('/web-quantri/danhmuc/FN_XOA_LYDOHUY',{
        p_lydohuy_id: lydohuy_id
    }),

    fn_insert_update_lydohuy: async (axios, data) => axios.post('/web-quantri/danhmuc/FN_INSERT_OR_UPDATE_LYDOHUY', data),

    
}