export default{
  kiemtraFileDangKy: async (axios, data) => axios.post('/web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-file-dang-ky-in-hoa-don', data),
  capnhatFileDangKy: async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-httt-file', data)
}
