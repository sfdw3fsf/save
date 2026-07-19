<template src="./index.template.html"></template>
<style>
.capnhat-vci-vpi .vue-treeselect__control {
  height: 32px !important;
}

.capnhat-vci-vpi .vue-treeselect--disabled .vue-treeselect__control {
  background-color: #f1f1f1;
}

.capnhat-vci-vpi .vue-treeselect__placeholder,
.capnhat-vci-vpi .vue-treeselect__single-value {
  line-height: 30px;
}

.capnhat-vci-vpi .vue-treeselect__control-arrow {
  width: 7px;
  height: 6px;
  color: inherit;
  margin-left: 7px;
}

.capnhat-vci-vpi .disable-btn:hover {
  background-color: white !important;
}

.capnhat-vci-vpi .disable-btn a {
  color: #6c757d !important;
}

.capnhat-vci-vpi .disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}

.capnhat-vci-vpi
  .e-grid
  div.e-gridheader
  div.e-headercontent
  thead
  tr.e-columnheader
  th[aria-colindex="1"],
.capnhat-vci-vpi
  .e-grid
  div.e-gridheader
  div.e-headercontent
  thead
  tr.e-columnheader
  th[aria-colindex="1"]
  > div {
  padding: 0 !important;
}
</style>
<script>
import { L10n } from "@syncfusion/ej2-base";
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import { required } from "vuelidate/lib/validators";

L10n.load({
  "vi-VN": {
    dropdowns: {
      noRecordsTemplate: "Không có dữ liệu",
      actionFailureTemplate: "",
    },
  },
});

export default {
  components: { BssErrorMarker, BssRequiredMarker },
  props: {
    modalId: String,
    inforPort: {},
  },
  validations: {
    idDslam: {
      required,
    },
  },
  data() {
    return {
      dsDonViQuanLy: [],
      dsTramThiCong: [],
      dsTramThietBi: [],
      dsDslam: [],
      idDonViQuanLy: null,
      idTramThiCong: null,
      idTramThietBi: null,
      idDslam: null,
      vciCvlan: 0,
      vpiSvlan: 0,
      btnNhapMoiEnabled: true,
      btnHuyBoEnabled: true,
      //btnXoaEnabled: true,
      btnGhiLaiEnabled: true,
      dsVciVpi: [],
      dsVciVpiDuocChon: [],
      vciVpiDuocChon: null,
    };
  },
  computed: {
    btnXoaEnabled: function () {
      let rs = false;
      if (!(this.dsVciVpiDuocChon == null || this.dsVciVpiDuocChon.length == 0)) {
        rs = true;
      }
      return rs;
    },
  },
  watch: {
    dsVciVpi() {
      this.dsVciVpiDuocChon = [];
    },
    async idDslam(newVal) {
      if (!newVal) {
        return;
      }

      try {
        this.loading(true);
        await this.hienThiDanhSach(newVal);
      } catch (err) {
      } finally {
        this.loading(false);
      }
    },
    async idTramThietBi(newVal) {
      this.idDslam = null;

      if (!newVal) {
        this.dsDslam = [];
        this.dsVciVpi = [];
        return;
      }

      try {
        this.loading(true);
        await this.layDmDslam(newVal);
      } catch (err) {
      } finally {
        this.loading(false);
      }
    },
    async idTramThiCong(newVal) {
      this.idTramThietBi = null;

      if (!newVal) {
        this.dsTramThietBi = [];
        return;
      }

      try {
        this.loading(true);
        await this.layDmTramThietBi(newVal);
      } catch (err) {
      } finally {
        this.loading(false);
      }
    },
    async idDonViQuanLy(newVal) {
      this.idTramThiCong = null;

      if (!newVal) {
        this.dsTramThiCong = [];
        return;
      }

      try {
        this.loading(true);
        await this.layDmTramThiCong(newVal);
      } catch (err) {
      } finally {
        this.loading(false);
      }
    },
  },
  methods: {
    onClickRowTableVciVpi(rowData) {
        this.refreshSelectedItems();
    },
    onRowDeselected: function (args) { this.refreshSelectedItems(); },
    refreshSelectedItems: function () {
      let grid = this.$refs["grid-vci-vpi"].grid;
      let items = grid.getSelectedRecords();
      this.dsVciVpiDuocChon = [];
      if (!(items == null || items == undefined)) {
        for (let i = 0; i < items.length; i++) {
          this.dsVciVpiDuocChon.push(items[i].VCI_VPI_ID);
        }
      }

      if (!(this.dsVciVpi == null || this.dsVciVpi.length == 0)) {
        this.vciVpiDuocChon = this.dsVciVpiDuocChon.length == 0 ? null : this.dsVciVpi.find(
          (e) => (e.VCI_VPI_ID == this.dsVciVpiDuocChon[0])
        );
        if (this.vciVpiDuocChon != null) {
          this.vciCvlan = this.vciVpiDuocChon.VCI;
          this.vpiSvlan = this.vciVpiDuocChon.VPI;
          this.setButton(3);
        } else {
          this.clear();
          this.setButton(2);
        }
      }
    },
    onClickButtonXoa() {
      if (!this.btnXoaEnabled) {
        return;
      }

      let error = !this.dsVciVpiDuocChon || this.dsVciVpiDuocChon.length == 0;

      if (error) {
        this.$toast.error("Vui lòng chọn Vci - Vpi để xóa !");
        return;
      }

      let dsVciVpiKhongTheXoa = this.dsVciVpi.filter((item) => {
        return (
          parseInt(item.TRANGTHAI) != 0 &&
          this.dsVciVpiDuocChon &&
          this.dsVciVpiDuocChon.indexOf(item.VCI_VPI_ID) >= 0
        );
      });

      if (dsVciVpiKhongTheXoa.length > 0) {
        this.$toast.error("Tồn tại Vci - Vpi không thể xóa");
        return;
      }

      this.$bvModal.show("modal-confirm-xoa");
    },
    async okXoaVciVpi() {
      let count = 0;

      this.loading(true);

      for (var i = 0; i < this.dsVciVpiDuocChon.length; i++) {
        try {
          await this.xoaVciVpi(this.dsVciVpiDuocChon[i]);
          ++count;
        } catch (err) {}
      }

      this.loading(false);

      if (count > 0) {
        this.$toast.success("Xóa thành công");
        this.hienThiDanhSach(this.idDslam);
        this.setButton(3);
      } else {
        this.$toast.error("Xóa thất bại");
      }
    },
    onClickButtonHuy() {
      if (!this.btnHuyBoEnabled) {
        return;
      }

      this.clear();
      this.setButton(2);
    },
    async onClickButtonGhiLai() {
      if (!this.btnGhiLaiEnabled) {
        return;
      }

      try {
        this.$v.$touch();

        if (this.$v.$invalid && !this.$v.idDslam.required) {
          throw "Bạn chưa chọn Dslam";
        }

        let input = {
          vci: this.vciCvlan,
          vpi: this.vpiSvlan,
          dslamid: this.idDslam,
        };

        this.loading(true);
        let result = null;

        if (!this.btnNhapMoiEnabled) {
          result = await this.themMoi(input);
          result.data.TRANGTHAI = 0;
          this.dsVciVpi.unshift(result.data);
          this.loading(false);
          this.$toast.success(result.message);
        } else {
          if (!this.vciVpiDuocChon) {
            throw "Bạn chưa chọn Vci - Vpi để cập nhật";
          }

          input.vcivpi = this.vciVpiDuocChon.VCI_VPI_ID;
          result = await this.capNhat(input);
          this.vciVpiDuocChon.VCI = input.vci;
          this.vciVpiDuocChon.VPI = input.vpi;
          this.vciVpiDuocChon.DSLAM_ID = input.dslamid;

          let grid = this.$refs["grid-vci-vpi"].grid;
          grid.setCellValue(input.vcivpi, "VCI", input.vci);
          grid.setCellValue(input.vcivpi, "VPI", input.vpi);
          grid.setCellValue(input.DSLAM_ID, "VPI", input.dslamid);

          this.loading(false);
          this.$toast.success(result);
        }
      } catch (err) {
        this.loading(false);
        this.$toast.error(err);
      }
    },
    onClickButtonNhapMoi() {
      if (this.dsVciVpi.length > 0) {
        this.$refs["grid-vci-vpi"].grid.clearSelection();
      }
      this.clear();
      this.setButton(1);
    },
    clear() {
      this.vciCvlan = 0;
      this.vpiSvlan = 0;
      this.$v.$reset();
    },
    setButton(kieu) {
      if (kieu == 0) {
        //Bat dau
        this.btnNhapMoiEnabled = true;
        this.btnHuyBoEnabled = false;
        //this.btnXoaEnabled = false
        this.btnGhiLaiEnabled = false;
      }

      if (kieu == 1) {
        //Them moi
        this.btnNhapMoiEnabled = false;
        this.btnGhiLaiEnabled = true;
        this.btnHuyBoEnabled = true;
      }

      if (kieu == 2) {
        //Huy
        this.btnNhapMoiEnabled = true;
        this.btnGhiLaiEnabled = false;
        this.btnHuyBoEnabled = false;
        //this.btnXoaEnabled = false
      }

      if (kieu == 3) {
        //Edit
        this.btnGhiLaiEnabled = true;
        this.btnNhapMoiEnabled = true;
        this.btnHuyBoEnabled = true;
      }
    },
    async onModalShown() {
      console.log("thiss,", this.inforPort);
      this.setButton(0);
      this.$refs["input-vci-cvlan"].$el.focus();

      try {
        this.loading(true);
        await this.layDmDonViQuanLy();
        if (this.inforPort.DONVI_ID) {
          this.idDonViQuanLy = this.inforPort.DONVI_ID;
        } else {
          if (this.dsDonViQuanLy && this.dsDonViQuanLy.length > 0) {
            this.idDonViQuanLy = this.dsDonViQuanLy[0].DONVI_ID;
          }
        }
        // await this.layDmTramThiCong(this.idDonViQuanLy);
        // await this.layDmTramThietBi(this.idTramThiCong);
        // await this.layDmDslam(this.dsTramThietBi);
      } catch (err) {
      } finally {
        this.loading(false);
      }
    },
    async layDmDslam(idTramThietBi) {
      try {
        let response = await this.$root.context.get(
          `/web-ecms/danhmuc/vcivpi/dslam/${idTramThietBi}`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsDslam = response.data;
        } else {
          this.dsDslam = [];
        }
        if (this.inforPort.DSLAM_TINH_ID) {
          this.idDslam = this.inforPort.DSLAM_TINH_ID;
        } else {
          this.idDslam = response.data[0].DSLAM_ID;
        }
      } catch (err) {
        this.dsDslam = [];
      }
    },
    async layDmTramThietBi(idTramThiCong) {
      try {
        let response = await this.$root.context.post(
          "/web-ecms/danhmuc/vcivpi/dstramtbi",
          { loaidv_id: 39, donvi_cha_id: idTramThiCong }
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsTramThietBi = response.data;
        } else {
          this.dsTramThietBi = [];
        }
        if (this.inforPort.TRAM_TB_ID) {
          this.idTramThietBi = this.inforPort.TRAM_TB_ID;
        } else {
          this.idTramThietBi = response.data[0].DONVI_ID;
        }
      } catch (err) {
        this.dsTramThietBi = [];
      }
    },
    async layDmTramThiCong(idDonViQuanLy) {
      try {
        let response = await this.$root.context.post(
          "/web-ecms/danhmuc/vcivpi/dstramthicong",
          { loaidv_id: 5, donvi_cha_id: idDonViQuanLy }
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsTramThiCong = response.data;
        } else {
          this.dsTramThiCong = [];
        }

        if (this.inforPort.TRAM_TC_ID) {
          this.idTramThiCong = this.inforPort.TRAM_TC_ID;
        } else {
          this.idTramThiCong = response.data[0].DONVI_ID;
        }
      } catch (err) {
        this.dsTramThiCong = [];
      }
    },
    async layDmDonViQuanLy() {
      try {
        let response = await this.$root.context.get(
          "/web-ecms/danhmuc/vcivpi/dsdonviql/3"
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsDonViQuanLy = response.data;
        } else {
          this.dsDonViQuanLy = [];
        }
      } catch (err) {
        this.dsDonViQuanLy = [];
      }
    },
    async hienThiDanhSach(idDslam) {
      try {
        let response = await this.$root.context.get(
          `/web-ecms/danhmuc/vcivpi/dsvci_vpi/${idDslam}`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsVciVpi = response.data;
        } else {
          this.dsVciVpi = [];
        }
      } catch (err) {
        this.dsVciVpi = [];
      }
    },
    async xoaVciVpi(idVciVpi) {
      try {
        let response = await this.$root.context.post(
          `/web-ecms/danhmuc/vcivpi/xoa/${idVciVpi}`
        );

        if (response.error_code !== "BSS-00000000") {
          throw { response: { data: response } };
        }
      } catch (err) {
        let message = [];

        if (err.response.data.message) {
          message.push(err.response.data.message);
        }

        if (err.response.data.message_detail) {
          message.push(err.response.data.message_detail);
        }

        throw message.join("\n");
      }
    },
    async themMoi(data) {
      try {
        let response = await this.$root.context.post(
          "/web-ecms/danhmuc/vcivpi/them",
          data
        );

        if (response.error_code === "BSS-00000000") {
          return {
            message: "Cập nhật dữ liệu lên viễn thông tỉnh thành công!",
            data: response.data,
          };
        }

        throw { response: { data: response } };
      } catch (err) {
        let message = [];
        let response = err.response.data;

        if (response.message) {
          message.push(response.message);
        }

        if (response.message_detail) {
          message.push(response.message_detail);
        }

        if (response.data) {
          let detailMessage = Object.getOwnPropertyNames(response.data).map(
            (item) => response.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        throw message.join("\n");
      }
    },
    async capNhat(data) {
      try {
        let response = await this.$root.context.post(
          "/web-ecms/danhmuc/vcivpi/sua",
          data
        );

        if (response.error_code !== "BSS-00000000") {
          throw { response: { data: response } };
        }

        return "Cập nhật dữ liệu lên viễn thông tỉnh thành công!";
      } catch (err) {
        let message = [];
        let response = err.response.data;

        if (response.message) {
          message.push(response.message);
        }

        if (response.message_detail) {
          message.push(response.message_detail);
        }

        if (response.data) {
          let detailMessage = Object.getOwnPropertyNames(response.data).map(
            (item) => response.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        throw message.join("\n");
      }
    },
  },
};
</script>
