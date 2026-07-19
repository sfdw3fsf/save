export default {
  getDanhSachCongViec: (axios, data) =>
    axios.post('/web-ecms/idc/cap-phat-thu-hoi-ip/ket-qua-cap-phat-thu-hoi-ip', {
        donviId: data.donviId == 0 ? null : data.donviId,
        loaiCvId: data.loaiCvId == 0 ? null : data.loaiCvId,
        maThuebao: data.maThuebao ? data.maThuebao.trim() : null,
        ngayYcEnd: data.ngayYcEnd ?? null,
        ngayYcStart: data.ngayYcStart ?? null,
        nhanvienThId: data.nhanvienThId == 0 ? null : data.nhanvienThId,
        tenKh: data.tenKh ? data.tenKh.trim() : null,
        trangthaiId: data.trangthaiId == 0 ? null : data.trangthaiId,
        phanloai: data.phanloai == 0 ? null : data.phanloai,
        khachhang: data.khachhang ? data.khachhang.trim() : null,
        congviec: data.congviec ? data.congviec.trim() : null,
        idcId: data.idcId == 0 ? null : data.idcId,
        ipStoreName: data.ipStoreName ? data.ipStoreName.trim() : null,
        ngayHTStart: data.ngayHTStart ?? null,
        ngayHTEnd: data.ngayHTEnd ?? null,
    }),
    getDanhMucLoaiCongViec: (axios, data) => 
    axios.post("/web-ecms/idc/danh-muc/common", {
        "loaiDanhMuc": data.loaiDanhMuc,
        "thamSo1": data.thamSo1 ?? null,
        "thamSo2": null
    }),
    getDanhMucNhanVien: (axios, data) => 
    axios.post("/web-ecms/idc/danh-muc/common", {
        "loaiDanhMuc": "NHANVIEN-DONVI-IDC",
        "thamSo1": data.donviId,
        "thamSo2": null
    }),
}
