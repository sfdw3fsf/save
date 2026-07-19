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
      const rs = await axios.post('web-ecms/idc/hatang-cntt/thiet-bi/tim-kiem', searchParams)
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
  insertThietBiIDC: async (axios, data) => {
    try {
      const payload = {
        ...data,
        lapDatTaiIdcId: data.phamViIdcId
      }
      delete payload.phamViIdcId

      const rs = await axios.post('web-ecms/idc/hatang-cntt/thiet-bi/them-moi', payload)
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
    }
  },

  updateThietBiIDC: async (axios, data) => {
    try {
      const payload = {
        ...data,
        lapDatTaiIdcId: data.phamViIdcId
      }
      delete payload.phamViIdcId

      const rs = await axios.post('web-ecms/idc/hatang-cntt/thiet-bi/sua', payload)
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
  // Xóa nhiều thiết bị IDC
  deleteThietBiIDC: async (axios, id) => {
    try {
      const rs = await axios.post('web-ecms/idc/hatang-cntt/thiet-bi/xoa', { id: id })
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
    try {
      const rs = await axios.get('/web-ecms/danhmuc/idc/get')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },

  // Lấy danh mục tòa nhà
  getDanhMucToaNha: async (axios) => {
    try {
      const rs = await axios.get('/web-ecms/danhmuc/toanha/get')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },
  // Lấy danh mục tòa nhà
  getDanhMucToaNhaByIdcId: async (axios, idcId) => {
    return await getDanhMucCommon(axios, 'IDC-TOANHA-BY-IDC-ID', idcId)
  },

  // Lấy danh mục mặt sàn
  getDanhMucMatSan: async (axios) => {
    try {
      const rs = await axios.get('/web-ecms/danh-muc-mat-san/get-all')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },

  // Lấy danh mục mặt sàn
  getDanhMucMatSanByToaNhaId: async (axios, toaNhaId) => {
    try {
      const rs = await axios.get('/web-ecms/danh-muc-mat-san/get-all')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },

  // Lấy danh mục phòng
  getDanhMucPhong: async (axios) => {
    try {
      const rs = await axios.get('/web-ecms/idc/phong-zone')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
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

  getDanhMucRackByPhongZone: async (axios) => {
    try {
      const rs = await axios.get('/web-ecms/idc/thiet-bi-vat-ly/get-rack-theo-phong-id', { params: { portId: 3114 } })
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },

  // Lấy danh mục đơn vị quản lý
  getDanhMucDonViQuanLy: async (axios, token = null) => {
    try {
      const rs = await axios.get('/web-ecms/idc/thiet-bi-vat-ly/get-danh-muc-dv-idc')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },

  // Lấy danh mục nhân viên quản lý
  getDanhMucNhanVienQuanLy: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-NHANVIEN-QLY', token.getDonViID())
  },

  // Lấy danh mục trạng thái vận hành
  getDanhMucTrangThaiVanHanh: async (axios) => {
    return await getDanhMucCommon(axios, '/trang-thai-van-hanh')
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
    return await getDanhMucCommon(axios, '/hang-san-xuat')
  },

  getDanhMucMangThietBi: async (axios) => {
    return await getDanhMucCommon(axios, '/mang-thiet-bi')
  },

  getDanhMucLoaiThietBiFromMangThietBi: async (axios, mangThietBiId) => {
    return await getDanhMucCommon(axios, '/loai-thiet-bi', { mang_thietbi_id: mangThietBiId })
  },

  getDanhMucHeThongThietBi: async (axios) => {
    return await getDanhMucCommon(axios, '/he-thong-thiet-bi')
  },

  // Lấy danh mục U theo Rack ID
  getDanhMucUByRack: async (axios, rackId) => {
    return await getDanhMucCommon(axios, 'IDC-RACK-UNIT-BY-RACK', rackId)
  },

  // Lấy danh mục nhà cung cấp
  getDanhMucNhaCungCap: async (axios) => {
    return await getDanhMucCommon(axios, '/nha-cung-cap')
  },

  getDanhMucLoaiCong: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-CONG')
  },

  // Lấy danh mục phân loại thiết bị theo loại thiết bị
  getDanhMucPhanLoaiThietBi: async (axios, loaiThietBiId) => {
    return await getDanhMucCommon(axios, '/phan-loai-thiet-bi', { loai_thietbi_id: loaiThietBiId })
  },

  // Lấy danh mục chủng loại thiết bị
  getDanhMucChungLoaiThietBi: async (axios) => {
    return await getDanhMucCommon(axios, '/chung-loai-thiet-bi')
  },

  // Lấy danh mục chủ quản sở hữu thiết bị
  getDanhMucChuQuanSoHuuThietBi: async (axios) => {
    return await getDanhMucCommon(axios, '/chu-quan-so-huu-thiet-bi')
  },

  // Lấy danh mục loại khách hàng
  getDanhMucLoaiKhachHang: async (axios) => {
    return await getDanhMucCommon(axios, '/loai-khach-hang')
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
const getDanhMucCommon = async (axios, url, param = null) => {
  try {
    const rs = await axios.get(`/web-ecms/idc/hatang-cntt/danhmuc${url}`, { params: param })
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
