<template>
  <div class="sidebar show">
    <vue-tree-menu class="report_menu" @change="changeDonVi" labelField="report_name" valueField="report_id" :options="menu"></vue-tree-menu>
  </div>
</template>
<script>
import axios from "axios";
import VueTree from './VueTree.vue'
import { mapGetters } from "vuex";
export default {
  name: "SidebarStyle",
  components: {
    'vue-tree-menu':VueTree
  },
  data() {
    return {
      menu: [],
      original: []
    };
  },
  async mounted() {
    this.loading(true);
    await axios
      .post("/web-quantri/nguoidung/report_list")
      .then((response) => {
        this.original = response.data.data ? response.data.data : [];
        this.menu = this.getNestedChildren(this.original, '0')
        this.loading(false);
      })
      .catch((error) => {
        this.loading(false);
      });
    if(this.path && this.name) {
      let item = this.getIitemByPathAndName(this.path, this.name);
      if(item.length > 0) {

      }
    }
  },
  destroyed() {},
  computed: {
    ...mapGetters({
      appName: "appName",
      logo: "logo",
    }),
    path() {
      return this.$route.query.path
    },
    name() {
      return this.$route.query.name
    }
  },
  methods: {
    getIitemByPathAndName(path, name) {
      return this.original.filter(item => (item.path == path && item.report_name == name))
    },
    getNestedChildren(arr, parent) {
      var out = [];
      for (var i in arr) {
        if (arr[i].report_parent_id == parent) {
          var children = this.getNestedChildren(arr, arr[i].report_id);

          if (children.length) {
            arr[i].children = children;
          }

          arr[i].isOpen = true;
          out.push(arr[i]);
        }
      }
      return out;
    },
    getItem(arr, filter) {
      return arr.filter(item => item.report_id == filter)
    },
    gotoPath(item) {
      //this.initMenu(event);
      if (item.path && item.path != '0') {
        if (
          this.$route.query &&
          (this.$route.query.path !== item.path ||
            this.$route.query.name !== item.report_name)
        )
          this.$router.push(
            "/report/bi?path=" + item.path + "&name=" + item.report_name.replaceAll("&", "@@")
          );
      }
    },
    changeDonVi(id) {
      let filt = this.getItem(this.original, id)
      if(filt.length > 0) {
        let item = filt[0]
        this.gotoPath(item)
      }
    },
    checkActive(route) {
      if (this.$route.name === route) {
        return true;
      }
      if (route.includes(this.$route.name)) {
        return true;
      }
    },
    initMenu(e) {
      console.log($(e.target));
      $(e.target).parent().siblings().removeClass("active");
      $(e.target).parent().addClass("active");
      $(e.target).parent().toggleClass("open");
    },
  },
};
</script>
<style>
  .report_menu .tree-plus {
    height: auto !important;
    font-size: 12px;
  }

  .report_menu .form-control {
    border-radius: 0px;
    border: 0px;
    overflow: scroll !important;
  }
  .report_menu input.form-control {
    border: 1px solid #e0e0e0;
  }
</style>
<style scoped>
  .sidebar.show {
    margin: 0px;
    padding: 5px;
  }

  .sidebar {
    width: 400px;
    height: calc(100vh - 52px);
  }

  .sidebar {
    border: 1px solid #e0e0e0;
    border-radius: 0;
    position: unset;
    margin-left: -400px;
    overflow-y: scroll;
  }

  .report_menu .form-control {
    border: 0px;
    border-radius: 0;
  }

  .report_menu input.form-control {
    border: 1px solid #e0e0e0;
    border-radius: 0;
  }
</style>
