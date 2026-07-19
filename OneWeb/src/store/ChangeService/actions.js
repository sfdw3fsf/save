import api from "@/modules/contract/setup/ChangeService/api";
import { data } from "jquery";

export const actions = {
  async fetchDichVuList({ commit, state }) {
    try {
      const res = await api.getDichVuList(this._vm.axios);
      commit("setDichVuList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchLoaiHinhList({ commit, state }) {
    try {
      const res = await api.getLoaiHinhList(this._vm.axios);
      commit("setLoaiHinhList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // ds kiểu yêu cầu khi ko có số máy
  async fetchYeuCauList({ commit, state }) {
    try {
      const res = await api.getKieuYeuCauList(this._vm.axios);
      commit("setKieuYeuCauList", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // ds kiểu yêu cầu khi có số máy
  async fetchKieuYeuCauListLD_BC({ commit, state }, variables) {
    try {
      const res = await api.getKieuYeuCauListLD_BC(this._vm.axios, variables);
      commit("setKieuYeuCauListLD_BC", res.data.data);
      commit("setKieuYeuCauList", res.data.data);
      console.log("res:", res.data);
    } catch (error) {
      throw error;
    }
  },

  async fetchDanhSachThueBao({ commit, state }) {
    const variables = {
      hdtb_id: 4
    };
    try {
      const res = await api.getDanhSachThueBao(this._vm.axios, variables);
      commit("setDsThueBao", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // 008 Lay_ds_tb_dvgt_theo_tt_ims_v2
  async Lay_ds_tb_dvgt_theo_tt_ims_v2({ commit, state }, variables) {
    try {
      const res = await api.getLay_ds_tb_dvgt_theo_tt_ims_v2(
        this._vm.axios,
        variables
      );
      commit("setDsDichVuGiaTang", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // 009 LAY_DS_THUEBAO_DICHVU_IMS_V2
  async LAY_DS_THUEBAO_DICHVU_IMS_V2({ commit, state }, variables) {
    try {
      const res = await api.getLAY_DS_THUEBAO_DICHVU_IMS_V2(
        this._vm.axios,
        variables
      );
      commit("setDsDichVuGiaTang", res.data.data);
    } catch (error) {
      throw error;
    }
  },
  async setDsDichVuGiaTang_new({ commit, state }, variables) {
    try {      
      commit("setDsDichVuGiaTang", variables);
    } catch (error) {
      throw error;
    }
  },
  // 010 LAY_DS_THUEBAO_DICHVU_LINHVUC_V2
  async LAY_DS_THUEBAO_DICHVU_LINHVUC_V2({ commit, state }, variables) {
    try {
      const res = await api.getLAY_DS_THUEBAO_DICHVU_LINHVUC_V2(
        this._vm.axios,
        variables
      );
      commit("setDsDichVuGiaTang", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // 011 LAY_DS_THUEBAO_DICHVU_V2
  async LAY_DS_THUEBAO_DICHVU_V2({ commit, state }, variables) {
    try {
      const res = await api.getLAY_DS_THUEBAO_DICHVU_V2(
        this._vm.axios,
        variables
      );
      if (res.data.data === null) {
        commit("setDsDichVuGiaTang", []);
      } else {
        commit("setDsDichVuGiaTang", res.data.data);
      }
    } catch (error) {
      throw error;
    }
  },

  // 012
  async lay_ds_thuebao_dichvu_v2({ commit, state }, variables) {
    try {
      const res = await api.getLay_ds_thuebao_dichvu_v2(
        this._vm.axios,
        variables
      );
      commit("setDsDichVuGiaTang", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // click số máy Acc
  async clickSoMaAcc({ commit, state }, variables) {
    try {
      const res = await api.soMayAcc(this._vm.axios, variables);
      commit("setDsSoMayAcc", res.data.data);
      console.log("res:", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // click mã giao dịch
  async clickMaGiaoDich({ commit, state }, variables) {
    try {
      const res = await api.maGiaoDich(this._vm.axios, variables);
      console.log("setDsMaGiaoDich:", res.data.data);
      commit("setDsMaGiaoDich", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách tổng tiền khoản mục
  async getTongTienKhoanMucList({ commit, state }, variables) {
    try {
      const res = await api.getLay_DS_TONG_TIEN_KHOAN_MUC(
        this._vm.axios,
        variables
      );
      commit("setDsTongTienKhoanMuc", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // lấy danh sách dịch vụ khác
  async getDanhSachDichVuKhac({ commit, state }, variables) {
    console.log("here");
    try {
      const res = await api.getLAY_DS_DICH_VU_KHAC(this._vm.axios, variables);

      // commit("setDsTongTienKhoanMuc", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  // xóa hợp đồng
  async deleteHD({ commit, state }, variables) {
    try {
      const res = await api.deleteHopDong(this._vm.axios, variables);
      return res.data.message;
    } catch (error) {
      throw error;
    }
  },

  // XÓA TB
  async xoaHopDongTB({ commit, state }, variables) {
    try {
      const res = await api.xoaHDTB(this._vm.axios, variables);
      return res.data.message;
    } catch (error) {
      throw error;
    }
  },
  // TaoDuLieu_HDKH - api MAP_ID
  async mapIDDuLieuHDKH({ commit, state }, variables) {
    try {
      const res = await api.mapID(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_HDTB_MGWAN - LAY_DS_DB_MEGAWAN_THEO_TBID
  async LAY_DS_DB_MEGAWAN_THEO_TBID({ commit, state }, hdtb_id) {
    try {
      const res = await api.apiLAY_DS_DB_MEGAWAN_THEO_TBID(
        this._vm.axios,
        hdtb_id
      );
      return res.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_HDTB_TSL - LAY_DS_DB_TSL_THEO_TBID
  async LAY_DS_DB_TSL_THEO_TBID({ commit, state }, hdtb_id) {
    try {
      const res = await api.apiLAY_DS_DB_TSL_THEO_TBID(this._vm.axios, hdtb_id);
      return res.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_HDTBDV
  async LAY_DS_HDTB_DV({ commit, state }, variables) {
    try {
      const res = await api.apiLAY_DS_HDTB_DV(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_HDTB_CD
  async LAY_DS_HDTB_DV({ commit, state }, variables) {
    try {
      const res = await api.apiLAY_DS_DB_CD_THEO_TBID(
        this._vm.axios,
        variables
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_HDTB_ADSL
  async LAY_DS_DB_ADSL_THEO_TBID({ commit, state }, hdtbId) {
    try {
      const res = await api.apiLAY_DS_DB_ADSL_THEO_TBID(this._vm.axios, hdtbId);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_HDTB_IMS
  async LAY_DS_DB_IMS_THEO_TBID({ commit, state }, hdtbId) {
    try {
      const res = await api.apiLAY_DS_DB_IMS_THEO_TBID(this._vm.axios, hdtbId);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_HDTB_GP
  async LAY_DS_DB_GP_THEO_TBID({ commit, state }, hdtbId) {
    try {
      const res = await api.apiLAY_DS_DB_GP_THEO_TBID(this._vm.axios, hdtbId);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_HDTB_CNTT
  async LAY_DS_DB_CNTT_THEO_TBID({ commit, state }, hdtbId) {
    try {
      const res = await api.apiLAY_DS_DB_CNTT_THEO_TBID(this._vm.axios, hdtbId);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // TaoDuLieu_BD_LYDOHUY
  async LAY_DS_DB_LYDOHUY_THEO_TBID({ commit, state }, hdtbId) {
    try {
      const res = await api.apiLAY_DS_DB_LYDOHUY_THEO_TBID(
        this._vm.axios,
        hdtbId
      );
      console.log("LAY_DS_DB_LYDOHUY_THEO_TBID:", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // Map ID
  async MAP_ID({ commit, state }, data) {
    try {
      const res = await api.apiMapId(this._vm.axios, data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // apiMapIdHuy
  async MAP_ID_HUY({ commit, state }, data) {
    try {
      const res = await api.apiMapIdHuy(this._vm.axios, data);
      console.log("apiMapIdHuy:", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // Map ID
  async Kiemtra_no_thanhly_yc({ commit, state }, data) {
    try {
      const res = await api.apiKiemTraNoThanhLyYC(this._vm.axios, data);
      console.log("Kiemtra_no_thanhly_yc:", res.data.data);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // nút ghi lại
  async ghiLaiHopDongTB({ commit, state }, variables) {
    try {
      const res = await api.ghiLaiHDTB(this._vm.axios, variables);
      return res.data.data.js_returnds;
    } catch (error) {
      throw error;
    }
  },

  // nút thêm tb
  async themMoiTB({ commit, state }, variables) {
    try {
      const res = await api.apiThemTB(this._vm.axios, variables);
      return res.data.data.js_returnds;
    } catch (error) {
      throw error;
    }
  },

  // HienThiTT_DanhBa
  async LAY_DANHBA_THEO_MATB({ commit, state }, variables) {
    try {
      const res = await api.apiLAY_DANHBA_THEO_MATB(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // apiGetKeys
  async getKeys({ commit, state }, variables) {
    try {
      console.log("variables getKeys:", variables);
      const res = await api.apiGetKeys(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // sinh mã giao dịch
  async Lay_Ma_GD_V2({ commit, state }, variables) {
    try {
      const res = await api.apiLay_Ma_GD_V2(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },
  // LayHuongGiao_TiepNhan
  async LayHuongGiao_TiepNhan({ commit, state }, variables) {
    try {
      const res = await api.apiLayHuongGiao_TiepNhan(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async Kiemtra_tb_mytv_chinhphu({ commit, state }, variables) {
    try {
      const res = await api.kiemTraMyTVChinhPhu(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async LAY_DS_THAMSO_MD_MATS({ commit, state }, variables) {
    try {
      const res = await api.layDsThamSo_Md_Mats(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  async LAY_DS_HOPDONG_TB_THEO_HDKH_ID({ commit, state }, variables) {
    try {
      const res = await api.apiLAY_DS_HOPDONG_TB_THEO_HDKH_ID(
        this._vm.axios,
        variables
      );
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },

  // LAY_DS_DK_LYDOHUY
  async LAY_DS_DK_LYDOHUY({ commit, state }, variables) {
    try {
      const res = await api.apiLAY_DS_DK_LYDOHUY(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  },
  async lay_ds_ly_do({ commit, state }, variables) {
    try {
      const res = await api.dsLyDo(this._vm.axios, variables);
      return res.data.data;
    } catch (error) {
      throw error;
    }
  }
};
