export default {
	// frmGiaoPhieu_PH_002 - Lấy danh sách dịch vụ viễn thông theo quy trình ID cho combobox Dịch vụ
	sp_giaophieu_ph_loaddichvuvt: async (axios, quytrinh_id) => axios.get(`/web-ccdv/giaophieu-phoihop/sp_giaophieu_ph_loaddichvuvt?quytrinh_id=${quytrinh_id}`),
	// frmGiaoPhieu_PH_003 - Lấy danh sách hướng giao cho combobox Hướng giao
	sp_lay_ds_huonggiao_ph: async (axios, quytrinh_id) => axios.get(`/web-ccdv/giaophieu-phoihop/sp_lay_ds_huonggiao_ph?quytrinh_id=${quytrinh_id}`),
	// frmGiaoPhieu_PH_004 - Lấy dữ liệu cho lưới Danh sách phiếu yêu cầu
	sp_lay_ds_hdtb_phieu_ph: async (axios, data) => axios.post('/web-ccdv/giaophieu-phoihop/sp_lay_ds_hdtb_phieu_ph', {
		huonggiao_id: data.huonggiao_id,
		quytrinh_id: data.quytrinh_id,
		nhanvien_id: data.nhanvien_id,
		dichvuvt_id: data.dichvuvt_id,
		tungay: data.tungay,
		denngay: data.denngay,
	}),
	// frmGiaoPhieu_PH_005 - Lấy dữ liệu cho lưới Danh sách phiếu đã giao đơn vị
	lay_danhsach_phieu_ph_dagiao: async (axios, data) => axios.post('/web-ccdv/giaophieu-phoihop/lay_danhsach_phieu_ph_dagiao', {
		phieu_cha_id: data.phieu_cha_id,
		nhanvien_giao_id: data.nhanvien_giao_id,
	}),
	// frmGiaoPhieu_PH_006 - Lấy danh sách don vi cho combobox Đơn vị nhận
	sp_lay_ds_donvi_nhan_phieu_ph: async (axios, data) => axios.post('/web-ccdv/giaophieu-phoihop/sp_lay_ds_donvi_nhan_phieu_ph', {
		huonggiao_id: data.huonggiao_id,
		quytrinh_id: data.quytrinh_id,
		phieu_id: data.phieu_id,
		nhanvien_id: data.nhanvien_id,
	}),
	// frmGiaoPhieu_PH_009 - Click nút Giao phiếu
	fn_giaophieu_ph: async (axios, data) => axios.post('/web-ccdv/giaophieu-phoihop/fn_giaophieu_ph', {
		phieu_id: data.phieu_id,
		huonggiao_id: data.huonggiao_id,
		quytrinh_id: data.quytrinh_id,
		donvi_nhan_id: data.donvi_nhan_id,
		nhanvien_giao_id: data.nhanvien_giao_id,
		noidung: data.noidung,
		may_cn: data.may_cn,
		nguoi_cn: data.nguoi_cn,
		ip_cn: data.ip_cn,
	}),
	// frmGiaoPhieu_PH_010 - Click nút X trên lưới Danh sách phiếu đã giao đơn vị
	fn_huygiao_ph_thicong: async (axios, phieu_id) => axios.post('/web-ccdv/giaophieu-phoihop/fn_huygiao_ph_thicong', {
		phieu_id: phieu_id
	}),
}
