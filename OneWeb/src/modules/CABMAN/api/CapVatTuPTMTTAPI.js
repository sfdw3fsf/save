export default {
    getDsQuyenND: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${data.nguoiDungId}`),
    getDanhMucCapVatTuPTMTT: (axios, data) => axios.get('/web-cabman/capvattu-ptm-tt/lay-danhmuc-capvattu-ptm-tt'),
    
    getDSNhanVienGiaoNhan: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-nhan-vien-giao-nhan/${data.donViId}/${data.nhanVienId}`,),
    getDSMucDichLyDo: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-muc-dich-ly-do/${data.nhomMdId}`,),
    KiemTraTinhTrangHongVT: (axios, data) => axios.post('/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-tinh-trang-hong-vat-tu',data),
    getDSThietBiDaCap: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-thiet-bi-da-cap?serial=${data.serial}`,),

    getDSThietBi: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-thiet-bi?serial=${data.serial}`,),
    getDSVatTu: (axios, data) => axios.get('/web-qlvt/api/cap-vat-tu-thue-bao/ds-vat-tu',),
    SoLuongThuHoiVTTheoPhieu: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/so-luong-thu-hoi-vat-tu-theo-phieu/${data.sdVtId}`,),
    SoLuongThuHoiVT: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/so-luong-thu-hoi-vat-tu/${data.sdVtId}`,),

    getDSHinhAnh: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/lay-ds-hinh-anh/${data.phieuVtId}`,),
    XoaThongTinHinhAnh: (axios, data) => axios.post(`/web-qlvt/api/cap-vat-tu-thue-bao/xoa-thong-tin-hinh-anh?linkImg=${data.linkImg}&phieuVtId=${phieuVtId}`,data.body),
    CapNhatThuHoiVatTuV2: (axios, data) => axios.post('/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-thu-hoi-vat-tu-v2',data),
    getDSTinhTrangThietBiVT: (axios, data) => axios.get('/web-qlvt/api/cap-vat-tu-thue-bao/ds-tinh-trang-thiet-bi-vt',),

    getDSTinhTrangVT: (axios, data) => axios.get('/web-qlvt/api/cap-vat-tu-thue-bao/ds-tinh-trang-vat-tu',),
    getDSLyDoTon: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-ly-do-ton/${data.nhomTonId}`,),

    getKeys: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/get-key`,data),
    getThamSoMacDinh: (axios, data) => axios.get(`/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/tham-so-mac-dinh/${data.kieuId}`,),
    getDSKhoTheoNV: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${data.nhanVienId}/${data.donViId}`,),
    getVaTuTheoSerial: (axios, data) => axios.get(`/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/vat-tu-theo-serial/${data.serial}`,),


    getDSHsccPTM: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/lay-ds-hscc-capvattu-ptm`,data),
    getDSGVCaiTaoBaoDuong: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/lay-ds-gv-caitao-baoduong`,data),

    getDSTamUngVT: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/lay-ds-tuvt-ptm`,data),
    getDSVatTuSDPTM: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/lay-ds-vattu-sd-ptm`,data),
    KiemTraThuHoi: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/kiemtra-vt-thuhoi-ptm`,data),
    KiemTraPTMSuDungVT: (axios, data) => axios.get(`/web-cabman/capvattu-ptm-tt/kiemtra-ptm-sdvt/${data.serial}`,),

    SoLuongVatTuPTM: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/soluong-vattu-ptm`,data),
    CapNhatSDVT: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/capnhat-sdvt-ptm`,data),
    CapNhatPhieuVTThuHoi: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/capnhat-phieuvt-thuhoi-ptm`,data),
    KiemTraVTCapRa: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/kiemtra-vtcapra-ptm`,data),

    KiemTraDayCTBD: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/kiemtra-day-ctbd`,data),
    InsertPhieuVT: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/insert-phieuvt-ptm`,data),
    getDSPhieuVTPTM: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/lay-ds-phieuvt-ptm`,data),
    getPhieuVTHscc: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/lay-ds-phieuvt-ptm-hscc`,data),

    getPhieuKhongVT: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/ht-phieu-khongvt-ptm/{hscc_id}`,data),
    DelUpdateLyDo: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/del-updatelydo-ptm`,data),
    GiaoPhieuTdQTVT: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/giaophieu-td-qtvt-ptm`,data),
    HuyGiaoHsccPTM: (axios, data) => axios.post(`/web-cabman/capvattu-ptm-tt/huygiao-hscc-ptm`,data),

    UploadFiles: (axios, data) => axios.post(`/web-cabman/storage/uploadFile`,data),
    DeleteFile: (axios, data) => axios.post(`/web-cabman/storage/deleteFile`,data),
    PresignedUrl: (axios, data) => axios.post(`/web-ecms/storage/getPresignedUrl`,data),
    
}