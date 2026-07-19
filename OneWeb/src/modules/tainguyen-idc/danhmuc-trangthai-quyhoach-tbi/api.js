export default {
  // Lấy danh sách trạng thái
  getDanhSachTrangThai: (axios) => axios.post('/web-ecms/danhmuc-qh-thietbi/get-all'),

  // Cập nhật trạng thái
  capNhatTrangThai: (axios, data) =>
    axios.post('/web-ecms/danhmuc-qh-thietbi/upsert', {
      trangThaiId: data.TRANGTHAI_ID == 0 ? null : data.TRANGTHAI_ID,
      ten: data.TEN,
      tenTat: data.TENTAT,
      suDung: data.SUDUNG ? 1 : 0
    }),

  // Xóa trạng thái
  xoaTrangThai: (axios, id) =>
    axios.post('/web-ecms/danhmuc-qh-thietbi/delete', {
      trangThaiId: id
    }),

  // Bulk create trạng thái
  bulkCreateTrangThai: (axios, data) =>
    axios.post('/web-ecms/danhmuc-qh-thietbi/bulk-create', data),

  // Validate batch trạng thái
  validateBatch: (axios, data) =>
    axios.post('/web-ecms/danhmuc-qh-thietbi/validate-batch', data)
}
