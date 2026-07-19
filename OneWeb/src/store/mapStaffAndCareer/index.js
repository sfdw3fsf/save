import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  selectedUnit: null,
  selectedTypeOfStaff: null,
  selectedTypeOfArea: null,

  staffCode: '',
  staffList: [],
  isLoadingStaffList: false,

  selectedStaff: null,

  isLoadingCareerMappedList: false,
  careerMappedList: [],

  isLoadingCareerNotMappedList: false,
  careerNotMappedList: []
}

const namespaced = true

export const mapStaffAndCareer = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
