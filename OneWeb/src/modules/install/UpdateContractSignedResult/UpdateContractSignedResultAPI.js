export default{
    get_sp_layds_loaihd_theoid: async (axios, id, data) => axios.get('/web-hopdong/danhmuc/sp_layds_loaihd_theoid/'+id, data),
    post_lay_ds_phieu_giao_kyhd: async (axios, data) => axios.post('/web-hopdong/danhmuc/lay_ds_phieu_giao_kyhd', data),
    get_lay_loai_ketqua_ky_hd:async (axios, data) => axios.get('/web-hopdong/danhmuc/lay_loai_ketqua_ky_hd', data),
    get_lay_thongtin_nhanvien:async (axios,nhanvien_id,data) => axios.get('/web-hopdong/danhmuc/lay_thongtin_nhanvien/'+nhanvien_id, data),
    get_lay_ds_nhanvien_thicong:async (axios,loainv_id, data) => axios.get('/web-hopdong/danhmuc/lay_ds_nhanvien_thicong/'+loainv_id, data),
    post_ds_nhanvien_theo_phieu: async (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/ds_nhanvien_theo_phieu', data),
    post_capnhat_thongtin_phieu: async (axios, data) => axios.post('/web-hopdong/hopdong/capnhat_thongtin_phieu', data),
  }