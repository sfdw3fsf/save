<template>
  <div class="page">
    <Header :citycode="citycode" :userName="userName" :nhanVien="nhanVien" />
    <SideBar :menuItems="menuItems" @tokenchanged="tokenChanged" />
    <div class="main-wrapper">
      <Breadcrumb :pathItems="currentPathItems" />
      <div class="main-content">
        <transition name="router-anim">
          <router-view :key="keyidx" />
        </transition>
      </div>
      <Footer :citycode="citycode" :userName="userName" />
    </div>
    <Loader />
    <b-modal
      ref="mainThongBao"
      id="mainThongBao"
      size="xl"
      title="Thông báo"
      hide-footer
      body-class="p-10"
    >
      <span v-html="thongbao"></span>
    </b-modal>
  </div>
</template>
<style lang="css" scoped>
.modal-dialog {
  margin: 50% 50%;
  transform: translate(-50%, -50%);
}
</style>
<script>
import "../../node_modules/@syncfusion/ej2-vue-layouts/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-base/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-buttons/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-calendars/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-dropdowns/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-inputs/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-navigations/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-popups/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-splitbuttons/styles/bootstrap4.css";
import "../../node_modules/@syncfusion/ej2-vue-grids/styles/bootstrap4.css";
import Loader from "@/components/Loader";
import SideBar from "@/components/layout/SideBar";
import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";
import Breadcrumb from "@/components/layout/BreadCrumb";
export default {
  name: "main-layout",
  components: {
    Header,
    Footer,
    SideBar,
    Loader,
    Breadcrumb,
  },
  data: function () {
    return {
      flag: false,
      citycode: "",
      userName: "",
      nhanVien: "",
      keyidx: 0,
      thongbao: "",
    };
  },
  created: async function () {
    let s = false;
    let isfirstLogedIn = false;
    if (this.$root.context.isAuthenticated()) {
      try {
        let startUpInfoJSON = localStorage.getItem("startUpInfo");
        let startUpInfo = null;
        if (startUpInfoJSON) startUpInfo = JSON.parse(startUpInfoJSON);
        if (!startUpInfo || !startUpInfo.data) {
          startUpInfo = await this.$root.context.get("/quantri/user/khoitao_ungdung", {
            p_idmodule: 12,
          });
          localStorage.setItem("startUpInfo", JSON.stringify(startUpInfo));
          // Bo sung kiem tra dong bo HRM
          if (
            startUpInfo.data.thongtin &&
            startUpInfo.data.thongtin.flag_check_hrm == 1
          ) {
            let hrminfo = await this.$root.context.get(
              "/tichhop/hrm/thongTinNguoiDung_hrm",
              {}
            );
            if (hrminfo && hrminfo.error_code == "BSS-00000000") {
              if (
                hrminfo &&
                hrminfo.data &&
                hrminfo.data.length > 0 &&
                hrminfo.data[0].trangthai == "confirmed"
              ) {
                console.log("Tim thay thong tin HRM", hrminfo.data[0]);
              } else {
                this.thongbao = `(TB) VinaPhone trân trọng thông báo: User/Eload của Quý anh/chị <strong>CHƯA THỰC HIỆN CHUẨN HÓA VÀ CHƯA CÓ MÃ ĐỊNH DANH TRÊN HRM</strong>, VinaPhone sẽ thực hiện <strong style="color:red">TẠM KHÓA</strong> User/Eload vào ngày 18/11/2024. Để không bị ảnh hưởng và gián đoạn đến công tác bán hàng và CSKH, VinaPhone Kính đề nghị Quý Kênh bán, NVKD liên hệ và phối hợp với NV quản lý Kênh, nhân viên địa bàn hoặc VinaPhone tỉnh/Tp để được hỗ trợ. Trân trọng!`;
                if (sessionStorage && sessionStorage.getItem("sthongbao")) {
                  this.thongbao = "";
                } else if (this.thongbao) {
                  sessionStorage.setItem("sthongbao", "1");
                  this.$bvModal.show("mainThongBao");
                  setTimeout(() => {
                    this.$bvModal.hide("mainThongBao");
                    this.thongbao = "";
                  }, 30000);
                }
              }
            }
          }
        }
        // let menuInfo = await this.$root.context.get(
        //   "web-quantri/nguoidung/lay_ds_main_menu_dagan_nd",
        //   {
        //     loai_ct_id: 12,
        //   }
        // );
        if (!(startUpInfo.data == null || startUpInfo.data == undefined)) {
          this.$root.context.configApplication(
            startUpInfo.data.ds_chucnang,
            // menuInfo.data.map(x=>({"stt":x.vitri,"name":x.ten_menu,"mota_cn":x.ten_hienthi,"icon":x.icon,"id":x.menu_id,"p_id":x.menu_cha_id,"url":x.url})),
            startUpInfo.data.thongtin
          );
          this.flag = !this.flag;
          s = true;
        }
        // if (!(startUpInfo.data == null || startUpInfo.data == undefined)) {
        //   this.$root.context.configApplication(
        //     startUpInfo.data.ds_chucnang,
        //     startUpInfo.data.thongtin
        //   );
        //   this.flag = !this.flag;
        //   s = true;
        // }
      } catch (e) {}
    }
    if (s) {
      try {
        s = false;
        var check_token = await this.$root.context.get(
          "/web-hopdong/lapdatmoi/check_token",
          {}
        );
        s = check_token.data == "ok";

        var check_dangnhaplandau = await this.$root.context.post(
          "/quantri/user/thongtin_nguoidung2",
          {}
        );
        isfirstLogedIn = check_dangnhaplandau.data.dangnhap_landau == "1";
        sessionStorage.setItem("dnld", check_dangnhaplandau.data.dangnhap_landau);
      } catch (e) {
        s = false;
      }
    }
    if (!s) {
      if (this.$route.name != "Login") this.$router.push({ name: "Login" });
    }

    if (isfirstLogedIn) {
      if (this.$route.name != "ChangePassword")
        this.$router.push({ name: "ChangePassword" });
    }
  },
  mounted: function () {
    this.addCssFile("/static/vendor/font-awesome/css/font-awesome.min.css");
    this.addCssFile("/static/vendor/bootstrap/css/bootstrap.min.css");
    this.addCssFile("/static/vendor/ap8/css/style.css");
    this.addCssFile("/static/vendor/nucleo/outline/css/style.css");
    this.addCssFile("/static/vendor/nucleo/glyph/css/style.css");
    this.addCssFile("/static/vendor/treegrid/css/jquery.treegrid.css");
    this.addCssFile("/static/vendor/select2/css/select2.min.css");
    this.addCssFile("/static/vendor/oneicon/style.css");
    this.addCssFile("/static/css/style.css");
    this.addCssFile("/static/css/app.css");
    this.addCssFile("/static/css/custom.css");
    this.citycode = this.$root.token.getMaTinh()
      ? this.$root.token.getMaTinh()
      : this.$root.token.getPhanVungID();
    this.userName = this.$root.token.getUserName();
    this.nhanVien = this.$root.context.user.getTenNhanVien();

    if (sessionStorage && sessionStorage.getItem("sthongbao")) {
      this.thongbao = "";
    } else if (this.thongbao) {
      sessionStorage.setItem("sthongbao", "1");
      this.$bvModal.show("mainThongBao");
      setTimeout(() => {
        this.$bvModal.hide("mainThongBao");
        this.thongbao = "";
      }, 30000);
    }
  },
  computed: {
    menuItems: function () {
      if (this.flag) return this.getMenuFromStorage();
      return this.getMenuFromStorage();
    },
    currentPathItems: function () {
      if (this.flag) return this.getMenuPath();
      return this.getMenuPath();
    },
    // userName: function () {
    //   this.citycode = (this.$root.token.getMaTinh()?this.$root.token.getMaTinh():this.$root.token.getPhanVungID());
    //   if (this.flag) return this.$root.token.getUserName();
    //   return this.$root.token.getUserName();
    // },
    // nhanVien: function () {
    //   this.citycode = (this.$root.token.getMaTinh()?this.$root.token.getMaTinh():this.$root.token.getPhanVungID());
    //   if (this.flag) return this.$root.token.getUserName();
    //   return this.$root.context.user.getTenNhanVien();
    // },
  },
  methods: {
    addCssFile: function (path) {
      let style = document.createElement("link");
      style.type = "text/css";
      style.rel = "stylesheet";
      style.href = path;
      document.head.appendChild(style);
    },
    getMenuPath: function () {
      let url = this.getCurrentPath();
      if (url != "/#/") {
        //return this.$root.context.getMenuPath(url).reverse();
        let items = this.$root.context.getMenuPath(url);
        if (items.length == 0) {
          url = this.getCurrentShortPath();
          items = this.$root.context.getMenuPath(url);
        }
        if (items.length > 0) {
          return items.reverse();
        } else {
          for (let i = 0; i < this.$route.matched.length; i++) {
            const element = this.$route.matched[i];
            items.push({
              name: element.name,
              displayName: element.name,
              url: "/#" + (i == this.$route.matched.length - 1 ? "/" : element.path),
            });
          }
          return items;
        }
      } else return [];
    },
    getCurrentPath: function () {
      let path = this.$route.fullPath;
      return "/#" + path;
    },
    getCurrentShortPath: function () {
      let path = this.$route.path;
      return "/#" + path;
    },
    getMenuFromStorage: function () {
      return this.$root.context.getMenu();
    },
    tokenChanged() {
      this.flag = !this.flag;
      this.citycode = this.$root.token.getMaTinh()
        ? this.$root.token.getMaTinh()
        : this.$root.token.getPhanVungID();
      this.userName = this.$root.token.getUserName();
      this.nhanVien = this.$root.context.user.getTenNhanVien();
      this.keyidx += 1;
    },
  },
};
</script>
