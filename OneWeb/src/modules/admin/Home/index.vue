<template>
  <div class="page-main-content">
    <ol>
      <li v-for="item in links" :key="item.path">
        <router-link :to="item.path">{{ item.title }}</router-link>
      </li>
      <li>
          <a href="#" v-on:click.prevent="openDialog('capnhatthaotac')">
            <span class="icon one-calendar-sync"></span> Cập nhật thao tác
          </a>
          <ProgramOperation ref="capnhatthaotac" />
      </li>
    </ol>
  </div>
</template>
<style scoped>
  div.page-main-content { height:100%; overflow-y: scroll; padding-bottom:40px; }
</style>
<script>
import ProgramOperation from '@/modules/admin/category/ProgramOperation/ProgramOperation.vue'
export default {
  components: {ProgramOperation},
  computed: {
    links: function () {
      let result = [];
      let moduleName = this.$route.matched[0].name;
      let pathArray = this.$route.path.split("/");
      this.$router.options.routes.forEach((route) => {
        if (route.name == moduleName) {
          route.children.forEach((child) => {
            result.push({
              path: "/" + moduleName + "/" + child.path,
              title: (child.meta ? child.meta.title : child.name),
            });
          });
        }
      });
      return result;
    },
  },
  methods: {
    openDialog(obj) {
      this.$refs[obj].openDialog();
    },
  }
};
</script>
