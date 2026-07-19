import axios from 'axios'

export default {
  lay_ds_khuvuc_theo_diemchia: (nhanvienId) => axios.post('/web-quantri/khuvuc_diadanh/lay_ds_khuvuc_theo_diemchia', {"vnhanvien_id": nhanvienId}).then(rs => rs.data.data),
  sp_lay_danh_sach_khu_vuc_cha: () => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_danh_sach_khu_vuc_cha', {}).then(rs => rs.data.data),
  sp_lay_danh_sach_loai_khu_vuc: (khuvucId, kieu) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_danh_sach_loai_khu_vuc', {
    "p_khuvuc_id": khuvucId,
    "p_kieu": kieu
  }).then(rs => rs.data.data),
  lay_ds_quan: () => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN').then(rs => rs.data.data),
  lay_ds_phuong: (quanId) => axios.post('/web-quantri/danhmuc-chung/CSS_PHUONG', { "ID": quanId }).then(rs => rs.data.data),
  lay_ds_phuong_xa: (khuvucId) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_ds_khuvuc_phuongxa', {
    "khuvuc_id": khuvucId
  }).then(rs => rs.data.data),
  sp_lay_ds_pho: (data) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_ds_pho', data).then(rs => rs.data.data),
  xoa_khuvuc: (khuvucId) => axios.post('/web-quantri/khuvuc_diadanh/fn_khuvuc_xoa', { "khuvuc_id": khuvucId }).then(rs => rs.data).then(rs => rs.data)

}
