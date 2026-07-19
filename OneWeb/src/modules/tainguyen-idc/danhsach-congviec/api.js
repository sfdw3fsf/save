export default {
    getDanhSachCongViec: (axios, data) => 
    axios.post("/web-ecms/idc/danhsach-congviec", {
        "loaicvId": data.loaicvId,
        "donviId": data.donviId,
        "idcId": data.idcId,
        "nhanvienId": data.nhanvienId,
        "phanloai": data.phanloai,
        "ycBatdau": data.ycBatdau,
        "ycKetthuc": data.ycKetthuc,
        "ychtBatdau": data.ychtBatdau,
        "ychtKetthuc": data.ychtKetthuc,
        "khachhang": data.khachhang,
        "congviec": data.congviec
    }),

    getDanhMucLoaiCongViec: (axios, data) => 
    axios.post("/web-ecms/idc/danh-muc/common", {
        // IDC-LOAICV, IDC-DONVI
        "loaiDanhMuc": data.loaiDanhMuc,
        "thamSo1": data.thamSo1 ?? null,
        "thamSo2": null
    }),

    getDanhMucIDC: (axios, data) => 
    axios.post("/web-ecms/idc/danh-muc/common", {
        "loaiDanhMuc": "DANHMUC_IDC",
        "thamSo1": data.donviId,
        "thamSo2": null
    }),

    getDanhMucNhanVien: (axios, data) => 
    axios.post("/web-ecms/idc/danh-muc/common", {
        "loaiDanhMuc": "NHANVIEN-DONVI-IDC",
        "thamSo1": data.donviId,
        "thamSo2": null
    }),
}
