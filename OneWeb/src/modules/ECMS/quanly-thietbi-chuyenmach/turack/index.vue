<template src="./index.template.html"></template>
<script>
import { required, numeric, minValue } from "vuelidate/lib/validators";
export default {
  data: function () {
    return {
      currentItem: {},
      dsTTVT: [],
      selectedTTVT: 0,
      dsToKT: [],
      selectedToKT: 0,
      dsTram: [],
      selectedTram: 0,
      dsLoaiTu: [],
    };
  },
  computed: {
    loaiTuInvalidStatus: function () {
      if (this.currentItem.TURACK_ID != undefined) {
        return this.$v.currentItem.LOAITU_ID.$invalid;
      } else return false;
    },
    tenTuInvalidStatus: function () {
      if (this.currentItem.TURACK_ID != undefined) {
        return this.$v.currentItem.TEN_TU.$invalid;
      } else return false;
    },
    viTriInvalidStatus: function () {
      if (this.currentItem.TURACK_ID != undefined) {
        return this.$v.currentItem.VITRI.$invalid;
      } else return false;
    },
    enabledSave: function () {
      return this.currentItem.TURACK_ID != undefined;
    },
    enabledNew: function () {
      return this.currentItem.TURACK_ID > 0;
    },
    enabledDelete: function () {
      return !(
        this.currentItem.TURACK_ID == 0 ||
        this.currentItem.TURACK_ID == undefined
      );
    },
  },
  validations: {
    currentItem: {
      TEN_TU: {
        required,
      },
      VITRI: {
        required,
        numeric,
        minValue: minValue(0),
      },
    },
  },
  methods: {
    getDsTTVT: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/danhmuc/layDsTTVTTrongToken"
      );
      return rs.data;
    },
    getDsToKT: async function () {
      let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsToKT", {
        id: this.selectedTTVT,
      });
      return rs.data;
    },
    getDsTram: async function () {
      let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsVeTinh", {
        id: this.selectedToKT,
      });
      return rs.data;
    },
    getDonVi: async function (donViID) {
      let rs = await this.$root.context.post(
        "/web-ecms/danhmuc/layThongTinDonVi",
        {
          id: donViID,
        }
      );
      return rs.data;
    },
    getDsLoaiTu: async function () {
      let rs = await this.$root.context.get(
        "/web-ecms/danhmuc/loaitu/lietke_danhsach"
      );
      return rs.data;
    },
    bindTTVT: async function () {
      this.dsTTVT = await this.getDsTTVT();
      if (!(this.dsTTVT == null || this.dsTTVT.length == 0)) {
        this.selectedTTVT = this.dsTTVT[0].DONVI_ID;
      } else this.selectedTTVT = 0;
      await this.bindToKT();
    },
    bindToKT: async function () {
      this.dsToKT = await this.getDsToKT();
      if (!(this.dsToKT == null || this.dsToKT.length == 0)) {
        this.selectedToKT = this.dsToKT[0].DONVI_ID;
      } else this.selectedToKT = 0;
      await this.bindTram();
    },
    bindTram: async function () {
      this.dsTram = await this.getDsTram();
    },
    bindLoaiTu: async function () {
      this.dsLoaiTu = await this.getDsLoaiTu();
      if (!(this.dsLoaiTu == null || this.dsLoaiTu.length == 0)) {
        this.currentItem.LOAITU_ID = this.dsLoaiTu[0].LOAITU_ID;
      } else this.currentItem.LOAITU_ID = 0;
    },
    layTuRack: async function (tuRackID) {
      let result = await this.$root.context.get(
        "/web-ecms/danhmuc/turack/chitiet",
        {
          turack_id: tuRackID,
        }
      );
      return result.data;
    },
    xoaTuRack: async function (id) {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/turack/xoa",
        {
          id: id,
        }
      );
      return result.data;
    },
    capNhatTuRack: async function (itemData) {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/turack/capnhat",
        {
          donvi_id: itemData.DONVI_ID,
          kieutu: itemData.KIEUTU,
          loaitu_id: itemData.LOAITU_ID,
          ten_tu: itemData.TEN_TU,
          turack_id: itemData.TURACK_ID,
          vitri: itemData.VITRI,
        }
      );
      return result.data;
    },
    themTuRack: async function (itemData) {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/turack/them",
        {
          donvi_id: itemData.DONVI_ID,
          kieutu: itemData.KIEUTU,
          loaitu_id: itemData.LOAITU_ID,
          ten_tu: itemData.TEN_TU,
          vitri: itemData.VITRI,
        }
      );
      return result.data;
    },
    bindData: async function (itemID) {
      let itemData = await this.layTuRack(itemID);
      await this.bindItem(itemData);
    },
    bindItem: async function (itemData) {
      if (itemData != null) {
        this.currentItem = {
          TURACK_ID: itemData.TURACK_ID,
          LOAITU_ID: itemData.LOAITU_ID,
          VITRI: itemData.VITRI,
          KIEUTU: itemData.KIEUTU,
          TEN_TU: itemData.TEN_TU,
          DONVI_ID: itemData.DONVI_ID,
        };
        let tram = await this.getDonVi(itemData.DONVI_ID);
        let toKT = null;
        if (tram != null) {
          toKT = await this.getDonVi(tram.DONVI_CHA_ID);
        }
        if (toKT != null) this.selectedTTVT = toKT.DONVI_CHA_ID;
        else {
          if (!(this.dsTTVT == null || this.dsTTVT.length == 0)) {
            this.selectedTTVT = this.dsTTVT[0].DONVI_ID;
          } else this.selectedTTVT = 0;
        }
        await this.bindToKT();
        this.selectedToKT = toKT.DONVI_ID;
        await this.bindTram();
      } else this.currentItem = {};
    },
    formNhapMoi: function () {
      if (this.currentItem.DONVI_ID != undefined) {
        this.currentItem.TURACK_ID = 0;
        this.currentItem.TEN_TU = "";
        this.currentItem.VITRI = 0;
        this.currentItem.KIEUTU = 1;
        if (this.dsLoaiTu.length > 0) {
          this.currentItem.LOAITU_ID = this.dsLoaiTu[0].LOAITU_ID;
        } else this.currentItem.LOAITU_ID = 0;
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    formGhiLai: async function () {
      let result = false;
      if (this.currentItem.TURACK_ID != undefined) {
        this.$v.$touch();
        if (this.$v.$invalid) {
          let msg = "";
          if (this.$v.currentItem.TEN_TU.$invalid) {
            msg += "Tên tủ không được để trống";
          }
          if (this.$v.currentItem.VITRI.$invalid) {
            if (msg != "") msg += "\n";
            msg += "Vị trí không hợp lệ";
          }
          this.$root.toastError(msg);
        } else {
          if (this.currentItem.TURACK_ID == 0) {
            let rs = await this.themTuRack(this.currentItem);
            if (rs != null) {
              this.currentItem.TURACK_ID = rs.TURACK_ID;
              result = true;
              this.$root.toastSuccess("Thêm mới thành công");
            }
          } else {
            let rs = await this.capNhatTuRack(this.currentItem);
            if (rs.result) {
              result = true;
              this.$root.toastSuccess("Cập nhật thành công");
            }
          }
        }
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
      return result;
    },
    formHuy: function () {
      if (this.currentItem.TURACK_ID != undefined) {
        this.currentItem.TURACK_ID = undefined;
        this.currentItem.TEN_TU = "";
        this.currentItem.VITRI = 0;
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    formXoa: async function () {
      try {
        let result = false;
        if (
          !(
            this.currentItem.TURACK_ID == undefined ||
            this.currentItem.TURACK_ID == 0
          )
        ) {
          let rs = await this.xoaTuRack(this.currentItem.TURACK_ID);
          if (rs.result) {
            this.currentItem.TURACK_ID = undefined;
            this.currentItem.TEN_TU = "";
            this.currentItem.VITRI = 0;
            result = true;
            this.$root.toastSuccess("Xóa thành công");
          }
        } else {
          this.$root.toastError("Bạn chưa chọn thiết bị");
        }
        return result;
      } catch (error) {
        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Xóa thất bại");
      }
    },
    selectTTVT_OnChange: async function () {
      this.$root.showLoading(true);
      try {
        await this.bindToKT();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectToKT_OnChange: async function () {
      this.$root.showLoading(true);
      try {
        await this.bindTram();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
  },
};
</script>
<style>
</style>
