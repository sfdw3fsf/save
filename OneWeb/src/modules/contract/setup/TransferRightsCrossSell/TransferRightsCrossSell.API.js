import axios from 'axios'
import { data } from 'jquery'
import cmUtil from '@/utils/commonFunction'

export default {
  getListTelecomService: () => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_dulieu_theo_dieukien_dvvt', {}).then(rs => rs.data.data),
  sp_ht_dichvuvt_combobox: (dsdichvuvt_id) => axios.post('/web-thicong/thanhly-thuebao/sp_ht_dichvuvt_combobox', {
    dichvu_vt_id: dsdichvuvt_id
  }).then(rs => rs.data.data),
  HT_DichVuVT_Combobox: () => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  getListSubscriptionType: () => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB').then(rs => rs.data.data),
  getListRequestType: (loaiThueBaoID, loaiHopDongID) => axios.get(`/web-hopdong/thanhly/lay_ds_kieu_ld_thanhly?loaitbId=${loaiThueBaoID}&loaihdId=${loaiHopDongID}`).then(rs => rs.data.data.map(r => ({ ...r, ID: parseInt(r.ID) }))),
  getListChanelSpeed: () => axios.post('/web-hopdong/thanhly/sp_lay_dulieu_theo_dieukien', {}),
  HT_TatCa_VeTinh_Combobox: (loaidv_id) => axios.post('/web-hopdong/thanhly/sp_lay_ds_tatca_vetinh', { loaidv_id: loaidv_id }).then(rs => rs.data.data),
  layDsTocDoADSL: () => axios.post('/web-hopdong/thanhly/sp_lay_dulieu_theo_dieukien', {}).then(rs => rs.data.data.map(i => ({ ...i, toc_do: i.thuonghieu }))),
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
    'in_loaihd_id': 0,
    'in_tthd_id': 0,
    'in_donvi_dl_id': 0
  }).then(rs => rs.data.data),
  lay_ds_hdtb_theo_hdkh_tthd: (hdkhId, trangThaiHD) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hdtb_theo_hdkh_tthd', {'hdkh_id': hdkhId, 'tthd_id': trangThaiHD}).then(rs => rs.data.data.map(r => cmUtil.transformKeyToLower(r))),
  LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC: (hdtb_id) => axios.post(`web-tracuu/thuebaoims/lay_ds_hopdong_tb_theo_hdtb_id`, { "in_hdtb_id": hdtb_id }).then(rs => rs.data.data),
  lay_ds_hopdong_theo_ma_tb: (
    ma_tb,
    in_loaihd_id,
    in_donvi_id,
    in_tthd_id,
    in_nhanvien_id,
    in_donvi_dl_id,
    in_dichvuvt_id,
    in_loaitb_id
  ) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb', {
    ma_tb,
    in_loaihd_id,
    in_donvi_id,
    in_tthd_id,
    in_nhanvien_id,
    in_donvi_dl_id,
    in_dichvuvt_id,
    in_loaitb_id
  }).then(rs => rs.data.data),
  lay_ds_dbtb_theo_tbid: (thuebao_id) => axios.post('web-hopdong/phattrienthuebao/lay_ds_dbtb_theo_tbid', {"thuebao_id": thuebao_id}).then(rs => rs.data.data),
  lay_ds_dbtt_theo_ttid: (thanhtoan_id) => axios.post('web-hopdong/phattrienthuebao/lay_ds_dbtt_theo_ttid', {"thanhtoan_id": thanhtoan_id}).then(rs => rs.data.data),
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
  lay_thongtin_phieu_tn: (hdtb_id, phanvung_id) => axios.post('web-hopdong/thanhly/sp_lay_thongtin_phieu_tn', {
    'hdtb_id': hdtb_id // 2115443
  }).then(rs => rs.data.data),
  Lay_DonVi_ThiCong: (phanvung_id, hdtb_id, kieu, loaidv_id, kieudv_id) => axios.post('web-hopdong/thanhly/sp_lay_donvi_thicong', {
    'phanvung_id': phanvung_id,
    'hdtb_id': hdtb_id, // 2115443
    'loaidv_id': loaidv_id,
    'kieudv_id': kieudv_id, // Mặc định
    'kieu': kieu
  }).then(rs => rs.data.data),
  lay_danhba_theo_matb: (in_ma_tb, in_dichvuvt_id, in_donvi_dl_id) => axios.post('web-hopdong/khoiphucthanhly/lay_danhba_theo_matb', {
    'in_ma_tb': in_ma_tb, // 3529779
    'in_dichvuvt_id': in_dichvuvt_id,
    'in_donvi_dl_id': in_donvi_dl_id
  }).then(rs => rs.data.data),
  kiemtra_ma_tb_lap_hopdong_khac: (maTb, maGd, dvId, loaiHopDongId) => axios.post('web-hopdong/khoiphucthanhly/kiemtra_ma_tb_lap_hopdong_khac', {
    'ma_tb': maTb,
    'ma_gd': maGd,
    'dichvuvt_id': dvId,
    'loaihd_id': loaiHopDongId
  }).then(rs => rs.data.data),
  kt_laphopdong_trongthang: (data) => axios.post('web-hopdong/thanhly/kt_laphopdong_trongthang', data).then(rs => rs.data.data),
  sl_nhanvien: (data) => axios.post('web-hopdong/thanhly/sp_sl_nhanvien', data).then(rs => rs.data.data),
  lay_ds_chitiet_khuyenmai: (thueBaoId, kieu) => axios.get(`web-hopdong/hopdong/lay_ds_chitiet_khuyenmai?thueBaoId=${thueBaoId}&kieu=${kieu}`).then(rs => rs.data.data),
  tracuu_no_thanhtoan: (thanhToanId) => axios.get(`web-hopdong/hopdong/tracuu_no_thanhtoan?thanhToanId=${thanhToanId}`).then(rs => rs.data.data),
  tracuu_no_thuebao: (thuaBaoId) => axios.get(`web-hopdong/hopdong/tracuu_no_thuebao?thueBaoId=${thuaBaoId}`).then(rs => rs.data.data),
  lay_text_huongketnoi: (huongKNId) => axios.post(`web-hopdong/khoiphucthanhly/fn_lay_text_huongketnoi_v2`, {'huong_kn': huongKNId}).then(rs => rs.data.data),
  lay_sl_hd: (hpkhId) => axios.post(`web-hopdong/thanhly/sp_sl_hd`, {'hdkh_id': hpkhId}).then(rs => rs.data.data),
  lay_huonggiao_theo_quytrinh: (quytrinhId, tthdId) => axios.get(`web-hopdong/danhmuc/ds_huonggiao/${quytrinhId}/${tthdId}`).then(rs => rs.data.data),
  lay_ds_thaotac_theo_luong_id_code: (luongId, code) => axios.post(`web-hopdong/thanhly/sp_lay_ds_thaotac_theo_luong_id_code`, {
    'luong_id': luongId,
    'code': code
  }).then(rs => rs.data.data),
  lay_ct_thaotac_control: (thaotacId) => axios.post(`web-hopdong/thanhly/sp_lay_ct_thaotac_control`, {'thaotac_id': thaotacId}).then(rs => rs.data.data),
  lay_ds_thaotac_theo_quytring_id: (quytrinhId) => axios.post(`web-hopdong/thanhly/sp_lay_ds_thaotac_theo_quytrinh_id`, {'quytrinh_id': quytrinhId}).then(rs => rs.data.data),
  tinh_cuoc_nong: (thuebaoId, thang) => axios.post(`web-hopdong/thanhly/tinh_cuoc_nong`, {'thuebao_id': thuebaoId, 'thang': thang}).then(rs => rs.data.data),
  lay_ds_thamso_md: (kieu_id) => axios.post(`web-hopdong/thanhly/sp_lay_ds_thamso_md`, {"kieu_id": kieu_id}).then(rs => rs.data.data),
  lay_ds_hopdong_tb_theo_hdtb_id: (hdtbId) => axios.post(`web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id`, {'in_hdtb_id': hdtbId}).then(rs => rs.data.data),
  map_id: (col, table, condition) => axios.post(`web-hopdong/thanhly/thanh_ly_map_id`, {'id_neo': col, 'in_table': table, 'in_dk': ` ${condition}`}).then(rs => rs.data.data),
  get_keys: (keyname) => axios.post(`web-hopdong/khoiphucthanhly/get_keys`, {keyname}).then(rs => rs.data.data),
  fn_giaophieu_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_giaophieu_chamdutsd`, data).then(rs => rs.data.data),
  themtb_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_themtb_chamdutsd`, data).then(rs => rs.data.data),
  capnhat_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_capnhat_chamdutsd`, data).then(rs => rs.data.data),
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
  lay_ds_hdtb_theo_hdkh: (hdkhID) => axios.post(`web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_hdtb_theo_hdkh`, {'hdkh_id': hdkhID}).then(rs => rs.data.data),
  xoa_hdkh_v2: (hdkhID, loaiHopDongID) => axios.post(`web-hopdong/khoiphucthanhly/xoa_hdkh_v2`, {'hdkh_id': hdkhID, 'loaihd_id': loaiHopDongID}).then(rs => rs.data),
  xoa_hdtb: (hdtbID) => axios.post(`web-hopdong/thanhly/fn_xoa_thanhly`, {'hdtb_id': hdtbID}).then(rs => rs.data),
  // fn_xoa_thanhly: (hdtbID) => axios.post(`web-hopdong/thanhly/fn_xoa_thanhly`, {'hdtb_id': hdtbID}).then(rs => rs.data.data),
  lay_ds_tb_cung_madoicap: (thueBaoID, kieu) => axios.post(`web-hopdong/thanhly/lay_ds_tb_cung_madoicap`, {'thuebao_id': thueBaoID, 'kieu': kieu}).then(rs => rs.data.data),
  sp_lay_dulieu_theo_dieukien_phanvung: () => axios.post(`web-hopdong/thanhly/sp_lay_dulieu_theo_dieukien_phanvung`, {}).then(rs => rs.data.data),
  lay_thongtin_table: (table, where, result, groupBy) => axios.post("web-ccdv/khaibaoims/lay_thongtin_table", {
    "table": table,
    "where": where,
    "result": result,
    "groupby": groupBy
  }).then(rs => rs.data.data),
  getDataSQL: (data) => axios.post('web-ccdv/khaibaoims/lay_thongtin_table', data).then(rs => rs.data.data),
  ds_huonggiao: (quytrinh_id, tthd_id) =>
    axios
      .get(`/web-hopdong/danhmuc/ds_huonggiao/${quytrinh_id}/${tthd_id}`)
      .then(rs => rs.data.data),
  lay_ds_chitiet_km_theo_tbid: (thuebaoId, kieu) =>
    axios
      .get(`web-tracuu/danhba/lay_ds_chitiet_km_by_id?thuebaoId=${thuebaoId}&kieu=${kieu}`)
      .then(rs => rs.data.data),
  lay_ds_db_cd_theo_tbid: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_codinh?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_db_cd_theo_tbid_bc: (tinhtc_id, thuebao_id) => axios.post(`web-bancheo/tracuu/lay_ds_db_cd_theo_tbid`, {
    "tinhthicong_id": tinhtc_id,
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),
  Kiemtra_makh_mtt: (hdtbId, ma_kh, vloaihd_id) => axios.get(`web-hopdong/chuyendich/kiemtra_makh_mtt?hdtbId=${hdtbId}&ma_kh=${ma_kh}&vloaihd_id=${vloaihd_id}`).then((rs) => rs.data.data),

  LAY_DANHBA_THEO_MATB: (dichVuVtId, donViDlId, maTb) => axios.post(`web-hopdong/hopdong/lay_danhba_theo_matb`, {
    "dichVuVtId": dichVuVtId,
    "donViDlId": donViDlId,
    "maTb": maTb
  }).then(rs => rs.data.data),
  KIEMTRA_DB_KHI_THEMMOI: (dichvuvt_id, khachhang_id, ma_tb, thuebao_id) => axios.post(`web-hopdong/chuyendich/kiemtra_db_khi_themmoi`, {
    "dichvuvt_id": dichvuvt_id,
    "khachhang_id": khachhang_id,
    "ma_tb": ma_tb,
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_tsl_theo_tbid: (in_thuebao_id, p_phanvung_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_tsl_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id,
    "p_phanvung_id": p_phanvung_id
  }).then(rs => rs.data.data),
  lay_ds_db_cntt_theo_tbid: (in_thuebao_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_cntt_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_megawan_theo_tbid: (in_thuebao_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_megawan_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_megawan_theo_tbid_bc: (tinhtc_ic, thuebao_id) => axios.post(`web-bancheo/tracuu/lay_ds_db_megawan_theo_tbid`, {
    "thuebao_id": thuebao_id,
    "tinhthicong_id": tinhtc_ic
  }).then(rs => rs.data.data),
  lay_ds_db_gp_theo_tbid: (in_thuebao_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_gp_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_megaeyes_theo_tbid: (in_thuebao_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_megaeyes_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id
  }).then(rs => rs.data.data),
  lay_danhsach_thuoctinh_v2: (loaitbID, tenBang, optList) => axios.post(`web-hopdong/khoiphucthanhly/lay_danhsach_thuoctinh_v2`, {
    'loaitb_id': loaitbID,
    'ten_bang': tenBang,
    'opt_list': optList
  }).then(rs => rs.data.data),
  LAY_DS_DB_CD_THEO_TBID: (thuebao_id) => axios.post(`web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid`, {
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),

  Kiemtra_tb_mytv_chinhphu: (ma_tb, kieu) => axios.post(`web-hopdong/thaydoidichvu/kiemtra_tb_mytv_chinhphu`, {
    "kieu": kieu,
    "ma_tb": ma_tb
  }).then(rs => rs.data.data),
  LAY_DS_THAMSO_MD_MATS: (ma_ts) => axios.post(`web-ccdv/tram-thicong-dv-cntt/lay_ds_thamso_md_mats`, {
    "ma_ts": ma_ts
  }).then(rs => rs.data.data),
  CAPNHAT_STATUS_HDTB: (hdtb_id, status) => axios.post(`web-hopdong/khoiphucthanhly/capnhat_status_hdtb`, {
    "hdtb_id": hdtb_id,
    // "metaClass": {},
    "status": status
  }).then(rs => rs.data.data),
  lay_tinh_theo_matb: (matb) => axios.post(`web-hopdong/chuyendoi-codinh-sang-ims-bancheo/lay_tinh_theo_matb`, {
    "matb": matb
  }).then(rs => {
    if (rs.data.data.length > 0) {
      return { tinhthicong_id: rs.data.data[0]['tinh_thicong'] }
    } else {
      return { tinhthicong_id: 0 }
    }
  }),
  lay_tinhtc_id_tsl: (vma_tb, vdichvuvt_id) => axios.post(`web-hopdong/thanhly-thuebao-bancheo/lay_tinhtc_id_tsl`, {
    "vma_tb": vma_tb,
    "vdichvuvt_id": vdichvuvt_id
  }).then(rs => rs.data.data),
  lay_tinh_thi_cong: (tinhid) => axios.post(`web-thicong/hoinghi_truyenhinh/lay_tinh_thi_cong`, {
    "tinhtc_id": tinhid
  }).then(rs => {
    if (rs.data.data.length == 0) {
      return {}
    }

    return { ...rs.data.data[0], tinhthicong_id: rs.data.data[0].tinh_id }
  }),
  lay_thuebaoid_thicong: (vtinh_id, vthuebao_id) => axios.post(`web-hopdong/chuyendoi-codinh-sang-ims-bancheo/lay_thuebaoid_thicong`, {
    "vtinh_id": vtinh_id,
    "vthuebao_id": vthuebao_id
  }).then(rs => ({ tinhthicong_id: rs.data.data[0] })),
  lay_danhba_theo_thuebaoid: (thuebaoid, dichvuvt_id, donvi_dl_id, loaihd_bancheo) => axios.post(`web-hopdong/chuyendoi-codinh-sang-ims-bancheo/lay_danhba_theo_thuebaoid`, {
    "in_thuebaoid": thuebaoid,
    "in_dichvuvt_id": dichvuvt_id,
    "in_donvi_dl_id": donvi_dl_id,
    "in_loaihd_bancheo": loaihd_bancheo
  }).then(rs => rs.data.data),
  bancheo_lay_danhba_theo_thuebaoid: (tinh, thuebaoid, dichvuvt_id, donvi_dl_id, loaihd_bancheo) => axios.post(`web-bancheo/tracuu/lay_danhba_theo_thuebaoid`, {
    "tinhthicong_id": tinh,
    "thuebao_id": thuebaoid,
    "dichvuvt_id": dichvuvt_id,
    "donvi_id": donvi_dl_id,
    "loaihdbc": loaihd_bancheo
  }).then(rs => rs.data.data),
  lay_ds_hopdong_tb_theo_hdkh_id: (hdkh_id) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/lay_ds_hopdong_tb_theo_hdkh_id?hdkh_id=${hdkh_id}`).then(rs => rs.data.data),
  lay_thongso_thuebao_hdbc: (hdtbid) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/lay_thongso_thuebao_hdbc?hdtbid=${hdtbid}`).then(rs => rs.data.data),
  lay_thuebao_kn_id_theo_matb: (matb) => axios.post(`web-hopdong/chuyendoi-codinh-sang-ims-bancheo/lay_thuebao_kn_id_theo_matb`, {
    "matb": matb
  }).then(rs => rs.data.data),
  lay_tinh_theo_thuebaoid: (thuebao_id) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/lay_tinh_theo_thuebaoid?thuebao_id=${thuebao_id}`).then(rs => {
    if (rs.data.data.length == 0) {
      return {}
    }

    return { tinhthicong_id: rs.data.data[0].tinh_thicong }
  }),
  lay_ds_loaiip_theo_hdtb_id: (hdtb_id) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/lay_ds_loaiip_theo_hdtb_id?hdtb_id=${hdtb_id}`).then(rs => rs.data.data),
  lay_ds_kieunt_theo_hdtb_id: (hdtb_id) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/lay_ds_kieunt_theo_hdtb_id?hdtb_id=${hdtb_id}`).then(rs => rs.data.data),
  lay_ds_danhba_ims: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_ims?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_adsl: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_db_adsl_theo_tbid_bc: (tinhtc_id, thuebao_id) => axios.post(`web-bancheo/tracuu/lay_ds_db_adsl_theo_tbid`, {
    "tinhthicong_id": tinhtc_id,
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_ims_theo_tbid_bc: (tinhtc_id, thuebao_id) => axios.post(`web-bancheo/tracuu/lay_ds_db_ims_theo_tbid`, {
    "thuebao_id": thuebao_id,
    "tinhthicong_id": tinhtc_id
  }).then(rs => rs.data.data),
  kiemtra_capnhat_chamdutsd_bancheo: (data) => axios.post(`web-hopdong/thanhly-thuebao-bancheo/fn_kiemtra_capnhat_chamdutsd_bancheo`, {
    "kieu": 0,
    "ds": JSON.stringify([data])
  }).then(rs => rs.data),
  lay_ngay_ktck_theo_thuebao_id: (thuebaoId) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/sp_lay_ngay_ktck_theo_thuebao_id?thuebao_id=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_ttph_kh_tamdung_roimang: (thuebaoId) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/lay_ds_ttph_kh_tamdung_roimang?thuebao_id=${thuebaoId}`).then(rs => rs.data.data),
  WinUICommon_KIEMTRA_MA_TB_LAP_HOPDONG_KHAC: (maTb, maGd, dichVtId, loaiHdId) => axios.post(`web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac`, {
    "maTb": maTb,
    "maGd": maGd,
    "dichVuVtId": dichVtId,
    "loaiHdId": loaiHdId
  }).then(rs => data.data),
  lay_ds_donvi_theo_tbid: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_donvi_theo_tbid?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  capnhat_chamdutsd_bancheo: (kieu, ds_hdkh, ds_hdtb, ds_dd, ds_dkhuy, ds_adsl, ds_cd, ds_gp, ds_mgw, ds_tsl, ds_ims, ds_cntt, ds_hdtbdv, huonggiao_tn,
    tinhtc_id, loaitb_id, kenhtrang,
    ds_hdbc, ds_hdtbbc, ds_hdbc_mgw, ds_hdbc_adsl, ds_hdbc_cd, ds_dk_dvgt, ds_hdbc_tsl, ds_hdbc_ims, ds_dkhuybc, ds_bd_lydohuy) => {
    console.log({
      "KIEU": kieu,
      "HUONGGIAO_TN": huonggiao_tn,
      "TINHTHICONG_ID": tinhtc_id,
      "LOAITB_ID": loaitb_id,
      "KENH_TRANG": kenhtrang,
      "DS_HDKH": ds_hdkh,
      "DS_HDTB": ds_hdtb,
      "DS_ADSL": ds_adsl,
      "DS_CD": ds_cd,
      "DS_GP": ds_gp,
      "DS_MGW": ds_mgw,
      "DS_TSL": ds_tsl,
      "DS_IMS": ds_ims,
      "DS_DD": ds_dd,
      "DS_CNTT": ds_cntt,
      "DS_DKHUY": ds_dkhuy,
      "DS_HDTBDV": ds_hdtbdv,
      "DS_HDBC": ds_hdbc,
      "DS_HDTBBC": ds_hdtbbc,
      "DS_HDBC_MGW": ds_hdbc_mgw,
      "DS_HDBC_ADSL": ds_hdbc_adsl,
      "DS_HDBC_CD": ds_hdbc_cd,
      "DS_DK_DVGT": ds_dk_dvgt,
      "DS_HDBC_TSL": ds_hdbc_tsl,
      "DS_HDBC_IMS": ds_hdbc_ims,
      "DS_DKHUYBC": ds_dkhuybc,
      "DS_BD_LYDOHUY": ds_bd_lydohuy
    })
    return axios.post(`web-hopdong/thanhly-thuebao-bancheo/fn_capnhat_chamdutsd_bancheo`, {
      "ds_para": JSON.stringify({
        "KIEU": kieu, // -- convert tsbtnNhapMoi.Enabled sang dạng number
        "HUONGGIAO_TN": huonggiao_tn, // -- biến huonggiao_tn
        "TINHTHICONG_ID": tinhtc_id, // -- biến tinh.tinhthicong_id
        "LOAITB_ID": loaitb_id, // -- biến Convert.ToInt32(cboLoaihinhTB.SelectedValue)
        "KENH_TRANG": kenhtrang, // -- convert kenh_trang sang number
        "DS_HDKH": ds_hdkh, // -- biến json dsHDKH
        "DS_HDTB": ds_hdtb, // -- biến json dsHDTB
        "DS_ADSL": ds_adsl, // -- biến json dsHDTB_ADSL
        "DS_CD": ds_cd, // -- biến json dsHDTB_CD
        "DS_GP": ds_gp, // -- biến json dsHDTB_GP
        "DS_MGW": ds_mgw, // -- biến json dsHDTB_MGW
        "DS_TSL": ds_tsl, // -- biến json dsHDTB_TSL
        "DS_IMS": ds_ims, // -- biến json dsHDTB_IMS
        "DS_DD": ds_dd, // -- biến json dsHDTB_DD
        "DS_CNTT": ds_cntt, // -- biến json dsHDTB_CNTT
        "DS_DKHUY": ds_dkhuy, // -- biến json dsDKHUY
        "DS_HDTBDV": ds_hdtbdv, // -- biến json dsHDTBDV
        "DS_HDBC": ds_hdbc, // -- biến json dsHDBC
        "DS_HDTBBC": ds_hdtbbc, // -- biến json dsHDTBBC
        "DS_HDBC_MGW": ds_hdbc_mgw, // -- biến json dsHDBC_MGW
        "DS_HDBC_ADSL": ds_hdbc_adsl, // -- biến json dsHDBC_ADSL
        "DS_HDBC_CD": ds_hdbc_cd, // -- biến json dsHDBC_CD
        "DS_DK_DVGT": ds_dk_dvgt, // -- biến json dsDK_DVGT
        "DS_HDBC_TSL": ds_hdbc_tsl, // -- biến json dsHDBC_TSL
        "DS_HDBC_IMS": ds_hdbc_ims, // -- biến json dsHDBC_IMS
        "DS_DKHUYBC": ds_dkhuybc, // -- biến json dsDKHUYBC
        "DS_BD_LYDOHUY": ds_bd_lydohuy // - biến json dsBD_LYDOHUY
      })
    }).then(rs => rs.data)
  },
  themhdthuebao_chamdutsd_bancheo: (ds_hdtb, ds_dd, ds_adsl, ds_cd, ds_gp, ds_mgw, ds_tsl, ds_ims, ds_dkhuy, ds_cntt, ds_hdtbdv, huonggiao_tn) => {
    console.log('du lieu them thue bao', {
      "kieu": 0,
      "huonggiao_tn": huonggiao_tn,
      "ds_hdtb": ds_hdtb,
      "ds_adsl": ds_adsl,
      "ds_cd": ds_cd,
      "ds_gp": ds_gp,
      "ds_mgw": ds_mgw,
      "ds_tsl": ds_tsl,
      "ds_ims": ds_ims,
      "ds_dd": ds_dd,
      "ds_cntt": ds_cntt,
      "ds_dkhuy": ds_dkhuy,
      "ds_hdtbdv": ds_hdtbdv
    })
    return axios.post(`web-hopdong/thanhly-thuebao-bancheo/fn_themhdthuebao_chamdutsd_bancheo`, {
      "kieu": 0,
      "huonggiao_tn": huonggiao_tn,
      "ds_hdtb": JSON.stringify(ds_hdtb),
      "ds_adsl": JSON.stringify(ds_adsl),
      "ds_cd": JSON.stringify(ds_cd),
      "ds_gp": JSON.stringify(ds_gp),
      "ds_mgw": JSON.stringify(ds_mgw),
      "ds_tsl": JSON.stringify(ds_tsl),
      "ds_ims": JSON.stringify(ds_ims),
      "ds_dd": JSON.stringify(ds_dd),
      "ds_cntt": JSON.stringify(ds_cntt),
      "ds_dkhuy": JSON.stringify(ds_dkhuy),
      "ds_hdtbdv": JSON.stringify(ds_hdtbdv)
    }).then(rs => rs.data)
  },
  capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo: (nhanvienId, tbId, hdtbId, chbThuHoi, dichVuVTId) => axios.post(`web-hopdong/thanhly-thuebao-bancheo/fn_capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo`, {
    "dichvuvt_id": dichVuVTId,
    "ds": JSON.stringify([{
      "NHANVIEN_ID": nhanvienId,
      "HDTB_ID": hdtbId,
      "CHKTHUHOI": chbThuHoi,
      "THUEBAO_ID": tbId
    }])
  }).then(rs => rs.data),
  capnhat_idsn_chamdutsd_bancheo: (dsHDTB, dsDKHUY, dsHDTB_CD, dsHDTBDV, huonggiao_tn) => axios.post(`web-hopdong/thanhly-thuebao-bancheo/fn_capnhat_idsn_chamdutsd_bancheo`, {
    "kieu": 0,
    "huonggiao_tn": JSON.stringify(huonggiao_tn),
    "ds_hdtb": JSON.stringify(dsHDTB),
    "ds_cd": JSON.stringify(dsHDTB_CD),
    "ds_dkhuy": JSON.stringify(dsDKHUY),
    "ds_hdtbdv": JSON.stringify(dsHDTBDV)
  }).then(rs => rs.data),
  lay_tt_tb_dd: (dstb) => axios.post(`web-hopdong/thaydoiloaihinhtb/lay_tt_tb_dd`, {
    "vds_tb_id": dstb
  }).then(rs => rs.data.data),
  capnhat_status_hdtb: (hdtb_id, status) => axios.post(`web-hopdong/thaydoiloaihinhtb/sp_capnhat_status_hdtb`, {
    "p_hdtb_id": hdtb_id,
    "p_status": status
  }).then(rs => rs.data),
  kiemtra_tb_cung_makh_tb_pl: (maTb, hdkhId, dichVuVtId, loaiTbId, thuebaoId) => axios.post(`web-hopdong/chuyendich/kiemtra_tb_cung_makh_tb_pl`, {
    "maTb": maTb,
    "hdkhId": hdkhId,
    "dichVuVtId": dichVuVtId,
    "loaiTbId": loaiTbId,
    "thuebaoId": thuebaoId
  }).then(rs => rs.data.data),
  sp_lay_tt_hdtb_theo_hdkh: (hdkh_id) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/sp_lay_tt_hdtb_theo_hdkh?hdkh_id=${hdkh_id}`).then(rs => rs.data.data),
  sp_giaophieu_tbi: (phieu_id) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/sp_giaophieu_tbi?phieu_id=${phieu_id}`).then(rs => rs.data.data),
  lay_ds_db_thuebao_theo_tbid: (tinhthicong_id, thuebao_id) => axios.post('web-bancheo/tracuu/lay_ds_db_thuebao_theo_tbid', {
    "tinhthicong_id": tinhthicong_id,
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_tsl_theo_tbid_bc: (thuebao_id, tinhthicong_id) => axios.post('web-bancheo/tracuu/lay_ds_db_tsl_theo_tbid', {
    "thuebao_id": thuebao_id,
    "tinhthicong_id": tinhthicong_id
  }).then(rs => rs.data.data),
  lay_ds_hd_tbdd_theo_hdkh_id: (hdkh_id) => axios.post('web-hopdong/thanhly/lay_ds_hd_tbdd_theo_hdkh_id', {
    "hdkh_id": hdkh_id
  }).then(rs => rs.data.data),
  tinh_thicong: (tinh_id) => axios.post('web-tracuu/tracuubaohong/sp_lay_tinh_tc_theo_tinh_id', {
    "tinh_id": tinh_id
  }).then(rs => {
    if (rs.data.data.length > 0) {
      return {...rs.data.data[0], tinhthicong_id: rs.data.data[0].tinh_id }
    } else {
      return null
    }
  })
}
