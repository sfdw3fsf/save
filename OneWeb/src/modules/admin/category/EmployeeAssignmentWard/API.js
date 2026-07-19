export default {
	ds_quan: async (axios, data) => axios.get('/web-quantri/danhmuc/diaban/quan', data),
	ds_phuong: async (axios, data) => axios.post('/web-quantri/danhmuc/diaban/phuong-theo-quan', data),
	ds_dacdiem: async (axios, data) => axios.post('/web-quantri/gan_nv_phuongxa/fn_nhanvien_diadanh_layds_dacdiem', data),
	formLoad: async (axios, data) => axios.post('/web-quantri/gan_nv_phuongxa/fn_nhanvien_diadanh_load', data),
	ds_loaiNhanVien: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_ds_loainhanvien', data),
	ds_daGan: async (axios, data) => axios.post('/web-quantri/gan_nv_phuongxa/lay_ds_nhanvien_px_v2', data),
	ds_chuaGan: async (axios, data) => axios.post('/web-quantri/gan_nv_phuongxa/fn_nhanvien_diadanh_hienthi_ds_chuagan', data),
	capnhat: async (axios, data) => axios.post('/web-quantri/diadanh-nhanvien/fn_nhanvien_diadanh_capnhat', data),
}