export default {
  getDSQuyTrinh: (axios,data) => axios.post('/web-thicong/quanlydonhang/sp_lay_quytrinh_theo_loaihd',data),
  getDSLuong: (axios,data) => axios.post('/web-thicong/quanlydonhang/sp_lay_ds_luong_theo_quytrinh',data),
  getDSLoaiKH: (axios,data) => axios.post('/web-thicong/quanlydonhang/sp_lay_ds_loai_kh',data),
  getDSNguonDon: (axios,data) => axios.post('/web-thicong/quanlydonhang/sp_lay_ds_nguon_don',data),
  getDSDichVuVT: (axios,data) => axios.get('/web-thicong/quanlydonhang/dsDichVu',data),
  getDSLoaiHinh : (axios,data) => axios.get(`web-thicong/quanlydonhang/dsLoaiHinh?dichvuvt_id=${data}`),
  getDSHinhThuc: (axios,data) => axios.post('/web-thicong/quanlydonhang/sp_lay_ds_hinhthuc_dh',data),
  getDSDonVi: (axios,data) => axios.post('/web-thicong/quanlydonhang/sp_lay_ds_donvi_donhang',data),
  updateCauHinh: (axios,data) => axios.post('/web-thicong/quanlydonhang/fn_capnhat_cauhinh_phangiao',data),

}