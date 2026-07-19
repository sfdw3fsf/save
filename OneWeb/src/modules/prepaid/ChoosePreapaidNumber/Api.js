// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1791360228&range=A294:G294
// WinUI.WinUIBase.frmChonSoKH
// https://api-onebss-dev.vnptit3.vn/ccbs/swagger-ui/?urls.primaryName=default#/Ch%E1%BB%8Dn%20s%E1%BB%91/chonSoPostUsingPOST
// https://api-onebss-dev.vnptit3.vn/ccbs/swagger-ui/?urls.primaryName=api2
/*
Nguyễn Như Ngọc, [20/03/2022 9:53 PM]
tt_1_khoitao_chonso_tratruoc=>tb_kttt_khoso_toanquoc

Nguyễn Như Ngọc, [20/03/2022 9:53 PM]
tt_1_khoitao_chonso_trasau=>tb_ktts_khoso_toanquoc

Nguyễn Như Ngọc, [20/03/2022 9:57 PM]
https://api-onebss-dev.vnptit3.vn/ccbs/swagger-ui/?urls.primaryName=default#/Ch%E1%BB%8Dn%20s%E1%BB%91
*/
export default {
  getDmDauSo: (axios, data) => axios.post('/ccbs/chonSo/app_ds_dauso', data),
  getDmKieuSo: (axios, data) => axios.post('/ccbs/chonSo/app_ds_kieu_so', data),
  getDsKhoSo: (axios, data) => axios.post('/ccbs/chonSo/ds_khoso_kt', data),
  getDmTrangThaiKhoSo: (axios, data) => axios.post('/ccbs/chonSo/ds_trangthai_khoso', data),
  getDmTrangThaiKhoSo_ChonSo: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/TRANGTHAI_CHONSO_KH_DD', data),
  Search: (axios, data) => axios.post('/ccbs/chonSo/app_tb_timkiem', data),
  SearchKhoSoVTT: (axios, data) => axios.post('/ccbs/chonSo/app_tb_timkiem_khoso_vtt', data),
  ChonSo: (axios, data) => axios.post('/ccbs/chonSo/app_tb_dkchonso', data),
  ChonSoDangKy: (axios, data) => axios.post('/ccbs/chonSo/app_tb_dkchonso', data),
  ChonSoKhoiTao: (axios, data) => axios.post('/ccbs/chonSo/app_tb_dkchonso', data),
  DanhSachKhoSoKhoiTaoUser: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DS_KHOSO_KHOITAO_USER', data),
  DS_KHOSO_KHOITAO_USER: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DS_KHOSO_KHOITAO_USER', data),//   P_DS_PARA: {"USER_NEO":"dtndiep_hpg"}
  NAP_DS_KHOSO: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/NAP_DS_KHOSO', data),////   P_DS_PARA: {"NGUOIDUNG_ID":3047,"LOAITB_ID":21}
  DanhSachKhoSoKhoiDangKy: (axios, data) => axios.post('/ccbs/chonSo/ts_ds_chonso', data),//axios.post('/ccbs/executor/tt_1_chonso_ds', data), // Doi ham theo yeu cau cua Ngoc
  ts_laphd_chonso_khotinh_phieudk: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chonso_khotinh_phieudk', data),
  tt_1_chonso_ds: (axios, data) => axios.post('/ccbs/chonSo/ts_ds_chonso', data),//axios.post('/ccbs/executor/tt_1_chonso_ds', data), // Doi ham theo yeu cau cua Ngoc
  tt_1_trangthai: (axios, data) => axios.post('/ccbs/executor/tt_1_trangthai', data),
  tt_1_kieuso: (axios, data) => axios.post('/ccbs/executor/tt_1_kieuso', data),
  ts_laphd_chonso_khotinh_layds: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chonso_khotinh_layds', data),
  ts_danhsach_khoso_khoitao_tb: (axios, data) => axios.post('/ccbs/executor/ts_danhsach_khoso_khoitao_tb', data),
  ts_laphd_chonso_khotinh: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chonso_khotinh ', data),
  ts_thongtin_khoso_khoitao: (axios, data) => axios.post('/ccbs/executor/ts_thongtin_khoso_khoitao', data),
  KhoiTaoThueBao: (axios, data) => axios.post('web-hopdong/hopdong/fn_didong', {vinput: data, vtype: 8,vhdtb_id: data.hdtb_id}),
  prefix_chonso_kh_dd: (axios, data) => axios.post(`/web-quantri/danhmuc/nghiepvu/PREFIX_CHONSO_KH_DD`, data),
  tt_1_tim_kiem_so: (axios, data) => axios.post(`/ccbs/chonSo/tim_kiem_so`, data),
  tt_1_chonso: (axios, data) => axios.post(`/ccbs/chonSo/ts_chonso`, data),
  // tt_1_khoitao_chonso_tratruoc: (axios, data) => axios.post(`/ccbs/chonSo/tb_kttt_khoso_toanquoc`, data),
  tt_1_khoitao_chonso_tratruoc: (axios, data) => axios.post(`/ccbs/chonSo/tb_kt_khoso_toanquoc`, data),
  map_id: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`, data),
  fn_tt_goicuoc_dd: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_goicuoc_dd`, data),
  tt_1_khoitao_chonso_trasau: (axios, data) => axios.post(`/ccbs/chonSo/tb_ktts_khoso_toanquoc`, data),
  ts_khoitao_tratruoc_ds_loaitb: (axios, data) => axios.post(`/ccbs/executor/ts_khoitao_tratruoc_ds_loaitb`, data),
  ts_khoitao_ds_loaitb: (axios, data) => axios.post(`/ccbs/executor/ts_khoitao_ds_loaitb`, data),
  DANHMUC_GOICUOC_DD_TS: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DANHMUC_GOICUOC_DD_TS', data),
  DANHMUC_GOICUOC_DD_TT: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DANHMUC_GOICUOC_DD_TT', data),
  sp_chonso_kh_danhmuc: (axios, data) => axios.post('/web-tratruoc/tra-truoc/sp_chonso_kh_danhmuc', data),
  sp_ht_prefix_lhtb: (axios, data) => axios.post('/web-tratruoc/tra-truoc/sp_ht_prefix_lhtb', data),
  fn_khoitao_tb_didong: (axios, data) => axios.post('/web-tratruoc/tra-truoc/fn_khoitao_tb_didong', data)
}
