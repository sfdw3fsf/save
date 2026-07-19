import ReceiveProfileAPI from '@/modules/contract/profile/receiveProfile/ReceiveProfileAPI'

export const actions = {
  async getListStatus ({ commit, state }) {
    let response = await ReceiveProfileAPI.getStatus()
    try {
      commit('setListStatus', response.data)
    } catch (error) {
      throw error
    }
  },
  async getListGroupFile ({ commit, state }) {
    let response = await ReceiveProfileAPI.getGroupFile()
    try {
      console.log('response', response)
      commit('setListGroupFile', response.data)
    } catch (error) {
      throw error
    }
  },
  async getListTypeFile ({ commit, state }) {
    let response = await ReceiveProfileAPI.getTypeFile()
    try {
      commit('setListTypeFile', response.data)
    } catch (error) {
      throw error
    }
  },
  async getListAgency ({ commit, state }) {
    let response = await ReceiveProfileAPI.getAgency()
    try {
      commit('setListAgency', response.data)
    } catch (error) {
      throw error
    }
  },
  async getListEmp ({ commit, state }, data) {
    let response = await ReceiveProfileAPI.getEmp(data)
    try {
      commit('setListEmp', response.data)
    } catch (error) {
      throw error
    }
  },
  async getListReason ({ commit, state }) {
    let response = await ReceiveProfileAPI.getReason()
    try {
      commit('setListReason', response.data)
    } catch (error) {
      throw error
    }
  },
  async getListAssign ({ commit, state }, data) {
    let response = await ReceiveProfileAPI.getAssignList(data)
    try {
      commit('setListAssign', response.data)
    } catch (error) {
      throw error
    }
  },
  async getListAppointment ({ commit, state }, data) {
    let response = await ReceiveProfileAPI.getAppointmentList(data)
    try {
      commit('setListAppointment', response.data)
    } catch (error) {
      throw error
    }
  }
}
