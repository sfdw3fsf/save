import API from "../../api/LoaiVTAPI";

export const actions = {
  async getDSNhomVT({ commit, state },) {
    let response = await API.getDSNhomVT(this._vm.axios,)
    try {
        return response.data.data || []
    } catch (error) {
        throw error
    }
  },
  async getDSLoaiVT({ commit, state },data) {
    let response = await API.getDSLoaiVT(this._vm.axios,data)
    try {
        return response.data.data || []
    } catch (error) {
        throw error
    }
  },
  async getDSCboLoaiVT({ commit, state },data) {
    let response = await API.getDSCboLoaiVT(this._vm.axios,data)
    try {
        return response.data.data || []
    } catch (error) {
        throw error
    }
  },
  async capNhatLoaiVT({ commit, state },data) {
    let response = await API.capNhatLoaiVT(this._vm.axios,data)    
    return response.data.data    
  },
};
