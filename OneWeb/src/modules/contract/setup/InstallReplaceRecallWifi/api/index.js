export default {
  getServices: (axios, params) => axios.get('web-quantri/danhmuc-chung/CSS_DICHVU_VT', { params }),
  getSubcriberTypes: (axios, params) => axios.get('web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', { params }),
  getProcedures: (axios, data) => axios.post('web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2', data),
  getDirectionList: (axios, params) => axios.get(`web-hopdong/danhmuc/ds_huonggiao/${params.quytrinh_id}/${params.tthd_id}`),
  getPersonGiveWork: (axios, param) => axios.get(`web-hopdong/danhmuc/lay_ds_nhanvien_thicong/0`),
  getChangeWireSubcribers: (axios, data) => axios.post('web-thicong/caidatwifi_thaythethuhoivt/lay_ds_phieu_hc_thayday', data),
  getEmployees: (axios, data) => axios.post('web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id', data),
  haveToDo: (axios, data) => axios.post('web-thicong/hoinghi_truyenhinh/phailam', data),
  update: (axios, data) => axios.post('web-thicong/caidatwifi_thaythethuhoivt/fn_frmhoancongthayday_capnhat', data),
  getDataControls: (axios, data) => axios.post('web-hopdong/hths-thaydoi-tocdo/sp_lay_thaotac', data),
  complete: (axios, data) => axios.post('web-thicong/caidatwifi_thaythethuhoivt/fn_frmhoancongthayday_hoancong', data),
  lay_phuluc_vattu_capmienphi: async (axios, data) => axios.post('/web-ccdv/hoan-cong-ban-thiet-bi/lay_phuluc_vattu_capmienphi', data),
}
