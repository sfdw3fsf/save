export default {
	ds_thulao_phathd: async (axios, data) => axios.post('/web-quantri/thulao_phat_hoadon/sp_grv_thulao_phathd', data),
	ds_kyhoadon: async (axios, data) => axios.post('/web-quantri/thulao_phat_hoadon/sp_lst_kyhoadon', data),
	ds_loainhanvien: async (axios, data) => axios.post('/web-quantri/thulao_phat_hoadon/sp_lst_loainhanvien', data),
	ds_khuvuc: async (axios, data) => axios.post('/web-quantri/thulao_phat_hoadon/sp_lst_khuvuc', data),
	thulao_phathd_update: async (axios, data) => axios.post('/web-quantri/thulao_phat_hoadon/sp_ghilai_thulao_ck', data),
	thulao_phathd_delete: async (axios, data) => axios.post('/web-quantri/thulao_phat_hoadon/sp_del_thulao_ck', data)
}
