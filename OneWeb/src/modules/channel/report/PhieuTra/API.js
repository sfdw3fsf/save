export default {
    dsHinhThucTT: async (axios) => axios.post('web-khdn/kenhban/dsHinhThucTT',{}),

    danhSachKenhBan: async (axios) => axios.post('web-khdn/kenhban/dsKenhBan',{}),
    dsUserGach: async (axios) => axios.post('web-khdn/kenhban/dsUserGach',{}),

    dsPhieuTraKenhBan: async (axios, data) => axios.post('web-khdn/kenhban/dsPhieuTraKenhBan',data),

    xoaPhieuTraKenhBan: async (axios, data) => axios.post('web-khdn/kenhban/xoaPhieuTraKenhBan',data),
    
    
}