export default {
  getSubcriberTypes: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', { params }),
  getBrasList: (axios, data) => axios.post('/web-ccdv/khaibaoims/sp_lay_ds_bras_adsl', data),
  getServices: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', { params }),
  getDsLam: (axios, params) => axios.get('/web-quantri/quan-ly-danh-ba/lay-danhmuc/DSLAM', { params }),
  getSubcriberInforList: (axios, data) => axios.post('/web-hopdong/hopdong/lay_danhba_theo_matb', data),
  getSpeedList: (axios, params) => axios.get('/web-quantri/quan-ly-danh-ba/lay-danhmuc/TOCDO_ADSL', { params }),
  getDsAdslBySubcriberId: (axios, params) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_adsl', { params }),
  syncVisaAndPttb: (axios, data) => axios.post('/web-quantri/quan-ly-danh-ba/sync_pttb_and_visa', data),
  checkSubcriber: (axios, data) => axios.post('/tichhop/visa-app/lookupRASAccount', data)
}
