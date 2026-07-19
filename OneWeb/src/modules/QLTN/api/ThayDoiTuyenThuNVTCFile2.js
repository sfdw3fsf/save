export default {
  capNhatDanhSach: (axios, data) => axios.put("/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-tuyen-thu-theo-file", data),
  capNhatDanhSach_HTTT: (axios, data) => axios.put("/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-tuyen-thu-httt-theo-file", data),
  getThongTinDaiLy: (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-thong-tin-thanh-toan-theo-thanh-toan',{params:data}),
  getThongTinMaTT: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-tuyen-thu-theo-file", data),
}
