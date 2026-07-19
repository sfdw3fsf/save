const BASE_URL = '/web-ecms'

export default {
  //DANH MUC
  async getIDCByDonVi(axios, donViId) {
    try {
      return await getDanhMucCommon(axios, 'IDC-DS-IDC', donViId)
    } catch (error) {
      handleApiError(error)
    }
  },
  async getIDC(axios) {
    try {
      const rs = await axios.get('/web-ecms/danhmuc/ha-tang-idc')
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getDonViQuanLyList(axios) {
    try {
      return await getDanhMucCommon(axios, 'IDC-CLOUD-DONVI-QLY')
    } catch (error) {
      handleApiError(error)
    }
  },
  async getPhongBanQuanLyList(axios, donViId) {
    return await getDanhMucCommon(axios, 'IDC-CLOUD-DONVI-QLY-CON', donViId)
  },
  async getNguoiQuanLyList(axios, phongBanId) {
    return await getDanhMucCommon(axios, 'NHANVIEN-DONVI-IDC', phongBanId)
  },
  async getTrangThaiCungCapDichVuList(axios) {
    return await getDanhMucCommon(axios, 'TRANGTHAI_CCDV')
  },
  async getCongNgheList(axios, loaiht_id) {
    try {
      return await getDanhMucCommon(axios, 'IDC-CONGNGHE-HT', loaiht_id)
    } catch (error) {
      handleApiError(error)
    }
  },
  // API QUẢN LÝ KHO HẠ TẦNG MẠNG
  /**
   * Lấy danh sách tất cả hạ tầng mạng
   * @param {Object} axios - axios instance
   * @returns {Promise}
   */
  getAll(axios, payload) {
    return axios.post(`${BASE_URL}/quanly-kho-ha-tang-mang/get-all`, payload)
  },

  /**
   * Lấy chi tiết hạ tầng mạng theo ID
   * @param {Object} axios - axios instance
   * @param {Number} id - ID hạ tầng mạng
   * @returns {Promise}
   */
  getDetail(axios, id) {
    return axios.get(`${BASE_URL}/quanly-kho-ha-tang-mang/${id}/detail`)
  },

  /**
   * Thêm mới hoặc cập nhật hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Object} data - Dữ liệu hạ tầng mạng
   * @returns {Promise}
   */
  insertOrUpdate(axios, data) {
    return axios.post(`${BASE_URL}/quanly-kho-ha-tang-mang/insert-update`, data)
  },

  /**
   * Xóa hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Number} id - ID hạ tầng mạng
   * @returns {Promise}
   */
  delete(axios, id) {
    return axios.post(`${BASE_URL}/quanly-kho-ha-tang-mang/delete/${id}`)
  },

  /**
   * Lấy danh sách subnet theo hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Number} hatangMangId - ID hạ tầng mạng
   * @returns {Promise}
   */
  getSubnetsByHaTang(axios, hatangMangId) {
    return axios.get(`${BASE_URL}/quanly-kho-ha-tang-mang/${hatangMangId}/subnets`)
  },

  /**
   * Xóa nhiều hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Array} ids - Mảng ID hạ tầng mạng cần xóa
   * @returns {Promise}
   */
  deleteMulti(axios, ids) {
    return axios.post(`${BASE_URL}/quanly-kho-ha-tang-mang/delete-multi`, { ids })
  },

  /**
   * Lấy danh sách nhân viên theo hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Number} htmId - ID hạ tầng mạng
   * @returns {Promise}
   */
  getNhanVienByHtm(axios, htmId) {
    return axios.get(`${BASE_URL}/quanly-kho-ha-tang-mang/${htmId}/nhanvien`)
  },

  /**
   * Lưu danh sách nhân viên quản lý hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Number} htmId - ID hạ tầng mạng
   * @param {Array} employees - Mảng nhân viên [{ nhanvienId, nhiemvuId }]
   * @returns {Promise}
   */
  saveNhanVienHtm(axios, htmId, employees) {
    return axios.post(`${BASE_URL}/quanly-kho-ha-tang-mang/${htmId}/nhanvien`, employees)
  },

  /**
   * Lấy danh sách nhiệm vụ
   * @param {Object} axios - axios instance
   * @returns {Promise}
   */
  async getNhiemVuList(axios) {
    try {
      return await getDanhMucCommon(axios, 'IDC-NHIEMVU-NHANVIEN')
    } catch (error) {
      handleApiError(error)
      return []
    }
  },

  /**
   * Kiểm tra tên hạ tầng mạng đã tồn tại
   * @param {Object} axios - axios instance
   * @param {Number} id - ID hạ tầng mạng (null khi insert)
   * @param {String} ten - Tên hạ tầng mạng
   * @returns {Promise}
   */
  checkTenHaTang(axios, id, ten) {
    return axios.post(`${BASE_URL}/quanly-kho-ha-tang-mang/check-ten`, { id, ten })
  },

  async getIpAddressByHaTang(axios, hatangMangId) {
    return axios.get(`${BASE_URL}/quanly-kho-ha-tang-mang/${hatangMangId}/ipaddress`)
  },

  /**
   * Lấy danh sách thiết bị có thể đặt vào hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Number} htmId - ID hạ tầng mạng
   * @returns {Promise}
   */
  getThietBiAvailable(axios, htmId) {
    return axios.get(`${BASE_URL}/quanly-kho-ha-tang-mang/${htmId}/thietbi-available`)
  },

  /**
   * Lấy danh sách thiết bị đã đặt vào hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Number} htmId - ID hạ tầng mạng
   * @returns {Promise}
   */
  getThietBiByHtm(axios, htmId) {
    return axios.get(`${BASE_URL}/quanly-kho-ha-tang-mang/${htmId}/thietbi`)
  },

  /**
   * Cập nhật thiết bị hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Object} data - { thietBiIds: [Long], haTangMangId: Long }
   * @returns {Promise}
   */
  updateThietBiHaTangMang(axios, data) {
    return axios.post(`${BASE_URL}/quanly-kho-ha-tang-mang/thietbi/update`, data)
  },

  /**
   * Lấy lịch sử thay đổi thiết bị hạ tầng mạng
   * @param {Object} axios - axios instance
   * @param {Number} htmId - ID hạ tầng mạng
   * @returns {Promise}
   */
  getLichSuThietBi(axios, htmId) {
    return axios.get(`${BASE_URL}/quanly-kho-ha-tang-mang/${htmId}/lichsu-thietbi`)
  }
}
// Hàm chung để lấy danh mục
const getDanhMucCommon = async (axios, loaiDanhMuc, thamSo1 = null) => {
  try {
    const payload = { loaiDanhMuc }
    if (thamSo1 !== null && thamSo1 !== undefined) {
      payload.thamSo1 = thamSo1
    }
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', payload)
    return rs && rs.data ? rs.data : []
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
