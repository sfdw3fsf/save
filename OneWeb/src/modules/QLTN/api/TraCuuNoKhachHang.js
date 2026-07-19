export default {
  getDanhSachThongTinNo: (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-thong-tin-no', {params: data}),
}
