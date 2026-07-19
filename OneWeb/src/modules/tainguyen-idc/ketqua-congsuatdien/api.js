const BASE_URL = '/web-ecms'
export default {
  getKetQuaCapPhatFiltered(axios, payload) {
    return axios.post(`${BASE_URL}/tainguyen-idc/congsuatdien/ketquacongviec`, payload)
  },
  getTrangThaiList(axios) {
    return axios.get(`${BASE_URL}/tainguyen-idc/congsuatdien/trangthai`)
  },
  getNhanVienByDonVi(axios, donvi_id) {
    return axios.get(`${BASE_URL}/ha-tang/thongtin-u/danhsach-nhanvien/${donvi_id}`, {
      params: { donvi_id }
    })
  },
  getDanhMucLoaiCongViec: (axios, data) =>
    axios.post('/web-ecms/idc/danh-muc/common', {
      // IDC-LOAICV, IDC-DONVI
      loaiDanhMuc: data.loaiDanhMuc,
      thamSo1: data.thamSo1 ?? null,
      thamSo2: null
    })
}
