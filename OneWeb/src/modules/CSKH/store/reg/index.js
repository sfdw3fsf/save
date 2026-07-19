import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  SMTP_CSKH_SERVER: '',
  SMTP_CSKH_USERNAME: '',
  SMTP_CSKH_PASSWORD: '',
  SMTP_CSKH_PORT: '',
  SMTP_ALIAS: '',

  SMTP_CSKH_DCLH: '',
  SMTP_CSKH_EMAIL: '',
  SMTP_CSKH_SDT: ''
}

const namespaced = true

export const reg = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
