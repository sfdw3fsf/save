export default {
  lay_ds_user_theo_chuquan: (axios, data) => axios.post('/web-quantri/capnhat-matkhau/lay_ds_user_theo_chuquan', data),
  capnhat_db_user: (axios, data) => axios.post('/web-quantri/capnhat-matkhau/capnhat_db_user', data),
  dongbo_sla: (axios, data) => axios.post('/tichhop/sla/UpdateAccountInfo', data)
}
