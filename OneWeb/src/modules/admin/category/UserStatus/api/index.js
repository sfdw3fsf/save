export default {
  ur_005_get_session_list: (axios, data) => axios.post('/web-quantri/trangthai-nguoidung/get_session_list', data),
  ur_004_get_session_detail: (axios, data) => axios.post('/web-quantri/trangthai-nguoidung/get_session_detail', data),

}
