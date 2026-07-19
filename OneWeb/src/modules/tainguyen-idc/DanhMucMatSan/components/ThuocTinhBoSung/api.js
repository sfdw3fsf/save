  const BASE_URL = '/web-ecms'  
  export default {
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