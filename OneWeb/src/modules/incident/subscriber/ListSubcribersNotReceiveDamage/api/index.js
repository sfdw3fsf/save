export default {
  getKyHoaDon: (axios, params) => axios.get('web-quantri/thamso-quanly-thuno/laythangno', {params}),
  getDsChuKyNo: (axios, data) => axios.post('web-ccdv/ds_tb_khongnhan_hong/sp_lay_ds_chukyno_theo_kyhoadon', data),
  getThamSoMd: (axios, data) => axios.post('web-hopdong/thanhly/sp_lay_ds_thamso_md', data),
  getInforSubcribers: (axios, data) => axios.post('web-ccdv/ds_tb_khongnhan_hong/lay_ds_khong_khoamo', data),
  getGridSubcribers: (axios, data) => axios.post('web-ccdv/ds_tb_khongnhan_hong/lay_tt_no_thuebao', data),
  getSubcriberByCode: (axios, data) => axios.post('web-ccdv/ds_tb_khongnhan_hong/lay_tt_thuebao_theo_ma_tb', data),
  saveData: (axios, data) => axios.post('web-ccdv/ds_tb_khongnhan_hong/sp_update_ds_khongxuly', data),
  insertData: (axios, data) => axios.post('web-ccdv/ds_tb_khongnhan_hong/sp_insert_ds_khongxuly', data),
  deleteSubcriber: (axios, data) => axios.post('web-ccdv/ds_tb_khongnhan_hong/sp_del_ds_khongxuly', data),
  getKey: (axios, data) => axios.post('web-hopdong/khoiphucthanhly/get_keys', data)
}
