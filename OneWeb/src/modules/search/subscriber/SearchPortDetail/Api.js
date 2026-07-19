// WinUI.WinUITraCuu.frmTCChiTietPort,PTTB.exe,ShowDialog
export default {
  getDsDonViQuanLy: (axios, data) => axios.get(`/web-tracuu/ctport/ds_donvi?donViId=${data.donViId}&loaiDVId=${data.loaiDVId}`),
  getDsDonViThiCong: (axios, data) => axios.get(`/web-tracuu/ctport/ds_tram_tc?donViId=${data.donViId}&loaiDVId=${data.loaiDVId}`),
  getDsTramThietBi: (axios, data) => axios.get(`/web-tracuu/ctport/ds_tram_tb?donViId=${data.donViId}&loaiDVId=${data.loaiDVId}`),
  getDsDSLamTheoDonVi: (axios, data) => axios.get(`/web-tracuu/ctport/ds_dslam?donViId=${data.donViId}`),
  getDmTrangThaiPort: (axios, data) => axios.get(`/web-tracuu/ctport/ds_tt_port`),
  LayDanhSach: (axios, data) => axios.post(`/web-tracuu/ctport/tracuu`, data),
  getDsKhachHang: (axios, data) => axios.get(`/web-tracuu/pttb/tracuu_khachhang_sudung_oa_zalo?sdt=${data.ma_thuebao}`)
}
