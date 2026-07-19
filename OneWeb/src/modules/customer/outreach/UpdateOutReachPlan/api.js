export default {
    tracuubaohanhthongtinlo: (axios, data) => axios.post('/web-tracuu/psis/tracuubaohanhthongtinlo', data),
    nghiemthulobaohanh: (axios, data) => axios.post('/tichhop/psis/nghiemthulobaohanh', data),
    fn_tudonggiao_phieubh_psis: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-bao-hanh/fn_tudonggiao_phieubh_psis', data)
  }
  