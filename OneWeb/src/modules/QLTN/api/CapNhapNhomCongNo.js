import { axios } from "axios"

export default {
    getDanhSachNhomCongNo: (axios) => axios.get('/web-thuno/api/quan-ly-thu-no/nhom-cong-no'),
    themMoiNhomCongNo: (axios, data) => axios.post('/web-thuno/api/quan-ly-thu-no/nhom-cong-no/' + data),
    capNhapNhomCongNo: (axios, data) => axios.put('/web-thuno/api/quan-ly-thu-no/nhom-cong-no', data),
    xoaNhomCongNo: (axios, data) => axios.delete('/web-thuno/api/quan-ly-thu-no/nhom-cong-no/' + data),
}