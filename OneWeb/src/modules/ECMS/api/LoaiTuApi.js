
export default{
    lietKeLoaiTu: (axios) => axios.get('/web-ecms/danhmuc/loaitu/lietke_danhsach'),
    xoaLoaiTu: (axios, data) => axios.post(`/web-ecms/danhmuc/loaitu/xoa/${data}`),
    themLoaiTu: (axios, data) => axios.post('/web-ecms/danhmuc/loaitu/them', data)
}