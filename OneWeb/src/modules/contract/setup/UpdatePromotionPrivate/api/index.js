export default{
    getDanhSachDVVT: (axios, data) => axios.post('/web-hopdong/danhmuc/dichvu_vienthong', data),
    getDanhSachLHTB: (axios, data) => axios.post('/web-hopdong/danhmuc/loaihinh_thuebao', data),
    getDanhSachNKM: (axios, data) => axios.post('/web-hopdong/danhmuc/nguon_khuyen_mai', data),
    getDanhSachCV: (axios, data) => axios.post('/web-hopdong/danhmuc/socongvan_uudai', data),
    getDanhSachTBUD: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/layds_thuebao_uudai', data),
    themMoiUd: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/them_moi_khuyen_mai_theo_cv', data),
    layds_thongtin_hdtb_dc: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/layds_thongtin_hdtb_dc', data)
}
  