export default {
  ds_thietbi_idc_sudung: async (axios, data, header) => axios.post('/web-hopdong/danh_sach_so_thanh_vien/lay_ds_thietbi_idc_sudung', data, header),
  ds_thietbi_idc_dangky: async (axios, data, header) => axios.post('/web-hopdong/danh_sach_so_thanh_vien/lay_ds_thietbi_idc_dangky', data, header)
}
