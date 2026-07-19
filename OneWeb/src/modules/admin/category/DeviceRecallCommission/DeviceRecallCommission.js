import axios from 'axios'

export default {
  get_keys: (keyname) => axios.post(`web-hopdong/khoiphucthanhly/get_keys`, {keyname}).then(rs => rs.data.data),
  getList: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LINHVUC'),
  create: (name) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_NHAP?linhVucId=0&linhVuc=${name}`),
  update: (id, name) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_NHAP?linhVucId=${id}&linhVuc=${name}`),
  delete: (id) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_XOA?linhVucId=${id}`),
  getListDeviceGroup: (data) => axios.post('/web-quantri/thulao_thuhoi_thietbi/sp_get_ds_nhomthietbi', data),
  getListDeviceType: (data) => axios.post('/web-quantri/thulao_thuhoi_thietbi/sp_get_ds_loaitbi_theo_nhomtbi', data),
  getListCondition: (data) => axios.post('/web-quantri/thulao_thuhoi_thietbi/sp_get_ds_cbb_dieukien', data),
  getListDeviceRecallCommission: (data) => axios.post('/web-quantri/thulao_thuhoi_thietbi/get_ds_thulao_thietbi', data),
  getListInvoicePeriod: (data) => axios.post('/web-quantri/thulao_thuhoi_thietbi/get_ds_kyhoadon_tbi', data),
  getListEmployeeType: (data) => axios.post('/web-quantri/thulao_thuhoi_thietbi/get_lst_loainhanvien_tbi', data),
  getListArea: (data) => axios.post('/web-quantri/thulao_thuhoi_thietbi/get_lst_khuvuc_tbi', data),
  updateDeviceRecall: (data) => {
    return axios.post('/web-quantri/thulao_thuhoi_thietbi/capnhat_ghilai_thulao_tbi', data)
  },
  layNhomTbTheoLoaiTb: (loaiTbId) => axios.post('/web-quantri/lay-dulieu/sp_tt_loai_tbi', {
    "param": loaiTbId,
    "type": 1
  }).then(rs => rs.data),
  deleteDeviceRecall: (id) => axios.post('/web-quantri/thulao_thuhoi_thietbi/del_thulao_tbi', {
    'thulao_tbi_id': id
  }).then(rs => rs.data)
}
