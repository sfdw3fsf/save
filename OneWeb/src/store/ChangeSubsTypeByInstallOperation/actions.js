import api from "@/modules/contract/setup/ChangeSubsTypeByInstallOperation/api";

export const actions = {
  async fetchDanhSachHopDong({ commit, state }, variables) {
    commit("setIsLoading", true);
    try {
      const res = await api.getDanhSachHopDongTheoMaGiaoDich(
        this._vm.axios,
        variables
      );
      commit("setHopDongListByMaGiaoDich", res.data.data);
    } catch (error) {
      throw error;
    }
    commit("setIsLoading", false);
  },

  async getDichVuList({ commit, state }) {
    try {
      const res = await api.getDichVuList(this._vm.axios);
      commit("setDichVuList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async getLoaiHinhList({ commit, state }) {
    try {
      const res = await api.getLoaiHinhList(this._vm.axios);
      commit("setLoaiHinhList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async getDVTinhCuocList({ commit, state }, variables) {
    try {
      const res = await api.getDVTinhCuoc(this._vm.axios, variables);
      commit("setDVTinhCuocList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchKieuYeuCauList({ commit, state }, variables) {
    try {
      const res = await api.getKieuYeuCau(this._vm.axios, variables);
      commit("setKieuYeuCauList", res.data.data);
    } catch (error) {
      throw error;
    }
  },
  async fetchKieuYeuCauCoLoaiTB({ commit, state }, variables) {
    try {
      const res = await api.getKieuYeuCauCoLoaiTB(this._vm.axios, variables);
      commit("setKieuYeuCauList", res.data.data);
    } catch (error) {
      throw error;
    }
  },
  async fetchKieuYeuCauKhongCoLoaiTB({ commit, state }, variables) {
    try {
      const res = await api.getKieuYeuCauKhongCoLoaiTB(
        this._vm.axios,
        variables
      );
      commit("setKieuYeuCauList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchTocDoList({ commit, state }) {
    try {
      const res = await api.getTocDo(this._vm.axios, {});
      commit("setTocDoList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchMucCuocList({ commit, state }) {
    try {
      const res = await api.getMucCuoc(this._vm.axios);
      commit("setMucCuocList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchLoaiThietBiList({ commit, state }, variables) {
    try {
      const res = await api.getLoaiThietBi(this._vm.axios, variables);
      commit("setLoaiThietBiList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchBrasList({ commit, state }) {
    try {
      const res = await api.getBras(this._vm.axios, {});
      commit("setBrasList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchDslamList({ commit, state }, variables) {
    try {
      const res = await api.getDslam(this._vm.axios, variables);
      commit("setDslamList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchInformationHopDongThueBaoGP({ commit, state }, variables) {
    try {
      const res = await api.getInformationHopDongThueBaoGP(
        this._vm.axios,
        variables
      );
      console.log("res 123:", res);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async checkMaSoTBKhoSo({ commit, state }, variables) {
    try {
      const res = await api.checkMaSoThueBaoKhoSo(this._vm.axios, variables);
      return res.data.message;
    } catch (error) {
      throw error;
    }
  },

  async checkMaSoTB({ commit, state }, variables) {
    try {
      const res = await api.checkMaSoThueBao(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async saveHoSoLoaiHinhThueBao({ commit, state }, variables) {
    try {
      const res = await api.ghiLaiHoSoLoaiHinhThueBao(
        this._vm.axios,
        variables
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async completedHoSoLoaiHinhThueBao({ commit, state }, variables) {
    try {
      const res = await api.hoanThienHoSoLoaiHinhThueBao(
        this._vm.axios,
        variables
      );
      return res.data.message;
    } catch (error) {
      throw error;
    }
  },

  // lấy thông tin Khách hàng khi enter mã giao dịch
  async LAY_DSHD_THEO_MA_GD({ commit, state }, variables) {
    try {
      const res = await api.api_LAY_DSHD_THEO_MA_GD(this._vm.axios, variables);
      commit("setHopDongListByMaGiaoDich", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },
  // lấy thông tin thuê bao từ thông tin KH
  async LAY_DS_HDTB_THEO_HDKH_TTHD({ commit, state }, variables) {
    try {
      const res = await api.api_LAY_DS_HDTB_THEO_HDKH_TTHD(
        this._vm.axios,
        variables
      );
      commit("setHopDongListByMaGiaoDich", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // LAY_DS_HDTB_HTHS
  async LAY_DS_HDTB_HTHS({ commit, state }, variables) {
    try {
      const res = await api.api_LAY_DS_HDTB_HTHS(this._vm.axios, variables);
      commit("setHopDongListByMaGiaoDich", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách loại hình( loại thuê bao) theo dịch vụ
  async HT_LoaiHinh_TB_Combobox({ commit, state }, variables) {
    try {
      const res = await api.api_HT_LoaiHinh_TB_Combobox(
        this._vm.axios,
        variables
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },
  // lấy danh sách kiểu yêu cầu theo loại hình - khi ko có data loại hình
  async HT_Kieu_LD_Combobox_No_LoaiTb_Id({ commit, state }, variables) {
    try {
      const res = await api.api_HT_Kieu_LD_Combobox_No_LoaiTb_Id(
        this._vm.axios,
        variables
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },
  // lấy danh sách kiểu yêu cầu theo loại hình - khi ko có data loại hình
  async HT_Kieu_LD_Combobox_Have_LoaiTb_Id({ commit, state }, variables) {
    try {
      const res = await api.api_HT_Kieu_LD_Combobox_Have_LoaiTb_Id(
        this._vm.axios,
        variables
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách loại kênh
  async HT_Loai_Kenh_Combobox({ commit, state }) {
    try {
      const res = await api.api_HT_Loai_Kenh_Combobox(this._vm.axios);
      commit("setLoaiKenhList", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách thời hạn
  async HT_ThoiHan_MegaWan_Combobox({ commit, state }) {
    try {
      const res = await api.api_HT_ThoiHan_MegaWan_Combobox(this._vm.axios);
      commit("setThoiHanList", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách Modem
  async HT_Loai_Modem_Combobox({ commit, state }) {
    try {
      const res = await api.api_HT_Loai_Modem_Combobox(this._vm.axios);
      commit("setModemList", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách tốc độ
  async HT_Tocdo_Kenh_Combobox({ commit, state }) {
    try {
      const res = await api.api_HT_Tocdo_Kenh_Combobox(this._vm.axios);
      commit("setTocDoKenhList", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách trang bị
  async HT_TrangBi_MegaWan_Combobox({ commit, state }) {
    try {
      const res = await api.api_HT_TrangBi_MegaWan_Combobox(this._vm.axios);
      commit("setTrangBiList", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách chủ quan
  async HT_ChuQuan_MegaWan_Combobox({ commit, state }) {
    try {
      const res = await api.api_HT_ChuQuan_MegaWan_Combobox(this._vm.axios);
      commit("setChuQuanList", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async LAY_DS_HOPDONG_THEO_MA_TB({ commit, state }, variables) {
    try {
      const res = await api.apiLAY_DS_HOPDONG_THEO_MA_TB(
        this._vm.axios,
        variables
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async LAY_DS_HDTB_ADSL_THEO_HDTB_ID({ commit, state }, hdtbId) {
    try {
      const res = await api.apiLAY_DS_HDTB_ADSL_THEO_HDTB_ID(
        this._vm.axios,
        hdtbId
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async lay_ds_hdtb_GP_theo_hdtb_id({ commit, state }, hdtbId) {
    try {
      const res = await api.api_lay_ds_hdtb_GP_theo_hdtb_id(
        this._vm.axios,
        hdtbId
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async LAY_DS_HDTB_MGWAN_THEO_HDTB_ID({ commit, state }, hdtbId) {
    try {
      const res = await api.apiLAY_DS_HDTB_MGWAN_THEO_HDTB_ID(
        this._vm.axios,
        hdtbId
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async HT_ROUTER_VTN_COMBOBOX({ commit, state }) {
    try {
      const res = await api.apiHT_ROUTER_VTN_COMBOBOX(this._vm.axios, {});
      console.log("RES:", res);
      commit("setRouterList", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  }
};
