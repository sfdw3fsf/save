export default {
    danhSachKenhBan: async (axios) => axios.post('web-khdn/kenhban/danhSachKenhBan',{}),
    dsUserGach: async (axios) => axios.post('web-khdn/kenhban/dsUserGach',{}),
    dsPhieuHuyKenhBan: async (axios, data) => axios.post('web-khdn/kenhban/dsPhieuHuyKenhBan',data),
}