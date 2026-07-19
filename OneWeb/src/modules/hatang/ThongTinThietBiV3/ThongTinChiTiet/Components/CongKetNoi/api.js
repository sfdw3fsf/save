export default {
  addPort: async (axios, data) => axios.post('/web-ecms/tainguyen-idc/cong-ketnoi-thietbi/them-cong', data),
  getPortList: async (axios, data) => axios.post('/web-ecms/tainguyen-idc/cong-ketnoi-thietbi/danh-sach', data),
  updatePort: async (axios, data) => axios.post('/web-ecms/tainguyen-idc/cong-ketnoi-thietbi/update-cong', data),
  getListThietBi: async (axios, data) =>
    axios.post('/web-ecms/tainguyen-idc/cong-ketnoi-thietbi/ds-thiet-bi-chua-ket-noi', data),
  insertThietBi: async (axios, data) =>
    axios.post('/web-ecms/tainguyen-idc/cong-ketnoi-thietbi/ket-noi-thiet-bi', data),
  thaoGoThietBi: async (axios, data) =>
    axios.post('/web-ecms/tainguyen-idc/cong-ketnoi-thietbi/ngat-ket-noi-thiet-bi', data),
  upSertPort: async (axios, data) => axios.post('/web-ecms/tainguyen-idc/cong-ketnoi-thietbi/them-cong-v2', data)
}
