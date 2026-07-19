export default {
  getDSPhieuThiCong: (axios, params) => axios.post('/web-ecms/idc/danhsach-congviec/v2', params),
  getDanhMucTaiNguyenIDC: (axios, params) => axios.post('/web-ecms/idc/danh-muc/common', params),
  getDSDichVuVT: (axios) => axios.post('web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt'),
  createUpdatePhieuThiCong: (axios, params) =>
    axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/create-or-update', params),
  getDSPhieuSO: (axios, params) => axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/ds-cv-kenhban', params),
  getDSPhieuSOV3: (axios, params) => axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/ds-cv-kenhban-v3', params),
  multipleUploadFile: (axios, formData) => axios.post('/web-ecms/storage/multiple-upload', formData),
  getDetailPhieuThiCong: (axios, params) => axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/ct-phieu-cv', params),
  chuyenXuLyPhieuThiCong: (axios, params) =>
    axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/chuyen-thuchien-phieu-cv', params),
  getPresigneUrl: (axios, params) => axios.post('/web-ecms/storage/getPresignedUrl', params),
  deleteFile: (axios, params) => axios.post('/web-ecms/storage/deleteFile', params),
  getDSPhieuSOCon: (axios, params) => axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/ds-cv-con', params),
  getDsCongViec: (axios, params) =>
    axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/danhsach-congviec-kenhban', params),
  createCongViecKenhBan: (axios, params) =>
    axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/insert-congviec-kenhban', params),
  deleteCongViec: (axios, params) =>
    axios.post('/web-ecms/tainguyen-idc/tiepnhan-thicong/delete-congviec', params),
  getDanhMucLoaiCongViec: (axios, data) =>
    axios.post('/web-ecms/idc/danh-muc/common', {
      // IDC-LOAICV, IDC-DONVI
      loaiDanhMuc: data.loaiDanhMuc,
      thamSo1: null,
      thamSo2: null
    }),
  getDanhMucIDC: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-DS-IDC', token.getDonViID())
  }
}

// ============= HELPER FUNCTIONS ============= //

// Lấy danh mục chung
const getDanhMucCommon = async (axios, loaiDanhMuc, thamSo1 = null) => {
  try {
    const payload = { loaiDanhMuc }
    if (thamSo1 !== null && thamSo1 !== undefined) {
      payload.thamSo1 = thamSo1
    }
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', payload)
    return rs && rs.data ? rs.data.data : []
  } catch (error) {
    handleApiError(error)
    return []
  }
}

const handleApiError = (e) => {
  console.log('API ERROR: ', e)
  if (e?.data?.message_detail) {
    throw new Error(e.data.message_detail)
  } else {
    console.error(e)
  }
}