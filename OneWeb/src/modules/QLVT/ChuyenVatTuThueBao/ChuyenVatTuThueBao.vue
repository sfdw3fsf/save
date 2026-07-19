<template src="./ChuyenVatTuThueBao.html"></template>

<script>
import API from "../api/ChuyenVatTuThueBao.js";
export default {
  name: "ChuyenVatTuThueBao",
  data() {
    return {
      gParams: {
        vthuebao_id: 0,
        txtMaTB: "",
        txtTenTB: "",
        txtLoaiHinhTB: "",
        cboDSThueBao: "",
        dtVatTuSDRowChanged: {},
        dtVatTuCDCRowChanged: {},
      },
      cboDSThueBao: [],
      dtVatTuCDC: [],
      dtVatTuSD: [],
    };
  },
  methods: {
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    dateToString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    setFirstValue(cbo, key) {
      if (cbo.length) {
        return cbo[0][key];
      } else {
        return "";
      }
    },
    btnLayTT() {
      this.TIMKIEM(0);
    },
    txtMaTBEnter() {
      this.TIMKIEM(0);
    },
    async btnGan() {
      await this.GAN();
    },
    async btnGo() {
      await this.GO();
    },
    cboDSThueBaoChanged(value) {
      this.TIMKIEM_VT();
    },
    onGridVTSDCellSaved(args) {
      console.log(args);
      if (args) {
        if (args.columnName == "MA_TBI") {
          this.dtVatTuSD.forEach((e) => {
            if ((e.SERIAL = args.rowData.SERIAL)) {
              e["MA_TBI"] = args.value;
            }
          });
        }
      }
    },
    onGridVTCDCCellSaved(args) {
      console.log(args);
      if (args) {
        if (args.columnName == "MA_TBI") {
          this.dtVatTuCDC.forEach((e) => {
            if ((e.SERIAL = args.rowData.SERIAL)) {
              e["MA_TBI"] = args.value;
            }
          });
        }
      }
    },
    onDtVatTuSDRowChanged(value) {
      this.gParams.dtVatTuSDRowChanged = value;
    },
    onDtVatTuCDCRowChanged(value) {
      this.gParams.dtVatTuCDCRowChanged = value;
    },
    async GAN() {
      try {
        let ds = this.dtVatTuSD;
        if (!ds.length) {
          return this.$toast.error(
            "Danh sách vật tư không có không thể chuyển"
          );
        }
        let dsChon = this.$refs.gridVatTuSD.getSelectedRecords();
        if (!dsChon.length) {
          return this.$toast.error("Bạn chưa chọn vật tư để chuyển");
        }
        if (this.isNullOrEmpty(this.gParams.cboDSThueBao)) {
          return this.$toast.error("Bạn chưa chọn thuê bao cần chuyển đến");
        }
        let isConfirm = await this.$bvModal.msgBoxConfirm(
          `Bạn có muốn chuyển vật tư vào thuê bao ${this.gParams.cboDSThueBao} không?`,
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        );
        if (isConfirm) {
          this.$root.loading(true);
          let dsVTId = dsChon.map((e) => e.SDVT_ID).join();
          await API.postGanVatTuTB(this.axios, {
            dsSDVTId: dsVTId,
            thueBaoId: this.gParams.cboDSThueBao,
          });
          this.$toast.success("Chuyển vật tư thành công");
          let tb_temp_id = this.gParams.cboDSThueBao;
          await this.TIMKIEM(1);
          this.gParams.cboDSThueBao = tb_temp_id;
          await this.TIMKIEM_VT();
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    async GO() {
      let ds = this.dtVatTuCDC;
      if (!ds.length) {
        return this.$toast.error("Danh sách vật tư không có không thể chuyển");
      }
      let dsChon = this.$refs.gridVatTuCDC.getSelectedRecords();
      if (!dsChon.length) {
        return this.$toast.error("Bạn chưa chọn vật tư để chuyển");
      }
      if (this.gParams.vthuebao_id == 0) {
        return this.$toast.error("Bạn chưa chọn thuê bao cần chuyển đến ");
      }
      let isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có muốn chuyển vật tư vào thuê bao ${this.gParams.txtMaTB} không?`,
        {
          okVariant: "success",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
          centered: true,
        }
      );
      if (isConfirm) {
        let dsVTId = dsChon.map((e) => e.SDVT_ID).join();
        await API.postGoVatTuTB(this.axios, {
          dsSDVTId: dsVTId,
          thueBaoId: this.gParams.vthuebao_id,
        });
        this.$toast.success("Chuyển vật tư thành công");
        let tb_temp_id = this.gParams.cboDSThueBao;
        await this.TIMKIEM(1);
        this.gParams.cboDSThueBao = tb_temp_id;
        await this.TIMKIEM_VT();
      }
    },
    async TIMKIEM(kieu) {
      try {
        this.$root.loading(true);
        this.gParams.vthuebao_id = 0;
        this.gParams.txtTenTB = "";
        this.dtVatTuSD = [];
        this.dtVatTuCDC = [];
        this.cboDSThueBao = [];
        if (this.isNullOrEmpty(this.gParams.txtMaTB)) {
          return this.$toast.error("Bạn chưa nhập mã thuê bao");
        }
        let ds = (
          await API.getThueBao(this.axios, {
            maTB: this.gParams.txtMaTB.trim(),
          })
        ).data.data;
        if (!ds.length) {
          return this.$toast.error("Không tìm thấy thông tin thuê bao ");
        }
        this.gParams.txtTenTB = ds[0]["TEN_TB"];
        this.gParams.txtLoaiHinhTB = ds[0]["LOAIHINH_TB"];
        this.gParams.vthuebao_id = parseInt(ds[0]["THUEBAO_ID"]);
        let ds_luoi = await (
          await API.getDSVTTBSD(this.axios, {
            thueBaoId: this.gParams.vthuebao_id,
          })
        ).data.data;
        this.dtVatTuSD = ds_luoi;
        let ds_cungdoicap = await (
          await API.getDSThueBaoCungDoiCap(this.axios, {
            maDoiCap: ds[0]["MADOICAP"],
            thueBaoId: ds[0]["THUEBAO_ID"],
          })
        ).data.data;
        this.cboDSThueBao = ds_cungdoicap;
        this.gParams.cboDSThueBao = this.setFirstValue(
          this.cboDSThueBao,
          "THUEBAO_ID"
        );
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    async TIMKIEM_VT() {
      try {
        this.$root.loading(true);
        this.dtVatTuCDC = [];
        if (!this.cboDSThueBao.length) {
          return;
        }
        let ds = await (
          await API.getTTThueBaoTheoTBId(this.axios, {
            thueBaoId: this.gParams.cboDSThueBao,
          })
        ).data.data;
        if (!ds.length) {
          return this.$toast.error("Không tìm thấy thông tin thuê bao ");
        }
        let ds_luoi = await (
          await API.getDSVTTBSD(this.axios, {
            thueBaoId: ds[0]["THUEBAO_ID"],
          })
        ).data.data;
        this.dtVatTuCDC = ds_luoi;
      } catch (error) {
      } finally {
        this.$root.loading(false);
      }
    },
  },
  mounted() {},
};
</script>

<style src="./ChuyenVatTuThueBao.scss">
</style>