export default {
    getDSThuocTinhLoaiVT: (axios, data) => axios.get(`/web-qlvt/api/gan-thuoc-tinh-ccdc/thuoc-tinh-vat-tu?vattu-id=${data.vattu_id}&thietbi-id=${data.thietbi_id}`),
    XacNhanGanThuocTinh: (axios, data) => axios.post(`/web-qlvt/api/gan-thuoc-tinh-ccdc/xac-nhan-gan-thuoc-tinh`,data),
    
}