<template>
  <div>
    <Loader />
    <HeaderStyle1 />
    <SidebarStyle />
    <div class="main-wrapper">
        <transition name="router-anim">
            <router-view />
        </transition>
        <FooterStyle />
    </div>
  </div>
</template>
<script>

//import '../assets/vendor/font-awesome/css/font-awesome.min.css'
//import '../assets/vendor/ap8/css/style.css'
//import '../assets/vendor/nucleo/outline/css/style.css'
//import '../assets/vendor/nucleo/glyph/css/style.css'
//import '../assets/vendor/bootstrap/css/bootstrap.min.css'
import '../../node_modules/@syncfusion/ej2-base/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-buttons/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-calendars/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-dropdowns/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-inputs/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-navigations/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-popups/styles/bootstrap4.css'
import '../../node_modules/@syncfusion/ej2-splitbuttons/styles/bootstrap4.css'
import "../../node_modules/@syncfusion/ej2-vue-grids/styles/bootstrap4.css"
//import '../assets/vendor/oneicon/style.css'
//import '../assets/css/style.css'
import '../assets/css/custom.css'

import Loader from '@/components/Loader'
/*
import SidebarStyle from '@/components/SidebarStyle'
import HeaderStyle1 from '@/components/HeaderStyle1'
import FooterStyle from '@/components/FooterStyle'
*/

import SidebarStyle from "@/components/layout/SideBar"
import HeaderStyle1 from "@/components/layout/Header"
import FooterStyle from "@/components/layout/Footer"

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
    HeaderStyle1,
    FooterStyle,
    SidebarStyle,
    Loader
  },
  computed: {
  },
  beforeCreate () {
  },
  mounted () {
    document.body.classList = ''
    initMenu()
    this.addCssFile("/static/vendor/font-awesome/css/font-awesome.min.css");
    this.addCssFile("/static/vendor/bootstrap/css/bootstrap.min.css");
    this.addCssFile("/static/vendor/ap8/css/style.css");
    this.addCssFile("/static/vendor/nucleo/outline/css/style.css");
    this.addCssFile("/static/vendor/nucleo/glyph/css/style.css");
    //this.addCssFile("/static/vendor/treegrid/css/jquery.treegrid.css");
    //this.addCssFile("/static/vendor/select2/css/select2.min.css");
    this.addCssFile("/static/vendor/oneicon/style.css");
    this.addCssFile("/static/css/style.css");
    this.addCssFile("/static/css/app.css");
  },
  destroyed () {
    document.body.classList = ''
  },
  methods:{
    addCssFile: function(path){
      let style = document.createElement('link');
      style.type = "text/css";
      style.rel = "stylesheet";
      style.href = path;
      document.head.appendChild(style);
    }
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
