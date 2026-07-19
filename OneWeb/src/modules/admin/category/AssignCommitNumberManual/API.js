
export default {
    getLoaiSo: async (axios,data) => axios.post('/web-quantri/gansodep/sp_lay_ds_loaiso',data),
    getNhomSoDep: async (axios) => axios.post('/web-quantri/gansodep/sp_lay_ds_nhomso'),
    getDichVuVT: async (axios) => axios.post(''),
    getKieuSoDep: async (axios, data) => axios.post('/web-quantri/gansodep/sp_lay_ds_kieuso_dep', data),
    getDSPrefix: async (axios, data) => axios.post('/web-quantri/gansodep/sp_lay_ds_prefix_tongdai_dvvt', data),
    getDSSo: async (axios, data) => axios.post('/web-quantri/gansodep/sp_lay_ds_khoso_theo_dauso_prefix', data),
    getCapNhatKieuSo: async (axios, data) => axios.post('/web-quantri/gansodep/sp_capnhat_kieuso', data),
}