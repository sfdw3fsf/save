export default {
    sinhMaThung: (axios, chungTuId) => axios.get(`/web-qlvt/api/gan-serial-thiet-bi/sinh-ma-thung?chung-tu-id=${chungTuId}`),
}