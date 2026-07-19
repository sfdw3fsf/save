// ============= API CALLS ============= //

export default {
  // Lấy danh sách mục đích sử dụng (truyền {} để lấy tất cả hoặc SearchMucDichSuDungDto để search)
  getDanhSachMucDichSuDung: async function(axios, searchDto = {}) {
    try {
      const rs = await axios.post('/web-ecms/idc/mucdich-sd', searchDto)
      return rs.data
    } catch (error) {
      throw error
    }
  },

  // Lấy chi tiết mục đích sử dụng theo ID
  getMucDichSuDungById: async function(axios, id) {
    try {
      const rs = await axios.post('/web-ecms/idc/mucdich-sd/detail', { id: id })
      return rs.data
    } catch (error) {
      throw error
    }
  },

  // Thêm mới/Cập nhật mục đích sử dụng (upsert)
  upSertMucDichSuDung: async function(axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/idc/mucdich-sd/upsert', payload)
      return rs.data
    } catch (e) {
      throw e
    }
  },

  // Xóa nhiều mục đích sử dụng
  deleteMucDichSuDung: async function(axios, ids) {
    try {
      const rs = await axios.post('/web-ecms/idc/mucdich-sd/delete-multi', { ids })
      return rs.data
    } catch (e) {
      throw e
    }
  },

  // Validate một mục đích sử dụng
  validateMucDichSuDung: async function(axios, payload) {
    try {
      const response = await axios.post('/web-ecms/idc/mucdich-sd/validate', payload)
      return response.data
    } catch (error) {
      throw error
    }
  },

  // Validate nhiều mục đích sử dụng
  validateMucDichSuDungData: async function(axios, payload) {
    try {
      const response = await axios.post('/web-ecms/idc/mucdich-sd/validate-multi', payload)
      return response.data
    } catch (error) {
      throw error
    }
  },

  // Lấy danh sách loại mục đích từ common danh mục
  getDanhSachLoaiMucDich: async function(axios) {
    try {
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-NHOM-MUCDICH'
      })
      return rs.data || []
    } catch (error) {
      throw error
    }
  }
}
