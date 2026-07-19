export default{
  getDsThamSo: (axios, data) => axios.post(`/web-quantri/phanquyenhths/fn_lay_ds_thamso_md_by`, data),
  getDsDonViDaGan: (axios, data) => axios.post(`/web-quantri/phanquyenhths/sp_lay_ds_thamso_md_gan_dv`, data),
  capNhat: (axios, data) => axios.post(`/web-quantri/phanquyenhths/sp_ghilai_thamso_md_gan_dv`, data),
  getDmLoaiDonVi: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/ADMIN_LOAI_DVI`)
}
