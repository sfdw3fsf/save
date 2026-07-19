export default {
    ds_chung_tu_v3: (axios, data) => axios.post("/web-qlvt/api/nhap-dieu-chinh/ds-chung-tu-v3", data),
    lay_ds_vattu_chungtu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${data.chungtu_id}`),
    tinh_trang_thiet_bi: (axios) => axios.get("/web-qlvt/api/cap-phat-ccdc/tinh-trang-thiet-bi"),
    // goChungTu: (axios, chungTuId) => axios.post(`/web-qlvt/api/nhap-dieu-chinh/go-chung-tu?chungTuGanId=${chungTuId}`),
    // layNgayCT: (axios, chungTuId) => axios.get(`/web-qlvt/api/cap-phat-ccdc/lay-ngay-ct/${chungTuId}`),
    // capNhatChungTuCha: (axios, data) => axios.post(`/web-qlvt/api/nhap-dieu-chinh/cap-nhat-chung-tu-cha`, data ),
    // getDSVatTuTheoMa: (axios, maVT) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-vat-tu-theo-ma?maVt=${maVT}`),
    kiem_tra_file_dieu_chinh: (axios, data) => axios.post(`/web-qlvt/api/nhap-dieu-chinh/kiem-tra-file-dieu-chinh`, data.chungtu_id),
    ds_kho_theo_nhanvien: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${data.nhanVienId}/${data.donViId}`,),
    tao_chung_tu_dieu_chinh_psis: (axios, data) => axios.post(`/web-qlvt/api/giao-phieu-bao-hanh/tao-chung-tu-dieu-chinh-psis`, data),
    sp_lay_ds_chungtu_frmnhapdieuchinh: (axios, data) => axios.post(`/web-qlvt/api/nhapxuatvt_v2/sp_lay_ds_chungtu_frmnhapdieuchinh`, data),
    fn_xacnhan_frmnhapdieuchinh_tag: (axios, data) => axios.post(`/web-qlvt/api/nhapxuatvt_v2/fn_xacnhan_frmnhapdieuchinh_tag`, data),
    ds_cong_trinh: async (axios, data) => axios.post(`/web-qlvt/api/dang-ky-cap-phat/ds-cong-trinh`, data),
    sp_getdanhmucvt_dvt_all: async (axios) => axios.get('/web-ccdv/khaibaotsl/sp_getdanhmucvt_dvt_all'),
    sp_kho_giao_psis: async (axios, data) => axios.post(`web-qlvt/api/nhapxuatvt_v2/sp_kho_giao_psis`, data),
    kt_dieuchinh_vt_psis: async (axios, data) => axios.post(`web-qlvt/api/nhapxuatvt_v2/kt_dieuchinh_vt_psis`, data)
};
