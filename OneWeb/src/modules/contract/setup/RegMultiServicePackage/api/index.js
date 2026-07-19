export default{
    getDichvu: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data),
    getLoaiHinhTb: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', {params: data}),
    getKieuYC: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD', {params: data})
  }
