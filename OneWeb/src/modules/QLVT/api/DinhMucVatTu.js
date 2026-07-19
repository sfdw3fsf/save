export default {
    getDichVu: (axios, data) =>
        axios.get(`/web-qlvt/api/dinh-muc-vat-tu/ds-dich-vu-vt`, data),
    getLoaiHinhDV: (axios, data) =>
        axios.get(
            `/web-qlvt/api/dinh-muc-vat-tu/loai-hinh-dich-vuu/${data.dichVuId}`
        ),
    getDSDinhMuc: (axios, data) =>
        axios.get(
            `/web-qlvt/api/dinh-muc-vat-tu/ds-dinh-muc-vat-tu/loai-tb/${data.loaiTBId}`
        ),
    // getTreeVTRight: (axios, data) => axios.get(`/web-qlvt/api/dinh-muc-vat-tu/ds-vat-tu-khong-co-dinh-muc/${data.dinhMucId}`),
    getTreeVTRight: (axios, data) =>
        axios.get(
            `/web-qlvt/api/dinh-muc-vat-tu/ds-vattu-chua-gan-dinh-muc-v2?dinhmuc-id=${data.dinhMucId}&page-no=${data.pageNo}&page-size=${data.pageSize}&total-row=${data.totalRow}`
        ),
    getDsVTLeft: (axios, data) =>
        axios.get(
            `/web-qlvt/api/dinh-muc-vat-tu/ds-vat-tu/dinh-muc/${data.dinhMucId}`
        ),
    getDsDonViLeft: (axios, data) =>
        axios.get(
            `/web-qlvt/api/dinh-muc-vat-tu/ds-don-vi/dinh-muc/${data.dinhMucId}`
        ),
    getDsDonViRight: (axios, data) =>
        axios.get(
            `/web-qlvt/api/dinh-muc-vat-tu/ds-don-vi-khong-co-dinh-muc/${data.dinhMucId}`
        ),
    postThemDinhMuc: (axios, data) =>
        axios.post(`/web-qlvt/api/dinh-muc-vat-tu/dinh-muc`, data),
    putThemDinhMuc: (axios, data) =>
        axios.put(`/web-qlvt/api/dinh-muc-vat-tu/dinh-muc`, data),
    deleteDinhMuc: (axios, data) =>
        axios.delete(
            `/web-qlvt/api/dinh-muc-vat-tu/dinh-muc/${data.dinhMucId}`
        ),
    putGanDinhMuc: (axios, data) =>
        axios.put(`/web-qlvt/api/dinh-muc-vat-tu/gan-dinh-muc-don-vi`, data),
    putGoDinhMuc: (axios, data) =>
        axios.put(`/web-qlvt/api/dinh-muc-vat-tu/go-dinh-muc`, data),
    capNhatDinhMuc: (axios, data) =>
        axios.put(`/web-qlvt/api/dinh-muc-vat-tu/cap-nhat-dinh-muc/${data.dinhMuc}/${data.ctmdId}/${data.Id}`)
};
