export default{
  getDanhSachDichVu: (axios, params) => axios.get('/web-tracuu/phattrien/ds_dichvu', {params}),
  getDanhSachLoaiHopDong: (axios, params) => axios.get('/web-tracuu/phattrien/ds_loai_hd', {params}),
  getDanhSachDonVi: (axios, params) => axios.get('/web-tracuu/phattrien/ds_don_vi', {params}),
  traCuuPhatTrien: (axios, data) => axios.post('/web-hopdong/ChuyenDoiLHTBGphone/gphone_capnhat_hoancong', data)
}
