export default{
    lay_ds_nv_theo_dv: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nv_theo_dv', data,header),
    lay_ds_dvvt: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_dvvt', data,header),
    lay_loai_hd: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_loai_hd', data,header),
    lay_ds_nhanvien_theo_phieu_id: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', data,header),
    //{"phieu_id":176120,"nhanvien_id":452,"kieu_id":2}
    capnhat_khaosat: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/capnhat_khaosat', data,header),
    capnhat_hoanthanh_khaosat: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/capnhat_hoanthanh_khaosat', data,header),
    lay_danhsach_hd_ks_v3: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_danhsach_hd_ks_v3', data,header),
    traphieu_giaosai: async (axios, data,header) => axios.post('/web-ccdv/HoanThanhKhaoSat/traphieu_giaosai', data,header),
    getLoaiHinhTB: async (axios,id) =>axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId='+id),
    update_nd_thuchien: async (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/update_nd_thuchien', data),
    Lay_ds_hopdong_tb_theo_hdtb_id: async (axios,data) =>axios.post('/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id',data),
}
    