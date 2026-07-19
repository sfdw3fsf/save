export default{
    lay_ds_tinh: async (axios, data,header) => axios.get('/web-tracuu/cntt_tskt/ds_tinh', data,header),
    lay_ds_dvvt: async (axios, data,header) => axios.get('/web-tracuu/khoso/ds_dichvu?dichVuVTId=13,14,15,16', data,header),
    lay_ds_lh_tb: async (axios, data,header) => axios.post('/web-baohong/TiepNhanBaoHong/lay_loaihinhtb_theo_dvvt', data,header),
    tracuu: async (axios, data,header) => axios.post('/web-tracuu/cntt_tskt/tim_kiem', data,header),
    // lay_ds_dv_timkiem: async (axios, data,header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_dv_timkiem', data,header),
    // lay_ds_loaihinh_theo_dichvu: async (axios, data,header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_loaihinh_theo_dichvu', data,header),
    // lay_ds_tb_svlan: async (axios, data,header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_tb_svlan', data,header),
    // timkiem_theo_site_id: async (axios, data,header) => axios.post('/web-tracuu/TraCuuDanhBaTB/timkiem_theo_site_id', data,header),
    // lay_db_theo_matb: async (axios, data,header) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_db_theo_matb', data,header),
    // tracuu_tb_bh: async (axios, data,header) => axios.post('/web-tracuu/TraCuuDanhBaTB/tracuu_tb_bh', data,header),
    lay_ds_tskt: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_danhsach_thuoctinh_v3', data),
}
    