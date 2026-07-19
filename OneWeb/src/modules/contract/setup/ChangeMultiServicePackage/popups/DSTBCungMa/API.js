export default {
  // select box dich vu
  GET_DICHVU_VT: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  CSS_LOAIHINH_TB: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB')
}
