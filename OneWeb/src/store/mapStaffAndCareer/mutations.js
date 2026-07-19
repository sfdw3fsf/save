export const mutations = {
  setSelectedUnit (state, data) {
    state.selectedUnit = data
  },
  setSelectedTypeOfStaff (state, data) {
    state.selectedTypeOfStaff = data
  },
  setSelectedTypeOfArea (state, data) {
    state.selectedTypeOfArea = data
  },
  setStaffCode (state, data) {
    state.staffCode = data
  },
  setStaffList (state, data) {
    state.staffList = data
  },
  setIsLoadingStaffList (state, data) {
    state.isLoadingStaffList = data
  },
  setCareerMappedList (state, data) {
    state.careerMappedList = data
  },
  setCareerNotMappedList (state, data) {
    state.careerNotMappedList = data
  },
  setIsLoadingCareerMappedList (state, data) {
    state.isLoadingCareerMappedList = data
  },
  setIsLoadingCareerNotMappedList (state, data) {
    state.isLoadingCareerNotMappedList = data
  },
  updateChecked (state, data) {
    data.checked = !data.checked
  },

  setSelectedStaff (state, data) {
    state.selectedStaff = JSON.parse(JSON.stringify(data))
  },

  pushToCareerMappedList (state, data) {
    const mappedList = JSON.parse(JSON.stringify(state.careerMappedList))
    mappedList.push(...data)
    state.careerMappedList = mappedList
  },

  removeFromNotCareerMappedList (state, data) {
    const notMappedList = JSON.parse(JSON.stringify(state.careerNotMappedList))
    data.forEach(selectedItem => {
      const index = notMappedList.findIndex(item => item.nganhnghe_id === selectedItem.nganhnghe_id)
      notMappedList.splice(index, 1)
    })
    state.careerNotMappedList = notMappedList
  },

  pushToNotCareerMappedList (state, data) {
    const notMappedList = JSON.parse(JSON.stringify(state.careerNotMappedList))
    notMappedList.push(...data)
    state.careerNotMappedList = notMappedList
  },

  removeFromCareerMappedList (state, data) {
    const mappedList = JSON.parse(JSON.stringify(state.careerMappedList))
    data.forEach(selectedItem => {
      const index = mappedList.findIndex(item => item.nganhnghe_id === selectedItem.nganhnghe_id)
      mappedList.splice(index, 1)
    })
    state.careerMappedList = mappedList
  }
}
