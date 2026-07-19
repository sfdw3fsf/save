export const mutations = {
  setListDeviceGroup (state, data) {
    state.listDeviceGroup = data.data
  },
  setListDeviceType (state, data) {
    state.listDeviceType = data.data || []
  },
  setListCondition (state, data) {
    state.listCondition = data.data
  },
  setListDeviceRecallCommission (state, data) {
    state.listDeviceRecallCommission = data.data
  }
}
