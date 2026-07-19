// ============= MAIN API ============= //

export default {
  getChiThietPhieuThiCong: async (axios, id) => {
    try {
      const rs = await axios.post('/web-ecms/idc/danhsach-congviec/chitiet/' + id)
      return rs && rs.data ? rs.data.data[0] : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  getDanhSachHetHongThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-hethong-thietbi-khachhang', params)
      return rs && rs.data ? rs.data.data : { total: 0, items: [] }
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },

  getDanhSachHetHongThietBiIDC: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/quanly-thietbi/hethong-thietbi-idc', params)
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

  getDanhSachThiCongHeThongKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-thi-cong-hethong-khachhang', params)
      return rs && rs.data ? rs.data.data : { total: 0, items: [] }
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },

  themThiCongHeThongKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/them-thi-cong-hethong-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  chuyenPhieuThiCong: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/chuyen-phieu-thi-cong', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  traPhieuThiCong: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/tra-phieu-thi-cong', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  hoanThanhPhieuThiCong: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/hoan-thanh-phieu-thi-cong', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  themMoiThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/upsert-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  themThiCongThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/them-thi-cong-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  getPhieuThiCongThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-thi-cong-thietbi-khachhang', params)
      return rs && rs.data ? rs.data.data : { total: 0, items: [] }
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },

  getThietBiTheoHeThong: async (axios, heThongId) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/danhsach-thietbi-theo-hethong-khachhang', {
        id: heThongId
      })
      return rs && rs.data ? rs.data.data : { total: 0, items: [] }
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  },

  kiemTraThietBiLapDat: async (axios, thietBiId) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/kiem-tra-thietbi-da-lap-dat', {
        thietBiKHId: thietBiId
      })
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  lapDatThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/lap-dat-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  thaoDoThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/go-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  thayDoiVitriLapDat: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/thay-doi-vi-tri-lap-dat', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  getDetailFromIdThiCong: async (axios, id) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/chitiet-thi-cong-hethong', {
        phieuThiCongId: id
      })
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  // ============= PORT & LOẠI CỔNG ============= //
  // Lấy danh sách port theo thiết bị (tái sử dụng endpoint của module quản lý thiết bị IDC)
  getDanhSachPortTheoThietBi: async (axios, id) => {
    try {
      const rs = await axios.post(`web-ecms/idc/thietbi-hethong-khachhang/danhsach-port-thietbi-khachhang`, {
        thietBiKHId: id
      })
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },

  upsertPortThietBiKhachHang: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/upsert-list-port-thietbi-khachhang', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  getDanhSachHeThongThietBi: async (axios) => {
    try {
      const rs = await axios.get('web-ecms/idc/quanly-hethong-thietbi')
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
    }
  },

  // Lấy danh mục loại cổng (tái sử dụng danh mục chung)
  getDanhMucLoaiCong: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-LOAI-CONG')
  },

  // ============= DANH MỤC ============= //
  // Lấy danh mục chung (có thể dùng cho các loại danh mục khác)
  getDanhMuc: async (axios, loaiDanhMuc, thamSo1 = null) => {
    return await getDanhMucCommon(axios, loaiDanhMuc, thamSo1)
  },

  // Lấy danh mục IDC
  getDanhMucIDC: async (axios, token, mode = null) => {
    return await getDanhMucCommon(axios, 'IDC-DS-IDC', token.getDonViID(), mode)
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

  getDanhMucRackByPhongZone: async (axios, phongId) => {
    return await getDanhMucCommon(axios, 'IDC-RACK-BY-PHONGZONE', phongId)
  },

  // Lấy danh mục trạng thái tài sản
  getDanhMucTrangThaiTaiSan: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-TRANGTHAI-TS')
  },

  getDanhMucUByRack: async (axios, rackId, isChuaLapDat = false) => {
    isChuaLapDat = isChuaLapDat ? null : 1
    return await getDanhMucCommon(axios, 'IDC-U-RACK', rackId, isChuaLapDat)
  },

  // Lấy danh mục thành phần thiết bị
  getDanhMucThanhPhanThietBi: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-THANHPHAN-TBI')
  },

  // Lấy danh mục nhà cung cấp (nơi sản xuất)
  getDanhMucNhaCungCap: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-NHA-CUNGCAP')
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

  getDanhMucHeThongIDC: async (axios, donViId) => {
    return await getDanhMucCommon(axios, 'IDC-HETHONG-TBI', donViId)
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

  getDanhMucDonViQuanLy: async (axios) => {
    return await getDanhMucCommon(axios, 'IDC-DONVI-QLY')
  },

  getDanhMucNhanVienQuanLy: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-NHANVIEN-QLY', token.getDonViID())
  },

  getDanhMucTaiNguyenIDC: (axios, params) => axios.post('/web-ecms/idc/danh-muc/common', params)
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
  console.log('API ERROR: ', e)
  if (e?.data?.message_detail) {
    throw new Error(e.data.message_detail)
  } else {
    console.error(e)
  }
}
