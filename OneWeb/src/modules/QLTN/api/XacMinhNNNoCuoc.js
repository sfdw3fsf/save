export default {
    getDanhMucXacMinh: async (axios) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/danh-muc-xac-minh-no-cuoc-theo-nhat-ky-thu-no'),
    getDSPhieuChoXL: async (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-phieu-xac-minh-cho-xu-ly',{params:data}),
    getDSChiTiet: async (axios, id) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-phieu-xac-minh-da-xu-ly/' + id),
    xacMinhPhieu: async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/giao-phieu-xu-ly-nhat-ky-tn', data),
}
