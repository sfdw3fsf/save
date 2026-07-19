// WinUI.WinUIDieuHanhThiCong.frmSuaTramVT
// [UR2.4.048] - Thay đổi đơn vị thi công
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1845219708&range=E1293
export default {
  getDMDichVuVT: (axios, data) => axios.get(`/web-thicong/danhmuc/lay_danhsach_dichvu_vienthong`),
  getDMLoaiHinh: (axios, data) => axios.get(`/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId=${data.dichVuVtId}`),
  getDMQuanHuyen: (axios, data) => axios.get(`/web-thicong/danhmuc/lay_danhsach_quan`),
  getDMPhuongXa: (axios, data) => axios.get(`/web-thicong/danhmuc/lay_ds_phuongxa?quanId=${data.quanId}`),
  getDMKieuDonVi: (axios, data) => axios.get(`/web-thicong/danhmuc/lay_danhsach_kieu_donvi`),
  getDSDacDiemDieuKien: (axios, data) => axios.post(`/web-thicong/thuebao/lay_ds_dacdiem_theo_dieukien`, data),
  getDMPho: (axios, data) => axios.get(`/web-thicong/danhmuc/lay_ds_pho_ap_khu?phuongId=${data.phuongId}&nhomPhoId=${data.nhomPhoId}`),
  getDanhSachTramTheoDK: (axios, data) => axios.post(`/web-thicong/thuebao/lay_ds_tram_theo_dieukien`, data),
  getDSHopDongTheoMaTB: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_hopdong_theo_mathuebao?maTb=${data.maTb}`),
  getDSHopDongTheoHDKHID: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_hopdong_theo_hdkhid?hdkhId=${data.hdkhId}`),
  getDSHopDongTheoHDTBID: (axios, data) => axios.get(`/web-thicong/thuebao/lay_ds_diachi_theo_hdtbid?hdtbId=${data.hdtbId}`),
  updateTram: (axios, data) => axios.post(`/web-thicong/thuebao/capnhat_tramvattu_lapdat`, data),
  kiemtraDuLieu: (axios, data) => axios.post(`/web-thicong/thuebao/kiemtra_dulieu`, data),
  layBangThamso: (axios, data) => axios.get(`/web-thicong/thuebao/lay_bang_thamso?hdtbId=${data.hdtbId}`),
  layDuLieuDonViByID: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi`, data),
  fn_tt_hd_thuebao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, data)
}
