const BASE_URL = '/web-ecms'

export default {
  getMatSanById(axios, matSanId) {
    return axios.get(`${BASE_URL}/danh-muc-mat-san/${matSanId}/detail`)
  },
  getAllMatSan(axios) {
    return axios.get(`${BASE_URL}/danh-muc-mat-san/get-all`)
  },
  checkTenMatSan(axios, ten, id = 0) {
    return axios.post(`${BASE_URL}/danh-muc-mat-san/check-ten`, { ten, id })
  },
  uploadImage(axios, formData) {
    return axios.post(`${BASE_URL}/danh-muc-mat-san/upload-image`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  },
  getIdcToaNha(axios) {
    return axios.get(`${BASE_URL}/danh-muc-mat-san/idc-toanha`)
  },
  insertUpdateMatSan(axios, data) {
    return axios.post(`${BASE_URL}/danh-muc-mat-san/insert-update`, data)
  },
  deleteMatSan(axios, matSanId) {
    return axios.post(`${BASE_URL}/danh-muc-mat-san/delete/${matSanId}`)
  },
  validateListMatSan(axios, data) {
    return axios.post(`${BASE_URL}/danh-muc-mat-san/validate-list-mat-san`, data)
  },
  insertUpdateMatSan(axios, data) {
    return axios.post(`${BASE_URL}/danh-muc-mat-san/insert-update`, data)
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
