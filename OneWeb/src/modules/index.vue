<template>
<div>
  <div class="login-wrapper">
    <div class="bg" :style="{ backgroundImage: 'url(' + require('@/assets/img/bg.gray.svg') + ')' }"></div>
  </div>
</div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: 'Home',
  components: {
  },
  data: () => ({
  }),
  created: async function () {
  },
  computed: {
  },
  mounted () {
    if(!$(".sidebar").hasClass("show"))
      $(".sidebar").addClass("show");
    $(".sidebar .sidebar-content").hover(function(){
      $(".sidebar").animate({opacity: 1},'fast');
    },function(){
      $(".sidebar").animate({opacity: 0.7},'fast');
    });
  },
  methods: {
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
  }
}
</script>
<style scoped>
.sidebar {
  opacity: 0.7;
}
</style>
