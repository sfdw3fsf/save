export default {
  LAY_KY_CUOC_HIEN_HANHN_HANH: async (axios) => axios.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh'),
  // UR1.1.035_001
  SP_DS_LOAITIEN: async (axios, data) => axios.post('/web-quantri/ty_gia_quy_doi/sp_ds_loaitien', data),
  // UR1.1.035_002
  SP_DS_TYGIA: async (axios, data) => axios.post('/web-quantri/ty_gia_quy_doi/sp_ds_tygia', data),
  // UR1.1.035_003
  SP_DS_KYCUOC: async (axios, data) => axios.post('/web-quantri/ty_gia_quy_doi/sp_ds_kycuoc', data),
  // UR1.1.035_004
  SP_TYGIA_UPDATE: async (axios, data) => axios.post('/web-quantri/ty_gia_quy_doi/sp_tygia_update', data),
  // UR1.1.035_005
  FN_TYGIA_DELETE: async (axios, data) => axios.post('/web-quantri/ty_gia_quy_doi/fn_tygia_delete', data),
  // Api đa năng lấy tham số mặc định
  LAY_DS_THAMSO_MD_ALL: async (axios, data) => axios.post('/web-hopdong/khuyenmai/lay_ds_thamso_md', data),
  // Api đa năng lấy tham số mặc định v2
  LAY_DS_THAMSO_MD_ALL_V2: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data)
}
