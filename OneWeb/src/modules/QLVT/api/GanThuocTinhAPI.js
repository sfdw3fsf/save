export default {    
    getDSThuocTinh: (axios,data) => axios.get(`/web-qlvt/api/gan-thuoc-tinh-ccdc/thuoc-tinh-vat-tu?vattu-id=${data.vatTuId}&thietbi-id=${data.thietBiId}`),
    capNhatThuocTinh: (axios,data) => axios.post(`/web-qlvt/api/gan-thuoc-tinh-ccdc/xac-nhan-gan-thuoc-tinh`,data),
}