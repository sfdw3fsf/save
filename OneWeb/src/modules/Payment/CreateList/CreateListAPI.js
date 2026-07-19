import { param } from "jquery";

export default{
  getKeys: (axios, params) => axios.post('/web-quantri/donvitinh/get_keys', params),
  getMaxStt: (axios, params) => axios.post('/web-ccdv/nhantra_tiensau/lay_max_stt', params),
  getDSBKDonVi: (axios, params) => axios.post('/web-hopdong/taobangke/lay_ds_bangke_donvi', params),
  capNhatBangKeID: (axios, params) => axios.post('/web-hopdong/taobangke/fn_taobangke_capnhat', params),
  xoaBanKe: (axios, params) => axios.post('/web-ccdv/nhantra_tiensau/sp_xoa_bangke_id', params),

  getDonVi: (axios, params) => axios.post('/web-quantri/danhmuc/donvi/theo_loai', params),
  getDonViTheoLoaiDV: (axios, params) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_ht_donvi_loaidv_nhanvien_combobox', params),
  getNhanVienTheoDVLoaiDV: (axios, params) => axios.post('/web-hopdong/nhanbangke/sp_lay_ds_nhanvien_theo_donvi_loainv_2', params),
  
  getDSHDDaGan: (axios, params) => axios.post('/web-hopdong/nhanbangke/lay_ds_hdkh_dagan_bk', params),
  getDSHDChuaGan: (axios, params) => axios.post('web-hopdong/taobangke/lay_ds_hdkh_chuagan_bk1', params),

  getDSTBDaGan: (axios, params) => axios.post('/web-hopdong/nhanbangke/lay_ds_hdtb_dagan_bk', params),
  getDSTBChuaGan: (axios, params) => axios.post('/web-hopdong/taobangke/lay_ds_hdtb_chuagan_bk', params),

  getMoneyToText: (axios, params) => axios.post(`/web-tracuu/tracuu_th/fn_doisosangchu`, params),
}