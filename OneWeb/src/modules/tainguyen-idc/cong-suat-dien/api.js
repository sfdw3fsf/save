const BASE_URL = '/web-ecms'
export default {
  getCongViecCongSuatDien(axios, phieutc_id) {
    return axios.get(`${BASE_URL}/tainguyen-idc/congsuatdien/congviec/${phieutc_id}`, {
      params: { phieutc_id }
    })
  },
  getDSThietBiCapPhatDien(axios, phieutc_id) {
    return axios.get(`${BASE_URL}/tainguyen-idc/congsuatdien/congviec/${phieutc_id}/dsthietbi`, {
      params: { phieutc_id }
    })
  },
  getDsThanhNguonOCam(axios, rack_id) {
    return axios.get(`${BASE_URL}/tainguyen-idc/rack/${rack_id}/dsthanhnguonocam`, {
      params: { rack_id }
    })
  },
  capPhatDien(axios, payload) {
    return axios.post(`${BASE_URL}/tainguyen-idc/congsuatdien/capphat`, payload)
  },
  getKetQuaCapPhat(axios, phieutc_id) {
    return axios.get(`${BASE_URL}/tainguyen-idc/congsuatdien/congviec/${phieutc_id}/ketquacapphat`, {
      params: { phieutc_id }
    })
  },
  updateTrangThaiPhieuTC(axios, payload) {
    return axios.post(`${BASE_URL}/tainguyen-idc/congsuatdien/phieutc/update`, payload)
  },
  getNhanVienByDonVi(axios, donvi_id) {
    return axios.get(`${BASE_URL}/ha-tang/thongtin-u/danhsach-nhanvien/${donvi_id}`, {
      params: { donvi_id }
    })
  },
  getDsThietBiThuHoiDien(axios, phieutc_id) {
    return axios.get(`${BASE_URL}/tainguyen-idc/congsuatdien/congviec/${phieutc_id}/dsthuhoi`, {
      params: { phieutc_id }
    })
  },
  getKetQuaThuHoiByPhieu(axios, phieutc_id) {
    return axios.get(`${BASE_URL}/tainguyen-idc/congsuatdien/congviec/${phieutc_id}/ketquathuhoi`, {
      params: { phieutc_id }
    })
  },
  thuHoiCongSuatDien(axios, payload) {
    return axios.post(`${BASE_URL}/tainguyen-idc/congsuatdien/thuhoi`, payload)
  },
  getDSThietBiChuyenDoiDien(axios, phieutc_id) {
    return axios.get(`${BASE_URL}/tainguyen-idc/congsuatdien/congviec/${phieutc_id}/dschuyendoi`, {
      params: { phieutc_id }
    })
  },
  chuyenDoiCongSuatDien(axios, payload) {
    return axios.post(`${BASE_URL}/tainguyen-idc/congsuatdien/chuyendoicongsuat`, payload)
  },
  getKetQuaChuyenDoiByPhieu(axios, phieutc_id) {
    return axios.get(`${BASE_URL}/tainguyen-idc/congsuatdien/congviec/${phieutc_id}/ketquachuyendoi`, {
      params: { phieutc_id }
    })
  },
  deleteKetQuaCongSuatDien(axios, payload) {
    return axios.post(`${BASE_URL}/tainguyen-idc/congsuatdien/ketquacongviec/delete`, payload)
  },
  selectThietBiCongSuatDien(axios, payload) {
    return axios.post(`${BASE_URL}/tainguyen-idc/congsuatdien/selectthietbi`, payload)
  }
}
