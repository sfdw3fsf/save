export default {
  sp_lay_ds_makhotd: async (axios, data) => axios.get(`/web-qlvt/api/nhapxuatvt_v2/sp_lay_ds_makhotd`),
  ds_loai_kho_td: (axios, data) =>  axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-loai-kho-td/${data.mucdich_id}`),
}
    