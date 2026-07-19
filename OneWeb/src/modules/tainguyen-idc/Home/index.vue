<template>
  <div class="page-main-content">
    <ol>
      <li v-for="item in links" :key="item.path">
        <router-link :to="item.path">{{ item.title }}</router-link>
      </li>
    </ol>
  </div>
</template>
<script>
export default {
  computed: {
    links: function () {
      let result = [];
      let moduleName = this.$route.matched[0].name;
      let p = this.$route.matched[0].path;
      this.$router.options.routes.forEach((route) => {
        if (route.name == moduleName) {
          route.children.forEach((child) => {
            result.push({
              path: p+ "/" + child.path,
              title: (child.meta ? child.meta.title : child.path),
            });
          });
        }
      });
      return result;
    },
  },
};
</script>
