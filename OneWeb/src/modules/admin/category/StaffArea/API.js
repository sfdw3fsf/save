// WinUI.WinUIBangTuDien.frmKhuVuc_NV,PTTB.exe

export default {
  getDmLoaiKhuVuc: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_ds_loaikv', data),
  getDmLoaiNhanVien: (axios, data) => axios.post('web-quantri/nhanvien/sp_lay_ds_loainv', data),
  getDmKhuVuc: (axios, data) => axios.post('/web-quantri/diaban/ds-khuvuc-khoan', data),
  getDmKhuVucByLoaiKV: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_ds_khuvuc_by_loaikv', data),
  getDmKhuVucTree: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_ds_khuvuc_view_tree', data),
  getDsKhuVucByMaNV: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_khuvuc_by_manv', data),
  getDsDonVi: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_ds_donvi_by_nhomlnv', data),
  getDmPhongBanHang: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_tt_nv_macdinh', data),
  getDanhSachNhanVienByKhuVuc: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_ds_nhanvien_by_khuvuc', data),
  getDanhSachNhanVienDaGan: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_ds_loainv', data),
  getDanhSachNhanVienChuaGan: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_ds_nhanvien_by_khuvuc', data),
  capnhatNhanvienMacdinh: (axios, data) => axios.post('/web-quantri/nhanvien/capnhat_nhanvien_macdinh', data),
  capnhatKhuvuc_nv_nh: (axios, data) => axios.post('/web-quantri/nhanvien/sp_khuvuc_nv_nh', data),
  getLogsKhuVucNhanVien: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_log_khuvuc_nv', data),
  getThamSoMacDinh: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_gt_thamso_md_mats', data),
  sp_lay_tt_nv_macdinh: (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_tt_nv_macdinh', data),

  get_DmNhomNhiemVu:(axios, data) =>axios.get('web-quantri/nhanvien/sp_lay_nhom_nvu_cv', data),
  get_DmNhiemVuByNhomNhiemVu:(axios, data) =>axios.post('web-quantri/nhanvien/sp_lay_nhiemvu_theo_nhom_nv', data),
  get_NhiemVuByKhuVuc:(axios, data) =>axios.post('web-quantri/nhanvien/lay_nhiemvu_theo_khuvuc_nv_id', data),
  view_GanNhiemVu:(axios, data) =>axios.post('web-quantri/nhanvien/lay_ds_khuvuc_nvu', data),
  capnhat_KV_NV:(axios, data) =>axios.post('web-quantri/nhanvien/capnhat_khuvuc_nvu', data),
  xoa_KV_NV:(axios, data) =>axios.post('web-quantri/nhanvien/xoa_khuvuc_nvu', data),





}
