export default {
  getListCustomer: (axios, nhanvien_nhan_id, thang_kt) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_ds_khachhang_ghtt?vnhanvien_nhan_id=${nhanvien_nhan_id}&vthang_kt=${thang_kt}`),
  getListThueBaoDCTT: (axios, khachhang_id, thang_kt) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_ds_thuebao_ghtt_theo_khachhang?vkhachhang_id=${khachhang_id}&vthang_kt=${thang_kt}&vkieu=1`),
  getListThueBaoDCLK: (axios, khachhang_id, thang_kt) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_ds_thuebao_ghtt_theo_khachhang?vkhachhang_id=${khachhang_id}&vthang_kt=${thang_kt}&vkieu=2`),
  getListThueBaoTS: (axios, khachhang_id, thang_kt) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_ds_thuebao_ghtt_theo_khachhang?vkhachhang_id=${khachhang_id}&vthang_kt=${thang_kt}&vkieu=3`),
  getDSHddChuaThanhToan: (axios, vkhachhang_id) => axios.get(`/web-hopdong/hopdong/lay_ds_hd_datcoc_chua_tt?vkhachhang_id=${vkhachhang_id}`),

  getdsTTKetNoi: (axios) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_tt_ketnoi`),
  getListKetQuaOB: (axios) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_ketqua_ob`),
  getdsTinhTrangOB: (axios, data) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_tinhtrang_ob`),
  updateOB: (axios, data) => axios.post(`/web-tratruoc/ob_ghtt/fn_capnhat_giahan_tt_ob`, data),
  // dsLyDo: (axios, data) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_lydo_capnhat_kq_ob`)

  getUserMapIPccInfo:(axios, {vphanvung_id, vuser_ipcc}) => axios.post(`/web-tratruoc/ob_ghtt/sp_lay_tt_nd_theo_user_ipcc`, {vphanvung_id, vuser_ipcc}),
  getInfoCustomer:(axios, {vphanvung_id, vkhachhang_id}) => axios.post(`/web-tratruoc/ob_ghtt/sp_lay_tt_kh_theo_khachhang_id`, {vphanvung_id, vkhachhang_id}),


  getResultOBDetails:(axios, {vphanvung_id, vkhachhang_id}) => axios.post(`/web-tratruoc/ob_ghtt/sp_lay_ct_kqob_theo_khachhang_id`, {vphanvung_id, vkhachhang_id}),
  chamSocKhachHangBaoDuong:(axios, vob_id) => axios.post(`/web-tratruoc/ob_ghtt/dieu_baoduong_ghtt`, {vob_id}),

  //
  getListCustomerOBNhanCong: (axios, {vtungay, vdenngay, vkieu}) => axios.post(`web-tratruoc/ob_ghtt/sp_lay_ds_kh_ob_nhancong`, {vtungay, vdenngay, vkieu}),

  getTapOBNhanCong : (axios) => axios.get(`web-tratruoc/ob_ghtt/sp_lay_tap_ds_ob_nhancong`),
  
  // getDSTB_MoChem: (axios, vhdkh_id) => axios.post(`web-tratruoc/ob_ghtt/sp_lay_thuebao_da_mochem_kc`, {vhdkh_id}),

  getDanSachTB_NhanCong: (axios,{vkhachhang_id, vtungay,vdenngay, vkieu} )=>  axios.post(`web-tratruoc/ob_ghtt/sp_lay_ds_thuebao_ob_nhancong`, {vkhachhang_id, vtungay, vdenngay, vkieu }),
  updateOB_NhanCong: (axios, data) => axios.post(`/web-tratruoc/ob_ghtt/fn_capnhat_giahan_ob_nhancong`, data),
  checkPhieuChamSoc_BaoDuong : (axios, {vngay_ktdc, vthuebao_id }) => axios.post(`/web-tratruoc/ob_ghtt/kiemtra_tao_phieu_dieu_bd`, {vngay_ktdc, vthuebao_id }),
 
  dieuBaoHongOb : (axios, ob_id) => axios.post(`/web-tratruoc/ob_ghtt/dieu_baohong_tu_ob_ghtt`, {ob_id}),

  //update theo yeu cau moi https://cntt.vnpt.vn/browse/BSS-114223
  getListResultOBConnect : (axios, vttkn_id) => axios.post(`web-tratruoc/ob_ghtt/sp_lay_ketqua_ob_theo_nv`, { vttkn_id, vnghiepvu_id: 1 }),

  getListChuDeKN : (axios) => axios.get(`web-tratruoc/ob_ghtt/sp_lay_cdkn_ob_ghtt`),
  dieuKNTuOBGHTT : (axios, { vob_id, vhenkn_tu, vhenkn_den, vchudekn_id }) => axios.post(`web-tratruoc/ob_ghtt/dieu_khieunai_tu_ob_ghtt`, { vob_id, vhenkn_tu, vhenkn_den, vchudekn_id }),
  autoGiaHan : (axios, p_thuebao_ids) => axios.post(`web-tratruoc/ob_ghtt/sp_capnhat_giahan_tudong`, { p_thuebao_ids}),

  //update 15/5/2024 BSS-114903
  chamSocKhachHangBaoDuongV2 : (axios, {vob_id, vds_thuebao_id}) => axios.post(`web-tratruoc/ob_ghtt/dieu_baoduong_ghtt_theo_tb_id`, {vob_id, vds_thuebao_id}),

}

