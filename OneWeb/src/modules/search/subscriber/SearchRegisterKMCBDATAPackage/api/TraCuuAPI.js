export default{
  // Tra cứu thông tin thuê bao:
  TraCuuDangKyGoiCuocKMCBDATA: (axios, data) => axios.post('/ccbs/tracuu/ts_tracuu_laytt_tb', data),
  TraCuuTTTB_TS: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_laytt_tb_ts', data),
  //Tra cuu goi ALO, DATA, goi dang SD
  TraCuuThongTinALO: (axios, data) => axios.post('/ccbs/executor/ts_ds_goicuoc_tbtt', data),
  TraCuuThongTinDATA: (axios, data) => axios.post('/ccbs/executor/ts_ds_goicuoc_tbtt', data),
  TraCuuGoiCuocDangSD: (axios, data) => axios.post('/ccbs/executor/ts_tc_sd_tbtt', data),
  //Thong tin khi bam link chi tiet
  ThongtinchitietALO: (axios, data) => axios.post('/ccbs/executor/ts_tt_goicuoc_tbtt', data),
  ThongtinchitietDATA: (axios, data) => axios.post('/ccbs/executor/ts_tt_goicuoc_tbtt', data),
  // Tra cuu danh sach goi de xuat KM
  TraCuuGoiCuoc: (axios, data) => axios.post('/ccbs/executor/ts_ds_dexuat_tbtt', data),
  // Tra cuu tien trong TK
  tracuu_taikhoan: (axios, data) => axios.post('/ccbs/didong/taikhoan-tien', data),
  // Đăng ký gói cước
  Dangkygoicuoc: (axios, data) => axios.post('ccbs/executor/ts_dk_goi_tbtt', data),

}
