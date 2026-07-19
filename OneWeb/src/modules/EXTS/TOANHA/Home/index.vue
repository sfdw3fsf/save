<template>
  <div class="page-main-content">
  </div>
</template>
<script>
export default {
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
};
</script>
