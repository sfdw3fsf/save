export default{
  getDSThueBaoKhongKhoaMo: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/thue-bao-khong-khao-sat/khong-khoa-mo?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
  getDSThongTinThueBaoNo: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/thue-bao-khong-khao-sat/thue-bao-no?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
  getThongTinThueBaoTheoMa: (axios, data) => axios.get('/web-cskh/api/thue-bao-khong-khao-sat/khong-khoa-mo?maThueBao=' + data),
  getDichVuVT: (axios) => axios.get('/web-cskh/api/danh-muc/dich-vu-vt' ),
  getDSThamSo: (axios, data) => {
    return [
      {
        ma_ts: 'DOCFILE_DSTB_KHONGNHANHONG',
        ten_ts: '1'
      }
    ]
  },
  getRDS_ID: (axios, data) => {
    return [
      {
        RDS_ID: 12345
      }
    ]
  },
  uploadFile: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/doc-file', data),
  getDSMaTTLoi: (axios, data) => axios.get('/web-cskh/api/thue-bao-khong-khao-sat/kiem-tra-up-file', {params: data}),
  getDsMaTTXoaLoi: (axios, data) =>  axios.get('/web-cskh/api/thue-bao-khong-khao-sat/kiem-tra-file-xoa', {params: data}),
  xoaThueBaoKhongKhoaMo: (axios, data) => axios.delete('/web-cskh/api/thue-bao-khong-khao-sat', {data: data}),
  xoaThueBaoKhongKhoaMoTheoFile: (axios, data) => axios.delete('/web-cskh/api/thue-bao-khong-khao-sat/xoa-file', {data: data}),
  capNhapThueBaoKhongKhoaMo: (axios, data) => axios.post('/web-cskh/api/thue-bao-khong-khao-sat', data),
  test: () => console.log('call test api')
}
  