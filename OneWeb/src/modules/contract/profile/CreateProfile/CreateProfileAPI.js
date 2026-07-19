export default{
    trangthai_bo_hoso: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/trangthai_bo_hoso', data,header),
    kho_hoso: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/kho_hoso', data,header),
    nhom_file: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/nhom_file', data,header),
    loai_file: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/loai_file', data,header),
    icon_loaihs: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/icon_loaihs', data,header),

    layds_bohoso_trong_kho: async (axios, data,header) => axios.post('/web-quantri/hoso/layds_bohoso_trong_kho', data,header),
    layds_file_trong_hoso: async (axios, data,header) => axios.post('/web-quantri/hoso/layds_file_trong_hoso', data,header),
    layds_donvi_nhan_hoso: async (axios, data,header) => axios.post('/web-quantri/hoso/layds_donvi_nhan_hoso', data,header),
    layds_hopdong_chuaco_hoso: async (axios, data,header) => axios.post('/web-quantri/hopdong/layds_hopdong_chuaco_hoso', data,header),
    upload_file_hoso: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
    xoa_bohoso: async (axios, data,header) => axios.post('/web-quantri/hoso/xoa_bohoso', data,header),
    xoa_file_hoso: async (axios, data,header) => axios.post('/web-quantri/hoso/xoa_file_hoso', data,header),
    capnhat_bohoso: async (axios, data,header) => axios.post('/web-quantri/hoso/capnhat_bohoso', data,header),
    layds_theo_matb: async (axios, data,header) => axios.post('/web-quantri/hopdong/layds_theo_matb', data,header),
    lay_ds_hd_tb_theo_hdkh_id: async (axios, data,header) => axios.post('/web-hopdong/hopdong/lay_ds_hd_tb_theo_hdkh_id', data,header),
    capnhat_file_hoso: async (axios, data,header) => axios.post('/web-hopdong/hopdong/capnhat_file_hoso', data,header),


    ds_quyen_nguoidung: async (axios, data) => axios.get('/web-quantri/quyen/ds_quyen_nguoidung', data),





}
