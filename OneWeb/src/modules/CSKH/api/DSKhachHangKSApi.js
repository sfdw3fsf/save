import axios from "axios";
export default{
  getDsLoaiHopDong: (axios) => axios.get('/web-cskh/api/danh-muc/loai-hop-dong'),
//   getDsCongViecCSKHTheoNguoiTao: (axios, data) => axios.post('/web-cskh/api/duyet-cong-viec-cskh/ds-cv-theo-nguoi-tao', data),
//   duyetCongViecCSKH: (axios, data) => axios.post('/web-cskh/api/duyet-cong-viec-cskh/duyet', data),
}
