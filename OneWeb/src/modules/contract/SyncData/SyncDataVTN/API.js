export default {
	lay_danhsach_dichvuvt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
	lay_danhsach_loaihd:async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD'),
	lay_danhsach_loaihinhtb:async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
	lay_ds_kieu_ld:async (axios, loaihd_id, loaitb_id) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_kieu_ld', {
		loaihd_id: loaihd_id,
    	loaitb_id: loaitb_id
	}),
	sp_ht_quytrinh_gdv_kh:async (axios, dichvuvt_id, loaihd_id) => axios.post('/web-ccdv/dongbo-dulieu-vtn/sp_ht_quytrinh_gdv_kh', {
		ds_dichvu_vt: dichvuvt_id,
    	ds_loai_hd: loaihd_id
	}),

	sp_lay_thaotac_theo_quytrinh:async (axios, quytrinh_id, loaidv_id) => axios.post('/web-ccdv/dongbo-dulieu-vtn/sp_lay_thaotac_theo_quytrinh', {
		p_quytrinh_id: quytrinh_id,
    	p_loaidv_id: loaidv_id
	}),

	lay_all_thaotac_control: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_thaotac_control',{
        p_param: "",
        p_type:1
    }),

	lay_dstb_ims_db_vtn: async (axios, data) => axios.post('/web-ccdv/dongbo-dulieu-vtn/lay_dstb_ims_db_vtn', data),

	lay_ds_dangky_dvgt:async (axios, hdtb_id) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_dangky_dvgt', {
		vhdtb_id:hdtb_id
	}),

	fn_frmdongbo_vtn_kichhoat:async (axios, data) => axios.post('/web-ccdv/dongbo-dulieu-vtn/fn_frmdongbo_vtn_kichhoat',data),

	sp_lay_huonggiao_theo_quytrinh:async (axios, quytrinh_id, loaidv_id) => axios.post('/web-ccdv/dongbo-dulieu-vtn/sp_lay_huonggiao_theo_quytrinh',{
		p_quytrinh_id: quytrinh_id,
    	p_loaidv_id: loaidv_id
	}),

	fn_frmdongbo_vtn_hoancong:async (axios, data) => axios.post('/web-ccdv/dongbo-dulieu-vtn/fn_frmdongbo_vtn_hoancong',data),
	

	
	
	
}
