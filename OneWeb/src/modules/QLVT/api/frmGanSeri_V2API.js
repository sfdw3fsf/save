export default {
    getSeriDieuChinh: (axios, data) => axios.get(`/web-qlvt/api/gan-serial-thiet-bi/ds-seri-dieu-chinh/${data.chungTuId}/${data.kieu}/${data.ctctId}`),
    CapNhatSeriDieuChinh: (axios, data) => axios.post(`/web-qlvt/api/gan-serial-thiet-bi/cap-nhat-seri-dieu-chinh`,data),
}