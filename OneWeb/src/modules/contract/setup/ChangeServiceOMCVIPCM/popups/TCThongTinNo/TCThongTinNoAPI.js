export default {
  // UR2.6.022_093_001
  FN_TCTHONGTINNO_LOAD: async (axios, request) => axios.post('/web-tracuu/tracuuthongtinno/fn_tcthongtinno_load', request),
  // UR2.6.022_093_002
  FN_TCTHONGTINNO_HIENTHI_DANHSACH: async (axios, request) => axios.post('/web-tracuu/tracuuthongtinno/fn_tcthongtinno_hienthi_danhsach', request),
  // UR2.6.022_093_003
  FN_DTPKYHOADON: async (axios, request) => axios.post('/web-tracuu/tracuuthongtinno/fn_dtpkyhoadon', request),
  // LAY_KY_CUOC_HIEN_HANHN_HANH
  LAY_KY_CUOC_HIEN_HANHN_HANH: async (axios) => axios.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh'),
  GET_CHUKYNO_THEO_KYCUOC: async (axios, param) => axios.get(`/web-thuno/api/thu-no/common/chu-ky-no/ky-cuoc/${param}`)
}
