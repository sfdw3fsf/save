<template>
  <div class="breadcrumb-top">
    <div class="main-title">{{ title }}</div>
    <ul class="breadcrumb">
      <li class="breadcrumb-item"><a href="/"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
      <!-- <li v-for="(item,index) in header.list" :key="index" class="breadcrumb-item"><router-link :to="item.link">{{item.name}}</router-link></li> -->
      <li v-for="(item,index) in currentPathItems" :key="index" class="breadcrumb-item"><a :href="getHref(item.url)">{{item.name}}</a></li>
    </ul>
  </div>
</template>
<style scoped>
.breadcrumb-top .breadcrumb { position: absolute; right:12px; }
</style>
<script>
import { mapState } from 'vuex'
export default {
  name: 'breadcrumb',
  props: {
    header: {
      type: Object
    },
  },
  data() {
    return { 
      title: "",
    }
  },
  created() {
    var menuItems = this.getMenuFromStorage();
    var currentPathItems = this.getMenuPath();
    if(this.currentPathItems && this.currentPathItems.length>0 && this.currentPathItems[this.currentPathItems.length-1].name) this.title = this.currentPathItems[this.currentPathItems.length-1].name;
    if(!this.title && this.header && this.header.title && this.header.title.trim()!="") this.title = this.header.title;
    if(!currentPathItems || currentPathItems.length<=0) { console.log("Không tìm thấy menu cho chức năng này! Vui lòng kiểm tra lại phân quyền & đường dẫn chức năng.",currentPathItems); return; }
    if(menuItems.filter(x=>this.getHref(x.url)==this.getHref(currentPathItems[currentPathItems.length-1].url) && x.name==currentPathItems[currentPathItems.length-1].name).length<=0)
      console.log("Bạn chưa được phân quyền truy cập chức năng này!",currentPathItems);
  },
  watch: {
    'header.title': function(newval) {
      if(newval) this.title = newval;
    },
    'title': function(newval) {
      if(newval) window.document.title = "OneBSS - " + newval;
      else window.document.title = "OneBSS - HỆ THỐNG ĐIỀU HÀNH SẢN XUẤT KINH DOANH TẬP TRUNG";
    }
  },
  computed: {
    // title: function(){
    //   if(this.header && this.header.title && this.header.title.trim()!="") return this.header.title;
    //   if(this.currentPathItems && this.currentPathItems.length>0 && this.currentPathItems[this.currentPathItems.length-1].name) return this.currentPathItems[this.currentPathItems.length-1].name;
    //   return "Trang chủ";
    // },
    currentPathItems: function () {
      return this.getMenuPath();
    },
    // ...mapState(['header']),
  },
  methods: {
    getHref: function (args) {
      if (args == null) return "javascript:void(0)";
      else if(args.indexOf("tag=")>=0) return args.substring(0,args.indexOf("tag=")+4)+encodeURIComponent(args.substring(args.indexOf("tag=")+4));
      else return args;
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
  },
}
</script>
