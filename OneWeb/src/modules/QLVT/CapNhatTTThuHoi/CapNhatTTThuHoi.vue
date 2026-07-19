<template src="./CapNhatTTThuHoi.html"></template>

<script>
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import Vue from "vue";
import API from "../api/CapNhatTTThuHoi.js";
import moment from "moment";
Vue.use(DatePickerPlugin);
export default {
  data() {
    return {
      gridChungTu: {
        dtChungTu: [],
        dtChungTuItemSelecteds: [],
        dtChungTuRowSelected: {},
      },
      gridChiTietVT: {
        dtChiTietVT: [],
        dtChiTietVTItemSelecteds: [],
        dtChiTietVTRowSelected: {},
      },
      dtimeTuNgay: moment(new Date()).subtract(7, "day").toDate(),
      dtimeDenNgay: new Date(),
      ckbSoPhieu: false,
      txtSoPhieu: "",
      ckbTheoSerial: false,
      txtTheoSerial: "",
      cboTinhTrangTbi: [],
    };
  },
  watch: {},
  methods: {
    dateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    async getDSTrangThai() {
      try {
        this.loading(true);
        let response = await API.getDSTrangThaiThuHoi(this.axios, {});
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.cboTinhTrangTbi = response.data.data;
        }
      } catch (error) {
        this.$toast.error(error.data.error_code);
      } finally {
        this.loading(false);
      }
    },
    async loadChungTu() {
      if (!this.gridChungTu.dtChungTuRowSelected)
        return this.$toast.error("Vui lòng chọn một chứng từ!");
      try {
        this.loading(true);
        let response = await API.getDSThuHoi(this.axios, {
          chungTuId: this.gridChungTu.dtChungTuRowSelected.CHUNGTU_ID,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          response.data.data.forEach((element, index) => {
            element["STT"] = index;
            element["TINHTRANGTBI"] = this.cboTinhTrangTbi;
          });
          this.gridChiTietVT.dtChiTietVT = response.data.data;
        }
      } catch (error) {
        this.$toast.error(error.data.error_code);
      } finally {
        this.loading(false);
      }
    },
    gridChungTuRowClicked(value) {
      // console.log("gridChungTuRowClicked", value);
      if (value) {
        this.gridChungTu.dtChungTuRowSelected = value.data;
        this.loadChungTu();
      } else {
        this.gridChiTietVT.dtChiTietVT = [];
      }
    },
    async btnTimKiem() {
      try {
        let sophieu = "";
        let serial = "";
        if (this.ckbSoPhieu) sophieu = this.txtSoPhieu.trim();
        if (this.ckbTheoSerial) serial = this.txtTheoSerial.trim();
        this.gridChungTu.dtChungTu = [];
        this.gridChiTietVT.dtChiTietVT = [];
        try {
          this.loading(true);
          let data = {
            soPhieu: sophieu ? sophieu : null,
            serial: serial ? serial : null,
            tuNgay: this.dateToString(this.dtimeTuNgay),
            denNgay: this.dateToString(this.dtimeDenNgay),
          };
          let response = await API.getDSChungTuThuHoi(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.gridChungTu.dtChungTu = response.data.data;
          }
        } catch (error) {
          this.$toast.error(error.data.error_code);
        } finally {
          this.loading(false);
        }
      } catch (error) {
      } finally {
      }
    },
    async btnCapNhat() {
      if (this.gridChiTietVT.dtChiTietVT.length == 0)
        return this.$toast.error("Không có danh sách chi tiết vật tư !");
      let chiTietVTSelector = this.$refs.gridChiTietVT.getSelectedRecords();
      if (chiTietVTSelector == null || chiTietVTSelector.length == 0)
        return this.$toast.error("Bạn chưa tích chọn dòng vật tư nào !");
      let isserial = chiTietVTSelector.filter((e) => e.LOAITBI_ID != 2);
      if (isserial.length > 0)
        return this.$toast.error(
          "Danh sách chỉ được phép có vật tư thuộc loại có serial !"
        );
      let vchungtu_id = this.gridChungTu.dtChungTuRowSelected.CHUNGTU_ID;
      let vkho_giao_id = this.gridChungTu.dtChungTuRowSelected.KHO_GIAO_ID;
      let vkho_nhan_id = this.gridChungTu.dtChungTuRowSelected.KHO_NHAN_ID;
      let ngay_ct = this.dateToString(
        this.stringToDate(
          this.gridChungTu.dtChungTuRowSelected.NGAY_CT,
          "DD/MM/YYYY hh:mm:ss"
        )
      );

      // let response = await API.postKiemTraHanChungTu(this.axios, {
      //   chungTuId: vchungtu_id,
      //   khoGiaoId: vkho_giao_id,
      //   khoNhanId: vkho_nhan_id,
      //   ngayCt: ngay_ct,
      // });
      // if (
      //   response.data.error_code === "BSS-00000000" &&
      //   response.data.data !== undefined
      // ) {
      //   if (response.data.data[0] != "1") {
      //     return this.$toast.error("Đã quá hạn sửa chứng từ!");
      //   }
      // }
      let mapJson = chiTietVTSelector.map((e) => {
        return {
          PHIEUVT_ID: e.PHIEUVT_ID,
          TINHTRANGTBI_ID: e.TINHTRANGTBI_ID,
        };
      });
      try {
        this.loading(true);

        let response = await API.postCapNhatTTTB(
          this.axios,
          // JSON.stringify(mapJson)
          {
            data: JSON.stringify(mapJson),
          }
        );
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data == "1") {
            this.$toast.success("Cập nhật thành công!");
            this.loadChungTu();
          } else {
            return this.$toast.error(response.data.data);
          }
        }
      } catch (error) {
        this.$toast.error(error.data.error_code);
      } finally {
        this.loading(false);
      }
    },

    getCBOTinhTrangTbi(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-combobox                           
                            :dataSource="data.TINHTRANGTBI"                        
                            :fields="{value:'tinhTrangTBiId', text: 'tinhTrangTBi'}"                   
                            :change="valueChange"
                            v-model="value"
                        ></ejs-combobox>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            computed: {},
            mounted() {
              // console.log(this.parent);
              // console.log(this.data);
              this.value = `${this.data.TINHTRANGTBI_ID}`;
            },
            methods: {
              valueChange(e) {
                // console.log(e.value);
                // this.parent.dsNhanVienGV.ds.find;
              },
            },
            watch: {
              value(newVal) {
                let obj = this.parent.gridChiTietVT.dtChiTietVT.find(
                  (item) => item.STT == this.data.STT
                );
                obj.TINHTRANGTBI_ID = this.value;
              },
            },
          },
        };
      };
    },
  },
  mounted() {
    this.getDSTrangThai();
  },
};
</script>

<style src="./CapNhatTTThuHoi.scss"></style>
