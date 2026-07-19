export default {
  getDSLoaiNVThuCuoc: (axios) => axios.get('/web-thuno/api/thu-no/common/ds-loai-nv-thu-cuoc-phat-sinh'),
  getKeHoachThuCuocPS: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ke-hoach-thu-cuoc-phat-sinh', {params: data}),
  xoaGiaoKeHoach: (axios, data) => axios.delete('/web-thuno/api/thu-no/quan-ly-dai-ly/phieu-giao-ke-hoach/ke-hoach/' + data),
  capnhatGiaoPhieu: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/thong-tin-giao-ke-hoach', data),

  /* giao GDV */
  getDSGiaoDichVien: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-giao-dich-vien/don-vi-quan-ly', {params: data}),
  capnhatKeHoachGiaoGDV: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/ke-hoach-thu-cuoc-phat-sinh-nv', data),
  /* chi tiết giao */
  getChiTietKeHoachCPS: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/chi-tiet-ke-hoach-thu-cps/' + data),

  /* gán dịch vụ */

  getKeHoachThuTheoLoaiTB: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-ke-hoach-thu-lhtb/' + data),
  capnhatKTTheoLoaiTB: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-ke-hoach-thu-lhtb', data),

  capnhatCTkeHoachCPS: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-chi-tiet-ke-hoach-cps/' +  data),
}
