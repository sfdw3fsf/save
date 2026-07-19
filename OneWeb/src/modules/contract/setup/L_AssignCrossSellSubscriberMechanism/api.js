import axios from 'axios'

export default {
  // lấy danh sách phân chia doanh thu
  lay_ds_PCDT: (axios, data) => axios.post('web-khdn/doanhthu/lay_thongtin_phanchia_doanhthu_socv_macoche', data),
//  lấy danh sách thuê bao đã gán cơ chế bán chéo
  lay_ds_tb_da_gan_coche_bancheo: (axios,data) =>axios.get('web-khdn/doanhthu/lay_ds_thuebao_gan_coche_bancheo?p_hdkh_id='+data),
  gan_coche_thuebao: (axios, data) => axios.post('web-khdn/doanhthu/capnhat_coche_thuebao', data),
  bogan_coche_thuebao: (axios, data) => axios.post('web-khdn/doanhthu/bogan_coche_thuebao', data),
  lay_ds_tinh: async (axios) => axios.get('/web-tracuu/cntt_tskt/ds_tinh'),
  lay_ds_hd_theo_ma_tb: (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),
  giaophieu_ph: (axios, data) => axios.post("web-thicong/giaoviec-vip/select/giaophieu_ph", data),
  bancheo_lay_ds_hd_theo_ma_tb: (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_hd_theo_ma_tb', data),
  lay_ds_hd_tb_theo_hdkh_id: (axios, data) => axios.post("web-hopdong/tracuu_chitiet_thicong/lay_ds_hd_tb_theo_hdkh_id_v3", data),
  bancheo_lay_ds_hd_tb_theo_hdkh_id: (axios, data) => axios.post("/web-bancheo/tracuu/lay_ds_hd_tb_theo_hdkh_id_v2", data),
  // danh mục trạng thái hợp đồng
  sp_lay_trangthai_hd: (axios, data) => axios.post("/web-tracuu/tracuu-thicong/sp_lay_trangthai_hd", data),
  lay_dm_tocdo_mgwan: (axios, data) => axios.post("/web-tracuu/TraCuuPhieuTCBC/lay_dm_tocdo_mgwan", data),
  getDsFileDinhKem: async(axios, data) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_file_congvan?p_congvan_id='+ data),
  
};
