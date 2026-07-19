const BASE_URL = '/web-ecms'

export default {
  getDanhSachMatSan(axios, toaNhaId) {
    return axios.get(`${BASE_URL}/so-do-mat-san/toa-nha/${toaNhaId}/list-mat-san`)
  }
}
