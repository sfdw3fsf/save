export default {
    layDSThuocTinh: (axios, kieu) => axios.get(`/web-qlvt/api/thiet-lap-khung-gia-tri/ds-thuoc-tinh-ktt/${kieu}`),
    layDSGiaTriThuocTinhGan: (axios, rttId) => axios.get(`/web-qlvt/api/thiet-lap-khung-gia-tri/ds-khung-gia-tri-theo-tt/${rttId}`),
    capNhatKhungGTTTT: (axios, data) => axios.post(`/web-qlvt/api/thiet-lap-khung-gia-tri/cap-nhat-khung-gia-tri-tt`, data),
};
