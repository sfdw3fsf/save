export default {
  // API
  capnhat_loainv_diemtn: (axios, data) => axios.post('web-thicong/gandiem-tinnhiem-lnv/capnhat_loainv_diemtn', data),
  sp_load_cbo_loai_nv: (axios, params) => axios.get('web-thicong/gandiem-tinnhiem-lnv/sp_load_cbo_loai_nv', { params }),
  lay_ds_loainv_diemtn: (axios, params) => axios.get('web-thicong/gandiem-tinnhiem-lnv/lay_ds_loainv_diemtn', { params })
}
