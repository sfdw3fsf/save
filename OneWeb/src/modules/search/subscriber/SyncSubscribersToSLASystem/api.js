export default {
  getDanhSachThueBaoSLA: (axios, body) => axios.post('web-ccdv/dong-bo-thue-bao-sla/sp_lay_ds_tb_user_sla', body),
  kiemTraThueBaoBanCheo: (axios, body) => axios.post('web-ccdv/dong-bo-thue-bao-sla/kiemtra_tb_bancheo', body),
  getThongTinThueBaoSLABc: (axios, body) => axios.post('web-ccdv/dong-bo-thue-bao-sla/lay_tt_tb_user_sla_bc', body),
  getTenTat: (axios, body) => axios.post('web-ccdv/dong-bo-thue-bao-sla/sp_lay_tentat_theo_thuebao_id', body),
  getThongTinTinh: (axios, body) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh', body),
  addThueBaoSLA: (axios, body) => axios.post('tichhop/sla/AddThueBao', body),
  updateDBTBUser: (axios, body) => axios.post('web-ccdv/dong-bo-thue-bao-sla/sp_update_dbtb_user', body),
  removeThueBaoSLA: (axios, body) => axios.post('tichhop/sla/RemoveThueBao', body),
  deleteDBTBUser: (axios, body) => axios.post('web-ccdv/dong-bo-thue-bao-sla/sp_delete_dbtb_user', body),
  updateThueBaoSLA: (axios, body) => axios.post('tichhop/sla/UpdateThueBao', body),
  AddOrUpdateThueBao: (axios, body) => axios.post('tichhop/sla/AddOrUpdateThueBao', body)
}
