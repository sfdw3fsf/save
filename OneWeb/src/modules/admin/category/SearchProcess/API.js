export default {
  ds3cbb: (axios,data) => axios.post('/web-quantri/thaotac/fn_load_tt_capnhat_quytrinh',data),
  getDSKieuLD: (axios,data) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD',data),
  getDSTocDo: (axios,data) => axios.post('/web-thicong/capnhatthongtin/fn_lay_danhsach_tocdo',data),
  getDSQuyTrinh: (axios,data) => axios.post('/web-thicong/capnhatthongtin/fn_tracuu_quytrinh',data),
  getChiTietQuyTrinh: (axios,data) => axios.post('/web-thicong/capnhatthongtin/fn_lay_chitiet_quytrinh',data),
}