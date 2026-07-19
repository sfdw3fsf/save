export const mutations = {
  setEmployees (state, data) {
    state.employees.employees = data
  },
  setEmployeesNotAdded (state, data) {
    state.employees.employeesNotAdded = data
  },
  setEmployeesAdded (state, data) {
    state.employees.employeesAdded = data
  },
  addEmployeesToEmployeesAdded (state, data) {
    state.employees.employeesAdded.unshift(...data)
    for (let i = 0; i < data.length; i++) {
      state.employees.employeesNotAdded = state.employees.employeesNotAdded.filter(k => data[i].nhanvien_id !== k.nhanvien_id)
    }
  },
  // removeEmployeesFromEmployeesNotAdded (state, data) {
  //   for (let i = 0; i < data.length; i++) {
  //     state.employees.employeesNotAdded = state.employees.employeesNotAdded.filter(k => data[i].nhanvien_id !== k.nhanvien_id)
  //   }
  // },
  // addEmployeesToEmployeesNotAdded (state, data) {
  //   state.employees.employeesNotAdded.unshift(...data)
  // },
  removeEmployeesFromEmployeesAdded (state, data) {
    state.employees.employeesNotAdded.unshift(...data)
    for (let i = 0; i < data.length; i++) {
      state.employees.employeesAdded = state.employees.employeesAdded.filter(k => data[i].nhanvien_id !== k.nhanvien_id)
    }
  },
  resetEmployees (state, data) {
    state.employees.employeesNotAdded = []
    state.employees.employeesAdded = []
  }
}
