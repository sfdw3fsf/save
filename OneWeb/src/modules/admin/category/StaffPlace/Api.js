// WinUI.WinUIBangTuDien.frmDiaDanh_NhanVien_V3,PTTB.exe,1
// [UR2.13.006] - Gán địa danh - NVKD
export default {
  getDsKhuVuc: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_khuvuc?kieugoiId=${data.kieugoiId}`),
  getDsKhuVucPx: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_khuvuc_px?khuvucId=${data.khuvucId}&loainvId=${data.loainvId}`),
  getDsKhuVucById: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_khuvuc_theo_id?khuvucId=${data.khuvucId}`),
  getDsNhanVien: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_nhanvien`),
  getDsNhanVienPx: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_nhanvien_px?loainvId=${data.loainvId}&nhanvienId=${data.nhanvienId}`),
  getDsLoaiNhanVien: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_loai_nhanvien?kieu=${data.kieu}&nhanvienId=${data.nhanvienId}`),
  getDsNhanVienByLoaiNV: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_nhanvien_px?loainvId=${data.loainvId}`),
  getDsNhanVienByKieu: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_loai_nhanvien?kieu=${data.kieuId}`),
  capNhatNhanVien: (axios, data) => axios.post(`/web-thicong/thuebao/capnhat_danhsach_nhanvien_px`, data),
  getKey: (axios, data) => axios.post(`/web-quantri/donvitinh/get_keys`, data)
}
