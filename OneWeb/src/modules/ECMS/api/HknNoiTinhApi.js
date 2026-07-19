export default function (apiCaller) {
  return {
    layDsHuongKetNoi: (data) => apiCaller.post('/web-ecms/danhmuc/huongketnoi/danhsach',data),
    layDanhMucHuongKetNoi: () => apiCaller.get('/web-ecms/danhmuc/huongketnoi/laydanhmuc'),
    chiTietHuongKetNoi: (id) => apiCaller.get(`/web-ecms/danhmuc/huongketnoi/chitiet/${id}`),
    themMoi:(data) => apiCaller.post('/web-ecms/danhmuc/huongketnoi/them', data),
    capNhat:(data) => apiCaller.post('/web-ecms/danhmuc/huongketnoi/capnhat', data),
    xoa: (hkn_id) => apiCaller.post(`/web-ecms/danhmuc/huongketnoi/xoa/${hkn_id}`),
  }
}
