// ============= API CALLS ============= //

export default {
  // Lấy danh sách chủng loại tủ đĩa (hỗ trợ search)
  getDanhSachChungLoaiTuDia: async function(axios, searchDto = {}) {
    try {
      const rs = await axios.post('/web-ecms/idc/chungloai-tudia', searchDto)
      return rs.data || []
    } catch (error) {
      throw error
    }
  },

  // Thêm mới/Cập nhật chủng loại tủ đĩa
  upSertChungLoaiTuDia: async function(axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/idc/chungloai-tudia/upsert', payload)
      return rs.data
    } catch (e) {
      throw e
    }
  },

  // Xóa chủng loại tủ đĩa
  deleteChungLoaiTuDia: async function(axios, ids) {
    try {
      const rs = await axios.post('/web-ecms/idc/chungloai-tudia/delete-multi', { ids: ids })
      return rs.data
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục hãng sản xuất
  getDanhMucHangSanXuat: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-HANG-SX'
      })
      return rs.data || []
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục loại tủ đĩa
  getDanhMucLoaiTuDia: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-LOAI-TUDIA'
      })
      return rs.data || []
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục vai trò thiết bị
  getDanhMucVaiTroThietBi: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-LOAI-VAITRO'
      })
      return rs.data || []
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục chuẩn đấu nối
  getDanhMucChuanDauNoi: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-CHUANDAU-DAUNOI'
      })
      return rs.data || []
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục module
  getDanhMucModule: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/danhmuc/thong-tin-module/danhsach')
      return rs.data || []
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục loại cổng
  getDanhMucLoaiCong: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-LOAI-CONG'
      })
      return rs.data || []
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục mục đích sử dụng
  getDanhMucMucDichSuDung: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-MUCDICH-SD-CONG'
      })
      return rs.data || []
    } catch (e) {
      throw e
    }
  },

  // Import chủng loại tủ đĩa (upsert multi)
  importChungLoaiTuDiaData: async function(axios, data) {
    try {
      const response = await axios.post('/web-ecms/idc/chungloai-tudia/upsert-multi', data)
      return response?.data?.data || {}
    } catch (err) {
      throw err
    }
  },

  // Validate dữ liệu chủng loại tủ đĩa
  validateChungLoaiTuDiaData: async function(axios, payload) {
    try {
      const response = await axios.post('/web-ecms/idc/chungloai-tudia/validate-multi', payload)
      return response?.data?.data || false
    } catch (error) {
      throw error
    }
  }
}

