import API from '@/modules/CSKH/api/XacNhanPhanHoiNVKTApi'

export const actions = {
  async clearData({ commit, state }) {
    commit("clearData");
  },
  async clearDsPhieuLH({commit, state}) {
    commit("clearDsPhieuLH")
  },
  async getDsDonViLoiXacNhan({ commit, state }, data) {
    try {
      let response = await API.getDsDonViLoiXacNhan(this._vm.axios, data)
      let ds = response.data.data || []
      let nganhNghe = []
      nganhNghe.push({id: '', text: ''})
      ds.forEach(element => {
        nganhNghe.push({
          id: element.DONVI_ID,
          text: element.TEN_DV
        })
      });
      commit("setDsDonViLoiXacNhan", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsDonViLoiXacNhan", []);
      return error.response.data
    }
  },
  async getDsNhanVienLoi({ commit, state }, data) {
    try {
      let response = await API.getDsNhanVienLoi(this._vm.axios, data)
      let ds = response.data.data || []
      let nganhNghe = []
      // nganhNghe.push({id: '', text: ''})
      ds.forEach(element => {
        nganhNghe.push({
          id: element.NHANVIEN_ID,
          text: element.TEN_NV
        })
      });
      commit("setDsNhanVienLoi", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsNhanVienLoi", []);
      return error.response.data
    }
  },
  async getDsNhomNguyenNhan({ commit, state }, data) {
    try {
      let response = await API.getDsNhomNguyenNhan(this._vm.axios, data)
      let ds = response.data.data.data || []
      let nganhNghe = []
      nganhNghe.push({id: '', text: ''})
      ds.forEach(element => {
        nganhNghe.push({
          id: element.NHOMNN_ID,
          text: element.NHOM_NN
        })
      });
      commit("setDsNhomNguyenNhan", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsNhomNguyenNhan", []);
      return error.response.data
    }
  },
  async getDsNguyenNhan({ commit, state }, data) {
    try {
      let response = await API.getDsNguyenNhan(this._vm.axios, data)
      let ds = response.data.data.data || []
      let nganhNghe = []
      // nganhNghe.push({id: '', text: ''})
      ds.forEach(element => {
        nganhNghe.push({
          id: element.NGUYENNHAN_ID,
          text: element.NGUYENNHAN
        })
      });
      commit("setDsNguyenNhan", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsNguyenNhan", []);
      return error.response.data
    }
  },
  async getDsPhieuDieuLaiXacNhan({ commit, state }, data) {
    try {
      let response = await API.getDsPhieuDieuLaiXacNhan(this._vm.axios, data)
      let ds = response.data.data.data || []
      commit("setDsPhieuLH", ds);
      return response;
    } catch (error) {
      commit("setDsPhieuLH", []);
      return error.response.data
    }
  },
  async getDsPhieuXacNhanKSKhachHang({ commit, state }, data) {
    try {
      let response = await API.getDsPhieuXacNhanKSKhachHang(this._vm.axios, data)
      let ds = response.data.data.data || []
      commit("setDsPhieuLH", ds);
      return response;
    } catch (error) {
      commit("setDsPhieuLH", []);
      return error.response.data
    }
  },
}