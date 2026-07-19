export default {
    getDSTTDongBo: (axios, data) => axios.post('/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-trang-thai-dong-bo-phieu',data),
    kiemTraTrangThaiIMS: (axios, data) => axios.post('/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-trang-thai-ims',data),
    getSoIDFMIS: (axios, data) => axios.post('/tichhop/fmis/fs_ctvt_nh_trang_thai',data),
}