export default {
    getDSQuyenNguoiDung: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${data.nguoiDungId}`, ),
    getDSLoaiChungTu: (axios, data) => axios.get(`/web-qlvt/api/chon-chung-tu-ccdc/danh-muc-loai-chung-tu`),
    getDSChungTuCCDC: (axios, data) => axios.post(`/web-qlvt/api/chon-chung-tu-ccdc/danh-sach-loai-chung-tu?tag=${data.tag}`,data.params),
    getDSChungTuXBH: (axios, data) => axios.post(`/web-qlvt/api/chon-chung-tu-ccdc/danh-sach-loai-chung-tu?tag=${data.tag}`,data.params),
    getDSKho: (axios, data) => axios.get(`/web-qlvt/api/xu-ly-thiet-bi-bao-hanh/kho-nha-cung-cap?loaikho-id=${data.loaikho_id}&nhanvien-id=${data.nhanvien_id}`),
    
    getDSThietBi: (axios, data) => axios.get(`/web-qlvt/api/chon-chung-tu-ccdc/thiet-bi-chung-tu?chungtu-id=${data.chungtu_id}`, ),
    getDSVatTu: (axios, data) => axios.get(`/web-qlvt/api/traCuuChungTu/lay-ds-vattu-chungtu/26/${data.chungTuId}`, ),
    getDanhMucLoaiChungTu: (axios, data) => axios.get(`/web-qlvt/api/chon-chung-tu-ccdc/danh-muc-loai-chung-tu-v2`, ),
}
