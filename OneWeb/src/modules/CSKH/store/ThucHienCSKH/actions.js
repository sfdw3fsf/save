import ThucHienCSKHApi from '@/modules/CSKH/api/ThucHienCSKHApi'

export const actions = {
  async getDsChuongTrinhCSKH({ commit, state }, data) {
    try {
      let response = await ThucHienCSKHApi.getDsChuongTrinhCSKH(this._vm.axios, data)
      let ds = response.data.data.data || []
      let dsChuongTrinhCSKH = [
        {
          id: '', 
          text: "Chọn chương trình"
        }
      ]
      if(ds.length > 0) {
        ds.forEach(element => {
          dsChuongTrinhCSKH.push({
            id: element.CTCSKH_ID,
            text: element.TEN_CT
          })
        });
      }
     
      commit("setDsChuongTrinhCSKH",dsChuongTrinhCSKH);
      return response;
    } catch (error) {
      commit("setDsChuongTrinhCSKH", []);
      return error.response.data.data
    }
  },

  async getDsLoaiDoiTuong({ commit, state }) {
    try {
      let response = await ThucHienCSKHApi.getDsLoaiDoiTuong(this._vm.axios)
      let ds = response.data.data || []
      let dsLoaiDoiTuong = [
        {
          id: '', 
          text: "Chọn đối tượng"
        }
      ]
      if(ds.length > 0) {
        ds.forEach(element => {
          dsLoaiDoiTuong.push({
            id: element.doiTuongCsId,
            text: element.tenDoiTuongCs
          })
        });
      }
     
      commit("setDsLoaiDoiTuong",dsLoaiDoiTuong);
      return response;
    } catch (error) {
      commit("setDsLoaiDoiTuong", []);
      return error.response.data
    }
  },

  async getDsChamSocTheoNV ({ commit, state }, data) {
    let response = await ThucHienCSKHApi.getDsChamSocTheoNV(data)
    try {
      console.log("response"+response)
      commit('setDsChamSocTheoNV', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getDsCongViecTheoCT ({ commit, state }, data) {
    let response = await ThucHienCSKHApi.getDsCongViecTheoCT(data)
    try {
      commit('setDsCongViecTheoCT', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
}