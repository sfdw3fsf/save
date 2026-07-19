export default {
  getThongTinThueBaoTLS: (axios, body) => axios.post('/web-baohong/GPDiemChiaTrungGian/lay_tt_db_tsl', body),
  getThongTinTramVaHuongGiao: (axios) => axios.get('/web-baohong/GPDiemChiaTrungGian/lay_tt_dm_pttb'),
  giaoPhieu: (axios, body) => axios.post('/web-baohong/GPDiemChiaTrungGian/giaophieu_tram_tg', body),
  huyGiaoPhieu: (axios, body) => axios.post('/web-baohong/GPDiemChiaTrungGian/huygiao_tram_tg', body)
}
