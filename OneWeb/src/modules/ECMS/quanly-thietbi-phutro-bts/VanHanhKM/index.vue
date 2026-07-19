<template src="./index.template.html"></template>
<script>
import Vue from "vue";
import { DateTimePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import EventBus from "../../../../utils/eventBus";
import DateTimeLib from "../../../../utils/DateTimeLib";
Vue.use(DateTimePickerPlugin);
export default {
  data: function () {
    return {
      waterMark: "Chọn ngày",
      dateVal: Date,
      dateFormat: "dd/MM/yyyy HH:mm:ss",
      tugio: new Date(),
      dengio: new Date(),
      currentItem: {},
      dsKhoanMuc: [],
      dsVH_KM: [],
      khoanmuc_id: 0,
      txtSoLuong: 0,
      txtThanhtien: "0",
      txtGhiChu: "",
      thang: 0,
      dsThang: [],
      khoanmuc_id_2: 0,
      tongtien: 0,
      isDisableBtnNhapMoi: false,
      isDisableBtnXoa: false,
      isDisableBtnChapNhan: false,
      isDisableThang: true,
      modalThongBaoXoaVH: false,
      aggregates: [
        {
          field: "THANHTIEN",
          type: "SUM",
          format:'N0'
        },
      ],
      cTemplateThanhTien: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                {{getThanhTien}}
                </div>`,
            data: function () {
              return {
                data: {},
              };
            },
            computed: {
              getThanhTien: function () {
                return this.numberWithCommas(this.data.THANHTIEN)
              }
              }  ,    
              methods: {
                numberWithCommas(number) {
                  try {
                    number = number.toString().replaceAll(",", "");
                    return number
                      .toString()
                      .replace(/^[+-]?\d+/, function (int) {
                        return int.replace(/(\d)(?=(\d{3})+$)/g, "$1,");
                      });
                  } catch (e) {
                    console.log(e);
                    return 0;
                  }
                },
              },            
          }),
        };
      },
       cTemplateSoLuong: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                {{getSoLuong}}
                </div>`,
            data: function () {
              return {
                data: {},
              };
            },
            computed: {
              getSoLuong: function () {
                return this.numberWithCommas(this.data.SOLUONG)
              }
              }  ,    
              methods: {
                numberWithCommas(number) {
                  try {
                    number = number.toString().replaceAll(",", "");
                    return number
                      .toString()
                      .replace(/^[+-]?\d+/, function (int) {
                        return int.replace(/(\d)(?=(\d{3})+$)/g, "$1,");
                      });
                  } catch (e) {
                    console.log(e);
                    return 0;
                  }
                },
              },            
          }),
        };
      },
      cTemplateTuGio: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                {{getTuGio}}
                </div>`,
            data: function () {
              return {
                data: {},
              };
            },
            computed: {
              getTuGio: function () {
                return DateTimeLib.toDateDisplayWithFormat(this.data.TUGIO,"DD/MM/yyyy HH:mm:ss")
              }
              }  ,    
              methods: {
              },            
          }),
        };
      },
      cTemplateDenGio: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                {{getDenGio}}
                </div>`,
            data: function () {
              return {
                data: {},
              };
            },
            computed: {
              getDenGio: function () {
                 return DateTimeLib.toDateDisplayWithFormat(this.data.DENGIO,"DD/MM/yyyy HH:mm:ss")
              }
              }  ,    
              methods: {
              },            
          }),
        };
      },
    };
  },
  props: ["modalId", "vanhanh_id"],
  computed: {},
  watch: {
    khoanmuc_id: function (val) {
      if (val == 12) this.isDisableThang = false;
      else this.isDisableThang = true;
    },
    thang: function (val) {
      this.set_datime_in_month(val);
    },
    txtThanhtien: function (val) {
      this.txtThanhtien = this.numberWithCommas(val);
    },
    txtSoLuong: function (val) {
      this.txtSoLuong = this.numberWithCommas(val);
    },
  },
  methods: {
    //#region "Nút bấm"
    focusMyElement: async function () {
      this.load_month();
      let date = new Date();
      this.thang = date.getMonth() + 1;
      this.convertDate(date.getMonth());
      this.load_dsKHoanMuc(1);
      await this.load_VanHanhKM();
    },
    convertDate: function (month) {
      let date = new Date();
      let lastday = new Date(date.getFullYear(), month, 0).getDate();
      this.tugio = new Date(
        date.getFullYear() + "-" + month + "-" + "1 00:00:00"
      );
      this.dengio = new Date(
        date.getFullYear() + "-" + month + "-" + lastday + " 23:59:59"
      );
    },
    hiddenForm: function () {
      this.dsVH_KM = [];
      this.dsKhoanMuc = [];
      this.txtThanhtien = "0";
      this.txtSoLuong = "0";
      this.txtGhiChu = "";
      this.currentItem = {};
      this.$bvModal.hide(this.modalId);
    },

    load_VanHanhKM: async function () {
      try {
        this.dsVH_KM = await this.phutro_lay_ds_vanhanh_km(this.vanhanh_id);
        let tien = 0;
        this.dsVH_KM.forEach((value, index) => {
          tien += parseInt(value.THANHTIEN);
        });
        this.tongtien = tien;
      } catch (ex) {
        console.log(ex);
      }
    },
    load_dsKHoanMuc: async function (kieu) {
      try {
        let response = await this.$root.context.get(
          `/web-ecms/baoduong-phutro/phutro_lay_ds_khmuc/` + kieu
        );
        this.dsKhoanMuc = response.data;
      } catch (err) {
        this.$root.toastError("Có lỗi lấy ra loại vận hành");
        console.log(ex);
        return null;
      }
    },
    phutro_lay_ds_vanhanh_km: async function (vanhanh_id) {
      try {
        let response = await this.$root.context.get(
          `/web-ecms/baoduong-phutro/phutro_lay_ds_vanhanh_km/` + vanhanh_id
        );
        return response.data;
      } catch (err) {
        console.log(ex);
      }
    },
    phutro_capnhap_vanhanh_km: async function (
      kieu,
      vanhanh_id,
      khoanmuc_id,
      khoanmuc_id_2,
      soluong,
      thanhtien,
      ghichu,
      tugio,
      dengio
    ) {
      try {
        soluong = soluong.replaceAll(",", "");
        thanhtien = thanhtien.replaceAll(",", "");
        let data = {
          kieu: kieu,
          vanhanh_id: vanhanh_id,
          khoanmuc_id: khoanmuc_id,
          khoanmuc_id_2: khoanmuc_id_2,
          soluong: soluong,
          thanhtien: thanhtien,
          ghichu: ghichu,
          tugio: DateTimeLib.toDateTimeDisplayWithFormat(
            tugio,
            "DD/MM/YYYY HH:mm:ss"
          ),
          dengio: DateTimeLib.toDateTimeDisplayWithFormat(
            dengio,
            "DD/MM/YYYY HH:mm:ss"
          ),
        };
        console.log("data:", data);
        let response = await this.$root.context.post(
          `/web-ecms/baoduong-phutro/phutro_capnhap_vanhanh_km`,
          data
        );
        return response.data;
      } catch (ex) {
        console.log(ex);
      }
    },

    load_month: function () {
      let thang_tem = [];
      for (let i = 1; i <= 12; i++) {
        let obj = new Object();
        obj.THANG_ID = i;
        obj.TEN_THANG = "Tháng " + i;
        thang_tem.push(obj);
      }
      this.dsThang = [...thang_tem];
    },
    setButton(kieu) {
      if (kieu == 1) {
        this.isDisableBtnNhapMoi = true;
        this.isDisableBtnXoa = false;
        this.txtGhiChu = "";
        this.txtSoLuong = "";
        this.txtThanhtien = "";
        this.isDisableBtnChapNhan = true;
      } else {
        this.isDisableBtnNhapMoi = false;
        this.isDisableBtnXoa = false;
        this.isDisableBtnChapNhan = false;
      }
    },
    tsbtnChapNhan_Click: function () {
      EventBus.$emit("chapNhanTongTien", this.txtThanhtien);
      this.hiddenForm();
    },
    tsbtnNhapMoi_Click: function () {
      this.setButton(1);
    },
    tsbtnGhiLai_Click: async function () {
      try {
        let dt1 = [...this.dsVH_KM];

        if (this.isDisableBtnNhapMoi) {
          //KT với khoản mục: 17-Nhiên liệu tiêu thụ, 13-Xăng
          if (this.khoanmuc_id == "13" || this.khoanmuc_id == "17") {
            if (
              DateTimeLib.toDateDisplay(this.tugio) !=
              DateTimeLib.toDateDisplay(this.dengio)
            ) {
              this.$root.toastError("Chỉ được nhập mốc giờ cùng 1 ngày!");
              return;
            }
          }
          if (this.tugio > this.dengio) {
            this.$root.toastError("Từ giờ phải nhỏ hơn đến giờ!");
            return;
          }
          //KT với khoản mục: 14-Chi phí tiền điện hàng tháng
          if (this.khoanmuc_id == "14") {
            let month = this.thang;
            if (!this.check_valid_datetime(month)) {
              this.$root.toastError(
                "Từ giờ đến giờ phải trong khoảng thời gian tháng được chọn!"
              );
              return;
            }
          }

          await this.phutro_capnhap_vanhanh_km(
            1,
            this.vanhanh_id,
            this.khoanmuc_id,
            0,
            this.txtSoLuong,
            this.txtThanhtien,
            this.txtGhiChu,
            this.tugio,
            this.dengio
          );
          this.$root.toastSuccess("Thêm mới Vận hành - khoản mục thành công!");
          this.load_VanHanhKM();
          this.setButton(0);
        } else {
          //KT với khoản mục: 17-Nhiên liệu tiêu thụ, 13-Xăng
          if (this.khoanmuc_id == "13" || this.khoanmuc_id == "17") {
            if (
              DateTimeLib.toDateObjectWithFormat(
                this.tugio,
                "DD/MM/yyyy HH:mm:ss"
              ) !=
              DateTimeLib.toDateObjectWithFormat(
                this.dengio,
                "DD/MM/yyyy HH:mm:ss"
              )
            ) {
              this.$root.toastError("Chỉ được nhập mốc giờ cùng 1 ngày!");
              return;
            }
          }
          if (this.tugio > this.dengio) {
            this.$root.toastError("Từ giờ phải nhỏ hơn đến giờ!");
            return;
          }

          //KT với khoản mục: 14-Chi phí tiền điện hàng tháng
          if (this.khoanmuc_id == "14") {
            let month = this.thang;
            if (!this.check_valid_datetime(month)) {
              this.$root.toastError(
                "Từ giờ đến giờ phải trong khoảng thời gian tháng được chọn!"
              );
              return;
            }
          }

          await this.phutro_capnhap_vanhanh_km(
            2,
            this.vanhanh_id,
            this.khoanmuc_id,
            this.khoanmuc_id_2,
            this.txtSoLuong,
            this.txtThanhtien,
            this.txtGhiChu,
            this.tugio,
            this.dengio
          );
          this.$root.toastSuccess("Cập nhập Vận hành - khoản mục thành công!");
          this.load_VanHanhKM();
        }
      } catch (ex) {
        console.log(ex);
        this.$root.toastError("Ghi lại thất bại");
      }
    },

    tsbtnHuyBo_Click: function () {
      this.setButton(0);
    },
    tsbtnXoa_Click: function () {
      this.modalThongBaoXoaVH = true;
    },
    xacNhan: async function () {
      try {
        await this.phutro_capnhap_vanhanh_km(
          3,
          this.vanhanh_id,
          this.khoanmuc_id,
          this.khoanmuc_id_2,
          this.txtSoLuong,
          this.txtThanhtien,
          this.txtGhiChu,
          this.tugio,
          this.dengio
        );
        this.$root.toastSuccess("Xóa Vận hành - khoản mục thành công!");
        this.load_VanHanhKM();
      } catch (e) {
        console.log(e);
        this.$root.toastError("Xóa thất bại");
      }
    },
    numberWithCommas(number) {
      try {
        number = number.toString().replaceAll(",", "");
        return number.toString().replace(/^[+-]?\d+/, function (int) {
          return int.replace(/(\d)(?=(\d{3})+$)/g, "$1,");
        });
      } catch (e) {
        console.log(e);
        return 0;
      }
    },
    txtSoLuong_press: function (event) {
      if (!(event.charCode >= 48 && event.charCode <= 57))
        event.preventDefault();
      //this.txtSoLuong = this.numberWithCommas(this.txtSoLuong)
    },
    txtThanhtien_press: function (event) {
      if (!(event.charCode >= 48 && event.charCode <= 57))
        event.preventDefault();
      // this.txtThanhtien = this.numberWithCommas(this.txtThanhtien)
    },
    gridThietBi_rowSelected: function (args) {
      if (args != null) {
        this.khoanmuc_id_2 = args.data.KHOANMUC_ID;
        this.khoanmuc_id = args.data.KHOANMUC_ID;
        this.txtSoLuong = args.data.SOLUONG;
        this.txtGhiChu = args.data.GHICHU;
        this.txtThanhtien = args.data.THANHTIEN;
        this.tugio = args.data.TUGIO;
        this.dengio = args.data.DENGIO;
      }
    },
    set_datime_in_month: function (val) {
      try {
        this.convertDate(val);
      } catch (ex) {
        console.log(ex);
      }
    },
    check_valid_datetime: function (month) {
      let date = new Date();
      let lastdayInMonth = new Date(date.getFullYear(), month, 0).getDate();
      let firstay = new Date(
        date.getFullYear() + "-" + month + "-" + "1 00:00:00"
      );
      let lastday = new Date(
        date.getFullYear() + "-" + month + "-" + lastdayInMonth + " 23:59:59"
      );
      if (this.tugio < firstay && this.tugio > lastday) return false;
      if (this.dengio < firstay && this.dengio > lastday) return false;
      return true;
    },
  },
};
</script>
<style>
.modal-fullscreen .modal-dialog {
  max-width: 100%;
  margin: 0;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100vh;
  display: flex;
  position: fixed;
  z-index: 100000;
}
div#formVanHanhKM___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disable {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>