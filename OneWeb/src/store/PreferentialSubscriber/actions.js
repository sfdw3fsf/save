import api from '@/modules/admin/category/PreferentialSubscriber/api'

export const actions = {
  async initializeDichVuVTList({ commit, state }) {
    try {
      const res = await api.getDichVuVTList(this._vm.axios)
      if (res.data && res.data.data) commit('setDichVuVTList', res.data.data)
    } catch (error) {
      throw error
    }
  },

  async initializeLoaiHinhTB({ commit, state }) {
    try {
      const res = await api.getLoaiHinhTBList(this._vm.axios)
      if (res.data && res.data.data) commit('setLoaiHinhTBList', res.data.data)
    } catch (error) {
      throw error
    }
  },

  async initializeCongVanList({ commit, state }) {
    try {
      const res = await api.getCongVanList(this._vm.axios)
      if (res.data && res.data.data) commit('setCongVanList', res.data.data)
    } catch (error) {
      throw error
    }
  },

  async initializeNguonKMList({ commit, state }) {
    try {
      const res = await api.getNguonKMList(this._vm.axios)
      if (res.data && res.data.data) commit('setNguonKMList', res.data.data)
    } catch (error) {
      throw error
    }
  },

  async fetchDanhSachThueBaoUuDai({ commit, state }, variables) {
    commit('setIsLoading', true)
    try {
      const res = await api.getDanhSachThueBaoUuDao(this._vm.axios, variables)
      if (res.data && res.data.data) commit('setDanhSachThueBaoUuDai', res.data.data)

      commit('setIsLoading', false)
    } catch (error) {
      commit('setIsLoading', false)
      throw error
    }
  },

  async fetchHopDongTheoMaThueBao({ commit, state }, variables) {
    commit('setIsLoading', true)
    try {
      const res = await api.traCuuDanhBaTheoMaTB(this._vm.axios, variables)
      console.log('LAY_DS_HOPDONG_THEO_MA_TB:', res)
      if (res.data && res.data.data) commit('setSoMayAccountTK', res.data.data)
      else commit('setSoMayAccountTK', null)

     
      commit('setIsLoading', false)
    } catch (error) {
      commit('setIsLoading', false)
      throw error
    }
  },
  async checkHopDongTheoMaThueBao({ commit, state }, variables) {

    try {
      const res = await api.traCuuDanhBaTheoMaTB(this._vm.axios, variables)
      console.log('LAY_DS_HOPDONG_THEO_MA_TB:', res)
      if (res.data && res.data.data) commit('checkMaTBTonTai', true)
      else commit('checkMaTBTonTai', false)
     
    } catch (error) {
    
      throw error
    }
  },

  setListDanhSachThueBao({ commit, state }, variables) {
    commit('setIsLoading', true)
    try {
      if (res.data && res.data.data) commit('setDanhSachThueBaoUuDai', variables)
    } catch (error) {
      throw error
    }
    commit('setIsLoading', false)
  },

  async getDanhBaTheoMaThueBao({ commit, state }, variables) {
    try {
      const res = await api.getDanhBa(this._vm.axios, variables)

      commit('setThueBaoItem', res.data.data)
    } catch (error) {
      throw error
    }
  },

  async getThongTinHopDong({ commit, state }, variables) {
    try {
      const res = await api.getDsThongTinHopDongTB(this._vm.axios, variables)
      console.log('res:', res.data)
      commit('setDsThongTinHopDongTB', res.data.data)
    } catch (error) {
      throw error
    }
  },

  async checkThueBaoUuDai({ commit, state }, variables) {
    try {
      const res = await api.checkExistThueBaoUuDai(this._vm.axios, variables)
      return res.data.data
    } catch (error) {
      throw error
    }
  },

  async saveThueBaoUuDaiNew({ commit, state }, variables) {
    try {
      const res = await api.saveThueBaoUuDai(this._vm.axios, variables);
      if(res.data && res.data.data){
        commit('setDsThongTinHopDongTB', res.data.data)
      }
    } catch (error) {
      throw error
    }
  },

  async updateThueBaoUuDai({ commit, state }, variables) {
    console.log('variables:', variables)
    try {
      const res = await api.updateThueBaoUuDai(this._vm.axios, variables)
      console.log('api update:', res)
      if(res.data && res.data.data){
        commit('isUpdateSuccess', res.data.data.data == 1 )
      }
    } catch (error) {
      throw error
    }
  }
}
