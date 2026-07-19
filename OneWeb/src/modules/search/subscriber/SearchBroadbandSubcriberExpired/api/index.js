export default{
  getDanhSach: (axios, data) => axios.post('/web-tracuu/tracuu_tb_sap_hethan/lay_cay_diaban_theo_nhanvien', data),
  getDanhSach_ThueBao: (axios, data) => axios.post('/web-tracuu/tracuu_tb_sap_hethan/lay_ds_tb_hethan_dc_km', data),
  
}