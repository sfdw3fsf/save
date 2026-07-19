// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=620043524&range=A1553
// WinUI.WinUILapHopDong.frmHD_SelfCare,PTTB.exe
export default {
  getDmTtHDSeflCare: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DS_TRANGTHAI_GIAO_HD_SEFLCARE', data),
  getDsDonViQLTT: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DS_DONVI_QL_TT', data),
  getDsDonViPBH: (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DS_DONVI_QL_TT_PBH', data),
  getDsHopDong: (axios, data) => axios.post('/web-ccdv/HDSeflCare/lay_ds_hd_selfcare', data),
  getDsLogXuLy: (axios, data) => axios.post('/web-ccdv/HDSeflCare/sp_layds_logxl_seflcare', data),
  giaoPhieu: (axios, data) => axios.post('/web-ccdv/HDSeflCare/giaonv_hd_selfcare', data),
  layDsNhanVienTheoDonVi: (axios, data) => axios.post('/web-ccdv/HDSeflCare/lay_ds_nlv_theo_donvi', data)
}
