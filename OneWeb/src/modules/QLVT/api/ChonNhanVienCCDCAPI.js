export default {
    getDanhMucDonVi: (axios, data) => axios.get(`/web-qlvt/api/chon-nhanvien-ccdc/ds-donvi-ccdc/${data.nhanVienId}`, ),
    getDSTinhTrangTBi: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/tinh-trang-thiet-bi`),
    getThongTinDonVi: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/don-vi-theo-ma-dich-vu?ma-dv=${data.ma_dv}`,),
    getThongTinNhanVien: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/nhan-vien-theo-ma-nhan-vien?ma-nv=${data.ma_nv}`,),
    getThongTinVatTu: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/vat-tu-theo-ma-vat-tu?ma-vt=${data.ma_vt}&type=${data.type}`),
    
    getDSNhanVien: (axios, data) => axios.get(`/web-qlvt/api/chon-nhanvien-ccdc/ds-nhanvien-ccdc`, ),
    getDSDonViV2: (axios, data) => axios.get(`/web-qlvt/api/chon-nhanvien-ccdc/ds-donvi-ccdc-v2/${data.donViId}`, ),
    getDSDonViV3: (axios, data) => axios.get(`/web-qlvt/api/chon-nhanvien-ccdc/ds-donvi-ccdc-v3/${data.donViId}`, ),
    KiemTraToTruong: (axios, data) => axios.get(`/web-qlvt/api/chon-nhanvien-ccdc/kiem-tra-to-truong/${data.donViId}`, ),
}

