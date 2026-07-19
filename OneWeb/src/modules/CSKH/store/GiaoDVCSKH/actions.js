import GiaoDVCSKHApi from '@/modules/CSKH/api/GiaoDVCSKHApi'

export const actions = {
  async getDsLoaiDonVi({ commit, state }) {
    try {
      let response = await GiaoDVCSKHApi.getDsLoaiDonVi(this._vm.axios)
      let ds = response.data.data || []
      let dsLoaiDonVi = []
      ds.forEach(element => {
        dsLoaiDonVi.push({
          id: element.LOAIDV_ID,
          text: element.TEN_LOAIDV
        })
      });
      commit("setDsLoaiDonVi", dsLoaiDonVi);
      return response;
    } catch (error) {
      commit("setDsLoaiDonVi", []);
      return error.response.data
    }
  },

  async getDsChuongTrinh({ commit, state }, data) {
    let response = await GiaoDVCSKHApi.getDsChuongTrinh(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds.forEach((element, index) => element.STT = index + 1)
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsChuongTrinh', ds)
  },

  async getDsDaGan({ commit, state }, data) {
    let response = await GiaoDVCSKHApi.getDsDaGanChuaGan(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsDaGan', ds)
  },

  async getDsChuaGan({ commit, state }, data) {
    // let response = {
    //   data: {
    //     "error": "200",
    //     "error_code": "BSS-00000000",
    //     "message": "Thành công",
    //     "message_detail": "",
    //     "request_id": "",
    //     "data": {
    //       "page": 0,
    //       "maxSize": 10,
    //       "totalElement": 2,
    //       "totalPages": 1,
    //       "sort": null,
    //       "propertiesSort": null,
    //       "data": [
    //         {
    //           "DONVI_ID": 102,
    //           "TEN_DV": "Phòng Khách hàng tổ chức - DN",
    //           "NOIDUNG": "test"
    //         },
    //         {
    //           "DONVI_ID": 863,
    //           "TEN_DV": "Phòng Bán hàng KV6",
    //           "NOIDUNG": "test"
    //         }
    //       ]
    //     }
    //   }
    // }
    let response = await GiaoDVCSKHApi.getDsDaGanChuaGan(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsChuaGan', ds)
  },

  clearData({ commit, state }, data) {
    commit('clearData', data)
  },

  setDanhSachGanChuaGan({ commit, state }, data) {
    console.log('data', data)
    if (data.setGan == 1) {
      commit('add', { source: state.dsDaGan, data: data.itemArr, txt: data.txt })
      commit('remove', { source: state.dsChuaGan, data: data.itemArr, field: data.field })
    } else if (data.setGan == 2){
      commit('remove', { source: state.dsDaGan, data: data.itemArr, field: data.field })
      commit('add', { source: state.dsChuaGan, data: data.itemArr })
    } 
  },
  khongGanDv({ commit, state }, data) {
    // commit('setDsDaGan', data)
    commit('remove', { source: state.dsDaGan, data: data.itemArr, field: data.field })
    commit('add', { source: state.dsChuaGan, data: data.itemArr })
  },

  ganTatCaDv({ commit, state }, data) {
    commit('ganTatCaDV', data)

    // commit('setDsDaGan', data)
  }
}