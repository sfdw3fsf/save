import axios from 'axios'
import { transformKeysToLower } from '@/utils/format'

export default {
  getStatus: () => axios.post('/web-hopdong/CapNhatBHS/capnhat_bohs_danhmuc/capnhat_bohs_trangthai', {}).then(rs => rs.data.data),
  getGroupFile: () => axios.post('/web-hopdong/CapNhatBHS/capnhat_bohs_danhmuc/capnhat_bohs_nhom_file', {}).then(rs => rs.data.data),
  getTypeFile: () => axios.post('/web-hopdong/CapNhatBHS/capnhat_bohs_danhmuc/capnhat_bohs_loai_file', {}).then(rs => rs.data.data),
  getAgency: (p_nguoidung_id) => axios.post('/web-hopdong/CapNhatBHS/sp_lay_ds_nhanvien_qldl', {p_nguoidung_id: p_nguoidung_id}).then(rs => rs.data.data),
  getEmp: (val) => axios.post('/web-hopdong/CapNhatBHS/sp_lay_ds_nhanvien_theo_donvi', { p_donvi_id: val }).then(rs => rs.data.data),
  getReason: () => axios.post('/web-hopdong/CapNhatBHS/capnhat_bohs_danhmuc/capnhat_bohs_lydoton_tc', {}).then(rs => rs.data.data),
  getIcon: () => axios.post('/web-hopdong//CapNhatBHS/capnhat_bohs_img_ico', {}).then(rs => rs.data.data),
  getInfoFileDetail: (p_bohs_id) => axios.post('/web-hopdong/CapNhatBHS/sp_lay_ds_file_hs', {
    "p_bohs_id": p_bohs_id
  }).then(rs => rs.data.data),
  getInfoFiles: (
    p_ma_gd,
    p_phieu_id,
    p_huonggiao_id,
    p_timngay,
    p_tungay,
    p_denngay,
    p_ttph_id,
    p_kieu
  ) => axios.post('/web-hopdong/CapNhatBHS/sp_lay_ds_phieu_phatkh', {
    "p_denngay": p_denngay,
    "p_huonggiao_id": p_huonggiao_id,
    "p_kieu": p_kieu,
    "p_ma_gd": p_ma_gd,
    "p_phieu_id": p_phieu_id,
    "p_timngay": p_timngay,
    "p_ttph_id": p_ttph_id,
    "p_tungay": p_tungay
  }).then(rs => rs.data.data),
  getAssignList: (phieuId) => axios.post('/web-hopdong/CapNhatBHS/sp_ht_ds_giaoviec_bo_hs', { p_phieu_id: phieuId }).then(rs => rs.data.data),
  getAppointmentList: () => axios.post('/web-hopdong/CapNhatBHS/capnhat_bohs_danhmuc/capnhat_bohs_tt_hen_kh', {}).then(rs => rs.data.data),
  confirmFiles: (data) => axios.post('/web-quantri/hoso/xacnhan_bohoso', data).then(rs => rs.data.data),
  checkPermission: () => axios.get('/web-hopdong/CapNhatBHS/capnhat_bohs_tt_loai_nv').then(rs => rs.data.data),
  assignWork: (p_ds, p_nd_giao, p_nhanvien_th_id, p_quyen_dl_id) => axios.post('/web-hopdong/CapNhatBHS/fn_capnhat_bhs_giaoviec', {
    "p_ds": p_ds,
    "p_nd_giao": p_nd_giao,
    "p_nhanvien_th_id": p_nhanvien_th_id,
    "p_quyen_dl_id": p_quyen_dl_id
  }).then(rs => rs.data),
  setAppointment: (
    lydotontc_id,
    p_bohs_id,
    p_nd_hen,
    p_ngayden,
    p_ngaytu,
    p_phieu_id) => axios.post('/web-hopdong/CapNhatBHS/fn_capnhat_bhs_hen_kh', {
    "lydotontc_id": lydotontc_id,
    "p_bohs_id": p_bohs_id,
    "p_nd_hen": p_nd_hen,
    "p_ngayden": p_ngayden,
    "p_ngaytu": p_ngaytu,
    "p_phieu_id": p_phieu_id
  }).then(rs => rs.data),
  layds_bohoso_chuagiao_theo_makh_magd: (data) => axios.post('/web-quantri/hoso/layds_bohoso_chuagiao_theo_makh_magd', data).then(rs => rs.data.data),
  lay_ds_hd_tb_theo_hdkh_id: (vhdkh_id) => axios.post('web-hopdong/hopdong/lay_ds_hd_tb_theo_hdkh_id', {
    vhdkh_id: vhdkh_id
  }).then(rs => rs.data.data.map(r => transformKeysToLower(r))),
  fn_lay_soluong_phieu_bohs: (phieu_id) => axios.post('web-hopdong/giao_cho_qlk/fn_lay_soluong_phieu_bohs', {
    "phieu_id": phieu_id
  }).then(rs => rs.data.data),
  xacnhan_bohoso: (danhsach, huonggiao_id, kieu, noidung, xn_gv) => axios.post('web-quantri/hoso/xacnhan_bohoso', {
    "danhsach": danhsach,
    "huonggiao_id": huonggiao_id,
    "kieu": kieu,
    "noidung": noidung,
    "xn_gv": xn_gv
  }).then(rs => rs.data)
// /web-hopdong/giao_cho_qlk/fn_lay_soluong_phieu_bohs
  // web-hopdong/CapNhatBHS/capnhat_bohs_tt_loai_nv
  // getList: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LINHVUC'),
  // create: (name) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_NHAP?linhVucId=0&linhVuc=${name}`),
  // update: (id, name) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_NHAP?linhVucId=${id}&linhVuc=${name}`),
  // delete: (id) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_XOA?linhVucId=${id}`)
}
