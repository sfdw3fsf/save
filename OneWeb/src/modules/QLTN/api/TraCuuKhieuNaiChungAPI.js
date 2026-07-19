export default {
    // getDanhSachHuongDamThoai: (axios) => axios.get("/web-thuno/api/thu-no/common/huong-dam-thoai"),
    getDanhSachDichVuVT: (axios) => axios.get("/web-thuno/api/thu-no/common/dich-vu-vien-thong"),
    getDanhSachLoaiHinhTB: (axios, data) => axios.get("/web-thuno/api/thu-no/common/loai-hinh-thue-bao/dich-vu-vien-thong/"+data),
    getThongTinThanhToan: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu-danh-ba/ma-thue-bao",{ params: data }),
    getDanhSachKhieuNaiTB: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/khieu-nai",{ params: data }),
    getDanhSachNVQLDaiLy: (axios, data) => axios.get("/web-thuno/api/thu-no/common/nhan-vien-quan-ly/dai-ly/"+data),
};