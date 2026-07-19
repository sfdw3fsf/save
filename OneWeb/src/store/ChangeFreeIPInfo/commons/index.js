import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  subcriberInfo: {

    // general information
    subcriberCode: null,
    subcriberName: '',
    address: '',
    selectedSubcriberType: 0,
    subcriberTypes: [],
    selectedOldSpeed: 0,
    oldSpeeds: [],
    subCriberId: null,

    // fee information
    subcriberFee: null,
    totalFee: null,
    dateDecrease: '',
    momentFormat: '',
    ipStaticFree: '',
    dateDecreaseIp: '',
    emailQtyMoreRent: null,
    emailDecreaseRatio: null,
    emailDecreaseTime: null,
    capacityFree: null,
    discount: null,
    packageFee: null,
    discountRatio: null,
    monthsApplyDiscount: null,
    ipDecreaseRatio: null,
    monthsIpDecrease: null,
    pricePerEmail: null,
    decreaseEmailStartDate: '',
    pricePerMB: null,
    freeFee: null,
    note: ''
  }
}

const namespaced = true

export const changeFreeIPInfoCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
