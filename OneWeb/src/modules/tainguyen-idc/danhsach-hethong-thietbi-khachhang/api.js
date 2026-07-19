// ============= MAIN API ============= //

export default {
  // Lấy danh sách hệ thống thiết bị
  getDanhSachHetHongThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-hethong-thietbi-khachhang', params)
      return rs && rs.data ? rs.data.data : { total: 0, items: [] }
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },

  deleteHetHongThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/xoa-hethong-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  createHeThongThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/upsert-hethong-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  updateHeThongThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/upsert-hethong-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
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
  getDanhMucDonViQuanLy: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-DONVI-QLY')
  },

  // Lấy danh mục nhân viên quản lý
  getDanhMucNhanVienQuanLy: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-NHANVIEN-QLY', token.getDonViID())
  },

  // Lấy danh mục trạng thái vận hành
  getDanhMucTrangThaiVanHanh: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-TRANGTHAI-VH')
  },

  // Lấy danh mục trạng thái tài sản
  getDanhMucTrangThaiTaiSan: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-TRANGTHAI-TS')
  },

  getDanhMucLoaiCong: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-CONG')
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
