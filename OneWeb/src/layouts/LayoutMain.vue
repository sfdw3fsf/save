<template>
  <div>
    <!-- <HeaderStyle1 /> -->
    <!-- <SidebarStyle /> -->
    <Header :citycode="citycode" :userName="userName" :nhanVien="nhanVien" />
    <SideBar :menuItems="menuItems" @tokenchanged="tokenChanged" />
    <div class="main-wrapper">
        <!-- <Breadcrumb :header="header"></Breadcrumb> -->
        <transition name="router-anim">
            <router-view :key="keyidx" />
        </transition>
        <FooterStyle :citycode="citycode" :userName="userName" />
    </div>
    <Loader />
  </div>
</template>
<script>

// import '../assets/vendor/bootstrap/js/bootstrap.min.js'
import '../assets/vendor/font-awesome/css/font-awesome.min.css'
import '../assets/vendor/ap8/css/style.css'
import '../assets/vendor/nucleo/outline/css/style.css'
import '../assets/vendor/nucleo/glyph/css/style.css'
import '../assets/vendor/bootstrap/css/bootstrap.min.css'
import '../../node_modules/@syncfusion/ej2-base/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-buttons/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-calendars/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-dropdowns/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-inputs/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-navigations/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-popups/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-splitbuttons/styles/bootstrap4.css'
import "../../node_modules/@syncfusion/ej2-vue-grids/styles/bootstrap4.css"
import '../assets/vendor/oneicon/style.css'
import '../assets/css/style.css'
// import '../assets/vendor/bootstrap-table/bootstrap-table.min.js'
import '../assets/css/custom.css'
// import '../assets/vendor/bootstrap/js/bootstrap.bundle.min.js'

import Loader from '@/components/Loader'
//import SidebarStyle from '@/components/SidebarStyle'
import SideBar from '@/components/layout/SideBar'
import Header from '@/components/layout/Header'
//import HeaderStyle1 from '@/components/HeaderStyle1'
import FooterStyle from '@/components/layout/Footer'
// import Breadcrumb from '@/components/breadcrumb'
import { mapState } from 'vuex'

function initMenu () {
  $('.menus .down').click(function () {
    $(this).parent().siblings().removeClass('active')
    $(this).parent().toggleClass('open')
  })
  $('.nav-toggle').click(function () {
    $('.sidebar').toggleClass('show')
  })
  $('.overlay-common').click(function () {
    $('.sidebar').removeClass('show')
  })
}
export default {
  name: 'Layout-1',
  components: {
    //HeaderStyle1,
    Header,
    FooterStyle,
    //SidebarStyle,
    SideBar,
    Loader,
    //Breadcrumb
  },
  data: function () {
    return {
      flag: false,
      citycode: "",
      userName: "",
      nhanVien: "",
      keyidx: 0,
    };
  },
  created: async function () {
    let s = false;
    if (this.$root.context.isAuthenticated()) {
      try {
        // let startUpInfo = await this.$root.context.get(
        //   "/quantri/user/khoitao_ungdung",
        //   {
        //     p_idmodule: 12,
        //   }
        // );
        // let menuInfo = await this.$root.context.get(
        //   "web-quantri/nguoidung/lay_ds_main_menu_dagan_nd",
        //   {
        //     loai_ct_id: 12,
        //   }
        // );

        let startUpInfoJSON = localStorage.getItem('startUpInfo');
        let startUpInfo = null;
        if(startUpInfoJSON) startUpInfo = JSON.parse(startUpInfoJSON);
        if(!startUpInfo || !startUpInfo.data) {
          startUpInfo = await this.$root.context.get(
            "/quantri/user/khoitao_ungdung",
            {
              p_idmodule: 12,
            }
          );
          localStorage.setItem('startUpInfo', JSON.stringify(startUpInfo));
        }

        // let [startUpInfo] = await Promise.all([
        //   this.$root.context.get('/quantri/user/khoitao_ungdung', {
        //     p_idmodule: 12
        //   }),
        //   // this.$root.context.get('web-quantri/nguoidung/lay_ds_main_menu_dagan_nd', {
        //   //   loai_ct_id: 12
        //   // })
        // ]);
        if (!(startUpInfo.data == null || startUpInfo.data == undefined)) {
          this.$root.context.configApplication(
            startUpInfo.data.ds_chucnang,
            // menuInfo.data.map(x=>({"stt":x.vitri,"name":x.ten_hienthi,"mota_cn":x.ten_menu,"icon":x.icon,"id":x.menu_id,"p_id":x.menu_cha_id,"url":x.url})),
            startUpInfo.data.thongtin
          );
          this.flag = !this.flag;
          s = true;
        }
      } catch (e) {}
    }
    if(s) {
      try {
        s = false;
        var check_token = await this.$root.context.get("/web-hopdong/lapdatmoi/check_token", {});
        s = check_token.data=="ok";
      } catch (e) { s = false;}
    }
    if (!s) {
      if (this.$route.name != "Login") this.$router.push({ name: "Login" });
    }
  },
  computed: {
    menuItems: function(){
      if (this.flag) return this.getMenuFromStorage();
      return this.getMenuFromStorage();
    },
    currentPathItems: function () {
      if (this.flag) return this.getMenuPath();
      return this.getMenuPath();
    },
    // userName: function () {
    //   if (this.flag) return this.$root.token.getUserName();
    //   return this.$root.token.getUserName();
    // },
    // nhanVien: function () {
    //   // if (this.flag) return this.$root.token.getUserName();
    //   return this.$root.context.user.getTenNhanVien()?this.$root.context.user.getTenNhanVien():this.$root.token.getUserName();
    // },
    ...mapState(['header']),
  },
  beforeCreate () {
  },
  mounted () {
    document.body.classList = ''
    // initMenu()
    //this.addCssFile("/static/css/style.css");
    this.addCssFile("/static/css/app.css");
    this.citycode = (this.$root.token.getMaTinh()?this.$root.token.getMaTinh():this.$root.token.getPhanVungID());
    this.userName = this.$root.token.getUserName();
    this.nhanVien = this.$root.context.user.getTenNhanVien();
  },
  methods:{
    addCssFile: function(path){
      let style = document.createElement('link');
      style.type = "text/css";
      style.rel = "stylesheet";
      style.href = path;
      document.head.appendChild(style);
    },
    getMenuPath: function () {
      let url = this.getCurrentPath();
      if (url != "/#/") {
        return this.$root.context.getMenuPath(url).reverse();
      } else return [];
    },
    getCurrentPath: function () {
      let path = this.$route.fullPath;
      return "/#" + path;
    },
    getMenuFromStorage: function () {
      return this.$root.context.getMenu();
    },
    tokenChanged(reload = true) {
      this.flag = !this.flag;
      this.citycode = (this.$root.token.getMaTinh()?this.$root.token.getMaTinh():this.$root.token.getPhanVungID());
      this.userName = this.$root.token.getUserName();
      this.nhanVien = this.$root.context.user.getTenNhanVien();
      if(reload) this.keyidx += 1; // mặc định reload page
    }
  },
  destroyed () {
    document.body.classList = ''
  }
}
</script>
<style>
    .vue-treeselect__control {
      height: 32px !important;
    }

    .vue-treeselect__placeholder, .vue-treeselect__single-value {
      line-height: 32px !important;
    }
</style>
