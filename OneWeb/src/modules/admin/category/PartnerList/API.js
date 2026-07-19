export default {
	ds_nganhang: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_NGANHANG'),
	ds_doitac: async (axios, data) => axios.post('/web-toanha/danhmuc-doitac/sp_lay_tt_doitac', data),
	update_doitac: async (axios, data) => axios.post('/web-toanha/danhmuc-doitac/sp_ghilai_doitac', data),
	delete_doitac: async (axios, data) => axios.post('/web-toanha/danhmuc-doitac/sp_del_doitac', data),
}
