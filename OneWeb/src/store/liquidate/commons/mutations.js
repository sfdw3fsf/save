export const mutations = {
  setListTelecomService (state, data) {
    let list = data.data
    list.sort((a, b) => a.DICHVUVT_ID - b.DICHVUVT_ID)
    state.listTelecomService = list
  },
  setListSubscriptionType (state, data) {
    state.listSubscriptionType = data.data
  },
  setListRequestType (state, data) {
    state.listRequestType = data
  },
  setListChanelSpeed (state, data) {
    state.listChanelSpeed = data.data
  },
  setListSatellite (state, data) {
    state.listSatellite = data.data
  },
  setListContract (state, data) {
    state.listContract = data.data
  },
  setDsThueBao (state, data) {
    state.dsThueBao = data
  },
  setDsLyDoHuy (state, data) {
    state.dsLyDoHuy = data
  },
  setDsNhomHuy (state, data) {
    state.dsNhomHuy = data
  }
}
