import CapNhatTienKyQuyAPI from "../../api/CapNhatTienKyQuyAPI";
import CommonAPI from "../../api/Commons";

export const actions = {
 
  // async getDanhSachNhanVienDatCoc({ commit, state }, data) {
  //   let response = await CapNhatTienKyQuyAPI.getDanhSachNhanVienDatCoc(this._vm.axios, data)
  //   let ds = []
  //   if (response.data.error_code === 'BSS-00000000') {
  //     if (response.data.data !== undefined) {
  //       ds = response.data.data
  //       console.log("dsNVDC:"+ds)
  //     } else {
  //       this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
  //     }
  //   } else {
  //     this._vm.$root.toastError(respone.data.message)
  //   }
  //   commit('setDanhSachNhanVienDatCoc', ds)
  // },

  async getDanhSachNhanVienDatCoc({ commit, state }, data) {
    try {
        let response = await CapNhatTienKyQuyAPI.getDanhSachNhanVienDatCoc(this._vm.axios, data);
        if (Array.isArray(response.data.data)) {
            // commit("setListCongNo", response.data.data);
        } else {
            // commit("setListCongNo", response.data.data.data);
        }
        return response;
    } catch (error) {
        //   commit("setListCongNo", []);
        return error.response.data;
    }
},

  async clearData({ commit, state }, data) {
    commit("clearData", data);
  }
};
