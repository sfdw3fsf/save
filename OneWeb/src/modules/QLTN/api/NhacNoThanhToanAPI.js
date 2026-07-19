export default {
    updateDSNhacNoThanhToan: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no-thanh-toan', data),
    getDSLanTaoNhacNoThanhToan: (axios, kyCuoc, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-lan-tao-nhac-no-thanh-toan/' + kyCuoc, { params: data }),
    getDSNhacNoThanhToan: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no-thanh-toan', { params: data }),
    updateChuyenDSNhacNoTT: (axios, id) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/chuyen-nhac-no-tt/' + id),
    deleteThanhToan: (axios, data) => axios.delete('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no-thanh-toan', { data: data }),
    deleteLanTao: (axios, id) => axios.delete('/web-thuno/api/thu-no/xu-ly-thue-bao/lan-tao-nhac-no/' + id),
    getDSSDTNoiDungNhacNo: (axios, id) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-sdt-noi-dung-nhac-no/' + id),
    getXuatFileXlsx: async (axios, id) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/xuat-xlsx-ds-nhac-no-thanh-toan/' + id)
}