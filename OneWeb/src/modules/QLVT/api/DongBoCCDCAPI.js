export default {
    getDSThietBiCCDC: (axios, chungTuId) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/lay-ds-thietbi-ccdc/${chungTuId}`),
    kiemTraDongBoCCDC: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-dong-bo-ccdc/${data.chungTuId}/${data.kieu}`),
    kiemTraCapNhapQLTS: (axios, chungTuId) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-cap-nhap-qlts/${chungTuId}`),
    getEmailNguoiDung: (axios, ) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/get-email-nguoi-dung`),
    capNhatThietBiQLTS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-thiet-bi-qlts`,data),
    dongBoCCDC: (axios, data) => axios.post(`/web-hopdong/qlts/gui_ccdc_qlts`,data),
}