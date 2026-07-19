import axios from 'axios'

export default {
  // UR2.4.081_001
  getRequestTypeList: () => axios.post(`/web-quantri/khoamodichvu/ht_combo_kieuyc`, {}),
  // UR2.4.081_002
  getEmployeeList: (data) => axios.post(`/web-quantri/khoamodichvu/sp_lay_ds_nhanvien_theo_donvi`, data),
  // UR2.4.081_003
  getServiceList: (data) => axios.post('/web-quantri/khoamodichvu/sp_lay_dichvuvt_theo_ds_id', data),
  // UR2.4.081_004
  getProcessList: (data) => axios.post(`/web-quantri/khoamodichvu/sp_lay_ds_quytrinh_theo_loaihd_dichvu`, data),
  // UR2.4.081_005
  getContractList: (data) => axios.post(`/web-quantri/khoamodichvu/lay_ds_hdtb_khoamomay_hc`, data),
  // UR2.4.081_015
  addDataAsRequire: (data) => axios.post(`/web-quantri/khoamodichvu/them_dulieu_khoamay_theo_yc`, data),
  getLoaiHinh: (idQuyTrinh) => axios.get(`/web-hopdong/danhmuc/lay_ds_loaihinh_tb_theo_quytrinh/${idQuyTrinh}`)
}
