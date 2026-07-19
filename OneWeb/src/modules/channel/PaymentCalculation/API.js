
export default {
  tinhThuLao : (axios, data) => axios.post('/web-khdn/quanlykenh/tinhthulao_hoahong_daily_ctv_theocongdoan', data),
  xemsolieuexcel : (axios, data) => axios.post('/web-khdn/chuyennokenh/xemsolieuexcel', data),
}