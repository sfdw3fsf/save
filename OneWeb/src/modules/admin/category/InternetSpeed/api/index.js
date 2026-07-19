export default{
    getComboLoaiHinh: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', data),
    getProfile: (axios, data) => axios.get('/web-quantri/tocdo/lay_ds_profile_dsl', data),
    getDanhSachGrid: (axios, data) => axios.post('/web-quantri/tocdo/sp_ht_tocdo_adsl', data),
    KtraDuLieu: (axios, data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_thamso_md_mats', data),
    Them_Sua_Xoa: (axios, data) => axios.post('/web-quantri/tocdo/sp_capnhat_tocdo_adsl', data),
  }