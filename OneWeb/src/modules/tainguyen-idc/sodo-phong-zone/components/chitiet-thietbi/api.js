// ============= MAIN API ============= //

export default {
  // ============= THIẾT BỊ ============= //
  // Lấy danh sách thiết bị
  getDanhSachThietBiKhachHang: async (axios, params) => {
    try {
      // if each value = -1 then return null
      const payload = Object.fromEntries(
        Object.entries(params).map(([key, value]) => [key, value == -1 ? null : value])
      )
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-thietbi-khachhang', payload)
      return rs && rs.data ? rs.data.data : { total: 0, items: [] }
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },

  getDanhSachThietBiCha: async (axios) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-thietbi-khachhang', {
        page: 1
      })
      let data = rs && rs.data ? rs.data.data : { total: 0, items: [] }
      return data.filter((item) => item.THIETBICHA_ID == null) // Lọc lấy các thiết bị không có thiết bị cha
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },
  getDanhMucHeThong: async (axios) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-hethong-thietbi-khachhang', {
        loaiDanhMuc: 'HE_THONG_TBI_KH'
      })
      return rs && rs.data ? rs.data.data : { total: 0, items: [] }
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },

  getDanhMucUByRack: async (axios, rackId) => {
    try {
      const rs = await getDanhMucCommon(axios, 'IDC-U-RACK', rackId)
      return rs
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },

  updateThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/upsert-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  deleteThietBiKhachHang: async (axios, id) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/xoa-thietbi-khachhang', {
        thietBiKHId: id
      })
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
    return await getDanhMucCommon(axios, 'IDC-DS-IDC', token.getDonViID(), 'VIEW')
  },

  // Lấy danh mục đơn vị quản lý
  getDanhMucDonViQuanLy: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-DS-IDC', token.getDonViID())
  },

  getDanhMucToaNha: async (axios, idcId) => {
    return await getDanhMucCommon(axios, 'IDC-TOANHA-BY-IDC', idcId)
  },

  // Lấy danh mục tòa nhà (tất cả) - tham khảo từ IDC
  getDanhMucToaNhaAll: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-TOANHA')
  },

  // Lấy danh mục mặt sàn
  getDanhMucMatSan: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-MATSAN')
  },

  // Lấy danh mục mặt sàn theo tòa nhà
  getDanhMucMatSanByToaNhaId: async (axios, toaNhaId) => {
    return await getDanhMucCommon(axios, 'IDC-MATSAN-BY-TOANHA-ID', toaNhaId)
  },

  // Lấy danh mục phòng/zone
  getDanhMucPhong: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-PHONG')
  },

  // Lấy danh mục phòng/zone theo mặt sàn
  getDanhMucPhongByMatSanId: async (axios, matSanId) => {
    return await getDanhMucCommon(axios, 'IDC-PHONG', matSanId)
  },

  // Lấy danh mục rack
  getDanhMucRack: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-RACK')
  },

  // Lấy danh mục rack theo phòng/zone
  getDanhMucRackByPhongZone: async (axios, phongZoneId) => {
    return await getDanhMucCommon(axios, 'IDC-RACK-BY-PHONGZONE', phongZoneId)
  },

  // Lấy danh mục loại thiết bị
  getDanhMucLoaiThietBi: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-TBI')
  },

  // Lấy danh mục mảng thiết bị
  getDanhMucMangThietBi: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-MANG-TBI')
  },

  // Lấy danh mục loại thiết bị theo mảng thiết bị
  getDanhMucLoaiThietBiFromMangThietBi: async (axios, mangThietBiId) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-TBI', mangThietBiId)
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

  getDanhMucRackByPhongZone: async (axios, phongId) => {
    return await getDanhMucCommon(axios, 'IDC-RACK-BY-PHONGZONE', phongId)
  },

  // ============= PORT OPERATIONS ============= //
  // Upsert danh sách port cho thiết bị khách hàng
  upsertPortThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/upsert-list-port-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  // Xóa port theo danh sách ID
  deletePortThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/xoa-port-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  // Lấy danh mục loại cổng
  getDanhMucLoaiCong: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-CONG')
  },

  // Lấy danh sách port của thiết bị khách hàng
  getDanhSachPortThietBiKhachHang: async (axios, thietBiId) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-port-thietbi-khachhang', {
        thietBiKHId: thietBiId
      })
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },

  // Lấy danh sách thành phần thiết bị
  getDanhSachThanhPhanThietBi: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-THANHPHAN-TBI')
  },

  // Lấy danh mục nhà cung cấp (nơi sản xuất)
  getDanhMucNhaCungCap: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-NHA-CUNGCAP')
  }
}

// ============= HELPER FUNCTIONS ============= //

// Lấy danh mục chung
const getDanhMucCommon = async (axios, loaiDanhMuc, thamSo1 = null, thamSo2 = null) => {
  try {
    const payload = { loaiDanhMuc }
    if (thamSo1 !== null && thamSo1 !== undefined) {
      payload.thamSo1 = thamSo1
    }
    if (thamSo2 !== null && thamSo2 !== undefined) {
      payload.thamSo2 = thamSo2
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
