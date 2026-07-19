export default {
    getList: (axios) => axios.get("/web-ecms/idc/ocam-tren-thanhnguon"),
    update: (axios, data) =>
        axios.post("/web-ecms/idc/ocam-tren-thanhnguon", {
        ocamId: data.ocamId,
        // thanhNguonId: data.thanhNguonId,
        // ten: data.ten,
        // moTa: data.moTa,
        trangThaiId: data.trangThaiId,
        trangThaiHdId: data.trangThaiHdId,
        // thietBiId: data.thietBiId,
        ghiChu: data.ghiChu,
        // suDung: data.suDung ? 1 : 0,
        // loaiOcamId: data.loaiOcamId,
    }),
    getListThanhNguon: (axios, data) =>
        axios.post("/web-ecms/idc/danh-muc/common", {
        "loaiDanhMuc": "IDC-THANHNGUON",
        "thamSo1": null,
        "thamSo2": null
    }),
}
