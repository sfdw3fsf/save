import axios from "axios";

export default {
    getDanhSachKho: (axios, nhanVienId, donViId) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${nhanVienId}/${donViId}`
        ),
    getDSHeThongQuyTrinh: (axios, nhomQtId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-he-thong-quy-trinh/${nhomQtId}`
        ),
    getDSHuongGiao: (axios, data) =>
        axios.post("/web-qlvt/api/giao-phieu-bao-hanh/ds-huong-giao", data),
    getDSLoaiDichVuHuongGiao: (axios, huongGiaoId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-loaidv-huong-giao/${huongGiaoId}`
        ),
    getDSDonViTheoLoaiDichVu: (axios, loaiDvuId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-dvi-theo-loai-dvu/${loaiDvuId}`
        ),
    getDsGiaoPhieuBaoHanh: (axios, data) =>
        axios.post(
            "/web-qlvt/api/giao-phieu-bao-hanh/ds-phieu-giao-bao-hanh",
            data
        ),
    taoChungTu: (axios, data) =>
        axios.post("/web-qlvt/api/giao-phieu-bao-hanh/tao-chung-tu-v2", data),
    getDSLuongThaoTac: (axios, nhomQtId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-luong-thao-tac/${nhomQtId}`
        ),
    getDSThietBiBaoHanhTra: (axios, baoHanhId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-tbi-bao-hanh-tra/${baoHanhId}`
        ),
    getDSMucDichXuatVatTuKho: (axios, maMd) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-muc-dich-xuat-vat-tu/${maMd}`
        ),
    getDSKhoGiaoTheoToTruong: (axios, loaiKhoId, donViId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-kho-giao-theo-to-truong/${loaiKhoId}/${donViId}`
        ),
    getCapNhatGPBHHuongGiao: (axios, huongGiaoId, baoHanhId) =>
        axios.put(
            `/web-qlvt/api/giao-phieu-bao-hanh/cap-nhat-giao-phieu-bh-huonggiao/${huongGiaoId}/${baoHanhId}`
        ),
    getCapNhatBaoHanhKho: (axios, khoId, baoHanhId) =>
        axios.put(
            `/web-qlvt/api/giao-phieu-bao-hanh/cap-nhat-bao-hanh-kho/${khoId}/${baoHanhId}`
        ),
    getDSThietBiKhoGiao: (axios, serial, khoGiaoId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-thiet-bi-kho-giao/${serial}/${khoGiaoId}`
        ),
    getCapNhatBaoHanhThietBi: (axios, khoId, baoHanhId) =>
        axios.put(
            `/web-qlvt/api/giao-phieu-bao-hanh/cap-nhat-bao-hanh-tbi/${khoId}/${baoHanhId}`
        ),
    getDSThietBiBaoHanh: (axios, baoHanhId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/ds-tbi-bao-hanh/${baoHanhId}`
        ),
    getDSKiemTraVatTuTrongBH: (axios, baoHanhId, khoGiaoId) =>
        axios.get(
            `/web-qlvt/api/giao-phieu-bao-hanh/kiem-tra-ds-vattu-tbi-kho/${baoHanhId}/${khoGiaoId}`
        ),
    getDSKiemTraVatTuTrongKho: (axios, data) =>
        axios.post(
            "/web-qlvt/api/giao-phieu-bao-hanh/kiem-tra-vat-tu-trong-kho",
            data
        ),
    getDSKiemTraTaoChungTu: (axios, data) =>
        axios.post(
            "/web-qlvt/api/giao-phieu-bao-hanh/kiem-tra-tao-chung-tu",
            data
        ),
    CapNhatGiaoPhieuBH: (axios, data) =>
        axios.put(
            "/web-qlvt/api/giao-phieu-bao-hanh/cap-nhat-giao-phieu-bh",
            data
        ),
    getDSKieuBaoHanh: (axios, baoHanhId) =>
        axios.get(`/web-qlvt/api/giao-phieu-bao-hanh/ds-kieu-bh/${baoHanhId}`),
    HoanThienBaoHanh: (axios, data) =>
        axios.put("/web-qlvt/api/giao-phieu-bao-hanh/hoan-thien-bh", data),
    TaoGiaoPhieuBaoHanh: (axios, data) =>
        axios.post(
            "/web-qlvt/api/giao-phieu-bao-hanh/tao-ds-giao-phieu-bao-hanh",
            data
        ),
    CapNhatDonViBH: (axios, data) =>
        axios.put(
            "/web-qlvt/api/giao-phieu-bao-hanh/cap-nhat-bao-hanh-donvi",
            data
        ),
    getLayTenMenu: (axios, data) =>
        axios.post(`/web-qlvt/api/dang-ky-cap-phat/ds-ten-menu`, data),
        
    tongHopGiaoPhieuBaohanh: (axios, data) =>  axios.post(`/web-qlvt/api/giao-phieu-bao-hanh/tong-hop-giao-phieu-bao-hanh`, data),
    huyGiaoPhieu: (axios, data) =>  axios.post(`/web-qlvt/api/giao-phieu-bao-hanh/huy-giao-phieu-bh`, data),
    getDSCayDonVi: (axios, donViId) => axios.get(`/web-qlvt/api/chon-nhanvien-ccdc/ds-donvi-ccdc-v2/${donViId}`),
    getParamsBaoCao: (axios, maBc) => axios.get(`/web-qlvt/api/in-phieu/ds-param-bc?maBc=${maBc}`),
};
