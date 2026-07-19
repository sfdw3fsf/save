const BASE_URL = '/web-ecms/tainguyen-idc/sodo-daunoi-mang'

export default {
    getDiagramByIDC: (axios, idcId) =>
        axios.post(`${BASE_URL}/get-diagram-by-idc`, {
            idcId
    }),

    getUnconnectedDevices: (axios, params) =>
        axios.post(`${BASE_URL}/get-unconnected-devices`, {
            idcId: params.idcId,
            keyword: params.keyword || null,
    }),

    goKetNoiThietBiMang: (axios, params) =>
        axios.post(`${BASE_URL}/go-ket-noi-thiet-bi-mang`, {
            thietBiId: params.thietBiId,
            thietbiKetnoiId: params.thietbiKetnoiId,
    }),

    getStatistics: (axios, idcId) =>
        axios.post(`${BASE_URL}/get-statistics`, {
            idcId
    }),
}
