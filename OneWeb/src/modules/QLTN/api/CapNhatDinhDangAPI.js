export default {
    getDSDinhDang: (axios) => axios.get('/web-thuno/api/thu-no/in-hoa-don/in-bao-cuoc-format'),
    deleteDinhDang: (axios, id) => axios.delete('/web-thuno/api/thu-no/in-hoa-don/in-bao-cuoc-format/' + id),
    updateDinhDang: (axios, data) => axios.put('/web-thuno/api/thu-no/bao-cao/format', data),
}