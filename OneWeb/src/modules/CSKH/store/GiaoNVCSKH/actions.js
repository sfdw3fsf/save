import GiaoNVCSKHApi from '@/modules/CSKH/api/GiaoNVCSKHApi'

export const actions = {
  async getDsDonVi({ commit, state }) {
    try {
      // let response = {
      //   data: {
      //     "error": "200",
      //     "error_code": "BSS-00000000",
      //     "message": "Thành công",
      //     "message_detail": "",
      //     "request_id": "",
      //     "data": {
      //         "page": 0,
      //         "maxSize": 10,
      //         "totalElement": 15,
      //         "totalPages": 2,
      //         "sort": null,
      //         "propertiesSort": null,
      //         "data": [
      //             {
      //                 "DONVI_ID": 10007,
      //                 "TEN_DV": "Host Thủy Nguyên - old"
      //             },
      //             {
      //                 "DONVI_ID": 981,
      //                 "TEN_DV": "Hồng Bàng 1"
      //             },
      //             {
      //                 "DONVI_ID": 40,
      //                 "TEN_DV": "Phòng Bán hàng KV1"
      //             },
      //             {
      //                 "DONVI_ID": 100,
      //                 "TEN_DV": "Phòng Bán hàng KV2"
      //             },
      //             {
      //                 "DONVI_ID": 41,
      //                 "TEN_DV": "Phòng Bán hàng KV3"
      //             },
      //             {
      //                 "DONVI_ID": 101,
      //                 "TEN_DV": "Phòng Bán hàng KV4"
      //             },
      //             {
      //                 "DONVI_ID": 42,
      //                 "TEN_DV": "Phòng Bán hàng KV5"
      //             },
      //             {
      //                 "DONVI_ID": 863,
      //                 "TEN_DV": "Phòng Bán hàng KV6"
      //             },
      //             {
      //                 "DONVI_ID": 46,
      //                 "TEN_DV": "Phòng Bán hàng KV7"
      //             },
      //             {
      //                 "DONVI_ID": 47,
      //                 "TEN_DV": "Phòng Bán hàng KV8"
      //             }
      //         ]
      //     }
      //   }
      // }
      let response = await GiaoNVCSKHApi.getDsDonVi(this._vm.axios)
      let dsDonVi = response.data.data || []
      //let dsDonVi = []
      // ds.forEach(element => {
      //   dsDonVi.push({
      //     id: element.DONVI_ID,
      //     text: element.TEN_DV
      //   })
      // });
      commit("setDsDonVi", dsDonVi);
      return response;
    } catch (error) {
      commit("setDsDonVi", []);
      return error.response.data
    }
  },

  async getDsChuongTrinh({ commit, state }, data) {
      let response = await GiaoNVCSKHApi.getDsChuongTrinh(this._vm.axios, data)
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
    let response = await GiaoNVCSKHApi.getDsDaGanChuaGan(this._vm.axios, data)
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
    let response = await GiaoNVCSKHApi.getDsDaGanChuaGan(this._vm.axios, data)
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

  // setDanhSachGanChuaGan({ commit, state }, data) {
  //       if (data.setGan == 1) {
  //         commit('add', { source: state.dsDaGan, data: data.itemArr, txt: data.txt })
  //         commit('remove', { source: state.dsChuaGan, data: data.itemArr, field: data.field })
  //       }
  //       else {
  //         commit('remove', { source: state.dsDaGan, data: data.itemArr, field: data.field })
  //         commit('add', { source: state.dsChuaGan, data: data.itemArr })
  //       }
  // },
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
  ganTatCaNv({ commit, state }, data) {
    commit('ganTatCaNV', data)

    // commit('setDsDaGan', data)
  }
}
