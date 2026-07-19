export default{
    getDsNVTaoNhomKH: (axios, data) => axios.get('/web-cskh/api/nhom-in-tong-hop/nhan-vien', {params: data}),
    getDsNhomKHLon: (axios, data) => axios.get('/web-cskh/api/nhom-in-tong-hop/nhom-khach-hang-lon', {params: data}),
    getDsThanhToanThuocNhom: (axios, data) => axios.get('/web-cskh/api/nhom-in-tong-hop/thanh-toan-thuoc-nhom', {params: data}),
    getDSThanhToanKoThuocNhom: (axios, data) => axios.get('/web-cskh/api/nhom-in-tong-hop/thanh-toan-khong-thuoc-nhom', {params: data}),
    
    themMaThanhToanVaoNhomKH: (axios, data) => axios.post('/web-cskh/api/nhom-in-tong-hop/them-mtt', data),
    xoaMaThanhToanKhoiNhomKH: (axios, data) => axios.delete('/web-cskh/api/nhom-in-tong-hop/xoa-ma-tt-khoi-nhom', {params: data}),
    capNhapNhom: (axios, data) => axios.post('/web-cskh/api/nhom-in-tong-hop/cap-nhat-nhom', data),
    xoaNhom: (axios, data) => axios.delete('/web-cskh/api/nhom-in-tong-hop/xoa-nhom', {params: data}),
  }
  