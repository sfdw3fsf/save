
<template src='./index.html'></template>
<script>
import api from "../api/index.js";
import { LoaiHinhTB } from "@/const/enums/index.js";
export default {
  components: {},
  data() {
    return {
      data: null,
      listLoaiHinhTb: [],
      disable: "disable",
      dataInfo: null,
      disableHuy: true,
      disableCapNhat: true,
      formView: {
        acount: "",
        loaiHinhTBSelected: "",
        tenThueBao: "",
        trangThaiThueBao: "",
        diaChiLD: "",
        passsword: "",
        newPassword: "",
      },
    };
  },
  created() {
    api.getLoaiHinhTB(this.axios).then((res) => {
      let temp = res.data.data.filter((item) => item.DICHVUVT_ID == 4);
      this.listLoaiHinhTb = res.data.data ? temp : [];
    });
  },
  methods: {
    show() {
      return this.$refs["ref-modal"].show();
    },

    hide() {
      this.$refs["ref-modal"].hide();
    },

    onModalHidden() {
      this.data = null;
      this.$emit("hidden");
    },

    setData(data) {
      this.data = data;
    },
    timeKiemThueBao() {
      let maTb = this.formView.acount.trim();
      let data = {
        in_ma_tb: maTb,
        in_dichvuvt_id: 4,
        in_donvi_dl_id: 0,
      };
      api.getThongTinTB(this.axios, data).then((res) => {
        console.log(res.data.data);
        if (res.data.data.length > 0) {
          let result = res.data.data[0];
          this.dataInfo = res.data.data[0];
          console.log(this.dataInfo);
          this.formView.tenThueBao = result.ten_kh;
          this.formView.trangThaiThueBao = result.trangthai_tb;
          this.formView.diaChiLD = result.diachi_ld;
          this.formView.loaiHinhTBSelected = result.loaitb_id;
          this.disableHuy = false;
          this.disableCapNhat = false;

          if (
            this.dataInfo.loaitb_id == LoaiHinhTB.INTERNET_ADSL ||
            this.dataInfo.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
            this.dataInfo.loaitb_id == LoaiHinhTB.WIFI_FIBER
          ) {
            api.getMatKhauTB(this.axios, maTb).then((res) => {
              this.formView.passsword = res.data.data ? res.data.data : "";
            });
          } else {
            let dataSend = {
              iptvaccount: maTb,
            };
            api.getMatKhauTBMyTV(this.axios, dataSend).then((res) => {
              this.formView.passsword = res.data.data
                ? res.data.data.iptvpw
                : "";
            });
          }
        } else {
          this.dataInfo = {};
          this.$toast.error(
            "Không tìm thấy thông tin về thuê bao: " +
              this.formView.acount +
              ". Đề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT!"
          );
          this.formView.tenThueBao = "";
          this.formView.trangThaiThueBao = "";
          this.formView.diaChiLD = "";
          this.formView.loaiHinhTBSelected = "";
          // this.disableHuy = true
          // this.disableCapNhat = true
        }
      });
    },
    capNhatThueBao() {
      try {
        if (this.formView.newPassword.trim() == "") {
          this.$toast.warning("Bạn chưa nhập mật khẩu mới");
          return;
        }
        if (
          this.dataInfo.loaitb_id == LoaiHinhTB.INTERNET_ADSL ||
          this.dataInfo.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
          this.dataInfo.loaitb_id == LoaiHinhTB.WIFI_FIBER
        ) {
          let data = {
            accountModel: {
              accountName: this.formView.acount,
              accountPassword: this.formView.newPassword,
            },
          };
          api.doiMatKhauTBVISA(this.axios, data).then((res) => {
            console.log(res.data);
          });
        }
        if (this.dataInfo.loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
          let data = {
            iptvaccount: this.formView.acount,
            newPassword: this.formView.newPassword,
            oldPassword: this.formView.passsword,
          };
          api.doiMatKhauMYTV(this.axios, data).then((res) => {
            console.log(res.data);
          });
        }
        let data = {
          THUEBAO_ID: this.dataInfo.thuebao_id,
          MA_TB: this.dataInfo.ma_tb,
          PASSWORD: this.formView.newPassword,
          PASSWORD_CU: this.formView.passsword,
          NHANVIEN_ID: this.$root.token.getNhanVienID(),
          DONVI_ID: this.$root.token.getDonViID(),
          MAY_CN: this.$root.token.getUserName(),
          NGUOI_CN: this.$root.token.getUserName(),
        };
        let temp = {
          vds: [data],
        };

        api.capNhatMatKhauTB(this.axios, temp).then((res) => {
          console.log(res.data);
        });
        this.$toast.success(
          "Cập nhật đổi mật khẩu Internet trên danh bạ viễn thông tỉnh thành công"
        );
      } catch (error) {
        this.$toast.error("Cập nhật đổi mật khẩu lỗi");
      }
    },
    clearView() {
      console.log("Click Huy");
      this.dataInfo = {};
      this.formView.acount = "";
      this.formView.tenThueBao = "";
      this.formView.trangThaiThueBao = "";
      this.formView.diaChiLD = "";
      this.formView.loaiHinhTBSelected = "";
      this.disable = "disable";
    },
  },
};
</script>
<style>
.toast:not(.showing):not(.show) {
  opacity: 1 !important;
}
.select-edit {
  height: 32px;
}
.link-click {
  cursor: pointer;
}
.disabled {
  cursor: no-drop;
  opacity: 0.7;
}

.disabled-color {
  color: gray !important;
}
.hoso-dauchuyen-daunoi-thuebao .page-content {
  overflow: visible !important;
  height: fit-content;
  background-color: white;
}
.custom-select {
  background-color: #e9ecef !important;
}
</style>

