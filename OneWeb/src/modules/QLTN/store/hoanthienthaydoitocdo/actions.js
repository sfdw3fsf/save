export const actions = {    
    setInitParamsActions({ commit, state }, data) {
        commit("setInitParams", data || []);        
    }
}
