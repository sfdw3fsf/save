<template src='./index.template.html'></template>
<script>
import moment from 'moment'
import menu from '@/utils/menu'
export default {
  name: "Login",
  data: () => ({
    username: "",
    password: "",
    otpValue: "",
    loginFailedAccount: false,
    loginFailedOTP: false,
    currentSecretCode: "",
    authType: false
  }),
  created() {
    this.$root.context.logOut();
  },
  computed: {
    stepOTP: function () {
      return this.currentSecretCode != "";
    },
    formTitle: function () {
      if (this.stepOTP) return "Xác thực OTP";
      else return "Đăng nhập";
    },
    formError: function () {
      return this.loginFailedAccount || this.loginFailedOTP;
    },
  },
  methods: {
    doLogin: async function () {
      var serverDate = await this.$root.token.getNgayCN();
      const browserDate = new Date();
      const timeDifference = browserDate - serverDate;
      if (Math.abs(timeDifference) > 5 * 60 * 1000) {
          this.$toast.error(`Thời gian trên máy người dùng đang lệch quá 5 phút so với thời gian máy chủ (Hiện tại là: ${moment(serverDate).format("DD/MM/YYYY HH:mm:ss")}). Đề nghị chỉnh lại thời gian.`);
          throw "Sai giờ";
      } else {
          // Check the browser's time zone offset
          const browserTimezoneOffset = browserDate.getTimezoneOffset();
          
          // Convert the time zone offset to hours
          const browserTimezoneOffsetHours = browserTimezoneOffset / 60;
          
          // Check if the browser's time zone offset is -420 (GMT+7)
          if (browserTimezoneOffsetHours === -7) {
              console.log('Time zone is GMT+7. OK!');
          } else {
            this.$toast.error("Múi giờ hiện tại trên máy của bạn đang thiết lập chưa đúng.\nBạn hãy thiết lập lại múi giờ: (GMT+7):  Bangkok, Hanoi, Jakarta");
            throw "Sai múi giờ";
          }
      }
      let url = "/quantri/user/login";
      let u = this.username;
      if(this.authType) {
        url = "/quantri/user/xacthuc_tapdoan";
        if(!u.endsWith("@vnpt.vn")){
          u += "@vnpt.vn";
        }
      }
      try {
        let postData = 
          {
            username: u,
            password: this.password,
          };
        // Bỏ theo yêu cầu của anh Danh ngày 19/10.
        // let ck = await this.$root.context.postWithOutToken('/quantri/user/tinhthanh_nguoidung', {username: u});
        // if(ck && ck.data && ck.data.length>1) {
        //   postData.phanvung_id = ck.data[0].phanvung_id;
        // }
        let rs = await this.$root.context.postWithOutToken(url,postData);
        return rs;
      } catch (error) {
        return error.response.data;
      }
    },
    doResetOTP: async function () {
      try {
        let rs = await this.$root.context.postWithOutToken(
          "/quantri/user/resendotp",
          {
            secretCode: this.currentSecretCode,
          }
        );
        return rs;
      } catch (error) {
        return error.response.data;
      }
    },
    doVerifyOTP: async function () {
      try {
        let rs = await this.$root.context.postWithOutToken(
          "/quantri/oauth/token",
          {
            grant_type: "password",
            client_id: "clientapp",
            client_secret: "password",
            otp: this.otpValue,
            secretCode: this.currentSecretCode,
          }
        );
        return rs;
      } catch (error) {
        return error.response.data;
      }
    },
    btnLogin_Click: async function () {
      this.$root.showLoading(true);
      try {
        let r = await this.doLogin();
        if (r.error == null) {
          this.loginFailedAccount = false;
          this.currentSecretCode = r.data.secretCode;
          // this.otpValue = "159874";
          // let tmp = await this.doVerifyOTP();
          // if (tmp.error == null) {
          //   this.$root.context.authenticate(tmp);
          //   this.loginFailedOTP = false;
          //   this.redirectToHome();
          // } else {
          //   this.loginFailedOTP = true;
          // }
        } else {
          this.loginFailedAccount = true;
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
      this.$root.showLoading(false);
    },
    btnXacNhanOTP_Click: async function () {
      this.$root.showLoading(true);
      try {
        let r = await this.doVerifyOTP();
        if (r.error == null) {
          this.$root.context.authenticate(r);
          this.loginFailedOTP = false;
          this.redirectToHome();
        } else {
          this.loginFailedOTP = true;
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnResetOTP_Click: async function () {
      this.$root.showLoading(true);
      try {
        let r = await this.doResetOTP();
        if (r.error == null) {
          this.$root.toastInfo(
            "Đã gửi lại số OTP đến số điện thoại trên tài khoản"
          );
        } else {
          this.$root.toastError(
            "Đã có lỗi xảy ra trong quá trình gửi lại số OTP"
          );
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnDungTaiKhoanKhac_Click: function () {
      this.$root.showLoading(true);
      try {
        this.username = "";
        this.password = "";
        this.otpValue = "";
        this.currentSecretCode = "";
        this.loginFailedAccount = false;
        this.loginFailedOTP = false;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    redirectToHome() {
      if(sessionStorage.getItem("currentPath") != null) {
        const path = sessionStorage.getItem("currentPath");
          menu.setCurrentMenuItemFromRouter(path)
        this.$router.push({ path: sessionStorage.getItem("currentPath") });
        sessionStorage.removeItem("currentPath");
      } else {
        this.$router.push({ name: "Home" });
      }
      // this.$router.push({ name: "Home" });
    },
  },
};
</script>
<style scoped>
.login-wrapper .form-group .input-icon.-captcha .refresh {
  right: 0px;
  top: 0px;
  height: 100%;
  font-size: 80%;
}
.login-wrapper .acts {
  text-align: right;
  padding-top: 20px;
}
.login-wrapper .acts .btn {
  padding: 10px 20px;
}
.login-wrapper .acts .btn.btn-login {
  width: 100%;
}
.login-wrapper .acts .btn.btn-outline-light {
  float: left;
}
.login-wrapper .box-login {
  min-width: 360px;
}
.login-wrapper .box-login .head {
  font-family: "Roboto";
  font-weight: normal;
}
.login-wrapper .box-login .input-icon.-captcha .form-control {
  padding-right: 100px;
}
.login-wrapper .box-login .error {
  color: #d8ff00;
}
.login-wrapper .box-login .group-hinhthuc .check-action .name {
  color: #FFF;
  padding-left: 25px;
}
.login-wrapper .box-login .group-hinhthuc .check-action .name::before {
  margin-top: 2px;
  border-radius: 50%;
  border-color: #ced4da;
}
</style>
