import axios from 'axios'
export default {
  getListTelecomService: () => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', {}),
  getListSubscriptionType: () => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', {}),
  getListRequestType: (loaiThueBaoID, loaiHopDongID) => axios.get(`/web-hopdong/thanhly/lay_ds_kieu_ld_thanhly?loaitbId=${loaiThueBaoID}&loaihdId=${loaiHopDongID}`).then(rs => rs.data.data),
  getListChanelSpeed: () => axios.post('/web-hopdong/thanhly/sp_lay_dulieu_theo_dieukien', {}),
  getListSatellite: (data) => axios.post('/web-hopdong/thanhly/sp_lay_ds_tatca_vetinh', data),
  lay_dulieu_theo_dieukien: (table, value, text, where, order) => axios.post(`web-hopdong/khoiphucthanhly/lay_dulieu_theo_dieukien`, {
    'schema': 'CSS',
    'table_name': table,
    'value': value,
    'text': text,
    'where': where,
    'order': order
  }).then(rs => rs.data.data),
  lay_ds_hopdong_theo_ma_gd: (maGD) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', {
    'in_ma_gd': maGD,
    'in_loaihd_id': 4,
    'in_tthd_id': 1,
    'in_donvi_dl_id': 0
  }).then(rs => rs.data.data),
  lay_ds_hdtb_theo_hdkh_tthd: (hdkhId, trangThaiHD) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hdtb_theo_hdkh_tthd', {'hdkh_id': hdkhId, 'tthd_id': trangThaiHD}).then(rs => rs.data.data),
  lay_ds_hopdong_theo_ma_tb: (data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb', data).then(rs => rs.data.data || []),
  lay_ds_dbtb_theo_tbid: (data) => axios.post('web-hopdong/phattrienthuebao/lay_ds_dbtb_theo_tbid', data).then(rs => rs.data.data),
  lay_ds_dbtt_theo_ttid: (data) => axios.post('web-hopdong/phattrienthuebao/lay_ds_dbtt_theo_ttid', data).then(rs => rs.data.data || []),
  lay_ds_dk_lydohuy: (hdtbID) => axios.post('web-hopdong/thanhly/lay_ds_dk_lydohuy', {'in_hdtb_id': hdtbID}).then(rs => rs.data.data),
  lay_ds_lydo_huy: (loaildID, loaitbID, nhomHuyID, kieu) => axios.post('web-hopdong/thanhly/lay_ds_lydo_huy', {
    'loaild_id': loaildID,
    'loaitb_id': loaitbID,
    'nhomhuy_id': nhomHuyID,
    'kieu': kieu
  }).then(rs => rs.data.data),
  lay_ds_nhomhuy_theo_loaitb: (loaildID, loaitbID, kieu) => axios.post('web-hopdong/thanhly/lay_ds_nhomhuy_theo_loaitb', {
    'loaild_id': loaildID,
    'loaitb_id': loaitbID,
    'kieu': kieu
  }).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_codinh: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_adsl: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_mgwan: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hdtb_tsl_theo_hdtb_id: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hdtb_tsl_theo_hdtb_id?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_gp: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_cntt: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_cntt?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_danhba_cntt: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_cntt?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_adsl: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_codinh: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_codinh?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_dd: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_dd?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_gp: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_gp?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_ims: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_ims?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_megaeys: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_megaeys?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_megawan: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_megawan?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_tsl: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_tsl?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_thongtin_phieu_tn: (data) => axios.post('web-hopdong/thanhly/sp_lay_thongtin_phieu_tn', data).then(rs => rs.data.data || []),
  lay_donvi_thicong: (data) => axios.post('web-hopdong/thanhly/sp_lay_donvi_thicong', data).then(rs => rs.data.data),
  lay_danhba_theo_matb: (data) => axios.post('web-hopdong/khoiphucthanhly/lay_danhba_theo_matb', data).then(rs => rs.data.data || []),
  kiemtra_ma_tb_lap_hopdong_khac: (maTb, maGd, dvId, loaiHopDongId) => axios.post('web-hopdong/khoiphucthanhly/kiemtra_ma_tb_lap_hopdong_khac', {
    'ma_tb': maTb,
    'ma_gd': maGd,
    'dichvuvt_id': dvId,
    'loaihd_id': loaiHopDongId
  }).then(rs => rs.data.data),
  kt_laphopdong_trongthang: (data) => axios.post('web-hopdong/thanhly/kt_laphopdong_trongthang', data).then(rs => rs.data.data || []),
  sl_nhanvien: (data) => axios.post('web-hopdong/thanhly/sp_sl_nhanvien', data).then(rs => rs.data.data || []),
  lay_ds_chitiet_khuyenmai: (thueBaoId, kieu) => axios.get(`web-hopdong/hopdong/lay_ds_chitiet_khuyenmai?thueBaoId=${thueBaoId}&kieu=${kieu}`).then(rs => rs.data.data),
  tracuu_no_thanhtoan: (thanhToanId) => axios.get(`web-hopdong/hopdong/tracuu_no_thanhtoan?thanhToanId=${thanhToanId}`).then(rs => rs.data.data),
  tracuu_no_thuebao: (thuaBaoId) => axios.get(`web-hopdong/hopdong/tracuu_no_thuebao?thueBaoId=${thuaBaoId}`).then(rs => rs.data.data),
  lay_text_huongketnoi: (huongKNId) => axios.post(`web-hopdong/khoiphucthanhly/fn_lay_text_huongketnoi_v2`, {'huong_kn': huongKNId}).then(rs => rs.data.data),
  lay_sl_hd: (hpkhId) => axios.post(`web-hopdong/thanhly/sp_sl_hd`, {'hdkh_id': hpkhId}).then(rs => rs.data.data),
  lay_huonggiao_theo_quytrinh: (quytrinhId, tthdId) => axios.get(`web-hopdong/danhmuc/ds_huonggiao/${quytrinhId}/${tthdId}`).then(rs => rs.data.data),
  lay_ds_thaotac_theo_luong_id_code: (phanvungId, luongId) => axios.post(`web-hopdong/thanhly/sp_lay_ds_thaotac_theo_luong_id_code`, {
    'phanvung_id': phanvungId,
    'code': 'VAT_TU_MOI,VAT_TU,DIEUKIEN_GIAOPHIEU_BP_TIEPTHEO,TAMTHU_VT',
    'luong_id': luongId
  }).then(rs => rs.data.data),
  lay_ct_thaotac_control: (thaotacId) => axios.post(`web-hopdong/thanhly/sp_lay_ct_thaotac_control`, {'phanvung_id': 26, 'thaotac_id': thaotacId}).then(rs => rs.data.data),
  lay_ds_thaotac_theo_quytring_id: (quytrinhId) => axios.post(`web-hopdong/thanhly/sp_lay_ds_thaotac_theo_quytrinh_id`, {'quytrinh_id': quytrinhId}).then(rs => rs.data.data),
  tinh_cuoc_nong: (thuebaoId, thang) => axios.post(`web-hopdong/thanhly/tinh_cuoc_nong`, {'thuebao_id': thuebaoId, 'thang': thang}).then(rs => rs.data.data),
  lay_ds_thamso_md: (data) => axios.post(`web-hopdong/thanhly/sp_lay_ds_thamso_md`, data).then(rs => rs.data.data),
  lay_ds_hopdong_tb_theo_hdtb_id: (hdtbId) => axios.post(`web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id`, {'in_hdtb_id': hdtbId}).then(rs => rs.data.data),
  map_id: (col, table, condition) => axios.post(`web-hopdong/thanhly/thanh_ly_map_id`, {'id_neo': col, 'in_table': table, 'in_dk': ` ${condition}`}).then(rs => rs.data.data),
  fn_tt_db_khachhang: (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_khachhang`,
      { param: param, type: type }
    ).then(rs => rs.data.data),
  fn_tt_db_thuebao: (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao`,
      { param: param, type: type }
    ).then(rs => rs.data.data),
  fn_tt_hd_thuebao: (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`,
      { param: param, type: type }
    ).then(rs => rs.data.data),
  get_keys: (keyname) => axios.post(`web-hopdong/khoiphucthanhly/get_keys`, {keyname}).then(rs => rs.data.data),
  fn_giaophieu_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_giaophieu_chamdutsd`, data).then(rs => rs.data.data),
  themtb_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_themtb_chamdutsd`, data).then(rs => rs.data),
  capnhat_chamdutsd: (data) => {
    return axios.post(`web-hopdong/thanhly/fn_capnhat_chamdutsd`, data).then(rs => rs.data)
  },
  lay_ds_isdn_theo_thuebao_id: (tbid) => axios.post(`web-hopdong/thanhly/lay_ds_isdn_theo_thuebao_id`, {'thuebao_id': tbid}).then(rs => rs.data.data),
  sinh_magd_v2: (donviId, loaiHopDongId) => axios.post(`web-hopdong/khoiphucthanhly/sinh_magd_v2`, {'donvi_id': donviId, 'loaihd_id': loaiHopDongId}).then(rs => rs.data.data),
  sinh_magd: (loaiHopDongId) => axios.post(`web-hopdong/khoiphucthanhly/sinh_magd`, {'loaihd_id': loaiHopDongId}).then(rs => rs.data.data),
  layhuonggiao_tiepnhan_v2: (loaiHDID, loaiTBID, tocDoID, muocCuocID, kieuID, loaiKenhID, DVID) => axios.post(`web-hopdong/khoiphucthanhly/layhuonggiao_tiepnhan_v2`, {
    'loaihd_id': loaiHDID,
    'loaitb_id': loaiTBID,
    'tocdo_id': tocDoID,
    'muccuoc_id': muocCuocID,
    'kieuld_id': kieuID,
    'loaikenh_id': loaiKenhID,
    'dichvuvt_id': DVID
  }).then(rs => rs.data.data),
  lay_ds_hd_tbdd_theo_hdkh_id: (hdkh_id) => axios.post('web-hopdong/thanhly/lay_ds_hd_tbdd_theo_hdkh_id', {
    "hdkh_id": hdkh_id
  }).then(rs => rs.data.data),
  LayHuongGiao_TiepNhan: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_huonggiao_tiepnhan', data),
  lay_ds_hdtb_theo_hdkh: (hdkhID) => axios.post(`web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_hdtb_theo_hdkh`, {'hdkh_id': hdkhID}).then(rs => rs.data.data),
  xoa_hdkh_v2: (hdkhID, loaiHopDongID) => axios.post(`web-hopdong/khoiphucthanhly/xoa_hdkh_v2`, {'hdkh_id': hdkhID, 'loaihd_id': loaiHopDongID}).then(rs => rs.data),
  xoa_hdtb: (hdtbID) => axios.post(`web-hopdong/khoiphucthanhly/xoa_hdtb`, {'hdtb_id': hdtbID}).then(rs => rs.data),
  fn_xoa_thanhly: (hdtbID) => axios.post(`web-hopdong/thanhly/fn_xoa_thanhly`, {'hdtb_id': hdtbID}).then(rs => rs.data.data),
  lay_ds_tb_cung_madoicap: (thueBaoID, kieu) => axios.post(`web-hopdong/thanhly/lay_ds_tb_cung_madoicap`, {'thuebao_id': thueBaoID, 'kieu': kieu}).then(rs => rs.data.data),
  lay_danhsach_thuoctinh_v2: (loaitbId, optList) => axios.post('web-hopdong/khoiphucthanhly/lay_danhsach_thuoctinh_v2', {
    "loaitb_id": loaitbId, "opt_list": optList
  }).then(rs => rs.data.data),
  sp_lay_ds_dbtb_dv: (thuebaoId) => axios.post('web-hopdong/dichvu-ca/sp_lay_ds_dbtb_dv', {
    "thuebao_id": thuebaoId
  }).then(rs => rs.data.data || []),
  kiemtra_tb_mytv_chinhphu: (maTB, kieu) => axios.post('web-hopdong/thaydoidichvu/kiemtra_tb_mytv_chinhphu', {ma_tb: maTB, kieu: kieu}).then(rs => rs.data.data || []),
  lay_ds_thamso_md_mats: (mats) => axios.post('web-ccdv/khaibaotsl/lay_ds_thamso_md_mats', {ma_ts: mats}).then(rs => rs.data.data || []),
  sp_lay_ds_hdtb_khac_moi_theo_hdkh_id: (hdkhId) => axios.post(`web-hopdong/thanhly/sp_lay_ds_hdtb_khac_moi_theo_hdkh_id`, { 'hdkh_id': hdkhId }).then(rs => rs.data.data || []),
  //FIX ATT
  lay_dulieu_theo_dieukien_hd_thuebao:(hdkh_cha_id)=> axios.post(`web-quantri/lay-dulieu/sp_tt_hd_thuebao`,  { type: 3, param: hdkh_cha_id }).then(rs => rs.data.data || []),
  ts_laphd_thanhly_danhsach_hopdong: (matinh, userid) => axios.post(`ccbs/executor/ts_laphd_thanhly_danhsach_hopdong`, {
    "ma_tinh": matinh,
    "userid": userid
  }).then(rs => rs.data.data || []),
  ts_xoa_hd_thanhly: (matinh, userid, mahd) => axios.post(`ccbs/executor/ts_xoa_hd_thanhly`, {
    "ma_tinh": matinh,
    "ma_hd": mahd,
    "userid": userid
  }).then(rs => rs.data || []),
  get_app_config: () => axios.post(`web-hopdong/hopdong/get_app_config`, {
    "keyname": "OPEN_SYNC_VINA"
  }).then(rs => rs.data.data),
  lay_luong_dv_hdtb:(data) => axios.get('web-hopdong/hopdong/lay_luong_dv_hdtb?vhdtb_id=' + data.hdtb_id),
  capnhat_hdtb_dvi_cntt:(data) => axios.post('web-hopdong/hopdong/capnhat_hdtb_dvi_cntt', data),
  kt_soluong_file_ca_v3: async (data) => axios.post('/web-hopdong/thuhoi-chungthuca-loi/kt_soluong_file_ca_v3', data),
}
