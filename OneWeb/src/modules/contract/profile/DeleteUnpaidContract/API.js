export default {
	ds_loaihd: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD'),
	ds_hdctt: async (axios, data) => axios.post('/web-hopdong/xoa_hd_tt/sp_lay_ds_hd_chuatt', data),
	delete_hdctt: async (axios, data) => axios.post('/web-hopdong/xoa_hd_tt/sp_del_hdkh_by_id_arr', data),
}
