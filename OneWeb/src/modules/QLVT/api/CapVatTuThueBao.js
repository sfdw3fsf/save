export default {
    getLoaiHDvaPL: (axios, nhomMdId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-loai-hop-dong/${nhomMdId}`
        ),
    getDMDichVu: axios =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-dich-vu-vat-tu"),
    getDMNhanVien: (axios, donViId, nhanVienId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-nhan-vien-giao-nhan/${donViId}/${nhanVienId}`
        ),
    getDMKho: (axios, nhanVienId, donViId) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${nhanVienId}/${donViId}`
        ),
    getDMKieuTB: axios =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-kieu-thiet-bi-v1"),
    getDMLyDoTon: (axios, nhomTonId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-ly-do-ton/${nhomTonId}`
        ),
    getDMTinhTrangTBCap: axios =>
        axios.get(
            "/web-qlvt/api/cap-vat-tu-thue-bao/ds-tinh-trang-thiet-bi-vt"
        ),
    getDMLyDoCap: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/ds-muc-dich-ly-do-cap",
            data
        ),
    getDMTrangBi: axios =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-kieu-trang-bi"),
    getDMMucDichLyDo: (axios, nhomMdId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-muc-dich-ly-do/${nhomMdId}`
        ),
    getDMTenThietBi: axios =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-vat-tu-thiet-bi"),
    getQuyenNguoiDungCVTTB: (axios, nguoiDungId) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${nguoiDungId}`
        ),
    getDSThueBaoCapVT: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/ds-thue-bao-cap-vat-tu",
            data
        ),
    getDSThueBaoCapVTTruyenSoLieu: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/ds-thue-bao-cap-vat-tu-truyen-sl",
            data
        ),
    getDMThaoTac: (axios, code) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/lay-ds-thao-tac-theo-code/${code}`
        ),
    getDMTinhTrangVTThuHoi: axios =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-tinh-trang-vat-tu"),
    getDSVatTuDaCap: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/ds-phieu-vattu-hop-dong-bao-hong",
            data
        ),
    getDSVatTuThueBaoSuDung: (axios, thueBaoId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-vat-tu-su-dung/${thueBaoId}`
        ),
    getDSVatTuTrongKho: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/ds-vat-tu-trong-kho",
            data
        ),
    kiemTraHuyPhieuGiao: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-huy-giao-phieu",
            data
        ),
    getNgayBatDauDungThu: (axios, hdtbId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-ngay-bat-dau-dung-thu/${hdtbId}`
        ),
    themMoiPhieuCapVT: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/tao-phieu-cap-vat-tu-v2",
            data
        ),
    getKeyDKVTMuaSam: (axios, data) =>
        axios.post("/web-qlvt/api/cap-phat-ccdc/get-key", data),
    getDSNhanVienTheoID: (axios, phieuId, nhanVienId, dkieuId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-nhan-vien-theo-phieu/${phieuId}/${nhanVienId}/${dkieuId}`
        ),
    deleteLichSuVT: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-thu-hoi-vat-tu-v2",
            data
        ),
    getKTTTHongVT: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-tinh-trang-hong-vat-tu",
            data
        ),
    KiemTraDinhMuc: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-dinh-muc-vat-tu",
            data
        ),
    KIEMTRA_VTCAPRA_V2: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-vat-tu-cap-ra",
            data
        ),
    KIEMTRA_VTDACAPTB: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-vat-tu-cap-thue-bao",
            data
        ),
    getDSThamSoMacDinh: (axios, kieuId) =>
        axios.get(
            `/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/tham-so-mac-dinh/${kieuId}`
        ),
    getDSTBSuDungVT: (axios, data) =>
        axios.post(
            `/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-thuebao-sd-vt`, data
        ),
    getThongTinTBDaCapTheoSerial: (axios, data) =>
        axios.post(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-thiet-bi-da-cap`, data
        ),
    KTSoLuongVTTBDangSD: (axios, vatTuId, thueBaoId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-sl-vattu-thuebao/${vatTuId}/${thueBaoId}`
        ),
    KTSoLuongVTTBDangSD_v2: (axios, vatTuId, thueBaoId, hdtb_id) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-sl-vattu-thuebao_v2/${vatTuId}/${thueBaoId}/${hdtb_id}`
        ),
    CapNhatSuDungVT_TH: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-su-dung-vt-th",
            data
        ),
    getSL_DD: (axios, sdVtId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/so-luong-thu-hoi-vat-tu-theo-phieu/${sdVtId}`
        ),
    getSL_HT: (axios, sdVtId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/so-luong-thu-hoi-vat-tu/${sdVtId}`
        ),
    CAPNHAT_PHIEUVT_THUHOI: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-phieu-vat-tu-thu-hoi",
            data
        ),
    getThamSoMacDinh: (axios, maTs, nhanvienId) =>
        axios.get(
            `/web-qlvt/api/test-port-v2/lay-tham-so-mac-dinh?ma-ts=${maTs}&nhanvien-id=${nhanvienId}`
        ),
    getMAP_ID: axios =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/inf-user-pw-from-tinh"),
    KTMaTBCoTonTai: (axios, maThueBao) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-ma-thue-bao-ton-tai?maThueBao=${maThueBao}`
        ),
    LAY_PORT_THEO_TBID: (axios, data) =>
        axios.post("/web-qlvt/api/test-port-v2/thong-tin-port", data),
    LAY_DS_PORT_THEOHD_TB: (axios, hdTbId, thueBaoId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/lay-port-theo-hdtb/${hdTbId}/${thueBaoId}`
        ),
    testPort: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/ds-test-port-thiet-bi",
            data
        ),
    getInfoTamUngVT: (axios, nhanVienId, serial) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-tam-ung-vat-tu?nhanVienId=${nhanVienId}&serial=${serial}`
        ),
    ht_phieu_kkhongvt: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/hoan-thien-phieu-vattu",
            data
        ),
    KT_TB_DA_ThuHoiVT: (axios, hdTbId, phieuId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-thue-bao-da-thu-hoi-vt/${hdTbId}/${phieuId}`
        ),
    KT_TB_BaoHong_DA_ThuHoiVT: (axios, baoHongId, phieuId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-thue-bao-baohong-da-thu-hoi/${baoHongId}/${phieuId}`
        ),
    getGiaTriDoKiem: axios =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/lay-gia-tri-do-kiem"),
    getThongTinPhieuCapVTTB: (axios, hdTbId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/phieu-cap-vat-tu-thue-bao/${hdTbId}`
        ),
    getThongTinPhieuCapVTTB_BaoHong: (axios, baoHongId) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/phieu-cap-vat-tu-thue-bao-bao-hong/${baoHongId}`
        ),
    KT_SR_GPON: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-sr-gpon", data),
    get_TT_ONT_Theo_Account: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/ds-ont-info-by-account",
            data
        ),
    deleteLyDo: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-thue-bao/xoa-capnhat-ly-do", data),
    getThongTinModemCTS: (axios, id, kieu) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/ds-modem-cts/${id}/${kieu}`
        ),
    GIAOPHIEU_TD_TAOPHIEU_QLVT: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/giao-phieu-truyen-dan-quy-trinh",
            data
        ),
    updateCTS: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-cts-serial",
            data
        ),
    taoDL_Ton_Khong_ThuHoi_Duoc: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/tao-giao-phieu-khong-thu-hoi",
            data
        ),
    getHopDongKhachHangId: (axios, hdtb_id) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/lay-id-hop-dong-kh/${hdtb_id}`
        ),
    updateKQGiaoPhieu: (axios, phieuId) =>
        axios.put(
            `/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-kqxl-giao-phieu-bao-hong?phieuId=${phieuId}`
        ),
    themMoiDSPhieuTon: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-thue-bao/tao-ds-phieu-ton", data),
    kt_thuhoi_khongvt: (axios, data) =>
        axios.post(
            "/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-thu-hoi-vat-tu",
            data
        ),
    getDSTheoSerialOrSoAcc: (axios, serial) =>
        axios.get(
            `/web-qlvt/api/cap-vat-tu-thue-bao/lay-ds-ma-thuebao-cap-vt?serial=${serial}`
        ),
    getDSLoaiHopDongMucDich: (axios) =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-loai-hd-muc-dich"),
    getParamsBaoCao: (axios, maBc) =>
        axios.get(`/web-qlvt/api/in-phieu/ds-param-bc?maBc=${maBc}`),
    btnCapVatTu: (axios, data) =>
        axios.post("web-qlvt/api/cap-vat-tu-thue-bao/cap-vattu-thuebao", data),
    capVatTuV2: (axios, data) =>
        axios.post("web-qlvt/api/cap-vat-tu-thue-bao/cap-vattu-thuebao-v2", data),
    hoanCongCapVatTuTB: (axios, data) =>  axios.post("web-qlvt/api/cap-vat-tu-thue-bao/hoancong-cap-vattu-thuebao", data),
    hoanCongDanhSach: (axios, data) =>  axios.post("web-qlvt/api/cap-vat-tu-thue-bao/hoancong-cap-vattu-thuebao-theo-ds", data),
    ChuyenToThiCong: (axios, data) =>  axios.post("web-qlvt/api/cap-vat-tu-thue-bao/chuyen-to-thi-cong", data),
    getHinhAnh: (axios, phieuVtId) =>  axios.get(`web-qlvt/api/cap-vat-tu-thue-bao/lay-ds-hinh-anh/${phieuVtId}`),
    downloadFile: (axios, linkFile) => axios.get(`/web-qlvt/api/storage/downloadFile?fileSource=${linkFile}`),
    getLinkFile: (axios, data) => axios.post(`/web-qlvt/api/storage/getPresignedUrl`,data),
    checkFileExist: (axios, data) => axios.post(`/web-qlvt/api/storage/checkExistsFile`,data),    
    deleteFile: (axios, data) => axios.post(`/web-qlvt/api/storage/deleteFile`,data),
    uploadFile: (axios, data) => axios.post(`/web-qlvt/api/storage/uploadFile`,data),
    updateHinhAnh: (axios, data) =>  axios.post(`web-qlvt/api/cap-vat-tu-thue-bao/xoa-thong-tin-hinh-anh?linkImg=${data.linkFile}&phieuVtId=${data.phieuVtId}`),

};
