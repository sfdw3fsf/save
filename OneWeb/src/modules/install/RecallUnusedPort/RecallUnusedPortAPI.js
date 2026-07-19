export default{
    get_loai_hd: async (axios, data) => axios.get('/web-thicong/danhmuc/loai_hd', data),
    get_dichvu: async (axios, data) => axios.get('/web-thicong/danhmuc/dichvu', data),
    post_nhap_san_pham_quay_vong: async (axios, data) => axios.post('/web-quantri/spmo/nhap_san_pham_quay_vong', data),
  }