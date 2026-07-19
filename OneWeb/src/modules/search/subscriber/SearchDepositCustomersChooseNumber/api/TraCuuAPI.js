export default{
  getTraCuuLichSuPhanAnh: (axios, data) => axios.post('/api/tra-cuu/lich-su-phan-anh/28/', data),
  TraCuuThongTinDatCocChonSo: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_datcoc_iphone_chonso', data),
  TraCuuThongTinCaNhanDoanhNghiep: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_cn_dn', data),
  TraCuuDangKyGoiCuocKMCBDATA: (axios, data) => axios.post('/ccbs/tracuu/ts_tracuu_laytt_tb', data),
  TraCuuDangTTTBTS: (axios, data)              => axios.post('/ccbs/executor/ts_tracuu_laytt_tb_ts', data),
  TraCuuGoiCuoc: (axios, data) => axios.post('/ccbs/executor/ts_ds_dexuat_tbtt', data),
  TraCuuTaiKhoan: (axios, data) => axios.post('/ccbs/didong/taikhoan-tien', data),
  TraCuuGoiCuocDangSD: (axios, data) => axios.post('/ccbs/executor/ts_tc_sd_tbtt', data)

  
  
  

  

 
}
