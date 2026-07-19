<template>
  <div class="top-pagination marb10" v-if="totalPage >= 1">
    <span class="title">Trang</span>
    <div class="pagination inline">
      <a v-for="page in totalPage"
         @click="changePage(page)"
         :class="{active: currentPage === page}">
        {{ page }}
      </a>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    totalPage: Number,
    currentPage: Number
  },
  methods: {
    changePage(page) {
      this.$emit('pageChanged', page);
    }
  }
}
</script>
