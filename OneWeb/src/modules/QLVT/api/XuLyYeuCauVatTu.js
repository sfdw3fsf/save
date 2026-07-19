export default {
    getDSXulyCapVatTu: (axios, data) =>
        axios.get(`/web-qlvt/api/xet-duyet-cap-vt/ds-xet-duyet-cap-vt`),
    getDSPhieuCapVTMienPhi: (axios, data) =>
        axios.get(
            `/web-qlvt/api/xet-duyet-cap-vt/ds-pheu-cap-vt-mien-phi/${data.loaiPhieu}/${data.huongGiaoId}`
        ),
    getDSPhuLucVTCapMienPhi: (axios, data) =>
        axios.get(
            `/web-qlvt/api/xet-duyet-cap-vt/ds-phu-luc-vat-tu-cap-mien-phi/${data.hdKdId}/${data.hdTbId}/${data.loai}`
        ),
    getDSHBTBTheoHDKH: (axios, data) =>
        axios.get(
            `/web-qlvt/api/xet-duyet-cap-vt/ds-hdtb-theo-hdkh/${data.hdKdId}`
        ),
    getDSThietBiHdTB: (axios, data) =>
        axios.get(
            `/web-qlvt/api/xet-duyet-cap-vt/ds-mua-thiet-bi/${data.hdTbId}`
        ),
    getTTCTThietBi: (axios, data) =>
        axios.get(
            `/web-qlvt/api/xet-duyet-cap-vt/ds-loai-thiet-bi/${data.loaiTbiId}`
        ),
    postDSKMThietBi: (axios, data) =>
        axios.post(`/web-qlvt/api/xet-duyet-cap-vt/ds-km-thiet-bi`, data),
    postBanVatTuMienPhi: (axios, data) =>
        axios.post(
            `/web-qlvt/api/xet-duyet-cap-vt/hoan-thanh-ho-so-ban-vt`,
            data
        ),
    getDsHuongGiaoQuyTrinh: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/huong-giao-quy-trinh/${data.quyTrinhId}/${data.thuTuId}`
        ),
    postDsHuongGiao: (axios, data) =>
        axios.post(`/web-qlvt/api/giao-phieu-bao-hanh/ds-huong-giao`, data)
};
