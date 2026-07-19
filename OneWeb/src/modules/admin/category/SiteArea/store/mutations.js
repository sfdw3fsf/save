// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  getAreaListFollowDividePointSuccess (state, data) {
    state.areaListFollowDividedPoint = data
  },
  getParentAreaListSuccess (state, data) {
    state.parentAreaList = data
  },
  getAssignedAreaSuccess (state, data) {
    state.assignedArea = data
  },
  getUnassignedAreaSuccess (state, data) {
    state.unassignedArea = data
  },
  getDistrictsSuccess (state, data) {
    state.districts = data
  },
  getWardsSuccess (state, data) {
    state.wards = data
  },
  getTownsSuccess (state, data) {
    state.towns = data
  },
  getHamletssSuccess (state, data) {
    state.hamlets = data
  },
  getAreasSuccess (state, data) {
    state.areas = data
  },
  getLoadDataSuccess (state, data) {
    state.loadData = data
  }
}
