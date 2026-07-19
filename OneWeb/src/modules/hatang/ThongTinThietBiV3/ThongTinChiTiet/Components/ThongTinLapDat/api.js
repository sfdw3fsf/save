const BASE_URL = '/web-ecms'
export default {
  getDsViTriLapDatTuDia(axios, thietBiId) {
    return axios.get(`${BASE_URL}/hatang/vi-tri-lap-dat-tb/tu-dia/${thietBiId}`)
  },
  getViTriLapDatThietBi(axios,thietBiId) {
    return axios.get(`${BASE_URL}/hatang/vi-tri-lap-dat-tb/${thietBiId}`)
  },
  updateViTriLapDatThietBi(axios, thietBiId, payload) {
    return axios.post(`${BASE_URL}/hatang/vi-tri-lap-dat-tb/${thietBiId}/update`, payload)
  }
}
