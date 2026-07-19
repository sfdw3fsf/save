// ============= API CALLS ============= //

export default {
  // Lấy danh sách phòng/zone theo ID
  getPhongZoneById: async function(axios, phongZoneId) {
    const rs = await axios.post('/web-ecms/idc/phong-zone/get-by-id', {
        id: phongZoneId
      })
    return rs.data
  },

  // Lấy danh sách phòng/zone
  getDanhSachPhongZone: async function(axios) {
    try {
      const rs = await axios.get('/web-ecms/idc/phong-zone')
      return rs.data.data
    } catch (error) {
      throw error
    }
  },

  // Lấy danh mục mặt sàn
  getDanhMucMatSan: async function(axios) {
    try {
      const rs = await axios.get('/web-ecms/idc/danh-muc/mat-san')
      return rs.data.data
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục đơn vị IDC
  getDanhMucDonViIdc: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-DS-DONVI'
      })
      return rs.data.data
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục IDC theo đơn vị
  getDanhMucIdc: async function(axios, donViId) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-DS-IDC',
        thamSo1: donViId
      })
      return rs.data.data
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục tòa nhà theo IDC
  getDanhMucToaNha: async function(axios, idcId) {
    try {
      if (!idcId) {
        return []
      }
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-TOANHA-BY-IDC-ID',
        thamSo1: idcId
      })
      return rs.data.data
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục mặt sàn theo tòa nhà
  getDanhMucMatSanByToaNha: async function(axios, toaNhaId) {
    try {
      if (!toaNhaId) {
        return []
      }
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-MATSAN-BY-TOANHA-ID',
        thamSo1: toaNhaId
      })
      return rs.data.data
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục mục đích sử dụng
  getDanhMucMucDichSuDung: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-MUCDICH-SD-PHONGZONE'
      })
      return rs.data.data
    } catch (e) {
      throw e
    }
  },

  // Lấy danh mục trạng thái vận hành
  getDanhMucTrangThaiVanHanh: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-TTVH-KHONGGIAN'
      })
      return rs.data.data || []
    } catch (e) {
      // If API doesn't exist, return empty list
      return []
    }
  },

  // Thêm mới/Cập nhật phòng/zone
  upSertThongTinPhongZone: async function(axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/idc/phong-zone', payload)
      return rs.data.data
    } catch (e) {
      throw e
    }
  },

  // Xóa phòng/zone
  deletePhongZone: async function(axios, ids) {
    try {
      const rs = await axios.delete('/web-ecms/idc/phong-zone/delete-multi', {
        data: { ids: ids }
      })
      return rs.data
    } catch (e) {
      throw e
    }
  },

  // Import phòng/zone (upsert multi)
  importPhongZoneData: async function(axios, data) {
    try {
      const response = await axios.post('/web-ecms/idc/phong-zone/upsert-multi', data)
      return response?.data?.data || {}
    } catch (err) {
      throw err
    }
  },

  // Validate dữ liệu phòng/zone
  validatePhongZoneData: async function(axios, payload) {
    try {
      const response = await axios.post('/web-ecms/idc/phong-zone/validate-multi', payload)
      return response?.data?.data || false
    } catch (error) {
      throw error
    }
  },

  // ============= THUỘC TÍNH BỔ SUNG ============= //
  // Lấy danh sách thuộc tính bổ sung của phòng/zone
  getThuocTinhPhongZone: async function(axios, phongZoneId) {
    try {
      const rs = await axios.post(`/web-ecms/thuoc-tinh/get`, {
        kieuTT: 'PHONG_ZONE',
        duLieuId: phongZoneId
      })
      return rs.data.data || []
    } catch (error) {
      throw error
    }
  },

  // Thêm thuộc tính bổ sung cho phòng/zone
  addThuocTinhPhongZone: async function(axios, data) {
    try {
      const rs = await axios.post('/web-ecms/thuoc-tinh/create', data)
      return rs.data.data
    } catch (error) {
      throw error
    }
  },

  // Cập nhật thuộc tính bổ sung của phòng/zone
  updateThuocTinhPhongZone: async function(axios, thuocTinhId, data) {
    try {
      const rs = await axios.post(`/web-ecms/thuoc-tinh/update?thuocTinhId=${thuocTinhId}`, data)
      return rs.data.data
    } catch (error) {
      throw error
    }
  },

  // Xóa thuộc tính bổ sung của phòng/zone
  deleteThuocTinhPhongZone: async function(axios, thuocTinhId) {
    try {
      const rs = await axios.post(`/web-ecms/thuoc-tinh/delete?thuocTinhId=${thuocTinhId}`)
      return rs.data
    } catch (error) {
      throw error
    }
  }
}
