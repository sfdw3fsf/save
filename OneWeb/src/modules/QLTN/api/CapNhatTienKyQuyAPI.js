export default {
  getDanhSachNhanVienDatCoc: async (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-dai-ly/nhan-vien-dat-coc/"+data),
  capNhatTienKyQuy: async (axios, data) => axios.put('web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-nhan-vien-dat-coc', data),
};
