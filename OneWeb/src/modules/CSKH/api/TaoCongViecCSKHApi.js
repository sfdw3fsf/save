import axios from "axios";
export default{
  getDsChuongTrinhCSKH: (axios, data) => axios.get('/web-cskh/api/gan-nhan-vien-cs-khl/danh-sach-ct', {params: data}),
  getDsCongViecCSKH: (axios, data) => axios.get('/web-cskh/api/cong-viec-cskh/danh-sach', {params: data}),
  capNhapCongViecCSKH: (axios, data) => axios.post('/web-cskh/api/cong-viec-cskh/cap-nhat', data),
  xoaCongViecCSKH: (axios, data) => axios.post('/web-cskh/api/cong-viec-cskh/xoa', data),
}
