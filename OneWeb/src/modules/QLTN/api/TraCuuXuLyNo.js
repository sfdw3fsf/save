export default {
  getDanhSachXuLyNo: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-no/ds-xu-ly-no', {params: data}),
}
