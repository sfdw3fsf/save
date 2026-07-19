import qs from "qs"
export default {
    getLanNangGoi: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/lan-nang-goi-trong-ngay', { params: data }),
    getDSNangGoi: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/lay-ds-nang-goi', { params: data, paramsSerializer: params => {return qs.stringify(params) } }),    
    getDSHaGoi: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-ha-goi', { params: data, paramsSerializer: params => {return qs.stringify(params) } }),
    getDSHaGoiTheoFile: (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-ha-goi-theo-file', data),
    getDSNangGoiTheoFile: (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nang-goi-theo-file', data),
    updateChuyenDSNangTocDoSangTDTD: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/chuyen-ds-nang-toc-do-sang-tdtd', data),
    updateChuyenDSHaTocDoSangTDTD: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/chuyen-ds-ha-toc-to-sang-tdtd', data),
    getDSLapHopDongKhacHaGoi: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-lap-hop-dong-ha-goi', { params: data }),

    //Popup doc file:
    getKiemTraDSTuFile: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/kiem-tra-ma-tt', { params: data, paramsSerializer: params => {return qs.stringify(params) } }),

    //Hoan thien thay doi toc do:
    getDSKieuLapDat: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-kieu-lap-dat', { params: data }),
    getDSNangHaTocDoDuyTri: (axios, id) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nang-ha-toc-do-duy-tri/kieu-ld/' + id),
    getDSNhanVienTheoPhieu: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhan-vien-theo-phieu', { params: data }),
    getDSHopDongHuongGiao: (axios, data) => axios.get('/web-thuno/api/thu-no/css/ds-hop-dong-huong-giao', { params: data }),
    updateGiaoViec: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/cap-nhat-giao-viec', data),
    updateHoanThienHoSoTDTDuyTri: (axios, id) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/kich-hoat-dich-vu/hop-dong-thue-bao/' + id)
}