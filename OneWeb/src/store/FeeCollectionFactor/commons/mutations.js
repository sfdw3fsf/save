export const mutations = {
  setFeeList (state, feeList) {
    state.feeCollectionFactor.feeList = feeList
  },
  setEmployeeTypesNotAdd (state, employeeTypes) {
    state.feeCollectionFactor.employeeTypesNotAdd = employeeTypes
  },
  setEmployeeTypesAdded (state, employeeTypes) {
    state.feeCollectionFactor.employeeTypesAdded = employeeTypes
  },
  setInvoicePeriodsAdded (state, invoicePeriods) {
    state.feeCollectionFactor.invoicePeriodsAdded = invoicePeriods
  },
  setInvoicePeriodsNotAdd (state, invoicePeriods) {
    state.feeCollectionFactor.invoicePeriodsNotAdd = invoicePeriods
  },
  setSelectedFeeFactor (state, feeFactor) {
    state.feeCollectionFactor.selectedFeeFactor = feeFactor
  },
  setFeeTypes (state, feeTypes) {
    state.feeCollectionFactor.feeTypes = feeTypes
  },
  addInvoicePeriodsToAddedList (state, invoicePeriods) {
    if (invoicePeriods && invoicePeriods.length > 0) {
      for (let i = 0; i < invoicePeriods.length; i++) {
        if (!state.feeCollectionFactor.invoicePeriodsAdded.find(item => item.id === invoicePeriods[i].id)) {
          state.feeCollectionFactor.invoicePeriodsAdded.push(invoicePeriods[i])
        }

        state.feeCollectionFactor.invoicePeriodsNotAdd = state.feeCollectionFactor.invoicePeriodsNotAdd.filter(item => item.id !== invoicePeriods[i].id)
      }
    }
  },
  removeInvoicePeriodsFromList (state, invoicePeriods) {
    if (invoicePeriods && invoicePeriods.length > 0) {
      for (let i = 0; i < invoicePeriods.length; i++) {
        if (!state.feeCollectionFactor.invoicePeriodsNotAdd.find(p => p.id === invoicePeriods[i].id)) {
          state.feeCollectionFactor.invoicePeriodsNotAdd.push(invoicePeriods[i])
        }

        state.feeCollectionFactor.invoicePeriodsAdded = state.feeCollectionFactor.invoicePeriodsAdded.filter(item => item.id !== invoicePeriods[i].id)
      }
    }
  },
  addEmployeesToAddedList (state, employees) {
    if (employees && employees.length > 0) {
      for (let i = 0; i < employees.length; i++) {
        if (!state.feeCollectionFactor.employeeTypesAdded.find(item => item.loainv_id === employees[i].loainv_id)) {
          state.feeCollectionFactor.employeeTypesAdded.push(employees[i])
        }

        state.feeCollectionFactor.employeeTypesNotAdd = state.feeCollectionFactor.employeeTypesNotAdd.filter(item => item.loainv_id !== employees[i].loainv_id)
      }
    }
  },
  removeEmployeesFromAddedList (state, employees) {
    if (employees && employees.length > 0) {
      for (let i = 0; i < employees.length; i++) {
        if (!state.feeCollectionFactor.employeeTypesNotAdd.find(p => p.loainv_id === employees[i].loainv_id)) {
          state.feeCollectionFactor.employeeTypesNotAdd.push(employees[i])
        }

        state.feeCollectionFactor.employeeTypesAdded = state.feeCollectionFactor.employeeTypesAdded.filter(item => item.loainv_id !== employees[i].loainv_id)
      }
    }
  }
}
