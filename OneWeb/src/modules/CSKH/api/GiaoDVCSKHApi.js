export default{
  getDsLoaiDonVi: (axios) => axios.get('/web-cskh/api/danh-muc/combo-loai-don-vi'),
  getDsChuongTrinh: (axios, data) => axios.get('/web-cskh/api/gan-nhan-vien-cs-khl/danh-sach-ct', {params: data}),
  getDsDaGanChuaGan: (axios, data) => axios.post(`/web-cskh/api/giao-don-vi-cskh/danh-sach-dv?pageNo=${data.pageNo}&pageSize=${data.pageSize}`, data),
  ganDVCSKH: (axios, data) => axios.post('/web-cskh/api/giao-don-vi-cskh/tao', data),
}
