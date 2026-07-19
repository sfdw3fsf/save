export default {
    getMaTTNeo: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/ma-tt-neo-vnp', {params: data}),
    getDSThuNoTamThuTraTruoc: (axios, data) => axios.post('ccbs/executor/ts_thuno_tamthu_tratruoc_nochitiet', data),
}
