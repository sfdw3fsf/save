export default{
    get_ds_san_pham_quay_vong: async (axios, data) => axios.get('/web-quantri/spmo/ds_san_pham_quay_vong', data),
    post_nhap_san_pham_quay_vong: async (axios, data) => axios.post('/web-quantri/spmo/nhap_san_pham_quay_vong', data),
  }