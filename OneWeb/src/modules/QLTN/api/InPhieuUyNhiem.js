export default {
    getHTT: (axios, data) =>
        axios.get(`/web-thuno/api/thu-no/common/hinh-thuc-thanh-toan/${data}`),
    getDSHoaDonTheoTT: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/in-hoa-don/thong-tin-no/hinh-thuc-thanh-toan",
            {
                params: data
            }
        ),
    getThamSoMD: (axios, data) =>
        axios.get(
            `/web-thuno/api/thu-no/in-uy-nhiem/lay-tham-so-mac-dinh-he-thong`,
            {
                params: data
            }
        ),
    getTTNoHTTT: (axios, data) =>
        axios.get(
            `/web-thuno/api/thu-no/in-uy-nhiem/thong-tin-no-hinh-thuc-thanh-toan`,
            {
                params: data
            }
        ),
    postInNganHang: (axios, data) =>
        axios.post(`/web-thuno/api/thu-no/in-uy-nhiem/in-phieu`, data)
};
