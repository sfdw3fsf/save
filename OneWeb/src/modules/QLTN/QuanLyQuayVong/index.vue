<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from "moment";
import API from "../api/QuanLyQuayVong";
import xlsx from "xlsx";

export default {
  components: {},
  data() {
    return {
      selectedTab: 0,
      idTrangThai_nvtc: 1,
      thang: moment(new Date()).subtract(1, "months").endOf("month").toDate(),
      ndGiao_ntvc: "",
      ngayGiao: new Date(),
      idTrangThai_ChuaThu: 1,
      denNgay: new Date(),
      ndGiao_chuathu: "",
      idHuongGiao: "",
      dsHuongGiao: [],
      idLyDo: "",
      dsLyDo: [],
      ghiChu: "",
      dsPhieu_nvtc: [],
      dsPhieu_chuathu: [],
      objSelected: null,
    };
  },
  validations: {},
  watch: {
    idTrangThai_nvtc: {
      handler: function (value) {
        this.fetchDSNVTC();
      },
    },
    idTrangThai_ChuaThu: {
      handler: function (value) {
        this.fetchDSChuaThu();
      },
    },
  },
  created() {
    this.fetchHuongGiao();
    this.fetchLyDo();
  },
  mounted() {},
  methods: {
    changeTab(val) {
      this.selectedTab = val;
    },
    TraCuu() {},
    async fetchHuongGiao() {
      const response = await API.getDSHuongGiao(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.idHuongGiao = response.data.data[0].HUONGGIAO_ID;
        this.dsHuongGiao = response.data.data;
      }
    },
    async fetchLyDo() {
      const params = {
        kieu: 5,
        nhomConId: 0,
      };
      const response = await API.getDSLyDo(this.axios, params);
      if (response.data.error_code === "BSS-00000000") {
        this.idLyDo = response.data.data[0].KQXL_ID;
        this.dsLyDo = response.data.data;
      }
    },
    LayDS() {
      this.clearForm();
      if (this.selectedTab == 0) {
        this.fetchDSNVTC();
      } else this.fetchDSChuaThu();
    },
    clearForm() {
      this.ndGiao_ntvc = "";
      this.ndGiao_chuathu = "";
      this.ghiChu = "";
    },
    async fetchDSNVTC() {
      try {
        this.loading(true);
        const params = {
          trangThaiId: this.idTrangThai_nvtc,
          thang: moment(this.thang).format("YYYYMM"), //202103
          nhanVienId: this.$auth.getNhanVienID(),
        };
        const response = await API.getDSPhieuNVTC(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsPhieu_nvtc = response.data.data;
        } else {
          this.dsPhieu_nvtc = [];
          this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsPhieu_nvtc = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSChuaThu() {
      try {
        this.loading(true);
        const params = {
          ttphId: this.idTrangThai_ChuaThu, //14
          nhanVienId: this.$auth.getNhanVienID(), //7017
          tuNgay: "01/10/2018",
          denNgay: moment(this.denNgay).format("DD/MM/YYYY"),
        };
        const response = await API.getDSPhieuChuaThu(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsPhieu_chuathu = response.data.data;
        } else {
          this.dsPhieu_chuathu = [];
          this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsPhieu_chuathu = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    chuathu_selectedRowChanged(data) {
      if (data) {
        this.idLyDo = data.KQXL_ID;
        this.ghiChu = data.GHICHU;
        this.objSelected = data;
      } else {
        this.objSelected = null;
      }
    },
    GiaoPhieu() {
      if (this.selectedTab == 1) {
        if (this.ndGiao_chuathu.trim() == "") {
          this.$root.toastError("Bạn chưa nhập nội dung giao!");
          return;
        }
        if (!this.objSelected) {
          this.$root.toastError("Bạn chưa chọn phiếu để giao!");
          return;
        }
        this.fetchQuayVong_GiaoPhieu();
      } else {
        this.TaoHDQuayVong_TraTruoc();
      }
    },
    async fetchQuayVong_GiaoPhieu() {
      try {
        this.loading(true);
        const data = {
          huongGiaoId: this.idHuongGiao,
          phieuId: this.objSelected.PHIEU_ID,
          nhanVienGiaoId: this.$auth.getNhanVienID(),
          donViId: this.$auth.getDonViID(),
          ndGiao: this.ndGiao_chuathu,
        };
        const response = await API.quayVong_GiaoPhieu(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.$root.toastSuccess("Giao phiếu thành công!");
          await this.getDSPhieuChuaThu();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async TaoHDQuayVong_TraTruoc() {
      const dsSelected_PhieuNVTC =
        this.$refs.gridDSNVTC.getSelectedRecords() || [];
      if (dsSelected_PhieuNVTC.length <= 0) {
        this.$root.toastError("Bạn chưa chọn phiếu để giao!");
        return;
      }

      try {
        this.loading(true);
        const ds_thuebaoID = dsSelected_PhieuNVTC.map(
          (item) => item.THUEBAO_ID
        );
        const data = {
          thueBaoId: ds_thuebaoID,
          ngayDK: moment(this.ngayGiao).format("DD/MM/YYYY"),
          ndGiao: this.ndGiao_ntvc,
        };
        const response = await API.taoHD_QuayVong_TraTruoc(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.$root.toastSuccess("Đã giao thành công phiếu cho NVTC!");
          await this.fetchDSNVTC();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    HuyGiao() {
      if (this.selectedTab == 1) {
        if (!this.objSelected) {
          this.$root.toastError("Bạn chưa chọn phiếu!");
          return;
        }
        // if (this.objSelected.TTPH_ID == 1) {
        //   this.$root.toastError("Phiếu đã thực hiện, không hủy giao được!");
        //   return;
        // }
        this.fetchQuayVong_HuyGiao(this.objSelected.PHIEU_ID);
      } else {
        let isFlat = false;
        const dsSelected_PhieuNVTC =
          this.$refs.gridDSNVTC.getSelectedRecords() || [];
        if (dsSelected_PhieuNVTC.length <= 0) {
          this.$root.toastError("Bạn chưa chọn phiếu!");
          return;
        }
        dsSelected_PhieuNVTC.map((item) => {
          if (item.TTPH_ID != 1) {
            this.$root.toastError(
              `Phiếu đã thực hiện, không hủy giao được thuê bao ${item.MA_TB}`
            );
            isFlat = true;
          }
        });
        if (!isFlat) {
          const idDS = dsSelected_PhieuNVTC.map((item) => item.PHIEU_ID);
          this.fetchQuayVong_HuyGiao(idDS);
        }
      }
    },
    async fetchQuayVong_HuyGiao(value) {
      try {
        this.loading(true);
        const data = value.toString();
        const response = await API.quayVong_HuyGiao(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.$root.toastSuccess("Hủy giao thành công!");
          await this.LayDS();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async HoanThanh() {
      if (!this.objSelected) {
        this.$root.toastError("Bạn chưa chọn phiếu!");
        return;
      }
      try {
        this.loading(true);
        const data = {
          phieuId: this.objSelected.PHIEU_ID,
          kqxlId: this.idLyDo,
          ghiChu: this.ghiChu,
          nhanVienId: this.$auth.getNhanVienID(),
        };
        const response = await API.hoanCong_QuayVong(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.$root.toastSuccess("Cập nhật hoàn thành thành công!");
          await this.fetchDSChuaThu();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async DieuHong() {
      if (!this.objSelected) {
        this.$root.toastError("Bạn chưa chọn phiếu!");
        return;
      }
      try {
        this.loading(true);
        const data = {
          phieuTCId: this.objSelected.PHIEU_ID,
          hdtbId: this.objSelected.HDTB_ID,
          thueBaoId: this.objSelected.THUEBAO_ID,
          donViNhanId: this.$auth.getDonViID(),
          nhanVienNhanId: this.$auth.getNhanVienID(),
          tinhtrangId: 1,
          khoaPhieu: 0,
          kenhTN: 1,
          ghichu: "KDV điều hỏng từ khảo sát thu quay vòng!",
        };
        const response = await API.dieuHong_QuayVong(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.$root.toastSuccess("Điều hỏng thành công!");
          await this.fetchDSChuaThu();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    XuatExcel() {
      const dataExcel =
        this.selectedTab == 0 ? this.dsPhieu_nvtc : this.dsPhieu_chuathu;
      if (dataExcel <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(dataExcel);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async GhiLai() {
      if (!this.objSelected) {
        this.$root.toastError("Bạn chưa chọn phiếu!");
        return;
      }
      const selectLyDo = this.dsLyDo.find(
        (item) => item.KQXL_ID == this.idLyDo
      );
      if (selectLyDo && selectLyDo.MUC != 5) {
        this.$root.toastError("Bạn chọn lý do chưa đúng! Xin hãy chọn lại!");
        return;
      }
      try {
        this.loading(true);
        const data = {
          phieuId: this.objSelected.PHIEU_ID,
          kqxlId: this.idLyDo,  //this.objSelected.KQXL_ID
          ghiChu: this.ghiChu,
        };
        const response = await API.capNhat_QuayVong(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.$root.toastSuccess("Cập nhật lý do thành công!");
          await this.fetchDSChuaThu();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
  },
  computed: {},
};
</script>
