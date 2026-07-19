import TraCuuKhieuNaiChungAPI from "../../api/TraCuuKhieuNaiChungAPI";
import CommonAPI from "../../api/Commons";

export const actions = {
 
  async getDanhSachDichVuVT({ commit, state }) {
    try {
      let response = await TraCuuKhieuNaiChungAPI.getDanhSachDichVuVT(this._vm.axios)
      let ds = response.data.data || []
      let danhSachDichVuVT = [
        {
          id: '', 
          text: "Chọn dịch vụ"
        }
      ]
      ds.forEach(element => {
        danhSachDichVuVT.push({
          id: element.DICHVUVT_ID,
          text: element.TEN_DVVT
        })
      });
      commit("setDanhSachDichVuVT",danhSachDichVuVT);
      return response;
    } catch (error) {
      commit("setDanhSachDichVuVT", []);
      return error.response.data
    }
  },

  async getDanhSachLoaiHinhTB({ commit, state }, data) {
    try {
      let response = await TraCuuKhieuNaiChungAPI.getDanhSachLoaiHinhTB(this._vm.axios, data)
      let ds = response.data.data || []
      let danhSachLoaiHinhTB = [
        {
          id: '', 
          text: "Chọn loại hình"
        }
      ]
      ds.forEach(element => {
        danhSachLoaiHinhTB.push({
          id: element.LOAITB_ID,
          text: element.TEN_LOAIHINH
        })
      });
      commit("setDanhSachLoaiHinhTB",danhSachLoaiHinhTB);
      return response;
    } catch (error) {
      commit("setDanhSachLoaiHinhTB", []);
      return error.response.data
    }
  },

  async getThongTinThanhToan ({ commit, state }, data) {
    let response = await TraCuuKhieuNaiChungAPI.getThongTinThanhToan(this._vm.axios, data)
    try {
      commit('setThongTinThanhToan', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getDanhSachKhieuNaiTB ({ commit, state }, data) {
    let response = await TraCuuKhieuNaiChungAPI.getDanhSachKhieuNaiTB(this._vm.axios, data)
    try {
      commit('setDanhSachKhieuNaiTB', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  
  async getDanhSachNVQLDaiLy ({ commit, state }, data) {
    let response = await TraCuuKhieuNaiChungAPI.getDanhSachNVQLDaiLy(this._vm.axios, data)
    try {
      commit('setDanhSachNVQLDaiLy', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
};
