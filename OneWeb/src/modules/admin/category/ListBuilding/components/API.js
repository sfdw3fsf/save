export default{
    sp_lay_ds_toanha_ims_da_gan: async (axios, data,header) => axios.post('/web-toanha/danhmuc-toanha/sp_lay_ds_toanha_ims_da_gan', data,header),
    CSS_TOANHA: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_TOANHA', data,header),
    sp_del_toanha_ims_by_id: async (axios, data,header) => axios.post('/web-toanha/danhmuc-toanha/sp_del_toanha_ims_by_id', data,header),
    sp_ins_toanha_ims: async (axios, data,header) => axios.post('/web-toanha/danhmuc-toanha/sp_ins_toanha_ims', data,header),
    get_ProjectbyInvestmentField: async (axios, data,header) => axios.get('/tichhop/qldt/get_ProjectbyInvestmentField', data,header),
    lichsu_capnhat: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/sp_biendong_toanha', data),
}
    