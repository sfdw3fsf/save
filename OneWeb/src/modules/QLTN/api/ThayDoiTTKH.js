export default {
    capNhatDanhSachTTLHFile: (axios, data) => axios.put("/web-thuno/api/thu-no/quan-ly-dai-ly/thong-tin-lien-he", data),
    kiemtraLoiMaTTTTLHFile: (axios, data) => axios.post('/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-ma-thanh-toan-loi', data),
    capNhatDanhSachDiaChi_BC: (axios, data) => axios.put("/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-dia-chi-bao-cuoc", data),
    kiemtraLoiDiaChi_BC: (axios, data) => axios.post('/web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-ds-dia-chi-theo-file', data),
    kiemtraLoiMaTT: (axios, data) => axios.post('/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-ma-thanh-toan-loi', data),
    capNhatDSDiaChiCT: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-thong-tin-file', data),
    capNhatHTTTTheoFile: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-httt-file', data),
    kiemtraHTTTTheoFile: (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/lay-hinh-thuc-thanh-toan-theo-danh-sach-thanhtoan', data),
  }
