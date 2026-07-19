import axios from "axios";

export default{
  getDMBuocXuLyNo: async (axios) => axios.get('/web-thuno/api/thu-no/xu-ly-no/buoc-xu-ly-no'),
  getLanTaoBuocXuLyNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/lan-tao/buoc-xu-ly-no/' + data),
  getNhanVienTheoLoai: async (axios, id) => axios.get('/web-thuno/api/thu-no/common/nhan-vien/loai-nhan-vien/' + id)
}
