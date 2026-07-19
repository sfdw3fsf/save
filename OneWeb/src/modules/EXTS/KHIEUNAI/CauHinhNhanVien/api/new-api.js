export default {
  get_app_config: async (axios, data) => axios.post('/web-hopdong/hopdong/get_app_config', data),
  sp_delete_nhanvien: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_delete_nhanvien', data),
  sp_insert_update_nhanvien: async (axios, data) => axios.post('web-quantri/nhanvien/sp_insert_update_nhanvien', data),
  map_donvi_id_neo: async (axios, args) => {
    let data = await axios.get(`/web-ccdv/hoanthienhschuyenquyen/map_donvi_id_neo?donvi_id=${args.donvi_id}`)
    if (data.data.data && data.data.data.length > 0) {
      if (data.data.data[0].donvi_id_neo) {
        return data.data.data[0].donvi_id_neo
      }
    }
    return 0
  },
  map_mabc_id_neo: async (axios, args) => {
    let data = await axios.get(`/web-ccdv/hoanthienhschuyenquyen/map_mabc_id_neo?donvi_id=${args.donvi_id}`)
    if (data.data.data && data.data.data.length > 0) {
      if (data.data.data[0].mabc_id_neo) {
        return data.data.data[0].mabc_id_neo
      }
    }
    return 0
  },
  sp_load_treeview: async (axios, data) => axios.get('/web-quantri/donvi/sp_load_treeview', data),
  sp_cbo_donvi_dichvu_cha: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_cbo_donvi_dichvu_cha', data),
  sp_nv_donvi_dl: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_nv_donvi_dl', data),
  sp_cbo_ds_hinhthuc_hopdong: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_cbo_ds_hinhthuc_hopdong', data),
  sp_lay_dulieu_theo_dieukien: async (axios, data) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_dulieu_theo_dieukien', data),
  sp_ds_nhanvien_donvi: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_ds_nhanvien_donvi', data),
  sp_ds_loainhanvien: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_ds_loainhanvien', data),
  sp_ds_donvi_theo_donvidl_loaidv: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_ds_donvi_theo_donvidl_loaidv', data),
  sp_ds_nhanvien_quanly: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_ds_nhanvien_quanly', data),
  fn_tt_nhanvien: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', data),
  sp_get_nhanvien_manv_tennv: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_get_nhanvien_manv_tennv', data),
  thanh_ly_map_id: async (axios, data) => axios.post('/web-hopdong/thanhly/thanh_ly_map_id', data)
}