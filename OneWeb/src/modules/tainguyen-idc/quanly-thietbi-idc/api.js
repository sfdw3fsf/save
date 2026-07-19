// ============= MAIN API ============= //

export default {
  // Lấy danh sách thiết bị IDC
  getDanhSachThietBiIDC: async (axios) => {
    try {
      const rs = await axios.get('web-ecms/idc/quanly-thietbi')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
    }
  },

  // Tìm kiếm thiết bị IDC
  searchThietBiIDC: async (axios, searchParams) => {
    try {
      const rs = await axios.post('web-ecms/idc/quanly-thietbi/search', searchParams)
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
    }
  },

  // MOCK SEARCH: log FormData and return mocked list (no filter)
  searchThietBiMock: async (axios, formData) => {
    // Log all form entries
    if (formData instanceof FormData) {
      const logObj = {}
      for (const [key, value] of formData.entries()) {
        logObj[key] = value
      }
      // eslint-disable-next-line no-console
      console.log('[IDC] searchThietBiMock formData =>', logObj)
    } else {
      // eslint-disable-next-line no-console
      console.log('[IDC] searchThietBiMock payload =>', formData)
    }

    // Optionally you could call real API here
    // const rs = await axios.post('web-ecms/idc/quanly-thietbi/search', formData)
    // return rs?.data?.data ?? []

    return []
  },

  // Upsert thiết bị IDC (thêm mới hoặc cập nhật)
  upsertThietBiIDC: async (axios, data) => {
    try {
      const payload = {
        ...data,
        lapDatTaiIdcId: data.phamViIdcId
      }
      delete payload.phamViIdcId

      const rs = await axios.post('web-ecms/idc/quanly-thietbi/upsert', payload)
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
    }
  },

  // Xóa nhiều thiết bị IDC
  deleteMultiThietBiIDC: async (axios, ids) => {
    try {
      const rs = await axios.post('web-ecms/idc/quanly-thietbi/delete-multi', { ids })
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
    }
  },

  // Lấy chi tiết thiết bị IDC theo ID
  getChiTietThietBiIDC: async (axios, id) => {
    try {
      const rs = await axios.get(`web-ecms/idc/quanly-thietbi/detail/${id}`)
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
    }
  },

  // Lấy danh sách port theo thiết bị ID
  getDanhSachPortTheoThietBi: async (axios, id) => {
    try {
      const rs = await axios.get(`web-ecms/idc/quanly-thietbi/${id}/ports`)
      return rs && rs.data ? rs.data.data : []
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
    return await getDanhMucCommon(axios, 'IDC-DS-IDC', token.getDonViID(), 'VIEW')
  },

  // Lấy danh mục tòa nhà
  getDanhMucToaNha: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-TOANHA')
  },
  // Lấy danh mục tòa nhà
  getDanhMucToaNhaByIdcId: async (axios, idcId) => {
    return await getDanhMucCommon(axios, 'IDC-TOANHA-BY-IDC-ID', idcId)
  },

  // Lấy danh mục mặt sàn
  getDanhMucMatSan: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-MATSAN')
  },

  // Lấy danh mục mặt sàn
  getDanhMucMatSanByToaNhaId: async (axios, toaNhaId) => {
    return await getDanhMucCommon(axios, 'IDC-MATSAN-BY-TOANHA-ID', toaNhaId)
  },

  // Lấy danh mục phòng
  getDanhMucPhong: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-PHONG')
  },

  // Lấy danh mục phòng
  getDanhMucPhongByMatSanId: async (axios, matSanId) => {
    return await getDanhMucCommon(axios, 'IDC-PHONG', matSanId)
  },

  // Lấy danh mục loại thiết bị
  getDanhMucLoaiThietBi: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-TBI')
  },

  // Lấy danh mục rack
  getDanhMucRack: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-RACK')
  },

  getDanhMucRackByPhongZone: async (axios, phongZoneId) => {
    return await getDanhMucCommon(axios, 'IDC-RACK-BY-PHONGZONE', phongZoneId)
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

  // Lấy danh mục thành phần thiết bị
  getDanhMucThanhPhanThietBi: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-THANHPHAN-TBI')
  },

  // Lấy thiết bị cha
  getThietBiCha: async (axios, dvqlId) => {
    return await getDanhMucCommon(axios, 'IDC-THIETBI-BY-DVQL', dvqlId)
  },

  // Lấy danh mục hãng sản xuất
  getDanhMucHangSanXuat: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-HANG-SX')
  },

  getDanhMucMangThietBi: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-MANG-TBI')
  },

  getDanhMucLoaiThietBiFromMangThietBi: async (axios, mangThietBiId) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-TBI', mangThietBiId)
  },

  getDanhMucHeThongThietBi: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-HETHONG-TBI', token.getDonViID())
  },

  // Lấy danh mục U theo Rack ID
  getDanhMucUByRack: async (axios, rackId) => {
    return await getDanhMucCommon(axios, 'IDC-RACK-UNIT-BY-RACK', rackId, 'VIEW')
  },

  // Lấy danh mục nhà cung cấp
  getDanhMucNhaCungCap: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-NHA-CUNGCAP')
  },

  getDanhMucLoaiCong: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-CONG')
  },

  // ============= PORT OPERATIONS ============= //
  // Upsert danh sách port cho thiết bị IDC
  upsertPortThietBiIDC: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/upsert-list-port-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  // Xóa port theo danh sách ID
  deletePortThietBiIDC: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/xoa-port-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
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
