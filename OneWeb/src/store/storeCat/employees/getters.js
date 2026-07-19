export const getters = {
  getEmployees (state, getters, rootState) {
    return state.employees.employees
  },
  getEmployeesNotAdded (state, getters, rootState) {
    return state.employees.employeesNotAdded
  },
  getEmployeesAdded (state, getters, rootState) {
    return state.employees.employeesAdded
  }
}
