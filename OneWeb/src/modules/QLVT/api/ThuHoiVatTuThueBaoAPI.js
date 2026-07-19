export default {
    getDSPhanHoiKH: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/danh-sach-phan-hoi-kh/${data.loaiTbId}/${data.loaiHdId}`
        ),
    getDSLoaiHopDong: (axios, data) =>
        axios.get(`/web-qlvt/api/thu-hoi-vt-thue-bao/ds-loai-hd`),
    getDSDVVienThong: (axios, data) =>
        axios.get(`/web-qlvt/api/quyet-toan-vat-tu/dich-vu-vat-tu`),
    getDSMucdichLyDoThuHoi: (axios, data) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-muc-dich-ly-do/${data.nhomMdId}`
        ),

    getDSTinhTrangTbi: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-tinh-trang-vat-tu`),
    getDSThietBiVatTuThuHoi: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-vat-tu-thiet-bi`),
    getDSKieuTrangBi: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-kieu-trang-bi`),
    getDSLyDoThuHoi: (axios, data) =>
        axios.post(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-muc-dich-ly-do-cap`,
            data
        ),

    getDSLyDoHuy: (axios, data) =>
        axios.get(`/web-qlvt/api/thu-hoi-vt-thue-bao/ds-ly-do-huy`),
    getDSDonViQuanLy: (axios, data) =>
        axios.post(`/web-qlvt/api/thu-hoi-vt-thue-bao/ds-don-vi-quan-ly`, data),
    getDSMucDichLoaiHopDong: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-loai-hd-muc-dich`),
    getDSThamSoMacDinh: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/tham-so-mac-dinh/${data.kieuId}`
        ),

    getDSHuongGiaoTheoTBao: (axios, data) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/lay-ds-ma-thue-bao-chua-giao-phieu?serial=${data.serial}`
        ),
    getDSThueBaoThuHoiVT_V1: (axios, data) =>
        // axios.post(
        //     `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-thue-bao-thu-hoi-vt?pageNum=${data.pageNum}&pageSize=${data.pageSize}&totalRow=${data.totalRow}`,
        //     data
        // ),
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-thue-bao-thu-hoi-vt`,
            data
        ),
    getDSThueBaoThuHoiVT_V2: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-thue-bao-thu-hoi-vt-v2`,
            data
        ),
    KiemTraThietBiThuHoi: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/kiem-tra-tbi-thu-hoi/${data.hdTbId}`
        ),

    getDSKieuYeuCau: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-kieu-yeu-cau/${data.hdTbId}`
        ),
    getDSSuDungVatTuTBao: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-su-dung-vt-thue-bao/${data.thueBaoId}`
        ),
    getDSPhieuThuHoiVTTB: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-phieu-thu-hoi-vt-thue-bao/${data.phieuId}/${data.hdTbId}`
        ),
    getDSVatTuGiaoDichThuHoi: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-vt-giao-dich-thu-hoi/${data.hdTbId}`
        ),

    getDSThietBiDaCapTheoSerialMoi: (axios, data) =>
        axios.post(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-thiet-bi-da-cap`,data
        ),
    KiemTraSerialMoiTBaoSuDung: (axios, data) =>
        axios.post(
            `/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-thuebao-sd-vt`,
            data
        ),
    getDSThietBiTheoSerial: (axios, data) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-thiet-bi?serial=${data.serial}`
        ),
    getDSVatTu: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-vat-tu`),

    getKeys: (axios, data) =>
        axios.post(`/web-qlvt/api/cap-phat-ccdc/get-key`, data),
    CapNhatThuHoiVatTu_V2: (axios, data) =>
        axios.post(
            `/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-thu-hoi-vat-tu-v2`,
            data
        ),
    getDSThaoTacTheoCode: (axios, data) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/lay-ds-thao-tac-theo-code/${data.code}`
        ),
    CapNhatPhieuVatTuThuHoi: (axios, data) =>
        axios.post(
            `/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-phieu-vat-tu-thu-hoi`,
            data
        ),

    KiemTraSeTopBox: (axios, data) =>
        axios.post(`/web-qlvt/api/thu-hoi-vt-thue-bao/kiem-tra-setopbox`, data),
    ThuHoiSeTopBox: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-he-thong-VASC`,
            data
        ),
    CapNhatTrangThaiDongBoSeTopBox: (axios, data) =>
        axios.put(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-trang-thai-dong-bo?hdTbId=${data.hdTbId}`
        ),
    CapNhatLogVASC: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-log-action`,
            data
        ),

    GiaoPhieuTapDoanDuyetTH: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/giao-phieu-td-duyet-th`,
            data
        ),
    getDSQuyTrinhTheoMa: (axios, data) =>
        axios.get(`/web-qlvt/api/thu-hoi-vt-thue-bao/ds-quy-trinh-theo-ma`),
    getIDPhieuTheoHDTB: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-id-phieu-giao-hd/${data.hdTbId}`
        ),
    HoanCongThuHoiVT_V1: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/hoan-cong-thu-hoi-vt`,
            data
        ),

    TaoGiaoPhieuTon: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/tao-giao-phieu-ton`,
            data
        ),
    CapNhatLyDoHuy: (axios, data) =>
        axios.put(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-ly-do-huy-giao-phieu?phieuId=${data.phieuId}&lydoHuyId=${data.lydoHuyId}`
        ),
    CapNhatKQXuLyKhongTH: (axios, data) =>
        axios.put(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-xu-ly-khong-thu-hoi?phieuId=${data.phieuId}&lyDoHuyId=${data.lydoHuyId}`
        ),
    CapNhatKQXyLyTH: (axios, data) =>
        axios.put(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-xu-ly-thu-hoi?phieuId=${data.phieuId}`
        ),

    getDSNhanVienChamSocThueBao: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-nhan-vien-theo-thue-bao/${data.thueBaoId}/${data.kieu}`
        ),
    SendSMS: (axios, data) => axios.get(`/web-qlvt/api/`),
    getThongTinVatTuTheoSerial: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/vat-tu-theo-serial/${data.serial}`
        ),
    CapNhatGhiChuTon: (axios, data) =>
        axios.put(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-ghi-chu-ton?phieuId=${data.phieuId}}&ghiChu=${data.ghiChu}`
        ),

    getDSNhanVienTheoPhieu: (axios, data) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-nhan-vien-theo-phieu/${data.phieuId}/${data.nhanVienId}/${data.kieuId}`
        ),
    CapNhatSuDungVatTuThuHoi: (axios, data) =>
        axios.post(
            `/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-su-dung-vt-th`,
            data
        ),
    KiemTraSerialThuHoi: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/kiem-tra-seri-thu-hoi-vt`,
            data
        ),
    KiemTraVatTuThuHoi: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-vat-tu-thu-hoi",
            data
        ),

    getSoLuongVatTuThueBao: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/so-luong-vt-thue-bao`,
            data
        ),
    getTongSoLuong: (axios, data) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/so-luong-thu-hoi-vat-tu-theo-phieu/${data.sdVtId}`
        ),
    getDSLyDoTon: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/danh-muc-ly-do-ton/${data.nhomTonId}`
        ),
    getDanhMucDonViQuanLy: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/ds-don-vi-quan-ly/${data.donViQl}/${data.loaiDvi}`
        ),

    CapNhatChuyenPhieuTo: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-chuyen-phieu-to`,
            data
        ),

    getDSMaTBMaHGThuHoi: (axios, data) =>
        axios.get(
            "/web-qlvt/api/thu-hoi-vt-thue-bao/lay-ds-ma-thue-bao-thu-hoi?serial=" +
                data.serial
        ),
    getCanhBaoSLThuHoi: (axios, data) =>
        axios.get(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/kiem-tra-canh-bao-sl-thu-hoi?hdTbId=${data.hdTbId}&baoHongId=${data.baoHongId}`
        ),

    CapNhatChuyenToThuHoiVT: (axios, data) =>
        axios.post(
            `/web-qlvt/api/thu-hoi-vt-thue-bao/cap-nhat-chuyen-to-thu-hoi-vt`,
            data
        ),
    HoanCongThuHoiVTTB: (axios, data) => axios.post(`/web-qlvt/api/thu-hoi-vt-thue-bao/hoancong-thuhoi-vattu-thuebao`, data),
    check_LAY_DS_THAMSO_MD_MATS: (axios, data) => axios.post(`/web-qlvt/api/test-port-v2/tham-so-mac-dinh`, data),
};
