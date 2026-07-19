export default function (apiCaller) {
  return {
    layDsRack: () => apiCaller.get('/web-ecms/danhmuc/rack/danh-sach'),
    capNhatApi: (data) => apiCaller.post('/web-ecms/danhmuc/rack/cap-nhat', data),
    themMoiApi: (data) => apiCaller.post('/web-ecms/danhmuc/rack/them', data),
    xoaApi: (data) => apiCaller.post(`/web-ecms/danhmuc/rack/xoa/${data}`)
  }
}
