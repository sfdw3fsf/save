import API from '../../api/Commons'

export const actions = {
  async getDanhSachDonVi({ commit, state }, data) {
    let response = await API.getDanhSachDonVi(this._vm.axios)
    try {
      commit('setDanhSachDonVi', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  setKyHoaDon({ commit, state }, data) {
    commit('setKyHoaDon', data)
  },
  setChuKyHoaDon({ commit, state }, data) {
    commit('setChuKyHoaDon', data)
  },
  async getDanhSachNganHang({ commit, state }, data) {
    let response = await API.getDanhSachNganHang(this._vm.axios)
    try {
      commit('setDanhSachNganHang', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  async getDanhSachHinhThucThanhToan({ commit, state }, data) {
    let response = await API.getDanhSachHinhThucThanhToan(this._vm.axios)
    try {
      commit('setDanhSachHinhThucThanhToan', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  async getDanhSachNhanVienThuCuoc({ commit, state }, data) {
    let response = await API.getDanhSachNhanVienThuCuoc(this._vm.axios)
    try {
      commit('setDanhSachNhanVienThuCuoc', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  async getDanhSachDichVuVienThong({ commit, state }, data) {
    let response = await API.getDanhSachDichVuVienThong(this._vm.axios)
    try {
      commit('setDanhSachDichVuVienThong', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  async getDanhSachLoaiHinhThueBao({ commit, state }, id) {
    let response = await API.getDanhSachLoaiHinhThueBao(this._vm.axios, id)
    try {
      commit('setDanhSachLoaiHinhThueBao', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  async getDanhSachDichVuVienThongDangKy({ commit, state }, data) {
    let response = await API.getDanhSachDichVuVienThongDangKy(this._vm.axios)
    try {
      commit('setDanhSachDichVuVienThongDangKy', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  async getDanhMucQuanHuyen({ commit, state }, data) {
    let response = await API.getDanhMucQuanHuyen(this._vm.axios)
    try {
      commit('setDanhMucQuanHuyen', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  async getDanhSachKHLon({ commit, state }, data) {
    try {
      let response = await API.getDanhSachKHLon(this._vm.axios)
      commit('setDanhSachKHLon', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachLoaiKhachHang({ commit, state }, data) {
    try {
      let response = await API.getDanhSachLoaiKhachHang(this._vm.axios)
      commit('setDanhSachLoaiKhachHang', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachLoaiPhieu({ commit, state }, data) {
    try {
      let response = await API.getDanhSachLoaiPhieu(this._vm.axios)
      commit('setDanhSachLoaiPhieu', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachNhanVienThuNgan({ commit, state }, data) {
    try {
      let response = await API.getDanhSachNhanVienThuNgan(this._vm.axios)
      commit('setDanhSachNhanVienThuNgan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachLanTaoCongNo({ commit, state }, data) {
    try {
      let response = await API.getDanhSachLanTaoCongNo(this._vm.axios)
      commit('setDanhSachLanTaoCongNo', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachLyDoXacNhan({ commit, state }, data) {
    try {
      let response = await API.getDanhSachLyDoXacNhan(this._vm.axios)
      commit('setDanhSachLyDoXacNhan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachNguyenNhanXacNhan({ commit, state }, data) {
    try {
      let response = await API.getDanhSachNguyenNhanXacNhan(this._vm.axios)
      commit('setDanhSachNguyenNhanXacNhan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachNganHangGachNo({ commit, state }, data) {
    try {
      let response = await API.getDanhSachNganHangGachNo(this._vm.axios)
      commit('setDanhSachNganHangGachNo', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getKyHoaDonHienTai({ commit, state }, data) {
    try {
      let response = await API.getKyCuocHienHanh(this._vm.axios)
      if(response.data.data.length > 0)
        commit('setKyHoaDonHienTai', response.data.data[0])
      return response
    } catch (error) {
      throw error
    }
  }
}
