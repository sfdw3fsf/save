import TCCuocGoiAPI from "../../api/TCCuocGoiAPI";
import CommonAPI from "../../api/Commons";

export const actions = {
 
  
  async getDanhSachHuongDamThoai({ commit, state }) {
    try {
      let response = await TCCuocGoiAPI.getDanhSachHuongDamThoai(this._vm.axios)
      let ds = response.data.data || []
      let danhSachHuongDamThoai = []
      ds.forEach(element => {
        danhSachHuongDamThoai.push({
          id: element.HUONGDT_ID,
          text: element.TENHUONG
        })
      });
      commit("setDanhSachHuongDamThoai", danhSachHuongDamThoai);
      return response;
    } catch (error) {
      commit("setDanhSachHuongDamThoai", []);
      return error.response.data
    }
  },

  async getDanhSachDichVuVT({ commit, state }) {
    try {
      let response = await TCCuocGoiAPI.getDanhSachDichVuVT(this._vm.axios)
      let ds = response.data.data || []
      let danhSachDichVuVT = []
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
      let response = await TCCuocGoiAPI.getDanhSachLoaiHinhTB(this._vm.axios, data)
      let ds = response.data.data || []
      let danhSachLoaiHinhTB = []
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

  async getDanhSachCuocGoi ({ commit, state }, data) {
    let response = await TCCuocGoiAPI.getDanhSachCuocGoi(this._vm.axios, data)
    try {
      commit('setDanhSachCuocGoi', response.data.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getDanhSachCuocGoiTheoFile ({ commit, state }, data) {
    let response = await TCCuocGoiAPI.getDanhSachCuocGoiTheoFile(this._vm.axios, data)
    try {
      commit('setDanhSachCuocGoi', response.data.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  
};
