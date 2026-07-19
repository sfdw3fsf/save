import axios from 'axios'
export default {
  CSS_DICHVU_VT: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data, header),
  QLTN_HINHTHUC_TT: async (axios, data) => axios.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data),
  CSS_LOAIHINH_TB: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', data, header),
  CSS_DOITUONG: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_DOITUONG', data, header),

  sp_lay_tt_db_theo_kh: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_tt_db_theo_kh', data, header),
  sp_lay_tt_db_theo_thuebao: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_tt_db_theo_thuebao', data, header),
  sp_lay_tt_db_theo_thanhtoan: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_tt_db_theo_thanhtoan', data, header),
  lay_ds_dbtb_theo_khid_v2: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/lay_ds_dbtb_theo_khid_v2', data, header),
  lay_ds_dbtb_theo_khid_page: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/lay_ds_dbtb_theo_khid_page', data, header),
  sp_lay_ds_khachhang_theo_matb: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_ds_khachhang_theo_matb', data, header),
  //sp_lay_ds_db_kh_theo_makh_v2: async (axios, data,header) => axios.post('/web-tracuu/tracuu_th/sp_lay_ds_db_kh_theo_makh_v2', data,header),
  sp_lay_ds_db_kh_theo_makh_v2: (data) => axios.post('/web-tracuu/tracuu_th/sp_lay_ds_db_kh_theo_makh_v2', data).then((rs) => rs.data.data),
  lay_thongtin_kh_theo_ma_tb: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/lay_thongtin_kh_theo_ma_tb', data, header),
  sp_lay_tt_kh_by_ma_tt: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_tt_kh_by_ma_tt', data, header),
  sp_lay_tt_chitiet_theo_tb_id: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_tt_chitiet_theo_tb_id', data, header),
  sp_lay_tt_thuebao_by_id: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_tt_thuebao_by_id', data, header),
  sp_lay_tt_kinhdo_vido: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_tt_kinhdo_vido', data, header),
  lay_ds_nhanvien_dbtb_by_id: (axios, data) => axios.post('/web-tracuu/danhba/lay_ds_nhanvien_dbtb_by_id', data),

  //popup toa nha
  css_tinh: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH'),
  css_quan: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN', null),
  css_phuong: async (axios, data) => axios.post('/web-quantri//danhmuc-chung/CSS_PHUONG', data),
  sp_lay_ds_toanha_by_quanphuong: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_toanha_by_quanphuong', data),
  sp_lay_tt_quan_phuong_by_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_quan_phuong_by_id', data),
  sp_lay_ds_thuebao_toanha_by_tag: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_lay_ds_thuebao_toanha_by_tag', data),
  lay_thongtin_ivan: async (axios, data) => axios.post('/tichhop/ivan/a108', data),
  //lay_thongtin_ivan: (data) => axios.post('/tichhop/ivan/a108',data).then(rs => rs.data.data),

  lay_ds_diachi_theo_dbtbid: async (axios, thuebao_id) =>
    axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid', {
      in_thuebao_id: thuebao_id
    }),
  lay_map_thamso: (axios, data) => axios.post('/web-quantri/thamso/map_id', data),
  ///tracuu_th/fn_map_nganhnghe_bhxh
  //fn_map_nganhnghe_bhxh: async (axios, data) => axios.post('/web-tracuu/tracuu_th/fn_map_nganhnghe_bhxh', data),
  fn_map_nganhnghe_bhxh: (data) => axios.post('/web-tracuu/tracuu_th/fn_map_nganhnghe_bhxh', data).then((rs) => rs.data.data),
  fn_tt_capdo_dv: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_capdo_dv', data).then((rs) => rs.data.data),
  fn_tt_loai_cts: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_cts', data).then((rs) => rs.data.data),
  fn_tt_nganhnghe: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nganhnghe', data).then((rs) => rs.data.data),
  fn_tt_phanloai_dv: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_phanloai_dv', data).then((rs) => rs.data.data),
  fn_tt_tocdo_adsl: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl', data).then((rs) => rs.data.data),
  // Tra cứu thông tin hóa đơn điện tử
  LayTTKhachHangHDDT: async (axios, _mst) => axios.get(`/tichhop/hddt/LayThongTinKHHDDT?taxcode=${_mst}`),
  LaySoLuongHDDTDaDK: async (axios, cus_id, _mst) => axios.get(`/tichhop/hddt/LaySoLuongHDDTDaDK?cus_id=${cus_id}&taxcode=${_mst}`),
  LayDSMauHoaDonDaDangKy: async (axios, data) => axios.post(`/tichhop/hddt/LayDSMauHoaDonDaDangKy`, data),
  dongbo_dulieu_hddt: async (axios, data) => axios.post(`/web-hopdong/hopdong/dongbo_dulieu_hddt`, data),
  LayChiTietMauHoaDon: async (axios, id) => axios.get(`/tichhop/hddt/LayChiTietMauHoaDon?inv_id=${id}`),
  SP_LAY_DS_DL_NGHIEPVU: async (axios, data) => axios.post(`/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu`, data),
  tracuuinvoiceinbot: async (axios, data) => axios.post(`/web-thicong/cnttdynamic/tracuuinvoiceinbot`, data),

  //đồng bộ lên CSKH
  dongbo_dulieu_cskh: async (axios, data) => axios.post(`/web-tracuu/tracuutonghop/dongbo_dulieu_cskh`, data)
}
