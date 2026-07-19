export default {
  getDSTacNghiep: (axios) => axios.get('/web-thuno/api/thu-no/common/ds-tac-nghiep'),
  capnhatGiaoPhieu: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-ket-qua-giao-phieu-thu-no', data)
}
