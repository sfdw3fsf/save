export default{
  LAY_DS_HOPDONG_THEO_MA_GD: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),
  LAY_HTTT: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_HINHTHUC_TT', data),
  LAY_DS_LoaiKH: (axios, data) => axios.get('web-quantri/danhmuc-chung/CSS_LOAI_KH', data),
  LAY_DS_LoaiGiayTo: (axios, data) => axios.get('web-quantri/danhmuc-chung/CSS_LOAI_GT', data),
  LAY_DS_QuocTich: (axios, data) => axios.get('web-quantri/danhmuc-chung/CSS_QUOCTICH', data),
  LAY_DS_NganhNghe: (axios, data) => axios.get('web-quantri/danhmuc-chung/CSS_NGANHNGHE', data),
  LAY_DS_DanToc: (axios, data) => axios.get('web-quantri/danhmuc-chung/CSS_DANTOC', data),
  LAY_DS_NganHang: (axios, data) => axios.get('web-quantri/danhmuc-chung/CSS_NGANHANG', data),
  LAY_DS_DVThanhToan: (axios, data) => axios.post('web-quantri/danhmuc/donvi/theo_loai', data),
  LAY_DS_DoiTuong: (axios, data) => axios.get('web-hopdong/hopdong_dungthu/lay_ds_doituong', data),
  LAY_DS_DichVu: (axios, data) => axios.post('web-baohong/TraCuuPhieuGiaoDenBH/sp_ht_dichvuvt_cbobox_lm', data),
  LAY_DS_GOICUOC_DIDONG: (axios, data) => axios.post('web-hopdong/lapdatmoi/sp_ht_goicuoc_dd', data),
  LAY_DS_KieuLD: (axios, data) => axios.post('web-ccdv/ds_yeucau_khaosat/lay_ds_kieu_ld', data),
  LAY_DS_DTVINAPHONE: (axios, data) => axios.get('web-ccdv/khaibaotsl/get_all_doituong_neo', data),


  
  }