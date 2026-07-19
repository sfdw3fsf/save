export default {
  getDanhSachDichVuVT: (axios, body) => axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/sp_ht_dichvuvt_combobox', body),
  getDanhSachDaiVT: (axios, loaiDVId) => axios.get(`web-tracuu/port/ds_dai_vt?loaiDVId=${loaiDVId}`),
  getDanhSachThueBao: (axios, body) => axios.post('web-quantri/capnhat-diemchia/lay_ds_thuebao_theo_diemchia', body),
  getDanhSachDiemChia: (axios, body) => axios.post('web-ccdv/tien_trinh_bao_hong/sp_ht_donvi_loaidv_nhanvien_combobox', body),
  getKeys: (axios, body) => axios.post('web-quantri/donvitinh/get_keys', body),
  capNhatLogDbtbDv: (axios, body) => axios.post('web-quantri/capnhat-diemchia/capnhat_log_dbtb_dv', body),
  capNhatDiemChia: (axios, body) => axios.post('web-quantri/capnhat-diemchia/fn_capnhat_diemchia_theo_ds', body),
  getLogCapNhatDiemChia: (axios, body) => axios.post('web-quantri/capnhat-diemchia/sp_log_capnhatdiemchia', body),
  getLogBDTBTheoMaThueBao: (axios, maThueBao) => axios.get(`web-quantri/diaban_nvkd/lay_ds_log_bdtb_matb?maTb=${maThueBao}`)
}
