export default{
  lay_muccuoc_tb: (axios,data) => axios.post('/web-bancheo/danhmuc/lay_muccuoc_tb', data),
  layds_trangbi: (axios,data) => axios.post('/web-bancheo/danhmuc/layds_trangbi', data),
  lay_muccuoc_tb_dt: (axios,data) => axios.post('/web-bancheo/danhmuc/lay_muccuoc_tb_dt', data),
}
