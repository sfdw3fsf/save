// ============= MAIN API ============= //

export default {
  // Lấy danh sách hệ thống thiết bị
  getDanhSachHeThongThietBi: async (axios) => {
    try {
      const rs = await axios.get('web-ecms/idc/quanly-hethong-thietbi')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
    }
  },

  // Tìm kiếm hệ thống thiết bị
  searchHeThongThietBi: async (axios, searchParams) => {
    try {
      const rs = await axios.post('web-ecms/idc/quanly-hethong-thietbi/search', searchParams)
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
    }
  },

  // Upsert hệ thống thiết bị (thêm mới hoặc cập nhật)
  upsertHeThongThietBi: async (axios, data) => {
    try {
      const rs = await axios.post('web-ecms/idc/quanly-hethong-thietbi', data)
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
    }
  },

  // Xóa nhiều hệ thống thiết bị
  deleteMultiHeThongThietBi: async (axios, ids) => {
    try {
      const rs = await axios.post('web-ecms/idc/quanly-hethong-thietbi/delete-multi', { ids })
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
    }
  },

  // Lấy chi tiết hệ thống thiết bị theo ID
  getChiTietHeThongThietBi: async (axios, id) => {
    try {
      const rs = await axios.get(`web-ecms/idc/quanly-hethong-thietbi/${id}`)
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
    }
  },

  // ============= DANH MỤC ============= //
  // Lấy danh mục chung (có thể dùng cho các loại danh mục khác)
  getDanhMuc: async (axios, loaiDanhMuc, thamSo1 = null) => {
    return await getDanhMucCommon(axios, loaiDanhMuc, thamSo1)
  },

  // Lấy danh mục IDC
  getDanhMucIDC: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-DS-IDC', token.getDonViID())
  },

  // Lấy danh mục đơn vị quản lý
  getDanhMucDonViQuanLy: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-DONVI-QLY', token.getDonViID())
  },

  // Lấy danh mục nhân viên quản lý
  getDanhMucNhanVienQuanLy: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-NHANVIEN-QLY', token.getDonViID())
  },

  // Lấy danh mục trạng thái vận hành
  getDanhMucTrangThaiVanHanh: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-TRANGTHAI-VH')
  },


  // Lấy danh mục loại khách hàng
  getDanhMucLoaiKhachHang: async (axios) => {
    return await getDanhMucCommon(axios, 'LOAI-KH')
  },

  // Lấy danh mục trạng thái tài sản
  getDanhMucTrangThaiTaiSan: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-TRANGTHAI-TS')
  },

  // Lấy danh mục mảng thiết bị
  getDanhMucMangThietBi: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-MANG-TBI')
  }
}

// ============= HELPER FUNCTIONS ============= //

// Lấy danh mục chung
const getDanhMucCommon = async (axios, loaiDanhMuc, thamSo1 = null) => {
  try {
    const payload = { loaiDanhMuc }
    if (thamSo1 !== null && thamSo1 !== undefined) {
      payload.thamSo1 = thamSo1
    }
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', payload)
    return rs && rs.data ? rs.data.data : []
  } catch (error) {
    handleApiError(error)
    return []
  }
}

const handleApiError = (e) => {
  if (e?.data?.message_detail) {
    throw new Error(e.data.message_detail)
  } else {
    console.error(e)
  }
}
