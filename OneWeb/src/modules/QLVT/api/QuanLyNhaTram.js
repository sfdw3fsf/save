export default {
    getDSLoaiVatTu: axios =>
        axios.get("/web-qlvt/api/quan-ly-nha-tram/ds-loai-vat-tu"),
    getDSThietBiTT: axios =>
        axios.get("/web-qlvt/api/quan-ly-nha-tram/ds-thiet-bi-thuoc-tinh"),
    getDSMaVatTu: (axios, loaiVt) =>
        axios.get(
            `/web-qlvt/api/quan-ly-nha-tram/ds-vt-theo-loai-vt/${loaiVt}`
        ),
    getDSTaiSanTB: (axios, data) =>
        axios.post("/web-qlvt/api/quan-ly-nha-tram/ds-tai-san-tbi", data)
};
