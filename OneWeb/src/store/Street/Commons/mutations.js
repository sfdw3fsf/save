export const mutations = {
  setTownAreas (state, data) {
    state.street.townAreas = data
  },
  setDistricts (state, data) {
    state.street.districts = data
  },
  setSelectedTownArea (state, id) {
    state.street.selectedTownAreaId = id
  },
  setSelectedDistrict (state, id) {
    state.street.selectedDistrictId = id
  },
  setTownName (state, townName) {
    state.street.townName = townName
  },
  setTownId (state, id) {
    state.street.townId = id
  },
  setTownIdNeo (state, townIdNeo) {
    state.street.townIdNeo = townIdNeo
  },
  setNote (state, note) {
    state.street.note = note
  },
  setSelectedSearchDistrictId (state, id) {
    state.street.selectedSearchDistrictId = id
  },
  setCurrentSearchWardId (state, id) {
    state.street.currentSearchWardId = id
  },
  setWardsAdded (state, data) {
    state.street.wardsAdded = []
    if (data && data.length > 0) {
      state.street.wardsAdded.push(...data.map(d => {
        return {
          id: d.phuong_id,
          ward: d.ten_phuong,
          wardIdNeo: d.phuong_id_neo,
          district: state.street.districts.find(i => i.QUAN_ID.toString().trim() === d.quan_id.toString().trim()).TEN_QUAN
        }
      }))
    }
  },
  setWardsNotAdded (state, data) {
    state.street.wardsNotAdded = []
    if (data && data.length > 0) {
      state.street.wardsNotAdded.push(...data.map(d => {
        return {
          id: d.phuong_id,
          ward: d.ten_phuong,
          wardIdNeo: d.phuong_id_neo,
          district: state.street.districts.find(i => i.QUAN_ID.toString().trim() === d.quan_id.toString().trim()).TEN_QUAN
        }
      }))
    }
  },
  setSearchWardsBelongDistrict (state, data) {
    state.street.searchWards = data
  },
  setTowns (state, data) {
    state.street.townsByWard = []
    if (data && data.length > 0) {
      state.street.townsByWard.push(...data.map(d => {
        return {
          townId: d.PHO_ID,
          townIdNeo: d.PHO_ID_NEO,
          townName: d.TEN_PHO,
          townGroup: state.street.townAreas.find(t => t.ID.toString() === d.NHOMPHO_ID.toString()).NAME,
          note: d.GHICHU,
          wardId: d.PHUONG_ID,
          wardIdNeo: d.PHUONG_ID_NEO
        }
      }))

      if (state.street.townsByWard.length > 0) {
        state.street.townIdNeo = state.street.townsByWard[0].townIdNeo
        state.street.townName = state.street.townsByWard[0].townName
        state.street.selectedTownAreaId = state.street.townsByWard[0].townGroup
        state.street.note = state.street.townsByWard[0].note
      }
    }
  },
  addWardsToWardsAdded (state, wards) {
    if (wards && wards.length > 0) {
      for (let i = 0; i < wards.length; i++) {
        if (!(state.street.wardsAdded.find(w => w.id.toString() === wards[i].id.toString()))) {
          state.street.wardsAdded.push(wards[i])
        }
      }
    }
  },
  removeWardsFromWardsNotAdded (state, wards) {
    for (let i = 0; i < wards.length; i++) {
      state.street.wardsNotAdded = state.street.wardsNotAdded.filter(k => k.id.toString() !== wards[i].id.toString())
    }
  },
  addWardsToWardsNotAdded (state, wards) {
    if (wards && wards.length > 0) {
      for (let i = 0; i < wards.length; i++) {
        if (!(state.street.wardsNotAdded.find(w => w.id.toString() === wards[i].id.toString()))) {
          state.street.wardsNotAdded.push(wards[i])
        }
      }
    }
  },
  removeWardsFromWardsAdded (state, wards) {
    for (let i = 0; i < wards.length; i++) {
      state.street.wardsAdded = state.street.wardsAdded.filter(k => k.id.toString() !== wards[i].id.toString())
    }
  },
  resetData (state, data) {
    state.street.selectedTownAreaId = state.street.townAreas.length > 0 ? state.street.townAreas[0].ID : 0
    state.street.townIdNeo = null
    state.street.townName = null
    state.street.townId = null
    state.street.note = null
    state.street.selectedDistrictId = state.street.districts.length > 0 ? state.street.districts[0].QUAN_ID : 0
    state.street.selectedSearchDistrictId = state.street.selectedDistrictId
  }
}
