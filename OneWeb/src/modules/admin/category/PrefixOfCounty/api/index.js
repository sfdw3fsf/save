export default {
  layDanhSachLoaiSo: (axios, data) => axios.get('/web-quantri/danhmuc/dauso/loai_phuongxa'),
  layDanhSachQuanHuyen: (axios, data) => axios.get('/web-quantri/danhmuc/diaban/quan'),
  layDanhSachCacDonVi: (axios, data) => axios.post('/web-quantri/danhmuc/donvi/theo_loai', data),
  layDanhSachTongDai: (axios, data) => axios.post('/web-quantri/danhmuc/donvi/theo_loai', data),
  layDanhSachPrefixChuaChon: (axios, data) => axios.post('/web-quantri/dauso/ds_theo_tongdai_va_diaban', data),
  layDanhSachPrefixDaChon: (axios, data) => axios.post('/web-quantri/dauso/ds_theo_tongdai_va_diaban', data),
  layDanhSachPhuongXa: (axios, data) => axios.post('/web-quantri/danhmuc/diaban/phuong', data),
  layDanhSachDichVu: (axios, data) => axios.post('/web-quantri/danhmuc/ds_dichvu_vienthong_theo_ids', data),
  xoaDauSoPhuongXa: (axios, data) => axios.post('/web-quantri/dauso/xoa', data),
  thenDauSoPhuongXa: (axios, data) => axios.post('/web-quantri/dauso/them', data),
  layDanhSachDaiCoDinh: (axios, data) => axios.post('/web-quantri/dauso/layds_dai_so_ims', data),
  taoDaiSo: (axios, data) => axios.post('/web-quantri/dauso/tao_dai_so', data)

}
