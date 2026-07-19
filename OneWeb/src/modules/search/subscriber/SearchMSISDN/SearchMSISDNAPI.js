export default{
    lay_ds_dv: async (axios, data) => axios.get('/web-tracuu/khoso/ds_dichvu?dichVuVTId=1,10,11', data),
    lay_ds_tt_so: async (axios, data) => axios.get('/web-tracuu/khoso/ds_tt_so', data),
    lay_ds_kieu_so: async (axios, data) => axios.get('/web-tracuu/khoso/ds_kieu_so?kieu=0', data),
    lay_ds_host: async (axios, data) => axios.get('/web-tracuu/khoso/ds_host', data),
    lay_ds_dia_danh: async (axios, data) => axios.get('/web-tracuu/khoso/ds_quan?prefix=3864', data),
    tracuu: async (axios, data) => axios.post('/web-tracuu/khoso/tracuu', data),
    giu_so: async (axios, data) => axios.post('/web-tracuu/khoso/fn_giuso_tracuukhoso', data),
    huy_giu_so: async (axios, data) => axios.post('/web-tracuu/khoso/fn_huy_tracuukhoso', data),
    nghiep_vu: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_ds_dl_nghiepvu', data),
}
    