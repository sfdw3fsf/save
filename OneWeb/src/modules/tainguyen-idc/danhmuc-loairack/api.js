export default {
  // Lấy danh sách loại rack
  getDanhSachLoaiRack: (axios) => axios.post('/web-ecms/idc/loai-rack/lay-danhsach'),

  // Cập nhật loại rack
  capNhatLoaiRack: (axios, data) =>
    axios.post('/web-ecms/idc/loai-rack/capnhat', {
      pId: data.id == 0 ? null : data.id,
      pTen: data.ten,
      pSoU: data.soU,
      pDai: data.dai,
      pRong: data.rong,
      pCao: data.cao,
      pMaMau: data.maMau,
      pGhiChu: data.ghiChu,
      pTenTat: data.tenTat,
      pMoTa: data.moTa,
      pSuDung: data.suDung ? 1 : 0
    }),

  // Xóa loại rack
  xoaLoaiRack: (axios, id) =>
    axios.post('/web-ecms/idc/loai-rack/xoa', {
      id: id
    }),

  // Bulk create loại rack
  bulkCreateLoaiRack: (axios, data) =>
    axios.post('/web-ecms/idc/loai-rack/bulk-create', {
      danhSachLoaiRack: data
    }),

  // Validate batch loại rack
  validateBatchLoaiRack: (axios, data) =>
    axios.post('/web-ecms/idc/loai-rack/validate-batch', {
      danhSachLoaiRack: data
    })
}
