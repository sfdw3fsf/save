export default {
    getDSDichVuVTGP: async (axios, data) =>
        axios.get("/web-cskh/api/giao-phieu-ks/ds-dich-vu-vt-gpks"),
    getDSDVVT: async (axios, data) =>
        axios.get("/web-cskh/api/danh-muc/dich-vu-vt"),
    getOptionSearchs: async (axios, data) =>
        axios.get("/web-cskh/api/giao-phieu-ks/ds-dieu-kien-tim"),
    getDSDonViVT: async (axios, data) =>
        axios.get("/web-cskh/api/danh-sach-don-vi-vt"),
    getDSNVTheoLNV: async (axios, data) =>
        axios.get("/web-cskh/api/danh-muc/dien-thoai-vien", {
            params: data
        }),
    getDSNV: async (axios, data) =>
        axios.get("/web-cskh/api/mo-phieu-khao-sat/nhan-vien-giao-khao-sat", {
            params: data
        }),
    getDSLoaiHinh: async (axios, data) =>
        axios.get("/web-cskh/api/danh-sach-loai-hinh", {
            params: data
        }),
    getDSHinhThucKS: async (axios, data) =>
        axios.get("/web-cskh/api/danh-sach-hinh-thuc-ks", {
            params: data
        }),
    getDSGPKSTB: async (axios, data) =>
        axios.post(
            "/web-cskh/api/gpkstb/danh-sach-de-giao-phieu" +
                `?pageNum=${data.pageNum}&pageSize=${data.pageSize}&totalRow=${data.totalRow}`,
            data
        ),
    getDSDaGiaoKS: async (axios, data) =>
        axios.get("/web-cskh/api/gpkstb/danh-sach-da-giao-khao-sat", {
            params: data
        }),
    getDSPhieuDG: async (axios, data) =>
        axios.get(
            "/web-cskh/api/gpkstb/danh-sach-phieu-da-giao-khao-sat-thue-bao",
            {
                params: data
            }
        ),
    postGiaoPhieu: async (axios, data) =>
        axios.post("/web-cskh/api/gpkstb/giao-phieu", data),
    postHuyGiao: async (axios, data) =>
        axios.post("/web-cskh/api/gpkstb/huy-giao-phieu", data),
    deleteHuyGiao: async (axios, data) =>
        axios.delete("/web-cskh/api/gpkstb/xoa-phieu-ks", { data: data }),
    postUploadDS: async (axios, data) =>
        axios.post("/web-cskh/api/gpkstb/upload-danh-sach", data),
    postUploadDSVer2: async (axios, data) =>
        axios.post("/web-cskh/api/gpkstb/upload-thue-bao", data),
    getDSFileTraVe: async (axios, data) =>
        axios.get(
            "/web-cskh/api/gpkstb/lay-ds-tra-ve" +
                `?hinhThucKSId=${data.hinhThucKSId}&pageNum=${data.pageNum}&pageSize=${data.pageSize}&totalRow=${data.totalRow}`
        ),
    // NhanPhieuKSTB:
    getDSThamSoMD: async (axios, data) =>
        axios.get("/web-cskh/api/danh-sach-tat-ca-tham-so-mac-dinh"),
    getDSphieuGiaoNhanKS: async (axios, data) =>
        axios.get("/web-cskh/api/nkstb/lay-ds-luot-giao-khao-sat", {
            params: data
        }),
    getDSphieuDaGiaoKS: async (axios, data) =>
        axios.get("/web-cskh/api/nkstb/lay-ds-phieu-da-giao-ks", {
            params: data
        }),
    getDSphieuDaGiaoKSTB: async (axios, data) =>
        axios.get("/web-cskh/api/nkstb/lay-ds-phieu-da-giao-kstb", {
            params: data
        }),
    getDSphieuDaGiaoKSV2: async (axios, data) =>
        axios.get("/web-cskh/api/nkstb/lay-ds-phieu-da-giao-ks-v2", {
            params: data
        }),
    getPhieuQuaGioTheoNV: async (axios, data) =>
        axios.get("/web-cskh/api/nkstb/lay-phieu-qua-gio-theo-nhan-vien", {
            params: data
        }),
    getKiemTraThoiGianLayPhieu: async (axios, data) =>
        axios.get("/web-cskh/api/nkstb/kiem-tra-thoi-gian-lay-phieu", {
            params: data
        }),
    deleteXoaPhieuDSTMPTheoNV: async (axios, data) =>
        axios.delete("/web-cskh/api/nkstb/xoa-nhan-vien-ks-tmp-theo-nhan-vien"),
    getThongBaoOBToanTinh: async (axios, data) =>
        axios.get("/web-cskh/api/nkstb/lay-thong-bao-OB-toan-tinh", {
            params: data
        }),
    postThemNVKS: async (axios, data) =>
        axios.post("/web-cskh/api/nkstb/them-nhan-vien-khao-sat", data),
    getBoQuaPhieuKS: async (axios, data) =>
        axios.get("/web-cskh/api/nkstb/bo-qua-phieu-ks", {
            params: data
        })
};
