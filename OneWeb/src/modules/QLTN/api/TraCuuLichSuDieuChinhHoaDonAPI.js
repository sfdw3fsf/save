export default {
    traCuuLichSu: (axios, data) =>
        axios.get(`/web-thuno/api/thu-no/in-hoa-don/ds-bien-dong-hddt`, {
            params: data
        }),
    traCuuChiTiet: (axios, data) =>
        axios.get(`/web-thuno/api/thu-no/in-hoa-don/bien-dong-hddt/${data}`)
};
