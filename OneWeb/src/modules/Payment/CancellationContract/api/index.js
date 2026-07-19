export default {
  lay_thamso_macdinh: (axios) => axios.get('web-hopdong/thanhly/lay_thamso_macdinh', { params: { ma_ts: 'TS_THOAITRA_CHON_THUNGAN' } }),
  sp_lay_ds_thungan: (axios, data) => axios.post('web-ccdv/tienhopdong/sp_lay_ds_thungan', data), // { "loainv_id": 17, "nhanvien_id": 13431 }
  sp_lay_ds_lydo_tt: (axios, data) => axios.post('web-hopdong/thoaitrahopdong/sp_lay_ds_lydo_tt', data),
  lay_ds_phieutt_thoaitra: (axios, data) => axios.post('web-hopdong/thoaitrahopdong/lay_ds_phieutt_thoaitra', data), // {"nhanvien_id":3923,"ngay_bg":"01/05/2021","ngay_kt":"10/05/2021","ma_gd":"HPG-LD/00741477"}
  lay_ds_hdtb_theo_phieutt: (axios, data) => axios.post('web-hopdong/thoaitrahopdong/lay_ds_hdtb_theo_phieutt', data), // {"phieutt_id":"2241818"}
  lay_ds_khoanmuc_theo_hdtb: (axios, data) => axios.post('web-hopdong/thoaitrahopdong/lay_ds_khoanmuc_theo_hdtb', data), // {"hdtb_id":"1695418","phieutt_id":"243839"}
  fn_thoaitra_kt_luong_qt: (axios, data) => axios.post('web-hopdong/thoaitrahopdong/fn_thoaitra_kt_luong_qt', data), // {"hdtb_id":"6052545"}
  thoaitra_hopdong: (axios, data) => axios.post('web-hopdong/thoaitrahopdong/thoaitra_hopdong', data) // {  "thoaitra_tam": 0, "hdtb_id": 4832645, "phieutt_id": 1785948,"ct_tienhd_id": 15363087, "lydotontc_id": 9150, "tt_dongbo": 0, "dichvuvt_id": 0,"loaihd_id": 0}
}
