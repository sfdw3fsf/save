/* eslint eqeqeq: 'off', quotes: 'off', camelcase: off */
import axios from 'axios'
export default {
  getListTelecomService: () => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_dulieu_theo_dieukien_dvvt', {}).then(rs => rs.data.data),
  getListSubscriptionType: () => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB').then(rs => rs.data.data),
  getListContractByTransactionCode: (data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),
  lay_ds_kieu_ld: (loaihdID, loaitbID) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_kieu_ld', {'loaihd_id': loaihdID, 'loaitb_id': loaitbID}).then(rs => (rs.data.data || []).map(r => ({ ID: r.kieuld_id, NAME: r.ten_kieuld }))),
  lay_ds_diachi_theo_hdtb_id: (hdtbID) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_hdtb_id', {'in_hdtb_id': hdtbID}).then(rs => rs.data.data),
  lay_ds_diachi_theo_dbtbid: (thuebaoID) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid', {'in_thuebao_id': thuebaoID}).then(rs => rs.data.data),
  lay_ds_donvi_theo_loaidv: (donViID, loaiDonViID) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_ds_donvi_theo_loaidv_v5', {
    'donvi_id': donViID,
    'loaidv_id': loaiDonViID
  }).then(rs => rs.data.data.map(r => ({...r, donvi_id: parseInt(r.donvi_id)}))),
  lay_dulieu_theo_dieukien_muccuoc_tb: (muccuoctbID) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_dulieu_theo_dieukien_muccuoc_tb', {}).then(rs => rs.data.data),
  lay_ds_hopdong_theo_ma_tb: (data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb', data).then(rs => rs.data.data),
  lay_ds_hopdong_theo_ma_gd: (maGD, loaihdId, tthd) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', {
    'in_ma_gd': maGD,
    'in_loaihd_id': loaihdId || 0,
    'in_tthd_id': tthd || 0,
    'in_donvi_dl_id': 0
  }).then(rs => rs.data.data || []),
  lay_ds_hdtb_theo_hdkh_tthd: (hdkhId, trangThaiHD) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hdtb_theo_hdkh_tthd', {'hdkh_id': hdkhId, 'tthd_id': trangThaiHD}).then(rs => rs.data.data),
  lay_donvi_thicong: (hdtbID, kieu, loaidv, kieudvID) => axios.post('web-hopdong/thanhly/sp_lay_donvi_thicong', {
    'kieu': kieu,
    'hdtb_id': hdtbID,
    'loaidv_id': loaidv,
    'kieudv_id': kieudvID}).then(rs => rs.data.data),
  lay_ds_thuebao_dichvu: (hdtbID, thuebaoID, loaitbID, kieu) => axios.post('web-hopdong/khoiphucthanhly/lay_ds_thuebao_dichvu', {
    'loaitb_id': loaitbID,
    'hdtb_id': hdtbID,
    'thuebao_id': thuebaoID,
    'kieu_id': kieu
  }).then(rs => rs.data.data),
  lay_ds_dangky_dvk: (hdID, loaiID) => axios.post('web-hopdong/khoiphucthanhly/lay_ds_dangky_dvk', {
    'hd_id': hdID,
    'loaiid': loaiID
  }).then(rs => rs.data.data),
  lay_tienhd_km_theo_hdtb: (hdtbID) => axios.post('web-hopdong/khoiphucthanhly/lay_tienhd_km_theo_hdtb', {'hdtb_id': hdtbID}).then(rs => rs.data.data),
  lay_danhba_theo_matb: (matb, dichvuvtID, donvidlID) => axios.post('web-hopdong/khoiphucthanhly/lay_danhba_theo_matb', {'in_ma_tb': matb, 'in_dichvuvt_id': dichvuvtID, 'in_donvi_dl_id': donvidlID}).then(rs => rs.data.data),
  lay_ds_db_adsl_theo_tbid: (tbID) => axios.post('web-ccdv/xuly_nghiemthubaohong/lay_ds_db_adsl_theo_tbid', {'thuebao_id': tbID}).then(rs => rs.data.data),
  lay_ds_db_ims_theo_tbid: (tbID) => axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_ims_theo_tbid', {'thuebao_id': tbID}).then(rs => rs.data.data),
  lay_ds_thamso_md: (kieu) => axios.post(`web-hopdong/thanhly/sp_lay_ds_thamso_md`, {'kieu_id': kieu}).then(rs => rs.data.data),
  sp_lay_db_theo_matb: (matb, donvidlID, dichvuvtID) => axios.post(`web-hopdong/khoiphucthanhly/sp_lay_db_theo_matb`, {'ma_tb': matb, 'donvi_dl_id': donvidlID, 'divhvuvt_id': dichvuvtID}).then(rs => rs.data.data),
  kiemtra_ma_tb_lap_hopdong_khac: (maTb, maGd, dvId, loaiHopDongId) => axios.post('web-hopdong/khoiphucthanhly/kiemtra_ma_tb_lap_hopdong_khac', {
    'ma_tb': maTb,
    'ma_gd': maGd,
    'dichvuvt_id': dvId,
    'loaihd_id': loaiHopDongId
  }).then(rs => rs.data.data),
  layhuonggiao_tiepnhan_v2: (loaiHDID, loaiTBID, tocDoID, muocCuocID, kieuID, loaiKenhID, DVID) => axios.post(`web-hopdong/khoiphucthanhly/layhuonggiao_tiepnhan_v2`, {
    'loaihd_id': loaiHDID,
    'loaitb_id': loaiTBID,
    'tocdo_id': tocDoID,
    'muccuoc_id': muocCuocID,
    'kieuld_id': kieuID,
    'loaikenh_id': loaiKenhID,
    'dichvuvt_id': DVID
  }).then(rs => rs.data.data || []),
  capnhat_status_hdtb: (hdtbID, status) => axios.post(`web-hopdong/khoiphucthanhly/capnhat_status_hdtb`, {
    'status': status,
    'hdtb_id': hdtbID
  }).then(rs => rs.data.data),
  update_huonggiao_tiepnhan: (hdtbID, huonggiaoID) => axios.post(`web-hopdong/khoiphucthanhly/update_huonggiao_tiepnhan`, {
    'hdtb_id': hdtbID,
    'huonggiao_id': huonggiaoID
  }).then(rs => rs.data.data),
  capnhat_goikt: (hdtbID) => axios.post(`web-hopdong/khoiphucthanhly/capnhat_goikt`, {
    'hdtb_id': hdtbID
  }).then(rs => rs.data.data),
  hien_thi_cbo_muc_cuoc: (mucuoctbID) => axios.post(`web-quantri/lay-dulieu/sp_tt_muccuoc_tb`, {
    'type': 4,
    'mucuoctb_id': mucuoctbID
  }).then(rs => rs.data.data || []),
  hien_thi_cbo_muc_cuoc_adsl: () => axios.post(`web-hopdong/khoiphucthanhly/lay_dulieu_theo_dieukien`, {}).then(rs => rs.data.data),
  lay_dulieu_theo_dieukien: (table, value, text, where, order) => axios.post(`web-hopdong/khoiphucthanhly/lay_dulieu_theo_dieukien`, {
    'schema': 'CSS',
    'table_name': table,
    'value': value,
    'text': text,
    'where': where,
    'order': order
  }).then(rs => rs.data.data || []),
  lay_ds_hopdong_thuebao_adsl: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_mgwan: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_ims: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_ims?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_gp: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_danhba_megawan: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_megawan?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_gp: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_gp?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_cntt: (thueBaoId) => axios.get(`web-hopdong//hopdong/lay_ds_danhba_cntt?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_danhsach_thuoctinh_v2: (loaitbID, tenBang, optList) => axios.post(`web-hopdong/khoiphucthanhly/lay_danhsach_thuoctinh_v2`, {
    'loaitb_id': loaitbID,
    'ten_bang': tenBang,
    'opt_list': optList
  }).then(rs => rs.data.data),
  lay_text_huongketnoi: (huongKN) => axios.post(`web-hopdong/khoiphucthanhly/fn_lay_text_huongketnoi_v2`, {'huong_kn': huongKN}).then(rs => rs.data.data),
  lay_ds_sudung_dvk: (dbID, loaiID) => axios.post(`web-hopdong/khoiphucthanhly/lay_ds_sudung_dvk`, {'db_id': dbID, 'loaiid': loaiID}).then(rs => rs.data.data),
  lay_tt_dichvu_khac: (dvkhacId) => axios.get(`web-tracuu/danhba/lay_tt_dichvu_khac?dvkhacId=${dvkhacId}`).then(rs => rs.data.data),
  get_keys: (keyname) => axios.post(`web-hopdong/khoiphucthanhly/get_keys`, {keyname}).then(rs => rs.data.data),
  sinh_magd: (loaiHopDongId) => axios.post(`web-hopdong/khoiphucthanhly/sinh_magd`, {'loaihd_id': loaiHopDongId}).then(rs => rs.data.data),
  sinh_magd_v2: (donviId, loaiHopDongId) => axios.post(`web-hopdong/khoiphucthanhly/sinh_magd_v2`, {'donvi_id': donviId, 'loaihd_id': loaiHopDongId}).then(rs => rs.data.data),
  // sp_lay_tienkhoanmuc_loaihd: (loaiHopDongId) => axios.post(`web-hopdong/tachnhapthuebao/sp_lay_tienkhoanmuc_loaihd`, {'loaihd_id': loaiHopDongId}).then(rs => rs.data.data),
  sp_lay_tienkhoanmuc_loaihd: (loaiHopDongId) => axios.post(`web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd`, {'vloaihd_id': loaiHopDongId}).then(rs => rs.data.data),
  lay_ds_dbkh_theo_khid: (hdkhId) => axios.post(`web-hopdong/CapNhatTraTruoc/lay_ds_dbkh_theo_khid`, { 'khachhang_id': hdkhId }).then(rs => rs.data.data),
  lay_ma_hd: (donviId) => axios.get(`web-hopdong/doisim/lay_ma_hd?donvi_id=${donviId}`).then(rs => rs.data.data),
  themmoi_hopdong_khoiphucthanhly_v2: (dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, huonggiao_tn, dsHDTB_IMS, dsHDTB_KV, dsHDTB_CNTT, dsHDTB_TSL) => {
    const dsHDTB_KV_upperKey = []
    for (const row of dsHDTB_KV.HDTB_KV) {
      const obj = {}
      for (const key in row) {
        if (Object.hasOwnProperty.call(row, key)) {
          obj[key.toUpperCase()] = row[key]
        }
      }
      dsHDTB_KV_upperKey.push(obj)
    }
    return axios.post(`web-hopdong/khoiphucthanhly/sp_themmoi_hopdong_khoiphucthanhly_v2`, {
      'js_hd_khachhang': JSON.stringify(dsHDKH),
      'js_hd_thuebao': JSON.stringify(dsHDTB),
      'js_ct_tienhd': JSON.stringify(dsCTTHD),
      'js_hdtb_adsl': JSON.stringify(dsHDTB_ADSL),
      'js_hdtb_cd': JSON.stringify(dsHDTB_CD),
      'js_hdtb_gp': JSON.stringify(dsHDTB_GP),
      'js_hdtb_mgwan': JSON.stringify(dsHDTB_MGW),
      'js_hdtb_ims': JSON.stringify(dsHDTB_IMS),
      'js_hdtb_tsl': JSON.stringify(dsHDTB_TSL),
      'js_hdtb_cntt': JSON.stringify(dsHDTB_CNTT),
      'js_hdtb_kv': JSON.stringify(dsHDTB_KV_upperKey),
      'js_dangky_dvgt': JSON.stringify(dsDK_DVGT),
      'js_dangky_dvk': JSON.stringify(dsDK_DVKHAC),
      'js_hdtb_dv': JSON.stringify(dsHDTBDV),
      'huonggiao_id': huonggiao_tn
    }).then(rs => rs.data)
  },
  kt_thaotac: (luongId, code) => axios.post(`web-hopdong/khoiphucthanhly/fn_kt_thaotac`, { 'luong_id': luongId, 'code': code }).then(rs => rs.data),
  lay_ds_db_cd_theo_tbid: (thuebaoId) => axios.post(`web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid`, {'thuebao_id': thuebaoId}).then(rs => rs.data.data),
  lay_ds_doitac: () => axios.post(`web-ccdv/khaibaotsl/sp_lay_ds_doitac`, {}).then(rs => rs.data.data.map(r => ({ ...r, doitac_id: parseInt(r.doitac_id) }))),
  lay_ds_hopdong_thuebao_theo_hdkh: (hdKhId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId=${hdKhId}`).then(rs => rs.data.data),
  xoa_hdkh_v2: (hdkhID, loaiHopDongID) => axios.post(`web-hopdong/khoiphucthanhly/xoa_hdkh_v2`, {'hdkh_id': hdkhID, 'loaihd_id': loaiHopDongID}).then(rs => rs.data),
  xoa_hdtb: (hdtbID) => axios.post(`web-hopdong/khoiphucthanhly/xoa_hdtb`, {'hdtb_id': hdtbID}).then(rs => rs.data),
  themmoi_hopdongtb_khoiphucthanhly_v2: (dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, huonggiao_tn, dsHDTB_IMS, dsHDTB_MGW, dsHDTB_TSL) => {
    return axios.post(`web-hopdong/khoiphucthanhly/sp_themmoi_hopdongtb_khoiphucthanhly_v2`, {
      "js_hd_thuebao": JSON.stringify(dsHDTB),
      "js_hdtb_adsl": JSON.stringify(dsHDTB_ADSL),
      "js_hdtb_cd": JSON.stringify(dsHDTB_CD),
      "js_hdtb_gp": JSON.stringify(dsHDTB_GP),
      "js_dangky_dvgt": JSON.stringify(dsDK_DVGT),
      "js_dangky_dvk": JSON.stringify(dsDK_DVKHAC),
      "js_ct_tienhd": JSON.stringify(dsCTTHD),
      "js_hdtb_dv": JSON.stringify(dsHDTBDV),
      "js_hdtb_ims": JSON.stringify(dsHDTB_IMS),
      "js_hdtb_mgwan": JSON.stringify(dsHDTB_MGW),
      "js_hdtb_tsl": JSON.stringify(dsHDTB_TSL),
      "huonggiao_id": huonggiao_tn
    }).then(rs => rs.data)
  },
  lay_ds_hdtb_theo_hdkh: (hdkhID) => axios.post(`web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_hdtb_theo_hdkh`, {'hdkh_id': hdkhID}).then(rs => rs.data.data),
  // -----------------------
  lay_ds_dbtb_theo_tbid: (data) => axios.post('web-hopdong/phattrienthuebao/lay_ds_dbtb_theo_tbid', data).then(rs => rs.data.data),
  lay_ds_dbtt_theo_ttid: (data) => axios.post('web-hopdong/phattrienthuebao/lay_ds_dbtt_theo_ttid', data).then(rs => rs.data.data),
  lay_ds_dk_lydohuy: (hdtbID) => axios.post('web-hopdong/thanhly/lay_ds_dk_lydohuy', {'in_hdtb_id': hdtbID}).then(rs => rs.data.data.map((r, i) => ({...r, stt: i + 1}))),
  lay_ds_hopdong_thuebao_codinh: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hdtb_tsl_theo_hdtb_id: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hdtb_tsl_theo_hdtb_id?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_cntt: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_cntt?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_thongtin_phieu_tn: (data) => axios.post('web-hopdong/thanhly/sp_lay_thongtin_phieu_tn', data).then(rs => rs.data.data),
  kt_laphopdong_trongthang: (data) => axios.post('web-hopdong/thanhly/kt_laphopdong_trongthang', data).then(rs => rs.data.data),
  sl_nhanvien: (data) => axios.post('web-hopdong/thanhly/sp_sl_nhanvien', data).then(rs => rs.data.data),
  lay_ds_chitiet_khuyenmai: (thueBaoId, kieu) => axios.get(`web-hopdong/hopdong/lay_ds_chitiet_khuyenmai?thueBaoId=${thueBaoId}&kieu=${kieu}`).then(rs => rs.data.data),
  tracuu_no_thanhtoan: (thanhToanId) => axios.get(`web-hopdong/hopdong/tracuu_no_thanhtoan?thanhToanId=${thanhToanId}`).then(rs => rs.data.data),
  tracuu_no_thuebao: (thuaBaoId) => axios.get(`web-hopdong/hopdong/tracuu_no_thuebao?thueBaoId=${thuaBaoId}`).then(rs => rs.data.data),
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
  lay_ds_hopdong_tb_theo_hdtb_id: (hdtbId) => axios.post(`web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id`, {'in_hdtb_id': hdtbId}).then(rs => rs.data.data),
  thanh_ly_map_id: (hdtbId) => axios.post(`web-hopdong/thanhly/thanh_ly_map_id`, {'id_neo': 'quytrinh_id', 'in_table': 'hd_thuebao', 'in_dk': ` where hdtb_id=${hdtbId}`}).then(rs => rs.data.data),
  map_id: (col, table, condition) => axios.post(`web-hopdong/thanhly/thanh_ly_map_id`, {'id_neo': col, 'in_table': table, 'in_dk': ` ${condition}`}).then(rs => rs.data.data),
  fn_tt_nhanvien:
  (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien`, { param: param, type: type })
      .then(rs => rs.data.data),
  fn_tt_loaihinh_tb:
  (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb`, { param: param, type: type })
      .then(rs => rs.data.data),
  fn_tt_kieu_ld:
  (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_kieu_ld`, { param: param, type: type })
      .then(rs => rs.data.data),
  fn_tt_muccuoc_tb:
  (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_muccuoc_tb`, { param: param, type: type })
      .then(rs => rs.data.data),
  fn_tt_hd_thuebao:
  (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, { param: param, type: type })
      .then(rs => rs.data.data),
  fn_giaophieu_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_giaophieu_chamdutsd`, data).then(rs => rs.data.data),
  themtb_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_themtb_chamdutsd`, data).then(rs => rs.data.data),
  capnhat_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_capnhat_chamdutsd`, data).then(rs => rs.data.data),
  lay_ds_isdn_theo_thuebao_id: (tbid) => axios.post(`web-hopdong/thanhly/lay_ds_isdn_theo_thuebao_id`, {'thuebao_id': tbid}).then(rs => rs.data.data),
  fn_xoa_thanhly: (hdtbID) => axios.post(`web-hopdong/thanhly/fn_xoa_thanhly`, {'hdtb_id': hdtbID}).then(rs => rs.data.data),
  LAY_DONVI_THEO_LOAIDV_DIACHI_LD: (thuebao_id) => axios.post(`web-hopdong/thaydoiloaihinhtb/ur2_1_035_038_temp`, {thuebao_id}).then(rs => rs.data.data),
  lay_ds_danhba_tsl: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_tsl?thueBaoId=${thueBaoId}`).then(rs => rs.data.data || []),
  // thoains-fix ATT
  lay_dulieu_theo_dieukien_toc_do_kenh: () => axios.post(`web-hopdong/laydulieu/sp_tt_tocdo_kenh`,{ p_type: 2 } ).then(rs => rs.data.data || []),
  lay_dulieu_theo_dieukien_muc_cuoc: () => axios.post(`web-hopdong/laydulieu/sp_tt_muccuoc`,{ p_type: 2 } ).then(rs => rs.data.data || []),
  lay_dulieu_theo_dieukien_thue_bao :(type, param)=> axios.post(`web-quantri/lay-dulieu/sp_tt_db_thuebao`,{ type, param} ).then(rs => rs.data.data ),
  //post_sp_lay_doituong_tb : async (axios, data) => axios.post('/web-hopdong/tachnhapthuebao/sp_lay_doituong_tb',data),
  sp_lay_doituong_tb: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_doituong_tb?nhanvienId=${data.nhanvienId}`),
  lay_dulieu_theo_dieukien_tocdo_adsl :(loaitbID)=> axios.post(`web-ccdv/laydulieu/sp_tt_tocdo_adsl`, { p_type : 2, p_param : loaitbID }
 ).then(rs => rs.data.data ),
  capnhat_hopdong_khoiphuc: (data) => {
    const dsHDTB_KV_upperKey = []
    for (const row of data.dsHDTB_KV.HDTB_KV) {
      const obj = {}
      for (const key in row) {
        if (Object.hasOwnProperty.call(row, key)) {
          obj[key.toUpperCase()] = row[key]
        }
      }
      dsHDTB_KV_upperKey.push(obj)
    }
    return axios.post(`web-hopdong/khoiphucthanhly/sp_capnhat_hopdong_khoiphuc`, {
      "p_js_hd_khachhang": JSON.stringify(data.dsHDKH),
      "p_js_hd_thuebao": JSON.stringify(data.dsHDTB),
      "p_js_hdtb_adsl": JSON.stringify(data.dsHDTB_ADSL),
      "p_js_hdtb_cd": JSON.stringify(data.dsHDTB_CD),
      "p_js_hdtb_gp": JSON.stringify(data.dsHDTB_GP),
      "p_js_hdtb_mgwan": JSON.stringify(data.dsHDTB_MGW),
      "p_js_dangky_dvgt": JSON.stringify(data.dsDK_DVGT),
      "p_js_dangky_dvk": JSON.stringify(data.dsDK_DVKHAC),
      "p_js_tiend_hd": JSON.stringify(data.dsCTTHD),
      "p_js_hdtb_dv": JSON.stringify(data.dsHDTBDV),
      "p_js_hdtb_ims": JSON.stringify(data.dsHDTB_IMS),
      "p_js_hdtb_dungthu": JSON.stringify(data.dsHDTB_DUNGTHU),
      'p_js_hdtb_kv': JSON.stringify(dsHDTB_KV_upperKey),
    }).then(rs => rs.data)
  }, 
  capnhat_hopdong_khoiphuc_v3: (data) => {
    const dsHDTB_KV_upperKey = []
    for (const row of data.dsHDTB_KV.HDTB_KV) {
      const obj = {}
      for (const key in row) {
        if (Object.hasOwnProperty.call(row, key)) {
          obj[key.toUpperCase()] = row[key]
        }
      }
      dsHDTB_KV_upperKey.push(obj)
    }
    return axios.post(`web-hopdong/khoiphucthanhly/sp_capnhat_hopdong_khoiphuc_v3`, {
      "p_js_hd_khachhang": JSON.stringify(data.dsHDKH),
      "p_js_hd_thuebao": JSON.stringify(data.dsHDTB),
      "p_js_hdtb_adsl": JSON.stringify(data.dsHDTB_ADSL),
      "p_js_hdtb_cd": JSON.stringify(data.dsHDTB_CD),
      "p_js_hdtb_gp": JSON.stringify(data.dsHDTB_GP),
      "p_js_hdtb_mgwan": JSON.stringify(data.dsHDTB_MGW),
      "p_js_dangky_dvgt": JSON.stringify(data.dsDK_DVGT),
      "p_js_dangky_dvk": JSON.stringify(data.dsDK_DVKHAC),
      "p_js_tiend_hd": JSON.stringify(data.dsCTTHD),
      "p_js_hdtb_dv": JSON.stringify(data.dsHDTBDV),
      "p_js_hdtb_ims": JSON.stringify(data.dsHDTB_IMS),
      "p_js_hdtb_dungthu": JSON.stringify(data.dsHDTB_DUNGTHU),
      'p_js_hdtb_kv': JSON.stringify(dsHDTB_KV_upperKey),
    }).then(rs => rs.data)
  },
  get_fn_ktra_tocdo_dk_greennet: async (axios, data) => axios.get('/web-tracuu/hopdong/fn_ktra_tocdo_dk_greennet',data),
  get_lay_tien_khoanmuc_ld_v2 : async (axios, data) => axios.get('/web-hopdong/lapdatmoi/lay_tien_khoanmuc_ld_v3',data),
  kiemtra_hd_bancheo_tc: async (axios, data) => axios.post(`/web-hopdong/thaydoidichvu/kiemtra_hd_bancheo_tc`, data),
}