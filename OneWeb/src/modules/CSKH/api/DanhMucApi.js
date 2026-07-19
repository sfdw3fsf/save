export default {
    getCayDonVi: (axios, data) => axios.get('/web-cskh/api/don-vi'),
    getThamSoMacDinh: (axios, data) => axios.get('/web-cskh/api/danh-muc/tham-so-mac-dinh', {params: data}),
    xoaSoThich: (axios, data) => axios.delete('/web-cskh/api/danh-muc/so-thich/' + data),
    xoaDoiTuong: (axios, data) => axios.delete('/web-cskh/api/danh-muc/doi-tuong-cskh/' + data),
    xoaCSKH: (axios, data) => axios.delete('/web-cskh/api/cham-soc-khach-hang/' + data),
}