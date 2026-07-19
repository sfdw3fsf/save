export default {
	thongtin_nguoidung2: async (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', {}),
	lay_user_neo: async (axios, data) => axios.post('/web-ccdv/dongbo_danhba_vnp/lay_ds_user_neo', data),
	lay_ds_user_ctv: async (axios, data) => axios.post('/web-thicong/hoso_datcoc_theofile/ht_ds_nhanvien_ctv_combobox', data),
	lay_ds_donvi_ctv: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/lay_ds_donvi_ctv'),
	lay_ds_nhom_nv: async (axios, data) => axios.post('/web-thicong/danhmuc/lay_ds_nhom_nv', data),
	lay_ds_kieubd_dongbo_vina: async (axios, data) => axios.get('/web-ccdv/dongbo_danhba_vnp/ht_combo_kieubd_dongbo_vina'),
	lay_ds_kieudl_dongbo_vina: async (axios, data) => axios.get('/web-ccdv/dongbo_danhba_vnp/ht_combo_kieudl_dongbo_vina'),
	lay_ds_du_lieu_dongbo: async (axios, data) => axios.post('/web-ccdv/dongbo_danhba_vnp/lay_ds_su_lieu_dongbo', data),
	kiemtra_diachi_vnp: async (axios, data) => axios.post('/web-ccdv/dongbo_danhba_vnp/kiemtra_diachi_vnp', data),
	dongbo_danhba_vnp_capnhat_dc: async (axios, data) => axios.post('/web-ccdv/dongbo_danhba_vnp/dongbo_danhba_vnp_capnhat_dc', data),
	lay_diachi_vnp: async (axios, data) => axios.post('/web-ccdv/dongbo_danhba_vnp/lay_diachi_vnp', data),
	// KiemTraSoGiayTo_VNP
	fn_thanhtoan: async (axios, data) => axios.post('/web-ccdv/dongbo_danhba_vnp/fn_thanhtoan', data),
	fn_thanhtoan_ktchuyenquyen: async (axios, data) => axios.post('/web-ccdv/dongbo_danhba_vnp/fn_thanhtoan_ktchuyenquyen', data),
}
