export default function (apiCaller) {
  return {
    layDsModule: () => apiCaller.get('/web-ecms/danhmuc/module/danhsach'),
    chiTietModule: (id) => apiCaller.get(`/web-ecms/danhmuc/module/danhsachtheoid?id=${id}`),
    themMoi:(data) => apiCaller.post('/web-ecms/danhmuc/module/them', data),
    capNhat:(data) => apiCaller.post('/web-ecms/danhmuc/module/capnhat', data),
    xoa: (module_id) => apiCaller.post(`/web-ecms/danhmuc/module/xoa?module_id=${module_id}`),
  }
}
