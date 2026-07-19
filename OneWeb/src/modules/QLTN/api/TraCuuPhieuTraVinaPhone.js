export default {
  getMaTTNeoVNP: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/ma-tt-neo-vnp', {params: data}),
  getLSPhieuTra: (axios, data) => axios.post('/ccbs/qltn/ts_tracuu_ls_phieutra', data),
  getLSNoTTTraPhieu: (axios, data) => axios.post('/ccbs/qltn/ts_tracuu_ls_no_tt_tra_phieu', data),
  getLSTraNoCCBS: (axios, data) => axios.post('/web-thuno/api/quan-ly-thu-no/tra-cuu-lich-su-tra-no-ccbs', data),
  getThongTinPhieuTra: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/lay-thong-tin-phieu-tra-ccbs', {params: data}),
}
