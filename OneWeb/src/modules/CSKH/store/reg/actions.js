import TaoCongViecCSKHApi from '@/modules/CSKH/api/TaoCongViecCSKHApi'

export const actions = {
  setSMTPInfor({ commit, state }, data) {
    commit('setSMTPInfor', data)
  },

  clearData({ commit, state }, data) {
    commit('clearData', data)
  },

}