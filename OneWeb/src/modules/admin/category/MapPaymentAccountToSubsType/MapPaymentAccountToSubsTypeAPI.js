export default{
  layDanhSachKMTTLHTB: (axios, data) => axios.post('/web-quantri/khoanmuc-thanhtoan/sp_lay_ds_kmtt_lhtb', data),
  spGhiLaiKMTTLHTB: (axios, data) => axios.post('web-quantri/khoanmuc-thanhtoan/sp_ghilai_kmtt_lhtb', data),

}
