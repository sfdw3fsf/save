export default function (apiCaller) {
  return {
    layDsViPhamCSHT: () => apiCaller.get('/web-cabman/quanly/viphamcsht/napDsCsht'),
    layDsDonViXl: () => apiCaller.get('/web-cabman/quanly/viphamcsht/napDonViXuLy'),
    layDsKetQuaXl: () => apiCaller.get('/web-cabman/quanly/viphamcsht/napKetQuaXuLy'),
    layDsDonViViPham: () => apiCaller.get('/web-cabman/quanly/viphamcsht/napDonViViPham'),
    themViPham: (data) => apiCaller.post('/web-cabman/quanly/viphamcsht/themViPhamCsht', data),
    capNhatViPham: (data) => apiCaller.post('/web-cabman/quanly/viphamcsht/capNhatViPhamCsht', data),
    xoaViPham: (id) => apiCaller.get(`/web-cabman/quanly/viphamcsht/XoaViPhamCsht?id=${id}`)
  }
}
