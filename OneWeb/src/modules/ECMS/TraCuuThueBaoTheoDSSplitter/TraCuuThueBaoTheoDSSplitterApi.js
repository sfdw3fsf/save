export default function (apiCaller) {
  return {
    getDsFilemauApi: () => apiCaller.get('/web-ecms/tra-cuu/tb-splitter/lay-dl-file-mau'),
    tracuuThueBaoApi: (data) => apiCaller.post('/web-ecms/tra-cuu/tb-splitter/lay-tt-thue-bao', data)
  }
}
