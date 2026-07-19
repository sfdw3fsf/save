export default {
  getDanhMuc: (axios, data) => axios.post('/web-ecms/idc/danh-muc/common', data),
  getPhieuCV: (axios, phieutc) => axios.post(`/web-ecms/dien/chitiet-congviec?idPhieuTc=${phieutc}`),
  // getThongTinKetNoi: (axios, ketnoi) => axios.post(`/web-ecms/dien/chitiet-ketnoi?idKetNoi=${ketnoi}`),
  saveKetNoi: (axios, data) => axios.post('/web-ecms/dien/ketnoi/save', data),
  // saveThiCongKetNoi: (axios, data) => axios.post('/web-ecms/dien/thicong-ketnoi/create-or-update', data),
  getChiTietKetNoi: (axios, id, phieutc) =>
    axios.post(`/web-ecms/dien/chitiet-ketnoi?idKetNoi=${id}&idPhieuTc=${phieutc}`),
  deleteKetNoi: (axios, data) => axios.post('/web-ecms/dien/ketnoi/delete', data),
  updateTrangThaiKetNoi: (axios, data) => axios.post('/web-ecms/dien/ketnoi/update-trang-thai', data),
  seachDSKetNoi: (axios, data) => axios.post('/web-ecms/dien/ketnoi/search', data),
  getDanhMucTaiNguyenIDC: (axios, params) => axios.post('/web-ecms/idc/danh-muc/common', params),
  getChiThietPhieuThiCong: async (axios, id) => {
    try {
      const rs = await axios.post('/web-ecms/idc/danhsach-congviec/chitiet/' + id)
      return rs && rs.data ? rs.data.data[0] : null
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
  getDSThietBi: (axios, params) => axios.post('/web-ecms/dien/tbi/search', params),
  saveThiCongKetNoiByPhieu: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/dien/thicong-ketnoi-by-phieutc', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
  getDSKetNoiKenhBan: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/dien/ketnoi/kenhban', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
  getDSRack: (axios, params) => axios.post('/web-ecms/dien/rack/search', params),
  getDanhMucTheoRack: (axios, data) => axios.post('/web-ecms/dien/danhmuc', data)
}
