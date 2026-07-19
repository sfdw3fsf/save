<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from "moment";
import API from "../api/HoaDonDienTuAPI";
import { checkStatus } from "../HuyHoaDon/checkStatus";
import Vue from 'vue'

export default {
  components: {},
  data() {
    return {
      header: {
        title: "HỦY HÓA ĐƠN",
        list: [
          { name: "Hóa đơn", link: { name: "Ui.cards" } },
          {
            name: "Hóa đơn điện tử",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Hủy hóa đơn",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      checkTuNgay: false,
      tuNgay: new Date(),
      checkDenNgay: false,
      denNgay: new Date(),
      maTT: "",
      dsHoaDon: [],
      viewInvTemplate: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div><a href="javascript: void(0)" @click="onViewInv" title="Xem chi tiết hóa đơn điện tử" class="text-danger"><span>Xem chi tiết</span></a></div>`,
            data () {
                return { data: {} };
            },
            methods: {
                async onViewInv() {
                    let rs = await this.getInvViewNoPay(this.data.invToken);
                    if (rs !== null) {
                        var newTab = window.open('', '_blank');
                        newTab.document.body.innerHTML = rs.response;
                    }
                },
                async getInvViewNoPay(invToken) {
                    let result = null;
                    try {
                        this.loading(true);
                        let apiBody = {
                            invToken: invToken,
                            phanvung_id: this.$auth.getPhanVungID()
                        };
                        const response = await API.getInvViewNoPay(this.axios, apiBody);
                        if (response.data.error_code == "BSS-00000000" && response.data.data != null)
                            result = response.data.data;
                        else {
                            this.$root.toastError('Không tìm thấy hóa đơn!');
                        }
                    }
                    catch (error) {
                        console.log(error)
                        this.$root.toastError('Không có dữ liệu, vui lòng thử lại!');
                    }
                    finally {
                        this.loading(false);
                        return result;
                    }
                }
            }})
        }
      },
    };
  },
  validations: {},
  watch: {},
  created() {},
  mounted() {
    this.$refs.maTT.focus();
  },
  methods: {
    TraCuu() {
      if (this.maTT.trim() == "") {
        this.$root.toastError("Chưa nhập mã thanh toán !");
        this.$refs.maTT.focus();
        return;
      }
      this.fetchTraCuu();
    },
    HuyHoaDon() {
      const selected = this.$refs.gridHoaDon.getSelectedRecords();
      if (selected == null || selected.length <= 0) {
        this.$root.toastError("Bạn chưa chọn hóa đơn cần hủy !");
        return;
      }
      this.$confirm(`Bạn thật sự muốn hủy bỏ hóa đơn đã chọn?`, "Thông báo", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy",
      })
        .then(() => {
          this.fetchHuyHoaDon();
        })
        .catch(() => {});
    },
    async fetchTraCuu() {
      const data = {
        cusCode: this.maTT,
        fromDate: this.checkTuNgay
          ? moment(this.tuNgay).format("DD/MM/YYYY")
          : "",
        toDate: this.checkDenNgay
          ? moment(this.denNgay).format("DD/MM/YYYY")
          : "",
        phanvung_id: this.$auth.getPhanVungID(),
      };
      try {
        this.loading(true);
        const response = await API.getDanhSachHoaDon(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          const result = response.data.data.map((item,index) => ({
            ...item,
            stt: ++index,
            cuscode: this.maTT.trim(),
            status_chu: checkStatus(item.status),
          }));
          this.dsHoaDon = result;
        } else {
          this.dsHoaDon = [];
          this.$root.toastError(
            "Không tìm thấy hóa đơn theo tiêu chí đã nhập !"
          );
        }
      } catch (error) {
        this.dsHoaDon = [];
        this.$root.toastError(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchHuyHoaDon() {
      const dsHoaDonSelected = this.$refs.gridHoaDon.getSelectedRecords() || [];
      this.loading(true);
      await dsHoaDonSelected.some(async (item, index) => {
        if (this.KiemTraDuLieu(item)) {
          const data = {
            fkey: item.fkey,
            phanvung_id: this.$auth.getPhanVungID(),
          };
          try {
            const response = await API.huyBoHoaDon(this.axios, data);
            if (response.data.error_code === "BSS-00000000") {
              this.dsHoaDon = [];
              this.$root.toastSuccess("Hủy hóa đơn thành công !");
              this.fetchTraCuu();
            }
          } catch (error) {
            this.$root.toastError(error.data.message);
          } finally {
            // this.loading(false);
            return true
          }
        } else {
           return false
        }
      });
      this.loading(false);
    },
    KiemTraDuLieu(item) {
      if (item.payment == "1") {
        this.$root.toastError(`Hóa đơn ${item.invNum} đã được thanh toán. Không thể hủy !`);
        return false;
      }
      if (item.status != "1") {
        this.$root.toastError(`Hóa đơn ${item.invNum} đã được điều chỉnh hoặc bị hủy !`);
        return false;
      }
      return true;
    },
  },
  computed: {},
};
</script>
