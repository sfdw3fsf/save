// các hàm đọc các giá trị thuộc tính của đối tượng trong store
export const getters = {
  areaListFollowDividedPoint: (state, getters) => {
    return state.areaListFollowDividedPoint
  },
  parentAreaList: (state, getters) => {
    return state.parentAreaList
  },
  assignedArea: (state, getters) => {
    return state.assignedArea
  },
  unassignedArea: (state, getters) => {
    return state.unassignedArea
  },
  districts: (state, getters) => {
    return state.districts
  },
  wards: (state, getters) => {
    return state.wards
  },
  towns: (state, getters) => {
    return state.towns
  },
  hamlets: (state, getters) => {
    return state.hamlets
  },
  areas: (state, getters) => {
    return state.areas
  },
  loadData: (state, getters) => {
    return state.loadData
  }
}
