export default {
  lay_ds_donvi: async (axios) => axios.post(`/web-hopdong/ds_phieukhaosat_taptrung/lay_ds_donvi`),
  sp_lay_thongtin_section_1_khachhang_banke_79: (axios, data) => axios.post('/web-hopdong/xuat-dlhd/sp_lay_thongtin_section_1_khachhang_banke_79', data),
  sp_lay_thongtin_section_2_ds_somay_banke_79: (axios, data) => axios.post('/web-hopdong/xuat-dlhd/sp_lay_thongtin_section_2_ds_somay_banke_79', data),
  sp_lay_thongtin_section_3_ds_somay_banke_79: (axios, data) => axios.post('/web-hopdong/xuat-dlhd/sp_lay_thongtin_section_3_ds_somay_banke_79', data),
  sp_lay_thongtin_section_4_didong_banke_79: (axios, data) => axios.post('/web-hopdong/xuat-dlhd/sp_lay_thongtin_section_4_didong_banke_79', data),
  sp_lay_thongtin_section_5_ct_didong_banke_79: (axios, data) => axios.post('/web-hopdong/xuat-dlhd/sp_lay_thongtin_section_5_ct_didong_banke_79', data),
  sp_lay_thongtin_ds_don_vi: async (axios, data) => axios.get('/web-hopdong/xuat-dlhd/sp_lay_thongtin_ds_don_vi', data),
  sp_layds_banke: async (axios, data) => axios.get('/web-hopdong/xuat-dlhd/sp_layds_banke', data),
}
