export default {
	// BSS-87351_1.1 - Hàm khởi tạo
	fn_khaibao_api: async (axios, hdtb_id) => axios.post(`/web-quantri/sms-brandname/fn_khaibao_api`, {
		vhdtb_id: hdtb_id
	}),
	// BSS-87351_1.2 - LoadThongTin();
	sp_lay_danhmuc_khoitao_acc_khdl: async (axios) => axios.post(`/web-quantri/sms-brandname/sp_lay_danhmuc_khoitao_acc_khdl`,{}),
	// BSS-87351_1.3 - LoadThongTinKhachHang();
	sp_lay_thongtin_khoitao_acc_khdl: async (axios, hdtb_id) => axios.post('/web-quantri/sms-brandname/sp_lay_thongtin_khoitao_acc_khdl', {
		vhdtb_id: hdtb_id
	}),
	// BSS-87351_5.1 - btnNhapMoi_Click
	fn_capnhat_khachhang_daily_brn: async (axios, data) => axios.post('/web-quantri/sms-brandname/fn_capnhat_khachhang_daily_brn', {
		vhdtb_id: data.hdtb_id,
		vagent_id: data.agent_id,
		vadser_id: data.adser_id,
		vcontract_id: data.contract_id,
		vusername: data.username,
		vpassword: data.password,
		vjson: data.json
	}),
	// BSS-80223_5.2 - btnCapNhat_Click
	fn_capnhat_thongtin_khachhang_daily_brn: async (axios, data) => axios.post('/web-quantri/sms-brandname/fn_capnhat_thongtin_khachhang_daily_brn', {
		vhdtb_id: data.hdtb_id,
		vjson: data.json
	}),

	TAO_KHTT: async (axios, data) => axios.post('/tichhop/brandname/TAO_KHTT', data),

}
