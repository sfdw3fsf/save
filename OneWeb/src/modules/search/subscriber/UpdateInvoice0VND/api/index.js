export default {
    dsPhongBanHang: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/sp_lay_ds_phongbh', data),
    dsCuaHang: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/sp_lay_ds_cuahang', data),
    dsThuNgan: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/sp_lay_ds_thungan', data),
    
    dsHoaDon: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/post_list_v2', data),
    dsLoaiTien: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/post_list_v2', data),
    dsSeri: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/post_list_v2', data),
    dsHDKH: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/lay_ds_hdkh_hoadon_0_dong', data),
    layDuLieuHD: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/sp_lay_dulieu_hoadon', data),
    capNhat: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/sp_capnhat_phieutt_hoadon0dong', data),
    LAY_DS_HDKH_THEO_HOADON: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/lay_ds_hdkh_theo_hoadon', data),
    lay_ds_donvi: async (axios, data) => axios.post('/web-quantri/hoadon-0dong/lay_ds_donvi', data),
}