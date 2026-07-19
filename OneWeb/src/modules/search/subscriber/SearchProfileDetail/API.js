export default{
    
  lay_danhsach_kho_hoso: async (axios) => axios.get('/web-tracuu/danhmuc/lay_danhsach_kho_hoso'),
  
  lay_danhsach_trangthai_bhs: async (axios) => axios.get('/web-tracuu/danhmuc/lay_danhsach_trangthai_bhs'),
  
  lay_danhsach_hopdong_theo_matb: async (axios, data) => axios.get('/web-tracuu/pttb/lay_danhsach_hopdong_theo_matb'+data),

  lay_thongtin_chitiet_hoso: async (axios, data) => axios.get('/web-tracuu/pttb/lay_thongtin_chitiet_hoso'+data),

  lay_ds_file_hoso: async (axios, data) => axios.get('/web-tracuu/pttb/lay_ds_file_hoso'+data),

  tracuu_chitiet_hoso: async (axios, data) => axios.get('/web-tracuu/pttb/tracuu_chitiet_hoso'+data),

  tracuu_hopdong: async (axios, data) => axios.get(`/tichhop/eContract/TraCuuHopDong?` + data)

  }
  