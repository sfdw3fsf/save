export default {
  getTTKhachHang: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/vi-pham-cam-ket-tt-gach-no", { params: data}),
  getDSLoaiHoaDon: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/loai-hoa-don/" + data),
  getDSKyHieuHoaDon: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/danh-muc-ky-hieu-hoa-don", {params: data}),
  gachNoKHViPhamCamKet: (axios, data) => axios.put("/web-thuno/api/quan-ly-thu-no/gach-no-vi-pham-cam-ket", data),
  getDSKHViPhamCamKet: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/vi-pham-cam-ket-ds-gach-no", {params: data}),
  deleteGachNoViPham: async (axios, data) => axios.delete('/web-thuno/api/quan-ly-thu-no/xoa-gach-no-vi-pham-cam-ket/' + data),
  updateCus: async (axios, data) => axios.post('/tichhop/vnptinvoice/updateCus', data),
  importAndPublishInv: async (axios, data) => axios.post('/tichhop/vnptinvoice/importAndPublishInv', data),
  updateHDDT: async (axios, data) => axios.post('/web-thuno/api/quan-ly-thu-no/vi-pham-cam-ket-hoa-don-dien-tu', data),
  confirmPaymentFkey: async (axios, data) => axios.post('/tichhop/vnptinvoice/confirmPaymentFkey', data)
};
