import api from "@/modules/admin/category/Project/api";

export const actions = {
  async setLoaiDonVi({ commit, state }, loai_dv){
    try {
      commit("setLoaiDonVi", loai_dv);
    } catch (error) {
      console.log(error);
    }
  },
  async fetchLoaiDonViList({ commit, state }) {
    const params = {
      "p_type": 1
    }
    try {
      // const res = await api.getChuTruongList(this._vm.axios, variables);
      const res = await api.getLoai_DonVi(this._vm.axios, params);
      commit("setDsLoaiDonVi", res.data.data);
    } catch (error) {
      throw error;
    }
  },
  async fetchChuTruongList({ commit, state }) {
    // const variables = {
    //   schema: "QLVT",
    //   table_name: "CHUTRUONG",
    //   list_of_cols: "chutruong_id, chutruong",
    //   where: "",
    //   order: "chutruong_id desc"
    // };
    const params ={
      "p_param": "DANHMUC_CHUTRUONG"   //fix SQL injection
    }
    try {
      // const res = await api.getChuTruongList(this._vm.axios, variables);
      const res = await api.getSubjectsList(this._vm.axios, params);
      
      commit("setChuTruongList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchDanhSachDuAn({ commit, state }) {
    commit("setIsLoadingDsDuAn", true);
    try {
      const variables = {
        schema: "CABMAN",
        table_name: "DUAN",
        list_of_cols:
          "PHANVUNG_ID,DUAN_ID,SOHIEU_CT,MA_CT,SOQD_DT,SOQD_QT,NGAY_NT,CHUTRUONG_ID,CONGNANG,GHICHU,NGAY_DT,DUAN_IMS_ID,DONGBO,IP_CN,MAY_CN,NGAY_CN,NGUOI_CN",
        where: "",
        order: "NGAY_NT DESC"
      };
      const res = await api.getDanhSachDuAn(this._vm.axios, variables);
      commit("setDanhSachDuAn", res.data.data);
    } catch (error) {
      throw error;
    }
    commit("setIsLoadingDsDuAn", false);
  },

  async fetchDanhSachDonViDaGan({ commit, state }, variables) {
    commit("setIsLoadingDsDonViDaGan", true);
    try {
      const res = await api.getDanhSachDonViDaGan(this._vm.axios, variables);
      commit("setDanhSachDonViDaGan", res.data.data);
    } catch (error) {
      throw error;
    }
    commit("setIsLoadingDsDonViDaGan", false);
  },

  async fetchDanhSachDonViChuaGan({ commit, state }, variables) {
    commit("setIsLoadingDsDonViChuaGan", true);
    try {
      const res = await api.getDanhSachDonViChuaGan(this._vm.axios, variables);
      commit("setDanhSachDonViChuaGan", res.data.data);
    } catch (error) {
      throw error;
    }
    commit("setIsLoadingDsDonViChuaGan", false);
  },

  async saveUpdateProject({ commit, state }, variables) {
    try {
      const res = await api.saveProject(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async removeProject({ commit, state }, variables) {
    try {
      const res = await api.deleteProject(this._vm.axios, variables);
      return res.data;
    } catch (error) {
      throw error;
    }
  },

  async ganDonVi({ commit, state }, variables) {
    try {
      const res = await api.addDonVi(this._vm.axios, variables);
      return res.data;
    } catch (error) {
      throw error;
    }
  },

  async boGanDonVi({ commit, state }, variables) {
    try {
      const res = await api.removeDonVi(this._vm.axios, variables);
      return res.data;
    } catch (error) {
      throw error;
    }
  },

  setSelecteItemsDonViDaGan({ commit, state }, variables) {
    commit("setItemsDonViDaGan", variables);
  },


  //fix Security SQL Injecttion
  async fetchListProjects({commit}){
    commit("setIsLoadingDsDuAn", true);
   
  try {
    const variables = {
      p_param: "DANHMUC_DUAN"
    };
    const res = await api.getListProjects(this._vm.axios, variables);
    commit("setDanhSachDuAn", res.data.data);
  } catch (error) {
    throw error;
  }
  commit("setIsLoadingDsDuAn", false);}
};
