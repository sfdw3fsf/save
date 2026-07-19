export default {
    // Lấy danh sách thanh nguồn trên rack
    getDanhSachThanhNguonTrenRack: (axios) => axios.get("/web-ecms/idc/thanhnguon-trenrack"),

    // Cập nhật thanh nguồn trên rack
    capNhatThanhNguonTrenRack: (axios, data) => 
        axios.post("/web-ecms/idc/thanhnguon-trenrack", {
        thanhNguonId: data.thanhNguonId == 0 ? null : data.thanhNguonId,
        loaiTnId: data.loaiTnId,
        rackId: data.rackId,
        ten: data.ten,
        moTa: data.moTa,
        trangThaiId: data.trangThaiId,
        viTriLd: data.viTriLd,
        kieuLd: data.kieuLd,
        ghiChu: data.ghiChu,
        suDung: data.suDung ? 1 : 0,
    }),

    // Xóa thanh nguồn trên rack
    xoaThanhNguonTrenRack: (axios, id) => 
        axios.post(`/web-ecms/idc/thanhnguon-trenrack/delete/${id}`),

    // Bulk create thanh nguồn trên rack
    bulkCreateThanhNguonTrenRack: (axios, data) => 
        axios.post("/web-ecms/idc/thanhnguon-trenrack/batch-insert", {
        danhSachThanhNguon: data
    }),

    // Kiểm tra có cho phép chỉnh sửa loại thanh nguồn thuộc thanh nguồn đã tồn tại không
    checkEditLoaiTN: (axios, thanhNguonId) => 
        axios.get(`/web-ecms/idc/thanhnguon-trenrack/check-edit-loai-tn/${thanhNguonId}`),

    // Lấy danh sách ổ cắm theo thanh nguồn ID cho Popup info (khi click vào btn hoạt động ở data grid thì show popup info)
    getOCamInfo: (axios, thanhNguonId) => 
        axios.get(`/web-ecms/idc/thanhnguon-trenrack/ocam-info/${thanhNguonId}`),

    // Lấy danh sách loại thanh nguồn
    getDanhSachLoaiThanhNguon: (axios) => axios.get("/web-ecms/idc/loai-thanh-nguon"),

    // Lấy danh sách rack
    getDanhSachRack: (axios) => axios.post("/web-ecms/thongtin-rack/get-tt-rack"),

    // Lấy danh mục U theo rackId
    getDanhMucU: (axios, data) => 
        axios.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
        loaiDanhMuc: data.loaiDanhMuc,
        thamSo1: data.thamSo1
    }),

    // Validate batch thanh nguồn trên rack
    validateBatchThanhNguonTrenRack: (axios, data) => 
        axios.post("/web-ecms/idc/thanhnguon-trenrack/validate-batch", {
        danhSachThanhNguon: data
    }),
}
