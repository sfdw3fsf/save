export default{
    lay_ds_nv_theo_dv: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nv_theo_dv', data,header),
    lay_ds_dvvt: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_dvvt', data,header),
    lay_loai_hd: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_loai_hd', data,header),
    lay_ds_nhanvien_theo_phieu_id: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', data,header),
    capnhat_khaosat: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/capnhat_khaosat', data,header),
    capnhat_hoanthanh_khaosat: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/capnhat_hoanthanh_khaosat', data,header),
    lay_danhsach_hd_ks_v3: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_danhsach_hd_ks_v3', data,header),
    traphieu_giaosai: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/traphieu_giaosai', data,header),
}
    