import api from "@/modules/admin/subscriber/DSLAMTransfer/api";

const TRANG_THAI_DEFAULT = 0;

export const actions = {
  async initializeInformationBrasList({ commit, state }) {
    try {
      const res = await api.getInformationBrasList(this._vm.axios);
      commit("setInformationBrasList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async initializeInformationDslamList({ commit, state }, bras_id) {
    try {
      const res = await api.getInformationDslamList(this._vm.axios, {
        bras_id
      });
      if(res.data && res.data.data){
        commit("setInformationDslamList", res.data.data);
      }

    } catch (error) {
      throw error;
    }
  },

  async initializeInformationDslamListVDC({ commit, state }, bras_id) {
    try {
      const res = await api.getInformationDslamVDCList(this._vm.axios, {
        bras_id: bras_id
      });
      // this.informationDslamListVDC = res.data.data;
      commit("setInformationDslamVDCList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async initializeInformationNewBrasList({ commit, state }) {
    try {
      const res = await api.getInformationNewBrasList(this._vm.axios);
      commit("setInformationNewBrasList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async initializeInformationNewDslamListVDC({ commit, state }, bras_id) {
    try {
      const res = await api.getInformationNewDslamVDCList(this._vm.axios, {
        bras_id: bras_id
      });
      commit("setInformationNewDslamVDCList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchDanhSachThueBao({ commit, state }, trangthai) {
    try {
      commit("setIsLoading", true);
      const res = await api.getThueBaoList(this._vm.axios, {
        trangthai: trangthai
      });
      commit("setDanhSachThueBao", res.data.data);
      commit("setIsLoading", false);
    } catch (error) {
      throw error;
    }
  },

  setIsChooseDsHoanThien({ commit, state }, trangthai) {
    commit("setIsChooseDsHoanThien", trangthai);
  },

  async saveBras({ commit, state }, params) {
    try {
      const res = await api.saveBras(this._vm.axios, {
        is_insert: params.is_insert,
        dslam_id: params.dslam_id,
        bras_id: params.bras_id,
        dslam_cha_id: params.dslam_cha_id,
        bras_moi_id: params.bras_moi_id,
        dslam_moi_id: params.dslam_moi_id,
        trangthai: params.trangthai
      });
      return res;
    } catch (error) {
      // return null;
      throw error;
    }
  },

  async deleteBras({ commit, state }, idRecord) {
    try {
      await api.deleteBras(this._vm.axios, {
        // data: [{ DSLAM_ID: idRecord }]
        data: idRecord
      });
    } catch (error) {
      throw error;
    }
  }
};
