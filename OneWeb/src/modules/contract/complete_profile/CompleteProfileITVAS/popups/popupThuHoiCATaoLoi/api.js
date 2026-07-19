import axios from 'axios'
export default {
  upload_file: (data, header) => axios.post('/web-quantri/upload', data, header),
  capnhat_file_hs: (data, header) => axios.post('/web-thicong/dvcntt/bosung_hoso_dvcntt', data, header),
  lay_huonggiao_tiepnhan_dvcntt: async (data, header) => axios.post('/web-hopdong/thuhoi-chungthuca-loi/lay_huonggiao_tiepnhan_dvcntt', data, header),
  capnhat_hd_thuhoi_ca_loi: async (data, header) => axios.post('/web-hopdong/thuhoi-chungthuca-loi/capnhat_hd_thuhoi_ca_loi', data, header),
  kt_xoa_hd_cntt: async(data,header) => axios.post('/web-thicong/dvcntt/kiemtra_truoc_xoahd_cntt', data, header),
  load_hopdong_thuhoi_taoloi: async(data,header) => axios.post('/web-thicong/dvcntt/lay_tthd_ca_taoloi_by_hdtb_cha_id', data, header),
  xoa_hdkh_v2: async (data, header) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdkh_v2', data, header)
}
