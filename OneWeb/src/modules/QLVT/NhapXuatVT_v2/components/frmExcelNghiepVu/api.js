export default {
  danh_muc_loai_chung_tu: async (axios, data) => axios.get(`/web-qlvt/api/chon-chung-tu-ccdc/danh-muc-loai-chung-tu`),
  ds_muc_dich_nhap_xuat: async (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-muc-dich-nhap-xuat/${data.nhommd_id}`),
  ds_nghiep_vu: async (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-nghiep-vu`, data),
  lay_ds_kieu_phieu: async (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-kieu-phieu/${data.loaict_id}`),
  sinh_chungtu_xuatims: async (axios, data) => axios.post(`/web-qlvt/api/nhapxuatvt_v2/sinh_chungtu_xuatims`, data),
  lay_tt_dongbo_hdms_ims: async (axios, data) => axios.post(`/web-qlvt/api/nhapxuatvt_v2/lay_tt_dongbo_hdms_ims`, data),
  ds_tinh: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-tinh`),
  ds_ref_ims: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ref-ims`,data),
}
