export default function (apiCaller) {
  return {
    layDsBras: () => apiCaller.get('/web-ecms/danhmuc/quanlybras/danhsach'),
    chiTietBras: (id) => apiCaller.get(`/web-ecms/danhmuc/quanlybras/lay_theokey?id=${id}`),
     themMoi:(data) => apiCaller.post('/web-ecms/danhmuc/quanlybras/them', data),
     capNhat:(data) => apiCaller.post('/web-ecms/danhmuc/quanlybras/capnhat', data),
     xoa: (bras_id) => apiCaller.post(`/web-ecms/danhmuc/quanlybras/xoa?bras_id=${bras_id}`),
  }
}
