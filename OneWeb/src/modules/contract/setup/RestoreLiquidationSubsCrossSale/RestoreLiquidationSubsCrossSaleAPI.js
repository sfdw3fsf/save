/* eslint eqeqeq: 'off', quotes: 'off', camelcase: off */

import axios from 'axios'

export default {
  getListTelecomService: () => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_dulieu_theo_dieukien_dvvt', {}).then(rs => rs.data.data),
  getListSubscriptionType: () => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB').then(rs => rs.data.data),
  getListRequestType: (loaiThueBaoID, loaiHopDongID) => axios.get(`/web-hopdong/thanhly/lay_ds_kieu_ld_thanhly?loaitbId=${loaiThueBaoID}&loaihdId=${loaiHopDongID}`).then(rs => rs.data.data.map(r => ({...r, ID: parseInt(r.ID)}))),
  getListContractByTransactionCode: (data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),

  lay_ds_kieu_ld: (loaihdID, loaitbID) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_kieu_ld', {'loaihd_id': loaihdID, 'loaitb_id': loaitbID}).then(rs => rs.data.data.map(r => ({ ID: r.kieuld_id, NAME: r.ten_kieuld }))),
  lay_ds_diachi_theo_hdtb_id: (hdtbID) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_hdtb_id', {'in_hdtb_id': hdtbID}).then(rs => rs.data.data),
  lay_ds_diachi_theo_dbtbid: (thuebaoID) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid', {'in_thuebao_id': thuebaoID}).then(rs => rs.data.data),
  lay_ds_donvi_theo_loaidv: (donViID, loaiDonViID) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_ds_donvi_theo_loaidv_v5', {
    'donvi_id': donViID,
    'loaidv_id': loaiDonViID
  }).then(rs => rs.data.data.map(r => ({...r, donvi_id: parseInt(r.donvi_id)}))),
  lay_ds_donvi_theo_loaidv_bc: (donViID, loaiDonViID) => axios.post('/web-hopdong/chuyendoi-codinh-sang-ims-bancheo/sp_lay_ds_donvi_loaidv', {
    'p_donvi_id': donViID,
    'p_loaidv_id': loaiDonViID
  }).then(rs => rs.data.data.map(r => ({...r, donvi_id: parseInt(r.donvi_id)}))),
  lay_dulieu_theo_dieukien_muccuoc_tb: (muccuoctbID) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_dulieu_theo_dieukien_muccuoc_tb', {}).then(rs => rs.data.data),
  lay_ds_hopdong_theo_ma_tb: (data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb', data).then(rs => rs.data.data),
  //API BÁN CHÉO
  lay_khachhangid_thicong: (axios, data) => axios.post('web-bancheo/tracuu/lay_khachhangid_thicong', data),
  lay_tinh_theo_matb: (data) => axios.post('/web-hopdong/chuyendoi-codinh-sang-ims-bancheo/lay_tinh_theo_matb', data).then(rs => rs.data.data),
  sp_lay_thuebaoid_thicong_theo_matb: (data) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/sp_lay_thuebaoid_thicong_theo_matb', data).then(rs => rs.data.data),
  get_thuebao_kn_id: (data) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/get_thuebao_kn_id', data).then(rs => rs.data.data),
  lay_danhba_theo_matb_bc: (data) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/lay_danhba_theo_matb', data).then(rs => rs.data.data),
  
  lay_ds_db_cd_theo_tbid: (thuebaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_codinh?thueBaoId=${thuebaoId}`).then(rs => rs.data.data),
  lay_ds_db_cd_theo_tbid_bc: (tinhtc_id, thuebao_id) => axios.post(`web-bancheo/tracuu/lay_ds_db_cd_theo_tbid`, {
    "tinhthicong_id": tinhtc_id,
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_danhba_ims: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_ims?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_db_ims_theo_tbid_bc: (tinhtc_id, thuebao_id) => axios.post(`web-bancheo/tracuu/lay_ds_db_ims_theo_tbid`, {
    "thuebao_id": thuebao_id,
    "tinhthicong_id": tinhtc_id
  }).then(rs => rs.data.data),
  lay_ds_db_megawan_theo_tbid: (in_thuebao_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_megawan_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_megawan_theo_tbid_bc: (tinhtc_ic, thuebao_id) => axios.post(`web-bancheo/tracuu/lay_ds_db_megawan_theo_tbid`, {
    "thuebao_id": thuebao_id,
    "tinhthicong_id": tinhtc_ic
  }).then(rs => rs.data.data),
  lay_ds_danhba_adsl: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_db_adsl_theo_tbid_bc: (tinhtc_id, thuebao_id) => axios.post(`web-bancheo/tracuu/lay_ds_db_adsl_theo_tbid`, {
    "tinhthicong_id": tinhtc_id,
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_tsl_theo_tbid: (in_thuebao_id, p_phanvung_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_tsl_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id,
    "p_phanvung_id": p_phanvung_id
  }).then(rs => rs.data.data),
  lay_ds_db_tsl_theo_tbid_bc: (data) => axios.post('web-bancheo/tracuu/lay_ds_db_tsl_theo_tbid', {
    "thuebao_id": data.thuebao_id,
    "tinhthicong_id": data.tinhthicong_id
  }).then(rs => rs.data.data),
  sp_lay_ds_kieu_ld_bc: (data) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/sp_lay_ds_kieu_ld_bc', data).then(rs => rs.data.data),
  lay_thongso_thuebao_bancheo_hdtbid: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_thongso_thuebao_bancheo_hdtbid', data),
  lay_thongso_thuebao_hdbc: (hdtbid) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/lay_thongso_thuebao_hdbc?hdtbid=${hdtbid}`).then(rs => rs.data.data),  
  lay_tinh_theo_thuebaoid: (thuebao_id) => axios.get(`web-hopdong/thanhly-thuebao-bancheo/lay_tinh_theo_thuebaoid?thuebao_id=${thuebao_id}`).then(rs => rs.data.data),  
  lay_tinh_thi_cong: (tinhtc_id) => axios.get(`web-ccdv//tientrinh-khieunai/lay_tinh_thi_cong?tinhtc_id=${tinhtc_id}`).then(rs => rs.data.data),  
  LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC: (hdtb_id) => axios.post(`web-hopdong/thanhly-thuebao-bancheo/lay_ds_hopdong_tb_theo_hdtb_id`, { "in_hdtb_id": hdtb_id }).then(rs => rs.data.data),
  fn_lay_huonggiao_quytrinh: (data) => axios.post(`web-hopdong/thanhly/fn_lay_huonggiao_quytrinh`, data).then(rs => rs.data.data),  
  lay_ds_huonggiao: (data) => axios.post(`web-hopdong/khoiphucthanhly/lay_ds_huonggiao`, data).then(rs => rs.data.data),  
  lay_tinhtc_id_tsl: (vma_tb, vdichvuvt_id) => axios.post(`web-hopdong/thanhly-thuebao-bancheo/lay_tinhtc_id_tsl`, {
    "vma_tb": vma_tb,
    "vdichvuvt_id": vdichvuvt_id
  }).then(rs => rs.data.data),
  tinh_thicong: (tinh_id) => axios.post('web-tracuu/tracuubaohong/sp_lay_tinh_tc_theo_tinh_id', {
    "tinh_id": tinh_id
  }).then(rs => {
    if (rs.data.data.length > 0) {
      return { ...rs.data.data[0], tinhthicong_id: rs.data.data[0].tinh_id }
    } else {
      return null
    }
  }),
  lay_ds_db_thuebao_theo_tbid: (tinhthicong_id, thuebao_id) => axios.post('web-bancheo/tracuu/lay_ds_db_thuebao_theo_tbid', {
    "tinhthicong_id": tinhthicong_id,
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),
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
  getKey: async (axios, data, header) => axios.post('/web-qlvt/api/cap-phat-ccdc/get-key', data,header),
  //web-hopdong/thanhly-thuebao-bancheo/sp_themmoi_hdbancheo_biendong_v3
  sp_themmoi_hdbancheo_biendong_v3: (dsHDBC, dsHDTBBC, dsHDBCMGWAN, dsHDBCADSL, dsHDBCCD, dsDKBCDVGT, dsHDBCTSL, dsHDBCIMS, dsLYDOHUYBC, dsDiaChi_BanCheo) => {
    console.log({
      "p_js_hd_bancheo": dsHDBC,
      "p_js_hdtb_bancheo": dsHDTBBC,
      "p_js_hdbc_mgwan": dsHDBCMGWAN,
      "p_js_hdbc_adsl": dsHDBCADSL,
      "p_js_hdbc_cd": dsHDBCCD,
      "p_js_dkbc_dvgt": dsDKBCDVGT,
      "p_js_hdbc_tsl": dsHDBCTSL,
      "p_js_hdbc_ims": dsHDBCIMS,
      "p_js_lydohuy_bc": dsLYDOHUYBC,
      "p_js_diachi_bc": dsDiaChi_BanCheo
    })


    return axios.post(`web-hopdong/thanhly-thuebao-bancheo/sp_themmoi_hdbancheo_biendong_v3`, {
      "p_js_hd_bancheo": JSON.stringify(dsHDBC) ,
      "p_js_hdtb_bancheo": JSON.stringify(dsHDTBBC) ,
      "p_js_hdbc_mgwan": JSON.stringify(dsHDBCMGWAN) ,
      "p_js_hdbc_adsl": JSON.stringify(dsHDBCADSL) ,
      "p_js_hdbc_cd": JSON.stringify(dsHDBCCD) ,
      "p_js_dkbc_dvgt": JSON.stringify(dsDKBCDVGT) ,
      "p_js_hdbc_tsl": JSON.stringify(dsHDBCTSL) ,
      "p_js_hdbc_ims": JSON.stringify(dsHDBCIMS) ,
      "p_js_lydohuy_bc": JSON.stringify(dsLYDOHUYBC) ,
      "p_js_diachi_bc": JSON.stringify(dsDiaChi_BanCheo) 

    }).then(rs => rs.data)
  },
  sp_capnhat_hdbancheo_biendong: (dsHDBC, dsHDTBBC, dsHDBCMGWAN, dsHDBCADSL, dsHDBCCD, dsDKBCDVGT, dsHDBCTSL, dsHDBCIMS, dsDiaChi_BanCheo) => {
    console.log({
      "p_js_hd_bancheo": dsHDBC,
      "p_js_hd_hdtb_bancheo": dsHDTBBC,
      "p_js_hdbc_mgwan": dsHDBCMGWAN,
      "p_js_hdbc_adsl": dsHDBCADSL,
      "p_js_hdbc_cd": dsHDBCCD,
      "p_js_dkbc_dvgt": dsDKBCDVGT,
      "p_js_hdbc_tsl": dsHDBCTSL,
      "p_js_hdbc_ims": dsHDBCIMS,      
      "p_js_diachi_bancheo": dsDiaChi_BanCheo
    })


    return axios.post(`web-hopdong/thanhly-thuebao-bancheo/sp_capnhat_hdbancheo_biendong`, {
      "p_js_hd_bancheo": JSON.stringify(dsHDBC) ,
      "p_js_hd_hdtb_bancheo": JSON.stringify(dsHDTBBC) ,
      "p_js_hdbc_mgwan": JSON.stringify(dsHDBCMGWAN) ,
      "p_js_hdbc_adsl": JSON.stringify(dsHDBCADSL) ,
      "p_js_hdbc_cd": JSON.stringify(dsHDBCCD) ,
      "p_js_dkbc_dvgt": JSON.stringify(dsDKBCDVGT) ,
      "p_js_hdbc_tsl": JSON.stringify(dsHDBCTSL) ,
      "p_js_hdbc_ims": JSON.stringify(dsHDBCIMS) ,      
      "p_js_diachi_bancheo": JSON.stringify(dsDiaChi_BanCheo) 

    }).then(rs => rs.data)
  },
  sp_capnhat_hopdong_khoiphuc: (dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC,dsHDTB_IMS,dsHDTB_TSL, dsHDTBDV,dsCTTHD) => {
    console.log({
      'p_js_hd_khachhang': dsHDKH,
      'p_js_hd_thuebao': dsHDTB,      
      'p_js_hdtb_adsl': dsHDTB_ADSL,
      'p_js_hdtb_cd': dsHDTB_CD,
      'p_js_hdtb_gp': dsHDTB_GP,
      'p_js_hdtb_mgwan': dsHDTB_MGW,
      'p_js_dangky_dvgt': dsDK_DVGT,
      'p_js_dangky_dvk': dsDK_DVKHAC,
      'p_js_hdtb_ims': dsHDTB_IMS,
      'p_js_hdtb_tsl': dsHDTB_TSL,
      'p_js_hdtb_dv': dsHDTBDV,
      "p_js_tiend_hd": dsCTTHD,
    })


    return axios.post(`web-baohong/TiepNhanBaoHong/sp_capnhat_hopdong_khoiphuc`, {
      'p_js_hd_khachhang': JSON.stringify(dsHDKH),
      'p_js_hd_thuebao': JSON.stringify(dsHDTB),      
      'p_js_hdtb_adsl': JSON.stringify(dsHDTB_ADSL),
      'p_js_hdtb_cd': JSON.stringify(dsHDTB_CD),
      'p_js_hdtb_gp': JSON.stringify(dsHDTB_GP),
      'p_js_hdtb_mgwan': JSON.stringify(dsHDTB_MGW),
      'p_js_dangky_dvgt': JSON.stringify(dsDK_DVGT),
      'p_js_dangky_dvk': JSON.stringify(dsDK_DVKHAC),
      'p_js_hdtb_ims': JSON.stringify(dsHDTB_IMS),
      'p_js_hdtb_tsl': JSON.stringify(dsHDTB_TSL),
      'p_js_hdtb_dv': JSON.stringify(dsHDTBDV),
      "p_js_tiend_hd": JSON.stringify(dsCTTHD),      
    }).then(rs => rs.data)
  },
  layhuonggiao_tiepnhan: async (axios, data) => axios.post('/web-bancheo/danhmuc/layhuonggiao_tiepnhan', data),
  lay_ds_dvk_macdinh: (data) => axios.post(`web-hopdong/khoiphucthanhly/lay_ds_dvk_macdinh`, data).then(rs => rs.data.data),  
  lay_tien_khoanmuc_ld: (data) => axios.post(`web-thicong/thicong/lay_tien_khoanmuc_ld`, data).then(rs => rs.data.data),    
  lay_ds_tb_dvgt_theo_trangthai: (data) => axios.post(`web-bancheo/tracuu/lay_ds_tb_dvgt_theo_trangthai`, data).then(rs => rs.data.data),
  lay_ds_tb_dvgt_theo_tt_ims: (data) => axios.post(`/web-hopdong/thaydoidichvu/lay_ds_tb_dvgt_theo_tt_ims`, data).then(rs => rs.data.data),
  sp_tt_dbtb_dv: (data) => axios.post(`/web-hopdong/laydulieu/sp_tt_dbtb_dv`, data).then(rs => rs.data.data),
  sp_tt_tocdo_adsl: (data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_tocdo_adsl`, data).then(rs => rs.data.data),
  sp_tt_tocdo_kenh: (data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_tocdo_kenh`, data).then(rs => rs.data.data),
  sp_tt_muccuoc: (data) => axios.post(`/web-hopdong/laydulieu/sp_tt_muccuoc`, data).then(rs => rs.data.data),
  sp_tt_muccuoc_tb: (data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_muccuoc_tb`, data).then(rs => rs.data.data),
  //​/
  //END API BÁN CHÉO
  lay_ds_hopdong_theo_ma_gd: (maGD, loaihdId, tthd) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', {
    'in_ma_gd': maGD,
    'in_loaihd_id': loaihdId || 0,
    'in_tthd_id': tthd || 0,
    'in_donvi_dl_id': 0
  }).then(rs => rs.data.data),
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
  lay_huonggiao_tiepnhan: (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id) => axios.post(`/web-hopdong/hopdong/lay_huonggiao_tiepnhan`,  {
    'kieuLdId': kieuld_id,
    'loaiHdId': loaihd_id,
    'loaiTbId': loaitb_id,
    'mucCuocId': muccuoc_id,
    'tocDoId': tocdo_id
  }).then(rs => rs.data.data),
  layhuonggiao_tiepnhan_v2: (loaiHDID, loaiTBID, tocDoID, muocCuocID, kieuID, loaiKenhID, DVID) => axios.post(`web-hopdong/khoiphucthanhly/layhuonggiao_tiepnhan_v2`, {
    'loaihd_id': loaiHDID,
    'loaitb_id': loaiTBID,
    'tocdo_id': tocDoID,
    'muccuoc_id': muocCuocID,
    'kieuld_id': kieuID,
    'loaikenh_id': loaiKenhID,
    'dichvuvt_id': DVID
  }).then(rs => rs.data.data),
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
  hien_thi_cbo_muc_cuoc: (mucuoctbID) => axios.post(`web-hopdong/khoiphucthanhly/lay_dulieu_theo_dieukien`, {
    'schema': 'CSS',
    'table_name': 'muccuoc_tb a',
    'value': 'mucuoctb_id',
    'text': "a.muccuoc||' - '||Decode(a.muccuoc_id,1, a.cuoc_tg, a.cuoc_tb)||' VND' tenmuccuoc",
    'where': 'mucuoctb_id = ' + mucuoctbID,
    'order': 'mucuoctb_id'
  }).then(rs => rs.data.data),
  hien_thi_cbo_muc_cuoc_adsl: () => axios.post(`web-hopdong/khoiphucthanhly/lay_dulieu_theo_dieukien`, {}).then(rs => rs.data.data),
  lay_dulieu_theo_dieukien: (table, value, text, where, order) => axios.post(`web-hopdong/khoiphucthanhly/lay_dulieu_theo_dieukien`, {
    'schema': 'CSS',
    'table_name': table,
    'value': value,
    'text': text,
    'where': where,
    'order': order
  }).then(rs => rs.data.data),
  
  lay_ds_hopdong_thuebao_adsl: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_mgwan: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_ims: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_ims?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hopdong_thuebao_gp: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_danhba_megawan: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_megawan?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_gp: (thueBaoId) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_gp?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_ds_danhba_cntt: (thueBaoId) => axios.get(`web-hopdong//hopdong/lay_ds_danhba_cntt?thueBaoId=${thueBaoId}`).then(rs => rs.data.data),
  lay_danhsach_thuoctinh_v2: (loaitbID, tenBang, optList) => axios.get(`web-hopdong/khoiphucthanhly/lay_danhsach_thuoctinh_v2`, {
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
  sp_lay_tienkhoanmuc_loaihd: (loaiHopDongId) => axios.post(`web-hopdong/tachnhapthuebao/sp_lay_tienkhoanmuc_loaihd`, {'loaihd_id': loaiHopDongId}).then(rs => rs.data.data),
  post_lay_khoanmuc_tt_theo_loaihd : (data) => axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd',data).then(rs => rs.data.data),
  lay_ds_dbkh_theo_khid: (hdkhId) => axios.post(`web-hopdong/CapNhatTraTruoc/lay_ds_dbkh_theo_khid`, { 'khachhang_id': hdkhId }).then(rs => rs.data.data),
  lay_ma_hd: (donviId) => axios.get(`web-hopdong/doisim/lay_ma_hd?donvi_id=${donviId}`).then(rs => rs.data.data),
  themmoi_hopdong_khoiphucthanhly_v2: (dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, huonggiao_tn, dsHDTB_IMS, dsHDTB_KV, dsHDTB_CNTT, dsHDTB_TSL) => {
    console.log({
      'js_hd_khachhang': dsHDKH,
      'js_hd_thuebao': dsHDTB,
      'js_ct_tienhd': dsCTTHD,
      'js_hdtb_adsl': dsHDTB_ADSL,
      'js_hdtb_cd': dsHDTB_CD,
      'js_hdtb_gp': dsHDTB_GP,
      'js_hdtb_mgwan': dsHDTB_MGW,
      'js_hdtb_ims': dsHDTB_IMS,
      'js_hdtb_tsl': dsHDTB_TSL,
      'js_hdtb_cntt': dsHDTB_CNTT,
      'js_hdtb_kv': dsHDTB_KV,
      'js_dangky_dvgt': dsDK_DVGT,
      'js_dangky_dvk': dsDK_DVKHAC,
      'js_hdtb_dv': dsHDTBDV,
      'huonggiao_id': huonggiao_tn
    })

    var dsHDTB_KV_upperKey = []

    if (dsHDTB_KV && dsHDTB_KV.length > 0) {
      for (var row of dsHDTB_KV.HDTB_KV) {
        var obj = {}
        for (var key in row) {
          if (Object.hasOwnProperty.call(row, key)) {
            obj[key.toUpperCase()] = row[key]
          }
        }
  
        dsHDTB_KV_upperKey.push(obj)
      }
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
  kt_thaotac: (luongId, code) => axios.post(`web-hopdong/khoiphucthanhly/fn_kt_thaotac`, { 'luong_id': luongId, 'code': code }).then(rs => rs.data.data),
  lay_ds_db_cd_theo_tbid: (thuebaoId) => axios.post(`web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid`, {'thuebao_id': thuebaoId}).then(rs => rs.data.data),
  lay_ds_doitac: () => axios.post(`web-ccdv/khaibaotsl/sp_lay_ds_doitac`, {}).then(rs => rs.data.data.map(r => ({ ...r, doitac_id: parseInt(r.doitac_id) }))),
  lay_ds_hopdong_thuebao_theo_hdkh: (hdKhId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId=${hdKhId}`).then(rs => rs.data.data),
  xoa_hdkh_v2: (hdkhID, loaiHopDongID) => axios.post(`web-hopdong/khoiphucthanhly/xoa_hdkh_v2`, {'hdkh_id': hdkhID, 'loaihd_id': loaiHopDongID}).then(rs => rs.data),
  xoa_hdtb: (hdtbID) => axios.post(`web-hopdong/khoiphucthanhly/xoa_hdtb`, {'hdtb_id': hdtbID}).then(rs => rs.data),
  themmoi_hopdongtb_khoiphucthanhly_v2: (dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, huonggiao_tn, dsHDTB_IMS, dsHDTB_MGW, dsHDTB_TSL) => {
    console.log('them thua bao data', {
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
    })
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
  fn_tt_nhanvien: (data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien`, data).then(rs => rs.data.data),
  fn_tt_loaihinh_tb: (data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb`, data).then(rs => rs.data.data),
  fn_tt_kieu_ld: (data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_kieu_ld`, data).then(rs => rs.data.data),
  fn_tt_muccuoc_tb: (data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_muccuoc_tb`, data).then(rs => rs.data.data),
  fn_tt_db_thuebao: (data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao`, data).then(rs => rs.data.data),
  fn_tt_hd_thuebao: (data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, data).then(rs => rs.data.data),
  fn_tt_db_thuebao_bc: (data) => axios.post(`/web-bancheo/tracuu/fn_tt_db_thuebao`, data).then(rs => rs.data.data),
  


  fn_giaophieu_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_giaophieu_chamdutsd`, data).then(rs => rs.data.data),
  themtb_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_themtb_chamdutsd`, data).then(rs => rs.data.data),
  capnhat_chamdutsd: (data) => axios.post(`web-hopdong/thanhly/fn_capnhat_chamdutsd`, data).then(rs => rs.data.data),
  lay_ds_isdn_theo_thuebao_id: (tbid) => axios.post(`web-hopdong/thanhly/lay_ds_isdn_theo_thuebao_id`, {'thuebao_id': tbid}).then(rs => rs.data.data),
  fn_xoa_thanhly: (hdtbID) => axios.post(`web-hopdong/thanhly/fn_xoa_thanhly`, {'hdtb_id': hdtbID}).then(rs => rs.data.data),

  //FIX ATT
  lay_dulieu_theo_dieukien_thue_bao :(type, param)=> axios.post(`web-quantri/lay-dulieu/sp_tt_db_thuebao`,{ type, param} ).then(rs => rs.data.data ),
  // Sua tu 5/3/2024
  post_layhuonggiao_tiepnhan_v4: async (axios, data) => axios.post('/web-bancheo/danhmuc/layhuonggiao_tiepnhan_v4', data),
}
