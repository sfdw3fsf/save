export default{
    //popup toa nha
    css_quan: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN', null),
    css_phuong: async (axios, data) => axios.post('/web-quantri//danhmuc-chung/CSS_PHUONG', data),
    sp_lay_ds_toanha_by_quanphuong: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_toanha_by_quanphuong', data),
    sp_lay_ds_thuebao_toanha_by_tag: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_lay_ds_thuebao_toanha_by_tag', data),
}