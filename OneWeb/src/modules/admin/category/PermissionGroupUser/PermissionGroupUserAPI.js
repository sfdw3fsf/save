import axios from 'axios'
export default {
  lay_danhsach_nguoidung: async (axios) => axios.post('/web-quantri/ganquyennd/fn_lay_ds_nguoidung', {}),
  sp_lay_ds_da_chua_gan_quyen_nd: async (axios, nguoidung_id) => axios.post('/web-quantri/ganquyennd/sp_lay_ds_da_chua_gan_quyen_nd', {
    nguoidung_id: nguoidung_id
  }),
  sp_ins_gan_quyen_nd: async (axios, data) => axios.post('/web-quantri/ganquyennd/sp_ins_gan_quyen_nd', {
    data: data
  }),
  sp_del_gan_quyen_nd: async (axios, data) => axios.post('/web-quantri/ganquyennd/sp_del_gan_quyen_nd', {
    data: data
  }),

  CAPNHAT_CTS_DUYETTUDONG_SMCA: async (data) => axios.post('/web-thicong/dvcntt/capnhat_cts_duyettudong_smca', data)
}
