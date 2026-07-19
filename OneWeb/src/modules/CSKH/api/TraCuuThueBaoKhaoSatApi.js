export default {
    getDsCauHoi: (axios, data) => axios.get('/web-cskh/api/thue-bao-khao-sat/cau-hoi'),
    getDsThueBaoKS: (axios, data) => axios.post('/web-cskh/api/tra-cuu/thue-bao-khao-sat?pageNo=' + data.pageNo + '&pageSize=' + data.pageSize, data),
    getChiTietTraLoi: (axios, data) => axios.get('/web-cskh/api/thue-bao-khao-sat', {params: data}),

    //frmKhaoSatKH
    getDsLyDoKTL: (axios, data) => axios.get('/web-cskh/api/danh-muc/ly-do-ktl'),
    getThongTinBaoHong: (axios, data) => axios.get('/web-cskh/api/thue-bao-khao-sat/thong-tin-bao-hong', {params: data}),
    
}