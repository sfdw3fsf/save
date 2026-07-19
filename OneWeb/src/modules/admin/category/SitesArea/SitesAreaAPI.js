import axios from 'axios'

export default {
  lay_ds_khuvuc_theo_diemchia: (nhanvienId) => axios.post('/web-quantri/khuvuc_diadanh/lay_ds_khuvuc_theo_diemchia', {"vnhanvien_id": nhanvienId}).then(rs => rs.data.data),
  sp_lay_danh_sach_khu_vuc_cha: () => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_danh_sach_khu_vuc_cha', {}).then(rs => rs.data.data),
  sp_lay_danh_sach_loai_khu_vuc: (khuvucId, kieu) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_danh_sach_loai_khu_vuc', {
    "p_khuvuc_id": khuvucId,
    "p_kieu": kieu
  }).then(rs => rs.data.data),
  lay_ds_tinh: () => axios.get('/web-quantri/danhmuc-chung/CSS_TINH').then(rs => rs.data.data),
  lay_ds_quan: (tinh_id) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_quan_huyen/${tinh_id}`).then(rs => rs.data.data),
  lay_ds_phuong: (quanId) => axios.post('/web-quantri/danhmuc-chung/CSS_PHUONG', { "ID": quanId }).then(rs => rs.data.data),
  lay_ds_phuong_xa: (khuvucId) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_ds_khuvuc_phuongxa', {
    "khuvuc_id": khuvucId
  }).then(rs => rs.data.data),
  sp_lay_ds_pho: (phuongId, nhomphoId) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_ds_pho', {
    "phuong_id": phuongId,
    "nhom_pho_id": nhomphoId
  }).then(rs => rs.data.data),
  xoa_khuvuc: (khuvucId) => axios.post('/web-quantri/khuvuc_diadanh/fn_khuvuc_xoa', { "khuvuc_id": khuvucId }).then(rs => rs.data),
  fn_khuvuc_dv_them: (data) => axios.post('/web-quantri/khuvuc_diadanh/fn_khuvuc_dv_them', data).then(rs => rs.data),
  get_keys: (keyname) => axios.post(`web-hopdong/khoiphucthanhly/get_keys`, {keyname}).then(rs => rs.data.data),
  sp_khuvuc_cap_nhat: (action, json_khuvuc_data, khuvuc_id, json_khuvuc_lkv_data, json_khuvuc_px_data) => {
    const data = {
      "action": action,
      "json_khuvuc_data": json_khuvuc_data,
      "khuvuc_id": khuvuc_id,
      "json_khuvuc_lkv_data": json_khuvuc_lkv_data,
      "json_khuvuc_px_data": json_khuvuc_px_data
    }
    return axios.post(`web-quantri/khuvuc_diadanh/sp_khuvuc_cap_nhat`, data).then(rs => rs.data).catch(err => {
      let msg = 'Có lổi xảy ra'
      if (err && err.data && err.data.message) {
        msg = err.data.message
      }
      return { error_code: 'BSS-00000500', message: msg, message_detail: err }
    })
  },
  sp_lay_dulieu_theo_dieukien: (p_schema, p_table_name, p_value, p_text, p_where, p_order) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_dulieu_theo_dieukien', {
    "p_schema": p_schema,
    "p_table_name": p_table_name,
    "p_value": p_value,
    "p_text": p_text,
    "p_where": p_where,
    "p_order": p_order
  }).then(rs => rs.data.data),
  Lay_ds_khuvuc_donvi: (kieu, khuvuc_id, loaidv_id) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_danh_sach_donvi_theo_khu_vuc', {
    "kieu": kieu,
    "khuvuc_id": khuvuc_id,
    "loaidv_id": loaidv_id
  }).then(rs => rs.data.data),
  Lay_ds_khuvuc_donvi_tructhuoc: (kieu, khuvuc_id, loaidv_id) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_danh_sach_donvi_theo_khu_vuc_tructhuoc', {
    "kieu": kieu,
    "khuvuc_id": khuvuc_id,
    "loaidv_id": loaidv_id
  }).then(rs => rs.data.data),
  DeleData: (khuvuc_id, loaidv_id, donvi_id) => axios.post('/web-quantri/khuvuc_diadanh/fn_khuvuc_dv_xoa', {
    "donvi_id": donvi_id,
    "khuvuc_id": khuvuc_id,
    "loaidv_id": loaidv_id
  }).then(rs => rs.data.data),
  layTinh_diadanh: () => axios.get('/web-quantri/danhmuc-chung/CSS_TINH').then(rs => rs.data.data),
  Lay_DS_DacDiem: (CODE, ID) => axios.post('/web-quantri/danhmuc-chung/CSS_DACDIEM', {
    "CODE": CODE,
    "ID": ID
  }).then(rs => rs.data.data),
  Lay_DS_KhuVuc_PX: (p_phuong_id, p_pho_id, p_ap_id, p_khu_id, p_dac_diem_id) => axios.post('/web-quantri/khuvuc_diadanh/sp_lay_ds_khuvuc_px', {
    "p_phuong_id": p_phuong_id,
    "p_pho_id": p_pho_id,
    "p_ap_id": p_ap_id,
    "p_khu_id": p_khu_id,
    "p_dac_diem_id": p_dac_diem_id
  }).then(rs => rs.data.data)
}
