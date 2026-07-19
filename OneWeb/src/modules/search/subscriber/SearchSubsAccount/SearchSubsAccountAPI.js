export default {
  lay_dk_tracuu: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_dk_tracuu', data, header),
  lay_ds_dvvt: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_dvvt', data, header),
  lay_ds_donvi: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_donvi', data, header),
  lay_thamso_md: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_thamso_md', data, header),
  lay_ds_dv_timkiem: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_dv_timkiem', data, header),
  // lay_ds_loaihinh_theo_dichvu: async (axios, data,header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_loaihinh_theo_dichvu', data,header),
  lay_ds_tb_svlan: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_tb_svlan', data, header),
  timkiem_theo_site_id: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/timkiem_theo_site_id', data, header),
  lay_db_theo_matb: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_db_theo_matb', data, header),
  tracuu_tb_bh: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/tracuu_tb_bh', data, header),
  tracuu_theo_db_tb: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/tracuu_theo_db_tb', data, header),
  lay_ds_loaihinh_theo_dichvu: async (axios, data, header) => axios.post('/web-hopdong/danhmuc/loaihinh_thuebao', data, header),
  tra_cuu_db_tb_dieukien: async (axios, data, header) => axios.post('/web-tracuu/TraCuuDanhBaTB/tra_cuu_db_tb_dieukien', data, header),

  post_fn_lay_tt_danhba: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/fn_lay_tt_danhba_clob', data),
  api_capnhat_tinh_quan_phuong_id_danhba: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/api_capnhat_tinh_quan_phuong_id_danhba', data),
  api_capnhat_diachi_thuebao: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/api_capnhat_diachi', data),
}
