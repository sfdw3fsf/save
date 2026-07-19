export default {
  getDsLoaiGT: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_GT'),
  getDsNganHang: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_NGANHANG'),
  getDsHinhThucTT: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_HINHTHUC_TT'),
  getDsDonvi: (axios, data) => axios.post('/web-quantri/danhmuc/donvi/theo_loai', data),
  getDsLoaiKH: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_KH'),
  getDsLoaiHinhTB: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
  getDsDantoc: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DANTOC'),
  getDsNganhnghe: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_NGANHNGHE'),
  getDsQuoctich: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_QUOCTICH'),
  getDsDichvuVT: (axios, data) => axios.post('/web-baohong/TraCuuPhieuGiaoDenBH/sp_ht_dichvuvt_cbobox_lm', data),
  getDsDoituong: (axios) => axios.get('/web-hopdong/hopdong_dungthu/lay_ds_doituong'),
  getDsKieuLD: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD'),
  getDsThanhToan: (axios, id) => axios.get('/web-hopdong//hopdong/lay_ds_ma_thanhtoan', {params: {hdkhId: id}}),

  LAY_DSHD_THEO_MA_GD: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sp_ds_hopdong_theo_magd_dc', data),
  LAY_DS_DIACHI_THEO_HDKH_ID: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_diachi_theo_hdkh', {params: {hdKhId: id}}),
  LAY_DS_DIACHI_THEO_HDTT_ID: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_diachi_theo_hdtt', {params: {hdttId: id}}),
  LAY_DS_DIACHI_THEO_HDTB_ID: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_hdtb_id', data),
  LAY_DS_HOPDONG_TT_THEO_HDKH_ID: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh', {params: {hdKhId: id}}),
  LAY_DS_HOPDONG_TT_THEO_HDTT_ID: (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_ds_hopdong_tt_theo_hdtt_id', data),
  LAY_DS_HDTB_THEO_HDKH_TTHD: (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hdtb_theo_hdkh_tthd', data),
  LAY_DS_HDTB_HTHS: (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_hdtb_hths', data),
  LAY_DS_HOPDONG_TB_THEO_HDTB_ID: (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id', data),
  getDanhBaTheoMaTb: (axios, data) => axios.post('/web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB', data),

  map_id: (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),

  LAY_DS_DANGKY_DVGT: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_dangky_dvgt', data),
  LAY_DS_DANGKY_DVK: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_dangky_dvk', data),

  getDsThueBaoCoDinh: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh', {params: {hdtbId: id}}),
  getDsTramVT_CODINH: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_ds_donvi_theo_loaidv_v5', data),

  getDsThueBaoAdsl: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl', {params: {hdtbId: id}}),
  getDsLoaiTB_ADSL: (axios) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_loai_thietbi_adsl'),
  getDsTocDo_ADSL: (axios) => axios.get('/web-hopdong/phattrienthuebao/get_tocdo_adsl_by_dichvuvt_id/4'),
  getDsMucCuoc_ADSL: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_MUCCUOC'),
  getDsBRAS_ADSL: (axios) => axios.post('/web-ccdv/khaibaoims/sp_lay_ds_bras_adsl'),
  getDsDSLAM_ADSL: (axios, data) => axios.post('/web-ccdv/khaibaoims/sp_lay_ds_dslam_adsl', data),

  getDsThueBao_GP: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp', {params: {hdtbId: id}}),

  getDsThueBaoMWAN: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan', {params: {hdtbId: id}}),
  getDsTrangBi_MWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGBI'),
  getDsLoaiKenh_MWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_KENH'),
  getDsModem_MWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_MODEM'),
  getDsChuQuan_MWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_CHUQUAN'),
  getDsThoiHan_MWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_THOIHAN'),
  getDsThietBi_MWAN: (axios) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_tbdc_theo_dichvu', {"dichvuvt_id": 8}),
  getDsRouter_MWAN: (axios) => axios.post('/web-hopdong/thaydoiloaihinhtb/pe_v2', {}),

  getDsThueBao_IMS: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_ims', {params: {hdtbId: id}}),
  getDsThietBi_IMS: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_THIETBI_IMS'),

  getDsThueBao_TSL: (axios, id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_tls', {params: {hdtbId: id}}),
  getDsTocDoKenh_TSL: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),
  getDsThietBi_TSL: (axios) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_tbdc_theo_dichvu', {"dichvuvt_id": 9}),

  sp_ht_donvi_combobox: (axios, data) => axios.post('/web-quantri/thutienphatsinh/sp_ht_donvi_combobox', data),
  LAY_THONGTIN_DIACHI: (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_hienthittdiachi_tsl_cdlhtb', data),
  HOANTHIEN_HS_DICHCHUYEN: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/hoanthien_hs_dichchuyen', data),
  KT_TaoDB_Thang: (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/kt_taodb_thang', data),

  ChangePromotions: (axios, data) => axios.post('/tichhop/vasc/changePromotions', data),
  changeCustomerType: (axios, data) => axios.post('/tichhop/visa-app/changeCustomerType', data),
  Kiemtra_doituong_vdc: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/fn_kiemtra_doituong_vdc', data),
}
