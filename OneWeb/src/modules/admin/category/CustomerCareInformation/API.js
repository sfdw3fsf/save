export default {
	getDSNganhNghe: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_NGANHNGHE'),
	getDSQuocTich: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_QUOCTICH'),
	getDSDanToc: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DANTOC'),
	getDSDoiTuongCS: async (axios, data) => axios.post('/web-tracuu/thongtin_cskh/sp_lay_ds_doituong_cs'),
	getDSSoThich: async (axios, data) => axios.post('/web-tracuu/thongtin_cskh/sp_lay_ds_sothich'),
	getDSSothichTheoDT: async (axios, data) => axios.post('/web-tracuu/thongtin_cskh/lay_sothich_kh_theo_thongtin_id', data),
	getDBTB: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_db_theo_matb', data),
	getDSCSKH: async (axios, data) => axios.post('/web-tracuu/thongtin_cskh/lay_thongtin_cskh_theo_makh', data),
	updateTTCSKH: async (axios, data) => axios.post('/web-tracuu/thongtin_cskh/fn_capnhat_thongtin_cskh', data),
	addTTCSKH: async (axios, data) => axios.post('/web-tracuu/thongtin_cskh/fn_them_thongtin_cskh', data),
	deleteTTCSKH: async (axios, data) => axios.post('/web-tracuu/thongtin_cskh/fn_xoa_thongtin_cskh', data),
}
