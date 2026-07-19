export const mutations = {
  setDsModule (state, dsModule) {
        state.dsModule = dsModule
    },
    setModule (state, chiTietModule) {
        state.chiTietModule = chiTietModule
    },
    setModuleNew (state, moduleNew) {
        state.moduleNew = moduleNew
    },
    clearState (state) {
        state.dsModule = null,
        state.chiTietModule=null,
        state.moduleNew=null
    }
}
