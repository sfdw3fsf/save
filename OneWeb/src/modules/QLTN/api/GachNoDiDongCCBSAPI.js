export default {
  getTTNoTheoMaTT: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-ma-tt-neo-vnp", {params: data}),
  getTTNoTheoTBao: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-thong-tin-no-thue-bao", {params: data}),
  getTTNoTheoTBao2: (axios, data) => axios.post("/ccbs/executor/ts_tracuu_ls_no_tt_tra", data),
  getDSPhieuTra: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-ds-phieu-tra", {params: data}),
  getDSThuNoTamThuTraTruoc: (axios, data) => axios.post('ccbs/executor/ts_thuno_tamthu_tratruoc_nochitiet', data),
  gachNoCCBS: (axios, data) => axios.post("/web-thuno/api/quan-ly-thu-no/dong-bo-sync-ws-ccbs", data),
  getTTNoDBSync: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/gach-no-thue-bao", { params: data }),
  getTTNoWS: (axios, data) => axios.get("/web-thuno/api/thu-no/dong-bo/tra-cu-thong-tin-no-ccbs", { params: data }),

}
