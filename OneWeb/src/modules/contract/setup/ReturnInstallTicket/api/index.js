export default {
  getDsLoaiHD: (axios, data) => axios.post('/web-hopdong/traphieuthicong/ht_loai_hd_combobox_tp', data),
  getDsDichvu: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data),
  getDsKieutra: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_PHIEUTRA', data),
  getDsPhieugiao: (axios, data) => axios.post('/web-hopdong/traphieuthicong/lay_ds_phieu_giao', data),
}
