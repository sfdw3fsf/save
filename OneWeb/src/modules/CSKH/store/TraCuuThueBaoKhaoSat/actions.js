import API from "@/modules/CSKH/api/TraCuuThueBaoKhaoSatApi";

export const actions = {
  async clearData({ commit, state }) {
    commit("clearData");
  },
  async getDsCauHoi({ commit, state }, data) {
    try {
      let response = await API.getDsCauHoi(this._vm.axios, data);
      let ds = response.data.data || [];
      let parentIds = ds.map(x => x.PARENT_ID);

      ds.forEach(element => {
        if (parentIds.includes(element.ID)) {
          element.hasChild = true;
          element.expanded = false;
        }
      });

      let dsDVKoDonViCha = ds.filter(x => !x.PARENT_ID);
      if (dsDVKoDonViCha.length > 0) {
        ds.push({
          ID: "0",
          NOIDUNG: "Câu hỏi",
          PARENT_ID: null,
          CAUHOI_ID: 0,
          TRALOI_ID: 0,
          hasChild: true,
          expanded: true
        });
        ds.filter(x => !x.PARENT_ID && x.ID != "0").forEach(
          x => (x.PARENT_ID = 0)
        );
      }
      commit("setDsCauHoi", ds);
      return response;
    } catch (error) {
      commit("setDsCauHoi", []);
      return error.response.data;
    }
  },
  async getDsThueBaoKS({ commit, state }, data) {
    try {
      let response = await API.getDsThueBaoKS(this._vm.axios, data)
      let ds = response.data.data.data || []
      ds.forEach((e, i) => {
        e.STT = i + 1
        e.FILENAME = e.FILENAME == 0 ? '' : e.FILENAME
      })
      commit("setDsThueBaoKS", ds);
      return response;
    } catch (error) {
      commit("setDsThueBaoKS", []);
      this._vm.$toast.error(error.data.message)
      console.log(error)
    }
  },
  async getDsLyDoKTL({ commit, state }, data) {
    try {
      let response = await API.getDsLyDoKTL(this._vm.axios, data)
      let ds = response.data.data || []
      commit("setDsLyDoKTL", ds);
      return response;
    } catch (error) {
      commit("setDsLyDoKTL", []);
      return error.response.data
    }
  },
};
