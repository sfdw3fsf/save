export const getters = {
  getFeeList (state, getters, rootState) {
    return state.feeCollectionFactor.feeList
  },
  getEmployeeTypesAdded (state, getters, rootState) {
    return state.feeCollectionFactor.employeeTypesAdded
  },
  getEmployeeTypesNotAdd (state, getters, rootState) {
    return state.feeCollectionFactor.employeeTypesNotAdd
  },
  getInvoicePeriodsAdded (state, getters, rootState) {
    return state.feeCollectionFactor.invoicePeriodsAdded
  },
  getInvoicePeriodsNotAdd (state, getters, rootState) {
    return state.feeCollectionFactor.invoicePeriodsNotAdd
  },
  getSelectedFeeFactor (state, getters, rootState) {
    return state.feeCollectionFactor.selectedFeeFactor
  },
  getFeeTypes (state, getters, rootState) {
    return state.feeCollectionFactor.feeTypes
  }
}
