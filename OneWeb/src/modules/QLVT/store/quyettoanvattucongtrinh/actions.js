import API from '../../api/QuyetToanVatTuCongTrinhAPI'

export const actions = {
  async getDanhMucVT_DVT({ commit, state }, data) {
    let response = await API.getDanhMucVT_DVT(this._vm.axios, data)
    return response.data.data
  },
  async getDanhMucVT_KieuTB({ commit, state }, data) {
    let response = await API.getDanhMucVT_KieuTB(this._vm.axios, data)
    return response.data.data
  },
  async getDSKhoGiao({ commit, state }, data) {
    let response = await API.getDSKhoGiao(this._vm.axios, data)
    return response.data.data
  },
  async getDSKhoNhan({ commit, state }, data) {
    let response = await API.getDSKhoNhan(this._vm.axios, data)
    return response.data.data
  },
  async getDSLoHang({ commit, state }, data) {
    let response = await API.getDSLoHang(this._vm.axios, data)
    return response.data.data
  },
  async getDSQuyetToanCT({ commit, state }, data) {
    let response = await API.getDSQuyetToanCT(this._vm.axios, data)
    return response.data.data
  },
  async getDSPhieuVatTuQTCT({ commit, state }, data) {
    let response = await API.getDSPhieuVatTuQTCT(this._vm.axios, data)
    return response.data.data
  },
  async getDSLichSuVatTu({ commit, state }, data) {
    let response = await API.getDSPhieuVatTuQTCT(this._vm.axios, data)
    return response.data.data
  },
  async getMucDichXuat({ commit, state }, data) {
    let response = await API.getMucDichXuat(this._vm.axios, data)
    return response.data.data
  },
  async getMucDichNhapBH({ commit, state }, data) {
    let response = await API.getMucDichNhapBH(this._vm.axios, data)
    return response.data.data
  },
  async KiemTraHanChungTu({ commit, state }, data) {
    let response = await API.KiemTraHanChungTu(this._vm.axios, data)
    return response.data.data
  },
  async TaoPhieuChungTuV2({ commit, state }, data) {
    let response = await API.TaoPhieuChungTuV2(this._vm.axios, data)
    return response.data.data
  },
  async QuyetToanVatTuCT({ commit, state }, data) {
    let response = await API.QuyetToanVatTuCT(this._vm.axios, data)
    return response.data.data
  },
  async XoaChungTuID({ commit, state }, data) {
    let response = await API.XoaChungTuID(this._vm.axios, data)
    return response.data.data
  },
  async KiemTraSerialThuHoi({ commit, state }, data) {
    let response = await API.KiemTraSerialThuHoi(this._vm.axios, data)
    return response.data.data
  },
  async KiemTraKhoThuHoi({ commit, state }, data) {
    let response = await API.KiemTraKhoThuHoi(this._vm.axios, data)
    return response.data.data
  },
  async ThuHoiVatTuCongTrinh({ commit, state }, data) {
    let response = await API.ThuHoiVatTuCongTrinh(this._vm.axios, data)
    return response.data.data
  },
  async CapNhatPhieuVatTu({ commit, state }, data) {
    let response = await API.CapNhatPhieuVatTu(this._vm.axios, data)
    return response.data.data
  },
  async CapMaVatTuPVT({ commit, state }, data) {
    let response = await API.CapMaVatTuPVT(this._vm.axios, data)
    return response.data.data
  },
  async CapNhatMaPVT({ commit, state }, data) {
    let response = await API.CapNhatMaPVT(this._vm.axios, data)
    return response.data.data
  },
  async QuyetToanVatTuCTAll({ commit, state }, data) {
    let response = await API.QuyetToanVatTuCTAll(this._vm.axios, data)
    return response.data.data
  },
  async ThuHoiCTAll({ commit, state }, data) {
    let response = await API.ThuHoiCTAll(this._vm.axios, data)
    return response.data.data
  },
  async ThayDoiVatTu({ commit, state }, data) {
    let response = await API.ThayDoiVatTu(this._vm.axios, data)
    return response.data.data
  },
  async capNhatMaVT({ commit, state }, data) {
    let response = await API.capNhatMaVT(this._vm.axios, data)
    return response.data.data
  },
}