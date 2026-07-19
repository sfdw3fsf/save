export default {
  getDsDVVTTinh: (axios, params) => axios.get('web-quantri/danhmuc/donvi_vienthong_tinh', {params}),
  getDsKyHoaDon: (axios, params) => axios.get('web-baohong/baohong/dsKyHD', {params}),
  getDsChuKy: (axios, params) => axios.get('web-baohong/baohong/dsChuKyNoTheoKyHD', {params}),
  getDsLoaiThongBaoSuCo: (axios, params) => axios.get('web-baohong/danhmuc/ds_loai_thongbao_suco', {params}),
  getDsDonViDuocThongBao: (axios, params) => axios.get('web-quantri/danhmuc/donvi_duoc_thongbao', {params}),
  getDsTienDoXuLy: (axios, params) => axios.get('web-quantri/danhmuc/trangthai_xuly_suco', {params}),
  getDsThongBaoSuCoTTVT: (axios, data) => axios.post('web-baohong/baohong/dsThongBaoSuCoTTVT', data),
  themMoiThongTin: (axios, data) => axios.post('web-baohong/baohong/cnThongBaoSuCoTTVT', data),
  getDsCboBox: (axios, params) => axios.get('web-ccdv/tien_trinh_bao_hong/lay_danhmuc_thongbao_suco_ttvt_v2', {params}),
  getDsPTHT: (axios, params) => axios.get('web-baohong/baohong/lay_ds_ptm_theo_loaitbi', {params})
}
