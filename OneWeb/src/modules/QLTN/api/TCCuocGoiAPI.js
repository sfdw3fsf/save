export default {
    getDanhSachHuongDamThoai: (axios) => axios.get("/web-thuno/api/thu-no/common/huong-dam-thoai"),
    getDanhSachDichVuVT: (axios) => axios.get("/web-thuno/api/thu-no/common/dich-vu-vien-thong/1,2,4"),
    getDanhSachLoaiHinhTB: (axios, data) => axios.get("/web-thuno/api/thu-no/common/loai-hinh-thue-bao/dich-vu-vien-thong/"+data),
    getDanhSachCuocGoi: (axios, data) => axios.get("/web-thuno/api/thu-no/common/cuoc-goi",{ params: data }),
    getDanhSachCuocGoiTheoFile: (axios, data) => axios.put("/web-thuno/api/thu-no/tra-cuu/chi-tiet-cuoc-goi-theo-file",data),

};