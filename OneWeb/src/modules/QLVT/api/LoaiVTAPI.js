export default {
    getDSNhomVT: (axios, ) => axios.get('/web-qlvt/api/loai-vat-tu/ds-cbo-nhom-vt'),
    getDSCboLoaiVT: (axios, data) => axios.get(`/web-qlvt/api/loai-vat-tu/ds-cbo-loai-vt/${data.loaiVTId}/${data.nhomVTId}`),
    getDSLoaiVT: (axios, nhomVTId) => axios.get(`/web-qlvt/api/loai-vat-tu/ds-loai-vt/${nhomVTId}`),
    capNhatLoaiVT: (axios, data) => axios.post('/web-qlvt/api/loai-vat-tu/cap-nhat-loai-vt',data),
}