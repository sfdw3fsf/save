export default{
  layDanhBaTheoMaMay: (axios, data) => axios.post('/web-danhba/chuanhoa_chuquan/lay_danhba_theo_matb', data),
  layThongTinDonVi: (axios, data) => axios.post('/web-quantri/thongsone/sp_ht_donvi_ne', data),
  layTrangThai: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGTHAI_SO'),
  layDanhSachSoNe: (axios, data) => axios.post('/web-quantri/thongsone/sp_danhsach_so_ne', data),
  layDanhBaCoDinh: (axios, data) => axios.post('/web-quantri/thongsone/lay_ds_db_cd_theo_tbid', data),
  spTdttCdUpdate: (axios, data) => axios.post('/web-quantri/thongsone/sp_tdtt_cd_update', data),
  dangKyDichVu: (axios, data) => axios.post('/tichhop/subman/dangKyDichVu', data),
  layDanhSuDungDichVuGiaTang: (axios, data) => axios.post('/web-quantri/thongsone/sp_lay_ds_sudung_dvgt', data),
  layDanhTongDai: (axios, data) => axios.post('/web-quantri/thongsone/sp_ne_danhsach_tongdai', data),
  get_app_config : (axios, data) => axios.post('/web-hopdong/hopdong/get_app_config', data)
}
