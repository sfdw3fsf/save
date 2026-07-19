// ============= API CALLS (Thiết bị mạng) ============= //

export default {
  // Lấy danh sách chủng loại thiết bị mạng (hỗ trợ search)
  getDanhSachChungLoaiTBM: async function(axios, searchDto = {}) {
    try {
      const rs = await axios.post('/web-ecms/idc/chungloai-thietbi-mang', searchDto)
      return rs.data || []
    } catch (error) {
      throw error
    }
  },

  // Thêm mới/Cập nhật chủng loại thiết bị mạng
  upSertChungLoaiTBM: async function(axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/idc/chungloai-thietbi-mang/upsert', payload)
      return rs.data
    } catch (e) {
      throw e
    }
  },

  // Xóa chủng loại thiết bị mạng
  deleteChungLoaiTBM: async function(axios, ids) {
    try {
      const rs = await axios.post('/web-ecms/idc/chungloai-thietbi-mang/delete-multi', { ids: ids })
      return rs.data
    } catch (e) {
      throw e
    }
  },

  // Danh mục dùng chung
  getDanhMucHangSanXuat: async function(axios) {
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', { loaiDanhMuc: 'IDC-HANG-SX' })
    return rs.data || []
  },
  getDanhMucVaiTroThietBi: async function(axios) {
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', { loaiDanhMuc: 'IDC-LOAI-VAITRO' })
    return rs.data || []
  },
  getDanhMucLoaiCong: async function(axios) {
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', { loaiDanhMuc: 'IDC-LOAI-CONG' })
    return rs.data || []
  },
  getDanhMucChuanDauNoi: async function(axios) {
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', { loaiDanhMuc: 'IDC-CHUANDAU-DAUNOI' })
    return rs.data || []
  },
  getDanhMucModule: async function(axios) {
    const rs = await axios.post('/web-ecms/danhmuc/thong-tin-module/danhsach')
    return rs.data || []
  },
  getDanhMucMucDichSuDung: async function(axios) {
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', { loaiDanhMuc: 'IDC-MUCDICH-SD-CONG' })
    return rs.data || []
  },

  // Import/Validate
  importChungLoaiTBMData: async function(axios, data) {
    const response = await axios.post('/web-ecms/idc/chungloai-thietbi-mang/upsert-multi', data)
    return response?.data?.data || {}
  },
  validateChungLoaiTBMData: async function(axios, payload) {
    const response = await axios.post('/web-ecms/idc/chungloai-thietbi-mang/validate-multi', payload)
    return response?.data?.data || false
  }
}



