export default{
    lay_loai_cap: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_CAP', data),
    lay_ds_dvvt: async (axios, data) => axios.get('/web-tracuu/khoso/ds_dichvu?dichVuVTId=1,4,11', data),
    lay_ds_lhtb: async (axios, data) => axios.post('/web-baohong/TiepNhanBaoHong/lay_loaihinhtb_theo_dvvt', data),
    luu_lai: async (axios, data) => axios.post('/web-quantri/thongtin-cap/sp_thongtincap_update', data),
    lay_tt_cap: async (axios, data) => axios.post('/web-quantri/thongsone/lay_ds_db_cd_theo_tbid', data),
    tracuu: async (axios, data) => axios.post('/web-quantri/thongtin-cap/sp_lay_tb_sua_diachi_db', data),
    lay_tt_cap_megawan: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_adsl?'+data),
    lay_tt_cap_ims: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_ims?'+data),
}