import api from '@/modules/admin/category/Street/api/index'

export const actions = {
  async setTownAreas ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getTownAreas(this._vm.axios, data)
    if (res.data && res.data.data) {
      commit('setTownAreas', res.data.data)
    }
  },
  async setDistricts ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getDistricts(this._vm.axios, data)
    if (res.data && res.data.data) {
      commit('setDistricts', res.data.data)
    }
  },
  async setWardsAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getWardsAdded(this._vm.axios, data)
    if (res.data && res.data.data) {
      commit('setWardsAdded', res.data.data)
    }
  },
  async setWardsNotAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getWardsNotAdded(this._vm.axios, data)
    if (res.data && res.data.data) {
      commit('setWardsNotAdded', res.data.data)
    }
  },
  async loadWardsByDistrict ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getWardsBelongDistrict(this._vm.axios, data)
    if (res.data && res.data.data) {
      commit('setSearchWardsBelongDistrict', res.data.data)
    }
  },
  async loadTownsByWard ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getTownsByWard(this._vm.axios, data)
    if (res.data && res.data.data) {
      commit('setTowns', res.data.data)
    }
  },
  setSelectedTownArea ({ state, commit, rootState, rootGetters }, id) {
    commit('setSelectedTownArea', id)
  },
  setSelectedDistrict ({ state, commit, rootState, rootGetters }, id) {
    commit('setSelectedDistrict', id)
  },
  setTownName ({ state, commit, rootState, rootGetters }, name) {
    commit('setTownName', name)
  },
  setTownId ({ state, commit, rootState, rootGetters }, id) {
    commit('setTownId', id)
  },
  setTownIdNeo ({ state, commit, rootState, rootGetters }, townIdNeo) {
    commit('setTownIdNeo', townIdNeo)
  },
  setNote ({ state, commit, rootState, rootGetters }, note) {
    commit('setNote', note)
  },
  setSelectedSearchDistrictId ({ state, commit, rootState, rootGetters }, id) {
    commit('setSelectedSearchDistrictId', id)
  },
  setCurrentSearchWardId ({ state, commit, rootState, rootGetters }, id) {
    commit('setCurrentSearchWardId', id)
  },
  addWardsToWardsAdded ({ state, commit, rootState, rootGetters }, wards) {
    commit('addWardsToWardsAdded', wards)
  },
  removeWardsFromWardsNotAdded ({ state, commit, rootState, rootGetters }, wards) {
    commit('removeWardsFromWardsNotAdded', wards)
  },
  addWardsToWardsNotAdded ({ state, commit, rootState, rootGetters }, wards) {
    commit('addWardsToWardsNotAdded', wards)
  },
  removeWardsFromWardsAdded ({ state, commit, rootState, rootGetters }, wards) {
    commit('removeWardsFromWardsAdded', wards)
  },
  async submitNewTown ({ state, commit, rootState, rootGetters }, data) {
    try {
      // const resTownCode = await api.submitNewTownCode(this._vm.axios, data)
      const resTown = await api.submitNewTown(this._vm.axios, data)
      if (resTown.data && resTown.data.message && resTown.data.message.toLowerCase().trim() === 'success') {
        this._vm.$toast.success(`Đã thêm mới phố ${state.street.townName}`)
        return
      }
      this._vm.$toast.error(`Không thêm mới được phố ${state.street.townName}`)
    } catch (err) {
      this._vm.$toast.error(`Không thêm mới được phố ${state.street.townName}`)
      console.log(err)
    }
  },
  async submitModifyTown ({ state, commit, rootState, rootGetters }, data) {
    try {
      const resTown = await api.submitModifyTown(this._vm.axios, data)
      if (resTown.data && resTown.data.message && resTown.data.message.toLowerCase().trim() === 'success') {
        this._vm.$toast.success(`Cập nhật dữ liệu phố thành công !`)
        // this.dispatch('loadTownsByWard', state.street.currentSearchWardId)
        return
      }
      this._vm.$toast.error(`Cập nhật dữ liệu phố thất bại !`)
    } catch (err) {
      this._vm.$toast.error(`Cập nhật dữ liệu phố thất bại`)
      console.log(err)
    }
  },
  async submitDeleteTown ({ state, commit, rootState, rootGetters }, data) {
    try {
      const res = await api.submitDeleteTown(this._vm.axios, data)
      if (res.data && res.data.message && res.data.message.toLowerCase().trim() === 'success') {
        this._vm.$toast.success(`Đã xóa thành công`)
        return
      }
      this._vm.$toast.error(`Không xóa được phố`)
    } catch (err) {
      this._vm.$toast.error(`Không xóa được phố`)
      console.log(err)
    }
  },
  resetData ({ state, commit, rootState, rootGetters }, data) {
    commit('resetData')
  }
}
