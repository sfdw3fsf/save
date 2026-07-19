export default {
  kiemtra_capnhat_hodan: async (axios, data) =>
    axios.post("/web-danhba/capnhat-tt_hodan/kiemtra_dl_capnhat_hodan", data),
  updateThongTinHoDan: (axios, data) =>
    axios.post("/web-danhba/capnhat-tt_hodan/capnhat_thongtin_hodan_file", data)
}
