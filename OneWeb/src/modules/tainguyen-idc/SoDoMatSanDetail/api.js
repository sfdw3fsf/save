const BASE_URL = '/web-ecms'
export default {
  getMatSanDetail(axios, matSanId) {
    return axios.get(`${BASE_URL}/so-do-mat-san/mat-san/${matSanId}`)
  },
  getNangLucDien(axios, matSanId) {
    return axios.get(`${BASE_URL}/so-do-mat-san/mat-san/${matSanId}/nang-luc-dien`)
  },
  getNangLucKhongGian(axios, matSanId) {
    return axios.get(`${BASE_URL}/so-do-mat-san/mat-san/${matSanId}/nang-luc-khong-gian`)
  },
  getPhongZoneByMatSanId(axios, matSanId) {
    return axios.get(`${BASE_URL}/so-do-mat-san/mat-san/${matSanId}/list-phong`)
  },
  getPhongZoneDetail(axios, phongZoneId) {
    return axios.get(`${BASE_URL}/so-do-mat-san/phong-zone/${phongZoneId}`)
  },
  deleteMatSan(axios, matSanId) {
    return axios.post(`${BASE_URL}/danh-muc-mat-san/delete/${matSanId}`)
  },
  // Thuộc tính API
  getThuocTinhMatSan(axios, matSanId) {
    return axios.get(`${BASE_URL}/so-do-mat-san/mat-san/${matSanId}/thuoc-tinh`)
  },
  addThuocTinhMatSan(axios, matSanId, data) {
    return axios.post(`${BASE_URL}/so-do-mat-san/mat-san/${matSanId}/thuoc-tinh`, data)
  },
  updateThuocTinhMatSan(axios, thuocTinhId, data) {
    return axios.post(`${BASE_URL}/so-do-mat-san/thuoc-tinh/${thuocTinhId}`, data)
  },
  deleteThuocTinhMatSan(axios, thuocTinhId) {
    return axios.post(`${BASE_URL}/so-do-mat-san/thuoc-tinh/${thuocTinhId}/delete`)
  }
}
