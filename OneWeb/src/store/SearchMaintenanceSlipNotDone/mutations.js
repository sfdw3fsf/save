export const mutations = {
  setDSCayDonvi (state, data) {
    // console.log("data mutations ===>", data)
    state.stores.dsCayDonvi = data
  },
  setSelectedDonVi (state, data) {
    if (data) {
      state.stores.selectedDonVi = data
    }
  }
}
