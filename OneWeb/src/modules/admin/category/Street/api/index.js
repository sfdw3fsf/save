export default {
  getTownAreas: (axios, params) => axios.get('/web-quantri/danhmuc/diaban/nhom-pho', { params }),
  getDistricts: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN', { params }),
  getDistrictsAll: (axios, params) => axios.get('/web-quantri/danhmuc-chung/sp_lay_ds_quan', { params }),
  getWardsAdded: (axios, params) => axios.get('/web-quantri/danhmuc/diaban/phuong-theo-pho', { params }),
  getWardsNotAdded: (axios, params) => axios.get('/web-quantri/danhmuc/diaban/phuong-chua-gan', { params }),
  getWardsBelongDistrict: (axios, data) => axios.post('/web-quantri/danhmuc-chung/CSS_PHUONG', data),
  // getTownsByWard: (axios, data) => axios.post('/web-quantri/danhmuc-chung/CSS_PHO', data),
  getTownsByWard: (axios, data) => axios.post('/web-quantri/danhmuc/diaban/pho-theo-quanphuong', data),
  checkDataBeforeSubmit: (axios, data) => axios.post('/web-quantri/diaban/kiemtra-dl-pho', data),
  submitNewTownCode: (axios, data) => axios.post('/web-quantri/diaban/them-ma-pho', data),
  submitNewTown: (axios, data) => axios.post('/web-quantri/diaban/them-pho', data),
  submitModifyTown: (axios, data) => axios.post('/web-quantri/diaban/sua-pho', data),
  submitDeleteTown: (axios, data) => axios.post('/web-quantri/diaban/xoa-pho', data)
}
