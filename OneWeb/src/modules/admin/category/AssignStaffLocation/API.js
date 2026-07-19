export default {
    getDSLoaiNV: async (axios) => axios.post('/web-quantri/nhanvien/sp_nhanvien_vt_ds_loainv'),
    getDSNhanVien: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_nhanvien_vt_dsnhanvien', data),
    insertNhanVienVT: async (axios, data) => axios.post('/web-quantri/nhanvien/fn_nhanvien_vt_insert', {data}),
    deleteNhanVienVT: async (axios, data) => axios.post('/web-quantri//nhanvien/fn_nhanvien_vt_delete', {data}),
    getNVChuaGan: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_nhanvien_vt_ds_chuagan', data),
    getNVDaGan: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_nhanvien_vt_ds_dagan', data),
    getDSViTri: async (axios) => axios.post('/web-quantri/nhanvien/sp_nhanvien_vt_ds_vitri'),
    getDonvi:async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI'),
    getDonviCon:async (axios, donvicha) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI?donvi_cha_id='+donvicha),
    getDonViIDTheoND: async(axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_donvi_id_theo_ma_nd', data),
}