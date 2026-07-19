export default{
    QLVT_NHOM_TT: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/QLVT_NHOM_TT', data,header),
    QLVT_THUOCTINH: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/QLVT_THUOCTINH', data,header),

    sp_nap_ds_vattu_dg: async (axios, data,header) => axios.post('/web-quantri/ganthuoctinh-vattu/sp_nap_ds_vattu_dg', data,header),
    sp_nap_ds_vattu_cg: async (axios, data,header) => axios.post('/web-quantri/ganthuoctinh-vattu/sp_nap_ds_vattu_cg', data,header),
    sp_ins_thuoctinh_vattu: async (axios, data,header) => axios.post('/web-quantri/ganthuoctinh-vattu/sp_ins_thuoctinh_vattu', data,header),
    sp_del_thuoctinh_vattu: async (axios, data,header) => axios.post('/web-quantri/ganthuoctinh-vattu/sp_del_thuoctinh_vattu', data,header),

}
    