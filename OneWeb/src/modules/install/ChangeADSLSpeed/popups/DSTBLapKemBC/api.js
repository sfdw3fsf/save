export default{
  lay_ds_thuebao_tn_theo_hdkhid : (axios,data) => axios.get('/web-bancheo/tracuu/lay_ds_thuebao_tn_theo_hdkhid?hdkh_id='+ data),
  check_map_id: (axios,data) => axios.post('/web-hopdong/thongtin_tratruoc_dn/map_id', data),
}
