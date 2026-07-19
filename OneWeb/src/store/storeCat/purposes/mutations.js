export const mutations = {
  setPurposeNotAdded (state, data) {
    state.purposes.purposesNotAdded = data
  },
  setPurposeAdded (state, data) {
    state.purposes.purposesAdded = data
  },
  removePurposesFromPurposesAdded (state, purposes) {
    if (purposes && purposes.length > 0) {
      for (let i = 0; i < purposes.length; i++) {
        if (!state.purposes.purposesNotAdded.find(p => p.mucdich_id === purposes[i].mucdich_id)) {
          state.purposes.purposesNotAdded.push(purposes[i])
        }

        state.purposes.purposesAdded = state.purposes.purposesAdded.filter(item => item.mucdich_id !== purposes[i].mucdich_id)
      }
    }
  },
  addPurposesToPurposesAdded (state, purposes) {
    if (purposes && purposes.length > 0) {
      for (let i = 0; i < purposes.length; i++) {
        if (!state.purposes.purposesAdded.find(item => item.mucdich_id === purposes[i].mucdich_id)) {
          state.purposes.purposesAdded.push(purposes[i])
        }

        state.purposes.purposesNotAdded = state.purposes.purposesNotAdded.filter(item => item.mucdich_id !== purposes[i].mucdich_id)
      }
    }
  },
  resetPurposes (state, data) {
    state.purposes.purposesAdded = []
    state.purposes.purposesNotAdded = []
  }
}
